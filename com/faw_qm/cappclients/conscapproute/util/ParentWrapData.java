/**
 * 生成程序ParentWrapData.java    1.0    2012-1-30
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Image;
import java.util.HashMap;

import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p>Title: 。</p>
 * <p>父件数据封装类: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public class ParentWrapData
{
    //父件ID
    private String parentID;
    //层级
    private String level;
    //父件编号
    String parentNum;
    //父件名称
    String parentName;
    //父件版本
    private String versionValue;
    //使用数量
    float count;
    //路线表编号
    String routeListNum;
    //路线串
    String routeStr;
    String expand ;
    
    

    /**
     * ParentWrapData类的构造方法
     */
    public ParentWrapData()
    {
        super();
    }

    /**
     * 获得父件ID
     * @return String 父件ID
     */
    public String getParentID()
    {
        return this.parentID;
    }

    /**
     * 设置父件ID
     * @param parentID String 父件ID
     */
    public void setParentID(String parentID)
    {
        this.parentID = parentID;
    }

    /**
     * 获得层级
     * @return int 层级
     */
    public String getLevel()
    {
        return this.level;
    }

    /**
     * 设置层级
     * @param level int 层级
     */
    public void setLevel(String level)
    {
        this.level = level;
    }

    /**
     * 获得父件编号
     * @return String 父件编号
     */
    public String getParentNum()
    {
        return this.parentNum;
    }

    /**
     * 设置父件编号
     * @param parentNum String 父件编号
     */
    public void setParentNum(String parentNum)
    {
        this.parentNum = parentNum;
    }

    /**
     * 获得父件名称
     * @return String 父件名称
     */
    public String getParentName()
    {
        return this.parentName;
    }

    /**
     * 设置父件名称
     * @param secondStr String 父件名称
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * 获得父件版本
     * @return String 父件版本
     */
    public String getVersionValue()
    {
        return this.versionValue;
    }

    /**
     * 设置父件版本
     * @param description String 父件版本
     */
    public void setVersionValue(String versionValue)
    {
        this.versionValue = versionValue;
    }

    /**
     * 获得使用数量
     * @return int 使用数量
     */
    public float getCount()
    {
        return count;
    }
    
    
    /**
     * 设置使用数量
     * @return int 使用数量
     */
    public void setCount(float count)
    {
        this.count = count;
    }
    
    /**
     * 获得路线表编号
     * @return String 路线表编号
     */
    public String getRouteListNum()
    {
        return this.routeListNum;
    }

    /**
     * 设置路线表编号
     * @param description String 路线表编号
     */
    public void setRouteListNum(String routeListNum)
    {
        this.routeListNum = routeListNum;
    }
    
    
    /**
     * 获得路线串
     * @return String 路线串
     */
    public String getRouteStr()
    {
        return this.routeStr;
    }

    /**
     * 设置路线串
     * @param description String 路线串
     */
    public void setRouteStr(String routeStr)
    {
        this.routeStr = routeStr;
    }
    
    /**
     * 获得图标
     * @return Image 图标
     */
    public String getExpand()
    {
        return this.expand;
    }

    /**
     * 设置图标
     * @param image Image 图标
     */
    public void setExpand(String expand)
    {
        this.expand = expand;
    }
}
