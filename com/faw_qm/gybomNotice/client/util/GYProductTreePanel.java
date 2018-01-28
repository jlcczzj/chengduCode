/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Title:产品结构树.挂节点PartTreeObject </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: 一汽启明</p>
 * @author 文柳
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

    /** 标记是否是通过搜索找出的节点在挂树时激发的树展开事件 */
    private boolean addMakeExpand = false;

    /**
     * 构造函数
     * @param bsoName 业务类名
     */
    public GYProductTreePanel(BomAdoptNoticeIfc noticeIfc,Vector vec)
    {
        try
        {
            resourceTree = new GYProductTree("产品结构",vec);
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
	 * 展开树的所有节点
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
	 * 列表添加数据
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
	 * 递归调用，查找零件结构，添加到树
	 * @param HashMap map 零件集合
	 * @param GYProductTreeNode partnode 树节点
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
	 * 初始化方法
	 */
    private void jbInit()
    {
        this.setLayout(borderLayout1);

        //添加树选择监听
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
     * 查找当前节点
     * @param obj RationTreeObject
     */
    public GYProductTreeNode findNode(GYPartTreeObject obj)
    {
        return this.resourceTree.findNode(obj);
    }

    /**
     * 得到当前选中节点
     * @return GYProductTreeNode
     */
    public GYProductTreeNode getSelectedNode()
    {
        return this.resourceTree.getSelectedNode();
    }

    /**
     * 得到当前选中的对象
     * @return
     */
    public GYPartTreeObject getSelectedObject()
    {
        return this.resourceTree.getSelectedObject();
    }

    /**
     * 添加树的选中监听
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
     * 去掉树的选中监听
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
     * 通知树的监听者
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
     * 处理树的valuechange事件
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
     * 添加树的鼠标监听
     * @param listener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        this.resourceTree.addMouseListener(listener);
    }

    /**
     * 添加树的鼠标监听
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
     * 树节点展开时执行 如果是零件节点，则将生效的定额和子件显示到其下面
     * @param e TreeExpansionEvent
     */
    public void treeWillExpand(TreeExpansionEvent e) throws QMException
    {

        // 如果通过搜索dialog找到的对象,在树节点展开时不作处理
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
            // 如果是零件节点
            if(bvi instanceof QMPartIfc)
            {

            }
        }
        setCursor(Cursor.getDefaultCursor());
    }
       

    /**
     * 获得根节点
     * @return GYProductTreeNode
     */
    public GYProductTreeNode getRootNode()
    {
        return resourceTree.getRoot();
    }

    /**
     * 添加树结点
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
     * 展开树节点(所有节点都可以调用本方法)
     * @param node 要展开的节点
     * @param vec 子节点（RationTreeObject）的集合
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
     * 展开树节点
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
     * 设置对象所在的节点选中
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
     * 更新树上的分类节点
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
     *得到树上选择得节点
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
     * 删除树上所有被选中的树节点
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
                GYProductTreeNode node = (GYProductTreeNode) dd.elementAt(i);
                if (node.getObject() != null)
                {
                    this.resourceTree.removeNode(node);
                }

            }
        }

    }
    
    
    /**
     * 给定一个节点，获得其所有直接孩子节点。
     * @param root 待查的节点。
     * @return ArrayList 该ArrayList中的元素为找到的所有节点。
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
