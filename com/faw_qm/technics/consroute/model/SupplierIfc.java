/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.model;
 

import com.faw_qm.framework.service.BaseValueIfc;




/**
 * <p>Title:������ϸ�� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author �� ��
 * @version 1.0
 */

public interface SupplierIfc extends  BaseValueIfc
{
    /**
		*	�õ���Ӧ������ 
   */
  public java.lang.String getCodename();

  /**
   * ���ù�Ӧ������
   */
  public void setCodename(String codename);

  /**
   * �õ���Ӧ�̴���s
   */
  public String getCode();

  /**
   * ���ù�Ӧ�̴���
   */
  public void setCode(String code);

  /**
   * ��ϵ��
   */
  public String getPeople();

  /**
   * ��ϵ��
   */
  public void setPeople(String people);

  /**
   * ���
   */
  public java.lang.String getJName();

  /**
   * ���
   */
  public void setJName(String jName);

  /**
   * ��ϵ�绰
   */
  public String getTelephone();

  /**
   * ��ϵ�绰
   */
  public void setTelephone(String telephone);


}
