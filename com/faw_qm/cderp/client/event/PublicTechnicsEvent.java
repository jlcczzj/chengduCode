/**
 * ���ɳ���PublicProcessEvent.java	1.0              2007-11-5
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.client.event;

import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title:���������¼��� ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class PublicTechnicsEvent extends EventSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static int REALEASE = 1;

    private int actionType = -1;

    private String processIDs = "";

    /**
     * ���캯����
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public PublicTechnicsEvent(int actionType)
    {
        this.actionType = actionType;
    }

    /**
     * ��ȡ�������͡�
     * @return int
     */
    public int getActionType()
    {
        return actionType;
    }

    /**
     * ��ȡ�¼����ơ�
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "com.faw_qm.cderp.client.ejbaction.PublicTechnicsEJBAction";
    }

    /**
     * ���÷����Ĺ��չ��ID��
     * @param ids
     */
    public void setProcessIDs(String ids)
    {
        processIDs = ids;
    }

    /**
     * ��ȡ�����Ĺ��չ��ID��
     * @return
     */
    public String getProcessIDs()
    {
        return processIDs;
    }
}