/**
 * ���ɳ��� RouteNodeInfo.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title:RouteNodeInfo.java</p> <p>Description: ·�߽ڵ�ֵ����</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class RouteNodeInfo extends BaseValueInfo implements RouteNodeIfc
{

    //����
    private String description;

    //���ŵ�id
    private String department;

    //������
    private String departmentName;

    //·������
    private String routeType;

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  strart
    //�Ƿ�����ʱ·��
    private boolean isTempRoute;

    //
    private String ratio;

    //��Чʱ��
    private String validTime;

    //ʧЧʱ��
    private String invalidTime;

    // gcy add in 2005.4.26 for reinforce requirement  end

    // ��ͼ��λ�õ�x����
    private long XLocation;

    // ��ͼ��λ�õ�y����
    private long YLocation;

    //·��ֵ����
    private TechnicsRouteIfc routeInfo;

    private static final long serialVersionUID = 1L;

    //begin CR1
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * ���캯��
     */
    public RouteNodeInfo()
    {

    }

    /**
     * ���ҵ���������
     */
    public String getBsoName()
    {
        return "consRouteNode";
    }

    /**
     * ����·���еĽڵ�˵����
     */
    public java.lang.String getNodeDescription()
    {
        return description;
    }

    /**
     * ����·���еĽڵ�˵����
     */
    public void setNodeDescription(String description)
    {
        this.description = description;
    }

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  start
    /**
     *����·���еĽڵ����˵��
     */
    public java.lang.String getNodeRatio()
    {
        return ratio;
    }

    /**
     * ����·���еĽڵ�����˵��
     */
    public void setNodeRatio(String ratio)
    {
        this.ratio = ratio;
    }

    /**
     * �õ��Ƿ�����ʱ·��
     * @return boolean
     */
    public boolean getIsTempRoute()
    {
        return this.isTempRoute;
    }

    /**
     * �����Ƿ�����ʱ·��
     * @param temp boolean
     */
    public void setIsTempRoute(boolean temp)
    {
        this.isTempRoute = temp;
    }

    /**
     * ����·�߽ڵ��е���Чʱ��˵��
     * @return validTime
     */
    public java.lang.String getNodeValidTime()
    {
        return validTime;
    }

    /**
     * ����·���еĽڵ����Чʱ��
     */
    public void setNodeValidTime(String validTime)
    {
        this.validTime = validTime;
    }

    /**
     * ����·���еĽӵ�ʧЧʱ��˵��
     */
    public java.lang.String getNodeInvalidTime()
    {
        return invalidTime;
    }

    /**
     * ����·���еĽڵ��ʧЧʱ��˵��
     */
    public void setNodeInvalidTime(String invalidTime)
    {
        this.invalidTime = invalidTime;
    }

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * ���·�ߵ�λ��
     */
    public java.lang.String getNodeDepartment()
    {
        return department;
    }

    /**
     * ����·�ߵ�λ��
     */
    public void setNodeDepartment(String department)
    {
        this.department = department;
    }

    /**
     * ���·�ߵ�λ���ơ�
     */
    public java.lang.String getNodeDepartmentName()
    {
        return this.departmentName;
    }

    /**
     * ����·�ߵ�λ���ơ�
     */
    public void setNodeDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     */
    public java.lang.String getRouteType()
    {
        return routeType;
    }

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     */
    public void setRouteType(String routeType)
    {
        this.routeType = routeType;
    }

    /**
     * ��ýڵ��X���ꡣ
     */
    public long getX()
    {
        return XLocation;
    }

    /**
     * ���ýڵ��X����
     */
    public void setX(long XLocation)
    {
        this.XLocation = XLocation;
    }

    /**
     * ��ýڵ��Y���ꡣ
     */
    public long getY()
    {
        return YLocation;
    }

    /**
     * ���ýڵ��Y����
     */
    public void setY(long YLocation)
    {
        this.YLocation = YLocation;
    }

    /**
     * �õ�·��ֵ����
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return routeInfo;
    }

    /**
     * ����·��ֵ����
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
    }

    /**
     * �õ���ʶ
     */
    public String getIdentity()
    {
        return this.getNodeDepartment() + this.getRouteType();
    }

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
