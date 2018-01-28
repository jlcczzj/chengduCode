package com.faw_qm.cappclients.capp.view;

import java.awt.*;
import javax.swing.*;
import com.faw_qm.clients.util.*;
import java.util.*;
import com.faw_qm.framework.remote.*;
import java.awt.event.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.wip.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.part.client.design.model.ProgressService;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.codemanage.model.CodingIfc;

/**
 * <p>Title:搜索艺准通知书，根据路线添加涂装零件一览表的零件集合</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 一汽启明</p>
 * @author 唐树涛
 * @version 1.0
 */

public class TstSearchRouteListJDialog
    extends JDialog {
  private CalendarSelectedPanel beginTime = new CalendarSelectedPanel();
  private CalendarSelectedPanel endTime = new CalendarSelectedPanel();
  private JPanel panel1 = new JPanel();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JTextField numJTextField = new JTextField();
  private JCheckBox numJCheckBox = new JCheckBox();
  private JLabel jLabel2 = new JLabel();
  private JTextField nameJTextField = new JTextField();
  private JCheckBox nameJCheckBox = new JCheckBox();
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
  private CodingIfc techifc = null;

  /**用于标记资源文件路径*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

  /**测试变量*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

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

  /** 标准图标*/
  private static Image startandImage;

  /** 检出图标*/
  private static Image checkOutImage;

  /** 原本图标*/
  private static Image workingImage;

  private JCheckBox appendResultJCheckBox = new JCheckBox();
  private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.
      clients.util.PaginatePanel();
  JLabel jLabelBeginTime = new JLabel();
  JLabel jLabelEndTime = new JLabel();
  public PaintPartSubListJFrame parentframe;
  public CappAssociationsPanel capppanel;
  public Frame jframe;

  /**
   * 构造函数
   * @param frame 父窗口
   */
  public TstSearchRouteListJDialog(Frame frame, PaintPartSubListJFrame jframe) {
    super(jframe, "", true);
    parentframe = jframe;
    try {
      init();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public TstSearchRouteListJDialog(Frame frame, String title,
                                   CappAssociationsPanel panel) {
    super(frame, title, true);
    jframe = frame;
    capppanel = panel;
    try {
      init();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 缺省的构造函数
   */
  public TstSearchRouteListJDialog() {
    try {
      init();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /**
   * 组件初始化
   * @throws Exception
   */
  private void init() throws Exception {
    this.setTitle("搜索艺准通知书");
    this.setSize(650, 500);
    panel1.setLayout(gridBagLayout3);
    panel1.setBounds(new Rectangle(0, 0, 650, 500));
    jLabel1.setMaximumSize(new Dimension(24, 22));
    jLabel1.setMinimumSize(new Dimension(24, 22));
    jLabel1.setPreferredSize(new Dimension(24, 22));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("编号");
    jPanel1.setLayout(gridBagLayout1);
    numJCheckBox.setMaximumSize(new Dimension(37, 22));
    numJCheckBox.setMinimumSize(new Dimension(37, 22));
    numJCheckBox.setPreferredSize(new Dimension(37, 22));
    numJCheckBox.setText("非");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("名称");
    nameJCheckBox.setMaximumSize(new Dimension(37, 22));
    nameJCheckBox.setMinimumSize(new Dimension(37, 22));
    nameJCheckBox.setPreferredSize(new Dimension(37, 22));
    nameJCheckBox.setText("非");
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
                                    TstSearchRouteListJDialog_searchJButton_actionAdapter(this));
    stopJButton.setMaximumSize(new Dimension(65, 23));
    stopJButton.setMinimumSize(new Dimension(65, 23));
    stopJButton.setPreferredSize(new Dimension(65, 23));
    stopJButton.setToolTipText("Stop");
    stopJButton.setText("停止");
    stopJButton.addActionListener(new
                                  TstSearchRouteListJDialog_stopJButton_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout2);
    okJButton.setMaximumSize(new Dimension(75, 23));
    okJButton.setMinimumSize(new Dimension(75, 23));
    okJButton.setPreferredSize(new Dimension(75, 23));
    okJButton.setToolTipText("Ok");
    okJButton.setMnemonic('Y');
    okJButton.setText("确定(Y)");
    okJButton.setEnabled(false);
    okJButton.addActionListener(new
                                TstSearchRouteListJDialog_okJButton_actionAdapter(this));
    cancelJButton.setMaximumSize(new Dimension(75, 23));
    cancelJButton.setMinimumSize(new Dimension(75, 23));
    cancelJButton.setPreferredSize(new Dimension(75, 23));
    cancelJButton.setToolTipText("Cancel");
    cancelJButton.setMnemonic('C');
    cancelJButton.setText("取消(C)");
    cancelJButton.addActionListener(new
                                    TstSearchRouteListJDialog_cancelJButton_actionAdapter(this));
    qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
    qMStatus.setMaximumSize(new Dimension(4, 22));
    qMStatus.setMinimumSize(new Dimension(4, 22));
    qMStatus.setPreferredSize(new Dimension(4, 22));
    appendResultJCheckBox.setText("附加结果");
    jLabelBeginTime.setText("开始时间");
    jLabelEndTime.setText("结束时间");
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
    jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 6, 0, 0), 0, 0));
    jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 0), 0, 0));
    panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 10, 0, 0), 0, 0));
    panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTHEAST,
                                               GridBagConstraints.NONE,
                                               new Insets(22, 20, 0, 10), 0, 0));
    jTabbedPane1.add(jPanel1, "搜索条件");
    getContentPane().add(panel1, null);
    jPanel1.add(jLabel2, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 3, 0), 0, 0));
    jPanel1.add(numJTextField, new GridBagConstraints(2, 0, 1, 2, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(6, 8, 3, 0), 0, 0));
    jPanel1.add(numJCheckBox, new GridBagConstraints(3, 0, 1, 2, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(6, 8, 3, 10), 1, 0));
    jPanel1.add(nameJTextField, new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(3, 8, 3, 0), 0, 0));
    jPanel1.add(nameJCheckBox, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 10), 0, 0));

    jPanel1.add(jLabelBeginTime, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(3, 0, 6, 0), 0, 0));
    jPanel1.add(beginTime, new GridBagConstraints(2, 6, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(3, 8, 6, 0), 0, 0));
    jPanel1.add(jLabelEndTime, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(3, 0, 6, 0), 0, 0));
    jPanel1.add(endTime, new GridBagConstraints(2, 7, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(3, 8, 6, 0), 0, 0));
    panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(appendResultJCheckBox,
               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                      , GridBagConstraints.WEST,
                                      GridBagConstraints.NONE,
                                      new Insets(0, 10, 0, 0), 0, 0));
    localize();
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
        "id", "编号", "名称", "级别", "相关产品编号", "版本"};
    qMMultiList.setHeadings(headings);
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 1});
    qMMultiList.setMultipleMode(true);
    qMMultiList.addActionListener(new
                                  TstSearchRouteListJDialog_qMMultiList_actionAdapter(this));
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
  private Object[][] getCondition() {
    Object[][] objs = new Object[4][2];
    objs[0][0] = numJTextField.getText().trim().toUpperCase();
    objs[0][1] = new Boolean(!numJCheckBox.isSelected());
    objs[1][0] = nameJTextField.getText().trim();
    objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
    objs[2][0] = beginTime.getDate();
    objs[3][0] = endTime.getDate();
    return objs;
    
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
    Object[][] condition = getCondition();
    try {
      //调用服务方法查询数据
      Class[] paraClass = {
          Object[][].class};
      Object[] paraObj = {
          condition};

      RequestServer server = RequestServerFactory.getRequestServer();
      ServiceRequestInfo info1 = new ServiceRequestInfo();
      info1.setServiceName("consTechnicsRouteService");
      info1.setMethodName("findRouteListsForCd");
      info1.setParaClasses(paraClass);
      info1.setParaValues(paraObj);
      Vector queryresult = (Vector) server.request(info1);
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
  }

  /**
   * 结果集后处理,将搜索结果加入结果列表中
   * @param searchResult 搜索结果(工艺路线表值对象)
   * @throws QMRemoteException
   */
  private void postHandleResult(Vector searchResult) throws QMRemoteException {

    if (searchResult == null || searchResult.size() == 0) {
      setStatus("找到 0 个艺准通知书");
      return;
    }
    //结果列表中的路线表总数
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
      TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) searchResult.
          elementAt(i);

      //判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
      boolean flag = false;
      if (resultVector.size() != 0) {
        Object[] ids = resultVector.keySet().toArray();
        for (int k = 0; k < resultVector.size(); k++) {
          if (routelist.getBsoID().equals(ids[k])) {
            flag = true;
            break;
          }
        }
      }
      if (flag == true) {
        continue;
      }

      resultVector.put(routelist.getBsoID(), routelist);

    }

    if (!appendResultJCheckBox.isSelected()) {
      setStatus("找到 " + resultVector.size() + " 个艺准通知书");
    }
    else {
      setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个艺准通知书");
    }
  }

  private synchronized void addResultToMultiList() {
    Vector newV = new Vector();
    newV.addAll(resultVector.values());
    Vector firstPageVector = paginatePanel.paginate(newV);
    addObjectToMultiList(firstPageVector);
  }

  private void addObjectToMultiList(Vector objs) {
    try {
      qMMultiList.clear();
      if (objs == null || objs.size() == 0) {
        return;
      }
      //将搜索结果加入结果列表中
      int p = objs.size();
      for (int i = 0; i < p; i++) {
        TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) objs.
            elementAt(i);
        qMMultiList.addTextCell(i, 0, routelist.getBsoID());
        if ( ( (WorkableIfc) routelist).getWorkableState().equals("c/o")) {
          if (checkOutImage == null) {
            checkOutImage = new ImageIcon(getClass().getResource(routelist.
                getIconName("CheckOutIcon"))).getImage();
          }
          qMMultiList.addCell(i, 1, routelist.getRouteListNumber(),
                              checkOutImage);
        }
        else if ( ( (WorkableIfc) routelist).getWorkableState().equals("c/i")) {
          if (startandImage == null) {
            startandImage = new ImageIcon(getClass().getResource(routelist.
                getIconName("StandardIcon"))).getImage();
          }
          qMMultiList.addCell(i, 1, routelist.getRouteListNumber(),
                              startandImage);
        }
        else if ( ( (WorkableIfc) routelist).getWorkableState().equals("wrk")) {
          if (workingImage == null) {
            workingImage = new ImageIcon(getClass().getResource(routelist.
                getIconName("WorkingIcon"))).getImage();
          }
          qMMultiList.addCell(i, 1, routelist.getRouteListNumber(),
                              workingImage);
        }

        qMMultiList.addTextCell(i, 2, routelist.getRouteListName());
        qMMultiList.addTextCell(i, 3, routelist.getRouteListLevel());
        Class[] paraClass = {
            String.class};
        Object[] paraObj = {
            routelist.getProductMasterID()};

        RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("PersistService");
        info1.setMethodName("refreshInfo");
        info1.setParaClasses(paraClass);
        info1.setParaValues(paraObj);
        QMPartMasterInfo partmasterinfo = (QMPartMasterInfo) server.request(
            info1);
        qMMultiList.addTextCell(i, 4, partmasterinfo.getPartNumber());
        qMMultiList.addTextCell(i, 5, routelist.getVersionValue());
      }
    }
    catch (QMRemoteException e) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
      JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                                    JOptionPane.INFORMATION_MESSAGE);
      setCursor(Cursor.getDefaultCursor());
    }
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

  public CodingIfc getQMFawTechnics() {
    return techifc;
  }

  public void setQMFawTechnics(CodingIfc ifc) {
    techifc = ifc;
  }

  /**
   * 处理确定按钮
   */
  private void processOKCommond() {

    //获得选择的艺准通知书
    Vector listvec = this.getSelectedDetails();
    int i = JOptionPane.showConfirmDialog(this, "如果关联零部件很多，时间会很长，是否继续？", "提示",
                                          JOptionPane.CANCEL_OPTION);
    if (i == JOptionPane.CANCEL_OPTION) {
      return;
    }
    else {
      Vector vector = new Vector();
      for (Iterator iter = listvec.iterator(); iter.hasNext(); ) {
        TechnicsRouteListInfo techinfo = (TechnicsRouteListInfo) iter.next();
        //主要零部件的艺准搜索
        if (capppanel != null) {
          BaseValueIfc[] ifc = getRouteListParts(techinfo);
          if (ifc != null && ifc.length > 0) {
            for (int k = 0; k < ifc.length; k++) {
              vector.add(ifc[k]);
            }
          }
        }
        //涂装零件一览表的艺准搜索
        else {
//          System.out.println();
          Vector vec = getRouteListPartLinks(techinfo);
          vector.addAll(vec);
        }
      }

      ProgressService.setProgressText("正在获取艺准通知书关联的零部件");
      ProgressService.showProgress();
      if (capppanel != null) {
        if (vector != null) {
          SelectStructPartJDialog selectpart = new SelectStructPartJDialog(
              jframe, "选择零件", true);
          selectpart.setBaseValueIfc((BaseValueIfc[]) (vector.toArray(new BaseValueIfc[0])));
          selectpart.setCappAssociationsPanel(capppanel);
          selectpart.setCodingIfc(techifc);
          selectpart.insertPartToMultilist();
          selectpart.setVisible(true);
        }
      }
      else {
        if (vector != null) {
          Vector vec1 = getAllPartsRouteText(vector);
          PaintTechnicsPartLinkJFrame linkframe = new
              PaintTechnicsPartLinkJFrame(
              vec1,
              parentframe);
          linkframe.setVisible(true);
        }
      }
      this.dispose();
      ProgressService.hideProgress();
    }
  }

  /**
   * 获得一个艺准通知书关联的所有零部件
   * @param obj TechnicsRouteListInfo
   * @return Vector 关联类的集合
   */
  private BaseValueIfc[] getRouteListParts(TechnicsRouteListInfo obj) {
    BaseValueIfc[] basevalue = null;
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info1 = new ServiceRequestInfo();
    info1.setServiceName("TechnicsRouteService");
    info1.setMethodName("getRouteListParts");
    Class[] paraClass = {
        TechnicsRouteListIfc.class};
    Object[] paraObj = {
        obj};
    info1.setParaClasses(paraClass);
    info1.setParaValues(paraObj);
    try {
      basevalue = (BaseValueIfc[]) server.request(info1);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }
    if (basevalue != null && basevalue.length > 0) {
      return basevalue;
    }
    else {
//      JOptionPane.showMessageDialog(this, "该艺准通知书没有关联的零部件", "警告",
//                                    JOptionPane.ERROR_MESSAGE);
      this.dispose();
    }
    return basevalue;
  }

  /**
   * 获得一个艺准通知书关联的所有零部件
   * @param obj TechnicsRouteListInfo
   * @return Vector 关联类的集合
   */
  private Vector getRouteListPartLinks(TechnicsRouteListInfo obj) {
    Vector v = new Vector();
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info1 = new ServiceRequestInfo();
    info1.setServiceName("TechnicsRouteService");
    info1.setMethodName("getRouteListLinkParts");
    Class[] paraClass = {
        TechnicsRouteListIfc.class};
    Object[] paraObj = {
        obj};
    info1.setParaClasses(paraClass);
    info1.setParaValues(paraObj);
    try {
      v = (Vector) server.request(info1);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }
    if (v != null && v.size() > 0) {
      return v;
    }
    else {
//      JOptionPane.showMessageDialog(this, "该艺准通知书没有关联的零部件", "警告",
//                                    JOptionPane.ERROR_MESSAGE);
      this.dispose();
    }
    return v;
  }

  /**
   * 获得艺准通知书关联的零部件
   * @param v Vector
   * @return Vector 零部件master集合
   */
  private Vector getAllPartsRouteText(Vector v) {
    Vector vec = new Vector();
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info1 = new ServiceRequestInfo();
    info1.setServiceName("TechnicsRouteService");
    info1.setMethodName("getRouteTextByLinkCollection");
    Class[] paraClass = {
        Vector.class};
    Object[] paraObj = {
        v};
    info1.setParaClasses(paraClass);
    info1.setParaValues(paraObj);
    try {
      vec = (Vector) server.request(
          info1);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }
    return vec;
  }

  /**
   * 获得结果域选中的业务对象
   * @return 选中的业务对象
   */
  private Vector getSelectedDetails() {
	  
    int[] rows = qMMultiList.getSelectedRows();
    BaseValueIfc[] vec = new BaseValueIfc[rows.length];
    if (rows != null && rows.length > 0) {
      for (int i = 0; i < rows.length; i++) {
        String bsoid = qMMultiList.getCellText(rows[i], 0);
        BaseValueIfc value = (TechnicsRouteListInfo) resultVector.get(bsoid);
        vec[i] = (value);
      }
    }
//    return vec;
//	  System.out.println("classname ========"+qMMultiList.getSelectedObjects().getClass().getName());
//	  BaseValueIfc[] base1 = (BaseValueIfc[]) qMMultiList.getSelectedObjects();
	  if (vec != null)
      {
          paginatePanel.cachSelected(vec, qMMultiList.getSelectedRows(),
                                     paginatePanel.getCurrentPageNumber());
      }
      Collection collect = paginatePanel.getSelectedObjMap().values();
//      System.out.println("FFF======="+paginatePanel.getSelectedObjMap());
      Iterator iter1 = collect.iterator();
      int size = 0;
      while (iter1.hasNext())
      {
          Object[] obj = (Object[]) iter1.next();
          if (obj != null)
          {
              size = size + obj.length;
          }
      }
      Vector allSelected = new Vector();
      Iterator iter = collect.iterator();
      while (iter.hasNext())
      {
          Object[] obj = (Object[]) iter.next();
          if (obj != null)
          {
              for (int m = 0, n = obj.length; m < n; m++)
              {
//            	  System.out.println("VVV======"+obj[m].getClass().getName());
                  allSelected.add(obj[m]);
              }
          }
      }
//      System.out.println("allSelected.size()======"+allSelected.size());
      return allSelected;
  }

  /**
   * 获得结果域选中的业务对象
   * @return 选中的业务对象
   */
  private BaseValueIfc getSelectedDetail() {
    int row = qMMultiList.getSelectedRow();
    String bsoid = qMMultiList.getCellText(row, 0);
    BaseValueIfc value = (TechnicsRouteListInfo) resultVector.get(bsoid);
    return value;
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
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: 一汽启明</p>
   * @author 刘明
   * @version 1.0
   */
  class SearchThread
      extends QMThread {
    /**判断是否保留原来的搜索结果*/
    private boolean isLeave;

    /**按工装属性搜索界面对象*/
    private TstSearchRouteListJDialog myDialog;

    /**
     * 构造查询实例
     * @param threadgroup 线程组
     * @param dialog 按工装搜索界面对象
     * @param flag 判断是否保留原来的搜索结果
     */
    public SearchThread(ThreadGroup threadgroup,
                        TstSearchRouteListJDialog dialog, boolean flag) {
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

  void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
//    2009.07.09徐德政改，原因：一次性添加多页的零部件
//	  this.addObjectToMultiList(paginatePanel.getCurrentObjects());

      Vector firstPageVector = paginatePanel.getCurrentObjects();
      int[] rows = qMMultiList.getSelectedRows();
      BaseValueIfc[] vec = new BaseValueIfc[rows.length];
      if (rows != null && rows.length > 0) {
        for (int i = 0; i < rows.length; i++) {
          String bsoid = qMMultiList.getCellText(rows[i], 0);
          BaseValueIfc value = (TechnicsRouteListInfo) resultVector.get(bsoid);
          vec[i] = (value);
        }
      }
      if (isMultipleMode() && vec != null && vec.length > 0)
      {
          paginatePanel.cachSelected(vec, qMMultiList.getSelectedRows());
      }
      this.qMMultiList.clear();
      this.addObjectToMultiList(firstPageVector);
//      for (int i = 0; i < firstPageVector.size(); i++)
//      {
//          if (isCancelInProgress())
//          {
//              break;
//          }
//          BaseValueIfc awtobject = (BaseValueIfc) firstPageVector.elementAt(i);
//          qMMultiList.addObject(awtobject);
//      }
      //恢复选中的行为选中状态
      if (isMultipleMode() && paginatePanel.getSelectedRows() != null)
      {
          int[] selectedRows = paginatePanel.getSelectedRows();
          for (int i = 0, j = selectedRows.length; i < j; i++)
          {
        	  qMMultiList.getTable().addRowSelectionInterval(selectedRows[i],
                      selectedRows[i]);
          }
      }
  
//	  end modify by mario in 20090709
  }
  /**
   * 判断结果域单选(false)模式,多选(true)模式
   * @return
   * @author 徐德政
   * @date 2009.07.09
   */
  public boolean isMultipleMode() {
    if (qMMultiList == null) {
      return false;
    }
    else {
      return qMMultiList.isMultipleMode();
    }
  }
}

class TstSearchRouteListJDialog_searchJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private TstSearchRouteListJDialog adaptee;

  TstSearchRouteListJDialog_searchJButton_actionAdapter(
      TstSearchRouteListJDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchJButton_actionPerformed(e);
  }
}

class TstSearchRouteListJDialog_stopJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private TstSearchRouteListJDialog adaptee;

  TstSearchRouteListJDialog_stopJButton_actionAdapter(TstSearchRouteListJDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.stopJButton_actionPerformed(e);
  }
}

class TstSearchRouteListJDialog_okJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private TstSearchRouteListJDialog adaptee;

  TstSearchRouteListJDialog_okJButton_actionAdapter(TstSearchRouteListJDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class TstSearchRouteListJDialog_cancelJButton_actionAdapter
    implements java.awt.event.ActionListener {
  private TstSearchRouteListJDialog adaptee;

  TstSearchRouteListJDialog_cancelJButton_actionAdapter(
      TstSearchRouteListJDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class TstSearchRouteListJDialog_qMMultiList_actionAdapter
    implements java.awt.event.ActionListener {
  private TstSearchRouteListJDialog adaptee;

  TstSearchRouteListJDialog_qMMultiList_actionAdapter(TstSearchRouteListJDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.qMMultiList_actionPerformed(e);
  }
}
