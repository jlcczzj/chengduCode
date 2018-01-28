package com.faw_qm.cappclients.summary.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import com.faw_qm.cappclients.summary.controller.SummaryRPTController;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.summary.model.TotalResultIfc;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkIfc;
import com.faw_qm.resource.support.model.QMToolIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;

/**
 * <p>Title:零件一览表汇总界面 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 一汽启明</p>
 * @author 唐树涛
 * @version 1.0
 */

public class RPTPanel
    extends JPanel {
  private JPanel jPanel1 = new JPanel();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel2 = new JPanel();
  private JButton summaryButton = new JButton();
  private JPanel jPanel4 = new JPanel();
  private JLabel partLabel = new JLabel();
  private JLabel techcategoryLabel = new JLabel();
  private JLabel departLabel = new JLabel();
  private JTextField partTextField = new JTextField();
  private JButton searchButton = new JButton();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JPanel jPanel3 = new JPanel();
  private JButton exportButton = new JButton();
  private JButton exitButton = new JButton();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private JLabel pdtypeLabel = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private SummaryRPTController ptControl;
  private CappMultiList multiList = new CappMultiList();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private TotalResultIfc totalResult;
  private String[] attrDisplayname;
  private String part;
  private int size;
  private static ResourceBundle res =
      ResourceBundle.getBundle(
      "com.faw_qm.cappclients.summary.util.SummaryResource",
      RemoteProperty.getVersionLocale());
  private Vector vector;
  private GridBagLayout gridBagLayout5 = new GridBagLayout();
  private PaginatePanel paginatePanel1 = new PaginatePanel();
  private QMProcedureIfc proinfo;

  /**
   * 不带参数的构造函数
   */
  public RPTPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 带一个参数的构造函数
   * @param control ActionListener
   */
  public RPTPanel(ActionListener control) {
    super();
    try {
      this.ptControl = (SummaryRPTController) control;
      jbInit();
      localize();

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 初始化函数
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout5);
    jPanel1.setLayout(gridBagLayout2);
    summaryButton.setMaximumSize(new Dimension(130, 23));
    summaryButton.setMinimumSize(new Dimension(130, 23));
    summaryButton.setPreferredSize(new Dimension(130, 23));

    jPanel2.setLayout(gridBagLayout1);
    jPanel4.setLayout(gridBagLayout3);

    searchButton.setMaximumSize(new Dimension(80, 23));
    searchButton.setMinimumSize(new Dimension(80, 23));
    searchButton.setPreferredSize(new Dimension(80, 23));

    jPanel3.setLayout(gridBagLayout4);

    exportButton.setMaximumSize(new Dimension(97, 23));
    exportButton.setMinimumSize(new Dimension(97, 23));
    exportButton.setPreferredSize(new Dimension(97, 23));
    //exportButton.setEnabled(false);

    exitButton.setMaximumSize(new Dimension(97, 23));
    exitButton.setMinimumSize(new Dimension(97, 23));
    exitButton.setPreferredSize(new Dimension(97, 23));
    exitButton.addActionListener(ptControl);
    summaryButton.addActionListener(ptControl);
    searchButton.addActionListener(ptControl);
    exportButton.addActionListener(ptControl);
    exitButton.setActionCommand("EXIT");
    exitButton.setMnemonic('X');
    summaryButton.setActionCommand("SUM");
    summaryButton.setMnemonic('T');
    searchButton.setActionCommand("SEARCH");
    exportButton.setActionCommand("EXPORT");
    exportButton.setMnemonic('R');

    paginatePanel1.addListener(new PaginateListener() {
      public void paginateEvent(PaginateEvent e) {
        paginatePanel_paginateEvent(e);
      }
    });
    this.add(jPanel1, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.HORIZONTAL,
                                             new Insets(8, 0, 0, 0), 0, 0));
    jPanel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 4, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
    jTabbedPane1.add(jPanel2, "汇总条件");
    partTextField.setEditable(false);
    jPanel2.add(partTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(4, 8, 4, 0), 0, 0));
    partLabel.setText("");
    techcategoryLabel.setText("");
    pdtypeLabel.setText("");
    jPanel2.add(partLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(4, 8, 4, 0), 0, 0));
    jPanel2.add(techcategoryLabel,
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.EAST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 4, 0), 0, 0));
    jPanel2.add(pdtypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(4, 8, 4, 0), 0, 0));
    jPanel2.add(departLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(4, 0, 4, 0), 0, 0));
    jPanel2.add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(4, 8, 4, 0), 0, 0));
    jPanel2.add(pdtypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(10, 8, 0, 0), 0, 0));
    jPanel1.add(summaryButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
        new Insets(24, 8, 0, 10), 0, 0));
    this.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(10, 10, 0, 10), 0,
                                             0));
    jPanel4.add(multiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 0, 0, 0), 0, 0));
    this.add(jPanel3, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.HORIZONTAL,
                                             new Insets(8, 0, 8, 10), 0, 0));
    jPanel3.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 0), 0, 0));
    jPanel3.add(exportButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
    this.add(paginatePanel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 10, 0, 8), 0, 0));
    String[] heading = {
        "序号", "零件图号", "零件名称", "材料定额", "工装(具)-名称", "工装(具)-编号/规格",
        "工装(具)-数量", "量检具-名称", "量检具-编号/规格", "量检具-数量"};
    this.multiList.setHeadings(heading);
    // 设置每列显示比例：
    int[] bsoListHeadsWidth = {
        1, 2, 2, 1, 2, 2, 2, 2, 2, 2};
    multiList.setRelColWidth(bsoListHeadsWidth);
    multiList.setAllowSorting(false);
    multiList.setCellEditable(false);
  }

  /**
   * 本地化
   */
  public void localize() {
    summaryButton.setText(res.getString("summary"));
    partLabel.setText("工序编号/名称");
    searchButton.setText(res.getString("search2"));
    exportButton.setText(res.getString("export"));
    exitButton.setText(res.getString("exit"));
  }

  /**
   * paginatePanel的监听实现
   * @param e PaginateEvent
   */
  void paginatePanel_paginateEvent(PaginateEvent e) {
//    Vector tempVector = paginatePanel1.getCurrentObjects();
//    this.addMultiList(tempVector);
  }

  /**
   * 返回零部件号的值
   * @return String
   */
  public String getPartNum() {
    part = partTextField.getText().trim();
    if (part.equals("")) {
      JOptionPane.showMessageDialog(this, "请输入工艺后再进行汇总", "提示",
                                    JOptionPane.ERROR_MESSAGE);
      return null;
    }
    else {
      return part;
    }
  }

  /**
   * 设定汇总信息
   * @return String
   */
  public String getSumInfo() {
    return size + res.getString("have been found");
  }

  /**
   * 设置汇总结果值对象
   * @param totalResult TotalResultIfc
   */
  public void setTotalResult(TotalResultIfc totalResult) {
    this.totalResult = totalResult;
  }

  /**
   * 将汇总结果导出到集合中
   * @return Vector
   */
  public Vector exportResult() {
    Vector vector = new Vector();
    int i = multiList.getNumberOfRows();
    if (i >= 0) {
      for (int row = 0; row < i; row++) {
        String[] data = new String[10];
        for (int column = 0; column < 10; column++) {
          data[column] = multiList.getCellText(row, column);
        }
        vector.add(data);
      }
    }
    return vector;
  }

  /**
   * 设置汇总属性显示名
   * @param s String[]
   */
  public void setAttributes(String[] s) {
    this.attrDisplayname = s;
  }

  /**
   * 设置工序的信息
   * @param info QMFawTechnicsIfc
   */
  public void setProcedureIfc(QMProcedureIfc info) {
    proinfo = info;
    this.partTextField.setText(info.getDescStepNumber() + info.getStepName());
  }

  public QMProcedureIfc getProcedureIfc() {
    return proinfo;
  }

  /**
   * 更新汇总表头
   */
  public void updateHeadings() {
    multiList.updateHeadings(attrDisplayname);
  }

  /**
   * 设置分页的页数为1
   */
  public void newPaginatePanel() {
    paginatePanel1.paginate(null);
  }

  /**
   * 使表头均匀分布
   * 问题（2）2006.08.11  徐春英修改  输出定制完成后，multilist里有很多空行，应该清空
   */
  public void setMultilistRow() {
    int a = ptControl.getAttrCount();
    if (a > 10) {
      a = 10;
    }
    int[] aa = new int[a];
    for (int s = 0; s < a; s++) {
      aa[s] = 1;
    }
    this.multiList.setRelColWidth(aa);
    //问题（2）2006.08.11  徐春英修改  输出定制完成后，multilist里有很多空行，应该清空
    this.multiList.clear();
  }

  /**
   * 设置汇总按钮的状态
   */
  public void setSaveButtonstate(boolean flag) {
    summaryButton.setEnabled(flag);
  }

  /**
   * 将汇总结果加入到表格中
   * @param v Vector
   */
  public void addMultiList(ArrayList v) {
    int i = 0;
    this.multiList.clear();
    System.out.println("c.size: "+ v.size());
    for (Iterator iterator = v.iterator(); iterator.hasNext(); ) {
      BaseValueIfc[] objs = (BaseValueIfc[]) iterator.next();
      QMProcedureIfc proifc = (QMProcedureIfc) objs[0];
      QMProcedureQMToolLinkIfc linkifc = (QMProcedureQMToolLinkIfc) objs[1];
      QMTechnicsQMMaterialLinkIfc mifc = (QMTechnicsQMMaterialLinkIfc) objs[2];
      QMToolIfc toolifc = (QMToolIfc) objs[3];
      QMPartMasterIfc masterifc = (QMPartMasterIfc) objs[4];
      ExtendAttContainer container = mifc.getExtendAttributes();
      ExtendAttModel model = container.findExtendAttModel("materialration");
      String ration = (String) model.getAttValue();
      String partname = masterifc.getPartName();
      String partnum = masterifc.getPartNumber();
      this.multiList.addTextCell(i, 0, Integer.toString(i + 1));
      this.multiList.addTextCell(i, 1, partnum);
      this.multiList.addTextCell(i, 2, partname);
      this.multiList.addTextCell(i, 3, ration);
      if (toolifc != null) {
        String biangui = toolifc.getToolNum();
        if (toolifc.getToolSpec() != null) {
          biangui = biangui + "/" + toolifc.getToolSpec();
        }
        if (toolifc.getToolCf().getCodeContent().equals("检具") ||
            toolifc.getToolCf().getCodeContent().equals("量具")) {
          this.multiList.addTextCell(i, 4, "");
          this.multiList.addTextCell(i, 5, "");
          this.multiList.addTextCell(i, 6, "");
          this.multiList.addTextCell(i, 7, toolifc.getToolName());

          this.multiList.addTextCell(i, 8, biangui);
          this.multiList.addTextCell(i, 9,
                                    linkifc.getUsageCount());
        }
        else {
          this.multiList.addTextCell(i, 4, toolifc.getToolName());
          this.multiList.addTextCell(i, 5, biangui);
          this.multiList.addTextCell(i, 6,
                                     linkifc.getUsageCount());
          this.multiList.addTextCell(i, 7, "");
          this.multiList.addTextCell(i, 8, "");
          this.multiList.addTextCell(i, 9, "");
        }
      }
      i++;
    }

  }

}
