/**
 * ����ResourceReplaceDialog.java 1.0 2008.01.14
 * ��Ȩ��һ��������˾����
 * ����������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.resource.util.ResourceHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.remote.*;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.part.model.QMPartIfc;
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;

/**
 * <p>Title:���հ��ṹ���������� </p>
 * <p>Description: ѡ��Ҫ��ӵ����</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:һ������ </p>
 * @author  ������
 * @version 1.0
 */

public class SonPartSelectJDialog
    extends JDialog {

  //�б�
  ComponentMultiList comlist;
  CappAssociationsPanel capppanel = null;
  BaseValueIfc object = null;
  CodingIfc techTypeifc = null;
  HashMap map = new HashMap();
  JPanel topPanel = new JPanel();
  JPanel downPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JButton selectAllButton = new JButton();
  //added by dikef
  BaseValueIfc dikef=null;
  //added by dikef end

  /**
   *���캯��
   * @param frame ������
   * @param title ���ڱ���
   * @param modal �Ƿ�ʹ�������ڿ��Լ���
   */
  public SonPartSelectJDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
    }
    catch (Exception ex) {
      if (ResourceHelper.VERBOSE) {
        ex.printStackTrace();
      }
    }
  }

  /**
   *���캯��
   */
  public SonPartSelectJDialog() {
    this(null, "", true);
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
    this.setSize(800, 650);
    this.setTitle("ѡ�����");
    this.getContentPane().setLayout(gridBagLayout1);
    comlist = new ComponentMultiList();
    String[] heading = {
        "ID",
        "������", "�������", "�汾","����","·��","����"};
    comlist.setHeadings(heading);
    // ����ÿ����ʾ������
    int[] bsoListHeadsWidth = {
        0, 3,3,1,1,4,1};
    comlist.setRelColWidth(bsoListHeadsWidth);
    // �����б���еĶ��뷽ʽ��
    String[] leftAlign = {
        "left", "left", "left", "left","left","left","left"};
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
    selectAllButton.setOpaque(true);
    selectAllButton.setActionCommand("jButton1");
    selectAllButton.setText("ȫѡ");
    selectAllButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectAllButton_actionPerformed(e);
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
    downPanel.add(selectAllButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
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
   * ȡ����ť����
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  public CodingIfc getCodingIfc() {
    return techTypeifc;
  }

  public void setCodingIfc(CodingIfc ifc) {
    techTypeifc = ifc;
  }

  public void insertPartToMultilist() {
    if (object != null ) {
      Vector v = null;
      //��������
      //String techType = techTypeifc.getBsoID();
      try {
        RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("StandardCappService");
        info1.setMethodName("getSonPartbyTechnics");
        Class[] paraClass1 = {
            QMTechnicsIfc.class};
        info1.setParaClasses(paraClass1);
        Object[] objs1 = {
            dikef};
        //System.out.println("object="+dikef.getBsoID());
        info1.setParaValues(objs1);
        v = (Vector) server.request(info1);
        //System.out.println("select result="+v.size());
        int rowd = comlist.getNumberOfRows();
        if (v != null && v.size() > 0) {
          Iterator iter = v.iterator();
          for (int row = 0; iter.hasNext(); row++) {
            Object[] cur = (Object[])iter.next();
            QMPartIfc ifc = (QMPartIfc) cur[0];
            map.put(ifc.getBsoID(), ifc);
            //QMPartMasterIfc masterifc = (QMPartMasterIfc) object[1];
            //String version = (String) object[2];
            //String routetext = (String) object[3];
            //String ishastechnics = (String) object[4];
            comlist.addTextCell(row + rowd, 0, ifc.getBsoID());
            comlist.addTextCell(row + rowd, 1, ifc.getPartNumber());
            comlist.addTextCell(row + rowd, 2, ifc.getPartName());
            comlist.addTextCell(row + rowd, 3, ifc.getVersionID());
            comlist.addTextCell(row + rowd, 4, ifc.getPartType().getDisplay());
            String routetext=(String)cur[1];
            comlist.addTextCell(row + rowd, 5, routetext.substring(routetext.indexOf(",")+1));
            String count=(String)cur[2];
            comlist.addTextCell(row + rowd, 6,count);
          }
        }
      }
      catch (QMRemoteException ex) {
        ex.printStackTrace();
      }

    }
  }

  /**
   * ȷ����ť����
   * @param e ActionEvent
   */
  void okButton_actionPerformed(ActionEvent e) {
    int[] rows = comlist.getSelectedRows();
    int rowlength=capppanel.getNumberOfRows();
    if (rows != null && rows.length > 0) {
      for (int k = 0; k < rows.length; k++) {
        String bsoid = comlist.getCellText(rows[k], 0);
        QMPartIfc part = (QMPartIfc) map.get(bsoid);
        //capppanel.addObjectToMultilist(part);
        try{
          capppanel.addObjectToRow(part, rowlength + k);
          capppanel.setCellTextValue(rowlength + k,2,comlist.getCellText(rows[k],6));
        }catch(Exception kk)
        {
          kk.printStackTrace();
        }
      }
    }
    this.dispose();
  }

  public void setCappAssociationsPanel(CappAssociationsPanel panel) {
    capppanel = panel;
  }

  public CappAssociationsPanel getCappAssociationsPanel() {
    return capppanel;
  }
  /**
   * added bydikef
   * @param ifc BaseValueIfc
   */
  public void setDikefTech(BaseValueIfc ifc) {
  dikef = ifc;
}
/**
 * added bydikef
 * @param ifc BaseValueIfc
 */

public BaseValueIfc getDikefTech() {
  return dikef;
}

  public void setBaseValueIfc(BaseValueIfc ifc) {
    object = ifc;
  }

  public BaseValueIfc getBaseValueIfc() {
    return object;
  }

  /**
   * ȫѡ��ť����
   * @param e ActionEvent
   */
  void selectAllButton_actionPerformed(ActionEvent e) {
    if (comlist.getRowCount() > 0) {
      for (int i = 0; i < comlist.getRowCount(); i++) {
        comlist.setSelectedRow(i);
      }
    }

  }
}
