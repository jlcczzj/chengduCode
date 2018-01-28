package com.faw_qm.cappclients.capp.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

public class SearchPartUseTechLinkJDialog
    extends JDialog {
  private QMFawTechnicsIfc ifc;
  private PaintPartSubListJFrame jframe;
  public SearchPartUseTechLinkJDialog(JFrame frame, QMFawTechnicsIfc ifc) {
    super(frame, "", true);
    jframe = (PaintPartSubListJFrame) frame;
    this.ifc = ifc;
    try {
      jbInit();
    }
    catch (Exception ex) {
    }
  }

  //�б�
  private ComponentMultiList comlist;
//  private QMFawTechnicsIfc techifc = null;
//  private TechnicsStepJPanel panel = null;
  private JPanel upPanel = new JPanel();
  private JPanel topPanel = new JPanel();
  private JPanel downPanel = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();
  private JButton searchButton = new JButton();
  private JTextField searchTextField = new JTextField();
  private JLabel partLabel = new JLabel();

  //��������
  private HashMap map = new HashMap();

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
    this.setTitle("����Ҫ�޸ĵ����");
    this.getContentPane().setLayout(gridBagLayout1);
    comlist = new ComponentMultiList();
    String[] heading = {
        "linkID", "������", "�������", "�汾"};
    comlist.setHeadings(heading);
    // ����ÿ����ʾ������
    int[] bsoListHeadsWidth = {
        0, 1, 1, 1};
    comlist.setRelColWidth(bsoListHeadsWidth);
    // �����б���еĶ��뷽ʽ��
    String[] leftAlign = {
        "left", "left", "left", "left"};
    comlist.setColumnAlignments(leftAlign);
    // ���ø��б�Ϊ�ɶ�ѡ��("Shift"����ѡ)
    comlist.setMultipleMode(true);
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
    upPanel.setLayout(gridBagLayout4);
    topPanel.setLayout(gridBagLayout2);
    downPanel.setLayout(gridBagLayout3);
    okButton.setForeground(Color.black);
    okButton.setActionCommand("okButton");
    okButton.setText("ȷ��(O)");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    okButton.setMnemonic('O');
    searchButton.setText("����(S)");
    searchButton.setMaximumSize(new Dimension(81, 29));
    searchButton.setMinimumSize(new Dimension(81, 29));
    searchButton.setPreferredSize(new Dimension(81, 29));
    searchButton.setMnemonic('S');
    cancelButton.setText("ȡ��(C)");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    cancelButton.setMnemonic('C');
    partLabel.setOpaque(false);
    partLabel.setText("������:");
    searchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchButton_actionPerformed(e);
      }
    });
    this.getContentPane().add(topPanel,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 8.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, -1, 0, 1), 0, 0));
    topPanel.add(comlist, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));

    this.getContentPane().add(downPanel,
                              new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, -1, 0, 1), 0, 0));
    this.getContentPane().add(upPanel,
                              new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, -1, 0, 1), 0, 0));
    upPanel.add(partLabel, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(0, 8, 0, 0), 0, 0));
    upPanel.add(searchTextField, new GridBagConstraints(1, 0, 1, 1, 4.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 8, 0, 0), 0, 0));
    upPanel.add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0.1, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 8), 0, 0));

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
   * ������ť����
   * @param e ActionEvent
   */
  void searchButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    String partnumber = searchTextField.getText();
    Vector returnvector = new Vector();
    map.clear();
    int rowcount = comlist.getRowCount();
    if (rowcount > 0) {
      for (int i = 1; i <= rowcount; i++) {
        comlist.removeRow(rowcount - i);
      }
    }
    try {
      RequestServer server = RequestServerFactory.getRequestServer();
      ServiceRequestInfo info1 = new ServiceRequestInfo();
      info1.setServiceName("StandardCappService");
      info1.setMethodName("findTechnicsPartLink");
      Class[] paraClass1 = {
          String.class,
          QMFawTechnicsIfc.class};
      info1.setParaClasses(paraClass1);
      Object[] objs1 = {
          partnumber,
          ifc};
      info1.setParaValues(objs1);
      returnvector = (Vector) server.request(info1);
      if (returnvector != null && returnvector.size() > 0) {
        Iterator iterator = returnvector.iterator();
        for (int row = 0; iterator.hasNext(); row++) {
          BaseValueIfc[] objs = (BaseValueIfc[]) iterator.next();
          PartUsageQMTechnicsLinkIfc linkifc = (PartUsageQMTechnicsLinkIfc)
              objs[0];
          QMPartIfc partifc = (QMPartIfc) objs[1];
          QMPartMasterIfc masterifc = (QMPartMasterIfc) objs[2];
          comlist.addTextCell(row, 0, linkifc.getBsoID());
          map.put(linkifc.getBsoID(), linkifc);
          comlist.addTextCell(row, 1, masterifc.getPartNumber());
          comlist.addTextCell(row, 2, masterifc.getPartName());
          comlist.addTextCell(row, 3, partifc.getVersionValue());
        }
      }
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * tangshutao 2008.03.22 ��Ҫ���Ļ�ɾ���Ĺ�����ȥ�����ɾ����Ҫ��Ȼ���޸Ĳ���
   * @param vec Vector
   */
  private void deleteTechPartLink(Vector vec) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
      RequestServer server = RequestServerFactory.getRequestServer();
      ServiceRequestInfo info = new ServiceRequestInfo();
      info.setServiceName("StandardCappService");
      info.setMethodName("deleteTechnicsPartLink");
      Class[] paraClass = {
          Vector.class};
      info.setParaClasses(paraClass);
      Object[] objs = {
          vec};
      info.setParaValues(objs);
      server.request(info);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }
    setCursor(Cursor.getDefaultCursor());

  }

  /**
   * ȷ����ť�����¼�
   * @param e ActionEvent
   */
  void okButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    int[] rows = comlist.getSelectedRows();
    if (rows != null && rows.length > 0) {
      Vector selectvector = new Vector();
      for (int i = 0; i < rows.length; i++) {
        String s = comlist.getStringValue(rows[i], 0);
        selectvector.add(map.get(s));
      }
      jframe.insertPartLinkToTable(selectvector);
//      2009.05.22�����ע��
      deleteTechPartLink(selectvector);
    }
    setCursor(Cursor.getDefaultCursor());
    this.dispose();
  }

  /**
   *  ȡ����ť�����¼�
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

}
