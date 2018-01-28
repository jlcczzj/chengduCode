/**
 * SS1 添加文件服务器传输逻辑 jiahx 2013-12-10
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
			user = userservice.getUserValueInfo("rdc");// yanqi-20070709,用户直接设置为技术中心
			PublishHelper.setStateBusy();
			sservice.setAccessEnforced(false);
			//sservice.setAdministrator();
			// 记录数据来源（值为技术中心）
			// 记录从技术中心发布此数据时的日期
			publishDate = cmd.paramValue("PUBLISHDATE");
			// 记录以何种形式发布（采用通知书/更改通知书）
			publishForm = cmd.paramValue("PUBLISHFORM");
			// 记录采用通知书或更改请求的编号
			noteNum = cmd.paramValue("NOTENUMBER");
			primaryPartNum = cmd.paramValue("PRIMARYPART");
			errorWriter = new PrintWriter(new FileWriter(new File(ERROR_PATH
					+ noteNum + ".log"), true), true);
			logger = new PublishPartsLog(noteNum);
			log("进行数据发布的用户是：" + user.getUsersName());
			ps = (PersistService) PublishHelper.getEJBService("PersistService");
			// log(script.getVdb().toXML());
			// 读传入输入组信息
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
					partsReviseGrp);// 为了防止验证信息不准确，重新进行验证
			partsReviseGrp = this.filterateReviseParts(partsReviseGrp);// 为了防止验证信息不准确，重新进行验证
			docsCreateGrp = this.filterateCreateDocs(docsCreateGrp,
					docsReviseGrp);// 为了防止验证信息不准确，重新进行验证
			docsReviseGrp = this.filterateReviseDocs(docsReviseGrp);

			this.loadErrorObjects();

			partsCreateGrp = this.filterateErrorParts(partsCreateGrp);
			partsReviseGrp = this.filterateErrorParts(partsReviseGrp);// 为了防止验证信息不准确，重新进行验证
			epmCreateGrp = this.filterateErrorEpms(epmCreateGrp);// 为了防止验证信息不准确，重新进行验证
			epmReviseGrp = this.filterateErrorEpms(epmReviseGrp);
			docsCreateGrp = this.filterateErrorDocs(docsCreateGrp);// 为了防止验证信息不准确，重新进行验证
			docsReviseGrp = this.filterateErrorDocs(docsReviseGrp);

			partUsageLinksGrp = this.filteratePartUsageLink(partUsageLinksGrp);
			partDescsGrp = this.filteratePartDescribeLink(partDescsGrp);
			partEPMBuildsGrp = this.filteratePartBuildLink(partEPMBuildsGrp);

			SimpleDateFormat logDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd-HH:mm:ss.SSS");
			userLog("#进行数据接收的用户是：" + user.getUsersName());
			userLog("#本次发布数据接收的时间是："
					+ logDateFormat.format(new java.util.Date()));
			// 创建反馈信息

			log("创建Part");
			if (partsCreateGrp != null) {
				PartCreateDelegate del = new PartCreateDelegate(partsCreateGrp,
						partIBA, this);
				ResultReport res = del.process();
				log("创建创建part数据 RESURLT ***************");
				log(res);
			}

			// 创建Doc
			log("创建Doc");
			if (docsCreateGrp != null) {
				DocCreateDelegate del = new DocCreateDelegate(docsCreateGrp,
						this);
				ResultReport res = del.process();
				log("创建创建Doc数据 RESURLT ***************");
				log(res);
			}

			// 创建EPM文档
			//CCBegin by liunan 2009-12-07 不再发布EPM相关内容。
			log("创建EPM文档");
			if (epmCreateGrp != null) {
				EpmCreateDelegate del = new EpmCreateDelegate(epmCreateGrp,
						epmIBA, this);
				ResultReport res = del.process();
				log("创建EPM文档数据 RESULT ***************");
				log(res);

			}
			//CCEnd by liunan 2009-12-07
			// ---------------------------------------------------------------------------------
			// 修订Part
			log("修订Part");
			if (partsReviseGrp != null) {
				PartReviseDelegate del = new PartReviseDelegate(partsReviseGrp,
						partIBA, this);
				ResultReport res = del.process();
				log("修订Part数据***************");
				log(res);
			}

			// 修订Doc
			log("修订Doc");
			if (docsReviseGrp != null) {
				DocReviseDelegate del = new DocReviseDelegate(docsReviseGrp,
						this);
				ResultReport res = del.process();
				log("修订Doc数据 RESURLT ***************");
				log(res);

			}
			// 修订EPM文档
			//CCBegin by liunan 2009-12-07 不再发布EPM相关内容。
			log("修订EPM文档");
			try {
				if (epmReviseGrp != null) {
					EpmReviseDelegate del = new EpmReviseDelegate(epmReviseGrp,
							epmIBA, this);
					ResultReport res = del.process();
					log("修订EPM文档数据 RESULT ***************");
					log(res);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//CCEnd by liunan 2009-12-07

			// 创建Part结构
			RelationsDelegate relationsDel = new RelationsDelegate(this);
			log("创建Part结构");
			if (partUsageLinksGrp != null) {
				ResultReport res = relationsDel
						.savePartUsageLinks(partUsageLinksGrp);
				log("创建Part结构数据 RESULT ***************");
				log(res);

			}

			// 创建Part与Doc描述关系
			log("创建Part与Doc描述关系");
			if (partDescsGrp != null) {
				ResultReport res = relationsDel
						.savePartDescribeLinks(partDescsGrp);
				log("创建Part与Doc描述关系数据 RESULT ***************");
				log(res);

			}
				
			//CCBegin by liunan 2008-12-01
			// 创建Part与替换件关联关系
			log("创建Part与alternate关联关系");
			if (partAlternatesGrp != null) {
				ResultReport res = relationsDel
						.savePartAlternatesLinks(partAlternatesGrp);
				log("创建Part与替换件关联关系数据 RESULT ***************");
				log(res);
			}
			// 创建Part与结构替换件关联关系
			log("创建Part与subtitutes关联关系");
			if (partSubstitutesGrp != null) {
				ResultReport res = relationsDel
						.savePartSubtitutesLinks(partSubstitutesGrp);
				log("创建Part与结构替换件关联关系数据 RESULT ***************");
				log(res);
			}
			//CCEnd by liunan 2008-12-01				
			//processBaseLine();//anan want
			generateLog();
			//CCBegin by liunan 2008-11-14 取消rdc临时管理员权限。
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
		//CCBegin by liunan 2008-12-03 添加对替换件和结构替换件的支持。
		JieFangTemplateProcessor jfTemplateProcessor = new JieFangTemplateProcessor(
				path, user.getUsersName(), zong, sucnum, doczong, docsucnum,
				rezong, resucnum, redoczong, redocnum,
				linkzong - linksuc, altzong, altnum, subzong, subnum);
		//CCEnd by liunan 2008-12-03
		// 在指定目录生成日志的html文件
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
		// 通过文档管理日志文件
		// 通过文档管理日志文件
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
		 * PublishHelper.getDocCf("其它类\\数据发布接收日志"); } catch (QMException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); } DocFormData
		 * formData = null; try { formData = new DocFormData(); } catch
		 * (DocException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } if (docInfo == null) { if (noteNum == null ||
		 * noteNum.length() == 0) { noteNum = "changepublish"; }
		 * formData.setDocumentAttribute("docName", docnum); // 设置文档编号
		 * formData.setDocumentAttribute("docNum", docnum); // 设置创建单位
		 * formData.setDocumentAttribute("createDept", "技术中心"); // 设置文档类别
		 * formData.setDocumentAttribute("docCfBsoID", docCfBsoID); // 设置资料夹
		 * formData.setDocumentAttribute("folder", "\\Root\\数据接收其它\\数据发布日志"); //
		 * 设置生命周期 formData.setDocumentAttribute("lifecycleTemplate",
		 * "数据接收生命周期"); formData.setDocumentAttribute("contDesc", partsBaseline +
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
				appDataInfo.setFileName("错误恢复结果统计.html");
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
			appDataInfo.setFileName("错误恢复结果统计.html");
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
			System.out.println("************** 基线创建结束");
	}

	private void processBaseLine() throws QMException {
		QMTransaction transaction = new QMTransaction();
		transaction.begin();
		try {
			log("进行创建基线的用户是：" + user.getUsersName());
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
			ManagedBaselineInfo baseline = new ManagedBaselineInfo();// 将本次接收的零部件添加到该基线中
			baseline.setBaselineName("数据接收基线" + noteNum + seqNum);
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
			// 创建基线，并将本次发布的根部件按结构添加到该基线中
			seqNum = pservice.getNextSequence("baselineIdentity_seq");
			ManagedBaselineInfo newBaseline = new ManagedBaselineInfo();
			newBaseline.setBaselineName("零部件基线" + primaryPartNum + seqNum);
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
			// 获取当前用户的配置规范
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
			errorLog("基线创建或添加失败，可通过浏览日志文件查看发布信息");
		}

	}

	/**
	 * 从参数集合中过滤出保存失败的零部件
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
	 * 从参数集合中过滤出保存失败的文档
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
	 * 从参数集合中过滤出保存失败的EPM文档
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
	 * 过滤零部件结构关联组。父件在导入时保存失败，关联要重建；子件导入失败并且子件是新建而不是修订时，关联需要重建。
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
