/** 生成程序 bomNoticeTreePanel.java    1.0    2014-4-21 文柳
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.gybomNotice.client.util;


import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.faw_qm.cappclients.capproute.view.RParentJPanel;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;


/**
 * <p>Title: 采用单树 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author lvhang
 * @version 1.0
 */

public class GYBomNoticeTreePanel extends JPanel implements RefreshListener
{
    private JScrollPane jScrollPane1 = new JScrollPane();
    private GYBomNoticeTree bomNoticeTree = new GYBomNoticeTree();
    private BorderLayout borderLayout1 = new BorderLayout();
    private Vector treeListeners = new Vector();


    /**
     * 构造函数
     */
    public GYBomNoticeTreePanel()
    {
        try
        {
        	bomNoticeTree = new GYBomNoticeTree("工艺BOM发布单");
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 组件初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        this.setLayout(borderLayout1);
        bomNoticeTree.addTreeSelectionListener(new javax.swing.event.
                                            TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
            	bomNoticeTree_valueChanged(e);
            }
        });
        this.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(bomNoticeTree, null);
    }


    /**
     * 树的选择改变监听事件
     * @param e TreeSelectionEvent
     */
    void bomNoticeTree_valueChanged(TreeSelectionEvent e)
    {
        Vector vector;
        if (treeListeners != null)
        {
            synchronized (treeListeners)
            {
                vector = (Vector) treeListeners.clone();
            }
            for (int i = 0; i < vector.size(); i++)
            {
                Object listener = (Object) vector.elementAt(i);
                if (listener instanceof TreeSelectionListener)
                {
                    ((TreeSelectionListener) listener).valueChanged((
                            TreeSelectionEvent) e);
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

        this.bomNoticeTree.addMouseListener(listener);
    }


    /**
     * 添加树的鼠标监听
     * @param listener MouseListener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.bomNoticeTree.removeMouseListener(listener);
    }


    /**
     *添加属性改变监听器
     * @param propertychangelistener -
     */
    public void addPropertyChangeListener(PropertyChangeListener
                                          propertychangelistener)
    {
    	bomNoticeTree.addPropertyChangeListener(propertychangelistener);
    }


    /**
     *删除属性改变监听器
     * @param propertychangelistener -
     */
    public void removePropertyChangeListener(PropertyChangeListener
                                             propertychangelistener)
    {
    	bomNoticeTree.removePropertyChangeListener(propertychangelistener);
    }


    /**
     * 添加树的选中监听
     * @param listener TreeSelectionListener
     */
    public synchronized void addTreeSelectionListener(TreeSelectionListener
            listener)
    {
        if (this.treeListeners == null)
        {
            this.treeListeners = new Vector();
        }
        this.treeListeners.add(listener);
    }


    /**
     * 去掉树的选中监听
     * @param listener TreeSelectionListener
     */
    public synchronized void removeTreeSelectionListener(TreeSelectionListener
            listener)
    {
        if (this.treeListeners != null)
        {
            this.treeListeners.remove(listener);
        }
    }


    /**
     * 刷新树节点
     * @param refreshevent RefreshEvent
     */
    public void refreshObject(RefreshEvent refreshevent)
    {
        Object obj = refreshevent.getTarget();
        Object obj1 = refreshevent.getSource();
        if (obj1 instanceof String)
        {
            String bsoAndProperty = (String) obj1;

        }
    }


    /**
     * 添加节点到树上
     * @param obj  RationTreeObject 子节点封装对象
     */
    public void addNode(GYBomNoticeTreeObject obj)
    {
        GYBomNoticeTreeNode node = bomNoticeTree.findNode(obj);
        if (node != null)
        {
            node.getObject().setObject(obj.getObject());
        }
        else
        {
            this.bomNoticeTree.addNode(obj);
        }
        node = this.findNode(obj);
        this.bomNoticeTree.scrollPathToVisible(new TreePath(node.getPath()));
    }


    public void addNode(GYBomNoticeTreeNode node)
    {
        GYBomNoticeTreeObject obj = node.getObject();
        if (!bomNoticeTree.isInTree(obj))
        {
        	bomNoticeTree.addNode(node);
        }
    }


    /**
     * 把集合中所有节点添加上树,不自动排序
     * @param nodeVector Vector
     */
    public void addNode(Vector nodeVector)
    {
        if (nodeVector != null && nodeVector.size() != 0)
        {
            int size = nodeVector.size();
            for (int i = 0; i < size; i++)
            {
                GYBomNoticeTreeNode node = (GYBomNoticeTreeNode) nodeVector.elementAt(i);
                GYBomNoticeTreeObject obj = node.getObject();
                if (!bomNoticeTree.isInTree(obj))
                {
                    bomNoticeTree.addNodeNotSort(node);
                }
            }
        }

    }


    /**
     * 添加节点到树上
     * @param RationTreeObjectVector 树节点对象的集合
     */
    public void addNodes(Vector routeTreeObjectVector)
    {
        if (routeTreeObjectVector != null && routeTreeObjectVector.size() != 0)
        {
            for (int i = 0; i < routeTreeObjectVector.size(); i++)
            {
                this.addNode((GYBomNoticeTreeObject) routeTreeObjectVector.elementAt(
                        i));
            }
        }
    }


    /**
     * 删除树上的所有节点(不包含根节点)
     */
    public void removeNodeExcepRoot()
    {
        Vector dd = getAllDirectChildren();
        if (dd != null && dd.size() != 0)
        {
            int size = dd.size();
            for (int i = 0; i < size; i++)
            {
                GYBomNoticeTreeNode node = (GYBomNoticeTreeNode) dd.elementAt(i);
                if (node.getObject() != null)
                {
                    this.bomNoticeTree.removeNode(node);
                }

            }
        }

    }


    /**
     * 获得根节点
     * @return CappTreeNode
     */
    public GYBomNoticeTreeNode getRootNode()
    {
        return bomNoticeTree.getRoot();
    }


    /**
     * 得到父节点
     * @param obj GYBomNoticeTreeObject
     * @return 指定节点的父节点
     */
    public GYBomNoticeTreeNode findParentNode(GYBomNoticeTreeObject obj)
    {
        return (GYBomNoticeTreeNode)this.findNode(obj).getParent();
    }


    /**
     * 得到父节点
     * @param node  RationTreeNode
     * @return 指定节点的父节点
     */
    public GYBomNoticeTreeNode findParentNode(GYBomNoticeTreeNode node)
    {
        return (GYBomNoticeTreeNode)this.findNode(node.getObject()).getParent();
    }

    public GYBomNoticeTreeNode findNode(GYBomNoticeTreeObject obj)
    {
        return this.bomNoticeTree.findNode(obj);
    }


    /**
     * 更新指定的节点
     * @param obj 节点对象
     * @return  被更新后的节点
     * @throws QMRemoteException
     */
    public GYBomNoticeTreeNode updateNode(GYBomNoticeTreeObject obj) throws QMException
    {

        GYBomNoticeTreeNode node = this.findNode(obj);
        if (node == null)
        {
            return null;
        }

        if (obj instanceof GYBomNoticeTreeObject)
        {
            BaseValueIfc bvi = (BaseValueIfc) obj.getObject();
            GYBomNoticeTreeObject treeobj = node.getObject();
            treeobj.setObject(RParentJPanel.refreshInfo(bvi.getBsoID()));
            node.setObject(treeobj);
        }
        else
        {
            this.bomNoticeTree.removeNode(node);
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
    public boolean setNodeSelected(GYBomNoticeTreeObject obj)
    {
        GYBomNoticeTreeNode node = this.findNode(obj);
        if (node == null)
        {
            return false;
        }
        TreePath path = new TreePath(node.getPath());
        this.bomNoticeTree.scrollPathToVisible(path);
        this.bomNoticeTree.setSelectionPath(path);
        return true;
    }


    /**
     * 取消选中树节点
     */
    public void clearSelection()
    {
        this.bomNoticeTree.scrollPathToVisible(null);
        this.bomNoticeTree.setSelectionPath(null);
    }


    /**
     * 删除指定的树节点
     * @param obj 指定的树节点
     */
    public void removeNode(GYBomNoticeTreeObject obj)
    {
        GYBomNoticeTreeNode node = this.bomNoticeTree.findNode(obj);
        if (node != null)
        {
            GYBomNoticeTreeNode father = (GYBomNoticeTreeNode) node.getParent();
            bomNoticeTree.removeNodes(obj);
            this.bomNoticeTree.scrollPathToVisible(new TreePath(father.getPath()));
        }
    }


    /**
     * 删除树上所有被选中的树节点
     */
    public void removeAllSelectedNodes()
    {
        GYBomNoticeTreeNode[] nodes = this.bomNoticeTree.getSelectedNodes();
        if (nodes != null && nodes.length > 0)
        {
            boolean flag = true;
            GYBomNoticeTreeNode father = null;
            for (int i = 0; i < nodes.length; i++)
            {
                GYBomNoticeTreeNode node = nodes[i];
                if (flag)
                {
                    father = (GYBomNoticeTreeNode) node.getParent();
                    flag = false;
                }
                bomNoticeTree.removeNode(node);
            }
            if (father != null)
            {
                bomNoticeTree.scrollPathToVisible(new TreePath(father.getPath()));
            }
        }
        else
        {
             this.removeNodeExcepRoot();
        }
    }


    /**
     * 删除除去根节点之外的所有节点
     */
    private void removeAllNodesExceptRoot() 
    {
        bomNoticeTree.removeAllExceptRoot();
    }


    /**
     * 得到当前树上选中的节点
     * @return 当前树上选中的节点
     */
    public GYBomNoticeTreeNode getSelectedNode()
    {
        return this.bomNoticeTree.getSelectedNode();
    }


    /**
     * 得到当前树上选中的对象
     * @return 当前树上选中的对象
     */
    public GYBomNoticeTreeObject getSelectedObject()
    {
        return this.bomNoticeTree.getSelectedObject();
    }


    /**
     * 刷新所有树节点
     */
    public void refreshNode()
    {
        for (GYBomNoticeTreeNode qmnode1 = bomNoticeTree.getRoot().getC(); qmnode1 != null;
                                      qmnode1 = bomNoticeTree.getRoot().getC())
        {
            this.bomNoticeTree.removeNode(qmnode1);
        }
        addChilds();
    }


    /**
     * 给定一个节点，获得其所有直接孩子节点。
     * @param root 待查的节点。
     * @return ArrayList 该ArrayList中的元素为找到的所有节点。
     */
    public Vector getAllDirectChildren()
    {
        GYBomNoticeTreeNode firstChild = bomNoticeTree.getRoot().getC();
        Vector allChildren = new Vector();
        for (GYBomNoticeTreeNode node = firstChild; node != null; node = node.getS())
        {
            allChildren.add(node);
        }
        return allChildren;
    }


    /**
     * 刷新指定的树节点
     * @param obj 节点
     */
    public void refreshNode(GYBomNoticeTreeObject obj) //lm
    {
        try
        {
            GYBomNoticeTreeNode node = this.findNode(obj);
            if (node == null)
            {
                return;
            }

            if (obj instanceof GYBomNoticeTreeObject)
            {
                BaseValueIfc bvi = (BaseValueIfc) obj.getObject();
                GYBomNoticeTreeObject treeobj = node.getObject();
                treeobj.setObject(RParentJPanel.refreshInfo(bvi.getBsoID()));
                node.setObject(treeobj);
            }
            else
            {
                this.bomNoticeTree.removeNode(node);
                this.addNode(obj);
            }
            this.addChilds();
            this.repaint();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Notice",
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * 展开树节点
     */
    synchronized void addChilds()
    {
        /**
             GYBomNoticeTreeObject obj =  routeTree.getRoot().getObject();
             try{
               Collection vec = obj.getContests();
               //if(obj.getObject() instanceof CodingIfc)
               //{
               //  ResourceTypeObject rtObj = (ResourceTypeObject)obj;
               //  vec = CappTreeHelper.getContentsByType(rtObj,this.contentBsoName);
               //}
               if(vec==null||vec.size()==0)
          return;
               Vector objs =(Vector)vec;
               GYBomNoticeTreeObject obj1;
               //把现在已经不存在的节点从树上删除
               for(int i = 0;i<routeTree.getRoot().getChildCount();i++)
               {
         RationTreeNode nodec = (RationTreeNode)routeTree.getRoot().getChildAt(i);
          if(!isInVector(objs,nodec.getObject()))
          {
            this.routeTree.removeNode(nodec);
            i =i-1;
          }
               }
               for(int i =0;i<objs.size();i++)
               {
          this.addNodeNotExpand((GYBomNoticeTreeObject)objs.elementAt(i));
               }

               }catch(Exception e)
               {
          if(e instanceof QMRemoteException)
         JOptionPane.showMessageDialog(null,((QMRemoteException)e).getClientMessage());
               }
         }*/
    }


    /**
     * 添加节点但不展开
     * @param obj GYBomNoticeTreeObject 子节点封装对象
     */
    public void addNodeNotExpand(GYBomNoticeTreeObject obj)
    {
        GYBomNoticeTreeNode node = bomNoticeTree.findNode(obj);
        if (node != null)
        {
            node.getObject().setObject(obj.getObject());
        }
        else
        {
            this.bomNoticeTree.addNode(obj);
        }
    }


    /**
     * 判断指定的树节点是否还在树上
     * @param vec 树上所有节点对象的集合
     * @param obj 指定的树节点
     * @return 如果该节点在树上，则返回true
     */
    private boolean isInVector(Vector vec, GYBomNoticeTreeObject obj)
    {
        for (int i = 0; i < vec.size(); i++)
        {
            if (((GYBomNoticeTreeObject) vec.elementAt(i)).getUniqueIdentity().
                equals(obj.getUniqueIdentity()))
            {
                return true;
            }
        }
        return false;
    }


}
