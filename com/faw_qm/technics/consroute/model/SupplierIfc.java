/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.model;
 

import com.faw_qm.framework.service.BaseValueIfc;




/**
 * <p>Title:定额明细单 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘 明
 * @version 1.0
 */

public interface SupplierIfc extends  BaseValueIfc
{
    /**
		*	得到供应商名称 
   */
  public java.lang.String getCodename();

  /**
   * 设置供应商名称
   */
  public void setCodename(String codename);

  /**
   * 得到供应商代码s
   */
  public String getCode();

  /**
   * 设置供应商代码
   */
  public void setCode(String code);

  /**
   * 联系人
   */
  public String getPeople();

  /**
   * 联系人
   */
  public void setPeople(String people);

  /**
   * 简称
   */
  public java.lang.String getJName();

  /**
   * 简称
   */
  public void setJName(String jName);

  /**
   * 联系电话
   */
  public String getTelephone();

  /**
   * 联系电话
   */
  public void setTelephone(String telephone);


}
