/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2010/09/09
 */


package com.faw_qm.technics.consroute.ejb.entity;

 
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: </p>
 * <p>Description: 目标成本服务端类</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @mener skybird
 * @version 1.0
 */
 
   
  



public interface Supplier
    extends  BsoReference{
  /**
		*	得到供应商名称
   */
  public java.lang.String getCodename();

  /**
   * 设置供应商名称
   */
  public void setCodename(String codename);

  /**
   * 得到供应商代码
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
  public String getJName();

  /**
   * 简称
   */
  public void setJName(String jname);

  /**
   * 联系电话
   */
  public String getTelephone();

  /**
   * 联系电话
   */
  public void setTelephone(String telephone);


}
