/** ���ɳ���TemplateSelectJDialog.java	1.1  2003/08/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��ʾ�������ӡģ������ liuyang 2013-3-22
 * SS2 ��������Ƽƻ��๤�մ�ӡ��� Liuyang 2014-2-18
 * SS3 Ĭ�ϴ�ӡԤ�����Ƽƻ� liunan 2014-8-20
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
 * Title:ģ��ѡ��Ի���
 * </p>
 * <p>
 * ִ����ѡ���ӡԤ��ʱ�������˽���ѡ��ģ��
 * </p>
 * <p>
 * �ڹ��������н��в鿴����ʱͬ�������ý������ѡ��ģ�壬Ȼ���ٽ�������׼����Ԥ����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *    
 * @author ����
 * CR1  2010/05/27  Ѧ��  �μ� TD2266
 * @version 1.0
 */
// 20061205�����޸ģ����4����ť��browsePdfJButton��createPdfJButton��browseDxfJButton��createDxfJButton�����������ɺ����Dxf��Pdf�ļ�
// modify by wangh on 20061226
// ��createDxfJButton��ť����ȷ����ť�����ǹ��ܲ��ı䡣�ӽ�����ȥ����browseDxfJButton��ť��
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

	// add by wangh on 20061205(��ӣ����������ɺ����Dxf��Pdf�ļ��İ�ť)
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

	/** ���ڱ����Դ�ļ�·�� */
	protected static String RESOURCE = "com.faw_qm.cappclients.capp.util.CappLMRB";
	private BaseValueInfo selectedObj;

	// private BaseValueInfo[] selectedObjs;

	/** ���ڱ����Դ��Ϣ */
	protected static ResourceBundle resource = null;

	/** ������Ա��� */
	private static boolean verbose = (RemoteProperty.getProperty(
			"com.faw_qm.cappclients.verbose", "true")).equals("true");

	/** ���棺���û�ѡ���ģ�� */
	private Vector selectedTemplates = new Vector();

	private Frame mainFrame;

	/**
	 * mario:��ӡ���������ҵ�����
	 *
	 * @date 2007.10.10
	 */
	private QMTechnicsIfc[] TechObjects;
	private boolean isPrintCenter = false;
	private boolean isHasCatalog = false;

	/**
	 * Ϊ��ӡ������׼��
	 *
	 * @author �����
	 * @date 2007.10.10 ���캯��
	 */
	public ConsTemplateSelectJDialog(JFrame parentFrame, QMTechnicsIfc[] info,
			boolean isHasCatalog) {

		this(parentFrame, "", true);
		this.setObjects(info);
		isPrintCenter = true;
		this.setIsHasCatalog(isHasCatalog);
	}

	/**
	 * ���캯��
	 */
	public ConsTemplateSelectJDialog(JFrame parentFrame) {
		this(parentFrame, "", true);
	}
//        public TemplateSelectJDialog(JFrame parentFrame,) {
//                        this(parentFrame, "", true);
//	}
	/**
	 * ���캯��
	 *
	 * @param frame
	 *            ������
	 * @param title
	 *            �������
	 * @param modal
	 *            ����ģ̬
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
	 * ��ʼ��
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
		multiList.setHeadings(new String[] { "ѡ��", "ģ������", "ģ��ߴ�" });
		// multiList.setHeadings(new String[]{"ѡ��", "ģ������"});
		// modify by sdg on 20070307 end
		// �����п�
		multiList.setRelColWidth(new int[] { 1, 1, 1 });
		// ���ÿ��Զ�ѡ
		multiList.setMultipleMode(true);
		// ��Ӷ�������
		multiList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multiList_actionPerformed(e);
			}
		});
		// ���õ�0�п��Ա༭
		multiList.setColsEnabled(new int[] { 0, 2 }, true);
		// }}
		// modify by wangh on 20061226 ȥ��ȷ����ť��
		// okJButton.setMaximumSize(new Dimension(75, 23));
		// okJButton.setMinimumSize(new Dimension(75, 23));
		// okJButton.setPreferredSize(new Dimension(75, 23));
		// okJButton.setActionCommand("ȷ��");
		// okJButton.setMnemonic('Y');
		// okJButton.setText("ȷ��(Y)");
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
		cancelJButton.setText("ȡ��(C)");
		cancelJButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		cancelJButton
				.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		cancelJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelJButton_actionPerformed(e);
			}
		});
		// add by wangh on 20061205(�ԣ�����ť���в�������)
		browsePdfJButton.setMaximumSize(new Dimension(123, 23));
		browsePdfJButton.setMinimumSize(new Dimension(123, 23));
		browsePdfJButton.setPreferredSize(new Dimension(123, 23));
		browsePdfJButton.setActionCommand("���PDF�ļ�");
		browsePdfJButton.setMnemonic('p');
		// browsePdfJButton.setText("���PDF�ļ�(P)");
		browsePdfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browsePdfJButton_actionPerformed(e);
			}
		});
		browseDxfJButton.setMaximumSize(new Dimension(123, 23));
		browseDxfJButton.setMinimumSize(new Dimension(123, 23));
		browseDxfJButton.setPreferredSize(new Dimension(123, 23));
		browseDxfJButton.setActionCommand("���DXF�ļ�");
		browseDxfJButton.setMnemonic('B');
		// browseDxfJButton.setText("���DXF�ļ�(B)");
		browseDxfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseDxfJButton_actionPerformed(e);
			}
		});
		createDxfJButton.setMaximumSize(new Dimension(75, 23));
		createDxfJButton.setMinimumSize(new Dimension(75, 23));
		createDxfJButton.setPreferredSize(new Dimension(75, 23));
		// modify by wangh on 20061226 �滻createDxfJButton��ť����ʾ����
		// createDxfJButton.setActionCommand("����DXF�ļ�");
		createDxfJButton.setActionCommand("ȷ��");
		createDxfJButton.setMnemonic('Y');
		// createDxfJButton.setText("����DXF�ļ�(D)");
		createDxfJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDxfJButton_actionPerformed(e);
			}
		});
		createPdfJButton.setMaximumSize(new Dimension(123, 23));
		createPdfJButton.setMinimumSize(new Dimension(123, 23));
		createPdfJButton.setPreferredSize(new Dimension(123, 23));
		createPdfJButton.setActionCommand("����PDF�ļ�");
		createPdfJButton.setMnemonic('O');
		// createPdfJButton.setText("����PDF�ļ�(O)");
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
		// add by wangh on 20061205(���ã����¼Ӱ�ť��λ��)
		buttonJPanel.add(createPdfJButton, new GridBagConstraints(3, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		buttonJPanel.add(browsePdfJButton, new GridBagConstraints(2, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		buttonJPanel.add(createDxfJButton, new GridBagConstraints(1, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 8, 0, 0), 0, 0));
		// modify by wangh on 20061226 ȥ��browseDxfJButton��ť�ڽ����ϵ���ʾ��
		// buttonJPanel.add(browseDxfJButton,
		// new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
		// , GridBagConstraints.EAST,
		// GridBagConstraints.NONE,
		// new Insets(0, 8, 0, 0), 0, 0));
		// add end

		setTemplateType();

		localize();
	}

	// add by wangh on 20061205(���ã�����ť��ʲô�������Щ��ť�ɼ�)
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
	 * ���ػ�
	 */
	private void localize() throws QMRemoteException {
		Vector v = getTemplateType();
		if (v != null) {
			for (int i = 0; i < v.size(); i++) {
				// ���б��0�����CheckBox
				// modify by sdg on 20070515(��������Ҫ�󽫸�ѡ��ĳɵ�ѡ��)
                                //Begin by chudaming 20090506 �ĳ�Ĭ��ѡ��
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
				// modify by sdg on 20070515 end(��������Ҫ�󽫸�ѡ��ĳɵ�ѡ��)
				// ���б��1������ı�
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
	 * ��ʼ����ʹ�õ���Դ����Ϣ��
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
	 * ����ҵ�����
	 *
	 * @param info
	 *            ��ӡ����
	 */
	public void setObject(BaseValueIfc info) {
		selectedObj = (BaseValueInfo) info;
	}

	/**
	 * ���ҵ�����
	 *
	 * @return ��ӡ����
	 */
	public BaseValueIfc getObject() {
		return selectedObj;
	}

	/**
	 *
	 * Ϊ��ӡ��������ҵ�����
	 *
	 * @param infos[]
	 *            ��ӡ���󼯺�
	 * @author �����
	 * @date 2007.10.10
	 */
	public void setObjects(QMTechnicsIfc[] infos) {
		this.TechObjects = infos;
	}

	/**
	 * �õ���ӡ���������ҵ�����
	 *
	 * @param infos[]
	 *            ��ӡ���󼯺�
	 * @return TechObjects
	 * @author �����
	 * @date 2007.10.10
	 */
	public QMTechnicsIfc[] getObjects() {
		return this.TechObjects;
	}

	/**
	 * ��������ļ��е�ģ������
	 *
	 * @return ģ������(�ַ�����ʽ)����
	 */
	public Vector getTemplateType() throws QMRemoteException {
		Class[] paramclass = new Class[] { String.class, String.class };
		Object[] paramobject = new Object[] { "ģ������", "ģ��" };
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
				String bsx = "������";
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
	 * ����
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
                                       "ģ������", "ģ��"};
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
	 * ���ý������ʾλ��
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
	 * ���ý�����ʾ
	 *
	 * @param flag
	 *            �����Ƿ���ʾ
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
	 * ִ��ȡ���������رս��档
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
	 * ��ӡԤ����
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
	 * �����ѡ���ģ��
	 *
	 * @return ��ѡ���ģ��ļ��ϡ������е�Ԫ��ΪString��
	 */
	public Vector getSelectedTemplates() {
		return selectedTemplates;
	}

	/**
	 * ������ѡ���ģ��
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
	 * ����flag��ǣ����ڱ�ʶ�Ƿ��Ǵ�ӡ���ĵ��õ��������
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	// add by wangh on 20061205(����Ԥ��ʱ���ɺ����PDF��Dxf�ļ���ť���ܼ���)
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
					// 2008.03.24 �����ע��
					/*
					 * if (getSelectedTemplates().size() > 1) {
					 * JOptionPane.showMessageDialog(this, "ֻ��ѡ��һ��ģ������", "����",
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
									//CCBegin by liunan 2010-6-1 �򲹶�v4r1_p027_20100531									
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
											"����DXF�ļ������г��ִ���", title,
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
									//2008.03.26 ������ģ�ʵ�ֹ����ģ������Ԥ��
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
											"����DXF�ļ������г��ִ���", title,
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
				JOptionPane.showMessageDialog(this, "����DXF�ļ������г��ִ���", title,
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
	 * Ϊ��ӡ������дԤ����ť��������
	 *
	 * @author �����
	 * @date 2007.10.10
	 */
	public void printCenter() {
		try {
			setSelectedTemplates();
			if (getSelectedTemplates().size() != 0) {
				//2008.03.24 �������
				/*if (getSelectedTemplates().size() > 1) {
					JOptionPane.showMessageDialog(this, "ֻ��ѡ��һ��ģ������", "����",
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
							//System.out.println("��������Ƽƻ�=====stringArray[0].trim()=====1111111111111111111111111============="+stringArray[0].trim());
							if(stringArray.length==1){
								if(stringArray[0].trim().indexOf("��������Ƽƻ�")!=-1){
									//System.out.println("��������Ƽƻ�==========aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa=============");
									DataDisposeTool tool=new DataDisposeTool();
									PDocument document=tool.prepareSuitCards((QMTechnicsIfc[]) getObjects(),stringArray, "A4");
									LightweightFileTool.previewSteps(document,stringArray);
								}
							}else{
								//System.out.println("��������Ƽƻ�==========bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb=============");
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
									"�ڴ�ӡ֮ǰ�����ڹ��չ�̹������ж�����ѡ��Ĺ��չ�̽��д�ӡԤ����", title,
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
									"���ڹ���������ж�����ѡ��Ĺ��չ���д�ӡԤ����", title,
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
			JOptionPane.showMessageDialog(this, "����DXF�ļ������г��ִ���", title,
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
					JOptionPane.showMessageDialog(this, "ֻ��ѡ��һ��ģ������", "����",
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
										"����DXF�ļ������г��ִ���", title,
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
										"����DXF�ļ������г��ִ���", title,
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
			JOptionPane.showMessageDialog(this, "����DXF�ļ������г��ִ���", title,
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			return;

		}

	}

	public void createPdfJButton_actionPerformed(ActionEvent e) {

	}

	// add end
}
