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
     *��ȡҵ������Ӧ��ֵ����
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
     *����ҵ������Ӧ��ֵ������Ҫ�趨���������Ϣ
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
        //��Ҫ��������У��,
        setPartNumber(minfo.getPartNumber());
        setRouteCode(minfo.getRouteCode());
        setSameMaterialNumber(minfo.getSameMaterialNumber());
    }

    /**
     *���ظ���ķ��������ȵ��ø������Ӧ�����������Ϊ�����ж��Ƶ�ҵ�����Ը�ֵ��
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        SameMaterialInfo minfo = (SameMaterialInfo) info;
        //��Ҫ��������У��,
        setPartNumber(minfo.getPartNumber());
        setRouteCode(minfo.getRouteCode());
        setSameMaterialNumber(minfo.getSameMaterialNumber());
    }
    
    
}