package com.faw_qm.gybomNotice.client.util;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author �� ��
 * @version 1.0
 */

/** ���ɳ���CappTree.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 20130809 xucy �μ�TD7806 ԭ�������������б�������Ĺ���
 */


public class GYProductTree extends JTree
{
    //��Դ����
    private static String __ENCODE__ = "GBK"; //һ��Ҫ��GBK
    private static String __SERVER_ENCODE__ = "GB2312"; //�������ϵ�ȱʡ����


    /**
     * ���ڵ�
     */
    private GYProductTreeNode root;


    /**
     * ����ģ��
     */
    private DefaultTreeModel myModel;


    /**
     *���캯��
     */
    public GYProductTree()
    {

    }


    /**
     * ���캯��
     * @param type
     * @type:String �������͡��й���������Դ���ȵȡ�������
     */
    public GYProductTree(String type,Vector vec)
    {
        super(new GYProductTreeNode(type));
        this.setRoot((GYProductTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new GYBomNoticeTreeCellRenderer(vec));

    
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
     * @return GYProductTreeNode
     */
    public GYProductTreeNode getRoot()
    {
        return this.root;
    }


    /**
     *  ���ø��ڵ�
     *  @param root;GYProductTreeNode
     */
    public void setRoot(GYProductTreeNode root)
    {
        this.root = root;
    }


    /**
     * ��ӽڵ�
     * @param father GYProductTreeNode  ���ڵ�
     * @param child  GYProductTreeNode  �ӽڵ�
     */
    public GYProductTreeNode addNode(GYProductTreeNode father,
                                   GYProductTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * �õ��ӽڵ�Ӧ�üӵ����ڵ��λ��
     * @param father  ���ڵ�
     * @param child  �ӽڵ�
     * @return
     */
    private int getLocation(GYProductTreeNode father, GYProductTreeNode child)
    {
        GYProductTreeNode node;
        for (int i = 0; i < father.getChildCount(); i++)
        {
            node = (GYProductTreeNode) father.getChildAt(i);
            if ((child.getNoteText().compareTo(node.getNoteText())) < 0)
            {
                return i;
            }

        }
        return father.getChildCount();

    }


    /**
     * ��ӽڵ�
     * @param father GYProductTreeNode, ���ڵ�
     * @param object  RationTreeObject  �ӽڵ��װ����
     */
    public GYProductTreeNode addNode(GYProductTreeNode father,
    		GYPartTreeObject object)
    {
        return addNode(father, new GYProductTreeNode(object));

    }


    /**
     * ɾ��һ���ڵ�
     * @param node GYProductTreeNode Ҫɾ���Ľڵ�
     * @return GYProductTreeNode
     */
    public GYProductTreeNode removeNode(GYProductTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }


    /**
     * ɾ������ָ������Ľڵ�
     * @param object : RationTreeObject ָ���Ķ���
     */
    public void removeNodes(GYPartTreeObject object)
    {
        Vector vec = findNodes(object);
        for (int i = 0; i < vec.size(); i++)
        {
            this.removeNode((GYProductTreeNode) vec.elementAt(i));
        }
    }


    /**
     * ɾ�������ڵ�������нڵ�
     */
    public void removeAllExceptRoot()
    {

    }


    /**
     * ���Ұ���ָ������Ľڵ㼯��
     * @param object RationTreeObject
     * @return  Vector,�ڵ�ļ���
     */
    public Vector findNodes(GYPartTreeObject object)
    {
        Vector vector = new Vector();
        String s1 = object.getUniqueIdentity();
        String s2;
        Enumeration en = (((GYProductTreeNode) (myModel().getRoot())).
                          depthFirstEnumeration());
        while (en.hasMoreElements())
        {
            GYProductTreeNode qmnode = (GYProductTreeNode) en.nextElement();
            GYPartTreeObject obj;
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
     * �ж������Ƿ������ǰ����
     * @param obj
     * @return
     */
    public boolean isInTree(GYProductTreeNode obj)
    {
        String s1 = obj.getObject().getUniqueIdentity();
        String s2;
        Enumeration en = (((GYProductTreeNode) (myModel().getRoot())).
                          depthFirstEnumeration());
        while (en.hasMoreElements())
        {
            GYProductTreeNode qmnode = (GYProductTreeNode) en.nextElement();
            GYPartTreeObject obj1;
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
     * @param object RationTreeObject
     * @return GYProductTreeNode  ���������Ľڵ�
     */
    public GYProductTreeNode findNode(GYPartTreeObject object)
    {
        Vector vec = findNodes(object);
        if (vec.size() > 0)
        {
            return (GYProductTreeNode) vec.elementAt(0);
        }
        else
        {
            return null;
        }
    }


    /**
     * ��ʼ��������ʾ��Ϣ
     * @param hashtable Hashtable ���������з�����Ϣ��hashtable
     */
    public void initTypeLabelNode(Hashtable hashtable)
    {

    }


    /**
     * �õ�ѡ�еĶ���
     * @return RationTreeObject
     */
    public GYPartTreeObject getSelectedObject()
    {
        GYProductTreeNode node = getSelectedNode();
        if (node == null)
        {
            return null;
        }
        return this.getSelectedNode().getObject();
    }


    /**
        �õ�ѡ�еĽڵ�
        @return GYProductTreeNode
        @roseuid 3F0630AA01B9
     */
    public GYProductTreeNode getSelectedNode()
    {
        return (GYProductTreeNode)this.getLastSelectedPathComponent();
    }


    /**
     * ����������еýڵ�
     * @return QMNode[]
     */
    public GYProductTreeNode[] getSelectedNodes()
    {
    	//CR1
        GYProductTreeNode[] nodes = null;
        TreePath[] path = getSelectionPaths();
        
        if (path != null && path.length > 0)
        {
            //CR1
            nodes = new GYProductTreeNode[path.length];
            for (int i = 0, j = path.length; i < j; i++)
            {
                Object[] obj = path[i].getPath();
                nodes[i] = (GYProductTreeNode) obj[obj.length - 1];
            }
        }
        return nodes;
    }

}
