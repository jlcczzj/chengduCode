/**
 * SS1 ����ļ������������߼� jiahx 2013-12-10
 */
package com.faw_qm.jfpublish.receive;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.ManagedBaselineInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.exception.ContentException;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemInfo;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.exception.DocException;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.domain.ejb.service.DomainService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Param;
import com.faw_qm.integration.model.Request;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.jfpublish.receive.PublishLoadHelper.NewReturnThread;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.QMThread;

public class PublishErrorProcessor extends PublishLoadHelper {

	//CCBegin SS1
	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
	//CCEnd SS1
	
	private Script myScript;

	public ArrayList errorParts = new ArrayList();

	public ArrayList errorDocs = new ArrayList();

	public ArrayList errorEpms = new ArrayList();

	public PublishErrorProcessor(Script script) {
		this.myScript = script;
	}

	public PublishErrorProcessor(String xmlFileName) {
		try {
			Request req = new Request(RequestIn.readRequest2(xmlFileName));
			this.myScript = req.getScript();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class MyThread extends QMThread {

		private PublishErrorProcessor errorProcessor;

		public void run() {
			errorProcessor.doProcess();
		}

		public MyThread(PublishErrorProcessor pp) {

			this.errorProcessor = pp;
		}
	}

	public void publishRecover() {
		MyThread thread = new MyThread(this);
		thread.start();
	}

	public void doProcess() {
		SessionService sservice = null;
		try {
			sservice = (SessionService) PublishHelper
					.getEJBService("SessionService");
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PublishHelper.setStateBusy();
			Command cmd = this.myScript.getCommand();
			String cmdName = cmd.getName();
			UsersService userservice = (UsersService) PublishHelper
					.getEJBService("UsersService");
			// user = sservice.getCurUserInfo();
			user = userservice.getUserValueInfo("rdc");// yanqi-20070709,�û�ֱ������Ϊ��������
			PublishHelper.setStateBusy();
			sservice.setAccessEnforced(false);
			//sservice.setAdministrator();
			// ��¼������Դ��ֵΪ�������ģ�
			// ��¼�Ӽ������ķ���������ʱ������
			publishDate = cmd.paramValue("PUBLISHDATE");
			// ��¼�Ժ�����ʽ����������֪ͨ��/����֪ͨ�飩
			publishForm = cmd.paramValue("PUBLISHFORM");
			// ��¼����֪ͨ����������ı��
			noteNum = cmd.paramValue("NOTENUMBER");
			primaryPartNum = cmd.paramValue("PRIMARYPART");
			errorWriter = new PrintWriter(new FileWriter(new File(ERROR_PATH
					+ noteNum + ".log"), true), true);
			logger = new PublishPartsLog(noteNum);
			log("�������ݷ������û��ǣ�" + user.getUsersName());
			ps = (PersistService) PublishHelper.getEJBService("PersistService");
			// log(script.getVdb().toXML());
			// ��������������Ϣ
			Group partsCreateGrp = null;
			Group partsReviseGrp = null;
			Group partUsageLinksGrp = null;
			// Group partRefsGrp = null;
			Group partEPMBuildsGrp = null;
			Group partDescsGrp = null;
			Group docsCreateGrp = null;
			Group docsReviseGrp = null;
			Group epmCreateGrp = null;
			Group epmReviseGrp = null;
			Group partsIBAAttr = null;
			Group epmIBAAttr = null;
			
			//CCBegin by liunan 2008-12-01
			Group partAlternatesGrp = null;
			Group partSubstitutesGrp = null;
			//CCEnd by liunan 2008-12-01
			
			partsCreateGrp = myScript.getGroupIn("CreateParts");
			partsReviseGrp = myScript.getGroupIn("ReviseParts");
			partUsageLinksGrp = myScript.getGroupIn("PartUsageLinks");
			// partRefsGrp = script.getGroupIn("PartRefrenceLinks");
			partDescsGrp = myScript.getGroupIn("PartDescribeLinks");
			partEPMBuildsGrp = myScript.getGroupIn("PartEPMBuilds");
			epmCreateGrp = myScript.getGroupIn("CreateEPMs");
			epmReviseGrp = myScript.getGroupIn("ReviseEPMs");
			docsCreateGrp = myScript.getGroupIn("CreateDocs");
			docsReviseGrp = myScript.getGroupIn("ReviseDocs");
			partsIBAAttr = myScript.getGroupIn("PartIBAAttrs");
			epmIBAAttr = myScript.getGroupIn("EPMIBAAttrs");

			//CCBegin by liunan 2008-12-01
			partAlternatesGrp = myScript.getGroupIn("AlternatesLinks");
			//System.out.println("=========error=========="+partAlternatesGrp);
			partSubstitutesGrp = myScript.getGroupIn("SubstitutesLinks");
			//System.out.println("=========error=========="+partSubstitutesGrp);
			//CCEnd by liunan 2008-12-01
				
			Hashtable partIBA = PublishHelper.initIBA(partsIBAAttr);
			Hashtable epmIBA = PublishHelper.initIBA(epmIBAAttr);

			partsCreateGrp = this.filterateCreateParts(partsCreateGrp,
					partsReviseGrp);// Ϊ�˷�ֹ��֤��Ϣ��׼ȷ�����½�����֤
			partsReviseGrp = this.filterateReviseParts(partsReviseGrp);// Ϊ�˷�ֹ��֤��Ϣ��׼ȷ�����½�����֤
			docsCreateGrp = this.filterateCreateDocs(docsCreateGrp,
					docsReviseGrp);// Ϊ�˷�ֹ��֤��Ϣ��׼ȷ�����½�����֤
			docsReviseGrp = this.filterateReviseDocs(docsReviseGrp);

			this.loadErrorObjects();

			partsCreateGrp = this.filterateErrorParts(partsCreateGrp);
			partsReviseGrp = this.filterateErrorParts(partsReviseGrp);// Ϊ�˷�ֹ��֤��Ϣ��׼ȷ�����½�����֤
			epmCreateGrp = this.filterateErrorEpms(epmCreateGrp);// Ϊ�˷�ֹ��֤��Ϣ��׼ȷ�����½�����֤
			epmReviseGrp = this.filterateErrorEpms(epmReviseGrp);
			docsCreateGrp = this.filterateErrorDocs(docsCreateGrp);// Ϊ�˷�ֹ��֤��Ϣ��׼ȷ�����½�����֤
			docsReviseGrp = this.filterateErrorDocs(docsReviseGrp);

			partUsageLinksGrp = this.filteratePartUsageLink(partUsageLinksGrp);
			partDescsGrp = this.filteratePartDescribeLink(partDescsGrp);
			partEPMBuildsGrp = this.filteratePartBuildLink(partEPMBuildsGrp);

			SimpleDateFormat logDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd-HH:mm:ss.SSS");
			userLog("#�������ݽ��յ��û��ǣ�" + user.getUsersName());
			userLog("#���η������ݽ��յ�ʱ���ǣ�"
					+ logDateFormat.format(new java.util.Date()));
			// ����������Ϣ

			log("����Part");
			if (partsCreateGrp != null) {
				PartCreateDelegate del = new PartCreateDelegate(partsCreateGrp,
						partIBA, this);
				ResultReport res = del.process();
				log("��������part���� RESURLT ***************");
				log(res);
			}

			// ����Doc
			log("����Doc");
			if (docsCreateGrp != null) {
				DocCreateDelegate del = new DocCreateDelegate(docsCreateGrp,
						this);
				ResultReport res = del.process();
				log("��������Doc���� RESURLT ***************");
				log(res);
			}

			// ����EPM�ĵ�
			//CCBegin by liunan 2009-12-07 ���ٷ���EPM������ݡ�
			log("����EPM�ĵ�");
			if (epmCreateGrp != null) {
				EpmCreateDelegate del = new EpmCreateDelegate(epmCreateGrp,
						epmIBA, this);
				ResultReport res = del.process();
				log("����EPM�ĵ����� RESULT ***************");
				log(res);

			}
			//CCEnd by liunan 2009-12-07
			// ---------------------------------------------------------------------------------
			// �޶�Part
			log("�޶�Part");
			if (partsReviseGrp != null) {
				PartReviseDelegate del = new PartReviseDelegate(partsReviseGrp,
						partIBA, this);
				ResultReport res = del.process();
				log("�޶�Part����***************");
				log(res);
			}

			// �޶�Doc
			log("�޶�Doc");
			if (docsReviseGrp != null) {
				DocReviseDelegate del = new DocReviseDelegate(docsReviseGrp,
						this);
				ResultReport res = del.process();
				log("�޶�Doc���� RESURLT ***************");
				log(res);

			}
			// �޶�EPM�ĵ�
			//CCBegin by liunan 2009-12-07 ���ٷ���EPM������ݡ�
			log("�޶�EPM�ĵ�");
			try {
				if (epmReviseGrp != null) {
					EpmReviseDelegate del = new EpmReviseDelegate(epmReviseGrp,
							epmIBA, this);
					ResultReport res = del.process();
					log("�޶�EPM�ĵ����� RESULT ***************");
					log(res);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//CCEnd by liunan 2009-12-07

			// ����Part�ṹ
			RelationsDelegate relationsDel = new RelationsDelegate(this);
			log("����Part�ṹ");
			if (partUsageLinksGrp != null) {
				ResultReport res = relationsDel
						.savePartUsageLinks(partUsageLinksGrp);
				log("����Part�ṹ���� RESULT ***************");
				log(res);

			}

			// ����Part��Doc������ϵ
			log("����Part��Doc������ϵ");
			if (partDescsGrp != null) {
				ResultReport res = relationsDel
						.savePartDescribeLinks(partDescsGrp);
				log("����Part��Doc������ϵ���� RESULT ***************");
				log(res);

			}
				
			//CCBegin by liunan 2008-12-01
			// ����Part���滻��������ϵ
			log("����Part��alternate������ϵ");
			if (partAlternatesGrp != null) {
				ResultReport res = relationsDel
						.savePartAlternatesLinks(partAlternatesGrp);
				log("����Part���滻��������ϵ���� RESULT ***************");
				log(res);
			}
			// ����Part��ṹ�滻��������ϵ
			log("����Part��subtitutes������ϵ");
			if (partSubstitutesGrp != null) {
				ResultReport res = relationsDel
						.savePartSubtitutesLinks(partSubstitutesGrp);
				log("����Part��ṹ�滻��������ϵ���� RESULT ***************");
				log(res);
			}
			//CCEnd by liunan 2008-12-01				
			//processBaseLine();//anan want
			generateLog();
			//CCBegin by liunan 2008-11-14 ȡ��rdc��ʱ����ԱȨ�ޡ�
			sservice.setAccessEnforced(true);
			//sservice.freeAdministrator();
			PublishHelper.setStateFree();
		} catch (Exception e) {
			/*try {
				sservice.setAccessEnforced(true);
				sservice.freeAdministrator();
			} catch (QMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PublishHelper.setStateFree();*/
			e.printStackTrace();
		}
		finally {
			/*try {
				PublishHelper.setStateFree();
				sservice.setAccessEnforced(true);
				} catch (QMException e1) {
				e1.printStackTrace();
			}*/
			}
			//CCEnd by liunan 2008-11-14
	}

	protected void generateLog() {
		int m = 0;
		int n = 0;
		int a = 0;
		int b = 0;
		String docnum = noteNum;
		String path = userLog + "userpublish" + noteNum + "0.log";
		File file = new File(userLog);
		boolean ischange = true;
		if (file.exists()) {
			path = userLog + "userpublish" + noteNum + "0.log";
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				int j = files[i].getPath().indexOf(noteNum);

				if (j != -1) {
					String s = files[i].getPath().substring(
							j + noteNum.length(), j + noteNum.length() + 1);
					try {
						m = Integer.valueOf(s).intValue();
						if (m > n) {
							n = m;
							path = files[i].getPath();

						}
					} catch (Exception e) {
						continue;
					}
					ischange = false;
					docnum = noteNum;
				}
			}
		}
		if (file.exists() && ischange) {
			path = userLog + "changepublish0.log";
			File[] changefiles = file.listFiles();
			for (int i = 0; i < changefiles.length; i++) {
				int k = changefiles[i].getPath().indexOf("changepublish");
				int l = changefiles[i].getPath().lastIndexOf(".");
				if (k != -1) {
					String s = changefiles[i].getPath().substring(k + 13, l);
					a = Integer.valueOf(s).intValue();
					if (a > b) {
						b = a;
						path = changefiles[i].getPath();

					}
				}

			}
			docnum = "changepublish" + b;
		}

		// System.out.println("---------------------------->>>"+path);
		//CCBegin by liunan 2008-12-03 ��Ӷ��滻���ͽṹ�滻����֧�֡�
		JieFangTemplateProcessor jfTemplateProcessor = new JieFangTemplateProcessor(
				path, user.getUsersName(), zong, sucnum, doczong, docsucnum,
				rezong, resucnum, redoczong, redocnum,
				linkzong - linksuc, altzong, altnum, subzong, subnum);
		//CCEnd by liunan 2008-12-03
		// ��ָ��Ŀ¼������־��html�ļ�
		logFileName = LOG_PATH + noteNum + "-"
				+ logDateFormat.format(new java.util.Date()) + ".html";
		// System.out.println("--------------------------->>>"+logFileName);

		try {
			JieFangTemplateProcessor.writeFile(jfTemplateProcessor
					.getLogContent(), logFileName);
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ͨ���ĵ�������־�ļ�
		// ͨ���ĵ�������־�ļ�
		DocInfo docInfo = null;
		try {
			docInfo = PublishHelper.getDocInfoByNumber("CONFIRMATION-"
					+ noteNum);
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (docInfo == null) {
			return;
		}
		StandardDocService ser = null;
		try {
			ser = (StandardDocService) PublishHelper
					.getEJBService("StandardDocService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ContentService cService = null;
		try {
			cService = (ContentService) PublishHelper
					.getEJBService("ContentService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * String docCfBsoID = null; try { docCfBsoID =
		 * PublishHelper.getDocCf("������\\���ݷ���������־"); } catch (QMException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); } DocFormData
		 * formData = null; try { formData = new DocFormData(); } catch
		 * (DocException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } if (docInfo == null) { if (noteNum == null ||
		 * noteNum.length() == 0) { noteNum = "changepublish"; }
		 * formData.setDocumentAttribute("docName", docnum); // �����ĵ����
		 * formData.setDocumentAttribute("docNum", docnum); // ���ô�����λ
		 * formData.setDocumentAttribute("createDept", "��������"); // �����ĵ����
		 * formData.setDocumentAttribute("docCfBsoID", docCfBsoID); // �������ϼ�
		 * formData.setDocumentAttribute("folder", "\\Root\\���ݽ�������\\���ݷ�����־"); //
		 * ������������ formData.setDocumentAttribute("lifecycleTemplate",
		 * "���ݽ�����������"); formData.setDocumentAttribute("contDesc", partsBaseline +
		 * "," + structureBaseline); if (user != null) {
		 * formData.setDocumentAttribute("iterationCreator", user .getBsoID());
		 * formData.setDocumentAttribute("iterationModifier", user .getBsoID());
		 * formData.setDocumentAttribute("aclOwner", user.getBsoID());
		 * formData.setDocumentAttribute("creator", user.getBsoID()); } try {
		 * docInfo = (DocInfo) ser.createDoc(formData); } catch (DocException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (ServiceLocatorException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (QMException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } try { docInfo = (DocInfo)
		 * ps.refreshInfo(docInfo); } catch (QMException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } else { //
		 * formData.setDocumentAttribute("lifeCState",State.toState("RECEIVING"));
		 * docInfo.setLifeCycleState(State.toState("QIYENEIKONG")); try {
		 * docInfo = (DocInfo) ((StandardDocService) PublishHelper
		 * .getEJBService("StandardDocService")).reviseDoc( docInfo, formData); }
		 * catch (DocException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (QMException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
		String LOG_PATH = null;
		try
		{
			LOG_PATH = QMProperties.getLocalProperties().getProperty("publish.log.path","opt/v4r3/jfupv4r3/support/publish/log/");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		File variableFile = new File(logFileName);
		DataInputStream dataInputStream = null;
		byte[] oldFileBytes = null;
		try {
			oldFileBytes = new byte[(int) variableFile.length()];
			dataInputStream = new DataInputStream(new FileInputStream(
					variableFile));
			dataInputStream.read(oldFileBytes);
			dataInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//CCBegin SS1
		if(fileVaultUsed)
		{
			ContentClientHelper helper = new ContentClientHelper();
			ApplicationDataInfo appDataInfo = null;
			try {
				appDataInfo = helper.requestUpload((byte[]) oldFileBytes);
			} catch (QMException e) {
				e.printStackTrace();
			}	
			try {
				appDataInfo.setFileName("����ָ����ͳ��.html");
				appDataInfo.setUploadPath(LOG_PATH);
				appDataInfo = (ApplicationDataInfo) cService.uploadContent(
						(FormatContentHolderIfc) docInfo, appDataInfo);
			} catch (ContentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
			appDataInfo.setFileName("����ָ����ͳ��.html");
			appDataInfo.setUploadPath(LOG_PATH);
			appDataInfo.setFileSize(variableFile.length());
			try {
				appDataInfo = (ApplicationDataInfo) cService.uploadContent(
						(FormatContentHolderIfc) docInfo, appDataInfo);
			} catch (ContentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String streamId = appDataInfo.getStreamDataID();
			try {
				StreamUtil.writeData(streamId, (byte[]) oldFileBytes);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//CCEnd SS1
		if (variableFile.isFile()) {
			variableFile.delete();
		}
		if (PublishHelper.VERBOSE)
			System.out.println("************** ���ߴ�������");
	}

	private void processBaseLine() throws QMException {
		QMTransaction transaction = new QMTransaction();
		transaction.begin();
		try {
			log("���д������ߵ��û��ǣ�" + user.getUsersName());
			BaselineService bslsvr = (BaselineService) PublishHelper
					.getEJBService("BaselineService");
			PersistService pservice = (PersistService) PublishHelper
					.getEJBService("PersistService");
			DomainService domain = (DomainService) PublishHelper
					.getEJBService("DomainService");
			String location = PublishHelper.strDecoding(mapPro
					.getProperty("jfpublish.baseline.folder"), "ISO8859-1");
			String domainID = domain.getDomainID("System");
			String lifecycle = mapPro.getProperty(
					"jfpublish.baseline.lifecycletemplate", null);
			lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			String lifeCyID = PublishHelper.getLifeCyID(lifecycle);

			String seqNum = pservice.getNextSequence("baselineIdentity_seq");
			ManagedBaselineInfo baseline = new ManagedBaselineInfo();// �����ν��յ��㲿����ӵ��û�����
			baseline.setBaselineName("���ݽ��ջ���" + noteNum + seqNum);
			baseline.setBaselineNumber(noteNum + seqNum);
			if (location != null && location.length() > 0) {
				baseline.setLocation(location);

			}
			baseline.setDomain(domainID);
			baseline.setLifeCycleTemplate(lifeCyID);
			baseline = (ManagedBaselineInfo) pservice.storeValueInfo(baseline,
					false);
			String partNum = null;
			QMPartIfc part = null;
			for (Iterator ite = partVector.iterator(); ite.hasNext();) {
				partNum = (String) ite.next();
				part = PublishHelper.getPartInfoByNumber(partNum);
				bslsvr.addToBaseline(part, baseline);
			}
			// �������ߣ��������η����ĸ��������ṹ��ӵ��û�����
			seqNum = pservice.getNextSequence("baselineIdentity_seq");
			ManagedBaselineInfo newBaseline = new ManagedBaselineInfo();
			newBaseline.setBaselineName("�㲿������" + primaryPartNum + seqNum);
			newBaseline.setBaselineNumber(primaryPartNum + seqNum);
			if (location != null && location.length() > 0) {
				newBaseline.setLocation(location);

			}
			newBaseline.setDomain(domainID);
			newBaseline = (ManagedBaselineInfo) pservice.storeValueInfo(
					newBaseline, false);
			QMPartIfc primarypart = PublishHelper
					.getPartInfoByNumber(primaryPartNum);
			StandardPartService standardservice = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
			// ��ȡ��ǰ�û������ù淶
			PartConfigSpecIfc spec = standardservice.findPartConfigSpecIfc();
			ExtendedPartService extendservice = (ExtendedPartService) PublishHelper
					.getEJBService("ExtendedPartService");
			if (primarypart != null) {
				extendservice.populateBaseline(primarypart, newBaseline, spec);

			}
			transaction.commit();

		} catch (QMException e) {

			transaction.rollback();
			e.printStackTrace();
			errorLog("���ߴ��������ʧ�ܣ���ͨ�������־�ļ��鿴������Ϣ");
		}

	}

	/**
	 * �Ӳ��������й��˳�����ʧ�ܵ��㲿��
	 * 
	 * @param parts
	 * @return
	 */
	private Group filterateErrorParts(Group parts) {
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>enter PublishErrorProcessor.filterateErrorParts,parts amount before filterate: "
							+ parts.getElementCount());
		Group result = new Group();
		if (parts != null) {
			Enumeration enu = parts.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				if (PublishHelper.VERBOSE)
					System.out.println("=====>part need create: "
							+ (String) ele.getValue("num"));
				if (this.errorParts.contains(((String) ele.getValue("num"))
						.trim())) {
					if (PublishHelper.VERBOSE)
						System.out.println("<======is error part!");
					result.addElement(ele);
				}
			}
		}
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>leave PublishErrorProcessor.filterateErrorParts,parts amount after filterate: "
							+ result.getElementCount());
		return result;
	}

	/**
	 * �Ӳ��������й��˳�����ʧ�ܵ��ĵ�
	 * 
	 * @param docs
	 * @return
	 */
	private Group filterateErrorDocs(Group docs) {
		Group result = new Group();
		if (docs != null) {
			Enumeration enu = docs.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				if (this.errorDocs.contains(((String) ele.getValue("num"))
						.trim())) {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	/**
	 * �Ӳ��������й��˳�����ʧ�ܵ�EPM�ĵ�
	 * 
	 * @param epms
	 * @return
	 */
	private Group filterateErrorEpms(Group epms) {
		Group result = new Group();
		if (epms != null) {
			Enumeration enu = epms.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				if (this.errorEpms.contains(((String) ele.getValue("num"))
						.trim())) {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	/**
	 * �����㲿���ṹ�����顣�����ڵ���ʱ����ʧ�ܣ�����Ҫ�ؽ����Ӽ�����ʧ�ܲ����Ӽ����½��������޶�ʱ��������Ҫ�ؽ���
	 * 
	 * @param partUsageLink
	 * @param partsCreateGrp
	 * @return
	 */
	private Group filteratePartUsageLink(Group partUsageLink) {
		Group result = new Group();
		if (partUsageLink != null) {
			Enumeration enu = partUsageLink.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				String parent = ((String) ele.getValue("parent")).trim();
				String child = ((String) ele.getValue("child")).trim();
				if (this.errorParts.contains(parent)
						|| (this.errorParts.contains(child) && !PublishHelper
								.hasPartMaster(child))) {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	private Group filteratePartDescribeLink(Group partDescribeLink) {
		Group result = new Group();
		if (partDescribeLink != null) {
			Enumeration enu = partDescribeLink.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				String partNum = ((String) ele.getValue("partNum")).trim();
				String docNum = ((String) ele.getValue("docNum")).trim();
				if (this.errorParts.contains(partNum)
						|| this.errorDocs.contains(docNum)) {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	private Group filteratePartBuildLink(Group partBuildLink) {
		Group result = new Group();
		if (partBuildLink != null) {
			Enumeration enu = partBuildLink.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				String partNum = ((String) ele.getValue("partNum")).trim();
				String docNum = ((String) ele.getValue("docNum")).trim();
				if (this.errorParts.contains(partNum)
						|| this.errorEpms.contains(docNum)) {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	private Group getReviseParts(Group reviseParts) {
		Group result = new Group();
		if (reviseParts != null) {
			Enumeration enu = reviseParts.getElements();
			while (enu.hasMoreElements()) {
				Element ele = (Element) enu.nextElement();
				if (this.errorParts.contains(((String) ele.getValue("num"))
						.trim())) {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	private void loadErrorObjects() {
		try {
			String filename = ERROR_PATH + this.noteNum + ".log";
			File file = new File(filename);
			if (file.exists() && file.isFile()) {
				if (PublishHelper.VERBOSE)
					System.out
							.println("start read error file******************************************"
									+ filename);
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
				String s = null;
				while ((s = reader.readLine()) != null) {
					if (PublishHelper.VERBOSE)
						System.out.println("AAAA" + s);
					String number = s.substring(8);
					if (s.startsWith("prt$$$$$")) {
						this.errorParts.add(number);
					} else if (s.startsWith("doc$$$$$")) {
						this.errorDocs.add(number);
					} else if (s.startsWith("epm$$$$$")) {
						this.errorEpms.add(number);
					}
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
