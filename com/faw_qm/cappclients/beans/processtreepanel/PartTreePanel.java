/**
 * 生成程序 PartTreePanel.java    1.0    2003/09/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 *CR1  2009/06/08 郭晓亮  参见:测试域:v4r3FunctionTest;TD号2286
 * 
 */

package com.faw_qm.cappclients.beans.processtreepanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorerPartialResultException;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.beans.explorer.QMTree;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.PartShowMasterDialog;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.part.client.design.model.UsageItem;


/**
 * <p>Title: 零部件树</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: faw_qm</p>
 * @author 薛静
 * @version 1.0
 */

public class PartTreePanel extends JPanel
{
    /**
     * 树
     */
    private QMTree partTree = new QMTree();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**配置规则*/
    private ConfigSpecItem configSpecItem = null;


    /**
     * 锁
     */
    protected Object lock2 = new Object();


    /**
     * 锁
     */
    private Object lock = new Object();
    private Vector changeConfigListeners;


    /**用于标记资源文件路径*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private JFrame parentFrame;
    
//  CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 
    private boolean showRoute = false;
//  CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 
    
  public Vector getchangeConfig(){              //CR1
	return changeConfigListeners;
  }

    /**
     * 构造方法
     */
    public PartTreePanel(JFrame frame)
    {
        parentFrame = frame;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 初始化界面
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
        setBorder(BorderFactory.createEtchedBorder());
        setToolTipText("");
        //partTree.setSize(100,200);
        jScrollPane1.setBorder(null);
        add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(partTree, null);

        partTree.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                parttree_valueChanged(e);
            }
        }
        );

    }


    /**
     * 零件被选中时的事件,将子件挂在树上
     * @param e TreeSelectionEvent 事件
     */
    private void parttree_valueChanged(TreeSelectionEvent e)
    {
        try
        {
            QMNode node = partTree.getSelected();
            if (node != null && !node.equals(partTree.getRoot()))
            {
                processNode(node, true);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


    /**
     * 添加鼠标右键监听
     * @param e
     */
    public void addMouseListener(MouseListener e)
    {
        partTree.addMouseListener(e);
    }


    /**
     * 获得树上选中节点
     * @return QMNode
     */
    public QMNode getSelected()
    {
        return partTree.getSelected();
    }


    /**
     * 获得树上所有得节点
     * @return QMNode[]
     */
    public Vector getSelectedNodes()
    {
        //QMNode[] nodes;
        TreePath[] path = partTree.getSelectionPaths();
        Vector nodes = new Vector();
        //nodes = new QMNode[path.length];
        if (path != null && path.length > 0)
        {
            for (int i = 0, j = path.length; i < j; i++)
            {
                Object[] obj = path[i].getPath();
                if (((QMNode) obj[obj.length - 1]).getObj() != null)
                {
                    nodes.add((QMNode) obj[obj.length - 1]);
                }
            }
        }
        return nodes;
    }


    /**
     * 得到树
     * @return QMTree 树
     */
    public QMTree getPartTree()
    {
        return partTree;
    }


    /**
     * 根据PartItem得到树上的节点
     * @param partItem PartItem 零件
     * @return QMNode[] 树上的节点
     */
    public QMNode[] findNodes(PartItem partItem)
    {
        return partTree.findNodes(partItem);
    }


    /**
     * 将节点挂在树上
     * @param node QMNode 父节点
     * @param pi PartItem 子零件
     * @return QMNode 子节点
     */
    public QMNode addNode(QMNode node, PartItem pi)
    {
        return partTree.addNode(node, pi);
    }


    /**
     * 展开第i行
     * @param i int 行
     */
    public void expandRow(int i)
    {
        partTree.expandRow(i);
    }


    /**
     * 获得根节点
     * @return QMNode 根节点
     */
    public QMNode getRoot()
    {
        return partTree.getRoot();
    }


    /**
     * 删除节点
     * @param node QMNode 要删除的节点
     * @return QMNode 删除的节点
     */
    public QMNode removeNode(QMNode node)
    {
        return partTree.removeNode(node);
    }


    /**
     * 将节点加在树上
     * @param qmnode QMNode 父节点
     * @param qmnode1 QMNode 子节点
     * @return QMNode 子节点
     */
    public QMNode addNode(QMNode qmnode, QMNode qmnode1)
    {
        return partTree.addNode(qmnode, qmnode1);

    }


    /**
     *根据接口变量的信息，在qmnode上构件 qmnode1(信息来自接口)，返回qmnode1
     * @param aParent 父节点
     * @param aChildren 子节点
     * @return QMNode
     */
//  CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准    
//  public QMNode addNode(QMNode aParent, Explorable aChildren)
//  {
//
//      QMNode qmnode1 = partTree.addNode(aParent, aChildren);
//      partTree.expandRow(0);
//      return qmnode1;
//  }
  
  public QMNode addNode (QMNode aParent,Explorable aChildren)
  {
    //System.out.println("jinru public          public QMNode addNode (QMNode aParent,Explorable aChildren)"+aChildren.getIdentity());
    QMNode qmnode1;
    if(this.showRoute&&aChildren instanceof UsageItem)
    {
      PartItem pi = (PartItem)((UsageItem)aChildren).getUsesPart();
      String routeText = getRouteText((QMPartInfo)pi.getPart());
      pi.setRouteTest(routeText);
       qmnode1=partTree.addNode (aParent,pi);
       //System.out.println("1111111111111");

    }else
    if(this.showRoute&&aChildren instanceof PartItem)
    {
      PartItem pi = (PartItem)aChildren;
      String routeText = getRouteText((QMPartInfo)pi.getPart());
      pi.setRouteTest(routeText);
       qmnode1=partTree.addNode (aParent,pi);
       //System.out.println("222222222222222");
    }else
     qmnode1=partTree.addNode (aParent,aChildren);
      partTree.expandRow (0);
      return qmnode1;
  }
//CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 


    /**
     * 设置配置规范
     * @param configSpecItem ConfigSpecItem
     */
    public void setConfigSpecItem(ConfigSpecItem configSpecItem)
    {
        this.configSpecItem = configSpecItem;
    }


    /**
     * 获得配置规范
     * @return ConfigSpecItem
     */
    public ConfigSpecItem getConfigSpecItem()
    {
        return configSpecItem;
    }


    /**
     * 根据当前设置的筛选条件刷新树
     * 此方法被 StructConditionController调用
     */
    public void processNewConfigSpec()
    {
        //2003/09/22 lgz
        synchronized (lock)
        {
            //获得根节点
            QMNode root = getRoot();
            Vector children = new Vector();
            //获得子节点，并将下一级节点的对象添加到children中
            for (QMNode n = root.getC(); n != null; n = n.getS())
            {
                QMNode node = n;
                Explorable e = n.getObj();
                children.addElement(e.getObject());
            }
            // get the masters from the parts
            QMPartMasterInfo[] masters = new QMPartMasterInfo[children.size()];
            for (int i = 0; i < children.size(); i++)
            {
                //如果是 master直接加入到数组
                if (children.elementAt(i) instanceof QMPartMasterInfo)
                {
                    masters[i] = (QMPartMasterInfo) children.elementAt(i);
                 
                }
                //如果是part获得其master加入数组
                else if (children.elementAt(i) instanceof QMPartInfo)
                {
                    QMPartInfo part = (QMPartInfo) children.elementAt(i);
                    masters[i] = (QMPartMasterInfo) part.getMaster();
                   
                }
                else
                {
                    return;
                }
            }
            QMNode qmnode;
            while ((qmnode = getRoot().getC()) != null)
            {
                removeNode(qmnode);
            }
           
            addPartMasters(masters);
        }
        notifyChangeConfigListeners();

    }


    /**
     * 创建零部件后在管理器中显示，将其加在根节点下.
     * @param aPart
     */
    public void addPart(QMPartInfo aPart)
    {
        //modify by shf 2003/09/13
        synchronized (lock)
        {
            if (verbose)
            {
                System.out.println(
                        "TechnicsRegulationsMainFrame.addPart()..begin ....");
            }
            boolean okToAdd = true;

            //构造PartItem对象
            PartItem pi = new PartItem(aPart);
//          CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 
            if(this.showRoute)
            {
               String routeText = getRouteText(aPart);
               pi.setRouteTest(routeText);
            }
//          CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线  
            if (configSpecItem == null)
            {
                setConfigSpecCommand();
            }
            //获得当前配置规范
            ConfigSpecItem specItem = configSpecItem;
            if (specItem != null)
            {
                //把当前配置规范传到partItem中
                pi.setConfigSpecItem(specItem);
                // 获得根节点:虚根
                QMNode rootNode = getRoot();

                QMNode[] matchingNodes = findNodes(pi);
                for (int i = 0; i < matchingNodes.length; i++)
                {
                    if (((QMNode) matchingNodes[i].getParent()).equals(
                            rootNode))
                    {
                        okToAdd = false;
                        break;
                    }
                }

                if (okToAdd)
                {
                    QMNode node = addNode(rootNode, pi);
                    expandRow(0);
                }
            }
            if (verbose)
            {
                System.out.println(
                        "TechnicsRegulationsMainFrame.addPart()..end ....");
            }
        }
    }


    /**
     * 把零件加在树上
     * @param partMasters QMPartMasterInfo[] 零件集合
     */
    public void addPartMasters(QMPartMasterInfo[] partMasters)
    {
        synchronized (lock2)
        {
            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.TechnicsRegulationsManager.addPartMaster()..begin ....");
            }
            //如果配置规范为空
            if (configSpecItem == null)
            {
                setConfigSpecCommand();
            }
            Hashtable table = null;
            try
            {
                //获得PART集合
                table = PartHelper.getAllVersionsNow(partMasters,
                        configSpecItem.getConfigSpecInfo());
            }
            catch (QMRemoteException e)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(parentFrame,
                                              e.getLocalizedMessage(),
                                              title,
                                              JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (table != null)
            {
                //获得part集合
                ArrayList part = (ArrayList) table.get("part");
                //获得master集合
                ArrayList partMaster = (ArrayList) table.get("partmaster");
             
                //如果没有不符合的PartMaster
                if (partMaster.size() == 0)
                {
                    for (int i = 0; i < part.size(); i++)
                    {
                        addPart((QMPartInfo) part.get(i));
                    }
                    //jMenuConfigCrit.setEnabled(true);
                }
                //如果有不符合的PartMaster
                else
                {
                    //对每一个part执行addExplorerPart()添加到树上
                    for (int i = 0; i < part.size(); i++)
                    {
                        addPart((QMPartInfo) part.get(i));
                    }

                    //jMenuConfigCrit.setEnabled(true);
                    if (isVisible())
                    {
                        PartShowMasterDialog dialog = new PartShowMasterDialog(
                                partMaster);
                        dialog.setSize(400, 300);
                        Dimension screenSize = Toolkit.getDefaultToolkit().
                                               getScreenSize();
                        Dimension frameSize = dialog.getSize();
                        if (frameSize.height > screenSize.height)
                        {
                            frameSize.height = screenSize.height;
                        }
                        if (frameSize.width > screenSize.width)
                        {
                            frameSize.width = screenSize.width;
                        }
                        dialog.setLocation((screenSize.width - frameSize.width) /
                                           2,
                                           (screenSize.height -
                                            frameSize.height) /
                                           2);
                        dialog.show();
                    }
                }
            }

        }
    }


    /**
     * 将已知节点的子节点加在树上
     * @param qmnode QMNode 已知节点
     * @param flag boolean
     */
    public void processNode(QMNode qmnode, boolean flag)
    {
        Explorable aexplorable[] = null;
        if (qmnode == null)
        {
            return;
        }
        if (qmnode.getObj() == null)
        {
            return;
        }
        boolean flag1 = qmnode.isDetailsCacheValid(); //false
        boolean flag2 = qmnode.isChildrenCacheValid(); //false
        if (qmnode.isChildrenCacheValid() || qmnode.isDetailsCacheValid())
        {
            return;
        }
        try
        {
            QMNode qmnode1 = qmnode;
            qmnode1.setChildrenCache(true); //设置为真
            //if ( !isRefreshContentsOnExpose () )
            qmnode1.setDetailsCache(true); //设置为真
            Explorable explorable = qmnode1.getObj();
            if (explorable != null)
            { //node存在执行
                if (!flag2)
                { //执行
                    //得到这个对象使用的对象数组:元素为usegeItem或者useageMasterItem
                    aexplorable = explorable.getUses();
                    for (int i = 0; i < aexplorable.length; i++)
                    {
                        //if ( isDisplayUsesAsContents () )
                        qmnode1.addDetail(aexplorable[i]);
                        //将子节点信息加入临时节点
                        addNode(qmnode1, aexplorable[i]);
                    }
                    //如果没有字节点信息
                    if (aexplorable.length == 0)
                    {
                        QMNode qmnode2 = qmnode1.getC();
                        if (qmnode2 != null)
                        {
                            removeNode(qmnode1.getC());
                        }
                    }
                }
                if (!flag1)
                { //执行
                    //if ( isRefreshContentsOnExpose () ) //不执行
                    qmnode1.clearDetail();
                    //判断执行，标准不执行
                    //  if ( isDisplayUsesAsContents () &&
                    // isRefreshContentsOnExpose () ) {
                    if (aexplorable == null)
                    {
                        aexplorable = explorable.getUses();
                    }
                    for (int j = 0; j < aexplorable.length; j++)
                    {
                        qmnode1.addDetail(aexplorable[j]);
                        //  }
                    }
                    if (flag)
                    { //执行
                        Explorable aexplorable1[] = new Explorable[0];
                        try
                        {
                            aexplorable1 = explorable.getContents();
                        }
                        catch (QMExplorerPartialResultException
                               qmexplorerpartialresultexception)
                        {
                            aexplorable1 = qmexplorerpartialresultexception.
                                           getPartialResults();
                            String s = String.valueOf(aexplorable1 != null ?
                                    aexplorable1.length : 0);
                            String s1 = qmexplorerpartialresultexception.
                                        getDisplayType() != null ?
                                        qmexplorerpartialresultexception.
                                        getDisplayType() :
                                        explorable.getClass().getName();
                            Object aobj[] =
                                    {
                                    s1, s};
                            // String s2 = "aaa" ;
                            // if ( !confirmAction ( s2 ) )
                            aexplorable1 = null;
                        }
                        if (aexplorable1 != null)
                        {
                            for (int k = 0; k < aexplorable1.length; k++)
                            {
                                qmnode1.addDetail(aexplorable1[k]); //将细节写入
                            }
                        }
                    }
                }
            }
        }
        catch (Exception exception)
        {
            if(verbose)
            exception.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(parentFrame,
                                          exception.getLocalizedMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
    }


    /**
     * 设置当前的筛选条件.
     * @param refresh = true :根据设置的筛选条件刷新管理器页面内的零部件
     *                =false :不刷新管理器页面内的零部件
     */
    public void setConfigSpecCommand()
    {
        try
        {
            //获取零部件配置规范
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper.
                                                getConfigSpec();
            ViewObjectIfc vo = null;
            ConfigSpecItem config = null;
            //如果没有配置规范，构造默认的“标准”配置规范。
            if (configSpecInfo == null)
            {
                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                try
                {
                    dd = (Vector) RequestServerFactory.getRequestServer().
                         request(
                            info1);
                }
                catch (QMRemoteException e)
                {
                    if(verbose)
                    e.printStackTrace();
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(parentFrame,
                                                  e.getLocalizedMessage(),
                                                  title,
                                                  JOptionPane.INFORMATION_MESSAGE);

                    return;
                }
                dd = null;
                if (dd == null || dd.size() == 0)
                {
                    return;
                }
                for (int i = 0; i < dd.size(); i++)
                {
                    if (((ViewObjectIfc) dd.elementAt(i)).getViewName().equals(
                            "制造视图"))
                    {
                        vo = (ViewObjectIfc) dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                //标准配置项
                PartStandardConfigSpec partStandardConfigSpec = new
                        PartStandardConfigSpec();
                //设置标准时的视图
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //调用服务将设置好的筛选条件保存到数据库中
                RequestServer server = RequestServerFactory.getRequestServer();
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("ExtendedPartService");
                info.setMethodName("savePartConfigSpec");
                Class[] paramClass =
                        {PartConfigSpecIfc.class};
                info.setParaClasses(paramClass);
                Object[] paramValue =
                        {configSpecInfo};
                info.setParaValues(paramValue);
                try
                {
                    configSpecInfo = (PartConfigSpecInfo) server.request(info);
                }
                catch (QMRemoteException ex)
                {
                    ex.printStackTrace();
                    return;
                }
                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpecItem(config);
            }
            else
            {
                config = new ConfigSpecItem(configSpecInfo);
            }
            setConfigSpecItem(config);
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 配置规则
     */
    public void configureCriterion()
    {
        setConfigSpecCommand();
        //显示“编辑结构筛选条件”页面并将数据库中存在的筛选条件传给该页面。
        ConfigSpecController c = new ConfigSpecController(configSpecItem, this,parentFrame);
    }


    /**
     * 添加配置规范改变监听者
     * @param obj ActionListener
     */
    public void addChangeConfigItemListener(ActionListener obj)
    {
        if (changeConfigListeners == null)
        {
            changeConfigListeners = new Vector();
        }
        
        changeConfigListeners.add(obj);
    }


    /**
     * 通知配置规范改变监听者
     */
    public void notifyChangeConfigListeners()
    {
        if (changeConfigListeners != null)
        {
            for (int i = 0; i < changeConfigListeners.size(); i++)
            {
                ((ActionListener) changeConfigListeners.elementAt(i)).
                        actionPerformed(
                        null);
            }
        }
    }
    
//  CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 
    
    public String getRouteText(QMPartInfo apart)
    {
     ServiceRequestInfo myInfo1 = new ServiceRequestInfo();
      myInfo1.setServiceName("TechnicsRouteService");
      myInfo1.setMethodName("getRouteText");
      Class[] theClass = {QMPartInfo.class};
      myInfo1.setParaClasses(theClass);
      Object[] theObject = {apart};
      myInfo1.setParaValues(theObject);
      String text;
      try{
        text = (String) RequestServerFactory.getRequestServer().request(myInfo1);
      }catch(QMRemoteException ex)
      {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                         QMMessage.getLocalizedMessage(
                   RESOURCE, "exception", null),
                                         JOptionPane.INFORMATION_MESSAGE);
           return "有异常了：）";
      }
       return text;
    }
    
    
    public void isShowRoute(boolean flag)
    {
      this.showRoute = flag;
    } 
    
//  CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准  
}
