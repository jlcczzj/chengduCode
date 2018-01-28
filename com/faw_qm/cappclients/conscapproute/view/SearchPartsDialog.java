/**
 * ���ɳ��� ParentPartJDialog 1.0 2005.3.2
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 2009/06/08 ��ѧ��  ԭ�򣺽�����·�ߵ�λ�������Ի����ɷ�ģ̬�޸�Ϊģ̬ 
 * SS1 ��������б�����ԭ���õġ���֯��������Ϊ����֯����-bsx����������༭·��ʱ��ʾ����֯����-bsx�� liunan 2014-6-17
 * SS2 ��������гɶ�ʹ�á���֯����-cd liunan 2016-8-29
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.beans.*;

import java.io.BufferedWriter;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.FileWriter;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.capp.view.*;
import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.XLSFileFilter;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.cappclients.util.ComponentMultiList;

//CCBegin SS1
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
//CCEnd SS1

/**
 * <p> Title:��·�ߵ�λ�����㲿�������� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2005 </p> <p> Company: һ������ </p>
 * @author skybird
 * @version 1.0
 */

public class SearchPartsDialog extends JDialog //CR1
{
    //�������
    private JComboBox comboBox;
    JPanel panel1 = new JPanel();

    JPanel jPanel2 = new JPanel();

    JPanel jPanel3 = new JPanel();

    JLabel makeJLabel = new JLabel();

    JLabel constructJLabel = new JLabel();
    
    JLabel stateLabel = new JLabel();

    CappSortingSelectedPanel makeSelectPanel;

    CappSortingSelectedPanel contructSelectPanel;
    
    final JPanel panel = new JPanel();

    JButton okJButton = new JButton();

    JButton cancelJButton = new JButton();
    /** �б� */
    private ComponentMultiList qMMultiList = new ComponentMultiList();

    //�߼�Ԫ��
    private String makeDepartmentid = "";

    private String constructDepartmentid = "";

    private JFrame parentFrame;

    private String state = "��ѡ�е� ";

    private String stateMes = "��ӭ���밴·�ߵ�λ����. . .";

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCEP = "com.faw_qm.part.client.other.util.OtherRB";

    /** Ҫ��ѯ��ҵ����� */
    public static String SCHEMA = QMMessage.getLocalizedMessage(RESOURCEP, "schema", null);

    /** ��ѯ���� */
    private CappSchema mySchema;

    /** ��Դ�ļ�·�� */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    TitledBorder titledBorder1;

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    JLabel partNoLabel = new JLabel();
    
    JLabel partNameLabel = new JLabel();

    JTextField partNoTextField = new JTextField();
    JTextField partNameTextField = new JTextField();

    JButton serchPartNoButton = new JButton();

    JLabel sourceLabel = new JLabel();

    EnumeratedChoice sourceComboBox = new EnumeratedChoice();

    EnumeratedChoice typeComboBox = new EnumeratedChoice();

    JLabel typeLabel = new JLabel();

    String productID = "";

    GridBagLayout gridBagLayout2 = new GridBagLayout();

    GridBagLayout gridBagLayout3 = new GridBagLayout();
    JButton exportButton = new JButton();

    JPanel jPanel4 = new JPanel();

    // JComboBox jComboBox2 = new JComboBox();
    //JComboBox jComboBox1 = new JComboBox();

    //CCBegin SS1
    private String comp="";
    //CCEnd SS1
    
    public SearchPartsDialog(JFrame frame)
    {
    	
        //begin CR1
        super(frame, "", true);
        getContentPane().setLayout(new GridBagLayout());
        setSize(new Dimension(801, 600));
        //end CR1
        this.parentFrame = frame;
        try
        {
        	//CCBegin SS1
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
            //CCEnd SS1
            jbInit();
            pack();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

       
       
    }

    /**
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit() throws Exception
    {
        URL url = CappRouteListManageJFrame.class.getResource("/images/routeM.gif");
        if(url != null)
        {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));

        }
        
        
        
        qMMultiList.setDebugGraphicsOptions(0);
        qMMultiList.setMaximumSize(new Dimension(380, 240));
        qMMultiList.setInputVerifier(null);
        qMMultiList.setHeadings(new String[]{"id", "������", "�������", "״̬", "����", "�汾", "����·��", "װ��·��", "·�ߵ���", "��������"});
        qMMultiList.setRelColWidth(new int[]{0, 2, 2, 1, 1, 1, 3, 3, 2, 1});
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
       
        final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
        gridBagConstraints_1.gridx = 0;
        gridBagConstraints_1.gridy = 1;
        gridBagConstraints_1.ipadx = 450;
        gridBagConstraints_1.ipady = 15;
        getContentPane().add(panel, gridBagConstraints_1);
        panel.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(8, 5, 25, 0), 290, 240));

        
        exportButton.setText("��  ��");
        final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
        gridBagConstraints_2.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints_2.insets = new Insets(0, 0, 20, 30);
        gridBagConstraints_2.anchor = GridBagConstraints.EAST;
        gridBagConstraints_2.gridy = 1;
        gridBagConstraints_2.gridx = 0;
        panel.add(exportButton, gridBagConstraints_2);
       
        
        
        this.setResizable(false);
        this.setTitle("��·�ߵ�λ����");
        titledBorder1 = new TitledBorder("");
        panel1.setLayout(gridBagLayout3);
        panel1.setBorder(new BevelBorder(BevelBorder.RAISED));
     
        jPanel2.setLayout(gridBagLayout1);
        makeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        makeJLabel.setText("���쵥λ");
        constructJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        constructJLabel.setText("װ�䵥λ");
        stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stateLabel.setText("״̬");
        okJButton.setText("����(S)");
        okJButton.setMnemonic('S');
        //��ʼ����ʱ��ok������ʹ��
        okJButton.setEnabled(true);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("ͨ��ѡ������쵥λ��װ�䵥λ�������㲿��. . .");
        okJButton.addMouseListener(new MyMouseListener());
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });

        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setVerifyInputWhenFocusTarget(true);
        cancelJButton.setMnemonic('C');
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        cancelJButton.setText("ȡ��(C)");
        System.out.println("��·��11 comp=="+comp);
        //CCBegin SS1
        Class[] c = { String.class, String.class };
        Object[] obj = { "��֯����-bsx", "�������" };
        CodingClassificationIfc cc = (CodingClassificationIfc) RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
        
        //makeSelectPanel = new CappSortingSelectedPanel("��λ", "consTechnicsRouteList", "routeListDepartment");
        if(comp.equals("zczx"))
        {
        	makeSelectPanel = new CappSortingSelectedPanel("��λ", "consTechnicsRouteList", "routeListDepartment");
        }
        //CCBegin SS2
        else if(comp.equals("cd"))
        {
        	Class[] c1 = { String.class, String.class };
        	Object[] obj1 = { "��֯����-cd", "�������" };
        	CodingClassificationIfc cc1 = (CodingClassificationIfc) RequestHelper.request("CodingManageService", "findClassificationByName", c1, obj1);
        System.out.println("cc1======"+cc1);
        	makeSelectPanel = new CappSortingSelectedPanel(cc1);
        	contructSelectPanel = new CappSortingSelectedPanel(cc1);
        }
        //CCEnd SS2
        else
        {
        	makeSelectPanel = new CappSortingSelectedPanel(cc);
        }
        //CCEnd SS1
        
        //add by guoxl on 2008.3.27(Ϊ��·�ߵ�λ���������쵥λ��������ť�������Ľ�����ӱ���)
        makeSelectPanel.setDialogTitle("���쵥λ");
        //add by guoxl end
        makeSelectPanel.setSelectBMnemonic('M');
        makeSelectPanel.setIsSelectCC(true);
        makeSelectPanel.transferFocus();
        
        //CCBegin SS1
        //contructSelectPanel = new CappSortingSelectedPanel("��λ", "consTechnicsRouteList", "routeListDepartment");
        if(comp.equals("zczx"))
        {
        	contructSelectPanel = new CappSortingSelectedPanel("��λ", "consTechnicsRouteList", "routeListDepartment");
        }
        //CCBegin SS2
        else if(comp.equals("cd"))
        {
        }
        //CCEnd SS2
        else
        {
        	contructSelectPanel = new CappSortingSelectedPanel(cc);
        }
        //CCEnd SS1
        
        //add by guoxl on 2008.3.27(Ϊ��·�ߵ�λ������װ�䵥λ��������ť�������Ľ�����ӱ���)
        contructSelectPanel.setDialogTitle("װ�䵥λ");
        //add by guoxl end
        contructSelectPanel.setIsSelectCC(true);

        jPanel3.setLayout(gridBagLayout2);

        partNoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partNoLabel.setText("������");
        partNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partNameLabel.setText("�������");
        serchPartNoButton.setMaximumSize(new Dimension(89, 23));
        serchPartNoButton.setMinimumSize(new Dimension(89, 23));
        serchPartNoButton.setPreferredSize(new Dimension(89, 23));
        serchPartNoButton.setMnemonic('P');
        serchPartNoButton.setSelected(false);
        serchPartNoButton.setText("����(P)...");
        serchPartNoButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
					serchPartNoButton_actionPerformed(e);
				} catch (QMRemoteException e1) {
					e1.printStackTrace();
					return;
				}
            }
        });
        exportButton.addActionListener(new java.awt.event.ActionListener(){
        
        
        public void actionPerformed(ActionEvent e)
        {
            try {
				exportButton_actionPerformed(e);
			} catch (QMRemoteException e1) {
				e1.printStackTrace();
				return;
			}
        }
        
        
        } );
        sourceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sourceLabel.setText("��Դ");
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeLabel.setText("����");
        partNoTextField.setEnabled(true);
        partNoTextField.setMinimumSize(new Dimension(220, 22));
        partNoTextField.setPreferredSize(new Dimension(220, 22));
        partNoTextField.setText("");
        
       
        partNameTextField.setEnabled(true);
        partNameTextField.setMinimumSize(new Dimension(220, 22));
        partNameTextField.setPreferredSize(new Dimension(220, 22));
        partNameTextField.setText("");
        
        sourceComboBox.setMinimumSize(new Dimension(220, 22));
        sourceComboBox.setPreferredSize(new Dimension(220, 22));
        typeComboBox.setMinimumSize(new Dimension(220, 22));
        typeComboBox.setPreferredSize(new Dimension(220, 22));

        sourceComboBox.setEnumeratedTypeClass(Class.forName("com.faw_qm.part.util.ProducedBy"));
        sourceComboBox.addItem("");
        sourceComboBox.setSelectedItem("");
        typeComboBox.setEnumeratedTypeClass(Class.forName("com.faw_qm.part.util.QMPartType"));
        typeComboBox.addItem("");
        typeComboBox.setSelectedItem("");
        panel1.setPreferredSize(new Dimension(411, 153));
        panel1.add(jPanel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(30, 0, 20, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
        jPanel3.add(partNoLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(partNoTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 8, 4, 10), 0, 0));
//        jPanel3.add(serchPartNoButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(partNameLabel,     new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 7), 0, 0));
        jPanel3.add(partNameTextField, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 8, 4, 10), 0, 0));
        
        
        //  jPanel3.add(sourceLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        //       ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0,
        // 0, 0), 0, 0));
        //jPanel3.add(sourceComboBox, new GridBagConstraints(1, 1, 2, 1, 1.0,
        // 0.0
        //       ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new
        // Insets(4, 8, 4, 0), 0, 0));
        //  jPanel3.add(typeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        //      ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0,
        // 0, 0), 0, 0));
        // jPanel3.add(typeComboBox, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
        //        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new
        // Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(makeSelectPanel, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 0), 0, 0));
        jPanel3.add(makeJLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 16, 6));
        jPanel3.add(stateLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 16, 6));
        jPanel3.add(contructSelectPanel , new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 10), 0, 0));
        jPanel3.add(constructJLabel, new GridBagConstraints(2, 3, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 10), 0, 0));

        comboBox = new JComboBox();
        jPanel3.add(comboBox, new GridBagConstraints(1, 4, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 5, 4, 0), 0, 0));

        jPanel2.add(okJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(jPanel4, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.ipady = 35;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 175;
        this.getContentPane().add(panel1, gridBagConstraints);
        
        initStateJComboBox();
    }
    /**
     * ����һ��������ר������Զ�̵��÷���˵ķ�����
     * @param component ��ʾ���ø÷����ĵ�ǰ������󣬿���Ϊ��
     * @param serviceName �����õķ���˷�������(ServiceName)
     * @param methodName �����õķ�����
     * @param paramTypes �����õķ����Ĳ������ͼ��ϣ���Ҫ���շ�����˳��
     * @param paramValues ����ĸ���������ֵ
     * @throws QMRemoteException �׳�Զ���쳣
     * @return �������
     */
    public static Object invokeRemoteMethodWithException(Component component, String serviceName, String methodName, Class[] paramTypes, Object[] paramValues)
    {
    	RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info = new ServiceRequestInfo();
        info.setServiceName(serviceName);
        info.setMethodName(methodName);
        //�������͵ļ���
        Class[] paraClass = paramTypes;
        info.setParaClasses(paraClass);
        //����ֵ�ļ���
        Object[] objs = paramValues;
        info.setParaValues(objs);
        Object result = null;
        try
        {
            result = RequestHelper.request(info);
        }catch(Exception ex)
        {
            //���쳣���д�������쳣��Ϣ�������ס�
            ex.printStackTrace();

        }
        //end try-catch
        return result;
    }
    /**
     * ��ʼ������·�߱�״̬��ѡ���ֵ
     */
    private void initStateJComboBox()
    {
        Class[] params = {String.class, String.class};
        Object[] values = {"״̬", "����·�߱�"};
        Collection result = null;
        try
        {
            result = (Collection)invokeRemoteMethodWithException(this, "CodingManageService", "findDirectClassificationByName", params, values);
            //System.out.println("�ҵ�״̬����  " + result.size());
        }catch(Exception ex)
        {
            //����쳣��Ϣ��
            String message = ex.getMessage();
            DialogFactory.showErrorDialog(this, message);
            return;
        }

        if(result != null && result.size() > 0)
        {
        	comboBox.addItem("");
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
            	comboBox.addItem((iterator.next()).toString());
            }
            comboBox.setSelectedItem("");
        }
    }
    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * ���ظ��෽����ʹ������ʾ����Ļ����
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * ִ�С�����������
     * @param e
     */
    private void okJButton_actionPerformed(ActionEvent e)
    {
        try
        {
        	 setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            confirmOperation();
            setCursor(Cursor.getDefaultCursor());
        }catch(Exception ex)
        {
            setCursor(Cursor.getDefaultCursor());
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this, message);
        }
    }

    /**
     * ִ��"ȡ��"����
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     * ������Ӧ�ü�������������Ȼ�������Ӧ ����˵ķ�����Ȼ�����ݿͻ��ѽ����ʾ ���û� ȷ������ִ���߼�
     * @throws QMRemoteException
     */
    public void confirmOperation()
    {
    	qMMultiList.clear();
    	Collection muResult;
        BaseValueIfc makeV = makeSelectPanel.getCoding();
        String partNum=this.partNoTextField.getText().trim();
        String partName=this.partNameTextField.getText().trim();
        String state=this.comboBox.getSelectedItem().toString();
       
        if(makeV != null)
        {
            this.makeDepartmentid = makeV.getBsoID();
        }else
            this.makeDepartmentid = "";
        BaseValueIfc constructV = this.contructSelectPanel.getCoding();
        if(constructV != null)
        {
            this.constructDepartmentid = constructV.getBsoID();
        }else
            this.constructDepartmentid = "";
        if((this.makeDepartmentid == null || this.makeDepartmentid.equals("")) && (this.constructDepartmentid == null || this.constructDepartmentid.equals("")))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, "��ѡ��·�ߵ�λ", title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(partNum == null || partNum.trim().equals(""))
        {
            int result = JOptionPane.showConfirmDialog(this, "����ʱ����ܻ�ܳ�����������", "��ʾ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch(result)
            {
            case JOptionPane.NO_OPTION:
            {
                return;
            }
            }

        }
        //�����ǵ��÷���ķ���
        //  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        HashMap hashmap = new HashMap();
        // System.out.println("makeDepartment" + this.makeDepartmentid);
        // System.out.println("constructDepartment" + this.constructDepartmentid);
        // System.out.println("productID" + this.productID);
        EnumeratedType sourceET = this.sourceComboBox.getSelectedEnumeratedType();
        if(sourceET != null)
        {
            // System.out.println("source=" + sourceET.toString());
            hashmap.put("source", sourceET.toString());
        }else
        {
            hashmap.put("source", null);
        }
        EnumeratedType typeET = this.typeComboBox.getSelectedEnumeratedType();
        if(typeET != null)
        {
            //  System.out.println("type=" + typeET.toString());
            hashmap.put("type", typeET.toString());
        }else
        {
            hashmap.put("type", null);
        }
//        hashmap.put("productID", this.productID);
//        hashmap.put("mDepartment", makeDepartmentid);
//        hashmap.put("cDepartment", constructDepartmentid);
        //��bsoID�������鿴·�߱�ҳ��
//        RichToThinUtil.toWebPage("route_look_partsListBy.screen", hashmap);
        //view.setCursor(Cursor.getDefaultCursor());
        Class[] params = {String.class, String.class,String.class,String.class,String.class};
        Object[] values = {makeDepartmentid,constructDepartmentid,partNum,partName,state};
        muResult = (Collection)invokeRemoteMethodWithException(this, "consTechnicsRouteService", "getAllPartsRoutes_new", params, values);
   
        if(muResult==null||muResult.size()==0){
        	
        	
        	 JOptionPane.showMessageDialog(this, "û���ҵ���Ӧ�Ľ����Ϣ��������������������!",
        			 "��ʾ", JOptionPane.INFORMATION_MESSAGE);
        	 return;
        }else{
            addInfoToList((Vector)muResult);
        }
        
        
       
        
    }

	private void addInfoToList(Vector v) {
		
		if (v != null && v.size() != 0) {
			for (int i = 0; i < v.size(); i++) {

				Vector v2 = (Vector) v.get(i);
				for (int j = 0; j < v2.size(); j++) {
					Object obj = (Object) v2.get(j);
					
					if(obj instanceof String ){
						
						String value=(String)obj;
						
						this.qMMultiList.addTextCell(i, j+1, value);
						
					} else if (obj instanceof Integer) {
						Integer value = (Integer) obj;

						this.qMMultiList
								.addTextCell(i, j + 1, value.toString());

					}
					
					
					
				}
			}
		}

	}
    void makeJTextField_actionPerformed(ActionEvent e)
    {

    }

    /**
     * <p>Title:��괦���¼�������</p> <p>Description: </p>
     */
    class MyMouseListener extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            Object obj = e.getSource();
            //��������ť
            if(obj instanceof JButton)
            {
                JButton button = (JButton)obj;

            }
            //�˵���
            /*
             * if (obj instanceof QMMenuItem) { QMMenuItem item = (QMMenuItem) obj; String s = item.getExplainText(); statusBar.setText(s); } //�˵� if (obj instanceof QMMenu) { QMMenu item = (QMMenu)
             * obj; statusBar.setText(item.getExplainText()); }
             */
        }

        public void mouseExited(MouseEvent e)
        {
        //����Ƴ�ʱ��״̬����ʾȱʡ��Ϣ
        //statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
        // "default_status", null));

        }
    }
    class FileFilter extends javax.swing.filechooser.FileFilter
    {

        /**
         * ����Excel�ļ�������
         */
        public FileFilter()
        {}

        /**
         * �ж�ָ�����ļ��Ƿ񱻱�����������
         * @param f �ļ�
         * @return ������ܣ��򷵻�true
         */
        public boolean accept(File f)
        {
            boolean accept = f.isDirectory();
            if(!accept)
            {
                String suffix = getSuffix(f);
                if(suffix != null)
                {
                    accept = suffix.equals("csv");
                }

            }
            return accept;
        }

        /**
         * ��ñ���������������Ϣ
         * @return Excel Files(*.csv)
         */
        public String getDescription()
        {
            return "Excel Files(*.csv)";
        }

        /**
         * ���ָ���ļ��ĺ�׺
         * @param f File
         * @return �ļ��ĺ�׺
         */
        private String getSuffix(File f)
        {
            String s = f.getPath(), suffix = null;
            int i = s.lastIndexOf('.');
            if(i > 0 && i < s.length() - 1)
                suffix = s.substring(i + 1).toLowerCase();
            return suffix;
        }
    }
    /**
     * ����excel
     * 
     */
     private void exportButton_actionPerformed(ActionEvent e)throws QMRemoteException{
	
    	 //�ļ�ѡ����
         JFileChooser chooser = new JFileChooser();
         //�����ļ�ѡȡģʽ���ļ���·��
         chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         //���ò��ɶ�ѡ
         chooser.setMultiSelectionEnabled(false);
         
         FileFilter filter = new FileFilter();
         
         chooser.setDialogTitle("���������...");
         chooser.setFileFilter(filter);
         //ɾ��ϵͳ�Դ���AcceptAllFileFilter
         chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());

         //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
         int state = chooser.showSaveDialog(this);// zz ָ���˸����� 2006-11
         //���ѡ����ļ�
         File selectedFile = chooser.getSelectedFile();
         //���ѡ������׼��ť,������ѡ���ļ���
         if(selectedFile != null && state == JFileChooser.APPROVE_OPTION)
         {
             //�ļ���ʽת��
             selectedFile = this.translateFile(selectedFile, filter);

             //�ж� 1 δ�����ļ���,�������ļ����� 2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
             if(!filter.accept(selectedFile))
             {
                 JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.PATH_NOT_EXIST, null), QMMessage.getLocalizedMessage(RESOURCE, "error", null),
                         JOptionPane.ERROR_MESSAGE);
                 return;
             }
             if(selectedFile.exists())
             {
                 JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.FILE_ALREADY_EXIST, null), QMMessage.getLocalizedMessage(RESOURCE, "warning", null),
                         JOptionPane.WARNING_MESSAGE);
                 return;
             }
            
             try
             {
            	 
            	 String tableTitle="������"+","+"�������"+","+"״̬"+","+"����"+","+"�汾"+","+"����·��"+","+"װ��·��"+","+"·�ߵ���"+","+"��������";
            	 
            	int rows= this.qMMultiList.getRowCount();
            	int colum= this.qMMultiList.getTable().getColumnCount();
            	 
                 BufferedWriter fw = new BufferedWriter(new FileWriter(selectedFile.getPath(), true));
                 fw.write(tableTitle+"\n");
                 for(int i=0;i<rows;i++){
                	 
                   for(int k=1;k<colum;k++){
                	  
                	  String value=this.qMMultiList.getCellText(i, k);
                	  if(k==colum-1){
                		 
                		  value=value.replaceAll(" ", "--");
                	  }
                	  fw.write(value+",");
                  }
                   fw.write("\n");
                 }
                 fw.flush();
                 fw.close();
             }catch(Exception ex)
             {
            	
                 String message = ex.getMessage();
                 DialogFactory.showWarningDialog(this, message);
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(this, "�������̳���!",
            			 "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                 return;
             }
             JOptionPane.showMessageDialog(this, "�����ɹ�!",
        			 "��ʾ", JOptionPane.INFORMATION_MESSAGE);
        	 
             this.setVisible(false);
         }
	
     }
     /**
      * ���fileû����չ���������չ��
      * @param file �ļ�
      * @param filter �ļ�������
      * @return ��ʽת������ļ�
      */
     private File translateFile(File file, FileFilter filter)
     {
         String path = file.getPath();
    if(filter instanceof FileFilter)
         {
             if(!path.endsWith(".csv"))
             {
                 path = path + ".csv";
             }
         }
         return new File(path);
     }
    /**
     * �����㲿�����
     * @param e
     * @throws QMRemoteException 
     */
    void serchPartNoButton_actionPerformed(ActionEvent e) throws QMRemoteException
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle", null);
        //����������
        CappChooser qmChooser = new CappChooser("QMPart", title, this);
        mySchema = new CappSchema(SCHEMA);
        //���ò�ѯ����
       
			qmChooser.setSchema(mySchema);
		
        qmChooser.setLastIteration(true);
        //qmChooser.setRelColWidth(new int[]{1,1});
        qmChooser.setChildQuery(false);
        qmChooser.setSplitDividerLocation(360);
        //���ո���������ִ������
        qmChooser.addListener(new CappQueryListener()
        {

            public void queryEvent(CappQueryEvent e)
            {
                qmChooser_queryEvent(e);
            }
        });
        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }

        qmChooser.setVisible(true);

    }

    /**
     * �����㲿�������¼�����
     * @param e ���������¼�
     */
    private void qmChooser_queryEvent(CappQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if(e.getType().equals(CappQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(CappQuery.OkCMD))
            {
                //��������������������������㲿��
                CappChooser c = (CappChooser)e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if(bvi != null)
                {
                    productID = bvi.getBsoID();
                    partNoTextField.setText(RParentJPanel.getIdentity(bvi));
                }
            }
            if(e.getCommand().equals(CappQuery.QuitCMD))
            {
                productID = "";
                partNoTextField.setText("");
            }
        }

        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

   

}
