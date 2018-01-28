/**
 * 生成程序ConfigMessageJDialog.java    1.0    2012-2-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ConfigMessageJDialog extends JDialog
{
    private static final long serialVersionUID = 1L;

    private JPanel jPanel1 = new JPanel();

    private JButton okJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    //private JLabel label1 = new JLabel();
    private JTextArea mesTextArea = new JTextArea();
    private JScrollPane jScrollPane1 = new JScrollPane();
    //父界面
    private JDialog dialog;
    
    /**
     * @roseuid 4031A77200CE
     */
    public ConfigMessageJDialog()
    {
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @roseuid 4031A77200CE
     */
    public ConfigMessageJDialog(JDialog dialog)
    {
        super(dialog);
        dialog = dialog;
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 初始化
     */
    private void jbInit()
    {
        this.setTitle("不符合配置规范的零部件");
        this.setSize(300, 250);
        this.setModal(true);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setLayout(gridBagLayout1);
        mesTextArea.setEditable(false);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new ConfigMessageJDialog_okJButton_actionAdapter(this));
        this.getContentPane().add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 8, 0, 8), 0, 0));
        jScrollPane1.getViewport().add(mesTextArea, null);
        this.getContentPane().add(okJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

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
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }
    
    public void setTextArea(String message)
    {
        mesTextArea.setText(message);
    }
    /**
     * <p>Title:确定按钮适配器</p> <p>Description: </p>
     */
    class ConfigMessageJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
    {
        private ConfigMessageJDialog adaptee;

        ConfigMessageJDialog_okJButton_actionAdapter(ConfigMessageJDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.okJButton_actionPerformed(e);
        }
    }
    
    /**
     * 执行确定操作
     * @param e ActionEvent
     * 20120118 徐春英  modify
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
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
        ConfigMessageJDialog d = new ConfigMessageJDialog();
        d.setVisible(true);
    }
}
