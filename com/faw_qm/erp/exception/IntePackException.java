/**
 * ���ɳ���IntePackException.java	1.0              2007-9-26
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.exception;

import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: ���ɰ����쳣�ࡣ</p>
 * <p>Description: ���ɰ����쳣�ࡣ</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class IntePackException extends QMException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ���쳣
     */
    public IntePackException()
    {
    }

    /**
     * ���ַ��������쳣����ֱ�����
     * @param msg �쳣��Ϣ
     */
    public IntePackException(String msg)
    {
        super(msg);
    }

    /**
     * Ƕ���쳣��Ϣ
     * @param arg0 �쳣ʵ��
     */
    public IntePackException(Exception e)
    {
        super(e);
    }

    /**
     * Ƕ���쳣��ֱ�������Ϣ
     * @param e �쳣ʵ��
     * @param msg �쳣��Ϣ
     */
    public IntePackException(Exception e, String msg)
    {
        super(e, msg);
    }

    /**
     * �������쳣��Ϣ
     * @param rb ��Դ����
     * @param key ��Ϣ�ļ�ֵ
     * @param obj ������Ϣ
     */
    public IntePackException(String rb, String key, Object[] obj)
    {
        super(rb, key, obj);
    }

    /**
     * Ƕ���쳣���������쳣��Ϣ
     * @param e �쳣ʵ��
     * @param rb ��Դ����
     * @param key ��Ϣ�ļ�ֵ
     * @param obj ������Ϣ
     */
    public IntePackException(Exception e, String rb, String key, Object[] obj)
    {
        super(e, rb, key, obj);
    }
}
