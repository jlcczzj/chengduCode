/**
 * SS1 ����ļ������������߼� jiahx 2013-12-10
 * SS2 ��������������Ҫ�������е��㲿���嵥���д������������嵥�͸�������Ƶ����Ƽ������������񵥱�����ԡ� liunan 2017-6-27
 * SS3 ����� �������񵥣��򱣴浽�����������ϼ��С� liunan 2017-9-7
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
			log("û����Ҫ�����ļ������ı����");
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
		// ���Ҫʹ����չ���Լ�¼�ĵ��ķ���Դ�����Ϣ�����ȳ�ʼ����չ���ԵĶ���
		boolean affixOk = false;
		DocInfo docInfo = null;
		QMTransaction transaction = null;
		int sernum = 0;
		log("�����������ı������");

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
						+ name + ") ERROR: ���������Ϊ��";

				errorLog(msg);
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save changeorder (num=" + num + " name="
						+ name + ") ERROR: ��������Ϊ��";

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
				errorLog("Ҫ�����ļ������ı����"+num+"�Ѵ���");
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
			// ͨ��ӳ��������ļ���ȡ���ĵ�����ӳ��ֵ
			docType = myHelper.mapPro.getProperty("doc.docType.changeorder",
					"֪ͨ\\�������ı����");
			department = "��������";
			location = myHelper.mapPro.getProperty("doc.location.changeorder",
					"\\Root\\�����\\�������ı����");
			
			//CCBegin SS3
			if(objType!=null&&objType.equals("��������"))
			{
				location = "\\Root\\�����\\��������";
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
				UserInfo user = userservice.getUserValueInfo("rdc");// yanqi-20070709,�û�ֱ������Ϊ��������
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
				formData.setDocumentAttribute("docAttrValueList", null);
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
					//CCEnd SS1
				}
				
				//CCBegin SS2
				if(objType!=null&&objType.equals("��������"))
				{
					//���÷��������������嵥excel�ļ�������������Ƶ�����״̬�㲿�����ø���֪ͨ������Ϊ�������񵥺�
					PublishHelper.createShiZhiRenWu(docInfo,description);
				}
				//CCEnd SS2
				
				transaction.commit();
				myHelper.changeorderzong++;
				log("�����������ı���� " + num + " �ɹ�");
				//userLog(sernum + "," + name + "," + num + "," + sourceVersion
						//+ "," + docInfo.getVersionValue() + ",�ɹ�");
				result.successOne();
				resetPartState(docInfo.getBsoID(), num);//20080410��ӣ�������ȡ�����㲿��״̬
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
   * �޸�����״̬�ķ���������Ҫ���á�����֪ͨ��š����ԣ����Զഫһ��������ԡ�
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
				//�޸��㲿��iba���ԡ�����֪ͨ��š�
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
	 * ����ȡ�����㲿��ҲҪ��Ӳ���֪ͨ�����ԣ�������׼���㲿����ӡ�
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
      //CCBegin by liunan 2011-04-07 �����������󣬡�����֪ͨ��š� ����Ϊ ������֪ͨ��š������԰����������ĳ�name
      //QueryCondition qc2 = new QueryCondition("displayName", " = ", "����֪ͨ���");
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
        QueryCondition qc5 = new QueryCondition("displayName", " = ", "���á�����֪ͨ���");
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
