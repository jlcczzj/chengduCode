/**
 * 生成程序 RouteNodeInfo.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title:RouteNodeInfo.java</p> <p>Description: 路线节点值对象</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public class RouteNodeInfo extends BaseValueInfo implements RouteNodeIfc
{

    //描述
    private String description;

    //部门的id
    private String department;

    //部门名
    private String departmentName;

    //路线类型
    private String routeType;

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  strart
    //是否是临时路线
    private boolean isTempRoute;

    //
    private String ratio;

    //生效时间
    private String validTime;

    //失效时间
    private String invalidTime;

    // gcy add in 2005.4.26 for reinforce requirement  end

    // 在图中位置的x坐标
    private long XLocation;

    // 在图中位置的y坐标
    private long YLocation;

    //路线值对象
    private TechnicsRouteIfc routeInfo;

    private static final long serialVersionUID = 1L;

    //begin CR1
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * 构造函数
     */
    public RouteNodeInfo()
    {

    }

    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consRouteNode";
    }

    /**
     * 工艺路线中的节点说明。
     */
    public java.lang.String getNodeDescription()
    {
        return description;
    }

    /**
     * 工艺路线中的节点说明。
     */
    public void setNodeDescription(String description)
    {
        this.description = description;
    }

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  start
    /**
     *工艺路线中的节点配比说明
     */
    public java.lang.String getNodeRatio()
    {
        return ratio;
    }

    /**
     * 工艺路线中的节点配置说明
     */
    public void setNodeRatio(String ratio)
    {
        this.ratio = ratio;
    }

    /**
     * 得到是否是临时路线
     * @return boolean
     */
    public boolean getIsTempRoute()
    {
        return this.isTempRoute;
    }

    /**
     * 设置是否是临时路线
     * @param temp boolean
     */
    public void setIsTempRoute(boolean temp)
    {
        this.isTempRoute = temp;
    }

    /**
     * 工艺路线节点中的生效时间说明
     * @return validTime
     */
    public java.lang.String getNodeValidTime()
    {
        return validTime;
    }

    /**
     * 工艺路线中的节点的生效时间
     */
    public void setNodeValidTime(String validTime)
    {
        this.validTime = validTime;
    }

    /**
     * 工艺路线中的接点失效时间说明
     */
    public java.lang.String getNodeInvalidTime()
    {
        return invalidTime;
    }

    /**
     * 工艺路线中的节点的失效时间说明
     */
    public void setNodeInvalidTime(String invalidTime)
    {
        this.invalidTime = invalidTime;
    }

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * 获得路线单位。
     */
    public java.lang.String getNodeDepartment()
    {
        return department;
    }

    /**
     * 设置路线单位。
     */
    public void setNodeDepartment(String department)
    {
        this.department = department;
    }

    /**
     * 获得路线单位名称。
     */
    public java.lang.String getNodeDepartmentName()
    {
        return this.departmentName;
    }

    /**
     * 设置路线单位名称。
     */
    public void setNodeDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * 记录路线节点的类别是制造还是装配，从枚举集合中取值{装配/制造}，默认值为制造
     */
    public java.lang.String getRouteType()
    {
        return routeType;
    }

    /**
     * 记录路线节点的类别是制造还是装配，从枚举集合中取值{装配/制造}，默认值为制造
     */
    public void setRouteType(String routeType)
    {
        this.routeType = routeType;
    }

    /**
     * 获得节点的X坐标。
     */
    public long getX()
    {
        return XLocation;
    }

    /**
     * 设置节点的X坐标
     */
    public void setX(long XLocation)
    {
        this.XLocation = XLocation;
    }

    /**
     * 获得节点的Y坐标。
     */
    public long getY()
    {
        return YLocation;
    }

    /**
     * 设置节点的Y坐标
     */
    public void setY(long YLocation)
    {
        this.YLocation = YLocation;
    }

    /**
     * 得到路线值对象
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return routeInfo;
    }

    /**
     * 设置路线值对象
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
    }

    /**
     * 得到标识
     */
    public String getIdentity()
    {
        return this.getNodeDepartment() + this.getRouteType();
    }

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
