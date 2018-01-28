/**
 * ���ɳ��� RouteTreePanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/28 ���� ԭ�� ���ӿ���ѡ�ж�����ڵ㷽��
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
 * Title: ���ع���·���������� Description: Copyright: Copyright (c) 2004 Company: һ������
 * @author ����
 * @version 1.0 ������һ�� zz �оɰ汾�����ϣ����������°汾���Ὣ�°汾Ҳ�ҵ����� ,��˵�ǲ��Եģ�Ӧֻ�����°汾
 */

public class RouteTreePanel extends JPanel implements RefreshListener
{
    private JScrollPane jScrollPane1 = new JScrollPane();

    private RouteTree routeTree = new RouteTree();

    private BorderLayout borderLayout1 = new BorderLayout();

    private Vector treeListeners = new Vector();

    /**
     * ���캯��
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
     * ���캯��
     * @param displayName ���ĸ��ڵ���ʾ��
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
     * �����ʼ��
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
     * ����ѡ��ı�����¼�
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
     * �������������
     * @param listener MouseListener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        this.routeTree.addMouseListener(listener);
    }

    /**
     * �������������
     * @param listener MouseListener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.routeTree.removeMouseListener(listener);
    }

    /**
     * ������Ըı������
     * @param propertychangelistener -java.beans.PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        routeTree.addPropertyChangeListener(propertychangelistener);
    }

    /**
     * ɾ�����Ըı������
     * @param propertychangelistener -java.beans.PropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        routeTree.removePropertyChangeListener(propertychangelistener);
    }

    /**
     * �������ѡ�м���
     * @param listener TreeSelectionListener
     */
    public synchronized void addTreeSelectionListener(TreeSelectionListener listener)
    {
        if(this.treeListeners == null)
            this.treeListeners = new Vector();
        this.treeListeners.add(listener);
    }

    /**
     * ȥ������ѡ�м���
     * @param listener TreeSelectionListener
     */
    public synchronized void removeTreeSelectionListener(TreeSelectionListener listener)
    {
        if(this.treeListeners != null)
            this.treeListeners.remove(listener);
    }

    /**
     * ˢ�����ڵ�
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
     * ��ӽڵ㵽���� ������һ�� zz �оɰ汾�����ϣ����������°汾���Ὣ�°汾Ҳ�ҵ����� ,��˵�ǲ��Եģ�Ӧֻ�����°汾
     * @param obj RouteTreeObject �ӽڵ��װ����
     */
    public void addNode(RouteTreeObject obj)
    {
        RouteTreeNode node = routeTree.findNode(obj);
        if(node != null)
        {
            //node.getObject().setObject(obj.getObject());ԭ����
            //begin zz
            this.removeNode(obj);
            this.routeTree.addNode(obj);//end zz
        }else
            this.routeTree.addNode(obj);
        node = this.findNode(obj);
        this.routeTree.scrollPathToVisible(new TreePath(node.getPath()));
    }

    /**
     * ��ӽڵ㵽����
     * @param routeTreeObjectVector ���ڵ����ļ���
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
     * �õ����ڵ�
     * @param obj RouteTreeObject
     * @return ָ���ڵ�ĸ��ڵ�
     */
    public RouteTreeNode findParentNode(RouteTreeObject obj)
    {
        return (RouteTreeNode)this.findNode(obj).getParent();
    }

    /**
     * �õ����ڵ�
     * @param node RouteTreeNode
     * @return ָ���ڵ�ĸ��ڵ�
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
     * ������� zz 20061220 �ҵ��������Ĺ���·�߱������ϵĸ��汾����
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
     * ����ָ���Ľڵ�
     * @param obj �ڵ����
     * @return �����º�Ľڵ�
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
     * ����ѡ��ָ�������ڵ�
     * @param obj ָ�������ڵ�
     * @return ����ýڵ㲻�����ϣ��򷵻�False;���򷵻�True
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
     * ȡ��ѡ�����ڵ�
     */
    public void clearSelection()
    {
        this.routeTree.scrollPathToVisible(null);
        this.routeTree.setSelectionPath(null);
    }

    /**
     * ɾ��ָ�������ڵ�
     * @param obj ָ�������ڵ�
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
     * ɾ���������б�ѡ�е����ڵ�
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
     * ɾ����ȥ���ڵ�֮������нڵ�
     */
    private void removeAllNodesExceptRoot() //����ʹ
    {
        routeTree.removeAllExceptRoot();
    }

    /**
     * �õ���ǰ����ѡ�еĽڵ�
     * @return ��ǰ����ѡ�еĽڵ�
     */
    public RouteTreeNode getSelectedNode()
    {
        return this.routeTree.getSelectedNode();
    }

    /**
     * �õ���ǰ����ѡ�еĶ���
     * @return ��ǰ����ѡ�еĶ���
     */
    public RouteTreeObject getSelectedObject()
    {
        return this.routeTree.getSelectedObject();
    }

    // CR1 begin
    /**
     * �õ���ǰ����ѡ�еĽڵ�
     * @return ��ǰ����ѡ�еĽڵ�
     */
    public RouteTreeNode[] getSelectedNodes()
    {
        return this.routeTree.getSelectedNodes();
    }

    // CR1 end
    /**
     * ˢ���������ڵ� zz ����������񲻺�ʹ
     */
    public void refreshNode()
    {
        for(RouteTreeNode qmnode1 = routeTree.getRoot().getC();qmnode1 != null;qmnode1 = routeTree.getRoot().getC())
            this.routeTree.removeNode(qmnode1);

        addChilds();
    }

    /**
     * ˢ�½ڵ���Ϣ zz �������
     * @param node RouteTreeNode
     */
    public void nodeChanged(RouteTreeNode node)
    {
        DefaultTreeModel model = (DefaultTreeModel)routeTree.getModel();
        model.nodeChanged(node);
    }

    /**
     * ˢ��ָ�������ڵ�
     * @param obj �ڵ�
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
     * չ�����ڵ�
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
                //�������Ѿ������ڵĽڵ������ɾ��
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
     * ��ӽڵ㵫��չ��
     * @param obj RouteTreeObject �ӽڵ��װ����
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
     * �ж�ָ�������ڵ��Ƿ�������
     * @param vec �������нڵ����ļ���
     * @param obj ָ�������ڵ�
     * @return ����ýڵ������ϣ��򷵻�true
     */
    private boolean isInVector(Vector vec, RouteTreeObject obj)
    {
        for(int i = 0;i < vec.size();i++)
            if(((RouteTreeObject)vec.elementAt(i)).getUniqueIdentity().equals(obj.getUniqueIdentity()))
                return true;
        return false;
    }

}
