/**
 * 生成程序 ListRoutePartLinkInfo.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 增加彩色件标识 liuyang 2014-6-6
 * SS2 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
 */
package com.faw_qm.technics.route.model;

import com.faw_qm.framework.service.BinaryLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.route.util.RouteAdoptedType;

/**
 * <p>Title:ListRoutePartLinkInfo.java</p>
 * <p>Description:路线零部件联系值对象 </p>
 * <p>Package:com.faw_qm.technics.route.model</p>
 * <p>ProjectName:CAPP</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public class ListRoutePartLinkInfo
    extends BinaryLinkInfo
    implements ListRoutePartLinkIfc {

  /**
   * 零部件主信息值对象
   */
  private QMPartMasterIfc partMasterInfo;

  /**
   * 父件值对象
   */
  private QMPartIfc parentPartInfo;

  /**
   * 路线id
   */
  private String routeID;

  /**
   * 修改状态
   */
  private int alterStatus;

  /**
   * 路线状态
   */
  private String status;

  //st skybird 2005.3.1

  /**
   * 父件编号
   */
  private String parentPartNum;

  /**
   * 父件名称
   */
  private String parentPartName;

  /**
   * 父件id
   */
  private String parentPartID;

  /**
   * 零部件在父件在产品中使用的数量
   */
  private int count;

  /**
   * 初始路线表大版本
   */
  private String vesion;

  /**
   * 路线表主信息id
   */
  private String listMasterID;

  /**
   * 路线表的具体版本
   */
  private String listIterationID;
  
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  private String PartBranchID;
  
  private QMPartIfc partBranchInfo;
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  
  private static final long serialVersionUID = 1L;
  //CCBegin SS1
  private String colorFlag;
  //CCEnd SS1
//CCBegin SS2
  private String specialFlag;
  //CCEnd SS2
  /**
   * 构造函数
   *
   */
  protected ListRoutePartLinkInfo() {
  }

  //不支持API.
  public static ListRoutePartLinkInfo newListRoutePartLinkInfo() {
    ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
    return listLinkInfo;
  }

  /**
   * 客户端调用方法。生成一个新的值对象
   * @param routeListInfo TechnicsRouteListIfc
   * @param partMasterID String
   * @return ListRoutePartLinkInfo
   */
  public static ListRoutePartLinkInfo newListRoutePartLinkInfo(
      TechnicsRouteListIfc routeListInfo,
      String partMasterID) {
    ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
    listLinkInfo.setPartMasterID(partMasterID);
    listLinkInfo.setRouteListID(routeListInfo.getBsoID());
    listLinkInfo.setInitialUsed(routeListInfo.getVersionID());
    //路线未生成，初始状态为继承。
    listLinkInfo.setAlterStatus(TechnicsRouteServiceEJB.INHERIT);
    listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
    listLinkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
    listLinkInfo.setRouteListMasterID(routeListInfo.getMaster().getBsoID());
    return listLinkInfo;
  }
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id 
  public static ListRoutePartLinkInfo newListRoutePartLinkInfo(
	      TechnicsRouteListIfc routeListInfo,
	      String partMasterID,String partid) {
	    ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
	    listLinkInfo.setPartMasterID(partMasterID);
	    listLinkInfo.setRouteListID(routeListInfo.getBsoID());
	    listLinkInfo.setInitialUsed(routeListInfo.getVersionID());
	    //路线未生成，初始状态为继承。
	    listLinkInfo.setAlterStatus(TechnicsRouteServiceEJB.INHERIT);
	    listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
	    listLinkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
	    listLinkInfo.setRouteListMasterID(routeListInfo.getMaster().getBsoID());
//	  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	    listLinkInfo.setPartBranchID(partid);
//	  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id

	    return listLinkInfo;
	  }
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  /**
   * getBsoName
   * @return String
   */
  public String getBsoName() {
    return "ListRoutePartLink";
  }

  /**
   * 得到路线id
   * @return String
   */
  public String getRouteID() {
    return this.routeID;
  }

  /**
   * 设置路线id
   */
  public void setRouteID(String routeID) {
    this.routeID = routeID;
  }

  /**
   * 得到零部件主信息id
   * @return String
   */
  public String getPartMasterID() {
    return this.getRightBsoID();
  }

  /**
   *设置零部件主信息id
   */
  public void setPartMasterID(String partMasterID) {
    this.setRightBsoID(partMasterID);
  }

  /**
   * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状 态作为持久化属性。
   * 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。
   * 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
   */
  public String getAdoptStatus() {
    return this.status;
  }

  /**
   * 设置零件的工艺路线是否被采用。
   * @param status
   *            采用、取消。
   */
  public void setAdoptStatus(String status) {
    this.status = status;
  }

  //st skybird 2005.3.1
  // gcy add in 2005.4.26 for reinforce requirement  start

  /**
   * 设置零部件表的零部件的父件编号
   * @param status
   *            采用、取消。
   */
  public String getParentPartNum() {
    return this.parentPartNum;
  }

  /**
   * 设置零部件的父件编号
   * @param parentPartNum String
   */
  public void setParentPartNum(String parentPartNum) {
    this.parentPartNum = parentPartNum;
  }

  /**
   * 得到父件名
   * @return String
   */
  public String getParentPartName() {
    return this.parentPartName;
  }

  /**
   * 设置父件名
   * @param parentPartNum String
   */
  public void setParentPartName(String parentPartNum) {
    this.parentPartName = parentPartNum;
  }

  /**
   * 得到父件的id
   * @return String
   */
  public String getParentPartID() {
    return this.parentPartID;
  }

  /**
   * 设置父件的id
   * @param parentPartNum String
   */
  public void setParentPartID(String parentPartNum) {
    this.parentPartID = parentPartNum;
  }

  /**
   * 得到父件值对象
   * @return String
   */
  public QMPartIfc getParentPart() {
    return this.parentPartInfo;
  }

  /**
   * 设置父件值对象
   * @param part QMPartIfc
   */
  public void setParentPart(QMPartIfc part) {
    this.parentPartInfo = part;
  }

  /**
   * 得到当前零部件在父件中使用数量（不一定是直接上级）
   * @return String
   */
  public int getCount() {
    return this.count;
  }

  /**
   * 设置当前零部件在父件中使用数量（不一定是直接上级）
   * @param count int
   */
  public void setCount(int count) {
    this.count = count;
  }

// gcy add in 2005.4.26 for reinforce requirement  end


  //ed

  /**
   * 初始采用版本。A, B.大版本号。
   * @return String
   */
  public String getInitialUsed() {
    return this.vesion;
  }

  /**
   * 设置初始采用版本。A, B.大版本号。
   * @param vesion String
   */
  public void setInitialUsed(String version) {
    this.vesion = version;
  }

  /**
   * 此标识用来判断与路线表关联的路线是否显示（在物料清单中）。是否处理采用、取消。
   * @return int =0
   *         表示是从上一版本继承下来的；int=1，从本版本生成的。涉及到路线是否重新生成；int=2，此版本删除的，当路线表检出时，不复制此关联。
   */
  public int getAlterStatus() {
    return this.alterStatus;
  }

  /**
   * 设置修改状态
   * @param alterStatus int int =0 表示是从上一版本继承下来的；
   * int=1，从本版本生成的。涉及到路线是否重新生成；
   * int=2，此版本删除的，当路线表检出时，不复制此关联。
   */
  public void setAlterStatus(int alterStatus) {
    this.alterStatus = alterStatus;
  }

  /**
   * 得到工艺路线表ID。
   * @return String
   */
  public String getRouteListID() {
    return this.getLeftBsoID();
  }

  /**
   * 设置工艺路线表ID。
   */
  public void setRouteListID(String routeListID) {
    this.setLeftBsoID(routeListID);
  }

  /**
   * 得到路线表的id
   * @return String 路线表的id
   */
  public String getRouteListMasterID() {
    return this.listMasterID;
  }

  /**
   * 设置路线表的id
   * @param listMasterID String 路线表的id
   */
  public void setRouteListMasterID(String listMasterID) {
    this.listMasterID = listMasterID;
  }

  /**
   * 得到关联中路线表的小版本
   * @return String
   */
  public String getRouteListIterationID() {
    return this.listIterationID;
  }

  /**
   * 设置关联中路线表的小版本
   * @param listIterationID String
   */
  public void setRouteListIterationID(String listIterationID) {
    this.listIterationID = listIterationID;
  }
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  /**
   * 得到关联中零件的小版本
   * @return String
   */
  public String getPartBranchID() {
    return this.PartBranchID;
  }

  /**
   * 设置关联中零件的小版本
   * @param listIterationID String
   */
  public void setPartBranchID(String partBranchID) {
    this.PartBranchID = partBranchID;
  }
  
  /**
   * 得到关联中零件的小版本
   * @return String
   */
  public QMPartIfc getPartBranchInfo() {
    return this.partBranchInfo;
  }

  /**
   * 设置关联中零件的小版本
   * @param listIterationID String
   */
  public void setPartBranchInfo(QMPartIfc partBranchInfo) {
    this.partBranchInfo = partBranchInfo;
  }
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  /**
   * 在客户端调用。
   * @return QMPartMasterIfc
   */
  public QMPartMasterIfc getPartMasterInfo() {
    return this.partMasterInfo;
  }

  /**
   * partMasterInfo在EJB初始化。
   * @param partMasterInfo QMPartMasterIfc
   */
  public void setPartMasterInfo(QMPartMasterIfc partMasterInfo) {
    this.partMasterInfo = partMasterInfo;
  }

//CCBegin SS1
public String getColorFlag() {
	
	return this.colorFlag;
}


public void setColorFlag(String colorFlag) {
	this.colorFlag=colorFlag;
}
//CCEnd SS1

//CCBegin SS2
public String getSpecialFlag() {
	
	return this.specialFlag;
}


public void setSpecialFlag(String specialFlag) {
	this.specialFlag=specialFlag;
}
//CCEnd SS2
}