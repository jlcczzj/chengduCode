/** ���ɳ���TechnicsPaceJPanel.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/27 �촺Ӣ  ԭ���ڹ���ġ��Ƿ񱣴桱��ʾ����������ȡ����ť��ĿǰΪ���ǡ��͡���
 *                        �������ڴ�һ��������и���ʱ������֮������л���ر�ʱ���ڵ����ġ��Ƿ񱣴桱��ʾ��������һ����ȡ������ť��
 *                        ��ע�������¼���ΪCRSS-005
 * CR2 2009/04/30     ����    ԭ�򣺼�ͼ�����Ĵ�����ֻ����û������ļ�ͼ���д���
 *                            ������
 *                            ��ע�����ܲ����������ƣ�"������£������� ��������ճ��"��  
 *                            
 *CR3  2009/06/01  ������  �μ�TD��:2202
 *CR4 2010/04/02 �촺Ӣ  ԭ�򣺲μ�TD����2245
 * CR5 2010/06/04  �촺Ӣ ԭ��:�μ�TD����2263
 * CCBegin by liunan 2011-08-25 �򲹶�P035
 * CR6 2011/07/14 ���� �μ�TD2423
 * CCEnd by liunan 2011-08-25
 * SS1 �����䡰���빤��������Ǩ�� zhaoqiuying 2013-01-23
 * SS2 ��ݹ������������� guoxiaoliang 2013-03-24        
 * SS3 ��ݹ����������֤�Ƿ�Ϊ�� ���� 2014-4-3     
 * SS4 ���򹤲�������ͼ,����˳��� liuyang 2014-3-31      
 * SS5 �ɶ��������ӹ��ղ���  guoxiaoliang 2016-7-26    
 * SS6 �ɶ����Ӳ��빤�� guoxiaoliang 2016-7-29 
 * SS7 �ɶ�����������Դ���ӹ�����Դ���� guoxiaoliang 2016-8-2
 * SS8 �ɶ����ӵ���������  guoxiaoliang 2016-8-3 
 * SS9 A004-2017-3486 �ɶ���������Ľ���޷��򿪹���ҳ��Ĵ��� liunan 2017-4-5
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
import javax.swing.JCheckBox;
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
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
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
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.client.model.CTool;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolInfo;
import com.faw_qm.wip.model.WorkableIfc;

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
 * ����(2) 20080602 �촺Ӣ�޸�  �����������ĵ�ת�����ĵ�master�͹���Ĺ���
 * ���⣨3��20080709 xucy add  �޸�ԭ�򣺹�������������Ԥ��������
 * ���⣨4��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��
 * ���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
 * ����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������
 * ���⣨6��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
 *                                  ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
 */


public class TechnicsPaceJPanel extends ParentJPanel
{
    /**�����������*/
    /**��ť���*/
    private JPanel buttonJPanel = new JPanel();
//CCBegin SS1
    //CCBegin by chudaming 20091119 ���빤��
    private JLabel insertJLabel=new JLabel();
    private JCheckBox insertCheckBox=new JCheckBox();
  //CCEnd by chudaming 20091119 ���빤��
  //CCEnd SS1
    /**������*/
    private JLabel paceNumberJLabel = new JLabel();
    private CappTextField paceNumJTextField;
    private JLabel numDisplayJLabel = new JLabel();
    
    //CCBegin by leixiao 2010-3-30  ���ӹ������
    /**�������*/
    private JLabel descStepNumberJLabel = new JLabel();
    private JLabel descNumDisplayJLabel = new JLabel();
    private CappTextField descNumJTextField;
   //CCEnd by leixiao 2010-3-30  ���ӹ������

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

    //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
    /**�豸�������*/
    //CCBegin SS7
    private ProcedureUsageEquipmentJPanel equiplinkJPanel = new
    ProcedureUsageEquipmentJPanel(false);
//CCEnd SS7

    /**��װ�������*/
    private ProcedureUsageToolJPanel toollinkJPanel = new
            ProcedureUsageToolJPanel();


    /**���Ϲ������*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel = new
            ProcedureUsageMaterialJPanel();
	//CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
    

    /**�ĵ��������*/
    private ProcedureUsageDocJPanel doclinkJPanel = new
            ProcedureUsageDocJPanel();


    /**��ⰴť*/
    private JButton storageJButton = new JButton();

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
    private CappExAttrPanel processControlJPanel;
    private JScrollPane scrollpane = new JScrollPane();
    //add by wangh on 20070326(�Ƿ���ʾTS16949�Ĺ�����߹�����Ϣ��)
    private static boolean ts16949 = (RemoteProperty.getProperty(
        "TS16949", "true")).equals("true");
    
    //CCBegin SS5
    private ProcedureOtherAttributeJPanel otherpanel = null;
	private JPanel otherAttibuteJPanel = new JPanel();
	private Hashtable otherAttributeTable = new Hashtable();
    
    //CCEnd SS5

    //CCBegin SS2 ������ݼ̳д��౨��
    public TechnicsPaceJPanel(){
    	
    }
    //CCEnd SS2
    /**
     * ���캯��
     * @param parentframe ���ñ���ĸ�����
     */
    public TechnicsPaceJPanel(JFrame parentframe)
    {
        try
        {
            parentJFrame = parentframe;
             //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            technicsContentJTextArea = new SpeCharaterTextPanel(parentframe,true);//CR4
             //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
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
        buttonJPanel.setLayout(gridBagLayout1);
        paceNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        paceNumberJLabel.setHorizontalTextPosition(SwingConstants.
                RIGHT);
        paceNumberJLabel.setText("*������");

        //CCBegin by leixiao 2010-3-30  ���ӹ������
        descNumJTextField = new CappTextField(parentJFrame, "�������",
                10, false);
        descStepNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descStepNumberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        descStepNumberJLabel.setText("*�������");
        descNumJTextField.setMaximumSize(new Dimension(10, 24));
        descNumJTextField.setMinimumSize(new Dimension(10, 24));
        descNumJTextField.setPreferredSize(new Dimension(10, 24));
        descNumJTextField.setMargin(new Insets(1, 1, 1, 1));
        //CCEnd by leixiao 2010-3-30  ���ӹ������
        
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame);
        //���⣨3��20080709 xucy add  �޸�ԭ�򣺹�������������Ԥ��������
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
        
        //CCBegin SS5
        otherAttibuteJPanel.setLayout(gridBagLayout3);
        //CCEnd SS5
        
        
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
        storageJButton.setMaximumSize(new Dimension(97, 23));
        storageJButton.setMinimumSize(new Dimension(97, 23));
        storageJButton.setPreferredSize(new Dimension(97, 23));
        storageJButton.setMnemonic('T');
        storageJButton.setText("���(ST)");
        storageJButton.addActionListener(new java.awt.event.
                                         ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                storageJButton_actionPerformed(e);
            }
        });


        numDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        numDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        numDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        splitJPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitJPanel.setDebugGraphicsOptions(0);
        splitJPanel.setMinimumSize(new Dimension(337, 357));
        splitJPanel.setPreferredSize(new Dimension(337, 357));
        splitJPanel.setContinuousLayout(true);
        splitJPanel.setOneTouchExpandable(true);
        splitJPanel.setResizeWeight(1.0);
        upJPanel.setLayout(gridBagLayout3);
        processHoursJPanel = new
                             ProcessHoursJPanel(parentJFrame);
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
        paceNumJTextField.setMargin(new Insets(1, 1, 1, 1));
      //CCBegin SS1      
        //CCBegin by chudaming 20091119 ���빤��
//        parentTechnics.getTechnicsType().getCodeContent();
        insertJLabel.setText("���빤��");
        insertCheckBox.setSelected(true);
      //CCEnd by chudaming 20091119 ���빤��
      //CCEnd SS1
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
        if (ts16949) {
          jTabbedPanel.add(flowExtendJPanel, "��������");
          jTabbedPanel.add(femaExtendJPanel, "����FMEA");
          jTabbedPanel.add(controlExtendJPanel, "���Ƽƻ�");
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
        buttonJPanel.add(storageJButton, new GridBagConstraints(1, 0,
                1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(jPanel1, new GridBagConstraints(2, 0, 1, 1,
                1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0,
                0));

        //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
        //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
        equiplinkJPanel.addListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                toollinkJPanel.addRelationTools(e);
            }
        });
        //�Ӵ˼�����ԭ�򣺵���װ���������빤װʱ���豸�������Ҫ�����빤װ��Ҫ�������豸
        toollinkJPanel.addListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                equiplinkJPanel.addRelationEquipments(e);
            }
        });
        equiplinkJPanel.setToolPanel(toollinkJPanel);
        toollinkJPanel.setEquipmentPanel(equiplinkJPanel);

        relationsJPanel.add(equiplinkJPanel,
                            "�豸");
        relationsJPanel.add(toollinkJPanel,
                            "��װ");
        relationsJPanel.add(materiallinkJPanel,
                            "����");
        relationsJPanel.add(doclinkJPanel,
                            "�ĵ�");
        relationsJPanel.add(drawingLinkPanel,
                            "��ͼ");
        
        
        //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
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
      //CCBegin by leixiao 2010-3-30  ���ӹ������
        upJPanel.add(descStepNumberJLabel, new GridBagConstraints(0, 1, 1,
                1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(-2, 15, 2, 2), 0, 0));
        upJPanel.add(descNumJTextField,
                new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(2, 8, 2, 8), 0, 0));
        upJPanel.add(descNumDisplayJLabel, new GridBagConstraints(2, 1, 2,
                1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(-2, 8, 2, 8), 0, 0));
      //CCEnd by leixiao 2010-3-30  ���ӹ������
        upJPanel.add(nulllabel, new GridBagConstraints(2, 0, 2, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 8, 2, 8), 0, 0));

        upJPanel.add(processHoursJPanel, new GridBagConstraints(0, 4,
                4, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 15, 4, 8), 0,
                0));
        upJPanel.add(numDisplayJLabel, new GridBagConstraints(1, 0, 2,
                1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 150), 289, 0));
      //CCBegin SS1
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
      //CCEnd SS1
        upJPanel.add(technicsContentJTextArea,
                     new GridBagConstraints(1, 3, 3, 1, 1.0, 1.0
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

            technicsContentJLabel.setText(QMMessage.
                                          getLocalizedMessage(RESOURCE,
                    "mtechContentJLabel", null));

            //JButton
            paraJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "ParaJButton", null));
            saveJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "QuitJButton", null));
            storageJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "storageJButton", null));
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
        //CCBegin SS6
        //CCBegin SS1
        if(isBSXInsertPace(parentTechnics.getTechnicsType().getCodeContent())||
        		isCdInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
        {
            insertJLabel.setVisible(true);
            insertCheckBox.setVisible(true);
        }else
        {
            insertJLabel.setVisible(false);
            insertCheckBox.setVisible(false);
        }
        //CCEnd SS1
        //CCEnd SS6
        
        
        //���ÿؼ���ʾ״̬
        paceNumJTextField.setVisible(true);
        numDisplayJLabel.setVisible(false);
      //CCBegin by leixiao 2010-3-30  ���ӹ������
        descNumJTextField.setVisible(true);
        descNumDisplayJLabel.setVisible(false);
      //CCEnd by leixiao 2010-3-30  ���ӹ������

        technicsContentJTextArea.speCharaterTextBean.setEditable(true);

        //���⣨3��20080820 �촺Ӣ�޸�     begin
        newEquiplinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(equiplinkJPanel != null)
        {
        equiplinkJPanel.setMode("Edit");
        //CCBegin SS7
		equiplinkJPanel.setProcedurePace(step);
		//CCEdn SS7
        }
        newToollinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(toollinkJPanel != null)
        {
        	toollinkJPanel.setMode("Edit");
        	//CCBegin SS7
        	toollinkJPanel.setProcedurePace(step);
        	//CCEnd SS7
        }
        newMateriallinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setMode("Edit");
        //CCBegin SS7
        materiallinkJPanel.setProcedurePace(step);
        //CCEnd SS7
        }
        doclinkJPanel.setMode("Edit");
        
        //CCBegin SS7
        
//        doclinkJPanel.setProcedurePace(step);
        //CCEnd SS7
        relationsJPanel.add(doclinkJPanel,
        "�ĵ�");
        relationsJPanel.add(drawingLinkPanel,
        "��ͼ");
        drawingLinkPanel.setModel(2); //EDIT
        
        //CCBegin SS7
        drawingLinkPanel.setQMProcedureIfc(step);
        //CCEnd SS7
        
        //���⣨3��20080820 �촺Ӣ�޸�   end
        //�㲿������
        newPartlinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if (partlinkJPanel != null)
        {
        	//CCBegin SS7
        	
        	partlinkJPanel.setProcedurePace(step);
        	//CCEnd SS7
            partlinkJPanel.setMode("Edit");
            //������ͼ���
        }
        processHoursJPanel.setCreateMode();
      //CCBegin SS1
        //CCBegin by chudaming 20091119 ���빤��
        this.insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 ���빤��
      //CCEnd SS1
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
                //CCBegin by leixiao 2010-3-30  ���ӹ������
                descNumJTextField.setText(String.valueOf(getStepInitNumber()));
                //CCEnd by leixiao 2010-3-30  ���ӹ������
            }
            else
            {
                if (number >= 99999)
                {
                    return;
                }
                paceNumJTextField.setText(String.valueOf(number +
                        getStepLong()));
              //CCBegin by leixiao 2010-3-30  ���ӹ������
                descNumJTextField.setText(String.valueOf(number + getStepLong()));
              //CCEnd by leixiao 2010-3-30  ���ӹ������
            }
           
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.ERROR);
        }
        setButtonVisible(true);
        paceTS16949Panel(getProcedure().getTechnicsType().getCodeContent());
        processControlJPanel.groupPanel.flowMultiList = processFlowJPanel.groupPanel.multiList;//CR3
        
        //CCBegin SS5
        //CCBegin SS9
        if(isCdInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
        //CCEnd SS9
        newOtherAttributePanel(getProcedure().getTechnicsType()
				.getCodeContent());
        
        //CCEnd SS5
        
        repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setCreateMode() end...return is void");
        }
    }
//CCBegin SS1
    /**
     * ����CAPP.property �������ж��Ƿ����ڱ�������빤��
     * @param techType
     * @return
     *  
     */
    public boolean isBSXInsertPace(String techType)
    {
        String techAllType = RemoteProperty.getProperty("bsx_technics_insertPace");
        String[] typeVec = techAllType.split(",");
        for(int i=0;i<typeVec.length;i++)
        {
            if(typeVec[i].equals(techType))
                return true; 
        }
        return false;
    }
  //CCEnd SS1
    
    //CCBegin SS6 
    public boolean isCdInsertPace(String techType)
    {
        String techAllType = RemoteProperty.getProperty("cd_technics_insertPace");
        String[] typeVec = techAllType.split(",");
        for(int i=0;i<typeVec.length;i++)
        {
            if(typeVec[i].equals(techType))
                return true; 
        }
        return false;
    }
    
    //CCEnd SS6
    
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
      //CCBegin SS1
        if(isBSXInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
        { 
            insertJLabel.setVisible(true);
            insertCheckBox.setVisible(true);
        }else
        {
            insertJLabel.setVisible(false);
            insertCheckBox.setVisible(false);
        }
        //CCEnd SS1
        //���ÿؼ���ʾ״̬
        paceNumJTextField.setVisible(true);
        paceNumJTextField.setText(Integer.toString(getProcedure().
                getStepNumber()));
        numDisplayJLabel.setVisible(false);
        
        //CCBegin by leixiao 2010-3-30  ���ӹ������
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(getProcedure().getDescStepNumber());
        descNumDisplayJLabel.setVisible(false);
      //CCEnd by leixiao 2010-3-30  ���ӹ������
      //CCBegin SS1 
        //CCBegin by chudaming 20091119 ���빤��
        insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 ���빤��
      //CCEnd SS1
        //��ͼ���ͼ���������˳���ܱ䣬ԭ�����������ü�ͼ����������ü�ͼʱ���ڼ����¼��н���ͼ����ÿ�
        //��ͼ
        drawingLinkPanel.setProcedure(getProcedure());
        drawingLinkPanel.setModel(2); //EDIT
        technicsContentJTextArea.speCharaterTextBean.setEditable(true);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
        	//20081212 xucy �޸�
            technicsContentJTextArea.clearPaceAll();
            technicsContentJTextArea.resumeData(v);
        }

        processHoursJPanel.setProcedure(getProcedure());
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
                    }
                    else
                    {
                        processHoursJPanel.setEditMode();

                    }
                }
                else
                {
                    processHoursJPanel.setEditMode();
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
        }

        //���⣨3��20080820 �촺Ӣ�޸�  begin
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
        //CCBegin SS7
        equiplinkJPanel.setProcedurePace(parentProcedure);
        //CCEnd SS7
        equiplinkJPanel.setProcedure(getProcedure());
        equiplinkJPanel.setMode("Edit");
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        	//CCBegin SS7
        	toollinkJPanel.setProcedurePace(parentProcedure);
        	//CCEnd SS7
        	toollinkJPanel.setProcedure(getProcedure());
        	toollinkJPanel.setMode("Edit");
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {   
        	//CCBegin SS7
        	materiallinkJPanel.setProcedurePace(parentProcedure);
        	//CCEnd SS7
        	materiallinkJPanel.setProcedure(getProcedure());
        	materiallinkJPanel.setMode("Edit");
        }
        //20081120 �촺Ӣ�޸�   ����״̬���ĵ�Ӧ�ÿɱ༭
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel,
        "�ĵ�");
        relationsJPanel.add(drawingLinkPanel,
        "��ͼ");
        //���⣨3��20080820 �촺Ӣ�޸� end
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());

        if (partlinkJPanel != null)
        {
        	//CCBegin SS7
        	
        	partlinkJPanel.setProcedurePace(parentProcedure);
        	//CCEnd SS7
            partlinkJPanel.setProcedure(getProcedure());
            partlinkJPanel.setMode("Edit");
        }
        //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ����Դ���ɿ����õ�
        String resourceUpdateflag = RemoteProperty.getProperty(
                "updateResourceLink", "true");
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
                             //���⣨3��20080820 �촺Ӣ�޸� 
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

        doclinkJPanel.setMode("Edit");
        f1.setEditMode();
        setButtonVisible(true);
        
        //CCBegin SS5
        //CCBegin SS9
        if(isCdInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
        {
        newOtherAttributePanel(getProcedure().getTechnicsType()
				.getCodeContent());
		otherpanel.setProcedure(getProcedure());
      }
        //CCEnd SS9
        //CCEnd SS5
        
        
        //add by wangh on 20070310
        paceTS16949Panel(getProcedure().getTechnicsType().getCodeContent());
        processControlJPanel.groupPanel.flowMultiList = processFlowJPanel.groupPanel.multiList;//CR3
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
      //CCBegin SS1
        if(isBSXInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
        {
            insertJLabel.setVisible(true);
            insertCheckBox.setVisible(true);
        }else
        {
            insertJLabel.setVisible(false);
            insertCheckBox.setVisible(false);
        }
        //CCEnd SS1
        //���ÿؼ���ʾ״̬
        paceNumJTextField.setVisible(false);
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        numDisplayJLabel.setVisible(true);
        
        //CCBegin by leixiao 2010-3-30  ���ӹ������
        descNumJTextField.setVisible(false);
        descNumDisplayJLabel.setText(getProcedure().getDescStepNumber());
        descNumDisplayJLabel.setVisible(true);
      //CCEnd by leixiao 2010-3-30  ���ӹ������
      //CCBegin SS1 
        //CCBegin by chudaming 20091119 ���빤��
        insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 ���빤��
      //CCEnd SS1 
        technicsContentJTextArea.speCharaterTextBean.setEditable(false);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            technicsContentJTextArea.resumeData(v);
        }
        processHoursJPanel.setProcedure(getProcedure());
        processHoursJPanel.setViewMode();
        setButtonVisible(false);
        //���⣨3��20080820 �촺Ӣ�޸� begin
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
        //���⣨4��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
        //                                 ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
        drawingLinkPanel.setProcedure(getProcedure());//CR2
        //���⣨4��20090112 ����.
        relationsJPanel.add(drawingLinkPanel,
        "��ͼ");
        //���⣨3��20080820 �촺Ӣ�޸�  end
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
        
        //CCBegin SS5
        //CCBegin SS9
        if(isCdInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
        {
        newOtherAttributePanel(getProcedure().getTechnicsType()
				.getCodeContent());
		otherpanel.setProcedure(getProcedure());
      }
        //CCEnd SS9
        //CCEnd SS5

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() end...return is void");
        }
    }

    //CCBegin SS5
    
    /**
	 * add by tangshutao for qingqi 2007.08.07
	 * ������ĸ������Ե���ʾ�������豸����һ������ʾ��ʽ������ʹ�ø������԰�ť
	 * 
	 * @param processType
	 *            String
	 */
	private void newOtherAttributePanel(String processType) {
		
		if (processType.equals(existProcessType)) {
			// getProcedure().setExtendAttributes(null);
			if (otherpanel != null) {

				otherAttibuteJPanel.remove(otherpanel);
			}
			

			
			if (otherAttributeTable.get(processType) == null) {
				otherpanel = new ProcedureOtherAttributeJPanel(procedure
						.getBsoName(), "");
				
				
				otherpanel.setProcedure(getProcedure());
				ExtendAttContainer c = otherpanel.getExtendAttributes();
				if (c != null) {
					// if (otherpanel.check()) {
					getProcedure().setExtendAttributes(c);
					// }
					// else {
					// return;
					// }
				}
				existProcessType = processType;

				otherAttributeTable.put(processType, otherpanel);
			} else {
				otherpanel = (ProcedureOtherAttributeJPanel) otherAttributeTable
						.get(processType);
				otherpanel.setProcedure(getProcedure());
			}
		}
		otherpanel.clear();

		if (mode == CREATE_MODE || mode == UPDATE_MODE) {
			otherpanel.setEditMode();
		} else {
			otherpanel.setViewMode();
		}
		otherAttibuteJPanel.add(otherpanel, new GridBagConstraints(0, 0, 1, 1,
				1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		relationsJPanel.add(otherAttibuteJPanel, "���ղ���");
		repaint();
		processType = "";
	}
    
    //CCEnd SS5
    
    

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

        //CCBegin SS3
        if(paceNumJTextField.check() == true&&descNumJTextField.check()==true){
        	isOK = true;
        }else{
        	isOK = false;
        }
        //CCEnd SS3
        if (isOK)
        {
            //���鹤�������Ƿ�Ϊ��
            if (technicsContentJTextArea.save() == null ||
                technicsContentJTextArea.save().trim().equals(""))
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
                        null);
                isOK = false;
                technicsContentJTextArea.getTextComponent().grabFocus();
            }
            else 
            {
            	String tempString=technicsContentJTextArea.save().trim();
            	if(1==tempString.length())
            	{
            		int tempChar=tempString.charAt(0);
            		if(tempChar==128)
            		{
            			message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
                                null);
                        isOK = false;
                        technicsContentJTextArea.getTextComponent().grabFocus();
            		}
            	}
            }

        }

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
     * @return ���������¡��鿴������Ϊģʽ
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

                d = new TParamJDialog(procedure.getBsoName(), parentJFrame,"");
                d.setProcedure(getProcedure());
                //CCBegin by liunan 2011-08-25 �򲹶�P035
                //CR6 begin
                TechnicsContentJPanel.addFocusLis.setCompsFocusListener(d.getContentPane());
                //CR6 end
                //CCEnd by liunan 2011-08-25
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
            saveWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)
        {
            saveWhenUpdate();
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
        setCreateMode();
        paceNumJTextField.setText(num);

        /* paceNumJTextField.setText("");
         drawingExportJComboBox.setSelectedIndex(0);
         technicsContentJTextArea.clearPaceAll();
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
        int i = confirmAction(s);//Begin CR1
		if (i == JOptionPane.YES_OPTION)
		{
			saveWhenCreate();
		}
		if (i == JOptionPane.NO_OPTION)
		{
			setVisible(false);
			isSave = true;
		}
		if (i == JOptionPane.CANCEL_OPTION)
		{
			isSave = false;
		}//End CR1
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.QuitWhenCreate() end...return is void");
        }
    }


//    /**
//     * ����ģʽ�£��˳���ť��ִ�з���
//     * add by guoxl 2009-1-6(ֻ�и��½��������޸���,�ŵ����Ƿ񱣴�Ի���)
//     */
//    private void quitWhenUpdate()
//    {
//        String s = QMMessage.getLocalizedMessage(RESOURCE,
//                                                 CappLMRB.
//                                                 IS_SAVE_QMPROCEDURE_PACE_UPDATE, null);
//        if (confirmAction(s))
//        {
//            saveWhenUpdate();
//        }
//        else
//        {
//            setVisible(false);
//            isSave = true;
//        }
//    }
    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     * add by guoxl 2009-1-6(ֻ�и��½��������޸���,�ŵ����Ƿ񱣴�Ի���)
     */
    private void quitWhenUpdate()
    {
    
    	
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 IS_SAVE_QMPROCEDURE_PACE_UPDATE, null);
         
        boolean ischange=TechnicsContentJPanel.addFocusLis.finalChangeValue();

        if(ischange) {
        	
        	int i = confirmAction(s);//Begin CR1
    		if (i == JOptionPane.YES_OPTION)
    		{
    			saveWhenUpdate();
    		}
    		if (i == JOptionPane.NO_OPTION)
    		{
    			setVisible(false);
    			isSave = true;
    		}
    		if (i == JOptionPane.CANCEL_OPTION)
    		{
    			isSave = false;
    		}//End CR1
        }
        else
        {
        	
            setVisible(false);
            isSave = true;
            
        }
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
    private int confirmAction(String s)//CR1
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
        JOptionPane okCancelPane = new JOptionPane();
		return okCancelPane.showConfirmDialog(getParentJFrame(), s, title,
				JOptionPane.YES_NO_CANCEL_OPTION);//CR1
    }


    /**
     * ���ù��������������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     */
    private CappWrapData commitAttributes()
    {
    	this.setProcedure(this.getProcedure());//CR1
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
      //CCBegin by leixiao 2010-3-30  ���ӹ������
        getProcedure().setDescStepNumber(descNumJTextField.getText().trim());
      //CCEnd by leixiao 2010-3-30  ���ӹ������

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
        //CCBegin SS5
        if (otherpanel != null) {
			ExtendAttContainer c = otherpanel.getExtendAttributes();
			// System.out.println("c: " + c);
			if (c != null) {
				if (otherpanel.check()) {
					getProcedure().setExtendAttributes(c);
				}
				// tangshutao modify end
				else {
					if (verbose) {
						System.out.println("��չ����¼�����");
					}
					isSave = false;
					// return null;
				}
			}
		}
        
        
        //CCEnd SS5
        
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
        if (processFlowJPanel.check())
        {
            //���ù�������
            procedure.setProcessFlow(processFlowJPanel.
                                             getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("������������¼�����");
            }
            isSave = false;
            return null;
        }
        if (femaJPanel.check()) {
          //����FEMA
          procedure.setFema(femaJPanel.getExAttr());
        }
        else {
          if (verbose) {
            System.out.println("����FMEA¼�����");
          }
          isSave = false;
          return null;
        }
        if (processControlJPanel.check())
        {
            //���ù��̿���
            procedure.setProcessControl(processControlJPanel.
                                             getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("�������̿���¼�����");
            }
            isSave = false;
            return null;
        }


        //������й���(�豸����װ�����ϡ��ĵ�)
        Vector docLinks = new Vector();
        Vector equipLinks = new Vector();
        Vector toolLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector partLinks = new Vector();
	    ArrayList updatedrawings = null;//Begin CR2
        ArrayList deletedrawings = null;//End CR2
        //����(2) 20080602 �촺Ӣ�޸�  �����������ĵ�ת�����ĵ�master�͹���Ĺ���
        Vector docMasterLinks = new Vector();
        
        //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
        //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������   begin
        Vector rEquipLinks = new Vector();
        Vector rToolLinks = new Vector();
        Vector rMaterialLinks = new Vector();
        Vector rDocLinks = new Vector();
        Vector rPartLinks = new Vector();
        //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
        //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������   end
        try
        {
            docLinks = doclinkJPanel.getAllLinks();
            //���⣨5��
            rDocLinks = doclinkJPanel.getDeletedLinks();
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

            }
            //���⣨3��20080820 �촺Ӣ�޸�   �޸�ԭ�򣺹������������Ԥ������
            //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
            //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������  
            if (equiplinkJPanel != null)
            {
	            equipLinks = equiplinkJPanel.getAllLinks();
	            //���⣨5��
	            rEquipLinks = equiplinkJPanel.getDeletedLinks();
            }
            if (toollinkJPanel != null)
            {
	            toolLinks = toollinkJPanel.getAllLinks();
	          //���⣨5��
	            rToolLinks = toollinkJPanel.getDeletedLinks();
            }
            if (materiallinkJPanel != null)
            {
                materialLinks = materiallinkJPanel.getAllLinks();
              //���⣨5��
                rMaterialLinks = materiallinkJPanel.getAllLinks();
            }
            if (partlinkJPanel != null)
            {
                partLinks = partlinkJPanel.getAllLinks();
                //���⣨5��
                rPartLinks = partlinkJPanel.getAllLinks();
            }
            //CCBegin  SS4�ж�˳����Ƿ��ظ�
            boolean checkFlag = drawingLinkPanel.checkSeqNum();
            if (checkFlag) {
            	return null;
            }
            
            //CCEnd  SS4
            Object[] obj = drawingLinkPanel.getDrawings();
            if (obj != null)
            {
				updatedrawings = (ArrayList) obj[0];//Begin CR3
				deletedrawings = (ArrayList) obj[1];//End CR3
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
        for (int k = 0; k < docMasterLinks.size(); k++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(k));
        }
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
		// �ϲ���ͼ��Դ����   Begin CR2
		/*if (drawingLinks != null)
		{
			for (int n = 0; n < drawingLinks.size(); n++)
			{
				resourceLinks.addElement(drawingLinks.get(n));
			}
		}*/
        //End CR2
        //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
        //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������   begin
        Vector delLinks = new Vector();
        for(int a = 0; a < rEquipLinks.size(); a++)
        {
        	delLinks.addElement(rEquipLinks.elementAt(a));
        }
        for(int b = 0; b < rToolLinks.size(); b++)
        {
        	delLinks.addElement(rToolLinks.elementAt(b));
        }
        for(int c = 0; c < rMaterialLinks.size(); c++)
        {
        	delLinks.addElement(rMaterialLinks.elementAt(c));
        }
        for(int d = 0; d < rDocLinks.size(); d++)
        {
        	delLinks.addElement(rDocLinks.elementAt(d));
        }
        if (partLinks != null)
        {
	        for(int e = 0; e < rPartLinks.size(); e++)
	        {
	        	delLinks.addElement(rPartLinks.elementAt(e));
	        }
        }
        //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
        //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������   end
        
        //��װ������Ϣ
        CappWrapData cappWrapData = new CappWrapData();
        
        //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
        //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������   begin
        if(delLinks != null)
        {
        	cappWrapData.setDeleteLinks(delLinks);
        }
        //���⣨5��20081231 xucy  �޸�ԭ���ڹ����д�����������������һ����װ��Ȼ�������������ѹ����Ĺ�װɾ��
        //����֮�����еĹ�װû��ɾ����������Դ�Ĺ���Ҳ��������������   end
        //���ù���
        cappWrapData.setQMProcedureIfc(getProcedure());
        //��װ����
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
		cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR2
	    cappWrapData.setDeleteDrawing(deletedrawings);//End CR2
	    return cappWrapData;
    }


    //add by wangh on 20070310
   private void paceTS16949Panel(String processType)
  {
  	//CCBegin SS1
  	if(!isBSXInsertPace(parentTechnics.getTechnicsType().getCodeContent()))
  	{
  	//CCEnd SS1	
      if (!processType.equals(existProcessType))
      {
          if (processFlowJPanel != null)
          {
              flowExtendJPanel.remove(processFlowJPanel);
          }
          if (processFlowTable.get(processType) == null)
          {
              try
              {
                  processFlowJPanel = new CappExAttrPanel(procedure.getBsoName(),
                          "��������", 1);
              }
              catch (QMException ex)
              {
                  ex.printStackTrace();
              }
              processFlowTable.put(processType, processFlowJPanel);
          }
          else
          {
              processFlowJPanel = (CappExAttrPanel) processFlowTable.get(
                      processType);
          }
          // CCBegin by leixiao 2008-10-28 ԭ�򣺽��ϵͳ���� ���������ݴ���
//        CCBegin by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
//          if ( ( (QMProcedureInfo) selectedNode.getObject().getObject()).
//              getIsProcedure()) {  
//            processFlowJPanel.setProIfc((QMProcedureInfo) procedure);
//          }
//          else if (parentProcedure != null) {  
            processFlowJPanel.setProIfc(procedure);
//          CCEnd by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
//          }

          // CCEnd by leixiao 2008-10-28 ԭ�򣺽��ϵͳ���� 
          if (femaJPanel != null)
          {
              femaExtendJPanel.remove(femaJPanel);
          }
          if (femaTable.get(processType) == null)
          {
              try
              {
                  femaJPanel = new CappExAttrPanel(procedure.getBsoName(),
                          "����FMEA", 1);
              }
              catch (QMException ex)
              {
                  ex.printStackTrace();
              }
              femaTable.put(processType, femaJPanel);
          }
          else
          {
              femaJPanel = (CappExAttrPanel) femaTable.get(
                      processType);
          }
//        CCBegin by leixiao 2008-10-28 ԭ�򣺽��ϵͳ���� 
//        CCBegin by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
//          if ( ( (QMProcedureInfo) selectedNode.getObject().getObject()).
//              getIsProcedure()) {
//        	  femaJPanel.setProIfc((QMProcedureInfo) procedure);
//          }
//          else if (parentProcedure != null) {
            femaJPanel.setProIfc(procedure);
//          CCEnd by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
//          }
          // CCEnd by leixiao 2008-10-28 ԭ�򣺽��ϵͳ�������������ݴ��� 
          if (processControlJPanel != null)
          {
              controlExtendJPanel.remove(processControlJPanel);
          }
          if (processControlTable.get(processType) == null)
          {
              try
              {
                  processControlJPanel = new CappExAttrPanel(procedure.getBsoName(),
                          "���Ƽƻ�", 1);
              }
              catch (QMException ex)
              {
                  ex.printStackTrace();
              }
              processControlTable.put(processType, processControlJPanel);
          }
          else
          {
              processControlJPanel = (CappExAttrPanel) processControlTable.get(
                      processType);
          }
          //CCBegin by leixiao 2008-10-28 ԭ�򣺽��ϵͳ�������������ݴ���
//        CCBegin by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
//          if ( ( (QMProcedureInfo) selectedNode.getObject().getObject()).
//              getIsProcedure()) {
//              processFlowJPanel.setProIfc((QMProcedureInfo) procedure);
//          }
//          else if (parentProcedure != null) {
            processControlJPanel.setProIfc(procedure);
//          CCEnd by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
//          }
          //CCEnd by leixiao 2008-10-28 ԭ�򣺽��ϵͳ���� 
           existProcessType = processType;
      }
      processFlowJPanel.clear();
      femaJPanel.clear();
      processControlJPanel.clear();
      if (mode == CREATE_MODE ||
          mode == UPDATE_MODE)
      {
          processFlowJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
          femaJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
          processControlJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
      }
      else
      {
          processFlowJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
          femaJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
          processControlJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
      }
      if (mode != CREATE_MODE)
      {
          processFlowJPanel.show(getProcedure().getProcessFlow());
          femaJPanel.show(getProcedure().getFema());
          processControlJPanel.show(getProcedure().getProcessControl());
      }
      flowExtendJPanel.add(processFlowJPanel,
                       new GridBagConstraints(0, 0, 1, 1, 1.0,
                                              1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 0, 0, 0), 0, 0));
     femaExtendJPanel.add(femaJPanel,
                       new GridBagConstraints(0, 0, 1, 1, 1.0,
                                              1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 0, 0, 0), 0, 0));
     controlExtendJPanel.add(processControlJPanel,
                       new GridBagConstraints(0, 0, 1, 1, 1.0,
                                              1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 0, 0, 0), 0, 0));
//CCBegin SS1
}
//CCEnd SS1
                                          //add by wangh on 200726(���ù������̣�����FMEA�Ϳ��Ƽƻ��Ƿ�ɼ�)
//                                          if (!ts16949) {
//                                            processFlowJPanel.setVisible(false);
//                                            femaJPanel.setVisible(false);
//                                            processControlJPanel.setVisible(false);
//                                          }
   //add by guoxl on 2009-1-7(���������½��������пؼ����Ӽ���)
     if(this.getMode()==0)
     {
    	 TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
     }
    //add by guoxl end on 2009-1-7
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
        //CCBegin SS3
        //���鹤������Ƿ�Ϊ����
        if (!checkIsInteger(descNumJTextField.getText().trim()))
        {
        	descNumJTextField.grabFocus();
        	descNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //CCEnd SS3
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
        QMProcedureInfo returnProce;//CR1
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
          //CCBegin SS1  
          //CCBegin by chudaming 20091119 ���빤��
//            returnProce = (QMProcedureInfo) useServiceMethod(
//                    "StandardCappService", "createQMProcedure", paraClass,
//                    obj);          
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
            //CCEnd by chudaming 20091119 ���빤��
          //CCEnd SS1
            //Begin CR2
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR2

            
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //���½��Ĺ���ڵ���Ϲ�����
            OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            parentProcedure = (QMProcedureInfo) selectedNode.getObject().getObject();//CR1
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
        String s = QMMessage.getLocalizedMessage(RESOURCE, "109", null);//Begin CR1
		int i = confirmAction(s);
		if (i == JOptionPane.YES_OPTION)
		{
			setCreateMode();
			isSave = false;
			setButtonWhenSave(true);
			return;
		}
		if (i == JOptionPane.NO_OPTION)
		{
			setProcedure(returnProce);
			mode = 0;
		}
		if (i == JOptionPane.CANCEL_OPTION)
		{
			isSave = false;
		}//End CR1
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
    private void saveWhenUpdate()
    {
    	
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() begin...");
        }
        //add by guoxl on 2009-1-8(�����򣨹��������½������Ӽ��������������Ϣ�����������
       // �Ƿ񱣴���ʾ��������ʾ)
        TechnicsContentJPanel.addFocusLis.initFlag();
        //add by guoxl end
        
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
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Object[] obj =
                    {
                    parentTechnics.getBsoID(),
                    parentProcedure.getBsoID(),
                    cappWrapData};
            QMProcedureInfo returnProce;
          //CCBegin SS1      
            //CCBegin by chudaming 20091119 ���빤��
//            returnProce = (QMProcedureInfo) useServiceMethod(
//                    "StandardCappService", "updateQMProcedure", paraClass,
//                    obj);
          
            
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
          //CCEnd SS1
            //Begin CR2
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }
            //End CR2
            //CCBegin  SS4�����,����ˢ�½���
            drawingLinkPanel.setProcedure(getProcedure());
            //CCEnd  SS4
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
              //CCBegin SS1  
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
              //CCEnd SS1
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
        //���⣨4��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� begin
//        try
//        {
//            setViewMode(VIEW_MODE);
//        }
//        catch (PropertyVetoException ex1)
//        {
//            ex1.printStackTrace();
//        }
      //���⣨4��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� end
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
        storageJButton.setEnabled(flag);
    }


    /**
     * ���ð�ť�Ŀɼ���
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
        storageJButton.setVisible(flag);
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
            //"�豸�б��͹�װ�б����붼���������ݣ�����ִ�������������������ݡ�"
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
     * �Ѹ�����ҵ��������ӵ���Ӧ�Ĺ����б���
     * @param info ������ҵ�������Դ��
     */
        //CCBegin by leixiao 2010-6-30 �򲹶�v4r3_p017_20100617 ����ʶCR5
    public void addObjectToTable(BaseValueInfo[] info)
    {
    	//���⣨3��20080820 �촺Ӣ�޸�
        if (info[0] instanceof QMEquipmentInfo)
        {
            Vector tools = null;//begin CR5
            try
            {//end CR5
            if (equiplinkJPanel != null)
            {
            for (int i = 0; i < info.length; i++)
            {
                equiplinkJPanel.addEquipmentToTable((
                        QMEquipmentInfo) info[i]);
                //begin CR5
                CEquipment cequip = new CEquipment((QMEquipmentInfo)info[i]);
                // �õ�����豸�����Ĺ�װ
                tools = cequip.getTools();
                if(tools != null && tools.size() != 0)
                {
                    // ������װ
                    for(int j = 0;j < tools.size();j++)
                    {
                        // ��������Ǳ�Ҫ��
                        if(((Boolean)((Object[])tools.elementAt(j))[1]).booleanValue() == true)
                        {
                            // �ѹ�װ�����б���
                            if(toollinkJPanel != null)
                                toollinkJPanel.addToolToTable((QMToolInfo)((Object[])tools.elementAt(j))[0]);
                        }

                    }
                }
                //end CR5        
            }
            }
             }//begin CR5
            catch (QMException ex)
            {
                ex.printStackTrace();
            }//end CR5
           
        }
        else if (info[0] instanceof QMToolInfo)
        {
        	Vector equips = null;//begin CR5
        	try
                {//end CR5
        	if (toollinkJPanel != null)
        	{
            for (int i = 0; i < info.length; i++)
            {
                toollinkJPanel.addToolToTable((QMToolInfo) info[i]);
                 //begin CR5
                        CTool ctool = new CTool((QMToolInfo)info[i]);
                        // �õ�����豸�����Ĺ�װ
                        equips = ctool.getEquips();
                        if(equips != null && equips.size() != 0)
                        {
                            // ������װ
                            for(int j = 0;j < equips.size();j++)
                            {
                                // ��������Ǳ�Ҫ��
                                if(((Boolean)((Object[])equips.elementAt(j))[1]).booleanValue() == true)
                                {
                                    // ���豸�����б���
                                	if(equiplinkJPanel != null)
                                		equiplinkJPanel.addEquipmentToTable((QMEquipmentInfo)((Object[])equips.elementAt(j))[0]);

                                }

                            }
                        }
                        //end CR5
            }
          }
          }//begin CR5
              catch (QMException ex)
              {
                  ex.printStackTrace();
              }//end CR5
        
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
   //CCEnd by leixiao 2010-6-30 �򲹶�v4r3_p017_20100617 ����ʶCR5

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
        numDisplayJLabel.setText("");
      //CCBegin by leixiao 2010-3-30  ���ӹ������
        descNumJTextField.setText("");
        descNumDisplayJLabel.setText("");
      //CCEnd by leixiao 2010-3-30  ���ӹ������
        drawingLinkPanel.clear();
        processHoursJPanel.clear();
        //equiplinkJPanel.clear();
        //materiallinkJPanel.clear();
        //toollinkJPanel.clear();
        doclinkJPanel.clear();
      //CCBegin SS1
        //CCBegin by chudaming 20091119 ���빤��
        insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 ���빤��
      //CCEnd SS1
        existProcessType = "";
        d = null;
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

        //����ʵ������������
        //20081212 xucy �޸�
        technicsContentJTextArea.clearPaceAll();
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
            //CCBegin SS7
            partlinkJPanel = new ProcedureUsagePartJPanel(stepType,false);
            //CCEnd SS7
            relationsJPanel.add(partlinkJPanel, "�㲿��");
            //CCBegin SS8
            
            partlinkJPanel.setTechnicsPaceJPanel(this);
            //CCEnd SS8
        }
    }
    /**
     * ����ʵ�����豸����
     * @param stepType String
     * ���⣨3��20080820 �촺Ӣ�޸�
     */
    private synchronized void newEquiplinkJPanel(String stepType)
    {
       
            if (equiplinkJPanel != null)
            {
                relationsJPanel.remove(equiplinkJPanel);
            }
            //CCBegin SS7
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType,false);
            //CCEnd SS7
            
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
     * ���⣨3��20080820 �촺Ӣ�޸�
     */
    private synchronized void newToollinkJPanel(String stepType)
    {
       
            if (toollinkJPanel != null)
            {
                relationsJPanel.remove(toollinkJPanel);
            }
            //CCBegin SS7
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType,false);
            //CCEnd SS7
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
     * ���⣨3��20080820 �촺Ӣ�޸�
     */
    private synchronized void newMateriallinkJPanel(String stepType)
    {
        
            if (materiallinkJPanel != null)
            {
                relationsJPanel.remove(materiallinkJPanel);
            }
            //CCBegin SS7
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType,false);
            //CCEnd SS7
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
     * Ϊ����������������������
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
    
    //CCBegin SS8
    
	public void setTechnicsPaceText(String s) {
		technicsContentJTextArea.setInsertText(s);
		technicsContentJTextArea.insertTexts();
	}
    //CCEnd SS8

}