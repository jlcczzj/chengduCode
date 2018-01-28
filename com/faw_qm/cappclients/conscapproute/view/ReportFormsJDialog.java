/** 
 * 生成程序 ReportFormsJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.conscapproute.controller.*;
import com.faw_qm.clients.util.ScreenParameter;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.technics.consroute.model.*;

/**
 * <p>
 * Title:生成报表界面
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * 
 * @author 刘明
 * @version 1.0
 */
public class ReportFormsJDialog extends JDialog {
    private JPanel jPanel1 = new JPanel();

    private TitledBorder titledBorder1;

    private JPanel jPanel2 = new JPanel();

    private TitledBorder titledBorder2;

    private JPanel jPanel3 = new JPanel();

    private JLabel statusJLabel = new JLabel();

    private ButtonGroup buttonGroup1 = new ButtonGroup();

    private ButtonGroup buttonGroup2 = new ButtonGroup();

    private JRadioButton displayJRadioButton = new JRadioButton();

    private JRadioButton localJRadioButton = new JRadioButton();

    private JRadioButton jRadioButton3 = new JRadioButton();

    private JRadioButton jRadioButton4 = new JRadioButton();

    private JRadioButton jRadioButton5 = new JRadioButton();

    private GridLayout gridLayout1 = new GridLayout();

    private GridLayout gridLayout2 = new GridLayout();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private JButton helpJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    /** 控制类对象 */
    private ReportFormsController controller;

    /** 业务对象:路线表 */
    private TechnicsRouteListInfo routeList;
    //CCBegin by wanghonglian 2008-07-01
    /** 业务对象:路线表id 用于瘦客户界面导出报表  liun */
    private String routeCompleteOrListId = "";
    //CCEnd by wanghonglian 2008-07-01
    /**
     * 构造函数
     * 
     * @param frame
     *            父窗口
     * @roseuid 40316EE90308
     */
    public ReportFormsJDialog(Frame frame) {
        super(frame, "", true);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 界面组件初始化
     * 
     * @throws Exception
     */
    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        this.setTitle("定制报表");
        this.setSize(400, 300);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setBorder(titledBorder1);
        jPanel1.setLayout(gridLayout1);
        jPanel2.setBorder(titledBorder2);
        jPanel2.setLayout(gridLayout2);
        jPanel3.setLayout(gridBagLayout1);
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setText("定制输出报表");
        statusJLabel.setMaximumSize(new Dimension(65, 22));
        statusJLabel.setMinimumSize(new Dimension(65, 22));
        statusJLabel.setPreferredSize(new Dimension(65, 22));
        displayJRadioButton.setActionCommand("Display");
        displayJRadioButton.setSelected(true);
        displayJRadioButton.setText("显示");
        displayJRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                displayJRadioButton_mouseEntered(e);
            }
        });
        localJRadioButton.setActionCommand("ToLocal");
        localJRadioButton.setText("本地文件");
        localJRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                localJRadioButton_mouseEntered(e);
            }
        });
        jRadioButton3.setEnabled(false);
        jRadioButton3.setActionCommand("TXT");
        jRadioButton3.setText("文本格式");
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jRadioButton3_mouseEntered(e);
            }
        });
        //暂时没有实现，隐藏按钮
        jRadioButton4.setVisible(false);
        jRadioButton4.setEnabled(false);
        jRadioButton4.setActionCommand("Excel");
        jRadioButton4.setText("Excel格式");
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jRadioButton4_mouseEntered(e);
            }
        });
        //暂时没有实现，隐藏按钮
        jRadioButton5.setVisible(false);
        jRadioButton5.setEnabled(false);
        jRadioButton5.setActionCommand("XML");
        jRadioButton5.setText("XML格式");
        jRadioButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jRadioButton5_mouseEntered(e);
            }
        });
        titledBorder1.setTitle("输出方式");
        titledBorder2.setTitle("输出格式");
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        helpJButton.setMaximumSize(new Dimension(75, 23));
        helpJButton.setMinimumSize(new Dimension(75, 23));
        helpJButton.setPreferredSize(new Dimension(75, 23));
        helpJButton.setToolTipText("Help");
        helpJButton.setActionCommand("HELP");
        helpJButton.setMnemonic('H');
        helpJButton.setText("帮助(H)");

        jPanel1.add(displayJRadioButton, null);
        jPanel1.add(localJRadioButton, null);
        buttonGroup1.add(displayJRadioButton);
        buttonGroup1.add(localJRadioButton);
        this.getContentPane().add(
                jPanel2,
                new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 10, 0, 10), 0, 0));
        jPanel2.add(jRadioButton3, null);
        jPanel2.add(jRadioButton4, null);
        jPanel2.add(jRadioButton5, null);
        buttonGroup2.add(jRadioButton3);
        buttonGroup2.add(jRadioButton4);
        buttonGroup2.add(jRadioButton5);
        this.getContentPane().add(
                jPanel1,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 10, 10, 10), 0, 0));
        this.getContentPane().add(
                jPanel3,
                new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(20, 0, 20, 12), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        jPanel3.add(helpJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        this.getContentPane().add(
                statusJLabel,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));

        //{{注册监听
        controller = new ReportFormsController(this);
        displayJRadioButton.addActionListener(controller);
        localJRadioButton.addActionListener(controller);
        jRadioButton3.addActionListener(controller);
        jRadioButton4.addActionListener(controller);
        jRadioButton5.addActionListener(controller);
        okJButton.addActionListener(controller);
        cancelJButton.addActionListener(controller);
        helpJButton.addActionListener(controller);
        //CCBegin by wanghonglian 2008-07-01
        helpJButton.setVisible(false);//liunan
        //CCEnd by wanghonglian 2008-07-01
        //}}
    }

    /**
     * 获得业务对象
     * 
     * @return 路线表
     */
    public TechnicsRouteListInfo getRouteList() {
        return routeList;
    }

    /**
     * 设置指定的业务对象
     * 
     * @param routeList
     *            路线表
     */
    public void setRouteList(TechnicsRouteListInfo routeList) {
        this.routeList = routeList;
    }

    /**
     * 更新界面的显示状态
     */
    public void updateRadioButtonEnable() {
        if (displayJRadioButton.isSelected()) {
            jRadioButton3.setEnabled(false);
            jRadioButton4.setEnabled(false);
            jRadioButton5.setEnabled(false);
        } else {
            jRadioButton3.setEnabled(true);
            jRadioButton4.setEnabled(true);
            jRadioButton5.setEnabled(true);
        }
        if (localJRadioButton.isSelected()) {
            jRadioButton3.setSelected(true);
            controller.setFormat(".csv");
        } else {
            jRadioButton3.setSelected(false);
        }
    }

    //Main method
    public static void main(String[] args) {
        try {
        	//CCBegin by wanghonglian 2008-07-01
            //modify by liunan 2007.10.23
            //ReportFormsJDialog d = new ReportFormsJDialog(null);        
            //ReportFormsJDialog d = new ReportFormsJDialog( (JFrame)null);
            if (args != null && args.length == 4) 
            {
              CappClientRequestServer server = new CappClientRequestServer(args[0],args[1],args[2]);
              RequestServerFactory.setRequestServer(server);
              System.setProperty("swing.useSystemFontSettings", "0");
              //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
              UIManager.setLookAndFeel(ScreenParameter.currentLookAndFeel);
          String listid = args[3];
              Class[] classes = {String.class};

        Object[] objs = {listid};
          TechnicsRouteListInfo listInfo= (TechnicsRouteListInfo) CappRouteAction.useServiceMethod(
              "PersistService",
              "refreshInfo", classes, objs);
              ReportFormsJDialog d = new ReportFormsJDialog( (JFrame)null);
              d.setRouteList(listInfo);
              
              d.setRouteCompleteOrListId(args[3]);
            d.setVisible(true);
            }
            //end adding
            //CCEnd by wanghonglian 2008-07-01
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    //CCBegin by wanghonglian 2008-07-01
    private void setRouteCompleteOrListId(String routeCompleteOrListId) 
    {
      this.routeCompleteOrListId = routeCompleteOrListId;
    }//liun
    
    public String getRouteCompleteOrListId() 
    {
      return routeCompleteOrListId;
    }//liun
    //CCEnd by wanghonglian 2008-07-01
    void displayJRadioButton_mouseEntered(MouseEvent e) {
        statusJLabel.setText("以WEB页形式显示输出结果。");
    }

    void localJRadioButton_mouseEntered(MouseEvent e) {
        statusJLabel.setText("输出到本地磁盘，请继续选择输出格式。");
    }

    void jRadioButton3_mouseEntered(MouseEvent e) {
        statusJLabel.setText("以.CSV的交换文件格式输出。");
    }

    void jRadioButton4_mouseEntered(MouseEvent e) {
        statusJLabel.setText("以Excel数据表的格式输出。");
    }

    void jRadioButton5_mouseEntered(MouseEvent e) {
        statusJLabel.setText("以XML文件格式输出。");
    }

    /**
     * 设置界面的显示位置
     */
    private void setViewLocation() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /**
     * 重载父类方法，使界面显示在屏幕中央
     * 
     * @param flag
     */
    public void setVisible(boolean flag) {
        this.setViewLocation();
        super.setVisible(flag);
    }
}