/**
 * 生成程序 RouteNodeIfc.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title:RouteNodeIfc.java</p> <p>Description:路线节点接口 </p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public interface RouteNodeIfc extends CappIdentity, NodeIfc, BaseValueIfc
{
    /**
     * 工艺路线中的节点说明。
     */
    public java.lang.String getNodeDescription();

    /**
     * 工艺路线中的节点说明。
     * @param description String
     */
    public void setNodeDescription(String description);

    /**
     * 获得路线单位。
     * @return String
     */
    public java.lang.String getNodeDepartment();

    /**
     * 设置路线单位
     * @param department String
     */
    public void setNodeDepartment(String department);

    /**
     * 获得路线单位名称。
     * @return String
     */
    public java.lang.String getNodeDepartmentName();

    /**
     * 设置路线单位名称。
     * @param departmentName String
     */
    public void setNodeDepartmentName(String departmentName);

    /**
     * 记录路线节点的类别是制造还是装配，从枚举集合中取值{装配/制造}，默认值为制造
     * @return String
     */
    public java.lang.String getRouteType();

    /**
     * 记录路线节点的类别是制造还是装配，从枚举集合中取值{装配/制造}，默认值为制造
     * @param routeType String
     */
    public void setRouteType(String routeType);

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  start
    /**
     *工艺路线中的节点配比说明
     */
    public java.lang.String getNodeRatio();

    /**
     * 工艺路线中的节点配置说明
     */
    public void setNodeRatio(String ratio);

    /**
     * 得到是否是临时路线
     * @return boolean
     */
    public boolean getIsTempRoute();

    /**
     * 设置是否是临时路线
     * @param temp boolean
     */
    public abstract void setIsTempRoute(boolean temp);

    /**
     * 工艺路线节点中的生效时间说明
     * @return validTime
     */
    public java.lang.String getNodeValidTime();

    /**
     * 工艺路线中的节点的生效时间
     */
    public void setNodeValidTime(String validTime);

    /**
     * 工艺路线中的接点失效时间说明
     */
    public java.lang.String getNodeInvalidTime();

    /**
     * 工艺路线中的节点的失效时间说明
     */
    public void setNodeInvalidTime(String invalidTime);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * 获得节点的X坐标。
     * @return long
     */
    public long getX();

    /**
     * 设置节点的X坐标
     * @param Xlocation long
     */
    public void setX(long Xlocation);

    /**
     * 获得节点的Y坐标。
     * @return long
     */
    public long getY();

    /**
     * 设置节点的Y坐标
     * @param Ylocation long
     */
    public void setY(long Ylocation);

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1

}
