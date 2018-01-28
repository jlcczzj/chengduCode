package com.faw_qm.cappclients.capp.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.faw_qm.capp.model.*;
import java.util.Hashtable;

/**
 * tangshutao add for qingqi 2007.08.14
 * <p>Title: Ϳװ���һ����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������˾</p>
 * @author ������
 * @version 1.0
 */
public class PaintPartListJFrame
    extends JFrame {
  //����ֵ����
  private QMFawTechnicsInfo techInfo;

  //����panel����ֵΪ�������࣬�������Ϳװ����
  private Hashtable partLinkTable = new Hashtable();

  //����ʹ������������
  private PartUsageTechLinkJPanel partTechLinkJPanel;
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton exitButton = new JButton();

  /**
   *
   * @param info QMFawTechnicsInfo
   */
  public PaintPartListJFrame(QMFawTechnicsInfo info) {
    techInfo = info;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * ȱʡ���캯��
   */
  public PaintPartListJFrame() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public PartUsageTechLinkJPanel getPartTechLinkJPanel() {
    return partTechLinkJPanel;
  }

  /**
   * ���ý������ʾλ��
   */
  private void setViewLocation() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation( (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

  }

  /**
   * ���ظ��෽��
   * @param flag
   */
  public void setVisible(boolean flag) {
    setViewLocation();
    super.setVisible(flag);
  }

  /**
   * �����ʼ��
   * @throws Exception
   */
  private void jbInit() throws Exception {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    this.setTitle("Ϳװ���һ����(�鿴)");
    setIconImage(new ImageIcon(getClass().getResource(
        "/images/technics.gif")).getImage());

    partTechLinkJPanel = (PartUsageTechLinkJPanel) partLinkTable.get(
        "Ϳװ����");
    if (partTechLinkJPanel == null) {
      partTechLinkJPanel = new PartUsageTechLinkJPanel("Ϳװ����");
      partLinkTable.put("Ϳװ����", partTechLinkJPanel);
    }
    partTechLinkJPanel.setTechnics(techInfo);
    int[] i = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    partTechLinkJPanel.getCappAssociationsPanel().setMutliSelectedModel(true);
    partTechLinkJPanel.getCappAssociationsPanel().setColsEnabled(i, false);
    this.getContentPane().setLayout(gridBagLayout1);
    this.setResizable(true);
    jPanel1.setLayout(gridBagLayout2);
    jPanel2.setLayout(gridBagLayout3);
    exitButton.setText("�˳�");
    exitButton.addActionListener(new
                                 PaintPartListJFrame_exitButton_actionAdapter(this));
    this.getContentPane().add(jPanel1,
                              new GridBagConstraints(0, 0, 1, 1, 1.0, 2.5
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(1, 1, 1, 1), 0, 0));
    jPanel1.add(partTechLinkJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(8, 8, 8, 8), 0, 0));
    this.getContentPane().add(jPanel2,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 0.2
        , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
        new Insets(0, 1, 2, 0), 0, 0));
    jPanel2.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
    setCursor(Cursor.getDefaultCursor());
  }
  /**
   * �˳���ť����
   * @param e ActionEvent
   */
  void exitButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }
}


class PaintPartListJFrame_exitButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintPartListJFrame adaptee;

  PaintPartListJFrame_exitButton_actionAdapter(PaintPartListJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exitButton_actionPerformed(e);
  }
}


