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
 * <p>Company: 一汽启明</p>
 * @author 刘 明
 * @version 1.0
 */

/** 生成程序CappTree.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 20130809 xucy 参见TD7806 原因：增加清除零件列表中零件的功能
 */


public class GYProductTree extends JTree
{
    //资源变量
    private static String __ENCODE__ = "GBK"; //一定要是GBK
    private static String __SERVER_ENCODE__ = "GB2312"; //服务器上的缺省编码


    /**
     * 根节点
     */
    private GYProductTreeNode root;


    /**
     * 树的模型
     */
    private DefaultTreeModel myModel;


    /**
     *构造函数
     */
    public GYProductTree()
    {

    }


    /**
     * 构造函数
     * @param type
     * @type:String 树的类型。有工艺树，资源树等等。。。。
     */
    public GYProductTree(String type,Vector vec)
    {
        super(new GYProductTreeNode(type));
        this.setRoot((GYProductTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new GYBomNoticeTreeCellRenderer(vec));

    
    }


    /**
     * 得到当前的树模型
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel) getModel();
        return myModel;
    }


    /**
     * 得到跟节点
     * @return GYProductTreeNode
     */
    public GYProductTreeNode getRoot()
    {
        return this.root;
    }


    /**
     *  设置跟节点
     *  @param root;GYProductTreeNode
     */
    public void setRoot(GYProductTreeNode root)
    {
        this.root = root;
    }


    /**
     * 添加节点
     * @param father GYProductTreeNode  父节点
     * @param child  GYProductTreeNode  子节点
     */
    public GYProductTreeNode addNode(GYProductTreeNode father,
                                   GYProductTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * 得到子节点应该加到父节点的位置
     * @param father  父节点
     * @param child  子节点
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
     * 添加节点
     * @param father GYProductTreeNode, 父节点
     * @param object  RationTreeObject  子节点封装对象
     */
    public GYProductTreeNode addNode(GYProductTreeNode father,
    		GYPartTreeObject object)
    {
        return addNode(father, new GYProductTreeNode(object));

    }


    /**
     * 删除一个节点
     * @param node GYProductTreeNode 要删除的节点
     * @return GYProductTreeNode
     */
    public GYProductTreeNode removeNode(GYProductTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }


    /**
     * 删除包含指定对象的节点
     * @param object : RationTreeObject 指定的对象
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
     * 删除初根节点外的所有节点
     */
    public void removeAllExceptRoot()
    {

    }


    /**
     * 查找包含指定对象的节点集合
     * @param object RationTreeObject
     * @return  Vector,节点的集合
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
     * 判断树上是否包含当前对象
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
     * 查找包含指定对象的节点
     * @param object RationTreeObject
     * @return GYProductTreeNode  满足条件的节点
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
     * 初始化分类显示信息
     * @param hashtable Hashtable ：包含所有分类信息的hashtable
     */
    public void initTypeLabelNode(Hashtable hashtable)
    {

    }


    /**
     * 得到选中的对象
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
        得到选中的节点
        @return GYProductTreeNode
        @roseuid 3F0630AA01B9
     */
    public GYProductTreeNode getSelectedNode()
    {
        return (GYProductTreeNode)this.getLastSelectedPathComponent();
    }


    /**
     * 获得树上所有得节点
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
