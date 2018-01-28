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
 * <p>Title:������׼֪ͨ�飬����·�����Ϳװ���һ������������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: һ������</p>
 * @author ������
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

  /**���ڱ����Դ�ļ�·��*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

  /**���Ա���*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /**���ڱ����Դ��Ϣ*/
  protected static ResourceBundle resource = null;

  /**��ֹ���̱�־*/
  protected boolean cancelInProgress = false;

  /**�߳���*/
  private static ThreadGroup theThreadGroup = Thread.currentThread().
      getThreadGroup();

  /**����������*/
  private SearchThread searchThread;

  private HashMap resultVector = new HashMap();

  /** ��׼ͼ��*/
  private static Image startandImage;

  /** ���ͼ��*/
  private static Image checkOutImage;

  /** ԭ��ͼ��*/
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
   * ���캯��
   * @param frame ������
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
   * ȱʡ�Ĺ��캯��
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
   * �����ʼ��
   * @throws Exception
   */
  private void init() throws Exception {
    this.setTitle("������׼֪ͨ��");
    this.setSize(650, 500);
    panel1.setLayout(gridBagLayout3);
    panel1.setBounds(new Rectangle(0, 0, 650, 500));
    jLabel1.setMaximumSize(new Dimension(24, 22));
    jLabel1.setMinimumSize(new Dimension(24, 22));
    jLabel1.setPreferredSize(new Dimension(24, 22));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("���");
    jPanel1.setLayout(gridBagLayout1);
    numJCheckBox.setMaximumSize(new Dimension(37, 22));
    numJCheckBox.setMinimumSize(new Dimension(37, 22));
    numJCheckBox.setPreferredSize(new Dimension(37, 22));
    numJCheckBox.setText("��");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("����");
    nameJCheckBox.setMaximumSize(new Dimension(37, 22));
    nameJCheckBox.setMinimumSize(new Dimension(37, 22));
    nameJCheckBox.setPreferredSize(new Dimension(37, 22));
    nameJCheckBox.setText("��");
    jPanel2.setMaximumSize(new Dimension(70, 70));
    jPanel2.setMinimumSize(new Dimension(70, 70));
    jPanel2.setPreferredSize(new Dimension(70, 70));
    jPanel2.setLayout(gridBagLayout4);
    searchJButton.setMaximumSize(new Dimension(65, 23));
    searchJButton.setMinimumSize(new Dimension(65, 23));
    searchJButton.setPreferredSize(new Dimension(65, 23));
    searchJButton.setToolTipText("Search");
    searchJButton.setText("����");
    searchJButton.addActionListener(new
                                    TstSearchRouteListJDialog_searchJButton_actionAdapter(this));
    stopJButton.setMaximumSize(new Dimension(65, 23));
    stopJButton.setMinimumSize(new Dimension(65, 23));
    stopJButton.setPreferredSize(new Dimension(65, 23));
    stopJButton.setToolTipText("Stop");
    stopJButton.setText("ֹͣ");
    stopJButton.addActionListener(new
                                  TstSearchRouteListJDialog_stopJButton_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout2);
    okJButton.setMaximumSize(new Dimension(75, 23));
    okJButton.setMinimumSize(new Dimension(75, 23));
    okJButton.setPreferredSize(new Dimension(75, 23));
    okJButton.setToolTipText("Ok");
    okJButton.setMnemonic('Y');
    okJButton.setText("ȷ��(Y)");
    okJButton.setEnabled(false);
    okJButton.addActionListener(new
                                TstSearchRouteListJDialog_okJButton_actionAdapter(this));
    cancelJButton.setMaximumSize(new Dimension(75, 23));
    cancelJButton.setMinimumSize(new Dimension(75, 23));
    cancelJButton.setPreferredSize(new Dimension(75, 23));
    cancelJButton.setToolTipText("Cancel");
    cancelJButton.setMnemonic('C');
    cancelJButton.setText("ȡ��(C)");
    cancelJButton.addActionListener(new
                                    TstSearchRouteListJDialog_cancelJButton_actionAdapter(this));
    qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
    qMStatus.setMaximumSize(new Dimension(4, 22));
    qMStatus.setMinimumSize(new Dimension(4, 22));
    qMStatus.setPreferredSize(new Dimension(4, 22));
    appendResultJCheckBox.setText("���ӽ��");
    jLabelBeginTime.setText("��ʼʱ��");
    jLabelEndTime.setText("����ʱ��");
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
    jTabbedPane1.add(jPanel1, "��������");
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
   * ���ػ�
   */
  private void localize() {
    qMMultiList.setCellEditable(false);
    qMMultiList.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setOkButtonEnabled();
      }
    });
    String[] headings = {
        "id", "���", "����", "����", "��ز�Ʒ���", "�汾"};
    qMMultiList.setHeadings(headings);
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 1});
    qMMultiList.setMultipleMode(true);
    qMMultiList.addActionListener(new
                                  TstSearchRouteListJDialog_qMMultiList_actionAdapter(this));
    qMMultiList.setEnabled(false);

  }

  /**
   * ���ý������ʾλ��
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
   * ���ظ��෽����ʹ������ʾ����Ļ����
   * @param flag
   */
  public void setVisible(boolean flag) {
    this.setViewLocation();
    super.setVisible(flag);
  }

  /**
   * ���ð�ť�ı༭״̬�������ڲ�ʹ��
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
   * ����ȷ����ť��״̬
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
   * ����״̬����Ϣ
   * @param text ״̬��Ҫ��ʾ���ı�
   */
  private void setStatus(String text) {
    qMStatus.setText(text);
  }

  /**
   * ��ս����
   * @param flag ���flag = true,����գ�������
   */
  private synchronized void clearResults(boolean flag) {
    if (flag) {
      qMMultiList.clear();
      okJButton.setEnabled(false);
    }
    setStatus(" ");
  }

  /**
   * �ж��Ƿ���ֹ�����߳�
   * @return �������True������ֹ�̡߳�
   */
  private boolean isCancelInProgress() {
    return cancelInProgress;
  }

  /**
   * �����Ƿ���ֹ�߳�
   * @param flag  ���flag=True������ֹ�̡߳�
   */
  private synchronized void setCancelInProgress(boolean flag) {
    cancelInProgress = flag;
  }

  /**
   * ��ֹ��������
   */
  private synchronized void cancel() {
    if (searchThread != null && searchThread.isAlive()) {
      setCancelInProgress(true);
      //searchThread.interrupt(); //�����ȥ��������Ϊ��ʹ�����˳��ˣ��ⲻ��ҵ��
      searchThread = null;
    }
  }

  /**
   * ����û�¼�����������
   * @return ��������
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
   * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
   * @param flag ��flag=true������ԭ�������������ϴβ�ѯ���
   */
  private void processSearchThread(boolean flag) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    //�Ƿ����ԭ�����
    clearResults(!flag);

    if (!flag) {
      resultVector.clear();
    }
    this.setStatus("�����������ݿ�...");
    //��ò�ѯ����
    Object[][] condition = getCondition();
    try {
      //���÷��񷽷���ѯ����
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
   * ���������,����������������б���
   * @param searchResult �������(����·�߱�ֵ����)
   * @throws QMRemoteException
   */
  private void postHandleResult(Vector searchResult) throws QMRemoteException {

    if (searchResult == null || searchResult.size() == 0) {
      setStatus("�ҵ� 0 ����׼֪ͨ��");
      return;
    }
    //����б��е�·�߱�����
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

      //�ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý���������������б���
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
      setStatus("�ҵ� " + resultVector.size() + " ����׼֪ͨ��");
    }
    else {
      setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " ����׼֪ͨ��");
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
      //����������������б���
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
   * ִ����������
   * @param e ActionEvent
   */
  void searchJButton_actionPerformed(ActionEvent e) {
    setButtons(false);
    //���������߳�
    searchThread = new SearchThread(theThreadGroup, this,
                                    appendResultJCheckBox.isSelected());
    searchThread.start();

  }

  /**
   * ִ�С���ֹ������
   * @param e ActionEvent
   */
  void stopJButton_actionPerformed(ActionEvent e) {
    stopJButton.setEnabled(false);
    cancel();
  }

  /**
   * ִ�С�ȷ��������
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
   * ����ȷ����ť
   */
  private void processOKCommond() {

    //���ѡ�����׼֪ͨ��
    Vector listvec = this.getSelectedDetails();
    int i = JOptionPane.showConfirmDialog(this, "��������㲿���ܶ࣬ʱ���ܳ����Ƿ������", "��ʾ",
                                          JOptionPane.CANCEL_OPTION);
    if (i == JOptionPane.CANCEL_OPTION) {
      return;
    }
    else {
      Vector vector = new Vector();
      for (Iterator iter = listvec.iterator(); iter.hasNext(); ) {
        TechnicsRouteListInfo techinfo = (TechnicsRouteListInfo) iter.next();
        //��Ҫ�㲿������׼����
        if (capppanel != null) {
          BaseValueIfc[] ifc = getRouteListParts(techinfo);
          if (ifc != null && ifc.length > 0) {
            for (int k = 0; k < ifc.length; k++) {
              vector.add(ifc[k]);
            }
          }
        }
        //Ϳװ���һ�������׼����
        else {
//          System.out.println();
          Vector vec = getRouteListPartLinks(techinfo);
          vector.addAll(vec);
        }
      }

      ProgressService.setProgressText("���ڻ�ȡ��׼֪ͨ��������㲿��");
      ProgressService.showProgress();
      if (capppanel != null) {
        if (vector != null) {
          SelectStructPartJDialog selectpart = new SelectStructPartJDialog(
              jframe, "ѡ�����", true);
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
   * ���һ����׼֪ͨ������������㲿��
   * @param obj TechnicsRouteListInfo
   * @return Vector ������ļ���
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
//      JOptionPane.showMessageDialog(this, "����׼֪ͨ��û�й������㲿��", "����",
//                                    JOptionPane.ERROR_MESSAGE);
      this.dispose();
    }
    return basevalue;
  }

  /**
   * ���һ����׼֪ͨ������������㲿��
   * @param obj TechnicsRouteListInfo
   * @return Vector ������ļ���
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
//      JOptionPane.showMessageDialog(this, "����׼֪ͨ��û�й������㲿��", "����",
//                                    JOptionPane.ERROR_MESSAGE);
      this.dispose();
    }
    return v;
  }

  /**
   * �����׼֪ͨ��������㲿��
   * @param v Vector
   * @return Vector �㲿��master����
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
   * ��ý����ѡ�е�ҵ�����
   * @return ѡ�е�ҵ�����
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
   * ��ý����ѡ�е�ҵ�����
   * @return ѡ�е�ҵ�����
   */
  private BaseValueIfc getSelectedDetail() {
    int row = qMMultiList.getSelectedRow();
    String bsoid = qMMultiList.getCellText(row, 0);
    BaseValueIfc value = (TechnicsRouteListInfo) resultVector.get(bsoid);
    return value;
  }

  /**
   * ִ�С�ȡ��������
   * @param e ActionEvent
   */
  void cancelJButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  /**
   * <p>Title: �ڲ������߳�</p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: һ������</p>
   * @author ����
   * @version 1.0
   */
  class SearchThread
      extends QMThread {
    /**�ж��Ƿ���ԭ�����������*/
    private boolean isLeave;

    /**����װ���������������*/
    private TstSearchRouteListJDialog myDialog;

    /**
     * �����ѯʵ��
     * @param threadgroup �߳���
     * @param dialog ����װ�����������
     * @param flag �ж��Ƿ���ԭ�����������
     */
    public SearchThread(ThreadGroup threadgroup,
                        TstSearchRouteListJDialog dialog, boolean flag) {
      super(threadgroup);
      myDialog = dialog;
      isLeave = flag;
    }

    /**
     * ʹ�߳��жϣ�ֹͣ��������
     */
    public synchronized void interrupt() {
      if (verbose) {
        System.out.println("Inside Interrupt......");
      }
      this.interrupt();
    }

    /**
     * �߳����з�����ִ��������
     */
    public synchronized void run() {
      if (verbose) {
        System.out.println("SearchThread: run() begin...");
      }
      try {
        //ִ������
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
        //���ý��水ť����ʾ״̬
        myDialog.setButtons(true);
        //����û�д����ж�״̬
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
//    2009.07.09������ģ�ԭ��һ������Ӷ�ҳ���㲿��
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
      //�ָ�ѡ�е���Ϊѡ��״̬
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
   * �жϽ����ѡ(false)ģʽ,��ѡ(true)ģʽ
   * @return
   * @author �����
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
