package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import javax.swing.*;
import com.faw_qm.clients.util.QMMultiList;
import java.awt.event.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import java.util.Collection;
import java.util.ArrayList;

import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import java.util.HashMap;
import java.util.Vector;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.clients.util.RefreshService;


/**
 * <p>Title:搜索没有编制路线的子件 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */


public class SearchSubDialog
    extends JDialog {
  JPanel jPanel2 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JButton searchJB = new JButton();
  JPanel jPanel3 = new JPanel();
  JButton selectAllJB = new JButton();
  JButton okJB = new JButton();
  JButton cancelJB = new JButton();
  JLabel jLabel2 = new JLabel();
  QMMultiList qMMultiList = new QMMultiList();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  private static String RESOURCE =
      "com.faw_qm.cappclients.capproute.util.CappRouteRB";


  public SearchSubDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public SearchSubDialog() {
    this(null, "", false);
  }


  private void jbInit() throws Exception {
    this.setSize(640, 480);
    this.getContentPane().setLayout(gridBagLayout4);
    jPanel2.setBorder(null);
    jPanel2.setLayout(gridBagLayout2);
    jPanel1.setBorder(null);
    jPanel1.setLayout(gridBagLayout3);
    jLabel1.setMaximumSize(new Dimension(165, 18));
    jLabel1.setMinimumSize(new Dimension(165, 18));
    jLabel1.setPreferredSize(new Dimension(181, 18));
    jLabel1.setText("请输入零件编号进行查询");
    jTextField1.setMaximumSize(new Dimension(80, 22));
    jTextField1.setMinimumSize(new Dimension(80, 22));
    jTextField1.setPreferredSize(new Dimension(80, 22));
    jTextField1.setText("");
    searchJB.setMaximumSize(new Dimension(65, 23));
    searchJB.setMinimumSize(new Dimension(65, 23));
    searchJB.setPreferredSize(new Dimension(65, 23));
    searchJB.setText("搜索");
    searchJB.addActionListener(new
                               SearchSubDialog_searchJB_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout1);
    selectAllJB.setMaximumSize(new Dimension(65, 23));
    selectAllJB.setMinimumSize(new Dimension(65, 23));
    selectAllJB.setPreferredSize(new Dimension(65, 23));
    selectAllJB.setText("全选");
    selectAllJB.addActionListener(new
                               SearchSubDialog_selectAllJB_actionAdapter(this));
    okJB.setMaximumSize(new Dimension(65, 23));
    okJB.setMinimumSize(new Dimension(65, 23));
    okJB.setPreferredSize(new Dimension(65, 23));
    okJB.setText("确定");
    okJB.addActionListener(new
                               SearchSubDialog_okJB_actionAdapter(this));
    cancelJB.setMaximumSize(new Dimension(65, 23));
    cancelJB.setMinimumSize(new Dimension(65, 23));
    cancelJB.setPreferredSize(new Dimension(65, 23));
    cancelJB.setText("取消");
    cancelJB.addActionListener(new
                               SearchSubDialog_cancelJB_actionAdapter(this));
    jLabel2.setText("");
    this.getContentPane().add(jPanel2,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 8, 0, 8), 0, 0));
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id 
    qMMultiList.setHeadings(new String[] {"id", "编号", "名称", "版本", "视图", "partid","状态"});
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 0, 1});
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    qMMultiList.setCellEditable(false);
    qMMultiList.setMultipleMode(true);
    qMMultiList.getTable().setShowHorizontalLines(true);
    qMMultiList.getTable().setShowVerticalLines(true);

    this.getContentPane().add(jPanel1,
                              new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 1), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.NONE,
                                                new Insets(9, 8, 0, 126), 0, 0));
    jPanel1.add(jTextField1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL,
        new Insets(9, 8, 10, 0), 0, 0));
    jPanel1.add(searchJB, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.SOUTHEAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(9, 9, 10, 8), 0, 0));
    this.getContentPane().add(jPanel3,
                              new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 8, 0), 0, 0));
    jPanel3.add(selectAllJB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTHEAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(8, 12, 1, 8), 16, 0));
    jPanel3.add(okJB, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(8, 0, 1, 8), 16, 0));
    jPanel3.add(cancelJB, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTHEAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(8, 0, 1, 8), 0, 0));
    jPanel3.add(jLabel2,    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 8, 0, 1), 0, 0));

  }

  private void setViewLocation() {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation( (screenSize.width - frameSize.width) / 2,
                     (screenSize.height - frameSize.height) / 2);
  }

  /**
   * 重载父类方法，使界面显示在屏幕中央
   * @param flag
   */
  public void setVisible(boolean flag) {
    this.setViewLocation();
    super.setVisible(flag);
  }

  /**
   * 按当前配置规范将指定零件号的所有未编制工艺路线的子件获得并显示在表格中
   * @param e ActionEvent
   */
  void searchJB_actionPerformed(ActionEvent e) {
    qMMultiList.clear();
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    String num =  jTextField1.getText().trim();
    if(num==null || num.equals(""))
    {
      JOptionPane.showMessageDialog(this, "请输入零件编号!");
      this.setCursor(Cursor.getDefaultCursor());
      return;
    }
    num = num.toUpperCase();
    Class[] string = {
        String.class};
    String[] value = {num};

    Vector partsColl = new Vector();
    try {
      partsColl = (Vector) CappRouteAction.
          useServiceMethod(
          "TechnicsRouteService", "getSubPartsNoRoute", string,
          value);
    }
    catch (QMRemoteException ex) {
      JOptionPane.showMessageDialog(this, ex.getClientMessage());
      this.setCursor(Cursor.getDefaultCursor());
      return;
    }
    if (partsColl.size() == 0) {
      this.setCursor(Cursor.getDefaultCursor());
      jLabel2.setText("0个搜索结果");
      return;
    }

    for (int i = 0; i < partsColl.size(); i++) {
      QMPartInfo part = (QMPartInfo) partsColl.get(i);
      qMMultiList.addTextCell(i, 0, part.getMasterBsoID());
      qMMultiList.addTextCell(i, 1, part.getPartNumber());
      qMMultiList.addTextCell(i, 2, part.getPartName());
      qMMultiList.addTextCell(i, 3, part.getVersionValue());
      qMMultiList.addTextCell(i, 4, part.getViewName());
//    CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
      qMMultiList.addTextCell(i, 5, part.getBsoID());
      qMMultiList.addTextCell(i, 6, part.getLifeCycleState().getDisplay());
//    CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id 
    }
    jLabel2.setText(partsColl.size() + "个搜索结果");
    this.setCursor(Cursor.getDefaultCursor());
  }

  void selectAllJB_actionPerformed(ActionEvent e) {
    if (qMMultiList.getNumberOfRows() > 0) {
      qMMultiList.selectAll();
    }

  }

  void cancelJB_actionPerformed(ActionEvent e) {

    this.dispose();
  }

  void okJB_actionPerformed(ActionEvent e) {
    Vector selectedPartMastersColl = new Vector();
    QMPartMasterIfc partMasterIfc;
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    QMPartIfc partIfc;  
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    int[] indexes = qMMultiList.getSelectedRows();
    for (int i = 0; i < indexes.length; i++) {
      int x = indexes[i];
      if (x != -1) {
        String masterbsoid = qMMultiList.getCellAt(x, 0).toString();
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        String partid=qMMultiList.getCellAt(x, 5).toString();
        System.out.println("--partid="+partid);
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        Class[] string = {
            String.class};
        String[] bsoId = {
            masterbsoid};
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        Class[] string1 = {
                String.class};
            String[] bsoId1 = {
            		partid};
//          CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        try {
          partMasterIfc = (QMPartMasterIfc) CappRouteAction.
              useServiceMethod(
              "PersistService", "refreshInfo", string, bsoId);
//        CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
          partIfc = (QMPartIfc) CappRouteAction.
          useServiceMethod(
          "PersistService", "refreshInfo", string1, bsoId1);
          Object [] part={partMasterIfc,partIfc};
          selectedPartMastersColl.addElement(part);
        //  selectedPartMastersColl.addElement(partMasterIfc);
//        CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        }
        catch (QMRemoteException ex) {
          JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(
              RESOURCE,
              "exception", null),
                                        JOptionPane.INFORMATION_MESSAGE);
        }

      }
    }
    this.dispose();
    RefreshService.getRefreshService().dispatchRefresh("addSelectedParts",
        0, selectedPartMastersColl);
  }

}

class SearchSubDialog_searchJB_actionAdapter
    implements java.awt.event.ActionListener {
  SearchSubDialog adaptee;

  SearchSubDialog_searchJB_actionAdapter(SearchSubDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchJB_actionPerformed(e);
  }
}

class SearchSubDialog_selectAllJB_actionAdapter
    implements java.awt.event.ActionListener {
  SearchSubDialog adaptee;

  SearchSubDialog_selectAllJB_actionAdapter(SearchSubDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.selectAllJB_actionPerformed(e);
  }
}

class SearchSubDialog_cancelJB_actionAdapter
    implements java.awt.event.ActionListener {
  SearchSubDialog adaptee;

  SearchSubDialog_cancelJB_actionAdapter(SearchSubDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJB_actionPerformed(e);
  }
}

class SearchSubDialog_okJB_actionAdapter
    implements java.awt.event.ActionListener {
  SearchSubDialog adaptee;

  SearchSubDialog_okJB_actionAdapter(SearchSubDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJB_actionPerformed(e);
  }
}
