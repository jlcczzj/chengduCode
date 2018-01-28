package com.faw_qm.cappclients.capp.view;
//SS1 轴齿工步编号需验证是否为空 文柳 2014-4-3   
//SS2 修改服务平台轴齿问题A034-2014-0109 郭晓亮 2014-07-04
//SS3 郭晓亮 2014-07-04 补充的注释信息，工时信息带到过程流程里
//SS4 郭晓亮 2014-07-04 补充的注释信息，扩展属性文件中属性名变化而更改
//SS5 郭晓亮 2014-07-07 修改服务平台问题A034-2014-0114
//SS6 传递功能将SpecialCharacter都传成text了，导致错误，所以修改 pante 2014-10-14
//SS7 修改轴齿创建工序（步）时自动在TS16949面板中增加一行空行并带出工序编号问题,取消SS3的修改 pante 20141021
//SS8 轴齿过程流程每个设备都要对应工时信息 pante 2014-10-29
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsLogic;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelForZC;
import com.faw_qm.cappclients.beans.processtreepanel.OperationTreeObject;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.view.TechnicsPaceJPanel.WorkThread;
import com.faw_qm.cappclients.capp.view.TechnicsStepJPanelForZC.ChuanDiActionListener;
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
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.client.model.CTool;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolInfo;
import com.faw_qm.wip.model.WorkableIfc;

public class TechnicsPaceJPanelForZC extends TechnicsPaceJPanel{
	
	
	/**工步内容面板*/
    /**按钮面板*/
    private JPanel buttonJPanel = new JPanel();
//CCBegin SS1
    //CCBegin by chudaming 20091119 插入工步
    private JLabel insertJLabel=new JLabel();
    private JCheckBox insertCheckBox=new JCheckBox();
  //CCEnd by chudaming 20091119 插入工步
  //CCEnd SS1
    /**工步号*/
    private JLabel paceNumberJLabel = new JLabel();
    private CappTextField paceNumJTextField;
    private JLabel numDisplayJLabel = new JLabel();
    
    //CCBegin by leixiao 2010-3-30  增加工步编号
    /**工步编号*/
    private JLabel descStepNumberJLabel = new JLabel();
    private JLabel descNumDisplayJLabel = new JLabel();
    private CappTextField descNumJTextField;
   //CCEnd by leixiao 2010-3-30  增加工步编号

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

    //CCBegin by liuzc 2009-11-29 原因：解放系统升级为v4r3。
    /**设备关联面板*/
    private ProcedureUsageEquipmentJPanel equiplinkJPanel = new
    ProcedureUsageEquipmentJPanel();


    /**工装关联面板*/
    private ProcedureUsageToolJPanel toollinkJPanel = new
            ProcedureUsageToolJPanel();


    /**材料关联面板*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel = new
            ProcedureUsageMaterialJPanel();
	//CCEnd by liuzc 2009-11-29 原因：解放系统升级为v4r3。
    

    /**文档关联面板*/
    private ProcedureUsageDocJPanel doclinkJPanel = new
            ProcedureUsageDocJPanel();


    /**入库按钮*/
    private JButton storageJButton = new JButton();

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
    private CappExAttrPanelForZC processFlowJPanel;
    private CappExAttrPanelForZC femaJPanel;
    private CappExAttrPanelForZC processControlJPanel;
    private JScrollPane scrollpane = new JScrollPane();
    //add by wangh on 20070326(是否显示TS16949的工序或者工步信息。)
    private static boolean ts16949 = (RemoteProperty.getProperty(
        "TS16949", "true")).equals("true");
    
    
    
    private Vector eqVec=new Vector();
    private Vector toolVec=new Vector();
    private Vector materiaVec=new Vector();
    
    private Vector eqDeleVec=new Vector();
    private Vector toolDeleVec=new Vector();
    private Vector materiaDeleVec=new Vector();
    
    CappAssociationsLogic taskLogic ;

    
    /**
     * 构造函数
     * @param parentframe 调用本类的父窗口
     */
    public TechnicsPaceJPanelForZC(JFrame parentframe)
    {
        try
        {
            parentJFrame = parentframe;
             //CCBegin by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
            technicsContentJTextArea = new SpeCharaterTextPanel(parentframe,true);//CR4
             //CCEnd by leixiao 2010-5-4 打补丁 v4r3_p014_20100415  
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
        buttonJPanel.setLayout(gridBagLayout1);
        paceNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        paceNumberJLabel.setHorizontalTextPosition(SwingConstants.
                RIGHT);
        paceNumberJLabel.setText("*工步号");

        //CCBegin by leixiao 2010-3-30  增加工步编号
        descNumJTextField = new CappTextField(parentJFrame, "工步编号",
                10, false);
        descStepNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descStepNumberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        descStepNumberJLabel.setText("*工步编号");
        descNumJTextField.setMaximumSize(new Dimension(10, 24));
        descNumJTextField.setMinimumSize(new Dimension(10, 24));
        descNumJTextField.setPreferredSize(new Dimension(10, 24));
        descNumJTextField.setMargin(new Insets(1, 1, 1, 1));
        //CCEnd by leixiao 2010-3-30  增加工步编号
        
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame);
        //问题（3）20080709 xucy add  修改原因：工步关联类添加预留属性用
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
        storageJButton.setMaximumSize(new Dimension(97, 23));
        storageJButton.setMinimumSize(new Dimension(97, 23));
        storageJButton.setPreferredSize(new Dimension(97, 23));
        storageJButton.setMnemonic('T');
        storageJButton.setText("入库(ST)");
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
                             ProcessHoursJPanel(parentJFrame,true);
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
        //CCBegin by chudaming 20091119 插入工步
//        parentTechnics.getTechnicsType().getCodeContent();
        insertJLabel.setText("插入工步");
        insertCheckBox.setSelected(true);
      //CCEnd by chudaming 20091119 插入工步
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
        jTabbedPanel.add(paceJPanel, "工步信息");
        if (ts16949) {
          jTabbedPanel.add(flowExtendJPanel, "过程流程");
          jTabbedPanel.add(femaExtendJPanel, "过程FMEA");
          jTabbedPanel.add(controlExtendJPanel, "控制计划");
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
//        CCBegin SS7
        //CCBegin SS3
// jTabbedPanel.addChangeListener(new ChangeListener() {
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
//					
//					
//					if(mode==VIEW_MODE){
//					    processFlowJPanel.groupPanel.multiList.addTextCell(0, 6, String.valueOf(getProcedure().getMachineHour()));
//					    processFlowJPanel.groupPanel.multiList.addTextCell(0, 7, String.valueOf(getProcedure().getStepHour()));
//					}else if(mode==CREATE_MODE||mode==UPDATE_MODE){
//						
//						//CCBegin SS5
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
					    //CCEnd SS5*/
//						
//					}
//					
//					
//					
//					
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
        
        //CCEnd SS3
//        CCEnd SS7
        
        

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

        //CCBegin by liuzc 2009-11-29 原因：解放系统升级为v4r3。
        //加此监听的原因：当设备关联面板加入设备时，工装关联面板要加入与设备必要关联的工装
        equiplinkJPanel.addListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                toollinkJPanel.addRelationTools(e);
            }
        });
        //加此监听的原因：当工装关联面板加入工装时，设备关联面板要加入与工装必要关联的设备
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
                            "设备");
        relationsJPanel.add(toollinkJPanel,
                            "工装");
        relationsJPanel.add(materiallinkJPanel,
                            "材料");
        relationsJPanel.add(doclinkJPanel,
                            "文档");
        relationsJPanel.add(drawingLinkPanel,
                            "简图");
        
        
        //CCBegin by liuzc 2009-11-29 原因：解放系统升级为v4r3。
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
      //CCBegin by leixiao 2010-3-30  增加工步编号
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
      //CCEnd by leixiao 2010-3-30  增加工步编号
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
        
        
        taskLogic = new CappAssociationsLogic();
        
        

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
     * 设置在工艺树所选择的父节点
     * @param parentnode
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setParentProcedure();
        setTechnics();
    }


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
        //设置控件显示状态
        paceNumJTextField.setVisible(true);
        numDisplayJLabel.setVisible(false);
      //CCBegin by leixiao 2010-3-30  增加工步编号
        descNumJTextField.setVisible(true);
        descNumDisplayJLabel.setVisible(false);
      //CCEnd by leixiao 2010-3-30  增加工步编号

        technicsContentJTextArea.speCharaterTextBean.setEditable(true);

        //问题（3）20080820 徐春英修改     begin
        newEquiplinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(equiplinkJPanel != null)
        {
        equiplinkJPanel.setMode("View");
        }
        newToollinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(toollinkJPanel != null)
        {
        	toollinkJPanel.setMode("View");
        }
        newMateriallinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setMode("View");
        }
        doclinkJPanel.setMode("Edit");
        relationsJPanel.add(doclinkJPanel,
        "文档");
        relationsJPanel.add(drawingLinkPanel,
        "简图");
        drawingLinkPanel.setModel(2); //EDIT
        //问题（3）20080820 徐春英修改   end
        //零部件关联
        newPartlinkJPanel(technicsTypeCodeInfo.getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setMode("Edit");
            //处理简图输出
        }
        processHoursJPanel.setCreateMode();
      //CCBegin SS1
        //CCBegin by chudaming 20091119 插入工步
        this.insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 插入工步
      //CCEnd SS1
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
                //CCBegin by leixiao 2010-3-30  增加工步编号
                descNumJTextField.setText(String.valueOf(getStepInitNumber()));
                //CCEnd by leixiao 2010-3-30  增加工步编号
            }
            else
            {
                if (number >= 99999)
                {
                    return;
                }
                paceNumJTextField.setText(String.valueOf(number +
                        getStepLong()));
              //CCBegin by leixiao 2010-3-30  增加工步编号
                descNumJTextField.setText(String.valueOf(number + getStepLong()));
              //CCEnd by leixiao 2010-3-30  增加工步编号
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
        repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.setCreateMode() end...return is void");
        }
    }
//CCBegin SS1
    /**
     * 根据CAPP.property 中配置判断是否属于变速箱插入工序
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
        //设置控件显示状态
        paceNumJTextField.setVisible(true);
        paceNumJTextField.setText(Integer.toString(getProcedure().
                getStepNumber()));
        numDisplayJLabel.setVisible(false);
        
        //CCBegin by leixiao 2010-3-30  增加工步编号
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(getProcedure().getDescStepNumber());
        descNumDisplayJLabel.setVisible(false);
      //CCEnd by leixiao 2010-3-30  增加工步编号
      //CCBegin SS1 
        //CCBegin by chudaming 20091119 插入工步
        insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 插入工步
      //CCEnd SS1
        //简图与简图输出的设置顺序不能变，原因是若先设置简图输出，则设置简图时会在监听事件中将简图输出置空
        //简图
        drawingLinkPanel.setProcedure(getProcedure());
        drawingLinkPanel.setModel(2); //EDIT
        technicsContentJTextArea.speCharaterTextBean.setEditable(true);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
        	//20081212 xucy 修改
            technicsContentJTextArea.clearPaceAll();
            technicsContentJTextArea.resumeData(v);
        }

        processHoursJPanel.setProcedure(getProcedure());
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

        //问题（3）20080820 徐春英修改  begin
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
        equiplinkJPanel.setProcedure(getProcedure());
        equiplinkJPanel.setMode("View");
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        	toollinkJPanel.setProcedure(getProcedure());
        	toollinkJPanel.setMode("View");
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {
        	materiallinkJPanel.setProcedure(getProcedure());
        	materiallinkJPanel.setMode("View");
        }
        //20081120 徐春英修改   更新状态下文档应该可编辑
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel,
        "文档");
        relationsJPanel.add(drawingLinkPanel,
        "简图");
        //问题（3）20080820 徐春英修改 end
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());

        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());
            partlinkJPanel.setMode("Edit");
        }
        //20060728薛静修改，是否自动维护父节点的资源做成可配置的
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
                             //问题（3）20080820 徐春英修改 
                             if (eqflag)
                             {
                                 equiplinkJPanel.setMode("View");
                             }
                             else
                             if (equiplinkJPanel != null)
                             {
                                 equiplinkJPanel.setMode("View");
                             }
                            
                             if (toolflag)
                             {
                                 toollinkJPanel.setMode("View");
                             }
                             else
                             if (toollinkJPanel != null)
                             {
                                 toollinkJPanel.setMode("View");
                             }

                             if (matflag)
                             {
                                 materiallinkJPanel.setMode("View");
                             }
                             else
                             if (materiallinkJPanel != null)
                             {
                                 materiallinkJPanel.setMode("View");
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
                         if (partlinkJPanel != null)
                         {
                             partlinkJPanel.setMode("Edit");
                         }
                     }

        doclinkJPanel.setMode("Edit");
        f1.setEditMode();
        setButtonVisible(true);
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
        //设置控件显示状态
        paceNumJTextField.setVisible(false);
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        numDisplayJLabel.setVisible(true);
        
        //CCBegin by leixiao 2010-3-30  增加工步编号
        descNumJTextField.setVisible(false);
        descNumDisplayJLabel.setText(getProcedure().getDescStepNumber());
        descNumDisplayJLabel.setVisible(true);
      //CCEnd by leixiao 2010-3-30  增加工步编号
      //CCBegin SS1 
        //CCBegin by chudaming 20091119 插入工步
        insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 插入工步
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
        //问题（3）20080820 徐春英修改 begin
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
        //问题（4）20090112  刘志城修改 修改原因：优化工序查看速度，应改为：在查看模式下不刷简图值对象（blob），
        //                                 点击关联简图面板上的查看按钮时，调底层刷新简图方法。
        drawingLinkPanel.setProcedure(getProcedure());//CR2
        //问题（4）20090112 结束.
        relationsJPanel.add(drawingLinkPanel,
        "简图");
        //问题（3）20080820 徐春英修改  end
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
//        CCBegin SS7
        //CCBegin SS3
//        if(paceNumJTextField.check() == true&&descNumJTextField.check()==true){
//        	isOK = true;
//        }else{
//        	isOK = false;
//        }
        //CCEnd SS3
//        CCEnd SS7
        if (isOK)
        {
            //检验工艺内容是否为空
//            if (technicsContentJTextArea.save() == null ||
//                technicsContentJTextArea.save().trim().equals(""))
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
//                        null);
//                isOK = false;
//                technicsContentJTextArea.getTextComponent().grabFocus();
//            }
//           else
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
                    "cappclients.capp.view.TechnicsPaceJPanel.checkIsEmpty() end...return: " +
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
                //CCBegin by liunan 2011-08-25 打补丁P035
                //CR6 begin
                TechnicsContentJPanel.addFocusLis.setCompsFocusListener(d.getContentPane());
                //CR6 end
                //CCEnd by liunan 2011-08-25
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
            saveWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)
        {
            saveWhenUpdate();
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
//     * 更新模式下，退出按钮的执行方法
//     * add by guoxl 2009-1-6(只有更新界面数据修改了,才弹出是否保存对话框)
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
     * 更新模式下，退出按钮的执行方法
     * add by guoxl 2009-1-6(只有更新界面数据修改了,才弹出是否保存对话框)
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
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
        JOptionPane okCancelPane = new JOptionPane();
		return okCancelPane.showConfirmDialog(getParentJFrame(), s, title,
				JOptionPane.YES_NO_CANCEL_OPTION);//CR1
    }


    /**
     * 设置工步卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     */
    private CappWrapData commitAttributes()
    {
    	
    	this.eqVec.clear();
    	this.toolVec.clear();
    	this.materiaVec.clear();
    	
    	this.eqDeleVec.clear();
    	this.toolDeleVec.clear();
    	this.materiaDeleVec.clear();
    	
    	
    	this.setProcedure(this.getProcedure());//CR1
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
      //CCBegin by leixiao 2010-3-30  增加工步编号
        getProcedure().setDescStepNumber(descNumJTextField.getText().trim());
      //CCEnd by leixiao 2010-3-30  增加工步编号

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
        if (processFlowJPanel.check())
        {
        	
        	
        	
        	 if (procedure != null) {
 				Vector eqDeleteVec = processFlowJPanel.groupPanel.eqDeleteVec;
 				Vector oldEqVec=null;
 				
 				
 				Class[] paraClass1 = { String.class };
				Object[] objs1 = { procedure.getBsoID() };
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
            procedure.setProcessFlow(processFlowJPanel.
                                             getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("工步过程流程录入错误！");
            }
            isSave = false;
            return null;
        }
        if (femaJPanel.check()) {
          //设置FEMA
          procedure.setFema(femaJPanel.getExAttr());
        }
        else {
          if (verbose) {
            System.out.println("工步FMEA录入错误！");
          }
          isSave = false;
          return null;
        }
        if (processControlJPanel.check())
        {
        	
        	
        	System.out.println("pppppppppppppppppppp=======*************============================"+procedure.getBranchID());
    		if (procedure != null) {
				Vector eqDeleteVec = processControlJPanel.groupPanel.eqDeleteVec;
				Vector materDeleteVec = processControlJPanel.groupPanel.materDeleteVec;
				Vector toolDeleteVec = processControlJPanel.groupPanel.toolDeleteVec;
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
									
									int oldCount=0;
									if(oldTool.getUsageCount()!=null)
										oldCount=Integer.parseInt(oldTool.getUsageCount());
									
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

											binarylinkinfo.setUsageCount(1);
											materiaVec
													.addElement(binarylinkinfo);

										}
										System.out.println("materiaVec============**************==============================="+materiaVec);

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
											 
											 System.out.println("toolVec============**************==============================="+toolVec);
											 
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
						//CCBegin SS4
						if(attrName.equals("Count")){
							intCount = (String)model.getAttValue();
						 }
						///CCEnd SS4
						
					}
				}

			}
        	
        	
        	
            //设置过程控制
            procedure.setProcessControl(processControlJPanel.
                                             getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("工步过程控制录入错误！");
            }
            isSave = false;
            return null;
        }


        //获得所有关联(设备、工装、材料、文档)
        Vector docLinks = new Vector();
        Vector equipLinks = new Vector();
        Vector toolLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector partLinks = new Vector();
	    ArrayList updatedrawings = null;//Begin CR2
        ArrayList deletedrawings = null;//End CR2
        //问题(2) 20080602 徐春英修改  将搜索到的文档转换成文档master和工序的关联
        Vector docMasterLinks = new Vector();
        
        //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
        //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题   begin
        Vector rEquipLinks = new Vector();
        Vector rToolLinks = new Vector();
        Vector rMaterialLinks = new Vector();
        Vector rDocLinks = new Vector();
        Vector rPartLinks = new Vector();
        //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
        //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题   end
        try
        {
            docLinks = doclinkJPanel.getAllLinks();
            //问题（5）
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
            //问题（3）20080820 徐春英修改   修改原因：工序关联类添加预留属性
            //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
            //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题  
            if (equiplinkJPanel != null)
            {
	            equipLinks = equiplinkJPanel.getAllLinks();
	            
	            if(eqVec.size()!=0){
                	for(int a=0;a<eqVec.size();a++){
                		boolean isHas=false;
                		QMProcedureQMEquipmentLinkInfo newlinkinfo=(QMProcedureQMEquipmentLinkInfo)eqVec.get(a);
                		System.out.println("newlinkinfo.getLeftBsoID()================步=================="+newlinkinfo.getLeftBsoID());
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
	            
	            
	            //问题（5）
	            rEquipLinks = equiplinkJPanel.getDeletedLinks();
            }
            if (toollinkJPanel != null)
            {
	            toolLinks = toollinkJPanel.getAllLinks();
	            
	            
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
	            
	          //问题（5）
	            rToolLinks = toollinkJPanel.getDeletedLinks();
            }
            if (materiallinkJPanel != null)
            {
                materialLinks = materiallinkJPanel.getAllLinks();
                
                
                if (materiaVec.size() != 0) {
    				for (int b = 0; b < materiaVec.size(); b++) {

    					
    					QMProcedureQMMaterialLinkInfo newMaterLinkInfo=(QMProcedureQMMaterialLinkInfo)materiaVec.get(b);
                		   
                		   String newLinkID=newMaterLinkInfo.getRightBsoID();
                		   

                		   QMProcedureQMMaterialLinkInfo oldMateLinkInfo=(QMProcedureQMMaterialLinkInfo) findIsHasObj(newLinkID,materialLinks);
                		   
                		   if(oldMateLinkInfo!=null){
                			   
                			   
                			   oldMateLinkInfo.setUsageCount(newMaterLinkInfo.getUsageCount());
                			   int a=materialLinks.indexOf(oldMateLinkInfo);
                			   materialLinks.remove(a);
                			   materialLinks.add(oldMateLinkInfo);
                			   
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
                
                
                
              //问题（5）
                rMaterialLinks = materiallinkJPanel.getAllLinks();
            }
            if (partlinkJPanel != null)
            {
                partLinks = partlinkJPanel.getAllLinks();
                //问题（5）
                rPartLinks = partlinkJPanel.getAllLinks();
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
        //将所有关联合并
        Vector resourceLinks = new Vector();
        //合并文档关联
        for (int k = 0; k < docMasterLinks.size(); k++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(k));
        }
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
		// 合并简图资源关联   Begin CR2
		/*if (drawingLinks != null)
		{
			for (int n = 0; n < drawingLinks.size(); n++)
			{
				resourceLinks.addElement(drawingLinks.get(n));
			}
		}*/
        //End CR2
        //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
        //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题   begin
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
        //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
        //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题   end
        
        //封装所有信息
        CappWrapData cappWrapData = new CappWrapData();
        
        //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
        //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题   begin
        if(delLinks != null)
        {
        	cappWrapData.setDeleteLinks(delLinks);
        }
        //问题（5）20081231 xucy  修改原因：在工序中创建工步，工步关联一个工装，然后更新这个工步把关联的工装删除
        //保存之后工序中的工装没有删除，其他资源的关联也存在这样的问题   end
        //设置工序卡
        cappWrapData.setQMProcedureIfc(getProcedure());
        //封装关联
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
		cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR2
	    cappWrapData.setDeleteDrawing(deletedrawings);//End CR2
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
    //add by wangh on 20070310
   private void paceTS16949Panel(String processType)
  {
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
                  processFlowJPanel = new CappExAttrPanelForZC(procedure.getBsoName(),
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
          // CCBegin by leixiao 2008-10-28 原因：解放系统升级 ，用于数据传递
//        CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
//          if ( ( (QMProcedureInfo) selectedNode.getObject().getObject()).
//              getIsProcedure()) {  
//            processFlowJPanel.setProIfc((QMProcedureInfo) procedure);
//          }
//          else if (parentProcedure != null) {  
            processFlowJPanel.setProIfc(procedure);
//          CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
//          }

          // CCEnd by leixiao 2008-10-28 原因：解放系统升级 
          if (femaJPanel != null)
          {
              femaExtendJPanel.remove(femaJPanel);
          }
          if (femaTable.get(processType) == null)
          {
              try
              {
                  femaJPanel = new CappExAttrPanelForZC(procedure.getBsoName(),
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
//        CCBegin by leixiao 2008-10-28 原因：解放系统升级 
//        CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
//          if ( ( (QMProcedureInfo) selectedNode.getObject().getObject()).
//              getIsProcedure()) {
//        	  femaJPanel.setProIfc((QMProcedureInfo) procedure);
//          }
//          else if (parentProcedure != null) {
            femaJPanel.setProIfc(procedure);
//          CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
//          }
          // CCEnd by leixiao 2008-10-28 原因：解放系统升级，用于数据传递 
          if (processControlJPanel != null)
          {
              controlExtendJPanel.remove(processControlJPanel);
          }
          if (processControlTable.get(processType) == null)
          {
              try
              {
                  processControlJPanel = new CappExAttrPanelForZC(procedure.getBsoName(),
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
              processControlJPanel = (CappExAttrPanelForZC) processControlTable.get(
                      processType);
          }
          //CCBegin by leixiao 2008-10-28 原因：解放系统升级，用于数据传递
//        CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
//          if ( ( (QMProcedureInfo) selectedNode.getObject().getObject()).
//              getIsProcedure()) {
//              processFlowJPanel.setProIfc((QMProcedureInfo) procedure);
//          }
//          else if (parentProcedure != null) {
            processControlJPanel.setProIfc(procedure);
//          CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
//          }
          //CCEnd by leixiao 2008-10-28 原因：解放系统升级 
           existProcessType = processType;
      }
      processFlowJPanel.clear();
      femaJPanel.clear();
      processControlJPanel.clear();
      
      //CCBegin SS2
      processFlowJPanel.groupPanel.inheritButton.addActionListener(new ChuanDiActionListener());
      femaJPanel.groupPanel.inheritButton.addActionListener(new ChuanDiActionListener());
      processControlJPanel.groupPanel.inheritButton.addActionListener(new ChuanDiActionListener());
      //CCEnd SS2
      
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

                                          //add by wangh on 200726(设置过程流程，过程FMEA和控制计划是否可见)
//                                          if (!ts16949) {
//                                            processFlowJPanel.setVisible(false);
//                                            femaJPanel.setVisible(false);
//                                            processControlJPanel.setVisible(false);
//                                          }
   //add by guoxl on 2009-1-7(给工步更新界面上所有控件添加监听)
     if(this.getMode()==0)
     {
    	 TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
     }
    //add by guoxl end on 2009-1-7
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
        //CCBegin SS1
        //检验工步编号是否为整型
        if (!checkIsInteger(descNumJTextField.getText().trim()))
        {
        	descNumJTextField.grabFocus();
        	descNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //CCEnd SS1
        
//      CCBegin SS7
        if(processFlowJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processFlowJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(femaJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	femaJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(processControlJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processControlJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//      CCEnd SS7
//      CCBegin SS8
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
      }
//      CCEnd SS8
        
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
        QMProcedureInfo returnProce;//CR1
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
          //CCBegin SS1  
          //CCBegin by chudaming 20091119 插入工步
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
            //CCEnd by chudaming 20091119 插入工步
          //CCEnd SS1
            //Begin CR2
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//End CR2

            
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //把新建的工序节点挂上工艺树
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


        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //提示是否连续建
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
     * 保存更新的工步
     */
    private void saveWhenUpdate()
    {
    	
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsPaceJPanel.saveWhenUpdate() begin...");
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
        if (!checkIsInteger(paceNumJTextField.getText().trim()))
        {
            paceNumJTextField.setText("");
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            paceNumJTextField.grabFocus();
            return;
        }
        
//      CCBegin SS7
        if(processFlowJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processFlowJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(femaJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	femaJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
        if(processControlJPanel.groupPanel.multiList.getCellText(0, 0) != null)
        	processControlJPanel.groupPanel.multiList.addTextCell(0, 0, descNumJTextField.getText());
//      CCEnd SS7
//      CCBegin SS8
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
      }
//      CCEnd SS8

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
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Object[] obj =
                    {
                    parentTechnics.getBsoID(),
                    parentProcedure.getBsoID(),
                    cappWrapData};
            QMProcedureInfo returnProce;
          //CCBegin SS1      
            //CCBegin by chudaming 20091119 插入工步
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
          //CCEnd by chudaming 20091119 插入工步
          //CCEnd SS1
            //Begin CR2
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }
            //End CR2
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
              //CCBegin SS1  
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

        //隐藏进度条
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;
        //问题（4）20081226 xucy  修改原因：优化更新时保存工序 begin
//        try
//        {
//            setViewMode(VIEW_MODE);
//        }
//        catch (PropertyVetoException ex1)
//        {
//            ex1.printStackTrace();
//        }
      //问题（4）20081226 xucy  修改原因：优化更新时保存工序 end
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
        //CCBegin by leixiao 2010-6-30 打补丁v4r3_p017_20100617 见标识CR5
    public void addObjectToTable(BaseValueInfo[] info)
    {
    	//问题（3）20080820 徐春英修改
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
   //CCEnd by leixiao 2010-6-30 打补丁v4r3_p017_20100617 见标识CR5

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
        numDisplayJLabel.setText("");
      //CCBegin by leixiao 2010-3-30  增加工步编号
        descNumJTextField.setText("");
        descNumDisplayJLabel.setText("");
      //CCEnd by leixiao 2010-3-30  增加工步编号
        drawingLinkPanel.clear();
        processHoursJPanel.clear();
        //equiplinkJPanel.clear();
        //materiallinkJPanel.clear();
        //toollinkJPanel.clear();
        doclinkJPanel.clear();
      //CCBegin SS1
        //CCBegin by chudaming 20091119 插入工步
        insertCheckBox.setSelected(false);
      //CCEnd by chudaming 20091119 插入工步
      //CCEnd SS1
        existProcessType = "";
        d = null;
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

        //重新实例化工艺内容
        //20081212 xucy 修改
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
     * 问题（3）20080820 徐春英修改
     */
    private synchronized void newEquiplinkJPanel(String stepType)
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
     * 问题（3）20080820 徐春英修改
     */
    private synchronized void newToollinkJPanel(String stepType)
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
     * 问题（3）20080820 徐春英修改
     */
    private synchronized void newMateriallinkJPanel(String stepType)
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
//CCBegin SS2
    
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
//					CCBegin SS6
//					processFlowJPanel.groupPanel.multiList.addTextCell(
//							pfSelectRow, 11, texingfl);
					Vector vc = new Vector();
					vc.add(texingfl);
					processFlowJPanel.groupPanel.multiList.addSpeCharCell(pfSelectRow, 11, vc);
//					CCEnd SS6
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
//					CCBegin SS4
//					String texingfl = processFlowJPanel.groupPanel.multiList
//							.getCellText(pfSelectRow, 11);
					String texingfl = processFlowJPanel.groupPanel.multiList.
							getCellAt(pfSelectRow, 11).getSpecialCharacter().save();
//					CCEnd SS4
					

					processControlJPanel.groupPanel.multiList.addTextCell(
							contSelectRow, 5, chanpintx);
					processControlJPanel.groupPanel.multiList.addTextCell(
							contSelectRow, 6, guochengtx);
//					CCBegin SS4
//					processControlJPanel.groupPanel.multiList.addTextCell(
//							contSelectRow, 7, texingfl);
					Vector vc = new Vector();
					vc.add(texingfl);
					processControlJPanel.groupPanel.multiList.addSpeCharCell(contSelectRow, 7, vc);
//					CCEnd SS4
				}
    			
    			
    		}
    		
    	}
    	
    	

    }
    
    
//CCEnd SS2

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
