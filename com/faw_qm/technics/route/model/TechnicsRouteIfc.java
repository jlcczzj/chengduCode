/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */


package com.faw_qm.technics.route.model;

import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * ����·��ֵ����ӿ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw-qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteIfc
    extends BaseValueIfc, CappIdentity {

  /**
   * ·��˵��
   * @return java.lang.String
   */
  public String getRouteDescription();

  /**
   * ·��˵����
   * @param description
   */
  public void setRouteDescription(String description);
 
  //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
  public CodingIfc getModefyIdenty();

  public void setModefyIdenty(CodingIfc identy);

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
