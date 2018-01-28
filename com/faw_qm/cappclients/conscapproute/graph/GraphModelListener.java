/** 
 * 生成程序 GraphModelListener.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.EventListener;
import java.beans.PropertyChangeEvent;

/**
 * <p> Title:图元模式（节点或连接）监听器接口 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public interface GraphModelListener extends EventListener
{
    public int NODE_INSERTED = 0;

    public int NODE_REMOVED = 1;

    public int STRUCTURE_CHANGED = 2;

    public int LINK_INSERTED = 3;

    public int LINK_REMOVED = 4;

    /**
     * 插入节点
     * @param graphmodelevent - 传入GraphModelEvent的对象
     * @roseuid 40288C4903C9
     */
    public abstract void graphNodeInserted(GraphModelEvent graphmodelevent);

    /**
     * 改变节点的属性
     * @param propertychangeevent - 传入PropertyChangeEvent的对象
     * @roseuid 40288C9D038E
     */
    public abstract void graphNodeChanged(PropertyChangeEvent propertychangeevent);

    /**
     * 删除节点
     * @param graphmodelevent
     * @roseuid 40288CE200EE
     */
    public abstract void graphNodeRemoved(GraphModelEvent graphmodelevent);

    /**
     * 改变图元结构
     * @param graphmodelevent
     * @roseuid 40288D1C0174
     */
    public abstract void graphStructureChanged(GraphModelEvent graphmodelevent);

    /**
     * 插入连接
     * @param graphmodelevent
     * @roseuid 40288D47007B
     */
    public abstract void graphLinkInserted(GraphModelEvent graphmodelevent);

    /**
     * 改变连接属性
     * @param propertychangeevent
     * @roseuid 40288D7F00FE
     */
    public abstract void graphLinkChanged(PropertyChangeEvent propertychangeevent);

    /**
     * 删除连接
     * @param graphmodelevent
     * @roseuid 40288DBA01E9
     */
    public abstract void graphLinkRemoved(GraphModelEvent graphmodelevent);
}