/** 生成程序 ListRoutePartLinkEJB 1.0 2005.3.2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 增加彩色件标识 liuyang 2014-6-6
 * SS2 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
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
 * 工艺路线对应的产品结构可以选择。产品的子零件
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm</p>
 * @author not 管春元
 * @version 1.0
 */
abstract public class ListRoutePartLinkEJB
    extends BinaryLinkEJB {

  /**
   * 构造函数
   */
  public ListRoutePartLinkEJB() {

  }

  /**
   * 得到业务类名
   * @return String
   */
  public String getBsoName() {
    return "ListRoutePartLink";
  }

  /**
   * 设置工艺路线ID
   * @param routeID String
   */
  public abstract void setRouteID(String routeID);

  /**
   * 得到工艺路线ID
   * @return String
   */
  public abstract String getRouteID();

  /**
   * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状
   * 态作为持久化属性。
   * 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。
   * 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
   * @return boolean
   */
  public abstract String getAdoptStatus();

  /**
   * 设置零件的工艺路线是否被采用。
   * @param status 采用、取消。
   */
  public abstract void setAdoptStatus(String status);

  //st skybird 2005.3.1
  // gcy add in 2005.4.26 for reinforce requirement  start

  /**
   * 得到父件的编号
   * @return String 父件的编号
   */
  public abstract String getParentPartNum();

  /**
   * 设置父件的编号
   * @param parentPartNum String
   */
  public abstract void setParentPartNum(String parentPartNum);

  /**
   * 得到父件名
   * @return String
   */
  public abstract String getParentPartName();

  /**
   * 设置父件的名称
   * @param parentPartNum String
   */
  public abstract void setParentPartName(String parentPartNum);

  /**
   * 得到父件的id
   * @return String
   */
  public abstract String getParentPartID();

  /**
   * 设置父件的id
   * @param parentPartNum String 父件的id
   */
  public abstract void setParentPartID(String parentPartNum);

  /**
   * 得到当前零部件在父件中使用数量（不一定是直接上级）
   * @return String
   */
  public abstract int getCount();

  /**
   * 设置零部件在父件中在产品中使用的数量（不一定是直接上级）
   * @param count int
   */
  public abstract void setCount(int count);

  // gcy add in 2005.4.26 for reinforce requirement  end


  /**
   * 此标识用来判断与路线表关联的路线是否显示（在物料清单中）。是否处理采用、取消。
   * @return alterStatus=0表示是从上一版本继承下来的。否则alterStatus=1，从本版本生成的。涉及到路线是否重新生成。
   */
  public abstract int getAlterStatus();

  /**
   * 设置修改状态
   * @param alterStatus int int =0 表示是从上一版本继承下来的；
   * int=1，从本版本生成的。涉及到路线是否重新生成；
   * int=2，此版本删除的，当路线表检出时，不复制此关联。
   */
  public abstract void setAlterStatus(int alterStatus);

  /**
   * 初始采用版本。A, B.大阪本号。
   * @return int
   */
  public abstract String getInitialUsed();

  /**
   * 设置采用版本。A, B.大版本号
   * @param version String
   */
  public abstract void setInitialUsed(String version);

  /**
   * 得到路线表的id
   * @return String 路线表的id
   */
  public abstract String getRouteListMasterID();

  /**
   * 设置路线表的id
   * @param listMasterID String 路线表的id
   */
  public abstract void setRouteListMasterID(String listMasterID);

  /**
   * 得到关联中路线表的小版本
   * @return String
   */
  public abstract String getRouteListIterationID();

  /**
   * 设置关联中路线表的小版本
   * @param listIterationID String
   */
  public abstract void setRouteListIterationID(String listIterationID);
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  /**
   * 得到关联中零件的小版本
   * @return String
   */
  public abstract String getPartBranchID();

  /**
   * 设置关联中零件的小版本
   * @param listIterationID String
   */
  public abstract void setPartBranchID(String partBranchID);
//CCBegin SS1
  /**
   * 颜色标识 
   */
  public abstract String getColorFlag();
  public abstract void setColorFlag(String colorFlag);
  //CCEnd SS1
  //CCBegin SS2
  /**
   * 特殊处理标识 
   */
  public abstract String getSpecialFlag();
  public abstract void setSpecialFlag(String specialFlag);
  //CCEnd SS2
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  /**
   * 获得值对象。
   */
  public BaseValueIfc getValueInfo() throws QMException {
    ListRoutePartLinkInfo info = ListRoutePartLinkInfo.newListRoutePartLinkInfo();
    setValueInfo(info);
    return info;
  }

  /**
   * 对新建值对象进行设置。
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
////CCBeginby leixiao 2009-2-20 原因：解放升级工艺路线,解放艺准不需要父件信息
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
//        //System.out.println("父件删除");
//      }
//    }
    // gcy add in 2005.4.26 for reinforce requirement  end
    //ed
////CCBeginby leixiao 2009-2-20 原因：解放升级工艺路线,解放艺准不需要父件信息
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
      throw new QMException("partMasterID 在listRoutePart关联中不应为空");
    }
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    listPartInfo.setPartBranchID(this.getPartBranchID());
    try
    {
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
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
   * 根据值对象进行持久化。
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
    //st skybird 2005.3.1 零部件的父件编号
    // gcy add in 2005.4.26 for reinforce requirement  start
    this.setParentPartNum(listPartInfo.getParentPartNum());
    this.setParentPartName(listPartInfo.getParentPartName());
    this.setParentPartID(listPartInfo.getParentPartID());
    this.setCount(listPartInfo.getCount());
    // gcy add in 2005.4.26 for reinforce requirement  end
    //ed
    this.setRouteID(listPartInfo.getRouteID());
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    this.setPartBranchID(listPartInfo.getPartBranchID());
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    //CCBegin SS1
    this.setColorFlag(listPartInfo.getColorFlag());
    //CCEnd SS1
  }

  /**
   * 根据值对象进行更新。
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
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    this.setPartBranchID(listPartInfo.getPartBranchID());
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    //CCBegin SS1
    this.setColorFlag(listPartInfo.getColorFlag());
    //CCEnd SS1
    
  }
}
