/**
 * ���ɳ���PublicProcessEventResponse.java	1.0              2007-11-5
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.client.event;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: ���������¼���Ӧ�ࡣ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class PublicTechnicsEventResponse extends EventResponseSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * UploadDocFileEventResponse���캯����
     * @param streamIDHashMap
     */
    public PublicTechnicsEventResponse(String BsoID)
    {
        super(BsoID);
    }

    /**
     * ��ȡ�¼���Ӧ���ơ�
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/PublicTechnicsEventResponse";
    }
}
