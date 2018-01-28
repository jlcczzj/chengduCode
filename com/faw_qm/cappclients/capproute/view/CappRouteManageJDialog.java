/**
 * 生成程序 CappRouteManageJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 郭晓亮 2009/06/04   测试域:v4r3FunctionTest;TD号2291
 * 
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import com.faw_qm.cappclients.capproute.controller.CappRouteManageController;
import com.faw_qm.cappclients.capproute.util.CappRouteRB;
import com.faw_qm.cappclients.capproute.util.RoutePartTreeObject;
import com.faw_qm.cappclients.capproute.util.RouteTree;
import com.faw_qm.cappclients.capproute.util.RouteTreeObject;
import com.faw_qm.cappclients.capproute.util.RouteTreePanel;
import com.faw_qm.clients.beans.explorer.QMMenu;
import com.faw_qm.clients.beans.explorer.QMMenuItem;
import com.faw_qm.clients.beans.explorer.QMToolBar;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.util.QMCt;
import java.util.HashMap;
import javax.swing.JMenuItem;
import com.faw_qm.cappclients.capproute.util.RouteListTreeObject;

/**
 * <p>
 * Title:工艺路线管理器
 * </p>
 * <p>
 * Description:
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

public class CappRouteManageJDialog extends JDialog implements ActionListener {
	private JPanel contentPane;

	private JMenuBar jMenuBar1 = new JMenuBar();

	private QMMenu jMenuFile = new QMMenu("文件", new MyMouseListener());

	private QMMenu jMenuHelp = new QMMenu("帮助", new MyMouseListener());

	/** 工具条 */
	private QMToolBar qmToolBar = new QMToolBar();

	/** 界面分割面板 */
	private JSplitPane jSplitPane = new JSplitPane();

	private JLabel statusBar = new JLabel();

	private BorderLayout borderLayout1 = new BorderLayout();

	/** 用于标记资源文件路径 */
	protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

	/** 用于标记资源 */
	protected static ResourceBundle resource = null;

	private JPanel leftJPanel = new JPanel();

	private JPanel rightJPanel = new JPanel();

	/** 路线树 */
	private RouteTreePanel routeTreePanel;

	private QMMenuItem jMenuCreate = new QMMenuItem("创建工艺路线",
			new MyMouseListener());

	private QMMenuItem jMenuCreate1 = new QMMenuItem("创建工艺路线",
			new MyMouseListener());

	private QMMenuItem jMenuUpdate = new QMMenuItem("更新", new MyMouseListener());

	private QMMenuItem jMenuUpdate1 = new QMMenuItem("更新",
			new MyMouseListener());

	private QMMenuItem jMenuDelete = new QMMenuItem("删除", new MyMouseListener());

	private QMMenuItem jMenuDelete1 = new QMMenuItem("删除",
			new MyMouseListener());

	private QMMenuItem jMenuRefresh = new QMMenuItem("刷新",
			new MyMouseListener());

	private QMMenuItem jMenuRefresh1 = new QMMenuItem("刷新",
			new MyMouseListener());

	private QMMenuItem jMenuClear = new QMMenuItem("清除", new MyMouseListener());

	private QMMenuItem jMenuClear1 = new QMMenuItem("清除", new MyMouseListener());

	private QMMenuItem jMenuExit = new QMMenuItem("退出", new MyMouseListener());

	private QMMenu jMenuEdit = new QMMenu("编辑", new MyMouseListener());

	private QMMenuItem jMenuCopy = new QMMenuItem("复制", new MyMouseListener());

	private QMMenuItem jMenuCopy1 = new QMMenuItem("复制", new MyMouseListener());

	private QMMenuItem jMenuCopyFrom = new QMMenuItem("复制自",
			new MyMouseListener());

	private QMMenuItem jMenuCopyFrom1 = new QMMenuItem("复制自",
			new MyMouseListener());

	private QMMenuItem jMenuPaste = new QMMenuItem("粘贴", new MyMouseListener());

	private QMMenuItem jMenuPaste1 = new QMMenuItem("粘贴", new MyMouseListener());

	private QMMenuItem jMenuPasteTo = new QMMenuItem("粘贴到",
			new MyMouseListener());

	private QMMenuItem jMenuPasteTo1 = new QMMenuItem("粘贴到",
			new MyMouseListener());

	private QMMenu jMenuRoute = new QMMenu("路线", new MyMouseListener());

	private QMMenuItem jMenuView = new QMMenuItem("查看", new MyMouseListener());

	private QMMenuItem jMenuView1 = new QMMenuItem("查看", new MyMouseListener());

	// CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
	private QMMenuItem jMenuViewParent = new QMMenuItem("查看装配位置",
			new MyMouseListener());

	private QMMenuItem jMenuViewParent1 = new QMMenuItem("查看装配位置",
			new MyMouseListener());

	private QMMenuItem jMenuViewpart = new QMMenuItem("查看零部件信息",
			new MyMouseListener());

	// CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线

	private QMMenuItem jMenuReselect = new QMMenuItem("选择",
			new MyMouseListener());

	private QMMenuItem jMenuHelpTopic = new QMMenuItem("帮助主题",
			new MyMouseListener());

	private QMMenuItem jMenuHelpAbout = new QMMenuItem("关于",
			new MyMouseListener());

	/** 代码测试变量 */
	private static boolean verbose = (RemoteProperty.getProperty(
			"com.faw_qm.cappclients.verbose", "true")).equals("true");

	/** 控制器 */
	private CappRouteManageController controller;

	/** 路线维护面板 */
	private RouteTaskJPanel routeTaskJPanel;

	private JLabel stateJLabel = new JLabel();

	private JLabel jLabel2 = new JLabel();

	private BorderLayout borderLayout2 = new BorderLayout();

	/** 当前编辑的路线表 */
	private TechnicsRouteListIfc myRouteList;

	/** 缓存：路线管理器中所有的零部件 */
	private Vector myPartLinks = new Vector();

	/** 标记搜索路线的线程是否已经结束 */
	public boolean threadEnd = false;

	private JPanel jPanel1 = new JPanel();

	private BorderLayout borderLayout3 = new BorderLayout();

	private BorderLayout borderLayout4 = new BorderLayout();

	/** 弹出菜单 */
	private JPopupMenu popup = new JPopupMenu();

	/** 父窗口 */
	private JFrame parentJFrame;

	// CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
	JMenuItem jMenuItem1 = new JMenuItem();

	/** 缓存：路线管理器中所有的路线. 键为零部件BsoID,值为路线值对象 */
	private HashMap routeMap = new HashMap();

	// CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线

	/**
	 * 构造函数
	 * 
	 * @param parentFrame
	 *            父窗口
	 * @param partlinks
	 *            要编辑路线的关联ListRoutePartLink的集合
	 */
	public CappRouteManageJDialog(JFrame parentFrame, Vector partlinks) {
		super(parentFrame, "", true);// leix jf为false 暂为更改
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.parentJFrame = parentFrame;
			// 显示选中的路线表零件关联于此界面的左侧零件树
			setRouteListData(partlinks);
		} catch (QMRemoteException ex) {
			JOptionPane
					.showMessageDialog(parentFrame, ex.getClientMessage(),
							QMMessage.getLocalizedMessage(RESOURCE,
									"information", null),
							JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 界面组件初始化
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		controller = new CappRouteManageController(this);
		// URL url = CappRouteManageJDialog.class.getResource("/images/rg.gif");
		// if(url!=null)
		// setIconImage(Toolkit.getDefaultToolkit().createImage(url));
		routeTaskJPanel = new RouteTaskJPanel(this.getParentJFrame(), this);
		this.routeTaskJPanel.setVisible(false);
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, dimension.width, dimension.height - 25);
		this.setTitle("工艺路线编辑器");
		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.setText("欢迎进入工艺路线编辑器. . .");
		jMenuFile.setText("文件(F)");
		jMenuFile.setMnemonic('F');
		jMenuHelp.setText("帮助(H)");
		jMenuHelp.setMnemonic('H');
		jMenuHelp.setVisible(false);
		ResourceBundle rb = getPropertiesRB();
		String str1[] = this.getValueSet(rb, "routetoolbar.icons");
		String str2[] = this.getValueSet(rb, "routetoolbar.text");
		String str3[] = this.getValueSet(rb, "routetoolbar.descripe");
		this.setTools(str1, str2, str3);
		jSplitPane.setLastDividerLocation(260);
		jSplitPane.setResizeWeight(1);
		jSplitPane.setDividerLocation(260);
		leftJPanel.setLayout(borderLayout2);
		rightJPanel.setLayout(borderLayout4);
		jMenuCreate.setText("新建(N)");
		jMenuCreate.setMnemonic('N');
		KeyStroke kscreate = KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Event.CTRL_MASK);
		jMenuCreate.setAccelerator(kscreate);
		jMenuCreate1.setText("创建(N)");
		jMenuCreate1.setMnemonic('N');
		KeyStroke kscreate1 = KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Event.CTRL_MASK);
		jMenuCreate1.setAccelerator(kscreate1);
		jMenuUpdate.setText("更新(U)");
		jMenuUpdate.setMnemonic('U');
		KeyStroke ksUpdate = KeyStroke.getKeyStroke(KeyEvent.VK_U,
				Event.CTRL_MASK);
		jMenuUpdate.setAccelerator(ksUpdate);
		jMenuDelete.setText("删除(D)");
		jMenuDelete.setMnemonic('D');
		KeyStroke ksDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D,
				Event.CTRL_MASK);
		jMenuDelete.setAccelerator(ksDelete);
		jMenuRefresh.setText("刷新(E)");
		jMenuRefresh.setMnemonic('E');
		KeyStroke ksRefresh = KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Event.CTRL_MASK);
		jMenuRefresh.setAccelerator(ksRefresh);
		jMenuClear.setText("清除(A)");
		jMenuClear.setMnemonic('A');
		KeyStroke ksClear = KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK);
		jMenuClear.setAccelerator(ksClear);
		jMenuClear.setAccelerator(ksClear);
		jMenuUpdate1.setText("更新(U)");
		jMenuUpdate1.setMnemonic('U');
		KeyStroke ksUpdate1 = KeyStroke.getKeyStroke(KeyEvent.VK_U,
				Event.CTRL_MASK);
		jMenuUpdate1.setAccelerator(ksUpdate1);
		jMenuDelete1.setText("删除(D)");
		jMenuDelete1.setMnemonic('D');
		KeyStroke ksDelete1 = KeyStroke.getKeyStroke(KeyEvent.VK_D,
				Event.CTRL_MASK);
		jMenuDelete1.setAccelerator(ksDelete1);
		jMenuRefresh1.setText("刷新(E)");
		jMenuRefresh1.setMnemonic('E');
		KeyStroke ksRefresh1 = KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Event.CTRL_MASK);
		jMenuRefresh1.setAccelerator(ksRefresh1);
		jMenuClear1.setText("清除(A)");
		jMenuClear1.setMnemonic('A');
		KeyStroke ksClear1 = KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK);
		jMenuClear1.setAccelerator(ksClear1);
		jMenuExit.setText("退出(X)");
		jMenuExit.setMnemonic('X');
		KeyStroke ksExit = KeyStroke.getKeyStroke(KeyEvent.VK_X,
				Event.CTRL_MASK);
		jMenuExit.setAccelerator(ksExit);
		jMenuEdit.setText("编辑(E)");
		jMenuEdit.setMnemonic('E');
		jMenuCopy.setText("复制(C)");
		jMenuCopy.setMnemonic('C');
		KeyStroke ksCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Event.CTRL_MASK);
		jMenuCopy.setAccelerator(ksCopy);
		jMenuCopyFrom.setText("复制自(F)...");
		jMenuCopyFrom.setMnemonic('F');
		KeyStroke ksCopyFrom = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Event.CTRL_MASK);
		jMenuCopyFrom.setAccelerator(ksCopyFrom);
		jMenuPaste.setText("粘贴(P)");
		jMenuPaste.setMnemonic('P');
		KeyStroke ksPaste = KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Event.CTRL_MASK);
		jMenuPaste.setAccelerator(ksPaste);
		jMenuPasteTo.setText("粘贴到(T)...");
		jMenuPasteTo.setMnemonic('T');
		KeyStroke ksPasteTo = KeyStroke.getKeyStroke(KeyEvent.VK_T,
				Event.CTRL_MASK);
		jMenuPasteTo.setAccelerator(ksPasteTo);
		jMenuCopy1.setText("复制(C)");
		jMenuCopy1.setMnemonic('C');
		KeyStroke ksCopy1 = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Event.CTRL_MASK);
		jMenuCopy1.setAccelerator(ksCopy1);
		jMenuCopyFrom1.setText("复制自(F)...");
		jMenuCopyFrom1.setMnemonic('F');
		KeyStroke ksCopyFrom1 = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Event.CTRL_MASK);
		jMenuCopyFrom1.setAccelerator(ksCopyFrom1);
		jMenuPaste1.setText("粘贴(P)");
		jMenuPaste1.setMnemonic('P');
		KeyStroke ksPaste1 = KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Event.CTRL_MASK);
		jMenuPaste1.setAccelerator(ksPaste1);
		jMenuPasteTo1.setText("粘贴到(T)...");
		jMenuPasteTo1.setMnemonic('T');
		KeyStroke ksPasteTo1 = KeyStroke.getKeyStroke(KeyEvent.VK_T,
				Event.CTRL_MASK);
		jMenuPasteTo1.setAccelerator(ksPasteTo1);
		jMenuRoute.setText("浏览(B)");
		jMenuRoute.setMnemonic('B');
		jMenuView.setText("查看(V)");
		jMenuView.setMnemonic('V');
		KeyStroke ksView = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				Event.CTRL_MASK);
		jMenuView.setAccelerator(ksView);
		jMenuView1.setText("查看(V)");
		// CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuViewParent.setText("查看装配位置(I)");
		jMenuView.setMnemonic('V');
		jMenuViewParent.setMnemonic('I');
		// CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
		KeyStroke ksView1 = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				Event.CTRL_MASK);
		jMenuView1.setAccelerator(ksView1);
		// CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuViewParent1.setText("查看装配位置(I)");
		jMenuViewParent1.setMnemonic('I');
		KeyStroke ksViewP1 = KeyStroke.getKeyStroke(KeyEvent.VK_I,
				Event.CTRL_MASK);
		jMenuViewParent1.setAccelerator(ksViewP1);
		jMenuViewpart.setText("查看零部件");
		// CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuReselect.setText("选择(S)");
		jMenuReselect.setMnemonic('S');
		KeyStroke ksReselect = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Event.CTRL_MASK);
		jMenuReselect.setAccelerator(ksReselect);
		jMenuHelpTopic.setText("帮助主题(S)");
		jMenuHelpTopic.setMnemonic('S');
		jMenuHelpAbout.setText("关于(A)...");
		jMenuHelpAbout.setMnemonic('A');
		stateJLabel.setBorder(BorderFactory.createEtchedBorder());
		stateJLabel.setText("路线的内容");
		jLabel2.setBorder(BorderFactory.createEtchedBorder());
		jLabel2.setText("工艺路线");
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setLayout(borderLayout3);
		// CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuItem1.setMnemonic('R');
		jMenuItem1.setText("生成报表(R)");
		jMenuItem1
				.addActionListener(new CappRouteManageJDialog_jMenuItem1_actionAdapter(
						this));
		// CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuHelp.add(jMenuHelpTopic);
		jMenuHelp.add(jMenuHelpAbout);
		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuEdit);
		jMenuBar1.add(jMenuRoute);
		jMenuBar1.add(jMenuHelp);
		this.setJMenuBar(jMenuBar1);
		contentPane.add(qmToolBar, BorderLayout.NORTH);
		contentPane.add(statusBar, BorderLayout.SOUTH);
		contentPane.add(jSplitPane, BorderLayout.CENTER);
		jSplitPane.add(leftJPanel, JSplitPane.LEFT);
		leftJPanel.add(routeTreePanel, BorderLayout.CENTER);
		leftJPanel.add(jLabel2, BorderLayout.NORTH);
		jSplitPane.add(rightJPanel, JSplitPane.RIGHT);
		rightJPanel.add(stateJLabel, BorderLayout.NORTH);
		rightJPanel.add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(routeTaskJPanel, BorderLayout.CENTER);
		jMenuFile.add(jMenuCreate);
		jMenuFile.add(jMenuUpdate);
		jMenuFile.add(jMenuDelete);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuRefresh);
		jMenuFile.add(jMenuClear);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuExit);
		jMenuEdit.add(jMenuCopy);
		jMenuEdit.add(jMenuCopyFrom);
		jMenuEdit.addSeparator();
		jMenuEdit.add(jMenuPaste);
		jMenuEdit.add(jMenuPasteTo);
		jMenuRoute.add(jMenuView);
		jMenuRoute.add(jMenuReselect);
		// CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuRoute.add(jMenuViewParent);
		jMenuRoute.add(jMenuReselect);
		jMenuRoute.add(jMenuItem1);
		// CCEndby leixiao 2008-7-31 原因：解放升级工艺路线

		// {{注册菜单监听
		RMenuAction rmAction = new RMenuAction();
		jMenuCreate.addActionListener(rmAction);
		jMenuUpdate.addActionListener(rmAction);
		jMenuDelete.addActionListener(rmAction);
		jMenuRefresh.addActionListener(rmAction);
		jMenuClear.addActionListener(rmAction);
		jMenuExit.addActionListener(rmAction);
		jMenuCopy.addActionListener(rmAction);
		jMenuCopyFrom.addActionListener(rmAction);
		jMenuPaste.addActionListener(rmAction);
		jMenuPasteTo.addActionListener(rmAction);
		jMenuView.addActionListener(rmAction);
		// CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuViewParent.addActionListener(rmAction);
		jMenuReselect.addActionListener(rmAction);
		jMenuHelpTopic.addActionListener(rmAction);
		jMenuHelpAbout.addActionListener(rmAction);

		jMenuUpdate1.addActionListener(rmAction);
		jMenuView1.addActionListener(rmAction);
		jMenuViewParent1.addActionListener(rmAction);
		jMenuViewpart.addActionListener(rmAction);
		jMenuViewpart.setEnabled(true);
		// CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
		jMenuReselect.addActionListener(rmAction);
		jMenuHelpTopic.addActionListener(rmAction);
		jMenuHelpAbout.addActionListener(rmAction);

		jMenuUpdate1.addActionListener(rmAction);
		jMenuView1.addActionListener(rmAction);
		jMenuDelete1.addActionListener(rmAction);
		jMenuRefresh1.addActionListener(rmAction);
		jMenuClear1.addActionListener(rmAction);
		jMenuCreate1.addActionListener(rmAction);
		jMenuCopy1.addActionListener(rmAction);
		jMenuCopyFrom1.addActionListener(rmAction);
		jMenuPaste1.addActionListener(rmAction);
		jMenuPasteTo1.addActionListener(rmAction);
		// }}

		// {{为路线树注册监听
		RootTreeSelectionListener treeSelectListener = new RootTreeSelectionListener();
		routeTreePanel.addTreeSelectionListener(treeSelectListener);
		RouteTreeMouseListener treeMouseListener = new RouteTreeMouseListener();
		routeTreePanel.addTreeMouseListener(treeMouseListener);
		RouteTreePropertyChangeListener propertyChangeListener = new RouteTreePropertyChangeListener();
		routeTreePanel.addPropertyChangeListener(propertyChangeListener);
		// }}

		popup.add(jMenuCreate1);
		popup.add(jMenuUpdate1);
		popup.add(jMenuView1);
		// CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
		popup.add(jMenuViewParent1);
		popup.add(jMenuViewpart);
		// CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
		popup.addSeparator();
		popup.add(jMenuDelete1);
		popup.addSeparator();
		popup.add(jMenuCopy1);
		popup.add(jMenuPaste1);
		popup.addSeparator();
		popup.add(jMenuCopyFrom1);
		popup.add(jMenuPasteTo1);
		popup.addSeparator();
		popup.add(jMenuRefresh1);
		popup.add(jMenuClear1);
		setNullMenu();
	}

	public JFrame getParentJFrame() {
		return this.parentJFrame;
	}

	/**
	 * 设置路线管理器中应具备的数据信息(启动新的路线管理器时，须调用本方法)
	 * 
	 * @param partlinks
	 *            零部件关联的集合
	 * @throws QMRemoteException
	 */
	private void setRouteListData(Vector partlinks) throws QMRemoteException {
		if (partlinks != null && partlinks.size() > 0) {
			myPartLinks = partlinks;
			ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) partlinks
					.elementAt(0);
			myRouteList = (TechnicsRouteListInfo) RParentJPanel
					.refreshInfo(link.getRouteListID());
			QMPartMasterInfo part = (QMPartMasterInfo) RParentJPanel
					.refreshInfo(myRouteList.getProductMasterID());
			this.routeTreePanel = new RouteTreePanel(myRouteList
					.getRouteListNumber()
					+ " "
					+ myRouteList.getRouteListLevel()
					+ " 用于产品"
					+ part.getPartNumber());

			addPartsToTree(partlinks);
		}

	}

	/**
	 * 获得路线表
	 * 
	 * @return 当前正在编辑的路线表
	 */
	public TechnicsRouteListIfc getRouteList() {
		return myRouteList;
	}

	/**
	 * 设置新选择的所有零部件（执行“重选”时须调用本方法） 注意：如果某零部件已在工艺路线管理器中，则不重新添加此零部件
	 * 
	 * @param addLinks
	 *            重新选择的所有零部件关联
	 */
	public void setParts(Vector addLinks) {
		if (addLinks != null && addLinks.size() > 0) {
			Vector v = new Vector();
			for (int i = 0; i < addLinks.size(); i++) {
				ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) addLinks
						.elementAt(i);
				if (!myPartLinks.contains(link)) {
					myPartLinks.addElement(link);
					v.addElement(link);
				}
			}
			addPartsToTree(v);
		}
	}

	/**
	 * 获得路线管理器中的所有零部件关联
	 * 
	 * @return 零部件关联对象的集合
	 */
	public Vector getPartLinks() {
		return myPartLinks;
	}

	/**
	 * 把选定的零部件关联添加到路线树中
	 * 
	 * @param v
	 *            零部件关联值对象的集合
	 */
	private void addPartsToTree(Vector v) {
		for (int i = 0; i < v.size(); i++) {
			ListRoutePartLinkIfc partlink = (ListRoutePartLinkIfc) v
					.elementAt(i);
			RoutePartTreeObject treeobj = new RoutePartTreeObject(partlink);
			this.getTreePanel().addNode(treeobj);
		}
		this.refresh();
	}

	/**
	 * 打开“关于”界面
	 */
	public void helpAbout() {
		CappRouteManageJFrame_AboutBox dlg = new CappRouteManageJFrame_AboutBox(
				this);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
				(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.show();
	}

	// Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		// super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			dispose();
	        //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线    
            ((CappRouteListManageJFrame) parentJFrame).myrefresh();
       //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
		}
	}

	/**
	 * 初始化所使用的资源绑定信息类
	 */
	protected void initResources() {
		try {
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
	 * @return ResourceBundle
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

	protected String[] getValueSet(ResourceBundle rb, String key) {
		String[] values = null;
		try {
			String value = rb.getString(key);
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
	 * 处理工具条上的按钮的命令.当用户点击工具条上的按钮时会发出一个命令,命令名称 与按钮图片的名称一样
	 * 
	 * @param e
	 *            ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		this.refresh();
		this.closeContentPanel();

		String name = e.getActionCommand();
		if (name.equals("route_create")) {
			controller.processCreateCommand();
		} else if (name.equals("route_update")) {
			controller.processUpdateCommand();
		} else if (name.equals("route_delete")) {
			controller.processDeleteCommand();
		} else if (name.equals("public_refresh")) {
			controller.processRefreshCommand();
		} else if (name.equals("public_clear")) {
			controller.processClearCommand();
		} else if (name.equals("route_view")) {
			controller.processViewCommand();
		} else if (name.equals("route_selectPart")) {
			controller.processSelectCommand();
		} else if (name.equals("public_help")) {
			controller.processHelpManageCommand();
		}
		// zz 20061108
		else if (name.equals("public_about")) {
			controller.processAboutCommand();
		}
		// zz 20061108
	}

	/**
	 * 刷新主界面
	 */
	public void refresh() {
		this.invalidate();
		this.doLayout();
		this.repaint();
	}
	
    //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
	private void createReportFromRouteEditingDialog() {
	CappRouteListManageJFrame hehe = null;
	if (parentJFrame instanceof CappRouteListManageJFrame) {
		hehe = (CappRouteListManageJFrame) parentJFrame;
	} else {
		return;
	}
	if (hehe == null) {
		return;
	}
	RouteListTreeObject obj = (RouteListTreeObject) hehe.getTreePanel()
			.getSelectedObject();
	ReportFormsJDialog dialog = new ReportFormsJDialog(this);
	dialog.setRouteList((TechnicsRouteListInfo) obj.getObject());
	dialog.setVisible(true);
}
//  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线

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
	class RMenuAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();
			CappRouteManageJDialog.this.refresh();
			CappRouteManageJDialog.this.closeContentPanel();

			if (object == jMenuCreate || object == jMenuCreate1) {
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
				controller.processExitCommand();
			} else if (object == jMenuCopy || object == jMenuCopy1) {
				controller.processCopyCommand();
			} else if (object == jMenuCopyFrom || object == jMenuCopyFrom1) {
				controller.processCopyFromCommand();
			} else if (object == jMenuPaste || object == jMenuPaste1) {
				controller.processPasteCommand();
			} else if (object == jMenuPasteTo || object == jMenuPasteTo1) {
				controller.processPasteToCommand();
			} else if (object == jMenuView || object == jMenuView1) {
				controller.processViewCommand();
                //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
		    } else if (object == jMenuViewParent || object == jMenuViewParent1) {
			controller.processViewParentCommand();
		    } else if (object == jMenuViewpart) {
			controller.processViewPartCommand();   
	            //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
			} else if (object == jMenuReselect) {
				controller.processSelectCommand();
			} else if (object == jMenuHelpTopic) {
				controller.processHelpManageCommand();
			} else if (object == jMenuHelpAbout) {
				controller.processAboutCommand();
			}
		}
	}

	/**
	 * 
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
	class RootTreeSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			RouteTreeObject obj = CappRouteManageJDialog.this.getTreePanel()
					.getSelectedObject();

			enableMenuItems(obj);

			// 如果选中节点是RoutePartTreeObject,则弹出查看界面
			if (obj != null && obj instanceof RoutePartTreeObject) {
				CappRouteManageJDialog.this.refresh();
				CappRouteManageJDialog.this.closeContentPanel();
				CappRouteManageJDialog.this.setState((ListRoutePartLinkIfc) obj
						.getObject());
				controller.viewDefaultRoute();
			}
			// else { //Begin CR1

			// CappRouteManageJDialog.this.setState(null);
			// CappRouteManageJDialog.this.getTaskPanel().setVisible(false);
			// } //End CR1
		}
	}

	class RouteTreePropertyChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			RouteTreeObject obj = CappRouteManageJDialog.this.getTreePanel()
					.getSelectedObject();

			enableMenuItems(obj);
		}
	}

	/** 鼠标监听 */
	class RouteTreeMouseListener extends MouseAdapter {
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
					RoutePartTreeObject treeObj = (RoutePartTreeObject) tree
							.getSelectedObject();
					if (treeObj != null) {
						popup.show(tree, e.getX(), e.getY());
						setPopupState();
					}
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
	 *            选择对象
	 */
	protected void enableMenuItems(Object obj) {
		if (verbose)
			System.out
					.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() begin...");
		// 在管理器中没有选择对象时菜单的有效性
		if (obj == null) {
			setNullMenu();
		}
		// 在管理器中选择对象为路线表时菜单的有效性
		else if (obj instanceof RoutePartTreeObject) {

			RoutePartTreeObject treeobj = (RoutePartTreeObject) obj;
			if (treeobj.getObject() == null) {

				setNullMenu();
			} else {
				setRouteMenu();

			}
		} else {
			setNullMenu();
		}
		if (verbose)
			System.out
					.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() end...return is void");
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
	 * @return RouteTaskJPanel
	 */
	public RouteTaskJPanel getTaskPanel() {
		return this.routeTaskJPanel;
	}

	/**
	 * 设置在管理器中没有选择对象时菜单的有效性
	 */
	private void setNullMenu() {
		jMenuCreate.setEnabled(false);
		jMenuRefresh.setEnabled(false);
		jMenuClear.setEnabled(false);
		jMenuUpdate.setEnabled(false);
		jMenuDelete.setEnabled(false);
		jMenuCopy.setEnabled(false);
		jMenuCopyFrom.setEnabled(false);
		jMenuPaste.setEnabled(false);
		jMenuPasteTo.setEnabled(false);
		jMenuView.setEnabled(false);
        //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
        jMenuViewParent.setEnabled(true);
        //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
	}

	/**
	 * 设置在管理器中选择对象为路线时菜单的有效性
	 */
	private void setRouteMenu() {

		ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) this
				.getTreePanel().getSelectedObject().getObject();
		if (link.getRouteID() == null) // 无路线
		{
			jMenuCreate.setEnabled(true);
			jMenuUpdate.setEnabled(false);
			jMenuDelete.setEnabled(false);
			jMenuView.setEnabled(false);
			jMenuCopy.setEnabled(false);
		} else // 有路线
		{
			jMenuCreate.setEnabled(false);
			jMenuUpdate.setEnabled(true);
			jMenuDelete.setEnabled(true);
			jMenuView.setEnabled(true);
			jMenuCopy.setEnabled(true);
            //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
            jMenuViewParent.setEnabled(true);
            //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
		}
		jMenuRefresh.setEnabled(true);
		jMenuClear.setEnabled(true);

		jMenuCopyFrom.setEnabled(true);
		// 剪贴板中不空时有效
		if (controller.originalRouteID != null) {
			jMenuPaste.setEnabled(true);
			jMenuPasteTo.setEnabled(true);
		} else {
			jMenuPaste.setEnabled(false);
			jMenuPasteTo.setEnabled(false);
		}
	}

	/**
	 * 设置右键菜单的有效性
	 */
	private void setPopupState() {
		ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) this
				.getTreePanel().getSelectedObject().getObject();
		if (link.getRouteID() == null) // 无路线
		{
			jMenuCreate1.setEnabled(true);
			jMenuUpdate1.setEnabled(false);
			jMenuDelete1.setEnabled(false);
			jMenuView1.setEnabled(false);
			jMenuCopy1.setEnabled(false);
            //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
            jMenuViewParent1.setEnabled(true);
            //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
		} else // 有路线
		{
			jMenuCreate1.setEnabled(false);
			jMenuUpdate1.setEnabled(true);
			jMenuDelete1.setEnabled(true);
			jMenuView1.setEnabled(true);
			jMenuCopy1.setEnabled(true);
            //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
            jMenuViewParent1.setEnabled(true);
            //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
		}
		// 剪贴板中不空时有效
		if (controller.originalRouteID != null) {
			jMenuPaste1.setEnabled(true);
			jMenuPasteTo1.setEnabled(true);
		} else {
			jMenuPaste1.setEnabled(false);
			jMenuPasteTo1.setEnabled(false);
		}

	}

	/**
	 * 关闭工艺内容面板 此方法用于每次进行某一菜单操作时更换界面
	 */
	public void closeContentPanel() {
		// this.contentJPanel.removeAll();

		try {
			if (routeTaskJPanel != null && routeTaskJPanel.isShowing()
					&& !routeTaskJPanel.isSave)
				routeTaskJPanel.processCancelCommond();
		} catch (QMRemoteException ex) {
			JOptionPane.showMessageDialog(this, ex.getClientMessage(),
					QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 设置当前操作状态(即标明是哪个零件的路线)
	 * 
	 * @param link
	 *            ListRoutePartLinkIfc
	 */
	public void setState(ListRoutePartLinkIfc link) {
		if (link != null) {
			QMPartMasterIfc part = link.getPartMasterInfo();
			stateJLabel.setText(" 路线" + part.getPartNumber() + "的内容");
		} else {
			stateJLabel.setText(" 路线的内容");
		}
	}

	/**
	 * 鼠标监听
	 */
	class MyMouseListener extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			// 工具条按钮
			if (obj instanceof JButton) {
				JButton button = (JButton) obj;
				statusBar.setText((String) button.getActionCommand());
			}
			// 菜单项
			if (obj instanceof QMMenuItem) {
				QMMenuItem item = (QMMenuItem) obj;
				String s = item.getExplainText();
				statusBar.setText(s);
			}
			// 菜单
			if (obj instanceof QMMenu) {
				QMMenu item = (QMMenu) obj;
				statusBar.setText(item.getExplainText());
			}
		}

		public void mouseExited(MouseEvent e) {
			// 鼠标移出时，状态栏显示缺省信息
			statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"default_status", null));
		}
	}

	/** 鼠标监听 */
	class MyMouseListener1 extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			// 工具条按钮
			if (obj instanceof JButton) {
				JButton button = (JButton) obj;
				String descripe = (String) button.getActionCommand();
				statusBar.setText(qmToolBar.getButtonDescription(descripe));
			}
			// 菜单项
			if (obj instanceof QMMenuItem) {
				QMMenuItem item = (QMMenuItem) obj;
				String s = item.getExplainText();
				statusBar.setText(s);
			}
			// 菜单
			if (obj instanceof QMMenu) {
				QMMenu item = (QMMenu) obj;
				statusBar.setText(item.getExplainText());
			}
		}

		public void mouseExited(MouseEvent e) {
			// 鼠标移出时，状态栏显示缺省信息
			statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"default_status", null));
		}
	}
	
	void jMenuItem1_actionPerformed(ActionEvent e) {
		createReportFromRouteEditingDialog();
	}

}
//  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线 v4r3
class CappRouteManageJDialog_jMenuItem1_actionAdapter implements
		java.awt.event.ActionListener {
	CappRouteManageJDialog adaptee;

	CappRouteManageJDialog_jMenuItem1_actionAdapter(
			CappRouteManageJDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItem1_actionPerformed(e);
	}
	  //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
}
