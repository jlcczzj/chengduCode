/**
 * 生成程序InterimMaterialSplitMap.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.model;

import java.sql.Timestamp;
import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class InterimMaterialSplitMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean colorFlag;

    private String defaultUnit;

    private String materialNumber;

    private String optionCode;

    private String partName;

    private String partNumber;

    private String partType;

    private Timestamp partModifyTime;

    private String partVersion;

    private String producedBy;

    private String route;

    private String routeCode;

    private boolean splited;

    private String state;

    private int status;

    private String sourceBsoID;

    private String updateFlag;

    private String userID;
    
    private boolean rootFlag;

    public boolean getColorFlag()
    {
        return colorFlag;
    }

    public String getDefaultUnit()
    {
        return defaultUnit;
    }

    public String getMaterialNumber()
    {
        return materialNumber;
    }

    public String getOptionCode()
    {
        return optionCode;
    }

    public String getPartName()
    {
        return partName;
    }

    public String getPartNumber()
    {
        return partNumber;
    }

    public String getPartType()
    {
        return partType;
    }

    /**
     * 获取零件更新时间。
     * @return 零件更新时间。
     */
    public Timestamp getPartModifyTime()
    {
        return partModifyTime;
    }

    public String getPartVersion()
    {
        return partVersion;
    }

    public String getProducedBy()
    {
        return producedBy;
    }

    public String getRoute()
    {
        return route;
    }

    public String getRouteCode()
    {
        return routeCode;
    }

    public boolean getSplited()
    {
        return splited;
    }

    public String getState()
    {
        return state;
    }

    public int getStatus()
    {
        return status;
    }

    public void setColorFlag(boolean colorFlag)
    {
        this.colorFlag = colorFlag;
    }

    public void setDefaultUnit(String defaultUnit)
    {
        this.defaultUnit = defaultUnit;
    }

    public void setMaterialNumber(String materialNumber)
    {
        this.materialNumber = materialNumber;
    }

    public void setOptionCode(String optionCode)
    {
        this.optionCode = optionCode;
    }

    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    public void setPartType(String partType)
    {
        this.partType = partType;
    }

    /**
     * 设置零件更新时间。
     * @param partModifyTime 零件更新时间。
     */
    public void setPartModifyTime(Timestamp partModifyTime)
    {
        this.partModifyTime = partModifyTime;
    }

    public void setPartVersion(String partVersion)
    {
        this.partVersion = partVersion;
    }

    public void setProducedBy(String producedBy)
    {
        this.producedBy = producedBy;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    public void setSplited(boolean splited)
    {
        this.splited = splited;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setStatus(int status)
    {
        this.status = status;
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
    
    /**
     * 设置顶级物料标识，1位数字：0为否、1为是。
     * @param colorFlag 顶级物料标识。
     */
    public void setRootFlag(boolean rootFlag)
    {
        this.rootFlag = rootFlag;
    }

    /**
     * 获取顶级物料标识，1位数字：0为否、1为是。
     * @return 顶级物料标识。
     */
    public boolean getRootFlag()
    {
        return rootFlag;
    }
}
