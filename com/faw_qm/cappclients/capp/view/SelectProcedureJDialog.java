/**
 * 程序ResourceReplaceDialog.java 1.0 2008.01.14
 * 版权归一汽启明公司所有
 * 本程序属于一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.view;

import javax.swing.*;
import java.awt.*;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import java.util.Vector;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import java.util.Iterator;
import com.faw_qm.capp.model.QMProcedureIfc;
import java.awt.event.*;
import java.util.HashMap;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
//CCBegin by dikefeng 20090706
import java.util.*;
//CCEnd by dikefeng 20090706

/**
 * <p>Title:选择关联的工序界面（为零件一览表） </p>
 * <p>Description: 选择要关联的工序</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:一汽启明 </p>
 * @author  唐树涛
 * @version 1.0
 */

public class SelectProcedureJDialog
    extends JDialog {
  //列表
  ComponentMultiList comlist;
  HashMap map = new HashMap();
  QMFawTechnicsIfc techifc = null;
  TechnicsStepJPanel panel = null;
  JPanel topPanel = new JPanel();
  JPanel downPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  /**
   *构造函数
   * @param frame 父窗口
   * @param title 窗口标题
   * @param modal 是否使其他窗口可以激活
   */
  public SelectProcedureJDialog(Frame frame, String title, boolean modal,
                                QMFawTechnicsIfc techifc,
                                TechnicsStepJPanel panel) {
    super(frame, title, modal);
    this.techifc = techifc;
    this.panel = panel;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *构造函数
   */
  public SelectProcedureJDialog() {
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
    setViewLocation();
    super.setVisible(flag);
  }

  /**组件初始化*/
  private void jbInit() throws Exception {
    this.setSize(650, 600);
    this.setTitle("选择" + techifc.getTechnicsNumber() + "下的工序");
    this.getContentPane().setLayout(gridBagLayout1);
    comlist = new ComponentMultiList();
    String[] heading = {
        "bsoid",
        "工序"};
    comlist.setHeadings(heading);
    // 设置每列显示比例：
    int[] bsoListHeadsWidth = {
        0,
        1};
    comlist.setRelColWidth(bsoListHeadsWidth);
    // 设置列表的列的对齐方式：
    String[] leftAlign = {
        "left", "left"};
    comlist.setColumnAlignments(leftAlign);
    // 设置该列表为不可多选：("Shift"键多选)
    comlist.setMultipleMode(false);
    // 允许按列排序：
    comlist.setAllowSorting(true);
    // 不允许对列表中的内容进行编辑
    comlist.setCellEditable(false);

    /**
     * 应用程序关闭程序
     */
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    topPanel.setLayout(gridBagLayout2);
    downPanel.setLayout(gridBagLayout3);
    okButton.setForeground(Color.black);
    okButton.setActionCommand("okButton");
    okButton.setText("确定");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    cancelButton.setText("取消");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    this.getContentPane().add(topPanel,
                              new GridBagConstraints(0, 0, 1, 1, 1.0, 9.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, -1, 0, 1), 0, 0));
    topPanel.add(comlist, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));

    this.getContentPane().add(downPanel,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, -1, 0, 1), 0, 0));
    downPanel.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 8), 0, 0));
    downPanel.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 8), 0, 0));
  }

  /**
   * 关闭应用程序
   * @param e 窗口事件
   * 对话框关闭之后要解锁
   */
  public void this_windowClosing(WindowEvent e) {
    dispose();
  }

  /**
   * 将工序放入table中
   */
  public void initTable() {
    if (techifc != null) {
      Vector v = new Vector();
      Class[] lass = {
          String.class, boolean.class};
      Object[] obj = {
          techifc.getBsoID(), new Boolean(false)};
      try {
        v = (Vector) TechnicsAction.useServiceMethod("StandardCappService",
            "browseProceduresByTechnicsForCd",
            lass, obj);
      }
      catch (QMRemoteException ex) {
        ex.printStackTrace();
      }
      if (v != null && v.size() > 0) {
        int i = 0;
        for (Iterator iter = v.iterator(); iter.hasNext(); ) {
          QMProcedureIfc proifc = (QMProcedureIfc) iter.next();
          comlist.addTextCell(i, 0, proifc.getBsoID());
          comlist.addTextCell(i, 1,
                              proifc.getDescStepNumber() + "" + proifc.getStepName());
          map.put(proifc.getBsoID(), proifc);
          i++;
        }
      }

    }
  }

  /**
   * 确定按钮点击事件
   * @param e ActionEvent
   */
  void okButton_actionPerformed(ActionEvent e) {
    if (comlist.getSelectedRow() >= 0) {
      int row = comlist.getSelectedRow();
      String bsoid = comlist.getStringValue(row, 0);
      QMProcedureIfc proceifc = (QMProcedureIfc) map.get(bsoid);
      //CCBegin by dikefeng 20090706
      QMFawTechnicsIfc currentTech = null;

      if(isProcedureCanbeRelated(proceifc))
      {
        panel.relatedProcedure = proceifc;
        panel.technicsinfo = (QMFawTechnicsInfo) techifc;
        panel.setRelationJTextField();
      }
      //CCBegin by dikefeng 20090706
//      panel.relatedProcedure = proceifc;
//      panel.technicsinfo = (QMFawTechnicsInfo)techifc;
//      panel.setRelationJTextField();
      this.dispose();
    }
  }

  /**
   * added by dikefeng 20090706，判断工序是否可以关联该工序
   * @param e ActionEvent
   */
  private boolean isProcedureCanbeRelated(QMProcedureIfc ccP) {
    boolean flag = true;
    QMFawTechnicsInfo currentTechnics = null;
    if (panel.getViewMode() == panel.CREATE_MODE) {
      currentTechnics = (QMFawTechnicsInfo) panel.getSelectedTreeNode().
          getObject().
          getObject();
    }
    //如果是更新状态
    if (panel.getViewMode() == panel.UPDATE_MODE) {
      currentTechnics = (QMFawTechnicsInfo) panel.getSelectedParentTechnics();
    }
    if (ccP.getRelationCardBsoID() != null &&
        ccP.
        getRelationCardBsoID().equalsIgnoreCase(currentTechnics.getBsoID())) {
      JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                    "该工序存在到原工艺的关联,所以不允许添加关联！",
                                    "提示",
                                    JOptionPane.
                                    INFORMATION_MESSAGE);
      return false;

    }
    //获得当前工艺的所有工序
    Class[] paraClass1 = {
        String.class, String.class};
    Object[] objs1 = {
        currentTechnics.getBsoID(), currentTechnics.getBsoID()};
//获得目标工艺的所有工序
    Collection currentCol = null;
    try {
      currentCol = (Collection) TechnicsAction.useServiceMethod(
          "StandardCappService", "browseProcedures", paraClass1, objs1);
    }
    catch (QMRemoteException ex) {
        ex.printStackTrace();
    }
    //缓存当前工艺包含的所有工序的id以及工序号
    String currentProcedureID = "";
    HashMap hash = new HashMap();
    System.out.println("");
    if (currentCol != null) {
      Iterator currentIte = currentCol.iterator();
      while (currentIte.hasNext()) {
        QMProcedureIfc pro = (QMProcedureIfc) currentIte.next();
        currentProcedureID = currentProcedureID + pro.getBsoID() + ";";
        hash.put(pro.getBsoID(), new Integer(pro.getStepNumber()));
      }
    }
    //获得目标工艺中的所有工序
    Class[] paraClass = {
        String.class,String.class};
    Object[] objs = {
        techifc.getBsoID(),techifc.getBsoID()};
    //获得目标工艺的所有工序
    Collection collection = null;
    try {
      collection = (Collection) TechnicsAction.useServiceMethod(
          "StandardCappService", "browseProcedures", paraClass, objs);
    }
    catch (QMRemoteException ex) {
        ex.printStackTrace();
    }
    if(collection!=null){
      Iterator ite = collection.iterator();
      while (ite.hasNext()) {
        QMProcedureIfc procedure = (QMProcedureIfc) ite.next();
        if (procedure.getRelationCardBsoID()!=null&&procedure.getRelationCardBsoID().equalsIgnoreCase(currentTechnics.
            getBsoID())) {
          JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                        "目标工艺中工序:" + procedure.getStepNumber() +
                                        "存在到本工艺的关联,所以不允许添加关联！",
                                        "提示",
                                        JOptionPane.
                                        INFORMATION_MESSAGE);
          return false;
        }
        if (procedure.getName() != null &&
            procedure.getName().trim().length() > 0 &&
            currentProcedureID.indexOf(procedure.getName()) >= 0) {
          System.out.println("目标工序编号");
          JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                        "目标工艺中工序:" + procedure.getStepNumber() +
                                        "存在到本工艺中工序" +
                                        hash.get(procedure.getName()) +
                                        "的关联,所以不允许添加关联！",
                                        "提示",
                                        JOptionPane.
                                        INFORMATION_MESSAGE);
          return false;

        }
      }
    }

    return flag;
  }
  
  /**
   * 取消按钮点击事件
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

}
