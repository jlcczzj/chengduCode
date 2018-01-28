/**
 *
 * SS1 ǰ׼�޸����ͱ�š� liunan 2013-1-24
 * SS2 ���ݽ��Ҫ����׼��ǰ׼����׼������׼�ı�����Ҫ���ӡ�������Ҫ�����Ե���ʾ�� liunan 2013-6-17
 * SS3 A004-2015-3278 ������׼ģ���¼��š�������CX011��ΪZY011 liunan 2016-3-8
 * SS4 A004-2016-3310 ��׼������ɫ���С� liunan 2016-3-9
 * SS5 ����poi��3.6��ע��//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
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
 *   //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
 *
 * @author ����
 * @version 1.0
 */
public class ExportExcel {

  private String makeDate = "";
  private String projectName = "";
  private String routelistNumber = "";
  //CCBegin by liunan 2012-01-30 ��resultŲ��������������ֽ�����ҵ����
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
//CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�
  private String type = "";
//CCEndby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�

    //CCBegin by liunan 2011-08-25 ��ͷ���ͱ�Ÿ������ͳ�
    private String typenum = "";
    //CCEnd by liunan 2011-08-25
    
  public ExportExcel() {
  }

  /**
   * �ռ���������
   * @param routeListID String
   * @param IsExpandByProduct �Ƿ񰴳�չ������ʱĬ����Ϊ�棩
   * @throws QMException
   */
  //CCBegin by liunan 2012-01-30 ��resultŲ��������
  //public void gatherExportData(String routeListID, String IsExpandByProduct) throws
  public Vector gatherExportData(String routeListID, String IsExpandByProduct) throws 
  //CCEnd by liunan 2012-01-30
      QMException {
    //CCBegin by liunan 2012-01-30 ��resultŲ��������
    Vector result = new Vector();
    //CCEnd by liunan 2012-01-30
    Class[] c = {
        String.class, String.class};
    Object[] oo = {
        routeListID, IsExpandByProduct};
//  CCBeginby leixiao 2009-2-13 ԭ�򣺽����������·��,����ͳһ�ӱ�������    
//    ArrayList allInformationColl = (ArrayList) CappRouteAction.useServiceMethod(
//        "TechnicsRouteService", "gatherExportData", c, oo);
    ArrayList allInformationColl = (ArrayList) CappRouteAction.useServiceMethod(
            "JFService", "gatherExportData", c, oo);
//  CCBeginby leixiao 2009-2-13 ԭ�򣺽����������·��,����ͳһ�ӱ�������     
    //CCBegin by leixiao 2010-1-5 ����б�Ϊ��ʱ��������Խ��
    if(allInformationColl==null||allInformationColl.size()<5)
	//CCBegin by liunan 2012-01-30 ��resultŲ��������
	//return;
	return null;
	//CCEnd by liunan 2012-01-30
//CCEnd by leixiao 2010-1-5 
    String[] header = (String[]) allInformationColl.get(0);
    projectName = "��Ŀ����:" + header[3]+" "+header[0];
    routelistNumber = header[1];
    makeDate = "��������:" + header[2];
//  CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�
    type=header[4];
//  CCEndby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�

          //CCBegin by liunan 2011-08-25 ��ͷ���ͱ�Ÿ������ͳ�
          //CCBegin SS3
          if(type.equals("�� �� ׼ �� �� �� ͨ ֪ ��"))
          {
          	//typenum = "Z28-CX011-JL-005-00";
          	typenum = "Z28-ZY011-JL-005-00";
          }
          else if(type.equals("�� �� ׼ �� ͨ ֪ ��"))
          {
          	//typenum = "Z28-CX011-JL-001-00";
          	typenum = "Z28-ZY011-JL-001-00";
          }
          else if(type.equals("�� ʱ �� �� ׼ �� ͨ ֪ ��"))
          {
          	//typenum = "Z28-CX011-JL-003-00";
          	typenum = "Z28-ZY011-JL-003-00";
          }
          else if(type.equals("�� ǰ �� �� ׼ �� ͨ ֪ ��"))
          {
          	//CCBegin SS1
          	//typenum = "Z28-CX011-JL-004-00";
          	//typenum = "Z28-CX011-JL-007-00";
          	typenum = "Z28-ZY011-JL-007-00";
          	//CCEnd SS1
          }
          else if(type.equals("�� �� �� �� ׼ �� ͨ ֪ ��"))
          {
          	//typenum = "Z28-CX011-JL-002-00";
          	typenum = "Z28-ZY011-JL-002-00";
          }
          //CCEnd by liunan 2011-08-25
          //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
          else if(type.equals("�� �� �� �� ͨ ֪ ��"))
          {
          	//typenum = "Z28-CX011-JL-006-00";
          	typenum = "Z28-ZY011-JL-006-00";
          }
          //CCEnd SS3
          //CCEnd by liunan 2011-09-21
          
    //˵��
    Collection tail2 = (Collection) allInformationColl.get(3);
    if (tail2 != null && tail2.size() > 0) {
      Object[] des = tail2.toArray();
      genju = des[0].toString();
      shuoming = des[1].toString();
    }

    ////////////////////���������Ϣ  �������� 20061213
    Vector wf = (Vector) allInformationColl.get(4);
    if (wf != null && wf.size() > 0) {
      //////////////////��׼////////////////////////
      ArrayList pizhun = (ArrayList) wf.elementAt(0);
      StringBuffer pizhunString = new StringBuffer("��׼��");
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

      //////////////////���///////////////////////////
      ArrayList shenhe = (ArrayList) wf.elementAt(1);
      StringBuffer shenheBuffer = new StringBuffer("��ˣ�");
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

      ///////////////У�� ��ǩ///////////////////
//  CCBegin by leixiao 2010-10-20 ԭ�򣺽����׼ģ����ģ�ԭУ�Ժͻ�ǩд��һ���ַֿ�
//      ArrayList jiaodui = (ArrayList) wf.elementAt(2);
//      StringBuffer jiaoduiBuffer = new StringBuffer("У�ԣ�");
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
      StringBuffer jiaoduiBuffer = new StringBuffer("��ǩ��");            
      ArrayList huiqian = (ArrayList) wf.elementAt(3);
      if (huiqian != null && huiqian.size() != 0) {
        for (int i = 0; i < huiqian.size(); i++) {
          Object p1 = huiqian.get(i);
          if (p1 != null) {
            jiaoduiBuffer.append(p1.toString() + " ");
          }
        }
      }
      jiaoduiBuffer.append("        У�ԣ�");
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
      bianzhiInfo = "���ƣ�" + wf.elementAt(4).toString();
    }
    ////////////////////////////////////////////////

    String tail1 = (String) allInformationColl.get(2);
    faWang = "������λ:" + tail1;
    

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
//    CCBeginby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
      String changiba = (String) arrayObjs[10];
//    CCEndby leixiao 2008-11-24 ԭ�򣺽����������·�� 
      String countInProduct = (String) arrayObjs[5];
      if(changeSign.equals("G"))
      {
        countInProduct = "";
      }
      String partMakeRoute = (String) arrayObjs[6];
      
      //CCBegin by liunan 2011-08-25 �������ϼ�ʱû�з�ͼ��
      String futushu = "";
      if(arrayObjs.length>=12)
      {
      	futushu = (String) arrayObjs[11];
      }
      //CCEnd by liunan 2011-08-25
      
     // System.out.println("----��ͼ����="+futushu);
      String remark ="";
//    CCBeginby leixiao 2009-12-18 ����һ�С��Ƿ񻥻�����arrayObjs.length==10��Ϊ11
      //CCBegin SS2
      String safety = "";
      //��ǰ���ӹ� ��ͼ��������������� ������Ҫ��arrayObjs.length�����13
      //if(arrayObjs.length==12)
      //CCBegin SS4 ������ ��ɫ����arrayObjs.length�����14
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
    	  if(type.equals("�� �� ׼ �� �� �� ͨ ֪ ��")){//ע���м���ո�
              String[] array1 = {
                      xuhao, changeSign,"W", partNum, partName, countInProduct,
                      partMakeRoute, informationStr[0],futushu,remark};

              result.addElement(array1);
              array1=null;  
    	  }
    	  //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
    	  else if(type.equals("�� �� �� �� ͨ ֪ ��")){//ע���м���ո�
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
//        CCBeginby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
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
//        CCEndby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
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
    	  if(type.equals("�� �� ׼ �� �� �� ͨ ֪ ��")){
              String[] array2 = {
                  "", "", "", "", "",
                  "","", informationStr[0],"",""};

              result.addElement(array2);
              array2=null;
    	  }
    	  //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
    	  else if(type.equals("�� �� �� �� ͨ ֪ ��")){
              String[] array2 = {
                  "", "", "", "", "",
                  "","", informationStr[0],"",""};

              result.addElement(array2);
              array2=null;
    	  }
    	  //CCEnd by liunan 2011-09-21
    	  else{
//        CCBeginby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
          String[] array2 = {
              "", "", "", "", "",
              //CCBegin SS2
              //"","", informationStr[0], informationStr[1], informationStr[2],"",""};
              //CCBegin SS4
              //"","", informationStr[0], informationStr[1], informationStr[2],"","",""};
              "","", informationStr[0], informationStr[1], informationStr[2],"","","",""};
              //CCEnd SS4
              //CCEnd SS2
//        CCEndby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
          result.addElement(array2);
          array2=null;
    	  }
        }
      }

    }
    //CCBegin by liunan 2012-01-30 ��resultŲ��������
    return result;
    //CCEnd by liunan 2012-01-30
  }

  /**
   * �������
   *
   * @param path
   * @roseuid 42C363440215
   */
  //CCBegin by liunan 2012-01-30 ��resultŲ��������
  //public boolean printFile(String path){
  public boolean printFile(String path, Vector result){
  //CCEnd by liunan 2012-01-3
	  
	  if(type.equals("�� �� ׼ �� �� �� ͨ ֪ ��")){
	  	//CCBegin by liunan 2012-01-30 ��resultŲ��������
		  //return  princompletetFile(path);
	  	return  princompletetFile(path, result);
	  	//CCEnd by liunan 2012-01-30
	  }
	  //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
	  else if(type.equals("�� �� �� �� ͨ ֪ ��")){
	  	//CCBegin by liunan 2012-01-30 ��resultŲ��������
		  //return  printdisaffirmFile(path);
	  	return  printdisaffirmFile(path, result);
	  	//CCEnd by liunan 2012-01-30
	  }
	  //CCEnd by liunan 2011-09-21
	  else{
	  	//CCBegin by liunan 2012-01-30 ��resultŲ��������
		  //return  printcommonFile(path);
	  	return  printcommonFile(path, result);
	  	//CCEnd by liunan 2012-01-30
	  }
  }
  //CCBegin by liunan 2012-01-30 ��resultŲ��������
  //public boolean printcommonFile(String path) {
  public boolean printcommonFile(String path, Vector result) {
  //CCEnd by liunan 2012-01-30

            //CCBegin by liunan 2011-08-25 �������ͼ��
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

    HSSFSheet sheet = wb.getSheetAt(0); //��õ�һ��ҳ��
//  CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�
    HSSFRow row = sheet.getRow(0);
                  
           
           //CCBegin by liunan 2011-08-23 ���ģ���޸ģ����ͼ�꣬����λ��Ҳ��֮������
           //HSSFCell cell = row.getCell( (short) 0); //��1�е�1�� ����
           HSSFCell cell = row.getCell( (short) 3); //��1��D�� ����
           
           //CCEnd by liunan 2011-08-23           
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue(type);
       //  CCEndby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ� 
           
           
    //CCBegin by liunan 2011-08-23 ���ģ���޸ģ����ͼ�꣬����λ��Ҳ��֮������
    //CCBegin SS2
    //cell = row.getCell( (short) 11);
    //CCBegin SS4
    //cell = row.getCell( (short) 12);
    cell = row.getCell( (short) 13);
    //CCEnd SS4
    //CCEnd SS2
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(typenum); //��1��L��  ���ͱ��
    //CCEnd by liunan 2011-08-23
    
    row = sheet.getRow(2);
    cell = row.getCell( (short) 6); //��2�е�5�� ��������
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(makeDate);

    row = sheet.getRow(3);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(projectName); //��3��0�� ��Ŀ����

    row = sheet.getRow(23);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(genju); //��24��0�� ����

    row = sheet.getRow(24);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(shuoming); //��25��0�� ˵��
//  CCBeginby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻������µ���
    row = sheet.getRow(25);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(pizhunInfo); //��26��0�� ��׼
    cell = row.getCell( (short) 3);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(shenheInfo); //��26��3�� ���
    cell = row.getCell( (short) 4);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(jiaoduiInfo); //��26��4�� У��
    cell = row.getCell( (short) 7);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(bianzhiInfo); //��26��7�� ����
    //CCBegin SS2
    //cell = row.getCell( (short) 10);
    cell = row.getCell( (short) 11);
    //CCEnd SS2
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(routelistNumber); //��26��10�� ��׼��
//  CCEndby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
    
    row = sheet.getRow(26);
    cell = row.getCell( (short) 0);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(faWang); //��27��0�� ������λ



    //������ܽ��
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
          sheet1 = wb.cloneSheet(0); //�����ҳ��
      }
      int page = 0;
      for (int i = 0; i < result.size(); i++) {
        Object[] o = (Object[]) result.elementAt(i);
        if (i % this.getRowNumber() == 0) { //����Ϊ0,������
          page++;
          if (page == 1) {            
            row = sheet.getRow(26);
//          CCBeginby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻������µڼ�ҳ����һλ
            cell = row.getCell( (short) 9);
//          CCEndby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻���
            //cell.setEncoding(cell.ENCODING_UTF_16);
            cell.setCellValue("��1ҳ  " + "��" + pageNumber + "ҳ");
          }
          else if (page > 1) {
            sheet = sheet1;
            wb.setSheetName(page - 1, "Sheet" + page);
            if (page < pageNumber) {
              sheet1 = wb.cloneSheet(page - 1);
            }
            
            row = sheet.getRow(26);
//          CCBeginby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻������µڼ�ҳ����һλ
            cell = row.getCell( (short) 9);
//          CCEndby leixiao 2008-11-24 ԭ�򣺽����������·��,����һ�С��Ƿ񻥻������µڼ�ҳ����һλ
            //cell.setEncoding(cell.ENCODING_UTF_16);
            cell.setCellValue("��" + page + "ҳ  ��" + pageNumber + "ҳ");
          }

        }
        if (verbose) {
          System.out.println("i=" + i + "    page=" + page);
        }
        
        //CCBegin by liunan 2011-08-25 ���ͼ��
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
    /* ��������˵��
dx1 	��1����Ԫ����x���ƫ����
dy1 	��1����Ԫ����y���ƫ����
dx2 	��2����Ԫ����x���ƫ����
dy2 	��2����Ԫ����y���ƫ����
col1 	��1����Ԫ����к�
row1 	��1����Ԫ����к�
col2 	��2����Ԫ����к�
row2 	��2����Ԫ����к�*/
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
//  	CCBegin by leixiao 2010-1-5 �ر���
      out.close();
//  	CCEnd by leixiao
      return true;
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
      return false;
    }

  }

  
  //CCBegin by liunan 2012-01-30 ��resultŲ��������
  //public boolean princompletetFile(String path) {
  public boolean princompletetFile(String path, Vector result) {
  //CCEnd by liunan 2012-01-30
//		CCBegin by leixiao 2009-3-27 ԭ���ձ��������    	  
          //CCBegin by liunan 2011-08-25 �������ͼ��
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

  HSSFSheet sheet = wb.getSheetAt(0); //��õ�һ��ҳ��
//CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�
  HSSFRow row = sheet.getRow(0);
        
           
      //CCBegin by liunan 2011-08-23 ���ģ���޸ģ����ͼ�꣬����λ��Ҳ��֮������
           //HSSFCell cell = row.getCell( (short) 0); //��1�е�1�� ����
           HSSFCell cell = row.getCell( (short) 3); //��1��D�� ����           
           //CCEnd by liunan 2011-08-23 
      //cell.setEncoding(cell.ENCODING_UTF_16);
      cell.setCellValue(type);
    //CCEndby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ� 
    
    //CCBegin by liunan 2011-08-23 ���ģ���޸ģ����ͼ�꣬����λ��Ҳ��֮������
    cell = row.getCell( (short) 9);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(typenum); //��1��J��  ���ͱ��
    //CCEnd by liunan 2011-08-23
  
  row = sheet.getRow(2);
  cell = row.getCell( (short) 6); //��2�е�7�� ��������
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(makeDate);

  row = sheet.getRow(3);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(projectName); //��3��0�� ��Ŀ����

  row = sheet.getRow(23);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(genju); //��24��0�� ����

  row = sheet.getRow(24);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shuoming); //��25��0�� ˵��
  
  row = sheet.getRow(25);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(pizhunInfo); //��26��0�� ��׼
  
  cell = row.getCell( (short) 4);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shenheInfo); //��26��3�� ���
  cell = row.getCell( (short) 5);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(jiaoduiInfo); //��26��4�� У��
  cell = row.getCell( (short) 7);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(bianzhiInfo); //��26��7�� ����
  cell = row.getCell( (short) 8);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(routelistNumber); //��26��9�� ��׼��
  row = sheet.getRow(26);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(faWang); //��27��0�� ������λ



  //������ܽ��
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
        sheet1 = wb.cloneSheet(0); //�����ҳ��
    }
    int page = 0;
    for (int i = 0; i < result.size(); i++) {
      Object[] o = (Object[]) result.elementAt(i);
      if (i % this.getRowNumber() == 0) { //����Ϊ0,������
        page++;
        if (page == 1) {
          row = sheet.getRow(26);

          cell = row.getCell( (short) 8);

          //cell.setEncoding(cell.ENCODING_UTF_16);
          cell.setCellValue("��1ҳ  " + "��" + pageNumber + "ҳ");
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
          cell.setCellValue("��" + page + "ҳ  ��" + pageNumber + "ҳ");
        }

      }
      if (verbose) {
        System.out.println("i=" + i + "    page=" + page);
      }

//CCBegin by liunan 2011-08-25 ���ͼ��
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
    /* ��������˵��
dx1 	��1����Ԫ����x���ƫ����
dy1 	��1����Ԫ����y���ƫ����
dx2 	��2����Ԫ����x���ƫ����
dy2 	��2����Ԫ����y���ƫ����
col1 	��1����Ԫ����к�
row1 	��1����Ԫ����к�
col2 	��2����Ԫ����к�
row2 	��2����Ԫ����к�*/
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
//	CCBegin by leixiao 2010-1-5 �ر���
    out.close();
//	CCEnd by leixiao
    return true;
  }
  catch (Exception ex1) {
    ex1.printStackTrace();
    return false;
  }

}
//	CCBegin by leixiao 2009-12-17 ԭ����׼������� ��������10��>11��TD267������׼ʱ��עδ��ʾ
  /**
   * ��׼�б������
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
   * �ձ��б������
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getcompleteColNumber() {
	    return 9;
	  }
  


  /**
   * ÿҳ���������
   *
   * @return int
   * @roseuid 42C3634401E3
   */
  private int getRowNumber() {
    return 18;
  }

//CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
  //CCBegin by liunan 2012-01-30 ��resultŲ��������
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

  HSSFSheet sheet = wb.getSheetAt(0); //��õ�һ��ҳ��
//CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�
  HSSFRow row = sheet.getRow(0);
        
           
      //CCBegin by liunan 2011-08-23 ���ģ���޸ģ����ͼ�꣬����λ��Ҳ��֮������
           //HSSFCell cell = row.getCell( (short) 0); //��1�е�1�� ����
           HSSFCell cell = row.getCell( (short) 3); //��1��D�� ����           
           //CCEnd by liunan 2011-08-23 
      //cell.setEncoding(cell.ENCODING_UTF_16);
      cell.setCellValue(type);
    //CCEndby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ� 
    
    //CCBegin by liunan 2011-08-23 ���ģ���޸ģ����ͼ�꣬����λ��Ҳ��֮������
    cell = row.getCell( (short) 8);
    //cell.setEncoding(cell.ENCODING_UTF_16);
    cell.setCellValue(typenum); //��1��I��  ���ͱ��
    //CCEnd by liunan 2011-08-23
  
  row = sheet.getRow(2);
  cell = row.getCell( (short) 5); //��2�е�6�� ��������
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(makeDate);

  row = sheet.getRow(3);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(projectName); //��3��0�� ��Ŀ����

  row = sheet.getRow(21);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(genju); //��24��0�� ����

  row = sheet.getRow(22);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shuoming); //��25��0�� ˵��
  
  row = sheet.getRow(23);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(pizhunInfo); //��26��0�� ��׼
  
  cell = row.getCell( (short) 3);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(shenheInfo); //��26��3�� ���
  cell = row.getCell( (short) 4);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(jiaoduiInfo); //��26��4�� У��
  cell = row.getCell( (short) 6);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(bianzhiInfo); //��26��7�� ����
  cell = row.getCell( (short) 8);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(routelistNumber); //��26��9�� ��׼��
  row = sheet.getRow(24);
  cell = row.getCell( (short) 0);
  //cell.setEncoding(cell.ENCODING_UTF_16);
  cell.setCellValue(faWang); //��27��0�� ������λ



  //������ܽ��
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
        sheet1 = wb.cloneSheet(0); //�����ҳ��
    }
    int page = 0;
    for (int i = 0; i < result.size(); i++) {
      Object[] o = (Object[]) result.elementAt(i);
      if (i % this.getDisaffirmRowNumber() == 0) { //����Ϊ0,������
        page++;
        if (page == 1) {
          row = sheet.getRow(24);

          cell = row.getCell( (short) 8);

          //cell.setEncoding(cell.ENCODING_UTF_16);
          cell.setCellValue("��1ҳ  " + "��" + pageNumber + "ҳ");
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
          cell.setCellValue("��" + page + "ҳ  ��" + pageNumber + "ҳ");
        }

      }
      if (verbose) {
        System.out.println("i=" + i + "    page=" + page);
      }

//CCBegin by liunan 2011-08-25 ���ͼ��
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
    /* ��������˵��
dx1 	��1����Ԫ����x���ƫ����
dy1 	��1����Ԫ����y���ƫ����
dx2 	��2����Ԫ����x���ƫ����
dy2 	��2����Ԫ����y���ƫ����
col1 	��1����Ԫ����к�
row1 	��1����Ԫ����к�
col2 	��2����Ԫ����к�
row2 	��2����Ԫ����к�*/
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
   * �ձ��б������
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getdisaffirmColNumber() {
	    return 9;
	  }
  


  /**
   * ÿҳ���������
   *
   * @return int
   * @roseuid 42C3634401E3
   */
  private int getDisaffirmRowNumber() {
    return 16;
  }
//CCEnd by liunan 2011-09-21
}
