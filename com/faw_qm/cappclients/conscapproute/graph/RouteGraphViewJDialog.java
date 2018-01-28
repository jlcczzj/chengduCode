/** 
 * ���ɳ��� RouteGraphViewJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title:�鿴����·��ͼ���� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class RouteGraphViewJDialog extends JDialog
{
    /** ·��ͼ�� */
    private GraphPanel graphPanel;

    private JButton quitJButton = new JButton();

    private JScrollPane jScrollPane1 = new JScrollPane();

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ������ */
    private Frame parentFrame;

    /** ��ǰ����·�� */
    private TechnicsRouteIfc myRoute;

    /** ��Դ�ļ�·�� */
    public static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    private JPanel jPanel1 = new JPanel();

    //private JButton helpJButton = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    DefaultGraphModel model = new DefaultGraphModel();
    
    /**
     * ���캯��
     * @param frame ������
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
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        graphPanel = new GraphPanel(parentFrame, false, this);
        this.getContentPane().setLayout(gridBagLayout3);
        this.setBackground(Color.white);
        this.setSize(550, 450);
        this.setTitle("�鿴·��ͼ");
        quitJButton.setMaximumSize(new Dimension(75, 23));
        quitJButton.setMinimumSize(new Dimension(75, 23));
        quitJButton.setPreferredSize(new Dimension(75, 23));
        quitJButton.setToolTipText("Quit");
        quitJButton.setMnemonic('Q');
        quitJButton.setText("�˳�");

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
        //helpJButton.setText("����");

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
     * ���ػ�
     */
    private void localize()
    {
        try
        {
            this.setTitle(QMMessage.getLocalizedMessage(RESOURCE, "viewGraphTitle", null));
            quitJButton.setText("�˳�(Q)");
            //helpJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,"helpJButton",null));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ���õ�ǰ�༭�Ĺ���·�߶���
     * @param route ����·�߶���
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
     * ��õ�ǰ�༭�Ĺ���·�߶���
     * @return TechnicsRouteIfc ��ǰ�༭�Ĺ���·�߶���
     */
    public TechnicsRouteIfc getTechnicsRoute()
    {
        return myRoute;
    }

    /**
     * ��ò�������ʾ·��ͼ������
     * @throws QMRemoteException
     */
    private void setGraphData()
    {
        if(myRoute == null || myRoute.getBsoID() == null)
            return;
        try
        {
            //���÷���,����·�߶�����·�߽ڵ������
            Class[] c = {String.class};
            Object[] obj = {myRoute.getBsoID()};
            Object[] map;
            map = (Object[])RequestHelper.request("consTechnicsRouteService", "getRouteNodeAndNodeLink", c, obj);

            Vector nodeVector = (Vector)map[0];
            Vector linkVector = (Vector)map[1];
//            DefaultGraphModel model = new DefaultGraphModel();
            //�������еĽڵ�ͼԪ
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
     * ��ø�����
     * @return ������
     */
    public Frame getParentFrame()
    {
        return parentFrame;
    }

    /**
     * ���ý������ʾλ��
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
     * ���ظ��෽����ʹ������ʾ����Ļ����
     * @param flag ����Visible
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * �˳�����
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
     * ���ӵ�Web����ҳ"�༭·��ͼ"
     * @param e
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {

    }

}

/**
 * ������ť ������
 */
class RouteGraphViewJDialog_helpJButton_actionAdapter implements java.awt.event.ActionListener
{
    //�鿴����·��ͼ���� 
    private RouteGraphViewJDialog adaptee;

    //���캯��
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