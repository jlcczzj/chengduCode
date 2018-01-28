/** 生成程序TechnicsPaceJPanel.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CCBegin by liunan 2010-12-17 优化
 * CR1  2009/02/20   徐春英   原因：优化创建工步的响应时间
 *                            方案：保存完创建的工步之后，不进入查看界面之后再进入更新界面，而是保持界面不动仍能更新
 *                            备注：性能测试用例名称是"创建工步"
 * CR2  2009/02/24   徐春英   原因：优化更新工步的响应时间
 *                            方案：保存完更新的工步之后，不进入更新界面，而是保持界面不动仍能更新
 *                            备注：性能测试用例名称是"更新工步"
 * CCEnd by liunan 2010-12-17
 * CCBegin by liunan 2011-5-27 优化
 * CR3 2009/04/30     李磊    原因：简图方案的处理，只针对用户操作的简图进行处理
 *                            方案：
 *                            备注：性能测试用例名称："工序更新，工序检出 ，工序复制粘贴"。    
 * CCEnd by liunan 2011-5-27
 * SS1 将搜索到的文档转换成文档master和工序的关联 Liuyang 2013-3-19
 * SS2 变速箱工艺所见即所得 guoxiaoliang 2013-03-14
 * SS3 变速箱工步资源不汇总到工序 liunan 2014-5-19
 * SS4 修改服务平台问题，问题编号：A005-2014-2842。郭晓亮  2014-05-021
 * SS5 增加简图顺序号的重复检查 liunan 2014-7-1
 * SS6 修改服务平台问题A005-2014-2973 guoxiaoliang 2014-07-03
 * SS7 修改服务平台上问题A005-2014-2964
 * SS8 修改检入工步后工步编号和名称不显示
 * SS9 SS7中量具的规格标准号显示规则与手工输入编号获取时不一致，此处只取了规格，没有的话没有取标准号。 liunan 2014-8-19
 * SS10 修改服务平台问题，问题编号：A005-2014-2843。郭晓亮  2014-05-07
 * SS11 工步处理历史数据时，只得到4个空行，没有内容。 liunan 2014-8-21
 * SS12 工步变化时，提示更新，但没有更新内容。 liunan 2015-3-31
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
//CCBegin by chudaming 20091119 插入工步
import javax.swing.JCheckBox;
//CCEnd by chudaming 20091119 插入工步
//SS1 解放工时默认为秒和变速箱工时默认为分leixiao 2013-9-11

/**
 * <p>Title:工步维护面板</p>
 * <p>为工艺规程浏览器提供服务。</p>
 * <p>提供三种显示模式(创建、更新、查看)，完成工步的创建、更新、查看操作。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 * （1）20060728薛静修改，原来在维护工步的工时、资源关联时，自动维护工序的工时、资源关联，即工序工时等于所有工步工时的和，
 *      资源也一样。现在做成可配置的,即在属性文件中配置是否要维护这种关系.修改方法 setUpdateMode()
 * 问题（2）20080709 xucy add  修改原因：工步关联类添加预留属性用
 */

public class TechnicsPaceJPanelForBSX extends TechnicsPaceJPanel
{
    /**工步内容面板*/
    /**按钮面板*/
    private JPanel buttonJPanel = new JPanel();

  //CCBegin by chudaming 20091119 插入工步
	  private JLabel insertJLabel=new JLabel();
	  private JCheckBox insertCheckBox=new JCheckBox();
	//CCEnd by chudaming 20091119 插入工步
    /**工步号*/
    private JLabel paceNumberJLabel = new JLabel();
    private CappTextField paceNumJTextField;
    private JLabel numDisplayJLabel = new JLabel();

    /**工步名称*/
    //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
   private JLabel paceNameJLabel = new JLabel();
   private CappTextField paceNameJTextField;
   private JLabel nameDisplayJLabel = new JLabel();
   //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性


    /**简图面板*/
    private ProcedureUsageDrawingPanel drawingLinkPanel;


    /**工艺内容*/
    private JLabel technicsContentJLabel = new JLabel();
    private SpeCharaterTextPanel technicsContentJTextArea;


    /**工时计算面板*/
    private ProcessHoursJPanel processHoursJPanel;


    /**关联关系处理面板*/
    private JTabbedPane relationsJPanel = new JTabbedPane();


    /**按钮组*/
    /**扩展内容按钮*/
    private JButton paraJButton = new JButton();


    /**保存按钮*/
    private JButton saveJButton = new JButton();


    /**取消按钮*/
    private JButton cancelJButton = new JButton();


    /**退出按钮*/
    private JButton quitJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**界面显示模式（更新模式）标记*/
    public final static int UPDATE_MODE = 0;


    /**界面显示模式（创建模式）标记*/
    public final static int CREATE_MODE = 1;


    /**界面显示模式（查看模式）标记*/
    public final static int VIEW_MODE = 2;


    /**面模式--查看*/
    private int mode = VIEW_MODE;


    /**工步*/
    private QMProcedureInfo procedure;


    /**父窗口*/
    private JFrame parentJFrame;


    /**所选择的工艺树节点*/
    private CappTreeNode selectedNode;

    //20080702 xucy 修改
    /**设备关联面板*/
    private ProcedureUsageEquipmentJPanel equiplinkJPanel ;


    /**工装关联面板*/
    private ProcedureUsageToolJPanel toollinkJPanel ;


    /**材料关联面板*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**文档关联面板*/
    private ProcedureUsageDocJPanel doclinkJPanel = new
            ProcedureUsageDocJPanel();


    /**入库按钮*/
   // private JButton storageJButton = new JButton();

    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JPanel jPanel1 = new JPanel();


    /**扩展内容界面对象*/
    private TParamJDialog d = null;
    private EquipmentToolMaintainJDialog f1 = null;


    /**父工序*/
    private QMProcedureIfc parentProcedure;


    /**工序（或工步）所属的工艺卡头*/
    private QMTechnicsIfc parentTechnics;


    /**记录是否第一次进入此界面*/
    private boolean firstInFlag = true;


    /**
     * 布局所用
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
    //add by wangh on 20070326(是否显示TS16949的工序或者工步信息。)
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
     * 构造函数
     * @param parentframe 调用本类的父窗口
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
     * 界面初始化
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
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField = new CappTextField(parentJFrame, numDisp, 10000, false);
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
        buttonJPanel.setLayout(gridBagLayout1);
        paceNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        paceNumberJLabel.setHorizontalTextPosition(SwingConstants.
                RIGHT);
        paceNumberJLabel.setText("*工步号");
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJLabel.setText("工步名称");
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame);
        //问题（2）20080709 xucy add  修改原因：工步关联类添加预留属性用
        //equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepTypeContent);
        //toollinkJPanel = new ProcedureUsageToolJPanel(stepTypeContent);
        //materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepTypeContent);


        technicsContentJLabel.setHorizontalAlignment(SwingConstants.
                RIGHT);
        technicsContentJLabel.setHorizontalTextPosition(
                SwingConstants.RIGHT);
        technicsContentJLabel.setText("工艺内容");
        technicsContentJTextArea.setBorder(null);
        technicsContentJTextArea.setMaximumSize(new Dimension(32767,
                80));
        technicsContentJTextArea.setMinimumSize(new Dimension(5, 5));
        technicsContentJTextArea.setPreferredSize(new Dimension(5, 5));
        paraJButton.setMaximumSize(new Dimension(114, 23));
        paraJButton.setMinimumSize(new Dimension(114, 23));
        paraJButton.setPreferredSize(new Dimension(114, 23));
        paraJButton.setActionCommand("附加信息");
        paraJButton.setMnemonic('E');
        paraJButton.setRolloverEnabled(false);
        paraJButton.setText("附加信息(E)");
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
        saveJButton.setText("保存(S)");
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
        cancelJButton.setText("取消(C)");
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
        quitJButton.setText("退出(Q)");
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
//        storageJButton.setText("入库(ST)");
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
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField.setMaximumSize(new Dimension(10, 24));
        paceNameJTextField.setMinimumSize(new Dimension(10, 24));
        paceNameJTextField.setPreferredSize(new Dimension(10, 24));
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
        paceNumJTextField.setMargin(new Insets(1, 1, 1, 1));
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField.setMargin(new Insets(1, 1, 1, 1));
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
      //CCBegin by chudaming 20091119 插入工步
	    insertJLabel.setText("插入工步");
	    insertCheckBox.setSelected(true);
	  //CCEnd by chudaming 20091119 插入工步
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
        jTabbedPanel.add(paceJPanel, "工步信息");
        System.out.println("ts16949================================================="+ts16949);
        if (ts16949) {
//          jTabbedPanel.add(flowExtendJPanel, "过程流程");
//          jTabbedPanel.add(femaExtendJPanel, "过程FMEA");
        	
		jTabbedPanel.add(controlExtendJPanel, "控制计划");
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
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        upJPanel.add(paceNameJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(2, 8, 2, 8), 0, 0));
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
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
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        upJPanel.add(nameDisplayJLabel, new GridBagConstraints(3, 0, 2,
               1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
               new Insets(0, 8, 0, 10), 0, 0));
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
      //CCBegin by chudaming 20091119 插入工步
	    upJPanel.add(insertJLabel, new GridBagConstraints(4, 0, 1,
	        1, 0.0, 0.0
	        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
	        new Insets(0, 15, 0, 2), 0, 0));
	    upJPanel.add(insertCheckBox,
	                 new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
	                                        , GridBagConstraints.WEST,
	                                        GridBagConstraints.HORIZONTAL,
	                                        new Insets(2, 8, 2, 8), 0, 0));

	  //CCEnd by chudaming 20091119 插入工步

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
     * 界面信息本地化
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
            technicsContentJLabel.setText("工艺内容");

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
     * 设置在工艺树所选择的父节点
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
     * 设置界面为创建模式
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
        //新建模式时，根据父工序（步）的工序种类，创建不同的工步卡
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
        //设置控件显示状态
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性w
        paceNumJTextField.setVisible(true);
        paceNameJTextField.setVisible(true);
        numDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性

        technicsContentJTextArea.speCharaterTextBean.setEditable(true);

        //问题（2）20080820 徐春英修改     begin
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
        "文档");
        relationsJPanel.add(drawingLinkPanel,
        "简图");
        drawingLinkPanel.setModel(2); //EDIT
        //问题（2）20080820 徐春英修改   end
        //零部件关联
        newPartlinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setMode("Edit");
            //处理简图输出
        }
        processHoursJPanel.setCreateMode();
      //CCBegin by chudaming 20091119 插入工步
	    this.insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 插入工步
	    //CCBegin SS2
	    if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX)
	       ((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().insertCheckBox.setSelected(false);
	    //CCEnd SS2
        f1.setEditMode();
        //{{初始化工步号
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
        if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
      	  
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
     * 设置界面为更新模式
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
          
        //设置控件显示状态
        paceNumJTextField.setVisible(true);
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField.setVisible(true);
        paceNumJTextField.setText(Integer.toString(getProcedure().
                getStepNumber()));
        
        paceNameJTextField.setText(getProcedure().
                getStepName());
        numDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
      //CCBegin by chudaming 20091119 插入工步
	    insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 插入工步
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

        //简图与简图输出的设置顺序不能变，原因是若先设置简图输出，则设置简图时会在监听事件中将简图输出置空
        //简图
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
        //查找子节点
        Collection c = null;
        //20060728薛静修改，是否自动维护父节点的工时做成可配置的
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
                    //工步中的工时是否大于零
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

        //问题（2）20080820 徐春英修改  begin
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
        //20081120 徐春英修改   更新状态下文档应该可编辑
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel,
        "文档");
        
        //CCBegin SS2
        if(processControlJPanel!=null && processControlJPanel instanceof CappExAttrPanelForBSX){
        	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX(). doclinkJPanel.setMode("Edit");
        	((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().doclinkJPanel.setProcedure(getProcedure());
        }
        //CCEnd SS2
        
        relationsJPanel.add(drawingLinkPanel,
        "简图");
        //问题（2）20080820 徐春英修改 end
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
        //20060728薛静修改，是否自动维护父节点的资源做成可配置的
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
                             //若存在子节点，且子节点使用了资源，则关联面板不可维护
                             //若存在子节点，且子节点使用了资源，则关联面板不可维护
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
                             //问题（2）20080820 徐春英修改
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
        if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
      	  
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
  							
  							if (!toolInfo.getToolCf().getCodeContent().equals("量具")
  									&& !toolInfo.getToolCf().getCodeContent().equals("万能量具")
  									&& !toolInfo.getToolCf().getCodeContent().equals("专用量具")
  									&& !toolInfo.getToolCf().getCodeContent().equals("检具")
  									&& !toolInfo.getToolCf().getCodeContent().equals("检验辅具")
  									&& !toolInfo.getToolCf().getCodeContent().equals("量仪")
  									&& !toolInfo.getToolCf().getCodeContent().equals("检验夹具")
  									&& !toolInfo.getToolCf().getCodeContent().equals("检验量辅具")) {
  								
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
     * 设置界面为查看模式
     */
    private void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() begin...");
        }
        clear();
        //设置控件显示状态
        paceNumJTextField.setVisible(false);
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField.setVisible(false);
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        numDisplayJLabel.setVisible(true);
        nameDisplayJLabel.setText(getProcedure().getStepName());
        nameDisplayJLabel.setVisible(true);
      //CCBegin by chudaming 20091119 插入工步
	    insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 插入工步


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
        //问题（2）20080820 徐春英修改 begin
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
        "文档");
        drawingLinkPanel.setModel(1); //VIEW
        drawingLinkPanel.setProcedure(getProcedure());  
        relationsJPanel.add(drawingLinkPanel,
        "简图");
        //问题（2）20080820 徐春英修改  end
        //part关联
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
     * 获取当前选择的工步的工艺卡头
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
     * 更新工步时，获得父工序（步）
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
     * 设置当前工步
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedure = info;
    }


    /**
     * 获取当前工步
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedure;
    }


    /**
     * 获得属性文件中的简图输出方式
     * @return 简图输出方式集合
     */
    /* public Vector getDrawingFormat()
     {
         return findCodingInfo("QMProcedure", "drawingFormat");
     }*/


    /**
     * 检验必填区域(编号)是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
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
//            //检验工艺内容是否为空
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
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() end...return: " +
                    isOK);
        }

        return isOK;
    }

 //CCBegin SS2
    
    
    /**
     * 检验必填区域(编号、名称)是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
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
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() end...return: " +
                    isOK);
        }

        return isOK;
        
        
        
        
        
    }
    
    //CCEnd SS2
    
    
    
    
    
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
            //信息：无效模式
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
                    "cappclients.capp.view.TechnicsPaceJPanel.setViewMode() end...return is void");
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

    private String existProcessType = "";


    /**
     * 根据工艺种类进行扩展内容维护
     * @param e ActionEvent
     */
    void paraJButton_actionPerformed(ActionEvent e)
    {
        //获得工序种类(代码内容编号)
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
            //创建或更新模式下
            if (mode == CREATE_MODE || mode == UPDATE_MODE)
            {
                //如果existProcessType不为空，改变了工序种类，则把工序对象的扩展属性置空
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
                //设置扩展属性
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
                        return;
                    }
                }
                existProcessType = processType;
            }
            //查看模式
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
     * 保存操作
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
        	
        	
        	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
      				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
        		  
        		  
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
        	
        	
        	  if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
      				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) {
        		  
        		  
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
     * 取消操作
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
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.CancelWhenCreate() begin...");
        }
        String num = paceNumJTextField.getText();
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        String name=paceNameJTextField.getText();
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
        setCreateMode();
        paceNumJTextField.setText(num);
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField.setText(name);
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性

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
     * 更新模式下，取消按钮的执行方法
     * <p>将本次录入的数据撤消，返回上次录入的数据</p>
     */
    private void cancelWhenUpdate()
    {
        setUpdateMode();
    }


    /**
     * 退出操作
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


    /**标记是否可以退出*/
    private boolean isSave = true;

    private ProcedureUsagePartJPanel partlinkJPanel;
    private JSplitPane splitJPanel = new JSplitPane();
    private JPanel upJPanel = new JPanel();
    private JPanel downJPanel = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();


    /**
     * 创建模式下，退出按钮的执行方法
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
     * 更新模式下，退出按钮的执行方法
     */
    private void quitWhenUpdate()
    {
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 IS_SAVE_QMPROCEDURE_PACE_UPDATE, null);
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
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(),
                                              s, title,
                                              JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    /**
     * 设置工步卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     */
    private CappWrapData commitAttributes()
    {
    	//CCBegin by liunan 2011-2-11 优化
    	this.setProcedure(this.getProcedure());//CR1
    	//CCEnd by liunan 2011-2-11
        //设置是工步
        getProcedure().setIsProcedure(false);
        //设置编号
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
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        getProcedure().setStepName(paceNameJTextField.getText().trim());
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性

        //设置工步内容
        Vector v = new Vector();
        v.addElement(technicsContentJTextArea.save());
        getProcedure().setContent(v);
        //计算单件工时
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
        //新建模式时，设置资料夹
        if (getViewMode() == CREATE_MODE)
        {
            procedure.setLocation(((QMProcedureInfo) selectedNode.getObject().
                                   getObject()).getLocation());

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
        //add by wangh on 20070310
//        if (processFlowJPanel.check())
//        {
//            //设置过程流程
//            procedure.setProcessFlow(processFlowJPanel.
//                                             getExAttr());
//        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("工步过程流程录入错误！");
//            }
//            isSave = false;
//            return null;
//        }
//        if (femaJPanel.check()) {
//          //设置FEMA
//          procedure.setFema(femaJPanel.getExAttr());
//        }
//        else {
//          if (verbose) {
//            System.out.println("工步FMEA录入错误！");
//          }
//          isSave = false;
//          return null;
//        }
        if (processControlJPanel.check())
        {
            //设置过程控制
            procedure.setProcessControl(processControlJPanel.
                                             getExAttr());
        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("工步过程控制录入错误！");
//            }
//            isSave = false;
//            return null;
//        }


        //获得所有关联(设备、工装、材料、文档)
        Vector docLinks = new Vector();
        Vector equipLinks = new Vector();
        Vector toolLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector partLinks = new Vector();
        //CCBegin by liunan 2011-5-27 优化
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
            //问题（2）20080820 徐春英修改   修改原因：工序关联类添加预留属性
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
            	//CCBegin by liunan 2011-5-27 优化
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
        //将所有关联合并
        Vector resourceLinks = new Vector();
        //合并文档关联
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
        //合并材料关联
        for (int j = 0; j < materialLinks.size(); j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }
        //合并设备关联
        for (int m = 0; m < equipLinks.size(); m++)
        {
            resourceLinks.addElement(equipLinks.elementAt(m));
        }
        //合并工装关联
        for (int n = 0; n < toolLinks.size(); n++)
        {
            resourceLinks.addElement(toolLinks.elementAt(n));
        }
        //合并零部件关联
        if (partLinks != null)
        {
            for (int n = 0; n < partLinks.size(); n++)
            {
                resourceLinks.addElement(partLinks.elementAt(n));
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
    
    //CCBegin SS2
    
    /**
     * 设置工步卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     */
    private CappWrapData commitAttributesForProcessControl()
    {
    	//CCBegin by liunan 2011-2-11 优化
    	this.setProcedure(this.getProcedure());//CR1
    	//CCEnd by liunan 2011-2-11
        //设置是工步
        getProcedure().setIsProcedure(false);
        //设置编号
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
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        getProcedure().setStepName(((CappExAttrPanelForBSX)processControlJPanel).getPaceProcessControlJPanelForBSX().paceNameJTextField.getText().trim());
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性

        //设置工步内容
        Vector v = new Vector();
        v.addElement(technicsContentJTextArea.save());
        getProcedure().setContent(v);
        //计算单件工时
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
        //新建模式时，设置资料夹
        if (getViewMode() == CREATE_MODE)
        {
            procedure.setLocation(((QMProcedureInfo) selectedNode.getObject().
                                   getObject()).getLocation());

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
        //add by wangh on 20070310
//        if (processFlowJPanel.check())
//        {
//            //设置过程流程
//            procedure.setProcessFlow(processFlowJPanel.
//                                             getExAttr());
//        }
//        else
//        {
//            if (verbose)
//            {
//                System.out.println("工步过程流程录入错误！");
//            }
//            isSave = false;
//            return null;
//        }
//        if (femaJPanel.check()) {
//          //设置FEMA
//          procedure.setFema(femaJPanel.getExAttr());
//        }
//        else {
//          if (verbose) {
//            System.out.println("工步FMEA录入错误！");
//          }
//          isSave = false;
//          return null;
//        }
        if (processControlJPanel.check())
        {
            //设置过程控制
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
							
							if(oldToolVec!=null){
								
                            for(int aa=0;aa<oldToolVec.size();aa++){
									
                            	QMProcedureQMToolLinkIfc  oldTool=(QMProcedureQMToolLinkIfc)oldToolVec.get(aa);
									
									String oldeqID=oldTool.getRightBsoID();
									int oldCount=Integer.parseInt(oldTool.getUsageCount());
									
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
                                                           if(count.indexOf("(")==-1||count.indexOf("（")==-1){
                                                           	
                                                           	
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
                                                          if(count.indexOf("(")==-1||count.indexOf("（")==-1){
                                                          	
                                                          	
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
//                System.out.println("工步过程控制录入错误！");
//            }
//            isSave = false;
//            return null;
//        }


        //获得所有关联(设备、工装、材料、文档)
        Vector docLinks = new Vector();
        Vector equipLinks = new Vector();
        Vector toolLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector partLinks = new Vector();
        //CCBegin by liunan 2011-5-27 优化
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
            //问题（2）20080820 徐春英修改   修改原因：工序关联类添加预留属性
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
            	//CCBegin by liunan 2011-5-27 优化
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
        //将所有关联合并
        Vector resourceLinks = new Vector();
        //合并文档关联
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
        //合并材料关联
        for (int j = 0; j < materialLinks.size(); j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }
        //合并设备关联
        for (int m = 0; m < equipLinks.size(); m++)
        {
            resourceLinks.addElement(equipLinks.elementAt(m));
        }
        //合并工装关联
        for (int n = 0; n < toolLinks.size(); n++)
        {
            resourceLinks.addElement(toolLinks.elementAt(n));
        }
        //合并零部件关联
        if (partLinks != null)
        {
            for (int n = 0; n < partLinks.size(); n++)
            {
                resourceLinks.addElement(partLinks.elementAt(n));
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
//                          "过程流程", 1);
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
//                          "过程FMEA", 1);
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
        	  System.out.println("processType==========aaaaaAAAAAAAAAAAAA======新实例化一个================");
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
                if (techType.equals("变速箱装配工序")||techType.equals("变速箱装配工艺") ||
          				techType.equals("变速箱机加工序")|| techType.equals("变速箱机加工艺")) 
                	
                     processControlJPanel = new CappExAttrPanelForBSX(procedure.getBsoName(),
                          "控制计划", 1,"工步",parentTechnics);
            	  else
            		  processControlJPanel = new CappExAttrPanel(procedure.getBsoName(),
                              "控制计划", 1);
			
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
        	  System.out.println("processType==========aaaaaAAAAAAAAAAAAA======已存在================");
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

                                          //add by wangh on 200726(设置过程流程，过程FMEA和控制计划是否可见)
//                                          if (!ts16949) {
//                                            processFlowJPanel.setVisible(false);
//                                            femaJPanel.setVisible(false);
//                                            processControlJPanel.setVisible(false);
//                                          }

//CCBegin by liunan 2011-6-1 优化
//add by guoxl on 2009-1-7(给工步更新界面上所有控件添加监听)
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
     * 保存新建的工步
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenCreate() begin...");
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
        if (!checkIsInteger(paceNumJTextField.getText().trim()))
        {
            paceNumJTextField.grabFocus();
            paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }

        //设置工步卡的所有属性和关联，并获得信息封装对象。
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

        //显示保存进度
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //     RESOURCE, CappLMRB.SAVING, null));
        // ProgressService.showProgress();
        //CCBegin by liunan 2011-2-11 优化
        // CR1
        OperationTreeObject treeObject;
        QMProcedureInfo returnProce;
        //CCEnd by liunan 2011-2-11

        try
        {
            //获得卡头，如果选择节点时节点在公共资料夹（即检入状态），则获得其副本
            parentTechnics = (QMTechnicsInfo) refreshInfo(
                    parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);

                //调用服务，保存工步
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
            //CCBegin by liunan 2011-2-11 优化
            // CR1
            //QMProcedureInfo returnProce;
            //CCEnd by liunan 2011-2-11
//            returnProce = (QMProcedureInfo) useServiceMethod(
//                    "StandardCappService", "createQMProcedure", paraClass,
//                    obj);
          //CCBegin by chudaming 20091119 插入工步
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
		      
		      //CCBegin by liunan 2011-5-27 优化
		      //Begin CR3
		      if(drawingLinkPanel!=null){
		      	drawingLinkPanel.resetArrayList();
		      }//End CR3
		      //CCEnd by liunan 2011-5-27
		      
		    //CCEnd by chudaming 20091119 插入工步
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //把新建的工序节点挂上工艺树
            //CCBegin by liunan 2011-2-11 优化
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

        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //提示是否连续建
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
        	//CCBegin by liunan 2011-2-11 优化
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
     * 保存新建的工步
     */
    private void saveWhenCreateForProcessControl()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenCreate() begin...");
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

        //设置工步卡的所有属性和关联，并获得信息封装对象。
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

        //显示保存进度
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //     RESOURCE, CappLMRB.SAVING, null));
        // ProgressService.showProgress();
        //CCBegin by liunan 2011-2-11 优化
        // CR1
        OperationTreeObject treeObject;
        QMProcedureInfo returnProce;
        //CCEnd by liunan 2011-2-11

        try
        {
            //获得卡头，如果选择节点时节点在公共资料夹（即检入状态），则获得其副本
            parentTechnics = (QMTechnicsInfo) refreshInfo(
                    parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                                                  parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo)
                                 CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) parentTechnics);

                //调用服务，保存工步
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
            //CCBegin by liunan 2011-2-11 优化
            // CR1
            //QMProcedureInfo returnProce;
            //CCEnd by liunan 2011-2-11
//            returnProce = (QMProcedureInfo) useServiceMethod(
//                    "StandardCappService", "createQMProcedure", paraClass,
//                    obj);
          //CCBegin by chudaming 20091119 插入工步
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
		      
		      //CCBegin by liunan 2011-5-27 优化
		      //Begin CR3
		      if(((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel!=null){
		    	  ((CappExAttrPanelForBSX)processControlJPanel).getStepLinkSouseControlJPanelForBSX().drawingLinkPanel.resetArrayList();
		      }//End CR3
		      //CCEnd by liunan 2011-5-27
		      
		    //CCEnd by chudaming 20091119 插入工步
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //把新建的工序节点挂上工艺树
            //CCBegin by liunan 2011-2-11 优化
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

        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //提示是否连续建
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
        	//CCBegin by liunan 2011-2-11 优化
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
     * 保存更新的工步
     */
    private void saveWhenUpdateForProcessControl()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() begin...");
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
        requiredFieldsFilled = checkIsEmptyForProcessControl();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //检验编号是否为整型
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
        
        //设置工步卡的所有属性和关联，并获得信息封装对象。
        CappWrapData cappWrapData = commitAttributesForProcessControl();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

        //显示保存进度
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //      RESOURCE, CappLMRB.SAVING, null));
        //  ProgressService.showProgress();

        try
        {
            //调用服务，保存工序
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
            
            //CCBegin by liunan 2011-5-27 优化
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
          //CCBegin by chudaming 20091119 插入工步
			
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
		    //CCEnd by chudaming 20091119 插入工步
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            //把treeObject挂到工艺树上,刷新工艺树
            //更新时则要挂在其父节点上
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().updateNode(treeObject);
                //CCBegin by liunan 2010-11-29 更新插入工步时，对当前工序下的其他工步节点也做刷新操作。
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

        //隐藏进度条
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
     * 保存更新的工步
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() begin...");
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
        if (!checkIsInteger(paceNumJTextField.getText().trim()))
        {
            paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            paceNumJTextField.grabFocus();
            return;
        }

        //设置工步卡的所有属性和关联，并获得信息封装对象。
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

        //显示保存进度
        // ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //      RESOURCE, CappLMRB.SAVING, null));
        //  ProgressService.showProgress();

        try
        {
            //调用服务，保存工序
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
            
            //CCBegin by liunan 2011-5-27 优化
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
          //CCBegin by chudaming 20091119 插入工步
			
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
		    //CCEnd by chudaming 20091119 插入工步
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            OperationTreeObject treeObject = new OperationTreeObject(
                    returnProce);
            treeObject.setParentID(parentTechnics.getBsoID());
            //把treeObject挂到工艺树上,刷新工艺树
            //更新时则要挂在其父节点上
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().updateNode(treeObject);
                //CCBegin by liunan 2010-11-29 更新插入工步时，对当前工序下的其他工步节点也做刷新操作。
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

        //隐藏进度条
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        //CCBegin by liunan 2011-2-11 优化
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
        //storageJButton.setVisible(flag);
    }


    /**
     * 执行入库操作。将工艺编制中发现的设备与工装的关系反填回工装维护中
     * @param e
     */
    void storageJButton_actionPerformed(ActionEvent e)
    {
        //首先判断选择的设备及工装，至少有一个是只选了一条实例
        Vector equipLinks = equiplinkJPanel.getSelectedObjects();
        Vector toolLinks = toollinkJPanel.getSelectedObjects();

        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
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
     * 根据指定的工序种类创建工步值对象
     * @param stepTypeKey 指定的工序种类
     * @return 有具体类型特征的工步对象
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
            //从属性文件中获得指定工序种类的工步值对象的路径和类名
        }
        String classpath = RemoteProperty.getProperty("instance" +
                technicsType);
        QMProcedureInfo procedureInfo = null;
        try
        {
            //根据指定的路径名获得类
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
     * 获得属性文件中的工序种类
     * @return 工序种类集合
     */
    public Vector getStepType()
    {
        return findCodingInfo("QMProcedure", "technicsType");
    }


    /**
     * 把给定的业务对象添加到相应的关联列表中
     * @param info 给定的业务对象（资源）
     */
    public void addObjectToTable(BaseValueInfo[] info)
    {
    	//问题（2）20080820 徐春英修改
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
     *此方法用来清除工步维护界面中的内容
     */
    public void clear()
    {
        if (firstInFlag)
        {
            firstInFlag = false;
            return;
        }
        paceNumJTextField.setText("");
        //CCBegin by chudaming 2008-11-18 bsx增加工步名称属性
        paceNameJTextField.setText("");
        numDisplayJLabel.setText("");
        nameDisplayJLabel.setText("");
        //CCEnd by chudaming 2008-11-18 bsx增加工步名称属性
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
      //CCBegin by chudaming 20091119 插入工步
	    insertCheckBox.setSelected(false);
	  //CCEnd by chudaming 20091119 插入工步
        existProcessType = "";
        d = null;
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

        //重新实例化工艺内容
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
     * 根据工序种类重新获得零件关联面板
     * @param stepType String 工序种类
     */
    private void newPartlinkJPanel(String stepType)
    {
        //动态配置工序使用零部件关联
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
            relationsJPanel.add(partlinkJPanel, "零部件");
        }
    }
    /**
     * 重新实例化设备关联
     * @param stepType String
     * 问题（2）20080820 徐春英修改
     */
    //private synchronized void newEquiplinkJPanel(String stepType)
    private void newEquiplinkJPanel(String stepType)//anan
    {

            if (equiplinkJPanel != null)
            {
                relationsJPanel.remove(equiplinkJPanel);
            }
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType);
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
     * 重新实例化工装关联
     * @param stepType String
     * 问题（2）20080820 徐春英修改
     */
    //private synchronized void newToollinkJPanel(String stepType)
    private void newToollinkJPanel(String stepType)//anan
    {

            if (toollinkJPanel != null)
            {
                relationsJPanel.remove(toollinkJPanel);
            }
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType);
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
     * 重新实例化材料关联
     * @param stepType String
     * 问题（2）20080820 徐春英修改
     */
    //private synchronized void newMateriallinkJPanel(String stepType)
    private void newMateriallinkJPanel(String stepType)//anan
    {

            if (materiallinkJPanel != null)
            {
                relationsJPanel.remove(materiallinkJPanel);
            }
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType);
            relationsJPanel.add(materiallinkJPanel, "材料");

    }


    /**
     * 获得界面模式
     * @return int
     */
    public int getMode()
    {
        return mode;
    }


    /**
     * 为工艺内容面板添加特殊符号
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

}
