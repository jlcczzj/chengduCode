/** 
 * ���ɳ��� ReportFormsJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.technics.route.model.*;

/**
 * <p>
 * Title:���ɱ�������
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * 
 * @author ����
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

    /** ��������� */
    private ReportFormsController controller;

    /** ҵ�����:·�߱� */
    private TechnicsRouteListInfo routeList;
    
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"����չ��"
    JRadioButton jRadioButton1 = new JRadioButton();
    
    private String routeCompleteOrListId = "";
    //CCEndnby leixiao 2008-8-4 ԭ�򣺽����������·��,����"����չ��"

    /**
     * ���캯��
     * 
     * @param frame
     *            ������
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
    
    //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��

    public ReportFormsJDialog(JDialog dialog) {
      super(dialog, "", true);
      try {
        jbInit();
      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }
    //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��

    /**
     * ���������ʼ��
     * 
     * @throws Exception
     */
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        this.setTitle("���Ʊ���");
        this.setSize(400, 300);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setBorder(titledBorder1);
        jPanel1.setLayout(gridLayout1);
        jPanel2.setBorder(titledBorder2);
        jPanel2.setLayout(gridLayout2);
        jPanel3.setLayout(gridBagLayout1);
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setText("�����������");
        statusJLabel.setMaximumSize(new Dimension(65, 22));
        statusJLabel.setMinimumSize(new Dimension(65, 22));
        statusJLabel.setPreferredSize(new Dimension(65, 22));
        displayJRadioButton.setActionCommand("Display");
        displayJRadioButton.setSelected(true);
        displayJRadioButton.setText("��ʾ");
        displayJRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            displayJRadioButton_mouseEntered(e);
          }
        });
        localJRadioButton.setActionCommand("ToLocal");
        localJRadioButton.setText("�����ļ�");
        localJRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            localJRadioButton_mouseEntered(e);
          }
        });
        jRadioButton3.setEnabled(false);
        jRadioButton3.setActionCommand("TXT");
        jRadioButton3.setText("�ı���ʽ");
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            jRadioButton3_mouseEntered(e);
          }
        });
        //��ʱû��ʵ�֣����ذ�ť
        jRadioButton3.setVisible(false);

        jRadioButton4.setEnabled(false);
        jRadioButton4.setActionCommand("Excel");
        jRadioButton4.setText("Excel��ʽ");
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            jRadioButton4_mouseEntered(e);
          }
        });
        //��ʱû��ʵ�֣����ذ�ť
        jRadioButton5.setVisible(false);
        jRadioButton5.setEnabled(false);
        jRadioButton5.setActionCommand("XML");
        jRadioButton5.setText("XML��ʽ");
        jRadioButton5.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            jRadioButton5_mouseEntered(e);
          }
        });
        titledBorder1.setTitle("�����ʽ");
        titledBorder2.setTitle("�����ʽ");
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        helpJButton.setMaximumSize(new Dimension(75, 23));
        helpJButton.setMinimumSize(new Dimension(75, 23));
        helpJButton.setPreferredSize(new Dimension(75, 23));
        helpJButton.setToolTipText("Help");
        helpJButton.setActionCommand("HELP");
        helpJButton.setMnemonic('H');
        helpJButton.setText("����(H)");
        jRadioButton1.setText("����չ��");
        jRadioButton1.setSelected(true);
        jPanel1.add(displayJRadioButton, null);
        jPanel1.add(localJRadioButton, null);
        jPanel1.add(jRadioButton1, null);
        buttonGroup1.add(displayJRadioButton);
        buttonGroup1.add(localJRadioButton);
        this.getContentPane().add(jPanel2,
                                  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 10, 0, 10), 0, 0));

        jPanel2.add(jRadioButton3, null);
        jPanel2.add(jRadioButton4, null);
        jPanel2.add(jRadioButton5, null);
        buttonGroup2.add(jRadioButton3);
        buttonGroup2.add(jRadioButton4);
        buttonGroup2.add(jRadioButton5);
        this.getContentPane().add(jPanel1,
                                  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 10, 10), 0, 0));
        this.getContentPane().add(jPanel3,
                                  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            , GridBagConstraints.EAST, GridBagConstraints.NONE,
            new Insets(20, 0, 20, 12), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                      , GridBagConstraints.CENTER,
                                                      GridBagConstraints.NONE,
                                                      new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            , GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 8, 0, 0), 0, 0));
        this.getContentPane().add(statusJLabel,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            , GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(helpJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        //{{ע�����
        controller = new ReportFormsController(this);
        displayJRadioButton.addActionListener(controller);
        localJRadioButton.addActionListener(controller);
        jRadioButton3.addActionListener(controller);
        jRadioButton4.addActionListener(controller);
        jRadioButton5.addActionListener(controller);
        okJButton.addActionListener(controller);
        cancelJButton.addActionListener(controller);
        //CCBegin by leixiao 2009��������������������
        helpJButton.addActionListener(controller);
       //CCEnd by leixiao 2009��������������������
        //}}
      }
    
    public boolean getIsExpandByProduct() {
    	  return jRadioButton1.isSelected();
    	}
    
    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��

    /**
     * ���ҵ�����
     * 
     * @return ·�߱�
     */
    public TechnicsRouteListInfo getRouteList() {
        return routeList;
    }

    /**
     * ����ָ����ҵ�����
     * 
     * @param routeList
     *            ·�߱�
     */
    public void setRouteList(TechnicsRouteListInfo routeList) {
        this.routeList = routeList;
    }

    /**
     * ���½������ʾ״̬
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
//        	CCBegin by leixiao 2008-11-5 ԭ�򣺽����������·�ߣ�Ĭ����������ļ�Ϊexcel,��ѡ��      	
//          jRadioButton3.setSelected(true);
//          controller.setFormat(".csv");        	
          jRadioButton4.setSelected(true);
          controller.setFormat("Excel");
      } else {
          jRadioButton4.setSelected(false);
//          CCEnd by leixiao 2008-11-5 ԭ�򣺽����������·��             

        }
    }
    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��
    
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"��·��չ��"
    private void setRouteCompleteOrListId(String routeCompleteOrListId) {
        this.routeCompleteOrListId = routeCompleteOrListId;
       // System.out.println("args3 = "+routeCompleteOrListId);
      }
    
    public String getRouteCompleteOrListId() {
        return routeCompleteOrListId;
      }
    
    //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"��·��չ��"



    //Main method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ReportFormsJDialog d = new ReportFormsJDialog((JFrame)null);
        d.setVisible(true);
    }

    void displayJRadioButton_mouseEntered(MouseEvent e) {
        statusJLabel.setText("��WEBҳ��ʽ��ʾ��������");
    }

    void localJRadioButton_mouseEntered(MouseEvent e) {
        statusJLabel.setText("��������ش��̣������ѡ�������ʽ��");
    }

    void jRadioButton3_mouseEntered(MouseEvent e) {
        statusJLabel.setText("��.CSV�Ľ����ļ���ʽ�����");
    }

    void jRadioButton4_mouseEntered(MouseEvent e) {
        statusJLabel.setText("��Excel���ݱ��ĸ�ʽ�����");
    }

    void jRadioButton5_mouseEntered(MouseEvent e) {
        statusJLabel.setText("��XML�ļ���ʽ�����");
    }

    /**
     * ���ý������ʾλ��
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
     * ���ظ��෽����ʹ������ʾ����Ļ����
     * 
     * @param flag
     */
    public void setVisible(boolean flag) {
        this.setViewLocation();
        super.setVisible(flag);
    }
}