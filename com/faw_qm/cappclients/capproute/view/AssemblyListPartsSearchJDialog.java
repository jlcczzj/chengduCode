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
 * <p>Title:按 工艺合件（路线）申请单 搜索零部件 </p>
 * <p>Description: 只填加 解放、变速箱发布状态的零部件</p>
 * <p>Copyright: Copyright (c) 2015-5-26</p>
 * <p>Company: </p>
 * @author 刘楠
 * @version 1.0
 * SS1 A004-2016-3436 新增已废弃状态的支持 liunan 2016-12-21
 */
public class AssemblyListPartsSearchJDialog extends JDialog
{
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
  private static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";
  
  private Frame parentFrame = null;
  
  /**
   * 构造函数
   * @param frame 父窗口
   */
  public AssemblyListPartsSearchJDialog(JFrame frame)
  {
    super(frame);
    try
    {
      jbInit();
      this.parentFrame=frame;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private void jbInit() throws Exception
  {
  	this.setTitle("按工艺合件（路线）申请单添加零件");
    this.getContentPane().setLayout(gridBagLayout4);
    jPanel2.setBorder(null);
    jPanel2.setLayout(gridBagLayout2);
    jPanel1.setBorder(null);
    jPanel1.setLayout(gridBagLayout3);
    jLabel1.setMaximumSize(new Dimension(350, 18));
    jLabel1.setMinimumSize(new Dimension(350, 18));
    jLabel1.setPreferredSize(new Dimension(350, 18));
    jLabel1.setText("搜索工艺合件（路线）申请单");
    jTextField1.setMaximumSize(new Dimension(80, 22));
    jTextField1.setMinimumSize(new Dimension(80, 22));
    jTextField1.setPreferredSize(new Dimension(80, 22));
    jTextField1.setText("");
    jButton1.setMaximumSize(new Dimension(65, 22));
    jButton1.setMinimumSize(new Dimension(65, 22));
    jButton1.setPreferredSize(new Dimension(65, 22));
    jButton1.setText("搜索");
    jButton1.addActionListener(new
                               AssemblyListPartsSearchJDialog_jButton1_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout1);
    jButton2.setMaximumSize(new Dimension(56, 23));
    jButton2.setMinimumSize(new Dimension(56, 23));
    jButton2.setPreferredSize(new Dimension(56, 23));
    jButton2.setText("全选");
    jButton2.addActionListener(new
                               AssemblyListPartsSearchJDialog_jButton2_actionAdapter(this));
    jButton3.setMaximumSize(new Dimension(56, 23));
    jButton3.setMinimumSize(new Dimension(56, 23));
    jButton3.setPreferredSize(new Dimension(56, 23));
    jButton3.setText("确定");
    jButton3.addActionListener(new
                               AssemblyListPartsSearchJDialog_jButton3_actionAdapter(this));
    jButton4.setMaximumSize(new Dimension(60, 23));
    jButton4.setMinimumSize(new Dimension(60, 23));
    jButton4.setPreferredSize(new Dimension(60, 23));
    jButton4.setText("取消");
    jButton4.addActionListener(new
                               AssemblyListPartsSearchJDialog_jButton4_actionAdapter(this));
    jLabel2.setText("");
    this.getContentPane().add(jPanel2,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 8, 0, 8), 0, 0));
    qMMultiList.setHeadings(new String[] {"id", "编号", "名称", "版本", "视图","partid","状态"});
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 0,1});
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

  private void setViewLocation()
  {
    this.setSize(560, 450);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
    {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width)
    {
      frameSize.width = screenSize.width;
    }
    this.setLocation( (screenSize.width - frameSize.width) / 2,
                     (screenSize.height - frameSize.height) / 2);
    this.setVisible(true);
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
    qMMultiList.clear();
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    Class[] string = {String.class};
    Object[] value = {jTextField1.getText()};
    Vector partsColl = new Vector();
    try
    {
      partsColl = (Vector) CappRouteAction.useServiceMethod("AssemblyListService", "findPartByAssemblyListNumber", string, value);
    }
    catch (QMRemoteException ex)
    {
      JOptionPane.showMessageDialog(this, ex.getClientMessage());
      return;
    }
    if (partsColl.size() == 0)
    {
      this.setCursor(Cursor.getDefaultCursor());
      jLabel2.setText("0个搜索结果");
      return;
    }
    
    int j = 0;
    for (int i = 0; i < partsColl.size(); i++)
    {
      QMPartIfc part = (QMPartIfc) partsColl.elementAt(i);
      String lcs = part.getLifeCycleState().getDisplay();
      //SS1
      if(!lcs.equals("已废弃")&&!lcs.equals("废弃")&&!lcs.equals("前准")&&!lcs.equals("试制")&&!lcs.equals("生产准备")&&!lcs.equals("生产")&&!lcs.equals("变速箱试制")&&!lcs.equals("变速箱生产准备")&&!lcs.equals("变速箱生产"))
      {
      	continue;
      }
      qMMultiList.addTextCell(i, 0, part.getMasterBsoID());
      qMMultiList.addTextCell(i, 1, part.getPartNumber());
      qMMultiList.addTextCell(i, 2, part.getPartName());
      qMMultiList.addTextCell(i, 3, part.getVersionValue());
      qMMultiList.addTextCell(i, 4, part.getViewName());
      qMMultiList.addTextCell(i, 5, part.getBsoID());
      qMMultiList.addTextCell(i, 6, lcs);
      j++;
    }
    jLabel2.setText(partsColl.size() + "个搜索结果，发布和废弃状态零件数为："+j);
    this.setCursor(Cursor.getDefaultCursor());
  }

  void jButton2_actionPerformed(ActionEvent e)
  {
    if (qMMultiList.getNumberOfRows() > 0) {
      qMMultiList.selectAll();
    }

  }

  void jButton4_actionPerformed(ActionEvent e)
  {

    this.dispose();
  }

  void jButton3_actionPerformed(ActionEvent e)
  {
    Vector selectedPartMastersColl = new Vector();
    
		String str = "工艺合件（路线）申请单," + jTextField1.getText().replaceAll("\\*","");
		selectedPartMastersColl.addElement(str);
		
    QMPartMasterIfc partMasterIfc;
    QMPartIfc partIfc; 
    int[] indexes = qMMultiList.getSelectedRows();
    for (int i = 0; i < indexes.length; i++)
    {
      int x = indexes[i];
      if (x != -1)
      {
        String masterbsoid = qMMultiList.getCellAt(x, 0).toString();
        String partid=qMMultiList.getCellAt(x, 5).toString();
        Class[] string = {String.class};
        String[] bsoId = {masterbsoid};
        Class[] string1 = {String.class};
        String[] bsoId1 = {partid};
        try
        {
          partMasterIfc = (QMPartMasterIfc) CappRouteAction.useServiceMethod("PersistService", "refreshInfo", string, bsoId);
          partIfc = (QMPartIfc) CappRouteAction.useServiceMethod("PersistService", "refreshInfo", string1, bsoId1);
          Object [] part={partMasterIfc,partIfc};
          selectedPartMastersColl.addElement(part);
       
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

class AssemblyListPartsSearchJDialog_jButton1_actionAdapter
    implements java.awt.event.ActionListener {
  AssemblyListPartsSearchJDialog adaptee;

  AssemblyListPartsSearchJDialog_jButton1_actionAdapter(AssemblyListPartsSearchJDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class AssemblyListPartsSearchJDialog_jButton2_actionAdapter
    implements java.awt.event.ActionListener {
  AssemblyListPartsSearchJDialog adaptee;

  AssemblyListPartsSearchJDialog_jButton2_actionAdapter(AssemblyListPartsSearchJDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class AssemblyListPartsSearchJDialog_jButton4_actionAdapter
    implements java.awt.event.ActionListener {
  AssemblyListPartsSearchJDialog adaptee;

  AssemblyListPartsSearchJDialog_jButton4_actionAdapter(AssemblyListPartsSearchJDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}

class AssemblyListPartsSearchJDialog_jButton3_actionAdapter
    implements java.awt.event.ActionListener {
  AssemblyListPartsSearchJDialog adaptee;

  AssemblyListPartsSearchJDialog_jButton3_actionAdapter(AssemblyListPartsSearchJDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}
