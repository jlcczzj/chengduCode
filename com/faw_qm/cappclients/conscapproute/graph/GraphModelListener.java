/** 
 * ���ɳ��� GraphModelListener.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.EventListener;
import java.beans.PropertyChangeEvent;

/**
 * <p> Title:ͼԪģʽ���ڵ�����ӣ��������ӿ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
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
     * ����ڵ�
     * @param graphmodelevent - ����GraphModelEvent�Ķ���
     * @roseuid 40288C4903C9
     */
    public abstract void graphNodeInserted(GraphModelEvent graphmodelevent);

    /**
     * �ı�ڵ������
     * @param propertychangeevent - ����PropertyChangeEvent�Ķ���
     * @roseuid 40288C9D038E
     */
    public abstract void graphNodeChanged(PropertyChangeEvent propertychangeevent);

    /**
     * ɾ���ڵ�
     * @param graphmodelevent
     * @roseuid 40288CE200EE
     */
    public abstract void graphNodeRemoved(GraphModelEvent graphmodelevent);

    /**
     * �ı�ͼԪ�ṹ
     * @param graphmodelevent
     * @roseuid 40288D1C0174
     */
    public abstract void graphStructureChanged(GraphModelEvent graphmodelevent);

    /**
     * ��������
     * @param graphmodelevent
     * @roseuid 40288D47007B
     */
    public abstract void graphLinkInserted(GraphModelEvent graphmodelevent);

    /**
     * �ı���������
     * @param propertychangeevent
     * @roseuid 40288D7F00FE
     */
    public abstract void graphLinkChanged(PropertyChangeEvent propertychangeevent);

    /**
     * ɾ������
     * @param graphmodelevent
     * @roseuid 40288DBA01E9
     */
    public abstract void graphLinkRemoved(GraphModelEvent graphmodelevent);
}