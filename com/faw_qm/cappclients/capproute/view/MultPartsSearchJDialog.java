/**
 * 生成程序 MultPartsSearchJDialog.java    1.0    2013-11-25
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 批量添加零部件增加添加子件功能  liuzhicheng 2012-11-7
 * SS2 添加出错问题，与添加零部件中获取零部件规则不一样，不走视图判断。 liunan 2015-12-1
 * SS3 去掉分页，批量添加结果就是多页，但分页添加操作就太麻烦了。 liunan 2016-4-7
 * SS4 为了能区分变速箱创建的件与中心和解放件，增加显示id列，带有bsxup字样为变速箱设计。 liunan 2016-5-17
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;

/**
 * <p>Title:批量添加零部件 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: 启明</p>
 * @author 郭晓亮
 * @author 刘楠
 * @version 1.0
 */

public class MultPartsSearchJDialog
    extends JDialog {
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
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JLabel qMStatus = new JLabel();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();

  /**用于标记资源文件路径*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

  /**测试变量*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.qq.verbose", "true")).equals("true");

  /**用于标记资源信息*/
  protected static ResourceBundle resource = null;

  /**中止进程标志*/
  protected boolean cancelInProgress = false;

  /**线程组*/
  private static ThreadGroup theThreadGroup = Thread.currentThread().
      getThreadGroup();

  /**检索控制类*/
  private SearchThread searchThread;

  private HashMap resultVector = new HashMap();

  private JCheckBox appendResultJCheckBox = new JCheckBox();
  
  //CCBegin SS1 批量添加零部件增加添加子件功能
  private JCheckBox appendChildJCheckBox = new JCheckBox();
  //CCEnd SS1 
  
  //CCBegin SS3
  //private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.
  //    clients.util.PaginatePanel();
  //CCEnd SS3
  JLabel jLabel1 = new JLabel();
  //CCBegin SS4
  JLabel desLabel1 = new JLabel();
  JLabel desLabel2 = new JLabel();
  //CCEnd SS4
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea numjTextArea1 = new JTextArea();

  /**
   * 构造函数
   * @param frame 父窗口
   */
  public MultPartsSearchJDialog(JFrame frame) {
    super(frame);
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
  public MultPartsSearchJDialog() {
    this(null);
  }

  /**
   * 组件初始化
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setTitle("批量添加零部件");
    //CCBegin SS4
    this.setSize(800, 600);
    panel1.setLayout(gridBagLayout3);
    panel1.setBounds(new Rectangle(0, 0, 800, 600));
    //CCEnd SS4
    numLabel.setMaximumSize(new Dimension(24, 22));
    numLabel.setMinimumSize(new Dimension(24, 22));
    numLabel.setPreferredSize(new Dimension(24, 22));
    numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    numLabel.setText("零件编号");
    jPanel1.setLayout(gridBagLayout1);
    jPanel2.setMaximumSize(new Dimension(70, 70));
    jPanel2.setMinimumSize(new Dimension(70, 70));
    jPanel2.setPreferredSize(new Dimension(70, 70));
    jPanel2.setLayout(gridBagLayout4);
    searchJButton.setMaximumSize(new Dimension(65, 23));
    searchJButton.setMinimumSize(new Dimension(65, 23));
    searchJButton.setPreferredSize(new Dimension(65, 23));
    searchJButton.setToolTipText("Search");
    searchJButton.setText("搜索");
    searchJButton.addActionListener(new
                                    MultPartsSearchJDialog_searchJButton_actionAdapter(this));
    stopJButton.setMaximumSize(new Dimension(65, 23));
    stopJButton.setMinimumSize(new Dimension(65, 23));
    stopJButton.setPreferredSize(new Dimension(65, 23));
    stopJButton.setToolTipText("Stop");
    stopJButton.setText("停止");
    stopJButton.addActionListener(new
                                  MultPartsSearchJDialog_stopJButton_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout2);
    okJButton.setMaximumSize(new Dimension(75, 23));
    okJButton.setMinimumSize(new Dimension(75, 23));
    okJButton.setPreferredSize(new Dimension(75, 23));
    okJButton.setToolTipText("Ok");
    okJButton.setMnemonic('Y');
    okJButton.setText("确定(Y)");
    okJButton.setEnabled(false);
    okJButton.addActionListener(new
                                MultPartsSearchJDialog_okJButton_actionAdapter(this));
    cancelJButton.setMaximumSize(new Dimension(75, 23));
    cancelJButton.setMinimumSize(new Dimension(75, 23));
    cancelJButton.setPreferredSize(new Dimension(75, 23));
    cancelJButton.setToolTipText("Cancel");
    cancelJButton.setMnemonic('C');
    cancelJButton.setText("取消(C)");
    cancelJButton.addActionListener(new
                                    MultPartsSearchJDialog_cancelJButton_actionAdapter(this));
    qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
    qMStatus.setMaximumSize(new Dimension(4, 22));
    qMStatus.setMinimumSize(new Dimension(4, 22));
    qMStatus.setPreferredSize(new Dimension(4, 22));
    appendResultJCheckBox.setText("附加结果");
    //CCBegin SS1
    appendChildJCheckBox.setText("是否添加零件子件");
    //CCEnd SS1
    jLabel1.setRequestFocusEnabled(true);
    jLabel1.setText("（一个零件号一行）");
    //CCBegin SS4
    desLabel1.setText("id有BSXUP为变速箱建件，");
    desLabel2.setText("请根据实际需求进行选择！");
    //CCEnd SS4
    numjTextArea1.setEditable(true);
    jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
        new Insets(10, 0, 0, 0), 0, 0));
    panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 10, 0, 10), 0, 0));
    panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
                                               , GridBagConstraints.EAST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(10, 0, 10, 10), 0, 0));
    //CCBegin SS3
    //jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
    //    , GridBagConstraints.WEST, GridBagConstraints.NONE,
    //    new Insets(0, 6, 0, 0), 0, 0));
    //CCEnd SS3
    jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 0), 0, 0));
    panel1.add(jTabbedPane1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 28));
    panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTHEAST,
                                               GridBagConstraints.NONE,
                                               new Insets(22, 20, 0, 10), 0, 0));
    jTabbedPane1.add(jPanel1, "批量搜索零部件");
    getContentPane().add(panel1, null);
    jPanel1.add(numLabel,                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
    jPanel1.add(jLabel1,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
    //CCBegin SS4
    jPanel1.add(desLabel1,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
    jPanel1.add(desLabel2,     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
    //CCEnd SS4
    jPanel1.add(jScrollPane1,                         new GridBagConstraints(1, 0, 5, 5, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(1, 0, 6, 0), 450, 120));
    //CCEnd SS4
    jScrollPane1.getViewport().add(numjTextArea1, null);
    panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(appendResultJCheckBox,
               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                      , GridBagConstraints.WEST,
                                      GridBagConstraints.NONE,
                                      new Insets(0, 10, 0, 0), 0, 0));
    //CCBegin SS1
    panel1.add(appendChildJCheckBox,
            new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                   , GridBagConstraints.WEST,
                                   GridBagConstraints.NONE,
                                   new Insets(0, 10, 0, 0), 0, 0));
    //CCEnd SS1
    localize();   

    //CCBegin SS3
    /*paginatePanel.addListener(new com.faw_qm.clients.util.PaginateListener() {
      public void paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
        paginatePanel_paginateEvent(e);
      }
    });*/
    //CCEnd SS3
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
        "id", "编号", "名称"};
    qMMultiList.setHeadings(headings);
    //CCBegin SS4
    //qMMultiList.setRelColWidth(new int[] {0, 1, 1});
    qMMultiList.setRelColWidth(new int[] {1, 1, 1});
    //CCEnd SS4
    qMMultiList.setMultipleMode(true);
    qMMultiList.addActionListener(new
                                  MultPartsSearchJDialog_qMMultiList_actionAdapter(this));
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
   * 中止检索进程
   */
  private synchronized void cancel() {
    if (searchThread != null && searchThread.isAlive()) {
      setCancelInProgress(true);
      //searchThread.interrupt(); //将这句去掉，是因为它使界面退出了，这不合业务
      searchThread = null;
    }
  }

  /**
   * 获得用户录入的搜索条件
   * @return 搜索条件
   */
  private Object[] getCondition() {
    int rows=numjTextArea1.getLineCount();
    Object[] objs = new Object[rows];
    String text = numjTextArea1.getText().trim();
    text=text.replaceAll("\n",";");
    
    if(text==null||text.equals("")||text.equals("null")){
    	
    	JOptionPane.showMessageDialog(this,"搜索条件不能为空，请输入搜索条件！", "提示信息",
                JOptionPane.INFORMATION_MESSAGE);
    	setCursor(Cursor.getDefaultCursor());
    	return null;
    	
    }
    StringTokenizer parts=new StringTokenizer(text,";");
    int i=0;
    while(i<rows&&parts.hasMoreTokens()){
      objs[i]=parts.nextToken().toString().trim();
      i++;
    }

    return objs;
  }

  /**
   * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
   * @param flag 若flag=true，则保留原结果，否则清除上次查询结果
   */
  private void processSearchThread(boolean flag) {
    if (verbose) {
      System.out.println(
          " processSearchThread()...begin");
    }
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    //是否清除原来结果
    clearResults(!flag);

    if (!flag) {
      resultVector.clear();
    }
    this.setStatus("正在搜索数据库...");
    //获得查询条件
    Object[] condition = getCondition();
    
    try {
      //调用服务方法查询数据
      Class[] paraClass = {
          Object[].class};
      Object[] paraObj = {
          condition};
      Vector queryresult = (Vector) RParentJPanel.useServiceMethod(
          "TechnicsRouteService", "findMultPartsByNumbers", paraClass, paraObj);
      this.postHandleResult(queryresult);
    }
    catch (QMRemoteException e) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
      JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                                    JOptionPane.INFORMATION_MESSAGE);
      setCursor(Cursor.getDefaultCursor());
      if (!isCancelInProgress()) {
        return;
      }
    }
    setCursor(Cursor.getDefaultCursor());
    if (verbose) {
      System.out.println("processSearchThread() end... ");
    }
  }

  /**
   * 结果集后处理,将搜索结果加入结果列表中
   * @param searchResult 搜索结果(零件)
   * @throws QMRemoteException
   */
  private void postHandleResult(Vector searchResult) throws QMRemoteException {
    if (verbose) {
      System.out.println(
          " postHandleResult() begin...");
    }
    if (searchResult == null || searchResult.size() == 0) {
      setStatus("找到 0 个零部件");
      return;
    }
    //结果列表中的零件总数
    if (verbose) {
      System.out.println("the result size is:::" + searchResult.size());
    }
    int oldSize = resultVector.size();
    for (int i = 0; i < searchResult.size(); i++) {
      if (isCancelInProgress()) {
        break;
      }
      if (verbose) {
        System.out.println("add the object to MultiList start......");
      }
      QMPartMasterIfc orderlist = (QMPartMasterIfc) searchResult.
          elementAt(i);

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

    }

    if (!appendResultJCheckBox.isSelected()) {
      setStatus("找到 " + resultVector.size() + " 个零件");
    }
    else {
      setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个零件");
    }

    if (verbose) {
      System.out.println("postHandleResult() end");
    }
  }

  private synchronized void addResultToMultiList() {
    Vector newV = new Vector();
    newV.addAll(resultVector.values());
    //CCBegin SS3
    //Vector firstPageVector = paginatePanel.paginate(newV);
    //addObjectToMultiList(firstPageVector);
    addObjectToMultiList(newV);
    //CCEnd SS3
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
      QMPartMasterIfc qmpart = (QMPartMasterIfc) objs.
          elementAt(i);
      qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
      qMMultiList.addTextCell(i, 1, qmpart.getPartNumber());
      qMMultiList.addTextCell(i, 2, qmpart.getPartName());
    }
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 执行搜索操作
   * @param e ActionEvent
   */
  void searchJButton_actionPerformed(ActionEvent e) {
    setButtons(false);
    //启动搜索线程
    searchThread = new SearchThread(theThreadGroup, this,
                                    appendResultJCheckBox.isSelected());
    searchThread.start();

  }

  /**
   * 执行“中止”操作
   * @param e ActionEvent
   */
  void stopJButton_actionPerformed(ActionEvent e) {
    stopJButton.setEnabled(false);
    cancel();
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
    //获得选择的所有零部件
    //if (verbose) 
    {
      System.out.println("&&&&&&&&&&&&&& 统计: " + this.getSelectedDetails());
    }
    QMPartMasterIfc[] objs = (QMPartMasterIfc[])this.getSelectedDetails();
    //if (verbose) 
    {
      System.out.println("&&&&&&&&&&&&&& 选择的结果数目: " + objs.length);
    }
    //返回QMPartMasterIfc[]
      for (int i=0;i<objs.length;i++) {
        QMPartMasterIfc masterinfo = (QMPartMasterIfc) objs[i];
        System.out.println("id==="+masterinfo.getBsoID());
        System.out.println("id==="+masterinfo.getPartNumber());
        //CCBegin SS1
    	Vector v = new Vector();
        if(appendChildJCheckBox.isSelected())
        {
            try 
            {
//	        	v.add(masterinfo);//
            	//调用服务获得最	新版本Part值对象
				//CCBegin SS2
	            Class[] paraClass = {
	                //String.class};
	                QMPartMasterIfc.class};
	            Object[] paraObj = {  
	            		//masterinfo.getBsoID()};
	            		masterinfo};
	            //QMPartIfc resultPartIfc = (QMPartIfc) RParentJPanel.useServiceMethod(
	                //"StandardPartService", "getPartByMasterID", paraClass, paraObj);
	            Collection resultPartIfc = (Collection) RParentJPanel.useServiceMethod(
	                "StandardPartService", "findPart", paraClass, paraObj);
				if(resultPartIfc != null)
				{  
					Iterator itt = resultPartIfc.iterator();
					if(itt.hasNext())
					{
						QMPartIfc  partIfc1 = (QMPartIfc) itt.next();
						System.out.println("partIfc1==="+partIfc1);
		            Class[] paraClass1 = {  
		            		QMPartIfc.class};
		            Object[] paraObj1 = {    
		            		//resultPartIfc};  
		            	partIfc1};
		            Collection collection = (Collection) RParentJPanel.useServiceMethod(
		                        "StandardPartService", "getAllSubParts", paraClass1, paraObj1);
					if (collection != null && collection.size() != 0) 
					{  
						Iterator iterdanche = collection.iterator();
						while (iterdanche.hasNext()) {
							QMPartIfc  partIfc = (QMPartIfc) iterdanche.next();
							QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)partIfc.getMaster();
							//v.add(partMasterIfc);      
							Object [] part={partMasterIfc,partIfc};
							v.add(part);
						}  
					}
				}
				//CCEnd SS2
				}
            }
            catch (QMRemoteException e) {
              String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
              JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                                            JOptionPane.INFORMATION_MESSAGE);
              setCursor(Cursor.getDefaultCursor());
              if (!isCancelInProgress()) {
                return;
              }
            }
        }else
        {
        	//v.add(masterinfo);
        	try{
        	//调用服务获得最	新版本Part值对象
        	//CCBegin SS2
	            Class[] paraClass = {
	                //String.class};
	                QMPartMasterIfc.class};
	            Object[] paraObj = {  
	            		//masterinfo.getBsoID()};
	            		masterinfo};
	            //QMPartIfc resultPartIfc = (QMPartIfc) RParentJPanel.useServiceMethod(
	                //"StandardPartService", "getPartByMasterID", paraClass, paraObj);
	            Collection resultPartIfc = (Collection) RParentJPanel.useServiceMethod(
	                "StandardPartService", "findPart", paraClass, paraObj);
				if(resultPartIfc != null)
				{
					Iterator itt = resultPartIfc.iterator();
					if(itt.hasNext())
					{
						QMPartIfc  partIfc = (QMPartIfc) itt.next();
						System.out.println("partIfc==="+partIfc);
						Object [] part={masterinfo,partIfc};
						v.add(part);
					}
				}
				//CCEnd SS2
				
            }
            catch (QMRemoteException e) {
              String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
              JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                                            JOptionPane.INFORMATION_MESSAGE);
              setCursor(Cursor.getDefaultCursor());
              if (!isCancelInProgress()) {
                return;
              }
            }
        }
        System.out.println("v==="+v);
        //CCEnd SS1
    	RefreshService.getRefreshService().dispatchRefresh("addSelectedParts",
                0,
                v);  
      }

    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 获得结果域选中的业务对象
   * @return 选中的业务对象
   */
  public QMPartMasterIfc[] getSelectedDetails() {
    int[] rows = qMMultiList.getSelectedRows();
    QMPartMasterIfc[] values = new QMPartMasterIfc[rows.length];
    for (int i = 0; i < rows.length; i++) {
      int xx = rows[i];
      String bsoid = qMMultiList.getCellText(xx, 0);
      values[i] = (QMPartMasterIfc) resultVector.get(bsoid);
      if (verbose) {
        System.out.println("选中的是：" + values[i]);
      }
    }
    return values;
  }

  /**
   * 执行“取消“操作
   * @param e ActionEvent
   */
  void cancelJButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  /**
   * <p>Title: 内部工作线程</p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2007</p>
   * <p>Company: 一汽启明</p>
   * @author
   * @version 1.0
   */
  class SearchThread
      extends QMThread {
    /**判断是否保留原来的搜索结果*/
    private boolean isLeave;

    /**搜索界面对象*/
    private MultPartsSearchJDialog myDialog;

    /**
     * 构造查询实例
     * @param threadgroup 线程组
     * @param dialog 按工装搜索界面对象
     * @param flag 判断是否保留原来的搜索结果
     */
    public SearchThread(ThreadGroup threadgroup,
                        MultPartsSearchJDialog dialog, boolean flag) {
      super(threadgroup);
      myDialog = dialog;
      isLeave = flag;
    }

    /**
     * 使线程中断，停止搜索进程
     */
    public synchronized void interrupt() {
      if (verbose) {
        System.out.println("Inside Interrupt......");
      }
      this.interrupt();
    }

    /**
     * 线程运行方法，执行搜索。
     */
    public synchronized void run() {
      if (verbose) {
        System.out.println("SearchThread: run() begin...");
      }
      try {
        //执行搜索
        myDialog.processSearchThread(isLeave);
      }
      catch (Throwable throwable) {
        if (!myDialog.isCancelInProgress()) {
          throwable.printStackTrace();
        }
      }
      finally {
        if (verbose) {
          System.out.println("Inside run finally......");
        }
        //设置界面按钮的显示状态
        myDialog.setButtons(true);
        //设置没有处于中断状态
        myDialog.setCancelInProgress(false);
        myDialog.addResultToMultiList();
      }

      if (verbose) {
        System.out.println("SearchThread: run() end...return is void");
      }
    }
  }

  void qMMultiList_actionPerformed(ActionEvent e) {
    processOKCommond();
  }

  //CCBegin SS3
  //void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
  //  this.addObjectToMultiList(paginatePanel.getCurrentObjects());
  //}
  //CCEnd SS3

}

class MultPartsSearchJDialog_searchJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private MultPartsSearchJDialog adaptee;

  MultPartsSearchJDialog_searchJButton_actionAdapter(
      MultPartsSearchJDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchJButton_actionPerformed(e);
  }
}

class MultPartsSearchJDialog_stopJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private MultPartsSearchJDialog adaptee;

  MultPartsSearchJDialog_stopJButton_actionAdapter(MultPartsSearchJDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.stopJButton_actionPerformed(e);
  }
}

class MultPartsSearchJDialog_okJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private MultPartsSearchJDialog adaptee;

  MultPartsSearchJDialog_okJButton_actionAdapter(MultPartsSearchJDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class MultPartsSearchJDialog_cancelJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private MultPartsSearchJDialog adaptee;

  MultPartsSearchJDialog_cancelJButton_actionAdapter(
      MultPartsSearchJDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class MultPartsSearchJDialog_qMMultiList_actionAdapter
    implements java.awt.event.ActionListener {
  private MultPartsSearchJDialog adaptee;

  MultPartsSearchJDialog_qMMultiList_actionAdapter(MultPartsSearchJDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.qMMultiList_actionPerformed(e);
  }
}
