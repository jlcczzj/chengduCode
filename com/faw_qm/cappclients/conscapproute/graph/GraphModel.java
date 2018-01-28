/** 
 * ���ɳ��� GraphModel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.Enumeration;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;

/**
 * <p> Title:ͼԪģʽ���ڵ�����ӣ��ӿ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public interface GraphModel
{

    /**
     * ������нڵ�
     * @return java.util.Enumeration
     * @roseuid 3DB67339005E
     */
    public abstract Enumeration allNodes();

    /**
     * �����������
     * @return java.util.Enumeration
     * @roseuid 3DB673390068
     */
    public abstract Enumeration allLinks();

    /**
     * �������ָ���ڵ��ϵ���������
     * @param graphnode ָ���ڵ�
     * @return java.util.Enumeration
     * @roseuid 3DB673390072
     */
    public abstract Enumeration findLinks(GraphNode graphnode);

    /**
     * ��ӽڵ�
     * @param graphnode GraphNode�Ķ���
     * @throws QMException 
     * @throws NodeAlreadyExistException
     * @throws InvalidNodeException
     * @roseuid 3DB673390086
     */
    public abstract void addNode(GraphNode graphnode) throws QMException;

    /**
     * �������
     * @param graphlink GraphLink�Ķ���
     * @throws QMException 
     * @throws NodeDoesNotExistException
     * @throws LinkAlreadyExistException
     * @throws InvalidLinkException
     * @roseuid 3DB6733900A4
     */
    public abstract void addLink(GraphLink graphlink) throws QMException;

    /**
     * ɾ���ڵ�
     * @param graphnode GraphNode�Ķ���
     * @throws QMException 
     * @throws NodeDoesNotExistException
     * @roseuid 3DB6733900B8
     */
    public abstract void removeNode(GraphNode graphnode) throws QMException;

    /**
     * ɾ������
     * @param graphlink GraphLink�Ķ���
     * @throws QMException 
     * @throws LinkDoesNotExistException
     * @roseuid 3DB6733900CC
     */
    public abstract void removeLink(GraphLink graphlink) throws QMException;

    /**
     * ����������ӵ�ǰ���ڵ�
     * @param graphnode - ����GraphNode�Ķ���
     * @return java.util.Enumeration
     * @roseuid 3DB6733900EA
     */
    public abstract Enumeration findPredecessorLinks(GraphNode graphnode);

    /**
     * ����������ӵĺ�̽ڵ�
     * @param graphnode - ����GraphNode�Ķ���
     * @return java.util.Enumeration
     * @roseuid 3DB6733900FE
     */
    public abstract Enumeration findSuccessorLinks(GraphNode graphnode);

    /**
     * ���ͼԪģʽ������
     * @param graphmodellistener - ����GraphModelListener�Ķ���
     * @roseuid 3DB673390112
     */
    public abstract void addGraphModelListener(GraphModelListener graphmodellistener);

    /**
     * ɾ��ͼԪģʽ������
     * @param graphmodellistener - ����GraphModelListener�Ķ���
     * @roseuid 3DB673390130
     */
    public abstract void removeGraphModelListener(GraphModelListener graphmodellistener);

}