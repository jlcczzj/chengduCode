/**
 * 生成程序ShortCutRouteInfo.java    1.0    2012-1-17
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.model;

import java.util.HashMap;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public class ShortCutRouteInfo extends BaseValueInfo implements ShortCutRouteIfc
{
    private static final long serialVersionUID = 1L;
    //用户ID
    private String userID;
    //快捷键
    private String shortKey;
    //路线串
    private String routeStr;
    
    /**
     * 构造函数
     */
    public ShortCutRouteInfo()
    {

    }
    
    /**
     * 得到业务类名
     * @return String ShortCutRoute
     */
    public String getBsoName()
    {
        return "consShortCutRoute";
    }
    
    /**
     * 获得用户ID
     * @return String 用户ID
     */
    public String getUserID()
    {
        return this.userID;
    }

    /**
     * 设置用户ID
     * @param userName 用户ID
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    
    /**
     * 获得定义的快捷键
     * @return String 快捷键
     */
    public String getShortKey()
    {
        return this.shortKey;
    }

    /**
     * 设置快捷键
     * @param shortKey 快捷键
     */
    public void setShortKey(String shortKey)
    {
        this.shortKey = shortKey;
    }
    
    /**
     * 获得定义的路线串
     * @return String 路线串
     */
    public String getRouteStr()
    {
        return this.routeStr;
    }

    /**
     * 设置路线串
     * @param routeStr 路线串
     */
    public void setRouteStr(String routeStr)
    {
        this.routeStr = routeStr;
    }
    
}
