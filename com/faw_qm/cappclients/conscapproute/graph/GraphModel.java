/** 
 * 生成程序 GraphModel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.Enumeration;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;

/**
 * <p> Title:图元模式（节点或连接）接口 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public interface GraphModel
{

    /**
     * 获得所有节点
     * @return java.util.Enumeration
     * @roseuid 3DB67339005E
     */
    public abstract Enumeration allNodes();

    /**
     * 获得所有链接
     * @return java.util.Enumeration
     * @roseuid 3DB673390068
     */
    public abstract Enumeration allLinks();

    /**
     * 搜索获得指定节点上的所有连接
     * @param graphnode 指定节点
     * @return java.util.Enumeration
     * @roseuid 3DB673390072
     */
    public abstract Enumeration findLinks(GraphNode graphnode);

    /**
     * 添加节点
     * @param graphnode GraphNode的对象
     * @throws QMException 
     * @throws NodeAlreadyExistException
     * @throws InvalidNodeException
     * @roseuid 3DB673390086
     */
    public abstract void addNode(GraphNode graphnode) throws QMException;

    /**
     * 添加连接
     * @param graphlink GraphLink的对象
     * @throws QMException 
     * @throws NodeDoesNotExistException
     * @throws LinkAlreadyExistException
     * @throws InvalidLinkException
     * @roseuid 3DB6733900A4
     */
    public abstract void addLink(GraphLink graphlink) throws QMException;

    /**
     * 删除节点
     * @param graphnode GraphNode的对象
     * @throws QMException 
     * @throws NodeDoesNotExistException
     * @roseuid 3DB6733900B8
     */
    public abstract void removeNode(GraphNode graphnode) throws QMException;

    /**
     * 删除连接
     * @param graphlink GraphLink的对象
     * @throws QMException 
     * @throws LinkDoesNotExistException
     * @roseuid 3DB6733900CC
     */
    public abstract void removeLink(GraphLink graphlink) throws QMException;

    /**
     * 搜索获得连接的前驱节点
     * @param graphnode - 传入GraphNode的对象
     * @return java.util.Enumeration
     * @roseuid 3DB6733900EA
     */
    public abstract Enumeration findPredecessorLinks(GraphNode graphnode);

    /**
     * 搜索获得连接的后继节点
     * @param graphnode - 传入GraphNode的对象
     * @return java.util.Enumeration
     * @roseuid 3DB6733900FE
     */
    public abstract Enumeration findSuccessorLinks(GraphNode graphnode);

    /**
     * 添加图元模式监听器
     * @param graphmodellistener - 传入GraphModelListener的对象
     * @roseuid 3DB673390112
     */
    public abstract void addGraphModelListener(GraphModelListener graphmodellistener);

    /**
     * 删除图元模式监听器
     * @param graphmodellistener - 传入GraphModelListener的对象
     * @roseuid 3DB673390130
     */
    public abstract void removeGraphModelListener(GraphModelListener graphmodellistener);

}