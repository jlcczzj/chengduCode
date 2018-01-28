package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.content.ejb.service.ContentService;
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
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.jfpublish.receive.LifeCycleHelper;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.jfpublish.receive.PublishLoadHelper;
import com.faw_qm.jfpublish.receive.ResultReport;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartReferenceLinkInfo;

public class RenWuDanCreateDelegate extends AbstractDocStoreDelegate {

	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
            
	protected Group myChangeOrders = null;

	public RenWuDanCreateDelegate(Group orders, PublishLoadHelper helper) {
		super();
		this.myChangeOrders = orders;
		this.myHelper = helper;
	}

	public ResultReport process() {
		//userLog("##ChangeOrder");
		ResultReport result = new ResultReport();
		if (this.myChangeOrders == null
				|| this.myChangeOrders.getElementCount() == 0) {
			System.out.println("没有需要创建的任务单文档");
			log("没有需要创建的任务单文档");
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
			String msg = "ERROR:" + ex.getLocalizedMessage();
			System.out.println(msg);
			return result;
			// ex.printStackTrace();
		} catch (Exception ex) {
			String msg = "ERROR:" + ex.getLocalizedMessage();
			System.out.println(msg);
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
		String username="";
		
		// 如果要使用扩展属性记录文档的发布源相关信息，则先初始化扩展属性的定义
		boolean affixOk = false;
		DocInfo docInfo = null;
		affixOk = initAffixAttrData();
		QMTransaction transaction = null;
		int sernum = 0;
		log("创建任务单文档：");

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
			version = (String) ele.getValue("sourceVersion");
			creater = (String) ele.getValue("creater");
			modifier = (String) ele.getValue("modify");
			description = (String) ele.getValue("desc");
			if (name == null || name.trim().length() == 0) {
				result.failureOne();
				String msg = "at save 任务单 (num=" + num + " name="
						+ name + ") ERROR: 任务单文档名称为空";

				errorLog(msg);
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save 任务单 (num=" + num + " name="
						+ name + ") ERROR: 任务单文档编号为空";

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
				errorLog("要创建的任务单文档"+num+"已存在");
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			myHelper.changeorderzong++;
			// 通过映射表（属性文件）取得文档类别的映射值
			docType = (String) ele.getValue("docType");
			//System.out.println("======>docTypeOri: "+docType);
			// 通过映射表（属性文件）取得文档类别的映射值
			docType = myHelper.mapPro.getProperty("doc.docType." + docType);
			if (docType == null)
			{
				docType = myHelper.mapPro.getProperty("doc.docType.default", null);
			}
			department = "技术中心";
			location = myHelper.mapPro.getProperty("doc.location.default", null);
			username = "rdc";
			location = PublishHelper.normalLocation(location);

			String createDept = "";
			if (department != null) {
				createDept = department;
			}
			String docCfBsoID = null;
			String keyWord = "";
			String project = "";
			String lifecycleTemplate = myHelper.mapPro.getProperty("jfpublish.doc.lifecycle", null);
			if (lifecycleTemplate == null|| lifecycleTemplate.trim().equals(""))
			{
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
						
				//CCBegin SS1
				//UserInfo user = userservice.getUserValueInfo("rdc");
				UserInfo user = userservice.getUserValueInfo(username);
				//CCEnd SS1
				
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
				// 如果需要在文档的扩展属性中记录文档的发布源信息,则设置这些信息
				HashMap sourceAffixAttrMap = new HashMap();
				if (affixOk) {
					// 发布源定义存在
					AttrDefineInfo def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_dataSource);
					if (def != null) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.dataSource);
					}
					// 发布源版本定义存在
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_version);
					if (def != null && version != null && version.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), version);
					}
					// 发布源数据的创建者
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_creater);
					if (def != null && creater != null && creater.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), creater);
					}
					// 发布源数据的更新者
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_modifier);
					if (def != null && modifier != null
							&& modifier.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), modifier);
					}

					// 发布时间定义存在
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_publishDate);
					if (def != null && myHelper.publishDate != null
							&& myHelper.publishDate.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.publishDate);
					}
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_publishForm);
					if (def != null && myHelper.publishForm != null
							&& myHelper.publishForm.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(),
								"任务单文档");
					}
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_noteNum);
					if (def != null && myHelper.noteNum != null
							&& myHelper.noteNum.length() > 0) {
						sourceAffixAttrMap
								.put(def.getBsoID(), myHelper.noteNum);
					}

				} // end 设置发布源扩展属性
				formData.setDocumentAttribute("docAttrValueList",
						sourceAffixAttrMap);
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
				}
				
				//保存文档和零部件的参考关系。
				StandardPartService sps = (StandardPartService) PublishHelper.getEJBService("StandardPartService");
				Vector partvec = PublishHelper.getParts(description);
				for(int i=0;i<partvec.size();i++)
				{
					String partid = (String)partvec.elementAt(i);
					PartReferenceLinkInfo link = new PartReferenceLinkInfo();
					link.setRightBsoID(partid);
					link.setLeftBsoID(docInfo.getMasterBsoID());
					sps.savePartReferenceLink(link);
				}
				
				transaction.commit();
				myHelper.changeorderzong++;
				log("创建任务单文档 " + num + " 成功");
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + docInfo.getVersionValue() + ",成功");
				result.successOne();
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
