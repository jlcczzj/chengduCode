/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.gybomNotice.client.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.part.model.*;
/**
 * <p>Title: 工艺BOM批量添加添加零件</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
 * @version 1.0
 */
public class GYPartentPartListSearchJDialog extends JDialog{
	  private JPanel panel1 = new JPanel();
	  private JTabbedPane jTabbedPane1 = new JTabbedPane();
	  private JPanel jPanel1 = new JPanel();
	  private JLabel numLabel = new JLabel();
	  private GridBagLayout gridBagLayout1 = new GridBagLayout();
	  private JPanel jPanel2 = new JPanel();
	  private JButton searchJButton = new JButton();
	  private JButton stopJButton = new JButton();
	  private QMMultiList qMMultiList = new QMMultiList();
	  private JPanel jPanel3 = new JPanel();
	  private JButton okJButton = new JButton();
	  private JButton cancelJButton = new JButton();
	//  private JButton addPJButton = new JButton();
	  
	  private GridBagLayout gridBagLayout2 = new GridBagLayout();
	  private JLabel qMStatus = new JLabel();
	  private GridBagLayout gridBagLayout3 = new GridBagLayout();
	  private GridBagLayout gridBagLayout4 = new GridBagLayout();

	  private JPanel remarkJPanel = new JPanel();
	 
	  private JFrame parentFrame = null;
	

	  /**用于标记资源信息*/
	  protected static ResourceBundle resource = null;

	  /**中止进程标志*/
	  protected boolean cancelInProgress = false;

	


	  private HashMap resultVector = new HashMap();
	  private ArrayList resultArrayList =new ArrayList();

	 // private JCheckBox appendResultJCheckBox = new JCheckBox();
	  private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.
	      clients.util.PaginatePanel();
	  JLabel jLabel1 = new JLabel();
	  JScrollPane jScrollPane1 = new JScrollPane();
	  JTextField numJTextField = new JTextField();
	  //CCBegin　SS1
	  QMPartIfc topPart = null;
	  public JPanel ZCAdoptNoticeAdjustPanel;
	  String type = "";
	  Vector vecParent=new Vector();
	  /**
	   * 构造函数
	   * @param frame 父窗口
	   */
	  public GYPartentPartListSearchJDialog(JFrame frame,JPanel listpanel,String type,Vector vecP) {
	    super(frame, "", true);
	    this.vecParent=vecP;
	    this.parentFrame = frame;
	    this.ZCAdoptNoticeAdjustPanel = (ZCAdoptNoticeAdjustPanel)listpanel;
	    this.type = type;

        JPanel parentPanel = ((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
        if(parentPanel instanceof CJAdoptNoticeViewPanel){
        	topPart = ((CJAdoptNoticeViewPanel)parentPanel).cxPart;
        }else if(parentPanel instanceof ZCBomAdoptNoticeViewPanel){
        	topPart = ((ZCBomAdoptNoticeViewPanel)parentPanel).cxPart;
        }
        //CCEnd SS1
        
	    try {
	      jbInit();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  /**
	   * 缺省的构造函数
	   */
	  public GYPartentPartListSearchJDialog() {
		  try {
		      jbInit();
		    }
		    catch (Exception ex) {
		      ex.printStackTrace();
		    }
	  }

	  /**
	   * 组件初始化
	   * @throws Exception
	   */
	  private void jbInit() throws Exception {
	    this.setTitle("添加父零部件");
	    this.setSize(650, 500);
	    panel1.setLayout(gridBagLayout3);
	    panel1.setBounds(new Rectangle(0, 0, 650, 500));
	    numLabel.setMaximumSize(new Dimension(200, 22));
	    numLabel.setMinimumSize(new Dimension(200, 22));
	    numLabel.setPreferredSize(new Dimension(200, 22));
	    numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    numLabel.setText("在表格中搜索父件编号");
	    jPanel1.setLayout(gridBagLayout1);
	    remarkJPanel.setLayout(gridBagLayout1);
	    jPanel2.setMaximumSize(new Dimension(70, 70));
	    jPanel2.setMinimumSize(new Dimension(70, 70));
	    jPanel2.setPreferredSize(new Dimension(70, 70));
	    jPanel2.setLayout(gridBagLayout4);
	    searchJButton.setMaximumSize(new Dimension(65, 23));
	    searchJButton.setMinimumSize(new Dimension(65, 23));
	    searchJButton.setPreferredSize(new Dimension(65, 23));
	    searchJButton.setToolTipText("Search");
	    searchJButton.setText("搜索");
	    searchJButton.addActionListener(new GYPartentPartListSearchJDialog_searchJButton_actionAdapter(this));
	    stopJButton.setMaximumSize(new Dimension(65, 23));
	    stopJButton.setMinimumSize(new Dimension(65, 23));
	    stopJButton.setPreferredSize(new Dimension(65, 23));
	    stopJButton.setToolTipText("Stop");
	    stopJButton.setText("停止");
	    stopJButton.addActionListener(new
	                                  GYPartentPartListSearchJDialog_stopJButton_actionAdapter(this));
	    jPanel3.setLayout(gridBagLayout2);
	    okJButton.setMaximumSize(new Dimension(75, 23));
	    okJButton.setMinimumSize(new Dimension(75, 23));
	    okJButton.setPreferredSize(new Dimension(75, 23));
	    okJButton.setToolTipText("Ok");
	    okJButton.setMnemonic('Y');
	    okJButton.setText("确定(Y)");
	    okJButton.setEnabled(false);
	    okJButton.addActionListener(new
	                                GYPartentPartListSearchJDialog_okJButton_actionAdapter(this));
	    cancelJButton.setMaximumSize(new Dimension(75, 23));
	    cancelJButton.setMinimumSize(new Dimension(75, 23));
	    cancelJButton.setPreferredSize(new Dimension(75, 23));
	    cancelJButton.setToolTipText("Cancel");
	    cancelJButton.setMnemonic('C');
	    cancelJButton.setText("取消(C)");
	    cancelJButton.addActionListener(new
	                                    GYPartentPartListSearchJDialog_cancelJButton_actionAdapter(this));

	    qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
	    qMStatus.setMaximumSize(new Dimension(4, 22));
	    qMStatus.setMinimumSize(new Dimension(4, 22));
	    qMStatus.setPreferredSize(new Dimension(4, 22));
	  //  appendResultJCheckBox.setText("附加结果");
//	    jLabel1.setRequestFocusEnabled(true);
//	    jLabel1.setText("（一个零件号一行）");
	    numJTextField.setEditable(true);
	    numJTextField.setMaximumSize(new Dimension(60, 10));
	    numJTextField.setMinimumSize(new Dimension(60, 10));
	    numJTextField.setPreferredSize(new Dimension(60, 10));
	    jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	        , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
	        new Insets(0, 0, 0, 0), 0, 0));
	    jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	        , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
	        new Insets(10, 0, 0, 0), 0, 0));
	    panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 10, 0, 10), 0, 0));
	    panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0 , GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(10, 0, 10, 10), 0, 0));
	    jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 6, 0, 0), 0, 0));

	    jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));
	    jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,new Insets(0, 8, 0, 0), 0, 0));
	    panel1.add(jTabbedPane1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 10), 0, 28));
	    panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0 , GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(22, 20, 0, 10), 0, 0));
	    jTabbedPane1.add(jPanel1, "搜索父零部件");
	    getContentPane().add(panel1, null);
	    
	    jPanel1.add(numLabel,new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 2, 8), 0, 0));
	  //  jPanel1.add(jLabel1,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
	    jScrollPane1.getViewport().add(numJTextField, null);
	    jPanel1.add(jScrollPane1,new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 2, 8), 10, 5));

	    panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
	                                                , GridBagConstraints.WEST,
	                                                GridBagConstraints.HORIZONTAL,
	                                                new Insets(0, 0, 0, 0), 0, 0));
//	    panel1.add(appendResultJCheckBox,
//	               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
//	                                      , GridBagConstraints.WEST,
//	                                      GridBagConstraints.NONE,
//	                                      new Insets(0, 10, 0, 0), 0, 0));
	    

	    localize();
	    System.out.println("vecParent===="+vecParent);
	    addObjectToMultiList(vecParent);
	    paginatePanel.addListener(new com.faw_qm.clients.util.PaginateListener() {
	      public void paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
	        paginatePanel_paginateEvent(e);
	      }
	    });
	  }

	  /**
	   * 本地化
	   */
	  private void localize() {
	    qMMultiList.setCellEditable(false);
	    qMMultiList.addItemListener(new ItemListener() {
	      public void itemStateChanged(ItemEvent e) {
	        setOkButtonEnabled();
	      }
	    });
	    String[] headings = {
	        "id", "编号", "名称","版本","子组","数量"};
	    qMMultiList.setHeadings(headings);
	    qMMultiList.setRelColWidth(new int[] {0, 1, 1,1,1,1});
	    qMMultiList.setMultipleMode(true);
	    qMMultiList.addActionListener(new
	                                  GYPartentPartListSearchJDialog_qMMultiList_actionAdapter(this));
	    qMMultiList.setEnabled(false);

	  }

	  /**
	   * 设置界面的显示位置
	   */
	  private void setViewLocation() {
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = this.getSize();
	    if (frameSize.height > screenSize.height) {
	      frameSize.height = screenSize.height;
	    }
	    if (frameSize.width > screenSize.width) {
	      frameSize.width = screenSize.width;
	    }
	    this.setLocation( (screenSize.width - frameSize.width) / 2,
	                     (screenSize.height - frameSize.height) / 2);

	  }

	  /**
	   * 重载父类方法，使界面显示在屏幕中央
	   * @param flag
	   */
	  public void setVisible(boolean flag) {
	    this.setViewLocation();
	    super.setVisible(flag);
	  }

	  /**
	   * 设置按钮的编辑状态，仅供内部使用
	   * @param flag
	   */
	  private void setButtons(boolean flag) {
	    searchJButton.setEnabled(flag);
	    stopJButton.setEnabled(!flag);

	    if (qMMultiList.getSelectedRows() == null) {
	      okJButton.setEnabled(false);
	    }
	    cancelJButton.setEnabled(flag);
	  }

	  /**
	   * 设置确定按钮的状态
	   */
	  private void setOkButtonEnabled() {
	    if (qMMultiList.getSelectedRows() == null) {
	      okJButton.setEnabled(false);
	    }
	    else {
	      okJButton.setEnabled(true);
	    }
	  }

	  /**
	   * 设置状态条信息
	   * @param text 状态条要显示的文本
	   */
	  private void setStatus(String text) {
	    qMStatus.setText(text);
	  }

	  /**
	   * 清空结果域
	   * @param flag 如果flag = true,则清空，否则不清
	   */
	  private synchronized void clearResults(boolean flag) {
	    if (flag) {
	      qMMultiList.clear();
	      //CCBegin SS2
	      resultArrayList.clear();
	      //CCEnd SS2
	      okJButton.setEnabled(false);
	    }
	    setStatus(" ");
	  }

	  /**
	   * 判断是否中止检索线程
	   * @return 如果返回True，则中止线程。
	   */
	  private boolean isCancelInProgress() {
	    return cancelInProgress;
	  }

	  /**
	   * 设置是否中止线程
	   * @param flag  如果flag=True，则中止线程。
	   */
	  private synchronized void setCancelInProgress(boolean flag) {
	    cancelInProgress = flag;
	  }



	  /**
	   * 获得用户录入的搜索条件
	   * @return 搜索条件
	   */
	  private String getCondition() {
//	    int rows=numjTextArea1.getText();
//	    Object[] objs = new Object[rows];
	    String text = numJTextField.getText().trim();
	    text=text.replaceAll("\n",";");
	 //   StringTokenizer parts=new StringTokenizer(text,";");
//	    int i=0;
//	    while(i<rows&&parts.hasMoreTokens()){
//	      objs[i]=parts.nextToken().toString().trim();
//	      i++;
//	    }

	    return text;
	  }

	  /**
	   * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
	   * @param flag 若flag=true，则保留原结果，否则清除上次查询结果
	   */
	  private void processSearchThread(boolean flag) {

	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    //是否清除原来结果
	    clearResults(!flag);

	    if (!flag) {
	      resultVector.clear();
	    }
	    this.setStatus("正在搜索数据库...");
	    //获得查询条件
	    String condition = getCondition();
	    try {
	      //调用服务方法查询数据
	      Class[] paraClass = {
	          Object[].class};
	      Object[] paraObj = {
	          condition};
	      Vector queryresult = (Vector) GYNoticeHelper.requestServer("GYBomNoticeService", "findMultPartsByNumbers", paraClass, paraObj);
	      this.postHandleResult(queryresult);
	    }
	    catch (QMRemoteException e) {
	      String title = "信息";
	      JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
	                                    JOptionPane.INFORMATION_MESSAGE);
	      setCursor(Cursor.getDefaultCursor());
	      if (!isCancelInProgress()) {
	        return;
	      }
	    }
	    catch(Exception ee){
	      ee.printStackTrace();
	    }
	    setCursor(Cursor.getDefaultCursor());

	  }

	  /**
	   * 结果集后处理,将搜索结果加入结果列表中
	   * @param searchResult 搜索结果(零件)
	   * @throws QMRemoteException
	   */
	  private void postHandleResult(Vector searchResult) throws QMRemoteException {

	    if (searchResult == null || searchResult.size() == 0) {
	      setStatus("找到 0 个零部件");
	      return;
	    }
	    //结果列表中的零件总数

	    int oldSize = resultVector.size();
	    for (int i = 0; i < searchResult.size(); i++) {
	      if (isCancelInProgress()) {
	        break;
	      }

	      QMPartInfo orderlist = (QMPartInfo) searchResult.get(i);

	      //判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
	      boolean flag = false;
	      if (resultVector.size() != 0) {
	        Object[] ids = resultVector.keySet().toArray();
	        for (int k = 0; k < resultVector.size(); k++) {
	          if (orderlist.getBsoID().equals(ids[k])) {
	            flag = true;
	            break;
	          }
	        }
	      }
	      if (flag == true) {
	        continue;
	      }

	      resultVector.put(orderlist.getBsoID(), orderlist);
	      resultArrayList.add(orderlist);
	    }

	  //  if (!appendResultJCheckBox.isSelected()) {
	      setStatus("找到 " + resultVector.size() + " 个零件");
//	    }
//	    else {
//	      setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个零件");
//	    }


	  }



	  /**
	   * 将结果添加到列表中
	   * @param objs Vector
	   */
	  private void addObjectToMultiList(Vector objs) {

	    qMMultiList.clear();
	    if (objs == null || objs.size() == 0) {
	      return;
	    }
	    //将搜索结果加入结果列表中
	    int p = objs.size();
	    for (int i = 0; i < p; i++) {
	    	String[] str  = (String[]) objs.get(i);
	    	System.out.println("i==="+i);
	      qMMultiList.addTextCell(i, 0, str[0]);
	      qMMultiList.addTextCell(i, 1, str[1]);
	      qMMultiList.addTextCell(i, 2, str[2]);
	      qMMultiList.addTextCell(i, 3, str[4]);
	      qMMultiList.addTextCell(i, 4, str[5]);
	      qMMultiList.addTextCell(i, 5, str[3]);
	    }
	    setCursor(Cursor.getDefaultCursor());
	  }

	  /**
	   * 执行搜索操作
	   * @param e ActionEvent
	   */
	  void searchJButton_actionPerformed(ActionEvent e) {
	    setButtons(false);


	  }

	  /**
	   * 执行“中止”操作
	   * @param e ActionEvent
	   */
	  void stopJButton_actionPerformed(ActionEvent e) {
	    stopJButton.setEnabled(false);
	    
	  }

	  /**
	   * 执行“确定”操作
	   * @param e  ActionEvent
	   */
	  void okJButton_actionPerformed(ActionEvent e) {
	    processOKCommond();
	  }

	  /**
	   * 点击确定按钮后，将选中的零部件添加到列表中
	   */
	  private void processOKCommond() {
	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    this.dispose();
	 //CCBegin SS1
	    String[] objs = (String[])this.getSelectedDetails();
	     
		    ((ZCAdoptNoticeAdjustPanel)ZCAdoptNoticeAdjustPanel).setParentMultiListData(type,objs);
	
	    setCursor(Cursor.getDefaultCursor());
	  }

	  /**
	   * 获得结果域选中的业务对象
	   * @return 选中的业务对象
	   */
	  public String[] getSelectedDetails() {
	    int rows = qMMultiList.getSelectedRow();

	      String bsoid = qMMultiList.getCellText(rows, 0);
	      String partNumber = qMMultiList.getCellText(rows, 1);
	      String subGroup = qMMultiList.getCellText(rows, 4);
	      String sl = qMMultiList.getCellText(rows, 5);
          String[] spart = new String[4];
          spart[0]=bsoid;
          spart[1]=partNumber;
          spart[2]=subGroup;
          spart[3]=sl;
	    return spart;
	  }

	  /**
	   * 执行“取消“操作
	   * @param e ActionEvent
	   */
	  void cancelJButton_actionPerformed(ActionEvent e) {
	    this.dispose();
	  }



	  void qMMultiList_actionPerformed(ActionEvent e) {
	    processOKCommond();
	  }

	  void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
	    //this.addObjectToMultiList(paginatePanel.getCurrentObjects());
	  }

	}

	class GYPartentPartListSearchJDialog_searchJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYPartentPartListSearchJDialog adaptee;

	  GYPartentPartListSearchJDialog_searchJButton_actionAdapter(
			  GYPartentPartListSearchJDialog adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.searchJButton_actionPerformed(e);
	  }
	}

	class GYPartentPartListSearchJDialog_stopJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYPartentPartListSearchJDialog adaptee;

	  GYPartentPartListSearchJDialog_stopJButton_actionAdapter(GYPartentPartListSearchJDialog
	      adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.stopJButton_actionPerformed(e);
	  }
	}

	class GYPartentPartListSearchJDialog_okJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYPartentPartListSearchJDialog adaptee;

	  GYPartentPartListSearchJDialog_okJButton_actionAdapter(GYPartentPartListSearchJDialog
	      adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.okJButton_actionPerformed(e);
	  }
	}

	class GYPartentPartListSearchJDialog_cancelJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYPartentPartListSearchJDialog adaptee;

	  GYPartentPartListSearchJDialog_cancelJButton_actionAdapter(
			  GYPartentPartListSearchJDialog adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.cancelJButton_actionPerformed(e);
	  }
	}
	class GYPartentPartListSearchJDialog_addPJButtonJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private GYPartentPartListSearchJDialog adaptee;

  GYPartentPartListSearchJDialog_addPJButtonJButton_actionAdapter(
		  GYPartentPartListSearchJDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}
	
	class GYPartentPartListSearchJDialog_qMMultiList_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYPartentPartListSearchJDialog adaptee;

	  GYPartentPartListSearchJDialog_qMMultiList_actionAdapter(GYPartentPartListSearchJDialog
	      adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.qMMultiList_actionPerformed(e);
	  }
}
