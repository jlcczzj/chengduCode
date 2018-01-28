/** 
 * ���ɳ��� GraphModelEvent.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.EventObject;
import java.io.Serializable;

/**
 * <p> Title:ͼԪģ�ͼ����¼� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class GraphModelEvent extends EventObject implements Serializable
{
    public GraphNode node;

    public GraphLink link;

    /**
     * ������
     * @param graphmodel - ����GraphModel�Ķ���
     * @param graphnode - ����GraphNode�Ķ���
     * @param graphlink - ����GraphLink�Ķ���
     * @roseuid 4028CF2D0253
     */
    public GraphModelEvent(GraphModel graphmodel, GraphNode graphnode, GraphLink graphlink)
    {
        super(graphmodel);
        node = graphnode;
        link = graphlink;
    }

    /**
     * �õ��ڵ�
     * @return capproute.graph.GraphNode
     * @roseuid 4028CF2D0257
     */
    public GraphNode getNode()
    {
        return node;
    }

    /**
     * �õ�����
     * @return capproute.graph.GraphLink
     * @roseuid 4028CF2D0258
     */
    public GraphLink getLink()
    {
        return link;
    }
}