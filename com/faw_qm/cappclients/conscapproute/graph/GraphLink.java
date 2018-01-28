/** 
 * 生成程序 GraphLink.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.beans.PropertyChangeListener;

/**
 * <p> Title:图元连接接口 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public interface GraphLink
{

    /**
     * 抽象方法，获取连接的前驱节点
     * @return capproute.graph.GraphNode
     * @roseuid 40297FDF01A0
     */
    public abstract GraphNode getPredecessor();

    /**
     * 抽象方法，获取链接的后继节点
     * @return capproute.graph.GraphNode
     * @roseuid 40297FDF01A1
     */
    public abstract GraphNode getSuccessor();

    /**
     * 获得连接的信息封装对象
     * @return RouteItem 为服务端完成持久化工作提供的业务对象信息封装类
     */
    public abstract RouteItem getRouteItem();

    /**
     * 抽象方法，添加属性改变监听器
     * @param propertychangelistener 监听器对象
     * @roseuid 40297FDF01A3
     */
    public abstract void addPropertyChangeListener(PropertyChangeListener propertychangelistener);

    /**
     * 抽象方法，删除属性改变监听器
     * @param propertychangelistener 监听器对象
     * @roseuid 40297FDF01A5
     */
    public abstract void removePropertyChangeListener(PropertyChangeListener propertychangelistener);
}