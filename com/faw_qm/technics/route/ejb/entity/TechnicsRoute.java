/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */


package com.faw_qm.technics.route.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: </p>
 * <p>Description: ����·�߷������</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */
public interface TechnicsRoute
    extends BsoReference {

  /**
   * ·��˵��
   */
  public java.lang.String getRouteDescription();

  /**
   * ·��˵����
   */
  public void setRouteDescription(String description);
  
  //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
  
  public String getModefyIdenty();

  public void setModefyIdenty(String identy);

  public boolean getIsAdopt();

 public void setIsAdopt(boolean adopt);
 /**
    * Ĭ����Ч��˵��.
    */
   public java.lang.String getDefaultDescreption();

   /**
    * Ĭ����Ч��˵��.
    */
   public void setDefaultDescreption(String des);

   //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��
}
