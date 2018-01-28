/**
 * 生成程序 CappRouteListManageController.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 *CR1  2009/03/12   郭晓亮   原因：优化删除工艺路线表功能。
 * 
 *                            方案：先将树节点和维护界面都消失，然后将真正的删除操作在后台的线程中
 *                                  处理，如果删除的事务执行的时间很长，用户同时进行搜索操作，在搜
 *                                  索的结果集中有可能会包含这个正在被删除的路线表，为了避免这种情
 *                                  况，在删除时将路线表的BSOID放到缓存中，然后将搜索出的结果与其比
 *                                  较，如果包含就给出提示。
 *                            
 *                           备注: 性能测试用例名称"删除工艺路线表". 
 * 
 * SS1 检出的限制不包括管理员，方便问题定位。 liunan 2014-4-14
 * SS2 A004-2016-3424 去掉SS1中判断处理，由权限判断。 liunan 2016-9-7
 */
package com.faw_qm.cappclients.capproute.controller;

import java.awt.Cursor;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JOptionPane;
import com.faw_qm.cappclients.beans.processtreepanel.ConfigSpecController;
import com.faw_qm.cappclients.beans.processtreepanel.PartTreePanel;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.util.AlreadyCheckedOutException;
import com.faw_qm.cappclients.capp.util.CheckedOutByOtherException;
import com.faw_qm.cappclients.capp.util.NotCheckedOutException;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.capproute.util.CappRouteRB;
import com.faw_qm.cappclients.capproute.util.RouteListTreeObject;
import com.faw_qm.cappclients.capproute.view.CappRouteListManageJFrame;
import com.faw_qm.cappclients.capproute.view.CompositiveRouteJFrame;
import com.faw_qm.cappclients.capproute.view.ReportFormsJDialog;
import com.faw_qm.cappclients.capproute.view.RouteListSearchJDialog;
import com.faw_qm.cappclients.capproute.view.RouteListTaskJPanel;
import com.faw_qm.cappclients.capproute.view.SearchPartsDialog;
import com.faw_qm.cappclients.capproute.view.SelectPartJDialog;
import com.faw_qm.cappclients.capproute.view.ViewPartsJDialog;
import com.faw_qm.clients.util.MessageDialog;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.lifecycle.client.view.LifeCycleStateDialog;
import com.faw_qm.lifecycle.client.view.ProjectStateDialog;
import com.faw_qm.lifecycle.client.view.SetLifeCycleStateDialog;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.QMThread;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterInfo;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.cappclients.capproute.view.RouteListRenameJDialog;
import com.faw_qm.clients.util.IntroduceDialog;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.clients.widgets.IBAUtility;
/**
 * Copyright: Copyright (c) 2004
 * Company: 一汽启明
 * Title:工艺路线表管理器主控制类
 * @author 刘明
 * @mender skybird
 * @mender zz
 * @version 1.0
 * （问题一）20061107 zz  提醒更明确些 start
 */
public class CappRouteListManageController extends CappRouteAction {
    public WorkThread theWorkThread;

    /** 工艺路线表维护主界面 */
    private CappRouteListManageJFrame view;
    /** 零部件树*/
    PartTreePanel partTreePanel ;//= new PartTreePanel(view);

    private ConfigSpecItem configSpecItem = null;
    //被删除的路线表缓存
    private  Vector deletedRouteListVec=new Vector();//CR1
    
    /** 线程组 */
    private ThreadGroup threadGroup = null;

    /** 菜单操作标识 */
    private final static int CREATE = 1;

    private final static int UPDATE = 2;

    private final static int DELETE = 3;

    private final static int REFRESH = 4;

    private final static int CLEAR = 5;

    private final static int EXIT = 6;

    private final static int VIEW = 7;

    private final static int BROWSE = 8;

    private final static int EDIT = 9;

    private final static int REPORT = 10;

    private final static int CHECKIN = 11;

    private final static int CHECKOUT = 12;

    private final static int UNDOCHECKOUT = 13;

    private final static int REVISE = 14;

    private final static int VERSIONHIS = 15;

    private final static int ITERATORHIS = 16;

    private final static int HELPMANAGE = 17;

    private final static int ABOUT = 18;

    private final static int RESETSTATE = 19;

    private final static int RESETLIFECYCLE = 20;

    private final static int LIFECYCLEHIS = 21;

    private final static int RESETPROJECT = 22;

    private final static int HELP = 23;

    private final static int CONFIG = 24;

    private final static int SEARCHBY = 25;

    private final static int COMPOSITIVEROUTE = 26;

    private final static int RENAME = 27;
    
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
    private final static int CHANGE = 28;
    //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总" 
//  CCBegin by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"   
    private final static int CHANGEFOLDER = 29;
//  CCEnd by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库" 
    
    /** 生成报表界面对象 */
    private ReportFormsJDialog reportDialog;

    /**
     * 构造函数
     *
     * @param frame
     *            工艺路线表维护主界面
     * @roseuid 4031638000E1
     */
    public CappRouteListManageController(CappRouteListManageJFrame frame) {
        this.view = frame;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        configInit();
        partTreePanel = new PartTreePanel(view);
    }

    /**
     * 初始化产品结构浏览器，获得用户当前的筛选条件 如果用户当前的筛选条件为为则设置默认值：
     * 标准 视图为空，生命周期为空，包括个人资料夹
     */
    private void configInit() {
        try {
            //获取零部件配置规范
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper
                    .getConfigSpec();
            ViewObjectIfc vo = null;
            //如果没有配置规范，构造默认的“标准”配置规范。
            if (configSpecInfo == null) {
                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                try {
                    dd = (Vector) RequestServerFactory.getRequestServer()
                            .request(info1);
                } catch (QMRemoteException e) {
                    JOptionPane.showMessageDialog(view, e.getClientMessage());
                }
                for (int i = 0; i < dd.size(); i++) {
                    if (((ViewObjectIfc) dd.elementAt(i)).getViewName().equals(
                            "制造视图")) {
                        vo = (ViewObjectIfc) dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);;
                //标准配置项
                PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
                //设置标准时的视图为空
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //调用服务将设置好的筛选条件保存到数据库中
                RequestServer server = RequestServerFactory.getRequestServer();
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("ExtendedPartService");
                info.setMethodName("savePartConfigSpec");
                Class[] paramClass = { PartConfigSpecIfc.class };
                info.setParaClasses(paramClass);
                Object[] paramValue = { configSpecInfo };
                info.setParaValues(paramValue);
                try {
                    configSpecInfo = (PartConfigSpecInfo) server.request(info);
                } catch (QMRemoteException ex) {
                    ex.printStackTrace();
                    return;
                }
                ConfigSpecItem config = new ConfigSpecItem(configSpecInfo);
                this.configSpecItem = config;
            } else {
                this.configSpecItem = new ConfigSpecItem(configSpecInfo);
            }

        } catch (QMRemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     *
     * @return TechnicsRouteListInfo 当前选择的对象
     * @throws QMRemoteException
     */
    public TechnicsRouteListInfo getSelectedObject() throws QMRemoteException {
        RouteListTreeObject obj = (RouteListTreeObject) view.getTreePanel()
                .getSelectedObject();
        if (obj == null) {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.NOT_SELECT_OBJECT, null);
            throw new QMRemoteException(message);
        } else if (obj.getObject() == null) {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.WRONG_TYPE_OBJECT, null);
            throw new QMRemoteException(message);
        }
        return (TechnicsRouteListInfo) obj.getObject();
    }

    /**
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     *
     * @return RouteListTreeObject 当前选择的对象
     * @throws QMRemoteException
     */
    public RouteListTreeObject getSelectedTreeObject() throws QMRemoteException {
        RouteListTreeObject obj = (RouteListTreeObject) view.getTreePanel()
                .getSelectedObject();
        if (obj == null) {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.NOT_SELECT_OBJECT, null);
            throw new QMRemoteException(message);
        } else if (obj.getObject() == null) {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.WRONG_TYPE_OBJECT, null);
            throw new QMRemoteException(message);
        }
        return obj;
    }


    /**
     * 向工艺路线表树上添加节点
     * @param RouteListTreeObject obj
     */
    public void addNode(RouteListTreeObject obj) {
        view.getTreePanel().addNode(obj);
    }

    /**
     * 处理"新建"命令
     * @see WorkThread
     */
    public void processCreateCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * 处理"更新"命令
     * @see WorkThread
     */
    public void processUpdateCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * 处理"删除"命令
     * @see WorkThread
     */
    public void processDeleteCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * 处理"刷新"命令
     * @see WorkThread
     */
    public void processRefreshCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESH);
        work.start();
    }

    /**
     * 处理"清除"命令
     * @see WorkThread
     */
    public void processClearCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * 处理"退出"命令
     * @see WorkThread
     */
    public void processExitCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), EXIT);
        work.start();
    }
    /**
     * 处理"重命名"命令
     * 20061220 zz
     */
    public void processRenameCommand() {
             WorkThread work = new WorkThread(getThreadGroup(), RENAME);
             work.start();
         }

    /**
     * 处理"查看"命令
     * @see WorkThread
     */
    public void processViewCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    /**
     * 处理“修改配置规则”命令
     * @see WorkThread
     */
    public void processConfigRuleCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CONFIG);
        work.start();
    }


    /**
     * 处理"搜索"命令
     * @see WorkThread
     */
    public void processBrowseCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), BROWSE);
        work.start();
    }

    /**
     * 处理"编辑路线"命令
     * @see WorkThread
     */
    public void processEditCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), EDIT);
        work.start();
    }

    /**
     * 处理按路线单位查看零部件及路线
     * @see WorkThread
     */
    public void processSearchByCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCHBY);
        work.start();
    }

    /**
     * 处理综合路线查看
     * @see WorkThread
     */
    public void processCompositiveRouteCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), COMPOSITIVEROUTE);
        work.start();
    }

    /**
     * 处理"生成报表"命令
     * @see WorkThread
     */
    public void processReportCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), REPORT);
        work.start();
    }
    
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"     
    /**
     * 处理"变更信息汇总"命令
     */
    public void processChangeCommand() {
      WorkThread work = new WorkThread(getThreadGroup(), CHANGE);
      work.start();
    }
    //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
    
//  CCBegin by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"    
    /**
     * 处理"变更信息汇总"命令
     */
    public void processChangeFolderCommand() {
      WorkThread work = new WorkThread(getThreadGroup(), CHANGEFOLDER);
      work.start();
    }
//  CCEnd by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"  

    /**
     * 处理"检入"命令
     * @see WorkThread
     */
    public void processCheckInCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKIN);
        work.start();
    }

    /**
     * 处理"检出"命令
     * @see WorkThread
     */
    public void processCheckOutCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKOUT);
        work.start();
    }

    /**
     * 处理"撤消检出"命令
     * @see WorkThread
     */
    public void processUndoCheckOutCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), UNDOCHECKOUT);
        work.start();
    }

    /**
     * 处理"修订"命令
     * @see WorkThread
     */
    public void processReviseCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), REVISE);
        work.start();
    }

    /**
     * 处理"查看版本历史"命令
     * @see WorkThread
     */
    public void processViewVersionCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), VERSIONHIS);
        work.start();
    }

    /**
     * 处理"查看版序历史"命令
     * @see WorkThread
     */
    public void processViewIteratorCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), ITERATORHIS);
        work.start();
    }

    /**
     * 处理"重新指定生命周期状态"命令
     * @see WorkThread
     */
    public void processResetStateCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), RESETSTATE);
        work.start();
    }

    /**
     * 处理"查看生命周期历史"命令
     * @see WorkThread
     */
    public void processViewLifeCycleCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), LIFECYCLEHIS);
        work.start();
    }

    /**
     * 处理"重新指定生命周期"命令
     * @see WorkThread
     */
    public void processResetLifeCycleCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), RESETLIFECYCLE);
        work.start();
    }

    /**
     * 处理"重新指定项目组"命令
     * @see WorkThread
     */
    public void processResetProjectCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), RESETPROJECT);
        work.start();
    }

    /**
     * 处理"关于路线表管理"命令
     * @see WorkThread
     */
    public void processHelpManageCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), HELPMANAGE);
        work.start();
    }

    /**
     * 处理"关于"命令
     * @see WorkThread
     */
    public void processHelpAboutCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * 处理"帮助"命令
     * @see WorkThread
     */
    public void processHelp1Command() {
        WorkThread work = new WorkThread(getThreadGroup(), HELP);
        work.start();
    }

    /**
     * <p>
     * Title:工作线程
     * </p>
     * <p>
     * Description:这是一个定义的内部的工作线程类
     * </p>
     * <p>
     * Copyright: Copyright (c) 2004
     * </p>
     * <p>
     * Company: 一汽启明
     * </p>
     * @see QMTread
     * @author 刘明
     * @version 1.0
     */

    class WorkThread extends QMThread {
        int myAction;

        //EJB定义的值对象
        TechnicsRouteListInfo myRouteList;
        //EJB定义的值对象
        TechnicsRouteListMasterInfo myRouteListMaster;

        /**
         * 重载的构造函数
         * @param ThreadGroup threadgroup
         * @param int action
         */
        public WorkThread(ThreadGroup threadgroup, int action) {
            super();
            CappRouteListManageController.this.threadGroup = threadgroup;
            this.myAction = action;
        }

        /**
         * 重载的构造函数
         * @param ThreadGroup threadgroup
         * @param int action
         * @param TechnicsRouteListInfo list
         */
        public WorkThread(ThreadGroup threadgroup, int action,
                TechnicsRouteListInfo list) {
            super();
            CappRouteListManageController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myRouteList = list;
        }

        /**
         * 重载的构造函数
         * @param ThreadGroup threadgroup
         * @param int action
         * @param TechnicsRouteListMasterInfo master
         */
        public WorkThread(ThreadGroup threadgroup, int action,
                TechnicsRouteListMasterInfo master) {
            super();
            CappRouteListManageController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myRouteListMaster = master;
        }
        /**
         * WorkThread的运行方法
         */
        public void run() {
            try {
                switch (myAction) {
                case CREATE:
                    createRouteList();
                    break;
                case UPDATE:
                    updateRouteList();
                    break;
                case DELETE:
                    deleteRouteList();
                    break;
                case REFRESH:
                    refresh();
                    break;
                case CLEAR:
                    clearAllObjects();
                    break;
                case EXIT:
                    exitSystem();
                    break;
                  case RENAME://zz
               renameRouteList();
               break;
                  case VIEW:
                    viewRouteList();
                    break;
                case CONFIG:
                    makeConfigRule();
                    break;
                case EDIT:
                    editCappRoute();
                    break;
                case SEARCHBY:
                    searchBy();
                    break;
                case COMPOSITIVEROUTE:
                    compositiveRoute();
                    break;
                case BROWSE:
                    searchRouteListFat();
                    break;
                case REPORT:
                    reportForms();
                    break;
                    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"      
                case CHANGE:
                    changeForms();
                    break;
                //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
//                  CCBegin by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"
                case CHANGEFOLDER:
                    changeFolder();
                    break;
//                  CCEnd by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"
                case CHECKIN:
                    checkInRouteList();
                    break;
                case CHECKOUT:
                    checkOutRouteList();
                    break;
                case UNDOCHECKOUT:
                    undoCheckOut();
                    break;
                case REVISE:
                    reviseRouteList();
                    break;
                case VERSIONHIS:
                    viewVersionHistory();
                    break;
                case ITERATORHIS:
                    viewIteratorHistory();
                    break;
                case HELPMANAGE:
                    helpManage();
                    break;
                case ABOUT:
                    helpAbout();
                    break;
                case HELP:
                    help();
                    break;
                case RESETSTATE:
                    resetLifeCycleState();
                    break;
                case LIFECYCLEHIS:
                    viewLifeCycleHistory();
                    break;
                case RESETLIFECYCLE:
                    resetLifeCycle();
                    break;
                case RESETPROJECT:
                    resetProject();
                }
            } catch (QMRemoteException e) {

                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "exception", null);
                JOptionPane.showMessageDialog(view, e.getClientMessage(),
                        title, JOptionPane.INFORMATION_MESSAGE);
            } finally {
                view.setCursor(Cursor.getDefaultCursor());
                QMCt.setContextGroup(null);
            }
        }
    }

    /**
     * 新建路线表
     *
     * @roseuid 403163BE0216
     */
    private void createRouteList() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTaskPanel().setViewMode(RouteListTaskJPanel.CREATE_MODE);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 更新路线表
     * @throws QMRemoteException
     * @roseuid 403163DA002C
     */
    private void updateRouteList() throws QMRemoteException {
        TechnicsRouteListInfo routelist = getSelectedObject();
        if (routelist instanceof WorkableIfc) {
            //如果是原本,则提示不许修改

            if (((WorkableIfc) routelist).getWorkableState().equals("c/o")) {
                if (verbose) {
                    System.out
                            .println("CappRouteListManageController:508:更新路线表的操作：如果是原本的分支");
                }

                 String whocheckoutthelist = routelist.getLocker();
                Class[] paraclass = {String.class};
                Object[] paraobj = {whocheckoutthelist};
                UserInfo info = (UserInfo) useServiceMethod(
                        "PersistService", "refreshInfo", paraclass, paraobj);

                Object[] objIdentity = { getIdentity(routelist),info.getUsersName() };
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);

                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.CANNOT_MODIFY_ORIGINAL_OBJECT, objIdentity);

                // （问题一）20061107 zz  提醒更明确些 start
                JOptionPane.showMessageDialog(view, message, title,
                        JOptionPane.INFORMATION_MESSAGE);
               // （问题一）20061107 zz  提醒更明确些 end
              //  JOptionPane.showMessageDialog(view,"工艺路线表"+routelist.getRouteListName()+"已被用户"+whocheckoutthelist+"检出");
                return;
            }
            //如果在公共资料夹,则检出
            else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) routelist)) {
                if (verbose) {
                    System.out
                            .println("CappRouteListManageController:526:更新路线表的操作：如果在公共资料夹中的分支");
                }

                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                Object[] objIdentity = { getIdentity(routelist) };
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.PLEASE_CONFIRM_CHECK_OUT, objIdentity);
                int result = JOptionPane.showConfirmDialog(view, message,
                        title, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                switch (result) {
                case JOptionPane.YES_OPTION: {
                    //routelist =
                    // (TechnicsRouteListInfo)this.checkOutRouteList();
                    if (checkOutRouteList()) //实质是判断是否检出成功了
                    {
                        view.setCursor(Cursor
                                .getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RouteListTaskJPanel p = view.getTaskPanel();
                        p.setTechnicsRouteList(this.getSelectedObject());
                        p.setViewMode(RouteListTaskJPanel.UPDATE_MODE);
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }
                }
                }
            } else //在个人资料夹
            {
                if (verbose) {
                    System.out
                            .println("CappRouteListManageController:559:更新路线表的操作：如果在个人资料夹中的分支");
                }

                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RouteListTaskJPanel p = view.getTaskPanel();
                p.setTechnicsRouteList(this.getSelectedObject());
                p.setViewMode(RouteListTaskJPanel.UPDATE_MODE);
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

//  CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线，已发布或已被检出的艺准通知书不能重命名
    /**
     * 20070103 add liuming
     * 重命名
     * @throws QMRemoteException
     */
    private void renameRouteList() throws QMRemoteException {
      TechnicsRouteListInfo obj = this.getSelectedObject();

      if (CheckInOutCappTaskLogic.isInVault( (WorkableIfc) obj)) {
        //CCBegin by liunan 2011-05-18 改成管理员可以改
        //if (isReleased(obj)) {
        //CCBegin SS2
        /*if (isReleased(obj)&&!inAdministrators()) {
        //CCEnd by liunan 2011-05-18
          String mm = obj.getRouteListNumber() + "已发布，不能修改！";
          JOptionPane.showMessageDialog(view, mm);
          return;
        }*/
        //CCBegin SS2

        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RouteListRenameJDialog dialog = new RouteListRenameJDialog(view, "", true);
        dialog.setTechnicsObject(obj);
        dialog.setVisible(true);
        this.viewDefaultRouteList();
        view.setCursor(Cursor.getDefaultCursor());
      }
      else
      {
        JOptionPane.showMessageDialog(view,obj.getRouteListNumber()+"已被检出，无法重命名！");
      }

    }
    
    /**
     * 判断：路线表是否处于发布状态
     * @param info TechnicsRouteListIfc
     * @return boolean
     */
    private boolean isReleased(TechnicsRouteListIfc info)
    {
      LifeCycleManagedIfc lcm = (LifeCycleManagedIfc)info;
     // System.out.println(lcm.getLifeCycleState().toString()) ;
      //System.out.println(lcm.getLifeCycleState().getDisplay()) ;
      if(lcm.getLifeCycleState().toString().equals("RELEASED"))
      {
        return true;
      }
      return false;
    }
//  CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线   

    /**
     * 查看路线表(廋客户)
     * @throws QMRemoteException
     * @roseuid 403163E60123
     */
    private void viewRouteList() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TechnicsRouteListIfc info = this.getSelectedObject();
        String bsoID = info.getBsoID();
        String masterID = ((TechnicsRouteListMasterIfc) info.getMaster())
                .getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //将bsoID传到“查看路线表”页面
        RichToThinUtil.toWebPage("route_look_routeList.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 修改配置规则
     */
    private void makeConfigRule() {
        new ConfigSpecController(configSpecItem, SelectPartJDialog
                .getPartTreePanel(),this.view);

    }


    /**
     * 查看路线表(肥客户)
     *
     * 当在路线表管理器上选中一个路线表时，将调用此方法来显示零部件信息。
     *
     * @roseuid 403163E60123
     */
    public void viewDefaultRouteList() {
        try {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RouteListTaskJPanel p = view.getTaskPanel();

            //工艺路线表对象
            p.setTechnicsRouteList(getSelectedObject());
            //查看界面模式
            p.setViewMode(RouteListTaskJPanel.VIEW_MODE);
            view.setCursor(Cursor.getDefaultCursor());

        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 删除路线表
     * @throws QMRemoteException
     * @roseuid 40316425019C
     */
    private void deleteRouteList() throws QMRemoteException {

        RouteListTreeObject treeObj = this.getSelectedTreeObject();
        TechnicsRouteListInfo info = (TechnicsRouteListInfo) treeObj
                .getObject();

            //modify by guoxl on 20080219(刷新路线表节点)
            Class[] paraclass = {
                String.class};
            Object[] paraobj = {
                info.getBsoID()};
            info = (TechnicsRouteListInfo) useServiceMethod(
                "PersistService", "refreshInfo", paraclass, paraobj);

            //modify by guoxl end

        //如果允许删除，显示是否删除选择框
        if (isDeleteAllowed(info)) {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.CONFIRM_DELETE_OBJECT, null);
            int result = JOptionPane.showConfirmDialog(view, message, title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            TechnicsRouteListInfo original = null;

            switch (result) {
            case JOptionPane.YES_OPTION: {
                //如果选择对象为工作副本，获得原本
                if (CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info)) {
                    original = (TechnicsRouteListInfo) CheckInOutCappTaskLogic
                            .getOriginalCopy((WorkableIfc) info);
                }
             // Begin CR1
                view.getTaskPanel().setVisible(false); 
				view.setCursor(Cursor.getDefaultCursor());
				deletedRouteListVec.add(info.getBsoID());
				view.getTreePanel().removeNode(treeObj);

				 java.net.URL url = CappRouteListManageJFrame.class.getResource(
			        "/images/routeList_delete2.gif");
			   
				 treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
				 treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
				 treeObj.setNoteText("(此路线表正在被删除...)");
				 view.getTreePanel().addNode(treeObj);
				// End CR1
                
                
                //刷新并删除info，同时刷新管理器界面
                //info = (TechnicsRouteListInfo)CappClientHelper.refresh(info);
                //调用Capp服务，删除指定的路线表
                Class[] paraClass = { TechnicsRouteListIfc.class };
                Object[] obj = { info };
                useServiceMethod("TechnicsRouteService",
                        "deleteRouteList", paraClass, obj);
                //如果原本不为空，刷新原本为检入状态
                if (original != null) {
                    original.setWorkableState("c/i");
                    view.getTreePanel().updateNode(
                            new RouteListTreeObject(original));
                }
                view.getTreePanel().removeNode(treeObj);
              //  view.getTaskPanel().setVisible(false);// End CR1
            }
            } //end case
           // view.setCursor(Cursor.getDefaultCursor());// End CR1
        }
    }
    /**
	 *  获得删除时缓存的路线表BSOID
	 * @return  Vector 存放路线表BSOID
	 * CR1
	 */
	public Vector getDeleteRouteLisVec()
	{

		return this.deletedRouteListVec;

	}

    /**
     * 判断是否允许删除指定的路线表。
     * <p>
     * 检查是否有其它用户更新该路线表
     * </p>
     *
     * @param info TechnicsRouteListInfo
     *            要删除的工艺卡(工序或工步)
     * @return 如果允许删除，则返回true
     */
    private boolean isDeleteAllowed(TechnicsRouteListInfo info) {
        //如果是原本，返回flase
        if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info)
                && CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) info)) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information",
                    null);
           // Object[] obj = { getIdentity(info) };
           String whocheckoutthelist = info.getLocker();
              Class[] paraclass = {String.class};
              Object[] paraobj = {whocheckoutthelist};
              UserInfo useinfo = null;
              try {
                 useinfo = (UserInfo) useServiceMethod(
                    "PersistService", "refreshInfo", paraclass, paraobj);
              }
              catch (QMRemoteException ex) {
                ex.printStackTrace();
              }

              Object[] objIdentity = { getIdentity(info),useinfo.getUsersName() };

              String message = QMMessage.getLocalizedMessage(RESOURCE,
                      CappRouteRB.CANNOT_MODIFY_ORIGINAL_OBJECT, objIdentity);


            JOptionPane.showMessageDialog(view, message, title,
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 搜索路线表（肥客户）
     *
     * @roseuid 4031646C0271
     */
    private void searchRouteListFat() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RouteListSearchJDialog d = new RouteListSearchJDialog(view);
        d.setVisible(true);
        view.setCursor(Cursor.getDefaultCursor());
    }
    /**
     * 搜索路线表（廋客户）
     *
     * @roseuid 403164BB03DD
     */
    private void searchRouteListThin() {

    }

    /**
     * 生成报表
     * @throws QMRemoteException
     * @roseuid 403164F103E4
     */
    private void reportForms() throws QMRemoteException {
        if (reportDialog == null) {
            reportDialog = new ReportFormsJDialog(view);
        }
        reportDialog.setRouteList(this.getSelectedObject());
        reportDialog.setVisible(true);
    }
    
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
    /**
     *  变更信息汇总
     * @roseuid 403164F103E4
     */
    private void changeForms() throws QMRemoteException {
      view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      BaseValueInfo obj = (BaseValueInfo)this.getSelectedObject();
      if (obj instanceof WorkableIfc) {
        WorkableIfc workable = (WorkableIfc) obj;
        String objID = workable.getBsoID();
        String vv = ( (TechnicsRouteListIfc) workable).getVersionID();
      //  System.out.println("vv==" + vv);
        if (vv.equals("A")) {
          String title = QMMessage.getLocalizedMessage(RESOURCE,
              "information", null);
          JOptionPane.showMessageDialog(view, "当前艺准通知书只有一个大版本", title,
                                        JOptionPane.INFORMATION_MESSAGE);
          return;

        }
        HashMap hashmap = new HashMap();
        hashmap.put("BsoID", objID);
        //转到"版本历史"页面
        RichToThinUtil.toWebPage("route_version_change.screen",
                                 hashmap);
      }
      view.setCursor(Cursor.getDefaultCursor());
    }
    //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"变更信息汇总"  
    
    /**
     * 处理"移动"命令。
     */
    public void changeFolder()throws QMRemoteException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo)this.getSelectedObject();
        //如果没有选择对象，显示信息“没有选择操作对象”。
        if(null == obj)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.NOT_SELECT_OBJECT, null);
         //   String message = "没有选择要更改资料夹的对象！";
            JOptionPane.showMessageDialog(view, message, title,
                    JOptionPane.WARNING_MESSAGE);
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
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
//                String message = QMMessage.getLocalizedMessage(RESOURCE,
//                        QMProductManagerRB.NOT_MOVE, null);
                String message="该对象已经被检出，不能被移动。";
                JOptionPane.showMessageDialog(view, message, title,
                        JOptionPane.WARNING_MESSAGE);
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
                new ChangeFolderController((RevisionControlledIfc) folderedIfc,view);
            }
            //如果obj在个人文件夹中，提示异常信息：
            //“该对象不在公共资料夹中，不能被移到其他资料夹中。”。
            else
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
//                String message = QMMessage.getLocalizedMessage(RESOURCE,
//                        QMProductManagerRB.NOT_IN_COMMON_FOLDER, null);
                String message="该对象不在公共资料夹中，不能被移到其他资料夹中。";
                    JOptionPane.showMessageDialog(view, message, title,
                                                  JOptionPane.WARNING_MESSAGE);
                  message = null;
                return;
            }
            folder = null;
        } //end if (obj instanceof FolderedIfc)     
      //  this.viewDefaultRouteList();
 
        obj = null;
    }
    

    
//  CCBegin by leixiao 2009-9-20 原因：解放升级工艺路线,增加"转库"

    /**
     * 编辑路线
     * @throws QMRemoteException
     * @roseuid 4031705701F6
     */
    private void editCappRoute() throws QMRemoteException {
        TechnicsRouteListInfo routelist = getSelectedObject();
        if (routelist instanceof WorkableIfc) {
            //如果是原本,则提示不许修改
            if (((WorkableIfc) routelist).getWorkableState().equals("c/o")) {
                Object[] objIdentity = { getIdentity(routelist) };
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.CANNOT_MODIFY_ORIGINAL_OBJECT, objIdentity);
                JOptionPane.showMessageDialog(view, message, title,
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //如果在公共资料夹,则检出
            else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) routelist)) {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                Object[] objIdentity = { getIdentity(routelist) };
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.PLEASE_CONFIRM_CHECK_OUT, objIdentity);
                int result = JOptionPane.showConfirmDialog(view, message,
                        title, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                switch (result) {
                case JOptionPane.YES_OPTION: {
                    //routelist =
                    // (TechnicsRouteListInfo)this.checkOutRouteList();
                    if (checkOutRouteList()) //实质是判断是否检出成功了
                    {
                        view.setCursor(Cursor
                                .getPredefinedCursor(Cursor.WAIT_CURSOR));
                        ViewPartsJDialog d = new ViewPartsJDialog(view);
                        d.setTechnicsRouteList(getSelectedObject());
                        d.setVisible(true);
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }
                }
                }
            } else //在个人资料夹
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ViewPartsJDialog d = new ViewPartsJDialog(view);
                d.setTechnicsRouteList(routelist);
                d.setVisible(true);
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    /**
     *按路线单位搜索
     *@see  SearchPartsDialog
     */
    public void searchBy() {
        SearchPartsDialog searchPartsDialog = new SearchPartsDialog(this.view);
        searchPartsDialog.setSize(400, 300);
        searchPartsDialog.setVisible(true);
    }

    /**
     * 显示综合路线界面
     * @see CompositiveRouteJFrame
     */
    public void compositiveRoute() {
        if (verbose) {
            System.out
                    .println("CappRouteListManageController:938:compositiveRoute()");
        }
        CompositiveRouteJFrame ompositiveRouteJFrame = new CompositiveRouteJFrame(
                this.view);
        ompositiveRouteJFrame.setSize(720, 540);
        ompositiveRouteJFrame.setVisible(true);
    }

    /**
     * 刷新
     * @throws QMRemoteException
     */
    public void refresh() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().refreshNode(this.getSelectedTreeObject());
        RouteListTreeObject obj = (RouteListTreeObject) view.getTreePanel()
                .getSelectedObject();
        if(obj==null)
        {
          String title = QMMessage.getLocalizedMessage(RESOURCE, "information",
                    null);
          JOptionPane.showMessageDialog(view, "对象已经不存在", title,
                    JOptionPane.INFORMATION_MESSAGE);
          return;
        }
      //  TechnicsRouteListIfc newList = RParentJPanel.refreshListForNew(this.getSelectedObject());
        view.getTaskPanel().setVisible(false);
        this.viewDefaultRouteList();
        view.refresh();
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 清除
     * @throws QMRemoteException
     */
    private void clearAllObjects() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().removeAllSelectedNodes();
        view.getTaskPanel().setVisible(false);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 退出
     */
    private void exitSystem() {
        if (view.isExitSystem) {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.IS_EXIT_SYSTEM, null);
            int result = JOptionPane.showConfirmDialog(view, message, title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (result) {
            case JOptionPane.YES_OPTION: {
                System.exit(0);
            }
            }
        } else {
            view.dispose();
        }
    }

    /**
     * 检入
     * @throws QMRemoteException
     */
    private void checkInRouteList() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RLCheckInController c = new RLCheckInController(view);
        try {
            c.setCheckinItem((WorkableIfc) this.getSelectedObject());
            c.checkin();
        } catch (NotCheckedOutException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 检出
     * @throws QMRemoteException
     */
    private boolean checkOutRouteList() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("capproute.controller.CappRouteListManageController.checkOutRouteList() begin...");
        }
        String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                null);
        boolean successful = false;
        WorkableIfc workable = (WorkableIfc) this.getSelectedObject();
//      CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,已发布的路线不能检出
        TechnicsRouteListInfo info = this.getSelectedObject();
        //CCBegin SS1
        //if (isReleased(info)) {
        //CCBegin SS2
        /*if (isReleased(info)&&!inAdministrators()) {
        //CCEnd SS1	
            String mm = info.getRouteListNumber() + "已发布，不能检出！";
            JOptionPane.showMessageDialog(view, mm);
            return false;
          }*/
        //CCEnd SS2
//      CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线  
        try {
            RLCheckOutController checkout_task = new RLCheckOutController(view,
                    CheckInOutCappTaskLogic.getCheckoutFolder());
            checkout_task.setCheckOutItem(workable);
            successful = checkout_task.checkout();
        } catch (AlreadyCheckedOutException e) {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (CheckedOutByOtherException e) {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (QMRemoteException e) {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (QMException e) {
            e.printStackTrace();
        }

        if (verbose) {
            System.out
                    .println("capproute.controller.CappRouteListManageController.checkOutRouteList() end...return void");
        }
        return successful;
    }

    /**
     * 撤消检出
     * @throws QMRemoteException
     */
    private void undoCheckOut() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo) this.getSelectedObject();
        if (obj instanceof WorkableIfc) {
            RLUndoCheckOutController undo_checkout_task = new RLUndoCheckOutController(
                    view, (WorkableIfc) obj);
            undo_checkout_task.undoCheckout();
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 修订
     * @throws QMRemoteException
     */
    private void reviseRouteList() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo) this.getSelectedObject();
        if (obj instanceof VersionedIfc) {
            VersionedIfc version = (VersionedIfc) obj;
            RLReviseController revise_task = new RLReviseController(view,
                    version);
            revise_task.revise();
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 查看版本历史
     * @throws QMRemoteException
     */
    private void viewVersionHistory() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo) this.getSelectedObject();

        if (obj instanceof WorkableIfc) {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = workable.getBsoID();

            HashMap hashmap = new HashMap();
            //modify by guoxl on 2008.03.31(进入瘦客户时传入的bsoID错误)
            hashmap.put("bsoID", objID);
            //modify by guoxl end
            //转到"版本历史"页面
            RichToThinUtil.toWebPage("route_version_viewVersionHistory.screen",
                    hashmap);

        }

        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 查看版序历史
     * @throws QMRemoteException
     */
    private void viewIteratorHistory() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo) this.getSelectedObject();


        if (obj instanceof WorkableIfc) {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = null;
            HashMap hashmap = new HashMap();
            if (workable instanceof IteratedIfc) {

              /*modify by guoxl on 20080304(为了满足查看版序历史时只显示当前版本及它的
               *前驱的功能，因此给route_version_viewIterationsHistory.jsp传入
               *TechnicsRouteList的BsoID)
               */
             // MasteredIfc master = ( (IteratedIfc) workable).getMaster();
             // objID = master.getBsoID();

                objID = ((IteratedIfc)workable).getBsoID();

              //modify end
              //modify by guoxl on 2008.03.31(进入瘦客户时传入的bsoID错误)
                hashmap.put("bsoID", objID);
              //modify by guoxl end
                //转到"版序历史"页面
                RichToThinUtil.toWebPage(
                        "route_version_viewIterationsHistory.screen", hashmap);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 关于路线表管理
     */
    private void helpManage() {

    }

    /**
     * 关于
     */
    private void helpAbout() {
        //CappRouteListManageJFrame_AboutBox dlg = new CappRouteListManageJFrame_AboutBox(view);
        IntroduceDialog dlg = new IntroduceDialog(view,"工艺路线管理器");
        dlg.setVisible();
    }
    /**
     * 帮助
     *
     */
    private void help() {
        QMHelpSystem helpSystem = null;
        if (helpSystem == null) {
            try {
                helpSystem = new QMHelpSystem(
                        "Summary",
                        null,
                        "OnlineHelp",
                        ResourceBundle
                                .getBundle(
                                        "com.faw_qm.cappclients.capproute.util.CappRouteRB",
                                        RemoteProperty.getVersionLocale()));
            } catch (Exception exception) {
                (new MessageDialog(this.view, true, exception
                        .getLocalizedMessage())).setVisible(true);
            }
        }
        helpSystem.showHelp("QMSummary");
    }

    /**
     * 重新指定生命周期状态
     * @throws QMRemoteException
     */
    private void resetLifeCycleState() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycleStateate() begin...");

        }
        TechnicsRouteListInfo info = this.getSelectedObject();
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SetLifeCycleStateDialog dialog = null;
        try {
            dialog = new SetLifeCycleStateDialog(view, info);
            dialog.setModal(true);
            dialog.setVisible(true);
            if (!dialog.isShowing() && dialog.getObject() != null) {
                Class[] paraclass = { BaseValueIfc.class, Boolean.TYPE };
                Object[] paraobj = {
                        (TechnicsRouteListInfo) dialog.getObject(),
                        new Boolean(false) };
                info = (TechnicsRouteListInfo) useServiceMethod(
                        "PersistService", "updateValueInfo", paraclass, paraobj);
                RouteListTreeObject treeObject = new RouteListTreeObject(info);
                view.getTreePanel().updateNode(treeObject);
                view.getTaskPanel().setVisible(false);
                this.viewDefaultRouteList();
            }
        } catch (QMException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        view.setCursor(Cursor.getDefaultCursor());

        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycleStateate() end...return is void");
        }
    }

    /**
     * 查看生命周期历史
     * @throws QMRemoteException
     */
    private void viewLifeCycleHistory() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.viewLifeCycleHistory() begin...");

        }
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String bsoID = this.getSelectedObject().getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //将technicsBsoID传到“查看生命周期历史记录”页面
        RichToThinUtil.toWebPage("route_look_lifeCyleHistory.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());

        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.viewLifeCycleHistory() end...return is void");
        }
    }

    /**
     * 重新指定生命周期
     * @throws QMRemoteException
     */
    private void resetLifeCycle() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycle() begin...");

        }
        TechnicsRouteListInfo obj = this.getSelectedObject();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "afreshAssignLifeCycle", null);
        LifeCycleStateDialog a = new LifeCycleStateDialog(view, title, true);
        String str = getIdentity(obj);
        a.setName(str);
        a.setLifeCycleManaged((LifeCycleManagedIfc) obj);
        a.setVisible(true);
        if (!a.isShowing() && a.getObject() != null) {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Class[] paraclass = { BaseValueIfc.class, Boolean.TYPE };
            Object[] paraobj = { (TechnicsRouteListInfo) a.getObject(),
                    new Boolean(false) };
            obj = (TechnicsRouteListInfo) useServiceMethod("PersistService",
                    "updateValueInfo", paraclass, paraobj);
            RouteListTreeObject treeObject = new RouteListTreeObject(obj);
            view.getTreePanel().updateNode(treeObject);
            view.getTaskPanel().setVisible(false);
            this.viewDefaultRouteList();
        } else {
            view.getTreePanel().updateNode(new RouteListTreeObject(obj));
        }

        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycle() end...return is void");
        }
    }

    /**
     * 重新指定项目组
     * @throws QMRemoteException
     */
    private void resetProject() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetProject() begin...");

        }
        TechnicsRouteListInfo obj = this.getSelectedObject();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "afreshAssignProject", null);
        ProjectStateDialog a = new ProjectStateDialog(view, title, true);
        a.setSize(430,120);
        String str = getIdentity(obj);
        a.setName(str);
        a.setLifeCycleManaged((LifeCycleManagedIfc) obj);
        a.setVisible(true);
        if (!a.isVisible() && a.getObject() != null) {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Class[] paraclass = { BaseValueIfc.class, Boolean.TYPE };
            Object[] paraobj = { (TechnicsRouteListInfo) a.getObject(),
                    new Boolean(false) };
                //调用服务传入获得的值对象paraobj
            obj = (TechnicsRouteListInfo) useServiceMethod("PersistService",
                    "updateValueInfo", paraclass, paraobj);
             //构造封装了指定的业务对象的路线表节点对象
            RouteListTreeObject treeObject = new RouteListTreeObject(obj);
            //显示路线被更新后的节点
            view.getTreePanel().updateNode(treeObject);
            //获得路线表维护面板
            view.getTaskPanel().setVisible(false);
            //当在路线表管理器上选中一个路线表时，将调用此方法来显示零部件信息。
            this.viewDefaultRouteList();
        } else {
            view.getTreePanel().updateNode(new RouteListTreeObject(obj));
        }

        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetProject() end...return is void");
        }
    }
    
    //CCBegin by liunan 2011-05-18 判断当前用户是否是管理员。
    public static boolean inAdministrators() throws QMRemoteException 
    {
    	Class[] paraclass = {};
    	Object[] paraobj = {};
    	return (Boolean)useServiceMethod("UsersService", "inAdministrators", paraclass, paraobj);
		}
}
