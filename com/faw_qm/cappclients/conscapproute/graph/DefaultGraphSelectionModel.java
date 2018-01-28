/** 
 * ���ɳ��� DefaultGraphSelectionModel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

/**
 * <p>
 * Title:Ĭ�ϵ�ͼԪѡ��ģʽ ѡ��ڵ������
 * </p>
 * <p>
 * Description:
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

import java.util.*;
import javax.swing.event.EventListenerList;
import com.faw_qm.clients.util.*;

public class DefaultGraphSelectionModel implements GraphSelectionModel
{
    /** �ڵ��Ƿ�����ѡ�� */
    public boolean nodeSelectionAllowed;

    /** �����Ƿ�����ѡ�� */
    public boolean linkSelectionAllowed;

    /** �Ƿ������ѡ */
    public boolean multiSelectionAllowed;

    /** ѡ�е����ӵļ��� */
    public Vector selectedLinks;

    /** ѡ�еĽڵ�ļ��� */
    public Vector selectedNodes;

    /** �¼������б� */
    private EventListenerList listenerList;

    /**
     * ������
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
     * �Ƿ����ѡ�нڵ㣬���ز���ֵ
     * @return boolean
     * @roseuid 3DB671B60350
     */
    public boolean isNodeSelectionAllowed()
    {
        return nodeSelectionAllowed;
    }

    /**
     * �ڵ�Ŀ�ѡ״̬
     * @param flag - boolean�ͣ������ڵ��Ƿ���Ա�ѡ��
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B6035A
     */
    public void setNodeSelectionAllowed(boolean flag)
    {
        nodeSelectionAllowed = flag;
        clearSelection();
    }

    /**
     * �Ƿ����ѡ������
     * @return boolean
     * @roseuid 3DB671B6036E
     */
    public boolean isLinkSelectionAllowed()
    {
        return linkSelectionAllowed;
    }

    /**
     * ���ӵĿ�ѡ״̬
     * @param flag - boolean�ͣ����������Ƿ��ѡ
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B60378
     */
    public void setLinkSelectionAllowed(boolean flag)
    {
        linkSelectionAllowed = flag;
        clearSelection();

    }

    /**
     * �Ƿ���Զ�ѡ*
     * @return boolean
     * @roseuid 3DB671B60383
     */
    public boolean isMultiSelectionAllowed()
    {
        return multiSelectionAllowed;
    }

    /**
     * ��̬��ѡ��״̬
     * @param flag - boolean�ͣ������Ƿ����ѡ����
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B6038C
     */
    public void setMultiSelectionAllowed(boolean flag)
    {
        multiSelectionAllowed = flag;
        clearSelection();
    }

    /**
     * ���ѡ��Ľӵ�
     * @return java.util.Vector
     * @roseuid 3DB671B603A1
     */
    public Vector getSelectedNodes()
    {
        return selectedNodes;
    }

    /**
     * ����ѡ���Ľڵ�
     * @param vector - Vector�ͣ�����һ������
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B603AA
     */
    public void setSelectedNodes(Vector vector)
    {
        selectedNodes = vector;
    }

    /**
     * �õ�ѡ������ӣ���������
     * @return java.util.Vector
     * @roseuid 3DB671B603BE
     */
    public Vector getSelectedLinks()
    {
        return selectedLinks;
    }

    /**
     * ����ѡ��������
     * @param vector - Vector�ͣ�����һ������
     * @throws QMPropertyVetoException
     * @roseuid 3DB671B603C8
     */
    public void setSelectedLinks(java.util.Vector vector)
    {
        selectedLinks = vector;
    }

    /**
     * �÷�������ص�ģ�ͣ��ڵ㣬���Ӳ������¼����ڵ�������Ƿ�ѡ�У�ģʽ�Ƿ���գ� ת������Ӧ�ļ�����
     * @param i - ���ͣ������̬ѡ��Ŀɹ�ѡ�����
     * @param graphselectionmodel - ����GraphSelectionModel�Ķ���
     * @param graphnode - ����GraphNode�Ķ���
     * @param graphlink - ����GraphLink�Ķ���
     * @roseuid 3DB671B603DC
     */
    public void fireGraphSelectionModelChanged(int i, GraphSelectionModel graphselectionmodel, GraphNode graphnode, GraphLink graphlink)
    {
        Object aobj[] = listenerList.getListenerList();
        GraphSelectionModelEvent graphselectionmodelevent = new GraphSelectionModelEvent(graphselectionmodel, graphnode, graphlink);
        for(int j = aobj.length - 2;j >= 0;j -= 2)
            //�ڲ�ͬ������¼���ͼ��ѡ��ģʽ�ĸı�
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
     * ��֤�ڵ��Ƿ�ѡ�������ز�����
     * @param graphnode - ����GraphNode�Ķ���
     * @return boolean
     * @roseuid 3DB671B70013
     */
    public boolean isSelected(GraphNode graphnode)
    {
        return selectedNodes.contains(graphnode);
    }

    /**
     * ��֤�����Ƿ�ѡ�������ز�����
     * @param graphlink - ����GraphLink�Ķ���
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
     * �õ���ѡ������ӵ���������������
     * @return int
     * @roseuid 3DB671B7004F
     */
    public int getLinksSelectedCount()
    {
        return selectedLinks.size();
    }

    /**
     * ���ѡ��Ľӵ������
     * @roseuid 3DB671B70063
     */
    public void clearSelection()
    {
        selectedNodes.removeAllElements();
        selectedLinks.removeAllElements();
        fireGraphSelectionModelChanged(4, this, null, null);
    }

    /**
     * ��������Ľڵ�û�б�ѡ�ж��Ҹýڵ��ǿ�ѡ����ѡ���Ľڵ�����Ӹýڵ�
     * @param graphnode - ����GraphNode�Ķ���
     * @roseuid 3DB671B7006D
     */
    public void add(GraphNode graphnode)
    {
        //�ڽӵ�����ѡ����û�б�ѡ��������
        if(!isSelected(graphnode) && isNodeSelectionAllowed())
        {
            if(!isMultiSelectionAllowed())
                clearSelection();
            selectedNodes.addElement(graphnode);
            fireGraphSelectionModelChanged(0, this, graphnode, null);
        }
    }

    /**
     * ɾ���ڵ�
     * @param graphnode - ����GraphNode�Ķ���
     * @roseuid 3DB671B7008B
     */
    public void remove(GraphNode graphnode)
    {
        selectedNodes.removeElement(graphnode);
        fireGraphSelectionModelChanged(1, this, graphnode, null);
    }

    /**
     * �������������û�б�ѡ�ж��Ҹ������ǿ�ѡ����ѡ������������Ӹ�����
     * @param graphlink - ����GraphLink�Ķ���
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
     * ɾ������
     * @param graphlink - ����GraphLink�Ķ���
     * @roseuid 3DB671B700B3
     */
    public void remove(GraphLink graphlink)
    {
        selectedLinks.removeElement(graphlink);
        fireGraphSelectionModelChanged(3, this, null, graphlink);
    }

    /**
     * ȡ�����б�ѡ��Ľڵ㣬����ö����
     * @return java.util.Enumeration
     * @roseuid 3DB671B700D1
     */
    public Enumeration allSelectedNodes()
    {
        return selectedNodes.elements();
    }

    /**
     * ȡ�����б�ѡ������ӣ�����ö����
     * @return java.util.Enumeration
     * @roseuid 3DB671B700E5
     */
    public Enumeration allSelectedLinks()
    {
        return selectedLinks.elements();
    }

    /**
     * ���ͼԪѡ��ģʽ������
     * @param graphselectionmodellistener - ����GraphSelectionModelListener�Ķ���
     * @roseuid 3DB671B70103
     */
    public void addGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener)
    {
        listenerList.add(GraphSelectionModelListener.class, graphselectionmodellistener);
    }

    /**
     * ɾ��ͼԪѡ��ģʽ������
     * @param graphselectionmodellistener - ����GraphSelectionModelListener�Ķ���
     * @roseuid 3DB671B70121
     */
    public void removeGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener)
    {
        listenerList.remove(GraphSelectionModelListener.class, graphselectionmodellistener);
    }

    /**
     * �õ�ѡ���ڵ�����һ���ڵ㣬����GraphNode��
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
     * �õ�ѡ�����ӵ����һ�����ӣ�����GraphNode��
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