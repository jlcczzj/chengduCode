/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * SS1 ���ӱ����ڲ�Ʒ����BsoID liuyang 2014-6-6
 */


package com.faw_qm.technics.route.model;

import java.util.Vector;

import com.faw_qm.enterprise.model.RevisionControlledInfo;
import com.faw_qm.technics.route.exception.TechnicsRouteException;

//����·�߱�ı�ţ�Ψһ��ʶ��ע����sql�жԶ�Ӧ��master����Ψһ�����������ڴ�����ʱҪ��װoracle�쳣��

/**
 * ����·�߱�ֵ����
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw-qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class TechnicsRouteListInfo
    extends RevisionControlledInfo
    implements TechnicsRouteListIfc {
  private static final long serialVersionUID = 1L;

  /**
   * ���ҵ���������
   */
  public String getBsoName() {
    return "TechnicsRouteList";
  }

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��.
   */
  private String name;

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ�����зǿռ�顣
   */
  private String number;

  /**
   * ·�߱�˵��
   */
  private String description;

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   */
  private String level;

  // ����·�߱��״̬
  private String state;

  /**
   * ����·�߱�ĵ�λID.
   */
  private String department;

  /**
   * ����·�߱�ĵ�λ����.
   */
  private String departmentName;

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   */
  private String productMasterID;
  //CCBegin SS1
  private String productID;
  //CCEnd SS1
  private Vector partIndex;
  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  private String defaultDescription;
  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  
  /**
   * ���캯��
   */
  public TechnicsRouteListInfo() {
    TechnicsRouteListMasterInfo masterInfo = new TechnicsRouteListMasterInfo();
    this.setMaster(masterInfo);
  }

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ��
   * @return java.lang.String
   */
  public String getRouteListNumber() {
    return number;
  }

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ
   * @param number - �ַ����Ȳ�����30
   */
  public void setRouteListNumber(String number) throws TechnicsRouteException {
    if (number == null || number.trim().length() == 0) {
      throw new TechnicsRouteException("2", null);
    }
    ( (TechnicsRouteListMasterInfo) getMaster()).setRouteListNumber(number);
    this.number = number;
  }

  /**
   * �õ��첿����˳��
   */
  public Vector getPartIndex() {
    return this.partIndex;
  }

  /**
   * �����㲿������ѫ
   */
  public void setPartIndex(Vector partIndex) {
    this.partIndex = partIndex;
  }

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��
   * @return java.lang.String
   */
  public String getRouteListName() {
    return name;
  }

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��
   * @param name
   */
  public void setRouteListName(String name) throws TechnicsRouteException {
    if (name == null || name.trim().length() == 0) {
      throw new TechnicsRouteException("1", null);
    }
    ( (TechnicsRouteListMasterInfo) getMaster()).setRouteListName(name);
    this.name = name;
  }

  /**
   * ·�߱�˵��
   * @return java.lang.String
   */
  public String getRouteListDescription() {
    return description;
  }

  /**
   * ·�߱�˵��
   * @param description
   */
  public void setRouteListDescription(String description) {
    this.description = description;
  }

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   * @return java.lang.String
   */
  public String getRouteListLevel() {
    return level;
  }

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   * @param level
   */
  public void setRouteListLevel(String level) {
    this.level = level;
  }

  /**
   *  ����·�߱��״̬
   * @return java.lang.String
   */
  public String getRouteListState() {
    return state;
  }

  /**
   * ���ù���·�߱��״̬
   * @param state
   */
  public void setRouteListState(String state) {
    this.state = state;
  }

  /**
   * ����·�߱�ĵ�λID.
   * @return java.lang.String
   */
  public String getRouteListDepartment() {
    return department;
  }

  /**
   * ����·�߱�ĵ�λID.
   * @param department - ����·�߱�ĵ�λID.
   */
  public void setRouteListDepartment(String department) {
    this.department = department;
  }

  /**
   * ����·�߱�ĵ�λ����.
   * @return java.lang.String
   */
  public String getDepartmentName() {
    return departmentName;
  }

  /**
   * ����·�߱�ĵ�λ����.�˷�����EJB���á�
   * @param department - ����·�߱�ĵ�λ����.
   */
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   * @return java.lang.String
   */
  public String getProductMasterID() {
    return productMasterID;
  }

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   * @param productMasterID - �㲿��ID.
   */
  public void setProductMasterID(String productMasterID) {
    ( (TechnicsRouteListMasterInfo) getMaster()).setProductMasterID(
        productMasterID);
    this.productMasterID = productMasterID;
  }
  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  /**
   * @roseuid 402C182802DA
   * @J2EE_METHOD  --  getProductMasterID
   * Ĭ����Ч��˵��.
   */
  public java.lang.String getDefaultDescreption()
  {
    return this.defaultDescription;
  }

  /**
   * @roseuid 402C185602CC
   * @J2EE_METHOD  --  setProductMasterID
   * Ĭ����Ч��˵��.
   */
  public void setDefaultDescreption(String des){
    this.defaultDescription = des;
  }
  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  //CCBegin SS1
  /**
   * �����ڲ�ƷbsoID
   */
  public String getProductID()
  {
	  return this.productID;
  }
  public void setProductID(String productID){
	  this.productID=productID;
  }
  //CCEnd SS1
}
