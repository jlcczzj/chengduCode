/** ���ɳ��� PartMasterTreePanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title: �����㲿���������� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class PartMasterTreePanel extends JPanel
{
    private JScrollPane jScrollPane1 = new JScrollPane();

    private PartMasterTree partMasterTree = new PartMasterTree();

    private BorderLayout borderLayout1 = new BorderLayout();

    private Vector treeListeners = new Vector();

    /**
     * ���캯��
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
     * ���캯��
     * @param displayName ���ĸ��ڵ���ʾ��
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
     * �����ʼ��
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
     * ����ѡ��ı�����¼�
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
     * �������������
     * @param listener MouseListener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        this.partMasterTree.addMouseListener(listener);
    }

    /**
     * �������������
     * @param listener MouseListener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.partMasterTree.removeMouseListener(listener);
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
     * ��ӽڵ㵽���ڵ���
     * @param treeobj
     */
    public void addNode(PartMasterTreeObject treeobj)
    {
        PartMasterTreeNode node = new PartMasterTreeNode(treeobj);
        partMasterTree.addNode(node);
    }

    /**
     * ��ӽڵ㵽����
     * @param father ���ڵ� PartMasterTreeNode
     * @param obj PartMasterTreeObject �ӽڵ��װ����
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
     * ��ӽڵ㵽����
     * @param father ���ڵ��װ����
     * @param obj �ӽڵ��װ����
     */
    public void addNode(PartMasterTreeObject father, PartMasterTreeObject obj)
    {
        this.addNode(this.findNode(father), obj);
    }

    /**
     * ��ӽڵ㵽����
     * @param father ���ڵ��װ����
     * @param PartMasterTreeObjectVector ���ڵ����ļ���
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
     * �õ����ڵ�
     * @param obj PartMasterTreeObject
     * @return ָ���ڵ�ĸ��ڵ�
     */
    public PartMasterTreeNode findParentNode(PartMasterTreeObject obj)
    {
        return (PartMasterTreeNode)this.findNode(obj).getParent();
    }

    /**
     * �õ����ڵ�
     * @param node PartMasterTreeNode
     * @return ָ���ڵ�ĸ��ڵ�
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
     * ����ѡ��ָ�������ڵ�
     * @param obj ָ�������ڵ�
     * @return ����ýڵ㲻�����ϣ��򷵻�False;���򷵻�True
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
     * ȡ��ѡ�����ڵ�
     */
    public void clearSelection()
    {
        this.partMasterTree.scrollPathToVisible(null);
        this.partMasterTree.setSelectionPath(null);
    }

    /**
     * ɾ��ָ�������ڵ�
     * @param obj ָ�������ڵ�
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
     * ɾ����ȥ���ڵ�֮������нڵ�
     */
    private void removeAllNodesExceptRoot() //����ʹ
    {
        partMasterTree.removeAllExceptRoot();
    }

    /**
     * �õ���ǰ����ѡ�еĽڵ�
     * @return ��ǰ����ѡ�еĽڵ�
     */
    public PartMasterTreeNode getSelectedNode()
    {
        return this.partMasterTree.getSelectedNode();
    }

    /**
     * �õ���ǰ����ѡ�еĶ���
     * @return ��ǰ����ѡ�еĶ���
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
     * չ�����ڵ�
     * @param node Ҫչ���Ľڵ�
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
            //�������Ѿ������ڵĽڵ������ɾ��
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
     * ��ӽڵ㵫���Զ�չ��
     * @param father ���ڵ�
     * @param obj PartMasterTreeObject �ӽڵ��װ����
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
     * �ҵ��ӽڵ�
     * @param father ���ڵ�
     * @param obj �ӽڵ�ҵ���װ����
     * @return �ӽڵ�
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
     * չ�����ڵ�
     * @param node Ҫչ���Ľڵ�
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
            //�������Ѿ������ڵĽڵ������ɾ��
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
     * �ж�ָ�������ڵ��Ƿ�������
     * @param vec �������нڵ����ļ���
     * @param obj ָ�������ڵ�
     * @return ����ýڵ������ϣ��򷵻�true
     */
    private boolean isInVector(Vector vec, PartMasterTreeObject obj)
    {
        for(int i = 0;i < vec.size();i++)
            if(((PartMasterTreeObject)vec.elementAt(i)).getUniqueIdentity().equals(obj.getUniqueIdentity()))
                return true;
        return false;
    }

    /**
     * ���ڵ�չ��
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