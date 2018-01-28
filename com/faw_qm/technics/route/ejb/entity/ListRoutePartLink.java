/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * SS1 增加彩色件标识 liuyang 2014-6-6
 */

package com.faw_qm.technics.route.ejb.entity;

import com.faw_qm.framework.service.BinaryLink;

/**
 * 工艺路线对应的产品结构可以选择。产品的子零件
 * <p>Title: </p>
 * <p>Description: leftID = routeListID, rightID = partMasterID.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw_qm </p>
 * @author 管春元
 * @version 1.0 2005/07/07
 */
public interface ListRoutePartLink
    extends BinaryLink {

  /**
   * 设置工艺路线ID。
   * @roseuid 4039BA590094
   */
  public void setRouteID(String routeID);

  /**
   * 得到路线的id
   * @return String
   */
  public String getRouteID();

  /**
   * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状
   * 态作为持久化属性。
   * 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。
   * 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
   * @return boolean
   */
  public String getAdoptStatus();

  /**
   * 设置零件的工艺路线是否被采用。
   * @param status 采用、取消。
   */
  public void setAdoptStatus(String status);

  //st skybird 2005.3.1 零部件的父件编号
  // gcy add in 2005.4.26 for reinforce requirement  start

  /**
   * 得到父件的编号
   * @return String 父件的编号
   */
  public String getParentPartNum();

  /**
   * 设置父件的编号
   * @param parentPartNum String
   */
  public void setParentPartNum(String parentPartNum);

  /**
   * 得到父件名
   * @return String 父件的名称
   */
  public String getParentPartName();

  /**
   * 设置父件的名称
   * @param parentPartNum String
   */
  public void setParentPartName(String parentPartNum);

  /**
   * 得到父件的id
   * @return String
   */
  public String getParentPartID();

  /**
   * 设置父件的id
   * @param parentPartNum String 父件的id
   */
  public void setParentPartID(String parentPartNum);

  /**
   * 得到当前零部件在父件中在产品中使用数量（不一定是直接上级）
   * @return int  零部件在父件中使用数量（不一定是直接上级）
   */
  public int getCount();

  /**
   * 设置零部件在父件中在产品中使用的数量（不一定是直接上级）
   * @param count int
   */
  public void setCount(int count);

  // gcy add in 2005.4.26 for reinforce requirement  end


  /**
   * 此标识用来判断与路线表关联的路线是否显示（在物料清单中）。是否处理采用、取消。
   * @return int =0 表示是从上一版本继承下来的；
   * int=1，从本版本生成的。涉及到路线是否重新生成；
   * int=2，此版本删除的，当路线表检出时，不复制此关联。
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
  public abstract String getPartBranchID();

  /**
   * 设置关联中零件的小版本
   * @param listIterationID String
   */
  public abstract void setPartBranchID(String partBranchID);
  
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id

  /**
   * 初始采用版本。A, B.大版本号。
   * @return String
   */
  public abstract String getInitialUsed();

  /**
   * 设置采用版本。A, B.大版本号
   * @param version String
   */
  public abstract void setInitialUsed(String version);
//CCBegin SS1
  /**
   * 颜色标识
   */
  public String getColorFlag();
  public void setColorFlag(String colorFlag);
  //CCEnd SS1
}
