/** 生成程序TechnicsRegulationsMainController.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *  CR1  2009/04/27  薛凯   原因：优化检出工序、工步的响应时间
 *                          方案：客户端用已有数据构造检出树，在去数据库中重新刷工艺树数据了
 *                          备注：性能测试用例名称是"工艺管理检出工序、工步" 
 *  CR2 2009/04/27  徐春英  原因：优化删除工艺的响应时间 
 *                          方案：客户端异步处理，点击删除工艺后，工艺树节点图标变为删除图标，客户端端不启进度条等待，而是能做其它操作。
 *                          备注：性能测试用例名称是"删除工艺"
 *  CR3 2009/04/28  刘学宇  原因：工步无重新生成工步号的功能
 *                          方案：添加了用于重新生成工步号的方法formNewPaceNumber()。
 *                          备注：变更记录标识为CRSS-007
 *  CR4 2009/04/29  刘志城   原因：工艺规程编辑界面添加移除按钮
 *                          方案：工艺规程从工艺树中擦去不显示但不删除。
 *                          备注：变更记录标识为"CRSS-012" 
 *  CR5 2009/05/05  徐春英  参见DefectID=2095    
 *  
 *  CR6 2009/05/22  刘玉柱   原因：工艺主信息下没有工序时，仍然可以重新生成工序号
 *                          方案：弹出对话框，提示不可重新生成工序号
 *                          
 *  CR7 2009/06/02  郭晓亮   参见:测试域:v4r3FunctionTest;TD号2235 
 *  
 *  CR8 2009/06/03  刘学宇   参见:测试域:v4r3FunctionTest;TD号2253 
 *  CR9 2009/08/31  徐春英   参见:测试域:v4r3FunctionTest;TD号2598
 *  CR10 2009/12/29 刘玉柱   TD问题2676
 *  CR11 2010/03/08 贾浩鑫  原因：参见TD2745问题 
 *  CR12 2010/04/13 徐春英  原因:参见TD问题2253
 *  SS1 变速箱资源清单一览表 zhaoqiuying 2013-01-23
 *  SS2 增加变速箱打印预览入口 liuyang 2013.2.4
 *  SS3 20130712 解放检入工艺不要显示生成派生文件 
 *  SS4 轴齿中心工艺派生文件 pante 2014-02-19
 *  SS5 新增“万能量具清单”和“工位器具清单”两个一览表。 liunan 2014-7-28
 *  SS6 TD2646问题，薛凯帮助修改  liunan 2014-8-7
 *  SS7 轴齿中心新需求，增加刀辅具一览表 pante 2014-09-10
 *  SS8 长特增加工装明细，设备清单，模具清单。 guoxiaoliang 2014-08-22
 *  SS9 增加长特合并工艺功能 徐德政 2014-12-15
 *  SS10 成都检出所有工序工步 guoxiaoliang 2016-7-13
 *  SS11 成都结构搜索工艺规程  guoxiaoliang 2016-7-13
 *  SS12 成都关键工序功能  guoxiaoliang 2016-8-6
 *  SS13 轴齿导出工序控制计划 liunan 2016-11-30
 
 */
package com.faw_qm.cappclients.capp.controller;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.cappclients.beans.processtreepanel.OperationTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.ProcessTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.util.AlreadyCheckedOutException;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappFileFilter;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.CheckedOutByOtherException;
import com.faw_qm.cappclients.capp.util.NotCheckedOutException;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.capp.util.QMThread;
import com.faw_qm.cappclients.capp.view.ConsTemplateSelectJDialog;
import com.faw_qm.cappclients.capp.view.ObjectSelectJDialog;
import com.faw_qm.cappclients.capp.view.TSearchMProcedureJDialog;
import com.faw_qm.cappclients.capp.view.TSearchMTechnicsJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsChangeLocationJFrame;
import com.faw_qm.cappclients.capp.view.TechnicsImportJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsReguRenameJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsRegulationsMainJFrame;
import com.faw_qm.cappclients.capp.view.TechnicsSearchFromEquipmentJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsSearchFromMaterialJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsSearchFromToolJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsSearchJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsStructSearchDialog;
import com.faw_qm.cappclients.capp.view.TemplateSelectJDialog;
import com.faw_qm.cappclients.util.BusinessTreeObject;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.lifecycle.client.view.LifeCycleStateDialog;
import com.faw_qm.lifecycle.client.view.ProjectStateDialog;
import com.faw_qm.lifecycle.client.view.SetLifeCycleStateDialog;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.print.clients.util.TPrintController;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.wip.model.WorkableIfc;

//CCBegin by liunan 2012-03-06 解放新提需求，特定人已发布状态可以修改，增加组允许改。
import java.util.Enumeration;

import com.faw_qm.users.model.GroupIfc;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserInfo;
//CCEnd by liunan 2012-03-07
import com.faw_qm.util.EJBServiceHelper;

//CCBegin SS13
import java.io.FileWriter;
//CCEnd SS13

/**
 * <p>Title: 工艺规程维护主界面控制类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 * （1）20060815薛静修改，将鼠标设成正常状态
 * （2）20061201王浩修改deleteSelectedObject()方法，去掉对父节点副本的获得，删除没有用的对象cto
 * （3）20061201王浩修改deleteSelectedObject()方法，去掉确定删除时多余的判断
 * （4）20061201王浩修改deleteSelectedObject()方法，去掉对父节点值对象的获得
 *  (5) delete by wangh on 20061206(去掉程序中没有被调用的参数和方法。具体删除位置在程序中已标明)
 * （6）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
 *  鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
 */

public class TechnicsRegulationsMainController extends TechnicsAction
{

    /**工艺规程维护主界面*/
    private TechnicsRegulationsMainJFrame view;


    /**线程组*/
    private ThreadGroup threadGroup = null;


    /**项目监听器*/
    protected ItemListener itemListener = null;


    /**监听器容器*/
    protected Vector myListener = null;

    //delete by wangh on 20061206(去掉没有引用的字段)
    /**在线帮助*/
    //private QMHelpContext helpContext = null;


    /**帮助系统*/
    //private QMHelpSystem helpSystem = null;


    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private int waitingOnCursor = 0;
  //CCBegin by leixiao 2010-5-7 打补丁　v4r3_p015_20100415 td2745  
    //Begin CR11
    /**是否删除最新版本分支中工艺未关联的所有工艺卡标识*/
    private static String isDeleteAllUnlinkedVersions = RemoteProperty.getProperty(
            "isDeleteAllUnlinkedVersions", "true");
   //End CR11
  //CCEnd by leixiao 2010-5-7 打补丁　v4r3_p015_20100415 td2745  

    //CCBegin by liuzc 2009-11-29 原因：解放系统升级。
    /**复制对象（源节点）*/
    private BaseValueInfo copyOriginalInfo;


    /**复制对象（源节点）*/
    private CappTreeObject copyOriginalTreeObject;


    //复制的原节点的工艺卡BsoID
    private String oriTechnicsID;
    //CCEnd by liuzc 2009-11-29 原因：解放系统升级。
    /**搜索工艺规程的界面*/
    private TechnicsSearchJDialog d;


    /**菜单操作标识*/
    private final static int CREATE_TECHNICS = 1;
    private final static int CREATE_PROCEDURE_STEP = 2;
    private final static int CREATE_PROCEDURE_PACE = 3;
    private final static int COLLECT_TECHNICS = 4;
    private final static int SEARCH_ALL = 5;
    private final static int REFRESH = 6;
    private final static int VIEW = 7;
    private final static int UPDATE = 8;
    private final static int DELETE = 9;
    private final static int FORM_NEW_STEP_NUM = 10;
    private final static int PRINT_BROWSE = 11;
    private final static int RENAME = 12;
    private final static int CHANGE_LOCATION = 13;
    private final static int SAVE_AS = 14;
    private final static int USAGE_MODELTECHNICS = 15;
    private final static int USAGE_MODELPROCEDURE = 16;
    private final static int BUILD_MODELTECHNICS = 17;
    private final static int BUILD_MODELPROCEDURE = 18;
    private final static int COPY = 19;
    private final static int PASTE = 20;
    private final static int CHECK_IN = 21;
    private final static int CHECK_OUT = 22;
    private final static int CANCEL_CHECK_OUT = 23;
    private final static int REVISE = 24;
    private final static int VIEW_VERSION_HISTORY = 25;
    private final static int VIEW_ITERATION_HISTORY = 26;

    //CR3 begin
    private final static int FORM_NEW_PACE_NUM = 27;
    //CR3 end
    //private final static int COMMIT_LIFECYCLE = 27;
    private final static int AFRESH_APPOINT_LIFECYCLE = 28;
    private final static int VIEW_LIFECYCLE_HISTORY = 29;
    private final static int AFRESH_APPOINT_PROJECT = 30;
    private final static int PART_CAPP_DERIVE = 31;
    private final static int MACHINE_CAPP_DERIVE = 32;
    private final static int SEARCH_IN_EQUIPMENT = 33;
    private final static int SEARCH_IN_TOOLS = 34;
    private final static int SEARCH_IN_MATERIAL = 35;
    private final static int SEARCH_TECHNICS = 36;
    private final static int HELP_TECHNICS_MANAGER = 37;
    private final static int IMPORT = 41;
    private final static int EXPORT = 42;
    private final static int CREATE_TOOL = 43;
    private final static int SEARCH_TREE_OBJECT = 44;
    private final static int REFRESH_TECHNICS = 45;
    private final static int SET_LIFECYCLE_STATE = 46;
    private final static int CONFIGURE_CRITERION = 47;
    private final static int EXPORTALL = 48;
  //CCBegin SS1
    private final static int ITEM_JIAJU = 50;
    private final static int ITEM_WANNENG = 51;
    private final static int ITEM_EQUIP = 52;
    private final static int ITEM_MOJU = 53;
    private final static int ITEM_DAOJU = 54;
    private final static int ITEM_JIAFUJU = 55;
    private final static int ITEM_ZHUANGPEI = 56;
  //CCEnd SS1
    
    //CCBegin SS4
    private final static int ITEM_ZCJJ = 57;
    private final static int ITEM_ZCWNGJ = 58;
    private final static int ITEM_ZCMJ = 59;
    private final static int ITEM_ZCDJ = 60;
    private final static int ITEM_ZCJFJ = 61;
    private final static int ITEM_ZCLJ = 62;
    private final static int ITEM_ZCWNLJ = 63;
    private final static int ITEM_ZCJIANJ = 64;
    private final static int ITEM_ZCSB = 65;
    private final static int ITEM_ZCJIANFJ = 66;
    //CCEnd SS4
    
    //CCBegin SS8
    private final static int ITEM_CTTOOL = 70;
    private final static int ITEM_CTSHEBEI = 71;
    private final static int ITEM_CTMOJU = 72;
    //CCEnd SS8
    
    //CCBegin SS5
    private final static int ITEM_WANNENGLIANGJU = 67;
    private final static int ITEM_GONGWEIQIJU = 68;
    //CCEnd SS5
    //    CCBegin SS7
    private final static int ITEM_ZCDFJ = 69;
//    CCEnd SS7
//    CCBegin SS9
    private final static int CTCOLLECT_TECHNICS = 73;
//    CCEnd SS9
    //CCBegin SS10
    private final static int CHECKALL = 80;
    //CCEnd SS10
    
    //CCBegin SS11
    private final static int STRUCT_SEARCH_TECHNICS = 81;
    //CCEnd SS11
    
    //CCBegin SS12
    private final static int FINDMAINSTEP=82;
    //CCEnd SS12
    
    //delete by wangh on 20061206(去掉没有引用的字段)
    //private Vector threadVec = new Vector();
    //20081218 徐春英修改       修改原因：在工艺规程肥树上按住Ctrl用鼠标选择2个或更多的工序，
    //鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
    //list用于存放要复制的源节点信息
    private ArrayList list = new ArrayList();
    
    private Vector vector = new Vector();//CR1
    
    
    /**
     * 构造函数
     * @param mainFrame 工艺规程维护主界面
     */
    public TechnicsRegulationsMainController(TechnicsRegulationsMainJFrame
                                             mainFrame)
    {
        this.view = mainFrame;
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void jbInit()
    {
        this.threadGroup = QMCt.getContext().getThreadGroup();
        setConfigSpecCommand();
    }


    /**
     * 增加刷新服务监听类
     */
    class MainRefreshListener implements RefreshListener
    {
        //实现接口中的抽象方法fefreshObject()
        public void refreshObject(RefreshEvent refreshevent)
        {
            Object obj = refreshevent.getTarget();

            int i = refreshevent.getAction();

            if (obj instanceof QMTechnicsInfo)
            {
                switch (i)
                {
                    case RefreshEvent.CREATE:
                    { // '\0'
                        //addExplorerPart( (QMPartInfo) obj);
                        return;
                    }

                    case RefreshEvent.UPDATE:
                    { // '\001'
                        refreshExplorerPart((QMTechnicsInfo) obj);
                        return;
                    }
                    case RefreshEvent.DELETE:
                    { // '\002'
                        //deleteExplorerPart( (QMPartInfo)obj );
                        return;
                    }
                }
            }
        }

        MainRefreshListener()
        {
        }
    }


    /**
     * <p>Title: 内部工作线程</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: 一汽启明</p>
     * @author 刘明
     * @version 1.0
     */
    class WorkThread extends QMThread
    {
        int myAction;
        QMTechnicsInfo myTechnics;
        QMTechnicsMasterInfo myTechnicsMaster;

        public WorkThread(ThreadGroup threadgroup, int action)
        {
            super();
            TechnicsRegulationsMainController.this.threadGroup = threadgroup;
            myAction = action;
        }

        public WorkThread(ThreadGroup threadgroup, int action,
                          QMTechnicsInfo technics)
        {
            super();
            TechnicsRegulationsMainController.this.threadGroup = threadgroup;
            myAction = action;
            myTechnics = technics;
        }

        public WorkThread(ThreadGroup threadgroup, int action,
                          QMTechnicsMasterInfo master)
        {
            super();
            TechnicsRegulationsMainController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myTechnicsMaster = master;
        }
       
        
        //CCEnd 

        public void run()
        {
            try
            {
                //view.setCursor(Cursor.WAIT_CURSOR);
                //  synchronized(TechnicsRegulationsMainController.this.cursorLock)
                //{
                if (waitingOnCursor == 0)
                {
                    //  view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                }
                waitingOnCursor++;

                switch (myAction)
                {
                    case CREATE_TOOL:
                        createObject();
                        break;

                    case CREATE_TECHNICS:
                        createTechnics();
                        break;

                    case CREATE_PROCEDURE_STEP:
                        createProcedureStep();
                        break;

                    case CREATE_PROCEDURE_PACE:
                        createProcedurePace();
                        break;

                    case COLLECT_TECHNICS:
                        collectTechnics();
                        break;

//                        CCBegin SS9
                    case CTCOLLECT_TECHNICS:
                        ctCollectTechnics();
                        break;
//                        CCEnd SS9
                        
                    case IMPORT:
                        importTechnics();
                        break;

                    case EXPORT:
                        ExportWorkThread exportWorkThread = new
                                ExportWorkThread();
                        exportWorkThread.start();
                        break;
                    case EXPORTALL:
                        ExportAllWorkThread exportAllWorkThread = new
                                ExportAllWorkThread();
                        exportAllWorkThread.start();
                        break;

                    case SEARCH_ALL:
                        searchAll();
                        break;

                    case REFRESH:
                    {
                    	//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2676
                    	CappTreeNode node = view.getSelectedNode();//begin CR10
                    	view.closeNode(node);//end CR10
                    	//CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2676
                        refreshSelectedObject();
                        break;
                    }

                    case VIEW:
                        viewSelectedObject();
                        break;

                    case UPDATE:
                        updateSelectedObject();
                        break;

                    case DELETE:
                        deleteSelectedObject();
                        break;

                    case FORM_NEW_STEP_NUM: //重新生成工序号

                        FormNewStepThread thread = new FormNewStepThread();
                        thread.start();
                        break;
                   
                    //CR3 begin   
					case FORM_NEW_PACE_NUM: // 重新生成工步号
	                    
						FormNewPaceThread thread1 = new FormNewPaceThread();
						thread1.start();
						break;
					//CR3 end
					//CCBegin SS10	
					case CHECKALL:
						checkAllStepAndPace();
						break;
                   //CCEnd SS10
					
				  //CCBegin SS12	
					case FINDMAINSTEP:
						findMainStep();
						break;
                   //CCEnd SS12
						
						
                    case PRINT_BROWSE: //打印预览
                        browsePrintObject();
                        break;

                    case CHANGE_LOCATION: //更改存储位置
                        changeTechnicsLocation();
                        break;

                    case RENAME: //重命名工艺卡
                        renameTechnics();
                        break;

                    case SAVE_AS:
                        saveAsTechnics(); //工艺卡另存为
                        break;

                    case USAGE_MODELTECHNICS: //应用典型工艺
                        usageModelTechnics();
                        break;

                    case USAGE_MODELPROCEDURE: //应用典型工序
                        usageModelProcedure();
                        break;

                    case BUILD_MODELTECHNICS: //生成典型工艺
                        buildModelTechnics();
                        break;

                    case BUILD_MODELPROCEDURE: //生成典型工序
                        buildModelProcedure();
                        break;

                    case COPY: //复制
                        copy();
                        break;

                    case PASTE: //粘贴
                        paste();
                        break;

                    case CHECK_IN: //检入
                        checkIn();
                        break;

                    case CHECK_OUT: //检出
                        checkOut();
                        break;

                    case CANCEL_CHECK_OUT: //撤销检出
                        cancelCheckOut();
                        break;

                    case REVISE: //修订
                        revise();
                        break;

                    case VIEW_VERSION_HISTORY: //查看版本历史
                        viewVersionHistory();
                        break;

                    case VIEW_ITERATION_HISTORY: //查看版序历史
                        viewIterationHistory();
                        break;

                    case SET_LIFECYCLE_STATE:
                        setLifeCycleState(); //设置生命周期状态
                        break;

                    case AFRESH_APPOINT_LIFECYCLE: //重新指定生命周期
                        afreshAppointLifeCycle();
                        break;

                    case VIEW_LIFECYCLE_HISTORY: //查看生命周期历史
                        viewLifeCycleHistory();
                        break;

                    case AFRESH_APPOINT_PROJECT: //重新指定工作组
                        afreshAppointProject();
                        break;

                    case PART_CAPP_DERIVE: //零件族工艺派生
                        partCappDerive();
                        break;

                    case MACHINE_CAPP_DERIVE: //加工路线工艺派生
                        machineCappDerive();
                        break;

                    case CONFIGURE_CRITERION: //配置规范
                        configureCriterion();
                        break;
                    case SEARCH_IN_EQUIPMENT: //按设备搜索工艺
                        searchInEquipment();
                        break;

                    case SEARCH_IN_TOOLS: //按工装搜索工艺
                        searchInTools();
                        break;

                    case SEARCH_IN_MATERIAL: //按材料搜索工艺
                        searchInMaterial();
                        break;

                    case SEARCH_TECHNICS: //搜索工艺规程
                        searchTechnics();
                        break;
                        
                        //CCBegin SS11
                    case STRUCT_SEARCH_TECHNICS:
    					searchStructTechnics();
    					break;
                        //CCEnd SS11

                    case HELP_TECHNICS_MANAGER: //工艺规程管理
                        helpTechnicsManager();
                        break;
                    case SEARCH_TREE_OBJECT:
                        searchTreeObject();
                    case REFRESH_TECHNICS:
                        refreshQMTechnics(myTechnics);
                      //CCBegin SS1
                    case ITEM_JIAJU:
                        schedule("夹具明细表");
                        break;
                    case ITEM_WANNENG:
                        schedule("万能工具清单");
                        break;
                    //CCBegin SS5
                    case ITEM_WANNENGLIANGJU:
                        schedule("万能量具清单");
                        break;
                    case ITEM_GONGWEIQIJU:
                        schedule("工位器具清单");
                        break;
                    //CCEnd SS5
                    case ITEM_EQUIP:
                        schedule("设备清单");
                        break;
                    case ITEM_MOJU:
                        schedule("磨具一览表");
                        break;
                    case ITEM_DAOJU:
                        schedule("刀具一览表");
                        break;
                    case ITEM_JIAFUJU:
                        schedule("夹辅具一览表");
                        break;
                    case ITEM_ZHUANGPEI:
                        schedule("装配工具一览表");
                        break;
                      //CCEnd SS1
                      //CCBegin SS4
                    case ITEM_ZCJJ:
                        schedule("轴齿夹具明细表");
                        break;
                    case ITEM_ZCWNGJ:
                        schedule("轴齿万能工具明细表");
                        break;
                    case ITEM_ZCMJ:
                        schedule("轴齿磨具一览表");
                        break;
                    case ITEM_ZCDJ:
                        schedule("轴齿刀具一览表");
                        break;
                        //                        CCBegin SS7
                    case ITEM_ZCDFJ:
                        schedule("轴齿刀辅具一览表");
                        break;
//                        CCEnd SS7
                    case ITEM_ZCJFJ:
                        schedule("轴齿夹辅具一览表");
                        break;
                    case ITEM_ZCLJ:
                        schedule("轴齿量具一览表");
                        break;
                    case ITEM_ZCWNLJ:
                        schedule("轴齿万能量具明细表");
                        break;
                    case ITEM_ZCJIANJ:
                        schedule("轴齿检具明细表");
                        break;
                    case ITEM_ZCSB:
                        schedule("轴齿设备清单");
                        break;
                    case ITEM_ZCJIANFJ:
                        schedule("轴齿检辅具一览表");
                        break;
                      //CCEnd SS4
                      
                    //CCBegin SS8
                    case ITEM_CTTOOL:
                        schedule("长特工装明细表");
                        break;
                    case ITEM_CTSHEBEI:
                        schedule("长特设备清单");
                        break;
                    case ITEM_CTMOJU:
                        schedule("长特模具清单");
                        break;
                      //CCEnd SS8
                }
                // }
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            finally
            {

            }
        } //end run()

    } //end WorkThread


    /**
     * 获得当前选择的对象(含标签节点)。
     * 如果没有选择对象返回null,并抛出提示信息。
     * @return 当前选择的对象
     * @throws QMRemoteException
     */
    /*  public CappTreeObject getSelectedObject() throws QMRemoteException
      {

        if(view.getSelectedObject() == null)
        {
          String message = QMMessage.getLocalizedMessage(
              RESOURCE,
              CappLMRB.NOT_SELECT_OBJECT,
              null);
          throw new QMRemoteException(message);
        }
        else
        {
          return view.getSelectedObject();
        }
      }*/

    /**
     * 处理工具条上“新建”按钮命令
     */
    public void processCreateObjectCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_TOOL);
        work.start();
    }


    /**
     * 处理"新建工艺主信息"命令
     */
    public void processCreateTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_TECHNICS);
        work.start();
    }


    /**
     * 处理"新建工序"命令
     */
    public void processCreateProcedureStepCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         CREATE_PROCEDURE_STEP);
        work.start();
    }


    /**
     * 处理"新建工步"命令
     */
    public void processCreateProcedurePaceCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         CREATE_PROCEDURE_PACE);
        work.start();
    }


    /**
     * 处理"合并"命令
     */
    public void processCollectTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COLLECT_TECHNICS);
        work.start();
    }

//    CCBegin SS9
    /**
     * 处理"合并"命令
     */
    public void processCTCollectTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CTCOLLECT_TECHNICS);
        work.start();
    }
//CCEnd SS9
    
    /**
     * 处理“导入工艺规程”命令
     */
    public void processImportCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), IMPORT);
        work.start();
    }


    /**
     *  处理“导出工艺规程”命令
     */
    public void processExportCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXPORT);
        work.start();
    }


    /**
     *  处理“导出全部工艺规程”命令
     */
    public void processExportAllCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXPORTALL);
        work.start();
    }


    /**
     * 处理"搜索"命令
     */
    public void processSearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_ALL);
        work.start();
    }


    /**
     * 处理"刷新"命令
     */
    public void processRefreshCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESH);
        work.start();
    }


    /**
     * 处理"查看"命令
     */
    public void processViewCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    //CCBegin SS10
    /**
     * 检出所有工序工步
     */
    public void processCheckAllCommond() {
    	
		WorkThread work = new WorkThread(getThreadGroup(), CHECKALL);
		work.start();
	}
    //CCEnd SS10
    
    //CCBegin SS12
    public void processFindMainStepCommond (){
    	
		WorkThread work = new WorkThread(getThreadGroup(), FINDMAINSTEP);
		work.start();
	}
    
    //CCEnd SS12
    
    
    /**
     * 处理"更新"命令
     */
    public void processUpdateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }
    
    //CCBegin SS11
    
    public void processStructSearchTechnicsCommand() {
		WorkThread work = new WorkThread(getThreadGroup(),
				STRUCT_SEARCH_TECHNICS);
		work.start();
	}
    //CCCEnd SS11


    /**
     * 处理"删除"命令
     */
    public void processDeleteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }


    /**
     * 处理"重新生成工序号"命令
     */
    public void processFormNewStepnumCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), FORM_NEW_STEP_NUM);
        work.start();
    }
   
    //CR3 begin
    /**
	 * 处理"重新生成工步号"命令
	 */
	
	public void processFormNewPacenumCommand()
	{
		WorkThread work = new WorkThread(getThreadGroup(), FORM_NEW_PACE_NUM);
		work.start();
	}
    //CR3 end

    /**
     * 处理"打印预览"命令
     */
    public void processPrintBrowseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PRINT_BROWSE);
        work.start();
    }


    /**
     * 处理"重命名"命令
     */
    public void processRenameCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RENAME);
        work.start();
    }


    /**
     * 处理"更改存储位置"命令
     */
    public void processChangeLocationCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHANGE_LOCATION);
        work.start();
    }


    /**
     * 处理"另存为"命令
     */
    public void processSaveAsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SAVE_AS);
        work.start();
    }


    /**
     * 处理"应用典型工艺"命令
     */
    public void processUsageModelTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), USAGE_MODELTECHNICS);
        work.start();
    }


    /**
     * 处理"应用典型工序"命令
     */
    public void processUsageModelProcedureCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), USAGE_MODELPROCEDURE);
        work.start();
    }


    /**
     * 处理"生成典型工艺"命令
     */
    public void processBuildModelTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_MODELTECHNICS);
        work.start();
    }


    /**
     * 处理"生成典型工序"命令
     */
    public void processBuildModelProcedureCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_MODELPROCEDURE);
        work.start();
    }


    /**
     * 处理"复制"命令
     */
    public void processCopyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COPY);
        work.start();
    }


    /**
     * 处理"粘贴"命令
     */
    public void processPasteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PASTE);
        work.start();
    }


    /**
     * 处理"检入"命令
     */
    public void processCheckInCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECK_IN);
        work.start();
    }


    /**
     * 处理"检出"命令
     */
    public void processCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECK_OUT);
        work.start();
    }


    /**
     * 处理"撤销检出"命令
     */
    public void processCancelCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CANCEL_CHECK_OUT);
        work.start();
    }


    /**
     * 处理"修订"命令
     */
    public void processReviseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REVISE);
        work.start();
    }


    /**
     * 处理"查看版本历史"命令
     */
    public void processViewVersionHistoryCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_VERSION_HISTORY);
        work.start();
    }


    /**
     * 处理"查看版序历史"命令
     */
    public void processViewIterationHistoryCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         VIEW_ITERATION_HISTORY);
        work.start();
    }


    /**
     * 处理"提交"命令
     */
    public void processSetLifeCycleStateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SET_LIFECYCLE_STATE);
        work.start();
    }


    /**
     * 处理"重新指定生命周期"命令
     */
    public void processAfreshAppointLifecycleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         AFRESH_APPOINT_LIFECYCLE);
        work.start();
    }


    /**
     * 处理"查看生命周期历史"命令
     */
    public void processViewLifecycleHistoryCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         VIEW_LIFECYCLE_HISTORY);
        work.start();
    }


    /**
     * 处理"重新指定工作组"命令
     */
    public void processAfreshAppointProjectCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         AFRESH_APPOINT_PROJECT);
        work.start();
    }


    /**
     * 处理"零件族工艺派生"命令
     */
    public void processPartCappDeriveCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PART_CAPP_DERIVE);
        work.start();
    }


    /**
     * 处理"加工路线工艺派生"命令
     */
    public void processMachineCappDeriveCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), MACHINE_CAPP_DERIVE);
        work.start();
    }


    /**
     * 处理"配置规范"命令
     */
    public void processConfigureCriterionCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CONFIGURE_CRITERION);
        work.start();
    }


    /**
     * 处理"按设备搜索工艺"命令
     */
    public void processSearchInEquipmentCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_IN_EQUIPMENT);
        work.start();
    }


    /**
     * 处理"按工装搜索工艺"命令
     */
    public void processSearchInToolsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_IN_TOOLS);
        work.start();
    }


    /**
     * 处理"按材料搜索工艺"命令
     */
    public void processSearchInMaterialCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_IN_MATERIAL);
        work.start();
    }


    /**
     * 处理"搜索工艺规程"命令
     */
    public void processSearchTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_TECHNICS);
        work.start();
    }


    /**
     * 处理"工艺规程管理"命令
     */
    public void processHelpManagerCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         HELP_TECHNICS_MANAGER);
        work.start();
    }
  //CCBegin SS1    
    public void jMItemjiaju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_JIAJU);
        work.start();
    }
    public void jMItemwanneng()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_WANNENG);
        work.start();
    }
    //CCBegin SS5
    public void jMItemwannengliangju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_WANNENGLIANGJU);
        work.start();
    }
    public void jMItemgongweiqiju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_GONGWEIQIJU);
        work.start();
    }
    //CCEnd SS5
    public void jMItemequip()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_EQUIP);
        work.start();
    }
    public void jMItemmoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_MOJU);
        work.start();
    }
    public void jMItemdaoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_DAOJU);
        work.start();
    }
    public void jMItemjiafuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_JIAFUJU);
        work.start();
    }
    public void jMItemzhuangpei()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZHUANGPEI);
        work.start();
    }
  // CCEnd SS1
    
    //CCBegin SS4
    public void jMItemzcjiaju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJJ);
        work.start();
    }
    public void jMItemzcwangong()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCWNGJ);
        work.start();
    }
    public void jMItemzcmoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCMJ);
        work.start();
    }
    public void jMItemzcdaoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCDJ);
        work.start();
    }
    //    CCBegin SS7
    public void jMItemzcdaofuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCDFJ);
        work.start();
    }
//    CCEnd SS7
    public void jMItemzcjiafuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJFJ);
        work.start();
    }
    public void jMItemzcliangju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCLJ);
        work.start();
    }
    public void jMItemzcwanliang()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCWNLJ);
        work.start();
    }
    public void jMItemzcjianju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJIANJ);
        work.start();
    }
    public void jMItemzcsb()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCSB);
        work.start();
    }
    public void jMItemzcjianfuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJIANFJ);
        work.start();
    }
    //CCEnd SS4
    
    //CCBegin SS8
    public void jMItemCtTool(){
    	
    	  WorkThread work = new WorkThread(getThreadGroup(), ITEM_CTTOOL);
          work.start();
    }
    
    public void jMItemCtSheBei(){
    	
  	  WorkThread work = new WorkThread(getThreadGroup(), ITEM_CTSHEBEI);
        work.start();
  }
    
    public void jMItemCtMoJu(){
    	
  	  WorkThread work = new WorkThread(getThreadGroup(), ITEM_CTMOJU);
        work.start();
  }
    //CCEnd SS8
    
    
    /**
     * 处理"搜索"按钮命令
     */
    public void processSearchTreeObjectCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_TREE_OBJECT);
        work.start();
    }


    /**
     * 新建工艺卡
     */
    private void createTechnics()
            throws QMException
    {
    	
    	view.getContentJPanel().setTechnicsMode(1);
    	
    	
    }


    /**
     * 新建工序
     */
    private void createProcedureStep()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedureStep() begin...");
        }
        try
        {

            CappTreeNode selectedNode = view.getSelectedNode();
            CappTreeObject obj = selectedNode.getObject();
            CappTreeNode parentTechnicsNode = view.getParentTechnicsNode(
                    selectedNode);
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//              delete by wangh on 20061206(去掉没有引用的字段)
               // BaseValueInfo info = (BaseValueInfo) obj.getObject();
                //判断是否允许新建工序
                BaseValueInfo newinfo = isAllowedCreateProcedure(selectedNode
                        );

                //如果没有被当前用户检出，并且在公共资料夹中，即对象处于检入状态，则将其检出
                //if (!CheckInOutCappTaskLogic.isCheckedOutByUser((WorkableIfc)
                //      newinfo)
                //   && CheckInOutCappTaskLogic.isInVault((WorkableIfc) newinfo))
                if (CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc)
                        newinfo))

                {
                    Object[] identity =
                            {
                            getIdentity(newinfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }

                    newinfo = checkOutParentNode(selectedNode,
                                                 parentTechnicsNode);
                }

                //设置所选择的对象（副本）
                view.getContentJPanel().setSelectedObject(newinfo);
                CappTreeNode node = view.findNode(new TechnicsTreeObject(
                        newinfo));
                view.getContentJPanel().setSelectedNode(node);
                //如果选择的对象是工作副本的原本，则获得副本节点
                /* if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info) &&
                 CheckInOutCappTaskLogic.isInVault((WorkableIfc) info)) //在公共资料夹
                 {
                     if (verbose)
                     {
                         System.out.println("选择的对象是工作副本的原本！");
                     }

                     info = (BaseValueInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy(
                             (WorkableIfc) info);
                     CappTreeNode node = view.findNode(new TechnicsTreeObject(
                             info));
                     view.getContentJPanel().setSelectedNode(node);
                 }
                 else
                 {
                     if (verbose)
                     {
                         System.out.println("选择的对象是工作副本,或从未检入过！");
                     }
                     view.getContentJPanel().setSelectedNode(selectedNode);
                 }*/

                view.getContentJPanel().setStepMode(1);
                view.setCursor(Cursor.getDefaultCursor());
            }

        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setCursor(Cursor.getDefaultCursor());
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedureStep() end...return is void");
        }
    }


    /**
     * 判断是否允许在指定的对象下新建工序或工步。
     * 只有在当前指定的对象已被检出，或尚未曾执行检入操作的情况下，才允许新建其工序（步）。
     * @param node 指定的对象（工艺卡、工序、工步）
     * @return 指定对象的工作副本。如果指定对象尚未曾执行检入操作，则直接返回指定对象。
     */
    private BaseValueInfo isAllowedCreateProcedure(CappTreeNode node)
            throws
            QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isAllowedCreateProcedure() begin...");
        }
        BaseValueInfo info = (BaseValueInfo) node.getObject().getObject();
        if (((WorkableIfc) info).getWorkableState().equals("c/i"))
        {
            if (((WorkableIfc) info).getOwner() != null &&
                !((WorkableIfc) info).getOwner().equals(TechnicsRegulationsMainJFrame.currentUser.
                    getBsoID()))
            {
                Object[] objs =
                        {
                        getIdentity(info)};
                String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                        "creatByOther", objs);
                throw new QMRemoteException(message1);

            }
            else
            {
                return info;
            }
        }

        //if 1:如果被其他用户检出,提示"已经被他人检出!"
        if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc) info,
                TechnicsRegulationsMainJFrame.currentUser))
        {
            Object[] objs =
                    {
                    getIdentity(info)};
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
            throw new QMRemoteException(message1);
        } //end if 1
        //2:如果被当前用户检出
        else
        {

            //如果是被当前用户检出但不是工作副本,就获得工作副本
            if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info))
            {
                info = (BaseValueInfo) CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) info);
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isAllowedCreateProcedure() end...return : "
                               + info);
        }
        return info;
    }


    /**
     * 新建工步
     */
    private void createProcedurePace()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedurePace() begin...");
        }
        try
        {
            CappTreeNode selectedNode = view.getSelectedNode();
            CappTreeNode parentTechNode = view.getParentTechnicsNode(
                    selectedNode);
            CappTreeObject obj = selectedNode.getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BusinessTreeObject selectedObject = (BusinessTreeObject) obj;
                QMProcedureInfo procedureinfo = (QMProcedureInfo)
                                                selectedObject.
                                                getObject();
                //如果有关联工艺，则不允许在该工序下创建工步；否则允许在该工序下创建工步
                if (procedureinfo.getRelationCardBsoID() != null
                    && !(procedureinfo.getRelationCardBsoID()).equals(""))
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.CAN_NOT_CREATE_PACE, null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, message, title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return;
                }

                //判断是否允许新建工步
                BaseValueInfo newinfo = isAllowedCreateProcedure(selectedNode);
                //如果没有被当前用户检出，并且在公共资料夹中，即对象处于检入状态，则将其检出
                if (CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc)
                        newinfo))

                {
                    Object[] identity =
                            {
                            getIdentity(newinfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }

                    newinfo = checkOutParentNode(selectedNode,
                                                 parentTechNode);
                }

                //设置所选择的对象（副本）
                view.getContentJPanel().setSelectedObject(newinfo);

                //如果选择的对象是工作副本的原本，则获得副本节点
//                if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)
//                        newinfo) &&
//                    CheckInOutCappTaskLogic.isInVault((WorkableIfc) newinfo)) //在公共资料夹
//                {
//                    if (verbose)
//                    {
//                        System.out.println("选择的对象是工作副本的原本！");
//                    }
//                    newinfo = (QMProcedureInfo) CheckInOutCappTaskLogic.
//                              getWorkingCopy(
//                            (WorkableIfc) newinfo);
//                }

                //{{设置所选择的树节点
                CappTreeNode node;
                if (((QMProcedureInfo) newinfo).getIsProcedure())
                {
                    node = view.findNode(new StepTreeObject(newinfo));
                }
                else
                {
                    node = view.findNode(new OperationTreeObject(newinfo));
                }
                view.getContentJPanel().setSelectedNode(node);
                view.getContentJPanel().setPaceMode(1);
                view.setCursor(Cursor.getDefaultCursor());

            }
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setCursor(Cursor.getDefaultCursor());
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedurePace() end...return is void");
        }
    }


    /**
     * 导入工艺规程
     */
    private void importTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.importTechnics() begin...");
        }
        TechnicsImportJDialog d = new TechnicsImportJDialog();
        d.setViewLocation();
        d.setVisible(true);
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.importTechnics() end...return is void");
        }
    }

    class ExportWorkThread extends Thread
    {
        public void run()
        {

            try
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                exportTechnics();
                view.setCursor(Cursor.getDefaultCursor());
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.
                                              INFORMATION_MESSAGE);
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());

            }

        }
    }


    class ExportAllWorkThread extends Thread
    {
        public void run()
        {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try
            {
                exportAllTechnics();
            }
            catch (QMRemoteException ex)
            {
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.
                                              INFORMATION_MESSAGE);

            }
            view.setCursor(Cursor.getDefaultCursor());

        }
    }


    /**
     * 导出工艺规程
     */
    private void exportTechnics()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() begin...");

        }
        CappTreeNode[] nodes = view.getProcessTreePanel().getSelectedNodes();
        if (nodes != null && nodes.length != 0)
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ArrayList list = new ArrayList();
            CappTreeObject treeobj = null;
            for (int i = 0, l = nodes.length; i < l; i++)
            {
                treeobj = view.update(nodes[i].getObject());
                QMFawTechnicsIfc tech = (QMFawTechnicsIfc) treeobj.
                                        getObject();
                list.add(tech);
            }
            //文件选择器
            JFileChooser chooser = new JFileChooser();
            //设置文件选取模式：文件和路径
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            //设置不可多选
            chooser.setMultiSelectionEnabled(false);
            chooser.setCurrentDirectory(new File("D:\\"));
            //文件选择器
            CappFileFilter filter = new CappFileFilter();
            chooser.setFileFilter(filter);
            //“保存”模式文件选择器中选择了批准按钮还是取消按钮
            int state = chooser.showSaveDialog(null);

            //获得选择的文件
            File file = chooser.getSelectedFile();

            //如果选择了批准按钮,将本系统的工艺规程数据（工艺规程中的主信息、工序、工步等），
            //按业务规则（CAPP-BR014）导出到指定路径及名字的文件中。
            if (file != null && state == JFileChooser.APPROVE_OPTION)
            {
                chooser.setVisible(false);
                view.startProgress();
                Class[] paraclass =
                        {
                        ArrayList.class, String.class,
                        Boolean.TYPE};
                Object[] paraobj =
                        {
                        list, file.getPath(), new Boolean(false)};
                useServiceMethod("StandardCappService", "exportTechnics",
                                 paraclass, paraobj);
                view.stopProgress();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "exportSuccessful", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);

                //调用服务端方法，执行导出
                //导出完成后，系统给出成功消息:操作已经成功！
                //如果由于网络原因或服务端错误等原因导致导出无法完成，系统应给出相应的提示信息:网络
                //故障或服务器端出现错误，导出无法继续
            }
            view.setCursor(Cursor.getDefaultCursor());

        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() end...return is void");
        }
    }


    /**
     * 导出工艺规程
     */
    private void exportAllTechnics()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() begin...");

        }

        JFileChooser chooser = new JFileChooser();
        //设置文件选取模式：文件和路径
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //设置不可多选
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File("D:\\"));
        //文件选择器
        CappFileFilter filter = new CappFileFilter();
        chooser.setFileFilter(filter);
        //“保存”模式文件选择器中选择了批准按钮还是取消按钮
        int state = chooser.showSaveDialog(null);

        //获得选择的文件
        File file = chooser.getSelectedFile();
        //如果选择了批准按钮,将本系统的工艺规程数据（工艺规程中的主信息、工序、工步等），
        //按业务规则（CAPP-BR014）导出到指定路径及名字的文件中。
        if (file != null && state == JFileChooser.APPROVE_OPTION)
        {
            chooser.setVisible(false);
            view.startProgress();
            Class[] paraclass =
                    {
                    String.class};
            Object[] paraobj =
                    {
                    file.getPath()};
            useServiceMethod("StandardCappService", "exportAllTechnics",
                             paraclass, paraobj);
            view.stopProgress();
            //调用服务端方法，执行导出
            //导出完成后，系统给出成功消息:操作已经成功！
            //如果由于网络原因或服务端错误等原因导致导出无法完成，系统应给出相应的提示信息:网络
            //故障或服务器端出现错误，导出无法继续
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    "exportSuccessful", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() end...return is void");
        }
    }


    /**
     * 搜索
     */
    private void searchAll()
    {
        try
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            TechnicsSearchJDialog d = new TechnicsSearchJDialog(view);
            d.addDefaultListener();
            d.setVisible(true);
            view.setCursor(Cursor.getDefaultCursor());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }

    }

    public void refreshSelectedObject()

    {
        CappTreeNode node = view.getSelectedNode();
        try
        {
            refreshSelectedObject(node);
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setCursor(Cursor.getDefaultCursor());

        }
    }


    /**
     * 刷新所选择的对象
     */
    public void refreshSelectedObject(CappTreeNode node)
            throws QMException
    {
        if (verbose)

        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.refreshSelectedObject() begin...");
        }
        //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 
        view.closeNode(node);//CR10
        //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 
        BaseValueInfo info = (BaseValueInfo) node.getObject().getObject();//Begin CR1
		if (vector.contains(info.getBsoID()))
		{
			JOptionPane.showMessageDialog(view, "此工艺正在删除，不能对其进行其他操作", "警告！",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}//End CR1
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Collection vec = (Collection) useServiceMethod("StandardCappService",
                "doAction",
                new Class[]
                {BaseValueIfc.class}
                ,
                new Object[]
                {node.getObject().getObject()});

        if (vec != null && vec.size() == 2)
        {
            BaseValueInfo latestinfo = (BaseValueInfo) vec.toArray()[0];
            //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 
             //Begin CR10
            UserIfc userIfc = this.getCurrentUser();
            if((((WorkableIfc)latestinfo).getWorkableState()).equals("c/o"))
            {
                if(userIfc.getUsersName().equals("Administrator") || 
                        userIfc.getBsoID().equals(((QMTechnicsInfo)latestinfo).getLocker()))
                {
                    latestinfo = (BaseValueInfo)CheckInOutCappTaskLogic.getWorkingCopy((WorkableIfc)latestinfo);
                }
            }//End CR10
            //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 
            CappTreeObject object = null;
            if (latestinfo instanceof QMTechnicsIfc)
            {
                object = new TechnicsTreeObject(latestinfo);
                //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 
                node.setObject(object);//CR10
                //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 
            }
            else if (latestinfo instanceof QMProcedureIfc)
            {
                if (((QMProcedureIfc) latestinfo).getIsProcedure())
                {
                    object = new StepTreeObject(latestinfo);
                }
                else
                {
                    object = new OperationTreeObject(latestinfo);
                }
            }

            if (object != null)
            {
                ((ProcessTreeObject) object).setParentID(((ProcessTreeObject)
                        node.getObject()).getParentID());
                view.getProcessTreePanel().updateNode(object);
            }
            view.enableMenuItems(object,
                                 ((Boolean) vec.toArray()[1]).booleanValue());
            viewSelectedObject(object);
        }

        view.setCursor(Cursor.getDefaultCursor());
    }

//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2253
     /**
     * 刷新所选择的对象
     */

    //begin CR12
    public void refreshSelectedObject1(CappTreeNode node) throws QMException
    {
        if (verbose)

        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.refreshSelectedObject() begin...");
        }
        BaseValueInfo info = (BaseValueInfo) node.getObject().getObject();
        if (vector.contains(info.getBsoID()))
		{
        	DialogFactory.showInformDialog(view, "此工艺正在删除，不能对其进行其他操作");
//			JOptionPane.showMessageDialog(view, "此工艺正在删除，不能对其进行其他操作", "警告！",
//					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        Collection vec = (Collection) useServiceMethod("StandardCappService",
                "doAction1",
                new Class[]
                {BaseValueIfc.class}
                ,
                new Object[]
                {node.getObject().getObject()});

        if (vec != null && vec.size() == 2)
        {
            BaseValueInfo latestinfo = (BaseValueInfo) vec.toArray()[0];
            //begin CR25
//            UserIfc userIfc = this.getCurrentUser();
//            if((((WorkableIfc)latestinfo).getWorkableState()).equals("c/o"))
//            {
//                if(userIfc.getUsersName().equals("Administrator") || 
//                        userIfc.getBsoID().equals(((WorkableIfc)latestinfo).getIterationCreator()))
//                {
//                    latestinfo = (BaseValueInfo)CheckInOutCappTaskLogic.getWorkingCopy((WorkableIfc)latestinfo);
//                }
//            }
            //end CR25
            CappTreeObject object = null;
            if (latestinfo instanceof QMTechnicsIfc)
            {
                
                object = new TechnicsTreeObject(latestinfo);
                viewSelectedObject(object);
            }
            else if (latestinfo instanceof QMProcedureIfc)
            {
                if (((QMProcedureIfc) latestinfo).getIsProcedure())
                {
                    object = new StepTreeObject(latestinfo);
                }
                else
                {
                    object = new OperationTreeObject(latestinfo);
                }
                if(((QMProcedureIfc) latestinfo).getOwner() != null)
                {
                    updateSelectedObject();
                }
                else
                {
                	viewSelectedObject(object);
                }
            }

            if (object != null)
            {
                ((ProcessTreeObject) object).setParentID(((ProcessTreeObject)
                        node.getObject()).getParentID());
                view.getProcessTreePanel().updateNode(object);
            }
            view.enableMenuItems(object,
                                 ((Boolean) vec.toArray()[1]).booleanValue());
        }

        view.setCursor(Cursor.getDefaultCursor());
    }
    //end CR12
    //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2253

//  delete by wangh on 20061206(去掉没有引用的方法)
//    /**
//     * 由新节点更换旧节点
//     * @param latestinfo BaseValueInfo 新节点
//     * @param node CappTreeNode 旧节点
//     * @param parentID String 新的工艺主信息的id
//     */
//    private CappTreeObject refreshNode(BaseValueInfo latestinfo,
//                                       CappTreeNode node, CappTreeObject Upper)
//    {
//
//        CappTreeObject object = null;
//        if (latestinfo instanceof QMTechnicsIfc)
//        {
//            object = new TechnicsTreeObject(latestinfo);
//            ((ProcessTreeObject) object).setParentID(latestinfo.getBsoID());
//        }
//        else if (latestinfo instanceof QMProcedureIfc &&
//                 ((QMProcedureIfc) latestinfo).getIsProcedure())
//        {
//            object = new StepTreeObject(latestinfo);
//            ((ProcessTreeObject) object).setParentID(((BaseValueInfo) Upper.
//                    getObject()).getBsoID());
//        }
//        else if (latestinfo instanceof QMProcedureIfc &&
//                 !((QMProcedureIfc) latestinfo).getIsProcedure())
//        {
//            object = new OperationTreeObject(latestinfo);
//            ((ProcessTreeObject) object).setParentID(((BaseValueInfo) Upper.
//                    getObject()).getBsoID());
//        }
//        CappTreeNode newnode = view.getProcessTreePanel().findNode(object);
//        if (newnode != null)
//        {
//            view.getProcessTreePanel().updateNode(object);
//        }
//        else
//        {
//            CappTreeObject upnobj = null;
//            if (object instanceof TechnicsTreeObject)
//            {
//                upnobj = view.getProcessTreePanel().findNode(node.getObject()).
//                         getP().getObject();
//            }
//            else
//            {
//                upnobj = Upper;
//            }
//            view.getProcessTreePanel().addNode(upnobj, object);
//            if (view.getProcessTreePanel().findNode(node.getObject()) != null)
//            {
//                view.getProcessTreePanel().removeNode(node.getObject());
//            }
//        }
//        return object;
//
//    }


    /**
     * 合并工艺规程
     */
    private void collectTechnics()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getContentJPanel().setUniteTechnicsJPanel();
        view.setCursor(Cursor.getDefaultCursor());
    }
    
//CCBegin SS9
    /**
     * 
     * 长特合并工艺规程
     */
    private void ctCollectTechnics()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getContentJPanel().setCTUniteTechnicsJPanel();
        view.setCursor(Cursor.getDefaultCursor());
    }
//CCEnd SS9

    private void viewSelectedObject(CappTreeObject obj)
    {
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setViewMode(2, obj);
            view.setCursor(Cursor.getDefaultCursor());
        }

    }

    
    /**
     * //CCBegin by liunan 2009-09-22 添加查看到瘦客户jsp界面的方法。
     */
    private void viewSelectedObjectJsp()
    {
      CappTreeObject curobj = view.getSelectedNode().getObject();
      BaseValueInfo curinfo = (BaseValueInfo) curobj.getObject();
      //当前节点是工艺规程。
      if (curinfo instanceof QMFawTechnicsInfo)
      {
        QMFawTechnicsInfo curtinfo = (QMFawTechnicsInfo) curinfo;
        String bsoID = curtinfo.getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //“查看工艺规程”的瘦客户页面
        RichToThinUtil.toWebPage("capp_viewTechnics.screen", hashmap);
      }
      //当前节点是工序或者工步。
      if (curinfo instanceof QMProcedureInfo)
      {
        QMProcedureInfo curpinfo = (QMProcedureInfo) curinfo;
        String bsoID = curpinfo.getBsoID();
        CappTreeNode pnode = view.getSelectedNode().getP();
        CappTreeObject pobj = pnode.getObject();
        BaseValueInfo pinfo = (BaseValueInfo) pobj.getObject();
        //父节点是工艺规程，所以当前节点是工序。
        if (pinfo instanceof QMFawTechnicsInfo)
        {
          QMFawTechnicsInfo curpftinfo = (QMFawTechnicsInfo) pinfo;
          HashMap hashmap = new HashMap();
          hashmap.put("bsoID", bsoID);
          hashmap.put("technicsBsoID", curpftinfo.getBsoID());
          hashmap.put("techNumber", curpftinfo.getTechnicsNumber());
          hashmap.put("techVersion", curpftinfo.getVersionValue());
          hashmap.put("techName", curpftinfo.getTechnicsName());
          //“查看工序”的瘦客户页面
          RichToThinUtil.toWebPage("capp_viewProcedure.screen", hashmap);
        }
        //父节点是工序，所以当前节点是工步。
        if (pinfo instanceof QMProcedureInfo)
        {
          CappTreeNode ppnode = pnode.getP();
          CappTreeObject ppobj = ppnode.getObject();
          BaseValueInfo ppinfo = (BaseValueInfo) ppobj.getObject();
          if (ppinfo instanceof QMFawTechnicsInfo)
          {
            QMFawTechnicsInfo curppftinfo = (QMFawTechnicsInfo) ppinfo;
            QMProcedureInfo curppinfo = (QMProcedureInfo) pinfo;
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", bsoID);
            hashmap.put("stepNum", Integer.toString(curppinfo.getStepNumber()));
            hashmap.put("stepName", curppinfo.getStepName());
            hashmap.put("stepVersion", curppinfo.getVersionValue());
            hashmap.put("technicsBsoID", curppftinfo.getBsoID());
            hashmap.put("techNumber", curppftinfo.getTechnicsNumber());
            hashmap.put("techVersion", curppftinfo.getVersionValue());
            hashmap.put("techName", curppftinfo.getTechnicsName());
            //“查看工步”的瘦客户页面
            RichToThinUtil.toWebPage("capp_viewPace.screen", hashmap);
          }
        }
      }
    }
    

    /**
     * 查看
     */
    private void viewSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewSelectedObject() begin...");

        }
        //CCBegin by liunan 2009-09-22 添加查看到瘦客户jsp界面的方法。
        viewSelectedObjectJsp();
        //CCEnd by liunan 2009-09-22
        CappTreeObject obj = view.getSelectedNode().getObject();
        try
        {
            obj = view.update(obj);
        }
        catch (QMRemoteException ex1)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view,
                                          ex1.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;

        }

        viewSelectedObject(obj);

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewSelectedObject() end...return is void");
        }
    }


    /**
     * 更新
     */
    private void updateSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.updateSelectedObject() begin...");
        }
        CappTreeNode selectedNode = view.getSelectedNode();
        CappTreeObject obj = selectedNode.getObject();
        CappTreeNode parentTechNode = view.getParentTechnicsNode(selectedNode);
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {

            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex1)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view,
                                              ex1.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            BaseValueInfo baseValueInfo = (BaseValueInfo) obj.getObject();
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try
            {
                if (baseValueInfo instanceof WorkableIfc)
                {

                    if (CheckInOutCappTaskLogic.isCheckInAndInVault((
                            WorkableIfc)
                            baseValueInfo))

                    {
                        Object[] identity =
                                {
                                getIdentity(baseValueInfo)};
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                "40", identity);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        int i = JOptionPane.showConfirmDialog(view, message,
                                title,
                                JOptionPane.YES_NO_OPTION);
                        if (i != 0)
                        {
                            view.setCursor(Cursor.getDefaultCursor());
                            return;
                        }
                        //检出选中节点及所有上级父节点
//                      CCBegin by leixiao 2009-9-4 原因：解放升级工艺路线,已发布的路线不能检出
                        //CCBegin by liunan 2012-03-06 解放新提需求，特定人已发布状态可以修改，增加组允许改。
                        //if ((baseValueInfo instanceof QMTechnicsIfc)&&(((LifeCycleManagedIfc)baseValueInfo).getLifeCycleState().toString().equals("RELEASED"))) {
                        if ((baseValueInfo instanceof QMTechnicsIfc)&&(((LifeCycleManagedIfc)baseValueInfo).getLifeCycleState().toString().equals("RELEASED"))&&!isCappSupperGroup()) {
                        //CCEnd by liunan 2012-03-06
                            String mm = ((QMTechnicsIfc)baseValueInfo).getTechnicsNumber() + "已发布，不能检出！";
                            JOptionPane.showMessageDialog(view, mm);
                            return ;
                          }
//                      CCEnd by leixiao 2009-9-4 原因：解放升级工艺路线
                        baseValueInfo = checkOutParentNode(selectedNode,
                                parentTechNode);
                       //System.out.println("baseValueInfo="+baseValueInfo);

                    }
                    else
                    if (CheckInOutCappTaskLogic.isCheckedOutByOther((
                            WorkableIfc) baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                    {
                        Object[] objs =
                                {
                                getIdentity(baseValueInfo)};
                        String message1 = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(view,
                                message1,
                                title,
                                JOptionPane.INFORMATION_MESSAGE);
                        view.setCursor(Cursor.getDefaultCursor());
                        return;

                    }
                }
            }
            catch (QMException e)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                view.setCursor(Cursor.getDefaultCursor());
                return;
            }
            view.getContentJPanel().setSelectedObject(baseValueInfo);
            if (obj instanceof TechnicsTreeObject)
            {
                selectedNode = view.findNode(new TechnicsTreeObject(
                        baseValueInfo));
                view.getContentJPanel().setSelectedNode(selectedNode);
                view.getContentJPanel().setTechnicsMode(0);
                view.setMenuAndToolBar(false,true);//CR7
            }
            else if (obj instanceof StepTreeObject)
            {
                selectedNode = view.findNode(new StepTreeObject(baseValueInfo));
                view.getContentJPanel().setSelectedNode(selectedNode);
                view.getContentJPanel().setStepMode(0);
            }
            else if (obj instanceof OperationTreeObject)
            {
                selectedNode = view.findNode(new OperationTreeObject(
                        baseValueInfo));
                view.getContentJPanel().setSelectedNode(selectedNode);
                view.getContentJPanel().setPaceMode(0);
            }
            view.setCursor(Cursor.getDefaultCursor());
            view.refresh();
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.updateSelectedObject() end...return is void");
        }
    }


    /**
     * 删除所选择的对象(将所选择的对象提交服务端删除)
     */
    private void deleteSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteSelectedObject() begin...");

        }
        CappTreeObject treeObj = view.getSelectedNode().getObject();
        if (treeObj != null && treeObj instanceof BusinessTreeObject)
        {
            //提示是否删除
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            //modify by wangh on 20080804
            String message = "";
            if(treeObj instanceof TechnicsTreeObject)
            {message = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "119",
                    null);}
            else{
            message = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    CappLMRB.CONFIRM_DELETE_OBJECT,
                    null);}
            //modify end
            int result = JOptionPane.showConfirmDialog(view, message, title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            //20061201王浩修改，去掉确定删除时多余的判断。
//            switch (result)
//            {
//                case JOptionPane.CLOSED_OPTION:
//                    return;
//                case JOptionPane.CANCEL_OPTION:
//                    return;
//                case JOptionPane.NO_OPTION:
//                    return;
//                case JOptionPane.YES_OPTION:
//                {
            if (result == JOptionPane.YES_OPTION)
            {
                {
                    //view.setCursor(Cursor.getPredefinedCursor(Cursor.
                     //       WAIT_CURSOR));//CR1
                    //20061201王浩修改，去掉对父节点值对象的获得
//          BaseValueInfo fatherInfo =
//              (BaseValueInfo) view.getSelectedNode().getP().
//              getObject().
//              getObject();
                    BaseValueInfo obj;
                    try
                    {
                        treeObj = view.update(treeObj);
                        obj = (BaseValueInfo) treeObj.getObject();
                        //判断是否被他人检出
                        if (CheckInOutCappTaskLogic.isCheckedOutByOther((
                                WorkableIfc)
                                obj))
                        {
                            Object[] objs =
                                    {
                                    getIdentity(obj)};
                            String message1 = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                            title = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    "information", null);
                            JOptionPane.showMessageDialog(view,
                                    message1,
                                    title,
                                    JOptionPane.
                                    INFORMATION_MESSAGE);
                            //20060815薛静修改，将鼠标设成正常状态
                            view.setCursor(Cursor.getDefaultCursor());
                            return;

                        }
                    }
                    catch (QMRemoteException ex2)
                    {
                        title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(view,
                                ex2.getClientMessage(),
                                title,
                                JOptionPane.INFORMATION_MESSAGE);
                        //20060815薛静修改，将鼠标设成正常状态
                        view.setCursor(Cursor.getDefaultCursor());
                        return;

                    }
                    QMTechnicsInfo technics;
                    QMProcedureInfo procedure;
                    if (obj instanceof QMTechnicsInfo)
                    {
                    	technics = (QMTechnicsInfo) obj;
                    	//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
//						vector.add(technics.getBsoID());//Begin CR1
//						view.getProcessTreePanel().removeNode(treeObj);
//						treeObj = new TechnicsTreeObject(
//								technics);
//		
//						 java.net.URL url = TechnicsRegulationsMainJFrame.class.getResource(
//					        "/images/public_deleteObj.gif");
//					   
//						 treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
//						 treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
//						 treeObj.setNoteText("(此工艺正在被删除...)");
//						view.getProcessTreePanel().addProcess(treeObj);// End CR1
                    	//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667

						if (deleteQMTechnics((QMFawTechnicsInfo) technics))
						{
							view.getProcessTreePanel().removeNode(treeObj);//CR1
						}
                    }
                    else if (obj instanceof QMProcedureInfo)
                    {
                        CappTreeNode parentNode = (CappTreeNode) view.
                                                  getParentTechnicsNode();
                        QMTechnicsInfo parentTechnics = (QMTechnicsInfo)
                                parentNode.
                                getObject().getObject();
                        procedure = (QMProcedureInfo) obj;
                        if (deleteQMProcedure(procedure, parentTechnics))
                        {

                            //20061201王浩修改，删除没有用的对象cto
                            /*
                             fatherInfo = CappClientHelper.refresh(
                                    fatherInfo);
                                                             //如果父节点是原本则获得副本
                             if (!CheckInOutCappTaskLogic.isWorkingCopy((
                                    WorkableIfc) fatherInfo)
                                &&
                                CheckInOutCappTaskLogic.isCheckedOut((
                                    WorkableIfc)
                                    fatherInfo))
                                                             {
                                fatherInfo = (BaseValueInfo)
                                             CheckInOutCappTaskLogic.
                                             getWorkingCopy(
                                        (WorkableIfc) fatherInfo);
                                                             }
                             CappTreeObject cto = null;
                             if (fatherInfo instanceof QMTechnicsInfo)
                                                             {
                                cto = new TechnicsTreeObject(fatherInfo);
                                                             }
                             if (fatherInfo instanceof QMProcedureInfo)
                                                             {
                                if (((QMProcedureInfo) fatherInfo).
                                    getIsProcedure())
                                {
                                    cto = new StepTreeObject(fatherInfo);
                                }
                                else
                                {
                                    cto = new OperationTreeObject(
                                            fatherInfo);
                                }
                                                             }*/

                            view.getProcessTreePanel().removeNode(treeObj);
                        }
                    }
                    else
                    {
                        message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                CappLMRB.WRONG_TYPE_OBJECT,
                                null);
                        title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(view, message, title,
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                return;
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteSelectedObject() end...return is void");
        }
    }
    
    
    //CCBegin SS12
    private void findMainStep(){
    	
    	CappTreeObject object = view.getSelectedNode().getObject();
    	
    	if (object != null) {
    		
			if (object instanceof TechnicsTreeObject) {
				
				try {
					object = view.update(object);
				} catch (QMRemoteException ex) {
					String title = QMMessage.getLocalizedMessage(RESOURCE,
							"information", null);
					JOptionPane.showMessageDialog(view, ex.getClientMessage(),
							title, JOptionPane.INFORMATION_MESSAGE);

				}
				
				BaseValueInfo info = (BaseValueInfo) object.getObject();
				QMFawTechnicsIfc technics = (QMFawTechnicsIfc) info;
				
				Class[] paraclass = { String.class ,String.class,Boolean.TYPE};
				Object[] paraobj = { technics.getBsoID(),technics.getBsoID(),true};
				try {
					Vector allStepVec=(Vector)useServiceMethod("StandardCappService",
							"browseProcedures", paraclass,
							paraobj);
					
					if(allStepVec!=null&&allStepVec.size()!=0){
						
						for(int i=0;i<allStepVec.size();i++){
							
							QMProcedureInfo stepInfo=(QMProcedureInfo)allStepVec.get(i);
							
							view.getProcessTreePanel().removeNode(
			                        new StepTreeObject((QMProcedureInfo) stepInfo));
							
							String sType=stepInfo.getStepClassification().getCodeContent();
							
							if(sType.equals("关键工序")){

							  
						       view.addProcedureNode(view.getSelectedNode(), stepInfo);
						       
							  
							}
						}
					}
					
					
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view,
							"查找关键工序时出现错误,请重新检出", "错误提示",
							JOptionPane.INFORMATION_MESSAGE);
					view.stopProgress();
					return;

				}
				
				
			}
			
    	}
    }
    
    //CCEnd SS12

    //CCBegin SS10
    /**
	 * 检出所有工序工步
	 */
	private void checkAllStepAndPace() {
		if (verbose) {
			System.out
					.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() begin...");
		}
		WorkableIfc copyinfo = null;
		boolean flag = true;
		CappTreeObject object = view.getSelectedNode().getObject();
		if (object != null) {
			if (object instanceof TechnicsTreeObject) {
				try {
					object = view.update(object);
				} catch (QMRemoteException ex) {
					String title = QMMessage.getLocalizedMessage(RESOURCE,
							"information", null);
					JOptionPane.showMessageDialog(view, ex.getClientMessage(),
							title, JOptionPane.INFORMATION_MESSAGE);
					// return null;

				}
				BaseValueInfo info = (BaseValueInfo) object.getObject();
				QMFawTechnicsIfc technics = (QMFawTechnicsIfc) info;
				view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				view.startProgress();
				// 获得检出后的对象
				if (technics.getOwner() != null
						&& technics.getOwner().length() > 0) {
					copyinfo = technics;
					flag = false;
					try {
						// 判断工艺是否已经被检出,如果已经被检出,则判断是不是被别人剪除,如果是则提示错误
						if (CheckInOutCappTaskLogic.isCheckedOutByOther(
								(WorkableIfc) technics, view.currentUser)) {
							Object[] objs = { getIdentity(technics) };
							String message1 = QMMessage.getLocalizedMessage(
									RESOURCE,
									CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
							String title = QMMessage.getLocalizedMessage(
									RESOURCE, "information", null);
							JOptionPane.showMessageDialog(view, message1,
									title, JOptionPane.INFORMATION_MESSAGE);
							view.setCursor(Cursor.DEFAULT_CURSOR);
							view.stopProgress();
							return;
						}

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(view, e
								.getLocalizedMessage(), "错误提示",
								JOptionPane.INFORMATION_MESSAGE);
						view.stopProgress();
						return;
					}
				} else {
					copyinfo = getCheckOutObject((WorkableIfc) info);
				}
				Class[] paraclass = { QMFawTechnicsIfc.class };
				Object[] paraobj = { (QMFawTechnicsIfc) copyinfo };
				try {
					useServiceMethod("StandardCappService",
							"checkoutAllStepAndPaceInTechnics", paraclass,
							paraobj);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view,
							"检出工艺下工序及工步是出现错误,请重新检出", "错误提示",
							JOptionPane.INFORMATION_MESSAGE);
					view.stopProgress();
					return;

				}

				view.stopProgress();
				if (copyinfo != null) {
					// 删除原本
					view.removeNode(info);
					if (!flag) {
						Object oo[] = { copyinfo };
						view.addTechnics(oo);
					}
					view.getProcessTreePanel().setNodeSelected(
							view.getProcessTreePanel().findNode(
									new TechnicsTreeObject(
											(BaseValueInfo) copyinfo))
									.getObject());

				}
				view.setCursor(Cursor.getDefaultCursor());
			} else {
				// 提示：对象类型错误
				String message = QMMessage.getLocalizedMessage(RESOURCE,
						CappLMRB.WRONG_TYPE_OBJECT, null);
				String title = QMMessage.getLocalizedMessage(RESOURCE,
						"information", null);
				JOptionPane.showMessageDialog(view, message, title,
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (verbose) {
			System.out
					.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() end...return is void");
		}
		// return copyinfo;

	}
    
    //CCEnd SS10

    /**
     * 删除指定的工艺卡(调用服务)
     * @param info 工艺卡
     */
    private boolean deleteQMTechnics(QMFawTechnicsInfo info)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMTechnics() begin...");
        }
        boolean isDeleted = false;
        if (!(info.equals(null)))
        {
            //如果允许删除，显示是否删除选择框
            if (isDeleteAllowed(info))
            {
            	//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
            	CappTreeObject treeObj = new TechnicsTreeObject(info);//CR9
            	//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                QMTechnicsIfc original = null;
                try
                {
                    //如果选择对象为工作副本，获得原本
                    if (CheckInOutCappTaskLogic.isWorkingCopy((
                            WorkableIfc) info))
                    {
                    	original = (QMFawTechnicsIfc)
                                   CheckInOutCappTaskLogic.
                                   getOriginalCopy(
                                (WorkableIfc) info);
                    }
                    //view.startProgress();//CR1
                    //view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));//CR1
                    //调用Capp服务，删除指定的工艺卡
                  //CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2745  
                 //begin CR11
                    Class[] paraClass=null;
                    Object[] obj=null;
                    if(isDeleteAllUnlinkedVersions.equals("false"))
                    {
                        Class[] paraClass1 ={String.class};
                        Object[] obj1 ={info.getBsoID()};
                        paraClass= paraClass1;  
                        obj=obj1;
                    }else if(isDeleteAllUnlinkedVersions.equals("true"))
                    {
                        Class[] paraClass2 ={QMFawTechnicsIfc.class};
                        Object[] obj2 ={info};
                        paraClass= paraClass2;  
                        obj=obj2;
                    }//end CR11 
                    //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2745
                    //CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
                    vector.add(info.getBsoID());//Begin CR9
					view.getProcessTreePanel().removeNode(treeObj);
					//treeObj = new TechnicsTreeObject( 
					//		info);
					java.net.URL url = TechnicsRegulationsMainJFrame.class.getResource(
				        "/images/public_deleteObj.gif");
					treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setNoteText("(此工艺正在被删除...)");
					view.getProcessTreePanel().addProcess(treeObj);// End CR9
					//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
	//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2745  
		//begin CR11
                    Collection relationTechnics = null;
                    if(isDeleteAllUnlinkedVersions.equals("false"))
                    relationTechnics = (Collection)
                                                  useServiceMethod(
                            "StandardCappService",
                            "deleteQMTechnics", paraClass, obj);//CR10
                    else if(isDeleteAllUnlinkedVersions.equals("true"))
                    relationTechnics = (Collection)
                                                  useServiceMethod(
                            "StandardCappService",
                             "deleteAllUnlinkedQMTechnics", paraClass, obj);
                    if(isDeleteAllUnlinkedVersions.equals("true"))
                    {
                        if(relationTechnics != null && relationTechnics.size()==1)
                        {                            
//                            String message1 = QMMessage.getLocalizedMessage(
//                                    RESOURCE,
//                                    CappLMRB.
//                                    CANNOT_DELETE_FOR_OLDVERSIONS,
//                                    null);
                        	String message1="不是最新版本，不能删除!";
                            String title1 = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                            JOptionPane.showMessageDialog(view, message1,title1,JOptionPane.INFORMATION_MESSAGE);
                            vector.clear();
                            view.setCursor(Cursor.getDefaultCursor());
                            return false;   
                        }
                        else if(relationTechnics != null && relationTechnics.size()>1)
                        {
                            Object[] results = relationTechnics.toArray();
                            Object[] object = new Object[2];
                            Object newTechnics=results[0];
                            Collection collection=(Collection)results[1];
                            Iterator i = collection.iterator();
                            Object[] techAndProcedure=(Object[])i.next();
                            QMTechnicsIfc tech=(QMTechnicsIfc)techAndProcedure[0];
                            QMProcedureIfc procedure=(QMProcedureIfc)techAndProcedure[1];
                            object[0] = getIdentity((QMTechnicsIfc)newTechnics);
                            object[1] = getIdentity(tech)+"的"+getIdentity(procedure);
//                            String message2 = QMMessage.getLocalizedMessage(
//                                    RESOURCE,
//                                    CappLMRB.
//                                    CANNOT_DELETE_FOR_LINKEDVERSIONS,
//                                    object);
                            String message2="此工艺被"+object[1]+"用作关联工艺，不能删除!";
                            String title2 = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                            JOptionPane.showMessageDialog(view, message2,title2,JOptionPane.INFORMATION_MESSAGE);
                            vector.clear();
                            view.getProcessTreePanel().removeNode(treeObj);
                            QMFawTechnicsInfo te = (QMFawTechnicsInfo)newTechnics;
                            treeObj = new TechnicsTreeObject(te);
                            java.net.URL url1 = TechnicsRegulationsMainJFrame.class.getResource(
                                "/images/technics.gif");
                            treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
                            treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
                            treeObj.setNoteText(te.getTechnicsNumber()+"_"+te.getVersionValue()+"_"+DataDisplay.translate(te.getTechnicsName()));
                            view.getProcessTreePanel().addProcess(treeObj);
                            view.setCursor(Cursor.getDefaultCursor());
                            return false;
                        }
                        else if(relationTechnics == null)
                            isDeleted = true;
                    }
                    else if(isDeleteAllUnlinkedVersions.equals("false"))
                    {
                    //end CR11
                    //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2745  
                    //view.stopProgress();//CR1
                    //view.setCursor(Cursor.getDefaultCursor());//CR1
                    //返回值若不为null,说明有关联的工艺卡,提示不能删除
                    if (relationTechnics != null && relationTechnics.size() > 0)
                    {
                        Object[] obj2 = new Object[1];

                        Iterator i = relationTechnics.iterator();
                        //20070111薛静修改，服务端返回的值已改，客户端相应作出修改
                        Object[] techAndProcedure=(Object[])i.next();
                        QMTechnicsIfc tech=(QMTechnicsIfc)techAndProcedure[0];
                        QMProcedureIfc procedure=(QMProcedureIfc)techAndProcedure[1];

                        obj2[0] = getIdentity(tech)+"的"+getIdentity(procedure);
                        String message2 = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                CappLMRB.
                                CANNOT_DELETE_FOR_RELATIONTECHNICS,
                                obj2);
                        String title2 = QMMessage.getLocalizedMessage(
                                RESOURCE, "information", null);
                        JOptionPane.showMessageDialog(view, message2,
                                title2,
                                JOptionPane.INFORMATION_MESSAGE);
                        //CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
                        vector.clear();//begin CR9
                        //view.getProcessTreePanel().removeNode(treeObj);
    					//treeObj = new TechnicsTreeObject( 
    					//		technics);
                        treeObj = new TechnicsTreeObject(info);
    					java.net.URL url1 = TechnicsRegulationsMainJFrame.class.getResource(
    				        "/images/technics.gif");
    					treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
    					treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
    					treeObj.setNoteText(info.getTechnicsNumber()+"_"+info.getVersionValue()+"_"+DataDisplay.translate(info.getTechnicsName()));
    					//view.getProcessTreePanel().addProcess(treeObj);
    					view.getProcessTreePanel().setNodeSelected(treeObj);//end CR9
    					//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
                        return false;
                    }
                    RefreshService.getRefreshService().dispatchRefresh(
                            view, RefreshEvent.DELETE, info);
                    //如果原本不为空，刷新原本为检入状态
                    if (original != null)
                    {
                        original = CappClientHelper.refresh((
                                QMFawTechnicsInfo) original);
                        RefreshService.getRefreshService().
                                dispatchRefresh(
                                view, RefreshEvent.UPDATE,
                                original);
                        original.setWorkableState("c/i");
                        view.updateNode((BaseValueInfo) original, null);
                    }

                    isDeleted = true;
                }
                }//CR11
                catch (QMRemoteException ex)
                {
                    view.stopProgress();
                    String ti = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  ex.getClientMessage(), ti,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    //CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
                    vector.clear();//begin CR9
                    //view.getProcessTreePanel().removeNode(treeObj);
					//treeObj = new TechnicsTreeObject( 
					//		technics);
                    treeObj = new TechnicsTreeObject(info);  
					java.net.URL url = TechnicsRegulationsMainJFrame.class.getResource(
				        "/images/technics.gif");
					treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setNoteText(info.getTechnicsNumber()+"_"+info.getVersionValue()+"_"+DataDisplay.translate(info.getTechnicsName()));
					//view.getProcessTreePanel().addProcess(treeObj);
					view.getProcessTreePanel().setNodeSelected(treeObj);//end CR9
					//CCBeginby liuzc 2009-12-19 原因：没有进行删除操作，但是该工艺图标一直显示为删除状态，参见DefectID=2667
                }

                view.setCursor(Cursor.getDefaultCursor());
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMTechnics() end...return : " +
                               isDeleted);
        }
        return isDeleted;
    }


    /**
     * 删除指定的工序或工步（调用服务）
     * @param info 工序或工步
     * @param parentTechnics 给定工序（步）的父工艺卡
     */
    private boolean deleteQMProcedure(QMProcedureInfo info,
                                      QMTechnicsInfo parentTechnics)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMProcedure() begin...");
        }
        boolean isDeleted = false;
        if (!(info.equals(null)))
        {
            //如果允许删除，显示是否删除选择框
            if (isDeleteAllowed(info))
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                QMProcedureInfo original = null;

                try
                {
                    //如果选择对象为工作副本，获得原本
                    if (CheckInOutCappTaskLogic.isWorkingCopy((
                            WorkableIfc) info))
                    {
                        original = (QMProcedureInfo)
                                   CheckInOutCappTaskLogic.
                                   getOriginalCopy(
                                (WorkableIfc) info);
                    }
                    //刷新并删除info，同时刷新管理器界面
                    view.startProgress();
                    view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    CappTreeNode selNode = view.getSelectedNode();
                    //先将所有处于检入状态的父节点检出
                    checkOutParentNode(selNode.getP(),
                                       view.getParentTechnicsNode(selNode));

                    parentTechnics = (QMTechnicsInfo) CappClientHelper.
                                     refresh(
                            (QMTechnicsInfo) parentTechnics);

                    //如果parentTechnics是原本，则获得副本
                    if ((!CheckInOutCappTaskLogic.isWorkingCopy((
                            WorkableIfc) parentTechnics))
                        &&
                        CheckInOutCappTaskLogic.isCheckedOut(
                            parentTechnics))
                    {
                        parentTechnics = (QMTechnicsInfo)
                                         CheckInOutCappTaskLogic.
                                         getWorkingCopy(
                                (WorkableIfc) parentTechnics);
                    }
                    //调用Capp服务，删除指定的工序（步）
                    Class[] paraClass =
                            {
                            String.class, String.class};
                    Object[] obj =
                            {
                            parentTechnics.getBsoID(), info.getBsoID()};
                    if (info.getIsProcedure())
                    {
                        useServiceMethod("StandardCappService",
                                         "deleteQMProcedure",
                                         paraClass, obj);
                    }
                    else
                    {
                        useServiceMethod("StandardCappService",
                                         "deletePace",
                                         paraClass, obj);

                    }
                    view.stopProgress();
                    view.setCursor(Cursor.getDefaultCursor());
                    RefreshService.getRefreshService().dispatchRefresh(
                            view, RefreshEvent.DELETE, info);
                    //如果原本不为空，刷新原本和管理器界面
                    if (original != null)
                    {
                        original.setWorkableState("c/i");
                        original.setLocker(null);
                        original.setNote(null);
                        original.setDate(null);
                        Class[] cla1 =
                                {
                                BaseValueIfc.class, Boolean.TYPE};
                        Object[] obj1 =
                                {
                                (BaseValueIfc) original, new Boolean(false)};
                        original = (QMProcedureInfo) useServiceMethod(
                                "PersistService", "updateValueInfo",
                                cla1,
                                obj1);
                        view.updateNode(original,
                                        parentTechnics.getBsoID());
                    }
                    isDeleted = true;
                }
                catch (QMException ex)
                {
                    view.stopProgress();
                    String ti = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  ex.getClientMessage(), ti,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMProcedure() end...return: " +
                               isDeleted);
        }
        return isDeleted;
    }


    /**
     * 判断是否允许删除指定的工艺卡(工序或工步)。
     * <p>检查是否具有删除权限</p>
     * <p>检查是否有其它用户更新该工艺规程的主信息、以及工序、工步等</p>
     * @param info 要删除的工艺卡(工序或工步)
     * @return 如果允许删除，则返回true
     */
    private boolean isDeleteAllowed(BaseValueInfo info)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isDeleteAllowed() begin...");
        }
        boolean allowed = false;

        //如果是原本，返回flase
        if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info) &&
            CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) info))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            Object[] obj =
                    {
                    getIdentity(info)};
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.CANNOT_MODIFY_ORIGINAL_OBJECT, obj);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            allowed = false;
        }
        else
        {
            allowed = true;

        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isDeleteAllowed() end...return : " +
                               allowed);
        }
        return allowed;
    }


    /**
     * 判断对象是否允许修改.对象必须是工作副本
     * @param obj
     * @return
     */
    public boolean isUpdateAllowed(FolderedIfc obj)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isUpdateAllowed() begin...");
        }
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        boolean flag = true;
        if ((obj instanceof WorkableIfc)
            && (obj instanceof OwnableIfc)
            && !CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) obj))
        {
            Object object = null;
            try
            {
                object = useServiceMethod("SessionService", "getCurUserID", null, null);
            }
            catch (QMRemoteException ex)
            {
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            try
            {
                flag = OwnershipHelper.isOwnedBy((OwnableIfc) obj,
                                                 (String) object);
            }
            catch (QMException ex)
            {
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isUpdateAllowed() end...return: " +
                               flag);
        }
        return flag;
    }

//  delete by wangh on 20061206(去掉没有引用的方法)
//    /**
//     * 检验给定的资料夹路径是否是个人资料夹路径。
//     * @return 如果给定的资料夹路径是否是个人资料夹路径，返回true
//     * @throws QMRemoteException
//     */
//    private boolean isPersonalLocation(String location)
//            throws
//            QMRemoteException
//    {
//        if (verbose)
//        {
//            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isPersonalLocation() begin...");
//        }
//        SubFolderInfo folderInfo = null;
//        boolean flag = false;
//        if (location != null && location.length() != 0)
//        {
//            //调用资料夹服务，根据路径获得资料夹
//            Class[] paraClass =
//                    {
//                    String.class};
//            Object[] objs =
//                    {
//                    location};
//            try
//            {
//                folderInfo = (SubFolderInfo) useServiceMethod(
//                        "FolderService", "getFolder", paraClass, objs);
//            }
//            catch (QMRemoteException ex)
//            {
//                throw ex;
//            }
//
//        }
//
//        if (folderInfo != null)
//        {
//            //调用资料夹服务，判断指定的文件夹是否是个人文件夹
//            Class[] paraClass2 =
//                    {
//                    FolderIfc.class};
//            Object[] objs2 =
//                    {
//                    folderInfo};
//            Boolean flag1 = null;
//            try
//            {
//                flag1 = (Boolean) useServiceMethod(
//                        "FolderService", "isPersonalFolder", paraClass2, objs2);
//            }
//            catch (QMRemoteException ex)
//            {
//                String title = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.LOCATION_NOT_VALID,
//                        null);
//                JOptionPane.showMessageDialog(view,
//                                              ex.getClientMessage(),
//                                              title,
//                                              JOptionPane.INFORMATION_MESSAGE);
//
//            }
//
//            if (flag1 != null)
//            {
//                flag = flag1.booleanValue();
//            }
//        }
//        if (verbose)
//        {
//            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isPersonalLocation() end...return: " +
//                               flag);
//        }
//        return flag;
//
//    }

    class FormNewStepThread extends Thread
    {
        public void run()
        {
            formNewStepNumber();
        }
    }


    /**
     * 将指定的工艺规程重新生成工序号。
     * <p>业务逻辑详述：如果当前工艺规程在个人资料夹且状态是检入状态,则调用服务</p>
     * <p>重新生成工序号，但不对该工艺规程进行检入检出操作；否则，需要进一步</p>
     * <p>判断该工艺规程是否已被检出。如果该工艺规程处于检出状态，则提示不能进行重新生成工序号</p>
     * <p>操作；如果该工艺规程正处于检入状态，则调用服务执行：检出——重新生成工序号——检入。</p>
     */
    private void formNewStepNumber()
    {

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.formNewStepNumber() begin...");
        }
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {

                //
                BaseValueInfo technics = (BaseValueInfo) obj.getObject();

                if (technics instanceof QMTechnicsInfo)
                {
                    Class[] paraClass =
                            {
                            String.class, String.class};
                    Object[] objs =
                            {
                            technics.getBsoID(), technics.getBsoID()};

                    Collection procedures = (Collection) useServiceMethod(
                            "StandardCappService",
                            "browseProcedures",
                            paraClass, objs);
                   
					Class[] paraClass2 = { String.class };//Begin CR5
					Object[] objs2 = { ((QMTechnicsInfo) technics).getIterationCreator()};
					UserIfc creator = (UserIfc) useServiceMethod(
							"PersistService", "refreshInfo",
							paraClass2, objs2);
					UserIfc userIfc = this.getCurrentUser();//End CR5
					if (procedures != null && procedures.size() > 0)
					{
						for (Iterator it = procedures.iterator(); it.hasNext();)
						{
							//begin CR5
							if (creator.getBsoID().equals(userIfc.getBsoID()) || userIfc.getUsersName().equals("Administrator"))
							{
								break;
							}
							else
							//end CR5
							{
								String title = QMMessage.getLocalizedMessage(
										RESOURCE, "information", null);
								String message = QMMessage.getLocalizedMessage(
										RESOURCE, "120", null);//CR5
								JOptionPane.showMessageDialog(view, message,
										title,

										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
					}
					//Begin CR6
					else
					{
						String title = QMMessage.getLocalizedMessage(
								RESOURCE, "information", null);
						String message = QMMessage.getLocalizedMessage(
								RESOURCE, "122", null);
						JOptionPane.showMessageDialog(view, message,
								title,

								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					//End CR6

                    boolean isInVault = CheckInOutCappTaskLogic.isInVault(
                            (QMTechnicsInfo) technics);
                    view.startProgress();
                    view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //直接调用服务方法实现。
                    String bsoID = technics.getBsoID();
                    Class[] paraClass1 =
                            {
                            String.class, String.class};
                    Object[] objs1 =
                            {
                            bsoID, bsoID};

                    useServiceMethod("StandardCappService",
                                     "updateAllProcedureNumofCard",
                                     paraClass1, objs1);
                    view.stopProgress();

                    if (!isInVault)
                    {
                        TechnicsTreeObject object = new TechnicsTreeObject(
                                technics);
                        view.getProcessTreePanel().removeNode(object);
                        view.getProcessTreePanel().addProcess(object);
                        CappTreeNode node = view.findNode(object);
                        view.getProcessTreePanel().nodeExpaned(node);

                    }

                    else if (((QMTechnicsInfo) technics).getWorkableState().
                             equals(
                            "c/i") &&
                             isInVault)
                    {
                        TechnicsTreeObject object = new TechnicsTreeObject(
                                technics);
                        view.getProcessTreePanel().addProcess(object);
                        CappTreeNode node = view.findNode(object);
                        view.getProcessTreePanel().nodeExpaned(node);
                    }

                    view.refresh();
                }

                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        catch (QMRemoteException e)
        {
            view.stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.formNewStepNumber() end...return is void");
        }
    }
    
    //CR3 begin
	class FormNewPaceThread extends Thread
	{
		public void run()
		{
			formNewPaceNumber();
		}
	}
	
    /**
	 * 将指定的工艺规程重新生成工步号。
	 */
	private void formNewPaceNumber(){

		try
		{
			CappTreeObject obj = view.getSelectedNode().getObject();
			if ((obj != null) && (obj instanceof StepTreeObject))
			{
				QMProcedureInfo procedure = (QMProcedureInfo) obj.getObject();
				QMFawTechnicsInfo technics = (QMFawTechnicsInfo)view.getSelectedNode().getP().getObject().getObject();
				boolean isInVault = CheckInOutCappTaskLogic
							.isInVault((QMTechnicsInfo) technics);
					view.startProgress();
					view.setCursor(Cursor.WAIT_CURSOR);
					Class[] paraClass1 = { String.class, String.class };
					Object[] objs1 = { technics.getBsoID(), procedure.getBsoID() };
					useServiceMethod("StandardCappService",
							"FormNewPaceNum", paraClass1, objs1);
					view.stopProgress();
                    //个人资料夹
					if (!isInVault)
					{
						if(procedure.getIsProcedure()){
							StepTreeObject object = new StepTreeObject(procedure);	
							CappTreeObject techObj = view.getSelectedNode().getP().getObject();
							view.getProcessTreePanel().removeNode(object);
							view.getProcessTreePanel().addNode(techObj, object);
							CappTreeNode node = view.findNode(object);
							
							object.setParentID(technics.getBsoID());
							view.getProcessTreePanel().setNodeSelected(object);
							view.getProcessTreePanel().nodeExpaned(node);							
						}
						/*else{
							OperationTreeObject object = new OperationTreeObject(procedure);
							view.getProcessTreePanel().removeNode(object);
							view.getProcessTreePanel().addProcess(object);
							CappTreeNode node = view.findNode(object);
							view.getProcessTreePanel().nodeExpaned(node);	
						}*/
						
					}
                    //公共资料夹
					else if (procedure.getWorkableState()
							.equals("c/i")&& isInVault)
					{
						if(procedure.getIsProcedure()){
							StepTreeObject object = new StepTreeObject(procedure);	
							CappTreeObject techObj = view.getSelectedNode().getP().getObject();
							view.getProcessTreePanel().removeNode(object);
							view.getProcessTreePanel().addNode(techObj, object);
							CappTreeNode node = view.findNode(object);
							
							object.setParentID(technics.getBsoID());
							view.getProcessTreePanel().setNodeSelected(object);
							view.getProcessTreePanel().nodeExpaned(node);						
						}
						/*else{
							OperationTreeObject object = new OperationTreeObject(procedure);
							view.getProcessTreePanel().addProcess(object);
							CappTreeNode node = view.findNode(object);
							view.getProcessTreePanel().nodeExpaned(node);
						}*/
					}
				view.refresh();
				view.setCursor(Cursor.getDefaultCursor());
			}
		}
		catch (QMRemoteException e)
		{
			view.stopProgress();
			String title = QMMessage.getLocalizedMessage(RESOURCE,
					"information", null);
			JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
    //CR3 end





    /**
     * 打印预览指定的对象
     */
    private void browsePrintObject()throws QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.browsePrintObject() begin...");
        }
        try
        {
            CappTreeNode node = view.getSelectedNode();
            CappTreeObject obj = node.getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                obj = view.update(obj);                
                //CCBegin SS6
                view.setViewMode(view.getContentJPanel().getMode(), obj);
                //CCEnd SS6
                Class[] paramclass = new Class[]
                                     {
                                     String.class, String.class};
                Object[] paramobject = new Object[]
                                       {
                                       "模板类型", "模板"};
                Collection c = (Collection) TechnicsAction.useServiceMethod(
                        "CodingManageService", "findDirectClassificationByName",
                        paramclass, paramobject);
                if (c != null)
                {
                    Vector vv = new Vector(c);
                    if (c.size() >= 1)
                     {

                        view.setCursor(Cursor.getPredefinedCursor(Cursor.
                                WAIT_CURSOR));
                        BaseValueInfo info = (BaseValueInfo) obj.getObject();
                       // TemplateSelectJDialog d = new TemplateSelectJDialog(
                        //CCBegin by liunan 2011-07-15 判断工艺类型选择默认纸张大小。
                                //view);
                           //     view,info);
                        //CCEnd by liunan 2011-07-15
                        //d.setObject(info);
                       // d.setVisible(true);
                        //view.setCursor(Cursor.getDefaultCursor());
                        // CCBegin SS2
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
                	        //通过请求服务器的工厂获得server
                	        server = RequestServerFactory.getRequestServer();
                		
                			yy = (Boolean) server.request(in);
                		} catch (QMRemoteException e) {
                			e.printStackTrace();
                		}
      
                        if (yy)
                        {
                        	ConsTemplateSelectJDialog d = new ConsTemplateSelectJDialog(view);      
                            d.setObject(info);
                            d.setVisible(true);
                            view.setCursor(Cursor.getDefaultCursor());
                        }
                        else
                        {
                        	TemplateSelectJDialog d = new TemplateSelectJDialog(
                                    //CCBegin by liunan 2011-07-15 判断工艺类型选择默认纸张大小。
                                            //view);
                                            view,info);
                       	 //CCEnd by liunan 2011-07-15
                            d.setObject(info);
                            d.setVisible(true);
                            view.setCursor(Cursor.getDefaultCursor());
                        	
                        }
                            
                       //CCend SS2
                    }
                    else if (c.size() == 1)
                    {
                        view.setCursor(Cursor.getPredefinedCursor(Cursor.
                                WAIT_CURSOR));
                        BaseValueInfo info = (BaseValueInfo) obj.getObject();
                        TPrintController tp = new TPrintController();
                        tp.setTreeNode(node);
                        tp.setMainFrame(view);
                        tp.setObject(info);
                        Vector v = new Vector();
                        v.add(vv.get(0).toString());
                        tp.setTemplateType(v);
                        tp.print();
                        view.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.browsePrintObject() end...return is void");
        }
    }


    /**
     * 更改工艺卡的存储位置
     */
    private void changeTechnicsLocation()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.changeTechnicsLocation() begin...");
        }
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                obj = view.update(obj);
                BaseValueInfo info = (BaseValueInfo) obj.getObject();
                if (info instanceof QMTechnicsInfo)
                {
                    QMTechnicsInfo technics = (QMTechnicsInfo) info;

                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    String message = "";
                    //如果当前对象已被检出，或不在公共资料夹，则不允许更改存储位置
//                    if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)
//                            technics))
                    if (!technics.getWorkableState().equals("c/i"))
                    {
                        Object[] objs =
                                {
                                getIdentity(technics)};
                        message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.
                                CANNOT_CHANGE_LOCATION_CHECK_OUT_OBJECT,
                                objs);
                        JOptionPane.showMessageDialog(view, message, title,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    else if (!CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                            technics))
                    {
                        Object[] objs =
                                {
                                getIdentity(technics)};
                        message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.CANNOT_CHANGE_LOCATION_INVAULT_OBJECT,
                                objs);
                        JOptionPane.showMessageDialog(view, message, title,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    view.setCursor(Cursor.getPredefinedCursor(Cursor.
                            WAIT_CURSOR));
                    TechnicsChangeLocationJFrame frame =
                            new TechnicsChangeLocationJFrame(technics, view);
                    frame.setVisible(true);
                    view.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.changeTechnicsLocation() end...return is void");
        }
    }


    /**
     * 工艺卡重命名
     */
    private void renameTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.renameTechnics() begin...");
        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo info = (BaseValueInfo) obj.getObject();
            if (info instanceof QMTechnicsInfo)
            {
                QMTechnicsInfo technics = (QMTechnicsInfo) info;
                TechnicsReguRenameJDialog dialog = new
                        TechnicsReguRenameJDialog(
                        view, "", true);
                dialog.setTechnicsObject(technics);
                dialog.setVisible(true);
            }
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.renameTechnics() end...return is void");
        }
    }


    /**
     * 工艺卡另存为
     */
    private void saveAsTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.saveAsTechnics() begin...");

        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setViewMode(3, obj);
            view.setCursor(Cursor.getDefaultCursor());
            view.refresh();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.saveAsTechnics() end...return is void");
        }
    }


    /**
     * 应用典型工艺(由典型工艺生成工艺规程)
     */
    private void usageModelTechnics()
    {
        //使用典型工艺搜索用例选择搜索条件并搜索出结果，在结果中选择一个典型工艺
        //如果执行者对工序无创建权限或当前工艺规程主信息已经被检出，不允许修改工艺规程中的任何信息。
        //将选择的典型工艺复制到当前的工艺中，复制内容包括工艺规程的多有工序及下级节点信息
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();

            if (obj != null && (obj instanceof TechnicsTreeObject))
            {
                obj = view.update(obj);
                BaseValueInfo baseValueInfo = (BaseValueInfo) obj.getObject();
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    Object[] objs =
                            {
                            getIdentity(baseValueInfo)};
                    String message1 = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  message1,
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
                if (CheckInOutCappTaskLogic.isCheckedOutByUser((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    //如果是被当前用户检出但不是工作副本,就获得工作副本
                    if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)
                            baseValueInfo))
                    {
                        baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                                        getWorkingCopy(
                                (WorkableIfc) baseValueInfo);
                    }
                }
                // 如果在公共资料夹,则检出当前节点
                else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                        baseValueInfo))
                {
                    Object[] identity =
                            {
                            getIdentity(baseValueInfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        return;
                    }

                    //检出选中节点及所有上级父节点
                    checkOut();
                    baseValueInfo = CappClientHelper.refresh(baseValueInfo);
                    baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                                    getWorkingCopy((WorkableIfc) baseValueInfo);
                    view.getProcessTreePanel().setNodeSelected(new
                            TechnicsTreeObject
                            (baseValueInfo));
                }
                final TSearchMTechnicsJDialog d = new TSearchMTechnicsJDialog(
                        view,
                        ((QMTechnicsIfc) baseValueInfo).getTechnicsType().
                        getCodeContent());
                d.addMultiListActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        CappMultiList c = (CappMultiList) e.getSource();
                        Object[] bvi = c.getSelectedObjects();
                        d.usageModelTechnics((BaseValueInfo) bvi[0]);
                        d.setVisible(false);
                    }
                });
                d.setObject((QMTechnicsInfo) baseValueInfo);
                d.setVisible(true);
                view.setCursor(Cursor.getDefaultCursor());

            }
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 应用典型工序(由典型工序生成工序)
     */
    private void usageModelProcedure()
    {
        //使用典型工艺搜索用例选择搜索条件并搜索出结果，在结果中选择一个典型工序
        //如果执行者对工序无创建权限或当前工艺规程主信息已经被检出，不允许修改工艺规程中的任何信息。
        //将选择的典型工序复制并在选择的工艺主信息下创建一个与典型工序相同的工序，此工序在其它本级所有工序的最后面
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();
            if (obj != null && (obj instanceof TechnicsTreeObject))
            {
                obj = view.update(obj);
                BaseValueInfo baseValueInfo = (BaseValueInfo) obj.getObject();
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    Object[] objs =
                            {
                            getIdentity(baseValueInfo)};
                    String message1 = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  message1,
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);

                    return;
                }

                if (CheckInOutCappTaskLogic.isCheckedOutByUser((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    //如果是被当前用户检出但不是工作副本,就获得工作副本
                    if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)
                            baseValueInfo))
                    {
                        baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                                        getWorkingCopy(
                                (WorkableIfc) baseValueInfo);
                    }
                }
                // 如果在公共资料夹,则检出当前节点
                else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                        baseValueInfo))
                {
                    Object[] identity =
                            {
                            getIdentity(baseValueInfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        return;
                    }
                    baseValueInfo = CappClientHelper.refresh(baseValueInfo);
                    //检出选中节点
                    baseValueInfo = (BaseValueInfo) checkOut();

                    //baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                    //                getWorkingCopy((WorkableIfc) baseValueInfo);
                    view.getProcessTreePanel().setNodeSelected(new
                            TechnicsTreeObject
                            (baseValueInfo));
                }
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                String technicsType = ((QMTechnicsIfc) baseValueInfo).
                                      getTechnicsType().getCodeContent();
                int i = technicsType.indexOf("工艺");
                if (i != -1)
                {
                    technicsType = technicsType.substring(0, i);
                }
                technicsType = technicsType + "工序";
                TSearchMProcedureJDialog d = new TSearchMProcedureJDialog(view,
                        technicsType);
                d.setObject((QMTechnicsInfo) baseValueInfo);
                d.setVisible(true);
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        catch (QMException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }


    /**
     * 生成典型工艺
     */
    private void buildModelTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelTechnics() begin...");

        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setModelTechnicsViewMode();
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelTechnics() end...return is void");
        }
    }


    /**
     * 生成典型工序
     */
    private void buildModelProcedure()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelProcedure() begin...");

        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setModelTechnicsViewMode();
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelProcedure() end...return is void");
        }
    }


    /**
     * 复制.
     * 由于可能出现同一个业务对象的原本和副本被同时复制的情况，故限定原本不允许复制。
     */
    private void copy()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.controller.TechnicsRegulationsMainController.copy() begin...");
        }
        //问题（6）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
        //鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
        CappTreeNode[]  nodes = view.getSelectedNodes();
        CappTreeObject object ;
    	BaseValueInfo info ;
    	//20081218  徐春英修改     此变量用于存放复制节点的信息
    	Object[]  arrays;
        for(int i = 0; i < nodes.length; i++)
        {
	        object = nodes[i].getObject();
	        if ((object != null) && (object instanceof BusinessTreeObject))
	        {
	            try
	            {
	                object = view.update(object);
	            }
	            catch (QMRemoteException ex)
	            {
	                String title = QMMessage.getLocalizedMessage(RESOURCE,
	                        "information", null);
	                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
	                                              title,
	                                              JOptionPane.INFORMATION_MESSAGE);
	                return;
	            }
	            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	            info = (BaseValueInfo) object.getObject();
	            //原本不允许复制
	            if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) info)
	                && !CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info))
	            {
	                Object[] obj =
	                        {
	                        getIdentity(info)};
	                String title = QMMessage.getLocalizedMessage(RESOURCE,
	                        "information", null);
	                String message = QMMessage.getLocalizedMessage(RESOURCE,
	                        CappLMRB.CANNOT_COPY_ORIGINAL_OBJECT, obj);
	                JOptionPane.showMessageDialog(view, message, title,
	                                              JOptionPane.INFORMATION_MESSAGE);
	                view.setCursor(Cursor.getDefaultCursor());
	                return;
	            }
	
	            //复制工艺卡
	            if (object instanceof TechnicsTreeObject)
	            {
	            	arrays = new Object[3];
	                arrays[0] = (QMTechnicsInfo) info;
	                arrays[1] = object;
	                arrays[2] = ((TechnicsTreeObject)object).getParentID();
	                list.add(arrays);
	                
	            }
	
	            //复制工序
	            if (object instanceof StepTreeObject)
	            {
	                //copyOriginalInfo = (QMProcedureInfo) info;
	                
	                //copyOriginalTreeObject = object;
	                //oriTechnicsID = ((StepTreeObject)object).getParentID();
	            	arrays = new Object[3];
	                arrays[0] = (QMProcedureInfo) info;
	                arrays[1] = object;
	                arrays[2] = ((StepTreeObject)object).getParentID();
	                list.add(arrays);
	            }
	            
	            //复制工步
	            if (object instanceof OperationTreeObject)
	            {
	            	arrays = new Object[3];
	                arrays[0] = (QMProcedureInfo) info;
	                arrays[1] = object;
	                arrays[2] = ((OperationTreeObject)object).getParentID();
	                list.add(arrays);
	            }
	
	            view.setCursor(Cursor.getDefaultCursor());
	        }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.copy() end...return is void");
        }
    }


    /**
     * 粘贴
     * 问题（6）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
     * 鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
     */
    private void paste()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.controller.TechnicsRegulationsMainController.paste() begin...");

        }
        //问题（6）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
        //鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
    	BaseValueInfo  copyOriginalInfo = null;
    	CappTreeObject  copyOriginalTreeObject = null;
    	String  oriTechnicsID = "";
    	
        long d1 = System.currentTimeMillis();
        CappTreeNode selectedNode = view.getSelectedNode();
        CappTreeObject object = selectedNode.getObject();
        if ((object != null) && (object instanceof BusinessTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex2)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex2.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            //begin CR8
            BusinessTreeObject selectedObject = (BusinessTreeObject) object;
            if(selectedObject.getObject() instanceof QMProcedureInfo){             
                QMProcedureInfo procedureinfo = (QMProcedureInfo)selectedObject.getObject();
                //如果有关联工艺，则不允许在该工序下粘贴工步
                if (procedureinfo.getRelationCardBsoID() != null
                    && !(procedureinfo.getRelationCardBsoID()).equals(""))
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.CAN_NOT_CREATE_PACE, null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, message, title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return;
                }
            }
           //end CR8

            CappTreeNode parentTechnicsNode = view.getParentTechnicsNode();
            for(int m = 0, n = list.size(); m < n; m++)
            {
            	Object[]  obj1 = (Object[])list.get(m);
            	copyOriginalTreeObject = (CappTreeObject)obj1[1];
	            if (parentTechnicsNode == null || copyOriginalTreeObject == null)
	            {
	                return;
	            }
            }
            //获得目标节点的父工艺卡
            QMTechnicsInfo parentTechnics =
                    (QMTechnicsInfo) parentTechnicsNode.getObject().getObject();
//          delete by wangh on 20061206(去掉重复的代码段)

//            try
//            {
//                object = view.update(object);
//            }
//            catch (QMRemoteException ex1)
//            {
//                String title = QMMessage.getLocalizedMessage(RESOURCE,
//                        "information", null);
//                JOptionPane.showMessageDialog(view, ex1.getClientMessage(),
//                                              title,
//                                              JOptionPane.INFORMATION_MESSAGE);
//                return;
//            }
            BaseValueInfo bvi = (BaseValueInfo) object.getObject();

            try
            {
            	
                //如果目标节点被别人检出了
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)
                        bvi))
                {
                    Object[] objs =
                            {
                            getIdentity(bvi)};
                    String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    throw new QMRemoteException(message1);
                }
                // progressDialog.show();

                //复制工艺卡
                //问题（6）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
                //鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
                if (object instanceof TechnicsTreeObject)
                {
                	for(int j = 0, k = list.size(); j < k; j++)
                	{
                		Object[]  obj = (Object[])list.get(j);
                		copyOriginalInfo = (BaseValueInfo)obj[0];
                		copyOriginalTreeObject = (CappTreeObject)obj[1];
                		oriTechnicsID = (String)obj[2];
                		//源节点：工艺卡 ； 目标节点：工艺卡
                        if (copyOriginalTreeObject instanceof
                            TechnicsTreeObject)
                        {
                            boolean flag = CappClientHelper.isTypeSame((
                                    QMTechnicsInfo)
                                    copyOriginalTreeObject.getObject(),
                                    (QMTechnicsInfo) object.getObject());
                            if (!flag)
                            {
                                throwPasteNotAllowedException();
                            }
                            //modify by wangh on 20061205(修改进度条启始时间不正确)
                            //view.startProgress();
                            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            //如果目标节点处于检入状态，则执行检出
                            if (CheckInOutCappTaskLogic.isCheckInAndInVault((
                                    WorkableIfc)
                                    bvi))
                            {
                                Object[] identity =
                                        {
                                        getIdentity(bvi)};
                                String message = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        "40", identity);
                                String title = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        "information", null);
                                int i = JOptionPane.showConfirmDialog(view, message,
                                        title,
                                        JOptionPane.YES_NO_OPTION);
                                if (i != 0)
                                {
                                    //view.stopProgress();
                                    view.setCursor(Cursor.getDefaultCursor());
                                    return;
                                }
                                view.startProgress();
                                //检出节点本身及父节点
                                bvi = checkOutParentNode(selectedNode,
                                        parentTechnicsNode);

                            }
                            QMTechnicsInfo technicsObj = (QMTechnicsInfo) bvi;
                            TechnicsCopyController c = new TechnicsCopyController();
                            c.setOriginalTechnics((QMTechnicsInfo)
                                                  copyOriginalInfo);
                            c.setObjectTechnics(technicsObj);
                            c.copy();
                            //刷新目标节点
                            CappTreeNode node = view.getProcessTreePanel().findNode(new
                                    TechnicsTreeObject(technicsObj));
                            view.getProcessTreePanel().nodeExpaned(node);
                            view.stopProgress();
                            view.setCursor(Cursor.getDefaultCursor());
                        }
                        //源节点：工序 ； 目标节点：工艺卡
                        else if (copyOriginalTreeObject instanceof
                                 StepTreeObject)
                        {
                            boolean flag = CappClientHelper.isTypeSame((
                                    QMTechnicsInfo)
                                    CappClientHelper.refresh(oriTechnicsID),
                                    (QMTechnicsInfo) object.getObject());
                            if (!flag)
                            {
                                throwPasteNotAllowedException();
                            }
                            view.startProgress();
                            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            //如果目标节点处于检入状态，则执行检出
                            if (CheckInOutCappTaskLogic.isCheckInAndInVault((
                                    WorkableIfc)
                                    bvi))
                            {

                                //检出节点本身及父节点
                                bvi = checkOutParentNode(selectedNode,
                                        parentTechnicsNode);
                                parentTechnics = (QMTechnicsInfo)
                                                 CheckInOutCappTaskLogic.
                                                 getWorkingCopy((WorkableIfc)
                                        parentTechnics);

                            }

                            QMTechnicsInfo procedureObj = (QMTechnicsInfo) bvi;
                            ProcedureCopyController c = new ProcedureCopyController();
                            c.setOriginalProcedure((QMProcedureInfo)
                                                   copyOriginalInfo);
                            c.setObjectProcedure(procedureObj);
                            c.setOriginalTechnicsID(oriTechnicsID);
                            //设置目标对象的父工艺卡
                            c.setParentTechnics(parentTechnics);
                            c.copyProcedureToTech();
                            //刷新目标节点
                            CappTreeNode node = view.getProcessTreePanel().findNode(new
                                    TechnicsTreeObject(bvi));
                            view.getProcessTreePanel().nodeExpaned(node);
                            view.setCursor(Cursor.getDefaultCursor());
                            //view.updateNode(procedureObj, null);
                        }
                			
                        else
                        {
                            //复制的节点信息与目标节点内容不一致
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
                            throw new QMRemoteException(message);

                        }
                	}
               
                }
                //复制工序
                else if (object instanceof StepTreeObject)
                {
                	for(int j = 0, k = list.size(); j < k; j++)
                	{
	                	Object[]  obj = (Object[])list.get(j);
	                	copyOriginalInfo = (BaseValueInfo)obj[0];
	                	System.out.println("粘贴时的原点"+copyOriginalInfo);
	                	copyOriginalTreeObject = (CappTreeObject)obj[1];
	                	oriTechnicsID = (String)obj[2];
	                    //源节点：工序； 目标节点：工序
	                    if (copyOriginalTreeObject instanceof StepTreeObject)
	                    {
	                        boolean flag = CappClientHelper.isTypeSame((
	                                QMProcedureInfo)
	                                copyOriginalTreeObject.getObject(),
	                                (QMProcedureInfo) object.getObject());
	                        if (!flag)
	                        {
	                            throwPasteNotAllowedException();
	                        }
	                        view.startProgress();
	                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	                        //如果目标节点处于检入状态，则执行检出
	                        if (CheckInOutCappTaskLogic.isCheckInAndInVault((
	                                WorkableIfc) bvi))
	                        {
	
	                            //检出节点本身及父节点
	                            bvi = checkOutParentNode(selectedNode,
	                                    parentTechnicsNode);
	                            parentTechnics = (QMTechnicsInfo)
	                                             CheckInOutCappTaskLogic.
	                                             getWorkingCopy((WorkableIfc)
	                                    parentTechnics);
	
	                        }
	
	                        QMProcedureInfo procedureObj = (QMProcedureInfo) bvi;
	                        copyOriginalInfo = CappClientHelper.refresh(
	                                copyOriginalInfo);
	                        StepCopyController c = new StepCopyController();
	                        //如果源节点是原本，则获得副本（只有在自身复制时源节点才可能是原本）,与目标节点比较
	                        if (((QMProcedureInfo) copyOriginalInfo).
	                            getWorkableState().equals("c/o"))
	                        {
	                            copyOriginalInfo = (QMProcedureInfo)
	                                               CheckInOutCappTaskLogic.
	                                               getWorkingCopy
	                                               ((WorkableIfc)
	                                                copyOriginalInfo);
	                        }
	                        //源节点的副本与目标节点一致
	                        if (copyOriginalInfo.getBsoID().equals(procedureObj.
	                                getBsoID()))
	                        {
	                            c.setOriginalProcedure(procedureObj);
	                            c.setObjectProcedure(procedureObj);
	                            //设置源节点的父工艺卡ID（由于源节点已经检出，故此处设为工艺卡的副本）
	                            c.setOriginalTechnicsID(parentTechnics.getBsoID());
	                            //设置目标对象的父工艺卡
	                            c.setParentTechnics(parentTechnics);
	                            c.copy();
	                        }
	                        else
	                        {
	                            c.setOriginalProcedure((QMProcedureInfo)
	                                    copyOriginalInfo);
	                            c.setObjectProcedure(procedureObj);
	                            //设置源节点的父工艺卡ID（由于源节点已经检出，故此处设为工艺卡的副本）
	                            c.setOriginalTechnicsID(oriTechnicsID);
	                            //设置目标对象的父工艺卡
	                            c.setParentTechnics(parentTechnics);
	                            c.copy();
	                        }
	                        //刷新目标节点
	                        CappTreeNode node = view.getProcessTreePanel().findNode(new
	                                StepTreeObject(procedureObj));
	                        view.getProcessTreePanel().nodeExpaned(node);
	                        view.setCursor(Cursor.getDefaultCursor());
	                        // view.updateNode(procedureObj, parentTechnics.getBsoID());
	                    }
	                    //源节点：工步 ； 目标节点：工序
	                    else if (copyOriginalTreeObject instanceof
	                             OperationTreeObject)
	                    {
	                        boolean flag = CappClientHelper.isTypeSame((
	                                QMProcedureInfo)
	                                copyOriginalTreeObject.getObject(),
	                                (QMProcedureInfo) object.getObject());
	                        if (!flag)
	                        {
	                            throwPasteNotAllowedException();
	                        }
	                        view.startProgress();
	                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	                        //如果目标节点处于检入状态，则执行检出
	                        if (CheckInOutCappTaskLogic.isCheckInAndInVault((
	                                WorkableIfc) bvi))
	                        {
	
	                            //检出节点本身及父节点
	                            bvi = checkOutParentNode(selectedNode,
	                                    parentTechnicsNode);
	                            parentTechnics = (QMTechnicsInfo)
	                                             CheckInOutCappTaskLogic.
	                                             getWorkingCopy((WorkableIfc)
	                                    parentTechnics);
	
	                        }
	
	                        QMProcedureInfo procedureObj = (QMProcedureInfo) bvi;
	                        ProcedureCopyController c = new ProcedureCopyController();
	                        c.setOriginalProcedure((QMProcedureInfo)
	                                               copyOriginalInfo);
	                        c.setObjectProcedure(procedureObj);
	
	                        //设置源节点的父工艺卡ID
	                        c.setOriginalTechnicsID(oriTechnicsID);
	                        //设置目标对象的父工艺卡
	                        c.setParentTechnics(parentTechnics);
	                        c.copyProcedureToProcedure();
	
	                        //刷新目标节点
	                        CappTreeNode node = view.getProcessTreePanel().findNode(new
	                                StepTreeObject(procedureObj));
	                        view.getProcessTreePanel().nodeExpaned(node);
	                        view.setCursor(Cursor.getDefaultCursor());
	
	                    }
	                    else
	                    {
	                        //复制的节点信息与目标节点内容不一致
	                        String message = QMMessage.getLocalizedMessage(
	                                RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
	                        throw new QMRemoteException(message);
	
	                    }
	
	                }
                }

                //复制工步
                else if (object instanceof OperationTreeObject)
                {
	                for(int j = 0, k = list.size(); j < k; j++)
	                {
		                Object[]  obj = (Object[])list.get(j);
		                copyOriginalInfo = (BaseValueInfo)obj[0];
		                System.out.println("粘贴时的原点"+copyOriginalInfo);
		                copyOriginalTreeObject = (CappTreeObject)obj[1];
		                oriTechnicsID = (String)obj[2];
	                    if (copyOriginalTreeObject instanceof
	                        OperationTreeObject)
	                    {
	                        boolean flag = CappClientHelper.isTypeSame((
	                                QMProcedureInfo)
	                                copyOriginalTreeObject.getObject(),
	                                (QMProcedureInfo) object.getObject());
	                        if (!flag)
	                        {
	                            throwPasteNotAllowedException();
	                        }
	                        view.startProgress();
	                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	                        //如果目标节点处于检入状态，则执行检出
	                        if (CheckInOutCappTaskLogic.isCheckInAndInVault((
	                                WorkableIfc) bvi))
	                        {
	
	                            //检出节点本身及父节点
	                            bvi = checkOutParentNode(selectedNode,
	                                    parentTechnicsNode);
	                            parentTechnics = (QMTechnicsInfo)
	                                             CheckInOutCappTaskLogic.
	                                             getWorkingCopy((WorkableIfc)
	                                    parentTechnics);
	
	                        }
	
	                        QMProcedureInfo procedureObj = (QMProcedureInfo) bvi;
	                        ProcedureCopyController c = new ProcedureCopyController();
	                        //如果源节点是原本，则获得副本（只有在自身复制时源节点才可能是原本）
	                        if (((QMProcedureInfo) copyOriginalInfo).
	                            getWorkableState().equals("c/o"))
	                        {
	
	                            copyOriginalInfo = (QMProcedureInfo)
	                                               CheckInOutCappTaskLogic.
	                                               getWorkingCopy
	                                               ((WorkableIfc)
	                                                copyOriginalInfo);
	                            //源节点的副本与目标节点一致
	                        }
	                        if (copyOriginalInfo.getBsoID().equals(procedureObj.
	                                getBsoID()))
	                        {
	                            c.setOriginalProcedure(procedureObj);
	                            c.setObjectProcedure(procedureObj);
	                            //设置源节点的父工艺卡ID（由于源节点已经检出，故此处设为工艺卡的副本）
	                            c.setOriginalTechnicsID(parentTechnics.getBsoID());
	                            //设置目标对象的父工艺卡
	                            c.setParentTechnics(parentTechnics);
	                            c.copyProcedureToProcedure();
	                        }
	                        else
	                        {
	                            c.setOriginalProcedure((QMProcedureInfo)
	                                    copyOriginalInfo);
	                            c.setObjectProcedure(procedureObj);
	                            //设置源节点的父工艺卡ID
	                            c.setOriginalTechnicsID(oriTechnicsID);
	                            //设置目标对象的父工艺卡
	                            c.setParentTechnics(parentTechnics);
	                            c.copyProcedureToProcedure();
	                        }
	                        //刷新目标节点
	                        CappTreeNode node = view.getProcessTreePanel().findNode(new
	                                StepTreeObject(procedureObj));
	                        view.getProcessTreePanel().nodeExpaned(node);
	                        view.setCursor(Cursor.getDefaultCursor());
	                        // view.updateNode(procedureObj, parentTechnics.getBsoID());
	
	                    }
	                    else
	                    {
	                        //复制的节点信息与目标节点内容不一致
	                        String message = QMMessage.getLocalizedMessage(
	                                RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
	                        throw new QMRemoteException(message);
	
	                    }
	
	                  }
	                }
	                else
	                {
	                    //复制的节点信息与目标节点内容不一致
	                    String message = QMMessage.getLocalizedMessage(
	                            RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
	                    throw new QMRemoteException(message);
	
	                }

            }
            catch (QMException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = ex.getClientMessage();
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());

            }
            finally
            {
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());
            }

        }
        list.clear();
        long d2 = System.currentTimeMillis();
        System.out.println("粘贴时间=" + (d2 - d1));
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.paste() end...return is void");
        }

    }

    private void throwPasteNotAllowedException()
            throws QMRemoteException
    {
        //复制的节点信息与目标节点内容不一致
        String message = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
        throw new QMRemoteException(message);

    }


    /**
     * 检入对象
     * @throws QMException 
     */
    private void checkIn() throws QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkIn() begin...");

        }

        CappTreeObject obje = view.getSelectedNode().getObject();
        if (obje != null)
        {
            if (obje instanceof TechnicsTreeObject)
            {
                try
                {

                    obje = view.update(obje);
                    //CCBegin SS1
//                    int res = JOptionPane.showConfirmDialog(view, "是否生成派生文件？", "选择", JOptionPane.YES_NO_OPTION);
//                    switch(res)
//                    {
//                    case JOptionPane.YES_OPTION:
                      //CCBegin SS3
                    //CCBegin SS4
                    //        				if(isBsxGroupUser()||getUserFromCompany().equals("zczx"))
                    if(isBsxGroupUser())
                    	//CCEnd SS4
        				{
        					SchDlg dlg = new SchDlg(view);
        				}
        				//CCEnd SS3
//                    }
                  //CCEnd SS1
                    BaseValueInfo obj = (BaseValueInfo) obje.getObject();
                    if (obj instanceof WorkableIfc)
                    {

                        CheckInCappController checkin_task = new
                                CheckInCappController(view);
                        if (!checkin_task.isCheckinAllowed((WorkableIfc) obj))
                        {
                            return;
                        }

                        //设置检入对象
                        checkin_task.setCheckinItem((WorkableIfc) obj);
                        //检入
                        checkin_task.checkin();
                    }
                    
                }
                catch (NotCheckedOutException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);

                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);

                }

            }
            else
            {
                //提示：对象类型错误
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkIn() end...return is void");
        }
    }


    /**
     * 检验指定的对象是否已被别人检出
     * @param workable 指定的对象
     * @throws QMException
     */
    /*private void testCheckOutByOther(WorkableIfc workable)
            throws QMException
         {
       // workable = CheckInOutCappTaskLogic.refresh(workable);
        //如果当前对象已经被其他人检出
        if (CheckInOutCappTaskLogic.isCheckedOutByOther(workable))
        {
            String s = "";
            try
            {
                UserIfc qmprincipal = CheckInOutCappTaskLogic.getCheckedOutBy(
                        workable);
                s = qmprincipal.getUsersName();
            }
            catch (QMException _ex)
            {
                s = "";
            }
            Object aobj1[] =
                    {
                    getIdentity((BaseValueIfc) workable), s
            };
            throw new CheckedOutByOtherException(
                    RESOURCE, CappLMRB.ALREADY_BY_OTHERS_CHECK_OUT, aobj1);
        }
         }*/


    /**
     * 检出选择的工艺卡
     */
    private WorkableIfc checkOut()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() begin...");

        }
        WorkableIfc copyinfo = null;
        CappTreeObject object = view.getSelectedNode().getObject();
        if (object != null)
        {
            if (object instanceof TechnicsTreeObject)
            {
                try
                {
                    object = view.update(object);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return null;

                }
                BaseValueInfo info = (BaseValueInfo) object.getObject();
//              CCBegin by leixiao 2009-9-4 原因：解放升级工艺路线,已发布的路线不能检出
                //CCBegin by liunan 2012-03-06 解放新提需求，特定人已发布状态可以修改，增加组允许改。
                //if (info instanceof QMTechnicsIfc&&((LifeCycleManagedIfc)info).getLifeCycleState().toString().equals("RELEASED")) {
                try
                {
                if (info instanceof QMTechnicsIfc&&((LifeCycleManagedIfc)info).getLifeCycleState().toString().equals("RELEASED")&&!isCappSupperGroup()) {
                //CCEnd by liunan 2012-03-06
                    String mm = ((QMTechnicsIfc)info).getTechnicsNumber() + "已发布，不能检出！";
                    JOptionPane.showMessageDialog(view, mm);
                    return null;
                  }
                //CCBegin by liunan 2012-03-06 解放新提需求，特定人已发布状态可以修改，增加组允许改。
                }
                catch (QMRemoteException e) {

                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                                  JOptionPane.INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return null;
                }
                //CCEnd by liunan 2012-03-06
//              CCEnd by leixiao 2009-9-4 原因：解放升级工艺路线
                //add by wangh on 20080222
                if(info instanceof WorkableIfc)
                {
                boolean canCheckOut = true;
                try{
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((
                        WorkableIfc) info, TechnicsRegulationsMainJFrame.currentUser))
                {
                    Object[] objs =
                            {
                            getIdentity(info)};
                    String message1 = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                            message1,
                            title,
                            JOptionPane.INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return null;
                }
                }
                catch (QMRemoteException e) {

                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                                  JOptionPane.INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return null;
				}
                }
                //add end
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                view.startProgress();
                copyinfo = getCheckOutObject((WorkableIfc)
                                             info);
                view.stopProgress();
                if (copyinfo != null)
                {
                    view.removeNode(info);
                    view.getProcessTreePanel().setNodeSelected(view.
                            getProcessTreePanel().findNode(new
                            TechnicsTreeObject((BaseValueInfo)
                                               copyinfo)).
                            getObject());

                }
                view.setCursor(Cursor.getDefaultCursor());
            }
            else
            {
                //提示：对象类型错误
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() end...return is void");
        }
        return copyinfo;
    }


    /**
     * 检出指定节点和所有处于检入状态的父节点（新建、更新、删除某工序、工步节点时，需调用本方法）
     * @param node 指定节点
     * @param parentTechNode 父工艺卡头节点
     * @throws QMException
     */
    private BaseValueInfo checkOutParentNode(CappTreeNode node,
                                             CappTreeNode parentTechNode)
            throws
            QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutParentNode() begin...");
        }
        QMTechnicsInfo pTech = (QMTechnicsInfo) parentTechNode.getObject().
                               getObject();
//      CCBegin by leixiao 2009-9-4 原因：解放升级工艺路线,已发布的路线不能检出
        //CCBegin by liunan 2012-03-06 解放新提需求，特定人已发布状态可以修改，增加组允许改。
        //if (pTech.getLifeCycleState().toString().equals("RELEASED")) {
        if (pTech.getLifeCycleState().toString().equals("RELEASED")&&!isCappSupperGroup()) {
        //CCEnd by liunan 2012-03-06

            throw new CheckedOutByOtherException(pTech.getTechnicsNumber()+"已发布，不能检出！");

          }
//      CCEnd by leixiao 2009-9-4 原因：解放升级工艺路线
        //检验其所属工艺卡头是否已被别人检出。如果被别人检出，则不能继续。
        if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc) pTech,
                TechnicsRegulationsMainJFrame.currentUser))
        {
            Object[] objs =
                    {
                    getIdentity(pTech)};
            throw new CheckedOutByOtherException(
                    RESOURCE, CappLMRB.ALREADY_BY_OTHERS_CHECK_OUT, objs);
        }

        // testCheckOutByOther((WorkableIfc) pTech);
        //得到需要检出的节点
        Vector nodes = view.getCheckInStateNodes(node);
        if (nodes.size() == 0)
        {
            return null;
        }

        Collection infos = new ArrayList();
        for (int i = 0; i < nodes.size(); i++)
        {
        	System.out.println("键入接点："+((CappTreeNode) nodes.elementAt(i)).getObject().
                    getObject());
            infos.add(((CappTreeNode) nodes.elementAt(i)).getObject().
                      getObject());

            //调服务检出infos中的对象,返回副本集合
        }
        Class[] cla =
                {
                Collection.class, QMTechnicsIfc.class};
        Object[] obj =
                {
                infos,
                (QMTechnicsIfc) (parentTechNode.getObject().getObject())};
        Collection c = (Collection) useServiceMethod("StandardCappService",
                "checkOutArray", cla, obj);

        //原本的第一个节点
        CappTreeNode oriNode = (CappTreeNode) nodes.elementAt(0);
        //原本第一个节点的父节点
        CappTreeNode parentOfFirstNode = oriNode.getP();
        //把树上的工艺卡的原本节点删掉
        //begin CR1
//        view.removeNode((BaseValueInfo) oriNode.getObject().getObject());
        //end CR1
        //副本数组
        Object copy[] = c.toArray();
      //begin CR2
		for(int i = 0;i<copy.length;i++)
	    {
	    	CappTreeNode oldNode = (CappTreeNode) nodes.elementAt(i);
	    	BaseValueInfo copyInfo = (BaseValueInfo) copy[i];
	    	if (copyInfo instanceof QMTechnicsInfo) 
	    	{
	    	    TechnicsTreeObject treeObject = new TechnicsTreeObject(copyInfo);
	    	    TechnicsTreeObject oldTreeObject = (TechnicsTreeObject)oldNode.getObject();
	    	    int count = oldNode.getChildCount();
	    	    for(int p = 0;p<count;p++)
	    	    {
	    	    	CappTreeNode cappTreeNode = (CappTreeNode)oldNode.getChildAt(p);
	    	    	StepTreeObject stepTreeObject = (StepTreeObject)cappTreeNode.getObject();
	    	    	stepTreeObject.setParentID(copyInfo.getBsoID());
	    	    }
	    	    treeObject.setParentID(oldTreeObject.getParentID());
	    	    oldNode.setObject(treeObject);
	    	}
	    	else if(copyInfo instanceof QMProcedureInfo)
	    	{
	    		QMProcedureInfo info = (QMProcedureInfo)copyInfo;
	    		if(info.getIsProcedure())
	    		{
	    			StepTreeObject stepObject = new StepTreeObject(copyInfo);
	    			StepTreeObject oldTreeObject = (StepTreeObject)oldNode.getObject();
		    	    int count = oldNode.getChildCount();
		    	    for(int p = 0;p<count;p++)
		    	    {
		    	    	CappTreeNode cappTreeNode = (CappTreeNode)oldNode.getChildAt(p);
		    	    	OperationTreeObject operationTreeObject = (OperationTreeObject)cappTreeNode.getObject();
		    	    	operationTreeObject.setParentID(copyInfo.getBsoID());
		    	    }
		    	    stepObject.setParentID(oldTreeObject.getParentID());
	    			oldNode.setObject(stepObject);
	    		}
	    		else
	    		{
	    			OperationTreeObject operationObject = new OperationTreeObject(copyInfo);
	    			OperationTreeObject oldTreeObject = (OperationTreeObject)oldNode.getObject();
		    	    int count = oldNode.getChildCount();
		    	    for(int p = 0;p<count;p++)
		    	    {
		    	    	CappTreeNode cappTreeNode = (CappTreeNode)oldNode.getChildAt(p);
		    	    	OperationTreeObject operationTreeObject = (OperationTreeObject)cappTreeNode.getObject();
		    	    	operationTreeObject.setParentID(copyInfo.getBsoID());
		    	    }
		    	    operationObject.setParentID(oldTreeObject.getParentID());
	    			oldNode.setObject(operationObject);
	    		}
	    	}
	    }
        //展开工艺卡的副本
//        BaseValueInfo copyInfo = null;
//        CappTreeNode copyNode = null;
//        for (int i = 0; i < copy.length; i++)
//        {
//            copyInfo = (BaseValueInfo) copy[i];
//            System.out.println("copyInfo="+ copy[i]);
//            if (copyInfo instanceof QMTechnicsInfo)
//            {
//                TechnicsTreeObject treeObject = new TechnicsTreeObject(copyInfo);
//                view.getProcessTreePanel().addProcess(treeObject);
//                copyNode = view.findNode(treeObject);
//            }
//            else if (((QMProcedureInfo) copyInfo).getIsProcedure())
//            {
//                //如果第一个节点是工序，则加在树上
//                if (i == 0)
//                { //得到工艺卡的副本
//
//                    WorkableIfc copyTech = CheckInOutCappTaskLogic.
//                                           getWorkingCopy(
//                            (WorkableIfc) parentOfFirstNode.getObject().
//                            getObject());
////                    System.out.println("copyInfo================="+copyInfo);
//                    view.addProcedureNode(view.findNode(new
//                            TechnicsTreeObject((
//                            BaseValueInfo) copyTech)),
//                                          (QMProcedureInfo) copyInfo);
//                }
//
//                StepTreeObject stepObject = new StepTreeObject(copyInfo);
//                copyNode = view.findNode(stepObject);
//                System.out.println("vvvvcopynode="+copyNode);
//
//            }
//            //工步
//            else if (!((QMProcedureInfo) copyInfo).getIsProcedure())
//            {
//                //如果第一个节点是工步，则加在树上
//                if (i == 0)
//                { //得到上级节点的副本
//                    WorkableIfc copyTech = CheckInOutCappTaskLogic.
//                                           getWorkingCopy(
//                            (WorkableIfc) parentOfFirstNode.getObject().
//                            getObject());
//                    if (((QMProcedureInfo) copyTech).getIsProcedure())
//                    {
//                        view.addProcedureNode(view.findNode(new
//                                StepTreeObject((BaseValueInfo)
//                                               copyTech)),
//                                              (QMProcedureInfo) copyInfo);
//                    }
//                    else
//                    if (!((QMProcedureInfo) copyTech).getIsProcedure())
//                    {
//                        view.addProcedureNode(view.findNode(new
//                                OperationTreeObject((
//                                BaseValueInfo) copyTech)),
//                                              (QMProcedureInfo) copyInfo);
//                    }
//                }
//
//                OperationTreeObject operationObject = new OperationTreeObject(
//                        copyInfo);
//                copyNode = view.findNode(operationObject);
//           
//            }
//        
//            //最后一个副本节点前的节点展开
//            if (i < copy.length - 1)
//            {
//                view.getProcessTreePanel().nodeExpaned(copyNode);
//               // copyNode = view.findNode(new StepTreeObject((BaseValueInfo)copy[1]));
//                
//
//            }
//        }
      //end CR1
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutParentNode() end...return is void");
        }
        return (BaseValueInfo) copy[c.size() - 1];
    }


    /* public void moveChild(CappTreeNode node)
     {
         if (node.getChildCount() == 0)
         {
             return;
         }
         //要粘贴的对象
         BaseValueInfo copyInfo = null;
         //node是工作原本，则获得副本
         try
         {
             if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) (node.
                     getObject().getObject())) &&
     CheckInOutCappTaskLogic.isInVault((WorkableIfc) (node.getObject().
                     getObject())))
             {

                 copyInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy
                            ((WorkableIfc) (node.getObject().getObject()));

             }
             //node是普通状态，获得本身
             else
             if (!CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) (node.
                     getObject().getObject())) &&
                 !CheckInOutCappTaskLogic.
                 isInVault((WorkableIfc) (node.getObject().getObject())))
             {
                 copyInfo = (BaseValueInfo) node.getObject().getObject();

                 //node是工作副本，则返回
             }
             else
             if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) (node.
                     getObject().getObject())) &&
                 !CheckInOutCappTaskLogic.
                 isInVault((WorkableIfc) (node.getObject().getObject())))
             {
                 return;
             }
         }
         catch (QMRemoteException ex)
         {
             if(verbose)
             ex.printStackTrace();
     String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
             JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                           JOptionPane.INFORMATION_MESSAGE);
         }

         //存放下一级节点
         Vector v = new Vector();
         for (int i = 0; i < node.getChildCount(); i++)
         {
             v.add(node.getChildAt(i));
             //要粘贴的树节点
         }
         CappTreeNode copyNode = new CappTreeNode();
         if (copyInfo instanceof QMTechnicsInfo)
         {
     TechnicsTreeObject technicsObject = new TechnicsTreeObject(copyInfo);
             copyNode = view.getProcessTreePanel().findNode(technicsObject);

         }
         else if ((copyInfo instanceof QMProcedureIfc) &&
                  ((QMProcedureInfo) copyInfo).getIsProcedure())
         {
             StepTreeObject stepObject = new StepTreeObject(copyInfo);
             copyNode = view.getProcessTreePanel().findNode(stepObject);
         }
         else if ((copyInfo instanceof QMProcedureIfc) &&
                  !((QMProcedureInfo) copyInfo).getIsProcedure())
         {
             StepTreeObject stepObject = new StepTreeObject(copyInfo);
             copyNode = view.getProcessTreePanel().findNode(stepObject);
         }
         for (int j = 0; j < v.size(); j++)
         {
             view.removeNode((BaseValueInfo) (((CappTreeNode) v.elementAt(j)).
                                              getObject().getObject()));
             QMProcedureInfo procedure = (QMProcedureInfo) (((CappTreeNode) v.
                     elementAt(j)).getObject().getObject());
             view.addNode(copyNode, procedure);
         }
         for (int j = 0; j < v.size(); j++)
         {
             moveChild((CappTreeNode) v.elementAt(j));
         }

     }

     */
    /**
     * 将对象检出（仅仅适用于连续检出父节点）
     * @param foldered_obj 检出对象
     * @param parentNode 检出对象的父节点
     * @return boolean 如果检出成功,返回true,否则返回false
     */
    /* protected boolean checkOutObject(FolderedIfc foldered_obj,
                                      CappTreeNode parentNode)
     {
         if (verbose)
         {
             System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutObject() begin..."
                                + "检出对象:" + foldered_obj + " parentNode: " +
                                parentNode);
             //标记是否检出成功
         }
         boolean successful_checkout = false;

         if (foldered_obj instanceof WorkableIfc)
         {
             try
             {
                 CheckOutCappController checkout_task = new
                         CheckOutCappController(
                         view,
                         CheckInOutCappTaskLogic.getCheckoutFolder());
                 checkout_task.setItem((WorkableIfc) foldered_obj);
                 checkout_task.setSelectedNodeParent(parentNode);
                 checkout_task.checkout();
                 successful_checkout = true;

             }
             catch (QMRemoteException e)
             {
                 String title = QMMessage.getLocalizedMessage(
                         RESOURCE, "information", null);
     JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
     JOptionPane.INFORMATION_MESSAGE);
             }
             catch (Exception e)
             {
                 String message = QMMessage.getLocalizedMessage(
                         RESOURCE, CappLMRB.CHECK_OUT_FAILURE, null);
                 String title = QMMessage.getLocalizedMessage(
                         RESOURCE, "information", null);
                 JOptionPane.showMessageDialog(view, message, title,
     JOptionPane.INFORMATION_MESSAGE);
                 successful_checkout = false;
             }
         }

         if (verbose)
         {
             System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutObject() end...return: " +
                                successful_checkout);
         }
         return successful_checkout;
     }
     */

    /**
     * 撤销检出
     */
    private void cancelCheckOut()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.cancelCheckOut() begin...");
        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if (object != null)
        {
            if (object instanceof TechnicsTreeObject)
            {
                try
                {
                    object = view.update(object);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;

                }
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BaseValueInfo obj = (BaseValueInfo) object.getObject();
                if (obj instanceof WorkableIfc)
                {
                    UndoCheckOutController undo_checkout_task =
                            new UndoCheckOutController(view, (WorkableIfc) obj);
                    undo_checkout_task.undoCheckout();
                }
                view.setCursor(Cursor.getDefaultCursor());
            }
            else
            {
                //提示：对象类型错误
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.cancelCheckOut() end...return is void");
        }
    }


    /**
     * 修订版本
     */
    private void revise()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.revise() begin...");
        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof TechnicsTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
//          CCBegin by leixiao 2009-9-4 原因：解放升级工艺路线,非已发布的路线不能修订
            if (!isReleased(obj)) {
                String mm = ((QMTechnicsIfc)obj).getTechnicsNumber() + "不是已发布状态，不能修订！";
                JOptionPane.showMessageDialog(view, mm);
                return ;
              }
//          CCEnd by leixiao 2009-9-4 原因：解放升级工艺路线
            if (obj instanceof VersionedIfc)
            {
                VersionedIfc version = (VersionedIfc) obj;
                ReviseCappController revise_task = new ReviseCappController(
                        view, version);
                revise_task.revise();
            }
            view.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            //提示：对象类型错误
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.revise() end...return is void");
        }
    }


//  CCBegin by leixiao 2009-9-4 原因：解放升级工艺路线,非已发布的路线不能修订
    /**
     * 判断：路线表是否处于发布状态
     * @param info TechnicsRouteListIfc
     * @return boolean
     */
    private boolean isReleased(BaseValueInfo info)
    {
      LifeCycleManagedIfc lcm = (LifeCycleManagedIfc)info;
//为发布状态或企业内控等状态时可以修订
      if(lcm.getLifeCycleState().toString().equals("RELEASED")
    		  ||lcm.getLifeCycleState().toString().equals("FEIMI")
    		  ||lcm.getLifeCycleState().toString().equals("MIMI")
    		  ||lcm.getLifeCycleState().toString().equals("JIMI")
    		  ||lcm.getLifeCycleState().toString().equals("QIYENEIKONG"))
      {
        return true;
      }
      return false;
    }
//  CCEnd by leixiao 2009-9-4 原因：解放升级工艺路线,非已发布的路线不能修订
    
    
    /**
     * 查看工艺卡的版本历史
     */
    private void viewVersionHistory()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewVersionHistory() begin...");
        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof TechnicsTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
            if (obj instanceof WorkableIfc)
            {
                WorkableIfc workable = (WorkableIfc) obj;
                String objID = workable.getBsoID();
                HashMap hashmap = new HashMap();
                hashmap.put("bsoID", objID);
                //转到"版本历史"页面
                RichToThinUtil.toWebPage(
                        "capp_version_viewTechnicsVersionHistory.screen",
                        hashmap);
            }

            view.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            //提示：对象类型错误
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewVersionHistory() end...return is void");
        }
    }


    /**
     * 查看工艺卡（工序、工步）版序历史
     */
    private void viewIterationHistory()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewIterationHistory() begin...");

        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof BusinessTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();

            if (obj instanceof WorkableIfc)
            {
                WorkableIfc workable = (WorkableIfc) obj;
                String objID = null;
                HashMap hashmap = new HashMap();
                if (workable instanceof IteratedIfc)
                {
                    MasteredIfc master = ((IteratedIfc) workable).getMaster();
                    objID = master.getBsoID();
                    hashmap.put("masterBsoID", objID);
                }
                if (obj instanceof QMFawTechnicsInfo)
                {
                    //转到工艺卡"版序历史"页面
                	QMTechnicsInfo technics =
                        (QMTechnicsInfo) view.getParentTechnicsNode().
                        getObject().getObject();
                	String bsoID = technics.getBsoID();
                	hashmap.put("bsoID", bsoID);
                    RichToThinUtil.toWebPage(
                            "capp_version_viewTechnicsIterationsHistory.screen",
                            hashmap);
                }
                if (obj instanceof QMProcedureInfo)
                {

                    QMTechnicsInfo technics =
                            (QMTechnicsInfo) view.getParentTechnicsNode().
                            getObject().getObject();
                    String technicsID = technics.getBsoID();
                    String bsoID = obj.getBsoID();
                    hashmap.put("technicsBsoID", technicsID);
                    hashmap.put("bsoID", bsoID);
                    if (((QMProcedureInfo) obj).getIsProcedure())
                    {
                        if (verbose)
                        {
                            System.out.println("参数依次为：bsoID = " + objID +
                                               "  technicsBsoID = " +
                                               technicsID);
                        }
                        //转到工序"版序历史"页面
                        RichToThinUtil.toWebPage(
                                "capp_version_viewProcedureStepIterationsHistory.screen",
                                hashmap);
                    }
                    else
                    {
                        if (verbose)
                        {
                            System.out.println("参数依次为：bsoID = " + objID +
                                               "  technicsBsoID = " +
                                               technicsID);
                        }
                        hashmap.put("BsoID", objID);
                        //转到工步"版序历史"页面
                        RichToThinUtil.toWebPage(
                                "capp_version_viewProcedurePaceIterationsHistory.screen",
                                hashmap);
                    }
                }
            }
            view.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            //提示：对象类型错误
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewIterationHistory() end...return is void");
        }
    }


    /**
     * 设置生命周期状态
     */
    private void setLifeCycleState()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.setLifeCycleState() begin...");
        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof TechnicsTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex1)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex1.getClientMessage(),
                                              title,
                                              JOptionPane.
                                              INFORMATION_MESSAGE);
                return;

            }
            BaseValueInfo info = (BaseValueInfo) obj.getObject();
            if (info instanceof QMFawTechnicsInfo)
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SetLifeCycleStateDialog dialog = null;
                try
                {
                    dialog = new SetLifeCycleStateDialog(view,
                            (QMFawTechnicsInfo) info);
                    dialog.setModal(true);
                    dialog.setVisible(true);
                    Class[] paraclass =
                            {  
                            BaseValueIfc.class, Boolean.TYPE};
                    Object[] paraobj =
                            {
                            (QMTechnicsInfo) dialog.getObject(),
                            new Boolean(false)};
                    QMTechnicsInfo technics = (QMTechnicsInfo) useServiceMethod(
                            "PersistService", "updateValueInfo", paraclass,
                            paraobj);
                    TechnicsTreeObject treeObject = new TechnicsTreeObject(
                            technics);
                    view.getProcessTreePanel().updateNode(treeObject);
                }
                catch (QMException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        else
        {
            //提示：对象类型错误
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.setLifeCycleState() end...return is void");
        }
    }


    /**
     * 重新指定生命周期
     */
    private void afreshAppointLifeCycle()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointLifeCycle() begin...");

        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof TechnicsTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
            QMTechnicsInfo technics;
            if (obj instanceof QMTechnicsInfo)
            {
                technics = (QMTechnicsInfo) obj;
            }
            else
            {
                //提示：对象类型错误
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, message, title, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String title = QMMessage.getLocalizedMessage(
                    RESOURCE, "afreshAssignLifeCycle", null);
            LifeCycleStateDialog a = new LifeCycleStateDialog(view, title, true);
            String str = getIdentity(technics);
            a.setName(str);
            a.setLifeCycleManaged((LifeCycleManagedIfc) technics);
            a.setVisible(true);

            if (!a.isShowing())
            {
                if (a.getObject() != null)
                {
                    TechnicsTreeObject treeObject = new TechnicsTreeObject(
                            (QMTechnicsInfo) a.getObject());

                    view.getProcessTreePanel().updateNode(treeObject);

                }

            }
        }
        else
        {
            //提示：对象类型错误
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointLifeCycle() end...return is void");
        }
    }


    /**
     * 查看生命周期历史
     */
    private void viewLifeCycleHistory()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewLifeCycleHistory() begin...");
        }

        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof BusinessTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
            QMTechnicsInfo technics;
            if (obj instanceof QMTechnicsInfo)
            {
                technics = (QMTechnicsInfo) obj;
            }
            else
            {
                //提示：对象类型错误
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, message, title, JOptionPane.INFORMATION_MESSAGE);
                view.setCursor(Cursor.getDefaultCursor());
                return;
            }
            String technicsBsoID = technics.getBsoID();
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", technicsBsoID);
            String docName = technics.getTechnicsNumber() + "(编号)" +
                             technics.getTechnicsName() + "(名称)";
            hashmap.put("docName", docName);
            //将technicsBsoID传到“查看生命周期历史记录”页面
            RichToThinUtil.toWebPage("capp_look_lifeCyleHistory.screen",
                                     hashmap);
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewLifeCycleHistory() end...return is void");
        }
    }


    /**
     * 重新指定工作组
     */
    private void afreshAppointProject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointProject() begin...");
        }
        try
        {
            CappTreeObject object = view.getSelectedNode().getObject();
            if ((object != null) && (object instanceof BusinessTreeObject))
            {
                object = view.update(object);
                BaseValueInfo obj = (BaseValueInfo) object.getObject();
                QMTechnicsInfo technics;
                if (obj instanceof QMTechnicsInfo)
                {
                    technics = (QMTechnicsInfo) obj;
                }
                else
                {
                    //提示：对象类型错误
                    String message = QMMessage.getLocalizedMessage(
                            RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(
                            view, message, title,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String title = QMMessage.getLocalizedMessage(
                        RESOURCE, "afreshAssignProject", null);
                ProjectStateDialog a = new ProjectStateDialog(view, title, true);
                String str = getIdentity(technics);
                a.setName(str);
                a.setLifeCycleManaged((LifeCycleManagedIfc) technics);
                a.setVisible(true);
                if (!a.isVisible())
                {
                    ;
                }
                {
                    Class[] paraclass =
                            {
                            BaseValueIfc.class, Boolean.TYPE};
                    Object[] paraobj =
                            {
                            (QMTechnicsInfo) a.getObject(),
                            new Boolean(false)};
                    technics = (QMTechnicsInfo) useServiceMethod(
                            "PersistService",
                            "updateValueInfo", paraclass, paraobj);
                    TechnicsTreeObject treeObject = new TechnicsTreeObject(
                            technics);
                    view.getProcessTreePanel().updateNode(treeObject);
                }
            }
        }
        catch (QMException ex)
        {
            if (verbose)
            {
                ex.printStackTrace();
            }
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointProject() end...return is void");
        }
    }


    /**
     * 零件族工艺派生
     */
    private void partCappDerive()
    {
    }


    /**
     * 加工路线工艺派生
     */
    private void machineCappDerive()
    {
    }


    /**
     * 设置当前的筛选条件.
     * @param refresh = true :根据设置的筛选条件刷新管理器页面内的零部件
     *                =false :不刷新管理器页面内的零部件
     */
    public void setConfigSpecCommand()
    {
        view.getPartTreePanel().setConfigSpecCommand();
    }


    /**
     * 配置规则
     */
    private void configureCriterion()
    {
        view.getPartTreePanel().configureCriterion();
    }


    /**
     * 按设备搜索工艺
     */
    private void searchInEquipment()
    {
        TechnicsSearchFromEquipmentJDialog dialog = new
                TechnicsSearchFromEquipmentJDialog(view);
        dialog.setVisible(true);
    }


    /**
     * 按工装搜索工艺
     */
    private void searchInTools()
    {
        TechnicsSearchFromToolJDialog dialog = new
                                               TechnicsSearchFromToolJDialog(
                view);
        dialog.setVisible(true);
    }


    /**
     * 按材料搜索工艺
     */
    private void searchInMaterial()
    {
        TechnicsSearchFromMaterialJDialog dialog = new
                TechnicsSearchFromMaterialJDialog(view);
        dialog.setVisible(true);
    }
//CCBegin SS11
    /**
	 * 结构搜索工艺规程
	 */
	private void searchStructTechnics() {
		TechnicsStructSearchDialog searchByStructJDialog = null;
		try {
			searchByStructJDialog = new TechnicsStructSearchDialog(view);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		searchByStructJDialog.setModal(false);
		searchByStructJDialog.setVisible(true);

	}
    //CCEnd SS11

    /**
     * 搜索工艺规程
     */
    private void searchTechnics()
    {

        try
        {
            d = new TechnicsSearchJDialog(view);
            d.addMultiListActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    view.myList_actionPerformed(e);
                    d.setVisible(false);
                }
            });

            d.addDefaultListener();
            view.setMenuItemEnable(false, "public_search");
            d.setVisible(true);
            view.setMenuItemEnable(true, "public_search");
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }

    }


    /**
     * 工艺规程管理
     */
    private void helpTechnicsManager()
    {
    }

//  delete by wangh on 20061206(去掉没有引用的方法)
//    /**检出某对象后，获得其新的原本节点*/
//    private CappTreeNode updateNode;

//    private CappTreeNode getUpdateNode()
//    {
//        return updateNode;
//    }


    /**
     * 获得检出后的对象,如果检出成功,返回检出后的对象,如果检出失败,返回null.
     * @param workable 工作副本
     * @return
     */
    protected WorkableIfc getCheckOutObject(WorkableIfc workable)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.getCheckOutObject() begin...");
        }
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        try
        {
            CheckOutCappController checkout_task = new CheckOutCappController(
                    view);
            //CheckInOutCappTaskLogic.getCheckoutFolder());
            if (view.getSelectedNode().getObject().getObject() instanceof
                QMProcedureInfo)
            {
                checkout_task.setSelectedNodeParent(view.getSelectedNode().
                        getP());
            }
            else
            {
                checkout_task.setSelectedNodeParent(view.getSelectedNode());
            }
            checkout_task.setCheckoutItem(workable);
            workable = checkout_task.checkout();
        }
        catch (AlreadyCheckedOutException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;

        }
        catch (CheckedOutByOtherException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;
        }
        catch (QMRemoteException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;
        }
        catch (LockException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;
        }
        catch (QMException e)
        {
            e.printStackTrace();
            workable = null;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.getCheckOutObject() end...return: " +
                               workable);
        }
        return workable;
    }


    /**
     * 向工艺树上添加节点
     */
    public void addNode(CappTreeNode parentNode, CappTreeObject obj)
    {
        view.getProcessTreePanel().addNode(parentNode, obj);
    }


    /**
     * 新建对象
     */
    private void createObject()
    {
        ObjectSelectJDialog dialog = new ObjectSelectJDialog(this, view);
        dialog.setVisible(true);
    }

    private void searchTreeObject()
    {
        view.searchTreeObject();
    }


    /**
     * 刷新工艺卡.
     * @param modifiedTechnics 被刷新的工艺卡.
     */
    protected void refreshExplorerPart(QMTechnicsInfo modifiedTechnics)
    {
        WorkThread work = new WorkThread(
                getThreadGroup(), REFRESH_TECHNICS, modifiedTechnics);
        work.start();
        try
        {
            work.join();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 在产品管理器上将修改的工艺卡刷新
     * @param modifiedPart被修改的工艺卡
     */
    protected void refreshQMTechnics(QMTechnicsInfo modifiedTechnics)
    {

    }
    //CCBegin SS1
    /**
     * 生成派生文件
     * @throws Exception 
     */
    public void schedule(String type) throws Exception
    {
        Vector vec = new Vector();
        vec.add(type);
        if(view.getContentJPanel().schedule(vec))
        {
           System.out.println("schedule         ----------------");
            int res = JOptionPane.showConfirmDialog(view, "系统正在生成派生文件，是否需要进行校对？", "选择", JOptionPane.YES_NO_OPTION);
            switch(res)
            {
            case JOptionPane.YES_OPTION:
                String clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
                String filePName = clientPath + "/"+type + ".xls";
                Desktop.getDesktop().open(new File(filePName)); 
            }

        }
        // JOptionPane.showMessageDialog(view, type + "创建成功!", "", JOptionPane.INFORMATION_MESSAGE);

    }
    // CCEnd SS1
    /**
     * 获得当前用户
     * @return String 当前用户
     */
    public UserIfc getCurrentUser()
            throws QMRemoteException
    {
        UserIfc sysUser = (UserIfc) useServiceMethod("SessionService",
                "getCurUserInfo", null, null);
        return sysUser;
    }
    
    //Begin CR4
    /**
     * 移出所选工艺
     * @param modifiedPart被修改的工艺卡
     */
    public void moveOutQMTechnics()
    {
    	CappTreeObject object=null;
        CappTreeNode[] nodes = view.getSelectedNodes();
    	if(nodes==null||nodes.length==0)
    	{
    		
    		return;
    	}
        for(int i = 0; i < nodes.length; i++)
        {
		    object = nodes[i].getObject();
	        if (object instanceof TechnicsTreeObject)
	        {
	            view.getProcessTreePanel().removeNode(object);
	
	        }
        }

    }
    //End CR4
    
    //CCBegin by liunan 2012-03-06 解放新提需求，特定人已发布状态可以修改，增加组允许改。
    private boolean isCappSupperGroup() throws QMRemoteException
    {    	
    	Class[] paraclass = {};
    	Object[] paraobj = {};    	
    	UserInfo user = (UserInfo) useServiceMethod("SessionService", "getCurUserInfo", paraclass, paraobj);
    	//System.out.println("user："+user.getUsersDesc());
    	Class[] paraclass1 = {UserInfo.class, boolean.class, boolean.class};
    	Object[] paraobj1 = {(UserInfo)user, Boolean.FALSE, Boolean.FALSE};
    	Enumeration enum1 = (Enumeration) useServiceMethod("UsersService","parentGroups", paraclass1,paraobj1);
    	while (enum1.hasMoreElements())
    	{
    		GroupInfo gp = (GroupInfo) enum1.nextElement();
    		//System.out.println("组名："+gp.getUsersDesc());
    		if(gp.getUsersDesc().indexOf("工艺超级权限组")!=-1)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    //CCEnd by liunan 2012-03-06
    //CCBegin SS1
    public void schedules(Vector types)
    {
        try
        {
            if(view.getContentJPanel().schedule(types))
            {
                String clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
                for(int i = 0;i < types.size();i++)
                {
                    String filePName = clientPath + "/" + (String)types.get(i) + ".xls";
                    Desktop.getDesktop().open(new File(filePName));
                }
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public class SchDlg extends JDialog
    {
        public SchDlg()
        {
            try
            {
                jbInit();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        public SchDlg(JFrame parent)
        {
            super(parent);
            try
            {                
                jbInit();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        private void jbInit() throws Exception
        {
            this.setModal(true);
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setResizable(false);
            //CCBegin SS5
            //setSize(300, 300);
            setSize(300, 400);
            //CCEnd SS5
            setLocation(300, 300);

            this.getContentPane().setLayout(gridBagLayout1);
            jj.setText("夹具明细表");
            wn.setText("万能工具清单");
            //CCBegin SS5
            lj.setText("万能量具清单");
            gw.setText("工位器具清单");
            //CCEnd SS5
            mj.setText("磨具一览表");
            jfj.setText("夹辅具一览表");
            zp.setText("装配工具一览表");
            dj.setText("刀具一览表");
            okBtn.setText("确定");
            cancelBtn.setText("取消");
            sb.setText("设备清单");
            //CCBegin SS5
            /*this.getContentPane().add(zp, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 12, 0));
            this.getContentPane().add(dj, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 36, 0));
            this.getContentPane().add(jfj, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(mj, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 36, 0));
            this.getContentPane().add(sb, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 48, 0));
            this.getContentPane().add(wn, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(jj, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(26, 98, 0, 105), 36, 0));
            this.getContentPane().add(okBtn, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 57, 35, 0), 24, 0));
            this.getContentPane().add(cancelBtn, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 63, 35, 42), 24, 0));*/
            
            this.getContentPane().add(zp, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 12, 0));
            this.getContentPane().add(dj, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 36, 0));
            this.getContentPane().add(jfj, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(mj, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 36, 0));
            this.getContentPane().add(sb, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 48, 0));
            
            this.getContentPane().add(gw, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(lj, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 20, 0));
            
            this.getContentPane().add(wn, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(jj, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(26, 98, 0, 105), 36, 0));
            this.getContentPane().add(okBtn, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 57, 35, 0), 24, 0));
            this.getContentPane().add(cancelBtn, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 63, 35, 42), 24, 0));
            //CCEnd SS5
            cancelBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    setVisible(false);
                    dispose();
                }
            });
            okBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Vector types = new Vector();
                    if(jj.isSelected())
                        types.add("夹具明细表");
                    if(wn.isSelected())
                        types.add("万能工具清单");
                    //CCBegin SS5
                    if(lj.isSelected())
                        types.add("万能量具清单");
                    if(gw.isSelected())
                        types.add("工位器具清单");
                    //CCEnd SS5
                    if(sb.isSelected())
                        types.add("设备清单");
                    if(mj.isSelected())
                        types.add("磨具一览表");
                    if(dj.isSelected())
                        types.add("刀具一览表");
                    if(jfj.isSelected())
                        types.add("夹辅具一览表");
                    if(zp.isSelected())
                        types.add("装配工具一览表");
                    schedules(types);
                    setVisible(false);
                    dispose();
                }
            });

            setVisible(true);
        }

        JCheckBox jj = new JCheckBox();
        JCheckBox wn = new JCheckBox();
        //CCBegin SS5
        JCheckBox lj = new JCheckBox();
        JCheckBox gw = new JCheckBox();
        //CCEnd SS5
        JCheckBox sb = new JCheckBox();
        JCheckBox mj = new JCheckBox();
        JCheckBox jfj = new JCheckBox();
        JCheckBox zp = new JCheckBox();
        JCheckBox dj = new JCheckBox();
        JButton okBtn = new JButton();
        JButton cancelBtn = new JButton();
        GridBagLayout gridBagLayout1 = new GridBagLayout();

    }   
    //CCEnd SS1
  //CCBegin SS3
  	private boolean isBsxGroupUser() throws QMException 
  	{
  		Vector groupVec = new Vector();  		
  		UserIfc sysUser = getCurrentUser();
  		Class[] paraclass1 = {UserInfo.class, boolean.class};
  		Object[] paraobj1 = {(UserInfo)sysUser, Boolean.TRUE};
  		Enumeration groups = (Enumeration) useServiceMethod("UsersService","userMembers", paraclass1,paraobj1);
  		for (; groups.hasMoreElements();) 
  		{			
  			GroupIfc group = (GroupIfc) groups.nextElement();
  			String groupName = group.getUsersName();
  			if (groupName.equals("变速箱用户组")) 
  			{
  				return true;
  			}
  		}
  		return false;
  	}
  //CCEnd SS3
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

    //CCBegin SS13
    public void exportQMProcedure()
    {
            QMProcedureIfc qmProcedureIfc = null;
            String technicsID = null;
            String qmProcedureId = null;
            File file = null;
            //
            JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("导出");
        fileChooser.setApproveButtonText("导出");
        int interval = fileChooser.showOpenDialog(view);
        if (interval == fileChooser.APPROVE_OPTION)
        {
            file = fileChooser.getSelectedFile();
            CappTreeObject obj = view.getSelectedNode().getObject();
                if(obj!=null)
                {
                        Object procedureObj = obj.getObject();
                        if(procedureObj instanceof QMProcedureIfc)
                    {
                            qmProcedureIfc = (QMProcedureIfc)procedureObj;
                        qmProcedureId = qmProcedureIfc.getBsoID();
                        if(qmProcedureIfc.getIsProcedure())
                        {
                                if(obj instanceof StepTreeObject)
                            {
                                    StepTreeObject parentObj = (StepTreeObject)obj;
                                    technicsID = parentObj.getParentID();
                            }
                        }
                    }
                    else if(procedureObj instanceof QMFawTechnicsIfc)
                    {
                    	QMFawTechnicsIfc ifc = (QMFawTechnicsIfc)procedureObj;
                    	qmProcedureId = ifc.getBsoID();
                    	technicsID = ifc.getBsoID();
                    }
                    
                try
                {
                  //
                  Class[] myClass = {String.class,String.class};
                  Object[] myObj = {technicsID,qmProcedureId};
                  System.out.println(technicsID+"==导出=="+qmProcedureId);
                   Vector v = (Vector)useServiceMethod("StandardCappService","exportQMProcedureControlPlan",myClass,myObj);
                    //通过请求服务器的工厂获得server
                            String s = "";
                            if(v!=null&& v.size()>0)
                            {
                                    for(int i=0;i<v.size();i++)
                                    {
                                            s = s + v.get(i).toString();
                                    }
                                    String filename = file.getAbsolutePath();
                                    if (!filename.endsWith(".csv"))
                            {
                                filename = filename + ".csv";
                            }
                                    FileWriter filewriter = new FileWriter(filename, false);
                            filewriter.write(s);
                            filewriter.flush();
                            filewriter.close();
                            }
                    }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
                }
        }
    }
    //CCEnd SS13
}
