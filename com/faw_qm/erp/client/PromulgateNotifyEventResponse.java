/**
 * ���ɳ���PromulgateNotifyEventResponse.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.client;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: ����֪ͨ�¼���Ӧ�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyEventResponse extends EventResponseSupport
{
    private static final long serialVersionUID = 1L;

    /**
     * ��ID��
     */
    private String BsoID = "";

    /**
     * UploadDocFileEventResponse���캯����
     * @param streamIDHashMap
     */
    public PromulgateNotifyEventResponse(String BsoID)
    {
        super(BsoID);
        this.BsoID = BsoID;
    }

    /**
     * ���ò����ĵ���bsoID��
     * @param streamIDHashMap
     */
    public void setPromulgateNotifyBsoID(String BsoID)
    {
        this.BsoID = BsoID;
    }

    /**
     * ��ȡ�����ĵ���bsoID��
     * @param streamIDHashMap
     */
    public String getPromulgateNotifyBsoID()
    {
        return BsoID;
    }

    /**
     * ��ȡ�¼���Ӧ���ơ�
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/PromulgateNotifyEventResponse";
    }
}
