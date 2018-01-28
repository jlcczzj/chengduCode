/** 生成程序TechnicsTreePanel.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/12/29 刘玉柱  TD问题2676
 * CR2 2010/01/13 徐春英  TD问题2806
 * SS1 工艺树按组织区分显示工艺种类 pante 2013-04-05
 * SS2 为了查看问题方便，管理员Administrator可以看到所有类别工艺 liunan 2014-2-8
 * SS3 轴齿用户在工艺树上只显示轴齿的工艺种类 wenliu 2014-2-20
 * SS4 轴齿用户要求其他用户不能看到轴齿工艺种类 pante 2014-06-19
 * SS5 长特用户只能看到长特的工艺种类 guoxiaoliang 2014-08-19
 * SS6 成都结构搜索工艺规程 guoxiaoliang 2016719
 * SS7 成都用户只能看到成都工艺种类 guoxiaoliang 2016719
 */
package com.faw_qm.cappclients.beans.processtreepanel;

import java.awt.BorderLayout;
import java.awt.Cursor;
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

import com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTypeObject;
import com.faw_qm.cappclients.util.BusinessTreeObject;
import com.faw_qm.cappclients.util.CappTree;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.wip.model.WorkableIfc;

//CCBegin SS2
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.users.model.UserIfc;
//CCEnd SS2

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 管春元
 * 修改者 薛静
 * @version 1.0      2003/08/11
 * （1）20060718 薛静修改 原因：新建工艺时不需要展开树，增加变量 notExpandAllNode，标记是否需要展开树
 * （2）20060718薛静修改，原因：树节点关闭时，将子节点删除,这样下次展开时可以避免排序。修改方法：
 *      processTree_treeWillCollapse(TreeExpansionEvent e)
 */

public class TechnicsTreePanel extends JPanel implements RefreshListener
{
    private JScrollPane jScrollPane1 = new JScrollPane();
    private CappTree processTree
            = new CappTree();
    private JCheckBox attrBox = new JCheckBox();
    private BorderLayout borderLayout1 = new BorderLayout();

    private Hashtable processtypes;
    private Vector treeListeners = new Vector();
    private String contentBsoName;
    private String propertyName;
    private String typeText;
    private ResourceBundle rb = ResourceBundle.getBundle(
            "com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeRB",
            RemoteProperty.getVersionLocale());

    private static boolean verbose = RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true").equals("true"); //debug标识

    private JFrame parentFrame;

    //20060713 薛静修改 原因：新建工艺和搜索工艺时，不需要展开所有节点
     /**标记添加节点时是否使树展开,是为false,否则为true*/
    private boolean notExpandAllNode = false;

    public TechnicsTreePanel()
    {
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
     * 新构造函数
     * @param type 树的根节点显示名
     * @param bsoName 类名
     */
    public TechnicsTreePanel(String type, String bsoName)
    {
        try
        {
            contentBsoName = bsoName;
            propertyName = RemoteProperty.getProperty(bsoName);
            typeText = CappTreeHelper.getTypeText(bsoName, propertyName);
            processTree = new CappTree(type);
            Hashtable table = CappTreeHelper.getCodingOfFirstClass(bsoName,
                    propertyName);
            if (table == null || table.size() == 0)
            {
                String title = rb.getString("information");
                String message = rb.getString("technicsTypeCodeLack");
                JOptionPane.showMessageDialog(this, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            setProcessTypes(table);
            RefreshService.getRefreshService().addRefreshListener(this);
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 旧的构造函数，现在不建议用
     * @param type
     * @param rts
     */
    public TechnicsTreePanel(String type, Hashtable rts)
    {
        try
        {
            processTree = new CappTree(type);
            setProcessTypes(rts);
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
       public TechnicsTreePanel(String bsoName,String type,Hashtable rts)
       {
      try {
        contentBsoName = bsoName;
        processTree = new CappTree(type);
        setProcessTypes(rts);
        jbInit();
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
       }*/
    void jbInit()
            throws Exception
    {
        setLayout(borderLayout1);
        attrBox.setToolTipText("代表是否清除树上原有的内容");
        attrBox.setText("附加结果");
        processTree.addTreeWillExpandListener(new javax.swing.event.
                                              TreeWillExpandListener()
        {
            public void treeWillExpand(TreeExpansionEvent e)
                    throws ExpandVetoException
            {
                processTree_treeWillExpand(e);
            }

            public void treeWillCollapse(TreeExpansionEvent e)
            {
            }
        });
        processTree.addTreeSelectionListener(new javax.swing.event.
                                             TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {

                processTree_valueChanged(e);
            }
        });
        processTree.addTreeWillExpandListener(new javax.swing.event.
                                              TreeWillExpandListener()
        {
            public void treeWillExpand(TreeExpansionEvent e)
            {
            }

            public void treeWillCollapse(TreeExpansionEvent e)
                    throws ExpandVetoException
            {
                processTree_treeWillCollapse(e);
            }
        });
        processTree.setToolTipText("");
        jScrollPane1.setBorder(null);
        setBorder(BorderFactory.createEtchedBorder());
        add(jScrollPane1, BorderLayout.CENTER);
        add(attrBox, BorderLayout.SOUTH);
        initProcessTypes();
        jScrollPane1.getViewport().add(processTree, null);
        localize();
    }


    /**
     * 本地化
     */
    private void localize()
    {
        attrBox.setText(rb.getString("append result"));
    }


    /**
     * 初始化分类节点
     */
    public void initProcessTypes()
    {
        CappTreeNode father = processTree.getRoot();
        if (processtypes != null)
        {
            addTypeToTree(father, processtypes);
        }
    }

//CCBegin SS3
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
//CCEnd SS3
    /**
     * 递归算法
     * 把Hashtable中的分类对象添加到树上
     * @param father 父节点
     * @param resources  Hashtable资源集合
     */
    private void addTypeToTree(CappTreeNode father, Hashtable pts)
    {
    	//CCBegin SS1
    	Boolean BSX = false;  
    	//CCBegin SS2
    	String cuser = "";
    	//CCEnd SS2
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
    		
    		//CCBegin SS2
    		UserIfc sysUser = (UserIfc) TechnicsAction.useServiceMethod("SessionService","getCurUserInfo", null, null);
    		cuser = sysUser.getUsersName();
    		//CCEnd SS2
                
    	} catch (QMRemoteException e) {
    		e.printStackTrace();
    	}
    	//CCEnd SS1
        Enumeration keys = pts.keys();
        while (keys.hasMoreElements())
        {
            BaseValueIfc rt = (BaseValueIfc) keys.nextElement();
            //CCBegin SS1
//            CappTreeNode childNode = processTree.addNode(father,
//                    new ResourceTypeObject(rt, contentBsoName));
//            Object childs = pts.get(rt);
//            if (childs instanceof Hashtable)
//            {
//
//                //Hashtable childs = (Hashtable)pts.get(rt);
//                addTypeToTree(childNode, (Hashtable) childs);
//            }
            //CCBegin SS2
            if(cuser.equals("Administrator"))
            {
            	CappTreeNode childNode = processTree.addNode(father,
    						new ResourceTypeObject(rt, contentBsoName));
    				Object childs = pts.get(rt);
    				if (childs instanceof Hashtable)
    				{
    					//Hashtable childs = (Hashtable)pts.get(rt);
    					addTypeToTree(childNode, (Hashtable) childs);
    				}
            } 
            //CCBegin SS3
            else
				try {
					
					//CCBegin SS5
				if(getUserFromCompany().equals("ct")){
					int i = rt.toString().indexOf("长特");
					if(i != -1){
						CappTreeNode childNode = processTree.addNode(father,
								new ResourceTypeObject(rt, contentBsoName));
						Object childs = pts.get(rt);
						if (childs instanceof Hashtable)
						{
							addTypeToTree(childNode, (Hashtable) childs);
						}
					}
					
					
					
				}
				
				else if(getUserFromCompany().equals("zczx")){
					//CCEnd SS5
							int i = rt.toString().indexOf("轴齿");
							if(i != -1){
								CappTreeNode childNode = processTree.addNode(father,
										new ResourceTypeObject(rt, contentBsoName));
								Object childs = pts.get(rt);
								if (childs instanceof Hashtable)
								{
									//Hashtable childs = (Hashtable)pts.get(rt);
									addTypeToTree(childNode, (Hashtable) childs);
								}
							}
					}
					//CCEnd SS3
				//CCBegin SS7
                else if(getUserFromCompany().equals("cd")){
					
					int i = rt.toString().indexOf("成都");
					if(i != -1){
						CappTreeNode childNode = processTree.addNode(father,
								new ResourceTypeObject(rt, contentBsoName));
						Object childs = pts.get(rt);
						if (childs instanceof Hashtable)
						{
							addTypeToTree(childNode, (Hashtable) childs);
						}
					}
				}
				//CCEnd SS7
					else
					if(BSX){
						int i = rt.toString().indexOf("变速箱");
						if(i != -1){
							CappTreeNode childNode = processTree.addNode(father,
									new ResourceTypeObject(rt, contentBsoName));
							Object childs = pts.get(rt);
							if (childs instanceof Hashtable)
							{
								//Hashtable childs = (Hashtable)pts.get(rt);
								addTypeToTree(childNode, (Hashtable) childs);
							}
						}
					}
					else{
						int i = rt.toString().indexOf("变速箱");
						//CCBegin SS4
						int i1 = rt.toString().indexOf("轴齿");
						//CCBegin SS7
						int i3 = rt.toString().indexOf("成都");
						//CCEnd SS7
						
						//CCBegin SS5
						int i2 = rt.toString().indexOf("长特");
						if(i == -1 && i1 == -1 && i2 == -1&&i3==-1){
							//CCEnd SS4
						//CCEnd SS5
							
						
							CappTreeNode childNode = processTree.addNode(father,
									new ResourceTypeObject(rt, contentBsoName));
							Object childs = pts.get(rt);
							if (childs instanceof Hashtable)
							{
								//Hashtable childs = (Hashtable)pts.get(rt);
								addTypeToTree(childNode, (Hashtable) childs);
							}
						}
					}
					 //CCBegin SS3
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//CCEnd SS3
          //CCEnd SS1
        }
    }


    /**
     * 设置工艺分类信息集合
     * @param rts Hashtable 工艺分类信息集合
     */
    public void setProcessTypes(Hashtable rts)
    {
        processtypes = rts;
    }


    /**
     * 添加节点到树上(不建议使用拉)
     * @param father 父节点 CappTreeNode
     * @param obj  CappTreeObject 子节点封装对象
     */
    public void addNode(CappTreeNode father, CappTreeObject obj)
    {
        CappTreeNode node = processTree.findNode(obj);
        if (node != null)
        {
            node.getObject().setObject(obj.getObject());
        }
        else
        {
            processTree.addNode(father, obj);
            node = findNode(obj);
        }
        processTree.scrollPathToVisible(new TreePath(node.getPath()));
        processTree.repaint();
        repaint();
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
            processTree.addNode(father, obj);
        }

    }


    /**
     * 添加节点但不展开，不用检查树上是否已经存在此节点，认为树上不存在此节点
     * @param father 父节点 CappTreeNode
     * @param obj CappTreeObject 子节点封装对象
     */
    public void addNodeFirstTime(CappTreeNode father, CappTreeObject obj)
    {
        processTree.addNodeNotGetLocation(father, obj);
        //processTree.addNode(father, obj);
    }


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


    /**
     * 添加节点到树上
     * @param father CappTreeObject 父节点封装对象
     * @param obj  CappTreeObject 子节点封装对象
     */
    public void addNode(CappTreeObject father, CappTreeObject obj)
    {
        addNode(findNode(father), obj);
    }


    /**
     * 得到父节点
     * @param obj CappTreeObject
     */
    public CappTreeNode findParentNode(CappTreeObject obj)
    {
        return findNode(obj).getP();
    }


    /**
     * 得到父节点
     * @param node  CappTreeNode
     */
    public CappTreeNode findParentNode(CappTreeNode node)
    {
        return findNode(node.getObject()).getP();
    }


    /* public void addProcess(CappTreeObject obj)
       {
      if(processTree.isInTree(obj))
        return;
      ResourceType rt = obj.getSortType();
     CappTreeNode father = processTree.findNode(new ResourceTypeObject(rt));
      processTree.addNode(father,obj);
     }*/

    public CappTreeNode findNode(CappTreeObject obj)
    {
        return processTree.findNode(obj);
    }

    public void addProcess(CappTreeObject obj)
    {
        BaseValueInfo rt = (BaseValueInfo) obj.getSortType();
        CappTreeNode father = processTree.findNode(new ResourceTypeObject(
                rt, contentBsoName));
       //20060718 薛静修改 原因：新建工艺时不需要展开树
         notExpandAllNode = true;
        addNode(father, obj);
        notExpandAllNode = false;
    }


    /**
     * 添加工艺卡/典型工艺卡主信息到树上
     * 只能用在将搜索工艺卡的结果集合添加树上
     * @param vec Vector （CappTreeObject）工艺卡或典型工艺卡封装对象
     */
    public void addProcesses(Vector vec)
    {
        if (!attrBox.isSelected())
        {
            processTree.removeAllExceptRoot();
            // CappTreeObject obj1;
        }
        for (int i = 0; i < vec.size(); i++)
        {
            addProcessNoSortNode((CappTreeObject) vec.elementAt(i));
        }
        /*    while(vec.size()>0)
            {
              obj1 = getFirstObject(vec);
              addProcess(obj1);
              vec.remove(obj1);
            }*/
    }


    /**
     * 添加资源(搜索的时候使用)
     * @param obj CappTreeObject
     */
    public void addProcessNoSortNode(CappTreeObject obj)
    {
        if (processTree.isInTree(obj))
        {
            return;
        }
        BaseValueIfc type = obj.getSortType();
        CappTreeNode father = findNode(new ResourceTypeObject(type,
                contentBsoName));
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

        processTree.addNode(father, obj);
        CappTreeNode node = processTree.findNode(obj);
        //20060718薛静修改 原因：新建工艺时，不需要展开树
        notExpandAllNode = true;
        processTree.scrollPathToVisible(new TreePath(node.getPath()));
        notExpandAllNode = false;

    }

    private CappTreeNode addSortNode(Vector vec)
    {
        BaseValueIfc firstType = (BaseValueIfc) vec.elementAt(vec.size() - 1);
        ResourceTypeObject firstObject = new ResourceTypeObject(firstType,
                contentBsoName);
        CappTreeNode firstNode = processTree.findNode(firstObject);
        CappTreeNode secNode = null;
        if (firstNode == null)
        {
            firstNode = processTree.addNode(processTree.getRoot(),
                                            firstObject);
        }
        for (int i = vec.size() - 2; i < 0; i--)
        {
            firstObject = new ResourceTypeObject((BaseValueIfc) vec.elementAt(i),
                                                 contentBsoName);
            secNode = processTree.findNode(firstObject);
            if (secNode == null)
            {
                firstNode = processTree.addNode(firstNode, firstObject);
            }
            else
            {
                firstNode = secNode;
            }
        }
        return firstNode;
    }


    /**
     * 更新节点
     * @param obj
     */
    public CappTreeNode updateNode(CappTreeObject obj)
    {
        CappTreeNode node = findNode(obj);
        if (node == null)
        {
            return null;
        }
        CappTreeNode father = node.getP();
        if (obj instanceof BusinessTreeObject)
        {
            node.setObject(obj);
           ((DefaultTreeModel)processTree.getModel()).nodeChanged(node);
        }
        else
        {
            processTree.removeNode(node);
            addNode(father, obj);
        }
        //  addChilds(node);
        repaint();
        return node;

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
        processTree.scrollPathToVisible(path);
        processTree.setSelectionPath(path);
        return true;
    }

    public boolean isSelected(CappTreeObject obj)
    {
        CappTreeNode node = findNode(obj);
        if (node == null)
        {
            return false;
        }
        TreePath path = new TreePath(node.getPath());
        return processTree.isPathSelected(path);
    }


    /**
     * 删除对象节点
     * @param obj
     */
    public void removeNode(Vector objs)
    {
        CappTreeObject obj;
        for (int i = 0; i < objs.size(); i++)
        {
            obj = (CappTreeObject) objs.elementAt(i);
            processTree.removeNodes(obj);
        }
    }


    /**
     * 删除对象节点
     * @param obj
     */
    public void removeNode(CappTreeObject obj)
    {
        CappTreeNode node = processTree.findNode(obj);
        if (node != null)
        {
            CappTreeNode father = node.getP();
            processTree.removeNodes(obj);
            processTree.scrollPathToVisible(new TreePath(father.getPath()));
        }
    }


    /**
     * 得到当前树上选中的节点
     * @return
     */
    public CappTreeNode getSelectedNode()
    {
        return processTree.getSelectedNode();
    }


    /**
     * 得到当前树上选中的对象
     * @return
     */
    public CappTreeObject getSelectedObject()
    {
        return processTree.getSelectedObject();
    }


    /**
     * 树节点关闭
     * @param e
     * @throws ExpandVetoException
     */
    void processTree_treeWillCollapse(TreeExpansionEvent e)
            throws ExpandVetoException
    {
        /**   TreePath myPath =(TreePath)e.getPath();
         CappTreeNode myNode =(CappTreeNode)(myPath.getLastPathComponent());
             if(myNode.getObject()==null)
          return;
             if(myNode.getObject().getType()==CappTreeObject.BUSINESSOBJECT)
         {
          System.out.println("删除子节点");

           myNode.removeAllChildren();
           processTree.myModel().reload(myNode);
             }*/

    //20060718薛静修改，原因：树节点关闭时，将子节点删除,这样下次展开时可以避免排序。
    TreePath myPath = (TreePath) e.getPath();
    CappTreeNode myNode = (CappTreeNode) (myPath.getLastPathComponent());
    if (myNode.getObject() == null)
    {
        return;
    }
    if (myNode.getObject().getObject() instanceof CodingIfc)
    {
        myNode.removeAllChildren();
        processTree.myModel().reload(myNode);
    }

     // processTree.removeAllExceptRoot();
    }


    /**
     * 树节电展开
     * @param e
     * @throws ExpandVetoException
     */
    void processTree_treeWillExpand(TreeExpansionEvent e)
            throws ExpandVetoException
    {
       //如果通过搜索dialog找到的对象挂在树上时激发的展开事件，返回
        if (notExpandAllNode)
        {
            return;
        }
        TreePath myPath = (TreePath) e.getPath();
        CappTreeNode myNode = (CappTreeNode) (myPath.getLastPathComponent());
        if (myNode.getObject() == null)
        {
            if (myNode.getChildCount() > 0)
            {
                return;
            }
            try
            {
                Collection col = CappTreeHelper.getFirstClassChildren(
                        contentBsoName, propertyName);
                addCodingChilds(myNode, col);
            }
            catch (QMRemoteException ex)
            {
                if(verbose)
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                                              ((QMRemoteException) ex).
                                              getClientMessage());

            }
        }
        else
        if (myNode.getObject().getObject() instanceof CodingClassificationIfc)
        {
            if (myNode.getChildCount() > 0)
            {
                return;
            }
            try
            {
                Collection col = CappTreeHelper.getChildrenByClassification((
                        CodingClassificationIfc) myNode.getObject().getObject(),
                        contentBsoName);
                addCodingChilds(myNode, col);
            }
            catch (QMRemoteException ex)
            {
                if(verbose)
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                                              ((QMRemoteException) ex).
                                              getClientMessage());

            }
        }
        else

        //如果是代码分类
        if (myNode.getObject().getObject() instanceof CodingIfc)
        {
            AddTechnicsThread thread=new AddTechnicsThread(myNode);
            thread.start();
            //addChilds(myNode);
        }
        else
        if (myNode.getObject().getType() == CappTreeObject.BUSINESSOBJECT)
        {

            AddTechnicsThread thread=new AddTechnicsThread(myNode);
            thread.start();
           // addChilds(myNode);
        }
    }


    /**
     * 展开树节点
     * @param node 要展开的节点
     */
    synchronized void addCodingChilds(CappTreeNode node, Collection vec)
    {
        try
        {
            if (vec == null || vec.size() == 0)
            {
                return;
            }
            Iterator i = vec.iterator();
            while (i.hasNext())
            {
                processTree.addNode(node, (CappTreeObject) i.next());
            }

        }
        catch (Exception e)
        {
            if (e instanceof QMRemoteException)
            {
                JOptionPane.showMessageDialog(this,
                                              ((QMRemoteException) e).
                                              getClientMessage());
            }
        }
    }


    /**
     * 更新当前节点及其祖先节点（只包括业务节点）
     * @param obj　CappTreeObject
     * @throws QMRemoteException
     */
    public void refreshNodeAndParent(CappTreeObject obj)
            throws QMRemoteException
    {
        CappTreeNode node = findNode(obj);
        while (node.getObject().getType() != CappTreeObject.LABELOBJECT)
        {
            BaseValueIfc obj1 = (BaseValueIfc) node.getObject().getObject();
            node.getObject().setObject(CappTreeHelper.refreshInfo(obj1.getBsoID()));
            node.setObject(node.getObject());
            repaint();
            node = node.getP();
        }
    }


    /**
     * 刷新节点
     * @param fatherNode 父节点
     * @param obj 当前节点对象
     */
    /*public void refreshNode(CappTreeNode fatherNode, CappTreeObject obj)
         {
        for (CappTreeNode qmnode1 = fatherNode.getC(); qmnode1 != null;
                                    qmnode1 = fatherNode.getC())
        {
            processTree.removeNode(qmnode1);
        }
        addChilds(fatherNode);
         }*/

      class AddTechnicsThread extends Thread
       {
           private CappTreeNode node;
           public AddTechnicsThread(CappTreeNode node)
           {
               this.node=node;
           }

           public void run()
           {
              // long d1=System.currentTimeMillis();
               if (node != null)
               {
                   setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                  // startProgress();

                  addChilds(node);

                   setCursor(Cursor.getDefaultCursor());

               }
              //long d2=System.currentTimeMillis();
             // System.out.println("展开时间："+(d2-d1));

           }

       }

    /**
     * 展开树节点
     * @param node 要展开的节点
     */
    void addChilds(CappTreeNode node)
    {

        synchronized(node)//begin CR2
    	{//end CR2
        CappTreeObject obj = node.getObject();
        int childcount = node.getChildCount();
        try
        {
            Collection vec = null;

            if (obj.getObject() instanceof CodingIfc)
            {

                ResourceTypeObject rtObj = (ResourceTypeObject) obj;
                //long dd1=System.currentTimeMillis();
                vec = CappTreeHelper.getContentsByType(rtObj,
                        contentBsoName);
                //long dd2=System.currentTimeMillis();
                //System.out.println("调服务+构造节点时间："+(dd2-dd1));
            }
            else
            {
                vec = obj.getContests();
            }
            if (vec == null || vec.size() == 0)
            {
                //把现在已经不存在的节点从树上删除
                for (int i = 0; i < node.getChildCount(); i++)
                {
                    CappTreeNode nodec = (CappTreeNode) node.getChildAt(i);
                    processTree.removeNode(nodec);
                    i = i - 1;

                }
                return;
            }
            Vector objs = (Vector) vec;
            //把现在已经不存在的节点从树上删除
            for (int i = 0,j=node.getChildCount(); i <j; i++)
            {
                CappTreeNode nodec = (CappTreeNode) node.getChildAt(i);
                if (!isInVector(objs, nodec.getObject()))
                {
                    processTree.removeNode(nodec);
                    i = i - 1;
                }
            }
            //父节点是副本
            boolean flag = obj.getObject() instanceof WorkableIfc &&
                           ((WorkableIfc) obj.getObject()).getWorkableState().
                           equalsIgnoreCase("wrk");
            if (flag)
            {
                if (childcount == 0)
                {
                    for (int i = 0; i < objs.size(); i++)
                    {
                        CappTreeObject child = (CappTreeObject) objs.elementAt(
                                i);
                        //父节点是副本，则若子节点是原本，跳过

                        if (((WorkableIfc) child.getObject()).getWorkableState().
                            equals("c/o"))
                        {
                            continue;
                        }
                        addNodeFirstTime(node, child);

                    }

                }
                else
                {
                    for (int i = 0; i < objs.size(); i++)
                    {
                        CappTreeObject child = (CappTreeObject) objs.elementAt(
                                i);
                        //父节点是副本，则若子节点是原本，跳过
                        if (((WorkableIfc) child.getObject()).getWorkableState().
                            equals("c/o"))
                        {
                            continue;
                        }

                        addNodeNotExpand(node, child);
                    }
                }

            }
            else
            {
                if (childcount == 0)
                {
                    for (int i = 0; i < objs.size(); i++)
                    {
                        addNodeFirstTime(node,
                                         (CappTreeObject) objs.elementAt(i));
                    }

                }
                else
                {

                    for (int i = 0; i < objs.size(); i++)
                    {
                        addNodeNotExpand(node,
                                         (CappTreeObject) objs.elementAt(i));
                    }
                }
            }

        }
        catch (Exception e)
        {
            if (e instanceof QMRemoteException)
            {
                JOptionPane.showMessageDialog(this,
                                              ((QMRemoteException) e).
                                              getClientMessage());
            }
        }
        }//CR2
    }


    /**
     * 展开树节点
     * @param node 要展开的节点
     */
    synchronized public void nodeExpaned(CappTreeNode node)
    {
        CappTreeObject obj = node.getObject();
        try
        {
            Collection vec = obj.getContests();

            if (vec == null || vec.size() == 0)
            {
                return;
            }
            Vector objs = (Vector) vec;
            //把现在已经不存在的节点从树上删除
            for (int i = 0; i < node.getChildCount(); i++)
            {
                CappTreeNode nodec = (CappTreeNode) node.getChildAt(i);
                if (!isInVector(objs, nodec.getObject()))
                {
                    processTree.removeNode(nodec);
                    i = i - 1;
                }
            }
            for (int i = 0; i < objs.size(); i++)
            {
                if (obj.getObject() instanceof WorkableIfc)
                {
                    if (((WorkableIfc) obj.getObject()).getWorkableState().
                        equalsIgnoreCase("wrk"))
                    {
                        if (((WorkableIfc) ((CappTreeObject) objs.elementAt(i)).
                             getObject()).getWorkableState().equals("c/o"))
                        {
                            continue;
                        }
                    }
                }
                
                addNode(node, (CappTreeObject) objs.elementAt(i));
            }
            /* while(objs.size()>0)
             {
               obj1 = getFirstObject(objs) ;
               addNodeNotExpand(node,obj1);
               objs.remove(obj1);
             }*/
        }
        catch (Exception e)
        {
            if (e instanceof QMRemoteException)
            {
                JOptionPane.showMessageDialog(this,
                                              ((QMRemoteException) e).
                                              getClientMessage());
            }
        }
    }

    private boolean isInVector(Vector vec, CappTreeObject obj)
    {
        for (int i = 0; i < vec.size(); i++)
        {
            if (((CappTreeObject) vec.elementAt(i)).getUniqueIdentity().equals(
                    obj.getUniqueIdentity()))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * 添加树的鼠标监听
     * @param listener
     */
    public synchronized void addTreeMouseListener(MouseListener listener)
    {
        processTree.addMouseListener(listener);
    }


    /**
     * 添加树的鼠标监听
     * @param listener
     */
    public synchronized void removeTreeMouseListener(MouseListener listener)
    {
        processTree.removeMouseListener(listener);
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

//CCBegin SS6
    
    /**
     * 
     * @param vec Vector
     * @return CappTreeNode
     */
    public void addNodeToTheTreeEnd(CappTreeObject obj) {
    if (processTree.isInTree(obj)) {
      return;
    }
    BaseValueIfc type = obj.getSortType();
    CappTreeNode father = findNode(new ResourceTypeObject(type,
        contentBsoName));
    if (father == null) {
      Vector vec = new Vector();
      CodingIfc code = (CodingIfc) type;
      vec.add(code);
      CodingClassificationIfc ccifc = code.getCodingClassification();
      while (ccifc != null && !ccifc.getCodeSort().equals(typeText)) {
        vec.add(ccifc);
        ccifc = ccifc.getParent();
      }
      father = addSortNode(vec);
    }

    //processTree.addNode(father, obj);
    processTree.addNodeNotGetLocation(father,obj);
    CappTreeNode node = processTree.findNode(obj);
    //20060718薛静修改 原因：新建工艺时，不需要展开树
    notExpandAllNode = true;
    processTree.scrollPathToVisible(new TreePath(node.getPath()));
    notExpandAllNode = false;

  }
    
//CCEnd SS6
    /**
     * 处理树的valuechange事件
     * @param e
     */
    void processTree_valueChanged(TreeSelectionEvent e)
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
                        addCodingChilds(node, vec);
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
        return processTree.getSelectedNodes();
    }

    //Begin CR1
    public CappTree getTree()
    {
        return processTree;
    }//End CR1
}
