package com.faw_qm.jfpublish.receive;

/**
 * <p>
 * Title: �����ļ���������
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

public class FileNameParser {

  private FileNameParser() {
  }

  /**
   * ��ȡ�ļ���
   *
   * @param fqfn
   *            String
   * @return String
   */
  public static String getFileTitle(String fqfn) {
    String title = "";
    int pos = fqfn.lastIndexOf(System.getProperty("file.separator"));
    title = fqfn.substring(pos + 1);
    pos = title.lastIndexOf(".");
    if (pos >= 0) {
      title = title.substring(0, title.length() - (title.length() - pos));
    }
    return title;
  }

  /**
   * ��ȡ�ļ�����չ��
   *
   * @param fqfn
   *            String
   * @return String
   */
  public static String getFileExtension(String fqfn) {
    String ext = "";
    int pos = fqfn.lastIndexOf(System.getProperty("file.separator"));
    ext = fqfn.substring(pos + 1);
    pos = ext.lastIndexOf(".");
    if (pos >= 0) {
      ext = ext.substring(pos + 1);
    }
    else {
      ext = "";
    }
    return ext;
  }

  /**
   * ��ȡ�ļ�������չ��(������·����Ϣ)
   *
   * @param fqfn
   *            String
   * @return String
   */
  public static String getFileTitleAndExtension(String fqfn) {
    int pos = fqfn.lastIndexOf(System.getProperty("file.separator"));
    return fqfn.substring(pos + 1);
  }

  /**
   * ��ȡ�ļ���·����Ϣ
   *
   * @param fqfn
   *            String
   * @return String
   */
  public static String getPath(String fqfn) {
    int pos = fqfn.lastIndexOf(System.getProperty("file.separator"));
    return fqfn.substring(0, pos + 1);
  }

  public static void main(String args[]) {
    System.out.println(System.getProperty("user.home"));
    System.out.println(getFileExtension("c:\\ddd\\22txt.txt.ddd"));
  }
}
