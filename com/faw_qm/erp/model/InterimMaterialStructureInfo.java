/**
 * 生成程序InterimMaterialStructureInfo.java	1.0              2007-9-25
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

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "InterimMaterialStructure";
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

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#getSourceBsoID()
     */
    public String getSourceBsoID()
    {
        return materialStructureMap.getSourceBsoID();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#getUpdateFlag()
     */
    public String getUpdateFlag()
    {
        return materialStructureMap.getUpdateFlag();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#setSourceBsoID(java.lang.String)
     */
    public void setSourceBsoID(String sourceBsoID)
    {
        materialStructureMap.setSourceBsoID(sourceBsoID);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#setUpdateFlag(java.lang.String)
     */
    public void setUpdateFlag(String updateFlag)
    {
        materialStructureMap.setUpdateFlag(updateFlag);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#getOwner()
     */
    public String getOwner()
    {
        return materialStructureMap.getOwner();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.InterimMaterialStructureIfc#setOwner(java.lang.String)
     */
    public void setOwner(String userID)
    {
        materialStructureMap.setOwner(userID);
    }
}
