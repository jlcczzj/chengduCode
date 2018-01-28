/**
 * ���ɳ���PromulgateNotifyServiceHomeDelegate.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.service;

import javax.ejb.CreateException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;

/**
 * <p>Title:����֪ͨ�������ʵ���� </p>
 * <p>Description: ����֪ͨ�������ʵ����</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyServiceHomeDelegate implements ServiceHomeDelegate
{
    public PromulgateNotifyServiceHomeDelegate()
    {
    }

    private PromulgateNotifyServiceHome home = null;

    /**
     * ʵ��HomeDelegate�ӿڷ�������һ�������ʼ����׼�ļ�����Home�ӿ�
     * @param obj ��ʼ������
     */
    public void init(Object obj)
    {
        if(!(obj instanceof PromulgateNotifyServiceHome))
        {
            Object[] objs = {obj.getClass(), "PromulgateNotifyServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (PromulgateNotifyServiceHome) obj;
    }

    /**
     * ʵ��ServiceHomeDelegate�ӿڷ�����������׼�ļ�����Beanʵ��
     * @return ��׼�ļ�����Beanʵ��
     * @throws CreateException
     */
    public BaseService create() throws CreateException
    {
        return home.create();
    }
}
