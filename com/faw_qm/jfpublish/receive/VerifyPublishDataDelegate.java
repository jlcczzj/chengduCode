package com.faw_qm.jfpublish.receive;
//SS1 零部件编号相同的情况除了part和gpart外，现在还多了变速箱的相同编号情况，qmpartmasterbsoid不同，有(BSXUP)后缀。 liunan 2013-7-24
//SS2 技术中心发零部件发布到中心设计视图 刘家坤 2014-7-8
//SS3 发布试制状态零部件。 liunan 2017-7-6
//SS4 成都回导数据是工程视图，改为工程视图查找，否则找到中心视图的件不是最新版本修订会出错。 liunan 2018-1-11
/**
 * <p>Title: cPDM集成引擎</p>
 * <p>Description: cPDM集成引擎</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: FAW_QM</p>
 * @author whj
 * @version 1.0
 */

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
//CCBegin by liunan 2008-09-02
import java.util.StringTokenizer;
//CCEnd by liunan 2008-09-02

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrValueInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemInfo;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Att;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Script;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;

public class VerifyPublishDataDelegate extends BaseCommandDelegate {

	// private String notenumber = null;

	public VerifyPublishDataDelegate() {
	}

	public Script _invoke(Script script) throws QMException {

		Command cmd = script.getCommand();
		SessionService sservice = (SessionService) getEJBService("SessionService");
		UserIfc user = sservice.getCurUserInfo();
		// 为当前用户设置管理员权限
		sservice.setAdministrator();
		String notenumber = cmd.paramValue("NOTENUMBER");
		// 获得输入组
		Group groupIn = script.getGroupIn(cmd.getGroupInName());
		// 获得输出组
		Group groupOut = script.getGroupOut(cmd.getGroupOutName());
		if (groupOut == null) {
			groupOut = new Group();
			groupOut.setName(cmd.getGroupOutName());
		}
		// System.out.println("接收到命令请求:" + cmd.getName());
		String type = cmd.paramValue("BSONAME");
		if (type.toUpperCase().trim().equals("DOC")) {
			groupOut = getNotExistDoc(groupIn, groupOut);
		}
		if (type.toUpperCase().trim().equals("PART")) {
			log("数据发布开始，发布令号是：" + notenumber);
			// groupIn.toXML(new PrintWriter(System.out), true);
			groupOut = getPublishParts(groupIn, groupOut);
			// 2006-06-16添加，当没有零件需要发布时，也创建日志文档
			if (groupOut.getElementCount() == 0) {
				if (PublishHelper.VERBOSE)
					System.out.println("发布令号是：" + notenumber + ",没有要发布的零部件！！！");
				// createLogDoc(notenumber);
			}
		}
		//CCBegin by liunan 2010-03-31
		if (type.toUpperCase().trim().equals("CONNECT")) {
			System.out.println("%%%%%%%%%%%%%%%%%链接测试%%%%%%%%%%%%%%%%%");
			groupOut = new Group();
		}
		//CCEnd by liunan 2010-03-31
		sservice.freeAdministrator();
		Script response = new Script();
		response.addVdb(groupOut);
		// String s = response.get236

		// Group outGrp = response.getGroupOut();
		// outGrp.toXML(new PrintWriter(System.out), true);
		return response;
	}

	/**
	 * 创建日志文档，说明本次没有数据需要发送
	 *
	 * @param notenumber
	 * @throws QMException
	 */
	private void createLogDoc(String notenumber) throws QMException {
		if (PublishHelper.VERBOSE)
			System.out.println("=====>createLogDoc,notenumber: " + notenumber);
		if (notenumber == null || notenumber.equals("")) {
			return;
		}
		PersistService ps = (PersistService) getEJBService("PersistService");
		StandardDocService docServ = (StandardDocService) getEJBService("StandardDocService");
		SessionService sservice = (SessionService) getEJBService("SessionService");
		DocInfo docInfo = PublishHelper.getDocInfoByNumber(notenumber);
		String docCfBsoID = PublishHelper.getDocCf("其它类\\数据发布接收日志");
		DocFormData formData = new DocFormData();
		if (docInfo == null) {
			formData.setDocumentAttribute("docName", notenumber);
			// 设置文档编号
			formData.setDocumentAttribute("docNum", notenumber);
			// 设置内容简述
			formData.setDocumentAttribute("contDesc", "此采用通知书的相关部件在系统中已经存在！");
			// 设置创建单位
			formData.setDocumentAttribute("createDept", "技术中心");
			// 设置文档类别
			formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
			// 设置资料夹
			formData.setDocumentAttribute("folder", "\\Root\\数据接收其它\\数据接收确认通知书");
			// 设置生命周期
			formData.setDocumentAttribute("lifecycleTemplate", "缺省");
			UserIfc user = sservice.getCurUserInfo();
			if (user != null) {
				formData.setDocumentAttribute("iterationCreator", user
						.getBsoID());
				formData.setDocumentAttribute("iterationModifier", user
						.getBsoID());
				formData.setDocumentAttribute("aclOwner", user.getBsoID());
				formData.setDocumentAttribute("creator", user.getBsoID());
			}

      //CCBegin by liunan 2008-08-07
      //现版本doc服务中已经存在createDoc方法，但返回的是vector，中间处理过程一致，
      //此处直接调用最新版本服务，然后得到docInfo。
      //原代码如下
			//docInfo = (DocInfo) docServ.createDoc(formData);
			Vector docvec = (Vector) docServ.createDoc(formData);
			docInfo = (DocInfo)docvec.elementAt(0);
			//CCBegin by liunan 2008-08-07

			docInfo = (DocInfo) ps.refreshInfo(docInfo);
		} else {
			ContentService cService = (ContentService) getEJBService("ContentService");
			ContentItemInfo item = (ContentItemInfo) cService
					.getPrimaryContent(docInfo);
			if (item != null && item instanceof ApplicationDataInfo) {
				cService.deleteApplicationData(docInfo,
						(ApplicationDataInfo) item);
			}
			// formData.setDocumentAttribute("lifeCState",State.toState("RECEIVING"));
			// 设置内容简述
			// formData.setDocumentAttribute("contDesc",
			// "此采用通知书的相关部件在系统中已经存在！");
			LifeCycleService lifecycleService = (LifeCycleService) EJBServiceHelper
					.getService("LifeCycleService");
			LifeCycleTemplateInfo info = lifecycleService
					.getLifeCycleTemplate("缺省");
			docInfo.setLifeCycleTemplate(info.getBsoID());
			docInfo.setContDesc("此采用通知书的相关部件在系统中已经存在！");
			docInfo.setLifeCycleState(LifeCycleState.toLifeCycleState("QIYENEIKONG"));
			docInfo = (DocInfo) docServ.reviseDoc(docInfo, formData);
		}
	}

	private Group getNotExistDoc(Group groupIn, Group groupOut)

	{
		if (PublishHelper.VERBOSE)
			System.out.println("===============>>>进行文档验证");
		log("文档验证结果：");
		if (groupIn == null) {
			log("输入的文档集合为空，没有需要验证的文档。");
			return groupOut;
		}
		Enumeration enu = groupIn.getElements();
		Element ele = null;
		String numBeforeTrim = null;
		String numAfterTrim = null; // 为解决wichill端个别零部件和文档编号中存在空格的现象，在接受端作搜索和保存时要先去掉空格，验证时返回给发送端的要保留空格。
		String version = null;
		StringBuffer resultLogBuff = new StringBuffer();// 记录验证结果
		int creatNum = 0;
		int reviseNum = 0;
		try {
			PersistService pservice = (PersistService) getEJBService("PersistService");
			AffixAttrService affService = (AffixAttrService) getEJBService("AffixAttrService");
			StandardDocService docService = (StandardDocService) getEJBService("StandardDocService");
			while (enu.hasMoreElements()) {

				ele = (Element) enu.nextElement();
				numBeforeTrim = (String) ele.getValue("num");
				numAfterTrim = numBeforeTrim.trim();
				version = (String) ele.getValue("version");
				QMQuery query = new QMQuery("DocMaster");
				query.addCondition(new QueryCondition("docNum", "=",
						numAfterTrim));
				Collection result = pservice.findValueInfo(query);
				if (result == null || result.size() == 0) {
					Element elem = new Element();
					elem.addAtt(new Att("create", numBeforeTrim));
					groupOut.addElement(elem);
					resultLogBuff.append(numBeforeTrim + "(create),");
					creatNum++;
				} else {
					// 系统中存在相同编号的文档对象，取出文档对象
					DocMasterIfc docMaster = (DocMasterIfc) result.iterator()
							.next();
					if (PublishHelper.VERBOSE)
						System.out.println("------------------------------->"
								+ docMaster.getDocNum());
					VersionControlService ser = (VersionControlService) getEJBService("VersionControlService");
					Collection coll = (Collection) ser
							.allVersionsOf((DocMasterIfc) docMaster);

					Iterator iterator = coll.iterator();
					DocIfc doc = null;
					if (iterator.hasNext()) {
						doc = (DocIfc) iterator.next();
						// 得到文档的所有附加属性值对象集合
					}
					Vector attvalues = affService.getAffixAttr(doc);
					// 定义附加属性值对象
					AttrValueInfo attvalue = null;
					// 定义附加属性定义值对象
					AttrDefineInfo attDefine = null;
					int i = attvalues.size();
					boolean flag = false;
					for (int j = 0; j < i; j++) {
						attvalue = (AttrValueInfo) attvalues.elementAt(j);
						// 通过附加属性值的对象取得属性定义的ID并刷出值对象
						attDefine = (AttrDefineInfo) pservice.refreshInfo(
								attvalue.getAttrDefID(), false);
						// 如果属性定义的名称等于"version"，取出附加属性值与接收的数据比较
						if (attDefine.getAttrName().toLowerCase().trim()
								.equals("sourceversion")) {
							flag = true;
							String verValue = attvalue.getValue();

                                                        //CCBegin by liunan 2008-09-02
                                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                                        //改用产品中版本服务采用的版本比较方法。
							//if (version.compareTo(verValue) > 0) {
                              //System.out.println(version+"==============getNotExistDoc()============="+verValue);
                                                        if(getPublishFlag(version,verValue)){
                                                        //CCEnd by liunan 2008-09-02
								Element redocele = new Element();
								redocele
										.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(redocele);
								resultLogBuff.append(numBeforeTrim
										+ "(revise),");
								reviseNum++;
							} else {
								resultLogBuff.append(numBeforeTrim
										+ "(notPublish),");
							}
						}
					}
					// 如果附加属性中没有version信息，并且有同号文档存在，修订该文档
					if (!flag) {
						Element redocele = new Element();
						redocele.addAtt(new Att("revise", numBeforeTrim));
						groupOut.addElement(redocele);
						resultLogBuff.append(numBeforeTrim + "(revise),");
						reviseNum++;
					}
				}
			}
		} catch (QMException e) {
			String msg = "查询结果失败－－－－－－";
			errorLog(msg);
			errorLog(e);
		}
		log(resultLogBuff);
		log("文档验证结果统计, 总数:" + groupIn.getElementCount() + ",需要创建的文档个数:"
				+ creatNum + ",需要修订的文档个数:" + reviseNum);
		return groupOut;
	}

	private Group getPublishParts(Group groupIn, Group groupOut)

	{
		if (PublishHelper.VERBOSE)
			System.out.println("===============>>>进行零部件验证");
		log("零部件验证结果：");
		if (groupIn == null) {
			log("输入的文档集合为空，没有需要验证的文档。");
			return groupOut;
		}
		Enumeration enumer = groupIn.getElements();
		Element ele = null;
		String numBeforeTrim = null; // 为解决wichill端个别零部件和文档编号中存在空格的现象，在接受端作搜索和保存时要先去掉空格，验证时返回给发送端的要保留空格。
		String numAfterTrim = null;
		String version = null;
		String classType = null;
		//CCBegin by liunan 2009-01-21 发布零部件的生命周期状态，用于修改之前全状态发布时未生准状态的零部件信息验证。
		String lifecyclestate = null;
		//CCEnd by liunan 2009-01-21
		//CCBegin by liunan 2009-11-08 将零部件定义挪到循环内，防止错误得到上一循环中的值。
		//QMPartInfo part = null;
		//QMPartMasterIfc master = null;
		//CCEnd by liunan 2009-11-08
		StringBuffer resultLogBuffer = new StringBuffer();
		int createNum = 0;
		int reviseNum = 0;

		IBAValueService ibaService = null;
		try {
			ibaService = (IBAValueService) getEJBService("IBAValueService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PersistService pservice = null;
		try {
			pservice = (PersistService) getEJBService("PersistService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (enumer.hasMoreElements()) {
			//CCBegin by liunan 2009-11-08 零部件定义挪到循环内
			QMPartInfo part = null;
			QMPartMasterIfc master = null;
			//CCEnd by liunan 2009-11-08
			ele = (Element) enumer.nextElement();
			numBeforeTrim = (String) ele.getValue("num");
			numAfterTrim = numBeforeTrim.trim();
			version = (String) ele.getValue("version");
			classType = (String) ele.getValue("classtype");
			//CCBegin by liunan 2009-01-21
			lifecyclestate = (String) ele.getValue("lifecyclestate");
			//CCEnd by liunan 2009-01-21
			try {
				log("验证零部件：" + numBeforeTrim);
				// notenumber=(String)ele.getValue("notenumber");
				QMQuery query = new QMQuery("QMPartMaster");
				query.setChildQuery(true);
				query.addCondition(new QueryCondition("partNumber", "=",
						numAfterTrim));
				Collection result = pservice.findValueInfo(query);
				if (result.isEmpty() || result.size() == 0) {
					Element elem = new Element();
					elem.addAtt(new Att("create", numBeforeTrim));
					groupOut.addElement(elem);
					resultLogBuffer.append(numBeforeTrim + "(create),");
					createNum++;
				} else {
					DefaultAttributeContainer attc = null;
					AbstractValueView[] valueview = null;
					AbstractValueIfc value = null;
					int m = 0;
					//CCBegin by liunan 2010-05-04 如果存在2个同编号的主信息，说明这个编号的part和gpart都有。
					//此时只取gpart，目前数据发布认为有相同编号的part和gpart都存在时，再发布的一定是gpart。
					//源代码
					//master = (QMPartMasterInfo) result.iterator().next();
					//CCBegin SS1
					//if(result.size()==2)
					if(result.size()>=2)
					{
						//System.out.println("in getPublishParts 存在相同编号的part和gpart！！！");
						//System.out.println("in getPublishParts 存在相同编号的part、gpart 或 变速箱part！！！");
						QMPartMasterIfc mastertemp = null;
						QMPartMasterIfc qmparttemp = null;
					//CCEnd SS1
						Iterator iterator = result.iterator();
						while (iterator.hasNext())
						{
							QMPartMasterIfc temp = (QMPartMasterInfo) iterator.next();
							//System.out.println("temp==="+temp);
							if(temp.getBsoID().startsWith("GenericPart"))
							{
							//CCBegin SS1
								//master = temp;
								mastertemp = temp;
							}
							
							else if(temp.getBsoID().endsWith("(BSXUP)"))
							{
								continue;
							}
							else
							{
								qmparttemp = temp;
							}
							//CCEnd SS1
						}
						//CCBegin SS1
						if(mastertemp!=null)
						{
							master = mastertemp;
						}
						else
						{
							master = qmparttemp;
						}
						//System.out.println("此时得到的零部件为： "+master);
						//CCEnd SS1
						//CCBegin by liunan 2010-05-20 解放系统中存在相同编号的多个partmaster情况
						//所以master为null时表示是这种情况，此时按以前方法取第一个，如果能从系统中保证只有一个
						//partmaster，则此条件不会进入。
						if(master==null)
						{
							master = (QMPartMasterInfo) result.iterator().next();
						}
						//CCEnd by liunan 2010-05-20
					}
					else
					{
						master = (QMPartMasterInfo) result.iterator().next();
					}
					//CCEnd by liunan 2010-05-04
					
					try {
						VersionControlService ser = (VersionControlService) EJBServiceHelper
								.getService("VersionControlService");
						Collection coll = (Collection) ser
								.allVersionsOf(master);
						Iterator iterator = coll.iterator();
						//取出最新的工程视图版本
						while (iterator.hasNext()) {
							QMPartInfo aPart = (QMPartInfo) iterator.next();
							//CCBegin SS2 SS4
							if (aPart.getViewName() != null
									&& (aPart.getViewName().trim().equals("工程视图")||aPart.getViewName().trim().equals("中心设计视图"))) {
//							if (aPart.getViewName() != null
//									&& aPart.getViewName().trim().equals("中心设计视图")) {
								//CCEnd SS2 SS4
								part=aPart;
								break;
							}
						}
						//如果没有工程视图，则取最新的制造视图
						if(part==null)
						{
							part=(QMPartInfo) coll.iterator().next();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (((part.getBsoName().toLowerCase()).equals("qmpart") && classType
							.toLowerCase().equals("genericpart"))
							|| (part.getBsoName().toLowerCase().equals(
									"genericpart") && classType.toLowerCase()
									.equals("wtpart"))) {
						String msg = "";
						//CCBegin by liunan 2010-05-04 如果系统中存在part，但此次发布的是gpart，则create此gpart。
						//源代码
						/*if (classType.toLowerCase().trim().equals("part")
								|| classType.toLowerCase().trim().equals(
										"wtpart")) {
							msg = "零部件" + numAfterTrim
									+ "在目标系统中存在相同编号的广义部件，信息冲突，发布失败！";
						} else {
							msg = "广义部件" + numAfterTrim
									+ "在目标系统中存在相同编号的零部件，信息冲突，发布失败！";
						}
						errorLog(msg);
						groupOut = null;
						return groupOut;*/
						if (classType.toLowerCase().trim().equals("part")
								|| classType.toLowerCase().trim().equals(
										"wtpart")) 
						{
							msg = "零部件" + numAfterTrim
									+ "在目标系统中存在相同编号的广义部件，信息冲突，发布失败！";
							errorLog(msg);
							groupOut = null;
							return groupOut;
						} 
						else 
						{
							Element elem = new Element();
							elem.addAtt(new Att("create", numBeforeTrim));
							groupOut.addElement(elem);
							resultLogBuffer.append(numBeforeTrim + "(create),");
							createNum++;
							continue;
						}
						//CCEnd by liunan 2010-05-04
						
					}

                                        //CCBegin by liunan 2009-01-21
                                        String partState = part.getLifeCycleState().toString();
                                        String partnote = part.getIterationNote();
                                        //CCEnd by liunan 2009-01-21

					part = (QMPartInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(part);
					attc = (DefaultAttributeContainer) part
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					m = valueview.length;
					boolean flag = false;
					boolean note1 = false;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							flag = true;
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], part);
							String s = ((StringValueIfc) value).getValue();
							//log("零部件属性：" + numBeforeTrim + " , " + version + " , " + classType + " , " + lifecyclestate + " , " + partState + " , " + partnote + " , " + s);
							//CCBegin by liunan 2008-09-02
							//修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
							//改用产品中版本服务采用的版本比较方法。
							//if (version.compareTo(s) > 0) {
							//System.out.println(version+"=============="+part.getPartNumber()+"============="+s);
							if(getPublishFlag(version,s)){
								//CCEnd by liunan 2008-09-02
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCBegin by liunan 2009-01-21
							//如果版本一样，则比较生命周期状态，汽研为前准，解放为非生准且非前准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。							
							//CCBegin by liunan 2012-05-14 存在生产状态后，再把生准状态发布过来的问题。
							//else if(version.equals(s)&&!partState.equals("PREPARING")&&lifecyclestate.equals("PREPARE"))
							else if(version.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&lifecyclestate.equals("PREPARE"))
							//CCEnd by liunan 2012-05-14
							{
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCBegin by liunan 2011-08-01
							//如果版本一样，则比较生命周期状态，汽研为前准，解放为非生准且非前准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。							
							else if(version.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&lifecyclestate.equals("ADVANCEPREPARE"))
							{
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCEnd by liunan 2011-08-01
							//如果版本一样，则比较生命周期状态，汽研为生准，解放为生准，且解放的“版本注释”属性含有“前准”字样，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
							//CCBegin by liunan 2011-04-22 前准判断由>改成>=
							else if(version.equals(s)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("前准")>=0&&lifecyclestate.equals("PREPARE"))
							{
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCEnd by liunan 2009-01-21
							//CCBegin SS3
							//如果版本一样，则比较生命周期状态，汽研为试制，解放为非生准且非前准非生产，则发布，但实际保存时只作状态和采用、更改通知书属性修改。							
							else if(version.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&!partState.equals("MANUFACTURING")&&!partState.equals("SHIZHI")&&lifecyclestate.indexOf("TEST")!=-1)
							{
								System.out.println("anan 验证要发布的零部件："+numBeforeTrim);
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCEnd SS3
							else {
								resultLogBuffer.append(numBeforeTrim
										+ "(notPublish),");
								note1 = true;
							}
						}
					}
					if (!flag) {
						Element ele2 = new Element();
						ele2.addAtt(new Att("revise", numBeforeTrim));
						groupOut.addElement(ele2);
						resultLogBuffer.append(numBeforeTrim + "(revise),");
						reviseNum++;
					}
				}
			} catch (QMException e) {
				String msg = "查询结果失败,零部件编号：" + numBeforeTrim;
				errorLog(msg);
				errorLog(e);
			}
		}
		log(resultLogBuffer);
		log("零部件验证结果统计, 总数:" + groupIn.getElementCount() + ",需要创建的零部件个数:"
				+ createNum + ",需要修订的零部件个数:" + reviseNum);
		return groupOut;
	}

	private void log(Object msg) {

		PublishPartsLog.log(msg);
	}

	private void errorLog(Object msg) {
		if (msg instanceof Throwable) {
			PublishPartsLog.log((Throwable) msg);
		} else {
			PublishPartsLog.log("*****ERROR: " + msg);
		}
	}

        /**
         * 比较两个版本的大小，决定是否发布此对象。
         * @param s1 String 汽研最新版本。
         * @param s2 String 解放pdm中记录的该对象对应汽研的版本。
         * @return boolean 需要发布时返回true，否则返回false。
         * @author liunan 2008-09-02
         */
        private boolean getPublishFlag(String s1, String s2)
        {
          if(s1.indexOf(".")<0)
          {
            s1 = s1 + ".1";
          }
          if (s1.equals(s2)) {
            return false;
          }

          //利用StringTokenizer分割st1
          StringTokenizer st1 = new StringTokenizer(s1, ".");
          //利用StringTokenizer分割st2
          StringTokenizer st2 = new StringTokenizer(s2, ".");
          int level1 = st1.countTokens();
          int level2 = st2.countTokens();
          //两个版本值长度大为较新的
          if (level1 < level2) {
            return false;
          }
          if (level1 > level2) {
            return true;
          }
          String[] sarray1 = new String[level1];
          String[] sarray2 = new String[level2];
          //把分割后的字串，加入字符串数组
          for (int i = 0; i < level1; i++) {
            sarray1[i] = st1.nextToken();
            sarray2[i] = st2.nextToken();
          }
          //在循环中比较数组中的字串
          for (int k = 0; k < level1; k++) {
            if (sarray1[k].length() > sarray2[k].length()) {
              return true;
            }
            if (sarray1[k].length() < sarray2[k].length()) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) < 0) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) > 0) {
              return true;
            }
          }
          return false;
        }

}
