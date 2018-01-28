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

	private String[] partColumn = { "���", "����", "���", "����", "�����汾", "���հ汾",
			"�Ƿ�ɹ�", "��ע" };

	private String[] revisePartColumn = { "���", "����", "���", "����", "����ǰ�汾",
			"������汾", "����ǰ�汾", "���պ�汾", "�Ƿ�ɹ�", "��ע" };

	private String[] docColumn = { "���", "����", "���", "�����汾", "���հ汾", "�Ƿ�ɹ�",
			"��ע" };

	private String[] reviseDocColumn = { "���", "����", "���", "����ǰ�汾", "������汾",
			"����ǰ�汾", "���պ�汾", "�Ƿ�ɹ�", "��ע" };

	private String[] docAndPart = { "�㲿�����", "�ĵ����", "�Ƿ�ɹ�", "��ע" };

	private String[] partUsageLink = { "���������", "�Ӳ������", "�Ƿ�ɹ�", "��ע" };

	private String[] blob = { "�ļ���", "StreamID", "���ݴ�С��k��", "�Ƿ�ɹ�", "��ע" };

	//CCBegin by liunan 2008-12-03 ����滻���ͽṹ�滻����ͳ�ơ�Դ�����£�
	/*private String[] subHead = { "�½��㲿��ͳ��", "�½������ĵ�ͳ��",
			"�㲿���������ĵ��Ĺ���ͳ��", "�㲿���Ľṹ����ͳ��", "Blob���ݷ������",
			"�޶��㲿��ͳ��", "�޶������ĵ�ͳ��"};*/
  private String[] subHead = { "�½��㲿��ͳ��", "�½������ĵ�ͳ��", "�㲿���������ĵ��Ĺ���ͳ��", "�㲿���Ľṹ����ͳ��", "Blob���ݷ������",
			"�޶��㲿��ͳ��", "�޶������ĵ�ͳ��", "�㲿�����滻���Ĺ���ͳ��", "�㲿���ͽṹ�滻���Ĺ���ͳ��" };
			
	private String[] alternatesColumn = { "�㲿�����", "�滻�����", "�Ƿ�ɹ�", "��ע" };
	
	private String[] substitutesColumn = { "���������", "�������汾", "�Ӳ������", "�ṹ�滻��", "�Ƿ�ɹ�", "��ע" };
	//CCEnd by liunan 2008-12-03
	
	private static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy/MM/dd-HH:mm:ss.SSS");

	private String userName = "��������";

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
	private int altzong = 0; //�滻����������
	
	private int altnum = 0; //�滻�������ɹ�����
	
	private int subzong = 0; //�ṹ�滻����������
	
	private int subnum = 0; //�ṹ�滻�������ɹ�����
	//CCEnd by liunan 2008-12-03

	private static PrintWriter out;

	/**
	 * �����ݱ��������
	 * 
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @param column
	 *            String ����
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

		buffer.append("<td><div align=\"left\"><font color=" + color + ">");
		buffer.append(column);
		buffer.append("</font></div></td>");
		enter(buffer);

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
		buffer.append("<h1 align=\"center\"><strong> �鿴���ݽ��ս��</strong> </h1>");
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

  //CCBegin by liunan 2008-12-03 ��Ӷ��滻���ͽṹ�滻��������֧�֡�
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
		this.altzong = altzong; //�滻������
		this.altnum = altnum; //�滻���ɹ�����
		this.subzong = subzong; //�ṹ�滻������
		this.subnum = subnum; //�ṹ�滻���ɹ�����
	}
	//CCEnd by liunan 2008-12-03

	/**
	 * ���֪ͨ�ľ�����Ϣ
	 * 
	 * @param locale
	 *            ������Ϣ
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
					//CCBegin by liunan 2008-12-02 ��Ӷ��滻����֧��
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
	 * ��дPart��
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
	 */
	private String writePart(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		buffer.append("<h3 align=\"left\"><strong>");
		buffer.append("���ݵ����ʱ�䣺" + logDateFormat.format(new java.util.Date()));
		buffer.append("</strong></h3>");
		// buffer.append("<br>");
		enter(buffer);
		buffer.append("<h3 align=\"left\"><strong>");
		buffer.append("�����ߣ�" + userName);
		buffer.append("</strong></h3>");
		// buffer.append("<br>");
		enter(buffer);
		writeSubHead(buffer, subHead[0]);
		enter(buffer);
		if (zong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("û����Ҫ�½����㲿����");
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�½��㲿��������" + zong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�½��㲿���ɹ�����" + sucnum);
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
	 * ��д�ĵ���
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
	 */
	private String writeDoc(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[1]);
		enter(buffer);
		if (doczong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("û����Ҫ�½����ĵ���");
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�����ĵ�������" + doczong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);

			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�����ĵ��ɹ�����" + docnum);
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
	 * ��д�ĵ������������
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
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
	 * ��д�㲿���ṹ��ϵ������
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
	 */
	private String writePartUsageLink(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[3]);
		enter(buffer);
		//yanqi-2007830-������ʾʧ����
		/*buffer.append("<h3 align=\"left\"><strong>");
		buffer.append("ʧ������" + linknum);
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
	 * ��дblob��
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
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
	 * ��д������
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
	 */
	private String writeContent(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		String temp = null;
		while ((temp = reader.readLine()) != null && temp.indexOf("##") == -1) {
			itemBegin(buffer);
			StringTokenizer s = new StringTokenizer(temp, ",");
			if(temp.indexOf("ʧ��")>0)
			{
				while (s.hasMoreTokens()) {
					String t = s.nextToken();
					//CCBegin by liunan 2009-10-19 ���Ρ�������������������������쳣
					//���쳣������ͬʱ�����ļ�������д�����ͬ��Ҫ�����Ľṹ������һ���������ˣ�
					//��һ�����޷��޸�ʱ�������쳣��ʵ���Ѿ������ɹ������޸��á��������ݷ������ͳ��.html��
					//�в�����ʾ���쳣��������ʾ�ɹ���
					//�� ���֡��������쳣�����м�¼�����ɹ���������
					//Դ����
					//writeColum(buffer, t,"Red");
					if(temp.indexOf("�����������������������")>0)
					{
						if(t.equals("ʧ��"))
						{
							writeColum(buffer, "�ɹ�");
						}
						else if(t.indexOf("����������������������� QMPart_")>0)
						{
							writeColum(buffer, null);
						}
						else
						{
							writeColum(buffer, t);
						}
					}
					//CCBegin by liunan 2011-10-27 ����������Ϣ�������Ѿ��޶����������°汾��
					else if(temp.indexOf("�����Ѿ��޶����������°汾")>0)
					{
						if(t.equals("ʧ��"))
						{
							writeColum(buffer, "�ɹ�");
						}
						else if(t.indexOf("�����Ѿ��޶����������°汾")>0)
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
					//CCBegin by liunan 2008-12-11 �޸�С�汾������ʶ
					//Դ�����£�
					/*writeColum(buffer, t);
					if (t.equals("�ɹ�") || t.equals("success")) {
						writeColum(buffer, null);
					}
					if (t.equals("�Ѵ���")) {
						writeColum(buffer, null);
					}*/
					if (t.equals("�ɹ�") || t.equals("success")) 
					{
						writeColum(buffer, t);
						writeColum(buffer, null);
					}else if (t.equals("�Ѵ���")) 
					{
						writeColum(buffer, t);
						writeColum(buffer, null);
					}else if (t.equals("С�汾����"))
					{
						writeColum(buffer, "�ɹ�");
						writeColum(buffer, t);
					//CCBegin by liunan 2009-12-07 ���ӶԴ������㲿����ͳ����Ϣ��ʾ��
				  }else if(t.equals("�и���"))
				  {
						writeColum(buffer, "�ɹ�");
						writeColum(buffer, t);
				  }else if(t.equals("С�汾�������и�����"))
				  {
						writeColum(buffer, "�ɹ�");
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
	 * ��д�㲿���޶���
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
	 */
	private String writeRevisePart(BufferedReader reader, StringBuffer buffer)
			throws IOException {
		writeSubHead(buffer, subHead[5]);
		enter(buffer);
		if (rezong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("û����Ҫ�޶����㲿����");
			buffer.append("</strong></h3>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�޶��㲿��������" + rezong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�޶��㲿���ɹ�����" + renum);
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
	 * ��д�ĵ��޶���
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
	 */
	private String writeReviseDoc(BufferedReader reader, StringBuffer buffer)
			throws IOException {

		writeSubHead(buffer, subHead[6]);
		enter(buffer);
		if (redoczong == 0) {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("û����Ҫ�޶��������ĵ���");
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);
		} else {
			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�޶��ĵ�������" + redoczong);
			buffer.append("</strong></h3>");
			// buffer.append("<br>");
			enter(buffer);

			buffer.append("<h3 align=\"left\"><strong>");
			buffer.append("�޶��ĵ��ɹ�����" + redocnum);
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
	 * ��д�滻����
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
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
	 * ��д�ṹ�滻����
	 * 
	 * @param reader
	 *            BufferedReader ������
	 * @param buffer
	 *            StringBuffer ���ݻ���
	 * @throws IOException
	 * @return String ��ǰ������
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
	 * ���ݴ���Ϊ����֪ͨ���к��������׼��
	 * 
	 * @param locale
	 *            ��������
	 * @return Object ��������
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
			throw new QMException(" �����ļ�ʱ���ִ���");
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
		// String testfile = "e:/userpublish�����������ݷ���.log";
		// JieFangTemplateProcessor j = new
		// JieFangTemplateProcessor(testfile,"rdc",17,17);
		// writeFile(j.getLogContent(), "e:/�����������ݷ���.html");
		// BufferedWriter writer = new BufferedWriter(new FileWriter(testfile));
		// writer.write(info);
		// writer.newLine();
		// writer.write(info);
		// writer.flush();
	}

}
