/** 生成程序RationTree.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Title: 采用单树，用于RationListTreePanel</p>
 * <p>Description: 这个树只允许挂两级节点.主要挂RationTreeObject对象.具体的业务类信息封装在RationTreeObject对象里</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
 * @version 1.0
 */


public class GYBomNoticeTree extends JTree
{

    /**
     * 根节点
     */
    private GYBomNoticeTreeNode root;


    /**
     * 树的模型
     */
    private DefaultTreeModel myModel;


    /**
     *构造函数
     */
    public GYBomNoticeTree()
    {

    }


    /**
     * 构造函数
     * @param type 根(标签)节点名
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
     * 得到当前的树模型
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel) getModel();
        return myModel;
    }


    /**
     * 得到根节点
     * @return RationTreeNode
     */
    public GYBomNoticeTreeNode getRoot()
    {
        return this.root;
    }


    /**
     *  设置根节点
     *  @param root RationTreeNode
     */
    public void setRoot(GYBomNoticeTreeNode root)
    {
        this.root = root;
    }


    /**
     * 添加节点
     * @param father RationTreeNode  父节点
     * @param child  RationTreeNode  子节点
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeNode father, GYBomNoticeTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * 添加节点
     * @param father RationTreeNode, 父节点
     * @param object  RationTreeObject  子节点封装对象
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeNode father,
    		GYBomNoticeTreeObject object)
    {
        return addNode(father, new GYBomNoticeTreeNode(object));

    }


    /**
     * 添加节点到根节点上
     * @param child 被添加的树节点
     * @return  被添加的树节点
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeNode child)
    {
        myModel.insertNodeInto(child, root, getLocation(root, child));
        return child;
    }


    /**
     * 添加节点到根节点上(不自动排序)
     * @param child RationTreeNode
     */
    public void addNodeNotSort(GYBomNoticeTreeNode child)
    {
        myModel.insertNodeInto(child, root, root.getChildCount());
    }


    /**
     * 得到子节点应该加到父节点的位置
     * @param father  父节点
     * @param child  子节点
     * @return 位置
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
     * 添加节点
     * @param object  被添加的节点对象
     * @return 被添加的节点
     */
    public GYBomNoticeTreeNode addNode(GYBomNoticeTreeObject object)
    {
        return addNode(new GYBomNoticeTreeNode(object));
    }


    /**
     * 删除一个节点
     * @param node RationTreeNode 要删除的节点
     * @return RationTreeNode
     */
    public GYBomNoticeTreeNode removeNode(GYBomNoticeTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }


    /**
     * 删除包含指定对象的节点
     * @param object 指定的对象
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
     * 删除除去根节点之外的所有节点
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
     * 查找包含指定对象的节点集合
     * @param object GYBomNoticeTreeObject
     * @return  Vector,节点的集合
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
     * 判断树上是否包含当前节点对象
     * @param obj 指定的节点对象
     * @return 如果该节点在树上,则返回true,否则返回false
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
     * 查找包含指定对象的节点
     * @param object GYBomNoticeTreeObject
     * @return RationTreeNode  满足条件的节点
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
     * 得到选中的对象
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
     得到选中的节点
     @return RationTreeNode
     @roseuid 3F0630AA01B9
     */
    public GYBomNoticeTreeNode getSelectedNode()
    {
        return (GYBomNoticeTreeNode)this.getLastSelectedPathComponent();
    }


    /**
     * 获得树上所有被选中的节点
     * @return 所有被选中的节点
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
     * 当鼠标松开时，设置选中鼠标所在的树节点
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
