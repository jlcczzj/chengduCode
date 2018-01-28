/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * SS1 A004-2015-3161 ·��״̬��ͬ������ͬ���޷���ȷ��Ӧ���ͻ����뱨��һ�¡� liunan 2015-7-8
 */


package com.faw_qm.cappclients.capproute.view;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;


import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.cappclients.capproute.util.RouteListTreeObject;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import java.util.Collection;
import java.util.Iterator;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * Title:��������·�߱�
 * Description:
 * Copyright: Copyright (c) 2004
* Company: һ������
 * @author ����
 * @version 1.0
 * ������һ�������ҳbean�Ļ��� zz 20061106
 */

public class SearchRouteListJDialog extends JDialog {
    private JPanel panel1 = new JPanel();

    private JTabbedPane jTabbedPane1 = new JTabbedPane();

    private JPanel jPanel1 = new JPanel();

    private JLabel jLabel1 = new JLabel();

    private JTextField numJTextField = new JTextField();

    private JCheckBox numJCheckBox = new JCheckBox();

    private JLabel jLabel2 = new JLabel();

    private JTextField nameJTextField = new JTextField();

    private JCheckBox nameJCheckBox = new JCheckBox();

    private JLabel jLabel3 = new JLabel();

    private JComboBox levelJComboBox = new JComboBox();

    private JLabel jLabel4 = new JLabel();

    private JTextField productJTextField = new JTextField();

    private JCheckBox productJCheckBox = new JCheckBox();

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

    private JLabel jLabel5 = new JLabel();

    private JTextField versionJTextField = new JTextField();

    private JCheckBox versionJCheckBox = new JCheckBox();

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    /** ���Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ���ڱ����Դ��Ϣ */
    protected static ResourceBundle resource = null;

    /** ��ֹ���̱�־ */
    protected boolean cancelInProgress = false;

    /** �߳��� */
    private static ThreadGroup theThreadGroup = Thread.currentThread()
            .getThreadGroup();

    /** ���������� */
    private SearchThread searchThread;

    private HashMap resultVector = new HashMap();

    /** ��׼ͼ�� */
    private static Image startandImage;

    /** ���ͼ�� */
    private static Image checkOutImage;

    /** ԭ��ͼ�� */
    private static Image workingImage;

    private CappRouteListManageJFrame view;

    private JCheckBox appendResultJCheckBox = new JCheckBox();

    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    // ��ѯ���Դ�Сд
    JCheckBox ignorejCheckBox = new JCheckBox();

    /**
     * ���캯��
     *
     * @param frame
     *            ������
     */
    public SearchRouteListJDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
          jbInit();
          pack();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      public SearchRouteListJDialog() {
        this(null, "", false);
      }


    /**
     * �����ʼ��
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
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
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("����");
        jLabel4.setMaximumSize(new Dimension(52, 22));
        jLabel4.setMinimumSize(new Dimension(52, 22));
        jLabel4.setPreferredSize(new Dimension(52, 22));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText("��ز�Ʒ");
        productJCheckBox.setMaximumSize(new Dimension(37, 22));
        productJCheckBox.setMinimumSize(new Dimension(37, 22));
        productJCheckBox.setPreferredSize(new Dimension(37, 22));
        productJCheckBox.setText("��");
        jPanel2.setMaximumSize(new Dimension(70, 70));
        jPanel2.setMinimumSize(new Dimension(70, 70));
        jPanel2.setPreferredSize(new Dimension(70, 70));
        jPanel2.setLayout(gridBagLayout4);
        searchJButton.setMaximumSize(new Dimension(65, 23));
        searchJButton.setMinimumSize(new Dimension(65, 23));
        searchJButton.setPreferredSize(new Dimension(65, 23));
        searchJButton.setToolTipText("Search");
        searchJButton.setText("����");
        searchJButton
                .addActionListener(new SearchRouteListJDialog_searchJButton_actionAdapter(
                        this));
        stopJButton.setMaximumSize(new Dimension(65, 23));
        stopJButton.setMinimumSize(new Dimension(65, 23));
        stopJButton.setPreferredSize(new Dimension(65, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setText("ֹͣ");
        stopJButton
                .addActionListener(new SearchRouteListJDialog_stopJButton_actionAdapter(
                        this));
        jPanel3.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.setEnabled(false);
        okJButton
                .addActionListener(new SearchRouteListJDialog_okJButton_actionAdapter(
                        this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton
                .addActionListener(new SearchRouteListJDialog_cancelJButton_actionAdapter(
                        this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        jLabel5.setMaximumSize(new Dimension(41, 22));
        jLabel5.setMinimumSize(new Dimension(41, 22));
        jLabel5.setPreferredSize(new Dimension(41, 22));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText("�汾");
        versionJCheckBox.setMaximumSize(new Dimension(37, 22));
        versionJCheckBox.setMinimumSize(new Dimension(37, 22));
        versionJCheckBox.setPreferredSize(new Dimension(37, 22));
        versionJCheckBox.setText("��");
        appendResultJCheckBox.setText("���ӽ��");
        ignorejCheckBox.setText("���Դ�Сд");
        //       CCBegin by leixiao 2008-10-10 ԭ�򣺽������,���Դ�СдĬ��ѡ��
        ignorejCheckBox.setSelected(true);
        //       CCEnd by leixiao 2008-10-10 ԭ�򣺽������
        ignorejCheckBox.addActionListener(new SearchRouteListJDialog_jCheckBox1_actionAdapter(this));
    jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(jPanel3,   new GridBagConstraints(0, 4, 3, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                        6, 0, 0), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1,   new GridBagConstraints(0, 0, 2, 2, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 0));
        panel1.add(jPanel2,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 0, 0));
        jTabbedPane1.add(jPanel1, "��������");
        getContentPane().add(panel1, null);
        jPanel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(6,
                        0, 3, 0), 0, 0));
        jPanel1.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(6, 8, 3, 0), 0, 0));
        jPanel1.add(numJCheckBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6,
                        8, 3, 10), 1, 0));
        jPanel1.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(nameJCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                        8, 0, 10), 0, 0));
        jPanel1.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel1.add(levelJComboBox, new GridBagConstraints(1, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        10, 0, 0), 0, 0));
        jPanel1.add(productJTextField, new GridBagConstraints(1, 3, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(productJCheckBox, new GridBagConstraints(2, 3, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 10), 0, 0));
        jPanel1.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3,
                        0, 6, 0), 0, 0));
        jPanel1.add(versionJTextField, new GridBagConstraints(1, 4, 1, 1, 1.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(versionJCheckBox, new GridBagConstraints(2, 4, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(3, 8, 6, 10), 0, 0));
        panel1.add(qMStatus,   new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(appendResultJCheckBox,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    panel1.add(qMMultiList,   new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
    panel1.add(ignorejCheckBox,       new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        localize();
        levelJComboBox.addItem("");
        levelJComboBox.addItem(RouteListLevelType.FIRSTROUTE.getDisplay());
        levelJComboBox.addItem(RouteListLevelType.SENCONDROUTE.getDisplay());
        levelJComboBox.setSelectedItem("");

        paginatePanel
                .addListener(new com.faw_qm.clients.util.PaginateListener() {
                    public void paginateEvent(
                            com.faw_qm.clients.util.PaginateEvent e) {
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
        String[] headings = { "id", "���", "����", "����", "��ز�Ʒ���", "�汾" };
        qMMultiList.setHeadings(headings);
        qMMultiList.setRelColWidth(new int[] { 0, 1, 1, 1, 1, 1 });
        qMMultiList.setMultipleMode(false);
        qMMultiList
                .addActionListener(new SearchRouteListJDialog_qMMultiList_actionAdapter(
                        this));
        qMMultiList.setEnabled(false);

    }

    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation() {
    	this.setSize(650, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

    }

    /**
     * ���ظ��෽����ʹ������ʾ����Ļ����
     *
     * @param flag
     */
    public void setVisible(boolean flag) {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * ���ð�ť�ı༭״̬�������ڲ�ʹ��
     *
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
        } else
            okJButton.setEnabled(true);
    }

    /**
     * ����״̬����Ϣ
     *
     * @param text
     *            ״̬��Ҫ��ʾ���ı�
     */
    private void setStatus(String text) {
        qMStatus.setText(text);
    }

    /**
     * ��ս����
     *
     * @param flag
     *            ���flag = true,����գ�������
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
     *
     * @return �������True������ֹ�̡߳�
     */
    private boolean isCancelInProgress() {
        return cancelInProgress;
    }

    /**
     * �����Ƿ���ֹ�߳�
     *
     * @param flag
     *            ���flag=True������ֹ�̡߳�
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
     *
     * @return ��������
     */
    private Object[][] getCondition() {
       // Object[][] objs = new Object[5][2];
       Object[][] objs = new Object[6][2];
        objs[0][0] = numJTextField.getText().trim();
        objs[0][1] = new Boolean(!numJCheckBox.isSelected());
        objs[1][0] = nameJTextField.getText().trim();
        objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
        objs[2][0] = levelJComboBox.getSelectedItem();
        objs[2][1] = new Boolean(true);
        objs[3][0] = productJTextField.getText().trim();
        objs[3][1] = new Boolean(!productJCheckBox.isSelected());
        objs[4][0] = versionJTextField.getText().trim();
        objs[4][1] = new Boolean(!versionJCheckBox.isSelected());
        objs[5][0] = new Boolean(ignorejCheckBox.isSelected());

        return objs;
    }

    /**
     * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
     *
     * @param flag
     *            ��flag=true������ԭ�������������ϴβ�ѯ���
     */
    private void processSearchThread(boolean flag) {
        if (verbose) {
            System.out
                    .println("part.client.other.view.ExtendSearchJDialog: processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //�Ƿ����ԭ�����
        clearResults(!flag);
        //������һ�������ҳbean�Ļ��� zz 20061106
        paginatePanel.clearResultCache();
        if (!flag)
            resultVector.clear();
        this.setStatus("�����������ݿ�...");
        //��ò�ѯ����
        Object[][] condition = getCondition();
       // try {
            //���÷��񷽷���ѯ����
            Class[] paraClass = { Object[][].class };
            Object[] paraObj = { condition };

            RequestServer server = RequestServerFactory.getRequestServer();
          ServiceRequestInfo info1 = new ServiceRequestInfo();
       info1.setServiceName("TechnicsRouteService");
       info1.setMethodName("findRouteLists");
       Class[] paraClass1 = paraClass;
       info1.setParaClasses(paraClass1);
       Object[] objs1 = paraObj;
       info1.setParaValues(objs1);

       try
                         {
                             Vector totalResult = new Vector(1000);
                             boolean bool = true;
                             while (true)
                             {
                                 Collection result = (Collection) server.
                                                     seriesRequest(info1);
                                 if (result.equals("over"))
                                 {
                                     break;
                                 }
                                 Iterator i = result.iterator();
                                 while(i.hasNext())
                                 {
                                     totalResult.add(i.next());
                                 }
                                 postHandleResult(totalResult);
                                 bool = false;
                                 //����result�߼�
                             }
                             server.close(info1);
                         }
                         catch (QMRemoteException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            if (!isCancelInProgress()) {
                return;
            }
        }

                         catch (Exception e)
                         {
                             server.close(info1); //�ر����ӷ���
                         }





          /* Vector queryresult = (Vector) RParentJPanel.useServiceMethod(
                    "TechnicsRouteService", "findRouteLists", paraClass,
                    paraObj);
            this.postHandleResult(queryresult);*/
        /*} catch (QMRemoteException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            if (!isCancelInProgress()) {
                return;
            }
        }*/
        setCursor(Cursor.getDefaultCursor());
        if (verbose) {
            System.out
                    .println("part.client.other.view.ExtendSearchJDialog: processSearchThread() end...return is void ");
        }
    }

    /**
     * ���������,����������������б���
     *
     * @param searchResult
     *            �������(����·�߱�ֵ����)
     * @throws QMRemoteException
     */
    private void postHandleResult(Vector searchResult) throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("part.client.other.view.ExtendSearchJDialog: postHandleResult() begin...");
        }
        if (searchResult == null || searchResult.size() == 0) {
            setStatus("�ҵ� 0 ������·�߱�");
            return;
        }
        //����б��е�·�߱�����
        if (verbose)
            System.out.println("the result size is:::" + searchResult.size());
        int oldSize = resultVector.size();
        for (int i = 0; i < searchResult.size(); i++) {
            if (isCancelInProgress()) {
                break;
            }
            if (verbose)
                System.out.println("add the object to MultiList start......");
            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) searchResult
                    .elementAt(i);

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
            if (flag == true)
                continue;

            resultVector.put(routelist.getBsoID(), routelist);

        }

        if (!appendResultJCheckBox.isSelected())
            setStatus("�ҵ� " + resultVector.size() + " ������·�߱�");
        else {
            setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " ������·�߱�");
        }

        if (verbose) {
            System.out
                    .println("part.client.other.view.ExtendSearchJDialog: postHandleResult() end...return is void");
        }
    }

    private synchronized void addResultToMultiList() {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        Vector firstPageVector = paginatePanel.paginate(newV,10,true);
        addObjectToMultiList(firstPageVector);
    }

    private void addObjectToMultiList(Vector objs) {
        try {
            qMMultiList.clear();
            if (objs == null || objs.size() == 0)
                return;
            //����������������б���
            int p = objs.size();
            for (int i = 0; i < p; i++) {
                TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) objs
                        .elementAt(i);
                qMMultiList.addTextCell(i, 0, routelist.getBsoID());
                if (((WorkableIfc) routelist).getWorkableState().equals("c/o")) {
                    if (checkOutImage == null)
                        checkOutImage = new ImageIcon(getClass().getResource(
                                routelist.getIconName("CheckOutIcon")))
                                .getImage();
                    qMMultiList.addCell(i, 1, routelist.getRouteListNumber(),
                            checkOutImage);
                } else if (((WorkableIfc) routelist).getWorkableState().equals(
                        "c/i")) {
                    if (startandImage == null)
                        startandImage = new ImageIcon(getClass().getResource(
                                routelist.getIconName("StandardIcon")))
                                .getImage();
                    qMMultiList.addCell(i, 1, routelist.getRouteListNumber(),
                            startandImage);
                } else if (((WorkableIfc) routelist).getWorkableState().equals(
                        "wrk")) {
                    if (workingImage == null)
                        workingImage = new ImageIcon(getClass().getResource(
                                routelist.getIconName("WorkingIcon")))
                                .getImage();
                    qMMultiList.addCell(i, 1, routelist.getRouteListNumber(),
                            workingImage);
                }

                qMMultiList.addTextCell(i, 2, routelist.getRouteListName());
                qMMultiList.addTextCell(i, 3, routelist.getRouteListLevel());
                qMMultiList.addTextCell(i, 4, ((QMPartMasterInfo) RParentJPanel
                        .refreshInfo(routelist.getProductMasterID()))
                        .getPartNumber());
                qMMultiList.addTextCell(i, 5, routelist.getVersionValue());
            }
        } catch (QMRemoteException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * ִ����������
     *
     * @param e
     *            ActionEvent
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
     *
     * @param e
     *            ActionEvent
     */
    void stopJButton_actionPerformed(ActionEvent e) {
        stopJButton.setEnabled(false);
        cancel();
    }

    /**
     * ִ�С�ȷ��������
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
      processOKCommond();
    }

    /**
     * ���÷��񣬻�õ�ǰ·�߱�������㲿������
     *
     * @return �㲿�������ļ���
     * @roseuid 4033530D01BC
     */
    private Vector getRouteListPartLinks(TechnicsRouteListInfo routeListInfo) {
      Vector nv = null;
      if (routeListInfo != null) {
        Class[] c = {
            TechnicsRouteListIfc.class};
        Object[] objs = {
            routeListInfo};
        try {
          nv = (Vector)CappRouteAction.useServiceMethod("TechnicsRouteService",
                                         "getRouteListLinkParts", c, objs);
        }
        catch (QMRemoteException ex) {
          JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "information",
              null), JOptionPane.INFORMATION_MESSAGE);
        }
      }
      return nv;
    }

    private QMPartIfc getlastedpart(QMPartMasterInfo partMasterIfc) {
    	QMPartIfc nv = null;
        if (partMasterIfc != null) {
          Class[] c = {
        		  QMPartMasterIfc.class};
          Object[] objs = {
        		  partMasterIfc};
          try {
            nv = (QMPartIfc)CappRouteAction.useServiceMethod("TechnicsRouteService",
                                           "getLastedPartByConfig", c, objs);
          }
          catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                          QMMessage.getLocalizedMessage(RESOURCE,
                "information",
                null), JOptionPane.INFORMATION_MESSAGE);
          }
        }
        return nv;
      }
    private void processOKCommond() {
        this.dispose();
        //���ѡ�������·�߱�
        Vector selectedPartMastersColl = new Vector();

        //�Ѵ����°汾�Ĺ���·�߱��������
        TechnicsRouteListInfo info=(TechnicsRouteListInfo)this.getSelectedDetail();
        
    //CCBegin SS1
    boolean routestateflag = false;
    //CCEnd SS1
        Vector indexVector = info.getPartIndex();
       // System.out.println(" indexVector="+indexVector);
        HashMap countMap = new HashMap();
        if(indexVector!=null && indexVector.size()>0)
        {
          int size2= indexVector.size();
          for(int k=0;k<size2;k++)
          {
            String[] ids = (String[])indexVector.elementAt(k);
            String key = "";
            //CCBegin SS1
            /*if(countMap.containsKey(ids[0]))
            {
                 key = ids[0]+"K"+k;
            }
            else
            {
                 key = ids[0];
            }*/
        String routestate = "";
        if(ids.length>5&&ids[5]!=null)
        {
        	routestate = ids[5];
        	if(!routestateflag)
        	routestateflag = true;
        }
        if(countMap.containsKey(ids[0]+routestate))
        {
             key = ids[0]+routestate+"K"+k;
        }
        else
        {
             key = ids[0]+routestate;
        }
        System.out.println(key+"=======SearchRouteListJDialog.java========"+ids[2]);
            //CCEnd SS1
            
        //    System.out.println("key = "+key+".........count = "+ids.length+" 1="+ids[0]+" 2="+ids[1]);
//          CCBegin by leixiao 2008-11-5 ԭ�򣺽����������·�� ��������ʷ��û��ids[2]���������´򲻿��˼�  
           if(ids.length>2)
//        	 CCEnd by leixiao 2008-11-5 ԭ�򣺽����������·��
            countMap.put(key,ids[2]);   //����������ظ��Ŀ��ܣ���Ҫ��������
          }
        }
        
        Vector v = this.getRouteListPartLinks(info);
       // Vector v = sortLinks(vold);
        //��ǰ·�߱�������㲿������Ϣ������б�
        //�ѵ�ǰ·�߱�������㲿������Ϣ����
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) v
                .elementAt(i);
            QMPartMasterInfo partMasterIfc = (QMPartMasterInfo) link
                .getPartMasterInfo();
            QMPartIfc partIfc=link.getPartBranchInfo();
            String routeid=link.getRouteID();
            if(partIfc==null){
            	partIfc=getlastedpart(partMasterIfc);	
            }
            String  masterID =partMasterIfc.getBsoID();
            String countInProduct = "";
            //CCBegin SS1
        if(routestateflag)
        {
            String coding;
           if (link.getRouteID() == null || link.getRouteID().length() < 1) {
             coding = "��";
           }
           else {
              coding = getCodingDesc(link.getRouteID());
           }
        	masterID = masterID + coding;
        }
        System.out.println("SearchRouteListJDialog.java  masterID========"+masterID);
            //CCEnd SS1
            if(countMap.containsKey(masterID))
            {
              countInProduct = countMap.get(masterID).toString();
              countMap.remove(masterID);
            }
            else
            {
                for(Iterator jt= countMap.keySet().iterator();jt.hasNext();)
                {
                  String keya = jt.next().toString();
                  if(keya.startsWith(masterID))
                  {
                    masterID = keya;
                    countInProduct = countMap.get(masterID).toString();
                    countMap.remove(masterID);
                    break;
                  }
                }
            }
            System.out.println("partMasterIfc="+partMasterIfc+" partIfc="+partIfc+" countInProduct="+countInProduct+" routeid="+routeid);
            Object [] part={partMasterIfc,partIfc,countInProduct,routeid};
            selectedPartMastersColl.addElement(part);
          
            }

          this.dispose();
          RefreshService.getRefreshService().dispatchRefresh("addSelectedParts",
              0, selectedPartMastersColl);

        }
    }
    
    //CCBegin SS1
      private String getCodingDesc(String routeID) {
	    String code = null;
	    if (routeID != null) {
	      Class[] c = {
	          String.class};
	      Object[] objs = {
	          routeID};
	      try {
	        code = (String)CappRouteAction.useServiceMethod("TechnicsRouteService",
	                                             "getRouteCodeDesc", c, objs);
	      }
	      catch (QMRemoteException ex) {
	        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
	                                      QMMessage.getLocalizedMessage(RESOURCE,
	            "information",
	            null), JOptionPane.INFORMATION_MESSAGE);
	      }
	    }
	    return code;
	  }
	  //CCEnd SS1

    /**
     * ��ý����ѡ�е�ҵ�����
     *
     * @return ѡ�е�ҵ�����
     */
    private BaseValueIfc[] getSelectedDetails() {
        int[] rows = qMMultiList.getSelectedRows();
        BaseValueIfc[] values = new BaseValueIfc[rows.length];
        for (int i = 0; i < rows.length; i++) {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            values[i] = (TechnicsRouteListInfo) resultVector.get(bsoid);
        }
        return values;
    }
    
    private BaseValueIfc getSelectedDetail() {
        int row = qMMultiList.getSelectedRow();
        String bsoid = qMMultiList.getCellText(row, 0);
        BaseValueIfc values = (TechnicsRouteListInfo) resultVector.get(bsoid);

        return values;
    }
    /**
     * ִ�С�ȡ��������
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * <p>
     * Title: �ڲ������߳�
     * </p>
     */
    class SearchThread extends QMThread {
        /** �ж��Ƿ���ԭ����������� */
        private boolean isLeave;

        /** ����װ��������������� */
        private SearchRouteListJDialog myDialog;

        /**
         * �����ѯʵ��
         *
         * @param threadgroup
         *            �߳���
         * @param dialog
         *            ����װ�����������
         * @param flag
         *            �ж��Ƿ���ԭ�����������
         */
        public SearchThread(ThreadGroup threadgroup,
        		SearchRouteListJDialog dialog, boolean flag) {
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
            } catch (Throwable throwable) {
                if (!myDialog.isCancelInProgress()) {
                    throwable.printStackTrace();
                }
            } finally {
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

    /**
     * qMMultiList�Ĵ�����
     * @param e
     */
    void qMMultiList_actionPerformed(ActionEvent e) {
        processOKCommond();
    }

    /**
     * ��MultiList�����Ӷ���
     * @param e com.faw_qm.clients.util.PaginateEvent
     */
    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

  void jCheckBox1_actionPerformed(ActionEvent e) {

  }

}

 /**
  * <p>Title:������ťִ��������</p>
  * <p>Description: </p>
  * <p>Package:com.faw_qm.cappclients.capproute.view</p>
  */
class SearchRouteListJDialog_searchJButton_actionAdapter implements
        java.awt.event.ActionListener {
    private SearchRouteListJDialog adaptee;

    SearchRouteListJDialog_searchJButton_actionAdapter(
            SearchRouteListJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.searchJButton_actionPerformed(e);
    }
}

 /**
  * <p>Title:ֹͣ��ťִ��������</p>
  * <p>Description: </p>
  */
class SearchRouteListJDialog_stopJButton_actionAdapter implements
        java.awt.event.ActionListener {
    private SearchRouteListJDialog adaptee;

    SearchRouteListJDialog_stopJButton_actionAdapter(
            SearchRouteListJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.stopJButton_actionPerformed(e);
    }
}

 /**
  * <p>Title:ȷ����ťִ��������</p>
  * <p>Description: </p>
  */
class SearchRouteListJDialog_okJButton_actionAdapter implements
        java.awt.event.ActionListener {
    private SearchRouteListJDialog adaptee;

    SearchRouteListJDialog_okJButton_actionAdapter(
            SearchRouteListJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.okJButton_actionPerformed(e);
    }
}

 /**
  * <p>Title:ȡ����ťִ��������</p>
  * <p>Description: </p>
  */
class SearchRouteListJDialog_cancelJButton_actionAdapter implements
        java.awt.event.ActionListener {
    private SearchRouteListJDialog adaptee;

    SearchRouteListJDialog_cancelJButton_actionAdapter(
            SearchRouteListJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

 /**
  * <p>Title��qMMultiListִ��������</p>
  * <p>Description: </p>
  */
class SearchRouteListJDialog_qMMultiList_actionAdapter implements
        java.awt.event.ActionListener {
    private SearchRouteListJDialog adaptee;

    SearchRouteListJDialog_qMMultiList_actionAdapter(
    		SearchRouteListJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.qMMultiList_actionPerformed(e);
    }
}

class SearchRouteListJDialog_jCheckBox1_actionAdapter implements java.awt.event.ActionListener {
	SearchRouteListJDialog adaptee;

  SearchRouteListJDialog_jCheckBox1_actionAdapter(SearchRouteListJDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBox1_actionPerformed(e);
  }
}
