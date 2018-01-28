/**
 * 生成程序 DefaultGraphModel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.graph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.faw_qm.cappclients.capproute.util.GraphRB;
import com.faw_qm.cappclients.capproute.util.InvalidLinkException;
import com.faw_qm.cappclients.capproute.util.InvalidNodeException;
import com.faw_qm.cappclients.capproute.util.LinkAlreadyExistException;
import com.faw_qm.cappclients.capproute.util.LinkDoesNotExistException;
import com.faw_qm.cappclients.capproute.util.NodeAlreadyExistException;
import com.faw_qm.cappclients.capproute.util.NodeDoesNotExistException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.technics.route.model.RouteNodeInfo;

/**
 * <p>
 * Title:默认图元模型
 * </p>
 * <p>
 * Description:插入、删除模型中的节点及其对应的连接
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

public class DefaultGraphModel implements GraphModel, PropertyChangeListener,
        Serializable {
    /** 放置图形节点的集合 */
    Vector graphNodeVector;

    /** 事件监听表 */
    private EventListenerList listenerList;

    /** 图元连接的集合 */
    Vector graphLinkVector;

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 资源文件 */
    private static final String RESOURCE = "com.faw_qm.cappclients.capproute.util.GraphRB";

    /**
     * 构造器
     */
    public DefaultGraphModel() {
        graphNodeVector = new Vector();
        graphLinkVector = new Vector();
        listenerList = new EventListenerList();
    }

    /**
     * 向节点哈希表中添加指定的元素，并实现属性改变监听，改变图元模式。
     *
     * @param graphnode
     *            GraphNode哈希表中的值
     * @throws NodeAlreadyExistException
     * @throws InvalidNodeException
     */
    public void addNode(GraphNode graphnode) throws NodeAlreadyExistException,
            InvalidNodeException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.addNode() begin...");
        validateNodeInexistence(graphnode);
        graphNodeVector.addElement(graphnode);
        graphnode.addPropertyChangeListener(this);
        fireGraphModelChanged(0, this, graphnode, null);
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.addNode() end !");
    }

    /**
     * 在前驱接点和后继接点都存在且不存在连接时，添加连接并改变图元模式.
     *
     * @param graphlink
     *            GraphLink
     * @throws NodeDoesNotExistException
     * @throws LinkAlreadyExistException
     * @throws InvalidLinkException
     */
    public void addLink(GraphLink graphlink) throws NodeDoesNotExistException,
            LinkAlreadyExistException, InvalidLinkException, QMRemoteException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.addLink() begin...");
        validateNodeExistence(graphlink.getPredecessor());
        validateNodeExistence(graphlink.getSuccessor());
        validateLinkInexistence(graphlink);
        //		CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,去掉同一加工单位不能在同一位置重复出现判断     
       // isValidateLink(graphlink);
//		CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,去掉同一加工单位不能在同一位置重复出现判断
        graphLinkVector.addElement(graphlink);
        graphlink.addPropertyChangeListener(this);
        fireGraphModelChanged(3, this, null, graphlink);
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.addLink() end!");
    }

    /**
     * 判断指定的连接的左右节点是否相同（单位和类型都相同）。如果相同，则提示错误。
     *
     * @param link
     *            指定的连接
     * @throws QMRemoteException
     */
    private void isValidateLink(GraphLink link) throws QMRemoteException {
        //前驱节点
        RouteNodeInfo preNode = (RouteNodeInfo) link.getPredecessor()
                .getRouteItem().getObject();
        //后继节点
        RouteNodeInfo sucNode = (RouteNodeInfo) link.getSuccessor()
                .getRouteItem().getObject();
        if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
                && preNode.getRouteType().equals(sucNode.getRouteType())) {
            //同一加工单位不能在同一位置重复出现！
            //add by guoxl on 2008.04.22(更新路线状态下系统能保存闭环的路线图)
            link.getRouteItem().setState(RouteItem.DELETE);
            //add end by guoxl
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.DEPARTMENT_IS_RECYCLE, null));
        }
    }

    /**
     * 删除指定的节点及其所有相关的连接
     *
     * @param graphnode
     *            GraphNode
     * @throws NodeDoesNotExistException
     */
    public void removeNode(GraphNode graphnode)
            throws NodeDoesNotExistException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeNode() begin...");
        validateNodeExistence(graphnode);
        removeLinks(graphnode);
        graphNodeVector.removeElement(graphnode);
        graphnode.removePropertyChangeListener(this);
        fireGraphModelChanged(1, this, graphnode, null);
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeNode() end!");
    }

    /**
     * 从连接集合中删除指定节点的所有相关连接
     *
     * @param graphnode
     *            指定节点
     * @throws NodeDoesNotExistException
     */
    public void removeLinks(GraphNode graphnode)
            throws NodeDoesNotExistException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeLinks() begin...");
        validateNodeExistence(graphnode);
        //遍历并删除指定节点的所有连接
        for (Enumeration enumeration = findLinks(graphnode); enumeration
                .hasMoreElements();) {
            try {
                removeLink((GraphLink) enumeration.nextElement());
            } catch (LinkDoesNotExistException linkdoesnotexistexception) {
                System.out.println(linkdoesnotexistexception
                        .getLocalizedMessage());
                System.out.println("This exception should never occur !");
                linkdoesnotexistexception.printStackTrace();
            }
        }
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeLinks() end!");
    }

    /**
     * 从连接集合中删除指定的连接
     *
     * @param graphlink
     *            GraphLink类的对象
     * @throws LinkDoesNotExistException
     */
    public void removeLink(GraphLink graphlink)
            throws LinkDoesNotExistException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeLink() begin...");
        validateLinkExistence(graphlink);
        graphLinkVector.removeElement(graphlink);
        graphlink.removePropertyChangeListener(this);
        fireGraphModelChanged(4, this, null, graphlink);
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeLink() end!");
    }

    /**
     * 从连接集合中移除指定的两个节点间的连接
     *
     * @param graphnode
     *            前驱节点
     * @param graphnode1
     *            后继节点
     * @throws LinkDoesNotExistException
     * @throws NodeDoesNotExistException
     */
    public void removeLink(GraphNode graphnode, GraphNode graphnode1)
            throws LinkDoesNotExistException, NodeDoesNotExistException {
        removeLink(getLink(graphnode, graphnode1));
    }

    /**
     * 获得指定的两个节点之间的连接
     *
     * @param graphnode
     *            前驱节点
     * @param graphnode1
     *            后继节点
     * @return GraphLink
     * @throws LinkDoesNotExistException
     */
    public GraphLink getLink(GraphNode graphnode, GraphNode graphnode1)
            throws LinkDoesNotExistException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.getLink() begin...");
        boolean flag = false;
        GraphLink graphlink = null;
        for (Enumeration enumeration = findSuccessorLinks(graphnode); !flag
                && enumeration.hasMoreElements(); flag = graphnode1 == graphlink
                .getSuccessor())
            graphlink = (GraphLink) enumeration.nextElement();

        if (!flag) {
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.LINK_IS_NOT_EXIST, null);
            throw new LinkDoesNotExistException(s);
        } else {
            if (verbose)
                System.out
                        .println("capproute.graph.DefaultGraphModel.getLink() end...return: "
                                + graphlink);
            return graphlink;
        }
    }

    /**
     * 判断指定的节点是否在节点哈西表中。
     *
     * @param graphnode
     *            指定的节点
     * @return 如果哈西表中包含此节点，则返回true
     */
    public boolean isNodeInModel(GraphNode graphnode) {
        return graphNodeVector.contains(graphnode);
    }

    /**
     * 判断指定的连接是否在本模型的连接集合中
     *
     * @param graphlink
     *            指定的连接对象
     * @return 如果指定的连接在本模型的连接集合中，则返回true
     */
    public boolean isLinkInModel(GraphLink graphlink) {
        boolean flag = false;
        if (isNodeInModel(graphlink.getPredecessor())
                && isNodeInModel(graphlink.getSuccessor())) {
            GraphLink graphlink1;
            for (Enumeration enumeration = allLinks(); !flag
                    && enumeration.hasMoreElements(); flag = graphlink
                    .getPredecessor() == graphlink1.getPredecessor()
                    && graphlink.getSuccessor() == graphlink1.getSuccessor())
                graphlink1 = (GraphLink) enumeration.nextElement();

        }
        return flag;
    }

    /**
     * 验证指定的节点是否存在于本模型中，不存在时抛出异常.
     *
     * @param graphnode
     *            指定的节点对象
     * @throws NodeDoesNotExistException
     */
    private void validateNodeExistence(GraphNode graphnode)
            throws NodeDoesNotExistException {
        if (!isNodeInModel(graphnode)) {
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.NODE_IS_NOT_EXIST, null);
            throw new NodeDoesNotExistException(s);
        } else {
            return;
        }
    }

    /**
     * 验证指定的节点是否不存在于本模型中，存在时抛出接点已经存在异常
     *
     * @param graphnode
     *            指定的节点
     * @throws NodeAlreadyExistException
     */
    private void validateNodeInexistence(GraphNode graphnode)
            throws NodeAlreadyExistException {
        if (isNodeInModel(graphnode)) {
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.NODE_IS_ALREADY_EXIST, null);
            throw new NodeAlreadyExistException(s);
        } else {
            return;
        }
    }

    /**
     * 验证指定的连接是否存在于本模型中，不存在时抛出连接不存在异常
     *
     * @param graphlink
     *            指定的连接
     * @throws LinkDoesNotExistException
     */
    private void validateLinkExistence(GraphLink graphlink)
            throws LinkDoesNotExistException {
        if (!isLinkInModel(graphlink)) {
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.LINK_IS_NOT_EXIST, null);
            throw new LinkDoesNotExistException(s);
        } else {
            return;
        }
    }

    /**
     * 验证指定的连接是否不存在于本模型中，存在时抛出连接已经存在异常
     *
     * @param graphlink
     *            指定的连接
     * @throws LinkAlreadyExistException
     */
    private void validateLinkInexistence(GraphLink graphlink)
            throws LinkAlreadyExistException {
        if (isLinkInModel(graphlink)) {
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.LINK_IS_ALREADY_EXIST, null);
            throw new LinkAlreadyExistException(s);
        } else {
            return;
        }
    }

    /**
     * 更新模型（在指定的模型中，对指定的节点或连接进行插入、删除等操作）
     *
     * @param i
     *            事件代号（0——插入节点；1——删除节点；2——改变结构；3——插入连接；4——删除连接）
     * @param graphmodel
     *            GraphModel模型
     * @param graphnode
     *            节点对象
     * @param graphlink
     *            连接对象
     */
    public void fireGraphModelChanged(int i, GraphModel graphmodel,
            GraphNode graphnode, GraphLink graphlink) {
        Object aobj[] = listenerList.getListenerList();
        GraphModelEvent graphmodelevent = new GraphModelEvent(graphmodel,
                graphnode, graphlink);
        for (int j = aobj.length - 2; j >= 0; j -= 2)
            if (aobj[j] == GraphModelListener.class)
                switch (i) {
                case 0: // '\0'
                    ((GraphModelListener) aobj[j + 1])
                            .graphNodeInserted(graphmodelevent);
                    break;

                case 1: // '\001'
                    ((GraphModelListener) aobj[j + 1])
                            .graphNodeRemoved(graphmodelevent);
                    break;

                case 2: // '\002'
                    ((GraphModelListener) aobj[j + 1])
                            .graphStructureChanged(graphmodelevent);
                    break;

                case 3: // '\003'
                    ((GraphModelListener) aobj[j + 1])
                            .graphLinkInserted(graphmodelevent);
                    break;

                case 4: // '\004'
                    ((GraphModelListener) aobj[j + 1])
                            .graphLinkRemoved(graphmodelevent);
                    break;
                }

    }

    /**
     * 改变节点或连接的属性
     *
     * @param propertychangeevent
     *            事件对象
     */
    public void propertyChange(PropertyChangeEvent propertychangeevent) {
        Object aobj[] = listenerList.getListenerList();
        //如果是节点属性改变
        if (propertychangeevent.getSource() instanceof GraphNode) {
            for (int i = aobj.length - 2; i >= 0; i -= 2)
                if (aobj[i] == GraphModelListener.class)
                    ((GraphModelListener) aobj[i + 1])
                            .graphNodeChanged(propertychangeevent);

            return;
        }

        //如果是连接属性改变
        if (propertychangeevent.getSource() instanceof GraphLink) {
            for (int j = aobj.length - 2; j >= 0; j -= 2)
                if (aobj[j] == GraphModelListener.class)
                    ((GraphModelListener) aobj[j + 1])
                            .graphLinkChanged(propertychangeevent);

        }
    }

    /**
     * 获取本模型节点哈希表中所有的节点
     *
     * @return 集合元素为GraphNode对象
     */
    public Enumeration allNodes() {
        return graphNodeVector.elements();
    }

    /**
     * 获取本模型连接集合中所有的连接
     *
     * @return 集合元素为GraphLink对象
     */
    public Enumeration allLinks() {
        return graphLinkVector.elements();
    }

    /**
     * 获取给定节点的所有连接
     *
     * @param graphnode
     *            GraphNode对象
     * @return 集合元素为GraphLink对象
     */
    public Enumeration findLinks(GraphNode graphnode) {
        Vector vector = new Vector();
        //遍历所有的连接
        for (Enumeration enumeration = allLinks(); enumeration
                .hasMoreElements();) {
            GraphLink graphlink = (GraphLink) enumeration.nextElement();
            //判断和给定节点有关的连接
            if (graphnode == graphlink.getPredecessor()
                    || graphnode == graphlink.getSuccessor())
                vector.addElement(graphlink);
        }

        return vector.elements();
    }

    /**
     * 获取给定节点是后继接点的连接
     *
     * @param graphnode
     *            给定节点
     * @return 集合元素为GraphLink对象
     */
    public Enumeration findPredecessorLinks(GraphNode graphnode) {
        Vector vector = new Vector();
        //遍历所有的连接
        for (Enumeration enumeration = allLinks(); enumeration
                .hasMoreElements();) {
            GraphLink graphlink = (GraphLink) enumeration.nextElement();
            //判断给定接点是后继接点的连接
            if (graphnode == graphlink.getSuccessor())
                vector.addElement(graphlink);
        }

        return vector.elements();
    }

    /**
     * 获取给定节点是前驱节点的连接
     *
     * @param graphnode
     *            给定节点
     * @return 集合元素为GraphLink对象
     */
    public Enumeration findSuccessorLinks(GraphNode graphnode) {
        Vector vector = new Vector();
        //遍历所有的连接
        for (Enumeration enumeration = allLinks(); enumeration
                .hasMoreElements();) {
            GraphLink graphlink = (GraphLink) enumeration.nextElement();
            //判断给定接点是前驱接点的连接
            if (graphnode == graphlink.getPredecessor())
                vector.addElement(graphlink);
        }

        return vector.elements();
    }

    /**
     * 添加图元模式监听器
     *
     * @param graphmodellistener
     *            监听器对象
     */
    public void addGraphModelListener(GraphModelListener graphmodellistener) {
        listenerList.add(GraphModelListener.class,
                (GraphModelListener) graphmodellistener);
    }

    /**
     * 移除图元模式监听器
     *
     * @param graphmodellistener
     *            监听器对象
     */
    public void removeGraphModelListener(GraphModelListener graphmodellistener) {
        listenerList.remove(GraphModelListener.class,
                (GraphModelListener) graphmodellistener);
    }

}
