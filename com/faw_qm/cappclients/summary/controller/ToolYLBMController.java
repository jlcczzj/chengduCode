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
import com.faw_qm.cappclients.summary.view.ToolPanel;

/**
 * <p>
 * Title: �о���ϸ�����
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: ������˾
 * </p>
 * 
 * @author �Ҵ���
 * @version 1.0
 * SS1 ����poi��3.6��ע��//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
 */
public class ToolYLBMController {
	public ToolYLBMController() {
	}

	/**
	 * ���ݻ��ܽ��,���ţ����������Լ�����Ŀ¼�������Ӧ��ģ��������
	 * 
	 * @param result
	 *            Vector
	 * @param condition
	 *            String
	 * @param path
	 *            String
	 * @param department
	 *            String
	 * @param toolType
	 *            String
	 */
	public void printFile(JPanel panel, Vector result, String department,
			String path, String partnum, String partname) {
		String url = "http://"
				+ RemoteProperty.getProperty("server.hostName", "")
				+ RemoteProperty.getProperty("routeListTemplate",
						"/PhosphorPDM/template/bsx/") + "gjylbm.xls";
	//	String url="http:"+"//"+"10.7.64.33"+"/"+"PhosphorPDM"+"/"+"template"+"/"+"gjylbm.xls";
		if (department != null && !department.equals("")&& department.trim().length() >0) {
			department = department;
		} else {
			department = "";
		}
		InputStream stream = null;
		try {
			URL aurl = new URL(url);
			stream = aurl.openStream();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(stream);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
		HSSFRow row = sheet.getRow(2);
		HSSFCell cell = row.getCell((short) 0); // ��1�е�0��

		HSSFSheet sheet1 = wb.cloneSheet(0); // �����ҳ��

		// ������ܽ��
		if (result != null && result.size() > 0) {
			int pageNumber = 0;
			int psize = result.size();
			if (psize % 17 == 0) {
				pageNumber = psize / 17;
			} else {
				pageNumber = 1 + (psize / 17);
			}
			int page = 0;
			for (int i = 0; i < result.size(); i++) {
				Object[] o = (Object[]) result.elementAt(i);
				if (i % 17 == 0) { // ����Ϊ0,������
					page++;
					if (page == 1) {
						// д��ͷ
						// �㲿�����
						row = sheet.getRow(2);
						cell = row.getCell((short) 7);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue(partnum);
						// �㲿������
						row = sheet.getRow(2);
						cell = row.getCell((short) 9);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue(partname);
						// ����
						row = sheet.getRow(2);
						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						if (!department.equals(""))
							cell.setCellValue(department + "����");

						row = sheet.getRow(4);
						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue("��  " + pageNumber + "  ҳ");

						row = sheet.getRow(5);
						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue("��  1  ҳ");

					} else if (page > 1) {
						sheet = sheet1;
						wb.setSheetName(page - 1, "Sheet" + page);
						if (page < pageNumber) {
							sheet1 = wb.cloneSheet(page - 1);
						}
						// д��ͷ
						// �㲿�����
						row = sheet.getRow(2);
						cell = row.getCell((short) 7);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue(partnum);
						// �㲿������
						row = sheet.getRow(2);
						cell = row.getCell((short) 9);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue(partname);
						// ����
						row = sheet.getRow(2);
						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						if (!department.equals(""))
							cell.setCellValue(department + "����");

						row = sheet.getRow(4);
						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue("��  " + pageNumber + "  ҳ");

						row = sheet.getRow(5);
						cell = row.getCell((short) 11);
						//cell.setEncoding(cell.ENCODING_UTF_16);
						cell.setCellValue("��  " + page + "  ҳ");

					}

				}
				// ��3��
				int rowIndex = 6 + i % 17;
				row = sheet.getRow(rowIndex + 1);
				cell = row.getCell( (short) 0);
		        //cell.setEncoding(cell.ENCODING_UTF_16);
		        cell.setCellValue(i + 1);
				
				for (int j = 0; j < o.length; j++) {
					switch (j) {
					case 0:
						cell = row.getCell((short) (1));
						break;
					case 1:
						cell = row.getCell((short) (3));//anan
						break;
					case 2:
						cell = row.getCell((short) (4));
						break;
					case 3:
						cell = row.getCell((short) (5));
						break;
					case 4:
						cell = row.getCell((short) (6));
						break;
					case 5:
						cell = row.getCell((short) (7));
						break;
					case 6:
						cell = row.getCell((short) (10));//anan
						break;
					case 7:
						cell = row.getCell((short) (11));//anan
						break;
					case 8:
						cell = row.getCell((short) (10));
						break;
					case 9:
						cell = row.getCell((short) (11));
						break;
					case 10:
						cell = row.getCell((short) (12));
						break;
					}
					//cell.setEncoding(cell.ENCODING_UTF_16);
					if (o[j] == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(o[j].toString());

					}
				}
			}
		}
		// ��ϸ����
		try {
			FileOutputStream out = new FileOutputStream(path);
			wb.write(out);

		} catch (Exception ex1) {
			ex1.printStackTrace();
			JOptionPane.showMessageDialog(panel, "�����ļ�" + path + "����!");
			return;
		}

	}

}
