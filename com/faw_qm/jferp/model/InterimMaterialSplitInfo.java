/**
 * 生成程序InterimMaterialSplitInfo.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

import java.sql.Timestamp;
import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
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

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "JFInterimMaterialSplit";
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getColorFlag()
     */
    public boolean getColorFlag()
    {
        return materialSplitMap.getColorFlag();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getDefaultUnit()
     */
    public String getDefaultUnit()
    {
        return materialSplitMap.getDefaultUnit();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getMaterial()
     */
    public String getMaterialNumber()
    {
        return materialSplitMap.getMaterialNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getOptionCode()
     */
    public String getOptionCode()
    {
        return materialSplitMap.getOptionCode();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartName()
     */
    public String getPartName()
    {
        return materialSplitMap.getPartName();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartNumber()
     */
    public String getPartNumber()
    {
        return materialSplitMap.getPartNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartType()
     */
    public String getPartType()
    {
        return materialSplitMap.getPartType();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartModifyTime()
     */
    public Timestamp getPartModifyTime()
    {
        return materialSplitMap.getPartModifyTime();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getPartVersion()
     */
    public String getPartVersion()
    {
        return materialSplitMap.getPartVersion();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getProducedBy()
     */
    public String getProducedBy()
    {
        return materialSplitMap.getProducedBy();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getRoute()
     */
    public String getRoute()
    {
        return materialSplitMap.getRoute();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getRouteCode()
     */
    public String getRouteCode()
    {
        return materialSplitMap.getRouteCode();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getSplited()
     */
    public boolean getSplited()
    {
        return materialSplitMap.getSplited();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getState()
     */
    public String getState()
    {
        return materialSplitMap.getState();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#getStatus()
     */
    public int getStatus()
    {
        return materialSplitMap.getStatus();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setColorFlag(boolean)
     */
    public void setColorFlag(boolean colorFlag)
    {
        materialSplitMap.setColorFlag(colorFlag);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setDefaultUnit(java.lang.String)
     */
    public void setDefaultUnit(String defaultUnit)
    {
        materialSplitMap.setDefaultUnit(defaultUnit);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setMaterialNumber(java.lang.String)
     */
    public void setMaterialNumber(String materialNumber)
    {
        materialSplitMap.setMaterialNumber(materialNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setOptionCode(java.lang.String)
     */
    public void setOptionCode(String optionCode)
    {
        materialSplitMap.setOptionCode(optionCode);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartName(java.lang.String)
     */
    public void setPartName(String partName)
    {
        materialSplitMap.setPartName(partName);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartNumber(java.lang.String)
     */
    public void setPartNumber(String partNumber)
    {
        materialSplitMap.setPartNumber(partNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartType(java.lang.String)
     */
    public void setPartType(String partType)
    {
        materialSplitMap.setPartType(partType);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartVersion(java.lang.String)
     */
    public void setPartVersion(String partVersion)
    {
        materialSplitMap.setPartVersion(partVersion);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setProducedBy(java.lang.String)
     */
    public void setProducedBy(String producedBy)
    {
        materialSplitMap.setProducedBy(producedBy);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setRoute(java.lang.String)
     */
    public void setRoute(String route)
    {
        materialSplitMap.setRoute(route);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setRouteCode(java.lang.String)
     */
    public void setRouteCode(String routeCode)
    {
        materialSplitMap.setRouteCode(routeCode);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setSplited(boolean)
     */
    public void setSplited(boolean splited)
    {
        materialSplitMap.setSplited(splited);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setState(java.lang.String)
     */
    public void setState(String state)
    {
        materialSplitMap.setState(state);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setStatus(int)
     */
    public void setStatus(int status)
    {
        materialSplitMap.setStatus(status);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        if(getBsoID() == null)
            return getBsoName();
        else
            return getMaterialNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.MaterialSplitIfc#setPartModifyTime(java.sql.Timestamp)
     */
    public void setPartModifyTime(Timestamp partModifyTime)
    {
        materialSplitMap.setPartModifyTime(partModifyTime);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getSourceBsoID()
     */
    public String getSourceBsoID()
    {
        return materialSplitMap.getSourceBsoID();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getUpdateFlag()
     */
    public String getUpdateFlag()
    {
        return materialSplitMap.getUpdateFlag();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setSourceBsoID(java.lang.String)
     */
    public void setSourceBsoID(String sourceBsoID)
    {
        materialSplitMap.setSourceBsoID(sourceBsoID);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setUpdateFlag(java.lang.String)
     */
    public void setUpdateFlag(String updateFlag)
    {
        materialSplitMap.setUpdateFlag(updateFlag);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getOwner()
     */
    public String getOwner()
    {
        return materialSplitMap.getOwner();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setOwner(java.lang.String)
     */
    public void setOwner(String userID)
    {
        materialSplitMap.setOwner(userID);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#getRootFlag()
     */
    public boolean getRootFlag()
    {
        return materialSplitMap.getRootFlag();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.jferp.model.InterimMaterialSplitIfc#setRootFlag(boolean)
     */
    public void setRootFlag(boolean rootFlag)
    {
        materialSplitMap.setRootFlag(rootFlag);        
    }
}
