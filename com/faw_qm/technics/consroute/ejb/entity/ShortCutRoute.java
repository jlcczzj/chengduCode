package com.faw_qm.technics.consroute.ejb.entity;

import java.util.HashMap;

import com.faw_qm.framework.service.BsoReference;

public interface ShortCutRoute extends BsoReference
{
	/**
     * 获得用户名
     * @return String 用户名
     */
    public String getUserID();
    /**
     * 设置用户名
     * @param userName 用户名
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
