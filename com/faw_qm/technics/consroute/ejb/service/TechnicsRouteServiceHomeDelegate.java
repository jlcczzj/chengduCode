/**
 * ��Ȩ��һ��������˾����

 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.service;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;

/**
 * <p>Title: </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */

public class TechnicsRouteServiceHomeDelegate implements ServiceHomeDelegate
{
    private TechnicsRouteServiceHome home = null;

    public void init(Object obj) 
    {
        if(!(obj instanceof TechnicsRouteServiceHome))
        {
            Object[] objs = {obj.getClass(), "TechnicsRouteServiceHome"};
            throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (TechnicsRouteServiceHome)obj;
    }

    public BaseService create() throws CreateException
    {
        return home.create();
    }

}
