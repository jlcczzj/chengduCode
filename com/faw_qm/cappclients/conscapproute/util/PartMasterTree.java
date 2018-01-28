/** 
 * 生成程序PartMasterTree.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.cappclients.conscapproute.util;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p> Title: 树封装类 </p> <p> Description: 这个树只允许挂两级节点.主要挂PartMasterTreeObject对象.具体的业务 类信息封装在PartMasterTreeObject对象里 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class PartMasterTree extends JTree
{

    /**
     * 根节点
     */
    private PartMasterTreeNode root;

    /**
     * 树的模型
     */
    private DefaultTreeModel myModel;

    /**
     * 构造函数
     */
    public PartMasterTree()
    {

    }

    /**
     * 构造函数
     * @param type 根(标签)节点名
     */
    public PartMasterTree(String type)
    {
        super(new PartMasterTreeNode(type));
        this.setRoot((PartMasterTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new PartMasterTreeCellRenderer());
    }

    /**
     * 得到当前的树模型
     * @return DefaultTreeModel javax.swing.tree.DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel)getModel();
        return myModel;
    }

    /**
     * 得到根节点
     * @return PartMasterTreeNode
     */
    public PartMasterTreeNode getRoot()
    {
        return this.root;
    }

    /**
     * 设置根节点
     * @param root PartMasterTreeNode
     */
    public void setRoot(PartMasterTreeNode root)
    {
        this.root = root;
    }

    /**
     * 添加节点到根结点上
     * @param child 被添加的树节点
     * @return 被添加的树节点
     */
    public PartMasterTreeNode addNode(PartMasterTreeNode child)
    {
        myModel.insertNodeInto(child, root, getLocation(root, child));
        return child;
    }

    /**
     * 添加节点
     * @param father PartMasterTreeNode 父节点
     * @param child PartMasterTreeNode 子节点
     */
    public PartMasterTreeNode addNode(PartMasterTreeNode father, PartMasterTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }

    /**
     * 得到子节点应该加到父节点的位置
     * @param father 父节点
     * @param child 子节点
     * @return 位置
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
     * 添加节点
     * @param father PartMasterTreeNode, 父节点
     * @param object PartMasterTreeObject 子节点封装对象
     */
    public PartMasterTreeNode addNode(PartMasterTreeNode father, PartMasterTreeObject object)
    {
        return addNode(father, new PartMasterTreeNode(object));
    }

    /**
     * 删除一个节点
     * @param node PartMasterTreeNode 要删除的节点
     * @return PartMasterTreeNode
     */
    public PartMasterTreeNode removeNode(PartMasterTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }

    /**
     * 删除包含指定对象的节点
     * @param object 指定的对象
     */
    public void removeNodes(PartMasterTreeObject object)
    {
        Vector vec = findNodes(object);
        for(int i = 0;i < vec.size();i++)
            this.removeNode((PartMasterTreeNode)vec.elementAt(i));
    }

    /**
     * 删除除去根节点之外的所有节点
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
     * 查找包含指定对象的节点集合
     * @param object PartMasterTreeObject
     * @return Vector,节点的集合
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
     * 判断树上是否包含当前节点对象
     * @param obj 指定的节点对象
     * @return 如果该节点在树上,则返回true,否则返回false
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
     * 查找包含指定对象的节点
     * @param object PartMasterTreeObject
     * @return PartMasterTreeNode 满足条件的节点
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
     * 得到选中的对象
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
     * 得到选中的节点
     * @return PartMasterTreeNode
     * @roseuid 3F0630AA01B9
     */
    public PartMasterTreeNode getSelectedNode()
    {
        return (PartMasterTreeNode)this.getLastSelectedPathComponent();
    }

    /**
     * 取得选中的节点
     * @return PartMasterTreeNode 构造业务对象的树节点
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
