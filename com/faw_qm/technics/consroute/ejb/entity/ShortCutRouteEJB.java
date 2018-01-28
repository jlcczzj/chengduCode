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
     * ����û���
     * @return String �û���
     */
    public abstract String getUserID();
    /**
     * �����û���
     * @param userName �û���
     */
    public abstract void setUserID(String userID);

    
    /**
     * ��ö���Ŀ�ݼ�
     * @return String ��ݼ�
     */
    public abstract String getShortKey();

    /**
     * ���ÿ�ݼ�
     * @param shortKey ��ݼ�
     */
    public abstract void setShortKey(String shortKey);
    
    /**
     * ��ö����·�ߴ�
     * @return String ·�ߴ�
     */
    public abstract String getRouteStr();

    /**
     * ����·�ߴ�
     * @param routeStr ·�ߴ�
     */
    public abstract void setRouteStr(String routeStr);
    
    /**
     * ���ҵ���������
     */
    public String getBsoName()
    {
        return "consShortCutRoute";
    }

    /**
     * ���ֵ����
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
    	ShortCutRouteInfo info = new ShortCutRouteInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
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
     * ����ֵ������г־û���
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
     * ����ֵ������и��¡�
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
