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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author 
 * CC by leixiao 2008-7-31 原因：解放升级工艺路线,增加采用通知书添加 
 * @version 1.0
 */


public class SearchPublishDialog
    extends JDialog {
  JPanel jPanel2 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JButton jButton1 = new JButton();
  JPanel jPanel3 = new JPanel();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JLabel jLabel2 = new JLabel();
  QMMultiList qMMultiList = new QMMultiList();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  private static String RESOURCE =
      "com.faw_qm.cappclients.capproute.util.CappRouteRB";
  
  //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
  private boolean isManufacturing = false;
  //CCEnd by liunan 2012-05-22
  
  public SearchPublishDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
  public SearchPublishDialog(Frame frame, String title, boolean modal, boolean isManufacturing) 
  {
    super(frame, title, modal);
    try 
    {
      jbInit();
      pack();
      this.isManufacturing = isManufacturing;
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
  }
  //CCEnd by liunan 2012-05-22

  public SearchPublishDialog() {
    this(null, "", false);
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(gridBagLayout4);
    jPanel2.setBorder(null);
    jPanel2.setLayout(gridBagLayout2);
    jPanel1.setBorder(null);
    jPanel1.setLayout(gridBagLayout3);
    jLabel1.setMaximumSize(new Dimension(165, 18));
    jLabel1.setMinimumSize(new Dimension(165, 18));
    jLabel1.setPreferredSize(new Dimension(181, 18));
    //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
    //jLabel1.setText("请输入发布令号进行查询");
    jLabel1.setText("请输入解放采用编号进行查询");
    //CCEnd by liunan 2011-04-07
    jTextField1.setMaximumSize(new Dimension(80, 22));
    jTextField1.setMinimumSize(new Dimension(80, 22));
    jTextField1.setPreferredSize(new Dimension(80, 22));
    jTextField1.setText("");
    jButton1.setMaximumSize(new Dimension(65, 22));
    jButton1.setMinimumSize(new Dimension(65, 22));
    jButton1.setPreferredSize(new Dimension(65, 22));
    jButton1.setText("搜索");
    jButton1.addActionListener(new
                               SearchPublishDialog_jButton1_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout1);
    jButton2.setMaximumSize(new Dimension(56, 23));
    jButton2.setMinimumSize(new Dimension(56, 23));
    jButton2.setPreferredSize(new Dimension(56, 23));
    jButton2.setText("全选");
    jButton2.addActionListener(new
                               SearchPublishDialog_jButton2_actionAdapter(this));
    jButton3.setMaximumSize(new Dimension(56, 23));
    jButton3.setMinimumSize(new Dimension(56, 23));
    jButton3.setPreferredSize(new Dimension(56, 23));
    jButton3.setText("确定");
    jButton3.addActionListener(new
                               SearchPublishDialog_jButton3_actionAdapter(this));
    jButton4.setMaximumSize(new Dimension(60, 23));
    jButton4.setMinimumSize(new Dimension(60, 23));
    jButton4.setPreferredSize(new Dimension(60, 23));
    jButton4.setText("取消");
    jButton4.addActionListener(new
                               SearchPublishDialog_jButton4_actionAdapter(this));
    jLabel2.setText("");
    this.getContentPane().add(jPanel2,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 8, 0, 8), 0, 0));
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    qMMultiList.setHeadings(new String[] {"id", "编号", "名称", "版本", "视图","partid","状态"});
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 0,1});
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
    jPanel1.add(jButton1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.SOUTHEAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(9, 9, 10, 8), 0, 0));
    this.getContentPane().add(jPanel3,
                              new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 8, 0), 0, 0));
    jPanel3.add(jButton2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTHEAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(8, 12, 1, 8), 16, 0));
    jPanel3.add(jButton3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(8, 0, 1, 8), 16, 0));
    jPanel3.add(jButton4, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTHEAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(8, 0, 1, 8), 0, 0));
    jPanel3.add(jLabel2,    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 8, 0, 1), 0, 0));
    setViewLocation();
  }

  private void setViewLocation() {
    this.setSize(560, 450);
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
    this.setVisible(true);
  }

  void jButton1_actionPerformed(ActionEvent e) {
    qMMultiList.clear();
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    
    //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
    /*Class[] string = {
        String.class};
    String[] value = {
        jTextField1.getText()};*/
    Class[] string = {String.class, boolean.class};
    Object[] value = {jTextField1.getText(), new Boolean(isManufacturing)};
    //CCEnd by liunan 2012-05-22
    
    ArrayList partsColl = new ArrayList();
    try {
      partsColl = (ArrayList) CappRouteAction.
          useServiceMethod(
          "TechnicsRouteService", "getPublishRelatedParts", string,
          value);
    }
    catch (QMRemoteException ex) {
      JOptionPane.showMessageDialog(this, ex.getClientMessage());
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

  void jButton2_actionPerformed(ActionEvent e) {
    if (qMMultiList.getNumberOfRows() > 0) {
      qMMultiList.selectAll();
    }

  }

  void jButton4_actionPerformed(ActionEvent e) {

    this.dispose();
  }

  void jButton3_actionPerformed(ActionEvent e) {
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

class SearchPublishDialog_jButton1_actionAdapter
    implements java.awt.event.ActionListener {
  SearchPublishDialog adaptee;

  SearchPublishDialog_jButton1_actionAdapter(SearchPublishDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class SearchPublishDialog_jButton2_actionAdapter
    implements java.awt.event.ActionListener {
  SearchPublishDialog adaptee;

  SearchPublishDialog_jButton2_actionAdapter(SearchPublishDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class SearchPublishDialog_jButton4_actionAdapter
    implements java.awt.event.ActionListener {
  SearchPublishDialog adaptee;

  SearchPublishDialog_jButton4_actionAdapter(SearchPublishDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}

class SearchPublishDialog_jButton3_actionAdapter
    implements java.awt.event.ActionListener {
  SearchPublishDialog adaptee;

  SearchPublishDialog_jButton3_actionAdapter(SearchPublishDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}
