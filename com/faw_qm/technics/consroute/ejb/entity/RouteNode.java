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

/**
 * ·�߽ڵ� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteNode extends Node
{
    /**
     * ����·���еĽڵ�˵����
     */
    public java.lang.String getNodeDescription();

    /**
     * ����·���еĽڵ�˵����
     */
    public void setNodeDescription(String description);

    /**
     * t ���·�ߵ�λ��id��
     */
    public java.lang.String getNodeDepartment();

    /**
     * ����·�ߵ�λ��
     */
    public void setNodeDepartment(String department);

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     */
    public java.lang.String getRouteType();

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     */
    public void setRouteType(String routeType);

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  start
    /**
     *����·���еĽڵ����˵��
     */
    public java.lang.String getNodeRatio();

    /**
     * ����·���еĽڵ�����˵��
     */
    public void setNodeRatio(String ratio);

    /**
     * �õ��Ƿ�����ʱ·��
     * @return boolean
     */
    public boolean getIsTempRoute();

    /**
     * �����Ƿ�����ʱ·��
     * @param temp boolean
     */
    public abstract void setIsTempRoute(boolean temp);

    /**
     * ����·�߽ڵ��е���Чʱ��˵��
     * @return validTime
     */
    public java.lang.String getNodeValidTime();

    /**
     * ����·���еĽڵ����Чʱ��
     */
    public void setNodeValidTime(String validTime);

    /**
     * ����·���еĽӵ�ʧЧʱ��˵��
     */
    public java.lang.String getNodeInvalidTime();

    /**
     * ����·���еĽڵ��ʧЧʱ��˵��
     */
    public void setNodeInvalidTime(String invalidTime);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * ��ýڵ��X���ꡣ
     */
    public long getX();

    /**
     * ���ýڵ��X����
     */
    public void setX(long Xlocation);

    /**
     * ��ýڵ��Y���ꡣ
     */
    public long getY();

    /**
     * ���ýڵ��Y����
     */
    public void setY(long Ylocation);

    /**
     * ��ù���·�߷�֦ID.
     */
    public java.lang.String getRouteBranchID();

    /**
     * ���ù���·�߷�֦ID.
     */
    public void setRouteBranchID(String routeBranchID);

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
