/** 
 * ���ɳ��� GraphSelectionModelListener.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.EventListener;

/**
 * <p> Title:ͼԪѡ��ģʽ����ѡ���δ��ѡ�񣩼����� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public interface GraphSelectionModelListener extends EventListener
{
    public static final int NODE_SELECTED = 0;

    public static final int NODE_UNSELECTED = 1;

    public static final int LINK_SELECTED = 2;

    public static final int LINK_UNSELECTED = 3;

    public static final int SELECTION_CLEARED = 4;

    /**
     * �ӵ㱻ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D8016A
     */
    public abstract void graphNodeSelected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * �ӵ�û�б�ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D8016C
     */
    public abstract void graphNodeUnselected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * ���ӱ�ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D8016E
     */
    public abstract void graphLinkSelected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * ����û�б�ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D80170
     */
    public abstract void graphLinkUnselected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * ���ѡ��Ľڵ������
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D80172
     */
    public abstract void graphSelectionCleared(GraphSelectionModelEvent graphselectionmodelevent);
}