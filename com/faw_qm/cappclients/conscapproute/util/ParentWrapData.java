/**
 * ���ɳ���ParentWrapData.java    1.0    2012-1-30
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Image;
import java.util.HashMap;

import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p>Title: ��</p>
 * <p>�������ݷ�װ��: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public class ParentWrapData
{
    //����ID
    private String parentID;
    //�㼶
    private String level;
    //�������
    String parentNum;
    //��������
    String parentName;
    //�����汾
    private String versionValue;
    //ʹ������
    float count;
    //·�߱���
    String routeListNum;
    //·�ߴ�
    String routeStr;
    String expand ;
    
    

    /**
     * ParentWrapData��Ĺ��췽��
     */
    public ParentWrapData()
    {
        super();
    }

    /**
     * ��ø���ID
     * @return String ����ID
     */
    public String getParentID()
    {
        return this.parentID;
    }

    /**
     * ���ø���ID
     * @param parentID String ����ID
     */
    public void setParentID(String parentID)
    {
        this.parentID = parentID;
    }

    /**
     * ��ò㼶
     * @return int �㼶
     */
    public String getLevel()
    {
        return this.level;
    }

    /**
     * ���ò㼶
     * @param level int �㼶
     */
    public void setLevel(String level)
    {
        this.level = level;
    }

    /**
     * ��ø������
     * @return String �������
     */
    public String getParentNum()
    {
        return this.parentNum;
    }

    /**
     * ���ø������
     * @param parentNum String �������
     */
    public void setParentNum(String parentNum)
    {
        this.parentNum = parentNum;
    }

    /**
     * ��ø�������
     * @return String ��������
     */
    public String getParentName()
    {
        return this.parentName;
    }

    /**
     * ���ø�������
     * @param secondStr String ��������
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * ��ø����汾
     * @return String �����汾
     */
    public String getVersionValue()
    {
        return this.versionValue;
    }

    /**
     * ���ø����汾
     * @param description String �����汾
     */
    public void setVersionValue(String versionValue)
    {
        this.versionValue = versionValue;
    }

    /**
     * ���ʹ������
     * @return int ʹ������
     */
    public float getCount()
    {
        return count;
    }
    
    
    /**
     * ����ʹ������
     * @return int ʹ������
     */
    public void setCount(float count)
    {
        this.count = count;
    }
    
    /**
     * ���·�߱���
     * @return String ·�߱���
     */
    public String getRouteListNum()
    {
        return this.routeListNum;
    }

    /**
     * ����·�߱���
     * @param description String ·�߱���
     */
    public void setRouteListNum(String routeListNum)
    {
        this.routeListNum = routeListNum;
    }
    
    
    /**
     * ���·�ߴ�
     * @return String ·�ߴ�
     */
    public String getRouteStr()
    {
        return this.routeStr;
    }

    /**
     * ����·�ߴ�
     * @param description String ·�ߴ�
     */
    public void setRouteStr(String routeStr)
    {
        this.routeStr = routeStr;
    }
    
    /**
     * ���ͼ��
     * @return Image ͼ��
     */
    public String getExpand()
    {
        return this.expand;
    }

    /**
     * ����ͼ��
     * @param image Image ͼ��
     */
    public void setExpand(String expand)
    {
        this.expand = expand;
    }
}
