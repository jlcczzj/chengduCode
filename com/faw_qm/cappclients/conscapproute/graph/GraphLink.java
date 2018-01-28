/** 
 * ���ɳ��� GraphLink.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.beans.PropertyChangeListener;

/**
 * <p> Title:ͼԪ���ӽӿ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public interface GraphLink
{

    /**
     * ���󷽷�����ȡ���ӵ�ǰ���ڵ�
     * @return capproute.graph.GraphNode
     * @roseuid 40297FDF01A0
     */
    public abstract GraphNode getPredecessor();

    /**
     * ���󷽷�����ȡ���ӵĺ�̽ڵ�
     * @return capproute.graph.GraphNode
     * @roseuid 40297FDF01A1
     */
    public abstract GraphNode getSuccessor();

    /**
     * ������ӵ���Ϣ��װ����
     * @return RouteItem Ϊ�������ɳ־û������ṩ��ҵ�������Ϣ��װ��
     */
    public abstract RouteItem getRouteItem();

    /**
     * ���󷽷���������Ըı������
     * @param propertychangelistener ����������
     * @roseuid 40297FDF01A3
     */
    public abstract void addPropertyChangeListener(PropertyChangeListener propertychangelistener);

    /**
     * ���󷽷���ɾ�����Ըı������
     * @param propertychangelistener ����������
     * @roseuid 40297FDF01A5
     */
    public abstract void removePropertyChangeListener(PropertyChangeListener propertychangelistener);
}