/** 
 * 生成程序 RouteGraphViewJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.faw_qm.cappclients.conscapproute.controller.CappRouteAction;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;

/**
 * <p> Title:查看工艺路线图界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class RouteGraphViewJDialog extends JDialog
{
    /** 路线图板 */
    private GraphPanel graphPanel;

    private JButton quitJButton = new JButton();

    private JScrollPane jScrollPane1 = new JScrollPane();

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 父窗口 */
    private Frame parentFrame;

    /** 当前工艺路线 */
    private TechnicsRouteIfc myRoute;

    /** 资源文件路径 */
    public static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    private JPanel jPanel1 = new JPanel();

    //private JButton helpJButton = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    DefaultGraphModel model = new DefaultGraphModel();
    
    /**
     * 构造函数
     * @param frame 父窗口
     */
    public RouteGraphViewJDialog(Frame frame)
    {
        super(frame, "", true);
        parentFrame = frame;
        //setTechnicsRoute(route);
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit()
    {
        graphPanel = new GraphPanel(parentFrame, false, this);
        this.getContentPane().setLayout(gridBagLayout3);
        this.setBackground(Color.white);
        this.setSize(550, 450);
        this.setTitle("查看路线图");
        quitJButton.setMaximumSize(new Dimension(75, 23));
        quitJButton.setMinimumSize(new Dimension(75, 23));
        quitJButton.setPreferredSize(new Dimension(75, 23));
        quitJButton.setToolTipText("Quit");
        quitJButton.setMnemonic('Q');
        quitJButton.setText("退出");

        quitJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
        jPanel1.setLayout(gridBagLayout1);
        //helpJButton.setMaximumSize(new Dimension(65, 23));
        //helpJButton.setMinimumSize(new Dimension(65, 23));
        //helpJButton.setPreferredSize(new Dimension(65, 23));
        //helpJButton.setToolTipText("Help");
        //helpJButton.setText("帮助");

        this.getContentPane().add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
        this.getContentPane().add(jPanel1,
                new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 0, 20, 10), 0, 0));
        jPanel1.add(quitJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        //jPanel1.add(helpJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        //       ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8,
        // 0, 0), 0, 0));
        jScrollPane1.getViewport().add(graphPanel, null);
        localize();
        //helpJButton.addActionListener(new
        // RouteGraphViewJDialog_helpJButton_actionAdapter(this));
    }

    /**
     * 本地化
     */
    private void localize()
    {
        try
        {
            this.setTitle(QMMessage.getLocalizedMessage(RESOURCE, "viewGraphTitle", null));
            quitJButton.setText("退出(Q)");
            //helpJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,"helpJButton",null));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 设置当前编辑的工艺路线对象
     * @param route 工艺路线对象
     */
    public void setTechnicsRoute(TechnicsRouteIfc route)
    {
        myRoute = route;
        graphPanel.setTechnicsRoute(route);
        try
        {
            setGraphData();
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentFrame(), message);
        }
    }

    /**
     * 获得当前编辑的工艺路线对象
     * @return TechnicsRouteIfc 当前编辑的工艺路线对象
     */
    public TechnicsRouteIfc getTechnicsRoute()
    {
        return myRoute;
    }

    /**
     * 获得并设置显示路线图的数据
     * @throws QMRemoteException
     */
    private void setGraphData()
    {
        if(myRoute == null || myRoute.getBsoID() == null)
            return;
        try
        {
            //调用服务,根据路线对象获得路线节点和连接
            Class[] c = {String.class};
            Object[] obj = {myRoute.getBsoID()};
            Object[] map;
            map = (Object[])RequestHelper.request("consTechnicsRouteService", "getRouteNodeAndNodeLink", c, obj);

            Vector nodeVector = (Vector)map[0];
            Vector linkVector = (Vector)map[1];
//            DefaultGraphModel model = new DefaultGraphModel();
            //缓存所有的节点图元
            Vector gnv = new Vector();
            if(nodeVector != null && nodeVector.size() > 0)
            {
                for(int i = 0;i < nodeVector.size();i++)
                {
                    RouteNodeInfo nodeinfo = (RouteNodeInfo)nodeVector.elementAt(i);
                    RouteItem nodeitem = new RouteItem(nodeinfo);
                    DefaultGraphNode newnode = new DefaultGraphNode();
                    newnode.setRouteItem(nodeitem);
                    newnode.setDepartmentName(nodeinfo.getNodeDepartmentName());
                    newnode.setPosition(new Long(nodeinfo.getX()).intValue(), new Long(nodeinfo.getY()).intValue());
                    newnode.updateImage();
                    model.graphNodeVector.addElement(newnode);
                    gnv.addElement(newnode);
                }
            }

            if(linkVector != null && linkVector.size() > 0)
            {
                String leftNodeID, rightNodeID, curBsoID;
                for(int j = 0;j < linkVector.size();j++)
                {
                    RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo)linkVector.elementAt(j);
                    RouteItem linkitem = new RouteItem(linkinfo);
                    DefaultGraphLink newlink = new DefaultGraphLink();
                    newlink.setRouteItem(linkitem);
                    leftNodeID = linkinfo.getSourceNode().getBsoID();
                    rightNodeID = linkinfo.getDestinationNode().getBsoID();
                    for(int k = 0;k < gnv.size();k++)
                    {
                        DefaultGraphNode graphNode = (DefaultGraphNode)gnv.elementAt(k);
                        curBsoID = graphNode.getRouteItem().getObject().getBsoID();
                        if(leftNodeID.equals(curBsoID))
                        {
                            newlink.setInitPredecessor(graphNode);
                        }
                        if(rightNodeID.equals(curBsoID))
                        {
                            newlink.setInitSuccessor(graphNode);
                        }
                    }

                    model.graphLinkVector.addElement(newlink);
                }
            }

            graphPanel.setModel(model);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentFrame(), message);
            ex.printStackTrace();
        }
    }

    /**
     * 获得父窗口
     * @return 父窗口
     */
    public Frame getParentFrame()
    {
        return parentFrame;
    }

    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag 设置Visible
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * 退出操作
     * @param e ActionEvent java.awt.event.ActionEvent
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
    	model.clearCache();
        this.setVisible(false);
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        RouteGraphViewJDialog d = new RouteGraphViewJDialog(null);
        d.setVisible(true);
    }

    /**
     * 连接到Web帮助页"编辑路线图"
     * @param e
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {

    }

}

/**
 * 帮助按钮 适配器
 */
class RouteGraphViewJDialog_helpJButton_actionAdapter implements java.awt.event.ActionListener
{
    //查看工艺路线图界面 
    private RouteGraphViewJDialog adaptee;

    //构造函数
    RouteGraphViewJDialog_helpJButton_actionAdapter(RouteGraphViewJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //Invoked when an action occurs.
    public void actionPerformed(ActionEvent e)
    {
        adaptee.helpJButton_actionPerformed(e);
    }
}