/**
 * ���ɳ���MaterialStructureInfo.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class MaterialStructureInfo extends BaseValueInfo implements
        MaterialStructureIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
 
    private MaterialStructureMap materialStructureMap;

    /**
     * 
     */
    public MaterialStructureInfo()
    {
        super();
        materialStructureMap = new MaterialStructureMap();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "JFMaterialStructure";
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getChildNumber()
     */
    public String getChildNumber()
    {
        return materialStructureMap.getChildNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialStructureMap.getDefaultUnit();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getLevelNumber()
     */
    public String getLevelNumber()
    {
        return materialStructureMap.getLevelNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getOptionFlag()
     */
    public boolean getOptionFlag()
    {
        return materialStructureMap.getOptionFlag();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getParentNumber()
     */
    public String getParentNumber()
    {
        return materialStructureMap.getParentNumber();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getParentPartNumber()
    {
        return materialStructureMap.getParentPartNumber();
    }
    public String getMaterialStructureType()
    {
        return materialStructureMap.getMaterialStructureType();
    }
    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getParentPartVersion()
    {
        return materialStructureMap.getParentPartVersion();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getQuantity()
     */
    public float getQuantity()
    {
        return materialStructureMap.getQuantity();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setChildNumber(java.lang.String)
     */
    public void setChildNumber(String childNumber)
    {
        materialStructureMap.setChildNumber(childNumber);
    }
    public void setMaterialStructureType(String materialStructureType)
    {
        materialStructureMap.setMaterialStructureType(materialStructureType);
    }
    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setDefaultUnit(String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialStructureMap.setDefaultUnit(defaultUnit);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setLevelNumber(String)
     */
    public void setLevelNumber(String levelNumber)
    {
        materialStructureMap.setLevelNumber(levelNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setOptionFlag(boolean)
     */
    public void setOptionFlag(boolean optionFlag)
    {
        materialStructureMap.setOptionFlag(optionFlag);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setParentNumber(String parentNumber)
    {
        materialStructureMap.setParentNumber(parentNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        materialStructureMap.setParentPartNumber(parentPartNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        materialStructureMap.setParentPartVersion(parentPartVersion);
    }
    /**
     * �������顣
     * @param subGroup ���顣
     */
    public void setSubGroup(String subGroup)
    {
    	materialStructureMap.setSubGroup(subGroup);
    }

    /**
     * ��ȡ���顣
     * @return ���顣
     */
    public String getSubGroup(){
    	 return materialStructureMap.getSubGroup();
    }
    /**
     * ����BOM����š�
     * @param BOMLine BOM����š�
     */
    public void setBomLine(String BOMLine){
    	materialStructureMap.setBomLine(BOMLine);
    }

    /**
     * ��ȡBOM����š�
     * @return BOM����š�
     */
    public String getBomLine(){
    	 return materialStructureMap.getBomLine();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setQuantity(float)
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
    
}
