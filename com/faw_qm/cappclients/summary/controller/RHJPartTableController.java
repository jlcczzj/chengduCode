package com.faw_qm.cappclients.summary.controller;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.faw_qm.framework.remote.RemoteProperty;

/**
 * <p>Title: 软化件零件一览表的输出</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:启明公司 </p>
 * @author 唐树涛
 * @version 1.0
 */
public class RHJPartTableController {
  public RHJPartTableController() {
  }

  /**
   * 根据汇总结果,部门，设备类型以及导出目录，获得相应的模板进行填充
   * @param result Vector
   * @param condition String
   * @param path String
   * @param department String
   */
  public void printFile(JPanel panel, Vector result, String partstring,
                        String department,
                        String path) {
    String url = "http://" +
        RemoteProperty.getProperty("server.hostName", "") +
        RemoteProperty.getProperty("summaryTemplate",
                                   "/PhosphorPDM/summaryTemplate/") +
        "rhjpart.xls";
    InputStream stream = null;
    try {
      URL aurl = new URL(url);
      stream = aurl.openStream();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return;
    }
    POIFSFileSystem fs = null;
    HSSFWorkbook wb = null;
    try {
      fs = new POIFSFileSystem(stream);
      wb = new HSSFWorkbook(fs);
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }

    HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
    HSSFRow row = sheet.getRow(1);
    HSSFCell cell = row.getCell( (short) 0); //第1行第0列
    
    //部门
    cell.setCellValue(department);
    //零件号
    row = sheet.getRow(2);
    cell = row.getCell( (short) 2); //第2行第2列
    
    cell.setCellValue(partstring);

    HSSFSheet sheet1 = wb.cloneSheet(0); //缓存空页面

    //处理汇总结果
    if (result != null && result.size() > 0) {
      int pageNumber = 0;
      int psize = result.size();
      if (psize % 25 == 0) {
        pageNumber = psize / 25;
      }
      else {
        pageNumber = 1 + (psize / 25);
      }
      int page = 0;
      for (int i = 0; i < result.size(); i++) {
        Object[] o = (Object[]) result.elementAt(i);
        if (i % 25 == 0) { //余数为0,即整除
          page++;
          if (page == 1) {
            row = sheet.getRow(0);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("共" + pageNumber + "页");
            row = sheet.getRow(1);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("第1页");

          }
          else if (page > 1) {
            sheet = sheet1;
            wb.setSheetName(page - 1, "Sheet" + page);
            if (page < pageNumber) {
              sheet1 = wb.cloneSheet(page - 1);
            }
            row = sheet.getRow(0);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("共" + pageNumber + "页");
            row = sheet.getRow(1);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("第" + page + "页");

          }

        }

        int rowIndex = 5 + i % 25;
        row = sheet.getRow(rowIndex);
        cell = row.getCell( (short) 0);
        
        cell.setCellValue(i + 1);

        for (int j = 0; j < o.length; j++) {
          cell = row.getCell( (short) (j));
          
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

    }
    catch (Exception ex1) {
      ex1.printStackTrace();
      JOptionPane.showMessageDialog(panel,
                                    "建立文件" + path + "错误!");
      return;
    }

  }

}
