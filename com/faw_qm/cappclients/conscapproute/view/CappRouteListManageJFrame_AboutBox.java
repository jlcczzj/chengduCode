/**
 * 生成程序 CappRouteListManageJFrame_AboutBox.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.clients.awt.HorizontalLine;
import com.faw_qm.framework.exceptions.QMException;

/**
 * <p> Title:工艺路线表管理器的"关于"界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class CappRouteListManageJFrame_AboutBox extends JDialog
{

    static protected ImageIcon personIcon;

    private static String RESOURCE = "com.faw_qm.cappclients.capp.util.CappLMRB";

    private JLabel jLabel1 = new JLabel();

    private JLabel jLabel2 = new JLabel();

    private JLabel jLabel3 = new JLabel();

    private JLabel jLabel4 = new JLabel();

    private Border border1;

    private Border border2;

    private Border border3;

    private JPanel labelJPanel = new JPanel();

    private JLabel iconJLabel = new JLabel();

    private JLabel internetJLabel = new JLabel();

    private JLabel jLabel6 = new JLabel();

    private JLabel jLabel7 = new JLabel();

    private JButton okJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    /**
     * 构造函数
     * @param parent 父窗口
     */
    public CappRouteListManageJFrame_AboutBox(Frame parent)
    {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 界面组件初始化
     * @throws PropertyVetoException 
     * @throws PropertyVetoException 
     * @throws Exception
     */
    private void jbInit() throws QMException, PropertyVetoException
    {
        border1 = BorderFactory.createEmptyBorder();
        border2 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));
        border3 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));
        okJButton.setBounds(new Rectangle(281, 253, 65, 23));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setRequestFocusEnabled(false);
        okJButton.setFocusPainted(false);
        okJButton.setMnemonic('Y');
        this.setResizable(false);
        this.setSize(400, 300);
        this.getContentPane().setLayout(gridBagLayout2);

        ImageIcon personIcon = new ImageIcon(this.getClass().getResource("/images/product.gif"));
        iconJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        iconJLabel.setIcon(personIcon);

        HorizontalLine horizontalLine = new HorizontalLine();
        horizontalLine.setFillMode(true);
        horizontalLine.setBevelStyle(0);

        labelJPanel.setLayout(gridBagLayout1);

        internetJLabel.setForeground(Color.blue);
        internetJLabel.setToolTipText(" www.faw-qm.com.cn");
        internetJLabel.setText("  www.faw-qm.com.cn");
        internetJLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                internetJLabel_mouseEntered(e);
            }

            public void mouseClicked(MouseEvent e)
            {
                internetJLabel_mouseClicked(e);
            }

            public void mouseExited(MouseEvent e)
            {
                internetJLabel_mouseExited(e);
            }
        });
        jLabel6.setText("网        址：");

        jLabel7.setBackground(Color.blue);
        jLabel7.setForeground(Color.blue);
        jLabel7.setMaximumSize(new Dimension(130, 2));
        jLabel7.setMinimumSize(new Dimension(130, 2));
        jLabel7.setPreferredSize(new Dimension(130, 2));
        jLabel7.setText("———————————————");
        labelJPanel.add(jLabel3, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        labelJPanel.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        labelJPanel.add(jLabel2, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        labelJPanel.add(jLabel4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 5, 0, 0), 0, 0));
        labelJPanel.add(internetJLabel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(7, 0, 0, 0), 0, 0));
        labelJPanel.add(jLabel6, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        this.getContentPane().add(horizontalLine,
                new GridBagConstraints(0, 2, 1, GridBagConstraints.REMAINDER, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 80, 0, 54), 0, 0));
        labelJPanel.add(jLabel7, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(-3, 5, 0, 0), 0, 0));
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("工艺路线管理器");
        jLabel2.setToolTipText("");
        jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel2.setText("版        本：  1.0");
        jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel3.setText("版权所有：  长春一汽启明信息技术有限公司");
        jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel4.setText("( C )  2004 ");

        this.getContentPane().add(iconJLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(labelJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 54), 0, 0));
        this.getContentPane().add(okJButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 54), 0, 0));

        localize();
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e)
    {
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            cancel();
        }
        super.processWindowEvent(e);
    }

    //Close the dialog
    void cancel()
    {
        dispose();
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
     * 本地化
     */
    public void localize()
    {
        this.setTitle("关于工艺路线管理器");
        okJButton.setText("确定(Y)");
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }catch(InstantiationException ex)
        {
            ex.printStackTrace();
        }catch(IllegalAccessException ex)
        {
            ex.printStackTrace();
        }catch(UnsupportedLookAndFeelException ex)
        {
            ex.printStackTrace();
        }
        //CappRouteListManageJFrame_AboutBox d = new
        // CappRouteListManageJFrame_AboutBox(new Frame());
        //d.setVisible(true);

    }

    /**
     * 鼠标进入监听事件方法
     * @param e MouseEvent
     */
    void internetJLabel_mouseEntered(MouseEvent e)
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * 鼠标单击监听事件方法
     * @param e MouseEvent
     */
    void internetJLabel_mouseClicked(MouseEvent e)
    {
        String urlString = "www.faw-qm.com.cn";
        try
        {
            Runtime.getRuntime().exec("C:/Program Files/Internet Explorer/IEXPLORE.EXE " + urlString);
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 鼠标离开监听事件方法
     * @param e MouseEvent
     */
    void internetJLabel_mouseExited(MouseEvent e)
    {
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 执行确定操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        dispose();
    }
}
