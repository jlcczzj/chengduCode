/**
 * ���ɳ���DefaultGraphNode.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Χ��,ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.Point;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.util.RouteCategoryType;

/**
 * <p> Title:Ĭ��ͼԪ�ڵ� </p> <p> Description:ά���ڵ�Ľ������Ժͳ־û����� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class DefaultGraphNode implements GraphNode, PropertyChangeListener, Externalizable
{
    /** ��������� */
    private static final String CLASSNAME;

    /** ͼ�� */
    public static final String ICON = "icon";

    private String icon;

    /** ��ѡ���ͼ�� */
    public static final String SELECTED_ICON = "selectedIcon";

    private String selectedIcon;

    /** λ�� */
    public static final String POSITION = "position";

    /** ������ */
    public static final String LISTENER_LIST = "listenerList";

    private EventListenerList listenerList;

    /** �ڵ��װ���� */
    private RouteItem node;

    /** ǰ���ڵ�ļ��� */
    Vector frontNodeVec = new Vector();

    /** ��̽ڵ�ļ��� */
    Vector behindNodeVec = new Vector();

    /** �������� */
    int num;

    /** ��λ�� */
    private String departmentName = "Department";

    static
    {
        CLASSNAME = (DefaultGraphNode.class).getName();
    }

    /**
     * ������
     */
    public DefaultGraphNode()
    {
        listenerList = new EventListenerList();
        this.setIcon("route_makeNode.gif");
    }

    /**
     * ��һ��ʮ���������͵Ķ���д���������ﲢ���ⲿ���
     * @param objectoutput ObjectOutput����
     * @throws IOException
     */
    public void writeExternal(ObjectOutput objectoutput)//
            throws IOException
    {
        objectoutput.writeLong(0xe7cd3ee2260b03e0L);
        objectoutput.writeObject(icon);
        objectoutput.writeObject(selectedIcon);
        //objectoutput.writeObject(position);
        //objectoutput.writeObject(description);
        //objectoutput.writeObject(key);
        objectoutput.writeObject(listenerList);
    }

    /**
     * ���ⲿ����ʮ�����Ƶ��������� �������ʮ�����Ƶ��� readOldVersion�����׳��쳣
     * @param objectinput ����Ķ���
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readExternal(ObjectInput objectinput) throws IOException, ClassNotFoundException
    {
        long l = objectinput.readLong();
        if(l != 0xe7cd3ee2260b03e0L)
        {
            readOldVersion(objectinput, l);
            return;
        }else
        {
            icon = (String)objectinput.readObject();
            selectedIcon = (String)objectinput.readObject();
            //position = (Point)objectinput.readObject();
            //description = (String)objectinput.readObject();
            //key = (String)objectinput.readObject();
            listenerList = (EventListenerList)objectinput.readObject();
            return;
        }
    }

    /**
     * ��ȡ�ɵİ汾
     * @param objectinput ObjectInput����
     * @param l long
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readOldVersion(ObjectInput objectinput, long l) throws IOException, ClassNotFoundException
    {
        throw new InvalidClassException(CLASSNAME, "Local class not compatible");
    }

    /**
     * ��ȡͼ��
     * @return ͼ�������
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * ���ݸ�����ͼ����������ͼ���µ�������������ֵ
     * @param s ������ͼ������
     * @throws QMPropertyVetoException
     */
    public void setIcon(String s)
    //throws QMPropertyVetoException
    {
        String s1 = icon;
        icon = s;
        //fireGraphNodeChanged("icon", s1, icon);
    }

    /**
     * ˢ��ͼ��
     */
    public void updateImage()
    {
        if(((RouteNodeInfo)this.getRouteItem().getObject()).getRouteType().equals(RouteCategoryType.MANUFACTUREROUTE.getDisplay()))
        {
            icon = "route_makeNode.gif";
        }else
        {
            icon = "route_assemNode.gif";
        }
    }

    /**
     * ��ȡѡ��ͼ�꣬�����ַ�����
     * @return String ����ͼ������
     */
    public String getSelectedIcon()
    {
        return selectedIcon;
    }

    /**
     * ����ѡ��ͼ��
     * @param s ����ͼ������
     * @throws QMPropertyVetoException
     */
    public void setSelectedIcon(String s)
    {
        selectedIcon = s;
    }

    /**
     * ��ȡλ�����꣬����Point�ͣ������ʱ���أ�0��0��
     * @return Point ���������
     */
    public Point getPosition()
    {
        //if(position == null)
        //ʵ����һ������
        //  position = new Point(0, 0);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)node.getObject();
        Long xLong = new Long(nodeInfo.getX());
        Long yLong = new Long(nodeInfo.getY());
        return new Point(xLong.intValue(), yLong.intValue());
    }

    /**
     * ��������
     * @param i -������ֵ
     * @param j -������ֵ
     */
    public void setPosition(int i, int j)
    {
        setPosition(new Point(i, j));
    }

    /**
     * �������겢������ֵ��Ϊ��
     * @param point �����µ�����ֵ
     */
    public void setPosition(Point point)
    {
        //Point point1 = position;
        //position = point;
        //����ֵΪ����ʱ��0
        if(point.x < 0)
            point.x = 0;
        if(point.y < 0)
            point.y = 0;
        //fireGraphNodeChanged("position", point1, position);

        RouteNodeInfo nodeInfo = (RouteNodeInfo)node.getObject();
        nodeInfo.setX(point.x);
        nodeInfo.setY(point.y);

    }

    /**
     * ����ı�ӵ��¼����ı�ӵ������
     * @param s -
     * @param obj -
     * @param obj1 -
     */
    private void fireGraphNodeChanged(String s, Object obj, Object obj1)
    {
        Object aobj[] = listenerList.getListenerList();
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(this, s, obj, obj1);
        for(int i = aobj.length - 2;i >= 0;i -= 2)
            if(aobj[i] == PropertyChangeListener.class)
                ((PropertyChangeListener)aobj[i + 1]).propertyChange(propertychangeevent);

    }

    /**
     * ������Ըı������
     * @param propertychangelistener -
     */
    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        listenerList.add(PropertyChangeListener.class, propertychangelistener);
    }

    /**
     * ɾ�����Ըı������
     * @param propertychangelistener -
     */
    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        listenerList.remove(PropertyChangeListener.class, propertychangelistener);
    }

    /**
     * ���Ըı��¼�
     * @param propertychangeevent
     */
    public void propertyChange(PropertyChangeEvent propertychangeevent)
    {}

    /**
     * ����ҵ�����
     * @param item
     */
    public void setRouteItem(RouteItem item)
    {
        node = item;
    }

    /**
     * ���ҵ�����
     * @return
     */
    public RouteItem getRouteItem()
    {
        return node;
    }

    /**
     * ��ýڵ�����
     * @return
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * ���ýڵ�����
     * @param departmentName
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

}