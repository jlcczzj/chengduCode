package com.faw_qm.cappclients.summary.view;

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

/**
 * <p>Title:ѡ������Ĺ�����棨Ϊ���һ���� </p>
 * <p>Description: ѡ��Ҫ�����Ĺ���</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:һ������ </p>
 * @author  ������
 * @version 1.0
 */

public class SummarySelectProcedureJDialog
    extends JDialog {
  //�б�
  ComponentMultiList comlist;
  HashMap map = new HashMap();
  QMFawTechnicsIfc techifc = null;
  JPanel panel = null;
  JPanel topPanel = new JPanel();
  JPanel downPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  /**
   *���캯��
   * @param frame ������
   * @param title ���ڱ���
   * @param modal �Ƿ�ʹ�������ڿ��Լ���
   */
  public SummarySelectProcedureJDialog(Frame frame, String title, boolean modal,
                                       QMFawTechnicsIfc techifc,
                                       JPanel panel) {
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
   *���캯��
   */
  public SummarySelectProcedureJDialog() {
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
   * ���ظ��෽��
   * @param flag
   */
  public void setVisible(boolean flag) {
    setViewLocation();
    super.setVisible(flag);
  }

  /**�����ʼ��*/
  private void jbInit() throws Exception {
    this.setSize(650, 600);
    this.setTitle("ѡ��" + techifc.getTechnicsNumber() + "�µĹ���");
    this.getContentPane().setLayout(gridBagLayout1);
    comlist = new ComponentMultiList();
    String[] heading = {
        "bsoid",
        "����"};
    comlist.setHeadings(heading);
    // ����ÿ����ʾ������
    int[] bsoListHeadsWidth = {
        0,
        1};
    comlist.setRelColWidth(bsoListHeadsWidth);
    // �����б���еĶ��뷽ʽ��
    String[] leftAlign = {
        "left", "left"};
    comlist.setColumnAlignments(leftAlign);
    // ���ø��б�Ϊ���ɶ�ѡ��("Shift"����ѡ)
    comlist.setMultipleMode(false);
    // ����������
    comlist.setAllowSorting(true);
    // ��������б��е����ݽ��б༭
    comlist.setCellEditable(false);

    /**
     * Ӧ�ó���رճ���
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
    okButton.setText("ȷ��");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    cancelButton.setText("ȡ��");
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
   * �ر�Ӧ�ó���
   * @param e �����¼�
   * �Ի���ر�֮��Ҫ����
   */
  public void this_windowClosing(WindowEvent e) {
    dispose();
  }

  /**
   * ���������table��
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
            "browseProceduresByTechnics",
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
   * ȷ����ť����¼�
   * @param e ActionEvent
   */
  void okButton_actionPerformed(ActionEvent e) {
    if (comlist.getSelectedRow() >= 0) {
      int row = comlist.getSelectedRow();
      String bsoid = comlist.getStringValue(row, 0);
      QMProcedureIfc proceifc = (QMProcedureIfc) map.get(bsoid);
          if (panel instanceof MPTPanel) {
            ( (MPTPanel) panel).setProcedureIfc(proceifc);
          }
          if (panel instanceof RPTPanel) {
           ( (RPTPanel) panel).setProcedureIfc(proceifc);
          }
      this.dispose();
    }
  }

  /**
   * ȡ����ť����¼�
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

}
