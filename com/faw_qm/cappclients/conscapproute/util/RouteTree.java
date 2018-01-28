/**
 * ���ɳ���RouteTree.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/28 ����  ԭ�� ·�߱����ڵ㲻��ѡ�ж���ڵ�
 */
package com.faw_qm.cappclients.conscapproute.util;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import java.util.Vector;
import java.util.*;
import com.faw_qm.wip.model.WorkableIfc;
import java.awt.event.*;
import javax.swing.tree.*;

/**
 * Title: ����·�߲��ֵ�����װ�� Description: �����ֻ����������ڵ�.��Ҫ��RouteTreeObject����.�����ҵ������Ϣ��װ��RouteTreeObject������ Copyright: Copyright (c) 2004 Company: һ������
 * @author ����
 * @version 1.0
 */

public class RouteTree extends JTree
{

    /**
     * ���ڵ�
     */
    private RouteTreeNode root;

    /**
     * ����ģ��
     */
    private DefaultTreeModel myModel;

    /**
     * ���캯��
     */
    public RouteTree()
    {

    }

    /**
     * ���캯��
     * @param type ��(��ǩ)�ڵ���
     */
    public RouteTree(String type)
    {
        super(new RouteTreeNode(type));
        this.setRoot((RouteTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new RouteTreeCellRenderer());
        this.setAutoscrolls(true);
        //CR1 begin
        // this.addMouseListener(new RouteTree_this_mouseAdapter(this));
        //CR1 end
    }

    /**
     * �õ���ǰ����ģ��
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel)getModel();
        return myModel;
    }

    /**
     * �õ����ڵ�
     * @return RouteTreeNode
     */
    public RouteTreeNode getRoot()
    {
        return this.root;
    }

    /**
     * ���ø��ڵ�
     * @param root RouteTreeNode
     */
    public void setRoot(RouteTreeNode root)
    {
        this.root = root;
    }

    /**
     * ��ӽڵ�
     * @param child ����ӵ����ڵ�
     * @return ����ӵ����ڵ�
     */
    public RouteTreeNode addNode(RouteTreeNode child)
    {
        myModel.insertNodeInto(child, root, getLocation(root, child));
        return child;
    }

    /**
     * �õ��ӽڵ�Ӧ�üӵ����ڵ��λ��
     * @param father���ڵ�
     * @param child �ӽڵ�
     * @return λ��
     */
    private int getLocation(RouteTreeNode father, RouteTreeNode child)
    {
        RouteTreeNode node;
        for(int i = 0;i < father.getChildCount();i++)
        {
            node = (RouteTreeNode)father.getChildAt(i);
            if((child.getNoteText().compareTo(node.getNoteText())) < 0)
                return i;
            else if((child.getNoteText().compareTo(node.getNoteText())) == 0)
                if(child.getObject().getObject() instanceof WorkableIfc)
                {
                    if(((WorkableIfc)child.getObject().getObject()).getWorkableState().equals("c/o"))
                        return i;
                }
        }
        return father.getChildCount();
    }

    /**
     * ��ӽڵ�
     * @param object ����ӵĽڵ����
     * @return ����ӵĽڵ�
     */
    public RouteTreeNode addNode(RouteTreeObject object)
    {
        return addNode(new RouteTreeNode(object));
    }

    /**
     * ɾ��һ���ڵ�
     * @param node RouteTreeNode Ҫɾ���Ľڵ�
     * @return RouteTreeNode
     */
    public RouteTreeNode removeNode(RouteTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }

    /**
     * ɾ������ָ������Ľڵ�
     * @param object ָ���Ķ���
     */
    public void removeNodes(RouteTreeObject object)
    {
        Vector vec = findNodes(object);
        for(int i = 0;i < vec.size();i++)
            this.removeNode((RouteTreeNode)vec.elementAt(i));
    }

    /**
     * ɾ����ȥ���ڵ�֮������нڵ�
     */
    public void removeAllExceptRoot()
    {
        Enumeration en = getRoot().depthFirstEnumeration();
        Vector vec = new Vector();
        while(en.hasMoreElements())
        {
            RouteTreeNode qmnode = (RouteTreeNode)en.nextElement();

            if((qmnode.getObject()) != null && qmnode.getObject() instanceof RoutePartTreeObject)
            {
                vec.add(qmnode);
            }
        }
        for(int i = 0;i < vec.size();i++)
        {
            myModel.removeNodeFromParent((RouteTreeNode)vec.elementAt(i));
        }
    }

    /**
     * zz ���Ұ���ָ������Ľڵ㼯�� ����·�߱�������֮�󣬽��ñ�ŵ��������а汾�Ľڵ�������ˢ�� �˷�����ͨ������·�߱��Ų������������汾��·�߱������󣨺��Լ���
     * @param object RouteTreeObject
     * @return Vector,�ڵ�ļ���
     */
    public Vector findNodesSpecial(RouteTreeObject object)
    {
        Vector vector = new Vector();
        String s1 = object.getUniqueIdentity();

        String number = s1.substring(0, s1.indexOf("@") + 2);
        String s2;
        Enumeration en = (((RouteTreeNode)(myModel().getRoot())).depthFirstEnumeration());
        while(en.hasMoreElements())
        {
            RouteTreeNode qmnode = (RouteTreeNode)en.nextElement();
            RouteTreeObject obj;
            if((obj = qmnode.getObject()) != null)
            {
                s2 = obj.getUniqueIdentity();
                if(s2.startsWith(number))
                    vector.add(qmnode);
            }
        }
        return vector;
    }

    public Vector findNodesSpecial(String UniqueIdentityStr)
    {
        Vector vector = new Vector();
        String s1 = UniqueIdentityStr;
        String number1 = s1 + "@";
        String s2;
        Enumeration en = (((RouteTreeNode)(myModel().getRoot())).depthFirstEnumeration());
        while(en.hasMoreElements())
        {
            RouteTreeNode qmnode = (RouteTreeNode)en.nextElement();
            RouteTreeObject obj;
            if((obj = qmnode.getObject()) != null)
            {
                s2 = obj.getUniqueIdentity();
                if(s2.startsWith(number1))
                    vector.add(qmnode);
            }
        }
        return vector;
    }

    /**
     * ���Ұ���ָ������Ľڵ㼯��
     * @param object RouteTreeObject
     * @return Vector,�ڵ�ļ���
     */
    public Vector findNodes(RouteTreeObject object)
    {
        Vector vector = new Vector();
        String s1 = object.getUniqueIdentity();
        String s2;
        Enumeration en = (((RouteTreeNode)(myModel().getRoot())).depthFirstEnumeration());
        while(en.hasMoreElements())
        {
            RouteTreeNode qmnode = (RouteTreeNode)en.nextElement();
            RouteTreeObject obj;
            if((obj = qmnode.getObject()) != null)
            {
                s2 = obj.getUniqueIdentity();
                if(s1.equals(s2))
                    vector.add(qmnode);
            }
        }
        return vector;
    }

    /**
     * �ж������Ƿ������ǰ�ڵ����
     * @param obj ָ���Ľڵ����
     * @return ����ýڵ�������,�򷵻�true,���򷵻�false
     */
    public boolean isInTree(RouteTreeObject obj)
    {
        String s1 = obj.getUniqueIdentity();
        String s2;
        Enumeration en = (((RouteTreeNode)(myModel().getRoot())).depthFirstEnumeration());
        while(en.hasMoreElements())
        {
            RouteTreeNode qmnode = (RouteTreeNode)en.nextElement();
            RouteTreeObject obj1;
            if((obj1 = qmnode.getObject()) != null)
            {
                s2 = obj1.getUniqueIdentity();
                if(s1.equals(s2))
                    return true;
            }
        }
        return false;
    }

    /**
     * ���Ұ���ָ������Ľڵ�
     * @param object RouteTreeObject
     * @return RouteTreeNode ���������Ľڵ�
     */
    public RouteTreeNode findNode(RouteTreeObject object)
    {
        Vector vec = findNodes(object);
        if(vec.size() > 0)
            return (RouteTreeNode)vec.elementAt(0);
        else
            return null;
    }

    /**
     * �õ�ѡ�еĶ���
     * @return RouteTreeObject
     */
    public RouteTreeObject getSelectedObject()
    {
        RouteTreeNode node = getSelectedNode();
        if(node == null)
            return null;
        return this.getSelectedNode().getObject();
    }

    /**
     * �õ�ѡ�еĽڵ�
     * @return RouteTreeNode
     * @roseuid 3F0630AA01B9
     */
    public RouteTreeNode getSelectedNode()
    {
        return (RouteTreeNode)this.getLastSelectedPathComponent();
    }

    /**
     * ����������б�ѡ�еĽڵ�
     * @return ���б�ѡ�еĽڵ�
     */
    public RouteTreeNode[] getSelectedNodes()
    {
        TreePath[] pathes = this.getSelectionPaths();
        if(pathes != null && pathes.length > 0)
        {
            RouteTreeNode[] array = new RouteTreeNode[pathes.length];
            for(int i = 0;i < pathes.length;i++)
            {
                TreePath path = pathes[i];
                array[i] = (RouteTreeNode)path.getLastPathComponent();
            }
            return array;
        }
        return null;
    }
    //CR1 begin
    //    /**
    //     * ������ɿ�ʱ������ѡ��������ڵ����ڵ�
    //     * @param e MouseEvent
    //     */
    //    void this_mouseReleased(MouseEvent e)
    //    {
    //        int selRow = this.getRowForLocation(e.getX(), e.getY());
    //        this.setSelectionRow(selRow);
    //    }
    //CR1 end
}

/**
 * <p>Title:RouteTree.java</p> <p>Description: </p> <p>Package:com.faw_qm.cappclients.conscapproute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author
 * @version 1.0
 */
//CR1 begin
//class RouteTree_this_mouseAdapter extends java.awt.event.MouseAdapter
//{
//    //����·�߲��ֵ�����װ��
//    private RouteTree adaptee;
//
//    //���캯��
//    RouteTree_this_mouseAdapter(RouteTree adaptee)
//    {
//        this.adaptee = adaptee;
//    }
//
//    //����ͷ�
//    public void mouseReleased(MouseEvent e)
//    {
//        adaptee.this_mouseReleased(e);
//    }
//}
//CR1 end
