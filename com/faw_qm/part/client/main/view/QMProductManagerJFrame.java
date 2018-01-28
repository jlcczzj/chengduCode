/** 程序QMProductManagerJFrame.java	1.0  2003/01/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1  2009/06/04    王亮  修改原因：详见缺陷TD=2247。
 * SS1 2013-1-21  产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能，导出的格式文件内容包括：零部件的零件号、零件名、父件数量、 
                  每车数量、计量单位、来源、制造路线、装配路线、（T单总、固定提前期合计、可变提前期），
                  其中后三项括弧中的内容需要从零部件关联的工艺卡中获取
 * SS2 2013-1-21 刘家坤 修改原因： 产品信息管理器中具有结构复制功能，可以将选中零部件结构完全复制，并按照结构粘贴到其他零部件中
 * SS3 添加“分子公司物料清单” liuyang 2013-12-18
 * SS4 添加"整车报毕统计"和“独立总成报毕统计 ”liuyang 2014-6-11
 * SS5 A004-2016-3286 整车一级件清单 liunan 2016-1-20
 */
package com.faw_qm.part.client.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMMenu;
import com.faw_qm.clients.beans.explorer.QMMenuItem;
import com.faw_qm.clients.util.ScreenParameter;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.HelpContext;
import com.faw_qm.help.HelpSystem;
import com.faw_qm.part.client.main.controller.PartRequestServer;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.util.QMCt;

/**
 * <p>Description:产品信息管理器主界面类。 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author 李玉芬
 * @version 1.0
 * 问题(1)20080226 张强修改 修改原因:当选中根节点时，可以进行部分移动操作。
 */
public class QMProductManagerJFrame extends JFrame
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;
    /**异常信息提示*/
    private final static Logger logger = Logger.getLogger(QMProductManagerJFrame.class);


    /**启明浏览器*/
    QMExplorer plorer=new QMExplorer(true);


    /**启明浏览器中的鼠标监听器*/
    QMExplorer.MouseListener1 mouseListener=plorer.new MouseListener1();


    /**零部件菜单的提示信息*/
    String partMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_MENU, null);


    /**创建零部件功能的提示信息*/
    String partCreate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_CREATE, null);


    /**另存为功能的提示信息*/
    String partSaveas = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_SAVEAS, null);


    /**更新零部件功能的提示信息*/
    String partUpdate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_UPDATE, null);


    /**重命名功能的提示信息*/
    String partRename = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_RENAME, null);


    /**删除零部件功能的提示信息*/
    String partDelete = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_DELETE, null);


    /**清除功能的提示信息*/
    String partClear = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_CLEAR, null);


    /**刷新功能的提示信息*/
    String partRefresh = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_REFRESH, null);


    /**退出功能的提示信息*/
    String partExit = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.PART_EXIT, null);


    /**版本菜单的提示信息*/
    String versionMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_MENU, null);


    /**检入功能的提示信息*/
    String versionCheckin = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_CHECKIN, null);


    /**检出功能的提示信息*/
    String versionCheckout = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_CHECKOUT, null);


    /**撤销检出功能的提示信息*/
    String versionUndoCheckout = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_UNDOCHECKOUT, null);


    /**更改资料夹功能的提示信息*/
    String versionChangeFile = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_CHANGEFILE, null);


    /**修订功能的提示信息*/
    String versionRevise = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_REVISE, null);


    /**查看版本历史功能的提示信息*/
    String versionVersions = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_VERSIONS, null);


    /**查看版序历史功能的提示信息*/
    String versionIterations = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.VERSION_ITERATIONS, null);


    /**管理菜单的提示信息*/
    String manageMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_MENU, null);


    /**基准线二级菜单的提示信息*/
    String manageBaseline = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE, null);


    /**创建基准线功能的提示信息*/
    String manageBaselineCreate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE_CREATE, null);


    /**更新基准线功能的提示信息*/
    String manageBaselineUpdate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE_UPDATE, null);


    /**查看基准线功能的提示信息*/
    String manageBaselineView = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE_VIEW, null);


    /**添加基准线功能的提示信息*/
    String manageBaselineAdd = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE_ADD, null);


    /**按结构添加基准线功能的提示信息*/
    String manageBaselinePopulate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE_POPULATE, null);


    /**移除基准线功能的提示信息*/
    String manageBaselineClear = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_BASELINE_CLEAR, null);


    /**有效性二级菜单的提示信息*/
    String manageEffectitvty = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY, null);


    /**创建有效性功能的提示信息*/
    String manageEffectitvtyCreate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY_CREATE, null);


    /**更新有效性功能的提示信息*/
    String manageEffectitvtyUpdate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY_UPDATE, null);


    /**查看有效性功能的提示信息*/
    String manageEffectitvtyView = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY_VIEW, null);


    /**添加有效性功能的提示信息*/
    String manageEffectitvtyAdd = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY_ADD, null);


    /**按结构添加有效性功能的提示信息*/
    String manageEffectitvtyPopulate = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY_POPULATE, null);


    /**修改有效性值功能的提示信息*/
    String manageEffectitvtyChangeValue = QMMessage.getLocalizedMessage(
            RESOURCE, QMProductManagerRB.MANAGE_EFFECTITVTY_CHANGEVALUE, null);


    /**移除有效性功能的提示信息*/
    String manageEffectitvtyClear = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_EFFECTITVTY_CLEAR, null);


    /**替换件功能的提示信息*/
    String manageAlternates = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_ALTERNATES, null);


    /**结构替换件功能的提示信息*/
    String manageSubstitutes = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_SUBSTITUTES, null);


    /**发布视图功能的提示信息*/
    String managePublishViewVersion = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_PUBLISHVIEWVERSION, null);
//CCBegin SS2
    //CCBegin 20090820 by chudaming
    String partAllCopy = QMMessage.getLocalizedMessage(RESOURCE,
    		QMProductManagerRB.PART_ALL_COPY, null);
    //CCEnd SS2
    /**发布特性功能的提示信息*/
    String managePublishIBA = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MANAGE_PUBLISHIBA, null);


    /**浏览菜单的提示信息*/
    String browseMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_MENU, null);


    /**设置配置规范功能的提示信息*/
    String browseConfigSpec = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_CONFIGSPEC, null);


    /**查看零部件属性功能的提示信息*/
    String browseView = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_VIEW, null);


    /**结构比较功能的提示信息*/
    String browseCompareStructure = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_COMPARESTRUCTURE, null);


    /**属性比较功能的提示信息*/
    String browseCompareIBA = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_COMPAREIBA, null);


    /**普通搜索功能的提示信息*/
    String browseGeneralSearch = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_GENERALSEARCH, null);


    /**按基本属性搜索功能的提示信息*/
    String browseBaseAttrSearch = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_BASEATTRSEARCH, null);


    /**按事物特性搜索功能的提示信息*/
    String browseIBASearch = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_IBASEARCH, null);


    /**物料清单二级菜单的提示信息*/
    String browseBom = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_BOM, null);


    /**分级功能的提示信息*/
    String browseBomMaterialList = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_BOM_MATERIALLIST, null);


    /**统计表功能的提示信息*/
    String browseBomBomlist = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_BOM_BOMLIST, null);


    /**定制功能的提示信息*/
    String browseBomTailorBom = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_BOM_TAILORBOM, null);


    /**广义部件物料清单功能的提示信息*/
    String browseBomGpartBom = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_BOM_GPARTBOM, null);


    /**排序功能的提示信息*/
    String browseSort = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.BROWSE_SORT, null);


    /**生命周期菜单的提示信息*/
    String lifecycleMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.LIFECYCLE_MENU, null);


    /**重新指定生命周期功能的提示信息*/
    String lifecycleReset = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.LIFECYCLE_RESET, null);


    /**设置生命周期状态功能的提示信息*/
    String lifecycleSetStatus = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.LIFECYCLE_SETSTATUS, null);


    /**显示生命周期历史功能的提示信息*/
    String lifecycleShowHistory = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.LIFECYCLE_SHOWHISTORY, null);
    
    //liyz add
    /**工艺菜单的提示信息*/
    String cappMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.CAPP_MENU, null);
    
    /**工艺浏览器的提示信息*/
    String technicsRegulation = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.TECHNICS_REGULATION, null);
    
    /**工艺路线表管理器的提示信息*/
    String technicsRoute = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.TECHNICS_ROUTE, null);
    
    /**工艺汇总管理器的提示信息*/
    String technicsSummary = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.TECHNICS_SUMMARY, null);


    /**帮助菜单的提示信息*/
    String helpMenu = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.HELP_MENU, null);


    /**产品管理功能的提示信息*/
    String helpProductManager = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.HELP_PRODUCTMANAGER, null);


    /**关于功能的提示信息*/
    String helpAbout = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.HELP_ABOUT, null);
    
    /**复制零部件功能的提示信息*/
    String partCopy = QMMessage.getLocalizedMessage(RESOURCE,
    		QMProductManagerRB.PART_COPY, null);

    /**剪切零部件功能的提示信息*/
    String partCut = QMMessage.getLocalizedMessage(RESOURCE,
    		QMProductManagerRB.PART_CUT, null);
 
    /**部分移动零部件功能的提示信息*/
    String partShareMove = QMMessage.getLocalizedMessage(RESOURCE,
    		QMProductManagerRB.PART_SHAREMOVE, null);
	//CCBegin SS2
	    private QMMenuItem copyAllPart = new QMMenuItem(partAllCopy, mouseListener);
	    String partAllPaste = QMMessage.getLocalizedMessage(RESOURCE,
	    		QMProductManagerRB.PART_All_PASTE, null);
	    private QMMenuItem pasteAllPart = new QMMenuItem(partAllPaste, mouseListener);
	//CCEnd SS2
//	  CCBegin SS1
	    /**erp能的提示信息*/
	    String browseBomErpList = QMMessage.getLocalizedMessage(RESOURCE,
	            QMProductManagerRB.BROWSE_BOM_ERPLIST, null);
//	  CCEnd SS1
    /**粘贴零部件功能的提示信息*/
    String partPaste = QMMessage.getLocalizedMessage(RESOURCE,
    		QMProductManagerRB.PART_PASTE, null);

    /**复制菜单项*/
    private QMMenuItem copyPart = new QMMenuItem(partCopy, mouseListener);

    /**剪切菜单项*/
    private QMMenuItem cutPart = new QMMenuItem(partCut, mouseListener);

    /**粘贴菜单项*/
    private QMMenuItem pastePart = new QMMenuItem(partPaste, mouseListener);

    /**部分移动菜单项*/
    private QMMenuItem shareMovePart = new QMMenuItem(partShareMove,
    		mouseListener);
    
    /**此处无调用*/
    String altPermission = null;


    /**此处无调用*/
    String subPermission = null;


    /**帮助上下文*/
    HelpContext helpContext = null;


    /**帮助系统*/
    HelpSystem helpSystem = null;


    /**客户端服务*/
    static String PART_CLIENT = "C";


    /**菜单栏*/
    protected JMenuBar mb = new JMenuBar();


    /**零部件菜单*/
    private QMMenu part = new QMMenu(partMenu, mouseListener);


    /**创建零部件菜单项*/
    private QMMenuItem createPart = new QMMenuItem(partCreate, mouseListener);


    /**版本菜单*/
    private QMMenu version = new QMMenu(versionMenu, mouseListener);


    /**检入菜单项*/
    private QMMenuItem checkIn = new QMMenuItem(versionCheckin, mouseListener);


    /**管理菜单*/
    private QMMenu manage = new QMMenu(manageMenu, mouseListener);


    /**另存为菜单项*/
    private QMMenuItem saveAsPart = new QMMenuItem(partSaveas, mouseListener);


    /**更新菜单项*/
    private QMMenuItem updatePart = new QMMenuItem(partUpdate, mouseListener);


    /**重命名菜单项*/
    private QMMenuItem renamePart = new QMMenuItem(partRename, mouseListener);


    /**删除菜单项*/
    private QMMenuItem deletePart = new QMMenuItem(partDelete, mouseListener);


    /**清除菜单项*/
    private QMMenuItem clear = new QMMenuItem(partClear, mouseListener);


    /**刷新菜单项*/
    private QMMenuItem refresh = new QMMenuItem(partRefresh, mouseListener);


    /**退出菜单项*/
    private QMMenuItem exit = new QMMenuItem(partExit, mouseListener);


    /**检出菜单项*/
    private QMMenuItem checkOut = new QMMenuItem(versionCheckout, mouseListener);


    /**撤销检出菜单项*/
    private QMMenuItem undoCheckOut = new QMMenuItem(versionUndoCheckout,
            mouseListener);


    /**更改资料夹菜单项*/
    private QMMenuItem move = new QMMenuItem(versionChangeFile, mouseListener);


    /**修订菜单项*/
    private QMMenuItem revise = new QMMenuItem(versionRevise, mouseListener);


    /**查看版本历史菜单项*/
    private QMMenuItem versionHistroy = new QMMenuItem(versionVersions,
            mouseListener);


    /**查看版序历史菜单项*/
    private QMMenuItem iterationHistroy = new QMMenuItem(versionIterations,
            mouseListener);


    /**基准线二级菜单*/
    private QMMenu baseline = new QMMenu(manageBaseline, mouseListener);


    /**创建基准线菜单项*/
    private QMMenuItem createBaseline = new QMMenuItem(manageBaselineCreate,
            mouseListener);


    /**维护基准线菜单项*/
    private QMMenuItem maintenanceBaseline = new QMMenuItem(
            manageBaselineUpdate, mouseListener);


    /**查看基准线菜单项*/
    private QMMenuItem viewBaseline = new QMMenuItem(manageBaselineView,
            mouseListener);


    /**添加基准线菜单项*/
    private QMMenuItem addBaseline = new QMMenuItem(manageBaselineAdd,
            mouseListener);


    /**按结构添加基准线菜单项*/
    private QMMenuItem populateBaseline = new QMMenuItem(manageBaselinePopulate,
            mouseListener);


    /**移除基准线菜单项*/
    private QMMenuItem removeBaseline = new QMMenuItem(manageBaselineClear,
            mouseListener);


    /**替换件菜单项*/
    private QMMenuItem defineAlternates = new QMMenuItem(manageAlternates,
            mouseListener);


    /**结构替换件菜单项*/
    private QMMenuItem defineSubstitutes = new QMMenuItem(manageSubstitutes,
            mouseListener);


    /**发布视图菜单项*/
    private QMMenuItem publishViewVersion = new QMMenuItem(
            managePublishViewVersion, mouseListener);


    /**发布特性菜单项*/
    private QMMenuItem publishIBA = new QMMenuItem(managePublishIBA,
            mouseListener);


    /**有效性二级菜单*/
    private QMMenu effectivity = new QMMenu(manageEffectitvty, mouseListener);


    /**创建有效性菜单项*/
    private QMMenuItem createConfigItem = new QMMenuItem(
            manageEffectitvtyCreate, mouseListener);


    /**维护有效性菜单项*/
    private QMMenuItem maintenanceConfigItem = new QMMenuItem(
            manageEffectitvtyUpdate, mouseListener);


    /**查看有效性菜单项*/
    private QMMenuItem viewConfigItem = new QMMenuItem(manageEffectitvtyView,
            mouseListener);


    /**添加有效性菜单项*/
    private QMMenuItem addEffectivity = new QMMenuItem(manageEffectitvtyAdd,
            mouseListener);


    /**按结构添加有效性菜单项*/
    private QMMenuItem populateEffectivity = new QMMenuItem(
            manageEffectitvtyPopulate, mouseListener);


    /**更新有效性菜单项*/
    private QMMenuItem updateEffValue = new QMMenuItem(
            manageEffectitvtyChangeValue, mouseListener);


    /**移除有效性菜单项*/
    private QMMenuItem removeEffectivity = new QMMenuItem(
            manageEffectitvtyClear, mouseListener);


    /**浏览菜单*/
    private QMMenu browse = new QMMenu(browseMenu, mouseListener);


    /**设置配置规范菜单项*/
    public QMMenuItem setConfigSpec = new QMMenuItem(browseConfigSpec,
            mouseListener);


    /**查看零部件菜单项*/
    private QMMenuItem viewPart = new QMMenuItem(browseView, mouseListener);


    /**结构比较菜单项*/
    private QMMenuItem compareStructure = new QMMenuItem(browseCompareStructure,
            mouseListener);


    /**查看另存为历史菜单项*/
    private QMMenuItem saveAsHistory = new QMMenuItem();

    /**查看注册历史菜单项*/
    private QMMenuItem catalogHistory = new QMMenuItem();

    /**普通搜索菜单项*/
    private QMMenuItem generalSearch = new QMMenuItem(browseGeneralSearch,
            mouseListener);


    /**按基本属性搜索菜单项*/
    private QMMenuItem baseAttrSearch = new QMMenuItem(browseBaseAttrSearch,
            mouseListener);

    /**按事物特性搜索菜单项*/
    private QMMenuItem ibaSearch = new QMMenuItem(browseIBASearch,
            mouseListener);


    /**排序菜单项*/
    private QMMenuItem sortMenuItem = new QMMenuItem(browseSort, mouseListener);


    /**属性比较菜单项*/
    private QMMenuItem ibaCompare = new QMMenuItem(browseCompareIBA,
            mouseListener);


    /**物料清单二级菜单*/
    private QMMenu BOM = new QMMenu(browseBom, mouseListener);


    /**分级菜单项*/
    private QMMenuItem materialList = new QMMenuItem(browseBomMaterialList,
            mouseListener);


    /**统计表菜单项*/
    private QMMenuItem bomList = new QMMenuItem(browseBomBomlist, mouseListener);

    //CCBegin by liunan 2008-07-30
    private QMMenuItem compareList = new QMMenuItem();
    private QMMenuItem firstLevelSonList = new QMMenuItem();
    private QMMenuItem logicMenuItem = new QMMenuItem();
    //CCEnd by liunan 2008-07-30
    //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
    private QMMenuItem newLogicMenuItem = new QMMenuItem();
    //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
    
    //CCBegin SS5
    private QMMenuItem firstLevelMenuItem = new QMMenuItem();
    //CCEnd SS5
    
    //CCBegin SS1
    /**分级菜单项*/
    private QMMenuItem erpList = new QMMenuItem(browseBomErpList,
            mouseListener);
    //CCend SS1
    
    /**定制菜单项*/
    private QMMenuItem tailorBOM = new QMMenuItem(browseBomTailorBom,
                                                  mouseListener);


    /**广义部件物料清单菜单项*/
    private QMMenuItem gpartBom = new QMMenuItem(browseBomGpartBom,
                                                 mouseListener);

    //CCBegin SS3
    private QMMenuItem subCompBom = new QMMenuItem();
    //CCEnd SS3
    //CCBeging SS4
    private QMMenuItem zcBom = new QMMenuItem();
    private QMMenuItem dlzcBom = new QMMenuItem();
    //CCEnd SS4
    /**生命周期菜单*/
    private QMMenu lifeCycle = new QMMenu(lifecycleMenu, mouseListener);
    
    //liyz add
    /**工艺菜单*/
    private QMMenu capp = new QMMenu(cappMenu,mouseListener);


    /**帮助菜单*/
    protected QMMenu help = new QMMenu(helpMenu, mouseListener);


    /**产品管理菜单项*/
    private QMMenuItem productManager = new QMMenuItem(helpProductManager,
            mouseListener);


    /**关于菜单项*/
    private QMMenuItem about = new QMMenuItem(helpAbout, mouseListener);
    
    
    /**界面的网格包布局管理*/
    GridLayout gridLayout1 = new GridLayout();


    /**界面的网格包布局管理*/
    BorderLayout borderLayout2 = new BorderLayout();


    /**启明浏览器*/
    private QMMenuItem showLifeCycleHistory = new QMMenuItem(
            lifecycleShowHistory, mouseListener);


    /**设置生命周期状态菜单项*/
    private QMMenuItem setLifeCycleState = new QMMenuItem(lifecycleReset,
            mouseListener);


    /**重新设置生命周期菜单项*/
    private QMMenuItem resetLifeCycle = new QMMenuItem(lifecycleSetStatus,
            mouseListener);
    
    //liyz add
    /**进入工艺浏览器菜单项*/
    private QMMenuItem setTechnicsRegulation = new QMMenuItem(technicsRegulation,
    		mouseListener);
    
    /**进入工艺路线表管理器菜单项*/
    private QMMenuItem setTechnicsRoute = new QMMenuItem(technicsRoute,
    		mouseListener);
    
    /**进入工艺汇总浏览器菜单项*/
    private QMMenuItem setTechnicsSummary = new QMMenuItem(technicsSummary,
    		mouseListener);
    


    /**当前浏览器*/
    protected QMProductManager myExplorer=new QMProductManager(plorer) ;


    /**资源信息路径*/
    private static String RESOURCE =
            "com.faw_qm.part.client.main.util.QMProductManagerRB";


    /**是否有父的标识*/
    private boolean hasParent = false;
    
    ////////////////////////////////begin////////////////////////////
    //add by muyongpeng 20080625
    /**应用所有功能的提示信息*/
    String browseApplyToAllParts = QMMessage.getLocalizedMessage(RESOURCE,
    		QMProductManagerRB.BROWSE_APPLYTOALLPARTS, null);
    ////////////////////////////////end//////////////////////////////
    
    ///////////////////////////begin/////////////////////////
    //add by muyongpeng 20080625
    private QMMenuItem applyMenuItem = new QMMenuItem(browseApplyToAllParts,
    		mouseListener);
    ///////////////////////////end///////////////////////////


    /**
     * 是否安装了产品配置模块
     */
    Boolean b = new Boolean(RemoteProperty.getProperty("com.faw_qm.hasPcfg" , "true"));
    boolean hasPcfg = b.booleanValue();
    /**
        * 构造函数。
        */
       public QMProductManagerJFrame()
       {
           //把QMProductManagerJFrame 设置到控制类 QMProductManager中。
           myExplorer.setFrame(this);
           try
           {
               jbInit();

               //菜单的初始化(可见性)。
               setNullMenu();
           }
           catch (Exception e)
           {
              // logger.error(e);
           }
       }


    /**
     * 构造函数。
     * @param qm QMProductManager 对应总界面的控制类。
     */
    //管春元 add for capp *******************************************
    public QMProductManagerJFrame(QMProductManager qm)
    {
        try
        {
            myExplorer = qm;
            myExplorer.setFrame(this);
            jbInit();
            setNullMenu();
        }
        catch (Exception e)
        {
          //  logger.error(e);
        }
    }


    /**
     * 初始化界面。
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        //设置各功能的快捷键。
        part.setMnemonic('P');
        createPart.setMnemonic('N');
        updatePart.setMnemonic('U');
        deletePart.setMnemonic('D');
        renamePart.setMnemonic('M');
        saveAsPart.setMnemonic('G');
        viewPart.setMnemonic('V');
        clear.setMnemonic('A');
        refresh.setMnemonic('E');
        exit.setMnemonic('X');

        version.setMnemonic('V');
        checkOut.setMnemonic('O');
        checkIn.setMnemonic('I');
        undoCheckOut.setMnemonic('U');
        revise.setMnemonic('R');
        move.setMnemonic('M');
        versionHistroy.setMnemonic('B');
        iterationHistroy.setMnemonic('X');

        browse.setMnemonic('B');
        ibaCompare.setMnemonic('B');
        saveAsHistory.setMnemonic('H');
        catalogHistory.setMnemonic('T');
        baseAttrSearch.setMnemonic('J');
        ibaSearch.setMnemonic('I');
        setConfigSpec.setMnemonic('P');
        compareStructure.setMnemonic('C');
        generalSearch.setMnemonic('S');

        baseline.setMnemonic('B');
        createBaseline.setMnemonic('C');
        maintenanceBaseline.setMnemonic('E');
        viewBaseline.setMnemonic('V');
        addBaseline.setMnemonic('A');
        populateBaseline.setMnemonic('P');
        removeBaseline.setMnemonic('M');

        effectivity.setMnemonic('E');
        createConfigItem.setMnemonic('C');
        maintenanceConfigItem.setMnemonic('E');
        viewConfigItem.setMnemonic('V');
        addEffectivity.setMnemonic('A');
        populateEffectivity.setMnemonic('P');
        updateEffValue.setMnemonic('U');
        removeEffectivity.setMnemonic('M');
        defineAlternates.setMnemonic('A');
        defineSubstitutes.setMnemonic('S');
        publishViewVersion.setMnemonic('P');
        publishIBA.setMnemonic('I');

        BOM.setMnemonic('M');
        materialList.setMnemonic('F');
        bomList.setMnemonic('T');
        tailorBOM.setMnemonic('D');
        gpartBom.setMnemonic('G');
        sortMenuItem.setMnemonic('O');
        //add by muyongpeng 20080625 begin
        applyMenuItem.setMnemonic('A');
        //end

        lifeCycle.setMnemonic('L');
        resetLifeCycle.setMnemonic('R');
        setLifeCycleState.setMnemonic('S');
        showLifeCycleHistory.setMnemonic('H');
        
        //liyz add工艺菜单项
        capp.setMnemonic('C');
        setTechnicsRegulation.setMnemonic('T');
        setTechnicsRoute.setMnemonic('R');
        setTechnicsSummary.setMnemonic('S');
        //end

        help.setMnemonic('H');
       //CCBegin SS2
        copyAllPart.setMnemonic('J');
        pasteAllPart.setMnemonic('K');
        erpList.setMnemonic('E');
        //CCEnd SS2
        about.setMnemonic('A');
        productManager.setMnemonic('P');
        copyPart.setMnemonic('C');
        cutPart.setMnemonic('X');
        pastePart.setMnemonic('P');
        shareMovePart.setMnemonic('S');
        copyPart.setText("copyPart");
        cutPart.setText("cutPart");
        		
        pastePart.setText("pastePart");
        //CCBegin SS2
        copyAllPart.setText("copyAllPart");
        pasteAllPart.setText("pasteAllPart");
         //CCEnd SS2
//      CCBegin SS1
        erpList.setText("materialList");
        //CCEnd SS1
        shareMovePart.setText("shareMovePart");

        String iconImage = QMMessage.getLocalizedMessage(RESOURCE,
                QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
        myExplorer.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent event)
            {
                PartDebug.trace(this, PartDebug.PART_CLIENT,
                                "SysItem.itemStateChanged().. begin ....");
                Object[] objs = myExplorer.getSelectedObjects();
                if ((null != objs) && (objs.length > 0))
                {
                    enableMenuItems(objs[0]);
                }
                //问题(1) 20080226 zhangq begin:当选中根节点时，使得相应的菜单项和按钮处于不可用状态。
                else {
                    setNullMenu();
                }
                //问题(1) 20080226 zhangq end
                PartDebug.trace(this, PartDebug.PART_CLIENT,
                                "SysItem.itemStateChanged()..end ....");
            }
        }
        );

        //匿名监听类实例结束。
        productManager.setActionCommand("productManager");
        this.setJMenuBar(mb);
        this.setSize(new Dimension(700, 500));
        this.getContentPane().setLayout(borderLayout2);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        myExplorer.setBackground(Color.white);
        myExplorer.setLayout(gridLayout1);
        part.setText("part");
        createPart.setText("createPart");
        version.setText("version");
        checkIn.setText("checkIn");
        manage.setActionCommand("manage");
        manage.setMnemonic('M');
        manage.setText("manage");
        updatePart.setText("updatePart");
        saveAsPart.setText("saveAsPart");
        renamePart.setText("renamePart");
        deletePart.setText("deletePart");
        clear.setText("clear");
        refresh.setText("refresh");
        exit.setText("exit");
        checkOut.setText("checkOut");
        undoCheckOut.setText("undoCheckOut");
        move.setText("move");
        revise.setText("revise");
        versionHistroy.setText("versionHistroy");
        iterationHistroy.setText("iterationHistroy");
        baseline.setText("baseline");
        createBaseline.setText("createBaseline");
        maintenanceBaseline.setText("maintenanceBaseline");
        viewBaseline.setText("viewBaseline");
        addBaseline.setText("addBaseline");
        populateBaseline.setText("populateBaseline");
        removeBaseline.setText("removeBaseline");
        defineAlternates.setText("defineAlternates");
        defineSubstitutes.setText("defineSubstitutes");
        publishViewVersion.setText("publishViewVersion");
        publishIBA.setText("publishIBA");
        effectivity.setText("effectivity");
        createConfigItem.setText("createConfigItem");
        maintenanceConfigItem.setText("maintenanceConfigItem");
        viewConfigItem.setText("viewConfigItem");
        addEffectivity.setText("addEffectivity");
        populateEffectivity.setText("populateEffectivity");
        updateEffValue.setText("updateEffValue");
        removeEffectivity.setText("removeEffectivity");
        browse.setText("browse");
        setConfigSpec.setText("setConfigSpec");
        viewPart.setText("viewPart");
        compareStructure.setActionCommand("compareStructure");
        compareStructure.setText("compareStructure");
        ibaCompare.setActionCommand("ibaCompare");
        ibaCompare.setText("ibaCompare");
        ////////////begin/////////////////
        //add by muyongpeng 20080625
        applyMenuItem.setText("applyToAllParts");
        ////////////end///////////////////

        saveAsHistory.setText("saveAsHistory");
        catalogHistory.setText("catalogHistory");
        generalSearch.setText("generalSearch");
        baseAttrSearch.setText("extendSearch");
        ibaSearch.setText("ibaSearch");
        BOM.setText("BOM");
        materialList.setText("materialList");
        bomList.setText("bomList");
        //CCBegin by liunan 2008-07-30
        compareList.setText("compareList");
        firstLevelSonList.setText("firstLevelSonList");
        logicMenuItem.setText("无合件装配表");
        //CCEnd by liunan 2008-07-30
        //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
        newLogicMenuItem.setText("逻辑总成数量报表");
        //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
        //CCBegin SS5
        firstLevelMenuItem.setText("整车一级件清单");
        //CCEnd SS5
        tailorBOM.setText("tailorBOM");
        gpartBom.setText("gpartBom"); //add by liun 2005.3.2
        lifeCycle.setText("lifeCycle");
        resetLifeCycle.setText("resetLifeCycle");
        setLifeCycleState.setText("setLifeCycleState");
        showLifeCycleHistory.setText("showLifeCycleHistory");
        help.setText("help");
        productManager.setText("productManager");
        about.setText("about");
        //CCBegin SS3
        subCompBom.setText("分子公司物料清单");
       //CCEnd SS3
        //CCBegin SS4
        zcBom.setText("整车报毕统计");
        dlzcBom.setText("独立总成报毕统计");
        //CCEnd SS4
        //liyz add capp MenuItem
        capp.setText("capp");
        setTechnicsRegulation.setText("setTechnicsRegulation");
        setTechnicsRoute.setText("setTechnicsRoute");
        setTechnicsSummary.setText("setTechnicsSummary");
        //end
        //设置各主菜单的显示名称。
        mb.setFont(new java.awt.Font("Dialog", 0, 12));
        mb.add(part);
        mb.add(version);
        mb.add(manage);
        mb.add(browse);
        mb.add(lifeCycle);
        //liyz add capp MenuItem可配置
//        //测试用，部署环境注释begin
//        java.util.Properties prop=new Properties();
//        FileInputStream fis = 
//             new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//        prop.load(fis);
//        String s=prop.getProperty( "manager.cappmenu");
//        //end
        String s = RemoteProperty.getProperty("manager.cappmenu");
        Boolean b=new Boolean(s);
        if( b.booleanValue())
        mb.add(capp);        
        //end
        mb.add(help);

        sortMenuItem.setActionCommand("sort");
        sortMenuItem.setText(QMMessage.getLocalizedMessage(RESOURCE, "sort", null));

        //设置各菜单下功能的显示名称。
        part.add(createPart);
        part.add(saveAsPart);
        //add for capp 王海军2007.06.29
        if(!myExplorer.fromcapp)
        {
        part.add(copyPart);
        part.add(cutPart);
        part.add(shareMovePart);
        part.add(pastePart);
        //CCBegin SS2
        part.add(copyAllPart);
        part.add(pasteAllPart);
         //CCEnd SS2
        }
        part.add(updatePart);
        part.addSeparator();
        part.add(renamePart);
        part.add(deletePart);
        part.addSeparator();
        part.add(clear);
        part.add(refresh);
        part.addSeparator();
        part.add(exit);

        version.add(checkIn);
        version.add(checkOut);
        version.add(undoCheckOut);
        version.add(move);
        version.addSeparator();
        version.add(revise);
        version.addSeparator();
        version.add(versionHistroy);
        version.add(iterationHistroy);
        if(hasPcfg)
            version.add(catalogHistory);

        this.getContentPane().add(myExplorer, BorderLayout.CENTER);

        manage.add(baseline);
        manage.add(effectivity);
        manage.addSeparator();
        manage.add(defineAlternates);
        manage.add(defineSubstitutes);
        manage.add(publishViewVersion);
        manage.addSeparator();
        manage.add(publishIBA);

        baseline.add(createBaseline);
        baseline.add(maintenanceBaseline);
        baseline.add(viewBaseline);
        baseline.addSeparator();
        baseline.add(addBaseline);
        baseline.add(populateBaseline);
        baseline.add(removeBaseline);
//根据要求对“有效性”菜单项重新设置。王亮  2009/06/09
//        effectivity.add(createConfigItem);
//        effectivity.add(maintenanceConfigItem);
//        effectivity.add(viewConfigItem);
//        effectivity.addSeparator();
        effectivity.add(addEffectivity);
//        effectivity.add(populateEffectivity);
        effectivity.add(updateEffValue);
        effectivity.add(removeEffectivity);

        browse.add(setConfigSpec);
        browse.addSeparator();
        browse.add(viewPart);
        browse.add(compareStructure);
        browse.add(ibaCompare);
        browse.addSeparator();
        browse.add(generalSearch);
        browse.add(baseAttrSearch);
        browse.add(ibaSearch);
        browse.addSeparator();
        browse.add(BOM);
        //liyz add 排序可配置        
//        String sort=prop.getProperty( "manager.compositor");
        String sort = RemoteProperty.getProperty("manager.compositor");
        Boolean bl=new Boolean(sort);
        if(bl.booleanValue())
        browse.add(sortMenuItem);
        //end
        //add by muyp 20080625 begin 
        browse.add(applyMenuItem);//“应用所有”
        //end

        BOM.add(materialList);
        BOM.add(bomList);
        //CCBegin by liunan 2008-07-30
        BOM.add(compareList);
        BOM.add(firstLevelSonList);
        BOM.add(logicMenuItem);
        //CCEnd by liunan 2008-07-30

        //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
        BOM.add(newLogicMenuItem);
        //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
        //CCBegin SS5
        BOM.add(firstLevelMenuItem);
        //CCEnd SS5
        BOM.add(tailorBOM);
        BOM.add(gpartBom); //add by liun 2005.3.2
        //CCBegin SS1
        BOM.add(erpList);
        //CCEnd SS1
        //CCBegin SS3
        BOM.add(subCompBom);
        //CCEnd SS3
        //CCBegin SS4
        BOM.add(zcBom);
        BOM.add(dlzcBom);
        //CCEnd SS4
        lifeCycle.add(resetLifeCycle);
        lifeCycle.add(setLifeCycleState);
        lifeCycle.add(showLifeCycleHistory);
        
        //liyz add capp MenuItem
        capp.add(setTechnicsRegulation);
        capp.add(setTechnicsRoute);
        capp.add(setTechnicsSummary);
        //end

        help.add(productManager);
        help.add(about);

        SymWindow aSymWindow = new SymWindow();
        this.addWindowListener(aSymWindow);
        SymAction lSymAction = new SymAction();

        class SymItem implements java.awt.event.ItemListener
        {
            public void itemStateChanged(java.awt.event.ItemEvent event)
            {
                PartDebug.trace(this, PartDebug.PART_CLIENT,
                                "SysItem.itemStateChanged().. begin ....");
                Object[] objs = myExplorer.getSelectedObjects();
                if ((null != objs) && (objs.length > 0))
                {
                    enableMenuItems(objs[0]);
                }
                PartDebug.trace(this, PartDebug.PART_CLIENT,
                                "SysItem.itemStateChanged()..end ....");
            }
        }


        createPart.addActionListener(lSymAction);
        updatePart.addActionListener(lSymAction);
        copyPart.addActionListener(lSymAction);
        cutPart.addActionListener(lSymAction);
        pastePart.addActionListener(lSymAction);
        //CCBegin SS2
        copyAllPart.addActionListener(lSymAction);
        pasteAllPart.addActionListener(lSymAction);
         //CCEnd SS2
//      CCBegin SS1
        erpList.addActionListener(lSymAction);
//      CCEnd SS1
        shareMovePart.addActionListener(lSymAction);
        saveAsPart.addActionListener(lSymAction);
        renamePart.addActionListener(lSymAction);
        deletePart.addActionListener(lSymAction);
        clear.addActionListener(lSymAction);
        refresh.addActionListener(lSymAction);
        exit.addActionListener(lSymAction);
        checkIn.addActionListener(lSymAction);
        checkOut.addActionListener(lSymAction);
        undoCheckOut.addActionListener(lSymAction);
        move.addActionListener(lSymAction);
        revise.addActionListener(lSymAction);
        versionHistroy.addActionListener(lSymAction);
        iterationHistroy.addActionListener(lSymAction);
        createBaseline.addActionListener(lSymAction);
        maintenanceBaseline.addActionListener(lSymAction);
        viewBaseline.addActionListener(lSymAction);
        addBaseline.addActionListener(lSymAction);
        populateBaseline.addActionListener(lSymAction);
        removeBaseline.addActionListener(lSymAction);
        createConfigItem.addActionListener(lSymAction);
        maintenanceConfigItem.addActionListener(lSymAction);
        viewConfigItem.addActionListener(lSymAction);
        addEffectivity.addActionListener(lSymAction);
        populateEffectivity.addActionListener(lSymAction);
        updateEffValue.addActionListener(lSymAction);
        removeEffectivity.addActionListener(lSymAction);
        defineAlternates.addActionListener(lSymAction);
        defineSubstitutes.addActionListener(lSymAction);
        publishViewVersion.addActionListener(lSymAction);
        publishIBA.addActionListener(lSymAction);
        setConfigSpec.addActionListener(lSymAction);
        viewPart.addActionListener(lSymAction);
        compareStructure.addActionListener(lSymAction);
        ibaCompare.addActionListener(lSymAction);
        saveAsHistory.addActionListener(lSymAction);
        catalogHistory.addActionListener(lSymAction);
        generalSearch.addActionListener(lSymAction);
        materialList.addActionListener(lSymAction);
        bomList.addActionListener(lSymAction);
        //CCBegin by liunan 2008-07-30
        compareList.addActionListener(lSymAction);
        firstLevelSonList.addActionListener(lSymAction);
        logicMenuItem.addActionListener(lSymAction);
        //CCEnd by liunan 2008-07-30
        //CCBegin SS3
        subCompBom.addActionListener(lSymAction); 
        //CCEnd SS3
        //CCBegin SS4
        zcBom.addActionListener(lSymAction); 
        dlzcBom.addActionListener(lSymAction); 
        //CCEnd SS4
        //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
        newLogicMenuItem.addActionListener(lSymAction);
        //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
        //CCBegin SS5
        firstLevelMenuItem.addActionListener(lSymAction);
        //CCEnd SS5
        tailorBOM.addActionListener(lSymAction);
        gpartBom.addActionListener(lSymAction);
        baseAttrSearch.addActionListener(lSymAction);
        ibaSearch.addActionListener(lSymAction);
        resetLifeCycle.addActionListener(lSymAction);
        setLifeCycleState.addActionListener(lSymAction);
        showLifeCycleHistory.addActionListener(lSymAction);
        productManager.addActionListener(lSymAction);
        about.addActionListener(lSymAction);
        sortMenuItem.addActionListener(lSymAction); //add by 熊代军 2003.10.21
        //////////////begin/////////////////
        applyMenuItem.addActionListener(lSymAction);//add by muyp 20080625
        ///////////////end//////////////////
        //liyz add capp MenuItem
        setTechnicsRegulation.addActionListener(lSymAction);
        setTechnicsRoute.addActionListener(lSymAction);
        setTechnicsSummary.addActionListener(lSymAction);
        //end
        localize();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                QMProductManagerRB.MAIN_PAGE_TITLE, null);
        setTitle(title);
        createPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('N',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke('Q',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        deletePart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('D',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        //CCBegin SS2
        copyAllPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('J',
                java.awt.event.KeyEvent.SHIFT_MASK, false));
        pasteAllPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('K',
                java.awt.event.KeyEvent.SHIFT_MASK, false));
         //CCEnd SS2
        checkIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke('R',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        checkOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke('T',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        revise.setAccelerator(javax.swing.KeyStroke.getKeyStroke('E',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        undoCheckOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke('Z',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        viewPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('W',
                java.awt.event.KeyEvent.CTRL_MASK, false));
//        compareStructure.setAccelerator(javax.swing.KeyStroke.getKeyStroke('C',//CR1
//                java.awt.event.KeyEvent.ALT_MASK, false));
        generalSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke('F',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        baseAttrSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke('S',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        ibaSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke('I',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        productManager.setAccelerator(javax.swing.KeyStroke.getKeyStroke(0x70,
                0, false));
        setConfigSpec.setAccelerator(javax.swing.KeyStroke.getKeyStroke('P',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        refresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(0x74, 0, false));
        updatePart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('U',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        copyPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('C',
                java.awt.event.KeyEvent.SHIFT_MASK, false));
        cutPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('X',
                java.awt.event.KeyEvent.SHIFT_MASK, false));
        pastePart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('V',
                java.awt.event.KeyEvent.SHIFT_MASK, false));
        shareMovePart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('M',
                java.awt.event.KeyEvent.SHIFT_MASK, false));
        renamePart.setAccelerator(javax.swing.KeyStroke.getKeyStroke('R',
                java.awt.event.KeyEvent.ALT_MASK, false));
        clear.setAccelerator(javax.swing.KeyStroke.getKeyStroke(0x73, 0, false));
        pack();
    }


    /**
     * <p>Title: 内部类。</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: 一汽启明</p>
     * @author 谢斌
     * @version 1.0
     */
    class SymWindow extends java.awt.event.WindowAdapter
    {
        public void windowClosing(java.awt.event.WindowEvent event)
        {
            Object object = event.getSource();
            if (QMProductManagerJFrame.this == object)
            {
                Frame1_WindowClosing(event);
            }
        }
    }


    /**
     * 关闭按钮动作。
     * @param event WindowEvent 窗口事件。
     */
    void Frame1_WindowClosing(java.awt.event.WindowEvent event)
    {
        if (hasParent)
        {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }


    /**
     * 判断是否有父窗格。
     */
    public void hasParent()
    {
        hasParent = true;
    }


    /*
     *本地化信息。
     */
    protected void localize()
    {
        ResourceBundle rb = ResourceBundle.getBundle(RESOURCE,
                QMCt.getContext().getLocale());
        part.setText(getMenuString(rb, "part"));
        createPart.setText(getMenuString(rb, "part.createPart"));
        updatePart.setText(getMenuString(rb, "part.updatePart"));
        copyPart.setText(getMenuString(rb, "part.copyPart"));
        cutPart.setText(getMenuString(rb, "part.cutPart"));
        //CCBegin SS2
        copyAllPart.setText(getMenuString(rb, "part.copyAllPart"));
        pasteAllPart.setText(getMenuString(rb, "part.pasteAllPart"));
        erpList.setText(getMenuString(rb, "browse.BOM.erpList"));
         //CCEnd SS2
        pastePart.setText(getMenuString(rb, "part.pastePart"));
        shareMovePart.setText(getMenuString(rb, "part.shareMovePart"));
        saveAsPart.setText(getMenuString(rb, "part.saveAsPart"));
        renamePart.setText(getMenuString(rb, "part.renamePart"));
        deletePart.setText(getMenuString(rb, "part.deletePart"));
        clear.setText(getMenuString(rb, "part.clear"));
        refresh.setText(getMenuString(rb, "part.refresh"));
        exit.setText(getMenuString(rb, "part.exit"));
        version.setText(getMenuString(rb, "version"));
        checkIn.setText(getMenuString(rb, "version.checkIn"));
        checkOut.setText(getMenuString(rb, "version.checkOut"));
        undoCheckOut.setText(getMenuString(rb, "version.undoCheckOut"));
        move.setText(getMenuString(rb, "version.move"));
        revise.setText(getMenuString(rb, "version.revise"));
        versionHistroy.setText(getMenuString(rb, "version.versionHistroy"));
        iterationHistroy.setText(getMenuString(rb, "version.iterationHistroy"));
        manage.setText(getMenuString(rb, "manage"));
        baseline.setText(getMenuString(rb, "manage.baseline"));
        createBaseline.setText(getMenuString(rb,
                                             "manage.baseline.createBaseline"));
        maintenanceBaseline.setText(getMenuString(rb,
                                                  "manage.baseline.maintenanceBaseline"));
        viewBaseline.setText(getMenuString(rb, "manage.baseline.viewBaseline"));
        addBaseline.setText(getMenuString(rb, "manage.baseline.addBaseline"));
        populateBaseline.setText(getMenuString(rb,
                                               "manage.baseline.populateBaseline"));
        removeBaseline.setText(getMenuString(rb,
                                             "manage.baseline.removeBaseline"));
        effectivity.setText(getMenuString(rb, "manage.effectivity"));
        createConfigItem.setText(getMenuString(rb,
                                               "manage.effectivity.createConfigItem"));
        maintenanceConfigItem.setText(getMenuString(rb,
                "manage.effectivity.maintenanceConfigItem"));
        viewConfigItem.setText(getMenuString(rb,
                                             "manage.effectivity.viewConfigItem"));
        addEffectivity.setText(getMenuString(rb,
                                             "manage.effectivity.addEffectivity"));
        populateEffectivity.setText(getMenuString(rb,
                                                  "manage.effectivity.populateEffectivity"));
        updateEffValue.setText(getMenuString(rb,
                                             "manage.effectivity.updateEffValue"));
        removeEffectivity.setText(getMenuString(rb,
                                                "manage.effectivity.removeEffectivity"));
        defineAlternates.setText(getMenuString(rb, "manage.defineAlternates"));
        defineSubstitutes.setText(getMenuString(rb, "manage.defineSubstitutes"));
        publishViewVersion.setText(getMenuString(rb,
                                                 "manage.publishViewVersion"));
        publishIBA.setText(getMenuString(rb, "manage.publishIBA"));
        browse.setText(getMenuString(rb, "browse"));
        setConfigSpec.setText(getMenuString(rb, "browse.setConfigSpec"));
        viewPart.setText(getMenuString(rb, "browse.viewPart"));
        compareStructure.setText(getMenuString(rb, "browse.compareStructure"));
        ibaCompare.setText(getMenuString(rb, "browse.ibaCompare"));
        saveAsHistory.setText(getMenuString(rb, "browse.saveAsHistory"));
        catalogHistory.setText(getMenuString(rb, "browse.catalogHistory"));
        generalSearch.setText(getMenuString(rb, "browse.generalSearch"));
        baseAttrSearch.setText(getMenuString(rb, "browse.baseAttrSearch"));
        ibaSearch.setText(getMenuString(rb, "browse.ibaSearch"));
        BOM.setText(getMenuString(rb, "browse.BOM"));
        materialList.setText(getMenuString(rb, "browse.BOM.materialList"));
        bomList.setText(getMenuString(rb, "browse.BOM.bomList"));
        //CCBegin by liunan 2008-07-30
        compareList.setText(getMenuString(rb, "browse.BOM.compareList"));
        firstLevelSonList.setText(getMenuString(rb, "browse.BOM.firstLevelSonList"));
        //CCEnd by liunan 2008-07-30
        tailorBOM.setText(getMenuString(rb, "browse.BOM.tailorBOM"));
        gpartBom.setText(getMenuString(rb, "browse.BOM.gPBomList")); //add by liun 2005.3.2
        applyMenuItem.setText(getMenuString(rb,"browse.applyToAllParts"));//muyp 080926
        lifeCycle.setText(getMenuString(rb, "lifeCycle"));
        resetLifeCycle.setText(getMenuString(rb, "lifeCycle.resetLifeCycle"));
        setLifeCycleState.setText(getMenuString(rb,
                                                "lifeCycle.setLifeCycleState"));
        showLifeCycleHistory.setText(getMenuString(rb,
                "lifeCycle.showLifeCycleHistory"));
        //liyz add capp MenuItem
        capp.setText(getMenuString(rb, "capp"));
        setTechnicsRegulation.setText(getMenuString(rb, "capp.setTechnicsRegulation"));
        setTechnicsRoute.setText(getMenuString(rb, "capp.setTechnicsRoute"));
        setTechnicsSummary.setText(getMenuString(rb, "capp.setTechnicsSummary"));
        //end
        help.setText(getMenuString(rb, "help"));
        productManager.setText(getMenuString(rb, "help.productManager"));
        about.setText(getMenuString(rb, "help.about"));
        PartDebug.trace(this, PartDebug.PART_CLIENT, "localize()..end.. ....");
    }


    /**
     * 从资源文件中获取菜单名称。
     * @param rb ResourceBundle 资源文件。
     * @param menuName String 键。
     * @return String 值。
     */
    protected String getMenuString(ResourceBundle rb, String menuName)
    {
        String itemLabel = menuName;
        try
        {
            itemLabel = rb.getString(menuName + ".label");
        }
        catch (MissingResourceException mre)
        {
          //  logger.error(mre);
        }
        return itemLabel;
    }


    /**
     * <p>Title: 内部类。</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: 一汽启明</p>
     * @author 谢斌
     * @version 1.0
     */
    class SymAction implements java.awt.event.ActionListener
    {
        public void actionPerformed(java.awt.event.ActionEvent event)
        {
            try
            {
                Object object = event.getSource();
                System.out.println("object="+object);
                if (sortMenuItem == object)
                {
                    myExplorer.processSort(); //add by 熊代军 2003.10.21
                }
                if (applyMenuItem == object)
                {
                    myExplorer.processAddAttrCommand();//add by muyongpeng 20080625
                }
                if (createPart == object)
                {
                    myExplorer.processNewCommand();
                }
                else if (saveAsPart == object)
                {
                    myExplorer.processSaveAsCommand();
                }
                else if (updatePart == object)
                {
                    myExplorer.processEditCommand();
                }
                else if(copyPart == object)
                {
                	myExplorer.copy();
                }
                else if(cutPart == object)
                {
                	myExplorer.cut();
                } //CCBegin SS2
                else if(copyAllPart == object)
                {
                	myExplorer.Allcopy();
                	
                } else if(pasteAllPart == object)
                {
                	System.out.println("000000000");
                	myExplorer.Allpaste();
                } //CCEnd SS2
                else if (erpList == object)
                {
                    myExplorer.processErpListCommand();
                }
                else if(shareMovePart == object)
                {
                	myExplorer.bMove();
                }
                else if(pastePart == object)
                {
                	myExplorer.paste();
                }
                else if (renamePart == object)
                {
                    myExplorer.processRenameCommand();
                }
                else if (deletePart == object)
                {
                    myExplorer.processDeletePartCommand();
                }
                else if (clear == object)
                {
                    myExplorer.processClearCommand();
                }
                else if (refresh == object)
                {
                    myExplorer.processRefreshCommand();
                }
                else if (exit == object)
                {
                    if (hasParent)
                    {
                        setVisible(false);
                    }
                    else
                    {
                        System.exit(0);
                    }
                }
                else if (checkIn == object)
                {
                    myExplorer.processCheckInCommand();
                }
                else if (checkOut == object)
                {
                    myExplorer.processCheckOutCommand();
                }
                else if (undoCheckOut == object)
                {
                    myExplorer.processUndoCheckOutCommand();
                }
                else if (move == object)
                {
                    myExplorer.processMoveCommand();
                }
                else if (revise == object)
                {
                    myExplorer.processReviseCommand();
                }
                else if (versionHistroy == object)
                {
                    myExplorer.processVersionHistroyCommand();
                }
                else if (iterationHistroy == object)
                {
                    myExplorer.processIterationHistroyCommand();
                }
                else if (createBaseline == object)
                {
                    myExplorer.processCreateBaselineCommand();
                }
                else if (maintenanceBaseline == object)
                {
                    myExplorer.processMaintenanceBaselineCommand();
                }
                else if (viewBaseline == object)
                {
                    myExplorer.processViewBaselineCommand();
                }
                else if (addBaseline == object)
                {
                    myExplorer.processAddBaselineCommand();
                }
                else if (populateBaseline == object)
                {
                    myExplorer.processPopulateBaselineCommand();
                }
                else if (removeBaseline == object)
                {
                    myExplorer.processRemoveBaselineCommand();
                }
                else if (createConfigItem == object)
                {
                    myExplorer.processCreateConfigItemCommand();
                }
                else if (maintenanceConfigItem == object)
                {
                    myExplorer.processMaintenanceConfigItemCommand();
                }
                else if (viewConfigItem == object)
                {
                    myExplorer.processViewConfigItemCommand();
                }
                else if (addEffectivity == object)
                {
                    myExplorer.processAddEffectivityCommand();
                }
                else if (populateEffectivity == object)
                {
                    myExplorer.processPopulateEffectivityCommand();
                }
                else if (updateEffValue == object)
                {
                    myExplorer.processUpdateEffValueCommand();
                }
                else if (removeEffectivity == object)
                {
                    myExplorer.processRemoveEffectivityCommand();
                }
                else if (defineAlternates == object)
                {
                    myExplorer.processDefineAlternatesCommand();
                }
                else if (defineSubstitutes == object)
                {
                    myExplorer.processDefineSubstitutesCommand();
                }
                else if (publishViewVersion == object)
                {
                    myExplorer.processPublishViewVersionCommand();
                }
                else if (publishIBA == object)
                {
                    myExplorer.processPublishIBACommand();
                }
                else if (setConfigSpec == object)
                {
                    myExplorer.processSetConfigSpecCommand();
                }
                else if (viewPart == object)
                {
                    myExplorer.processViewPartCommand();
                }
                else if (compareStructure == object)
                {
                    myExplorer.processCompareStructrueCommand();
                }
                else if (ibaCompare == object)
                {
                    myExplorer.processIbaCompareCommand();
                }
                else if (generalSearch == object)
                {
                    myExplorer.processGeneralSearchCommand();
                }
                else if (baseAttrSearch == object)
                {
                    myExplorer.processBaseAttrSearchCommand();
                }
                else if (ibaSearch == object)
                {
                    myExplorer.processIBASearchCommand();
                }
                else if (materialList == object)
                {
                    myExplorer.processMaterialListCommand();
                }
                //CCBegin by liunan 2008-07-30
                else if (object == compareList) {
                    myExplorer.processCompareListCommand();
                }
                else if (object == firstLevelSonList) {
                    myExplorer.processFirstLevelSonListCommand();
                }
                else if(object == logicMenuItem)
                {
                    myExplorer.processLogicBOMCommand();
                }
                //CCEnd by liunan 2008-07-30
              //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表
                else if(object == newLogicMenuItem)
                {
                    myExplorer.processNewLogicCommand();                    
                }
              //CCEnd by leix 2010-12-20
                //CCBegin SS5
                else if(object == firstLevelMenuItem)
                {
                    myExplorer.processFirstLevelCommand();                    
                }
                //CCEnd SS5
                //CCBegin SS3
                else if(object == subCompBom)
                {
                    myExplorer.processSubCompBom();                    
                }
                //CCEnd SS3
                //CCBegin SS4
                else if(object == zcBom)
                {
                    myExplorer.processZCBom();                    
                }
                else if(object == dlzcBom)
                {
                    myExplorer.processDLZCBom();                    
                }
                //CCEnd SS4
                
                else if (bomList == object)
                {
                    myExplorer.processBOMListCommand();
                }
                else if (tailorBOM == object)
                {
                    myExplorer.processTailorBOMCommand();
                }
                else if (gpartBom == object)
                {
                    myExplorer.processGPBomListCommand(); //add by liun 2005.3.2
                }
                else if (resetLifeCycle == object)
                {
                    myExplorer.processResetLifeCycleCommand();
                }
                else if (setLifeCycleState == object)
                {
                    myExplorer.processSetLifeCycleStateCommand();
                }
                else if (showLifeCycleHistory == object)
                {
                    myExplorer.processShowLifeCycleHistoryCommand();
                }
                else if (productManager == object)
                {
                    myExplorer.processProductManagerCommand();
                }
                else if (about == object)
                {
                    myExplorer.processAboutCommand();
                }
                else if (catalogHistory == object)
                {
                    myExplorer.viewCatalogHistory();
                }
                //liyz add 处理相关工艺浏览器命令
                else if(setTechnicsRegulation == object)
                {
                	myExplorer.processsetTechnicsRegulationCommand();
                }
                else if(setTechnicsRoute == object)
                {
                	myExplorer.processsetTechnicsRouteCommand();
                }
                else if(setTechnicsSummary == object)
                {
                	myExplorer.processsetTechnicsSummaryCommand();
                }
                //end
            }
            finally
            {
                QMProductManagerJFrame.this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }


    /**
     * 根据所取对象的类型确定菜单项的有效性。
     * @param obj Object 取得的对象。
     */
    protected void enableMenuItems(Object obj)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "enableMenuItems().. begin ....");
        if (null == obj)
        {
            setNullMenu();
        }
        else if (obj instanceof QMPartMasterIfc)
        {
            setPartMasterMenu();
        }
        else if (obj instanceof QMPartIfc)
        {
            setPartMenu(obj);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "enableMenuItems().. end ....");
    }


    /**
     * 设置生命周期菜单组的有效性。
     * @param flag boolean ＝true表示生命周期菜单组有效，否则表示无效。
     */
    protected void setLifeCycleMenu(boolean flag)
    {
        resetLifeCycle.setEnabled(flag);
        setLifeCycleState.setEnabled(flag);
        showLifeCycleHistory.setEnabled(flag);
    }
    

    /**
     * 未在产品信息浏览器上选择对象时所有菜单的有效性。
     */
    protected void setNullMenu()
    {
        boolean flag = false;
        createPart.setEnabled(true);
        updatePart.setEnabled(flag);
        copyPart.setEnabled(flag);
        cutPart.setEnabled(flag);
        //CCBegin SS2
        pasteAllPart.setEnabled(flag);
        copyAllPart.setEnabled(flag);
         //CCEnd SS2
        pastePart.setEnabled(flag);
        shareMovePart.setEnabled(flag);
        saveAsPart.setEnabled(flag);
        renamePart.setEnabled(flag);
        deletePart.setEnabled(flag);
        clear.setEnabled(true);
        refresh.setEnabled(true);
        exit.setEnabled(true);
        checkIn.setEnabled(flag);
        checkOut.setEnabled(flag);
        undoCheckOut.setEnabled(flag);
        move.setEnabled(flag);
        revise.setEnabled(flag);
        versionHistroy.setEnabled(flag);
        iterationHistroy.setEnabled(flag);
        /**
          标准板：以下功能无效：
          基准线、有效性、替换件和结构替换件、生命周期、定制物料清单、
          按扩展属性搜索、发布视图版本、配置规范、结构比较。
         */
        if (PART_CLIENT.equals("A"))
        {
            //基准线。
            createBaseline.setEnabled(flag);
            addBaseline.setEnabled(flag);
            populateBaseline.setEnabled(flag);
            viewBaseline.setEnabled(flag);
            removeBaseline.setEnabled(flag);

            //有效性。
            createConfigItem.setEnabled(flag);
            maintenanceConfigItem.setEnabled(flag);
            viewConfigItem.setEnabled(flag);
            addEffectivity.setEnabled(flag);
            populateEffectivity.setEnabled(flag);
            updateEffValue.setEnabled(flag);
            removeEffectivity.setEnabled(flag);

            //替换件和结构替换件。
            defineAlternates.setEnabled(flag);
            defineSubstitutes.setEnabled(flag);
            publishViewVersion.setEnabled(flag);

            //发布特性到基本记录。
            publishIBA.setEnabled(flag);

            //配置规范。
            setConfigSpecEnable();

            //查看。
            viewPart.setEnabled(flag);

            //结构比较。
            compareStructure.setEnabled(flag);
            ibaCompare.setEnabled(flag);
            saveAsHistory.setEnabled(flag);
            catalogHistory.setEnabled(flag);
            generalSearch.setEnabled(true);
            baseAttrSearch.setEnabled(true);
            ibaSearch.setEnabled(true);
            materialList.setEnabled(flag);
            bomList.setEnabled(flag);
            //CCBegin by liunan 2008-07-30
            compareList.setEnabled(flag);
            firstLevelSonList.setEnabled(flag);
            logicMenuItem.setEnabled(flag);
            //CCEnd by liunan 2008-07-30
            //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
            newLogicMenuItem.setEnabled(flag);
            //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
            //CCBegin SS5
            firstLevelMenuItem.setEnabled(flag);
            //CCEnd SS5
            //CCBegin SS3
            subCompBom.setEnabled(flag);
            //CCEnd SS3
            //CCBegin SS4
            zcBom.setEnabled(flag);
            dlzcBom.setEnabled(flag);
            //CCEnd SS4
            tailorBOM.setEnabled(flag);
            //CCBegin SS1
            erpList.setEnabled(flag);
//CCEnd SS1
            //add by liun 2005.3.2
            gpartBom.setEnabled(flag);
        }
        else if (PART_CLIENT.equals("B"))
        {
            createBaseline.setEnabled(true);
            addBaseline.setEnabled(flag);
            populateBaseline.setEnabled(flag);
            viewBaseline.setEnabled(true);
            removeBaseline.setEnabled(flag);
            createConfigItem.setEnabled(flag);
            maintenanceConfigItem.setEnabled(flag);
            viewConfigItem.setEnabled(flag);
            addEffectivity.setEnabled(flag);
            populateEffectivity.setEnabled(flag);
            updateEffValue.setEnabled(flag);
            removeEffectivity.setEnabled(flag);
            defineAlternates.setEnabled(flag);
            defineSubstitutes.setEnabled(flag);
            publishViewVersion.setEnabled(flag);
            publishIBA.setEnabled(flag);
            setConfigSpecEnable();
            viewPart.setEnabled(flag);
            compareStructure.setEnabled(flag);
            ibaCompare.setEnabled(flag);
            saveAsHistory.setEnabled(flag);
            catalogHistory.setEnabled(flag);
            generalSearch.setEnabled(true);
            baseAttrSearch.setEnabled(true);
            ibaSearch.setEnabled(true);
            materialList.setEnabled(flag);
            bomList.setEnabled(flag);
            //CCBegin by liunan 2008-07-30
            compareList.setEnabled(flag);
            firstLevelSonList.setEnabled(flag);
            logicMenuItem.setEnabled(flag);
            //CCEnd by liunan 2008-07-30
            //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
            newLogicMenuItem.setEnabled(flag);
            //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
            //CCBegin SS5
            firstLevelMenuItem.setEnabled(flag);
            //CCEnd SS5
            //CCBegin SS3
            subCompBom.setEnabled(flag);
            //CCEnd SS3
            //CCBegin SS4
            zcBom.setEnabled(flag);
            dlzcBom.setEnabled(flag);
            //CCEnd SS4
            tailorBOM.setEnabled(flag);
            //CCBegin SS1
            erpList.setEnabled(flag);
            //CCEnd SS1
            gpartBom.setEnabled(flag);
        }
        else
        {
            createBaseline.setEnabled(true);
            addBaseline.setEnabled(flag);
            populateBaseline.setEnabled(flag);
            viewBaseline.setEnabled(true);
            removeBaseline.setEnabled(flag);
            createConfigItem.setEnabled(true);
            maintenanceConfigItem.setEnabled(true);
            viewConfigItem.setEnabled(flag);
            addEffectivity.setEnabled(flag);
            populateEffectivity.setEnabled(true);
            updateEffValue.setEnabled(flag);
            removeEffectivity.setEnabled(flag);
            defineAlternates.setEnabled(flag);
            defineSubstitutes.setEnabled(flag);
            publishViewVersion.setEnabled(flag);
            publishIBA.setEnabled(flag);
            setConfigSpecEnable();
            viewPart.setEnabled(flag);
            compareStructure.setEnabled(flag);
            ibaCompare.setEnabled(flag);
            saveAsHistory.setEnabled(flag);
            catalogHistory.setEnabled(flag);
            generalSearch.setEnabled(true);
            baseAttrSearch.setEnabled(true);
            ibaSearch.setEnabled(true);
            materialList.setEnabled(flag);
            bomList.setEnabled(flag);
            //CCBegin by liunan 2008-07-30
            compareList.setEnabled(flag);
            firstLevelSonList.setEnabled(flag);
            logicMenuItem.setEnabled(flag);
            //CCEnd by liunan 2008-07-30
            //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
            newLogicMenuItem.setEnabled(flag);
            //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
            //CCBegin SS5
            firstLevelMenuItem.setEnabled(flag);
            //CCEnd SS5
            //CCBegin SS3
            subCompBom.setEnabled(flag);
            //CCEnd SS3
            //CCBegin SS4
            zcBom.setEnabled(flag);
            dlzcBom.setEnabled(flag);
            //CCEnd SS4
            tailorBOM.setEnabled(flag);
            //CCBegin SS1
            erpList.setEnabled(flag);
            //CCEnd SS1
            gpartBom.setEnabled(flag);
        }
        //问题(1) 20080226 zhangq begin:当选中根节点时，使得相应的菜单项和按钮处于不可用状态。
        //设置工具条上的按钮可用
        this.myExplorer.getExplorer().enableButton("part_update",false);
        this.myExplorer.getExplorer().enableButton("part_delete",false);
        this.myExplorer.getExplorer().enableButton("part_view",false);
        this.myExplorer.getExplorer().enableButton("part_checkIn",false);
        this.myExplorer.getExplorer().enableButton("part_checkOut",false);
        this.myExplorer.getExplorer().enableButton("part_repeal",false);
        this.myExplorer.getExplorer().enableButton("public_refresh",true);
        this.myExplorer.getExplorer().enableButton("public_clear",true);
        this.myExplorer.getExplorer().enableButton("public_copy",false);
        this.myExplorer.getExplorer().enableButton("public_cut",false);
        this.myExplorer.getExplorer().enableButton("public_paste",false);
        //问题(1) 20080226 zhangq end
        setLifeCycleMenu(false);
        productManager.setEnabled(true);
        about.setEnabled(true);
        PartDebug.trace(this, PartDebug.PART_CLIENT, "setNullMenu()..end....");
    }


    /**
     * 设置配置规范菜单的可操作性。
     */
    private void setConfigSpecEnable()
    {
        try
        {
            PartConfigSpecIfc partConfigSpecIfc = PartHelper.getConfigSpec();
            if (null == partConfigSpecIfc)
            {
                setConfigSpec.setEnabled(false);
            }
            else
            {
                setConfigSpec.setEnabled(true);
            }
        }
        catch (QMRemoteException ex)
        {
            setConfigSpec.setEnabled(false);
           // logger.error(ex);
        }
    }


    /**
     * gcy add for capp , dont delete it!!!!!!
     * 当选中节点是标签时，设置按钮的状态。
     */
    protected void setLabelMenu()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "setLabelMenu()..begin.. ....");
        boolean flag = true;
        createPart.setEnabled(flag);
        updatePart.setEnabled(!flag);
        copyPart.setEnabled(!flag);
        cutPart.setEnabled(!flag);
        pastePart.setEnabled(!flag);
        //CCBegin SS2
        copyAllPart.setEnabled(!flag);
        pasteAllPart.setEnabled(!flag);
         //CCEnd SS2
        shareMovePart.setEnabled(!flag);
        saveAsPart.setEnabled(!flag);
        renamePart.setEnabled(!flag);
        deletePart.setEnabled(!flag);
        clear.setEnabled(!flag);
        refresh.setEnabled(!flag);
        exit.setEnabled(flag);

        checkIn.setEnabled(!flag);
        checkOut.setEnabled(!flag);
        undoCheckOut.setEnabled(!flag);
        move.setEnabled(!flag);
        revise.setEnabled(!flag);
        versionHistroy.setEnabled(!flag);
        iterationHistroy.setEnabled(!flag);

        createBaseline.setEnabled(flag);
        maintenanceBaseline.setEnabled(flag);
        addBaseline.setEnabled(!flag);
        populateBaseline.setEnabled(!flag);
        viewBaseline.setEnabled(flag);
        removeBaseline.setEnabled(!flag);
        createConfigItem.setEnabled(flag);
        maintenanceConfigItem.setEnabled(flag);
        viewConfigItem.setEnabled(flag);
        addEffectivity.setEnabled(!flag);
        populateEffectivity.setEnabled(flag);
        updateEffValue.setEnabled(!flag);
        removeEffectivity.setEnabled(!flag);
        defineAlternates.setEnabled(!flag);
        defineSubstitutes.setEnabled(!flag);
        publishViewVersion.setEnabled(!flag);
        publishIBA.setEnabled(!flag);

        setConfigSpecEnable();
        viewPart.setEnabled(!flag);
        compareStructure.setEnabled(!flag);
        ibaCompare.setEnabled(!flag);
        saveAsHistory.setEnabled(!flag);
        catalogHistory.setEnabled(!flag);
        generalSearch.setEnabled(flag);
        baseAttrSearch.setEnabled(flag);
        ibaSearch.setEnabled(flag);
        BOM.setEnabled(!flag);
        materialList.setEnabled(!flag);
        bomList.setEnabled(!flag);
        //CCBegin by liunan 2008-07-30
        compareList.setEnabled(!flag);
        firstLevelSonList.setEnabled(!flag);
        logicMenuItem.setEnabled(!flag);
        //CCBegin SS1
        erpList.setEnabled(!flag);
        //CCEnd SS1
        //CCEnd by liunan 2008-07-30
        //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
        newLogicMenuItem.setEnabled(!flag);
        //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
        //CCBegin SS5
        firstLevelMenuItem.setEnabled(!flag);
        //CCEnd SS5
        //CCBegin SS3
        subCompBom.setEnabled(!flag);
        //CCEnd SS3
        //CCBegin SS4
        zcBom.setEnabled(!flag);
        dlzcBom.setEnabled(!flag);
        //CCEnd SS4
        tailorBOM.setEnabled(!flag);
        gpartBom.setEnabled(!flag);

        setLifeCycleMenu(false);
        productManager.setEnabled(true);
        about.setEnabled(true);

        myExplorer.getExplorer().enableButton("part_update",!flag);
        myExplorer.getExplorer().enableButton("part_delete",!flag);
        myExplorer.getExplorer().enableButton("part_view",!flag);
        myExplorer.getExplorer().enableButton("part_checkIn",!flag);
        myExplorer.getExplorer().enableButton("part_checkOut",!flag);
        myExplorer.getExplorer().enableButton("part_repeal",!flag);
        myExplorer.getExplorer().enableButton("public_refresh",!flag);
        myExplorer.getExplorer().enableButton("public_clear",!flag);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "setLabelMenu()..end.. ....");
    }
    
    /**
     * 使物料清单菜单项和工具栏上的按钮可用。
     */
    //zz 20070321 start  zz add for capp , don't delete it!!!!!!
    protected void enableButtons()
    {
        boolean flag = true;
        BOM.setEnabled(flag);//zz
        myExplorer.getExplorer().enableButton("part_update", flag);
        myExplorer.getExplorer().enableButton("part_delete", flag);
        myExplorer.getExplorer().enableButton("part_view", flag);
        myExplorer.getExplorer().enableButton("part_checkIn", flag);
        myExplorer.getExplorer().enableButton("part_checkOut", flag);
        myExplorer.getExplorer().enableButton("part_repeal", flag);
        myExplorer.getExplorer().enableButton("public_refresh", flag);
        myExplorer.getExplorer().enableButton("public_clear", flag);
    }
   //zz 20070321 end

    /**
     * 选择对象为零部件时菜单的有效性。
     *
     * @param obj Object
     */
    protected void setPartMenu(Object obj)
    {
        productManager.setEnabled(true);
        about.setEnabled(true);
        boolean flag = true;
        createPart.setEnabled(flag);
        updatePart.setEnabled(flag);
        copyPart.setEnabled(flag);
        cutPart.setEnabled(flag);
        pastePart.setEnabled(flag);
        //CCBegin SS2
        copyAllPart.setEnabled(flag);
        pasteAllPart.setEnabled(flag);
         //CCEnd SS2
        shareMovePart.setEnabled(flag);
        saveAsPart.setEnabled(flag);
        renamePart.setEnabled(flag);
        deletePart.setEnabled(flag);
        clear.setEnabled(flag);
        refresh.setEnabled(flag);
        exit.setEnabled(flag);
        checkIn.setEnabled(flag);
        checkOut.setEnabled(flag);
        undoCheckOut.setEnabled(flag);
        move.setEnabled(flag);
        revise.setEnabled(flag);
        versionHistroy.setEnabled(flag);
        iterationHistroy.setEnabled(flag);
        if (PART_CLIENT.equals("A"))
        {
            createBaseline.setEnabled(!flag);
            maintenanceBaseline.setEnabled(!flag);
            addBaseline.setEnabled(!flag);
            populateBaseline.setEnabled(!flag);
            viewBaseline.setEnabled(!flag);
            removeBaseline.setEnabled(!flag);
            createConfigItem.setEnabled(!flag);
            maintenanceConfigItem.setEnabled(!flag);
            viewConfigItem.setEnabled(!flag);
            addEffectivity.setEnabled(!flag);
            populateEffectivity.setEnabled(!flag);
            updateEffValue.setEnabled(!flag);
            removeEffectivity.setEnabled(!flag);
            defineAlternates.setEnabled(!flag);
            defineSubstitutes.setEnabled(!flag);
            publishViewVersion.setEnabled(!flag);
            publishIBA.setEnabled(!flag);
            setConfigSpecEnable();
            viewPart.setEnabled(flag);
            compareStructure.setEnabled(!flag);
            ibaCompare.setEnabled(!flag);
            saveAsHistory.setEnabled(!flag);
            catalogHistory.setEnabled(!flag);
            generalSearch.setEnabled(flag);
            baseAttrSearch.setEnabled(flag);
            ibaSearch.setEnabled(flag);
            //CCBegin by liunan 2008-07-30
           /* if (((QMPartIfc) obj).getBsoName().equals("GenericPart"))
            {
                materialList.setEnabled(!flag);
                bomList.setEnabled(!flag);
                tailorBOM.setEnabled(!flag);
                gpartBom.setEnabled(flag);
            }
            else
            {*/
            //CCEnd by liunan 2008-07-30
                materialList.setEnabled(flag);
                bomList.setEnabled(flag);
                //CCBegin by liunan 2008-07-30
                compareList.setEnabled(flag);
                firstLevelSonList.setEnabled(flag);
                logicMenuItem.setEnabled(!flag);
                //CCEnd by liunan 2008-07-30
                //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
                newLogicMenuItem.setEnabled(!flag);
                //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
                //CCBegin SS5
                firstLevelMenuItem.setEnabled(!flag);
                //CCEnd SS5
                //CCBegin SS3
                subCompBom.setEnabled(!flag);
                //CCEnd SS3
                //CCBegin SS4
                zcBom.setEnabled(!flag);
                dlzcBom.setEnabled(!flag);
                //CCEnd SS4
                //CCBegin SS1
                erpList.setEnabled(flag);
                //CCEnd SS1
                tailorBOM.setEnabled(!flag);
                gpartBom.setEnabled(!flag);
          //  }
            setLifeCycleMenu(false);
        }
        else if (PART_CLIENT.equals("B"))
        {
            createBaseline.setEnabled(flag);
            maintenanceBaseline.setEnabled(flag);
            addBaseline.setEnabled(flag);
            populateBaseline.setEnabled(flag);
            viewBaseline.setEnabled(flag);
            removeBaseline.setEnabled(flag);
            createConfigItem.setEnabled(!flag);
            maintenanceConfigItem.setEnabled(!flag);
            viewConfigItem.setEnabled(!flag);
            addEffectivity.setEnabled(!flag);
            populateEffectivity.setEnabled(!flag);
            updateEffValue.setEnabled(!flag);
            removeEffectivity.setEnabled(!flag);
            defineAlternates.setEnabled(flag);
            defineSubstitutes.setEnabled(flag);
            publishViewVersion.setEnabled(flag);
            publishIBA.setEnabled(flag);
            setConfigSpecEnable();
            viewPart.setEnabled(flag);
            compareStructure.setEnabled(!flag);
            ibaCompare.setEnabled(!flag);
            saveAsHistory.setEnabled(!flag);
            catalogHistory.setEnabled(!flag);
            generalSearch.setEnabled(flag);
            baseAttrSearch.setEnabled(flag);
            ibaSearch.setEnabled(flag);
            //leix 2009-11-27
            /* if (((QMPartIfc) obj).getBsoName().equals("GenericPart"))
            {
                materialList.setEnabled(!flag);
                bomList.setEnabled(!flag);

               //CCBegin by liunan 2008-07-30
               compareList.setEnabled(!flag);
               firstLevelSonList.setEnabled(!flag);
               logicMenuItem.setEnabled(!flag);
               //CCEnd by liunan 2008-07-30

                tailorBOM.setEnabled(!flag);
                gpartBom.setEnabled(flag);
            }
            else
            {*/
                materialList.setEnabled(flag);
                bomList.setEnabled(flag);

                //CCBegin by liunan 2008-07-30
                compareList.setEnabled(flag);
                firstLevelSonList.setEnabled(flag);
                logicMenuItem.setEnabled(!flag);
                //CCEnd by liunan 2008-07-30
                //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
                newLogicMenuItem.setEnabled(!flag);
                //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
                //CCBegin SS5
                firstLevelMenuItem.setEnabled(!flag);
                //CCEnd SS5
                //CCBegin SS1
                erpList.setEnabled(flag);
                //CCEnd SS1
                tailorBOM.setEnabled(!flag);
                gpartBom.setEnabled(!flag);
                //CCBegin SS3
                subCompBom.setEnabled(!flag);
                //CCEnd SS3
                //CCBegin SS4
                zcBom.setEnabled(!flag);
                dlzcBom.setEnabled(!flag);
                //CCEnd SS4
            //}
            setLifeCycleMenu(false);
//          leix 2009-11-27
        }
        else
        {
            createBaseline.setEnabled(flag);
            maintenanceBaseline.setEnabled(flag);
            addBaseline.setEnabled(flag);
            populateBaseline.setEnabled(flag);
            viewBaseline.setEnabled(flag);
            removeBaseline.setEnabled(flag);
            createConfigItem.setEnabled(flag);
            maintenanceConfigItem.setEnabled(flag);
            viewConfigItem.setEnabled(flag);
            addEffectivity.setEnabled(flag);
            populateEffectivity.setEnabled(flag);
            updateEffValue.setEnabled(flag);
            removeEffectivity.setEnabled(flag);
            defineAlternates.setEnabled(flag);
            defineSubstitutes.setEnabled(flag);
            publishViewVersion.setEnabled(flag);
            publishIBA.setEnabled(flag);
            setConfigSpecEnable();
            viewPart.setEnabled(flag);
            compareStructure.setEnabled(flag);
            ibaCompare.setEnabled(flag);
            saveAsHistory.setEnabled(flag);
            catalogHistory.setEnabled(flag);
            generalSearch.setEnabled(flag);
            baseAttrSearch.setEnabled(flag);
            ibaSearch.setEnabled(flag);
            /*if (((QMPartIfc) obj).getBsoName().equals("GenericPart"))
            {
                materialList.setEnabled(!flag);
                bomList.setEnabled(!flag);
                tailorBOM.setEnabled(!flag);
                gpartBom.setEnabled(flag);
            }
            else
            {
                materialList.setEnabled(flag);
                bomList.setEnabled(flag);
                tailorBOM.setEnabled(flag);
                gpartBom.setEnabled(!flag);
            }*/
            //CCBegin SS1
            erpList.setEnabled(flag);
            //CCEnd SS1
            materialList.setEnabled(flag);
            bomList.setEnabled(flag);
            tailorBOM.setEnabled(flag);

                //CCBegin by liunan 2008-07-30
                compareList.setEnabled(flag);
                firstLevelSonList.setEnabled(flag);
                logicMenuItem.setEnabled(flag);
                //CCEnd by liunan 2008-07-30
                //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
                newLogicMenuItem.setEnabled(flag);
                //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
                //CCBegin SS5
                firstLevelMenuItem.setEnabled(flag);
                //CCEnd SS5
                //CCBegin SS3
                subCompBom.setEnabled(flag);
                //CCEnd SS3
                //CCBegin SS4
                zcBom.setEnabled(!flag);
                dlzcBom.setEnabled(flag);
                //CCEnd SS4
            gpartBom.setEnabled(!flag);
            setLifeCycleMenu(true);
        }
        //CCBegin SS4
        if (obj instanceof QMPartIfc){
        	QMPartIfc part=(QMPartIfc)obj;
        	if(part.getPartType().toString().equalsIgnoreCase(
			"Model")){
        		zcBom.setEnabled(flag);
        	}else{
        		zcBom.setEnabled(!flag);
        	}
        }
        //CCEnd SS4
        //设置工具条上的按钮可用
        this.myExplorer.getExplorer().enableButton("part_update",true);
        this.myExplorer.getExplorer().enableButton("part_delete",true);
        this.myExplorer.getExplorer().enableButton("part_view",true);
        this.myExplorer.getExplorer().enableButton("part_checkIn",true);
        this.myExplorer.getExplorer().enableButton("part_checkOut",true);
        this.myExplorer.getExplorer().enableButton("part_repeal",true);
        this.myExplorer.getExplorer().enableButton("public_refresh",true);
        this.myExplorer.getExplorer().enableButton("public_clear",true);
        this.myExplorer.getExplorer().enableButton("public_paste",true);
        //问题(1) 20080226 zhangq begin:当选中根节点时，使得相应的菜单项和按钮处于不可用状态。
        this.myExplorer.getExplorer().enableButton("part_delete",true);
        this.myExplorer.getExplorer().enableButton("public_copy",true);
        this.myExplorer.getExplorer().enableButton("public_cut",true);
        //问题(1) 20080226 zhangq end
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "setPartMenu()..end.. ....");
    }


    /**
     * 选择对象为零部件主信息时菜单的有效性。
     */
    protected void setPartMasterMenu()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "setPartMasterMenu()..begin.. ....");
        boolean flag = true;
        createPart.setEnabled(flag);
        updatePart.setEnabled(!flag);
        copyPart.setEnabled(flag);
        //CCBegin SS2
        copyAllPart.setEnabled(flag);
        pasteAllPart.setEnabled(!flag);
        //CCEnd SS2
        cutPart.setEnabled(flag);
        pastePart.setEnabled(!flag);
        shareMovePart.setEnabled(flag);
        saveAsPart.setEnabled(!flag);
        renamePart.setEnabled(!flag);
        deletePart.setEnabled(!flag);

        //2003/12/16  两句
        clear.setEnabled(!flag);
        refresh.setEnabled(!flag);

        exit.setEnabled(flag);
        checkIn.setEnabled(!flag);
        checkOut.setEnabled(!flag);
        undoCheckOut.setEnabled(!flag);
        move.setEnabled(!flag);
        revise.setEnabled(!flag);
        versionHistroy.setEnabled(!flag);
        iterationHistroy.setEnabled(!flag);
        createBaseline.setEnabled(!flag);
        maintenanceBaseline.setEnabled(!flag);
        addBaseline.setEnabled(!flag);
        populateBaseline.setEnabled(!flag);
        viewBaseline.setEnabled(!flag);
        removeBaseline.setEnabled(!flag);
        createConfigItem.setEnabled(!flag);
        maintenanceConfigItem.setEnabled(!flag);
        viewConfigItem.setEnabled(!flag);
        addEffectivity.setEnabled(!flag);
        populateEffectivity.setEnabled(!flag);
        updateEffValue.setEnabled(!flag);
        removeEffectivity.setEnabled(!flag);
        defineAlternates.setEnabled(!flag);
        defineSubstitutes.setEnabled(!flag);
        publishViewVersion.setEnabled(!flag);
        publishIBA.setEnabled(!flag);
        setConfigSpecEnable();
        viewPart.setEnabled(flag);
        compareStructure.setEnabled(!flag);
        ibaCompare.setEnabled(!flag);
        saveAsHistory.setEnabled(!flag);
        catalogHistory.setEnabled(!flag);
        generalSearch.setEnabled(flag);
        baseAttrSearch.setEnabled(flag);
        ibaSearch.setEnabled(flag);
        materialList.setEnabled(!flag);
        bomList.setEnabled(!flag);
        //CCBegin by liunan 2008-07-30
        compareList.setEnabled(!flag);
        firstLevelSonList.setEnabled(!flag);
        logicMenuItem.setEnabled(!flag);
        //CCEnd by liunan 2008-07-30
        //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
        newLogicMenuItem.setEnabled(!flag);
        //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表 
        //CCBegin SS5
        firstLevelMenuItem.setEnabled(!flag);
        //CCEnd SS5
        tailorBOM.setEnabled(!flag);
        //CCBegin SS1
        erpList.setEnabled(!flag);
        //CCEnd SS1
        gpartBom.setEnabled(!flag);
        //CCBegin SS3
        subCompBom.setEnabled(!flag);
        //CCEnd SS3
        //CCBegin SS4
        zcBom.setEnabled(!flag);
        dlzcBom.setEnabled(!flag);
        //CCEnd SS4
        if ((PART_CLIENT.equals("B")) || (PART_CLIENT.equals("C")))
        {
            defineAlternates.setEnabled(flag);
            defineSubstitutes.setEnabled(flag);
        }
        else
        {
            defineAlternates.setEnabled(!flag);
            defineSubstitutes.setEnabled(!flag);
        }
        setLifeCycleMenu(false);
        productManager.setEnabled(true);
        about.setEnabled(true);
        
        //设置工具条上的按钮不可用
        this.myExplorer.getExplorer().enableButton("part_update",false);
        this.myExplorer.getExplorer().enableButton("part_delete",false);
//        this.myExplorer.getExplorer().enableButton("part_view",false);
        this.myExplorer.getExplorer().enableButton("part_checkIn",false);
        this.myExplorer.getExplorer().enableButton("part_checkOut",false);
        this.myExplorer.getExplorer().enableButton("part_repeal",false);
        this.myExplorer.getExplorer().enableButton("public_refresh",false);
        this.myExplorer.getExplorer().enableButton("public_clear",false);
        this.myExplorer.getExplorer().enableButton("public_paste",false);
        //问题(1) 20080226 zhangq begin:当选中根节点时，使得相应的菜单项和按钮处于不可用状态。
        this.myExplorer.getExplorer().enableButton("part_view",true);
        this.myExplorer.getExplorer().enableButton("public_copy",true);
        this.myExplorer.getExplorer().enableButton("public_cut",true);
        //问题(1) 20080226 zhangq end

        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "setPartMasterMenu()..end.. ....");
    }


    /**
     * 在重用管理器中使用。
     * 2003/12/23 为分类部分增加方法
     */
    public void launchFind()
    {
        myExplorer.processGeneralSearchCommand();
    }


    /**
     * 将选中零部件添加到浏览器中。
     * 2003/12/23 为分类部分增加方法
     * @param part QMPartIfc 选中的零部件值对象。
     */
    public void setPart(QMPartIfc part)
    {
        myExplorer.addExplorerPart(part);
    }


    /**
     * 显示错误信息框。
     * @param message String 信息内容。
     * @param title String 信息标题。
     */
    protected void showMessage(String message, String title)
    {
        String ok = QMMessage.getLocalizedMessage(RESOURCE,
                                                  QMProductManagerRB.OK, null);
        JOptionPane.showMessageDialog(new JButton(ok), message, title,
                                      JOptionPane.ERROR_MESSAGE);
    }


    /**
     * 获得myExplorer对象。
     * @return QMProductManager
     */
    public QMProductManager getMyExplorer()
    {
        return myExplorer;
    }    


    /**
     * 主函数。
     * @param args String[] 传递进来的参数。
     */
    public static void main(String[] args)
    {
        try
        {
//          String host = "10.28.68.118";
//          String port = "7001";
//          String username = "Administrator";
//          String userpassword = "Administrator";
//          String sid = null;
//          try
//          {
//              sid = RequestServer.getSessionID(host, port,
//                      username, userpassword);
//          }
//          catch (QMRemoteException ex1)
//          {
//              ex1.printStackTrace();
//          }
//          PartRequestServer server = new PartRequestServer(host,
//                  port, sid);
          PartRequestServer server = new PartRequestServer(args[0],
                  args[1], args[2]);
            RequestServerFactory.setRequestServer(server);
            System.setProperty("swing.useSystemFontSettings", "0");
            System.setProperty("swing.handleTopLevelPaint", "false");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            QMProductManagerJFrame frame = new QMProductManagerJFrame();
           // frame.getMyExplorer().setFromCapp(true);
            frame.setSize(ScreenParameter.largeWindowSize);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            ScreenParameter.centerWindow(frame);
        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }
    
}
