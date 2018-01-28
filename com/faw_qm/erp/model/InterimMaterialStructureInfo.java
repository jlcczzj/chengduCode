/**
 * ���ɳ���InterimMaterialStructureInfo.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class InterimMaterialStructureInfo extends BaseValueInfo implements
        InterimMaterialStructureIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private InterimMaterialStructureMap materialStructureMap;

    /**
     * 
     */
    public InterimMaterialStructureInfo()
    {
        super();
        materialStructureMap = new InterimMaterialStructureMap();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "InterimMaterialStructure";
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getChildNumber()
     */
    public String getChildNumber()
    {
        return materialStructureMap.getChildNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialStructureMap.getDefaultUnit();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getLevelNumber()
     */
    public String getLevelNumber()
    {
        return materialStructureMap.getLevelNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getOptionFlag()
     */
    public boolean getOptionFlag()
    {
        return materialStructureMap.getOptionFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentNumber()
     */
    public String getParentNumber()
    {
        return materialStructureMap.getParentNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getParentPartNumber()
    {
        return materialStructureMap.getParentPartNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getParentPartVersion()
    {
        return materialStructureMap.getParentPartVersion();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getQuantity()
     */
    public float getQuantity()
    {
        return materialStructureMap.getQuantity();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setChildNumber(java.lang.String)
     */
    public void setChildNumber(String childNumber)
    {
        materialStructureMap.setChildNumber(childNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setDefaultUnit(String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialStructureMap.setDefaultUnit(defaultUnit);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setLevelNumber(String)
     */
    public void setLevelNumber(String levelNumber)
    {
        materialStructureMap.setLevelNumber(levelNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setOptionFlag(boolean)
     */
    public void setOptionFlag(boolean optionFlag)
    {
        materialStructureMap.setOptionFlag(optionFlag);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setParentNumber(String parentNumber)
    {
        materialStructureMap.setParentNumber(parentNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        materialStructureMap.setParentPartNumber(parentPartNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        materialStructureMap.setParentPartVersion(parentPartVersion);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setQuantity(float)
     */
    public void setQuantity(float quantity)
    {
        materialStructureMap.setQuantity(quantity);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return super.getIdentity();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#getSourceBsoID()
     */
    public String getSourceBsoID()
    {
        return materialStructureMap.getSourceBsoID();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#getUpdateFlag()
     */
    public String getUpdateFlag()
    {
        return materialStructureMap.getUpdateFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#setSourceBsoID(java.lang.String)
     */
    public void setSourceBsoID(String sourceBsoID)
    {
        materialStructureMap.setSourceBsoID(sourceBsoID);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#setUpdateFlag(java.lang.String)
     */
    public void setUpdateFlag(String updateFlag)
    {
        materialStructureMap.setUpdateFlag(updateFlag);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#getOwner()
     */
    public String getOwner()
    {
        return materialStructureMap.getOwner();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#setOwner(java.lang.String)
     */
    public void setOwner(String userID)
    {
        materialStructureMap.setOwner(userID);
    }
}
