/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 *  CR1 ����  ��������ﲻ���������� �μ�TD2525
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
 * ����·��
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public abstract class TechnicsRouteEJB
    extends BsoReferenceEJB {

  public TechnicsRouteEJB() {

  }

  /**
   * ·��˵��
   */
  public abstract java.lang.String getRouteDescription();

  /**
   * ·��˵����
   */
  public abstract void setRouteDescription(String description);

  //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
  public abstract String getModefyIdenty();

  public abstract void setModefyIdenty(String identy);

  public abstract boolean getIsAdopt();

 public abstract void setIsAdopt(boolean adopt);
 /**
    * Ĭ����Ч��˵��.
    */
   public abstract java.lang.String getDefaultDescreption();

   /**
    * Ĭ����Ч��˵��.
    */
   public abstract void setDefaultDescreption(String des);

   //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��
  
  /**
   * ���ҵ���������
   */
  public String getBsoName() {
    return "TechnicsRoute";
  }

  /**
   * ���ֵ����
   */
  public BaseValueIfc getValueInfo() throws QMException {
    TechnicsRouteInfo info = new TechnicsRouteInfo();
    setValueInfo(info);
    return info;
  }

  /**
   * ���½�ֵ����������á�
   */
  public void setValueInfo(BaseValueIfc info) throws QMException {
    super.setValueInfo(info);
    TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) info;
    routeInfo.setRouteDescription(this.getRouteDescription());
    //routeInfo.setRouteListID(this.getRouteListID());
    //routeInfo.setPartMasterID(this.getPartMasterID());
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
    if(this.getModefyIdenty() != null)
    {
       PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
       CodingIfc codInfo = (CodingIfc) pservice.refreshInfo(this.getModefyIdenty());
       routeInfo.setModefyIdenty(codInfo);
    }
     routeInfo.setIsAdopt(this.getIsAdopt());
     routeInfo.setDefaultDescreption(this.getDefaultDescreption());
     //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��
  }

  /**
   * ����ֵ������г־û���
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
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
    this.setIsAdopt(routeInfo.getIsAdopt());
    this.setDefaultDescreption(routeInfo.getDefaultDescreption());
    CodingIfc code = routeInfo.getModefyIdenty();
    if (code != null)
    {
        setModefyIdenty(code.getBsoID());

    }
           //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��
  }

  /**
   * ����ֵ������и��¡�
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
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
    this.setIsAdopt(routeInfo.getIsAdopt());
    this.setDefaultDescreption(routeInfo.getDefaultDescreption());
    CodingIfc code = routeInfo.getModefyIdenty();
    if (code != null)
    {
        setModefyIdenty(code.getBsoID());

    }

                  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��
}

}
