package com.faw_qm.cappclients.summary.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMFawTechnicsMasterInfo;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.version.model.MasteredIfc;
//tangshutao 2008.01.18
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import javax.swing.JPanel;

/**
 * <p>Title: 按基本属性搜索典型工艺界面</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:一汽启明</p>
 * @author
 * @version 1.0
 */

public class SummaryTechnicsSearchJDialog
    extends JDialog {
  /**查询类*/
  private CappQuery cappQuery = new CappQuery();

  /**要查询的业务对象*/
  public static String SCHEMA;

  /**查询方案*/
  private CappSchema mySchema;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  //tangshutao 汇总主界面
  private SummaryMainJFrame summaryJFrame;

  /**用于标记资源文件路径*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capp.util.CappLMRB";

  /**测试变量*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /**
   * 是否实施TS16949属性
   */
  private static boolean ts16949 = (RemoteProperty.getProperty(
      "TS16949", "true")).equals("true");
  private JPanel panel;
  JFrame frame;
  /**
   * 构造方法
   * @param frame  工艺浏览器主界面
   */
  public SummaryTechnicsSearchJDialog(JFrame frame, JPanel panel) throws
      QMRemoteException {
    super(frame, "", true);
    this.frame = frame;
    this.panel = panel;
    if (ts16949) {
      SCHEMA = "C:QMFawTechnics; G:搜索条件;A:productState;A:technicsNumber;A:technicsName;A:technicsType;A:processFlowNum;A:controlPlanNum;A:femaNum;A:taskInstructNum;E:technicsType;";
    }
    else {
      SCHEMA = "C:QMFawTechnics; G:搜索条件;A:technicsNumber;A:technicsName;A:technicsType;E:technicsType;";
      //定义查询方案
    }
    mySchema = new CappSchema(SCHEMA);
    //设置查询方案
    cappQuery.setSchema(mySchema);
    cappQuery.notAcessInPersonalFolder();
    cappQuery.setLastIteration(true);
    try {
      cappQuery.setMultipleMode(false);
    }
    catch (PropertyVetoException ex) {
    }
    cappQuery.addExttrPanel("QMFawTechnics", "technicsType");
    //tangshutao 2008.01.18
    if (frame instanceof SummaryMainJFrame) {
      summaryJFrame = (SummaryMainJFrame) frame;
    }
    //tangshutao end
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 初始化
   * @throws Exception
   */
  private void jbInit() throws Exception {
    setTitle("搜索工艺规程");
    setSize(850, 750);
    getContentPane().setLayout(gridBagLayout1);
    //界面布局管理
    getContentPane().add(cappQuery,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));

  }

  /**
   * 添加默认查询监听
   */
  public void addDefaultListener() {
    cappQuery.addListener(new CappQueryListener() {
      public void queryEvent(CappQueryEvent e) {
        cappQuery_queryEvent(e);
      }
    });
  }

  /**
   * 添加查询监听
   * @param s
   */
  public void addQueryListener(CappQueryListener s) {
    cappQuery.addListener(s);
  }

  /**
   * 删除查询监听
   * @param s
   */
  public void removeQueryListener(CappQueryListener s) {
    cappQuery.removeListener(s);
  }

  /**
   * 搜索工艺监听事件方法
   * @param e 搜索监听事件
   */
  public void cappQuery_queryEvent(CappQueryEvent e) {
    if (e.getType().equals(CappQueryEvent.COMMAND)) {
      if (e.getCommand().equals(CappQuery.OkCMD)) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CappQuery c = (CappQuery) e.getSource();
        BaseValueIfc bvi = c.getSelectedDetail();
        setVisible(false);
        if (bvi instanceof QMFawTechnicsIfc) {
          //tangshutao 2008.01.21
          SummarySelectProcedureJDialog dialog = new
              SummarySelectProcedureJDialog(frame, "", true,
                                            (QMFawTechnicsIfc) bvi, panel);
          dialog.initTable();
          dialog.setVisible(true);
         //end
        }
        setCursor(Cursor.getDefaultCursor());
      }
      if (e.getCommand().equals(CappQuery.QuitCMD)) {
        setVisible(false);
      }
    }
  }

  /**
   * 给定工艺主信息，获得工艺卡的所有版本中的最新小版本
   * @param masterInfo 工艺主信息
   * @return 工艺卡的所有版本中的最新小版本
   */
  private QMFawTechnicsInfo getLastedVersion(QMFawTechnicsMasterInfo
                                             masterInfo) {
    if (verbose) {
      System.out.println(
          "cappclients.capp.view.TechnicsSearchJDialog:getLastedIterations() begin...");
    }
    QMFawTechnicsInfo technicsInfo = null;
    //调用服务方法，获得工艺卡的所有小版本（包括不同分枝）
    ServiceRequestInfo info1 = new ServiceRequestInfo();
    Class[] paraClass = {
        MasteredIfc.class};
    Object[] objs = {
        masterInfo};
    Collection collection = null;
    try {
      collection = (Collection) TechnicsAction.useServiceMethod(
          "VersionControlService", "allIterationsOf", paraClass, objs);
    }
    catch (QMRemoteException ex) {
      if (verbose) {
        ex.printStackTrace();
      }
      String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
      JOptionPane.showMessageDialog(this,
                                    ex.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
    }

    Iterator iterator = collection.iterator();
    if (iterator.hasNext()) {
      //获得工艺卡的最新小版本
      technicsInfo = (QMFawTechnicsInfo) iterator.next();
    }
    if (verbose) {
      System.out.println("cappclients.capp.view.TechnicsSearchJDialog:getLastedIterations() end...return " +
                         technicsInfo);
    }
    return technicsInfo;
  }

  /**
   * 设置列表为单选模式
   */
  public void setSingleSelectMode() {
    try {
      cappQuery.setMultipleMode(false);
    }
    catch (PropertyVetoException ex) {
      ex.printStackTrace();
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
   * 重载父类方法，使界面显示在屏幕中央
   * @param flag
   */
  public void setVisible(boolean flag) {
    setViewLocation();
    super.setVisible(flag);
  }

  public void addMultiListActionListener(ActionListener lis) {
    cappQuery.addMultiListActionListener(lis);
  }

}
