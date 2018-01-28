/**
 *
 * SS1 前准修改类型编号。 liunan 2013-1-24
 * SS2 根据解放要求，艺准、前准、临准、艺试准的报表中要增加“保安重要”属性的显示。 liunan 2013-6-17
 * SS3 A004-2015-3278 更改艺准模板记录编号。由现有CX011改为ZY011 liunan 2016-3-8
 * SS4 A004-2016-3310 艺准增加颜色件列。 liunan 2016-3-9
 * SS5 升级poi到3.6，注释//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
 */
package com.faw_qm.cappclients.capproute.controller;

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

//CCBegin by liunan 2011-08-25
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.ByteArrayOutputStream;
//CCEnd by liunan 2011-08-25
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *   //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
 *
 * @author 刘明
 * @version 1.0
 */
public class ExportExcel {

  private String makeDate = "";
  private String projectName = "";
  private String routelistNumber = "";
  //CCBegin by liunan 2012-01-30 将result挪到方法里，否则会出现结果混乱的情况
  //private Vector result = new Vector();
  //CCEnd by liunan 2012-01-30
  private static final boolean verbose = false;
  private String genju = "";
  private String shuoming = "";
  private String pizhunInfo = "";
  private String shenheInfo = "";
  private String jiaoduiInfo = "";
  private String bianzhiInfo = "";
  private String faWang = "";
//CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出
  private String type = "";
//CCEndby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出

    //CCBegin by liunan 2011-08-25 表头类型标号根据类型出
    private String typenum = "";
    //CCEnd by liunan 2011-08-25
    
  public ExportExcel() {
  }

  /**
   * 收集报表数据
   * @param routeListID String
   * @param IsExpandByProduct 是否按车展开（暂时默认总为真）
   * @throws QMException
   */
  //CCBegin by liunan 2012-01-30 将result挪到方法里
  //public void gatherExportData(String routeListID, String IsExpandByProduct) throws
  public Vector gatherExportData(String routeListID, String IsExpandByProduct) throws 
  //CCEnd by liunan 2012-01-30
      QMException {
    //CCBegin by liunan 2012-01-30 将result挪到方法里
    Vector result = new Vector();
    //CCEnd by liunan 2012-01-30
    Class[] c = {
        String.class, String.class};
    Object[] oo = {
        routeListID, IsExpandByProduct};
//  CCBeginby leixiao 2009-2-13 原因：解放升级工艺路线,报表统一从报表服务调    
//    ArrayList allInformationColl = (ArrayList) CappRouteAction.useServiceMethod(
//        "TechnicsRouteService", "gatherExportData", c, oo);
    ArrayList allInformationColl = (ArrayList) CappRouteAction.useServiceMethod(
            "JFService", "gatherExportData", c, oo);
//  CCBeginby leixiao 2009-2-13 原因：解放升级工艺路线,报表统一从报表服务调     
    //CCBegin by leixiao 2010-1-5 零件列表为空时导出数组越界
    if(allInformationColl==null||allInformationColl.size()<5)
	//CCBegin by liunan 2012-01-30 将result挪到方法里
	//return;
	return null;
	//CCEnd by liunan 2012-01-30
//CCEnd by leixiao 2010-1-5 
    String[] header = (String[]) allInformationColl.get(0);
    projectName = "项目名称:" + header[3]+" "+header[0];
    routelistNumber = header[1];
    makeDate = "编制日期:" + header[2];
//  CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出
    type=header[4];
//  CCEndby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出

          //CCBegin by liunan 2011-08-25 表头类型标号根据类型出
          //CCBegin SS3
          if(type.equals("工 艺 准 备 完 毕 通 知 书"))
          {
          	//typenum = "Z28-CX011-JL-005-00";
          	typenum = "Z28-ZY011-JL-005-00";
          }
          else if(type.equals("工 艺 准 备 通 知 书"))
          {
          	//typenum = "Z28-CX011-JL-001-00";
          	typenum = "Z28-ZY011-JL-001-00";
          }
          else if(type.equals("临 时 工 艺 准 备 通 知 书"))
          {
          	//typenum = "Z28-CX011-JL-003-00";
          	typenum = "Z28-ZY011-JL-003-00";
          }
          else if(type.equals("提 前 工 艺 准 备 通 知 书"))
          {
          	//CCBegin SS1
          	//typenum = "Z28-CX011-JL-004-00";
          	//typenum = "Z28-CX011-JL-007-00";
          	typenum = "Z28-ZY011-JL-007-00";
          	//CCEnd SS1
          }
          else if(type.equals("试 制 工 艺 准 备 通 知 书"))
          {
          	//typenum = "Z28-CX011-JL-002-00";
          	typenum = "Z28-ZY011-JL-002-00";
          }
          //CCEnd by liunan 2011-08-25
          //CCBegin by liunan 2011-09-21 艺废通知书。
          else if(type.equals("工 艺 废 弃 通 知 书"))
          {
          	//typenum = "Z28-CX011-JL-006-00";
          	typenum = "Z28-ZY011-JL-006-00";
          }
          //CCEnd SS3
          //CCEnd by liunan 2011-09-21
          
    //说明
    Collection tail2 = (Collection) allInformationColl.get(3);
    if (tail2 != null && tail2.size() > 0) {
      Object[] des = tail2.toArray();
      genju = des[0].toString();
      shuoming = des[1].toString();
    }

    ////////////////////添加流程信息  ——刘明 20061213
    Vector wf = (Vector) allInformationColl.get(4);
    if (wf != null && wf.size() > 0) {
      //////////////////批准////////////////////////
      ArrayList pizhun = (ArrayList) wf.elementAt(0);
      StringBuffer pizhunString = new StringBuffer("批准：");
      if (pizhun != null && pizhun.size() != 0) {
        for (int i = 0; i < pizhun.size(); i++) {
          Object p1 = pizhun.get(i);
          if (p1 != null) {
            pizhunString.append(p1.toString() + " ");
          }
        }
      }
      pizhunInfo = pizhunString.toString();
      ////////////////////////////////////////////

      //////////////////审核///////////////////////////
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
      ////////////////////////////////////////////

      ///////////////校对 会签///////////////////
//  CCBegin by leixiao 2010-10-20 原因：解放艺准模板更改，原校对和会签写在一起，现分开
//      ArrayList jiaodui = (ArrayList) wf.elementAt(2);
//      StringBuffer jiaoduiBuffer = new StringBuffer("校对：");
//      if (jiaodui != null && jiaodui.size() != 0) {
//        for (int i = 0; i < jiaodui.size(); i++) {
//          Object p1 = jiaodui.get(i);
//          if (p1 != null) {
//            jiaoduiBuffer.append(p1.toString() + " ");
//          }
//        }
//      }
//      ArrayList huiqian = (ArrayList) wf.elementAt(3);
//      if (huiqian != null && huiqian.size() != 0) {
//        for (int i = 0; i < huiqian.size(); i++) {
//          Object p1 = huiqian.get(i);
//          if (p1 != null) {
//            jiaoduiBuffer.append(p1.toString() + " ");
//          }
//        }
//      }
      ArrayList jiaodui = (ArrayList) wf.elementAt(2);
      StringBuffer jiaoduiBuffer = new StringBuffer("会签：");            
      ArrayList huiqian = (ArrayList) wf.elementAt(3);
      if (huiqian != null && huiqian.size() != 0) {
        for (int i = 0; i < huiqian.size(); i++) {
          Object p1 = huiqian.get(i);
          if (p1 != null) {
            jiaoduiBuffer.append(p1.toString() + " ");
          }
        }
      }
      jiaoduiBuffer.append("        校对：");
      if (jiaodui != null && jiaodui.size() != 0) {
        for (int i = 0; i < jiaodui.size(); i++) {
          Object p1 = jiaodui.get(i);
          if (p1 != null) {
            jiaoduiBuffer.append(p1.toString() + " ");
          }
        }
      }            
      //  CCEndby leixiao 2010-10-20 
      jiaoduiInfo = jiaoduiBuffer.toString();
      ///////////////////////////
      bianzhiInfo = "编制：" + wf.elementAt(4).toString();
    }
    ////////////////////////////////////////////////

    String tail1 = (String) allInformationColl.get(2);
    faWang = "发往单位:" + tail1;
    

    Collection informationColl = (Collection) allInformationColl.get(1);
    for (Iterator iter = informationColl.iterator(); iter.hasNext(); )
    {
      Object[] arrayObjs = (Object[]) iter.next();
      String xuhao = (String) arrayObjs[0];
      String changeSign = (String) arrayObjs[1];
      String partNum = (String) arrayObjs[2];
      String version = (String) arrayObjs[3];
      partNum = partNum + version;
      String partName = (String) arrayObjs[4];
//    CCBeginby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
      String changiba = (String) arrayObjs[10];
//    CCEndby leixiao 2008-11-24 原因：解放升级工艺路线 
      String countInProduct = (String) arrayObjs[5];
      if(changeSign.equals("G"))
      {
        countInProduct = "";
      }
      String partMakeRoute = (String) arrayObjs[6];
      
      //CCBegin by liunan 2011-08-25 个人资料夹时没有发图数
      String futushu = "";
      if(arrayObjs.length>=12)
      {
      	futushu = (String) arrayObjs[11];
      }
      //CCEnd by liunan 2011-08-25
      
     // System.out.println("----发图数量="+futushu);
      String remark ="";
//    CCBeginby leixiao 2009-12-18 增加一列“是否互换”　arrayObjs.length==10变为11
      //CCBegin SS2
      String safety = "";
      //此前增加过 发图数，这次有增加了 保安重要。arrayObjs.length变成了13
      //if(arrayObjs.length==12)
      //CCBegin SS4 增加了 颜色件。arrayObjs.length变成了14
      String colorFlag = "";
      //if(arrayObjs.length==13)
      if(arrayObjs.length==14)
      //CCEnd SS4
      {
         remark = (String) arrayObjs[9];
         safety = (String) arrayObjs[12];
         colorFlag = (String) arrayObjs[13];
      }
      //CCEnd SS2
//    CCEndby leixiao 2008-12-18
      
      ArrayList list = (ArrayList) arrayObjs[7];

      for (int i = 0; i < list.size(); i++)
      {
        if (i == 0) {
          String[] informationStr = (String[]) list.get(0);
          if(changeSign.equals("G"))
          {
            informationStr[1] = "";
          }
    	  if(type.equals("工 艺 准 备 完 毕 通 知 书")){//注意中间带空格
              String[] array1 = {
                      xuhao, changeSign,"W", partNum, partName, countInProduct,
                      partMakeRoute, informationStr[0],futushu,remark};

              result.addElement(array1);
              array1=null;  
    	  }
    	  //CCBegin by liunan 2011-09-21 艺废通知书。
    	  else if(type.equals("工 艺 废 弃 通 知 书")){//注意中间带空格
              String sycx = "";
              sycx = (String) arrayObjs[10];
              String[] array1 = {
                      xuhao, changeSign, partNum, partName, countInProduct,
                      partMakeRoute, informationStr[0],sycx,remark};

              result.addElement(array1);
              array1=null;  
    	  }
    	  //CCEnd by liunan 2011-09-21
    	  else{
//        CCBeginby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
              String[] array1 = {
                      xuhao, changeSign, partNum, partName, changiba, countInProduct,
                      partMakeRoute, informationStr[0], informationStr[1],
                      //CCBegin SS2
                      //informationStr[2],futushu,remark};
                      //CCBegin SS4
                      //informationStr[2],safety,futushu,remark};
                      informationStr[2],safety,futushu,colorFlag,remark};
                      //CCEnd SS4
                      //CCEnd SS2
//        CCEndby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
          result.addElement(array1);
          array1=null;
    	  }
        }
        else {
          String[] informationStr = (String[]) list.get(i);
          if(changeSign.equals("G"))
          {
            informationStr[1] = "";
          }
    	  if(type.equals("工 艺 准 备 完 毕 通 知 书")){
              String[] array2 = {
                  "", "", "", "", "",
                  "","", informationStr[0],"",""};

              result.addElement(array2);
              array2=null;
    	  }
    	  //CCBegin by liunan 2011-09-21 艺废通知书。
    	  else if(type.equals("工 艺 废 弃 通 知 书")){
              String[] array2 = {
                  "", "", "", "", "",
                  "","", informationStr[0],"",""};

              result.addElement(array2);
              array2=null;
    	  }
    	  //CCEnd by liunan 2011-09-21
    	  else{
//        CCBeginby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
          String[] array2 = {
              "", "", "", "", "",
              //CCBegin SS2
              //"","", informationStr[0], informationStr[1], informationStr[2],"",""};
              //CCBegin SS4
              //"","", informationStr[0], informationStr[1], informationStr[2],"","",""};
              "","", informationStr[0], informationStr[1], informationStr[2],"","","",""};
              //CCEnd SS4
              //CCEnd SS2
//        CCEndby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
          result.addElement(array2);
          array2=null;
    	  }
        }
      }

    }
    //CCBegin by liunan 2012-01-30 将result挪到方法里
    return result;
    //CCEnd by liunan 2012-01-30
  }

  /**
   * 输出报表
   *
   * @param path
   * @roseuid 42C363440215
   */
  //CCBegin by liunan 2012-01-30 将result挪到方法里
  //public boolean printFile(String path){
  public boolean printFile(String path, Vector result){
  //CCEnd by liunan 2012-01-3
	  
	  if(type.equals("工 艺 准 备 完 毕 通 知 书")){
	  	//CCBegin by liunan 2012-01-30 将result挪到方法里
		  //return  princompletetFile(path);
	  	return  princompletetFile(path, result);
	  	//CCEnd by liunan 2012-01-30
	  }
	  //CCBegin by liunan 2011-09-21 艺废通知书。
	  else if(type.equals("工 艺 废 弃 通 知 书")){
	  	//CCBegin by liunan 2012-01-30 将result挪到方法里
		  //return  printdisaffirmFile(path);
	  	return  printdisaffirmFile(path, result);
	  	//CCEnd by liunan 2012-01-30
	  }
	  //CCEnd by liunan 2011-09-21
	  else{
	  	//CCBegin by liunan 2012-01-30 将result挪到方法里
		  //return  printcommonFile(path);
	  	return  printcommonFile(path, result);
	  	//CCEnd by liunan 2012-01-30
	  }
  }
  //CCBegin by liunan 2012-01-30 将result挪到方法里
  //public boolean printcommonFile(String path) {
  public boolean printcommonFile(String path, Vector result) {
  //CCEnd by liunan 2012-01-30

            //CCBegin by liunan 2011-08-25 报表添加图标
            /*String url = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/") +
               "routeListTemplate.xls";*/
            
            String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
            String url = urlroot + "routeListTemplate.xls";
            //CCEnd by liunan 2011-08-25

 //   System.out.println("URL = "+url);
    InputStream stream = null;
    try {
      URL aurl = new URL(url);
      stream = aurl.openStream();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }

    POIFSFileSystem fs = null;
    HSSFWorkbook wb = null;
    try {
      fs = new POIFSFileSystem(stream);
      wb = new HSSFWorkbook(fs);
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
//  CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出
    HSSFRow row = sheet.getRow(0);
                  
           
           //CCBegin by liunan 2011-08-23 表格模板修改，添加图标，内容位置也随之调整。
           //HSSFCell cell = row.getCell( (short) 0); //第1行第1列 标题
           HSSFCell cell = row.getCell( (short) 3); //第1行D列 标题
           
           //CCEnd by liunan 2011-08-23           
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue(type);
       //  CCEndby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出 
           
           
    //CCBegin by liunan 2011-08-23 表格模板修改，添加图标，内容位置也随之调整。
    //CCBegin SS2
    //cell = row.getCell( (short) 11);
    //CCBegin SS4
    //cell = row.getCell( (short) 12);
    cell = row.getCell( (short) 13);
    //CCEnd SS4
    //CCEnd SS2
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(typenum); //第1行L列  类型标号
    //CCEnd by liunan 2011-08-23
    
    row = sheet.getRow(2);
    cell = row.getCell( (short) 6); //第2行第5列 编制日期
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(makeDate);

    row = sheet.getRow(3);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(projectName); //第3行0列 项目名称

    row = sheet.getRow(23);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(genju); //第24行0列 根据

    row = sheet.getRow(24);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(shuoming); //第25行0列 说明
//  CCBeginby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”导致调整
    row = sheet.getRow(25);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(pizhunInfo); //第26行0列 批准
    cell = row.getCell( (short) 3);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(shenheInfo); //第26行3列 审核
    cell = row.getCell( (short) 4);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(jiaoduiInfo); //第26行4列 校对
    cell = row.getCell( (short) 7);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(bianzhiInfo); //第26行7列 编制
    //CCBegin SS2
    //cell = row.getCell( (short) 10);
    cell = row.getCell( (short) 11);
    //CCEnd SS2
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(routelistNumber); //第26行10列 艺准号
//  CCEndby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
    
    row = sheet.getRow(26);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(faWang); //第27行0列 发往单位



    //处理汇总结果
    if (result != null && result.size() > 0) {
      int pageNumber = 0;
      int psize = result.size();
      if (psize % this.getRowNumber() == 0) {
        pageNumber = psize / this.getRowNumber();
      }
      else {
        pageNumber = 1 + (psize / this.getRowNumber());
      }
      HSSFSheet sheet1 = null;
      if(pageNumber>1)
      {
          sheet1 = wb.cloneSheet(0); //缓存空页面
      }
      int page = 0;
      for (int i = 0; i < result.size(); i++) {
        Object[] o = (Object[]) result.elementAt(i);
        if (i % this.getRowNumber() == 0) { //余数为0,即整除
          page++;
          if (page == 1) {            
            row = sheet.getRow(26);
//          CCBeginby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”导致第几页后移一位
            cell = row.getCell( (short) 9);
//          CCEndby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”
            //cell.setEncoding(cell.ENCODING_UTF_16);
            cell.setCellValue("第1页  " + "共" + pageNumber + "页");
          }
          else if (page > 1) {
            sheet = sheet1;
            wb.setSheetName(page - 1, "Sheet" + page);
            if (page < pageNumber) {
              sheet1 = wb.cloneSheet(page - 1);
            }
            
            row = sheet.getRow(26);
//          CCBeginby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”导致第几页后移一位
            cell = row.getCell( (short) 9);
//          CCEndby leixiao 2008-11-24 原因：解放升级工艺路线,增加一列“是否互换”导致第几页后移一位
            //cell.setEncoding(cell.ENCODING_UTF_16);
            cell.setCellValue("第" + page + "页  共" + pageNumber + "页");
          }

        }
        if (verbose) {
          System.out.println("i=" + i + "    page=" + page);
        }
        
        //CCBegin by liunan 2011-08-25 添加图标
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"12.jpg"));
           ImageIO.write(bufferImg,"jpg",byteArrayOut);
          }
           catch (IOException e) {
             e.printStackTrace();

           }
          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
    /* 参数　　说明
dx1 	第1个单元格中x轴的偏移量
dy1 	第1个单元格中y轴的偏移量
dx2 	第2个单元格中x轴的偏移量
dy2 	第2个单元格中y轴的偏移量
col1 	第1个单元格的列号
row1 	第1个单元格的行号
col2 	第2个单元格的列号
row2 	第2个单元格的行号*/
           HSSFClientAnchor anchor = new HSSFClientAnchor(50,30,380,220,(short) 0,0,(short)2,0); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG)); 
           //CCEnd by liunan 2011-08-25
           
        int rowIndex = 5 + i % this.getRowNumber();
        row = sheet.getRow(rowIndex);
        if (verbose) {
          System.out.println("row=" + rowIndex);

        }
        for (int j = 0; j < this.getColNumber(); j++) {
          cell = row.getCell( (short) j);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          if (o[j] == null) {
            cell.setCellValue("");
          }
          else {
            cell.setCellValue(o[j].toString());
          }
        }
      }
    }

    try {
      FileOutputStream out = new FileOutputStream(path);
      wb.write(out);
//  	CCBegin by leixiao 2010-1-5 关闭流
      out.close();
//  	CCEnd by leixiao
      return true;
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
      return false;
    }

  }

  
  //CCBegin by liunan 2012-01-30 将result挪到方法里
  //public boolean princompletetFile(String path) {
  public boolean princompletetFile(String path, Vector result) {
  //CCEnd by liunan 2012-01-30
//		CCBegin by leixiao 2009-3-27 原因：艺毕输出报表    	  
          //CCBegin by liunan 2011-08-25 报表添加图标
            /*String url = "http://" +
          RemoteProperty.getProperty("server.hostName", "") +
          RemoteProperty.getProperty("routeListTemplate",
                                     "/PhosphorPDM/template/") +
          "routeListcompeletTemplate.xls";*/
            
            String urlroot = "http://" +
                 RemoteProperty.getProperty("server.hostName", "") +
                 RemoteProperty.getProperty("routeListTemplate",
                                     "/PhosphorPDM/template/");
            String url = urlroot + "routeListcompeletTemplate.xls";
            //CCEnd by liunan 2011-08-25

//   System.out.println("URL = "+url);
  InputStream stream = null;
  try {
    URL aurl = new URL(url);
    stream = aurl.openStream();
  }
  catch (Exception ex) {
    ex.printStackTrace();
    return false;
  }

  POIFSFileSystem fs = null;
  HSSFWorkbook wb = null;
  try {
    fs = new POIFSFileSystem(stream);
    wb = new HSSFWorkbook(fs);
  }
  catch (IOException e) {
    e.printStackTrace();
    return false;
  }

  HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
//CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出
  HSSFRow row = sheet.getRow(0);
        
           
      //CCBegin by liunan 2011-08-23 表格模板修改，添加图标，内容位置也随之调整。
           //HSSFCell cell = row.getCell( (short) 0); //第1行第1列 标题
           HSSFCell cell = row.getCell( (short) 3); //第1行D列 标题           
           //CCEnd by liunan 2011-08-23 
      //cell.setEncoding(cell.ENCODING_UTF_16);
      cell.setCellValue(type);
    //CCEndby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出 
    
    //CCBegin by liunan 2011-08-23 表格模板修改，添加图标，内容位置也随之调整。
    cell = row.getCell( (short) 9);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(typenum); //第1行J列  类型标号
    //CCEnd by liunan 2011-08-23
  
  row = sheet.getRow(2);
  cell = row.getCell( (short) 6); //第2行第7列 编制日期
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(makeDate);

  row = sheet.getRow(3);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(projectName); //第3行0列 项目名称

  row = sheet.getRow(23);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(genju); //第24行0列 根据

  row = sheet.getRow(24);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shuoming); //第25行0列 说明
  
  row = sheet.getRow(25);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(pizhunInfo); //第26行0列 批准
  
  cell = row.getCell( (short) 4);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shenheInfo); //第26行3列 审核
  cell = row.getCell( (short) 5);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(jiaoduiInfo); //第26行4列 校对
  cell = row.getCell( (short) 7);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(bianzhiInfo); //第26行7列 编制
  cell = row.getCell( (short) 8);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(routelistNumber); //第26行9列 艺准号
  row = sheet.getRow(26);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(faWang); //第27行0列 发往单位



  //处理汇总结果
  if (result != null && result.size() > 0) {
    int pageNumber = 0;
    int psize = result.size();
    if (psize % this.getRowNumber() == 0) {
      pageNumber = psize / this.getRowNumber();
    }
    else {
      pageNumber = 1 + (psize / this.getRowNumber());
    }
    HSSFSheet sheet1 = null;
    if(pageNumber>1)
    {
        sheet1 = wb.cloneSheet(0); //缓存空页面
    }
    int page = 0;
    for (int i = 0; i < result.size(); i++) {
      Object[] o = (Object[]) result.elementAt(i);
      if (i % this.getRowNumber() == 0) { //余数为0,即整除
        page++;
        if (page == 1) {
          row = sheet.getRow(26);

          cell = row.getCell( (short) 8);

          //cell.setEncoding(cell.ENCODING_UTF_16);
          cell.setCellValue("第1页  " + "共" + pageNumber + "页");
        }
        else if (page > 1) {
          sheet = sheet1;
          wb.setSheetName(page - 1, "Sheet" + page);
          if (page < pageNumber) {
            sheet1 = wb.cloneSheet(page - 1);
          }
          row = sheet.getRow(26);

          cell = row.getCell( (short) 8);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          cell.setCellValue("第" + page + "页  共" + pageNumber + "页");
        }

      }
      if (verbose) {
        System.out.println("i=" + i + "    page=" + page);
      }

//CCBegin by liunan 2011-08-25 添加图标
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"12.jpg"));
           ImageIO.write(bufferImg,"jpg",byteArrayOut);
          }
           catch (IOException e) {
             e.printStackTrace();

           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
    /* 参数　　说明
dx1 	第1个单元格中x轴的偏移量
dy1 	第1个单元格中y轴的偏移量
dx2 	第2个单元格中x轴的偏移量
dy2 	第2个单元格中y轴的偏移量
col1 	第1个单元格的列号
row1 	第1个单元格的行号
col2 	第2个单元格的列号
row2 	第2个单元格的行号*/
           HSSFClientAnchor anchor = new HSSFClientAnchor(50,30,380,220,(short) 0,0,(short)2,0); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG)); 
           //CCEnd by liunan 2011-08-25
           
      int rowIndex = 5 + i % this.getRowNumber();
      row = sheet.getRow(rowIndex);
      if (verbose) {
        System.out.println("row=" + rowIndex);

      }

      for (int j = 0; j < this.getcompleteColNumber(); j++) {
        cell = row.getCell( (short) j);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if (o[j] == null) {
          cell.setCellValue("");
        }
        else {
          cell.setCellValue(o[j].toString());
        }
      }
    }
  }

  try {
    FileOutputStream out = new FileOutputStream(path);
    wb.write(out);
//	CCBegin by leixiao 2010-1-5 关闭流
    out.close();
//	CCEnd by leixiao
    return true;
  }
  catch (Exception ex1) {
    ex1.printStackTrace();
    return false;
  }

}
//	CCBegin by leixiao 2009-12-17 原因：艺准输出报表 的总列数10－>11　TD267导出艺准时备注未显示
  /**
   * 艺准列表的列数
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getColNumber() {
    //CCBegin SS2
    //return 12;
    return 13;
    //CCEnd SS2
  }
//	CCEnd by leixiao 2009-12-17 
  /**
   * 艺毕列表的列数
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getcompleteColNumber() {
	    return 9;
	  }
  


  /**
   * 每页的零件行数
   *
   * @return int
   * @roseuid 42C3634401E3
   */
  private int getRowNumber() {
    return 18;
  }

//CCBegin by liunan 2011-09-21 艺废通知书。
  //CCBegin by liunan 2012-01-30 将result挪到方法里
  //public boolean printdisaffirmFile(String path) {
  public boolean printdisaffirmFile(String path, Vector result) {
  //CCEnd by liunan 2012-01-30
            String urlroot = "http://" +
                 RemoteProperty.getProperty("server.hostName", "") +
                 RemoteProperty.getProperty("routeListTemplate",
                                     "/PhosphorPDM/template/");
            String url = urlroot + "routeListdisaffirmTemplate.xls";
            //CCEnd by liunan 2011-08-25

//   System.out.println("URL = "+url);
  InputStream stream = null;
  try {
    URL aurl = new URL(url);
    stream = aurl.openStream();
  }
  catch (Exception ex) {
    ex.printStackTrace();
    return false;
  }

  POIFSFileSystem fs = null;
  HSSFWorkbook wb = null;
  try {
    fs = new POIFSFileSystem(stream);
    wb = new HSSFWorkbook(fs);
  }
  catch (IOException e) {
    e.printStackTrace();
    return false;
  }

  HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
//CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出
  HSSFRow row = sheet.getRow(0);
        
           
      //CCBegin by liunan 2011-08-23 表格模板修改，添加图标，内容位置也随之调整。
           //HSSFCell cell = row.getCell( (short) 0); //第1行第1列 标题
           HSSFCell cell = row.getCell( (short) 3); //第1行D列 标题           
           //CCEnd by liunan 2011-08-23 
      //cell.setEncoding(cell.ENCODING_UTF_16);
      cell.setCellValue(type);
    //CCEndby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出 
    
    //CCBegin by liunan 2011-08-23 表格模板修改，添加图标，内容位置也随之调整。
    cell = row.getCell( (short) 8);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(typenum); //第1行I列  类型标号
    //CCEnd by liunan 2011-08-23
  
  row = sheet.getRow(2);
  cell = row.getCell( (short) 5); //第2行第6列 编制日期
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(makeDate);

  row = sheet.getRow(3);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(projectName); //第3行0列 项目名称

  row = sheet.getRow(21);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(genju); //第24行0列 根据

  row = sheet.getRow(22);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shuoming); //第25行0列 说明
  
  row = sheet.getRow(23);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(pizhunInfo); //第26行0列 批准
  
  cell = row.getCell( (short) 3);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shenheInfo); //第26行3列 审核
  cell = row.getCell( (short) 4);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(jiaoduiInfo); //第26行4列 校对
  cell = row.getCell( (short) 6);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(bianzhiInfo); //第26行7列 编制
  cell = row.getCell( (short) 8);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(routelistNumber); //第26行9列 艺准号
  row = sheet.getRow(24);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(faWang); //第27行0列 发往单位



  //处理汇总结果
  if (result != null && result.size() > 0) {
    int pageNumber = 0;
    int psize = result.size();
    if (psize % this.getDisaffirmRowNumber() == 0) {
      pageNumber = psize / this.getDisaffirmRowNumber();
    }
    else {
      pageNumber = 1 + (psize / this.getDisaffirmRowNumber());
    }
    HSSFSheet sheet1 = null;
    if(pageNumber>1)
    {
        sheet1 = wb.cloneSheet(0); //缓存空页面
    }
    int page = 0;
    for (int i = 0; i < result.size(); i++) {
      Object[] o = (Object[]) result.elementAt(i);
      if (i % this.getDisaffirmRowNumber() == 0) { //余数为0,即整除
        page++;
        if (page == 1) {
          row = sheet.getRow(24);

          cell = row.getCell( (short) 8);

          //cell.setEncoding(cell.ENCODING_UTF_16);
          cell.setCellValue("第1页  " + "共" + pageNumber + "页");
        }
        else if (page > 1) {
          sheet = sheet1;
          wb.setSheetName(page - 1, "Sheet" + page);
          if (page < pageNumber) {
            sheet1 = wb.cloneSheet(page - 1);
          }
          row = sheet.getRow(24);

          cell = row.getCell( (short) 8);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          cell.setCellValue("第" + page + "页  共" + pageNumber + "页");
        }

      }
      if (verbose) {
        System.out.println("i=" + i + "    page=" + page);
      }

//CCBegin by liunan 2011-08-25 添加图标
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"12.jpg"));
           ImageIO.write(bufferImg,"jpg",byteArrayOut);
          }
           catch (IOException e) {
             e.printStackTrace();

           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
    /* 参数　　说明
dx1 	第1个单元格中x轴的偏移量
dy1 	第1个单元格中y轴的偏移量
dx2 	第2个单元格中x轴的偏移量
dy2 	第2个单元格中y轴的偏移量
col1 	第1个单元格的列号
row1 	第1个单元格的行号
col2 	第2个单元格的列号
row2 	第2个单元格的行号*/
           HSSFClientAnchor anchor = new HSSFClientAnchor(50,30,380,220,(short) 0,0,(short)2,0); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG)); 
           //CCEnd by liunan 2011-08-25
           
      int rowIndex = 5 + i % this.getDisaffirmRowNumber();
      row = sheet.getRow(rowIndex);
      if (verbose) {
        System.out.println("row=" + rowIndex);

      }

      for (int j = 0; j < this.getdisaffirmColNumber(); j++) {
        cell = row.getCell( (short) j);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if (o[j] == null) {
          cell.setCellValue("");
        }
        else {
          cell.setCellValue(o[j].toString());
        }
      }
    }
  }

  try {
    FileOutputStream out = new FileOutputStream(path);
    wb.write(out);
    out.close();
    return true;
  }
  catch (Exception ex1) {
    ex1.printStackTrace();
    return false;
  }

}

//	CCEnd by leixiao 2009-12-17 
  /**
   * 艺毕列表的列数
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getdisaffirmColNumber() {
	    return 9;
	  }
  


  /**
   * 每页的零件行数
   *
   * @return int
   * @roseuid 42C3634401E3
   */
  private int getDisaffirmRowNumber() {
    return 16;
  }
//CCEnd by liunan 2011-09-21
}
