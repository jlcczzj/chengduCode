package com.faw_qm.cappclients.conscapproute.controller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import java.io.InputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import javax.swing.JOptionPane;
import java.net.URL;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;

import java.util.Vector;
import java.io.FileOutputStream;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;

import java.util.*;

import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.conscapproute.web.ReportLevelOneUtil;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 * SS1 �޸ĵ���excelģ�����ƶ�Ӧ�������� pante 20130730
 * SS2 �����������ģ�� liuyang 2014-1-13
 * SS3 ��������û�����ë��״̬����  liuyang 2014-1-14
 * SS4 �ж���������û�  liuyang 2014-1-14
 * SS5 �����������ģ���� liuyang 2014-1-14
 * SS6  �������˵����Ϊ���� liuyang 2014-4-17
 * SS7 ������ĵ�������ѡ�е�һ��sheetҳ Liuyang 2014-4-23
 * SS8 ��ʾ��ע Liuyang 2014-4-24
 * SS9 ��׼ģ��ȥ���汾�У���һ��������ţ�����źͰ汾�ϲ���һ�С� liunan 2015-5-5
 * SS10 �������ձϣ����ӵڶ�����ʾ����Ϸ�ΪW�� liunan 2016-3-27
 * SS11 ����poi��3.6��ע��//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
 */
public class ExportExcel {

  private String makeDate = "";
  private String projectName = "";
  private String routelistNumber = "";
  private Vector result = new Vector();
  private static final boolean verbose = false;
  private String shuoming = "";
  private String shuoming1 = "" ;
  private String pizhunInfo = "";
  private String shenheInfo = "";
  private String jiaoduiInfo = "";
  private String bianzhiInfo = "";
  private String faWang = "";
  //CCBegin by liunan 2012-6-4 ����·��״̬�������շ��������������boolean�͡�
  //private static boolean isYshizhun = false ;
  private static String routeListState = "" ;
  //CCEnd by liunan 2012-6-4
  //CCBegin SS4
  private String comp="";
  //CCEnd SS4
//CCBegin SS6
  private String shuoming2="";
  private String shuoming3="";
  //CCEnd SS6
  public ExportExcel() {
  }

  /**
   * �ռ���������
   * @param routeListID String
   * @param IsExpandByProduct �Ƿ񰴳�չ������ʱĬ����Ϊ�棩
   * @throws QMException
   */
  public void gatherExportData(String routeListID) throws
      QMException
  {
      ArrayList allInformationColl = new ArrayList();
      try
      {
          Class[] c =
              {
              String.class};
          Object[] oo =
              {
              routeListID};
          allInformationColl = (ArrayList) CappRouteAction.
              useServiceMethod(
              "consTechnicsRouteService", "gatherExportData", c, oo);
        //CCBegin SS4
          RequestServer server = RequestServerFactory.getRequestServer();
          StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
      	  info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
          info1.setMethodName("getUserFromCompany");
          Class[] classes = {};
          info1.setParaClasses(classes);
          Object[] objs = {};
          info1.setParaValues(objs);
          comp=(String)server.request(info1);
          //CCEnd SS4
          
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    String[] header = (String[]) allInformationColl.get(0);
    //CCBegin by wanghonglian 2008-09-05
    //ȥ����Ŀ�еġ����ڲ�Ʒ�������Բ��֣�ֱ����ʾ��׼������
    //Դ�������£�
    //projectName = "��Ŀ����:" + header[3]+" "+header[0];
       projectName = "��Ŀ����:" +header[0];
    //CCEnd by wanghonglian 2008-09-05
    //CCBegin by wanghonglian 2008-06-04
    //�ж��Ƿ�Ϊ����׼״̬
    boolean isYsz =isYshizhun(routeListID);
    if(isYsz)
    {
    	routelistNumber = header[1];
    }
    else 
    	routelistNumber = header[1];
    //CCEnd by wanghonglian 2008-06-04
    //routelistNumber = "��ţ�"+header[1];
      makeDate = "��������:" + header[2];

    //˵��
    Collection tail2 = (Collection) allInformationColl.get(3);

    if (tail2 != null && tail2.size() > 0) {
      Object[] des = tail2.toArray();
      shuoming = des[0].toString();
      if(des.length!=1)
      {
       shuoming1 = des[1].toString();
       //CCBegin by wanghonglian 2008-06-05
       //��˵����Ϣ�����ԡ�˵��������ʼ����Ϣ��Ϊ·�ߴ���˵����Ϣ
       //CCBegin by wanghonglian 2008-09-05
       //������˵����Ϣд�뵽excel�ļ���,����Ҫȥ��ǰ��ġ�˵������
      /* if(shuoming1.indexOf("˵����")!=-1)
       {
    	   shuoming1 = shuoming1.substring(shuoming1.indexOf("˵����")+3);
       }*/
       //CCEnd by wanghonglian 2008-09-05
       //CCEnd by wanghonglian 2008-06-05 
      }
      //CCBegin SS6
      if(comp.equals("zczx")){
    	  shuoming2=des[2].toString();
    	  shuoming3=des[3].toString();
      }
      //CCEnd SS6
      
    }
    ////////////////////���������Ϣ  �������� 20061213
    Vector wf = (Vector) allInformationColl.get(4);
    if (wf != null && wf.size() > 0) {

      //////////////////���///////////////////////////
      ArrayList pizhun = (ArrayList) wf.elementAt(0);
      //CCBegin by wanghonglian 2008-06-04
      //����У���ߺ���׼�ߵ���Ϣ��ʾ
      ArrayList shenhe = (ArrayList) wf.elementAt(1);
      ArrayList jiaodui = (ArrayList) wf.elementAt(2);
      StringBuffer pizhunBuffer = new StringBuffer("��׼��");
      if (pizhun != null && pizhun.size() != 0) {
        for (int i = 0; i < pizhun.size(); i++) {
          Object p1 = pizhun.get(i);
          if (p1 != null) {
        	  pizhunBuffer.append(p1.toString() + " ");
          }
        }
      }
      pizhunInfo = pizhunBuffer.toString();
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
      StringBuffer jiaoduiBuffer = new StringBuffer("У�ԣ�");
      if (jiaodui != null && jiaodui.size() != 0) {
        for (int i = 0; i < jiaodui.size(); i++) {
          Object p1 = jiaodui.get(i);
          if (p1 != null) {
        	  jiaoduiBuffer.append(p1.toString() + " ");
          }
        }
      }
      jiaoduiInfo = jiaoduiBuffer.toString();
      //CCEnd by wanghonglian 2008-06-04
    
      ////////////////////////////////////////////
      bianzhiInfo = "���ƣ�" + wf.elementAt(3).toString();
    }
    ////////////////////////////////////////////////

    String tail1 = (String) allInformationColl.get(2);
    faWang = "������λ:" + tail1;
    Collection informationColl = (Collection) allInformationColl.get(1);
    for (Iterator iter = informationColl.iterator(); iter.hasNext(); )
    {
      Object[] arrayObjs = (Object[]) iter.next();
    //  String xuhao = (String) arrayObjs[0];
      String changeSign = (String) arrayObjs[0];//zczx�����
      String partNum = (String) arrayObjs[1];//zczx�Ǹ��ı��
      String version = (String) arrayObjs[2];//zczx�Ǳ��+�汾
      //partNum = partNum + version;
      String partName = (String) arrayObjs[3];
      String countInProduct = (String) arrayObjs[4];
      //if(changeSign.equals("G"))
      //{
        //countInProduct = "";
      //}
      String partMakeRoute = (String) arrayObjs[5];
      String remark ="";
//CCBegin SS8
//      if(arrayObjs.length==9)
//      {
      //CCEnd SS8
         remark = (String) arrayObjs[8];
         //CCBegin SS8
//      }
         //CCEnd SS8
      //CCBegin SS3
      String stockID=(String)arrayObjs[9];
      //CCEnd SS3
      //CCBegin by wanghonglian 2008-06-05
      //�˴����װ���������Ϣ��ȥ����ţ����Ӱ汾��Ϣ����ʾ��ԭ�������£�
/*      String[] array1 = {
              xuhao, changeSign, partNum, partName, countInProduct,
              partMakeRoute, "", (String) arrayObjs[7],
              "",remark};
          result.addElement(array1);*/
   
      //CCBegin by wanghonglian 2008-09-05
      //��������ı���У�װ��·�߱�д����עһ��������
      //CCBegin SS3
//      String[] array1 = {
//              changeSign, partNum, version,partName, countInProduct,
//             partMakeRoute, (String) arrayObjs[6],remark};
      if(comp.equals("zczx")){
    	  String[] array1 = {
                  changeSign, partNum, version,partName, countInProduct,stockID,
                 partMakeRoute, (String) arrayObjs[6],remark};
    	  result.addElement(array1);
      }else{
      	//CCBegin SS10
      	if(routeListState.equals("yb"))
      	{
      		String[] array1 = {
                   changeSign, "W", partNum, version,partName, countInProduct,
                  partMakeRoute, (String) arrayObjs[6],remark};
    	    result.addElement(array1);
      	}
      	else
      	{
      	//CCEnd SS10
    	  String[] array1 = {
                   changeSign, partNum, version,partName, countInProduct,
                  partMakeRoute, (String) arrayObjs[6],remark};
    	  result.addElement(array1);
      	//CCBegin SS10
      	}
      	//CCEnd SS10
      }
        
          //CCEnd SS3
             
            //CCEnd by wanghonglian 2008-06-05     
    }
  }
  
  /**
   * ���ݸ���·�ߣ��������Ӧ��״̬
   * �ж�����״̬�Ƿ�Ϊ����׼״̬
   * @author ������
   * @version 1.0
   */
  
  private static boolean isYshizhun(String routeListID)throws
      QMException
{
	boolean isYsz = false ;
	 TechnicsRouteListIfc listInfo = null;
    try
    {
        Class[] c =
            {
            String.class};
        Object[] oo =
            {
            routeListID};
        listInfo = (TechnicsRouteListIfc) CappRouteAction.
            useServiceMethod(
            "PersistService", "refreshInfo", c, oo);
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
	String state = listInfo.getRouteListState();
	System.out.println("state============"+state);
	if(state != null && (state.equalsIgnoreCase("����")||state.equalsIgnoreCase("����׼")))
	{
		isYsz = true ;
		//CCBegin by liunan 2012-6-4 ����·��״̬�������շ��������������boolean�͡�
		routeListState = "sz";
		//CCEnd by liunan 2012-6-4
	}
	//CCBegin by liunan 2012-6-4 ����·��״̬�������շ��������������boolean�͡�
	//isYshizhun = isYsz ;
	else if(state != null && state.equalsIgnoreCase("��׼"))
	{
		
		routeListState = "yz";
	}
	else if(state != null && (state.equalsIgnoreCase("�շ�")||state.equalsIgnoreCase("����")))
	{
		routeListState = "yf";
	}
	else if(state != null && state.equalsIgnoreCase("��׼"))
	{
		routeListState = "lz";
	}
	else if(state != null && state.equalsIgnoreCase("ǰ׼"))
	{
		routeListState = "qz";
	}
	else if(state != null && state.equalsIgnoreCase("�ձ�"))
	{
		routeListState = "yb";
	}
	//CCEnd by liunan 2012-6-4
	return isYsz ;
}

  
  /**
   * �������
   *
   * @param path
   * @roseuid 42C363440215
   */
  public boolean printFile(String path) {
	  //CCBegin by wanghonglian 2008-06-04
	  //�ж��Ƿ�Ϊ����׼״̬�����������׼״̬��ѡ��ģ��yszrouteListTemplate.xls������ѡ��ģ��routeListTemplate.xls
	  String url = "";
	  //CCBegin by liunan 2012-6-4 ����·��״̬�������շ��������������boolean�͡�
	  //if(isYshizhun)
	  System.out.println("routeListState=============="+routeListState);
	  if(routeListState.equals("sz"))
	  //CCEnd by liunan 2012-6-4
	  {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zcsz.xls";
			}else{
		    //CCEnd SS2
		    url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "sz.xls";
			}
	  }
	  //CCBegin by liunan 2012-6-4 ����·��״̬�������շ��������������boolean�͡�
	  //else
	  else if(routeListState.equals("yz"))
	  //CCEnd by liunan 2012-6-4
	  //CCEnd by wanghonglian 2008-06-04
    {
		  //CCBegin SS2
		if(comp.equals("zczx")){
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +"zcyz.xls";
		}else{
	    //CCEnd SS2
		url = "http://" +
        RemoteProperty.getProperty("server.hostName", "") +
        RemoteProperty.getProperty("routeListTemplate",
                                   "/PhosphorPDM/template/") +
                                   //SSBegin SS1
        "yz.xls";
		}
		//SSEnd SS1
    }
	  //CCBegin by liunan 2012-6-4 ����·��״̬�������շ��������������boolean�͡�
	  else if(routeListState.equals("yf"))
 {
			// CCBegin SS2
			if (comp.equals("zczx")) {
				url = "http://"
						+ RemoteProperty.getProperty("server.hostName", "")
						+ RemoteProperty.getProperty("routeListTemplate",
								"/PhosphorPDM/template/") + "zcyf.xls";
			} else {
				// CCEnd SS2
				url = "http://"
						+ RemoteProperty.getProperty("server.hostName", "")
						+ RemoteProperty.getProperty("routeListTemplate",
								"/PhosphorPDM/template/") + "yf.xls";
			}
		}
	  else if(routeListState.equals("yb"))
	    {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zcyb.xls";
			}else{
		    //CCEnd SS2
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "yb.xls";
			}
	    }
	  else if(routeListState.equals("lz"))
	    {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zclz.xls";
			}else{
		    //CCEnd SS2
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "lz.xls";
			}
	    }
	  else if(routeListState.equals("qz"))
	    {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zcqz.xls";
			}else{
		  //CCEnd SS2
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "qz.xls";
			}
	    }
	  //CCEnd by liunan 2012-6-4
    System.out.println("URL = "+url);
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
  
    	HSSFRow row = sheet.getRow(2);
    	HSSFCell cell = null;
    	//CCBegin SS5
    	if(comp.equals("zczx")){
    		cell = row.getCell( (short) 8); //��2�е�9�� ��������
    	}else{
    	if(!routeListState.equals("yb"))
    		cell = row.getCell( (short) 7); //��2�е�8�� ��������
    	else
    		cell = row.getCell( (short) 8); //��2�е�9�� ��������
    	}
    	//CCEnd SS5
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(makeDate);

    	row = sheet.getRow(3);
    	cell = row.getCell( (short) 0);
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(projectName); //��3��0�� ��Ŀ����

    	row = sheet.getRow(20);
    	cell = row.getCell( (short) 0);
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(shuoming); //��21��0�� ˵��

    	row = sheet.getRow(21);
    	cell = row.getCell( (short) 0);
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(shuoming1); //��22��0�� ˵��1,
    	//CCBegin SS6
        if(comp.equals("zczx")){
        	row = sheet.getRow(22);
        	cell = row.getCell( (short) 0);
        	//cell.setEncoding(cell.ENCODING_UTF_16);
        	cell.setCellValue(shuoming2); //��23��˵��2
        	
        	row = sheet.getRow(23);
        	cell = row.getCell( (short) 0);
        	//cell.setEncoding(cell.ENCODING_UTF_16);
        	cell.setCellValue(shuoming3); //��24��0�� ·�ߴ���˵��

        	row = sheet.getRow(24);
        	cell = row.getCell( (short) 0);
        	//cell.setEncoding(cell.ENCODING_UTF_16);
        	for(int n=0;n<13-pizhunInfo.trim().length();n++){
        		pizhunInfo+=" ";
        	}
        	for(int n=0;n<13-shenheInfo.trim().length();n++){
        		shenheInfo+=" ";
        	}
        	for(int n=0;n<13-jiaoduiInfo.trim().length();n++){
        		jiaoduiInfo+=" ";
        	}
        	for(int n=0;n<13-bianzhiInfo.trim().length();n++){
        		bianzhiInfo+=" ";
        	}
        	String str=pizhunInfo+shenheInfo+jiaoduiInfo
        	            +bianzhiInfo+routelistNumber;
        	cell.setCellValue(str);
        	
        	row = sheet.getRow(25);
			cell = row.getCell((short) 0);
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(faWang); // ��26��0�� ������λ

        	
		} else {
			//CCEnd SS6
			row = sheet.getRow(22);
			cell = row.getCell((short) 0);
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(pizhunInfo); // ��23��0�� ��׼

			// CCBegin by wanghonglian 2008-06-04
			// ��������е���Ϣ��ʾ�к���
			cell = row.getCell((short) 2);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 4);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(shenheInfo); // ��23��2�� ���

			cell = row.getCell((short) 4);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 5);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(jiaoduiInfo); // ��23��4�� У��

			// CCEnd by wanghonglian 2008-06-04
			cell = row.getCell((short) 6);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 7);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(bianzhiInfo); // ��23��5�� ����

			cell = row.getCell((short) 7);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 8);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(routelistNumber); // ��26��7�� ��׼��

			row = sheet.getRow(23);
			cell = row.getCell((short) 0);
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(faWang); // ��27��0�� ������λ

		}
    	System.out.println("result----------------"+result.size());
    	//������ܽ��
    	if (result != null && result.size() > 0) {
    		System.out.println("ssssssssss----------------");
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
				if (i % this.getRowNumber() == 0) { // ����Ϊ0,������
					page++;
					if (page == 1) {
						
						// CCBegin SS5
						if (comp.equals("zczx")) {
							row = sheet.getRow(26);
							cell = row.getCell((short) 8);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("��" + pageNumber + "ҳ  "
									+ "��1ҳ  ");
						} else {
							row = sheet.getRow(24);
							if (!routeListState.equals("yb"))
								cell = row.getCell((short) 7);
							else
								cell = row.getCell((short) 2);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("��" + pageNumber + "ҳ  "
									 +  "��1ҳ  ");
						}
						// CCEnd SS5
					} else if (page > 1) {
						sheet = sheet1;
						wb.setSheetName(page - 1, "Sheet" + page);
						//CCBegin SS7
						if (comp.equals("zczx")) {
						sheet1.setSelected(false);
						}
						//CCEnd SS7
						if (page < pageNumber) {
							sheet1 = wb.cloneSheet(page - 1);
						}
					
						// CCBegin SS5
						if (comp.equals("zczx")) {
							row = sheet.getRow(26);
							cell = row.getCell((short) 8);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("��" + pageNumber + "ҳ  "
									+ "��"
									+ page + "ҳ");
					
						} else {
							// CCEnd SS5
							row = sheet.getRow(24);
							if (!routeListState.equals("yb"))
								cell = row.getCell((short) 7);
							else
								cell = row.getCell((short) 2);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("��" + pageNumber + "ҳ  " + "��"
									+ page + "ҳ");
						}
						
					}
				
				}
    			if (verbose) {
    				System.out.println("i=" + i + "    page=" + page);
    			}

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
        //CCBegin SS7
		if (comp.equals("zczx")) {
			HSSFSheet sheet1 = wb.getSheetAt(0); // ��õ�һ��ҳ��
			wb.setSheetName(0, "Sheet1");
			sheet1.setSelected(true);
		}
    	//CCEnd SS7

    try {
      FileOutputStream out = new FileOutputStream(path);
      wb.write(out);
      return true;
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
      return false;
    }

  }

  /**
   * �б������
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getColNumber() {
    return 8;
  }

  /**
   * ÿҳ���������
   *
   * @return int
   * @roseuid 42C3634401E3
   */
  private int getRowNumber() {
    return 15;
  }

}
