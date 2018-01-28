package com.faw_qm.jfpublish.receive;

/**
 * <p>
 * Title: 文件列表封装类
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

  // 文件名
  private String fn;

  // 文件路径名及文件名
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
   * 设置文件名
   *
   * @param fileN
   *            String
   */
  public void setFileName(String fileN) {
    this.fn = fileN;
  }

  /**
   * 设置文件路径全名,包括文件名
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
   * 获取文件路径名（不包括文件名）
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
   * 获取文件名
   *
   * @return String
   */
  public String getFileName() {
    return fn;
  }

  /**
   * 获取文件路径全名（包括文件名）
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
