/**
 * CR1 吕航 原因参见TD5938
 * SS1 变速箱工艺路线搜索零件时显示零件多种信息 pante 2013-11-02
 * SS2 变速箱工艺路线重复添加同一零件不同版本 pante 2013-11-16
 * SS3 由于在SS2中将添加零部件，都已改成传递QMPartIfc，所以获取结构处也要随之调整。 liunan 2014-3-17
 * SS4 轴齿路线只允许添加生命周期状态为已发布生产试制生产准备的零件 pante 2014-09-28
 * SS5 长特搜索零件，搜索结果生命周期状态、修改时间等显示英文  文柳 2015-3-10
 * SS6 避免空指针错误。 liunan 2015-4-29
 * SS7 平台问题：A004-2015-3124、A004-2015-3126。轴齿要求能够添加变速箱的发布生命周期，包括“变速箱试制、变速箱生产准备、变速箱生产” liunan 2015-5-15
 * SS8 平台问题：A004-2015-3135。状态改为代码管理方式维护可以添加的生命周期状态。 liunan 2015-6-10
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
 * Title:添加零件搜索界面
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * 
 * @author 吕航
 * @version 1.0
 * CR1 2012/03/27 吕航 原因 参见：TD5942
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
	/** 检索控制类 */
	private SearchThread searchThread;
	/** 线程组 */
	private static ThreadGroup theThreadGroup = Thread.currentThread()
			.getThreadGroup();
	/** 中止进程标志 */
	protected boolean cancelInProgress = false;
	private HashMap resultVector = new HashMap();

	/**
	 * 构造函数
	 * 
	 * @param frame
	 *            父窗口
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
	 * 组件初始化
	 * 
	 * @throws Exception
	 */
	private void jbInit()
	{
		// CCBegin by liuzhicheng 2011-08-30 原因：参见 TD4990
		this.setTitle("搜索零部件");
		// CCEnd by liuzhicheng 2011-08-30
		this.setSize(650, 500);
		panel1.setLayout(gridBagLayout3);
		panel1.setBounds(new Rectangle(0, 0, 650, 500));
		numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		numLabel.setText("编号");
		jPanel1.setLayout(gridBagLayout1);
		numJCheckBox.setMaximumSize(new Dimension(37, 22));
		numJCheckBox.setMinimumSize(new Dimension(37, 22));
		numJCheckBox.setPreferredSize(new Dimension(37, 22));
		numJCheckBox.setText("非");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setText("名称");
		nameJCheckBox.setMaximumSize(new Dimension(37, 22));
		nameJCheckBox.setMinimumSize(new Dimension(37, 22));
		nameJCheckBox.setPreferredSize(new Dimension(37, 22));
		nameJCheckBox.setText("非");

		assemblerouteLabel.setMaximumSize(new Dimension(75, 22));
		assemblerouteLabel.setMinimumSize(new Dimension(75, 22));
		assemblerouteLabel.setPreferredSize(new Dimension(75, 22));
		assemblerouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		assemblerouteLabel.setText("装配路线包含");

		ifLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ifLabel.setText("条件");
		ifjcombobox.setMaximumSize(new Dimension(37, 22));
		ifjcombobox.setMinimumSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setPreferredSize(new Dimension(37, 22));
		ifjcombobox.addItem("与");
		ifjcombobox.addItem("或");

		assemblerouteJCheckBox.setMaximumSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setMinimumSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setPreferredSize(new Dimension(37, 22));
		assemblerouteJCheckBox.setText("非");
		makerouteLabel.setMaximumSize(new Dimension(75, 22));
		makerouteLabel.setMinimumSize(new Dimension(75, 22));
		makerouteLabel.setPreferredSize(new Dimension(75, 22));
		makerouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		makerouteLabel.setText("制造路线包含");
		makerouteJCheckBox.setMaximumSize(new Dimension(37, 22));
		makerouteJCheckBox.setMinimumSize(new Dimension(37, 22));
		makerouteJCheckBox.setPreferredSize(new Dimension(37, 22));
		makerouteJCheckBox.setText("非");
		jPanel2.setMaximumSize(new Dimension(70, 70));
		jPanel2.setMinimumSize(new Dimension(70, 70));
		jPanel2.setPreferredSize(new Dimension(70, 70));
		jPanel2.setLayout(gridBagLayout4);
		searchJButton.setMaximumSize(new Dimension(75, 23));
		searchJButton.setMinimumSize(new Dimension(75, 23));
		searchJButton.setPreferredSize(new Dimension(75, 23));
		searchJButton.setToolTipText("Search");
		searchJButton.setMnemonic('F');
		searchJButton.setText("搜索(F)");
		searchJButton
				.addActionListener(new SearchStructurePartJDialog_searchJButton_actionAdapter(
						this));
		stopJButton.setMaximumSize(new Dimension(75, 23));
		stopJButton.setMinimumSize(new Dimension(75, 23));
		stopJButton.setPreferredSize(new Dimension(75, 23));
		stopJButton.setToolTipText("Stop");
		stopJButton.setMnemonic('S');
		stopJButton.setText("停止(S)");
		stopJButton
				.addActionListener(new SearchStructurePartJDialog_stopJButton_actionAdapter(
						this));

		clearJButton.setMaximumSize(new Dimension(75, 23));
		clearJButton.setMinimumSize(new Dimension(75, 23));
		clearJButton.setPreferredSize(new Dimension(75, 23));
		clearJButton.setToolTipText("Clear");
		clearJButton.setMnemonic('L');
		clearJButton.setText("清除(L)");
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
		okJButton.setText("确定(Y)");
		okJButton.setEnabled(false);
		okJButton
				.addActionListener(new SearchStructurePartJDialog_okJButton_actionAdapter(
						this));
		cancelJButton.setMaximumSize(new Dimension(75, 23));
		cancelJButton.setMinimumSize(new Dimension(75, 23));
		cancelJButton.setPreferredSize(new Dimension(75, 23));
		cancelJButton.setToolTipText("Cancel");
		cancelJButton.setMnemonic('C');
		cancelJButton.setText("取消(C)");
		cancelJButton
				.addActionListener(new SearchStructurePartJDialog_cancelJButton_actionAdapter(
						this));
		qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
		qMStatus.setMaximumSize(new Dimension(4, 22));
		qMStatus.setMinimumSize(new Dimension(4, 22));
		qMStatus.setPreferredSize(new Dimension(4, 22));
		appendResultJCheckBox.setText("附加结果");
		structureCheckBox.setText("按结构添加");
		exactJCheckBox.setText("精确查找");
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
		jTabbedPane1.add(jPanel1, "搜索条件");
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
	 * 本地化
	 */
	private void localize() {
		qMMultiList.setCellEditable(false);
		qMMultiList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setOkButtonEnabled();
			}
		});
		//CCBegin SS1
		String[] headings = { "id", "编号", "名称", "视图", "版本", "发布源版本", "类型", "来源", "生命周期", "所有者", "资料夹", "修改时间" };
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
	 * 设置确定按钮的状态
	 */
	private void setOkButtonEnabled() {
		if (qMMultiList.getSelectedRows() == null) {
			okJButton.setEnabled(false);
		} else {
			okJButton.setEnabled(true);
		}
	}

	/**
	 * 设置界面的显示位置
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
 * 执行“清除“操作
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
	 * 执行“取消“操作
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void cancelJButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * 执行“中止”操作
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void stopJButton_actionPerformed(ActionEvent e) {
		stopJButton.setEnabled(false);
		cancel();
	}

	/**
	 * 中止检索进程
	 */
	private synchronized void cancel() {
		if (searchThread != null && searchThread.isAlive()) {
			setCancelInProgress(true);
			// searchThread.interrupt(); //将这句去掉，是因为它使界面退出了，这不合业务
			searchThread = null;
		}
	}

	/**
	 * 执行搜索操作
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void searchJButton_actionPerformed(ActionEvent e) {
		setButtons(false);
		// 启动搜索线程
		searchThread = new SearchThread(theThreadGroup, this,
				appendResultJCheckBox.isSelected());
		searchThread.start();

	}

	/**
	 * 执行“确定”操作
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void okJButton_actionPerformed(ActionEvent e) {
		processOKCommond();
	}

	/**
	 * 点击确定按钮后，将选中的通知书关联的零部件添加到列表中
	 */
	private void processOKCommond() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.dispose();
		// 获得选择的所有零部件
		//CCBegin SS2
//		QMPartMasterInfo[] objs = (QMPartMasterInfo[]) this
//				.getSelectedDetails();
		QMPartInfo[] objs = (QMPartInfo[]) this
				.getSelectedDetails1();
		//CCEnd SS2
		// 返回QMPartMasterIfc[]
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
			//判断是否按结构添加
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
				    	      aa.append("零部件："+master.getPartNumber()+"  不符合配置规范"+"\n");
				    	      
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
	 * 获得结果域选中的业务对象
	 * 
	 * @return 选中的业务对象
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
	 * 获得结果域选中的业务对象
	 * 
	 * @return 选中的业务对象
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
	 * 设置按钮的编辑状态，仅供内部使用
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
	 * Title: 内部工作线程
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2007
	 * </p>
	 * <p>
	 * Company: 一汽启明
	 * </p>
	 * 
	 * @author
	 * @version 1.0
	 */
	class SearchThread extends QMThread {
		/** 判断是否保留原来的搜索结果 */
		private boolean isLeave;

		/** 搜索界面对象 */
		private SearchStructurePartJDialog myDialog;

		/**
		 * 构造查询实例
		 * 
		 * @param threadgroup
		 *            线程组
		 * @param dialog
		 *            按工装搜索界面对象
		 * @param flag
		 *            判断是否保留原来的搜索结果
		 */
		public SearchThread(ThreadGroup threadgroup,
				SearchStructurePartJDialog dialog, boolean flag) {
			super(threadgroup);
			myDialog = dialog;
			isLeave = flag;
		}

		/**
		 * 使线程中断，停止搜索进程
		 */
		public synchronized void interrupt() {

			this.interrupt();
		}

		/**
		 * 线程运行方法，执行搜索。
		 */
		public synchronized void run() {
			try {
				// 执行搜索
				myDialog.processSearchThread(isLeave);
			} catch (Throwable throwable) {
				if (!myDialog.isCancelInProgress()) {
					throwable.printStackTrace();
				}
			} finally {

				// 设置界面按钮的显示状态
				myDialog.setButtons(true);
				// 设置没有处于中断状态
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
	 * 将结果添加到列表中
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
		// 将搜索结果加入结果列表中
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
	 * 获得图标
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
	 * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
	 * 
	 * @param flag
	 *            若flag=true，则保留原结果，否则清除上次查询结果
	 */
	private void processSearchThread(boolean flag) {

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// 是否清除原来结果
		clearResults(!flag);

		if (!flag) {
			paginatePanel.clearResultCache();
			resultVector.clear();
		}
		this.setStatus("正在搜索数据库...");
		// 获得查询条件
		Object[] condition = getCondition();
		try {
			// 调用服务方法查询数据
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
	 * 判断是否中止检索线程
	 * 
	 * @return 如果返回True，则中止线程。
	 */
	private boolean isCancelInProgress() {
		return cancelInProgress;
	}

	/**
	 * 设置是否中止线程
	 * 
	 * @param flag
	 *            如果flag=True，则中止线程。
	 */
	private synchronized void setCancelInProgress(boolean flag) {
		cancelInProgress = flag;
	}

	/**
	 * 清空结果域
	 * 
	 * @param flag
	 *            如果flag = true,则清空，否则不清
	 */
	private synchronized void clearResults(boolean flag) {
		if (flag) {
			qMMultiList.clear();
			okJButton.setEnabled(false);
		}
		setStatus(" ");
	}

	/**
	 * 设置状态条信息
	 * 
	 * @param text
	 *            状态条要显示的文本
	 */
	private void setStatus(String text) {
		qMStatus.setText(text);
	}

	/**
	 * 获得用户录入的搜索条件
	 * 
	 * @return 搜索条件
	 */
	private Object[][] getCondition() {
		// 通知书类型不用获取 直接判断
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
	 * 结果集后处理,将搜索结果加入结果列表中
	 * 
	 * @param searchResult
	 *            搜索结果(工艺路线表值对象)
	 * @throws QMRemoteException
	 */
	private void postHandleResult(Vector searchResult) {
		if (searchResult == null || searchResult.size() == 0) {
			setStatus("找到 0 个零部件");
			return;
		}
		// 结果列表中的路线表总数

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
        Object[] values = {"轴齿中心发布状态", "轴齿中心代码配置"};
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
			// 判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
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
			setStatus("找到 " + resultVector.size() + " 个零部件");
		} else {
			setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个零部件");
		}

	}
}
