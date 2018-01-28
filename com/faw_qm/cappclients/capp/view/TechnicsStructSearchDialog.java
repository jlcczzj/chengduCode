package com.faw_qm.cappclients.capp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.util.Enumeration;
import java.util.HashMap;

import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.clients.beans.query.QMStatus;
import com.faw_qm.clients.util.MultiList;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.TextAndImageCell;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.util.QMCt;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.cappclients.util.CalendarSelectedPanel;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.cappclients.capp.view.CappSortingSelectedPanel;
import com.faw_qm.cappclients.util.CappMultiList;
import java.awt.*;
//CCBegin by dikefeng 20090705
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.acl.ejb.entity.QMPermission;
//CCBegin by dikefeng 20090705

/**
 * <p>Title:����װ�������͹��ս��� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 */

public class TechnicsStructSearchDialog
    extends JDialog {
  private JPanel panel1 = new JPanel();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private TitledBorder titledBorder1;
  private JPanel queryConditionPanel = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel workshopLabel = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();

  //tangshutao 20080521 begin ����������������״̬
  private JLabel lifecyclestatelabel = new JLabel();
  private JComboBox lifecyclestateComboBox = new JComboBox();
  private Hashtable lifeCycleStateTable = null;

  //end
  //modify by sdg on 20061114 (���豸���,��װ���,���ϱ������,�����*�ȱ��ʱ,��*1ʱҪ����Ӧ��ʾ.)
  //private JTextField toolNumTextField = new JTextField();
  private CappTextField toolNumTextField;

  //modify end
  private JTextField versionTextField = new JTextField();
  private JComboBox stateComboBox = new JComboBox();
  private JCheckBox typeCheckBox = new JCheckBox();
  private JCheckBox versionCheckBox = new JCheckBox();
  private JCheckBox stateCheckBox = new JCheckBox();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel searchButtonPanel = new JPanel();
  private TitledBorder titledBorder2;
  private JButton searchButton = new JButton();
  private JButton searchPartButton = new JButton();
  private JPanel okButtonPanel = new JPanel();
  private TitledBorder titledBorder3;
  private JButton okButton = new JButton();
  private JButton quitButton = new JButton();
  private TitledBorder titledBorder4;
  private JButton stopButton = new JButton();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private GridBagLayout gridBagLayout5 = new GridBagLayout();
  private MultiList multiList1 = new MultiList();
  private JCheckBox appendResultCheckBox = new JCheckBox();
  private String partBsoID = "";
  private Hashtable productStateTable = null;
  //CCBegin by dikefeng 20090705
  FolderPanel folderPanel=new FolderPanel();
  //CCEnd by dikefeng 20090705
  
  /**���multiList�еĶ���*/
  private Vector resultVector = new Vector();

  /**���ڱ����Դ�ļ�·��*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capp.util.CappLMRB";

  /**���Ա���*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /**���ڱ����Դ��Ϣ*/
  protected static ResourceBundle resource = null;

  /**��ֹ���̱�־*/
  protected boolean cancelInProgress = false;
  private QMStatus qMStatus = new QMStatus();

  /**����������*/
  private ToolLinkSearchThread searchThread;

  /**�߳���*/
  private static ThreadGroup theThreadGroup =
      Thread.currentThread().getThreadGroup();

  /**���ڴ����������״̬���ؼ��֣�״̬��������������״̬*/
  private Hashtable table;

  /**
   * ���չ��������
   */
  private TechnicsRegulationsMainJFrame view;

  /** ��׼ͼ��*/
  private static ImageIcon startandImage;

  /** ���ͼ��*/
  private static ImageIcon checkOutImage;

  private CalendarSelectedPanel beginTime = new CalendarSelectedPanel();
  private CalendarSelectedPanel endTime = new CalendarSelectedPanel();
  private JTextField userJtext = new JTextField();
  JLabel begintimeLabel = new JLabel();
  JLabel endtimeLabel = new JLabel();
  JLabel creatorLabel = new JLabel();
  JLabel descriptionLabel = new JLabel();
  private HashMap map = new HashMap();

  /** ԭ��ͼ��*/
  private static ImageIcon workingImage;
  private SortingSelectedPanel technicsTypeSortingSelectedPanel = null;
  private CappSortingSelectedPanel workshopSelectedPanel = null;
  private PaginatePanel paginatePanel = new PaginatePanel();

  public TechnicsStructSearchDialog(TechnicsRegulationsMainJFrame frame) {
    super(frame, "", true);
    view = frame;
    try {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    String title1 = QMMessage.getLocalizedMessage(RESOURCE,
                                                  CappLMRB.TECHNICSTYPE, null);
    technicsTypeSortingSelectedPanel = new SortingSelectedPanel(title1,
        "QMTechnicsMaster", "technicsType");
    workshopSelectedPanel = new CappSortingSelectedPanel("���ղ���", "QMTechnics",
        "workShop");
    workshopSelectedPanel.setIsSelectCC(true);
    workshopSelectedPanel.setIsOnlyCC(true);
    technicsTypeSortingSelectedPanel.setDialogTitle(title1);
    workshopSelectedPanel.setDialogTitle("���ղ���");
    technicsTypeSortingSelectedPanel.setButtonSize(89, 23);
    workshopSelectedPanel.setButtonSize(89, 23);
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    titledBorder4 = new TitledBorder("");
    panel1.setLayout(gridBagLayout4);
    getContentPane().setLayout(gridBagLayout5);
    panel1.setToolTipText("");
    jLabel1.setText("*�㲿��");
    queryConditionPanel.setLayout(gridBagLayout1);
    jLabel2.setText("��������");
    workshopLabel.setText("����");
    jLabel3.setText("���հ汾");
    jLabel4.setText("��Ʒ״̬");
    //tangshutao 20080521 ������������״̬
    lifecyclestatelabel.setText("��������״̬");
    //end
    //CCBegin by dikefeng 20090705
    folderPanel.setFolderJLabelName("���ϼ�");
    folderPanel.setIsPersonalFolder(false);
    folderPanel.setIsPublicFolders(false);
    folderPanel.setPermission(QMPermission.READ);
    //CCEnd by dikefeng 20090705
    begintimeLabel.setText("��ʼʱ��");
    endtimeLabel.setText("��ֹʱ��");
    creatorLabel.setText("������");
    descriptionLabel.setText("<-�û�ȫ��");
    queryConditionPanel.setToolTipText("");
    typeCheckBox.setText("��");
    versionCheckBox.setText("��");
    stateCheckBox.setText("��");
    searchButtonPanel.setLayout(gridBagLayout2);
    searchPartButton.setText("�Ѽ�(P)");
    searchPartButton.setMaximumSize(new Dimension(75, 23));
    searchPartButton.setMinimumSize(new Dimension(75, 23));
    searchPartButton.setPreferredSize(new Dimension(75, 23));
    searchPartButton.setMnemonic('P');
    searchPartButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchPartButton_actionPerformed(e);
      }
    });

    searchButton.setMaximumSize(new Dimension(75, 23));
    searchButton.setMinimumSize(new Dimension(75, 23));
    searchButton.setPreferredSize(new Dimension(75, 23));
    searchButton.setMnemonic('F');
    searchButton.setText("����(F)");
    searchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchButton_actionPerformed(e);
      }
    });
    //add by sdg on 20061114 (���豸���,��װ���,���ϱ������,�����*�ȱ��ʱ,��*1ʱҪ����Ӧ��ʾ.)
    toolNumTextField = new CappTextField(view,
                                         "�����", 100, false);
    //add end
    toolNumTextField.setEditable(false);
    okButtonPanel.setLayout(gridBagLayout3);
    okButton.setMaximumSize(new Dimension(75, 23));
    okButton.setMinimumSize(new Dimension(75, 23));
    okButton.setPreferredSize(new Dimension(75, 23));
    okButton.setText("ȷ��");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    quitButton.setMaximumSize(new Dimension(75, 23));
    quitButton.setMinimumSize(new Dimension(75, 23));
    quitButton.setPreferredSize(new Dimension(75, 23));
    quitButton.setText("�˳�");
    quitButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        quitButton_actionPerformed(e);
      }
    });
    stopButton.setMaximumSize(new Dimension(75, 23));
    stopButton.setMinimumSize(new Dimension(75, 23));
    stopButton.setPreferredSize(new Dimension(75, 23));
    stopButton.setMnemonic('S');
    stopButton.setText("ֹͣ(S)");
    stopButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        stopButton_actionPerformed(e);
      }
    });
    appendResultCheckBox.setText("���ӽ��");
    panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(8, 0, 4, 0), 0, 0));
    panel1.add(searchButtonPanel,
               new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                      , GridBagConstraints.CENTER,
                                      GridBagConstraints.VERTICAL,
                                      new Insets(16, 0, 0, 0), 1, 1));
    jTabbedPane1.add(queryConditionPanel, "��������");
    searchButtonPanel.add(stopButton,
                          new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.NORTH,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 8, 8, 8), 0, 0));
    searchButtonPanel.add(searchButton,
                          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.VERTICAL,
                                                 new Insets(16, 8, 8, 8), 1, 1));
    panel1.add(okButtonPanel, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
        , GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 8, 0), 0, 0));
    okButtonPanel.add(paginatePanel,
                      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(4, 0, 0, 0), 0, 0));
    okButtonPanel.add(okButton, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(4, 0, 0, 0), 0, 0));
    okButtonPanel.add(quitButton,
                      new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.NONE,
                                             new Insets(4, 8, 0, 8), 0, 0));
    panel1.add(multiList1, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(4, 0, 0, 8), 0, 0));
    panel1.add(appendResultCheckBox,
               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                      , GridBagConstraints.WEST,
                                      GridBagConstraints.NONE,
                                      new Insets(0, 0, 0, 0), 0, 0));
    getContentPane().add(qMStatus,
                         new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
    getContentPane().add(panel1,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 8, 0, 0), 0, 0));
    queryConditionPanel.add(jLabel1,
                            new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 5, 0, 4), 0, 0));
    queryConditionPanel.add(jLabel2,
                            new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(workshopLabel,
                            new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    //tangshutao 20080530 ������������״̬
    queryConditionPanel.add(lifecyclestatelabel,
                            new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(lifecyclestateComboBox,
                            new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(0, 8, 4, 0), 0, 0));
    //CCBegin by dikefeng 20090705
    queryConditionPanel.add(folderPanel,
                            new GridBagConstraints(0, 9,3, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 36, 5, 0), 0, 0));

//CCBegin by dikefeng 20090705
    //tangshutao end

    queryConditionPanel.add(jLabel3,
                            new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(jLabel4,
                            new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(begintimeLabel,
                            new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(endtimeLabel,
                            new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(creatorLabel,
                            new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(descriptionLabel,
                            new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 4), 0, 0));
    queryConditionPanel.add(userJtext,
                            new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 8, 2, 4), 0, 0));
    queryConditionPanel.add(beginTime,
                            new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 8, 2, 4), 0, 0));
    queryConditionPanel.add(endTime,
                            new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 8, 2, 4), 0, 0));
    queryConditionPanel.add(stateComboBox,
                            new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 8, 4, 0), 0, 0));
    queryConditionPanel.add(versionTextField,
                            new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 8, 0, 0), 0, 0));
    queryConditionPanel.add(toolNumTextField,
                            new GridBagConstraints(1, 0, 1, 1, 8.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 8, 5, 0), 0, 0));
    queryConditionPanel.add(searchPartButton,
                            new GridBagConstraints(2, 0, 1, 1, 0.3, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 8, 5, 0), 0, 0));
    //queryConditionPanel.add(typeCheckBox,
    //new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
    //, GridBagConstraints.CENTER, GridBagConstraints.NONE,
    //new Insets(7, 11, 0, 0), 0, 0));
    queryConditionPanel.add(versionCheckBox,
                            new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(7, 11, 0, 0), 0, 0));
    //queryConditionPanel.add(stateCheckBox,
    // new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
    //, GridBagConstraints.CENTER, GridBagConstraints.NONE,
    //new Insets(7, 11, 0, 0), 0, 0));
    queryConditionPanel.add(technicsTypeSortingSelectedPanel,
                            new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 0, 5, 0), 0, 0));
    queryConditionPanel.add(workshopSelectedPanel,
                            new GridBagConstraints(1, 7, 2, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 0, 5, 0), 0, 0));
    multiList1.setCellEditable(false);
    multiList1.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setOkButtton();
      }
    });
    String[] headings = {
        "ID", "���ձ��", "��������", "��������", "�汾��", };
    multiList1.setHeadings(headings);
    multiList1.setRelColWidth(new int[] {0, 1, 1, 1, 1});
    multiList1.setAllowSorting(true);
    multiList1.setMultipleMode(true);
    setTitle("�������չ��");
    okButton.setEnabled(false);

    versionTextField.setVisible(false);
    versionCheckBox.setVisible(false);
    jLabel3.setVisible(false);
    localize();
  }

  /**
   * ���ػ�
   */
  private void localize() throws QMRemoteException {
    initProductStateTable();
    initLifeCycleStateTable();
    table = new Hashtable();
    //��Ʒ״̬��������ӿ���
    stateComboBox.addItem("");
    if (productStateTable != null) {
      Enumeration e = productStateTable.keys();
      while (e.hasMoreElements()) {
        stateComboBox.addItem(e.nextElement());
      }
    }
    //��������״̬��������ӿ���
    lifecyclestateComboBox.addItem("");
    if (lifeCycleStateTable != null) {
      Enumeration e = lifeCycleStateTable.keys();
      while (e.hasMoreElements()) {
        lifecyclestateComboBox.addItem(e.nextElement());
      }
    }

    if (resource == null) {
      initResources();
    }

    try {
      okButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
          "OkJButton", null));
      quitButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
          "QuitJButton", null));
    }
    catch (MissingResourceException ex) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
      JOptionPane.showMessageDialog(this,
                                    CappLMRB.MISSING_RESOURCER,
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);

    }
  }

  /**
   *��ʼ����ʹ�õ���Դ����Ϣ��
   */
  protected void initResources() {
    try {
      if (resource == null) {
        resource = ResourceBundle.getBundle(RESOURCE,
                                            QMCt.getContext().getLocale());

      }
    }
    catch (MissingResourceException mre) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
      JOptionPane.showMessageDialog(this,
                                    CappLMRB.MISSING_RESOURCER,
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
      return;
    }
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
   * ���ظ��෽����ʹ������ʾ����Ļ����
   * @param flag
   */
  public void setVisible(boolean flag) {
    setSize(1024, 768);
    setViewLocation();
    super.setVisible(flag);
  }

  public static void main(String[] args) {
    String sid =
        "A2qjNwo7z0QnKposqMOQtoVbsX5XXEH335bItxmLs1Fi1RtsuyhE!127363950!1077340707975".
        trim();
    CappClientRequestServer server = new CappClientRequestServer("xj", "80",
        sid);
    RequestServerFactory.setRequestServer(server);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {

    }

    TechnicsStructSearchDialog frame = new TechnicsStructSearchDialog(null);
    frame.setSize(650, 450);
    frame.setVisible(true);

  }

  /**
   * ��������ļ��еĹ�װ����
   * @return ��װ����(�ַ�����ʽ)����
   */
  public Vector getTechnicsType() throws QMRemoteException {
    Class[] paramclass = new Class[] {
        String.class, String.class};
    Object[] paramobject = new Object[] {
        "��������", "��������Ϣ"};
    Collection c = (Collection) TechnicsAction.useServiceMethod(
        "CodingManageService", "getCoding", paramclass, paramobject);
    Vector v = new Vector(c);
    return v;
  }

  //������ť
  void searchButton_actionPerformed(ActionEvent e) {
    //add by sdg on 20061114 (���豸���,��װ���,���ϱ������,�����*�ȱ��ʱ,��*1ʱҪ����Ӧ��ʾ.)
    if (!toolNumTextField.check()) {
      return;
    }
    //add end

    //��������
    if (toolNumTextField.getText() == null ||
        toolNumTextField.getText().trim().equals("")) {
      String message = new String("�㲿����Ų���Ϊ��");
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this, message, title,
                                    JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    setButtons(false);
    //���������߳�
    searchThread = new ToolLinkSearchThread(theThreadGroup, this,
                                            appendResultCheckBox.isSelected());
    searchThread.start();
  }

  /**
   * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
   * @param flag ��flag=true��������ϴβ�ѯ������������ϴβ�ѯ��������
   */
  public void processToolLinkSearch(boolean flag) {
    if (verbose) {
      System.out.println("cappclients.capp.view.TechnicsSearchFromToolJDialog: processExtendSearch()...begin");
    }
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    setStatus(QMMessage.getLocalizedMessage(RESOURCE,
                                            "jMenuSearchTechnics", null));
    if (!flag) {
      resultVector.clear();
      //��ò�ѯ����
    }

    Vector condition = getCondition();
    Class[] paraClass = {
        Vector.class};
    Object[] paraObj = {
        condition};
    Vector queryresult = null;
    try {
      setStatus(QMMessage.getLocalizedMessage(RESOURCE,
                                              "jMenuSearchTechnics", null));
      queryresult = (Vector) CappClientHelper.useServiceMethod(
          "StandardCappService", "selectTechnicsByPartAndWorkshop",
          paraClass, paraObj);
      System.out.println("client reslut=" + queryresult.size());

    }
    catch (QMRemoteException e) {
      if (verbose) {
        System.out.println("Exception processing the query - Cancel = " +
                           cancelInProgress + "--" +
                           e.getLocalizedMessage());
        e.printStackTrace();
      }
      String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
      JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                                    JOptionPane.INFORMATION_MESSAGE);
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      if (!isCancelInProgress()) {
        return;
      }

    }
    multiList1.clear();
    postHandleResult(queryresult);
    setCursor(Cursor.getDefaultCursor());
    if (verbose) {
      System.out.println("cappclients.capp.view.TechnicsSearchFromToolJDialog: processExtendSearch() end...return is void ");
    }
  }

  /**
   *��ò�ѯ����
   */
  public Vector getCondition() {
    Vector v = new Vector();
    v.add(this.partBsoID);
    if (technicsTypeSortingSelectedPanel.getCoding() == null) {
      v.add("");
    }
    else {
      v.add(technicsTypeSortingSelectedPanel.getCoding().getBsoID());
    }
    if (stateComboBox.getSelectedItem().toString().equals("")) {
      v.add("");
    }
    else {
      v.add( ( (CodingIfc) productStateTable.get(stateComboBox.getSelectedItem())).
            getBsoID());
    }
    if (beginTime.getDate() == null) {
      v.add("");
    }
    else {
      v.add(beginTime.getDate());
    }
    if (endTime.getDate() == null) {
      v.add("");
    }
    else {
      v.add(endTime.getDate());
    }
    if (userJtext.getText() == null) {
      v.add("");
    }
    else {
      v.add(userJtext.getText());
    }
    if (workshopSelectedPanel.getCoding() != null) {
      v.add(workshopSelectedPanel.getCoding().getBsoID());
    }
    else {
      v.add("");

    }
    //tangshutao 20080530 ������������״̬
    if (lifecyclestateComboBox.getSelectedItem().toString().equals("")) {
      v.add("");
    }
    else {
      v.add(lifeCycleStateTable.get(lifecyclestateComboBox.getSelectedItem()));
    }
    //CCBegin by dikefeng 20090705
    //9
    if(folderPanel.getFolderLocation()!=null&&folderPanel.getFolderLocation().length()>0)
      v.add(folderPanel.getFolderLocation());
      else
      v.add("");
      //CCEnd by dikefeng 20090705
    
    //tangshutao end
    if (verbose) {
      System.out.println("v=" + v);
    }
    return v;
  }

  /**
   * ��ս����
   * @param flag ���flag = true,����գ�������
   */
  public void clearResults(boolean flag) {
    if (verbose) {
      System.out.println(
          "cappclients.capp.view.TechnicsSearchFromToolJDialog: clearResults() begin...");
    }

    if (flag) {
      multiList1.clear();
      okButton.setEnabled(false);
    }
    setStatus(" ");
    if (verbose) {
      System.out.println("cappclients.capp.view.TechnicsSearchFromToolJDialog: clearResults() end...return is void");
    }
  }

  /**
   * ����״̬����Ϣ
   * @param text ״̬��Ҫ��ʾ���ı�
   */
  public void setStatus(String text) {
    if (qMStatus != null) {
      qMStatus.setValue(text);
    }

  }

  /**
   * �ж��Ƿ���ֹ�����߳�
   * @return �������True������ֹ�̡߳�
   */
  public boolean isCancelInProgress() {
    return cancelInProgress;
  }

  /**
   * �����Ƿ���ֹ�߳�
   * @param flag  ���flag=True������ֹ�̡߳�
   */
  public synchronized void setCancelInProgress(boolean flag) {
    if (verbose) {
      System.out.println("Setting cancelInProgress to: " + flag);
    }
    cancelInProgress = flag;
  }

  /**
   * ���������,����������������б���
   * @param searchResult �������
   */
  protected void postHandleResult(Vector searchResult) {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExtendSearchJDialog: postHandleResult() begin...");
    }
    if (searchResult == null || searchResult.size() == 0) {
      qMStatus.setValue("�ҵ�0����¼");
      return;
    }

    //����б��еĹ�������
    if (verbose) {
      System.out.println("the result size is:::" + searchResult.size());
    }
    //Vector tempVector = new Vector();
    //tempVector.addAll(resultVector);
    multiList1.clear();
    map.clear();
    for (int j = 0; j < searchResult.size(); j++) {
      QMTechnicsIfc resultobject = (QMTechnicsIfc) searchResult.elementAt(j);
      //�ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý��������������������
      resultVector.addElement(resultobject);
      multiList1.addTextCell(j, 0, resultobject.getBsoID());
      multiList1.addTextCell(j, 1, resultobject.getTechnicsNumber());
      multiList1.addTextCell(j, 2, resultobject.getTechnicsName());
      multiList1.addTextCell(j, 3,
                             resultobject.getTechnicsType().getCodeContent());
      multiList1.addTextCell(j, 4, resultobject.getVersionValue());
      map.put(resultobject.getBsoID(), resultobject);
    }

    if (verbose) {
      System.out.println("part.client.other.view.ExtendSearchJDialog: postHandleResult() end...return is void");
    }
  }

  /**
   * ��ֹ��������
   */
  private synchronized void cancel() {
    if (verbose) {
      System.out.println(
          "cappclients.capp.view.TechnicsSearchFromToolJDialog: cancel() begin...");
    }
    if (searchThread != null && searchThread.isAlive()) {
      setCancelInProgress(true);
      //searchThread.interrupt(); //�����ȥ��������Ϊ��ʹ�����˳��ˣ��ⲻ��ҵ��
      searchThread = null;
    }
    if (verbose) {
      System.out.println("cappclients.capp.view.TechnicsSearchFromToolJDialog: cancel() end...return is void");
    }
  }

  /**
   * ���ð�ť�ı༭״̬�������ڲ�ʹ��
   * @param flag
   */
  public void setButtons(boolean flag) {
    if (verbose) {
      System.out.println(
          "cappclients.capp.view.TechnicsSearchFromToolJDialog: setButtons() begin...");
    }
    searchButton.setEnabled(flag);
    stopButton.setEnabled(!flag);

    if (getSelectedDetails() == null) {
      okButton.setEnabled(false);
    }
    quitButton.setEnabled(flag);
  }

  public void setOkButtton() {
    if (getSelectedDetails() == null) {
      okButton.setEnabled(false);

    }
    else {
      okButton.setEnabled(true);
    }
  }

  /**
   * <p>Title: �ڲ������߳�</p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: һ������</p>
   * @author ����
   * @version 1.0
   */
  class ToolLinkSearchThread
      extends QMThread {
    /**�ж��Ƿ���ԭ�����������*/
    private boolean isLeave;

    /**����װ���������������*/
    private TechnicsStructSearchDialog myDialog;

    /**
     * �����ѯʵ��
     * @param threadgroup �߳���
     * @param dialog ����װ�����������
     * @param flag �ж��Ƿ���ԭ�����������
     */
    public ToolLinkSearchThread(ThreadGroup threadgroup,
                                TechnicsStructSearchDialog dialog,
                                boolean flag) {
      super(threadgroup);
      myDialog = dialog;
      isLeave = flag;

    }

    /**
     * ʹ�߳��жϣ�ֹͣ��������
     */
    public void interrupt() {
      if (verbose) {
        System.out.println("Inside Interrupt......");
      }
      interrupt();
    }

    /**
     * �߳����з�����ִ��������
     */
    public void run() {
      if (verbose) {
        System.out.println(
            "cappclients.capp.view.TechnicsSearchFromToolJDialog: run() begin...");
      }
      try {
        //ִ������
        myDialog.processToolLinkSearch(isLeave);
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
        //myDialog.addResultToMultiList();
        //���ý��水ť����ʾ״̬
        myDialog.setButtons(true);
        //����û�д����ж�״̬
        myDialog.setCancelInProgress(false);
      }
      if (verbose) {
        System.out.println("cappclients.capp.view.TechnicsSearchFromToolJDialog: run() end...return is void");
      }
    }
  }

  void stopButton_actionPerformed(ActionEvent e) {
    stopButton.setEnabled(false);
    cancel();
  }

  void okButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    dispose();
    //���ѡ��������㲿��
    if (view != null) {
      int[] objs = multiList1.getSelectedRows();
      TechnicsTreeObject selectedObject = null;
      for (int i = 0; i < objs.length; i++) {
        String bsoid = multiList1.getCellText(objs[i], 0);
        QMTechnicsInfo info = (QMTechnicsInfo) map.get(bsoid);
        TechnicsTreeObject treeObject = new TechnicsTreeObject(info);
        if (i == 0) {
          selectedObject = (TechnicsTreeObject) treeObject;
        }
        view.getProcessTreePanel().addNodeToTheTreeEnd(treeObject);

      }
    }

    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * ��ý����ѡ�е�ҵ�����
   * @return ѡ�е�ҵ�����
   */
  public BaseValueIfc[] getSelectedDetails() {
    int[] rows = multiList1.getSelectedRows();
    BaseValueIfc[] values = new BaseValueIfc[rows.length];
    for (int i = 0; i < rows.length; i++) {
      values[i] = (BaseValueIfc) ( (TextAndImageCell) multiList1.
                                  getCellAt(rows[i], 0)).getObject();
    }
    return values;
  }

  /**
   * �˳�
   * @param e ActionEvent
   */
  void quitButton_actionPerformed(ActionEvent e) {
    dispose();
  }

  public void searchPartButton_actionPerformed(ActionEvent e) {
    searchPartButton.setEnabled(false);
    String s = "�����㲿��";
    String s1 = "QMPart"; //getOtherSideClassName();
    CappChooser qmchooser = new CappChooser(s1, s, this);
    //20060926Ѧ���޸ģ�����������ȥ���Ӳ�ѯ������

    qmchooser.setLastIteration(true);
    qmchooser.setSize(650, 500);
    String s3 = "C:QMPart; G:" + "��������" + ";";
    //String as[] = getOtherSideAttributes();
    qmchooser.setChildQuery(false);
    try {
      qmchooser.setMultipleMode(false);
    }
    catch (Exception e2) {
      e2.printStackTrace();
      return;
    }
    //qmchooser.setRelColWidth(relcols);
    qmchooser.addListener(new CappQueryListener() {
      public void queryEvent(CappQueryEvent qmqueryevent) {
        if (qmqueryevent.getType().equals(CappQueryEvent.COMMAND) &&
            qmqueryevent.getCommand().equals(CappQuery.OkCMD)) {
          CappChooser qmchooser1 = (CappChooser) qmqueryevent.
              getSource();
          BaseValueIfc base = qmchooser1.getSelectedDetail();
          //BaseValueIfc finalawtobject[];
          try {
            if (base != null) {
              toolNumTextField.setText( ( (QMPartIfc) base).getPartNumber());
              partBsoID = ( (QMPartIfc) base).getBsoID();
            }
            return;
          }
          catch (Exception _e) {
            _e.printStackTrace();
            return;
          }
        }
        else {
          return;
        }
      }
    });

    this.setLocationRelativeTo(qmchooser);
    //setLocation(qmchooser);
    qmchooser.setVisible(true);
    searchPartButton.setEnabled(true);
    setEnabled(true);

  }

  public void addSelectedObject(BaseValueIfc basevalueinfo) throws
      IllegalAccessException, NoSuchMethodException,
      InstantiationException,
      ClassNotFoundException {

  }

  public void initProductStateTable() {
    //�������
    String name = "��Ʒ״̬";
    String parent = "��������Ϣ";
    Class[] paraclass = {
        String.class, String.class};
    Object[] paraobj = {
        name, parent};
    Collection c = null;
    try {

      c = (Collection) CappClientHelper.useServiceMethod("CodingManageService",
          "getCoding",
          paraclass, paraobj);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }

    if (c != null && c.size() != 0) {
      productStateTable = new Hashtable();
      for (Iterator i = c.iterator(); i.hasNext(); ) {
        CodingIfc coding = (CodingIfc) i.next();
        productStateTable.put(coding.getCodeContent(), coding);
      }
    }
  }

  /**
   * tangshutao 20080530 ������������״̬
   */
  private void initLifeCycleStateTable() {
    lifeCycleStateTable = new Hashtable();
    lifeCycleStateTable.put("����", "BIANZHI");
    lifeCycleStateTable.put("�޸�", "CHANGE");
    lifeCycleStateTable.put("��У��", "DAIJIAODUI");
    lifeCycleStateTable.put("�����", "DAISHENHE");
    lifeCycleStateTable.put("����ǩ", "DAIHUIQIAN");
    lifeCycleStateTable.put("����׼", "DAIPIZHUN");
    lifeCycleStateTable.put("�ѷ���", "YIFABU");
    lifeCycleStateTable.put("����", "FEIQI");
  }

}
