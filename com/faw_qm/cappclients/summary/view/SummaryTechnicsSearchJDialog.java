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
 * <p>Title: �����������������͹��ս���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:һ������</p>
 * @author
 * @version 1.0
 */

public class SummaryTechnicsSearchJDialog
    extends JDialog {
  /**��ѯ��*/
  private CappQuery cappQuery = new CappQuery();

  /**Ҫ��ѯ��ҵ�����*/
  public static String SCHEMA;

  /**��ѯ����*/
  private CappSchema mySchema;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  //tangshutao ����������
  private SummaryMainJFrame summaryJFrame;

  /**���ڱ����Դ�ļ�·��*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capp.util.CappLMRB";

  /**���Ա���*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /**
   * �Ƿ�ʵʩTS16949����
   */
  private static boolean ts16949 = (RemoteProperty.getProperty(
      "TS16949", "true")).equals("true");
  private JPanel panel;
  JFrame frame;
  /**
   * ���췽��
   * @param frame  ���������������
   */
  public SummaryTechnicsSearchJDialog(JFrame frame, JPanel panel) throws
      QMRemoteException {
    super(frame, "", true);
    this.frame = frame;
    this.panel = panel;
    if (ts16949) {
      SCHEMA = "C:QMFawTechnics; G:��������;A:productState;A:technicsNumber;A:technicsName;A:technicsType;A:processFlowNum;A:controlPlanNum;A:femaNum;A:taskInstructNum;E:technicsType;";
    }
    else {
      SCHEMA = "C:QMFawTechnics; G:��������;A:technicsNumber;A:technicsName;A:technicsType;E:technicsType;";
      //�����ѯ����
    }
    mySchema = new CappSchema(SCHEMA);
    //���ò�ѯ����
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
   * ��ʼ��
   * @throws Exception
   */
  private void jbInit() throws Exception {
    setTitle("�������չ��");
    setSize(850, 750);
    getContentPane().setLayout(gridBagLayout1);
    //���沼�ֹ���
    getContentPane().add(cappQuery,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));

  }

  /**
   * ���Ĭ�ϲ�ѯ����
   */
  public void addDefaultListener() {
    cappQuery.addListener(new CappQueryListener() {
      public void queryEvent(CappQueryEvent e) {
        cappQuery_queryEvent(e);
      }
    });
  }

  /**
   * ��Ӳ�ѯ����
   * @param s
   */
  public void addQueryListener(CappQueryListener s) {
    cappQuery.addListener(s);
  }

  /**
   * ɾ����ѯ����
   * @param s
   */
  public void removeQueryListener(CappQueryListener s) {
    cappQuery.removeListener(s);
  }

  /**
   * �������ռ����¼�����
   * @param e ���������¼�
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
   * ������������Ϣ����ù��տ������а汾�е�����С�汾
   * @param masterInfo ��������Ϣ
   * @return ���տ������а汾�е�����С�汾
   */
  private QMFawTechnicsInfo getLastedVersion(QMFawTechnicsMasterInfo
                                             masterInfo) {
    if (verbose) {
      System.out.println(
          "cappclients.capp.view.TechnicsSearchJDialog:getLastedIterations() begin...");
    }
    QMFawTechnicsInfo technicsInfo = null;
    //���÷��񷽷�����ù��տ�������С�汾��������ͬ��֦��
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
      //��ù��տ�������С�汾
      technicsInfo = (QMFawTechnicsInfo) iterator.next();
    }
    if (verbose) {
      System.out.println("cappclients.capp.view.TechnicsSearchJDialog:getLastedIterations() end...return " +
                         technicsInfo);
    }
    return technicsInfo;
  }

  /**
   * �����б�Ϊ��ѡģʽ
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
   * ���ý������ʾλ��
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
   * ���ظ��෽����ʹ������ʾ����Ļ����
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
