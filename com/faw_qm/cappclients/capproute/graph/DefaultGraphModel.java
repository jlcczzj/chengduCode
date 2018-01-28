/**
 * ���ɳ��� DefaultGraphModel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * Title:Ĭ��ͼԪģ��
 * </p>
 * <p>
 * Description:���롢ɾ��ģ���еĽڵ㼰���Ӧ������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */

public class DefaultGraphModel implements GraphModel, PropertyChangeListener,
        Serializable {
    /** ����ͼ�νڵ�ļ��� */
    Vector graphNodeVector;

    /** �¼������� */
    private EventListenerList listenerList;

    /** ͼԪ���ӵļ��� */
    Vector graphLinkVector;

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ��Դ�ļ� */
    private static final String RESOURCE = "com.faw_qm.cappclients.capproute.util.GraphRB";

    /**
     * ������
     */
    public DefaultGraphModel() {
        graphNodeVector = new Vector();
        graphLinkVector = new Vector();
        listenerList = new EventListenerList();
    }

    /**
     * ��ڵ��ϣ�������ָ����Ԫ�أ���ʵ�����Ըı�������ı�ͼԪģʽ��
     *
     * @param graphnode
     *            GraphNode��ϣ���е�ֵ
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
     * ��ǰ���ӵ�ͺ�̽ӵ㶼�����Ҳ���������ʱ��������Ӳ��ı�ͼԪģʽ.
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
        //		CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,ȥ��ͬһ�ӹ���λ������ͬһλ���ظ������ж�     
       // isValidateLink(graphlink);
//		CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,ȥ��ͬһ�ӹ���λ������ͬһλ���ظ������ж�
        graphLinkVector.addElement(graphlink);
        graphlink.addPropertyChangeListener(this);
        fireGraphModelChanged(3, this, null, graphlink);
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.addLink() end!");
    }

    /**
     * �ж�ָ�������ӵ����ҽڵ��Ƿ���ͬ����λ�����Ͷ���ͬ���������ͬ������ʾ����
     *
     * @param link
     *            ָ��������
     * @throws QMRemoteException
     */
    private void isValidateLink(GraphLink link) throws QMRemoteException {
        //ǰ���ڵ�
        RouteNodeInfo preNode = (RouteNodeInfo) link.getPredecessor()
                .getRouteItem().getObject();
        //��̽ڵ�
        RouteNodeInfo sucNode = (RouteNodeInfo) link.getSuccessor()
                .getRouteItem().getObject();
        if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
                && preNode.getRouteType().equals(sucNode.getRouteType())) {
            //ͬһ�ӹ���λ������ͬһλ���ظ����֣�
            //add by guoxl on 2008.04.22(����·��״̬��ϵͳ�ܱ���ջ���·��ͼ)
            link.getRouteItem().setState(RouteItem.DELETE);
            //add end by guoxl
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.DEPARTMENT_IS_RECYCLE, null));
        }
    }

    /**
     * ɾ��ָ���Ľڵ㼰��������ص�����
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
     * �����Ӽ�����ɾ��ָ���ڵ�������������
     *
     * @param graphnode
     *            ָ���ڵ�
     * @throws NodeDoesNotExistException
     */
    public void removeLinks(GraphNode graphnode)
            throws NodeDoesNotExistException {
        if (verbose)
            System.out
                    .println("capproute.graph.DefaultGraphModel.removeLinks() begin...");
        validateNodeExistence(graphnode);
        //������ɾ��ָ���ڵ����������
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
     * �����Ӽ�����ɾ��ָ��������
     *
     * @param graphlink
     *            GraphLink��Ķ���
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
     * �����Ӽ������Ƴ�ָ���������ڵ�������
     *
     * @param graphnode
     *            ǰ���ڵ�
     * @param graphnode1
     *            ��̽ڵ�
     * @throws LinkDoesNotExistException
     * @throws NodeDoesNotExistException
     */
    public void removeLink(GraphNode graphnode, GraphNode graphnode1)
            throws LinkDoesNotExistException, NodeDoesNotExistException {
        removeLink(getLink(graphnode, graphnode1));
    }

    /**
     * ���ָ���������ڵ�֮�������
     *
     * @param graphnode
     *            ǰ���ڵ�
     * @param graphnode1
     *            ��̽ڵ�
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
     * �ж�ָ���Ľڵ��Ƿ��ڽڵ�������С�
     *
     * @param graphnode
     *            ָ���Ľڵ�
     * @return ����������а����˽ڵ㣬�򷵻�true
     */
    public boolean isNodeInModel(GraphNode graphnode) {
        return graphNodeVector.contains(graphnode);
    }

    /**
     * �ж�ָ���������Ƿ��ڱ�ģ�͵����Ӽ�����
     *
     * @param graphlink
     *            ָ�������Ӷ���
     * @return ���ָ���������ڱ�ģ�͵����Ӽ����У��򷵻�true
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
     * ��ָ֤���Ľڵ��Ƿ�����ڱ�ģ���У�������ʱ�׳��쳣.
     *
     * @param graphnode
     *            ָ���Ľڵ����
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
     * ��ָ֤���Ľڵ��Ƿ񲻴����ڱ�ģ���У�����ʱ�׳��ӵ��Ѿ������쳣
     *
     * @param graphnode
     *            ָ���Ľڵ�
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
     * ��ָ֤���������Ƿ�����ڱ�ģ���У�������ʱ�׳����Ӳ������쳣
     *
     * @param graphlink
     *            ָ��������
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
     * ��ָ֤���������Ƿ񲻴����ڱ�ģ���У�����ʱ�׳������Ѿ������쳣
     *
     * @param graphlink
     *            ָ��������
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
     * ����ģ�ͣ���ָ����ģ���У���ָ���Ľڵ�����ӽ��в��롢ɾ���Ȳ�����
     *
     * @param i
     *            �¼����ţ�0��������ڵ㣻1����ɾ���ڵ㣻2�����ı�ṹ��3�����������ӣ�4����ɾ�����ӣ�
     * @param graphmodel
     *            GraphModelģ��
     * @param graphnode
     *            �ڵ����
     * @param graphlink
     *            ���Ӷ���
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
     * �ı�ڵ�����ӵ�����
     *
     * @param propertychangeevent
     *            �¼�����
     */
    public void propertyChange(PropertyChangeEvent propertychangeevent) {
        Object aobj[] = listenerList.getListenerList();
        //����ǽڵ����Ըı�
        if (propertychangeevent.getSource() instanceof GraphNode) {
            for (int i = aobj.length - 2; i >= 0; i -= 2)
                if (aobj[i] == GraphModelListener.class)
                    ((GraphModelListener) aobj[i + 1])
                            .graphNodeChanged(propertychangeevent);

            return;
        }

        //������������Ըı�
        if (propertychangeevent.getSource() instanceof GraphLink) {
            for (int j = aobj.length - 2; j >= 0; j -= 2)
                if (aobj[j] == GraphModelListener.class)
                    ((GraphModelListener) aobj[j + 1])
                            .graphLinkChanged(propertychangeevent);

        }
    }

    /**
     * ��ȡ��ģ�ͽڵ��ϣ�������еĽڵ�
     *
     * @return ����Ԫ��ΪGraphNode����
     */
    public Enumeration allNodes() {
        return graphNodeVector.elements();
    }

    /**
     * ��ȡ��ģ�����Ӽ��������е�����
     *
     * @return ����Ԫ��ΪGraphLink����
     */
    public Enumeration allLinks() {
        return graphLinkVector.elements();
    }

    /**
     * ��ȡ�����ڵ����������
     *
     * @param graphnode
     *            GraphNode����
     * @return ����Ԫ��ΪGraphLink����
     */
    public Enumeration findLinks(GraphNode graphnode) {
        Vector vector = new Vector();
        //�������е�����
        for (Enumeration enumeration = allLinks(); enumeration
                .hasMoreElements();) {
            GraphLink graphlink = (GraphLink) enumeration.nextElement();
            //�жϺ͸����ڵ��йص�����
            if (graphnode == graphlink.getPredecessor()
                    || graphnode == graphlink.getSuccessor())
                vector.addElement(graphlink);
        }

        return vector.elements();
    }

    /**
     * ��ȡ�����ڵ��Ǻ�̽ӵ������
     *
     * @param graphnode
     *            �����ڵ�
     * @return ����Ԫ��ΪGraphLink����
     */
    public Enumeration findPredecessorLinks(GraphNode graphnode) {
        Vector vector = new Vector();
        //�������е�����
        for (Enumeration enumeration = allLinks(); enumeration
                .hasMoreElements();) {
            GraphLink graphlink = (GraphLink) enumeration.nextElement();
            //�жϸ����ӵ��Ǻ�̽ӵ������
            if (graphnode == graphlink.getSuccessor())
                vector.addElement(graphlink);
        }

        return vector.elements();
    }

    /**
     * ��ȡ�����ڵ���ǰ���ڵ������
     *
     * @param graphnode
     *            �����ڵ�
     * @return ����Ԫ��ΪGraphLink����
     */
    public Enumeration findSuccessorLinks(GraphNode graphnode) {
        Vector vector = new Vector();
        //�������е�����
        for (Enumeration enumeration = allLinks(); enumeration
                .hasMoreElements();) {
            GraphLink graphlink = (GraphLink) enumeration.nextElement();
            //�жϸ����ӵ���ǰ���ӵ������
            if (graphnode == graphlink.getPredecessor())
                vector.addElement(graphlink);
        }

        return vector.elements();
    }

    /**
     * ���ͼԪģʽ������
     *
     * @param graphmodellistener
     *            ����������
     */
    public void addGraphModelListener(GraphModelListener graphmodellistener) {
        listenerList.add(GraphModelListener.class,
                (GraphModelListener) graphmodellistener);
    }

    /**
     * �Ƴ�ͼԪģʽ������
     *
     * @param graphmodellistener
     *            ����������
     */
    public void removeGraphModelListener(GraphModelListener graphmodellistener) {
        listenerList.remove(GraphModelListener.class,
                (GraphModelListener) graphmodellistener);
    }

}
