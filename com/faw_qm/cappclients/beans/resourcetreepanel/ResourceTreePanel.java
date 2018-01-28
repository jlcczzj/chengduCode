package com.faw_qm.cappclients.beans.resourcetreepanel;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import com.faw_qm.cappclients.util.CappTree;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;


/**
 * <p>Title:资源树bean </p>
 * <p>Description:资源的树的封装bean，bean中封装了capptree，树节点类型为capptreeobject的子类
 * 新添加一种类型的资源到树上时要做一个新的节点封装类，既capptreeobject的子类 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 管春元
 * 修改者 薛静
 * @version 1.0      2003/08/11
 * （1）20060713薛静修改 原因：新建资源和搜索资源时，不需要展开树，添加变量notExpandAllNode标记是否使树展开
 * （2）20060725薛静修改,展开树时不需排序，关闭时删除子节点，修改方法：addChilds(CappTreeNode node, Collection vec)
 *    resourceTree_treeWillCollapse(TreeExpansionEvent e),增加addNodeNotExpand(CappTreeNode father, CappTreeObject obj)
 *    findChild(CappTreeNode father, CappTreeObject obj)
 *    SS1 根据用户组添加工艺树节点 xudezheng 2013-07-22
 */
public class ResourceTreePanel extends JPanel implements RefreshListener
{
    private JScrollPane jScrollPane1 = new JScrollPane();
    private CappTree resourceTree = new CappTree();
    private JCheckBox attrBox = new JCheckBox();
    private BorderLayout borderLayout1 = new BorderLayout();
    private Hashtable resourceTypes;
    private String propertyName;
    private String contentBsoName;
    private String typeText;
    private Vector treeListeners = new Vector();
    private static boolean verbose = RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true").equals("true"); //debug标识

    //20060713 薛静修改 原因：新建资源和搜索资源时，不需要展开所有节点
     /**标记添加节点时是否使树展开,是为false,否则为true*/
    private boolean notExpandAllNode = false;



    //资源文件
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTreeRB",
            RemoteProperty.getVersionLocale());
    /**父窗口*/
    private JFrame parentFrame;

    public ResourceTreePanel(JFrame frame)
    {
        parentFrame = frame;
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     * 构造函数
     * @param type String 资源树名称
     * @param bsoName String 资源的业务类名
     */
    public ResourceTreePanel(String type, String bsoName)
    {
        try
        {
            contentBsoName = bsoName;
            propertyName = RemoteProperty.getProperty(bsoName);
            typeText = CappTreeHelper.getTypeText(bsoName, propertyName);
            resourceTree = new CappTree(type);
            setResourceTypes(CappTreeHelper.getCodingOfFirstClass(bsoName,
                    propertyName));
            RefreshService.getRefreshService().addRefreshListener(this);

            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResourceTreePanel(String type, Hashtable rts)
    {
        try
        {
            resourceTree = new CappTree(type);
            setResourceTypes(rts);
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResourceTreePanel(String bsoName, String type, Hashtable rts)
    {
        try
        {
            contentBsoName = bsoName;
            resourceTree = new CappTree(type);
            setResourceTypes(rts);
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit()
            throws Exception
    {
        setLayout(borderLayout1);
        attrBox.setToolTipText("选中代表不清空树上原来的节点，否则清空树上的节点再添加查找结果");
        attrBox.setText("附加结果");
        resourceTree.addTreeSelectionListener(new javax.swing.event.
                                              TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                resourceTree_valueChanged(e);
            }
        });
        resourceTree.addTreeWillExpandListener(new javax.swing.event.
                                               TreeWillExpandListener()
        {
            public void treeWillExpand(TreeExpansionEvent e)
                    throws ExpandVetoException
            {
                resourceTree_treeWillExpand(e);
            }

            public void treeWillCollapse(TreeExpansionEvent e)
            {
            }
        });
        resourceTree.addTreeWillExpandListener(new javax.swing.event.
                                               TreeWillExpandListener()
        {
            public void treeWillExpand(TreeExpansionEvent e)
            {
            }

            public void treeWillCollapse(TreeExpansionEvent e)
                    throws ExpandVetoException
            {
                resourceTree_treeWillCollapse(e);
            }
        });
        jScrollPane1.setBorder(null);
        setBorder(BorderFactory.createEtchedBorder());
        add(jScrollPane1, BorderLayout.CENTER);
        add(attrBox, BorderLayout.SOUTH);
        jScrollPane1.getViewport().add(resourceTree, null);
        initResourceTypes();
        /**  Enumeration dd =  resourceTree.getRoot().depthFirstEnumeration();
             while(dd.hasMoreElements())
             {

          CappTreeNode node = (CappTreeNode)dd.nextElement();

             }*/
        localize();
    }


    /**
     * 删除节点不包含根节点
     */
    public void removeNodeExceptionNode()
    {
        Enumeration dd = resourceTree.getRoot().children();
        while (dd.hasMoreElements())
        {
            CappTreeNode node = (CappTreeNode) dd.nextElement();
            if (node.getObject() != null)
            {
                resourceTree.removeNode(node);
            }
        }
    }


    /**
     * 本地化
     */
    private void localize()
    {
        attrBox.setText(rb.getString("append result"));
    }


    /**
     * 初始化资源分类信息
     */
    private void initResourceTypes()
    {
        Hashtable resTypes = getResourceTypes();
        CappTreeNode father = resourceTree.getRoot();
        if (resTypes != null)
        {
            addTypeToTree(father, resTypes);

        }
    }


    /**
     * 递归算法
     * 把Hashtable中的分类对象添加到树上
     * @param father 父节点
     * @param resources  Hashtable资源集合
     */
    private void addTypeToTree(CappTreeNode father, Hashtable resources)
    {
        Enumeration keys = resources.keys();
        while (keys.hasMoreElements())
        {

            BaseValueIfc rt = (BaseValueIfc) keys.nextElement();
            ResourceTypeObject rtObj = new ResourceTypeObject(rt,
                    contentBsoName);
            //  rtObj.setContentBsoName(contentBsoName);
//          CCBegin SS1 
            Boolean BSX = false;  
        	try {
        		Class[] paraClass = {};
        		Object[] objs = {};
        		String className = "com.faw_qm.doc.util.DocServiceHelper";
        		String methodName = "isBSXGroup";
        		StaticMethodRequestInfo in = new StaticMethodRequestInfo();
        		in.setClassName(className);
        		in.setMethodName(methodName);
        		in.setParaClasses(paraClass);
        		in.setParaValues(objs);
        		RequestServer server = null;
        		server = RequestServerFactory.getRequestServer(); 
        		BSX = (Boolean) server.request(in);
        	} catch (QMRemoteException e) {
        		e.printStackTrace();
        	}
			if (BSX && rtObj.getNoteText().contains("变速箱")) {
				CappTreeNode childNode = resourceTree.addNode(father, rtObj);
				Object childs = resources.get(rt);
	            if (childs instanceof Hashtable)
	            {
	                addTypeToTree(childNode, (Hashtable) childs);
	            }
			}else if(!BSX && !rtObj.getNoteText().contains("变速箱")){
				CappTreeNode childNode = resourceTree.addNode(father, rtObj);
				Object childs = resources.get(rt);
	            if (childs instanceof Hashtable)
	            {
	                addTypeToTree(childNode, (Hashtable) childs);
	            }
			}
//			CCEnd SS1 
            //Hashtable childs = (Hashtable)resources.get(rt);
			
            
        }

    }


    /**
     * 设置资源分类信息
     * @param rts
     */
    public void setResourceTypes(Hashtable rts)
    {
        resourceTypes = rts;
    }


    /**
     * 得到资源分类信息
     * @return
     */
    public Hashtable getResourceTypes()
    {
        return resourceTypes;
    }


    /**
     * 添加资源(搜索的时候使用，这时树上没有分类节点)
     * @param obj CappTreeObject
     */
    public void addResourceNoSortNode(CappTreeObject obj)
    {
        if (resourceTree.isInTree(obj))
        {
            return;
        }
        BaseValueIfc type = obj.getSortType();
        CappTreeNode father = findNode(new ResourceTypeObject(type,
                contentBsoName));
        System.out.println("father="+father);
        if (father == null)
        {
            Vector vec = new Vector();
            CodingIfc code = (CodingIfc) type;
            vec.add(code);
            CodingClassificationIfc ccifc = code.getCodingClassification();
            while (ccifc != null && !ccifc.getCodeSort().equals(typeText))
            {
                vec.add(ccifc);
                ccifc = ccifc.getParent();
            }
            father = addSortNode(vec);
        }
        //resourceTree.addNode(father,obj);
        //  CappTreeNode node = resourceTree.findNode(obj);
        CappTreeNode node = resourceTree.addNode(father, obj);
        notExpandAllNode = true;
        resourceTree.scrollPathToVisible(new TreePath(node.getPath()));
        notExpandAllNode = false;
    }


    /**
     * 添加分类节点
     * @param vec Vector
     * @return CappTreeNode
     */
    private CappTreeNode addSortNode(Vector vec)
    {
        BaseValueIfc firstType = (BaseValueIfc) vec.elementAt(vec.size() - 1);
        ResourceTypeObject firstObject = new ResourceTypeObject(firstType,
                contentBsoName);
        CappTreeNode firstNode = resourceTree.findNode(firstObject);
        CappTreeNode secNode = null;
        if (firstNode == null)
        {
            firstNode = resourceTree.addNode(resourceTree.getRoot(),
                                             firstObject);
        }
        for (int i = vec.size() - 2; i >= 0; i--)
        {
            firstObject = new ResourceTypeObject((BaseValueIfc) vec.elementAt(i),
                                                 contentBsoName);
            secNode = resourceTree.findNode(firstObject);
            if (secNode == null)
            {
                firstNode = resourceTree.addNode(firstNode, firstObject);
            }
            else
            {
                firstNode = secNode;
            }
        }
        return firstNode;
    }


    /**
     * 添加资源
     * @param obj CappTreeObject
     */
    public void addResource(CappTreeObject obj)
    {
        if (resourceTree.isInTree(obj))
        {
            return;
        }
        CappTreeNode father = findNode(new ResourceTypeObject(obj.
                getSortType(), contentBsoName));
        if (father == null)
        {
            Vector vec = new Vector();
            CodingIfc code = (CodingIfc) obj.getSortType();
            vec.add(code);
            CodingClassificationIfc ccifc = code.getCodingClassification();
            while (ccifc != null && !ccifc.getCodeSort().equals(typeText))
            {
                vec.add(ccifc);
                ccifc = ccifc.getParent();
            }
            father = addSortNode(vec);
        }

        //  resourceTree.addNode(father,obj);
        // CappTreeNode node = resourceTree.findNode(obj);
        //20060713薛静修改 原因：新建资源时，不需要展开树
        notExpandAllNode=true;
        CappTreeNode node = resourceTree.addNode(father, obj);
        resourceTree.scrollPathToVisible(new TreePath(node.getPath()));
        notExpandAllNode=false;
    }


    /**
     * 添加资源的集合
     * @param vec Vector 封装类CappTreeObject的集合
     */
    public void addResources(Vector vec)
    {
        if (!attrBox.isSelected())
        {
            resourceTree.removeAllExceptRoot();
        }
        for (int i = 0; i < vec.size(); i++)
        {
            addResourceNoSortNode((CappTreeObject) vec.elementAt(i));
        }
        /* CappTreeObject obj;
         while(vec.size()>0)
         {
           obj = getFirstObject(vec);
           addResource(obj);
           vec.remove(obj);
         }*/
    }


    /**
     * 更新资源
     * @param obj  资源封装类
     */
    public void updateResource(CappTreeObject obj)
    {
        CappTreeNode node = findNode(obj);
        node.getObject().setObject(obj.getObject());
        node.setObject(obj);
        ((DefaultTreeModel)resourceTree.getModel()).nodeChanged(node);
        // resourceTree.removeNode(node);
        // addResource(obj);
        repaint();
    }


    /**
     * 删除资源
     * @param obj CappTreeObject
     */
    public void removeResource(CappTreeObject obj)
    {
        ResourceTypeObject rt = new ResourceTypeObject(obj.getSortType(),
                contentBsoName);
        resourceTree.removeNodes(obj);
        CappTreeNode node = findNode(rt);
        resourceTree.scrollPathToVisible(new TreePath(node.getPath()));
    }


    /**
     * 删除资源集合
     * @param vec Vector 封装类CappTreeObject的集合
     */
    public void removeResources(Vector vec)
    {
        for (int i = 0; i < vec.size(); i++)
        {
            CappTreeObject obj = (CappTreeObject) vec.elementAt(i);
            removeResource(obj);
        }
    }


    /**
     * 查找当前资源的节点
     * @param obj CappTreeObject
     */
    public CappTreeNode findNode(CappTreeObject obj)
    {
        return resourceTree.findNode(obj);
    }


    /**
     * 得到当前选中节点
     * @return CappTreeNode
     */
    public CappTreeNode getSelectedNode()
    {
        return resourceTree.getSelectedNode();
    }


    /**
     * 得到当前选中的对象
     * @return
     */
    public CappTreeObject getSelectedObject()
    {
        return resourceTree.getSelectedObject();
    }


    /**
     * 添加树的选中监听
     * @param listener TreeSelectionListener
     */
    public synchronized void addTreeSelectionListener(TreeSelectionListener
            listener)
    {
        if (treeListeners == null)
        {
            treeListeners = new Vector();
        }
        treeListeners.add(listener);
    }


    /**
     * 去掉树的选中监听
     * @param listener
     */
    public synchronized void removeTreeSelectionListener(TreeSelectionListener
            listener)
    {
        if (treeListeners != null)
        {
            treeListeners.remove(listener);
        }
    }


    /**
     * 通知树的监听者
     * @param e TreeSelectionEvent
     */
    private void notifyTreeSelectionListener(TreeSelectionEvent e)
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
     * 处理树的valuechange事件
     * @param e
     */
    void resourceTree_valueChanged(TreeSelectionEvent e)
    {
        try
        {
            notifyTreeSelectionListener(e);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 添加树的鼠标监听
     * @param listener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        resourceTree.addMouseListener(listener);
    }


    /**
     * 添加树的鼠标监听
     * @param listener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        resourceTree.removeMouseListener(listener);
    }

    private CappTreeObject getFirstObject(Vector objs)
    {
        if (objs.size() <= 0)
        {
            return null;
        }
        CappTreeObject firstObj, temp;
        firstObj = (CappTreeObject) objs.elementAt(0);
        for (Iterator i = objs.iterator(); i.hasNext(); )
        {
            temp = (CappTreeObject) i.next();
            if (getObjectNum(firstObj) > getObjectNum(temp))
            {
                firstObj = temp;
            }
        }
        return firstObj;

    }

    private int getObjectNum(CappTreeObject obj)
    {
        if (obj == null)
        {
            return -1;
        }
        String nt = obj.getNoteText();
        StringTokenizer ston = new StringTokenizer(nt, "_");
        if (ston.hasMoreTokens())
        {
            try
            {
                return Integer.parseInt(ston.nextToken());
            }
            catch (Exception e)
            {
                return -1;
            }
        }
        return -1;
    }

    void resourceTree_treeWillExpand(TreeExpansionEvent e)
            throws ExpandVetoException
    {
        //如果通过搜索dialog找到的对象,在树节点展开时不作处理
        if (notExpandAllNode)
        {
            return;
        }
        TreePath myPath = (TreePath) e.getPath();
        CappTreeNode myNode = (CappTreeNode) (myPath.getLastPathComponent());
        // if(myNode.getChildCount()>0)
        //  return ;
        try
        {
            if (myNode.getObject() == null)
            {
                if (myNode.getChildCount() > 0)
                {
                    return;
                }

                Collection col = CappTreeHelper.getFirstClassChildren(
                        contentBsoName, propertyName);
                addChilds(myNode, col);
            }
            else
            if (myNode.getObject().getObject() instanceof
                CodingClassificationIfc)
            {
                if (myNode.getChildCount() > 0)
                {
                    return;
                }

                Collection col = CappTreeHelper.getChildrenByClassification((
                        CodingClassificationIfc) myNode.getObject().getObject(),
                        contentBsoName);
                addChilds(myNode, col);
            }
            else
            if (myNode.getObject().getObject() instanceof CodingIfc)
            {
                Collection vec = CappTreeHelper.getContentsByType((
                        ResourceTypeObject) myNode.getObject(),
                        contentBsoName);
                addChilds(myNode, vec);
            }
        }
        catch (QMRemoteException ex)
        {
            if(verbose)
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame,
                                          ((QMRemoteException) ex).
                                          getClientMessage());

        }
    }


    /**
     * 展开树节点
     * @param node 要展开的节点
     */
    void addChilds(CappTreeNode node, Collection vec)
    {
        //ResourceTypeObject obj = (ResourceTypeObject) node.getObject();
        try
        {
            if (vec == null || vec.size() == 0)
            {
                return;
            }
           //20060725薛静修改,展开树时,如果没有子节点则不排序，否则排序
           int childcount = node.getChildCount();
           Iterator i = vec.iterator();
           if (childcount == 0)
           {
               CappTreeObject obj2 ;
               while (i.hasNext())
               {
                    obj2 = (CappTreeObject) i.next();
                   resourceTree.addNodeNotGetLocation(node, obj2);
               }

           }
           else
           {
               CappTreeObject obj2;
               while (i.hasNext())
               {
                   obj2 = (CappTreeObject) i.next();
                   if (!resourceTree.isInTree(obj2))
                   {
                      addNodeNotExpand(node, obj2);
                   }
               }

           }

       }
       catch (Exception e)
       {
           e.printStackTrace();
           if (e instanceof QMRemoteException)
           {
               JOptionPane.showMessageDialog(parentFrame,
                                             ((QMRemoteException) e).
                                             getClientMessage());
           }
       }
   }


       /**
        * 添加节点但不展开
        * @param father 父节点 CappTreeNode
        * @param obj CappTreeObject 子节点封装对象
        */
       public void addNodeNotExpand(CappTreeNode father, CappTreeObject obj)
       {

           CappTreeNode node = findChild(father, obj);
           if (node != null)
           {
               node.setObject(obj);
           }
           else
           {
               resourceTree.addNode(father, obj);
           }

       }

       /**
        * 判断父节点下是否有指定子节点
        * @param father CappTreeNode 父节点
        * @param obj CappTreeObject 子节点
        * @return CappTreeNode 子节点
        */
       private CappTreeNode findChild(CappTreeNode father, CappTreeObject obj)
       {
           CappTreeNode resultNode = null;
           for (int i = 0; i < father.getChildCount(); i++)
           {
               resultNode = (CappTreeNode) father.getChildAt(i);
               if (resultNode.getObject().getUniqueIdentity().equals(obj.
                       getUniqueIdentity()))
               {
                   return resultNode;
               }
           }
           return null;
       }


    void resourceTree_treeWillCollapse(TreeExpansionEvent e)
            throws ExpandVetoException
    {
        TreePath myPath = (TreePath) e.getPath();
        CappTreeNode myNode = (CappTreeNode) (myPath.getLastPathComponent());
        if (myNode.getObject() == null)
        {
            return;
        }
        if (myNode.getObject().getObject() instanceof CodingIfc)
        {//20060725薛静修改,树关闭时将节点删除
            myNode.removeAllChildren();
            resourceTree.myModel().reload(myNode);
        }

    }


    /**
     * 设置对象所在的节点选中
     * @param obj
     * @return
     */
    public boolean setNodeSelected(CappTreeObject obj)
    {
        CappTreeNode node = findNode(obj);
        if (node == null)
        {
            return false;
        }
        TreePath path = new TreePath(node.getPath());
        resourceTree.scrollPathToVisible(path);
        resourceTree.setSelectionPath(path);
        return true;
    }


    /**
     * 更新树上的分类节点
     * @param refreshevent
     */
    public void refreshObject(RefreshEvent refreshevent)
    {
        Object obj = refreshevent.getTarget();
        Object obj1 = refreshevent.getSource();
        if (obj1 instanceof String)
        {
            String bsoAndProperty = (String) obj1;
            if (bsoAndProperty.equals(contentBsoName + propertyName))
            {
                if (obj instanceof Object[])
                {
                    Object[] objs = (Object[]) obj;
                    BaseValueIfc father = (BaseValueIfc) objs[0];
                    Collection childs = (Collection) objs[1];
                    Vector vec = new Vector();
                    Iterator i = childs.iterator();
                    while (i.hasNext())
                    {
                        vec.add(new ResourceTypeObject((BaseValueIfc) i.next(),
                                contentBsoName));
                    }
                    CappTreeNode node = findNode(new ResourceTypeObject(
                            father, contentBsoName));
                    if (node.getChildCount() == 0)
                    {
                        addChilds(node, vec);
                    }
                }
            }
        }
    }


    /**
     *得到树上选择得节点
     * @return CappTreeNode[]
     */
    public CappTreeNode[] getSelectedNodes()
    {
        return resourceTree.getSelectedNodes();
    }

}
