/**
 * 生成程序MaterialStructureMap.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 增加添加父件和子组功能 刘家坤 2017-3-3
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class MaterialStructureMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String parentPartNumber;

    private String parentPartVersion;

    private String parentNumber;
    private String materialStructureType;

    private String childNumber;

    private float quantity;

    private String levelNumber;

    private String defaultUnit;

    private boolean optionFlag;

    private String subGroup;

    private String BOMLine;
    //CCBegin SS1
    private float bquantity;

    private String beforeMaterial;

    //CCEnd SS1
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
    public void setMaterialStructureType(String materialStructureType)
    {
        this.materialStructureType = materialStructureType;
    }

    /**
     * @return 返回 parentPartVersion。
     */
    public String getMaterialStructureType()
    {
        return materialStructureType;
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
    /**
     * 设置子组。
     * @param subGroup 子组。
     */
    public void setSubGroup(String subGroup)
    {
    	this.subGroup=subGroup;
    }

    /**
     * 获取子组。
     * @return 子组。
     */
    public String getSubGroup(){
    	 return subGroup;
    }
    /**
     * 设置BOM行项号。
     * @param BOMLine BOM行项号。
     */
    public void setBomLine(String BOMLine){
    	this.BOMLine=BOMLine;
    }

    /**
     * 获取BOM行项号。
     * @return BOM行项号。
     */
    public String getBomLine(){
    	return BOMLine;
    }
    //CCBegin SS1
    /**
     * 获取替换前零件号。
     * @return 替换前零件号。
     */
 
    public String getBeforeMaterial(){
    	 return beforeMaterial;
    }
    /**
     * 设置替换前零件号。
     * @param flag 替换前零件号。
     */
    public  void setBeforeMaterial(String beforeMaterial){
    	this.beforeMaterial = beforeMaterial;
    }

    /**
     * 获取替换前数量。
     * @return 替换前数量。
     */
    
    public float getBeforeQuantity(){
    	return bquantity;
    }
    /**
     * 设置替换前数量。
     * @param flag 替换前数量。
     */
    public void setBeforeQuantity(float bquantity){
    	this.bquantity = bquantity;
    }
    //CCEnd SS1
   
}
