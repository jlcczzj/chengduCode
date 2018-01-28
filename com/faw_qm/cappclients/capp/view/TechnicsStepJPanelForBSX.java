package com.faw_qm.cappclients.capp.view;
//SS1 将搜索到的文档转换成文档master和工序的关联 Liuyang 2013-3-19
//SS2 解放工时默认为秒和变速箱工时默认为分leixiao 2013-9-11
// SS3 工步中增加关联“累加到工序”并且不在工序中显示 leixiao 2013-10-14  
// SS4 变速箱工步资源不汇总到工序 liunan 2014-5-19
//SS5 修改服务平台问题，问题编号：A005-2014-2843。郭晓亮  2014-05-06
//SS6 修改服务平台问题，问题编号：A005-2014-2842。郭晓亮  2014-05-21
//SS7 修改服务平台问题，问题编号：A005-2014-2923。郭晓亮  2014-6-5
//SS8 修改弹出是否保存提示，点是保存不上简图问题。
//SS9 增加简图顺序号的重复检查 liunan 2014-7-1
//SS10 修改服务平台上问题A005-2014-2964
//SS11 变速箱工艺所见即所得  guoxiaoliang 2014-03-10
//SS12 工艺资源设置顺序 liunan 2015-3-17
//SS13 控制计划中资源与工序中资源关联不一致，3列工装维护，14列量检具维护，没能同步更新工序中资源关联。 liunan 2015-3-19
//SS14 平台问题：A005-2015-3041 工序号检查不是数字给出提示。 liunan 2015-3-24
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


	/**工序维护面板*/
//  private JPanel stepJPanel = new JPanel();
    /**按钮面板*/
    private JPanel buttonJPanel = new JPanel();


    /**编号*/
    private JLabel numJLabel = new JLabel();
    //add by wangh on 20070131(加入工序编号标签和文本框，添加一个JTabbedPane，和3个新JPanel。);
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



    /**关联工艺*/
    private JLabel relationTechJLabel = new JLabel();
    private JTextField relationTechJTextField = new JTextField();
    private JButton searchTechJButton = new JButton();
    private JLabel relationTechDisJLabel = new JLabel();
    private JPanel rbJPanel = new JPanel();
    private JButton deleteTechJButton = new JButton();


    /**名称*/
    private JLabel nameJLabel = new JLabel();
    private TermTextField nameJTextField;
    private JLabel nameDisplayJLabel = new JLabel();


    /**工序种类*/
    private JLabel stepTypeJLabel = new JLabel();
    private JLabel stepTypeDisJLabel = new JLabel();


    /**工序类别*/
    private JLabel stepClassifiJLabel = new JLabel();
    private JLabel stepClassiDisJLabel = new JLabel();


    /**加工类型*/
    private JLabel processTypeJLabel = new JLabel();
    private JLabel processTypeDisJLabel = new JLabel();


    /**部门*/
//    private JLabel workshopJLabel = new JLabel();
//    private JLabel workshopDisJLabel = new JLabel();


    /**简图维护*/
    private ProcedureUsageDrawingPanel drawingLinkPanel;


    /**工艺内容*/
    private JLabel contentJLabel = new JLabel();
    private SpeCharaterTextPanel contentPanel;


    /**关联面板*/
    private JTabbedPane relationsJPanel = new JTabbedPane();


    /**按钮组*/
    private JButton paraJButton = new JButton();
    private JButton saveJButton = new JButton();
    private JButton cancelJButton = new JButton();
    private JButton quitJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();
//    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**工时统计面板*/
    private ProcessHoursJPanel processHoursJPanel;


    /**界面显示模式（更新模式）标记*/
    public final static int UPDATE_MODE = 0;


    /**界面显示模式（创建模式）标记*/
    public final static int CREATE_MODE = 1;


    /**界面显示模式（查看模式）标记*/
    public final static int VIEW_MODE = 2;


    /**界面模式--查看*/
    private int mode = VIEW_MODE;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**工序*/
    private QMProcedureInfo procedureInfo;
    private GridBagLayout gridBagLayout4 = new GridBagLayout();


    /**父窗口*/
    private JFrame parentJFrame;


    /**所选中的节点*/
    private CappTreeNode selectedNode;


    /**设备关联的维护面板*/
    private ProcedureUsageEquipmentJPanel equiplinkJPanel;


    /**工装关联的维护面板*/
    private ProcedureUsageToolJPanel toollinkJPanel;


    /**材料关联的维护面板*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**文档关联的维护面板*/
    private ProcedureUsageDocJPanel doclinkJPanel = new ProcedureUsageDocJPanel();


    /**入库按钮*/
    //private JButton storageJButton = new JButton();

    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    private JScrollPane scrollpane = new JScrollPane();


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JPanel jPanel1 = new JPanel();


    /**扩展内容界面对象*/
    private TParamJDialog d = null;
    private EquipmentToolMaintainJDialog f1 = null;


    /**
     * 工艺卡
     */
    private QMTechnicsIfc parentTechnics;


    /**c中存放工序下的直接工步*/
    private Collection c = null;


    /**记录是否第一次进入此界面*/
    private boolean firstInFlag = true;


    /**
     *代码管理中 工艺内容\工序种类下的代码项
     * 键:工序种类名称  值:工序种类代码项
     */
    private Hashtable stepTypetable = null;
    private CodingIfc stepType;
    private JSplitPane splitJPanel = new JSplitPane();
  
    private JPanel upJPanel = new JPanel();
    private JPanel downJPanel = new JPanel();

    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private GridBagLayout gridBagLayout7 = new GridBagLayout();
    //add by wangh on 20070207(定义过程流程,过程FMEA和控制计划Panel,并给每个Panel定义一个Hashtable)
    private Hashtable processFlowTable = new Hashtable();
    private Hashtable femaTable = new Hashtable();
    private Hashtable processControlTable = new Hashtable();
    private CappExAttrPanel processFlowJPanel;
    private CappExAttrPanel femaJPanel;
    //CCBegin SS11
//    private CappExAttrPanel processControlJPanel;
    private CappExAttrPanelForBSX processControlJPanel;
    //CCEnd SS11

  //add by wangh on 20070326(是否显示TS16949的工序或者工步信息。)
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
     * 构造函数
     * @param parent 调用本类的父窗口
     */
    public TechnicsStepJPanelForBSX(JFrame parent)
    {
    	super();
        try
        {
            parentJFrame = parent;
            //CCBegin by chudaming 20090916  在工序名称里可输入字符增大到99
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
     * 构造函数
     * @param parent 调用本类的父窗口
     * @param parentnode 父节点
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
     * 界面初始化
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
        numJLabel.setText("*工序号");

        //add by wangh on 20070131
        //薛凯 修改
//        descStepNumberJLabel.setMaximumSize(new Dimension(48, 22));
//        descStepNumberJLabel.setMinimumSize(new Dimension(48, 22));
//        descStepNumberJLabel.setPreferredSize(new Dimension(48, 22));
        descStepNumberJLabel.setMaximumSize(new Dimension(53, 22));
        descStepNumberJLabel.setMinimumSize(new Dimension(53, 22));
        descStepNumberJLabel.setPreferredSize(new Dimension(53, 22));
        //薛凯 修改结束
        descStepNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descStepNumberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//      薛凯修改 20080219 修改原因：在工艺规程中在工艺下创建工序,工序编号为必输项字段前有*
        //descStepNumberJLabel.setText("工序编号");
        descStepNumberJLabel.setText("*工序编号");
        //薛凯修改结束
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
        relationTechJLabel.setText("关联工艺");
        searchTechJButton.setMaximumSize(new Dimension(89, 23));
        searchTechJButton.setMinimumSize(new Dimension(89, 23));
        searchTechJButton.setPreferredSize(new Dimension(89, 23));
        searchTechJButton.setToolTipText("");
        searchTechJButton.setMnemonic('B');
        searchTechJButton.setText("浏览(B)");
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
        deleteTechJButton.setText("删除(R)");
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
        nameJLabel.setText("*工序名称");
        stepTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stepTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        stepTypeJLabel.setText("*工序种类");
        stepClassifiJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stepClassifiJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        stepClassifiJLabel.setText("工序类别");
        processTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        processTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        processTypeJLabel.setText("*加工类型");
//        workshopJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        workshopJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//        workshopJLabel.setText("*部门");
        contentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        contentJLabel.setText("工艺内容");

        paraJButton.setMaximumSize(new Dimension(114, 23));
        paraJButton.setMinimumSize(new Dimension(114, 23));
        paraJButton.setPreferredSize(new Dimension(114, 23));
        paraJButton.setMnemonic('E');
        paraJButton.setText("附加信息. . .");
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
        saveJButton.setText("保存");
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
        cancelJButton.setText("取消");
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
        quitJButton.setText("退出");
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
//        storageJButton.setText("入库");
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
        //用于查看模式
        upJPanel.add(nameDisplayJLabel,
                      new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(7, 8, 0, 7), 0, 0));

        //用于更新和查看模式
        upJPanel.add(stepTypeDisJLabel,
                     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));

        //用于查看模式
        upJPanel.add(processTypeDisJLabel,
                      new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(7, 8, 0, 0), 0, 0));

        upJPanel.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(7, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumJTextField,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));


        //用于查看模式
        upJPanel.add(numDisplayJLabel,
                     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(10, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumDisplayJLabel,
                      new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 8, 0, 0), 0, 0));


        //用于查看模式

        upJPanel.add(relationTechJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 20, 0, 0), 0, 0));
        //用于查看模式
        upJPanel.add(stepClassiDisJLabel,
                      new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 8, 0, 7), 0, 0));

        upJPanel.add(stepClassifiJLabel,
                      new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 0, 0, 0), 0, 0));
        //用于查看模式
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

        //add by wangh on 20070201(将工序信息,过程流程,过程FMEA和控制计划Panel加入到jTabbedPanel中)
        jTabbedPanel.add(extendJPanel, "工序信息");
        if (ts16949) {
			// jTabbedPanel.add(extendJPanel2, "过程流程");
			// jTabbedPanel.add(extendJPanel3, "过程FMEA");
        	
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
			
			if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
				
				jTabbedPanel.add(extendJPanel4, "控制计划");
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
     * 界面信息本地化
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
            stepTypeJLabel.setText("*工序种类");
            stepClassifiJLabel.setText("工序类别");
            processTypeJLabel.setText("*加工类型");
            //workshopJLabel.setText("*部门");
            //薛凯修改 20080219 修改原因：在工艺规程中在工艺下创建工序,工序编号为必输项字段前有*
           descStepNumberJLabel.setText("*工序编号");
            //薛凯修改结束
//            contentJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "mtechContentJLabel", null));
           contentJLabel.setText("工艺内容");
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
     * 设置在工艺树所选择的节点
     * @param parentnode
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setTechnics();
    }


    /**
     * 获取当前选择的工序的工艺卡头
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
     * 设置界面为创建模式
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
        //{{初始化工序号
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
        //设置默认值
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
     * 设置界面为创建模式
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
        //{{初始化工序号
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
     
        //设置默认值
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
     * 设置界面为更新模式，并将工序属性显示在界面上
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
        
       
        
        //工序编号
        numJTextField.setVisible(true);
        numJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
        numDisplayJLabel.setVisible(false);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(String.valueOf(getProcedure().getDescStepNumber()));
        descNumDisplayJLabel.setVisible(false);
        

        //关联工艺
        //如果工序下没有工步，则关联工艺可维护；否则不可维护
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

        //工序名称
        nameJTextField.setVisible(true);
        String name = getProcedure().getStepName();
        //if(name != null && !name.equals(""))
        //  nameJTextField.resumeDataDisplay(name);
        nameJTextField.setText(name);
        nameDisplayJLabel.setVisible(false);
        //工序类别
        stepClassifiSortingSelectedPanel.setVisible(true);
        //CCBegin by chudaming 2009-2-19 bsx 更新工序是工序类别不显示
        if(getProcedure().getStepClassification()==null||getProcedure().getStepClassification().equals("")){
          stepClassiDisJLabel.setText("");
         }
         else{
           stepClassifiSortingSelectedPanel.setCoding(getProcedure()
                                                  .getStepClassification());
         }
         //CCEnd by chudaming 2009-2-19 bsx 更新工序是工序类别不显示
        stepClassiDisJLabel.setVisible(false);
        //加工类型
        processTypeSortingSelectedPanel.setVisible(true);
        processTypeSortingSelectedPanel.setCoding(getProcedure().
                                                  getProcessType());
        
        processTypeDisJLabel.setVisible(false);
        //部门
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
        //薛凯 修改 20080428 用于优化更新工艺，提高速度
        drawingLinkPanel.setProcedure(getProcedure());
//        DrawThread dt = new DrawThread();
//        dt.start();
        //薛凯 修改结束 20080428

        //工艺内容
        contentPanel.setEditable(true);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.clearAll();
            contentPanel.resumeData(v);
        }
        //工时
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
        //关联
        //20081120 徐春英修改   更新状态下文档应该可编辑
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "文档");
        
        drawingLinkPanel.setModel(2); //EDIT
        relationsJPanel.add(drawingLinkPanel, "简图");
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());

        }
        //根据是否具有关联工艺，确定processHoursJPanel和各关联面板的状态
        setRelatedEff();
        //modify by wangh on 20070615(去掉在有关联工艺时文档出现多余按纽)
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
        //CCBegin by liunan 2011-6-1 优化
        //add by guoxl on 2009-1-7(给更新界面添加监听，如果界面信息改变则给出是否保存提示，否则不提示)
        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
        //add by guoxl end
        //CCEnd by liunan 2011-6-1
    }
    
    
    //CCBegin SS11
    /**
     * 设置界面为更新模式，并将工序属性显示在界面上
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
        
        
       
        
        //工序编号
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
        
        
        
        //关联工艺
        //如果工序下没有工步，则关联工艺可维护；否则不可维护
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

        //工序名称
        nameJTextField.setVisible(true);
        String name = getProcedure().getStepName();
        //if(name != null && !name.equals(""))
        //  nameJTextField.resumeDataDisplay(name);
        nameJTextField.setText(name);
        //CCBegin SS11
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setText(name);
        //CCEnd SS11
        nameDisplayJLabel.setVisible(false);
        //工序类别
        //CCBegin SS5
        stepClassifiSortingSelectedPanel.setVisible(false);
        //CCEnd SS5
        //CCBegin by chudaming 2009-2-19 bsx 更新工序是工序类别不显示
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
      //CCEnd by chudaming 2009-2-19 bsx 更新工序是工序类别不显示
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
        
        //加工类型
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
        
        //部门
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
        //薛凯 修改 20080428 用于优化更新工艺，提高速度
        drawingLinkPanel.setProcedure(getProcedure());         
        
        //CCBegin SS11
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setModel(2);
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.setProcedure(getProcedure());            
        //CCEnd SS11
//        DrawThread dt = new DrawThread();
//        dt.start();
        //薛凯 修改结束 20080428

        //工艺内容
        //CCBegin SS5
        contentPanel.setEditable(false);
        
        //CCEnd SS5
        
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.clearAll();
            contentPanel.resumeData(v);
        }
        //工时
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
        //关联
        //20081120 徐春英修改   更新状态下文档应该可编辑
        //CCBegin SS5
        doclinkJPanel.setMode("View");
        //CCEnd SS5
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "文档");
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
        relationsJPanel.add(drawingLinkPanel, "简图");
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());
            //CCBegin SS11

            processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.setProcedure(getProcedure());
            //CCEnd SS11

        }
        //根据是否具有关联工艺，确定processHoursJPanel和各关联面板的状态
      //CCBegin SS5
        setRelatedEffforBSX();
        processHoursJPanel.setViewMode();
        //CCEnd SS5
        //modify by wangh on 20070615(去掉在有关联工艺时文档出现多余按纽)
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
        //CCBegin by liunan 2011-6-1 优化
        //add by guoxl on 2009-1-7(给更新界面添加监听，如果界面信息改变则给出是否保存提示，否则不提示)
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
							
							if (!toolInfo.getToolCf().getCodeContent().equals("量具")
									&& !toolInfo.getToolCf().getCodeContent().equals("万能量具")
									&& !toolInfo.getToolCf().getCodeContent().equals("专用量具")
									&& !toolInfo.getToolCf().getCodeContent().equals("检具")
									&& !toolInfo.getToolCf().getCodeContent().equals("检验辅具")
									&& !toolInfo.getToolCf().getCodeContent().equals("量仪")
									&& !toolInfo.getToolCf().getCodeContent().equals("检验夹具")
									&& !toolInfo.getToolCf().getCodeContent().equals("检验量辅具")) {
								
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
							
							if (!toolInfo.getToolCf().getCodeContent().equals("量具")
									&& !toolInfo.getToolCf().getCodeContent().equals("万能量具")
									&& !toolInfo.getToolCf().getCodeContent().equals("专用量具")
									&& !toolInfo.getToolCf().getCodeContent().equals("检具")
									&& !toolInfo.getToolCf().getCodeContent().equals("检验辅具")
									&& !toolInfo.getToolCf().getCodeContent().equals("量仪")
									&& !toolInfo.getToolCf().getCodeContent().equals("检验夹具")
									&& !toolInfo.getToolCf().getCodeContent().equals("检验量辅具")) {
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
     * 设置界面为查看模式，并将工序属性显示在界面上
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
        
        //工序号
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

        //关联工艺
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
        
        
        //工序名称
        nameJTextField.setVisible(false);
        nameDisplayJLabel.setVisible(true);
        String name = getProcedure().getStepName();
        nameDisplayJLabel.setText(name);
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setEditable(false);
        processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.setText(name);
        }
        
       
        
        //工序类别
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
        
        
        //加工类型
        processTypeSortingSelectedPanel.setVisible(false);
        processTypeDisJLabel.setVisible(true);
        processTypeDisJLabel.setText(getProcedure().getProcessType().
                                     getCodeContent());
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.setCoding(getProcedure().
                getProcessType());
        }
        
        
        //部门
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setVisible(false);
        }
//        workshopDisJLabel.setVisible(true);
//        workshopDisJLabel.setText(getProcedure().getWorkShop().
//                                  getCodeContent());

        //工艺内容
        contentPanel.setEditable(false);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.resumeData(v);
        }
        //工时计算
        processHoursJPanel.setProcedure(getProcedure());
        processHoursJPanel.setViewMode();
        if(processControlJPanel!=null){
        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
        processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setViewMode();
        }
        setButtonVisible(false);
        //简图
        drawingLinkPanel.setModel(1); //VIEW
        //薛凯 修改 20080428 用于优化更新工艺，提高速度
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
        //薛凯 修改结束 20080428
        //关联
        doclinkJPanel.setMode("View");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "文档");
        if(processControlJPanel!=null){
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setMode("View");
        processControlJPanel.getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setProcedure(getProcedure());
        }
        
       
        //简图
        drawingLinkPanel.setModel(1); //VIEW
        relationsJPanel.add(drawingLinkPanel, "简图");
       //part关联
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
     * 设置当前选择的工序
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedureInfo = info;
    }


    /**
     * 获得当前选择的工序
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedureInfo;
    }


    /**
     * 检验必填区域(编号、名称)是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
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



            //检验加工类型是否为空
            if (processTypeSortingSelectedPanel.getCoding() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_PROCESSTYPE_ENTERED,
                        null);
                isOK = false;
                processTypeSortingSelectedPanel.getJButton().grabFocus();
            }

            //检验工艺内容是否为空
//            else if (contentPanel.save() == null ||
//                contentPanel.save().trim().equals(""))
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                        null);
//                isOK = false;
//                contentPanel.getTextComponent().grabFocus();
//            }
//            //检验工序类别是否为空
//            else if (stepClassifiSortingSelectedPanel.getCoding() == null)
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_STEPCLASSIFI_ENTERED,
//                        null);
//                isOK = false;
//                stepClassifiSortingSelectedPanel.getJButton().grabFocus();
//            }
            //检验部门是否为空
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
        //检验简图输出方式是否为空
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
            //显示信息：缺少必需的字段
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
     * 检验必填区域(编号、名称)是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
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



            //检验加工类型是否为空
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
            //显示信息：缺少必需的字段
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
     * 设置界面模式（创建、更新或查看）。
     * @param aMode 新界面模式
     * @exception java.beans.PropertyVetoException 如果模式Mode无效，则抛出异常。
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
            //信息：无效模式
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

            case CREATE_MODE: //创建模式
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
            	
       		  
            	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
          				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
            		  
            		  
            		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            		  
            		  setCreateModeForBSX();
            		  
                  	
                  }else{
                	  
                	  
                	  setCreateMode();
                  }
            	  
            	  //CCEnd SS11
            	
                
                break;
            }

            case UPDATE_MODE: //更新模式
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
            	
       		  
            	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
          				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
            		  
            		  
            		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            		  
            		  setUpdateModeForBSX();
            		  
                  	
                  }else{
                	  
                	  
                	  setUpdateMode();
                  }
            	  
            	  //CCEnd SS11
            	
            	
                
                break;
            }

            case VIEW_MODE: //查看模式
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
     * 获得界面模式
     * @return 创建、更新、查看或另存为模式
     */
    public int getViewMode()
    {
        return mode;
    }


    /**
     * 执行保存操作
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
         WorkThread thread = new WorkThread();
         thread.start();

    }


    /**
     * 保存
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
        	
   		  
        	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
      				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
        		  
        		  
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
        	
        	
        	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
      				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
        		  
        		  
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
     * 执行取消操作
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
     * 创建模式下，取消按钮的执行方法
     * <p>将已经录入的数据都置空</p>
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
     * 更新模式下，取消按钮的执行方法
     * <p>将本次录入的数据撤消，返回上次录入的数据</p>
     */
    private void cancelWhenUpdate()
    {
        setUpdateMode();
    }


    /**
     * 执行退出操作
     * @param e
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


    /**
     * 退出
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


    /**标记是否可以退出*/
    private boolean isSave = true;


    /**
     * 创建模式下，退出按钮的执行方法
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
        	
   		  
        	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
      				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
        		  
        		  
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
     * 更新模式下，退出按钮的执行方法
     */
    private void quitWhenUpdate()
    {
        String s = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE_UPDATE, null);
        //CCBegin by liunan 2011-6-1 优化
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
            	
       		  
            	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
          				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
            		  
            		  
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
     * 查看模式下，退出按钮的执行方法
     */
    private void quitWhenView()
    {
        setVisible(false);
        isSave = true;
    }


    /**
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return  如果用户选择了“确定”按钮，则返回true;否则返回false
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
     * 设置工序卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     */
    private CappWrapData commitAttributes()
    {
        //设置工序属性(编号、名称、工序种类、工序类别、加工类型、部门、关联工艺、工艺简图、
        //简图输出方式)
        //设置是工序,并设置工序种类
        processHoursJPanel.setProcedure(getProcedure());
        getProcedure().setIsProcedure(true);
        if (getViewMode() == CREATE_MODE)
        {
            CodingIfc code = stepType;
            getProcedure().setTechnicsType(code);
        }
        //设置编号
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
        //add by wangh on 20070208(得到并设置工序编号)
        String s=String.valueOf(descNumJTextField.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //设置名称
        getProcedure().setStepName(nameJTextField.getText());
        //设置工序类别
        getProcedure().setStepClassification(
                (CodingIfc) stepClassifiSortingSelectedPanel.getCoding());
        //设置加工类型
        getProcedure().setProcessType(
                (CodingIfc) processTypeSortingSelectedPanel.getCoding());
        //设置部门
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
        //设置关联工艺
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
        //设置工序内容
        Vector v = new Vector();
        v.addElement(contentPanel.save());
        getProcedure().setContent(v);
        //1105

        if(contentPanel.save() == null ||
                contentPanel.save().trim().equals("")){
          v.addElement(nameJTextField.getText());
          getProcedure().setContent(v);
        }
        //计算单件工时(如果工时面板可维护，即没有关联工艺)
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

        //设置资料夹
        if (getViewMode() == CREATE_MODE)
        {

            QMFawTechnicsInfo pTechnics = (QMFawTechnicsInfo) selectedNode.
                                          getObject().getObject();
            procedureInfo.setLocation(pTechnics.getLocation());

        }
        //维护工艺参数
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
                        System.out.println("扩展属性录入错误！");
                    }
                    isSave = false;
                    return null;
                }
            }
        }
        //add by wangh on 20070212
       // System.out.println("getProcedure().getTechnicsType()==="+getProcedure().getTechnicsType());
//        if(!(getProcedure().getTechnicsType().toString().trim().equals("控制计划工序"))){
//        if (processFlowJPanel.check())
//        {
//            //设置过程流程
//            procedureInfo.setProcessFlow(processFlowJPanel.
//            		                        getExAttr1(procedureInfo)););
//        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("工序过程流程录入错误！");
//            }
//            isSave = false;
//            return null;
//        }
//        if (femaJPanel.check()) {
//          //设置FMEA
//          procedureInfo.setFema(femaJPanel.getExAttr());
//        }
//        else {
//          if (verbose) {
//            System.out.println("工序FMEA录入错误！");
//          }
//          isSave = false;
//          return null;
//        }
        //CCBegin SS11
        if (processControlJPanel.check())
        {
            //设置过程控制
            procedureInfo.setProcessControl(processControlJPanel.
            		getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("工序过程控制录入错误！");
            }
            isSave = false;
            return null;
        }
        //CCEnd SS11
//
//    }

        //将所有关联合并
        Vector resourceLinks = new Vector();

        //获得所有关联(设备、工装、材料、文档)
        Vector docLinks;
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
        //CCBegin by liunan 2011-5-27 优化
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
            	//CCBegin by liunan 2011-5-27 优化
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

        //合并文档关联
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
        //合并材料关联
        if(materialLinks!=null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }

        //合并设备关联
        if(equipLinks!=null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //合并工装关联
        if(toolLinks!=null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
            }
        }
        //CCBegin by liunan 2011-5-27 优化
        //合并简图资源关联   Begin CR3
        /*if(drawingLinks!=null)
        {
            for (int n = 0; n < drawingLinks.size(); n++)
            {
                resourceLinks.addElement(drawingLinks.get(n));
            }
        }*/
        //End CR3
        //CCEnd by liunan 2011-5-27

        //合并零部件关联
         if(partLinks!=null)
         {
             for (int n = 0; n < partLinks.size(); n++)
             {
                 resourceLinks.addElement(partLinks.elementAt(n));
             }
         }

        //封装所有信息
        CappWrapData cappWrapData = new CappWrapData();
        //设置工序卡
        cappWrapData.setQMProcedureIfc(getProcedure());
        //封装关联
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
        //CCBegin by liunan 2011-5-27 优化
        //cappWrapData.setPDrawings(pDrawings);
        cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR3
        cappWrapData.setDeleteDrawing(deletedrawings);//End CR3
        //CCEnd by liunan 2011-5-27

        return cappWrapData;

    }

    
    //CCBegin SS11
    
    
    /**
     * 设置工序卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     */
    private CappWrapData commitAttributesForProcessControl()
    {
        //设置工序属性(编号、名称、工序种类、工序类别、加工类型、部门、关联工艺、工艺简图、
        //简图输出方式)
        //设置是工序,并设置工序种类
    	processControlJPanel.getStepProcessControlJPanelForBSX().processHoursJPanel.setProcedure(getProcedure());
        getProcedure().setIsProcedure(true);
        if (getViewMode() == CREATE_MODE)
        {
            CodingIfc code = stepType;
            getProcedure().setTechnicsType(code);
        }
        //设置编号
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
        System.out.println("检查 11  工序编号："+processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim());
        Integer i = Integer.valueOf(processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim());
        getProcedure().setStepNumber(i.intValue());
        //add by wangh on 20070208(得到并设置工序编号)
        String s=String.valueOf(processControlJPanel.getStepProcessControlJPanelForBSX().stepNumText.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //设置名称
        getProcedure().setStepName(processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.getText());
        //设置工序类别
        getProcedure().setStepClassification(
                (CodingIfc) processControlJPanel.getStepProcessControlJPanelForBSX().stepClassifiSortingSelectedPanel.getCoding());
        //设置加工类型
        getProcedure().setProcessType(
                (CodingIfc) processControlJPanel.getStepProcessControlJPanelForBSX().processTypeSortingSelectedPanel.getCoding());
        //设置部门
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
        //设置工序内容
        Vector v = new Vector();
        v.addElement(contentPanel.save());
        getProcedure().setContent(v);

        if(contentPanel.save() == null ||
                contentPanel.save().trim().equals("")){
          v.addElement(processControlJPanel.getStepProcessControlJPanelForBSX().stepNameTextField.getText());
          getProcedure().setContent(v);
        }
        //计算单件工时(如果工时面板可维护，即没有关联工艺)
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

        //设置资料夹
        if (getViewMode() == CREATE_MODE)
        {

            QMFawTechnicsInfo pTechnics = (QMFawTechnicsInfo) selectedNode.
                                          getObject().getObject();
            procedureInfo.setLocation(pTechnics.getLocation());

        }
        //维护工艺参数
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
                        System.out.println("扩展属性录入错误！");
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
										
										System.out.println("oldeqID==========设备======*************==================="+oldeqID);
										System.out.println("key==============设备==*************==================="+key);
										if(key.equals(oldeqID)){
											
											int rcount=oldCount-count;
											
											System.out.println("rcount========设备========*************==================="+rcount);
											
											try {
												
												Class[] p1 = { BaseValueIfc.class };
												Object[] ob1 = { oldeq };
												
											if(rcount>0){
												
												   oldeq.setUsageCount(rcount);
												    //更新保存
													BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
															"PersistService", "saveValueInfo",
															p1, ob1);
													
													System.out.println("=====更新===设备========*************===================");
											     }else{
												
												      //删除
											    	useServiceMethod(
																"PersistService", "deleteValueInfo",
																p1, ob1);
											    	eqDeleVec.add(oldeq);
											    	System.out.println("=====删除====设备=======*************===================");
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
											
											
											System.out.println("Float.intBitsToFloat(count)========材料========*************==================="+Float.intBitsToFloat(count));
											
											float rcount=oldCount-count;
											
											System.out.println("rcount========材料========*************==================="+rcount);
											
											try {
												
												Class[] p1 = { BaseValueIfc.class };
												Object[] ob1 = { oldMater };
												
											if(rcount>0){
												oldMater.setUsageCount(rcount);
												    //更新保存
													BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
															"PersistService", "saveValueInfo",
															p1, ob1);
													
													System.out.println("=====更新====材料=======*************===================");
											     }else{
												
												      //删除
											    	useServiceMethod(
																"PersistService", "deleteValueInfo",
																p1, ob1);
											    	materiaDeleVec.add(oldMater);
											    	System.out.println("=====删除====材料=======*************===================");
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
										
										System.out.println("oldeqID========工装========*************==================="+oldeqID);
										System.out.println("key============工装====*************==================="+key);
										if(key.equals(oldeqID)){
											
											int rcount=oldCount-count;
											
											System.out.println("rcount=======工装=========*************==================="+rcount);
											
											try {
												
												Class[] p1 = { BaseValueIfc.class };
												Object[] ob1 = { oldTool };
												
											if(rcount>0){
												
												oldTool.setUsageCount(String.valueOf(rcount));
												    //更新保存
													BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
															"PersistService", "saveValueInfo",
															p1, ob1);
													
													System.out.println("=====更新====工装=======*************===================");
											     }else{
												
												      //删除
											    	useServiceMethod(
																"PersistService", "deleteValueInfo",
																p1, ob1);
											    	toolDeleVec.add(oldTool);
											    	System.out.println("=====删除====工装=======*************===================");
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
						"控制计划");
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
														System.out.println("有相同的tool====111111111111====count============="+toolid);
														System.out.println("有相同的tool====222222222222====count============="+obj);
														if (toolid.equals(obj)) {
															String count = binarylinkinfo.getUsageCount();
                                                            if(count.indexOf("(")==-1||count.indexOf("（")==-1){
                                                            	
                                                            	//CCBegin SS6
                                                            	
	                                                            int c=Integer.parseInt(count)+Integer.parseInt(intCount);
                                                            	
                                                            	count=String.valueOf(c);
                                                            	System.out.println("有相同的tool========count============="+count);
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
//                                                           if(count.indexOf("(")==-1||count.indexOf("（")==-1){
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
        		
        		
        		
        		
        	
        	
            //设置过程控制
            procedureInfo.setProcessControl(processControlJPanel.
            		getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("工序过程控制录入错误！");
            }
            isSave = false;
            return null;
        }


        //将所有关联合并
        Vector resourceLinks = new Vector();

        //获得所有关联(设备、工装、材料、文档)
        Vector docLinks;
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
        //CCBegin by liunan 2011-5-27 优化
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
                //CCBegin 变速箱所见即所得
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
                
                
                //CCEnd 变速箱所见即多的 
            }
            if(toollinkJPanel != null)
            {
               toolLinks = toollinkJPanel.getAllLinks();
               System.out.println("toolLinks 111 ======================================="+toolLinks);
               //CCBesgin 变速箱所见即所得
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
               
               
               //CCEnd 变速箱所见即所得
               
            }
            if(materiallinkJPanel != null)
            {
               materialLinks = materiallinkJPanel.getAllLinks();
               //CCBegin 变速箱所见即所得
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
               
               //CCEnd 变速箱所见即所得
               
            }
          //CCBegin 变速箱所见即所得
            if (processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel != null)
            {
                partLinks = processControlJPanel.getStepLinkSouseControlJPanelForBSX().partlinkJPanel.getAllLinks();

            }
            Object[] obj = processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.getDrawings();
            System.out.println("obj============sssssssssssssssssss++++++++++++++++++++===================="+obj);
            //CCEnd 变速箱所见即所得
            if (obj != null)
            {
            	//CCBegin by liunan 2011-5-27 优化
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
        //合并材料关联
        if(materialLinks!=null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }

        //合并设备关联
        if(equipLinks!=null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //合并工装关联
        if(toolLinks!=null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
            }
        }

        //合并零部件关联
         if(partLinks!=null)
         {
             for (int n = 0; n < partLinks.size(); n++)
             {
                 resourceLinks.addElement(partLinks.elementAt(n));
             }
         }

        //封装所有信息
        CappWrapData cappWrapData = new CappWrapData();
        //设置工序卡
        cappWrapData.setQMProcedureIfc(getProcedure());
        //封装关联
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
    
    
    

//add by wangh on 20070207(根据不同工序种类获得不同过程流程，FMEA和过程控制)
    private void newExtendPanel(String processType)
    {
//      System.out.println("processType============"+processType);
//      System.out.println("existProcessType============"+existProcessType);
      //1111
//      if(!processType.equals("控制计划工序")){
//        jTabbedPanel.add(extendJPanel2, "过程流程");
//       jTabbedPanel.add(extendJPanel3, "过程FEMA");
//       jTabbedPanel.add(extendJPanel4, "控制计划");
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
//                  "过程流程", 1);
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
//                                               "过程FMEA", 1);
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
      	
 		  
      	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
    				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
      		  
      		  
      		  System.out.println("11111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
      		  
      		
      		 if (processControlTable.get(processType) == null) {
                 try {
                	
                 	  processControlJPanel = new CappExAttrPanelForBSX(procedureInfo.
                               getBsoName(),
                               "控制计划", 1,"工序",parentTechnics);
                   
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
  	
		  
  	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
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
//        //add by wangh on 200726(设置过程流程，过程FMEA和控制计划是否可见)
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
     * 保存新建的工序
     */
    private void saveWhenCreateForProcessControl()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkIsEmptyForProcessControl();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //检验编号是否为整型
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
        System.out.println("在 step 2 检查简图顺序号！！！");
        //CCBegin SS9
        if(processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.checkSeqNum())
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //CCEnd SS9

        //设置工序卡的所有属性和关联，并获得信息封装对象。
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
            //获得卡头，如果选择节点时节点在公共资料夹（即原本），则获得其副本
            QMTechnicsInfo pTechnics =
                    (QMTechnicsInfo) selectedNode.getObject().getObject();
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) pTechnics))
            {
                pTechnics = (QMTechnicsInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy(
                        (WorkableIfc) pTechnics);

                //调用服务，保存工序
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {String.class, String.class, CappWrapData.class};
            //参数依次为： 该工序所属的工艺卡BsoID,父节点BsoID,工序卡对象的封装类对象
            Object[] obj =
                    {pTechnics.getBsoID(), pTechnics.getBsoID(), cappWrapData};
            returnProce = (QMProcedureInfo) useServiceMethod(
                    "StandardCappService", "createQMProcedure", paraClass, obj);
            
            //CCBegin by liunan 2011-5-27 优化
            //Begin CR3
            if(drawingLinkPanel!=null&&processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            	processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.resetArrayList();
            }
            
            //End CR3
            //CCEnd by liunan 2011-5-27
      
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
           //20060727薛静修改，去掉刷新工艺节点的步骤
            //刷新树节点
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                //   pTechnics, pTechnics.getBsoID());

            //挂新节点
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

        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //提示是否连续建
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
        	//CCBegin by liunan 2010-12-17 优化\
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
     * 保存新建的工序
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkIsEmpty();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //检验编号是否为整型
        if (!checkIsInteger(numJTextField.getText().trim()))
        {
            numJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            numJTextField.grabFocus();
            return;
        }

        //设置工序卡的所有属性和关联，并获得信息封装对象。
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
            //获得卡头，如果选择节点时节点在公共资料夹（即原本），则获得其副本
            QMTechnicsInfo pTechnics =
                    (QMTechnicsInfo) selectedNode.getObject().getObject();
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) pTechnics))
            {
                pTechnics = (QMTechnicsInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy(
                        (WorkableIfc) pTechnics);

                //调用服务，保存工序
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {String.class, String.class, CappWrapData.class};
            //参数依次为： 该工序所属的工艺卡BsoID,父节点BsoID,工序卡对象的封装类对象
            Object[] obj =
                    {pTechnics.getBsoID(), pTechnics.getBsoID(), cappWrapData};
            returnProce = (QMProcedureInfo) useServiceMethod(
                    "StandardCappService", "createQMProcedure", paraClass, obj);
            
            //CCBegin by liunan 2011-5-27 优化
            //Begin CR3
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR3
            //CCEnd by liunan 2011-5-27
      
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
           //20060727薛静修改，去掉刷新工艺节点的步骤
            //刷新树节点
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                //   pTechnics, pTechnics.getBsoID());

            //挂新节点
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

        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //提示是否连续建
        if (confirmAction(QMMessage.getLocalizedMessage(RESOURCE, "108", null)))
        {
            setCreateMode();
            isSave = false;
            setButtonWhenSave(true);
            return;
        }
        else
        {
        	//CCBegin by liunan 2010-12-17 优化\
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
     * 保存更新后的工序
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() begin...");
        }
        //CCBegin by liunan 2011-6-1 优化 add by guoxl on 2009-1-8(给工序（工步）更新界面添加监听，如果界面信息更改了则给出
        TechnicsContentJPanel.addFocusLis.initFlag();
        //CCEnd by liunan 2011-6-1
        
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkIsEmpty();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //检验编号是否为整型
        if (!checkIsInteger(numJTextField.getText().trim()))
        {
            numJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            numJTextField.grabFocus();
            return;
        }

        //设置工序卡的所有属性和关联，并获得信息封装对象。
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //显示保存进度
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
                //调用服务，保存工序
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
            
            //CCBegin by liunan 2011-5-27 优化
            //Begin CR3
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR3
            //CCEnd by liunan 2011-5-27
            
            //刷新树节点
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

        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        //转换成查看界面
        //CCBegin by liunan 2010-12-17 优化
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
     * 保存更新后控制计划中工序信息
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
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkIsEmptyForProcessControl();
        
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        System.out.println("检查 工序编号："+processControlJPanel.getStepProcessControlJPanelForBSX().stepCountText.getText().trim());
        //检验编号是否为整型
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
        System.out.println("在 step 1 检查简图顺序号！！！");
        //CCBegin SS9
        if(processControlJPanel.getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.checkSeqNum())
        {
        	setCursor(Cursor.getDefaultCursor());
        	setButtonWhenSave(true);
        	isSave = false;
        	return;
        }
        //CCEnd SS9

        //设置工序卡的所有属性和关联，并获得信息封装对象。
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
                //调用服务，保存工序
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
            
            //刷新树节点
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

        //隐藏进度条
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        //转换成查看界面
        
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() end...return is void");
        }
    }
    
    
    //CCEnd SS11


    /**
     * 设置按钮的显示状态（有效或失效）
     * @param flag  flag为True，按钮有效；否则按钮失效
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
     * 设置按钮的可见性
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
       // storageJButton.setVisible(flag);

    }


    /**
     * 搜索工艺
     * @param e
     */
    void searchTechJButton_actionPerformed(ActionEvent e)
    {

        searchRelatedTechnics();
    }


    /**
     * 搜索关联工艺
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
            //添加TechnicsSearchJDialog中的mutilist的行的双击监听
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


    /**搜索关联工艺的界面对象*/
    private TechnicsSearchJDialog sd = null;


    /**关联工艺（用于缓存）*/
    //private QMFawTechnicsInfo relatedTechnics = null;
      private String relatedTechnicsID = null;

    /**
     * 搜索工艺监听事件方法
     * @param e 搜索监听事件
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
     *把数组中的工艺规程加入关联工艺”文本框中，此方法提供给TechnicsSearchJDialog的两个监听事件
     */
    private void actionPerformed(Object[] bvi)
    {
        if (bvi != null)
        {
            //把从结果域选中的业务对象加入“关联工艺”文本框
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


    /**用来处理搜索界面的双击事件*/
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
     * 工艺参数维护
     * <p>工艺参数维护为弹出窗口，其界面内属性是根据工序种类不同，显示内容不同。</p>
     * @param e
     */
    void paraJButton_actionPerformed(ActionEvent e)
    {

        String processType = "";
        //获得工序种类
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
            //创建或更新模式下
            if (mode == CREATE_MODE || mode == UPDATE_MODE)
            {
                //如果existProcessType不为空，改变了工序种类，则把工序对象的扩展属性置空
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
                //设置扩展属性
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
                            System.out.println("扩展属性录入错误！");
                        }
                        return;
                    }
                }
                existProcessType = processType;
            }
            //查看模式
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
     * 执行入库操作。将工艺编制中发现的设备与工装的关系反填回工装维护中.
     * 将设备与工装的关联保存到设备库中。设备列表和工装列表中至少有一个列表中是一条数
     * 据（设备或工装），如果设备、工装都选择了多条数据，选择入库操作时，给出提示，要求重新选择。
     * 如果设备列表中的设备是一个，则系统将该设备与工装列表中的所有工装建立关联关系。
     * 如果设备列表中的设备是多个，则必然工装列表中是一个，则系统分别将每个设备与该工装建立关联关系。
     * @param e
     */
    void storageJButton_actionPerformed(ActionEvent e)
    {
        //首先判断选择的设备及工装，至少有一个是只选了一条实例
        Vector equipLinks = equiplinkJPanel.getSelectedObjects();
        Vector toolLinks = toollinkJPanel.getSelectedObjects();

        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        if (equipLinks.size() > 0 && toolLinks.size() > 0)
        {
            if (equipLinks.size() == 1 || toolLinks.size() == 1)
            {
                //系统将该设备与工装列表中的所有工装建立关联关系。
                f1.populateEquipments(equipLinks);
                f1.populateTools(toolLinks);
                f1.setVisible(true);
            }
            else
            {
                //设备、工装列表中至少有一个列表是一条数据
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
            //"设备列表和工装列表必须都添加有数据，才能执行入库操作。请添加数据。"
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.EQUIPMENT_OR_TOOL_NOT_ONLYONE, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * 关联工艺文本框的字符更新监听事件方法
     * <p>如果选择了关联工艺，则工时信息不能维护。</p>
     * <p>如果选择了关联工艺，关联工装、设备、材料等都为不可用，并在工序下不能创建工步。</p>
     * @param e CaretEvent
     */
    void relationTechJTextField_caretUpdate(CaretEvent e)
    {
        setRelatedEff();
    }
    //CCBegin SS5
    /**
     * <p>如果选择了关联工艺，则工时信息不能维护。</p>
     * <p>如果选择了关联工艺，关联工装、设备、材料等都为不可用，并在工序下不能创建工步。</p>
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
            //无关联工艺且工序下有工步,工时不可维护，使用资源关联面板不可维护
            if (c != null && c.size() != 0)
            {
                //20060728薛静修改，是否自动维护父节点的工时做成可配置的
                String hourUpdateflag = RemoteProperty.getProperty(
                        "updateMachineHour", "true");
                if (hourUpdateflag.equals("true"))
                {
                    //工步中的工时是否大于零
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
                        //若存在子节点，且子节点使用了资源，则关联面板不可维护
                        //若存在子节点，且子节点使用了资源，则关联面板不可维护
                        
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
                //20060728薛静修改，是否自动维护父节点的资源做成可配置的
               String resourceUpdateflag = RemoteProperty.getProperty(
                       //CCBegin SS4
                       //"updateResourceLink", "true");
                       "updateResourceLink_bsx", "true");
                       //CCEnd SS4
               if (resourceUpdateflag.equals("true"))
               {
              //子节点使用资源的集合
                Collection linkCollection = null;
                //标志子节点是否使用了资源
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
                       //CCBegin by chudaming 2008-12-4 bsx工序有工步并且工步有工装、材料、设备时工序的工装、材料、设备可以更新
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
                     //CCEnd by chudaming 2008-12-4 bsx工序有工步并且工步有工装、材料、设备时工序的工装、材料、设备可以更新
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
                    //add by wangh on 20080226 设置文档可编辑.
                    doclinkJPanel.setMode("View");
                    //add end
                }
            }

            //无关联工艺且工序下无工步，则工时可维护，使用资源关联面板可维护
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
     * <p>如果选择了关联工艺，则工时信息不能维护。</p>
     * <p>如果选择了关联工艺，关联工装、设备、材料等都为不可用，并在工序下不能创建工步。</p>
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
            //modify by wangh on 20080226 将原来的查看模式改为可编辑模式
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
            //无关联工艺且工序下有工步,工时不可维护，使用资源关联面板不可维护
            if (c != null && c.size() != 0)
            {
                //20060728薛静修改，是否自动维护父节点的工时做成可配置的
                String hourUpdateflag = RemoteProperty.getProperty(
                        "updateMachineHour", "true");
                if (hourUpdateflag.equals("true"))
                {
                    //工步中的工时是否大于零
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
                        //若存在子节点，且子节点使用了资源，则关联面板不可维护
                        //若存在子节点，且子节点使用了资源，则关联面板不可维护
                        
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
                //20060728薛静修改，是否自动维护父节点的资源做成可配置的
               String resourceUpdateflag = RemoteProperty.getProperty(
                       "updateResourceLink", "true");
               if (resourceUpdateflag.equals("true"))
               {
              //子节点使用资源的集合
                Collection linkCollection = null;
                //标志子节点是否使用了资源
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
                       //CCBegin by chudaming 2008-12-4 bsx工序有工步并且工步有工装、材料、设备时工序的工装、材料、设备可以更新
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
                     //CCEnd by chudaming 2008-12-4 bsx工序有工步并且工步有工装、材料、设备时工序的工装、材料、设备可以更新
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
                    //add by wangh on 20080226 设置文档可编辑.
                    doclinkJPanel.setMode("View");
                    //add end
                	//CCBegin SS5
                }
            }

            //无关联工艺且工序下无工步，则工时可维护，使用资源关联面板可维护
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
     * 把给定的业务对象添加到相应的关联列表中
     * @param info 给定的业务对象（资源）
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
     * 给定工艺主信息，获得工艺卡的所有版本中的最新小版本
     * @param masterInfo 工艺主信息
     * @return 工艺卡的所有版本中的最新小版本
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
        //调用服务方法，获得工艺卡的所有小版本（包括不同分枝）
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
            //获得工艺卡的最新小版本
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
     * 删除关联工艺
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
     *此方法用来清除工序维护界面中的内容
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

        //重新实例化工艺内容
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
     * 创建模式时调用此方法.负责实例化工序值对象,处理简图输出组建
     * 改变part关联,动态配置
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
            { //根据录入的工序种类，创建不同的工序卡
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
            relationsJPanel.add(doclinkJPanel, "文档");
            relationsJPanel.add(drawingLinkPanel, "简图");
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
     * 创建模式时调用此方法.负责实例化工序值对象,处理简图输出组建
     * 改变part关联,动态配置
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
            { //根据录入的工序种类，创建不同的工序卡
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
            relationsJPanel.add(doclinkJPanel, "文档");
            relationsJPanel.add(drawingLinkPanel, "简图");
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
     * 重新实例化part关联
     * @param stepType String
     */
    //private synchronized void newPartlinkJPanel(String stepType)
    private void newPartlinkJPanel(String stepType)//anan
    {
        //动态配置工序使用零部件关联
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
            relationsJPanel.add(partlinkJPanel, "零部件");
        }
    }


     /**
     * 重新实例化part关联
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

            //加此监听的原因：当设备关联面板加入设备时，工装关联面板要加入与设备必要关联的工装
            equiplinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  //1222
                    //toollinkJPanel.addRelationTools(e);
                }
            });

            equiplinkJPanel.setToolPanel(toollinkJPanel);
            //relationsJPanel.add(equiplinkJPanel, "设备");

        relationsJPanel.add(equiplinkJPanel, "设备");
    }
    /**
     * 重新实例化part关联
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
            //加此监听的原因：当设备关联面板加入设备时，工装关联面板要加入与设备必要关联的工装
            toollinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    equiplinkJPanel.addRelationEquipments(e);
                }
            });
            toollinkJPanel.setEquipmentPanel(equiplinkJPanel);

        relationsJPanel.add(toollinkJPanel, "工装");
    }

    /**
     * 重新实例化part关联
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
            relationsJPanel.add(materiallinkJPanel, "材料");

    }

    /**
     * 删掉原来的部门，重新实例化（原因是工艺卡部门有可能更新）
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
     * 初始化工序种类
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
     * 设置工序种类
     * @param codeContent String
     */
    public void setStepType(String codeContent)
    {
        stepType = (CodingIfc) stepTypetable.get(codeContent);
    }

    /**
     * 为工艺内容面板添加特殊符号
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
     * <p>Title:工作线程 </p>
     * <p>Description: 用来保存的线程</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author not 薛静
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
   *此方法用来清除工序维护界面中的内容
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

      //重新实例化工艺内容
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
  
  //CCBegin by liunan 2011-4-22 打补丁v4r1_p037
  /**
   * <p>Title:工作线程 </p>
   * <p>Description: 用于调用简图服务，优化更新工艺，提高速度</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: 一汽启明</p>
   * @author 薛凯 2008 04 28 添加
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
