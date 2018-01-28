package com.faw_qm.cappclients.capp.view;
/**
 * SS1 郭晓亮 2014-07-04 修改服务平台问题A034-2014-0109
 * SS2 郭晓亮 2014-07-04 补充的注释信息，工时信息带到过程流程里
 * SS3 郭晓亮 2014-07-04 补充的注释信息，扩展属性文件中属性名变化而更改
 * SS4 郭晓亮 2014-07-07 修改服务平台问题A034-2014-0114
 * SS5 传递功能将SpecialCharacter都传成text了，导致错误，所以修改 pante 2014-10-14
 * SS6 修改轴齿创建工序（步）时自动在TS16949面板中增加一行空行并带出工序编号问题,取消SS2的修改 pante 2014-10-21
 * SS7 轴齿过程流程每个设备都要对应工时信息 pante 2014-10-29
 * SS8 轴齿需求再次改变，过程流程中第一行有设备时工时对应设备，没有时也要带上工时 pante 2015-02-11
 * SS9 维护工序资源，与工序关联资源不一致。 liunan 2015-6-1
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
 * 轴齿工艺所见即所得功能新加类 郭晓亮
 * @author a
 *
 */
public class TechnicsStepJPanelForZC extends TechnicsStepJPanel{
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
    private JLabel workshopJLabel = new JLabel();
    private JLabel workshopDisJLabel = new JLabel();


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
    private ProcedureUsageToolJPanel toollinkJPanel ;


    /**材料关联的维护面板*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**文档关联的维护面板*/
    private ProcedureUsageDocJPanel doclinkJPanel = new ProcedureUsageDocJPanel();


    /**入库按钮*/
    private JButton storageJButton = new JButton();

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
    private CappExAttrPanelForZC processFlowJPanel;
    private CappExAttrPanelForZC femaJPanel;
    private CappExAttrPanelForZC processControlJPanel;

    //add by wangh on 20070326(是否显示TS16949的工序或者工步信息。)
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    

    //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
    //然后双击第一层结构节点后展开第二层结构
    //只有一级菜单的选择添加项目，改为下拉列表方式添加
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
     * 构造函数
     * @param parent 调用本类的父窗口
     */
    public TechnicsStepJPanelForZC(JFrame parent)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 改成30个汉字，60个字符。
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 打补丁 v4r3_p014_20100415        
            contentPanel = new SpeCharaterTextPanel(parent,true);//CR7
            //CCEnd by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
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
    public TechnicsStepJPanelForZC(JFrame parent, BaseValueIfc technics)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 改成30个汉字，60个字符。
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
            contentPanel = new SpeCharaterTextPanel(parent,true);
            //CCEnd by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
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
        //问题（3） 20080704 徐春英修改 修改原因：工序关联表里添加预留属性用
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
        descNumJTextField = new CappTextField(parentJFrame, procedureNumDisp1,
                                              10, false);

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
        stepClassifiJLabel.setText("*工序类别");
        processTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        processTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        processTypeJLabel.setText("*加工类型");
        workshopJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workshopJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        workshopJLabel.setText("*部门");
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
        processHoursJPanel = new ProcessHoursJPanel(parentJFrame,true);
        storageJButton.setMaximumSize(new Dimension(97, 23));
        storageJButton.setMinimumSize(new Dimension(97, 23));
        storageJButton.setPreferredSize(new Dimension(97, 23));
        storageJButton.setMnemonic('T');
        storageJButton.setText("入库");
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
        //用于查看模式
        upJPanel.add(nameDisplayJLabel,
                     new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));

        //用于更新和查看模式
        upJPanel.add(stepTypeDisJLabel,
                     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));
        //用于查看模式
        upJPanel.add(processTypeDisJLabel,
                     new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));

        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加
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

        //用于查看模式
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

        //用于查看模式

        upJPanel.add(relationTechJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 20, 0, 0), 0, 0));
        //用于查看模式
        upJPanel.add(stepClassiDisJLabel,
                     new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));
        //20080811 XUCY
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加
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
        //用于查看模式
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
//        //加此监听的原因：当设备关联面板加入设备时，工装关联面板要加入与设备必要关联的工装
//        equiplinkJPanel.addListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                toollinkJPanel.addRelationTools(e);
//            }
//        });
        //加此监听的原因：当工装关联面板加入工装时，设备关联面板要加入与工装必要关联的设备
//        toollinkJPanel.addListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                equiplinkJPanel.addRelationEquipments(e);
//            }
//        });
        //add by wangh on 20070201(将工序信息,过程流程,过程FMEA和控制计划Panel加入到jTabbedPanel中)
        jTabbedPanel.add(extendJPanel, "工序信息");
        if (ts16949)
        {
            jTabbedPanel.add(extendJPanel2, "过程流程");
            jTabbedPanel.add(extendJPanel3, "过程FMEA");
            jTabbedPanel.add(extendJPanel4, "控制计划");
        }
//20080820
//        equiplinkJPanel.setToolPanel(toollinkJPanel);
//        toollinkJPanel.setEquipmentPanel(equiplinkJPanel);
        //20080820
        //relationsJPanel.add(equiplinkJPanel, "设备");
//        relationsJPanel.add(toollinkJPanel,
//                            "工装");
//        relationsJPanel.add(materiallinkJPanel,
//                            "材料");
//        relationsJPanel.add(doclinkJPanel,
//                            "文档");
//        relationsJPanel.add(drawingLinkPanel, "简图");
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
            stepClassifiJLabel.setText("*工序类别");
            processTypeJLabel.setText("*加工类型");
            workshopJLabel.setText("*部门");
            //薛凯修改 20080219 修改原因：在工艺规程中在工艺下创建工序,工序编号为必输项字段前有*
            descStepNumberJLabel.setText("*工序编号");
            //薛凯修改结束
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
 * 鼠标监听内部类
 * @author new
 * CR2 郭晓亮(guoxl) on 2009-1-15(点击关联工艺文本框，扭转到此工艺的查看界面) 
 * 
 */
	class MouseListener extends MouseAdapter {

		TechnicsRegulationsMainJFrame tecframe;

		// 鼠标进入
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof JTextField) {
				relationTechJTextField.setForeground(Color.blue);
				relationTechJTextField.setToolTipText("单击可查看此工艺");
				// 光标为手型
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else if (e.getSource() instanceof JLabel) {
				relationTechDisJLabel.setForeground(Color.blue);
				relationTechDisJLabel.setToolTipText("单击可查看此工艺");
				// 光标为手型
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

		}

		// 鼠标离开
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof JTextField) {
				relationTechJTextField.setForeground(Color.black);
				setCursor(Cursor.getDefaultCursor());
			} else if (e.getSource() instanceof JLabel) {
				relationTechDisJLabel.setForeground(Color.black);
				setCursor(Cursor.getDefaultCursor());
			}
		}

		// 鼠标释放
		public void mouseReleased(MouseEvent e) {
			// 获得关联工艺bsoid
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

					// 将关联工艺挂到另一个工艺管理器的树上,并显示此工艺的查看界面.
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
	 * 设置在工艺树所选择的节点
	 * 
	 * @param parentnode
	 */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setTechnics();
    }


    /**
	 * 获取当前选择的工序的工艺卡头
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
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加
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
        changeWorkShopSortingSelectedPanel();
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        //工序编号
        numJTextField.setVisible(true);
        numJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
        numDisplayJLabel.setVisible(false);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(String.valueOf(getProcedure().
                                                 getDescStepNumber()));
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
        this.setComboBox(stepTypeComboBox, getProcedure().getStepClassification());     
        stepClassiDisJLabel.setVisible(false);
        //加工类型
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加 begin
        processTypeComboBox.setVisible(true);
        this.stepTypeComboBox.setVisible(true);
        ResourcePanel  rsPanel = new ResourcePanel();
        rsPanel.setComboBox(processTypeComboBox,getProcedure().getProcessType());
        processTypeDisJLabel.setVisible(false);
        //部门
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
          //CCBegin by leixiao 2010-4-1 工序部门取工艺部门
            String workshop="";
            if(getProcedure().getWorkShop() instanceof CodingClassificationIfc){
            	workshop=((CodingClassificationIfc)getProcedure().getWorkShop()).getCodeSort();
            }
            else{
            	workshop=((CodingIfc)getProcedure().getWorkShop()).getCodeContent();
            }
            workshopDisJLabel.setText(workshop);
          //CCBegin by leixiao 2010-4-1 工序部门取工艺部门
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        }
       
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
        
        System.out.println("aaaaaaaaaaaaa");//anan
      //add by guoxl on 2009-1-7(给更新界面添加监听，如果界面信息改变则给出是否保存提示，否则不提示)
        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
        //add by guoxl end
    }


    /**
     * 设置界面为查看模式，并将工序属性显示在界面上
     * 问题（8）20090112  刘志城修改 修改原因：优化工序查看速度，应改为：在查看模式下不刷简图值对象（blob），
     *                              点击关联简图面板上的查看按钮时，调底层刷新简图方法。
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
        //工序号
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
        stepClassiDisJLabel.setVisible(true);
        stepClassiDisJLabel.setText(
                getProcedure().getStepClassification().getCodeContent());
        //加工类型
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加 begin
        processTypeComboBox.setVisible(false);
        //工序类别
        stepTypeComboBox.setVisible(false);
        processTypeDisJLabel.setVisible(true);
        processTypeDisJLabel.setText(getProcedure().getProcessType().
                                     getCodeContent());
        //工序部门
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setVisible(false);
        }
        workshopDisJLabel.setVisible(true);
        //CCBegin by leixiao 2010-4-1 工序部门取工艺部门
        String workshop="";
        if(getProcedure().getWorkShop() instanceof CodingClassificationIfc){
        	workshop=((CodingClassificationIfc)getProcedure().getWorkShop()).getCodeSort();
        }
        else{
        	workshop=((CodingIfc)getProcedure().getWorkShop()).getCodeContent();
        }
        	
        workshopDisJLabel.setText(workshop);
      //CCEnd by leixiao 2010-4-1 工序部门取工艺部门 

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

        setButtonVisible(false);
        //薛凯 修改 20080428 用于优化更新工艺，提高速度
        //问题（8）20090112  刘志城修改 修改原因：优化工序查看速度，应改为：在查看模式下不刷简图值对象（blob），
        //                           点击关联简图面板上的查看按钮时，调底层刷新简图方法。
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
        //薛凯 修改结束 20080428
        //关联
        doclinkJPanel.setMode("View");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "文档");
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
        if (isOK)
        {
            isOK = descNumJTextField.check();
        }
        if (isOK)
        {

//            //检验加工类型是否为空
//            if (processTypeSortingSelectedPanel.getCoding() == null)
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_PROCESSTYPE_ENTERED,
//                        null);
//                isOK = false;
//                processTypeSortingSelectedPanel.getJButton().grabFocus();
//            }
        	//问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
            //然后双击第一层结构节点后展开第二层结构
            //只有一级菜单的选择添加项目，改为下拉列表方式添加
        	if(processTypeComboBox.getSelectedItem().equals("") || processTypeComboBox.getSelectedItem() == null)
        	{
                message = QMMessage.getLocalizedMessage(RESOURCE,
                       CappLMRB.NO_PROCESSTYPE_ENTERED,
                       null);
                isOK = false;
                processTypeComboBox.grabFocus();
        	}
            //检验工艺内容是否为空
        	
//            else if (contentPanel.save() == null ||
//                     contentPanel.save().trim().equals(""))
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                        null);
//                isOK = false;
//                contentPanel.getTextComponent().grabFocus();
//            }
        	
        	
            //检验工序类别是否为空
        	//问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
            //然后双击第一层结构节点后展开第二层结构
            //只有一级菜单的选择添加项目，改为下拉列表方式添加 begin
            else if (this.stepTypeComboBox.getSelectedItem().equals("") || stepTypeComboBox.getSelectedItem() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_STEPCLASSIFI_ENTERED,
                        null);
                isOK = false;
                stepTypeComboBox.grabFocus();
            }
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
                setCreateMode();
                break;
            }

            case UPDATE_MODE: //更新模式
            {
                setUpdateMode();
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
            saveWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)//CR1
        {
            saveWhenUpdate();
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
     * 更新模式下，退出按钮的执行方法
     * add by guoxl 2009-1-7(只有更新界面数据修改了,才弹出是否保存对话框)
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
    private int confirmAction(String s)//CR1
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(), s, title,
				JOptionPane.YES_NO_CANCEL_OPTION);//CR1
    }


    /**
     * 设置工序卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     * 问题(3) 20080602 徐春英修改  修改原因:添加工序关联文档时,普通用户能搜索出全部文档,因为
     * 搜索出来的是文档master没有权限控制,现在要求搜索结果显示文档,这样就有权限控制了
     * 当保存到数据库时,应该转换成文档master和工序的关联
     */
    private CappWrapData commitAttributes()
    {
    	
    	this.eqVec.clear();
    	this.toolVec.clear();
    	this.materiaVec.clear();
    	
    	this.eqDeleVec.clear();
    	this.toolDeleVec.clear();
    	this.materiaDeleVec.clear();
    	
    	
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
        String s = String.valueOf(descNumJTextField.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //设置名称
        getProcedure().setStepName(nameJTextField.getText());
        //设置工序类别
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加 begin
        getProcedure().setStepClassification((CodingIfc)stepTypeComboBox.getSelectedItem());
        getProcedure().setProcessType((CodingIfc)processTypeComboBox.getSelectedItem());
        //设置部门
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
            //  CCBeginby leixiao 2010-4-2 原因：工艺部门可修改,工序部门取工艺部门
                getProcedure().setWorkShop((BaseValueIfc)
                                           parentTechnics.getWorkShop());
            //  CCEndby leixiao 2010-4-2 原因：工艺部门可修改,工序部门取工艺部门
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
            
            }
            
            
            
            
	        int groupCount=processFlowJPanel.getExAttr().getAttGroupCount();
    		
        	if (groupCount > 0) {
				Vector vec = processFlowJPanel.getExAttr().getAttGroups(
						"特性控制");
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
										 System.out.println("eqVec============**************=====过程流程=========================="+eqVec);
											 
										 
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
            
        	  //设置过程流程
            procedureInfo.setProcessFlow(processFlowJPanel.
                                         getExAttr());
            
        }
        else
        {
            if (verbose)
            {
                System.out.println("工序过程流程录入错误！");
            }
            isSave = false;
            return null;
        }
        if (femaJPanel.check())
        {
            //设置FMEA
            procedureInfo.setFema(femaJPanel.getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("工序FMEA录入错误！");
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
									
									System.out.println("oldeqID==========设备======*************==================="+oldeqID);
									System.out.println("key==============设备==*************==================="+key);
									if(key.equals(oldeqID)){
										
										//int rcount=oldCount-count;
										
										//System.out.println("rcount========设备========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldeq };
											
										/*if(rcount>0){
											
											   oldeq.setUsageCount(rcount);
											    //更新保存
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====更新===设备========*************===================");
										     }else{*/
											
											      //删除
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	eqDeleVec.add(oldeq);
										    	System.out.println("=====删除====设备=======*************===================");
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
										
										
										//System.out.println("Float.intBitsToFloat(count)========材料========*************==================="+Float.intBitsToFloat(count));
										
										//float rcount=oldCount-count;
										
										//System.out.println("rcount========材料========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldMater };
											
										/*if(rcount>0){
											oldMater.setUsageCount(rcount);
											    //更新保存
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====更新====材料=======*************===================");
										     }else{*/
											
											      //删除
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	materiaDeleVec.add(oldMater);
										    	System.out.println("=====删除====材料=======*************===================");
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
									
									
									System.out.println("oldeqID========工装========*************==================="+oldeqID);
									System.out.println("key============工装====*************==================="+key);
									if(key.equals(oldeqID)){
										
										//int rcount=oldCount-count;
										
										//System.out.println("rcount=======工装=========*************==================="+rcount);
										
										try {
											
											Class[] p1 = { BaseValueIfc.class };
											Object[] ob1 = { oldTool };
											
										/*if(rcount>0){
											
											oldTool.setUsageCount(String.valueOf(rcount));
											    //更新保存
												BaseValueIfc Info = (BaseValueIfc) useServiceMethod(
														"PersistService", "saveValueInfo",
														p1, ob1);
												
												System.out.println("=====更新====工装=======*************===================");
										     }else{*/
											
											      //删除
										    	useServiceMethod(
															"PersistService", "deleteValueInfo",
															p1, ob1);
										    	toolDeleVec.add(oldTool);
										    	System.out.println("=====删除====工装=======*************===================");
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
						"控制计划");
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
															
															
                                                            if(count!=null&&(count.indexOf("(")==-1||count.indexOf("（")==-1)){
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
        //20080820
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
//        ArrayList pDrawings = null;
//        ArrayList drawingLinks = null;
		ArrayList updatedrawings = null;//Begin CR3
		ArrayList deletedrawings = null;//End CR3
        //问题(3) 20080602 徐春英修改  将搜索到的文档转换成文档master和工序的关联
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
                		/*System.out.println("newlinkinfo.getLeftBsoID()================序=================="+newlinkinfo.getLeftBsoID());
                		
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

        //合并文档关联
        if (docMasterLinks != null)
        {
            for (int k = 0; k < docMasterLinks.size(); k++)
            {
                resourceLinks.addElement(docMasterLinks.elementAt(k));
            }
        }

        //合并材料关联
        if (materialLinks != null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }
        
        //合并设备关联
        if (equipLinks != null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //合并工装关联
        if (toolLinks != null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
            }
        }
		// 合并简图资源关联   Begin CR3
		/*if (drawingLinks != null)
		{
			for (int n = 0; n < drawingLinks.size(); n++)
			{
				resourceLinks.addElement(drawingLinks.get(n));
			}
		}*/  
        //End CR3

        //合并零部件关联
        if (partLinks != null)
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

//add by wangh on 20070207(根据不同工序种类获得不同过程流程，FMEA和过程控制)
    //问题（5）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
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
                            "过程流程", 1);
                    
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
            // CCBegin by leixiao 2008-10-28 原因：解放系统升级,用于数据传递 
            processFlowJPanel.setProIfc(procedureInfo);
           
//          CCEnd by leixiao 2008-10-28 原因：解放系统升级 
            if (femaJPanel != null)
            {
                extendJPanel3.remove(femaJPanel);
            }
            if (femaTable.get(processType) == null)
            {
                try
                {
                    femaJPanel = new CappExAttrPanelForZC(procedureInfo.getBsoName(),
                            "过程FMEA", 1);
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
            // CCBegin by leixiao 2008-10-27 原因：解放系统升级 
            femaJPanel.setProIfc(procedureInfo);
            // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
            
         
            
            
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
                            "控制计划", 1);
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
            // CCBegin by leixiao 2008-10-27 原因：解放系统升级 
            processControlJPanel.setProIfc(procedureInfo);
            // CCEnd by leixiao 2008-10-27 原因：解放系统升级
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

        //add by wangh on 200726(设置过程流程，过程FMEA和控制计划是否可见)
//       if (!ts16949) {
//         processFlowJPanel.setVisible(false);
//         femaJPanel.setVisible(false);
//         processControlJPanel.setVisible(false);
//       }
        //问题（5）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
        processControlJPanel.groupPanel.flowMultiList = processFlowJPanel.groupPanel.multiList;
        //processControlJPanel.groupPanel.processFlowJPanel=processFlowJPanel;
        repaint();
        processType = "";
    }


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

        //设置工序卡的所有属性和关联，并获得信息封装对象。
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
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //20060727薛静修改，去掉刷新工艺节点的步骤
            //刷新树节点
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
            //   pTechnics, pTechnics.getBsoID());
            //begin cr4
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//end cr4
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
     * 保存更新后的工序
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() begin...");
        }
        
        //add by guoxl on 2009-1-8(给工序（工步）更新界面添加监听，如果界面信息更改了则给出
        // 是否保存提示，否则不提示)
         TechnicsContentJPanel.addFocusLis.initFlag();
         //add by guoxl end
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
            //begin cr4
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }
            //end cr4
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //刷新树节点
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
        	//20081119 xucy 修改   修改原因：出异常的时候把进度条关掉
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

        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;  
        //问题（6）20081226 xucy  修改原因：优化更新时保存工序    begin 
        //转换成查看界面
//        try
//        {
//            setViewMode(VIEW_MODE);
//        }
//        catch (PropertyVetoException ex1)
//        {
//        	//20081119 xucy 修改   修改原因：出异常的时候把进度条关掉
//        	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
//            ex1.printStackTrace();
//        }
        //问题（6）20081226 xucy  修改原因：优化更新时保存工序   end
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() end...return is void");
        }
       
    }


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
        storageJButton.setEnabled(flag);
    }


    /**
     * 设置按钮的可见性
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
        storageJButton.setVisible(flag);

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
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
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
                d.setProcedure(getProcedure());
                
                //CCBegin by liunan 2011-08-25 打补丁P035
                //CR12 begin
                TechnicsContentJPanel.addFocusLis.setCompsFocusListener(d.getContentPane());
                //CR12 end
                //CCEnd by liunan 2011-08-25
                
                d.setEditMode();
                d.setVisible(true);
                //CCBegin by liunan 2011-08-25 打补丁P035
                //CR11 start
                if(d.getIsOk())
                {
                //CCEnd by liunan 2011-08-25
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
                //CCBegin by liunan 2011-08-25 打补丁P035
              }
                //CCEnd by liunan 2011-08-25
                existProcessType = processType;
            }
            //查看模式
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
                        processHoursJPanel.setViewMode();
                    }
                    else
                    {
                        processHoursJPanel.setEditMode();
                        //若存在子节点，且子节点使用了资源，则关联面板不可维护
                        //若存在子节点，且子节点使用了资源，则关联面板不可维护

                    }
                }
                else
                {
                    processHoursJPanel.setEditMode();
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
                    //20081205 徐春英修改   修改原因：有的工序没有和part的关联，
                    //所以partlinkJPanel可能为空
                    if(partlinkJPanel != null)
                    partlinkJPanel.setMode("Edit");
                    //add by wangh on 20080226 设置文档可编辑.
                    doclinkJPanel.setMode("Edit");
                    //add end
                }
            }

            //无关联工艺且工序下无工步，则工时可维护，使用资源关联面板可维护
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
     * 把给定的业务对象添加到相应的关联列表中
     * @param info 给定的业务对象（资源）
     */
     //CCBegin by leixiao 2010-6-30 打补丁v4r3_p017_20100617 见标识CR8
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
                     // 得到与该设备关联的工装
                     tools = cequip.getTools();
                     if(tools != null && tools.size() != 0)
                     {
                         // 遍历工装
                         for(int j = 0;j < tools.size();j++)
                         {
                             // 如果关联是必要的
                             if(((Boolean)((Object[])tools.elementAt(j))[1]).booleanValue() == true)
                             {
                                 // 把工装加入列表中
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
                        // 得到与该设备关联的工装
                        equips = ctool.getEquips();
                        if(equips != null && equips.size() != 0)
                        {
                            // 遍历工装
                            for(int j = 0;j < equips.size();j++)
                            {
                                // 如果关联是必要的
                                if(((Boolean)((Object[])equips.elementAt(j))[1]).booleanValue() == true)
                                {
                                    // 把设备加入列表中
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
  //CCEnd by leixiao 2010-6-30 打补丁v4r3_p017_20100617

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
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加
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

        //重新实例化工艺内容
        upJPanel.remove(contentPanel);
        //CCBegin by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
        contentPanel = new SpeCharaterTextPanel(parentJFrame,true);//CR7
        //CCEnd by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
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
        //20080811 徐春英修改
        //jTabbedPanel.setSelectedIndex(0);
    }


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
            relationsJPanel.add(doclinkJPanel, "文档");
            relationsJPanel.add(drawingLinkPanel, "简图");
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
     * 重新实例化part关联
     * @param stepType String
     */
    private synchronized void newPartlinkJPanel(String stepType)
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
            //CCBegin SS1
            partlinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            relationsJPanel.add(partlinkJPanel, "零部件");
        }
    }
    
    /**
     * 重新实例化part关联
     * @param stepType String
     */
    private  void newDoclinkJPanel(String stepType)
    {
        
        relationsJPanel.add(doclinkJPanel, "文档");
        
    }
    
    /**
     * 重新实例化part关联
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
            //问题（7）20090108  徐春英修改    初始化设备关联面板的时候也初始化工装关联面板   不然会出现空指针异常
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType);
            //加此监听的原因：当设备关联面板加入设备时，工装关联面板要加入与设备必要关联的工装
            equiplinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    toollinkJPanel.addRelationTools(e);
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
            relationsJPanel.add(materiallinkJPanel, "辅料");
            //CR5 end        
    }
    
    /**
     * 删掉原来的部门，重新实例化（原因是工艺卡部门有可能更新）
     */
    private void changeWorkShopSortingSelectedPanel()
    {

        if (workshopSortingSelectedPanel != null)
        {
            upJPanel.remove(workshopSortingSelectedPanel);
            workshopSortingSelectedPanel = null;
        }
        //CCBegin by leixiao 2010-4-1 工序部门取工艺部门
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
          //CCEnd by leixiao 2010-4-1 工序部门取工艺部门
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
     * 设置选择的单位
     * @param comboBox 列表框
     * @param coding 代码项
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


    void descNumJTextField_actionPerformed(ActionEvent e)
    {

    }
//CCBegin SS1
    
    public class ChuanDiActionListener implements ActionListener{
    	
    	
    	public ChuanDiActionListener(){
    		
    	}

    	public void actionPerformed(ActionEvent arg0) {
    		
    		//过程
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
    		//过程FMEA
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
    		//控制计划
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
     * <p>Title:工作线程 </p>
     * <p>Description: 用于调用简图服务，优化更新工艺，提高速度</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author 薛凯 2008 04 28 添加
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
