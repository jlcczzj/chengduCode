/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * SS1 ���ӱ����ڲ�Ʒ����BsoID liuyang 2014-6-6
 * SS2 ��Ӹ����� liunan 2015-6-18
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
 * <p>Description: ����·�߷������</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: һ��������˾</p>
 * @author ������
 * @mener skybird
 * @version 1.0
 */
public abstract class TechnicsRouteListEJB
    extends RevisionControlledEJB {

  public TechnicsRouteListEJB() {

  }

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ����Ҫ���зǿռ�顣
   * ����·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
   */
  public abstract java.lang.String getRouteListNumber();

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ������·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
   */
  public abstract void setRouteListNumber(String number);

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ�顣
   */
  public abstract java.lang.String getRouteListName();

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��
   */
  public abstract void setRouteListName(String name);

  /**
   * ·�߱�˵��
   */
  public abstract java.lang.String getRouteListDescription();

  /**
   * ·�߱�˵��
   */
  public abstract void setRouteListDescription(String description);

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������).
   */
  public abstract java.lang.String getRouteListLevel();

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   */
  public abstract void setRouteListLevel(String level);

  public abstract java.lang.String getRouteListState();

  public abstract void setRouteListState(String state);

  /**
   * ����·�߱�ĵ�λ��ʶ
   */
  public abstract java.lang.String getRouteListDepartment();

  /**
   * ����·�߱�ĵ�λ��ʶ
   */
  public abstract void setRouteListDepartment(String department);

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   */
  public abstract java.lang.String getProductMasterID();

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   */
  public abstract void setProductMasterID(String productMasterID);

  //CCBegin SS1
  /**
   * �����ڲ�ƷbsoID
   */
  public abstract String getProductID();
  public abstract void setProductID(String productID);
  //CCEnd SS1
  /**
   * �õ��첿����˳��
   */
  public abstract Vector getPartIndex();

  /**
   * �����㲿������ѫ
   */
  public abstract void setPartIndex(Vector partIndex);
  
  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  /**
   * @roseuid 402C182802DA
   * @J2EE_METHOD  --  getProductMasterID
   * Ĭ����Ч��˵��.
   */
  public abstract java.lang.String getDefaultDescreption();

  /**
   * @roseuid 402C185602CC
   * @J2EE_METHOD  --  setProductMasterID
   * Ĭ����Ч��˵��.
   */
  public abstract void setDefaultDescreption(String des);
  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  /**
   * ���ҵ���������
   */
  public String getBsoName() {
    return "TechnicsRouteList";
  }

  /**
   * ���ֵ����
   */
  public BaseValueIfc getValueInfo() throws QMException {
    TechnicsRouteListInfo info = new TechnicsRouteListInfo();
    setValueInfo(info);
    return info;
  }

  /**
   * ���½�ֵ����������á�
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
      //throw new TechnicsRouteException("��λID��Ӧ��Ϊ�ա�");
    }
    //����ö�����ֵ��
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
      throw new TechnicsRouteException("MasterBsoID��ӦΪ�ա�");
    }
    MasterIfc masterInfo = (MasterIfc) pservice.refreshInfo(this.getMasterBsoID());
    listinfo.setMaster(masterInfo);    
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
    listinfo.setDefaultDescreption(this.getDefaultDescreption());
    //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  }

  private String getFirstDepartmentID() {
    return null;
  }

  //��õ�λ���ơ�
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
   * ����ֵ������г־û���
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
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
    this.setDefaultDescreption(listinfo.getDefaultDescreption());
    //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  }

  /**
   * ����ֵ������и��¡�
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
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
    this.setDefaultDescreption(listinfo.getDefaultDescreption());
    //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  }
  
  
  //CCBegin SS2
  /**
   * �鿴�����Ƿ��������
   * @return boolean true������
   * @throws QMException
   */
  public boolean isHasContents() throws QMException
  {
  	ContentService service = (ContentService) EJBServiceHelper.getService("ContentService");
  	return service.isHasContent((ContentHolder) entityContext.getEJBLocalObject());
  }
  
  /**
   * ��ȡ������������������
   * @return Vector ������������������
   * @throws QMException
   */
  public Vector getContentVector() throws QMException
  {
  	ContentService service = (ContentService) EJBServiceHelper.getService("ContentService");
    return service.getContents((ContentHolder) entityContext.getEJBLocalObject());
  }
  //CCEnd SS2
}
