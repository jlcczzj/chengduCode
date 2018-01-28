/** 
 * 程序 ExportFirstLeveList.java	1.0 2016-8-11
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 修改导出总成规则及方法 liuyuzhu 2017-07-25
 * SS2 工艺BOM导出列表去掉版本列，将版本和零件号进行合并 maxiaotong 2017-09-20
 */
package com.faw_qm.gybom.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.sql.Timestamp;

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
import com.faw_qm.issuerequest.ejb.service.IssueRequestService;
import com.faw_qm.issuerequest.model.*;
import com.faw_qm.util.EJBServiceHelper;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.ByteArrayOutputStream;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;

import com.faw_qm.users.model.UserIfc;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.workflow.engine.ejb.service.WfEngineHelper;
import com.faw_qm.workflow.engine.model.WfActivityIfc;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.work.model.WfBallotInfo;
import com.faw_qm.workflow.work.model.WorkItemIfc;
import com.faw_qm.users.model.ActorInfo;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.issuerequest.ejb.entity.*;
import com.faw_qm.part.model.QMPartIfc;

/**
 * 下载文档文件内容 ExportFirstLeveList
 */
public class ExportCheckBom extends HttpServlet
{
    private static boolean VERBOSE = true;
    
    private String num = "编号：";//A列
    private String source = "依据文件：";//H列
    private String testTime = "试装时间：";//M列
    private String phase = "阶段：";//P列
    private String company = "";
    private Vector vec = new Vector();

    public ExportCheckBom()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, java.io.IOException
    {
        doPost(request, response);
    }


    /**
     * doPost方法处理文档文件内容下载。
     * @param request 请求；
     * @param response 响应；
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, java.io.IOException
    {
        if (VERBOSE)
        {
            System.out.println("类名" + this.getClass().getName() + "\n" +
                               "方法: doPost(HttpServletRequest request, HttpServletResponse response)" +
                               "\n" + "参数: request" + request + "\n" +
                               "参数: response" + response + "\n" +
                               "描述: 执行doPost开始");
        }
        try
        {
            //获得断点续传的起始点
            String range = request.getHeader("RANGE");
            //System.out.println("range range range ===="+range);
            //获取参数
            String id = request.getParameter("id");
            if (VERBOSE)
            {
                System.out.println("id: " + id);
            }
            PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
            QMPartIfc info = (QMPartIfc)ps.refreshInfo(id);
            GYBomHelper helper = new GYBomHelper();
            //CCBegin SS1
//            vec = (Vector)helper.getExportFirstLeveList(id, carModelCode, dwbs);
            vec = (Vector)helper.checkBom(id);
            //CCEnd SS1

            String encoding = request.getParameter("encoding");
            if (encoding == null || encoding.trim().length() == 0)
            {
                encoding = "GBK";
            }
            String fileName = info.getPartNumber()+".xls";
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
                //设置响应状态
                response.setStatus(response.SC_PARTIAL_CONTENT);
                response.addHeader("Content-Range",
                        "bytes " + startPoint + 0 + "/" + 0);
            }
            //写到客户端
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
        		throw new ServletException("用户 "+user.getUsersDesc()+" 导出文件 "+fileName+" 导出失败1111！");
        	}

            if (VERBOSE)
            {
                System.out.println("类名" + this.getClass().getName() + "\n" +
                                   "方法: doPost(HttpServletRequest request, HttpServletResponse response)" +
                                   "\n" +
                                   "描述: 执行doPost结束。");
            }
        }
        catch (QMException ex)
        {
            throw new ServletException(ex.getClientMessage());
        }
    }
  
  /**
   * 根据单个关联，构造报表文件
   */
  public boolean getFile(Vector vec1, OutputStream out)throws Exception
  {
  	boolean flag = false;
  	vec = vec1;
    flag= printcommonFile(out);
    //System.out.println("printcommonFile  flag==="+flag);
    return flag;
  }
        
  public boolean printcommonFile(OutputStream out)
  {
  	try
  	{
  		PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
  		String urlroot = "http://" + RemoteProperty.getProperty("server.hostName", "") + RemoteProperty.getProperty("routeListTemplate", "/PhosphorPDM/template/");
  		//CCBegin SS1
//      String url = urlroot + "firstlevellist.xls";
  		String url = urlroot + "gybomllist.xls";
  		//CCEnd SS1
      //System.out.println("URL = "+url);
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
      
      HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
      HSSFRow row = null;
      
      HSSFCell cell = null; //第1行A列 编号
      
      //处理汇总结果
      if (vec != null && vec.size() > 0)
      {
      	for (int i = 0; i < vec.size(); i++)
      	{
      		Object[] o = (Object[]) vec.elementAt(i);
            int rowIndex = 2 + i;
            row = sheet.createRow(rowIndex);
//            System.out.println("o=" + o.length);
//            System.out.println("row=" + rowIndex);
//            System.out.println("getColNumber()=" + getColNumber());
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
  
  //每行的列数
  private int getColNumber()
  {
  	//CCBegin SS2
//  	return 8;
  	return 7;
   //CCEnd SS2
  }
}
