/**
 * 生成程序 CappRouteListManageJFrame.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 *CR1 郭晓亮  2009/05/06    原因: 点工艺路线树右键后，出现快捷菜单，但焦点与当前
 *                                鼠标位置不一致，容易造成用户误操作        
 *                          方案: 点右键时清除当前树节点的选择,通过坐标选中节
 *                                点并弹出快捷菜单.   
 *                                
 *CR2 郭晓亮  2009/06/04    参见:测试域:v4r3FunctionTest;TD号2277
 * 
 * 
 * SS1 添加附件。 liunan 2015-6-18
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
 * Title:艺准/艺毕通知书管理器
 * Description:艺准/艺毕通知书管理器主界面
 * Copyright: Copyright (c) 2004
 * Company: 一汽启明
 * @author 刘明
 * @mender skybird
 * @version 1.0
 *   //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线，工艺路线表－＞艺准通知书
 * (问题一) zz  周茁添加 20061215 添加重命名功能
 */

public class CappRouteListManageJFrame extends JFrame implements ActionListener {
    private JPanel contentPane;

    private JMenuBar jMenuBar1 = new JMenuBar();

    private QMMenu jMenuFile = new QMMenu("文件", new MyMouseListener());

    private QMMenuItem jMenuExit = new QMMenuItem("退出", new MyMouseListener());
    //(问题一) zz  周茁添加 20061215 添加重命名功能 start
    private QMMenuItem jMenuRename = new QMMenuItem("重命名", new MyMouseListener());
    //(问题一) zz  周茁添加 20061215 添加重命名功能 end

    private QMMenu jMenuHelp = new QMMenu("帮助", new MyMouseListener());

    private QMMenuItem jMenuHelpAbout = new QMMenuItem("关于. . .",
            new MyMouseListener());

    private QMMenuItem jMenuHelp1 = new QMMenuItem("帮助", new MyMouseListener());

    /** 工具条 */
    private QMToolBar qmToolBar = new QMToolBar();

    /** 界面分割面板 */
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

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    /** 用于标记资源 */
    protected static ResourceBundle resource = null;

    private QMMenuItem jMenuCreate = new QMMenuItem("创建艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuUpdate = new QMMenuItem("更新", new MyMouseListener());

    private QMMenuItem jMenuUpdate1 = new QMMenuItem("更新",
            new MyMouseListener());

    private QMMenuItem jMenuDelete = new QMMenuItem("删除艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuDelete1 = new QMMenuItem("删除艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuClear = new QMMenuItem("清除艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuClear1 = new QMMenuItem("清除艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuRefresh = new QMMenuItem("刷新",
            new MyMouseListener());

    private QMMenuItem jMenuRefresh1 = new QMMenuItem("刷新",
            new MyMouseListener());

    private QMMenu jMenuVersion = new QMMenu("版本", new MyMouseListener());

    private QMMenuItem jMenuCheckIn = new QMMenuItem("检入",
            new MyMouseListener());

    private QMMenuItem jMenuCheckOut = new QMMenuItem("检出",
            new MyMouseListener());

    private QMMenuItem jMenuUndoCheckOut = new QMMenuItem("撤消检出",
            new MyMouseListener());

    private QMMenuItem jMenuRevise = new QMMenuItem("修订", new MyMouseListener());

    private QMMenuItem jMenuVersionHis = new QMMenuItem("查看版本历史",
            new MyMouseListener());

    private QMMenuItem jMenuIteratorHis = new QMMenuItem("查看版序历史",
            new MyMouseListener());

    private QMMenu jMenuBrowse = new QMMenu("浏览", new MyMouseListener());

    private QMMenuItem jMenuView = new QMMenuItem("查看艺准/艺毕通知书",
            new MyMouseListener());

    //st skybird 2005.2.25
    private QMMenuItem jMenuConfigRule = new QMMenuItem("修改配置规则",
            new MyMouseListener());

    private QMMenuItem jMenuRoute = new QMMenuItem("综合路线",
            new MyMouseListener());

    private QMMenuItem jMenuSearchBy = new QMMenuItem("按单位搜索零部件",
            new MyMouseListener());

    //ed
    private QMMenuItem jMenuView1 = new QMMenuItem("查看艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuSearch = new QMMenuItem("搜索艺准/艺毕通知书",
            new MyMouseListener());

    private QMMenuItem jMenuEditRoute = new QMMenuItem("编辑工艺路线",
            new MyMouseListener());

    private QMMenuItem jMenuEditRoute1 = new QMMenuItem("编辑工艺路线",
            new MyMouseListener());

    private QMMenuItem jMenuReport = new QMMenuItem("生成报表",
            new MyMouseListener());
    
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  去掉此功能　2010-1-14　leix      
//    private QMMenuItem jMenuChange = new QMMenuItem("变更信息汇总", new MyMouseListener());
  //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"
    
  //CCBegin by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"        
    private QMMenuItem jMenuChangeFolder = new QMMenuItem("更改资料夹", new MyMouseListener());
  //CCEnd by leixiao 2009-8-20 原因：解放升级工艺路线,增加"变更信息汇总"

    private QMMenuItem jMenuHelpRouteList = new QMMenuItem("帮助",
            new MyMouseListener());

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private QMMenu jMenuLifeCycle = new QMMenu("生命周期", new MyMouseListener());

    private QMMenuItem jMenuResetState = new QMMenuItem("重新指定生命周期状态",
            new MyMouseListener());

    private QMMenuItem jMenuResetLifeCycle = new QMMenuItem("重新指定生命周期",
            new MyMouseListener());

    private QMMenuItem jMenuLifeCycleHis = new QMMenuItem("查看生命周期历史",
            new MyMouseListener());

    private QMMenuItem jMenuResetProject = new QMMenuItem("重新指定工作组",
            new MyMouseListener());


    /** 控制器 */
    protected CappRouteListManageController controller;

    /** 标记是否默认显示查看路线表界面 */
    public static boolean isViewRouteList = true;

    /** 界面退出时是否退出系统 */
    public boolean isExitSystem = true;

    /** 弹出菜单 */
    private JPopupMenu popup = new JPopupMenu();
    /**
     * 构造函数
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
     * 构造函数
     * @param isExitSystem 界面退出时是否退出系统。如果为true，则退出系统
     */
    public CappRouteListManageJFrame(boolean isExitSystem) {
        this();
        this.isExitSystem = isExitSystem;
    }

    /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit() throws Exception {
        controller = new CappRouteListManageController(this);
        routeTreePanel = new RouteTreePanel("艺准/艺毕通知书");
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
        this.setTitle("艺准/艺毕通知书管理器");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.setText("欢迎进入艺准/艺毕通知书管理器. . .");
        jMenuFile.setText("文件(F)");
        jMenuFile.setMnemonic('F');
        jMenuExit.setText("退出(X)");
        jMenuExit.setMnemonic('X');
        KeyStroke ksExit = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                Event.CTRL_MASK);
        jMenuExit.setAccelerator(ksExit);
        //(问题一) zz  周茁添加 20061215 添加重命名功能 start
         jMenuRename.setText("重命名(C)");
         jMenuRename.setMnemonic('C');
         KeyStroke ksRename = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                 Event.CTRL_MASK);
         jMenuRename.setAccelerator(ksRename);
         //(问题一) zz  周茁添加 20061215 添加重命名功能 end

        jMenuHelp.setText("帮助(H)");
        jMenuHelp.setMnemonic('H');
        jMenuHelpAbout.setText("关于(A)...");
        jMenuHelpAbout.setMnemonic('A');
        jMenuHelp1.setToolTipText("");
        jMenuHelp1.setText("艺准/艺毕通知书管理器(H)...");
        jMenuHelp1.setMnemonic('H');
        KeyStroke SS = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        jMenuHelp1.setAccelerator(SS);
        leftJPanel.setLayout(gridBagLayout1);
        jLabel1.setBorder(BorderFactory.createEtchedBorder());
        jLabel1.setText(" 艺准/艺毕通知书");
        rightJPanel.setLayout(gridBagLayout2);
        stateJLabel.setBorder(BorderFactory.createEtchedBorder());
        stateJLabel.setText("艺准/艺毕通知书的内容");
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(gridBagLayout3);
        routeListTaskJPanel.setMaximumSize(new Dimension(343, 2147483647));
        jMenuCreate.setText("创建(N)...");
        jMenuCreate.setMnemonic('N');
        KeyStroke ksCreate = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Event.CTRL_MASK);
        jMenuCreate.setAccelerator(ksCreate);
        jMenuUpdate.setText("更新(M)...");
        jMenuUpdate.setMnemonic('M');
        KeyStroke ksUpdate = KeyStroke.getKeyStroke(KeyEvent.VK_M,
                Event.CTRL_MASK);
        jMenuUpdate.setAccelerator(ksUpdate);
        jMenuUpdate1.setText("更新(M)");
        jMenuUpdate1.setMnemonic('M');
        jMenuUpdate1.setAccelerator(ksUpdate);
        jMenuDelete.setText("删除(D)");
        jMenuDelete.setMnemonic('D');
        KeyStroke ksDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D,
                Event.CTRL_MASK);
        jMenuDelete.setAccelerator(ksDelete);
        jMenuDelete1.setText("删除(D)");
        jMenuDelete1.setMnemonic('D');
        jMenuDelete1.setAccelerator(ksDelete);
        jMenuClear.setText("清除(A)");
        jMenuClear.setMnemonic('A');
        // KeyStroke ksClear =
        // KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK);
        //  jMenuClear.setAccelerator(ksClear);
        jMenuClear1.setText("清除(A)");
        jMenuClear1.setMnemonic('A');
        //  jMenuClear1.setAccelerator(ksClear);
        jMenuRefresh.setText("刷新(E)");
        jMenuRefresh.setMnemonic('E');
        //   KeyStroke ksRefresh =
        // KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK);
        // jMenuRefresh.setAccelerator(ksRefresh);
        jMenuRefresh1.setText("刷新(E)");
        jMenuRefresh1.setMnemonic('E');
        // jMenuRefresh1.setAccelerator(ksRefresh);
        jMenuVersion.setText("版本(V)");
        jMenuVersion.setMnemonic('V');
        jMenuCheckIn.setText("检入(I). . .");
        jMenuCheckIn.setMnemonic('I');
        KeyStroke ksCheckIn = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                Event.CTRL_MASK);
        jMenuCheckIn.setAccelerator(ksCheckIn);
        jMenuCheckOut.setText("检出(O). . .");
        jMenuCheckOut.setMnemonic('O');
        KeyStroke ksCheckOut = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                Event.CTRL_MASK);
        jMenuCheckOut.setAccelerator(ksCheckOut);
        jMenuUndoCheckOut.setText("撤消检出(U)");
        jMenuUndoCheckOut.setMnemonic('U');
        KeyStroke ksUndocheckOut = KeyStroke.getKeyStroke(KeyEvent.VK_U,
                Event.CTRL_MASK);
        jMenuUndoCheckOut.setAccelerator(ksUndocheckOut);
        jMenuRevise.setText("修订(R)");
        jMenuRevise.setMnemonic('R');
        // KeyStroke ksRevise =
        // KeyStroke.getKeyStroke(KeyEvent.VK_R,Event.CTRL_MASK);
        // jMenuRevise.setAccelerator(ksRevise);
        jMenuVersionHis.setText("查看版本历史(V)");
        jMenuVersionHis.setMnemonic('V');
        //  KeyStroke ksVersionHis =
        // KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK);
        //  jMenuVersionHis.setAccelerator(ksVersionHis);
        jMenuIteratorHis.setText("查看版序历史(S)");
        jMenuIteratorHis.setMnemonic('S');
        // KeyStroke ksIteratorHis =
        // KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK);
        // jMenuIteratorHis.setAccelerator(ksIteratorHis);
        jMenuBrowse.setText("浏览(B)");
        jMenuBrowse.setMnemonic('B');
        jMenuView.setText("查看(V)");
        jMenuView.setMnemonic('V');
        KeyStroke ksView = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                Event.CTRL_MASK);
        jMenuView.setAccelerator(ksView);
        //st skybird 2005.2.25
        jMenuConfigRule.setText("修改配置规则(M). . .");
        jMenuConfigRule.setActionCommand("修改配置规则(M)");
        jMenuConfigRule.setMnemonic('M');
        //   KeyStroke ksConfigRule =
        // KeyStroke.getKeyStroke(KeyEvent.VK_M,Event.CTRL_MASK);
        // jMenuConfigRule.setAccelerator(ksConfigRule);
        jMenuRoute.setText("综合路线(C). . .");
        jMenuRoute.setMnemonic('C');
        // KeyStroke ksRoute =
        // KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
        // jMenuRoute.setAccelerator(ksRoute);
        jMenuSearchBy.setText("按路线单位搜索(D). . .");
        jMenuSearchBy.setMnemonic('D');
        // KeyStroke ksSearchBy =
        // KeyStroke.getKeyStroke(KeyEvent.VK_D,Event.CTRL_MASK);
        // jMenuSearchBy.setAccelerator(ksSearchBy);
        //ed
        jMenuSearch.setText("搜索(S). . .");
        jMenuSearch.setMnemonic('S');
        KeyStroke ksSearch = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Event.CTRL_MASK);
        jMenuSearch.setAccelerator(ksSearch);
        jMenuView1.setText("查看(V)");
        jMenuView1.setMnemonic('V');
        jMenuView1.setAccelerator(ksView);
        jMenuEditRoute.setText("编辑路线(R)...");
        jMenuEditRoute.setMnemonic('R');
        KeyStroke ksEditRoute = KeyStroke.getKeyStroke(KeyEvent.VK_R,
                Event.CTRL_MASK);
        jMenuEditRoute.setAccelerator(ksEditRoute);
        jMenuEditRoute1.setText("编辑路线(R)...");
        jMenuEditRoute1.setMnemonic('R');
        jMenuEditRoute1.setAccelerator(ksEditRoute);
        jMenuReport.setText("生成报表(R). . .");
        jMenuReport.setMnemonic('R');
        //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"
//        jMenuChange.setText("变更信息汇总(F). . .");
//        jMenuChange.setMnemonic('F');
         //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"   
        //CCBegin by leixiao 2008-9-20 原因：解放升级工艺路线,增加"转库"
        jMenuChangeFolder.setText("更改资料夹. . .");
        jMenuChangeFolder.setMnemonic('M');
        //CCEnd by leixiao 2008-9-20 原因：解放升级工艺路线,增加"转库"   
        // KeyStroke ksReport =
        // KeyStroke.getKeyStroke(KeyEvent.VK_R,Event.CTRL_MASK);
        //  jMenuReport.setAccelerator(ksReport);
        jMenuHelpRouteList.setText("关于艺准/艺毕通知书管理(R)");
        jMenuHelpRouteList.setMnemonic('R');
        jMenuHelpRouteList.setVisible(false);
        jMenuLifeCycle.setText("生命周期(L)");
        jMenuLifeCycle.setMnemonic('L');
        jMenuResetState.setText("重新指定生命周期状态(S)");
        jMenuResetState.setMnemonic('S');
        //  KeyStroke ksResetState =
        // KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK);
        //  jMenuResetState.setAccelerator(ksResetState);
        jMenuResetLifeCycle.setText("重新指定生命周期(C)");
        jMenuResetLifeCycle.setMnemonic('C');
        //KeyStroke ksResetLifeCycle =
        // KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
        // jMenuResetLifeCycle.setAccelerator(ksResetLifeCycle);
        jMenuLifeCycleHis.setText("查看生命周期历史(H)");
        jMenuLifeCycleHis.setMnemonic('H');
        // KeyStroke ksLifeCycleHis =
        // KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK);
        // jMenuLifeCycleHis.setAccelerator(ksLifeCycleHis);
        jMenuResetProject.setText("重新指定工作组(P)");
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
        //CCBegin by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"  
        jMenuFile.add(jMenuChangeFolder);
        jMenuFile.addSeparator();
        //CCEnd by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"  
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
        //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总" 
//        jMenuVersion.add(this.jMenuChange);
        //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总" 
        //st skybird 2005.2.25
        jMenuBrowse.add(jMenuConfigRule);
        //ed
        jMenuBrowse.add(jMenuView);
        jMenuBrowse.add(jMenuSearch);
        jMenuBrowse.add(jMenuSearchBy);
        // jMenuBrowse.add(jMenuRoute);zz暂时封闭此功能1
        jMenuBrowse.add(jMenuReport);
        jMenuLifeCycle.add(jMenuResetState);
        jMenuLifeCycle.add(jMenuResetLifeCycle);
        jMenuLifeCycle.add(jMenuLifeCycleHis);
        jMenuLifeCycle.addSeparator();
        jMenuLifeCycle.add(jMenuResetProject);

        //{{注册菜单的动作监听
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
        //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总" 
//        jMenuChange.addActionListener(menuAction);        
        //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总" 
//      CCBegin by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库" 
        jMenuChangeFolder.addActionListener(menuAction);
//      CCEnd by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库" 
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

        //{{为路线表树注册监听
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
    
    //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,增加"按路线展开"
    public void myrefresh() {
        controller.processRefreshCommand();
      }
    //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,增加"按路线展开"

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        //super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            controller.processExitCommand();
        }
    }

    /**
     * 初始化所使用的资源绑定信息类
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
     * 获得资源信息
     *
     * @return ResourceBundle 资源包
     */
    protected ResourceBundle getPropertiesRB() {
        if (resource == null) {
            initResources();
        }
        return resource;
    }

    /**
     * 获得按钮信息显示出来,带浮动文字
     *
     * @param as1
     * @param as2
     * @param as3
     *            3个浮动文字
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
     * 在ResourceBundle中取得key 并保存成StringTokenizer
     * 最后保存成String[]
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
     * 处理工具条上的按钮的命令.当用户点击工具条上的按钮时会发出一个命令,
     * 命令名称 与按钮图片的名称一样
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
     * 刷新主界面
     */
    public void refresh() {
        this.invalidate();
        this.doLayout();
        this.repaint();
    }

    /**
     * 界面中所有的菜单操作在此注册
     * <p>
     * 根据当前所选择的菜单和业务对象，确定界面的显示内容
     * </p>
     * <p>
     * Copyright: Copyright (c) 2003
     * </p>
     * <p>
     * Company: 一汽启明
     * </p>
     *
     * @author 刘明
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
                //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"     
//            }else if (object == jMenuChange) {
//                controller.processChangeCommand();
            //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"   
            }
//          CCBegin by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"    
            else if (object == jMenuChangeFolder) {
            controller.processChangeFolderCommand();
//          CCEnd by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"  
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
     * Title:路线列表树的节点选择监听
     * </p>
     * <p>
     * 当树节点的选择值变化时,更新菜单状态,弹出查看路线表界面
     * </p>
     * <p>
     * Copyright: Copyright (c) 2004
     * </p>
     * <p>
     * Company: 一汽启明
     * </p>
     *
     * @author 刘明
     * @version 1.0
     */
    class RootListTreeSelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            RouteTreeObject obj = CappRouteListManageJFrame.this.getTreePanel()
                    .getSelectedObject();
          
            enableMenuItems(obj);
          
            //如果选中节点是RouteListTreeObject,则弹出查看界面
            if (obj != null && obj instanceof RouteListTreeObject) {
                CappRouteListManageJFrame.this.refresh();
                CappRouteListManageJFrame.this.closeContentPanel();
//              CCBegin by leixiao 2008-11-4 原因：解放升级工艺路线,无法搜索出路线默认查看路线                 
//              if(!CappRouteListManageJFrame.this.routeListTaskJPanel.isSave)
//                  return;
//            CCEnd by leixiao 2008-11-4 原因：解放升级工艺路线   
                //if(!isSaveRoute)
                //    return;
                //skybird
                CappRouteListManageJFrame.this.setState(RParentJPanel
                        .getIdentity(obj.getObject()));
                TechnicsRouteListIfc tmp = (TechnicsRouteListIfc) obj
                        .getObject();
                statusBar.setText("当前选中的艺准/艺毕通知书： " + tmp.getRouteListName());
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
     *路线列表树的鼠标监听
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
     * 根据所取对象的类型确定菜单项的有效性
     *
     * @param obj
     *            所选取对象
     */
    protected void enableMenuItems(Object obj) {
       if (verbose) 
        {
            System.out
                    .println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() begin...");
            //在管理器中没有选择对象时菜单的有效性
        }
        if (obj == null) {
        
            setNullMenu();
        }
        //在管理器中选择对象为路线表时菜单的有效性
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
     * 获得路线表树
     *
     * @return RouteTreePanel
     */
    public RouteTreePanel getTreePanel() {
        return this.routeTreePanel;
    }

    /**
     * 获得路线表维护面板
     *
     * @return RouteListTaskJPanel
     */
    public RouteListTaskJPanel getTaskPanel() {
        return this.routeListTaskJPanel;
    }

    /**
     * 设置在管理器中没有选择对象时菜单的有效性
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
        //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
//        jMenuChange.setEnabled(false);
        //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
        //CCBegin by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"   
        jMenuChangeFolder.setEnabled(false);
        //CCEnd by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"   
        jMenuResetState.setEnabled(false);
        jMenuResetLifeCycle.setEnabled(false);
        jMenuLifeCycleHis.setEnabled(false);
        jMenuResetProject.setEnabled(false);
        jMenuRefresh.setEnabled(false);
        jMenuClear.setEnabled(false);
        jMenuRename.setEnabled(false);
    }

    /**
     * 设置在管理器中选择对象为路线表时菜单的有效性
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
        //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
//        jMenuChange.setEnabled(true);
        //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总" 
        //CCBegin by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"   
        jMenuChangeFolder.setEnabled(true);
        //CCEnd by leixiao 2009-8-20 原因：解放升级工艺路线,增加"转库"  
        jMenuResetState.setEnabled(true);
        jMenuResetLifeCycle.setEnabled(true);
        jMenuLifeCycleHis.setEnabled(true);
        jMenuResetProject.setEnabled(true);
        jMenuRename.setEnabled(true);

    }

    /**
     * 关闭工艺内容面板 此方法用于每次进行某一菜单操作时更换界面
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
        * 关闭工艺内容面板
        * 此方法用于每次进行某一菜单操作时更换界面
        * @return 更换界面之前是否执行了保存操作。如果执行了保存，则返回真。
        */
       public boolean closeRouteListTaskJPanelPanel()
       {

            boolean flag = routeListTaskJPanel.setNullMode();
           return flag;

       }

    /**
     * 设置当前操作状态
     *
     * @param state
     *            新建、更新或查看某路线表
     */
    public void setState(String state) {
        if (!state.equals("")) {
            stateJLabel.setText(state + "的内容");
        } else {
            stateJLabel.setText("艺准/艺毕通知书的内容");
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
     * 鼠标监听
     */
    class MyMouseListener extends MouseAdapter {
        //鼠标进入
        public void mouseEntered(MouseEvent e) {
            Object obj = e.getSource();
            //工具条按钮
            if (obj instanceof JButton) {
                JButton button = (JButton) obj;
                statusBar.setText((String) button.getActionCommand());
            }
            //菜单项
            if (obj instanceof QMMenuItem) {
                QMMenuItem item = (QMMenuItem) obj;
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            //菜单
            if (obj instanceof QMMenu) {
                QMMenu item = (QMMenu) obj;
                statusBar.setText(item.getExplainText());
            }
        }
        //鼠标退出
        public void mouseExited(MouseEvent e) {
            //鼠标移出时，状态栏显示缺省信息
            statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "default_status", null));
        }
    }

    class MyMouseListener1 extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            Object obj = e.getSource();
            //工具条按钮
            if (obj instanceof JButton) {
                JButton button = (JButton) obj;
                String descripe = (String) button.getActionCommand();
                statusBar.setText(qmToolBar.getButtonDescription(descripe));
            }
            //菜单项
            if (obj instanceof QMMenuItem) {
                QMMenuItem item = (QMMenuItem) obj;
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            //菜单
            if (obj instanceof QMMenu) {
                QMMenu item = (QMMenu) obj;
                statusBar.setText(item.getExplainText());
            }
        }

        public void mouseExited(MouseEvent e) {
            //鼠标移出时，状态栏显示缺省信息
            statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "default_status", null));
        }
    }
}
