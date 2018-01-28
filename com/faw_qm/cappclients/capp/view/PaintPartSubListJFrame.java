package com.faw_qm.cappclients.capp.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import com.faw_qm.framework.remote.*;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import java.util.Hashtable;
import java.util.Iterator;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkIfc;

/**
 * <p>Title: Ϳװ���һ������޸Ľ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������˾</p>
 * @author ������
 * @version 1.0
 */

public class PaintPartSubListJFrame
    extends JFrame {
  JPanel panel1 = new JPanel();
  //����ֵ����
  private QMFawTechnicsInfo techInfo;

  //Ϳװ���һ����
  PaintPartListJFrame paintframe;
  //����ʹ������������
  PartUsageTechLinkJPanel partTechLinkJPanel;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JButton searchPartbyRouteButton = new JButton();
  //����鿴����
  JButton viewButton = new JButton();
  //�޸��㲿��
  JButton modifyButton = new JButton();
  //����panel����ֵΪ�������࣬�������Ϳװ����
  private Hashtable partLinkTable = new Hashtable();

//  2009.06.22������ӣ�ԭ�򣺻��������޸ĵ�������������Ա��ڲ�������˳�ʱ��ԭ��
  private Vector partTempVec = new Vector();
  
  public Vector getPartTempVec(){
	  return this.partTempVec;
  }
//  end add by mario in 20090622
  
  /**
   *
   * @param info QMFawTechnicsInfo
   */
  public PaintPartSubListJFrame(QMFawTechnicsInfo info) {
    techInfo = info;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * ȱʡ���캯��
   */
  public PaintPartSubListJFrame() {
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

  /**
   * �����ʼ��
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setSize(1200, 800);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        Object object = e.getSource();
        if (object == PaintPartSubListJFrame.this) {
          closewindow();
        }
      }
    });
    setIconImage(new ImageIcon(getClass().getResource(
        "/images/technics.gif")).getImage());
    this.setTitle("Ϳװ���һ����(�༭)");

    partTechLinkJPanel = (PartUsageTechLinkJPanel) partLinkTable.get(
        "Ϳװ����");
    if (partTechLinkJPanel == null) {
      partTechLinkJPanel = new PartUsageTechLinkJPanel("Ϳװ����");
      partLinkTable.put("Ϳװ����", partTechLinkJPanel);
    }
    partTechLinkJPanel.setMode("Edit");
    this.getContentPane().setLayout(gridBagLayout1);
    partTechLinkJPanel.getCappAssociationsPanel().setMutliSelectedModel(true);
    panel1.setLayout(gridBagLayout2);
    jPanel1.setLayout(gridBagLayout3);
    okButton.setToolTipText("");
    okButton.setMnemonic('0');
    okButton.setText("����");
    okButton.addActionListener(new
                               PaintPartSubListJFrame_okButton_actionAdapter(this));
    cancelButton.setOpaque(true);
    cancelButton.setText("�˳�");
    cancelButton.addActionListener(new
                                   PaintPartSubListJFrame_cancelButton_actionAdapter(this));
    searchPartbyRouteButton.setText("��·�������㲿��");
    searchPartbyRouteButton.addActionListener(new
                                              PaintPartSubListJFrame_searchPartbyRouteButton_actionAdapter(this));
    viewButton.setText("����鿴���棨���ã�");
    modifyButton.setText("�޸Ļ�ɾ��");
    modifyButton.addActionListener(new
                                   PaintPartSubListJFrame_modifyButton_actionAdapter(this));
    viewButton.addActionListener(new
                                 PaintPartSubListJFrame_viewButton_actionAdapter(this));
    this.getContentPane().add(panel1,
                              new GridBagConstraints(0, 0, 2, 1, 1.0, 3.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(4, 4, 4, 4), 0, 0));
    panel1.add(partTechLinkJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(4, 4, 4, 4), 0, 0));
    this.getContentPane().add(jPanel1,
                              new GridBagConstraints(0, 1, 1, 1, 1.0, 0.1
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(4, 4, 0, 0), 0, 0));
    jPanel1.add(viewButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(modifyButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(cancelButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(searchPartbyRouteButton,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 0, 0, 0), 0, 0));

  }

  /**
   * ���������Ĺ�����������б���
   * @param v Vector
   */
  public void insertPartLinkToTable(Vector v) {
//	  2009.06.22  �������.
	  this.partTempVec = v;
//	  end add by mario in 20090622
    if (v != null && v.size() > 0) {
      Iterator iter = v.iterator();
      int k = partTechLinkJPanel.getCappAssociationsPanel().
          getNumberOfRows();
      for (int i = k; iter.hasNext(); i++) {
        PartUsageQMTechnicsLinkIfc linkifc = (PartUsageQMTechnicsLinkIfc) iter.
            next();
        try {
          partTechLinkJPanel.getCappAssociationsPanel().addToTable(linkifc, i);
        }
        catch (Exception ex2) {
          ex2.printStackTrace();
        }
      }
    }

  }

  /**
   * ȷ����ť����
   * @param e ActionEvent
   */
  void okButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    String bsoid = techInfo.getBsoID();
    Vector vector = null;
    try {
      vector = (Vector) partTechLinkJPanel.getAllLinks();
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
    }
    if (vector != null && vector.size() > 0) {
      try {
        RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("StandardCappService");
        info1.setMethodName("createPartUsageQMTechnicsLink");
        Class[] paraClass1 = {
            String.class,
            Vector.class};
        info1.setParaClasses(paraClass1);
        Object[] objs1 = {
            bsoid,
            vector};
        info1.setParaValues(objs1);
        server.request(info1);
      }
      catch (QMRemoteException ex) {
        ex.printStackTrace();
      }
      JOptionPane.showMessageDialog(this, "����ɹ�������˳���ť�رս���", "��ʾ",
                                    JOptionPane.WARNING_MESSAGE);
//      2009.06.22 �������
      this.partTempVec.removeAllElements();
//      add by mario in 20090622
    }
    setCursor(Cursor.getDefaultCursor());
  }

  void closewindow() {
    int i = JOptionPane.showConfirmDialog(this, "�Ƿ�ȷ���˳���", "��ʾ",
                                          JOptionPane.WARNING_MESSAGE);
    if (i == JOptionPane.CANCEL_OPTION) {
      return;
    }
    else {
    	String[] partIDs = partTechLinkJPanel.getPartListPanel().getPartLists();
    	if(partIDs != null && partIDs.length > 0 && partTempVec != null && partTempVec.size() > 0){
    		for(int x = 0;x < partIDs.length;x++){
    			String partID = partIDs[x];
    			for(int xx = 0;xx < partTempVec.size();xx++){
    				String tempID = ((PartUsageQMTechnicsLinkIfc)partTempVec.get(xx)).getLeftBsoID();
//    				System.out.println("partID===="+partID+"====tempID----"+tempID);
    				if(tempID.equals(partID)){
    					partTempVec.remove(xx);
    					break;
    				}
    			}
    		}
    	}
    	if(this.partTempVec != null && this.partTempVec.size() > 0){
    		String bsoid = techInfo.getBsoID();
    		try {
    	        RequestServer server = RequestServerFactory.getRequestServer();
    	        ServiceRequestInfo info1 = new ServiceRequestInfo();
    	        info1.setServiceName("StandardCappService");
    	        info1.setMethodName("createPartUsageQMTechnicsLink");
    	        Class[] paraClass1 = {
    	            String.class,
    	            Vector.class};
    	        info1.setParaClasses(paraClass1);
    	        Object[] objs1 = {
    	            bsoid,
    	            partTempVec};
    	        info1.setParaValues(objs1);
    	        server.request(info1);
    	      }
    	      catch (QMRemoteException ex) {
    	        ex.printStackTrace();
    	      }
    	}
      dispose();
    }
  }

  /**
   * ȡ����ť����
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    closewindow();
  }

  /**
   * ��·�������㲿������
   * @param e ActionEvent
   */

  void searchPartbyRouteButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    TstSearchRouteListJDialog d = new TstSearchRouteListJDialog(null, this);
    d.setVisible(true);
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * ����Ϳװ���һ����鿴���棬�Ƚ���
   * @param e ActionEvent
   */
  void viewButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    PaintPartListJFrame frame = new PaintPartListJFrame(techInfo);
    frame.setVisible(true);
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * �޸Ļ�ɾ�����һ����ԭ�������Ϣ
   * @param e ActionEvent
   */
  void modifyButton_actionPerformed(ActionEvent e) {
    SearchPartUseTechLinkJDialog searchDialog = new
        SearchPartUseTechLinkJDialog(this, techInfo);
    searchDialog.setVisible(true);
  }
}

class PaintPartSubListJFrame_okButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintPartSubListJFrame adaptee;

  PaintPartSubListJFrame_okButton_actionAdapter(PaintPartSubListJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class PaintPartSubListJFrame_cancelButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintPartSubListJFrame adaptee;

  PaintPartSubListJFrame_cancelButton_actionAdapter(PaintPartSubListJFrame
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class PaintPartSubListJFrame_searchPartbyRouteButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintPartSubListJFrame adaptee;

  PaintPartSubListJFrame_searchPartbyRouteButton_actionAdapter(
      PaintPartSubListJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchPartbyRouteButton_actionPerformed(e);
  }
}

class PaintPartSubListJFrame_viewButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintPartSubListJFrame adaptee;

  PaintPartSubListJFrame_viewButton_actionAdapter(PaintPartSubListJFrame
                                                  adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.viewButton_actionPerformed(e);
  }
}

class PaintPartSubListJFrame_modifyButton_actionAdapter
    implements java.awt.event.ActionListener {
  PaintPartSubListJFrame adaptee;

  PaintPartSubListJFrame_modifyButton_actionAdapter(PaintPartSubListJFrame
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.modifyButton_actionPerformed(e);
  }
}
