/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */


package com.faw_qm.technics.route.model;

import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * 工艺路线值对象接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw-qm </p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteIfc
    extends BaseValueIfc, CappIdentity {

  /**
   * 路线说明
   * @return java.lang.String
   */
  public String getRouteDescription();

  /**
   * 路线说明。
   * @param description
   */
  public void setRouteDescription(String description);
 
  //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
  public CodingIfc getModefyIdenty();

  public void setModefyIdenty(CodingIfc identy);

  public boolean getIsAdopt();

 public void setIsAdopt(boolean adopt);
 /**
    * 默认有效性说明.
    */
   public java.lang.String getDefaultDescreption();

   /**
    * 默认有效性说明.
    */
   public void setDefaultDescreption(String des);

   //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线
}
