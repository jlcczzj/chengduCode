/** 
 * 生成程序 GraphNode.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.beans.PropertyChangeListener;

/**
 * <p> Title:图元节点接口 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public interface GraphNode
{

    /**
     * 得到图标
     * @return java.lang.String
     * @roseuid 40297DEC00C6
     */
    public abstract String getIcon();

    /**
     * 得到选择的图标
     * @return java.lang.String
     * @roseuid 40297DEC00C7
     */
    public abstract String getSelectedIcon();

    /**
     * 得到坐标位置
     * @return java.awt.Point
     * @roseuid 40297DEC00C9
     */
    public abstract java.awt.Point getPosition();

    /**
     * 设置坐标位置
     * @param i - 整型，传入横坐标的值
     * @param j - 整型，传入纵坐标的值
     * @roseuid 40297DEC00CB
     */
    public abstract void setPosition(int i, int j);

    /**
     * 获得节点信息封装对象
     * @return RouteItem 为服务端完成持久化工作提供的业务对象信息封装类
     */
    public abstract RouteItem getRouteItem();

    /**
     * 获得节点名称
     * @return String
     */
    public abstract String getDepartmentName();

    /**
     * 更新图片
     */
    public abstract void updateImage();

    /**
     * 添加属性改变监听器
     * @param propertychangelistener - 传入PropertyChangeListener的对象
     * @roseuid 40297DEC00CE
     */
    public abstract void addPropertyChangeListener(PropertyChangeListener propertychangelistener);

    /**
     * 删除属性改变监听器
     * @param propertychangelistener - 传入PropertyChangeListener的对象
     * @roseuid 40297DEC00D0
     */
    public abstract void removePropertyChangeListener(PropertyChangeListener propertychangelistener);
}