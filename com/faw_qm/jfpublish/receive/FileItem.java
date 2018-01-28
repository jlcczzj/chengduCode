package com.faw_qm.jfpublish.receive;

/**
 * <p>
 * Title: �ļ��б��װ��
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: FAW_QM
 * </p>
 *
 * @author ShangHaiFeng
 * @version 1.0
 */

public class FileItem {

  // �ļ���
  private String fn;

  // �ļ�·�������ļ���
  private String fp;

  public FileItem() {
    this.fn = null;
    this.fp = null;
  }

  public FileItem(String fileN, String fpfn) {
    this.fn = fileN;
    this.fp = fpfn;
  }

  /**
   * �����ļ���
   *
   * @param fileN
   *            String
   */
  public void setFileName(String fileN) {
    this.fn = fileN;
  }

  /**
   * �����ļ�·��ȫ��,�����ļ���
   *
   * @param fpfn
   *            String
   */
  public void setFilePathAndName(String fpfn) {
    this.fp = fpfn;
    if (fpfn != null) {
      int pos = fpfn.lastIndexOf(System.getProperty("file.separator"));
      fn = fpfn.substring(pos + 1);
    }
  }

  /**
   * ��ȡ�ļ�·�������������ļ�����
   *
   * @return String
   */
  public String getFilePath() {
    if (fp != null) {
      return FileNameParser.getPath(fp);
    }
    else {
      return null;
    }
  }

  /**
   * ��ȡ�ļ���
   *
   * @return String
   */
  public String getFileName() {
    return fn;
  }

  /**
   * ��ȡ�ļ�·��ȫ���������ļ�����
   *
   * @return String
   */
  public String getFilePathAndName() {
    return fp;
  }

  public static void main(String args[]) {
    FileItem fi = new FileItem();
    fi.setFilePathAndName("c:\\opt\\ext\\1112.333.txt");
    System.out.println("fileN =" + fi.getFileName());
    System.out.println("fileP =" + fi.getFilePath());
    System.out.println("filePN=" + fi.getFilePathAndName());
  }

}
