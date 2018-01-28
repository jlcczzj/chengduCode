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


package com.faw_qm.technics.route.model;

import java.util.Vector;

import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
//CCBegin SS2
import com.faw_qm.content.model.ContentHolderIfc;
//CCEnd SS2
/**
 * ����Ϊ����ʹ�����Ҳ���������ʹ�ã�Ϊ����Map. ������֮ͬ��
 * �������Լ�ejb.
 */

/**
 * ����·�߱�ֵ����ӿ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteListIfc
    //CCBegin SS2
    //extends RevisionControlledIfc {
    extends RevisionControlledIfc, ContentHolderIfc {
    //CCEnd SS2
  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ��
   * @return java.lang.String
   */
  public String getRouteListNumber();

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ
   * @param number - �ַ����Ȳ�����30
   */
  public void setRouteListNumber(String number) throws TechnicsRouteException;

  /**
   * �õ��첿����˳��
   */
  public Vector getPartIndex();

  /**
   * �����㲿������ѫ
   */
  public void setPartIndex(Vector partIndex);

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��
   * @return java.lang.String
   */
  public String getRouteListName();

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��
   * @param name
   */
  public void setRouteListName(String name) throws TechnicsRouteException;

  /**
   * ·�߱�˵��
   * @return java.lang.String
   */
  public String getRouteListDescription();

  /**
   * ·�߱�˵��
   * @param description
   */
  public void setRouteListDescription(String description);

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   * @return java.lang.String
   */
  public String getRouteListLevel();

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   * @param level
   */
  public void setRouteListLevel(String level);

  /**
   * ����·�߱��״̬
   * ǰ׼����׼����׼����׼�ȣ��ڴ��������ά��
   * @return java.lang.String
   */
  public String getRouteListState();

  /**
   * ���ù���·�߱��״̬
   * @param state
   */
  public void setRouteListState(String state);

  /**
   * ����·�߱�ĵ�λ����ID
   * @return java.lang.String
   */
  public String getRouteListDepartment();

  /**
   * ����·�߱�ĵ�λ����ID
   * @param department - ����·�߱�ĵ�λ����ID
   */
  public void setRouteListDepartment(String department);

  /**
   * ����·�߱�ĵ�λ����.
   * @return java.lang.String
   */
  public String getDepartmentName();

  /**
   * ����·�߱�ĵ�λ����.�˷�����EJB���á�
   * @param department - ����·�߱�ĵ�λ����.
   */
  public void setDepartmentName(String departmentName);

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   * @return java.lang.String
   */
  public String getProductMasterID();

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   * @param productMasterID - �㲿��ID.
   */
  public void setProductMasterID(String productMasterID);
  
  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  /**
   * @roseuid 402C182802DA
   * @J2EE_METHOD  --  getProductMasterID
   * Ĭ����Ч��˵��.
   */
  public java.lang.String getDefaultDescreption();

  /**
   * @roseuid 402C185602CC
   * @J2EE_METHOD  --  setProductMasterID
   * Ĭ����Ч��˵��.
   */
  public void setDefaultDescreption(String des);
  
  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"Ĭ����Ч��˵��"
  //CCBegin SS1
  /**
   * �����ڲ�ƷbsoID
   */
  public String getProductID();
  public void setProductID(String productID);
  //CCEnd SS1
}
