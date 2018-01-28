package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: ��������������װ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: FAW_QM</p>
 * @author ShangHaiFeng
 * @version 1.0
 */

import java.util.Vector;

public class ResultReport {

  public int success = 0;

  public int failure = 0;

  public Vector errMsg;

  public Vector successMsg;

  public ResultReport() {
    success = 0;
    failure = 0;
    errMsg = new Vector();
    successMsg = new Vector();
  }

  /**
   * �ɹ�����1
   */
  public void successOne() {
    success++;
  }

  /**
   * ʧ������1
   */
  public void failureOne() {
    failure++;
  }

  /**
   * c
   *
   * @return ����ɹ���
   */
  public int success() {
    return success;
  }

  /**
   * ��ȡ����ʧ����
   *
   * @return ����ʧ����
   */
  public int failure() {
    return failure;
  }

  /**
   * ����ʧ����
   *
   * @param count
   *            int
   */
  public void setFailureCount(int count) {
    failure = count;
  }

  /**
   * ���óɹ���
   *
   * @param count
   *            int
   */
  public void setSuccessCount(int count) {
    success = count;
  }

  /**
   * ��ȡʧ��ԭ��(һ��Vector���ϣ��ۼ�String��)
   *
   * @return
   */
  public Vector errMsg() {
    return errMsg;
  }

  /**
   * ��ȡ�ɹ���Ϣ(һ��Vector���ϣ��ۼ�String��)
   *
   * @return
   */
  public Vector successMsg() {
    return successMsg;
  }

  /**
   * ���ʧ����Ϣ
   *
   * @param msg
   *            ʧ����Ϣ
   */
  public void addErroMsg(String msg) {
    errMsg.add(msg);
  }

  /**
   * ��ӳɹ���Ϣ
   *
   * @param msg
   *            �ɹ���Ϣ
   */
  public void addSuccessMsg(String msg) {
    successMsg.add(msg);
  }

  /**
   * ��ʾ������Ϣ
   *
   * @return ������Ϣ
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ResultReport: success=");
    sb.append(success);
    sb.append(", failure=");
    sb.append(failure);
    sb.append(", msg.size()=");
    sb.append(errMsg.size());
    return sb.toString();
  }

  public static void main(String[] args) {
    ResultReport tt = new ResultReport();
  }

}
