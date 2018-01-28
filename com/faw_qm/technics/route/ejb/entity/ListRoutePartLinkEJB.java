/** ���ɳ��� ListRoutePartLinkEJB 1.0 2005.3.2
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���Ӳ�ɫ����ʶ liuyang 2014-6-6
 * SS2 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 */

package com.faw_qm.technics.route.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.util.RouteAdoptedType;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ����·�߶�Ӧ�Ĳ�Ʒ�ṹ����ѡ�񡣲�Ʒ�������
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm</p>
 * @author not �ܴ�Ԫ
 * @version 1.0
 */
abstract public class ListRoutePartLinkEJB
    extends BinaryLinkEJB {

  /**
   * ���캯��
   */
  public ListRoutePartLinkEJB() {

  }

  /**
   * �õ�ҵ������
   * @return String
   */
  public String getBsoName() {
    return "ListRoutePartLink";
  }

  /**
   * ���ù���·��ID
   * @param routeID String
   */
  public abstract void setRouteID(String routeID);

  /**
   * �õ�����·��ID
   * @return String
   */
  public abstract String getRouteID();

  /**
   * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״
   * ̬��Ϊ�־û����ԡ�
   * ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ�
   * ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
   * @return boolean
   */
  public abstract String getAdoptStatus();

  /**
   * ��������Ĺ���·���Ƿ񱻲��á�
   * @param status ���á�ȡ����
   */
  public abstract void setAdoptStatus(String status);

  //st skybird 2005.3.1
  // gcy add in 2005.4.26 for reinforce requirement  start

  /**
   * �õ������ı��
   * @return String �����ı��
   */
  public abstract String getParentPartNum();

  /**
   * ���ø����ı��
   * @param parentPartNum String
   */
  public abstract void setParentPartNum(String parentPartNum);

  /**
   * �õ�������
   * @return String
   */
  public abstract String getParentPartName();

  /**
   * ���ø���������
   * @param parentPartNum String
   */
  public abstract void setParentPartName(String parentPartNum);

  /**
   * �õ�������id
   * @return String
   */
  public abstract String getParentPartID();

  /**
   * ���ø�����id
   * @param parentPartNum String ������id
   */
  public abstract void setParentPartID(String parentPartNum);

  /**
   * �õ���ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
   * @return String
   */
  public abstract int getCount();

  /**
   * �����㲿���ڸ������ڲ�Ʒ��ʹ�õ���������һ����ֱ���ϼ���
   * @param count int
   */
  public abstract void setCount(int count);

  // gcy add in 2005.4.26 for reinforce requirement  end


  /**
   * �˱�ʶ�����ж���·�߱������·���Ƿ���ʾ���������嵥�У����Ƿ�����á�ȡ����
   * @return alterStatus=0��ʾ�Ǵ���һ�汾�̳������ġ�����alterStatus=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɡ�
   */
  public abstract int getAlterStatus();

  /**
   * �����޸�״̬
   * @param alterStatus int int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�
   * int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�
   * int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
   */
  public abstract void setAlterStatus(int alterStatus);

  /**
   * ��ʼ���ð汾��A, B.���汾�š�
   * @return int
   */
  public abstract String getInitialUsed();

  /**
   * ���ò��ð汾��A, B.��汾��
   * @param version String
   */
  public abstract void setInitialUsed(String version);

  /**
   * �õ�·�߱��id
   * @return String ·�߱��id
   */
  public abstract String getRouteListMasterID();

  /**
   * ����·�߱��id
   * @param listMasterID String ·�߱��id
   */
  public abstract void setRouteListMasterID(String listMasterID);

  /**
   * �õ�������·�߱��С�汾
   * @return String
   */
  public abstract String getRouteListIterationID();

  /**
   * ���ù�����·�߱��С�汾
   * @param listIterationID String
   */
  public abstract void setRouteListIterationID(String listIterationID);
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  /**
   * �õ������������С�汾
   * @return String
   */
  public abstract String getPartBranchID();

  /**
   * ���ù����������С�汾
   * @param listIterationID String
   */
  public abstract void setPartBranchID(String partBranchID);
//CCBegin SS1
  /**
   * ��ɫ��ʶ 
   */
  public abstract String getColorFlag();
  public abstract void setColorFlag(String colorFlag);
  //CCEnd SS1
  //CCBegin SS2
  /**
   * ���⴦���ʶ 
   */
  public abstract String getSpecialFlag();
  public abstract void setSpecialFlag(String specialFlag);
  //CCEnd SS2
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  /**
   * ���ֵ����
   */
  public BaseValueIfc getValueInfo() throws QMException {
    ListRoutePartLinkInfo info = ListRoutePartLinkInfo.newListRoutePartLinkInfo();
    setValueInfo(info);
    return info;
  }

  /**
   * ���½�ֵ����������á�
   */
  public void setValueInfo(BaseValueIfc info) throws QMException {
    super.setValueInfo(info);
    ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo) info;
    if (this.getAdoptStatus() != null &&
        RouteAdoptedType.toRouteAdoptedType(this.getAdoptStatus()) != null) {
      listPartInfo.setAdoptStatus(RouteAdoptedType.toRouteAdoptedType(this.
          getAdoptStatus()).getDisplay());
    }
    listPartInfo.setInitialUsed(this.getInitialUsed());
    listPartInfo.setRouteListMasterID(this.getRouteListMasterID());    
    listPartInfo.setRouteListIterationID(this.getRouteListIterationID());
    listPartInfo.setAlterStatus(this.getAlterStatus());
    //st skybird 2005.3.1
    // gcy add in 2005.4.26 for reinforce requirement  start
////CCBeginby leixiao 2009-2-20 ԭ�򣺽����������·��,�����׼����Ҫ������Ϣ
    listPartInfo.setParentPartNum(this.getParentPartNum());
    listPartInfo.setParentPartName(this.getParentPartName());
    listPartInfo.setParentPartID(this.getParentPartID());
    listPartInfo.setCount(this.getCount());
//    if (this.getParentPartID() != null &&
//        this.getParentPartID().trim().length() != 0) {
//      PersistService pservice = (PersistService) EJBServiceHelper.
//          getPersistService();
//      try{
//        QMPartIfc partInfo = (QMPartIfc) pservice.refreshInfo(this.
//            getParentPartID());
//        listPartInfo.setParentPart(partInfo);
//      }catch(Exception e)
//      {
//        //System.out.println("����ɾ��");
//      }
//    }
    // gcy add in 2005.4.26 for reinforce requirement  end
    //ed
////CCBeginby leixiao 2009-2-20 ԭ�򣺽����������·��,�����׼����Ҫ������Ϣ
    listPartInfo.setRouteListID(this.getLeftBsoID());
    listPartInfo.setPartMasterID(this.getRightBsoID());
    listPartInfo.setRouteID(this.getRouteID());
    //CCBegin SS1
    listPartInfo.setColorFlag(this.getColorFlag());
    //CCEnd SS1
    if (this.getRightBsoID() != null &&
        this.getRightBsoID().trim().length() != 0) {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) pservice.refreshInfo(this.
          getRightBsoID());
      listPartInfo.setPartMasterInfo(partMasterInfo);
    }
    else {
      throw new QMException("partMasterID ��listRoutePart�����в�ӦΪ��");
    }
//  CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    listPartInfo.setPartBranchID(this.getPartBranchID());
    try
    {
//  CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    if (this.getPartBranchID() != null &&
            this.getPartBranchID().trim().length() != 0) {
          PersistService pservice = (PersistService) EJBServiceHelper.
              getPersistService();
          QMPartIfc partBranchInfo = (QMPartIfc) pservice.refreshInfo(this.
        		  getPartBranchID());
          listPartInfo.setPartBranchInfo(partBranchInfo);
        }
      }
      catch(Exception e)
      {
      	e.printStackTrace();
      }
  }

  /**
   * ����ֵ������г־û���
   */
  public void createByValueInfo(BaseValueIfc info) throws CreateException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter ListRoutePartLinkEJB's createByValueInfo");
    }
    super.createByValueInfo(info);
    ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo) info;
    this.setAdoptStatus(
        RouteHelper.getValue(RouteAdoptedType.getRouteAdoptedTypeSet(),
                             listPartInfo.getAdoptStatus()));
    this.setInitialUsed(listPartInfo.getInitialUsed());
    this.setRouteListMasterID(listPartInfo.getRouteListMasterID());
    this.setRouteListIterationID(listPartInfo.getRouteListIterationID());
    this.setAlterStatus(listPartInfo.getAlterStatus());
    //st skybird 2005.3.1 �㲿���ĸ������
    // gcy add in 2005.4.26 for reinforce requirement  start
    this.setParentPartNum(listPartInfo.getParentPartNum());
    this.setParentPartName(listPartInfo.getParentPartName());
    this.setParentPartID(listPartInfo.getParentPartID());
    this.setCount(listPartInfo.getCount());
    // gcy add in 2005.4.26 for reinforce requirement  end
    //ed
    this.setRouteID(listPartInfo.getRouteID());
//  CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    this.setPartBranchID(listPartInfo.getPartBranchID());
//  CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    //CCBegin SS1
    this.setColorFlag(listPartInfo.getColorFlag());
    //CCEnd SS1
  }

  /**
   * ����ֵ������и��¡�
   */
  public void updateByValueInfo(BaseValueIfc info) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter ListRoutePartLinkEJB's updateByValueInfo");
    }
    super.updateByValueInfo(info);
    ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo) info;
    this.setAdoptStatus(RouteHelper.getValue(RouteAdoptedType.
                                             getRouteAdoptedTypeSet(),
                                             listPartInfo.getAdoptStatus()));
    // gcy add in 2005.4.26 for reinforce requirement  start
    this.setParentPartNum(listPartInfo.getParentPartNum());
    this.setParentPartName(listPartInfo.getParentPartName());
    this.setParentPartID(listPartInfo.getParentPartID());
    this.setCount(listPartInfo.getCount());
    // gcy add in 2005.4.26 for reinforce requirement  end
    this.setInitialUsed(listPartInfo.getInitialUsed());
    this.setRouteListMasterID(listPartInfo.getRouteListMasterID());
    this.setRouteListIterationID(listPartInfo.getRouteListIterationID());
    this.setAlterStatus(listPartInfo.getAlterStatus());
    this.setRouteID(listPartInfo.getRouteID());
//  CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    this.setPartBranchID(listPartInfo.getPartBranchID());
//  CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    //CCBegin SS1
    this.setColorFlag(listPartInfo.getColorFlag());
    //CCEnd SS1
    
  }
}
