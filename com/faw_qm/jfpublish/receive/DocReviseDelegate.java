package com.faw_qm.jfpublish.receive;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrValueInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.URLDataInfo;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCBegin by liunan 2008-10-23
import java.util.StringTokenizer;
//CCEnd by liunan 2008-10-23


public class DocReviseDelegate extends AbstractDocStoreDelegate {

	public DocReviseDelegate(Group docs, PublishLoadHelper helper) {
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
		userLog("##ReviseDoc");
		ResultReport result = new ResultReport();
		if (myDocs == null || myDocs.getElementCount() == 0) {
			String s = "û��Ҫ�޶����ĵ�";
			log(s);
			// userLog(s);
			return result;
		}
		Enumeration eum = myDocs.getElements();
		Element ele = null;
		String docNum = null;
		String docName = null;
		DocInfo doc = null;
		String location = null;
		String sourVersion = null;
		//CCBegin by liunan 2008-12-23
		String creater = null;
		//CCEnd by liunan 2008-12-23
		String modifier = null;
		String secret = null;
		String title = null;
		//CCBegin by liunan 2009-12-30 ����޶�ʱ�ĵ�����δ�����õ����⡣
		String docType = null;
		//CCEnd by liunan 2009-12-30
		DocFormData formData = null;
		QMTransaction transaction = null;
		String prosourceversion = "";
		String fbproversion = "";
		log("�޶��ĵ���Ϣ:");
		AttrValueInfo attvalue = null;
		// ���帽�����Զ���ֵ����
		AttrDefineInfo attDefine = null;
		AffixAttrService affService = null;
		try {
			affService = (AffixAttrService) PublishHelper
					.getEJBService("AffixAttrService");
		} catch (QMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PersistService pservice = null;
		try {
			pservice = (PersistService) PublishHelper
					.getEJBService("PersistService");
		} catch (QMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int sernum = 0;
		boolean affixOk = initAffixAttrData();
		while (eum.hasMoreElements()) {
			sernum++;
			myHelper.redoczong++;
			try {
				transaction = new QMTransaction();
				transaction.begin();
				ele = (Element) eum.nextElement();
				docNum = ((String) ele.getValue("num")).trim();
				docName = ((String) ele.getValue("name"));
				sourVersion = (String) ele.getValue("sourceVersion");
				location = (String) ele.getValue("path");
				//CCBegin by liunan 2008-12-23
			  creater = (String) ele.getValue("creater");
			  //CCEnd by liunan 2008-12-23
				modifier = (String) ele.getValue("modify");
				
				//Begin by liunan 2012-03-19 �޶�ʱû��title �ֶΣ����º����Դ�Ϊ�������ж϶�ʧЧ����Ҫ�ǵ���ĵ����ϼ��޶��󶼱��ı��ˡ�
				title = (String) ele.getValue("title");
				//CCEnd by liunan 2012-03-19
				
				secret = (String) ele.getValue("secret");
				if (secret == null || secret.equals("")) {
					secret = "$$1";
				}
				
				//CCBegin by liunan 2009-12-30 ����޶�ʱ�ĵ�����δ�����õ����⡣
				docType = (String) ele.getValue("docType");
				// ͨ��ӳ��������ļ���ȡ���ĵ�����ӳ��ֵ
				docType = myHelper.mapPro.getProperty("doc.docType." + docType);
				if (docType == null) {
					docType = myHelper.mapPro.getProperty("doc.docType.default",
							null);
				}
				//CCEnd by liunan 2009-12-30
				
				if (title != null && title.indexOf("��ص�Ԫ����") != -1) {
					location = myHelper.mapPro
							.getProperty("doc.location.diankong",
									"\\Root\\����\\36����װ��\\����ĵ�");
					//CCBegin by liunan 2009-05-25 Ϊ1000009��ͷ�ĵ���ĵ�ָ�����ϼС�
					if(docNum.startsWith("1000009"))
					{
						location = myHelper.mapPro.getProperty("doc.location.dkfordalianxicai",
						"\\Root\\����\\36����װ��\\������񹫹�����ļ�");
					}
					//CCEnd by liunan 2009-05-25
				} else {
					if (location != null && location.length() > 0) {
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

						location = myHelper.mapPro.getProperty("doc.location."
								+ location);
					}
					if (location == null) {
						location = myHelper.mapPro.getProperty(
								"doc.location.default", null);
					}
				}
				// location = PublishHelper.strDecoding(location, "ISO8859-1");
				doc = PublishHelper.getDocInfoByNumber(docNum);
				fbproversion = doc.getVersionValue();

				Vector attvalues = affService.getAffixAttr(doc);
				int i = attvalues.size();
				// boolean flag = false;
				for (int j = 0; j < i; j++) {
					attvalue = (AttrValueInfo) attvalues.elementAt(j);
					// ͨ����������ֵ�Ķ���ȡ�����Զ����ID��ˢ��ֵ����
					attDefine = (AttrDefineInfo) pservice.refreshInfo(attvalue
							.getAttrDefID(), false);
					// ������Զ�������Ƶ���"version"��ȡ����������ֵ����յ����ݱȽ�
					if (attDefine.getAttrName().toLowerCase().trim().equals(
							"sourceversion")) {
						prosourceversion = attvalue.getValue();
					}
				}
				//CCBegin by liunan 2008-10-23
        //�޸���ǰ�İ汾�ȽϷ������޷���ȷ�Ƚϳ�AA��B��C��D�Ȱ汾�Ĵ�С��
        //���ò�Ʒ�а汾������õİ汾�ȽϷ�����
				//if(!(sourVersion.compareTo(prosourceversion)>0)){
        if(!getPublishFlag(sourVersion,prosourceversion))
        {
        //CCEnd by liunan 2008-10-23
					try {
						transaction.rollback();
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myHelper.redocnum++;
					log("�ĵ� " + docNum + "����Ҫ�޶�");
					result.successOne();
					userLog(sernum + "," + doc.getDocName() + "," + docNum + ","
							+ prosourceversion + "," + sourVersion + ","
							+ fbproversion + "," + doc.getVersionValue() + ",�ɹ�");
					continue;
				}
				formData = new DocFormData();
				
				//CCBegin by liunan 2009-05-14 ����޶�ʱ���ϼиı�δ�����õ����⡣
				//Դ����
				//formData.setDocumentAttribute("folder", location);								
				formData.setDocumentAttribute("NewLocation", location);
				//CCEnd by liunan 2009-05-14
				
				//CCBegin by liunan 2009-12-30 ����޶�ʱ�ĵ�����δ�����õ����⡣
				String docCfBsoID = PublishHelper.getDocCf(docType);
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				//CCEnd by liunan 2009-12-30
				
				String lifecycleTemplate = myHelper.mapPro.getProperty(
						"jfpublish.doc.lifecycle", null);
				String lifecycleState = myHelper.mapPro.getProperty(
						"doc.secret." + secret, null);
				if (lifecycleTemplate == null
						|| lifecycleTemplate.trim().equals("")) {
					lifecycleTemplate = PublishLoadHelper.DEFAULT_LIFECYCLE;
				} // else {
				// lifecycleTemplate = PublishHelper.strDecoding(
				// lifecycleTemplate, "ISO8859-1");
				// }
				formData.setDocumentAttribute("lifecycleTemplate",
						lifecycleTemplate);
				// ������������״̬
				formData.setDocumentAttribute("lifeCState", LifeCycleState
						.toLifeCycleState(lifecycleState));
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
					if (def != null && sourVersion != null
							&& sourVersion.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), sourVersion);
					}
					//CCBegin by liunan 2008-12-22 �޸��޶���û�з���Դ���ݴ����ߵ����⡣
					// ����Դ���ݵĴ�����
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_creater);
					if (def != null && creater != null && creater.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), creater);
					}
					//CCEnd by liunan 2008-12-22
					// ����Դ���ݸ�����
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
					// ������ʽ�������
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_publishForm);
					if (def != null && myHelper.publishForm != null
							&& myHelper.publishForm.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.publishForm);
					}
					// ������Ŷ������
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
				StandardDocService docService = (StandardDocService) PublishHelper
				.getEJBService("StandardDocService");
				doc = (DocInfo) docService.reviseDoc(doc, formData);
				doc = (DocInfo) checkout(doc, "����ĵ�");
				// formData.setDocumentAttribute("bsoID",doc.getBsoID());
				formData.setDocumentAttribute("bsoID", doc.getBsoID());
				//CCBegin by liunan 2008-12-22 �޸��޶���û�д�����λ�����⡣
				formData.setDocumentAttribute("createDept", "��������");
				//CCEnd by liunan 2008-12-22

        //CCBegin by liunan 2008-08-07
        //�ְ汾doc�������Ѿ�����createDoc�����������ص���vector���м䴦�����һ�£�
        //�˴�ֱ�ӵ������°汾����Ȼ��õ�docInfo��
        //ԭ��������
				//doc = (DocInfo) docService.updateDoc(formData);
				Vector docvec = (Vector) docService.updateDoc(formData);
				doc = (DocInfo)docvec.elementAt(0);
				//CCBegin by liunan 2008-08-07

				doc = (DocInfo) checkin(doc, "�����ĵ�");
				// ����°汾���ĵ����ƺ�ԭ���Ĳ���ͬ����������
				if (docName != null && !docName.equals(doc.getName())) {
					formData = new DocFormData();
					formData.setDocumentAttribute("bsoID", doc.getBsoID());
					formData.setDocumentAttribute("docNum", doc.getDocNum());
					formData.setDocumentAttribute("docName", docName);
					docService.renameDoc(formData);
				}
				removeContents(doc);
				transaction.commit();
				myHelper.redocnum++;
				log("�޶��ĵ� " + docNum + " �ɹ�");
				result.successOne();
				userLog(sernum + "," + doc.getDocName() + "," + docNum + ","
						+ prosourceversion + "," + sourVersion + ","
						+ fbproversion + "," + doc.getVersionValue() + ",�ɹ�");
			} catch (QMException e) {
				e.printStackTrace();
				errorLog("�޶��ĵ� " + docNum + " ʧ��");
				errorLog(e);
				try {
					transaction.rollback();
				} catch (QMException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.myHelper.logErrorDoc(docNum);// ��δ�ܳɹ��޶��������ĵ���¼��������־��
				this.myHelper.failedDocs.add(docNum);
				result.failureOne();
				userLog(sernum + "," + doc.getDocName() + "," + docNum + ","
						+ prosourceversion + "," + sourVersion + ","
						+ fbproversion + "," + " " + ",ʧ��" + ","
						+ e.getClientMessage());

			}
		}
		return result;

	}

	private void removeContents(DocInfo doc) throws QMException {
		ContentService cs = (ContentService) PublishHelper
				.getEJBService("ContentService");
		Vector v = cs.getContents(doc);
		ContentItemIfc item;
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			item = (ContentItemIfc) iter.next();
			if (item instanceof ApplicationDataInfo) {
				cs.deleteApplicationData(doc, (ApplicationDataInfo) item);
			} else {
				cs.deleteURLData(doc, (URLDataInfo) item);
			}
		}
	}

	private WorkableIfc checkin(WorkableIfc obj, String note)
			throws QMException {
		if (obj == null) {
			return obj;
		}
		if (WorkInProgressHelper.isCheckedOut(obj)) {
			obj = ((WorkInProgressService) PublishHelper
					.getEJBService("WorkInProgressService")).checkin(obj, note);
		}
		return obj;
	}

	private WorkableIfc checkout(WorkableIfc obj, String note)
			throws QMException {
		if (obj == null) {
			return obj;
		}
		if (WorkInProgressHelper.isCheckedOut(obj)) {
			if (!WorkInProgressHelper.isWorkingCopy(obj)) {
				obj = (WorkableIfc) PublishHelper.workingCopyOf(obj);
			}
		} else {
			// if (!helper.inPersonalFolder(obj))
			{
				PublishHelper.checkOut(obj, note);
				obj = (WorkableIfc) PublishHelper.workingCopyOf(obj);
			}
		}
		return obj;
	}


        /**
         * �Ƚ������汾�Ĵ�С�������Ƿ񷢲��˶���
         * @param s1 String �������°汾��
         * @param s2 String ���pdm�м�¼�ĸö����Ӧ���еİ汾��
         * @return boolean ��Ҫ����ʱ����true�����򷵻�false��
         * @author liunan 2008-10-23
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

          //����StringTokenizer�ָ�st1
          StringTokenizer st1 = new StringTokenizer(s1, ".");
          //����StringTokenizer�ָ�st2
          StringTokenizer st2 = new StringTokenizer(s2, ".");
          int level1 = st1.countTokens();
          int level2 = st2.countTokens();
          //�����汾ֵ���ȴ�Ϊ���µ�
          if (level1 < level2) {
            return false;
          }
          if (level1 > level2) {
            return true;
          }
          String[] sarray1 = new String[level1];
          String[] sarray2 = new String[level2];
          //�ѷָ����ִ��������ַ�������
          for (int i = 0; i < level1; i++) {
            sarray1[i] = st1.nextToken();
            sarray2[i] = st2.nextToken();
          }
          //��ѭ���бȽ������е��ִ�
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
