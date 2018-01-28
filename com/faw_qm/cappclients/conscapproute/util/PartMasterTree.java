/** 
 * ���ɳ���PartMasterTree.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.cappclients.conscapproute.util;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p> Title: ����װ�� </p> <p> Description: �����ֻ����������ڵ�.��Ҫ��PartMasterTreeObject����.�����ҵ�� ����Ϣ��װ��PartMasterTreeObject������ </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class PartMasterTree extends JTree
{

    /**
     * ���ڵ�
     */
    private PartMasterTreeNode root;

    /**
     * ����ģ��
     */
    private DefaultTreeModel myModel;

    /**
     * ���캯��
     */
    public PartMasterTree()
    {

    }

    /**
     * ���캯��
     * @param type ��(��ǩ)�ڵ���
     */
    public PartMasterTree(String type)
    {
        super(new PartMasterTreeNode(type));
        this.setRoot((PartMasterTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new PartMasterTreeCellRenderer());
    }

    /**
     * �õ���ǰ����ģ��
     * @return DefaultTreeModel javax.swing.tree.DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel)getModel();
        return myModel;
    }

    /**
     * �õ����ڵ�
     * @return PartMasterTreeNode
     */
    public PartMasterTreeNode getRoot()
    {
        return this.root;
    }

    /**
     * ���ø��ڵ�
     * @param root PartMasterTreeNode
     */
    public void setRoot(PartMasterTreeNode root)
    {
        this.root = root;
    }

    /**
     * ��ӽڵ㵽�������
     * @param child ����ӵ����ڵ�
     * @return ����ӵ����ڵ�
     */
    public PartMasterTreeNode addNode(PartMasterTreeNode child)
    {
        myModel.insertNodeInto(child, root, getLocation(root, child));
        return child;
    }

    /**
     * ��ӽڵ�
     * @param father PartMasterTreeNode ���ڵ�
     * @param child PartMasterTreeNode �ӽڵ�
     */
    public PartMasterTreeNode addNode(PartMasterTreeNode father, PartMasterTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }

    /**
     * �õ��ӽڵ�Ӧ�üӵ����ڵ��λ��
     * @param father ���ڵ�
     * @param child �ӽڵ�
     * @return λ��
     */
    private int getLocation(PartMasterTreeNode father, PartMasterTreeNode child)
    {
        PartMasterTreeNode node;
        for(int i = 0;i < father.getChildCount();i++)
        {
            node = (PartMasterTreeNode)father.getChildAt(i);
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
     * @param father PartMasterTreeNode, ���ڵ�
     * @param object PartMasterTreeObject �ӽڵ��װ����
     */
    public PartMasterTreeNode addNode(PartMasterTreeNode father, PartMasterTreeObject object)
    {
        return addNode(father, new PartMasterTreeNode(object));
    }

    /**
     * ɾ��һ���ڵ�
     * @param node PartMasterTreeNode Ҫɾ���Ľڵ�
     * @return PartMasterTreeNode
     */
    public PartMasterTreeNode removeNode(PartMasterTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }

    /**
     * ɾ������ָ������Ľڵ�
     * @param object ָ���Ķ���
     */
    public void removeNodes(PartMasterTreeObject object)
    {
        Vector vec = findNodes(object);
        for(int i = 0;i < vec.size();i++)
            this.removeNode((PartMasterTreeNode)vec.elementAt(i));
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
            PartMasterTreeNode qmnode = (PartMasterTreeNode)en.nextElement();

            if((qmnode.getObject()) != null && qmnode.getObject() instanceof PartMasterTreeObject)
            {
                vec.add(qmnode);
            }
        }
        for(int i = 0;i < vec.size();i++)
        {
            myModel.removeNodeFromParent((PartMasterTreeNode)vec.elementAt(i));
        }
    }

    /**
     * ���Ұ���ָ������Ľڵ㼯��
     * @param object PartMasterTreeObject
     * @return Vector,�ڵ�ļ���
     */
    public Vector findNodes(PartMasterTreeObject object)
    {
        Vector vector = new Vector();
        String s1 = object.getUniqueIdentity();
        String s2;
        Enumeration en = (((PartMasterTreeNode)(myModel().getRoot())).depthFirstEnumeration());
        while(en.hasMoreElements())
        {
            PartMasterTreeNode qmnode = (PartMasterTreeNode)en.nextElement();
            PartMasterTreeObject obj;
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
    public boolean isInTree(PartMasterTreeObject obj)
    {
        String s1 = obj.getUniqueIdentity();
        String s2;
        Enumeration en = (((PartMasterTreeNode)(myModel().getRoot())).depthFirstEnumeration());
        while(en.hasMoreElements())
        {
            PartMasterTreeNode qmnode = (PartMasterTreeNode)en.nextElement();
            PartMasterTreeObject obj1;
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
     * @param object PartMasterTreeObject
     * @return PartMasterTreeNode ���������Ľڵ�
     */
    public PartMasterTreeNode findNode(PartMasterTreeObject object)
    {
        Vector vec = findNodes(object);
        if(vec.size() > 0)
            return (PartMasterTreeNode)vec.elementAt(0);
        else
            return null;
    }

    /**
     * �õ�ѡ�еĶ���
     * @return PartMasterTreeObject
     */
    public PartMasterTreeObject getSelectedObject()
    {
        PartMasterTreeNode node = getSelectedNode();
        if(node == null)
            return null;
        return this.getSelectedNode().getObject();
    }

    /**
     * �õ�ѡ�еĽڵ�
     * @return PartMasterTreeNode
     * @roseuid 3F0630AA01B9
     */
    public PartMasterTreeNode getSelectedNode()
    {
        return (PartMasterTreeNode)this.getLastSelectedPathComponent();
    }

    /**
     * ȡ��ѡ�еĽڵ�
     * @return PartMasterTreeNode ����ҵ���������ڵ�
     */
    public PartMasterTreeNode[] getSelectedNodes()
    {
        TreePath[] pathes = this.getSelectionPaths();
        if(pathes != null && pathes.length > 0)
        {
            PartMasterTreeNode[] array = new PartMasterTreeNode[pathes.length];
            for(int i = 0;i < pathes.length;i++)
            {
                TreePath path = pathes[i];
                array[i] = (PartMasterTreeNode)path.getLastPathComponent();
            }
            return array;
        }
        return null;
    }

}
