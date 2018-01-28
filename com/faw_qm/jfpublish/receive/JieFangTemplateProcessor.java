package com.faw_qm.jfpublish.receive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.notify.util.BasicNotificationProcessor;

public class JieFangTemplateProcessor extends BasicNotificationProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String logfile = null;

	private boolean isblob = true;

	private String[] partColumn = { "序号", "名称", "编号", "类型", "发布版本", "接收版本",
			"是否成功", "备注" };

	private String[] revisePartColumn = { "序号", "名称", "编号", "类型", "发布前版本",
			"发布后版本", "接收前版本", "接收后版本", "是否成功", "备注" };

	private String[] docColumn = { "序号", "名称", "编号", "发布版本", "接收版本", "是否成功",
			"备注" };

	private String[] reviseDocColumn = { "序号", "名称", "编号", "发布前版本", "发布后版本",
			"接收前版本", "接收后版本", "是否成功", "备注" };

	private String[] docAndPart = { "零部件编号", "文档编号", "是否成功", "备注" };

	private String[] partUsageLink = { "父部件编号", "子部件编号", "是否成功", "备注" };

	private String[] blob = { "文件名", "StreamID", "数据大小（k）", "是否成功", "备注" };

	//CCBegin by liunan 2008-12-03 添加替换件和结构替换件的统计。源码如下：
	/*private String[] subHead = { "新建零部件统计", "新建描述文档统计",
			"零部件和描述文档的关联统计", "零部件的结构关联统计", "Blob数据发布结果",
			"修订零部件统计", "修订描述文档统计"};*/
  private String[] subHead = { "新建零部件统计", "新建描述文档统计", "零部件和描述文档的关联统计", "零部件的结构关联统计", "Blob数据发布结果",
			"修订零部件统计", "修订描述文档统计", "零部件和替换件的关联统计", "零部件和结构替换件的关联统计" };
			
	private String[] alternatesColumn = { "零部件编号", "替换件编号", "是否成功", "备注" };
	
	private String[] substitutesColumn = { "父部件编号", "父部件版本", "子部件编号", "结构替换件", "是否成功", "备注" };
	//CCEnd by liunan 2008-12-03
	
	private static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy/MM/dd-HH:mm:ss.SSS");

	private String userName = "技术中心";

	private int sucnum = 0;

	private int zong = 0;

	private int doczong = 0;

	private int docnum = 0;

	private int rezong = 0;

	private int renum = 0;

	private int redoczong = 0;

	private int redocnum = 0;

	private int linknum = 0;
	
	//CCBegin by liunan 2008-12-03
	private int altzong = 0; //替换件关联总数
	
	private int altnum = 0; //替换件关联成功总数
	
	private int subzong = 0; //结构替换件关联总数
	
	private int subnum = 0; //结构替换件关联成功总数
	//CCEnd by liunan 2008-12-03

	private static PrintWriter out;

	/**
	 * 向数据表内添加列
	 * 
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @param column
	 *            String 列名
	 */
	private void writeColum(StringBuffer buffer, String column) {
		if (column == null || column.equals("") || column.equals(" ")) {
			column = "&nbsp;";
		}
		buffer.append("<td><div align=\"left\">");
		buffer.append(column);
		buffer.append("</div></td>");
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

		buffer.append("<td><div align=\"left\"><font color=" + color + ">");
		buffer.append(column);
		buffer.append("</font></div></td>");
		enter(buffer);

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
		buffer.append("<table width=\"75%\" border=\"1\">");
		enter(buffer);
	}

	private void writeHtmlHead(StringBuffer buffer) {
		buffer
				.append(" <!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		enter(buffer);
		buffer.append("<html>");
		enter(buffer);
		buffer.append("<head>");
		enter(buffer);
		buffer.append("<title>Untitled Document</title>");
		enter(buffer);
		buffer
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		enter(buffer);
		buffer.append("</head>");
		enter(buffer);
		buffer.append("<body>");
		enter(buffer);
		buffer.append("<h1 align=\"center\"><strong> 查看数据接收结果</strong> </h1>");
		enter(buffer);
	}

	private void writeHtmlEnd(StringBuffer buffer) {
		// System.out.println("start write html end-----");
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
		// System.out.println("finish write html end------");
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

  //CCBegin by liunan 2008-12-03 添加对替换件和结构替换件的数量支持。
	public JieFangTemplateProcessor(String logfile, String userName, int zong,
			int sucnum, int doczong, int docnum, int rezong, int renum, int redoczong, int redocnum,
			int linknum, int altzong, int altnum, int subzong, int subnum) {
		this.logfile = logfile;
		this.userName = userName;
		this.zong = zong;
		this.sucnum = sucnum;
		this.doczong = doczong;
		this.docnum = docnum;
		this.rezong = rezong;
		this.renum = renum;
		this.redoczong = redoczong;
		this.redocnum = redocnum;
		this.linknum = linknum;
		this.altzong = altzong; //替换件数量
		this.altnum = altnum; //替换件成功数量
		this.subzong = subzong; //结构替换件数量
		this.subnum = subnum; //结构替换件成功数量
	}
	//CCEnd by liunan 2008-12-03

	/**
	 * 获得通知的具体信息
	 * 
	 * @param locale
	 *            地域信息
	 * @return String
	 * @throws QMException
	 */
	public String getLogContent() throws QMException {
		// System.out.println(" enter the getLogContent----------");
		StringBuffer buffer = new StringBuffer();
		writeHtmlHead(buffer);
		try {
			// System.out.println(" the log file is " + logfile);
			BufferedReader reader = new BufferedReader(new FileReader(logfile));
			String s = null;
			while ((s = reader.readLine()) != null) {
				// System.out.println("start read the log file-----------");
				write: while (s != null) {
					if (s.indexOf("##Part") != -1) {

						s = writePart(reader, buffer);

						continue write;
					} else if (s.indexOf("##Doc") != -1) {
						// System.out.println("Start write doc ------");
						s = writeDoc(reader, buffer);
						// System.out.println("end write doc-----");
						continue write;
					} else if (s.indexOf("##DescriptionLink") != -1) {
						// System.out.println(" start write the docandpart
						// link");
						s = writeDocAndPart(reader, buffer);
						// System.out.println("end write the docandpart link");
						continue write;
					} else if (s.indexOf("##UsageLink") != -1) {
						s = writePartUsageLink(reader, buffer);
						continue write;
					} else if (s.startsWith("##RevisePart")) {
						s = writeRevisePart(reader, buffer);
						continue write;
					} else if (s.startsWith("##ReviseDoc")) {
						s = writeReviseDoc(reader, buffer);
						continue write;
					}
					//CCBegin by liunan 2008-12-02 添加对替换件的支持
					 else if (s.startsWith("##AlternatesLinks")) {
						s = writeAlternates(reader, buffer);
						continue write;
					} else if (s.startsWith("##SubtitutesLinks")) {
						s = writeSubtitutes(reader, buffer);
						continue write;
					} 
					//CCEnd by liunan 2008-12-02
					else {
						s = reader.readLine();
						continue write;
					}
				}

			}
			// System.out.println("finish the body write--------");
			reader.close();
			writeHtmlEnd(buffer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return buffer.toString();
	}

	/**
	 * 填写Part表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writePart(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		buffer.append("<h3 align=\"left\"><strong>");
		buffer.append("数据导入的时间：" + logDateFormat.format(new java.util.Date()));
		buffer.append("</strong></h3>");
		// buffer.append("<br>");
		enter(buffer);
		buffer.append("<h3 align=\"left\"><strong>");
		buffer.append("导入者：" + userName);
		buffer.append("</strong></h3>");
		// buffer.append("<br>");
		enter(buffer);
		writeSubHead(buffer, subHead[0]);
		enter(buffer);
		if (zong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("没有需要新建的零部件！");
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("新建零部件总数：" + zong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("新建零部件成功数：" + sucnum);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		}

		int i = partColumn.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, partColumn[j]);
		}
		String temp = writeContent(reader, buffer);
		return temp;
	}

	/**
	 * 填写文档表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writeDoc(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[1]);
		enter(buffer);
		if (doczong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("没有需要新建的文档！");
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("接收文档总数：" + doczong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);

			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("接收文档成功数：" + docnum);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		}
		int i = docColumn.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, docColumn[j]);
		}
		String temp = writeContent(reader, buffer);
		return temp;
	}

	/**
	 * 填写文档和零件关联表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writeDocAndPart(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[2]);
		int i = docAndPart.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, docAndPart[j]);
		}
		String temp = writeContent(reader, buffer);
		return temp;
	}

	/**
	 * 填写零部件结构关系关联表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writePartUsageLink(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[3]);
		enter(buffer);
		//yanqi-2007830-不再显示失败数
		/*buffer.append("<h3 align=\"left\"><strong>");
		buffer.append("失败数：" + linknum);
		buffer.append("</strong></h3>");
		// buffer.append("<br>");
		enter(buffer);*/

		int i = partUsageLink.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, partUsageLink[j]);
		}
		String temp = writeContent(reader, buffer);
		return temp;
	}

	/**
	 * 填写blob表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writeBlob(BufferedReader reader, StringBuffer buffer)
			throws IOException {

		writeSubHead(buffer, subHead[4]);
		int i = blob.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, blob[j]);
		}
		String temp = writeContent(reader, buffer);
		return temp;
	}

	/**
	 * 书写数据项
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writeContent(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		String temp = null;
		while ((temp = reader.readLine()) != null && temp.indexOf("##") == -1) {
			itemBegin(buffer);
			StringTokenizer s = new StringTokenizer(temp, ",");
			if(temp.indexOf("失败")>0)
			{
				while (s.hasMoreTokens()) {
					String t = s.nextToken();
					//CCBegin by liunan 2009-10-19 屏蔽“锁定对象出错：不能锁定对象”异常
					//该异常是由于同时发布的几个变更中存在相同需要发布的结构，其中一个创建好了，
					//另一个则无法修改时报出的异常，实际已经创建成功，现修改让《基础数据发布结果统计.html》
					//中不再显示此异常，而是显示成功。
					//即 出现“锁定”异常的那行记录当做成功的来处理。
					//源代码
					//writeColum(buffer, t,"Red");
					if(temp.indexOf("锁定对象出错：不能锁定对象")>0)
					{
						if(t.equals("失败"))
						{
							writeColum(buffer, "成功");
						}
						else if(t.indexOf("锁定对象出错：不能锁定对象 QMPart_")>0)
						{
							writeColum(buffer, null);
						}
						else
						{
							writeColum(buffer, t);
						}
					}
					//CCBegin by liunan 2011-10-27 新增屏蔽信息“对象已经修订过或不是最新版本”
					else if(temp.indexOf("对象已经修订过或不是最新版本")>0)
					{
						if(t.equals("失败"))
						{
							writeColum(buffer, "成功");
						}
						else if(t.indexOf("对象已经修订过或不是最新版本")>0)
						{
							writeColum(buffer, null);
						}
						else
						{
							writeColum(buffer, t);
						}
					}
					//CCEnd by liunan 2011-10-27
					else
					{
						writeColum(buffer, t,"Red");
					}
					//CCEnd by liunan 2009-10-19
				}
			}else
			{
				while (s.hasMoreTokens()) {
					String t = s.nextToken();
					//CCBegin by liunan 2008-12-11 修改小版本升级标识
					//源码如下：
					/*writeColum(buffer, t);
					if (t.equals("成功") || t.equals("success")) {
						writeColum(buffer, null);
					}
					if (t.equals("已存在")) {
						writeColum(buffer, null);
					}*/
					if (t.equals("成功") || t.equals("success")) 
					{
						writeColum(buffer, t);
						writeColum(buffer, null);
					}else if (t.equals("已存在")) 
					{
						writeColum(buffer, t);
						writeColum(buffer, null);
					}else if (t.equals("小版本升级"))
					{
						writeColum(buffer, "成功");
						writeColum(buffer, t);
					//CCBegin by liunan 2009-12-07 增加对带附件零部件的统计信息显示。
				  }else if(t.equals("有附件"))
				  {
						writeColum(buffer, "成功");
						writeColum(buffer, t);
				  }else if(t.equals("小版本升级（有附件）"))
				  {
						writeColum(buffer, "成功");
						writeColum(buffer, t);
					//CCEnd by liunan 2009-12-07
					}else
					{
						writeColum(buffer, t);
					}
					//CCEnd by liunan 2008-12-11
				}
			}
			itemEnd(buffer);
		}
		tableEnd(buffer);
		return temp;
	}

	/**
	 * 填写零部件修订表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writeRevisePart(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[5]);
		enter(buffer);
		if (rezong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("没有需要修订的零部件！");
			buffer.append("</strong></h3>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("修订零部件总数：" + rezong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("修订零部件成功数：" + renum);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		}

		int i = revisePartColumn.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, revisePartColumn[j]);
		}
		String temp = writeContent(reader, buffer);

		return temp;

	}

	/**
	 * 填写文档修订表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 */
	private String writeReviseDoc(BufferedReader reader, StringBuffer buffer)
			throws IOException {

		writeSubHead(buffer, subHead[6]);
		enter(buffer);
		if (redoczong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("没有需要修订的描述文档！");
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("修订文档总数：" + redoczong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);

			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("修订文档成功数：" + redocnum);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		}

		int i = reviseDocColumn.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, reviseDocColumn[j]);
		}
		String temp = writeContent(reader, buffer);

		return temp;

	}

	/**
	 * 填写替换件表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 * @author liunan 2008-12-02
	 */
	private String writeAlternates(BufferedReader reader, StringBuffer buffer)
			throws IOException {

		writeSubHead(buffer, subHead[7]);
		enter(buffer);
		int i = alternatesColumn.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, alternatesColumn[j]);
		}
		String temp = writeContent(reader, buffer);

		return temp;

	}


	/**
	 * 填写结构替换件表
	 * 
	 * @param reader
	 *            BufferedReader 读数据
	 * @param buffer
	 *            StringBuffer 内容缓存
	 * @throws IOException
	 * @return String 当前数据行
	 * @author liunan 2008-12-02
	 */
	private String writeSubtitutes(BufferedReader reader, StringBuffer buffer)
			throws IOException {

		writeSubHead(buffer, subHead[8]);
		enter(buffer);
		int i = substitutesColumn.length;
		for (int j = 0; j < i; j++) {
			writeColum(buffer, substitutesColumn[j]);
		}
		String temp = writeContent(reader, buffer);

		return temp;

	}
		
	/**
	 * 数据处理，为发送通知进行合理的数据准备
	 * 
	 * @param locale
	 *            地理区域
	 * @return Object 对象数组
	 * @throws QMException
	 */
	public Object[] process(Locale locale) throws QMException {
		Object[] obj = { getLogContent() };
		return obj;
	}

	public static void writeFile(String s, String path) throws QMException {
		// System.out.println("++++++++++++++++++++>>>>"+s);
		try {
			out = new PrintWriter(new FileWriter(path, true), true);
			out.println(s);
			out.flush();
			out.close();
			// FileWriter writer = new FileWriter(path);
			// writer.write(s);
			// writer.flush();
			// writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new QMException(" 生成文件时出现错误");
		}
	}

	public static void main(String[] args) throws Exception {
		// String logfile = "e:\\jiefanglog.txt";
		// JieFangTemplateProcessor processor = new
		// JieFangTemplateProcessor(logfile);
		// processor.getLogContent();
		// StringBuffer sb = new StringBuffer();
		// sb.append(tableHeader);
		// sb.append(partTableColumn);
		// sb.append(tableTail);
		// String info = sb.toString();
		// String testfile = "e:/userpublish启明测试数据发布.log";
		// JieFangTemplateProcessor j = new
		// JieFangTemplateProcessor(testfile,"rdc",17,17);
		// writeFile(j.getLogContent(), "e:/启明测试数据发布.html");
		// BufferedWriter writer = new BufferedWriter(new FileWriter(testfile));
		// writer.write(info);
		// writer.newLine();
		// writer.write(info);
		// writer.flush();
	}

}
