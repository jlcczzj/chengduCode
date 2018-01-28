/**
 * ���ɳ���FilterPartInfo.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ���˷������ʵ��</p>
 * <p>Description:���˷������ʵ�� </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class FilterPartInfo extends BaseValueInfo implements FilterPartIfc
{
    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return "FilterPart:" + partNumber + versionValue + state;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String partNumber;

    private String versionValue;

    private String state;

    private String noticeNumber;

    private String noticeType;
    
    private String route;

    public String getNoticeType()
    {
        return noticeType;
    }

    public String getNoticeNumber()
    {
        return noticeNumber;
    }

    public String getState()
    {
        return state;
    }

    public String getVersionValue()
    {
        return versionValue;
    }

    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public void setNoticeNumber(String noticeNumber)
    {
        this.noticeNumber = noticeNumber;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setVersionValue(String versionValue)
    {
        this.versionValue = versionValue;
    }

    public String getPartNumber()
    {
        return partNumber;
    }
    
    public String getRoute()
    {
    	return route;
    }
    
    public void setRoute(String rout)
    {
    	route=rout;
    }

    public FilterPartInfo()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFFilterPart";
    }
}
