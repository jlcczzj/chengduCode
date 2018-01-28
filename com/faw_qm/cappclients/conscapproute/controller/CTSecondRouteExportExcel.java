//SS1 升级poi到3.6，注释//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
package com.faw_qm.cappclients.conscapproute.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;

public class CTSecondRouteExportExcel {
	
	  private String makeDate = "";
	  private String head="";
	  private String projectNum = "";
	  private String routelistNumber = "";
	  private Vector result = new Vector();
	  private static final boolean verbose = false;


	  public CTSecondRouteExportExcel() {
	  }

	  /**
	   * 收集报表数据
	   * @param routeListID String
	   * @param IsExpandByProduct 是否按车展开（暂时默认总为真）
	   * @throws QMException
	   */
	  public void gatherExportData(String routeListID) throws
	      QMException
	  {
		  
		  
	      try
	      {
	          Class[] c =
	              {
	              String.class};
	          Object[] oo =
	              {
	              routeListID};
	          result = (Vector) CappRouteAction.
	              useServiceMethod(
	              "consTechnicsRouteService", "CTSecondgatherExportData", c, oo);
	          head=(String) CappRouteAction.
              useServiceMethod(
    	              "consTechnicsRouteService", "getSencondRouteReportHead", c, oo);
	          projectNum=(String) CappRouteAction.
              useServiceMethod(
    	              "consTechnicsRouteService", "getSecondPartProduct", c, oo);
	          
	      }
	      catch(Exception e)
	      {
	          e.printStackTrace();
	      }  
	    
	  }

	  
	  /**
	   * 输出报表
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
		        "ctsecond.xls";

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
	    	HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
	  
	    	HSSFRow row = sheet.getRow(2);
	    	HSSFCell cell = null;
	       	cell = row.getCell( (short) 5); //第2行第5列 题头        
	    	//cell.setEncoding(cell.ENCODING_UTF_16);
	    	cell.setCellValue(head);
	    	
	    	HSSFRow row1 = sheet.getRow(4);
	    	cell=row1.getCell( (short) 5); 
	    	//cell.setEncoding(cell.ENCODING_UTF_16);
	    	cell.setCellValue(projectNum);
	    	
	    	for(int n=0;n<result.size();n++){
	    		String[] str = (String[] )result.get(n);
	    		HSSFRow row2 = sheet.getRow(6+n);
	    		
	    	 	cell = row.getCell( (short) 0);   
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue( str[1]);
			    
				cell = row.getCell( (short) 1);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue( String.valueOf(n+1));
			    
			    cell = row.getCell( (short) 2);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue( str[2]);
			    
			    cell = row.getCell( (short) 3);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[3]);
			    
			    cell = row.getCell( (short) 5);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[4]);
			    
			    cell = row.getCell( (short) 6);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[5]);
			    
			    cell = row.getCell( (short) 7);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[6]);
			    
			    cell = row.getCell( (short) 8);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[7]);
			    
			    cell = row.getCell( (short) 9);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[8]);
			    
			    cell = row.getCell( (short) 10);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[9]);
			    
			    cell = row.getCell( (short) 11);    
			    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
			    cell.setCellValue(str[10]);	    
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

	  
	}

