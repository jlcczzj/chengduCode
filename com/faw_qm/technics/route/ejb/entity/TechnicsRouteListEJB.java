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
import javax.ejb.CreateException;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.enterprise.ejb.entity.RevisionControlledEJB;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;
//CCBegin SS2
import com.faw_qm.content.ejb.service.ContentHolder;
import com.faw_qm.content.ejb.service.ContentService;
//CCEnd SS2
/**
 * <p>Title: </p>
 * <p>Description: 工艺路线服务端类</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @mener skybird
 * @version 1.0
 */
public abstract class TechnicsRouteListEJB
    extends RevisionControlledEJB {

  public TechnicsRouteListEJB() {

  }

  /**
   * 工艺路线表的编号，唯一标识。需要进行非空检查。
   * 工艺路线表有版本，编号的唯一性在对应的Master中保证。
   */
  public abstract java.lang.String getRouteListNumber();

  /**
   * 工艺路线表的编号，唯一标识。工艺路线表有版本，编号的唯一性在对应的Master中保证。
   */
  public abstract void setRouteListNumber(String number);

  /**
   * 工艺路线表的名称，需要进行非空检查。
   */
  public abstract java.lang.String getRouteListName();

  /**
   * 工艺路线表的名称，需要进行非空检查
   */
  public abstract void setRouteListName(String name);

  /**
   * 路线表说明
   */
  public abstract java.lang.String getRouteListDescription();

  /**
   * 路线表说明
   */
  public abstract void setRouteListDescription(String description);

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级).
   */
  public abstract java.lang.String getRouteListLevel();

  /**
   * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
   */
  public abstract void setRouteListLevel(String level);

  public abstract java.lang.String getRouteListState();

  public abstract void setRouteListState(String state);

  /**
   * 二级路线表的单位标识
   */
  public abstract java.lang.String getRouteListDepartment();

  /**
   * 二级路线表的单位标识
   */
  public abstract void setRouteListDepartment(String department);

  /**
   * 获得工艺路线表对应的产品ID.
   */
  public abstract java.lang.String getProductMasterID();

  /**
   * 获得工艺路线表对应的产品ID.
   */
  public abstract void setProductMasterID(String productMasterID);

  //CCBegin SS1
  /**
   * 被用于产品bsoID
   */
  public abstract String getProductID();
  public abstract void setProductID(String productID);
  //CCEnd SS1
  /**
   * 得到领部件的顺序
   */
  public abstract Vector getPartIndex();

  /**
   * 设置零部件的殊勋
   */
  public abstract void setPartIndex(Vector partIndex);
  
  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  /**
   * @roseuid 402C182802DA
   * @J2EE_METHOD  --  getProductMasterID
   * 默认有效性说明.
   */
  public abstract java.lang.String getDefaultDescreption();

  /**
   * @roseuid 402C185602CC
   * @J2EE_METHOD  --  setProductMasterID
   * 默认有效性说明.
   */
  public abstract void setDefaultDescreption(String des);
  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  /**
   * 获得业务对象名。
   */
  public String getBsoName() {
    return "TechnicsRouteList";
  }

  /**
   * 获得值对象。
   */
  public BaseValueIfc getValueInfo() throws QMException {
    TechnicsRouteListInfo info = new TechnicsRouteListInfo();
    setValueInfo(info);
    return info;
  }

  /**
   * 对新建值对象进行设置。
   */
  public void setValueInfo(BaseValueIfc info) throws QMException {
    super.setValueInfo(info);
    TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo) info;
    listinfo.setRouteListName(this.getRouteListName());
    listinfo.setRouteListNumber(this.getRouteListNumber());
    listinfo.setRouteListDescription(this.getRouteListDescription());
    listinfo.setRouteListDepartment(this.getRouteListDepartment());
    if (this.getRouteListDepartment() != null) {
      listinfo.setDepartmentName(getDepartmentName(this.getRouteListDepartment()));
    }
    else {
      listinfo.setDepartmentName(getFirstDepartmentID());
      //throw new TechnicsRouteException("单位ID不应该为空。");
    }
    //保存枚举类的值。
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println(
          "TechnicsRouteListEJB's setValueInfo RouteListLevelType.display = " +
          this.getRouteListLevel());
    }
    listinfo.setRouteListLevel(RouteListLevelType.toRouteListLevelType(this.
        getRouteListLevel()).getDisplay());
    listinfo.setRouteListState(this.getRouteListState());
    listinfo.setProductMasterID(this.getProductMasterID());
    //CCBegin SS1
    listinfo.setProductID(this.getProductID());
    //CCEnd SS1
    listinfo.setPartIndex(this.getPartIndex());
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    if (this.getMasterBsoID() == null) {
      throw new TechnicsRouteException("MasterBsoID不应为空。");
    }
    MasterIfc masterInfo = (MasterIfc) pservice.refreshInfo(this.getMasterBsoID());
    listinfo.setMaster(masterInfo);    
    //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
    listinfo.setDefaultDescreption(this.getDefaultDescreption());
    //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  }

  private String getFirstDepartmentID() {
    return null;
  }

  //获得单位名称。
  private String getDepartmentName(String departmentID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    BaseValueIfc codeInfo = pservice.refreshInfo(departmentID);
    String departmentName = null;
    if (codeInfo instanceof CodingIfc) {
      departmentName = ( (CodingIfc) codeInfo).getCodeContent();
    }
    if (codeInfo instanceof CodingClassificationIfc) {
      departmentName = ( (CodingClassificationIfc) codeInfo).getCodeSort();
    }
    return departmentName;
  }

  /**
   * 根据值对象进行持久化。
   */
  public void createByValueInfo(BaseValueIfc info) throws CreateException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter TechnicsRouteListEJB's createByValueInfo");
    }
    super.createByValueInfo(info);
    TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo) info;
    this.setRouteListName(listinfo.getRouteListName());
    this.setRouteListNumber(listinfo.getRouteListNumber());
    this.setRouteListDescription(listinfo.getRouteListDescription());
    this.setRouteListDepartment(listinfo.getRouteListDepartment());
    String value = RouteListLevelType.getValue(listinfo.getRouteListLevel());
    this.setRouteListLevel(value);
    this.setPartIndex(listinfo.getPartIndex());
    this.setRouteListState(listinfo.getRouteListState());
    this.setProductMasterID(listinfo.getProductMasterID());
    //CCBegin SS1
    this.setProductID(listinfo.getProductID());
    //CCEnd SS1
    //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
    this.setDefaultDescreption(listinfo.getDefaultDescreption());
    //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  }

  /**
   * 根据值对象进行更新。
   */
  public void updateByValueInfo(BaseValueIfc info) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter TechnicsRouteListEJB's updateByValueInfo");
    }
    super.updateByValueInfo(info);
    TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo) info;
    this.setRouteListName(listinfo.getRouteListName());
    this.setRouteListNumber(listinfo.getRouteListNumber());
    this.setRouteListDescription(listinfo.getRouteListDescription());
    this.setRouteListDepartment(listinfo.getRouteListDepartment());
    this.setRouteListLevel(RouteHelper.getValue(RouteListLevelType.
                                                getRouteListLevelTypeSet(),
                                                listinfo.getRouteListLevel()));
    this.setRouteListState(listinfo.getRouteListState());
    this.setPartIndex(listinfo.getPartIndex());
    this.setProductMasterID(listinfo.getProductMasterID());
    //CCBegin SS1
    this.setProductID(listinfo.getProductID());
    //CCEnd SS1
    //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
    this.setDefaultDescreption(listinfo.getDefaultDescreption());
    //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"默认有效性说明"
  }
  
  
  //CCBegin SS2
  /**
   * 查看容器是否包含内容
   * @return boolean true，包含
   * @throws QMException
   */
  public boolean isHasContents() throws QMException
  {
  	ContentService service = (ContentService) EJBServiceHelper.getService("ContentService");
  	return service.isHasContent((ContentHolder) entityContext.getEJBLocalObject());
  }
  
  /**
   * 获取容器关联的所有内容
   * @return Vector 容器关联的所有内容
   * @throws QMException
   */
  public Vector getContentVector() throws QMException
  {
  	ContentService service = (ContentService) EJBServiceHelper.getService("ContentService");
    return service.getContents((ContentHolder) entityContext.getEJBLocalObject());
  }
  //CCEnd SS2
}
