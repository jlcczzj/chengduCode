package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: 操作结果报告类封装</p>
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
   * 成功数加1
   */
  public void successOne() {
    success++;
  }

  /**
   * 失败数加1
   */
  public void failureOne() {
    failure++;
  }

  /**
   * c
   *
   * @return 报告成功数
   */
  public int success() {
    return success;
  }

  /**
   * 获取报告失败数
   *
   * @return 报告失败数
   */
  public int failure() {
    return failure;
  }

  /**
   * 设置失败数
   *
   * @param count
   *            int
   */
  public void setFailureCount(int count) {
    failure = count;
  }

  /**
   * 设置成功数
   *
   * @param count
   *            int
   */
  public void setSuccessCount(int count) {
    success = count;
  }

  /**
   * 获取失败原因(一个Vector集合，聚集String类)
   *
   * @return
   */
  public Vector errMsg() {
    return errMsg;
  }

  /**
   * 获取成功信息(一个Vector集合，聚集String类)
   *
   * @return
   */
  public Vector successMsg() {
    return successMsg;
  }

  /**
   * 添加失败信息
   *
   * @param msg
   *            失败信息
   */
  public void addErroMsg(String msg) {
    errMsg.add(msg);
  }

  /**
   * 添加成功信息
   *
   * @param msg
   *            成功信息
   */
  public void addSuccessMsg(String msg) {
    successMsg.add(msg);
  }

  /**
   * 显示报告信息
   *
   * @return 报告信息
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
