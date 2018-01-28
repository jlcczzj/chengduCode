package com.faw_qm.cappclients.capp.view;
//SS1 �����������ĵ�ת�����ĵ�master�͹���Ĺ��� Liuyang 2013-3-19
//SS2 ��Ź�ʱĬ��Ϊ��ͱ����乤ʱĬ��Ϊ��leixiao 2013-9-11
// SS3 ���������ӹ������ۼӵ����򡱲��Ҳ��ڹ�������ʾ leixiao 2013-10-14  
// SS4 �����乤����Դ�����ܵ����� liunan 2014-5-19
//SS5 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2843��������  2014-05-06
//SS6 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2842��������  2014-05-21
//SS7 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2923��������  2014-6-5
//SS8 �޸ĵ����Ƿ񱣴���ʾ�����Ǳ��治�ϼ�ͼ���⡣
//SS9 ���Ӽ�ͼ˳��ŵ��ظ���� liunan 2014-7-1
//SS10 �޸ķ���ƽ̨������A005-2014-2964
//SS11 �����乤������������  guoxiaoliang 2014-03-10
//SS12 ������Դ����˳�� liunan 2015-3-17
//SS13 ���Ƽƻ�����Դ�빤������Դ������һ�£�3�й�װά����14�������ά����û��ͬ�����¹�������Դ������ liunan 2015-3-19
//SS14 ƽ̨���⣺A005-2015-3041 ����ż�鲻�����ָ�����ʾ�� liunan 2015-3-24
import java.awt.Cursor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;

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
import com.faw_qm.capp.model.procedure.consQMAssembleProcedureInfo;
import com.faw_qm.capp.model.procedure.consQMMachineProcedureInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsLogic;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelForBSX;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.TermTextField;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
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
import com.faw_qm.framework.remote.ServiceRequestInfo;
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
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.model.WorkableIfc;




public class TechnicsStepJPanelForBSX extends TechnicsStepJPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


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
//    private JLabel workshopJLabel = new JLabel();
//    private JLabel workshopDisJLabel = new JLabel();


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
    private ProcedureUsageToolJPanel toollinkJPanel;


    /**���Ϲ�����ά�����*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**�ĵ�������ά�����*/
    private ProcedureUsageDocJPanel doclinkJPanel = new ProcedureUsageDocJPanel();


    /**��ⰴť*/
    //private JButton storageJButton = new JButton();

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
    private CappExAttrPanel processFlowJPanel;
    private CappExAttrPanel femaJPanel;
    //CCBegin SS11
//    private CappExAttrPanel processControlJPanel;
    private CappExAttrPanelForBSX processControlJPanel;
    //CCEnd SS11

  //add by wangh on 20070326(�Ƿ���ʾTS16949�Ĺ�����߹�����Ϣ��)
    private static boolean ts16949 = (RemoteProperty.getProperty(
        "TS16949", "true")).equals("true");

//CCBegin SS11
    
    private Vector eqVec=new Vector();
    private Vector toolVec=new Vector();
    private Vector materiaVec=new Vector();
    CappAssociationsLogic taskLogic ;
    
    private Vector eqDeleVec=new Vector();
    private Vector toolDeleVec=new Vector();
    private Vector materiaDeleVec=new Vector();
//CCEnd SS11

    /**
     * ���캯��
     * @param parent ���ñ���ĸ�����
     */
    public TechnicsStepJPanelForBSX(JFrame parent)
    {
    	super();
        try
        {
            parentJFrame = parent;
            //CCBegin by chudaming 20090916  �ڹ���������������ַ�����99
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    RESOURCE, "procedureName", null), 99, false);
          //CCEnd by chudaming 20090916
            contentPanel = new SpeCharaterTextPanel(parent);
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
    public TechnicsStepJPanelForBSX(JFrame parent, BaseValueIfc technics)
    {
    	super();
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    RESOURCE, "procedureName", null), 40, false);
            contentPanel = new SpeCharaterTextPanel(parent);
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

        processTypeSortingSelectedPanel = new CappSortingSelectedPanel(title2,
                "QMProcedure", "processType");
        stepClassifiSortingSelectedPanel = new CappSortingSelectedPanel(title3,
                "QMProcedure", "stepClassification");

        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame);
        processTypeSortingSelectedPanel.setDialogTitle(title2);
        stepClassifiSortingSelectedPanel.setDialogTitle(title3);
        processTypeSortingSelectedPanel.setButtonSize(89, 23);
        stepClassifiSortingSelectedPanel.setButtonSize(89, 23);

        processTypeSortingSelectedPanel.setSelectBMnemonic('R');
        stepClassifiSortingSelectedPanel.setSelectBMnemonic('G');

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
        descNumJTextField = new CappTextField(parentJFrame, procedureNumDisp1, 10, false);

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
        stepClassifiJLabel.setText("�������");
        processTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        processTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        processTypeJLabel.setText("*�ӹ�����");
//        workshopJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        workshopJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//        workshopJLabel.setText("*����");
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
        //CCBegin SS2
        processHoursJPanel = new ProcessHoursJPanel(parentJFrame,true);
        //CCEnd SS2
//        storageJButton.setMaximumSize(new Dimension(97, 23));
//        storageJButton.setMinimumSize(new Dimension(97, 23));
//        storageJButton.setPreferredSize(new Dimension(97, 23));
//        storageJButton.setMnemonic('T');
//        storageJButton.setText("���");
//        storageJButton.addActionListener(new java.awt.event.ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                storageJButton_actionPerformed(e);
//            }
//        });
        relationTechJTextField.setEditable(false);
        relationTechJTextField.addCaretListener(new javax.swing.event.
                                                CaretListener()
        {
            public void caretUpdate(CaretEvent e)
            {
                relationTechJTextField_caretUpdate(e);
            }
        });
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
        stepClassiDisJLabel.setMaximumSize(new Dimension(4, 22));
        stepClassiDisJLabel.setMinimumSize(new Dimension(4, 22));
        stepClassiDisJLabel.setPreferredSize(new Dimension(4, 22));
//        workshopDisJLabel.setMaximumSize(new Dimension(4, 22));
//        workshopDisJLabel.setMinimumSize(new Dimension(4, 22));
//        workshopDisJLabel.setPreferredSize(new Dimension(4, 22));
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
      descNumJTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        descNumJTextField_actionPerformed(e);
      }
    });
//    descNumJTextField.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        descNumJTextField_actionPerformed(e);
//      }
//    });

        scrollpane.setBorder(null);
        extendJPanel.add(scrollpane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
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
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 7), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(nameDisplayJLabel,
                      new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(7, 8, 0, 7), 0, 0));

        //���ڸ��ºͲ鿴ģʽ
        upJPanel.add(stepTypeDisJLabel,
                     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));

        //���ڲ鿴ģʽ
        upJPanel.add(processTypeDisJLabel,
                      new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(7, 8, 0, 0), 0, 0));

        upJPanel.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(7, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumJTextField,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));


        //���ڲ鿴ģʽ
        upJPanel.add(numDisplayJLabel,
                     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(10, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumDisplayJLabel,
                      new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 8, 0, 0), 0, 0));


        //���ڲ鿴ģʽ

        upJPanel.add(relationTechJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 20, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(stepClassiDisJLabel,
                      new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 8, 0, 7), 0, 0));

        upJPanel.add(stepClassifiJLabel,
                      new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 0, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
//        upJPanel.add(workshopDisJLabel,
//                     new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
//                                            , GridBagConstraints.WEST,
//                                            GridBagConstraints.BOTH,
//                                            new Insets(7, 8, 0, 7), 0, 0));
//
//        upJPanel.add(workshopJLabel,
//                     new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
//                                            , GridBagConstraints.EAST,
//                                            GridBagConstraints.NONE,
//                                            new Insets(7, 0, 0, 0), 0, 0));
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
        upJPanel.add(nameJLabel,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(numJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(7, 21, 0, 0), 0, 0));

            //add by wangh on 20070131
            upJPanel.add(descStepNumberJLabel,
                          new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 21, 0, 0), 0, 0));


        upJPanel.add(rbJPanel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(10, 0, 0, 7), 0, 0));

        upJPanel.add(contentJLabel,   new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(7, 0, 0, 0), 0, 0));
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
//        buttonJPanel.add(storageJButton,
//                         new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
//                                                , GridBagConstraints.WEST,
//                                                GridBagConstraints.NONE,
//                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(jPanel1, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
            //add by wangh on 20070202
            this.add(jTabbedPanel,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 2, 0, 0), 0, 0));

        //add by wangh on 20070201(��������Ϣ,��������,����FMEA�Ϳ��Ƽƻ�Panel���뵽jTabbedPanel��)
        jTabbedPanel.add(extendJPanel, "������Ϣ");
        if (ts16949) {
			// jTabbedPanel.add(extendJPanel2, "��������");
			// jTabbedPanel.add(extendJPanel3, "����FMEA");
        	
        	//CCBegin SS11
			Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
					.getSelectedObject().getObject();

			String techType = "";
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
				
				jTabbedPanel.add(extendJPanel4, "���Ƽƻ�");
				jTabbedPanel.setSelectedIndex(1);
			}
			//CCEnd SS11
		}
        upJPanel.add(stepClassifiSortingSelectedPanel,
                      new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 0, 2, 8), 0, 0));
        upJPanel.add(processTypeSortingSelectedPanel,
                      new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

        scrollpane.getViewport().add(splitJPanel, null);

        localize();
        initStepTypeTable();
        splitJPanel.setDividerLocation(300);
        //CCBegin SS11
        taskLogic = new CappAssociationsLogic();
        //CCEnd SS11

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
            stepClassifiJLabel.setText("�������");
            processTypeJLabel.setText("*�ӹ�����");
            //workshopJLabel.setText("*����");
            //Ѧ���޸� 20080219 �޸�ԭ���ڹ��չ�����ڹ����´�������,������Ϊ�������ֶ�ǰ��*
           descStepNumberJLabel.setText("*������");
            //Ѧ���޸Ľ���
//            contentJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "mtechContentJLabel", null));
           contentJLabel.setText("��������");
            //JButton
            paraJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "ParaJButton", null));
            saveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "QuitJButton", null));
//            storageJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "storageJButton", null));
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
     * �����ڹ�������ѡ��Ľڵ�
     * @param parentnode
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setTechnics();
    }


    /**
     * ��ȡ��ǰѡ��Ĺ���Ĺ��տ�ͷ
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
//        changeWorkShopSortingSelectedPanel();
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
        stepClassifiSortingSelectedPanel.setVisible(true);
        stepClassiDisJLabel.setVisible(false);
        processTypeSortingSelectedPanel.setVisible(true);
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
        processTypeSortingSelectedPanel.setDefaultCoding(processType,
                stepType.getCodeContent());
        String stepClassi = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.STEPCLASSIFI, null);
        stepClassifiSortingSelectedPanel.setDefaultCoding(stepClassi,
                stepType.getCodeContent());
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
//CCBegin SS11
    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setCreateModeForBSX()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setCreateMode() begin...");
        }
        //CCBegin SS7
//        clear();
        clearForBsx();
        //CCEnd SS7
        //CCBegin SS5
        //CCBegin SS11
        refreshObjectForBSX();
        //CCEnd SS11
      //CCEnd SS5
//        changeWorkShopSortingSelectedPanel();
      
      //CCBegin SS5
        descNumJTextField.setEditable(false);
        numJTextField.setEditable(false);
        nameJTextField.setEditable(false);
        //CCEnd SS5
        
        
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
        
        //CCBegin SS7
       
        processControlJPanel.getStepProcessControlJPanelForBSX().stepTypeDisJLabel.setText(stepType.getCodeContent());
        
        //CCEnd SS7
        
        //CCBegin SS5
        stepClassifiSortingSelectedPanel.setVisible(false);
        stepClassiDisJLabel.setVisible(true);
        processTypeSortingSelectedPanel.setVisible(false);
        processTypeDisJLabel.setVisible(true);
        //CCEnd SS5
        //workshopSortingSelectedPanel.setVisible(true);
        // workshopDisJLabel.setVisible(false);
        //CCBegin SS5
        contentPanel.setEditable(false);
       
        drawingLinkPanel.setModel(1); //EDIT
        
        doclinkJPanel.setMode("View");
      //CCEnd SS5
        //20080820
        //equiplinkJPanel.setMode("Edit");
        //toollinkJPanel.setMode("Edit");
        //materiallinkJPanel.setMode("Edit");
        f1.setEditMode();
        //CCBegin SS5
        processHoursJPanel.setEnabled(false);
        //CCEnd SS5
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
                //CCBegin SS11
                processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText(String.valueOf(getStepInitNumber()));
                processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText(String.valueOf(getStepInitNumber()));
                //CCEnd SS11
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
                
                //CCBegin SS11
                processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText(String.valueOf(number + getStepLong()));
                processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText(String.valueOf(number + getStepLong()));
                //CCEnd SS11

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
        processTypeSortingSelectedPanel.setDefaultCoding(processType,
                stepType.getCodeContent());
        String stepClassi = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.STEPCLASSIFI, null);
        stepClassifiSortingSelectedPanel.setDefaultCoding(stepClassi,
                stepType.getCodeContent());
        String workshop = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.WORKSHOP, null);
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setDefaultCoding(workshop,
                    stepType.getCodeContent());
        }
        
        setButtonVisible(true);
        // paraJButton.setVisible(false);
        //CCBegin SS11
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setMode("Edit");
        //CCEnd SS11
     
        repaint();
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setCreateMode() end...return is void");
        }
    }
    //CCEnd SS11
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
        
//        changeWorkShopSortingSelectedPanel();
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        
       
        
        //������
        numJTextField.setVisible(true);
        numJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
        numDisplayJLabel.setVisible(false);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(String.valueOf(getProcedure().getDescStepNumber()));
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
        //�������
        stepClassifiSortingSelectedPanel.setVisible(true);
        //CCBegin by chudaming 2009-2-19 bsx ���¹����ǹ��������ʾ
        if(getProcedure().getStepClassification()==null||getProcedure().getStepClassification().equals("")){
          stepClassiDisJLabel.setText("");
         }
         else{
           stepClassifiSortingSelectedPanel.setCoding(getProcedure()
                                                  .getStepClassification());
         }
         //CCEnd by chudaming 2009-2-19 bsx ���¹����ǹ��������ʾ
        stepClassiDisJLabel.setVisible(false);
        //�ӹ�����
        processTypeSortingSelectedPanel.setVisible(true);
        processTypeSortingSelectedPanel.setCoding(getProcedure().
                                                  getProcessType());
        
        processTypeDisJLabel.setVisible(false);
        //����
//        if (workshopSortingSelectedPanel != null)
//        {
//            workshopSortingSelectedPanel.setCoding(getProcedure().
//                    getWorkShop());
//            workshopDisJLabel.setVisible(false);
//        }
//        else
//        {
//            workshopDisJLabel.setVisible(true);
//            workshopDisJLabel.setText(getProcedure().getWorkShop().
//                                      getCodeContent());
//        }

        drawingLinkPanel.setModel(2); //EDIT
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
        //CCBegin by liunan 2011-6-1 �Ż�
        //add by guoxl on 2009-1-7(�����½�����Ӽ��������������Ϣ�ı�������Ƿ񱣴���ʾ��������ʾ)
        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
        //add by guoxl end
        //CCEnd by liunan 2011-6-1
    }
    
    
    //CCBegin SS11
    /**
     * ���ý���Ϊ����ģʽ����������������ʾ�ڽ�����
     */
    private void setUpdateModeForBSX()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setUpdateMode() begin...");
        }
        clear();
        
     
        //CCBegin SS11
        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());
        this.setProcedure((QMProcedureInfo) selectedNode.getObject().getObject());
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().newPartlinkJPanel(getProcedure().getTechnicsType().getCodeContent());
        //CCEnd SS11
//        changeWorkShopSortingSelectedPanel();
        
        
       
        
        //������
        numJTextField.setVisible(true);
        numJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
        
        //CCBegin SS5
        numJTextField.setEditable(false);
        descNumJTextField.setEditable(false);
        nameJTextField.setEditable(false);
        //CCEnd SS5
        
        numDisplayJLabel.setVisible(false);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(String.valueOf(getProcedure().getDescStepNumber()));
        descNumDisplayJLabel.setVisible(false);
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                getCodeContent());
        //CCBegin SS11
        processControlJPanel.getStepProcessControlJPanelForBSX().stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                getCodeContent());
        processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText(Integer.toString(getProcedure().getStepNumber()));
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText(String.valueOf(getProcedure().getDescStepNumber()));
        
        //CCEnd SS11
        
        
        
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
        //CCBegin SS11
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setText(name);
        //CCEnd SS11
        nameDisplayJLabel.setVisible(false);
        //�������
        //CCBegin SS5
        stepClassifiSortingSelectedPanel.setVisible(false);
        //CCEnd SS5
        //CCBegin by chudaming 2009-2-19 bsx ���¹����ǹ��������ʾ
        if(getProcedure().getStepClassification()==null||getProcedure().getStepClassification().equals("")){
          stepClassiDisJLabel.setText("");
          //CCBegin SS11
          processControlJPanel.getStepProcessControlJPanelForBSX(). stepClassiDisJLabel.setText("");
          //CCEnd SS11
         }
         else{
           stepClassifiSortingSelectedPanel.setCoding(getProcedure()
                                                  .getStepClassification());
           //CCBegin SS11
           processControlJPanel.getStepProcessControlJPanelForBSX(). stepClassifiSortingSelectedPanel.setCoding(getProcedure()
                   .getStepClassification());
           //CCEnd SS11
         }
      //CCEnd by chudaming 2009-2-19 bsx ���¹����ǹ��������ʾ
        //CCBegin SS5
        stepClassiDisJLabel.setVisible(true);
        if(getProcedure().getStepClassification()==null||getProcedure().getStepClassification().equals("")){
            stepClassiDisJLabel.setText("");
           }
           else{
             stepClassiDisJLabel.setText(
                   getProcedure().getStepClassification().getCodeContent());
           }
        
        //CCEnd SS5
        
        //�ӹ�����
        //CCBegin SS5
        processTypeSortingSelectedPanel.setVisible(false);
        //CCEnd SS5
        processTypeSortingSelectedPanel.setCoding(getProcedure().
                                                  getProcessType());
        //CCBegin SS11
        processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.setCoding(getProcedure().
                getProcessType());
        //CCEnd SS11
        //CCBegin SS5
        processTypeDisJLabel.setVisible(true);
        processTypeDisJLabel.setText(getProcedure().getProcessType().
                getCodeContent());
        
        //CCEnd SS5
        
        //����
//        if (workshopSortingSelectedPanel != null)
//        {
//            workshopSortingSelectedPanel.setCoding(getProcedure().
//                    getWorkShop());
//            workshopDisJLabel.setVisible(false);
//        }
//        else
//        {
//            workshopDisJLabel.setVisible(true);
//            workshopDisJLabel.setText(getProcedure().getWorkShop().
//                                      getCodeContent());
//        }
     
        //CCBegin SS5
        drawingLinkPanel.setModel(1); //EDIT
        //CCEnd SS5
        //Ѧ�� �޸� 20080428 �����Ż����¹��գ�����ٶ�
        drawingLinkPanel.setProcedure(getProcedure());         
        
        //CCBegin SS11
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setModel(2);
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setProcedure(getProcedure());            
        //CCEnd SS11
//        DrawThread dt = new DrawThread();
//        dt.start();
        //Ѧ�� �޸Ľ��� 20080428

        //��������
        //CCBegin SS5
        contentPanel.setEditable(false);
        
        //CCEnd SS5
        
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.clearAll();
            contentPanel.resumeData(v);
        }
        //��ʱ
        processHoursJPanel.setProcedure(getProcedure());
       
        //CCBegin SS11
        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
        //CCEnd SS11
       
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
        //CCBegin SS5
        doclinkJPanel.setMode("View");
        //CCEnd SS5
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        //CCBegin SS11
        processControlJPanel.getStepLinkSouseControlJPanelForBSX(). doclinkJPanel.setMode("Edit");
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setProcedure(getProcedure());
        //CCEnd SS11
        //CCBegin SS5
        drawingLinkPanel.setModel(1); //EDIT
        //CCEnd SS5
        //CCBegin SS11
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setModel(2); 
        //CCEnd SS11
        relationsJPanel.add(drawingLinkPanel, "��ͼ");
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());
            //CCBegin SS11

            processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setProcedure(getProcedure());
            //CCEnd SS11

        }
        //�����Ƿ���й������գ�ȷ��processHoursJPanel�͸���������״̬
      //CCBegin SS5
        setRelatedEffforBSX();
        processHoursJPanel.setViewMode();
        //CCEnd SS5
        //modify by wangh on 20070615(ȥ�����й�������ʱ�ĵ����ֶ��ఴŦ)
        //doclinkJPanel.setMode("Edit");
        f1.setEditMode();
        //add by wangh on 20070310
      //CCBegin SS11
        if(processControlJPanel!=null)
            processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setMode("Edit");
        //CCEnd SS11

        setButtonVisible(true);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setUpdateMode() end...return is void");
        }
        //CCBegin by liunan 2011-6-1 �Ż�
        //add by guoxl on 2009-1-7(�����½�����Ӽ��������������Ϣ�ı�������Ƿ񱣴���ʾ��������ʾ)
        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
        //add by guoxl end
        //CCEnd by liunan 2011-6-1
        
        //CCBegin SS10
        //CCBegin SS6
        int conRows=processControlJPanel.groupPanel.multiList.getRowCount();
        if(conRows==0){
        	
        	
        	try {
        		
				Vector OldEqLinkVec=equiplinkJPanel.getAllLinks();
//				System.out.println("equiplinkJPanel============11111111111111111111==============================="+equiplinkJPanel);
//				System.out.println("toollinkJPanel============11111111111111111111==============================="+toollinkJPanel);
				Vector OldToolLinkVec=toollinkJPanel.getAllLinks();
				
				Vector oldMaterLinkVec=materiallinkJPanel.getAllLinks();
					
//				System.out.println("OldEqLinkVec============22222222222222222222==============================="+OldEqLinkVec.size());
//				System.out.println("OldToolLinkVec============22222222222222222222==============================="+OldToolLinkVec.size());
				
				
//				Vector OldMateriaLinkVec=materiallinkJPanel.getAllLinks();
				
				
				int nowRow=0;
				if(OldEqLinkVec.size()!=0){
					
					for(int a=0;a<OldEqLinkVec.size();a++){
						
					   QMProcedureQMEquipmentLinkInfo oldlinkInfo=(QMProcedureQMEquipmentLinkInfo)OldEqLinkVec.get(a);
					   
					   
					   for (int b = 0; b < 4; b++) {
						   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
							processControlJPanel.groupPanel.addNewRow();
							processControlJPanel.groupPanel.multiList.getTable().clearSelection();
							
							nowRow++;
						}
//					   System.out.println("a====================================="+a);
//					   System.out.println("nowRow================================"+nowRow);
					   
					   QMEquipmentInfo eqInfo = new QMEquipmentInfo();
						eqInfo.setBsoID(oldlinkInfo.getRightBsoID());

						eqInfo = (QMEquipmentInfo) this
								.refreshInfo(eqInfo);
						
						

						if(a==0){
							
							processControlJPanel.groupPanel.multiList
							.addTextCell(0, 20, eqInfo.getBsoID());
						
						  processControlJPanel.groupPanel.multiList
								.addTextCell(0, 1, eqInfo
										.getEqNum());
						  
						   
						  String eqModel=eqInfo.getEqModel();
						  if(eqModel==null||eqModel.equals(""))
							  processControlJPanel.groupPanel.multiList
								.addTextCell(1, 1, "--");
							  
						  else	  
						    processControlJPanel.groupPanel.multiList
							.addTextCell(1, 1, eqInfo.getEqModel());
						    
						    processControlJPanel.groupPanel.multiList
							.addTextCell(2, 1, eqInfo
									.getEqName());
						    
						    String eqEqManu=eqInfo.getEqManu();
							 if(eqEqManu==null||eqEqManu.equals(""))
								 processControlJPanel.groupPanel.multiList
								   .addTextCell(3, 1, "--");
							 else	 
							     processControlJPanel.groupPanel.multiList
								   .addTextCell(3, 1, eqInfo.getEqManu());
						    
						    processControlJPanel.groupPanel.multiList.setSelectedRow(4);
							
						}
						else
						{
							
							
						  processControlJPanel.groupPanel.multiList
							.addTextCell(nowRow-4, 20, eqInfo.getBsoID());
						
						  processControlJPanel.groupPanel.multiList
								.addTextCell(nowRow-4, 1, eqInfo
										.getEqNum());
						  String eqModel=eqInfo.getEqModel();
						  if(eqModel==null||eqModel.equals(""))
							  processControlJPanel.groupPanel.multiList
								.addTextCell(nowRow-3, 1, "--");
							  
						  else	  
						    processControlJPanel.groupPanel.multiList
							.addTextCell(nowRow-3, 1, eqInfo.getEqModel());
						  
						 processControlJPanel.groupPanel.multiList
								.addTextCell( nowRow-2, 1, eqInfo
										.getEqName());
						   
						   
						 String eqEqManu=eqInfo.getEqManu();
						 if(eqEqManu==null||eqEqManu.equals(""))
							 processControlJPanel.groupPanel.multiList
							   .addTextCell(nowRow-1, 1, "--");
						 else	 
						     processControlJPanel.groupPanel.multiList
							   .addTextCell(nowRow-1, 1, eqInfo.getEqManu());
						 
						 processControlJPanel.groupPanel.multiList.setSelectedRow(nowRow-1);
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
						conRows=processControlJPanel.groupPanel.multiList.getRowCount();
						if(conRows==0){
							
							if (!toolInfo.getToolCf().getCodeContent().equals("����")
									&& !toolInfo.getToolCf().getCodeContent().equals("��������")
									&& !toolInfo.getToolCf().getCodeContent().equals("ר������")
									&& !toolInfo.getToolCf().getCodeContent().equals("���")
									&& !toolInfo.getToolCf().getCodeContent().equals("���鸨��")
									&& !toolInfo.getToolCf().getCodeContent().equals("����")
									&& !toolInfo.getToolCf().getCodeContent().equals("����о�")
									&& !toolInfo.getToolCf().getCodeContent().equals("����������")) {
								
								       processControlJPanel.groupPanel.multiList.getTable().clearSelection();
								       processControlJPanel.groupPanel.addNewRow();
								       processControlJPanel.groupPanel.multiList.getTable().clearSelection();
								
							           processControlJPanel.groupPanel.multiList.addTextCell(i, 22, toolInfo.getBsoID());
							           processControlJPanel.groupPanel.multiList.addTextCell(i, 2, toolInfo.getToolName());
							           processControlJPanel.groupPanel.multiList.addTextCell(i, 3, toolInfo.getToolNum());
							           processControlJPanel.groupPanel.multiList.addTextCell(i, 4, toolInfo.getToolSpec());
							           processControlJPanel.groupPanel.multiList.addTextCell(i, 5, oldToolLinkInfo.getUsageCount());
							}else{
								for(int n=0;n<3;n++){
									
									processControlJPanel.groupPanel.multiList.getTable().clearSelection();
									 processControlJPanel.groupPanel.addNewRow();
									 processControlJPanel.groupPanel.multiList.getTable().clearSelection();
									 nowRow++;
								}
								if(i==0){
								       processControlJPanel.groupPanel.multiList.addTextCell(0, 23, toolInfo.getBsoID());
								       processControlJPanel.groupPanel.multiList.addTextCell(0, 14, toolInfo.getToolNum());
						               processControlJPanel.groupPanel.multiList.addTextCell(1, 14, toolInfo.getToolName());
						               processControlJPanel.groupPanel.multiList.addTextCell(2, 14, toolInfo.getToolSpec());
								}else{
									   processControlJPanel.groupPanel.multiList.addTextCell(nowRow-3, 23, toolInfo.getBsoID());
									   processControlJPanel.groupPanel.multiList.addTextCell(nowRow-3, 14, toolInfo.getToolNum());
							           processControlJPanel.groupPanel.multiList.addTextCell(nowRow-2, 14, toolInfo.getToolName());
							           processControlJPanel.groupPanel.multiList.addTextCell(nowRow-1, 14, toolInfo.getToolSpec());
									
								}
								
							}
							
						}else{
							
							 int rows= processControlJPanel.groupPanel.multiList.getRowCount();
							
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
									   
							          String rowValue= processControlJPanel.groupPanel.multiList.getCellText(aa, 3);
							          
							          if(rowValue!=null&&rowValue.equals("")){
							        	  
							        	  nullRow=aa;
							        	  
							        	  break;
							          }else{
							        	  
							        	  nullRow=-1;
							          }
							          
								   }
							       
							       
							       if(nullRow!=-1){
							    	   processControlJPanel.groupPanel.multiList.addTextCell(nullRow, 22, toolInfo.getBsoID());
							           processControlJPanel.groupPanel.multiList.addTextCell(nullRow, 2, toolInfo.getToolName());
							           processControlJPanel.groupPanel.multiList.addTextCell(nullRow, 3, toolInfo.getToolNum());
							           processControlJPanel.groupPanel.multiList.addTextCell(nullRow, 4, toolInfo.getToolSpec());
							           processControlJPanel.groupPanel.multiList.addTextCell(nullRow, 5, oldToolLinkInfo.getUsageCount());
							    	   
							       }
							       else{
							    	   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
							    	   processControlJPanel.groupPanel.addNewRow();
							    	   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
							    	   
							    	   int allRow= processControlJPanel.groupPanel.multiList.getRowCount();
							    	   
							    	   processControlJPanel.groupPanel.multiList.addTextCell(allRow-1, 22, toolInfo.getBsoID());
							           processControlJPanel.groupPanel.multiList.addTextCell(allRow-1, 2, toolInfo.getToolName());
							           processControlJPanel.groupPanel.multiList.addTextCell(allRow-1, 3, toolInfo.getToolNum());
							           processControlJPanel.groupPanel.multiList.addTextCell(allRow-1, 4, toolInfo.getToolSpec());
							           processControlJPanel.groupPanel.multiList.addTextCell(allRow-1, 5, oldToolLinkInfo.getUsageCount());
							    	   
							    	   
							       }
								    
							}else{
//								 String rowValue= processControlJPanel.groupPanel.multiList.getCellText(i,14);
								  int nullRow=0;
								   for(int aa=0;aa<rows;aa++){
									   
							          String rowValue= processControlJPanel.groupPanel.multiList.getCellText(aa, 14);
							          
							          if(rowValue!=null&&rowValue.equals("")){
							        	  
							        	  nullRow=aa;
							        	  
							        	  break;
							          }else{
							        	  
							        	  nullRow=-1;
							          }
							          
							          
								   }
								   
								 
									   
									   if(nullRow==-1){
										   
										   for(int n=0;n<3;n++){
											   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
												 processControlJPanel.groupPanel.addNewRow();
												 processControlJPanel.groupPanel.multiList.getTable().clearSelection();
												 nowRow++;
											}
										   
								    	   processControlJPanel.groupPanel.multiList.addTextCell(nowRow-3, 23, toolInfo.getBsoID());
								           processControlJPanel.groupPanel.multiList.addTextCell(nowRow-3, 14, toolInfo.getToolName());
								           processControlJPanel.groupPanel.multiList.addTextCell(nowRow-2, 14, toolInfo.getToolNum());
								           processControlJPanel.groupPanel.multiList.addTextCell(nowRow-1, 14, toolInfo.getToolSpec());
								    	   
								       }
								       else{
								    	   
								    	   
								    	   int allRow= processControlJPanel.groupPanel.multiList.getRowCount();
								    	   
								    	   processControlJPanel.groupPanel.multiList.addTextCell(nullRow, 23, toolInfo.getBsoID());
								    	   processControlJPanel.groupPanel.multiList.addTextCell(nullRow,14, toolInfo.getToolNum());
								          
								           String nextvalue=processControlJPanel.groupPanel.multiList.getCellText(nullRow+1, 14);
								           if(nextvalue==null){
								        	   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
								        	   processControlJPanel.groupPanel.addNewRow();
								        	   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
								           }
								          
								           processControlJPanel.groupPanel.multiList.addTextCell(nullRow+1,14, toolInfo.getToolSpec());
								           nextvalue=processControlJPanel.groupPanel.multiList.getCellText(nullRow+2, 14);
								           if(nextvalue==null){
								        	   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
								        	   processControlJPanel.groupPanel.addNewRow();
								        	   processControlJPanel.groupPanel.multiList.getTable().clearSelection();
								           }
								           processControlJPanel.groupPanel.multiList.addTextCell(nullRow+2, 14, toolInfo.getToolName());
								           
								    	   
								    	   
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
						conRows = processControlJPanel.groupPanel.multiList
								.getRowCount();

						if (conRows == 0) {
							processControlJPanel.groupPanel.multiList.getTable().clearSelection();
							processControlJPanel.groupPanel.addNewRow();
							processControlJPanel.groupPanel.multiList.getTable().clearSelection();

							processControlJPanel.groupPanel.multiList
									.addTextCell(i, 21, materialInfo.getBsoID());
							processControlJPanel.groupPanel.multiList
									.addTextCell(i, 1, materialInfo
											.getMaterialName());

						} else {
							int rows = processControlJPanel.groupPanel.multiList
									.getRowCount();
							int nullRow = 0;
							for (int aa = 0; aa < rows; aa++) {

								String rowValue = processControlJPanel.groupPanel.multiList
										.getCellText(aa, 1);

								if (rowValue != null && rowValue.equals("")) {

									nullRow = aa;

									break;
								} else {

									nullRow = -1;
								}

								if (nullRow != -1) {

									processControlJPanel.groupPanel.multiList
											.addTextCell(nullRow, 21,
													materialInfo.getBsoID());
									processControlJPanel.groupPanel.multiList
											.addTextCell(nullRow, 1,
													materialInfo
															.getMaterialName());

								} else {
									processControlJPanel.groupPanel.multiList.getTable().clearSelection();
									processControlJPanel.groupPanel.addNewRow();
									processControlJPanel.groupPanel.multiList.getTable().clearSelection();
									int allRow = processControlJPanel.groupPanel.multiList
											.getRowCount();
									processControlJPanel.groupPanel.multiList
											.addTextCell(allRow - 1, 21,
													materialInfo.getBsoID());
									processControlJPanel.groupPanel.multiList
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
        // CCEnd SS6
        //CCEnd SS10
        
        
        
    }
//CCEnd SS11

    /**
     * ���ý���Ϊ�鿴ģʽ����������������ʾ�ڽ�����
     */
    private void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() begin...");
        }
        clear();

        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());
        
        
        stepTypeDisJLabel.setVisible(true);
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().stepTypeDisJLabel.setVisible(true);
        processControlJPanel.getStepProcessControlJPanelForBSX().stepTypeDisJLabel.setText(getProcedure().getTechnicsType().getCodeContent());
        }
        
        //�����
        numJTextField.setVisible(false);
        numDisplayJLabel.setVisible(true);
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setEditable(false);
        processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText(Integer.toString(getProcedure().
                getStepNumber()));
        }
        
        
        
        //add by wangh on 20070201
        descNumJTextField.setVisible(false);
        descNumDisplayJLabel.setVisible(true);
        descNumDisplayJLabel.setText(String.valueOf(getProcedure().
                                                  getDescStepNumber()));
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setEditable(false);
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText(String.valueOf(getProcedure().
                getDescStepNumber()));
        }


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
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setEditable(false);
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setText(name);
        }
        
       
        
        //�������
        stepClassifiSortingSelectedPanel.setVisible(false);
        stepClassiDisJLabel.setVisible(true);
        if(getProcedure().getStepClassification()==null||getProcedure().getStepClassification().equals("")){
         stepClassiDisJLabel.setText("");
        }
        else{
          stepClassiDisJLabel.setText(
                getProcedure().getStepClassification().getCodeContent());
        }
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().stepClassifiSortingSelectedPanel.setEnabled(false);
        processControlJPanel.getStepProcessControlJPanelForBSX(). stepClassifiSortingSelectedPanel.setCoding(getProcedure()
                .getStepClassification());
        }
        
        
        //�ӹ�����
        processTypeSortingSelectedPanel.setVisible(false);
        processTypeDisJLabel.setVisible(true);
        processTypeDisJLabel.setText(getProcedure().getProcessType().
                                     getCodeContent());
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.setCoding(getProcedure().
                getProcessType());
        }
        
        
        //����
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setVisible(false);
        }
//        workshopDisJLabel.setVisible(true);
//        workshopDisJLabel.setText(getProcedure().getWorkShop().
//                                  getCodeContent());

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
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setViewMode();
        }
        setButtonVisible(false);
        //��ͼ
        drawingLinkPanel.setModel(1); //VIEW
        //Ѧ�� �޸� 20080428 �����Ż����¹��գ�����ٶ�
        //090206
        drawingLinkPanel.setProcedure(getProcedure());
        if(processControlJPanel!=null){
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setModel(1);
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setProcedure(getProcedure());
        }
        
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
        if(processControlJPanel!=null){
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setMode("View");
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setProcedure(getProcedure());
        }
        
       
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
        if(processControlJPanel!=null){
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setProcedure(getProcedure());
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setMode("View");
        
        }
        f1.setViewMode();
//        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());

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
        if (isOK){
          isOK=descNumJTextField.check();
        }
        if (isOK)
        {



            //����ӹ������Ƿ�Ϊ��
            if (processTypeSortingSelectedPanel.getCoding() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_PROCESSTYPE_ENTERED,
                        null);
                isOK = false;
                processTypeSortingSelectedPanel.getJButton().grabFocus();
            }

            //���鹤�������Ƿ�Ϊ��
//            else if (contentPanel.save() == null ||
//                contentPanel.save().trim().equals(""))
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                        null);
//                isOK = false;
//                contentPanel.getTextComponent().grabFocus();
//            }
//            //���鹤������Ƿ�Ϊ��
//            else if (stepClassifiSortingSelectedPanel.getCoding() == null)
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_STEPCLASSIFI_ENTERED,
//                        null);
//                isOK = false;
//                stepClassifiSortingSelectedPanel.getJButton().grabFocus();
//            }
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
//            	String tempString=contentPanel.save().trim();
//            	if(1==tempString.length())
//            	{
//            		int tempChar=tempString.charAt(0);
//            		if(tempChar==128)
//            		{
//            			message = QMMessage.getLocalizedMessage(RESOURCE,
//                                CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                                null);
//                        isOK = false;
//                        contentPanel.getTextComponent().grabFocus();
//            		}
//            	}
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

    
    //CCBegin SS11
    
    
    /**
     * �����������(��š�����)�Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkIsEmptyForProcessControl()
    {
        
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.checkIsEmpty() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        isOK = processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.check();
        if (isOK)
        {
            isOK = processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.check();
        }
        
        if (isOK)
        {



            //����ӹ������Ƿ�Ϊ��
            if (processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.getCoding() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_PROCESSTYPE_ENTERED,
                        null);
                isOK = false;
                processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.getJButton().grabFocus();
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
                    "cappclients.capp.view.TechnicsStepJPanel.checkIsEmpty() end...return: " +
                    isOK);
        }
        return isOK;
        
        
        
        
    }
    
    //CCEnd SS11

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
            	
            	//CCBegin SS11
            	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
    			.getSelectedObject().getObject();
            	String techType="";
            	if (obj instanceof consQMAssembleProcedureInfo) {
    				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
    				techType = info.getTechnicsType().getCodeContent();
            	}else if (obj instanceof QMFawTechnicsInfo) {

    				QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

    				techType = info.getTechnicsType().getCodeContent();

    			}else if(obj instanceof consQMMachineProcedureInfo){
    				
    				consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
    				techType = info.getTechnicsType().getCodeContent();
    				
    			}
            	
       		  
            	  if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
          				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
            		  
            		  
            		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            		  
            		  setCreateModeForBSX();
            		  
                  	
                  }else{
                	  
                	  
                	  setCreateMode();
                  }
            	  
            	  //CCEnd SS11
            	
                
                break;
            }

            case UPDATE_MODE: //����ģʽ
            {
            	
            	
            	//CCBegin SS11
            	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
    			.getSelectedObject().getObject();
            	String techType="";
            	if (obj instanceof consQMAssembleProcedureInfo) {
    				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
    				techType = info.getTechnicsType().getCodeContent();
            	}else if (obj instanceof QMFawTechnicsInfo) {

    				QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

    				techType = info.getTechnicsType().getCodeContent();

    			}else if(obj instanceof consQMMachineProcedureInfo){
    				
    				consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
    				techType = info.getTechnicsType().getCodeContent();
    				
    			}
            	
       		  
            	  if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
          				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
            		  
            		  
            		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            		  
            		  setUpdateModeForBSX();
            		  
                  	
                  }else{
                	  
                	  
                	  setUpdateMode();
                  }
            	  
            	  //CCEnd SS11
            	
            	
                
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
        	
        	
        	//CCBegin SS11
        	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
			.getSelectedObject().getObject();
        	String techType="";
        	if (obj instanceof consQMAssembleProcedureInfo) {
				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
        	}else if (obj instanceof QMFawTechnicsInfo) {

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
        	  
        	  //CCEnd SS11
        	
        	
        	
           
        }
        else if (getViewMode() == UPDATE_MODE)//anan
        {
        	
        	
        	//CCBegin SS11
        	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
			.getSelectedObject().getObject();
        	String techType="";
        	if (obj instanceof consQMAssembleProcedureInfo) {
				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
        	}else if (obj instanceof QMFawTechnicsInfo) {

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
        	  
        	  //CCEnd SS11
        	
        	
            
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
        if (confirmAction(s))
        {
        	
        	
        	//CCBegin SS8
        	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
			.getSelectedObject().getObject();
        	String techType="";
        	if (obj instanceof consQMAssembleProcedureInfo) {
				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
        	}else if (obj instanceof QMFawTechnicsInfo) {

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
        	  //CCEnd SS8
        	
        	
        }
        else
        {
            setVisible(false);
            isSave = true;
        }

    }


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenUpdate()
    {
        String s = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE_UPDATE, null);
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
        		//CCBegin SS8
            	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
    			.getSelectedObject().getObject();
            	String techType="";
            	if (obj instanceof consQMAssembleProcedureInfo) {
    				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
    				techType = info.getTechnicsType().getCodeContent();
            	}else if (obj instanceof QMFawTechnicsInfo) {

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
            	  //CCEnd SS8
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
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(),
                                              s, title,
                                              JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    /**
     * ���ù��򿨵��������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     */
    private CappWrapData commitAttributes()
    {
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
        String s=String.valueOf(descNumJTextField.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //��������
        getProcedure().setStepName(nameJTextField.getText());
        //���ù������
        getProcedure().setStepClassification(
                (CodingIfc) stepClassifiSortingSelectedPanel.getCoding());
        //���üӹ�����
        getProcedure().setProcessType(
                (CodingIfc) processTypeSortingSelectedPanel.getCoding());
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
                getProcedure().setWorkShop((BaseValueIfc)
                                           parentTechnics.getWorkShop());
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
        //1105

        if(contentPanel.save() == null ||
                contentPanel.save().trim().equals("")){
          v.addElement(nameJTextField.getText());
          getProcedure().setContent(v);
        }
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
       // System.out.println("getProcedure().getTechnicsType()==="+getProcedure().getTechnicsType());
//        if(!(getProcedure().getTechnicsType().toString().trim().equals("���Ƽƻ�����"))){
//        if (processFlowJPanel.check())
//        {
//            //���ù�������
//            procedureInfo.setProcessFlow(processFlowJPanel.
//            		                        getExAttr1(procedureInfo)););
//        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("�����������¼�����");
//            }
//            isSave = false;
//            return null;
//        }
//        if (femaJPanel.check()) {
//          //����FMEA
//          procedureInfo.setFema(femaJPanel.getExAttr());
//        }
//        else {
//          if (verbose) {
//            System.out.println("����FMEA¼�����");
//          }
//          isSave = false;
//          return null;
//        }
        //CCBegin SS11
        if (processControlJPanel.check())
        {
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
        //CCEnd SS11
//
//    }

        //�����й����ϲ�
        Vector resourceLinks = new Vector();

        //������й���(�豸����װ�����ϡ��ĵ�)
        Vector docLinks;
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
        //CCBegin by liunan 2011-5-27 �Ż�
        //ArrayList pDrawings = null;
        //ArrayList drawingLinks = null;
        ArrayList updatedrawings = null;//Begin CR3
        ArrayList deletedrawings = null;//End CR3
        //CCEnd by liunan 2011-5-27
        //CCBegin SS1
        Vector docMasterLinks = new Vector();
        //CCEnd SS1
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
            //CCEnd SS1
             //20080820 xucy
            if(equiplinkJPanel != null)
            {
                equipLinks = equiplinkJPanel.getAllLinks();
            }
            if(toollinkJPanel != null)
            {
            toolLinks = toollinkJPanel.getAllLinks();
            }
            if(materiallinkJPanel != null)
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
              //pDrawings = (ArrayList) obj[0];
              //drawingLinks = (ArrayList) obj[1];
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

        //�ϲ��ĵ�����
//        if(docLinks!=null)
//        {
//            for (int k = 0; k < docLinks.size(); k++)
//            {
//                resourceLinks.addElement(docLinks.elementAt(k));
//            }
//        }
        //CCBegin SS1
        if (docMasterLinks != null)
        {
            for (int k = 0; k < docMasterLinks.size(); k++)
            {
                resourceLinks.addElement(docMasterLinks.elementAt(k));
            }
        }
        //CCEnd SS1
        //�ϲ����Ϲ���
        if(materialLinks!=null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }

        //�ϲ��豸����
        if(equipLinks!=null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //�ϲ���װ����
        if(toolLinks!=null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
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

        //�ϲ��㲿������
         if(partLinks!=null)
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
        //CCBegin by liunan 2011-5-27 �Ż�
        //cappWrapData.setPDrawings(pDrawings);
        cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR3
        cappWrapData.setDeleteDrawing(deletedrawings);//End CR3
        //CCEnd by liunan 2011-5-27

        return cappWrapData;

    }

    
    //CCBegin SS11
    
    
    /**
     * ���ù��򿨵��������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     */
    private CappWrapData commitAttributesForProcessControl()
    {
        //���ù�������(��š����ơ��������ࡢ������𡢼ӹ����͡����š��������ա����ռ�ͼ��
        //��ͼ�����ʽ)
        //�����ǹ���,�����ù�������
    	processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
        getProcedure().setIsProcedure(true);
        if (getViewMode() == CREATE_MODE)
        {
            CodingIfc code = stepType;
            getProcedure().setTechnicsType(code);
        }
        //���ñ��
        if (processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().length() > 5)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEP_NUMBER_INVALID, null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEPNUMBER_TOO_LONG, null);
            JOptionPane.showMessageDialog(getParentJFrame(), message,
                                          title,
                                          JOptionPane.WARNING_MESSAGE);
            processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.grabFocus();
            return null;
        }
        System.out.println("��� 11  �����ţ�"+processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim());
        Integer i = Integer.valueOf(processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim());
        getProcedure().setStepNumber(i.intValue());
        //add by wangh on 20070208(�õ������ù�����)
        String s=String.valueOf(processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //��������
        getProcedure().setStepName(processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.getText());
        //���ù������
        getProcedure().setStepClassification(
                (CodingIfc) processControlJPanel.getStepProcessControlJPanelForBSX().stepClassifiSortingSelectedPanel.getCoding());
        //���üӹ�����
        getProcedure().setProcessType(
                (CodingIfc) processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.getCoding());
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
                getProcedure().setWorkShop((BaseValueIfc)
                                           parentTechnics.getWorkShop());
            }
        }
       
          getProcedure().setRelationCardBsoID(relatedTechnicsID);
        //���ù�������
        Vector v = new Vector();
        v.addElement(contentPanel.save());
        getProcedure().setContent(v);

        if(contentPanel.save() == null ||
                contentPanel.save().trim().equals("")){
          v.addElement(processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.getText());
          getProcedure().setContent(v);
        }
        //���㵥����ʱ(�����ʱ����ά������û�й�������)
        if (processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.getMode() == "EDIT" &&
        		processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.isEnabled())
        {
            try
            {
            	processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setHours();
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.SAVE_STEP_FAILURE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                processControlJPanel.getStepProcessControlJPanelForBSX(). processHoursJPanel.clear();
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
        
        if (processControlJPanel.check())
        {
        	this.eqVec.clear();
        	this.toolVec.clear();
        	this.materiaVec.clear();
        	
        	this.eqDeleVec.clear();
        	this.toolDeleVec.clear();
        	this.materiaDeleVec.clear();
        	
        	
        	
        		
        		System.out.println("pppppppppppppppppppp=======*************============================"+procedureInfo.getBranchID());
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
								int count = (Integer) eqHsh.get(key);
								System.out.println(key+"================"+count);
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
								System.out.println(key+"****"+count);
								if(oldToolVec!=null){
									
								System.out.println("oldToolVec===="+oldToolVec);
                                for(int aa=0;aa<oldToolVec.size();aa++){
										
                                	QMProcedureQMToolLinkIfc  oldTool=(QMProcedureQMToolLinkIfc)oldToolVec.get(aa);
										
										String oldeqID=oldTool.getRightBsoID();
										//CCBegin SS7
										int oldCount=1;
										if(oldTool.getUsageCount()!=null)
											oldCount=Integer.parseInt(oldTool.getUsageCount());
										//CCEnd SS7
										
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
				//CCBegin SS6
				String intCount="0";
				//CCEnd SS6
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
															//CCBegin SS12
															binarylinkinfo.setSeq(a);
															//CCEnd SS12
										 System.out.println("eqVec============**************==============================="+eqVec);
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
													
															//CCBegin SS12
															binarylinkinfo1.setSeq(a);
															//CCEnd SS12

													materiaVec
															.addElement(binarylinkinfo1);

												}

											}
										} else {


											QMProcedureQMMaterialLinkInfo binarylinkinfo = (QMProcedureQMMaterialLinkInfo) taskLogic
													.createNewLinkForBSX(Info);

															//CCBegin SS12
															binarylinkinfo.setSeq(a);
															//CCEnd SS12
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
							 
							 
							 
							 
						//CCBegin SS13	
						//}else if(attrName.equals("FLJtoolBsoID")){
						}else if(attrName.equals("FLJtoolBsoID")||attrName.equals("LJtoolBsoID")){
						//CCEnd SS13
							
							
							 obj = model.getAttValue();
							 if (obj != null && !obj.equals("")) {
									Class[] paraClass1 = { String.class };
									Object[] objs1 = { obj };
									try {
										BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
												"PersistService", "refreshInfo",
												paraClass1, objs1);
												System.out.println("Info===="+Info+"   and  a=="+a+"   and   k=="+k);
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
														System.out.println("����ͬ��tool====111111111111====count============="+toolid);
														System.out.println("����ͬ��tool====222222222222====count============="+obj);
														if (toolid.equals(obj)) {
															String count = binarylinkinfo.getUsageCount();
                                                            if(count.indexOf("(")==-1||count.indexOf("��")==-1){
                                                            	
                                                            	//CCBegin SS6
                                                            	
	                                                            int c=Integer.parseInt(count)+Integer.parseInt(intCount);
                                                            	
                                                            	count=String.valueOf(c);
                                                            	System.out.println("����ͬ��tool========count============="+count);
                                                            	binarylinkinfo.setUsageCount(count);
                                                            	
                                                            	//CCEnd SS6
                                                            }else{
                                                            	
                                                            	break;
                                                            }
															
                                                           

														} else {


															QMProcedureQMToolLinkInfo binarylinkinfo1 = (QMProcedureQMToolLinkInfo) taskLogic
																	.createNewLinkForBSX(Info);
															//CCBegin SS6
															binarylinkinfo1.setUsageCount(intCount);
															//CCEnd SS6
System.out.println("toolvec  add   111=="+binarylinkinfo1+"    and rightbsoid=="+binarylinkinfo1.getRightBsoID());
															//CCBegin SS12
															binarylinkinfo1.setSeq(a);
															//CCEnd SS12
															toolVec.addElement(binarylinkinfo1);
															break;

														}

													}
												} else {


													QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) taskLogic
															.createNewLinkForBSX(Info);
System.out.println("toolvec  add   222=="+binarylinkinfo+"    and rightbsoid=="+binarylinkinfo.getRightBsoID());
													//CCBegin SS12
													binarylinkinfo.setSeq(a);
													//CCEnd SS12
													//CCBegin SS6
													binarylinkinfo.setUsageCount(intCount);
													toolVec.addElement(binarylinkinfo);
													//CCEnd SS6

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
						
						//CCBegin SS6
//						else if(attrName.equals("LJtoolBsoID")){
//							
//							
//							 obj = model.getAttValue();
//							 if (obj != null && !obj.equals("")) {
//									Class[] paraClass1 = { String.class };
//									Object[] objs1 = { obj };
//									try {
//										BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
//												"PersistService", "refreshInfo",
//												paraClass1, objs1);
//										 try {
//											 taskLogic.setOtherSideRole(null);
//											 taskLogic.setLinkClassName("com.faw_qm.capp.model.QMProcedureQMToolLinkInfo");
//											 taskLogic.setOtherSideClass(
//													 Class.forName("com.faw_qm.resource.support.model.QMToolInfo"));
//											 
//											 
//											 
//											 if (toolVec.size() != 0) {
//													for (int n = 0; n < toolVec
//															.size(); n++) {
//
//														QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) toolVec
//																.get(n);
//
//														String toolid = binarylinkinfo
//																.getRightBsoID();
//														
//														if (toolid.equals(obj)) {
//															String count = binarylinkinfo.getUsageCount();
//                                                           if(count.indexOf("(")==-1||count.indexOf("��")==-1){
//                                                           	
//                                                           	
//                                                           	int c=Integer.parseInt(count)+1;
//                                                           	
//                                                           	count=String.valueOf(c);
//                                                           }else{
//                                                           	
//                                                           	break;
//                                                           }
//															
//
//															binarylinkinfo
//																	.setUsageCount(count);
//													
//
//															break;
//
//														} else {
//
//															
//
//															QMProcedureQMToolLinkInfo binarylinkinfo1 = (QMProcedureQMToolLinkInfo) taskLogic
//																	.createNewLinkForBSX(Info);
//
//															toolVec
//																	.addElement(binarylinkinfo1);
//															
//															break;
//
//														}
//
//													}
//												} else {
//
//
//													QMProcedureQMToolLinkInfo binarylinkinfo = (QMProcedureQMToolLinkInfo) taskLogic
//															.createNewLinkForBSX(Info);
//
//													toolVec
//															.addElement(binarylinkinfo);
//
//												}
//											 
//												 
//											 
//										} catch (Exception e) {
//											e.printStackTrace();
//										} 
//										
//										
//										
//										
//									} catch (QMRemoteException ex) {
//										ex.printStackTrace();
//										String title = QMMessage
//												.getLocalizedMessage(RESOURCE,
//														"information", null);
//										JOptionPane.showMessageDialog(
//												getParentJFrame(), ex
//														.getClientMessage(), title,
//												JOptionPane.INFORMATION_MESSAGE);
//									}
//
//								}
//						}
						
						if(attrName.equals("shuliang")){
							
							intCount = (String)model.getAttValue();
							System.out.println("pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp=="+intCount);
						 }
						//CCEnd SS6
						
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
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
        //CCBegin by liunan 2011-5-27 �Ż�
        //ArrayList pDrawings = null;
        //ArrayList drawingLinks = null;
        ArrayList updatedrawings = null;//Begin CR3
        ArrayList deletedrawings = null;//End CR3
        //CCEnd by liunan 2011-5-27
        //CCBegin SS1
        Vector docMasterLinks = new Vector();
        //CCEnd SS1
        try
        {
            docLinks =processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.getAllLinks();
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
                    System.out.println("docMasterLinks=========step===ssssssssssssssssss======================="+docMasterLinks.size());
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
            //CCEnd SS1
             //20080820 xucy
            if(equiplinkJPanel != null)
            {
                equipLinks = equiplinkJPanel.getAllLinks();
                System.out.println("equipLinks.size()==========*******************========="+equipLinks.size());
                System.out.println("eqVec.size()==========*******************========="+eqVec.size());
                //CCBegin ����������������
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
                System.out.println("eqDeleVec======ggggggggggggg==================="+eqDeleVec);
                if(eqDeleVec.size()!=0){
                	for(int m=0;m<eqDeleVec.size();m++){
                		
                		QMProcedureQMEquipmentLinkInfo deleteEqLinkInfo=(QMProcedureQMEquipmentLinkInfo)eqDeleVec.get(m);
                		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),equipLinks);
                		
                		if(hasLink!=null){
                			equipLinks.remove(hasLink);
                			
                		}
                	}
                	
                }
                
                
                //CCEnd ��������������� 
            }
            if(toollinkJPanel != null)
            {
               toolLinks = toollinkJPanel.getAllLinks();
               System.out.println("toolLinks 111 ======================================="+toolLinks);
               //CCBesgin ����������������
               if(toolVec.size()!=0){
            	   System.out.println("toolVec.size()======================================="+toolVec.size());
            	   for (int b = 0; b < toolVec.size(); b++) {
            		   
            		   QMProcedureQMToolLinkInfo newToolLinkInfo=(QMProcedureQMToolLinkInfo)toolVec.get(b);
            		   
            		   String newLinkID=newToolLinkInfo.getRightBsoID();
            		   
            		   
            		   QMProcedureQMToolLinkInfo oldToolLinkInfo=(QMProcedureQMToolLinkInfo)findIsHasObj(newLinkID,toolLinks);
            		   
            		   if(oldToolLinkInfo!=null){
            			   System.out.println(oldToolLinkInfo.getSeq()+"==seq=="+newToolLinkInfo.getSeq());
            			   oldToolLinkInfo.setUsageCount(newToolLinkInfo.getUsageCount());
            			   //CCBegin SS12
            			   oldToolLinkInfo.setSeq(newToolLinkInfo.getSeq());
            			   //CCEnd SS12
            			   int a=toolLinks.indexOf(oldToolLinkInfo);
            			   System.out.println("index=================================="+a+"   and seq=="+newToolLinkInfo.getSeq());
            			   toolLinks.remove(a);
            			   toolLinks.add(a, oldToolLinkInfo);
            		   }
            		   else{
            			   
            			   toolLinks.add(newToolLinkInfo);
            		   }
            		   
            		   
            		   
					}
               }
               
               System.out.println("toolLinks 222 ======================================="+toolLinks);
               
               System.out.println("toolDeleVec======ggggggggggggg==================="+toolDeleVec);
               if(toolDeleVec.size()!=0){
               	for(int m=0;m<toolDeleVec.size();m++){
               		
               		QMProcedureQMToolLinkInfo deleteEqLinkInfo=(QMProcedureQMToolLinkInfo)toolDeleVec.get(m);
               		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),toolLinks);
               		
               		if(hasLink!=null){
               			toolLinks.remove(hasLink);
               			
               		}
               	}
               	
               }
               
               
               //CCEnd ����������������
               
            }
            if(materiallinkJPanel != null)
            {
               materialLinks = materiallinkJPanel.getAllLinks();
               //CCBegin ����������������
               if (materiaVec.size() != 0) {
					for (int b = 0; b < materiaVec.size(); b++) {

						
						QMProcedureQMMaterialLinkInfo newMaterLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaVec.get(b);
	            		   
	            		   String newLinkID=newMaterLinkInfo.getRightBsoID();
	            		   
//	            		   if(materialLinks!=null&&materialLinks.size()!=0){
//	            			   
//	            			   for(int c=0;c<materialLinks.size();c++){
//	            				   
//	            				   QMProcedureQMMaterialLinkInfo oldMateLinkInfo=(QMProcedureQMMaterialLinkInfo)materialLinks.get(c);
//	            				   
//	            				   String oldLinkID=oldMateLinkInfo.getRightBsoID();
//	            				   
//	            				   if(newLinkID.equals(oldLinkID)){
//	            					   
//	            					   oldMateLinkInfo.setUsageCount(newMaterLinkInfo.getUsageCount());
//	            					   
//	            				   }else{
//	            					   
//	            					   materialLinks.add(newMaterLinkInfo);
//	            				   }
//	            				   
//	            			   }
//	            		   }
	            		   QMProcedureQMMaterialLinkInfo oldMateLinkInfo=(QMProcedureQMMaterialLinkInfo) findIsHasObj(newLinkID,materialLinks);
	            		   
	            		   if(oldMateLinkInfo!=null){
	            			   
	            			   oldMateLinkInfo.setUsageCount(newMaterLinkInfo.getUsageCount());
	            			   
	            		   }
	            		   
	            		   else{
	            			   
	            			   materialLinks.add(newMaterLinkInfo);
	            		   }
						
						
					}
            	   
				}
               System.out.println("materiaDeleVec======ggggggggggggg==================="+materiaDeleVec);
               if(materiaDeleVec.size()!=0){
                  	for(int m=0;m<materiaDeleVec.size();m++){
                  		
                  		QMProcedureQMMaterialLinkInfo deleteEqLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaDeleVec.get(m);
                  		BinaryLinkIfc hasLink=findIsHasObj(deleteEqLinkInfo.getRightBsoID(),materialLinks);
                  		
                  		if(hasLink!=null){
                  			materialLinks.remove(hasLink);
                  			
                  		}
                  	}
                  	
                  }
               
               //CCEnd ����������������
               
            }
          //CCBegin ����������������
            if (processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel != null)
            {
                partLinks = processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.getAllLinks();

            }
            Object[] obj = processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.getDrawings();
            System.out.println("obj============sssssssssssssssssss++++++++++++++++++++===================="+obj);
            //CCEnd ����������������
            if (obj != null)
            {
            	//CCBegin by liunan 2011-5-27 �Ż�
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

        
        if (docMasterLinks != null)
        {
            for (int k = 0; k < docMasterLinks.size(); k++)
            {
                resourceLinks.addElement(docMasterLinks.elementAt(k));
            }
        }
        //CCEnd SS1
        //�ϲ����Ϲ���
        if(materialLinks!=null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }

        //�ϲ��豸����
        if(equipLinks!=null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //�ϲ���װ����
        if(toolLinks!=null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
            }
        }

        //�ϲ��㲿������
         if(partLinks!=null)
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
    //CCEnd SS11
    
    
    

//add by wangh on 20070207(���ݲ�ͬ���������ò�ͬ�������̣�FMEA�͹��̿���)
    private void newExtendPanel(String processType)
    {
//      System.out.println("processType============"+processType);
//      System.out.println("existProcessType============"+existProcessType);
      //1111
//      if(!processType.equals("���Ƽƻ�����")){
//        jTabbedPanel.add(extendJPanel2, "��������");
//       jTabbedPanel.add(extendJPanel3, "����FEMA");
//       jTabbedPanel.add(extendJPanel4, "���Ƽƻ�");
//
    	//CCBegin SS11
        if (!processType.equals(existProcessType)) {
        	//CCEnd SS11
//          if (processFlowJPanel != null) {
//            extendJPanel2.remove(processFlowJPanel);
//          }
//          if (processFlowTable.get(processType) == null) {
//            try {
////              System.out.println("processFlowJPanelprocessFlowJPanel===" +
////                                 processFlowJPanel);
////              System.out.println("procedureInfo===" +
////                                 procedureInfo.getClass().getName());
////              System.out.println("procedureInfo2===" + procedureInfo.getBsoName());
//
//              processFlowJPanel = new CappExAttrPanel(procedureInfo.getBsoName(),
//                  "��������", 1);
//            }
//            catch (QMException ex) {
//              ex.printStackTrace();
//            }
//            processFlowTable.put(processType, processFlowJPanel);
//          }
//          else {
//            processFlowJPanel = (CappExAttrPanel) processFlowTable.get(
//                processType);
//          }
//          if (femaJPanel != null) {
//            extendJPanel3.remove(femaJPanel);
//          }
//          if (femaTable.get(processType) == null) {
//            try {
//              femaJPanel = new CappExAttrPanel(procedureInfo.getBsoName(),
//                                               "����FMEA", 1);
//            }
//            catch (QMException ex) {
//              ex.printStackTrace();
//            }
//            femaTable.put(processType, femaJPanel);
//          }
//          else {
//            femaJPanel = (CappExAttrPanel) femaTable.get(
//                processType);
//          }
        	 //CCBegin SS11
          if (processControlJPanel != null) {
            extendJPanel4.remove(processControlJPanel);
          }
          System.out.println("processType==========BBBBBBBBBBBBBBBBBBBBBBBB======================"+processType);
       
      	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
			.getSelectedObject().getObject();
      	String techType="";
      	if (obj instanceof consQMAssembleProcedureInfo) {
				consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
      	}else if (obj instanceof QMFawTechnicsInfo) {

				QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

				techType = info.getTechnicsType().getCodeContent();

			}else if(obj instanceof consQMMachineProcedureInfo){
				
				consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
				techType = info.getTechnicsType().getCodeContent();
				
			}
      	
 		  
      	  if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
    				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
      		  
      		  
      		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
      		  
      		
      		 if (processControlTable.get(processType) == null) {
                 try {
                	
                 	  processControlJPanel = new CappExAttrPanelForBSX(procedureInfo.
                               getBsoName(),
                               "���Ƽƻ�", 1,"����",parentTechnics);
                   
                 }
                 catch (QMException ex) {
                   ex.printStackTrace();
                 }
                   processControlTable.put(processType, processControlJPanel);
               }
               else {
            	   //CCBegin SS7
            	   processControlJPanel.clear();
            	   //CCEnd SS7
                 processControlJPanel = (CappExAttrPanelForBSX) processControlTable.get(
                     processType);
               }
               processControlJPanel.setProIfc(procedureInfo);
            	
            }
         //CCEnd SS11
        }
//        processFlowJPanel.clear();
//        femaJPanel.clear();
        
        //CCBegin SS11
    	Object obj = ((TechnicsRegulationsMainJFrame) parentJFrame)
		.getSelectedObject().getObject();
  	String techType="";
  	if (obj instanceof consQMAssembleProcedureInfo) {
			consQMAssembleProcedureInfo info = (consQMAssembleProcedureInfo) obj;
			techType = info.getTechnicsType().getCodeContent();
  	}else if (obj instanceof QMFawTechnicsInfo) {

			QMFawTechnicsInfo info = (QMFawTechnicsInfo) obj;

			techType = info.getTechnicsType().getCodeContent();

		}else if(obj instanceof consQMMachineProcedureInfo){
			
			consQMMachineProcedureInfo info = (consQMMachineProcedureInfo) obj;
			techType = info.getTechnicsType().getCodeContent();
			
		}
  	
		  
  	  if (techType.equals("������װ�乤��")||techType.equals("������װ�乤��") ||
				techType.equals("��������ӹ���")|| techType.equals("��������ӹ���")) {
        processControlJPanel.clear();
        if (mode == CREATE_MODE ||
            mode == UPDATE_MODE) {
//          processFlowJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
//          femaJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
          processControlJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
        }
        else {
//          processFlowJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
//          femaJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
          processControlJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
        }
        if (mode != CREATE_MODE) {
//          processFlowJPanel.show(getProcedure().getProcessFlow());
//          femaJPanel.show(getProcedure().getFema());
          processControlJPanel.show(getProcedure().getProcessControl());
        }
//        extendJPanel2.add(processFlowJPanel,
//                          new GridBagConstraints(0, 0, 1, 1, 1.0,
//                                                 1.0
//                                                 , GridBagConstraints.CENTER,
//                                                 GridBagConstraints.BOTH,
//                                                 new Insets(0, 0, 0, 0), 0, 0));
//        extendJPanel3.add(femaJPanel,
//                          new GridBagConstraints(0, 0, 1, 1, 1.0,
//                                                 1.0
//                                                 , GridBagConstraints.CENTER,
//                                                 GridBagConstraints.BOTH,
//                                                 new Insets(0, 0, 0, 0), 0, 0));
        extendJPanel4.add(processControlJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));
  	  }
  	  //CCEnd SS11
//
//        //add by wangh on 200726(���ù������̣�����FMEA�Ϳ��Ƽƻ��Ƿ�ɼ�)
////       if (!ts16949) {
////         processFlowJPanel.setVisible(false);
////         femaJPanel.setVisible(false);
////         processControlJPanel.setVisible(false);
////       }
//      }
        repaint();
        processType="";
    }
    //CCBegin SS11
    /**
     * �����½��Ĺ���
     */
    private void saveWhenCreateForProcessControl()
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
        requiredFieldsFilled = checkIsEmptyForProcessControl();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //�������Ƿ�Ϊ����
        //CCBegin SS14
        //if (!checkIsInteger(processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.getText().trim()))
        if (!checkIsInteger(processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim()))
        {
        	//processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText("");
        	processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            //processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.grabFocus();
            processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.grabFocus();
            return;
        }
        //CCEnd SS14
        System.out.println("�� step 2 ����ͼ˳��ţ�����");
        //CCBegin SS9
        if(processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.checkSeqNum())
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //CCEnd SS9

        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributesForProcessControl();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

            QMProcedureInfo returnProce;
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
            
            //CCBegin by liunan 2011-5-27 �Ż�
            //Begin CR3
            if(drawingLinkPanel!=null&&processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            	processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.resetArrayList();
            }
            
            //End CR3
            //CCEnd by liunan 2011-5-27
      
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
           //20060727Ѧ���޸ģ�ȥ��ˢ�¹��սڵ�Ĳ���
            //ˢ�����ڵ�
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                //   pTechnics, pTechnics.getBsoID());

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
        if (confirmAction(QMMessage.getLocalizedMessage(RESOURCE, "108", null)))
        {
        	
        	//CCBegin SS7
//            setCreateMode();
        	setCreateModeForBSX();
        	//CCEnd SS7
            isSave = false;
            setButtonWhenSave(true);
            return;
        }
        else
        {
        	//CCBegin by liunan 2010-12-17 �Ż�\
        	//Begin CR1
            //setVisible(false);
            setProcedure(returnProce);
            mode = 0; // End CR1
            //CCEnd by liunan 2010-12-17
            TechnicsContentJPanel.addFocusLis.initFlag();//anan
        }
        setButtonWhenSave(true);
        isSave = true;
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() end...return is void");
        }
    }

    //CCEnd SS11
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

        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

            QMProcedureInfo returnProce;
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
            
            //CCBegin by liunan 2011-5-27 �Ż�
            //Begin CR3
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR3
            //CCEnd by liunan 2011-5-27
      
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
           //20060727Ѧ���޸ģ�ȥ��ˢ�¹��սڵ�Ĳ���
            //ˢ�����ڵ�
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                //   pTechnics, pTechnics.getBsoID());

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
        if (confirmAction(QMMessage.getLocalizedMessage(RESOURCE, "108", null)))
        {
            setCreateMode();
            isSave = false;
            setButtonWhenSave(true);
            return;
        }
        else
        {
        	//CCBegin by liunan 2010-12-17 �Ż�\
        	//Begin CR1
            //setVisible(false);
            setProcedure(returnProce);
            mode = 0; // End CR1
            //CCEnd by liunan 2010-12-17
            TechnicsContentJPanel.addFocusLis.initFlag();//anan
        }
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
        if (!checkIsInteger(numJTextField.getText().trim()))
        {
            numJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            numJTextField.grabFocus();
            return;
        }

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
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            
            //CCBegin by liunan 2011-5-27 �Ż�
            //Begin CR3
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR3
            //CCEnd by liunan 2011-5-27
            
            //ˢ�����ڵ�
            ((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                    returnProce, parentTechnics.getBsoID());
            procedureInfo = returnProce;
        }
        catch (QMRemoteException ex)
        {
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
        //ת���ɲ鿴����
        //CCBegin by liunan 2010-12-17 �Ż�
        //Begin CR2
        /*try
        {
            setViewMode(VIEW_MODE);
        }
        catch (PropertyVetoException ex1)
        {
            ex1.printStackTrace();
        }*/
        //End CR2
        //CCEnd by liunan 2010-12-17
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() end...return is void");
        }
    }
    
    
    //CCBegin SS11
    
    /**
     * ������º���Ƽƻ��й�����Ϣ
     */
    private void saveWhenUpdateForProcessControl()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() begin...");
        }
        TechnicsContentJPanel.addFocusLis.initFlag();
        
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
        System.out.println("��� �����ţ�"+processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim());
        //�������Ƿ�Ϊ����
        //CCBegin SS14
        //if (!checkIsInteger(processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.getText().trim()))
        if (!checkIsInteger(processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim()))
        {
        	//processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText("");
        	processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            //processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.grabFocus();
            processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.grabFocus();
            return;
        }
        //CCEnd SS14
        System.out.println("�� step 1 ����ͼ˳��ţ�����");
        //CCBegin SS9
        if(processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.checkSeqNum())
        {
        	setCursor(Cursor.getDefaultCursor());
        	setButtonWhenSave(true);
        	isSave = false;
        	return;
        }
        //CCEnd SS9

        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributesForProcessControl();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
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
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            
            if(drawingLinkPanel!=null&&processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            	processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.resetArrayList();
            }
            
            //ˢ�����ڵ�
            ((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                    returnProce, parentTechnics.getBsoID());
            procedureInfo = returnProce;
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        //ת���ɲ鿴����
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() end...return is void");
        }
    }
    
    
    //CCEnd SS11


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
       // storageJButton.setVisible(flag);

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
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(), title,
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
    private CappSortingSelectedPanel stepClassifiSortingSelectedPanel = null;
    private CappSortingSelectedPanel processTypeSortingSelectedPanel = null;
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
        //System.out.println("RRRRRRRRRRRRRRRRRR--------"+processType);
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
              //  d.setProIfc(procedureInfo);
                d.setProcedure(getProcedure());
                d.setEditMode();
                d.setVisible(true);
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
                existProcessType = processType;
            }
            //�鿴ģʽ
            if (getViewMode() == VIEW_MODE)
            {
                d = new TParamJDialog(procedureInfo.getBsoName(), parentJFrame, "");
                //System.out.println("HHHHHHHHHHHHH------------_________"+getProcedure().getTechnicsType().getCodeContent());
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
    //CCBegin SS5
    /**
     * <p>���ѡ���˹������գ���ʱ��Ϣ����ά����</p>
     * <p>���ѡ���˹������գ�������װ���豸�����ϵȶ�Ϊ�����ã����ڹ����²��ܴ���������</p>
     */
    private void setRelatedEffforBSX()
    {
        String s = relationTechJTextField.getText().trim();
        if (s != null && !s.equals(""))
        {
            processHoursJPanel.setEnabled(false);
            //CCBegin SS11
            processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEnabled(false);
            //CCEnd SS11
            //equiplinkJPanel.clear();
            //toollinkJPanel.clear();
            //materiallinkJPanel.clear();
            //doclinkJPanel.clear();
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
            doclinkJPanel.setMode("View");
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("View");
            }
        }
        else
        {
            processHoursJPanel.setEnabled(true);
          //CCBegin SS11
            if(processControlJPanel!=null)
                processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEnabled(true);
            //CCEnd SS11
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
                        processHoursJPanel.setEditMode();
                        //CCBegin SS11
                        if(processControlJPanel!=null)
                        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                        //CCEnd SS11
                    }
                    else
                    {
                        processHoursJPanel.setEditMode();
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                        
                      //CCBegin SS11
                        if(processControlJPanel!=null)
                        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                        //CCEnd SS11

                    }
                }
                else
                {
                   processHoursJPanel.setEditMode();
                 //CCBegin SS11
                   if(processControlJPanel!=null)
                   processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                   //CCEnd SS11
                }
                //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ����Դ���ɿ����õ�
               String resourceUpdateflag = RemoteProperty.getProperty(
                       //CCBegin SS4
                       //"updateResourceLink", "true");
                       "updateResourceLink_bsx", "true");
                       //CCEnd SS4
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
                    if (linkCollection != null && linkCollection.size() != 0)
                    {
                        for (Iterator it = linkCollection.iterator();
                                           it.hasNext(); )
                        {
                            BinaryLinkIfc link = (BinaryLinkIfc) it.next();
                            if (link instanceof QMProcedureQMEquipmentLinkIfc)
                            {
                                eqflag = true;
                            }
                            else
                            if (link instanceof QMProcedureQMToolLinkIfc)
                            {
                                toolflag = true;
                            }
                            else
                            if (link instanceof QMProcedureQMMaterialLinkIfc)
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
                       //CCBegin by chudaming 2008-12-4 bsx�����й������ҹ����й�װ�����ϡ��豸ʱ����Ĺ�װ�����ϡ��豸���Ը���
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
                     //CCEnd by chudaming 2008-12-4 bsx�����й������ҹ����й�װ�����ϡ��豸ʱ����Ĺ�װ�����ϡ��豸���Ը���
                   }
                    if (partlinkJPanel != null)
                    {
                        if (partflag)
                        {
                            partlinkJPanel.setMode("View");
                        }
                        else
                        {
                        	partlinkJPanel.setMode("View");
                        }
                    }
                }
                else
                {
                    equiplinkJPanel.setMode("View");
                    toollinkJPanel.setMode("View");
                    materiallinkJPanel.setMode("View");
                    partlinkJPanel.setMode("View");
                    //add by wangh on 20080226 �����ĵ��ɱ༭.
                    doclinkJPanel.setMode("View");
                    //add end
                }
            }

            //�޹��������ҹ������޹�������ʱ��ά����ʹ����Դ��������ά��
            else
            {
                processHoursJPanel.setEditMode();
              //CCBegin SS11
                if(processControlJPanel!=null)
                processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                //CCEnd SS11
                equiplinkJPanel.setMode("View");
                toollinkJPanel.setMode("View");
                materiallinkJPanel.setMode("View");
                doclinkJPanel.setMode("View");
                if (partlinkJPanel != null)
                {
                    partlinkJPanel.setMode("View");
                }
            }
        }
    }
    //CCEnd SS5

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
            //CCBegin SS11
            processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEnabled(false);
            //CCEnd SS11
            //equiplinkJPanel.clear();
            //toollinkJPanel.clear();
            //materiallinkJPanel.clear();
            //doclinkJPanel.clear();
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
          //CCBegin SS11
            if(processControlJPanel!=null)
                processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEnabled(true);
            //CCEnd SS11
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
                        processHoursJPanel.setEditMode();
                        //CCBegin SS11
                        if(processControlJPanel!=null)
                        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                        //CCEnd SS11
                    }
                    else
                    {
                        processHoursJPanel.setEditMode();
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                        
                      //CCBegin SS11
                        if(processControlJPanel!=null)
                        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                        //CCEnd SS11

                    }
                }
                else
                {
                   processHoursJPanel.setEditMode();
                 //CCBegin SS11
                   if(processControlJPanel!=null)
                   processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                   //CCEnd SS11
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
                    if (linkCollection != null && linkCollection.size() != 0)
                    {
                        for (Iterator it = linkCollection.iterator();
                                           it.hasNext(); )
                        {
                            BinaryLinkIfc link = (BinaryLinkIfc) it.next();
                            if (link instanceof QMProcedureQMEquipmentLinkIfc)
                            {
                                eqflag = true;
                            }
                            else
                            if (link instanceof QMProcedureQMToolLinkIfc)
                            {
                                toolflag = true;
                            }
                            else
                            if (link instanceof QMProcedureQMMaterialLinkIfc)
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
                    	//CCBegin SS5
                       //CCBegin by chudaming 2008-12-4 bsx�����й������ҹ����й�װ�����ϡ��豸ʱ����Ĺ�װ�����ϡ��豸���Ը���
                        equiplinkJPanel.setMode("View");
                        //CCBegin SS5
                    }
                    else
                    {
                        equiplinkJPanel.setMode("Edit");
                    }
                    }
                    if(toollinkJPanel != null)
                    {
                    if (toolflag)
                    {
                        toollinkJPanel.setMode("Edit");
                    }
                    else
                    {
                        toollinkJPanel.setMode("Edit");
                    }
                    }
                   if(materiallinkJPanel != null)
                   {
                    if (matflag)
                    {
                        materiallinkJPanel.setMode("Edit");
                    }
                    else
                    {
                        materiallinkJPanel.setMode("Edit");
                    }
                     //CCEnd by chudaming 2008-12-4 bsx�����й������ҹ����й�װ�����ϡ��豸ʱ����Ĺ�װ�����ϡ��豸���Ը���
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
                	//CCBegin SS5
                    equiplinkJPanel.setMode("View");
                    toollinkJPanel.setMode("View");
                    materiallinkJPanel.setMode("View");
                    partlinkJPanel.setMode("View");
                    //add by wangh on 20080226 �����ĵ��ɱ༭.
                    doclinkJPanel.setMode("View");
                    //add end
                	//CCBegin SS5
                }
            }

            //�޹��������ҹ������޹�������ʱ��ά����ʹ����Դ��������ά��
            else
            {
                processHoursJPanel.setEditMode();
              //CCBegin SS11
                if(processControlJPanel!=null)
                processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setEditMode();
                //CCEnd SS11
                equiplinkJPanel.setMode("Edit");
                toollinkJPanel.setMode("Edit");
                materiallinkJPanel.setMode("Edit");
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
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (mode != VIEW_MODE)
        {
            if (info[0] instanceof QMEquipmentInfo)
            {
                for (int i = 0; i < info.length; i++)
                {
                    equiplinkJPanel.addEquipmentToTable((QMEquipmentInfo)
                            info[i]);
                }
            }
            else if (info[0] instanceof QMToolInfo)
            {
                for (int i = 0; i < info.length; i++)
                {
                    toollinkJPanel.addToolToTable((QMToolInfo) info[i]);
                }
            }
            else if (info[0] instanceof QMMaterialInfo)
            {
                for (int i = 0; i < info.length; i++)
                {
                    materiallinkJPanel.addMaterialToTable((QMMaterialInfo)
                            info[i]);
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
        equiplinkJPanel.setMode("Edit");
        equiplinkJPanel.repaint();
        toollinkJPanel.setMode("Edit");
        toollinkJPanel.repaint();
        materiallinkJPanel.setMode("Edit");
        materiallinkJPanel.repaint();
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
        stepClassifiSortingSelectedPanel.setCoding(null);
        processTypeSortingSelectedPanel.setCoding(null);

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
        contentPanel = new SpeCharaterTextPanel(parentJFrame);
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
        jTabbedPanel.setSelectedIndex(0);
    }
//CCBegin SS5
    /**
     * ����ģʽʱ���ô˷���.����ʵ��������ֵ����,�����ͼ����齨
     * �ı�part����,��̬����
     */
    public void refreshObjectForBSX()
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
                	
                	System.out.println("-----3333333----"+processType);
                    QMProcedureInfo procedureinfo = CappServiceHelper.
                            instantiateQMProcedure(processType);
//                    System.out.println("-----3333333----"+getProcedure().getBsoName());
                    setProcedure(procedureinfo);
                }
                catch (ClassNotFoundException ex)
                {
//                    if (verbose)
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
            
            if(processControlJPanel!=null){
                 processControlJPanel.getStepLinkSouseControlJPanelForBSX().newPartlinkJPanel(processType);
            }
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("View");
            }
            System.out.println("---------"+getProcedure().getBsoName());
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
    
    
    //CCEnd SS5

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
                	System.out.println("-----3333333----"+processType);
                    QMProcedureInfo procedureinfo = CappServiceHelper.
                            instantiateQMProcedure(processType);
//                    System.out.println("-----3333333----"+getProcedure().getBsoName());
                    setProcedure(procedureinfo);
                }
                catch (ClassNotFoundException ex)
                {
//                    if (verbose)
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
            	equiplinkJPanel.setMode("Edit");
            }
            newToollinkJPanel(processType);
            if (toollinkJPanel != null)
            {
            	toollinkJPanel.setMode("Edit");
            }
            newMateriallinkJPanel(processType);
            if (materiallinkJPanel != null)
            {
            	materiallinkJPanel.setMode("Edit");
            }
            relationsJPanel.add(doclinkJPanel, "�ĵ�");
            relationsJPanel.add(drawingLinkPanel, "��ͼ");
            newPartlinkJPanel(processType);
            
            //CCBegin SS11
            if(processControlJPanel!=null){
                 processControlJPanel.getStepLinkSouseControlJPanelForBSX().newPartlinkJPanel(processType);
            }
            //CCEnd SS11
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("Edit");
            }
            System.out.println("---------"+getProcedure().getBsoName());
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
    //private synchronized void newPartlinkJPanel(String stepType)
    private void newPartlinkJPanel(String stepType)//anan
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
            //CCBegin SS3
            partlinkJPanel.setIsProcedure(true);
            //CCEnd SS3
            relationsJPanel.add(partlinkJPanel, "�㲿��");
        }
    }


     /**
     * ����ʵ����part����
     * @param stepType String
     * 20080820 xucy
     */
    //private synchronized void newEquiplinkJPanel(String stepType)
    private void newEquiplinkJPanel(String stepType)//anan
    {
            if (equiplinkJPanel != null)
            {
                relationsJPanel.remove(equiplinkJPanel);
            }
            //System.out.println("stepTypestepType======="+stepType);
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType);
            //CCBegin SS3
            equiplinkJPanel.setIsProcedure(true);
            //CCEnd SS3

            //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
            equiplinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  //1222
                    //toollinkJPanel.addRelationTools(e);
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
    //private synchronized void newToollinkJPanel(String stepType)
    private void newToollinkJPanel(String stepType)//anan
    {

            if (toollinkJPanel != null)
            {
                relationsJPanel.remove(toollinkJPanel);
            }
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType);
            //CCBegin SS3
            toollinkJPanel.setIsProcedure(true);
            //CCEnd SS3
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
    //private synchronized void newMateriallinkJPanel(String stepType)
    private void newMateriallinkJPanel(String stepType)//anan
    {

            if (materiallinkJPanel != null)
            {
                relationsJPanel.remove(materiallinkJPanel);
            }
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType);
            //CCBegin SS3
            materiallinkJPanel.setIsProcedure(true);
            //CCEnd SS3
            relationsJPanel.add(materiallinkJPanel, "����");

    }

    /**
     * ɾ��ԭ���Ĳ��ţ�����ʵ������ԭ���ǹ��տ������п��ܸ��£�
     */
//    private void changeWorkShopSortingSelectedPanel()
//    {
//        if (workshopSortingSelectedPanel != null)
//        {
//            upJPanel.remove(workshopSortingSelectedPanel);
//            workshopSortingSelectedPanel = null;
//        }
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
//            //workshopDisJLabel.setVisible(false);
//        }
////        else
////        {
////            workshopDisJLabel.setVisible(true);
////            workshopDisJLabel.setText(((CodingIfc) parentTechnics.getWorkShop()).
////                                      getCodeContent());
////        }
//    }

    public JPanel getPartLinkJPanel()
    {
        return partlinkJPanel;
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

  void descNumJTextField_actionPerformed(ActionEvent e) {

  }
  //CCBegin SS7
  /**
   *�˷��������������ά�������е�����
   */
  public void clearForBsx()
  {
      if (firstInFlag)
      {
          firstInFlag = false;
          return;
      }
      
      
      numJTextField.setText("");
      processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.setText("");
      //add by wangh on 20070201
      descNumJTextField.setText("");
      descNumDisplayJLabel.setText("");
      processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.setText("");
      processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setText("");
      numDisplayJLabel.setText("");
      this.relatedTechnicsID = null;
      //relatedTechnics = null;
      relationTechJTextField.setText("");
      relationTechDisJLabel.setText("");
      nameJTextField.setText("");
      nameDisplayJLabel.setText("");
      contentPanel.clearAll();
      drawingLinkPanel.clear();
      processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.clear();
      stepClassifiSortingSelectedPanel.setCoding(null);
      processControlJPanel.getStepProcessControlJPanelForBSX().stepClassifiSortingSelectedPanel.setCoding(null);
      processTypeSortingSelectedPanel.setCoding(null);
      processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.setCoding(null);
      processHoursJPanel.clear();
      processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.clear();
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
      processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.clear();
    	  
      existProcessType = "";
      d = null;
      if (partlinkJPanel != null)
      {
          relationsJPanel.remove(partlinkJPanel);
          partlinkJPanel = null;
      }
      
      if(processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel!=null){
    	  
    	  processControlJPanel.getStepLinkSouseControlJPanelForBSX().relationsJPanel.remove(
    			          processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel);
    	  
    	  processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel=null;
      }

      //����ʵ������������
      upJPanel.remove(contentPanel);
      contentPanel = new SpeCharaterTextPanel(parentJFrame);
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
      processControlJPanel.getStepLinkSouseControlJPanelForBSX().repaint();
      processControlJPanel.repaint();
      paraJButton.setVisible(true);
      jTabbedPanel.setSelectedIndex(1);
  }
  
  //CCEnd SS7
  
  //CCBegin by liunan 2011-4-22 �򲹶�v4r1_p037
  /**
   * <p>Title:�����߳� </p>
   * <p>Description: ���ڵ��ü�ͼ�����Ż����¹��գ�����ٶ�</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: һ������</p>
   * @author Ѧ�� 2008 04 28 ���
   * @version 1.0
   */
    /*class DrawThread extends Thread
    {
        public void run()
        {
            drawingLinkPanel.setProcedure(getProcedure());
        }
    }*/
    //CCEnd by liunan 2011-4-22
}
