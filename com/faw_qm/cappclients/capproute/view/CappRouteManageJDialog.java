/**
 * ���ɳ��� CappRouteManageJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 ������ 2009/06/04   ������:v4r3FunctionTest;TD��2291
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
 * Title:����·�߹�����
 * </p>
 * <p>
 * Description:
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

public class CappRouteManageJDialog extends JDialog implements ActionListener {
	private JPanel contentPane;

	private JMenuBar jMenuBar1 = new JMenuBar();

	private QMMenu jMenuFile = new QMMenu("�ļ�", new MyMouseListener());

	private QMMenu jMenuHelp = new QMMenu("����", new MyMouseListener());

	/** ������ */
	private QMToolBar qmToolBar = new QMToolBar();

	/** ����ָ���� */
	private JSplitPane jSplitPane = new JSplitPane();

	private JLabel statusBar = new JLabel();

	private BorderLayout borderLayout1 = new BorderLayout();

	/** ���ڱ����Դ�ļ�·�� */
	protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

	/** ���ڱ����Դ */
	protected static ResourceBundle resource = null;

	private JPanel leftJPanel = new JPanel();

	private JPanel rightJPanel = new JPanel();

	/** ·���� */
	private RouteTreePanel routeTreePanel;

	private QMMenuItem jMenuCreate = new QMMenuItem("��������·��",
			new MyMouseListener());

	private QMMenuItem jMenuCreate1 = new QMMenuItem("��������·��",
			new MyMouseListener());

	private QMMenuItem jMenuUpdate = new QMMenuItem("����", new MyMouseListener());

	private QMMenuItem jMenuUpdate1 = new QMMenuItem("����",
			new MyMouseListener());

	private QMMenuItem jMenuDelete = new QMMenuItem("ɾ��", new MyMouseListener());

	private QMMenuItem jMenuDelete1 = new QMMenuItem("ɾ��",
			new MyMouseListener());

	private QMMenuItem jMenuRefresh = new QMMenuItem("ˢ��",
			new MyMouseListener());

	private QMMenuItem jMenuRefresh1 = new QMMenuItem("ˢ��",
			new MyMouseListener());

	private QMMenuItem jMenuClear = new QMMenuItem("���", new MyMouseListener());

	private QMMenuItem jMenuClear1 = new QMMenuItem("���", new MyMouseListener());

	private QMMenuItem jMenuExit = new QMMenuItem("�˳�", new MyMouseListener());

	private QMMenu jMenuEdit = new QMMenu("�༭", new MyMouseListener());

	private QMMenuItem jMenuCopy = new QMMenuItem("����", new MyMouseListener());

	private QMMenuItem jMenuCopy1 = new QMMenuItem("����", new MyMouseListener());

	private QMMenuItem jMenuCopyFrom = new QMMenuItem("������",
			new MyMouseListener());

	private QMMenuItem jMenuCopyFrom1 = new QMMenuItem("������",
			new MyMouseListener());

	private QMMenuItem jMenuPaste = new QMMenuItem("ճ��", new MyMouseListener());

	private QMMenuItem jMenuPaste1 = new QMMenuItem("ճ��", new MyMouseListener());

	private QMMenuItem jMenuPasteTo = new QMMenuItem("ճ����",
			new MyMouseListener());

	private QMMenuItem jMenuPasteTo1 = new QMMenuItem("ճ����",
			new MyMouseListener());

	private QMMenu jMenuRoute = new QMMenu("·��", new MyMouseListener());

	private QMMenuItem jMenuView = new QMMenuItem("�鿴", new MyMouseListener());

	private QMMenuItem jMenuView1 = new QMMenuItem("�鿴", new MyMouseListener());

	// CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
	private QMMenuItem jMenuViewParent = new QMMenuItem("�鿴װ��λ��",
			new MyMouseListener());

	private QMMenuItem jMenuViewParent1 = new QMMenuItem("�鿴װ��λ��",
			new MyMouseListener());

	private QMMenuItem jMenuViewpart = new QMMenuItem("�鿴�㲿����Ϣ",
			new MyMouseListener());

	// CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��

	private QMMenuItem jMenuReselect = new QMMenuItem("ѡ��",
			new MyMouseListener());

	private QMMenuItem jMenuHelpTopic = new QMMenuItem("��������",
			new MyMouseListener());

	private QMMenuItem jMenuHelpAbout = new QMMenuItem("����",
			new MyMouseListener());

	/** ������Ա��� */
	private static boolean verbose = (RemoteProperty.getProperty(
			"com.faw_qm.cappclients.verbose", "true")).equals("true");

	/** ������ */
	private CappRouteManageController controller;

	/** ·��ά����� */
	private RouteTaskJPanel routeTaskJPanel;

	private JLabel stateJLabel = new JLabel();

	private JLabel jLabel2 = new JLabel();

	private BorderLayout borderLayout2 = new BorderLayout();

	/** ��ǰ�༭��·�߱� */
	private TechnicsRouteListIfc myRouteList;

	/** ���棺·�߹����������е��㲿�� */
	private Vector myPartLinks = new Vector();

	/** �������·�ߵ��߳��Ƿ��Ѿ����� */
	public boolean threadEnd = false;

	private JPanel jPanel1 = new JPanel();

	private BorderLayout borderLayout3 = new BorderLayout();

	private BorderLayout borderLayout4 = new BorderLayout();

	/** �����˵� */
	private JPopupMenu popup = new JPopupMenu();

	/** ������ */
	private JFrame parentJFrame;

	// CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
	JMenuItem jMenuItem1 = new JMenuItem();

	/** ���棺·�߹����������е�·��. ��Ϊ�㲿��BsoID,ֵΪ·��ֵ���� */
	private HashMap routeMap = new HashMap();

	// CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��

	/**
	 * ���캯��
	 * 
	 * @param parentFrame
	 *            ������
	 * @param partlinks
	 *            Ҫ�༭·�ߵĹ���ListRoutePartLink�ļ���
	 */
	public CappRouteManageJDialog(JFrame parentFrame, Vector partlinks) {
		super(parentFrame, "", true);// leix jfΪfalse ��Ϊ����
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.parentJFrame = parentFrame;
			// ��ʾѡ�е�·�߱���������ڴ˽������������
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
	 * ���������ʼ��
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
		this.setTitle("����·�߱༭��");
		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.setText("��ӭ���빤��·�߱༭��. . .");
		jMenuFile.setText("�ļ�(F)");
		jMenuFile.setMnemonic('F');
		jMenuHelp.setText("����(H)");
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
		jMenuCreate.setText("�½�(N)");
		jMenuCreate.setMnemonic('N');
		KeyStroke kscreate = KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Event.CTRL_MASK);
		jMenuCreate.setAccelerator(kscreate);
		jMenuCreate1.setText("����(N)");
		jMenuCreate1.setMnemonic('N');
		KeyStroke kscreate1 = KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Event.CTRL_MASK);
		jMenuCreate1.setAccelerator(kscreate1);
		jMenuUpdate.setText("����(U)");
		jMenuUpdate.setMnemonic('U');
		KeyStroke ksUpdate = KeyStroke.getKeyStroke(KeyEvent.VK_U,
				Event.CTRL_MASK);
		jMenuUpdate.setAccelerator(ksUpdate);
		jMenuDelete.setText("ɾ��(D)");
		jMenuDelete.setMnemonic('D');
		KeyStroke ksDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D,
				Event.CTRL_MASK);
		jMenuDelete.setAccelerator(ksDelete);
		jMenuRefresh.setText("ˢ��(E)");
		jMenuRefresh.setMnemonic('E');
		KeyStroke ksRefresh = KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Event.CTRL_MASK);
		jMenuRefresh.setAccelerator(ksRefresh);
		jMenuClear.setText("���(A)");
		jMenuClear.setMnemonic('A');
		KeyStroke ksClear = KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK);
		jMenuClear.setAccelerator(ksClear);
		jMenuClear.setAccelerator(ksClear);
		jMenuUpdate1.setText("����(U)");
		jMenuUpdate1.setMnemonic('U');
		KeyStroke ksUpdate1 = KeyStroke.getKeyStroke(KeyEvent.VK_U,
				Event.CTRL_MASK);
		jMenuUpdate1.setAccelerator(ksUpdate1);
		jMenuDelete1.setText("ɾ��(D)");
		jMenuDelete1.setMnemonic('D');
		KeyStroke ksDelete1 = KeyStroke.getKeyStroke(KeyEvent.VK_D,
				Event.CTRL_MASK);
		jMenuDelete1.setAccelerator(ksDelete1);
		jMenuRefresh1.setText("ˢ��(E)");
		jMenuRefresh1.setMnemonic('E');
		KeyStroke ksRefresh1 = KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Event.CTRL_MASK);
		jMenuRefresh1.setAccelerator(ksRefresh1);
		jMenuClear1.setText("���(A)");
		jMenuClear1.setMnemonic('A');
		KeyStroke ksClear1 = KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK);
		jMenuClear1.setAccelerator(ksClear1);
		jMenuExit.setText("�˳�(X)");
		jMenuExit.setMnemonic('X');
		KeyStroke ksExit = KeyStroke.getKeyStroke(KeyEvent.VK_X,
				Event.CTRL_MASK);
		jMenuExit.setAccelerator(ksExit);
		jMenuEdit.setText("�༭(E)");
		jMenuEdit.setMnemonic('E');
		jMenuCopy.setText("����(C)");
		jMenuCopy.setMnemonic('C');
		KeyStroke ksCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Event.CTRL_MASK);
		jMenuCopy.setAccelerator(ksCopy);
		jMenuCopyFrom.setText("������(F)...");
		jMenuCopyFrom.setMnemonic('F');
		KeyStroke ksCopyFrom = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Event.CTRL_MASK);
		jMenuCopyFrom.setAccelerator(ksCopyFrom);
		jMenuPaste.setText("ճ��(P)");
		jMenuPaste.setMnemonic('P');
		KeyStroke ksPaste = KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Event.CTRL_MASK);
		jMenuPaste.setAccelerator(ksPaste);
		jMenuPasteTo.setText("ճ����(T)...");
		jMenuPasteTo.setMnemonic('T');
		KeyStroke ksPasteTo = KeyStroke.getKeyStroke(KeyEvent.VK_T,
				Event.CTRL_MASK);
		jMenuPasteTo.setAccelerator(ksPasteTo);
		jMenuCopy1.setText("����(C)");
		jMenuCopy1.setMnemonic('C');
		KeyStroke ksCopy1 = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Event.CTRL_MASK);
		jMenuCopy1.setAccelerator(ksCopy1);
		jMenuCopyFrom1.setText("������(F)...");
		jMenuCopyFrom1.setMnemonic('F');
		KeyStroke ksCopyFrom1 = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Event.CTRL_MASK);
		jMenuCopyFrom1.setAccelerator(ksCopyFrom1);
		jMenuPaste1.setText("ճ��(P)");
		jMenuPaste1.setMnemonic('P');
		KeyStroke ksPaste1 = KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Event.CTRL_MASK);
		jMenuPaste1.setAccelerator(ksPaste1);
		jMenuPasteTo1.setText("ճ����(T)...");
		jMenuPasteTo1.setMnemonic('T');
		KeyStroke ksPasteTo1 = KeyStroke.getKeyStroke(KeyEvent.VK_T,
				Event.CTRL_MASK);
		jMenuPasteTo1.setAccelerator(ksPasteTo1);
		jMenuRoute.setText("���(B)");
		jMenuRoute.setMnemonic('B');
		jMenuView.setText("�鿴(V)");
		jMenuView.setMnemonic('V');
		KeyStroke ksView = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				Event.CTRL_MASK);
		jMenuView.setAccelerator(ksView);
		jMenuView1.setText("�鿴(V)");
		// CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
		jMenuViewParent.setText("�鿴װ��λ��(I)");
		jMenuView.setMnemonic('V');
		jMenuViewParent.setMnemonic('I');
		// CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
		KeyStroke ksView1 = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				Event.CTRL_MASK);
		jMenuView1.setAccelerator(ksView1);
		// CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
		jMenuViewParent1.setText("�鿴װ��λ��(I)");
		jMenuViewParent1.setMnemonic('I');
		KeyStroke ksViewP1 = KeyStroke.getKeyStroke(KeyEvent.VK_I,
				Event.CTRL_MASK);
		jMenuViewParent1.setAccelerator(ksViewP1);
		jMenuViewpart.setText("�鿴�㲿��");
		// CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
		jMenuReselect.setText("ѡ��(S)");
		jMenuReselect.setMnemonic('S');
		KeyStroke ksReselect = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Event.CTRL_MASK);
		jMenuReselect.setAccelerator(ksReselect);
		jMenuHelpTopic.setText("��������(S)");
		jMenuHelpTopic.setMnemonic('S');
		jMenuHelpAbout.setText("����(A)...");
		jMenuHelpAbout.setMnemonic('A');
		stateJLabel.setBorder(BorderFactory.createEtchedBorder());
		stateJLabel.setText("·�ߵ�����");
		jLabel2.setBorder(BorderFactory.createEtchedBorder());
		jLabel2.setText("����·��");
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setLayout(borderLayout3);
		// CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
		jMenuItem1.setMnemonic('R');
		jMenuItem1.setText("���ɱ���(R)");
		jMenuItem1
				.addActionListener(new CappRouteManageJDialog_jMenuItem1_actionAdapter(
						this));
		// CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
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
		// CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
		jMenuRoute.add(jMenuViewParent);
		jMenuRoute.add(jMenuReselect);
		jMenuRoute.add(jMenuItem1);
		// CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��

		// {{ע��˵�����
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
		// CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
		jMenuViewParent.addActionListener(rmAction);
		jMenuReselect.addActionListener(rmAction);
		jMenuHelpTopic.addActionListener(rmAction);
		jMenuHelpAbout.addActionListener(rmAction);

		jMenuUpdate1.addActionListener(rmAction);
		jMenuView1.addActionListener(rmAction);
		jMenuViewParent1.addActionListener(rmAction);
		jMenuViewpart.addActionListener(rmAction);
		jMenuViewpart.setEnabled(true);
		// CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
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

		// {{Ϊ·����ע�����
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
		// CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
		popup.add(jMenuViewParent1);
		popup.add(jMenuViewpart);
		// CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
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
	 * ����·�߹�������Ӧ�߱���������Ϣ(�����µ�·�߹�����ʱ������ñ�����)
	 * 
	 * @param partlinks
	 *            �㲿�������ļ���
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
					+ " ���ڲ�Ʒ"
					+ part.getPartNumber());

			addPartsToTree(partlinks);
		}

	}

	/**
	 * ���·�߱�
	 * 
	 * @return ��ǰ���ڱ༭��·�߱�
	 */
	public TechnicsRouteListIfc getRouteList() {
		return myRouteList;
	}

	/**
	 * ������ѡ��������㲿����ִ�С���ѡ��ʱ����ñ������� ע�⣺���ĳ�㲿�����ڹ���·�߹������У���������Ӵ��㲿��
	 * 
	 * @param addLinks
	 *            ����ѡ��������㲿������
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
	 * ���·�߹������е������㲿������
	 * 
	 * @return �㲿����������ļ���
	 */
	public Vector getPartLinks() {
		return myPartLinks;
	}

	/**
	 * ��ѡ�����㲿��������ӵ�·������
	 * 
	 * @param v
	 *            �㲿������ֵ����ļ���
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
	 * �򿪡����ڡ�����
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
	        //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��    
            ((CappRouteListManageJFrame) parentJFrame).myrefresh();
       //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
		}
	}

	/**
	 * ��ʼ����ʹ�õ���Դ����Ϣ��
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
	 * �����Դ��Ϣ
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
	 * ��ð�ť��Ϣ��ʾ����,����������
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
	 * ���������ϵİ�ť������.���û�����������ϵİ�ťʱ�ᷢ��һ������,�������� �밴ťͼƬ������һ��
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
	 * ˢ��������
	 */
	public void refresh() {
		this.invalidate();
		this.doLayout();
		this.repaint();
	}
	
    //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
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
//  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��

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
                //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
		    } else if (object == jMenuViewParent || object == jMenuViewParent1) {
			controller.processViewParentCommand();
		    } else if (object == jMenuViewpart) {
			controller.processViewPartCommand();   
	            //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
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
	class RootTreeSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			RouteTreeObject obj = CappRouteManageJDialog.this.getTreePanel()
					.getSelectedObject();

			enableMenuItems(obj);

			// ���ѡ�нڵ���RoutePartTreeObject,�򵯳��鿴����
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

	/** ������ */
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
	 * ������ȡ���������ȷ���˵������Ч��
	 * 
	 * @param obj
	 *            ѡ�����
	 */
	protected void enableMenuItems(Object obj) {
		if (verbose)
			System.out
					.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() begin...");
		// �ڹ�������û��ѡ�����ʱ�˵�����Ч��
		if (obj == null) {
			setNullMenu();
		}
		// �ڹ�������ѡ�����Ϊ·�߱�ʱ�˵�����Ч��
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
	 * @return RouteTaskJPanel
	 */
	public RouteTaskJPanel getTaskPanel() {
		return this.routeTaskJPanel;
	}

	/**
	 * �����ڹ�������û��ѡ�����ʱ�˵�����Ч��
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
        //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
        jMenuViewParent.setEnabled(true);
        //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
	}

	/**
	 * �����ڹ�������ѡ�����Ϊ·��ʱ�˵�����Ч��
	 */
	private void setRouteMenu() {

		ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) this
				.getTreePanel().getSelectedObject().getObject();
		if (link.getRouteID() == null) // ��·��
		{
			jMenuCreate.setEnabled(true);
			jMenuUpdate.setEnabled(false);
			jMenuDelete.setEnabled(false);
			jMenuView.setEnabled(false);
			jMenuCopy.setEnabled(false);
		} else // ��·��
		{
			jMenuCreate.setEnabled(false);
			jMenuUpdate.setEnabled(true);
			jMenuDelete.setEnabled(true);
			jMenuView.setEnabled(true);
			jMenuCopy.setEnabled(true);
            //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
            jMenuViewParent.setEnabled(true);
            //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
		}
		jMenuRefresh.setEnabled(true);
		jMenuClear.setEnabled(true);

		jMenuCopyFrom.setEnabled(true);
		// �������в���ʱ��Ч
		if (controller.originalRouteID != null) {
			jMenuPaste.setEnabled(true);
			jMenuPasteTo.setEnabled(true);
		} else {
			jMenuPaste.setEnabled(false);
			jMenuPasteTo.setEnabled(false);
		}
	}

	/**
	 * �����Ҽ��˵�����Ч��
	 */
	private void setPopupState() {
		ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) this
				.getTreePanel().getSelectedObject().getObject();
		if (link.getRouteID() == null) // ��·��
		{
			jMenuCreate1.setEnabled(true);
			jMenuUpdate1.setEnabled(false);
			jMenuDelete1.setEnabled(false);
			jMenuView1.setEnabled(false);
			jMenuCopy1.setEnabled(false);
            //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
            jMenuViewParent1.setEnabled(true);
            //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
		} else // ��·��
		{
			jMenuCreate1.setEnabled(false);
			jMenuUpdate1.setEnabled(true);
			jMenuDelete1.setEnabled(true);
			jMenuView1.setEnabled(true);
			jMenuCopy1.setEnabled(true);
            //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
            jMenuViewParent1.setEnabled(true);
            //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
		}
		// �������в���ʱ��Ч
		if (controller.originalRouteID != null) {
			jMenuPaste1.setEnabled(true);
			jMenuPasteTo1.setEnabled(true);
		} else {
			jMenuPaste1.setEnabled(false);
			jMenuPasteTo1.setEnabled(false);
		}

	}

	/**
	 * �رչ���������� �˷�������ÿ�ν���ĳһ�˵�����ʱ��������
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
	 * ���õ�ǰ����״̬(���������ĸ������·��)
	 * 
	 * @param link
	 *            ListRoutePartLinkIfc
	 */
	public void setState(ListRoutePartLinkIfc link) {
		if (link != null) {
			QMPartMasterIfc part = link.getPartMasterInfo();
			stateJLabel.setText(" ·��" + part.getPartNumber() + "������");
		} else {
			stateJLabel.setText(" ·�ߵ�����");
		}
	}

	/**
	 * ������
	 */
	class MyMouseListener extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			// ��������ť
			if (obj instanceof JButton) {
				JButton button = (JButton) obj;
				statusBar.setText((String) button.getActionCommand());
			}
			// �˵���
			if (obj instanceof QMMenuItem) {
				QMMenuItem item = (QMMenuItem) obj;
				String s = item.getExplainText();
				statusBar.setText(s);
			}
			// �˵�
			if (obj instanceof QMMenu) {
				QMMenu item = (QMMenu) obj;
				statusBar.setText(item.getExplainText());
			}
		}

		public void mouseExited(MouseEvent e) {
			// ����Ƴ�ʱ��״̬����ʾȱʡ��Ϣ
			statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"default_status", null));
		}
	}

	/** ������ */
	class MyMouseListener1 extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			// ��������ť
			if (obj instanceof JButton) {
				JButton button = (JButton) obj;
				String descripe = (String) button.getActionCommand();
				statusBar.setText(qmToolBar.getButtonDescription(descripe));
			}
			// �˵���
			if (obj instanceof QMMenuItem) {
				QMMenuItem item = (QMMenuItem) obj;
				String s = item.getExplainText();
				statusBar.setText(s);
			}
			// �˵�
			if (obj instanceof QMMenu) {
				QMMenu item = (QMMenu) obj;
				statusBar.setText(item.getExplainText());
			}
		}

		public void mouseExited(MouseEvent e) {
			// ����Ƴ�ʱ��״̬����ʾȱʡ��Ϣ
			statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"default_status", null));
		}
	}
	
	void jMenuItem1_actionPerformed(ActionEvent e) {
		createReportFromRouteEditingDialog();
	}

}
//  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·�� v4r3
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
	  //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
}
