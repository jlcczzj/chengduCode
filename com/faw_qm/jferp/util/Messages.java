/**
 * ���ɳ���Messages.java	1.0              2006-11-16
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.util.ResourceBundle;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;

/**
 * <p>Title: ��ȡ��Դ��Ϣ��װ�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public final class Messages
{
    public static final String BUNDLE_NAME = "com.faw_qm.jferp.util.ERPResource"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME, RemoteProperty.getVersionLocale());

    /**
     * ȱʡ���캯����
     */
    private Messages()
    {
        super();
    }

    /**
     * ��ȡ��Դֵ��
     * @param key ����
     * @return ��Դֵ��
     */
    public final static String getString(String key)
    {
        return RESOURCE_BUNDLE.getString(key);
    }

    /**
     * ��ȡ���б�������Դֵ��
     * @param key ����
     * @param aobj �������ϡ�
     * @return ��Դֵ��
     */
    public final static String getString(String key, Object aobj[])
    {
        return QMMessage.getLocalizedMessage(BUNDLE_NAME, key, aobj);
    }
}
