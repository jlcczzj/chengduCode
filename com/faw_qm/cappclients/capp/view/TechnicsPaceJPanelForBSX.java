/** ���ɳ���TechnicsPaceJPanel.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CCBegin by liunan 2010-12-17 �Ż�
 * CR1  2009/02/20   �촺Ӣ   ԭ���Ż�������������Ӧʱ��
 *                            �����������괴���Ĺ���֮�󣬲�����鿴����֮���ٽ�����½��棬���Ǳ��ֽ��治�����ܸ���
 *                            ��ע�����ܲ�������������"��������"
 * CR2  2009/02/24   �촺Ӣ   ԭ���Ż����¹�������Ӧʱ��
 *                            ��������������µĹ���֮�󣬲�������½��棬���Ǳ��ֽ��治�����ܸ���
 *                            ��ע�����ܲ�������������"���¹���"
 * CCEnd by liunan 2010-12-17
 * CCBegin by liunan 2011-5-27 �Ż�
 * CR3 2009/04/30     ����    ԭ�򣺼�ͼ�����Ĵ���ֻ����û������ļ�ͼ���д���
 *                            ������
 *                            ��ע�����ܲ����������ƣ�"������£������� ��������ճ��"��    
 * CCEnd by liunan 2011-5-27
 * SS1 �����������ĵ�ת�����ĵ�master�͹���Ĺ��� Liuyang 2013-3-19
 * SS2 �����乤������������ guoxiaoliang 2013-03-14
 * SS3 �����乤����Դ�����ܵ����� liunan 2014-5-19
 * SS4 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2842��������  2014-05-021
 * SS5 ���Ӽ�ͼ˳��ŵ��ظ���� liunan 2014-7-1
 * SS6 �޸ķ���ƽ̨����A005-2014-2973 guoxiaoliang 2014-07-03
 * SS7 �޸ķ���ƽ̨������A005-2014-2964
 * SS8 �޸ļ��빤���󹤲���ź����Ʋ���ʾ
 * SS9 SS7�����ߵĹ���׼����ʾ�������ֹ������Ż�ȡʱ��һ�£��˴�ֻȡ�˹��û�еĻ�û��ȡ��׼�š� liunan 2014-8-19
 * SS10 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2843��������  2014-05-07
 * SS11 ����������ʷ����ʱ��ֻ�õ�4�����У�û�����ݡ� liunan 2014-8-21
 * SS12 �����仯ʱ����ʾ���£���û�и������ݡ� liunan 2015-3-31
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMProcedureQMDocumentLinkInfo;
import com.faw_qm.capp.model.QMProcedureQMEquipmentLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMEquipmentLinkInfo;
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkInfo;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.procedure.consQMAssembleProcedureInfo;
import com.faw_qm.capp.model.procedure.consQMMachineProcedureInfo;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsLogic;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelForBSX;
import com.faw_qm.cappclients.beans.processtreepanel.OperationTreeObject;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttGroup;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolInfo;
import com.faw_qm.wip.model.WorkableIfc;
//CCBegin by chudaming 20091119 ���빤��
import javax.swing.JCheckBox;
//CCEnd by chudaming 20091119 ���빤��
//SS1 ��Ź�ʱĬ��Ϊ��ͱ����乤ʱĬ��Ϊ��leixiao 2013-9-11

/**
 * <p>Title:����ά�����</p>
 * <p>Ϊ���չ��������ṩ����</p>
 * <p>�ṩ������ʾģʽ(���������¡��鿴)����ɹ����Ĵ��������¡��鿴������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * ��1��20060728Ѧ���޸ģ�ԭ����ά�������Ĺ�ʱ����Դ����ʱ���Զ�ά������Ĺ�ʱ����Դ������������ʱ�������й�����ʱ�ĺͣ�
 *      ��ԴҲһ�����������ɿ����õ�,���������ļ��������Ƿ�Ҫά�����ֹ�ϵ.�޸ķ��� setUpdateMode()
 * ���⣨2��20080709 xucy add  �޸�ԭ�򣺹������������Ԥ��������
 */

public class TechnicsPaceJPanelForBSX extends TechnicsPaceJPanel
{
    /**�����������*/
    /**��ť���*/
    private JPanel buttonJPanel = new JPanel();

  //CCBegin by chudaming 20091119 ���빤��
	  private JLabel insertJLabel=new JLabel();
	  private JCheckBox insertCheckBox=new JCheckBox();
	//CCEnd by chudaming 20091119 ���빤��
    /**������*/
    private JLabel paceNumberJLabel = new JLabel();
    private CappTextField paceNumJTextField;
    private JLabel numDisplayJLabel = new JLabel();

    /**��������*/
    //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
   private JLabel paceNameJLabel = new JLabel();
   private CappTextField paceNameJTextField;
   private JLabel nameDisplayJLabel = new JLabel();
   //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������


    /**��ͼ���*/
    private ProcedureUsageDrawingPanel drawingLinkPanel;


    /**��������*/
    private JLabel technicsContentJLabel = new JLabel();
    private SpeCharaterTextPanel technicsContentJTextArea;


    /**��ʱ�������*/
    private ProcessHoursJPanel processHoursJPanel;


    /**������ϵ�������*/
    private JTabbedPane relationsJPanel = new JTabbedPane();


    /**��ť��*/
    /**��չ���ݰ�ť*/
    private JButton paraJButton = new JButton();


    /**���水ť*/
    private JButton saveJButton = new JButton();


    /**ȡ����ť*/
    private JButton cancelJButton = new JButton();


    /**�˳���ť*/
    private JButton quitJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;


    /**������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;


    /**������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;


    /**��ģʽ--�鿴*/
    private int mode = VIEW_MODE;


    /**����*/
    private QMProcedureInfo procedure;


    /**������*/
    private JFrame parentJFrame;


    /**��ѡ��Ĺ������ڵ�*/
    private CappTreeNode selectedNode;

    //20080702 xucy �޸�
    /**�豸�������*/
    private ProcedureUsageEquipmentJPanel equiplinkJPanel ;


    /**��װ�������*/
    private ProcedureUsageToolJPanel toollinkJPanel ;


    /**���Ϲ������*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**�ĵ��������*/
    private ProcedureUsageDocJPanel doclinkJPanel = new
            ProcedureUsageDocJPanel();


    /**��ⰴť*/
   // private JButton storageJButton = new JButton();

    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JPanel jPanel1 = new JPanel();


    /**��չ���ݽ������*/
    private TParamJDialog d = null;
    private EquipmentToolMaintainJDialog f1 = null;


    /**������*/
    private QMProcedureIfc parentProcedure;


    /**���򣨻򹤲��������Ĺ��տ�ͷ*/
    private QMTechnicsIfc parentTechnics;


    /**��¼�Ƿ��һ�ν���˽���*/
    private boolean firstInFlag = true;


    /**
     * ��������
     */
    private JLabel nulllabel = new JLabel();
    //add by wangh on 20070310
    private JPanel paceJPanel = new JPanel();
    private JPanel flowExtendJPanel = new JPanel();
    private JPanel femaExtendJPanel = new JPanel();
    private JPanel controlExtendJPanel = new JPanel();
    private JTabbedPane jTabbedPanel = new JTabbedPane();
    private Hashtable processFlowTable = new Hashtable();
    private Hashtable femaTable = new Hashtable();
    private Hashtable processControlTable = new Hashtable();
    private CappExAttrPanel processFlowJPanel;
    private CappExAttrPanel femaJPanel;
    //CCBegin SS2
    private CappExAttrPanel processControlJPanel;
//    private CappExAttrPanelForBSX processControlJPanel;
    //CCEnd SS2
    private JScrollPane scrollpane = new JScrollPane();
    //add by wangh on 20070326(�Ƿ���ʾTS16949�Ĺ�����߹�����Ϣ��)
    private static boolean ts16949 = (RemoteProperty.getProperty(
        "TS16949", "true")).equals("true");
    
    
//CCBegin SS2
    
    private Vector eqVec=new Vector();
    private Vector toolVec=new Vector();
    private Vector materiaVec=new Vector();
    CappAssociationsLogic taskLogic ;
    
    
    private Vector eqDeleVec=new Vector();
    private Vector toolDeleVec=new Vector();
    private Vector materiaDeleVec=new Vector();
    
//CCEnd SS2


    /**
     * ���캯��
     * @param parentframe ���ñ���ĸ�����
     */
    public TechnicsPaceJPanelForBSX(JFrame parentframe)
    {
    	super(parentframe);
        try
        {
            parentJFrame = parentframe;
            technicsContentJTextArea = new SpeCharaterTextPanel(parentframe);
            initSpeCharaterTextPanel();
            technicsContentJTextArea.speCharaterTextBean.setFont(new java.awt.
                    Font("Dialog", 0, 18));
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        
    }


    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        String title1 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.DRAWINGEXPORT, null);

        f1 = new EquipmentToolMaintainJDialog(parentJFrame);
        setLayout(gridBagLayout4);
        setSize(550, 450);
        String numDisp = QMMessage.getLocalizedMessage(RESOURCE, "paceNum", null);
        paceNumJTextField = new CappTextField(parentJFrame, numDisp, 10000, false);
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField = new CappTextField(parentJFrame, numDisp, 10000, false);
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        buttonJPanel.setLayout(gridBagLayout1);
        paceNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        paceNumberJLabel.setHorizontalTextPosition(SwingConstants.
                RIGHT);
        paceNumberJLabel.setText("*������");
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJLabel.setText("��������");
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame);
        //���⣨2��20080709 xucy add  �޸�ԭ�򣺹������������Ԥ��������
        //equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepTypeContent);
        //toollinkJPanel = new ProcedureUsageToolJPanel(stepTypeContent);
        //materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepTypeContent);


        technicsContentJLabel.setHorizontalAlignment(SwingConstants.
                RIGHT);
        technicsContentJLabel.setHorizontalTextPosition(
                SwingConstants.RIGHT);
        technicsContentJLabel.setText("��������");
        technicsContentJTextArea.setBorder(null);
        technicsContentJTextArea.setMaximumSize(new Dimension(32767,
                80));
        technicsContentJTextArea.setMinimumSize(new Dimension(5, 5));
        technicsContentJTextArea.setPreferredSize(new Dimension(5, 5));
        paraJButton.setMaximumSize(new Dimension(114, 23));
        paraJButton.setMinimumSize(new Dimension(114, 23));
        paraJButton.setPreferredSize(new Dimension(114, 23));
        paraJButton.setActionCommand("������Ϣ");
        paraJButton.setMnemonic('E');
        paraJButton.setRolloverEnabled(false);
        paraJButton.setText("������Ϣ(E)");
        paraJButton.addActionListener(new java.awt.event.
                                      ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                paraJButton_actionPerformed(e);
            }
        });
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setMnemonic('S');
        saveJButton.setText("����(S)");
        saveJButton.addActionListener(new java.awt.event.
                                      ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.
                                        ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        quitJButton.setMaximumSize(new Dimension(75, 23));
        quitJButton.setMinimumSize(new Dimension(75, 23));
        quitJButton.setPreferredSize(new Dimension(75, 23));
        quitJButton.setMnemonic('Q');
        quitJButton.setText("�˳�(Q)");
        quitJButton.addActionListener(new java.awt.event.
                                      ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
//        storageJButton.setMaximumSize(new Dimension(97, 23));
//        storageJButton.setMinimumSize(new Dimension(97, 23));
//        storageJButton.setPreferredSize(new Dimension(97, 23));
//        storageJButton.setMnemonic('T');
//        storageJButton.setText("���(ST)");
//        storageJButton.addActionListener(new java.awt.event.
//                                         ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                storageJButton_actionPerformed(e);
//            }
//        });


        numDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        numDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        numDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        nameDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        nameDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        nameDisplayJLabel.setPreferredSize(new Dimension(4, 22));


        splitJPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitJPanel.setDebugGraphicsOptions(0);
        splitJPanel.setMinimumSize(new Dimension(337, 357));
        splitJPanel.setPreferredSize(new Dimension(337, 357));
        splitJPanel.setContinuousLayout(true);
        splitJPanel.setOneTouchExpandable(true);
        splitJPanel.setResizeWeight(1.0);
        upJPanel.setLayout(gridBagLayout3);
        //CCBegin SS1
        processHoursJPanel = new
                             ProcessHoursJPanel(parentJFrame,true);
        //CCEnd SS1
        //drawFormatSortingSelectedPanel1.setVisible(false);
        // drawFormatJLabel.setVisible(false);
        downJPanel.setLayout(gridBagLayout2);
        downJPanel.setMinimumSize(new Dimension(337, 10));
        downJPanel.setPreferredSize(new Dimension(337, 243));

        upJPanel.setMinimumSize(new Dimension(337, 109));
        upJPanel.setPreferredSize(new Dimension(337, 109));
        processHoursJPanel.setMinimumSize(new Dimension(337, 22));
        processHoursJPanel.setPreferredSize(new Dimension(337, 22));
        paceNumJTextField.setMaximumSize(new Dimension(10, 24));
        paceNumJTextField.setMinimumSize(new Dimension(10, 24));
        paceNumJTextField.setPreferredSize(new Dimension(10, 24));
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField.setMaximumSize(new Dimension(10, 24));
        paceNameJTextField.setMinimumSize(new Dimension(10, 24));
        paceNameJTextField.setPreferredSize(new Dimension(10, 24));
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        paceNumJTextField.setMargin(new Insets(1, 1, 1, 1));
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField.setMargin(new Insets(1, 1, 1, 1));
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
      //CCBegin by chudaming 20091119 ���빤��
	    insertJLabel.setText("���빤��");
	    insertCheckBox.setSelected(true);
	  //CCEnd by chudaming 20091119 ���빤��
        nulllabel.setMaximumSize(new Dimension(6, 24));
        nulllabel.setMinimumSize(new Dimension(6, 24));
        nulllabel.setPreferredSize(new Dimension(6, 24));
        //add by wangh on 20070310
        paceJPanel.setLayout(gridBagLayout3);
        flowExtendJPanel.setLayout(gridBagLayout3);
        femaExtendJPanel.setLayout(gridBagLayout3);
        controlExtendJPanel.setLayout(gridBagLayout3);
        paceJPanel.setBorder(BorderFactory.createEtchedBorder());
        flowExtendJPanel.setBorder(BorderFactory.createEtchedBorder());
        femaExtendJPanel.setBorder(BorderFactory.createEtchedBorder());
        controlExtendJPanel.setBorder(BorderFactory.createEtchedBorder());
        jTabbedPanel.setMaximumSize(new Dimension(405, 32767));
        jTabbedPanel.setMinimumSize(new Dimension(405, 78));
        jTabbedPanel.setPreferredSize(new Dimension(405, 536));
        scrollpane.setBorder(null);
        jTabbedPanel.add(paceJPanel, "������Ϣ");
        System.out.println("ts16949================================================="+ts16949);
        if (ts16949) {
//          jTabbedPanel.add(flowExtendJPanel, "��������");
//          jTabbedPanel.add(femaExtendJPanel, "����FMEA");
        	
		jTabbedPanel.add(controlExtendJPanel, "���Ƽƻ�");
		jTabbedPanel.setSelectedIndex(1);
          
          
          
        }
        paceJPanel.add(scrollpane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 0, 0, 0), 0, 0));
        scrollpane.getViewport().add(splitJPanel, null);
        this.add(jTabbedPanel,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 2, 0, 0), 0, 0));

        this.add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 1.0,
                                                 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(4, 5, 4, 8),
                                                 163, 0));
        buttonJPanel.add(paraJButton, new GridBagConstraints(0, 0, 1,
                1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(quitJButton, new GridBagConstraints(5, 0, 1,
                1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(4, 0,
                1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton, new GridBagConstraints(3, 0, 1,
                1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
//        buttonJPanel.add(storageJButton, new GridBagConstraints(1, 0,
//                1, 1, 1.0, 0.0
//                , GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(jPanel1, new GridBagConstraints(2, 0, 1, 1,
                1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0,
                0));

        upJPanel.add(technicsContentJLabel, new GridBagConstraints(0,
                2, 1, 2, 0.0, 1.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 9, 0, 2), 0, 0));
        upJPanel.add(paceNumberJLabel, new GridBagConstraints(0, 0, 1,
                1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 15, 0, 2), 0, 0));
        upJPanel.add(paceNumJTextField,
                     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(2, 8, 2, 8), 0, 0));
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        upJPanel.add(paceNameJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(2, 8, 2, 8), 0, 0));
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        upJPanel.add(paceNameJTextField,
              new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
                                     , GridBagConstraints.WEST,
                                     GridBagConstraints.HORIZONTAL,
                                     new Insets(2, 8, 2, 8), 0, 0));

        upJPanel.add(nulllabel, new GridBagConstraints(2, 0, 2, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 8, 2, 8), 0, 0));

        upJPanel.add(processHoursJPanel, new GridBagConstraints(0, 4,
                4, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 15, 4, 8), 0,
                0));
        upJPanel.add(numDisplayJLabel,
                     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(2, 8, 2, 8), 0, 0));
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        upJPanel.add(nameDisplayJLabel, new GridBagConstraints(3, 0, 2,
               1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
               new Insets(0, 8, 0, 10), 0, 0));
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
      //CCBegin by chudaming 20091119 ���빤��
	    upJPanel.add(insertJLabel, new GridBagConstraints(4, 0, 1,
	        1, 0.0, 0.0
	        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
	        new Insets(0, 15, 0, 2), 0, 0));
	    upJPanel.add(insertCheckBox,
	                 new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
	                                        , GridBagConstraints.WEST,
	                                        GridBagConstraints.HORIZONTAL,
	                                        new Insets(2, 8, 2, 8), 0, 0));

	  //CCEnd by chudaming 20091119 ���빤��

        upJPanel.add(technicsContentJTextArea,
                     new GridBagConstraints(1, 3, 5, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(2, 8, 2, 8), 0, 0));
    splitJPanel.add(downJPanel, JSplitPane.BOTTOM);
    downJPanel.add(relationsJPanel, new GridBagConstraints(0, 0,
                1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 2, 2, 2), 0, 0));
        splitJPanel.add(upJPanel, JSplitPane.TOP);
        localize();
        splitJPanel.setDividerLocation(200);
        
        
      //CCBegin SS2
        taskLogic = new CappAssociationsLogic();
        //CCEnd SS2

    }


    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
        initResources();
        try
        {
            //JLabel
            paceNumberJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "paceNumberJLabel", null));

//            technicsContentJLabel.setText(QMMessage.
//                                          getLocalizedMessage(RESOURCE,
//                    "mtechContentJLabel", null));
            technicsContentJLabel.setText("��������");

            //JButton
            paraJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "ParaJButton", null));
            saveJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "QuitJButton", null));
//            storageJButton.setText(QMMessage.getLocalizedMessage(
//                    RESOURCE, "storageJButton", null));
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }


    /**
     * �����ڹ�������ѡ��ĸ��ڵ�
     * @param parentnode
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setParentProcedure();
        setTechnics();
    }
    //CCBegin SS2
    public void refreshObject(){
    	
    	
    	if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
    	{
            ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().newPartlinkJPanel(getProcedure().getTechnicsType().getCodeContent());
       }
    }
//CCEnd SS2
    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setCreateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setCreateMode() begin...");
        }
        clear();
        //CCBegin SS2
        System.out.println("existProcessType=================gggggggggggggggggggggggggggg==========================="+existProcessType);
        this.setProcedure((QMProcedureInfo) selectedNode.getObject().getObject());
        paceTS16949Panel(getProcedure().getTechnicsType().getCodeContent());
        refreshObject();
        //CCEnd SS2
        //�½�ģʽʱ�����ݸ����򣨲����Ĺ������࣬������ͬ�Ĺ�����
        QMProcedureInfo step = (QMProcedureInfo) selectedNode.
                               getObject().getObject();
        CodingIfc technicsTypeCodeInfo = step.getTechnicsType();
        try
        {
            setProcedure(instantiateQMProcedure(
                    technicsTypeCodeInfo.getCodeContent()));
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return;
        }
        getProcedure().setTechnicsType(technicsTypeCodeInfo);
        //���ÿؼ���ʾ״̬
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������w
        paceNumJTextField.setVisible(true);
        paceNameJTextField.setVisible(true);
        numDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������

        technicsContentJTextArea.speCharaterTextBean.setEditable(true);

        //���⣨2��20080820 �촺Ӣ�޸�     begin
        newEquiplinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(equiplinkJPanel != null)
        {
            equiplinkJPanel.setMode("Edit");
        }
        newToollinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(toollinkJPanel != null)
        {
        	toollinkJPanel.setMode("Edit");
        }
        newMateriallinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setMode("Edit");
        }
        doclinkJPanel.setMode("Edit");
        relationsJPanel.add(doclinkJPanel,
        "�ĵ�");
        relationsJPanel.add(drawingLinkPanel,
        "��ͼ");
        drawingLinkPanel.setModel(2); //EDIT
        //���⣨2��20080820 �촺Ӣ�޸�   end
        //�㲿������
        newPartlinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setMode("Edit");
            //�����ͼ���
        }
        processHoursJPanel.setCreateMode();
      //CCBegin by chudaming 20091119 ���빤��
	    this.insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 ���빤��
	    //CCBegin SS2
	    if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
	       ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.setSelected(false);
	    //CCEnd SS2
        f1.setEditMode();
        //{{��ʼ��������
        String parentBsoID = ((QMProcedureInfo) selectedNode.
                              getObject().getObject()).getBsoID();
        String technicsBsoID = parentTechnics.getBsoID();
        Class[] c =
                {
                String.class, String.class};
        Object[] objs =
                {
                technicsBsoID, parentBsoID};
        try
        {
            Integer maxNum = (Integer) useServiceMethod(
                    "StandardCappService", "getMaxNumber", c, objs);
            int number = maxNum.intValue();
            if (number == 0)
            {
                paceNumJTextField.setText(String.valueOf(
                        getStepInitNumber()));
                //CCBegin SS2
                if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
        	       ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.setText(String.valueOf(
                        getStepInitNumber()));
        	    //CCEnd SS2
            }
            else
            {
                if (number >= 99999)
                {
                    return;
                }
                paceNumJTextField.setText(String.valueOf(number +
                        getStepLong()));
              //CCBegin SS2
                if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
        	       ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.setText(String.valueOf(number +
                        getStepLong()));
        	    //CCEnd SS2

            }
            
             //CCBegin SS6
            if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
                ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNameJTextField.setText("");
 	            ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setCreateMode();
 	            ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.clear();
 	            ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().doclinkJPanel.clear();
 	            ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().partlinkJPanel.clear();
            }
 	    //CCEnd SS6
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.ERROR);
        }
        
        
        //CCBegin SS10
        String techType=this.getTechnicsType();
        if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
      	  
      	  paceNumJTextField.setEditable(false);
      	  paceNameJTextField.setEditable(false);
      	  insertCheckBox.setEnabled(false);
      	  technicsContentJTextArea.setEditable(false);
      	  processHoursJPanel.setEnabled(false);
      	  processHoursJPanel.setCreateMode();
      	  insertCheckBox.setEnabled(false);
      	  equiplinkJPanel.setMode("View");
      	  toollinkJPanel.setMode("View");
      	  materiallinkJPanel.setMode("View");
      	  doclinkJPanel.setMode("View");
      	  partlinkJPanel.setMode("View");
      	  drawingLinkPanel.setModel(1);
      	  
      	  
		  
	  }else{
		  
	          paceNumJTextField.setEditable(true);
	          paceNameJTextField.setEditable(true);
	          insertCheckBox.setEnabled(true);
	          technicsContentJTextArea.setEditable(true);
	          processHoursJPanel.setEnabled(true);
	          processHoursJPanel.setCreateMode();
	          insertCheckBox.setEnabled(true);
	          equiplinkJPanel.setMode("Edit");
	      	  toollinkJPanel.setMode("Edit");
	      	  materiallinkJPanel.setMode("Edit");
	      	  doclinkJPanel.setMode("Edit");
	      	  partlinkJPanel.setMode("Edit");
	      	  drawingLinkPanel.setModel(2);
		  
	  }
        //CCEnd SS10
        
        
        
        
        
        setButtonVisible(true);
        //CCBegin SS2
        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
           ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setMode("Edit");
        //CCEnd SS2
        repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setCreateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setUpdateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setUpdateMode() begin...");
        }
        clear();
      //CCBegin SS2
          
          paceTS16949Panel(getProcedure().getTechnicsType().getCodeContent());
          refreshObject();
        //CCEnd SS2
          
        //���ÿؼ���ʾ״̬
        paceNumJTextField.setVisible(true);
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField.setVisible(true);
        paceNumJTextField.setText(Integer.toString(getProcedure().
                getStepNumber()));
        
        paceNameJTextField.setText(getProcedure().
                getStepName());
        numDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
      //CCBegin by chudaming 20091119 ���빤��
	    insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 ���빤��
       System.out.println("getProcedure()=============================="+getProcedure());
       System.out.println("getProcedure().getStepNumber()()=============================="+getProcedure().getStepNumber());
       System.out.println("processControlJPanel.getPaceProcessControlJPanelForBSX()=============================="+processControlJPanel);
//CCBegin SS2
       if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
	      ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
	      ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNameJTextField.setText(getProcedure().getStepName());
	      ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.setSelected(false);
       }
//CCEnd SS2    

        //��ͼ���ͼ���������˳���ܱ䣬ԭ�����������ü�ͼ����������ü�ͼʱ���ڼ����¼��н���ͼ����ÿ�
        //��ͼ
        drawingLinkPanel.setProcedure(getProcedure());     
        drawingLinkPanel.setModel(2); //EDIT
        
        //CCBegin SS2
        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
        	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setProcedure(getProcedure());    
        	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setModel(2);
        }
        //CCEnd SS2
        
        
        technicsContentJTextArea.speCharaterTextBean.setEditable(true);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            technicsContentJTextArea.clearAll();
            technicsContentJTextArea.resumeData(v);
        }

        processHoursJPanel.setProcedure(getProcedure());
        //CCBegin SS2
        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
        	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
        //CCEnd SS2
        //�����ӽڵ�
        Collection c = null;
        //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ�Ĺ�ʱ���ɿ����õ�
        String hourUpdateflag = RemoteProperty.getProperty(
                "updateMachineHour", "true");
        if (hourUpdateflag.equals("true"))
        {

            try
            {
                c = CappTreeHelper.browseProcedures(parentTechnics.
                        getBsoID(),
                        getProcedure().getBsoID());
                if (c != null && c.size() != 0)
                {
                    //�����еĹ�ʱ�Ƿ������
                    boolean hourflag = false;
                    for (Iterator it = c.iterator(); it.hasNext(); )
                    {
                        QMProcedureInfo procedure = (QMProcedureInfo) it.
                                next();
                        if (procedure.getStepHour() > 0)
                        {
                            hourflag = true;
                            break;
                        }
                    }
                    if (hourflag)
                    {
                        processHoursJPanel.setViewMode();
                        //CCBegin SS2
                        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)

                        	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setViewMode();
                        //CCEnd SS2
                    }
                    else
                    {
                        processHoursJPanel.setEditMode();
                        //CCBegin SS2
                        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
                           ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                        //CCEnd SS2

                    }
                }
                else
                {
                    processHoursJPanel.setEditMode();
                    //CCBegin SS2
                    if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
                    	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                    //CCEnd  SS2
                }
            }
            catch (QMRemoteException ex)
            {
                ex.printStackTrace();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
        {
            processHoursJPanel.setEditMode();
            //CCBegin SS2
            if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)

                 ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
            //CCEnd SS2
        }

        //���⣨2��20080820 �촺Ӣ�޸�  begin
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
        equiplinkJPanel.setProcedure(getProcedure());
        equiplinkJPanel.setMode("Edit");
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        	toollinkJPanel.setProcedure(getProcedure());
        	toollinkJPanel.setMode("Edit");
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {
        	materiallinkJPanel.setProcedure(getProcedure());
        	materiallinkJPanel.setMode("Edit");
        }
        //20081120 �촺Ӣ�޸�   ����״̬���ĵ�Ӧ�ÿɱ༭
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel,
        "�ĵ�");
        
        //CCBegin SS2
        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
        	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX(). doclinkJPanel.setMode("Edit");
        	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setProcedure(getProcedure());
        }
        //CCEnd SS2
        
        relationsJPanel.add(drawingLinkPanel,
        "��ͼ");
        //���⣨2��20080820 �촺Ӣ�޸� end
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());

        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());
            partlinkJPanel.setMode("Edit");
            //CCBegin SS2
            if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
                ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setProcedure(getProcedure());
                ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setMode("Edit");
            }
            //CCEnd SS2
        }
        //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ����Դ���ɿ����õ�
        String resourceUpdateflag = RemoteProperty.getProperty(
                //CCBegin SS3
                //"updateResourceLink", "true");
                "updateResourceLink_bsx", "true");
                //CCEnd SS3
        if (resourceUpdateflag.equals("true"))
        {
            if (c == null)
            {
                try
                {
                    c = CappTreeHelper.browseProcedures(parentTechnics.
                            getBsoID(),
                            getProcedure().getBsoID());
                }
                catch (QMRemoteException ex)
                {
                    ex.printStackTrace();
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

            }

            if (c != null && c.size() != 0)
            {
                             //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                             //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                             //�ӽڵ�ʹ����Դ�ļ���
                             Collection linkCollection = null;
                             //��־�ӽڵ��Ƿ�ʹ������Դ
                             boolean eqflag = false;
                             boolean toolflag = false;
                             boolean matflag = false;
                             boolean partflag = false;
                             for (Iterator i = c.iterator(); i.hasNext(); )
                             {
                                 try
                                 {
                                     linkCollection = CappClientHelper.
                                                      getUsageResources(((BaseValueInfo) i.
                                             next())
                                             .getBsoID());
                                 }
                                 catch (QMRemoteException ex)
                                 {
                                     ex.printStackTrace();
                                     return;
                                 }
                                 if (linkCollection != null &&
                                     linkCollection.size() != 0)
                                 {
                                     for (Iterator it = linkCollection.iterator();
                                                        it.hasNext(); )
                                     {
                                         BinaryLinkIfc link = (BinaryLinkIfc) it.next();
                                         if (link instanceof
                                             QMProcedureQMEquipmentLinkIfc)
                                         {
                                             eqflag = true;
                                         }
                                         else
                                         if (link instanceof QMProcedureQMToolLinkIfc)
                                         {
                                             toolflag = true;
                                         }
                                         else
                                         if (link instanceof
                                             QMProcedureQMMaterialLinkIfc)
                                         {
                                             matflag = true;
                                         }
                                         if (link instanceof QMProcedureQMPartLinkIfc)
                                         {
                                             partflag = true;
                                         }
                                         if (eqflag && toolflag && matflag && partflag)
                                         {
                                             break;
                                         }
                                     }
                                 }
                                 if (eqflag && toolflag && matflag && partflag)
                                 {
                                     break;
                                 }
                             }
                             //���⣨2��20080820 �촺Ӣ�޸�
                             if (eqflag)
                             {
                                 equiplinkJPanel.setMode("View");
                             }
                             else
                             if (equiplinkJPanel != null)
                             {
                                 equiplinkJPanel.setMode("Edit");
                             }

                             if (toolflag)
                             {
                                 toollinkJPanel.setMode("View");
                             }
                             else
                             if (toollinkJPanel != null)
                             {
                                 toollinkJPanel.setMode("Edit");
                             }

                             if (matflag)
                             {
                                 materiallinkJPanel.setMode("View");
                             }
                             else
                             if (materiallinkJPanel != null)
                             {
                                 materiallinkJPanel.setMode("Edit");
                             }
                             if (partflag)
                             {
                                 partlinkJPanel.setMode("View");
                             }
                             else
                             if (partlinkJPanel != null)
                             {
                                 partlinkJPanel.setMode("Edit");
                             }

                         }

                         else
                         {
                        	 if (equiplinkJPanel != null)
                        	 {
                             equiplinkJPanel.setMode("Edit");
                        	 }
                        	 if(toollinkJPanel != null)
                        	 {
                             toollinkJPanel.setMode("Edit");
                        	 }
                        	 if(materiallinkJPanel != null)
                        	 {
                             materiallinkJPanel.setMode("Edit");
                        	 }
                             if (partlinkJPanel != null)
                             {
                                 partlinkJPanel.setMode("Edit");
                             }
                         }
                     }
                     else
                     {
                    	 if (equiplinkJPanel != null)
                    	 {
                         equiplinkJPanel.setMode("Edit");
                    	 }
                    	 if(toollinkJPanel != null)
                    	 {
                         toollinkJPanel.setMode("Edit");
                    	 }
                    	 if(materiallinkJPanel != null)
                    	 {
                         materiallinkJPanel.setMode("Edit");
                    	 }
                         if (partlinkJPanel != null)
                         {
                             partlinkJPanel.setMode("Edit");
                         }
                     }
        
        //CCBegin SS10
        String techType=this.getTechnicsType();
        if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
      	  
      	  paceNumJTextField.setEditable(false);
      	  paceNameJTextField.setEditable(false);
      	  insertCheckBox.setEnabled(false);
      	  technicsContentJTextArea.setEditable(false);
      	  processHoursJPanel.setEnabled(false);
      	  processHoursJPanel.setCreateMode();
      	  insertCheckBox.setEnabled(false);
      	  equiplinkJPanel.setMode("View");
      	  toollinkJPanel.setMode("View");
      	  materiallinkJPanel.setMode("View");
      	  doclinkJPanel.setMode("View");
      	  partlinkJPanel.setMode("View");
      	  drawingLinkPanel.setModel(1);
      	  
      	  
      	//CCBegin SS7
          //CCBegin SS4
          int conRows=((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getRowCount();
          System.out.println("conRows===="+conRows);
          if(conRows==0){
          	
          	
          	try {
          		
  				Vector OldEqLinkVec=equiplinkJPanel.getAllLinks();
//  				System.out.println("equiplinkJPanel============11111111111111111111==============================="+equiplinkJPanel);
//  				System.out.println("toollinkJPanel============11111111111111111111==============================="+toollinkJPanel);
  				Vector OldToolLinkVec=toollinkJPanel.getAllLinks();
  				
  				Vector oldMaterLinkVec=materiallinkJPanel.getAllLinks();
  					
//  				System.out.println("OldEqLinkVec============22222222222222222222==============================="+OldEqLinkVec.size());
//  				System.out.println("OldToolLinkVec============22222222222222222222==============================="+OldToolLinkVec.size());
  				
  				
//  				Vector OldMateriaLinkVec=materiallinkJPanel.getAllLinks();
  				
  				
  				int nowRow=0;
  				if(OldEqLinkVec.size()!=0){
  					
  					for(int a=0;a<OldEqLinkVec.size();a++){
  						
  					   QMProcedureQMEquipmentLinkInfo oldlinkInfo=(QMProcedureQMEquipmentLinkInfo)OldEqLinkVec.get(a);
  					   
  					   
  					   for (int b = 0; b < 4; b++) {
  						 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  						((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  						((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  							
  							nowRow++;
  						}
//  					   System.out.println("a====================================="+a);
//  					   System.out.println("nowRow================================"+nowRow);
  					   
  					   QMEquipmentInfo eqInfo = new QMEquipmentInfo();
  						eqInfo.setBsoID(oldlinkInfo.getRightBsoID());

  						eqInfo = (QMEquipmentInfo) this
  								.refreshInfo(eqInfo);
  						
  						

  						if(a==0){
  							
  							//CCBegin SS11
  							//processControlJPanel.groupPanel.multiList
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							//CCEnd SS11
  							.addTextCell(0, 20, eqInfo.getBsoID());
  						
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								.addTextCell(0, 1, eqInfo
  										.getEqNum());
  						  
  						   
  						  String eqModel=eqInfo.getEqModel();
  						  if(eqModel==null||eqModel.equals(""))
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								.addTextCell(1, 1, "--");
  							  
  						  else	  
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							.addTextCell(1, 1, eqInfo.getEqModel());
  						    
  						((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							.addTextCell(2, 1, eqInfo
  									.getEqName());
  						    
  						    String eqEqManu=eqInfo.getEqManu();
  							 if(eqEqManu==null||eqEqManu.equals(""))
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								   .addTextCell(3, 1, "--");
  							 else	 
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								   .addTextCell(3, 1, eqInfo.getEqManu());
  						    
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.setSelectedRow(4);
  							
  						}
  						else
  						{
  							
  							
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							.addTextCell(nowRow-4, 20, eqInfo.getBsoID());
  						
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								.addTextCell(nowRow-4, 1, eqInfo
  										.getEqNum());
  						  String eqModel=eqInfo.getEqModel();
  						  if(eqModel==null||eqModel.equals(""))
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								.addTextCell(nowRow-3, 1, "--");
  							  
  						  else	  
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							.addTextCell(nowRow-3, 1, eqInfo.getEqModel());
  						  
  						((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								.addTextCell( nowRow-2, 1, eqInfo
  										.getEqName());
  						   
  						   
  						 String eqEqManu=eqInfo.getEqManu();
  						 if(eqEqManu==null||eqEqManu.equals(""))
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							   .addTextCell(nowRow-1, 1, "--");
  						 else	 
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  							   .addTextCell(nowRow-1, 1, eqInfo.getEqManu());
  						 
  						((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.setSelectedRow(nowRow-1);
  						}
  					   
  					}
  					
  					
  				}
  				if(OldToolLinkVec!=null&&OldToolLinkVec.size()!=0){
  					
  					for (int i = 0; i < OldToolLinkVec.size(); i++) {
  						
  						QMProcedureQMToolLinkInfo oldToolLinkInfo = (QMProcedureQMToolLinkInfo) OldToolLinkVec
  								.get(i);

  						QMToolInfo toolInfo = new QMToolInfo();
  						toolInfo.setBsoID(oldToolLinkInfo.getRightBsoID());

  						toolInfo = (QMToolInfo) this.refreshInfo(toolInfo);
  						conRows=((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getRowCount();
  						if(conRows==0){
  							
  							if (!toolInfo.getToolCf().getCodeContent().equals("����")
  									&& !toolInfo.getToolCf().getCodeContent().equals("��������")
  									&& !toolInfo.getToolCf().getCodeContent().equals("ר������")
  									&& !toolInfo.getToolCf().getCodeContent().equals("���")
  									&& !toolInfo.getToolCf().getCodeContent().equals("���鸨��")
  									&& !toolInfo.getToolCf().getCodeContent().equals("����")
  									&& !toolInfo.getToolCf().getCodeContent().equals("����о�")
  									&& !toolInfo.getToolCf().getCodeContent().equals("����������")) {
  								
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  								
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(i, 22, toolInfo.getBsoID());
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(i, 2, toolInfo.getToolName());
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(i, 3, toolInfo.getToolNum());
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(i, 4, toolInfo.getToolSpec());
  								((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(i, 5, oldToolLinkInfo.getUsageCount());
  							}else{
  								for(int n=0;n<3;n++){
  									
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  									 nowRow++;
  								}
  								if(i==0){
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(0, 23, toolInfo.getBsoID());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(0, 14, toolInfo.getToolNum());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(1, 14, toolInfo.getToolName());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(2, 14, toolInfo.getToolSpec());
  								}else{
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-3, 23, toolInfo.getBsoID());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-3, 14, toolInfo.getToolNum());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-2, 14, toolInfo.getToolName());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-1, 14, toolInfo.getToolSpec());
  									
  								}
  								
  							}
  							
  						}else{
  							
  							 int rows= ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getRowCount();
  							
  							if (!toolInfo.getToolCf().getCodeContent().equals("����")
  									&& !toolInfo.getToolCf().getCodeContent().equals("��������")
  									&& !toolInfo.getToolCf().getCodeContent().equals("ר������")
  									&& !toolInfo.getToolCf().getCodeContent().equals("���")
  									&& !toolInfo.getToolCf().getCodeContent().equals("���鸨��")
  									&& !toolInfo.getToolCf().getCodeContent().equals("����")
  									&& !toolInfo.getToolCf().getCodeContent().equals("����о�")
  									&& !toolInfo.getToolCf().getCodeContent().equals("����������")) {
  								   int nullRow=0;
  								   for(int aa=0;aa<rows;aa++){
  									   
  							          String rowValue= ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getCellText(aa, 3);
  							          
  							          if(rowValue!=null&&rowValue.equals("")){
  							        	  
  							        	  nullRow=aa;
  							        	  
  							        	  break;
  							          }else{
  							        	  
  							        	  nullRow=-1;
  							          }
  							          
  								   }
  							       
  							       
  							       if(nullRow!=-1){
  							    	 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow, 22, toolInfo.getBsoID());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow, 2, toolInfo.getToolName());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow, 3, toolInfo.getToolNum());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow, 4, toolInfo.getToolSpec());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow, 5, oldToolLinkInfo.getUsageCount());
  							    	   
  							       }
  							       else{
  							    	 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  							    	   
  							    	   int allRow= ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getRowCount();
  							    	   
  							    	 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(allRow-1, 22, toolInfo.getBsoID());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(allRow-1, 2, toolInfo.getToolName());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(allRow-1, 3, toolInfo.getToolNum());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(allRow-1, 4, toolInfo.getToolSpec());
  							    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(allRow-1, 5, oldToolLinkInfo.getUsageCount());
  							    	   
  							    	   
  							       }
  								    
  							}else{
//  								 String rowValue= processControlJPanel.groupPanel.multiList.getCellText(i,14);
  								  int nullRow=0;
  								   for(int aa=0;aa<rows;aa++){
  									   
  							          String rowValue= ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getCellText(aa, 14);
  							          
  							          if(rowValue!=null&&rowValue.equals("")){
  							        	  
  							        	  nullRow=aa;
  							        	  
  							        	  break;
  							          }else{
  							        	  
  							        	  nullRow=-1;
  							          }
  							          
  							          
  								   }
  								   
  								 
  									   
  									   if(nullRow==-1){
  										   
  										   for(int n=0;n<3;n++){
  											 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  											((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  											((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  												 nowRow++;
  											}
  										   
  										 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-3, 23, toolInfo.getBsoID());
  										((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-3, 14, toolInfo.getToolName());
  										((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-2, 14, toolInfo.getToolNum());
  										((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nowRow-1, 14, toolInfo.getToolSpec());
  								    	   
  								       }
  								       else{
  								    	   
  								    	   
  								    	   int allRow= ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getRowCount();
  								    	   
  								    	 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow, 23, toolInfo.getBsoID());
  								    	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow,14, toolInfo.getToolNum());
  								          
  								           String nextvalue=((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getCellText(nullRow+1, 14);
  								           if(nextvalue==null){
  								        	 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  								        	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  								        	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  								           }
  								        //CCBegin SS9
  								         //((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow+1,14, toolInfo.getToolSpec());
  								        if((toolInfo.getToolSpec() == null || toolInfo.getToolSpec().length() == 0))
  								        {
  								        	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow+1,14, toolInfo.getToolStdNum());
  								        }
  								        else
  								        {
  								        	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow+1,14, toolInfo.getToolSpec());
  								        }
  								         //CCEnd SS9
  								           nextvalue=((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getCellText(nullRow+2, 14);
  								           if(nextvalue==null){
  								        	 ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  								        	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  								        	((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  								           }
  								         ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.addTextCell(nullRow+2, 14, toolInfo.getToolName());
  								           
  								    	   
  								    	   
  								       }
  									
  								
  							}
  								
  							
  							
  						}
  					}
  					
  				}
  				
  				if (oldMaterLinkVec != null && oldMaterLinkVec.size() != 0) {

  					for (int i = 0; i < oldMaterLinkVec.size(); i++) {

  						QMProcedureQMMaterialLinkInfo oldMaterLinkInfo = (QMProcedureQMMaterialLinkInfo) oldMaterLinkVec
  								.get(i);

  						QMMaterialInfo materialInfo = new QMMaterialInfo();
  						materialInfo.setBsoID(oldMaterLinkInfo.getRightBsoID());

  						materialInfo = (QMMaterialInfo) this
  								.refreshInfo(materialInfo);
  						conRows = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  								.getRowCount();

  						if (conRows == 0) {
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();

  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  									.addTextCell(i, 21, materialInfo.getBsoID());
  							((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  									.addTextCell(i, 1, materialInfo
  											.getMaterialName());

  						} else {
  							int rows = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  									.getRowCount();
  							int nullRow = 0;
  							for (int aa = 0; aa < rows; aa++) {

  								String rowValue = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  										.getCellText(aa, 1);

  								if (rowValue != null && rowValue.equals("")) {

  									nullRow = aa;

  									break;
  								} else {

  									nullRow = -1;
  								}

  								if (nullRow != -1) {

  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  											.addTextCell(nullRow, 21,
  													materialInfo.getBsoID());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  											.addTextCell(nullRow, 1,
  													materialInfo
  															.getMaterialName());

  								} else {
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.addNewRow();
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList.getTable().clearSelection();
  									int allRow = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  											.getRowCount();
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  											.addTextCell(allRow - 1, 21,
  													materialInfo.getBsoID());
  									((CappExAttrPanelForBSX)processControlJPanel).groupPanel.multiList
  											.addTextCell(allRow - 1, 1,
  													materialInfo
  															.getMaterialName());

  								}
  							}

  						}
  					}

  				}
  				
  				
  				
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
          }
          // CCEnd SS4
          //CCEnd SS7
      	  
      
      	  
		  
	  }else{
		  
	          paceNumJTextField.setEditable(true);
	          paceNameJTextField.setEditable(true);
	          insertCheckBox.setEnabled(true);
	          technicsContentJTextArea.setEditable(true);
	          processHoursJPanel.setEnabled(true);
	          processHoursJPanel.setCreateMode();
	          insertCheckBox.setEnabled(true);
	          equiplinkJPanel.setMode("Edit");
	      	  toollinkJPanel.setMode("Edit");
	      	  materiallinkJPanel.setMode("Edit");
	      	  doclinkJPanel.setMode("Edit");
	      	  partlinkJPanel.setMode("Edit");
	      	  drawingLinkPanel.setModel(2);
		  
	  }
        //CCEnd SS10

        doclinkJPanel.setMode("Edit");
        f1.setEditMode();
        setButtonVisible(true);

        
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setUpdateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ�鿴ģʽ
     */
    private void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() begin...");
        }
        clear();
        //���ÿؼ���ʾ״̬
        paceNumJTextField.setVisible(false);
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField.setVisible(false);
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        numDisplayJLabel.setVisible(true);
        nameDisplayJLabel.setText(getProcedure().getStepName());
        nameDisplayJLabel.setVisible(true);
      //CCBegin by chudaming 20091119 ���빤��
	    insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 ���빤��


	  //CCBegin SS8
	       if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
		      ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
		      ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNameJTextField.setText(getProcedure().getStepName());
		      ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.setSelected(false);
	       }
	//CCEnd SS8  
	    
	    

        technicsContentJTextArea.speCharaterTextBean.setEditable(false);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            technicsContentJTextArea.resumeData(v);
        }
        processHoursJPanel.setProcedure(getProcedure());
        processHoursJPanel.setViewMode();
        setButtonVisible(false);
        //���⣨2��20080820 �촺Ӣ�޸� begin
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
        equiplinkJPanel.setMode("View");
        equiplinkJPanel.setProcedure(getProcedure());
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        toollinkJPanel.setMode("View");
        toollinkJPanel.setProcedure(getProcedure());
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setMode("View");
        materiallinkJPanel.setProcedure(getProcedure());
        }
        doclinkJPanel.setMode("View");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel,
        "�ĵ�");
        drawingLinkPanel.setModel(1); //VIEW
        drawingLinkPanel.setProcedure(getProcedure());  
        relationsJPanel.add(drawingLinkPanel,
        "��ͼ");
        //���⣨2��20080820 �촺Ӣ�޸�  end
        //part����
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setMode("View");
            partlinkJPanel.setProcedure(getProcedure());
        }
        //add by wangh on 20070310
        paceTS16949Panel(getProcedure().getTechnicsType().getCodeContent());
        f1.setViewMode();

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() end...return is void");
        }
    }


    /**
     * ��ȡ��ǰѡ��Ĺ����Ĺ��տ�ͷ
     * @return
     */
    private void setTechnics()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setTechnics() begin...");
        }
        CappTreeNode parentNode = (CappTreeNode) selectedNode.
                                  getParent();
        BaseValueInfo baseValueInfo = (BaseValueInfo) parentNode.
                                      getObject().getObject();
        while (!(baseValueInfo instanceof QMFawTechnicsInfo))
        {
            parentNode = (CappTreeNode) parentNode.getParent();
            baseValueInfo = (BaseValueInfo) parentNode.getObject().
                            getObject();
        }
        parentTechnics = (QMFawTechnicsInfo) baseValueInfo;
        if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)
                                                 parentTechnics)
            &&
            !CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) parentTechnics))
        {
            try
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);
            }
            catch (QMRemoteException ex)
            {
                ex.printStackTrace();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setTechnics() end...return: void");

        }
    }


    /**
     * ���¹���ʱ����ø����򣨲���
     * @return
     */
    private void setParentProcedure()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setParentProcedure() begin...");
        }
        CappTreeNode parentNode = (CappTreeNode) selectedNode.
                                  getParent();
        BaseValueInfo info = (BaseValueInfo) parentNode.getObject().
                             getObject();
        if (info instanceof QMProcedureInfo)
        {
            parentProcedure = (QMProcedureInfo) info;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsPaceJPanel.setParentProcedure() end...return void ");

        }
    }


    /**
     * ���õ�ǰ����
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedure = info;
    }


    /**
     * ��ȡ��ǰ����
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedure;
    }


    /**
     * ��������ļ��еļ�ͼ�����ʽ
     * @return ��ͼ�����ʽ����
     */
    /* public Vector getDrawingFormat()
     {
         return findCodingInfo("QMProcedure", "drawingFormat");
     }*/


    /**
     * �����������(���)�Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkIsEmpty()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        isOK = paceNumJTextField.check();
        
        
//        if (isOK)
//        {
//            //���鹤�������Ƿ�Ϊ��
//            if (technicsContentJTextArea.save() == null ||
//                technicsContentJTextArea.save().trim().equals(""))
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                        null);
//                isOK = false;
//                technicsContentJTextArea.getTextComponent().grabFocus();
//            }
//            else
//            {
//            	String tempString=technicsContentJTextArea.save().trim();
//            	if(1==tempString.length())
//            	{
//            		int tempChar=tempString.charAt(0);
//            		if(tempChar==128)
//            		{
//            			message = QMMessage.getLocalizedMessage(RESOURCE,
//                                CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                                null);
//                        isOK = false;
//                        technicsContentJTextArea.getTextComponent().grabFocus();
//            		}
//            	}
//            }
//
//        }

        if (!isOK && message != null)
        {
            //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
            title = QMMessage.getLocalizedMessage(RESOURCE,
                                                  CappLMRB.
                                                  REQUIRED_FIELDS_MISSING,
                                                  null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() end...return: " +
                    isOK);
        }

        return isOK;
    }

 //CCBegin SS2
    
    
    /**
     * �����������(��š�����)�Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkIsEmptyForProcessControl()
    {
        
        
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        //CCBegin SS2
        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
           isOK = ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.check();
        else
           isOK = paceNumJTextField.check();
        //CCEnd SS2

        if (!isOK && message != null)
        {
            //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
            title = QMMessage.getLocalizedMessage(RESOURCE,
                                                  CappLMRB.
                                                  REQUIRED_FIELDS_MISSING,
                                                  null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() end...return: " +
                    isOK);
        }

        return isOK;
        
        
        
        
        
    }
    
    //CCEnd SS2
    
    
    
    
    
    /**
     * ���ý���ģʽ�����������»�鿴����
     * @param aMode �½���ģʽ
     * @exception java.beans.PropertyVetoException ���ģʽMode��Ч�����׳��쳣��
     */
    public void setViewMode(int aMode)
            throws PropertyVetoException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() begin...");
        }
        if ((aMode == UPDATE_MODE) ||
            (aMode == CREATE_MODE) ||
            (aMode == VIEW_MODE))
        {
            mode = aMode;
        }
        else
        {
            //��Ϣ����Чģʽ
            throw (new PropertyVetoException(QMMessage.
                                             getLocalizedMessage(RESOURCE,
                    "invalid Mode", null),
                                             new PropertyChangeEvent(this,
                    "mode",
                    new Integer(getViewMode()),
                    new Integer(aMode))));
        }

        switch (aMode)
        {

            case CREATE_MODE: //����ģʽ
            {
                setCreateMode();
                break;
            }

            case UPDATE_MODE: //����ģʽ
            {
                setUpdateMode();
                break;
            }

            case VIEW_MODE: //�鿴ģʽ
            {
                setViewMode();
                break;
            }

        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() end...return is void");
        }
    }


    /**
     * ��ý���ģʽ
     * @return ���������¡��鿴�����Ϊģʽ
     */
    public int getViewMode()
    {
        return mode;
    }

    private String existProcessType = "";


    /**
     * ���ݹ������������չ����ά��
     * @param e ActionEvent
     */
    void paraJButton_actionPerformed(ActionEvent e)
    {
        //��ù�������(�������ݱ��)
        String processType = "";
        if (getViewMode() == CREATE_MODE)
        {
            processType = ((QMProcedureInfo) selectedNode.
                           getObject().
                           getObject()).getTechnicsType().getCodeContent().trim();
        }
        if (getViewMode() == UPDATE_MODE ||
            getViewMode() == VIEW_MODE)
        {
            processType = getProcedure().getTechnicsType().
                          getCodeContent().trim();
        }
        if (!processType.equals(""))
        {
            //���������ģʽ��
            if (mode == CREATE_MODE || mode == UPDATE_MODE)
            {
                //���existProcessType��Ϊ�գ��ı��˹������࣬��ѹ���������չ�����ÿ�
                if (!existProcessType.equals("") &&
                    !existProcessType.equals(processType))
                {
                    getProcedure().setExtendAttributes(null);
                }

                d = new TParamJDialog(procedure.getBsoName(), parentJFrame,
                                      "");
                d.setProcedure(getProcedure());
                d.setEditMode();
                d.setVisible(true);
                //������չ����
                ExtendAttContainer c = d.getExtendAttributes();
                if (c != null)
                {
                    if (d.check())
                    {
                        getProcedure().setExtendAttributes(c);
                    }
                    else
                    {
                        if (verbose)
                        {
                            System.out.println("��չ����¼�����");
                        }
                        return;
                    }
                }
                existProcessType = processType;
            }
            //�鿴ģʽ
            if (getViewMode() == VIEW_MODE)
            {
                d = new TParamJDialog(procedure.getBsoName(), parentJFrame,
                                      "");
                d.setProcedure(getProcedure());
                d.setViewMode();
                d.setVisible(true);
            }
        }
    }


    /**
     * �������
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        WorkThread thread = new WorkThread();
        thread.start();
    }


    /**
     * ����
     */
    private void save()
    {
        if (getViewMode() == CREATE_MODE)
        {
        	
        	
        	
        	//CCBegin SS2
        	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
			.getSelectedObject().getObject();
        	String techType="";
        	if (obj instanceof consQMAssembleProcedureInfo) {
				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
        	} else if (obj instanceof QMFawTechnicsInfo) {

				QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

				techType = info.getTechnicsType().getCodeContent();

			}else if(obj instanceof consQMMachineProcedureInfo){
				
				consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
				
			}
        	
        	
        	  if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
      				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
        		  
        		  
        		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        		  
        		  saveWhenCreateForProcessControl();
        		  
              	
              }else{
            	  
            	  
            	  saveWhenCreate();
              }
        	  
        	  //CCEnd SS2
        	
        	
        }
        else if (getViewMode() == UPDATE_MODE)
        {
        	
        	
        	//CCBegin SS2
        	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
			.getSelectedObject().getObject();
        	String techType="";
        	if (obj instanceof consQMAssembleProcedureInfo) {
				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
        	} else if (obj instanceof QMFawTechnicsInfo) {

				QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

				techType = info.getTechnicsType().getCodeContent();

			}else if(obj instanceof consQMMachineProcedureInfo){
				
				consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
				
			}
        	
        	
        	  if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
      				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
        		  
        		  
        		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        		  
        		  saveWhenUpdateForProcessControl();
        		  
              	
              }else{
            	  
            	  
                 saveWhenUpdate();
              }
        	  
        	  //CCEnd SS2
        	
        	
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();

    }


    /**
     * ȡ������
     * @param e
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        if (getViewMode() == CREATE_MODE)
        {
            cancelWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)
        {
            cancelWhenUpdate();
        }
    }


    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���
     * <p>���Ѿ�¼������ݶ��ÿ�</p>
     */
    private void cancelWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.CancelWhenCreate() begin...");
        }
        String num = paceNumJTextField.getText();
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        String name=paceNameJTextField.getText();
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        setCreateMode();
        paceNumJTextField.setText(num);
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField.setText(name);
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������

        /* paceNumJTextField.setText("");
         drawingExportJComboBox.setSelectedIndex(0);
         technicsContentJTextArea.clearAll();
         processHoursJPanel.clear();
         equiplinkJPanel.clear();
         materiallinkJPanel.clear();
         toollinkJPanel.clear();
         doclinkJPanel.clear();
         drawingPanel.setDrawingDate(null);*/
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsPaceJPanel.CancelWhenCreate() end...return is void");
        }
    }



    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���
     * <p>������¼������ݳ����������ϴ�¼�������</p>
     */
    private void cancelWhenUpdate()
    {
        setUpdateMode();
    }


    /**
     * �˳�����
     * @param e
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();

    }

    protected boolean quit()
    {
        if (getViewMode() == CREATE_MODE)
        {
            quitWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)
        {
            quitWhenUpdate();
        }
        else if (getViewMode() == VIEW_MODE)
        {
            quitWhenView();
        }
        return isSave;
    }


    /**����Ƿ�����˳�*/
    private boolean isSave = true;

    private ProcedureUsagePartJPanel partlinkJPanel;
    private JSplitPane splitJPanel = new JSplitPane();
    private JPanel upJPanel = new JPanel();
    private JPanel downJPanel = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.QuitWhenCreate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 IS_SAVE_QMPROCEDURE_PACE, null);
        if (confirmAction(s))
        {
            saveWhenCreate();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.QuitWhenCreate() end...return is void");
        }
    }


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenUpdate()
    {
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 IS_SAVE_QMPROCEDURE_PACE_UPDATE, null);
        //CCBegin by liunan 2011-6-1 �Ż�
        /*if (confirmAction(s))
        {
            saveWhenUpdate();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }*/
        boolean ischange=TechnicsContentJPanel.addFocusLis.finalChangeValue();
        if(ischange)
        {
        	if (confirmAction(s))
        	{
        		//CCBegin SS12
            //saveWhenUpdate();
            saveWhenUpdateForProcessControl();
            //CCEnd SS12
          }
          else
          {
            setVisible(false);
            isSave = true;
          }
        }
        else
        {
        	setVisible(false);
        	isSave = true;
        }
        //CCEnd by liunan 2011-6-1
    }


    /**
     * �鿴ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenView()
    {
        setVisible(false);
        isSave = true;
    }


    /**
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return  ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(),
                                              s, title,
                                              JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    /**
     * ���ù��������������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     */
    private CappWrapData commitAttributes()
    {
    	//CCBegin by liunan 2011-2-11 �Ż�
    	this.setProcedure(this.getProcedure());//CR1
    	//CCEnd by liunan 2011-2-11
        //�����ǹ���
        getProcedure().setIsProcedure(false);
        //���ñ��
        if (paceNumJTextField.getText().length() > 5)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEP_NUMBER_INVALID, null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.PACENUMBER_TOO_LONG, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.WARNING_MESSAGE);
            paceNumJTextField.grabFocus();
            return null;
        }
        Integer i = Integer.valueOf(paceNumJTextField.getText().trim());
        getProcedure().setStepNumber(i.intValue());
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        getProcedure().setStepName(paceNameJTextField.getText().trim());
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������

        //���ù�������
        Vector v = new Vector();
        v.addElement(technicsContentJTextArea.save());
        getProcedure().setContent(v);
        //���㵥����ʱ
        if (processHoursJPanel.getMode() == "EDIT")
        {
            try
            {
                processHoursJPanel.setProcedure(getProcedure());
                processHoursJPanel.setHours();
                
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.SAVE_STEP_FAILURE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                processHoursJPanel.clear();
                return null;
            }
        }
        if (verbose)
        {
            System.out.println(">>>>>>>>>>>>> getAidTime = " +
                               getProcedure().getAidTime());
            System.out.println(">>>>>>>>>>>>> getmachineHour = " +
                               getProcedure().getMachineHour());
        }
        QMProcedureInfo step = (QMProcedureInfo) selectedNode.
                               getObject().getObject();
        //�½�ģʽʱ���������ϼ�
        if (getViewMode() == CREATE_MODE)
        {
            procedure.setLocation(((QMProcedureInfo) selectedNode.getObject().
                                   getObject()).getLocation());

        }
        //ά�����ղ���
        if (d != null)
        {
            ExtendAttContainer c = d.getExtendAttributes();
            if (c != null)
            {
                if (d.check())
                {
                    getProcedure().setExtendAttributes(c);
                }
                else
                {
                    if (verbose)
                    {
                        System.out.println("��չ����¼�����");
                    }
                    isSave = false;
                    return null;
                }
            }
        }
        //add by wangh on 20070310
//        if (processFlowJPanel.check())
//        {
//            //���ù�������
//            procedure.setProcessFlow(processFlowJPanel.
//                                             getExAttr());
//        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("������������¼�����");
//            }
//            isSave = false;
//            return null;
//        }
//        if (femaJPanel.check()) {
//          //����FEMA
//          procedure.setFema(femaJPanel.getExAttr());
//        }
//        else {
//          if (verbose) {
//            System.out.println("����FMEA¼�����");
//          }
//          isSave = false;
//          return null;
//        }
        if (processControlJPanel.check())
        {
            //���ù��̿���
            procedure.setProcessControl(processControlJPanel.
                                             getExAttr());
        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("�������̿���¼�����");
//            }
//            isSave = false;
//            return null;
//        }


        //������й���(�豸����װ�����ϡ��ĵ�)
        Vector docLinks = new Vector();
        Vector equipLinks = new Vector();
        Vector toolLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector partLinks = new Vector();
        //CCBegin by liunan 2011-5-27 �Ż�
        //ArrayList pDrawings = null;
        //ArrayList drawingLinks = null;
        //CCBegin SS1
        Vector docMasterLinks = new Vector();
        //CCEnd SS1
        ArrayList updatedrawings = null;//Begin CR3
        ArrayList deletedrawings = null;//End CR3
        //CCEnd by liunan 2011-5-27
        try
        {
            docLinks = doclinkJPanel.getAllLinks();
            //CCBegin SS1
            int size = docLinks.size();
            for (int j = 0; j < size; j++)
            {
                String docId = ((QMProcedureQMDocumentLinkInfo) docLinks.
                                elementAt(j)).getRightBsoID();
                Class[] paraClass1 =
                        {String.class};
                Object[] objs1 =
                        {docId};
                //DocMasterInfo masterInfo = new DocMasterInfo();
                String masterID = "";
                try
                {
                    BaseValueInfo docInfo = (BaseValueInfo)
                                            useServiceMethod(
                            "PersistService", "refreshInfo", paraClass1,
                            objs1);
                    if (docInfo instanceof DocInfo)
                    {
                        masterID = ((DocInfo) docInfo).getMasterBsoID();
                        QMProcedureQMDocumentLinkInfo linkInfo = new
                                QMProcedureQMDocumentLinkInfo();
                        linkInfo.setRightBsoID(masterID);
                        linkInfo.setLeftBsoID(((
                                QMProcedureQMDocumentLinkInfo)
                                               docLinks.elementAt(j)).
                                              getLeftBsoID());
                        docMasterLinks.add(linkInfo);
                    }
                    else
                    if (docInfo instanceof DocMasterInfo)
                    {
                        docMasterLinks.add((QMProcedureQMDocumentLinkInfo)
                                           docLinks.elementAt(j));
                    }
                    //displayString = getIdentity(relationTechnics);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }
                //CCEnd SS1
            }
            //���⣨2��20080820 �촺Ӣ�޸�   �޸�ԭ�򣺹�����������Ԥ������
            if (equiplinkJPanel != null)
            {
            equipLinks = equiplinkJPanel.getAllLinks();
            }
            if (toollinkJPanel != null)
            {
            toolLinks = toollinkJPanel.getAllLinks();
            }
            if (materiallinkJPanel != null)
            {
            materialLinks = materiallinkJPanel.getAllLinks();
            }
            if (partlinkJPanel != null)
            {
                partLinks = partlinkJPanel.getAllLinks();
            }
            Object[] obj = drawingLinkPanel.getDrawings();
            if (obj != null)
            {
            	//CCBegin by liunan 2011-5-27 �Ż�
              //  pDrawings = (ArrayList) obj[0];
              //  drawingLinks = (ArrayList) obj[1];
              updatedrawings = (ArrayList) obj[0];//Begin CR3
              deletedrawings = (ArrayList) obj[1];//End CR3
              //CCEnd by liunan 2011-5-27
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            isSave = false;
            return null;
        }
        //�����й����ϲ�
        Vector resourceLinks = new Vector();
        //�ϲ��ĵ�����
      //CCBegin SS1
//        for (int k = 0; k < docLinks.size(); k++)
//
//        {
//            resourceLinks.addElement(docLinks.elementAt(k));
//        }
        for (int k = 0; k < docMasterLinks.size(); k++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(k));
        }
        //CCEnd SS1
        //�ϲ����Ϲ���
        for (int j = 0; j < materialLinks.size(); j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }
        //�ϲ��豸����
        for (int m = 0; m < equipLinks.size(); m++)
        {
            resourceLinks.addElement(equipLinks.elementAt(m));
        }
        //�ϲ���װ����
        for (int n = 0; n < toolLinks.size(); n++)
        {
            resourceLinks.addElement(toolLinks.elementAt(n));
        }
        //�ϲ��㲿������
        if (partLinks != null)
        {
            for (int n = 0; n < partLinks.size(); n++)
            {
                resourceLinks.addElement(partLinks.elementAt(n));
            }
        }
        //CCBegin by liunan 2011-5-27 �Ż�
        //�ϲ���ͼ��Դ����   Begin CR3
        /*if(drawingLinks!=null)
        {
            for (int n = 0; n < drawingLinks.size(); n++)
            {
                resourceLinks.addElement(drawingLinks.get(n));
            }
        }*/
        //End CR3
        //CCEnd by liunan 2011-5-27


        //��װ������Ϣ
        CappWrapData cappWrapData = new CappWrapData();
        //���ù���
        cappWrapData.setQMProcedureIfc(getProcedure());
        //��װ����
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
        //CCBegin by liunan 2011-5-27 �Ż�
        //cappWrapData.setPDrawings(pDrawings);
        cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR3
        cappWrapData.setDeleteDrawing(deletedrawings);//End CR3
        //CCEnd by liunan 2011-5-27
        return cappWrapData;
    }
    
    //CCBegin SS2
    
    /**
     * ���ù��������������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     */
    private CappWrapData commitAttributesForProcessControl()
    {
    	//CCBegin by liunan 2011-2-11 �Ż�
    	this.setProcedure(this.getProcedure());//CR1
    	//CCEnd by liunan 2011-2-11
        //�����ǹ���
        getProcedure().setIsProcedure(false);
        //���ñ��
        if (((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.getText().length() > 5)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEP_NUMBER_INVALID, null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.PACENUMBER_TOO_LONG, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.WARNING_MESSAGE);
            ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX(). paceNumJTextField.grabFocus();
            return null;
        }
        Integer i = Integer.valueOf(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.getText().trim());
        getProcedure().setStepNumber(i.intValue());
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        getProcedure().setStepName(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNameJTextField.getText().trim());
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������

        //���ù�������
        Vector v = new Vector();
        v.addElement(technicsContentJTextArea.save());
        getProcedure().setContent(v);
        //���㵥����ʱ
        if (((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.getMode() == "EDIT")
        {
            try
            {
            	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
            	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.setHours();
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.SAVE_STEP_FAILURE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().processHoursJPanel.clear();
                return null;
            }
        }
        if (verbose)
        {
            System.out.println(">>>>>>>>>>>>> getAidTime = " +
                               getProcedure().getAidTime());
            System.out.println(">>>>>>>>>>>>> getmachineHour = " +
                               getProcedure().getMachineHour());
        }
        QMProcedureInfo step = (QMProcedureInfo) selectedNode.
                               getObject().getObject();
        //�½�ģʽʱ���������ϼ�
        if (getViewMode() == CREATE_MODE)
        {
            procedure.setLocation(((QMProcedureInfo) selectedNode.getObject().
                                   getObject()).getLocation());

        }
        //ά�����ղ���
        if (d != null)
        {
            ExtendAttContainer c = d.getExtendAttributes();
            if (c != null)
            {
                if (d.check())
                {
                    getProcedure().setExtendAttributes(c);
                }
                else
                {
                    if (verbose)
                    {
                        System.out.println("��չ����¼�����");
                    }
                    isSave = false;
                    return null;
                }
            }
        }
        //add by wangh on 20070310
//        if (processFlowJPanel.check())
//        {
//            //���ù�������
//            procedure.setProcessFlow(processFlowJPanel.
//                                             getExAttr());
//        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("������������¼�����");
//            }
//            isSave = false;
//            return null;
//        }
//        if (femaJPanel.check()) {
//          //����FEMA
//          procedure.setFema(femaJPanel.getExAttr());
//        }
//        else {
//          if (verbose) {
//            System.out.println("����FMEA¼�����");
//          }
//          isSave = false;
//          return null;
//        }
        if (processControlJPanel.check())
        {
            //���ù��̿���
            procedure.setProcessControl(processControlJPanel.
                                             getExAttr());
            
            this.eqVec.clear();
        	this.toolVec.clear();
        	this.materiaVec.clear();
        	
        	this.eqDeleVec.clear();
        	this.toolDeleVec.clear();
        	this.materiaDeleVec.clear();
        	
        	
        	
        	if (procedure != null) {
				Vector eqDeleteVec = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.eqDeleteVec;
				Vector materDeleteVec = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.materDeleteVec;
				Vector toolDeleteVec = ((CappExAttrPanelForBSX)processControlJPanel).groupPanel.toolDeleteVec;
				Vector oldEqVec=null;
				Vector oldMaterVec=null;
				Vector oldToolVec=null;
				
				
				Class[] paraClass1 = { String.class };
				Object[] objs1 = { procedure.getBsoID() };
				try {
					
					 oldEqVec = (Vector) useServiceMethod(
							"StandardCappService", "getEquipByProcedure",
							paraClass1, objs1);
					 
					 oldMaterVec = (Vector) useServiceMethod(
								"StandardCappService", "getMaterByProcedure",
								paraClass1, objs1);
					 
					 oldToolVec = (Vector) useServiceMethod(
								"StandardCappService", "getToolByProcedure",
								paraClass1, objs1);
					
					
				}catch (QMRemoteException ex) {
					ex.printStackTrace();
					String title = QMMessage
							.getLocalizedMessage(RESOURCE,
									"information", null);
					JOptionPane.showMessageDialog(
							getParentJFrame(), ex
									.getClientMessage(), title,
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				if (eqDeleteVec != null && eqDeleteVec.size() != 0) {

					for (int a = 0; a < eqDeleteVec.size(); a++) {

						Hashtable eqHsh = (Hashtable) eqDeleteVec.get(a);

						for (Iterator it = eqHsh.keySet().iterator(); it
								.hasNext();) {

							String key = (String) it.next();
							int count = (Integer) eqHsh.get(key);
							
							if(oldEqVec!=null){
								
								for(int aa=0;aa<oldEqVec.size();aa++){
									
									QMProcedureQMEquipmentLinkIfc  oldeq=(QMProcedureQMEquipmentLinkIfc)oldEqVec.get(aa);
									
									String oldeqID=oldeq.getRightBsoID();
									int oldCount=oldeq.getUsageCount();
									
									System.out.println("oldeqID==========�豸======*************==================="+oldeqID);
									System.out.println("key==============�豸==*************==================="+key);
									if(key.equals(oldeqID)){
										
										int rcount=oldCount-count;
										
										System.out.println("rcount========�豸========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldeq };
											
										if(rcount>0){
											
											   oldeq.setUsageCount(rcount);
											    //���±���
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====����===�豸========*************===================");
										     }else{
											
											      //ɾ��
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	eqDeleVec.add(oldeq);
										    	System.out.println("=====ɾ��====�豸=======*************===================");
										     }
										
										}catch (QMRemoteException ex) {
											ex.printStackTrace();
											String title = QMMessage
													.getLocalizedMessage(RESOURCE,
															"information", null);
											JOptionPane.showMessageDialog(
													getParentJFrame(), ex
															.getClientMessage(), title,
													JOptionPane.INFORMATION_MESSAGE);
										}
										break;
									}
								}
								
							}
							
							
						}

					}

				} 
				if (materDeleteVec != null
						&& materDeleteVec.size() != 0) {
					for(int b=0;b<materDeleteVec.size();b++){
						Hashtable materHsh = (Hashtable) materDeleteVec.get(b);

						for (Iterator it = materHsh.keySet().iterator(); it.hasNext();) {
							String key = (String) it.next();
							int count = (Integer) materHsh.get(key);
							
							if(oldMaterVec!=null){
								
                            for(int aa=0;aa<oldMaterVec.size();aa++){
                            	QMProcedureQMMaterialLinkIfc  oldMater=(QMProcedureQMMaterialLinkIfc)oldMaterVec.get(aa);
									
									String oldeqID=oldMater.getRightBsoID();
									float oldCount=oldMater.getUsageCount();
									if(key.equals(oldeqID)){
										
										
										System.out.println("Float.intBitsToFloat(count)========����========*************==================="+Float.intBitsToFloat(count));
										
										float rcount=oldCount-count;
										
										System.out.println("rcount========����========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldMater };
											
										if(rcount>0){
											oldMater.setUsageCount(rcount);
											    //���±���
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====����====����=======*************===================");
										     }else{
											
											      //ɾ��
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	materiaDeleVec.add(oldMater);
										    	System.out.println("=====ɾ��====����=======*************===================");
										     }
										
										}catch (QMRemoteException ex) {
											ex.printStackTrace();
											String title = QMMessage
													.getLocalizedMessage(RESOURCE,
															"information", null);
											JOptionPane.showMessageDialog(
													getParentJFrame(), ex
															.getClientMessage(), title,
													JOptionPane.INFORMATION_MESSAGE);
										}
										break;
									}
								}
								
								
							}
							
						}
						
						
					}
					
					

				} 
				if (toolDeleteVec != null
						&& toolDeleteVec.size() != 0) {
					
                  for(int b=0;b<toolDeleteVec.size();b++){
						Hashtable toolHsh = (Hashtable) toolDeleteVec.get(b);

						for (Iterator it = toolHsh.keySet().iterator(); it.hasNext();) {
							String key = (String) it.next();
							int count = (Integer) toolHsh.get(key);
							
							if(oldToolVec!=null){
								
                            for(int aa=0;aa<oldToolVec.size();aa++){
									
                            	QMProcedureQMToolLinkIfc  oldTool=(QMProcedureQMToolLinkIfc)oldToolVec.get(aa);
									
									String oldeqID=oldTool.getRightBsoID();
									int oldCount=Integer.parseInt(oldTool.getUsageCount());
									
									System.out.println("oldeqID========��װ========*************==================="+oldeqID);
									System.out.println("key============��װ====*************==================="+key);
									if(key.equals(oldeqID)){
										
										int rcount=oldCount-count;
										
										System.out.println("rcount=======��װ=========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldTool };
											
										if(rcount>0){
											
											oldTool.setUsageCount(String.valueOf(rcount));
											    //���±���
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====����====��װ=======*************===================");
										     }else{
											
											      //ɾ��
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	toolDeleVec.add(oldTool);
										    	System.out.println("=====ɾ��====��װ=======*************===================");
										     }
										
										}catch (QMRemoteException ex) {
											ex.printStackTrace();
											String title = QMMessage
													.getLocalizedMessage(RESOURCE,
															"information", null);
											JOptionPane.showMessageDialog(
													getParentJFrame(), ex
															.getClientMessage(), title,
													JOptionPane.INFORMATION_MESSAGE);
										}
										break;
									}
								}
								
								
							}
							
						}
						
						
					}
					
					

				}
			}
        	
        	
        	
        	
        	
        	
        	
        	int groupCount=processControlJPanel.getExAttr().getAttGroupCount();
        	if (groupCount > 0) {
        		
				Vector vec = processControlJPanel.getExAttr().getAttGroups(
						"���Ƽƻ�");
				for (int a = 0; a < vec.size(); a++) {
					
					ExtendAttGroup cc = (ExtendAttGroup) vec.get(a);
					
					for (int k = 0; k < cc.getCount(); k++) {
						
						ExtendAttModel model = cc.getAttributeAt(k);
						
						String attrName = model.getAttName();
						Object obj;
						if (attrName.equals("eqBsoID")) {

							obj = model.getAttValue();

							if (obj != null && !obj.equals("")) {
								Class[] paraClass1 = { String.class };
								Object[] objs1 = { obj };
								try {
									BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
											"PersistService", "refreshInfo",
											paraClass1, objs1);
									 try {
										 taskLogic.setOtherSideRole(null);
										 taskLogic.setLinkClassName("com.faw_qm.capp.model.QMProcedureQMEquipmentLinkInfo");
										 taskLogic.setOtherSideClass(
												 Class.forName("com.faw_qm.resource.support.model.QMEquipmentInfo"));
										 
										 QMProcedureQMEquipmentLinkInfo  binarylinkinfo =(QMProcedureQMEquipmentLinkInfo) taskLogic.createNewLinkForBSX(Info);
										 if(!eqVec.contains(binarylinkinfo)){
											 
										   eqVec.addElement(binarylinkinfo);
										 }
											 
										 
									} catch (Exception e) {
										e.printStackTrace();
									} 
									
									
									
									
								} catch (QMRemoteException ex) {
									ex.printStackTrace();
									String title = QMMessage
											.getLocalizedMessage(RESOURCE,
													"information", null);
									JOptionPane.showMessageDialog(
											getParentJFrame(), ex
													.getClientMessage(), title,
											JOptionPane.INFORMATION_MESSAGE);
								}

							}


						}else if(attrName.equals("materBsoID")){
							
							 obj = model.getAttValue();
							 
							
							 if (obj != null && !obj.equals("")) {

								Class[] paraClass1 = { String.class };
								Object[] objs1 = { obj };
								try {
									BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
											"PersistService", "refreshInfo",
											paraClass1, objs1);
									try {
										
										taskLogic
										.setOtherSideRole(null);
								        taskLogic
										.setLinkClassName("com.faw_qm.capp.model.QMProcedureQMMaterialLinkInfo");
								        taskLogic
										.setOtherSideClass(Class
												.forName("com.faw_qm.resource.support.model.QMMaterialInfo"));

										if (materiaVec.size() != 0) {
											for (int n = 0; n < materiaVec
													.size(); n++) {

												QMProcedureQMMaterialLinkInfo binarylinkinfo = (QMProcedureQMMaterialLinkInfo) materiaVec
														.get(n);

												String eqid = binarylinkinfo
														.getRightBsoID();

												if (eqid.equals(obj)) {

													float count = binarylinkinfo
															.getUsageCount();

													count += 1;

													binarylinkinfo
															.setUsageCount(count);
											

													break;

												} else {

													

													QMProcedureQMMaterialLinkInfo binarylinkinfo1 = (QMProcedureQMMaterialLinkInfo) taskLogic
															.createNewLinkForBSX(Info);
													

													materiaVec
															.addElement(binarylinkinfo1);

												}

											}
										} else {


											QMProcedureQMMaterialLinkInfo binarylinkinfo = (QMProcedureQMMaterialLinkInfo) taskLogic
													.createNewLinkForBSX(Info);

											materiaVec
													.addElement(binarylinkinfo);

										}

									} catch (Exception e) {
										e.printStackTrace();
									}

								} catch (QMRemoteException ex) {
									ex.printStackTrace();
									String title = QMMessage
											.getLocalizedMessage(RESOURCE,
													"information", null);
									JOptionPane.showMessageDialog(
											getParentJFrame(), ex
													.getClientMessage(), title,
											JOptionPane.INFORMATION_MESSAGE);
								}

							}
							 
							 
							 
							 
							
						}else if(attrName.equals("FLJtoolBsoID")){
							
							
							 obj = model.getAttValue();
							 if (obj != null && !obj.equals("")) {
									Class[] paraClass1 = { String.class };
									Object[] objs1 = { obj };
									try {
										BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
												"PersistService", "refreshInfo",
												paraClass1, objs1);
										 try {
											 taskLogic.setOtherSideRole(null);
											 taskLogic.setLinkClassName("com.faw_qm.capp.model.QMProcedureQMToolLinkInfo");
											 taskLogic.setOtherSideClass(
													 Class.forName("com.faw_qm.resource.support.model.QMToolInfo"));
											 
											 
											 
											 if (toolVec.size() != 0) {
													for (int n = 0; n < toolVec
															.size(); n++) {

														QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) toolVec
																.get(n);

														String toolid = binarylinkinfo
																.getRightBsoID();

														if (toolid.equals(obj)) {
															String count = binarylinkinfo.getUsageCount();
                                                           if(count.indexOf("(")==-1||count.indexOf("��")==-1){
                                                           	
                                                           	
                                                           	int c=Integer.parseInt(count)+1;
                                                           	
                                                           	count=String.valueOf(c);
                                                           }else{
                                                           	
                                                           	break;
                                                           }
															

															binarylinkinfo
																	.setUsageCount(count);
													

															break;

														} else {

															

															QMProcedureQMToolLinkInfo binarylinkinfo1 = (QMProcedureQMToolLinkInfo) taskLogic
																	.createNewLinkForBSX(Info);
															

															toolVec
																	.addElement(binarylinkinfo1);
															break;

														}

													}
												} else {


													QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) taskLogic
															.createNewLinkForBSX(Info);

													toolVec
															.addElement(binarylinkinfo);

												}
											 
												 
											 
										} catch (Exception e) {
											e.printStackTrace();
										} 
										
										
										
										
									} catch (QMRemoteException ex) {
										ex.printStackTrace();
										String title = QMMessage
												.getLocalizedMessage(RESOURCE,
														"information", null);
										JOptionPane.showMessageDialog(
												getParentJFrame(), ex
														.getClientMessage(), title,
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
						}
						
						
						else if(attrName.equals("LJtoolBsoID")){
							
							
							 obj = model.getAttValue();
							 if (obj != null && !obj.equals("")) {
									Class[] paraClass1 = { String.class };
									Object[] objs1 = { obj };
									try {
										BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
												"PersistService", "refreshInfo",
												paraClass1, objs1);
										 try {
											 taskLogic.setOtherSideRole(null);
											 taskLogic.setLinkClassName("com.faw_qm.capp.model.QMProcedureQMToolLinkInfo");
											 taskLogic.setOtherSideClass(
													 Class.forName("com.faw_qm.resource.support.model.QMToolInfo"));
											 
											 
											 
											 if (toolVec.size() != 0) {
													for (int n = 0; n < toolVec
															.size(); n++) {

														QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) toolVec
																.get(n);

														String toolid = binarylinkinfo
																.getRightBsoID();
														
														if (toolid.equals(obj)) {
															String count = binarylinkinfo.getUsageCount();
                                                          if(count.indexOf("(")==-1||count.indexOf("��")==-1){
                                                          	
                                                          	
                                                          	int c=Integer.parseInt(count)+1;
                                                          	
                                                          	count=String.valueOf(c);
                                                          }else{
                                                          	
                                                          	break;
                                                          }
															

															binarylinkinfo
																	.setUsageCount(count);
													

															break;

														} else {

															

															QMProcedureQMToolLinkInfo binarylinkinfo1 = (QMProcedureQMToolLinkInfo) taskLogic
																	.createNewLinkForBSX(Info);

															toolVec
																	.addElement(binarylinkinfo1);
															
															break;

														}

													}
												} else {


													QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) taskLogic
															.createNewLinkForBSX(Info);

													toolVec
															.addElement(binarylinkinfo);

												}
											 
												 
											 
										} catch (Exception e) {
											e.printStackTrace();
										} 
										
										
										
										
									} catch (QMRemoteException ex) {
										ex.printStackTrace();
										String title = QMMessage
												.getLocalizedMessage(RESOURCE,
														"information", null);
										JOptionPane.showMessageDialog(
												getParentJFrame(), ex
														.getClientMessage(), title,
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
						}
					}
				}

			}
            
            
        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("�������̿���¼�����");
//            }
//            isSave = false;
//            return null;
//        }


        //������й���(�豸����װ�����ϡ��ĵ�)
        Vector docLinks = new Vector();
        Vector equipLinks = new Vector();
        Vector toolLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector partLinks = new Vector();
        //CCBegin by liunan 2011-5-27 �Ż�
        //ArrayList pDrawings = null;
        //ArrayList drawingLinks = null;
        //CCBegin SS1
        Vector docMasterLinks = new Vector();
        //CCEnd SS1
        ArrayList updatedrawings = null;//Begin CR3
        ArrayList deletedrawings = null;//End CR3
        //CCEnd by liunan 2011-5-27
        try
        {
            docLinks =((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().doclinkJPanel.getAllLinks();
            //CCBegin SS1
            int size = docLinks.size();
            System.out.println("docLinks=======pace=====ssssssssssssssssss======================="+size);
            for (int j = 0; j < size; j++)
            {
                String docId = ((QMProcedureQMDocumentLinkInfo) docLinks.
                                elementAt(j)).getRightBsoID();
                Class[] paraClass1 =
                        {String.class};
                Object[] objs1 =
                        {docId};
                //DocMasterInfo masterInfo = new DocMasterInfo();
                String masterID = "";
                try
                {
                    BaseValueInfo docInfo = (BaseValueInfo)
                                            useServiceMethod(
                            "PersistService", "refreshInfo", paraClass1,
                            objs1);
                    if (docInfo instanceof DocInfo)
                    {
                        masterID = ((DocInfo) docInfo).getMasterBsoID();
                        QMProcedureQMDocumentLinkInfo linkInfo = new
                                QMProcedureQMDocumentLinkInfo();
                        linkInfo.setRightBsoID(masterID);
                        linkInfo.setLeftBsoID(((
                                QMProcedureQMDocumentLinkInfo)
                                               docLinks.elementAt(j)).
                                              getLeftBsoID());
                        docMasterLinks.add(linkInfo);
                    }
                    else
                    if (docInfo instanceof DocMasterInfo)
                    {
                        docMasterLinks.add((QMProcedureQMDocumentLinkInfo)
                                           docLinks.elementAt(j));
                    }
                    //displayString = getIdentity(relationTechnics);
                    System.out.println("docMasterLinks=========pace===ssssssssssssssssss======================="+docMasterLinks.size());
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }
                //CCEnd SS1
            }
            //���⣨2��20080820 �촺Ӣ�޸�   �޸�ԭ�򣺹�����������Ԥ������
            if (equiplinkJPanel != null)
            {
                 equipLinks = equiplinkJPanel.getAllLinks();
                 
                 //CCBegin SS2
                 if(eqVec.size()!=0){
                 	for(int a=0;a<eqVec.size();a++){
                 		boolean isHas=false;
                 		QMProcedureQMEquipmentLinkInfo newlinkinfo=(QMProcedureQMEquipmentLinkInfo)eqVec.get(a);
                 		
                 		String newLinkID=newlinkinfo.getRightBsoID();
                 		
                 		if(equipLinks!=null&&equipLinks.size()!=0){
                 		    for(int b=0;b<equipLinks.size();b++){
                 			
                 		    	QMProcedureQMEquipmentLinkInfo oldlinkInfo=(QMProcedureQMEquipmentLinkInfo)equipLinks.get(b);
                 		    	
                 		    	String oldLinkID=oldlinkInfo.getRightBsoID();
                 		    	
                 		    	if(newLinkID.equals(oldLinkID)){
                 		    		
                 		    		 isHas=true;
                 		    		 break;
                 		    	}
                 		    	
                 		    }
                 		}else{
                 			 equipLinks.add(newlinkinfo);
                 			 isHas=true;
                 		}
                 		if(isHas){
                 			continue;
                 		}else{
                 			equipLinks.add(newlinkinfo);
                 		}
                 		
                 		
                 	
                 	}
                 }
                 
                 
                 if(eqDeleVec.size()!=0){
                 	for(int m=0;m<eqDeleVec.size();m++){
                 		
                 		QMProcedureQMEquipmentLinkInfo deleteEqLinkInfo=(QMProcedureQMEquipmentLinkInfo)eqDeleVec.get(m);
                 		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),equipLinks);
                 		
                 		if(hasLink!=null){
                 			equipLinks.remove(hasLink);
                 			
                 		}
                 	}
                 	
                 }
                 
                 //CCEnd SS2
            }
            if (toollinkJPanel != null)
            {
               toolLinks = toollinkJPanel.getAllLinks();
               
             //CCBesgin SS2
               if(toolVec.size()!=0){
            	   System.out.println("toolVec.size()======================================="+toolVec.size());
            	   for (int b = 0; b < toolVec.size(); b++) {
            		   
            		   QMProcedureQMToolLinkInfo newToolLinkInfo=(QMProcedureQMToolLinkInfo)toolVec.get(b);
            		   
            		   String newLinkID=newToolLinkInfo.getRightBsoID();
            		   
            		   
            		   QMProcedureQMToolLinkInfo oldToolLinkInfo=(QMProcedureQMToolLinkInfo)findIsHasObj(newLinkID,toolLinks);
            		   
            		   if(oldToolLinkInfo!=null){
            			   
            			   oldToolLinkInfo.setUsageCount(newToolLinkInfo.getUsageCount());
            			   int a=toolLinks.indexOf(oldToolLinkInfo);
            			   System.out.println("index=================================="+a);
            			   toolLinks.remove(a);
            			   toolLinks.add(a, oldToolLinkInfo);
            		   }
            		   else{
            			   
            			   toolLinks.add(newToolLinkInfo);
            		   }
            		   
            		   
            		   
					}
               }
               
               if(toolDeleVec.size()!=0){
                  	for(int m=0;m<toolDeleVec.size();m++){
                  		
                  		QMProcedureQMToolLinkInfo deleteEqLinkInfo=(QMProcedureQMToolLinkInfo)toolDeleVec.get(m);
                  		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),toolLinks);
                  		
                  		if(hasLink!=null){
                  			toolLinks.remove(hasLink);
                  			
                  		}
                  	}
                  	
                  }
               //CCEnd SS2
            }
            if (materiallinkJPanel != null)
            {
                 materialLinks = materiallinkJPanel.getAllLinks();
               //CCBegin SS2
                 if (materiaVec.size() != 0) {
  					for (int b = 0; b < materiaVec.size(); b++) {

  						
  						QMProcedureQMMaterialLinkInfo newMaterLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaVec.get(b);
  	            		   
  	            		   String newLinkID=newMaterLinkInfo.getRightBsoID();
  	            		   
//  	            		   if(materialLinks!=null&&materialLinks.size()!=0){
//  	            			   
//  	            			   for(int c=0;c<materialLinks.size();c++){
//  	            				   
//  	            				   QMProcedureQMMaterialLinkInfo oldMateLinkInfo=(QMProcedureQMMaterialLinkInfo)materialLinks.get(c);
//  	            				   
//  	            				   String oldLinkID=oldMateLinkInfo.getRightBsoID();
//  	            				   
//  	            				   if(newLinkID.equals(oldLinkID)){
//  	            					   
//  	            					   oldMateLinkInfo.setUsageCount(newMaterLinkInfo.getUsageCount());
//  	            					   
//  	            				   }else{
//  	            					   
//  	            					   materialLinks.add(newMaterLinkInfo);
//  	            				   }
//  	            				   
//  	            			   }
//  	            		   }
  	            		   QMProcedureQMMaterialLinkInfo oldMateLinkInfo=(QMProcedureQMMaterialLinkInfo) findIsHasObj(newLinkID,materialLinks);
  	            		   
  	            		   if(oldMateLinkInfo!=null){
  	            			   
  	            			   oldMateLinkInfo.setUsageCount(newMaterLinkInfo.getUsageCount());
  	            			   
  	            		   }
  	            		   
  	            		   else{
  	            			   
  	            			   toolLinks.add(newMaterLinkInfo);
  	            		   }
  						
  						
  						
  						
  						
  					}
              	   
  				}
                 
                 if(materiaDeleVec.size()!=0){
                   	for(int m=0;m<materiaDeleVec.size();m++){
                   		
                   		QMProcedureQMMaterialLinkInfo deleteEqLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaDeleVec.get(m);
                   		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),materialLinks);
                   		
                   		if(hasLink!=null){
                   			materialLinks.remove(hasLink);
                   			
                   		}
                   	}
                   	
                   }
                 //CCEnd SS2
            }
            
            //CCBegin SS2
            if (((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().partlinkJPanel != null)
            {
                partLinks = ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().partlinkJPanel.getAllLinks();
            }
            Object[] obj = ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.getDrawings();
            //CCEnd SS2
            if (obj != null)
            {
            	//CCBegin by liunan 2011-5-27 �Ż�
              //  pDrawings = (ArrayList) obj[0];
              //  drawingLinks = (ArrayList) obj[1];
              updatedrawings = (ArrayList) obj[0];//Begin CR3
              deletedrawings = (ArrayList) obj[1];//End CR3
              //CCEnd by liunan 2011-5-27
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            isSave = false;
            return null;
        }
        //�����й����ϲ�
        Vector resourceLinks = new Vector();
        //�ϲ��ĵ�����
      //CCBegin SS1
//        for (int k = 0; k < docLinks.size(); k++)
//
//        {
//            resourceLinks.addElement(docLinks.elementAt(k));
//        }
        for (int k = 0; k < docMasterLinks.size(); k++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(k));
        }
        //CCEnd SS1
        //�ϲ����Ϲ���
        for (int j = 0; j < materialLinks.size(); j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }
        //�ϲ��豸����
        for (int m = 0; m < equipLinks.size(); m++)
        {
            resourceLinks.addElement(equipLinks.elementAt(m));
        }
        //�ϲ���װ����
        for (int n = 0; n < toolLinks.size(); n++)
        {
            resourceLinks.addElement(toolLinks.elementAt(n));
        }
        //�ϲ��㲿������
        if (partLinks != null)
        {
            for (int n = 0; n < partLinks.size(); n++)
            {
                resourceLinks.addElement(partLinks.elementAt(n));
            }
        }
        //CCBegin by liunan 2011-5-27 �Ż�
        //�ϲ���ͼ��Դ����   Begin CR3
        /*if(drawingLinks!=null)
        {
            for (int n = 0; n < drawingLinks.size(); n++)
            {
                resourceLinks.addElement(drawingLinks.get(n));
            }
        }*/
        //End CR3
        //CCEnd by liunan 2011-5-27


        //��װ������Ϣ
        CappWrapData cappWrapData = new CappWrapData();
        //���ù���
        cappWrapData.setQMProcedureIfc(getProcedure());
        //��װ����
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
        //CCBegin by liunan 2011-5-27 �Ż�
        //cappWrapData.setPDrawings(pDrawings);
        cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR3
        cappWrapData.setDeleteDrawing(deletedrawings);//End CR3
        //CCEnd by liunan 2011-5-27
        return cappWrapData;
    }
    
   private BinaryLinkIfc findIsHasObj(String str,Vector vec){
    	
    	BinaryLinkIfc resltInfo=null;
    	if(vec!=null&&vec.size()!=0){
		    for(int b=0;b<vec.size();b++){
			
		    	BinaryLinkIfc oldlinkInfo=(BinaryLinkIfc)vec.get(b);
		    	
		    	String oldLinkID=oldlinkInfo.getRightBsoID();
		    	
		    	if(str.equals(oldLinkID)){
		    		
		    		 resltInfo=oldlinkInfo;
		    		 break;
		    	}
		    	
		    }
		}
    	return resltInfo;
    	
    }
    
    
    //CCEnd SS2
    
    
    


    //add by wangh on 20070310
   private void paceTS16949Panel(String processType)
  {
      if (!processType.equals(existProcessType))
      {
//          if (processFlowJPanel != null)
//          {
//              flowExtendJPanel.remove(processFlowJPanel);
//          }
//          if (processFlowTable.get(processType) == null)
//          {
//              try
//              {
//                  processFlowJPanel = new CappExAttrPanel(procedure.getBsoName(),
//                          "��������", 1);
//              }
//              catch (QMException ex)
//              {
//                  ex.printStackTrace();
//              }
//              processFlowTable.put(processType, processFlowJPanel);
//          }
//          else
//          {
//              processFlowJPanel = (CappExAttrPanel) processFlowTable.get(
//                      processType);
//          }
//          if (femaJPanel != null)
//          {
//              femaExtendJPanel.remove(femaJPanel);
//          }
//          if (femaTable.get(processType) == null)
//          {
//              try
//              {
//                  femaJPanel = new CappExAttrPanel(procedure.getBsoName(),
//                          "����FMEA", 1);
//              }
//              catch (QMException ex)
//              {
//                  ex.printStackTrace();
//              }
//              femaTable.put(processType, femaJPanel);
//          }
//          else
//          {
//              femaJPanel = (CappExAttrPanel) femaTable.get(
//                      processType);
//          }
          if (processControlJPanel != null)
          {
              controlExtendJPanel.remove(processControlJPanel);
          }
          System.out.println("processType========1111111111111111111111111========================"+processType);
          if (processControlTable.get(processType) == null)
          {
        	  System.out.println("processType==========aaaaaAAAAAAAAAAAAA======��ʵ����һ��================");
              try
              {
            	  //CCBegin SS2
            	  Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
      			.getSelectedObject().getObject();
              	String techType="";
              	if (obj instanceof consQMAssembleProcedureInfo) {
      				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
      				techType = info.getTechnicsType().getCodeContent();
              	} else if (obj instanceof QMFawTechnicsInfo) {

      				QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

      				techType = info.getTechnicsType().getCodeContent();

      			}else if(obj instanceof consQMMachineProcedureInfo){
      				
      				consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
      				techType = info.getTechnicsType().getCodeContent();
      				
      			}
                if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
          				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) 
                	
                     processControlJPanel = new CappExAttrPanelForBSX(procedure.getBsoName(),
                          "���Ƽƻ�", 1,"����",parentTechnics);
            	  else
            		  processControlJPanel = new CappExAttrPanel(procedure.getBsoName(),
                              "���Ƽƻ�", 1);
			
                  //CCEnd SS2
                System.out.println("processControlJPanel.getClass()========1111111111111111111111111========================"+processControlJPanel.getClass());
              }
              catch (QMException ex)
              {
                  ex.printStackTrace();
              }
              processControlTable.put(processType, processControlJPanel);
          }
          else
          {
        	  System.out.println("processType==========aaaaaAAAAAAAAAAAAA======�Ѵ���================");
        	  //CCBegin SS2
              processControlJPanel = (CappExAttrPanel) processControlTable.get(
                      processType);
              //CCEnd SS2
              System.out.println("processControlJPanel.getClass()========222222222222222========================"+processControlJPanel.getClass());
          }
          processControlJPanel.setProIfc(procedure);
           existProcessType = processType;
      }
//      processFlowJPanel.clear();
//      femaJPanel.clear();
      processControlJPanel.clear();
      if (mode == CREATE_MODE ||
          mode == UPDATE_MODE)
      {
//          processFlowJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
//          femaJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
          processControlJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
      }
      else
      {
//          processFlowJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
//          femaJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
          processControlJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
      }
      if (mode != CREATE_MODE)
      {
//          processFlowJPanel.show(getProcedure().getProcessFlow());
//          femaJPanel.show(getProcedure().getFema());
          processControlJPanel.show(getProcedure().getProcessControl());
      }
//      flowExtendJPanel.add(processFlowJPanel,
//                       new GridBagConstraints(0, 0, 1, 1, 1.0,
//                                              1.0
//                                              , GridBagConstraints.CENTER,
//                                              GridBagConstraints.BOTH,
//                                              new Insets(0, 0, 0, 0), 0, 0));
//     femaExtendJPanel.add(femaJPanel,
//                       new GridBagConstraints(0, 0, 1, 1, 1.0,
//                                              1.0
//                                              , GridBagConstraints.CENTER,
//                                              GridBagConstraints.BOTH,
//                                              new Insets(0, 0, 0, 0), 0, 0));
     controlExtendJPanel.add(processControlJPanel,
                       new GridBagConstraints(0, 0, 1, 1, 1.0,
                                              1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 0, 0, 0), 0, 0));

                                          //add by wangh on 200726(���ù������̣�����FMEA�Ϳ��Ƽƻ��Ƿ�ɼ�)
//                                          if (!ts16949) {
//                                            processFlowJPanel.setVisible(false);
//                                            femaJPanel.setVisible(false);
//                                            processControlJPanel.setVisible(false);
//                                          }

//CCBegin by liunan 2011-6-1 �Ż�
//add by guoxl on 2009-1-7(���������½��������пؼ���Ӽ���)
     if(this.getMode()==0)
     {
    	 TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
     }
    //add by guoxl end on 2009-1-7
        //CCEnd by liunan 2011-6-1
      repaint();
      processType="";
  }




    /**
     * �����½��Ĺ���
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkIsEmpty();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //�������Ƿ�Ϊ����
        if (!checkIsInteger(paceNumJTextField.getText().trim()))
        {
            paceNumJTextField.grabFocus();
            paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }

        //���ù��������������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        if (verbose)
        {
            System.out.println(
                    ">>>>>>>>>>>>> client create AidTime = " +
                    getProcedure().getAidTime());
            System.out.println(
                    ">>>>>>>>>>>>> client create machineHour = " +
                    getProcedure().getMachineHour());
        }

        //��ʾ�������
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //     RESOURCE, CappLMRB.SAVING, null));
        // ProgressService.showProgress();
        //CCBegin by liunan 2011-2-11 �Ż�
        // CR1
        OperationTreeObject treeObject;
        QMProcedureInfo returnProce;
        //CCEnd by liunan 2011-2-11

        try
        {
            //��ÿ�ͷ�����ѡ��ڵ�ʱ�ڵ��ڹ������ϼУ�������״̬���������丱��
            parentTechnics = (QMTechnicsInfo) refreshInfo(
                    parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);

                //���÷��񣬱��湤��
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {
                    String.class, String.class, CappWrapData.class};
            Object[] obj =
                    {
                    parentTechnics.getBsoID(),
                    ((QMProcedureInfo) selectedNode.getObject().getObject()).
                    getBsoID(),
                    cappWrapData};
            //CCBegin by liunan 2011-2-11 �Ż�
            // CR1
            //QMProcedureInfo returnProce;
            //CCEnd by liunan 2011-2-11
//            returnProce = (QMProcedureInfo) useServiceMethod(
//                    "StandardCappService", "createQMProcedure", paraClass,
//                    obj);
          //CCBegin by chudaming 20091119 ���빤��
		      if(insertCheckBox.isSelected())
		      {

		    	  returnProce = (QMProcedureInfo) useServiceMethod(
				            "StandardCappService", "insertQMProcedure", paraClass,
				            obj);
		      }
		      else
		      {
		        
		        returnProce = (QMProcedureInfo) useServiceMethod(
		            "StandardCappService", "createQMProcedure", paraClass,
		            obj);
		           
		      }
		      
		      //CCBegin by liunan 2011-5-27 �Ż�
		      //Begin CR3
		      if(drawingLinkPanel!=null){
		      	drawingLinkPanel.resetArrayList();
		      }//End CR3
		      //CCEnd by liunan 2011-5-27
		      
		    //CCEnd by chudaming 20091119 ���빤��
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //���½��Ĺ���ڵ���Ϲ�����
            //CCBegin by liunan 2011-2-11 �Ż�
            // CR1
            /*OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());*/
            treeObject = new OperationTreeObject(returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            parentProcedure = (QMProcedureInfo) selectedNode.getObject().getObject();
            //CCEnd by liunan 2011-2-11
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().
                        addNode(selectedNode.getObject(), treeObject);

            }

        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //��ʾ�Ƿ�������
        if (confirmAction(QMMessage.getLocalizedMessage(RESOURCE,
                "109", null)))
        {
            setCreateMode();
            isSave = false;
            setButtonWhenSave(true);
            return;
        }
        else
        {
        	//CCBegin by liunan 2011-2-11 �Ż�
        	// CR1
            //setVisible(false);
            setProcedure(returnProce);
            mode = 0;
          // CR1
          //CCEnd by liunan 2011-2-11
            TechnicsContentJPanel.addFocusLis.initFlag();//anan
        }
        setButtonWhenSave(true);
        isSave = true;
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenCreate() end...return is void");
        }
    }

    //CCBegin SS2
    /**
     * �����½��Ĺ���
     */
    private void saveWhenCreateForProcessControl()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkIsEmptyForProcessControl();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //�������Ƿ�Ϊ����
        if (!checkIsInteger(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.getText().trim()))
        {
        	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.grabFocus();
        	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        
        //CCBegin SS5
        if(((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.checkSeqNum())
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //CCEnd SS5

        //���ù��������������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributesForProcessControl();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        if (verbose)
        {
            System.out.println(
                    ">>>>>>>>>>>>> client create AidTime = " +
                    getProcedure().getAidTime());
            System.out.println(
                    ">>>>>>>>>>>>> client create machineHour = " +
                    getProcedure().getMachineHour());
        }

        //��ʾ�������
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //     RESOURCE, CappLMRB.SAVING, null));
        // ProgressService.showProgress();
        //CCBegin by liunan 2011-2-11 �Ż�
        // CR1
        OperationTreeObject treeObject;
        QMProcedureInfo returnProce;
        //CCEnd by liunan 2011-2-11

        try
        {
            //��ÿ�ͷ�����ѡ��ڵ�ʱ�ڵ��ڹ������ϼУ�������״̬���������丱��
            parentTechnics = (QMTechnicsInfo) refreshInfo(
                    parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);

                //���÷��񣬱��湤��
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {
                    String.class, String.class, CappWrapData.class};
            Object[] obj =
                    {
                    parentTechnics.getBsoID(),
                    ((QMProcedureInfo) selectedNode.getObject().getObject()).
                    getBsoID(),
                    cappWrapData};
            //CCBegin by liunan 2011-2-11 �Ż�
            // CR1
            //QMProcedureInfo returnProce;
            //CCEnd by liunan 2011-2-11
//            returnProce = (QMProcedureInfo) useServiceMethod(
//                    "StandardCappService", "createQMProcedure", paraClass,
//                    obj);
          //CCBegin by chudaming 20091119 ���빤��
		      if(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.isSelected())
		      {

		    	  returnProce = (QMProcedureInfo) useServiceMethod(
				            "StandardCappService", "insertQMProcedure", paraClass,
				            obj);
		      }
		      else
		      {
		        
		        returnProce = (QMProcedureInfo) useServiceMethod(
		            "StandardCappService", "createQMProcedure", paraClass,
		            obj);
		           
		      }
		      
		      //CCBegin by liunan 2011-5-27 �Ż�
		      //Begin CR3
		      if(((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel!=null){
		    	  ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.resetArrayList();
		      }//End CR3
		      //CCEnd by liunan 2011-5-27
		      
		    //CCEnd by chudaming 20091119 ���빤��
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //���½��Ĺ���ڵ���Ϲ�����
            //CCBegin by liunan 2011-2-11 �Ż�
            // CR1
            /*OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());*/
            treeObject = new OperationTreeObject(returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            parentProcedure = (QMProcedureInfo) selectedNode.getObject().getObject();
            //CCEnd by liunan 2011-2-11
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().
                        addNode(selectedNode.getObject(), treeObject);

            }

        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //��ʾ�Ƿ�������
        if (confirmAction(QMMessage.getLocalizedMessage(RESOURCE,
                "109", null)))
        {
            setCreateMode();
            isSave = false;
            setButtonWhenSave(true);
            return;
        }
        else
        {
        	//CCBegin by liunan 2011-2-11 �Ż�
        	// CR1
            //setVisible(false);
            setProcedure(returnProce);
            mode = 0;
          // CR1
          //CCEnd by liunan 2011-2-11
            TechnicsContentJPanel.addFocusLis.initFlag();//anan
        }
        setButtonWhenSave(true);
        isSave = true;
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenCreate() end...return is void");
        }
    }
    
    /**
     * ������µĹ���
     */
    private void saveWhenUpdateForProcessControl()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() begin...");
        }
        //CCBegin by liunan 2011-6-1 �Ż� add by guoxl on 2009-1-8(�����򣨹��������½�����Ӽ��������������Ϣ�����������
        TechnicsContentJPanel.addFocusLis.initFlag();
        //CCEnd by liunan 2011-6-1
        
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkIsEmptyForProcessControl();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //�������Ƿ�Ϊ����
        if (!checkIsInteger(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.getText().trim()))
        {
        	((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNumJTextField.grabFocus();
            return;
        }

        //CCBegin SS5
        if(((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.checkSeqNum())
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //CCEnd SS5
        
        //���ù��������������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributesForProcessControl();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

        //��ʾ�������
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //      RESOURCE, CappLMRB.SAVING, null));
        //  ProgressService.showProgress();

        try
        {
            //���÷��񣬱��湤��
            Class[] paraClass =
                    {
                    String.class, String.class, CappWrapData.class};

            parentTechnics = (QMTechnicsInfo) refreshInfo(
                    parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);

            }
            parentProcedure = (QMProcedureInfo) refreshInfo(
                    parentProcedure);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentProcedure))
            {
                parentProcedure = (QMProcedureInfo)
                                  CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentProcedure);

            }
            
            //CCBegin by liunan 2011-5-27 �Ż�
            //Begin CR3
            if(drawingLinkPanel!=null&&((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.resetArrayList();
            }//End CR3
            //CCEnd by liunan 2011-5-27
            
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Object[] obj =
                    {
                    parentTechnics.getBsoID(),
                    parentProcedure.getBsoID(),
                    cappWrapData};
            QMProcedureInfo returnProce;
          //CCBegin by chudaming 20091119 ���빤��
			
		      if(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.isSelected())
		      {

		    	  Class[] iClass = { String.class, String.class,
							CappWrapData.class };
		    	  Object[] iObj = { parentTechnics.getBsoID(),
							parentProcedure.getBsoID(), cappWrapData};
		    	  returnProce=(QMProcedureInfo)useServiceMethod(
			              "StandardCappService", "changeQMProcedure", iClass,
			              iObj);
		      }else
		      {
		        returnProce = (QMProcedureInfo) useServiceMethod(
		            "StandardCappService", "updateQMProcedure", paraClass,
		            obj);
		      }
		    //CCEnd by chudaming 20091119 ���빤��
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            //��treeObject�ҵ���������,ˢ�¹�����
            //����ʱ��Ҫ�����丸�ڵ���
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().updateNode(treeObject);
                //CCBegin by liunan 2010-11-29 ���²��빤��ʱ���Ե�ǰ�����µ����������ڵ�Ҳ��ˢ�²�����
                if(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.isSelected())
                {
                	Collection c = CappTreeHelper.browseProcedures(parentTechnics.getBsoID(),parentProcedure.getBsoID());
                	if (c != null && c.size() != 0)
                	{
                    for (Iterator it = c.iterator(); it.hasNext(); )
                    {
                    	QMProcedureInfo procedure = (QMProcedureInfo) it.next();
                    	OperationTreeObject treeObject1 = new OperationTreeObject(procedure);
                    	((TechnicsRegulationsMainJFrame) parentJFrame).getProcessTreePanel().updateNode(treeObject1);
                    }
                  }
                }
                //CCEnd by liunan 2010-11-29

            }
            procedure = returnProce;
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() end...return is void");
        }
    }
    
  //CCEnd SS2
    /**
     * ������µĹ���
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() begin...");
        }
        //CCBegin by liunan 2011-6-1 �Ż� add by guoxl on 2009-1-8(�����򣨹��������½�����Ӽ��������������Ϣ�����������
        TechnicsContentJPanel.addFocusLis.initFlag();
        //CCEnd by liunan 2011-6-1
        
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkIsEmpty();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //�������Ƿ�Ϊ����
        if (!checkIsInteger(paceNumJTextField.getText().trim()))
        {
            paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            paceNumJTextField.grabFocus();
            return;
        }

        //���ù��������������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

        //��ʾ�������
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //      RESOURCE, CappLMRB.SAVING, null));
        //  ProgressService.showProgress();

        try
        {
            //���÷��񣬱��湤��
            Class[] paraClass =
                    {
                    String.class, String.class, CappWrapData.class};

            parentTechnics = (QMTechnicsInfo) refreshInfo(
                    parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);

            }
            parentProcedure = (QMProcedureInfo) refreshInfo(
                    parentProcedure);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentProcedure))
            {
                parentProcedure = (QMProcedureInfo)
                                  CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentProcedure);

            }
            
            //CCBegin by liunan 2011-5-27 �Ż�
            //Begin CR3
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR3
            //CCEnd by liunan 2011-5-27
            
            
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Object[] obj =
                    {
                    parentTechnics.getBsoID(),
                    parentProcedure.getBsoID(),
                    cappWrapData};
            QMProcedureInfo returnProce;
          //CCBegin by chudaming 20091119 ���빤��
			
		      if(insertCheckBox.isSelected())
		      {

		    	  Class[] iClass = { String.class, String.class,
							CappWrapData.class };
		    	  Object[] iObj = { parentTechnics.getBsoID(),
							parentProcedure.getBsoID(), cappWrapData};
		    	  returnProce=(QMProcedureInfo)useServiceMethod(
			              "StandardCappService", "changeQMProcedure", iClass,
			              iObj);
		      }else
		      {
		        returnProce = (QMProcedureInfo) useServiceMethod(
		            "StandardCappService", "updateQMProcedure", paraClass,
		            obj);
		      }
		    //CCEnd by chudaming 20091119 ���빤��
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            //��treeObject�ҵ���������,ˢ�¹�����
            //����ʱ��Ҫ�����丸�ڵ���
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().updateNode(treeObject);
                //CCBegin by liunan 2010-11-29 ���²��빤��ʱ���Ե�ǰ�����µ����������ڵ�Ҳ��ˢ�²�����
                if(insertCheckBox.isSelected())
                {
                	Collection c = CappTreeHelper.browseProcedures(parentTechnics.getBsoID(),parentProcedure.getBsoID());
                	if (c != null && c.size() != 0)
                	{
                    for (Iterator it = c.iterator(); it.hasNext(); )
                    {
                    	QMProcedureInfo procedure = (QMProcedureInfo) it.next();
                    	OperationTreeObject treeObject1 = new OperationTreeObject(procedure);
                    	((TechnicsRegulationsMainJFrame) parentJFrame).getProcessTreePanel().updateNode(treeObject1);
                    }
                  }
                }
                //CCEnd by liunan 2010-11-29

            }
            procedure = returnProce;
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        //CCBegin by liunan 2011-2-11 �Ż�
        // CR2
        /*try
        {
            setViewMode(VIEW_MODE);
        }
        catch (PropertyVetoException ex1)
        {
            ex1.printStackTrace();
        }*/
        // CR2
        //CCEnd by liunan 2011-2-11
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() end...return is void");
        }
    }


    /**
     * ���ð�ť����ʾ״̬����Ч��ʧЧ��
     * @param flag  flagΪTrue����ť��Ч������ťʧЧ
     */
    private void setButtonWhenSave(boolean flag)
    {
        quitJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
        paraJButton.setEnabled(flag);
        //storageJButton.setEnabled(flag);
    }


    /**
     * ���ð�ť�Ŀɼ���
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
        //storageJButton.setVisible(flag);
    }


    /**
     * ִ���������������ձ����з��ֵ��豸�빤װ�Ĺ�ϵ����ع�װά����
     * @param e
     */
    void storageJButton_actionPerformed(ActionEvent e)
    {
        //�����ж�ѡ����豸����װ��������һ����ֻѡ��һ��ʵ��
        Vector equipLinks = equiplinkJPanel.getSelectedObjects();
        Vector toolLinks = toollinkJPanel.getSelectedObjects();

        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
        if (equipLinks.size() > 0 && toolLinks.size() > 0)
        {
            if (equipLinks.size() == 1 || toolLinks.size() == 1)
            {
                //ϵͳ�����豸�빤װ�б��е����й�װ����������ϵ��
                f1.populateEquipments(equipLinks);
                f1.populateTools(toolLinks);
                f1.setVisible(true);
            }
            else
            {
                //�豸����װ�б���������һ���б���һ������
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.EQUIPMENT_OR_TOOL_NOT_ONLYONE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              message,
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }

        }
        else
        {
            //"�豸�б�͹�װ�б���붼��������ݣ�����ִ������������������ݡ�"
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.EQUIPMENT_OR_TOOL_NOT_ONLYONE, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * ����ָ���Ĺ������ഴ������ֵ����
     * @param stepTypeKey ָ���Ĺ�������
     * @return �о������������Ĺ�������
     * @throws ClassNotFoundException
     */
    protected QMProcedureInfo instantiateQMProcedure(String
            technicsType)
            throws ClassNotFoundException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.instantiateQMProcedure() begin...");
            //�������ļ��л��ָ����������Ĺ���ֵ�����·��������
        }
        String classpath = RemoteProperty.getProperty("instance" +
                technicsType);
        QMProcedureInfo procedureInfo = null;
        try
        {
            //����ָ����·���������
            Class procedureClass = Class.forName(classpath);
            Object obj = null;
            try
            {
                obj = procedureClass.newInstance();
            }
            catch (InstantiationException ex)
            {
                ex.printStackTrace();
            }
            catch (IllegalAccessException ex)
            {
                ex.printStackTrace();
            }
            if (obj instanceof QMProcedureInfo)
            {
                procedureInfo = (QMProcedureInfo) obj;
            }
            if (verbose)
            {
                System.out.println("cappclients.capp.view.TechnicsPaceJPanel.instantiateQMProcedure() end...return: " +
                                   procedureInfo);
            }
            return procedureInfo;
        }
        catch (ClassNotFoundException ex)
        {
            throw ex;
        }

    }


    /**
     * ��������ļ��еĹ�������
     * @return �������༯��
     */
    public Vector getStepType()
    {
        return findCodingInfo("QMProcedure", "technicsType");
    }


    /**
     * �Ѹ�����ҵ�������ӵ���Ӧ�Ĺ����б���
     * @param info ������ҵ�������Դ��
     */
    public void addObjectToTable(BaseValueInfo[] info)
    {
    	//���⣨2��20080820 �촺Ӣ�޸�
        if (info[0] instanceof QMEquipmentInfo)
        {
        	if (equiplinkJPanel != null)
        	{
            for (int i = 0; i < info.length; i++)
            {
                equiplinkJPanel.addEquipmentToTable((
                        QMEquipmentInfo) info[i]);
            }
        	}
        }
        else if (info[0] instanceof QMToolInfo)
        {
        	if (toollinkJPanel != null)
        	{
            for (int i = 0; i < info.length; i++)
            {
                toollinkJPanel.addToolToTable((QMToolInfo) info[i]);
            }
        	}
        }
        else if (info[0] instanceof QMMaterialInfo)
        {
        	if(materiallinkJPanel != null)
        	{
            for (int i = 0; i < info.length; i++)
            {
                materiallinkJPanel.addMaterialToTable((
                        QMMaterialInfo) info[i]);
            }
        	}
        }
        else if (info[0] instanceof QMTermInfo)
        {
            technicsContentJTextArea.setInsertText(((
                    QMTermInfo) info[0]).getTermName());
        }
        else if (info[0] instanceof DrawingInfo)
        {
            for (int i = 0; i < info.length; i++)
            {
                drawingLinkPanel.addDrawingToTable((DrawingInfo) info[i]);
            }
        }
        else if (info[0] instanceof QMPartInfo)
        {
            if (partlinkJPanel != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    partlinkJPanel.addPartToTable((QMPartInfo) info[i]);
                }
            }
        }
    }


    /**
     *�˷��������������ά�������е�����
     */
    public void clear()
    {
        if (firstInFlag)
        {
            firstInFlag = false;
            return;
        }
        paceNumJTextField.setText("");
        //CCBegin by chudaming 2008-11-18 bsx���ӹ�����������
        paceNameJTextField.setText("");
        numDisplayJLabel.setText("");
        nameDisplayJLabel.setText("");
        //CCEnd by chudaming 2008-11-18 bsx���ӹ�����������
        drawingLinkPanel.clear();
        processHoursJPanel.clear();
        //CCBegin SS2
//        processControlJPanel.getPaceProcessControlJPanelForBSX().processHoursJPanel.clear();
//        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.clear();
//        processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.clear();
//        processControlJPanel.getPaceProcessControlJPanelForBSX().insertCheckBox.setSelected(false);
        //CCEnd SS2
        //equiplinkJPanel.clear();
        //materiallinkJPanel.clear();
        //toollinkJPanel.clear();
        doclinkJPanel.clear();
      //CCBegin by chudaming 20091119 ���빤��
	    insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 ���빤��
        existProcessType = "";
        d = null;
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

        //����ʵ������������
        technicsContentJTextArea.clearAll();
        /*upJPanel.remove(technicsContentJTextArea);
         technicsContentJTextArea = new SpeCharaterTextPanel(parentJFrame);
         technicsContentJTextArea.speCharaterTextBean.setFont(new java.awt.Font(
                "Dialog", 0, 18));

                 technicsContentJTextArea.setBorder(null);
                 technicsContentJTextArea.setMaximumSize(new Dimension(32767,
                32767));
         technicsContentJTextArea.setMinimumSize(new Dimension(10, 10));
         technicsContentJTextArea.setPreferredSize(new Dimension(10, 10));
                 upJPanel.add(technicsContentJTextArea,
                     new GridBagConstraints(1, 3, 3, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(2, 8, 2, 8), 0, 0));*/
        upJPanel.repaint();

    }


    /**
     * ���ݹ����������»������������
     * @param stepType String ��������
     */
    private void newPartlinkJPanel(String stepType)
    {
        //��̬���ù���ʹ���㲿������
        String link = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + stepType);
        if (link.trim().equals("null") || link.equals(""))
        {
            if (partlinkJPanel != null)
            {
                relationsJPanel.remove(partlinkJPanel);
            }
            partlinkJPanel = null;
        }
        else
        {
            if (partlinkJPanel != null)
            {
                relationsJPanel.remove(partlinkJPanel);
            }
            partlinkJPanel = new ProcedureUsagePartJPanel(stepType);
            relationsJPanel.add(partlinkJPanel, "�㲿��");
        }
    }
    /**
     * ����ʵ�����豸����
     * @param stepType String
     * ���⣨2��20080820 �촺Ӣ�޸�
     */
    //private synchronized void newEquiplinkJPanel(String stepType)
    private void newEquiplinkJPanel(String stepType)//anan
    {

            if (equiplinkJPanel != null)
            {
                relationsJPanel.remove(equiplinkJPanel);
            }
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType);
            //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
            equiplinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    toollinkJPanel.addRelationTools(e);
                }
            });

            equiplinkJPanel.setToolPanel(toollinkJPanel);
            //relationsJPanel.add(equiplinkJPanel, "�豸");

        relationsJPanel.add(equiplinkJPanel, "�豸");
    }
    /**
     * ����ʵ������װ����
     * @param stepType String
     * ���⣨2��20080820 �촺Ӣ�޸�
     */
    //private synchronized void newToollinkJPanel(String stepType)
    private void newToollinkJPanel(String stepType)//anan
    {

            if (toollinkJPanel != null)
            {
                relationsJPanel.remove(toollinkJPanel);
            }
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType);
            //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
            toollinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    equiplinkJPanel.addRelationEquipments(e);
                }
            });
            toollinkJPanel.setEquipmentPanel(equiplinkJPanel);

        relationsJPanel.add(toollinkJPanel, "��װ");
    }

    /**
     * ����ʵ�������Ϲ���
     * @param stepType String
     * ���⣨2��20080820 �촺Ӣ�޸�
     */
    //private synchronized void newMateriallinkJPanel(String stepType)
    private void newMateriallinkJPanel(String stepType)//anan
    {

            if (materiallinkJPanel != null)
            {
                relationsJPanel.remove(materiallinkJPanel);
            }
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType);
            relationsJPanel.add(materiallinkJPanel, "����");

    }


    /**
     * ��ý���ģʽ
     * @return int
     */
    public int getMode()
    {
        return mode;
    }


    /**
     * Ϊ���������������������
     */
    private void initSpeCharaterTextPanel()
    {
        technicsContentJTextArea.setDrawInfo(CappClientHelper.getSpechar());

        String path = RemoteProperty.getProperty("spechar.image.path");
        if (path == null)
        {
            technicsContentJTextArea.setFilePath("/spechar/");
        }
        else
        {
            technicsContentJTextArea.setFilePath(path.trim());

        }
    }
    //CCBegin SS10
    private String getTechnicsType(){
	
	
	
	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
	.getSelectedObject().getObject();
	String techType="";
	if (obj instanceof consQMAssembleProcedureInfo) {
		consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
		techType = info.getTechnicsType().getCodeContent();
	} else if (obj instanceof QMFawTechnicsInfo) {

		QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

		techType = info.getTechnicsType().getCodeContent();

	}else if(obj instanceof consQMMachineProcedureInfo){
		
		consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
		techType = info.getTechnicsType().getCodeContent();
		
	}
	
	return techType;
	
	
}
    //CCEnd SS10

    /**
     *
     * <p>Title:�����߳� </p>
     * <p>Description: ����������߳�</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author not Ѧ��
     * @version 1.0
     */
    class WorkThread extends Thread
    {
        public void run()
        {
            save();
        }
    }

}
