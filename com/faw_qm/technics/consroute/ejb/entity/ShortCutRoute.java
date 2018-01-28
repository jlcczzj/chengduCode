package com.faw_qm.technics.consroute.ejb.entity;

import java.util.HashMap;

import com.faw_qm.framework.service.BsoReference;

public interface ShortCutRoute extends BsoReference
{
	/**
     * ����û���
     * @return String �û���
     */
    public String getUserID();
    /**
     * �����û���
     * @param userName �û���
     */
    public void setUserID(String userID);

    /**
     * ��ö���Ŀ�ݼ�
     * @return String ��ݼ�
     */
    public String getShortKey();

    /**
     * ���ÿ�ݼ�
     * @param shortKey ��ݼ�
     */
    public void setShortKey(String shortKey);
    
    /**
     * ��ö����·�ߴ�
     * @return String ·�ߴ�
     */
    public String getRouteStr();

    /**
     * ����·�ߴ�
     * @param routeStr ·�ߴ�
     */
    public void setRouteStr(String routeStr);
}
