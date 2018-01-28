/**
 * SS1 添加文件服务器传输逻辑 jiahx 2013-12-10
 * SS2 发布试制任务单需要对描述中的零部件清单进行处理，生成试制清单和给解放试制的试制件设置试制任务单编号属性。 liunan 2017-6-27
 * SS3 如果是 试制任务单，则保存到试制任务单资料夹中。 liunan 2017-9-7
 */
package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.exception.ContentException;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;

//CCBegin by liunan 2008-08-07
import java.util.Vector;
//CCEnd by liunan 2008-08-07
//CCBegin by liunan 2008-11-06
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.iba.value.model.StringValueInfo;
//CCEnd by liunan 2008-11-06

public class ChangeOrderCreateDelegate extends AbstractStoreDelegate {

	protected Group myChangeOrders = null;
	
	//CCBegin SS1
	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
	//CCEnd SS1

	public ChangeOrderCreateDelegate(Group orders, PublishLoadHelper helper) {
		super();
		this.myChangeOrders = orders;
		this.myHelper = helper;
	}

	public ResultReport process() {
		//userLog("##ChangeOrder");
		ResultReport result = new ResultReport();
		if (this.myChangeOrders == null
				|| this.myChangeOrders.getElementCount() == 0) {
			log("没有需要创建的技术中心变更单");
			return result;
		}
		StandardDocService ser = null;
		ContentService cser = null;
		try {
			ser = (StandardDocService) PublishHelper
					.getEJBService("StandardDocService");
			cser = (ContentService) PublishHelper
					.getEJBService("ContentService");
		} catch (QMException ex) {
			result.setFailureCount(this.myChangeOrders.getElementCount());
			String msg = "ERROR:" + ex.getLocalizedMessage();

			errorLog(msg);
			errorLog(ex);
			return result;
			// ex.printStackTrace();
		} catch (Exception ex) {
			result.setFailureCount(this.myChangeOrders.getElementCount());
			String msg = "ERROR:" + ex.getLocalizedMessage();

			errorLog(msg);
			errorLog(ex);
			return result;
			// ex.printStackTrace();
		}

		Enumeration enumeration = this.myChangeOrders.getElements();
		Element ele = null;
		String num = null;
		String name = null;
		String description = null;
		String docType = null;
		String department = null;
		String location = null;
		String version = null;
		String secret = null;
		String creater = null;
		String modifier = null;
		String sourceVersion = "";
		//CCBegin SS2
		String objType = null;
		//CCEnd SS2
		// 如果要使用扩展属性记录文档的发布源相关信息，则先初始化扩展属性的定义
		boolean affixOk = false;
		DocInfo docInfo = null;
		QMTransaction transaction = null;
		int sernum = 0;
		log("创建技术中心变更单：");

		while (enumeration.hasMoreElements()) {
			sernum++;
			try {
				transaction = new QMTransaction();
				transaction.begin();
			} catch (QMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ele = (Element) enumeration.nextElement();
			num = ((String) ele.getValue("num")).trim();
			name = (String) ele.getValue("name");
			description = (String) ele.getValue("desc");
			//CCBegin SS2
			objType = (String) ele.getValue("type");
			//CCEnd SS2
			if (name == null || name.trim().length() == 0) {
				result.failureOne();
				String msg = "at save changeorder (num=" + num + " name="
						+ name + ") ERROR: 变更单名称为空";

				errorLog(msg);
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save changeorder (num=" + num + " name="
						+ name + ") ERROR: 变更单编号为空";

				errorLog(msg);
				continue;
			}

			// 判断要创建的文档是否存在
			DocMasterInfo docmaster = null;
			try {
				docmaster = PublishHelper.getDocMasterByNumber(num);
			} catch (QMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (docmaster != null) {
				errorLog("要创建的技术中心变更单"+num+"已存在");
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			myHelper.changeorderzong++;
			// docType = (String) ele.getValue("docType");
			// 通过映射表（属性文件）取得文档类别的映射值
			docType = myHelper.mapPro.getProperty("doc.docType.changeorder",
					"通知\\技术中心变更单");
			department = "技术中心";
			location = myHelper.mapPro.getProperty("doc.location.changeorder",
					"\\Root\\变更单\\技术中心变更单");
			
			//CCBegin SS3
			if(objType!=null&&objType.equals("试制任务单"))
			{
				location = "\\Root\\变更单\\试制任务单";
			}
			//CCEnd SS3
			location = PublishHelper.normalLocation(location);

			String createDept = "";
			if (department != null) {
				createDept = department;
			}
			String docCfBsoID = null;
			String keyWord = "";
			String project = "";
			String lifecycleTemplate = myHelper.mapPro.getProperty(
					"jfpublish.doc.lifecycle", null);
			if (lifecycleTemplate == null
					|| lifecycleTemplate.trim().equals("")) {
				lifecycleTemplate = PublishLoadHelper.DEFAULT_LIFECYCLE;
			}
			try {
				docCfBsoID = PublishHelper.getDocCf(docType);

				DocFormData formData = new DocFormData();

				formData.setDocumentAttribute("docName", name);
				// 设置文档编号
				formData.setDocumentAttribute("docNum", num);
				// 设置内容简述
				if (description.length() < 2000) {
					formData.setDocumentAttribute("contDesc", description);
				} else {
					formData.setDocumentAttribute("contDesc", "TOOLONG");
				}
				// 设置创建单位
				formData.setDocumentAttribute("createDept", createDept);
				// 设置文档类别
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				// 设置项目组
				formData.setDocumentAttribute("projTeam", project);
				// 设置资料夹
				formData.setDocumentAttribute("folder", location);
				// 设置生命周期
				formData.setDocumentAttribute("lifecycleTemplate",
						lifecycleTemplate);

				UsersService userservice = (UsersService) PublishHelper
						.getEJBService("UsersService");
				UserInfo user = userservice.getUserValueInfo("rdc");// yanqi-20070709,用户直接设置为技术中心
				if (user != null) {
					formData.setDocumentAttribute("iterationCreator", user
							.getBsoID());
					formData.setDocumentAttribute("iterationModifier", user
							.getBsoID());
					formData.setDocumentAttribute("aclOwner", user.getBsoID());
					formData.setDocumentAttribute("creator", user.getBsoID());
				}
				// 设置关键词
				formData.setDocumentAttribute("keyWord", keyWord);
				formData
						.setDocumentAttribute("dependencyList", new ArrayList());
				formData.setDocumentAttribute("usageList", new ArrayList());
				formData.setDocumentAttribute("docAttrValueList", null);
				HashMap fileDescHashMap = new HashMap();
				formData.setDocumentAttribute("fileDescriptList",
						fileDescHashMap);

        //CCBegin by liunan 2008-08-07
        //现版本doc服务中已经存在createDoc方法，但返回的是vector，中间处理过程一致，
        //此处直接调用最新版本服务，然后得到docInfo。
        //原代码如下
				//docInfo = (DocInfo) ser.createDoc(formData);
				Vector docvec = (Vector) ser.createDoc(formData);
				docInfo = (DocInfo)docvec.elementAt(0);
				//CCBegin by liunan 2008-08-07

				PersistService ps = (PersistService) PublishHelper
						.getEJBService("PersistService");
				docInfo = (DocInfo) ps.refreshInfo(docInfo);
				if (!(description.length() < 2000)) {
					byte fileContent[] = description.getBytes();
					//CCBegin SS1
					if(fileVaultUsed)
					{
						ContentClientHelper helper = new ContentClientHelper();
						ApplicationDataInfo appDataInfo = helper.requestUpload(fileContent);
						appDataInfo.setFileName("description.txt");
						appDataInfo.setFileSize(fileContent.length);
						appDataInfo = (ApplicationDataInfo) cser
						.uploadPrimaryContent(docInfo, appDataInfo);
					}
					else
					{
						ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
						appDataInfo.setFileName("description.txt");
						appDataInfo.setFileSize(fileContent.length);
						appDataInfo = (ApplicationDataInfo) cser
						.uploadPrimaryContent(docInfo, appDataInfo);
						StreamUtil.writeData(appDataInfo.getStreamDataID(),
						fileContent);
					}
					//CCEnd SS1
				}
				
				//CCBegin SS2
				if(objType!=null&&objType.equals("试制任务单"))
				{
					//调用方法，创建试制清单excel文件，并给解放试制的试制状态零部件设置更改通知书属性为试制任务单号
					PublishHelper.createShiZhiRenWu(docInfo,description);
				}
				//CCEnd SS2
				
				transaction.commit();
				myHelper.changeorderzong++;
				log("创建技术中心变更单 " + num + " 成功");
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + docInfo.getVersionValue() + ",成功");
				result.successOne();
				resetPartState(docInfo.getBsoID(), num);//20080410添加，设置已取消的零部件状态
			} catch (QMException ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// this.myHelper.logErrorDoc(num);// 将未能成功创建的描述文档记录到错误日志中
				result.failureOne();
				String msg = "at save changeorder (name=" + name + " num="
						+ num + ") ERROR:" + ex.getClientMessage();

				errorLog(msg);
				errorLog(ex);
				ex.printStackTrace();
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + " " + ",失败" + "," + ex.getClientMessage());
				// ex.printStackTrace();
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// this.myHelper.logErrorDoc(num);// 将未能成功创建的描述文档记录到错误日志中
				result.failureOne();
				String msg = "at save changeorder (name=" + name + " num="
						+ num + ") ERROR:" + ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + " " + ",失败" + "," + ex.getLocalizedMessage());
				// ex.printStackTrace();
			}
		}
		return result;
	}

  /**
   * 修改设置状态的方法，还需要设置“采用通知书号”属性，所以多传一个编号属性。
   * @author liunan 2008-11-06
   */
	private void resetPartState(String docID, String num) throws QMException {
		if(docID==null||docID.length()==0)
		{
			return;
		}
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		Collection coll=PublishHelper.getPartBeforechange(docID);
		if(coll==null||coll.size()==0)
		{
			return;
		}
		Iterator iter1=coll.iterator();
		while(iter1.hasNext())
		{
			String[] partInfo=(String[]) iter1.next();
			if(partInfo[4].equals("CANCELLED"))
			{
				QMPartIfc part=(QMPartIfc) ps.refreshInfo(partInfo[0]);
				if(part==null)
				{
					continue;
				}
				LifeCycleHelper.resetState(part,"CANCELLED");
				//CCBegin by liunan 2008-11-06
				//修改零部件iba属性“采用通知书号”
				saveCaiYongIba(partInfo[0], num);
				//CCEnd by liunan 2008-11-06
				EPMDocumentIfc epm=PublishHelper.getLinkedEPMDocument(part);
				if(epm!=null)
				{
					LifeCycleHelper.resetState(epm,"CANCELLED");
				}
				Collection descDocs=PublishHelper.getDescribeDocs(part);
				if(descDocs==null||descDocs.size()==0)
				{
					continue;
				}
				Iterator iter2=descDocs.iterator();
				while(iter2.hasNext())
				{
					DocIfc doc=(DocIfc) iter2.next();
					LifeCycleHelper.resetState(doc,"CANCELLED");
				}
			}
		}
	}

	/**
	 * 废弃取消的零部件也要添加采用通知书属性，用于艺准的零部件添加。
	 * @author liunan 2008-11-06
	 */
	private void saveCaiYongIba(String partid, String num) throws QMException
	{
		try
		{
      PersistService  ps = (PersistService) EJBServiceHelper.getPersistService();
      QMQuery query = new QMQuery("StringValue");
      int j = query.appendBso("StringDefinition", false);
      QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=",partid);
      query.addCondition(qc);
      query.addAND();
      QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
      query.addCondition(0, j, qc1);
      query.addAND();
      //CCBegin by liunan 2011-04-07 根据最新需求，“采用通知书号” 改名为 “更改通知书号”，所以把搜索条件改成name
      //QueryCondition qc2 = new QueryCondition("displayName", " = ", "采用通知书号");
      QueryCondition qc2 = new QueryCondition("name", " = ", "AdoptionNumber");
      //CCEnd by liunan 2011-04-07
      query.addCondition(j, qc2);
      Collection col = ps.findValueInfo(query, false);
      if (col.size() > 0)
      {
        StringValueInfo svi = (StringValueInfo) col.iterator().next();
        svi.setValue(num);
        ps.saveValueInfo(svi);
      }
      else
      {
      	QMQuery query1 = new QMQuery("StringValue");
        int jj = query1.appendBso("StringDefinition", false);
        QueryCondition qc3 = new QueryCondition("iBAHolderBsoID", "=",partid);
        query1.addCondition(qc3);
        query1.addAND();
        QueryCondition qc4 = new QueryCondition("definitionBsoID", "bsoID");
        query1.addCondition(0, jj, qc4);
        query1.addAND();
        QueryCondition qc5 = new QueryCondition("displayName", " = ", "采用、更改通知书号");
        query1.addCondition(jj, qc5);
        Collection col1 = ps.findValueInfo(query1, false);
        if (col1.size() > 0)
        {
          StringValueInfo svi1 = (StringValueInfo) col1.iterator().next();
          svi1.setValue(num);
          ps.saveValueInfo(svi1);
       }
      }
    }
    catch (QMException e) {
      e.printStackTrace();
    }

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
