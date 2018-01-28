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


package com.faw_qm.technics.route.ejb.entity;

import java.util.Vector;

import com.faw_qm.enterprise.ejb.entity.RevisionControlled;
//CCBegin SS2
import com.faw_qm.content.ejb.service.ContentHolder;
//CCEnd SS2
/**
 * 一个零部件可以有多个工艺路线表。
 * 一个工艺路线表有多个工艺路线。
 * TechnicsRouteListIfc的唯一标识由标识代理来决定。
 */


/**
 * <p>Title: </p>
 * <p>Description: 工艺路线服务端类</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @mener skybird
 * @version 1.0
 */
public interface TechnicsRouteList
    //CCBegin SS2
    //extends RevisionControlled {
    extends RevisionControlled, ContentHolder {
    //CCEnd SS2
  /**
   * 工艺路线表的编号，唯一标识。需要进行非空检查。
   * 工艺路线表有版本，编号的唯一性在对应的Master中保证。
   */
  public java.lang.String getRouteListNumber();

  /**
   * 工艺路线表的编号，唯一标识。工艺路线表有版本，编号的唯一性在对应的Master中保证。
   */
  public void setRouteListNumber(String number);

  /**
   * 得到领部件的顺序
   */
  public Vector getPartIndex();

  /**
   * 设置零部件的殊勋
   */
  public void setPartIndex(Vector partIndex);

  /**
   * 工艺路线表的名称，需要进行非空检查。
   */
  public java.lang.String getRouteListName();

  /**
   * 工艺路线表的名称，需要进行非空检查
   */
  public void setRouteListName(String name);

  /**
   * 路线表说明
   */
  public java.lang.String getRouteListDescription();

  /**
   * 路线表说明
   */
  public void setRouteListDescription(String description);

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级).
   */
  public java.lang.String getRouteListLevel();

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   */
  public void setRouteListLevel(String level);

  /**
   * 工艺路线表状态
   * @return
   */
  public java.lang.String getRouteListState();

  /**
   * 设置工艺路线的状态
   */
  public void setRouteListState(String state);

  /**
   * 二级路线表的单位标识:codeID
   */
  public java.lang.String getRouteListDepartment();

  /**
   * 二级路线表的单位标识:codeID
   */
  public void setRouteListDepartment(String department);

  /**
   * 获得工艺路线表对应的产品ID.
   */
  public java.lang.String getProductMasterID();

  /**
   * 获得工艺路线表对应的产品ID.
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
