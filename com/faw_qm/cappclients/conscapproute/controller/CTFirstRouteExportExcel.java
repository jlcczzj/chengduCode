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
 */
//SS1 ����һ��·�ߵ�������û����ʾ·��˵��  ���� 2015-2-6
//SS2 ����poi��3.6��ע��//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
public class CTFirstRouteExportExcel {

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
  private String huiqianInfo="";
  private static String routeListState = "" ;

  private String comp="";

  private String shuoming2="";
  private String shuoming3="";

  public CTFirstRouteExportExcel() {
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
          
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    String[] header = (String[]) allInformationColl.get(0);

       projectName = "��Ŀ����:" +header[0];

  
    	routelistNumber = header[1];

      makeDate = "��������:" + header[2];

    Collection tail2 = (Collection) allInformationColl.get(3);

    if (tail2 != null && tail2.size() > 0) {
      Object[] des = tail2.toArray();
      shuoming = des[0].toString();
      if(des.length!=1)
      {
       shuoming1 = des[1].toString();
      }
      
    }
    Vector wf = (Vector) allInformationColl.get(4);
    if (wf != null && wf.size() > 0) {

      //////////////////���///////////////////////////
      ArrayList pizhun = (ArrayList) wf.elementAt(0);

      ArrayList shenhe = (ArrayList) wf.elementAt(1);
      ArrayList jiaodui = (ArrayList) wf.elementAt(2);
      ArrayList huiqian=(ArrayList)wf.elementAt(3);
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
      StringBuffer huiqianBuffer = new StringBuffer("��ǩ��");
      if (huiqian != null && huiqian.size() != 0) {
        for (int i = 0; i < huiqian.size(); i++) {
          Object p1 = huiqian.get(i);
          if (p1 != null) {
            huiqianBuffer.append(p1.toString() + " ");
          }
        }
      }
      huiqianInfo = huiqianBuffer.toString(); 

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

      bianzhiInfo = "���ƣ�" + wf.elementAt(4).toString();
    }
    ////////////////////////////////////////////////

    String tail1 = (String) allInformationColl.get(2);
    faWang = "������λ:" + tail1;
    Collection informationColl = (Collection) allInformationColl.get(1);
    int n=1;
    for (Iterator iter = informationColl.iterator(); iter.hasNext(); )
    {
      Object[] arrayObjs = (Object[]) iter.next();
    //  String xuhao = (String) arrayObjs[0];
      String changeSign = (String) arrayObjs[0];
      String partNum = (String) arrayObjs[1];
      String version = (String) arrayObjs[2];
      //partNum = partNum + version;
      String partName = (String) arrayObjs[3];
      String countInProduct = (String) arrayObjs[4];

      String partMakeRoute = (String) arrayObjs[5];
      String remark ="";

         remark = (String) arrayObjs[8];

      String stockID=(String)arrayObjs[9];

     String xuhao=String.valueOf(n);
     String[] array1 = {xuhao,
                  changeSign, partNum, partName," " ,countInProduct,
                 partMakeRoute, (String) arrayObjs[6]," "," "," ",remark};
   	  result.addElement(array1);
       n++;
  
      }
        
  
    
  }

  
  /**
   * �������
   *
   * @param path
   * @roseuid 42C363440215
   */
  public boolean printFile(String path) {
	 
	  String url = "";

	 url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "ctfirst.xls";

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

    	cell = row.getCell( (short) 11); //��2�е�11�� ��������
   
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
		row = sheet.getRow(22);
		cell = row.getCell((short) 0);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(pizhunInfo); // ��23��0�� ��׼

			
		cell = row.getCell((short) 3);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(shenheInfo); // ��23��2�� ���
		
		cell = row.getCell((short) 4);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(huiqianInfo); // ��23��4�� ���
		
		cell = row.getCell((short) 6);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(jiaoduiInfo); // ��23��6�� У��

			
		cell = row.getCell((short) 8);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(bianzhiInfo); // ��23��8�� ����

		cell = row.getCell((short) 10);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(routelistNumber); // ��26��7�� ��׼��

		row = sheet.getRow(23);
		cell = row.getCell((short) 0);
		//cell.setEncoding(cell.ENCODING_UTF_16);
		cell.setCellValue(faWang); // ��27��0�� ������λ

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
				if (i % this.getRowNumber() == 0) { // ����Ϊ0,������
					page++;
					if (page == 1) {

						row = sheet.getRow(23);

						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue("��" + pageNumber + "ҳ  " + "��1ҳ  ");
					}

				 else if (page > 1) {
					sheet = sheet1;
					wb.setSheetName(page - 1, "Sheet" + page);
					if (page < pageNumber) {
						sheet1 = wb.cloneSheet(page - 1);
					}

					row = sheet.getRow(23);

					cell = row.getCell((short) 11);
					//cell.setEncoding(cell.ENCODING_UTF_16);
					cell.setCellValue("��" + pageNumber + "ҳ  " + "��" + page
							+ "ҳ");

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
	 //CCBegin SS1
     //return 11;
	  return 12;
    //CCEnd SS1
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
