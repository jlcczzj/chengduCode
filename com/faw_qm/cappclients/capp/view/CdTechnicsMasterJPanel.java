/** 生成程序TechnicsMasterJPanel.java	1.1  2003/8/6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利     
 * 
 *CR1 2009/04/28 刘玉柱      原因：工艺规程的肥客户界面没有工艺的创建者、当前状态等等信息
 *                           方案：添加属性：“创建者”“创建时间”“修改者”“修改时间”“版本”。
 * CR2 2009/04/29 刘志城    原因：工序简图的修改，简图字段分开为两个
 *                         方案：将简图字段分开存储                            
 * CR3 2009/05/05  刘玉柱     原因：创建，更新界面不出现“创建者”“创建时间”等属性
 *                           方案：在创建更新界面设置属性不可见，并调整布局  
 * CR4 2009/05/19  徐春英   原因：创建工艺主信息，输入数据库中已有工艺编号，点击其他树节点，出现编号不唯一的提示信息，
 *                                关闭提示框之后，进度条不关闭。  
 *                          方案：在catch的头部关闭进度条     
 * CR5 2009/05/21 徐春英    原因：在新建工艺主信息界面点击取消按钮，部门和生命周期没取消
 *                          方案：调用clear()方法执行新建界面的取消功能   
 * CR6 2009/05/31 徐春英   参见DefectID=2169  
 * CR7 2009/07/01 刘玉柱   参见DefactID=2498   
 * SS1 修改部门属性如果没有，则界面显示""，避免出现异常界面无法正常显示。 liunan 2012-9-11
 * SS2 部门检查，如果当前选中附加属性页，点保存，就无法判断出部门是空。 liunan 2012-9-13
 * SS3 变速箱工艺自动编号 zhaoqiuying 2013-1-23
 * SS4 变速箱资源清单一览表 zhaoqiuying 2013-01-23
 * SS5 增加变速箱 “另存为”模式自身的定制 liuyang  2013-03-15
 * SS6 根据不同公司选择不同的实施TS16949属性面板 liuyang 2013-3-22
 * SS7 变速箱工艺下有工序时，部门不能修改 liuyang 2013-2-22
 * SS8 变速箱公司显示备注为PFEMA编号 liuyang 2013-3-27
 * SS9 变速箱公司组织机构能展开类别下代码内容 pant 2013-4-17
 * SS10 添加文件服务器上传附件分支 jiahx 2013-11-05
 * SS11 轴齿工艺自动编号  文柳 2014-1-9
 * SS12 轴齿新增"总成型号”、“总成名称”、“数量”属性  文柳 2014-2-17
 * SS13 变速箱默认使用自己的生命周期。 liunan 2014-5-9
 * SS14 变速箱更新部门时，允许选择子分类。 liunan 2014-5-26
 * SS15 修改轴齿工艺生命周期默认值,并使其可选 pante 2014-05-16
 * SS16 修改轴齿工艺选择产品平台出错问题 pante 2014-06-19
 * SS17 按用户要求改为总成型号总成名称和数量 pante 2014-06-23
 * SS18 在查看工艺主信息界面点击创建工艺时,新建界面中产品平台相关文本框及按钮不能用 pante 2014-07-31
 * SS19 轴齿工艺产品状态不显示变速箱的 pante 2014-10-09
 * SS20 轴齿工艺添加主要零件时，如果零件有材料牌号就自动添加到其他相关信息中 pante 2014-10-23
 * SS21 设置长特默认生命周期 guoxiaoliang 2014-08-21
 * SS22 长特增加工装明细，设备清单，模具清单。 guoxiaoliang 2014-08-22
 * SS23 长特TS16949不根据编号变化 文柳 2014-10-20
 * SS24 解放整车装配工艺，要单走流程，增加了客户会签任务。 liunan 2014-12-10
 * SS25 长特工艺主信息产品状态，去掉变速箱状态 文柳 2014-12-22
 * SS26 变速箱另存为时也默认使用自己的生命周期。 liunan 2015-2-25
 * SS27 长特默认部门 文柳 2015-3-26
 * SS28 td12008 创建成都质量检查表工艺规程ff，无法选择检查工艺种类；根据用户选择适用车型和检查工艺种类自动生成工艺编号； guoxiaoliang 2016-11-2
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsQMDocumentLinkInfo;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.drawingpanel.DrawingPanel;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.resource.view.ResourcePanel;
import com.faw_qm.cappclients.util.CappCharTextField;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
//CCBegin by liuzc 2009-11-29 原因：解放系统升级为v4r3。
import com.faw_qm.ration.client.view.AddPartByRouteListJDialog;
import com.faw_qm.ration.client.view.RationListPartLinkPanel;
import com.faw_qm.ration.exception.RationException;  
import com.faw_qm.ration.util.RationHelper;
//CCEnd by liuzc 2009-11-29 原因：解放系统升级为v4r3。
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCBegin SS27
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
//CCEnd SS27

/**
 * <p>Title: 工艺卡维护面板</p>
 * <p>Description: 工艺卡的创建、更新、查看、另存为操作均在本面板维护。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 * （1） 20060814薛静修改，修改方法cancelWhenCreate（），将文档，零件，材料关联清除
 * (2)20070201薛静修改，增加基本属性：产品状态，增加TS16949属性
 * 问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
 * 然后双击第一层结构节点后展开第二层结构
 * 只有一级菜单的选择添加项目，改为下拉列表方式添加
 */
 
public class CdTechnicsMasterJPanel extends TechnicsMasterJPanel 
{
    /**保存按钮*/
    private JButton saveJButton = new JButton();


    /**取消按钮*/
    private JButton cancelJButton = new JButton();


    /**退出按钮*/
    private JButton quitJButton = new JButton();
//CCBegin SS11
    //记录主要零件编号，为了组装工艺自动编号
    public JTextField hideMainPart = new JTextField();
//CCEnd SS11
//CCBegin SS12
    //记录主要零件bsoID,为了轴齿选择产品平台
    public JTextField hideMainPartBsoID = new JTextField();
//CCEnd SS12   
    private JPanel buttonJPanel = new JPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel masterJPanel = new JPanel();
    private JLabel numberJLabel = new JLabel();
    private JLabel nameJLabel = new JLabel();
    private JLabel typeJLabel = new JLabel();
//  CCBeginby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
    public llTextField numberJTextField;
//  CCEndby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成  
//  CCBeginby leixiao 2010-4-20 原因：零件面板添加零件时，工艺名称自动生成
    public CappCharTextField nameJTextField;
//  CCEndby leixiao 2010-4-20 原因：零件面板添加零件时，工艺名称自动生成
    private JLabel remarkJLabel = new JLabel();
    private CappCharTextField remarkJTextField;
    private FolderPanel folderPanel = new FolderPanel();

    private JLabel numberDisplayJLabel = new JLabel();
    private JLabel nameDisplayJLabel = new JLabel();
    private JLabel typeDisplayJLabel = new JLabel();
    private JLabel remarkDisplayJLabel = new JLabel();
    //CCBegin SS12
    public JTextField separableNumber = new JTextField();
    private JLabel separableNumberJLabel = new JLabel();
    private JLabel separableNumberDisplayJLabel = new JLabel();
    public JTextField separableName = new JTextField();
    private JLabel separableNameJLabel = new JLabel();
    private JLabel separableNameDisplayJLabel = new JLabel();
    public JTextField separableCount = new JTextField();
    private JLabel separableCountJLabel = new JLabel();
    private JLabel separableCountDisplayJLabel = new JLabel();
    private JButton selectPartJButton = new JButton();
    private JPanel separableJPanel = new JPanel();
    //CCEnd SS12
    
	// Begin CR1
	private JLabel creatorJLabel = new JLabel();
	private JLabel creatorTextField = new JLabel();
	private JLabel creatTimeJLabel = new JLabel();
	private JLabel creatTimeTextField = new JLabel();
	private JLabel moderfilerJLabel = new JLabel();
	private JLabel moderfilerTextField = new JLabel();
	private JLabel modifyTimeJLabel = new JLabel();
	private JLabel modifyTimeTextField = new JLabel();
	private JLabel iterationJLabel = new JLabel();
	private JLabel iterationTextField = new JLabel();
    // End CR1
	
	//Begin CR7
	private JLabel workStateJLabel = new JLabel();
	private JLabel workStateTextField = new JLabel();
    //End CR7
	
    /**仅用于界面布局方便，没有实际意义*/
    // private JPanel extraJPanel = new JPanel();
    /**界面显示模式（更新模式）标记*/
    public final static int UPDATE_MODE = 0;


    /**界面显示模式（创建模式）标记*/
    public final static int CREATE_MODE = 1;


    /**界面显示模式（查看模式）标记*/
    public final static int VIEW_MODE = 2;


    /**界面显示模式（另存为模式）标记*/
    public final static int SAVEAS_MODE = 3;


    /**界面模式--查看*/
    private int mode = VIEW_MODE;


    /**工艺卡值对象*/
    private QMFawTechnicsInfo technicsInfo;


    /**父窗口*/
    private JFrame parentJFrame;


    /**当前选择的工艺树节点*/
    private CappTreeNode selectedNode;


    private JTabbedPane relationsJTabbedPane = new JTabbedPane();


    /**工艺使用文档关联的维护面板*/
    private TechUsageDocLinkJPanel doclinkJPanel = new
            TechUsageDocLinkJPanel();


    /**工艺使用材料关联的维护面板*/
    private TechUsageMaterialLinkJPanel materialLinkJPanel;


    /**零部件使用工艺卡关联的维护面板*/
    private PartUsageTechLinkJPanel partLinkJPanel;
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel extendJPanel = new JPanel();
    private CappExAttrPanel cappExAttrPanel;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();

    /**缓存：执行另存为之前的源工艺卡*/
    private QMTechnicsInfo existTechnicsInfo;


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel jpanel = new JPanel();


    /**标记是否可以退出*/
    private boolean isSave = true;
    private JLabel workShopJLabel = new JLabel();
    //CCBegin SS11
    public CappSortingSelectedPanel workshopSortingSelectedPanel = null;
    //CCEnd SS11
    private JLabel workShopDisplayjLabel = new JLabel();

    private String existTechnicsType = "";


    /**记录是否第一次进入此界面*/
    private boolean firstInFlag = true;


    /**存放扩展属性bean;键:工艺种类 值:扩展属性bean*/
    private Hashtable extendTable = new Hashtable();
    private Hashtable materialLinkTable = new Hashtable();
    private Hashtable partLinkTable = new Hashtable();
    public CodingIfc technicsType;
    private DrawingPanel drawingpanel;
    private JLabel flowDrawingDisp;
//    private CappWrapData data;
    /**
     * 产品状态选择框
     * 20070201薛静添加
     */
    private CappSortingSelectedPanel productStateSortingSelectedPanel = null;
    private JLabel productStateJLabel;
    private JLabel productStateDisplJLabel;
    
    private JLabel checkTechTypeDisplJLabel;

    /**
     * 是否实施TS16949属性
     */
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    //CCBegin SS11
    public MasterTS16949Panel masterTS16949Panel;
    //CCEnd SS11
    //CCBegin SS6
    private CONSMasterTS16949Panel consmasterTS16949Panel;
    //CCEnd SS6
    //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
    //然后双击第一层结构节点后展开第二层结构
    //只有一级菜单的选择添加项目，改为下拉列表方式添加
    private JComboBox productStateComboBox = new JComboBox();
    
    private JLabel checkTechTypeLabel=new JLabel("检查工艺种类");
    private JComboBox checkTechTypeComboBox = new JComboBox();
    
    
//  CCBeginby leixiao 2009-3-31 原因：解放升级工艺,编号按类型出
    public static final Map typeMap = new HashMap(4);
    static {
    	typeMap.put("薄板冲压工艺", "YB-");
    	typeMap.put("厚板冲压工艺", "YH-");
    	typeMap.put("驾驶室焊装工艺", "HZ-");
    	typeMap.put("驾驶室涂装工艺", "TJ-");
    	typeMap.put("内饰装配工艺", "NS-");
    	typeMap.put("整车装配工艺", "ZC-");
    	typeMap.put("车架装配工艺", "CJ-");
    	typeMap.put("车架涂装工艺", "TC-");
    	typeMap.put("钻焊工艺", "HK-");
    	//CCBegin by liunan 2010-11-17 发动机添加“机加工艺”和“特殊工艺” 预留位置。
    	typeMap.put("机加工艺", "");
    	typeMap.put("特殊工艺", "");
    	//CCEnd by liunan 2010-11-17
      }
//  CCEndby leixiao 2009-3-31 原因：解放升级工艺路线

//CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
      private UpFilePanel upFilePanel;
//CCEnd by liunan 2009-10-12
      
      //CCBegin SS10
      static boolean fileVaultUsed = (RemoteProperty.getProperty(
              "registryFileVaultStoreMode", "true")).equals("true");
      //CCEnd SS10
    /**
     * 构造函数
     * @param parentFrame 调用本类的父窗口
     * @param parentnode  父节点
     */
    public CdTechnicsMasterJPanel(JFrame parentFrame)
    {
    	super(parentFrame);
    	
        try
        {
            parentJFrame = parentFrame;

            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
  
    
//CCBegin SS11
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
//CCEnd SS11
    
    
    //CCBegin SS28
    /**
     * 获得质量检查表工艺编号流水号
     */
    public String getTechnicsAutoNum() throws QMException 
    {
    	 Class[] paraclass ={String.class,String.class,boolean.class};
         Object[] paraobj ={"cdZLJCListTechnics","cdZLJCList",false};
         String returnStr = String.valueOf( CappClientHelper.useServiceMethod(
                 "StandardCappService", "getNextSortNumber", paraclass, paraobj));
         return returnStr;
	}
	//CCEnd SS28
    

    /**
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        initResources();
        setLayout(gridBagLayout4);
        String technicsNumdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null);
//      CCBegin by leixiao 2009-6-18 原因：解放系统升级 ,编号长度20->50
        numberJTextField = new llTextField(parentJFrame, technicsNumdisp, 50, false);
//      CCEnd by leixiao 2009-6-18 原因：解放系统升级 ,编号长度20->50
        String technicsNameNamedisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null);
        nameJTextField = new CappCharTextField(parentJFrame,
                                               technicsNameNamedisp, 40, false);
        String remarkdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "remarkJLabel", null);
        //CCBegin by liuzhicheng 2010-02-25 原因：解放要求增大备注输入长度。
        remarkJTextField = new CappCharTextField(parentJFrame, remarkdisp, 100, true);
        //CCEnd by liuzhicheng 2010-02-25。
        productStateJLabel=new JLabel("产品状态");
        productStateDisplJLabel=new JLabel();
        
        checkTechTypeDisplJLabel=new JLabel();
        
        String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.WORKSHOP, null);
         flowDrawingDisp = new JLabel(getMessage("flowdrawing"));
         //部门，代码分类可选（部门只能选择厂和分厂）
         //CCBegin by liuzc 2009-11-29 原因：解放系统升级。
         //CCBegin SS11
         hideMainPart.setText("");//初始化时，清理变量
         if(getUserFromCompany().equals("zczx")){
//             workshopSortingSelectedPanel = new CappSortingSelectedPanel(
//                     title2, "QMTechnics", "workShop",this); 
         }else{
             workshopSortingSelectedPanel = new CappSortingSelectedPanel(
                     title2, "QMTechnics", "workShop");
         }
         //CCEnd SS11

         productStateSortingSelectedPanel=new CappSortingSelectedPanel("产品状态","QMTechnics","productState");
         productStateSortingSelectedPanel.setDialogTitle("产品状态");
         //CCEnd by liuzc 2009-11-29 原因：解放系统升级。
           
        // Begin CR1
 		creatorJLabel = new JLabel("创建者");
 		creatTimeJLabel = new JLabel("创建时间");
 		moderfilerJLabel = new JLabel("修改者");
 		modifyTimeJLabel = new JLabel("修改时间");
 		iterationJLabel = new JLabel("版本");
        //End CR1
 
 		workStateJLabel = new JLabel("工作状态");//CR7
         
        //代码分类可选
//        workshopSortingSelectedPanel.setIsSelectCC(true);
//        workshopSortingSelectedPanel.setButtonSize(89, 23);
//        workshopSortingSelectedPanel.setDialogTitle(title2);
        localize();
        folderPanel.setIsPersonalFolder(true);
        folderPanel.setIsPublicFolders(false);
        
        
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new
                Dimension(89, 23));
        lifeCycleInfo.getProjectPanel().setMnemonicAndText('R',
                "R");
        //CCBegin SS6
        Boolean yy = false; 	
      	try {
  	    	Class[] paraClass = {};
  	        Object[] objs = {};
  	        String className = "com.faw_qm.doc.util.DocServiceHelper";
  	        String methodName = "isBSXGroup";
  	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
  	        in.setClassName(className);
  	        in.setMethodName(methodName);
  	        in.setParaClasses(paraClass);
  	        in.setParaValues(objs);
  	        RequestServer server = null;
  	        server = RequestServerFactory.getRequestServer();
  		
  			yy = (Boolean) server.request(in);
  		} catch (QMRemoteException e) {
  			e.printStackTrace();
  		}
        if(ts16949){

        	 if(yy){
       		  consmasterTS16949Panel=new CONSMasterTS16949Panel();
       		  System.out.println("ts16949====="+ts16949);
       	     }else{
                 masterTS16949Panel=new MasterTS16949Panel();
                 System.out.println("yy====="+yy);
       	     }
        	 
        }
        //CCEnd SS6
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        upFilePanel = new UpFilePanel(this.parentJFrame);
        //CCEnd by liunan 2009-10-12
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setVerifyInputWhenFocusTarget(true);
        saveJButton.setActionCommand("SAVE");
        saveJButton.setMnemonic('S');
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
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
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
        quitJButton.setActionCommand("QUIT");
        quitJButton.setMnemonic('Q');
        quitJButton.addActionListener(new java.awt.event.
                                      ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
        buttonJPanel.setLayout(gridBagLayout1);
        masterJPanel.setLayout(gridBagLayout2);
        numberJLabel.setMaximumSize(new Dimension(53, 22));
        numberJLabel.setMinimumSize(new Dimension(53, 22));
        numberJLabel.setPreferredSize(new Dimension(53, 22));
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        typeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        remarkJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        remarkJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        extendJPanel.setLayout(gridBagLayout3);
        numberDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        numberDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        numberDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        nameDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        nameDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        nameDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        typeDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        typeDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        typeDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        remarkDisplayJLabel.setMaximumSize(new Dimension(6, 23));
        remarkDisplayJLabel.setMinimumSize(new Dimension(6, 23));
        remarkDisplayJLabel.setPreferredSize(new Dimension(6, 23));
        jTabbedPane1.setMaximumSize(new Dimension(405, 32767));
        jTabbedPane1.setMinimumSize(new Dimension(405, 78));
        jTabbedPane1.setPreferredSize(new Dimension(405, 536));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        scrollPane.setMaximumSize(new Dimension(405, 32767));
        scrollPane.setMinimumSize(new Dimension(405, 24));
        scrollPane.setPreferredSize(new Dimension(405, 507));
        setMaximumSize(new Dimension(2000, 2147483647));
        setMinimumSize(new Dimension(405, 121));
        setPreferredSize(new Dimension(405, 800));
        masterJPanel.setBorder(null);
        masterJPanel.setMaximumSize(new Dimension(370, 2147483647));
        masterJPanel.setMinimumSize(new Dimension(370, 463));
        masterJPanel.setPreferredSize(new Dimension(370, 487));
        numberJTextField.setMaximumSize(new Dimension(200, 22));
        jpanel.setMaximumSize(new Dimension(405, 2147483647));
        jpanel.setPreferredSize(new Dimension(405, 470));
        //CCBegin by liuzc 2009-11-29 原因：解放系统升级。
        lifeCycleInfo.setMaximumSize(new Dimension(200, 60));
        //CCEnd by liuzc 2009-11-29 原因：解放系统升级。
        lifeCycleInfo.setPreferredSize(new Dimension(20, 60));
        lifeCycleInfo.setMinimumSize(new Dimension(20, 60));
        extendJPanel.setBorder(BorderFactory.createEtchedBorder());
        masterJPanel.add(numberJLabel,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(10, 15, 5,
                0), 0, 0));
        masterJPanel.add(nameJLabel,
                         new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(5, 15, 5,
                0), 0, 0));
        masterJPanel.add(typeJLabel,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
        masterJPanel.add(numberJTextField,
                          new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0//CR1
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                          new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0//CR1
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));

        //添加替换组件(更新工艺卡时，编号、名称、类型显示标签)
        masterJPanel.add(numberDisplayJLabel,
                          new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameDisplayJLabel,
                          new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 8, 5, 15), 0, 0));
        masterJPanel.add(typeDisplayJLabel,
                         new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                ,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.
                                                HORIZONTAL,
                                                new Insets(5, 8, 5, 8),
                                                0, 0));
        masterJPanel.add(workShopJLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 100, 0, 0), 0, 0));//CR1
//        masterJPanel.add(workshopSortingSelectedPanel, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
//            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 16), 0, 0));
        masterJPanel.add(workShopDisplayjLabel, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 40, 5, 0), 0, 0));//CR1 
       
        //CCBegin SS11
        if(getUserFromCompany().equals("zczx")){
//            workshopSortingSelectedPanel = new CappSortingSelectedPanel(
//                    title2, "QMTechnics", "workShop",this); 
        }else{
        	 //部门，代码分类可选（部门只能选择厂和分厂)
            workshopSortingSelectedPanel = new CappSortingSelectedPanel(
                    title2, "QMTechnics", "workShop");
        }
        //CCEnd SS11
        workshopSortingSelectedPanel.setIsSelectCC(true);
        workshopSortingSelectedPanel.setButtonSize(89, 23);
        workshopSortingSelectedPanel.setDialogTitle(title2);
        workshopSortingSelectedPanel.setSelectBMnemonic('W');
        masterJPanel.add(workshopSortingSelectedPanel, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 16), 0, 0));
//        masterJPanel.add(relationsJTabbedPane,
//            new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0,
//                                   GridBagConstraints.EAST,
//                                   GridBagConstraints.BOTH, new Insets(5, 8, 5, 8), 0, 0));
        //add by guoxl on 20080226(界面布局问题)
            masterJPanel.add(relationsJTabbedPane,
               new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 9, 5, 7), 0, -1000));
        //add end

	    //添加替换组件(查看工艺卡时，备注显示标签)
	   

	    masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 2, 2, 1.0, 0.0//CR1
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
	    masterJPanel.add(folderPanel,
	            new GridBagConstraints(0, 7, 2, 1, 0, 0,GridBagConstraints.CENTER, 
	            		GridBagConstraints.HORIZONTAL, new Insets(5, 27, 0, 15), 0, 0));
	    //Begin CR7
	    masterJPanel.add(workStateJLabel,
	            new GridBagConstraints(2, 7, 1, 1, 0, 0,GridBagConstraints.CENTER, 
	            		GridBagConstraints.HORIZONTAL, new Insets(5, 100, 10, 15), 0, 0));
	    masterJPanel.add(workStateTextField,
	            new GridBagConstraints(3, 7, 1, 1, 0, 0,GridBagConstraints.CENTER, 
	            		GridBagConstraints.HORIZONTAL, new Insets(5, 40, 10, 15), 0, 0));
	    //End CR7
	    
	    masterJPanel.add(flowDrawingDisp, new GridBagConstraints(0, 8, 1, 1, 0.0,
	                                                0.0
	                                                , GridBagConstraints.EAST,
	                                                GridBagConstraints.NONE,
	                                                new Insets(5, 10, 5, 0), 0, 0));
	    masterJPanel.add(productStateJLabel,  new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
	    
	    masterJPanel.add(checkTechTypeLabel,  new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
	    
//	    masterJPanel.add(productStateSortingSelectedPanel,   new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0
//	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 16), 0, 0));
	    masterJPanel.add(productStateDisplJLabel,   new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
	    masterJPanel.add(checkTechTypeDisplJLabel,   new GridBagConstraints(1, 11, 3, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
	    
	    masterJPanel.add(remarkDisplayJLabel,
                new GridBagConstraints(1, 10, 1, 1, 1.0, 0.0//CR1
               ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 15), 0, 0));//CR3
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 1, 1, 1.0, 0.0//CR1
               ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));//CR3
        masterJPanel.add(remarkJLabel,
              new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
                                     ,
                                     GridBagConstraints.EAST,
                                     GridBagConstraints.
                                     NONE,
                                     new Insets(0, 0, 0, 0),
                                     0, 0));
	    
	    
	    //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        ResourcePanel  rsPanel = new ResourcePanel();
        Collection col = rsPanel.getCoding("QMTechnics", "productState", "SortType");
        if (col == null || col.size() == 0)
        {
            productStateComboBox.addItem("");
        }
        else
        {
        	productStateComboBox.addItem("");
            for (Iterator iter = col.iterator(); iter.hasNext();)
            {
                CodingIfc code = (CodingIfc) iter.next();
                //                CCBegin SS19
                if(getUserFromCompany().equals("zczx")){
                	if(!code.getCodeContent().toString().contains("变速箱"))
                		productStateComboBox.addItem(code);
                }
                //CCBegin SS25
                else if(getUserFromCompany().equals("ct")){
                	if(!code.getCodeContent().toString().contains("变速箱"))
                		productStateComboBox.addItem(code);
                }
                //CCEnd SS25
                else
//                	CCEnd SS19
                productStateComboBox.addItem(code);
            }
        }
        productStateComboBox.setMaximumSize(new Dimension(80, 22));
        productStateComboBox.setMinimumSize(new Dimension(80, 22));
        productStateComboBox.setPreferredSize(new Dimension(80, 22));
        masterJPanel.add(productStateComboBox,   new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0
    	        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
        
        
        
        Collection cTypecol = rsPanel.getCoding("QMTechnics", "checkTechType", "SortType");
        if (cTypecol == null || cTypecol.size() == 0)
        {
        	checkTechTypeComboBox.addItem("");
        }
        else
        {
        	checkTechTypeComboBox.addItem("");
            for (Iterator iter = cTypecol.iterator(); iter.hasNext();)
            {
                CodingIfc code = (CodingIfc) iter.next();
  
               
                checkTechTypeComboBox.addItem(code);
            }
            
        }
        checkTechTypeComboBox.setMaximumSize(new Dimension(80, 22));
        checkTechTypeComboBox.setMinimumSize(new Dimension(80, 22));
        checkTechTypeComboBox.setPreferredSize(new Dimension(80, 22));
        masterJPanel.add(checkTechTypeComboBox,   new GridBagConstraints(1, 11, 3, 1, 1.0, 0.0
    	        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
        
        //CCBegin SS28
        checkTechTypeComboBox.addItemListener(new ItemListener()
        {
			public void itemStateChanged(ItemEvent event) {
				 Date dt=new Date();
			     SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
			     String liushuiNum="";
			     try {
			    	 liushuiNum=getTechnicsAutoNum();
				} catch (QMException e) {
					e.printStackTrace();
				}
				 Object obj=event.getItem();
				 String technicsAutoNum="CD-JC-"+obj+"-"+remarkJTextField.getText()+"-"+matter1.format(dt)+"-"+liushuiNum;
				 numberJTextField.setText(technicsAutoNum);
//				 System.out.println("technicsAutoNum=======1111111111111========"+technicsAutoNum);
			}
        });
        //CCEnd SS28
        
		// Begin CR1
		masterJPanel.add(iterationJLabel, new GridBagConstraints(2, 6, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,//CR3
				new Insets(5, 100, 5, 8), 0, 0));
		masterJPanel.add(iterationTextField, new GridBagConstraints(3, 6, 1, 1,
				1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 40, 5, 8), 0, 0));
		
		masterJPanel.add(creatorJLabel, new GridBagConstraints(2, 0, 1, 1, 0,//CR3
				0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(creatorTextField, new GridBagConstraints(3, 0, 1, 1,//CR3
				0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));

		masterJPanel.add(creatTimeJLabel, new GridBagConstraints(2, 1, 1, 1,//CR3
				0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(creatTimeTextField, new GridBagConstraints(3, 1, 1,//CR3
				1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));

		masterJPanel.add(moderfilerJLabel, new GridBagConstraints(2, 4, 1, 1,//CR3
				0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(moderfilerTextField, new GridBagConstraints(3,4, 1,//CR3
				1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));

		masterJPanel.add(modifyTimeJLabel, new GridBagConstraints(2, 5, 1, 1,//CR3
				0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(modifyTimeTextField, new GridBagConstraints(3, 5, 1,//CR3
				1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));
        //End CR1
		//CCBegin SS6
	    if(ts16949){
	    	 if(yy){ 
	    		 
	    		
	    		 
	    	    masterJPanel.add(consmasterTS16949Panel,   new GridBagConstraints(0, 14, 4, 1, 1.0, 0.0
	      	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 4, 7), 0, 0));
       	     }else{
       	    	 
       	   //guoguo
// 	               masterJPanel.add(masterTS16949Panel,   new GridBagConstraints(0, 14, 4, 1, 1.0, 0.0
// 	   	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 4, 7), 0, 0));
       	    	 
       	    	 //guoguo
 	   	       }
             	
       	   
	    	 
	    	
	    }
	    //CCEnd SS6
	    //CCBegin SS12
	    //清理变量
	    hideMainPartBsoID.setText("");
	   // String techtype=technicsType.getCodeContent();
	    separableJPanel.setLayout(new GridBagLayout());
	    //CCBegin SS17
	    //separableNumberJLabel.setText("平台编号:");
	    //separableNameJLabel.setText("平台名称:");
	    //separableCountJLabel.setText("平台数量:");
	    separableNumberJLabel.setText("总成型号:");
	    separableNameJLabel.setText("总成名称:");
	    separableCountJLabel.setText("数量:");
	  //CCEnd SS17
	    selectPartJButton.setText("选择..");
	    separableNumberJLabel.setMaximumSize(new Dimension(30, 22));
	    separableNumberJLabel.setMinimumSize(new Dimension(30, 22));
	    separableNumberJLabel.setPreferredSize(new Dimension(30, 22));

	    separableNumber.setMaximumSize(new Dimension(140, 22));
	    separableNumber.setMinimumSize(new Dimension(140, 22));
	    separableNumber.setPreferredSize(new Dimension(140, 22));

	    separableNameJLabel.setMaximumSize(new Dimension(30, 22));
	    separableNameJLabel.setMinimumSize(new Dimension(30, 22));
	    separableNameJLabel.setPreferredSize(new Dimension(30, 22));

	    separableName.setMaximumSize(new Dimension(140, 22));
	    separableName.setMinimumSize(new Dimension(140, 22));
	    separableName.setPreferredSize(new Dimension(140, 22));

	    separableCountJLabel.setMaximumSize(new Dimension(30, 22));
	    separableCountJLabel.setMinimumSize(new Dimension(30, 22));
	    separableCountJLabel.setPreferredSize(new Dimension(30, 22));

	    separableCount.setMaximumSize(new Dimension(140, 22));
	    separableCount.setMinimumSize(new Dimension(140, 22));
	    separableCount.setPreferredSize(new Dimension(140, 22));
	    
	    
	    selectPartJButton.setMaximumSize(new Dimension(20, 22));
	    selectPartJButton.setMinimumSize(new Dimension(20, 22));
	    selectPartJButton.setPreferredSize(new Dimension(20, 22));
	    
	    selectPartJButton.addActionListener(new TechnicsMasterJPanel_productSelectButton_actionAdapter(this));
	    
	    separableJPanel.add(separableNumberJLabel,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    separableJPanel.add(separableNumber,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 2, 4), 0, 0));
	    separableJPanel.add(separableNameJLabel,   new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    separableJPanel.add(separableName,   new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 2, 4), 0, 0));
	    separableJPanel.add(separableCountJLabel,   new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    separableJPanel.add(separableCount,   new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 2, 4), 0, 0));
	    separableJPanel.add(selectPartJButton,   new GridBagConstraints(6, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    //CCEnd SS12
	    relationsJTabbedPane.add(doclinkJPanel, "文档");
	    //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
	    //guoguo
	    //relationsJTabbedPane.add(upFilePanel, "附件");
	    //guoguo
	    //CCEnd by liunan 2009-10-12
	        jpanel.setLayout(new BorderLayout());
	        jTabbedPane1.add(jpanel, "工艺主信息");
	    jpanel.add(scrollPane,  BorderLayout.CENTER);
	    scrollPane.getViewport().add(masterJPanel);
        jTabbedPane1.add(extendJPanel, "其他相关信息");

        buttonJPanel.add(saveJButton,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
        buttonJPanel.add(quitJButton,
                         new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(0, 8, 0, 0),
                                                0, 0));
        add(jTabbedPane1,
            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                   , GridBagConstraints.CENTER,
                                   GridBagConstraints.BOTH,
                                   new Insets(0, 2, 0, 0), 0, 0));

        lifeCycleInfo.setBsoName("QMFawTechnics");
        folderPanel.setButtonMnemonic('B');
        workshopSortingSelectedPanel.setSelectBMnemonic('W');
        //CCBegin add by lil in 20080903-----------begin
        numberJTextField.addTextListener(new java.awt.event.TextListener()
        {
        public void textValueChanged(TextEvent e)
        {
                   text_textValueChanged(e);
        }
        });
    }

    private void text_textValueChanged(TextEvent e)  
    {
    	//CCBegin by liunan 2010-11-17 发动机对“机加工艺”“特殊工艺”不做此操作。
      //源码
      //masterTS16949Panel.setAttribute(numberJTextField.getText());
      String type=technicsType.getCodeContent();
      //CCBegin SS6
      //CCBegin SS11
      if (!isBSXAutoNum(type)){
    	  try {
			if(getUserFromCompany().equals("zczx")){
				if(type.equals("轴齿机加工艺")||type.equals("轴齿热处理工艺")){
					//轴齿中心Fema等编号不根据工艺编号变化。所以不处理masterTS16949Panel页面
				}
			}
			//CCBegin SS23
			else if(getUserFromCompany().equals("ct")){
				masterTS16949Panel.setAttribute(numberJTextField.getText());
			}
			//CCEnd SS23
			else{
		         if(!type.equals("机加工艺")&&!type.equals("特殊工艺"))
		           {
		      	   masterTS16949Panel.setAttribute(numberJTextField.getText());
		           }
			}
		} catch (QMException e1) {
			e1.printStackTrace();
		}

      }
      //CCEnd SS11
      //CCEnd SS6
      //CCEnd by liunan 2010-11-17
    }
    //CCEnd add by lil in 20080903-----------end
    /**
     * 界面信息本地化
     */
    protected void localize()
    {
        initResources();
        try
        {
        	//CCBegin SS8
        	Boolean BSX = false; 	
          	try {
      	    	Class[] paraClass = {};
      	        Object[] objs = {};
      	        String className = "com.faw_qm.doc.util.DocServiceHelper";
      	        String methodName = "isBSXGroup";
      	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
      	        in.setClassName(className);
      	        in.setMethodName(methodName);
      	        in.setParaClasses(paraClass);
      	        in.setParaValues(objs);
      	        RequestServer server = null;
      	        server = RequestServerFactory.getRequestServer();	
      	        BSX = (Boolean) server.request(in);
      		} catch (QMRemoteException e) {
      			e.printStackTrace();
      		}
            if(BSX){
            	remarkJLabel.setText("PFEMA编号");
            }else{
                remarkJLabel.setText("适用车型");
            }
            //CCEnd SS8
            saveJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "QuitJButton", null));
            numberJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "technicsNumberJLabel", null));
            nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsNameJLabel", null));
            typeJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsTypeJLabel", null));

            workShopJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "workshopJLabel", null));
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
        add(buttonJPanel,
            new GridBagConstraints(0, 1,
                                   GridBagConstraints.REMAINDER,
                                   GridBagConstraints.REMAINDER,
                                   0.0, 0.0
                                   , GridBagConstraints.EAST,
                                   GridBagConstraints.NONE,
                                   new Insets(10, 0, 10, 5), 0,
                                   0));

    }
//CCBegin SS12
    /**
     * 添加产品按钮平台内部类
     */
    class TechnicsMasterJPanel_productSelectButton_actionAdapter implements java.awt.event.ActionListener
    {
    	TechnicsMasterJPanel adaptee;

    	TechnicsMasterJPanel_productSelectButton_actionAdapter(TechnicsMasterJPanel adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.productSelectButton_actionPerformed(e);
        }
    }
    /**
     * 按照主要零件添加产品平台
     * @param e ActionEvent
     */
    void productSelectButton_actionPerformed(ActionEvent e) {
		//CCBegin SS16
//    	if (mainPart == null || mainPart.equals("")) 
//			JOptionPane.showMessageDialog(this.getParentJFrame(),
//					"请先选择主要零件，再选择产品平台！");
//			setCursor(Cursor.getDefaultCursor());
//			return;
//		}
//		String mainPart = hideMainPartBsoID.getText();
//    	Class[] paraclass = {QMTechnicsIfc.class};
//    	Object[] paraobj = {getTechnics()};
//    	QMPartIfc c = null;
    	String mainPart = null;
//    	try {
//			c = (QMPartIfc) useServiceMethod("StandardCappService","getQMPart",paraclass,paraobj);
//		} catch (QMRemoteException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	try {
			Vector v = partLinkJPanel.getAllLinks();
	    	for(int i = 0;i<v.size();i++){
	    		PartUsageQMTechnicsLinkInfo p = (PartUsageQMTechnicsLinkInfo)v.get(i);
	    		if(p.getMajorpartMark())
	    		{
	    			mainPart = p.getLeftBsoID();
	    		}
	    	}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mainPart == null) {
			JOptionPane.showMessageDialog(this.getParentJFrame(),
					"请先选择主要零件，再选择产品平台！");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
//    	String mainPart = c.getBsoID();
    	//CCEnd SS16
		
		// //查询主要零件的被用于产品列表
		Vector partUsedByProductVec = new Vector();
		RequestServer server = RequestServerFactory.getRequestServer();
		
		//当前用户的配置规范
		PartConfigSpecIfc partConfigSpecIfc = null;
		StaticMethodRequestInfo info1 = new StaticMethodRequestInfo();
		info1.setClassName("com.faw_qm.part.util.PartServiceRequest");
		info1.setMethodName("findPartConfigSpecIfc");
		Class[] paraClass = { };
		info1.setParaClasses(paraClass);
		Object[] obj = { };
		info1.setParaValues(obj);
		try {
			partConfigSpecIfc = ((PartConfigSpecIfc) server.request(info1));
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		//获得PartInfo
		QMPartIfc partIfc = null;
		ServiceRequestInfo info2 = new ServiceRequestInfo();
		info2.setServiceName("PersistService");
		info2.setMethodName("refreshInfo");
		Class[] theClass = { String.class };
		Object[] obj2 = { mainPart };
		info2.setParaClasses(theClass);
		info2.setParaValues(obj2);
		try {
		 partIfc = (QMPartIfc) server.request(info2);
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		//判断获得参数是否正确
		if(partConfigSpecIfc==null){
			JOptionPane.showMessageDialog(this.getParentJFrame(),"用户配置规范初始化出错！");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		if(partIfc==null){
			JOptionPane.showMessageDialog(this.getParentJFrame(),"主要零件初始化出错！");
			setCursor(Cursor.getDefaultCursor());
			return;	
		}
		
		//获得当前零件的被用于产品
		StaticMethodRequestInfo info3 = new StaticMethodRequestInfo();
		info3.setClassName("com.faw_qm.part.util.PartServiceRequest");
		info3.setMethodName("setUsageList");
		Class[] paraClass3 = {QMPartIfc.class,PartConfigSpecIfc.class };
		info3.setParaClasses(paraClass3);
		Object[] obj3 = {partIfc, partConfigSpecIfc};
		info3.setParaValues(obj3);
		try {
			partUsedByProductVec = ((Vector) server.request(info3));
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		
		if(partUsedByProductVec==null||partUsedByProductVec.size()<=0){
			JOptionPane.showMessageDialog(this.getParentJFrame(),"主要零件不存在产品平台信息！");
			setCursor(Cursor.getDefaultCursor());
			return;	
		}
		ProductSelectJDialog partJDialog = new ProductSelectJDialog(this);
		partJDialog.setPartVec(partUsedByProductVec);
		partJDialog.setVisible(true);

    }
//CCEnd SS12

    /**
     * 设置在工艺树所选择的父节点
     * @param parent 父节点
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
    }
    /**
     * 根据CAPP.property 中配置判断是否属于变速箱自动编号
     * @param techType
     * @return
     *  CCBegin SS3
     */
    public boolean isBSXAutoNum(String techType)
    {
        String techAllType = RemoteProperty.getProperty("bsx_technics_autoNum");
        String[] typeVec = techAllType.split(",");
        for(int i=0;i<typeVec.length;i++)
        {
            if(typeVec[i].equals(techType))
                return true; 
        }
        return false;
    }
  //CCEnd SS3
    /**
     * 设置界面为创建模式
     */
    private void setCreateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setCreateMode() begin...");
        }
        try
        {
            QMFawTechnicsInfo info = new QMFawTechnicsInfo();
            setTechnics(info);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        clear();
        WorkThread wt = new WorkThread(5);
        wt.start();
        // refreshObject();
//      CCBeginby leixiao 2009-3-31 原因：解放升级工艺,编号按类型出
        String type=technicsType.getCodeContent();
        //CCBegin SS3
        //CCBegin SS23
        try{
        if(isBSXAutoNum(type))
        {
            numberJTextField.setText("自动编号");
            numberJTextField.setEditable(false);
        }
    	 else if(getUserFromCompany().equals("ct")){
    		 numberJTextField.setText((String)typeMap.get(type));
         	 numberJTextField.setEditable(true);
         }
        else
        {
        if (typeMap.get(type)!=null){
        	numberJTextField.setText((String)typeMap.get(type));
        	numberJTextField.setEditable(true);
        }
//      CCEndby leixiao 2009-3-31 原因：解放升级工艺,编号按类型出
        }
        }catch(QMException e){
        	e.printStackTrace();
        }
      //CCEnd SS23
      //CCEnd SS3
        numberJTextField.setVisible(true);
        nameJTextField.setVisible(true);
        remarkJTextField.setVisible(true);
        remarkDisplayJLabel.setVisible(false);
        numberDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        typeDisplayJLabel.setVisible(true);
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
        //然后双击第一层结构节点后展开第二层结构
        //只有一级菜单的选择添加项目，改为下拉列表方式添加
        workshopSortingSelectedPanel.setVisible(true);
        workShopDisplayjLabel.setVisible(false);
        //CCBegin SS27
        try
        {
	    	 if(getUserFromCompany().equals("ct")){
	    		 Class[] paraclass ={String.class,String.class};
	             Object[] paraobj ={"长特分公司","组织机构"};
	             CodingClassificationInfo coding = (CodingClassificationInfo) CappClientHelper.useServiceMethod(
	                     "CodingManageService", "findClassificationByName", paraclass, paraobj);
	        	if(coding!=null){
	        		workshopSortingSelectedPanel.setCoding(coding);
	        		workshopSortingSelectedPanel.setVisible(true);
	        		workshopSortingSelectedPanel.setEnabled(true);
	        	} 
	         }
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        //CCEnd SS27
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        productStateComboBox.setVisible(true);
//      CCBegin by liuzc 2009-12-23 原因：根据解放要求，工艺主信息产品状态默认为生产。
        productStateComboBox.setSelectedIndex(2);
//      CCEnd by liuzc 2009-12-23 原因：根据解放要求，工艺主信息产品状态默认为生产。
        productStateDisplJLabel.setVisible(false);
        checkTechTypeDisplJLabel.setVisible(false);
        folderPanel.setViewState(true);
        ////add by wangh on 20070514 设置资料夹文本框在创建模式下不可写.
        folderPanel.setTextFielEnable(false);
        //productStateDisplJLabel.setLocale();
        
        //Begin CR7
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(false);
        workStateTextField.setText(null);
        //End CR7
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumberJLabel.setVisible(true);
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        //CCBegin SS18
        separableNumber.setEditable(true);
        separableName.setEditable(true);
        separableCount.setEditable(true);
        selectPartJButton.setEnabled(true);
      //CCEnd SS18
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("轴齿机加工艺")||techtype.equals("轴齿热处理工艺"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
        
        //Begin CR3
        
        creatorJLabel.setVisible(false);
        creatorTextField.setVisible(false);
        creatTimeJLabel.setVisible(false);
        creatTimeTextField.setVisible(false);
        moderfilerJLabel.setVisible(false);
        moderfilerTextField.setVisible(false);
        modifyTimeJLabel.setVisible(false);
        modifyTimeTextField.setVisible(false);
        iterationJLabel.setVisible(false);
        iterationTextField.setVisible(false);

        masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0
	    				 ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
        
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));
        
        masterJPanel.add(numberJTextField,
                new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));
        //End CR3
        
        
         
        try
        {
            folderPanel.setLabelText(getPersionalFolder());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(getTechnics());
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
//      CCBeginby leixiao 2009-10-23 原因：
        String defaultlifecycle = RemoteProperty.getProperty(
				"默认的工艺生命周期", "工艺生命周期");
				//CCBegin by liunan 2010-11-17 发动机的机加和特殊工艺时采用“发动机工艺卡生命周期”
				if(type.equals("机加工艺")||type.equals("特殊工艺"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.fdjLifeCycle", "发动机工艺卡生命周期");
				}
				//CCEnd by liunan 2010-11-17
				//CCBegin SS15
				if(type.equals("轴齿热处理工艺")||type.equals("轴齿机加工艺"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.fdjLifeCycle", "轴齿中心工艺规程生命周期");
				}
				//CCEnd SS15
				//CCBegin SS13
				if(type.startsWith("变速箱"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.bsxLifeCycle", "变速箱(工艺)编校审批接生命周期");
				}
				//CCEnd SS13
					//CCBegin SS21
				if(type.startsWith("长特"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.ctLifeCycle", "长特工艺生命周期");
				}
				
				//CCEnd SS21
				//CCBegin SS24
				if(type.equals("整车装配工艺"))
				{
					defaultlifecycle = "整车装配工艺生命周期";
				}
				//CCEnd SS24
				
				
				if(type.startsWith("成都"))
				{
					defaultlifecycle = "成都工艺规程生命周期";
				}
				
				
				
        lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(defaultlifecycle);
      //CCBegin SS15
        //lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
        if(type.equals("轴齿热处理工艺")||type.equals("轴齿机加工艺"))
		{
        	lifeCycleInfo.getLifeCyclePanel().setEnabled(true);
		}
        else{
        	lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
        }
      //CCEnd SS15
//      CCEndby leixiao 2009-10-23 原因： 
        setButtonVisible(true);
        typeDisplayJLabel.setText(technicsType.getCodeContent());
        workshopSortingSelectedPanel.setDefaultCoding("部门",
                technicsType.getCodeContent());
//      CCBeginby leixiao 2010-4-2 原因：工艺部门可更改，工序部门取工艺部门
		//CCBegin by liunan 2012-03-15 根据解放 吴薇 要求 新增内饰车间 可选下级。
		//if(type.equals("机加工艺")||type.equals("特殊工艺"))
        //SSBegin SS9
//        if(type.equals("机加工艺")||type.equals("特殊工艺")||type.equals("内饰装配工艺"))
		if(type.equals("机加工艺")||type.equals("特殊工艺")||type.equals("内饰装配工艺")||type.contains("变速箱"))
//		SSEnd SS9
			//CCEnd by liunan 2012-03-15
		{
		workshopSortingSelectedPanel.setIsOnlyCC(false);	
		}
		else{
        workshopSortingSelectedPanel.setIsOnlyCC(true);
		}
//      CCEndby leixiao 2010-4-2 
        //20070202薛静add
		//CCBegin SS6
         if(ts16949){
        	 // CCBegin SS6
        	 if(isBSXAutoNum(type)){
        		  consmasterTS16949Panel.setCreateMode();
        	 }else{
             masterTS16949Panel.setCreateMode();
             }
         } 
         ////CCEnd SS6
         //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(false);
         //this.getUpFilePanel().getMultiList().clear();
         this.getUpFilePanel().setRow(0);
         //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.setCreateMode() end...return is void");
        }
    }


    /**
     * 设置界面为更新模式(只可以更新备注和各种关联)
     */
    private void setUpdateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setUpdateMode() begin...");
        }
        clear();
        //处理工艺种类
        setTechnicsType(getTechnics().getTechnicsType());
        WorkThread wt = new WorkThread(5);
        wt.start();
        //refreshObject();
        numberJTextField.setVisible(false);
        nameJTextField.setVisible(false);
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        this.productStateComboBox.setVisible(true);
        checkTechTypeComboBox.setVisible(true);
        productStateDisplJLabel.setVisible(false);
        checkTechTypeDisplJLabel.setVisible(false);
        //找到工艺卡下面的工序
//      CCBeginby leixiao 2010-4-2 原因：工艺部门可更改，工序部门取工艺部门
//        Class[] paraclass =
//                {
//                String.class, String.class};
//        Object[] paraobj =
//                {
//                getTechnics().getBsoID(),
//                getTechnics().getBsoID()};
//        Collection c = null;
//        try
//        {
//            c = (Collection) useServiceMethod("StandardCappService",
//                                              "browseProcedures",
//                                              paraclass,
//                                              paraobj);
//        }
//        catch (QMRemoteException ex)
//        {
//            ex.printStackTrace();
//            return;
//        }
        //没有工序，则可更新部门
//        if (c == null || c.size() == 0)
//        {
            workshopSortingSelectedPanel.setVisible(true);
            workshopSortingSelectedPanel.setCoding(getTechnics().getWorkShop());
//          CCBeginby leixiao 2010-4-2 原因：工艺部门可更改，工序部门取工艺部门
            //CCBegin by liunan 2012-03-15 根据解放 吴薇 要求 新增内饰车间 可选下级。
            //if(getTechnics().getTechnicsType().getCodeContent().equals("机加工艺")||getTechnics().getTechnicsType().getCodeContent().equals("特殊工艺"))
            //CCBegin SS14
            String ttype = getTechnics().getTechnicsType().getCodeContent();
            //if(getTechnics().getTechnicsType().getCodeContent().equals("机加工艺")||getTechnics().getTechnicsType().getCodeContent().equals("特殊工艺")||getTechnics().getTechnicsType().getCodeContent().equals("内饰装配工艺"))
            if(ttype.equals("机加工艺")||ttype.equals("特殊工艺")||ttype.equals("内饰装配工艺")||ttype.contains("变速箱"))
            //CCEnd SS14
            //CCEnd by liunan 2012-03-15
            {
            workshopSortingSelectedPanel.setIsOnlyCC(false);
            }
            else{
            workshopSortingSelectedPanel.setIsOnlyCC(true);// 工艺部门取到车间
            }
//          CCEndby leixiao 2010-4-2 原因：工艺部门可更改，工序部门取工艺部门
            workShopDisplayjLabel.setVisible(false);
//        }
        //否则不可更新部门
//        else
//        {
//        	
//            workshopSortingSelectedPanel.setVisible(false);
//            workShopDisplayjLabel.setVisible(true);
//            workShopDisplayjLabel.setText(getTechnics().
//                                          getWorkShop().toString());
//
//        }
//          CCEndby leixiao 2010-4-2 原因：工艺部门可更改，工序部门取工艺部门
        //Begin CR7
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(true);
        //CCBegin by liuzc 2009-12-19 原因：更新时没有设置状态，参见DefectID=2689
	    String workState;
		try {
	        WorkInProgressHelper wip = new WorkInProgressHelper();
			workState = wip.getStatus((WorkableIfc)getTechnics());
			workStateTextField.setText(workState);
		} catch (QMException e) {
			e.printStackTrace();
		}
		//CCEnd by liuzc 2009-12-19 原因：更新时没有设置状态，参见DefectID=2689
        //End CR7
        
        //Begin CR3
        
        creatorJLabel.setVisible(false);
        creatorTextField.setVisible(false);
        creatTimeJLabel.setVisible(false);
        creatTimeTextField.setVisible(false);
        moderfilerJLabel.setVisible(false);
        moderfilerTextField.setVisible(false);
        modifyTimeJLabel.setVisible(false);
        modifyTimeTextField.setVisible(false);
        iterationJLabel.setVisible(false);
        iterationTextField.setVisible(false);
        
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumber.setText(getTechnics().getSeparableNumber());
        separableNumber.setEditable(true);
        separableNumberJLabel.setVisible(true);
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableName.setText(getTechnics().getSeparableName());
        separableName.setEditable(true);
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCount.setText(getTechnics().getSeparableCount()); 
        separableCount.setEditable(true);
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        selectPartJButton.setEnabled(true);
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("轴齿机加工艺")||techtype.equals("轴齿热处理工艺"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
        masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0
	    				 ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
        
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));
        
        masterJPanel.add(numberJTextField,
                new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));
        //End CR3
        
        
        remarkJTextField.setVisible(true);
        numberDisplayJLabel.setVisible(true);
        nameDisplayJLabel.setVisible(true);
        typeDisplayJLabel.setVisible(true);
        remarkDisplayJLabel.setVisible(false);
        folderPanel.setViewState(true);
        //add by wangh on 20070514 设置资料夹文本框在更新时不可写.
        folderPanel.setTextFielEnable(false);
        folderPanel.getFolderPanelLabel();
        numberDisplayJLabel.setText(getTechnics().
                                    getTechnicsNumber());

        String name = getTechnics().getTechnicsName();
        nameDisplayJLabel.setText(name);

        typeDisplayJLabel.setText(technicsType.getCodeContent()); //代码内容
        workshopSortingSelectedPanel.setCoding(getTechnics().
                                               getWorkShop());
        String remark = getTechnics().getRemark();
        remarkJTextField.setText(remark);
        folderPanel.setLabelText(getTechnics().getLocation());

        lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
                LifeCycleManagedIfc) getTechnics());
        lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(getTechnics());
        setButtonVisible(true);
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        setComboBox(productStateComboBox, getTechnics().getProductState());
        setComboBox(checkTechTypeComboBox, getTechnics().getCheckTechType());
        
        //CCBegin SS6
        String type=technicsType.getCodeContent();
        if(ts16949)
        	 if(isBSXAutoNum(type)){
       		  consmasterTS16949Panel.setUpdateMode(getTechnics());
       	     }else{
             masterTS16949Panel.setUpdateMode(getTechnics());
       	     }
         //CCEnd SS6
         //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
         setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(true);
         //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.setUpdateMode() end...return is void");
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
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() begin...");
        }
        clear();
       /* Class[] paraclass=new Class[]{String.class};
        Object[] paraobj=new Object[]{getTechnics().getBsoID()};
        try
        {
           data=  (CappWrapData)useServiceMethod("StandardCappService", "getCappWrapData",
                             paraclass, paraobj);

        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }*/
        //处理工艺种类
        setTechnicsType(getTechnics().getTechnicsType());
        //modify by wangh on 20080255
//        WorkThread wt = new WorkThread(5);
//        wt.start();
         refreshObject();
//modify end
        numberJTextField.setVisible(false);
        nameJTextField.setVisible(false);
        workshopSortingSelectedPanel.setVisible(false);
        workShopDisplayjLabel.setVisible(true);
        remarkJTextField.setVisible(false);

        numberDisplayJLabel.setVisible(true);
        nameDisplayJLabel.setVisible(true);
        typeDisplayJLabel.setVisible(true);
        remarkDisplayJLabel.setVisible(true);
        folderPanel.setViewState(false);
        folderPanel.getFolderPanelLabel();
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        productStateComboBox.setVisible(false);
        checkTechTypeComboBox.setVisible(false);
        productStateDisplJLabel.setVisible(true);
        checkTechTypeDisplJLabel.setVisible(true);
        
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumber.setEditable(false);
        separableNumberJLabel.setVisible(true);
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableName.setEditable(false);
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCount.setEditable(false);
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        selectPartJButton.setEnabled(false);
        separableNumber.setText(getTechnics().getSeparableNumber());
        separableName.setText(getTechnics().getSeparableName());
        separableCount.setText(getTechnics().getSeparableCount()); 
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("轴齿机加工艺")||techtype.equals("轴齿热处理工艺"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
        
        //Begin CR7
        WorkInProgressHelper wip = new WorkInProgressHelper();
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(true);
	    String workState;
			try {
				workState = wip.getStatus((WorkableIfc)getTechnics());
				workStateTextField.setText(workState);
			} catch (QMException e) {
				e.printStackTrace();
			}
	    //End CR7


        
        // Begin CR1
		creatorJLabel.setVisible(true);
		creatorTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String creator = this.getCreator(getTechnics().getIterationCreator());
			creatorTextField.setText(creator);
		}

		creatTimeJLabel.setVisible(true);
		creatTimeTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String creattime = getTechnics().getCreateTime().toString();
			creatTimeTextField.setText(creattime);
		}

		moderfilerJLabel.setVisible(true);
		moderfilerTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String moderfiler = this.getCreator(getTechnics()
					.getIterationModifier());
			moderfilerTextField.setText(moderfiler);
		}

		modifyTimeJLabel.setVisible(true);
		modifyTimeTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String modifyTime = getTechnics().getModifyTime().toString();
			modifyTimeTextField.setText(modifyTime);
		}

		iterationJLabel.setVisible(true);
		iterationTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String iteration = getTechnics().getVersionValue();
			iterationTextField.setText(iteration);
		}
        //End CR1
        doclinkJPanel.setMode("View");
        doclinkJPanel.setTechnics(getTechnics());
        numberDisplayJLabel.setText(getTechnics().
                                    getTechnicsNumber());
        nameDisplayJLabel.setText(getTechnics().getTechnicsName());

        typeDisplayJLabel.setText(technicsType.getCodeContent());
        //CCBegin SS1
        if(getTechnics().getWorkShop()==null)
        {
        	workShopDisplayjLabel.setText("");
        }
        else
        {
          workShopDisplayjLabel.setText(getTechnics().
                                      getWorkShop().toString());
        }
        //CCEnd SS1
        remarkDisplayJLabel.setText(getTechnics().getRemark());
        folderPanel.setLabelText(getTechnics().getLocation());
        //20080807 xucy
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(getTechnics());
        lifeCycleInfo.getProjectPanel().setObject((
                LifeCycleManagedIfc) getTechnics());
        lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
        //20070202薛静add
        //CCBegin SS6
        String type=technicsType.getCodeContent();
        if(ts16949)
        	if(isBSXAutoNum(type)){
        		consmasterTS16949Panel.setViewMode( getTechnics());
        	}else{
        		masterTS16949Panel.setViewMode( getTechnics());
        	}
        //CCEnd SS6 
        if(getTechnics().getProductState()!=null)
        productStateDisplJLabel.setText(getTechnics().getProductState().getCodeContent());
        
        if(getTechnics().getCheckTechType()!=null)
        checkTechTypeDisplJLabel.setText(getTechnics().getCheckTechType().getCodeContent());
        
        
        setButtonVisible(false);
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
        this.getUpFilePanel().setAButtonVisable(false);
        this.getUpFilePanel().setDButtonVisable(false);
        this.getUpFilePanel().setVButtonVisable(false);
        this.getUpFilePanel().setDLButtonVisable(true);
        //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() end...return is void");
        }
    }


    /**
     * 设置界面为“另存为“模式(只有工艺种类不可修改)
     */
	private void setSaveAsMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setSaveAsMode() begin...");
        }
        clear();
        existTechnicsInfo = getTechnics();
        numberJTextField.setVisible(true);
        nameJTextField.setVisible(true);
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        productStateComboBox.setVisible(true);
        productStateDisplJLabel.setVisible(false);
        checkTechTypeComboBox.setVisible(true);
        checkTechTypeDisplJLabel.setVisible(false);
       
        //找到工艺卡下面的工序
//		CCBeginby leixiao 2010-04-01
//        Class[] paraclass =
//                {
//                String.class, String.class};
//        Object[] paraobj =
//                {
//                getTechnics().getBsoID(),
//                getTechnics().getBsoID()};
//        Collection c = null;
//        try
//        {
//            c = (Collection) useServiceMethod("StandardCappService",
//                                              "browseProcedures",
//                                              paraclass,
//                                              paraobj);
//        }
//        catch (QMRemoteException ex)
//        {
//            ex.printStackTrace();
//            return;
//        }
//        //没有工序，则可更新部门
//        if (c == null || c.size() == 0)
//        {
        //CCBegin SS5
        String type=technicsType.getCodeContent();
        if(isBSXAutoNum(type)){
			 //找到工艺卡下面的工序
	        Class[] paraclass =
	                {
	                String.class, String.class};
	        Object[] paraobj =
	                {
	                getTechnics().getBsoID(),
	                getTechnics().getBsoID()};
	        Collection c = null;
	        try
	        {
	            c = (Collection) useServiceMethod("StandardCappService",
	                                              "browseProcedures",
	                                              paraclass,
	                                              paraobj);
	        }
	        catch (QMRemoteException ex)
	        {
	            ex.printStackTrace();
	            return;
	        }
	        //没有工序，则可更新部门
	        if (c == null || c.size() == 0)
	        {
	            workshopSortingSelectedPanel.setVisible(true);
	            workShopDisplayjLabel.setVisible(false);
	            workshopSortingSelectedPanel.setCoding(getTechnics().
	                    getWorkShop());
	        }
	        //否则不可更新部门
	        else
	        {
	            workshopSortingSelectedPanel.setVisible(false);
	            workShopDisplayjLabel.setVisible(true);
	            workShopDisplayjLabel.setText(getTechnics().
	                                          getWorkShop().toString());
	        }
	       
	        lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
	                LifeCycleManagedIfc) getTechnics());
	      
	        numberJTextField.setVisible(false); 
	        numberJTextField.setText(getTechnics().getTechnicsNumber());
		   }else{
        	workShopDisplayjLabel.setVisible(false);
            workshopSortingSelectedPanel.setVisible(true);
            workshopSortingSelectedPanel.setCoding(getTechnics().
                        getWorkShop());
            //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
            setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
            this.getUpFilePanel().setAButtonVisable(true);
            this.getUpFilePanel().setDButtonVisable(true);
            this.getUpFilePanel().setVButtonVisable(false);
            this.getUpFilePanel().setDLButtonVisable(true);
            numberJTextField.setText(getTechnics().getTechnicsNumber());
            //CCEnd by liunan 2009-10-12
		}
        //CCEnd SS5
//        }
//        //否则不可更新部门
//        else
//        {
//        	workshopSortingSelectedPanel.setVisible(false);
//            workShopDisplayjLabel.setVisible(true);
//            workShopDisplayjLabel.setText(getTechnics().
//                                          getWorkShop().toString());
//        }
//    		CCEndby leixiao 2010-04-01
        remarkJTextField.setVisible(true);
        numberDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        typeDisplayJLabel.setVisible(true);
        remarkDisplayJLabel.setVisible(false);
        folderPanel.setViewState(true);
//		CCBeginby liuzhicheng 2010-02-08 原因：生命周期另存为状态时不可编辑。        
//        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
		//      lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
		//      LifeCycleManagedIfc) getTechnics());
		//CCBeginby leixiao 2009-10-23 原因：
//		String defaultlifecycle = RemoteProperty.getProperty(
//				"默认的工艺生命周期", "工艺生命周期");
//		lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(defaultlifecycle);
//		lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
		//CCEndby leixiao 2009-10-23 原因：
//		CCEndby liuzhicheng 2010-02-08
 

        String name = getTechnics().getTechnicsName();
        nameJTextField.setText(name);
        String remark = getTechnics().getRemark();
        remarkJTextField.setText(remark);
        
        //Begin CR3
        
        //Begin CR7
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(true);
        //End CR7
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumberJLabel.setVisible(true);
        separableNumber.setEditable(true);
        separableNumber.setText(getTechnics().getSeparableNumber());
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableName.setEditable(true);
        separableName.setText(getTechnics().getSeparableName());
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCount.setEditable(true);
        separableCount.setText(getTechnics().getSeparableCount());
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        selectPartJButton.setEnabled(true);
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("轴齿机加工艺")||techtype.equals("轴齿热处理工艺"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12  
        creatorJLabel.setVisible(false);
        creatorTextField.setVisible(false);
        creatTimeJLabel.setVisible(false);
        creatTimeTextField.setVisible(false);
        moderfilerJLabel.setVisible(false);
        moderfilerTextField.setVisible(false);
        modifyTimeJLabel.setVisible(false);
        modifyTimeTextField.setVisible(false);
        iterationJLabel.setVisible(false);
        iterationTextField.setVisible(false);

        masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0
	    				 ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
        
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));
        
        masterJPanel.add(numberJTextField,
                new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));
        //End CR3
        

        //处理工艺种类
        setTechnicsType(getTechnics().getTechnicsType());
        typeDisplayJLabel.setText(technicsType.getCodeContent());

        try
        {
            folderPanel.setLabelText(getPersionalFolder());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(getTechnics());
        refreshObject();
//		CCBeginby liuzhicheng 2010-02-08 原因：生命周期另存为状态时不可编辑。        
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);  
		String defaultlifecycle = RemoteProperty.getProperty(
				"默认的工艺生命周期", "工艺生命周期");
				
				//CCBegin SS26
				if(isBSXAutoNum(type))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.bsxLifeCycle", "变速箱(工艺)编校审批接生命周期");
				}
				//CCEnd SS26
				
		lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(defaultlifecycle);
		lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
//		CCEndby liuzhicheng 2010-02-08 
        setButtonVisible(true);
        //20060202薛静add
        //CCBegin SS6
         if(ts16949)
        	 if(isBSXAutoNum(type)){
       		  consmasterTS16949Panel.setSaveAsMode(getTechnics());
       	     }else{
             masterTS16949Panel.setSaveAsMode(getTechnics());
       	     }
         //CCEnd SS6
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
 	    //然后双击第一层结构节点后展开第二层结构
 	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        setComboBox(productStateComboBox, getTechnics().getProductState());
        setComboBox(checkTechTypeComboBox, getTechnics().getCheckTechType());
        
//        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
//        setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
//        this.getUpFilePanel().setAButtonVisable(true);
//        this.getUpFilePanel().setDButtonVisable(true);
//        this.getUpFilePanel().setVButtonVisable(false);
//        this.getUpFilePanel().setDLButtonVisable(true);
//        numberJTextField.setText(getTechnics().getTechnicsNumber());
//        //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.setSaveAsMode() end...return is void");
        }
    }


    /**
     * 设置当前选择的工艺卡主信息显示在界面上
     * @param info 工艺卡主信息
     */
    public void setTechnics(QMFawTechnicsInfo info)
    {
        technicsInfo = info;
    }


    /**
     * 获得工艺卡主信息
     * @return 工艺卡主信息
     */
    public QMFawTechnicsInfo getTechnics()
    {
        return technicsInfo;
    }


    /**
     * 设置界面模式（创建、更新或查看）。
     * @param aMode 新界面模式
     * @exception java.beans.PropertyVetoException 如果模式Mode无效，则抛出异常。
     */
    public void setViewMode(int aMode)
            throws PropertyVetoException
    {
    	
    	
    	//CCBegin SS11
    	hideMainPart.setText("");//初始化时，清理变量
    	//CCEnd SS11
    	//CCBegin SS12
    	hideMainPartBsoID.setText("");//初始化时，清理变量
    	//CCEnd SS12	
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() begin...");
        }
        jTabbedPane1.setSelectedIndex(0);
        if ((aMode == UPDATE_MODE) ||
            (aMode == CREATE_MODE) ||
            (aMode == VIEW_MODE) ||
            (aMode == SAVEAS_MODE))
        {
            mode = aMode;
        }
        else
        {
            //信息：无效模式
            throw (new PropertyVetoException(QMMessage.
                                             getLocalizedMessage(
                    RESOURCE,
                    "invalid Mode", null),
                                             new PropertyChangeEvent(this,
                    "mode",
                    new Integer(getViewMode()),
                    new Integer(aMode))));
        }

        switch (aMode)
        {

            case CREATE_MODE:
            { //创建模式
                setCreateMode();
                break;
            }

            case UPDATE_MODE:
            { //更新模式
                setUpdateMode();
                break;
            }

            case VIEW_MODE:
            { //查看模式
                setViewMode();
                break;
            }

            case SAVEAS_MODE:
            { //另存为模式
                setSaveAsMode();
                break;
            }

        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() end...return is void");
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

    void saveJButton_actionPerformed(ActionEvent e)
    {
        WorkThread thread = new WorkThread(6);
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
        else if (getViewMode() == SAVEAS_MODE)
        {
            saveWhenSaveAs();
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();

    }


    /**
     * 设置工艺卡的所有属性和关联，并获得信息封装对象。
     * @return  信息封装对象
     */
    private CappWrapData commitAllAttributes()
    {
        //设置工艺卡属性(编号、名称、类型、备注、资料夹)
        if (getViewMode() == CREATE_MODE ||
            getViewMode() == SAVEAS_MODE)
        {
            technicsInfo.setTechnicsNumber(numberJTextField.
                                           getText().trim());
            technicsInfo.setTechnicsName(nameJTextField.getText().
                                         trim());
        }
        //设置资料夹
        technicsInfo.setLocation(folderPanel.getFolderLocation());
        //CCBegin SS12
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("轴齿机加工艺")||techtype.equals("轴齿热处理工艺"))){
		        if(separableName.getText()!=null){
		            technicsInfo.setSeparableName(separableName.getText());
		        }
		        if(separableNumber.getText()!=null){
		            technicsInfo.setSeparableNumber(separableNumber.getText());	
		        }
		        if(separableCount.getText()!=null){
		            technicsInfo.setSeparableCount(separableCount.getText());  	
		        }
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
		
        //设置工艺种类
        //CCBegin SS5
        //if (getViewMode() == CREATE_MODE)
        if (getViewMode() == CREATE_MODE ||
                getViewMode() == SAVEAS_MODE)
        {
            technicsInfo.setTechnicsType(technicsType);
        }
        //CCEnd SS5
        //设置生命周期和项目组
        if (getViewMode() == CREATE_MODE ||
            getViewMode() == SAVEAS_MODE)
        {
            try
            {
                LifeCycleManagedIfc lcm = lifeCycleInfo.assign((
                        LifeCycleManagedIfc) getTechnics());
                setTechnics((QMFawTechnicsInfo) lcm);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
               return null;
            }
        }        
        if (workshopSortingSelectedPanel.isVisible())
        {
            technicsInfo.setWorkShop((BaseValueInfo)
                                     workshopSortingSelectedPanel.
                                     getCoding());
        }
        if (remarkJTextField.getText() != null &&
            remarkJTextField.getText() != "")
        {
            technicsInfo.setRemark(remarkJTextField.getText());
        }
        //ll modify in 20071101 begin
        ArrayList drawingList =null;
        //制件流程图
        if (drawingpanel != null && drawingpanel.isVisible())
        {
            //technicsInfo.setFlowDrawing(drawingpanel.getDrawingDate());
            if(drawingpanel.getDrawingDate()!=null){
                drawingList = new ArrayList(2);
                PDrawingInfo pdrawing = new PDrawingInfo();
                pdrawing.setDrawingByte((byte[])drawingpanel.getDrawingDate().elementAt(0));//CR2
                pdrawing.setDrawingType((String)drawingpanel.getDrawingDate().elementAt(1));//CR6
                drawingList.add(pdrawing);
            }
        }
        //ll modify in 20071101 end
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        if(!productStateComboBox.getSelectedItem().equals(""))
        {
        	technicsInfo.setProductState((CodingIfc)productStateComboBox.getSelectedItem());
        }
        if(!checkTechTypeComboBox.getSelectedItem().equals(""))
        {
        	technicsInfo.setCheckTechType((CodingIfc)checkTechTypeComboBox.getSelectedItem());
        }
        
        
        //20070202薛静add
        //CCBegin SS6
        String type=technicsType.getCodeContent();
         if(ts16949)
         {
        	 if(isBSXAutoNum(type)){
        	    if (consmasterTS16949Panel.check())
                {
                    consmasterTS16949Panel.commitAttributes(technicsInfo);
                }
                 else
                {
                    isSave = false;
                    return null;
                }
         }
        	 else{
        		 if (masterTS16949Panel.check())
                 {
                     masterTS16949Panel.commitAttributes(technicsInfo);
                 }
                 else
                 {
                     isSave = false;
                     return null;
                 }
        	 }
         }
         //CCEnd SS6
        if (cappExAttrPanel.check())
        {
            //设置扩展属性
            technicsInfo.setExtendAttributes(cappExAttrPanel.
                                             getExAttr());
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
        Vector docLinks = new Vector();
        Vector partLinks = new Vector();
        Vector materialLinks = new Vector();
        //20080619 xucy add
        Vector docMasterLinks = new Vector();
        //获得所有添加和更新的关联
        try
        {
        	
            docLinks = doclinkJPanel.getAllLinks();
            int size = docLinks.size();
            for (int j = 0; j < size; j++)
            {
                String docId = ((QMTechnicsQMDocumentLinkInfo) docLinks.
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
                        QMTechnicsQMDocumentLinkInfo linkInfo = new
                        QMTechnicsQMDocumentLinkInfo();
                        linkInfo.setRightBsoID(masterID);
                        linkInfo.setLeftBsoID(((
                        		QMTechnicsQMDocumentLinkInfo)
                                               docLinks.elementAt(j)).
                                              getLeftBsoID());
                        docMasterLinks.add(linkInfo);
                    }
                    else
                    if (docInfo instanceof DocMasterInfo)
                    {
                        docMasterLinks.add((QMTechnicsQMDocumentLinkInfo)
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

            partLinks = partLinkJPanel.getAllLinks();
            if (verbose)
            {
                System.out.println(
                        " partLinks.size() = " +
                        partLinks.size());
            }
            materialLinks = materialLinkJPanel.getAllLinks();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            isSave = false;
            return null;
        }
        //另存为模式,去掉持久化信息
        if (mode == SAVEAS_MODE)
        {
            QMTechnicsQMDocumentLinkInfo dlink;
            for (int i = 0; i < docLinks.size(); i++)
            {
                dlink = (
                        QMTechnicsQMDocumentLinkInfo) docLinks.elementAt(i);
                dlink.setBsoID(null);
            }
            QMTechnicsQMMaterialLinkInfo mlink;
            for (int i = 0; i < materialLinks.size(); i++)
            {
                mlink = (
                        QMTechnicsQMMaterialLinkInfo) materialLinks.
                        elementAt(i);
                mlink.setBsoID(null);
            }
            PartUsageQMTechnicsLinkInfo plink;
            for (int i = 0; i < partLinks.size(); i++)
            {
                plink = (
                        PartUsageQMTechnicsLinkInfo) partLinks.elementAt(i);
                plink.setBsoID(null);
            }
        }
        //将文档关联和材料关联合并
        Vector resourceLinks = new Vector();
        for (int i = 0; i < docMasterLinks.size(); i++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(i));
        }
        for (int j = 0; j < materialLinks.size(); j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }

        //封装所有信息
        CappWrapData cappWrapData = new CappWrapData();
        cappWrapData.setQMTehnicsIfc(technicsInfo);
        cappWrapData.setPartUsageQMTechnics(partLinks);
        cappWrapData.setQMTechnicsUsageResource(resourceLinks);
        //ll add in 20071101 begin
        cappWrapData.setUpdateDrawing(drawingList);
         //ll add in 20071101 end
        return cappWrapData;
    }
  //CCBegin SS4
    /**
     * 获得关联零部件
     * @return
     * 
     * PartUsageTechLinkJPanel
     */
   public PartUsageTechLinkJPanel getPartPanel()
   {
       return partLinkJPanel;
   }
 //CCEnd SS4
   //CCBegin SS22
   
   /**
    * 获得关联材料
    * @return
    * 
    * PartUsageTechLinkJPanel
    */
  public TechUsageMaterialLinkJPanel  getMaterialPanel()
  {
      return materialLinkJPanel;
  }
   
   //CCEnd SS22
    /**
     * 创建工艺卡时，保存新建的工艺卡
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //设置工艺卡的所有属性和关联，并获得信息封装对象。
        CappWrapData cappWrapData = commitAllAttributes();

        /*ProgressService.setProgressText(QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.SAVING, null));
                 ProgressService.showProgress();*/

        if (cappWrapData == null)
        {
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        //获得附件信息
        ArrayList arrayList = getArrayList();        
        //显示保存进度
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        //原保存方法，改成添加附件的保存方法。
        /*Class[] paraClass =
                {
                CappWrapData.class};
        Object[] obj =
                {
                cappWrapData};*/
        Class[] paraClass ={CappWrapData.class, ArrayList.class};
        Object[] obj ={cappWrapData, arrayList};
        //CCEnd by liunan 2009-10-12

            QMFawTechnicsInfo returnTechnics = null;

            try
            {
                returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                        "StandardCappService", "createQMTechnics", paraClass,
                        obj);

            }
            catch (QMRemoteException ex)
            {
            	
            	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();//CR4
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                //CCBegin by liuzc 2009-11-29 原因：解放系统升级为v4r3。
                ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
                //CCEnd by liuzc 2009-11-29 原因：解放系统升级为v4r3。
                //ProgressService.hideProgress();
                setCursor(Cursor.getDefaultCursor());
                setButtonWhenSave(true);
                isSave = false;    
                return;
            }
       ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        TechnicsTreeObject treeObject = new TechnicsTreeObject(
                returnTechnics);
        //把treeObject挂到工艺树上
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().addProcess(treeObject);
        //隐藏进度条
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        //隐藏保存和取消按钮
        setVisible(false);
        //设置焦点
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().setNodeSelected(treeObject);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.saveWhenCreate() end...return is true");
        }
    }


    /**
     * 更新工艺卡时，保存更新后的工艺卡
     * 不可更新资料夹，但可通过更改存放位置，改变工艺规程存放的资料夹
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.saveWhenUpdate() begin...");

        }
        setButtonWhenSave(false);
        //检查必填区域是否已填
        boolean requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
//      CCBeginby leixiao 2010-4-2 原因：工艺部门可修改,修改工艺部门时修改其工序的部门
        boolean changworkshop = checkChangeWorkshop(); 
//      CCEndby leixiao 2010-4-2 原因：工艺部门可更改
        //设置工艺卡的所有属性和关联，并获得信息封装对象。
        CappWrapData cappWrapData = commitAllAttributes();
        if (cappWrapData == null)
        {
            setButtonWhenSave(true);
            return;
        }
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        ArrayList arrayList = getArrayListupdate();
        Collection deleteContentCol = (Collection)getUpFilePanel().
		                            getDeleteContentMap().values();
		    Collection vec = new Vector(deleteContentCol);
		    getUpFilePanel().getDeleteContentMap().clear();  
			    
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //显示保存进度
        /* ProgressService.setProgressText(QMMessage.getLocalizedMessage(
                 RESOURCE, CappLMRB.SAVING, null));
         ProgressService.showProgress();
         */
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        //原工艺保存方法，改成保存附件的方法。
        /*Class[] paraClass =
                {
                CappWrapData.class};
        Object[] obj =
                {
                cappWrapData};*/
//      CCBeginby leixiao 2010-4-2 原因：工艺部门可修改,修改工艺部门时修改其工序的部门
        Class[] paraClass ={CappWrapData.class, ArrayList.class, Vector.class,boolean.class};
        Object[] obj ={cappWrapData, arrayList, vec,new Boolean(changworkshop)};
//      CCEndby leixiao 2010-4-2 原因：工艺部门可修改,修改工艺部门时修改其工序的部门
        //CCEnd by liunan 2009-10-12
        QMFawTechnicsInfo returnTechnics;
        try
        {
            returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                    "StandardCappService", "updateQMTechnics", paraClass,
                    obj);
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
            isSave = false;
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            return;
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        TechnicsTreeObject treeObject = new TechnicsTreeObject(
                returnTechnics);
        //把treeObject挂到工艺树上,刷新工艺树
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().updateNode(
                treeObject);

        //隐藏进度条
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);

        technicsInfo = returnTechnics;
        try
        {
            setViewMode(VIEW_MODE);
        }
        catch (PropertyVetoException ex1)
        {
            ex1.printStackTrace();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.saveWhenUpdate() end...return is true");

        }
    }

//  CCBeginby leixiao 2010-4-2 原因：工艺部门可修改,修改工艺部门时修改其工序的部门
    private boolean checkChangeWorkshop() {
    	boolean changworkshop=false;
        if (workshopSortingSelectedPanel.isVisible())
        {
        	if(technicsInfo.getWorkShop()!=(BaseValueInfo)workshopSortingSelectedPanel.getCoding()){
        		System.out.println("------单位有变动");
            changworkshop=true;
        	}
        }
		return changworkshop;
	}
//  CCEndby leixiao 2010-4-2 原因：工艺部门可修改,修改工艺部门时修改其工序的部门



	/**
     * 工艺卡另存为
     */
    private void saveWhenSaveAs()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.saveWhenSaveAs() begin...");

        }
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //另存为后的工艺卡
        try
        {
            QMFawTechnicsInfo saveasInfo = new QMFawTechnicsInfo();
            setTechnics(saveasInfo);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            return;
        }
        //设工艺种类
        getTechnics().setTechnicsType(existTechnicsInfo.
                                      getTechnicsType());
        if (!workshopSortingSelectedPanel.isVisible())
        {
            getTechnics().setWorkShop(existTechnicsInfo.
                                          getWorkShop());
        }
        //设置工艺卡的所有属性和关联，并获得信息封装对象。
        CappWrapData cappWrapData = commitAllAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //显示保存进度
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        //获得新加附件的ApplicationDataInfo集合。
        ArrayList arrayList = getArrayListupdate();
        //获得原工艺所拥有的附件ApplicationDataInfo集合。
		    Vector vec = getVectorSaveAs();
		    getUpFilePanel().getDeleteContentMap().clear();
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        //调用服务，保存工艺卡
        //原工艺保存方法，改成保存附件的方法。
        /*Class[] paraClass =
                {
                CappWrapData.class, QMTechnicsIfc.class};
        Object[] obj =
                {
                cappWrapData, existTechnicsInfo};*/
        Class[] paraClass ={CappWrapData.class, QMTechnicsIfc.class, ArrayList.class, Vector.class};
        Object[] obj ={cappWrapData, existTechnicsInfo, arrayList, vec};
        //CCEnd by liunan 2009-10-12
        QMFawTechnicsInfo returnTechnics=null;
        try
        {
           // for(int i=381;i<400;i++)
           // {
              //  cappWrapData.getQMTechnicsIfc().setTechnicsNumber(technicsType.getCodeContent()+i);
            returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                    "StandardCappService", "saveAsTechnics", paraClass,
                    obj);
          // }
        }
        catch (QMRemoteException ex)
        {
            if(verbose)
            ex.printStackTrace();
             ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            setTechnics((QMFawTechnicsInfo)existTechnicsInfo);
            //   ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            return;
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        //刷新旧节点
        //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
        //      existTechnicsInfo, null);
        //挂新节点
        TechnicsTreeObject treeObject = new TechnicsTreeObject(
                returnTechnics);
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().addProcess(treeObject);
        //隐藏进度条
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        setVisible(false);
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().setNodeSelected(treeObject);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.saveWhenSaveAs() end...return is void");
        }
    }


    /**
     * 取消按钮的动作事件方法
     * <p>将用户录入的信息删除</p>
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
        else if (getViewMode() == SAVEAS_MODE)
        {
            cancelWhenSaveAs();
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
                    "cappclients.capp.view.TechnicsMasterJPanel.CancelWhenCreate() begin...");
        }
        numberJTextField.setText("");
        nameJTextField.setText("");
        remarkJTextField.setText("");
        //folderPanel.setTextFieldNull();//CR5
        //20060814薛静修改，将文档，零件，材料关联清除
        doclinkJPanel.clear();
        partLinkJPanel.clear();
        materialLinkJPanel.clear();
        //20070202薛静add
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        this.productStateComboBox.setSelectedIndex(0);
        this.checkTechTypeComboBox.setSelectedIndex(0);
        
        //CCBegin SS6
        String type=technicsType.getCodeContent();
         if(ts16949){
        	 if(isBSXAutoNum(type)){
                 this.consmasterTS16949Panel.clear();
        	 }else{
        		 this.masterTS16949Panel.clear();
        	 }
         }
         //CCEnd SS6
        workshopSortingSelectedPanel.setCoding(null);//begin CR5
        lifeCycleInfo.clear();
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(null);
        lifeCycleInfo.getProjectPanel().setObject(null);
        try
		{
			folderPanel.setLabelText(getPersionalFolder());
		}
		catch (QMRemoteException e)
		{
			e.printStackTrace();
		}//end CR5
        
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.CancelWhenCreate() end...return is void");
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
     * 另存为模式下，取消按钮的执行方法
     * <p>将本次录入的数据撤消，返回上次录入的数据</p>
     */
    private void cancelWhenSaveAs()
    {
        setSaveAsMode();
    }


    /**
     * 退出按钮的动作监听事件方法
     * @param e
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


    /**
     * 退出
     * @return boolean 是否执行了退出
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
        else if (getViewMode() == SAVEAS_MODE)
        {
            quitWhenSaveAs();
        }
        else if (getViewMode() == VIEW_MODE)
        {
            quitWhenView();
        }
        return isSave;
    }


    /**
     * 创建模式下，退出按钮的执行方法
     */
    private void quitWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.QuitWhenCreate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.IS_SAVE_QMTECHNICS, null);
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
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.QuitWhenCreate() end...return is void");

        }
    }


    /**
     * 更新模式下，退出按钮的执行方法
     */
    private void quitWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.QuitWhenUpdate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 IS_SAVE_QMTECHNICS_UPDATE, null);
        if (confirmAction(s))
        {
            saveWhenUpdate();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.QuitWhenUpdate() end...return is void");
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
     * 另存为模式下，退出按钮的执行方法
     */
    private void quitWhenSaveAs()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.QuitWhenSaveAs() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.IS_SAVE_QMTECHNICS, null);
        if (confirmAction(s))
        {
            saveWhenSaveAs();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.QuitWhenSaveAs() end...return is void");

        }
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
     * 设置按钮的显示状态（有效或失效）
     * @param flag  flag为True，按钮有效；否则按钮失效
     */
    private void setButtonWhenSave(boolean flag)
    {
        quitJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        //this.getUpFilePanel().setVButtonVisable(flag);
        this.getUpFilePanel().setDLButtonVisable(flag);
        //CCEnd by liunan 2009-10-12

    }


    /**
     * 设置按钮的可见性
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
    }


    /**
     * 检验必填区域是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
     */
    private boolean checkRequiredFields()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.checkRequiredFields() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        if ((getViewMode() == CREATE_MODE) ||
            getViewMode() == SAVEAS_MODE)
        {
//          CCBeginby leixiao 2009-7-30 原因：去掉对编号名称特殊字符的限制
          //CCBegin by liuzc 2009-12-19 原因：工艺编号不能为空，参见DefectID=2658
          isOK = numberJTextField.check();
          if (isOK)
          {
              isOK = nameJTextField.check();

          }
//        CCEndby leixiao 2009-7-30 原因：去掉对编号特殊字符的限制
        //CCBegin by liunan 2009-09-17 还是需要对名称是否为空进行检验。
        if(nameJTextField.getText().trim().equals(""))
        {
          isOK = false;
          message="工艺名称不能为空！";
          return false;
        }
//        else
//      	  isOK=true;
        //CCEnd by liuzc 2009-12-19 原因：工艺编号不能为空，参见DefectID=2658
      	//CCEnd by liunan 2009-09-17

        }
        if (isOK)
        {
            isOK = remarkJTextField.check();
        }

        //检验部门是否为空
        //CCBegin SS2
        //if (isOK && workshopSortingSelectedPanel.isShowing() &&
//        if (isOK &&
        //CCBegin SS7
//        //CCEnd SS2
//            workshopSortingSelectedPanel.getCoding() == null)
     
        if (isOK && workshopSortingSelectedPanel.isShowing() &&
                workshopSortingSelectedPanel.getCoding() == null)
       //CCEnd SS7
        {
            message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.NO_WORKSHOP_ENTERED,
                    null);
            isOK = false;
            workshopSortingSelectedPanel.getJButton().
                    grabFocus();
        }

        //检验资料夹是否为空
        else if (isOK && !checkFolderLocation())
        {
            message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.NO_LOCATION_ENTERED,
                    null);
            isOK = false;
            folderPanel.grabFocus();
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
                                          JOptionPane.
                                          INFORMATION_MESSAGE);
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.checkRequiredFields() end...return: " +
                               isOK);
        }
        return isOK;

    }
    

    /**
     * 检验是否已指定资料夹
     * @return 如果已指定资料夹路径，则返回资料夹。
     * @throws QMRemoteException
     */
    private boolean checkFolderLocation()

    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.checkFolderLocation() begin...");
        }
        String location = "";
        //获得资料夹路径
        location = folderPanel.getFolderLocation();
        if (location == null || location.length() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }


    /**
     * 把给定的业务对象添加到相应的关联列表中
     * @param info 给定的业务对象（设备、工装、材料、简图、术语等）
     */
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (info[0] instanceof QMMaterialInfo)
        {
            materialLinkJPanel.addMaterialToTable((
                    QMMaterialInfo) info[0]);
        }
        else if (info[0] instanceof QMTermInfo)
        {
            String termName = ((QMTermInfo) info[0]).getTermName();
            nameJTextField.setInsertText(termName);
            remarkJTextField.setInsertText(termName);
        }
        else if (info[0] instanceof QMPartInfo)
        {
            for (int i = 0; i < info.length; i++)
            {
                partLinkJPanel.addPartToTable((QMPartInfo) info[i]);
            }
        }
    }


    /**
     * 获得当前用户的个人资料夹位置
     * @return 当前用户的个人资料夹位置
     * @throws QMRemoteException
     */
    public String getPersionalFolder()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.getPersionalFolder() begin...");
        }
        Class[] c =
                {};
        Object[] objs =
                {};
        UserInfo user = (UserInfo) useServiceMethod(
                "SessionService", "getCurUserInfo", c, objs);
        Class[] c1 =
                {
                UserInfo.class};
        Object[] objs1 =
                {
                user};
        SubFolderInfo folder = (SubFolderInfo) useServiceMethod(
                "FolderService", "getPersonalFolder", c1, objs1);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.getPersionalFolder() end...return: "
                    + folder.getPath());
        }
        return folder.getPath();
    }

    //add by wangh on 20070518(得到产品状态的代码管理的第一个值)
//    public String getFirstCoding()
//    throws QMRemoteException
//    {
//    	Strin code = technicsType.getCodeContent();
//
//    	return code;
//    }


    /**
     * 清除
     */

    public void clear()
    {
        if (firstInFlag)
        {
            firstInFlag = false;
            return;
        }
        numberJTextField.setText("");
        nameJTextField.setText("");
        remarkJTextField.setText("");
        remarkDisplayJLabel.setText("");
        numberDisplayJLabel.setText("");
        nameDisplayJLabel.setText("");
        typeDisplayJLabel.setText("");
        workshopSortingSelectedPanel.setCoding(null);
        workShopDisplayjLabel.setText("");
        folderPanel.setLabelText("");
        doclinkJPanel.clear();
        //CCBegin SS12
        separableNumber.setText("");
        separableName.setText("");
        separableCount.setText("");
        masterJPanel.remove(separableJPanel);
        //CCEnd SS12
        if (partLinkJPanel != null)
        {
            partLinkJPanel.clear();
        }
        if (materialLinkJPanel != null)
        {
            materialLinkJPanel.clear();
        }
        lifeCycleInfo.clear();
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(null);
        lifeCycleInfo.getProjectPanel().setObject(null);
        //20070202薛静add
      //CCBegin SS6
        String type=technicsType.getCodeContent();
        if(ts16949){
       	 if(isBSXAutoNum(type)){
               this.consmasterTS16949Panel.clear();
       	 }else{
       		   this.masterTS16949Panel.clear();
       	 }
        }
        //CCEnd SS6
        isSave = true;
        //问题（3） 20080811  徐春英修改  修改原因调用的“代码管理”内容结构为多层时，默认第一层结构为展开状态，
	    //然后双击第一层结构节点后展开第二层结构
	    //只有一级菜单的选择添加项目，改为下拉列表方式添加
        productStateComboBox.setSelectedIndex(0);
        checkTechTypeComboBox.setSelectedIndex(0);
        
        productStateDisplJLabel.setText("");
        checkTechTypeDisplJLabel.setText("");
        //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
        this.getUpFilePanel().getMultiList().clear();
        //CCEnd by liunan 2009-10-12

    }


    /**
     * 设置按钮可见
     * @param flag boolean
     */
    public void setAllButonVisible(boolean flag)
    {
        if (!flag)
        {
            saveJButton.setVisible(false);
            quitJButton.setVisible(false);
            quitJButton.setVisible(false);
        }
    }


    /**
     *根据工艺种类调整界面中各个组建
     */
    public void refreshObject()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMaster.refreshObject bedin ");
        }
        String processType = technicsType.getCodeContent().trim();
        newCappExAttrPanel(processType);

        //改变零部件使用工艺卡panel
        newPartLinkPanel(processType);
        //更改材料关联面板
        newMaterialLinkJPanel(processType);
        //更改制件流程图面板
        newDrawingpanel(processType);
        existTechnicsType = processType;

        /*
                    //改变零部件使用工艺卡panel与材料关联面板
                    WorkThread partAndMaththread = new WorkThread(0);
                    partAndMaththread.start();
                   //更新扩展属性面板
                    WorkThread extthread = new WorkThread(2);
                    extthread.start();
                    //更改制件流程图面板
                    WorkThread drawthread = new WorkThread(3);
                    drawthread.start();
         */

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMaster.refreshObject end");
        }
    }


    /**
     * 实例化材料面板
     * @param technicsType String 工艺种类
     */
    private void newMaterialLinkJPanel(String technicsType)
    {
        if (!technicsType.equals(existTechnicsType))
        {
            relationsJTabbedPane.remove(materialLinkJPanel);
            materialLinkJPanel = (TechUsageMaterialLinkJPanel)
                                 materialLinkTable.get(technicsType);
            if (materialLinkJPanel == null)
            {
                materialLinkJPanel = new TechUsageMaterialLinkJPanel(
                        technicsType);
                materialLinkTable.put(technicsType, materialLinkJPanel);
                ((PartUsageTechLinkJPanel) partLinkTable.get(technicsType)).
                        setMaterialLinkJPanel(materialLinkJPanel);

            }
        }
        materialLinkJPanel.clear();
        materialLinkJPanel.setTechnics(getTechnics());
        //materialLinkJPanel.setLinks(data.getQMTechnicsUsageResource());
        if (mode == VIEW_MODE)
        {
            materialLinkJPanel.setMode("View");
        }
        else
        {
            materialLinkJPanel.setMode("Edit");
        }
        if (!technicsType.equals(existTechnicsType))
        {
        
        	//guoguo
//              relationsJTabbedPane.add(materialLinkJPanel,
//                                     QMMessage.
//                                     getLocalizedMessage(RESOURCE,
//                    "materialTitle", null), 2);
            //guoguo
        }

    }


    /**
     * 根据录入的工艺卡种类，创建不同的零件关联面板
     * @param technicsType String 工艺种类
     */
    public void newPartLinkPanel(String technicsType)
    {
        if (!technicsType.equals(existTechnicsType))
        {
            relationsJTabbedPane.remove(partLinkJPanel);
            partLinkJPanel = (PartUsageTechLinkJPanel) partLinkTable.get(
                    technicsType);

            if (partLinkJPanel == null)
            {
                //CCBegin SS3
                if(isBSXAutoNum(technicsType))
                {
                    partLinkJPanel = new PartUsageTechLinkJPanel(technicsType);
                    
                }
                else
                {
                  
                	
//              CCBeginby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
                partLinkJPanel = new PartUsageTechLinkJPanel(this,technicsType);
//              CCEndby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
                
                //CCBegin guoguo
                System.out.println("technicsType=========mmm==========="+technicsType);
                partLinkJPanel.setCoding(this.technicsType);
                //CCEnd guoguo
                
                
                }
                //CCEnd SS3
                partLinkTable.put(technicsType, partLinkJPanel);
                //加此监听的原因：当零部件关联面板加入主要零部件,则处理材料关联面板
                partLinkJPanel.addListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        materialLinkJPanel.
                                actionPerformd_whenAddedMajorPark(e);
                        
                    }
                });

            }
        }

        partLinkJPanel.clear();
        partLinkJPanel.setTechnics(getTechnics());
        //partLinkJPanel.setLinks(data.getPartUsageQMTehnics());
        if (mode == VIEW_MODE)
        {
            partLinkJPanel.setMode("View");
        }
        else
        {
            partLinkJPanel.setMode("Edit");
        }
        if (!technicsType.equals(existTechnicsType))
        {
            relationsJTabbedPane.add(partLinkJPanel,
                                     QMMessage.
                                     getLocalizedMessage(RESOURCE,
                    "partTitle", null), 1);
        }

    }


    /**
     * 根据录入的工艺卡种类，创建不同的扩展属性
     * @param technicsType String 工艺卡种类
     */
    private void newCappExAttrPanel(String technicsType)
    {
//CCBegin by liuzhicheng 2010-01-15 原因：工艺卡其他关联信息面板查看时滚动条不起作用。
//        if (!technicsType.equals(existTechnicsType))
//        {
//CCBegin by liuzhicheng 2010-01-15 原因：工艺卡其他关联信息面板查看时滚动条不起作用。
            if (cappExAttrPanel != null)
            {
                extendJPanel.remove(cappExAttrPanel);
            }
//CCBegin by liuzhicheng 2010-01-15 原因：工艺卡其他关联信息面板查看时滚动条不起作用。
//            if (extendTable.get(technicsType) == null)
//            {
//CCBegin by liuzhicheng 2010-01-15 原因：工艺卡其他关联信息面板查看时滚动条不起作用。
                try
                {
                    cappExAttrPanel = new CappExAttrPanel("QMFawTechnics",
                            technicsType, 1);
                }
                catch (QMException ex)
                {
                    ex.printStackTrace();
                }
//CCBegin by liuzhicheng 2010-01-15 原因：工艺卡其他关联信息面板查看时滚动条不起作用。                
//                extendTable.put(technicsType, cappExAttrPanel);
//            }
//            else
//            {
//                cappExAttrPanel = (CappExAttrPanel) extendTable.get(
//                        technicsType);
//
//            }
//        }
//CCBegin by liuzhicheng 2010-01-15 原因：工艺卡其他关联信息面板查看时滚动条不起作用。                
        cappExAttrPanel.clear();
        if (mode == CREATE_MODE ||
            mode == UPDATE_MODE || mode == SAVEAS_MODE)
        {
            cappExAttrPanel.setModel(CappExAttrPanel.EDIT_MODEL);
        }
        else
        {
            cappExAttrPanel.setModel(CappExAttrPanel.VIEW_MODEL);

        }
        if (mode != CREATE_MODE)
        {
            cappExAttrPanel.show(getTechnics());
        }
        extendJPanel.add(cappExAttrPanel,
                         new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
        repaint();
    }


    public int getMode()
    {
        return mode;
    }


    /**
     * 设置工艺种类
     * @param type CodingIfc 工艺种类
     */
    public void setTechnicsType(CodingIfc type)
    {
        technicsType = type;
    }


    /**
     * 获得资源文件中信息的封装方法
     * @param s String 资源文件中的键
     * @param obj 插入的内容
     * @return String 资源文件中的信息
     */

    private String getMessage(String s, Object[] obj)
    {
        return QMMessage.getLocalizedMessage(RESOURCE, s, obj);
    }


    /**
     * 获得资源文件中信息的封装方法
     * @param s String 资源文件中的键
     * @return String 资源文件中的信息
     */
    private String getMessage(String s)
    {
        return QMMessage.getLocalizedMessage(RESOURCE, s, null);
    }


    /**
     * 根据工艺种类重新获得制件流程图panel
     * @param technicsType String 工艺种类
     */
    private void newDrawingpanel(String technicsType)
    {
        String has = RemoteProperty.getProperty("制件流程图" +
                                                technicsType, "false").
                     trim();
        //此工艺种类存在制件流程图
        if (has.equals("true"))
        {
            //制件流程图panle还未被初始化,将其初始化
            if (drawingpanel == null)
            {
                drawingpanel = new DrawingPanel(true);
                drawingpanel.setEditButtonMnemonic('E', "E");
                drawingpanel.setEditButtonSize(89, 23);
                drawingpanel.setDeleteButtonMnemonic('T');
                drawingpanel.setDeleteButtonSize(89, 23);
                drawingpanel.setViewButtonMnemonic('K');
                drawingpanel.setViewButtonSize(89, 23);

                masterJPanel.add(drawingpanel,
                                 new
                                 GridBagConstraints(1, 8, 5, 1,
                        1.0, 0.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        new
                        Insets(5, 0, 10, 15),
                        0, 0));
            }
            //将制件流程图panel显示
            drawingpanel.setVisible(true);
            if (mode == VIEW_MODE)
            {
                drawingpanel.setModel(DrawingPanel.VIEW);
            }
            else
            {
                drawingpanel.setModel(DrawingPanel.EDIT);
            }
            if (mode != CREATE_MODE)
            {
              // ll modify in 20071101 begin
                //drawingpanel.setDrawingDate(getTechnics().
                                           // getFlowDrawing());
             try{
                 drawingpanel.setDrawingDate(getTechnics().getCappFlowing());
             }catch(QMException e){
                 e.printStackTrace();
             }
              // ll modify in 20071101 end
            }
            else
            {
                drawingpanel.setDrawingDate(null);
            }
            flowDrawingDisp.setVisible(true);
        }
        //此工艺种类不存在制件流程图,在界面中不显示制件流程图panel
        else
        {
            if (drawingpanel != null &&
                drawingpanel.getParent() == masterJPanel)
            {
                drawingpanel.setVisible(false);
            }
            flowDrawingDisp.setVisible(false);
        }
    }

    
    /**
     * 设置选择的单位
     * @param comboBox 列表框
     * @param coding 代码项
     * 问题（1） 20080812 xucy 修改
     */
    public void setComboBox(JComboBox box, BaseValueIfc coding)
    {
        int j = box.getItemCount();
        BaseValueIfc temp = null;
        for (int i = 0; i < j; i++)
        {
        	//问题（1） 20080812 xucy 修改
        	if(!box.getItemAt(i).equals(""))
        	{
        	    temp = (BaseValueIfc) box.getItemAt(i);
                if (coding != null && coding.getBsoID().equals(temp.getBsoID()))
                {
                    box.setSelectedIndex(i);
                    break;
                }
        	}
        	
        }
    }

 

    /**
     * <p>Title:工作线程 </p>
     * <p>Description: 内部类,此线程类主要处理界面,做此线程的目的是使界面的
     * 各项工作并行进行,以提高工作效率</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author not 薛静
     * @version 1.0
     */
    class WorkThread extends Thread
    {
        /**
         * 工作标识
         */
        int workaction;


        public WorkThread(int action)
        {
            workaction = action;
        }

        public void run()
        {
            switch (workaction)
            {
                //处理零件关联面板，材料关联面板
                case 0:
                    newPartLinkPanel(technicsType.getCodeContent().trim());
                    newMaterialLinkJPanel(technicsType.getCodeContent().trim());
                    break;
                    //处理扩展属性面板
                case 2:
                    newCappExAttrPanel(technicsType.getCodeContent().trim());
                    break;

                    //处理制件流程图面板
                case 3:
                    newDrawingpanel(technicsType.getCodeContent().trim());
                    break;
                    //处理生命周期面板
                case 4:
                    lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
                            LifeCycleManagedIfc) getTechnics());
                    lifeCycleInfo.getProjectPanel().setObject((
                            LifeCycleManagedIfc) getTechnics());
                    break;
                case 5:
                    refreshObject();
                    break;
                case 6:
                    save();
                    break;
                default:
                    break;
            }
        }
    }
    // Begin CR1
	/**获得创建者
	 * @param s  工艺bsoid
	 * @return   返回创建者
	 */
	private String getCreator(String s) 
	{
		String creator = "";
		try {
			RequestServer server = RequestServerFactory.getRequestServer();
			ServiceRequestInfo info1 = new ServiceRequestInfo();
			info1.setServiceName("PersistService");
			info1.setMethodName("refreshInfo");
			Class[] theClass = { String.class };
			Object[] obj = { s };
			info1.setParaClasses(theClass);
			info1.setParaValues(obj);
			UserIfc ifc = (UserIfc) server.request(info1);
			creator = ifc.getUsersDesc();
		} catch (QMRemoteException ex) {
			ex.printStackTrace();
		}

		return creator;
	}
	//End CR1
	
	
	 //CCBegin by liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
    /**
     * 获得所有的附件信息
     */
    private ArrayList getArrayList() 
    {
	    /**内容项值对象*/
	    ApplicationDataInfo applicationData = null;
	    ArrayList arrayList = new ArrayList();
      int j = getUpFilePanel().getMultiList().getTable().getRowCount();
	  //CCBegin SS10
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
	    	//问题(3)2007.01.17 徐春英修改  修改原因:字节流没有保存到数据库,
	          //构造二维数组存放ApplicationDataInfo对象和字节流.
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
	          //问题(3)2007.01.17 徐春英修改 获得文件字节流
	          byte[] byteStream = getFileByte(path);
	          object[0] = applicationData;
	          object[1] = byteStream;
	          //问题(3)2007.01.17 徐春英修改  将object数组放到arrayList1里,供客户端保存用
	          arrayList.add(object);
	      }
	  }
	  //CCEnd SS10
      return arrayList;
    }
    
    /**
     * 获得更新时所有的附件信息，都是新增的附件。
     */
    private ArrayList getArrayListupdate() 
    {
	    /**内容项值对象*/
	    ApplicationDataInfo applicationData = null;

        int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        ArrayList arrayList1 = new ArrayList();
        //CCBegin SS10
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
                    //问题(3)2007.01.17 徐春英修改  修改原因:字节流没有保存到数据库,
                    //构造二维数组存放ApplicationDataInfo对象和字节流.
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
                    //问题(3)2007.01.17 徐春英修改 获得文件字节流
                    byte[] byteStream = getFileByte(path);
                    object[0] = applicationData;
                    object[1] = byteStream;
                    //问题(3)2007.01.17 徐春英修改  将object数组放到arrayList1里,供客户端保存用
                    arrayList1.add(object);
                }
            }
    	}
    	//CCEnd SS10
      return arrayList1;
    }
    
    /**
     * 获得另存为时原工艺卡所拥有的附件信息。
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
     * 根据文件路径获得文件流
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
     * 设置定额界面中添加附件的信息
     * @param equip QMEquipmentInfo
     */                                 
    private void setUpFileAccessaryName(QMFawTechnicsInfo upFileList)
    {
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
     * 得到内容容器中指定的数据项
     * @param priInfo QMEquipmentInfo 内容容器
     * @return Vector ApplicationDataInfo 内容项
     * @throws RationException 
     */
    private Vector getContents(QMFawTechnicsInfo priInfo) throws QMRemoteException
    {
    	Class[] paraClass ={ContentHolderIfc.class};
    	Object[] obj ={priInfo};
    	Vector  c = (Vector) TechnicsAction.useServiceMethod(
                    "ContentService", "getContents", paraClass, obj);
		  return c;
    }
    
    private UpFilePanel getUpFilePanel()
    {
      return upFilePanel;
    }
    //CCEnd by liunan 2009-10-12
    
    //    CCBegin SS20
    public CappExAttrPanel getCappExAttrPanel(){
    	return cappExAttrPanel;
    }
//    CCEnd SS20
}
