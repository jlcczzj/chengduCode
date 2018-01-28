/**
 * ���ɳ��� PartTreePanel.java    1.0    2003/09/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 *CR1  2009/06/08 ������  �μ�:������:v4r3FunctionTest;TD��2286
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
 * <p>Title: �㲿����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: faw_qm</p>
 * @author Ѧ��
 * @version 1.0
 */

public class PartTreePanel extends JPanel
{
    /**
     * ��
     */
    private QMTree partTree = new QMTree();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**���ù���*/
    private ConfigSpecItem configSpecItem = null;


    /**
     * ��
     */
    protected Object lock2 = new Object();


    /**
     * ��
     */
    private Object lock = new Object();
    private Vector changeConfigListeners;


    /**���ڱ����Դ�ļ�·��*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private JFrame parentFrame;
    
//  CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 
    private boolean showRoute = false;
//  CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 
    
  public Vector getchangeConfig(){              //CR1
	return changeConfigListeners;
  }

    /**
     * ���췽��
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
     * ��ʼ������
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
     * �����ѡ��ʱ���¼�,���Ӽ���������
     * @param e TreeSelectionEvent �¼�
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
     * �������Ҽ�����
     * @param e
     */
    public void addMouseListener(MouseListener e)
    {
        partTree.addMouseListener(e);
    }


    /**
     * �������ѡ�нڵ�
     * @return QMNode
     */
    public QMNode getSelected()
    {
        return partTree.getSelected();
    }


    /**
     * ����������еýڵ�
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
     * �õ���
     * @return QMTree ��
     */
    public QMTree getPartTree()
    {
        return partTree;
    }


    /**
     * ����PartItem�õ����ϵĽڵ�
     * @param partItem PartItem ���
     * @return QMNode[] ���ϵĽڵ�
     */
    public QMNode[] findNodes(PartItem partItem)
    {
        return partTree.findNodes(partItem);
    }


    /**
     * ���ڵ��������
     * @param node QMNode ���ڵ�
     * @param pi PartItem �����
     * @return QMNode �ӽڵ�
     */
    public QMNode addNode(QMNode node, PartItem pi)
    {
        return partTree.addNode(node, pi);
    }


    /**
     * չ����i��
     * @param i int ��
     */
    public void expandRow(int i)
    {
        partTree.expandRow(i);
    }


    /**
     * ��ø��ڵ�
     * @return QMNode ���ڵ�
     */
    public QMNode getRoot()
    {
        return partTree.getRoot();
    }


    /**
     * ɾ���ڵ�
     * @param node QMNode Ҫɾ���Ľڵ�
     * @return QMNode ɾ���Ľڵ�
     */
    public QMNode removeNode(QMNode node)
    {
        return partTree.removeNode(node);
    }


    /**
     * ���ڵ��������
     * @param qmnode QMNode ���ڵ�
     * @param qmnode1 QMNode �ӽڵ�
     * @return QMNode �ӽڵ�
     */
    public QMNode addNode(QMNode qmnode, QMNode qmnode1)
    {
        return partTree.addNode(qmnode, qmnode1);

    }


    /**
     *���ݽӿڱ�������Ϣ����qmnode�Ϲ��� qmnode1(��Ϣ���Խӿ�)������qmnode1
     * @param aParent ���ڵ�
     * @param aChildren �ӽڵ�
     * @return QMNode
     */
//  CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼    
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
//CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 


    /**
     * �������ù淶
     * @param configSpecItem ConfigSpecItem
     */
    public void setConfigSpecItem(ConfigSpecItem configSpecItem)
    {
        this.configSpecItem = configSpecItem;
    }


    /**
     * ������ù淶
     * @return ConfigSpecItem
     */
    public ConfigSpecItem getConfigSpecItem()
    {
        return configSpecItem;
    }


    /**
     * ���ݵ�ǰ���õ�ɸѡ����ˢ����
     * �˷����� StructConditionController����
     */
    public void processNewConfigSpec()
    {
        //2003/09/22 lgz
        synchronized (lock)
        {
            //��ø��ڵ�
            QMNode root = getRoot();
            Vector children = new Vector();
            //����ӽڵ㣬������һ���ڵ�Ķ�����ӵ�children��
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
                //����� masterֱ�Ӽ��뵽����
                if (children.elementAt(i) instanceof QMPartMasterInfo)
                {
                    masters[i] = (QMPartMasterInfo) children.elementAt(i);
                 
                }
                //�����part�����master��������
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
     * �����㲿�����ڹ���������ʾ��������ڸ��ڵ���.
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

            //����PartItem����
            PartItem pi = new PartItem(aPart);
//          CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 
            if(this.showRoute)
            {
               String routeText = getRouteText(aPart);
               pi.setRouteTest(routeText);
            }
//          CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��  
            if (configSpecItem == null)
            {
                setConfigSpecCommand();
            }
            //��õ�ǰ���ù淶
            ConfigSpecItem specItem = configSpecItem;
            if (specItem != null)
            {
                //�ѵ�ǰ���ù淶����partItem��
                pi.setConfigSpecItem(specItem);
                // ��ø��ڵ�:���
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
     * �������������
     * @param partMasters QMPartMasterInfo[] �������
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
            //������ù淶Ϊ��
            if (configSpecItem == null)
            {
                setConfigSpecCommand();
            }
            Hashtable table = null;
            try
            {
                //���PART����
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
                //���part����
                ArrayList part = (ArrayList) table.get("part");
                //���master����
                ArrayList partMaster = (ArrayList) table.get("partmaster");
             
                //���û�в����ϵ�PartMaster
                if (partMaster.size() == 0)
                {
                    for (int i = 0; i < part.size(); i++)
                    {
                        addPart((QMPartInfo) part.get(i));
                    }
                    //jMenuConfigCrit.setEnabled(true);
                }
                //����в����ϵ�PartMaster
                else
                {
                    //��ÿһ��partִ��addExplorerPart()��ӵ�����
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
     * ����֪�ڵ���ӽڵ��������
     * @param qmnode QMNode ��֪�ڵ�
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
            qmnode1.setChildrenCache(true); //����Ϊ��
            //if ( !isRefreshContentsOnExpose () )
            qmnode1.setDetailsCache(true); //����Ϊ��
            Explorable explorable = qmnode1.getObj();
            if (explorable != null)
            { //node����ִ��
                if (!flag2)
                { //ִ��
                    //�õ��������ʹ�õĶ�������:Ԫ��ΪusegeItem����useageMasterItem
                    aexplorable = explorable.getUses();
                    for (int i = 0; i < aexplorable.length; i++)
                    {
                        //if ( isDisplayUsesAsContents () )
                        qmnode1.addDetail(aexplorable[i]);
                        //���ӽڵ���Ϣ������ʱ�ڵ�
                        addNode(qmnode1, aexplorable[i]);
                    }
                    //���û���ֽڵ���Ϣ
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
                { //ִ��
                    //if ( isRefreshContentsOnExpose () ) //��ִ��
                    qmnode1.clearDetail();
                    //�ж�ִ�У���׼��ִ��
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
                    { //ִ��
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
                                qmnode1.addDetail(aexplorable1[k]); //��ϸ��д��
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
     * ���õ�ǰ��ɸѡ����.
     * @param refresh = true :�������õ�ɸѡ����ˢ�¹�����ҳ���ڵ��㲿��
     *                =false :��ˢ�¹�����ҳ���ڵ��㲿��
     */
    public void setConfigSpecCommand()
    {
        try
        {
            //��ȡ�㲿�����ù淶
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper.
                                                getConfigSpec();
            ViewObjectIfc vo = null;
            ConfigSpecItem config = null;
            //���û�����ù淶������Ĭ�ϵġ���׼�����ù淶��
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
                            "������ͼ"))
                    {
                        vo = (ViewObjectIfc) dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                //��׼������
                PartStandardConfigSpec partStandardConfigSpec = new
                        PartStandardConfigSpec();
                //���ñ�׼ʱ����ͼ
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //���÷������úõ�ɸѡ�������浽���ݿ���
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
     * ���ù���
     */
    public void configureCriterion()
    {
        setConfigSpecCommand();
        //��ʾ���༭�ṹɸѡ������ҳ�沢�����ݿ��д��ڵ�ɸѡ����������ҳ�档
        ConfigSpecController c = new ConfigSpecController(configSpecItem, this,parentFrame);
    }


    /**
     * ������ù淶�ı������
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
     * ֪ͨ���ù淶�ı������
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
    
//  CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 
    
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
           return "���쳣�ˣ���";
      }
       return text;
    }
    
    
    public void isShowRoute(boolean flag)
    {
      this.showRoute = flag;
    } 
    
//  CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼  
}
