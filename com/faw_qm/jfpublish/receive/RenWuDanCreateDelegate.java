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
			System.out.println("û����Ҫ�����������ĵ�");
			log("û����Ҫ�����������ĵ�");
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
		
		// ���Ҫʹ����չ���Լ�¼�ĵ��ķ���Դ�����Ϣ�����ȳ�ʼ����չ���ԵĶ���
		boolean affixOk = false;
		DocInfo docInfo = null;
		affixOk = initAffixAttrData();
		QMTransaction transaction = null;
		int sernum = 0;
		log("���������ĵ���");

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
				String msg = "at save ���� (num=" + num + " name="
						+ name + ") ERROR: �����ĵ�����Ϊ��";

				errorLog(msg);
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save ���� (num=" + num + " name="
						+ name + ") ERROR: �����ĵ����Ϊ��";

				errorLog(msg);
				continue;
			}

			// �ж�Ҫ�������ĵ��Ƿ����
			DocMasterInfo docmaster = null;
			try {
				docmaster = PublishHelper.getDocMasterByNumber(num);
			} catch (QMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (docmaster != null) {
				errorLog("Ҫ�����������ĵ�"+num+"�Ѵ���");
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			myHelper.changeorderzong++;
			// ͨ��ӳ��������ļ���ȡ���ĵ�����ӳ��ֵ
			docType = (String) ele.getValue("docType");
			//System.out.println("======>docTypeOri: "+docType);
			// ͨ��ӳ��������ļ���ȡ���ĵ�����ӳ��ֵ
			docType = myHelper.mapPro.getProperty("doc.docType." + docType);
			if (docType == null)
			{
				docType = myHelper.mapPro.getProperty("doc.docType.default", null);
			}
			department = "��������";
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
				// �����ĵ����
				formData.setDocumentAttribute("docNum", num);
				// �������ݼ���
				if (description.length() < 2000) {
					formData.setDocumentAttribute("contDesc", description);
				} else {
					formData.setDocumentAttribute("contDesc", "TOOLONG");
				}
				// ���ô�����λ
				formData.setDocumentAttribute("createDept", createDept);
				// �����ĵ����
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				// ������Ŀ��
				formData.setDocumentAttribute("projTeam", project);
				// �������ϼ�
				formData.setDocumentAttribute("folder", location);
				// ������������
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
				// ���ùؼ���
				formData.setDocumentAttribute("keyWord", keyWord);
				formData
						.setDocumentAttribute("dependencyList", new ArrayList());
				formData.setDocumentAttribute("usageList", new ArrayList());
				// �����Ҫ���ĵ�����չ�����м�¼�ĵ��ķ���Դ��Ϣ,��������Щ��Ϣ
				HashMap sourceAffixAttrMap = new HashMap();
				if (affixOk) {
					// ����Դ�������
					AttrDefineInfo def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_dataSource);
					if (def != null) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.dataSource);
					}
					// ����Դ�汾�������
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_version);
					if (def != null && version != null && version.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), version);
					}
					// ����Դ���ݵĴ�����
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_creater);
					if (def != null && creater != null && creater.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), creater);
					}
					// ����Դ���ݵĸ�����
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_modifier);
					if (def != null && modifier != null
							&& modifier.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), modifier);
					}

					// ����ʱ�䶨�����
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
								"�����ĵ�");
					}
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_noteNum);
					if (def != null && myHelper.noteNum != null
							&& myHelper.noteNum.length() > 0) {
						sourceAffixAttrMap
								.put(def.getBsoID(), myHelper.noteNum);
					}

				} // end ���÷���Դ��չ����
				formData.setDocumentAttribute("docAttrValueList",
						sourceAffixAttrMap);
				HashMap fileDescHashMap = new HashMap();
				formData.setDocumentAttribute("fileDescriptList",
						fileDescHashMap);

        //CCBegin by liunan 2008-08-07
        //�ְ汾doc�������Ѿ�����createDoc�����������ص���vector���м䴦�����һ�£�
        //�˴�ֱ�ӵ������°汾����Ȼ��õ�docInfo��
        //ԭ��������
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
				
				//�����ĵ����㲿���Ĳο���ϵ��
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
				log("���������ĵ� " + num + " �ɹ�");
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + docInfo.getVersionValue() + ",�ɹ�");
				result.successOne();
			} catch (QMException ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// this.myHelper.logErrorDoc(num);// ��δ�ܳɹ������������ĵ���¼��������־��
				result.failureOne();
				String msg = "at save changeorder (name=" + name + " num="
						+ num + ") ERROR:" + ex.getClientMessage();

				errorLog(msg);
				errorLog(ex);
				ex.printStackTrace();
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + " " + ",ʧ��" + "," + ex.getClientMessage());
				// ex.printStackTrace();
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// this.myHelper.logErrorDoc(num);// ��δ�ܳɹ������������ĵ���¼��������־��
				result.failureOne();
				String msg = "at save changeorder (name=" + name + " num="
						+ num + ") ERROR:" + ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + " " + ",ʧ��" + "," + ex.getLocalizedMessage());
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
