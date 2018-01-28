/**
 * ���ɳ���ShortCutRouteIfc.java    1.0    2012-1-17
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.model;

import java.util.HashMap;

import com.faw_qm.framework.service.BaseValueIfc;



/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public interface ShortCutRouteIfc extends BaseValueIfc
{
    /**
     * ����û�ID
     * @return String �û�ID
     */
    public String getUserID();
    
    /**
     * �����û�ID
     * @param userName �û�ID
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
