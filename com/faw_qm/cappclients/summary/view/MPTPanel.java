package com.faw_qm.cappclients.summary.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import com.faw_qm.cappclients.summary.controller.SummaryMPTController;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.summary.model.TotalResultIfc;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import java.util.ArrayList;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.capp.model.QMTechnicsQMProcedureLinkIfc;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkIfc;
import com.faw_qm.resource.support.model.QMMaterialIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.procedure.QMMachineProcedureIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttModel;

/**
 * <p>Title:�������һ������ܽ��� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: һ������</p>
 * @author ������
 * @version 1.0
 */

public class MPTPanel
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
  private SummaryMPTController ptControl;
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
   * ���������Ĺ��캯��
   */
  public MPTPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ��һ�������Ĺ��캯��
   * @param control ActionListener
   */
  public MPTPanel(ActionListener control) {
    super();
    try {
      this.ptControl = (SummaryMPTController) control;
      jbInit();
      localize();

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ��ʼ������
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
    jTabbedPane1.add(jPanel2, "��������");
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
        "���", "���ͼ��", "�������", "ë���ߴ�", "���Ϲ��", "�����ƺ�", "���ϱ�׼��",
        "����", "��ʱ", "��Ա", "��ע"};
    this.multiList.setHeadings(heading);
    // ����ÿ����ʾ������
    int[] bsoListHeadsWidth = {
        1, 2, 2, 1, 2, 2, 2, 1, 1, 1, 2};
    multiList.setRelColWidth(bsoListHeadsWidth);
    multiList.setAllowSorting(false);
    multiList.setCellEditable(false);
  }

  /**
   * ���ػ�
   */
  public void localize() {
    summaryButton.setText(res.getString("summary"));
    partLabel.setText("������/����");
    searchButton.setText(res.getString("search2"));
    exportButton.setText(res.getString("export"));
    exitButton.setText(res.getString("exit"));
  }

  /**
   * paginatePanel�ļ���ʵ��
   * @param e PaginateEvent
   */
  void paginatePanel_paginateEvent(PaginateEvent e) {
//    Vector tempVector = paginatePanel1.getCurrentObjects();
//    this.addMultiList(tempVector);
  }

  /**
   * �����㲿���ŵ�ֵ
   * @return String
   */
  public String getPartNum() {
    part = partTextField.getText().trim();
    if (part.equals("")) {
      JOptionPane.showMessageDialog(this, "�����빤�պ��ٽ��л���", "��ʾ",
                                    JOptionPane.ERROR_MESSAGE);
      return null;
    }
    else {
      return part;
    }
  }

  /**
   * �趨������Ϣ
   * @return String
   */
  public String getSumInfo() {
    return size + res.getString("have been found");
  }

  /**
   * ���û��ܽ��ֵ����
   * @param totalResult TotalResultIfc
   */
  public void setTotalResult(TotalResultIfc totalResult) {
    this.totalResult = totalResult;
  }

  /**
   * �����ܽ��������������
   * @return Vector
   */
  public Vector exportResult() {
    Vector vector = new Vector();
    int i = multiList.getNumberOfRows();
    if (i >= 0) {
      for (int row = 0; row < i; row++) {
        String[] data = new String[11];
        for (int column = 0; column < 11; column++) {
          data[column] = multiList.getCellText(row, column);
        }
        vector.add(data);
      }
    }
    return vector;
  }

  /**
   * ���û���������ʾ��
   * @param s String[]
   */
  public void setAttributes(String[] s) {
    this.attrDisplayname = s;
  }

  /**
   * ���ù������Ϣ
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
   * ���»��ܱ�ͷ
   */
  public void updateHeadings() {
    multiList.updateHeadings(attrDisplayname);
  }

  /**
   * ���÷�ҳ��ҳ��Ϊ1
   */
  public void newPaginatePanel() {
    paginatePanel1.paginate(null);
  }

  /**
   * ���û��ܰ�ť��״̬
   */
  public void setSaveButtonstate(boolean flag) {
    summaryButton.setEnabled(flag);
  }

  /**
   * �����ܽ�����뵽�����
   * @param v Vector
   */
  public void addMultiList(ArrayList v) {
    int i = 0;
    this.multiList.clear();
    for (Iterator iterator = v.iterator(); iterator.hasNext(); ) {
      BaseValueIfc[] objs = (BaseValueIfc[]) iterator.next();
      QMMachineProcedureIfc proifc = (QMMachineProcedureIfc) objs[0];
      QMTechnicsQMProcedureLinkIfc pifc = (QMTechnicsQMProcedureLinkIfc) objs[1];
      QMTechnicsQMMaterialLinkIfc mifc = (QMTechnicsQMMaterialLinkIfc) objs[2];
      QMMaterialIfc materialifc = (QMMaterialIfc) objs[3];
      QMPartMasterIfc masterifc = (QMPartMasterIfc) objs[4];
      ExtendAttContainer container = mifc.getExtendAttributes();
      ExtendAttModel model = container.findExtendAttModel("roughSizePerPiece");
      ExtendAttModel model1 = container.findExtendAttModel("jingzhong");
      String maopichicun = (String) model.getAttValue();
      String jingzhong = (String) model1.getAttValue();
      String materialguige = materialifc.getMaterialSpecial();
      String materialpaihao = materialifc.getMaterialName();
      String materialbiaozhun = materialifc.getMaterialCrision();
      String partname = masterifc.getPartName();
      String partnum = masterifc.getPartNumber();
      //��ʱ
      String hour = Double.toString(proifc.getStepHour());
      //��Ա
      String dingyuan = Double.toString(proifc.getAidTime());
      this.multiList.addTextCell(i, 0, Integer.toString(i + 1));
      this.multiList.addTextCell(i, 1, partnum);
      this.multiList.addTextCell(i, 2, partname);
      this.multiList.addTextCell(i, 3, maopichicun);
      this.multiList.addTextCell(i, 4, materialguige);
      this.multiList.addTextCell(i, 5, materialpaihao);
      this.multiList.addTextCell(i, 6, materialbiaozhun);
      this.multiList.addTextCell(i, 7, jingzhong);
      this.multiList.addTextCell(i, 8, hour);
      this.multiList.addTextCell(i, 9, dingyuan);
      this.multiList.addTextCell(i, 10, "");
      i++;
    }

  }

}
