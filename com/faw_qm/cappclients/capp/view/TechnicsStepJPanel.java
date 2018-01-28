/** 生成程序TechnicsStepJPanel.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/27 徐春英  原因：在工序的“是否保存”提示界面中添加取消按钮，目前为“是”和“否”
 *                       方案：在打开一个工序进行更新时，工序之间进行切换或关闭时，在弹出的“是否保存”提示框上添加一个“取消”按钮。
 *                       备注：变更记录标记为CRSS-005    
 *                               
 * CR2  郭晓亮 2009/01/15 原因:用户查看工序时，看到了关联工艺，无法方便的对被关联工艺点击进行进一步查看                             
 *                       方案:
 *                       备注:变更记录标记"CRSS-006"     
 *                       
 * CR3 2009/04/05 刘志城  原因：优化工序、工步查看，减少查询数据库次数。
 *                       方案：避免刷简图值对象。
 *                       备注：性能测试用例名称："工艺树节点切换（大简图工序、工步查看）"。 
 *                         
 * CR4 2009/04/29 李磊     原因：工序保存时判断简图是否更改
 *                        方案：
 *                        备注：性能测试用例名称："工序更新"。
 * CR5 2009/04/27 刘学宇    原因:将工序中关联“材料”tab页名称改为“辅料”（实施提出的问题）
 *                          方案:将工序中关联“材料”tab页名称改为“辅料”  
 *                          备注:变更记录标记"CRSS-010"
 * CR6 2009/04/05 刘志城    原因：优化撤销检出逻辑，减少查询数据库次数。
 *                         方案：去掉判断是否被别的关联，优化撤销检出代码逻辑。
 *                         备注：性能测试用例名称：“工艺撤销检出”。  
 *CR7 2010/04/02 徐春英   原因：参见TD问题2245                            
 * CR8 2010/06/04  徐春英 原因:参见TD问题2263                
 * CCBegin by liunan 2011-08-25 打补丁P035   
 * CR11 20110706  lvh      参见TD2419
 * CR12 2011/07/14 吕航 参见TD2423      
 * CCEnd by liunan 2011-08-25      
 * SS1 工步中增加关联“累加到工序”并且不在工序中显示 leixiao 2013-10-14      
 * SS2 工序工步关联简图,添加顺序号 liuyang 2014-04-03      
 * SS3 成都预览工序，工艺功能 guoxiaoliang 2016-7-25         
 * SS4 成都工序添加获得子件功能  guoxiaoliang 2016-7-28     
 * SS5 成都工序界面添加关联工序功能  guoxiaoliang 2016-7-28    
 * SS6 成都工步关联资源添加工序资源功能 guoxiaoliang 2016-8-2         
 * SS7 成都添加分装点或子组属性  guoxiaoliang  2016-10-8     
 * SS8 成都检查作业指导书加工类型默认选择检查工序，工序种类默认一般工序  guoxiaoliang 2016-11-11
 * SS9 成都工位号可以输入字母  guoxiaoliang 2016-11-12
 */
package com.faw_qm.cappclients.capp.view;

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

import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMFawTechnicsMasterInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMProcedureQMDocumentLinkInfo;
import com.faw_qm.capp.model.QMProcedureQMEquipmentLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreePanel;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.TermTextField;
import com.faw_qm.cappclients.resource.view.ResourcePanel;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.print.lightweightfile.util.LightweightFileTool;
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
 * <p>Title:工序维护面板</p>
 * <p>为工艺规程浏览器提供服务。</p>
 * <p>提供三种显示模式(创建、更新、查看)，完成工序的创建、更新、查看操作。</p>
 * <p>有关工时信息维护:如果选择了关联工艺，则工时信息不能维护。如果工序下有工步节点，</p>
 * <p>且工步的工时信息不为“0”或空，则工序中的工时信息应为工步工时的和，而不以工序中</p>
 * <p>手工录入的值为准。</p>
 * <p>工艺种类可以是多级的，第一级是工艺种类，第二级是工序种类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 *（1）20060727薛静修改，修改方法saveWhenCreate(),去掉刷新工艺节点的步骤
 *（2）20060728薛静修改，原来在维护工步的工时、资源关联时，自动维护工序的工时、资源关联，即工序工时等于所有工步工时的和，
 *      资源也一样。现在做成可配置的,即在属性文件中配置是否要维护这种关系.修改方法 setRelatedEff()
 * 问题（3） 20080704 徐春英修改 修改原因：工序关联表里添加预留属性用
 * 问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
 * 然后双击第一层结构节点后展开第二层结构
 * 只有一级菜单的选择添加项目，改为下拉列表方式添加
 * 问题（5）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
 * 问题（6）20081226 xucy  修改原因：优化更新时保存工序
 * 问题（7）20090108  徐春英修改    初始化设备关联面板的时候也初始化工装关联面板   不然会出现空指针异常
 * 问题（8）20090112  刘志城修改 修改原因：优化工序查看速度，应改为：在查看模式下不刷简图值对象（blob），
 *                                  点击关联简图面板上的查看按钮时，调底层刷新简图方法。
 *  
 */

public class TechnicsStepJPanel extends ParentJPanel
{
    /**工序维护面板*/
//  private JPanel stepJPanel = new JPanel();
    /**按钮面板*/
    private JPanel buttonJPanel = new JPanel();


    /**编号*/
    private JLabel numJLabel = new JLabel();
    //CCBegin SS7
    //分装点和子组信息
    private JLabel sonTeamDateJLabel = new JLabel();
    private JTextField sonTeamDateTextField= new JTextField();
    private JLabel sonTeamDateDisJLabel = new JLabel();
    //CCEnd SS7

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
    
    //CCBegin SS5
    
    private JLabel relationJLabel = new JLabel();
    
    private JButton searchTechJButton1 = new JButton();
    private JButton deleteTechJButton1 = new JButton();
    private JButton viewProcedureJButton = new JButton();
    private JTextField relationJTextField = new JTextField();
    private JLabel relationDisJLabel = new JLabel();
    public QMFawTechnicsInfo technicsinfo;
    
    private JPanel relationJPanel = new JPanel();
    private GridBagLayout gridBagLayout8 = new GridBagLayout();
    //CCEnd SS5


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
    
    //CCBegin SS5
    
    public QMProcedureIfc relatedProcedure = null;
    //CCEnd SS5


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
    private CappExAttrPanel processControlJPanel;

    //add by wangh on 20070326(是否显示TS16949的工序或者工步信息。)
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    

    //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
    //然后双击第一层结构节点后展开第二层结构
    //只有一级菜单的选择添加项目，改为下拉列表方式添加
    private JComboBox processTypeComboBox = new JComboBox();
    private JComboBox stepTypeComboBox = new JComboBox();
    
    
    //CCBegin SS3
    //预览一个工序的各个卡片
    private JButton viewproJButton = new JButton();

    //预览本工序所在工艺的各个卡片
    private JButton viewTechnicsJButton = new JButton();
    
    //用来标识是否执行预览按钮
    boolean isview = false;
    //CCEnd SS3
    
    public TechnicsStepJPanel(){
    	super();}
    
    /**
     * 构造函数
     * @param parent 调用本类的父窗口
     */
    public TechnicsStepJPanel(JFrame parent)
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
    
    //CCBegin SS8
    
    String createStepType="";
    /**
     * 构造函数
     * @param parent 调用本类的父窗口
     */
    public TechnicsStepJPanel(JFrame parent,String stepType)
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
            createStepType=stepType;
            
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //CCEnd SS8


    /**
     * 构造函数
     * @param parent 调用本类的父窗口
     * @param parentnode 父节点
     */
    public TechnicsStepJPanel(JFrame parent, BaseValueIfc technics)
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
        //CCBegin SS6
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame,true); 
        //CCEnd SS6
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
        //CCBegin SS9
        if((getUserFromCompany().equals("cd")))
        	descStepNumberJLabel.setText("*工位");
        else
            descStepNumberJLabel.setText("*工序编号");
        //CCEnd SS9
        
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
        
        //CCBegin SS5
        relationJPanel.setLayout(gridBagLayout8);
        relationDisJLabel.setMaximumSize(new Dimension(10, 22));
        relationDisJLabel.setMinimumSize(new Dimension(10, 22));
        relationDisJLabel.setPreferredSize(new Dimension(10, 22));
        
        relationJLabel.setMaximumSize(new Dimension(90, 22));
        relationJLabel.setMinimumSize(new Dimension(90, 22));
        relationJLabel.setPreferredSize(new Dimension(90, 22));
        relationJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        relationJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        relationJLabel.setText("关联工序");
        
        //CCBegin SS7
        
        
        sonTeamDateJLabel.setMaximumSize(new Dimension(90, 22));
        sonTeamDateJLabel.setMinimumSize(new Dimension(90, 22));
        sonTeamDateJLabel.setPreferredSize(new Dimension(90, 22));
        sonTeamDateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sonTeamDateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        sonTeamDateJLabel.setText("分装点或子组属性信息");
        
        //CCEnd SS7
        
        
        searchTechJButton1.setMaximumSize(new Dimension(89, 23));
        searchTechJButton1.setMinimumSize(new Dimension(89, 23));
        searchTechJButton1.setPreferredSize(new Dimension(89, 23));
        searchTechJButton1.setToolTipText("");
        searchTechJButton1.setMnemonic('D');
        searchTechJButton1.setText("浏览(D)");
        searchTechJButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              searchTechJButton1_actionPerformed(e);
            }
          });
        
        deleteTechJButton1.setMaximumSize(new Dimension(75, 23));
        deleteTechJButton1.setMinimumSize(new Dimension(75, 23));
        deleteTechJButton1.setPreferredSize(new Dimension(75, 23));
        deleteTechJButton1.setMnemonic('W');
        deleteTechJButton1.setText("删除(W)");
        deleteTechJButton1.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            deleteTechJButton1_actionPerformed(e);
          }
        });


        
        viewProcedureJButton.setMaximumSize(new Dimension(75, 23));
        viewProcedureJButton.setMinimumSize(new Dimension(75, 23));
        viewProcedureJButton.setPreferredSize(new Dimension(75, 23));
        viewProcedureJButton.setMnemonic('L');
        viewProcedureJButton.setText("查看(L)");
        viewProcedureJButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            viewProcedureJButton_actionPerformed(e);
          }
        });
        
        //CCEnd SS5

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
        processHoursJPanel = new ProcessHoursJPanel(parentJFrame);
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
        //CCBegin SS5
        relationJTextField.setEditable(false);
        //CCEnd SS5
        relationTechJTextField.setEditable(false);
        relationTechJTextField.addCaretListener(new javax.swing.event.
                                                CaretListener()
        {
            public void caretUpdate(CaretEvent e)
            {
                relationTechJTextField_caretUpdate(e);
            }
        });
        
        
        //CCBegin SS3
        
        viewproJButton.setMaximumSize(new Dimension(100, 23));
        viewproJButton.setMinimumSize(new Dimension(100, 23));
        viewproJButton.setPreferredSize(new Dimension(100, 23));
        viewproJButton.setMnemonic('E');
        viewproJButton.setText("预览工序(E)");
        viewproJButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            viewproJButton_actionPerformed(e);
          }
        });

        viewTechnicsJButton.setMaximumSize(new Dimension(100, 23));
        viewTechnicsJButton.setMinimumSize(new Dimension(100, 23));
        viewTechnicsJButton.setPreferredSize(new Dimension(100, 23));
        viewTechnicsJButton.setMnemonic('H');
        viewTechnicsJButton.setText("预览工艺(H)");
        viewTechnicsJButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            viewTechnicsJButton_actionPerformed(e);
          }
        });
        
        //CCEnd SS3
        
       
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
        
        //CCBegin SS7
        sonTeamDateDisJLabel.setMaximumSize(new Dimension(4, 22));
        sonTeamDateDisJLabel.setMinimumSize(new Dimension(4, 22));
        sonTeamDateDisJLabel.setPreferredSize(new Dimension(4, 22));
        
        //CCEnd SS7
        

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
        //CCBegin SS7
       if((getUserFromCompany().equals("cd"))){
    	   
    	      upJPanel.add(sonTeamDateJLabel,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
    	                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 8, 0, 0), 0, 0));
    	      
    	      upJPanel.add(sonTeamDateTextField,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
  	                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));
    	      
    	      
    	      upJPanel.add(sonTeamDateDisJLabel,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
    	                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));
    	      
    	      
    	      upJPanel.add(nameJTextField,
                     new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 8, 0, 7), 0, 0));
       }else{
        upJPanel.add(nameJTextField,
                new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(7, 8, 0, 7), 0, 0));
       }
       
       //CCEnd SS7
        
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
            //CCBegin SS8
            
            if(createStepType.equals("成都检查作业指导书工序"))
               processTypeComboBox.setSelectedIndex(1);
            //CCEnd SS8
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
        
       
      
        
        //CCBegin SS5
        
        upJPanel.add(relationJLabel,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 8, 0, 0), 0, 0));
        
      
      
        relationJPanel.add(relationJTextField,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));
        
        
        relationJPanel.add(relationDisJLabel,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));
        
        
        
        relationJPanel.add(viewProcedureJButton,
                new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 8, 0, 0), 0, 0));

        relationJPanel.add(deleteTechJButton1,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 8, 0, 0), 0, 0));

         relationJPanel.add(searchTechJButton1,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 8, 0, 0), 0, 0));



        
        upJPanel.add(relationJPanel,  new GridBagConstraints(3, 1, 3, 1, 1.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 7), 0, 0));
        
        //CCEnd SS5
        
        
        
        
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
            
          //CCBegin SS8
            System.out.println("ttttttttttttttt============================="+createStepType);
            if(createStepType.equals("成都检查作业指导书工序"))
            	stepTypeComboBox.setSelectedIndex(1);
            //CCEnd SS8
            
            
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
        
        //CCBegin SS3
        
        
        buttonJPanel.add(viewproJButton,
                new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.EAST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));
        
        buttonJPanel.add(viewTechnicsJButton,
                new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.EAST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));

        
        //CCEnd SS3
        
        
        
        //add by wangh on 20070202
        this.add(jTabbedPanel,
                 new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                        , GridBagConstraints.CENTER,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 2, 0, 0), 0, 0));
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

        
        
        
    }


    /**
     * 界面信息本地化
     */
    protected void localize()
    {
        try
        {
            //JLabel
//            numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "stepNumberJLabel", null));
        	
        	 
        		 numJLabel.setText("工序号");
        	
        	

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
            //CCBegin SS9
            if((getUserFromCompany().equals("cd")))
            	descStepNumberJLabel.setText("*工位");
            else
                descStepNumberJLabel.setText("*工序编号");
            //CCEnd SS9
            //薛凯修改结束
            contentJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "mtechContentJLabel", null));
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
        //CCBegin SS7
        sonTeamDateTextField.setVisible(true);
        sonTeamDateDisJLabel.setVisible(false);
        //CCEnd SS7
        //CCBegin SS5
        relationJTextField.setVisible(true);
        relationDisJLabel.setVisible(false);
        //CCEnd SS5

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
        
        //CCBegin SS5
        
        String techname = getProcedure().getName();
        relationJTextField.setVisible(true);
        relationDisJLabel.setVisible(false);
        searchTechJButton1.setVisible(true);
        deleteTechJButton1.setVisible(true);
        
        
        if (techname != null && !techname.equals("")) {
            Class[] paraClass1 = {
                String.class};
            Object[] objs1 = {
                techname};
            String displayString = "";
            try {
              Vector techandpro = (Vector)
                  useServiceMethod(
                  "StandardCappService", "getTechInfoAndProInfoByProID", paraClass1,
                  objs1);
              if (techandpro != null && techandpro.size() > 0) {
                QMFawTechnicsIfc techifc = (QMFawTechnicsIfc) techandpro.elementAt(0);
                String techString = getIdentity1(techifc);
                QMProcedureIfc relationProcedure = (QMProcedureIfc) techandpro.
                    elementAt(1);
                String proString = relationProcedure.getDescStepNumber() +"("+
                    relationProcedure.getStepName()+")"+relationProcedure.getVersionValue();
                displayString = techString + "--" + proString;
                this.relatedProcedure = relationProcedure;
                this.technicsinfo = (QMFawTechnicsInfo) techifc;
                relationJTextField.setText(displayString);
              }
            }
            catch (QMRemoteException ex) {
              String title = QMMessage.getLocalizedMessage(RESOURCE,
                  "information", null);
              JOptionPane.showMessageDialog(getParentJFrame(),
                                            ex.getClientMessage(), title,
                                            JOptionPane.
                                            INFORMATION_MESSAGE);
            }
            relationDisJLabel.setText(displayString);
        }
        else {
          relationDisJLabel.setText("");
        }
        
        
        //CCEnd SS5
        
        
         //CCBegin SS7
        
        sonTeamDateTextField.setVisible(true);
        sonTeamDateDisJLabel.setVisible(false);
        sonTeamDateTextField.setText(getProcedure().getSonTeamDate());
        //CCEnd SS7
        
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
        //CCBegin SS3
        viewproJButton.setEnabled(true);
        viewTechnicsJButton.setEnabled(true);
        //CCEnd SS3
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

    //CCBegin SS5
    
    private String getIdentity1(QMFawTechnicsIfc technics)
    {
      String result="";
      result=technics.getTechnicsNumber()+"_"+technics.getVersionValue();
      return result;
    }
    
    //CCEnd SS5

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
       //CCBegin SS5
        
        relationJTextField.setVisible(false);
        relationDisJLabel.setVisible(true);
        //CCEnd SS5
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
                
                //CCBegin SS5
                relationJTextField.setText(displayString);
                
                //CCEnd SS5
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

        //CCBegin SS7
        
        sonTeamDateTextField.setVisible(false);
        sonTeamDateDisJLabel.setVisible(true);
        sonTeamDateDisJLabel.setText(getProcedure().getSonTeamDate());
        
        //CCEnd SS7
        
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
        //CCBegin SS3
        viewproJButton.setEnabled(true);
        viewTechnicsJButton.setEnabled(true);
        //CCEnd SS3
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
            else if (contentPanel.save() == null ||
                     contentPanel.save().trim().equals(""))
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
                        null);
                isOK = false;
                contentPanel.getTextComponent().grabFocus();
            }
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
            else
            {
                String tempString = contentPanel.save().trim();
                if (1 == tempString.length())
                {
                    int tempChar = tempString.charAt(0);
                    if (tempChar == 128)
                    {
                        message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
                                null);
                        isOK = false;
                        contentPanel.getTextComponent().grabFocus();
                    }
                }
            }
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
        //设置工序属性(编号、名称、工序种类、工序类别、加工类型、部门、关联工艺、工艺简图、
        //简图输出方式)
        //设置是工序,并设置工序种类
        processHoursJPanel.setProcedure(getProcedure());
        
        //CCBegin SS4
        getProcedure().setIsProcedure(true);
        //CCEnd SS4
        
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
        
        //CCBegin SS7
        System.out.println("1111111=============="+sonTeamDateTextField.getText());
        getProcedure().setSonTeamDate(sonTeamDateTextField.getText());
        //CCEnd SS7
        
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
        
        //CCBegin SS5
        if (relatedProcedure != null) {
            getProcedure().setName(relatedProcedure.
                                   getBsoID());
          }
          else {
            getProcedure().setName(null);
          }
        
        //CCEnd SS5
        
        
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
            //CCBegin SS2 判断顺序号是否重复
            boolean checkFlag = drawingLinkPanel.checkSeqNum();
            System.out.println("sssssssssssssssssssS===="+checkFlag);
            if (checkFlag) {
            	return null;
            }
            //CCEnd  SS2
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
                    processFlowJPanel = new CappExAttrPanel(procedureInfo.
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
                processFlowJPanel = (CappExAttrPanel) processFlowTable.get(
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
                    femaJPanel = new CappExAttrPanel(procedureInfo.getBsoName(),
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
                femaJPanel = (CappExAttrPanel) femaTable.get(
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
                    processControlJPanel = new CappExAttrPanel(procedureInfo.
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
                processControlJPanel = (CappExAttrPanel) processControlTable.
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
    	System.out.println("11111111111111");
		if (i == JOptionPane.YES_OPTION)
		{
    	System.out.println("222222");
			setCreateMode();
			isSave = false;
			setButtonWhenSave(true);
			return;
		}
		if ((i == JOptionPane.NO_OPTION))
		{
    	System.out.println("33333333333");
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
    	System.out.println("44444444444");
			isSave = false;
		}//End CR1
    	System.out.println("55555555555");
        setButtonWhenSave(true);
        isSave = true;
    	System.out.println("66666666666");
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
    	System.out.println("77777777777");
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
            //CCBegin SS2 保存后,重新刷新界面
            drawingLinkPanel.setProcedure(getProcedure());
            //CCEnd  SS2
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //刷新树节点
            ((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                    returnProce, parentTechnics.getBsoID());
            procedureInfo = returnProce;
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
    
    //CCBegin SS3
    
    /**
     * 打印预览按钮,根据选中的tab页的不同预览不同的卡，工序
     * @param e ActionEvent
     */
    void viewproJButton_actionPerformed(ActionEvent e) {
      parentJFrame.setCursor(Cursor.WAIT_CURSOR);
      int i = jTabbedPanel.getSelectedIndex();
      isview = true;
      //检查工序不用快速预览
      // if (!bsoname.equals("QMCheckProcedure")) {
      //没有选中工序信息tab页
      if (i != 0) {
        //更新模式
        if (getMode() == UPDATE_MODE) {
          //先保存
          saveWhenUpdate();
          QMProcedureIfc proifc = getProcedure();
          startPreviewJFrame(i, proifc);
        }
        //创建模式
        else if (getMode() == CREATE_MODE) {
          //先保存
          saveWhenCreate();
          QMProcedureIfc proifc = getProcedure();
          startPreviewJFrame(i, proifc);
        }
        //查看模式
        else if (getMode() == VIEW_MODE) {
          QMProcedureIfc proifc = getProcedure();
          startPreviewJFrame(i, proifc);
        }
        //}
      }
      isview = false;
      parentJFrame.setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    /**
     * 根据不同卡类型进行工序预览
     * @param i int
     * @param proifc QMProcedureIfc
     */
    public void startPreviewJFrame(int i, QMProcedureIfc proifc) {
      String cardType = "";
      if (i == 1) {
        cardType = "过程流程图";
      }
      if (i == 2) {
        cardType = "过程FMEA";
      }
      if (i == 3) {
        cardType = "控制计划";
      }
      LightweightFileTool.templatesSizeArray = new String[1];
      LightweightFileTool.templatesSizeArray[0] = "A4";
      LightweightFileTool.isPrintQMTechnics = true;
      LightweightFileTool.init(parentJFrame, parentTechnics, "");
      String[] stringArray = {
          cardType};
      try {
        LightweightFileTool.previewOneStep(proifc, stringArray);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    
    
    /**
     * 打印预览按钮,根据选中的tab页的不同预览不同的卡，工艺
     * @param e ActionEvent
     */
    void viewTechnicsJButton_actionPerformed(ActionEvent e) {
      parentJFrame.setCursor(Cursor.WAIT_CURSOR);
      int i = jTabbedPanel.getSelectedIndex();
      isview = true;
      QMProcedureIfc prifc = getProcedure();
      String bsoname = "";
      if (prifc != null) {
        bsoname = prifc.getBsoName();
      }
      //检查工序不用快速预览
      //if (!bsoname.equals("QMCheckProcedure")) {
      //没有选中工序信息tab页
      if (i != 0) {
        //更新模式
        if (getMode() == UPDATE_MODE) {
          //先保存
          saveWhenUpdate();
          startPreviewJFrame(i);
        }
        //创建模式
        else if (getMode() == CREATE_MODE) {
          //先保存
          saveWhenCreate();
          startPreviewJFrame(i);
        }
        //查看模式
        else if (getMode() == VIEW_MODE) {
          startPreviewJFrame(i);
        }
        // }
      }
      isview = false;
      parentJFrame.setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    
    /**
     * 根据不同卡类型进行工序预览
     * @param i int
     * @param proifc QMProcedureIfc
     */
    public void startPreviewJFrame(int i) {
      String cardType = "";
      if (i == 1) {
        cardType = "过程流程图";
      }
      if (i == 2) {
        cardType = "过程FMEA";
      }
      if (i == 3) {
        cardType = "控制计划";
      }
      LightweightFileTool.templatesSizeArray = new String[1];
      LightweightFileTool.templatesSizeArray[0] = "A4";
      LightweightFileTool.isPrintQMTechnics = false;
      LightweightFileTool.init(parentJFrame, parentTechnics, "");
      String[] stringArray = {
          cardType};
      try {
        LightweightFileTool.createLightweightFiles(stringArray);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    
    
    //CCEnd SS3


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
                        equiplinkJPanel.setMode("Edit");
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
                        toollinkJPanel.setMode("Edit");
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
                        materiallinkJPanel.setMode("Edit");
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
                    equiplinkJPanel.setMode("Edit");
                    toollinkJPanel.setMode("Edit");
                    materiallinkJPanel.setMode("Edit");
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

//CCBegin SS5
    /**
     * 实现使用作业指导书查看工序
     * @param e ActionEvent
     */
    void viewProcedureJButton_actionPerformed(ActionEvent e) {
      if (relatedProcedure != null && technicsinfo != null) {
        TechnicsTreePanel techTreePanel = ( (TechnicsRegulationsMainJFrame)
                                           parentJFrame).getProcessTreePanel();
        StepTreeObject stepobj = new StepTreeObject( (QMProcedureInfo)
                                                    relatedProcedure);
        TechnicsTreeObject techobj = new TechnicsTreeObject(technicsinfo);
        CappTreeNode nNode = null;
        if (selectedNode.getObject() instanceof StepTreeObject) {
          nNode = selectedNode.getP().getP();
        }
        else {
          nNode = selectedNode.getP();
        }
//        nNode.get
        //先查看工艺节点
        techTreePanel.addNodeNotExpand(nNode, techobj);
        ( (TechnicsRegulationsMainJFrame) parentJFrame).getProcessTreePanel().
            setNodeSelected(techobj);
        nNode = ( (TechnicsRegulationsMainJFrame) parentJFrame).
            getProcessTreePanel().getSelectedNode();
        //再查看工序节点
        //techTreePanel.nodeExpaned(nNode);
        techTreePanel.addNodeNotExpand(nNode, stepobj);
        //techTreePanel.addNode(nNode, stepobj);
        ( (TechnicsRegulationsMainJFrame) parentJFrame).getProcessTreePanel().
            setNodeSelected(stepobj);
        ( (TechnicsRegulationsMainJFrame) parentJFrame).refresh();
      }

    }
    
    //CCEnd SS5
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
        
        //CCBegin SS7
        this.sonTeamDateDisJLabel.setText("");
        this.sonTeamDateTextField.setText("");
        
        //CCEnd SS7
        
        
        //CCBegin SS5
        
        relationJTextField.setText("");
        //CCEnd SS5
        
        //问题（4） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加
        //CCBegin SS8
//        stepTypeComboBox.setSelectedIndex(0);
//        processTypeComboBox.setSelectedIndex(0);
      
        if(createStepType.equals("成都检查作业指导书工序")){
        	
        	stepTypeComboBox.setSelectedIndex(1);
            processTypeComboBox.setSelectedIndex(1);
        }else{
        	
        	stepTypeComboBox.setSelectedIndex(0);
            processTypeComboBox.setSelectedIndex(0);
        }
        //CCEnd SS8
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
  //CCBegin SS4
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
//CCEnd SS4

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
            //CCBegin SS4
            try {
				if(getUserFromCompany().equals("cd")){
					
					QMTechnicsIfc tech = null;
				    if (selectedNode.getObject().getObject() instanceof QMProcedureIfc) {
				      tech = (QMTechnicsIfc) selectedNode.getP().getObject().getObject();
				    }
				    else {
				      tech = (QMTechnicsIfc) selectedNode.getObject().getObject();
				    }
				    partlinkJPanel = new ProcedureUsagePartJPanel(stepType, tech);
				    partlinkJPanel.setLinkedTechnics(tech);
					
				}else{
					//CCBegin SS6
				   partlinkJPanel = new ProcedureUsagePartJPanel(stepType,true);
				   //CCEnd SS6
				
				}
			} catch (QMException e) {
				e.printStackTrace();
			}
			//CCEnd SS4
            
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
            //CCBegin SS6
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType,true);
            //CCEnd SS6
            //CCBegin SS1
            equiplinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            //问题（7）20090108  徐春英修改    初始化设备关联面板的时候也初始化工装关联面板   不然会出现空指针异常
            //CCBegin SS6
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType,true);
            //CCEnd SS6
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
            //CCBegin SS6
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType,true);
            //CCEnd SS6
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
            //CCBegin SS6
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType,true);
            //CCEnd SS6
            
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
    
    //CCBegin SS5
    public void setRelationJTextField() {
        if (technicsinfo != null && relatedProcedure != null) {
          String techString = getIdentity1(technicsinfo);
          String displayString = relatedProcedure.getDescStepNumber() +
              relatedProcedure.getStepName();
          relationJTextField.setText(techString + "--" + displayString);
        }
      }
    
    void searchTechJButton1_actionPerformed(ActionEvent e) {
        try {
          sd = new TechnicsSearchJDialog( (TechnicsRegulationsMainJFrame)
                                         parentJFrame);
          sd.setModal(false);
          sd.setSingleSelectMode();
          sd.addQueryListener(new CappQueryListener() {
            public void queryEvent(CappQueryEvent e) {
              qmQuery_queryEvent1(e);
            }
          });
          //添加TechnicsSearchJDialog中的mutilist的行的双击监听
          sd.addMultiListActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              myList_actionPerformed1(e);
            }
          }
          );
          sd.setVisible(true);
        }
        catch (QMRemoteException ex) {
          String title = QMMessage.getLocalizedMessage(RESOURCE,
              "information", null);
          JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                        title,
                                        JOptionPane.INFORMATION_MESSAGE);

        }

      }

      void deleteTechJButton1_actionPerformed(ActionEvent e) {
        relationJTextField.setText("");
        relatedProcedure = null;


      }
    
    //CCEnd SS5


    void descNumJTextField_actionPerformed(ActionEvent e)
    {

    }


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
    
    //CCBegin SS5
    /**
     * 搜索工艺监听事件方法
     * @param e 搜索监听事件
     */
    public void qmQuery_queryEvent1(CappQueryEvent e) {
      if (verbose) {
        System.out.println(
            "cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) begin...");
      }
      if (e.getType().equals(CappQueryEvent.COMMAND)) {
        if (e.getCommand().equals(CappQuery.OkCMD)) {
          setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          CappQuery c = (CappQuery) e.getSource();
          BaseValueIfc[] bvi = c.getSelectedDetails();
          actionPerformed1(bvi);
          setCursor(Cursor.getDefaultCursor());
        }
        if (e.getCommand().equals(CappQuery.QuitCMD)) {
          sd.setVisible(false);
        }
      }

      if (verbose) {
        System.out.println("cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) end...return is void");
      }
    }
    
    
    /**
     *把数组中的工艺规程加入关联工艺”文本框中，此方法提供给TechnicsSearchJDialog的两个监听事件
     */
    private void actionPerformed1(Object[] bvi) {
      if (bvi != null) {
        //把从结果域选中的业务对象加入“关联工艺”文本框
        for (int i = 0; i < bvi.length; i++) {
          QMFawTechnicsInfo info = (QMFawTechnicsInfo) bvi[i];
          String compareBsoID = "";
          if (getViewMode() == CREATE_MODE) {
            compareBsoID = ( (BaseValueInfo) selectedNode.getObject().
                            getObject()).getBsoID();
          }
          if (getViewMode() == UPDATE_MODE) {
            compareBsoID = parentTechnics.getBsoID();
          }
          if (info.getBsoID().equals(compareBsoID)) {
            JOptionPane.showMessageDialog(getParentJFrame(), "不能关联本工艺中的工序", "提示",
                                          JOptionPane.
                                          INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            return;
          }
//          relationJTextField.setText(getIdentity(info));
//          relatedTechnics1 = info;
          if (sd != null) {
            sd.setVisible(false);
          }
          //tangshutao 2008.01.21
          SelectProcedureJDialog dialog = new SelectProcedureJDialog(this.
              parentJFrame, "", true, info, this);
          dialog.initTable();
          dialog.setVisible(true);
          //end
        }
      }
    }
    
    
    /**
     * 创建状态下，获得当前工序所在的父结点
     * added by dikefeng 20090706
     * @param bvi Object[]
     */
    public CappTreeNode getSelectedTreeNode()
    {
      return this.selectedNode;
    }
    
    /**
     * 更新状态下，获得当前工序所在的父结点
     * added by dikefeng 20090706
     * @param bvi Object[]
     */
    public QMTechnicsIfc getSelectedParentTechnics()
    {
      return this.parentTechnics;
    }
    
    /**用来处理搜索界面的双击事件*/
    private void myList_actionPerformed1(ActionEvent e) {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      CappMultiList c = (CappMultiList) e.getSource();
      Object[] bvi = c.getSelectedObjects();
      actionPerformed1(bvi);
      setCursor(Cursor.getDefaultCursor());
    }
    
    
    //CCend SS5
    

}
