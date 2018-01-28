/**
 * ���ɳ���IntePackServiceHomeDelegate.java	1.0              2007-9-25
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
 * <p>Title: ���ɰ��������ʵ���ࡣ</p>
 * <p>Description: ���ɰ��������ʵ���ࡣ</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class IntePackServiceHomeDelegate implements ServiceHomeDelegate
{
    public IntePackServiceHomeDelegate()
    {
    }

    private IntePackServiceHome home = null;

    /**
     * ʵ��HomeDelegate�ӿڷ�����
     * @param obj ��ʼ������
     */
    public void init(Object obj)
    {
        if(!(obj instanceof IntePackServiceHome))
        {
            Object[] objs = {obj.getClass(), "IntePackServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (IntePackServiceHome) obj;
    }

    /**
     * ʵ��ServiceHomeDelegate�ӿڷ�����
     * @return ��׼�ļ�����Beanʵ��
     * @throws CreateException
     */
    public BaseService create() throws CreateException
    {
        return home.create();
    }
}
