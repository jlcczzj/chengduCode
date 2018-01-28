/**
 * ���ɳ��� RouteListTaskJPanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 2009/02/26  ������  ԭ���Ż��������·�߱���
 *  
 *                         ������1.������duplicate()����ȥ����ԭ����������ֱ�ӽ����������
 *                                 bsoid,createTime,modifyTime������Ϊnull��
 *                                 
 *                               2.��getRouteListLinkParts()�����ϲ���copyRouteList()�����У�
 *                                 ���ٶ�ͬһ�����ϵ�ѭ�������������־û������
 *                                 ��ѯ�ͱ��淽���滻Ϊ�����źš�
 *                               
 *                               3.����ʾ�������·�߱�ʱ�����ҹ������߼��ڼ�������з����ȥ,
 *                                 ֻ��ѡ��"�㲿��"TABҳʱ��ȥ�����.
 *                         
 *                         ��ע: ���ܲ�����������"�������·�߱�".  
 *                         
 *CR2  2009/05/14 ������    ԭ��TD���⣺�ڴ�����һ������·�߱��ѡ�񵽡����ԡ���
 *                               TABҳȻ����и��²������ڱ���ʱ�׳����쳣��Ϣ��
 *                               
 *                         �������ڸ���ģʽ�н���������ɾ������Ļ�����ա���������
 *                         
 *CR3  2009/05/14 ������   ԭ��:��Ʒ����ʱ����·�߱������볬��15�������ַ�ʱ�����쳣��Ϣ,������Ϊ
 *                              ���õײ��У���߼���û��һ�������ַ����������ֽ�.
 *                         ����:���ñ�ŵ����뷶Χ;
 *                              ��Ҫ���޸��Ѿ�ת��base;     
 *                              
 *CR4 2009/06/04  ������  �μ���������:v4r3FunctionTest;TD��2307
 *                                                 
 *CR5 2009/07/02  ��ѧ��  �μ���������:v4r3FunctionTest;TD��2498        
 *CR6 2011/12/14  �촺Ӣ   ԭ��folderר����ģ��μ�folderר�����˵��_л��_20091019.doc  
 *CR7 2011/07/07 ������   �μ���������:v4r3FunctionTest;TD��4733 
 * CR8 2011/07/08 ������   �μ���������:v4r3FunctionTest;TD��4709
 * CR9 2011/07/15 ������  �μ���������:v4r3FunctionTest;TD��4733
 * CR10 2011/12/28 ���� ԭ��Ϊ·����ṹ�йغ�·�߽��뱾���й�ʱ���ڲ�Ʒ���Ǳ�����
 * CR11 2012/01/04 �촺Ӣ  ԭ���޸ı�����׼��������ķ���
 * CR12 2012/03/31 ���� �μ�TD6000
 * CR13 20140303 ���� �μ�TD2636
 * SS1 2013-03-01 ������ ԭ��:���û�ѡ��֪ͨ��״̬Ϊ���ձϡ�ʱ��ϵͳ��Ϊ��֪ͨ��Ϊ�ձ�֪ͨ��
 * SS2 2013-11-26 ����ͮ ԭ��:��������·��ʱ����������Ĭ��ֵ��Ϊ����������׼֪ͨ�顱
 * SS3 3014-1-10 ����  ������������û��ж� 
 * SS4 2014-1-10 ���� ����������ؼ���Ĭ��һ��·��
 * SS5 2014-2-7 ���� �������·�߱�˵�������з�����λ 
 * SS6 2014-2-27 ���� ������İ��ձ��ƵĹ���׼��֪ͨ�����ͽ����Զ����
 * SS7 ��������������͹���·��Ĭ��˵����Ϣ liunan 2014-5-12
 * SS8 ��SS7�е����� ��������λ:�� ����Ϊ ��������λ���� liunan 2014-5-30
 * SS9 ���Ҫ���޸�·��˵����"������λ�� pante 2014-10-09
 * SS10 ����ٴ�Ҫ���޸�·��˵����"������λ�������������Ҹ������ƻ��ң���ȥ�����Ƴ��� pante 2014-11-06
 * SS11 ���Ҫ������������ڲ�Ʒ���� pante 2014-11-11
 * SS12 ����·�߱�˵����Ϣ liuyang 2014-8-21
 * SS13 ����ʱ����ѡ��·�߼������ liuyang 2014-8-27
 * SS14 �������ض���·��ʱ��ʾ����λ�� liuyang 2014-8-27
 * SS15 ���ظ��ݲ�ͬģʽ��·�߼���ˢ���㲿���� liuyang 2014-9-19
 * SS16 ����ȥ��������Ĭ���������� ���� 2014-12-22
 * SS17 �����޸�·�߱��30���ַ����ƣ����40���ַ� ���� 2015-3-13 ������Ϊ100 liunan 2016-11-14
 * SS18 �޸ĳ���·��ѡ��״̬�������·�߱�ŵ����� ���� 2015-3-24
 * SS19 �����½�·�ߣ�Ĭ��״̬Ϊ����׼�� ���� 2015-4-22
 * SS20 ��������Ĭ�Ϸ�����λ��Ϣ liuzhicheng 2015-12-17
 * SS21 2016-05-26 ������ ���������û��򴴽�����·��ʱ����������Ĭ��ֵ��Ϊ��������Ĺ���׼��֪ͨ���������ڡ�
 * SS22 �ɶ�����·�����ϣ��Զ���ţ����ڲ�Ʒ���Ĭ�����Ե��� liunan 2016-8-5
 * SS23 �ɶ��û���֤�Ƿ����ظ�����·�ߣ����ظ��Ĳ����� liuyuzhu 2016-10-12
 * SS24 ����SS7 SS8���ݣ���������������͹���·��Ĭ��˵����Ϣ liunan 2017-2-7
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.faw_qm.clients.beans.folderPanel.*;
import com.faw_qm.clients.beans.lifecycle.*;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.users.model.*;
import com.faw_qm.folder.model.*;
import com.faw_qm.lifecycle.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.cappclients.resource.view.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.cappclients.conscapproute.controller.*;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

/**
 * Title:ά��·�߱� Description: Copyright: Copyright (c) 2004 Company: һ������
 * @author ����
 * @mender skybird
 * @mender zz
 * @version 1.0 ������һ��zz 20060925 �����°������ ������� �����������ֹ��������������ʱ�ٴγ���������ʾ zz 20061106 �������
 */
public class RouteListTaskJPanel extends RParentJPanel
{
    private JTabbedPane jTabbedPane = new JTabbedPane();

    private JPanel jPanel1 = new JPanel();

    /***/
    private RouteListPartLinkJPanel partLinkJPanel;

    private JScrollPane jScrollPane1 = new JScrollPane();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel attriJPanel = new JPanel();

    private JLabel numberJLabel = new JLabel();

    private JTextField numberJTextField = new JTextField();

    private JLabel nameJLabel = new JLabel();

    private JTextField nameJTextField = new JTextField();

    private JLabel levelJLabel = new JLabel();

    private JLabel departmentJLabel = new JLabel();

    public JComboBox levelJComboBox = new JComboBox();

    //begin CR5
    private JLabel workstateJLabel = new JLabel();

    private JLabel workstateJLabel1 = new JLabel();
    //end CR5

    /** ��λѡ����� */
    public SortingSelectedPanel departmentSelectedPanel;

    private JPanel jPanel3 = new JPanel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel4 = new JPanel();

    private JLabel productJLabel = new JLabel();

    private JTextField productJTextField = new JTextField();

    private JButton browseJButton = new JButton();
    //    CCBegin SS11
    private JButton clearJButton = new JButton();
//    CCEnd SS11
    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private FolderPanel folderPanel = new FolderPanel();

    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();

    private JLabel descriJLabel = new JLabel();

    private JScrollPane jScrollPane2 = new JScrollPane();

    private JTextArea descriJTextArea = new JTextArea();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    private JPanel buttonJPanel = new JPanel();

    private JButton saveJButton = new JButton();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    private JLabel levelDisplayJLabel = new JLabel();

    private JLabel stateDisplayJLabel = new JLabel();

    private JLabel departmentDisplayJLabel = new JLabel();

    /** ������ʾģʽ������ģʽ����� */
    public final static int UPDATE_MODE = 0;

    /** ������ʾģʽ������ģʽ����� */
    public final static int CREATE_MODE = 1;

    /** ������ʾģʽ���鿴ģʽ����� */
    public final static int VIEW_MODE = 2;

    private final static int OKOPTION = 3;
    private final static int SAVE = 4;
    private final static int SAVAAFTERCANEL = 5;
    /** ����ģʽ--�鿴 */
    private int mode = VIEW_MODE;
    /** ҵ����� */
    private TechnicsRouteListIfc myRouteList;

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ����Ƿ�ִ���˱������ */
    protected boolean isSave = false;

    private JLabel jLabel1 = new JLabel();

    private JLabel versionJLabel = new JLabel();

    /** ����:��Ʒ��ʶ */
    private String productID = "";

    TextValidCheck textheck = new TextValidCheck("����·�߱�", 30);

    String routemanagermode = RemoteProperty.getProperty("routeManagerMode");
	//CCBegin SS3
    String comp ="";
  //CCEnd SS3
   //CCBegin SS13
    boolean flag=false;
    //CCEnd SS13
    /**
     * ���캯��
     * @roseuid 4031A737030E
     */
    public RouteListTaskJPanel()
    {
        try
        {
         	//CCBegin SS3
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
            //CCEnd SS3
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
    	
        partLinkJPanel = new RouteListPartLinkJPanel(this);
        this.setLayout(gridBagLayout5);
        this.setSize(new Dimension(500, 478));
        jPanel1.setLayout(borderLayout1);
        attriJPanel.setLayout(gridBagLayout3);
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setText("*���");
        numberJLabel.setBounds(new Rectangle(12, 14, 41, 18));
        numberJTextField.setMaximumSize(new Dimension(2147483647, 22));
        numberJTextField.setBounds(new Rectangle(65, 13, 63, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setText("*����");
        nameJLabel.setBounds(new Rectangle(15, 53, 41, 18));
        nameJTextField.setMaximumSize(new Dimension(2147483647, 22));
        nameJTextField.setBounds(new Rectangle(64, 50, 63, 22));
        //CCBegin SS4
		if (!comp.equals("zczx")) {
			levelJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			levelJLabel.setText("����");
			levelJLabel.setBounds(new Rectangle(232, 14, 41, 18));
		}
        //CCEnd SS4
        departmentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        departmentJLabel.setText("*��λ");
        //CCBegin SS4
		if (!comp.equals("zczx")) {
			levelJComboBox.setMaximumSize(new Dimension(50, 22));
			levelJComboBox.setMinimumSize(new Dimension(50, 22));
			levelJComboBox.setPreferredSize(new Dimension(50, 22));
			levelJComboBox.setBounds(new Rectangle(291, 10, 126, 22));
		}
        //CCEnd SS4
        //begin CR5
        workstateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workstateJLabel.setText("����״̬");
        workstateJLabel1.setText("sm");
        workstateJLabel1.setMaximumSize(new Dimension(200, 22));
        workstateJLabel1.setMinimumSize(new Dimension(200, 22));
        workstateJLabel1.setPreferredSize(new Dimension(200, 22));
        //end CR5

        departmentSelectedPanel = new SortingSelectedPanel("��λ", "TechnicsRouteList", "routeListDepartment");
        departmentSelectedPanel.setMaximumSize(new Dimension(91, 22));
        departmentSelectedPanel.setMinimumSize(new Dimension(91, 22));
        departmentSelectedPanel.setPreferredSize(new Dimension(91, 22));
        departmentSelectedPanel.setButtonSize(91, 23);
        departmentSelectedPanel.setDialogTitle("ѡ��λ");
        departmentSelectedPanel.setIsOnlyCC(true);
        departmentSelectedPanel.setIsSelectCC(true);
        jPanel3.setLayout(gridBagLayout1);
        jPanel4.setLayout(gridBagLayout2);
        productJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //CR10 begin
        if(routemanagermode.equals("parentRelative") || routemanagermode.equals("partRelative"))
        {
        	//CCBegin SS22
        	if(comp.equals("cd"))
        	{
        		productJLabel.setText("*���ڲ�Ʒ");
        	}
        	else
        	//CCEnd SS22
            productJLabel.setText("���ڲ�Ʒ");
        }else
        {
            productJLabel.setText("*���ڲ�Ʒ");
        }
        //CR10 end
        productJTextField.setMaximumSize(new Dimension(2147483647, 22));
        productJTextField.setEditable(false);
        browseJButton.setMaximumSize(new Dimension(91, 23));
        browseJButton.setMinimumSize(new Dimension(91, 23));
        browseJButton.setPreferredSize(new Dimension(91, 23));
        browseJButton.setMnemonic('R');
        browseJButton.setText("����(R). . .");
        browseJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                browseJButton_actionPerformed(e);
            }
        });
        //        CCBegin SS11
        clearJButton.setMaximumSize(new Dimension(91, 23));
        clearJButton.setMinimumSize(new Dimension(91, 23));
        clearJButton.setPreferredSize(new Dimension(91, 23));
        clearJButton.setText("���. . .");
        clearJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	productJTextField.setText("");
            	productID = "";
            	partLinkJPanel.setProductIfc(null);
            }
        });
//        CCEnd SS11
        descriJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriJLabel.setText("˵��");
        partLinkJPanel.setBorder(BorderFactory.createEtchedBorder());
        partLinkJPanel.setMaximumSize(new Dimension(338, 32767));
        partLinkJPanel.setMinimumSize(new Dimension(338, 10));
        partLinkJPanel.setPreferredSize(new Dimension(338, 10));
        attriJPanel.setBorder(null);
        attriJPanel.setMaximumSize(new Dimension(338, 2147483647));
        attriJPanel.setPreferredSize(new Dimension(338, 233));
        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane1.setMaximumSize(new Dimension(338, 32767));
        jScrollPane1.setMinimumSize(new Dimension(338, 24));
        jScrollPane1.setPreferredSize(new Dimension(338, 253));
        jPanel1.setMaximumSize(new Dimension(338, 2147483647));
        jTabbedPane.setMaximumSize(new Dimension(343, 32767));
        buttonJPanel.setLayout(gridBagLayout4);
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setActionCommand("SAVEROUTELIST");
        saveJButton.setMnemonic('S');
        saveJButton.setText("����(S)");
        saveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveJButton_actionPerformed(e);
            }
        });
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OKROUTELIST");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
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
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        //CCBegin SS1
        stateJComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	stateJComboBox_actionPerformed(e);
            }
        });
        //CCEnd SS1
        buttonJPanel.setMaximumSize(new Dimension(211, 23));
        departmentDisplayJLabel.setMaximumSize(new Dimension(41, 22));
        departmentDisplayJLabel.setMinimumSize(new Dimension(41, 22));
        departmentDisplayJLabel.setPreferredSize(new Dimension(41, 22));
        departmentDisplayJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        levelDisplayJLabel.setMaximumSize(new Dimension(41, 22));
        levelDisplayJLabel.setMinimumSize(new Dimension(41, 22));
        levelDisplayJLabel.setPreferredSize(new Dimension(41, 22));
        levelDisplayJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //st
        stateDisplayJLabel.setMaximumSize(new Dimension(41, 22));
        stateDisplayJLabel.setMinimumSize(new Dimension(41, 22));
        stateDisplayJLabel.setPreferredSize(new Dimension(41, 22));
        stateDisplayJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //ed
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("�汾");
        versionJLabel.setText("A.1");
        jLabel2.setText("*���ϼ�");
        locationJLabel.setMaximumSize(new Dimension(0, 22));
        locationJLabel.setMinimumSize(new Dimension(0, 22));
        locationJLabel.setPreferredSize(new Dimension(0, 22));
        lifeCycleInfo.getLifeCyclePanel().setBrowseButtonSize(new Dimension(83, 23));
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new Dimension(83, 23));
        lifeCycleInfo.setBsoName("consTechnicsRouteList");
        lifeCycleInfo.getProjectPanel().setButtonMnemonic('P');
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new Dimension(91, 23));
        stateLabel.setText("״̬");
        buttonJPanel.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel4.add(browseJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 8, 2, 0), 0, 0));
        //        CCBegin SS11
        if(comp.equals("zczx")){
        	jPanel4.add(clearJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 8, 2, 0), 0, 0));
        }
//        CCEnd SS11
        if(routemanagermode.equals("parentRelative") || routemanagermode.equals("partRelative"))
        {
            jPanel4.add(productJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 4, 2, 0), 0, 0));
        }else
        {
            jPanel4.add(productJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
        }
        jPanel4.add(productJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 0), 0, 0));
        attriJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 10, 4, 8), 0, 10));
        //begin CR5
        attriJPanel.add(workstateJLabel, new GridBagConstraints(0, 4, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 15, 4, 10), 0, 0));
        attriJPanel.add(workstateJLabel1, new GridBagConstraints(1, 4, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 8, 4, 0), 0, 0));
        //end CR5

        attriJPanel.add(descriJLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 40, 0, 0), 0, 0));
        attriJPanel.add(jScrollPane2, new GridBagConstraints(1, 5, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 8, 10, 10), 0, 0));
        // jScrollPane2.setHorizontalScrollBarPolicy( jScrollPane2.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.getViewport().add(descriJTextArea, null);
        //  descriJTextArea.setColumns(128);
        descriJTextArea.setLineWrap(true);
        attriJPanel.add(folderPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 7, 4, 10), 0, 0));
        attriJPanel.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        attriJPanel.add(locationJLabel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 10), 0, 0));
        this.add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        folderPanel.removeLabel();
        folderPanel.setButtonMnemonic('O');
        folderPanel.setButtonSize(91, 23);
        jTabbedPane.add(jPanel1, "����");
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jTabbedPane.add(partLinkJPanel, "�㲿����");

        //        //Begin CR1
        //        class WorkThread extends Thread
        //        {
        //            public void run()
        //            {
        //                //20120113 xucy modify �鿴��׼������Ϣ����ӽ�����
        ////                ProgressDialog d = new ProgressDialog(getParentJFrame());
        ////                d.setMessage("���ڴ������ݣ����Ժ�...");
        ////                d.startProcess();
        //                partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
        //                //d.stopProcess();
        //            }
        //        }
        //        jTabbedPane.addChangeListener(new ChangeListener()
        //        {
        //
        //            public void stateChanged(ChangeEvent cevent)
        //            {
        //                //CR9 Begin
        //                partLinkJPanel.setIschangFlag(true);
        //                //CR9 End
        //                if(jTabbedPane.getSelectedIndex() == 1 && mode == VIEW_MODE)
        //                {//CR7
        //
        //                    WorkThread t = new WorkThread();
        //                    t.start();
        //                }
        //            }
        //
        //        });
        //        //End CR1   

        jScrollPane1.getViewport().add(attriJPanel, null);

        jPanel3.add(numberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(numberJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJComboBox, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 18, 0, 0), 0, 0));
        jPanel3.add(levelDisplayJLabel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentDisplayJLabel, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentJLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 18, 0, 0), 0, 0));
        jPanel3.add(departmentSelectedPanel, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 0, 2, 0), 0, 0));
        jPanel3.add(versionJLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 2, 0), 0, 0));
        jPanel3.add(stateJComboBox, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateDisplayJLabel, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        
        attriJPanel.add(jPanel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 34, 0, 10), 0, 0));
        attriJPanel.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 10, 4, 10), 0, 0));
        this.add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));

        departmentSelectedPanel.setVisible(false);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        localize();

    }

    /**
     * ���ػ�
     */
    private void localize()
    {
        RouteListLevelType[] levelTypes = RouteListLevelType.getRouteListLevelTypeSet();
        for(int i = 0;i < levelTypes.length;i++)
        {
            levelJComboBox.addItem(levelTypes[i].getDisplay());
        }
        levelJComboBox.setSelectedItem(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        levelJComboBox.addActionListener(new RouteListTaskJPanel_levelJComboBox_actionAdapter(this));
        initStateJComboBox();
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
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
            	 //CCBegin SS19
            	String stateStr = (iterator.next()).toString();
                stateJComboBox.addItem(stateStr);
               
                if(comp.equals("ct")){
                	if(stateStr.equals("��׼")){
                		stateJComboBox.setSelectedItem(stateStr)	;
                	}
                }
           
            }
           
            if(!comp.equals("ct")){
            	   stateJComboBox.setSelectedIndex(0);
            }
            //CCEnd SS19
         
        }
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
     * ����ҵ�����
     * @param routelist ����·�߱����
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist)
    {
        myRouteList = routelist;
        //CCBegin SS15
        partLinkJPanel.routeListInfo=routelist;
        //CCEnd SS15
    }

    /**
     * ���ҵ�����
     * @return ����·�߱����
     */
    public TechnicsRouteListIfc getTechnicsRouteList()
    {
        return myRouteList;
    }

    /**
     * ����Ĭ����ʾ�½�ָ����Ʒ��·�߱����(��ʱ���ñ�����)
     * @param product ָ����Ʒ
     */
    public void setDefaultProductView(QMPartMasterIfc product)
    {
        this.setViewMode(CREATE_MODE);
        productJTextField.setText(getIdentity(product));
        productID = product.getBsoID();
    }

    /**
     * ���ý���ģʽ�����������»�鿴����
     * @param aMode �½���ģʽ
     */
    public void setViewMode(int aMode)
    {
        if((aMode == UPDATE_MODE) || (aMode == CREATE_MODE) || (aMode == VIEW_MODE))
        {
            mode = aMode;
        }

        switch(aMode)
        {

        case CREATE_MODE: //����ģʽ
        {
            this.setCreateModel();
            break;
        }

        case UPDATE_MODE: //����ģʽ
        {
            this.setUpdateModel();
            break;
        }

        case VIEW_MODE: //�鿴ģʽ
        {
            this.setViewDisplayModel();
            break;
        }
        }
    }

    /**
     * ��õ�ǰ����ģʽ
     * @return ��ǰ����ģʽ
     */
    public int getViewMode()
    {
        return mode;
    }

    /**
     * ���ý���Ϊ�½�ģʽ
     */
    private void setCreateModel()
    {
        //CCBegin SS13
    	if(comp.equals("ct")){
    		SelectLevelRouteJDialog p=new SelectLevelRouteJDialog(this
					.getParentJFrame(), this);
    		p.setVisible(true);		  
			partLinkJPanel.routeListInfo=null;
   		    partLinkJPanel.setMultilistInforms(); 
   		   
    	}   
    	//CCEnd SS13
        // CR13 Begin
        //((CappRouteListManageJFrame)this.getParentJFrame()).getTreePanel().clearSelection();
        // CR13 end
        isSave = false;  
        // CR12
   
        departmentSelectedPanel.setCoding(null);
  
        //begin 20120109 xucy add  ����ģʽ�£�����༭�㲿����   
        //jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.setEditModel();
        partLinkJPanel.setViewMode(CREATE_MODE);
        //end 20120109 xucy add  ����ģʽ�£�����༭�㲿����
        jTabbedPane.setSelectedIndex(0);
        levelDisplayJLabel.setVisible(false);
        stateDisplayJLabel.setVisible(false);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelJComboBox.setVisible(false);
        }else{ 
        	levelJComboBox.setVisible(true);          
        }
        //CCEnd SS4
//        System.out.println("pppppppppppp=====" + RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
       //CCBegin SS13
        if(!comp.equals("ct")){
            //CCEnd SS13
         levelJComboBox.setSelectedItem(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        }


             //20120113 xucy add
        partLinkJPanel.setRouteLevel(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        stateJComboBox.setVisible(true);
        this.setTextFieldVisible(numberJTextField);
        //CCBegin SS6
        if(comp.equals("zczx")){
        	
        	java.util.Date date = new java.util.Date();
        	String year = Integer.toString(date.getYear()+1900);
        	 Class[] paraClass={String.class,int.class};
        	 Component com=null;
        	if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
        		Object[] aa={"��׼-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);		
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("����")){
        		Object[] aa={"����׼-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("ǰ׼")){
        		Object[] aa={"ǰ׼-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
        		Object[] aa={"��׼-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("�ձ�")){
        		Object[] aa={"�ձ�-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("�շ�")){
        		Object[] aa={"�շ�-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
     
        }
        //CCBegin SS22
        else if(comp.equals("cd"))
        {
        	numberJTextField.setText("�Զ����");
        }
        //CCEnd SS22
        else{
           numberJTextField.setText("");
        }
        //CCEnd SS6
        this.setTextFieldVisible(nameJTextField);
        nameJTextField.setText("");
        this.setTextFieldVisible(productJTextField); 
        productJTextField.setText("");
        productJTextField.setEditable(false);
        descriJTextArea.setEditable(true);
      //CCBegin by wanghonglian 2008-06-04
        //����˵���Ի����е�������ʾ
        //descriJTextArea.setText("");
        //CCBegin SS7
        /*if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
          descriJTextArea.setText(
              "���ݣ�PDM��������˵����     ������׼��������׼����\n˵����·�ߴ���\n������λ�����������ɹ������ʱ��������Ʋ�����������������װ�䣩"); //modify for cq by liunan 2007.08.08
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("����׼")){
          descriJTextArea.setText(
              "���ݣ�PDM��������˵����     ��������׼������������׼����\n˵����·�ߴ���\n������λ�����������ɹ������ʱ��������Ʋ�����������������װ�䣩");
        }else{
          descriJTextArea.setText(
              "���ݣ�PDM��������˵����     ������׼��������׼����\n˵����·�ߴ���\n������λ�����������ɹ������ʱ��������Ʋ�����������������װ�䣩");
        }*/
        //CCBegin SS8 SS24
        if(stateJComboBox.getSelectedItem().toString().equals("ǰ׼")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��       ����ǰ׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��       ������׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��       ������׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("����")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��                    ��������׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("�ձ�")){
          descriJTextArea.setText(
              "����PDM��������˵����        ����׼     ������������׼�����,��Ͷ��������\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("�շ�")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��             ���������㲿����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }else{
          descriJTextArea.setText("");
        }
        //CCEnd SS8 SS24
        //CCEnd SS7
        //CCEnd by wanghonglian 2008-06-04
        //CCBeign SS5
        if(comp.equals("zczx")){
        	//CCBegin SS9
//        	descriJTextArea.setText("���ݽ�Ź�˾�滮��չ��һ��·���ļ���        �����ƴ˶���·�ߡ�\n���ݣ�PDM��������˵����     ������׼��������׼����\n˵����                   \n·�ߴ��룺\n������λ���滮��չ���������ƻ������ɹ�����������֤�������첿������");
        	//CCBegin SS10
        	//descriJTextArea.setText("���ݽ�Ź�˾�滮��չ��һ��·���ļ���        �����ƴ˶���·�ߡ�\n���ݣ�PDM��������˵����     ������׼��������׼����\n˵����                   \n·�ߴ��룺\n������λ��������չ�ҡ���Դ�����ҡ����������ҡ�������֤�ҡ����켼���ҡ����һ���䡢��ݶ����䡢�ȴ����䡢���Ƴ���");
        	descriJTextArea.setText("���ݽ�Ź�˾�滮��չ��һ��·���ļ���        �����ƴ˶���·�ߡ�\n���ݣ�PDM��������˵����     ������׼��������׼����\n˵����                   \n·�ߴ��룺\n������λ��������չ�ҡ���Դ�����ҡ������ƻ��ҡ�������֤�ҡ����켼���ҡ����һ���䡢��ݶ����䡢�ȴ�����");
//        CCEnd SS10
//        CCEnd SS9
        }
        //CCEnd SS5
        //CCBegin SS12 SS20
        if(comp.equals("ct")){
        	if(flag){
        		descriJTextArea.setText("����װ���嵥  ��һ��·��  ������׼��������׼��\n������λ��ר������ר�������ǻ�е���ǣ��󣩡����ݵ��ߡ���̩�Ǳ�");
        	}else{
        		descriJTextArea.setText("���ݣ�PDM��������˵����     ������׼��������׼��\n������λ��ר������ר�������ǻ�е���ǣ��󣩡����ݵ��ߡ���̩�Ǳ�");	
        	}
        }
       
        //CCend SS12   SS20
        //CCBegin SS22
        if(comp.equals("cd"))
        {
        	descriJTextArea.setText("���ݣ���������PDM��������˵����/��������֪ͨ��/��Ʒ���ü�����֪ͨ�鼰����׼��������׼����\n˵����\n������λ��");	
        }
        //CCEnd SS22
        browseJButton.setVisible(true);
//      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(true);
        }
//        CCEnd SS11
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(false);
        workstateJLabel1.setText(null);
        //end CR5
        //begin 20120110 xucy add ����ģʽ����չ�������
        this.partLinkJPanel.clearHashMap();
        this.partLinkJPanel.cleanMutlist();
        //end 20120110 xucy add
        TechnicsRouteListInfo routeList = new TechnicsRouteListInfo();
        this.setTechnicsRouteList(routeList);
        //20120105 xucy  add
        partLinkJPanel.setProductIfc(null);
        this.productID = "";
        //20120106 xucy add
        partLinkJPanel.getBranchMap().clear();
        versionJLabel.setText("A.1");
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
        lifeCycleInfo.getProjectPanel().setObject(null);
        //CCBegin SS16
        if(comp.equals("ct")){
        	String ctRoutelistLevel = levelDisplayJLabel.getText();	
        	if(this.flag){
        		 lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("���ض���·�߹���׼��֪ͨ����������");
        	}else{
        		 lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("����һ��·�߹���׼��֪ͨ����������");
        	}
        	 //CCBegin SS21
        }else if(comp.equals("zczx")){
        	lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("������Ĺ���׼��֪ͨ����������");//CCEnd SS21
        }
        //CCBegin SS22
        else if(comp.equals("cd"))
        {
        	lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("�ɶ�����׼��֪ͨ����������");
        }
        //CCEnd SS22
        else{
        
        	//SS2 BEGIN
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("��������׼֪ͨ��");
         	//SS2 END
        }
        //CCEnd SS16
        
     
        //lifeCycleInfo.setObject((LifeCycleManagedIfc)this.getTechnicsRouteList());
        locationJLabel.setVisible(false);
        folderPanel.setVisible(true);
        folderPanel.setSelectModel(true);
        folderPanel.setButtonSize(91, 23);
        //CCBegin SS14
       
        if(flag){
			 departmentSelectedPanel.setVisible(true);
	         departmentJLabel.setVisible(true);
	         departmentSelectedPanel.setViewTextField("");

		 } 
        //CCEnd SS14
        try
        {
            folderPanel.setLabelText(this.getPersionalFolder());
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setUpdateModel()
    {
        isSave = false;
        //jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.clearHashMap();
        partLinkJPanel.setEditModel();
        partLinkJPanel.setViewMode(UPDATE_MODE);
        //CCBegin SS15
        partLinkJPanel.setMultilistInforms(); 
        //CCEnd SS15
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        productJTextField.setEditable(false);
        levelJComboBox.setVisible(false);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelDisplayJLabel.setVisible(false);
        }else{ 
        	levelDisplayJLabel.setVisible(true);
        }
        //CCEnd SS4
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //ed
        descriJTextArea.setEditable(true);
        browseJButton.setVisible(false);
        //      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(false);
        }
//      CCEnd SS11
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        setButtonWhenSave(true);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try
        {
            numberJTextField.setText(this.getTechnicsRouteList().getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList().getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListLevel());
            stateDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListState());
            versionJLabel.setText(this.getTechnicsRouteList().getVersionValue());
            // partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());//CR1
            //this.partLinkJPanel.getAddedPartLinks().clear();//Begin CR2
            //this.partLinkJPanel.getDeletedPartLinks().clear();//End CR2

            //CR9 Begin
            this.partLinkJPanel.cleanMutlist();
            String productID = this.getTechnicsRouteList().getProductMasterID();
            if(productID == null || productID.equals(""))
            {
                productJTextField.setText("");
                partLinkJPanel.setProductIfc(null);
            }else
            {
                this.productID = productID;
                QMPartMasterInfo partinfo = null;
                partinfo = (QMPartMasterInfo)refreshInfo(productID);
                productJTextField.setText(getIdentity(partinfo));
                //20120105 xucy add
                //QMPartIfc product = ()refreshInfo(getTechnicsRouteList().getProductMasterID());
                partLinkJPanel.setProductIfc(partinfo);
            }
            this.partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
            //CR9 End
            //�ж��Ƿ���ʾ��λ
            String department = ((TechnicsRouteListInfo)this.getTechnicsRouteList()).getDepartmentName();
            if(department != null && !department.equals(""))
            {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            }else
            {
                departmentJLabel.setVisible(false);
                departmentDisplayJLabel.setVisible(false);
            }
            //folderPanel.setSelectModel(false);
            //folderPanel.getFolderPanelLabel();
            //folderPanel.setLabelText(this.getTechnicsRouteList().getLocation());
            folderPanel.setVisible(false);
            locationJLabel.setText(this.getTechnicsRouteList().getLocation());
            locationJLabel.setVisible(true);
            //lifeCycleInfo.setObject((LifeCycleManagedIfc)getTechnicsRouteList());

            // modify by guoxl on 20080214(����·�߱�ʱ�������ں���Ŀ�����Ϣ����ʾ)
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            lifeCycleInfo.getProjectPanel().setObject((LifeCycleManagedIfc)getTechnicsRouteList());

            //modify by guoxl end

            //lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
           

            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
			workstateJLabel1.setText(str);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setListUpdateModel()
    {
        isSave = false;
        //jTabbedPane.setEnabledAt(1, true);
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        productJTextField.setEditable(false);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelDisplayJLabel.setVisible(false);
        }else{ 
        	levelDisplayJLabel.setVisible(true);
        }
        //CCEnd SS4
        //ed
        descriJTextArea.setEditable(true);
        browseJButton.setVisible(false);
        //      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(false);
        }
//      CCEnd SS11
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        setButtonWhenSave(true);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try
        {
            numberJTextField.setText(this.getTechnicsRouteList().getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList().getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListLevel());
            stateDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListState());
            versionJLabel.setText(this.getTechnicsRouteList().getVersionValue());
            // partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());//CR1
            //this.partLinkJPanel.getAddedPartLinks().clear();//Begin CR2
            //this.partLinkJPanel.getDeletedPartLinks().clear();//End CR2
            //CR9 Begin
            //this.partLinkJPanel.cleanMutlist();
            //this.partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
            //CR9 End
            //�ж��Ƿ���ʾ��λ
            String department = ((TechnicsRouteListInfo)this.getTechnicsRouteList()).getDepartmentName();
            if(department != null && !department.equals(""))
            {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            }else
            {
                departmentJLabel.setVisible(false);
                departmentDisplayJLabel.setVisible(false);
            }
            //folderPanel.setSelectModel(false);
            //folderPanel.getFolderPanelLabel();
            //folderPanel.setLabelText(this.getTechnicsRouteList().getLocation());
            folderPanel.setVisible(false);
            locationJLabel.setText(this.getTechnicsRouteList().getLocation());
            locationJLabel.setVisible(true);
            //lifeCycleInfo.setObject((LifeCycleManagedIfc)getTechnicsRouteList());

            // modify by guoxl on 20080214(����·�߱�ʱ�������ں���Ŀ�����Ϣ����ʾ)
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            lifeCycleInfo.getProjectPanel().setObject((LifeCycleManagedIfc)getTechnicsRouteList());
            //modify by guoxl end

            //lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            String productID = this.getTechnicsRouteList().getProductMasterID();
            if(productID == null || productID.equals(""))
            {
                productJTextField.setText("");
                //partLinkJPanel.setProductIfc(null);
            }else
            {
                this.productID = productID;
                QMPartMasterInfo partinfo = null;
                partinfo = (QMPartMasterInfo)refreshInfo(productID);
                productJTextField.setText(getIdentity(partinfo));
                //20120105 xucy add
                //QMPartIfc product = ()refreshInfo(getTechnicsRouteList().getProductMasterID());
                //partLinkJPanel.setProductIfc(partinfo);
            }

            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
			workstateJLabel1.setText(str);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * ���ý���Ϊ�鿴ģʽ
     */
    public void setViewDisplayModel()
    {
        jTabbedPane.setSelectedIndex(0);//CR1
        //jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.setViewModel();
        //CCBegin SS15
        partLinkJPanel.setMultilistInforms(); 
        //CCEnd SS15
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelDisplayJLabel.setVisible(false);
        }else{ 
        	levelDisplayJLabel.setVisible(true);
        }
        //CCEnd SS4
        //ed
        browseJButton.setVisible(false);
        //      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(false);
        }
//      CCEnd SS11
        descriJTextArea.setEditable(false);
        saveJButton.setVisible(false);
        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try
        {
            numberJTextField.setText(this.getTechnicsRouteList().getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList().getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListLevel());
            //st skybird 2005.2.24
            stateDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListState());
            //ed
            versionJLabel.setText(this.getTechnicsRouteList().getVersionValue());
            descriJTextArea.setText(this.getTechnicsRouteList().getRouteListDescription());
            String productID = this.getTechnicsRouteList().getProductMasterID();
            if(productID == null || productID.equals(""))
            {
                productJTextField.setText("");
                partLinkJPanel.setProductIfc(null);
            }else
            {
                this.productID = productID;
                QMPartMasterInfo partinfo = null;
                partinfo = (QMPartMasterInfo)refreshInfo(productID);
                productJTextField.setText(getIdentity(partinfo));
                //20120105 xucy add
                //QMPartIfc product = partLinkJPanel.filterPart(getTechnicsRouteList().getProductMasterID());
                partLinkJPanel.setProductIfc(partinfo);
            }
            partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());
            //�ж��Ƿ���ʾ��λ
            String department = ((TechnicsRouteListInfo)this.getTechnicsRouteList()).getDepartmentName();
            if(department != null && !department.equals(""))
            {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            }else
            {
                departmentJLabel.setVisible(false);
                departmentDisplayJLabel.setVisible(false);
            }
            //folderPanel.setSelectModel(false);
            //folderPanel.getFolderPanelLabel();
            //folderPanel.setLabelText(this.getTechnicsRouteList().getLocation());
            folderPanel.setVisible(false);
            locationJLabel.setText(this.getTechnicsRouteList().getLocation());
            locationJLabel.setVisible(true);
            //lifeCycleInfo.setObject((LifeCycleManagedIfc)getTechnicsRouteList());
            if(verbose)
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + this.getTechnicsRouteList().getProjectId());
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            //modify by guoxl on 20080214(����·�߱�����ָ����Ŀ��ʱ���治��ˢ�¸��ĺ����Ŀ����Ϣ���ر���Ŀ��ָ��Ϊ��ʱ)
            //if (getTechnicsRouteList().getProjectId() != null)
            lifeCycleInfo.getProjectPanel().setObject((LifeCycleManagedIfc)getTechnicsRouteList());
            //else
            //lifeCycleInfo.getProjectPanel().setObject(null);
            //modify by guoxl end
           
            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            try
            {
                String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
                workstateJLabel1.setText(str);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            //end CR5
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * ��õ�ǰ�û��ĸ������ϼ�λ��
     * @return ��ǰ�û��ĸ������ϼ�λ��
     * @throws QMRemoteException
     */
    public String getPersionalFolder()
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() begin...");
        Class[] c = {};
        Object[] objs = {};
        UserInfo user=null;
		try {
			user = (UserInfo)RequestHelper.request("SessionService", "getCurUserInfo", c, objs);
		
        Class[] c1 = {UserInfo.class};
        Object[] objs1 = {user};
        SubFolderInfo folder = (SubFolderInfo)RequestHelper.request("FolderService", "getPersonalFolder", c1, objs1);
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() end...return: " + folder.getPath());
        return folder.getPath();
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		
		return null;
    }

    /**
     * ���ð�ָ���ĵ����ı�����ʾΪ��ǩ
     * @param textfield ָ���ĵ����ı���
     */
    private void setTextFieldToLabel(JTextField textfield)
    {
        textfield.setBorder(null);
        textfield.setBackground(SystemColor.control);
        textfield.setEditable(false);
    }

    /**
     * ���ð�ָ���ĵ����ı�����ʾΪ�ɱ༭
     * @param textfield ָ���ĵ����ı���
     */
    private void setTextFieldVisible(JTextField textfield)
    {
        textfield.setBorder(javax.swing.plaf.basic.BasicBorders.getTextFieldBorder());
        textfield.setBackground(Color.white);
        textfield.setEditable(true);
    }

    /**
     * ����Ĭ��Ϊ��һ����,��ʱ��λ�Ӵ���������ж�ȡ. ����ѡ��Ϊ��������ʱ����λ��ǩ�������б����ʾ������λ��ǩ�������б��ɼ�
     * @param e ItemEvent
     */
    void levelJComboBox_itemStateChanged(ItemEvent e)
    {

    }

    /**
     * ������������Ƿ�������Чֵ
     * @return �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkRequiredFields()
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() begin...");
        boolean isOK = false;
        String message = "fell through ";
        String title = "";
        System.out.println("check comp=="+comp);
        try
        {

            if((getViewMode() == CREATE_MODE))
            {
                //�������Ƿ�Ϊ��
                if(numberJTextField.getText().trim().length() == 0)
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_NUMBER_ENTERED, null);
                    this.numberJTextField.grabFocus();
                    isOK = false;
                }
                //���������Ƿ�Ϊ��
                else if(nameJTextField.getText().trim().length() == 0)
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_NAME_ENTERED, null);
                    this.nameJTextField.grabFocus();
                    isOK = false;
                }
                //�������ϼ��Ƿ�Ϊ��
                else if(checkFolderLocation() == null)
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_LOCATION_ENTERED, null);
                    this.folderPanel.grabFocus();
                    isOK = false;
                }
                //CR10 begin
                //����"���ڲ�Ʒ"�Ƿ�Ϊ��
                //CCBegin SS22
                //else if(productJTextField.getText().trim().length() == 0 && (routemanagermode.equals("productRelative") || routemanagermode.equals("productAndparentRelative")))
                else if(productJTextField.getText().trim().length() == 0 && (routemanagermode.equals("productRelative") || routemanagermode.equals("productAndparentRelative")||comp.equals("cd")))
                //CCEnd SS22
                {

                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_PRODUCT_ENTERED, null);
                    this.productJTextField.grabFocus();
                    isOK = false;

                    //CR10 end
                    //CR12 
                }else if(departmentSelectedPanel.getCoding() == null && this.levelJComboBox.getSelectedItem().toString().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_DEPARTMENT_ENTER, null);
                    departmentSelectedPanel.grabFocus();
                    isOK = false;
                }else
                {
                    isOK = true;
                }
            }else
            {
                isOK = true;
            }
            if(!isOK)
            {
                //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
                title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception qre)
        {
            //��ʾ��Ϣ����ָ�������ϼв��Ǹ������ϼ�
            String message1 = qre.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message1);
            this.folderPanel.grabFocus();
        }
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() end...return: " + isOK);
        return isOK;

    }

    /**
     * ����ı������ֵ��Ч��
     * @return boolean
     */
    private boolean check()
    {
        if(numberJTextField.getText().indexOf("*") != -1 || numberJTextField.getText().indexOf("%") != -1 || numberJTextField.getText().indexOf("?") != -1)
        {
            String message = "���" + "���зǷ��ַ�eg:*%?";
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.numberJTextField.grabFocus();
            return false;
        }

        if(nameJTextField.getText().indexOf("*") != -1 || nameJTextField.getText().indexOf("%") != -1 || nameJTextField.getText().indexOf("?") != -1)
        {
            String message = "����" + "���зǷ��ַ�eg:*%?";
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.nameJTextField.grabFocus();
            return false;
        }
        if(numberJTextField.getText().trim().getBytes().length > 30)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, "52", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.numberJTextField.grabFocus();
            return false;
        }
        if(this.nameJTextField.getText().trim().getBytes().length > 200)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, "53", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.nameJTextField.grabFocus();
            return false;
        }
        if(this.descriJTextArea.getText().trim().getBytes().length > 2000)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, "54", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.descriJTextArea.grabFocus();
            return false;
        }
        return true;
    }

    private boolean checkText()
    {
        try
        {
//CCBegin SS17
            textheck.setMax(100); //CR3
//CCEnd SS17
            textheck.check(numberJTextField, true);

        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            this.numberJTextField.grabFocus();
            return false;

        }
        try
        {
            textheck.setMax(200);
            textheck.check(nameJTextField, true);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            this.nameJTextField.grabFocus();
            return false;

        }

        try
        {
            textheck.setMax(2000);
            textheck.check(descriJTextArea, false);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            this.descriJTextArea.grabFocus();
            return false;

        }

        return true;
    }

    /**
     * �����Ƿ���ָ�����ϼ�
     * @return �����ָ�����ϼ�·�����򷵻����ϼС�
     * @throws QMException 
     * @throws QMRemoteException
     */
    private SubFolderInfo checkFolderLocation() throws QMException
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() begin...");
        String location = "";
        SubFolderInfo folderInfo = null;
        //������ϼ�·��
        location = folderPanel.getFolderLocation();

        if(location != null && location.length() != 0)
        {
            //�������ϼз��񣬸��ݻ�õ����ϼ�·��������ϼ�
            Class[] paraClass = {String.class};
            Object[] objs = {location};
            try
            {
                folderInfo = (SubFolderInfo)RequestHelper.request("FolderService", "getFolder", paraClass, objs);
            }catch(Exception ex)
            {
                String message = ex.getMessage();
                DialogFactory.showInformDialog(this.getParentJFrame(), message);
            }

        }

        if(folderInfo != null)
        {
            //            //�������ϼз����ж�ָ�����ļ����Ƿ��Ǹ����ļ���
            //            Class[] paraClass2 = {FolderIfc.class};
            //            Object[] objs2 = {folderInfo};
            Boolean flag1 = null;
            try
            {
                //flag1 = (Boolean)RequestHelper.request("FolderService", "isPersonalFolder", paraClass2, objs2);
                flag1 = folderInfo.isPersonalFolder();//CR6
            }catch(Exception ex)
            {
                //String title = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.LOCATION_NOT_VALID, null);
                String message =ex.getMessage();
                DialogFactory.showInformDialog(this.getParentJFrame(), message);
                this.folderPanel.grabFocus();

            }

            boolean flag = false;
            if(flag1 != null)
            {
                flag = flag1.booleanValue();
            }

            if(!flag)
            {
                //�׳��쳣��Ϣ����ָ�������ϼв��Ǹ����ļ���
                throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.LOCATION_NOT_PERSONAL_CABINET, null));
            }
        }

        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() end...return : " + folderInfo);
        return folderInfo;

    }

    /**
     * ִ�б������
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        isSave = true;
        commond = e.getActionCommand();
        //  processSaveCommond();
        WKThread work = new WKThread(SAVE);
        work.start();

    }

    private String commond = "";

    private JLabel jLabel2 = new JLabel();

    private JLabel locationJLabel = new JLabel();

    JLabel stateLabel = new JLabel();

    JComboBox stateJComboBox = new JComboBox();

    /**
     * ϵͳ����ҵ�����PHOS-CAPP-BR201���Ҫ��ǿյ������Ƿ�Ϊ��(E1)�� ����ҵ�����PHOS-CAPP-BR202��鹤��·�߱����Ƿ�Ψһ(E2)�� �����ǰΪ����ģʽ,��ϵͳ�������¹���·�߱����´����Ĺ���·�߱���Ϣ��ӵ�·�߱��������·�߱��б��У� �Ѵ�������ˢ��Ϊ���½���(���ѡ����"ȷ��",��ˢ��Ϊ�鿴ģʽ).
     * �����ǰΪ����ģʽ,����´�·�߱�,ˢ�����ڵ�,������ˢ��Ϊ�鿴ģʽ.
     */
    private void processSaveCommond()
    {
        ProgressDialog progressDialog = null;
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        boolean flag;

        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkRequiredFields();
        //������ݵ���Ч��
        //  flag = this.check();
        flag = this.checkText();
        if(!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        if(!flag)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //20120113 xucy add
        if(!partLinkJPanel.checkLinkAttrs())
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        ArrayList list = partLinkJPanel.getSaveRouteMap();
        //���·�ߵ�λ�Ƿ�Ϸ�
        if(!checkRouteDepartment(list))
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //��ʾ�������
        //        ProgressService.setProgressText(QMMessage.getLocalizedMessage(RESOURCE,
        //                CappRouteRB.SAVING, null));
        //        ProgressService.showProgress();
        //������һ��zz 20060925 �����°������
        progressDialog = new ProgressDialog(getParentJFrame());
        progressDialog.startProcess();

        try
        {
            /*
             * add by guoxl on 2008.4.3(����·�߱�ʱ��ʾ���������ڽ����У����Ժ����ԡ����쳣��Ϣ�� ԭ���� �ڱ���ʱûˢ�£�����ʱ�����һ��(�Ļ����᳧ʵʩ����))
             */
            if(this.getViewMode() == UPDATE_MODE)
            {
                try
                {
                    this.setTechnicsRouteList((TechnicsRouteListIfc)(refreshInfo(getTechnicsRouteList().getBsoID())));

                }catch(Exception ex)
                {

                    String message = ex.getMessage();
                    DialogFactory.showWarningDialog(this.getParentJFrame(), message);
                    CappRouteListManageJFrame f = (CappRouteListManageJFrame)this.getParentJFrame();
                    f.getTreePanel().removeNode(new RouteListTreeObject(getTechnicsRouteList()));
                    setCursor(Cursor.getDefaultCursor());
                    setButtonWhenSave(true);
                    this.setVisible(false);
                    return;
                }
            }
            //�ύ��������
            this.commitAllAttributes();         
            if(this.partLinkJPanel != null)
                getTechnicsRouteList().setPartIndex(partLinkJPanel.getPartIndex());
            if(verbose)
                System.out.println("����ǰ��˳��" + getTechnicsRouteList().getPartIndex());
         
            if(this.getViewMode() == CREATE_MODE)
            {
            	//CCBegin SS23
            	String userdesc = getUserFromCompany();
            	if(userdesc.equals("cd")){
            		boolean isSameNameList = searchSameNameList();
            		if(isSameNameList){
            			DialogFactory.showWarningDialog(this.getParentJFrame(), "ϵͳ������ͬ���Ƶ�·�߱����޸����ƺ��ٱ��棡");
            			progressDialog.stopProcess();
            			setCursor(Cursor.getDefaultCursor());
                        setButtonWhenSave(true);
            			return;
            		}
            	}
            	//CCEnd SS23
                //���÷��񣬱��湤��·�߱�
                Class[] paraClass = {TechnicsRouteListIfc.class};
                Object[] obj = {this.getTechnicsRouteList()};
                // �����������ֹ��������������ʱ�ٴγ���������ʾ zz 20061106
                isSave = true;
               
                //20120109 xucy add ������׼�������
                Class[] c = {TechnicsRouteListIfc.class, ArrayList.class};
                Object[] objs = {this.getTechnicsRouteList(), list};
                Object[] objaa = (Object[])RequestHelper.request("consTechnicsRouteService", "createLinksAndRoutes", c, objs);
                TechnicsRouteListIfc returnRouteList = (TechnicsRouteListIfc)objaa[0];
                //20120112 xucy add
                partLinkJPanel.clearPartLinks();
                //���´����Ĺ���·�߱���Ϣ��ӵ�·�߱��������·�߱��б���
                RouteTreePanel treePanel = ((CappRouteListManageJFrame)this.getParentJFrame()).getTreePanel();
                RouteListTreeObject newObj = new RouteListTreeObject(returnRouteList);
                treePanel.addNode(newObj);
                ((CappRouteListManageJFrame)getParentJFrame()).isViewRouteList = false;
                treePanel.setNodeSelected(newObj);
                ((CappRouteListManageJFrame)getParentJFrame()).isViewRouteList = true;
                //�Ѵ�������ˢ��Ϊ���½���
                //jTabbedPane.setEnabledAt(1, true);
                this.setTechnicsRouteList(returnRouteList);
                //20120113 xucy add ���ñ��·����Ϣ�͹���ID
                partLinkJPanel.setMultiListAttrs((ArrayList)objaa[1]);
                //Begin CR8
                if(commond.equals("SAVEROUTELIST"))
                {
                    partLinkJPanel.setEditModel();
                    partLinkJPanel.setViewMode(UPDATE_MODE);
                    mode = UPDATE_MODE;
                    //this.setViewMode(mode);
                    
                    //������׼Ϊ����ģʽ 20120405
                    setListUpdateModel();
                }else if(commond.equals("OKROUTELIST"))
                {
                    //������ˢ��Ϊ�鿴ģʽ
                    mode = VIEW_MODE;
                    this.setViewMode(mode);
                    //partLinkJPanel.setViewModel();
                    descriJTextArea.setEditable(false);
                    saveJButton.setVisible(false);
                    okJButton.setVisible(false);
                    cancelJButton.setVisible(false);

                }else
                {
                    mode = VIEW_MODE;
                    this.setViewMode(mode);
                    //partLinkJPanel.setViewModel();
                }
                //End CR8
               

            }else if(this.getViewMode() == UPDATE_MODE)
            {
                //begin CR11
                //����·�߱����㲿���Ĺ������䱣��·�߶���
                Class[] c = {HashMap.class, TechnicsRouteListIfc.class, ArrayList.class};
                Object[] objs = {partLinkJPanel.getDeletedPartLinks(), this.getTechnicsRouteList(), list};
                if(verbose)
                    System.out.println("ɾ������ ==" + partLinkJPanel.getDeletedPartLinks());
                if(verbose)
                    System.out.println("��Ӽ��� ==" + partLinkJPanel.getAddedPartLinks());
                //20120116 xucy add ���ӷ���ֵ
                Object[] obj = (Object[])RequestHelper.request("consTechnicsRouteService", "updateLinksAndRoutes", c, objs);
                //end CR11
                //   isSave = true;//CR4
                partLinkJPanel.clearPartLinks();
                //�����㲿���ĸ������ added by skybird 2005.3.4
                //c = new Class[]{Collection.class,TechnicsRouteListIfc.class};
                //                Class[] c1 = {String.class};
                //                String theBeforedBsoid = this.getTechnicsRouteList().getBsoID();
                //                Object[] obj1 = {theBeforedBsoid};
                //                if(verbose)
                //                    System.out.println("ԭ�е�·�߱�id" + theBeforedBsoid);
                TechnicsRouteListIfc technicsRouteIfc = (TechnicsRouteListIfc)obj[0];
                //theBeforedBsoid = technicsRouteIfc.getBsoID();
                if(verbose)
                    System.out.println("���º��partIndexs" + technicsRouteIfc.getPartIndex());
                this.setTechnicsRouteList(technicsRouteIfc);
                //20120113 xucy add �������Ҫ�������ý�����Ϣ���Ա��������ʱ��
                System.out.println("����1===="+((ArrayList)obj[1]).size());
                partLinkJPanel.setMultiListAttrs((ArrayList)obj[1]);
                //ˢ�����ڵ�
                ((CappRouteListManageJFrame)this.getParentJFrame()).getTreePanel().updateNode(new RouteListTreeObject(technicsRouteIfc));
                if(commond.equals("OKROUTELIST"))
                {
                    //������ˢ��Ϊ�鿴ģʽ
                    mode = VIEW_MODE;
                    this.setViewMode(mode);
                    partLinkJPanel.setViewModel();
                    descriJTextArea.setEditable(false);
                    saveJButton.setVisible(false);
                    okJButton.setVisible(false);
                    cancelJButton.setVisible(false);
                }
            }
        }catch(Exception ex)
        {
            progressDialog.stopProcess();
            ex.printStackTrace();
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message);
            isSave = false;
            //mode = CREATE_MODE;
            setCursor(Cursor.getDefaultCursor());
            //return;
        }
        //ProgressService.hideProgress();
        progressDialog.stopProcess();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
    }

    //CCBegin SS23
    /**
     * ����ϵͳ���Ƿ�����ͬ���Ƶ�·�߱�
     * @return
     * @throws QMException 
     */
    private boolean searchSameNameList() throws QMException {
    	//���÷��񣬲���ϵͳ���Ƿ�����ͬ���Ƶ�·�߱�
        Class[] paraClass = {TechnicsRouteListIfc.class};
        Object[] obj = {this.getTechnicsRouteList()};
        Class[] c = {TechnicsRouteListIfc.class};
        Object[] objs = {this.getTechnicsRouteList()};
        boolean objaa = (Boolean)RequestHelper.request("consTechnicsRouteService", "searchSameNameList", c, objs);
		return objaa;
	}
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	RequestServer server = RequestServerFactory.getRequestServer();;
    	String returnStr = "";
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         System.out.println("�û���===="+returnStr);
         return returnStr;
    }
    //CCEnd SS23

	/**
     * ���·�ߵ�λ�Ƿ�Ϸ�
     * @param list
     */
    private boolean checkRouteDepartment(ArrayList list)
    {

        //�����һ��·�ߣ�ֻ�ж�·�ߵ�λ�����ݿ����Ƿ���ڼ��ɣ�����Ƕ���·�ߵ�λҪ�ж�������ĵ�λ�Ƿ�Ϊ������λ���ӵ�λ
        //20120210 xucy add ���·�ߵ�λ�����ݿ��в����ڣ�������ʾ��Ϣ
        StringBuffer aa = new StringBuffer();
        for(int i = 0, j = list.size();i < j;i++)
        {
            RouteWrapData routeData = (RouteWrapData)list.get(i);
            String partInform = routeData.getPartNum() + "(" + routeData.getPartName() + ")";
            Object[] depVec = routeData.getDepartmentVec();
            if(depVec!=null)
            {
            aa.append("�㲿��" + partInform + "��" + "\n");
            
            String str = (String)depVec[0];
            if(str != null && !str.trim().equals(""))
                aa.append(str + "������"+"\n");
            if((String)depVec[1] != null && !((String)depVec[1]).trim().equals(""))
            {
                aa.append((String)depVec[1] + "���Ƕ���·�ߵ�λ���ӵ�λ"+"\n");
            }
            }
        }
        //��ʾ��Щ�������Щ·�ߵ�λ�����ڵĶԻ���
        if(aa.length() > 0)
        {
            MessageJDialog dia = new MessageJDialog(this.getParentJFrame());
            dia.setTextArea(aa.toString());
            dia.setVisible(true);
            return false;
        }
        return true;
    }

    /**
     * ִ��ȷ������
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        isSave = true;
        commond = e.getActionCommand();
        //  processSaveCommond();

        WKThread work = new WKThread(OKOPTION);
        work.start();

    }

    /**
     * �ύ·�߱�����������Թ�����
     * @throws TechnicsRouteException
     * @throws QMRemoteException
     */
    private void commitAllAttributes()
    {
        //˵��
        try  
        {
            this.getTechnicsRouteList().setRouteListDescription(descriJTextArea.getText());
            System.out.println("pppp===" + this.getViewMode());
            if(this.getViewMode() == CREATE_MODE)
            {
                //���.����
                this.getTechnicsRouteList().setRouteListNumber(numberJTextField.getText());
                this.getTechnicsRouteList().setRouteListName(nameJTextField.getText());
                //����
                this.getTechnicsRouteList().setRouteListLevel(levelJComboBox.getSelectedItem().toString());

                //״̬
                this.getTechnicsRouteList().setRouteListState(stateJComboBox.getSelectedItem().toString());
                //��λ
                //CR12 
                if(departmentSelectedPanel.getCoding()!=null)
                    this.getTechnicsRouteList().setRouteListDepartment(departmentSelectedPanel.getCoding().getBsoID());
                //���ڲ�Ʒ
                this.getTechnicsRouteList().setProductMasterID(this.productID);
                //�������ϼ�
                Class[] theClass = {FolderEntryIfc.class, String.class};
                Object[] objs = {this.getTechnicsRouteList(), folderPanel.getFolderLocation()};
                TechnicsRouteListInfo info = (TechnicsRouteListInfo)RequestHelper.request("FolderService", "assignFolder", theClass, objs);
                this.setTechnicsRouteList(info);
                //�����������ں���Ŀ��
                LifeCycleManagedIfc lcm = lifeCycleInfo.assign((LifeCycleManagedIfc)getTechnicsRouteList());
                this.setTechnicsRouteList((TechnicsRouteListInfo)lcm);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
        }
    }

    /**
     * ȡ������
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        processCancelCommond();
    }
  //CCBegin SS1
    /**
     * �����жϣ���ѡ��Ϊ���ձϡ���������������Ϊ���ձ�֪ͨ�顱��������������Ϊ����׼֪ͨ�顱��
     * @param e ActionEvent
     */
    void stateJComboBox_actionPerformed(ActionEvent e)
    {
    	 if(stateJComboBox.getSelectedItem().toString().equals("�ձ�")){
    			lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("�ձ�֪ͨ��");
    	 }else{
    		 lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("��׼֪ͨ��");
    	 }
         //CCBegin SS6
         if(comp.equals("zczx")){
         	java.util.Date date = new java.util.Date();
         	String year = Integer.toString(date.getYear()+1900);
         	 Class[] paraClass={String.class,int.class};
        	 Component com=null;
        		if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
            		Object[] aa={"��׼-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);		
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("����")){
            		Object[] aa={"����׼-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("ǰ׼")){
            		Object[] aa={"ǰ׼-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
            		Object[] aa={"��׼-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("�ձ�")){
            		Object[] aa={"�ձ�-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("�շ�")){
            		Object[] aa={"�շ�-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	       
         }
         //CCBegin SS18
         else if(comp.equals("ct")){
         	descriJTextArea.setText("");
         }
         //CCEnd SS18
         //CCBegin SS22
         else if(comp.equals("cd"))
         {
         	numberJTextField.setText("�Զ����");
         }
         //CCEnd SS22
         else{
            numberJTextField.setText("");
            
            //CCBegin SS7
            //CCBegin SS8 SS24
        if(stateJComboBox.getSelectedItem().toString().equals("ǰ׼")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��       ����ǰ׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��       ������׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("��׼")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��       ������׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("����")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��                    ��������׼��������׼����       ���׼����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("�ձ�")){
          descriJTextArea.setText(
              "����PDM��������˵����        ����׼     ������������׼�����,��Ͷ��������\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("�շ�")){
          descriJTextArea.setText(
              "����PDM��������˵����/��������֪ͨ��             ���������㲿����\n˵����·�ߴ��롰Э��Ϊ��Ź�˾�ɹ��������ɡ�Ϊ������ֹ�˾�����������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬���ᡱΪ������ģ�������ΪӪ���������ܡ�Ϊ��Ź�˾��������������Ϊ�ൺ������˾��������Ϊ���ع�˾����ר��Ϊ���طֹ�˾��������Ϊ�ɶ��ֹ�˾��\n������λ�����������ʱ��������������ƿز���������Դ��");
        }else{
          descriJTextArea.setText("");
        }
        //CCEnd SS8 SS24
         //CCEnd SS7   
         }
         //CCEnd SS6
    }
  //CCEnd SS1
    /**
     * �����ǰ����ģʽΪ����,�����δ����,����ʾ�û��Ƿ񱣴�;���������,���˳�����. �����ǰ����ģʽΪ����,�����δ��������ʾ�û��Ƿ񱣴�;���������,�򽫽���ˢ��Ϊ�鿴״̬
     */
    protected boolean processCancelCommond()
    {
        if(this.getViewMode() == CREATE_MODE)
        {
            if(!isSave)
                this.quitWhenCreate();
            else
                this.setVisible(false);
        }else if(this.getViewMode() == UPDATE_MODE)
        {
            if(!isSave)
                this.quitWhenUpdate();
            else
            {
                this.setViewMode(2);
                isSave = false;
            }

        }
        //System.out.println("isSave isSave isSave " + isSave);
        return isSave;
    }

    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���. �����ǰ����ģʽΪ����,�����δ����,����ʾ�û��Ƿ񱣴�;���������,���˳�����.
     */
    private void quitWhenCreate()
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.IS_SAVE_CREATE_ROUTELIST, null);
        if(this.confirmAction(s))
        {
            // this.processSaveCommond();
            //������һ��zz 20061107 �������������߳�
            //             WKThread work = new WKThread(SAVAAFTERCANEL);
            //             work.start();
            processSaveCommond();
            isSave = true;//add by guoxl for TD3642  on 2011/2/21
            //��ֹ��������������ʱ�ٴγ���������ʾ zz 20061106 start
            //isSave = true;     // end
        }else
        {
            //20120112 xucy add
            this.partLinkJPanel.getAddedPartLinks().clear();
            this.partLinkJPanel.clearPartLinks();
            this.setVisible(false);
            isSave = true;
        }
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() end...return is void ");

    }

    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���. �����ǰ����ģʽΪ����,�����δ��������ʾ�û��Ƿ񱣴�;���������,�򽫽���ˢ��Ϊ�鿴״̬
     */
    private void quitWhenUpdate()
    {
        //System.out.println(" routelisttaskjpanel 1440");
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.IS_SAVE_UPDATE_ROUTELIST, null);
        if(this.confirmAction(s))
        {
            this.processSaveCommond();
            isSave = true;
        }else
        {
            this.setViewMode(2);
            isSave = true;
        }
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() end...return is void");

    }

    /**
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        JOptionPane okCancelPane = new JOptionPane();
        return okCancelPane.showConfirmDialog(getParentJFrame(), s, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * ���������ڲ�Ʒ�����㲿��
     * @param e ActionEvent
     */
    void browseJButton_actionPerformed(ActionEvent e)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle", null);
        //����������
        QmChooser qmChooser = new QmChooser("QMPartMaster", title, this.getParentJFrame());
        qmChooser.setRelColWidth(new int[]{1, 1});
        qmChooser.setChildQuery(false);
        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        //���ո���������ִ������
        qmChooser.addListener(new QMQueryListener()
        {

            public void queryEvent(QMQueryEvent e)
            {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * �����㲿�������¼�����
     * @param e ���������¼�
     */
    private void qmChooser_queryEvent(QMQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //��������������������������㲿��
                QmChooser c = (QmChooser)e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if(bvi != null)
                {
                    productID = bvi.getBsoID();
                    productJTextField.setText(getIdentity(bvi));
                    //20120109 xucy add
                    partLinkJPanel.setProductIfc((QMPartMasterIfc)bvi);
                }
            }
        }
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

    /**
     * ���ð�ť����ʾ״̬����Ч��ʧЧ��
     * @param flag flagΪTrue����ť��Ч������ťʧЧ
     */
    private void setButtonWhenSave(boolean flag)
    {
        okJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }

    /**
     * ���ñ����Ϊ�ա�
     * @return ��ǰ�����Ƿ�ִ���˱�����������ִ���˱��棬�򷵻��档
     */
    public boolean setNullMode()
    {

        return isSave;
    }

    /**
     * 20120112 xucy add ��ȡ�������
     * @return
     */
    public RouteListPartLinkJPanel getPartLinkJPanel()
    {
        return this.partLinkJPanel;
    }

    /**
     * ���·�߼��� //20120113 xucy add
     */
    public String getRouteLevel()
    {
        return levelJComboBox.getSelectedItem().toString();
    }

    /**
     * ��ò���ID
     */
    public String getDepartmentID()
    {
        if(departmentSelectedPanel.getCoding() != null)
        {
            return departmentSelectedPanel.getCoding().getBsoID();
        }else
        {
            return "";
        }
    }

    /**
     * ����Combo��Ӧ�¼�
     * @param e
     */
    void levelJComboBox_actionPerformed(ActionEvent e)
    {
        partLinkJPanel.setRouteLevel(this.levelJComboBox.getSelectedItem().toString());
        if(this.levelJComboBox.getSelectedItem().toString().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            departmentSelectedPanel.setVisible(true);
            departmentJLabel.setVisible(true);
            if(this.mode == CREATE_MODE)
            {
                departmentSelectedPanel.setViewTextField("");
            }
        }else
        {
            departmentSelectedPanel.setVisible(false);
            departmentJLabel.setVisible(false);
        }
    }

    class WKThread extends QMThread
    {
        int myAction;

        public WKThread(int action)
        {
            super();
            this.myAction = action;
        }

        /**
         * WKTread���з���
         */
        public void run()
        {

            switch(myAction)
            {
            case SAVE:
                processSaveCommond();
                break;
            case OKOPTION:
                processSaveCommond();
                break;
            case SAVAAFTERCANEL:
                processSaveCommond();
                break;

            }
        }

    }

}

/**
 * <p>Title:����Combo�¼���Ӧ������</p> <p>Description: </p>
 */
class RouteListTaskJPanel_levelJComboBox_actionAdapter implements java.awt.event.ActionListener
{
    private RouteListTaskJPanel adaptee;

    RouteListTaskJPanel_levelJComboBox_actionAdapter(RouteListTaskJPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.levelJComboBox_actionPerformed(e);
    }
}
