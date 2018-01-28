/** 程序QMProductManager.java	1.0  2003/01/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/02 谢斌   原因:该方法为右键单击树节点进行更新操作，使用缓存树节点信息
 *                       方案:其它问题
 *                       备注:解放v3r11-展开零部件树节点性能优化
 * CR2 穆勇鹏 20090601  问题原因：对原件进行更新时，会获得副本并对其进行更新操作，然
 * 						  焦点仍在原本，这导致刷新时不能兑付本进行刷新。
 * 						解决方案：经与需求确认，现在将国际更改为——当对当前用户和更
 * 						  改得零部件原本进行更新时，会自动跳往其副本相应TAB页。如果
 *   					  左侧零部件树上没有该件的副本时，则将其副本添加到零部件树上。
 * 						备注：TD问题2085
 * CR3  2009/06/16  马辉 TD:2339 在产品信息管理器，检出零部件（零部件不是最新版本）,提示信息有错误
 *                       原因：在检出过程中零部件即使不是最新版本，最后也调用了一次“检入成功”的提示信息
 *                       方案：如果零部件不是最新版本，调用“检入失败”的提示信息
 *                       
 * CR4  20090616 张强 修改原因：TD2256 从产品信息管理器进入工艺规程管理器，
 *                             然后在产品信息管理器检入零部件出现错误提示信息。
 *                    方案：检入分配刷新事件时事件源从Vector修改为WorkableIfc。
 * CR5  20090618 王亮 修改原因：TD2436 在产品信息管理中，零部件A的事物特性进行“应用所有”操作
 *                             将事物特性添加到零部件B后,零部件B的状态有问题。
 *                    方案：每次调用刷新都获得新的参数。
 * CR6  20090630 马辉 修改原因：TD2432 在proe中将零部件检出再检入，在产品信息管理器中，刷新该零部件，不能显示零部件最新版本
 *                    修改方案：每次刷新都重新取一次最新大版本的最新小版本。  
 * CR7  2009/07/06 马辉 修改原因：TD2523 在产品信息管理器中，更改配置规范后，点击“确定”，弹出多余的提示信息
 *                      修改方案: 在根据当前设置的配置规范刷新树的时候清空面板上的显示内容。
 * CR8  20090630 马辉 修改原因：TD2432 在proe中将零部件检出再检入，在产品信息管理器中，刷新该零部件，不能显示零部件最新版本.
 *                    备注：通过cr6的修改，刷新已经能得到最新版本，但是通过刷新会出现零部件树上有两个完全相同的最新版本，所以要从树上删除一个。
 *                    修改方案：如果出现两个完全相同的最新版本，从树上删除当前选中的，焦点移到另一个相同的节点上。                                                                              
 * CR9 2009/12/25 王亮 修改原因：修改在产品信息管理器中查看零部件主信息时的界面。
 *                     修改方案：使用没有sidebar的part_version_iterationsViewMain-001.screen来显示。 
 * CCBegin by liunan 2012-04-25 打补丁v4r3_p044
 * CR12 2012/03/05 马辉：修改原因：产品所有模块统一修改重命名方法
 *                       修改方案：1.调取EnterpriseService服务中的hasRenameAccess方法进行校验
 *                                2.零部件模块master对象不允许进行重命名操作
 *                                3.判断当前操作的零部件视图是否与此零部件第一个小版本（A.1）的视图相同，如果相同可以重命名，否则不允许重命名操作！  
 * CCEnd by liunan 2012-04-25
 * SS1 2013-1-21  产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能，导出的格式文件内容包括：零部件的零件号、零件名、父件数量、 
                  每车数量、计量单位、来源、制造路线、装配路线、（T单总、固定提前期合计、可变提前期），
                  其中后三项括弧中的内容需要从零部件关联的工艺卡中获取
 * SS2 2013-1-21  产品信息管理器中具有结构复制功能，可以将选中零部件结构完全复制，并按照结构粘贴到其他零部件中
 * SS3 增加用户检入时检查零部件对应CAD文档的状态，如果为检出状态则提示用户 侯焊锋 2013-11-26
 * SS4 增加“分子公司物料清单” liuyang 2013-12-18
 * SS5 添加"整车报毕统计"和“独立总成报毕统计 ”liuyang 2014-6-11
 * SS6 鼠标双击零件树，展开的结构按编号升序排序 pante 2015-01-07
 * SS7 增加产品信息管理器零件树定位搜索结构内零件功能 pante 2015-01-08
 * SS8 零部件检入的时候判断是否关联采用单和变更单，如果有关联，自动将零部件更新到采用单的采用零件列表 刘家坤 2015-02-04
 * SS9 某一逻辑总成或零件重复复制是否可以增加提示信息，避免零件重复 高义升 2015-04-21
 * SS10 结构调整时,同时选择多个零件进行复制工作 高义升 2015-04-24
 * SS11 零部件检入的时候，只有采用单和变更单在生命周期状态是“设计”和“驳回”时，才更新采用单和变更单  高义升 2015-05-17
 * SS12 A004-2015-3158 选用轴齿生命周期新建件时无法检入，因为轴齿零部件流程处理过零部件，因此检入前需要刷新对象。 liunan 2015-7-7
 * SS13 A004-2016-3286 整车一级件清单 liunan 2016-1-20
 */
package com.faw_qm.part.client.main.controller;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.auth.RequestHelper;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.cappclients.capp.view.TechnicsRegulationsMainJFrame;
import com.faw_qm.cappclients.capproute.view.CappRouteListManageJFrame;
import com.faw_qm.cappclients.capproute.view.SearchZXAdoptPartDialog;
import com.faw_qm.cappclients.summary.controller.SummaryMainController;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMExplorerEvent;
import com.faw_qm.clients.beans.explorer.QMExplorerListener;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.beans.explorer.QMTree;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.IntroduceDialog;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.vc.controller.CheckOutTask;
import com.faw_qm.clients.vc.controller.ReviseTask;
import com.faw_qm.clients.vc.controller.UndoCheckoutTask;
import com.faw_qm.clients.vc.util.AlreadyCheckedOutException;
import com.faw_qm.clients.vc.util.CheckInOutException;
import com.faw_qm.clients.vc.util.CheckedOutByOtherException;
import com.faw_qm.clients.vc.util.ObjectNoLongerExistsException;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.folder.model.FolderBasedIfc;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.lifecycle.client.view.LifeCycleStateDialog;
import com.faw_qm.lifecycle.client.view.SetLifeCycleStateDialog;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.client.baseline.controller.BaselineAddController;
import com.faw_qm.part.client.baseline.controller.BaselineCreateController;
import com.faw_qm.part.client.baseline.controller.BaselineRemoveController;
import com.faw_qm.part.client.baseline.controller.BaselineSearchController;
import com.faw_qm.part.client.design.controller.TaskDelegate;
import com.faw_qm.part.client.design.controller.TaskDelegateFactory;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.model.ProgressService;
import com.faw_qm.part.client.design.model.QMPartInfoTaskDelegate;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsageMasterItem;
import com.faw_qm.part.client.design.util.TaskDelegateException;
import com.faw_qm.part.client.design.view.BaseAttrApplyAllJDialog;
import com.faw_qm.part.client.design.view.ExtendAttrApplyAllJDialog;
import com.faw_qm.part.client.design.view.MessageDialog;
import com.faw_qm.part.client.design.view.PartDesignRenameJDialog;
import com.faw_qm.part.client.design.view.PartTaskJPanel;
import com.faw_qm.part.client.effectivity.controller.EffCreateController;
import com.faw_qm.part.client.effectivity.controller.EffModifyController;
import com.faw_qm.part.client.effectivity.controller.EffRemoveController;
import com.faw_qm.part.client.effectivity.controller.EffSearchController;
import com.faw_qm.part.client.effectivity.view.EffectivityAddJFrame;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.PartShowMasterDialog;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.client.main.view.DWSSJDialog;
import com.faw_qm.part.client.main.view.MoveNumberDialog;
import com.faw_qm.part.client.main.view.PartPopupMenu;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.client.other.controller.BasicSearchController;
import com.faw_qm.part.client.other.controller.BatchCheckInTask;
import com.faw_qm.part.client.other.controller.DifferenceCompareController;
import com.faw_qm.part.client.other.controller.IBASearchController;
import com.faw_qm.part.client.other.controller.MaterialController;
import com.faw_qm.part.client.other.controller.MoveController;
import com.faw_qm.part.client.other.controller.SortController;
import com.faw_qm.part.client.other.controller.StructureConditionController;
import com.faw_qm.part.client.other.view.GPartBOM;
import com.faw_qm.part.client.other.view.LogicBomFrame;
import com.faw_qm.part.client.other.view.NewLogicBomFrame;
import com.faw_qm.part.client.other.view.NewSubCompBomJFrame;
import com.faw_qm.part.client.other.view.NewSubCompBomJFrame;
import com.faw_qm.part.client.other.view.SelectViewNameDialog;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.QMCt;
import com.faw_qm.util.QMThread;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCEnd by liunan 2008-08-30
//import com.sun.tools.javac.main.JavacOption;
//import com.sun.tools.jdi.EventSetImpl.Itr;
//CCBegin SS13
import com.faw_qm.part.client.other.view.FirstLevelListFrame;
//CCEnd SS13

/**
 *
 * <p>Title: 产品结构管理器主控制类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p
 * <p>Company: 一汽启明</p>
 * @author 李玉芬
 * @version 1.0
 * 修改1：2007-10-25，穆勇鹏
 * 原因：在右键更新时若所选对象是被当前用户检出，但不是工作副本时，会获得副本对象
 * 解决方案：在右键更新时若所选对象是被当前用户检出，但不是工作副本时，则直接返回
 * 修改2：2007-11-07，穆勇鹏，
 * 修改原因：检入或撤销检出时，若只有副本显示在产品管理器上则副本也会在执行操作后消失
 * 解决方案：添加方法以检查操作对象的原本是否显示在产品管理器上，若已显示则删除副本，否则保留副本
 * 修改3：2007-11-14，穆勇鹏
 * 修改原因：检入或撤销检出后，操作对象作为其他零件的子件不从检出状态变为检入状态
 * 解决方案：修改刷新程序，使其可以在检入或撤销检出后刷新操作对象作为其它零件的子件
 * 修改4：2008-02-25，张强
 * 产生原因：检出的子件在执行剪切操作时，找不到与父件之间的关联类值对象。
 * 解决办法：检出的子件在执行剪切操作时，获取与父件之间的关联类值对象。
 * 修改5：2008-03-06，张强
 * 产生原因：检出的子件在执行部分移动操作时，找不到与父件之间的关联类值对象。
 * 解决办法：检出的子件在执行部分移动操作时，获取与父件之间的关联类值对象。
 */
public class QMProductManager extends Container implements ActionListener,
        QMExplorerListener, ItemSelectable
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**线程组*/
    protected ThreadGroup threadGroup = null;

    /**Item监听*/
    protected ItemListener itemListener = null;

    /**浏览器监听*/
    protected Vector myListener = null;

    /**当前配置规范*/
    protected ConfigSpecItem configSpecItem = null;

    /**当前applet*/
    private JApplet applet = null;

    /**绑定的资源信息*/
    private static ResourceBundle resource = null;

    /**指针锁*/
    private Object cursorLock = new Object();  
   
    /**指针等待*/
    private int waitingOnCursor = 0;

    /**设置零部件值对象*/
    final static int SET_PART_ITEM = 0;

    /**普通搜索*/
    final static int GENERAL_SEARCH = 1;

    /**创建零部件*/
    final static int CREATE = 2;

    /**更新零部件*/
    final static int UPDATE = 3;

    /**查看零部件*/
    final static int VIEW = 4;

    /**删除零部件*/
    final static int DELETE = 5;

    /**检出*/
    final static int CHECKOUT = 6;

    /**检入*/
    final static int CHECKIN = 7;

    /**撤销检出*/
    final static int UNDOCHECKOUT = 8;

    /**修订*/
    final static int REVISE = 9;

    /**设置当前配置规范*/
    final static int SYSCFIG = 10;

    /**刷新*/
    final static int REFRESH = 11;

    /**清除*/
    final static int CLEAR = 12;

    /**分级物料清单*/
    final static int BUILD_GRADE_BOM = 13;

    /**统计表*/
    final static int BUILD_STATISTIC_BOM = 15;

    /**发布视图版本*/
    final static int NEWVIEWVERSION = 16;

    /**关于*/
    final static int ABOUT = 17;

    /**帮助*/
    final static int HELP = 18;

    /**定制物料清单*/
    final static int TAILOR_BOM = 19;

    /**创建有效性*/
    final static int CREATE_EFFCONFIGITEM = 20;

    /**更新有效性*/
    final static int UPDATE_EFFECTIVITY = 21;

    /**添加有效性*/
    final static int ADD_EFFECTIVITY = 22;

    /**按结构添加有效性*/
    final static int ADD_EFFECTIVITY_BY_STRUCTURE = 23;

    /**定义替换件*/
    final static int DEFINE_ALTERNATES = 24;

    /**定义结构替换件*/
    final static int DEFINE_SUBSTITUTES = 25;

    /**清除所有*/
    final static int CLEAR_ALL = 26;

    /**结构比较*/
    final static int COMPARE_STRUCTURE = 27;

    /**查看有效性*/
    final static int VIEW_EFFCONFIGITEM = 28;

    /**移除有效性*/
    final static int REMOVE_EFFECTIVITY = 29;

    /**维护有效性方案*/
    final static int MAINTENANCE_EFFCONFIGITEM = 30;

    /**创建基准线*/
    final static int CREATE_BASELINE = 31;

    /**维护基准线方案*/
    final static int MAINTENANCE_BASELINE = 32;

    /**添加基准线*/
    final static int ADD_BASELINE = 33;

    /**按结构添加基准线*/
    final static int POPULATE_BASELINE = 34;

    /**查看基准线*/
    final static int VIEW_BASELINE = 35;

    /**移除基准线*/
    final static int REMOVE_BASELINE = 36;

    /**添加零部件到树结构*/
    final static int ADDPART = 37;

    /**在树结构上刷新修改的零部件*/
    final static int REFRESHPART = 39;

    /**从树结构上删除零部件*/
    final static int DELETEPART = 40;

    /**查看版本历史*/
    final static int VIEW_VERSION_HISTORY = 41;

    /**查看版序历史*/
    final static int VIEW_ITERATION_HISTORY = 42;

    /**按基本属性搜索*/
    final static int BASE_ATTR_SEARCH = 43;

    /**按事物特性搜索*/
    final static int IBA_SEARCH = 44;

    /**重新指定生命周期*/
    final static int RESET_LIFECYCLE = 45;

    /**设置生命周期状态*/
    final static int SET_LIFECYCLE_STATE = 46;

    /**显示生命周期历史*/
    final static int SHOW_LIFECYCLE_HISTORY = 47;

    /**另存为*/
    final static int SAVEAS = 48;

    /**排序*/
    //add by 熊代军 2003.10.21
    final static int SORT = 49;

    /**发布特性*/
    //add by 谢斌 2004.04.06
    final static int PUBLISHIBA = 50;

    /**另存为历史*/
    //add by skx 2004.5.12
    final static int SAVEAS_HISTORY = 51;

    /**属性比较*/
    final static int IBA_COMPARE = 52;

    /**广义部件物料清单*/
    //add by liun 2005.3.9
    final static int GP_BOM_LIST = 53;

    /**查看注册历史*/
    final static int CATALOG_HISTORY = 54;

    //added by whj
    final static int COPY = 97;

    final static int PASTE = 98;

    final static int CUT = 99;

    final static int PUBLIC_SORT = 96;
    
    //CCBegin by liunan 2008-07-30
    //添加三种新加报表的标识，根据解放版本修改。
    final static int LOGIC=100;
    final static int BUILD_COMPARE_BOM = 60;
    final static int BUILD_FIRSTLEVELSON_BOM = 61;
    //CCEnd by liunan 2008-07-30
  //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
    final static int NEWLOGIC=101;
  //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
    
    /**在树结构上刷新修改的零部件*/
    final static int REFRESHSOMEPART = 110;
    //CCBegin SS1
    final static int BUILD_ERP_BOM = 111;
    //CCEnd SS1
    //CCBegin SS4
    final static int SUBCOMPBOM = 120;
    //CCEnd SS4
    //CCBegin SS5
    final static int ZCBOM = 130;
    final static int DLZCBOM = 140;
    //CCEnd SS5
    
//    CCBegin SS7
    /**定位搜索*/
    final static int DWSS = 141;
//    CCEnd SS7
    
    //CCBegin SS13
    final static int FIRSTLEVEL = 142;
    //CCEnd SS13
    
    /**锁操作*/
    //modify by shf 2003/09/13
    private Object lock = new Object();

    protected Object lock2 = new Object();

    /**内部类*/
    private MainRefreshListener listener;

    /**浏览器*/
    protected QMExplorer myExplorer = null;

    /**产品信息浏览器主界面*/
    private QMProductManagerJFrame frame;

    /**用于标记资源信息路径*/
    protected static final String RESOURCE = "com.faw_qm.part.client.main.util.QMProductManagerRB";

    /**异常信息标题:"警告"*/
    static String exceptionTitle = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MESSAGE, null);

    /**选择对象错误*/
    static String errorObject = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.WRANG_TYPE_OBJECT, null);

    /**没有选择对象*/
    static String noSelectObj = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.NOT_SELECT_OBJECT, null);

    /**没有选择广义部件*/
    //add by liun 2005.3.14
    static String noSelectGPart = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.NO_SELECT_GPART, null);

    /**不能删除替换件和结构替换件*/
    static String alterOrSub = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ALTER_OR_SUB, null);

    /**分级和统计表的输出属性（用于显示在界面上）*/
    static String attrName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ATTR_NAME, null);

    /**分级和统计表的输出属性（用于参数传递）*/
    static String attrEnglishName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ATTR_ENG_NAME, null);
     //CCBegin SS1  
     /**分级和统计表的输出属性（用于显示在界面上）*/
    static String erpAttrName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ERPATTR_NAME, null);

    /**分级和统计表的输出属性（用于参数传递）*/
    static String erpAttrEnglishName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ERPATTR_ENG_NAME, null);
    //CCEnd SS1
//  CCBegin SS2 
    private QMPartMasterIfc allAchePartMasterIfc = null;
    private Vector vcachePartUsageLinkIfc = new Vector();
//  CCEnd SS2
//    //将源零部件的属性应用到目标零部件//add by muyp 080926
//    static String attrApplyName = QMMessage.getLocalizedMessage(RESOURCE,
//            QMProductManagerRB.APPLY_ATTRIBUTE_TO_OTHER, null);//end
    //liyz add
    /**应用所有选择tab页警告*/
    static String selectObject = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.WRANG_TYPE_SELECT_TAB, null);

    private PartPopupMenu partPop;

    /** 源零部件的Master。*/
    private QMTree myTree;

    /** 源零部件的Master。*/
    private QMPartMasterIfc sourcePartMaster;

    /** 源零部件的父件。*/
    private QMPartIfc sourceParentPart = null;

    /** 目标零部件。*/
    private QMPartIfc targetPart;

    /** 缓存源零部件的父树节点，在拖拽源零部件后刷新其父节点时使用。*/
    private QMNode tempParentNode = null;

    /** 缓存上次拖拽的节点。 */
    private QMNode oldnode = null;

    /** 移动的节点，即拖拽的源零部件节点 */
    private QMNode movenode = null;

    /** 缓存上次拖拽的节点对应的零部件。*/
    private QMPartIfc oldpart = null;

    /** 缓存上次拖拽的节点对应的零部件的基本信息。*/
    private QMPartMasterIfc oldpartmaster= null;

    /** 缓存复制、剪切、部分移动的非顶级件在原来父零部件中的使用关系。*/
    private PartUsageLinkIfc cachePartUsageLinkIfc = null;

    /** 缓存复制、剪切、部分移动的零部件的基本信息。*/
    private QMPartMasterIfc cachePartMasterIfc = null;
    
    //CCBegin SS10
    /** 缓存复制、剪切、部分移动的零部件的基本信息。*/
    private Vector cachePartMasterVec = new Vector();
    //CCEnd SS10
   

    private PartHelper helper = new PartHelper();

    boolean iscopy = true;

    private boolean isdraged = false;

    private Vector workablecopy=new Vector();
    
    ResourceBundle resourcebundle = ResourceBundle.getBundle(
            "com.faw_qm.part.client.main.util.QMProductManagerRB",
            RemoteProperty.getVersionLocale());

    /** 部分移动的使用数量。 */
    private float bmovenum = 1;
    public boolean fromcapp=false;
    private boolean capp=false;
    
    //add by muyp 20080623 begin
    /**应用所有*/
    final static int APPLY_TO_ALL_PARTS = 55;
  
    
    /**
     * 是否安装了产品配置模块
     */
    String hasPcfg = com.faw_qm.framework.remote.RemoteProperty.getProperty(
            "com.faw_qm.hasPcfg", "true");
    
    //缓存根节点下的零部件信息，
    //key是零部件主信息的BsoId加零部件的大版本号，值是零部件主信息值对象。
    private HashMap partMap=new HashMap();

    /**
     * 构造函数。含有一个参数。
     * @param qe QMExplorer 启明浏览器。
     */
    //管春元 add for capp
    public QMProductManager(QMExplorer qe)
    {
        this.myExplorer = qe;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(
                    listener = new MainRefreshListener());
        }
        try
        {
            jbInit();
            getConfigSpecItem();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 构造函数。多添加一个参数主要是为capp提供，参数值为true。
     * @param qe QMExplorer 启明浏览器。
     */
    //王海军 add for capp 2007.06.29
    public QMProductManager(QMExplorer qe,boolean cp)
    {
    	this.fromcapp=cp;
        this.myExplorer = qe;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(
                    listener = new MainRefreshListener());
        }
        try
        {
            jbInit();
            getConfigSpecItem();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数。
     */
    public QMProductManager()
    {
        this.threadGroup = QMCt.getContext().getThreadGroup();
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(
                    listener = new MainRefreshListener());
        }
        try
        {
            jbInit();
            getConfigSpecItem();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取浏览器。
     * @return QMExplorer 启明浏览器。
     */
    //熊代军 add for sort 2003.10.23
    public QMExplorer getExplorer()
    {
        return myExplorer;
    }

    /**
     * 设置窗格。
     * @param frame QMProductManagerJFrame 产品信息浏览器主界面。
     */
    public void setFrame(QMProductManagerJFrame frame)
    {
        this.frame = frame;
    }

    /**
     * by muyp
     * @throws Exception
     */
    private void setWorkableCopy(WorkableIfc workableIfc)
    {
    	
    	workablecopy.addElement(workableIfc);
    }
    
    /**
     * by muyp
     * @throws Exception
     */
    private Vector getWorkableCopy()
    {
    	return workablecopy;
    }
    
    /*
     * 界面初始化。
     */
    private void jbInit() throws Exception
    {
        this.setLayout(new BorderLayout());
        this.add(myExplorer, BorderLayout.CENTER);
        myExplorer.setDisplayUsesAsContents(true);
        myExplorer.addListener(this);
        myExplorer.setManager(this);
        myTree = myExplorer.getTree();
        if(myExplorer.getIsPart())
        {
//        	add whj 2008/08/08
            partPop = new PartPopupMenu(this);
           // partPop.setManager(this);
            partPop.setInvoker(myTree);
            TreeMouse treem = new TreeMouse();
            myTree.addMouseListener(treem);
            myTree.addMouseMotionListener(treem);
        }
        localize();
    }

    /*
     * 本地化信息。
     */
    protected void localize() throws QMException
    {
        ResourceBundle rb = getPropertiesRB();
        java.lang.String[] tempString;
        java.lang.String[] tempString1;
        java.lang.String[] tempString2;
        java.lang.String temp;
        try
        {
            tempString = getValueSet(rb, "partexplorer.explorer.list.headings");
            myExplorer.setListCellFont(new java.awt.Font("Dialog", 0, 12));
            tempString = getValueSet(rb, "partexplorer.explorer.list.methods");
            myExplorer.setListMethods(tempString);
            tempString = getValueSet(rb,
                    "partexplorer.explorer.list.columnAlignments");
            myExplorer.setListColumnAlignments(tempString);
            tempString = getValueSet(rb,
                    "partexplorer.explorer.list.columnSizes");
            tempString = getValueSet(rb,
                    "partexplorer.explorer.list.columnWidths");
//            //liyz add 在properties中配置是否添加排序功能
//            //测试用，部署环境注释begin
//            java.util.Properties prop=new Properties();
//            FileInputStream fis = 
//                 new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//            prop.load(fis);
//            String sort=prop.getProperty( "manager.compositor");
//            //end
            String sort = RemoteProperty.getProperty("manager.compositor");
            Boolean b=new Boolean(sort);
            String[] sort1=getValueSet(rb, "partexplorer.explorer.toolbar.iconSort");
            String[] sort2 = getValueSet(rb, "partexplorer.explorer.toolbar.textSort");
            String[] sort3 = getValueSet(rb,
                    "partexplorer.explorer.toolbar.singleSort");
            tempString = getValueSet(rb, "partexplorer.explorer.toolbar.icons");
            tempString1 = getValueSet(rb, "partexplorer.explorer.toolbar.text");
            tempString2 = getValueSet(rb,
                    "partexplorer.explorer.toolbar.single");
            if(b.booleanValue())
            {
                myExplorer.setTools(sort1,sort2,sort3);
            }
            else
            {
            	myExplorer.setTools(tempString, tempString1, tempString2);
            }
            //end
            temp = getValue(rb, "partexplorer.explorer.tree.statusBarText");
            myExplorer.setTreeStatusBarFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setTreeStatusBarText(temp);
            temp = getValue(rb, "partexplorer.explorer.list.statusBarText");
            myExplorer.setListFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setListHeadingFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setListStatusBarFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setListStatusBarText(temp);
            temp = getValue(rb, "partexplorer.explorer.tree.rootNodeText");
            myExplorer.setRootNodeText(temp);
            myExplorer.setTreeFont(new java.awt.Font("Dialog", 0, 12));
            //myExplorer.getPartTaskJPanel().addList((QMPartList)myExplorer.getList());
            //myExplorer.getPartTaskJPanel().addList((NewQMList)myExplorer.getList());//CR1
            //变量复位。
            tempString = null;
            tempString1 = null;
            tempString2 = null;
            temp = null;
            rb = null;
        }
        catch (java.beans.PropertyVetoException e)
        {
            e.printStackTrace();
        }
        catch (java.util.MissingResourceException mse)
        {
            mse.printStackTrace();
        } catch (Exception e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
    }

    /*
     * 获得资源信息。
     */
    protected ResourceBundle getPropertiesRB()
    {
        if(null == resource)
        {
            initResources();
        }
        return resource;
    }

    /**
     * 从资源文件中获得对应关键字的信息。
     * @param rb ResourceBundle 绑定的资源文件。
     * @param key String 关键字。
     * @return String[] 资源文件中关键字对应的信息组。
     */
    protected String[] getValueSet(ResourceBundle rb, String key)
    {
        String[] values = null;
        try
        {
            String value = rb.getString(key);
            StringTokenizer st = new StringTokenizer(value, ",");
            int count = st.countTokens();
            values = new String[count];
            for (int i = 0; i < count; i++)
            {
                values[i] = st.nextToken();
            }
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return values;
    }

    /**
     *从资源文件中获得对应关键字的信息。
     * @param rb ResourceBundle 绑定的资源文件。
     * @param key String 关键字。
     * @return String 资源文件中关键字对应的信息。
     */
    protected String getValue(ResourceBundle rb, String key)
    {
        String values = null;
        try
        {
            values = rb.getString(key);
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
        return values;
    }

    /**
     *初始化资源。
     */
    protected void initResources()
    {
        try
        {
            if(null == resource)
            {
                Locale locale = QMCt.getContext().getLocale();
                resource = ResourceBundle.getBundle(RESOURCE, locale);
            }
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }

    /**
     *
     * <p>Title: 控制界面的所有操作的线程。</p>
     * <p>Description: 内部类。</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: 一汽启明</p>
     * @author 李玉芬
     * @version 1.0
     */
    class WorkThread extends QMThread
    {
        /**事件的编号*/
        int action;

        /**当前零部件*/
        QMPartIfc part = null;

        /**当前零部件的主信息*/
        QMPartMasterIfc partMaster = null;

        /**
         * 构造函数。含有两个参数。
         * @param threadGroup ThreadGroup 线程组。
         * @param action int 事件的编号。
         */
        public WorkThread(ThreadGroup threadGroup, int action)
        {
            super();
            this.action = action;
        }

        /**
         * 构造函数。含有三个参数。
         * @param threadGroup ThreadGroup 线程组。
         * @param action int 事件的编号。
         * @param part QMPartIfc 当前零部件值对象。
         */
        public WorkThread(ThreadGroup threadGroup, int action, QMPartIfc part)
        {
            super();
            this.action = action;
            this.part = part;
        }

        /**
         * 构造函数。含有三个参数。
         * @param threadGroup ThreadGroup 线程组。
         * @param action int 事件的编号。
         * @param partmaster QMPartMasterIfc 当前零部件的主信息。
         */
        public WorkThread(ThreadGroup threadGroup, int action,
                QMPartMasterIfc partmaster)
        {
            super();
            this.action = action;
            this.partMaster = partmaster;
        }

        /**
         * 执行。
         */
        public void run()
        {
            try
            {
                synchronized (QMProductManager.this.cursorLock)
                {
                    waitingOnCursor++;
                }
                switch (action)
                {
                    case SET_PART_ITEM:
                        break;
                    case GENERAL_SEARCH:
                        general_search();
                        break;
                    case CREATE:
                        newObject();
                        break;
                    case SAVEAS:
                        saveAsPart();
                        break;
                    case UPDATE:
                        editSelectedObject();
                        break;
                    case VIEW:
                        viewSelectedObject();
                        break;
                    case DELETE:
                        deleteSelectedObject();
                        break;
                    case CHECKOUT:
                        checkoutSelectedObjects();
                        break;
                    case CHECKIN:
                        checkinSelectedObjects();
                        break;
                    case UNDOCHECKOUT:
                        undoCheckoutSelectedObjects();
                        break;
                    case REVISE:
                        reviseSelectedObject();
                        break;
                    case SYSCFIG:
                        setConfigSpecCommand(true);
                        break;
                    case REFRESH:
                        refreshSelectedObject();
                        break;
                    case CLEAR:
                        clear();
                        break;
                    case BUILD_GRADE_BOM:
                        buildSelectedGradeBOM();
                        break;  
//                  CCbegin SS1BUILD_ERP_BOM
                    case BUILD_ERP_BOM:  
                        buildSelectedErpBOM();
                        break; 
//                      CCend SS1
                    case BASE_ATTR_SEARCH:
                        baseAttrSearch();
                        break;
                    case IBA_SEARCH:
                        ibaSearch();
                        break;
                    case BUILD_STATISTIC_BOM:
                        buildSelectedStatisticBOM();
                        break;
                    case NEWVIEWVERSION:
                        assignViewSelectedObject();
                        break;                    //CCBegin by liunan 2008-07-30
                    //添加三种新加报表的调用方法，根据解放版本修改。
                    case BUILD_COMPARE_BOM:
                        buildSelectedCompareBOM();
                        break;
                    case BUILD_FIRSTLEVELSON_BOM:
                        buildSelectedFirstLevelSonBOM();
                        break;
                    case LOGIC:
                        bomLogic();
                        break;
                    //CCEnd by liunan 2008-07-30   
                      //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
                    case NEWLOGIC:
                    	bomNewLogic();
                        break;
                      //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
                    //CCBegin SS4
                    //CCBegin SS13
                    case FIRSTLEVEL:
                    	firstLevelList();
                        break;
                    //CCEnd SS13
                    case SUBCOMPBOM:
                    	subBom();
                    	break;
                   //CCEnd SS4
                   //CCBegin SS5
                    case ZCBOM:
                    	zcBom();
                    	break;
                    case DLZCBOM:
                    	dlzcBom();
                    	break;               
                   //CCEnd SS5
                    case ABOUT:
                        about();
                        break;
                    case TAILOR_BOM:
                        tailorBOM();
                        break;
                    //add by liun 2005.3.9
                    case GP_BOM_LIST:
                        gPBomList();
                        break;
                    case CREATE_EFFCONFIGITEM:
                        createEffConfigItem();
                        break;
                    case UPDATE_EFFECTIVITY:
                        updateSelectedEffectivity();
                        break;
                    case ADD_EFFECTIVITY:
                        addEffectivity();
                        break;
                    case ADD_EFFECTIVITY_BY_STRUCTURE:
                        populateEffectivity();
                        break;
                    case DEFINE_ALTERNATES:
                        defineAlternates();
                        break;
                    case DEFINE_SUBSTITUTES:
                        defineSubstitutes();
                        break;
                    case CLEAR_ALL:
                        clearAll();
                        break;
                    case COMPARE_STRUCTURE:
                        compareStructure();
                        break;
                    case IBA_COMPARE:
                        ibaCompare();
                        break;
                    case VIEW_EFFCONFIGITEM:
                        viewEffConfigItem();
                        break;
                    case REMOVE_EFFECTIVITY:
                        removeEffectivity();
                        break;
                    case MAINTENANCE_EFFCONFIGITEM:
                        maintenanceEffConfigItem();
                        break;
                    case CREATE_BASELINE:
                        createBaseline();
                        break;
                    case MAINTENANCE_BASELINE:
                        maintenanceBaseline();
                        break;
                    case ADD_BASELINE:
                        addBaseline();
                        break;
                    case POPULATE_BASELINE:
                        populateBaseline();
                        break;
                    case VIEW_BASELINE:
                        viewBaseline();
                        break;
                    case REMOVE_BASELINE:
                        removeBaseline();
                        break;
                    case ADDPART:
                        addPart(part);
                        break;
                    case REFRESHPART:
                        refreshPart(part);
                        break;
                    case DELETEPART:
                        deletePart(part);
                        break;
                    case VIEW_VERSION_HISTORY:
                        viewVersionHistory();
                        break;
                    case VIEW_ITERATION_HISTORY:
                        viewIterationHistory();
                        break;
                    //add by 熊代军 2003.10.21
                    case SORT:
                        sort();
                        break;
                    //add by 谢斌 2004.04.06
                    case PUBLISHIBA:
                        assignIBASelectedObject();
                        break;
                    case CATALOG_HISTORY:
                        viewCatalogHistory();
                        break;
                    //added by whj
                    case COPY:
                        copy();
                        break;
                    case PASTE:
                        paste();
                        break;
                    case CUT:
                        cut();
                        break;
                      case PUBLIC_SORT:
                        sort();
                        break;
                        //add by muyp 20080623 begin
                      case APPLY_TO_ALL_PARTS:
                    	  addAttrToOthersParts();
                    	  break;
                    	  //end
//                  	  CCBegin SS7
                      case DWSS:
                    	  DWSSPart();
                    	  break;
//                    	  CCEnd SS7
                }
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            finally
            {
                synchronized (cursorLock)
                {
                    if(0 == waitingOnCursor)
                    {
                        displayWaitIndicatorOff();
                    }
                }
                QMCt.setContextGroup(null);
            }
        } //end inner class

        /**
         * 获取父窗格。
         * @return JFrame 父窗口。
         */
        protected JFrame getParentFrame()
        {
            Component parent = getParent();
            if(null == parent)
            {
                return null;
            }
            while (!(parent instanceof JFrame))
            {
                parent = parent.getParent();
            }
            JFrame returnJFrame = (JFrame) parent;
            return returnJFrame;
        }
    }

    /**
     * 获得当前操作的线程组。
     * @return ThreadGroup 线程组。
     */
    public ThreadGroup getThreadGroup()
    {
        ThreadGroup threadgroup = QMCt.getContextGroup();
        return threadgroup;
    }

    /**
     * 检验是否选择了根结点或根结点的下一级结点。
     * @return boolean 如果选择的是根节点返回true，否则返回false。
     */
    public boolean topLevelNodeSelected()
    {
        Explorable selectedDetail = myExplorer.getSelectedDetail();
        // 如果对象是从列表中选择的,它一定不是根结点。
        if(null != selectedDetail)
        {
            return false;
        }
        String rootNodeText = (myExplorer.getRootNode()).getLabel();
        String selectedNodeText = myExplorer.getSelectedNode().getLabel();
        if(selectedNodeText.equals(rootNodeText))
        {
            return true;
        }
        QMNode parentNode = (myExplorer.getSelectedNode()).getP();
        String parentNodeText = parentNode.getLabel();
        if(parentNodeText.equals(rootNodeText))
        {
            return true;
        }
        return false;
    }

    /**
     * 获得当前选择的对象.如果是在列表中选择的,就返回列表中对象,否则在返回从树上选择的对象
     * 如果没有选择对象返回null。
     * @return Object 当前选中的对象。
     */
    public Object getSelectedObject()
    {
        Explorable busobj = null;
        QMNode node = null;
        busobj = myExplorer.getSelectedDetail();
        if(null == busobj)
        {
            node = myExplorer.getSelectedNode();
            if(null != node)
            {
                busobj = node.getObj();
            }
        }
        if(null == busobj)
        {
            return null;
        }
        else
        {
            Object returnObject = busobj.getObject();
            return returnObject;
        }
    }

    /**
     * 获得在列表中选中的对象集合,如果列表中没有被选中的对象,就返回选择的树结构上的对象。
     * @return Object[] 当前选中的对象集合。
     */
    public Object[] getSelectedObjects()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getSelectedObjects()--- begin");
        Explorable[] busobjs = null;
        Object[] objs = null;
        Explorable busobj = null;
        QMNode[] node = null;
        busobjs = myExplorer.getSelectedDetails();
        if((null == busobjs) || (busobjs.length == 0))
        {
        	////////////muyp modify 080918
            node = myExplorer.getSelectedNodes();
            objs = new Object[node.length];
            if(null != node&&node.length!=0)
            {
            	for(int i=0;i<node.length;i++)
            	{
                    busobj = node[i].getObj();
                    if(null != busobj)
                    {
                        objs[i] = busobj.getObject();
                    }
            	}

            }//end
        }
        else
        {
            objs = new Object[busobjs.length];
            for (int i = 0, j = objs.length; i < j; i++)
            {
                objs[i] = busobjs[i].getObject();
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getSelectedObjects()--- end");
        return objs;
    }

    /**
     * 在产品管理器上将修改的零部件刷新。
     * @param modifiedPart QMPartIfc 被修改的零部件。
     */
    public void refreshPart(QMPartIfc modifiedPart) {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- 刷新被修改的零部件：refreshPart(part)");
		PartDebug.trace(PartDebug.PART_CLIENT, "------- 零部件 状态为 ：   "
				+ modifiedPart.getWorkableState());

		if (null == modifiedPart) {
			return;
		}

		QMNode currentNode = null;
		QMNode refreshNode = null;
		Explorable busobj = new PartItem(modifiedPart);
		((PartItem) busobj).setConfigSpecItem(getConfigSpecItem());
		QMNode[] matchingNodes = myExplorer.findNodes(busobj);
		for (int i = 0, j = matchingNodes.length; i < j; i++) {

			refreshNode = matchingNodes[i];
			currentNode = myExplorer.getSelectedNode();
			if ((null != currentNode) && (currentNode.equals(refreshNode))) {

				// 设置节点图标。
				myExplorer.refreshNode(refreshNode, busobj, true, true);
			} else {

				// 设置节点图标。
				myExplorer.refreshNode(refreshNode, busobj, false, true);
			}
			if (refreshNode.getLevel() > 1) {

				refreshCache(((QMNode) refreshNode.getParent()), busobj);
			}
		}

		// 修改3：2007-11-14，穆勇鹏 begin，刷新被选择对象在产品信息管理器上的副本
		if (!workablecopy.isEmpty()) 
		{
			for (Iterator ite = workablecopy.iterator(); ite.hasNext();) {
				WorkableIfc work1 = (WorkableIfc) ite.next();
				QMPartIfc part = (QMPartIfc) work1;

				if (!modifiedPart.getPartNumber().equals(part.getPartNumber()))
					continue;
				Explorable busobj1 = new PartItem(part);
				((PartItem) busobj1).setConfigSpecItem(getConfigSpecItem());
				QMNode[] copymatchingNodes = myExplorer.findNodes(busobj1);
				for (int i = 0, j = copymatchingNodes.length; i < j; i++) {

					refreshNode = copymatchingNodes[i];
					currentNode = myExplorer.getSelectedNode();
					if ((null != currentNode)
							&& (currentNode.equals(refreshNode))) {

						// 设置节点图标。
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					} else {

						// 设置节点图标。
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					}
					if (refreshNode.getLevel() > 1) {

						refreshCache(((QMNode) refreshNode.getP()), busobj,
								busobj1);
					}
				}
			}
		} // end
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- 刷新被修改的零部件结束：refreshPart(part)------end()");
	}

    /**
     * 刷新缓存。
     * @param tempParentNode QMNode 更新的零部件节点。
     * @param newexp Explorable 节点包含的零部件。
     */
    private void refreshCache(QMNode node, Explorable newexp)
    {
        if(null != newexp)
        {
            String oid = newexp.getUniqueIdentity();
            int length = node.totalDetails();
            for (int i = 0; i < length; i++)
            {
                Explorable oldexp = (Explorable) node.getDetail(i);
                if(oid.equals(oldexp.getUniqueIdentity()))
                {
                    if(oldexp instanceof UsageItem)
                    {
                        ((UsageItem) oldexp).setUsesPart((PartItem) newexp);
                    }
                    else
                    {
                        node.setDetail(i, newexp);
                    }
                }
            }
            oid = null;
        }
    }

    /**
     * 刷新缓存。
     * @param tempParentNode QMNode 更新的零部件节点。
     * @param newexp Explorable 节点包含的零部件。
     * @param old Explorable 
     */
    //修改3：2007-11-14，穆勇鹏
    private void refreshCache(QMNode node, Explorable newexp, Explorable old)
    {
        if(null != newexp)
        {
            String oid = old.getUniqueIdentity();
            int length = node.totalDetails();
            for (int i = 0; i < length; i++)
            {
                Explorable oldexp = (Explorable) node.getDetail(i);
                if(oid.equals(oldexp.getUniqueIdentity()))
                {
                    if(oldexp instanceof UsageItem)
                    {
                        ((UsageItem) oldexp).setUsesPart((PartItem) newexp);
                    }
                    else
                    {
                        node.setDetail(i, newexp);
                    }
                }
            }
            oid = null;
        }
    }
    /**
     * 从产品管理器页面上将零部件删除。
     * @param deletedPart QMPartIfc 将要删除的零部件值对象。
     */
    protected void deletePart(QMPartIfc deletedPart)
    {
        QMNode currentNode = null;
        try
        {
            Explorable busobj = new PartItem(deletedPart);
            
            QMNode[] matchingNodes = myExplorer.findNodes(busobj);
           
            for (int i = 0, j = matchingNodes.length; i < j; i++)
            {
            	
                if(CheckInOutTaskLogic.isWorkingCopy(deletedPart))
                {
                    boolean flag = matchingNodes[i].getParent().equals(
                            myExplorer.getRootNode());
                    if(flag)
                    {
                        myExplorer.removeNode(matchingNodes[i]);
                        //add by 0430  begin
                     myExplorer.getPartTaskJPanel().clear();
                     //add by 0430 end

                    }
                    else
                    {
                        //获得父接点。
                        QMNode refreshNode = matchingNodes[i].getP();
                        //获得父接点对象。
                        busobj = refreshNode.getObj();
                        //获得当前选择的接点。
                        currentNode = myExplorer.getSelectedNode();
                        boolean flag1 = false;
                        if(null != currentNode)
                        {
                            //update by cdc 2004.01.17
                            flag1 = currentNode.equals(refreshNode);
                        }
                        if(flag1)
                        {
                            //设置节点图标。
                            if(busobj instanceof UsageItem)
                            {
                                ((UsageItem) busobj).setIcon();
                            }
                            else
                            {
                                ((PartItem) busobj).setIcon();
                            }
                            myExplorer.refreshNode(refreshNode, busobj, true,
                                    true);


                        } //end if (flag1)
                        else
                        {
                            if(busobj instanceof UsageItem)
                            {
                                ((UsageItem) busobj).setIcon();
                            }
                            else
                            {
                                ((PartItem) busobj).setIcon();
                            }
                            
                            myExplorer.refreshNode(refreshNode, busobj, false,
                                    true);
                        } //end if (flag1) else
                        //update by cdc 2004.01.17
                    } //end if (flag) else
                } //end if (CheckInOutTaskLogic.isWorkingCopy(deletedPart))
                else
                {
                    myExplorer.removeNode(matchingNodes[i]);
                    //add by 0430  begin
                    myExplorer.getPartTaskJPanel().clear();
                    //add by 0430 end

                } //end if (CheckInOutTaskLogic.isWorkingCopy(deletedPart)) else
            }
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * <p>Title: 增加刷新服务监听类2003-08-27。</p>
     * <p>Description: 内部类。</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: 一汽启明</p>
     * @author someone
     * @version 1.0
     */
    class MainRefreshListener implements RefreshListener
    {
        //实现接口中的抽象方法fefreshObject()。
        public void refreshObject(RefreshEvent refreshevent)
        {
            Object obj = refreshevent.getTarget();
            int i = refreshevent.getAction();
            
            //CR4 Begin 20090616 zhangq 修改原因：检入零部件提示信息有误。  
            PropertyChangeEvent[]  propertyChanges=refreshevent.getPropertyChanges();
            if(propertyChanges!=null&&propertyChanges.length>0){
				Vector vec = new Vector(2);
				vec.add(propertyChanges[0].getNewValue());
				vec.add(propertyChanges[0].getOldValue());
				refreshPart(vec);
            }
            else if(obj instanceof QMPartIfc)
            {
                switch (i)
                {
                    case RefreshEvent.CREATE:
                    {
                        addQMExplorerPart((QMPartIfc) obj, true);
                        obj = null;
                        return;
                    }
                    case RefreshEvent.UPDATE:
                    {
                        refreshExplorerPart((QMPartIfc) obj);
                        obj = null;
                        return;
                    }
                    case RefreshEvent.DELETE:
                    {
                        deleteExplorerPart((QMPartIfc) obj);
                        obj = null;
                        return;
                    }
                }
            }
//            if(obj instanceof Vector)
//            {
//                switch (i)
//                {
////                    case RefreshEvent.CREATE:
////                    {
////                        addQMExplorerPart((QMPartIfc) obj, true);
////                        obj = null;
////                        return;
////                    }
//                    case RefreshEvent.UPDATE:
//                    {
//                        refreshExplorerPart((Vector) obj);
//                        obj = null;
//                        return;
//                    }
////                    case RefreshEvent.DELETE:
////                    {
////                        deleteExplorerPart((QMPartIfc) obj);
////                        obj = null;
////                        return;
////                    }
//                }
//            }
            //CR4 End 20090616 zhangq
            obj = null;
        }

        /**
         * 构造函数。
         */
        MainRefreshListener()
        {
        }
    }

    /**
     * 创建零部件后在管理器中显示，将其加在根节点下。
     * 线程启动后调用方法addPart(QMPartIfc aPart)。
     * @param aPart QMPartIfc 将要添加的零部件值对象。
     */
    public void addExplorerPart(QMPartIfc aPart)
    {
        addPart(aPart);
    }

    /**
     * 创建零部件后在管理器中显示，将其加在根节点下。
     * 线程启动后调用方法addPart(QMPartIfc aPart)。
     * @param aPart QMPartIfc 将要添加的零部件值对象。
     */
    public void addQMExplorerPart(QMPartIfc aPart, boolean flag)
    {
        addQMPart(aPart, flag);
    }

    /**
     * 创建零部件后在管理器中显示，将其加在根节点下。
     * @param aPart QMPartIfc 创建的零部件值对象。
     */
    protected void addQMPart(QMPartIfc aPart, boolean flag)
    {
        //modify by shf 2003/09/13
        synchronized (lock)
        {
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "QMProductManager.addPart()..begin ....");
            boolean okToAdd = true;
            //构造PartItem对象。
            PartItem pi = new PartItem(aPart);
            //获得当前配置规范。
            ConfigSpecItem specItem = getConfigSpecItem();
            if(null == specItem)
            {
                setConfigSpecCommand(false);
            }
            if(null != specItem)
            {
                //把当前配置规范传到partItem中。
                pi.setConfigSpecItem(specItem);
                // 获得根节点:虚根。
                QMNode rootNode = myExplorer.getRootNode();
                QMNode[] matchingNodes = myExplorer.findNodes(pi);
                for (int i = 0, j = matchingNodes.length; i < j; i++)
                {
                    //如果节点已经在根节点下，提示信息“零部件已经在管理器中显示。”。
                    if(rootNode.equals(((QMNode) matchingNodes[i].getParent())))
                    {
                        if(!flag)
                        {
                            Object[] params = {aPart.getPartNumber()
                                    + aPart.getPartName()};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.PART_ALREADY_DISPLAYED,
                                    params);
                            showMessage(message, exceptionTitle);
                        }
                        okToAdd = false;
                        break;
                    }
                }
                if(okToAdd)
                {
                    myExplorer.addNode(rootNode, pi);
                }
            }
            PartDebug.trace(this, PartDebug.PART_CLIENT, "addPart()..end ....");
        }
    }

    /**
     * 创建零部件后在管理器中显示，将其加在根节点下。
     * @param aPart QMPartIfc 创建的零部件值对象。
     */
    protected void addPart(QMPartIfc aPart)
    {
        //modify by shf 2003/09/13
        synchronized (lock)
        {
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "QMProductManager.addPart()..begin ....");
            boolean okToAdd = true;
            //构造PartItem对象。
            PartItem pi = new PartItem(aPart);
            //获得当前配置规范。
            ConfigSpecItem specItem = getConfigSpecItem();
            if(null == specItem)
            {
                setConfigSpecCommand(false);
            }
            if(null != specItem)
            {
                //把当前配置规范传到partItem中。
                pi.setConfigSpecItem(specItem);
                // 获得根节点:虚根。
                QMNode rootNode = myExplorer.getRootNode();
                QMNode[] matchingNodes = myExplorer.findNodes(pi);
                for (int i = 0, j = matchingNodes.length; i < j; i++)
                {
                    //如果节点已经在根节点下，提示信息“零部件已经在管理器中显示。”。
                    if(rootNode.equals(((QMNode) matchingNodes[i].getParent())))
                    {
                        Object[] params = {aPart.getPartNumber()
                                + aPart.getPartName()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.PART_ALREADY_DISPLAYED,
                                params);
                        showMessage(message, exceptionTitle);
                        okToAdd = false;
                        message = null;
                        break;
                    }
                }
                if(okToAdd)
                {
                    myExplorer.addNode(rootNode, pi);
                }
            }
            PartDebug.trace(this, PartDebug.PART_CLIENT, "addPart()..end ....");
        }
    }

    /**
     * 根据当前的配置规范找part。
     * @param aPart QMPartMasterIfc
     */
    protected void addPartMaster(QMPartMasterIfc aPart)
    {
        if(getConfigSpecItem().equals(null))
        {
            setConfigSpecCommand(true);
        }
        if(null != getConfigSpecItem())
        {
            try
            {
                //获得PART集合。
                Vector vector = PartHelper.getAllVersions(aPart,
                        getConfigSpecItem().getConfigSpecInfo());
                if((null == vector) || (vector.size() == 0))
                {
                    Object[] params = {aPart.getPartNumber()};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.NO_QUALIFIED_VERSION, params);
                    showMessage(message, exceptionTitle);
                    message = null;
                    return;
                }
                int vecSize = vector.size();
                Object[] obj = null;
                ArrayList parts = new ArrayList();
                Object tempObject = null;
                for (int m = 0; m < vecSize; m++)
                {
                    tempObject = vector.elementAt(m);
                    if(tempObject instanceof Object[])
                    {
                        obj = (Object[]) tempObject;
                        //对obj中的所有元素(QMPartIfc类型)进行循环:
                        for (int j = 0, jj = obj.length; j < jj; j++)
                        {
                            parts.add(obj[j]);
                        }
                    }
                    else if(tempObject instanceof Object)
                    {
                        parts.add(tempObject);
                    }
                }
                //变量复位。
                tempObject = null;
                obj = null;
                if(0 == parts.size())
                {
                    Object[] param = {aPart.getPartNumber()};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.NO_QUALIFIED_VERSION, param);
                    showMessage(message, exceptionTitle);
                    message = null;
                }
                for (int i = 0, ii = parts.size(); i < ii; i++)
                {
                    addQMExplorerPart((QMPartIfc) parts.get(i), false);
                }
            }
            catch (QMRemoteException e)
            {
                showMessage(e.getMessage(), exceptionTitle);
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置配置规范按钮的可操作性。
     * @param flag boolean 配置规范可设置返回true，否则返回false。
     */
    protected void setConfigMenu(boolean flag)
    {
        synchronized (lock)
        {
            frame.setConfigSpec.setEnabled(flag);
        }
    }

    /**
     * 向树添加符合配置规范的零部件。
     * @param partMasters QMPartMasterIfc[] 添加的零部件。
     */
    //2003.09.16  synchronized更改
    protected void addPartMasters(QMPartMasterIfc[] partMasters)
    {
    	 addPartMasters(partMasters,true);
    }
    
    /**
     * 向树添加符合配置规范的零部件。
     * @param partMasters QMPartMasterIfc[] 添加的零部件。
     * @param isLatest boolean 是否只显示最新版本。
     */
    //2003.09.16  synchronized更改
    protected void addPartMasters(QMPartMasterIfc[] partMasters,boolean isLatest)
    {
        synchronized (lock2)
        {
            //如果配置规范为空。
            if(null == getConfigSpecItem())
            {
                setConfigSpecCommand(true);
            }
            //如果配置规范不为空。
            else
            {
                displayWaitIndicatorOn();
                ProgressService.setProgressText(QMMessage.getLocalizedMessage(
                        RESOURCE, "working", null));
                ProgressService.showProgress();
                Hashtable table = null;
                Hashtable table2 = null;
                try
                {
                    //获得PART集合。
                    table = PartHelper.getAllVersionsNow(partMasters,
                            getConfigSpecItem().getConfigSpecInfo(),isLatest);
                    if(!isLatest){
                        table2=PartHelper.getAllVersionsNow(partMasters,
                                getConfigSpecItem().getConfigSpecInfo(),true);
                    }
                }
                catch (QMRemoteException e)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "warn", null);
                    JOptionPane.showMessageDialog(frame,
                            e.getLocalizedMessage(), title,
                            JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                    title = null;
                    return;
                }
                if(null != table)
                {
                    //获得part集合。
                    ArrayList part = (ArrayList) table.get("part");
                    //获得master集合。
                    ArrayList partMaster = (ArrayList) table.get("partmaster");
                    //已经显示的零部件的集合，值是零部件的BsoId，防止重复添加相同的零部件。
                    List showPartList = new ArrayList();
                    //不符合的PartMaster的集合，值是零部件的主信息值对象。
                    List partMasterList = new ArrayList();
                    QMPartIfc partIfc = null;
                    //对每一个part执行addExplorerPart()添加到树上。
                    //在isLatest==true的情况下，添加所有的零部件。
                    //在isLatest!=true的情况下，对于先前显示的零部件，
                    //如果先前显示的版本符合配置规范，则显示该版本的最新版序的零部件。
                    //如果先前显示的版本不符合配置规范，则显示该零部件符合配置规范的最新版本。
                    for (int i = 0, j = part.size(); i < j; i++)
                    {
                        partIfc = (QMPartIfc) part.get(i);
                        boolean isAddFlag = false;
                        if(isLatest)
                        {
                            isAddFlag = true;
                        }
                        else if(partMap != null
                                && partMap.get(partIfc.getMasterBsoID()
                                        + partIfc.getVersionID()) != null)
                        {
                            isAddFlag = true;
                            partMap.remove(partIfc.getMasterBsoID()
                                    + partIfc.getVersionID());
                        }
                        if(isAddFlag)
                        {
                            addQMExplorerPart(partIfc, false);
                            showPartList.add(partIfc.getBsoID());
                        }
                    }
   
                    //对于先前显示的零部件，如果先前显示的版本不符合配置规范，则查找一个符合配置规范的最新版本。
                    if(!isLatest)
                    {
                        //key是零部件主信息的BsoID，value是符合配置规范的最新版本的零部件。
                        Hashtable latestParts = new Hashtable();
                        if(table2 != null)
                        {
                            //获得part集合。
                            ArrayList part2 = (ArrayList) table2.get("part");
                            for (int index = 0; index < part2.size(); index++)
                            {
                                partIfc = (QMPartIfc) part2.get(index);
                                latestParts.put(partIfc.getMasterBsoID(), partIfc);
                            }
                        }
                        Iterator partMapKeyIter = partMap.keySet().iterator();
                        QMPartMasterIfc partMasterIfc =null;
                        while (partMapKeyIter.hasNext())
                        {
                            partMasterIfc = (QMPartMasterIfc) partMap
                                    .get((String) partMapKeyIter.next());
                            partIfc = (QMPartIfc) latestParts.get(partMasterIfc
                                    .getBsoID());
                            if(partIfc != null
                                    && !showPartList.contains(partIfc.getBsoID()))
                            {
                                addQMExplorerPart(partIfc, false);
                                showPartList.add(partIfc.getBsoID());
                            }
                            else
                            {
                                partMasterList.add(partMasterIfc);
                            }
                        }
                    }
                    else{
                        for (int i = 0; i < partMaster.size(); i++)
                        {
                            partMasterList.add(partMaster.get(i));
                        }
                    }
                    setConfigMenu(true);
                    //如果有不符合的PartMaster。
                    if(0 != partMasterList.size())
                    {
                        PartShowMasterDialog dialog = new PartShowMasterDialog(
                                partMasterList, frame);
                        dialog.setSize(400, 300);
                        PartScreenParameter.centerWindow(dialog);
                    } //end if (0 != list.size())
                }
                ProgressService.hideProgress();
                displayWaitIndicatorOff();
            } //end if (null == getConfigSpecItem()) else
        }
    }

    /**
     * 设置配置规范。
     * @param a_configSpecItem ConfigSpecItem 当前配置规范。
     */
    public void setConfigSpecItem(ConfigSpecItem a_configSpecItem)
    {
        this.configSpecItem = a_configSpecItem;
    }

    /**
     * 获得配置规范,如果为空就初试化。
     * @return ConfigSpecItem 当前配置规范。
     */
    public ConfigSpecItem getConfigSpecItem()
    {
        if(null == configSpecItem)
        {
            initialize();
        }
        return configSpecItem;
    }

    /**
     * 初始化产品结构浏览器，获得用户当前的配置规范
     * 如果用户当前的配置规范为空则设置默认值：标准
     * 视图为空，生命周期为空，包括个人资料夹。
     */
    protected void initialize()
    {
        try
        {
            PartConfigSpecIfc configSpecIfc = (PartConfigSpecIfc) PartServiceRequest
                    .getCurrentConfigSpec();
            configSpecItem = new ConfigSpecItem(configSpecIfc);
        }
        catch (QMRemoteException qmRemoteException)
        {
            showMessage(qmRemoteException.getClientMessage(), exceptionTitle);
            qmRemoteException.printStackTrace();
        }
        catch (QMException qmException)
        {
            showMessage(qmException.getClientMessage(), exceptionTitle);
            qmException.printStackTrace();
        }
    }

    /**
     * 设置当前的配置规范。
     * @param refresh boolean = true :根据设置的筛选条件刷新管理器页面内的零部件，
     *                        =false :不刷新管理器页面内的零部件。
     */
    protected void setConfigSpecCommand(boolean refresh)
    {
        ConfigSpecItem configSpecItem = null;
        try
        {
            //获取零部件配置规范。
            PartConfigSpecIfc partConfigSpecIfc = PartHelper.getConfigSpec();
            if(null != partConfigSpecIfc)
            {
                configSpecItem = new ConfigSpecItem(
                        (PartConfigSpecIfc) partConfigSpecIfc);
            }
            else
            {
                configSpecItem = new ConfigSpecItem();
            }
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
        }
        //显示“编辑配置规范”页面并将数据库中存在的筛选条件传给该页面。
        new StructureConditionController(configSpecItem, this);
    }

    /**
     * 获得父窗格。
     * @return JFrame 父窗口。
     */
    protected JFrame getParentFrame()
    {
        Component parent = getParent();
        if(null == parent)
        {
            return null;
        }
        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        JFrame returnJFrame = (JFrame) parent;
        return returnJFrame;
    }

    /**
     * 处理"创建"命令。
     */
    public void processNewCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * 创建。
     */
    protected void newObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "newObject()..begin ....");
        TaskDelegate delegate = TaskDelegateFactory
                .instantiateTaskDelegate(QMPartInfo.class);
        if(null != delegate)
        {
            try
            {
                delegate.launchCreateTask();
            }
            catch (TaskDelegateException e)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.CREATE_TASKDELEGATE_FAILED, null);
                showMessage(message, exceptionTitle);
                message = null;
                e.printStackTrace();
            }
        }
        else
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.CREATE_TASKDELEGATE_FAILED, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
        }
        displayWaitIndicatorOff();
        PartDebug.trace(this, PartDebug.PART_CLIENT, "newObject()..end ....");
    }

    /**
     * 处理"更新"命令。
     */
    public void processEditCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * 更新。
     */
    protected void editSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..begin ....");
        try
        {
            BaseValueIfc selectObject = (BaseValueIfc) getSelectedObject();
            if(null == selectObject)
            {
                showMessage(noSelectObj, exceptionTitle);
                return;
            }
            else
            {
                //2003/12/16
                if(selectObject instanceof QMPartMasterIfc)
                {
                    showMessage(errorObject, exceptionTitle);
                    return;
                }
                if(selectObject instanceof WorkableIfc)
                {
                    //if 11:如果被其他用户检出并是零部件,提示"部件已经被他人检出!"。
                    if(CheckInOutTaskLogic
                            .isCheckedOutByOther((WorkableIfc) selectObject))
                    {
                        //if 1
                        if(selectObject instanceof QMPartIfc)
                        {
                            QMPartIfc part = (QMPartIfc) selectObject;
                            String username = "";
                            UserIfc qmprincipal = CheckInOutTaskLogic
                                    .getCheckedOutBy(part);
                            if(qmprincipal != null)
                            {
                                username = qmprincipal.getUsersDesc();
                            }
                            if(username.trim().length()==0)
                            {
                            	/*判断对象是否是工作副本。*/
                                boolean flag1 = WorkInProgressHelper
                                        .isWorkingCopy((WorkableIfc)part);
                                if(flag1)
                                {
                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                                }
                            }
                            Object[] objs = {username};
                            String message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                            objs);
                            showMessage(message, exceptionTitle);
                            message = null;
                            return;
                        }
                    } //end if 12:如果被当前用户检出。
                    else if(CheckInOutTaskLogic
                            .isCheckedOutByUser((WorkableIfc) selectObject))
                    {
                        //if 2:如果是被当前用户检出但不是工作副本,就获得工作副本。
                        if(!CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) selectObject))
                        {
                            selectObject = CheckInOutTaskLogic
                                    .getWorkingCopy((WorkableIfc) selectObject);
                        }
                    }
                    //if 3:如果不允许修改,提示"对象 * 需要检出才能修改,现在要检出吗"。
                    else if(!helper.isUpdateAllowed((FolderedIfc) selectObject))
                    {
                        QMPartIfc part = (QMPartIfc) selectObject;
                        Object[] objs = {part.getIdentity()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.CONFIRM_TO_CHECKOUT, objs);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                QMProductManagerRB.CHECKOUT_TITLE, null);
                        //显示确认对话框并获得选择的返回值。
                        int i = JOptionPane.showConfirmDialog(frame, message,
                                title, JOptionPane.YES_NO_OPTION);
                        //if 4
                        if(JOptionPane.OK_OPTION == i)
                        {
                            //个人资料夹中的零部件不允许检出。
                            //提示"当前零部件在个人资料夹中，不能检出!"。
                            if(!CheckInOutTaskLogic
                                    .isInVault((WorkableIfc) selectObject))
                            {
                                String message1 = QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                                null);
                                showMessage(message1, exceptionTitle);
                                return;
                            }
                            selectObject = getCheckOutObject((WorkableIfc) selectObject);
                            //2003/12/15
                            if(null != selectObject)
                            {
                                if(!CheckInOutTaskLogic
                                        .isWorkingCopy((WorkableIfc) selectObject))
                                {
                                    selectObject = CheckInOutTaskLogic
                                            .getWorkingCopy((WorkableIfc) selectObject);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        } //end if 4
                        else
                        {
                            return;
                        }
                    } //end if 3
                }
                //2003/12/15 加 if判断
                if(null != selectObject)
                {
                    TaskDelegate delegate = TaskDelegateFactory
                            .instantiateTaskDelegate(selectObject);
                    //2003/12/15
                    if(null != delegate)
                    {
                        delegate.setParentJFrame(getParentFrame());
                        delegate.setObject(selectObject);
                        try
                        {
                            delegate.launchUpdateTask();
                        }
                        catch (TaskDelegateException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Object[] objs = {selectObject.getBsoName()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE, QMProductManagerRB.UPDATE_FAILED,
                                objs);
                        showMessage(message, exceptionTitle);
                        message = null;
                    }
                }
            }
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"另存为"命令。
     */
    public void processSaveAsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SAVEAS);
        work.start();
    }

    /**
     * 另存为。
     */
    public void saveAsPart()
    {
        Object object = getSelectedObject();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "----------- selected object is :" + object);
        if(null == object)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part = null;
        if(object instanceof QMPartIfc)
        {
            part = (QMPartIfc) object;
            PartDebug.trace(PartDebug.PART_CLIENT, "---- object is:   " + part);
            //add by skx 2004.5.11
            try
            {
                TaskDelegate taskdelegate = new QMPartInfoTaskDelegate();
                taskdelegate.setObject(part);
                taskdelegate.setParentJFrame(frame);
                taskdelegate.launchSaveAsTask();
            }
            catch (TaskDelegateException e)
            {
                e.printStackTrace();
            }
        }
    }
    
//    CCBegin SS7
    public void processDWSSCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DWSS);
        work.start();
    }

    public void DWSSPart()
    {
    	new DWSSJDialog(this,frame);
    }
//    CCEnd SS7

    /**
     * 处理"重命名"命令。
     */
    public void processRenameCommand()
    {
        changeIdentity();
    }

    /**
     * 重命名。
     */
    protected void changeIdentity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "changeIdentity()..begin ....");
        Object object = getSelectedObject();
        PartDebug.trace(PartDebug.PART_CLIENT,
                "----------- selected object is :" + object);
        if(null == object)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        else
        {
            if(object instanceof QMPartIfc)
            {
                QMPartIfc part = (QMPartIfc) object;
                //check rename permission
                Class[] paraClass2 = {Object.class, String.class};
                Object[] objs2 = {(Object) part, QMPermission.MODIFY};
                //Collection result = null;
                try
                {
                    IBAUtility.invokeServiceMethod("AccessControlService",
                            "checkAccess", paraClass2, objs2);
                }
                catch (QMRemoteException ex)
                {
                    //flag=false;
                    String RESOURCES = "com.faw_qm.part.client.design.util.PartDesignViewRB";
                    String title = QMMessage.getLocalizedMessage(RESOURCES,
                            "exception", null);
                    JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                            title, JOptionPane.INFORMATION_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
                //end check
                QMPartMasterIfc master = (QMPartMasterIfc) part.getMaster();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.RENAME_TITLE, null);
                try
                {
                	//CCBegin by liunan 2012-04-25 打补丁v4r3_p044
                    //校验广义部件是否已被其他用户检出。
                    /*part = (QMPartIfc) CheckInOutTaskLogic.refresh(part);



                    if(CheckInOutTaskLogic.isCheckedOutByOther(part))
                    {

                        String username = "";
                        UserIfc qmprincipal = CheckInOutTaskLogic
                                .getCheckedOutBy(part);
                        if(qmprincipal != null)
                        {
                            username = qmprincipal.getUsersDesc();
                        }
                        if(username.trim().length()==0)
                        {
                        	//判断对象是否是工作副本。
                            boolean flag = WorkInProgressHelper
                                    .isWorkingCopy((WorkableIfc) part);
                            if(flag)
                            {
                            	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                            }
                        }
                        Object[] aobj = {username};
                        String message = QMMessage
                                .getLocalizedMessage(
                                        RESOURCE,
                                        QMProductManagerRB.ALREADY_CYHECKOUT_OTHER_NOTRENAME,
                                        aobj);
                        DialogFactory.showWarningDialog(this, message);
                    }
                    else
                    {
                        PartDesignRenameJDialog dialog = new PartDesignRenameJDialog(
                                getParentFrame(), title, true);
                        dialog.setIdentifiedObject((QMPartMasterIfc) master);
                        PartScreenParameter.centerWindow(dialog);
                        //变量复位。
                        part = null;
                        master = null;
                        title = null;
                    }*/
                    //CR12 begin
                    Class[] paraClass = {MasteredIfc.class};
                    // 参数值的集合。
                    Object[] objs = {master};
                    // 判断当前用户是否有重命名的权限
                    Boolean flag1 = (Boolean)IBAUtility.invokeServiceMethod("EnterpriseService", "hasRenameAccess", paraClass, objs);
                    if(flag1.booleanValue())
                    {
                        /* 校验广义部件是否已被其他用户检出。 */
                        part = (QMPartIfc)CheckInOutTaskLogic.refresh(part);
                        // 获取当前操作的零部件的视图
                        String partViewName = part.getViewName();
                        // 此零部件的第一个版本
                        QMPartIfc partfirst = new QMPartInfo();
                        // 通过一个零部件主信息找到该零部件所有版本值对象的集合。
                        Collection collection = PartServiceRequest.findPart(master);
                        Iterator iter = collection.iterator();
                        // 获取此零部件的第一个版本
                        while(iter.hasNext())
                        {
                            IteratedIfc iterIfc = (IteratedIfc)iter.next();
                            if(iterIfc instanceof QMPartIfc)
                            {
                                partfirst = (QMPartIfc)iterIfc;
                            }
                        }
                        // 如果所操作的零部件视图与此零部件第一个版本的视图相同，可以重命名！否则不可以
                        if(partViewName.equals(partfirst.getViewName()))
                        {
                            if(CheckInOutTaskLogic.isCheckedOutByOther(part))
                            {
                                String username = "";
                                UserIfc qmprincipal = CheckInOutTaskLogic.getCheckedOutBy(part);
                                if(qmprincipal != null)
                                {
                                    username = qmprincipal.getUsersDesc();
                                }
                                if(username.trim().length() == 0)
                                {
                                    /* 判断对象是否是工作副本。 */
                                    boolean flag = WorkInProgressHelper.isWorkingCopy((WorkableIfc)part);
                                    if(flag)
                                    {
                                        username = CheckInOutTaskLogic.getCheckedOutBy(part, java.util.Locale.SIMPLIFIED_CHINESE);
                                    }
                                }
                                Object[] aobj = {username};
                                String message = QMMessage.getLocalizedMessage(RESOURCE, QMProductManagerRB.ALREADY_CYHECKOUT_OTHER_NOTRENAME, aobj);
                                DialogFactory.showWarningDialog(this, message);
                            }else
                            {
                                PartDesignRenameJDialog dialog = new PartDesignRenameJDialog(getParentFrame(), title, true);
                                dialog.setIdentifiedObject((QMPartMasterIfc)master);
                                PartScreenParameter.centerWindow(dialog);
                                // 变量复位。
                                part = null;
                                master = null;
                                title = null;
                            }
                        }else
                        {
                            String message = "您所操作的零部件视图与此零部件第一个版本的视图不相同，你不能将其重命名！";
                            showMessage(message, exceptionTitle);
                            message = null;
                        }

                    }else
                    {
                        String message = "您无权限将此零部件重命名！";
                        showMessage(message, exceptionTitle);
                        message = null;
                    }//CR12 End
                    //CCEnd by liunan 2012-04-25
                }
                catch (QMException e)
                {
                    e.printStackTrace();
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.RENAME_ERROR, null);
                    showMessage(message, exceptionTitle);
                    message = null;
                }
            }
        } //end if (null == object) else
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "changeIdentity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"删除"命令。
     */
    public void processDeletePartCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * 删除。
     */
    public void deleteSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "deleteSelectedObject()..begin ....");
        Object obj = getSelectedObject();
        PartDebug.trace(PartDebug.PART_CLIENT,
                "----------- selected object is :" + obj);
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //2003/12/16
        if(obj instanceof QMPartMasterIfc)
        {
            showMessage(errorObject, exceptionTitle);
            return;
        }
        QMPartIfc part = null;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
            return;
        }
        try
        {
            part = (QMPartIfc) CheckInOutTaskLogic.refresh(part);
            if((null != part) && (isDeleteAllowed(part)))
            {
                Object[] param = {part.getPartName()};
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.DELETE_OBJECT, null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.CONFIRM_DELETE_OBJECT, param);
                //显示确认对话框并获得选择的返回值。
                int result = JOptionPane.showConfirmDialog(this, message,
                        title, JOptionPane.WARNING_MESSAGE);
                title = null;
                message = null;
                QMPartIfc original = null;
                switch (result)
                {
                    case JOptionPane.CLOSED_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        return;
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.YES_OPTION:
                    {
                        //如果选择对象为工作副本，获得原本。
                        if(CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) part))
                        {
                            original = (QMPartIfc) CheckInOutTaskLogic
                                    .getOriginalCopy((WorkableIfc) part);
                            //刷新并删除part，同时刷新管理器界面。
                        }
                        part = PartHelper.refresh(part);
                        boolean flag = PartServiceRequest.isAlterOrSub(part);
                        if(flag)
                        {
                            showMessage(alterOrSub, exceptionTitle);
                            return;
                        }
                        PartHelper.deletePart(part);
                        RefreshService.getRefreshService().dispatchRefresh(
                                getParentFrame(), RefreshEvent.DELETE, part);
                        //如果原本不为空，刷新原本和管理器界面。
                        if(null != original)
                        {
                            PartDebug.trace(PartDebug.PART_CLIENT,
                                    "deleteSelectedObject()---original is:  "
                                            + original);
                            original = PartHelper.refresh(original);
                            RefreshService.getRefreshService().dispatchRefresh(
                                    getParentFrame(), RefreshEvent.UPDATE,
                                    original);
                        }
                    }
                }
            }
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        displayWaitIndicatorOff();
    }

    /**
     * 处理"清除"命令。
     */
    public void processClearCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * 清除。
     */
    public void clear()
    {
        ResourceBundle rb = getPropertiesRB();
        String temp = getValue(rb, "partexplorer.explorer.list.statusBarText");
        //2003/12/16
        Object obj = getSelectedObject();
        if(null != obj)
        {
            if(obj instanceof QMPartMasterIfc)
            {
                showMessage(errorObject, exceptionTitle);
                return;
            }
        }
        QMNode selected_node = myExplorer.getSelectedNode();
        //条件1
        if((null != selected_node)
                && (selected_node != myExplorer.getRootNode()))
        //条件1开始
        {
            if(selected_node.getParent() == myExplorer.getRootNode())
            {
                myExplorer.checkChange(selected_node);
                myExplorer.removeNode(selected_node);
//                myExplorer.setClearAfterSign(true);
                //add by 0430 begin
                myExplorer.getPartTaskJPanel().clear();
                //add by 0430 end

            }
        } //条件1结束
        else
        {
            myExplorer.clear();
        } //条件1结束 else
        myExplorer.setListBar(temp);
        //变量复位。
        temp = null;
        obj = null;
        selected_node = null;
        displayWaitIndicatorOff();
    }

    /**
     * 处理"刷新"命令。
     */
    public void processRefreshCommand()
    {
        refreshSelectedObject();
    }

    /**
     * 刷新。CR6 Begin
     */
    public void refreshSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "refreshSelectedObject()..begin ....");
        // 获得选择的对象(树上的或者列表中的)。
        Object obj = getSelectedObject();

        // 如果选择的对象不为null。
        if(null != obj)
        {
            // 2003/12/16
            if(obj instanceof QMPartMasterIfc)
            {
                showMessage(errorObject, exceptionTitle);
                return;
            }
            // 获得从树上选择的节点。
            // 如果选择对象为QMPartIfc。
            if(obj instanceof QMPartIfc)
            {
                IteratedIfc BaseIfc = (IteratedIfc)obj;
                QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)BaseIfc.getMaster();
                PartConfigSpecIfc configSpecIfc = null;
                try
                {
                    configSpecIfc = (PartConfigSpecIfc)PartServiceRequest.getCurrentConfigSpec();
                }catch(QMException e1)
                {
                    e1.printStackTrace();
                }
                Class[] class2 = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
                Object[] param2 = {partMasterIfc, configSpecIfc};
                try
                {

                    BaseIfc = (IteratedIfc)IBAUtility.invokeServiceMethod("StandardPartService", "getPartByConfigSpec", class2, param2);
                }catch(QMException e)
                {
                    showMessage(e.getClientMessage(), exceptionTitle);
                    this.clear();
                    e.printStackTrace();
                    return;
                }
                if(BaseIfc != null)
                {
                    BaseValueIfc baseIfc = (BaseValueIfc)BaseIfc;
                    Class[] class1 = {BaseValueIfc.class};
                    Object[] param = {(BaseValueIfc)baseIfc};
                    try
                    {
                        // 调用持久化服务的方法。
                        baseIfc = (BaseValueIfc)IBAUtility.invokeServiceMethod("PersistService", "refreshInfo", class1, param);
                    }catch(QMException e)
                    {
                        showMessage(e.getClientMessage(), exceptionTitle);
                        this.clear();
                        e.printStackTrace();
                        return;
                    }
                    // 用part构造一个QMPartItem对象。
                    QMPartIfc part = (QMPartIfc)baseIfc;
                    // 转换成PartItem。
                    Explorable newObj = new PartItem(part);
                    ((PartItem)newObj).setConfigSpecItem(getConfigSpecItem());
                    // 刷新选择的节点，
                    // 设置节点图标。
                    ((PartItem)newObj).setIcon();
                    QMNode selectedNode = myExplorer.getSelectedNode();
                    myExplorer.getPartTaskJPanel().setReference(true);
                    if(myExplorer.checkChange(selectedNode))
                    {
                        QMPartIfc part1 = (QMPartIfc)myTree.getSelected().getObj().getObject();
                        // 转换成PartItem。
                        Explorable newObj1 = new PartItem(part1);
                        ((PartItem)newObj1).setConfigSpecItem(getConfigSpecItem());
                        // 刷新选择的节点，
                        // 设置节点图标。
                        ((PartItem)newObj1).setIcon();
                        QMNode selectedNode1 = myExplorer.getSelectedNode();
                        // myExplorer.refreshNode(selectedNode1, newObj1, true, false);
                        myExplorer.refreshNode(selectedNode1, newObj1, true, false);
                    }else
                    {
                        // myExplorer.refreshNode(selectedNode, newObj, true, false);
                        myExplorer.refreshNode(selectedNode, newObj, true, false);
                    }
                    // CR8 Begin  构造PartItem对象。
                    PartItem partItem = new PartItem(part);
                    // 得到浏览器树上信息相同的节点放入数组中
                    QMNode[] matchingNodes = myExplorer.findNodes(partItem);
                    // 获得根节点:虚根。
                    QMNode rootNode = myExplorer.getRootNode();
                    // 用来记录matchingNodes数组中父节点为根节点个数
                    int z = 0;
                    for(int i = 0, j = matchingNodes.length;i < j;i++)
                    {
                        if(rootNode.equals(((QMNode)matchingNodes[i].getParent())))
                        {
                            // matchingNodes数组中每有一个父节点为根节点，z就加一
                            z = ++z;
                        }
                    }
                    // 如果浏览器树上相应节点并且父节点为根节点的节点个数大于一（证明树上有相同的零部件），
                    // 弹出提示信息，删除当前这个零部件，焦点落在另一个相同的零部件上。
                   if(z > 1)
                    {
                        Object[] params = {part.getPartNumber() + part.getPartName()};
                        String message = QMMessage.getLocalizedMessage(RESOURCE, QMProductManagerRB.PART_ALREADY_DISPLAYED, params);
                        showMessage(message, exceptionTitle);
                        myExplorer.removeNode(selectedNode);
                        this.setSelectNode(partItem);
                    }
                    z = 0;
                    //Cr8 End
                    myExplorer.getPartTaskJPanel().setReference(false);
                }else
                {
                    QMNode selected_node = myExplorer.getSelectedNode();
                    myExplorer.checkChange(selected_node);
                    myExplorer.removeNode(selected_node);
                    myExplorer.getPartTaskJPanel().clear();
                    selected_node = null;
                }
                // 变量复位。
                obj = null;
                // selectedNode = null;
            }
            PartDebug.trace(this, PartDebug.PART_CLIENT, "refreshSelectedObject()..end ....");
            displayWaitIndicatorOff();
        }

    }
//CR6 End
    /**
     * 处理"检入"命令。
     */
    public void processCheckInCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKIN);
        work.start();
    }

//    /**
//     * 检入。
//     */
//    public void checkinSelectedObject()
//    {
//        PartDebug.trace(this, PartDebug.PART_CLIENT, "检入对象..开始 ....");
//        try
//        {
//            //获得选择的对象。
//            Object selected_obj = getSelectedObject();
//            //如果没有选择对象，显示信息“没有选择操作对象”。
//            if(null == selected_obj)
//            {
//                showMessage(noSelectObj, exceptionTitle);
//                return;
//            }
//            if(selected_obj instanceof QMPartMasterIfc)
//            {
//                showMessage(errorObject, exceptionTitle);
//                return;
//            }
//            if(selected_obj instanceof WorkableIfc)
//            {
//                try
//                {
//                    BaseValueIfc baseIfc = (BaseValueIfc) selected_obj;
//                    Class[] class1 = {BaseValueIfc.class};
//                    Object[] param = {(BaseValueIfc) baseIfc};
//                    //调用持久化服务的方法。
//                    baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
//                            "PersistService", "refreshInfo", class1, param);
//                    //如果该零件被其他人检出。
//                    if(CheckInOutTaskLogic
//                            .isCheckedOutByOther((WorkableIfc) baseIfc))
//                    {
//                        //if 1
//                        if(baseIfc instanceof QMPartIfc)
//                        {
//                            QMPartIfc part = (QMPartIfc) baseIfc;
//                            String username = "";
//                            UserIfc qmprincipal = CheckInOutTaskLogic
//                                    .getCheckedOutBy(part);
//                            if(qmprincipal != null)
//                            {
//                                username = qmprincipal.getUsersDesc();
//                            }
//                            else
//                            {
//                                username = part.getLocker();
//                            }
//                            if(username==null||username.trim().length()==0)
//                            {
//                            	/*判断对象是否是工作副本。*/
//                                boolean flag = WorkInProgressHelper
//                                        .isWorkingCopy((WorkableIfc) part);
//                                if(flag)
//                                {
//                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
//                                }
//                            }
//                            Object[] objs = {username};
//                            String message = QMMessage
//                                    .getLocalizedMessage(
//                                            RESOURCE,
//                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN,
//                                            objs);
//                            showMessage(message, exceptionTitle);
//                            message = null;
//                            part = null;
//                            objs = null;
//                            return;
//                        }
//                    }
//                    else
//                    {
//                    	//2007-11-07,add by muyp begin,获得被选中对象的副本
//                    	WorkableIfc workableIfc=(WorkableIfc)selected_obj;
//                    	WorkableIfc workableIfc1=null;
//                    	if(CheckInOutTaskLogic.isWorkingCopy(workableIfc))
//                    	{
//                    		this.setWorkableCopy(workableIfc);
//                    		workableIfc1=CheckInOutTaskLogic.getOriginalCopy(workableIfc);
//                    	}
//                    	else 
//                    	{
//                    		workableIfc1=workableIfc;
//                    		this.setWorkableCopy(CheckInOutTaskLogic.getWorkingCopy(workableIfc));
//                    	}//end
//                    	
//                        myExplorer.setIsCheckin(false);
//                        boolean flag = myExplorer.checkChange(myExplorer
//                                .getSelectedNode());
//                        myExplorer.setTreeValueChanged(false);
//                        JFrame frame = getParentFrame();
//                        PartsCheckInTask checkin_task = new PartsCheckInTask(frame);
//                        //修改2：2007-11-07,add by muyp,begin，判断操作对象的原本是否已经显示在左侧树上,并设置标志
//                    	if(workableIfc1 instanceof QMPartIfc)
//                    	{
//                    		QMPartIfc part = (QMPartIfc) workableIfc1;
//                    		PartItem selected_obj1=new PartItem(part);
//                    		checkin_task.setIsInTree(isInTree(selected_obj1));
//                    	}//end
//                        //设置检入的对象。
//                        if(flag)
//                        {
//                            checkin_task
//                                    .setCheckinItem((WorkableIfc) myExplorer
//                                            .getSelectedNode().getObj()
//                                            .getObject());
//                            flag = false;
//                        }
//                        else
//                            checkin_task.setCheckinItem((WorkableIfc) baseIfc);
//                        //检入。
//                        checkin_task.checkin();
//                        myExplorer.setIsCheckin(true);
//                    }
//                    //变量复位。
//                    selected_obj = null;
//                    baseIfc = null;
//                }
//                //提示:"当前尚未将*检出。"。
//                catch (NotCheckedOutException notcheckedoutexception)
//                {
//                    showMessage(notcheckedoutexception.getClientMessage(),
//                            exceptionTitle);
//                    notcheckedoutexception.printStackTrace();
//                }
//                catch (QMException qmexception)
//                {
//                    showMessage(qmexception.getClientMessage(), exceptionTitle);
//                    qmexception.printStackTrace();
//                }
//            }
//        }
//        finally
//        {
//            displayWaitIndicatorOff();
//        }
//        PartDebug.trace(this, PartDebug.PART_CLIENT,
//                "checkinSelectedObject()..结束 ....");
//    }

    /**
     * 处理"检出"命令。
     */
    public void processCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKOUT);
        work.start();
    }

//    /**
//     * 检出。
//     */
//    public void checkoutSelectedObject()
//    {
//        PartDebug.trace(this, PartDebug.PART_CLIENT,
//                "checkoutSelectedObject()..begin....");
//        Object obj = getSelectedObject();
//        //如果没有选择对象，显示信息“没有选择操作对象”。
//        if(null == obj)
//        {
//            showMessage(noSelectObj, exceptionTitle);
//            return;
//        }
//        if(obj instanceof QMPartMasterIfc)
//        {
//            showMessage(errorObject, exceptionTitle);
//            return;
//        }
//        //如果选择的对象受资料夹管理
//        if(obj instanceof FolderedIfc)
//        {
//            FolderedIfc selected_object = (FolderedIfc) obj;
//            //如果selected_object对象不为null,将该对象检出。
//            if(null != selected_object)
//            {
//                checkOutObject(selected_object);
//            }
//        }
//        obj = null;
//        displayWaitIndicatorOff();
//    }

    /**
     * 将对象检出,如果检出成功,返回true,否则返回false
     * @param foldered_obj FolderedIfc
     * @return boolean
     */
    protected boolean checkOutObject(FolderedIfc foldered_obj,Vector messageVec)
    {
        boolean successful_checkout = false;
        String messageStr="";
        try
        {
            if(foldered_obj instanceof WorkableIfc)
            {
                try
                {
                    //个人资料夹中的零部件不允许检出。
                    //提示"当前零部件在个人资料夹中，不能检出!"。
                    if(!CheckInOutTaskLogic
                            .isInVault((WorkableIfc) foldered_obj))
                    {
                        QMPartIfc part = (QMPartIfc) foldered_obj;
                        String username = "";
                        UserIfc qmprincipal = CheckInOutTaskLogic
                                .getCheckedOutBy(part);
                        if(qmprincipal != null)
                        {
                            username = qmprincipal.getUsersDesc();
                        }
                        if(username.trim().length()==0)
                        {
                        	/*判断对象是否是工作副本。*/
                            boolean flag = WorkInProgressHelper
                                    .isWorkingCopy((WorkableIfc) part);
                            if(flag)
                            {
                            	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                            }
                        }
                        String message = "";
                        if(username != null && username.length() > 0)
                        {
                            //零部件被自己检出
                            if(CheckInOutTaskLogic
                                    .isCheckedOutByUser((WorkableIfc) foldered_obj))
                                message = QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                QMProductManagerRB.ALREADY_CHECKOUT_SELF,
                                                null);
                            else
                            {
                                //零部件被他人检出
                                if(CheckInOutTaskLogic
                                        .isCheckedOutByOther((WorkableIfc) part))
                                {
                                    Object[] obj = {username};
                                    message = QMMessage
                                            .getLocalizedMessage(
                                                    RESOURCE,
                                                    QMProductManagerRB.ALREADY_CYHECKOUT_OTHER,
                                                    obj);
                                }
                                //零部件即没有被他人检出也没被自己检出说明零部件是新创建的位于个人资料夹中
                                else
                                {
                                    Object[] obj = {username};
                                    message = QMMessage
                                            .getLocalizedMessage(
                                                    RESOURCE,
                                                    QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                                    obj);
                                }
                            }
                        }
                        else if(CheckInOutTaskLogic
                                .isCheckedOut((WorkableIfc) foldered_obj))
                        {
                            message = QMMessage.getLocalizedMessage(RESOURCE,
                                    QMProductManagerRB.ALREADY_CHECKOUT_SELF,
                                    null);
                        }
                        else
                        {
                            message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                            null);
                        }
//                        showMessage(message, exceptionTitle);
                        messageStr=message;
                        return false;
                    }
                    myExplorer.setTreeValueChanged(true);
                    CheckOutTask checkout_task = new CheckOutTask(
                            getParentFrame(), CheckInOutTaskLogic
                                    .getCheckoutFolder());
                    checkout_task.setCheckoutItem((WorkableIfc) foldered_obj);
                    checkout_task.checkout();
                    successful_checkout = true;
                    messageStr="检出成功";
                }
                catch (AlreadyCheckedOutException acoe)
                {
                    successful_checkout = false;
                    String folder_name = "";
                    try
                    {
                        FolderIfc checkout_folder = CheckInOutTaskLogic
                                .getCheckoutFolder((WorkableIfc) foldered_obj);
                        //提示:"零部件*已经被检出到*资料夹中!"。
                        if(null != checkout_folder)
                        {
                            Class[] class1 = {FolderBasedIfc.class};
                            Object[] paraClass = {(FolderBasedIfc) checkout_folder};
                            folder_name = (String) IBAUtility
                                    .invokeServiceMethod("FolderService",
                                            "getPath", class1, paraClass);
                            Object[] param = {folder_name};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.AIREADY_CHECKOUT, param);
//                            showMessage(message, exceptionTitle);
                            messageStr=message;
                        }
                    }
                    //提示:"在搜索检出资料夹时发生错误."。
                    catch (QMException e)
                    {
                        Object[] param = {folder_name};
                        String message = QMMessage
                                .getLocalizedMessage(
                                        RESOURCE,
                                        QMProductManagerRB.RETRIEVE_CHECKOUT_FOLDER_FAILED,
                                        param);
//                        showMessage(message, exceptionTitle);
                        messageStr=message;
                        param = null;
                        message = null;
                        e.printStackTrace();
                    }
                    folder_name = null;
                    acoe.printStackTrace();
                }
                //提示:"该零部件已经被他人检出!"。
                catch (CheckedOutByOtherException coboe)
                {
                    try
                    {
                        successful_checkout = false;
                        String username1 = "";
                        UserIfc qmprincipal = CheckInOutTaskLogic
                                .getCheckedOutBy((QMPartIfc) foldered_obj);
                        if(qmprincipal != null)
                        {
                            username1 = qmprincipal.getUsersDesc();
                        }
                        if(username1.trim().length()==0)
                        {
                        	/*判断对象是否是工作副本。*/
                            boolean flag = WorkInProgressHelper
                                    .isWorkingCopy((WorkableIfc) (QMPartIfc) foldered_obj);
                            if(flag)
                            {
                            	username1=CheckInOutTaskLogic.getCheckedOutBy((QMPartIfc) foldered_obj,java.util.Locale.SIMPLIFIED_CHINESE);
                            }
                        }
                        Object[] objs = {username1};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                objs);
//                        showMessage(message, exceptionTitle);
                        messageStr=message;
                        message = null;
                        coboe.printStackTrace();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (Exception e)
                {
                	messageStr=e.getMessage();
//                    showMessage(e.getMessage(), exceptionTitle);
                    successful_checkout = false;
                    e.printStackTrace();
                }
            } //end if (foldered_obj instanceof WorkableIfc)
        }
        finally
        {
        	if(!successful_checkout){
        		messageStr="检出失败:"+messageStr;
        	}
        	messageStr=foldered_obj.getIdentity()+messageStr;
            displayWaitIndicatorOff();
            messageVec.add(messageStr);
        }
        return successful_checkout;
    }

    /**
     * 处理"撤消检出"命令。
     */
    public void processUndoCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UNDOCHECKOUT);
        work.start();
    }

//    /**
//     * 撤消检出。
//     */
//    public void undoCheckoutSelectedObject()
//    {
//        try
//        {
//            Object selected_obj = getSelectedObject();
//            //如果没有选择对象，显示信息“没有选择操作对象”。
//            if(null == selected_obj)
//            {
//                showMessage(noSelectObj, exceptionTitle);
//                return;
//            }
//            else
//            {
//                //2003/12/16
//                if(selected_obj instanceof QMPartMasterIfc)
//                {
//                    showMessage(errorObject, exceptionTitle);
//                    return;
//                }
//                if(selected_obj instanceof WorkableIfc)
//                {
//                    /*判断对象是否是工作副本。*/
//                    boolean flag = WorkInProgressHelper
//                            .isWorkingCopy((WorkableIfc) selected_obj);
//                    
//                    Object obj = null;
//                    //2007-11-12,add by muyp
//                    WorkableIfc workableIfc=(WorkableIfc)selected_obj;
//                	WorkableIfc workableIfc1=null;
//                	
//                    /*如果是原本，则获取对象对应的工作副本。*/
//                    if(!flag)
//                    {
//                        Class[] classes = {WorkableIfc.class};
//                        Object[] aobj = {(WorkableIfc) selected_obj};
//                        obj = IBAUtility.invokeServiceMethod(
//                                "WorkInProgressService", "workingCopyOf",
//                                classes, aobj);
//                    }
//                    /*如果对象是工作副本，或当前用户可获取对象的工作副本，则认为该用户是对象的检出者或是管理员。*/
//                    if(flag || obj != null)
//                    {
//                    	//2007-11-12,add by muyp,获得操作对象的副本
//                    	if(flag && obj == null)
//                    	{
//                    		this.setWorkableCopy(workableIfc);
//                    		workableIfc1=CheckInOutTaskLogic.getOriginalCopy(workableIfc);
//                    	}
//                    	else if(!flag && obj != null)
//                    	{
//                    		workableIfc1=workableIfc;
//                    		this.setWorkableCopy((WorkableIfc)obj);
//                    	}//end
//                    	
//                        UndoCheckoutTask undo_checkout_task = new UndoCheckoutTask(
//                                getParentFrame(), (WorkableIfc) selected_obj);
//                        //修改2：2007-11-12,add by muyp,begin,零部件是否已经显示
//                    	if(workableIfc1 instanceof QMPartIfc)
//                    	{
//                    		QMPartIfc part = (QMPartIfc) workableIfc1;
//                    		PartItem selected_obj1=new PartItem(part);
//                    		undo_checkout_task.setIsInTree(isInTree(selected_obj1));
//                    	}//end
//                        undo_checkout_task.undoCheckout();
//                    }
//                    /*否则认为当前用户既不是该对象的检出者也不是管理员，弹出该对象已被某个用户检出的提示信息。*/
//                    else if(CheckInOutTaskLogic
//                            .isCheckedOutByOther((WorkableIfc) selected_obj))
//                    {
//                        String username = "";
//                        UserIfc qmprincipal = CheckInOutTaskLogic
//                                .getCheckedOutBy((WorkableIfc) selected_obj);
//                        if(qmprincipal != null)
//                        {
//                            username = qmprincipal.getUsersDesc();
//                        }
//                        if(username.trim().length()==0)
//                        {
//                        	/*判断对象是否是工作副本。*/
//                            boolean flag1 = WorkInProgressHelper
//                                    .isWorkingCopy((WorkableIfc)selected_obj);
//                            if(flag1)
//                            {
//                            	username=CheckInOutTaskLogic.getCheckedOutBy((QMPartIfc) selected_obj,java.util.Locale.SIMPLIFIED_CHINESE);
//                            }
//                        }
//                        Object[] aobj = {username};
//                        String message = QMMessage.getLocalizedMessage(
//                                RESOURCE,
//                                QMProductManagerRB.ALREADY_CYHECKOUT_OTHER,
//                                aobj);
//                        DialogFactory.showWarningDialog(this, message);
//                    }
//                    else
//                    {
//                        Object[] aobj = {((QMPartIfc) selected_obj)
//                                .getPartNumber()};
//                        String message = QMMessage.getLocalizedMessage(
//                                RESOURCE, QMProductManagerRB.NOT_CHECKED_OUT,
//                                aobj);
//                        DialogFactory.showWarningDialog(this, message);
//                    }
//                    myExplorer.setIsCheckin(true);
//                }
//            }
//            selected_obj = null;
//        }
//        catch (QMException ex)
//        {
//            ex.printStackTrace();
//        }
//        finally
//        {
//            displayWaitIndicatorOff();
//        }
//    }
    
    /**
     * 撤消检出。
     */
    public void undoCheckoutSelectedObject(BaseValueIfc selected_obj,
			Vector messageVec) {
    	String messageStr="";
		try {
			if (selected_obj instanceof QMPartMasterIfc) {
				showMessage(errorObject, exceptionTitle);
				messageVec.add(selected_obj.getIdentity()+"撤销检出失败"+errorObject);
				return;
			}
			if (selected_obj instanceof WorkableIfc) {
				/* 判断对象是否是工作副本。 */
				boolean flag = WorkInProgressHelper
						.isWorkingCopy((WorkableIfc) selected_obj);

				Object obj = null;
				// 2007-11-12,add by muyp
				WorkableIfc workableIfc = (WorkableIfc) selected_obj;
				WorkableIfc workableIfc1 = null;

				/* 如果是原本，则获取对象对应的工作副本。 */
				if (!flag) {
					Class[] classes = { WorkableIfc.class };
					Object[] aobj = { (WorkableIfc) selected_obj };
					obj = IBAUtility.invokeServiceMethod(
							"WorkInProgressService", "workingCopyOf", classes,
							aobj);
				}
				/* 如果对象是工作副本，或当前用户可获取对象的工作副本，则认为该用户是对象的检出者或是管理员。 */
				if (flag || obj != null) {
					// 2007-11-12,add by muyp,获得操作对象的副本
					if (flag && obj == null) {
						this.setWorkableCopy(workableIfc);
						workableIfc1 = CheckInOutTaskLogic
								.getOriginalCopy(workableIfc);
					} else if (!flag && obj != null) {
						workableIfc1 = workableIfc;
						this.setWorkableCopy((WorkableIfc) obj);
					}// end

					UndoCheckoutTask undo_checkout_task = new UndoCheckoutTask(
							getParentFrame(), (WorkableIfc) selected_obj);
					// 修改2：2007-11-12,add by muyp,begin,零部件是否已经显示
					if (workableIfc1 instanceof QMPartIfc) {
						QMPartIfc part = (QMPartIfc) workableIfc1;
						PartItem selected_obj1 = new PartItem(part);
						undo_checkout_task.setIsInTree(isInTree(selected_obj1));
					}// end
					undo_checkout_task.undoCheckout();
					if(undo_checkout_task.getIsUndoCheckOutSuc())
					{
						messageStr=selected_obj.getIdentity()+"撤销检出成功";
					}
					else
					{
						messageStr=selected_obj.getIdentity()+"撤销检出失败";
					}

				}
				/* 否则认为当前用户既不是该对象的检出者也不是管理员，弹出该对象已被某个用户检出的提示信息。 */
				else if (CheckInOutTaskLogic
						.isCheckedOutByOther((WorkableIfc) selected_obj)) {
					String username = "";
					UserIfc qmprincipal = CheckInOutTaskLogic
							.getCheckedOutBy((WorkableIfc) selected_obj);
					if (qmprincipal != null) {
						username = qmprincipal.getUsersDesc();
					}
					if (username.trim().length() == 0) {
						/* 判断对象是否是工作副本。 */
						boolean flag1 = WorkInProgressHelper
								.isWorkingCopy((WorkableIfc) selected_obj);
						if (flag1) {
							username = CheckInOutTaskLogic.getCheckedOutBy(
									(QMPartIfc) selected_obj,
									java.util.Locale.SIMPLIFIED_CHINESE);
						}
					}
					Object[] aobj = { username };
					String message = QMMessage.getLocalizedMessage(RESOURCE,
							QMProductManagerRB.ALREADY_CYHECKOUT_OTHER, aobj);
//					DialogFactory.showWarningDialog(this, message);
					messageStr=selected_obj.getIdentity()+"撤销检出失败:"+message;
				} else {
					Object[] aobj = { ((QMPartIfc) selected_obj)
							.getPartNumber() };
					String message = QMMessage.getLocalizedMessage(RESOURCE,
							QMProductManagerRB.NOT_CHECKED_OUT, aobj);
//					DialogFactory.showWarningDialog(this, message);
					messageStr=selected_obj.getIdentity()+"撤销检出失败:"+message;
				}
				// myExplorer.setIsCheckin(true);
			}
			selected_obj = null;
		} catch (QMException ex) {
			ex.printStackTrace();
			messageStr=selected_obj.getIdentity()+"撤销检出失败:"+ex.getMessage();
		} finally {
			displayWaitIndicatorOff();
			messageVec.add(messageStr);
		}
	}

    /**
     * 处理"移动"命令。
     */
    public void processMoveCommand()
    {
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //判断所选择的对象是否为WorkableIfc对象。
        if(obj instanceof WorkableIfc)
        {
            //判断所选择的对象是否是检出状态。
            boolean flag = WorkInProgressHelper.isCheckedOut((WorkableIfc) obj);
            //如果为检出状态，提示异常信息“该对象已经被检出，不能被移动。”。
            if(flag)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NOT_MOVE, null);
                showMessage(message, exceptionTitle);
                message = null;
                return;
            }
        }
        //判断所选择的对象是否为FolderedIfc对象。
        if(obj instanceof FolderedIfc)
        {
            FolderEntryIfc folderedIfc = (FolderEntryIfc) getSelectedObject();
            Class[] class1 = {FolderEntryIfc.class};
            Object[] param1 = {obj};
            //调用文件夹服务的方法获得对象所在的文件夹。
            FolderIfc folder = null;
            try
            {
                folder = (FolderIfc) IBAUtility.invokeServiceMethod(
                        "FolderService", "getFolder", class1, param1);
            }
            catch (QMRemoteException ex)
            {
                folder = null;
                ex.printStackTrace();
            }
            Class[] class2 = {FolderIfc.class};
            Object[] param2 = {folder};
            //调用文件夹服务的方法判断folder是否为公共文件夹。
            Boolean flag = null;
            try
            {
                flag = (Boolean) IBAUtility.invokeServiceMethod(
                        "FolderService", "isPersonalFolder", class2, param2);
            }
            catch (QMRemoteException ex1)
            {
                flag = null;
                ex1.printStackTrace();
            }
            boolean flag1 = flag.booleanValue();
            //如果obj不在个人文件夹中，即在公共文件夹中。
            if(!flag1)
            {
                new MoveController((RevisionControlledIfc) folderedIfc);
            }
            //如果obj在个人文件夹中，提示异常信息：
            //“该对象不在公共资料夹中，不能被移到其他资料夹中。”。
            else
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NOT_IN_COMMON_FOLDER, null);
                showMessage(message, exceptionTitle);
                message = null;
                return;
            }
            folder = null;
        } //end if (obj instanceof FolderedIfc)
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * 处理"修订"命令。
     */
    public void processReviseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REVISE);
        work.start();
    }

    /**
     * 修订。
     */
    protected void reviseSelectedObject()
    {
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        VersionedIfc version = null;
        if(obj instanceof VersionedIfc)
        {
            version = (VersionedIfc) obj;
            /*         Class[] paraClass2 = {Object.class,String.class};
             Object[] objs2 = {(Object) version,QMPermission.MODIFY};
             //Collection result = null;
             try
             {
             IBAUtility.invokeServiceMethod(
             "AccessControlService", "checkAccess",
             paraClass2, objs2);
             }
             catch (QMRemoteException ex)
             {
             //flag=false;
             String RESOURCES =
             "com.faw_qm.part.client.design.util.PartDesignViewRB";
             String title = QMMessage.getLocalizedMessage(RESOURCES,
             "exception", null);
             JOptionPane.showMessageDialog(this,
             ex.getClientMessage(),
             title,
             JOptionPane.
             INFORMATION_MESSAGE);

             return;
             }
             */
            /**Class[] class2 =
             {VersionedIfc.class};
             Object[] param2 =
             {version};

             Boolean flag = null;
             try
             {
             flag = (Boolean) IBAUtility.invokeServiceMethod(
             "VersionControlService",
             "isLatestVersion", class2, param2);
             }
             catch (QMRemoteException ex1)
             {
             flag = null;
             ex1.printStackTrace();
             }
             boolean flag1 = flag.booleanValue();
             if (!flag1)
             {
             String iden = "";
             if(obj instanceof QMPartIfc)
             {
             QMPartIfc partifc = (QMPartIfc)obj;
             iden = partifc.getIdentity();
             }
             Object aobj[] = {iden};*/
            /**不是最新版本，不能修订*/
            /**String notNewView = QMMessage.getLocalizedMessage(
             RESOURCE,
             QMProductManagerRB.NOT_NEW_REVISE, aobj);
             showMessage(notNewView , exceptionTitle);
             return;
             }*/
            JFrame frame = getParentFrame();
            //判断用户对该零部件是否有"修订"权限，有"修订"权限才能修订零部件2007.03.19
            boolean flag = true;
            Class[] paraClass = {Object.class, String.class};
            Object[] para = {version, QMPermission.REVISE};
            try
            {
                 Boolean flag1 = (Boolean)IBAUtility.invokeServiceMethod(
                          "AccessControlService", "hasAccess", paraClass, para);
                 flag = flag1.booleanValue();
            }
            catch (QMException ex)
            {
                 ex.printStackTrace();
            }
            if(!flag)
            {
                  JOptionPane.showMessageDialog(frame, QMMessage.getLocalizedMessage(RESOURCE,
                       "canNotRevise", null),exceptionTitle,JOptionPane.WARNING_MESSAGE);
                  return;
            }
            ReviseTask revise_task = new ReviseTask(frame, version);
            revise_task.revise(false);
        }
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * 处理"版本历史"命令。
     */
    public void processVersionHistroyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_VERSION_HISTORY);
        work.start();
    }

    /**
     * 版本历史。
     */
    protected void viewVersionHistory()
    {
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof WorkableIfc)
        {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = workable.getBsoID();
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", objID);
            hashmap.put("mpage", "banben");
            //转到"版本历史"页面。
            RichToThinUtil.toWebPage("part_version_versionViewMain.screen",
                    hashmap);
        }
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * 处理"版序历史"命令。
     */
    public void processIterationHistroyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                VIEW_ITERATION_HISTORY);
        work.start();
    }

    /**
     * 版序历史。
     */
    protected void viewIterationHistory()
    {
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof WorkableIfc)
        {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = null;
            HashMap hashmap = new HashMap();
            if(workable instanceof IteratedIfc)
            {
                MasteredIfc master = ((IteratedIfc) workable).getMaster();
                objID = master.getBsoID();
                hashmap.put("BsoID", objID);
                hashmap.put("bsoID", ((IteratedIfc) workable).getBsoID());
                hashmap.put("mpage", "banxu");
            }
            //转到"版序历史"页面。
            RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
                    hashmap);
            //变量复位。
            workable = null;
            objID = null;
            hashmap = null;
        }
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * 处理"创建基准线"命令。
     */
    public void processCreateBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_BASELINE);
        work.start();
    }

    /**
     * 创建基准线。
     */
    protected void createBaseline()
    {
        new BaselineCreateController();
    }

    /**
     * 处理"维护基准线"命令。
     */
    public void processMaintenanceBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), MAINTENANCE_BASELINE);
        work.start();
    }

    /**
     * 维护基准线。
     */
    protected void maintenanceBaseline()
    {
        new BaselineSearchController(frame);
    }

    /**
     * 处理"查看基准线"命令。
     */
    public void processViewBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_BASELINE);
        work.start();
    }

    /**
     * 查看基准线。
     */
    protected void viewBaseline()
    {
        new BaselineAddController(4, frame);
    }

    /**
     * 处理"添加基准线"命令。
     */
    public void processAddBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ADD_BASELINE);
        work.start();
    }

    /**
     * 添加基准线。
     */
    protected void addBaseline()
    {
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误。”。
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        new BaselineAddController(0, part, frame);
        //变量复位。
        part = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT, "addBaseline()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"排序"命令。
     */
    //add by 熊代军 2003.10.21
    public void processSort()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SORT);
        work.start();
    }

    /**
     * 排序。
     */
    //add by 熊代军 2003.10.21
    protected void sort()
    {
        new SortController(this, frame);
    }

    /**
     * 处理"按结构添加基准线"命令。
     */
    public void processPopulateBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), POPULATE_BASELINE);
        work.start();
    }

    /**
     * 按结构添加基准线。
     */
    protected void populateBaseline()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "populateBaseline()..begin ....");
        QMPartIfc part = null;
        PartConfigSpecIfc configSpecIfc = configSpecItem.getConfigSpecInfo();
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误。”。
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        new BaselineAddController(1, part, configSpecIfc, frame);
        //变量复位。
        part = null;
        configSpecIfc = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "populateBaseline()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"移除基准线"命令。
     */
    public void processRemoveBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REMOVE_BASELINE);
        work.start();
    }

    /**
     * 移除基准线。
     */
    protected void removeBaseline()
    {
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误。”。
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        //将part传到“移除基准线”页面。
        new BaselineRemoveController(part, frame);
        //变量复位。
        part = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "removeBaseline()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"创建有效性方案"命令。
     */
    public void processCreateConfigItemCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_EFFCONFIGITEM);
        work.start();
    }

    /**
     * 创建有效性方案。
     */
    protected void createEffConfigItem()
    {
        new EffCreateController();
    }

    /**
     * 处理"维护有效性方案"命令。
     */
    public void processMaintenanceConfigItemCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                MAINTENANCE_EFFCONFIGITEM);
        work.start();
    }

    /**
     * 维护有效性方案。
     */
    protected void maintenanceEffConfigItem()
    {
        new EffSearchController("MAINTAIN", this, true);
    }

    /**
     * 处理"查看有效性方案"命令。
     */
    public void processViewConfigItemCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_EFFCONFIGITEM);
        work.start();
    }

    /**
     * 查看有效性方案。
     */
    protected void viewEffConfigItem()
    {
        new EffSearchController("APP", this, false);
    }

    /**
     * 处理"添加有效性"命令。
     */
    public void processAddEffectivityCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ADD_EFFECTIVITY);
        work.start();
    }

    /**
     * 添加有效性。
     */
    protected void addEffectivity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addEffectivity()..begin ....");
        QMPartIfc part = null;
        //获得选择的零件。
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //如果不是QMPartIfc对象，提示信息：“对象选择类型错误。”。
        if(!(obj instanceof QMPartIfc))
        {
            showMessage(errorObject, exceptionTitle);
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
            QMPartIfc[] parts = new QMPartIfc[] {part};
            //转入“添加有效性”页面。
            EffectivityAddJFrame frame1 = new EffectivityAddJFrame(parts, true);
            PartScreenParameter.centerWindow(frame1);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addEffectivity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"按结构添加有效性"命令。
     */
    public void processPopulateEffectivityCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                ADD_EFFECTIVITY_BY_STRUCTURE);
        work.start();
    }

    /**
     * 按结构添加有效性。
     */
    protected void populateEffectivity()
    {
        //调用“搜索有效性方案”界面。
        new EffSearchController("APP", this, true);
    }

    /**
     * 获得选择的有效性方案。
     * 此方法为“搜索有效性方案”用例而做。
     * @param a QMConfigurationItemIfc 选择的有效性方案。
     */
    //2003/12/19
    public void addConfiEff(QMConfigurationItemIfc a)
    {
        QMPartIfc[] parts = null;
        //获得有效性方案的solutionID。
        String solutionID = a.getSolutionID();
        // 如果选择的有效性方案没有零部件，提示信息：
        //“该有效性方案没有零部件方案，不能进行此项操作。”。
        if(null == solutionID)
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.NO_SOLUTION, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
            return;
        }
        PartConfigSpecIfc partConfig = null;
        try
        {
            partConfig = (PartConfigSpecIfc) PartServiceRequest
                    .findPartConfigSpecIfc();
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        Class[] class1 = {String.class};
        Object[] param1 = {solutionID};
        if(!partConfig.getStandardActive())
        {
            String message0 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.STANDCONFIG, null);
            showMessage(message0, exceptionTitle);
            return;
        }
        //调用持久化服务的方法获得零部件方案。
        QMPartMasterIfc master = null;
        try
        {
            master = (QMPartMasterIfc) IBAUtility.invokeServiceMethod(
                    "PersistService", "refreshInfo", class1, param1);
        }
        catch (QMRemoteException ex2)
        {
            master = null;
            ex2.printStackTrace();
        }
        //获得在当前筛选条件下partMasterIfc的结构下所有子件。
        try
        {
            parts = (QMPartIfc[]) PartServiceRequest
                    .getAllSubPartsByConfigSpec(master, partConfig);
        }
        catch (QMException ex1)
        {
            ex1.printStackTrace();
        }
        //如果parts为空或没有元素，提示异常信息：“该结构没有合适的版本对象。”。
        if((null == parts) || (parts.length == 0))
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.NO_QUALIFIED_VERSION, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
            return;
        }
        //调用“按结构添加有效性”页面，并将parts中的零部件信息添到列表框中。
        EffectivityAddJFrame frame1 = new EffectivityAddJFrame(parts, false);
        frame1.setConfigItem(a);
        frame1.setShow();
        PartScreenParameter.centerWindow(frame1);
        //变量复位。
        parts = null;
        solutionID = null;
        partConfig = null;
        master = null;
    }

    /**
     * 处理"修改有效性值"命令。
     */
    public void processUpdateEffValueCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE_EFFECTIVITY);
        work.start();
    }

    /**
     * 修改有效性值。
     */
    protected void updateSelectedEffectivity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "updateSelectedEffectivity()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "----------  selected object is:  " + obj);
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误。”。
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        //将part传到“修改有效性值”页面。
        new EffModifyController(part);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "updateSelectedEffectivity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"移除有效性"命令。
     */
    public void processRemoveEffectivityCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REMOVE_EFFECTIVITY);
        work.start();
    }

    /**
     * 移除有效性。
     */
    protected void removeEffectivity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "removeEffectivity()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误。”。
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        //检验当前用户的权限。
        //将part传到“移除有效性”页面。
        new EffRemoveController(part);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "removeEffectivity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"替换件"命令。
     */
    public void processDefineAlternatesCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DEFINE_ALTERNATES);
        work.start();
    }

    /**
     * 替换件。
     */
    protected void defineAlternates()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineAlternates()..begin ....");
        //获得选择的对象。
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartMasterIfc partmaster = null;
        //如果选择的对象为QMPartIfc，获得对应的QMPartMasterIfc。
        if(obj instanceof QMPartIfc)
        {
            partmaster = (QMPartMasterIfc) (((QMPartIfc) obj).getMaster());
        }
        else if(obj instanceof QMPartMasterIfc)
        {
            partmaster = (QMPartMasterIfc) obj;
        }
        else
        //显示：“对象类型错误。”。
        {
            showMessage(errorObject, exceptionTitle);
        }
        //获得对象的master的bsoID。
        String bsoID = partmaster.getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //转到编辑替换件页面。
        RichToThinUtil.toWebPage("Part-AltAndSub-EditAlterPart-001-0A.do",
                hashmap);
        //变量复位。
        obj = null;
        partmaster = null;
        bsoID = null;
        hashmap = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineAlternates()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"结构替换件"命令。
     */
    public void processDefineSubstitutesCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DEFINE_SUBSTITUTES);
        work.start();
    }

    /**
     * 结构替换件。
     */
    protected void defineSubstitutes()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineSubstitutes()..begin ....");
        Object selectedObj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == selectedObj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        // 检验是否选择了根结点或根结点的下一级结点，如果是，显示：“不能定义结构替换件。”。
        if(topLevelNodeSelected())
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.CAN_NOT_DEFINE_SUBSTITUTES, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
            return;
        }
        // 获得所选对象的master值对象。
        QMPartMasterIfc selectedMaster = null;
        if(selectedObj instanceof QMPartIfc)
        {
            selectedMaster = (QMPartMasterIfc) (((QMPartIfc) selectedObj)
                    .getMaster());
        }
        else if(selectedObj instanceof QMPartMasterIfc)
        {
            //如果selectedMaster为null，显示：“选择的对象不是零部件。”。
            selectedMaster = (QMPartMasterIfc) selectedObj;
        }
        if(null == selectedMaster)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.OBJ_NOT_PART_OR_PARTMASTER, null);
            showMessage(message, exceptionTitle);
            message = null;
        }
        else
        {
            //如果selectedMaster是在右边列表中选择的，则其父部件就是左边树上选择展开
            //的结点上的部件（因为只有选中树上结点并展开时右边列表中才会有对象存在）；
            //如果selectedMaster是在左边树上选择的，则树上的父结点对象即为其父部件。
            BaseValueIfc detailObj = null;
            BaseValueIfc parentPart = null;
            //如果selectedMaster是在右边列表中选择的，获得列表中所选对象。
            if(null != (myExplorer.getSelectedDetail()))
            {
                detailObj = (BaseValueIfc) (myExplorer.getSelectedDetail()
                        .getObject());
                //如果列表中选择的对象与树结构中选择的对象相同（bsoID相同）。
                if(detailObj.getBsoID().equals(
                        ((BaseValueIfc) getSelectedObject()).getBsoID()))
                {
                    //获得列表中选择对象在树结构中的父节点和父部件。
                    QMNode parentNode = myExplorer.getSelectedNode();
                    parentPart = (BaseValueIfc) (parentNode.getObj()
                            .getObject());
                }
            }
            // 如果对象是在树结构中获得的，直接获得它的父节点和父部件。
            if(null == parentPart)
            {
                QMNode parentNode = (myExplorer.getSelectedNode()).getP();
                parentPart = (BaseValueIfc) (parentNode.getObj().getObject());
            }
            Class[] paraClass = {String.class, BaseValueIfc.class,
                    String.class, BaseValueIfc.class};
            Object[] objs = {"PartUsageLink", (BaseValueIfc) parentPart,
                    "usedBy", (BaseValueIfc) selectedMaster};
            Collection collection = null;
            try
            {
                collection = (Collection) IBAUtility.invokeServiceMethod(
                        "PersistService", "findLinkValueInfo", paraClass, objs);
            }
            catch (QMRemoteException ex)
            {
                collection = null;
                ex.printStackTrace();
            }
            PartUsageLinkIfc usageLink = null;
            Iterator iterator = collection.iterator();
            //如果集合中没有对象，提示：“没有找到使用关系。”。
            if(!iterator.hasNext())
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NO_USAGE_RELATIONSHIP_FOUND, null);
                showMessage(message, exceptionTitle);
            }
            else
            {
                //获得使用关系及对应的父件和子件的bsoID。
                usageLink = (PartUsageLinkIfc) iterator.next();
                QMPartMasterIfc currentUsesMaster = (QMPartMasterIfc) usageLink
                        .getUses();
                QMPartIfc part = (QMPartIfc) usageLink.getUsedBy();
                String childID = currentUsesMaster.getBsoID();
                String parentID = part.getBsoID();
                String usageLinkID = usageLink.getBsoID();
                //将使用关系及对应的父件和子件的bsoID放入hashmap中。
                HashMap hashmap = new HashMap();
                hashmap.put("childID", childID);
                hashmap.put("parentID", parentID);
                hashmap.put("usageLinkID", usageLinkID);
                //转到编辑机构替换件页面。
                RichToThinUtil.toWebPage(
                        "Part-AltAndSub-EditStruAlterPart-001-0A.do", hashmap);
                usageLink = null;
                currentUsesMaster = null;
                part = null;
                childID = null;
                parentID = null;
                usageLinkID = null;
                hashmap = null;
            } //end if (!iterator.hasNext()) else
            detailObj = null;
            parentPart = null;
        } //end if (null == selectedMaster) else
        //变量复位。
        selectedObj = null;
        selectedMaster = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineSubstitutes()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"发布视图版本"命令。
     */
    public void processPublishViewVersionCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), NEWVIEWVERSION);
        work.start();
    }

    /**
     * 发布视图版本。
     */
    protected void assignViewSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "assignViewSelectedObject()..begin ....");
        VersionedIfc version = null;
        Object selected_obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == selected_obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        else
        {
            if (selected_obj instanceof VersionedIfc)
            {
                version = (VersionedIfc) selected_obj;
                JFrame frame = getParentFrame();
                //判断用户对该零部件是否有"修订视图版本"权限，有"修订视图版本"权限才能发布视图2007.01.12
                 boolean flag = true;
                 Class[] paraClass = {Object.class, String.class};
                 Object[] para = {version, QMPermission.REVISEVIEW};
                 try
                 {
                      Boolean flag1 = (Boolean)IBAUtility.invokeServiceMethod(
                               "AccessControlService", "hasAccess", paraClass, para);
                      flag = flag1.booleanValue();
                 }
                 catch (QMException ex)
                 {
                      ex.printStackTrace();

                 }
                 if(!flag)
                 {
                       JOptionPane.showMessageDialog(frame, QMMessage.getLocalizedMessage(RESOURCE,
                            "canNotReviseView", null),exceptionTitle,JOptionPane.WARNING_MESSAGE);
                       return;
                 }
                ReviseTask revise_task = new ReviseTask(frame, version);
                revise_task.revise(true);
            }
            else
            {
                //提示"所选对象不能发布视图版本。"。
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NOT_ASSIGN_VIEW_VERSION, null);
                showMessage(message, exceptionTitle);
            }
        }
        //变量复位。
        version = null;
        selected_obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "assignViewSelectedObject()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理“发布特性”命令。
     */
    //add by 谢斌2004.04.06
    public void processPublishIBACommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PUBLISHIBA);
        work.start();
    }

    /**
     * 发布特性。
     */
    //add by 谢斌2004.04.06
    protected void assignIBASelectedObject()
    {
        try
        {
            displayWaitIndicatorOn();
            BaseValueIfc selectObject = (BaseValueIfc) getSelectedObject();
            if(null == selectObject)
            {
                showMessage(noSelectObj, exceptionTitle);
                return;
            }
            else
            {
                if(selectObject instanceof QMPartMasterIfc)
                {
                    showMessage(errorObject, exceptionTitle);
                    return;
                }
                if(selectObject instanceof WorkableIfc)
                {
                    //if 11:如果被其他用户检出并是零部件,提示"部件已经被他人检出!"。
                    if(CheckInOutTaskLogic
                            .isCheckedOutByOther((WorkableIfc) selectObject))
                    {
                        //if 1
                        if(selectObject instanceof QMPartIfc)
                        {
                            QMPartIfc part = (QMPartIfc) selectObject;
                            String username = "";
                            UserIfc qmprincipal = CheckInOutTaskLogic
                                    .getCheckedOutBy(part);
                            if(qmprincipal != null)
                            {
                                username = qmprincipal.getUsersDesc();
                            }
                            if(username.trim().length()==0)
                            {
                            	/*判断对象是否是工作副本。*/
                                boolean flag1 = WorkInProgressHelper
                                        .isWorkingCopy((WorkableIfc)part);
                                if(flag1)
                                {
                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                                }
                            }
                            Object[] objs = {username};
                            String message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                            objs);
                            showMessage(message, exceptionTitle);
                            return;
                        }
                    } //end if 12:如果被当前用户检出
                    else if(CheckInOutTaskLogic
                            .isCheckedOutByUser((WorkableIfc) selectObject))
                    {
                        //if 2:如果是被当前用户检出但不是工作副本,就获得工作副本
                        if(!CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) selectObject))
                        {
                            selectObject = CheckInOutTaskLogic
                                    .getWorkingCopy((WorkableIfc) selectObject);
                        }
                    }
                }
                int i=0;
                if(selectObject instanceof QMPartIfc)
                	i=PartServiceRequest.getIBACount((QMPartIfc)selectObject);
                if(i==0)
                {
                	
                     String message = QMMessage
                             .getLocalizedMessage(
                                     RESOURCE,
                                     QMProductManagerRB.NOIBA,
                                     null);
                     showMessage(message, exceptionTitle);
                     return;
                }
                if(!PartServiceRequest.publishIBA((QMPartIfc)selectObject))
                	
                {

                    String message = QMMessage
                            .getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.ALREADYPUBLISH,
                                    null);
                    showMessage(message, exceptionTitle);
                    return;
                }
                Object value=null;
                
                if(i>0)
                {
                	Object[] objs={Integer.toString(i)};
                	String message = QMMessage
                    .getLocalizedMessage(
                            RESOURCE,
                            QMProductManagerRB.IBACOUNT,
                            objs);
                    String title=QMMessage
                    .getLocalizedMessage(
                            RESOURCE,
                            QMProductManagerRB.PUBLISHSURE,
                            null);
                	JOptionPane op=new JOptionPane(message,JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION);;
                	JDialog dialog=op.createDialog(frame, title);
                	dialog.setVisible(true);
                	value=op.getValue();
                	
                }
                int intVal=-2;
                if(value!=null)
                
                	if(value instanceof Integer)
                intVal=((Integer)value).intValue();
                if(intVal==JOptionPane.OK_OPTION)
                if(null != selectObject)
                {
                	
                    Class[] classes = {QMPartIfc.class};
                    Object[] objects = {selectObject};
                    Object obj = (QMPartMasterIfc) IBAUtility
                            .invokeServiceMethod("IBAValueService",
                                    "publishIBAToPartMaster", classes, objects);
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.SUCCESSFUL, null);
                    String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.UNSUCCESSFUL, null);
                    if((null != obj) && (obj instanceof QMPartMasterIfc))
                    {
                        DialogFactory.showFormattedInformDialog(this, message);
                        /**QMPartMasterIfc master = (QMPartMasterIfc) obj;
                         String masterBsoID = master.getBsoID();
                         HashMap hashmap = new HashMap();
                         hashmap.put("BsoID", masterBsoID);

                         //转入页面查看零部件主信息的属性。
                         RichToThinUtil.toWebPage(
                         "part_version_iterationsViewMain.screen",
                         hashmap);

                         master = null;
                         masterBsoID = null;
                         hashmap = null;*/
                    }
                    else
                    {
                        DialogFactory.showFormattedInformDialog(this, message1);
                    }
                    message1 = null;
                } //end if (null != selectObject)
            }
            selectObject = null;
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        finally
        {
            displayWaitIndicatorOff();
        }
    }

    /**
     * 处理"配置规范"命令。
     */
    public void processSetConfigSpecCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SYSCFIG);
        work.start();
    }

    /**
     * 配置规范。
     */
    protected void editConfigSpecItem()
    {
        new StructureConditionController(configSpecItem, this);
    }

    /**
     * 处理"查看"命令。
     */
    public void processViewPartCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    /**
     * 查看注册历史。
     */
    public void viewCatalogHistory()
    {
        BaseValueIfc obj = (BaseValueIfc) getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //如果所选择的对象为QMPartIfc。
        if(obj instanceof QMPartIfc)
        {
            String bsoID = obj.getBsoID();
            try
            {
                QMQuery query = new QMQuery("GenericVariantLink");
                QueryCondition cond = new QueryCondition("leftBsoID", "=",
                        bsoID);
                query.addCondition(cond);
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("PersistService");
                info.setMethodName("findValueInfo");
                Class[] theClass = {QMQuery.class};
                info.setParaClasses(theClass);
                Object[] objs = {query};
                info.setParaValues(objs);
                RequestServer server = RequestServerFactory.getRequestServer();
                Collection links = (Collection) server.request(info);
                if(links == null || links.size() == 0)
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "noCatalog", null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "prompt", null);
                    showMessage(message, title);
                    return;
                }
                else
                {
                    Iterator iter = links.iterator();
                    String gpID = "";
                    while (iter.hasNext())
                    {
                        BinaryLinkIfc link = (BinaryLinkIfc) iter.next();
                        gpID = link.getRightBsoID();
                    }
                    HashMap hashmap = new HashMap();
                    hashmap.put("bsoID", gpID);
                    RichToThinUtil.toWebPage("GPart_Base_View.do", hashmap);
                }
            }
            catch (QMException e)
            {
                e.printStackTrace();
            }
            displayWaitIndicatorOff();
        }
    }

    /**
     * 查看。
     */
    protected void viewSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "viewSelectedObject()..begin ....");
        BaseValueIfc obj = (BaseValueIfc) getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //如果所选择的对象为QMPartIfc。
        if(obj instanceof QMPartIfc)
        {
            String bsoID = obj.getBsoID();
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", bsoID);
            String hasPcfg = com.faw_qm.framework.remote.RemoteProperty
                    .getProperty("com.faw_qm.hasPcfg", "true");
            hashmap.put("infoD", hasPcfg);
            //转入页面查看零部件属性。
            RichToThinUtil.toWebPage("Part-Other-PartLookOver-001-0A.do",
                    hashmap);
            bsoID = null;
            hashmap = null;
            displayWaitIndicatorOff();
        }
        else if(obj instanceof QMPartMasterIfc)
        {
            QMPartMasterIfc master = (QMPartMasterIfc) obj;
            String masterBsoID = master.getBsoID();
            ///////////////////////modify begin////////////////////////////
            //modify by muyp 20080507
            //修改原因：对零部件主信息对象进行查看，页面显示不正确(TD1754)
          //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 TD2741    
            //Begin CR9
/*            ServiceRequestInfo info = new ServiceRequestInfo();
            info.setServiceName("StandardPartService");
            info.setMethodName("findPart");
            Class[] theClass = {QMPartMasterIfc.class};
            info.setParaClasses(theClass);
            Object[] objs = {master};
            info.setParaValues(objs);
            RequestServer server = RequestServerFactory.getRequestServer();
            try {
				Collection links = (Collection) server.request(info);
				if(links != null)
				{
                    Iterator iter = links.iterator();
                    String partID = "";
                    if (iter.hasNext())
                    {
                    	QMPartIfc link = (QMPartIfc) iter.next();
                        partID = link.getBsoID();
                    }
                    */
                    HashMap hashmap = new HashMap();
                    //modify by shf 2003/09/13
		            hashmap.put("BsoID", masterBsoID);;
//                    hashmap.put("bsoID", partID);
                    //转入页面查看零部件主信息的属性。
		            //modify by shf 2003/09/13
                    RichToThinUtil.toWebPage("part_version_iterationsViewMain-001.screen",
                    		hashmap);
                    master = null;
		            masterBsoID = null;
//                    partID = "";
                    hashmap = null;
        /*       }
			} 
   
    catch (QMRemoteException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}*/
                  //End CR9
                  //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 TD2741     
			//////////////////////modify end//////////////////////////
        }
    }

    /**
     * 从产品结构管理器的总界面获取当前筛选条件。
     * effectibityActive  “1”筛选条件为有效性；“0”不为有效性。
     * baselineActive     “1”筛选条件为基准线；“0”不为基准线。
     * standardActive     “1”筛选条件为标准；  “0”不为标准。
     * baselineID          如果筛选条件为基准线，表示基准线的bsoID。
     * configItemID        如果筛选条件为有效性，表示有效性方案的bsoID。
     * viewObjectID        如果筛选条件为有效性或标准，表示视图的bsoID。
     * effectiveUnit       如果筛选条件为有效性，表示有效性的筛选值。
     * effevtivityType     如果筛选条件为有效性，表示有效性类型。
     * state               如果筛选条件为标准，表示生命周期状态。
     * workingIncluded     如果筛选条件为标准，表示是否包括工作副本（是否可以被检出）“1”表示包括；“0”表示不包括。
     * @return HashMap 包含以上十个参数信息的集合。
     */
    protected HashMap getInfoAboutConfigItem()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getInfoAboutConfigItem()..begin ....");
        String effectivityActive = "0";
        String baselineActive = "0";
        String standardActive = "0";
        String baselineID = "";
        String configItemID = "";
        String viewObjectID = "";
        String effectiveUnit = "";
        String effectivityType = "";
        String state = "";
        String workingIncluded = "0";
        boolean flag4 = false;
        //如果为true，筛选条件为“有效性”。
        boolean flag1 = configSpecItem.getEffectivityActive();
        //如果为true，筛选条件为“基准线”。
        boolean flag2 = configSpecItem.getBaselineActive();
        //如果为true，筛选条件为“标准”。
        boolean flag3 = configSpecItem.getStandardActive();
        //如果为true，筛选条件为“标准”时对于检出的工作副本也要显示。
        if(flag3)
        {
            flag4 = configSpecItem.getCheckedOut();
        }
        //设置为“有效性”筛选条件时。
        if(flag1)
        {
            effectivityActive = "1";
            //获得有效性类型。
            if(configSpecItem.getConfigSpecInfo().getEffectivity() == null)
            {
                effectivityType = "DATE";
            }
            else
            {
                //获得有限性筛选条件的值。
                effectivityType = configSpecItem.getConfigSpecInfo()
                        .getEffectivity().getEffectivityType().toString();
            }
            effectiveUnit = configSpecItem.getConfigSpecInfo().getEffectivity()
                    .getEffectiveUnit();
            //获得筛选条件中的视图对象。
            ViewObjectIfc viewObject = null;
            if(null != configSpecItem)
            {
                viewObject = configSpecItem.getViewObjectIfc();
            }
            if(null == viewObject)
            {
                viewObjectID = "";
            }
            else if(PersistHelper.isPersistent(viewObject))
            {
                //获得有效性方案。
                viewObjectID = viewObject.getBsoID();
            }
            EffConfigurationItemIfc configItem = configSpecItem
                    .getEffConfigItemIfc();
            if(null != configItem)
            {
                //如果有效性方案存在，获得有效性方案的bsoID。
                if(PersistHelper.isPersistent(configItem))
                {
                    configItemID = configItem.getBsoID();
                }
            }
        }
        //设置为“基准线”筛选条件时。
        if(flag2)
        {
            baselineActive = "1";
            BaselineIfc baseline = configSpecItem.getBaselineIfc();
            //如果基准线存在，获取基准线的bsoID。
            if(PersistHelper.isPersistent(baseline))
            {
                baselineID = baseline.getBsoID();
            }
        } //end if(flag2)
        //设置为“标准”筛选条件时。
        if(flag3)
        {
            standardActive = "1";
            if(flag4)
            {
                workingIncluded = "1";
            }
            try
            {
                //获得筛选条件中的视图对象。
                ViewObjectIfc viewObject = configSpecItem.getViewObjectIfc();
                //如果视图存在，获得视图的bsoID。
                if(null == viewObject)
                {
                    viewObjectID = "";
                }
                else if(PersistHelper.isPersistent(viewObject))
                {
                    //获得生命周期状态。
                    viewObjectID = viewObject.getBsoID();
                }
                LifeCycleState state2 = configSpecItem.getState();
                if(null == state2)
                {
                    state = "";
                }
                else
                {
                    state = state2.getDisplay();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                showMessage(e.getMessage(), exceptionTitle);
            }
        } //end if(flag3)
        //向哈西表中添加记录。
        HashMap hashmap = new HashMap();
        hashmap.put("effectivityActive", effectivityActive);
        hashmap.put("baselineActive", baselineActive);
        hashmap.put("standardActive", standardActive);
        hashmap.put("baselineID", baselineID);
        hashmap.put("configItemID", configItemID);
        hashmap.put("viewObjectID", viewObjectID);
        hashmap.put("effectivityType", effectivityType);
        hashmap.put("effectiveUnit", effectiveUnit);
        hashmap.put("state", state);
        hashmap.put("workingIncluded", workingIncluded);
        //变量复位。
        effectivityActive = null;
        baselineActive = null;
        standardActive = null;
        baselineID = null;
        configItemID = null;
        viewObjectID = null;
        effectivityType = null;
        effectiveUnit = null;
        state = null;
        workingIncluded = null;
        return hashmap;
    }

    /**
     * 处理"结构比较"命令。
     */
    public void processCompareStructrueCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COMPARE_STRUCTURE);
        work.start();
    }

    /**
     * 结构比较。
     */
    protected void compareStructure()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "compareStructure()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();

        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误。”。
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        QMPartMasterIfc partMaster = (QMPartMasterIfc) part.getMaster();
        new DifferenceCompareController(getConfigSpecItem(), part, partMaster);
        //变量复位。
        part = null;
        obj = null;
        partMaster = null;
    }

    /**
     * 处理属性比较命令。
     */
    public void processIbaCompareCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), IBA_COMPARE);
        work.start();
    }

    /**
     * 处理属性比较命令。2005.4.20
     */
    protected void ibaCompare()
    {
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(!(obj instanceof QMPartIfc))
        {
            showMessage(errorObject, exceptionTitle);
        }
        JFrame parentFrame = getParentFrame();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                QMProductManagerRB.GENENAL_SEARCH, null);
        QmChooser chooser = new QmChooser("QMPartMaster", title, parentFrame);
        chooser.setChildQuery(false);
        chooser.setRelColWidth(new int[]{1, 1});
        chooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                if(e.getType().equals(QMQueryEvent.COMMAND))
                {
                    if(e.getCommand().equals(QmQuery.OkCMD))
                    {
                        frame.setConfigSpec.setEnabled(false);
                        QmChooser qmChooser = (QmChooser) e.getSource();
                        //获得选择的零件数组。
                        BaseValueIfc[] objs = (BaseValueIfc[]) qmChooser
                                .getSelectedDetails();
                        QMPartMasterIfc[] partMasters = new QMPartMasterIfc[objs.length];
                        for (int i = 0, j = objs.length; i < j; i++)
                        {
                            QMPartMasterIfc part = (QMPartMasterIfc) objs[i];
                            partMasters[i] = part;
                        }
                        IBACompareThread thread = new IBACompareThread(
                                partMasters);
                        thread.start();
                    }
                }
            }
        });
        //弹出搜索界面。
        chooser.setVisible(true);
        frame.setConfigSpec.setEnabled(true);
        obj = null;
        title = null;
    }

    /**
     * 属性比较线程处理。
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: 一汽启明</p>
     * @author 谢斌
     * @version 1.0
     */
    class IBACompareThread extends QMThread
    {
        /**零部件主信息的值对象数组*/
        private QMPartMasterIfc[] partMasters;

        /**
         * 属性比较的线程。
         * @param partMaster QMPartMasterIfc[] 零部件主信息的值对象数组。
         */
        public IBACompareThread(QMPartMasterIfc[] partMaster)
        {
            partMasters = partMaster;
        }

        /**
         * 执行处理属性比较的方法。
         */
        public void run()
        {
            processIbaCompare(partMasters);
        }
    }

    /**
     * 处理属性比较。
     * @param masters QMPartMasterIfc[] 将要进行比较的零部件。
     */
    private void processIbaCompare(QMPartMasterIfc[] masters)
    {
        Object obj = getSelectedObject();
        QMPartIfc sourcePart = (QMPartIfc) obj;
        if(null == getConfigSpecItem())
        {
            setConfigSpecCommand(true);
        }
        //如果配置规范不为空。
        else
        {
            Hashtable table = null;
            try
            {
                //获得PART集合。
                table = PartHelper.getAllVersionsNow(masters,
                        getConfigSpecItem().getConfigSpecInfo());
            }
            catch (QMRemoteException e)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "warn",
                        null);
                JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(),
                        title, JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
                return;
            }
            if(null != table)
            {
                ArrayList part = (ArrayList) table.get("part");
                ArrayList partMaster = (ArrayList) table.get("partmaster");
                //如果没有不符合的PartMaster。
                if(0 == partMaster.size())
                {
                    String ids = sourcePart.getBsoID();
                    for (int n = 0; n < part.size(); n++)
                    {
                        ids = ids + ":" + ((QMPartIfc) part.get(n)).getBsoID();
                    }
                    HashMap hashmap = new HashMap();
                    hashmap.put("partIDs", ids);
                    RichToThinUtil.toWebPage("Part-Other-IBACompare.screen",
                            hashmap);
                    hashmap = null;
                    ids = null;
                }
                else if(0 == part.size())
                {
                    PartShowMasterDialog dialog = new PartShowMasterDialog(
                            partMaster, frame);
                    dialog.setSize(400, 300);
                    PartScreenParameter.centerWindow(dialog);
                }
                //如果有不符合的PartMaster。
                else
                {
                    PartShowMasterDialog dialog = new PartShowMasterDialog(
                            partMaster, frame);
                    dialog.setSize(400, 300);
                    PartScreenParameter.centerWindow(dialog);
                    StringBuffer ids = new StringBuffer();
                    ids.append(sourcePart.getBsoID());
                    for (int n = 0, nn = part.size(); n < nn; n++)
                    {
                        ids.append(":" + ((QMPartIfc) part.get(n)).getBsoID());
                    }
                    HashMap hashmap = new HashMap();
                    hashmap.put("partIDs", ids);
                    RichToThinUtil.toWebPage("Part-Other-IBACompare.screen",
                            hashmap);
                    hashmap = null;
                    ids = null;
                }
            }
        } //end if (null == getConfigSpecItem()) else
    }

    /**
     * 处理"普通搜索"命令。
     */
    public void processGeneralSearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), GENERAL_SEARCH);
        work.start();
    }

    /**
     * <p>Title: 添加零部件线程。</p>
     * <p>Description: 向界面添加零部件线程类。</p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class AddPartsThread extends QMThread
    {
        /**零部件主信息的值对象数组*/
        private QMPartMasterIfc[] partMasters;

        /**
         * 添加零部件的线程。
         * @param partMaster QMPartMasterIfc[] 零部件主信息的值对象数组。
         */
        public AddPartsThread(QMPartMasterIfc[] partMaster)
        {
            partMasters = partMaster;
        }

        /**
         * 运行添加零部件的方法。
         */
        public void run()
        {
            addPartMasters(partMasters);
        }
    }

    /**
     * 普通搜索。
     */
    //2003.09.10 lgz
    protected void general_search()
    {
        JFrame parentFrame = getParentFrame();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                QMProductManagerRB.GENENAL_SEARCH, null);
        QmChooser chooser = new QmChooser("QMPartMaster", title, parentFrame);
        chooser.setChildQuery(false);
        chooser.setRelColWidth(new int[]{1, 1});
        chooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                if(e.getType().equals(QMQueryEvent.COMMAND))
                {
                    if(e.getCommand().equals(QmQuery.OkCMD))
                    {
                        frame.setConfigSpec.setEnabled(false);
                        QmChooser qmChooser = (QmChooser) e.getSource();
                        //获得选择的零件数组。
                        BaseValueIfc[] objs = (BaseValueIfc[]) qmChooser
                                .getSelectedDetails();
                        QMPartMasterIfc[] partMasters = new QMPartMasterIfc[objs.length];
                        for (int i = 0, j = objs.length; i < j; i++)
                        {
                            QMPartMasterIfc part = (QMPartMasterIfc) objs[i];
                            partMasters[i] = part;
                        }
                        AddPartsThread thread = new AddPartsThread(partMasters);
                        thread.start();
                    }
                }
            }
        });
        //弹出搜索界面。
        chooser.setVisible(true);
        title = null;
    }

    /**
     * 处理"按基本属性搜索"命令。
     */
    public void processBaseAttrSearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BASE_ATTR_SEARCH);
        work.start();
    }

    /**
     * 按基本属性搜索。
     */
    protected void baseAttrSearch()
    {
        new BasicSearchController(getFrame());
    }

    /**
     * 处理"按事物特性搜索"命令。
     */
    public void processIBASearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), IBA_SEARCH);
        work.start();
    }

    /**
     * 按事物特性搜索。
     */
    protected void ibaSearch()
    {
        new IBASearchController(getFrame(), QMPartInfo.class.getName());
    }

    /**
     * 处理"分级物料清单"命令。
     */
    public void processMaterialListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_GRADE_BOM);
        work.start();
    }

    /**
     * 分级物料清单。
     */
    protected void buildSelectedGradeBOM()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedGradeBOM()..begin ....");
        HashMap hashmap = new HashMap();
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        String bsoID = part.getBsoID();
        hashmap.put("PartID", bsoID);
        //给廋客户端传递参数：零部件的输出属性（用于界面显示）
        hashmap.put("attributeName", attrName);
        //给廋客户端传递参数：零部件的输出属性（用于调用服务）
        hashmap.put("attributeName1", attrEnglishName);
        RichToThinUtil.toWebPage("Part-Other-classifylisting-001-0A.do",
                hashmap);
        bsoID = null;
        hashmap = null;
        part = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedGradeBOM()..end ....");
        displayWaitIndicatorOff();
    }
 //CCbegin SS1
    
     /**
     * 处理"erp物料清单"命令。
     */
    public void processErpListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_ERP_BOM);
        work.start();
    }
    
    /**
     * 分级物料清单。
     */
    protected void buildSelectedErpBOM()
    {
         PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedErpBOM()..begin ....");
        System.out.println("----buildSelectedErpBOM-----");
        HashMap hashmap = new HashMap();
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        String bsoID = part.getBsoID();
        hashmap.put("PartID", bsoID);
        System.out.println("erpAttrName="+erpAttrName);
        System.out.println("erpAttrEnglishName="+erpAttrEnglishName);
        //给廋客户端传递参数：零部件的输出属性（用于界面显示）
        hashmap.put("attributeName", erpAttrName);
        //给廋客户端传递参数：零部件的输出属性（用于调用服务）
        hashmap.put("attributeName1", erpAttrEnglishName);
        RichToThinUtil.toWebPage("Part-ERP-classifylisting-001-0A.do",
                hashmap);
        bsoID = null;
        hashmap = null;
        part = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedErpBOM()..end ....");
        displayWaitIndicatorOff();
    }
    //CCend SS1
    
    /**
     * 处理"统计表物料清单"命令。
     */
    public void processBOMListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_STATISTIC_BOM);
        work.start();
    }

    /**
     * 统计表物料清单。
     */
    protected void buildSelectedStatisticBOM()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedStatisticBOM()..begin ....");
        HashMap hashmap = new HashMap();
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        String bsoID = part.getBsoID();
        hashmap.put("PartID", bsoID);
        //给廋客户端传递参数：零部件的输出属性（用于界面显示）
        hashmap.put("attributeName", attrName);
        //给廋客户端传递参数：零部件的输出属性（用于调用服务）
        hashmap.put("attributeName1", attrEnglishName);
        hashmap.put("source", "");
        hashmap.put("type", "");
        RichToThinUtil
                .toWebPage("Part-Other-PartStatistics-001-0A.do", hashmap);
        bsoID = null;
        hashmap = null;
        part = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedStatisticBOM()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"定制物料清单"命令。
     */
    public void processTailorBOMCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), TAILOR_BOM);
        work.start();
    }

    /**
     * 定制物料清单。
     */
    protected void tailorBOM()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "tailorBOM()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
        }
        //CCBegin by liunan 2008-08-05
        //改为调用带路线的定制界面，原代码如下
        //new MaterialController(part);
        new MaterialController(part,false);
        //CCBegin by liunan 2008-08-05
        PartDebug.trace(this, PartDebug.PART_CLIENT, "tailorBOM()..end ....");
        displayWaitIndicatorOff();
    }

    //add by liun 2005.3.9
    /**
     * 处理"定制广义部件物料清单"命令。
     */
    public void processGPBomListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), GP_BOM_LIST);
        work.start();
    }

    /**
     * 定制广义部件物料清单。
     */
    protected void gPBomList()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "gPBomList()..begin ....");
        QMPartIfc gPart = null;
        Object obj = getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(((QMPartIfc) obj).getBsoName().equals("GenericPart"))
        {
            gPart = (QMPartIfc) obj;
        }
        //显示“选择的对象类型错误,请选择一个广义部件。”。
        else
        {
            showMessage(noSelectGPart, exceptionTitle);
            return;
        }
        //将hashmap和part传到“定制广义部件物料清单”页面。
        GPartBOM newBOM = new GPartBOM(gPart.getBsoID());
        newBOM.setVisible(true);
        gPart = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT, "gPBomList()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * 处理"产品结构管理"命令。
     */
    public void processProductManagerCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "---- 产品结构管理器 ----");
        RichToThinUtil.toWebPage("help/zh_cn/phosphorhelp_zh_CN.jsp",
                "?index=part/PIMOverView.html");
    }

    /**
     * 处理"关于"命令。
     */
    public void processAboutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * 关于。
     */
    protected void about()
    {
    	String title=QMMessage.getLocalizedMessage(RESOURCE,
                "ProductionManager", null);
    	IntroduceDialog introduceFrame = new IntroduceDialog(this.getFrame(),title);
    	introduceFrame.setVisible();
    }

    //  added by whj
    public void processCopyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COPY);
        work.start();
    }

    /**
     *将复制的零件粘贴到选中的零部件下面
     */
    public void processPasteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PASTE);
        work.start();
    }

    /**
     * 将当前零件从当前零件的父件下移除
     */
    public void processCutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CUT);
        work.start();
    }

    /**
     * 将当前零件从当前零件的父件下移除
     */
    public void prodessDeleteStruCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PUBLIC_SORT);
        work.start();
    }

    //whj
    /**
     * 清除所有。
     */
    protected void clearAll()
    {
        myExplorer.clear();
    }

    /**
     * 添加监听器。
     * @param qmexplorerlistener QMExplorerListener 启明浏览器监听。
     */
    public synchronized void addListener(QMExplorerListener qmexplorerlistener)
    {
        myListener.addElement(qmexplorerlistener);
    }

    /**
     * 移除监听器。
     * @param qmexplorerlistener QMExplorerListener 启明浏览器监听。
     */
    public synchronized void removeListener(
            QMExplorerListener qmexplorerlistener)
    {
        myListener.removeElement(qmexplorerlistener);
    }

    /**
     * 添加ItemListener监听器。
     * @param l ItemListener Item监听。
     */
    public void addItemListener(ItemListener l)
    {
        itemListener = l;
    }

    /**
     * 移除ItemListener监听器。
     * @param l ItemListener Item监听。
     */
    public synchronized void removeItemListener(ItemListener l)
    {
        itemListener = AWTEventMulticaster.remove(itemListener, l);
    }

    /**
     * 处理Item事件。
     * @param e ItemEvent Item事件。
     */
    protected void processItemEvent(ItemEvent e)
    {
        if(itemListener != null)
        {
            itemListener.itemStateChanged(e);
        }
    }

    /**
     * 浏览器事件。
     * @param e QMExplorerEvent 启明浏览器事件。
     */
    public void explorerEvent(QMExplorerEvent e)
    {
        if(e.getType().equals(QMExplorerEvent.COMMAND))
        {
            processCommand(e);
        }
        else if(e.getType().equals(QMExplorerEvent.OPEN))
        {
            handleDoubleClickEvent(e);
        }
        else if(e.getType().equals(QMExplorerEvent.SELECT))
        {
            handleSelectEvent(e);
        }
        else if(e.getType().equals(QMExplorerEvent.EXPOSE))
        {
            handleSelectEvent(e);
        }
        else if(e.getType().equals(QMExplorerEvent.DESELECT))
        {
            handleDeselectEvent(e);
        }
    }

    /**
     * 处理选取事件。
     * @param e QMExplorerEvent  启明浏览器事件。
     */
    protected void handleSelectEvent(QMExplorerEvent e)
    {
        ItemEvent itemEvent = new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED,
                null, ItemEvent.SELECTED);
        processItemEvent(itemEvent);
    }

    /**
     * 处理取消选取事件。
     * @param e QMExplorerEvent  启明浏览器事件。
     */
    protected void handleDeselectEvent(QMExplorerEvent e)
    {
        ItemEvent itemEvent = new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED,
                null, ItemEvent.DESELECTED);
        processItemEvent(itemEvent);
    }

    /**
     * 处理鼠标双击事件，显示对象的属性页面。
     * @param e QMExplorerEvent  启明浏览器事件。
     */
    protected void handleDoubleClickEvent(QMExplorerEvent e)
    {
        processViewPartCommand();
        displayWaitIndicatorOff();
    }

    /**
     * 处理产品结构管理器的命令.当用户点击工具条上的按钮时会发出一个命令,命令名称
     * 与按钮图片的名称一样。
     * @param e QMExplorerEvent  启明浏览器事件。
     */
    protected void processCommand(QMExplorerEvent e)
    {
        PartDebug
                .trace(this, PartDebug.PART_CLIENT, "processCommand()...begin");
        if(e.getCommand().equals("part_create"))
        {
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "processComand.create begin ....");
            processNewCommand();
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "processComand.create end ....");
        }
        else if(e.getCommand().equals("part_update"))
        {
            processEditCommand();
        }
        else if(e.getCommand().equals("part_delete"))
        {
            processDeletePartCommand();
        }
        else if(e.getCommand().equals("part_view"))
        {
            processViewPartCommand();
        }
        else if(e.getCommand().equals("part_checkIn"))
        {
            processCheckInCommand();
        }
        else if(e.getCommand().equals("part_checkOut"))
        {
            processCheckOutCommand();
        }
        else if(e.getCommand().equals("part_repeal"))
        {
            processUndoCheckOutCommand();
        }
        else if(e.getCommand().equals("public_refresh"))
        {
            processRefreshCommand();
        }
        else if(e.getCommand().equals("public_clear"))
        {
            processClearCommand();
        }
        else if(e.getCommand().equals("public_search"))
        {
            processGeneralSearchCommand();
        }
        else if(e.getCommand().equals("public_copy"))
        {
            processCopyCommand();
        }
        else if(e.getCommand().equals("public_paste"))
        {
            processPasteCommand();
        }
        else if(e.getCommand().equals("public_cut"))
        {
            processCutCommand();
        }
        else if(e.getCommand().equals("public_sort"))
        {
            prodessDeleteStruCommand();
        }
        //liyz add 应用所有的图标按钮
        else if(e.getCommand().equals("part_applyAll"))
        {
        	processAddAttrCommand();
        }
        //end
        displayWaitIndicatorOff();
        PartDebug.trace(this, PartDebug.PART_CLIENT, "processCommand()...end");
    }

    /**
     * 获取JApplet。
     * @return JApplet 当前applet。
     */
    public JApplet getApplet()
    {
        return applet;
    }

    /**
     * 设置JApplet。
     * @param applet JApplet 当前applet。
     */
    public void setApplet(JApplet applet)
    {
        this.applet = applet;
        QMCt.setContext(applet);
    }
//CCBegin SS2
     /**
     * 对选择件进行结构复制。 
     */
    public void Allcopy()
    {
    	if(myTree.getSelected()!=null)
    	{
//    		System.out.println("partIfc1.getBsoID()partIfc1.getBsoID()====="+partIfc1.getBsoID());
//    		if(myTree.getSelected()== myExplorer.getRootNode())
//    		{
    			iscopy = true;
    	        //选择的对象。可能是左边树节点，也可能是右边列表中的对象。
    	        Object selectObject = getSelectedObject();
    	        //零部件基本信息BsoID。
    	        String masterID = null;
    	        if(selectObject instanceof QMPartIfc)
    	        {
    	        	String masterID1 = null;
    	            if(selectObject instanceof QMPartIfc)
    	            {
    	            	allAchePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject).getMaster();
    	                masterID1 = allAchePartMasterIfc.getBsoID();
    	            }
    	            else if(selectObject instanceof QMPartMasterIfc)
    	            {
    	                masterID1 = ((QMPartMasterIfc) selectObject).getBsoID();
    	                allAchePartMasterIfc = (QMPartMasterIfc) selectObject;
    	            }
    	            //如果不是零部件则直接获取左边树节点。
    	            if(masterID1 == null || masterID1.equals(""))
    	            {
    	            	allAchePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) myTree.getSelected()
    	                        .getObj().getObject()).getMaster();
    	            }
    	        	QMPartIfc partIfc1=(QMPartIfc) selectObject;
    	        	System.out.println("partIfc1.getBsoID()partIfc1.getBsoID()====="+partIfc1.getBsoID());
    	        	Class[] classes = {
    	        			QMPartIfc.class, PartConfigSpecIfc.class};
    	                Object[] objs = {
    	                		partIfc1, null};
    	                try
                        {
    	                Collection col = (Collection) IBAUtility
                        .invokeServiceMethod(
    	                    "StandardPartService", "getUsesPartIfcs", classes,
    	                    objs);
    	                System.out.println("colcolcolcolcol"+col.size());
    	                Iterator iter = col.iterator();
    	                while (iter.hasNext()) {
    	                	Object[] subPartList;
    	                	
    	                	subPartList=(Object[])iter.next();
    	                	
//    	                	if (subPartList != null) {
//    	                        for (int i = 0; i < col.size(); i++) {
    	                        	PartUsageLinkIfc  cachePartUsageLinkIfc1  =  (PartUsageLinkIfc)subPartList[0];
    	                        	System.out.println("cachePartUsageLinkIfc1====="+cachePartUsageLinkIfc1.getBsoID());
    	                        	vcachePartUsageLinkIfc.add(cachePartUsageLinkIfc1);
//    	                          }
    	                        
    	                      }
    	                   
    	                
    	                
                        }
    	                catch (QMRemoteException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(getFrame(), e
                                    .getClientMessage(), resourcebundle
                                    .getString("worn"),
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
    	        	
    	        }
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(getFrame(), "除了根节点不能结构复制！", resourcebundle
                       .getString("worn"), JOptionPane.ERROR_MESSAGE);
   		}
    	}
        
    /**
     * 粘贴零部件分为复制粘贴、剪切粘贴和部分粘贴。
     */
    public void Allpaste()
    {
    	System.out.println("0000000001");
        if(vcachePartUsageLinkIfc.size() == 0)
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("notselectpart"), resourcebundle
                    .getString("worn"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println("0000000002");
        //得到目标零部件
        //得到目标零部件
        Object obj=myTree.getSelected().getObj().getObject();
        System.out.println("obj="+obj);
        if(obj instanceof QMPartMasterIfc)
        {
        	 System.out.println("0000000003");
        	 JOptionPane.showMessageDialog(myExplorer.getManager(), "目标件"+((QMPartMasterInfo)obj).getPartNumber()+"不符合配置规范，不能建立结构！", "错误",
                     JOptionPane.INFORMATION_MESSAGE);
        	 return;
        }
        else
        	if(obj instanceof QMPartIfc)
        {
        QMPartIfc part = (QMPartInfo) obj;
        System.out.println("0000000004");
        //目标件是否是可更新状态
        boolean isall = helper.isAllowUpdate(part);
        if(isall)
        {
        	for (int i = 0; i < vcachePartUsageLinkIfc.size(); i++){
        		PartUsageLinkIfc all=(PartUsageLinkIfc)vcachePartUsageLinkIfc.get(i);
          boolean isparentpart = false;
          try {
            isparentpart = isParentPart(part, allAchePartMasterIfc);

          }
          catch (QMException e) {
            e.printStackTrace();
            return;
          }
          System.out.println("isparentpart"+isparentpart);
          if (isparentpart) {
            DisplayIdentity displayidentity = IdentityFactory
                .getDisplayIdentity(allAchePartMasterIfc);
            //获得对象类型 + 标识
            String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {
                s};
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                "nestingwaringtext", params);
            String title = resourcebundle.getString("nestingwaringtitle");
            JOptionPane.showMessageDialog(getParentFrame(), message, title,
                                          JOptionPane.WARNING_MESSAGE);
            displayWaitIndicatorOff();
            return;

          }
          else {
            PartUsageLinkIfc link = new PartUsageLinkInfo();
            link.setRightBsoID(part.getBsoID());
            link.setLeftBsoID(all.getLeftBsoID());
            System.out.println("1111111");
            if (iscopy) {
            	 System.out.println("all="+all);
              if (all != null) {
              	//修改4 20080225 zhnagq begin
                link.setQuantity(all.getQuantity());
                link.setDefaultUnit(all.getDefaultUnit());
                System.out.println("222222222222");
              }
              else {
                link.setQuantity(1);
                link.setDefaultUnit(Unit.getUnitDefault());
              }
              try {
                PartHelper.savePartUsageLink(link);
              }
              catch (Exception exc) {
                exc.printStackTrace();
              }
            }
            else {            
              link.setQuantity(bmovenum);
              if (all != null) {
                link.setDefaultUnit(all.getDefaultUnit());
              }
              else {
                link.setDefaultUnit(Unit.getUnitDefault());
              }
              try {
                PartHelper.savePartUsageLink(link);
              }
              catch (Exception exc) {
                exc.printStackTrace();
              }
              sourcePartMaster = null;
//              cachePartUsageLinkIfc = null;
            }
            Explorable newObj = new PartItem(part);
            ( (PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
            //设置节点图标。
            ( (PartItem) newObj).setIcon();
            myExplorer.refreshNode(myTree.getSelected(), newObj, true, false);
          }
        }
        }
       
        //目标零部件不是可更新状态。
        else
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("noupdate"), resourcebundle.getString("worn"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
        vcachePartUsageLinkIfc.clear();
    }
    }
//CCEnd SS2
    /**
     * 获得检出后的对象,如果检出成功,返回检出后的对象,如果检出失败,返回null。
     * @param workable WorkableIfc Workable值对象。
     * @return WorkableIfc Workable值对象。
     */
    protected WorkableIfc getCheckOutObject(WorkableIfc workable)
    throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getCheckOutObject()..begin ....");
        try
        {
            CheckOutTask checkout_task = new CheckOutTask(getParentFrame(),
                    CheckInOutTaskLogic.getCheckoutFolder());
            checkout_task.setCheckoutItem(workable);
            workable = checkout_task.checkout();
        }
        catch (AlreadyCheckedOutException acoe)
        {
            try
            {
                FolderIfc checkout_folder = CheckInOutTaskLogic
                        .getCheckoutFolder(workable);
                String folder_name = "";
                if(null != checkout_folder)
                {
                    Class[] class1 = {FolderBasedIfc.class};
                    Object[] paraClass = {(FolderBasedIfc) checkout_folder};
                    folder_name = (String) IBAUtility.invokeServiceMethod(
                            "FolderService", "getPath", class1, paraClass);
                    if(workable instanceof QMPartIfc)
                    {
                        Object[] param = {folder_name};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE, QMProductManagerRB.AIREADY_CHECKOUT,
                                param);
                        showMessage(message, exceptionTitle);
                        message = null;
                    }
                }
            }
            catch (QMRemoteException e)
            {
                e.printStackTrace();
            }
            catch (CheckInOutException e)
            {
                e.printStackTrace();
            }
            catch (QMException e)
            {
                e.printStackTrace();
            }
            workable = null;
            acoe.printStackTrace();
        }
        //2003/12/15 有异常信息后 workable = null;
        catch (CheckedOutByOtherException coboe)
        {
            workable = null;
            coboe.printStackTrace();
            showMessage(coboe.getClientMessage(), exceptionTitle);
        }
        catch (ObjectNoLongerExistsException onle)
        {
            onle.printStackTrace();
            workable = null;
        }

        return workable;
    }

    /**
     * 判断对象是否允许被删除。
     * @param part QMPartIfc 进行判断的零部件值对象。
     * @return boolean 返回true则允许被删除，否则不允许。
     */
    protected boolean isDeleteAllowed(QMPartIfc part)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "isDeleteAllowed()..begin ....");
        boolean allowed = false;
        try
        {
            if((CheckInOutTaskLogic.isCheckedOut(part))
                    && (!CheckInOutTaskLogic.isWorkingCopy(part)))
            {
                //Object[] params =
                //        {part.getPartName()};
                String username = "";
                UserIfc qmprincipal = CheckInOutTaskLogic.getCheckedOutBy(part);
                if(qmprincipal != null)
                {
                    username = qmprincipal.getUsersDesc();
                }
                if(username.trim().length()==0)
                {
                	/*判断对象是否是工作副本。*/
                    boolean flag1 = WorkInProgressHelper
                            .isWorkingCopy((WorkableIfc)part);
                    if(flag1)
                    {
                    	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                    }
                }
                Object[] aobj = {username};
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.CANNOT_DELETE_CHECKED_OUT, aobj);
                showMessage(message, exceptionTitle);
                message = null;
            }
            else
            {
                allowed = true;
            }
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "isDeleteAllowed()..end....");
        return allowed;
    }

    /**
     * 处理产品结构管理器的命令.当用户点击工具条上的按钮时会发出一个命令,命令名称
     * 与按钮图片的名称一样。
     * @param actionevent ActionEvent 传来的活动事件。
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getActionCommand().equals("create"))
        {
            processNewCommand();
        }
        else if(actionevent.getActionCommand().equals("update"))
        {
            processEditCommand();
        }
        else if(actionevent.getActionCommand().equals("delete"))
        {
            processDeletePartCommand();
        }
        else if(actionevent.getActionCommand().equals("viewprt"))
        {
            processViewPartCommand();
        }
        else if(actionevent.getActionCommand().equals("checkin"))
        {
            processCheckInCommand();
        }
        else if(actionevent.getActionCommand().equals("checkout"))
        {
            processCheckOutCommand();
        }
        else if(actionevent.getActionCommand().equals("undocheckout"))
        {
            processUndoCheckOutCommand();
        }
        else if(actionevent.getActionCommand().equals("refresh"))
        {
            processRefreshCommand();
        }
        else if(actionevent.getActionCommand().equals("clear"))
        {
            processClearCommand();
        }
        else if(actionevent.getActionCommand().equals("gsearch"))
        {
            processGeneralSearchCommand();
        }
        displayWaitIndicatorOff();
    }

    /**
     * 值改变。
     * @param e TreeSelectionEvent 树的选择事件。
     */
    public void valueChanged(TreeSelectionEvent e)
    {
        itemListener.itemStateChanged(null);
    }

    /**
     * 根据当前设置的配置规范刷新树。
     * 此方法被 StructConditionController调用。
     */
    public void processNewConfigSpec()
    {
        //2003/09/22 lgz
        synchronized (lock)
        {
//            myExplorer.setIsCheckin(false);
//            if(myExplorer.getSelectedNode()!=null) {
//                myExplorer.checkChange(myExplorer
//                        .getSelectedNode());
//            }
            boolean isChanged = myExplorer.isChange();
            boolean isSaved = false;
            if(isChanged)
            {
                isSaved = myExplorer.saveChange();
            }
            if(!isSaved && isChanged)
            {
                refreshPart(myExplorer.getPartItem().getPart());
            }

            //获得根节点。
            QMNode root = myExplorer.getRootNode();
            Vector children = new Vector();
            //获得子节点，并将下一级节点的对象添加到Vector中。
            for (QMNode n = root.getC(); null != n; n = n.getS())
            {
                Explorable e = n.getObj();
                children.addElement(e.getObject());
            }
            partMap=new HashMap();
            QMPartMasterIfc[] masters = new QMPartMasterIfc[children.size()];
            for (int i = 0, j = children.size(); i < j; i++)
            {
                //如果是 master直接加入到数组。
                if(children.elementAt(i) instanceof QMPartMasterIfc)
                {
                    masters[i] = (QMPartMasterIfc) children.elementAt(i);
                }
                //如果是part获得其master加入数组。
                else if((children.elementAt(i)) instanceof QMPartIfc)
                {
                    QMPartIfc part = (QMPartIfc) children.elementAt(i);
                    masters[i] = (QMPartMasterIfc) part.getMaster();
                    partMap.put(part.getMasterBsoID()+part.getVersionID(), 
                            part.getMaster());
                }
                else
                {
                    return;
                }
            }
            myExplorer.clear();
            addPartMasters(masters,false);
            myExplorer.getPartTaskJPanel().clear();//CR7
//            myExplorer.setIsCheckin(true);
        }
    }

    /**
     * 刷新管理器中的零部件。
     * @param modifiedPart QMPartIfc 被刷新的零部件。
     */
    protected void refreshExplorerPart(QMPartIfc modifiedPart)
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESHPART,
                modifiedPart);
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
    
    //CR4 Begin 20090616 zhangq 修改原因：检入零部件提示信息有误。
//    /**
//     * 刷新管理器中的零部件。
//     * @param modifiedPart QMPartIfc 被刷新的零部件。
//     */
//    protected void refreshExplorerPart(Vector parts)
//    {
//    	refreshPart(parts);
////        WorkThread work = new WorkThread(getThreadGroup(), REFRESHSOMEPART,
////        		parts);
////        work.start();
////        try
////        {
////            work.join();
////        }
////        catch (InterruptedException ex)
////        {
////            ex.printStackTrace();
////        }
//    }
    //CR4 End 20090616 zhangq
    
    /**
     * 在产品管理器上将修改的零部件刷新。
     * @param partVec Vector 被修改的零部件。
     */
    public void refreshPart(Vector partVec) {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- 刷新被修改的零部件：refreshPart(part)");

		if (null == partVec||partVec.size()==0) {
			return;
		}

		QMNode currentNode = null;
		QMNode refreshNode = null;
		QMPartIfc modifiedPart=(QMPartIfc)partVec.get(0);
		QMPartIfc workCopyIfc=(QMPartIfc)partVec.get(1);
		Explorable busobj = new PartItem(modifiedPart);
		((PartItem) busobj).setConfigSpecItem(getConfigSpecItem());
		QMNode[] matchingNodes = myExplorer.findNodes(busobj);
		for (int i = 0, j = matchingNodes.length; i < j; i++) {

			refreshNode = matchingNodes[i];
			currentNode = myExplorer.getSelectedNode();
			if ((null != currentNode) && (currentNode.equals(refreshNode))) {

				// 设置节点图标。
				myExplorer.refreshNode(refreshNode, busobj, true, true);
			} else {

				// 设置节点图标。
				myExplorer.refreshNode(refreshNode, busobj, false, true);
			}
			if (refreshNode.getLevel() > 1) {

				refreshCache(((QMNode) refreshNode.getParent()), busobj);
			}
		}

				QMPartIfc part = (QMPartIfc) workCopyIfc;
				if (!modifiedPart.getPartNumber().equals(part.getPartNumber()))
					return;
				Explorable busobj1 = new PartItem(part);
				((PartItem) busobj1).setConfigSpecItem(getConfigSpecItem());
				QMNode[] copymatchingNodes = myExplorer.findNodes(busobj1);
				for (int i = 0, j = copymatchingNodes.length; i < j; i++) {

					refreshNode = copymatchingNodes[i];
					currentNode = myExplorer.getSelectedNode();
					if ((null != currentNode)
							&& (currentNode.equals(refreshNode))) {

						// 设置节点图标。
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					} else {

						// 设置节点图标。
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					}
					if (refreshNode.getLevel() > 1) {

						refreshCache(((QMNode) refreshNode.getP()), busobj,
								busobj1);
					}
				}
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- 刷新被修改的零部件结束：refreshPart(part)------end()");
	}

    /**
     * 从管理器中删除零部件。
     * @param deletedPart QMPartIfc 被删除的零部件。
     */
    protected void deleteExplorerPart(QMPartIfc deletedPart)
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETEPART,
                deletedPart);
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
     * 显示错误信息框。
     * @param message String 显示的错误信息。
     * @param title String 信息框的标题。
     */
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(frame, message, title,
                JOptionPane.WARNING_MESSAGE);
        
    }

    /**
     * 处理“重新指定生命周期”命令。
     */
    public void processResetLifeCycleCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "processResetLifeCycleCommand()---begin");
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
            return;
        }
        LifeCycleStateDialog lifeCycleStateDialog = new LifeCycleStateDialog(
                frame, "", false);
        String str = part.getPartNumber() + "  " + part.getPartName();
        lifeCycleStateDialog.setName(str);
        lifeCycleStateDialog.setLifeCycleManaged(part);
        PartScreenParameter.centerWindow(lifeCycleStateDialog);
        str = null;
        obj = null;
        part = null;
    }

    /**
     * 处理“设置生命周期状态”命令。
     */
    public void processSetLifeCycleStateCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "processSetLifeCycleStateCommand()---begin");
        Object obj = getSelectedObject();
        PartDebug.trace(PartDebug.PART_CLIENT,
                "----------- selected object is :" + obj);
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
            return;
        }
        try
        {
            SetLifeCycleStateDialog dialog = new SetLifeCycleStateDialog(frame,
                    part);
            String identity = part.getPartNumber() + "  " + part.getPartName();
            dialog.setName(identity);
            dialog.show();
            identity = null;
        }
        catch (QMException e)
        {
            //modify by shf 2003/09/13
            String message = e.getClientMessage();
            showMessage(message, exceptionTitle);
            message = null;
            e.printStackTrace();
        }
        obj = null;
        part = null;
    }

    /**
     * 处理“查看生命周期历史记录”命令。
     */
    public void processShowLifeCycleHistoryCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "processShowLifeCycleHistoryCommand()---begin");
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
            return;
        }
        String partBsoID = part.getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", partBsoID);
        //2003/12/13
        hashmap.put("partName", part.getPartName());
        hashmap.put("partNumber", part.getPartNumber());
        hashmap.put("mpage", "shengminglishi");
        RichToThinUtil.toWebPage("Part_Look_LifeCycleHistory.screen", hashmap);
        partBsoID = null;
        hashmap = null;
        part = null;
        obj = null;
    }

    /**
     * 获取窗格。
     * @return QMProductManagerJFrame
     */
    public QMProductManagerJFrame getFrame()
    {
        return frame;
    }

    /**
     * 对选择件进行结构复制。
     */
    public void copy()
    {
    	//add whj 20070612
    	if(myTree.getSelected()!=null)
    	{
    		if(myTree.getSelected()== myExplorer.getRootNode())
    		{
    			JOptionPane.showMessageDialog(getFrame(), "根节点不能复制！", resourcebundle
                        .getString("warn"), JOptionPane.ERROR_MESSAGE);
    		}
    		else
    		{
        iscopy = true;
        //选择的对象。可能是左边树节点，也可能是右边列表中的对象。
        //CCBegin SS10
        Object[] selectObject = getSelectedObjects();
//      Object selectObject = getSelectedObject();
        //CCEnd SS10
        //零部件基本信息BsoID。
        String masterID = null;
        //CCBegin SS10
        cachePartMasterVec.clear();
        if(null != selectObject && selectObject.length!=0)
        {
        	for(int i=0;i<selectObject.length;i++)
        	{
//        		 if(selectObject instanceof QMPartIfc)
        			 if(selectObject[i] instanceof QMPartIfc)
        //CCEnd SS10
        	        {
        	            cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject[i]).getMaster();
        	            //CCBegin SS10
        	            cachePartMasterVec.add(cachePartMasterIfc);
        	            //CCEnd SS10
        	            masterID = cachePartMasterIfc.getBsoID();
        	        }
        			//CCBegin SS10
//         	        else if(selectObject instanceof QMPartMasterIfc)
     	        	else if(selectObject[i] instanceof QMPartMasterIfc)
        		    //CCEnd SS10
        	        {
        	            masterID = ((QMPartMasterIfc) selectObject[i]).getBsoID();
        	            cachePartMasterIfc = (QMPartMasterIfc) selectObject[i];
        	        }
        			   //如果不是零部件则直接获取左边树节点。
        		        if(masterID == null || masterID.equals(""))
        		        {
        		            cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) myTree.getSelected()
        		                    .getObj().getObject()).getMaster();
        		        }
        		        Explorable explorable = myTree.getSelected().getObj();
        		        if(myTree.getSelected().getP() != myExplorer.getRootNode())
        		        {
        		            if(explorable instanceof UsageMasterItem)
        		            {
        		                cachePartUsageLinkIfc = ((UsageMasterItem) explorable).getPartUsageLink();
        		            }
        		            if(explorable instanceof UsageItem)
        		            {
        		                cachePartUsageLinkIfc = ((UsageItem) explorable).getPartUsageLink();
        		            }
        		        }
        		        else
        		        {
        		            cachePartUsageLinkIfc = null;
        		        }
        		        //CCBegin SS10
        	   }
    	}	 
        		        //CCEnd SS10
        	}

        }//end
    	else
    	{
    		JOptionPane.showMessageDialog(getFrame(), "没有选择要复制的树节点！", resourcebundle
                    .getString("warn"), JOptionPane.ERROR_MESSAGE);
    	}
    }

    /**
     * 剪切零部件。
     */
    public void cut()
    {
        //选择的树节点存在父节点。
    	if(myTree.getSelected()!=null)
    	{
    		if(myTree.getSelected()== myExplorer.getRootNode())
    		{
    			JOptionPane.showMessageDialog(getFrame(), "根节点不能剪切！", resourcebundle
                        .getString("warn"), JOptionPane.ERROR_MESSAGE);
    		}
    		else

        if(myTree.getSelected().getP() != myExplorer.getRootNode())
        {
        	 QMPartInfo part=null;
        	Object obj=myTree.getSelected().getP().getObj()
            .getObject();
        	if(obj instanceof QMPartInfo)
        	{
        		part=(QMPartInfo)obj;
        	}
        	else
        		if((((QMNode)myTree.getSelected().getParent()).getP().getObj().getObject()) instanceof QMPartIfc)
    			{
    				part=(QMPartInfo)((QMNode)myTree.getSelected().getParent()).getP().getObj().getObject();
    			}
            if(helper.isAllowUpdate(part))
            {
                iscopy = true;
//              选择的对象。可能是左边树节点，也可能是右边列表中的对象。
                Object selectObject = getSelectedObject();
//              零部件基本信息BsoID。
                String masterID = null;
                //CCBegin SS10
	            cachePartMasterVec.clear();
	            //CCEnd SS10
                if(selectObject instanceof QMPartIfc)
                {
                    cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject)
                            .getMaster();
                    //CCBegin SS10
    	            cachePartMasterVec.add(cachePartMasterIfc);
    	            //CCEnd SS10
                    masterID = ((QMPartIfc) selectObject).getMasterBsoID();
                }
                else if(selectObject instanceof QMPartMasterIfc)
                {
                    cachePartMasterIfc = (QMPartMasterIfc) selectObject;
                    masterID = ((QMPartMasterIfc) selectObject).getBsoID();
                }
//              如果不是零部件则直接获取左边树节点。
                if(masterID == null || masterID.equals(""))
                {
                    cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) myTree.getSelected()
                            .getObj().getObject()).getMaster();
                }
                Explorable explorable = myTree.getSelected().getObj();
                if(explorable instanceof UsageMasterItem)
                {
                    cachePartUsageLinkIfc = ((UsageMasterItem) explorable)
                            .getPartUsageLink();
                }
                if(explorable instanceof UsageItem)
                {
                    cachePartUsageLinkIfc = ((UsageItem) explorable).getPartUsageLink();
                }
                //修改4 20080225 zhangq begin
                if(explorable instanceof PartItem)
                {
                    Explorable parentExplorable = myTree.getSelected().getP()
                            .getObj();
                    if(parentExplorable instanceof UsageMasterItem)
                    {
                        cachePartUsageLinkIfc = ((UsageMasterItem) parentExplorable)
                                .getPartUsageLink();
                    }
                    String parentBsoID="";
                    String childMasterBsoID="";
                    if(parentExplorable instanceof UsageItem)
                    {
                        parentBsoID = ((UsageItem) parentExplorable)
                                .getUsesPart().getPart().getBsoID();
                        childMasterBsoID = ((PartItem) explorable)
                                .getPart().getMasterBsoID();
                    }
                    if(parentExplorable instanceof PartItem)
                    {
                        parentBsoID = ((PartItem) parentExplorable)
                                .getPart().getBsoID();
                        childMasterBsoID = ((PartItem) explorable)
                                .getPart().getMasterBsoID();

                    }
                    if(parentExplorable instanceof UsageItem ||parentExplorable instanceof PartItem) {
                        Class[] paraClass = {String.class, String.class,
                                String.class, String.class};
                        //参数值的集合。
                        Object[] objs = {"PartUsageLink", parentBsoID,
                                "usedBy", childMasterBsoID};
                        try
                        {
                            Collection col = (Collection) IBAUtility
                                    .invokeServiceMethod("PersistService",
                                            "findLinkValueInfo", paraClass,
                                            objs);
                            Iterator iter = col.iterator();
                            if(iter.hasNext())
                            {
                                cachePartUsageLinkIfc = (PartUsageLinkIfc) iter
                                        .next();
                            }
                        }
                        catch (QMRemoteException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(getFrame(), e
                                    .getClientMessage(), resourcebundle
                                    .getString("warn"),
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                //修改4 20080225 zhangq end
                try
                {
                    PartHelper.deletePartUsageLink(cachePartUsageLinkIfc);
                }
                catch (QMRemoteException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getFrame(), e.getClientMessage(), resourcebundle
                            .getString("warn"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //刷新选择的节点的父节点，
                Explorable newObj = new PartItem(part);
                ((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
                //设置节点图标。
                ((PartItem) newObj).setIcon();
                myExplorer.refreshNode(myTree.getSelected().getP(), newObj);
            }
            else
            {
                JOptionPane.showMessageDialog(getFrame(), resourcebundle
                        .getString("parentnoupdate"), resourcebundle
                        .getString("warn"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
        //没有父节点，选择剪切，零部件从浏览器中清除
        else

        {
            cachePartUsageLinkIfc = null;
            JOptionPane.showMessageDialog(getFrame(), "一级节点不能剪切，可以选择复制操作！", resourcebundle
                    .getString("warn"), JOptionPane.WARNING_MESSAGE);
            cachePartMasterIfc=null;
//            Object selectObject = myTree.getSelected().getObj().getObject();
//
//
//            if(selectObject instanceof QMPartIfc)
//            {
//                cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject)
//                        .getMaster();
//
//            }
//            clear();
        }
    }
    	else
    	{
    		JOptionPane.showMessageDialog(getFrame(), "没有选择要剪切的树节点！", resourcebundle
                    .getString("warn"), JOptionPane.ERROR_MESSAGE);
    	}
    }


    /**
     * 当前零部件从父件中移除。
     * @throws QMException
     */
    public void moveClear() throws QMException
    {
        QMPartIfc parentPart = null;
        if(myTree.getSelected().getP() != myExplorer.getRootNode())
        {
            parentPart = (QMPartIfc) myTree.getSelected().getP().getObj()
                    .getObject();
            Explorable explorable = myTree.getSelected().getObj();
            PartUsageLinkIfc link = null;
            if(explorable instanceof UsageMasterItem)
            {
                link = ((UsageMasterItem) explorable).getPartUsageLink();
            }
            else if(explorable instanceof UsageItem)
            {
                link = ((UsageItem) explorable).getPartUsageLink();
            }
            if(link != null)
            {
                PartHelper.deletePartUsageLink(link);
            }
            Explorable newObj = new PartItem(parentPart);
            ((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
            //刷新选择的节点，
            //设置节点图标。
            ((PartItem) newObj).setIcon();
            myExplorer.refreshNode(myTree.getSelected().getP(), newObj);
        }
        else
        {
            JOptionPane.showMessageDialog(getFrame(), "顶级件不能移除", resourcebundle
                    .getString("warn"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 粘贴零部件分为复制粘贴、剪切粘贴和部分粘贴。
     */
    public void paste()
    {
        //CCBegin SS10
//        if(cachePartMasterIfc == null)
    	if(cachePartMasterVec == null)
    		//CCEnd SS10
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("notselectpart"), resourcebundle
                    .getString("warn"), JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        //CCBegin SS10
    	else
    	{
    		for(int i=0;i<cachePartMasterVec.size();i++)
    		{
    			cachePartMasterIfc=(QMPartMasterIfc) cachePartMasterVec.elementAt(i);
    	//CCEnd SS10
    			 //得到目标零部件
    	        //得到目标零部件
    	        Object obj=myTree.getSelected().getObj().getObject();
    	        if(obj instanceof QMPartMasterIfc)
    	        {
    	        	 JOptionPane.showMessageDialog(myExplorer.getManager(), "目标件"+((QMPartMasterInfo)obj).getPartNumber()+"不符合配置规范，不能建立结构！", "错误",
    	                     JOptionPane.INFORMATION_MESSAGE);
    	        	 return;
    	        }
    	        else
    	        	if(obj instanceof QMPartIfc)
    	        {
    	        QMPartIfc part = (QMPartInfo) obj;
    	        //目标件是否是可更新状态
    	        boolean isall = helper.isAllowUpdate(part);
    	        if(isall)
    	        {
    	        	//CCBegin SS9
    	            boolean issamepart = false;
    	          	  try {
    					issamepart = isSamePart(part, cachePartMasterIfc);
    					if(issamepart)
    	            	{
    	            		 JOptionPane.showMessageDialog(getFrame(), 
    	                             "零部件"+ part.getPartNumber()+"下，已存在"+cachePartMasterIfc.getPartNumber()+"零部件，不能重复复制！", resourcebundle.getString("warn"),
    	                             JOptionPane.INFORMATION_MESSAGE);
    	            		  return;
    	            	}
    				} catch (QMException e1) {
    					e1.printStackTrace();
    				}
    			    //CCEnd SS9
    	          boolean isparentpart = false;
    	          try {
    	            isparentpart = isParentPart(part, cachePartMasterIfc);
    	          }
    	          catch (QMException e) {
    	            e.printStackTrace();
    	            return;
    	          }
    	          if (isparentpart) {
    	            DisplayIdentity displayidentity = IdentityFactory
    	                .getDisplayIdentity(cachePartMasterIfc);
    	            //获得对象类型 + 标识
    	            String s = displayidentity.getLocalizedMessage(null);
    	            Object[] params = {
    	                s};
    	            String message = QMMessage.getLocalizedMessage(RESOURCE,
    	                "nestingwaringtext", params);
    	            String title = resourcebundle.getString("nestingwaringtitle");
    	            JOptionPane.showMessageDialog(getParentFrame(), message, title,
    	                                          JOptionPane.WARNING_MESSAGE);
    	            displayWaitIndicatorOff();
    	            return;
    	          }
    	          else {
    	            PartUsageLinkIfc link = new PartUsageLinkInfo();
    	            link.setRightBsoID(part.getBsoID());
    	            link.setLeftBsoID(cachePartMasterIfc.getBsoID());
    	            if (iscopy) {
    	              if (cachePartUsageLinkIfc != null) {
    	              	//修改4 20080225 zhnagq begin
    	                QMQuantity qmQuantity=new QMQuantity();
    	                qmQuantity.setDefaultUnit(cachePartUsageLinkIfc.getDefaultUnit());
    	                qmQuantity.setQuantity(cachePartUsageLinkIfc.getQuantity());
    	                link.setQMQuantity(qmQuantity);
    	                //修改4 20080225 zhangq end
    	                link.setQuantity(cachePartUsageLinkIfc.getQuantity());
    	                link.setDefaultUnit(cachePartUsageLinkIfc.getDefaultUnit());
    	              }
    	              else {
    	                link.setQuantity(1);
    	                link.setDefaultUnit(Unit.getUnitDefault());
    	              }
    	              try {
    	                PartHelper.savePartUsageLink(link);
    	              }
    	              catch (Exception exc) {
    	                exc.printStackTrace();
    	              }
    	            }
    	            else {
    	              link.setQuantity(bmovenum);
    	              if (cachePartUsageLinkIfc != null) {
    	                link.setDefaultUnit(cachePartUsageLinkIfc.getDefaultUnit());
    	              }
    	              else {
    	                link.setDefaultUnit(Unit.getUnitDefault());
    	              }
    	              try {
    	                PartHelper.savePartUsageLink(link);
    	              }
    	              catch (Exception exc) {
    	                exc.printStackTrace();
    	              }
    	              sourcePartMaster = null;
    	              cachePartUsageLinkIfc = null;
    	            }
    	            Explorable newObj = new PartItem(part);
    	            ( (PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
    	            //设置节点图标。
    	            ( (PartItem) newObj).setIcon();
    	            myExplorer.refreshNode(myTree.getSelected(), newObj, true, false);
    	          }
    	        }
    	        //目标零部件不是可更新状态。
    	        else
    	        {
    	            JOptionPane.showMessageDialog(getFrame(), resourcebundle
    	                    .getString("noupdate"), resourcebundle.getString("warn"),
    	                    JOptionPane.INFORMATION_MESSAGE);
    	        }
    	    }
    		}
    	      //CCBegin SS10
    	}
            //CCEnd SS10
    }

    /**
     * 部分移动方法，如果选择件父件处于可更新状态，进行部分移动，添加移动数量。
     */
    public void bMove()
    {
        if(myTree.getSelected().getP() == myExplorer.getRootNode())
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("notbmove"), resourcebundle.getString("warn"),
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        else
        {
            //          2007.05.31 whj change for capp zz
            //          QMPartIfc ppart = (QMPartIfc) myTree.getSelected().getP().getObj()
            //                  .getObject();
            QMPartIfc ppart = null;
            if(myTree.getSelected().getP().getObj().getObject() instanceof QMPartIfc)
                ppart = (QMPartIfc) myTree.getSelected().getP().getObj()
                        .getObject();
            else
             ppart =(QMPartIfc)  ((QMNode)myTree.getSelected().getParent()).getP().getObj().getObject();
            boolean isalw = helper.isAllowUpdate(ppart);
            try
            {
                if(isalw)
                {
                    iscopy = false;
                    Object select = myTree.getSelected().getObj().getObject();
                    if(select instanceof QMPartIfc)
                    {
                        cachePartMasterIfc=(QMPartMasterIfc)((QMPartIfc) select).getMaster();
                    }
                    else if(select instanceof QMPartMasterIfc)
                    {
                      cachePartMasterIfc=(QMPartMasterIfc) select;
                    }
                    QMPartIfc parentpart = (QMPartIfc) myTree.getSelected()
                            .getP().getObj().getObject();
                    QMNode node = myTree.getSelected();
                    Explorable explorable = node.getObj();
                    PartUsageLinkIfc link = null;
                    if(explorable instanceof UsageMasterItem)
                    {
                        link = ((UsageMasterItem) explorable)
                                .getPartUsageLink();
                    }
                    if(explorable instanceof UsageItem)
                    {
                        link = ((UsageItem) explorable).getPartUsageLink();
                    }
                    //修改5 20080306 zhangq begin:修改原因：检出的子件在执行部分移动操作时，
                    //找不到与父件之间的关联类值对象。
                    if(explorable instanceof PartItem)
                    {
                        Explorable parentExplorable = node.getP()
                                .getObj();
                        if(parentExplorable instanceof UsageMasterItem)
                        {
                            link = ((UsageMasterItem) parentExplorable)
                                    .getPartUsageLink();
                        }
                        String parentBsoID="";
                        String childMasterBsoID="";
                        if(parentExplorable instanceof UsageItem)
                        {
                            parentBsoID = ((UsageItem) parentExplorable)
                                    .getUsesPart().getPart().getBsoID();
                            childMasterBsoID = ((PartItem) explorable)
                                    .getPart().getMasterBsoID();
                        }
                        if(parentExplorable instanceof PartItem)
                        {
                            parentBsoID = ((PartItem) parentExplorable)
                                    .getPart().getBsoID();
                            childMasterBsoID = ((PartItem) explorable)
                                    .getPart().getMasterBsoID();

                        }
                        if(parentExplorable instanceof UsageItem ||parentExplorable instanceof PartItem) {
                            Class[] paraClass = {String.class, String.class,
                                    String.class, String.class};
                            //参数值的集合。
                            Object[] objs = {"PartUsageLink", parentBsoID,
                                    "usedBy", childMasterBsoID};
                            try
                            {
                                Collection col = (Collection) IBAUtility
                                        .invokeServiceMethod("PersistService",
                                                "findLinkValueInfo", paraClass,
                                                objs);
                                Iterator iter = col.iterator();
                                if(iter.hasNext())
                                {
                                    link = (PartUsageLinkIfc) iter
                                            .next();
                                }
                            }
                            catch (QMRemoteException e)
                            {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(getFrame(), e
                                        .getClientMessage(), resourcebundle
                                        .getString("warn"),
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    //修改5 20080306 zhangq end
                    bmovenum = link.getQuantity();
                    MoveNumberDialog movenumdia = new MoveNumberDialog(
                            getFrame(), "部分移动", true, link, bmovenum);
                    bmovenum = movenumdia.getNumber();
                    if(bmovenum == 0)
                    {
                        return;
                    }
                    cachePartUsageLinkIfc = link;
                    if(bmovenum == link.getQuantity())
                    {
                        PartHelper.deletePartUsageLink(link);
                    }
                    else
                    {
                        link.setQuantity(link.getQuantity() - bmovenum);
                        PartHelper.savePartUsageLink(link);
                    }
                    Explorable newObj = new PartItem(parentpart);
                    ((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
                    //设置节点图标。
                    ((PartItem) newObj).setIcon();
                    myExplorer.refreshNode(myTree.getSelected().getP(), newObj);
                }
                else
                {
                    String s = resourcebundle.getString("parentnoupdate");
                    JOptionPane
                            .showMessageDialog(getFrame(), s, resourcebundle
                                    .getString("warn"),
                                    JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (QMException qme)
            {
                qme.printStackTrace();
                showMessage(qme.getMessage(), exceptionTitle);
            }
        }
    }

    
    /**
     *浏览器左侧树上的鼠标监听类，主要实现右键的点击选择和鼠标的拖拽。
     */
    class TreeMouse implements MouseListener, MouseMotionListener
    {
        public void mouseClicked(MouseEvent e)
        {
//        	CCBegin SS6
        	if(e.getClickCount() == 2){
        		QMNode root = myExplorer.getSelectedNode();
        		if (root == null)
        		{
        			QMNode topRoot = myExplorer.getRootNode();
        			if (topRoot.getC() == null)
        			{
        				String information = QMMessage.getLocalizedMessage(RESOURCE,
        						"noPart", null);
        				return;
        			}
        			root = myExplorer.getTree().getRoot();
        		}
        		ArrayList allChildren = getAllDirectChildren(root);
        		ArrayList sortedChildren = (ArrayList) allChildren.clone();
        		sortedChildren = finalSort(sortedChildren);
        		for (int i = 0; i < allChildren.size(); i++)
        		{
        			myExplorer.removeNode((QMNode) allChildren.get(i));
        		}
        		for (int i = 0; i < sortedChildren.size(); i++)
        		{
        			myExplorer.addNode(root, (QMNode) sortedChildren.get(i));
        		}
        		myExplorer.getTree().expandNode(root);
        		myExplorer.refresh();
        	}
//        	CCEnd SS6
        }

        /**
         * 空方法，鼠标移动
         * @param mouseevent MouseEvent
         */
        public void mouseMoved(MouseEvent mouseevent)
        {
        }

        public void mousePressed(MouseEvent e)
        {

            if(e.isPopupTrigger())
            {
                TreePath treePath = myTree.getPathForLocation(e.getX(), e
                        .getY());
                myTree.setSelectionPath(treePath);
            }

            if(myTree.getSelected() != null
                    && myTree.getSelected() != myExplorer.getRootNode())
            {
                movenode = myTree.getSelected();
                Object obj = myTree.getSelected().getObj().getObject();
                if(obj instanceof QMPartIfc)
                {
                    sourcePartMaster = (QMPartMasterIfc) ((QMPartIfc) obj)
                            .getMaster();
                    capp=false;
                }
                else

                	if(obj instanceof QMPartMasterIfc)
                	{
                    sourcePartMaster = (QMPartMasterIfc) obj;
                	capp=false;
                }
                else
                {
                	capp=true;
                }
                if(myTree.getSelected().getP() != myExplorer.getRootNode())
                {
                    tempParentNode = myTree.getSelected().getP();
                    if((myTree.getSelected().getP()
                            .getObj().getObject()) instanceof QMPartIfc)
                    sourceParentPart = (QMPartIfc) myTree.getSelected().getP()
                            .getObj().getObject();
                }
                else
                {
                    sourceParentPart = null;
                }
            }
        }

        public void mouseDragged(MouseEvent e)
        {
        	if(fromcapp)
        		return;
            isdraged = true;
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }

        public void mouseReleased(MouseEvent e)
        {
        	if(fromcapp)
        		return;
            if(myTree.getSelected() == myExplorer.getRootNode())
            {
                return;
            }
            //不是拖拽。
            if(!isdraged)
            {
                displayWaitIndicatorOff();
                myExplorer.getList().deselectAll();
                if(e.getModifiers() == MouseEvent.META_MASK)
                {
                    TreePath treePath = myTree.getPathForLocation(e.getX(), e
                            .getY());
                    myTree.setSelectionPath(treePath);
                    if( myTree.getSelected() != null&&myTree.getSelected()!= myExplorer.getRootNode())
                    {
                    	partPop.initMenuItem();
                    partPop.show(myTree, e.getX(), e.getY());
                    }
                    else
                    	return;
                }
                oldnode = myTree.getSelected();
                if(oldnode == null)
                {
                    TreePath treePath0 = myTree.getPathForLocation(e.getX(), e
                            .getY());
                    myTree.setSelectionPath(treePath0);
                    oldnode = myTree.getSelected();
                }
                if(oldnode != null)
                {
                    Object select = oldnode.getObj().getObject();
                    if(select instanceof QMPartIfc)
                    {
                        oldpart = (QMPartIfc) select;
                    	capp=false;
                    }
                    else if(select instanceof QMPartMasterInfo)
                    {
                        oldpart = null;
                        oldpartmaster = (QMPartMasterInfo) select;
                    	capp=false;
                    }
                    else
                    {
                    	capp=true;
                    }
                }
            }
            //是拖拽。
            else
            	if(fromcapp)
            	{
            		return;

            	}
            	else
            {
                TreePath treePath1 = myTree.getPathForLocation(e.getX(), e
                        .getY());
                myTree.setSelectionPath(treePath1);
                //如果在左边树上选择了节点，则是往节点上拖拽，否则是往右边使用结构面板上拖拽。
                if(myTree.getSelected() != null)
                {
                    Object sel = myTree.getSelected().getObj().getObject();
                    if(sel instanceof QMPartIfc)
                    {
                        targetPart = (QMPartIfc) sel;
                    }
                    else if(sel instanceof QMPartMasterIfc)
                    {
                        targetPart = null;
                        JOptionPane.showMessageDialog(myExplorer.getManager(),
                                "拖拽目标件"
                                        + ((QMPartMasterInfo) sel)
                                                .getPartNumber()
                                        + "不符合配置规范，不能建立结构！", "错误",
                                JOptionPane.INFORMATION_MESSAGE);
                        displayWaitIndicatorOff();
                        isdraged = false;
                        return;
                    }
                    boolean sameparent = false;
                    if(sourceParentPart != null)
                    {
                        sameparent = targetPart.getPartNumber().equals(
                                sourceParentPart.getPartNumber());
                    }
                    if(sameparent)
                    {
                        displayWaitIndicatorOff();
                        JOptionPane.showMessageDialog(myExplorer.getManager(),
                                "拖拽目标件和原件的父件相同！", "警告",
                                JOptionPane.INFORMATION_MESSAGE);
                        isdraged = false;
                        return;
                    }
                    oldnode = myTree.getSelected();
                    Object select = oldnode.getObj().getObject();
                    if(select instanceof QMPartIfc)
                    {
                        oldpart = (QMPartIfc) select;
                    }
                    else if(select instanceof QMPartMasterInfo)
                    {
                        oldpart = null;
                        oldpartmaster = (QMPartMasterInfo) select;
                    }
                    if((!sourcePartMaster.getPartNumber().equals(
                            targetPart.getPartNumber()))
                            && (!sameparent))
                    {
                        try
                        {
                            processDrage(true);
                        }
                        catch (QMException ex)
                        {
                            displayWaitIndicatorOff();
                            JOptionPane.showMessageDialog(getParentFrame(), ex
                                    .getClientMessage(), exceptionTitle,
                                    JOptionPane.INFORMATION_MESSAGE);
                            ex.printStackTrace();
                            isdraged = false;
                            return;
                        }
                        myExplorer.getList().deselectAll();
                    }
                }
                //往右边使用结构面板上拖拽。
                else
                {
                    //如果上次选择的节点不为空并且是符合配置规范的零部件。正式开始执行拖拽操作。
                    if(oldnode != null && oldpart != null)
                    {
                        targetPart = oldpart;
                        if(getEnterList())
                        {
                            boolean sameparent = false;
                            if(sourceParentPart != null)
                            {
                                sameparent = targetPart.getPartNumber().equals(
                                        sourceParentPart.getPartNumber());
                            }
                            if(sameparent)
                            {
                                displayWaitIndicatorOff();
                                JOptionPane.showMessageDialog(myExplorer
                                        .getManager(), "拖拽目标件和原件的父件相同！", "警告",
                                        JOptionPane.INFORMATION_MESSAGE);
                                isdraged = false;
                                return;
                            }
                            if((!sourcePartMaster.getPartNumber().equals(
                                    targetPart.getPartNumber()))
                                    && (!sameparent))
                            {
                                try
                                {
                                    processDrage(false);
                                }
                                catch (QMException ex)
                                {
                                    displayWaitIndicatorOff();
                                    JOptionPane.showMessageDialog(
                                            getParentFrame(), ex
                                                    .getClientMessage(),
                                            exceptionTitle,
                                            JOptionPane.INFORMATION_MESSAGE);
                                    ex.printStackTrace();
                                    isdraged = false;
                                    return;
                                }
                                setEnterList(false);
                                myExplorer.getList().deselectAll();
                                myTree.setSelectedNode(oldnode);
                            }
                        }
                        myExplorer.getList().deselectAll();
                        myTree.setSelectedNode(oldnode);
                    }
                    else if(oldpart == null)
                    {
                        if(oldpartmaster != null)
                        {
                            displayWaitIndicatorOff();
                            JOptionPane.showMessageDialog(myExplorer
                                    .getManager(), "拖拽目标件"
                                    + oldpartmaster.getPartNumber()
                                    + "不符合配置规范，不能建立结构！", "错误",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        //change whj 20070613
//                        else
//                        {
//                            displayWaitIndicatorOff();
//                            JOptionPane.showMessageDialog(myExplorer
//                                    .getManager(), "拖拽目标件不符合配置规范，不能建立结构！",
//                                    "错误", JOptionPane.INFORMATION_MESSAGE);
//                        }
                    }
                }
                isdraged = false;
                displayWaitIndicatorOff();
            }
            displayWaitIndicatorOff();
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }
    }

    /**
     * 鼠标拖拽事件的处理。
     * @throws QMException
     */
    public void processDrage(boolean isLeft) throws QMException
    {
        if(targetPart == null)
            return;
        displayWaitIndicatorOn();
        //源零部件与目标零部件相同。
        if(sourcePartMaster != null
                && sourcePartMaster.getPartNumber().equals(
                        targetPart.getPartNumber()))
        {
            displayWaitIndicatorOff();
            return;
        }
        //源零部件的父件不可更新。
        if(sourceParentPart != null && !helper.isAllowUpdate(sourceParentPart))
        {
            displayWaitIndicatorOff();
            JOptionPane.showMessageDialog(this, "父件不是可更新状态，不允许拖拽！", "错误",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //目标零部件不可更新。
        if(!helper.isAllowUpdate(targetPart))
        {
            displayWaitIndicatorOff();
            JOptionPane.showMessageDialog(this, "目标件不是可更新状态，操作失败！", "错误",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //如果源零部件是目标零部件或目标零部件的父件，造成结构嵌套的，不可拖拽。
        if(isParentPart(targetPart, sourcePartMaster))
        {
            displayWaitIndicatorOff();
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    "nestingwaringtext", new Object[] {sourcePartMaster.getIdentity()});
            String title = resourcebundle.getString("nestingwaringtitle");
            JOptionPane.showMessageDialog(getParentFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Explorable explorable = movenode.getObj();
        PartUsageLinkIfc oldUsageLink = null;
        if(explorable instanceof UsageMasterItem)
        {
            oldUsageLink = ((UsageMasterItem) explorable).getPartUsageLink();
        }
        if(explorable instanceof UsageItem)
        {
            oldUsageLink = ((UsageItem) explorable).getPartUsageLink();
        }
        float quantity = 1;
        Unit unit = Unit.getUnitDefault();
        if(oldUsageLink != null)
        {
            quantity = oldUsageLink.getQuantity();
            unit = oldUsageLink.getDefaultUnit();
        }
        //处理完拖拽前的事件。
        //处理拖拽后事件。
        int i = -1;
        //如果是向左边拖拽，则弹出确认对话框。
        if(isLeft)
        {
            Object[] obj = new Object[3];
            obj[0] = sourcePartMaster.getPartNumber();
            if(sourceParentPart != null)
            {
                obj[1] = sourceParentPart.getPartNumber();
                obj[2] = targetPart.getPartNumber();
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "confrimdrag", obj);
                i = DialogFactory.showYesNoDialog(getParentFrame(), message,
                        resourcebundle.getString("dragdialogtitle"));
            }
            else
            {
                obj[1] = targetPart.getPartNumber();
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "onlyaddconfrimdrag", obj);
                i = DialogFactory.showYesNoDialog(getParentFrame(), message,
                        resourcebundle.getString("dragdialogtitle"));
            }
        }
        //如果确认对话框选择是或直接向右面拖拽。
        if(i == DialogFactory.YES_OPTION || !isLeft)
        {
            PartUsageLinkIfc newUsagelink = new PartUsageLinkInfo();
            newUsagelink.setRightBsoID(targetPart.getBsoID());
            newUsagelink.setLeftBsoID(sourcePartMaster.getBsoID());
            //修改4 20080225 zhnagq begin
            QMQuantity qmQuantity=new QMQuantity();
            qmQuantity.setDefaultUnit(unit);
            qmQuantity.setQuantity(quantity);
            newUsagelink.setQMQuantity(qmQuantity);
            //修改4 20080225 zhangq end
            newUsagelink.setQuantity(quantity);
            newUsagelink.setDefaultUnit(unit);
            if(oldUsageLink != null)
                PartHelper.deletePartUsageLink(oldUsageLink);
            PartHelper.savePartUsageLink(newUsagelink);
            if(sourceParentPart != null)
            {
                Explorable newExplorable = new PartItem(sourceParentPart);
                ((PartItem) newExplorable).setConfigSpecItem(getConfigSpecItem());
                //设置节点图标。
                ((PartItem) newExplorable).setIcon();
                //刷新源零部件的父件。
                myExplorer.refreshNode(tempParentNode, newExplorable);
            }
            Explorable newExplorable = new PartItem(targetPart);
            ((PartItem) newExplorable).setConfigSpecItem(getConfigSpecItem());
            //设置节点图标。
            ((PartItem) newExplorable).setIcon();
            //刷新选择的节点。
            myExplorer.refreshNode(oldnode, newExplorable, true, false);
            myTree.setSelectedNode(oldnode);
        }
        displayWaitIndicatorOff();
    }

    /**
     * 右键的更新。
     */
    public void updateSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..begin ....");
        try
        {
            BaseValueIfc selectObject = (BaseValueIfc) getSelectedObject();
            if(null == selectObject)
            {
                showMessage(noSelectObj, exceptionTitle);
                return;
            }
            else
            {
                if(((QMPartIfc) selectObject).getBsoName().equals("GenericPart"))
                {
                    showMessage(QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.CHOOSE_GENERIC_PART_WARNING, null),exceptionTitle);
                    return;
                }
                if(selectObject instanceof QMPartMasterIfc)
                {
                    showMessage(errorObject, exceptionTitle);
                    return;
                }
                if(selectObject instanceof WorkableIfc)
                {
                    //if 11:如果被其他用户检出并是零部件,提示"部件已经被他人检出!"。
                    if(CheckInOutTaskLogic
                            .isCheckedOutByOther((WorkableIfc) selectObject))
                    {
                        //if 1
                        if(selectObject instanceof QMPartIfc)
                        {
                            QMPartIfc part = (QMPartIfc) selectObject;
                            String username = "";
                            UserIfc qmprincipal = CheckInOutTaskLogic
                                    .getCheckedOutBy(part);
                            if(qmprincipal != null)
                            {
                                username = qmprincipal.getUsersDesc();
                            }
                            if(username.trim().length()==0)
                            {
                            	/*判断对象是否是工作副本。*/
                                boolean flag1 = WorkInProgressHelper
                                        .isWorkingCopy((WorkableIfc)part);
                                if(flag1)
                                {
                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                                }
                            }
                            Object[] objs = {username};
                            String message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                            objs);
                            showMessage(message, exceptionTitle);
                            message = null;
                            return;
                        }
                    } //end if 12:如果被当前用户检出。
                    else if(CheckInOutTaskLogic
                            .isCheckedOutByUser((WorkableIfc) selectObject))
                    {
                    	//修改1 begin，2007-10-25，by 穆勇鹏
                        //if 2:如果是被当前用户检出但不是工作副本,就直接返回
                        if(!CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) selectObject))
                        {
                            selectObject = CheckInOutTaskLogic //Begin:CR2
        										.getWorkingCopy((WorkableIfc) selectObject);
//        					return;//End:CR2
                        }//修改1 end，2007-10-25，by 穆勇鹏
                    }
                    //if 3:如果不允许修改,提示"对象 * 需要检出才能修改,现在要检出吗"。
                    else if(!helper.isUpdateAllowed((FolderedIfc) selectObject))
                    {
                        QMPartIfc part = (QMPartIfc) selectObject;
                        Object[] objs = {part.getIdentity()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.CONFIRM_TO_CHECKOUT, objs);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                QMProductManagerRB.CHECKOUT_TITLE, null);
                        //显示确认对话框并获得选择的返回值。
                        int i = JOptionPane.showConfirmDialog(frame, message,
                                title, JOptionPane.YES_NO_OPTION);
                        //if 4
                        if(JOptionPane.OK_OPTION == i)
                        {
                            //个人资料夹中的零部件不允许检出。
                            //提示"当前零部件在个人资料夹中，不能检出!"。
                            if(!CheckInOutTaskLogic
                                    .isInVault((WorkableIfc) selectObject))
                            {
                                String message1 = QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                                null);
                                showMessage(message1, exceptionTitle);
                                return;
                            }
                            selectObject = getCheckOutObject((WorkableIfc) selectObject);
                            if(null != selectObject)
                            {
                                if(!CheckInOutTaskLogic
                                        .isWorkingCopy((WorkableIfc) selectObject))
                                {
                                    selectObject = CheckInOutTaskLogic
                                            .getWorkingCopy((WorkableIfc) selectObject);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        } //end if 4
                        else
                        {
                            return;
                        }
                    } //end if 3
                }
                //加 if判断
                if(null != selectObject)//Begin:CR2
                {
                	if(selectObject instanceof QMPartIfc)
                	{
                      PartItem partItem = new PartItem(
                    		  					(QMPartIfc) selectObject);
                      partItem.setConfigSpecItem(getConfigSpecItem());
                      //当副本不再零部件树上时，将副本添加到零部件树上。
                      if(!this.isInTree(partItem))
                      {
                    	  myExplorer.addNode(myExplorer.getRootNode(), (Explorable)partItem);
                      }
                      this.setSelectNode(partItem);
                	}
                }//End:CR2
            }
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..end ....");
        displayWaitIndicatorOff();
    }
    
    //Begin:CR2
    /**
     * 取得对特定节点的操控权
     */
    protected void setSelectNode(PartItem part)
    {
        QMNode qmnode =myExplorer.findNode((Explorable) part);
        if (qmnode != null)
        {
            try
            {
            	myExplorer.setSelectedNode(qmnode);
            }
            catch (Throwable _ex)
            {}
        }
        else
        {
            return;
        }
    }//End:CR2

    /**
     * 设置是否是拖拽动作
     * @param isd boolean
     */
    public void setDrage(boolean isd)
    {
        isdraged = isd;
    }

    /**
     * 判断是否是拖拽动作
     * @return boolean
     */
    public boolean getDrage()
    {
        return isdraged;
    }

    private boolean isenterlist = false;

    public void setEnterList(boolean ent)
    {
        isenterlist = ent;
    }

    public boolean getEnterList()
    {
        return isenterlist;
    }

    /**
     * 判断零部件partMasterIfc2是否是零部件partIfc1的祖先部件或是partIfc1本身。
     * @param partIfc1 :QMPartIfc 指定的零部件的值对象。
     * @param partMasterIfc2 :QMPartMasterIfc 被检验的零部件的值对象。
     * flag==true:零部件partMasterIfc2是零部件partIfc1的祖先部件或是part1本身。
     * flag==false:零部件partMasterIfc2不是零部件partIfc1的祖先部件，也不是part1本身。
     * @return flag
     * @throws QMException
     */
    public boolean isParentPart(QMPartIfc partIfc1,
            QMPartMasterIfc partMasterIfc2) throws QMException
    {
        QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc) partIfc1.getMaster();
       // System.out.println("11");
        if(partMasterIfc1.getBsoID().equals(partMasterIfc2.getBsoID()))
        {
            return true;
        }
        Vector parentParts1 = PartServiceRequest.getAllParentParts(partIfc1);

        //如果partIfc1没有父亲节点，说明partMasterIfc2永远不可能是partIfc1的父亲节点，所以方法
        //永远返回false
        if(parentParts1 == null || parentParts1.size() == 0)
        {
            return false;
        }
        for (int i = 0; i < parentParts1.size(); i++)
        {
            String partMasterBsoID2 = partMasterIfc2.getBsoID();
            String parenPartMasterBsoID1 = ((QMPartMasterIfc) parentParts1.elementAt(i)).getBsoID();
            //如果partMasterIfc2的BsoID和partIfc1的某个父亲节点的BsoID相等，返回true;
            if(partMasterBsoID2.equals(parenPartMasterBsoID1))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置鼠标等待状态
     */
    public void displayWaitIndicatorOn()
    {
        setCursor(new Cursor(3));
    }

    /**
     * 设置鼠标的一般状态
     */
    public void displayWaitIndicatorOff()
    {
        setCursor(Cursor.getDefaultCursor());
    }
    //add whj for capp 2007.06.25
    public void setFromCapp(boolean capp)

    {
    	fromcapp=capp;

    }
    public boolean getFromCapp()
    {
    	if(capp)
    	{
    	 Object obj = myTree.getSelected().getObj().getObject();
         if(obj instanceof QMPartIfc)
         {
             sourcePartMaster = (QMPartMasterIfc) ((QMPartIfc) obj)
                     .getMaster();
             capp=false;
         }
         else

         	if(obj instanceof QMPartMasterIfc)
         	{
             sourcePartMaster = (QMPartMasterIfc) obj;
         	capp=false;
         }
    	}
    	return (fromcapp&&capp);
    }
    
   //CCBeginby leixiao 2008-07-30  
    
         /**
   * 处理"比较物料清单"命令——yanqi-20060915。
   */
  public void processCompareListCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), BUILD_COMPARE_BOM);
    work.start();
  }
    /**
   * 创建比较工程视图和制造视图下零件使用情况的物料清单－闫琦－20060915
   */
  protected void buildSelectedCompareBOM() {

    HashMap hashmap = new HashMap();
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //如果没有选择对象，显示信息“没有选择操作对象”
    if (obj == null) {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    //显示“选择的对象类型错误。”
    else {
      showMessage(errorObject, exceptionTitle);
    }
    String bsoID = part.getBsoID();
    hashmap.put("PartID", bsoID);

    //给廋客户端传递参数：零部件的输出属性（用于界面显示）
    hashmap.put("attributeName", attrName);

    //给廋客户端传递参数：零部件的输出属性（用于调用服务）
    hashmap.put("attributeName1", attrEnglishName);

    hashmap.put("source", "");
    hashmap.put("type", "");
    //传到“生成零部件统计表”页面
    RichToThinUtil.toWebPage("Part-Other-PartCompare-001.screen", hashmap);

    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

  }

    /**
   * 处理"一级子件列表"命令——yanqi-20061017。
   */
  public void processFirstLevelSonListCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), BUILD_FIRSTLEVELSON_BOM);
    work.start();
  }

    /**
     * 创建一级子件列表－闫琦－20061017
     */
    protected void buildSelectedFirstLevelSonBOM() {

      HashMap hashmap = new HashMap();
      QMPartIfc part = null;
      Object obj = getSelectedObject();
      //如果没有选择对象，显示信息“没有选择操作对象”
      if (obj == null) {
        showMessage(noSelectObj, exceptionTitle);
        return;
      }
      if (obj instanceof QMPartIfc) {
        part = (QMPartIfc) obj;
      }
      //显示“选择的对象类型错误。”
      else {
        showMessage(errorObject, exceptionTitle);
      }
      String bsoID = part.getBsoID();
      hashmap.put("PartID", bsoID);

      //给廋客户端传递参数：零部件的输出属性（用于界面显示）
      //hashmap.put("attributeName", attrName);

      //给廋客户端传递参数：零部件的输出属性（用于调用服务）
      //hashmap.put("attributeName1", attrEnglishName);

      //hashmap.put("source", "");
     // hashmap.put("type", "");
      //传到“生成零部件统计表”页面
      RichToThinUtil.toWebPage("Part-Other-PartFirstLevelSonList-001.screen", hashmap);

      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }

      /**
   * liuming 20070208 add
   * 处理"无合件装配表"命令。
   */
  public void processLogicBOMCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), LOGIC);
    work.start();
  }

  /**
   * liuming 20070208 add
   * 处理"无合件装配表"命令。
   */
  protected void bomLogic()
  {
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //如果没有选择对象，显示信息“没有选择操作对象”
    if (obj == null) {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    //显示“选择的对象类型错误。”
    else {
      showMessage(exceptionTitle, exceptionTitle);
    }
    //将hashmap和part传到“定制物料清单”页面
    boolean bool = false; //add by liun
    LogicBomFrame a = new LogicBomFrame(part, bool);
    a.setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
     //CCEndby leixiao 2008-07-30  
  
  
//CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
  /**
   * leix
   * 处理"逻辑总成数量报表"命令。
   */
  public void processNewLogicCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), NEWLOGIC);
    work.start();
  }
   

  /**
   * leix
   * 处理"逻辑总成数量报表"命令。
   */
  protected void bomNewLogic()
  {
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //如果没有选择对象，显示信息“没有选择操作对象”
    if (obj == null) {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    else {
      showMessage(exceptionTitle, exceptionTitle);
    }
    boolean bool = false; //add by liun
    NewLogicBomFrame a = new NewLogicBomFrame(part, bool);
    a.setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
  
  
  //CCBegin SS13
  /**
   * 处理"整车一级件清单"命令。
   */
  public void processFirstLevelCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), FIRSTLEVEL);
    work.start();
  }
   

  /**
   * 处理"整车一级件清单"命令。
   */
  protected void firstLevelList()
  {
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //如果没有选择对象，显示信息“没有选择操作对象”
    if (obj == null)
    {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    else
    {
      showMessage(exceptionTitle, exceptionTitle);
    }
    FirstLevelListFrame a = new FirstLevelListFrame(part);
    a.setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
  //CCEnd SS13
  
  //CCBegin SS4
  /**
   *   处理“分子公司物料清单”命令
   */
  public void processSubCompBom(){
	  WorkThread work = new WorkThread(getThreadGroup(),SUBCOMPBOM );
	  work.start();
  }
  /**
   *   处理“分子公司物料清单”命令
   */
  protected void subBom()
 {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"subBom()..begin ....");
		HashMap hashmap = new HashMap();
		QMPartIfc part = null;
		Object obj = getSelectedObject();
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		if (obj instanceof QMPartIfc) {
			part = (QMPartIfc) obj;
		} else {
			showMessage(errorObject, exceptionTitle);
		}
	    boolean bool = false;
	    NewSubCompBomJFrame a = new NewSubCompBomJFrame(part, bool);
		a.setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
 //CCEnd SS4 
  //CCBegin SS5
  
  public void processZCBom()
  {
	  WorkThread work = new WorkThread(getThreadGroup(),ZCBOM );
	  work.start();

  }
  protected void zcBom()
  {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"zcBom()..begin ....");
		HashMap hashmap = new HashMap();
		QMPartIfc part = null;
		Object obj = getSelectedObject();
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		if (obj instanceof QMPartIfc) {
			part = (QMPartIfc) obj;
		} else {
			showMessage(errorObject, exceptionTitle);
		}
		String bsoID = part.getBsoID();
		hashmap.put("PartID", bsoID);
		RichToThinUtil.toWebPage("Part-Other-WholePartStatistics-001.screen",
				hashmap);
		bsoID = null;
		hashmap = null;
		part = null;
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"buildSelectedGradeBOM()..end ....");
		displayWaitIndicatorOff();
	}
  public void processDLZCBom()
  {
	  WorkThread work = new WorkThread(getThreadGroup(),DLZCBOM );
	  work.start();

  }
  protected void dlzcBom()
  {
		PartDebug.trace(this, PartDebug.PART_CLIENT, "subBom()..begin ....");
		HashMap hashmap = new HashMap();
		QMPartIfc part = null;
		Object obj = getSelectedObject();
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		if (obj instanceof QMPartIfc) {
			part = (QMPartIfc) obj;
		} else {
			showMessage(errorObject, exceptionTitle);
		}
		boolean bool = false;
		SelectViewNameDialog a = new SelectViewNameDialog(frame,part);
		a.setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

  //CCEnd SS5
    //修改2：2007-11-07,add by 穆勇鹏  begin
    /**
     * 用于判断对象是否已经显示,并且是root的1级子节点
     * @param partItem
     * @return flag
     */
    public boolean isInTree(PartItem partItem)
    {
    	boolean flag=false;
    	Explorable explorable=(Explorable)partItem;
    	QMNode[] node=myTree.findNodes(explorable);
    	for(int i=0;i<node.length &&!flag;i++)
    	{
    		String rootText=myTree.getRoot().getLabel();
    		String parentNodeText=node[i].getP().getLabel();
    		if(rootText.equals(parentNodeText))
    			flag=true;
    	}
    	return flag;
    }//end
    //add whj 2008/08/08
    /**
     * 判断是否存在可粘贴部件
     * @return
     *@see boolean
     */
    public boolean isPaste()
    {
    	return cachePartMasterIfc!=null;
    }
    
    ////////////////////add by muyp begin///////////////////////
    /**
     * 处理“应用所有”命令
     */
    public void processAddAttrCommand()
    {
    	WorkThread work = new WorkThread(getThreadGroup(), APPLY_TO_ALL_PARTS);
        work.start();
    }
    
    /**
     * 应用所有
     *
     */
    public void addAttrToOthersParts()
    {
    	PartTaskJPanel task=myExplorer.getPartTaskJPanel();
    	int i = task.getSelectTabbed();
		int baseIndex=task.getBaseIndex();
		int ibaIndex=task.getIbaIndex();
    	//获得选择的对象
		Object selected_obj = this.getSelectedObject();
		//如果没有选择对象，显示信息“没有选择操作对象”。
		if(null == selected_obj)
		{
		    showMessage(noSelectObj, exceptionTitle);
		    return;
		}
		//如果是零部件主信息，显示信息“选择对象错误”
		if(selected_obj instanceof QMPartMasterIfc)
        {
            showMessage(errorObject, exceptionTitle);
            return;
        }
		if(selected_obj instanceof WorkableIfc)
		{
			BaseValueIfc baseIfc = (BaseValueIfc) selected_obj;
            Class[] class1 = {BaseValueIfc.class};
            Object[] param = {(BaseValueIfc) baseIfc};
            //调用持久化服务的方法。
            try {
				baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
				        "PersistService", "refreshInfo", class1, param);
				if(baseIfc instanceof QMPartIfc)
				{
					QMPartIfc part = (QMPartIfc)baseIfc;
					
                    Object[] objs = {part.getName()};
                    String title = QMMessage
                            .getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.APPLY_ATTRIBUTE_TO_OTHER,
                                    objs);
					if(i==baseIndex)
					{
						BaseAttrApplyAllJDialog dialog = new BaseAttrApplyAllJDialog(part,this.getParentFrame());					
						PartScreenParameter.centerWindow(dialog);
					}
					else if(i==ibaIndex)
					{
						ExtendAttrApplyAllJDialog dialog=new ExtendAttrApplyAllJDialog(this.getParentFrame(),title,
								task.getIBAAttributesJPanel());
						PartScreenParameter.centerWindow(dialog);

						//获得更新成功的零部件 //add by muyp 20081103 begin
						BaseValueIfc[] suc_obj = dialog.getApplySucObj();
						if(suc_obj!=null&&suc_obj.length>0)
						//刷新在节点树上的零部件
						refreshApplySucPart(suc_obj);//end
					}
					else
					{
						showMessage(selectObject, exceptionTitle);
					}
				}
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}

		}
		
    	
    }
    
//  add by muyp 081103 begin
	/**
	 * 应用所有之后，对于工作副本在树上的零部件进行刷新
	 * @param suc_obj BaseValueIfc[] 应用所有更新成功的零部件
	 * @see com.faw_qm.framework.service.BaseValueIfc
	 */
	private void refreshApplySucPart(BaseValueIfc[] suc_obj)
	{
		for(int i=0;i<suc_obj.length;i++)
		{
			if (suc_obj[i] instanceof WorkableIfc) {
				/* 判断对象是否是工作副本。 */
				boolean flag = WorkInProgressHelper
						.isWorkingCopy((WorkableIfc) suc_obj[i]);
				if(flag)
				{
					if (suc_obj[i] instanceof QMPartIfc) {
						QMPartIfc partIfc = (QMPartIfc) suc_obj[i];
						PartItem selected_obj1 = new PartItem(partIfc);
						// 如果副本在树上，就对副本进行刷新
						if(isInTree(selected_obj1))
						{
							// 如果对象为QMPartIfc。
							if (suc_obj[i] instanceof QMPartIfc) {
								BaseValueIfc baseIfc = (BaseValueIfc) suc_obj[i];
								Class[] class1 = { BaseValueIfc.class };
								Object[] param = { (BaseValueIfc) baseIfc };
								try {
									// 调用持久化服务的方法。
									baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
											"PersistService", "refreshInfo", class1, param);
								} catch (QMException e) {
									showMessage(e.getClientMessage(), exceptionTitle);
									this.clear();
									e.printStackTrace();
									return;
								}
								// 用part构造一个QMPartItem对象。
								QMPartIfc part = (QMPartIfc) baseIfc;
								// 转换成PartItem。
								Explorable newObj = new PartItem(part);
								((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
								// 刷新选择的节点，
								// 设置节点图标。
								((PartItem) newObj).setIcon();
								//获取更新成功零部件在树上的节点
						        Enumeration en = (((QMNode) (myTree.myModel().getRoot())).
				                          depthFirstEnumeration());
				                while (en.hasMoreElements())
				                {
				                    QMNode qmnode = (QMNode) en.nextElement();
				                    //如果此节点不是根节点
				                    if(!qmnode.isRoot())
				                    {
				                    	//如果节点零部件是当前的零部件，则进行刷新
					                    if(qmnode.getObj().getObjectNumber().equals(partIfc.getName()))
					                    {
					                    	Explorable explorable=qmnode.getObj();       //CR5
											myExplorer.refreshNode(qmnode, explorable, false, true);
											break;
					                    }
				                    }
				                }
							}
						
						}
					}
				}
			}
		}

	}//end
    //end
	
	 /**
     * 批量检入。可以对多个零部件进行检入。
     */
    public void checkinSelectedObjects() {
		PartDebug.trace(this, PartDebug.PART_CLIENT, "检入对象..开始 ....");
		Vector checkin = new Vector();
		Vector isInTreeVec = new Vector();
		try {
			// 获得选择的对象。
			Object[] selected_obj = getSelectedObjects();

			// 如果没有选择对象，显示信息“没有选择操作对象”。
			if (null == selected_obj) {
				showMessage(noSelectObj, exceptionTitle);
				return;
			}
			JFrame frame = getParentFrame();
			BatchCheckInTask checkin_task = new BatchCheckInTask(frame);
			WorkableIfc workingCopy=null;
			WorkableIfc originalCopy=null;
			try {
				for (int i = 0; i < selected_obj.length; i++) {
					if (selected_obj[i] instanceof QMPartMasterIfc) {
						isInTreeVec.add(new Boolean(false));
						checkin.add(selected_obj[i]);
						checkin.add(selected_obj[i]);
						checkin.add(selected_obj[i]);
					} else if (selected_obj[i] instanceof WorkableIfc) {
						WorkableIfc workableIfc = (WorkableIfc) selected_obj[i];
						boolean isChanged = myExplorer.isChange();
						boolean isSaved = false;
						if (isChanged) {
							isSaved = myExplorer.saveChange();
						}
						myExplorer.setTreeValueChanged(false);
						//CCBegin SS12
						//if (isSaved)
						//CCEnd SS12
						{
							BaseValueIfc baseIfc = (BaseValueIfc) workableIfc;
				            Class[] class1 = {BaseValueIfc.class};
				            Object[] param = {(BaseValueIfc) baseIfc};
				            workableIfc = (WorkableIfc) IBAUtility.invokeServiceMethod(
								        "PersistService", "refreshInfo", class1, param);
						} 
						if (workableIfc instanceof QMPartIfc) {
							//CCBegin SS3
							//针对每个part查找对应CAD文档，如果CAD状态是检出，给出提示，抛出异常终止进程
							QMPartIfc partIfcTemp = (QMPartIfc) workableIfc;
							BaseValueIfc baseIfc = (BaseValueIfc) partIfcTemp;
				            Class[] class1 = {BaseValueIfc.class};
				            Object[] param = {(BaseValueIfc) baseIfc};
				            Vector EPMTemp = (Vector) IBAUtility.invokeServiceMethod(
								        "StandardPartService", "getEMPDocument", class1, param);
				            if(EPMTemp != null && EPMTemp.size()>0)
				            {
				            	for(int ii=0;ii<EPMTemp.size();ii++)
				            	{
				            		EPMDocumentIfc epmIfcTemp = (EPMDocumentIfc) EPMTemp.get(ii);
				            		if(CheckInOutTaskLogic.isCheckedOut(epmIfcTemp))
				            		{
				            			throw new QMException("零部件"+partIfcTemp.getPartNumber()+
				            					"对应的EPM文档处于检出状态！请在CAD集成中将其检入后再对零部件进行更改！");
				            		}
				            	}
				            }
							//CCEnd SS3

							boolean isWorkingCopy=CheckInOutTaskLogic.isWorkingCopy(workableIfc);
							if(isWorkingCopy){
								workingCopy=workableIfc;
								originalCopy=CheckInOutTaskLogic.getOriginalCopy(workableIfc);
							}
							else{
								originalCopy=workableIfc;
								workingCopy=CheckInOutTaskLogic.getWorkingCopy(workableIfc);
							}
							PartItem selected_obj1 = new PartItem((QMPartIfc)originalCopy);
							isInTreeVec
									.add(new Boolean(isInTree(selected_obj1)));
						} else {
							isInTreeVec.add(new Boolean(false));
						}
						// 设置检入的对象。
						checkin.add(workableIfc);
						checkin.add(originalCopy);
						checkin.add(workingCopy);
//						System.out.println("workableIfc="+workableIfc);
//						System.out.println("originalCopy="+originalCopy);
//						System.out.println("workingCopy="+workingCopy);
//				          CCBegin SS8	
//						  CCBegin SS11	
//						  Class[] class1 = {QMPartIfc.class,QMPartIfc.class};
//				            Object[] param = {originalCopy,workingCopy};
				            Class[] class1 = {QMPartIfc.class};
				            Object[] param = {workingCopy};
//					          CCEnd SS11
						 IBAUtility.invokeServiceMethod(
							        "BomNoticeService", "processAdoptNoticeByPart", class1, param);
//				          CCEnd SS8
					}
				}
				checkin_task.setIsInTree(isInTreeVec);
				checkin_task.setCheckinItem(checkin);
				checkin_task.checkIn();
			}
			// 提示:"当前尚未将*检出。"。
			catch (QMException qmexception) {
				showMessage(qmexception.getClientMessage(), exceptionTitle);
				qmexception.printStackTrace();
			}

			selected_obj = null;
		} finally {
			displayWaitIndicatorOff();
		}
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"checkinSelectedObject()..结束 ....");
	}
    
    /**
     * 批量检出。可以对多个零部件进行检出。
     */
    public void checkoutSelectedObjects()
    {
    	//CR3 begin
		Object[] obj = getSelectedObjects();

		// 如果没有选择对象，显示信息“没有选择操作对象”。
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		// 如果需要检出的零部件的数量大于1，则循环操作。
		// modify by muyp 20080707
		Vector messageVec = new Vector();
		for (int i = 0; i < obj.length; i++) {
			try {
				if (obj[i] instanceof QMPartMasterIfc) {
					// showMessage(errorObject, exceptionTitle);
					messageVec.add(((QMPartMasterIfc) obj[i]).getIdentity()
							+ "检出失败：" + errorObject);

					continue;
				} 
				else {
					WorkableIfc workable = null;
					String objID = ((QMPartIfc) obj[i]).getBsoID();
					Class[] theClass = { String.class };
					Object[] theObjs = { objID };
					RequestHelper requestHelper = new RequestHelper();
					workable = (WorkableIfc) requestHelper.request(
							"PersistService", "refreshInfo", theClass, theObjs);
					WorkableIfc original = workable;
					boolean flag = VersionControlHelper
							.isLatestIteration(original); // 是否是最新版

					if (!flag) {
						messageVec.add(((QMPartIfc) obj[i]).getIdentity()
								+ "检出失败：非最小版本不能进行检出");
						continue;
					}

					// 如果选择的对象受资料夹管理
					if (obj[i] instanceof FolderedIfc) {
						FolderedIfc selected_object = (FolderedIfc) obj[i];
						// 如果selected_object对象不为null,将该对象检出。
						if (null != selected_object) {
							checkOutObject(selected_object, messageVec);
						}

					}
				}
			} catch (QMException e) {
				e.printStackTrace();
			}
		}

		obj = null;
		displayWaitIndicatorOff();
		MessageDialog mess = new MessageDialog(getParentFrame(), "检出信息", true,
				messageVec);
		mess.setVisible(true);
		//CR3 end
	}
    
    /**
	 * 撤消检出。可以对多个零部件进行操作。
	 */
    public void undoCheckoutSelectedObjects() {
		Vector messageVec = new Vector();
		Object[] selected_obj = getSelectedObjects();
		// 如果没有选择对象，显示信息“没有选择操作对象”。
		if (null == selected_obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		} else {
			for (int i = 0; i < selected_obj.length; i++) {
				undoCheckoutSelectedObject((BaseValueIfc) selected_obj[i],
						messageVec);
			}
		}
		selected_obj = null;
		displayWaitIndicatorOff();
		MessageDialog mess = new MessageDialog(getParentFrame(), "撤销检出信息",
				true, messageVec);
		mess.setVisible(true);
	}
    
    //liyz add 处理相关工艺浏览器命令
    
    /**
     * 处理“工艺浏览器”命令。
     */
    public void processsetTechnicsRegulationCommand()
    {
    	TechnicsRegulationsMainJFrame tecJFrame =new TechnicsRegulationsMainJFrame(true);
    	tecJFrame.setVisible(true);
    }
    
    /**
     * 处理“工艺路线表管理器”命令。
     */
    public void processsetTechnicsRouteCommand()
    {
    	CappRouteListManageJFrame routeJFrame = new CappRouteListManageJFrame(false);
    	routeJFrame.setVisible(true);    	
    }
    
    /**
     * 处理“工艺汇总浏览器”命令。
     */
    public void processsetTechnicsSummaryCommand()
    {
    	SummaryMainController summary = new SummaryMainController(false);
    	summary.getMainFrame().setVisible(true);
    }
    
	boolean ischeckin=true;
//    private WorkableIfc checkinSelectedObject()
//  {
//    	WorkableIfc work=null;
//      PartDebug.trace(this, PartDebug.PART_CLIENT, "检入对象..开始 ....");
//      
//          //获得选择的对象。
//          Object selected_obj = getSelectedObject();
//          //如果没有选择对象，显示信息“没有选择操作对象”。
//          if(null == selected_obj)
//          {
//              showMessage(noSelectObj, exceptionTitle);
//              ischeckin=false;
//          }
//          if(selected_obj instanceof QMPartMasterIfc)
//          {
//              showMessage(errorObject, exceptionTitle);
//              ischeckin=false;
//          }
//          if(selected_obj instanceof WorkableIfc)
//          {
//              try
//              {
//                  BaseValueIfc baseIfc = (BaseValueIfc) selected_obj;
//                  Class[] class1 = {BaseValueIfc.class};
//                  Object[] param = {(BaseValueIfc) baseIfc};
//                  //调用持久化服务的方法。
//                  baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
//                          "PersistService", "refreshInfo", class1, param);
//                  //如果该零件被其他人检出。
//                  if(CheckInOutTaskLogic
//                          .isCheckedOutByOther((WorkableIfc) baseIfc))
//                  {
//                      //if 1
//                      if(baseIfc instanceof QMPartIfc)
//                      {
//                          QMPartIfc part = (QMPartIfc) baseIfc;
//                          String username = "";
//                          UserIfc qmprincipal = CheckInOutTaskLogic
//                                  .getCheckedOutBy(part);
//                          if(qmprincipal != null)
//                          {
//                              username = qmprincipal.getUsersDesc();
//                          }
//                          else
//                          {
//                              username = part.getLocker();
//                          }
//                          if(username==null||username.trim().length()==0)
//                          {
//                          	/*判断对象是否是工作副本。*/
//                              boolean flag = WorkInProgressHelper
//                                      .isWorkingCopy((WorkableIfc) part);
//                              if(flag)
//                              {
//                              	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
//                              }
//                          }
//                          Object[] objs = {username};
//                          String message = QMMessage
//                                  .getLocalizedMessage(
//                                          RESOURCE,
//                                          QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN,
//                                          objs);
//                          showMessage(message, exceptionTitle);
//                          message = null;
//                          part = null;
//                          objs = null;
//                          ischeckin=false;
//                      }
//                  }
//                  else
//                  {
//                  	work=(WorkableIfc) baseIfc;
//                  }
//                  //变量复位。
//                  selected_obj = null;
//                  baseIfc = null;
//              }
//              //提示:"当前尚未将*检出。"。
//              catch (NotCheckedOutException notcheckedoutexception)
//              {
//                  showMessage(notcheckedoutexception.getClientMessage(),
//                          exceptionTitle);
//                  notcheckedoutexception.printStackTrace();
//                  ischeckin=false;
//              }
//              catch (QMException qmexception)
//              {
//                  showMessage(qmexception.getClientMessage(), exceptionTitle);
//                  qmexception.printStackTrace();
//                  ischeckin=false;
//              }
//          }
//  
//      PartDebug.trace(this, PartDebug.PART_CLIENT,
//              "checkinSelectedObject()..结束 ....");
//      return work;
//  }
	
//	CCBegin SS6
	/**
     * 给定一个节点，获得其所有直接孩子节点。
     * @param root 待查的节点。
     * @return ArrayList 该ArrayList中的元素为找到的所有节点。
     */
    public ArrayList getAllDirectChildren(QMNode root)
    {
        QMNode firstChild = root.getC();
        ArrayList allChildren = new ArrayList();
        for (QMNode node = firstChild; node != null; node = node.getS())
        {
            allChildren.add(node);
        }
        return allChildren;
    }
    
    public ArrayList finalSort(ArrayList list)
	{
		boolean isAscending = true;
		boolean isByNumber = true;
		boolean isByName = false;
		boolean isByCreateTime = false;
		boolean isByVersion = false;
		//用与二进制相似的表示方法说明排序规则
		//t代表true，f代表false
		boolean tttt = isByNumber && isByName && isByCreateTime && isByVersion;
		boolean ffff = !isByNumber && !isByName && !isByCreateTime &&
		!isByVersion;
		boolean tttf = isByNumber && isByName && isByCreateTime && !isByVersion;
		boolean ttft = isByNumber && isByName && !isByCreateTime && isByVersion;
		boolean tftt = isByNumber && !isByName && isByCreateTime && isByVersion;
		boolean fttt = !isByNumber && isByName && isByCreateTime && isByVersion;
		boolean tfff = isByNumber && !isByName && !isByCreateTime &&
		!isByVersion;
		boolean ftff = !isByNumber && isByName && !isByCreateTime &&
		!isByVersion;
		boolean fftf = !isByNumber && !isByName && isByCreateTime &&
		!isByVersion;
		boolean ffft = !isByNumber && !isByName && !isByCreateTime &&
		isByVersion;
		boolean ttff = isByNumber && isByName && !isByCreateTime &&
		!isByVersion;
		boolean fftt = !isByNumber && !isByName && isByCreateTime &&
		isByVersion;
		boolean tftf = isByNumber && !isByName && isByCreateTime &&
		!isByVersion;
		boolean ftft = !isByNumber && isByName && !isByCreateTime &&
		isByVersion;
		boolean fttf = !isByNumber && isByName && isByCreateTime &&
		!isByVersion;
		boolean tfft = isByNumber && !isByName && !isByCreateTime &&
		isByVersion;
		ArrayList result = new ArrayList();
		if (tfff || tfft || tftf || tftt || ttff || ttft || tttf || tttt) //先按编号排序，已经唯一
		{
			sortByNumber(list, isAscending);
			result = list;
		}
		return result;
	}
	public void sortByNumber(ArrayList allChildren, boolean isAscending)
	{
		if (allChildren == null)
		{
			return;
		}
		if (allChildren.size() < 2)
		{
			return;
		}
		QMPartComparator comp=new QMPartComparator(QMPartComparator.SORTBYNUMBER,isAscending);
		Collections.sort(allChildren,comp);
	}
	static class QMPartComparator implements java.util.Comparator {    //Begin CR1
		private static int SORTBYNUMBER= 1;
		private static int SORTBYNAME = 2;
		private static int SORTBYCREATIONDATE = 3;
		private static int SORTBYVERSION = 4;
		private int sortByKind = 1;
		private boolean isDesc = false;

		public QMPartComparator(int sortKind, boolean isDescFlag) {
			sortByKind = sortKind;
			isDesc = isDescFlag;
		}

		public int compare(Object obj1, Object obj2) {
			QMNode node1 = null;
			QMNode node2 = null;
			int number = 0;
			node1 = (QMNode) obj1;
			node2 = (QMNode) obj2;
			if (isDesc) {
				if (sortByKind == SORTBYNUMBER) {
					number = new String(node1.getObj().getObjectNumber())
					.compareTo(new String(node2.getObj()
							.getObjectNumber()));
				} else if (sortByKind == SORTBYNAME) {
					number = chineseCompareTo(node1.getObj().getObjectName(),node2.getObj().getObjectName());
				}
				else if (sortByKind == SORTBYCREATIONDATE) {
					number = node1.getObj().getObjectCreationDate()
							.compareTo(node2.getObj()
									.getObjectCreationDate());
				} else if(sortByKind == SORTBYVERSION) {
					number = new String(node1.getObj().getObjectVersion())
					.compareTo(new String(node2.getObj()
							.getObjectVersion()));
				}
				return number;
			}
			else {
				if (sortByKind == SORTBYNUMBER) {
					number = node1.getObj().getObjectNumber()
							.compareTo(node2.getObj()
									.getObjectNumber());
				} else if(sortByKind == SORTBYNAME) {
					number = chineseCompareTo(node1.getObj().getObjectName(),node2.getObj().getObjectName());
				}
				else if (sortByKind == SORTBYCREATIONDATE) {
					number = node1.getObj().getObjectCreationDate()
							.compareTo(node2.getObj()
									.getObjectCreationDate());
				} else if(sortByKind == SORTBYVERSION) {
					number = new String(node1.getObj().getObjectVersion())
					.compareTo(new String(node2.getObj()
							.getObjectVersion()));
				}
				return -number;
			}

		}

	}

	public static int chineseCompareTo(String s1, String s2)//CR!
	{
		int len1 = s1.length();
		int len2 = s2.length();
		int n = Math.min(len1, len2);
		for (int i = 0; i < n; i++)
		{
			int s1_code = getCharCode(s1.charAt(i) + "");
			int s2_code = getCharCode(s2.charAt(i) + "");
			if (s1_code != s2_code)
			{
				return s1_code - s2_code;
			}
		}
		return len1 - len2;
	}
	public static int getCharCode(String s)
	{
		if (s == null && s.equals(""))
		{
			return -1; //保护代码
		}
		byte[] b = s.getBytes();
		int value = 0;
		//保证取第一个字符（汉字或者英文）
		for (int i = 0; i < b.length && i <= 2; i++)
		{
			value = value * 100 + b[i];
		}
		return value;
	}
//	CCEnd SS6
	
	
	
    //CCBegin SS9
    /**
     * 判断零部件partMasterIfc2是否是零部件partIfc1的子件。
     * @param partIfc1 :QMPartIfc 指定的零部件的值对象。
     * @param partMasterIfc2 :QMPartMasterIfc 被检验的零部件的值对象。
     * flag==true:零部件partMasterIfc2是零部件partIfc1的子件。
     * flag==false:零部件partMasterIfc2不是零部件partIfc1的子件。
     * @return flag
     * @throws QMException
     */
    public boolean isSamePart(QMPartIfc partIfc1,
            QMPartMasterIfc partMasterIfc2) throws QMException
    {
        QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc) partIfc1.getMaster();
        Class[] paraClass = {QMPartIfc.class};
        //参数值的集合。
        Object[] objs = {partIfc1};
        Collection col = (Collection) IBAUtility
                    .invokeServiceMethod("StandardPartService",
                            "getSubParts", paraClass, objs);
            Vector parentParts1 = (Vector)col;
        if(parentParts1 == null || parentParts1.size() == 0)
        {
            return false;
        }
        for (int i = 0; i < parentParts1.size(); i++)
        {
            String partMasterBsoID2 = partMasterIfc2.getBsoID();
            String parenPartMasterBsoID1 = ((QMPartMasterIfc)((QMPartInfo) parentParts1.elementAt(i)).getMaster()).getBsoID();
            //如果partMasterIfc2的BsoID和partIfc1的某个子节点的BsoID相等，返回true;
            if(partMasterBsoID2.equals(parenPartMasterBsoID1))
            {
                return true;
            }
        }
        return false;
    }
    //CCEnd SS9
}
