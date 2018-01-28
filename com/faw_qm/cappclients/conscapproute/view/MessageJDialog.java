/**
 * ���ɳ���MessageJDialog.java    1.0    2012-2-10
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.faw_qm.clients.util.CheckboxMultiList;
import com.faw_qm.technics.consroute.util.RouteWrapData;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public class MessageJDialog extends JDialog
{
    private JPanel jPanel1 = new JPanel();

    private JButton okJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    //private JLabel label1 = new JLabel();
    private JTextArea mesTextArea = new JTextArea();
    private JScrollPane jScrollPane1 = new JScrollPane();
    //������
    private JFrame parentFrame;
    
    /**
     * @roseuid 4031A77200CE
     */
    public MessageJDialog()
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
    public MessageJDialog(JFrame frame)
    {
        super(frame);
        parentFrame = frame;
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * ��ʼ��
     */
    private void jbInit()
    {
        this.setTitle("������Ҫ���·�ߵ�λ");
        this.setSize(300, 250);
        this.setModal(true);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.addActionListener(new MessageJDialog_okJButton_actionAdapter(this));
        //statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        //label1.setText("�����ڵ�·�ߵ�λ");
        //jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        //jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        //this.getContentPane().add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 8, 0, 0), 0, 0));
        this.getContentPane().add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 8, 0, 8), 0, 0));
        jScrollPane1.getViewport().add(mesTextArea, null);
        this.getContentPane().add(okJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

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
     * <p>Title:ȷ����ť������</p> <p>Description: </p>
     */
    class MessageJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
    {
        private MessageJDialog adaptee;

        MessageJDialog_okJButton_actionAdapter(MessageJDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.okJButton_actionPerformed(e);
        }
    }
    
    /**
     * ִ��ȷ������
     * @param e ActionEvent
     * 20120118 �촺Ӣ  modify
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
        MessageJDialog d = new MessageJDialog();
        d.setVisible(true);
    }
}
