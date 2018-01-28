/**
 * ���ɳ���IntegrationEventResponse.java   1.0              2007-10-10
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.client.event;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: ���ɰ��¼���Ӧ�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class IntegrationEventResponse extends EventResponseSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ��ID��
     */
    private String BsoID = "";

    private String message = "";

    /**
     * UploadDocFileEventResponse���캯����
     * @param streamIDHashMap
     */
    public IntegrationEventResponse(String BsoID)
    {
        super(BsoID);
        this.BsoID = BsoID;
    }

    /**
     * ��ȡ�����ĵ���bsoID��
     * @param streamIDHashMap
     */
    public String getBsoID()
    {
        return BsoID;
    }

    /**
     * ���ò�����ķ�����Ϣ
     * @param message
     */
    public void setResult(String message)
    {
        this.message = message;
    }

    /**
     * ��ȡ��Ϣ
     * @return
     */
    public String getResult()
    {
        return message;
    }

    /**
     * ��ȡ�¼���Ӧ���ơ�
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/IntegrationEventResponse";
    }
}
