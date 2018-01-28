/**
 * ����ResourceReplaceDialog.java 1.0 2008.01.14
 * ��Ȩ��һ��������˾����
 * ����������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title:ѡ������Ĺ�����棨Ϊ���һ���� </p>
 * <p>Description: ѡ��Ҫ�����Ĺ���</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:һ������ </p>
 * @author  ������
 * @version 1.0
 */

public class SelectProcedureJDialog
    extends JDialog {
  //�б�
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
   *���캯��
   * @param frame ������
   * @param title ���ڱ���
   * @param modal �Ƿ�ʹ�������ڿ��Լ���
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
   *���캯��
   */
  public SelectProcedureJDialog() {
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
   * ȷ����ť����¼�
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
   * added by dikefeng 20090706���жϹ����Ƿ���Թ����ù���
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
    //����Ǹ���״̬
    if (panel.getViewMode() == panel.UPDATE_MODE) {
      currentTechnics = (QMFawTechnicsInfo) panel.getSelectedParentTechnics();
    }
    if (ccP.getRelationCardBsoID() != null &&
        ccP.
        getRelationCardBsoID().equalsIgnoreCase(currentTechnics.getBsoID())) {
      JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                    "�ù�����ڵ�ԭ���յĹ���,���Բ�������ӹ�����",
                                    "��ʾ",
                                    JOptionPane.
                                    INFORMATION_MESSAGE);
      return false;

    }
    //��õ�ǰ���յ����й���
    Class[] paraClass1 = {
        String.class, String.class};
    Object[] objs1 = {
        currentTechnics.getBsoID(), currentTechnics.getBsoID()};
//���Ŀ�깤�յ����й���
    Collection currentCol = null;
    try {
      currentCol = (Collection) TechnicsAction.useServiceMethod(
          "StandardCappService", "browseProcedures", paraClass1, objs1);
    }
    catch (QMRemoteException ex) {
        ex.printStackTrace();
    }
    //���浱ǰ���հ��������й����id�Լ������
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
    //���Ŀ�깤���е����й���
    Class[] paraClass = {
        String.class,String.class};
    Object[] objs = {
        techifc.getBsoID(),techifc.getBsoID()};
    //���Ŀ�깤�յ����й���
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
                                        "Ŀ�깤���й���:" + procedure.getStepNumber() +
                                        "���ڵ������յĹ���,���Բ�������ӹ�����",
                                        "��ʾ",
                                        JOptionPane.
                                        INFORMATION_MESSAGE);
          return false;
        }
        if (procedure.getName() != null &&
            procedure.getName().trim().length() > 0 &&
            currentProcedureID.indexOf(procedure.getName()) >= 0) {
          System.out.println("Ŀ�깤����");
          JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                        "Ŀ�깤���й���:" + procedure.getStepNumber() +
                                        "���ڵ��������й���" +
                                        hash.get(procedure.getName()) +
                                        "�Ĺ���,���Բ�������ӹ�����",
                                        "��ʾ",
                                        JOptionPane.
                                        INFORMATION_MESSAGE);
          return false;

        }
      }
    }

    return flag;
  }
  
  /**
   * ȡ����ť����¼�
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

}
