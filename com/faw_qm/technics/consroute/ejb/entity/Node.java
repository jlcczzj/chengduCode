/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * �˽ӿ���ʱ�����壬��ɾ�������޸���ʡ�ļ��� <p>Title: </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */
public interface Node extends BsoReference
{
    /**
     * ��ù���·��ID.
     * @return java.lang.String
     * @roseuid 4032F60B0364
     */
    public String getRouteID();

    /**
     * ���ù���·��ID.
     * @roseuid 4032F613026B
     */
    public void setRouteID(String routeID);
}
