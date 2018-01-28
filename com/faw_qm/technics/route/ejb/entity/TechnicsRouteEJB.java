/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 *  CR1 吕航  具体对象里不保存编号名称 参见TD2525
 */


package com.faw_qm.technics.route.ejb.entity;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.route.model.TechnicsRouteInfo;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 工艺路线
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public abstract class TechnicsRouteEJB
    extends BsoReferenceEJB {

  public TechnicsRouteEJB() {

  }

  /**
   * 路线说明
   */
  public abstract java.lang.String getRouteDescription();

  /**
   * 路线说明。
   */
  public abstract void setRouteDescription(String description);

  //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
  public abstract String getModefyIdenty();

  public abstract void setModefyIdenty(String identy);

  public abstract boolean getIsAdopt();

 public abstract void setIsAdopt(boolean adopt);
 /**
    * 默认有效性说明.
    */
   public abstract java.lang.String getDefaultDescreption();

   /**
    * 默认有效性说明.
    */
   public abstract void setDefaultDescreption(String des);

   //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线
  
  /**
   * 获得业务对象名。
   */
  public String getBsoName() {
    return "TechnicsRoute";
  }

  /**
   * 获得值对象。
   */
  public BaseValueIfc getValueInfo() throws QMException {
    TechnicsRouteInfo info = new TechnicsRouteInfo();
    setValueInfo(info);
    return info;
  }

  /**
   * 对新建值对象进行设置。
   */
  public void setValueInfo(BaseValueIfc info) throws QMException {
    super.setValueInfo(info);
    TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) info;
    routeInfo.setRouteDescription(this.getRouteDescription());
    //routeInfo.setRouteListID(this.getRouteListID());
    //routeInfo.setPartMasterID(this.getPartMasterID());
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
    if(this.getModefyIdenty() != null)
    {
       PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
       CodingIfc codInfo = (CodingIfc) pservice.refreshInfo(this.getModefyIdenty());
       routeInfo.setModefyIdenty(codInfo);
    }
     routeInfo.setIsAdopt(this.getIsAdopt());
     routeInfo.setDefaultDescreption(this.getDefaultDescreption());
     //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线
  }

  /**
   * 根据值对象进行持久化。
   */
  public void createByValueInfo(BaseValueIfc info) throws CreateException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter TechnicsRouteEJB's updateByValueInfo");
    }
    super.createByValueInfo(info);
    TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) info;
    this.setRouteDescription(routeInfo.getRouteDescription());
    //this.setRouteListID(routeInfo.getRouteListID());
    //this.setPartMasterID(routeInfo.getPartMasterID());
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
    this.setIsAdopt(routeInfo.getIsAdopt());
    this.setDefaultDescreption(routeInfo.getDefaultDescreption());
    CodingIfc code = routeInfo.getModefyIdenty();
    if (code != null)
    {
        setModefyIdenty(code.getBsoID());

    }
           //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线
  }

  /**
   * 根据值对象进行更新。
   */
  public void updateByValueInfo(BaseValueIfc info) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter TechnicsRouteEJB's updateByValueInfo");
    }
    super.updateByValueInfo(info);
    TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) info;
    this.setRouteDescription(routeInfo.getRouteDescription());
    //this.setRouteListID(routeInfo.getRouteListID());
    //this.setPartMasterID(routeInfo.getPartMasterID());
    //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
    this.setIsAdopt(routeInfo.getIsAdopt());
    this.setDefaultDescreption(routeInfo.getDefaultDescreption());
    CodingIfc code = routeInfo.getModefyIdenty();
    if (code != null)
    {
        setModefyIdenty(code.getBsoID());

    }

                  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线
}

}
