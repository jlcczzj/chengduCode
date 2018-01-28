/**
 * 生成程序GetReportInformServlet.java    1.0    2012-2-14
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.SearchResult;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: Excel文件导出。</p>
 * <p>Description:生成报表并将文件下载到客户端的类 </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public class GetReportInformServlet extends HttpServlet
{
    static final long serialVersionUID = -1L;
    public GetReportInformServlet()
    {

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
    {
            doPost(request,response);
    }
    /**
     * doPost方法处理文档文件内容下载
     * @param request 请求
     * @param response 响应
     * @roseuid 3E0ADB250394
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
    {
         String listBsoID = request.getParameter("bsoID");
         
         String dirStr = RemoteProperty.getProperty("com.sg.bomList.dir", "D:/");
         String userName = "";
         String sourcefile = null;
         String listnumber = "";
         try
         {
             // 下载文件是用到的文件名字和路径

             sourcefile = dirStr;
             File tempFile = null;
             // 获得当前用户，此处为每位用户创建一个报表生成文件，下载完成后删除
             SessionService service = (SessionService)EJBServiceHelper.getService("SessionService");
             UserIfc user = service.getCurUserInfo();
             userName = user.getUsersName();
             tempFile = new File(dirStr + "/" + userName + "List.csv");
             sourcefile = sourcefile + "/" + userName + "List.csv";
             PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
             TechnicsRouteListIfc routeList = (TechnicsRouteListIfc)pservice.refreshInfo(listBsoID);
             // 从session中获得表单结果集合
             listnumber = routeList.getRouteListNumber();
             HttpSession session = request.getSession();
             String returnStr;
             Collection coll = null;
             Vector vec = new Vector();
             returnStr = (String)session.getAttribute("bsbom");
             FileWriter fw = new FileWriter(tempFile.getPath(), false);
             fw.write(returnStr);
             fw.close();
             // 下面要从服务端中将此文件下载的客户端上
             response.reset();
             // 设置响应状态
             // response.setStatus(response.SC_PARTIAL_CONTENT);
             response.setHeader("Content-Disposition", "attachment; filename=\"" + userName + "_" + listnumber + "_List.csv" + "\"");
             response.setContentType("application/vnd.ms-excel");

             javax.servlet.ServletOutputStream sos = null;

             // 将刚才保存好的文件变成文件流，然后写到本地客户端
             java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourcefile);

             try
             {
                 sos = response.getOutputStream();
                 int i;
                 while((i = fileInputStream.read()) != -1)
                 {
                     sos.write(i);
                     sos.flush();
                 }
             }finally
             {
                 fileInputStream.close();
                 sos.close();
                 //删除临时文件
                 File tempFile1 = new File(dirStr + "/" + userName + "List.csv");
                 if(tempFile1.exists())
                 {

                     if(tempFile1.isFile())
                     {

                         tempFile1.delete();
                     }
                 }
             }
         }catch(QMException e)
         {
            e.printStackTrace();
            return;
         }catch(Exception e)
         {
        	 e.printStackTrace();
             return;
         }
     }
}
