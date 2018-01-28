/**
 * 成都工艺路线整合，工艺路线5种报表 liunan 2016-8-15
 */
package com.faw_qm.cappclients.conscapproute.controller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import java.io.InputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.net.URL;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.faw_qm.framework.remote.RemoteProperty;

import java.util.ArrayList;
import java.util.Vector;
import java.io.FileOutputStream;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import java.io.ByteArrayOutputStream;
/**
 * <p>Title: 成都工艺路线报表导出</p>
 * <p>Description: 成都专用，导出工艺路线报表类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 启明</p>
 * @author 刘楠
 * @version 1.0
 */
public class CdExportExcel
{
  private static final boolean verbose = false;
  private String makeDate = "";
  private String projectName = "";
  private String routelistNumber = "";
  private String genju = "";
  private String shuoming = "";
  private String pizhunInfo = "";
  private String shenheInfo = "";
  private String jiaoduiInfo = "";
  private String bianzhiInfo = "";
  private String faWang = "";
  private String type = "";
  private String typenum = "";
    
  public CdExportExcel()
  {
  }

  /**
   * 收集报表数据
   * @param routeListID String
   * @param IsExpandByProduct 是否按车展开（暂时默认总为真）
   * @throws QMException
   */
  public Vector gatherExportData(String routeListID) throws QMException
  {
    Vector result = new Vector();
    Class[] c = {String.class};
    Object[] oo = {routeListID};
    ArrayList allInformationColl = (ArrayList) CappRouteAction.useServiceMethod(
            "consTechnicsRouteService", "gatherExportData", c, oo);
    if(allInformationColl==null||allInformationColl.size()<5)
    {
    	return null;
    }
    String[] header = (String[]) allInformationColl.get(0);
    projectName = "项目名称:" + header[3]+" "+header[0];
    routelistNumber = header[1];
    makeDate = "编制日期:" + header[2];
    type=header[4];
          
    //说明
    Collection tail2 = (Collection) allInformationColl.get(3);
    if (tail2 != null && tail2.size() > 0)
    {
      Object[] des = tail2.toArray();
      if(des.length==1)
      {
      	genju = des[0].toString();
      }
      else if(des.length==2)
      {
      	genju = des[0].toString();
      	shuoming = des[1].toString();
      }
    }
    
    Vector wf = (Vector) allInformationColl.get(4);
    if (wf != null && wf.size() > 0)
    {
      ArrayList pizhun = (ArrayList) wf.elementAt(0);
      StringBuffer pizhunString = new StringBuffer("批准：");
      if (pizhun != null && pizhun.size() != 0)
      {
        for (int i = 0; i < pizhun.size(); i++)
        {
          Object p1 = pizhun.get(i);
          if (p1 != null)
          {
            pizhunString.append(p1.toString() + " ");
          }
        }
      }
      pizhunInfo = pizhunString.toString();
      
      ArrayList shenhe = (ArrayList) wf.elementAt(1);
      StringBuffer shenheBuffer = new StringBuffer("审核：");
      if (shenhe != null && shenhe.size() != 0) {
        for (int i = 0; i < shenhe.size(); i++) {
          Object p1 = shenhe.get(i);
          if (p1 != null) {
            shenheBuffer.append(p1.toString() + " ");
          }
        }
      }
      shenheInfo = shenheBuffer.toString();
      
      bianzhiInfo = "编制：" + wf.elementAt(4).toString();
    }

    String tail1 = (String) allInformationColl.get(2);
    faWang = "发往单位:" + tail1;

    Collection informationColl = (Collection) allInformationColl.get(1);
    for (Iterator iter = informationColl.iterator(); iter.hasNext(); )
    {
      Object[] arrayObjs = (Object[]) iter.next();
      //序号、更改标记、编号、版本、名称+见xxx、数量、制造路线、（装配路线、数量、父件）、bsoID、备注、影响互换、？？、保安重要
      String xuhao = (String) arrayObjs[0];
      String changeSign = (String) arrayObjs[1];
      String partNum = (String) arrayObjs[2];
      String version = (String) arrayObjs[3];
      partNum = partNum + version;
      String partName = (String) arrayObjs[4];
      String changiba = (String) arrayObjs[10];//“是否互换”
      String countInProduct = (String) arrayObjs[5];
      if(changeSign.equals("G"))
      {
        countInProduct = "";
      }
      String partMakeRoute = (String) arrayObjs[6];
      
      //不计算发图数
      String futushu = "";
      String remark = (String) arrayObjs[9];
      String safety = (String) arrayObjs[12];
      String colorFlag = (String) arrayObjs[13];
      
      ArrayList list = (ArrayList) arrayObjs[7];
      for (int i = 0; i < list.size(); i++)
      {
        if (i == 0)
        {
          String[] informationStr = (String[]) list.get(0);
          if(changeSign.equals("G"))
          {
            informationStr[1] = "";
          }
          if(type.equals("艺废"))//注意中间带空格
          {
          	String sycx = (String) arrayObjs[10];
          	//序号、更改标记、编号、名称、数量、制造、（装配、数量、上级件）、首用车型、备注
          	String[] array1 = {
                      xuhao, changeSign, partNum, partName, countInProduct,
                      partMakeRoute, informationStr[0],sycx,remark};
            result.addElement(array1);
            array1=null;
          }
          else
          {
          	//序号、更改标记、编号、名称、影响互换、数量、制造、装配、数量、上级件、保安、发图数、颜色件标识、备注
          	String[] array1 = {
                      xuhao, changeSign, partNum, partName, changiba, countInProduct,
                      partMakeRoute, informationStr[0], informationStr[2], informationStr[1],safety,futushu,colorFlag,remark};
            result.addElement(array1);
            array1=null;
          }
        }
        else
        {
          String[] informationStr = (String[]) list.get(i);
          if(changeSign.equals("G"))
          {
            informationStr[1] = "";
          }
          if(type.equals("艺废"))
          {
          	String[] array2 = {"", "", "", "", "", "","", informationStr[0],"",""};
          	result.addElement(array2);
          	array2=null;
          }
          else
          {
          	String[] array2 = {"", "", "", "", "", "","", informationStr[0], informationStr[1], informationStr[2],"","","",""};
          	result.addElement(array2);
          	array2=null;
          }
        }
      }
    }
    return result;
  }

  /**
   * 输出报表
   * @param path
   * @roseuid 42C363440215
   */
  public boolean printFile(String path, Vector result)
  {
  	if(type.equals("艺废"))
	  {
	  	return printdisaffirmFile(path, result);
	  }
	  else
	  {
	  	return printcommonFile(path, result);
	  }
  }
  
  public boolean printcommonFile(String path, Vector result)
  {
  	String urlroot = "http://" + RemoteProperty.getProperty("server.hostName", "") + RemoteProperty.getProperty("routeListTemplate", "/PhosphorPDM/template/");
  	String url = "";
  	if(type.equals("艺准"))
  	{
  		url = urlroot + "cdyz.xls";
  	}
  	else if(type.equals("前准"))
  	{
  		url = urlroot + "cdqz.xls";
  	}
  	else if(type.equals("临准"))
  	{
  		url = urlroot + "cdlz.xls";
  	}
  	else if(type.equals("试制"))
  	{
  		url = urlroot + "cdsz.xls";
  	}
  	else if(type.equals("艺废"))
  	{
  		url = urlroot + "cdyf.xls";
  	}
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
      return false;
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
      return false;
    }

    HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
    HSSFRow row = sheet.getRow(2);
    HSSFCell cell = row.getCell( (short) 6); //第2行第6列 编制日期
    cell.setCellValue(makeDate);

    row = sheet.getRow(3);
    cell = row.getCell( (short) 0);
    cell.setCellValue(projectName); //第3行0列 项目名称

    row = sheet.getRow(22);
    cell = row.getCell( (short) 0);
    cell.setCellValue(genju); //第23行0列 根据

    row = sheet.getRow(23);
    cell = row.getCell( (short) 0);
    cell.setCellValue(shuoming); //第24行0列 说明
    
    row = sheet.getRow(24);
    cell = row.getCell( (short) 0);
    cell.setCellValue(pizhunInfo); //第25行0列 批准
    cell = row.getCell( (short) 3);
    cell.setCellValue(shenheInfo); //第25行3列 审核
    cell = row.getCell( (short) 7);
    cell.setCellValue(bianzhiInfo); //第25行7列 编制
    cell = row.getCell( (short) 11);
    cell.setCellValue(routelistNumber); //第25行10列 艺准号
    
    row = sheet.getRow(25);
    cell = row.getCell( (short) 0);
    cell.setCellValue(faWang); //第26行0列 发往单位
    
    //处理汇总结果
    if (result != null && result.size() > 0)
    {
      int pageNumber = 0;
      int psize = result.size();
      if (psize % this.getRowNumber() == 0)
      {
        pageNumber = psize / this.getRowNumber();
      }
      else
      {
        pageNumber = 1 + (psize / this.getRowNumber());
      }
      HSSFSheet sheet1 = null;
      if(pageNumber>1)
      {
      	sheet1 = wb.cloneSheet(0); //缓存空页面
      }
      int page = 0;
      for (int i = 0; i < result.size(); i++)
      {
        Object[] o = (Object[]) result.elementAt(i);
        //System.out.println(Arrays.deepToString(o));
        if (i % this.getRowNumber() == 0)//余数为0,即整除
        {
          page++;
          if (page == 1)
          {
            row = sheet.getRow(25);
            cell = row.getCell( (short) 9);
            cell.setCellValue("第1页  " + "共" + pageNumber + "页");
          }
          else if (page > 1)
          {
            sheet = sheet1;
            wb.setSheetName(page - 1, "Sheet" + page);
            if (page < pageNumber)
            {
              sheet1 = wb.cloneSheet(page - 1);
            }
            row = sheet.getRow(25);
            cell = row.getCell( (short) 9);
            cell.setCellValue("第" + page + "页  共" + pageNumber + "页");
          }
        }
        if (verbose)
        {
          System.out.println("i=" + i + "    page=" + page);
        }  
        int rowIndex = 5 + i % this.getRowNumber();
        row = sheet.getRow(rowIndex);
        if (verbose)
        {
          System.out.println("row=" + rowIndex);
        }
        for (int j = 0; j < this.getColNumber(); j++)
        {
          cell = row.getCell( (short) j);
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
    }
    if(result.size()==0)
    {
    	row = sheet.getRow(25);
    	cell = row.getCell( (short) 9);
    	cell.setCellValue("第 1 页  " + "共 1 页");
    }
    
    try
    {
      FileOutputStream out = new FileOutputStream(path);
      wb.write(out);
      out.close();
      return true;
    }
    catch (Exception ex1)
    {
      ex1.printStackTrace();
      return false;
    }
  }
  
  
  public boolean printdisaffirmFile(String path, Vector result)
  {
  	String urlroot = "http://" +
                 RemoteProperty.getProperty("server.hostName", "") +
                 RemoteProperty.getProperty("routeListTemplate",
                                     "/PhosphorPDM/template/");
    
    String url = urlroot + "cdyf.xls";
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
    	return false;
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
    	return false;
    }
    HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
    HSSFRow row = sheet.getRow(0);
    HSSFCell cell = row.getCell( (short) 5); //第2行第6列 编制日期
    cell.setCellValue(makeDate);
    
    row = sheet.getRow(3);
    cell = row.getCell( (short) 0);
    cell.setCellValue(projectName); //第3行0列 项目名称
    
    row = sheet.getRow(21);
    cell = row.getCell( (short) 0);
    cell.setCellValue(genju); //第22行0列 根据
    
    row = sheet.getRow(22);
    cell = row.getCell( (short) 0);
    cell.setCellValue(shuoming); //第23行0列 说明
    
    row = sheet.getRow(23);
    cell = row.getCell( (short) 0);
    cell.setCellValue(pizhunInfo); //第24行0列 批准
    
    cell = row.getCell( (short) 3);
    cell.setCellValue(shenheInfo); //第24行3列 审核
    cell = row.getCell( (short) 4);
    cell.setCellValue(jiaoduiInfo); //第24行4列 校对
    cell = row.getCell( (short) 6);
    cell.setCellValue(bianzhiInfo); //第24行7列 编制
    cell = row.getCell( (short) 8);
    cell.setCellValue(routelistNumber); //第26行9列 艺准号
    row = sheet.getRow(24);
    cell = row.getCell( (short) 0);
    cell.setCellValue(faWang); //第27行0列 发往单位
    
    //处理汇总结果
    if (result != null && result.size() > 0)
    {
    	int pageNumber = 0;
    	int psize = result.size();
    	if (psize % this.getDisaffirmRowNumber() == 0)
    	{
    		pageNumber = psize / this.getDisaffirmRowNumber();
    	}
    	else
    	{
    		pageNumber = 1 + (psize / this.getDisaffirmRowNumber());
    	}
    	HSSFSheet sheet1 = null;
    	if(pageNumber>1)
    	{
        sheet1 = wb.cloneSheet(0); //缓存空页面
      }
      int page = 0;
      for (int i = 0; i < result.size(); i++)
      {
      	Object[] o = (Object[]) result.elementAt(i);
      	if (i % this.getDisaffirmRowNumber() == 0)//余数为0,即整除
      	{
      		page++;
      		if (page == 1)
      		{
      			row = sheet.getRow(24);
      			cell = row.getCell( (short) 8);
      			cell.setCellValue("第1页  " + "共" + pageNumber + "页");
      		}
      		else if (page > 1)
      		{
      			sheet = sheet1;
      			wb.setSheetName(page - 1, "Sheet" + page);
      			if (page < pageNumber)
      			{
      				sheet1 = wb.cloneSheet(page - 1);
      			}
      			row = sheet.getRow(24);
      			cell = row.getCell( (short) 8);
      			cell.setCellValue("第" + page + "页  共" + pageNumber + "页");
      		}
      	}
      	if (verbose)
      	{
      		System.out.println("i=" + i + "    page=" + page);
      	}
      	
      	int rowIndex = 5 + i % this.getDisaffirmRowNumber();
      	row = sheet.getRow(rowIndex);
      	if (verbose)
      	{
      		System.out.println("row=" + rowIndex);
      	}
      	for (int j = 0; j < this.getdisaffirmColNumber(); j++)
      	{
      		cell = row.getCell( (short) j);
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
    }
    try
    {
    	FileOutputStream out = new FileOutputStream(path);
    	wb.write(out);
    	out.close();
    	return true;
    }
    catch (Exception ex1)
    {
    	ex1.printStackTrace();
    	return false;
    }
  }
  
  /**
   * 艺准列表的列数
   */
  private int getColNumber()
  {
    return 13;
  }
  
  /**
   * 艺毕列表的列数
   */
  private int getcompleteColNumber()
  {
  	return 9;
  }
  
  /**
   * 艺废列表的列数
   */
  private int getdisaffirmColNumber()
  {
  	return 9;
  }
  
  /**
   * 每页的零件行数
   */
  private int getRowNumber()
  {
    return 18;
  }
  
  /**
   * 艺废每页的零件行数
   */
  private int getDisaffirmRowNumber()
  {
    return 16;
  }
}
