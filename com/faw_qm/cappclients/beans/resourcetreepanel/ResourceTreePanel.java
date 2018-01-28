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
 * <p>Title:��Դ��bean </p>
 * <p>Description:��Դ�����ķ�װbean��bean�з�װ��capptree�����ڵ�����Ϊcapptreeobject������
 * �����һ�����͵���Դ������ʱҪ��һ���µĽڵ��װ�࣬��capptreeobject������ </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author �ܴ�Ԫ
 * �޸��� Ѧ��
 * @version 1.0      2003/08/11
 * ��1��20060713Ѧ���޸� ԭ���½���Դ��������Դʱ������Ҫչ��������ӱ���notExpandAllNode����Ƿ�ʹ��չ��
 * ��2��20060725Ѧ���޸�,չ����ʱ�������򣬹ر�ʱɾ���ӽڵ㣬�޸ķ�����addChilds(CappTreeNode node, Collection vec)
 *    resourceTree_treeWillCollapse(TreeExpansionEvent e),����addNodeNotExpand(CappTreeNode father, CappTreeObject obj)
 *    findChild(CappTreeNode father, CappTreeObject obj)
 *    SS1 �����û�����ӹ������ڵ� xudezheng 2013-07-22
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
            "com.faw_qm.cappclients.verbose", "true").equals("true"); //debug��ʶ

    //20060713 Ѧ���޸� ԭ���½���Դ��������Դʱ������Ҫչ�����нڵ�
     /**�����ӽڵ�ʱ�Ƿ�ʹ��չ��,��Ϊfalse,����Ϊtrue*/
    private boolean notExpandAllNode = false;



    //��Դ�ļ�
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTreeRB",
            RemoteProperty.getVersionLocale());
    /**������*/
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
     * ���캯��
     * @param type String ��Դ������
     * @param bsoName String ��Դ��ҵ������
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
        attrBox.setToolTipText("ѡ�д����������ԭ���Ľڵ㣬����������ϵĽڵ�����Ӳ��ҽ��");
        attrBox.setText("���ӽ��");
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
     * ɾ���ڵ㲻�������ڵ�
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
     * ���ػ�
     */
    private void localize()
    {
        attrBox.setText(rb.getString("append result"));
    }


    /**
     * ��ʼ����Դ������Ϣ
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
     * �ݹ��㷨
     * ��Hashtable�еķ��������ӵ�����
     * @param father ���ڵ�
     * @param resources  Hashtable��Դ����
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
			if (BSX && rtObj.getNoteText().contains("������")) {
				CappTreeNode childNode = resourceTree.addNode(father, rtObj);
				Object childs = resources.get(rt);
	            if (childs instanceof Hashtable)
	            {
	                addTypeToTree(childNode, (Hashtable) childs);
	            }
			}else if(!BSX && !rtObj.getNoteText().contains("������")){
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
     * ������Դ������Ϣ
     * @param rts
     */
    public void setResourceTypes(Hashtable rts)
    {
        resourceTypes = rts;
    }


    /**
     * �õ���Դ������Ϣ
     * @return
     */
    public Hashtable getResourceTypes()
    {
        return resourceTypes;
    }


    /**
     * �����Դ(������ʱ��ʹ�ã���ʱ����û�з���ڵ�)
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
     * ��ӷ���ڵ�
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
     * �����Դ
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
        //20060713Ѧ���޸� ԭ���½���Դʱ������Ҫչ����
        notExpandAllNode=true;
        CappTreeNode node = resourceTree.addNode(father, obj);
        resourceTree.scrollPathToVisible(new TreePath(node.getPath()));
        notExpandAllNode=false;
    }


    /**
     * �����Դ�ļ���
     * @param vec Vector ��װ��CappTreeObject�ļ���
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
     * ������Դ
     * @param obj  ��Դ��װ��
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
     * ɾ����Դ
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
     * ɾ����Դ����
     * @param vec Vector ��װ��CappTreeObject�ļ���
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
     * ���ҵ�ǰ��Դ�Ľڵ�
     * @param obj CappTreeObject
     */
    public CappTreeNode findNode(CappTreeObject obj)
    {
        return resourceTree.findNode(obj);
    }


    /**
     * �õ���ǰѡ�нڵ�
     * @return CappTreeNode
     */
    public CappTreeNode getSelectedNode()
    {
        return resourceTree.getSelectedNode();
    }


    /**
     * �õ���ǰѡ�еĶ���
     * @return
     */
    public CappTreeObject getSelectedObject()
    {
        return resourceTree.getSelectedObject();
    }


    /**
     * �������ѡ�м���
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
     * ȥ������ѡ�м���
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
     * ֪ͨ���ļ�����
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
     * ��������valuechange�¼�
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
     * �������������
     * @param listener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        resourceTree.addMouseListener(listener);
    }


    /**
     * �������������
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
        //���ͨ������dialog�ҵ��Ķ���,�����ڵ�չ��ʱ��������
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
     * չ�����ڵ�
     * @param node Ҫչ���Ľڵ�
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
           //20060725Ѧ���޸�,չ����ʱ,���û���ӽڵ������򣬷�������
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
        * ��ӽڵ㵫��չ��
        * @param father ���ڵ� CappTreeNode
        * @param obj CappTreeObject �ӽڵ��װ����
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
        * �жϸ��ڵ����Ƿ���ָ���ӽڵ�
        * @param father CappTreeNode ���ڵ�
        * @param obj CappTreeObject �ӽڵ�
        * @return CappTreeNode �ӽڵ�
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
        {//20060725Ѧ���޸�,���ر�ʱ���ڵ�ɾ��
            myNode.removeAllChildren();
            resourceTree.myModel().reload(myNode);
        }

    }


    /**
     * ���ö������ڵĽڵ�ѡ��
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
     * �������ϵķ���ڵ�
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
     *�õ�����ѡ��ýڵ�
     * @return CappTreeNode[]
     */
    public CappTreeNode[] getSelectedNodes()
    {
        return resourceTree.getSelectedNodes();
    }

}
