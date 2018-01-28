/**
 * SS1 修订时会带着上一版的附件，如果此次修订的件没有发布附件，就会看的是上一版附件。
 * 此前在修订后保存前做过附件删除，但其中提到logicbase信息，忘记如何处理了。改在最后删除。 liunan 2012-12-10
 * SS2 “不是唯一的”的是重复创建时提示的，当作成功返回。 liunan 2014-2-19
 * SS3 对于变速箱设计零部件有如下新需求：1、将重号件设计视图发布至工程视图。 2、将工程视图升级版本。 liunan 2014-4-4
 * SS4 如果是由于已经在其他发布中创建完引起的异常，则显示发布成功。 liunan 2014-4-14
 * SS5 技术中心发零部件发布到中心设计视图 刘家坤 2014-7-8
 * SS6 修订之后的零件设置为中心设计视图 刘家坤 2014-10-8
 * SS7 解放设计到汽研后，解放设计零部件都导出到汽研，因此会重新发布到解放，目前解放零部件都没有发布源版本，解放在汽研设计升级后，
 * 升版件和解放上一版件会在一个大版本中。改为：在解放系统中没有发布源版本时，如果汽研件是A版则升小版序，否则修订。liunan 2017-5-23
 * SS8 零部件升版后，将版本注释属性清空。 liunan 2017-7-3
 * SS9 发布试制状态零部件。 liunan 2017-7-6
 * SS10 双版本发布规则调整。 liunan 2017-7-25
 * SS11 成都回导数据是工程视图，改为工程视图查找，否则找到中心视图的件不是最新版本修订会出错。 liunan 2018-1-11
 */
 // 
package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;	
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
//CCBegin by liunan 2008-09-02
import java.util.StringTokenizer;
//CCEnd by liunan 2008-09-02

import com.faw_qm.acl.model.AdHocControlledIfc;
import com.faw_qm.acl.util.AclEntrySet;
import com.faw_qm.acl.util.AdHocAcl;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.definition.litedefinition.BooleanDefView;
import com.faw_qm.iba.definition.litedefinition.FloatDefView;
import com.faw_qm.iba.definition.litedefinition.IntegerDefView;
import com.faw_qm.iba.definition.litedefinition.RatioDefView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.litedefinition.TimestampDefView;
import com.faw_qm.iba.definition.litedefinition.URLDefView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.BooleanValueDefaultView;
import com.faw_qm.iba.value.litevalue.FloatValueDefaultView;
import com.faw_qm.iba.value.litevalue.IntegerValueDefaultView;
import com.faw_qm.iba.value.litevalue.RatioValueDefaultView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.litevalue.TimestampValueDefaultView;
import com.faw_qm.iba.value.litevalue.URLValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.logic.LogicBase;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
//CCBegin by liunan 2008-10-22
import com.faw_qm.version.model.IteratedIfc;
//CCEnd by liunan 2008-10-22

//CCBegin by liunan 2008-11-17
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.lock.util.LockHelper;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.lock.exception.LockException;
//CCEnd by liunan 2008-11-17

//CCBegin by liunan 2009-11-06
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import com.faw_qm.integration.util.QMProperties;
//CCEnd by liunan 2009-11-06

import com.faw_qm.session.ejb.service.SessionService;

//CCBegin by liunan 2010-01-20
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
//CCEnd by liunan 2010-01-20
import com.jf.util.changeVersion;
//CCBegin SS10
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//CCEnd SS10

public class PartReviseDelegate extends AbstractStoreDelegate {

	public static final String DEFAULT_PART_LOCATION = "\\Root\\Administrator";

	private Group myParts;

	private Hashtable myPartIBAMap;
	
	//CCBegin by liunan 2009-11-06 添加对logicbase的保存
	private static String LOGICBASE_PATH;

	static {
		try {
			LOGICBASE_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.logicbase.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm");
	//CCEnd by liunan 2009-11-06
	
	public PartReviseDelegate(Group parts, Hashtable partIBAMap,
			PublishLoadHelper helper) {
		this.myParts = parts;
		this.myPartIBAMap = partIBAMap;
		this.myHelper = helper;
	}

	public ResultReport process() {
		userLog("##RevisePart");
		ResultReport result = new ResultReport();
		if (myParts == null || myParts.getElementCount() == 0) {
			log("没有要修订的零部件");
			return result;
		}
		else
		{
			System.out.println("发布零部件的个数为："+myParts.getElementCount());
		}
		StandardPartService partSer = null;
		try {
			partSer = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IBAValueService ibaService = null;
		try {
			ibaService = (IBAValueService) PublishHelper
					.getEJBService("IBAValueService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ConfigService cservice = null;
		try {
			cservice = (ConfigService) PublishHelper
					.getEJBService("ConfigService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Enumeration enumeration = myParts.getElements();
		PersistService ps = null;
		try {
			ps = (PersistService) PublishHelper.getEJBService("PersistService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element ele = null;
		String num = null;
		String name = null;
		String location = null;
		String partType = null;
		String source = null;
		String type = null;
		String lifecycle = null;
		String project = null;
		String viewName = null;
		String maxCompIdUsed = null;
		String sourceVersion = null;
		String modifier = null;
		QMPartIfc part = null;
		int serNum = 0;
		boolean isgpart = false;
		log("修订零部件信息:");
		QMTransaction transaction = null;
		DefaultAttributeContainer attc = null;
		AbstractValueView[] valueview = null;
		AbstractValueIfc value = null;
		String fbsourversion = "";
		String receproversion = "";
		//CCBegin by liunan 2008-10-28
		//添加生命周期状态的发布。
		String lifecyclestate = "";
		String qylifecyclestate = "";
		//CCEnd by liunan 2008-10-28
		//CCBegin by liunan 2009-12-07 添加是否有附件的标识。
		//String isHasCont = ""; 
		//CCEnd by liunan 2009-12-07
		while (enumeration.hasMoreElements()) {
			myHelper.rezong++;
			serNum++;
			ele = (Element) enumeration.nextElement();
			num = ((String) ele.getValue("num")).trim();
			partType = (String) ele.getValue("partType");
			type = (String) ele.getValue("type");
			source = (String) ele.getValue("source");
			sourceVersion = (String) ele.getValue("sourceVersion");
			modifier = (String) ele.getValue("modify");
			isgpart = type.toLowerCase().trim().equals("genericpart");
			//CCBegin by liunan 2011-09-29 去掉名称中空格。
			//name = (String) ele.getValue("name");
			name = ((String) ele.getValue("name")).trim();
			//CCEnd by liunan 2011-09-29
			//CCBegin by liunan 2009-09-24 如果名称中含有英文“;”，则改成中文“；”
			name = name.replaceAll(";","；");
			//CCBegin by liunan 2009-09-24
			viewName = (String) ele.getValue("viewName");
		  //CCBegin by liunan 2008-10-28
	  	//添加生命周期状态的发布。
			lifecyclestate = (String) ele.getValue("lifecyclestate");
			qylifecyclestate = lifecyclestate;
			
			//CCBegin by liunan 2009-12-07 获得是否有附件的标识。
			//isHasCont = (String) ele.getValue("isHasCont");
			//CCEnd by liunan 2009-12-07
			
			//CCBegin by liunan 2011-03-14 避免用户手工创建零部件时，fbsourversion没有赋值还是用上一条记录。
			fbsourversion = "";
			//CCEnd by liunan 2011-03-14

			//System.out.println("汽研零部件生命周期状态======="+lifecyclestate);
			//通过属性文件获取与之对应的解放生命周期状态。
			lifecyclestate = myHelper.mapPro.getProperty("part.lifecyclestate." + lifecyclestate);
			//System.out.println("解放零部件生命周期状态======="+lifecyclestate);
		  //CCEnd by liunan 2008-10-28

			// 读取属性文件，获得视图对应值
			viewName = myHelper.mapPro.getProperty("part.viewName." + viewName);
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Part (num=" + num + " name=" + name
						+ ") ERROR: 零部件编号为空";
				result.addErroMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + partType + ",失败");
				errorLog(msg);
				continue;
			}
			location = (String) ele.getValue("path");
			if (location == null || location.trim().equals("")) {
				//location = DEFAULT_PART_LOCATION;
				location = myHelper.mapPro.getProperty("part.location.default");
			} else {
				location=PublishHelper.cutPathString(location);
				if (location.startsWith("/")) {
					location = location.substring(1);
				}
				if (location.startsWith("Standard_part")) {
					location = location.substring(0, 13);
					String stand = location.substring(13);
					int l = stand.indexOf("/");
					if (l != -1 && l != 0) {
						location = location + stand.substring(0, l);
					}
				} else {
					int h = location.indexOf("/");
					int m = 0;
					if (h == -1) {
						m = location.indexOf("\\");
						if (m != -1) {
							location = location.substring(0, m);
						}
					} else {
						location = location.substring(0, h);

					}
				}

				if (isgpart) {
					location = myHelper.mapPro.getProperty("gpart.location."
							+ location, null);
					if (location == null || location.trim().equals("")) {
						location = myHelper.mapPro
								.getProperty("gpart.location.default");
					}
				} else {
					location = myHelper.mapPro.getProperty("part.location."
							+ location);
					if (location == null || location.trim().equals("")) {
						location = myHelper.mapPro
								.getProperty("part.location.default");
					}
				}
			}
			// location = PublishHelper.strDecoding(location, "ISO8859-1");
			location = PublishHelper.normalLocation(location);

			lifecycle = myHelper.mapPro.getProperty("jfpublish.part.lifecycle",
					null);
			if (lifecycle == null || lifecycle.trim().equals("")) {
				lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
			} // else {
			// lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			// }

			Properties sourceProps = new Properties();
			if (myHelper.dataSource != null && myHelper.dataSource.length() > 0) {
				sourceProps.put("dataSource", myHelper.dataSource);
			}
			if (myHelper.publishDate != null
					&& myHelper.publishDate.length() > 0) {
				sourceProps.put("publishDate", myHelper.publishDate);
			}
			if (sourceVersion != null && sourceVersion.length() > 0) {
				sourceProps.put("sourceVersion", sourceVersion);
			}
			else
			{
				System.out.println("anantt revise   零部件 "+num+" sourceVersion  is  null!!!       and =="+ (String) ele.getValue("sourceVersion"));//anan
			}
			if (myHelper.publishForm != null
					&& myHelper.publishForm.length() > 0) {
				sourceProps.put("publishForm", myHelper.publishForm);
			}
			if (modifier != null && modifier.length() > 0) {
				sourceProps.put("modifier", modifier);
			}
			if (myHelper.noteNum != null && myHelper.noteNum.length() > 0) {
				sourceProps.put("noteNum", myHelper.noteNum);
			}
			System.out.println("anantt revise   零部件 "+num+" and sourceVersion=="+ (String) ele.getValue("sourceVersion")+"  and  sourceProps.get==="+sourceProps.get("sourceVersion"));//anan
			try {
				transaction = new QMTransaction();
				transaction.begin();
				LogicBase lb = null;
				//CCBegin by liunan 2008-10-22
				//添加是修订还是升级小版本的标识，true表示修订，false表示升级小版本。
				boolean isReviseOrUp = true;
				//CCEnd by liunan 2008-10-22
				if (isgpart) {
					part = PublishHelper.getGPartInfoByNumber(num);
					receproversion = part.getVersionValue();

					//CCBegin by liunan 2009-01-21
					String partState = part.getLifeCycleState().toString();
					String partnote = part.getIterationNote();
					//CCEnd by liunan 2009-01-21

					part = (QMPartInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(part);
					attc = (DefaultAttributeContainer) part
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					int m = valueview.length;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], part);
							fbsourversion = ((StringValueIfc) value).getValue();
						}
					}
                                        //CCBegin by liunan 2008-09-02
                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                        //改用产品中版本服务采用的版本比较方法。
                                        //if(!(sourceVersion.compareTo(fbsourversion)>0))
                                        //System.out.println(sourceVersion+"==============process()的gpart============="+fbsourversion);
                                        //CCBegin by liunan 2009-01-21
                                        //源码如下：
                                        //if(!getPublishFlag(sourceVersion,fbsourversion))
                                        //{
                                        System.out.println(sourceVersion+"=="+fbsourversion+"=="+partState+"=="+qylifecyclestate);
                                        if(getPublishFlag(sourceVersion,fbsourversion))
                                        {
                                        }
                                        //CCBegin by liunan 2009-01-21
                                        //如果版本一样，则比较生命周期状态，汽研为生准，解放为非生准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        //CCBegin by liunan 2012-05-14 存在生产状态后，再把生准状态发布过来的问题。
                                        //else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&qylifecyclestate.equals("PREPARE"))
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&qylifecyclestate.equals("PREPARE"))
                                        //CCEnd by liunan 2012-05-14
                                        {
                                        }
                                        //CCBegin by liunan 2011-08-01
                                        //如果版本一样，则比较生命周期状态，汽研为前准，解放为非生准、非前准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&qylifecyclestate.equals("ADVANCEPREPARE"))
                                        {
                                        	System.out.println("new ADVANCEPREPARE 发布输出============="+num);
                                        }
                                        //CCEnd by liunan 2011-08-01
                                        //如果版本一样，则比较生命周期状态，汽研为生准，解放为生准，且解放的“版本注释”属性含有“前准”字样，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        //CCBegin by liunan 2011-04-22 前准判断由>改成>=
                                        else if(sourceVersion.equals(fbsourversion)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("前准")>=0&&qylifecyclestate.equals("PREPARE"))
                                        {
                                        }
                                        //CCBegin SS9
                                        //如果版本一样，则比较生命周期状态，汽研为试制，解放为非生准且非前准非生产，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&!partState.equals("MANUFACTURING")&&!partState.equals("SHIZHI")&&qylifecyclestate.indexOf("TEST")!=-1)
                                        {
                                        	System.out.println("anan  发试制件！");
                                        }
                                        //CCEnd SS9
                                        //CCBegin by liunan 2010-05-19
                                        //如果fbsourversion为""，说明该件在系统中存在，是解放用户手工创建的。直接升级
                                        else if(fbsourversion.equals(""))
                                        {
                                        	//CCBegin SS7
                                        	//isReviseOrUp = false;
                                        	if(sourceVersion.startsWith("A"))
                                        	{
                                        		isReviseOrUp = false;
                                        	}
                                        	//CCEnd SS7
                                        }
                                        //CCEnd by liunan 2010-05-19
                                        else
                                        {
                                        //CCEnd by liunan 2009-01-21
                                        //CCEnd by liunan 2008-09-02
						try {
							transaction.rollback();
						} catch (QMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.myHelper.partsCannotStore.add(part.getPartNumber());
						log("广义部件 " + num + " 不需要修订");
						userLog(serNum + "," + name + "," + num + ","
								+ "广义零部件" + "," + fbsourversion + ","
								+ sourceVersion + "," + receproversion + ","
								+ part.getVersionValue() + ",成功");
						//CCBegin by liunan 2011-04-22 对于已存在的，提示成功，但不往partVector中添加，防止结构多套。
						//myHelper.partVector.addElement(num);
						//CCEnd by liunan 2011-04-22
						result.successOne();
						myHelper.resucnum++;
						continue;
					}
					//CCBegin by liunan 2008-10-22
					//走到此处，表示该零部件需要发布到解放中。然后比较两个大版本，如果一样表示零部件是升级小版本，否则是修订操作。
					if(sourceVersion.indexOf(".")>0&&fbsourversion.indexOf(".")>0)
					{
					  if(sourceVersion.substring(0,sourceVersion.indexOf(".")).equals(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  {
						  isReviseOrUp = false;
					  }
					  //CCBegin SS10
					  //P.2变成P2.1，如果汽研发布源版本，是以解放大版本为开头字母，则表示可互换，进行小版本升级。
					  //if(sourceVersion.substring(0,sourceVersion.indexOf(".")).startsWith(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  if(matchResult(Pattern.compile("[a-z||A-Z]"),sourceVersion).equals(matchResult(Pattern.compile("[a-z||A-Z]"),fbsourversion)))
					  {
						  isReviseOrUp = false;
					  }
					  //CCEnd SS10
				  }
					//CCEnd by liunan 2008-10-22
					maxCompIdUsed = (String) ele.getValue("maxCompIdUsed");
					String logicBase = (String) ele.getValue("logicBase");
					
					//CCBegin by liunan 2010-01-20 防止logicbase为null。
					if(logicBase==null)
					{
						logicBase = "";
					}
					//CCEnd by liunan 2010-01-20
					
					//CCBegin by liunan 2009-11-06 添加对logicbase的保存。
					try 
					{
						String path = LOGICBASE_PATH+num+"-"+logDateFormat.format(new java.util.Date())+".xml";
						FileWriter writer = new FileWriter(path);
						writer.write(logicBase);
						writer.flush();
						writer.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					//CCEnd by liunan 2009-11-06
					System.out.println("GPart number =========="+num);
					if(logicBase==null)
						System.out.println("logicBase is null!!!");
					else
						System.out.println("logicBase.length()=========="+logicBase.length());
					lb = LogicBaseConversion.xmlToLogicBase(logicBase);
					if(lb==null)
						System.out.println("logicBase is null!!!");
					else
						System.out.println("logicBase is not null!!!");
				} else {
					ViewService viewService = (ViewService) PublishHelper
							.getEJBService("ViewService");
					//CCBegin SS5 SS11
					ViewObjectIfc view = viewService.getView("工程视图");
					//ViewObjectIfc view = viewService.getView("中心设计视图");
					//CCEnd SS5 SS11
					PartConfigSpecIfc partConfigSpecIfc = new PartConfigSpecInfo();
					partConfigSpecIfc = new PartConfigSpecInfo();
					partConfigSpecIfc.setStandardActive(true);
					partConfigSpecIfc.setBaselineActive(false);
					partConfigSpecIfc.setEffectivityActive(false);
					PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
					partStandardConfigSpec.setViewObjectIfc(view);
					partStandardConfigSpec.setLifeCycleState(null);
					partStandardConfigSpec.setWorkingIncluded(true);
					partConfigSpecIfc.setStandard(partStandardConfigSpec);

					QMPartMasterIfc partmaster = PublishHelper
							.getPartMasterByNumber(num);
					if (PublishHelper.VERBOSE)
						System.out.println("==============================>"
								+ num);
					Collection part1 = cservice.filteredIterationsOf(
							(MasteredIfc) partmaster,
							new PartConfigSpecAssistant(partConfigSpecIfc));
					Iterator ite = part1.iterator();
					//如果没有找到工程视图的版本则使用最新的制造视图版本
					if (ite.hasNext()) {
						Object[] obj = (Object[]) ite.next();

						part = (QMPartInfo) obj[0];
					} else {
						part = PublishHelper.getPartInfoByNumber(num);
					}
					
					//CCBegin SS3
					//System.out.println("part000======"+part.getPartName());
					if(part.getViewName().trim().equals("设计视图"))
					//if(part.getViewName().trim().equals("设计视图"))
					{
						System.out.println("变速箱零部件降视图：partNum == "+num+" and  version=="+part.getVersionValue());
						com.jf.util.changeVersion.queryPartInfo(num);
						part = PublishHelper.getPartInfoByNumber(num);
			
					}	
//					if(part.getViewName().trim().equals("工程视图"))
//						//if(part.getViewName().trim().equals("设计视图"))
//						{
//							com.jf.util.changeVersion.queryPartInfo(num);
//							part = PublishHelper.getPartInfoByNumber(num);
//						}	
					//CCEnd SS3
					
					receproversion = part.getVersionValue();

					//CCBegin by liunan 2009-01-21
					String partState = part.getLifeCycleState().toString();
					String partnote = part.getIterationNote();
					//CCEnd by liunan 2009-01-21

					part = (QMPartInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(part);
					attc = (DefaultAttributeContainer) part
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					int m = valueview.length;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], part);
							fbsourversion = ((StringValueIfc) value).getValue();
						}
					}
                                        //CCBegin by liunan 2008-09-02
                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                        //改用产品中版本服务采用的版本比较方法。
                                        //if(!(sourceVersion.compareTo(fbsourversion)>0))
                                        System.out.println(sourceVersion+"==============零部件("+num+")============= ("+fbsourversion+")");
                                        //System.out.println(sourceVersion+"=="+fbsourversion+"=="+partState+"=="+qylifecyclestate);
                                        //CCBegin by liunan 2009-01-21
                                        //源码如下：
                                        //if(!getPublishFlag(sourceVersion,fbsourversion))
                                        //{
                                        if(getPublishFlag(sourceVersion,fbsourversion))
                                        {
                                        }
                                        //CCBegin by liunan 2009-01-21
                                        //如果版本一样，则比较生命周期状态，汽研为生准，解放为非生准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        //CCBegin by liunan 2012-05-14 存在生产状态后，再把生准状态发布过来的问题。
                                        //else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&qylifecyclestate.equals("PREPARE"))
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&qylifecyclestate.equals("PREPARE"))
                                        //CCEnd by liunan 2012-05-14
                                        {
                                        }
                                        //CCBegin by liunan 2011-08-01
                                        //如果版本一样，则比较生命周期状态，汽研为生准，解放为非生准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&qylifecyclestate.equals("ADVANCEPREPARE"))
                                        {
                                        	System.out.println("new ADVANCEPREPARE 发布输出============="+num);
                                        }
                                        //CCEnd by liunan 2011-08-01
                                        //如果版本一样，则比较生命周期状态，汽研为生准，解放为生准，且解放的“版本注释”属性含有“前准”字样，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        //CCBegin by liunan 2011-04-22 前准判断由>改成>=
                                        else if(sourceVersion.equals(fbsourversion)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("前准")>=0&&qylifecyclestate.equals("PREPARE"))
                                        {
                                        }
                                        //CCBegin SS9
                                        //如果版本一样，则比较生命周期状态，汽研为试制，解放为非生准且非前准非生产，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&!partState.equals("MANUFACTURING")&&!partState.equals("SHIZHI")&&qylifecyclestate.indexOf("TEST")!=-1)
                                        {
                                        }
                                        //CCEnd SS9
                                        //CCBegin by liunan 2010-05-19
                                        //如果fbsourversion为""，说明该件在系统中存在，是解放用户手工创建的。直接升级
                                        else if(fbsourversion.equals(""))
                                        {
                                        	//CCBegin SS7
                                        	//isReviseOrUp = false;
                                        	if(sourceVersion.startsWith("A"))
                                        	{
                                        		isReviseOrUp = false;
                                        	}
                                        	//CCEnd SS7
                                        }
                                        //CCEnd by liunan 2010-05-19
                                        else
                                        {
                                        //CCEnd by liunan 2009-01-21
                                        //CCEnd by liunan 2008-09-02
						try {
							transaction.rollback();
						} catch (QMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.myHelper.partsCannotStore.add(part.getPartNumber());
						log("部件 " + num + " 不需要修订");
						userLog(serNum + "," + name + "," + num + ","
								+ "零部件" + "," + fbsourversion + "," + sourceVersion
								+ "," + receproversion + ","
								+ part.getVersionValue() + ",成功");
						//CCBegin by liunan 2011-04-22 对于已存在的，提示成功，但不往partVector中添加，防止结构多套。
						//myHelper.partVector.addElement(num);
						//CCEnd by liunan 2011-04-22
						result.successOne();
						myHelper.resucnum++;
						continue;
					}
					//CCBegin by liunan 2008-10-22
					//走到此处，表示该零部件需要发布到解放中。然后比较两个大版本，如果一样表示零部件是升级小版本，否则是修订操作。
					if(sourceVersion.indexOf(".")>0&&fbsourversion.indexOf(".")>0)
					{
					  if(sourceVersion.substring(0,sourceVersion.indexOf(".")).equals(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  {
						  isReviseOrUp = false;
					  }
					  //CCBegin SS10
					  //P.2变成P2.1，如果汽研发布源版本，是以解放大版本为开头字母，则表示可互换，进行小版本升级。
					  //if(sourceVersion.substring(0,sourceVersion.indexOf(".")).startsWith(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  if(matchResult(Pattern.compile("[a-z||A-Z]"),sourceVersion).equals(matchResult(Pattern.compile("[a-z||A-Z]"),fbsourversion)))
					  {
						  isReviseOrUp = false;
					  }
					  //CCEnd SS10
				  }
					//CCEnd by liunan 2008-10-22
				}
				String rightString=PublishHelper.getXRightString(part);//获取修订前的特殊授权字符串

				//CCBegin by liunan 2008-10-22
				//通过isReviseOrUp判断是对零部件进行修订还是小版本升级，
				//如果修订则用newVersion();否则升级小版本用newIteration()。
                                //System.out.println("begin version is ============="+part.getVersionValue());

        WorkInProgressService wService = (WorkInProgressService) PublishHelper
					.getEJBService("WorkInProgressService");
				SessionService sservice = (SessionService) PublishHelper
						.getEJBService("SessionService");
				//System.out.println("*********************revisedelegate********************=="+sservice.isAccessEnforced());
				
				//CCBegin by liunan 2011-06-21 如果fbsourversion为""，表示解放自己建的part，则不做检出或修订，直接做检入。
				if(fbsourversion.equals("")&&part.getOwner()!=null&&!part.getOwner().equals(""))
				{
					
				}
				//if(isReviseOrUp)
				else if(isReviseOrUp)
				//CCEnd by liunan 2011-06-21
				{
				    part = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						     .newVersion((VersionedIfc) part);
				}
				else if(isCheckoutAllowed(part))
				{
            FolderIfc folder = wService.getCheckoutFolder();
            wService.checkout(part, folder, "");
            part = (QMPartIfc) wService.workingCopyOf(part);
					  /*QMPartIfc iterationPartTemp = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						     .newIteration((IteratedIfc) part);
						part = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						     .supersede((IteratedIfc) part,(IteratedIfc) iterationPartTemp);
						*/
        }
                                //System.out.println("after version is ============="+part.getVersionValue());
				//CCEnd by liunan 2008-10-22
                                //这个是修订的方法，还有个newIteration方法是升级小版序。
                                //可以判断当前版本和原版本的大版本是否相同，一样则升级小版本，不一样则修订。
				String lifeCyID = PublishHelper.getLifeCyID(lifecycle);
				part.setLifeCycleTemplate(lifeCyID);

		    //CCBegin by liunan 2008-10-28
	    	//设置发布过来的零部件状态，而不是仅仅的“生准”。
	    	//源码如下：
				//part.setLifeCycleState(State.toState("PREPARING"));//设置状态，需要改成解放发布的状态了。
				//CCBegin by liunan 2009-12-23 修改状态表示
				part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
				//CCEnd by liunan 2009-12-23
				//CCEnd by liunan 2008-10-28
				//CCBegin SS1
				viewName = "中心设计视图";
				//CCEnd SS1
				if (viewName != null && PublishHelper.isHasView(viewName)) {
					part.setViewName(viewName);
					
				} else {
					part.setViewName(PublishLoadHelper.DEFAULT_VIEW);
			
				}
	
				if (source == null || source.trim().length() == 0) {
					part.setProducedBy(ProducedBy.getProducedByDefault());
				} else {
					source = myHelper.mapPro.getProperty("part.source."
							+ source);
					ProducedBy produce = ProducedBy.toProducedBy(source);
					if (produce == null) {
						part.setProducedBy(ProducedBy.getProducedByDefault());
					} else {
						part.setProducedBy(produce);
					}
				}
				// 设置类型
				if (partType == null || partType.trim().length() == 0) {
					part.setPartType(QMPartType.getQMPartTypeDefault());
				} else {

					partType = myHelper.mapPro.getProperty("part.parttype."
							+ partType);
					// partType = PublishHelper.strDecoding(partType,
					// "ISO8859-1");

					QMPartType type1 = QMPartType.toQMPartType(partType);

					if (type1 == null) {

						part.setPartType(QMPartType.getQMPartTypeDefault());
					} else {

						part.setPartType(type1);
					}
				}
				if (myHelper.user != null) {
					part.setIterationCreator(myHelper.user.getBsoID());
					part.setIterationModifier(myHelper.user.getBsoID());
					//CCBegin by liunan 2009-1-21
					//增加前准状态，不再需要标记。
					/*if(qylifecyclestate.equals("ADVANCEPREPARE"))
					{
					  part.setIterationNote("前准");
					}*/
					//CCEnd by liunan 2011-04-26
					//CCEnd by liunan 2009-1-21
					//CCBegin by liunan 2008-10-08
					//修改零部件发布时含有owner字段的问题。注释掉以下代码。
					//part.setOwner(myHelper.user.getBsoID());
					part.setOwner("");
					//CCEnd by liunan 2008-10-08
				}
				
				//CCBegin by liunan 2010-01-20 为零部件添加logicbase之前删除所有Contents内容。
				//因为零部件的Contents里有logicbase和附件，所以在添加logicbase之前将其清空。
				//然后在后续添加设置logicbase，并关联新的附件。
				//目前认为零部件的Contents内容只是logicbase或者附件。
				try 
				{
					ContentService cs = (ContentService) PublishHelper.getEJBService("ContentService");
					Vector v = cs.getContents(part);
					if(v!=null&&v.size()>0)
					{
						System.out.println(num+" 的Contents中含有 "+v.size()+" 个内容。");
						for (int i = 0; i < v.size(); i++)
						{
							ApplicationDataInfo application = (ApplicationDataInfo) v.get(i);
							cs.deleteApplicationData((ContentHolderIfc)part, application);
						}
				  }
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				//CCEnd by liunan 2010-01-20
				

				// 保存零部件
				part.setAttributeContainer(new DefaultAttributeContainer());
				
				//CCBegin by liunan 2011-06-21 如果fbsourversion为""，表示解放自己建的part，则不做检出或修订，直接做检入。
				if(fbsourversion.equals("")&&part.getOwner()!=null&&!part.getOwner().equals(""))
				{
            part = (QMPartIfc) wService.checkin(part, "");
            //CCBegin by liunan 2009-12-23 修改状态表示方式。
            part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
            //CCEnd by liunan 2009-12-23
            part = (QMPartIfc) ps.saveValueInfo(part);
				}
				//if(isReviseOrUp)
				else if(isReviseOrUp)
				//CCEnd by liunan 2011-06-21
				{
					  part = (QMPartInfo) ps.saveValueInfo(part);
			  }
				else
        {
            part = (QMPartIfc) wService.checkin(part, "");
            //CCBegin by liunan 2009-12-23 修改状态表示方式。
            part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
            //CCEnd by liunan 2009-12-23
            part = (QMPartIfc) ps.saveValueInfo(part);
        }
//

				//CCBegin SS8
				part.setIterationNote("");
				//CCEnd SS8
				
				//CCBegin by liunan 2011-10-27 修订也能改资料夹。
				//PublishHelper.assignFolder(part, location);
				String oldpath = part.getLocation();
				System.out.println("PartReviseDelegate "+num+" location:"+location);
				part = (QMPartInfo)PublishHelper.assignFolder(part, location);
				System.out.println("PartReviseDelegate  part location:"+part.getLocation());
				if(!oldpath.equals(part.getLocation()))
				{
					part = (QMPartIfc) ps.saveValueInfo(part);
				}
				//CCEnd by liunan 2011-10-27
				
				//CCBegin SS6
				changeVersion cha = new changeVersion();
				cha.updatePartView(part);
				// System.out.println("viewName8="+part.getViewName());
				//CCEnd SS6
				// 如果名称改变则重命名该零部件
				if (!part.getPartName().equals(name)) {
					QMPartMasterInfo master = (QMPartMasterInfo) part
							.getMaster();
					master.setPartName(name);
					partSer.renamePartMaster(master, false);
				}

				Collection links = PublishHelper.getPartUsageLinks(part);
				for (Iterator oldlinks = links.iterator(); oldlinks.hasNext();) {
					PartUsageLinkInfo link = (PartUsageLinkInfo) oldlinks
							.next();
					ps.deleteValueInfo(link);
				}
				links = null;
				part = (QMPartInfo) updateIBA((IBAHolderIfc) part, sourceProps,
						(Vector) myPartIBAMap.get(num));
				part = deleteOldStruct(part);
				// 删除文档关联
				part = deleteOldDocAssociate(part);
				//增加借阅单权限处理
				
				//CCBegin SS1				
				try 
				{
					ContentService cs = (ContentService) PublishHelper.getEJBService("ContentService");
					Vector v = cs.getContents(part);
					if(v!=null&&v.size()>0)
					{
						System.out.println(num+" 的Contents中含有 "+v.size()+" 个内容。");
						for (int i = 0; i < v.size(); i++)
						{
							ApplicationDataInfo application = (ApplicationDataInfo) v.get(i);
							//添加pdf的判断。
							if(application.getFileName().endsWith(".pdf")||application.getFileName().endsWith(".edz"))
							{
								cs.deleteApplicationData((ContentHolderIfc)part, application);
							}
						}
				  }
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				//CCEnd SS1
				
				
				if(rightString!=null&&rightString.length()>0)
				{
					AdHocAcl acl = AdHocAcl.newAdHocAcl();
					acl.setEntrySet(new AclEntrySet(rightString));
					part.setAdHocAcl(acl);
					//CCBegin by liunan 2010-01-31 修改updateBso更新时不再修改时间戳。
					//由原来的两个参数改成三个参数的方法
					part=(QMPartIfc) ((PersistService) PublishHelper
							.getEJBService("PersistService")).updateBso(part, false, false)
							.getValueInfo();
					//CCEnd by liunan 2010-01-31			
					myHelper.partNeedAcl.add(part);
				}
				
				
				if (isgpart) {
					try
					{
					System.out.println(num+" 是广义部件，即将保存logicbase！！！");
					part = (QMPartIfc)ps.refreshInfo(part);
					((GenericPartInfo) part).setMaxCompIdUsed(Long.valueOf(
							maxCompIdUsed).longValue());

					if (lb != null) {
						System.out.println(num+" 的logicbase不为空，可以保存！！！");
						((GenericPartInfo) part).setLogicBase(lb);
						ps.saveValueInfo(part);
						System.out.println(num+" 的logicbase保存完毕！！！");
					}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}

				}
				
				transaction.commit();
				myHelper.partVector.addElement(num);
				result.successOne();
				result.addSuccessMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + type + ",成功");
				//CCBegin by liunan 2008-12-11 输入信息时区分修订还是小版本升级。
				//CCBegin by liunan 2009-12-07 修改结果统计的内容，增加附件的信息。
				String temp = "";
				if(isReviseOrUp)
				{
					//temp = isHasCont.trim().equals("true") ? "有附件" : "成功" ;
					temp = "成功";
				}
				else
				{
					//temp = isHasCont.trim().equals("true") ? "小版本升级（有附件）" : "小版本升级" ;
					temp = "小版本升级" ;
				}
				if (isgpart) {
					log("修订广义部件 " + num + " 成功");
                                        if(isReviseOrUp)
					  userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "广义零部件" + "," + fbsourversion + ","
							+ sourceVersion + "," + receproversion + ","
							+ part.getVersionValue() + "," + temp);
							//+ part.getVersionValue() + ",成功");
                                        else
                                          userLog(serNum + "," + part.getPartName() + "," + num + ","
                                                        + "广义零部件" + "," + fbsourversion + ","
                                                        + sourceVersion + "," + receproversion + ","
                                                        + part.getVersionValue() + "," + temp);
                                                        //+ part.getVersionValue() + ",小版本升级");
				} else {
					log("修订零部件 " + num + " 成功");
                                        if(isReviseOrUp)
					  userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "零部件" + "," + fbsourversion + "," + sourceVersion
							+ "," + receproversion + ","
							+ part.getVersionValue() + "," + temp);
							//+ part.getVersionValue() + ",成功");
                                        else
                                          userLog(serNum + "," + part.getPartName() + "," + num + ","
                                                        + "零部件" + "," + fbsourversion + "," + sourceVersion
                                                        + "," + receproversion + ","
                                                        + part.getVersionValue() + "," + temp);
                                                        //+ part.getVersionValue() + ",小版本升级");
				}
				//CCEnd by liunan 2009-12-07
				//CCEnd by liunan 2008-12-11
				myHelper.resucnum++;
			} catch (QMException ex) {
				ex.printStackTrace();
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// 将未能成功修订的零部件记录到错误日志中
				result.failureOne();
				String msg = "at save Part (num=" + num + ") ClientMessage ERROR:"
						+ ex.getClientMessage();
				result.addErroMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + partType + ",失败");
				errorLog(msg);
				// ex.printStackTrace();
				errorLog(ex);
				
				//CCBegin SS4
				boolean suflag = false;
				//CCEnd SS4
				
				//CCBegin by liunan 2009-10-19 屏蔽“锁定对象出错：不能锁定对象”异常
				//该异常是由于同时发布的几个变更中存在相同需要发布的结构，其中一个创建好了，
				//另一个则无法修改时报出的异常，实际已经创建成功，现修改让《基础数据发布结果统计.html》
				//中不再显示此异常，而是显示成功。
				//即 出现“锁定”异常的那行记录当做成功的来处理。
				if(msg.indexOf("锁定对象出错：不能锁定对象")>0)
				{
					myHelper.resucnum++;
				}
				//CCBegin by liunan 2011-10-27 新增屏蔽信息“对象已经修订过或不是最新版本”
				if(msg.indexOf("对象已经修订过或不是最新版本")>0)
				{
					myHelper.resucnum++;
					//CCBegin SS4
					errorLog("num= "+num+" 不需要修订 ，因为对象已经修订过或不是最新版本！");
					suflag = true;
					//CCEnd SS4
				}
				//CCEnd by liunan 2011-10-27
				//CCEnd by liunan 2009-10-19
				
				//CCBegin SS2
				if(msg.indexOf("不是唯一的")>0)
				{
					myHelper.resucnum++;
					//CCBegin SS4
					errorLog("num= "+num+" 不需要修订 ，因为 不是唯一的！");
					suflag = true;
					//CCEnd SS4
				}
				//CCEnd SS2
				
				//CCBegin by liunan 2011-06-21 如果异常是“在检入该对象之前必须将其检出。”，则另提示。
				String msg1 = ex.getLocalizedMessage();
				if(msg1!=null&&msg1.startsWith("在检入该对象之前必须将其检出"))
				{
					msg1 = "在个人资料夹发布失败";
				}
				if (isgpart) {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "广义零部件" + "," + fbsourversion + ","
							+ sourceVersion + "," + receproversion + "," + " "
							//+ ",失败" + "," + ex.getClientMessage());
							//CCBegin SS4
							//+ ",失败" + "," + msg1);
							+ (suflag?",成功":",失败") + "," + msg1);
							//CCEnd SS4
				} else {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "零部件" + "," + fbsourversion + "," + sourceVersion
							//CCBegin SS4
							//+ "," + receproversion + "," + " " + ",失败" + ","
							+ "," + receproversion + "," + " " + (suflag?",成功":",失败") + ","
							//CCEnd SS4
							//+ ex.getClientMessage());
							+ msg1);
				}
				//CCEnd by liunan 2011-06-21
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// 将未能成功修订的零部件记录到错误日志中
				result.failureOne();
				String msg = "at save Part (num=" + num + ") LocalizedMessage ERROR:"
						+ ex.getLocalizedMessage();
				result.addErroMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + partType + ",失败");
				errorLog(msg);
				// ex.printStackTrace();
				errorLog(ex);				
				
				//CCBegin SS4
				boolean suflag = false;
				//CCEnd SS4
				
				//CCBegin SS2
				if(msg.indexOf("不是唯一的")>0)
				{
					myHelper.resucnum++;
					//CCBegin SS4
					errorLog("num= "+num+" 不需要修订 ，因为 不是唯一的！");
					suflag = true;
					//CCEnd SS4
				}
				//CCEnd SS2
				
				
				//CCBegin by liunan 2011-06-21 如果异常是“在检入该对象之前必须将其检出。”，则另提示。
				String msg1 = ex.getLocalizedMessage();
				if(msg1!=null&&msg1.startsWith("在检入该对象之前必须将其检出"))
				{
					msg1 = "在个人资料夹发布失败";
				}
				if (isgpart) {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "广义零部件" + "," + fbsourversion + ","
							+ sourceVersion + "," + receproversion + "," + " "
							//+ ",失败" + "," + ex.getLocalizedMessage());
							//CCBegin SS4
							//+ ",失败" + "," + msg1);
							+ (suflag?",成功":",失败") + "," + msg1);
							//CCEnd SS4
				} else {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "零部件" + "," + fbsourversion + "," + sourceVersion
							//CCBegin SS4
							//+ "," + receproversion + "," + " " + ",失败" + ","
							+ "," + receproversion + "," + " " + (suflag?",成功":",失败") + ","
							//CCEnd SS4
							//+ ex.getLocalizedMessage());
							+ msg1);
				}
				//CCEnd by liunan 2011-06-21
			}
		} // end while
		return result;

	}

	/**
	 * 更新IBA值,但不做保存
	 *
	 * @param holder
	 * @param url
	 * @param group
	 * @return
	 * @throws java.lang.Exception
	 */
	private IBAHolderIfc updateIBA(IBAHolderIfc holder, Properties props,
			Vector vector) throws QMException {
		if (holder == null) {
			return holder;
		}
		// 事物特性持有者。
		holder = ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).refreshAttributeContainer(
				holder, null, null, null);
		// 属性容器。
		DefaultAttributeContainer container = (DefaultAttributeContainer) holder
				.getAttributeContainer();
		container.setConstraintGroups(new Vector());
		AbstractValueView aValueView[] = container.getAttributeValues();
		// 删除容器中原有的属性值。
		for (int i = 0; aValueView != null && i < aValueView.length; i++) {
			if (aValueView[i] instanceof ReferenceValueDefaultView) {
				ReferenceValueDefaultView referenceValue = (ReferenceValueDefaultView) aValueView[i];
				if (referenceValue.getLiteIBAReferenceable() != null
						&& referenceValue.getLiteIBAReferenceable()
								.getReferenceID().indexOf("Ranking") == -1) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else if (aValueView[i] instanceof IntegerValueDefaultView) {
				IntegerValueDefaultView integerValue = (IntegerValueDefaultView) aValueView[i];
				if (integerValue.getReferenceValueDefaultView() == null) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else {
				container.deleteAttributeValue(aValueView[i]);
			}
		}
		// 添加发布数据属性值。
		if (myHelper.saveSourceInfoByIBA) {
			holder = PublishHelper.setPublishSourceInfoByIBA(holder, props);
			container = (DefaultAttributeContainer) holder
					.getAttributeContainer();
		}
		// 添加原有属性值。
		if (vector != null) {
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) instanceof BooleanValueDefaultView) {
					BooleanValueDefaultView booleanValue = (BooleanValueDefaultView) vector
							.get(i);
					BooleanValueDefaultView booleanValue1 = new BooleanValueDefaultView(
							(BooleanDefView) booleanValue.getDefinition(),
							booleanValue.isValue());
					if (booleanValue.getReferenceValueDefaultView() != null) {
						booleanValue1.setReferenceValueDefaultView(booleanValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(booleanValue1);
				} else if (vector.get(i) instanceof FloatValueDefaultView) {
					FloatValueDefaultView floatValue = (FloatValueDefaultView) vector
							.get(i);
					FloatValueDefaultView floatValue1 = new FloatValueDefaultView(
							(FloatDefView) floatValue.getDefinition(),
							floatValue.getValue(), floatValue.getPrecision());
					if (floatValue.getReferenceValueDefaultView() != null) {
						floatValue1.setReferenceValueDefaultView(floatValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(floatValue1);
				} else if (vector.get(i) instanceof IntegerValueDefaultView) {
					IntegerValueDefaultView integerValue = (IntegerValueDefaultView) vector
							.get(i);
					IntegerValueDefaultView integerValue1 = new IntegerValueDefaultView(
							(IntegerDefView) integerValue.getDefinition(),
							integerValue.getValue());
					if (integerValue.getReferenceValueDefaultView() == null) {
						container.addAttributeValue(integerValue1);
					}
				} else if (vector.get(i) instanceof RatioValueDefaultView) {
					RatioValueDefaultView ratioValue = (RatioValueDefaultView) vector
							.get(i);
					RatioValueDefaultView ratioValue1 = new RatioValueDefaultView(
							(RatioDefView) ratioValue.getDefinition(),
							ratioValue.getValue(), ratioValue.getDenominator());
					if (ratioValue.getReferenceValueDefaultView() != null) {
						ratioValue1.setReferenceValueDefaultView(ratioValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(ratioValue1);
				} else if (vector.get(i) instanceof ReferenceValueDefaultView) {
					ReferenceValueDefaultView referenceValue1 = (ReferenceValueDefaultView) vector
							.get(i);
					if (referenceValue1.getLiteIBAReferenceable() != null
							&& referenceValue1.getLiteIBAReferenceable()
									.getReferenceID().indexOf("Ranking") == -1) {
						container.setConstraintParameter("CSM");
						container.addAttributeValue(referenceValue1);
					}
				} else if (vector.get(i) instanceof StringValueDefaultView) {
					StringValueDefaultView stringValue = (StringValueDefaultView) vector
							.get(i);
					StringValueDefaultView stringValue1 = new StringValueDefaultView(
							(StringDefView) stringValue.getDefinition(),
							stringValue.getValue());
					if (stringValue.getReferenceValueDefaultView() != null) {
						stringValue1.setReferenceValueDefaultView(stringValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(stringValue1);
				} else if (vector.get(i) instanceof TimestampValueDefaultView) {
					TimestampValueDefaultView timestampValue = (TimestampValueDefaultView) vector
							.get(i);
					TimestampValueDefaultView timestampValue1 = new TimestampValueDefaultView(
							(TimestampDefView) timestampValue.getDefinition(),
							timestampValue.getValue());
					if (timestampValue.getReferenceValueDefaultView() != null) {
						timestampValue1
								.setReferenceValueDefaultView(timestampValue
										.getReferenceValueDefaultView());
					}
					container.addAttributeValue(timestampValue1);
				} else if (vector.get(i) instanceof UnitValueDefaultView) {
					UnitValueDefaultView unitValue = (UnitValueDefaultView) vector
							.get(i);
					UnitValueDefaultView unitValue1 = new UnitValueDefaultView(
							(UnitDefView) unitValue.getDefinition(), unitValue
									.getValue(), unitValue.getPrecision());
					if (unitValue.getReferenceValueDefaultView() != null) {
						unitValue1.setReferenceValueDefaultView(unitValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(unitValue1);
				} else if (vector.get(i) instanceof URLValueDefaultView) {
					URLValueDefaultView urlValue = (URLValueDefaultView) vector
							.get(i);
					URLValueDefaultView urlValue1 = new URLValueDefaultView(
							(URLDefView) urlValue.getDefinition(), urlValue
									.getValue(), urlValue.getDescription());
					if (urlValue.getReferenceValueDefaultView() != null) {
						urlValue1.setReferenceValueDefaultView(urlValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(urlValue1);
				}
			}
		}

		holder.setAttributeContainer(container);
		container = (DefaultAttributeContainer) ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).updateAttributeContainer(
				holder, container != null ? container.getConstraintParameter()
						: null, null, null);

		holder.setAttributeContainer(container);
		return holder;
	}

	private QMPartIfc deleteOldStruct(QMPartIfc parent) throws Exception {

		Iterator result = null;
		if (parent instanceof GenericPartInfo) {
			result = ((PersistService) PublishHelper
					.getEJBService("PersistService")).navigateValueInfo(parent,
					"usedBy", "GenericPartUsageLink", false).iterator();
		} else {
			result = ((StandardPartService) PublishHelper
					.getEJBService("StandardPartService")).getUsesPartMasters(
					parent).iterator();
		}
		PartUsageLinkIfc temp;

		while (result.hasNext()) {
			temp = (PartUsageLinkIfc) result.next();
			// PersistenceHelper.manager.delete(temp);
			((PersistService) PublishHelper.getEJBService("PersistService"))
					.removeValueInfo(temp);
		}
		return parent;
	}

	private QMPartIfc deleteOldDocAssociate(QMPartIfc part) throws Exception {
		PersistService pservice = (PersistService) PublishHelper
				.getEJBService("PersistService");

		// 删除零部件的参考关联
		Vector result = (Vector) pservice.navigateValueInfo(part,
				"referencedBy", "PartReferenceLink", false);
		PartReferenceLinkIfc refLinkT;
		for (Iterator ite = result.iterator(); ite.hasNext();) {
			refLinkT = (PartReferenceLinkIfc) ite.next();
			pservice.removeValueInfo(refLinkT);
		}

		// 删除零部件的描述关联
		result = (Vector) pservice.navigateValueInfo(part, "describes",
				"PartDescribeLink", false);
		Enumeration eum = result.elements();
		PartDescribeLinkIfc descLinkT;
		while (eum.hasMoreElements()) {
			descLinkT = (PartDescribeLinkIfc) eum.nextElement();
			pservice.removeValueInfo(descLinkT);
		}

		// 删除零部件与epm的构造规则
		QMQuery branchQuery = new QMQuery("EPMBuildLinksRule");
		QueryCondition condition = new QueryCondition("rightBsoID", "=", part
				.getBranchID());
		branchQuery.addCondition(condition);
		Collection queryresult = pservice.findValueInfo(branchQuery);
		for (Iterator iter = queryresult.iterator(); iter.hasNext();) {
			EPMBuildRuleIfc epmbuildrule = (EPMBuildRuleIfc) iter.next();
			pservice.removeValueInfo(epmbuildrule);
		}
		// 删除零部件与epm的历史规则
		branchQuery = new QMQuery("EPMBuildHistory");
		condition = new QueryCondition("rightBsoID", "=", part.getBsoID());
		branchQuery.addCondition(condition);
		queryresult = pservice.findValueInfo(branchQuery);
		for (Iterator iter = queryresult.iterator(); iter.hasNext();) {
			EPMBuildHistoryIfc link = (EPMBuildHistoryIfc) iter.next();
			pservice.removeValueInfo(link);
		}

		return part;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

        /**
         * 比较两个版本的大小，决定是否发布此对象。
         * @param s1 String 汽研最新版本。
         * @param s2 String 解放pdm中记录的该对象对应汽研的版本。
         * @return boolean 需要发布时返回true，否则返回false。
         * @author liunan 2008-10-04
         */
        private boolean getPublishFlag(String s1, String s2)
        {
        	//CCBegin by liunan 2011-03-15 如果解放中没有源版本，说明是解放自己手工创建的，这样的返回false，稍后按更新检出检入处理。
        	if(s2.equals(""))
        	{
        		return false;
        	}
        	//CCEnd by liunan 2011-03-15
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

    /**
     * 判断是否允许被检出。
     * @param workable 被检出对象。
     * @return 标识。
     * @throws LockException
     * @throws QMException
     * @author liunan 2008-11-17
     */
    private static boolean isCheckoutAllowed(WorkableIfc workable)
            throws LockException, QMException
    {
        boolean flag = false;
        //获得文件夹服务
        FolderService folderService = (FolderService) PublishHelper
					.getEJBService("FolderService");
        if(!WorkInProgressHelper.isWorkingCopy(workable))
        {
            flag = !WorkInProgressHelper.isCheckedOut(workable)
                    && !LockHelper.isLocked(workable)
                    && !folderService.inPersonalFolder(workable);
        }
        return flag;
    }
    
    //CCBegin SS10
    public static String matchResult(Pattern p,String str)
    {
    	StringBuilder sb = new StringBuilder();
    	Matcher m = p.matcher(str);
    	while (m.find())
    	{
    		for (int i = 0; i <= m.groupCount(); i++)
    		{
    			sb.append(m.group());
    		}
    	}
    	return sb.toString();
    } 
    //CCEnd SS10
}
