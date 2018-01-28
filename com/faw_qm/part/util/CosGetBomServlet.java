/**
 * ���ɳ��� GetBomServlet.java    1.0    2006/04/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.part.util;

import java.io.File;
import java.util.Vector;
import java.util.Collection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.SearchResult;
import com.faw_qm.persist.ejb.service.PersistService;

import javax.servlet.http.HttpSession;

/**
 * <p>Title: Excel�ļ�����</p>
 * <p>Description: Ϊ�㲿�������嵥�������Ƶ�BOM����Excel�Ͳ����ص��ͻ��˵��ࡣ</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ����</p>
 * @author ���
 * @version 1.0
 */
public class CosGetBomServlet extends HttpServlet
{

   public CosGetBomServlet()
   {

   }
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,java.io.IOException
   {
           doPost(request,response);
   }
   /**
    * doPost���������ĵ��ļ���������
    * @param request ����
    * @param response ��Ӧ
    * @roseuid 3E0ADB250394
    */
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,java.io.IOException
   {
       //���������ԣ��ֱ��Ǳ�������͡����㲿����id����Ҫ��ʾ�����Լ��ϡ���Ҫ��ʾ���㲿����Դ����Ҫ��ʾ���㲿�����͡�
       String actionName = request.getParameter("action");
       String PartBsoID = request.getParameter("PartID");
       String attributeName1 = request.getParameter("attributeName1");
       String source = request.getParameter("source");
       String type = request.getParameter("type");
       //CCBegin by chudaming 20091020
       //String dirStr = "/"+"data"+"/"+"doc";
       String dirStr = RemoteProperty.getProperty("com.sg.bomList.dir", "D:/");
       //CCEnd by chudaming 20091020
       String userName="";
       String sourcefile = null;
       String partnumber="";
       try
       {
           //�����ļ����õ����ļ����ֺ�·��
          
           sourcefile = dirStr;
           File tempFile = null;

           //����һ����д������
           WritableWorkbook workbook = null;

           //���������������һ����д������
           WritableSheet sheet = null;

           //һЩ��ʱ����������д��Excel��
           Label l = null;

           //��õ�ǰ�û����˴�Ϊÿλ�û�����һ�����������ļ���������ɺ�ɾ��
           SessionService service = (SessionService) EJBServiceHelper.getService("SessionService");
           PersistService pservice = (PersistService) EJBServiceHelper.getService("PersistService");
           QMPartIfc part=(QMPartIfc)pservice.refreshInfo(PartBsoID);
           partnumber=part.getPartNumber();
           UserIfc user = service.getCurUserInfo();
            userName = user.getUsersName();

           //��session�л�ñ��������
        
           HttpSession session = request.getSession();
           SearchResult searchResult;
           Collection coll = null;
           Vector vec = new Vector();
           PartServiceHelper pshelper = new PartServiceHelper();
//ͳ�Ʊ�
           if (actionName.equals("bsbom"))
           {
               tempFile = new File(dirStr + "/"+userName+"List.xls");
               sourcefile = sourcefile + "/"+userName+"List.xls";
               workbook = Workbook.createWorkbook(tempFile);
               sheet = workbook.createSheet("ͳ�Ʊ�", 0);
               searchResult = (SearchResult)session.getAttribute("bsbom");
               coll = (Collection)searchResult.getValue();
               vec = (Vector)coll;
             
           }
//�ּ������嵥
           else if (actionName.equals("ass"))
           {
               tempFile = new File(dirStr + "/"+userName+"List.xls");
               sourcefile = sourcefile + "/"+userName+"List.xls";
               workbook = Workbook.createWorkbook(tempFile);
               sheet = workbook.createSheet("�ּ������嵥", 0);
               searchResult = (SearchResult)session.getAttribute("assbom");
               coll = (Collection)searchResult.getValue();
               vec = (Vector)coll;
             
           }
          
           int rolsize = vec.size();
           for (int i = 0; i < rolsize; i++)
           {
               Object[] obj = (Object[]) vec.elementAt(i);
               int cowsize = obj.length;

              
            	   if (actionName.equals("bsbom"))
               {
                   for (int j = 2; j < cowsize; j++)
                   {
                       String ss = obj[j].toString();

                       l = new jxl.write.Label(j - 2, i, ss);

                       sheet.addCell(l);
                   }
               }

               else if (actionName.equals("ass"))
               {
            	   for (int j = 2; j < cowsize; j++)
                   {
                       String ss = obj[j].toString();

                       l = new jxl.write.Label(j - 2, i, ss);

                       sheet.addCell(l);
                   }
               }
              
           }
           //֮ǰ�����ݶ���д�ڻ����У����ڰ���д���ƶ���Excel�ļ���
           workbook.write();

           //д���ر�
           workbook.close();
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
      
       //����Ҫ�ӷ�����н����ļ����صĿͻ�����
       response.reset();
//     ������Ӧ״̬
       //response.setStatus(response.SC_PARTIAL_CONTENT);
       response.setHeader("Content-Disposition","attachment; filename=\"" +userName+"_"+partnumber+"_List.xls"+"\"");
       response.setContentType("application/vnd.ms-excel");
      
       javax.servlet.ServletOutputStream sos = null;

       //���ղű���õ��ļ�����ļ�����Ȼ��д�����ؿͻ���
       java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourcefile);
      
       try
       {
           sos = response.getOutputStream();
           int i;
           while ( (i = fileInputStream.read()) != -1)
           {
               sos.write(i);
               sos.flush();
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       finally
       {
           fileInputStream.close();
           sos.close();
           //ɾ����ʱ�ļ�
          File tempFile = new File(dirStr + "/"+userName+"List.xls");
          if(tempFile.exists())
          {
        	
        	  if(tempFile.isFile())
        	  {
        		  
        		  tempFile.delete();
        	  }
          }
       }
   }
}
