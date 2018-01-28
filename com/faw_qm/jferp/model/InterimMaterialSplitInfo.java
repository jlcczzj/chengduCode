/**
 * ���ɳ���InterimMaterialSplitInfo.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import java.sql.Timestamp;
import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class InterimMaterialSplitInfo extends BaseValueInfo implements
        InterimMaterialSplitIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public InterimMaterialSplitInfo()
    {
        super();
        materialSplitMap = new InterimMaterialSplitMap();
    }

    private InterimMaterialSplitMap materialSplitMap;

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "JFInterimMaterialSplit";
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getColorFlag()
     */
    public boolean getColorFlag()
    {
        return materialSplitMap.getColorFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialSplitMap.getDefaultUnit();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getMaterial()
     */
    public String getMaterialNumber()
    {
        return materialSplitMap.getMaterialNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getOptionCode()
     */
    public String getOptionCode()
    {
        return materialSplitMap.getOptionCode();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartName()
     */
    public String getPartName()
    {
        return materialSplitMap.getPartName();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartNumber()
     */
    public String getPartNumber()
    {
        return materialSplitMap.getPartNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartType()
     */
    public String getPartType()
    {
        return materialSplitMap.getPartType();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartModifyTime()
     */
    public Timestamp getPartModifyTime()
    {
        return materialSplitMap.getPartModifyTime();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartVersion()
     */
    public String getPartVersion()
    {
        return materialSplitMap.getPartVersion();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getProducedBy()
     */
    public String getProducedBy()
    {
        return materialSplitMap.getProducedBy();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getRoute()
     */
    public String getRoute()
    {
        return materialSplitMap.getRoute();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getRouteCode()
     */
    public String getRouteCode()
    {
        return materialSplitMap.getRouteCode();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getSplited()
     */
    public boolean getSplited()
    {
        return materialSplitMap.getSplited();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getState()
     */
    public String getState()
    {
        return materialSplitMap.getState();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getStatus()
     */
    public int getStatus()
    {
        return materialSplitMap.getStatus();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setColorFlag(boolean)
     */
    public void setColorFlag(boolean colorFlag)
    {
        materialSplitMap.setColorFlag(colorFlag);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setDefaultUnit(java.lang.String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialSplitMap.setDefaultUnit(defaultUnit);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setMaterialNumber(java.lang.String)
     */
    public void setMaterialNumber(String materialNumber)
    {
        materialSplitMap.setMaterialNumber(materialNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setOptionCode(java.lang.String)
     */
    public void setOptionCode(String optionCode)
    {
        materialSplitMap.setOptionCode(optionCode);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartName(java.lang.String)
     */
    public void setPartName(String partName)
    {
        materialSplitMap.setPartName(partName);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartNumber(java.lang.String)
     */
    public void setPartNumber(String partNumber)
    {
        materialSplitMap.setPartNumber(partNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartType(java.lang.String)
     */
    public void setPartType(String partType)
    {
        materialSplitMap.setPartType(partType);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartVersion(java.lang.String)
     */
    public void setPartVersion(String partVersion)
    {
        materialSplitMap.setPartVersion(partVersion);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setProducedBy(java.lang.String)
     */
    public void setProducedBy(String producedBy)
    {
        materialSplitMap.setProducedBy(producedBy);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setRoute(java.lang.String)
     */
    public void setRoute(String route)
    {
        materialSplitMap.setRoute(route);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setRouteCode(java.lang.String)
     */
    public void setRouteCode(String routeCode)
    {
        materialSplitMap.setRouteCode(routeCode);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setSplited(boolean)
     */
    public void setSplited(boolean splited)
    {
        materialSplitMap.setSplited(splited);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setState(java.lang.String)
     */
    public void setState(String state)
    {
        materialSplitMap.setState(state);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setStatus(int)
     */
    public void setStatus(int status)
    {
        materialSplitMap.setStatus(status);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        if(getBsoID() == null)
            return getBsoName();
        else
            return getMaterialNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartModifyTime(java.sql.Timestamp)
     */
    public void setPartModifyTime(Timestamp partModifyTime)
    {
        materialSplitMap.setPartModifyTime(partModifyTime);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getSourceBsoID()
     */
    public String getSourceBsoID()
    {
        return materialSplitMap.getSourceBsoID();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getUpdateFlag()
     */
    public String getUpdateFlag()
    {
        return materialSplitMap.getUpdateFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setSourceBsoID(java.lang.String)
     */
    public void setSourceBsoID(String sourceBsoID)
    {
        materialSplitMap.setSourceBsoID(sourceBsoID);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setUpdateFlag(java.lang.String)
     */
    public void setUpdateFlag(String updateFlag)
    {
        materialSplitMap.setUpdateFlag(updateFlag);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getOwner()
     */
    public String getOwner()
    {
        return materialSplitMap.getOwner();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setOwner(java.lang.String)
     */
    public void setOwner(String userID)
    {
        materialSplitMap.setOwner(userID);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getRootFlag()
     */
    public boolean getRootFlag()
    {
        return materialSplitMap.getRootFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setRootFlag(boolean)
     */
    public void setRootFlag(boolean rootFlag)
    {
        materialSplitMap.setRootFlag(rootFlag);        
    }
}
