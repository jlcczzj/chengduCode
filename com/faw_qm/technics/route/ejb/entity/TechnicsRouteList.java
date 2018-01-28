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

import com.faw_qm.enterprise.ejb.entity.RevisionControlled;
//CCBegin SS2
import com.faw_qm.content.ejb.service.ContentHolder;
//CCEnd SS2
/**
 * һ���㲿�������ж������·�߱�
 * һ������·�߱��ж������·�ߡ�
 * TechnicsRouteListIfc��Ψһ��ʶ�ɱ�ʶ������������
 */


/**
 * <p>Title: </p>
 * <p>Description: ����·�߷������</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: һ��������˾</p>
 * @author ������
 * @mener skybird
 * @version 1.0
 */
public interface TechnicsRouteList
    //CCBegin SS2
    //extends RevisionControlled {
    extends RevisionControlled, ContentHolder {
    //CCEnd SS2
  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ����Ҫ���зǿռ�顣
   * ����·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
   */
  public java.lang.String getRouteListNumber();

  /**
   * ����·�߱�ı�ţ�Ψһ��ʶ������·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
   */
  public void setRouteListNumber(String number);

  /**
   * �õ��첿����˳��
   */
  public Vector getPartIndex();

  /**
   * �����㲿������ѫ
   */
  public void setPartIndex(Vector partIndex);

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ�顣
   */
  public java.lang.String getRouteListName();

  /**
   * ����·�߱�����ƣ���Ҫ���зǿռ��
   */
  public void setRouteListName(String name);

  /**
   * ·�߱�˵��
   */
  public java.lang.String getRouteListDescription();

  /**
   * ·�߱�˵��
   */
  public void setRouteListDescription(String description);

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������).
   */
  public java.lang.String getRouteListLevel();

  /**
   * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
   */
  public void setRouteListLevel(String level);

  /**
   * ����·�߱�״̬
   * @return
   */
  public java.lang.String getRouteListState();

  /**
   * ���ù���·�ߵ�״̬
   */
  public void setRouteListState(String state);

  /**
   * ����·�߱�ĵ�λ��ʶ:codeID
   */
  public java.lang.String getRouteListDepartment();

  /**
   * ����·�߱�ĵ�λ��ʶ:codeID
   */
  public void setRouteListDepartment(String department);

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
   */
  public java.lang.String getProductMasterID();

  /**
   * ��ù���·�߱��Ӧ�Ĳ�ƷID.
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
