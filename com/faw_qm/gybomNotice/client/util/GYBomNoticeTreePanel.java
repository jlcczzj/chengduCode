/** ���ɳ��� bomNoticeTreePanel.java    1.0    2014-4-21 ����
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ���õ��� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
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
     * ���캯��
     */
    public GYBomNoticeTreePanel()
    {
        try
        {
        	bomNoticeTree = new GYBomNoticeTree("����BOM������");
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * �����ʼ��
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
     * ����ѡ��ı�����¼�
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
     * �������������
     * @param listener MouseListener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {

        this.bomNoticeTree.addMouseListener(listener);
    }


    /**
     * �������������
     * @param listener MouseListener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.bomNoticeTree.removeMouseListener(listener);
    }


    /**
     *������Ըı������
     * @param propertychangelistener -
     */
    public void addPropertyChangeListener(PropertyChangeListener
                                          propertychangelistener)
    {
    	bomNoticeTree.addPropertyChangeListener(propertychangelistener);
    }


    /**
     *ɾ�����Ըı������
     * @param propertychangelistener -
     */
    public void removePropertyChangeListener(PropertyChangeListener
                                             propertychangelistener)
    {
    	bomNoticeTree.removePropertyChangeListener(propertychangelistener);
    }


    /**
     * �������ѡ�м���
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
     * ȥ������ѡ�м���
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
     * ˢ�����ڵ�
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
     * ��ӽڵ㵽����
     * @param obj  RationTreeObject �ӽڵ��װ����
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
     * �Ѽ��������нڵ��������,���Զ�����
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
     * ��ӽڵ㵽����
     * @param RationTreeObjectVector ���ڵ����ļ���
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
     * ɾ�����ϵ����нڵ�(���������ڵ�)
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
     * ��ø��ڵ�
     * @return CappTreeNode
     */
    public GYBomNoticeTreeNode getRootNode()
    {
        return bomNoticeTree.getRoot();
    }


    /**
     * �õ����ڵ�
     * @param obj GYBomNoticeTreeObject
     * @return ָ���ڵ�ĸ��ڵ�
     */
    public GYBomNoticeTreeNode findParentNode(GYBomNoticeTreeObject obj)
    {
        return (GYBomNoticeTreeNode)this.findNode(obj).getParent();
    }


    /**
     * �õ����ڵ�
     * @param node  RationTreeNode
     * @return ָ���ڵ�ĸ��ڵ�
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
     * ����ָ���Ľڵ�
     * @param obj �ڵ����
     * @return  �����º�Ľڵ�
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
     * ����ѡ��ָ�������ڵ�
     * @param obj ָ�������ڵ�
     * @return ����ýڵ㲻�����ϣ��򷵻�False;���򷵻�True
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
     * ȡ��ѡ�����ڵ�
     */
    public void clearSelection()
    {
        this.bomNoticeTree.scrollPathToVisible(null);
        this.bomNoticeTree.setSelectionPath(null);
    }


    /**
     * ɾ��ָ�������ڵ�
     * @param obj ָ�������ڵ�
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
     * ɾ���������б�ѡ�е����ڵ�
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
     * ɾ����ȥ���ڵ�֮������нڵ�
     */
    private void removeAllNodesExceptRoot() 
    {
        bomNoticeTree.removeAllExceptRoot();
    }


    /**
     * �õ���ǰ����ѡ�еĽڵ�
     * @return ��ǰ����ѡ�еĽڵ�
     */
    public GYBomNoticeTreeNode getSelectedNode()
    {
        return this.bomNoticeTree.getSelectedNode();
    }


    /**
     * �õ���ǰ����ѡ�еĶ���
     * @return ��ǰ����ѡ�еĶ���
     */
    public GYBomNoticeTreeObject getSelectedObject()
    {
        return this.bomNoticeTree.getSelectedObject();
    }


    /**
     * ˢ���������ڵ�
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
     * ����һ���ڵ㣬���������ֱ�Ӻ��ӽڵ㡣
     * @param root ����Ľڵ㡣
     * @return ArrayList ��ArrayList�е�Ԫ��Ϊ�ҵ������нڵ㡣
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
     * ˢ��ָ�������ڵ�
     * @param obj �ڵ�
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
     * չ�����ڵ�
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
               //�������Ѿ������ڵĽڵ������ɾ��
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
     * ��ӽڵ㵫��չ��
     * @param obj GYBomNoticeTreeObject �ӽڵ��װ����
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
     * �ж�ָ�������ڵ��Ƿ�������
     * @param vec �������нڵ����ļ���
     * @param obj ָ�������ڵ�
     * @return ����ýڵ������ϣ��򷵻�true
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
