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

import com.faw_qm.framework.service.BaseServiceHome;

/**
 * <p>Title: </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */

public interface TechnicsRouteServiceHome extends BaseServiceHome
{
    public abstract TechnicsRouteService create() throws CreateException;
}
