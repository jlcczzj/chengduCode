/** 
 * ���ɳ��� GraphSelectionModel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

/**
 * <p> Title:ͼԪѡ��ģʽ����ѡ���δ��ѡ�񣩽ӿ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public interface GraphSelectionModel
{

    /**
     * �жϽڵ��Ƿ�ѡ
     * @param graphnode - ����GraphNode�Ķ���
     * @return boolean
     * @roseuid 4028D38C037C
     */
    public abstract boolean isSelected(GraphNode graphnode);

    /**
     * �ж������Ƿ�ѡ
     * @param graphlink - ����GraphLink�Ķ���
     * @return boolean
     * @roseuid 4028D38C037E
     */
    public abstract boolean isSelected(GraphLink graphlink);

    /**
     * �õ���ѡ��Ľڵ� ������
     * @return int
     * @roseuid 4028D38C0380
     */
    public abstract int getNodesSelectedCount();

    /**
     * �õ���ѡ������ӵ�����
     * @return int
     * @roseuid 4028D38C0381
     */
    public abstract int getLinksSelectedCount();

    /**
     * ���ѡ��Ľڵ������
     * @roseuid 4028D38C0382
     */
    public abstract void clearSelection();

    /**
     * ��ӽڵ�
     * @param graphnode - ����GraphNode�Ķ���
     * @roseuid 4028D38C0383
     */
    public abstract void add(GraphNode graphnode);

    /**
     * ɾ���ڵ�
     * @param graphnode - ����GraphNode�Ķ���
     * @roseuid 4028D38C0385
     */
    public abstract void remove(GraphNode graphnode);

    /**
     * �������
     * @param graphlink - ����GraphLink�Ķ���
     * @roseuid 4028D38C0387
     */
    public abstract void add(GraphLink graphlink);

    /**
     * ɾ������
     * @param graphlink - ����GraphLink�Ķ���
     * @roseuid 4028D38C0389
     */
    public abstract void remove(GraphLink graphlink);

    /**
     * �õ����б�ѡ��Ľڵ�
     * @return java.util.Enumeration
     * @roseuid 4028D38C038B
     */
    public abstract java.util.Enumeration allSelectedNodes();

    /**
     * �õ����б�ѡ�������
     * @return java.util.Enumeration
     * @roseuid 4028D38C038C
     */
    public abstract java.util.Enumeration allSelectedLinks();

    /**
     * ���ͼԪѡ��ģʽ������
     * @param graphselectionmodellistener - ����GraphSelectionModelListener�Ķ���
     * @roseuid 4028D38C038D
     */
    public abstract void addGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener);

    /**
     * ɾ��ͼԪѡ��ģʽ������
     * @param graphselectionmodellistener - ����GraphSelectionModelListener�Ķ���
     * @roseuid 4028D38C038F
     */
    public abstract void removeGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener);

    /**
     * �õ����ѡ��Ľڵ�
     * @return GraphNode
     * @roseuid 4028D38C0391
     */
    public abstract GraphNode getLastSelectedNode();

    /**
     * �õ����ѡ�������
     * @return GraphLink
     * @roseuid 4028D38C0392
     */
    public abstract GraphLink getLastSelectedLink();
}