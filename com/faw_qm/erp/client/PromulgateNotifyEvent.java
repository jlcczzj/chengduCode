/**
 * ���ɳ���PromulgateNotifyEvent.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.client;

import java.util.Vector;
import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title: ����֪ͨ�¼��ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyEvent extends EventSupport
{
    private static final long serialVersionUID = 1L;

    public static int ADD = 1;

    public static int DELETE = 2;

    public static int UPDATE = 3;

    public static int UPDATEADDDOC = 4;

    public static int UPDATEPRODUCT = 5;

    /**
     * �ĵ������ݡ�
     */
    private Vector data = new Vector();

    private int actionType = -1;

    /**
     * ���캯����
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public PromulgateNotifyEvent(int actionType1, Vector data)
    {
        this.actionType = actionType1;
        this.data = data;
    }

    /**
     * ��ȡ�ĵ������ݡ�
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getVector()
    {
        return data;
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
        return "com.faw_qm.erp.client.PromulgateNotifyEJBAction";
    }
}
