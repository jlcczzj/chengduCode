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
			log("û����Ҫ�������ĵ�");
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
		// ���Ҫʹ����չ���Լ�¼�ĵ��ķ���Դ�����Ϣ�����ȳ�ʼ����չ���ԵĶ���
		boolean affixOk = false;
		DocInfo docInfo = null;
		affixOk = initAffixAttrData();
		QMTransaction transaction = null;
		int sernum = 0;
		log("�����ĵ���Ϣ��");

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
						+ ") ERROR: �ĵ�����Ϊ��";

				errorLog(msg);
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Doc (num=" + num + " name=" + name
						+ ") ERROR: �ĵ����Ϊ��";

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
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHelper.docsucnum++;
				log("�ĵ� " + num + " �Ѵ���");
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + docInfo.getVersionValue() + ",�ɹ�");
				result.successOne();
				continue;
			}
			docType = (String) ele.getValue("docType");
			//System.out.println("======>docTypeOri: "+docType);
			// ͨ��ӳ��������ļ���ȡ���ĵ�����ӳ��ֵ
			docType = myHelper.mapPro.getProperty("doc.docType." + docType);
			if (docType == null) {
				docType = myHelper.mapPro.getProperty("doc.docType.default",
						null);
			}
			//System.out.println("=====>docTypeAfterMap:"+docType);
			// docType = PublishHelper.strDecoding(docType, "ISO8859-1");
			department = "��������";
			if (title != null && title.indexOf("��ص�Ԫ����") != -1) {
				location = myHelper.mapPro.getProperty("doc.location.diankong",
						"\\Root\\����\\36����װ��\\����ĵ�");
				if(num.startsWith("1000009"))
				{
					location = myHelper.mapPro.getProperty("doc.location.dkfordalianxicai",
						"\\Root\\����\\36����װ��\\������񹫹�����ļ�");
				}
			} else {
				location = (String) ele.getValue("path");
				location=PublishHelper.cutPathString(location);
				if (location.startsWith("/")) {
					location = location.substring(1);
					// ͨ��ӳ��������ļ���ȡ���ĵ��ļ���λ�õ�ӳ��ֵ
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
			String description = "�������ĵ�";
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
				// �����ĵ����
				formData.setDocumentAttribute("docNum", num);
				// �������ݼ���
				formData.setDocumentAttribute("contDesc", description);
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

				// ������������״̬
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
								myHelper.publishForm);
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

				transaction.commit();
				myHelper.docsucnum++;
				log("�����ĵ� " + num + " �ɹ�");
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + docInfo.getVersionValue() + ",�ɹ�");
				result.successOne();
			} catch (QMException ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.logErrorDoc(num);// ��δ�ܳɹ������������ĵ���¼��������־��
				this.myHelper.failedDocs.add(num);
				result.failureOne();
				String msg = "at save Doucment (name=" + name + " num=" + num
						+ ") ERROR:" + ex.getClientMessage();

				errorLog(msg);
				errorLog(ex);
				ex.printStackTrace();
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + " " + ",ʧ��" + "," + ex.getClientMessage());
				// ex.printStackTrace();
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.logErrorDoc(num);// ��δ�ܳɹ������������ĵ���¼��������־��
				this.myHelper.failedDocs.add(num);
				result.failureOne();
				String msg = "at save Doucment (name=" + name + " num=" + num
						+ ") ERROR:" + ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				userLog(sernum + "," + name + "," + num + "," + sourceVersion
						+ "," + " " + ",ʧ��" + "," + ex.getLocalizedMessage());
				// ex.printStackTrace();
			}
		}
		return result;
	}
}
