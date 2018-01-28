/**
 * SS1 添加文件服务器传输逻辑 jiahx 2012-12-10
 * SS2 发图结果只显示支持的几种情况，其余都不显示。 liunan 2014-5-4
 * SS3 增加时间记录。 liunan 2014-11-20
 * SS4 汽研windchill升级10.2，不发布epm把中性文件发不到part上。 liunan 2015-8-21
 * SS5 没有图也要显示图纸结果统计。 liunan 2015-11-25 2017-7-25
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

	private String[] resultColumn = { "", "编号", "名称" };

	public BolbPublishResultGenerator(Script script) {
		this.myScript = script;
		Command cmd = myScript.getCommand();
		noteNum = cmd.paramValue("NOTENUMBER").trim();
	}

	/**
	 * 形成文件
	 * 
	 * @param buffer
	 *            StringBuffer 字符容器
	 * @param path
	 *            String 生成文件的路径
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
			//为输出语句添加开关。
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
			//CCBegin by liunan 2011-10-18 修改屏蔽不需显示内容后 计数错误问题。
			String num = ((String) ele.getValue("num")).trim();
			
			//不需要的类型而且也无法转换成所需类型的文档不显示结果
			//目前此类文档有： tif CATPart CATDrawing model
			if(!result.trim().equalsIgnoreCase("true"))
			{
				if(num.endsWith("(tif)")||num.endsWith("(CATPart)")||num.endsWith("(CATDrawing)")
				   //||num.endsWith("(model)"))
				   ||num.endsWith("(model)")||num.endsWith("(hex)"))//liunan 2011-12-22 新增hex类型。
				{
					continue;
				}
				//CCBegin by liunan 2011-11-02 如果epm 的编号中有 G01，如 5205G01-A95，就表示是逻辑总成，没有图，也就不统计结果。
				if(isLogic(num))
				{
					continue;
				}
				//CCEnd by liunan 2011-11-02
				//CCBegin SS2
				if(num.endsWith("(EPM)")||num.endsWith("(附件)")||num.endsWith("(电控)")||num.toLowerCase().endsWith("(ol)")||num.toLowerCase().endsWith("(ed)")||num.toLowerCase().endsWith("(pdf)"))
				{
					//需要补发。
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
			//修改图纸发布结果统计文件的名称
			//filename = HTML_PATH + noteNum + ".html";
			filename = HTML_PATH + noteNum + "_blob.html";
			//CCEnd by liunan 2009-09-03
			writeFile(buffer, filename);
			System.out.println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult writeFile over and filename:"+filename);
		//CCBegin SS5
		//}
		//CCBegin by liunan 2012-02-29 如果发过来的文档，还是不需要显示的，则直接返回。
		//if(succeedAmount==0 && failedAmount==0)
		//{
			//System.out.println("发过来的文档，并不需要显示，则不构造《图纸发布结果统计.html》文件！");
			//return;
		//}
		//CCEnd SS5
		//CCEnd by liunan 2012-02-29
		//CCBegin by liunan 2009-07-16
		//源代码
		//try {
		//CCBegin by liunan 2009-07-16
			DocInfo docInfo = PublishHelper
					.getDocInfoByNumber(getConfirmationNumber(noteNum));
			ContentService cser = (ContentService) PublishHelper
					.getEJBService("ContentService");
			if (docInfo == null) {	
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			//liunan 2009-09-03if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
				System.out.println("=====>leave BolbPublishResultGenerator.generateBolbPublishResult,根据采用通知书号："+getConfirmationNumber(noteNum)+"没有找到对应的数据发布结果统计文档");
			return;
			} else {
				File file = new File(filename);
				//CCBegin SS1
				if(fileVaultUsed)
				{
					ContentClientHelper contentClientHelper = new ContentClientHelper();
					ApplicationDataInfo app = contentClientHelper.requestUpload(file);
					app.setFileName("图纸发布结果统计" + ".html");
					app.setUploadPath(filename);
					app.setFileSize(file.length());
					app = (ApplicationDataInfo) cser
					.uploadContent((FormatContentHolderIfc) docInfo, app);
				}
				else
				{
					ApplicationDataInfo app = new ApplicationDataInfo();
					app.setFileName("图纸发布结果统计" + ".html");
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
		//CCBegin by liunan 2011-10-18 修改屏蔽不需显示内容后 计数错误问题。
		int notNum = 0;
		//CCEnd by liunan 2011-10-18
		for (int i = 0; i < blobPublishResult.getElementCount(); i++) {
			Element ele = (Element) enumeration.nextElement();
			String num = ((String) ele.getValue("num")).trim();
			String result = (String) ele.getValue("result");
			
			//CCBegin by liunan 2011-10-12 不需要的类型而且也无法转换成所需类型的文档不显示结果
			//目前此类文档有： tif CATPart CATDrawing model
			if(!result.trim().equalsIgnoreCase("true"))
			{
				if(num.endsWith("(tif)")||num.endsWith("(CATPart)")||num.endsWith("(CATDrawing)")
				   //||num.endsWith("(model)"))
				   ||num.endsWith("(model)")||num.endsWith("(hex)"))//liunan 2011-12-22 新增hex类型。
				{
					//CCBegin by liunan 2011-10-18 修改屏蔽不需显示内容后 计数错误问题。
					notNum ++;
					//CCEnd by liunan 2011-10-18
					continue;
				}
				//CCBegin by liunan 2011-11-02 如果epm 的编号中有 G01，如 5205G01-A95，就表示是逻辑总成，没有图，也就不统计结果。
				if(isLogic(num))
				{
					notNum ++;
					continue;
				}
				//CCEnd by liunan 2011-11-02
				//CCBegin SS2
				if(num.endsWith("(EPM)")||num.endsWith("(附件)")||num.endsWith("(电控)")||num.toLowerCase().endsWith("(ol)")||num.toLowerCase().endsWith("(ed)")||num.toLowerCase().endsWith("(pdf)"))
				{
					//需要补发。
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
				//CCBegin by liunan 2011-10-18 修改屏蔽不需显示内容后 计数错误问题。
				//writeColum(buffer, "" + (i + 1), "black");
				writeColum(buffer, "" + (i + 1 - notNum), "black");
				//CCEnd by liunan 2011-10-18
				writeColum(buffer, num, "black");
				writeColum(buffer, "图纸发布成功", "black");
				this.succeedAmount++;
			} else {
				//CCBegin by liunan 2011-10-18 修改屏蔽不需显示内容后 计数错误问题。
				//writeColum(buffer, "" + (i + 1), "red");
				writeColum(buffer, "" + (i + 1 - notNum), "red");
				//CCEnd by liunan 2011-10-18
				writeColum(buffer, num, "red");
				writeColum(buffer, "图纸发布失败", "red");
				this.failedAmount++;
			}
			itemEnd(buffer);
		}
	}
	
	//CCBegin by liunan 2011-11-02 
	//逻辑总成判断方法。如果epm 的编号中有 G01，如 5205G01-A95，就表示是逻辑总成，没有图，也就不统计结果。
	private boolean isLogic(String num)
	{
		//判断依据：编号前四位是数字，然后是G01的，就当作是逻辑总成。
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
	 * 写html表头
	 * 
	 * @param buffer
	 *            StringBuffer 字符容器
	 */
	private void writeTableHead(StringBuffer buffer) {
		buffer.append("<HTML>\n");
		buffer.append("<HEAD>\n");
		buffer.append("<TITLE> 图纸发布结果统计 </TITLE>\n");
		buffer
				.append("<META http-equiv=\"Content-Type\" content=\"text/html; charset=chinese\">\n");
		buffer.append("</HEAD>\n");
		buffer.append("<BODY bgcolor = \"#EBEBEB\">\n");
		buffer.append("<P>\n");
		buffer.append("<P>\n");
		buffer.append("<table width=\"100%\" border=\"0\" align=\"center\">");
		//CCBegin SS3
		buffer
				//.append("<tr><td height=\"44\" colspan=\"2\" ><div align=\"center\"><font face=\"楷体_GB2312\" size=\"5\">图纸发布结果统计</font></div></td></tr>");
		    .append("<tr><td height=\"44\" colspan=\"2\" ><div align=\"center\"><font face=\"楷体_GB2312\" size=\"5\">图纸发布结果统计" + logDateFormat.format(new java.util.Date()) + "</font></div></td></tr>");
		//CCEnd SS3
		//CCBegin by liunan 2010-12-24 添加对图纸类型的说明
		//CCBegin SS4
		//buffer.append("<tr><td height=\"30\" ><div align=\"center\"><font face=\"楷体_GB2312\" size=\"5\" color=\"red\">括号内字段表示该图纸类型：零部件（附件），EPM文档（EPM），普通文档（记录的文件后缀，目前只发布ol，ed，pdf和电控文档）</font></div></td></tr>");		
		buffer.append("<tr><td height=\"30\" colspan=\"2\" ><div align=\"center\"><font face=\"楷体_GB2312\" size=\"5\" color=\"red\">括号内字段表示该图纸类型：零部件（pdf），零部件（中性），普通文档（记录的文件后缀，目前只发布ol，ed，pdf和电控文档）</font></div></td></tr>");		
		//CCEnd SS4
		//CCEnd by liunan 2010-12-24
		buffer
				.append("<tr><td height=\"30\" width=\"50%\" align=\"center\">成功数：");
		buffer.append(this.succeedAmount);
		buffer
				.append("</td><td height=\"30\" width=\"50%\"align=\"center\">失败数：");
		buffer.append(this.failedAmount);
		buffer.append("</td></tr></table>");
	}

	/**
	 * 向数据表内添加子标题
	 * 
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @param subHead
	 *            String 子标题
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
	 * 向数据表内添加列
	 * 
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @param column
	 *            String 列名
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
	 * 回车
	 * 
	 * @param buffer
	 *            StringBuffer 内容缓存
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
