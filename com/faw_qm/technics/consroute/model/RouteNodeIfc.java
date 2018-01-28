/**
 * ���ɳ��� RouteNodeIfc.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title:RouteNodeIfc.java</p> <p>Description:·�߽ڵ�ӿ� </p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteNodeIfc extends CappIdentity, NodeIfc, BaseValueIfc
{
    /**
     * ����·���еĽڵ�˵����
     */
    public java.lang.String getNodeDescription();

    /**
     * ����·���еĽڵ�˵����
     * @param description String
     */
    public void setNodeDescription(String description);

    /**
     * ���·�ߵ�λ��
     * @return String
     */
    public java.lang.String getNodeDepartment();

    /**
     * ����·�ߵ�λ
     * @param department String
     */
    public void setNodeDepartment(String department);

    /**
     * ���·�ߵ�λ���ơ�
     * @return String
     */
    public java.lang.String getNodeDepartmentName();

    /**
     * ����·�ߵ�λ���ơ�
     * @param departmentName String
     */
    public void setNodeDepartmentName(String departmentName);

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     * @return String
     */
    public java.lang.String getRouteType();

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     * @param routeType String
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
     * @return long
     */
    public long getX();

    /**
     * ���ýڵ��X����
     * @param Xlocation long
     */
    public void setX(long Xlocation);

    /**
     * ��ýڵ��Y���ꡣ
     * @return long
     */
    public long getY();

    /**
     * ���ýڵ��Y����
     * @param Ylocation long
     */
    public void setY(long Ylocation);

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
