/** ���ɳ��� BatchCheckInDialog.java    1.0     2009/05/25
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1  20090616 ��ǿ �޸�ԭ��TD2256 �Ӳ�Ʒ��Ϣ���������빤�չ�̹�������
 *                             Ȼ���ڲ�Ʒ��Ϣ�����������㲿�����ִ�����ʾ��Ϣ��
 *                    �������������ˢ���¼�ʱ�¼�Դ��Vector�޸�ΪWorkableIfc��   
 * SS1 A004-2015-3158 ѡ��������������½���ʱ�޷����룬��Ϊ����㲿�����̴�����㲿������˼���ǰ��Ҫˢ�¶��� liunan 2015-7-22
 */
package com.faw_qm.part.client.other.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.clients.beans.explorer.ReferenceHolder;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.util.ContainerUtility;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.vc.util.NotCheckedOutException;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.folder.model.FolderBasedIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.view.MessageDialog;
import com.faw_qm.part.client.other.model.PartCheckInModel;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.util.HorizontalLine;
import com.faw_qm.util.QMCt;
import com.faw_qm.version.exception.VersionControlException;
import com.faw_qm.wip.model.WorkableIfc;

//CCBegin SS1
import com.faw_qm.clients.widgets.IBAUtility;
//CCEnd SS1

/**
 * <p>
 * Title: ������������Ľ��棬�����������Ѿ�������㲿�������´������㲿����
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * 
 * @author ��ǿ
 * @version 1.0
 */
public class BatchCheckInDialog extends JDialog {

	/** ���л�ID */
	static final long serialVersionUID = 1L;

	public static final int UNKNOWN = 0;
	public static final int CANCEL = 1;
	public static final int NO_UPLOAD = 2;
	public static final int DO_UPLOAD = 3;
	boolean fComponentsAdjusted;
	JLabel workableNameLabel;
	JList workableNameValue;
	DefaultListModel model = new DefaultListModel();
	JLabel checkedOutByLabel;
	JLabel checkedOutByValue;
	JLabel commentLabel;
	JTextArea commentTextArea;
	HorizontalLine horizontalLine1;
	JPanel buttonPanel;
	JButton continueButton;
	JButton cancelButton;
	private WorkableHandle checkInItemHandle;
	private FolderIfc checkInFolder;
	private boolean firstCheckInFlag;
	private ThreadGroup contextGroup;
	private static ResourceBundle resources = null;
	private static String RESOURCES = "com.faw_qm.clients.vc.util.VcRB";
	private FolderPanel folderPanel;
	private JLabel statusJLabel = new JLabel();
	Vector checkIns = null;
	JPanel jPanel1 = new JPanel();
	Border border1;
	Border border2;
	private final ProgressDialog progressDialog = new ProgressDialog(
			getParentFrame());
	// // �����ж��Ƿ����ɹ�,Ȼ��رմ˼���Ի���.
	// private boolean disFlag = true;
	private Vector firstCheckInVec = new Vector();

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company: һ������
	 * </p>
	 * 
	 * @author ��ǿ
	 * @version 2.0
	 */
	class WorkableHandle implements ReferenceHolder {
		WorkableIfc workable;

		/**
		 * 
		 * @param workable1
		 *            WorkableIfc
		 */
		public WorkableHandle(WorkableIfc workable1) {
			workable = null;
			workable = workable1;
		}

		/**
		 * 
		 * @param obj
		 *            Object
		 */
		public void setObject(Object obj) {
			if (obj instanceof WorkableIfc)
				workable = (WorkableIfc) obj;
		}

		/**
		 * 
		 * @return Object
		 */
		public Object getObject() {
			return workable;
		}

	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company: һ������
	 * </p>
	 * 
	 * @author ��ǿ
	 * @version 2.0
	 */
	class SymKey extends KeyAdapter {
		/**
     *
     */
		SymKey() {
		}

		/**
		 * 
		 * @param keyevent
		 *            KeyEvent
		 */
		public void keyPressed(KeyEvent keyevent) {
			Object obj = keyevent.getSource();
			if (obj == continueButton)
				continueButton_KeyPressed(keyevent);
			else if (obj == cancelButton)
				cancelButton_KeyPressed(keyevent);
		}

	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company: һ������
	 * </p>
	 * 
	 * @author л��
	 * @version 2.0
	 */
	class SymAction implements ActionListener {
		/**
     *
     */
		SymAction() {
		}

		/**
		 * 
		 * @param actionevent
		 *            ActionEvent
		 */
		public void actionPerformed(ActionEvent actionevent) {
			Object obj = actionevent.getSource();
			if (obj == cancelButton)
				cancelButton_Action(actionevent);
			else if (obj == continueButton)
				continueButton_Action(actionevent);
		}

	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company: һ������
	 * </p>
	 * 
	 * @author ��ǿ
	 * @version 2.0
	 */
	class SymWindow extends WindowAdapter {
		/**
     *
     */
		SymWindow() {
		}

		/**
		 * 
		 * @param windowevent
		 *            WindowEvent
		 */
		public void windowClosing(WindowEvent windowevent) {
			Object obj = windowevent.getSource();
			if (obj == BatchCheckInDialog.this)
				Dialog1_WindowClosing(windowevent);
		}

	}

	/**
	 * 
	 * <p>
	 * Title: �߳��ࡣ
	 * </p>
	 * <p>
	 * Description: �ڲ��ࡣ
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2005
	 * </p>
	 * <p>
	 * Company: һ������
	 * </p>
	 * 
	 * @author ��ǿ
	 * @version 1.0
	 */
	class WorkThread extends QMThread {
		/**
		 * ���캯����
		 */
		WorkThread() {
			super();
		}

		/**
		 * ִ�С�
		 */
		public void run() {
			processContinueCommand();
		}
	}

	/**
	 * ���캯������������������
	 * 
	 * @param frame
	 *            JFrame �����档
	 * @param s
	 *            String ���⡣
	 * @param flag
	 *            boolean �Ի����ģ�͡�
	 */
	public BatchCheckInDialog(JFrame frame, String s, boolean flag) {
		this(frame, flag);
		setTitle(s);
	}

	/**
	 * ���캯������������������
	 * 
	 * @param frame
	 *            JFrame �����档
	 * @param flag
	 *            boolean �Ի����ģ�͡�
	 */
	public BatchCheckInDialog(JFrame frame, boolean flag) {
		super(frame, flag);
		folderPanel = new FolderPanel();
		folderPanel.setIsPersonalFolder(false);
		folderPanel.setIsPublicFolders(true);
		folderPanel.setPermission("modify");
		// lily lin 2004.8.21 ���ϼв����ֹ�����
		folderPanel.setTextFielEnable(false);
		fComponentsAdjusted = false;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʼ�����档
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		fComponentsAdjusted = false;
		workableNameLabel = new JLabel();
		workableNameValue = new JList();

		workableNameValue.setModel(model);
		checkedOutByLabel = new JLabel();
		checkedOutByValue = new JLabel();
		commentLabel = new JLabel();
		JScrollPane scrollPane = new JScrollPane();
		commentTextArea = new JTextArea();
		border1 = BorderFactory.createEmptyBorder();
		border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.white, Color.white, new Color(124, 124, 124), new Color(
						178, 178, 178));
		// scrollPane.setAutoscrolls(true);
		// scrollPane.setBorder(border1);
		commentTextArea.setFont(new java.awt.Font("����", 0, 12));
		commentTextArea.setBorder(border2);
		scrollPane.getViewport().add(commentTextArea);
		commentTextArea.setAutoscrolls(true);
		commentTextArea.setDebugGraphicsOptions(0);
		horizontalLine1 = new HorizontalLine();
		buttonPanel = new JPanel();
		continueButton = new JButton();
		cancelButton = new JButton();
		checkInItemHandle = null;
		checkInFolder = null;
		firstCheckInFlag = false;
		contextGroup = null;
		contextGroup = QMCt.getContext().getThreadGroup();
		GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		setVisible(false);
		workableNameLabel.setText("Checking In:");
		checkedOutByLabel.setText("Checked Out By:");
		commentLabel.setText("Comments:");
		continueButton.setText("Continue...");
		cancelButton.setText("Cancel");
		setTitle("Check In");
		setSize(400, 300);
		// commentTextArea.setBackground(Color.white);
		commentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		commentLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		commentTextArea.setText("");
		// commentTextArea.setLineWrap(true);
		// commentTextArea.setWrapStyleWord(true);
		try {
			horizontalLine1.setBevelStyle(0);
		} catch (PropertyVetoException _ex) {
		}
		buttonPanel.setLayout(gridBagLayout);
		continueButton.setMaximumSize(new Dimension(75, 23));
		continueButton.setMinimumSize(new Dimension(75, 23));
		continueButton.setPreferredSize(new Dimension(75, 23));
		continueButton.setMnemonic(KeyEvent.VK_Y);
		cancelButton.setMaximumSize(new Dimension(75, 23));
		cancelButton.setMinimumSize(new Dimension(75, 23));
		cancelButton.setPreferredSize(new Dimension(75, 23));
		cancelButton.setMnemonic(KeyEvent.VK_C);
		// workableNameValue.setMaximumSize(new Dimension(48, 100));
		// workableNameValue.setMinimumSize(new Dimension(48, 100));
		// workableNameValue.setPreferredSize(new Dimension(48, 100));
		workableNameValue.setRequestFocusEnabled(false);

		workableNameLabel.setMaximumSize(new Dimension(48, 22));
		workableNameLabel.setMinimumSize(new Dimension(48, 22));
		workableNameLabel.setPreferredSize(new Dimension(48, 22));
		workableNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		workableNameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkedOutByValue.setMaximumSize(new Dimension(48, 22));
		checkedOutByValue.setMinimumSize(new Dimension(48, 22));
		checkedOutByValue.setPreferredSize(new Dimension(48, 22));
		checkedOutByLabel.setMaximumSize(new Dimension(48, 22));
		checkedOutByLabel.setMinimumSize(new Dimension(48, 22));
		checkedOutByLabel.setPreferredSize(new Dimension(48, 22));
		checkedOutByLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		checkedOutByLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		statusJLabel.setMaximumSize(new Dimension(45, 18));
		statusJLabel.setMinimumSize(new Dimension(45, 18));
		statusJLabel.setPreferredSize(new Dimension(45, 18));
		buttonPanel.add(continueButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		buttonPanel.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				workableNameLabel,
				new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(10, 10, 0, 0), 6, 0));
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.getViewport().add(workableNameValue);
		// workableNameValue.setEnabled(false);
		workableNameValue.setBackground(this.getBackground());
		scrollPane1.setMaximumSize(new Dimension(45, 60));
		scrollPane1.setMinimumSize(new Dimension(45, 60));
		scrollPane1.setPreferredSize(new Dimension(45, 60));
		this.getContentPane().add(
				scrollPane1,
				new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 8, 5, 15),
						0, 0));
		// this.getContentPane().add(checkedOutByLabel, new
		// GridBagConstraints(0,2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST,
		// GridBagConstraints.NONE, new Insets(16, 10, 0, 0), 6, 0));
		// this.getContentPane().add(checkedOutByValue, new
		// GridBagConstraints(1, 1, 2, 1, 1.0, 0.0,
		// GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new
		// Insets(10, 8, 0, 15), 0, 0));
		this.getContentPane().add(
				folderPanel,
				new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0,
						GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(10, 23, 10,
								15), 145, 0));
		this.getContentPane().add(
				commentLabel,
				new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				scrollPane,
				new GridBagConstraints(1, 4, 1, 3, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 8, 5, 15), 0, 0));
		this.getContentPane()
				.add(
						new JLabel(""),
						new GridBagConstraints(0, 5, 3,
								GridBagConstraints.REMAINDER, 1.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH, new Insets(0, 10, 0,
										10), 0, 0));
		this.getContentPane()
				.add(
						new JLabel(""),
						new GridBagConstraints(0, 6, 3,
								GridBagConstraints.REMAINDER, 1.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH, new Insets(0, 10, 0,
										10), 0, 0));
		this.getContentPane().add(
				buttonPanel,
				new GridBagConstraints(0, 7, 3, 1, 1.0, 0.0,
						GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL,
						new Insets(5, 10, 10, 10), 0, 0));
		this.getContentPane().add(
				statusJLabel,
				new GridBagConstraints(0, 8, 3, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
		checkedOutByLabel.setVisible(false);
		checkedOutByValue.setVisible(false);
		SymWindow symwindow = new SymWindow();
		addWindowListener(symwindow);
		SymAction symaction = new SymAction();
		cancelButton.addActionListener(symaction);
		continueButton.addActionListener(symaction);
		SymKey symkey = new SymKey();
		continueButton.addKeyListener(symkey);
		cancelButton.addKeyListener(symkey);
		localize();
	}

	/**
	 * ��ʼ����Դ�ļ���
	 */
	private void initResources() {
		try {
			resources = ResourceBundle.getBundle(RESOURCES, getContext()
					.getLocale());
		} catch (MissingResourceException missingresourceexception) {
			DialogFactory.showErrorDialog(this, missingresourceexception
					.getLocalizedMessage());
		}
	}

	/**
	 * ��ʼ��������Ϣ��
	 */
	private void localize() {
		try {
			if (resources == null)
				initResources();
			workableNameLabel.setText(resources.getString("45"));
			checkedOutByLabel.setText(resources.getString("34"));
			commentLabel.setText(resources.getString("commentLabel"));
			continueButton.setText(resources.getString("okButton"));
			cancelButton.setText(resources.getString("cancelButton"));
			setTitle(resources.getString("checkInTitle"));
		} catch (MissingResourceException missingresourceexception) {
			DialogFactory.showErrorDialog(this, missingresourceexception
					.getLocalizedMessage());
		}
	}

	/**
	 * �����Ϣ��
	 */
	public void addNotify() {
		Dimension dimension = getSize();
		super.addNotify();
		if (fComponentsAdjusted)
			return;
		setSize(getInsets().left + getInsets().right + dimension.width,
				getInsets().top + getInsets().bottom + dimension.height);
		Component acomponent[] = getComponents();
		for (int i = 0; i < acomponent.length; i++) {
			Point point = acomponent[i].getLocation();
			point.translate(getInsets().left, getInsets().top);
			acomponent[i].setLocation(point);
		}
		fComponentsAdjusted = true;
	}

	/**
	 * ʹ�Ի�����ʧ��
	 */
	public void dispose() {
		ContainerUtility.enableInputMethods(this, false);
		super.dispose();
	}

	/**
	 * ��ȡ�����Ļ�����
	 * 
	 * @return QMCt
	 */
	private QMCt getContext() {
		QMCt qmcontext = null;
		if (contextGroup != null)
			qmcontext = QMCt.getContext(contextGroup);
		if (qmcontext == null)
			qmcontext = QMCt.getContext();
		return qmcontext;
	}

	/**
	 * ���������Ļ�����
	 * 
	 * @param flag
	 *            boolean
	 */
	private void setContext(boolean flag) {
		if (flag) {
			if (contextGroup != null)
				QMCt.setContextGroup(contextGroup);
			else
				QMCt.setContext(this);
		} else
			QMCt.setContext(null);
	}

	/**
	 * �رհ�ť������
	 * 
	 * @param windowevent
	 *            WindowEvent
	 */
	void Dialog1_WindowClosing(WindowEvent windowevent) {
		dispose();
	}

	/**
	 * ȡ����ť������
	 * 
	 * @param actionevent
	 *            ActionEvent
	 */
	void cancelButton_Action(ActionEvent actionevent) {
		processCancelCommand();
	}

	/**
	 * ȡ����
	 */
	private void processCancelCommand() {
		try {
			setContext(true);
			dispose();
		} finally {
			setContext(false);
		}
	}

	/**
	 * ���ü���Ķ���
	 * 
	 * @param workable
	 *            WorkableIfc ����Ķ���
	 * @throws NotCheckedOutException
	 * @throws QMException
	 */
	private void setCheckInItems(Vector workables)
			throws NotCheckedOutException, QMException {
		PartCheckInModel partCheckInModel = null;
		BaseValueIfc workable = null;
		boolean firstCheckInFlag = false;
		String firstCheckIn = "-�״μ���";
		// �����������ǣ����ǵ�һ�μ��룬������Ҫ����Ķ���������롣
		for (Iterator ite = workables.iterator(); ite.hasNext();) {
			partCheckInModel = (PartCheckInModel) ite.next();
			workable = (BaseValueIfc) partCheckInModel.getWorkableIfc();
			firstCheckInFlag = partCheckInModel.isFirstCheckIn();
			// // �����������ǵ�һ�μ��벢�Ҳ�������û������
			// if (!firstCheckInFlag
			// && !CheckInOutTaskLogic.isCheckedOutByUser(workable)) {
			// checkedOutByValue.setText(CheckInOutTaskLogic.getCheckedOutBy(
			// workable, getContext().getLocale()));
			// System.out.println("ss "+CheckInOutTaskLogic.getCheckedOutBy(
			// workable, getContext().getLocale()));
			// checkedOutByLabel.setVisible(true);
			// checkedOutByValue.setVisible(true);
			// }
			if (firstCheckInFlag) {
				firstCheckInVec.add(workable);
				model.addElement(workable.getIdentity() + firstCheckIn);
			} else {
				model.addElement(workable.getIdentity());
			}
		}
		if (resources == null)
			initResources();
		Object aobj1[] = {};
		setTitle(QMMessage.getLocalizedMessage(RESOURCES, "checkInTitle",
				aobj1, getContext().getLocale()));
		// setItem(workables);
	}

	/**
	 * ���ö���
	 * 
	 * @param workable
	 *            WorkableIfc Ҫ����Ķ���
	 */
	protected void setItem(WorkableIfc workable) {
		if (checkInItemHandle == null)
			checkInItemHandle = new WorkableHandle(workable);
		else
			checkInItemHandle.setObject(workable);
	}

	/**
	 * ����Ҫ����Ķ���
	 * 
	 * @param workable
	 *            WorkableIfc Ҫ����Ķ���
	 * @param isFirstflag
	 *            boolean �Ƿ��ǵ�һ�μ��롣
	 * @throws NotCheckedOutException
	 * @throws QMException
	 */
	public void setCheckInItems(Vector checkInVec, boolean isFirstflag)
			throws NotCheckedOutException, QMException {
		firstCheckInFlag = isFirstflag;
		checkIns = checkInVec;
		if (!firstCheckInFlag) {
			remove(folderPanel);
			doLayout();
		}
		setCheckInItems(checkInVec);
	}

	/**
	 * �趨����׼�����뵽���ļ��У�ֻ�е�һ�μ����ʱ����Ҫ��
	 * 
	 * @param folder
	 *            FolderIfc ����׼��������ļ��С�
	 */
	public void setCheckInFolder(FolderIfc folder) {
		try {
			checkInFolder = folder;
			Class[] theClass = { FolderBasedIfc.class };
			Object[] theObjs = { checkInFolder };
			RequestHelper requestHelper = new RequestHelper();
			String pathString = (String) requestHelper.request("FolderService",
					"getPath", theClass, theObjs);
			folderPanel.setLabelText(pathString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������Ť��
	 * 
	 * @param actionevent
	 *            ActionEvent
	 */
	void continueButton_Action(ActionEvent actionevent) {
		WorkThread workThread = new WorkThread();
		workThread.start();
	}

	/**
	 * ִ�м��������
	 */
	private void processContinueCommand() {
		try {
			setContext(true);
			if (!isModal())
				setCursor(Cursor.getPredefinedCursor(3));
			int i = commentTextArea.getText().length();
			if (i > 400) {
				DialogFactory.showFormattedErrorDialog(this,
						"˵�����ֳ��Ȳ��ô���400���ַ�!");
				return;
			}
			// ��Ť�Ŀɱ༭��
			enableActions(false);
			if (checkIns != null && !checkIns.isEmpty())
				processBatchCheckIn();
			// �˳�����
			// disFlag = true;
			// } catch (InterruptedException interruptedexception) {
			// DialogFactory.showFormattedErrorDialog(this, interruptedexception
			// .getLocalizedMessage());
		} catch (PropertyVetoException propertyvetoexception) {
			Object aobj[] = {
					propertyvetoexception.getPropertyChangeEvent()
							.getNewValue(),
					propertyvetoexception.getPropertyChangeEvent()
							.getPropertyName() };
			DialogFactory.displayExceptionMessage(this, resources, "2", aobj);
		} catch (QMException qmexception) {
			DialogFactory.showFormattedErrorDialog(this, qmexception
					.getClientMessage());
		} finally {
			if (!isModal())
				setCursor(Cursor.getDefaultCursor());
			enableActions(true);
			setContext(false);
		}
	}

	/**
	 * �ַ�ˢ���źš�
	 * 
	 * @param i
	 *            int
	 * @param obj
	 *            Object
	 */
	private void dispatchRefresh(int i, Object obj) {
		if (obj != null) {

			// ������ˢ���¼�
			RefreshEvent refreshevent = new RefreshEvent(this, i, obj);
			// ��ˢ�·������ˢ���¼�
			RefreshService.getRefreshService().dispatchRefresh(refreshevent);

		}
	}
	
	//CR1 Begin 20090616 zhangq �޸�ԭ�򣺼����㲿����ʾ��Ϣ����
	/**
	 * �ַ�ˢ���źš�
	 * 
	 * @param i
	 *            int
	 * @param oldValue
	 *            Object
	 * @param newValue
	 *            Object       
	 */
	private void dispatchRefresh(int i, Object oldValue,Object newValue) {
		if ( oldValue!=null && newValue!=null) {
			PropertyChangeEvent[] propertyChanges=new PropertyChangeEvent[1];
			propertyChanges[0]=new PropertyChangeEvent(this,"iteratorVersionChange",oldValue,newValue);
			// ������ˢ���¼�
			RefreshEvent refreshevent = new RefreshEvent(this, i, newValue,propertyChanges);
			// ��ˢ�·������ˢ���¼�
			RefreshService.getRefreshService().dispatchRefresh(refreshevent);

		}
	}
	//CR1 End 20090616 zhangq 

	/**
	 * ���ð�ť�Ŀɲ����ԡ�
	 * 
	 * @param flag
	 *            boolean
	 */
	private void enableActions(boolean flag) {
		continueButton.setEnabled(flag);
		cancelButton.setEnabled(flag);
	}

	/**
	 * 
	 * @param keyevent
	 *            KeyEvent
	 */
	void continueButton_KeyPressed(KeyEvent keyevent) {
		if (keyevent.getKeyCode() == 10) {
			WorkThread workThread = new WorkThread();
			workThread.start();
		}
	}

	/**
	 * 
	 * @param keyevent
	 *            KeyEvent
	 */
	void cancelButton_KeyPressed(KeyEvent keyevent) {
		if (keyevent.getKeyCode() == 10)
			processCancelCommand();
	}

	private void processBatchCheckIn() throws PropertyVetoException,
			VersionControlException, QMException {
		// ������ϼ�Ϊ��
		if (firstCheckInFlag && checkInFolder == null
				&& folderPanel.getFolderLocation() == null) {
			if (resources == null)
				initResources();
			StringBuffer display = new StringBuffer();
			String douhao = ",";
			for (int index = 0; index < firstCheckInVec.size(); index++) {
				if (index > 0) {
					display.append(douhao);
				}
				display.append(getDisplayIdentity(firstCheckInVec.get(index)));
			}
			Object aobj[] = { display, display };
			throw new QMException(null, RESOURCES, "15", aobj);
		} else {
			progressDialog.startProcess();
			WorkableIfc workableIfc;
			String commentStr = commentTextArea.getText().trim();
			WorkableIfc workableCopy;
			WorkableIfc workableOriginal;
			PartCheckInModel partCheckInModel;
			BaseValueIfc obj;
			boolean isInTree = false;
			String checkInSucStr = "����ɹ�";
			String checkInFailStr = "����ʧ�ܣ�";
			Vector messageVec = new Vector();
			StringBuffer messageStr;
			String tempStr;
			for (int index = 0; index < checkIns.size(); index++) {
				partCheckInModel = (PartCheckInModel) checkIns.get(index);
				obj = partCheckInModel.getWorkableIfc();
				messageStr = new StringBuffer(obj.getIdentity());
				try {
					isInTree = false;
					if (partCheckInModel.isCanCheckIn()) {
						workableIfc = (WorkableIfc) obj;
						if (partCheckInModel.isFirstCheckIn()) {
							if (checkInFolder != null) {
								workableIfc = CheckInOutTaskLogic
										.checkInObject(workableIfc,
												checkInFolder, commentStr);
							} else {
							//CCBegin SS1
							BaseValueIfc baseIfc = (BaseValueIfc) workableIfc;
				            Class[] class1 = {BaseValueIfc.class};
				            Object[] param = {(BaseValueIfc) baseIfc};
				            workableIfc = (WorkableIfc) IBAUtility.invokeServiceMethod(
								        "PersistService", "refreshInfo", class1, param);
							System.out.println("����ǰˢ�¶���");
							//CCEnd SS1
								workableIfc = CheckInOutTaskLogic
										.checkInObject(workableIfc, folderPanel
												.getFolderLocation().trim(),
												commentStr);
							}
							dispatchRefresh(1, workableIfc);
							partCheckInModel.setWorkableIfc(workableIfc);
						} else {
							isInTree = partCheckInModel.isMutInTree();
							workableCopy = CheckInOutTaskLogic
									.getWorkingCopy(workableIfc);
							workableOriginal = CheckInOutTaskLogic
									.checkInObject(workableIfc, commentStr);
							if (isInTree) {
								dispatchRefresh(2, workableCopy);
							}
							//CR1 Begin 20090616 zhangq �޸�ԭ�򣺼����㲿����ʾ��Ϣ����
							// ����
//							Vector vec = new Vector(2);
//							vec.add(workableOriginal);
//							vec.add(workableCopy);
//							// dispatchRefresh(1, workableOriginal);
//							dispatchRefresh(1, vec);
							dispatchRefresh(1, workableCopy,workableOriginal);
							//CR1 End 20090616 zhangq
							partCheckInModel.setWorkableIfc(workableOriginal);
						}
						tempStr = checkInSucStr;
					} else {
						if (obj instanceof QMPartMasterIfc) {
							tempStr = checkInFailStr + "�Ҳ����������ù淶���㲿��";
						} else if (partCheckInModel.isCheckOut()) {
							tempStr = checkInFailStr + "��"
									+ partCheckInModel.getCheckOutUserName()
									+ "���";
						} else {
							tempStr = checkInFailStr + "��δ�������";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					tempStr = checkInFailStr + e.getMessage();
				}
				messageStr.append(tempStr);
				messageVec.add(messageStr);
			}
			progressDialog.stopProcess();
			dispose();
			MessageDialog mess = new MessageDialog(getParentFrame(), "������Ϣ",
					true, messageVec);
			mess.setVisible(true);
		}

	}

	/**
	 * ����ʹ�����dialog�Ľ����Frame��
	 * 
	 * @return JFrame
	 */
	public JFrame getParentFrame() {
		JFrame frame = null;
		if (getParent() instanceof JFrame) {
			frame = (JFrame) getParent();
		}
		return frame;
	}

	/**
	 * ��ȡ�����Ψһ��ʶ��
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	protected String getDisplayIdentity(Object obj) {
		String s = "";
		if (obj instanceof BaseValueIfc) {
			String displayidentity = ((BaseValueInfo) obj).getIdentity();
			s = displayidentity;
		} else
			s = obj.toString();
		return s;
	}

}