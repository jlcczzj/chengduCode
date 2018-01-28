/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.ejb.entity;
 

import javax.ejb.CreateException;


import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.technics.consroute.model.SupplierInfo;



/**
 * <p>Title:目标成本 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息</p>
 * @author not attributable
 * @version 1.0 
 */

public abstract class SupplierEJB extends BsoReferenceEJB
{

    /**
     * 获得业务对象的Bso名
     * @return String 业务对象的Bso名
     */
    public String getBsoName()   
    {
        return "Supplier";
    }


  /**
		*	得到供应商名称
   */
  public abstract java.lang.String getCodename();

  /**
   * 设置供应商名称
   */
  public abstract void setCodename(String codename);

  /**
   * 得到供应商代码
   */
  public abstract String getCode();

  /**
   * 设置供应商代码
   */
  public abstract void setCode(String code);
  

  /**
   * 联系人
   */
  public abstract String getPeople();

  /**
   * 联系人
   */
  public abstract void setPeople(String people);

  /**
   * 简称
   */
  public abstract String getJName();

  /**
   * 简称
   */
  public abstract void setJName(String jname);

  /**
   * 联系电话
   */
  public abstract String getTelephone();

  /**
   * 联系电话
   */
  public abstract void setTelephone(String telephone);

 

    /**
     * 获得业务对象的值对象
     * @return BaseValueIfc 业务对象的值对象接口
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
     * 设置业务对象的值对象
     * @param info 业务对象的值对象
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
     * 根据值对象创建业务对象
     * @param info 指定的业务对象的值对象
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
     * 根据值对象更新业务对象
     * @param info 指定的业务对象的值对象
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
