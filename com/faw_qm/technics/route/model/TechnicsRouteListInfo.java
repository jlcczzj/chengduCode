/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * SS1 增加被用于产品最新BsoID liuyang 2014-6-6
 */


package com.faw_qm.technics.route.model;

import java.util.Vector;

import com.faw_qm.enterprise.model.RevisionControlledInfo;
import com.faw_qm.technics.route.exception.TechnicsRouteException;

//工艺路线表的编号，唯一标识。注意在sql中对对应的master建立唯一索引。服务在处理保存时要封装oracle异常。

/**
 * 工艺路线表值对象
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw-qm </p>
 * @author 管春元
 * @version 1.0
 */
public class TechnicsRouteListInfo
    extends RevisionControlledInfo
    implements TechnicsRouteListIfc {
  private static final long serialVersionUID = 1L;

  /**
   * 获得业务对象名。
   */
  public String getBsoName() {
    return "TechnicsRouteList";
  }

  /**
   * 工艺路线表的名称，需要进行非空检查.
   */
  private String name;

  /**
   * 工艺路线表的编号，唯一标识。进行非空检查。
   */
  private String number;

  /**
   * 路线表说明
   */
  private String description;

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   */
  private String level;

  // 工艺路线表的状态
  private String state;

  /**
   * 二级路线表的单位ID.
   */
  private String department;

  /**
   * 二级路线表的单位名称.
   */
  private String departmentName;

  /**
   * 获得工艺路线表对应的产品ID.
   */
  private String productMasterID;
  //CCBegin SS1
  private String productID;
  //CCEnd SS1
  private Vector partIndex;
  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  private String defaultDescription;
  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  
  /**
   * 构造函数
   */
  public TechnicsRouteListInfo() {
    TechnicsRouteListMasterInfo masterInfo = new TechnicsRouteListMasterInfo();
    this.setMaster(masterInfo);
  }

  /**
   * 工艺路线表的编号，唯一标识。
   * @return java.lang.String
   */
  public String getRouteListNumber() {
    return number;
  }

  /**
   * 工艺路线表的编号，唯一标识
   * @param number - 字符长度不超过30
   */
  public void setRouteListNumber(String number) throws TechnicsRouteException {
    if (number == null || number.trim().length() == 0) {
      throw new TechnicsRouteException("2", null);
    }
    ( (TechnicsRouteListMasterInfo) getMaster()).setRouteListNumber(number);
    this.number = number;
  }

  /**
   * 得到领部件的顺序
   */
  public Vector getPartIndex() {
    return this.partIndex;
  }

  /**
   * 设置零部件的殊勋
   */
  public void setPartIndex(Vector partIndex) {
    this.partIndex = partIndex;
  }

  /**
   * 工艺路线表的名称，需要进行非空检查
   * @return java.lang.String
   */
  public String getRouteListName() {
    return name;
  }

  /**
   * 工艺路线表的名称，需要进行非空检查
   * @param name
   */
  public void setRouteListName(String name) throws TechnicsRouteException {
    if (name == null || name.trim().length() == 0) {
      throw new TechnicsRouteException("1", null);
    }
    ( (TechnicsRouteListMasterInfo) getMaster()).setRouteListName(name);
    this.name = name;
  }

  /**
   * 路线表说明
   * @return java.lang.String
   */
  public String getRouteListDescription() {
    return description;
  }

  /**
   * 路线表说明
   * @param description
   */
  public void setRouteListDescription(String description) {
    this.description = description;
  }

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   * @return java.lang.String
   */
  public String getRouteListLevel() {
    return level;
  }

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   * @param level
   */
  public void setRouteListLevel(String level) {
    this.level = level;
  }

  /**
   *  工艺路线表的状态
   * @return java.lang.String
   */
  public String getRouteListState() {
    return state;
  }

  /**
   * 设置工艺路线表的状态
   * @param state
   */
  public void setRouteListState(String state) {
    this.state = state;
  }

  /**
   * 二级路线表的单位ID.
   * @return java.lang.String
   */
  public String getRouteListDepartment() {
    return department;
  }

  /**
   * 二级路线表的单位ID.
   * @param department - 二级路线表的单位ID.
   */
  public void setRouteListDepartment(String department) {
    this.department = department;
  }

  /**
   * 二级路线表的单位名称.
   * @return java.lang.String
   */
  public String getDepartmentName() {
    return departmentName;
  }

  /**
   * 二级路线表的单位名称.此方法有EJB调用。
   * @param department - 二级路线表的单位名称.
   */
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  /**
   * 获得工艺路线表对应的产品ID.
   * @return java.lang.String
   */
  public String getProductMasterID() {
    return productMasterID;
  }

  /**
   * 获得工艺路线表对应的产品ID.
   * @param productMasterID - 零部件ID.
   */
  public void setProductMasterID(String productMasterID) {
    ( (TechnicsRouteListMasterInfo) getMaster()).setProductMasterID(
        productMasterID);
    this.productMasterID = productMasterID;
  }
  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  /**
   * @roseuid 402C182802DA
   * @J2EE_METHOD  --  getProductMasterID
   * 默认有效性说明.
   */
  public java.lang.String getDefaultDescreption()
  {
    return this.defaultDescription;
  }

  /**
   * @roseuid 402C185602CC
   * @J2EE_METHOD  --  setProductMasterID
   * 默认有效性说明.
   */
  public void setDefaultDescreption(String des){
    this.defaultDescription = des;
  }
  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  //CCBegin SS1
  /**
   * 被用于产品bsoID
   */
  public String getProductID()
  {
	  return this.productID;
  }
  public void setProductID(String productID){
	  this.productID=productID;
  }
  //CCEnd SS1
}
