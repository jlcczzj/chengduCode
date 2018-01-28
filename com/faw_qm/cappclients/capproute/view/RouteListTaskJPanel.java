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
 *
 * SS1 ���⣺����·�߲��������������������������ͱ༭·��ѡ��������������һ�¡�
 *     ԭ�򣺴��������û��л�·�߽��б༭ʱ����ֻ���л��������޸�û�н����޸��㲿����������ʱ���Ὣ�л���·���㲿������������
 *     �����ȥ��CR1��һ����ʼ�����ô����ע�ͼ��ɡ�ÿ�ν�����½��涼ˢ���㲿��������
 * liunan 2012-8-1 01
 * SS2 �޸�ǰ׼��˵����Ϣ�� liunan 2013-1-24
 * SS3 ���·���Ƿ��Զ���ȡ����·�ߵĸ�ѡ���ʶ��Ĭ��ѡ�У����Զ��������·�ߣ�Ȼ����·�߱༭�������û������Ƿ��޸ġ� liunan 2013-4-17
 * SS4 ��׼֪ͨ���������Ϊ�����պϼ����Ĺ���·�� liuyang 2014-6-3
 * SS5 ȡ���������ϼ� Liuyang 2014-6-3
 * SS6 ���Ϊ·�� liuyang 2014-6-9  
 * SS7 ǰ׼����׼�����ơ���׼4������֪ͨ��ȥ����׼�㲿���б���ԭ���İ�ť������֪ͨ������ӡ��͡����ý����ӡ���������ť������֪ͨ�顱�͡�����֪ͨ�顱 ��"���Ϊ"��ѡ�� liuyang 2014-6-18
 * SS8 ?
 * SS9 A004-2015-3109��׼�Զ��޸�˵�����ݡ� liunan 2015-5-6
 * SS10 ��Ӹ����� liunan 2015-6-18
 * SS11 ���������30��Ϊ50 liunan 2015-9-1
 * SS12 ��׼����ʱȥ�����ͷβ�Ķ���ո� liunan 2016-10-19
 * SS13 A004-2017-3580 �޸���׼˵�������� liunan 2017-7-6
 * SS14 A004-2017-3618 ������Ƶ�˵������ liunan 2017-11-17
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.faw_qm.clients.beans.folderPanel.*;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.users.model.*;
import com.faw_qm.folder.model.*;
import com.faw_qm.lifecycle.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.cappclients.capproute.util.*;
import com.faw_qm.cappclients.resource.view.*;
import com.faw_qm.technics.route.exception.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCBegin SS10
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//CCEnd SS10
/**
 * Title:ά��·�߱�
 * Description:
 * Copyright: Copyright (c) 2004
 * Company: һ������
 * @author ����
 * @mender skybird
* @mender zz
 * @version 1.0
* ������һ��zz 20060925 �����°������ �������
* �����������ֹ��������������ʱ�ٴγ���������ʾ zz 20061106 �������
 */
public class RouteListTaskJPanel extends RParentJPanel {
    private JTabbedPane jTabbedPane = new JTabbedPane();

    private JPanel jPanel1 = new JPanel();

    /***/
    private RouteListPartLinkJPanel partLinkJPanel = new RouteListPartLinkJPanel();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel attriJPanel = new JPanel();

    private JLabel numberJLabel = new JLabel();
    private JTextField numberJTextField = new JTextField();

    private JLabel nameJLabel = new JLabel();

    private JTextField nameJTextField = new JTextField();

    private JLabel levelJLabel = new JLabel();

    private JLabel departmentJLabel = new JLabel();

    private JComboBox levelJComboBox = new JComboBox();
    
    //begin CR5
    private JLabel workstateJLabel = new JLabel();
    
    private JLabel workstateJLabel1 = new JLabel();
    //end CR5

    /** ��λѡ����� */
    private SortingSelectedPanel departmentSelectedPanel;

    private JPanel jPanel3 = new JPanel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel4 = new JPanel();

    private JLabel productJLabel = new JLabel();

    private JTextField productJTextField = new JTextField();

    private JButton browseJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private FolderPanel folderPanel = new FolderPanel();

    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();

    private JLabel descriJLabel = new JLabel();

    private JScrollPane jScrollPane2 = new JScrollPane();

    //CCBegin SS9
    //private JTextArea descriJTextArea = new JTextArea();
    public JTextArea descriJTextArea = new JTextArea();
    //CCEnd SS9

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
    //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�ߣ�����Ĭ����Ч���ֶ�
    private JLabel defaultJLabel = new JLabel();
    private JScrollPane defaultJScrollPane = new JScrollPane();
    private JTextArea defaultJTextArea = new JTextArea();
    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·�ߣ�����Ĭ����Ч���ֶ�

    /** ������ʾģʽ������ģʽ����� */
    public final static int UPDATE_MODE = 0;

    /** ������ʾģʽ������ģʽ����� */
    public final static int CREATE_MODE = 1;

    /** ������ʾģʽ���鿴ģʽ����� */
    public final static int VIEW_MODE = 2;

    private final static int OKOPTION = 3;
    private final static  int SAVE = 4;
    private final static  int SAVAAFTERCANEL = 5;
    /** ����ģʽ--�鿴 */
    private int mode = VIEW_MODE;
    /** ҵ����� */
    private TechnicsRouteListIfc myRouteList;

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ����Ƿ�ִ���˱������ */
    protected boolean isSave = false;

    private JLabel jLabel1 = new JLabel();

    private JLabel versionJLabel = new JLabel();

    /** ����:��Ʒ��ʶ */
    private String productID = "";
    
    //CCBegin by liunan 2011-04-28 �����������Ƿ�ִ�еı�ʶ��
    public static boolean stateJComboBoxFlag = true;
    //CCEnd by liunan 2011-04-28
    
//CCBegin SS10
      private UpFilePanel upFilePanel;
      private JFrame fFrame;
      static boolean fileVaultUsed = (RemoteProperty.getProperty(
              "registryFileVaultStoreMode", "true")).equals("true");
    private JLabel fujianJLabel = new JLabel();
//CCEnd SS10
    
   TextValidCheck textheck  = new TextValidCheck("����·�߱�",30);
    /**
     * ���캯��
     *
     * @roseuid 4031A737030E
     */
    public RouteListTaskJPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ʼ��
     *
     * @throws Exception
     */
   private void jbInit() throws Exception {
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
        nameJLabel.setText("*��Ŀ����");
        nameJLabel.setBounds(new Rectangle(15, 53, 41, 18));
        nameJTextField.setMaximumSize(new Dimension(2147483647, 22));
        nameJTextField.setBounds(new Rectangle(64, 50, 63, 22));
        levelJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        levelJLabel.setText("����");
        levelJLabel.setBounds(new Rectangle(232, 14, 41, 18));
        departmentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        departmentJLabel.setText("*��λ");
        levelJComboBox.setMaximumSize(new Dimension(50, 22));
        levelJComboBox.setMinimumSize(new Dimension(50, 22));
        levelJComboBox.setPreferredSize(new Dimension(50, 22));
        levelJComboBox.setBounds(new Rectangle(291, 10, 126, 22));
        
        //begin CR5
        workstateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workstateJLabel.setText("����״̬");
        workstateJLabel1.setText("sm");
        workstateJLabel1.setMaximumSize(new Dimension(200, 22));
        workstateJLabel1.setMinimumSize(new Dimension(200, 22));
        workstateJLabel1.setPreferredSize(new Dimension(200, 22));
        //end CR5
        
        departmentSelectedPanel = new SortingSelectedPanel("��λ",
                "TechnicsRouteList", "routeListDepartment");
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
        productJLabel.setText("*���ڲ�Ʒ");
        productJTextField.setMaximumSize(new Dimension(2147483647, 22));
        productJTextField.setEditable(false);
        browseJButton.setMaximumSize(new Dimension(91, 23));
        browseJButton.setMinimumSize(new Dimension(91, 23));
        browseJButton.setPreferredSize(new Dimension(91, 23));
        browseJButton.setMnemonic('R');
        browseJButton.setText("����(R). . .");
        browseJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseJButton_actionPerformed(e);
            }
        });
        descriJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriJLabel.setText("˵��");
       //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        defaultJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        defaultJLabel.setText("Ĭ����Ч��");
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
        partLinkJPanel.setBorder(BorderFactory.createEtchedBorder());
        partLinkJPanel.setMaximumSize(new Dimension(338, 32767));
        partLinkJPanel.setMinimumSize(new Dimension(338, 10));
        partLinkJPanel.setPreferredSize(new Dimension(338, 10));
        //CCBegin SS9
        partLinkJPanel.setRLTJPanel(this);
        //CCEnd SS9
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
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveJButton_actionPerformed(e);
            }
        });
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OKROUTELIST");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });
        //CCBegin SS4
        stateJComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
          
					stateJComboBox_actionPerformed(e);
		
            }
        });
        //CCEnd SS4
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
        lifeCycleInfo.getLifeCyclePanel().setBrowseButtonSize(
                new Dimension(83, 23));
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(
                new Dimension(83, 23));
        lifeCycleInfo.setBsoName("TechnicsRouteList");
        lifeCycleInfo.getProjectPanel().setButtonMnemonic('P');
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(
                new Dimension(91, 23));
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        stateLabel.setText("���");
        stateJComboBox.addItemListener(new
                RouteListTaskJPanel_stateJComboBox_itemAdapter(this));
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
        buttonJPanel.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel4.add(browseJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2,
                        8, 2, 0), 0, 0));
        jPanel4.add(productJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2,
                        0, 2, 0), 0, 0));
        jPanel4.add(productJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 8, 2, 0), 0, 0));
//      CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��        
//        attriJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 3, 2, 1, 1.0,
//                0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                new Insets(4, 10, 4, 10), 0, 10));
        attriJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(4, 34, 4, 10), 0, 10));
//      CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��        
        //begin CR5
        attriJPanel.add(workstateJLabel, new GridBagConstraints(0, 4, 4, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 40, 4, 10), 0, 0));
        attriJPanel.add(workstateJLabel1, new GridBagConstraints(1, 4, 4, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        //end CR5

        attriJPanel.add(descriJLabel, new GridBagConstraints(0, 5, 1, 1, 0.0,
                0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(5, 40, 0, 0), 0, 0));
        attriJPanel.add(jScrollPane2, new GridBagConstraints(1, 5, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 8, 10, 10), 0, 0));
       // jScrollPane2.setHorizontalScrollBarPolicy( jScrollPane2.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.getViewport().add(descriJTextArea, null);
//      CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
       attriJPanel.add(defaultJLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
          , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
          new Insets(5, 28, 0, 0), 0, 0));
       attriJPanel.add(defaultJScrollPane,
                      new GridBagConstraints(1, 6, 1, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(5, 8, 10, 10), 0, 0));
       defaultJScrollPane.getViewport().add(defaultJTextArea, null);  
//     CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
      //  descriJTextArea.setColumns(128);
        descriJTextArea.setLineWrap(true);
        attriJPanel.add(folderPanel, new GridBagConstraints(1, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(4, 7, 4, 10), 0, 0));
        attriJPanel.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        attriJPanel.add(locationJLabel, new GridBagConstraints(1, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 10), 0, 0));
        this.add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        folderPanel.removeLabel();
        folderPanel.setButtonMnemonic('O');
        folderPanel.setButtonSize(91, 23);
        
        jTabbedPane.add(jPanel1, "����");
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jTabbedPane.add(partLinkJPanel,    "�㲿����");
        
      //Begin CR1
        class WorkThread extends Thread               
      	{
      		public void run()
      		{
      			ProgressDialog d = new ProgressDialog(getParentJFrame());
      			d.setMessage("���ڴ������ݣ����Ժ�...");
      			d.startProcess();

      			partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
      			d.stopProcess();
      		}
      	}
        jTabbedPane.addChangeListener(new ChangeListener(){               

    		public void stateChanged(ChangeEvent cevent)
            {
    			if(jTabbedPane.getSelectedIndex()==1){
    				
    				  WorkThread t = new WorkThread();
    				   t.start();
             }
            }
        	
        });       
      //End CR1   
        
        jScrollPane1.getViewport().add(attriJPanel, null);

        jPanel3.add(numberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel3.add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel3.add(numberJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJComboBox, new GridBagConstraints(3, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        18, 0, 0), 0, 0));
        jPanel3.add(levelDisplayJLabel, new GridBagConstraints(3, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentDisplayJLabel, new GridBagConstraints(3, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentJLabel, new GridBagConstraints(2, 1, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 18, 0, 0), 0, 0));
        jPanel3.add(departmentSelectedPanel, new GridBagConstraints(3, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4,
                        0, 2, 0), 0, 0));
        jPanel3.add(versionJLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 2, 0), 0, 0));
        jPanel3.add(stateJComboBox, new GridBagConstraints(3, 2, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateDisplayJLabel, new GridBagConstraints(3, 2, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));

        attriJPanel.add(jPanel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        7, 34, 0, 10), 0, 0));
//      CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��     
//        attriJPanel.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
//                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//                new Insets(4, 10, 4, 10), 0, 0));
        attriJPanel.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
          new Insets(4, 34, 4, 10), 0, 0));
//      CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��   
        this.add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
                        10, 0, 10, 0), 0, 0));

	      //CCBegin SS10
        fujianJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fujianJLabel.setText("����");
        upFilePanel = new UpFilePanel((JFrame)this.getFrame());
        attriJPanel.add(fujianJLabel, new GridBagConstraints(0, 7, 1, 1, 0.0,
                0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(5, 40, 0, 0), 0, 0));
        attriJPanel.add(upFilePanel, new GridBagConstraints(1, 7, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 8, 10, 10), 0, 0));
	      //CCEnd SS10
                
        departmentSelectedPanel.setVisible(false);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        localize();
        
    }
    
    //CCBegin SS10
    public void setFrame(JFrame f)
    {
    	fFrame = f;
    }
    public JFrame getFrame()
    {
    	return fFrame;
    }
    //CCEnd SS10


    /**
     * ���ػ�
     */
    private void localize() {
        RouteListLevelType[] levelTypes = RouteListLevelType.getRouteListLevelTypeSet();
        for (int i = 0; i < levelTypes.length; i++) {
            levelJComboBox.addItem(levelTypes[i].getDisplay());
        }
        levelJComboBox.setSelectedItem(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        levelJComboBox
                .addActionListener(new RouteListTaskJPanel_levelJComboBox_actionAdapter(
                        this));
        initStateJComboBox();
    }

    /**
     * ��ʼ������·�߱�״̬��ѡ���ֵ
     */
    private void initStateJComboBox() {
        Class[] params = { String.class, String.class };
        Object[] values = { "״̬", "����·�߱�" };
        Collection result = null;
        try {
            result = (Collection) invokeRemoteMethodWithException(this,
                    "CodingManageService", "findDirectClassificationByName",
                    params, values);
                //System.out.println("�ҵ�״̬����  " + result.size());
        } catch (QMRemoteException ex) {
            //����쳣��Ϣ��
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    QMMessage.getLocalizedMessage(RESOURCE, "ERROR", null),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (result != null && result.size() > 0) {
            Iterator iterator = result.iterator();
            while (iterator.hasNext()) {
                stateJComboBox.addItem((iterator.next()).toString());
            }
            stateJComboBox.setSelectedIndex(0);
        }
    }

    /**
     * ����һ��������ר������Զ�̵��÷���˵ķ�����
     *
     * @param component
     *            ��ʾ���ø÷����ĵ�ǰ������󣬿���Ϊ��
     * @param serviceName
     *            �����õķ���˷�������(ServiceName)
     * @param methodName
     *            �����õķ�����
     * @param paramTypes
     *            �����õķ����Ĳ������ͼ��ϣ���Ҫ���շ�����˳��
     * @param paramValues
     *            ����ĸ���������ֵ
     * @throws QMRemoteException
     *             �׳�Զ���쳣
     * @return �������
     */
    public static Object invokeRemoteMethodWithException(Component component,
            String serviceName, String methodName, Class[] paramTypes,
            Object[] paramValues) throws QMRemoteException {
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
        try {
            result = server.request(info);
        } catch (QMRemoteException ex) {
            //���쳣���д�������쳣��Ϣ�������ס�
            //ex.printStackTrace();
            throw ex;
        }
        //end try-catch
        return result;
    }


    /**
     * ����ҵ�����
     *
     * @param routelist
     *            ����·�߱����
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
        myRouteList = routelist;
    }

    /**
     * ���ҵ�����
     *
     * @return ����·�߱����
     */
    public TechnicsRouteListIfc getTechnicsRouteList() {
        return myRouteList;
    }

    /**
     * ����Ĭ����ʾ�½�ָ����Ʒ��·�߱����(��ʱ���ñ�����)
     *
     * @param product
     *            ָ����Ʒ
     */
    public void setDefaultProductView(QMPartMasterIfc product) {
        this.setViewMode(CREATE_MODE);
        productJTextField.setText( getIdentity(product));
        productID = product.getBsoID();
    }

    /**
     * ���ý���ģʽ�����������»�鿴����
     *
     * @param aMode
     *            �½���ģʽ
     */
    public void setViewMode(int aMode) {
        if ((aMode == UPDATE_MODE) || (aMode == CREATE_MODE)
                || (aMode == VIEW_MODE)) {
            mode = aMode;
        }

        switch (aMode) {

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
     *
     * @return ��ǰ����ģʽ
     */
    public int getViewMode() {
        return mode;
    }

    /**
     * ���ý���Ϊ�½�ģʽ
     */
    private void setCreateModel() {
        ((CappRouteListManageJFrame) this.getParentJFrame()).getTreePanel()
                .clearSelection();
        isSave = false;
        jTabbedPane.setEnabledAt(1, false);
        jTabbedPane.setSelectedIndex(0);
        levelDisplayJLabel.setVisible(false);
        stateDisplayJLabel.setVisible(false);
        levelJComboBox.setVisible(true);
        levelJComboBox.setSelectedItem(RouteListLevelType
                .getRouteListLevelTypeDefault().getDisplay());
//      CCBegin by leixiao 2010-1-8 ���ֻ��һ��·�ߣ����û�����ѡ�����·��
        levelJComboBox.setEnabled(false);
//      CCEnd by leixiao 2010-1-8
        stateJComboBox.setVisible(true);
//      CCBegin by leixiao 2010-2-23 Ĭ��Ϊ��׼
        stateJComboBox.setSelectedIndex(2);
//      CCEnd by leixiao 2010-2-23 
        //partLinkJPanel.setEditModel();
        this.setTextFieldVisible(numberJTextField);
        numberJTextField.setText("");
        //CCBegin SS4
        numberJTextField.setVisible(true);
        numberJTextField.setEnabled(true);
        //CCend SS4
        this.setTextFieldVisible(nameJTextField);
        nameJTextField.setText("");
        this.setTextFieldVisible(productJTextField);
        productJTextField.setText("");
        productJTextField.setEditable(false);
        descriJTextArea.setEditable(true);
//      CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����˵��Ĭ���ֶ�
        defaultJTextArea.setEditable(false);
        descriJTextArea.setText("���ݣ���������PDM��������˵����      ������׼��������׼����\n" + "˵����");
        defaultJTextArea.setText("");
//      CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����˵��Ĭ���ֶ�
        browseJButton.setVisible(true);
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(false);
        workstateJLabel1.setVisible(false);
        workstateJLabel1.setText(null);
        //end CR5
        TechnicsRouteListInfo routeList = new TechnicsRouteListInfo();
        this.setTechnicsRouteList(routeList);

        versionJLabel.setText("A.1");
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
        lifeCycleInfo.getProjectPanel().setObject(null);
        //lifeCycleInfo.setObject((LifeCycleManagedIfc)this.getTechnicsRouteList());
        locationJLabel.setVisible(false);
        folderPanel.setVisible(true);
        folderPanel.setSelectModel(true);
        folderPanel.setButtonSize(91, 23);
        try {

            folderPanel.setLabelText(this.getPersionalFolder());
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }

         //CCBegin SS10
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(false);
         this.getUpFilePanel().getMultiList().clear();
         this.getUpFilePanel().setRow(0);
         //CCEnd SS10
         
        if (!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setUpdateModel() {
        isSave = false;
        jTabbedPane.setEnabledAt(1, true);
//      CCBegin by leixiao 2009-3-30 ԭ�򣺽����������·��,��Ϊ�ձϵ��жϣ���setEditModel����setTechnicsRouteList����
//      partLinkJPanel.setEditModel();
//    CCEnd by leixiao 2009-3-30 ԭ�򣺽����������·��
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        productJTextField.setEditable(false);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        //CCBegin by liunan 2011-04-12 ��ƼҪ�������������ġ����
        //stateJComboBox.setVisible(false);
        stateJComboBox.setVisible(true);
        //CCBegin by liunan 2011-04-28 setSelectedItemʱ��Ĭ����ǰ׼��������ݵ�ǰ��׼����иı�Ļ��ͻ������������ı�˵�������ݡ�
        //������һ����ʶstateJComboBoxFlag��ֻ�д˴������������á�
    	  stateJComboBoxFlag = false;
        stateJComboBox.setSelectedItem(this.getTechnicsRouteList().getRouteListState());
        stateJComboBoxFlag = true;
        //CCEnd by liunan 2011-04-28
        //CCEnd by liunan 2011-04-12
        stateDisplayJLabel.setVisible(true);
        //end
//      CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����Ĭ����Ч��
        if (this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
            defaultJTextArea.setEditable(true);
          }
          else {
            defaultJTextArea.setEditable(false);
          }
//      CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����Ĭ����Ч��
        descriJTextArea.setEditable(true);
        browseJButton.setVisible(false);
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try {
            numberJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListLevel());
            stateDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListState());
            versionJLabel
                    .setText(this.getTechnicsRouteList().getVersionValue());
            //CCBegin SS1
            partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());//CR1
            //CCEnd SS1
            //CCBegin SS8
            if(this.getTechnicsRouteList().getRouteListState().equals("ǰ׼")||this.getTechnicsRouteList().getRouteListState().equals("��׼")||
            		this.getTechnicsRouteList().getRouteListState().equals("��׼")
        				|| this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
            	partLinkJPanel.setPartLinkJPanel();
            }
            //CCEnd SS8
            this.partLinkJPanel.getAddedPartLinks().clear();//Begin CR2
            this.partLinkJPanel.getDeletedPartLinks().clear();//End CR2
//          CCBegin by leixiao 2009-3-30 ԭ�򣺽����������·��,��Ϊ�ձϵ��жϣ���setEditModel����setTechnicsRouteList����
            partLinkJPanel.setEditModel(this.getTechnicsRouteList());
//          CCEnd by leixiao 2009-3-30 ԭ�򣺽����������·��
            //�ж��Ƿ���ʾ��λ
            String department = ((TechnicsRouteListInfo) this
                    .getTechnicsRouteList()).getDepartmentName();
            if (department != null && !department.equals("")) {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            } else {
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
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle(
                (LifeCycleManagedIfc) getTechnicsRouteList());
            lifeCycleInfo.getProjectPanel().setObject(
                (LifeCycleManagedIfc) getTechnicsRouteList());
            //modify by guoxl end

            //lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            String productID = this.getTechnicsRouteList().getProductMasterID();
            this.productID = productID;
            QMPartMasterInfo partinfo = null;

            partinfo = (QMPartMasterInfo) refreshInfo(productID);
            productJTextField.setText(getIdentity(partinfo));
            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            try {
				String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
				workstateJLabel1.setText(str);
			} catch (QMException e) {
				e.printStackTrace();
			}
			//end CR5
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }

         //CCBegin SS10
         setUpFileAccessaryName((TechnicsRouteListIfc) getTechnicsRouteList());
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(true);
         //CCEnd SS10
         
        if (!this.isShowing())
            this.setVisible(true);
        repaint();
    }
    //CCBegin SS4
    
    void stateJComboBox_actionPerformed(ActionEvent e) 
    {
		if (stateJComboBox.getSelectedItem().toString().equals("���պϼ�")) {
			lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("���պϼ�·����������");
			numberJTextField.setText("�Զ����");
			numberJTextField.setEditable(false);
			numberJTextField.setEnabled(false);
        	String folder = RemoteProperty.getProperty("hechengRouteFolder", "\\Root\\���պϼ���׼֪ͨ��");   
        	folderPanel.setLabelText(folder);
		}
     
    }
  //CCEnd SS4  

    /**
     * ���ý���Ϊ�鿴ģʽ
     */
    private void setViewDisplayModel() {
    	
    	if (verbose)
            {
                System.out.println("�鿴ģʽ"
                        + getTechnicsRouteList().getPartIndex().size());
                        
                            Vector vv = getTechnicsRouteList().getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                            	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]);
                            }}
                            
    	jTabbedPane.setSelectedIndex(0);//CR1
        jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.setViewModel();
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //ed
        browseJButton.setVisible(false);
        descriJTextArea.setEditable(false);
//      CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����Ĭ����Ч��
        defaultJTextArea.setEditable(false);
//      CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����Ĭ����Ч��
        saveJButton.setVisible(false);
        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5

        try {
            numberJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListLevel());
            //st skybird 2005.2.24
            stateDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListState());
            //ed
            versionJLabel
                    .setText(this.getTechnicsRouteList().getVersionValue());
            descriJTextArea.setText(this.getTechnicsRouteList()
                    .getRouteListDescription());
//          CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����Ĭ����Ч��
            defaultJTextArea.setText(this.getTechnicsRouteList().
                    getDefaultDescreption());
//          CCEnd by leixiao 2009-7-31 ԭ�򣺽����������·��,����Ĭ����Ч��
//          CCBegin by leixiao 2009-12-04 ԭ�򣺽����������·�߳�ʹ��ʱ��ˢ�¹��ˣ��鿴�ɲ���ˢ��
           // partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());
//          CCEnd by leixiao 2008-12-04 ԭ�򣺽����������·��,����Ĭ����Ч��
            //�ж��Ƿ���ʾ��λ
            String department = ((TechnicsRouteListInfo) this
                    .getTechnicsRouteList()).getDepartmentName();
            if (department != null && !department.equals("")) {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            } else {
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
            if (verbose)
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!"
                        + this.getTechnicsRouteList().getProjectId());
           // lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            //CCBegin By leixiao 2009-12-10 ״̬����ʾ
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            //CCEnd By leixiao 2009-12-10 ״̬����ʾ
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle(
                    (LifeCycleManagedIfc) getTechnicsRouteList());
            //modify by guoxl on 20080214(����·�߱�����ָ����Ŀ��ʱ���治��ˢ�¸��ĺ����Ŀ����Ϣ���ر���Ŀ��ָ��Ϊ��ʱ)
            //if (getTechnicsRouteList().getProjectId() != null)
                lifeCycleInfo.getProjectPanel().setObject(
                        (LifeCycleManagedIfc) getTechnicsRouteList());
            //else
                //lifeCycleInfo.getProjectPanel().setObject(null);
                //modify by guoxl end
            String productID = this.getTechnicsRouteList().getProductMasterID();
            QMPartMasterInfo partinfo = null;

            partinfo = (QMPartMasterInfo) refreshInfo(productID);
            productJTextField.setText(getIdentity(partinfo));
            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            try {
				String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
				workstateJLabel1.setText(str);
			} catch (QMException e) {
				e.printStackTrace();
			}
            //end CR5
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //CCBegin SS10
        setUpFileAccessaryName((TechnicsRouteListIfc) getTechnicsRouteList());
        this.getUpFilePanel().setAButtonVisable(false);
        this.getUpFilePanel().setDButtonVisable(false);
        this.getUpFilePanel().setVButtonVisable(false);
        this.getUpFilePanel().setDLButtonVisable(true);
        //CCEnd SS10

        if (!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * ��õ�ǰ�û��ĸ������ϼ�λ��
     *
     * @return ��ǰ�û��ĸ������ϼ�λ��
     * @throws QMRemoteException
     */
    public String getPersionalFolder() throws QMRemoteException {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() begin...");
        Class[] c = {};
        Object[] objs = {};
        UserInfo user = (UserInfo) useServiceMethod("SessionService",
                "getCurUserInfo", c, objs);
        Class[] c1 = { UserInfo.class };
        Object[] objs1 = { user };
        SubFolderInfo folder = (SubFolderInfo) useServiceMethod(
                "FolderService", "getPersonalFolder", c1, objs1);
        if (verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() end...return: "
                            + folder.getPath());
        return folder.getPath();

    }

    /**
     * ���ð�ָ���ĵ����ı�����ʾΪ��ǩ
     *
     * @param textfield
     *            ָ���ĵ����ı���
     */
    private void setTextFieldToLabel(JTextField textfield) {
        textfield.setBorder(null);
        textfield.setBackground(SystemColor.control);
        textfield.setEditable(false);
    }

    /**
     * ���ð�ָ���ĵ����ı�����ʾΪ�ɱ༭
     *
     * @param textfield
     *            ָ���ĵ����ı���
     */
    private void setTextFieldVisible(JTextField textfield) {
        textfield.setBorder(javax.swing.plaf.basic.BasicBorders
                .getTextFieldBorder());
        textfield.setBackground(Color.white);
        textfield.setEditable(true);
    }

    /**
     * ����Ĭ��Ϊ��һ����,��ʱ��λ�Ӵ���������ж�ȡ. ����ѡ��Ϊ��������ʱ����λ��ǩ�������б����ʾ������λ��ǩ�������б��ɼ�
     *
     * @param e
     *            ItemEvent
     */
    void levelJComboBox_itemStateChanged(ItemEvent e) {

    }

    /**
     * ������������Ƿ�������Чֵ
     *
     * @return �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkRequiredFields() {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() begin...");
        boolean isOK = false;
        String message = "fell through ";
        String title = "";
        try {

            if ((getViewMode() == CREATE_MODE)) {
                //�������Ƿ�Ϊ��
                if (numberJTextField.getText().trim().length() == 0) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_NUMBER_ENTERED, null);
                    this.numberJTextField.grabFocus();
                    isOK = false;
                }
                //���������Ƿ�Ϊ��
                else if (nameJTextField.getText().trim().length() == 0) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_NAME_ENTERED, null);
                    this.nameJTextField.grabFocus();
                    isOK = false;
                }
                //�������ϼ��Ƿ�Ϊ��
                else if (checkFolderLocation() == null) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_LOCATION_ENTERED, null);
                    this.folderPanel.grabFocus();
                    isOK = false;
                }
                //����"���ڲ�Ʒ"�Ƿ�Ϊ��
                else if (productJTextField.getText().trim().length() == 0) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_PRODUCT_ENTERED, null);
                    this.productJTextField.grabFocus();
                    isOK = false;
                } else if (departmentSelectedPanel.isShowing()
                        && departmentSelectedPanel.getCoding() == null) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_DEPARTMENT_ENTER, null);
                    departmentSelectedPanel.grabFocus();
                    isOK = false;
                } else {
                    isOK = true;
                }
            } else {
                isOK = true;
            }
            if (!isOK) {
                //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
                title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(), message,
                        title, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (QMRemoteException qre) {
            //��ʾ��Ϣ����ָ�������ϼв��Ǹ������ϼ�
            title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(), qre
                            .getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
              this.folderPanel.grabFocus();
        }
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() end...return: "
                            + isOK);
        return isOK;

    }

    /**
     * ����ı������ֵ��Ч��
     *
     * @return boolean
     */
    private boolean check() {
      if (numberJTextField.getText().indexOf("*") != -1 || numberJTextField.getText().indexOf("%") != -1 ||
            numberJTextField.getText().indexOf("?") != -1)
        {
            String message = "���" + "���зǷ��ַ�eg:*%?";
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                  null);
          JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                  JOptionPane.WARNING_MESSAGE);
          this.numberJTextField.grabFocus();
            return false;
        }

        if (nameJTextField.getText().indexOf("*") != -1 || nameJTextField.getText().indexOf("%") != -1 ||
           nameJTextField.getText().indexOf("?") != -1)
       {
           String message = "����" + "���зǷ��ַ�eg:*%?";
           String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                 null);
         JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                 JOptionPane.WARNING_MESSAGE);
         this.numberJTextField.grabFocus();
           return false;
       }
        if (numberJTextField.getText().trim().getBytes().length > 200) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            String message = QMMessage
                    .getLocalizedMessage(RESOURCE, "52", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            this.numberJTextField.grabFocus();
            return false;
        }
        if (this.nameJTextField.getText().trim().getBytes().length > 200) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            String message = QMMessage
                    .getLocalizedMessage(RESOURCE, "53", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            this.nameJTextField.grabFocus();
            return false;
        }
        if (this.descriJTextArea.getText().trim().getBytes().length > 2000) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            String message = QMMessage
                    .getLocalizedMessage(RESOURCE, "54", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
           this.descriJTextArea.grabFocus();
            return false;
        }
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�ߣ�����Ĭ����Ч���ֶ�
        if (this.defaultJTextArea.getText().trim().length() > 4000) {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                "warning",
                null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                "54",
                null);
            JOptionPane.showMessageDialog(null, message, title,
                                          JOptionPane.WARNING_MESSAGE);
            return false;
          }
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·�ߣ�����Ĭ����Ч���ֶ�
        return true;
    }
    private boolean checkText (){
      try {
       //CCBegin SS11
       //textheck.setMax(30); //CR3
       textheck.setMax(100); //CR3
       //CCEnd SS11
       textheck.check(numberJTextField, true);
      

     }
     catch (QMRemoteException ex) {
           String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                 null);
         JOptionPane.showMessageDialog(this.getParentJFrame(), ex.getClientMessage(), title,
                 JOptionPane.WARNING_MESSAGE);
         this.numberJTextField.grabFocus();
           return false;

     }
     try {textheck.setMax(200);
           textheck.check(nameJTextField, true);
         }
         catch (QMRemoteException ex) {
               String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                     null);
             JOptionPane.showMessageDialog(this.getParentJFrame(), ex.getClientMessage(), title,
                     JOptionPane.WARNING_MESSAGE);
             this.nameJTextField.grabFocus();
               return false;

         }

        try {textheck.setMax(2000);
                   textheck.check(descriJTextArea, false);
                 }
                 catch (QMRemoteException ex) {
                       String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                             null);
                     JOptionPane.showMessageDialog(this.getParentJFrame(),  ex.getClientMessage(), title,
                             JOptionPane.WARNING_MESSAGE);
                     this.descriJTextArea.grabFocus();
                       return false;

                 }

         return true;
    }
    /**
     * �����Ƿ���ָ�����ϼ�
     *
     * @return �����ָ�����ϼ�·�����򷵻����ϼС�
     * @throws QMRemoteException
     */
    private SubFolderInfo checkFolderLocation() throws QMRemoteException {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() begin...");
        String location = "";
        SubFolderInfo folderInfo = null;
        //������ϼ�·��

        location = folderPanel.getFolderLocation();

        if (location != null && location.length() != 0) {
            //�������ϼз��񣬸��ݻ�õ����ϼ�·��������ϼ�
            Class[] paraClass = { String.class };
            Object[] objs = { location };
            try {
                folderInfo = (SubFolderInfo) useServiceMethod(
                        "FolderService", "getFolder", paraClass, objs);
            } catch (QMRemoteException ex) {
                throw ex;
            }

        }
//CCBegin SS5
//        if (folderInfo != null) {
//            //�������ϼз����ж�ָ�����ļ����Ƿ��Ǹ����ļ���
//            Class[] paraClass2 = { FolderIfc.class };
//            Object[] objs2 = { folderInfo };
//            Boolean flag1 = null;
//            try {
//                flag1 = (Boolean) useServiceMethod("FolderService",
//                        "isPersonalFolder", paraClass2, objs2);
//            } catch (QMRemoteException ex) {
//                String title = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappRouteRB.LOCATION_NOT_VALID, null);
//                JOptionPane.showMessageDialog(getParentJFrame(), ex
//                        .getClientMessage(), title,
//                        JOptionPane.INFORMATION_MESSAGE);
//                this.folderPanel.grabFocus();
//
//            }
//
//            boolean flag = false;
//            if (flag1 != null) {
//                flag = flag1.booleanValue();
//            }
//
//            if (!flag) {
//                //�׳��쳣��Ϣ����ָ�������ϼв��Ǹ����ļ���
//                throw new QMRemoteException(QMMessage.getLocalizedMessage(
//                        RESOURCE, CappRouteRB.LOCATION_NOT_PERSONAL_CABINET,
//                        null));
//            }
//        }
//CCEnd SS5
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() end...return : "
                            + folderInfo);
        return folderInfo;

    }

    /**
     * ִ�б������
     *
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e) {
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
     * ϵͳ����ҵ�����PHOS-CAPP-BR201���Ҫ��ǿյ������Ƿ�Ϊ��(E1)��
     * ����ҵ�����PHOS-CAPP-BR202��鹤��·�߱����Ƿ�Ψһ(E2)��
     * �����ǰΪ����ģʽ,��ϵͳ�������¹���·�߱����´����Ĺ���·�߱���Ϣ��ӵ�·�߱��������·�߱��б��У�
     * �Ѵ�������ˢ��Ϊ���½���(���ѡ����"ȷ��",��ˢ��Ϊ�鿴ģʽ). �����ǰΪ����ģʽ,����´�·�߱�,ˢ�����ڵ�,������ˢ��Ϊ�鿴ģʽ.
     */
    private void processSaveCommond() {

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
        if (!requiredFieldsFilled) {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave=false;
            return;
        }
        if (!flag) {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave=false;
            return;
        }
        //��ʾ�������
//        ProgressService.setProgressText(QMMessage.getLocalizedMessage(RESOURCE,
//                CappRouteRB.SAVING, null));
//        ProgressService.showProgress();
    //������һ��zz 20060925 �����°������
                progressDialog = new ProgressDialog(getParentJFrame());
                progressDialog.startProcess();


        try {
   /*add by guoxl on 2008.4.3(����·�߱�ʱ��ʾ���������ڽ����У����Ժ����ԡ����쳣��Ϣ��
    ԭ���� �ڱ���ʱûˢ�£�����ʱ�����һ��(�Ļ����᳧ʵʩ����))
   */
if (this.getViewMode() == UPDATE_MODE) {
       

  try {
   this.setTechnicsRouteList(
     (TechnicsRouteListIfc)(CappClientHelper.refresh(this.getTechnicsRouteList().getBsoID())));

      }
    catch (QMRemoteException ex) {

          JOptionPane.showMessageDialog(getParentJFrame(), ex.getClientMessage());
          CappRouteListManageJFrame  f = (CappRouteListManageJFrame)this.getParentJFrame();
          f.getTreePanel().removeNode(new RouteListTreeObject(getTechnicsRouteList()));
          setCursor(Cursor.getDefaultCursor());
          setButtonWhenSave(true);
           this.setVisible(false);
          return ;
        }
}
    //add end by guoxl on 2008.4.3
          //�ύ��������
            this.commitAllAttributes();
            if (verbose)
            {
                System.out.println("setPartIndexǰ˳��"
                        + getTechnicsRouteList().getPartIndex().size());
                        
                            Vector vv = getTechnicsRouteList().getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                            	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]);
                            }}
            if (this.partLinkJPanel != null)
            {
                getTechnicsRouteList().setPartIndex(
                        partLinkJPanel.getPartIndex());
            }
            if (verbose)
            {
                System.out.println("����ǰ��˳��"
                        + getTechnicsRouteList().getPartIndex().size());
                        
                            Vector vv = getTechnicsRouteList().getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                            	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]+"=="+idd[3]);
                            }}
            if (this.getViewMode() == CREATE_MODE) {


              //CCBegin SS10
              //��ø�����Ϣ
              ArrayList arrayList = getArrayList();
              //���÷��񣬱��湤��·�߱�
                //Class[] paraClass = { TechnicsRouteListIfc.class };
                Class[] paraClass = { TechnicsRouteListIfc.class , ArrayList.class};
                 //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��,���ת�ɴ�д
                getTechnicsRouteList().setRouteListNumber(getTechnicsRouteList().
                                                          getRouteListNumber().
                                                          toUpperCase());
              //CCEnd SS10
                 //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
                //Object[] obj = { this.getTechnicsRouteList() };
                Object[] obj = { this.getTechnicsRouteList(), arrayList };
                TechnicsRouteListIfc returnRouteList = null;

                   returnRouteList = (TechnicsRouteListIfc)
                      useServiceMethod(
                      "TechnicsRouteService", "storeRouteList", paraClass,
                      obj);
        
                // �����������ֹ��������������ʱ�ٴγ���������ʾ zz 20061106
                isSave = true;
                this.setTechnicsRouteList(returnRouteList);
              //String ct = returnRouteList.getCreateTime().toString();
             // System.out.println("����ʱ��======"+ ct.substring(0,ct.lastIndexOf(".")));

                //���´����Ĺ���·�߱���Ϣ��ӵ�·�߱��������·�߱��б���
                RouteTreePanel treePanel = ((CappRouteListManageJFrame) this
                        .getParentJFrame()).getTreePanel();
                RouteListTreeObject newObj = new RouteListTreeObject(
                        returnRouteList);
                treePanel.addNode(newObj);
                ((CappRouteListManageJFrame) getParentJFrame()).isViewRouteList = false;
                treePanel.setNodeSelected(newObj);
                ((CappRouteListManageJFrame) getParentJFrame()).isViewRouteList = true;
                //�Ѵ�������ˢ��Ϊ���½���
                jTabbedPane.setEnabledAt(1, true);
                this.setTechnicsRouteList(returnRouteList);
                this.setViewMode(UPDATE_MODE);
               
                if (commond.equals("OKROUTELIST")) {

                    //������ˢ��Ϊ�鿴ģʽ
                    partLinkJPanel.setViewModel();
                    descriJTextArea.setEditable(false);
                    saveJButton.setVisible(false);
                    okJButton.setVisible(false);
                    cancelJButton.setVisible(false);
                    mode = VIEW_MODE;
                }

            } else if (this.getViewMode() == UPDATE_MODE) {
            	//CCBegin SS10
              //��ø�����Ϣ
        ArrayList arrayList = getArrayListupdate();
        Collection deleteContentCol = (Collection)getUpFilePanel().
		                            getDeleteContentMap().values();
		    Collection vec = new Vector(deleteContentCol);
		    getUpFilePanel().getDeleteContentMap().clear();  
                //����·�߱����㲿���Ĺ���
                //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�� ���ý�ŵķ���
                Class[] c = { HashMap.class, ArrayList.class,HashMap.class,
                        //CCBegin SS3
                        //TechnicsRouteListIfc.class };
                	
                       // TechnicsRouteListIfc.class, boolean.class };
                //TechnicsRouteListIfc.class, boolean.class , boolean.class};
                TechnicsRouteListIfc.class, boolean.class , boolean.class, ArrayList.class, Vector.class};
                        //CCEnd SS3
   
                Object[] objs = {
                    partLinkJPanel.getAddedPartLinks(),
                    partLinkJPanel.getUpdateLinks(),
                    partLinkJPanel.getDeletedPartLinks(),
                    //CCBegin SS3
                    //this.getTechnicsRouteList()};
                    this.getTechnicsRouteList(),
                    partLinkJPanel.addLastRouteCheckBox.isSelected(),
                    //CCEnd SS3
                //CCBegin SS6
                    //partLinkJPanel.saveAs.isSelected()};
                    partLinkJPanel.saveAs.isSelected(), arrayList, vec};
                    //CCEnd SS10
                //CCEnd SS6
                    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
                if (verbose)
                    System.out.println("ɾ������ =="
                            + partLinkJPanel.getDeletedPartLinks());
                if (verbose)
                    System.out.println("��Ӽ��� =="
                            + partLinkJPanel.getAddedPartLinks());
                useServiceMethod("TechnicsRouteService",
                        "saveListRoutePartLink", c, objs);
             //   isSave = true;//CR4
               partLinkJPanel.clearPartLinks();
                //�����㲿���ĸ������ added by skybird 2005.3.4
                //c = new Class[]{Collection.class,TechnicsRouteListIfc.class};
                Class[] c1 = { String.class };
                String theBeforedBsoid = this.getTechnicsRouteList().getBsoID();
                Object[] obj1 = { theBeforedBsoid };
                if (verbose)
                    System.out.println("ԭ�е�·�߱�id" + theBeforedBsoid);
                TechnicsRouteListIfc technicsRouteIfc = (TechnicsRouteListIfc) CappRouteAction
                        .useServiceMethod("PersistService", "refreshInfo", c1,
                                obj1);
                theBeforedBsoid = technicsRouteIfc.getBsoID();
                if (verbose){
                    System.out.println("���º��partIndexs"
                            + technicsRouteIfc.getPartIndex().size());
                            Vector vv = technicsRouteIfc.getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                               	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]+"=="+idd[3]);
                            }};
                this.myRouteList = technicsRouteIfc;
                //CCBegin by liunan 2011-04-12 ��ƼҪ�������������ġ����
                //this.setTechnicsRouteList(technicsRouteIfc);//anan
                //System.out.println("anan ��"+this.myRouteList.getPartIndex().size());
                //CCEnd by liunan 2011-04-12
                // System.out.println("Ҫ�ĵ��㲿��");
                // Vector tmp = (Vector)partLinkJPanel.getPartsToChange();
                //  System.out.println(tmp.size());
                // for(int i=0;i<tmp.size();i++)
                // {
                //   Object[] a = (Object[])tmp.elementAt(i);
                /// System.out.println("part bsoid"+a[0]+"parentPartID"+a[1]);
                //  }
                //  objs = new
                // Object[]{partLinkJPanel.getPartsToChange(),this.getTechnicsRouteList()};
                //  useServiceMethod(
                //  "TechnicsRouteService","updateListRoutePartLink",c,objs);
                //ˢ�����ڵ�
                ((CappRouteListManageJFrame) this.getParentJFrame())
                        .getTreePanel()
                        .updateNode(
                                new RouteListTreeObject(getTechnicsRouteList()));
                if (commond.equals("OKROUTELIST")) {
                  //������ˢ��Ϊ�鿴ģʽ

                  partLinkJPanel.setViewModel();
                  descriJTextArea.setEditable(false);
                  saveJButton.setVisible(false);
                  okJButton.setVisible(false);
                  cancelJButton.setVisible(false);
                  //CCBegin by liunan 2011-04-12 ��ƼҪ�������������ġ����
                  stateJComboBox.setVisible(false);
                  this.setViewMode(2);
                  //CCEnd by liunan 2011-04-12
                  mode = VIEW_MODE;
                }
              //CCBegin by leixiao 2011-1-12 ԭ��:���·��������
                //��Ϊ�ձ��Զ�������ÿ�������·��,����ˢ��������ʾ·��״̬
                if (this.getTechnicsRouteList().getRouteListState().equals("�ձ�")){
                partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
                }
                //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
                else if (this.getTechnicsRouteList().getRouteListState().equals("�շ�")){
                partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
                }
                //CCEnd by liunan 2011-09-21
              //CCBegin by leixiao 2011-1-12 ԭ��
            }

            //isSave=true;//CR4
        } catch (TechnicsRouteException ex) {

            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            isSave = false;
            mode = CREATE_MODE;
        } catch (QMRemoteException ex) {
            progressDialog.stopProcess();
            System.out.println(getParentJFrame());
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            isSave = false;
            mode = CREATE_MODE;
        }

        //ProgressService.hideProgress();
        progressDialog.stopProcess();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);

    }

    /**
     * ִ��ȷ������
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        isSave = true;
        commond = e.getActionCommand();
      //  processSaveCommond();

      WKThread work = new WKThread(OKOPTION);
       work.start();

    }

    /**
     * �ύ·�߱�����������Թ�����
     *
     * @throws TechnicsRouteException
     * @throws QMRemoteException
     */
    private void commitAllAttributes() throws TechnicsRouteException,
            QMRemoteException {
        //˵��
        this.getTechnicsRouteList().setRouteListDescription(
                descriJTextArea.getText());
        //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·�ߣ�����Ĭ����Ч��
        this.getTechnicsRouteList().setDefaultDescreption(defaultJTextArea.getText());
        //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��
        
        //CCBegin by liunan 2011-04-12 ������ƼҪ�󣬸���ʱ���Ը��ġ���𡱡�
        this.getTechnicsRouteList().setRouteListState(stateJComboBox.getSelectedItem().toString());
        //CCEnd by liunan 2011-04-12

        if (this.getViewMode() == CREATE_MODE) {
            //���.����
            //CCBegin SS12
            this.getTechnicsRouteList().setRouteListNumber(
                    //numberJTextField.getText());
                    numberJTextField.getText().trim());
            //CCEnd SS12
            this.getTechnicsRouteList().setRouteListName(
                    nameJTextField.getText());
            //����
            this.getTechnicsRouteList().setRouteListLevel(
                    levelJComboBox.getSelectedItem().toString());
            //CCBegin by liunan 2011-04-12 ������ƼҪ�󣬸���ʱ���Ը��ġ���𡱡�Ų��if��˴�ע�͡�
            //״̬
            //this.getTechnicsRouteList().setRouteListState(
                    //stateJComboBox.getSelectedItem().toString());
            //CCEnd by liunan 2011-04-12
            //��λ
            if (departmentSelectedPanel.isShowing())
                this.getTechnicsRouteList().setRouteListDepartment(
                        departmentSelectedPanel.getCoding().getBsoID());
            //���ڲ�Ʒ
            this.getTechnicsRouteList().setProductMasterID(this.productID);
            //�������ϼ�
            Class[] theClass = { FolderEntryIfc.class, String.class };
            Object[] objs = { this.getTechnicsRouteList(),
                    folderPanel.getFolderLocation() };
            TechnicsRouteListInfo info = (TechnicsRouteListInfo)
                    useServiceMethod("FolderService", "assignFolder",
                            theClass, objs);
            this.setTechnicsRouteList(info);
            //�����������ں���Ŀ��
            LifeCycleManagedIfc lcm = lifeCycleInfo
                    .assign((LifeCycleManagedIfc) getTechnicsRouteList());
            this.setTechnicsRouteList((TechnicsRouteListInfo) lcm);
        }
    }

    /**
     * ȡ������
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        processCancelCommond();
    }

    /**
     * �����ǰ����ģʽΪ����,�����δ����,����ʾ�û��Ƿ񱣴�;���������,���˳�����.
     * �����ǰ����ģʽΪ����,�����δ��������ʾ�û��Ƿ񱣴�;���������,�򽫽���ˢ��Ϊ�鿴״̬
     */
    protected boolean processCancelCommond() {
        if (this.getViewMode() == CREATE_MODE) {
           //System.out.println("CREATE_MODE");
            if (!isSave)
                this.quitWhenCreate();
            else
                this.setVisible(false);
        } else if (this.getViewMode() == UPDATE_MODE) {
          //System.out.println("UPDATE_MODE");
            if (!isSave)
                this.quitWhenUpdate();
            else {
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
    private void quitWhenCreate() {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.IS_SAVE_CREATE_ROUTELIST, null);
        if (this.confirmAction(s)) {
           // this.processSaveCommond();
            //������һ��zz 20061107 �������������߳�
//             WKThread work = new WKThread(SAVAAFTERCANEL);
//             work.start();
            processSaveCommond();
            //��ֹ��������������ʱ�ٴγ���������ʾ zz 20061106 start
            //isSave = true;     // end
        } else {
            this.setVisible(false);
            isSave = true;
        }
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() end...return is void ");

    }

    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���. �����ǰ����ģʽΪ����,�����δ��������ʾ�û��Ƿ񱣴�;���������,�򽫽���ˢ��Ϊ�鿴״̬
     */
    private void quitWhenUpdate() {
      //System.out.println(" routelisttaskjpanel 1440");
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.IS_SAVE_UPDATE_ROUTELIST, null);
        if (this.confirmAction(s)) {
            this.processSaveCommond();
            isSave = true;
        } else {
            this.setViewMode(2);
            isSave = true;
        }
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() end...return is void");

    }

    /**
     * ��ʾȷ�϶Ի���
     *
     * @param s
     *            �ڶԻ�������ʾ����Ϣ
     * @return ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private boolean confirmAction(String s) {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information",
                null);
        JOptionPane okCancelPane = new JOptionPane();
        return okCancelPane.showConfirmDialog(getParentJFrame(), s, title,
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * ���������ڲ�Ʒ�����㲿��
     *
     * @param e
     *            ActionEvent
     */
    void browseJButton_actionPerformed(ActionEvent e) {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle",
                null);
        //����������
        QmChooser qmChooser = new QmChooser("QMPartMaster", title, this
                .getParentJFrame());
        qmChooser.setRelColWidth(new int[] { 1, 1 });
//      CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,
        qmChooser.setChildQuery(true);
//      CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��, 
        try {
            qmChooser.setMultipleMode(false);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
            return;
        }
        //���ո���������ִ������
        qmChooser.addListener(new QMQueryListener() {

            public void queryEvent(QMQueryEvent e) {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * �����㲿�������¼�����
     *
     * @param e
     *            ���������¼�
     */
    private void qmChooser_queryEvent(QMQueryEvent e) {
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if (e.getType().equals(QMQueryEvent.COMMAND)) {
            if (e.getCommand().equals(QmQuery.OkCMD)) {
                //��������������������������㲿��
                QmChooser c = (QmChooser) e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if (bvi != null) {
                    productID = bvi.getBsoID();
                    productJTextField.setText(getIdentity(bvi));
                }
            }
        }
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

    /**
     * ���ð�ť����ʾ״̬����Ч��ʧЧ��
     *
     * @param flag
     *            flagΪTrue����ť��Ч������ťʧЧ
     */
    private void setButtonWhenSave(boolean flag) {
        okJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
        //CCBegin SS10
        this.getUpFilePanel().setDLButtonVisable(flag);
        //CCEnd SS10
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
     * ����Combo��Ӧ�¼�
     * @param e
     */
    void levelJComboBox_actionPerformed(ActionEvent e) {
        if (this.levelJComboBox.getSelectedItem().toString().equals(
                RouteListLevelType.SENCONDROUTE.getDisplay())) {
            departmentSelectedPanel.setVisible(true);
            departmentJLabel.setVisible(true);
        } else {
            departmentSelectedPanel.setVisible(false);
            departmentJLabel.setVisible(false);
        }
    }
    
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����Ĭ����Ч��˵��
    void stateJComboBox_itemStateChanged(ItemEvent e) {
    	//CCBegin by liunan 2011-04-28 ����stateJComboBoxFlag�ж��Ƿ������ֻ�е�һ�θ�����׼ʱ�����⡣
    	if(stateJComboBoxFlag)
    	{
    	//CCEnd by liunan 2011-04-28
        if (this.stateJComboBox.getSelectedItem().equals("��׼")) {
          this.defaultJTextArea.setEditable(true);
        }
        else {
          this.defaultJTextArea.setEditable(false);
        }
//      CCBegin by leixiao 2009-5-4 ԭ�򣺽����������·��,�����ձ�
        if(this.stateJComboBox.getSelectedItem().equals("�ձ�")) {
        //CCBegin by liunan 2011-04-12 ������ƼҪ�󣬸���ʱ���Ը��ġ���𡱣����������ڲ��������޸ģ����û��ֹ��޸ġ�
        if(this.getViewMode() != UPDATE_MODE)
        //CCEnd by liunan 2011-04-12
        lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(
                "�ձ�֪ͨ��");
     //   descriJTextArea.setText("���ݣ���������PDM��������˵����      \n" + "˵����");
        descriJTextArea.setText("���ݣ��������Ĳ���֪ͨ�飨PDM��������˵������       ����׼     ������������׼�����,��Ͷ��������\n" + "˵����");
        }
        //CCBegin SS2
        else if(this.stateJComboBox.getSelectedItem().equals("ǰ׼"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("��׼֪ͨ�飨���ǩ��");
          }
          descriJTextArea.setText("���ݣ���������PDM��������˵����/��������֪ͨ��/��Ʒ���ü�����֪ͨ��      ����ǰ׼��������׼����\n" + "˵����");
        }
        //CCEnd SS2
        //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
        else if(this.stateJComboBox.getSelectedItem().equals("�շ�"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("�շ�֪ͨ��");
          }
          descriJTextArea.setText("���ݣ���������֪ͨ��������֪ͨ�飩    �����������ݡ�\n" + "˵����");
        }
        //CCBegin SS13
        else if(this.stateJComboBox.getSelectedItem().equals("��׼"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("��׼֪ͨ�飨���ǩ��");
          }
          descriJTextArea.setText("���ݣ��������ļ�������֪ͨ��      ������׼��������׼����\n" + "˵����");
        }
        //CCEnd SS13
        //CCBegin SS14
        else if(this.stateJComboBox.getSelectedItem().equals("����"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("��׼֪ͨ�飨���ǩ��");
          }
          descriJTextArea.setText("���ݣ��������ļ�������֪ͨ��      ��������׼��������׼����\n" + "˵����");
        }
        //CCEnd SS14
        else{
            //CCBegin by liunan 2011-04-12 ������ƼҪ�󣬸���ʱ���Ը��ġ���𡱣����������ڲ��������޸ģ����û��ֹ��޸ġ�
        if(this.getViewMode() != UPDATE_MODE)
        //CCEnd by liunan 2011-04-12
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(
            "��׼֪ͨ�飨���ǩ��");
            descriJTextArea.setText("���ݣ���������PDM��������˵����      ������׼��������׼����\n" + "˵����");
        }
//      CCEnd by leixiao 2009-5-4 ԭ�򣺽����������·��
      //CCBegin by liunan 2011-04-28 ����stateJComboBoxFlag�ж��Ƿ������ֻ�е�һ�θ�����׼ʱ�����⡣
      }
      //CCEnd by liunan 2011-04-28
      }
    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��

    class WKThread extends QMThread {
        int myAction;
        public WKThread(int action) {
            super();
            this.myAction = action;
        }


        /**
         * WKTread���з���
         */
        public void run() {

                switch (myAction) {
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

	 //CCBegin SS10
    /**
     * ������еĸ�����Ϣ
     */
    private ArrayList getArrayList() 
    {
	    /**������ֵ����*/
	    ApplicationDataInfo applicationData = null;
	    ArrayList arrayList = new ArrayList();
      int j = getUpFilePanel().getMultiList().getTable().getRowCount();
	  if(fileVaultUsed)
	  {
		  ContentClientHelper helper = new ContentClientHelper();
	      for (int i = 0; i < j; i++)
	      {
	    	  String path = (String)getUpFilePanel().getMultiList().getCellText(i, 1);
	    	  try 
	    	  {
				applicationData =  helper.requestUpload(new File(path));
	    	  } catch (QMException e) 
	    	  {
				e.printStackTrace();
	    	  }
	    	  arrayList.add(applicationData);
	      }
	  }
	  else
	  {
	      for (int i = 0; i < j; i++)
	      {
	    	//����(3)2007.01.17 �촺Ӣ�޸�  �޸�ԭ��:�ֽ���û�б��浽���ݿ�,
	          //�����ά������ApplicationDataInfo������ֽ���.
	          Object[] object = new Object[2];
	          String fileName = (String)getUpFilePanel().
	                            getMultiList().getCellText(i, 0);
	          String path = (String)getUpFilePanel().
	                        getMultiList().
	                        getCellText(i, 1);
	          String length = (String) getUpFilePanel().
	                          getMultiList().
	                          getCellText(i, 2);
	          applicationData = new ApplicationDataInfo();
	          applicationData.setFileName(fileName);
	          applicationData.setUploadPath(path);
	          applicationData.setFileSize(Long.parseLong(length));
	          //����(3)2007.01.17 �촺Ӣ�޸� ����ļ��ֽ���
	          byte[] byteStream = getFileByte(path);
	          object[0] = applicationData;
	          object[1] = byteStream;
	          //����(3)2007.01.17 �촺Ӣ�޸�  ��object����ŵ�arrayList1��,���ͻ��˱�����
	          arrayList.add(object);
	      }
	  }
      return arrayList;
    }
    
    /**
     * ��ø���ʱ���еĸ�����Ϣ�����������ĸ�����
     */
    private ArrayList getArrayListupdate() 
    {
	    /**������ֵ����*/
	    ApplicationDataInfo applicationData = null;

        int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        ArrayList arrayList1 = new ArrayList();
    	if(fileVaultUsed)
   	  	{
    		ContentClientHelper helper = new ContentClientHelper();
    		for (int i = 0; i < j; i++)
            {
                String appDataID = (String) getUpFilePanel().
                                   getMultiList().getCellText(i, 3);
                if (appDataID == null)
                {
                    String path = (String) getUpFilePanel().
                                  getMultiList().
                                  getCellText(i, 1);
                    try 
      	    	  	{
	      				applicationData =  helper.requestUpload(new File(path));
	      	    	} catch (QMException e) 
	      	    	{
	      	    		e.printStackTrace();
	      	    	}
	      	    	arrayList1.add(applicationData);
                }
            }
   	  	}
    	else
    	{
    		for (int i = 0; i < j; i++)
            {
                String appDataID = (String) getUpFilePanel().
                                   getMultiList().getCellText(i, 3);
                if (appDataID == null)
                {
                    //����(3)2007.01.17 �촺Ӣ�޸�  �޸�ԭ��:�ֽ���û�б��浽���ݿ�,
                    //�����ά������ApplicationDataInfo������ֽ���.
                    Object[] object = new Object[2];
                    String fileName = (String)getUpFilePanel().
                                      getMultiList().
                                      getCellText(i, 0);
                    String path = (String) getUpFilePanel().
                                  getMultiList().
                                  getCellText(i, 1);
                    String length = (String)getUpFilePanel().
                                    getMultiList().
                                    getCellText(i, 2);
                    applicationData = new ApplicationDataInfo();
                    applicationData.setFileName(fileName);
                    applicationData.setUploadPath(path);
                    applicationData.setFileSize(Long.parseLong(length));
                    //����(3)2007.01.17 �촺Ӣ�޸� ����ļ��ֽ���
                    byte[] byteStream = getFileByte(path);
                    object[0] = applicationData;
                    object[1] = byteStream;
                    //����(3)2007.01.17 �촺Ӣ�޸�  ��object����ŵ�arrayList1��,���ͻ��˱�����
                    arrayList1.add(object);
                }
            }
    	}
      return arrayList1;
    }
    
    /**
     * ������Ϊʱԭ���տ���ӵ�еĸ�����Ϣ��
     */
    private Vector getVectorSaveAs() 
    {
    	  int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        Vector vec = new Vector();
        for (int i = 0; i < j; i++)
        {
            String appDataID = (String) getUpFilePanel().
                               getMultiList().getCellText(i, 3);
            if (appDataID != null)
            {
                vec.add(appDataID);
            }
        }
      return vec;
    }
    
    /**
     * �����ļ�·������ļ���
     * @param path String
     * @return byte[]
     */
    private byte[] getFileByte(String path)
    {
      File file = new File(path);
      long length = file.length();
      byte[] b = new byte[(int) length];
      try
      {
          FileInputStream in = new FileInputStream(file);
          in.read(b);
          in.close();
      }
      catch (FileNotFoundException ex1)
      {
      	ex1.printStackTrace();
      }
      catch (IOException ex2)
      {
      	ex2.printStackTrace();
      }
      return b;
    }
    
    
    /**
     * ���ö����������Ӹ�������Ϣ
     * @param equip QMEquipmentInfo
     */                                 
    private void setUpFileAccessaryName(TechnicsRouteListIfc upFileList)
    {
    	this.getUpFilePanel().getMultiList().clear();
      Vector vec = null;
      try 
      {
      	vec = getContents(upFileList);
		  }
		  catch (QMRemoteException e)
		  {
		  	e.printStackTrace();
		  }
		  if (vec == null)
		  {
		  	return;
		  }
		  int size = vec.size();
		  for (int m = 0; m < size; m++)
		  {
          ApplicationDataInfo applicationData = (ApplicationDataInfo)
                                                vec.elementAt(m);
          this.getUpFilePanel().getMultiList().addTextCell(m, 0,
                  applicationData.getFileName());
          this.getUpFilePanel().getMultiList().addTextCell(m, 1,
                  applicationData.getUploadPath());
          this.getUpFilePanel().getMultiList().addTextCell(m, 2,
                  String.valueOf(applicationData.getFileSize()));
          this.getUpFilePanel().getMultiList().addTextCell(m, 3,
                  applicationData.getBsoID());
          this.getUpFilePanel().setApplication(
                  applicationData);
      }
      this.getUpFilePanel().setRow(size);
    }
    
    /**
     * �õ�����������ָ����������
     * @param priInfo TechnicsRouteListIfc ��������
     * @return Vector ApplicationDataInfo ������
     * @throws RationException 
     */
    private Vector getContents(TechnicsRouteListIfc priInfo) throws QMRemoteException
    {
    	Class[] paraClass ={ContentHolderIfc.class};
    	Object[] obj ={priInfo};
    	Vector  c = (Vector) useServiceMethod(
                    "ContentService", "getContents", paraClass, obj);
		  return c;
    }
    
    private UpFilePanel getUpFilePanel()
    {
      return upFilePanel;
    }
    //CCEnd SS10
}

 /**
  * <p>Title:����Combo�¼���Ӧ������</p>
  * <p>Description: </p>
  */
class RouteListTaskJPanel_levelJComboBox_actionAdapter implements
        java.awt.event.ActionListener {
    private RouteListTaskJPanel adaptee;

    RouteListTaskJPanel_levelJComboBox_actionAdapter(RouteListTaskJPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.levelJComboBox_actionPerformed(e);
    }
    
    
}

//CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����Ĭ����Ч��˵��
class RouteListTaskJPanel_stateJComboBox_itemAdapter
implements java.awt.event.ItemListener {
RouteListTaskJPanel adaptee;

RouteListTaskJPanel_stateJComboBox_itemAdapter(RouteListTaskJPanel adaptee) {
this.adaptee = adaptee;
}

public void itemStateChanged(ItemEvent e) {
adaptee.stateJComboBox_itemStateChanged(e);
}
}    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��
