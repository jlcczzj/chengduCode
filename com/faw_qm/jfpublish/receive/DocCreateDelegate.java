package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.ejb.service.PersistService;

//CCBegin by liunan 2008-08-07
import java.util.Vector;
//CCEnd by liunan 2008-08-07

public class DocCreateDelegate extends AbstractDocStoreDelegate {

	public DocCreateDelegate(Group docs, PublishLoadHelper helper) {
		this.myDocs = docs;
		this.myHelper = helper;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ResultReport process() {
		userLog("##Doc");
		ResultReport result = new ResultReport();
		if (myDocs == null || myDocs.getElementCount() == 0) {
			log("没有需要创建的文档");
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
			result.setFailureCount(myDocs.getElementCount());
			String msg = "ERROR:" + ex.getLocalizedMessage();

			errorLog(msg);
			errorLog(ex);
			return result;
			// ex.printStackTrace();
		} catch (Exception ex) {
			result.setFailureCount(myDocs.getElementCount());
			String msg = "ERROR:" + ex.getLocalizedMessage();

			errorLog(msg);
			errorLog(ex);
			return result;
			// ex.printStackTrace();
		}

		Enumeration enumeration = myDocs.getElements();
		Element ele = null;
		String num = null;
		// String partNum=null;
		String name = null;
		String location = null;
		String docType = null;
		String department = null;
		String version = null;
		String secret = null;
		String title = null;
		String creater = null;
		String modifier = null;
		String sourceVersion = "";
		// 如果要使用扩展属性记录文档的发布源相关信息，则先初始化扩展属性的定义
		boolean affixOk = false;
		DocInfo docInfo = null;
		affixOk = initAffixAttrData();
		QMTransaction transaction = null;
		int sernum = 0;
		log("创建文档信息：");

		while (enumeration.hasMoreElements()) {
			sernum++;
			myHelper.doczong++;
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
			secret = (String) ele.getValue("secret");
			creater = (String) ele.getValue("creater");
			modifier = (String) ele.getValue("modify");
			title = (String) ele.getValue("title");
			if (secret == null || secret.equals("")) {
				secret = "$$1";
			}
			if (name == null || name.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Doc (num=" + num + " name=" + name
						+ ") ERROR: 文档名称为空";

				errorLog(msg);
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Doc (num=" + num + " name=" + name
						+ ") ERROR: 文档编号为空";

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
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHelper.docsucnum++;
				log("文档 " + num + " 已存在");
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + docInfo.getVersionValue() + ",成功");
				result.successOne();
				continue;
			}
			docType = (String) ele.getValue("docType");
			//System.out.println("======>docTypeOri: "+docType);
			// 通过映射表（属性文件）取得文档类别的映射值
			docType = myHelper.mapPro.getProperty("doc.docType." + docType);
			if (docType == null) {
				docType = myHelper.mapPro.getProperty("doc.docType.default",
						null);
			}
			//System.out.println("=====>docTypeAfterMap:"+docType);
			// docType = PublishHelper.strDecoding(docType, "ISO8859-1");
			department = "技术中心";
			if (title != null && title.indexOf("电控单元数据") != -1) {
				location = myHelper.mapPro.getProperty("doc.location.diankong",
						"\\Root\\电气\\36电子装置\\电控文档");
				if(num.startsWith("1000009"))
				{
					location = myHelper.mapPro.getProperty("doc.location.dkfordalianxicai",
						"\\Root\\电气\\36电子装置\\大柴锡柴公共电控文件");
				}
			} else {
				location = (String) ele.getValue("path");
				location=PublishHelper.cutPathString(location);
				if (location.startsWith("/")) {
					location = location.substring(1);
					// 通过映射表（属性文件）取得文档文件夹位置的映射值
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

				location = myHelper.mapPro.getProperty("doc.location."
						+ location);
				if (location == null || location.trim().equals("")) {
					location = myHelper.mapPro.getProperty(
							"doc.location.default", null);
				}
			}
			// location = PublishHelper.strDecoding(location, "ISO8859-1");
			location = PublishHelper.normalLocation(location);

			String createDept = "";
			if (department != null) {
				createDept = department;
			}
			String docCfBsoID = null;
			String description = "发布的文档";
			String keyWord = "";
			String project = "";
			String lifecycleTemplate = myHelper.mapPro.getProperty(
					"jfpublish.doc.lifecycle", null);
			String lifecycleState = myHelper.mapPro.getProperty("doc.secret."
					+ secret, null);
			if (lifecycleTemplate == null
					|| lifecycleTemplate.trim().equals("")) {
				lifecycleTemplate = PublishLoadHelper.DEFAULT_LIFECYCLE;
			} /*
				 * else { lifecycleTemplate = PublishHelper.strDecoding(
				 * lifecycleTemplate, "ISO8859-1"); }
				 */
			sourceVersion = (String) ele.getValue("sourceVersion");
			try {
				//System.out.println("=====>docTypeBeforeGetId: "+docType);
				docCfBsoID = PublishHelper.getDocCf(docType);
                //System.out.println("=====>docCfBsoId: "+docCfBsoID);
				DocFormData formData = new DocFormData();

				formData.setDocumentAttribute("docName", name);
				// 设置文档编号
				formData.setDocumentAttribute("docNum", num);
				// 设置内容简述
				formData.setDocumentAttribute("contDesc", description);
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

				// 设置生命周期状态
				formData.setDocumentAttribute("lifeCState", LifeCycleState
						.toLifeCycleState(lifecycleState));
				if (myHelper.user != null) {
					formData.setDocumentAttribute("iterationCreator",
							myHelper.user.getBsoID());
					formData.setDocumentAttribute("iterationModifier",
							myHelper.user.getBsoID());
					formData.setDocumentAttribute("aclOwner", myHelper.user
							.getBsoID());
					formData.setDocumentAttribute("creator", myHelper.user
							.getBsoID());
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
								myHelper.publishForm);
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

				transaction.commit();
				myHelper.docsucnum++;
				log("创建文档 " + num + " 成功");
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + docInfo.getVersionValue() + ",成功");
				result.successOne();
			} catch (QMException ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.logErrorDoc(num);// 将未能成功创建的描述文档记录到错误日志中
				this.myHelper.failedDocs.add(num);
				result.failureOne();
				String msg = "at save Doucment (name=" + name + " num=" + num
						+ ") ERROR:" + ex.getClientMessage();

				errorLog(msg);
				errorLog(ex);
				ex.printStackTrace();
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + " " + ",失败" + "," + ex.getClientMessage());
				// ex.printStackTrace();
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.logErrorDoc(num);// 将未能成功创建的描述文档记录到错误日志中
				this.myHelper.failedDocs.add(num);
				result.failureOne();
				String msg = "at save Doucment (name=" + name + " num=" + num
						+ ") ERROR:" + ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + " " + ",失败" + "," + ex.getLocalizedMessage());
				// ex.printStackTrace();
			}
		}
		return result;
	}
}
