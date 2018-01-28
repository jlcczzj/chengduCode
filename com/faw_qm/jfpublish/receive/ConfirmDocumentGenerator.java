/**
 * SS1 ����ļ������������߼� 2013-12-10
 * SS2 A004-2017-3608 Ϊָ�����̣�ָ����ɫ�����ò����ߡ� liunan 2017-9-27
 */
package com.faw_qm.jfpublish.receive;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

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
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Request;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;

//CCBegin by liunan 2008-08-07
import java.util.Vector;
//CCEnd by liunan 2008-08-07
//CCBegin SS2
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
//CCEnd SS2

public class ConfirmDocumentGenerator {
	private static String HTML_PATH;
	static {
		try {
			HTML_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.html.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Script myScript;

	HashMap createPartAndElement = new HashMap();

	HashMap revisePartAndElement = new HashMap();

	HashMap partAndDocs = new HashMap();

	HashMap partAndChilds = new HashMap();

	HashMap createDocAndElement = new HashMap();

	HashMap reviseDocAndElement = new HashMap();

	String primaryPartNum = null;

	String noteNum = "";

	StringBuffer buffer = new StringBuffer();

	private String[] partColumn = { "", "���", "����", "�汾", "״̬" };
	
	//CCBegin SS1
	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
	//CCEnd SS1

	public ConfirmDocumentGenerator(String xmlFileName) {
		Request req = new Request(RequestIn.readRequest2(xmlFileName));
		this.myScript = req.getScript();
		Command cmd = myScript.getCommand();
		primaryPartNum = cmd.paramValue("PRIMARYPART");
		noteNum = cmd.paramValue("NOTENUMBER").trim();
	}

	public ConfirmDocumentGenerator(Script script) {
		this.myScript = script;
		Command cmd = myScript.getCommand();
		primaryPartNum = cmd.paramValue("PRIMARYPART");
		noteNum = cmd.paramValue("NOTENUMBER").trim();
	}

	private void init() {
		if (PublishHelper.VERBOSE)
			System.out.println("=====>enter ConfirmDocumentGeneator.init().");
		Group partsCreateGrp = myScript.getGroupIn("CreateParts");
		Enumeration enu = partsCreateGrp.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			this.createPartAndElement.put(
					((String) ele.getValue("num")).trim(), ele);
		}
		if (PublishHelper.VERBOSE)
			System.out.println("=====>create parts amount: "
					+ createPartAndElement.size());
		Group partsReviseGrp = myScript.getGroupIn("ReviseParts");
		enu = partsReviseGrp.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			this.revisePartAndElement.put(
					((String) ele.getValue("num")).trim(), ele);
		}
		if (PublishHelper.VERBOSE)
			System.out.println("=====>revise parts amount: "
					+ revisePartAndElement.size());
		Group docsCreateGrp = myScript.getGroupIn("CreateDocs");
		enu = docsCreateGrp.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			this.createDocAndElement.put(((String) ele.getValue("num")).trim(),
					ele);
		}
		if (PublishHelper.VERBOSE)
			System.out.println("=====>create doc amount: "
					+ createDocAndElement.size());
		Group docsReviseGrp = myScript.getGroupIn("ReviseDocs");
		enu = docsReviseGrp.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			this.reviseDocAndElement.put(((String) ele.getValue("num")).trim(),
					ele);
		}
		if (PublishHelper.VERBOSE)
			System.out.println("=====>revise doc amount: "
					+ reviseDocAndElement.size());
		Group partUsageLinksGrp = myScript.getGroupIn("PartUsageLinks");
		enu = partUsageLinksGrp.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			String parent = ((String) ele.getValue("parent")).trim();
			String child = ((String) ele.getValue("child")).trim();
			if (this.partAndChilds.get(parent) == null) {
				ArrayList childs = new ArrayList();
				childs.add(child);
				this.partAndChilds.put(parent, childs);
			} else {
				ArrayList childs = (ArrayList) this.partAndChilds.get(parent);
				childs.add(child);
			}
		}
		if (PublishHelper.VERBOSE)
			System.out.println("=====>part childs links amount: "
					+ partAndChilds.size());
		Group partDescsGrp = myScript.getGroupIn("PartDescribeLinks");
		enu = partDescsGrp.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			String partNum = ((String) ele.getValue("partNum")).trim();
			String docNum = ((String) ele.getValue("docNum")).trim();
			if (PublishHelper.VERBOSE)
				System.out.println("=====>part doc link, part: " + partNum
						+ ",doc: " + docNum);
			if (this.partAndDocs.get(partNum) == null) {
				ArrayList docs = new ArrayList();
				docs.add(docNum);
				this.partAndDocs.put(partNum, docs);
			} else {
				ArrayList docs = (ArrayList) this.partAndDocs.get(partNum);
				docs.add(docNum);
			}
		}
		if (PublishHelper.VERBOSE)
			System.out.println("=====>part doc links amount: "
					+ partAndDocs.size());

		if (PublishHelper.VERBOSE)
			System.out.println("=====>leave ConfirmDocumentGeneator.init().");
	}

	/**
	 * �γ��ļ�
	 * 
	 * @param buffer
	 *            StringBuffer �ַ�����
	 * @param path
	 *            String �����ļ���·��
	 * @throws Exception
	 */
	public void writeFile(StringBuffer buffer, String path)

	{
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(buffer.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateConfirmDocument() {
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>enter ConfirmDocumentGenerator.generateConfirmDocument.");
		Group partsCreateGrp = myScript.getGroupIn("CreateParts");
		Group partsReviseGrp = myScript.getGroupIn("ReviseParts");
		int partsAmount = 0;
		if (partsCreateGrp != null) {
			partsAmount += partsCreateGrp.getElementCount();
		}
		if (partsReviseGrp != null) {
			partsAmount += partsReviseGrp.getElementCount();
		}
		String filename = "";
		// �������Ҫ�������㲿�����췢�������嵥������ֻ����ȷ��֪ͨ�飬���ϴ�������
		if (partsAmount > 0) {
			init();
			writeTableHead(buffer);
			writeSubHead(buffer, "");
			this.itemBegin(buffer);
			int i = partColumn.length;
			for (int j = 0; j < i; j++)
				writeColum(buffer, partColumn[j], "black");
			this.itemEnd(buffer);
			Collection contents = prepareConfirmDocumentContents();
			if (PublishHelper.VERBOSE)
				System.out.println("=====>content block amout: "
						+ contents.size());
			if (contents != null && contents.size() != 0) {
				Iterator iter = contents.iterator();
				while (iter.hasNext()) {
					ConfirmDocumentContent content = (ConfirmDocumentContent) iter
							.next();
					generateContentBlock(content);
				}
			}
			tableEnd(buffer);
			writeHtmlEnd(buffer);
			filename = HTML_PATH + noteNum + ".html";
			writeFile(buffer, filename);
		} else {
			writeHtmlFileNoParts(buffer);
			filename = HTML_PATH + noteNum + ".html";
			writeFile(buffer, filename);
		}
		// �����ĵ��������ɵ��ļ���Ϊ�ĵ�����Ҫ�ļ�
		DocInfo docInfo = null;
		PersistService ps = null;
		StandardDocService ser = null;
		ContentService cser = null;
		UsersService userservice = null;
		SessionService sservice = null;
		UserIfc user = null;
		//CCBegin by liunan 2010-01-29
		String tempNum = "";
		//CCEnd by liunan 2010-01-29
		try {
			docInfo = PublishHelper
					.getDocInfoByNumber(getConfirmationNumber(noteNum));
			ps = (PersistService) EJBServiceHelper.getService("PersistService");
			ser = (StandardDocService) PublishHelper
					.getEJBService("StandardDocService");
			cser = (ContentService) PublishHelper
					.getEJBService("ContentService");
			sservice = (SessionService) PublishHelper
					.getEJBService("SessionService");
			userservice = (UsersService) PublishHelper
					.getEJBService("UsersService");
			// user = sservice.getCurUserInfo();
			user = userservice.getUserValueInfo("rdc");// yanqi-20070709,�û�ֱ������Ϊ��������
			String docCfBsoID = PublishHelper.getDocCf("������\\���ݽ���ȷ��֪ͨ��");
			DocFormData formData = new DocFormData();
			if (docInfo == null) {
				formData
						.setDocumentAttribute("docName", "���ݽ���ȷ��֪ͨ�飭" + noteNum);
				formData.setDocumentAttribute("docNum",
						getConfirmationNumber(noteNum));// �����ĵ����
				//CCBegin by liunan 2011-03-17 ���ݽ��Ҫ���޸�������������
				//formData.setDocumentAttribute("lifecycleTemplate",
						//"���ݽ���ȷ��֪ͨ����������");
				formData.setDocumentAttribute("lifecycleTemplate",
						"���ݽ�����������һ");
				//CCEnd by liunan 2011-03-17		
				//CCBegin by liunan 2010-01-21 ����ǲ���֪ͨ�飬�򽫲����㲿����Ϣ��ӵ�˵����Ϣ�С�
				//Դ����
				//formData.setDocumentAttribute("contDesc", noteNum);
				Command cmd = myScript.getCommand();				
				String publishform = cmd.paramValue("PUBLISHFORM");				
				if(publishform.equals("����֪ͨ��"))
				{
					String cyList = cmd.paramValue("CAIYONGPARTSLIST");
					System.out.println("cyList===="+cyList);
					tempNum = noteNum + PublishHelper.doCaiYongList(cyList);
				}
				else
				{
					tempNum = noteNum;
				}
				System.out.println("tempNum===="+tempNum);
				//CCBegin by liunan 2010-01-28 ���˵�����ݹ���������һ��applicationData����������
				if (tempNum.length() < 2000)
				{
					formData.setDocumentAttribute("contDesc", tempNum);
				}
				else
				{
					formData.setDocumentAttribute("contDesc", noteNum+PublishHelper.CHANGEORDER_DES_PART_SEPERATOR+"TOOLONG");
				}
				//CCEnd by liunan 2010-01-28
				//CCEnd by liunan 2010-01-21
				formData.setDocumentAttribute("createDept", "ϵͳ");// ���ô�����λ
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				formData.setDocumentAttribute("folder",
						"\\Root\\���ݽ�������\\���ݽ���ȷ��֪ͨ��");// �������ϼ�
				//CCBegin by liunan 2009-12-23 �޸�״̬�ı�ʾ��ʽ��		
				formData.setDocumentAttribute("lifeCState", LifeCycleState
						.toLifeCycleState("AUDITING"));// ������������״̬
				if (user != null) {
					formData.setDocumentAttribute("iterationCreator", user
							.getBsoID());
					formData.setDocumentAttribute("iterationModifier", user
							.getBsoID());
					formData.setDocumentAttribute("aclOwner", user.getBsoID());
					formData.setDocumentAttribute("creator", user.getBsoID());
				}
        
        //CCBegin by liunan 2008-08-07
        //�ְ汾doc�������Ѿ�����createDoc�����������ص���vector���м䴦�����һ�£�
        //�˴�ֱ�ӵ������°汾����Ȼ��õ�docInfo��
        //ԭ��������
				//docInfo = (DocInfo) ser.createDoc(formData);
				Vector docvec = (Vector) ser.createDoc(formData);
				docInfo = (DocInfo)docvec.elementAt(0);
				//CCBegin by liunan 2008-08-07
				
				//CCBegin SS2
				String hqz = cmd.paramValue("JFHUIQIANZHE");
				System.out.println("���л�ǩ�� 11===="+hqz);
				if(hqz!=null&&!hqz.equals("")&&hqz.length()>3)
				{
					hqz = hqz.substring(0,hqz.indexOf("_jf"));
					QMQuery queryspec = new QMQuery("User");
					queryspec.addCondition(new QueryCondition("usersName", "=", hqz));
					Collection result = ps.findValueInfo(queryspec, false);
					Iterator iteresult = result.iterator();
					if (iteresult.hasNext())
					{
						UserIfc hquser = (UserIfc) iteresult.next();
						docInfo.setIterationNote(hquser.getBsoID());
						docInfo = (DocInfo)ps.saveValueInfo(docInfo);
					}
				}
				//CCEnd SS2
				
				docInfo = (DocInfo) ps.refreshInfo(docInfo);
			} else {
				//CCBegin by liunan 2009-12-23 �޸�״̬�ı�ʾ��ʽ��
				docInfo.setLifeCycleState(LifeCycleState.toLifeCycleState("RECEIVING"));
				//CCEnd by liunan 2009-12-23
				docInfo = (DocInfo) ((StandardDocService) PublishHelper
						.getEJBService("StandardDocService")).reviseDoc(
						docInfo, formData);
				//CCBegin by liunan 2010-04-01 ֻ�������εķ��������ɾ����һ�汾�����и����ļ���
				/*ContentItemInfo item = (ContentItemInfo) cser
						.getPrimaryContent(docInfo);
				if (item instanceof ApplicationDataInfo) {
					cser.deleteApplicationData(docInfo,
							(ApplicationDataInfo) item);
				}*/				
				
				//CCBegin SS2
				Command cmd = myScript.getCommand();
				String hqz = cmd.paramValue("JFHUIQIANZHE");
				System.out.println("���л�ǩ�� 22===="+hqz);
				if(hqz!=null&&!hqz.equals("")&&hqz.length()>3)
				{
					hqz = hqz.substring(0,hqz.indexOf("_jf"));
					QMQuery queryspec = new QMQuery("User");
					queryspec.addCondition(new QueryCondition("usersName", "=", hqz));
					Collection result = ps.findValueInfo(queryspec, false);
					Iterator iteresult = result.iterator();
					if (iteresult.hasNext())
					{
						UserIfc hquser = (UserIfc) iteresult.next();
						docInfo.setIterationNote(hquser.getBsoID());
						docInfo = (DocInfo)ps.saveValueInfo(docInfo);
					}
				}
				//CCEnd SS2
				
				Vector v = cser.getContents(docInfo);
				ContentItemInfo item;
				for (Iterator iter = v.iterator(); iter.hasNext();) 
				{
					item = (ContentItemInfo) iter.next();
					if (item instanceof ApplicationDataInfo) 
					{
						cser.deleteApplicationData(docInfo, (ApplicationDataInfo) item);
					}
				}
				//CCEnd by liunan 2010-04-01
			}
			//CCBegin by liunan 2010-01-28 ��������㲿�����ࣨ˵������̫�����򱣴��applicationData��
			if (tempNum.length() >= 2000) 
			{
					byte fileContent[] = tempNum.getBytes();
					//CCBegin SS1
					if(fileVaultUsed)
					{
						ContentClientHelper helper = new ContentClientHelper();
						ApplicationDataInfo appDataInfo = helper.requestUpload(fileContent);
						appDataInfo.setFileName("caiyongdescription.txt");
						appDataInfo.setFileSize(fileContent.length);	
						appDataInfo = (ApplicationDataInfo) cser.uploadContent(
								(FormatContentHolderIfc) docInfo, appDataInfo);
					}
					else
					{
						ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
						appDataInfo.setFileName("caiyongdescription.txt");
						appDataInfo.setFileSize(fileContent.length);	
						appDataInfo = (ApplicationDataInfo) cser.uploadContent(
								(FormatContentHolderIfc) docInfo, appDataInfo);
						StreamUtil.writeData(appDataInfo.getStreamDataID(), fileContent);
					}
					//CCEnd SS1
			}
			//CCEnd by liunan 2010-01-28
			// �������Ҫ�������㲿��,���ϴ����������嵥
			File file = new File(filename);
			//CCBegin SS1
			if(fileVaultUsed)
			{
				ContentClientHelper helper = new ContentClientHelper();
				ApplicationDataInfo app = helper.requestUpload(file);
				app.setFileName("���������嵥.html");
				app.setUploadPath(filename);
				app = (ApplicationDataInfo) cser
				.uploadPrimaryContent(docInfo, app);
			}
			else
			{
				ApplicationDataInfo app = new ApplicationDataInfo();
				app.setFileName("���������嵥.html");
				app.setUploadPath(filename);
				app.setFileSize(file.length());
				ApplicationDataInfo datainfo = (ApplicationDataInfo) cser
				.uploadPrimaryContent(docInfo, app);
				String streamid = datainfo.getStreamDataID();
				DataInputStream dataInputStream = null;
				byte[] data = null;
				data = new byte[(int) file.length()];
				dataInputStream = new DataInputStream(new FileInputStream(file));
				dataInputStream.read(data);
				dataInputStream.close();
				StreamUtil.writeData(streamid, data);
			}
			//CCEnd SS1
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>leave ConfirmDocumentGenerator.generateConfirmDocument.");
	}

	private String getConfirmationNumber(String noteNumber) {
		return "CONFIRMATION-" + noteNumber;
	}

	private void generateContentBlock(ConfirmDocumentContent content) {
		writePartMessage(content);
		writeChildMessage(content);
		writeDocMessage(content);
	}

	private void writeChildMessage(ConfirmDocumentContent content) {
		Collection childs = content.getChildAndState().keySet();
		if (childs != null && childs.size() != 0) {
			int size = childs.size();
			Iterator iter = childs.iterator();
			for (int i = 0; i < size; i++) {
				String child = (String) iter.next();
				String state = (String) content.getChildAndState().get(child);
				itemBegin(buffer);
				writeColum(buffer, "�Ӽ�" + (i + 1), "gray");
				writeColum(buffer, child, "gray");
				writeColum(buffer, "", "gray");
				writeColum(buffer, "", "gray");
				writeColum(buffer, state, "gray");
				itemEnd(buffer);
			}
		}
	}

	private void writeDocMessage(ConfirmDocumentContent content) {
		Collection docs = content.getDocs();
		if (docs != null && docs.size() != 0) {
			int size = docs.size();
			Iterator iter = docs.iterator();
			for (int i = 0; i < size; i++) {
				String doc = (String) iter.next();
				String docNumber;
				String docName;
				String sourceVersion;
				String state;
				if (this.createDocAndElement.get(doc) != null) {
					Element ele = (Element) this.createDocAndElement.get(doc);
					docNumber = ((String) ele.getValue("num")).trim();
					docName = (String) ele.getValue("name");
					sourceVersion = (String) ele.getValue("sourceVersion");
					state = ConfirmDocumentContent.STATE_CREATE;
				} else if (this.reviseDocAndElement.get(doc) != null) {
					Element ele = (Element) this.reviseDocAndElement.get(doc);
					docNumber = ((String) ele.getValue("num")).trim();
					docName = (String) ele.getValue("name");
					sourceVersion = (String) ele.getValue("sourceVersion");
					state = ConfirmDocumentContent.STATE_REVISE;
				} else {
					docNumber = doc;
					docName = "";
					sourceVersion = "";
					state = ConfirmDocumentContent.STATE_EXIST;
				}
				itemBegin(buffer);
				writeColum(buffer, "�����ĵ�", "gray");
				writeColum(buffer, docNumber, "gray");
				writeColum(buffer, docName, "gray");
				writeColum(buffer, sourceVersion, "gray");
				writeColum(buffer, state, "gray");
				itemEnd(buffer);
			}
		}
	}

	private void writePartMessage(ConfirmDocumentContent content) {
		itemBegin(buffer);
		writeColum(buffer, "", "purple");
		writeColum(buffer, content.getPartNumber(), "purple");
		writeColum(buffer, content.getPartName(), "purple");
		writeColum(buffer, content.getVersionInfo(), "purple");
		writeColum(buffer, content.getState(), "purple");
		itemEnd(buffer);
	}

	private Collection prepareConfirmDocumentContents() {
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>enter ConfirmDocumentGenerator.prepareConfirmDocumentContents.");
		Collection result = new ArrayList();
		Collection createParts = getConfirmDocumentContents(this.createPartAndElement);
		if (PublishHelper.VERBOSE)
			System.out.println("=====>create part content amount: "
					+ createParts.size());
		Collection reviseParts = getConfirmDocumentContents(this.revisePartAndElement);
		if (PublishHelper.VERBOSE)
			System.out.println("=====>revise part content amount: "
					+ reviseParts.size());
		result.addAll(createParts);
		result.addAll(reviseParts);
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>leave ConfirmDocumentGenerator.prepareConfirmDocumentContents.");
		return result;
	}

	private Collection getConfirmDocumentContents(HashMap numbersAndElements) {
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>enter ConfirmDocumentGenerator.getConfirmDocumentContents.");
		Collection result = new ArrayList();
		Collection parts = numbersAndElements.keySet();
		Iterator iter1 = parts.iterator();
		while (iter1.hasNext()) {
			String partNumber = (String) iter1.next();
			ConfirmDocumentContent content = new ConfirmDocumentContent();
			Element ele = (Element) numbersAndElements.get(partNumber);
			content.setPartNumber(partNumber);
			content.setPartName((String) ele.getValue("name"));
			content.setVersionInfo((String) ele.getValue("sourceVersion"));
			content.setState(this.getPartState(partNumber));
			Collection childs = (Collection) this.partAndChilds.get(partNumber);
			if (childs != null) {
				Iterator iter2 = childs.iterator();
				while (iter2.hasNext()) {
					String child = (String) iter2.next();
					content.addChild(child, this.getPartState(child));
				}
			}
			Collection docs = (Collection) this.partAndDocs.get(partNumber);
			if (docs != null) {
				Iterator iter3 = docs.iterator();
				while (iter3.hasNext()) {
					String doc = (String) iter3.next();
					content.addDoc(doc);
				}
			}
			result.add(content);
		}
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>leave ConfirmDocumentGenerator.getConfirmDocumentContents.");
		return result;
	}

	private String getPartState(String child) {
		if (this.createPartAndElement.containsKey(child)) {
			return ConfirmDocumentContent.STATE_CREATE;
		} else if (this.revisePartAndElement.containsKey(child)) {
			return ConfirmDocumentContent.STATE_REVISE;
		} else {
			return ConfirmDocumentContent.STATE_EXIST;
		}
	}

	private String getDocState(String doc) {
		if (this.createDocAndElement.containsKey(doc)) {
			return ConfirmDocumentContent.STATE_CREATE;
		} else if (this.reviseDocAndElement.containsKey(doc)) {
			return ConfirmDocumentContent.STATE_REVISE;
		} else {
			return ConfirmDocumentContent.STATE_EXIST;
		}
	}

	private void writeHtmlFileNoParts(StringBuffer buffer) {
		buffer.append("<HTML>\n");
		buffer.append("<HEAD>\n");
		buffer.append("<TITLE> ���������嵥 </TITLE>\n");
		buffer
				.append("<META http-equiv=\"Content-Type\" content=\"text/html; charset=chinese\">\n");
		buffer.append("</HEAD>\n");
		buffer.append("<BODY bgcolor = \"#EBEBEB\">\n");
		buffer.append("<P>\n");
		buffer.append("<P>\n");
		buffer.append("<table width=\"100%\" border=\"0\" align=\"center\">");
		buffer
				.append("<tr><td height=\"44\" colspan=\"2\" ><div align=\"center\"><font face=\"����_GB2312\" size=\"5\">�˴α�����漰�����������������������ϵͳ�У�</font></div></td></tr>");
		buffer.append("</table>");
		writeHtmlEnd(buffer);

	}

	/**
	 * дhtml��ͷ
	 * 
	 * @param buffer
	 *            StringBuffer �ַ�����
	 */
	private void writeTableHead(StringBuffer buffer) {
		buffer.append("<HTML>\n");
		buffer.append("<HEAD>\n");
		buffer.append("<TITLE> ���������嵥 </TITLE>\n");
		buffer
				.append("<META http-equiv=\"Content-Type\" content=\"text/html; charset=chinese\">\n");
		buffer.append("</HEAD>\n");
		buffer.append("<BODY bgcolor = \"#EBEBEB\">\n");
		buffer.append("<P>\n");
		buffer.append("<P>\n");
		buffer.append("<table width=\"100%\" border=\"0\" align=\"center\">");
		buffer
				.append("<tr><td height=\"44\" colspan=\"2\" ><div align=\"center\"><font face=\"����_GB2312\" size=\"5\">���η���������ͳ��</font></div></td></tr>");
		buffer
				.append("<tr><td height=\"30\" width=\"50%\" align=\"center\">�½����㲿������");
		buffer.append(this.createPartAndElement.size());
		buffer
				.append("</td><td height=\"30\" width=\"50%\"align=\"center\">�޶����㲿������");
		buffer.append(this.revisePartAndElement.size());
		buffer.append("</td></tr>");
		buffer
				.append("<tr><td height=\"30\" width=\"50%\" align=\"center\">�½��������ĵ�����");
		buffer.append(this.createDocAndElement.size());
		buffer
				.append("</td><td height=\"30\" width=\"50%\"align=\"center\">�޶��������ĵ�����");
		buffer.append(this.reviseDocAndElement.size());
		buffer.append("</td></tr></table>");
	}

	/**
	 * �����ݱ�������ӱ���
	 * 
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @param subHead
	 *            String �ӱ���
	 */
	private void writeSubHead(StringBuffer buffer, String subHead) {
		buffer.append("<h3 align=\"left\"><strong>");
		buffer.append(subHead);
		buffer.append("</strong></h3>");
		enter(buffer);
		buffer.append("<table width=\"100%\" border=\"1\">");
		enter(buffer);
	}

	/**
	 * �����ݱ��������
	 * 
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @param column
	 *            String ����
	 */
	private void writeColum(StringBuffer buffer, String column, String color) {
		if (column == null || column.equals("") || column.equals(" "))
			column = "&nbsp;";

		buffer.append("<td><div align=\"center\"><font color=" + color + ">");
		buffer.append(column);
		buffer.append("</font></div></td>");
		enter(buffer);

	}

	private void writeHtmlEnd(StringBuffer buffer) {

		buffer.append("<p><strong></strong></p>");
		enter(buffer);
		buffer.append("<p><strong></strong></p>");
		enter(buffer);
		buffer.append("<p>&nbsp;</p>");
		enter(buffer);
		buffer.append("<p><strong></strong></p>");
		enter(buffer);
		buffer.append("<p><strong></strong></p>");
		enter(buffer);
		buffer.append("<p align=\"left\"><strong></strong></p>");
		enter(buffer);
		buffer.append("<p align=\"left\"><strong></strong></p>");
		enter(buffer);
		buffer.append("<p align=\"left\"><strong></strong></p>");
		enter(buffer);
		buffer.append("<p align=\"left\">&nbsp;</p>");
		enter(buffer);
		buffer.append("</body>");
		enter(buffer);
		buffer.append("</html>");
		enter(buffer);

	}

	/**
	 * �س�
	 * 
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 */
	private void enter(StringBuffer buffer) {
		buffer.append("\n");
	}

	private void itemBegin(StringBuffer buffer) {
		buffer.append("<tr>");
		enter(buffer);
	}

	private void itemEnd(StringBuffer buffer) {
		buffer.append("</tr>");
		enter(buffer);
	}

	private void tableEnd(StringBuffer buffer) {
		buffer.append("</table>");
		enter(buffer);
	}

	public static void main(String[] args) {
	}

}
