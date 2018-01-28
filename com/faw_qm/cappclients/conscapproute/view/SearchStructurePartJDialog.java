/**
 * CR1 ���� ԭ��μ�TD5938
 * SS1 �����乤��·���������ʱ��ʾ���������Ϣ pante 2013-11-02
 * SS2 �����乤��·���ظ����ͬһ�����ͬ�汾 pante 2013-11-16
 * SS3 ������SS2�н�����㲿�������Ѹĳɴ���QMPartIfc�����Ի�ȡ�ṹ��ҲҪ��֮������ liunan 2014-3-17
 * SS4 ���·��ֻ���������������״̬Ϊ�ѷ���������������׼������� pante 2014-09-28
 * SS5 ����������������������������״̬���޸�ʱ�����ʾӢ��  ���� 2015-3-10
 * SS6 �����ָ����� liunan 2015-4-29
 * SS7 ƽ̨���⣺A004-2015-3124��A004-2015-3126�����Ҫ���ܹ���ӱ�����ķ����������ڣ����������������ơ�����������׼���������������� liunan 2015-5-15
 * SS8 ƽ̨���⣺A004-2015-3135��״̬��Ϊ�������ʽά��������ӵ���������״̬�� liunan 2015-6-10
 */
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
//CCBegin SS5
import java.sql.Timestamp;
//CCEnd SS5
import java.util.HashMap;
import java.util.Vector;
//CCBegin SS8
import java.util.Collection;
import java.util.Iterator;
//CCEnd SS8
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
//CCBegin SS4
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS4
/**
 * <p>
 * Title:��������������
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
 * @author ����
 * @version 1.0
 * CR1 2012/03/27 ���� ԭ�� �μ���TD5942
 */
public class SearchStructurePartJDialog extends JDialog {

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
	private JLabel makerouteLabel = new JLabel();
	private JTextField makerouteJTextField = new JTextField();
	private JCheckBox makerouteJCheckBox = new JCheckBox();
	private JLabel assemblerouteLabel = new JLabel();
	private JTextField assemblerouteJTextField = new JTextField();
	private JCheckBox assemblerouteJCheckBox = new JCheckBox();
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
	private JCheckBox structureCheckBox = new JCheckBox();
	private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
	/** ���������� */
	private SearchThread searchThread;
	/** �߳��� */
	private static ThreadGroup theThreadGroup = Thread.currentThread()
			.getThreadGroup();
	/** ��ֹ���̱�־ */
	protected boolean cancelInProgress = false;
	private HashMap resultVector = new HashMap();

	/**
	 * ���캯��
	 * 
	 * @param frame
	 *            ������
	 */
	public SearchStructurePartJDialog(JFrame frame) {
		super(frame);
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
		// CCBegin by liuzhicheng 2011-08-30 ԭ�򣺲μ� TD4990
		this.setTitle("�����㲿��");
		// CCEnd by liuzhicheng 2011-08-30
		this.setSize(650, 500);
		panel1.setLayout(gridBagLayout3);
		panel1.setBounds(new Rectangle(0, 0, 650, 500));
		numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		numLabel.setText("���");
		jPanel1.setLayout(gridBagLayout1);
		numJCheckBox.setMaximumSize(new Dimension(37, 22));
		numJCheckBox.setMinimumSize(new Dimension(37, 22));
		numJCheckBox.setPreferredSize(new Dimension(37, 22));
		numJCheckBox.setText("��");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setText("����");
		nameJCheckBox.setMaximumSize(new Dimension(37, 22));
		nameJCheckBox.setMinimumSize(new Dimension(37, 22));
		nameJCheckBox.setPreferredSize(new Dimension(37, 22));
		nameJCheckBox.setText("��");

		assemblerouteLabel.setMaximumSize(new Dimension(75, 22));
		assemblerouteLabel.setMinimumSize(new Dimension(75, 22));
		assemblerouteLabel.setPreferredSize(new Dimension(75, 22));
		assemblerouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		assemblerouteLabel.setText("װ��·�߰���");

		ifLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ifLabel.setText("����");
		ifjcombobox.setMaximumSize(new Dimension(37, 22));
		ifjcombobox.setMinimumSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setPreferredSize(new Dimension(37, 22));
		ifjcombobox.addItem("��");
		ifjcombobox.addItem("��");

		assemblerouteJCheckBox.setMaximumSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setMinimumSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setPreferredSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setText("��");
		makerouteLabel.setMaximumSize(new Dimension(75, 22));
		makerouteLabel.setMinimumSize(new Dimension(75, 22));
		makerouteLabel.setPreferredSize(new Dimension(75, 22));
		makerouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		makerouteLabel.setText("����·�߰���");
		makerouteJCheckBox.setMaximumSize(new Dimension(37, 22));
		makerouteJCheckBox.setMinimumSize(new Dimension(37, 22));
		makerouteJCheckBox.setPreferredSize(new Dimension(37, 22));
		makerouteJCheckBox.setText("��");
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
		//CR1 begin
		clearJButton
        .addActionListener(new SearchStructurePartJDialog_clearJButton_actionAdapter(
                this));
		//CR1 end
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
		structureCheckBox.setText("���ṹ���");
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
		jPanel3.add(structureCheckBox, new GridBagConstraints(1, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 50), 0, 0));
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
		jPanel1.add(makerouteLabel, new GridBagConstraints(0, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 10, 0, 0), 0, 0));
		jPanel1.add(ifLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		jPanel1.add(assemblerouteLabel, new GridBagConstraints(0, 4, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

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

		jPanel1.add(makerouteJTextField, new GridBagConstraints(1, 2, 1, 1,
				1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
		jPanel1.add(makerouteJCheckBox, new GridBagConstraints(2, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 10), 7, 0));

		jPanel1.add(ifjcombobox, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(3, 8, 3, 0), 0, 0));

		jPanel1.add(assemblerouteJTextField, new GridBagConstraints(1, 4, 1, 1,
				1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
		jPanel1.add(assemblerouteJCheckBox, new GridBagConstraints(2, 4, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 10), 7, 0));

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
		//CCBegin SS1
		String[] headings = { "id", "���", "����", "��ͼ", "�汾", "����Դ�汾", "����", "��Դ", "��������", "������", "���ϼ�", "�޸�ʱ��" };
		//CCEnd SS1
		qMMultiList.setHeadings(headings);
		//CCBegin SS1
		//CCBegin SS5
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
		info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
		info1.setMethodName("getUserFromCompany");
		Class[] classes = {};
		info1.setParaClasses(classes);
		Object[] objs = {};
		info1.setParaValues(objs);
		String comp = null;
		try {
			comp = (String)server.request(info1);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		if(comp.equals("ct")){
			qMMultiList.setRelColWidth(new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1 });
		}else{
			qMMultiList.setRelColWidth(new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
		}

		//CCEnd SS5
		//CCEnd SS1
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
		private SearchStructurePartJDialog adaptee;

		SearchStructurePartJDialog_searchJButton_actionAdapter(
				SearchStructurePartJDialog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.searchJButton_actionPerformed(e);
		}
	}

	class SearchStructurePartJDialog_okJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private SearchStructurePartJDialog adaptee;

		SearchStructurePartJDialog_okJButton_actionAdapter(
				SearchStructurePartJDialog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.okJButton_actionPerformed(e);
		}
	}
	//CR1 begin	
class SearchStructurePartJDialog_clearJButton_actionAdapter implements
       java.awt.event.ActionListener {
   private SearchStructurePartJDialog adaptee;

   SearchStructurePartJDialog_clearJButton_actionAdapter(
           SearchStructurePartJDialog adaptee) {
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
    makerouteJTextField.setText("");
    assemblerouteJTextField.setText("");
    numJCheckBox.setSelected(false);
    nameJCheckBox.setSelected(false);
    makerouteJCheckBox.setSelected(false);
    assemblerouteJCheckBox.setSelected(false);
    appendResultJCheckBox.setSelected(false);
    structureCheckBox.setSelected(false);
    exactJCheckBox.setSelected(false);
    setStatus(" ");
}
//CR1 end
	class SearchStructurePartJDialog_cancelJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private SearchStructurePartJDialog adaptee;

		SearchStructurePartJDialog_cancelJButton_actionAdapter(
				SearchStructurePartJDialog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.cancelJButton_actionPerformed(e);
		}
	}

	class SearchStructurePartJDialog_stopJButton_actionAdapter implements
			java.awt.event.ActionListener {
		private SearchStructurePartJDialog adaptee;

		SearchStructurePartJDialog_stopJButton_actionAdapter(
				SearchStructurePartJDialog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.stopJButton_actionPerformed(e);
		}
	}

	class SearchStructurePartJDialog_qMMultiList_actionAdapter implements
			java.awt.event.ActionListener {
		private SearchStructurePartJDialog adaptee;

		SearchStructurePartJDialog_qMMultiList_actionAdapter(
				SearchStructurePartJDialog adaptee) {
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
		// ���������߳�
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
	 * ���ȷ����ť�󣬽�ѡ�е�֪ͨ��������㲿����ӵ��б���
	 */
	private void processOKCommond() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.dispose();
		// ���ѡ��������㲿��
		//CCBegin SS2
//		QMPartMasterInfo[] objs = (QMPartMasterInfo[]) this
//				.getSelectedDetails();
		QMPartInfo[] objs = (QMPartInfo[]) this
				.getSelectedDetails1();
		//CCEnd SS2
		// ����QMPartMasterIfc[]
		for (int i = 0; i < objs.length; i++) {
			//CCBegin SS2
//			QMPartMasterInfo masterinfo = (QMPartMasterInfo) objs[i];
			QMPartInfo partinfo = (QMPartInfo) objs[i];
			//CCEnd SS2
			Vector v = new Vector();
			
			//CCBegin SS2
//			v.add(masterinfo);
			v.add(partinfo);
			//CCEnd SS2
			//�ж��Ƿ񰴽ṹ���
			//CR1 begin
			if(structureCheckBox.isSelected())
			{
				 Class[] paraClass = {
				          Vector.class};
				      Object[] paraObj = {
				          v};
				      Object[] childparts;
					try {
						childparts = (Object[]) RequestHelper.request(
						      "consTechnicsRouteService", "getAllPartAndSubPartByCurConfigSpec", paraClass, paraObj);
					
				      if(childparts!=null)
				      {
				          StringBuffer aa = new StringBuffer();
				    	  Vector partVec=(Vector)childparts[1];
				    	  for(int j=0;j<partVec.size();j++)
				    	  {
				    		  QMPartInfo part= (QMPartInfo) partVec.get(j);
				    		  //CCBegin SS3
				    		  //v.add(part.getMaster());
				    		  v.add(part);
				    		  //CCEnd SS3
				    	  }
				    	  Vector masterPartVec=(Vector)childparts[0];
				    	  for(int k=0;k<masterPartVec.size();k++)
				    	  {
				    	      QMPartMasterInfo master = (QMPartMasterInfo) masterPartVec.get(k);
				    	      aa.append("�㲿����"+master.getPartNumber()+"  ���������ù淶"+"\n");
				    	      
				    	  }
				    	   if(aa.length() > 0)
				           {
				    	       ConfigMessageJDialog dia = new ConfigMessageJDialog(this);
				               dia.setTextArea(aa.toString());
				               dia.setVisible(true);
				           }
				      }
					} catch (QMRemoteException e) {
						e.printStackTrace();
					}
				    //CR1 end
			}
			RefreshService.getRefreshService().dispatchRefresh(
					"addSelectedParts", 0, v);
		}

		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ��ý����ѡ�е�ҵ�����
	 * 
	 * @return ѡ�е�ҵ�����
	 */
	public QMPartMasterInfo[] getSelectedDetails() {
		int[] rows = qMMultiList.getSelectedRows();
		QMPartMasterInfo[] values = new QMPartMasterInfo[rows.length];
		for (int i = 0; i < rows.length; i++) {
			int xx = rows[i];
			String bsoid = qMMultiList.getCellText(xx, 0);
			System.out.println("bsoid=============="+bsoid);
			values[i] = (QMPartMasterInfo) resultVector.get(bsoid);
		}
		return values;
	}
	
	/**
	 * ��ý����ѡ�е�ҵ�����
	 * 
	 * @return ѡ�е�ҵ�����
	 */
	public QMPartInfo[] getSelectedDetails1() {
		int[] rows = qMMultiList.getSelectedRows();
		QMPartInfo[] values = new QMPartInfo[rows.length];
		for (int i = 0; i < rows.length; i++) {
			int xx = rows[i];
			String bsoid = qMMultiList.getCellText(xx, 0);
			System.out.println("bsoid=============="+bsoid);
			values[i] = (QMPartInfo) resultVector.get(bsoid);
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
		private SearchStructurePartJDialog myDialog;

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
				SearchStructurePartJDialog dialog, boolean flag) {
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
			//CCBegin SS2
//			QMPartMasterInfo qmpart = (QMPartMasterInfo) objs.elementAt(i);
			QMPartInfo qmpart = (QMPartInfo) objs.elementAt(i);
			//CCEnd SS2
			Image image = this.getStandardIcon(qmpart);
			qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
			qMMultiList.addCell(i, 1, qmpart.getPartNumber(), image);
			qMMultiList.addTextCell(i, 2, qmpart.getPartName());
			//CCBegin SS1
//			qMMultiList.addTextCell(i, 2, qmpart.getBsoName()-----);
//			PartConfigSpecIfc configSpecIfc = null;
//			Class[] paraClass = { QMPartMasterIfc.class,PartConfigSpecIfc.class };
//			Object[] paraObj = { qmpart,configSpecIfc };
//			QMPartIfc part = null;
//			try {
//				part = (QMPartIfc) RequestHelper.request(
//						"consTechnicsRouteService", "getLastedPartByConfig", paraClass, paraObj);
//			} catch (QMRemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			qMMultiList.addTextCell(i, 3, part.getViewName());
//			qMMultiList.addTextCell(i, 4, part.getVersionValue());
//			Class[] paraClass1 = { QMPartIfc.class };
//			Object[] paraObj1 = { part };
//			String version = (String) RequestHelper.request(
//					"consTechnicsRouteService", "getIBA", paraClass1, paraObj1);
//			qMMultiList.addTextCell(i, 5, version);
//			qMMultiList.addTextCell(i, 6, part.getPartType().getDisplay());
//			qMMultiList.addTextCell(i, 7, part.getProducedBy().getDisplay());
//			qMMultiList.addTextCell(i, 8, part.getLifeCycleTemplate());
//			qMMultiList.addTextCell(i, 9, part.getOwner());
//			qMMultiList.addTextCell(i, 10, part.getLocation());
//			qMMultiList.addTextCell(i, 11, part.getModifyTime().toGMTString());
			qMMultiList.addTextCell(i, 3, qmpart.getViewName());
			qMMultiList.addTextCell(i, 4, qmpart.getVersionValue());
			
			Class[] paraClass1 = { QMPartIfc.class };
			Object[] paraObj1 = { qmpart };
			String version = (String) RequestHelper.request(
					"consTechnicsRouteService", "getIBA", paraClass1, paraObj1);
			
			qMMultiList.addTextCell(i, 5, version);
			qMMultiList.addTextCell(i, 6, qmpart.getPartType().getDisplay());
			qMMultiList.addTextCell(i, 7, qmpart.getProducedBy().getDisplay());
			//CCBegin SS5
			qMMultiList.addTextCell(i, 8, qmpart.getLifeCycleState().getDisplay());
			qMMultiList.addTextCell(i, 9, qmpart.getOwner());
			qMMultiList.addTextCell(i, 10, qmpart.getLocation());
			qMMultiList.addTextCell(i, 11, ((Timestamp)qmpart.getModifyTime()).toString());
			//CCEnd SS5
			//CCEnd SS1
			
		}
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ���ͼ��
	 * 
	 * @param basevalueinfo
	 * @return
	 */
	public Image getStandardIcon(BaseValueIfc basevalueinfo) {
		Image image = null;
		if (basevalueinfo != null) {
			try {
				image = QM.getStandardIcon(basevalueinfo.getBsoName());
			} catch (Exception _ex) {
				image = null;
			}
		}
		return image;
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
		Object[] condition = getCondition();
		try {
			// ���÷��񷽷���ѯ����
			Class[] paraClass = { Object[][].class };
			Object[] paraObj = { condition };
			Vector queryresult = (Vector) RequestHelper.request(
					"consTechnicsRouteService", "findQMPart", paraClass, paraObj);
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
	 * ����û�¼�����������
	 * 
	 * @return ��������
	 */
	private Object[][] getCondition() {
		// ֪ͨ�����Ͳ��û�ȡ ֱ���ж�
		Object[][] objs = new Object[5][2];
		objs[0][0] = numJTextField.getText().trim();
		objs[0][1] = new Boolean(!numJCheckBox.isSelected());
		objs[1][0] = nameJTextField.getText().trim();
		objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
		objs[2][0] = makerouteJTextField.getText().trim();
		objs[2][1] = new Boolean(!makerouteJCheckBox.isSelected());
		objs[3][0] = ifjcombobox.getSelectedIndex();
		objs[4][0] = assemblerouteJTextField.getText().trim();
		objs[4][1] = new Boolean(!assemblerouteJCheckBox.isSelected());
		return objs;
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
			setStatus("�ҵ� 0 ���㲿��");
			return;
		}
		// ����б��е�·�߱�����

		int oldSize = resultVector.size();
		for (int i = 0; i < searchResult.size(); i++) {
			if (isCancelInProgress()) {
				break;
			}

			BaseValueInfo orderlist = null;
			//CCBegin SS2
			//			orderlist = (QMPartMasterInfo) searchResult.elementAt(i);
			//			CCBegin SS4
			QMPartInfo part = (QMPartInfo) searchResult.elementAt(i);
			RequestServer server = RequestServerFactory.getRequestServer();
			StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
			info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
			info1.setMethodName("getUserFromCompany");
			Class[] classes = {};
			info1.setParaClasses(classes);
			Object[] objs = {};
			info1.setParaValues(objs);
			String comp = null;
			try {
				comp = (String)server.request(info1);
			} catch (QMRemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(comp.equals("zczx")){
				//CCBegin SS8
        Class[] params = {String.class, String.class};
        Object[] values = {"������ķ���״̬", "������Ĵ�������"};
        Collection result = null;
        try
        {
        	result = (Collection)RequestHelper.request("CodingManageService", "findDirectClassificationByName", params, values);
        	//System.out.println("result ============="+result);
        }
        catch(Exception ex)
        {
        	String message = ex.getMessage();
        	DialogFactory.showErrorDialog(this, message);
        	return;
        }
        boolean stateflag = false;
        if(result != null && result.size() > 0)
        {
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
            	String state = (iterator.next()).toString();
            	if(state.equals(part.getLifeCycleState().getDisplay().toString()))
            	{
            		stateflag = true;
            		break;
            	}
            }
        }
        if(stateflag)
        {
        	orderlist = (QMPartInfo) searchResult.elementAt(i);
        }
        /*
				if(part.getLifeCycleState().toString().equals("RELEASED")
						||part.getLifeCycleState().toString().equals("SHIZHI")
						||part.getLifeCycleState().toString().equals("PREPARING")
						//CCBegin SS7
						||part.getLifeCycleState().toString().equals("BSXTrialProduce")
						||part.getLifeCycleState().toString().equals("BSXTrialYield")
						||part.getLifeCycleState().toString().equals("BSXYield")
						//CCEnd SS7
						||part.getLifeCycleState().toString().equals("MANUFACTURING")){
					orderlist = (QMPartInfo) searchResult.elementAt(i);
				}
				*/
				//CCEnd SS8
			}
			else
				//CCEnd SS4
				orderlist = (QMPartInfo) searchResult.elementAt(i);
			//			System.out.println("searchResult=============="+searchResult.elementAt(i));
			//CCEnd SS2
			//CCBegin SS6
			if(orderlist==null)
			{
				continue;
			}
			//CCEnd SS6
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
			setStatus("�ҵ� " + resultVector.size() + " ���㲿��");
		} else {
			setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " ���㲿��");
		}

	}
}
