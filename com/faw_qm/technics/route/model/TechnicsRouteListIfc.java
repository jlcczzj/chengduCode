/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * SS1 增加被用于产品最新BsoID liuyang 2014-6-6
 * SS2 添加附件。 liunan 2015-6-18
 */


package com.faw_qm.technics.route.model;

import java.util.Vector;

import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
//CCBegin SS2
import com.faw_qm.content.model.ContentHolderIfc;
//CCEnd SS2
/**
 * 因其为最终使用类且不被其它包使用，为建立Map. 其他类同之。
 * 具体属性见ejb.
 */

/**
 * 工艺路线表值对象接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteListIfc
    //CCBegin SS2
    //extends RevisionControlledIfc {
    extends RevisionControlledIfc, ContentHolderIfc {
    //CCEnd SS2
  /**
   * 工艺路线表的编号，唯一标识。
   * @return java.lang.String
   */
  public String getRouteListNumber();

  /**
   * 工艺路线表的编号，唯一标识
   * @param number - 字符长度不超过30
   */
  public void setRouteListNumber(String number) throws TechnicsRouteException;

  /**
   * 得到领部件的顺序
   */
  public Vector getPartIndex();

  /**
   * 设置零部件的殊勋
   */
  public void setPartIndex(Vector partIndex);

  /**
   * 工艺路线表的名称，需要进行非空检查
   * @return java.lang.String
   */
  public String getRouteListName();

  /**
   * 工艺路线表的名称，需要进行非空检查
   * @param name
   */
  public void setRouteListName(String name) throws TechnicsRouteException;

  /**
   * 路线表说明
   * @return java.lang.String
   */
  public String getRouteListDescription();

  /**
   * 路线表说明
   * @param description
   */
  public void setRouteListDescription(String description);

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   * @return java.lang.String
   */
  public String getRouteListLevel();

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   * @param level
   */
  public void setRouteListLevel(String level);

  /**
   * 工艺路线表的状态
   * 前准、试准、艺准、临准等，在代码管理中维护
   * @return java.lang.String
   */
  public String getRouteListState();

  /**
   * 设置工艺路线表的状态
   * @param state
   */
  public void setRouteListState(String state);

  /**
   * 二级路线表的单位代码ID
   * @return java.lang.String
   */
  public String getRouteListDepartment();

  /**
   * 二级路线表的单位代码ID
   * @param department - 二级路线表的单位代码ID
   */
  public void setRouteListDepartment(String department);

  /**
   * 二级路线表的单位名称.
   * @return java.lang.String
   */
  public String getDepartmentName();

  /**
   * 二级路线表的单位名称.此方法有EJB调用。
   * @param department - 二级路线表的单位名称.
   */
  public void setDepartmentName(String departmentName);

  /**
   * 获得工艺路线表对应的产品ID.
   * @return java.lang.String
   */
  public String getProductMasterID();

  /**
   * 获得工艺路线表对应的产品ID.
   * @param productMasterID - 零部件ID.
   */
  public void setProductMasterID(String productMasterID);
  
  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  /**
   * @roseuid 402C182802DA
   * @J2EE_METHOD  --  getProductMasterID
   * 默认有效性说明.
   */
  public java.lang.String getDefaultDescreption();

  /**
   * @roseuid 402C185602CC
   * @J2EE_METHOD  --  setProductMasterID
   * 默认有效性说明.
   */
  public void setDefaultDescreption(String des);
  
  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  //CCBegin SS1
  /**
   * 被用于产品bsoID
   */
  public String getProductID();
  public void setProductID(String productID);
  //CCEnd SS1
}
