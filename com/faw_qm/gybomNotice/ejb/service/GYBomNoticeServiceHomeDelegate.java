/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.gybomNotice.ejb.service;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class GYBomNoticeServiceHomeDelegate implements ServiceHomeDelegate
{
    /**
     * ��ʼ������Home�ӿ�
     * @param object ����Home�ӿ�ʵ��
     */
    public void init(Object object)
    {
        if (!(object instanceof GYBomNoticeServiceHome))
        {
            Object[] objs =
                    {
                    object.getClass(),
                    "GYBomNoticeServiceHome"
            };
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkMaterial", "70", objs);
        }
        home = (GYBomNoticeServiceHome) object;
    }


    /**
     * ʵ��ServiceHomeDelegate�ӿڷ����������������Beanʵ��
     * @return BaseService ����ӿ�ʵ��
     */
    public BaseService create()
            throws CreateException
    {
        return home.create();
    }

    private GYBomNoticeServiceHome home = null;


}
