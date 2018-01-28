package com.faw_qm.cappclients.summary.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.summary.view.ExportCustomizeDialog;
import com.faw_qm.cappclients.summary.view.RPTPanel;
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.summary.model.TotalResultIfc;
import com.faw_qm.framework.util.QMMessage;
import java.util.ArrayList;
import com.faw_qm.cappclients.summary.view.SummaryTechnicsSearchJDialog;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMProcedureIfc;

/**
 * <p>Title:零件一览表汇总控制类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author 唐树涛
 * @version 1.0
 */

public class SummaryRPTController
    extends SummaryController {
  public SummaryMainJFrame mainFrame;
  RPTPanel ptPanel;
  private ExportCustomizeDialog ecDialog;
  private int len;
  private TotalResultIfc totalResult;
  private WorkThread sumThread;
  private WorkThread sumThread1;
  private CappChooser qmchooser;
  private SummaryMainController mainControl;
  private SummaryTechnicsSearchJDialog searchDialog;

  /**用于标记资源文件路径*/
  private static String RESOURCE =
      "com.faw_qm.cappclients.summary.util.SummaryResource";

  /*
   ** 构造函数
   */
  public SummaryRPTController(SummaryMainJFrame mainFrame) {
    this.mainFrame = mainFrame;
    ptPanel = new RPTPanel(this);
    this.mainControl = mainFrame.mainControl;
  }

  /**
   *内部类：线程
   */
  class WorkThread
      extends Thread {
    int action;
    public WorkThread(int action) {
      this.action = action;
    }

    public void run() {
      switch (action) {
        case SEARCH:

          //搜索零部件
          searchQM();
          break;
        case SUM:
          mainFrame.setCursor(Cursor.WAIT_CURSOR);
          summary();
          mainFrame.setCursor(Cursor.getDefaultCursor());
          break;
      }
    }
  }

  /*
   ** 搜索零部件
   */
  private void searchQM() {

    try {
      searchDialog = new SummaryTechnicsSearchJDialog(mainFrame, ptPanel);
      searchDialog.addDefaultListener();
      searchDialog.setVisible(true);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }

  }

  /*
   ** 监听实现
   * 问题(2) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
   */
  public void actionPerformed(ActionEvent e) {

    //汇总按钮监听
    if (e.getActionCommand().equals("SUM")) {
      sumThread1 = new WorkThread(SUM);
      sumThread1.start();
    }
    //退出按钮监听
    if (e.getActionCommand().equals("EXIT")) {
      mainFrame.removePanel();
    }
    //搜索按钮监听
    if (e.getActionCommand().equals("SEARCH")) {
      sumThread = new WorkThread(SEARCH);
      sumThread.start();
    }
    //导出按钮监听
    if (e.getActionCommand().equals("EXPORT")) {
      String title = res.getString("export");
      File file = null;
      String str;
      String s = "";

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle(title);
      fileChooser.setApproveButtonText(title);
      int interval = fileChooser.showOpenDialog(mainFrame);
      if (interval == fileChooser.APPROVE_OPTION) {
        file = fileChooser.getSelectedFile();
        if(file!=null&&file.exists())
        {
          JOptionPane.showMessageDialog(null,"文件已存在","警告",
                        JOptionPane.WARNING_MESSAGE);
          return;
        }
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Vector vector = ptPanel.exportResult();
        exportRHJPartTable(vector,  file.getAbsolutePath());
        //问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
        JOptionPane.showMessageDialog(mainFrame,
                                      QMMessage.getLocalizedMessage(
            RESOURCE, "export success", null),
                                      QMMessage.getLocalizedMessage(
            RESOURCE,
            "hint", null), JOptionPane.INFORMATION_MESSAGE);
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }
  }
  public void exportRHJPartTable(Vector v,
                                     String filename) {
    if (!filename.endsWith(".xls")) {
      filename += ".xls";
    }
    RHJPartTableController sbqd = new RHJPartTableController();
    //v.remove(0); //去掉表列名
    sbqd.printFile(ptPanel, v, "", "",filename);
  }

  /*
   ** 调用汇总服务
   */
  public void summary() {
    ArrayList v = new ArrayList();
    if (ptPanel.getPartNum() != null) {
      QMProcedureIfc techifc = ptPanel.getProcedureIfc();
      if (techifc != null) {
        Class[] lass = {
            QMProcedureIfc.class};
        Object[] obj = {
            techifc};
        try {
          v = (ArrayList) getServiceObject("TotalService",
                                           "summaryRHJPartTable",
                                           lass, obj);
        }
        catch (QMRemoteException ex) {
          ex.printStackTrace();
        }

      }
    }
    if (v != null && v.size() > 0) {
      ptPanel.addMultiList(v);
    }

  }

  /**
   * 中止线程
   * 问题(3) 2006.08.30 徐春英修改  修改原因:中断汇总结果
   */
  public synchronized void interruptThread() {
    if (sumThread1 != null && sumThread1.isAlive()) {
      mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
      sumThread1.stop();
      sumThread1 = null;
    }
  }

  /*
   ** 得到定制输出的显示名
   */
  public String[] getAttrName() {
    return ecDialog.getAttrDisplayname();
  }

  /*
   ** 得到表头的长度
   */
  public int getHeadsCount() {
    return len;
  }

  /*
   ** 得到属性表头的个数
   */
  public int getAttrCount() {
    return ecDialog.getAttrCount();
  }

}
