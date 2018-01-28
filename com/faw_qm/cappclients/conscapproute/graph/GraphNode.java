/** 
 * ���ɳ��� GraphNode.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.beans.PropertyChangeListener;

/**
 * <p> Title:ͼԪ�ڵ�ӿ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public interface GraphNode
{

    /**
     * �õ�ͼ��
     * @return java.lang.String
     * @roseuid 40297DEC00C6
     */
    public abstract String getIcon();

    /**
     * �õ�ѡ���ͼ��
     * @return java.lang.String
     * @roseuid 40297DEC00C7
     */
    public abstract String getSelectedIcon();

    /**
     * �õ�����λ��
     * @return java.awt.Point
     * @roseuid 40297DEC00C9
     */
    public abstract java.awt.Point getPosition();

    /**
     * ��������λ��
     * @param i - ���ͣ�����������ֵ
     * @param j - ���ͣ������������ֵ
     * @roseuid 40297DEC00CB
     */
    public abstract void setPosition(int i, int j);

    /**
     * ��ýڵ���Ϣ��װ����
     * @return RouteItem Ϊ�������ɳ־û������ṩ��ҵ�������Ϣ��װ��
     */
    public abstract RouteItem getRouteItem();

    /**
     * ��ýڵ�����
     * @return String
     */
    public abstract String getDepartmentName();

    /**
     * ����ͼƬ
     */
    public abstract void updateImage();

    /**
     * ������Ըı������
     * @param propertychangelistener - ����PropertyChangeListener�Ķ���
     * @roseuid 40297DEC00CE
     */
    public abstract void addPropertyChangeListener(PropertyChangeListener propertychangelistener);

    /**
     * ɾ�����Ըı������
     * @param propertychangelistener - ����PropertyChangeListener�Ķ���
     * @roseuid 40297DEC00D0
     */
    public abstract void removePropertyChangeListener(PropertyChangeListener propertychangelistener);
}