/** 
 * ���� ExportBomListServlet.java	1.0 2016-11-11
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.gybom.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Vector;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.persist.ejb.service.PersistService;

import com.faw_qm.util.EJBServiceHelper;

import com.faw_qm.users.model.UserIfc;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.part.model.QMPartIfc;

/**
 * ��������BOM ExportBomListServlet
 */
public class ExportERPBomListServlet extends HttpServlet
{
    private static boolean VERBOSE = true;
    private Vector vec = new Vector();
    public ExportERPBomListServlet()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, java.io.IOException
    {
        doPost(request, response);
    }


    /**
     * doPost���������ĵ��ļ��������ء�
     * @param request ����
     * @param response ��Ӧ��
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, java.io.IOException
    {
        if (VERBOSE)
        {
            System.out.println("����" + this.getClass().getName() + "\n" +
                               "����: doPost(HttpServletRequest request, HttpServletResponse response)" +
                               "\n" + "����: request" + request + "\n" +
                               "����: response" + response + "\n" +
                               "����: ִ��doPost��ʼ");
        }
        try
        {
            //��öϵ���������ʼ��
            String range = request.getHeader("RANGE");
            System.out.println("range range range ===="+range);
            //��ȡ����
            String carModelCode = request.getParameter("carModelCode");
            if (VERBOSE)
            {
                System.out.println("carModelCode: " + carModelCode);
            }
            PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
//            if(carModelCode.indexOf("Part_")!=-1)
//            {
            	QMPartIfc part = (QMPartIfc)ps.refreshInfo(carModelCode);
            	
            //}
            	GYBomExportToERP helper = new GYBomExportToERP();
            vec = (Vector)helper.getExportERPBomList(part);
            System.out.println("doPost  vec3333333333333333333==="+vec.size());
            String encoding = request.getParameter("encoding");
            if (encoding == null || encoding.trim().length() == 0)
            {
                encoding = "GBK";
            }
            String fileName = part.getPartNumber()+".xls";
            try
            {
                byte abyte0[] = fileName.getBytes(encoding);
                fileName = new String(abyte0, 0);

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
            response.setHeader("Content-Disposition",
                               "attachment; filename=\"" + fileName + "\"");

            if (range != null)
            {
                String startPoint = range.substring(range.indexOf("="));
                //������Ӧ״̬
                response.setStatus(response.SC_PARTIAL_CONTENT);
                response.addHeader("Content-Range",
                        "bytes " + startPoint + 0 + "/" + 0);
            }
            //д���ͻ���
            boolean flag=false;
            try
            {
            	flag = printcommonFile(response.getOutputStream());
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
            System.out.println("doPost  flag==="+flag);
        	if(!flag)
        	{
        		SessionService sessionser = (SessionService) EJBServiceHelper.getService("SessionService");
		        UserIfc user = sessionser.getCurUserInfo();
        		throw new ServletException("�û� "+user.getUsersDesc()+" �����ļ� "+fileName+" ����ʧ��1111��");
        	}

            if (VERBOSE)
            {
                System.out.println("����" + this.getClass().getName() + "\n" +
                                   "����: doPost(HttpServletRequest request, HttpServletResponse response)" +
                                   "\n" +
                                   "����: ִ��doPost������");
            }
        }
        catch (QMException ex)
        {
            throw new ServletException(ex.getClientMessage());
        }
    }
  
  /**
   * ���ݵ������������챨���ļ�
   */
  public boolean getFile(Vector vec1, OutputStream out)throws Exception
  {
  	boolean flag = false;
  	vec = vec1;
    flag= printcommonFile(out);
    return flag;
  }
        
  public boolean printcommonFile(OutputStream out)
  {
  	try
  	{
  		PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
  		String urlroot = "http://" + RemoteProperty.getProperty("server.hostName", "") + RemoteProperty.getProperty("routeListTemplate", "/PhosphorPDM/template/");
      String url = urlroot + "erpbomlist.xls";
      
      System.out.println("URL = "+url);
      InputStream stream = null;
      try
      {
      	URL aurl = new URL(url);
      	stream = aurl.openStream();
      }
      catch (Exception ex)
      {
      	ex.printStackTrace();
      }
      POIFSFileSystem fs = null;
      HSSFWorkbook wb = null;
      try
      {
      	fs = new POIFSFileSystem(stream);
      	wb = new HSSFWorkbook(fs);
      }
      catch (IOException e)
      {
      	e.printStackTrace();
      }
      
      HSSFSheet sheet = wb.getSheetAt(0); //��õ�һ��ҳ��
      HSSFRow row = null;
      
      HSSFCell cell = null; //��1��A�� ���
      
      //������ܽ��
      if (vec != null && vec.size() > 0)
      {
      	for (int i = 0; i < vec.size(); i++)
      	{
      		Object[] o = (Object[]) vec.elementAt(i);
            int rowIndex = 2 + i;
            row = sheet.createRow(rowIndex);
            System.out.println("row=" + rowIndex);
            System.out.println("getColNumber=" + this.getColNumber());
            for (int j = 0; j < this.getColNumber(); j++)
            {
            	cell = row.createCell( (short) j);
            	if (o[j] == null)
            	{
            		cell.setCellValue("");
            	}
            	else
            	{
            	   cell.setCellValue(o[j].toString());	
            	}
            }
        }
        wb.write(out);
        out.close();
      }
    }
    catch (Exception ex1)
    {
    	ex1.printStackTrace();
    	return false;
    }
    return true;
  }
  
  //ÿ�е�����
  private int getColNumber()
  {
  	return 4;
  }
}
