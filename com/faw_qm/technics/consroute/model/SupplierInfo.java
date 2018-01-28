/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.model;
import com.faw_qm.framework.service.BaseValueInfo;


/**
 * <p>Title:定额明细单 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘 明
 * @version 1.0
 */

public class SupplierInfo extends BaseValueInfo implements
        SupplierIfc
{
	 static final long serialVersionUID = 1L;
	  private String codename;
	  private String code;
	  private String people;
	  private String jName;
	  private String telephone;
	  
    /**
     * 构造值对象
     */
    public SupplierInfo()
    {
        super();


    }

  
    /**
     * getBsoName
     * @return String
     */
    public String getBsoName()
    {
        return "Supplier";
    }


    /**
		*	得到供应商名称
   */
  public java.lang.String getCodename(){
  	return this.codename;
  }
  

  /**
   * 设置供应商名称
   */
  public void setCodename(String codeName){
 		 this.codename = codeName;
  }

  /**
   * 得到供应商代码
   */
  public String getCode(){
  	return this.code;
  }

  /**
   * 设置供应商代码
   */
  public void setCode(String code){
  	this.code = code;
  }

  /**
   * 联系人
   */
  public String getPeople(){
  	return this.people;
  }

  /**
   * 联系人
   */
  public void setPeople(String people){
  		this.people = people;
  }

  /**
   * 简称
   */
  public java.lang.String getJName(){
  	return this.jName;
  }

  /**
   * 简称
   */
  public void setJName(String jname){
  	this.jName = jname;
  }

  /**
   * 联系电话
   */
  public String getTelephone(){
  	return this.telephone;
  }

  /**
   * 联系电话
   */
  public void setTelephone(String telephone){
  	this.telephone = telephone;
  }



}
