/** 
 * 生成程序 DefaultGraphSelectionModel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

/**
 * <p>
 * Title:默认的图元选择模式 选择节点或链接
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * 
 * @author 刘明
 * @version 1.0
 */

import java.util.*;
import javax.swing.event.EventListenerList;
import com.faw_qm.clients.util.*;

public class DefaultGraphSelectionModel implements GraphSelectionModel
{
    /** 节点是否允许被选中 */
    public boolean nodeSelectionAllowed;

    /** 连接是否允许被选中 */
    public boolean linkSelectionAllowed;

    /** 是否允许多选 */
    public boolean multiSelectionAllowed;

    /** 选中的连接的集合 */
    public Vector selectedLinks;

    /** 选中的节点的集合 */
    public Vector selectedNodes;

    /** 事件监听列表 */
    private EventListenerList listenerList;

    /**
     * 构造器
     * @roseuid 3DB671B60346
     */
    public DefaultGraphSelectionModel()
    {
        nodeSelectionAllowed = true;
        linkSelectionAllowed = true;
        multiSelectionAllowed = true;
        selectedNodes = new Vector();
        selectedLinks = new Vector();
        listenerList = new EventListenerList();

    }

    /**
     * 是否可以选中节点，返回布尔值
     * @return boolean
     * @roseuid 3DB671B60350
     */
    public boolean isNodeSelectionAllowed()
    {
        return nodeSelectionAllowed;
    }

    /**
     * 节点的可选状态
     * @param flag - boolean型，决定节点是否可以被选择
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B6035A
     */
    public void setNodeSelectionAllowed(boolean flag)
    {
        nodeSelectionAllowed = flag;
        clearSelection();
    }

    /**
     * 是否可以选择连接
     * @return boolean
     * @roseuid 3DB671B6036E
     */
    public boolean isLinkSelectionAllowed()
    {
        return linkSelectionAllowed;
    }

    /**
     * 链接的可选状态
     * @param flag - boolean型，决定链接是否可选
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B60378
     */
    public void setLinkSelectionAllowed(boolean flag)
    {
        linkSelectionAllowed = flag;
        clearSelection();

    }

    /**
     * 是否可以多选*
     * @return boolean
     * @roseuid 3DB671B60383
     */
    public boolean isMultiSelectionAllowed()
    {
        return multiSelectionAllowed;
    }

    /**
     * 多态的选择状态
     * @param flag - boolean型，决定是否可以选择多个
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B6038C
     */
    public void setMultiSelectionAllowed(boolean flag)
    {
        multiSelectionAllowed = flag;
        clearSelection();
    }

    /**
     * 获得选择的接点
     * @return java.util.Vector
     * @roseuid 3DB671B603A1
     */
    public Vector getSelectedNodes()
    {
        return selectedNodes;
    }

    /**
     * 设置选定的节点
     * @param vector - Vector型，传入一个向量
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B603AA
     */
    public void setSelectedNodes(Vector vector)
    {
        selectedNodes = vector;
    }

    /**
     * 得到选择的链接，返回向量
     * @return java.util.Vector
     * @roseuid 3DB671B603BE
     */
    public Vector getSelectedLinks()
    {
        return selectedLinks;
    }

    /**
     * 设置选定的链接
     * @param vector - Vector型，传入一个向量
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B603C8
     */
    public void setSelectedLinks(java.util.Vector vector)
    {
        selectedLinks = vector;
    }

    /**
     * 该方法将相关的模型，节点，链接产生的事件（节点和链接是否被选中，模式是否被清空） 转发给相应的监听器
     * @param i - 整型，传入多态选择的可供选择的项
     * @param graphselectionmodel - 传入GraphSelectionModel的对象
     * @param graphnode - 传入GraphNode的对象
     * @param graphlink - 传入GraphLink的对象
     * @roseuid 3DB671B603DC
     */
    public void fireGraphSelectionModelChanged(int i, GraphSelectionModel graphselectionmodel, GraphNode graphnode, GraphLink graphlink)
    {
        Object aobj[] = listenerList.getListenerList();
        GraphSelectionModelEvent graphselectionmodelevent = new GraphSelectionModelEvent(graphselectionmodel, graphnode, graphlink);
        for(int j = aobj.length - 2;j >= 0;j -= 2)
            //在不同的情况下激发图形选择模式的改变
            if(aobj[j] == (GraphSelectionModelListener.class))
                switch(i)
                {
                case 0: // '\0'
                    ((GraphSelectionModelListener)aobj[j + 1]).graphNodeSelected(graphselectionmodelevent);
                    break;

                case 1: // '\001'
                    ((GraphSelectionModelListener)aobj[j + 1]).graphNodeUnselected(graphselectionmodelevent);
                    break;

                case 4: // '\004'
                    ((GraphSelectionModelListener)aobj[j + 1]).graphSelectionCleared(graphselectionmodelevent);
                    break;

                case 2: // '\002'
                    ((GraphSelectionModelListener)aobj[j + 1]).graphLinkSelected(graphselectionmodelevent);
                    break;

                case 3: // '\003'
                    ((GraphSelectionModelListener)aobj[j + 1]).graphLinkUnselected(graphselectionmodelevent);
                    break;
                }

    }

    /**
     * 验证节点是否被选定，返回布尔型
     * @param graphnode - 传入GraphNode的对象
     * @return boolean
     * @roseuid 3DB671B70013
     */
    public boolean isSelected(GraphNode graphnode)
    {
        return selectedNodes.contains(graphnode);
    }

    /**
     * 验证链接是否被选定，返回布尔型
     * @param graphlink - 传入GraphLink的对象
     * @return boolean
     * @roseuid 3DB671B70027
     */
    public boolean isSelected(GraphLink graphlink)
    {
        return selectedLinks.contains(graphlink);
    }

    /**
     * @return int
     * @roseuid 3DB671B70045
     */
    public int getNodesSelectedCount()
    {
        return selectedNodes.size();
    }

    /**
     * 得到被选择的链接的总数，返回整型
     * @return int
     * @roseuid 3DB671B7004F
     */
    public int getLinksSelectedCount()
    {
        return selectedLinks.size();
    }

    /**
     * 清除选择的接点和连接
     * @roseuid 3DB671B70063
     */
    public void clearSelection()
    {
        selectedNodes.removeAllElements();
        selectedLinks.removeAllElements();
        fireGraphSelectionModelChanged(4, this, null, null);
    }

    /**
     * 如果给定的节点没有被选中而且该节点是可选的在选定的节点中添加该节点
     * @param graphnode - 传入GraphNode的对象
     * @roseuid 3DB671B7006D
     */
    public void add(GraphNode graphnode)
    {
        //在接点允许被选择且没有被选择的情况下
        if(!isSelected(graphnode) && isNodeSelectionAllowed())
        {
            if(!isMultiSelectionAllowed())
                clearSelection();
            selectedNodes.addElement(graphnode);
            fireGraphSelectionModelChanged(0, this, graphnode, null);
        }
    }

    /**
     * 删除节点
     * @param graphnode - 传入GraphNode的对象
     * @roseuid 3DB671B7008B
     */
    public void remove(GraphNode graphnode)
    {
        selectedNodes.removeElement(graphnode);
        fireGraphSelectionModelChanged(1, this, graphnode, null);
    }

    /**
     * 如果给定的链接没有被选中而且该链接是可选的在选定的链接中添加该链接
     * @param graphlink - 传入GraphLink的对象
     * @roseuid 3DB671B7009F
     */
    public void add(GraphLink graphlink)
    {
        if(!isSelected(graphlink) && isLinkSelectionAllowed())
        {
            if(!isMultiSelectionAllowed())
                clearSelection();
            selectedLinks.addElement(graphlink);
            fireGraphSelectionModelChanged(2, this, null, graphlink);
        }
    }

    /**
     * 删除链接
     * @param graphlink - 传入GraphLink的对象
     * @roseuid 3DB671B700B3
     */
    public void remove(GraphLink graphlink)
    {
        selectedLinks.removeElement(graphlink);
        fireGraphSelectionModelChanged(3, this, null, graphlink);
    }

    /**
     * 取得所有被选择的节点，返回枚举型
     * @return java.util.Enumeration
     * @roseuid 3DB671B700D1
     */
    public Enumeration allSelectedNodes()
    {
        return selectedNodes.elements();
    }

    /**
     * 取得所有被选择的链接，返回枚举型
     * @return java.util.Enumeration
     * @roseuid 3DB671B700E5
     */
    public Enumeration allSelectedLinks()
    {
        return selectedLinks.elements();
    }

    /**
     * 添加图元选择模式监听器
     * @param graphselectionmodellistener - 传入GraphSelectionModelListener的对象
     * @roseuid 3DB671B70103
     */
    public void addGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener)
    {
        listenerList.add(GraphSelectionModelListener.class, graphselectionmodellistener);
    }

    /**
     * 删除图元选择模式监听器
     * @param graphselectionmodellistener - 传入GraphSelectionModelListener的对象
     * @roseuid 3DB671B70121
     */
    public void removeGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener)
    {
        listenerList.remove(GraphSelectionModelListener.class, graphselectionmodellistener);
    }

    /**
     * 得到选定节点的最后一个节点，返回GraphNode型
     * @return GraphNode
     * @roseuid 3DB671B7013F
     */
    public GraphNode getLastSelectedNode()
    {
        GraphNode graphnode = null;
        try
        {
            graphnode = (GraphNode)selectedNodes.lastElement();
        }catch(NoSuchElementException _ex)
        {}
        return graphnode;
    }

    /**
     * 得到选定链接的最后一个链接，返回GraphNode型
     * @return GraphLink
     * @roseuid 3DB671B7015D
     */
    public GraphLink getLastSelectedLink()
    {
        GraphLink graphlink = null;
        try
        {
            graphlink = (GraphLink)selectedLinks.lastElement();
        }catch(NoSuchElementException _ex)
        {}
        return graphlink;
    }
}