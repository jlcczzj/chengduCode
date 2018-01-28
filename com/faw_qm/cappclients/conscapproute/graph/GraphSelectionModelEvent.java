/** 
 * 生成程序 GraphSelectionModelEvent.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.EventObject;
import java.io.Serializable;

/**
 * <p> Title:图元选择模型监听事件 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public class GraphSelectionModelEvent extends EventObject implements Serializable
{
    public GraphNode node;

    public GraphLink link;

    /**
     * 构造器
     * @param graphselectionmodel - 传入GraphSelectionModel的对象
     * @param graphnode - 传入GraphNode的对象
     * @param graphlink - 传入GraphLink的对象
     * @roseuid 4028D63401E2
     */
    public GraphSelectionModelEvent(GraphSelectionModel graphselectionmodel, GraphNode graphnode, GraphLink graphlink)
    {
        super(graphselectionmodel);
        node = graphnode;
        link = graphlink;
    }

    /**
     * 得到节点
     * @return capproute.graph.GraphNode
     * @roseuid 4028D63401E6
     */
    public GraphNode getNode()
    {
        return node;
    }

    /**
     * 得到链接
     * @return capproute.graph.GraphLink
     * @roseuid 4028D63401E7
     */
    public GraphLink getLink()
    {
        return link;
    }
}