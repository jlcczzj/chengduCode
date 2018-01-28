/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */


package com.faw_qm.technics.route.model;

import com.faw_qm.codemanage.ejb.entity.Coding;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.service.BaseValueInfo;

/**
 * 工艺路线值对象
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw-qm </p>
 * @author 管春元
 * @version 1.0
 */
public class TechnicsRouteInfo
    extends BaseValueInfo
    implements TechnicsRouteIfc {
  //描述
  private String description;
  //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
  private CodingIfc modefyIdenty;
  private String defaultDescreption;
  private boolean isAdopt;
  //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线  
  private static final long serialVersionUID = 1L;

  /**
   * 构造函数
   */
  public TechnicsRouteInfo() {

  }

  /**
   * 路线说明
   * @return java.lang.String
   */
  public String getRouteDescription() {
    return this.description;
  }

  /**
   * 路线说明。
   * @param description
   */
  public void setRouteDescription(String description) {
    this.description = description;
  }

  /**
   * 得到标识
   * @return java.lang.String
   */
  public String getIdentity() {
    if (this.getBsoID() == null) {
      return this.getBsoName();
    }
    else {
      return this.getBsoID();
    }
  }
  
  //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
       public CodingIfc getModefyIdenty(){
	    return this.modefyIdenty;
	   }

	    public void setModefyIdenty(CodingIfc identy){
	      this.modefyIdenty = identy;
	    }

	    public boolean getIsAdopt(){
	      return this.isAdopt;
	    }

	   public void setIsAdopt(boolean adopt){
	     this.isAdopt = adopt;
	   }
	   /**
	      * 默认有效性说明.
	      */
	     public java.lang.String getDefaultDescreption(){
	       return this.defaultDescreption;
	     }

	     /**
	      * 默认有效性说明.
	      */
	     public void setDefaultDescreption(String des){
	       this.defaultDescreption = des;
	     }
	     //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线  

  /**
   * 得到业务类名
   * @return String
   */
  public String getBsoName() {
    return "TechnicsRoute";
  }
}
