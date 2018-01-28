/** 生成程序 PartMasterTreePanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.BorderLayout;

import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p> Title: 加载零部件树的容器 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class PartMasterTreePanel extends JPanel
{
    private JScrollPane jScrollPane1 = new JScrollPane();

    private PartMasterTree partMasterTree = new PartMasterTree();

    private BorderLayout borderLayout1 = new BorderLayout();

    private Vector treeListeners = new Vector();

    /**
     * 构造函数
     */
    public PartMasterTreePanel()
    {
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 构造函数
     * @param displayName 树的根节点显示名
     */
    public PartMasterTreePanel(String displayName)
    {
        try
        {
            partMasterTree = new PartMasterTree(displayName);
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 组件初始化
     * @throws Exception
     */
    void jbInit()
    {
        this.setLayout(borderLayout1);
        partMasterTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                partMasterTree_valueChanged(e);
            }
        });

        partMasterTree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener()
        {
            public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException
            {
                partMasterTree_treeWillExpand(e);
            }

            public void treeWillCollapse(TreeExpansionEvent e)
            {}
        });

        this.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(partMasterTree, null);
    }

    /**
     * 树的选择改变监听事件
     * @param e TreeSelectionEvent
     */
    void partMasterTree_valueChanged(TreeSelectionEvent e)
    {
        Vector vector;
        if(treeListeners != null)
        {
            synchronized(treeListeners)
            {
                vector = (Vector)treeListeners.clone();
            }
            for(int i = 0;i < vector.size();i++)
            {
                Object listener = (Object)vector.elementAt(i);
                if(listener instanceof TreeSelectionListener)
                {
                    ((TreeSelectionListener)listener).valueChanged((TreeSelectionEvent)e);
                }
            }
        }
    }

    /**
     * 添加树的鼠标监听
     * @param listener MouseListener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        this.partMasterTree.addMouseListener(listener);
    }

    /**
     * 添加树的鼠标监听
     * @param listener MouseListener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.partMasterTree.removeMouseListener(listener);
    }

    /**
     * 添加树的选中监听
     * @param listener TreeSelectionListener
     */
    public synchronized void addTreeSelectionListener(TreeSelectionListener listener)
    {
        if(this.treeListeners == null)
            this.treeListeners = new Vector();
        this.treeListeners.add(listener);
    }

    /**
     * 去掉树的选中监听
     * @param listener TreeSelectionListener
     */
    public synchronized void removeTreeSelectionListener(TreeSelectionListener listener)
    {
        if(this.treeListeners != null)
            this.treeListeners.remove(listener);
    }

    /**
     * 添加节点到根节点上
     * @param treeobj
     */
    public void addNode(PartMasterTreeObject treeobj)
    {
        PartMasterTreeNode node = new PartMasterTreeNode(treeobj);
        partMasterTree.addNode(node);
    }

    /**
     * 添加节点到树上
     * @param father 父节点 PartMasterTreeNode
     * @param obj PartMasterTreeObject 子节点封装对象
     */
    private void addNode(PartMasterTreeNode father, PartMasterTreeObject obj)
    {
        PartMasterTreeNode node = partMasterTree.findNode(obj);
        if(node != null)
            node.getObject().setObject(obj.getObject());
        else
            this.partMasterTree.addNode(father, obj);
        node = this.findNode(obj);
        this.partMasterTree.scrollPathToVisible(new TreePath(node.getPath()));
    }

    /**
     * 添加节点到树上
     * @param father 父节点封装对象
     * @param obj 子节点封装对象
     */
    public void addNode(PartMasterTreeObject father, PartMasterTreeObject obj)
    {
        this.addNode(this.findNode(father), obj);
    }

    /**
     * 添加节点到树上
     * @param father 父节点封装对象
     * @param PartMasterTreeObjectVector 树节点对象的集合
     */
    public void addNodes(PartMasterTreeObject father, Vector PartMasterTreeObjectVector)
    {
        if(PartMasterTreeObjectVector != null && PartMasterTreeObjectVector.size() > 0)
        {
            for(int i = 0;i < PartMasterTreeObjectVector.size();i++)
            {
                this.addNode(this.findNode(father), (PartMasterTreeObject)PartMasterTreeObjectVector.elementAt(i));
            }
        }
    }

    /**
     * 得到父节点
     * @param obj PartMasterTreeObject
     * @return 指定节点的父节点
     */
    public PartMasterTreeNode findParentNode(PartMasterTreeObject obj)
    {
        return (PartMasterTreeNode)this.findNode(obj).getParent();
    }

    /**
     * 得到父节点
     * @param node PartMasterTreeNode
     * @return 指定节点的父节点
     */
    public PartMasterTreeNode findParentNode(PartMasterTreeNode node)
    {
        return (PartMasterTreeNode)this.findNode(node.getObject()).getParent();
    }

    public PartMasterTreeNode findNode(PartMasterTreeObject obj)
    {
        return this.partMasterTree.findNode(obj);
    }

    /**
     * 设置选中指定的树节点
     * @param obj 指定的树节点
     * @return 如果该节点不在树上，则返回False;否则返回True
     */
    public boolean setNodeSelected(PartMasterTreeObject obj)
    {
        PartMasterTreeNode node = this.findNode(obj);
        if(node == null)
            return false;
        TreePath path = new TreePath(node.getPath());
        this.partMasterTree.scrollPathToVisible(path);
        this.partMasterTree.setSelectionPath(path);
        return true;
    }

    /**
     * 取消选中树节点
     */
    public void clearSelection()
    {
        this.partMasterTree.scrollPathToVisible(null);
        this.partMasterTree.setSelectionPath(null);
    }

    /**
     * 删除指定的树节点
     * @param obj 指定的树节点
     */
    public void removeNode(PartMasterTreeObject obj)
    {
        PartMasterTreeNode node = this.partMasterTree.findNode(obj);
        if(node != null)
        {
            PartMasterTreeNode father = (PartMasterTreeNode)node.getParent();
            partMasterTree.removeNodes(obj);
            this.partMasterTree.scrollPathToVisible(new TreePath(father.getPath()));
        }
    }

    /**
     * 删除除去根节点之外的所有节点
     */
    private void removeAllNodesExceptRoot() //不好使
    {
        partMasterTree.removeAllExceptRoot();
    }

    /**
     * 得到当前树上选中的节点
     * @return 当前树上选中的节点
     */
    public PartMasterTreeNode getSelectedNode()
    {
        return this.partMasterTree.getSelectedNode();
    }

    /**
     * 得到当前树上选中的对象
     * @return 当前树上选中的对象
     */
    public PartMasterTreeObject getSelectedObject()
    {
        return this.partMasterTree.getSelectedObject();
    }

    public PartMasterTreeNode[] getSelectedNodes()
    {
        return partMasterTree.getSelectedNodes();
    }

    /**
     * 展开树节点
     * @param node 要展开的节点
     */
    synchronized void addChilds(PartMasterTreeNode node)
    {
        PartMasterTreeObject obj = node.getObject();
        try
        {
            Collection vec = obj.getContests();
            if(vec == null || vec.size() == 0)
                return;
            Vector objs = (Vector)vec;
            PartMasterTreeObject obj1;
            //把现在已经不存在的节点从树上删除
            for(int i = 0;i < node.getChildCount();i++)
            {
                PartMasterTreeNode nodec = (PartMasterTreeNode)node.getChildAt(i);
                if(!isInVector(objs, nodec.getObject()))
                {
                    this.partMasterTree.removeNode(nodec);
                    i = i - 1;
                }
            }
            for(int i = 0;i < objs.size();i++)
            {
                this.addNodeNotExpand(node, (PartMasterTreeObject)objs.elementAt(i));
            }

        }catch(Exception e)
        {
            String message = e.getMessage();
            DialogFactory.showInformDialog(this, message);
        }
    }

    /**
     * 添加节点但不自动展开
     * @param father 父节点
     * @param obj PartMasterTreeObject 子节点封装对象
     */
    public void addNodeNotExpand(PartMasterTreeNode father, PartMasterTreeObject obj)
    {
        PartMasterTreeNode node = findChild(father, obj);
        if(node != null)
            node.getObject().setObject(obj.getObject());
        else
            this.partMasterTree.addNode(father, obj);
    }

    /**
     * 找到子节点
     * @param father 父节点
     * @param obj 子节点业务封装对象
     * @return 子节点
     */
    private PartMasterTreeNode findChild(PartMasterTreeNode father, PartMasterTreeObject obj)
    {
        PartMasterTreeNode resultNode = null;
        for(int i = 0;i < father.getChildCount();i++)
        {
            resultNode = (PartMasterTreeNode)father.getChildAt(i);
            if(resultNode.getObject().getUniqueIdentity().equals(obj.getUniqueIdentity()))
                return resultNode;
        }
        return null;
    }

    /**
     * 展开树节点
     * @param node 要展开的节点
     */
    synchronized public void nodeExpaned(PartMasterTreeNode node)
    {
        PartMasterTreeObject obj = node.getObject();
        try
        {
            Collection vec = obj.getContests();

            if(vec == null || vec.size() == 0)
                return;
            Vector objs = (Vector)vec;
            PartMasterTreeObject obj1;
            //把现在已经不存在的节点从树上删除
            for(int i = 0;i < node.getChildCount();i++)
            {
                PartMasterTreeNode nodec = (PartMasterTreeNode)node.getChildAt(i);
                if(!isInVector(objs, nodec.getObject()))
                {
                    this.partMasterTree.removeNode(nodec);
                    i = i - 1;
                }
            }
            for(int i = 0;i < objs.size();i++)
            {
                this.addNode(node, (PartMasterTreeObject)objs.elementAt(i));
            }

        }catch(Exception e)
        {
            String message = e.getMessage();
            DialogFactory.showWarningDialog(this, message);
        }
    }

    /**
     * 判断指定的树节点是否还在树上
     * @param vec 树上所有节点对象的集合
     * @param obj 指定的树节点
     * @return 如果该节点在树上，则返回true
     */
    private boolean isInVector(Vector vec, PartMasterTreeObject obj)
    {
        for(int i = 0;i < vec.size();i++)
            if(((PartMasterTreeObject)vec.elementAt(i)).getUniqueIdentity().equals(obj.getUniqueIdentity()))
                return true;
        return false;
    }

    /**
     * 树节电展开
     * @param e
     * @throws ExpandVetoException
     */
    void partMasterTree_treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException
    {
        TreePath myPath = (TreePath)e.getPath();
        PartMasterTreeNode myNode = (PartMasterTreeNode)(myPath.getLastPathComponent());
        if(myNode.getObject() == null)
        {

        }else if(myNode.getObject().getObject() instanceof QMPartMasterIfc)
        {
            addChilds(myNode);
        }
    }

}