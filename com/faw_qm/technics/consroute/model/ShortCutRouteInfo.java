/**
 * ���ɳ���ShortCutRouteInfo.java    1.0    2012-1-17
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.model;

import java.util.HashMap;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public class ShortCutRouteInfo extends BaseValueInfo implements ShortCutRouteIfc
{
    private static final long serialVersionUID = 1L;
    //�û�ID
    private String userID;
    //��ݼ�
    private String shortKey;
    //·�ߴ�
    private String routeStr;
    
    /**
     * ���캯��
     */
    public ShortCutRouteInfo()
    {

    }
    
    /**
     * �õ�ҵ������
     * @return String ShortCutRoute
     */
    public String getBsoName()
    {
        return "consShortCutRoute";
    }
    
    /**
     * ����û�ID
     * @return String �û�ID
     */
    public String getUserID()
    {
        return this.userID;
    }

    /**
     * �����û�ID
     * @param userName �û�ID
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    
    /**
     * ��ö���Ŀ�ݼ�
     * @return String ��ݼ�
     */
    public String getShortKey()
    {
        return this.shortKey;
    }

    /**
     * ���ÿ�ݼ�
     * @param shortKey ��ݼ�
     */
    public void setShortKey(String shortKey)
    {
        this.shortKey = shortKey;
    }
    
    /**
     * ��ö����·�ߴ�
     * @return String ·�ߴ�
     */
    public String getRouteStr()
    {
        return this.routeStr;
    }

    /**
     * ����·�ߴ�
     * @param routeStr ·�ߴ�
     */
    public void setRouteStr(String routeStr)
    {
        this.routeStr = routeStr;
    }
    
}
