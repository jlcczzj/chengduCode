/**
 * 生成程序RouteTree.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/28 吕航  原因 路线表树节点不能选中多个节点
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
 * Title: 工艺路线部分的树封装类 Description: 这个树只允许挂两级节点.主要挂RouteTreeObject对象.具体的业务类信息封装在RouteTreeObject对象里 Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @version 1.0
 */

public class RouteTree extends JTree
{

    /**
     * 根节点
     */
    private RouteTreeNode root;

    /**
     * 树的模型
     */
    private DefaultTreeModel myModel;

    /**
     * 构造函数
     */
    public RouteTree()
    {

    }

    /**
     * 构造函数
     * @param type 根(标签)节点名
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
     * 得到当前的树模型
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel)getModel();
        return myModel;
    }

    /**
     * 得到根节点
     * @return RouteTreeNode
     */
    public RouteTreeNode getRoot()
    {
        return this.root;
    }

    /**
     * 设置根节点
     * @param root RouteTreeNode
     */
    public void setRoot(RouteTreeNode root)
    {
        this.root = root;
    }

    /**
     * 添加节点
     * @param child 被添加的树节点
     * @return 被添加的树节点
     */
    public RouteTreeNode addNode(RouteTreeNode child)
    {
        myModel.insertNodeInto(child, root, getLocation(root, child));
        return child;
    }

    /**
     * 得到子节点应该加到父节点的位置
     * @param father父节点
     * @param child 子节点
     * @return 位置
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
     * 添加节点
     * @param object 被添加的节点对象
     * @return 被添加的节点
     */
    public RouteTreeNode addNode(RouteTreeObject object)
    {
        return addNode(new RouteTreeNode(object));
    }

    /**
     * 删除一个节点
     * @param node RouteTreeNode 要删除的节点
     * @return RouteTreeNode
     */
    public RouteTreeNode removeNode(RouteTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }

    /**
     * 删除包含指定对象的节点
     * @param object 指定的对象
     */
    public void removeNodes(RouteTreeObject object)
    {
        Vector vec = findNodes(object);
        for(int i = 0;i < vec.size();i++)
            this.removeNode((RouteTreeNode)vec.elementAt(i));
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
     * zz 查找包含指定对象的节点集合 工艺路线表重命名之后，将该编号的树上所有版本的节点树对象刷新 此方法可通过工艺路线表编号查找所有其他版本的路线表树对象（含自己）
     * @param object RouteTreeObject
     * @return Vector,节点的集合
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
     * 查找包含指定对象的节点集合
     * @param object RouteTreeObject
     * @return Vector,节点的集合
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
     * 判断树上是否包含当前节点对象
     * @param obj 指定的节点对象
     * @return 如果该节点在树上,则返回true,否则返回false
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
     * 查找包含指定对象的节点
     * @param object RouteTreeObject
     * @return RouteTreeNode 满足条件的节点
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
     * 得到选中的对象
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
     * 得到选中的节点
     * @return RouteTreeNode
     * @roseuid 3F0630AA01B9
     */
    public RouteTreeNode getSelectedNode()
    {
        return (RouteTreeNode)this.getLastSelectedPathComponent();
    }

    /**
     * 获得树上所有被选中的节点
     * @return 所有被选中的节点
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
    //     * 当鼠标松开时，设置选中鼠标所在的树节点
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
 * <p>Title:RouteTree.java</p> <p>Description: </p> <p>Package:com.faw_qm.cappclients.conscapproute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author
 * @version 1.0
 */
//CR1 begin
//class RouteTree_this_mouseAdapter extends java.awt.event.MouseAdapter
//{
//    //工艺路线部分的树封装类
//    private RouteTree adaptee;
//
//    //构造函数
//    RouteTree_this_mouseAdapter(RouteTree adaptee)
//    {
//        this.adaptee = adaptee;
//    }
//
//    //鼠标释放
//    public void mouseReleased(MouseEvent e)
//    {
//        adaptee.this_mouseReleased(e);
//    }
//}
//CR1 end
