/** ���ɳ���RationTree.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.gybomNotice.client.util;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


/**
 * <p>Title: ���õ���������RationListTreePanel</p>
 * <p>Description: �����ֻ����������ڵ�.��Ҫ��RationTreeObject����.�����ҵ������Ϣ��װ��RationTreeObject������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */


public class GYBomNoticeTree extends JTree
{

    /**
     * ���ڵ�
     */
    private GYBomNoticeTreeNode root;


    /**
     * ����ģ��
     */
    private DefaultTreeModel myModel;


    /**
     *���캯��
     */
    public GYBomNoticeTree()
    {

    }


    /**
     * ���캯��
     * @param type ��(��ǩ)�ڵ���
     */
    public GYBomNoticeTree(String type)
    {
        super(new GYBomNoticeTreeNode(type));
        this.setRoot((GYBomNoticeTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new GYBomNoticeTreeCellRenderer(new Vector()));
        this.setAutoscrolls(true);
        this.addMouseListener(new BomNoticeTree_this_mouseAdapter(this));
    }


    /**
     * �õ���ǰ����ģ��
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel) getModel();
        return myModel;
    }


    /**
     * �õ����ڵ�
     * @return RationTreeNode
     */
    public GYBomNoticeTreeNode getRoot()
    {
        return this.root;
    }


    /**
     *  ���ø��ڵ�
     *  @param root RationTreeNode
     */
    public void setRoot(GYBomNoticeTreeNode root)
    {
        this.root = root;
    }


    /**
     * ��ӽڵ�
     * @param father RationTreeNode  ���ڵ�
     * @param child  RationTreeNode  �ӽڵ�
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeNode father, GYBomNoticeTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * ��ӽڵ�
     * @param father RationTreeNode, ���ڵ�
     * @param object  RationTreeObject  �ӽڵ��װ����
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeNode father,
    		GYBomNoticeTreeObject object)
    {
        return addNode(father, new GYBomNoticeTreeNode(object));

    }


    /**
     * ��ӽڵ㵽���ڵ���
     * @param child ����ӵ����ڵ�
     * @return  ����ӵ����ڵ�
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeNode child)
    {
        myModel.insertNodeInto(child, root, getLocation(root, child));
        return child;
    }


    /**
     * ��ӽڵ㵽���ڵ���(���Զ�����)
     * @param child RationTreeNode
     */
    public void addNodeNotSort(GYBomNoticeTreeNode child)
    {
        myModel.insertNodeInto(child, root, root.getChildCount());
    }


    /**
     * �õ��ӽڵ�Ӧ�üӵ����ڵ��λ��
     * @param father  ���ڵ�
     * @param child  �ӽڵ�
     * @return λ��
     */
    private int getLocation(GYBomNoticeTreeNode father, GYBomNoticeTreeNode child)
    {
        GYBomNoticeTreeNode node;
        for (int i = 0; i < father.getChildCount(); i++)
        {
            node = (GYBomNoticeTreeNode) father.getChildAt(i);
            if ((child.getNoteText().compareTo(node.getNoteText())) < 0)
            {
                return i;
            }

        }
        return father.getChildCount();
    }


    /**
     * ��ӽڵ�
     * @param object  ����ӵĽڵ����
     * @return ����ӵĽڵ�
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeObject object)
    {
        return addNode(new GYBomNoticeTreeNode(object));
    }


    /**
     * ɾ��һ���ڵ�
     * @param node RationTreeNode Ҫɾ���Ľڵ�
     * @return RationTreeNode
     */
    public GYBomNoticeTreeNode removeNode(GYBomNoticeTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }


    /**
     * ɾ������ָ������Ľڵ�
     * @param object ָ���Ķ���
     */
    public void removeNodes(GYBomNoticeTreeObject object)
    {
        Vector vec = findNodes(object);
        for (int i = 0; i < vec.size(); i++)
        {
            this.removeNode((GYBomNoticeTreeNode) vec.elementAt(i));
        }
    }


    /**
     * ɾ����ȥ���ڵ�֮������нڵ�
     */
    public void removeAllExceptRoot()
    {
        /**
             Enumeration en=getRoot().depthFirstEnumeration();
             Vector vec  = new Vector();
             while(en.hasMoreElements())
             {
               RationTreeNode qmnode =(RationTreeNode)en.nextElement();

               if((qmnode.getObject()) != null&&qmnode.getObject() instanceof RationPartTreeObject)
               {
          vec.add(qmnode);
               }
             }
             for(int i =0;i<vec.size();i++)
             {
               myModel.removeNodeFromParent((RationTreeNode)vec.elementAt(i));
             }
         }*/
    }


    /**
     * ���Ұ���ָ������Ľڵ㼯��
     * @param object GYBomNoticeTreeObject
     * @return  Vector,�ڵ�ļ���
     */
    public Vector findNodes(GYBomNoticeTreeObject object)
    {
        Vector vector = new Vector();
        String s1 = object.getUniqueIdentity();
        String s2;
        Enumeration en = (((GYBomNoticeTreeNode) (myModel().getRoot())).
                          depthFirstEnumeration());
        while (en.hasMoreElements())
        {
            GYBomNoticeTreeNode qmnode = (GYBomNoticeTreeNode) en.nextElement();
            GYBomNoticeTreeObject obj;
            if ((obj = qmnode.getObject()) != null)
            {
                s2 = obj.getUniqueIdentity();
                if (s1.equals(s2))
                {
                    vector.add(qmnode);
                }
            }
        }
        return vector;
    }


    /**
     * �ж������Ƿ������ǰ�ڵ����
     * @param obj ָ���Ľڵ����
     * @return ����ýڵ�������,�򷵻�true,���򷵻�false
     */
    public boolean isInTree(GYBomNoticeTreeObject obj)
    {
        String s1 = obj.getUniqueIdentity();
        String s2;
        Enumeration en = (((GYBomNoticeTreeNode) (myModel().getRoot())).
                          depthFirstEnumeration());
        while (en.hasMoreElements())
        {
            GYBomNoticeTreeNode qmnode = (GYBomNoticeTreeNode) en.nextElement();
            GYBomNoticeTreeObject obj1;
            if ((obj1 = qmnode.getObject()) != null)
            {
                s2 = obj1.getUniqueIdentity();
                if (s1.equals(s2))
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * ���Ұ���ָ������Ľڵ�
     * @param object GYBomNoticeTreeObject
     * @return RationTreeNode  ���������Ľڵ�
     */
    public GYBomNoticeTreeNode findNode(GYBomNoticeTreeObject object)
    {
        Vector vec = findNodes(object);
        if (vec.size() > 0)
        {
            return (GYBomNoticeTreeNode) vec.elementAt(0);
        }
        else
        {
            return null;
        }
    }


    /**
     * �õ�ѡ�еĶ���
     * @return GYBomNoticeTreeObject
     */
    public GYBomNoticeTreeObject getSelectedObject()
    {
        GYBomNoticeTreeNode node = getSelectedNode();
        if (node == null)
        {
            return null;
        }
        return this.getSelectedNode().getObject();
    }


    /**
     �õ�ѡ�еĽڵ�
     @return RationTreeNode
     @roseuid 3F0630AA01B9
     */
    public GYBomNoticeTreeNode getSelectedNode()
    {
        return (GYBomNoticeTreeNode)this.getLastSelectedPathComponent();
    }


    /**
     * ����������б�ѡ�еĽڵ�
     * @return ���б�ѡ�еĽڵ�
     */
    public GYBomNoticeTreeNode[] getSelectedNodes()
    {
        TreePath[] pathes = this.getSelectionPaths();
        if (pathes != null && pathes.length > 0)
        {
            GYBomNoticeTreeNode[] array = new GYBomNoticeTreeNode[pathes.length];
            for (int i = 0; i < pathes.length; i++)
            {
                TreePath path = pathes[i];
                array[i] = (GYBomNoticeTreeNode) path.getLastPathComponent();
            }
            return array;
        }
        return null;
    }


    /**
     * ������ɿ�ʱ������ѡ��������ڵ����ڵ�
     * @param e MouseEvent
     */
    void this_mouseReleased(MouseEvent e)
    {
        int selRow = this.getRowForLocation(e.getX(), e.getY());
        this.setSelectionRow(selRow);
    }
}


class BomNoticeTree_this_mouseAdapter extends java.awt.event.MouseAdapter
{
    private GYBomNoticeTree adaptee;

    BomNoticeTree_this_mouseAdapter(GYBomNoticeTree adaptee)
    {
        this.adaptee = adaptee;
    }

    public void mouseReleased(MouseEvent e)
    {
        adaptee.this_mouseReleased(e);
    }
}
