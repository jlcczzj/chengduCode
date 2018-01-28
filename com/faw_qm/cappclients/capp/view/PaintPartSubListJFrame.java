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
 * <p>Title: 涂装零件一览表的修改界面</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明公司</p>
 * @author 唐树涛
 * @version 1.0
 */

public class PaintPartSubListJFrame
    extends JFrame {
  JPanel panel1 = new JPanel();
  //工艺值对象
  private QMFawTechnicsInfo techInfo;

  //涂装零件一览表
  PaintPartListJFrame paintframe;
  //工艺使用零件关联面板
  PartUsageTechLinkJPanel partTechLinkJPanel;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JButton searchPartbyRouteButton = new JButton();
  //进入查看界面
  JButton viewButton = new JButton();
  //修改零部件
  JButton modifyButton = new JButton();
  //关联panel，键值为工艺种类，这里就是涂装工艺
  private Hashtable partLinkTable = new Hashtable();

//  2009.06.22徐德政加，原因：缓存用于修改的零件关联对象，以便在不保存而退出时还原。
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
   * 缺省构造函数
   */
  public PaintPartSubListJFrame() {
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

  /**
   * 界面初始化
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
    this.setTitle("涂装零件一览表(编辑)");

    partTechLinkJPanel = (PartUsageTechLinkJPanel) partLinkTable.get(
        "涂装工艺");
    if (partTechLinkJPanel == null) {
      partTechLinkJPanel = new PartUsageTechLinkJPanel("涂装工艺");
      partLinkTable.put("涂装工艺", partTechLinkJPanel);
    }
    partTechLinkJPanel.setMode("Edit");
    this.getContentPane().setLayout(gridBagLayout1);
    partTechLinkJPanel.getCappAssociationsPanel().setMutliSelectedModel(true);
    panel1.setLayout(gridBagLayout2);
    jPanel1.setLayout(gridBagLayout3);
    okButton.setToolTipText("");
    okButton.setMnemonic('0');
    okButton.setText("保存");
    okButton.addActionListener(new
                               PaintPartSubListJFrame_okButton_actionAdapter(this));
    cancelButton.setOpaque(true);
    cancelButton.setText("退出");
    cancelButton.addActionListener(new
                                   PaintPartSubListJFrame_cancelButton_actionAdapter(this));
    searchPartbyRouteButton.setText("按路线搜索零部件");
    searchPartbyRouteButton.addActionListener(new
                                              PaintPartSubListJFrame_searchPartbyRouteButton_actionAdapter(this));
    viewButton.setText("进入查看界面（慎用）");
    modifyButton.setText("修改或删除");
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
   * 将搜索到的关联零件插入列表中
   * @param v Vector
   */
  public void insertPartLinkToTable(Vector v) {
//	  2009.06.22  徐德政加.
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
   * 确定按钮动作
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
      JOptionPane.showMessageDialog(this, "保存成功，点击退出按钮关闭界面", "提示",
                                    JOptionPane.WARNING_MESSAGE);
//      2009.06.22 徐德政加
      this.partTempVec.removeAllElements();
//      add by mario in 20090622
    }
    setCursor(Cursor.getDefaultCursor());
  }

  void closewindow() {
    int i = JOptionPane.showConfirmDialog(this, "是否确认退出？", "提示",
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
   * 取消按钮动作
   * @param e ActionEvent
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    closewindow();
  }

  /**
   * 按路线搜索零部件动作
   * @param e ActionEvent
   */

  void searchPartbyRouteButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    TstSearchRouteListJDialog d = new TstSearchRouteListJDialog(null, this);
    d.setVisible(true);
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 进入涂装零件一览表查看界面，比较慢
   * @param e ActionEvent
   */
  void viewButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    PaintPartListJFrame frame = new PaintPartListJFrame(techInfo);
    frame.setVisible(true);
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 修改或删除零件一览表原有零件信息
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
