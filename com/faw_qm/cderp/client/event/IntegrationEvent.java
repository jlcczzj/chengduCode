/**
 * ���ɳ���IntegrationEvent.java   1.0              2007-10-10
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.client.event;

import java.util.Vector;
import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title: ���ɰ��¼��ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class IntegrationEvent extends EventSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static int CREATE = 1;

    public static int DELETE = 2;

    public static int RELEASE = 3;
    
    public static int RELEASETECH = 4;

    /**
     * �ĵ������ݡ�
     */
    private Vector data = new Vector();

    private int actionType = -1;

    private String bsoID = "";

    /**
     * ���캯����
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public IntegrationEvent(int actionType1)
    {
        this.actionType = actionType1;
    }

    /**
     * ��ȡ�ĵ������ݡ�
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getVector()
    {
        return data;
    }

    public void setVector(Vector vector)
    {
        data = vector;
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
        return "com.faw_qm.cderp.client.ejbaction.IntegrationEJBAction";
    }

    public void setBsoID(String id)
    {
        bsoID = id;
    }

    public String getBsoID()
    {
        return bsoID;
    }
}
