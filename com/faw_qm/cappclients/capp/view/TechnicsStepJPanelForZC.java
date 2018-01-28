package com.faw_qm.cappclients.capp.view;
/**
 * SS1 ������ 2014-07-04 �޸ķ���ƽ̨����A034-2014-0109
 * SS2 ������ 2014-07-04 �����ע����Ϣ����ʱ��Ϣ��������������
 * SS3 ������ 2014-07-04 �����ע����Ϣ����չ�����ļ����������仯������
 * SS4 ������ 2014-07-07 �޸ķ���ƽ̨����A034-2014-0114
 * SS5 ���ݹ��ܽ�SpecialCharacter������text�ˣ����´��������޸� pante 2014-10-14
 * SS6 �޸���ݴ������򣨲���ʱ�Զ���TS16949���������һ�п��в���������������,ȡ��SS2���޸� pante 2014-10-21
 * SS7 ��ݹ�������ÿ���豸��Ҫ��Ӧ��ʱ��Ϣ pante 2014-10-29
 * SS8 ��������ٴθı䣬���������е�һ�����豸ʱ��ʱ��Ӧ�豸��û��ʱҲҪ���Ϲ�ʱ pante 2015-02-11
 * SS9 ά��������Դ���빤�������Դ��һ�¡� liunan 2015-6-1
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMFawTechnicsMasterInfo;
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
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsLogic;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelForZC;
import com.faw_qm.cappclients.beans.cappexattrpanel.GroupPanelForZC;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.TermTextField;
import com.faw_qm.cappclients.capp.view.TechnicsStepJPanel.MouseListener;
import com.faw_qm.cappclients.capp.view.TechnicsStepJPanel.WorkThread;
import com.faw_qm.cappclients.resource.view.ResourcePanel;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
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
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.client.model.CTool;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolInfo;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * ��ݹ������������ù����¼��� ������
 * @author a
 *
 */
public class TechnicsStepJPanelForZC extends TechnicsStepJPanel{
	 /**����ά�����*/
//  private JPanel stepJPanel = new JPanel();
    /**��ť���*/
    private JPanel buttonJPanel = new JPanel();


    /**���*/
    private JLabel numJLabel = new JLabel();

    //add by wangh on 20070131(���빤���ű�ǩ���ı������һ��JTabbedPane����3����JPanel��);
    private JLabel descStepNumberJLabel = new JLabel();
    private JLabel descNumDisplayJLabel = new JLabel();
    private CappTextField descNumJTextField;
    private JTabbedPane jTabbedPanel = new JTabbedPane();
    private JPanel extendJPanel = new JPanel();
    private JPanel extendJPanel2 = new JPanel();
    private JPanel extendJPanel3 = new JPanel();
    private JPanel extendJPanel4 = new JPanel();


    private CappTextField numJTextField;
    private JLabel numDisplayJLabel = new JLabel();


    /**��������*/
    private JLabel relationTechJLabel = new JLabel();
    private JTextField relationTechJTextField = new JTextField();
    private JButton searchTechJButton = new JButton();
    private JLabel relationTechDisJLabel = new JLabel();
    private JPanel rbJPanel = new JPanel();
    private JButton deleteTechJButton = new JButton();


    /**����*/
    private JLabel nameJLabel = new JLabel();
    private TermTextField nameJTextField;
    private JLabel nameDisplayJLabel = new JLabel();


    /**��������*/
    private JLabel stepTypeJLabel = new JLabel();
    private JLabel stepTypeDisJLabel = new JLabel();


    /**�������*/
    private JLabel stepClassifiJLabel = new JLabel();
    private JLabel stepClassiDisJLabel = new JLabel();


    /**�ӹ�����*/
    private JLabel processTypeJLabel = new JLabel();
    private JLabel processTypeDisJLabel = new JLabel();


    /**����*/
    private JLabel workshopJLabel = new JLabel();
    private JLabel workshopDisJLabel = new JLabel();


    /**��ͼά��*/
    private ProcedureUsageDrawingPanel drawingLinkPanel;


    /**��������*/
    private JLabel contentJLabel = new JLabel();
    private SpeCharaterTextPanel contentPanel;


    /**�������*/
    private JTabbedPane relationsJPanel = new JTabbedPane();


    /**��ť��*/
    private JButton paraJButton = new JButton();
    private JButton saveJButton = new JButton();
    private JButton cancelJButton = new JButton();
    private JButton quitJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();


//    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**��ʱͳ�����*/
    private ProcessHoursJPanel processHoursJPanel;


    /**������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;


    /**������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;


    /**������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;


    /**����ģʽ--�鿴*/
    private int mode = VIEW_MODE;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**����*/
    private QMProcedureInfo procedureInfo;
    private GridBagLayout gridBagLayout4 = new GridBagLayout();


    /**������*/
    private JFrame parentJFrame;


    /**��ѡ�еĽڵ�*/
    private CappTreeNode selectedNode;


    /**�豸������ά�����*/
    private ProcedureUsageEquipmentJPanel equiplinkJPanel;


    /**��װ������ά�����*/
    private ProcedureUsageToolJPanel toollinkJPanel ;


    /**���Ϲ�����ά�����*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**�ĵ�������ά�����*/
    private ProcedureUsageDocJPanel doclinkJPanel = new ProcedureUsageDocJPanel();


    /**��ⰴť*/
    private JButton storageJButton = new JButton();

    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    private JScrollPane scrollpane = new JScrollPane();


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JPanel jPanel1 = new JPanel();


    /**��չ���ݽ������*/
    private TParamJDialog d = null;
    private EquipmentToolMaintainJDialog f1 = null;


    /**
     * ���տ�
     */
    private QMTechnicsIfc parentTechnics;


    /**c�д�Ź����µ�ֱ�ӹ���*/
    private Collection c = null;


    /**��¼�Ƿ��һ�ν���˽���*/
    private boolean firstInFlag = true;


    /**
     *��������� ��������\���������µĴ�����
     * ��:������������  ֵ:�������������
     */
    private Hashtable stepTypetable = null;
    private CodingIfc stepType;
    private JSplitPane splitJPanel = new JSplitPane();
    private JPanel upJPanel = new JPanel();
    private JPanel downJPanel = new JPanel();

    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private GridBagLayout gridBagLayout7 = new GridBagLayout();

    //add by wangh on 20070207(�����������,����FMEA�Ϳ��Ƽƻ�Panel,����ÿ��Panel����һ��Hashtable)
    private Hashtable processFlowTable = new Hashtable();
    private Hashtable femaTable = new Hashtable();
    private Hashtable processControlTable = new Hashtable();
    private CappExAttrPanelForZC processFlowJPanel;
    private CappExAttrPanelForZC femaJPanel;
    private CappExAttrPanelForZC processControlJPanel;

    //add by wangh on 20070326(�Ƿ���ʾTS16949�Ĺ�����߹�����Ϣ��)
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    

    //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
    private JComboBox processTypeComboBox = new JComboBox();
    private JComboBox stepTypeComboBox = new JComboBox();
    
    
    
    private Vector eqVec=new Vector();
    private Vector toolVec=new Vector();
    private Vector materiaVec=new Vector();
    
    private Vector eqDeleVec=new Vector();
    private Vector toolDeleVec=new Vector();
    private Vector materiaDeleVec=new Vector();
    
    CappAssociationsLogic taskLogic ;
    
    
    
    public TechnicsStepJPanelForZC(){}
    
    /**
     * ���캯��
     * @param parent ���ñ���ĸ�����
     */
    public TechnicsStepJPanelForZC(JFrame parent)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 �ĳ�30�����֣�60���ַ���
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415        
            contentPanel = new SpeCharaterTextPanel(parent,true);//CR7
            //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            initSpeCharaterTextPanel();
            contentPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog",
                    0,
                    18));
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ���캯��
     * @param parent ���ñ���ĸ�����
     * @param parentnode ���ڵ�
     */
    public TechnicsStepJPanelForZC(JFrame parent, BaseValueIfc technics)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 �ĳ�30�����֣�60���ַ���
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            contentPanel = new SpeCharaterTextPanel(parent,true);
            //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            initSpeCharaterTextPanel();
            if (technics instanceof QMTechnicsIfc)
            {
                parentTechnics = (QMTechnicsIfc) technics;
            }
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
        String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.PROCESSTYPE, null);
        String title3 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.STEPCLASSIFI, null);
        //���⣨3�� 20080704 �촺Ӣ�޸� �޸�ԭ�򣺹�������������Ԥ��������
        //equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepTypeContent);
        //toollinkJPanel = new ProcedureUsageToolJPanel(stepTypeContent);
        //materiallinkJPanel = new  ProcedureUsageMaterialJPanel(stepTypeContent);
        
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame); 
        
        f1 = new EquipmentToolMaintainJDialog(parentJFrame);
        setLayout(gridBagLayout4);
        setSize(550, 450);
        upJPanel.setLayout(gridBagLayout3);
        buttonJPanel.setLayout(gridBagLayout2);
        numJLabel.setMaximumSize(new Dimension(41, 22));
        numJLabel.setMinimumSize(new Dimension(41, 22));
        numJLabel.setPreferredSize(new Dimension(41, 22));
        numJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numJLabel.setText("*�����");

        //add by wangh on 20070131
        //Ѧ�� �޸�
//        descStepNumberJLabel.setMaximumSize(new Dimension(48, 22));
//        descStepNumberJLabel.setMinimumSize(new Dimension(48, 22));
//        descStepNumberJLabel.setPreferredSize(new Dimension(48, 22));
        descStepNumberJLabel.setMaximumSize(new Dimension(53, 22));
        descStepNumberJLabel.setMinimumSize(new Dimension(53, 22));
        descStepNumberJLabel.setPreferredSize(new Dimension(53, 22));
        //Ѧ�� �޸Ľ���
        descStepNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descStepNumberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//      Ѧ���޸� 20080219 �޸�ԭ���ڹ��չ�����ڹ����´�������,������Ϊ�������ֶ�ǰ��*
        //descStepNumberJLabel.setText("������");
        descStepNumberJLabel.setText("*������");
        //Ѧ���޸Ľ���
        extendJPanel.setLayout(gridBagLayout3);
        extendJPanel2.setLayout(gridBagLayout3);
        extendJPanel3.setLayout(gridBagLayout3);
        extendJPanel4.setLayout(gridBagLayout3);
        extendJPanel.setBorder(BorderFactory.createEtchedBorder());
        extendJPanel2.setBorder(BorderFactory.createEtchedBorder());
        extendJPanel3.setBorder(BorderFactory.createEtchedBorder());
        extendJPanel4.setBorder(BorderFactory.createEtchedBorder());
        jTabbedPanel.setMaximumSize(new Dimension(405, 32767));
        jTabbedPanel.setMinimumSize(new Dimension(405, 78));
        jTabbedPanel.setPreferredSize(new Dimension(405, 536));

        String procedureNumDisp = QMMessage.
                                  getLocalizedMessage(RESOURCE,
                "procedureNum", null);
        String procedureNumDisp1 = QMMessage.
                                   getLocalizedMessage(RESOURCE,
                "procedureNum1", null);

        numJTextField = new CappTextField(parentJFrame, procedureNumDisp, 5, false);
        //add by wangh on 20070201
        descNumJTextField = new CappTextField(parentJFrame, procedureNumDisp1,
                                              10, false);

        relationTechJLabel.setMaximumSize(new Dimension(48, 22));
        relationTechJLabel.setMinimumSize(new Dimension(48, 22));
        relationTechJLabel.setPreferredSize(new Dimension(48, 22));
        relationTechJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        relationTechJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        relationTechJLabel.setText("��������");
        searchTechJButton.setMaximumSize(new Dimension(89, 23));
        searchTechJButton.setMinimumSize(new Dimension(89, 23));
        searchTechJButton.setPreferredSize(new Dimension(89, 23));
        searchTechJButton.setToolTipText("");
        searchTechJButton.setMnemonic('B');
        searchTechJButton.setText("���(B)");
        searchTechJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                searchTechJButton_actionPerformed(e);
            }
        });
        deleteTechJButton.setMaximumSize(new Dimension(75, 23));
        deleteTechJButton.setMinimumSize(new Dimension(75, 23));
        deleteTechJButton.setPreferredSize(new Dimension(75, 23));
        deleteTechJButton.setMnemonic('R');
        deleteTechJButton.setText("ɾ��(R)");
        deleteTechJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteTechJButton_actionPerformed(e);
            }
        });

        nameJLabel.setMaximumSize(new Dimension(53, 22));
        nameJLabel.setMinimumSize(new Dimension(53, 22));
        nameJLabel.setPreferredSize(new Dimension(53, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setText("*��������");
        stepTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stepTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        stepTypeJLabel.setText("*��������");
        stepClassifiJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stepClassifiJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        stepClassifiJLabel.setText("*�������");
        processTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        processTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        processTypeJLabel.setText("*�ӹ�����");
        workshopJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workshopJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        workshopJLabel.setText("*����");
        contentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        contentJLabel.setText("��������");

        paraJButton.setMaximumSize(new Dimension(114, 23));
        paraJButton.setMinimumSize(new Dimension(114, 23));
        paraJButton.setPreferredSize(new Dimension(114, 23));
        paraJButton.setMnemonic('E');
        paraJButton.setText("������Ϣ. . .");
        paraJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                paraJButton_actionPerformed(e);
            }
        });
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setActionCommand("SAVE");
        saveJButton.setMnemonic('S');
        saveJButton.setText("����");
        saveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	
                saveJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
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
        quitJButton.setText("�˳�");
        quitJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
        processHoursJPanel = new ProcessHoursJPanel(parentJFrame,true);
        storageJButton.setMaximumSize(new Dimension(97, 23));
        storageJButton.setMinimumSize(new Dimension(97, 23));
        storageJButton.setPreferredSize(new Dimension(97, 23));
        storageJButton.setMnemonic('T');
        storageJButton.setText("���");
        storageJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                storageJButton_actionPerformed(e);
            }
        });
        relationTechJTextField.setEditable(false);
        relationTechJTextField.addCaretListener(new javax.swing.event.
                                                CaretListener()
        {
            public void caretUpdate(CaretEvent e)
            {
                relationTechJTextField_caretUpdate(e);
            }
        });
       
        //Begin CR2
        MouseListener mouseListener= new MouseListener();
        relationTechJTextField.addMouseListener(mouseListener);
        //End CR2
        numDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        numDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        numDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        nameDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        nameDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        nameDisplayJLabel.setPreferredSize(new Dimension(4, 22));

        //add by wangh on 20070201
        descNumDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        descNumDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        descNumDisplayJLabel.setPreferredSize(new Dimension(4, 22));

        stepTypeDisJLabel.setMaximumSize(new Dimension(4, 22));
        stepTypeDisJLabel.setMinimumSize(new Dimension(4, 22));
        stepTypeDisJLabel.setOpaque(false);
        stepTypeDisJLabel.setPreferredSize(new Dimension(4, 22));
        processTypeDisJLabel.setMaximumSize(new Dimension(4, 22));
        processTypeDisJLabel.setMinimumSize(new Dimension(4, 22));
        processTypeDisJLabel.setPreferredSize(new Dimension(4, 22));
        relationTechDisJLabel.setMaximumSize(new Dimension(10, 22));
        relationTechDisJLabel.setMinimumSize(new Dimension(10, 22));
        relationTechDisJLabel.setPreferredSize(new Dimension(10, 22));
        //Begin CR2
    	relationTechDisJLabel.addMouseListener(mouseListener);
    	//End CR2
        stepClassiDisJLabel.setMaximumSize(new Dimension(4, 22));
        stepClassiDisJLabel.setMinimumSize(new Dimension(4, 22));
        stepClassiDisJLabel.setPreferredSize(new Dimension(4, 22));
        workshopDisJLabel.setMaximumSize(new Dimension(4, 22));
        workshopDisJLabel.setMinimumSize(new Dimension(4, 22));
        workshopDisJLabel.setPreferredSize(new Dimension(4, 22));
        rbJPanel.setLayout(gridBagLayout5);
        contentPanel.setMaximumSize(new Dimension(32767, 80));
        contentPanel.setMinimumSize(new Dimension(10, 10));
        contentPanel.setPreferredSize(new Dimension(100, 50));
        splitJPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitJPanel.setBorder(null);
        splitJPanel.setMinimumSize(new Dimension(337, 357));
        splitJPanel.setOpaque(true);
        splitJPanel.setPreferredSize(new Dimension(337, 500));
        splitJPanel.setContinuousLayout(true);
        splitJPanel.setDividerSize(5);
        splitJPanel.setOneTouchExpandable(true);
        splitJPanel.setResizeWeight(1.0);
        upJPanel.setMinimumSize(new Dimension(337, 200));
        upJPanel.setPreferredSize(new Dimension(337, 300));
        downJPanel.setLayout(gridBagLayout7);
        downJPanel.setMinimumSize(new Dimension(337, 100));
        downJPanel.setPreferredSize(new Dimension(337, 120));
        upJPanel.setLayout(gridBagLayout6);
        numJTextField.setMaximumSize(new Dimension(6, 24));
        nameJTextField.setMaximumSize(new Dimension(6, 24));

        //add by wangh on 20070201
        descNumJTextField.setMaximumSize(new Dimension(6, 24));
        descNumJTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                descNumJTextField_actionPerformed(e);
            }
        });
//    descNumJTextField.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        descNumJTextField_actionPerformed(e);
//      }
//    });

        scrollpane.setBorder(null);
        extendJPanel.add(scrollpane,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
        splitJPanel.add(upJPanel, JSplitPane.TOP);

        splitJPanel.add(downJPanel, JSplitPane.BOTTOM);

        rbJPanel.add(searchTechJButton,
                     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.NONE,
                                            new Insets(0, 8, 0, 0), 0, 0));
        rbJPanel.add(deleteTechJButton,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.NONE,
                                            new Insets(0, 8, 0, 0), 0, 0));
        rbJPanel.add(relationTechJTextField,
                     new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 8, 0, 0), 0, 0));
        rbJPanel.add(relationTechDisJLabel,
                     new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 8, 0, 0), 0, 0));

        upJPanel.add(nameJTextField,
                     new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 8, 0, 7), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(nameDisplayJLabel,
                     new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));

        //���ڸ��ºͲ鿴ģʽ
        upJPanel.add(stepTypeDisJLabel,
                     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(processTypeDisJLabel,
                     new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));

        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        ResourcePanel  rsPanel = new ResourcePanel();
        Collection processTypeCol = rsPanel.getCoding("QMProcedure", "processType", "SortType");
        if (processTypeCol == null || processTypeCol.size() == 0)
        {
            processTypeComboBox.addItem("");
        }
        else
        {
            for (Iterator iter = processTypeCol.iterator(); iter.hasNext(); )
            {
                CodingIfc code = (CodingIfc) iter.next();
                processTypeComboBox.addItem(code);
            }
        }
        processTypeComboBox.setMaximumSize(new Dimension(93, 22));
        processTypeComboBox.setMinimumSize(new Dimension(93, 22));
        processTypeComboBox.setPreferredSize(new Dimension(93, 22));
        upJPanel.add(processTypeComboBox,
                new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(2, 8, 0, 0), 0, 0));
        upJPanel.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(7, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumJTextField,
                     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 8, 0, 0), 0, 0));

        //���ڲ鿴ģʽ
        upJPanel.add(numDisplayJLabel,
                     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(10, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumDisplayJLabel,
                     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(10, 8, 0, 0), 0, 0));

        //���ڲ鿴ģʽ

        upJPanel.add(relationTechJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 20, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(stepClassiDisJLabel,
                     new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));
        //20080811 XUCY
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        Collection stepCol = rsPanel.getCoding("QMProcedure", "stepClassification", "SortType");
        if (stepCol == null || stepCol.size() == 0)
        {
        	stepTypeComboBox.addItem("");
        }
        else
        {
            for (Iterator iter = stepCol.iterator(); iter.hasNext(); )
            {
                CodingIfc code = (CodingIfc) iter.next();
                stepTypeComboBox.addItem(code);
            }
        }
        stepTypeComboBox.setMaximumSize(new Dimension(80, 22));
        stepTypeComboBox.setMinimumSize(new Dimension(80, 22));
        stepTypeComboBox.setPreferredSize(new Dimension(80, 22));
        upJPanel.add(stepTypeComboBox,
                new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(2, 8, 0, 8), 0, 0));
        upJPanel.add(stepClassifiJLabel,
                     new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 0, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(workshopDisJLabel,
                     new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));

        upJPanel.add(workshopJLabel,
                     new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 0, 0, 0), 0, 0));
        upJPanel.add(processTypeJLabel,
                     new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(stepTypeJLabel,
                     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(nameJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(numJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(7, 21, 0, 0), 0, 0));

        //add by wangh on 20070131
        upJPanel.add(descStepNumberJLabel,
                     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 21, 0, 0), 0, 0));

        upJPanel.add(rbJPanel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(10, 0, 0, 7), 0, 0));

        upJPanel.add(contentJLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(7, 0, 0, 0), 0, 0));
        downJPanel.add(relationsJPanel,
                       new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(5, 9, 5, 7), 0, 0));

        upJPanel.add(processHoursJPanel,
                     new GridBagConstraints(0, 7, 4, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 13, 0, 7), 0, 0));
        upJPanel.add(contentPanel,
                     new GridBagConstraints(1, 6, 3, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(5, 8, 5, 7), 0, 0));

        add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(10, 5, 10, 5), 0, 0));
        buttonJPanel.add(paraJButton,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(quitJButton,
                         new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton,
                         new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(storageJButton,
                         new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(jPanel1, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        //add by wangh on 20070202
        this.add(jTabbedPanel,
                 new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                        , GridBagConstraints.CENTER,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 2, 0, 0), 0, 0));
        
//        CCBegin SS6
        //CCBegin SS2
//        jTabbedPanel.addChangeListener(new ChangeListener() {
//            
//			public void stateChanged(ChangeEvent cevent) {
//				 
//				if (jTabbedPanel.getSelectedIndex() == 1||jTabbedPanel.getSelectedIndex() == 2
//						||jTabbedPanel.getSelectedIndex() == 3) {
//
//					int rowCount=processFlowJPanel.groupPanel.multiList.getRowCount();
//					if(rowCount==0)
//					   processFlowJPanel.groupPanel.addNewRow();
//					
//					processFlowJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//					
//					if(mode==VIEW_MODE){
//					    processFlowJPanel.groupPanel.multiList.addTextCell(0, 6, String.valueOf(getProcedure().getMachineHour()));
//					    processFlowJPanel.groupPanel.multiList.addTextCell(0, 7, String.valueOf(getProcedure().getStepHour()));
//					}else if(mode==CREATE_MODE||mode==UPDATE_MODE){
//						
//						//CCBegin SS4
						    /*String machineHourValue=processHoursJPanel.getMachineHourJTextField().getText();
						    String aidTimeString=processHoursJPanel.getAidTimeJTextField().getText();
						    
						   double machineHourF = 0;
						   double aidTimeF = 0;
						    
						    if(machineHourValue!=null&&!machineHourValue.equals("")){
						    	machineHourF=Double.valueOf(machineHourValue).doubleValue();
					    	   processFlowJPanel.groupPanel.multiList.addTextCell(0, 6, String.valueOf(processHoursJPanel.getMachineHourJTextField().getText()));
						    
						    }else{
						    	processFlowJPanel.groupPanel.multiList.addTextCell(0, 6, String.valueOf(0.0));
						    }
						    
						    if(aidTimeString!=null&&!aidTimeString.equals("")){
						    	
						    	aidTimeF=Double.valueOf(aidTimeString).doubleValue();
						    	
						    	
						    }
						    
						     double partHour = Math.round((machineHourF + aidTimeF) * 10000000000d) /
	                          10000000000d;
						    processFlowJPanel.groupPanel.multiList.addTextCell(0, 7, String.valueOf(partHour));
						    //CCEnd SS4*/
//						
//					}
//						
//					rowCount=femaJPanel.groupPanel.multiList.getRowCount();
//					if(rowCount==0)
//						femaJPanel.groupPanel.addNewRow();
//					
//					femaJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//					
//					
//					rowCount=processControlJPanel.groupPanel.multiList.getRowCount();
//					if(rowCount==0)
//						processControlJPanel.groupPanel.addNewRow();
//					
//					processControlJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//					
//					
//				}
//			}
//
//		});
        
        //CCEnd SS2
//        CCEnd SS6
        
        
//20080820 xucy 
//        //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
//        equiplinkJPanel.addListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                toollinkJPanel.addRelationTools(e);
//            }
//        });
        //�Ӵ˼�����ԭ�򣺵���װ���������빤װʱ���豸�������Ҫ�����빤װ��Ҫ�������豸
//        toollinkJPanel.addListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                equiplinkJPanel.addRelationEquipments(e);
//            }
//        });
        //add by wangh on 20070201(��������Ϣ,��������,����FMEA�Ϳ��Ƽƻ�Panel���뵽jTabbedPanel��)
        jTabbedPanel.add(extendJPanel, "������Ϣ");
        if (ts16949)
        {
            jTabbedPanel.add(extendJPanel2, "��������");
            jTabbedPanel.add(extendJPanel3, "����FMEA");
            jTabbedPanel.add(extendJPanel4, "���Ƽƻ�");
        }
//20080820
//        equiplinkJPanel.setToolPanel(toollinkJPanel);
//        toollinkJPanel.setEquipmentPanel(equiplinkJPanel);
        //20080820
        //relationsJPanel.add(equiplinkJPanel, "�豸");
//        relationsJPanel.add(toollinkJPanel,
//                            "��װ");
//        relationsJPanel.add(materiallinkJPanel,
//                            "����");
//        relationsJPanel.add(doclinkJPanel,
//                            "�ĵ�");
//        relationsJPanel.add(drawingLinkPanel, "��ͼ");
//        upJPanel.add(stepClassifiSortingSelectedPanel,
//                     new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
//                                            , GridBagConstraints.WEST,
//                                            GridBagConstraints.HORIZONTAL,
//                                            new Insets(7, 0, 2, 8), 0, 0));
//        upJPanel.add(processTypeSortingSelectedPanel,
//                     new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
//                                            , GridBagConstraints.WEST,
//                                            GridBagConstraints.HORIZONTAL,
//                                            new Insets(2, 0, 2, 0), 0, 0));

        scrollpane.getViewport().add(splitJPanel, null);

        localize();
        initStepTypeTable();
        splitJPanel.setDividerLocation(300);
        
        
        taskLogic = new CappAssociationsLogic();

    }


    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
        try
        {
            //JLabel
            numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "stepNumberJLabel", null));

            //add by wangh on 20070131
            descStepNumberJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "stepNumberJLabel2", null));

            relationTechJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "relationTechnicsJLabel", null));
            nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "stepNameJLabel", null));
            stepTypeJLabel.setText("*��������");
            stepClassifiJLabel.setText("*�������");
            processTypeJLabel.setText("*�ӹ�����");
            workshopJLabel.setText("*����");
            //Ѧ���޸� 20080219 �޸�ԭ���ڹ��չ�����ڹ����´�������,������Ϊ�������ֶ�ǰ��*
            descStepNumberJLabel.setText("*������");
            //Ѧ���޸Ľ���
//            contentJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "mtechContentJLabel", null));
            //JButton
            paraJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "ParaJButton", null));
            saveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "QuitJButton", null));
            storageJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "storageJButton", null));
            searchTechJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "browseJButton", null));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(parentJFrame, message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }  
    }
/**
 * �������ڲ���
 * @author new
 * CR2 ������(guoxl) on 2009-1-15(������������ı���Ťת���˹��յĲ鿴����) 
 * 
 */
	class MouseListener extends MouseAdapter {

		TechnicsRegulationsMainJFrame tecframe;

		// ������
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof JTextField) {
				relationTechJTextField.setForeground(Color.blue);
				relationTechJTextField.setToolTipText("�����ɲ鿴�˹���");
				// ���Ϊ����
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else if (e.getSource() instanceof JLabel) {
				relationTechDisJLabel.setForeground(Color.blue);
				relationTechDisJLabel.setToolTipText("�����ɲ鿴�˹���");
				// ���Ϊ����
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

		}

		// ����뿪
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof JTextField) {
				relationTechJTextField.setForeground(Color.black);
				setCursor(Cursor.getDefaultCursor());
			} else if (e.getSource() instanceof JLabel) {
				relationTechDisJLabel.setForeground(Color.black);
				setCursor(Cursor.getDefaultCursor());
			}
		}

		// ����ͷ�
		public void mouseReleased(MouseEvent e) {
			// ��ù�������bsoid
			String RelationCardBsoID = getProcedure().getRelationCardBsoID();

			Class[] c = { String.class };
			Object[] obj = { RelationCardBsoID };

			try {
				if (null != RelationCardBsoID
						&& !(RelationCardBsoID.equals(""))) {
					BaseValueIfc baseInfo = (BaseValueIfc) useServiceMethod(
							"PersistService", "refreshInfo", c, obj);

					if (tecframe == null) {

						tecframe = new TechnicsRegulationsMainJFrame(true);

					}

					Object[] obj1 = { baseInfo };

					// ���������չҵ���һ�����չ�����������,����ʾ�˹��յĲ鿴����.
					tecframe.addTechnics(obj1);
					tecframe.setVisible(true);

				} else {
					return;
				}

			} catch (Exception ee) {

				ee.printStackTrace();

			}
		}

	}
    /**
	 * �����ڹ�������ѡ��Ľڵ�
	 * 
	 * @param parentnode
	 */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setTechnics();
    }


    /**
	 * ��ȡ��ǰѡ��Ĺ���Ĺ��տ�ͷ
	 * 
	 * @return
	 */
    private void setTechnics()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setTechnics() begin...");
        }
//        CappTreeNode parentNode;
        if (selectedNode.getObject() instanceof StepTreeObject)
        {
            parentTechnics = (QMTechnicsInfo) selectedNode.getP().getObject().
                             getObject();
        }
        else
        {
            parentTechnics = (QMTechnicsInfo) selectedNode.getObject().
                             getObject();

            /*
             if(CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)parentTechnics)
             &&!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)parentTechnics))
              {
                try {
             parentTechnics=(QMTechnicsInfo)CheckInOutCappTaskLogic.getWorkingCopy(
                      (WorkableIfc)parentTechnics);
                }
                catch (QMRemoteException ex) {
                  ex.printStackTrace();
             String title = QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                  JOptionPane.showMessageDialog(getParentJFrame(),
                                                ex.getClientMessage(),title,
             JOptionPane.INFORMATION_MESSAGE);
                }
              }*/
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setTechnics() end...return:void ");
        }
    }


    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setCreateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setCreateMode() begin...");
        }
        clear();
        changeWorkShopSortingSelectedPanel();
        numJTextField.setVisible(true);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumDisplayJLabel.setVisible(false);

        numDisplayJLabel.setVisible(false);
        relationTechJTextField.setVisible(true);
        relationTechDisJLabel.setVisible(false);
        searchTechJButton.setVisible(true);
        nameJTextField.setVisible(true);
        nameDisplayJLabel.setVisible(false);
        stepTypeDisJLabel.setText(stepType.getCodeContent());
        stepClassiDisJLabel.setVisible(false);
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        processTypeComboBox.setVisible(true);
        stepTypeComboBox.setVisible(true);
        processTypeDisJLabel.setVisible(false);
        //workshopSortingSelectedPanel.setVisible(true);
        // workshopDisJLabel.setVisible(false);
        
        contentPanel.setEditable(true);
        drawingLinkPanel.setModel(2); //EDIT

        doclinkJPanel.setMode("Edit");
        //20080820
        //equiplinkJPanel.setMode("Edit");
        //toollinkJPanel.setMode("Edit");
        //materiallinkJPanel.setMode("Edit");
        f1.setEditMode();
        processHoursJPanel.setEnabled(true);
        processHoursJPanel.setCreateMode();
        //{{��ʼ�������
        QMFawTechnicsInfo pTechnics = (QMFawTechnicsInfo) selectedNode.
                                      getObject().
                                      getObject();
        Class[] c =
                {String.class, String.class};
        Object[] objs =
                {pTechnics.getBsoID(), pTechnics.getBsoID()};
        try
        {
            Integer maxNum = (Integer) useServiceMethod(
                    "StandardCappService", "getMaxNumber", c, objs);
            int number = maxNum.intValue();
            if (number == 0)
            {
                numJTextField.setText(String.valueOf(getStepInitNumber()));
                //add by wangh on 20070201
                descNumJTextField.setText(String.valueOf(getStepInitNumber()));
            }
            else
            {
                if (number >= 99999)
                {
                    return;
                }
                numJTextField.setText(String.valueOf(number + getStepLong()));
                //add by wangh on 20070201
                descNumJTextField.setText(String.valueOf(number + getStepLong()));

            }
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        //����Ĭ��ֵ
        String processType = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.PROCESSTYPE, null);
        //20080811 xucy
//        processTypeSortingSelectedPanel.setDefaultCoding(processType,
//                stepType.getCodeContent());
        String stepClassi = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.STEPCLASSIFI, null);
//        stepClassifiSortingSelectedPanel.setDefaultCoding(stepClassi,
//                stepType.getCodeContent());
        String workshop = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.WORKSHOP, null);
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setDefaultCoding(workshop,
                    stepType.getCodeContent());
        }
        setButtonVisible(true);
        // paraJButton.setVisible(false);
        refreshObject();
        repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setCreateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ����ģʽ����������������ʾ�ڽ�����
     */
    private void setUpdateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setUpdateMode() begin...");
        }
        clear();
        changeWorkShopSortingSelectedPanel();
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        //������
        numJTextField.setVisible(true);
        numJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
        numDisplayJLabel.setVisible(false);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(String.valueOf(getProcedure().
                                                 getDescStepNumber()));
        descNumDisplayJLabel.setVisible(false);

        //��������
        //���������û�й�������������տ�ά�������򲻿�ά��
        String technicsID = parentTechnics.getBsoID();
        try
        {
            c = CappTreeHelper.browseProcedures(technicsID,
                                                getProcedure().getBsoID());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        //String relationCardBsoID = getProcedure().getRelationCardBsoID();
        relatedTechnicsID = getProcedure().getRelationCardBsoID();
        if (c != null && c.size() != 0)
        {
            relationTechJTextField.setVisible(false);
            relationTechDisJLabel.setVisible(true);
            searchTechJButton.setVisible(false);
            deleteTechJButton.setVisible(false);
        }
        else
        {
            relationTechJTextField.setVisible(true);
            relationTechDisJLabel.setVisible(false);
            searchTechJButton.setVisible(true);
            deleteTechJButton.setVisible(true);
            if (relatedTechnicsID != null && !relatedTechnicsID.equals(""))
            {
                Class[] paraClass1 =
                        {String.class};
                Object[] objs1 =
                        {relatedTechnicsID};
                String displayString = "";
                try
                {
                    QMFawTechnicsInfo relationTechnics = (QMFawTechnicsInfo)
                            useServiceMethod(
                            "PersistService", "refreshInfo", paraClass1, objs1);
                    displayString = getIdentity(relationTechnics);
                    relationTechJTextField.setText(displayString);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(), title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

            }
        }

        //��������
        nameJTextField.setVisible(true);
        String name = getProcedure().getStepName();
        //if(name != null && !name.equals(""))
        //  nameJTextField.resumeDataDisplay(name);
        nameJTextField.setText(name);
        nameDisplayJLabel.setVisible(false);
        this.setComboBox(stepTypeComboBox, getProcedure().getStepClassification());     
        stepClassiDisJLabel.setVisible(false);
        //�ӹ�����
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
        processTypeComboBox.setVisible(true);
        this.stepTypeComboBox.setVisible(true);
        ResourcePanel  rsPanel = new ResourcePanel();
        rsPanel.setComboBox(processTypeComboBox,getProcedure().getProcessType());
        processTypeDisJLabel.setVisible(false);
        //����
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setCoding(getProcedure().
                    getWorkShop());
            workshopDisJLabel.setVisible(false);
        }
        else
        {
        	try
        	{
            workshopDisJLabel.setVisible(true);
          //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
            String workshop="";
            if(getProcedure().getWorkShop() instanceof CodingClassificationIfc){
            	workshop=((CodingClassificationIfc)getProcedure().getWorkShop()).getCodeSort();
            }
            else{
            	workshop=((CodingIfc)getProcedure().getWorkShop()).getCodeContent();
            }
            workshopDisJLabel.setText(workshop);
          //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        }
       
        //Ѧ�� �޸� 20080428 �����Ż����¹��գ�����ٶ�
        drawingLinkPanel.setProcedure(getProcedure());
//        DrawThread dt = new DrawThread();
//        dt.start();
        //Ѧ�� �޸Ľ��� 20080428

        //��������
        contentPanel.setEditable(true);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.clearAll();
            contentPanel.resumeData(v);
        }
        //��ʱ
        processHoursJPanel.setProcedure(getProcedure());
      
        //20080820 xucy
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
            equiplinkJPanel.setProcedure(getProcedure());
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        toollinkJPanel.setProcedure(getProcedure());
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setProcedure(getProcedure());
        }
        //����
        //20081120 �촺Ӣ�޸�   ����״̬���ĵ�Ӧ�ÿɱ༭
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        drawingLinkPanel.setModel(2); //EDIT
        relationsJPanel.add(drawingLinkPanel, "��ͼ");
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());

        }
        //�����Ƿ���й������գ�ȷ��processHoursJPanel�͸���������״̬
        setRelatedEff();
        //modify by wangh on 20070615(ȥ�����й�������ʱ�ĵ����ֶ��ఴŦ)
        //doclinkJPanel.setMode("Edit");
        f1.setEditMode();
        //add by wangh on 20070310
        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());

        setButtonVisible(true);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setUpdateMode() end...return is void");
        }
        
        System.out.println("aaaaaaaaaaaaa");//anan
      //add by guoxl on 2009-1-7(�����½�����Ӽ��������������Ϣ�ı�������Ƿ񱣴���ʾ��������ʾ)
        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
        //add by guoxl end
    }


    /**
     * ���ý���Ϊ�鿴ģʽ����������������ʾ�ڽ�����
     * ���⣨8��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
     *                              ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
     */
    private void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() begin...");
        }
        clear();

        stepTypeDisJLabel.setVisible(true);
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        //�����
        numJTextField.setVisible(false);
        numDisplayJLabel.setVisible(true);
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        //add by wangh on 20070201
        descNumJTextField.setVisible(false);
        descNumDisplayJLabel.setVisible(true);
        descNumDisplayJLabel.setText(String.valueOf(getProcedure().
                getDescStepNumber()));

        relationTechJTextField.setVisible(false);
        relationTechDisJLabel.setVisible(true);
        //��������
        //String relationCardBsoID = getProcedure().getRelationCardBsoID();
        relatedTechnicsID = getProcedure().getRelationCardBsoID();
        if (relatedTechnicsID != null && !relatedTechnicsID.equals(""))
        {
            Class[] paraClass1 =
                    {String.class};
            Object[] objs1 =
                    {relatedTechnicsID};
            String displayString = "";
            try
            {
                QMFawTechnicsInfo relationTechnics = (QMFawTechnicsInfo)
                        useServiceMethod(
                        "PersistService", "refreshInfo", paraClass1, objs1);
                displayString = getIdentity(relationTechnics);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            relationTechDisJLabel.setText(displayString);
        }
        else
        {
            relationTechDisJLabel.setText("");
        }

        searchTechJButton.setVisible(false);
        deleteTechJButton.setVisible(false);
        //��������
        nameJTextField.setVisible(false);
        nameDisplayJLabel.setVisible(true);
        String name = getProcedure().getStepName();
        nameDisplayJLabel.setText(name);
        stepClassiDisJLabel.setVisible(true);
        stepClassiDisJLabel.setText(
                getProcedure().getStepClassification().getCodeContent());
        //�ӹ�����
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
        processTypeComboBox.setVisible(false);
        //�������
        stepTypeComboBox.setVisible(false);
        processTypeDisJLabel.setVisible(true);
        processTypeDisJLabel.setText(getProcedure().getProcessType().
                                     getCodeContent());
        //������
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setVisible(false);
        }
        workshopDisJLabel.setVisible(true);
        //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
        String workshop="";
        if(getProcedure().getWorkShop() instanceof CodingClassificationIfc){
        	workshop=((CodingClassificationIfc)getProcedure().getWorkShop()).getCodeSort();
        }
        else{
        	workshop=((CodingIfc)getProcedure().getWorkShop()).getCodeContent();
        }
        	
        workshopDisJLabel.setText(workshop);
      //CCEnd by leixiao 2010-4-1 ������ȡ���ղ��� 

        //��������
        contentPanel.setEditable(false);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.resumeData(v);
        }
        //��ʱ����
        processHoursJPanel.setProcedure(getProcedure());
        processHoursJPanel.setViewMode();

        setButtonVisible(false);
        //Ѧ�� �޸� 20080428 �����Ż����¹��գ�����ٶ�
        //���⣨8��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
        //                           ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
        drawingLinkPanel.setProcedure(getProcedure());//CR3
//        DrawThread dt = new DrawThread();
//        dt.start();
        //20080820 xucy
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
        //Ѧ�� �޸Ľ��� 20080428
        //����
        doclinkJPanel.setMode("View");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        //��ͼ
        drawingLinkPanel.setModel(1); //VIEW
        relationsJPanel.add(drawingLinkPanel, "��ͼ");
        //part����
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());
            partlinkJPanel.setMode("View");
        }
        f1.setViewMode();
        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());

        upJPanel.repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() end...return is void");

        }
    }


    /**
     * ���õ�ǰѡ��Ĺ���
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedureInfo = info;
    }


    /**
     * ��õ�ǰѡ��Ĺ���
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedureInfo;
    }


    /**
     * �����������(��š�����)�Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkIsEmpty()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.checkIsEmpty() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        isOK = numJTextField.check();
        if (isOK)
        {
            isOK = nameJTextField.check();
        }
        //add by wangh on 20070201
        if (isOK)
        {
            isOK = descNumJTextField.check();
        }
        if (isOK)
        {

//            //����ӹ������Ƿ�Ϊ��
//            if (processTypeSortingSelectedPanel.getCoding() == null)
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_PROCESSTYPE_ENTERED,
//                        null);
//                isOK = false;
//                processTypeSortingSelectedPanel.getJButton().grabFocus();
//            }
        	//���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
            //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
            //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        	if(processTypeComboBox.getSelectedItem().equals("") || processTypeComboBox.getSelectedItem() == null)
        	{
                message = QMMessage.getLocalizedMessage(RESOURCE,
                       CappLMRB.NO_PROCESSTYPE_ENTERED,
                       null);
                isOK = false;
                processTypeComboBox.grabFocus();
        	}
            //���鹤�������Ƿ�Ϊ��
        	
//            else if (contentPanel.save() == null ||
//                     contentPanel.save().trim().equals(""))
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                        null);
//                isOK = false;
//                contentPanel.getTextComponent().grabFocus();
//            }
        	
        	
            //���鹤������Ƿ�Ϊ��
        	//���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
            //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
            //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
            else if (this.stepTypeComboBox.getSelectedItem().equals("") || stepTypeComboBox.getSelectedItem() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_STEPCLASSIFI_ENTERED,
                        null);
                isOK = false;
                stepTypeComboBox.grabFocus();
            }
            //���鲿���Ƿ�Ϊ��
            else if (workshopSortingSelectedPanel != null &&
                    workshopSortingSelectedPanel.getCoding() == null)
           {
               message = QMMessage.getLocalizedMessage(RESOURCE,
                       CappLMRB.NO_WORKSHOP_ENTERED,
                       null);
               isOK = false;
               workshopSortingSelectedPanel.getJButton().grabFocus();
            }
//            else
//            {
//                String tempString = contentPanel.save().trim();
//                if (1 == tempString.length())
//                {
//                    int tempChar = tempString.charAt(0);
//                    if (tempChar == 128)
//                    {
//                        message = QMMessage.getLocalizedMessage(RESOURCE,
//                                CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                                null);
//                        isOK = false;
//                        contentPanel.getTextComponent().grabFocus();
//                    }
//                }
//            }
        }
        //�����ͼ�����ʽ�Ƿ�Ϊ��
        /* else if ( drawingExportJComboBox.isVisible()&& drawingExportSortingSelectedPanel.getCoding()==null)
         {
           message = QMMessage.getLocalizedMessage(RESOURCE,
               CappLMRB.NO_DRAWINGEXPORT_ENTERED,
               null);
           isOK = false;
           drawingExportSortingSelectedPanel.getJButton().grabFocus();
          }*/
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
                    "cappclients.capp.view.TechnicsStepJPanel.checkIsEmpty() end...return: " +
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
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() begin...");
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
            throw (new PropertyVetoException(QMMessage.getLocalizedMessage(
                    RESOURCE,
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
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() end...return is void");
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


    /**
     * ִ�б������
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
        else if (getViewMode() == UPDATE_MODE)//CR1
        {
            saveWhenUpdate();
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();

    }


    /**
     * ִ��ȡ������
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
        String num = numJTextField.getText();
        setCreateMode();
        numJTextField.setText(num);
        //add by wangh on 20070201
        descNumJTextField.setText(num);
        /*numJTextField.setText("");
                 nameJTextField.setText("");
                 relationTechJTextField.setText("");
                 stepClassifiSortingSelectedPanel.setViewTextField(null);
                 processTypeSortingSelectedPanel.setViewTextField(null);
                 workshopSortingSelectedPanel.setViewTextField(null);
                 drawingExportJComboBox.setSelectedIndex(0);
                 contentPanel.clearAll();
                 processHoursJPanel.clear();
                 equiplinkJPanel.clear();
                 materiallinkJPanel.clear();
                 toollinkJPanel.clear();
                 doclinkJPanel.clear();
                 if(partlinkJPanel!=null)
            partlinkJPanel.clear();
                 drawingPanel.setDrawingDate(null);*/

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
     * ִ���˳�����
     * @param e
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


    /**
     * �˳�
     * @return boolean
     */
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


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenCreate()
    {
        String s = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE, null);
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

    }


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     * add by guoxl 2009-1-7(ֻ�и��½��������޸���,�ŵ����Ƿ񱣴�Ի���)
     */
//    private void quitWhenUpdate()
//    {
//        String s = QMMessage.getLocalizedMessage(
//                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE_UPDATE, null);
//
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

    private void quitWhenUpdate()
    {
        String s = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE_UPDATE, null);
        
        boolean ischange=TechnicsContentJPanel.addFocusLis.finalChangeValue();
        System.out.println("quitWhenUpdate!!!!!!!!!!");
        if(ischange){
        	int i = confirmAction(s);//Begin CR1
    		if (i == JOptionPane.YES_OPTION)
    		{
    			saveWhenUpdate();
    		}
    		if ((i == JOptionPane.NO_OPTION))
    		{
    			setVisible(false);
    			isSave = true;
    		}
    		if ((i == JOptionPane.CANCEL_OPTION))
    		{
    			isSave = false;
    		}//End CR1
        }else{
        	
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
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(), s, title,
				JOptionPane.YES_NO_CANCEL_OPTION);//CR1
    }


    /**
     * ���ù��򿨵��������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     * ����(3) 20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
     * �������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
     * �����浽���ݿ�ʱ,Ӧ��ת�����ĵ�master�͹���Ĺ���
     */
    private CappWrapData commitAttributes()
    {
    	
    	this.eqVec.clear();
    	this.toolVec.clear();
    	this.materiaVec.clear();
    	
    	this.eqDeleVec.clear();
    	this.toolDeleVec.clear();
    	this.materiaDeleVec.clear();
    	
    	
        //���ù�������(��š����ơ��������ࡢ������𡢼ӹ����͡����š��������ա����ռ�ͼ��
        //��ͼ�����ʽ)
        //�����ǹ���,�����ù�������
        processHoursJPanel.setProcedure(getProcedure());
        getProcedure().setIsProcedure(true);
        if (getViewMode() == CREATE_MODE)
        {
            CodingIfc code = stepType;
            getProcedure().setTechnicsType(code);
        }
        //���ñ��
        if (numJTextField.getText().length() > 5)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEP_NUMBER_INVALID, null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEPNUMBER_TOO_LONG, null);
            JOptionPane.showMessageDialog(getParentJFrame(), message,
                                          title,
                                          JOptionPane.WARNING_MESSAGE);
            numJTextField.grabFocus();
            return null;
        }
        Integer i = Integer.valueOf(numJTextField.getText().trim());
        getProcedure().setStepNumber(i.intValue());
        //add by wangh on 20070208(�õ������ù�����)
        String s = String.valueOf(descNumJTextField.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //��������
        getProcedure().setStepName(nameJTextField.getText());
        //���ù������
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
        getProcedure().setStepClassification((CodingIfc)stepTypeComboBox.getSelectedItem());
        getProcedure().setProcessType((CodingIfc)processTypeComboBox.getSelectedItem());
        //���ò���
        //���ò���
        if (workshopSortingSelectedPanel != null)
        {
            getProcedure().setWorkShop(
                    (CodingIfc) workshopSortingSelectedPanel.getCoding());
        }
        else
        {
            if (mode == CREATE_MODE)
            {
            //  CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,������ȡ���ղ���
                getProcedure().setWorkShop((BaseValueIfc)
                                           parentTechnics.getWorkShop());
            //  CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,������ȡ���ղ���
            }
        }
        //���ù�������
        //if (relatedTechnics != null)
        //{
        //getProcedure().setRelationCardBsoID(relatedTechnics.
        //                                      getBsoID());
        //}
        //else
        //{
        //getProcedure().setRelationCardBsoID(null);
        // }
        //if(relationTechJTextField.getText()!=null&&relationTechJTextField.getText().trim().length()!=0){
        getProcedure().setRelationCardBsoID(relatedTechnicsID);
        // }
        //���ù�������
        Vector v = new Vector();
        v.addElement(contentPanel.save());
        getProcedure().setContent(v);
        //���㵥����ʱ(�����ʱ����ά������û�й�������)
        if (processHoursJPanel.getMode() == "EDIT" &&
            processHoursJPanel.isEnabled())
        {
            try
            {
                processHoursJPanel.setHours();
            }
            catch (QMRemoteException ex)
            {
            	ex.printStackTrace();
                String title = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.SAVE_STEP_FAILURE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                processHoursJPanel.clear();
                return null;
            }
        }

        //�������ϼ�
        if (getViewMode() == CREATE_MODE)
        {

            QMFawTechnicsInfo pTechnics = (QMFawTechnicsInfo) selectedNode.
                                          getObject().getObject();
            procedureInfo.setLocation(pTechnics.getLocation());

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
        //add by wangh on 20070212
        if (processFlowJPanel.check())
        {
          
            
            if (procedureInfo != null) {
				Vector eqDeleteVec = processFlowJPanel.groupPanel.eqDeleteVec;
				System.out.println("anan   eqDeleteVec===="+eqDeleteVec);
				Vector oldEqVec=null;
				
				
 				Class[] paraClass1 = { String.class };
				Object[] objs1 = { procedureInfo.getBsoID() };
				try {
					
					 oldEqVec = (Vector) useServiceMethod(
							"StandardCappService", "getEquipByProcedure",
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
System.out.println("anan   eqHsh===="+eqHsh);
System.out.println("anan   eqHsh===="+eqHsh.size());
					for (Iterator it = eqHsh.keySet().iterator(); it
							.hasNext();) {

						String key = (String) it.next();
						System.out.println("anan   key===="+key);
						int count = (Integer) eqHsh.get(key);
						
						if(oldEqVec!=null){
							
						System.out.println("anan   oldEqVec.size()===="+oldEqVec.size());
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
            
            }
            
            
            
            
	        int groupCount=processFlowJPanel.getExAttr().getAttGroupCount();
    		
        	if (groupCount > 0) {
				Vector vec = processFlowJPanel.getExAttr().getAttGroups(
						"���Կ���");
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa============================="+vec);
				if(vec!=null){
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
										 System.out.println("eqVec============**************=====��������=========================="+eqVec);
											 
										 
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
            
        	  //���ù�������
            procedureInfo.setProcessFlow(processFlowJPanel.
                                         getExAttr());
            
        }
        else
        {
            if (verbose)
            {
                System.out.println("�����������¼�����");
            }
            isSave = false;
            return null;
        }
        if (femaJPanel.check())
        {
            //����FMEA
            procedureInfo.setFema(femaJPanel.getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("����FMEA¼�����");
            }
            isSave = false;
            return null;
        }
        if (processControlJPanel.check())
        {
        	
    		if (procedureInfo != null) {
				Vector eqDeleteVec = processControlJPanel.groupPanel.eqDeleteVec;
				Vector materDeleteVec = processControlJPanel.groupPanel.materDeleteVec;
				Vector toolDeleteVec = processControlJPanel.groupPanel.toolDeleteVec;
				Vector oldEqVec=null;
				Vector oldMaterVec=null;
				Vector oldToolVec=null;
				
				
				Class[] paraClass1 = { String.class };
				Object[] objs1 = { procedureInfo.getBsoID() };
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
							//CCBegin SS9
							//int count = (Integer) eqHsh.get(key);
							
							if(oldEqVec!=null){
								
								for(int aa=0;aa<oldEqVec.size();aa++){
									
									QMProcedureQMEquipmentLinkIfc  oldeq=(QMProcedureQMEquipmentLinkIfc)oldEqVec.get(aa);
									
									String oldeqID=oldeq.getRightBsoID();
									//int oldCount=oldeq.getUsageCount();
									
									System.out.println("oldeqID==========�豸======*************==================="+oldeqID);
									System.out.println("key==============�豸==*************==================="+key);
									if(key.equals(oldeqID)){
										
										//int rcount=oldCount-count;
										
										//System.out.println("rcount========�豸========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldeq };
											
										/*if(rcount>0){
											
											   oldeq.setUsageCount(rcount);
											    //���±���
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====����===�豸========*************===================");
										     }else{*/
											
											      //ɾ��
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	eqDeleVec.add(oldeq);
										    	System.out.println("=====ɾ��====�豸=======*************===================");
										     //}
										//CCEnd SS9
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
							//CCBegin SS9
							//int count = (Integer) materHsh.get(key);
							
							if(oldMaterVec!=null){
								
                            for(int aa=0;aa<oldMaterVec.size();aa++){
                            	QMProcedureQMMaterialLinkIfc  oldMater=(QMProcedureQMMaterialLinkIfc)oldMaterVec.get(aa);
									
									String oldeqID=oldMater.getRightBsoID();
									//float oldCount=oldMater.getUsageCount();
									if(key.equals(oldeqID)){
										
										
										//System.out.println("Float.intBitsToFloat(count)========����========*************==================="+Float.intBitsToFloat(count));
										
										//float rcount=oldCount-count;
										
										//System.out.println("rcount========����========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldMater };
											
										/*if(rcount>0){
											oldMater.setUsageCount(rcount);
											    //���±���
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====����====����=======*************===================");
										     }else{*/
											
											      //ɾ��
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	materiaDeleVec.add(oldMater);
										    	System.out.println("=====ɾ��====����=======*************===================");
										     //}
										//CCEnd SS9
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
							//CCBegin SS9
							//int count = (Integer) toolHsh.get(key);
							
							if(oldToolVec!=null){
								
                            for(int aa=0;aa<oldToolVec.size();aa++){
									
                            	QMProcedureQMToolLinkIfc  oldTool=(QMProcedureQMToolLinkIfc)oldToolVec.get(aa);
									
									String oldeqID=oldTool.getRightBsoID();
									//int oldCount=0;
									if(oldTool.getUsageCount()!=null)
										//oldCount=Integer.parseInt(oldTool.getUsageCount());
									
									
									System.out.println("oldeqID========��װ========*************==================="+oldeqID);
									System.out.println("key============��װ====*************==================="+key);
									if(key.equals(oldeqID)){
										
										//int rcount=oldCount-count;
										
										//System.out.println("rcount=======��װ=========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldTool };
											
										/*if(rcount>0){
											
											oldTool.setUsageCount(String.valueOf(rcount));
											    //���±���
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====����====��װ=======*************===================");
										     }else{*/
											
											      //ɾ��
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	toolDeleVec.add(oldTool);
										    	System.out.println("=====ɾ��====��װ=======*************===================");
										     //}
										//CCEnd SS9
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
				String intCount="0";
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
											 
											//CCBegin SS9
											if(intCount!=null)
											binarylinkinfo.setUsageCount(new Integer(intCount));
											//CCEnd SS9
										   eqVec.addElement(binarylinkinfo);
										 }
										 System.out.println("eqVec============**************==============================="+eqVec);
											 
										 
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
													
													System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===00========="+eqid);
													System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq===11========="+obj);

													float count = binarylinkinfo
															.getUsageCount();
													count += 1;

													binarylinkinfo
															.setUsageCount(count);
											
													break;

												} else {

													System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

													QMProcedureQMMaterialLinkInfo binarylinkinfo1 = (QMProcedureQMMaterialLinkInfo) taskLogic
															.createNewLinkForBSX(Info);
													

											//CCBegin SS9
											if(intCount!=null)
											binarylinkinfo1.setUsageCount(new Integer(intCount));
											//CCEnd SS9
													if(!materiaVec.contains(binarylinkinfo1))
													    materiaVec.addElement(binarylinkinfo1);
												 break;

												}
												 
												

											}
										} else {

											System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccc");
											QMProcedureQMMaterialLinkInfo binarylinkinfo = (QMProcedureQMMaterialLinkInfo) taskLogic
													.createNewLinkForBSX(Info);
											//CCBegin SS9
											//binarylinkinfo.setUsageCount(1);
											if(intCount!=null)
											binarylinkinfo.setUsageCount(new Integer(intCount));
											//CCEnd SS9
											if(!materiaVec.contains(binarylinkinfo))
											   materiaVec.addElement(binarylinkinfo);

										}
										System.out.println("materiaVec============**************==============================="+materiaVec.size());

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
							 
							 
							 
							 
							
						}else if(attrName.equals("toolBsoID")){
							
							
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
															
															
                                                            if(count!=null&&(count.indexOf("(")==-1||count.indexOf("��")==-1)){
                                                            	int c=Integer.parseInt(count)+Integer.parseInt(intCount);
                                                            	
                                                            	count=String.valueOf(c);
                                                            	
                                                            	binarylinkinfo.setUsageCount(count);
                                                            	
                                                            }else{
                                                            	
                                                            	break;
                                                            }
															

														} else {

															

															QMProcedureQMToolLinkInfo binarylinkinfo1 = (QMProcedureQMToolLinkInfo) taskLogic
																	.createNewLinkForBSX(Info);
															binarylinkinfo1.setUsageCount(intCount);
															
															toolVec.addElement(binarylinkinfo1);
															break;

														}

													}
												} else {


													QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) taskLogic
															.createNewLinkForBSX(Info);
													binarylinkinfo.setUsageCount(intCount);
													toolVec.addElement(binarylinkinfo);

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
						//CCBegin SS3
						if(attrName.equals("Count")){
							intCount = (String)model.getAttValue();
						 }
						//CCEnd SS3
						
					}
				}

			}
        	
        	
        	
        	
        	
        	
            //���ù��̿���
            procedureInfo.setProcessControl(processControlJPanel.
                                            getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("������̿���¼�����");
            }
            isSave = false;
            return null;
        }

        //�����й����ϲ�
        Vector resourceLinks = new Vector();

        //������й���(�豸����װ�����ϡ��ĵ�)
        Vector docLinks;
        //20080820
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
//        ArrayList pDrawings = null;
//        ArrayList drawingLinks = null;
		ArrayList updatedrawings = null;//Begin CR3
		ArrayList deletedrawings = null;//End CR3
        //����(3) 20080602 �촺Ӣ�޸�  �����������ĵ�ת�����ĵ�master�͹���Ĺ���
        Vector docMasterLinks = new Vector();
        try
        {
            docLinks = doclinkJPanel.getAllLinks();
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
            //20080820 xucy
            if(equiplinkJPanel != null)
            {
                equipLinks = equiplinkJPanel.getAllLinks();
                 System.out.println("anan equipLinks = "+equipLinks);
                 System.out.println("anan eqVec = "+eqVec);
                 System.out.println("anan eqDeleVec = "+eqDeleVec);
                
                if(eqVec.size()!=0){
                	for(int a=0;a<eqVec.size();a++){
                		boolean isHas=false;
                		QMProcedureQMEquipmentLinkInfo newlinkinfo=(QMProcedureQMEquipmentLinkInfo)eqVec.get(a);
                		
                		String newLinkID=newlinkinfo.getRightBsoID();
                		//CCBegin SS9
              		   QMProcedureQMEquipmentLinkInfo oldEqLinkInfo=(QMProcedureQMEquipmentLinkInfo)findIsHasObj(newLinkID,equipLinks);
              		   
              		   if(oldEqLinkInfo!=null){
              			   oldEqLinkInfo.setUsageCount(newlinkinfo.getUsageCount());
              			   int b=equipLinks.indexOf(oldEqLinkInfo);
              			   System.out.println("index  oldEqLinkInfo=================================="+b+" =========== count=="+newlinkinfo.getUsageCount());
              			   equipLinks.remove(b);
              			   equipLinks.add(b, oldEqLinkInfo);
              		   }
              		   else{
              			   equipLinks.add(newlinkinfo);
              		   }
                		/*System.out.println("newlinkinfo.getLeftBsoID()================��=================="+newlinkinfo.getLeftBsoID());
                		
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
                		}*/
                		//CCEnd SS9
                		
                	
                	}
                }
                System.out.println("eqDeleVec======ggggggggggggg==================="+eqDeleVec);
                if(eqDeleVec.size()!=0){
                	for(int m=0;m<eqDeleVec.size();m++){
                		
                		QMProcedureQMEquipmentLinkInfo deleteEqLinkInfo=(QMProcedureQMEquipmentLinkInfo)eqDeleVec.get(m);
                		
              		//CCBegin SS9
              		/*BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),equipLinks);
                		
                		if(hasLink!=null){
                			equipLinks.remove(hasLink);
                			
                		}*/
              		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),eqVec);
              		if(hasLink==null)
              		{
              			for(int n=0;n<equipLinks.size();n++)
                 			{
                 				QMProcedureQMEquipmentLinkInfo equiplinksInfo=(QMProcedureQMEquipmentLinkInfo)equipLinks.get(n);
                 				if(equiplinksInfo.getBsoID().equals(deleteEqLinkInfo.getBsoID()))
                 				{
                 					equipLinks.removeElementAt(n);
                 					break;
                 				}
                 			}
              		}
              		//CCEnd SS9
                	}
                	
                }
                
                
            }
            if(toollinkJPanel != null)
            {
                 toolLinks = toollinkJPanel.getAllLinks();
                 System.out.println("anan toolLinks = "+toolLinks);
                 System.out.println("anan toolVec = "+toolVec);
                 System.out.println("anan toolDeleVec = "+toolDeleVec);
                 if(toolVec.size()!=0){
              	   System.out.println("toolVec.size()======================================="+toolVec.size());
              	   for (int b = 0; b < toolVec.size(); b++) {
              		   
              		   QMProcedureQMToolLinkInfo newToolLinkInfo=(QMProcedureQMToolLinkInfo)toolVec.get(b);
              		   
              		   String newLinkID=newToolLinkInfo.getRightBsoID();
              		   
              		   
              		   QMProcedureQMToolLinkInfo oldToolLinkInfo=(QMProcedureQMToolLinkInfo)findIsHasObj(newLinkID,toolLinks);
              		   
              		   if(oldToolLinkInfo!=null){
              			   oldToolLinkInfo.setUsageCount(newToolLinkInfo.getUsageCount());
              			   int a=toolLinks.indexOf(oldToolLinkInfo);
              			   System.out.println("index  oldToolLinkInfo=================================="+a+" =========== count=="+newToolLinkInfo.getUsageCount());
              			   toolLinks.remove(a);
              			   toolLinks.add(a, oldToolLinkInfo);
              		   }
              		   else{
              			   toolLinks.add(newToolLinkInfo);
              		   }
  					}
                 }
                 //System.out.println("toolDeleVec======ggggggggggggg==================="+toolDeleVec);
                 if(toolDeleVec.size()!=0){
                 	for(int m=0;m<toolDeleVec.size();m++){
                 		
                 		QMProcedureQMToolLinkInfo deleteEqLinkInfo=(QMProcedureQMToolLinkInfo)toolDeleVec.get(m);
                 		//CCBegin SS9
                 		//BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),toolLinks);
                 		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),toolVec);
                 		//if(hasLink!=null){
                 		if(hasLink==null){
                 			//toolLinks.remove((BinaryLinkIfc)deleteEqLinkInfo);
                 			for(int n=0;n<toolLinks.size();n++)
                 			{
                 				QMProcedureQMToolLinkInfo toollinksInfo=(QMProcedureQMToolLinkInfo)toolLinks.get(n);
                 				if(toollinksInfo.getBsoID().equals(deleteEqLinkInfo.getBsoID()))
                 				{
                 					toolLinks.removeElementAt(n);
                 					break;
                 				}
                 			}
                 		}
                 		//CCEnd SS9
                 	}
                 	
                 }
            
            }
            if(materiallinkJPanel != null)
            {
            materialLinks = materiallinkJPanel.getAllLinks();
                 System.out.println("anan materialLinks = "+materialLinks);
                 System.out.println("anan materiaVec = "+materiaVec);
                 System.out.println("anan materiaDeleVec = "+materiaDeleVec);
            if (materiaVec.size() != 0) {
				for (int b = 0; b < materiaVec.size(); b++) {

					
					QMProcedureQMMaterialLinkInfo newMaterLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaVec.get(b);
            		   
            		   String newLinkID=newMaterLinkInfo.getRightBsoID();
            		   

            		   QMProcedureQMMaterialLinkInfo oldMateLinkInfo=(QMProcedureQMMaterialLinkInfo) findIsHasObj(newLinkID,materialLinks);
            		   
            		   if(oldMateLinkInfo!=null){
            			   
            			   oldMateLinkInfo.setUsageCount(newMaterLinkInfo.getUsageCount());
            			   int a=materialLinks.indexOf(oldMateLinkInfo);
            			   System.out.println("index oldMateLinkInfo=================================="+a+" =========== count=="+oldMateLinkInfo.getUsageCount());
            			   materialLinks.remove(a);
            			   //CCBegin SS9
            			   //materialLinks.add(oldMateLinkInfo);
            			   materialLinks.add(a,oldMateLinkInfo);
            			   //CCEnd SS9
            		   }
            		   
            		   else{
            			   
            			   materialLinks.add(newMaterLinkInfo);
            		   }
					
				}
				
				System.out.println("materialLinks.size();=======###########################=================="+materialLinks.size());
        	   
			}
           System.out.println("materiaDeleVec======ggggggggggggg==================="+materiaDeleVec);
           if(materiaDeleVec.size()!=0){
              	for(int m=0;m<materiaDeleVec.size();m++){
              		
              		QMProcedureQMMaterialLinkInfo deleteEqLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaDeleVec.get(m);
              		//CCBegin SS9
              		/*BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),materialLinks);
              		
              		if(hasLink!=null){
              			materialLinks.remove(hasLink);
              			
              		}*/
              		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),materiaVec);
              		if(hasLink==null)
              		{
              			for(int n=0;n<materialLinks.size();n++)
                 			{
                 				QMProcedureQMMaterialLinkInfo materiallinksInfo=(QMProcedureQMMaterialLinkInfo)materialLinks.get(n);
                 				if(materiallinksInfo.getBsoID().equals(deleteEqLinkInfo.getBsoID()))
                 				{
                 					materialLinks.removeElementAt(n);
                 					break;
                 				}
                 			}
              		}
              		//CCEnd SS9
              	}
              }
            }
        
            if (partlinkJPanel != null)
            {
                partLinks = partlinkJPanel.getAllLinks();

            }
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

        //�ϲ��ĵ�����
        if (docMasterLinks != null)
        {
            for (int k = 0; k < docMasterLinks.size(); k++)
            {
                resourceLinks.addElement(docMasterLinks.elementAt(k));
            }
        }

        //�ϲ����Ϲ���
        if (materialLinks != null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }
        
        //�ϲ��豸����
        if (equipLinks != null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //�ϲ���װ����
        if (toolLinks != null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
            }
        }
		// �ϲ���ͼ��Դ����   Begin CR3
		/*if (drawingLinks != null)
		{
			for (int n = 0; n < drawingLinks.size(); n++)
			{
				resourceLinks.addElement(drawingLinks.get(n));
			}
		}*/  
        //End CR3

        //�ϲ��㲿������
        if (partLinks != null)
        {
            for (int n = 0; n < partLinks.size(); n++)
            {
                resourceLinks.addElement(partLinks.elementAt(n));
            }
        }

        //��װ������Ϣ
        CappWrapData cappWrapData = new CappWrapData();
        //���ù���
        cappWrapData.setQMProcedureIfc(getProcedure());
        //��װ����
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
//        cappWrapData.setPDrawings(pDrawings);
		cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR3
	    cappWrapData.setDeleteDrawing(deletedrawings);//End CR3

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

//add by wangh on 20070207(���ݲ�ͬ���������ò�ͬ�������̣�FMEA�͹��̿���)
    //���⣨5��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
    private void newExtendPanel(String processType)
    {
        if (!processType.equals(existProcessType))
        {
            if (processFlowJPanel != null)
            {
                extendJPanel2.remove(processFlowJPanel);
            }
            if (processFlowTable.get(processType) == null)
            {
                try
                {
                    processFlowJPanel = new CappExAttrPanelForZC(procedureInfo.
                            getBsoName(),
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
                processFlowJPanel = (CappExAttrPanelForZC) processFlowTable.get(
                        processType);
            }
            // CCBegin by leixiao 2008-10-28 ԭ�򣺽��ϵͳ����,�������ݴ��� 
            processFlowJPanel.setProIfc(procedureInfo);
           
//          CCEnd by leixiao 2008-10-28 ԭ�򣺽��ϵͳ���� 
            if (femaJPanel != null)
            {
                extendJPanel3.remove(femaJPanel);
            }
            if (femaTable.get(processType) == null)
            {
                try
                {
                    femaJPanel = new CappExAttrPanelForZC(procedureInfo.getBsoName(),
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
                femaJPanel = (CappExAttrPanelForZC) femaTable.get(
                        processType);
            }
            // CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
            femaJPanel.setProIfc(procedureInfo);
            // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
            
         
            
            
            if (processControlJPanel != null)
            {
                extendJPanel4.remove(processControlJPanel);
            }
            if (processControlTable.get(processType) == null)
            {
                try
                {
                    processControlJPanel = new CappExAttrPanelForZC(procedureInfo.
                            getBsoName(),
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
                processControlJPanel = (CappExAttrPanelForZC) processControlTable.
                                       get(
                        processType);
            }
            // CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
            processControlJPanel.setProIfc(procedureInfo);
            // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����
        }
        processFlowJPanel.clear();
        femaJPanel.clear();
        processControlJPanel.clear();
        
        
        
        //CCBegin SS1
        processFlowJPanel.groupPanel.inheritButton.addActionListener(new ChuanDiActionListener());
        femaJPanel.groupPanel.inheritButton.addActionListener(new ChuanDiActionListener());
        processControlJPanel.groupPanel.inheritButton.addActionListener(new ChuanDiActionListener());
        //CCEnd SS1
        
        
        
        if (mode == CREATE_MODE ||
            mode == UPDATE_MODE)
        {
            processFlowJPanel.setModel(CappExAttrPanelForZC.EDIT_MODEL);
            femaJPanel.setModel(CappExAttrPanelForZC.EDIT_MODEL);
            processControlJPanel.setModel(CappExAttrPanelForZC.EDIT_MODEL);
        }
        else
        {
            processFlowJPanel.setModel(CappExAttrPanelForZC.VIEW_MODEL);
            femaJPanel.setModel(CappExAttrPanelForZC.VIEW_MODEL);
            processControlJPanel.setModel(CappExAttrPanelForZC.VIEW_MODEL);
        }
        if (mode != CREATE_MODE)
        {
            processFlowJPanel.show(getProcedure().getProcessFlow());
            femaJPanel.show(getProcedure().getFema());
            processControlJPanel.show(getProcedure().getProcessControl());
        }
        extendJPanel2.add(processFlowJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));
        extendJPanel3.add(femaJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));
        extendJPanel4.add(processControlJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));

        //add by wangh on 200726(���ù������̣�����FMEA�Ϳ��Ƽƻ��Ƿ�ɼ�)
//       if (!ts16949) {
//         processFlowJPanel.setVisible(false);
//         femaJPanel.setVisible(false);
//         processControlJPanel.setVisible(false);
//       }
        //���⣨5��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
        processControlJPanel.groupPanel.flowMultiList = processFlowJPanel.groupPanel.multiList;
        //processControlJPanel.groupPanel.processFlowJPanel=processFlowJPanel;
        repaint();
        processType = "";
    }


    /**
     * �����½��Ĺ���
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() begin...");
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
        if (!checkIsInteger(numJTextField.getText().trim()))
        {
            numJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            numJTextField.grabFocus();
            return;
        }
        
//      CCBegin SS6
        if(processFlowJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processFlowJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(femaJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	femaJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(processControlJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processControlJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//      CCEnd SS6
//        CCBegin SS7
        int row = processFlowJPanel.groupPanel.multiList.getRowCount();
        for(int i=0;i<row;i++){
        	String eqbsoid = processFlowJPanel.groupPanel.multiList.getCellText(i, 12);
        	if(!eqbsoid.equals("")){
        		if(processHoursJPanel.getMachineHour()!=0.0||processHoursJPanel.getPartProcessHour()!=0.0)
        		{
        			processFlowJPanel.groupPanel.multiList.addTextCell(i, 6, String.valueOf(processHoursJPanel.getMachineHour()));
        			processFlowJPanel.groupPanel.multiList.addTextCell(i, 7, String.valueOf(processHoursJPanel.getPartProcessHour()));
        		}
        	}
//        	CCBegin SS8
        	if(i==0 && eqbsoid.equals("")){
        		if(processHoursJPanel.getMachineHour()!=0.0||processHoursJPanel.getPartProcessHour()!=0.0)
        		{
        			processFlowJPanel.groupPanel.multiList.addTextCell(i, 6, String.valueOf(processHoursJPanel.getMachineHour()));
        			processFlowJPanel.groupPanel.multiList.addTextCell(i, 7, String.valueOf(processHoursJPanel.getPartProcessHour()));
        		}
        	}
//        	CCEnd SS8
        }
//        CCEnd SS7

        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        QMProcedureInfo returnProce;//CR1
        try
        {
            //��ÿ�ͷ�����ѡ��ڵ�ʱ�ڵ��ڹ������ϼУ���ԭ�����������丱��
            QMTechnicsInfo pTechnics =
                    (QMTechnicsInfo) selectedNode.getObject().getObject();
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) pTechnics))
            {
                pTechnics = (QMTechnicsInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy(
                        (WorkableIfc) pTechnics);

                //���÷��񣬱��湤��
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {String.class, String.class, CappWrapData.class};
            //��������Ϊ�� �ù��������Ĺ��տ�BsoID,���ڵ�BsoID,���򿨶���ķ�װ�����
            Object[] obj =
                    {pTechnics.getBsoID(), pTechnics.getBsoID(), cappWrapData};
            returnProce = (QMProcedureInfo) useServiceMethod(
                    "StandardCappService", "createQMProcedure", paraClass, obj);
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //20060727Ѧ���޸ģ�ȥ��ˢ�¹��սڵ�Ĳ���
            //ˢ�����ڵ�
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
            //   pTechnics, pTechnics.getBsoID());
            //begin cr4
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//end cr4
            //���½ڵ�
            StepTreeObject treeObject = new StepTreeObject(returnProce);
            treeObject.setParentID(pTechnics.getBsoID());
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
            return;
        }

        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //��ʾ�Ƿ�������
        String s = QMMessage.getLocalizedMessage(RESOURCE, "108", null);//Begin CR1
		int i = confirmAction(s);
		if (i == JOptionPane.YES_OPTION)
		{
			setCreateMode();
			isSave = false;
			setButtonWhenSave(true);
			return;
		}
		if ((i == JOptionPane.NO_OPTION))
		{
			try
			{
				setProcedure(returnProce);
				mode = 0; 
			}
			catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			TechnicsContentJPanel.addFocusLis.initFlag();//anan
		}
		if ((i == JOptionPane.CANCEL_OPTION))
		{
			isSave = false;
		}//End CR1
        setButtonWhenSave(true);
        isSave = true;
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() end...return is void");
        }
        
    }


    /**
     * ������º�Ĺ���
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() begin...");
        }
        
        //add by guoxl on 2009-1-8(�����򣨹��������½�����Ӽ��������������Ϣ�����������
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
        if (!checkIsInteger(numJTextField.getText().trim()))
        {
            numJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            numJTextField.grabFocus();
            return;
        }
        
//      CCBegin SS6
        if(processFlowJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processFlowJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(femaJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	femaJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(processControlJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processControlJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//      CCEnd SS6
//      CCBegin SS7
      int row = processFlowJPanel.groupPanel.multiList.getRowCount();
      for(int i=0;i<row;i++){
      	String eqbsoid = processFlowJPanel.groupPanel.multiList.getCellText(i, 12);
      	if(!eqbsoid.equals("")){
      		if(processHoursJPanel.getMachineHour()!=0.0||processHoursJPanel.getPartProcessHour()!=0.0)
      		{
      			processFlowJPanel.groupPanel.multiList.addTextCell(i, 6, String.valueOf(processHoursJPanel.getMachineHour()));
      			processFlowJPanel.groupPanel.multiList.addTextCell(i, 7, String.valueOf(processHoursJPanel.getPartProcessHour()));
      		}
      	}
//      CCBegin SS8
      	if(i==0 && eqbsoid.equals("")){
    		if(processHoursJPanel.getMachineHour()!=0.0||processHoursJPanel.getPartProcessHour()!=0.0)
    		{
    			processFlowJPanel.groupPanel.multiList.addTextCell(i, 6, String.valueOf(processHoursJPanel.getMachineHour()));
    			processFlowJPanel.groupPanel.multiList.addTextCell(i, 7, String.valueOf(processHoursJPanel.getPartProcessHour()));
    		}
    	}
//      CCEnd SS8
      }
//      CCEnd SS7

        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //��ʾ�������
        //ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //       RESOURCE, CappLMRB.SAVING, null));
        //ProgressService.showProgress();
        try
        {
            parentTechnics = (QMTechnicsInfo) refreshInfo(parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo) CheckInOutCappTaskLogic.
                                 getWorkingCopy(
                        (WorkableIfc) parentTechnics);
                //���÷��񣬱��湤��
            }

            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {String.class, String.class, CappWrapData.class};
            Object[] obj =
                    {parentTechnics.getBsoID(), parentTechnics.getBsoID(),
                    cappWrapData};
            QMProcedureInfo returnProce;

            returnProce = (QMProcedureInfo) useServiceMethod(
                    "StandardCappService", "updateQMProcedure", paraClass, obj);
            //begin cr4
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }
            //end cr4
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //ˢ�����ڵ�
            ((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                    returnProce, parentTechnics.getBsoID());
            procedureInfo = returnProce;
            //CCBegin SS9
            setUpdateMode();
            //CCEnd SS9
        }
        catch (QMRemoteException ex)
        {
        	ex.printStackTrace();
        	//20081119 xucy �޸�   �޸�ԭ�򣺳��쳣��ʱ��ѽ������ص�
        	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //  ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;  
        //���⣨6��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��    begin 
        //ת���ɲ鿴����
//        try
//        {
//            setViewMode(VIEW_MODE);
//        }
//        catch (PropertyVetoException ex1)
//        {
//        	//20081119 xucy �޸�   �޸�ԭ�򣺳��쳣��ʱ��ѽ������ص�
//        	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
//            ex1.printStackTrace();
//        }
        //���⣨6��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��   end
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() end...return is void");
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
     * ��������
     * @param e
     */
    void searchTechJButton_actionPerformed(ActionEvent e)
    {

        searchRelatedTechnics();
    }


    /**
     * ������������
     */
    private void searchRelatedTechnics()
    {
        try
        {
            sd = new TechnicsSearchJDialog((TechnicsRegulationsMainJFrame)
                                           parentJFrame);
            sd.setModal(false);
            sd.setSingleSelectMode();
            sd.addQueryListener(new CappQueryListener()
            {
                public void queryEvent(CappQueryEvent e)
                {
                    qmQuery_queryEvent(e);
                }
            });
            //���TechnicsSearchJDialog�е�mutilist���е�˫������
            sd.addMultiListActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    myList_actionPerformed(e);
                }
            }
            );
            sd.setVisible(true);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
    }


    /**�����������յĽ������*/
    private TechnicsSearchJDialog sd = null;


    /**�������գ����ڻ��棩*/
    //private QMFawTechnicsInfo relatedTechnics = null;
    private String relatedTechnicsID = null;


    /**
     * �������ռ����¼�����
     * @param e ���������¼�
     */
    public void qmQuery_queryEvent(CappQueryEvent e)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) begin...");
        }
        if (e.getType().equals(CappQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(CappQuery.OkCMD))
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CappQuery c = (CappQuery) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                actionPerformed(bvi);
                setCursor(Cursor.getDefaultCursor());
            }
            if (e.getCommand().equals(CappQuery.QuitCMD))
            {
                sd.setVisible(false);
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) end...return is void");
        }
    }


    /**
     *�������еĹ��չ�̼���������ա��ı����У��˷����ṩ��TechnicsSearchJDialog�����������¼�
     */
    private void actionPerformed(Object[] bvi)
    {
        if (bvi != null)
        {
            //�Ѵӽ����ѡ�е�ҵ�������롰�������ա��ı���
            for (int i = 0; i < bvi.length; i++)
            {
                QMFawTechnicsInfo info = (QMFawTechnicsInfo) bvi[i];
                String compareBsoID = "";
                //Begin CR6
                if(info.getWorkableState().equals("wrk"))
                {
                    String s = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.RELATIONTECHNICS_CANTBE_CHECK_OUT_OBJECT, null);
                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(), s, ss1,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    setCursor(Cursor.getDefaultCursor());
                    return;
                }
                //End CR6
                if (getViewMode() == CREATE_MODE)
                {
                    compareBsoID = ((BaseValueInfo) selectedNode.getObject().
                                    getObject()).getBsoID();
                }
                if (getViewMode() == UPDATE_MODE)
                {
                    compareBsoID = parentTechnics.getBsoID();
                }
                if (info.getBsoID().equals(compareBsoID))
                {
                    String s = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.RELATIONTECHNICS_CANTBE_THISTECHNICS, null);
                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(), s, ss1,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    setCursor(Cursor.getDefaultCursor());
                    return;
                }
                relationTechJTextField.setText(getIdentity(info));
                //relatedTechnics = info;
                relatedTechnicsID = info.getBsoID();
                if (sd != null)
                {
                    sd.setVisible(false);
                }
            }
        }
    }


    /**�����������������˫���¼�*/
    private void myList_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CappMultiList c = (CappMultiList) e.getSource();
        Object[] bvi = c.getSelectedObjects();
        
        actionPerformed(bvi);
        setCursor(Cursor.getDefaultCursor());
    }

    private String existProcessType = "";
    private CappSortingSelectedPanel workshopSortingSelectedPanel = null;
    private ProcedureUsagePartJPanel partlinkJPanel;


    /**
     * ���ղ���ά��
     * <p>���ղ���ά��Ϊ�������ڣ�������������Ǹ��ݹ������಻ͬ����ʾ���ݲ�ͬ��</p>
     * @param e
     */
    void paraJButton_actionPerformed(ActionEvent e)
    {

        String processType = "";
        //��ù�������
        if (getViewMode() == CREATE_MODE)
        {
            processType = stepType.getCodeContent().trim();
        }
        else if (getViewMode() == UPDATE_MODE ||
                 getViewMode() == VIEW_MODE)
        {
            processType = getProcedure().getTechnicsType().getCodeContent().
                          trim();
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

                d = new TParamJDialog(procedureInfo.getBsoName(), parentJFrame,
                                      "");
                d.setProcedure(getProcedure());
                
                //CCBegin by liunan 2011-08-25 �򲹶�P035
                //CR12 begin
                TechnicsContentJPanel.addFocusLis.setCompsFocusListener(d.getContentPane());
                //CR12 end
                //CCEnd by liunan 2011-08-25
                
                d.setEditMode();
                d.setVisible(true);
                //CCBegin by liunan 2011-08-25 �򲹶�P035
                //CR11 start
                if(d.getIsOk())
                {
                //CCEnd by liunan 2011-08-25
                //������չ����
                ExtendAttContainer c = d.getExtendAttributes();
//                System.out.println("ssss==="+c.getSecondClassify());
//                System.out.println("ddddddd==="+c.getSecondClassifyValue());
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
                //CCBegin by liunan 2011-08-25 �򲹶�P035
              }
                //CCEnd by liunan 2011-08-25
                existProcessType = processType;
            }
            //�鿴ģʽ
            if (getViewMode() == VIEW_MODE)
            {
                d = new TParamJDialog(procedureInfo.getBsoName(), parentJFrame,
                                      "");
                d.setProcedure(getProcedure());
                d.setViewMode();
                d.setVisible(true);
            }
        }
    }


    /**
     * ִ���������������ձ����з��ֵ��豸�빤װ�Ĺ�ϵ����ع�װά����.
     * ���豸�빤װ�Ĺ������浽�豸���С��豸�б�͹�װ�б���������һ���б�����һ����
     * �ݣ��豸��װ��������豸����װ��ѡ���˶������ݣ�ѡ��������ʱ��������ʾ��Ҫ������ѡ��
     * ����豸�б��е��豸��һ������ϵͳ�����豸�빤װ�б��е����й�װ����������ϵ��
     * ����豸�б��е��豸�Ƕ�������Ȼ��װ�б�����һ������ϵͳ�ֱ�ÿ���豸��ù�װ����������ϵ��
     * @param e
     */
    void storageJButton_actionPerformed(ActionEvent e)
    {
        //�����ж�ѡ����豸����װ��������һ����ֻѡ��һ��ʵ��
        Vector equipLinks = equiplinkJPanel.getSelectedObjects();
        Vector toolLinks = toollinkJPanel.getSelectedObjects();

        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
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
     * ���������ı�����ַ����¼����¼�����
     * <p>���ѡ���˹������գ���ʱ��Ϣ����ά����</p>
     * <p>���ѡ���˹������գ�������װ���豸�����ϵȶ�Ϊ�����ã����ڹ����²��ܴ���������</p>
     * @param e CaretEvent
     */
    void relationTechJTextField_caretUpdate(CaretEvent e)
    {
        setRelatedEff();
    }


    /**
     * <p>���ѡ���˹������գ���ʱ��Ϣ����ά����</p>
     * <p>���ѡ���˹������գ�������װ���豸�����ϵȶ�Ϊ�����ã����ڹ����²��ܴ���������</p>
     */
    private void setRelatedEff()
    {
        String s = relationTechJTextField.getText().trim();
        if (s != null && !s.equals(""))
        {
            processHoursJPanel.setEnabled(false);
            //20080820
            //equiplinkJPanel.clear();
            //toollinkJPanel.clear();
            //materiallinkJPanel.clear();
            //doclinkJPanel.clear();
            //20080820 xucy
            if(equiplinkJPanel != null)
            {
            equiplinkJPanel.setMode("View");
            }
            if(toollinkJPanel != null)
            {
            toollinkJPanel.setMode("View");
            }
            if(materiallinkJPanel != null)
            {
            materiallinkJPanel.setMode("View");
            }
            //modify by wangh on 20080226 ��ԭ���Ĳ鿴ģʽ��Ϊ�ɱ༭ģʽ
            doclinkJPanel.setMode("Edit");
            //modify end
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("View");
            }
        }
        else
        {
            processHoursJPanel.setEnabled(true);
            //�޹��������ҹ������й���,��ʱ����ά����ʹ����Դ������岻��ά��
            if (c != null && c.size() != 0)
            {
                //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ�Ĺ�ʱ���ɿ����õ�
                String hourUpdateflag = RemoteProperty.getProperty(
                        "updateMachineHour", "true");
                if (hourUpdateflag.equals("true"))
                {
                    //�����еĹ�ʱ�Ƿ������
                    boolean hourflag = false;
                    for (Iterator it = c.iterator(); it.hasNext(); )
                    {
                        QMProcedureInfo procedure = (QMProcedureInfo) it.next();
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
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��

                    }
                }
                else
                {
                    processHoursJPanel.setEditMode();
                }
                //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ����Դ���ɿ����õ�
                String resourceUpdateflag = RemoteProperty.getProperty(
                        "updateResourceLink", "true");                
                if (resourceUpdateflag.equals("true"))
                {
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
                            linkCollection = CappClientHelper.getUsageResources(((
                                    BaseValueInfo) i.next())
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
                                else
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
                        if (eqflag && toolflag && matflag)
                        {
                            break;
                        }
                    }
                    //20080820 xucy
                    if(equiplinkJPanel != null)
                    {
                    if (eqflag)
                    {
                        equiplinkJPanel.setMode("View");
                    }
                    else
                    { 
                        equiplinkJPanel.setMode("View");
                    }
                    }
                    if(toollinkJPanel != null)
                    {
                    if (toolflag)
                    {
                        toollinkJPanel.setMode("View");
                    }
                    else
                    {
                        toollinkJPanel.setMode("View");
                    }
                    }
                   if(materiallinkJPanel != null)
                   {
                    if (matflag)
                    {
                        materiallinkJPanel.setMode("View");
                    }
                    else
                    {
                        materiallinkJPanel.setMode("View");
                    }
                   }
                    if (partlinkJPanel != null)
                    {
                        if (partflag)
                        {
                            partlinkJPanel.setMode("View");
                        }
                        else
                        {
                            partlinkJPanel.setMode("Edit");
                        }
                    }
                }
                else
                {
                    equiplinkJPanel.setMode("View");
                    toollinkJPanel.setMode("View");
                    materiallinkJPanel.setMode("View");
                    //20081205 �촺Ӣ�޸�   �޸�ԭ���еĹ���û�к�part�Ĺ�����
                    //����partlinkJPanel����Ϊ��
                    if(partlinkJPanel != null)
                    partlinkJPanel.setMode("Edit");
                    //add by wangh on 20080226 �����ĵ��ɱ༭.
                    doclinkJPanel.setMode("Edit");
                    //add end
                }
            }

            //�޹��������ҹ������޹�������ʱ��ά����ʹ����Դ��������ά��
            else
            {
                processHoursJPanel.setEditMode();
                //20080820 xucy
                if(equiplinkJPanel != null)
                {
                equiplinkJPanel.setMode("View");
                }
                if(toollinkJPanel != null)
                {
                toollinkJPanel.setMode("View");
                }
                if(materiallinkJPanel != null)
                {
                materiallinkJPanel.setMode("View");
                }
                doclinkJPanel.setMode("Edit");
                if (partlinkJPanel != null)
                {
                    partlinkJPanel.setMode("Edit");
                }
            }
        }
    }


    /**
     * �Ѹ�����ҵ�������ӵ���Ӧ�Ĺ����б���
     * @param info ������ҵ�������Դ��
     */
     //CCBegin by leixiao 2010-6-30 �򲹶�v4r3_p017_20100617 ����ʶCR8
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (mode != VIEW_MODE)
        {
            if (info[0] instanceof QMEquipmentInfo)
            {
            	Vector tools = null;//begin CR8
            	try
            	{//end CR8
            	if(equiplinkJPanel != null)
            	{
                for (int i = 0; i < info.length; i++)
                {
                    equiplinkJPanel.addEquipmentToTable((QMEquipmentInfo)
                            info[i]);
                     //begin CR8
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
                        //end CR8
                }
            	}
            	 }//begin CR8
            catch (QMException ex)
            {
                ex.printStackTrace();
            }//end CR8
            }
            else if (info[0] instanceof QMToolInfo)
            {
            	Vector equips = null;//begin CR8
            	try
            	{//end CR8
            	if(toollinkJPanel != null)
            	{
                for (int i = 0; i < info.length; i++)
                {
                    toollinkJPanel.addToolToTable((QMToolInfo) info[i]);
                     //begin CR8
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
                        //end CR8
                }
            	}
            	 }//begin CR8
            catch (QMException ex)
            {
                ex.printStackTrace();
            }//end CR8
            }
            else if (info[0] instanceof QMMaterialInfo)
            {
            	if(materiallinkJPanel != null)
            	{
                for (int i = 0; i < info.length; i++)
                {
                    materiallinkJPanel.addMaterialToTable((QMMaterialInfo)
                            info[i]);
                }
            	}
            }
            else if (info[0] instanceof QMTermInfo)
            {
                String termName = ((QMTermInfo) info[0]).getTermName();
                nameJTextField.setInsertText(termName);
                contentPanel.setInsertText(termName);
            }
            else if (info[0] instanceof DrawingInfo)
            {
                for (int i = 0; i < info.length; i++)
                {
                    drawingLinkPanel.addDrawingToTable(((DrawingInfo) info[i]));
                }
            }
            else if (info[0] instanceof QMTechnicsInfo)
            {

                if (mode == CREATE_MODE)
                {
                    Object[] obj =
                            {info[0]};
                    actionPerformed(obj);
                    return;
                }
                else if (mode == UPDATE_MODE)
                {
                    if (c == null || c.size() == 0)
                    {
                        Object[] obj =
                                {info[0]};
                        actionPerformed(obj);
                    }

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

    }
  //CCEnd by leixiao 2010-6-30 �򲹶�v4r3_p017_20100617

    /**
     * ������������Ϣ����ù��տ������а汾�е�����С�汾
     * @param masterInfo ��������Ϣ
     * @return ���տ������а汾�е�����С�汾
     */
    private QMFawTechnicsInfo getLastedVersion(QMFawTechnicsMasterInfo
                                               masterInfo)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel:getLastedIterations() begin...");
        }
        QMFawTechnicsInfo technicsInfo = null;
        //���÷��񷽷�����ù��տ�������С�汾��������ͬ��֦��
//        ServiceRequestInfo info1 = new ServiceRequestInfo();
        Class[] paraClass =
                {MasteredIfc.class};
        Object[] objs =
                {masterInfo};
        Collection collection = null;
        try
        {
            collection = (Collection) TechnicsAction.useServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            if (verbose)
            {
                ex.printStackTrace();
            }
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(parentJFrame,
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //��ù��տ�������С�汾
            technicsInfo = (QMFawTechnicsInfo) iterator.next();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel:getLastedIterations() end...return " +
                    technicsInfo);
        }
        return technicsInfo;
    }


    /**
     * ɾ����������
     * @param e ActionEvent
     */
    void deleteTechJButton_actionPerformed(ActionEvent e)
    {
        relationTechJTextField.setText("");
        //relatedTechnics = null;
        relatedTechnicsID = null;
        processHoursJPanel.setEnabled(true);
        setRelatedEff();
        //20080820
//        equiplinkJPanel.setMode("Edit");
//        equiplinkJPanel.repaint();
//        toollinkJPanel.setMode("Edit");
//        toollinkJPanel.repaint();
        //materiallinkJPanel.setMode("Edit");
        //materiallinkJPanel.repaint();
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.repaint();

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
        numJTextField.setText("");
        //add by wangh on 20070201
        descNumJTextField.setText("");
        descNumDisplayJLabel.setText("");

        numDisplayJLabel.setText("");
        this.relatedTechnicsID = null;
        //relatedTechnics = null;
        relationTechJTextField.setText("");
        relationTechDisJLabel.setText("");
        nameJTextField.setText("");
        nameDisplayJLabel.setText("");
        contentPanel.clearAll();
        drawingLinkPanel.clear();
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        stepTypeComboBox.setSelectedIndex(0);
        processTypeComboBox.setSelectedIndex(0);
        processHoursJPanel.clear();
        //20080820 xucy
        if(equiplinkJPanel != null)
        {
        	relationsJPanel.remove(equiplinkJPanel);
        	equiplinkJPanel = null;
        }
       
        if(toollinkJPanel != null)
        {
        	relationsJPanel.remove(toollinkJPanel);
        	toollinkJPanel = null;
        }
        if(materiallinkJPanel != null)
        {
        	relationsJPanel.remove(materiallinkJPanel);
        	materiallinkJPanel = null;
        }
        doclinkJPanel.clear();
        existProcessType = "";
        d = null;
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

        //����ʵ������������
        upJPanel.remove(contentPanel);
        //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
        contentPanel = new SpeCharaterTextPanel(parentJFrame,true);//CR7
        //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
        initSpeCharaterTextPanel();
        contentPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog", 0,
                18));

        contentPanel.setMaximumSize(new Dimension(32767, 80));
        contentPanel.setMinimumSize(new Dimension(10, 10));
        contentPanel.setPreferredSize(new Dimension(100, 50));
        upJPanel.add(contentPanel,
                     new GridBagConstraints(1, 6, 3, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(2, 8, 2, 7), 0, 0));
        upJPanel.repaint();
        paraJButton.setVisible(true);
        //20080811 �촺Ӣ�޸�
        //jTabbedPanel.setSelectedIndex(0);
    }


    /**
     * ����ģʽʱ���ô˷���.����ʵ��������ֵ����,�����ͼ����齨
     * �ı�part����,��̬����
     */
    public void refreshObject()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.refreshObject ");

        }
        String processType = stepType.getCodeContent().trim();

        if (!existProcessType.equals(processType))
        {
            if (getViewMode() == CREATE_MODE)
            { //����¼��Ĺ������࣬������ͬ�Ĺ���
                try
                {
                    QMProcedureInfo procedureinfo = CappServiceHelper.
                            instantiateQMProcedure(processType);
                    setProcedure(procedureinfo);
                }
                catch (ClassNotFoundException ex)
                {
                    if (verbose)
                    {
                        ex.printStackTrace();
                    }
                    String message = QMMessage.getLocalizedMessage(
                            RESOURCE, CappLMRB.SYSTEM_ERROR, null);
                    String title = QMMessage.getLocalizedMessage(
                            RESOURCE, CappLMRB.SAVE_STEP_FAILURE, null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  message, title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }
            }
            //add by wangh on 20070208
            newExtendPanel(processType);
          //20080820
            newEquiplinkJPanel(processType);
            if (equiplinkJPanel != null)
            {
            	equiplinkJPanel.setMode("View");
            }
            newToollinkJPanel(processType);
            if (toollinkJPanel != null)
            {
            	toollinkJPanel.setMode("View");
            }
            newMateriallinkJPanel(processType);
            if (materiallinkJPanel != null)
            {
            	materiallinkJPanel.setMode("View");
            }
            relationsJPanel.add(doclinkJPanel, "�ĵ�");
            relationsJPanel.add(drawingLinkPanel, "��ͼ");
            newPartlinkJPanel(processType);
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("Edit");
            }
            getProcedure().setExtendAttributes(null);
            existProcessType = processType;
        }
        if (processType != null)
        {
            paraJButton.setVisible(true);

        }

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.refreshObject end");
        }
    }


    /**
     * ����ʵ����part����
     * @param stepType String
     */
    private synchronized void newPartlinkJPanel(String stepType)
    {
        //��̬���ù���ʹ���㲿������
        String link = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + stepType);
        if (link == null || link.trim().equals("null") || link.equals(""))
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
            //CCBegin SS1
            partlinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            relationsJPanel.add(partlinkJPanel, "�㲿��");
        }
    }
    
    /**
     * ����ʵ����part����
     * @param stepType String
     */
    private  void newDoclinkJPanel(String stepType)
    {
        
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        
    }
    
    /**
     * ����ʵ����part����
     * @param stepType String
     * 20080820 xucy
     */
    private  void newEquiplinkJPanel(String stepType)
    {
       
            if (equiplinkJPanel != null)
            {
                relationsJPanel.remove(equiplinkJPanel);
            }
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType);
            //CCBegin SS1
            equiplinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            //���⣨7��20090108  �촺Ӣ�޸�    ��ʼ���豸��������ʱ��Ҳ��ʼ����װ�������   ��Ȼ����ֿ�ָ���쳣
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType);
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
     * ����ʵ����part����
     * @param stepType String
     * 20080820 xucy
     */
    private  void newToollinkJPanel(String stepType)
    {
       
            if (toollinkJPanel != null)
            {
                relationsJPanel.remove(toollinkJPanel);
            }
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType);
            //CCBegin SS1
            toollinkJPanel.setIsProcedure(true);
            //CCEnd SS1
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
     * ����ʵ����part����
     * @param stepType String
     */
    private  void newMateriallinkJPanel(String stepType)
    {
        
            if (materiallinkJPanel != null)
            {
                relationsJPanel.remove(materiallinkJPanel);
            }
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType);
            //CCBegin SS1
            materiallinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            //CR5 begin
            relationsJPanel.add(materiallinkJPanel, "����");
            //CR5 end        
    }
    
    /**
     * ɾ��ԭ���Ĳ��ţ�����ʵ������ԭ���ǹ��տ������п��ܸ��£�
     */
    private void changeWorkShopSortingSelectedPanel()
    {

        if (workshopSortingSelectedPanel != null)
        {
            upJPanel.remove(workshopSortingSelectedPanel);
            workshopSortingSelectedPanel = null;
        }
        //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
//        if (parentTechnics.getWorkShop() instanceof CodingClassificationIfc)
//        {
//            workshopSortingSelectedPanel = new CappSortingSelectedPanel(
//                    (CodingClassificationIfc) parentTechnics.getWorkShop());
//            String title4 = QMMessage.getLocalizedMessage(RESOURCE,
//                    CappLMRB.WORKSHOP, null);
//            workshopSortingSelectedPanel.setDialogTitle(title4);
//            workshopSortingSelectedPanel.setButtonSize(89, 23);
//            upJPanel.add(workshopSortingSelectedPanel,
//                         new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
//                                                , GridBagConstraints.WEST,
//                                                GridBagConstraints.HORIZONTAL,
//                                                new Insets(2, 0, 2, 8), 0, 0));
//            workshopSortingSelectedPanel.setSelectBMnemonic('W');
//            workshopDisJLabel.setVisible(false);
//        }
//        else
//        {
            workshopDisJLabel.setVisible(true);
            String workshop="";
            if (parentTechnics.getWorkShop() instanceof CodingClassificationIfc){
            	workshop=((CodingClassificationIfc) parentTechnics.getWorkShop()).getCodeSort();

            }
            else{
            	workshop=((CodingIfc) parentTechnics.getWorkShop()).getCodeContent();	
                
            }
            	
            workshopDisJLabel.setText(workshop);
            
//        }
          //CCEnd by leixiao 2010-4-1 ������ȡ���ղ���
    }


    public JPanel getPartLinkJPanel()
    {
        return partlinkJPanel;
    }

    public JPanel getEquipLinkJPanel()
    {
        return equiplinkJPanel;
    }
    /**
     * ��ʼ����������
     */
    public void initStepTypeTable()
    {
        String name = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.capp.util.CappLMRB", "86", null);
        String parent = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.capp.util.CappLMRB", "112", null);
        Class[] paraclass =
                {String.class, String.class};
        Object[] paraobj =
                {name, parent};
        Collection c = null;
        try
        {
            c = (Collection) useServiceMethod("CodingManageService",
                                              "getCoding",
                                              paraclass, paraobj);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }

        if (c != null && c.size() != 0)
        {
            stepTypetable = new Hashtable();
            for (Iterator i = c.iterator(); i.hasNext(); )
            {
                CodingIfc coding = (CodingIfc) i.next();
                stepTypetable.put(coding.getCodeContent(), coding);
            }
        }
    }

    public int getMode()
    {
        return mode;
    }


    /**
     * ���ù�������
     * @param codeContent String
     */
    public void setStepType(String codeContent)
    {
        stepType = (CodingIfc) stepTypetable.get(codeContent);
    }


    /**
     * Ϊ���������������������
     */
    private void initSpeCharaterTextPanel()
    {
        contentPanel.setDrawInfo(CappClientHelper.getSpechar());
        String path = RemoteProperty.getProperty("spechar.image.path");
        if (path == null)
        {
            contentPanel.setFilePath("/spechar/");
        }
        else
        {
            contentPanel.setFilePath(path.trim());
        }
    }

    
    /**
     * ����ѡ��ĵ�λ
     * @param comboBox �б��
     * @param coding ������
     */
    public void setComboBox(JComboBox box, CodingIfc coding)
    {
        int j = box.getItemCount();
        for (int i = 0; i < j; i++)
        {
            BaseValueIfc temp = (BaseValueIfc) box.getItemAt(i);
            if (coding != null && coding.getBsoID().equals(temp.getBsoID()))
            {
                box.setSelectedIndex(i);
                break;
            }
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


    void descNumJTextField_actionPerformed(ActionEvent e)
    {

    }
//CCBegin SS1
    
    public class ChuanDiActionListener implements ActionListener{
    	
    	
    	public ChuanDiActionListener(){
    		
    	}

    	public void actionPerformed(ActionEvent arg0) {
    		
    		//����
    		if(jTabbedPanel.getSelectedIndex()==1){
    			int contSelectRow=processControlJPanel.groupPanel.multiList.getSelectedRow();
    			int pfSelectRow=processFlowJPanel.groupPanel.multiList.getSelectedRow();
    			
    			if (contSelectRow != -1 && pfSelectRow != -1) {
					String chanpintx = processControlJPanel.groupPanel.multiList
							.getCellText(contSelectRow, 5);
					String guochengtx = processControlJPanel.groupPanel.multiList
							.getCellText(contSelectRow, 6);
					
					String texingfl = processControlJPanel.groupPanel.multiList.
					                              getCellAt(contSelectRow, 7).getSpecialCharacter().save();
					

					processFlowJPanel.groupPanel.multiList.addTextCell(
							pfSelectRow, 9, chanpintx);
					processFlowJPanel.groupPanel.multiList.addTextCell(
							pfSelectRow, 10, guochengtx);
//					CCBegin SS5
//					processFlowJPanel.groupPanel.multiList.addTextCell(
//							pfSelectRow, 11, texingfl);
					Vector vc = new Vector();
					vc.add(texingfl);
					processFlowJPanel.groupPanel.multiList.addSpeCharCell(pfSelectRow, 11, vc);
//					CCEnd SS5
				}
    			
    		}
    		//����FMEA
    		else if(jTabbedPanel.getSelectedIndex()==2){
    			
    			int contSelectRow=processControlJPanel.groupPanel.multiList.getSelectedRow();
    			int fmSelectRow=femaJPanel.groupPanel.multiList.getSelectedRow();
    			
    			if(contSelectRow!=-1&&fmSelectRow!=-1){
    				
    				String yanzhongdu = processControlJPanel.groupPanel.multiList.
                    getCellAt(contSelectRow, 7).getSpecialCharacter().save();
    				femaJPanel.groupPanel.multiList.addTextCell(
    						fmSelectRow, 4, yanzhongdu);


    			}
    			
    			
    		}
    		//���Ƽƻ�
    		else if(jTabbedPanel.getSelectedIndex()==3){
    			
    			
    			int contSelectRow=processControlJPanel.groupPanel.multiList.getSelectedRow();
    			int pfSelectRow=processFlowJPanel.groupPanel.multiList.getSelectedRow();
    			
    			if (contSelectRow != -1 && pfSelectRow != -1) {
					String chanpintx = processFlowJPanel.groupPanel.multiList
							.getCellText(pfSelectRow, 9);
					String guochengtx = processFlowJPanel.groupPanel.multiList
							.getCellText(pfSelectRow, 10);
//					CCBegin SS5
//					String texingfl = processFlowJPanel.groupPanel.multiList
//							.getCellText(pfSelectRow, 11);
					String texingfl = processFlowJPanel.groupPanel.multiList.
							getCellAt(pfSelectRow, 11).getSpecialCharacter().save();
//					CCEnd SS5
					

					processControlJPanel.groupPanel.multiList.addTextCell(
							contSelectRow, 5, chanpintx);
					processControlJPanel.groupPanel.multiList.addTextCell(
							contSelectRow, 6, guochengtx);
//					CCBegin SS5
//					processControlJPanel.groupPanel.multiList.addTextCell(
//							contSelectRow, 7, texingfl);
					Vector vc = new Vector();
					vc.add(texingfl);
					processControlJPanel.groupPanel.multiList.addSpeCharCell(contSelectRow, 7, vc);
//					CCEnd SS5
				}
    			
    			
    		}
    		
    	}
    	
    	

    }
    
    
//CCEnd SS1

    /**
     * <p>Title:�����߳� </p>
     * <p>Description: ���ڵ��ü�ͼ�����Ż����¹��գ�����ٶ�</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author Ѧ�� 2008 04 28 ���
     * @version 1.0
     */
//    class DrawThread extends Thread
//    {
//        public void run()
//        {
//            drawingLinkPanel.setProcedure(getProcedure());
//        }
//    }
}
