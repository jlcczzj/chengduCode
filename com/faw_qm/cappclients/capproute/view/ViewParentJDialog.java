package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import javax.swing.*;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.part.model.*;
import java.util.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import java.awt.event.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 *   //  CC by leixiao 2008-7-31 原因：解放升级工艺路线
 */
public class ViewParentJDialog
    extends JDialog {
  private QMPartMasterIfc part;
  private String listID;
  JPanel panel1 = new JPanel();
  JLabel tipLabel = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  CappMultiList cappMultiList1 = new CappMultiList();
  JButton quiqButton = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public ViewParentJDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setPartMaster(QMPartMasterIfc part,String listID) {
    this.part = part;
    this.listID = listID;
    this.setTitle("零部件<"+part.getPartNumber()+"("+part.getPartName()+")>的上级部件和路线");
    this.tipLabel.setText("使用零部件<"+part.getPartNumber()+"("+part.getPartName()+")>的部件") ;
    initMultilist();
  }

  private void initMultilist() {
    Class[] params = {
        QMPartMasterIfc.class,String.class};
    Object[] values = {
        part,this.listID};
    Vector result = null;
    try {
      result = (Vector) RouteListTaskJPanel.invokeRemoteMethodWithException(this,
          "TechnicsRouteService", "findParentsAndRoutes", params, values);
    }
    catch (QMRemoteException ex) {
      //输出异常信息：
      JOptionPane.showMessageDialog(this, ex.getClientMessage(), "错误",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }
    int size = result.size();
    for (int i = 0; i < size; i++) {
      int row = this.cappMultiList1.getNumberOfRows();
      Object[] objs = (Object[]) result.elementAt(i);
      QMPartInfo parent = (QMPartInfo) objs[0];
      PartUsageLinkIfc link = (PartUsageLinkIfc) objs[1];
      TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) objs[2];
      String branch = (String) objs[3];
      cappMultiList1.addTextCell(row, 0, parent.getBsoID());
      cappMultiList1.addTextCell(row, 1, new Integer(i).toString());
      cappMultiList1.addTextCell(row, 2, parent.getPartNumber());
      cappMultiList1.addTextCell(row, 3, parent.getPartName());
      cappMultiList1.addTextCell(row, 4, parent.getVersionValue());
      cappMultiList1.addTextCell(row, 5, new Float(link.getQuantity()).toString());
      if (routelist != null)
        cappMultiList1.addTextCell(row, 6,
                                   routelist.getRouteListNumber() + "(" +
                                   routelist.getRouteListName() + ")" +
                                   routelist.getVersionValue());
        else
         cappMultiList1.addTextCell(row, 6,"");
      if (branch != null)
        cappMultiList1.addTextCell(row, 7, branch);
        else
        cappMultiList1.addTextCell(row, 7, "");
    }
  }

  public ViewParentJDialog() {
    this(null, "", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    tipLabel.setText("jLabel1");
    quiqButton.setText("退出");
    quiqButton.addActionListener(new ViewParentJDialog_quiqButton_actionAdapter(this));
    getContentPane().add(panel1);
    panel1.add(tipLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 8, 0), 366, 0));
    panel1.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 0, 0, 8), 193, 71));
    panel1.add(quiqButton,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 8, 8, 8), 16, 0));
    jScrollPane1.getViewport().add(cappMultiList1, null);
    cappMultiList1.setHeadings(new String[] {"id", "序号", "编号", "名称", "版本",
                               "数量", "路线表", "路线"});
    cappMultiList1.setRelColWidth(new int[] {0, 1, 1, 1, 1, 1, 1, 1});
    cappMultiList1.setCellEditable(false);

  }

  void quiqButton_actionPerformed(ActionEvent e) {
   this.dispose();
  }
}

class ViewParentJDialog_quiqButton_actionAdapter implements java.awt.event.ActionListener {
  ViewParentJDialog adaptee;

  ViewParentJDialog_quiqButton_actionAdapter(ViewParentJDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.quiqButton_actionPerformed(e);
  }
}
