/**
 * 生成程序InterimMaterialStructureMap.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class InterimMaterialStructureMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String parentPartNumber;

    private String parentPartVersion;

    private String parentNumber;

    private String childNumber;

    private float quantity;

    private String levelNumber;

    private String defaultUnit;

    private boolean optionFlag;
    
    private String sourceBsoID;

    private String updateFlag;
    
    private String userID;
    
    /**
     * @return 返回 childNumber。
     */
    public String getChildNumber()
    {
        return childNumber;
    }

    /**
     * @param childNumber 要设置的 childNumber。
     */
    public void setChildNumber(String childNumber)
    {
        this.childNumber = childNumber;
    }

    /**
     * @return 返回 defaultUnit。
     */
    public String getDefaultUnit()
    {
        return defaultUnit;
    }

    /**
     * @param defaultUnit 要设置的 defaultUnit。
     */
    public void setDefaultUnit(String defaultUnit)
    {
        this.defaultUnit = defaultUnit;
    }

    /**
     * @return 返回 levelNumber。
     */
    public String getLevelNumber()
    {
        return levelNumber;
    }

    /**
     * @param levelNumber 要设置的 levelNumber。
     */
    public void setLevelNumber(String levelNumber)
    {
        this.levelNumber = levelNumber;
    }

    /**
     * @return 返回 optionFlag。
     */
    public boolean getOptionFlag()
    {
        return optionFlag;
    }

    /**
     * @param optionFlag 要设置的 optionFlag。
     */
    public void setOptionFlag(boolean optionFlag)
    {
        this.optionFlag = optionFlag;
    }

    /**
     * @return 返回 parentNumber。
     */
    public String getParentNumber()
    {
        return parentNumber;
    }

    /**
     * @param parentNumber 要设置的 parentNumber。
     */
    public void setParentNumber(String parentNumber)
    {
        this.parentNumber = parentNumber;
    }

    /**
     * @return 返回 parentPartNumber。
     */
    public String getParentPartNumber()
    {
        return parentPartNumber;
    }

    /**
     * @param parentPartNumber 要设置的 parentPartNumber。
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        this.parentPartNumber = parentPartNumber;
    }

    /**
     * @return 返回 parentPartVersion。
     */
    public String getParentPartVersion()
    {
        return parentPartVersion;
    }

    /**
     * @param parentPartVersion 要设置的 parentPartVersion。
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        this.parentPartVersion = parentPartVersion;
    }

    /**
     * @return 返回 quantity。
     */
    public float getQuantity()
    {
        return quantity;
    }

    /**
     * @param quantity 要设置的 quantity。
     */
    public void setQuantity(float quantity)
    {
        this.quantity = quantity;
    }
    
    public String getSourceBsoID()
    {
        return sourceBsoID;
    }

    public String getUpdateFlag()
    {
        return updateFlag;
    }

    public void setSourceBsoID(String sourceBsoID)
    {
        this.sourceBsoID = sourceBsoID;
    }

    public void setUpdateFlag(String updateFlag)
    {
        this.updateFlag = updateFlag;
    }
    
    public String getOwner()
    {
        return userID;
    }

    public void setOwner(String userID)
    {
        this.userID = userID;
    }
}
