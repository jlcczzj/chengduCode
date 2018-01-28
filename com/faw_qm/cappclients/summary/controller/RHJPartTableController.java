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
 * <p>Title: �������һ��������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:������˾ </p>
 * @author ������
 * @version 1.0
 */
public class RHJPartTableController {
  public RHJPartTableController() {
  }

  /**
   * ���ݻ��ܽ��,���ţ��豸�����Լ�����Ŀ¼�������Ӧ��ģ��������
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

    HSSFSheet sheet = wb.getSheetAt(0); //��õ�һ��ҳ��
    HSSFRow row = sheet.getRow(1);
    HSSFCell cell = row.getCell( (short) 0); //��1�е�0��
    
    //����
    cell.setCellValue(department);
    //�����
    row = sheet.getRow(2);
    cell = row.getCell( (short) 2); //��2�е�2��
    
    cell.setCellValue(partstring);

    HSSFSheet sheet1 = wb.cloneSheet(0); //�����ҳ��

    //������ܽ��
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
        if (i % 25 == 0) { //����Ϊ0,������
          page++;
          if (page == 1) {
            row = sheet.getRow(0);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("��" + pageNumber + "ҳ");
            row = sheet.getRow(1);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("��1ҳ");

          }
          else if (page > 1) {
            sheet = sheet1;
            wb.setSheetName(page - 1, "Sheet" + page);
            if (page < pageNumber) {
              sheet1 = wb.cloneSheet(page - 1);
            }
            row = sheet.getRow(0);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("��" + pageNumber + "ҳ");
            row = sheet.getRow(1);
            cell = row.getCell( (short) 9);
            
            cell.setCellValue("��" + page + "ҳ");

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
                                    "�����ļ�" + path + "����!");
      return;
    }

  }

}
