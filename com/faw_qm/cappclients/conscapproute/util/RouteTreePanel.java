/**
 * 生成程序 RouteTreePanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/28 吕航 原因 增加可以选中多个树节点方法
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.BorderLayout;

import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.faw_qm.cappclients.conscapproute.view.RParentJPanel;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import javax.swing.tree.DefaultTreeModel;

/**
 * Title: 加载工艺路线树的容器 Description: Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @version 1.0 （问题一） zz 有旧版本在树上，再搜索出新版本，会将新版本也挂到树上 ,据说是不对的，应只挂最新版本
 */

public class RouteTreePanel extends JPanel implements RefreshListener
{
    private JScrollPane jScrollPane1 = new JScrollPane();

    private RouteTree routeTree = new RouteTree();

    private BorderLayout borderLayout1 = new BorderLayout();

    private Vector treeListeners = new Vector();

    /**
     * 构造函数
     */
    public RouteTreePanel()
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
    public RouteTreePanel(String displayName)
    {
        try
        {
            routeTree = new RouteTree(displayName);
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
        routeTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                routeTree_valueChanged(e);
            }
        });
        this.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(routeTree, null);
    }

    /**
     * 树的选择改变监听事件
     * @param e TreeSelectionEvent
     */
    void routeTree_valueChanged(TreeSelectionEvent e)
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
        this.routeTree.addMouseListener(listener);
    }

    /**
     * 添加树的鼠标监听
     * @param listener MouseListener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.routeTree.removeMouseListener(listener);
    }

    /**
     * 添加属性改变监听器
     * @param propertychangelistener -java.beans.PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        routeTree.addPropertyChangeListener(propertychangelistener);
    }

    /**
     * 删除属性改变监听器
     * @param propertychangelistener -java.beans.PropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        routeTree.removePropertyChangeListener(propertychangelistener);
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
     * 刷新树节点
     * @param refreshevent RefreshEvent
     */
    public void refreshObject(RefreshEvent refreshevent)
    {
        Object obj = refreshevent.getTarget();
        Object obj1 = refreshevent.getSource();
        if(obj1 instanceof String)
        {
            String bsoAndProperty = (String)obj1;
            /**
             * if(bsoAndProperty.equals(contentBsoName+propertyName)) { if(obj instanceof Object[]) { Object[] objs = (Object[])obj; BaseValueIfc father = (BaseValueIfc)objs[0]; Collection childs =
             * (Collection)objs[1]; Vector vec = new Vector(); Iterator i = childs.iterator(); while(i.hasNext()) { vec.add(new ResourceTypeObject((BaseValueIfc)i.next(),contentBsoName)); }
             * CappTreeNode node = this.findNode(new ResourceTypeObject(father,contentBsoName)); if(node.getChildCount()==0) addCodingChilds(node,vec); } }
             */
        }
    }

    /**
     * 添加节点到树上 （问题一） zz 有旧版本在树上，再搜索出新版本，会将新版本也挂到树上 ,据说是不对的，应只挂最新版本
     * @param obj RouteTreeObject 子节点封装对象
     */
    public void addNode(RouteTreeObject obj)
    {
        RouteTreeNode node = routeTree.findNode(obj);
        if(node != null)
        {
            //node.getObject().setObject(obj.getObject());原代码
            //begin zz
            this.removeNode(obj);
            this.routeTree.addNode(obj);//end zz
        }else
            this.routeTree.addNode(obj);
        node = this.findNode(obj);
        this.routeTree.scrollPathToVisible(new TreePath(node.getPath()));
    }

    /**
     * 添加节点到树上
     * @param routeTreeObjectVector 树节点对象的集合
     */
    public void addNodes(Vector routeTreeObjectVector)
    {
        if(routeTreeObjectVector != null && routeTreeObjectVector.size() > 0)
        {
            for(int i = 0;i < routeTreeObjectVector.size();i++)
            {
                this.addNode((RouteTreeObject)routeTreeObjectVector.elementAt(i));
            }
        }

    }

    /**
     * 得到父节点
     * @param obj RouteTreeObject
     * @return 指定节点的父节点
     */
    public RouteTreeNode findParentNode(RouteTreeObject obj)
    {
        return (RouteTreeNode)this.findNode(obj).getParent();
    }

    /**
     * 得到父节点
     * @param node RouteTreeNode
     * @return 指定节点的父节点
     */
    public RouteTreeNode findParentNode(RouteTreeNode node)
    {
        return (RouteTreeNode)this.findNode(node.getObject()).getParent();
    }

    public RouteTreeNode findNode(RouteTreeObject obj)
    {
        return this.routeTree.findNode(obj);
    }

    /**
     * 周茁添加 zz 20061220 找到重命名的工艺路线表在树上的各版本对象
     * @param treeObj RouteTreeObject
     * @return Vector
     */
    public Vector findNodesforRename(RouteTreeObject treeObj)
    {
        return this.routeTree.findNodesSpecial(treeObj);
    }

    public Vector findNodesforRename(String nunberStr)
    {
        return this.routeTree.findNodesSpecial(nunberStr);
    }

    /**
     * 更新指定的节点
     * @param obj 节点对象
     * @return 被更新后的节点
     * @throws QMRemoteException
     */
    public RouteTreeNode updateNode(RouteTreeObject obj)
    {

        RouteTreeNode node = this.findNode(obj);
        if(node == null)
        {
            return null;
        }

        if(obj instanceof RouteTreeObject)
        {
            BaseValueIfc bvi = (BaseValueIfc)obj.getObject();
            RouteTreeObject treeobj = node.getObject();
            treeobj.setObject(RParentJPanel.refreshInfo(bvi.getBsoID()));
            node.setObject(treeobj);
        }else
        {
            this.routeTree.removeNode(node);
            this.addNode(obj);
        }
        this.addChilds();
        this.repaint();
        return this.findNode(obj);

    }

    /**
     * 设置选中指定的树节点
     * @param obj 指定的树节点
     * @return 如果该节点不在树上，则返回False;否则返回True
     */
    public boolean setNodeSelected(RouteTreeObject obj)
    {
        RouteTreeNode node = this.findNode(obj);
        if(node == null)
            return false;
        TreePath path = new TreePath(node.getPath());
        this.routeTree.scrollPathToVisible(path);
        this.routeTree.setSelectionPath(path);
        return true;
    }

    /**
     * 取消选中树节点
     */
    public void clearSelection()
    {
        this.routeTree.scrollPathToVisible(null);
        this.routeTree.setSelectionPath(null);
    }

    /**
     * 删除指定的树节点
     * @param obj 指定的树节点
     */
    public void removeNode(RouteTreeObject obj)
    {
        RouteTreeNode node = this.routeTree.findNode(obj);
        if(node != null)
        {
            RouteTreeNode father = (RouteTreeNode)node.getParent();
            routeTree.removeNodes(obj);
            this.routeTree.scrollPathToVisible(new TreePath(father.getPath()));
        }
    }

    /**
     * 删除树上所有被选中的树节点
     */
    public void removeAllSelectedNodes()
    {
        RouteTreeNode[] nodes = this.routeTree.getSelectedNodes();
        if(nodes != null && nodes.length > 0)
        {
            boolean flag = true;
            RouteTreeNode father = null;
            for(int i = 0;i < nodes.length;i++)
            {
                RouteTreeNode node = nodes[i];
                if(flag)
                {
                    father = (RouteTreeNode)node.getParent();
                    flag = false;
                }
                routeTree.removeNode(node);
            }
            if(father != null)
                routeTree.scrollPathToVisible(new TreePath(father.getPath()));
        }
    }

    /**
     * 删除除去根节点之外的所有节点
     */
    private void removeAllNodesExceptRoot() //不好使
    {
        routeTree.removeAllExceptRoot();
    }

    /**
     * 得到当前树上选中的节点
     * @return 当前树上选中的节点
     */
    public RouteTreeNode getSelectedNode()
    {
        return this.routeTree.getSelectedNode();
    }

    /**
     * 得到当前树上选中的对象
     * @return 当前树上选中的对象
     */
    public RouteTreeObject getSelectedObject()
    {
        return this.routeTree.getSelectedObject();
    }

    // CR1 begin
    /**
     * 得到当前树上选中的节点
     * @return 当前树上选中的节点
     */
    public RouteTreeNode[] getSelectedNodes()
    {
        return this.routeTree.getSelectedNodes();
    }

    // CR1 end
    /**
     * 刷新所有树节点 zz 这个方法好像不好使
     */
    public void refreshNode()
    {
        for(RouteTreeNode qmnode1 = routeTree.getRoot().getC();qmnode1 != null;qmnode1 = routeTree.getRoot().getC())
            this.routeTree.removeNode(qmnode1);

        addChilds();
    }

    /**
     * 刷新节点信息 zz 周茁添加
     * @param node RouteTreeNode
     */
    public void nodeChanged(RouteTreeNode node)
    {
        DefaultTreeModel model = (DefaultTreeModel)routeTree.getModel();
        model.nodeChanged(node);
    }

    /**
     * 刷新指定的树节点
     * @param obj 节点
     */
    public void refreshNode(RouteTreeObject obj) //lm
    {
        try
        {
            RouteTreeNode node = this.findNode(obj);
            if(node == null)
            {
                return;
            }

            if(obj instanceof RouteTreeObject)
            {
                BaseValueIfc bvi = (BaseValueIfc)obj.getObject();
                if(bvi instanceof TechnicsRouteListIfc)
                {
                    RouteTreeObject treeobj = node.getObject();
                    TechnicsRouteListIfc oldList = (TechnicsRouteListIfc)bvi;
                    TechnicsRouteListIfc newList = RParentJPanel.refreshListForNew(oldList);
                    if(newList == null)
                    {
                        this.removeNode(obj);
                        return;
                    }
                    // this.routeTree.removeNode(node);
                    // this.addNode(new RouteListTreeObject(newList));
                    treeobj.setObject(newList);
                    node.setObject(treeobj);
                }else
                {
                    RouteTreeObject treeobj = node.getObject();
                    treeobj.setObject(RParentJPanel.refreshInfo(bvi.getBsoID()));
                    node.setObject(treeobj);
                }
            }else
            {
                this.routeTree.removeNode(node);
                this.addNode(obj);
            }
            this.addChilds();
            this.repaint();
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(null, message);
        }
    }

    /**
     * 展开树节点
     */
    synchronized void addChilds()
    {
        RouteTreeObject obj = routeTree.getRoot().getObject();
        try
        {
            if(obj != null)
            {
                Collection vec = obj.getContests();
                //if(obj.getObject() instanceof CodingIfc)
                //{
                //  ResourceTypeObject rtObj = (ResourceTypeObject)obj;
                //  vec =
                // CappTreeHelper.getContentsByType(rtObj,this.contentBsoName);
                //}
                if(vec == null || vec.size() == 0)
                    return;
                Vector objs = (Vector)vec;
                RouteTreeObject obj1;
                //把现在已经不存在的节点从树上删除
                for(int i = 0;i < routeTree.getRoot().getChildCount();i++)
                {
                    RouteTreeNode nodec = (RouteTreeNode)routeTree.getRoot().getChildAt(i);
                    if(!isInVector(objs, nodec.getObject()))
                    {
                        this.routeTree.removeNode(nodec);
                        i = i - 1;
                    }
                }
                for(int i = 0;i < objs.size();i++)
                {
                    this.addNodeNotExpand((RouteTreeObject)objs.elementAt(i));
                }
            }
        }catch(Exception e)
        {
            String message =e.getMessage();
            DialogFactory.showInformDialog(null, message);
        }
    }

    /**
     * 添加节点但不展开
     * @param obj RouteTreeObject 子节点封装对象
     */
    public void addNodeNotExpand(RouteTreeObject obj)
    {
        RouteTreeNode node = routeTree.findNode(obj);
        if(node != null)
            node.getObject().setObject(obj.getObject());
        else
            this.routeTree.addNode(obj);
    }

    /**
     * 判断指定的树节点是否还在树上
     * @param vec 树上所有节点对象的集合
     * @param obj 指定的树节点
     * @return 如果该节点在树上，则返回true
     */
    private boolean isInVector(Vector vec, RouteTreeObject obj)
    {
        for(int i = 0;i < vec.size();i++)
            if(((RouteTreeObject)vec.elementAt(i)).getUniqueIdentity().equals(obj.getUniqueIdentity()))
                return true;
        return false;
    }

}
