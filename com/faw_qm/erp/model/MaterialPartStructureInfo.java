/**
 * ���ɳ���MaterialStructureInfo.java	1.0              2007-9-25
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
public class MaterialPartStructureInfo extends BaseValueInfo implements MaterialPartStructureIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private MaterialPartStructureMap materialPartStructureMap;

    /**
     * 
     */
    public MaterialPartStructureInfo()
    {
        super();
        materialPartStructureMap = new MaterialPartStructureMap();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "MaterialPartStructure";
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getChildNumber()
     */
    public String getChildNumber()
    {
        return materialPartStructureMap.getChildNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialPartStructureMap.getDefaultUnit();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getLevelNumber()
     */
    public String getLevelNumber()
    {
        return materialPartStructureMap.getLevelNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getOptionFlag()
     */
    public boolean getOptionFlag()
    {
        return materialPartStructureMap.getOptionFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentNumber()
     */
    public String getParentNumber()
    {
        return materialPartStructureMap.getParentNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getParentPartNumber()
    {
        return materialPartStructureMap.getParentPartNumber();
    }
    public String getMaterialStructureType()
    {
        return materialPartStructureMap.getMaterialStructureType();
    }
    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getParentPartVersion()
    {
        return materialPartStructureMap.getParentPartVersion();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getQuantity()
     */
    public float getQuantity()
    {
        return materialPartStructureMap.getQuantity();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setChildNumber(java.lang.String)
     */
    public void setChildNumber(String childNumber)
    {
        materialPartStructureMap.setChildNumber(childNumber);
    }
    public void setMaterialStructureType(String materialStructureType)
    {
        materialPartStructureMap.setMaterialStructureType(materialStructureType);
    }
    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setDefaultUnit(String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialPartStructureMap.setDefaultUnit(defaultUnit);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setLevelNumber(String)
     */
    public void setLevelNumber(String levelNumber)
    {
        materialPartStructureMap.setLevelNumber(levelNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setOptionFlag(boolean)
     */
    public void setOptionFlag(boolean optionFlag)
    {
        materialPartStructureMap.setOptionFlag(optionFlag);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setParentNumber(String parentNumber)
    {
        materialPartStructureMap.setParentNumber(parentNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        materialPartStructureMap.setParentPartNumber(parentPartNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        materialPartStructureMap.setParentPartVersion(parentPartVersion);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setQuantity(float)
     */
    public void setQuantity(float quantity)
    {
        materialPartStructureMap.setQuantity(quantity);
    }
    
    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return super.getIdentity();
    }
}
