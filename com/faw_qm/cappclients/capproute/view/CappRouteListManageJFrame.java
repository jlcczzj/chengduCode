/**
 * ���ɳ��� CappRouteListManageJFrame.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 *CR1 ������  2009/05/06    ԭ��: �㹤��·�����Ҽ��󣬳��ֿ�ݲ˵����������뵱ǰ
 *                                ���λ�ò�һ�£���������û������        
 *                          ����: ���Ҽ�ʱ�����ǰ���ڵ��ѡ��,ͨ������ѡ�н�
 *                                �㲢������ݲ˵�.   
 *                                
 *CR2 ������  2009/06/04    �μ�:������:v4r3FunctionTest;TD��2277
 * 
 * 
 * SS1 ��Ӹ����� liunan 2015-6-18
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import com.faw_qm.cappclients.capproute.controller.CappRouteListManageController;
import com.faw_qm.cappclients.capproute.util.*;
import com.faw_qm.clients.beans.explorer.QMToolBar;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.util.QMCt;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.clients.beans.explorer.QMMenu;
import com.faw_qm.clients.beans.explorer.QMMenuItem;

/**
 * Title:��׼/�ձ�֪ͨ�������
 * Description:��׼/�ձ�֪ͨ�������������
 * Copyright: Copyright (c) 2004
 * Company: һ������
 * @author ����
 * @mender skybird
 * @version 1.0
 *   //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·�ߣ�����·�߱�����׼֪ͨ��
 * (����һ) zz  ������� 20061215 �������������
 */

public class CappRouteListManageJFrame extends JFrame implements ActionListener {
    private JPanel contentPane;

    private JMenuBar jMenuBar1 = new JMenuBar();

    private QMMenu jMenuFile = new QMMenu("�ļ�", new MyMouseListener());

    private QMMenuItem jMenuExit = new QMMenuItem("�˳�", new MyMouseListener());
    //(����һ) zz  ������� 20061215 ������������� start
    private QMMenuItem jMenuRename = new QMMenuItem("������", new MyMouseListener());
    //(����һ) zz  ������� 20061215 ������������� end

    private QMMenu jMenuHelp = new QMMenu("����", new MyMouseListener());

    private QMMenuItem jMenuHelpAbout = new QMMenuItem("����. . .",
            new MyMouseListener());

    private QMMenuItem jMenuHelp1 = new QMMenuItem("����", new MyMouseListener());

    /** ������ */
    private QMToolBar qmToolBar = new QMToolBar();

    /** ����ָ���� */
    private JSplitPane jSplitPane = new JSplitPane();

    private JLabel statusBar = new JLabel();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel leftJPanel = new JPanel();

    private JPanel rightJPanel = new JPanel();

    private JLabel jLabel1 = new JLabel();

    private RouteTreePanel routeTreePanel;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JLabel stateJLabel = new JLabel();

    private JPanel jPanel1 = new JPanel();

    private RouteListTaskJPanel routeListTaskJPanel = new RouteListTaskJPanel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    /** ���ڱ����Դ */
    protected static ResourceBundle resource = null;

    private QMMenuItem jMenuCreate = new QMMenuItem("������׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuUpdate = new QMMenuItem("����", new MyMouseListener());

    private QMMenuItem jMenuUpdate1 = new QMMenuItem("����",
            new MyMouseListener());

    private QMMenuItem jMenuDelete = new QMMenuItem("ɾ����׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuDelete1 = new QMMenuItem("ɾ����׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuClear = new QMMenuItem("�����׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuClear1 = new QMMenuItem("�����׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuRefresh = new QMMenuItem("ˢ��",
            new MyMouseListener());

    private QMMenuItem jMenuRefresh1 = new QMMenuItem("ˢ��",
            new MyMouseListener());

    private QMMenu jMenuVersion = new QMMenu("�汾", new MyMouseListener());

    private QMMenuItem jMenuCheckIn = new QMMenuItem("����",
            new MyMouseListener());

    private QMMenuItem jMenuCheckOut = new QMMenuItem("���",
            new MyMouseListener());

    private QMMenuItem jMenuUndoCheckOut = new QMMenuItem("�������",
            new MyMouseListener());

    private QMMenuItem jMenuRevise = new QMMenuItem("�޶�", new MyMouseListener());

    private QMMenuItem jMenuVersionHis = new QMMenuItem("�鿴�汾��ʷ",
            new MyMouseListener());

    private QMMenuItem jMenuIteratorHis = new QMMenuItem("�鿴������ʷ",
            new MyMouseListener());

    private QMMenu jMenuBrowse = new QMMenu("���", new MyMouseListener());

    private QMMenuItem jMenuView = new QMMenuItem("�鿴��׼/�ձ�֪ͨ��",
            new MyMouseListener());

    //st skybird 2005.2.25
    private QMMenuItem jMenuConfigRule = new QMMenuItem("�޸����ù���",
            new MyMouseListener());

    private QMMenuItem jMenuRoute = new QMMenuItem("�ۺ�·��",
            new MyMouseListener());

    private QMMenuItem jMenuSearchBy = new QMMenuItem("����λ�����㲿��",
            new MyMouseListener());

    //ed
    private QMMenuItem jMenuView1 = new QMMenuItem("�鿴��׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuSearch = new QMMenuItem("������׼/�ձ�֪ͨ��",
            new MyMouseListener());

    private QMMenuItem jMenuEditRoute = new QMMenuItem("�༭����·��",
            new MyMouseListener());

    private QMMenuItem jMenuEditRoute1 = new QMMenuItem("�༭����·��",
            new MyMouseListener());

    private QMMenuItem jMenuReport = new QMMenuItem("���ɱ���",
            new MyMouseListener());
    
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  ȥ���˹��ܡ�2010-1-14��leix      
//    private QMMenuItem jMenuChange = new QMMenuItem("�����Ϣ����", new MyMouseListener());
  //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"
    
  //CCBegin by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"        
    private QMMenuItem jMenuChangeFolder = new QMMenuItem("�������ϼ�", new MyMouseListener());
  //CCEnd by leixiao 2009-8-20 ԭ�򣺽����������·��,����"�����Ϣ����"

    private QMMenuItem jMenuHelpRouteList = new QMMenuItem("����",
            new MyMouseListener());

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private QMMenu jMenuLifeCycle = new QMMenu("��������", new MyMouseListener());

    private QMMenuItem jMenuResetState = new QMMenuItem("����ָ����������״̬",
            new MyMouseListener());

    private QMMenuItem jMenuResetLifeCycle = new QMMenuItem("����ָ����������",
            new MyMouseListener());

    private QMMenuItem jMenuLifeCycleHis = new QMMenuItem("�鿴����������ʷ",
            new MyMouseListener());

    private QMMenuItem jMenuResetProject = new QMMenuItem("����ָ��������",
            new MyMouseListener());


    /** ������ */
    protected CappRouteListManageController controller;

    /** ����Ƿ�Ĭ����ʾ�鿴·�߱���� */
    public static boolean isViewRouteList = true;

    /** �����˳�ʱ�Ƿ��˳�ϵͳ */
    public boolean isExitSystem = true;

    /** �����˵� */
    private JPopupMenu popup = new JPopupMenu();
    /**
     * ���캯��
     */
    public CappRouteListManageJFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���캯��
     * @param isExitSystem �����˳�ʱ�Ƿ��˳�ϵͳ�����Ϊtrue�����˳�ϵͳ
     */
    public CappRouteListManageJFrame(boolean isExitSystem) {
        this();
        this.isExitSystem = isExitSystem;
    }

    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit() throws Exception {
        controller = new CappRouteListManageController(this);
        routeTreePanel = new RouteTreePanel("��׼/�ձ�֪ͨ��");
        routeListTaskJPanel.setVisible(false);
        //CCBegin SS1
        routeListTaskJPanel.setFrame(this);
        //CCEnd SS1
        ResourceBundle rb = getPropertiesRB();
        String str1[] = this.getValueSet(rb, "toolbar.icons");
        String str2[] = this.getValueSet(rb, "toolbar.text");
        String str3[] = this.getValueSet(rb, "toolbar.discribe");
        this.setTools(str1, str2, str3);
        //skybird
        URL url = CappRouteListManageJFrame.class
                .getResource("/images/routeM.gif");
        if (url != null) {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));
        }
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, dimension.width, dimension.height - 25);
        this.setTitle("��׼/�ձ�֪ͨ�������");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.setText("��ӭ������׼/�ձ�֪ͨ�������. . .");
        jMenuFile.setText("�ļ�(F)");
        jMenuFile.setMnemonic('F');
        jMenuExit.setText("�˳�(X)");
        jMenuExit.setMnemonic('X');
        KeyStroke ksExit = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                Event.CTRL_MASK);
        jMenuExit.setAccelerator(ksExit);
        //(����һ) zz  ������� 20061215 ������������� start
         jMenuRename.setText("������(C)");
         jMenuRename.setMnemonic('C');
         KeyStroke ksRename = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                 Event.CTRL_MASK);
         jMenuRename.setAccelerator(ksRename);
         //(����һ) zz  ������� 20061215 ������������� end

        jMenuHelp.setText("����(H)");
        jMenuHelp.setMnemonic('H');
        jMenuHelpAbout.setText("����(A)...");
        jMenuHelpAbout.setMnemonic('A');
        jMenuHelp1.setToolTipText("");
        jMenuHelp1.setText("��׼/�ձ�֪ͨ�������(H)...");
        jMenuHelp1.setMnemonic('H');
        KeyStroke SS = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        jMenuHelp1.setAccelerator(SS);
        leftJPanel.setLayout(gridBagLayout1);
        jLabel1.setBorder(BorderFactory.createEtchedBorder());
        jLabel1.setText(" ��׼/�ձ�֪ͨ��");
        rightJPanel.setLayout(gridBagLayout2);
        stateJLabel.setBorder(BorderFactory.createEtchedBorder());
        stateJLabel.setText("��׼/�ձ�֪ͨ�������");
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(gridBagLayout3);
        routeListTaskJPanel.setMaximumSize(new Dimension(343, 2147483647));
        jMenuCreate.setText("����(N)...");
        jMenuCreate.setMnemonic('N');
        KeyStroke ksCreate = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Event.CTRL_MASK);
        jMenuCreate.setAccelerator(ksCreate);
        jMenuUpdate.setText("����(M)...");
        jMenuUpdate.setMnemonic('M');
        KeyStroke ksUpdate = KeyStroke.getKeyStroke(KeyEvent.VK_M,
                Event.CTRL_MASK);
        jMenuUpdate.setAccelerator(ksUpdate);
        jMenuUpdate1.setText("����(M)");
        jMenuUpdate1.setMnemonic('M');
        jMenuUpdate1.setAccelerator(ksUpdate);
        jMenuDelete.setText("ɾ��(D)");
        jMenuDelete.setMnemonic('D');
        KeyStroke ksDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D,
                Event.CTRL_MASK);
        jMenuDelete.setAccelerator(ksDelete);
        jMenuDelete1.setText("ɾ��(D)");
        jMenuDelete1.setMnemonic('D');
        jMenuDelete1.setAccelerator(ksDelete);
        jMenuClear.setText("���(A)");
        jMenuClear.setMnemonic('A');
        // KeyStroke ksClear =
        // KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK);
        //  jMenuClear.setAccelerator(ksClear);
        jMenuClear1.setText("���(A)");
        jMenuClear1.setMnemonic('A');
        //  jMenuClear1.setAccelerator(ksClear);
        jMenuRefresh.setText("ˢ��(E)");
        jMenuRefresh.setMnemonic('E');
        //   KeyStroke ksRefresh =
        // KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK);
        // jMenuRefresh.setAccelerator(ksRefresh);
        jMenuRefresh1.setText("ˢ��(E)");
        jMenuRefresh1.setMnemonic('E');
        // jMenuRefresh1.setAccelerator(ksRefresh);
        jMenuVersion.setText("�汾(V)");
        jMenuVersion.setMnemonic('V');
        jMenuCheckIn.setText("����(I). . .");
        jMenuCheckIn.setMnemonic('I');
        KeyStroke ksCheckIn = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                Event.CTRL_MASK);
        jMenuCheckIn.setAccelerator(ksCheckIn);
        jMenuCheckOut.setText("���(O). . .");
        jMenuCheckOut.setMnemonic('O');
        KeyStroke ksCheckOut = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                Event.CTRL_MASK);
        jMenuCheckOut.setAccelerator(ksCheckOut);
        jMenuUndoCheckOut.setText("�������(U)");
        jMenuUndoCheckOut.setMnemonic('U');
        KeyStroke ksUndocheckOut = KeyStroke.getKeyStroke(KeyEvent.VK_U,
                Event.CTRL_MASK);
        jMenuUndoCheckOut.setAccelerator(ksUndocheckOut);
        jMenuRevise.setText("�޶�(R)");
        jMenuRevise.setMnemonic('R');
        // KeyStroke ksRevise =
        // KeyStroke.getKeyStroke(KeyEvent.VK_R,Event.CTRL_MASK);
        // jMenuRevise.setAccelerator(ksRevise);
        jMenuVersionHis.setText("�鿴�汾��ʷ(V)");
        jMenuVersionHis.setMnemonic('V');
        //  KeyStroke ksVersionHis =
        // KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK);
        //  jMenuVersionHis.setAccelerator(ksVersionHis);
        jMenuIteratorHis.setText("�鿴������ʷ(S)");
        jMenuIteratorHis.setMnemonic('S');
        // KeyStroke ksIteratorHis =
        // KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK);
        // jMenuIteratorHis.setAccelerator(ksIteratorHis);
        jMenuBrowse.setText("���(B)");
        jMenuBrowse.setMnemonic('B');
        jMenuView.setText("�鿴(V)");
        jMenuView.setMnemonic('V');
        KeyStroke ksView = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                Event.CTRL_MASK);
        jMenuView.setAccelerator(ksView);
        //st skybird 2005.2.25
        jMenuConfigRule.setText("�޸����ù���(M). . .");
        jMenuConfigRule.setActionCommand("�޸����ù���(M)");
        jMenuConfigRule.setMnemonic('M');
        //   KeyStroke ksConfigRule =
        // KeyStroke.getKeyStroke(KeyEvent.VK_M,Event.CTRL_MASK);
        // jMenuConfigRule.setAccelerator(ksConfigRule);
        jMenuRoute.setText("�ۺ�·��(C). . .");
        jMenuRoute.setMnemonic('C');
        // KeyStroke ksRoute =
        // KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
        // jMenuRoute.setAccelerator(ksRoute);
        jMenuSearchBy.setText("��·�ߵ�λ����(D). . .");
        jMenuSearchBy.setMnemonic('D');
        // KeyStroke ksSearchBy =
        // KeyStroke.getKeyStroke(KeyEvent.VK_D,Event.CTRL_MASK);
        // jMenuSearchBy.setAccelerator(ksSearchBy);
        //ed
        jMenuSearch.setText("����(S). . .");
        jMenuSearch.setMnemonic('S');
        KeyStroke ksSearch = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Event.CTRL_MASK);
        jMenuSearch.setAccelerator(ksSearch);
        jMenuView1.setText("�鿴(V)");
        jMenuView1.setMnemonic('V');
        jMenuView1.setAccelerator(ksView);
        jMenuEditRoute.setText("�༭·��(R)...");
        jMenuEditRoute.setMnemonic('R');
        KeyStroke ksEditRoute = KeyStroke.getKeyStroke(KeyEvent.VK_R,
                Event.CTRL_MASK);
        jMenuEditRoute.setAccelerator(ksEditRoute);
        jMenuEditRoute1.setText("�༭·��(R)...");
        jMenuEditRoute1.setMnemonic('R');
        jMenuEditRoute1.setAccelerator(ksEditRoute);
        jMenuReport.setText("���ɱ���(R). . .");
        jMenuReport.setMnemonic('R');
        //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"
//        jMenuChange.setText("�����Ϣ����(F). . .");
//        jMenuChange.setMnemonic('F');
         //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"   
        //CCBegin by leixiao 2008-9-20 ԭ�򣺽����������·��,����"ת��"
        jMenuChangeFolder.setText("�������ϼ�. . .");
        jMenuChangeFolder.setMnemonic('M');
        //CCEnd by leixiao 2008-9-20 ԭ�򣺽����������·��,����"ת��"   
        // KeyStroke ksReport =
        // KeyStroke.getKeyStroke(KeyEvent.VK_R,Event.CTRL_MASK);
        //  jMenuReport.setAccelerator(ksReport);
        jMenuHelpRouteList.setText("������׼/�ձ�֪ͨ�����(R)");
        jMenuHelpRouteList.setMnemonic('R');
        jMenuHelpRouteList.setVisible(false);
        jMenuLifeCycle.setText("��������(L)");
        jMenuLifeCycle.setMnemonic('L');
        jMenuResetState.setText("����ָ����������״̬(S)");
        jMenuResetState.setMnemonic('S');
        //  KeyStroke ksResetState =
        // KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK);
        //  jMenuResetState.setAccelerator(ksResetState);
        jMenuResetLifeCycle.setText("����ָ����������(C)");
        jMenuResetLifeCycle.setMnemonic('C');
        //KeyStroke ksResetLifeCycle =
        // KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
        // jMenuResetLifeCycle.setAccelerator(ksResetLifeCycle);
        jMenuLifeCycleHis.setText("�鿴����������ʷ(H)");
        jMenuLifeCycleHis.setMnemonic('H');
        // KeyStroke ksLifeCycleHis =
        // KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK);
        // jMenuLifeCycleHis.setAccelerator(ksLifeCycleHis);
        jMenuResetProject.setText("����ָ��������(P)");
        jMenuResetProject.setMnemonic('P');
        // KeyStroke ksResetProject =
        // KeyStroke.getKeyStroke(KeyEvent.VK_P,Event.CTRL_MASK);
        // jMenuResetProject.setAccelerator(ksResetProject);
        jSplitPane.setContinuousLayout(true);
        jMenuFile.add(jMenuCreate);
        jMenuFile.add(jMenuUpdate);
        jMenuFile.add(jMenuEditRoute);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuDelete);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuRefresh);
        jMenuFile.add(jMenuClear);
        jMenuFile.addSeparator();
        //zz
        jMenuFile.add(jMenuRename);
        jMenuFile.addSeparator();
        //zz
        //CCBegin by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"  
        jMenuFile.add(jMenuChangeFolder);
        jMenuFile.addSeparator();
        //CCEnd by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"  
        jMenuFile.add(jMenuExit);
        jMenuHelp.add(jMenuHelpRouteList);
        jMenuHelp.add(jMenuHelp1);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenuBrowse);
        jMenuBar1.add(jMenuVersion);
        jMenuBar1.add(jMenuLifeCycle);
        jMenuBar1.add(jMenuHelp);
        this.setJMenuBar(jMenuBar1);
        contentPane.add(qmToolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        jSplitPane.setResizeWeight(1.0);
        jSplitPane.setDividerLocation(260);
        contentPane.add(jSplitPane, BorderLayout.CENTER);
        jSplitPane.add(leftJPanel, JSplitPane.LEFT);
        leftJPanel.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        leftJPanel.add(routeTreePanel, new GridBagConstraints(0, 1, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        jSplitPane.add(rightJPanel, JSplitPane.RIGHT);
        rightJPanel.add(stateJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        rightJPanel.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        jPanel1.add(routeListTaskJPanel, new GridBagConstraints(0, 0, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 0, 5), 0, 0));
        jMenuVersion.add(jMenuCheckIn);
        jMenuVersion.add(jMenuCheckOut);
        jMenuVersion.add(jMenuUndoCheckOut);
        jMenuVersion.addSeparator();
        jMenuVersion.add(jMenuRevise);
        jMenuVersion.addSeparator();
        jMenuVersion.add(jMenuVersionHis);
        jMenuVersion.add(jMenuIteratorHis);
        //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����" 
//        jMenuVersion.add(this.jMenuChange);
        //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����" 
        //st skybird 2005.2.25
        jMenuBrowse.add(jMenuConfigRule);
        //ed
        jMenuBrowse.add(jMenuView);
        jMenuBrowse.add(jMenuSearch);
        jMenuBrowse.add(jMenuSearchBy);
        // jMenuBrowse.add(jMenuRoute);zz��ʱ��մ˹���1
        jMenuBrowse.add(jMenuReport);
        jMenuLifeCycle.add(jMenuResetState);
        jMenuLifeCycle.add(jMenuResetLifeCycle);
        jMenuLifeCycle.add(jMenuLifeCycleHis);
        jMenuLifeCycle.addSeparator();
        jMenuLifeCycle.add(jMenuResetProject);

        //{{ע��˵��Ķ�������
        MenuAction menuAction = new MenuAction();
        jMenuExit.addActionListener(menuAction);
        jMenuRename.addActionListener(menuAction); //zz
        jMenuHelpAbout.addActionListener(menuAction);
        jMenuHelp1.addActionListener(menuAction);
        jMenuCreate.addActionListener(menuAction);
        jMenuUpdate.addActionListener(menuAction);
        jMenuDelete.addActionListener(menuAction);
        jMenuClear.addActionListener(menuAction);
        jMenuRefresh.addActionListener(menuAction);
        jMenuCheckIn.addActionListener(menuAction);
        jMenuCheckOut.addActionListener(menuAction);
        jMenuUndoCheckOut.addActionListener(menuAction);
        jMenuRevise.addActionListener(menuAction);
        jMenuVersionHis.addActionListener(menuAction);
        jMenuIteratorHis.addActionListener(menuAction);
        jMenuView.addActionListener(menuAction);
        //st skybird 2005.2.25
        jMenuConfigRule.addActionListener(menuAction);
        jMenuSearchBy.addActionListener(menuAction);
        jMenuRoute.addActionListener(menuAction);
        //ed
        jMenuSearch.addActionListener(menuAction);
        jMenuEditRoute.addActionListener(menuAction);
        jMenuReport.addActionListener(menuAction);
        //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����" 
//        jMenuChange.addActionListener(menuAction);        
        //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����" 
//      CCBegin by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��" 
        jMenuChangeFolder.addActionListener(menuAction);
//      CCEnd by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��" 
        jMenuHelpRouteList.addActionListener(menuAction);
        jMenuResetState.addActionListener(menuAction);
        jMenuResetLifeCycle.addActionListener(menuAction);
        jMenuLifeCycleHis.addActionListener(menuAction);
        jMenuResetProject.addActionListener(menuAction);

        jMenuUpdate1.addActionListener(menuAction);
        jMenuView1.addActionListener(menuAction);
        jMenuEditRoute1.addActionListener(menuAction);
        jMenuDelete1.addActionListener(menuAction);
        jMenuRefresh1.addActionListener(menuAction);
        jMenuClear1.addActionListener(menuAction);
        //}}

        //{{Ϊ·�߱���ע�����
        RootListTreeSelectionListener treeSelectListener = new RootListTreeSelectionListener();
        routeTreePanel.addTreeSelectionListener(treeSelectListener);
        RouteListTreeMouseListener treeMouseListener = new RouteListTreeMouseListener();
        routeTreePanel.addTreeMouseListener(treeMouseListener);
        //}}

        popup.add(jMenuUpdate1);
        popup.add(jMenuView1);
        popup.add(jMenuEditRoute1);
        popup.addSeparator();
        popup.add(jMenuDelete1);
        popup.addSeparator();
        popup.add(jMenuRefresh1);
        popup.add(jMenuClear1);
        setNullMenu();
    }
    
    //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,����"��·��չ��"
    public void myrefresh() {
        controller.processRefreshCommand();
      }
    //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,����"��·��չ��"

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        //super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            controller.processExitCommand();
        }
    }

    /**
     * ��ʼ����ʹ�õ���Դ����Ϣ��
     */
    protected void initResources() {
        try {
            //skybird
            if (resource == null) {
                resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
                        .getLocale());
            }
        } catch (MissingResourceException mre) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(null, CappRouteRB.MISSING_RESOURCER,
                    title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    /**
     * �����Դ��Ϣ
     *
     * @return ResourceBundle ��Դ��
     */
    protected ResourceBundle getPropertiesRB() {
        if (resource == null) {
            initResources();
        }
        return resource;
    }

    /**
     * ��ð�ť��Ϣ��ʾ����,����������
     *
     * @param as1
     * @param as2
     * @param as3
     *            3����������
     */
    public void setTools(String as1[], String as2[], String as3[]) {
        String myTools[] = as1;
        for (int i = 0; i < myTools.length; i++) {
            qmToolBar.addButton(myTools[i], as2[i], as3[i], this,
                    new MyMouseListener1());
        }
        for (int ii = 0; ii < qmToolBar.getComponentCount(); ii++) {
            if (qmToolBar.getComponentAtIndex(ii) instanceof JButton) {
                JButton jb = (JButton) (qmToolBar.getComponentAtIndex(ii));
                jb.setBorder(BorderFactory.createEtchedBorder());
            }
        }
        refresh();
    }

    /**
     * ��ResourceBundle��ȡ��key �������StringTokenizer
     * ��󱣴��String[]
     * @param rb
     * @param key
     * @return String[]
     */
    protected String[] getValueSet(ResourceBundle rb, String key) {
        String[] values = null;
        try {
            String value = rb.getString(key);
            //The string tokenizer class allows an application to break
            //a string into tokens
            StringTokenizer st = new StringTokenizer(value, ",");

            int count = st.countTokens();
            values = new String[count];

            for (int i = 0; i < count; i++) {
                values[i] = st.nextToken();
            }
        } catch (MissingResourceException mre) {
            mre.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * ���������ϵİ�ť������.���û�����������ϵİ�ťʱ�ᷢ��һ������,
     * �������� �밴ťͼƬ������һ��
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        this.refresh();
        this.closeContentPanel();
        String name = e.getActionCommand();
        if (name.equals("routeList_create")) {
            controller.processCreateCommand();
        } else if (name.equals("routeList_update")) {
            controller.processUpdateCommand();
        } else if (name.equals("routeList_delete")) {
            controller.processDeleteCommand();
        } else if (name.equals("routeList_checkIn")) {
            controller.processCheckInCommand();
        } else if (name.equals("routeList_checkOut")) {
            controller.processCheckOutCommand();
        } else if (name.equals("routeList_repeal")) {
            controller.processUndoCheckOutCommand();
        } else if (name.equals("routeList_view")) {
            controller.processViewCommand();
        } else if (name.equals("public_search")) {
            controller.processBrowseCommand();
        } else if (name.equals("route_edit")) {
            controller.processEditCommand();
        } else if (name.equals("public_help")) {
            controller.processHelp1Command();
        }
    }

    /**
     * ˢ��������
     */
    public void refresh() {
        this.invalidate();
        this.doLayout();
        this.repaint();
    }

    /**
     * ���������еĲ˵������ڴ�ע��
     * <p>
     * ���ݵ�ǰ��ѡ��Ĳ˵���ҵ�����ȷ���������ʾ����
     * </p>
     * <p>
     * Copyright: Copyright (c) 2003
     * </p>
     * <p>
     * Company: һ������
     * </p>
     *
     * @author ����
     * @version 1.0
     */
    class MenuAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object object = e.getSource();
            CappRouteListManageJFrame.this.refresh();
            CappRouteListManageJFrame.this.closeContentPanel();
            if (object == jMenuCreate) {
                controller.processCreateCommand();
            } else if (object == jMenuUpdate || object == jMenuUpdate1) {
                controller.processUpdateCommand();
            } else if (object == jMenuDelete || object == jMenuDelete1) {
                controller.processDeleteCommand();
            } else if (object == jMenuRefresh || object == jMenuRefresh1) {
                controller.processRefreshCommand();
            } else if (object == jMenuClear || object == jMenuClear1) {
                controller.processClearCommand();
            } else if (object == jMenuExit) {
              controller.processExitCommand(); }
              else if (object == jMenuRename ) {
              controller.processRenameCommand();

            } else if (object == jMenuView || object == jMenuView1) {
                controller.processViewCommand();
            }
            //st skybird 2005.2.25
            else if (object == jMenuConfigRule) {
                controller.processConfigRuleCommand();
            } else if (object == jMenuRoute) {
                controller.processCompositiveRouteCommand();
            } else if (object == jMenuSearchBy) {
                controller.processSearchByCommand();
            }
            //ed
            else if (object == jMenuSearch) {
                controller.processBrowseCommand();
            } else if (object == jMenuEditRoute || object == jMenuEditRoute1) {
                controller.processEditCommand();
            } else if (object == jMenuReport) {
                controller.processReportCommand();
                //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"     
//            }else if (object == jMenuChange) {
//                controller.processChangeCommand();
            //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"   
            }
//          CCBegin by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"    
            else if (object == jMenuChangeFolder) {
            controller.processChangeFolderCommand();
//          CCEnd by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"  
            } else if (object == jMenuCheckIn) {
                controller.processCheckInCommand();
            } else if (object == jMenuCheckOut) {
                controller.processCheckOutCommand();
            } else if (object == jMenuUndoCheckOut) {
                controller.processUndoCheckOutCommand();
            } else if (object == jMenuRevise) {
                controller.processReviseCommand();
            } else if (object == jMenuVersionHis) {
                controller.processViewVersionCommand();
            } else if (object == jMenuIteratorHis) {
                controller.processViewIteratorCommand();
            } else if (object == jMenuResetState) {
                controller.processResetStateCommand();
            } else if (object == jMenuResetLifeCycle) {
                controller.processResetLifeCycleCommand();
            } else if (object == jMenuLifeCycleHis) {
                controller.processViewLifeCycleCommand();
            } else if (object == jMenuResetProject) {
                controller.processResetProjectCommand();
            } else if (object == jMenuHelpRouteList) {
                controller.processHelpManageCommand();
            } else if (object == jMenuHelpAbout) {
                controller.processHelpAboutCommand();
            } else if (object == jMenuHelp1) {
                controller.processHelp1Command();
            }
        }
    } ////MenuAction END

    /**
     * <p>
     * Title:·���б����Ľڵ�ѡ�����
     * </p>
     * <p>
     * �����ڵ��ѡ��ֵ�仯ʱ,���²˵�״̬,�����鿴·�߱����
     * </p>
     * <p>
     * Copyright: Copyright (c) 2004
     * </p>
     * <p>
     * Company: һ������
     * </p>
     *
     * @author ����
     * @version 1.0
     */
    class RootListTreeSelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            RouteTreeObject obj = CappRouteListManageJFrame.this.getTreePanel()
                    .getSelectedObject();
          
            enableMenuItems(obj);
          
            //���ѡ�нڵ���RouteListTreeObject,�򵯳��鿴����
            if (obj != null && obj instanceof RouteListTreeObject) {
                CappRouteListManageJFrame.this.refresh();
                CappRouteListManageJFrame.this.closeContentPanel();
//              CCBegin by leixiao 2008-11-4 ԭ�򣺽����������·��,�޷�������·��Ĭ�ϲ鿴·��                 
//              if(!CappRouteListManageJFrame.this.routeListTaskJPanel.isSave)
//                  return;
//            CCEnd by leixiao 2008-11-4 ԭ�򣺽����������·��   
                //if(!isSaveRoute)
                //    return;
                //skybird
                CappRouteListManageJFrame.this.setState(RParentJPanel
                        .getIdentity(obj.getObject()));
                TechnicsRouteListIfc tmp = (TechnicsRouteListIfc) obj
                        .getObject();
                statusBar.setText("��ǰѡ�е���׼/�ձ�֪ͨ�飺 " + tmp.getRouteListName());
                if (isViewRouteList) {
                    //573
                    controller.viewDefaultRouteList();
                }
            }
            else {
            	CappRouteListManageJFrame.this.closeContentPanel();//CR2
                CappRouteListManageJFrame.this.setState("");
                CappRouteListManageJFrame.this.getTaskPanel().setVisible(false);
            }
        }
    }

    /**
     *·���б�����������
     */
    class RouteListTreeMouseListener extends MouseAdapter {
        /**
         * Invoked when the mouse has been clicked on a component.
         */
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * Invoked when a mouse button has been pressed on a component.
         */
        public void mousePressed(MouseEvent e) {
        }

        /**
         * Invoked when a mouse button has been released on a component.
         */
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                if (e.getSource() instanceof RouteTree) {
                    RouteTree tree = (RouteTree) e.getSource();
                    RouteListTreeObject treeObj = (RouteListTreeObject) tree
                            .getSelectedObject();
                    if (treeObj != null) {
                        popup.show(tree, e.getX(), e.getY());
                       //setPopupState();
                    }
                    if (e.getButton() == 3)//Begin CR1
    				{
    						tree.getSelectionModel().clearSelection();
    						int selRow = tree.getRowForLocation(e.getX(), e.getY());
    						if (selRow != -1)
    						{
    							tree.addSelectionRow(selRow);
    						}

    						popup.repaint();

    				}                       //Begin CR1
                }
            }
            
        }

        /**
         * Invoked when the mouse enters a component.
         */
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * Invoked when the mouse exits a component.
         */
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * ������ȡ���������ȷ���˵������Ч��
     *
     * @param obj
     *            ��ѡȡ����
     */
    protected void enableMenuItems(Object obj) {
       if (verbose) 
        {
            System.out
                    .println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() begin...");
            //�ڹ�������û��ѡ�����ʱ�˵�����Ч��
        }
        if (obj == null) {
        
            setNullMenu();
        }
        //�ڹ�������ѡ�����Ϊ·�߱�ʱ�˵�����Ч��
        else if (obj instanceof RouteListTreeObject) {
            RouteListTreeObject treeobj = (RouteListTreeObject) obj;
            if (treeobj.getObject() == null) {
                setNullMenu();
            } else {
                setRouteListMenu();
            }
        } else {
            setNullMenu();
        }
        if (verbose) {
            System.out
                    .println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() end...return is void");
        }
    }

    /**
     * ���·�߱���
     *
     * @return RouteTreePanel
     */
    public RouteTreePanel getTreePanel() {
        return this.routeTreePanel;
    }

    /**
     * ���·�߱�ά�����
     *
     * @return RouteListTaskJPanel
     */
    public RouteListTaskJPanel getTaskPanel() {
        return this.routeListTaskJPanel;
    }

    /**
     * �����ڹ�������û��ѡ�����ʱ�˵�����Ч��
     */
    private void setNullMenu() {
        jMenuUpdate.setEnabled(false);
        jMenuDelete.setEnabled(false);
        jMenuCheckIn.setEnabled(false);
        jMenuCheckOut.setEnabled(false);
        jMenuUndoCheckOut.setEnabled(false);
        jMenuRevise.setEnabled(false);
        jMenuVersionHis.setEnabled(false);
        jMenuIteratorHis.setEnabled(false);
        jMenuView.setEnabled(false);
        jMenuEditRoute.setEnabled(false);
        jMenuReport.setEnabled(false);
        //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
//        jMenuChange.setEnabled(false);
        //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
        //CCBegin by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"   
        jMenuChangeFolder.setEnabled(false);
        //CCEnd by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"   
        jMenuResetState.setEnabled(false);
        jMenuResetLifeCycle.setEnabled(false);
        jMenuLifeCycleHis.setEnabled(false);
        jMenuResetProject.setEnabled(false);
        jMenuRefresh.setEnabled(false);
        jMenuClear.setEnabled(false);
        jMenuRename.setEnabled(false);
    }

    /**
     * �����ڹ�������ѡ�����Ϊ·�߱�ʱ�˵�����Ч��
     */
    private void setRouteListMenu() {
        jMenuRefresh.setEnabled(true);
        jMenuClear.setEnabled(true);
        jMenuUpdate.setEnabled(true);
        jMenuDelete.setEnabled(true);
        jMenuCheckIn.setEnabled(true);
        jMenuCheckOut.setEnabled(true);
        jMenuUndoCheckOut.setEnabled(true);
        jMenuRevise.setEnabled(true);
        jMenuVersionHis.setEnabled(true);
        jMenuIteratorHis.setEnabled(true);
        jMenuView.setEnabled(true);
        jMenuEditRoute.setEnabled(true);
        jMenuReport.setEnabled(true);
        //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
//        jMenuChange.setEnabled(true);
        //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����" 
        //CCBegin by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"   
        jMenuChangeFolder.setEnabled(true);
        //CCEnd by leixiao 2009-8-20 ԭ�򣺽����������·��,����"ת��"  
        jMenuResetState.setEnabled(true);
        jMenuResetLifeCycle.setEnabled(true);
        jMenuLifeCycleHis.setEnabled(true);
        jMenuResetProject.setEnabled(true);
        jMenuRename.setEnabled(true);

    }

    /**
     * �رչ���������� �˷�������ÿ�ν���ĳһ�˵�����ʱ��������
     */
    public void closeContentPanel() {
        if (routeListTaskJPanel != null && routeListTaskJPanel.isShowing()
                && !routeListTaskJPanel.isSave) {
              //System.out.println("closeContentPanel");
            routeListTaskJPanel.processCancelCommond();
            //isSaveRoute=false;
        }
        //else
            //isSaveRoute=true;
    }
    //protected boolean isSaveRoute=false;
    /**
        * �رչ����������
        * �˷�������ÿ�ν���ĳһ�˵�����ʱ��������
        * @return ��������֮ǰ�Ƿ�ִ���˱�����������ִ���˱��棬�򷵻��档
        */
       public boolean closeRouteListTaskJPanelPanel()
       {

            boolean flag = routeListTaskJPanel.setNullMode();
           return flag;

       }

    /**
     * ���õ�ǰ����״̬
     *
     * @param state
     *            �½������»�鿴ĳ·�߱�
     */
    public void setState(String state) {
        if (!state.equals("")) {
            stateJLabel.setText(state + "������");
        } else {
            stateJLabel.setText("��׼/�ձ�֪ͨ�������");
        }
    }
    //zz added this method to support BOM in 20070406
   public void addRouteListtoTree (TechnicsRouteListIfc routeListinfo){
       RouteTreePanel treePanel = this.getTreePanel();
              RouteListTreeObject newObj = new RouteListTreeObject(
                     routeListinfo);
             treePanel.addNode(newObj);
           treePanel.setNodeSelected(newObj);
   }
    /**
     * ������
     */
    class MyMouseListener extends MouseAdapter {
        //������
        public void mouseEntered(MouseEvent e) {
            Object obj = e.getSource();
            //��������ť
            if (obj instanceof JButton) {
                JButton button = (JButton) obj;
                statusBar.setText((String) button.getActionCommand());
            }
            //�˵���
            if (obj instanceof QMMenuItem) {
                QMMenuItem item = (QMMenuItem) obj;
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            //�˵�
            if (obj instanceof QMMenu) {
                QMMenu item = (QMMenu) obj;
                statusBar.setText(item.getExplainText());
            }
        }
        //����˳�
        public void mouseExited(MouseEvent e) {
            //����Ƴ�ʱ��״̬����ʾȱʡ��Ϣ
            statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "default_status", null));
        }
    }

    class MyMouseListener1 extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            Object obj = e.getSource();
            //��������ť
            if (obj instanceof JButton) {
                JButton button = (JButton) obj;
                String descripe = (String) button.getActionCommand();
                statusBar.setText(qmToolBar.getButtonDescription(descripe));
            }
            //�˵���
            if (obj instanceof QMMenuItem) {
                QMMenuItem item = (QMMenuItem) obj;
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            //�˵�
            if (obj instanceof QMMenu) {
                QMMenu item = (QMMenu) obj;
                statusBar.setText(item.getExplainText());
            }
        }

        public void mouseExited(MouseEvent e) {
            //����Ƴ�ʱ��״̬����ʾȱʡ��Ϣ
            statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "default_status", null));
        }
    }
}
