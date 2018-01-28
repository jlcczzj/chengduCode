/**
 * 生成程序DefaultGraphLink.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司范围内,使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Locale;

import javax.swing.event.EventListenerList;

import com.faw_qm.cappclients.conscapproute.util.GraphRB;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;

/**
 * <p> 维护连接的两节点之间的关联。负责新建、更新、删除、保存关联。 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class DefaultGraphLink implements GraphLink, Externalizable
{
    /** 说明 */
    public static final String DESCRIPTION = "description";

    /** 说明 */
    private String description;

    /** 前驱节点 */
    public static final String PREDECESSOR = "predecessor";

    /** 前驱节点 */
    private GraphNode predecessor;

    /** 后继节点 */
    public static final String SUCCESSOR = "successor";

    /** 后继节点 */
    private GraphNode successor;

    /** 监听表 */
    public static final String LISTENER_LIST = "listenerList";

    /** 监听表 */
    private EventListenerList listenerList;

    /** 资源文件 */
    private static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    /** 是否循环 */
    public static final String LOOP = "loop";

    /** 是否循环 */
    private Boolean loop;

    /** 本类的类名 */
    private static final String CLASSNAME = (DefaultGraphLink.class).getName();

    /** 连接的封装对象 */
    private RouteItem linkItem;

    /** 节点值对象 */
    private RouteNodeInfo nodeInfo;

    /** 连接值对象 */
    private RouteNodeLinkInfo linkInfo;

    /**
     * 构造器
     * @param graphnode GraphNode
     * @param graphnode1 GraphNode
     * @throws QMException 
     * @throws QMRemoteException
     */
    public DefaultGraphLink(GraphNode graphnode, GraphNode graphnode1) throws QMException
    {
        this(graphnode, graphnode1, "");
    }

    /**
     * 构造器
     * @param graphnode GraphNode
     * @param graphnode1 GraphNode
     * @param s String
     * @throws QMException 
     * @throws QMRemoteException
     */
    public DefaultGraphLink(GraphNode graphnode, GraphNode graphnode1, String s) throws QMException
    {
        this();
        setPredecessor(graphnode);
        setSuccessor(graphnode1);
        setDescription(s);
    }

    /**
     * 构造器
     */
    public DefaultGraphLink()
    {
        loop = Boolean.FALSE;
        listenerList = new EventListenerList();//实例化事件监听列表
    }

    /**
     * 把各种类型的对象写到数据流里并在外部输出
     * @param objectoutput ObjectOutput
     * @exception java.lang.IOException
     */
    public void writeExternal(ObjectOutput objectoutput)
    {
        try
        {
            objectoutput.writeLong(0xbd3d09c61e50a6a2L);
            objectoutput.writeObject(description);
            objectoutput.writeObject(predecessor);
            objectoutput.writeObject(successor);
            objectoutput.writeObject(listenerList);
            objectoutput.writeObject(loop);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(null, message);
        }
    }

    /**
     * 在外部以十六进制的形式输入各种类型的对象
     * @param objectinput ObjectInput
     * @throws java.lang.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void readExternal(ObjectInput objectinput)
    {
        try
        {
            long l = objectinput.readLong();
            if(l != 0xbd3d09c61e50a6a2L)//判断是否为十六进制
            {
                readOldVersion(objectinput, l);
                return;
            }else
            {
                description = (String)objectinput.readObject();
                predecessor = (GraphNode)objectinput.readObject();
                successor = (GraphNode)objectinput.readObject();
                listenerList = (EventListenerList)objectinput.readObject();
                loop = (Boolean)objectinput.readObject();
                return;
            }
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(null, message);
        }
    }

    /**
     * 该方法用于当输入不是十六进制时抛出异常
     * @param objectinput ObjectInput
     * @param l long
     * @exception java.lang.IOException
     * @exception java.lang.ClassNotFoundException
     */
    private void readOldVersion(ObjectInput objectinput, long l) throws IOException, ClassNotFoundException
    {
        throw new InvalidClassException(CLASSNAME, "Local class not compatible");
    }

    /**
     * 得到描述的字符串，返回类型是字符串型
     * @return String
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * 设置改变链接的描述属性
     * @param s String
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void setDescription(String s) throws QMException
    {
        String s1 = description;
        descriptionValidate(s);
        description = s;
        fireGraphLinkChanged("description", s1, description);
    }

    /**
     * 如果传入的参数不为空并且字符长度大于200 创建新的对象，抛出异常 否则返回空值
     * @param s String
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void descriptionValidate(String s) throws QMException
    {
        if(s != null && s.length() > 200)
        {
            Locale locale = RemoteProperty.getVersionLocale();
            String s1 = QMMessage.getLocalizedMessage(RESOURCE, GraphRB.PROPERTY_VALUE_IS_LARGER, null, locale);
            throw new QMException(s1);
        }else
        {
            return;
        }
    }

    /**
     * 得到图元的前驱节点，返回值是GraphNode型
     * @return GraphNode
     */
    public GraphNode getPredecessor()
    {
        return predecessor;
    }

    /**
     * 设置改变链接的前驱节点属性
     * @param graphnode GraphNode类参数作为原有类型的新的属性值
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void setPredecessor(GraphNode graphnode) throws QMException
    {
        GraphNode graphnode1 = predecessor;
        predecessorValidate(graphnode);
        predecessor = graphnode;
        RouteNodeInfo nodeInfo = (RouteNodeInfo)graphnode.getRouteItem().getObject();
        RouteNodeLinkInfo linkInfo = (RouteNodeLinkInfo)this.getRouteItem().getObject();
        linkInfo.setSourceNode(nodeInfo);
        fireGraphLinkChanged("predecessor", graphnode1, predecessor);
    }

    /**
     * 设置图元前驱节点
     * @param graphnode GraphNode 图元节点接口
     */
    public void setInitPredecessor(GraphNode graphnode)//lm
    {
        predecessor = graphnode;
    }

    /**
     * 设置图元后继节点
     * @param graphnode GraphNode 图元节点接口
     */
    public void setInitSuccessor(GraphNode graphnode)//lm
    {
        successor = graphnode;
    }

    /**
     * 图元前驱节点的有效验证，为空时创建新的对象并抛出异常
     * @param graphnode GraphNode类的对象
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void predecessorValidate(GraphNode graphnode) throws QMException
    {
        if(graphnode == null)
        {
            Locale locale = RemoteProperty.getVersionLocale();
            String s = QMMessage.getLocalizedMessage(RESOURCE, GraphRB.PROPERTY_VALUE_IS_EMPTY, null, locale);
            throw new QMException(s);
        }else
        {
            return;
        }
    }

    /**
     * 得到后继的图元接点，返回图元接点型
     * @return GraphNode
     */
    public GraphNode getSuccessor()
    {
        return successor;
    }

    /**
     * 调用successorValidate（）方法，设置改变链接的后继节点属性
     * @param graphnode GraphNode类的对象参数作为后继类型的新的属性值
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void setSuccessor(GraphNode graphnode) throws QMException
    {
        GraphNode graphnode1 = successor;
        successorValidate(graphnode);
        successor = graphnode;
        nodeInfo = (RouteNodeInfo)graphnode.getRouteItem().getObject();
        linkInfo = (RouteNodeLinkInfo)this.getRouteItem().getObject();
        linkInfo.setDestinationNode(nodeInfo);
        fireGraphLinkChanged("successor", graphnode1, successor);
    }

    /**
     * 设置业务对象
     * @param item RouteItem
     */
    public void setRouteItem(RouteItem item)
    {
        linkItem = item;
    }

    /**
     * 获得业务对象
     * @return RouteItem
     */
    public RouteItem getRouteItem()
    {
        return linkItem;
    }

    /**
     * 后继接点的有效验证，为空抛出异常
     * @param graphnode GraphNode对象
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void successorValidate(GraphNode graphnode) throws QMException
    {
        if(graphnode == null)
        {
            Locale locale = RemoteProperty.getVersionLocale();
            String s = QMMessage.getLocalizedMessage(RESOURCE, GraphRB.PROPERTY_VALUE_IS_EMPTY, null, locale);
            throw new QMException(s);
        }else
        {
            return;
        }
    }

    /**
     * 用来改变链接的节点的属性
     * @param s String
     * @param obj Object对象
     * @param obj1 Object对象
     */
    private void fireGraphLinkChanged(String s, Object obj, Object obj1)
    {
        Object aobj[] = listenerList.getListenerList();//创建监听列表对象
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(this, s, obj, obj1);//实例化新的属性改变事件
        for(int i = aobj.length - 2;i >= 0;i -= 2)
            if(aobj[i] == (java.beans.PropertyChangeListener.class))

                ((PropertyChangeListener)aobj[i + 1]).propertyChange(propertychangeevent);

    }

    /**
     * 添加监听器
     * @param propertychangelistener PropertyChangeListener类的对象
     */
    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)//
    {
        listenerList.add(java.beans.PropertyChangeListener.class, propertychangelistener);

    }

    /**
     * 移除监听器
     * @param propertychangelistener PropertyChangeListener类的对象
     */
    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)//
    {
        listenerList.remove(java.beans.PropertyChangeListener.class, propertychangelistener);
    }

}