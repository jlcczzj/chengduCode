/**
 * ���ɳ��� ExportProduceServlet.java    1.0    2013/05/07
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �޸� ����ר�ü�����ʾIE�޷����ء�  xianglx 2014-8-27
 * SS2 ����poi��3.6��ע��//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
 */
package com.faw_qm.gybomNotice.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;




import com.faw_qm.framework.remote.RemoteProperty;


/**
 * ����ר�ü�
 * @version 1.0
 */
public class ExportSpecPartServlet extends HttpServlet {
	

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,java.io.IOException
	   {
	           doPost(request,response);
	   }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,java.io.IOException
	{

	    
		GYNoticeHelper helper = new GYNoticeHelper();

		try {

			// ����һ��������
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        //���ݳ����Excelʵ�ʴ�С������ı�����ֵԽ�󣬱���ԽС����ȡ����ƽ�����е�ֵ
	        double columnScale = 264;
	        double rowScale = 18.88;
	        
	        HSSFCellStyle cs = workbook.createCellStyle();
	        //�Զ�����
	        cs.setWrapText(true);
	        //���ݾ�����ʾ
	        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        
	        HSSFFont font = workbook.createFont();
	        font.setFontName("����");
	        font.setFontHeightInPoints((short)12);
	        cs.setFont(font);
	        
	        HSSFCellStyle cs1 = workbook.createCellStyle();
	        //�Զ�����
	        cs1.setWrapText(true);
	        //���ݿ�����ʾ
	        cs1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        cs1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        cs1.setFont(font);
	        
	        Calendar cal=Calendar.getInstance();//ʹ��������  
	        int year=cal.get(Calendar.YEAR);//�õ���  
	        int month=cal.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1  
	        int day=cal.get(Calendar.DAY_OF_MONTH);//�õ���
	        Vector dataVector = helper.getSpecPart();//ר�ü����ݼ��ϡ�
			int count = 0;
			count = dataVector.size()/10;
			if(dataVector.size()%10>0)
				count++;
			if(count==0)
				count = 1;
			   
			for(int i=0;i<count;i++)
			{
		    
				// ����һ�����
		        HSSFSheet sheet = workbook.createSheet(String.valueOf(i+1));
		        //�����п�
		        double aColumnWidth = 18 * columnScale;
		        double bColumnWidth = 18 * columnScale;
		        double cColumnWidth = 18 * columnScale;
		        double dColumnWidth = 18 * columnScale;
		        double eColumnWidth = 18 * columnScale;
		        double fColumnWidth = 18 * columnScale;
		        double gColumnWidth = 18 * columnScale;
		        double hColumnWidth = 18 * columnScale;

		        sheet.setColumnWidth((short)0, (short)aColumnWidth);
		        sheet.setColumnWidth((short)1, (short)bColumnWidth);
		        sheet.setColumnWidth((short)2, (short)cColumnWidth);
		        sheet.setColumnWidth((short)3, (short)dColumnWidth);
		        sheet.setColumnWidth((short)4, (short)eColumnWidth);
		        sheet.setColumnWidth((short)5, (short)fColumnWidth);
		        sheet.setColumnWidth((short)6, (short)fColumnWidth);
		        sheet.setColumnWidth((short)7, (short)fColumnWidth);
		        double row0Height = 40 * rowScale;
		        HSSFRow row0 = sheet.createRow((short) 0);      
		        row0.setHeight((short) row0Height);
		              
		        HSSFCell cell0 = row0.createCell((short)0);     
		        cell0.setCellStyle(cs);     
		        //cell0.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell0.setCellValue("���");
		        
		        //sheet.addMergedRegion(new Region(0, (short) 1, 0, (short) 2));      
		        HSSFCell cell1 = row0.createCell((short)1);   
		        cell1.setCellStyle(cs);     
		        //cell1.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell1.setCellValue("��ʶ");
		        
      
		        HSSFCell cell2 = row0.createCell((short)2);     
		        cell2.setCellStyle(cs);     
		        //cell2.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell2.setCellValue("�㲿������");
		        
		        HSSFCell cell3 = row0.createCell((short)3);     
		        cell3.setCellStyle(cs);     
		        //cell3.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell3.setCellValue("�㲿������");
		        
		        HSSFCell cell4 = row0.createCell((short)4);     
		        cell4.setCellStyle(cs);     
		        //cell4.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell4.setCellValue("�汾");
		        
		        HSSFCell cell5 = row0.createCell((short)5);     
		        cell5.setCellStyle(cs);     
		        //cell5.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell5.setCellValue("����·��");
		        
		        HSSFCell cell6= row0.createCell((short)6);     
		        cell6.setCellStyle(cs);     
		        //cell6.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell6.setCellValue("װ��·��");
		        
		        HSSFCell cell7= row0.createCell((short)7);     
		        cell7.setCellStyle(cs);     
		        //cell7.setEncoding(HSSFCell.ENCODING_UTF_16);   
		        cell7.setCellValue("��׼");
//		        
//		        for(int n=0;n<5;n++)
//		        	sheet.addMergedRegion(new Region(5+n, (short) 1, 5+n, (short) 2));      
		        
		        for(int begin=i*10;begin<(i+1)*10&&begin<dataVector.size();begin++)
		        {
		        	 String[] str = (String[] )dataVector.get(begin);
		        	
		        	HSSFRow row = sheet.createRow((short)(1+begin%10));  
			        double rowHeight = 20 * rowScale;
			        row.setHeight((short) rowHeight);
			        
			        HSSFCell cell01 = row.createCell((short)0);     
			        cell01.setCellStyle(cs);     
			        //cell01.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell01.setCellValue( String.valueOf(begin+1));
			        
			        HSSFCell cell02 = row.createCell((short)1);   
			        cell02.setCellStyle(cs);     
			        //cell02.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell02.setCellValue(str[0]);
			        
			        HSSFCell cell03 = row.createCell((short)2);     
			        cell03.setCellStyle(cs);     
			        //cell03.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell03.setCellValue(str[1]);
			        
			        HSSFCell cell04 = row.createCell((short)3);     
			        cell04.setCellStyle(cs);     
			        //cell04.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell04.setCellValue(str[2]);
			        
			        HSSFCell cell05 = row.createCell((short)4);     
			        cell05.setCellStyle(cs);     
			        //cell05.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell05.setCellValue(str[3]);
			        
			        HSSFCell cell06 = row.createCell((short)5);     
			        cell06.setCellStyle(cs);     
			        //cell06.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell06.setCellValue(str[4]);
			        
			        HSSFCell cell07 = row.createCell((short)6);     
			        cell07.setCellStyle(cs);     
			        //cell07.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell07.setCellValue(str[5]);
			        
			        HSSFCell cell08 = row.createCell((short)7);     
			        cell08.setCellStyle(cs);     
			        //cell08.setEncoding(HSSFCell.ENCODING_UTF_16);   
			        cell08.setCellValue(str[6]);
		        }
		        
		        
			}

//CCBegin SS1
 
 			String dath = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE).format(Calendar.getInstance().getTime());
 			String fileName=dath+".xls";
     //����Ҫ�ӷ�����н����ļ����صĿͻ�����
      response.reset();
      //������������
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Disposition","attachment; filename=\"" + new String(fileName.getBytes("GBK"), "ISO8859_1") + "\"");
      javax.servlet.ServletOutputStream sos = response.getOutputStream();
      workbook.write(sos);
      sos.close();
/*
					String filePath = System.getProperty("java.io.tmpdir")+"workbook.xls";
	        FileOutputStream fileOut = new FileOutputStream(filePath);     
	        workbook.write(fileOut);     
	        fileOut.close(); 
	        
	        FileInputStream fis = new FileInputStream(filePath);
	        DataInputStream dataInputStream = new DataInputStream(fis);
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] b = new byte[1024000];
	        int n;
	        while ((n = dataInputStream.read(b)) != -1)
	        {
	            // �����ո�
	            out.write(b, 0, n);
	        }
	        byte[] content = out.toByteArray();
	        dataInputStream.close();
	        fis.close();
	        out.close();
	        File file = new File(filePath);
	        if(file.exists())
	        	file.delete();
			String dath = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE).format(Calendar.getInstance().getTime());
			response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment; filename=\"" +new String(dath.getBytes("utf-8"),"ISO-8859-1")+ ".xls" + "\"");
			response.addHeader("Content-Length", "" + file.length());
   

	        //������Ӧ״̬
	        response.setStatus(response.SC_PARTIAL_CONTENT);
	        
	        //д���ͻ���
	        response.getOutputStream().write(content, 0, content.length);
	*/        
//CCEnd SS1
	        
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
