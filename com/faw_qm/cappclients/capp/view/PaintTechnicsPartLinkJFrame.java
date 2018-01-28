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
 * <p>Title:显示艺准通知书中路线中带有“涂”字的零部件集合界面</p>
 * <p>Description: tangshutao add for qingqi 2007.09.13</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明</p>
 * @author 唐树涛
 * @version 1.0
 */
public class PaintTechnicsPartLinkJFrame
    extends JFrame {
  //关联零部件的master集合
  private Vector vec = new Vector();

  private HashMap map = new HashMap();

  //涂装零件一览表编辑界面
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
   * 缺省构造函数
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
   * 设置界面的显示位置
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
   * 重载父类方法
   * @param flag
   */
  public void setVisible(boolean flag) {
    // this.setSize(1000,800);

    setViewLocation();
    super.setVisible(flag);
  }

  /**
   * @param vec Vector 关联零部件集合
   * @param parentframe PaintPartSubListJFrame 涂装零件一览表编辑界面
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
    this.setTitle("选择涂装零件");
    setIconImage(new ImageIcon(getClass().getResource(
        "/images/technics.gif")).getImage());
    this.setResizable(true);
    String[] heading = {
        "零件编号", "零件名称", "路线串"};
    comlist.setHeadings(heading);
    // 设置每列显示比例：
    int[] bsoListHeadsWidth = {
        1, 2, 2};
    comlist.setRelColWidth(bsoListHeadsWidth);
    // 设置列表的列的对齐方式：
    String[] leftAlign = {
        "left", "left", "left"};
    comlist.setColumnAlignments(leftAlign);
    // 设置该列表为可多选：("Shift"键多选)
    comlist.setMultipleMode(true);
    // 允许按列排序：
    comlist.setAllowSorting(true);
    // 不允许对列表中的内容进行编辑
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
    selectAllButton.setText("全选");
    selectAllButton.addActionListener(new
                                      PaintTechnicsPartLinkJFrame_selectAllButton_actionAdapter(this));
    okButton.setMaximumSize(new Dimension(79, 29));
    okButton.setMinimumSize(new Dimension(79, 29));
    okButton.setPreferredSize(new Dimension(79, 29));
    okButton.setRequestFocusEnabled(true);
    okButton.setText("确定");
    okButton.addActionListener(new
                               PaintTechnicsPartLinkJFrame_okButton_actionAdapter(this));
    exitButton.setMaximumSize(new Dimension(79, 29));
    exitButton.setMinimumSize(new Dimension(79, 29));
    exitButton.setPreferredSize(new Dimension(79, 29));
    exitButton.setText("退出");
    exitButton.addActionListener(new
                                 PaintTechnicsPartLinkJFrame_exitButton_actionAdapter(this));
    xiaojianButton.setMaximumSize(new Dimension(79, 29));
    xiaojianButton.setMinimumSize(new Dimension(79, 29));
    xiaojianButton.setPreferredSize(new Dimension(79, 29));
    xiaojianButton.setText("小件线");
    xiaojianButton.addActionListener(new
                                     PaintTechnicsPartLinkJFrame_xiaojianButton_actionAdapter(this));
    chejiaButton.setMaximumSize(new Dimension(79, 29));
    chejiaButton.setMinimumSize(new Dimension(79, 29));
    chejiaButton.setPreferredSize(new Dimension(79, 29));
    chejiaButton.setText("车架线");
    chejiaButton.addActionListener(new
                                   PaintTechnicsPartLinkJFrame_chejiaButton_actionAdapter(this));
    cheshenButton.setMaximumSize(new Dimension(79, 29));
    cheshenButton.setMinimumSize(new Dimension(79, 29));
    cheshenButton.setOpaque(true);
    cheshenButton.setPreferredSize(new Dimension(79, 29));
    cheshenButton.setText("车身线");
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
   * 全选按钮动作
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
   * 退出按钮动作
   * @param e ActionEvent
   */
  void exitButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  /**
   * 确定按钮动作
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
   * 向列表中插入数据
   * 首先通过partmaster获得编号和名称，然后获得最新小版本，通过最新小版本调用服务获得零件的路线串
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
        //将数据放入缓存，在确定按钮时使用
        map.put( (String) str[0], (QMPartIfc) str[3]);
      }
    }
  }

  /**
   * 车身按钮监听事件
   * @param e ActionEvent
   */
  void cheshenButton_actionPerformed(ActionEvent e) {
    comlist.deselectAll();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int row = 0; row < rowcount; row++) {
        String routetext = comlist.getStringValue(row, 2);
        if (routetext.indexOf("漆(身)") != -1) {
          comlist.selectRow(row);
        }
      }
    }
  }

  /**
   * 车架按钮监听事件
   * @param e ActionEvent
   */
  void chejiaButton_actionPerformed(ActionEvent e) {
    comlist.deselectAll();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int row = 0; row < rowcount; row++) {
        String routetext = comlist.getStringValue(row, 2);
        if (routetext.indexOf("漆(架)") != -1) {
          comlist.selectRow(row);
        }
      }
    }

  }

  /**
   * 小件按钮监听事件
   * @param e ActionEvent
   */
  void xiaojianButton_actionPerformed(ActionEvent e) {
    comlist.deselectAll();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int row = 0; row < rowcount; row++) {
        String routetext = comlist.getStringValue(row, 2);
        if (routetext.indexOf("涂") != -1) {
          if (routetext.indexOf("涂(塑)") == -1) {
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
