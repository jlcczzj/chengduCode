package com.faw_qm.technics.consroute.ejb.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.ShortCutRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.util.EJBServiceHelper;

public abstract class ShortCutRouteEJB extends BsoReferenceEJB
{
	/**
     * 获得用户名
     * @return String 用户名
     */
    public abstract String getUserID();
    /**
     * 设置用户名
     * @param userName 用户名
     */
    public abstract void setUserID(String userID);

    
    /**
     * 获得定义的快捷键
     * @return String 快捷键
     */
    public abstract String getShortKey();

    /**
     * 设置快捷键
     * @param shortKey 快捷键
     */
    public abstract void setShortKey(String shortKey);
    
    /**
     * 获得定义的路线串
     * @return String 路线串
     */
    public abstract String getRouteStr();

    /**
     * 设置路线串
     * @param routeStr 路线串
     */
    public abstract void setRouteStr(String routeStr);
    
    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consShortCutRoute";
    }

    /**
     * 获得值对象。
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
    	ShortCutRouteInfo info = new ShortCutRouteInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @throws QMException 
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        ShortCutRouteInfo routeInfo = (ShortCutRouteInfo)info;
        routeInfo.setUserID(this.getUserID());
        routeInfo.setShortKey(this.getShortKey());
        routeInfo.setRouteStr(this.getRouteStr());
    }

    /**
     * 根据值对象进行持久化。
     * @throws CreateException 
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        ShortCutRouteInfo routeInfo = (ShortCutRouteInfo)info;
        this.setUserID(routeInfo.getUserID());  
        this.setShortKey(routeInfo.getShortKey());
        this.setRouteStr(routeInfo.getRouteStr());
    }

    /**
     * 根据值对象进行更新。
     * @throws QMException 
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        ShortCutRouteInfo routeInfo = (ShortCutRouteInfo)info;
        this.setUserID(routeInfo.getUserID());
        this.setShortKey(routeInfo.getShortKey());
        this.setRouteStr(routeInfo.getRouteStr());
    }
}
