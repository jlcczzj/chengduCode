/**
 * ���ɳ���DefaultGraphLink.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Χ��,ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> ά�����ӵ����ڵ�֮��Ĺ����������½������¡�ɾ������������� </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class DefaultGraphLink implements GraphLink, Externalizable
{
    /** ˵�� */
    public static final String DESCRIPTION = "description";

    /** ˵�� */
    private String description;

    /** ǰ���ڵ� */
    public static final String PREDECESSOR = "predecessor";

    /** ǰ���ڵ� */
    private GraphNode predecessor;

    /** ��̽ڵ� */
    public static final String SUCCESSOR = "successor";

    /** ��̽ڵ� */
    private GraphNode successor;

    /** ������ */
    public static final String LISTENER_LIST = "listenerList";

    /** ������ */
    private EventListenerList listenerList;

    /** ��Դ�ļ� */
    private static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    /** �Ƿ�ѭ�� */
    public static final String LOOP = "loop";

    /** �Ƿ�ѭ�� */
    private Boolean loop;

    /** ��������� */
    private static final String CLASSNAME = (DefaultGraphLink.class).getName();

    /** ���ӵķ�װ���� */
    private RouteItem linkItem;

    /** �ڵ�ֵ���� */
    private RouteNodeInfo nodeInfo;

    /** ����ֵ���� */
    private RouteNodeLinkInfo linkInfo;

    /**
     * ������
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
     * ������
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
     * ������
     */
    public DefaultGraphLink()
    {
        loop = Boolean.FALSE;
        listenerList = new EventListenerList();//ʵ�����¼������б�
    }

    /**
     * �Ѹ������͵Ķ���д���������ﲢ���ⲿ���
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
     * ���ⲿ��ʮ�����Ƶ���ʽ����������͵Ķ���
     * @param objectinput ObjectInput
     * @throws java.lang.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void readExternal(ObjectInput objectinput)
    {
        try
        {
            long l = objectinput.readLong();
            if(l != 0xbd3d09c61e50a6a2L)//�ж��Ƿ�Ϊʮ������
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
     * �÷������ڵ����벻��ʮ������ʱ�׳��쳣
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
     * �õ��������ַ����������������ַ�����
     * @return String
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * ���øı����ӵ���������
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
     * �������Ĳ�����Ϊ�ղ����ַ����ȴ���200 �����µĶ����׳��쳣 ���򷵻ؿ�ֵ
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
     * �õ�ͼԪ��ǰ���ڵ㣬����ֵ��GraphNode��
     * @return GraphNode
     */
    public GraphNode getPredecessor()
    {
        return predecessor;
    }

    /**
     * ���øı����ӵ�ǰ���ڵ�����
     * @param graphnode GraphNode�������Ϊԭ�����͵��µ�����ֵ
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
     * ����ͼԪǰ���ڵ�
     * @param graphnode GraphNode ͼԪ�ڵ�ӿ�
     */
    public void setInitPredecessor(GraphNode graphnode)//lm
    {
        predecessor = graphnode;
    }

    /**
     * ����ͼԪ��̽ڵ�
     * @param graphnode GraphNode ͼԪ�ڵ�ӿ�
     */
    public void setInitSuccessor(GraphNode graphnode)//lm
    {
        successor = graphnode;
    }

    /**
     * ͼԪǰ���ڵ����Ч��֤��Ϊ��ʱ�����µĶ����׳��쳣
     * @param graphnode GraphNode��Ķ���
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
     * �õ���̵�ͼԪ�ӵ㣬����ͼԪ�ӵ���
     * @return GraphNode
     */
    public GraphNode getSuccessor()
    {
        return successor;
    }

    /**
     * ����successorValidate�������������øı����ӵĺ�̽ڵ�����
     * @param graphnode GraphNode��Ķ��������Ϊ������͵��µ�����ֵ
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
     * ����ҵ�����
     * @param item RouteItem
     */
    public void setRouteItem(RouteItem item)
    {
        linkItem = item;
    }

    /**
     * ���ҵ�����
     * @return RouteItem
     */
    public RouteItem getRouteItem()
    {
        return linkItem;
    }

    /**
     * ��̽ӵ����Ч��֤��Ϊ���׳��쳣
     * @param graphnode GraphNode����
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
     * �����ı����ӵĽڵ������
     * @param s String
     * @param obj Object����
     * @param obj1 Object����
     */
    private void fireGraphLinkChanged(String s, Object obj, Object obj1)
    {
        Object aobj[] = listenerList.getListenerList();//���������б����
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(this, s, obj, obj1);//ʵ�����µ����Ըı��¼�
        for(int i = aobj.length - 2;i >= 0;i -= 2)
            if(aobj[i] == (java.beans.PropertyChangeListener.class))

                ((PropertyChangeListener)aobj[i + 1]).propertyChange(propertychangeevent);

    }

    /**
     * ��Ӽ�����
     * @param propertychangelistener PropertyChangeListener��Ķ���
     */
    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)//
    {
        listenerList.add(java.beans.PropertyChangeListener.class, propertychangelistener);

    }

    /**
     * �Ƴ�������
     * @param propertychangelistener PropertyChangeListener��Ķ���
     */
    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)//
    {
        listenerList.remove(java.beans.PropertyChangeListener.class, propertychangelistener);
    }

}