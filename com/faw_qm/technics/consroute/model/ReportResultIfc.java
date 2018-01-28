package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.*;
import java.util.Vector;
/**
 * <p>Title:报表结果 </p>
 * <p>Description:专为解放公司设计 20061101 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */


public interface ReportResultIfc extends BaseValueIfc{

  /**
   * 设置路线表的编号
   * @param num String
   */
  public void setRouteListNum(String num);

  /**
   * 获得路线表编号
   * @return String
   */
  public String getRouteListNum();

  /**
   * 设置报表内容（Blob字段）
   * @param v Vector
   */
  public void setContent(Vector v);

  /**
   * 获得报表内容（Blob字段）
   * @return Vector
   */
  public Vector getContent();

}
