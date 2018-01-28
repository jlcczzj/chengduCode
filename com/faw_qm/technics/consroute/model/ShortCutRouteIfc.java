/**
 * 生成程序ShortCutRouteIfc.java    1.0    2012-1-17
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.model;

import java.util.HashMap;

import com.faw_qm.framework.service.BaseValueIfc;



/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public interface ShortCutRouteIfc extends BaseValueIfc
{
    /**
     * 获得用户ID
     * @return String 用户ID
     */
    public String getUserID();
    
    /**
     * 设置用户ID
     * @param userName 用户ID
     */
    public void setUserID(String userID);

    
    /**
     * 获得定义的快捷键
     * @return String 快捷键
     */
    public String getShortKey();

    /**
     * 设置快捷键
     * @param shortKey 快捷键
     */
    public void setShortKey(String shortKey);
    
    /**
     * 获得定义的路线串
     * @return String 路线串
     */
    public String getRouteStr();

    /**
     * 设置路线串
     * @param routeStr 路线串
     */
    public void setRouteStr(String routeStr);
    
    
}
