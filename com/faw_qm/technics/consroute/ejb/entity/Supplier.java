/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2010/09/09
 */


package com.faw_qm.technics.consroute.ejb.entity;

 
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: </p>
 * <p>Description: Ŀ��ɱ��������</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: һ��������˾</p>
 * @mener skybird
 * @version 1.0
 */
 
   
  



public interface Supplier
    extends  BsoReference{
  /**
		*	�õ���Ӧ������
   */
  public java.lang.String getCodename();

  /**
   * ���ù�Ӧ������
   */
  public void setCodename(String codename);

  /**
   * �õ���Ӧ�̴���
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
  public String getJName();

  /**
   * ���
   */
  public void setJName(String jname);

  /**
   * ��ϵ�绰
   */
  public String getTelephone();

  /**
   * ��ϵ�绰
   */
  public void setTelephone(String telephone);


}
