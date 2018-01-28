/**
 * ���ɳ���MaterialSplitServiceHomeDelegate.java 1.0              2007-10-7
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
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class MaterialSplitServiceHomeDelegate implements ServiceHomeDelegate
{
    public MaterialSplitServiceHomeDelegate()
    {
    }

    private MaterialSplitServiceHome home = null;

    /**
     * ʵ��HomeDelegate�ӿڷ�����
     * @param obj ��ʼ������
     */
    public void init(Object obj)
    {
        if(!(obj instanceof MaterialSplitServiceHome))
        {
            Object[] objs = {obj.getClass(), "CDMaterialSplitServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (MaterialSplitServiceHome) obj;
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
