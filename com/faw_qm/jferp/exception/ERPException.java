/**
 * ���ɳ���ERPException.java    1.0              2007-10-7
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.exception;

import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class ERPException extends QMException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ERPException()
    {
        // TODO �Զ����ɹ��캯�����
    }

    /**
     * @param arg0
     */
    public ERPException(String arg0)
    {
        super(arg0);
        // TODO �Զ����ɹ��캯�����
    }

    /**
     * @param arg0
     */
    public ERPException(Exception arg0)
    {
        super(arg0);
        // TODO �Զ����ɹ��캯�����
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ERPException(Exception arg0, String arg1)
    {
        super(arg0, arg1);
        // TODO �Զ����ɹ��캯�����
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public ERPException(String arg0, String arg1, Object[] arg2)
    {
        super(arg0, arg1, arg2);
        // TODO �Զ����ɹ��캯�����
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public ERPException(Exception arg0, String arg1, String arg2,
            Object[] arg3)
    {
        super(arg0, arg1, arg2, arg3);
        // TODO �Զ����ɹ��캯�����
    }
}
