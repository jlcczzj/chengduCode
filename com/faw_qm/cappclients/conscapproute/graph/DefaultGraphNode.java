/**
 * 生成程序DefaultGraphNode.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司范围内,使用本程序
 * 保留所有权利
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
 * <p> Title:默认图元节点 </p> <p> Description:维护节点的界面属性和持久化属性 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class DefaultGraphNode implements GraphNode, PropertyChangeListener, Externalizable
{
    /** 本类的类名 */
    private static final String CLASSNAME;

    /** 图标 */
    public static final String ICON = "icon";

    private String icon;

    /** 所选择的图标 */
    public static final String SELECTED_ICON = "selectedIcon";

    private String selectedIcon;

    /** 位置 */
    public static final String POSITION = "position";

    /** 监听表 */
    public static final String LISTENER_LIST = "listenerList";

    private EventListenerList listenerList;

    /** 节点封装对象 */
    private RouteItem node;

    /** 前驱节点的集合 */
    Vector frontNodeVec = new Vector();

    /** 后继节点的集合 */
    Vector behindNodeVec = new Vector();

    /** 计数变量 */
    int num;

    /** 单位名 */
    private String departmentName = "Department";

    static
    {
        CLASSNAME = (DefaultGraphNode.class).getName();
    }

    /**
     * 构造器
     */
    public DefaultGraphNode()
    {
        listenerList = new EventListenerList();
        this.setIcon("route_makeNode.gif");
    }

    /**
     * 把一个十六进制类型的对象写到数据流里并在外部输出
     * @param objectoutput ObjectOutput对象
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
     * 在外部输入十六进制的数据类型 如果不是十六进制调用 readOldVersion（）抛出异常
     * @param objectinput 输入的对象
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
     * 读取旧的版本
     * @param objectinput ObjectInput对象
     * @param l long
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readOldVersion(ObjectInput objectinput, long l) throws IOException, ClassNotFoundException
    {
        throw new InvalidClassException(CLASSNAME, "Local class not compatible");
    }

    /**
     * 获取图标
     * @return 图标的名称
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * 根据给定的图标名称设置图标新的属性名和属性值
     * @param s 给定的图标名称
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
     * 刷新图标
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
     * 获取选择图标，返回字符串型
     * @return String 返回图标名称
     */
    public String getSelectedIcon()
    {
        return selectedIcon;
    }

    /**
     * 设置选择图标
     * @param s 传入图标名称
     * @throws QMPropertyVetoException
     */
    public void setSelectedIcon(String s)
    {
        selectedIcon = s;
    }

    /**
     * 获取位置坐标，返回Point型，如果空时返回（0，0）
     * @return Point 返回坐标点
     */
    public Point getPosition()
    {
        //if(position == null)
        //实例化一个坐标
        //  position = new Point(0, 0);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)node.getObject();
        Long xLong = new Long(nodeInfo.getX());
        Long yLong = new Long(nodeInfo.getY());
        return new Point(xLong.intValue(), yLong.intValue());
    }

    /**
     * 设置坐标
     * @param i -横坐标值
     * @param j -纵坐标值
     */
    public void setPosition(int i, int j)
    {
        setPosition(new Point(i, j));
    }

    /**
     * 设置坐标并且坐标值不为负
     * @param point 给出新的坐标值
     */
    public void setPosition(Point point)
    {
        //Point point1 = position;
        //position = point;
        //坐标值为负数时置0
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
     * 激活改变接点事件，改变接点的属性
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
     * 添加属性改变监听器
     * @param propertychangelistener -
     */
    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        listenerList.add(PropertyChangeListener.class, propertychangelistener);
    }

    /**
     * 删除属性改变监听器
     * @param propertychangelistener -
     */
    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        listenerList.remove(PropertyChangeListener.class, propertychangelistener);
    }

    /**
     * 属性改变事件
     * @param propertychangeevent
     */
    public void propertyChange(PropertyChangeEvent propertychangeevent)
    {}

    /**
     * 设置业务对象
     * @param item
     */
    public void setRouteItem(RouteItem item)
    {
        node = item;
    }

    /**
     * 获得业务对象
     * @return
     */
    public RouteItem getRouteItem()
    {
        return node;
    }

    /**
     * 获得节点名称
     * @return
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * 设置节点名称
     * @param departmentName
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

}