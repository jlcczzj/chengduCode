/** 
 * ���ɳ��� RouteItem.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import com.faw_qm.framework.service.*;
import java.io.*;

/**
 * <p> Title:Ϊ�������ɳ־û������ṩ��ҵ�������Ϣ��װ�� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class RouteItem implements Serializable
{
    /** ҵ�����(·�߽ڵ������ֵ����) */
    private BaseValueIfc object;

    /** ���ҵ������״̬ */
    private String state;

    /** ҵ������״̬:�½� */
    public static final String CREATE = "create";

    /** ҵ������״̬:���� */
    public static final String UPDATE = "update";

    /** ҵ������״̬:ɾ�� */
    public static final String DELETE = "delete";

    private static final String versionID = "$Header:RouteItem.java 4 $";

    /**
     * ���캯��
     */
    public RouteItem()
    {}

    /**
     * ����һ������ָ��ҵ������RouteItem
     * @param obj ָ��ҵ�����(·�߽ڵ������ֵ����)
     */
    public RouteItem(BaseValueIfc obj)
    {
        setObject(obj);
    }

    /**
     * ����ҵ�����
     * @param obj ָ��ҵ�����(·�߽ڵ������ֵ����)
     */
    public void setObject(BaseValueIfc obj)
    {
        object = obj;
    }

    /**
     * ���ҵ�����
     * @return ·�߽ڵ������ֵ����
     */
    public BaseValueIfc getObject()
    {
        return object;
    }

    /**
     * ��ýڵ�����ӵ�״̬
     * @return �½������»�ɾ��
     */
    public String getState()
    {
        return state;
    }

    /**
     * ���� �ڵ������ �� ״̬
     * @param state �½������»�ɾ��
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * д����
     * @param oos java.io.ObjectOutputStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException
    {
        oos.defaultWriteObject();
    }

    /**
     * ������
     * @param ois java.io.ObjectInputStream
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
    {
        ois.defaultReadObject();
    }

}