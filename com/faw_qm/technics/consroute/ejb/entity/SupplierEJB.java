/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.ejb.entity;
 

import javax.ejb.CreateException;


import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.technics.consroute.model.SupplierInfo;



/**
 * <p>Title:Ŀ��ɱ� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ</p>
 * @author not attributable
 * @version 1.0 
 */

public abstract class SupplierEJB extends BsoReferenceEJB
{

    /**
     * ���ҵ������Bso��
     * @return String ҵ������Bso��
     */
    public String getBsoName()   
    {
        return "Supplier";
    }


  /**
		*	�õ���Ӧ������
   */
  public abstract java.lang.String getCodename();

  /**
   * ���ù�Ӧ������
   */
  public abstract void setCodename(String codename);

  /**
   * �õ���Ӧ�̴���
   */
  public abstract String getCode();

  /**
   * ���ù�Ӧ�̴���
   */
  public abstract void setCode(String code);
  

  /**
   * ��ϵ��
   */
  public abstract String getPeople();

  /**
   * ��ϵ��
   */
  public abstract void setPeople(String people);

  /**
   * ���
   */
  public abstract String getJName();

  /**
   * ���
   */
  public abstract void setJName(String jname);

  /**
   * ��ϵ�绰
   */
  public abstract String getTelephone();

  /**
   * ��ϵ�绰
   */
  public abstract void setTelephone(String telephone);

 

    /**
     * ���ҵ������ֵ����
     * @return BaseValueIfc ҵ������ֵ����ӿ�
     * @throws QMException
     */
    public BaseValueIfc getValueInfo()
            throws QMException
    {
        SupplierInfo info = new SupplierInfo();
        setValueInfo(info);
        return info;
    }


    /**
     * ����ҵ������ֵ����
     * @param info ҵ������ֵ����
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.setValueInfo(info);
        SupplierInfo valueInfo = (SupplierInfo) info;
        valueInfo.setCodename(getCodename());
        valueInfo.setCode(getCode());
        valueInfo.setPeople(getPeople());
        valueInfo.setJName(getJName());
        valueInfo.setTelephone(getTelephone());
    }


    /**
     * ����ֵ���󴴽�ҵ�����
     * @param info ָ����ҵ������ֵ����
     * @throws CreateException
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        super.createByValueInfo(info);
        SupplierInfo valueInfo = (SupplierInfo) info;


       setCodename(valueInfo.getCodename());
        setCode(valueInfo.getCode());
        setPeople(valueInfo.getPeople());
        setJName(valueInfo.getJName());
        setTelephone(valueInfo.getTelephone());
       

    }


    /**
     * ����ֵ�������ҵ�����
     * @param info ָ����ҵ������ֵ����
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.updateByValueInfo(info);
        SupplierInfo valueInfo = (SupplierInfo) info;
       setCodename(valueInfo.getCodename());
       setCode(valueInfo.getCode());
       setPeople(valueInfo.getPeople());
       setJName(valueInfo.getJName());
       setTelephone(valueInfo.getTelephone());

    }
   

}
