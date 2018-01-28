
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.SupplierInfo;

/**
 * <p>
 * Title:��ӹ�Ӧ�̽���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * 
 */
public class searthSupplierJDailog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel panel1 = new JPanel();
	private JTabbedPane jTabbedPane1 = new JTabbedPane();
	private JPanel jPanel1 = new JPanel();
	private JLabel numLabel = new JLabel();
	private JTextField numJTextField = new JTextField();
	private JCheckBox numJCheckBox = new JCheckBox();
	private JLabel nameLabel = new JLabel();
	private JTextField nameJTextField = new JTextField();
	private JCheckBox nameJCheckBox = new JCheckBox();
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private JPanel jPanel2 = new JPanel();
	private JButton searchJButton = new JButton();
	private JButton stopJButton = new JButton();
	private JButton clearJButton = new JButton();
	private QMMultiList qMMultiList = new QMMultiList();
	private JPanel jPanel3 = new JPanel();
	private JButton okJButton = new JButton();
	private JButton cancelJButton = new JButton();
	private GridBagLayout gridBagLayout2 = new GridBagLayout();
	private JLabel qMStatus = new JLabel();
	private GridBagLayout gridBagLayout3 = new GridBagLayout();
	private GridBagLayout gridBagLayout4 = new GridBagLayout();
	private JCheckBox appendResultJCheckBox = new JCheckBox();
	private JCheckBox exactJCheckBox = new JCheckBox();
	private JLabel ifLabel = new JLabel();
	private JComboBox ifjcombobox = new JComboBox();
	private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
	/** ���������� */
	private SearchThread searchThread;
	/** �߳��� */
	private static ThreadGroup theThreadGroup = Thread.currentThread()
			.getThreadGroup();
	/** ��ֹ���̱�־ */
	protected boolean cancelInProgress = false;
	private HashMap resultVector = new HashMap();
    private JFrame parentFrame = null;
    private  EditSupplierJDialog parentDialog= null;
	/**
	 * ���캯��
	 * 
	 * @param frame
	 *            ������
	 */
	public searthSupplierJDailog(JFrame frame,EditSupplierJDialog dialog) {
		//super(frame);
		this.parentFrame = frame;
		this.parentDialog = (EditSupplierJDialog)dialog;
		try {
			jbInit();
			setViewLocation();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �����ʼ��
	 * 
	 * @throws Exception
	 */
	private void jbInit()
	{
		this.setTitle("������Ӧ��");
		this.setSize(650, 500);
		panel1.setLayout(gridBagLayout3);
		panel1.setBounds(new Rectangle(0, 0, 650, 500));
		numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		numLabel.setText("��Ӧ�̴���");
		jPanel1.setLayout(gridBagLayout1);
		numJCheckBox.setMaximumSize(new Dimension(37, 22));
		numJCheckBox.setMinimumSize(new Dimension(37, 22));
		numJCheckBox.setPreferredSize(new Dimension(37, 22));
		numJCheckBox.setText("��");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setText("��Ӧ������");
		nameJCheckBox.setMaximumSize(new Dimension(37, 22));
		nameJCheckBox.setMinimumSize(new Dimension(37, 22));
		nameJCheckBox.setPreferredSize(new Dimension(37, 22));
		nameJCheckBox.setText("��");

		jPanel2.setMaximumSize(new Dimension(70, 70));
		jPanel2.setMinimumSize(new Dimension(70, 70));
		jPanel2.setPreferredSize(new Dimension(70, 70));
		jPanel2.setLayout(gridBagLayout4);
		searchJButton.setMaximumSize(new Dimension(75, 23));
		searchJButton.setMinimumSize(new Dimension(75, 23));
		searchJButton.setPreferredSize(new Dimension(75, 23));
		searchJButton.setToolTipText("Search"); 
		searchJButton.setMnemonic('F');
		searchJButton.setText("����(F)");
		searchJButton
				.addActionListener(new SearchStructurePartJDialog_searchJButton_actionAdapter(
						this));
		stopJButton.setMaximumSize(new Dimension(75, 23));
		stopJButton.setMinimumSize(new Dimension(75, 23));
		stopJButton.setPreferredSize(new Dimension(75, 23));
		stopJButton.setToolTipText("Stop");
		stopJButton.setMnemonic('S');
		stopJButton.setText("ֹͣ(S)");
		stopJButton
				.addActionListener(new SearchStructurePartJDialog_stopJButton_actionAdapter(
						this));

		clearJButton.setMaximumSize(new Dimension(75, 23));
		clearJButton.setMinimumSize(new Dimension(75, 23));
		clearJButton.setPreferredSize(new Dimension(75, 23));
		clearJButton.setToolTipText("Clear");
		clearJButton.setMnemonic('L');
		clearJButton.setText("���(L)");
		clearJButton
        .addActionListener(new SearchStructurePartJDialog_clearJButton_actionAdapter(
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
				.addActionListener(new SearchStructurePartJDialog_okJButton_actionAdapter(
						this));
		cancelJButton.setMaximumSize(new Dimension(75, 23));
		cancelJButton.setMinimumSize(new Dimension(75, 23));
		cancelJButton.setPreferredSize(new Dimension(75, 23));
		cancelJButton.setToolTipText("Cancel");
		cancelJButton.setMnemonic('C');
		cancelJButton.setText("ȡ��(C)");
		cancelJButton
				.addActionListener(new SearchStructurePartJDialog_cancelJButton_actionAdapter(
						this));
		qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
		qMStatus.setMaximumSize(new Dimension(4, 22));
		qMStatus.setMinimumSize(new Dimension(4, 22));
		qMStatus.setPreferredSize(new Dimension(4, 22));
		appendResultJCheckBox.setText("���ӽ��");
		exactJCheckBox.setText("��ȷ����");
		jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(10, 0, 0, 0), 0, 0));
		jPanel2.add(clearJButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(10, 0, 0, 0), 0, 0));
		panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 10, 0, 10), 0, 0));
		panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 10, 10), 0, 0));
		jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						6, 0, 0), 0, 0));
		jPanel3.add(okJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		jPanel3.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						8, 0, 0), 0, 0));
		panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 10, 0, 0), 0, 0));
		panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(28, 5, 0, 10), 10, 18));
		panel1.add(appendResultJCheckBox, new GridBagConstraints(0, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 10, 0, 0), 0, 0));
		panel1.add(exactJCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						10, 0, 25), 0, 0));
		  panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
                  , GridBagConstraints.WEST,
                  GridBagConstraints.HORIZONTAL,
                  new Insets(0, 0, 0, 0), 0, 0));
		jTabbedPane1.add(jPanel1, "��������");
		getContentPane().add(panel1, null);
		jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 10, 0));
		jPanel1.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));

		jPanel1.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(6, 8, 3, 0), 0, 0));
		jPanel1.add(numJCheckBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						8, 0, 10), 7, 0));

		jPanel1.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(3, 8, 3, 0), 0, 0));
		jPanel1.add(nameJCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						8, 0, 10), 7, 0));


		localize();
   
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
		String[] headings = { "id", "��Ӧ�̴���", "��Ӧ������" };
		qMMultiList.setHeadings(headings);
		qMMultiList.setRelColWidth(new int[] { 0, 1, 1});
		qMMultiList.setMultipleMode(true);
		qMMultiList
				.addActionListener(new SearchStructurePartJDialog_qMMultiList_actionAdapter(
						this));
		qMMultiList.setEnabled(false);

	}

	/**
	 * ����ȷ����ť��״̬
	 */
	private void setOkButtonEnabled() {
		if (qMMultiList.getSelectedRows() == null) {
			okJButton.setEnabled(false);
		} else {
			okJButton.setEnabled(true);
		}
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
		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

	}

	void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
		try {
			this.addObjectToMultiList(paginatePanel.getCurrentObjects());
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	class SearchStructurePartJDialog_searchJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private searthSupplierJDailog adaptee;

		SearchStructurePartJDialog_searchJButton_actionAdapter(
				searthSupplierJDailog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.searchJButton_actionPerformed(e);
		}
	}

	class SearchStructurePartJDialog_okJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private searthSupplierJDailog adaptee;

		SearchStructurePartJDialog_okJButton_actionAdapter(
				searthSupplierJDailog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.okJButton_actionPerformed(e);
		}
	}
class SearchStructurePartJDialog_clearJButton_actionAdapter implements
       java.awt.event.ActionListener {
   private searthSupplierJDailog adaptee;

   SearchStructurePartJDialog_clearJButton_actionAdapter(
           searthSupplierJDailog adaptee) {
       this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
       adaptee.clearJButton_actionPerformed(e);
   }
}
/**
 * ִ�С����������
 * 
 * @param e
 *            ActionEvent
 */
void clearJButton_actionPerformed(ActionEvent e) {
    qMMultiList.clear();
    okJButton.setEnabled(false);
    numJTextField.setText("");
    nameJTextField.setText("");
    numJCheckBox.setSelected(false);
    nameJCheckBox.setSelected(false);
    appendResultJCheckBox.setSelected(false);
    exactJCheckBox.setSelected(false);
    setStatus(" ");
}

	class SearchStructurePartJDialog_cancelJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private searthSupplierJDailog adaptee;

		SearchStructurePartJDialog_cancelJButton_actionAdapter(
				searthSupplierJDailog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.cancelJButton_actionPerformed(e);
		}
	}

	class SearchStructurePartJDialog_stopJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private searthSupplierJDailog adaptee;

		SearchStructurePartJDialog_stopJButton_actionAdapter(
				searthSupplierJDailog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.stopJButton_actionPerformed(e);
		}
	}

	class SearchStructurePartJDialog_qMMultiList_actionAdapter implements
			java.awt.event.ActionListener {
		private searthSupplierJDailog adaptee;

		SearchStructurePartJDialog_qMMultiList_actionAdapter(
				searthSupplierJDailog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.qMMultiList_actionPerformed(e);
		}
	}

	void qMMultiList_actionPerformed(ActionEvent e) {
		processOKCommond();
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
	 * ��ֹ��������
	 */
	private synchronized void cancel() {
		if (searchThread != null && searchThread.isAlive()) {
			setCancelInProgress(true);
			// searchThread.interrupt(); //�����ȥ��������Ϊ��ʹ�����˳��ˣ��ⲻ��ҵ��
			searchThread = null;
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
		searchThread = new SearchThread(theThreadGroup, this,
				appendResultJCheckBox.isSelected());
		searchThread.start();

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
	 * ���ȷ����ť�󣬽�ѡ�еĹ�Ӧ����ӵ��б���
	 */
	private void processOKCommond() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.dispose();
		// ���ѡ������й�Ӧ��
		SupplierInfo[] objs = (SupplierInfo[]) this
				.getSelectedDetails();
		parentDialog.addData(objs);
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ��ý����ѡ�е�ҵ�����
	 * 
	 * @return ѡ�е�ҵ�����
	 */
	public SupplierInfo[] getSelectedDetails() {
		int[] rows = qMMultiList.getSelectedRows();
		SupplierInfo[] values = new SupplierInfo[rows.length];
		for (int i = 0; i < rows.length; i++) {
			int xx = rows[i];
			String bsoid = qMMultiList.getCellText(xx, 0);
			values[i] = (SupplierInfo) resultVector.get(bsoid);
		}
		return values;
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
	 * <p>
	 * Title: �ڲ������߳�
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2007
	 * </p>
	 * <p>
	 * Company: һ������
	 * </p>
	 * 
	 * @author
	 * @version 1.0
	 */
	class SearchThread extends QMThread {
		/** �ж��Ƿ���ԭ����������� */
		private boolean isLeave;

		/** ����������� */
		private searthSupplierJDailog myDialog;

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
				searthSupplierJDailog dialog, boolean flag) {
			super(threadgroup);
			myDialog = dialog;
			isLeave = flag;
		}

		/**
		 * ʹ�߳��жϣ�ֹͣ��������
		 */
		public synchronized void interrupt() {

			this.interrupt();
		}

		/**
		 * �߳����з�����ִ��������
		 */
		public synchronized void run() {
			try {
				// ִ������
				myDialog.processSearchThread(isLeave);
			} catch (Throwable throwable) {
				if (!myDialog.isCancelInProgress()) {
					throwable.printStackTrace();
				}
			} finally {

				// ���ý��水ť����ʾ״̬
				myDialog.setButtons(true);
				// ����û�д����ж�״̬
				myDialog.setCancelInProgress(false);
				myDialog.addResultToMultiList();
			}

		}
	}

	private synchronized void addResultToMultiList() {
		Vector newV = new Vector();
		newV.addAll(resultVector.values());
		Vector firstPageVector = paginatePanel.paginate(newV);
		try {
			addObjectToMultiList(firstPageVector);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �������ӵ��б���
	 * 
	 * @param objs
	 *            Vector
	 * @throws QMException 
	 */
	private void addObjectToMultiList(Vector objs) throws QMException {

		qMMultiList.clear();
		if (objs == null || objs.size() == 0) {
			return;
		}
		// ����������������б���
		int p = objs.size();
		for (int i = 0; i < p; i++) {
			SupplierInfo sup = (SupplierInfo) objs.elementAt(i);

			//Image image = this.getStandardIcon(sup);
			qMMultiList.addTextCell(i, 0, sup.getBsoID());
			qMMultiList.addTextCell(i, 1, sup.getCode());
			qMMultiList.addTextCell(i, 2,sup.getCodename());			
		}
		setCursor(Cursor.getDefaultCursor());
	}



	/**
	 * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
	 * 
	 * @param flag
	 *            ��flag=true������ԭ�������������ϴβ�ѯ���
	 */
	private void processSearchThread(boolean flag) {

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// �Ƿ����ԭ�����
		clearResults(!flag);

		if (!flag) {
			paginatePanel.clearResultCache();
			resultVector.clear();
		}
		this.setStatus("�����������ݿ�...");
		// ��ò�ѯ����
		
		String code = numJTextField.getText().trim();
		boolean codeFlag = new Boolean(!numJCheckBox.isSelected());
		String codename = nameJTextField.getText().trim();
		boolean nameFlage = new Boolean(!nameJCheckBox.isSelected());
		try {
			// ���÷��񷽷���ѯ����
			Class[] paraClass = { String.class,String.class,boolean.class,boolean.class };
			Object[] paraObj = { code,codename, codeFlag,nameFlage};
			Vector queryresult = (Vector) RequestHelper.request(
					"SupplierService", "findSupplier", paraClass, paraObj);
			this.postHandleResult(queryresult);
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			DialogFactory.showInformDialog(this, message);
			if (!isCancelInProgress()) {
				return;
			}
		}
		setCursor(Cursor.getDefaultCursor());
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
	 * ����״̬����Ϣ
	 * 
	 * @param text
	 *            ״̬��Ҫ��ʾ���ı�
	 */
	private void setStatus(String text) {
		qMStatus.setText(text);
	}



	/**
	 * ���������,����������������б���
	 * 
	 * @param searchResult
	 *            �������(����·�߱�ֵ����)
	 * @throws QMRemoteException
	 */
	private void postHandleResult(Vector searchResult) {

		if (searchResult == null || searchResult.size() == 0) {
			setStatus("�ҵ� 0 ����Ӧ��");
			return;
		}
		// ����б��е�·�߱�����

		int oldSize = resultVector.size();
		for (int i = 0; i < searchResult.size(); i++) {
			if (isCancelInProgress()) {
				break;
			}

			BaseValueInfo orderlist = null;

			orderlist = (SupplierInfo) searchResult.elementAt(i);
			// �ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý���������������б���
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
			setStatus("�ҵ� " + resultVector.size() + " ����Ӧ��");
		} else {
			setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " ����Ӧ��");
		}

	}
}
