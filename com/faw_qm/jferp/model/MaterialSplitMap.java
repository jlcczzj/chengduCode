/**
 * 生成程序MaterialSplitMap.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

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
public class MaterialSplitMap implements ObjectMappable
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
    
    private String rCode;
    private String materialSplitType;
    private String partType;
    
    private Timestamp partModifyTime;

    private String partVersion;

    private String producedBy;

    private String route;

    private String routeCode;

    private boolean splited;

    private String state;

    private int status;
    
    private boolean rootFlag;
    
    private boolean virtualFlag=false;
    
    private boolean isMoreRoute;
    
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
    public String getRCode()
    {
        return rCode;
    }
    public String getMaterialSplitType()
    {
        return materialSplitType;
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
    public void setRCode(String rCode)
    {
        this.rCode = rCode;
    }
    public void setMaterialSplitType(String materialSplitType)
    {
        this.materialSplitType = materialSplitType;
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
    
    /**
     * 设置虚拟物料标识
     * @param flag 虚拟物料标识
     */
    public void setVirtualFlag(boolean flag)
    {
    	this.virtualFlag=flag;
    }
    
    /**
     * 获取虚拟物料标识
     * @return 虚拟物料标识
     */
    public boolean getVirtualFlag()
    {
    	return this.virtualFlag;
    }
    
    /**
     * 获取多路线标识
     * @return 多路线标识
     */
    public boolean getIsMoreRoute()
    {
    	return this.isMoreRoute;
    }
    
    /**
     * 设置多路线标识
     * @param flag 多路线标识
     */
    public void setIsMoreRoute(boolean flag)
    {
    	this.isMoreRoute=flag;
    }
}
