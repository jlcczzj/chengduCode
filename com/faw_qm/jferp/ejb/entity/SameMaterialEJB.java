package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;

import com.faw_qm.jferp.model.FilterPartInfo;
import com.faw_qm.jferp.model.SameMaterialIfc;
import com.faw_qm.jferp.model.SameMaterialInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;


public abstract class SameMaterialEJB extends BsoReferenceEJB //implements SameMaterial
{
	public SameMaterialEJB()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFSameMaterial";
    }
    
    /**
     * getBsoID
     *
     * @return String
     */
    public abstract String getBsoID();

    /**
     * setBsoID
     *
     * @param string String
     */
    public abstract void setBsoID(String string);

    /**
     * getCreateTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getCreateTime();

    /**
     * setCreateTime
     *
     * @param timestamp Timestamp
     */
    public abstract void setCreateTime(Timestamp timestamp);

    /**
     * getModifyTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getModifyTime();

    /**
     * setModifyTime
     *
     * @param timestamp Timestamp
     */
    public abstract void setModifyTime(Timestamp timestamp);    
    
    public abstract String getPartNumber();
    
    public abstract void setPartNumber(String number);
    
    public abstract String getRouteCode();
	
	public abstract void setRouteCode(String code);
	
	public abstract String getSameMaterialNumber();
	
	public abstract void setSameMaterialNumber(String number);
	
	/**
     *获取业务对象对应的值对象
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        SameMaterialInfo info = new SameMaterialInfo();
        setValueInfo(info);
        return info;
    }

    /**
     *设置业务对象对应的值对象，需要设定锁服务的信息
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        SameMaterialInfo minfo = (SameMaterialInfo) info;
        minfo.setPartNumber(getPartNumber());
        minfo.setRouteCode(getRouteCode());
        minfo.setSameMaterialNumber(getSameMaterialNumber());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        SameMaterialInfo minfo = (SameMaterialInfo) info;
        //需要进行属性校验,
        setPartNumber(minfo.getPartNumber());
        setRouteCode(minfo.getRouteCode());
        setSameMaterialNumber(minfo.getSameMaterialNumber());
    }

    /**
     *过载父类的方法。首先调用父类的相应方法，其后在为本类中定制的业务属性赋值。
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        SameMaterialInfo minfo = (SameMaterialInfo) info;
        //需要进行属性校验,
        setPartNumber(minfo.getPartNumber());
        setRouteCode(minfo.getRouteCode());
        setSameMaterialNumber(minfo.getSameMaterialNumber());
    }
    
    
}