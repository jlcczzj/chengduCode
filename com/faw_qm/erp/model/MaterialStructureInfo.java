/**
 * 生成程序MaterialStructureInfo.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
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

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "MaterialStructure";
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getChildNumber()
     */
    public String getChildNumber()
    {
        return materialStructureMap.getChildNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialStructureMap.getDefaultUnit();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getLevelNumber()
     */
    public String getLevelNumber()
    {
        return materialStructureMap.getLevelNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getOptionFlag()
     */
    public boolean getOptionFlag()
    {
        return materialStructureMap.getOptionFlag();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentNumber()
     */
    public String getParentNumber()
    {
        return materialStructureMap.getParentNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getParentPartNumber()
    {
        return materialStructureMap.getParentPartNumber();
    }
    public String getMaterialStructureType()
    {
        return materialStructureMap.getMaterialStructureType();
    }
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getParentPartVersion()
    {
        return materialStructureMap.getParentPartVersion();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getQuantity()
     */
    public float getQuantity()
    {
        return materialStructureMap.getQuantity();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setChildNumber(java.lang.String)
     */
    public void setChildNumber(String childNumber)
    {
        materialStructureMap.setChildNumber(childNumber);
    }
    public void setMaterialStructureType(String materialStructureType)
    {
        materialStructureMap.setMaterialStructureType(materialStructureType);
    }
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setDefaultUnit(String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialStructureMap.setDefaultUnit(defaultUnit);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setLevelNumber(String)
     */
    public void setLevelNumber(String levelNumber)
    {
        materialStructureMap.setLevelNumber(levelNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setOptionFlag(boolean)
     */
    public void setOptionFlag(boolean optionFlag)
    {
        materialStructureMap.setOptionFlag(optionFlag);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setParentNumber(String parentNumber)
    {
        materialStructureMap.setParentNumber(parentNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        materialStructureMap.setParentPartNumber(parentPartNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        materialStructureMap.setParentPartVersion(parentPartVersion);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setQuantity(float)
     */
    public void setQuantity(float quantity)
    {
        materialStructureMap.setQuantity(quantity);
    }
    
    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return super.getIdentity();
    }
}
