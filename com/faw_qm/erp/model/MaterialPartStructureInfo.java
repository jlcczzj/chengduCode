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

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "MaterialPartStructure";
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getChildNumber()
     */
    public String getChildNumber()
    {
        return materialPartStructureMap.getChildNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialPartStructureMap.getDefaultUnit();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getLevelNumber()
     */
    public String getLevelNumber()
    {
        return materialPartStructureMap.getLevelNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getOptionFlag()
     */
    public boolean getOptionFlag()
    {
        return materialPartStructureMap.getOptionFlag();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentNumber()
     */
    public String getParentNumber()
    {
        return materialPartStructureMap.getParentNumber();
    }

    /* （非 Javadoc）
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
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getParentPartVersion()
    {
        return materialPartStructureMap.getParentPartVersion();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getQuantity()
     */
    public float getQuantity()
    {
        return materialPartStructureMap.getQuantity();
    }

    /* （非 Javadoc）
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
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setDefaultUnit(String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialPartStructureMap.setDefaultUnit(defaultUnit);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setLevelNumber(String)
     */
    public void setLevelNumber(String levelNumber)
    {
        materialPartStructureMap.setLevelNumber(levelNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setOptionFlag(boolean)
     */
    public void setOptionFlag(boolean optionFlag)
    {
        materialPartStructureMap.setOptionFlag(optionFlag);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setParentNumber(String parentNumber)
    {
        materialPartStructureMap.setParentNumber(parentNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        materialPartStructureMap.setParentPartNumber(parentPartNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        materialPartStructureMap.setParentPartVersion(parentPartVersion);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setQuantity(float)
     */
    public void setQuantity(float quantity)
    {
        materialPartStructureMap.setQuantity(quantity);
    }
    
    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return super.getIdentity();
    }
}
