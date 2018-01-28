package com.faw_qm.cappclients.capp.view;

import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import com.faw_qm.cappclients.util.*;
import java.awt.event.*;
import com.faw_qm.part.model.*;
import java.util.Iterator;
import java.util.HashMap;

/**
 * <p>Title:��ʾ��׼֪ͨ����·���д��С�Ϳ���ֵ��㲿�����Ͻ���</p>
 * <p>Description: tangshutao add for qingqi 2007.09.13</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ����</p>
 * @author ������
 * @version 1.0
 */
public class PaintTechnicsPartLinkJFrame
    extends JFrame {
  //�����㲿����master����
  private Vector vec = new Vector();

  private HashMap map = new HashMap();

  //Ϳװ���һ����༭����
  private PaintPartSubListJFrame parentframe;
  CappMultiList multilist;
  ComponentMultiList comlist = new ComponentMultiList();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton selectAllButton = new JButton();
  JButton okButton = new JButton();
  JButton exitButton = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton xiaojianButton = new JButton();
  JButton chejiaButton = new JButton();
  JButton cheshenButton = new JButton();

  /**
   * ȱʡ���캯��
   */
  public PaintTechnicsPartLinkJFrame() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
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
    // this.setSize(1000,800);

    setViewLocation();
    super.setVisible(flag);
  }

  /**
   * @param vec Vector �����㲿������
   * @param parentframe PaintPartSubListJFrame Ϳװ���һ����༭����
   */
  public PaintTechnicsPartLinkJFrame(Vector vec,
                                     PaintPartSubListJFrame parentframe) {
    this.vec = vec;
    this.parentframe = parentframe;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.setSize(650, 600);
    this.setTitle("ѡ��Ϳװ���");
    setIconImage(new ImageIcon(getClass().getResource(
        "/images/technics.gif")).getImage());
    this.setResizable(true);
    String[] heading = {
        "������", "�������", "·�ߴ�"};
    comlist.setHeadings(heading);
    // ����ÿ����ʾ������
    int[] bsoListHeadsWidth = {
        1, 2, 2};
    comlist.setRelColWidth(bsoListHeadsWidth);
    // �����б���еĶ��뷽ʽ��
    String[] leftAlign = {
        "left", "left", "left"};
    comlist.setColumnAlignments(leftAlign);
    // ���ø��б�Ϊ�ɶ�ѡ��("Shift"����ѡ)
    comlist.setMultipleMode(true);
    // ����������
    comlist.setAllowSorting(true);
    // ��������б��е����ݽ��б༭
    comlist.setCellEditable(false);
    if (vec != null && vec.size() > 0) {
      insertPartToMultiList(vec);
    }
    this.getContentPane().setLayout(gridBagLayout1);
    jPanel1.setLayout(gridBagLayout2);
    jPanel2.setLayout(gridBagLayout3);
    selectAllButton.setMaximumSize(new Dimension(79, 29));
    selectAllButton.setMinimumSize(new Dimension(79, 29));
    selectAllButton.setPreferredSize(new Dimension(79, 29));
    selectAllButton.setToolTipText("");
    selectAllButton.setText("ȫѡ");
    selectAllButton.addActionListener(new
                                      PaintTechnicsPartLinkJFrame_selectAllButton_actionAdapter(this));
    okButton.setMaximumSize(new Dimension(79, 29));
    okButton.setMinimumSize(new Dimension(79, 29));
    okButton.setPreferredSize(new Dimension(79, 29));
    okButton.setRequestFocusEnabled(true);
    okButton.setText("ȷ��");
    okButton.addActionListener(new
                               PaintTechnicsPartLinkJFrame_okButton_actionAdapter(this));
    exitButton.setMaximumSize(new Dimension(79, 29));
    exitButton.setMinimumSize(new Dimension(79, 29));
    exitButton.setPreferredSize(new Dimension(79, 29));
    exitButton.setText("�˳�");
    exitButton.addActionListener(new
                                 PaintTechnicsPartLinkJFrame_exitButton_actionAdapter(this));
    xiaojianButton.setMaximumSize(new Dimension(79, 29));
    xiaojianButton.setMinimumSize(new Dimension(79, 29));
    xiaojianButton.setPreferredSize(new Dimension(79, 29));
    xiaojianButton.setText("С����");
    xiaojianButton.addActionListener(new
                                     PaintTechnicsPartLinkJFrame_xiaojianButton_actionAdapter(this));
    chejiaButton.setMaximumSize(new Dimension(79, 29));
    chejiaButton.setMinimumSize(new Dimension(79, 29));
    chejiaButton.setPreferredSize(new Dimension(79, 29));
    chejiaButton.setText("������");
    chejiaButton.addActionListener(new
                                   PaintTechnicsPartLinkJFrame_chejiaButton_actionAdapter(this));
    cheshenButton.setMaximumSize(new Dimension(79, 29));
    cheshenButton.setMinimumSize(new Dimension(79, 29));
    cheshenButton.setOpaque(true);
    cheshenButton.setPreferredSize(new Dimension(79, 29));
    cheshenButton.setText("������");
    cheshenButton.addActionListener(new
                                    PaintTechnicsPartLinkJFrame_cheshenButton_actionAdapter(this));
    this.getContentPane().add(jPanel1,
                              new GridBagConstraints(0, 0, 1, 1, 9.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, -2, 0, 0), 0, 0));
    jPanel1.add(comlist, new GridBagConstraints(0, 0, 1, 1, 0.8, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jPanel2,
                              new GridBagConstraints(1, 0, 1, 1, 0.4, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        new Insets(0, 0, 0, 2), 0, 0));
    jPanel2.add(okButton, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(4, 0, 0, 0), 0, 0));
    jPanel2.add(exitButton, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(4, 0, 356, 0), 0, 0));
    jPanel2.add(selectAllButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(4, 0, 0, 0), 0, 0));
    jPanel2.add(xiaojianButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(4, 0, 0, 0), 0, 0));
    jPanel2.add(chejiaButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(4, 0, 0, 0), 0, 0));
    jPanel2.add(cheshenButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(4, 0, 0, 0), 0, 0));

  }

  /**
   * ȫѡ��ť����
   * @param e ActionEvent
   */
  void selectAllButton_actionPerformed(ActionEvent e) {
    if (comlist.getRowCount() > 0) {
      for (int i = 0; i < comlist.getRowCount(); i++) {
        comlist.setSelectedRow(i);
      }
    }
  }

  /**
   * �˳���ť����
   * @param e ActionEvent
   */
  void exitButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  /**
   * ȷ����ť����
   * @param e ActionEvent
   */
  void okButton_actionPerformed(ActionEvent e) {
    int[] rownums = comlist.getSelectedRows();
    if (rownums != null && rownums.length > 0) {
      for (int i = 0; i < rownums.length; i++) {
        String partnum = comlist.getCellText(rownums[i], 0);
        parentframe.partTechLinkJPanel.addPartToTable( (QMPartIfc) map.get(
            partnum));
      }
      this.dispose();
    }

  }

  /**
   * ���б��в�������
   * ����ͨ��partmaster��ñ�ź����ƣ�Ȼ��������С�汾��ͨ������С�汾���÷����������·�ߴ�
   * @param v Vector
   */
  private void insertPartToMultiList(Vector v) {
    if (v != null && v.size() > 0) {
      Iterator iter = v.iterator();
      for (int i = 0; iter.hasNext(); i++) {
        Object[] str = (Object[]) iter.next();
        comlist.addTextCell(i, 0, (String) str[0]);
        comlist.addTextCell(i, 1, (String) str[1]);
        comlist.addTextCell(i, 2, (String) str[2]);
        //�����ݷ��뻺�棬��ȷ����ťʱʹ��
        map.put( (String) str[0], (QMPartIfc) str[3]);
      }
    }
  }

  /**
   * ����ť�����¼�
   * @param e ActionEvent
   */
  void cheshenButton_actionPerformed(ActionEvent e) {
    comlist.deselectAll();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int row = 0; row < rowcount; row++) {
        String routetext = comlist.getStringValue(row, 2);
        if (routetext.indexOf("��(��)") != -1) {
          comlist.selectRow(row);
        }
      }
    }
  }

  /**
   * ���ܰ�ť�����¼�
   * @param e ActionEvent
   */
  void chejiaButton_actionPerformed(ActionEvent e) {
    comlist.deselectAll();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int row = 0; row < rowcount; row++) {
        String routetext = comlist.getStringValue(row, 2);
        if (routetext.indexOf("��(��)") != -1) {
          comlist.selectRow(row);
        }
      }
    }

  }

  /**
   * С����ť�����¼�
   * @param e ActionEvent
   */
  void xiaojianButton_actionPerformed(ActionEvent e) {
    comlist.deselectAll();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int row = 0; row < rowcount; row++) {
        String routetext = comlist.getStringValue(row, 2);
        if (routetext.indexOf("Ϳ") != -1) {
          if (routetext.indexOf("Ϳ(��)") == -1) {
            comlist.selectRow(row);
          }
        }
      }
    }

  }
}

class PaintTechnicsPartLinkJFrame_selectAllButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintTechnicsPartLinkJFrame adaptee;

  PaintTechnicsPartLinkJFrame_selectAllButton_actionAdapter(
      PaintTechnicsPartLinkJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.selectAllButton_actionPerformed(e);
  }
}

class PaintTechnicsPartLinkJFrame_exitButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintTechnicsPartLinkJFrame adaptee;

  PaintTechnicsPartLinkJFrame_exitButton_actionAdapter(
      PaintTechnicsPartLinkJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exitButton_actionPerformed(e);
  }
}

class PaintTechnicsPartLinkJFrame_okButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintTechnicsPartLinkJFrame adaptee;

  PaintTechnicsPartLinkJFrame_okButton_actionAdapter(
      PaintTechnicsPartLinkJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class PaintTechnicsPartLinkJFrame_cheshenButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintTechnicsPartLinkJFrame adaptee;

  PaintTechnicsPartLinkJFrame_cheshenButton_actionAdapter(
      PaintTechnicsPartLinkJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cheshenButton_actionPerformed(e);
  }
}

class PaintTechnicsPartLinkJFrame_chejiaButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintTechnicsPartLinkJFrame adaptee;

  PaintTechnicsPartLinkJFrame_chejiaButton_actionAdapter(
      PaintTechnicsPartLinkJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.chejiaButton_actionPerformed(e);
  }
}

class PaintTechnicsPartLinkJFrame_xiaojianButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintTechnicsPartLinkJFrame adaptee;

  PaintTechnicsPartLinkJFrame_xiaojianButton_actionAdapter(
      PaintTechnicsPartLinkJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.xiaojianButton_actionPerformed(e);
  }
}
