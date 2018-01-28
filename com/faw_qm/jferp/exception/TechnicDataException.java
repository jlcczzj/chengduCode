package com.faw_qm.jferp.exception;

/**
 * ���ɳ���TechnicDataException.java	1.0              2007-10-31
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */

import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: �������ݵ��쳣�ࡣ</p>
 * <p>Description: �������ݵ��쳣�ࡣ</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public class TechnicDataException extends QMException
{
	
    private static final long serialVersionUID = 1L;
    
    /**
     * ���쳣
     */
    public TechnicDataException()
    {
    }

    /**
     * ���ַ��������쳣����ֱ�����
     * @param msg �쳣��Ϣ
     */
    public TechnicDataException(String msg)
    {
        super(msg);
    }

    /**
     * Ƕ���쳣��Ϣ
     * @param arg0 �쳣ʵ��
     */
    public TechnicDataException(Exception e)
    {
        super(e);
    }

    /**
     * Ƕ���쳣��ֱ�������Ϣ
     * @param e �쳣ʵ��
     * @param msg �쳣��Ϣ
     */
    public TechnicDataException(Exception e, String msg)
    {
        super(e, msg);
    }

    /**
     * �������쳣��Ϣ
     * @param rb ��Դ����
     * @param key ��Ϣ�ļ�ֵ
     * @param obj ������Ϣ
     */
    public TechnicDataException(String rb, String key, Object[] obj)
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
    public TechnicDataException(Exception e, String rb, String key, Object[] obj)
    {
        super(e, rb, key, obj);
    }
}
