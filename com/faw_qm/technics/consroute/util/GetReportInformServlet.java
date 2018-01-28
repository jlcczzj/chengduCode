/**
 * ���ɳ���GetReportInformServlet.java    1.0    2012-2-14
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: Excel�ļ�������</p>
 * <p>Description:���ɱ������ļ����ص��ͻ��˵��� </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
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
     * doPost���������ĵ��ļ���������
     * @param request ����
     * @param response ��Ӧ
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
             // �����ļ����õ����ļ����ֺ�·��

             sourcefile = dirStr;
             File tempFile = null;
             // ��õ�ǰ�û����˴�Ϊÿλ�û�����һ�����������ļ���������ɺ�ɾ��
             SessionService service = (SessionService)EJBServiceHelper.getService("SessionService");
             UserIfc user = service.getCurUserInfo();
             userName = user.getUsersName();
             tempFile = new File(dirStr + "/" + userName + "List.csv");
             sourcefile = sourcefile + "/" + userName + "List.csv";
             PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
             TechnicsRouteListIfc routeList = (TechnicsRouteListIfc)pservice.refreshInfo(listBsoID);
             // ��session�л�ñ��������
             listnumber = routeList.getRouteListNumber();
             HttpSession session = request.getSession();
             String returnStr;
             Collection coll = null;
             Vector vec = new Vector();
             returnStr = (String)session.getAttribute("bsbom");
             FileWriter fw = new FileWriter(tempFile.getPath(), false);
             fw.write(returnStr);
             fw.close();
             // ����Ҫ�ӷ�����н����ļ����صĿͻ�����
             response.reset();
             // ������Ӧ״̬
             // response.setStatus(response.SC_PARTIAL_CONTENT);
             response.setHeader("Content-Disposition", "attachment; filename=\"" + userName + "_" + listnumber + "_List.csv" + "\"");
             response.setContentType("application/vnd.ms-excel");

             javax.servlet.ServletOutputStream sos = null;

             // ���ղű���õ��ļ�����ļ�����Ȼ��д�����ؿͻ���
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
                 //ɾ����ʱ�ļ�
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
