/**
 * SS1 ����ļ������������߼� jiahx 2012-12-10
 * SS2 ��ͼ���ֻ��ʾ֧�ֵļ�����������඼����ʾ�� liunan 2014-5-4
 * SS3 ����ʱ���¼�� liunan 2014-11-20
 * SS4 ����windchill����10.2��������epm�������ļ�������part�ϡ� liunan 2015-8-21
 * SS5 û��ͼҲҪ��ʾͼֽ���ͳ�ơ� liunan 2015-11-25 2017-7-25
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
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemInfo;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Request;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
//CCBegin SS3
import java.text.SimpleDateFormat;
//CCEnd SS3
public class BolbPublishResultGenerator {

	private static String HTML_PATH;
	
	//CCBegin SS3
	private static SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS");
	//CCEnd SS3
	
	static {
		try {
			HTML_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.html.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//CCBegin SS1
	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
	//CCEnd SS1

	Script myScript;

	String noteNum = "";

	int succeedAmount = 0;

	int failedAmount = 0;

	StringBuffer buffer = new StringBuffer();

	private String[] resultColumn = { "", "���", "����" };

	public BolbPublishResultGenerator(Script script) {
		this.myScript = script;
		Command cmd = myScript.getCommand();
		noteNum = cmd.paramValue("NOTENUMBER").trim();
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
	public void writeFile(StringBuffer buffer, String path) {
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(buffer.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateBolbPublishResult() {
		//liunan 2009-09-03if (PublishHelper.VERBOSE)
			System.out
					.println("=====>enter BolbPublishResultGenerator.generateBolbPublishResult");
		//CCBegin by liunan 2009-07-16
		//System.out.println("lntest=====>enter BolbPublishResultGenerator.generateBolbPublishResult");
		try {
		//CCBegin by liunan 2009-07-16
		Group blobPublishResult = myScript.getGroupIn("BlobPublishResult");
		if(blobPublishResult==null||blobPublishResult.getElementCount()==0)
		{			
			//CCBegin by liunan 2008-09-03
			//Ϊ��������ӿ��ء�
			//liunan 2009-09-03if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
			System.out
			  .println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult,element amount is zero!");
			//CCBegin SS5
			//return;
			if(blobPublishResult==null)
			{
				blobPublishResult = new Group();
			}
			//CCEnd SS5
		}
		Enumeration enumeration = blobPublishResult.getElements();
		for (int i = 0; i < blobPublishResult.getElementCount(); i++) {
			Element ele = (Element) enumeration.nextElement();
			String result = (String) ele.getValue("result");
			//CCBegin by liunan 2011-10-18 �޸����β�����ʾ���ݺ� �����������⡣
			String num = ((String) ele.getValue("num")).trim();
			
			//����Ҫ�����Ͷ���Ҳ�޷�ת�����������͵��ĵ�����ʾ���
			//Ŀǰ�����ĵ��У� tif CATPart CATDrawing model
			if(!result.trim().equalsIgnoreCase("true"))
			{
				if(num.endsWith("(tif)")||num.endsWith("(CATPart)")||num.endsWith("(CATDrawing)")
				   //||num.endsWith("(model)"))
				   ||num.endsWith("(model)")||num.endsWith("(hex)"))//liunan 2011-12-22 ����hex���͡�
				{
					continue;
				}
				//CCBegin by liunan 2011-11-02 ���epm �ı������ G01���� 5205G01-A95���ͱ�ʾ���߼��ܳɣ�û��ͼ��Ҳ�Ͳ�ͳ�ƽ����
				if(isLogic(num))
				{
					continue;
				}
				//CCEnd by liunan 2011-11-02
				//CCBegin SS2
				if(num.endsWith("(EPM)")||num.endsWith("(����)")||num.endsWith("(���)")||num.toLowerCase().endsWith("(ol)")||num.toLowerCase().endsWith("(ed)")||num.toLowerCase().endsWith("(pdf)"))
				{
					//��Ҫ������
				}
				else
				{
					continue;
				}
				//CCEnd SS2
			}
			//CCEnd by liunan 2011-10-18
			
			if (result.trim().equalsIgnoreCase("true")) {
				this.succeedAmount++;
			} else {
				this.failedAmount++;
			}
		}
		String filename = "";
		//CCBegin SS5
		//if (succeedAmount + failedAmount > 0) {
		//CCEnd SS5
			writeTableHead(buffer);
			writeSubHead(buffer, "");
			this.itemBegin(buffer);
			int i = resultColumn.length;
			for (int j = 0; j < i; j++)
				writeColum(buffer, resultColumn[j], "black");
			this.itemEnd(buffer);
			generateContent(blobPublishResult);
			tableEnd(buffer);
			writeHtmlEnd(buffer);
			//CCBegin by liunan 2009-09-03
			//�޸�ͼֽ�������ͳ���ļ�������
			//filename = HTML_PATH + noteNum + ".html";
			filename = HTML_PATH + noteNum + "_blob.html";
			//CCEnd by liunan 2009-09-03
			writeFile(buffer, filename);
			System.out.println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult writeFile over and filename:"+filename);
		//CCBegin SS5
		//}
		//CCBegin by liunan 2012-02-29 ������������ĵ������ǲ���Ҫ��ʾ�ģ���ֱ�ӷ��ء�
		//if(succeedAmount==0 && failedAmount==0)
		//{
			//System.out.println("���������ĵ���������Ҫ��ʾ���򲻹��졶ͼֽ�������ͳ��.html���ļ���");
			//return;
		//}
		//CCEnd SS5
		//CCEnd by liunan 2012-02-29
		//CCBegin by liunan 2009-07-16
		//Դ����
		//try {
		//CCBegin by liunan 2009-07-16
			DocInfo docInfo = PublishHelper
					.getDocInfoByNumber(getConfirmationNumber(noteNum));
			ContentService cser = (ContentService) PublishHelper
					.getEJBService("ContentService");
			if (docInfo == null) {	
			//CCBegin by liunan 2008-09-03
			//Ϊ��������ӿ��ء�
			//liunan 2009-09-03if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
				System.out.println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult,���ݲ���֪ͨ��ţ�"+getConfirmationNumber(noteNum)+"û���ҵ���Ӧ�����ݷ������ͳ���ĵ�");
			return;
			} else {
				File file = new File(filename);
				//CCBegin SS1
				if(fileVaultUsed)
				{
					ContentClientHelper contentClientHelper = new ContentClientHelper();
					ApplicationDataInfo app = contentClientHelper.requestUpload(file);
					app.setFileName("ͼֽ�������ͳ��" + ".html");
					app.setUploadPath(filename);
					app.setFileSize(file.length());
					app = (ApplicationDataInfo) cser
					.uploadContent((FormatContentHolderIfc) docInfo, app);
				}
				else
				{
					ApplicationDataInfo app = new ApplicationDataInfo();
					app.setFileName("ͼֽ�������ͳ��" + ".html");
					app.setUploadPath(filename);
					app.setFileSize(file.length());
					ApplicationDataInfo datainfo = (ApplicationDataInfo) cser
					.uploadContent((FormatContentHolderIfc) docInfo, app);
					String streamid = datainfo.getStreamDataID();
					DataInputStream dataInputStream = null;
					byte[] data = null;
					data = new byte[(int) file.length()];
					dataInputStream = new DataInputStream(new FileInputStream(file));
					dataInputStream.read(data);
					dataInputStream.close();
					StreamUtil.writeData(streamid, data);
					System.out.println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult StreamUtil.writeData over!");
				}
				//CCEnd SS1
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//liunan 2009-09-03if (PublishHelper.VERBOSE)
			System.out
					.println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult!");
	}

	private void generateContent(Group blobPublishResult) {
		Enumeration enumeration = blobPublishResult.getElements();
		//CCBegin by liunan 2011-10-18 �޸����β�����ʾ���ݺ� �����������⡣
		int notNum = 0;
		//CCEnd by liunan 2011-10-18
		for (int i = 0; i < blobPublishResult.getElementCount(); i++) {
			Element ele = (Element) enumeration.nextElement();
			String num = ((String) ele.getValue("num")).trim();
			String result = (String) ele.getValue("result");
			
			//CCBegin by liunan 2011-10-12 ����Ҫ�����Ͷ���Ҳ�޷�ת�����������͵��ĵ�����ʾ���
			//Ŀǰ�����ĵ��У� tif CATPart CATDrawing model
			if(!result.trim().equalsIgnoreCase("true"))
			{
				if(num.endsWith("(tif)")||num.endsWith("(CATPart)")||num.endsWith("(CATDrawing)")
				   //||num.endsWith("(model)"))
				   ||num.endsWith("(model)")||num.endsWith("(hex)"))//liunan 2011-12-22 ����hex���͡�
				{
					//CCBegin by liunan 2011-10-18 �޸����β�����ʾ���ݺ� �����������⡣
					notNum ++;
					//CCEnd by liunan 2011-10-18
					continue;
				}
				//CCBegin by liunan 2011-11-02 ���epm �ı������ G01���� 5205G01-A95���ͱ�ʾ���߼��ܳɣ�û��ͼ��Ҳ�Ͳ�ͳ�ƽ����
				if(isLogic(num))
				{
					notNum ++;
					continue;
				}
				//CCEnd by liunan 2011-11-02
				//CCBegin SS2
				if(num.endsWith("(EPM)")||num.endsWith("(����)")||num.endsWith("(���)")||num.toLowerCase().endsWith("(ol)")||num.toLowerCase().endsWith("(ed)")||num.toLowerCase().endsWith("(pdf)"))
				{
					//��Ҫ������
				}
				else
				{
					continue;
				}
				//CCEnd SS2
			}
			//CCEnd by liunan 2011-10-12
			
			itemBegin(buffer);
			if (result.trim().equalsIgnoreCase("true")) {
				//CCBegin by liunan 2011-10-18 �޸����β�����ʾ���ݺ� �����������⡣
				//writeColum(buffer, "" + (i + 1), "black");
				writeColum(buffer, "" + (i + 1 - notNum), "black");
				//CCEnd by liunan 2011-10-18
				writeColum(buffer, num, "black");
				writeColum(buffer, "ͼֽ�����ɹ�", "black");
				this.succeedAmount++;
			} else {
				//CCBegin by liunan 2011-10-18 �޸����β�����ʾ���ݺ� �����������⡣
				//writeColum(buffer, "" + (i + 1), "red");
				writeColum(buffer, "" + (i + 1 - notNum), "red");
				//CCEnd by liunan 2011-10-18
				writeColum(buffer, num, "red");
				writeColum(buffer, "ͼֽ����ʧ��", "red");
				this.failedAmount++;
			}
			itemEnd(buffer);
		}
	}
	
	//CCBegin by liunan 2011-11-02 
	//�߼��ܳ��жϷ��������epm �ı������ G01���� 5205G01-A95���ͱ�ʾ���߼��ܳɣ�û��ͼ��Ҳ�Ͳ�ͳ�ƽ����
	private boolean isLogic(String num)
	{
		//�ж����ݣ����ǰ��λ�����֣�Ȼ����G01�ģ��͵������߼��ܳɡ�
		boolean digitFlag = true;
		for(int j=0;j<4;j++)
		{
			if(!java.lang.Character.isDigit(num.charAt(j)))
			{
				digitFlag = false;
				break;
			}
		}
		if(digitFlag&&num.substring(4,7).equals("G01"))
		{
			return true;
		}
		return false;
	}
	//CCEnd by liunan 2011-11-02
				
	private String getConfirmationNumber(String noteNumber) {
		return "CONFIRMATION-" + noteNumber;
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
		buffer.append("<TITLE> ͼֽ�������ͳ�� </TITLE>\n");
		buffer
				.append("<META http-equiv=\"Content-Type\" content=\"text/html; charset=chinese\">\n");
		buffer.append("</HEAD>\n");
		buffer.append("<BODY bgcolor = \"#EBEBEB\">\n");
		buffer.append("<P>\n");
		buffer.append("<P>\n");
		buffer.append("<table width=\"100%\" border=\"0\" align=\"center\">");
		//CCBegin SS3
		buffer
				//.append("<tr><td height=\"44\" colspan=\"2\" ><div align=\"center\"><font face=\"����_GB2312\" size=\"5\">ͼֽ�������ͳ��</font></div></td></tr>");
		    .append("<tr><td height=\"44\" colspan=\"2\" ><div align=\"center\"><font face=\"����_GB2312\" size=\"5\">ͼֽ�������ͳ��" + logDateFormat.format(new java.util.Date()) + "</font></div></td></tr>");
		//CCEnd SS3
		//CCBegin by liunan 2010-12-24 ��Ӷ�ͼֽ���͵�˵��
		//CCBegin SS4
		//buffer.append("<tr><td height=\"30\" ><div align=\"center\"><font face=\"����_GB2312\" size=\"5\" color=\"red\">�������ֶα�ʾ��ͼֽ���ͣ��㲿������������EPM�ĵ���EPM������ͨ�ĵ�����¼���ļ���׺��Ŀǰֻ����ol��ed��pdf�͵���ĵ���</font></div></td></tr>");		
		buffer.append("<tr><td height=\"30\" colspan=\"2\" ><div align=\"center\"><font face=\"����_GB2312\" size=\"5\" color=\"red\">�������ֶα�ʾ��ͼֽ���ͣ��㲿����pdf�����㲿�������ԣ�����ͨ�ĵ�����¼���ļ���׺��Ŀǰֻ����ol��ed��pdf�͵���ĵ���</font></div></td></tr>");		
		//CCEnd SS4
		//CCEnd by liunan 2010-12-24
		buffer
				.append("<tr><td height=\"30\" width=\"50%\" align=\"center\">�ɹ�����");
		buffer.append(this.succeedAmount);
		buffer
				.append("</td><td height=\"30\" width=\"50%\"align=\"center\">ʧ������");
		buffer.append(this.failedAmount);
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
