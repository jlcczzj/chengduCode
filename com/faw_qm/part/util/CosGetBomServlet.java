/**
 * 生成程序 GetBomServlet.java    1.0    2006/04/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Title: Excel文件导出</p>
 * <p>Description: 为零部件物料清单导出定制的BOM生成Excel和并下载到客户端的类。</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明</p>
 * @author 刘楠
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
    * doPost方法处理文档文件内容下载
    * @param request 请求
    * @param response 响应
    * @roseuid 3E0ADB250394
    */
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,java.io.IOException
   {
       //获得相关属性，分别是报表的类型、该零部件的id、所要显示的属性集合、所要显示的零部件来源、所要显示的零部件类型。
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
           //下载文件是用到的文件名字和路径
          
           sourcefile = dirStr;
           File tempFile = null;

           //创建一个可写工作簿
           WritableWorkbook workbook = null;

           //创建这个工作簿的一个可写工作表
           WritableSheet sheet = null;

           //一些临时变量，用于写到Excel中
           Label l = null;

           //获得当前用户，此处为每位用户创建一个报表生成文件，下载完成后删除
           SessionService service = (SessionService) EJBServiceHelper.getService("SessionService");
           PersistService pservice = (PersistService) EJBServiceHelper.getService("PersistService");
           QMPartIfc part=(QMPartIfc)pservice.refreshInfo(PartBsoID);
           partnumber=part.getPartNumber();
           UserIfc user = service.getCurUserInfo();
            userName = user.getUsersName();

           //从session中获得表单结果集合
        
           HttpSession session = request.getSession();
           SearchResult searchResult;
           Collection coll = null;
           Vector vec = new Vector();
           PartServiceHelper pshelper = new PartServiceHelper();
//统计表
           if (actionName.equals("bsbom"))
           {
               tempFile = new File(dirStr + "/"+userName+"List.xls");
               sourcefile = sourcefile + "/"+userName+"List.xls";
               workbook = Workbook.createWorkbook(tempFile);
               sheet = workbook.createSheet("统计表", 0);
               searchResult = (SearchResult)session.getAttribute("bsbom");
               coll = (Collection)searchResult.getValue();
               vec = (Vector)coll;
             
           }
//分级物料清单
           else if (actionName.equals("ass"))
           {
               tempFile = new File(dirStr + "/"+userName+"List.xls");
               sourcefile = sourcefile + "/"+userName+"List.xls";
               workbook = Workbook.createWorkbook(tempFile);
               sheet = workbook.createSheet("分级物料清单", 0);
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
           //之前的内容都是写在缓存中，现在把它写到制定的Excel文件中
           workbook.write();

           //写完后关闭
           workbook.close();
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
      
       //下面要从服务端中将此文件下载的客户端上
       response.reset();
//     设置响应状态
       //response.setStatus(response.SC_PARTIAL_CONTENT);
       response.setHeader("Content-Disposition","attachment; filename=\"" +userName+"_"+partnumber+"_List.xls"+"\"");
       response.setContentType("application/vnd.ms-excel");
      
       javax.servlet.ServletOutputStream sos = null;

       //将刚才保存好的文件变成文件流，然后写到本地客户端
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
           //删除临时文件
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
