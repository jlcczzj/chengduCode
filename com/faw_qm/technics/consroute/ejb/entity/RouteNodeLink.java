/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.framework.service.BinaryLink;

/**
 * ���кͽڵ�Ĺ��� <p>Title: </p> <p>Description: routeID��Ϊ�˲���·���п�ʼ��λ�ͽ�����λ�� ҵ�����󣺹���·�ߴ��Ĺ���Ϊ·�ߵ�λ�ڵ㣬һ����λ������һ��·�ߴ��г��ֶ�Ρ�·�ߴ��а����ӹ���λ��װ�䵥λ������·�ߴ��Ĺ��ɱ���������й��� 1. װ�䵥λ��һ��·�ߴ���ֻ����һ������ֻ�������һ���ڵ㣻 2.
 * һ����λ�����һ��·�ߴ��г��ֶ�Σ�������ǲ�ͬ���͵Ľڵ�(�����װ��)�������������ڵ�λ�ó��֡� �� * ���һ��·�ߴ�������˶��װ��ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���װ��ڵ㲻�����ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���·�ߴ��д������ڵ�ͬ���ͽڵ㣬����ʾ��Ӧ����Ϣ </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:
 * faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteNodeLink extends BinaryLink
{
    /**
     * ����·��ID
     */
    public java.lang.String getRouteID();

    /**
     * ����·��ID
     */
    public void setRouteID(String routeID);

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1
}
