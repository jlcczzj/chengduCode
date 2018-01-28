/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.gybomNotice.client.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.util.NoticeHelper;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p>Title:��Ʒ�ṹ��.�ҽڵ�PartTreeObject </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: һ������</p>
 * @author ����
 * @version 1.0 2014/05/09
 */
public class GYProductTreePanel extends JPanel implements RefreshListener
{
    private JScrollPane jScrollPane1 = new JScrollPane();
    private GYProductTree resourceTree = new GYProductTree();
//    private JCheckBox attrBox = new JCheckBox();
    private BorderLayout borderLayout1 = new BorderLayout();
    private Vector treeListeners = new Vector();
	private BomAdoptNoticeIfc myBomAdoptNotice;
    private static final boolean verbose = NoticeHelper.VERBOSE;
    protected Color redColor = Color.red;

    /** ����Ƿ���ͨ�������ҳ��Ľڵ��ڹ���ʱ��������չ���¼� */
    private boolean addMakeExpand = false;

    /**
     * ���캯��
     * @param bsoName ҵ������
     */
    public GYProductTreePanel(BomAdoptNoticeIfc noticeIfc,Vector vec)
    {
        try
        {
            resourceTree = new GYProductTree("��Ʒ�ṹ",vec);
            addPartData(noticeIfc.getNewAuto());
            RefreshService.getRefreshService().addRefreshListener(this);
            jbInit();
            expandAll(resourceTree, new TreePath(getRootNode()), true);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
	 * չ���������нڵ�
	 * @param tree
	 * @param parent
	 * @param expand
	 */
	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		GYProductTreeNode node = (GYProductTreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				GYProductTreeNode n = (GYProductTreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}
    /**
	 * �б��������
	 * @param String partBsoID
	 */
    private void addPartData(String partBsoID) {

		QMPartIfc partIfc;
		try {
			partIfc = (QMPartIfc) GYNoticeClientUtil.refresh(partBsoID);
			HashMap dataMap = new HashMap();
			// 
			Class[] paraClass = {QMPartIfc.class,HashMap.class};
			Object[] obj = {partIfc,dataMap};
		    dataMap = (HashMap) NoticeHelper.requestServer("BomNoticeService","getSubPartMastersByConfigSpec", paraClass, obj);
		    GYPartTreeObject treeObje = new GYPartTreeObject(getRootNode(),partIfc);
		    GYProductTreeNode node = new GYProductTreeNode();
		    node.setObject(treeObje);
			this.addNode(getRootNode(), node);
		    bianli(dataMap,node);
		
		} catch (QMException e) {
			e.printStackTrace();
		}

	}
    /**
	 * �ݹ���ã���������ṹ����ӵ���
	 * @param HashMap map �������
	 * @param GYProductTreeNode partnode ���ڵ�
	 */
    private void bianli (HashMap map,GYProductTreeNode partnode){

		if(map!=null&&map.size()>0){
			Collection col = (Collection)map.get(((QMPartIfc)partnode.getObject().getObject()).getBsoID());
			if(col!=null&&col.size()>0){
				for(Iterator ite = col.iterator();ite.hasNext();){
					QMPartIfc part = (QMPartIfc)ite.next();
					GYPartTreeObject treeObje = new GYPartTreeObject(partnode,part);
				    GYProductTreeNode node = new GYProductTreeNode();
				    node.setObject(treeObje);
				    
				    this.addNode(partnode, node);
				    bianli(map,node);
				}
			}
			
		}
    }
    
    
    /**
	 * ��ʼ������
	 */
    private void jbInit()
    {
        this.setLayout(borderLayout1);

        //�����ѡ�����
        resourceTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                resourceTree_valueChanged(e);
            }
        });
        
        jScrollPane1.setBorder(null);
        this.setBorder(BorderFactory.createEtchedBorder());
        resourceTree.addMouseListener(new ProductTreePanel_resourceTree_mouseAdapter(this));
        this.add(jScrollPane1, BorderLayout.CENTER);
//        this.add(attrBox, BorderLayout.SOUTH);
        jScrollPane1.getViewport().add(resourceTree, null);


    }

   

    /**
     * ���ҵ�ǰ�ڵ�
     * @param obj RationTreeObject
     */
    public GYProductTreeNode findNode(GYPartTreeObject obj)
    {
        return this.resourceTree.findNode(obj);
    }

    /**
     * �õ���ǰѡ�нڵ�
     * @return GYProductTreeNode
     */
    public GYProductTreeNode getSelectedNode()
    {
        return this.resourceTree.getSelectedNode();
    }

    /**
     * �õ���ǰѡ�еĶ���
     * @return
     */
    public GYPartTreeObject getSelectedObject()
    {
        return this.resourceTree.getSelectedObject();
    }

    /**
     * �������ѡ�м���
     * @param listener TreeSelectionListener
     */
    public synchronized void addTreeSelectionListener(TreeSelectionListener listener)
    {
        if(this.treeListeners == null)
        {
            this.treeListeners = new Vector();
        }
        this.treeListeners.add(listener);
    }

    /**
     * ȥ������ѡ�м���
     * @param listener
     */
    public synchronized void removeTreeSelectionListener(TreeSelectionListener listener)
    {
        if(this.treeListeners != null)
        {
            this.treeListeners.remove(listener);
        }
    }

    /**
     * ֪ͨ���ļ�����
     * @param e TreeSelectionEvent
     */
    private void notifyTreeSelectionListener(TreeSelectionEvent e)
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
     * ��������valuechange�¼�
     * @param e
     */
    void resourceTree_valueChanged(TreeSelectionEvent e)
    {
        try
        {
            this.notifyTreeSelectionListener(e);
        }catch(Exception ex)
        {}
    }

    /**
     * �������������
     * @param listener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        this.resourceTree.addMouseListener(listener);
    }

    /**
     * �������������
     * @param listener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        this.resourceTree.removeMouseListener(listener);
    }

    public synchronized void removeTreeWillExpandListener(TreeWillExpandListener listener)
    {
        resourceTree.removeTreeWillExpandListener(listener);
    }

    private GYPartTreeObject getFirstObject(Vector objs)
    {
        if(objs.size() <= 0)
        {
            return null;
        }
        GYPartTreeObject firstObj, temp;
        firstObj = (GYPartTreeObject)objs.elementAt(0);
        for(Iterator i = objs.iterator();i.hasNext();)
        {
            temp = (GYPartTreeObject)i.next();
            if(getObjectNum(firstObj) > getObjectNum(temp))
            {
                firstObj = temp;
            }
        }
        return firstObj;

    }

    private int getObjectNum(GYPartTreeObject obj)
    {
        if(obj == null)
        {
            return -1;
        }
        String nt = obj.getNoteText();
        StringTokenizer ston = new StringTokenizer(nt, "_");
        if(ston.hasMoreTokens())
        {
            try
            {
                return Integer.parseInt(ston.nextToken());
            }catch(Exception e)
            {
                return -1;
            }
        }
        return -1;
    }
    
    public synchronized void addTreeWillExpandListener(TreeWillExpandListener
            listener)
    {
        resourceTree.addTreeWillExpandListener(listener);
    }
    
    /**
     * ���ڵ�չ��ʱִ�� ���������ڵ㣬����Ч�Ķ�����Ӽ���ʾ��������
     * @param e TreeExpansionEvent
     */
    public void treeWillExpand(TreeExpansionEvent e) throws QMException
    {

        // ���ͨ������dialog�ҵ��Ķ���,�����ڵ�չ��ʱ��������
        if(addMakeExpand)
        {
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TreePath myPath = (TreePath)e.getPath();
        GYProductTreeNode myNode = (GYProductTreeNode)(myPath.getLastPathComponent());
        if(!myNode.equals(this.getRootNode()))
        {
            myNode.removeAllChildren();
            this.resourceTree.myModel().reload(myNode);
        }
        GYPartTreeObject myObject = myNode.getObject();
        if(myObject == null)
        {
            setCursor(Cursor.getDefaultCursor());
            return;
        }else
        {

            Object bvi = myObject.getObject();
            // ���������ڵ�
            if(bvi instanceof QMPartIfc)
            {

            }
        }
        setCursor(Cursor.getDefaultCursor());
    }
       

    /**
     * ��ø��ڵ�
     * @return GYProductTreeNode
     */
    public GYProductTreeNode getRootNode()
    {
        return resourceTree.getRoot();
    }

    /**
     * ��������
     * @param parent GYProductTreeNode
     * @param child RationTreeObject
     */
    public void addNode(GYProductTreeNode parent, GYProductTreeNode child)
    {
        if(!this.resourceTree.isInTree(child))
        {
            this.resourceTree.addNode(parent, child);
        }
    }

    /**
     * չ�����ڵ�(���нڵ㶼���Ե��ñ�����)
     * @param node Ҫչ���Ľڵ�
     * @param vec �ӽڵ㣨RationTreeObject���ļ���
     */
    public void addChilds(GYProductTreeNode node, Collection vec)
    {
        try
        {
            if(vec == null || vec.size() == 0)
            {
                return;
            }
            Iterator i = vec.iterator();
            while(i.hasNext())
            {
            	GYPartTreeObject obj2 = (GYPartTreeObject)i.next();
            	GYProductTreeNode cnode=new GYProductTreeNode(obj2);
                if(!this.resourceTree.isInTree(cnode))
                {
                    this.resourceTree.addNode(node, cnode);
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();

        }
    }
    /**
     * չ�����ڵ�
     * @param TreeExpansionEvent e
     */
    public void treeWillCollapse(TreeExpansionEvent e)
    {
        TreePath myPath = (TreePath)e.getPath();
        GYProductTreeNode myNode = (GYProductTreeNode)(myPath.getLastPathComponent());
        if(myNode.getObject() == null)
        {
            return;
        }
        if(myNode.getObject().getObject() instanceof CodingIfc)
        {
            myNode.removeAllChildren();
            this.resourceTree.myModel().reload(myNode);
        }

    }

    /**
     * ���ö������ڵĽڵ�ѡ��
     * @param obj
     * @return
     */
    public boolean setNodeSelected(GYPartTreeObject obj)
    {
        GYProductTreeNode node = this.findNode(obj);
        if(node == null)
        {
            return false;
        }
        TreePath path = new TreePath(node.getPath());
        this.resourceTree.scrollPathToVisible(path);
        this.resourceTree.setSelectionPath(path);
        return true;
    }

    /**
     * �������ϵķ���ڵ�
     * @param refreshevent
     */
    public void refreshObject(RefreshEvent refreshevent)
    {
    /**
     * Object obj = refreshevent.getTarget(); Object obj1 = refreshevent.getSource(); if (obj1 instanceof String) { String bsoAndProperty = (String) obj1; if (bsoAndProperty.equals(contentBsoName +
     * propertyName)) { if (obj instanceof Object[]) { Object[] objs = (Object[]) obj; BaseValueIfc father = (BaseValueIfc) objs[0]; Collection childs = (Collection) objs[1]; Vector vec = new
     * Vector(); Iterator i = childs.iterator(); while (i.hasNext()) { vec.add(new WorkShopTreeObject((BaseValueIfc) i.next())); } GYProductTreeNode node = this.findNode(new WorkShopTreeObject(
     * father)); if (node.getChildCount() == 0) { addChilds(node, vec); } } } }
     */
    }

    /**
     *�õ�����ѡ��ýڵ�
     * @return GYProductTreeNode[]
     */
    public GYProductTreeNode[] getSelectedNodes()
    {
        return resourceTree.getSelectedNodes();
    }

    void resourceTree_mouseReleased(MouseEvent e)
    {
        int selRow = resourceTree.getRowForLocation(e.getX(), e.getY());
        resourceTree.setSelectionRow(selRow);
    }
    

    /**
     * ɾ���������б�ѡ�е����ڵ�
     */
    public void removeAllSelectedNodes()
    {
        GYProductTreeNode[] nodes = this.resourceTree.getSelectedNodes();
        if (nodes != null && nodes.length > 0)
        {
            boolean flag = true;
            GYProductTreeNode father = null;
            for (int i = 0; i < nodes.length; i++)
            {
                GYProductTreeNode node = nodes[i];
                if (flag)
                {
                    father = (GYProductTreeNode) node.getParent();
                    flag = false;
                }
                resourceTree.removeNode(node);
            }
            if (father != null)
            {
                resourceTree.scrollPathToVisible(new TreePath(father.getPath()));
            }
        }
        else
        {
             this.removeNodeExcepRoot();
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
                GYProductTreeNode node = (GYProductTreeNode) dd.elementAt(i);
                if (node.getObject() != null)
                {
                    this.resourceTree.removeNode(node);
                }

            }
        }

    }
    
    
    /**
     * ����һ���ڵ㣬���������ֱ�Ӻ��ӽڵ㡣
     * @param root ����Ľڵ㡣
     * @return ArrayList ��ArrayList�е�Ԫ��Ϊ�ҵ������нڵ㡣
     */
    public Vector getAllDirectChildren()
    {
        GYProductTreeNode firstChild = resourceTree.getRoot().getC();
        Vector allChildren = new Vector();
        for (GYProductTreeNode node = firstChild; node != null; node = node.getS())
        {
            allChildren.add(node);
        }
        return allChildren;
    }
   
}

class ProductTreePanel_resourceTree_mouseAdapter extends java.awt.event.MouseAdapter
{
    GYProductTreePanel adaptee;

    ProductTreePanel_resourceTree_mouseAdapter(GYProductTreePanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void mouseReleased(MouseEvent e)
    {
        adaptee.resourceTree_mouseReleased(e);
    }
}
