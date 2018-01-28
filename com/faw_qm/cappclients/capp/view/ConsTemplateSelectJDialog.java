/** 生成程序TemplateSelectJDialog.java	1.1  2003/08/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 显示变速箱打印模板类型 liuyang 2013-3-22
 * SS2 变速箱控制计划多工艺打印入口 Liuyang 2014-2-18
 * SS3 默认打印预览控制计划 liunan 2014-8-20
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import java.awt.*;
import javax.swing.*;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.consprint.lightweightfile.util.LightweightFileTool;
import com.faw_qm.consprint.lightweightfile.util.LightweightFileToolForPrintCenter;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.cappclients.beans.processtreepanel.ProcessTreeObject;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;

//CCBegin SS2
import com.faw_qm.consprint.lightweightfile.util.DataDisposeTool;
import com.faw_qm.print.clients.printModel.PDocument;
//CCEnd SS2

/**
 * <p>
 * Title:模板选择对话框
 * </p>
 * <p>
 * 执行者选择打印预览时，弹出此界面选择模板
 * </p>
 * <p>
 * 在工艺流程中进行查看工艺时同样弹出该界面进行选择模板，然后再进行数据准备并预览。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *    
 * @author 刘明
 * CR1  2010/05/27  薛凯  参见 TD2266
 * @version 1.0
 */
// 20061205王浩修改，添加4个按钮（browsePdfJButton、createPdfJButton、browseDxfJButton、createDxfJButton），用于生成和浏览Dxf和Pdf文件
// modify by wangh on 20061226
// 用createDxfJButton按钮代替确定按钮，但是功能不改变。从界面上去掉了browseDxfJButton按钮。
public class ConsTemplateSelectJDialog extends JDialog {
	private boolean flag = false;
	private JPanel mainJPanel = new JPanel();
	private ComponentMultiList multiList = new ComponentMultiList();
	private JPanel buttonJPanel = new JPanel();
	// private JButton okJButton = new JButton();
	private JButton cancelJButton = new JButton();
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private GridBagLayout gridBagLayout2 = new GridBagLayout();
	private GridBagLayout gridBagLayout3 = new GridBagLayout();

	// add by wangh on 20061205(添加４个用于生成和浏览Dxf和Pdf文件的按钮)
	private JButton browsePdfJButton = new JButton();
	private JButton createPdfJButton = new JButton();
	private JButton browseDxfJButton = new JButton();
	private JButton createDxfJButton = new JButton();
        private TechnicsRegulationsMainJFrame view;
	// add end

	// add by sdg on 20061225
	private ProcessTreeObject processTreeObject;
	// add by sdg end

	// add by sdg on 20070307
	private Vector templateSizeVector = new Vector();
	// add by sdg on 20070307 end

	/** 用于标记资源文件路径 */
	protected static String RESOURCE = "com.faw_qm.cappclients.capp.util.CappLMRB";
	private BaseValueInfo selectedObj;

	// private BaseValueInfo[] selectedObjs;

	/** 用于标记资源信息 */
	protected static ResourceBundle resource = null;

	/** 代码测试变量 */
	private static boolean verbose = (RemoteProperty.getProperty(
			"com.faw_qm.cappclients.verbose", "true")).equals("true");

	/** 缓存：被用户选择的模板 */
	private Vector selectedTemplates = new Vector();

	private Frame mainFrame;

	/**
	 * mario:打印中心所需的业务对象
	 *
	 * @date 2007.10.10
	 */
	private QMTechnicsIfc[] TechObjects;
	private boolean isPrintCenter = false;
	private boolean isHasCatalog = false;

	/**
	 * 为打印中心做准备
	 *
	 * @author 徐德政
	 * @date 2007.10.10 构造函数
	 */
	public ConsTemplateSelectJDialog(JFrame parentFrame, QMTechnicsIfc[] info,
			boolean isHasCatalog) {

		this(parentFrame, "", true);
		this.setObjects(info);
		isPrintCenter = true;
		this.setIsHasCatalog(isHasCatalog);
	}

	/**
	 * 构造函数
	 */
	public ConsTemplateSelectJDialog(JFrame parentFrame) {
		this(parentFrame, "", true);
	}
//        public TemplateSelectJDialog(JFrame parentFrame,) {
//                        this(parentFrame, "", true);
//	}
	/**
	 * 构造函数
	 *
	 * @param frame
	 *            父窗口
	 * @param title
	 *            界面标题
	 * @param modal
	 *            界面模态
	 */
	public ConsTemplateSelectJDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			mainFrame = frame;
                        if(mainFrame==null)
                        {
                        mainFrame=new JFrame();
                        }
			// add by sdg on 20061225
			if (frame instanceof TechnicsRegulationsMainJFrame) {
				TechnicsRegulationsMainJFrame tempFrame = (TechnicsRegulationsMainJFrame) (frame);
				processTreeObject = (ProcessTreeObject) tempFrame
						.getSelectedNode().getObject();
			}
			// add by sdg end
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 初始化
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		// add by sdg on 20070307
		templateSizeVector.addElement("A4");
		templateSizeVector.addElement("A3");
		// add by sdg on 20070307 end

		mainJPanel.setLayout(gridBagLayout2);
		setTitle(QMMessage.getLocalizedMessage(RESOURCE, "selectTemplateTitle",
				null));
		setSize(400, 300);
		getContentPane().setLayout(gridBagLayout3);
		buttonJPanel.setLayout(gridBagLayout1);

		// {{init multiList
		// modify by sdg on 20070307
		multiList.setHeadings(new String[] { "选中", "模板类型", "模板尺寸" });
		// multiList.setHeadings(new String[]{"选中", "模板类型"});
		// modify by sdg on 20070307 end
		// 设置列宽
		multiList.setRelColWidth(new int[] { 1, 1, 1 });
		// 设置可以多选
		multiList.setMultipleMode(true);
		// 添加动作监听
		multiList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multiList_actionPerformed(e);
			}
		});
		// 设置第0列可以编辑
		multiList.setColsEnabled(new int[] { 0, 2 }, true);
		// }}
		// modify by wangh on 20061226 去掉确定按钮。
		// okJButton.setMaximumSize(new Dimension(75, 23));
		// okJButton.setMinimumSize(new Dimension(75, 23));
		// okJButton.setPreferredSize(new Dimension(75, 23));
		// okJButton.setActionCommand("确定");
		// okJButton.setMnemonic('Y');
		// okJButton.setText("确定(Y)");
		// okJButton.addActionListener(new java.awt.event.ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// okJButton_actionPerformed(e);
		// }
		// });
		cancelJButton.setMaximumSize(new Dimension(75, 23));
		cancelJButton.setMinimumSize(new Dimension(75, 23));
		cancelJButton.setPreferredSize(new Dimension(75, 23));
		cancelJButton.setBorderPainted(true);
		cancelJButton.setMnemonic('C');
		cancelJButton.setText("取消(C)");
		cancelJButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		cancelJButton
				.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		cancelJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelJButton_actionPerformed(e);
			}
		});
		// add by wangh on 20061205(对４个按钮进行参数设置)
		browsePdfJButton.setMaximumSize(new Dimension(123, 23));
		browsePdfJButton.setMinimumSize(new Dimension(123, 23));
		browsePdfJButton.setPreferredSize(new Dimension(123, 23));
		browsePdfJButton.setActionCommand("浏览PDF文件");
		browsePdfJButton.setMnemonic('p');
		// browsePdfJButton.setText("浏览PDF文件(P)");
		browsePdfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browsePdfJButton_actionPerformed(e);
			}
		});
		browseDxfJButton.setMaximumSize(new Dimension(123, 23));
		browseDxfJButton.setMinimumSize(new Dimension(123, 23));
		browseDxfJButton.setPreferredSize(new Dimension(123, 23));
		browseDxfJButton.setActionCommand("浏览DXF文件");
		browseDxfJButton.setMnemonic('B');
		// browseDxfJButton.setText("浏览DXF文件(B)");
		browseDxfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseDxfJButton_actionPerformed(e);
			}
		});
		createDxfJButton.setMaximumSize(new Dimension(75, 23));
		createDxfJButton.setMinimumSize(new Dimension(75, 23));
		createDxfJButton.setPreferredSize(new Dimension(75, 23));
		// modify by wangh on 20061226 替换createDxfJButton按钮的显示内容
		// createDxfJButton.setActionCommand("生成DXF文件");
		createDxfJButton.setActionCommand("确定");
		createDxfJButton.setMnemonic('Y');
		// createDxfJButton.setText("生成DXF文件(D)");
		createDxfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDxfJButton_actionPerformed(e);
			}
		});
		createPdfJButton.setMaximumSize(new Dimension(123, 23));
		createPdfJButton.setMinimumSize(new Dimension(123, 23));
		createPdfJButton.setPreferredSize(new Dimension(123, 23));
		createPdfJButton.setActionCommand("生成PDF文件");
		createPdfJButton.setMnemonic('O');
		// createPdfJButton.setText("生成PDF文件(O)");
		createPdfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPdfJButton_actionPerformed(e);
			}
		});
		// add end

		mainJPanel.setMaximumSize(new Dimension(2147483647, 2147483647));
		mainJPanel.setPreferredSize(new Dimension(518, 208));
		getContentPane().add(
				mainJPanel,
				new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 185, 92));
		mainJPanel.add(multiList, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						20, 20, 0, 20), 0, 0));
		mainJPanel.add(buttonJPanel, new GridBagConstraints(1, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(15, 0, 25, 20), 0, 0));
		// buttonJPanel.add(okJButton, new GridBagConstraints(4, 0, 1, 1, 0.0,
		// 0.0
		// , GridBagConstraints.EAST, GridBagConstraints.NONE,
		// new Insets(0, 8, 0, 0), 0, 0));
		buttonJPanel.add(cancelJButton, new GridBagConstraints(5, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		// add by wangh on 20061205(设置４个新加按钮的位置)
		buttonJPanel.add(createPdfJButton, new GridBagConstraints(3, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		buttonJPanel.add(browsePdfJButton, new GridBagConstraints(2, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		buttonJPanel.add(createDxfJButton, new GridBagConstraints(1, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		// modify by wangh on 20061226 去掉browseDxfJButton按钮在界面上的显示。
		// buttonJPanel.add(browseDxfJButton,
		// new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
		// , GridBagConstraints.EAST,
		// GridBagConstraints.NONE,
		// new Insets(0, 8, 0, 0), 0, 0));
		// add end

		setTemplateType();

		localize();
	}

	// add by wangh on 20061205(设置４个按钮在什么情况下哪些按钮可见)
	private void setTemplateType() {
		String selectTemplateType = RemoteProperty.getProperty(
				"selectTemplateType", "all");
		if (selectTemplateType.equals("dxf")) {
			browsePdfJButton.setVisible(false);
			createPdfJButton.setVisible(false);
		} else if (selectTemplateType.equals("pdf")) {
			browseDxfJButton.setVisible(false);
			createDxfJButton.setVisible(false);
		} else if (selectTemplateType.equals("all")) {
			browsePdfJButton.setVisible(false);
			createPdfJButton.setVisible(false);
		}

	}

	// add end

	/**
	 * 本地化
	 */
	private void localize() throws QMRemoteException {
		Vector v = getTemplateType();
		if (v != null) {
			for (int i = 0; i < v.size(); i++) {
				// 向列表第0列添加CheckBox
				// modify by sdg on 20070515(按测试组要求将复选框改成单选框)
                                //Begin by chudaming 20090506 改成默认选中
				//SSBegin pant 20130908
				//CCBegin SS3
				//multiList.addCheckboxCell(i, 0, false);
				boolean isSelsected=false;
				if(i==1)
				{
					isSelsected=true;
				}
				multiList.addCheckboxCell(i, 0, isSelsected);
				//CCBegin SS3
				//SSEnd pant 20130908
				// multiList.addRadioButtonCell(i, 0, false);
				// modify by sdg on 20070515 end(按测试组要求将复选框改成单选框)
				// 向列表第1列添加文本
				multiList.addTextCell(i, 1, v.elementAt(i).toString());
				// add by sdg on 20070307
				multiList.addComboBoxCell(i, 2,
						templateSizeVector.elementAt(0), templateSizeVector);
				// add by sdg on 20070307 end
			}
		}
		if (resource == null) {
			initResources();
		}

		try {
			// okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
			// "OkJButton", null));
			cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"CancelJButton", null));
			browsePdfJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"browsePdfJButton", null));
			browseDxfJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"browseDxfJButton", null));
			createDxfJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"createDxfJButton", null));
			createPdfJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
					"createPdfJButton", null));
			// multiList.setHeadings(new String[]
			// {
			// QMMessage.getLocalizedMessage(RESOURCE,
			// "select", null),
			// QMMessage.getLocalizedMessage(RESOURCE,
			// "templateType", null)});
		} catch (MissingResourceException ex) {
			ex.printStackTrace();
			String title = QMMessage.getLocalizedMessage(RESOURCE,
					"information", null);
			JOptionPane.showMessageDialog(this, CappLMRB.MISSING_RESOURCER,
					title, JOptionPane.INFORMATION_MESSAGE);

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
			String title = QMMessage.getLocalizedMessage(RESOURCE,
					"information", null);
			JOptionPane.showMessageDialog(this, CappLMRB.MISSING_RESOURCER,
					title, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	/**
	 * 设置业务对象
	 *
	 * @param info
	 *            打印对象
	 */
	public void setObject(BaseValueIfc info) {
		selectedObj = (BaseValueInfo) info;
	}

	/**
	 * 获得业务对象
	 *
	 * @return 打印对象
	 */
	public BaseValueIfc getObject() {
		return selectedObj;
	}

	/**
	 *
	 * 为打印中心设置业务对象
	 *
	 * @param infos[]
	 *            打印对象集合
	 * @author 徐德政
	 * @date 2007.10.10
	 */
	public void setObjects(QMTechnicsIfc[] infos) {
		this.TechObjects = infos;
	}

	/**
	 * 得到打印中心所需的业务对象
	 *
	 * @param infos[]
	 *            打印对象集合
	 * @return TechObjects
	 * @author 徐德政
	 * @date 2007.10.10
	 */
	public QMTechnicsIfc[] getObjects() {
		return this.TechObjects;
	}

	/**
	 * 获得属性文件中的模板类型
	 *
	 * @return 模板类型(字符串形式)集合
	 */
	public Vector getTemplateType() throws QMRemoteException {
		Class[] paramclass = new Class[] { String.class, String.class };
		Object[] paramobject = new Object[] { "模板类型", "模板" };
		Collection c = (Collection) TechnicsAction.useServiceMethod(
				"CodingManageService", "findDirectClassificationByName",
				paramclass, paramobject);
		//CCBegin SS1
//		Vector v = new Vector(c);
		Vector v = new Vector();
		if(c!=null){
			for(Iterator iter = c.iterator(); iter.hasNext();){
				CodingClassificationIfc codingccifc = (CodingClassificationIfc)
                iter.next();
				String cc = codingccifc.getCodeNote();
				String bsx = "变速箱";
				if (cc!= null){
				  if (cc.indexOf (bsx) > -1){
					v.addElement(codingccifc.getCodeSort());
				  }
				}
			}
		   	
		}
		//CCEnd SS1
		// v.remove(0);
		return v;
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
                  System.setProperty("swing.useSystemFontSettings", "0");
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

        if (args.length == 4)
        {
            try
            {
                CappClientRequestServer partServer = new
                        CappClientRequestServer(
                        args[0], args[1], args[2]);
                RequestServerFactory.setRequestServer(partServer);
                BaseValueInfo info = CappClientHelper.refresh(args[3]);
                Class[] paramclass = new Class[]
                                     {
                                     String.class, String.class};
                Object[] paramobject = new Object[]
                                       {
                                       "模板类型", "模板"};
                Collection c = (Collection) TechnicsAction.useServiceMethod(
                        "CodingManageService", "findDirectClassificationByName",
                        paramclass, paramobject);
                if (c.size() >= 1){
                       ConsTemplateSelectJDialog d = new ConsTemplateSelectJDialog(
                               null);
                       d.setObject(info);
                       d.setVisible(true);
                }

            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
	}

	/**
	 * 设置界面的显示位置
	 */
	private void showLocation() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

	}

	/**
	 * 设置界面显示
	 *
	 * @param flag
	 *            界面是否显示
	 */
	public void setVisible(boolean flag) {
		showLocation();
		super.setVisible(flag);
	}

	/*
	 * private void okJButton_actionPerformed(ActionEvent e) { try {
	 * setSelectedTemplates(); if (getSelectedTemplates().size() != 0) { if
	 * (!flag) { commit(); } } } catch (QMRemoteException ex) { String title =
	 * QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	 * JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
	 * JOptionPane.INFORMATION_MESSAGE); return; } dispose(); }
	 */

	/**
	 * 执行取消操作，关闭界面。
	 *
	 * @param e
	 *            ActionEvent
	 */
	void cancelJButton_actionPerformed(ActionEvent e) {
		setVisible(false);
	}

	void multiList_actionPerformed(ActionEvent e) {

	}

	/**
	 * 打印预览。
	 *
	 * @throws QMRemoteException
	 */
	/*
	 * private void commit() throws QMRemoteException { if
	 * (TemplateSelectJDialog.this.getObject() == null) { PrintMainJFrame
	 * printMainFrame = (PrintMainJFrame) mainFrame;
	 * printMainFrame.setSelectTemplateCards(TemplateSelectJDialog.this.
	 * getSelectedTemplates()); dispose(); } else { final TPrintController c =
	 * new TPrintController();
	 * c.setObject(TemplateSelectJDialog.this.getObject());
	 * c.setTemplateType(TemplateSelectJDialog.this. getSelectedTemplates());
	 * Runnable able = new Runnable() { public void run() {
	 *
	 * try { c.print(); } catch (QMRemoteException ex) { } } }; Thread t = new
	 * Thread(able); t.start(); dispose(); } }
	 */

	/**
	 * 获得所选择的模板
	 *
	 * @return 所选择的模板的集合。集合中的元素为String。
	 */
	public Vector getSelectedTemplates() {
		return selectedTemplates;
	}

	/**
	 * 设置所选则的模板
	 */
	private void setSelectedTemplates() {
		selectedTemplates.removeAllElements();
		int rowNums = multiList.getNumberOfRows();
		// int[] selectRows=multiList.getSelectedRows();
		// LightweightFileTool.templatesSizeArray=new String[selectRows.length];
		Vector tempVec = new Vector();
		if (rowNums > 0) {
			for (int i = 0; i < rowNums; i++) {
				if (multiList.isCheckboxSelected(i, 0)) {
					selectedTemplates.addElement(multiList.getCellText(i, 1));
					tempVec.addElement((String) multiList.getComboBoxObject(i,
							2));
					// LightweightFileTool.templatesSizeArray[rowSelect++]=(String)multiList.getComboBoxObject(i,2);
				}
			}
		}
		if (selectedTemplates.size() > 0) {
			LightweightFileTool.templatesSizeArray = new String[selectedTemplates
					.size()];
			int rowSelect = 0;
			for (int i = 0; i < rowNums; i++) {
				if (multiList.isCheckboxSelected(i, 0)) {
					LightweightFileTool.templatesSizeArray[rowSelect++] = (String) multiList
							.getComboBoxObject(i, 2);
				}
			}
		}
	}

	/**
	 * 设置flag标记，用于标识是否是打印中心调用的这个界面
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	// add by wangh on 20061205(设置预览时生成和浏览PDF与Dxf文件按钮功能监听)
	// modify by xdz on 20071010
	public void createDxfJButton_actionPerformed(ActionEvent e) {
		if (isPrintCenter) {
			this.printCenter();
		} else {
			try {
				// System.out.println(this.getClass().getResource("TemplateSelectJDialog.class"));
				// java.net.URL url=new java.net.URL();
				// System.out.println(url);
				setSelectedTemplates();
				if (getSelectedTemplates().size() != 0) {
					// 2008.03.24 徐德政注释
					/*
					 * if (getSelectedTemplates().size() > 1) {
					 * JOptionPane.showMessageDialog(this, "只能选择一种模板类型", "错误",
					 * JOptionPane.INFORMATION_MESSAGE);
					 * getSelectedTemplates().clear(); return; }
					 */
					if (this.getObject() instanceof QMTechnicsIfc) {
						Runnable able = new Runnable() {
							public void run() {
								try {
									LightweightFileTool.isPrintQMTechnics = false;
									LightweightFileTool.init(mainFrame,
											(QMTechnicsIfc) getObject(), "");
									String[] stringArray = new String[getSelectedTemplates()
											.size()];

									for (int i = 0; i < getSelectedTemplates()
											.size(); i++) {
										stringArray[i] = getSelectedTemplates()
												.elementAt(i).toString();
									}
									//CCBegin by liunan 2010-6-1 打补丁v4r1_p027_20100531									
									//begin CR1
                                    QMTechnicsIfc te = (QMTechnicsIfc) getObject();
                                    if(!te.getIterationIfLatest())
                                    {
                                        LightweightFileTool.previewHistroyTechnicss(stringArray);
                                    }
                                    else
                                     //end CR1
									//CCEnd by liunan 2010-6-1
									LightweightFileTool
											.createLightweightFiles(stringArray);
									// LightweightFileTool.browseLightweightFile(stringArray[0]);
								} catch (QMException ex) {
									ex.printStackTrace();
									String title = QMMessage
											.getLocalizedMessage(RESOURCE,
													"exception", null);
									JOptionPane.showMessageDialog(
											ConsTemplateSelectJDialog.this, ex
													.getClientMessage(), title,
											JOptionPane.INFORMATION_MESSAGE);
									ConsTemplateSelectJDialog.this.dispose();
									return;
								} catch (Exception e) {
									e.printStackTrace();
									String title = QMMessage
											.getLocalizedMessage(RESOURCE,
													"exception", null);
									JOptionPane.showMessageDialog(
											ConsTemplateSelectJDialog.this,
											"生成DXF文件过程中出现错误", title,
											JOptionPane.INFORMATION_MESSAGE);
									ConsTemplateSelectJDialog.this.dispose();
									return;
								}
							}
						};
						Thread t = new Thread(able);
						t.start();
						ConsTemplateSelectJDialog.this.dispose();
					} else if (this.getObject() instanceof QMProcedureIfc) {
						Runnable able = new Runnable() {
							public void run() {
								try {
									LightweightFileTool.isPrintQMTechnics = true;
									String techniceBsoID = processTreeObject
											.getParentID();
									Class[] paraclass = { String.class, };
									Object[] paraobj = { techniceBsoID };

									QMTechnicsIfc tempIfc = (QMTechnicsIfc) TechnicsAction
											.useServiceMethod("PersistService",
													"refreshInfo", paraclass,
													paraobj);
									LightweightFileTool.init(mainFrame,
											tempIfc, "");
									String[] stringArray = new String[getSelectedTemplates()
											.size()];
									for (int i = 0; i < getSelectedTemplates()
											.size(); i++) {
										stringArray[i] = getSelectedTemplates()
												.elementAt(i).toString();
									}
									//2008.03.26 徐德政改，实现工序多模板类型预览
									LightweightFileTool.previewOneStep(
											(QMProcedureIfc) getObject(),
											stringArray);
									/*LightweightFileTool.previewOneStep(
											(QMProcedureIfc) getObject(),
											stringArray[0]);*/
									//end mario
									// LightweightFileTool.createLightweightFiles(stringArray);
									// LightweightFileTool.browseLightweightFile(stringArray[0]);
								} catch (QMRemoteException ex) {
									ex.printStackTrace();
									String title = QMMessage
											.getLocalizedMessage(RESOURCE,
													"exception", null);
									JOptionPane.showMessageDialog(
											ConsTemplateSelectJDialog.this, ex
													.getClientMessage(), title,
											JOptionPane.INFORMATION_MESSAGE);
									ConsTemplateSelectJDialog.this.dispose();
									return;

								} catch (Exception e) {
									e.printStackTrace();
									String title = QMMessage
											.getLocalizedMessage(RESOURCE,
													"exception", null);
									JOptionPane.showMessageDialog(
											ConsTemplateSelectJDialog.this,
											"生成DXF文件过程中出现错误", title,
											JOptionPane.INFORMATION_MESSAGE);
									ConsTemplateSelectJDialog.this.dispose();
									return;

								}
							}
						};
						Thread t = new Thread(able);
						t.start();
						ConsTemplateSelectJDialog.this.dispose();

					}
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				String title = QMMessage.getLocalizedMessage(RESOURCE,
						"exception", null);
				JOptionPane.showMessageDialog(this, "生成DXF文件过程中出现错误", title,
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				return;
			}
		}
	}

	public void setIsHasCatalog(boolean has) {
		this.isHasCatalog = has;
	}

	public boolean getIsHasCatalog() {
		return this.isHasCatalog;
	}

	/**
	 * 为打印中心重写预览按钮监听方法
	 *
	 * @author 徐德政
	 * @date 2007.10.10
	 */
	public void printCenter() {
		try {
			setSelectedTemplates();
			if (getSelectedTemplates().size() != 0) {
				//2008.03.24 徐德政改
				/*if (getSelectedTemplates().size() > 1) {
					JOptionPane.showMessageDialog(this, "只能选择一种模板类型", "错误",
							JOptionPane.INFORMATION_MESSAGE);
					getSelectedTemplates().clear();
					return;
				}*/
				Runnable able = new Runnable() {
					public void run() {
						try {
							LightweightFileToolForPrintCenter.isPrintQMTechnics = false;
							LightweightFileToolForPrintCenter.init(mainFrame,
									(QMTechnicsIfc[]) getObjects(), "",
									getIsHasCatalog());
							String[] stringArray = new String[getSelectedTemplates()
									.size()];
							for (int i = 0; i < getSelectedTemplates().size(); i++) {
								stringArray[i] = getSelectedTemplates()
										.elementAt(i).toString();
							}
							// CCBegin SS2
//							LightweightFileToolForPrintCenter
//							.findLightweightFilesForPrintCenter(stringArray);
							//System.out.println("变速箱控制计划=====stringArray[0].trim()=====1111111111111111111111111============="+stringArray[0].trim());
							if(stringArray.length==1){
								if(stringArray[0].trim().indexOf("变速箱控制计划")!=-1){
									//System.out.println("变速箱控制计划==========aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa=============");
									DataDisposeTool tool=new DataDisposeTool();
									PDocument document=tool.prepareSuitCards((QMTechnicsIfc[]) getObjects(),stringArray, "A4");
									LightweightFileTool.previewSteps(document,stringArray);
								}
							}else{
								//System.out.println("变速箱控制计划==========bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb=============");
								LightweightFileToolForPrintCenter
								.findLightweightFilesForPrintCenter(stringArray);
							}
							//CCEnd SS2
						} catch (QMException ex) {
							ex.printStackTrace();
							String title = QMMessage.getLocalizedMessage(
									RESOURCE, "exception", null);
							JOptionPane.showMessageDialog(
									ConsTemplateSelectJDialog.this,
									"在打印之前，请在工艺规程管理器中对您所选择的工艺规程进行打印预览！", title,
									JOptionPane.INFORMATION_MESSAGE);
							// JOptionPane.showMessageDialog(TemplateSelectJDialog.this,
							// ex.getClientMessage(), title,
							// JOptionPane.INFORMATION_MESSAGE);
							ConsTemplateSelectJDialog.this.dispose();
							return;
						} catch (Exception e) {
							e.printStackTrace();
							String title = QMMessage.getLocalizedMessage(
									RESOURCE, "exception", null);
							JOptionPane.showMessageDialog(
									ConsTemplateSelectJDialog.this,
									"请在工艺浏览器中对您所选择的工艺规进行打印预览！", title,
									JOptionPane.INFORMATION_MESSAGE);
							ConsTemplateSelectJDialog.this.dispose();
							return;
						}
					}
				};
				Thread t = new Thread(able);
				t.start();
				ConsTemplateSelectJDialog.this.dispose();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
					null);
			JOptionPane.showMessageDialog(this, "生成DXF文件过程中出现错误", title,
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			return;
		}
	}

	// end mario

	public void browsePdfJButton_actionPerformed(ActionEvent e) {

	}

	public void browseDxfJButton_actionPerformed(ActionEvent e) {
		try {
			setSelectedTemplates();
			if (getSelectedTemplates().size() != 0) {
				if (getSelectedTemplates().size() > 1) {
					JOptionPane.showMessageDialog(this, "只能选择一种模板类型", "错误",
							JOptionPane.INFORMATION_MESSAGE);
					getSelectedTemplates().clear();
					return;
				}

				if (this.getObject() instanceof QMTechnicsIfc) {
					Runnable able = new Runnable() {
						public void run() {

							try {
								LightweightFileTool.init(mainFrame,
										(QMTechnicsIfc) getObject(), "A4");
								String[] stringArray = new String[getSelectedTemplates()
										.size()];
								for (int i = 0; i < getSelectedTemplates()
										.size(); i++) {
									stringArray[i] = getSelectedTemplates()
											.elementAt(i).toString();
									// LightweightFileTool.createLightweightFiles(stringArray);
								}
								LightweightFileTool
										.browseLightweightFile(stringArray[0]);

							} catch (QMRemoteException ex) {
								ex.printStackTrace();
								String title = QMMessage.getLocalizedMessage(
										RESOURCE, "exception", null);
								JOptionPane.showMessageDialog(
										ConsTemplateSelectJDialog.this, ex
												.getClientMessage(), title,
										JOptionPane.INFORMATION_MESSAGE);
								ConsTemplateSelectJDialog.this.dispose();
								return;

							} catch (Exception e) {
								e.printStackTrace();
								String title = QMMessage.getLocalizedMessage(
										RESOURCE, "exception", null);
								JOptionPane.showMessageDialog(
										ConsTemplateSelectJDialog.this,
										"生成DXF文件过程中出现错误", title,
										JOptionPane.INFORMATION_MESSAGE);
								ConsTemplateSelectJDialog.this.dispose();
								return;

							}
						}
					};
					Thread t = new Thread(able);
					t.start();
					ConsTemplateSelectJDialog.this.dispose();

				} else if (this.getObject() instanceof QMProcedureIfc) {
					Runnable able = new Runnable() {
						public void run() {

							try {
								LightweightFileTool.init(mainFrame,
										(QMTechnicsIfc) getObject(), "A4");
								String[] stringArray = new String[getSelectedTemplates()
										.size()];
								for (int i = 0; i < getSelectedTemplates()
										.size(); i++) {
									stringArray[i] = getSelectedTemplates()
											.elementAt(i).toString();
									// LightweightFileTool.createLightweightFiles(stringArray);
								}
								LightweightFileTool
										.browseLightweightFile(stringArray[0]);

							} catch (QMRemoteException ex) {
								ex.printStackTrace();
								String title = QMMessage.getLocalizedMessage(
										RESOURCE, "exception", null);
								JOptionPane.showMessageDialog(
										ConsTemplateSelectJDialog.this, ex
												.getClientMessage(), title,
										JOptionPane.INFORMATION_MESSAGE);
								ConsTemplateSelectJDialog.this.dispose();
								return;

							} catch (Exception e) {
								e.printStackTrace();
								String title = QMMessage.getLocalizedMessage(
										RESOURCE, "exception", null);
								JOptionPane.showMessageDialog(
										ConsTemplateSelectJDialog.this,
										"生成DXF文件过程中出现错误", title,
										JOptionPane.INFORMATION_MESSAGE);
								ConsTemplateSelectJDialog.this.dispose();
								return;

							}
						}
					};
					Thread t = new Thread(able);
					t.start();
					ConsTemplateSelectJDialog.this.dispose();

				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
					null);
			JOptionPane.showMessageDialog(this, "生成DXF文件过程中出现错误", title,
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			return;

		}

	}

	public void createPdfJButton_actionPerformed(ActionEvent e) {

	}

	// add end
}
