/**
 * 生成程序 ListRoutePartLinkIfc.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 增加彩色件标识 liuyang 2014-6-6
 */
package com.faw_qm.technics.route.model;

import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p> Title:ListRoutePartLinkIfc.java </p>
 * <p> Description:路线连接接口 leftID = routeListID, rightID = partMasterID. </p>
 * <p> Package:com.faw_qm.technics.route.model </p>
 * <p> ProjectName:CAPP </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company:一汽启明 </p>
 * @author 管春元
 * @version 1.0
 */
public interface ListRoutePartLinkIfc
    extends BinaryLinkIfc {
  /**
   * 设置零件ID。
   */
  public void setPartMasterID(String partMasterID);

  /**
   * 得到领部件的ID
   * @return String
   */
  public String getPartMasterID();

  /**
   * 设置工艺路线ID。
   */
  public void setRouteID(String routeID);

  /**
   * 得到工艺路线ID。
   * @return String
   */
  public String getRouteID();

  /**
   * 设置工艺路线表ID。
   */
  public void setRouteListID(String routeListID);

  /**
   * 得到工艺路线表ID。
   * @return String
   */
  public String getRouteListID();

  /**
   * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状 态作为持久化属性。
   * 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。
   * 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
   * @return String
   */
  public String getAdoptStatus();

  /**
   * 设置零件的工艺路线是否被采用。
   * @param status
   *            采用、取消。
   */
  public void setAdoptStatus(String status);

  //st skybird 2005.3.1 零部件表的父件编号
  // gcy add in 2005.4.26 for reinforce requirement start
  public String getParentPartNum();

  /**
   * 设置零部件表的零部件的父件编号
   * @param status
   *            采用、取消。
   */
  public void setParentPartNum(String parentPartNum);

  /**
   * 得到父件名
   * @return String
   */
  public String getParentPartName();

  /**
   * 设置父件名
   * @param parentPartNum String
   */
  public void setParentPartName(String parentPartNum);

  /**
   * 得到父件的id
   */
  public String getParentPartID();

  /**
   * 设置父件的id
   * @param parentPartNum String
   */
  public void setParentPartID(String parentPartNum);

  /**
   * 得到父件值对象
   * @return QMPartIfc
   */
  public QMPartIfc getParentPart();

  /**
   * 设置父件值对象
   * @param part QMPartIfc
   */
  public void setParentPart(QMPartIfc part);

  /**
   * 得到当前零部件在父件中使用数量（不一定是直接上级）
   * @return String
   */
  public int getCount();

  /**
   * 设置当前零部件在父件中使用数量（不一定是直接上级）
   * @param count int
   */
  public void setCount(int count);

  // gcy add in 2005.4.26 for reinforce requirement end

  /**
   * 此标识用来判断与路线表关联的路线是否显示（在物料清单中）。是否处理采用、取消。
   * @return int =0
   *         表示是从上一版本继承下来的；int=1，从本版本生成的。涉及到路线是否重新生成；int=2，此版本删除的，当路线表检出时，不复制此关联。
   */
  public int getAlterStatus();

  /**
   * 设置修改状态
   * @param alterStatus int int =0 表示是从上一版本继承下来的；
   * int=1，从本版本生成的。涉及到路线是否重新生成；
   * int=2，此版本删除的，当路线表检出时，不复制此关联。
   */
  public void setAlterStatus(int alterStatus);

  /**
   * 得到路线表的id
   * @return String 路线表的id
   */
  public String getRouteListMasterID();

  /**
   * 设置路线表的id
   * @param listMasterID String 路线表的id
   */
  public void setRouteListMasterID(String listMasterID);

  /**
   * 得到关联中路线表的小版本
   * @return String
   */
  public String getRouteListIterationID();

  /**
   * 设置关联中路线表的小版本
   * @param listIterationID String
   */
  public void setRouteListIterationID(String listIterationID);
  
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  /**
   * 得到关联中零件的小版本
   * @return String
   */
  public String getPartBranchID();

  /**
   * 设置关联中零件的小版本
   * @param listIterationID String
   */
  public void setPartBranchID(String partBranchID) ;
  
  /**
   * 得到关联中零件的小版本
   * @return String
   */
  public QMPartIfc getPartBranchInfo();

  /**
   * 设置关联中零件的小版本
   * @param listIterationID String
   */
  public void setPartBranchInfo(QMPartIfc partBranchInfo) ;
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  

  /**
   * 初始采用版本。A, B.大版本号。
   * @return String
   */
  public String getInitialUsed();

  /**
   * 设置采用版本。A, B.大版本号
   * @param version String
   */
  public void setInitialUsed(String version);

  /**
   * 返回零部件主信息值对象
   * @return QMPartMasterIfc
   */
  public QMPartMasterIfc getPartMasterInfo();

  /**
   * 设置零部件主信息值对象
   * @param partMasterInfo QMPartMasterIfc
   */
  public void setPartMasterInfo(QMPartMasterIfc partMasterInfo);
//CCBegin SS1
  /**
   * 颜色标识
   */
  public String getColorFlag();
  public void setColorFlag(String colorFlag);
  //CCEnd SS1
}
