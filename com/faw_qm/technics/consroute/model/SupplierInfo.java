/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.model;
import com.faw_qm.framework.service.BaseValueInfo;


/**
 * <p>Title:������ϸ�� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author �� ��
 * @version 1.0
 */

public class SupplierInfo extends BaseValueInfo implements
        SupplierIfc
{
	 static final long serialVersionUID = 1L;
	  private String codename;
	  private String code;
	  private String people;
	  private String jName;
	  private String telephone;
	  
    /**
     * ����ֵ����
     */
    public SupplierInfo()
    {
        super();


    }

  
    /**
     * getBsoName
     * @return String
     */
    public String getBsoName()
    {
        return "Supplier";
    }


    /**
		*	�õ���Ӧ������
   */
  public java.lang.String getCodename(){
  	return this.codename;
  }
  

  /**
   * ���ù�Ӧ������
   */
  public void setCodename(String codeName){
 		 this.codename = codeName;
  }

  /**
   * �õ���Ӧ�̴���
   */
  public String getCode(){
  	return this.code;
  }

  /**
   * ���ù�Ӧ�̴���
   */
  public void setCode(String code){
  	this.code = code;
  }

  /**
   * ��ϵ��
   */
  public String getPeople(){
  	return this.people;
  }

  /**
   * ��ϵ��
   */
  public void setPeople(String people){
  		this.people = people;
  }

  /**
   * ���
   */
  public java.lang.String getJName(){
  	return this.jName;
  }

  /**
   * ���
   */
  public void setJName(String jname){
  	this.jName = jname;
  }

  /**
   * ��ϵ�绰
   */
  public String getTelephone(){
  	return this.telephone;
  }

  /**
   * ��ϵ�绰
   */
  public void setTelephone(String telephone){
  	this.telephone = telephone;
  }



}
