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
 *CR2  2011/12/15 徐春英    原因：统一检入、检出、撤销检出、修订功能的界面和控制类   参见：Wip专题更改说明1_张强.doc、Version专题更改说明1_张强.doc
 *CR3  2011/12/20 吕航  原因：统一重命名
 *CR4 2011/12/28 吕航 原因 增加可以选中多个树节点方法
 *CR5 2012/01/06 吕航 原因 改为批量检入、检出、撤销检出、删除
 *CR6 2012/01/06 徐春英     增加发布路线功能
 *CR7 2012/03/29 吕航原因参见TD5975
 *CR8 吕航  具体对象里不保存编号名称
 *SS1 长特供应商 liuyang 2014-8-14
 * SS2 重命名长度，名称由30改为50。 liunan 2015-1-19
 */
package com.faw_qm.cappclients.conscapproute.controller;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JOptionPane;

import com.faw_qm.cappclients.beans.processtreepanel.ConfigSpecController;
import com.faw_qm.cappclients.beans.processtreepanel.PartTreePanel;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.util.NotCheckedOutException;

import com.faw_qm.util.QMCt;
import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.conscapproute.util.RouteListTreeObject;
import com.faw_qm.cappclients.conscapproute.util.RouteTreeNode;
import com.faw_qm.cappclients.conscapproute.util.RouteTreePanel;
import com.faw_qm.cappclients.conscapproute.view.CappRouteListManageJFrame;
import com.faw_qm.cappclients.conscapproute.view.CompositiveRouteJFrame;
import com.faw_qm.cappclients.conscapproute.view.CompositiveShowPartsJDialog;
import com.faw_qm.cappclients.conscapproute.view.RouteListSearchJDialog;
import com.faw_qm.cappclients.conscapproute.view.RouteListTaskJPanel;
import com.faw_qm.cappclients.conscapproute.view.SearchPartsDialog;
import com.faw_qm.cappclients.conscapproute.view.SelectPartJDialog;
import com.faw_qm.cappclients.conscapproute.view.ShortCutRouteDialog;
import com.faw_qm.cappclients.conscapproute.view.ViewModelRouteJDialog;
import com.faw_qm.clients.rename.view.RenameJDialog;
import com.faw_qm.clients.util.MessageDialog;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.enterprise.client.vc.control.BatchCheckInTask;
import com.faw_qm.enterprise.client.vc.control.ReviseControl;
import com.faw_qm.enterprise.client.vc.control.ReviseTask;
import com.faw_qm.enterprise.client.vc.util.CheckInOutHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
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
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.util.PartHelper;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterInfo;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressUtil;
import com.faw_qm.wip.util.WorkingPair;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.clients.util.IntroduceDialog;
import com.faw_qm.clients.widgets.DialogFactory;

/**
 * Copyright: Copyright (c) 2004 Company: 一汽启明 Title:工艺路线表管理器主控制类
 * @author 刘明
 * @mender skybird
 * @mender zz
 * @version 1.0 （问题一）20061107 zz 提醒更明确些 start
 */
public class CappRouteListManageController extends CappRouteAction
{
    public WorkThread theWorkThread;

    /** 工艺路线表维护主界面 */
    private CappRouteListManageJFrame view;
    /** 零部件树 */
    PartTreePanel partTreePanel;//= new PartTreePanel(view);
    private ConfigSpecItem configSpecItem = null;
    //被删除的路线表缓存
    private Vector deletedRouteListVec = new Vector();//CR1

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
    //CR5 begin
    private final static int REFRESHROUTELIST = 28;
    private final static int ADDROUTELIST = 29;
    private final static int DELROUTELIST = 30;
    //CR6
    private final static int RELEASEROUTE = 31;
    private final static int VIEWMODELROUTE = 32;
//CCBegin SS1
    private final static int CREATESUPPLIER=33;
    private final static int SEARCHSUPPLIER=34;
    private final static int SEARCHPART=35;
 //CCEnd SS1
    /** 内部类 */
    private MainRefreshListener listener;
    //CR5 end
    /** 生成报表界面对象 */
    private CompositiveShowPartsJDialog compositiveDialog;

    //20120111 xucy add
    /** 资源文件路径 */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * 构造函数
     * @param frame 工艺路线表维护主界面
     * @roseuid 4031638000E1
     */
    public CappRouteListManageController(CappRouteListManageJFrame frame)
    {
        this.view = frame;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        configInit();
        partTreePanel = new PartTreePanel(view);
        //CR5 begin
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(listener = new MainRefreshListener());
        }
        //CR5 end
    }

    /**
     * 初始化产品结构浏览器，获得用户当前的筛选条件 如果用户当前的筛选条件为为则设置默认值： 标准 视图为空，生命周期为空，包括个人资料夹
     */
    private void configInit()
    {
        try
        {
            //获取零部件配置规范
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo)PartHelper.getConfigSpec();
            ViewObjectIfc vo = null;
            //如果没有配置规范，构造默认的“标准”配置规范。
            if(configSpecInfo == null)
            {
                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                //                try
                //                {
                dd = (Vector)RequestHelper.request(info1);
                //                }catch(Exception e)
                //                {
                //                    //JOptionPane.showMessageDialog(view, e.getClientMessage());
                //                    String message = QMExceptionHandler.handle(e);
                //                    DialogFactory.showInformDialog(view, message);
                //                }
                for(int i = 0;i < dd.size();i++)
                {
                    if(((ViewObjectIfc)dd.elementAt(i)).getViewName().equals("制造视图"))
                    {
                        vo = (ViewObjectIfc)dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                ;
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
                Class[] paramClass = {PartConfigSpecIfc.class};
                info.setParaClasses(paramClass);
                Object[] paramValue = {configSpecInfo};
                info.setParaValues(paramValue);
                //                try
                //                {
                configSpecInfo = (PartConfigSpecInfo)RequestHelper.request(info);
                //                }catch(QMRemoteException ex)
                //                {
                //                    ex.printStackTrace();
                //                    return;
                //                }
                ConfigSpecItem config = new ConfigSpecItem(configSpecInfo);
                this.configSpecItem = config;
            }else
            {
                this.configSpecItem = new ConfigSpecItem(configSpecInfo);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showInformDialog(view, message);
        }
    }

    //CR5 begin
    /**
     * <p>Title: 增加刷新服务监听类2003-08-27。</p> <p>Description: 内部类。</p> <p>Copyright: Copyright (c) 2003</p> <p>Company: 一汽启明</p>
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
            try {
            if(obj instanceof TechnicsRouteListInfo)
            {
                switch(i)
                {
                case RefreshEvent.CREATE:
                {
                 
						addRouteList((TechnicsRouteListInfo)obj);
					
                    obj = null;
                    return;
                }
                case RefreshEvent.UPDATE:
                {
                    refreshRouteList((TechnicsRouteListInfo)obj);
                    obj = null;
                    return;
                }
                case RefreshEvent.DELETE:
                {
                    delRouteList((TechnicsRouteListInfo)obj);
                    obj = null;
                    return;
                }
                }
            }
            } catch (QMException e) {
				
				e.printStackTrace();
				
				return;
			}
            obj = null;
        }

        /**
         * 构造函数。
         */
        MainRefreshListener()
        {}
    }

    /**
     * 刷新管理器中的零部件。
     * @param modifiedPart QMPartIfc 被刷新的零部件。
     * @throws QMException 
     */
    protected void refreshRouteList(TechnicsRouteListInfo modifiedroutelist) throws QMException
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESHROUTELIST, modifiedroutelist);
        work.start();
        try
        {
            work.join();
        }catch(Exception e)
        {
            throw new QMException(e);
        }
    }

    protected void delRouteList(TechnicsRouteListInfo delroutelist) throws QMException
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELROUTELIST, delroutelist);
        work.start();
        try
        {
            work.join();
        }catch(Exception e)
        {
            throw new QMException(e);
        }
    }

    protected void addRouteList(TechnicsRouteListInfo addroutelist) throws QMException
    {
        WorkThread work = new WorkThread(getThreadGroup(), ADDROUTELIST, addroutelist);
        work.start();
        try
        {
            work.join();
        }catch(Exception e)
        {
            throw new QMException(e);
        }
    }

    //CR5 end
    /**
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     * @return TechnicsRouteListInfo 当前选择的对象
     * @throws QMException 
     * @throws QMRemoteException
     */
    public TechnicsRouteListInfo getSelectedObject() throws QMException
    {
        RouteListTreeObject obj = (RouteListTreeObject)view.getTreePanel().getSelectedObject();
        if(obj == null)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_SELECT_OBJECT, null);
            throw new QMException(message);
        }else if(obj.getObject() == null)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.WRONG_TYPE_OBJECT, null);
            throw new QMException(message);
        }
        return (TechnicsRouteListInfo)obj.getObject();
    }

    //CR4 begin
    /**
     * 获得所有选中的工艺路线表值对象
     * @return TechnicsRouteListInfo[] 当前选择的对象
     * @throws QMException 
     * @throws QMRemoteException
     */
    public TechnicsRouteListInfo[] getSelectedObjects() throws QMException
    {
        RouteTreeNode[] nodes = view.getTreePanel().getSelectedNodes();
        if(nodes == null)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_SELECT_OBJECT, null);
            throw new QMException(message);
        }
        RouteListTreeObject[] obj = new RouteListTreeObject[nodes.length];
        TechnicsRouteListInfo[] obj1 = new TechnicsRouteListInfo[obj.length];

        for(int i = 0;i < nodes.length;i++)
        {
            obj[i] = (RouteListTreeObject)nodes[i].getObject();
        }
        for(int i = 0;i < obj.length;i++)
        {
            if(obj[i] == null)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_SELECT_OBJECT, null);
                throw new QMException(message);
            }else if(obj[i].getObject() == null)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.WRONG_TYPE_OBJECT, null);
                throw new QMException(message);
            }
            obj1[i] = (TechnicsRouteListInfo)obj[i].getObject();
        }

        return obj1;
    }

    public RouteListTreeObject[] getSelectedTreeObjects() throws QMException
    {
        RouteTreeNode[] nodes = view.getTreePanel().getSelectedNodes();
        RouteListTreeObject[] obj = new RouteListTreeObject[nodes.length];
        for(int i = 0;i < nodes.length;i++)
        {
            obj[i] = (RouteListTreeObject)nodes[i].getObject();
            if(obj[i] == null)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_SELECT_OBJECT, null);
                throw new QMException(message);
            }else if(obj[i].getObject() == null)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.WRONG_TYPE_OBJECT, null);
                throw new QMException(message);
            }
        }
        return obj;
    }

    //CR4 end

    /**
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     * @return RouteListTreeObject 当前选择的对象
     * @throws QMException 
     * @throws QMRemoteException
     */
    public RouteListTreeObject getSelectedTreeObject() throws QMException
    {
        RouteListTreeObject obj = (RouteListTreeObject)view.getTreePanel().getSelectedObject();
        if(obj == null)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_SELECT_OBJECT, null);
            throw new QMException(message);
        }else if(obj.getObject() == null)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.WRONG_TYPE_OBJECT, null);
            throw new QMException(message);
        }
        return obj;
    }

    /**
     * 向工艺路线表树上添加节点
     * @param RouteListTreeObject obj
     */
    public void addNode(RouteListTreeObject obj)
    {
        view.getTreePanel().addNode(obj);
    }

    /**
     * 处理"新建"命令
     * @see WorkThread
     */
    public void processCreateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * 处理"更新"命令
     * @see WorkThread
     */
    public void processUpdateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * 处理"删除"命令
     * @see WorkThread
     */
    public void processDeleteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * 处理"刷新"命令
     * @see WorkThread
     */
    public void processRefreshCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESH);
        work.start();
    }

    /**
     * 处理"清除"命令
     * @see WorkThread
     */
    public void processClearCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * 处理"退出"命令
     * @see WorkThread
     */
    public void processExitCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXIT);
        work.start();
    }

    /**
     * 处理"重命名"命令 20061220 zz
     */
    public void processRenameCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RENAME);
        work.start();
    }

    /**
     * 处理"查看"命令
     * @see WorkThread
     */
    public void processViewCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    /**
     * 处理“修改配置规则”命令
     * @see WorkThread
     */
    public void processConfigRuleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CONFIG);
        work.start();
    }

    /**
     * 处理"搜索"命令
     * @see WorkThread
     */
    public void processBrowseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BROWSE);
        work.start();
    }

    /**
     * 处理"编辑路线"命令
     * @see WorkThread
     */
    public void processEditCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EDIT);
        work.start();
    }

    /**
     * 处理按路线单位查看零部件及路线
     * @see WorkThread
     */
    public void processSearchByCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCHBY);
        work.start();
    }

    /**
     * 处理综合路线查看
     * @see WorkThread
     */
    public void processCompositiveRouteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COMPOSITIVEROUTE);
        work.start();
    }

    /**
     * 处理"生成报表"命令
     * @see WorkThread
     */
    public void processReportCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REPORT);
        work.start();
    }

    //begin CR6
    /**
     * 处理"生成报表"命令
     * @see WorkThread
     */
    public void processReleaseRouteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RELEASEROUTE);
        work.start();
    }

    //end CR6
    /**
     * 处理"查看典型路线"命令
     * @see WorkThread
     */
    public void processViewModelRouteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEWMODELROUTE);
        work.start();
    }

    /**
     * 处理"检入"命令
     * @see WorkThread
     */
    public void processCheckInCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKIN);
        work.start();
    }

    /**
     * 处理"检出"命令
     * @see WorkThread
     */
    public void processCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKOUT);
        work.start();
    }

    /**
     * 处理"撤消检出"命令
     * @see WorkThread
     */
    public void processUndoCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UNDOCHECKOUT);
        work.start();
    }

    /**
     * 处理"查看版本历史"命令
     * @see WorkThread
     */
    public void processViewVersionCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VERSIONHIS);
        work.start();
    }

    /**
     * 处理"查看版序历史"命令
     * @see WorkThread
     */
    public void processViewIteratorCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITERATORHIS);
        work.start();
    }

    /**
     * 处理"重新指定生命周期状态"命令
     * @see WorkThread
     */
    public void processResetStateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RESETSTATE);
        work.start();
    }

    /**
     * 处理"查看生命周期历史"命令
     * @see WorkThread
     */
    public void processViewLifeCycleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), LIFECYCLEHIS);
        work.start();
    }

    /**
     * 处理"重新指定生命周期"命令
     * @see WorkThread
     */
    public void processResetLifeCycleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RESETLIFECYCLE);
        work.start();
    }

    /**
     * 处理"重新指定项目组"命令
     * @see WorkThread
     */
    public void processResetProjectCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RESETPROJECT);
        work.start();
    }

    /**
     * 处理"关于路线表管理"命令
     * @see WorkThread
     */
    public void processHelpManageCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), HELPMANAGE);
        work.start();
    }

    /**
     * 处理"关于"命令
     * @see WorkThread
     */
    public void processHelpAboutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * 处理"帮助"命令
     * @see WorkThread
     */
    public void processHelp1Command()
    {
        WorkThread work = new WorkThread(getThreadGroup(), HELP);
        work.start();
    }
//CCBegin SS1
    /**
     * 处理“创建供应商”
     */
    public void processCreateSuppler(){
    	 WorkThread work = new WorkThread(getThreadGroup(), CREATESUPPLIER);
         work.start();
    }
    /**
     * 处理“搜索供应商”
     */
    public void processSearchSuppler(){
    	WorkThread work = new WorkThread(getThreadGroup(), SEARCHSUPPLIER);
        work.start();
    }
    /**
     * 处理“搜索零部件”
     */
    public void processSearchPart(){
    	WorkThread work = new WorkThread(getThreadGroup(), SEARCHPART);
        work.start();
    }
 //CCEnd SS1
    /**
     * <p> Title:工作线程 </p> <p> Description:这是一个定义的内部的工作线程类 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
     * @see QMTread
     * @author 刘明
     * @version 1.0
     */

    class WorkThread extends QMThread
    {
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
        public WorkThread(ThreadGroup threadgroup, int action)
        {
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
        public WorkThread(ThreadGroup threadgroup, int action, TechnicsRouteListInfo list)
        {
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
        public WorkThread(ThreadGroup threadgroup, int action, TechnicsRouteListMasterInfo master)
        {
            super();
            CappRouteListManageController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myRouteListMaster = master;
        }

        /**
         * WorkThread的运行方法
         */
        public void run()
        {
            try
            {
                switch(myAction)
                {
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
                case CHECKIN:
                    checkInRouteList();
                    break;
                case CHECKOUT:
                    checkOutRouteList();
                    break;
                case UNDOCHECKOUT:
                    undoCheckOut();
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
                    break;
                //CR5 begin
                case REFRESHROUTELIST:
                    refreshRouteListInfo(myRouteList);
                    break;
                case ADDROUTELIST:
                    addRouteListInfo(myRouteList);
                    break;
                case DELROUTELIST:
                    delRouteListInfo(myRouteList);
                    break;
                //CR5 end
                //begin CR6
                case RELEASEROUTE:
                    releaseRoute();
                    break;
                //end CR6
                case VIEWMODELROUTE:
                    viewModelRoute();
                    break;
                 //CCBegin SS1
                case CREATESUPPLIER:
                	createSupplier();
                	break;
                case SEARCHSUPPLIER:
                	searchSupplier();
                	break;
                case SEARCHPART:
                	searchPart();
                	break;
                //CCend SS1
                }
            }catch(Exception e)
            {

                String message = e.getMessage();
                DialogFactory.showWarningDialog(view, message);
            }finally
            {
                view.setCursor(Cursor.getDefaultCursor());
                QMCt.setContextGroup(null);
            }
        }
    }

    /**
     * 新建路线表
     * @roseuid 403163BE0216
     */
    private void createRouteList()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //20120208 xucy add
        view.getTaskPanel().getPartLinkJPanel().setMultilistInforms();
        view.getTaskPanel().setViewMode(RouteListTaskJPanel.CREATE_MODE);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 更新路线表
     * @throws QMException 
     * @throws QMRemoteException
     * @roseuid 403163DA002C
     */
    private void updateRouteList() throws QMException
    {
        TechnicsRouteListInfo routelist = getSelectedObject();
        if(routelist instanceof WorkableIfc)
        {
            //如果是原本,则提示不许修改

            if(((WorkableIfc)routelist).getWorkableState().equals("c/o"))
            {
                if(verbose)
                {
                    System.out.println("CappRouteListManageController:508:更新路线表的操作：如果是原本的分支");
                }

                String whocheckoutthelist = routelist.getLocker();
                Class[] paraclass = {String.class};
                Object[] paraobj = {whocheckoutthelist};
                try
                {
                    UserInfo info = (UserInfo)RequestHelper.request("PersistService", "refreshInfo", paraclass, paraobj);

                    Object[] objIdentity = {getIdentity(routelist), info.getUsersName()};
                    String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);

                    String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.CANNOT_MODIFY_ORIGINAL_OBJECT, objIdentity);

                    // （问题一）20061107 zz  提醒更明确些 start
                    JOptionPane.showMessageDialog(view, message, title, JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception ex)
                {
                    String message = ex.getMessage();
                    DialogFactory.showInformDialog(view, message);
                }
                // （问题一）20061107 zz  提醒更明确些 end
                //  JOptionPane.showMessageDialog(view,"工艺路线表"+routelist.getRouteListName()+"已被用户"+whocheckoutthelist+"检出");
                return;
            }
            //如果在公共资料夹,则检出
            else if(CheckInOutCappTaskLogic.isInVault((WorkableIfc)routelist))
            {
                if(verbose)
                {
                    System.out.println("CappRouteListManageController:526:更新路线表的操作：如果在公共资料夹中的分支");
                }

                String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                Object[] objIdentity = {getIdentity(routelist)};
                String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.PLEASE_CONFIRM_CHECK_OUT, objIdentity);
                int result = JOptionPane.showConfirmDialog(view, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                switch(result)
                {
                case JOptionPane.YES_OPTION:
                {
                    //routelist =
                    // (TechnicsRouteListInfo)this.checkOutRouteList();
                    if(checkOutRouteList()) //实质是判断是否检出成功了
                    {
                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RouteListTaskJPanel p = view.getTaskPanel();
                        p.setTechnicsRouteList(this.getSelectedObject());
                        p.setViewMode(RouteListTaskJPanel.UPDATE_MODE);
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }
                }
                }
            }else
            //在个人资料夹
            {
                if(verbose)
                {
                    System.out.println("CappRouteListManageController:559:更新路线表的操作：如果在个人资料夹中的分支");
                }

                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RouteListTaskJPanel p = view.getTaskPanel();
                p.setTechnicsRouteList(this.getSelectedObject());
                p.setViewMode(RouteListTaskJPanel.UPDATE_MODE);
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    //CR3 begin
    private void renameRouteList() throws QMException
    {
        TechnicsRouteListIfc routelist = getSelectedObject();
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(routelist != null)
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(routelist instanceof TechnicsRouteListInfo)
            {
                TechnicsRouteListMasterInfo routeListMaster = (TechnicsRouteListMasterInfo)routelist.getMaster();
                //CCBegin SS2
                //RenameJDialog dia = new RenameJDialog(view, routeListMaster, 30, 30);
                RenameJDialog dia = new RenameJDialog(view, routeListMaster, 30, 50);
                //CCEnd SS2
                routeListMaster = (TechnicsRouteListMasterInfo)dia.showDialog();
                if(routeListMaster != null)
                {
                    Vector nodesVec = view.getTreePanel().findNodesforRename(routelist.getRouteListNumber());
                    String numberNew = routeListMaster.getRouteListNumber();
                    String nameNew = routeListMaster.getRouteListName();
                    if(nodesVec != null)
                    {
                        for(int i = 0;i < nodesVec.size();i++)
                        {
                            RouteTreeNode node = (RouteTreeNode)nodesVec.elementAt(i);
                            RouteListTreeObject treelistobjict = (RouteListTreeObject)node.getObject();
                            TechnicsRouteListIfc list = (TechnicsRouteListIfc)treelistobjict.getObject();
                            list.setMaster(routeListMaster);
                            //CR8 begin
//                            list.setRouteListNumber(numberNew);
//                            list.setRouteListName(nameNew);
                          //CR8 end
                            treelistobjict.setObject(list);
                            node.setObject(treelistobjict);
                            view.getTreePanel().nodeChanged(node);
                        }
                    }
                    RouteListTreeObject newObj = new RouteListTreeObject(routelist);
                    view.getTreePanel().updateNode(newObj);
                    refresh();
                }
            }
            view.setCursor(Cursor.getDefaultCursor());
        }

    }

    //CR3 end

    /**
     * 查看路线表(廋客户)
     * @throws QMException 
     * @throws QMRemoteException
     * @roseuid 403163E60123
     */
    private void viewRouteList() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TechnicsRouteListIfc info = this.getSelectedObject();
        String bsoID = info.getBsoID();
        String masterID = ((TechnicsRouteListMasterIfc)info.getMaster()).getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //将bsoID传到“查看路线表”页面
        RichToThinUtil.toWebPage("consroute_look_routeList.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());
    }
//CCBegin SS1
    /**
     * 创建供应商
     */
    private void createSupplier()throws QMException
    {
    	view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	RichToThinUtil.toWebPage("supCreate.screen");
    	view.setCursor(Cursor.getDefaultCursor());
    }
    /**
     * 搜索供应商
     * @throws QMException
     */
    private void searchSupplier()throws QMException
    {
    	view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	RichToThinUtil.toWebPage("Search-001.screen?selectobject=Supplier&scheme=");
    	view.setCursor(Cursor.getDefaultCursor());
    }
    /**
     * 搜索零部件
     * @throws QMException
     */
    private void searchPart()throws QMException
    {
    	view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	RichToThinUtil.toWebPage("supPartSearch.screen");
    	view.setCursor(Cursor.getDefaultCursor());
    }
    
//CCEnd SS1
    /**
     * 修改配置规则
     */
    private void makeConfigRule()
    {
        new ConfigSpecController(configSpecItem, SelectPartJDialog.getPartTreePanel(), this.view);

    }

    /**
     * 查看路线表(肥客户) 当在路线表管理器上选中一个路线表时，将调用此方法来显示零部件信息。
     * @roseuid 403163E60123
     */
    public void viewDefaultRouteList()
    {
        try
        {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RouteListTaskJPanel p = view.getTaskPanel();
            //工艺路线表对象
            p.setTechnicsRouteList(getSelectedObject());
            //查看界面模式
            p.setViewMode(RouteListTaskJPanel.VIEW_MODE);
            view.setCursor(Cursor.getDefaultCursor());

        }catch(Exception ex)
        {
            String message =ex.getMessage();
            DialogFactory.showInformDialog(view, message);
        }
    }

    //CR5 begin
    /**
     * 删除路线表
     * @throws QMException 
     * @throws QMRemoteException
     * @roseuid 40316425019C
     */
    private void deleteRouteList() throws QMException
    {
        boolean flag = false;
        RouteListTreeObject[] treeObj = this.getSelectedTreeObjects();
        TechnicsRouteListInfo info;
        for(int i = 0;i < treeObj.length;i++)
        {
            info = (TechnicsRouteListInfo)treeObj[i].getObject();

            //modify by guoxl on 20080219(刷新路线表节点)
            Class[] paraclass = {String.class};
            Object[] paraobj = {info.getBsoID()};
            info = (TechnicsRouteListInfo)RequestHelper.request("PersistService", "refreshInfo", paraclass, paraobj);
            //modify by guoxl end
            //如果允许删除，显示是否删除选择框
            flag = isDeleteAllowed(info);
        }
        if(flag)
        {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.CONFIRM_DELETE_OBJECT, null);
            int result = JOptionPane.showConfirmDialog(view, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            TechnicsRouteListInfo original = null;

            switch(result)
            {
            case JOptionPane.YES_OPTION:
            {
                for(int j = 0;j < treeObj.length;j++)
                {
                    info = (TechnicsRouteListInfo)treeObj[j].getObject();

                    //如果选择对象为工作副本，获得原本
                    if(WorkInProgressUtil.isWorkingCopy((WorkableIfc)info))
                    {
                        original = (TechnicsRouteListInfo)CheckInOutCappTaskLogic.getOriginalCopy((WorkableIfc)info);
                    }
                    // Begin CR1
                    view.getTaskPanel().setVisible(false);
                    view.setCursor(Cursor.getDefaultCursor());
                    deletedRouteListVec.add(info.getBsoID());
                    view.getTreePanel().removeNode(treeObj[j]);
                    java.net.URL url = CappRouteListManageJFrame.class.getResource("/images/routeList_delete2.gif");
                    treeObj[j].setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
                    treeObj[j].setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
                    treeObj[j].setNoteText("(此路线表正在被删除...)");
                    view.getTreePanel().addNode(treeObj[j]);
                    // End CR1
                    //刷新并删除info，同时刷新管理器界面
                    //info = (TechnicsRouteListInfo)CappClientHelper.refresh(info);
                    //调用Capp服务，删除指定的路线表
                    Class[] paraClass = {TechnicsRouteListIfc.class};
                    Object[] obj = {info};
                    RequestHelper.request("consTechnicsRouteService", "deleteRouteList", paraClass, obj);
                    //如果原本不为空，刷新原本为检入状态
                    if(original != null)
                    {
                        original.setWorkableState("c/i");
                        view.getTreePanel().updateNode(new RouteListTreeObject(original));
                    }
                    view.getTreePanel().removeNode(treeObj[j]);
                    //  view.getTaskPanel().setVisible(false);// End CR1
                }
                //end case
                // view.setCursor(Cursor.getDefaultCursor());// End CR1
            }
            }
        }
    }

    //CR5 end
    /**
     * 获得删除时缓存的路线表BSOID
     * @return Vector 存放路线表BSOID CR1
     */
    public Vector getDeleteRouteLisVec()
    {

        return this.deletedRouteListVec;

    }

    /**
     * 判断是否允许删除指定的路线表。 <p> 检查是否有其它用户更新该路线表 </p>
     * @param info TechnicsRouteListInfo 要删除的工艺卡(工序或工步)
     * @return 如果允许删除，则返回true
     */
    private boolean isDeleteAllowed(TechnicsRouteListInfo info)
    {
        //如果是原本，返回flase
        if(!WorkInProgressUtil.isWorkingCopy((WorkableIfc)info) && WorkInProgressUtil.isCheckedOut((WorkableIfc)info))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            // Object[] obj = { getIdentity(info) };
            String whocheckoutthelist = info.getLocker();
            Class[] paraclass = {String.class};
            Object[] paraobj = {whocheckoutthelist};
            UserInfo useinfo = null;
            try
            {
                useinfo = (UserInfo)RequestHelper.request("PersistService", "refreshInfo", paraclass, paraobj);
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }

            Object[] objIdentity = {getIdentity(info), useinfo.getUsersName()};

            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.CANNOT_MODIFY_ORIGINAL_OBJECT, objIdentity);

            JOptionPane.showMessageDialog(view, message, title, JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else
        {
            return true;
        }
    }

    /**
     * 搜索路线表（肥客户）
     * @roseuid 4031646C0271
     */
    private void searchRouteListFat()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RouteListSearchJDialog d = new RouteListSearchJDialog(view);
        d.setVisible(true);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 搜索路线表（廋客户）
     * @roseuid 403164BB03DD
     */
    private void searchRouteListThin()
    {

    }

    /**
     * 生成报表
     * @throws QMRemoteException
     * @roseuid 403164F103E4
     */
    private void reportForms()
    {

        CompositiveShowPartsJDialog compositiveDialog = new CompositiveShowPartsJDialog(view);
        compositiveDialog.setVisible(true);
    }

    //begin CR6
    /**
     * 发布路线
     * @throws QMException 
     */
    private void releaseRoute() throws QMException
    {
        TechnicsRouteListInfo routeList = getSelectedObject();
        if(routeList != null)
        {
            Object[] obj = {routeList.getIdentity()};
            if(routeList.getOwner() != null)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE, "56", obj);
                DialogFactory.showInformDialog(view, message);
                return;
            }
            Class[] c = {TechnicsRouteListIfc.class};
            Object[] objs = {routeList};
            try
            {
                //CR7 begin
                routeList = (TechnicsRouteListInfo)RequestHelper.request("consTechnicsRouteService", "releaseListPartsRoute", c, objs);
                String message = QMMessage.getLocalizedMessage(RESOURCE, "57", obj);
                DialogFactory.showInformDialog(view, message);
                RouteListTreeObject treeObject = new RouteListTreeObject(routeList);
                view.getTreePanel().updateNode(treeObject);
                view.getTaskPanel().setVisible(false);
                this.viewDefaultRouteList();
                //CR7 end
            }catch(Exception ex)
            {
                String message = ex.getMessage();
                DialogFactory.showInformDialog(view, message);
            }
        }

    }

    //end CR6

    /**
     * 查看典型路线
     */
    private void viewModelRoute()
    {
        ViewModelRouteJDialog viewmodelroutedialog = new ViewModelRouteJDialog();
        viewmodelroutedialog.setVisible(true);
    }

    /**
     * 定义快捷路线 20120118 徐春英 add
     */
    private void editCappRoute()
    {
        HashMap map = RouteClientUtil.getShortCutRoute();
        //显示定义快捷路线界面
        ShortCutRouteDialog shortCutDialog = new ShortCutRouteDialog(view, map);
        shortCutDialog.setSize(500, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        shortCutDialog.setLocation((int)(screenSize.getWidth() - 500) / 2, (int)(screenSize.getHeight() - 400) / 2);
        shortCutDialog.setVisible(true);
    }

    /**
     *按路线单位搜索
     *@see SearchPartsDialog
     */
    public void searchBy()
    {
        SearchPartsDialog searchPartsDialog = new SearchPartsDialog(this.view);
        searchPartsDialog.setSize(800, 600);
        searchPartsDialog.setVisible(true);
    }

    /**
     * 显示综合路线界面
     * @see CompositiveRouteJFrame
     */
    public void compositiveRoute()
    {
        if(verbose)
        {
            System.out.println("CappRouteListManageController:938:compositiveRoute()");
        }
        CompositiveRouteJFrame ompositiveRouteJFrame = new CompositiveRouteJFrame(this.view);
        ompositiveRouteJFrame.setSize(700, 600);
        ompositiveRouteJFrame.setVisible(true);
    }

    /**
     * 刷新
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void refresh() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().refreshNode(this.getSelectedTreeObject());
        RouteListTreeObject obj = (RouteListTreeObject)view.getTreePanel().getSelectedObject();
        if(obj == null)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(view, "对象已经不存在", title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        view.getTaskPanel().setVisible(false);
        this.viewDefaultRouteList();
        view.refresh();
        view.setCursor(Cursor.getDefaultCursor());
    }

    //20120105 begin
    /**
     * 刷新树节点，批量检入时使用
     */
    private void refreshRouteListInfo(TechnicsRouteListInfo refreshroutelist)
    {
        RouteTreePanel treePanel = view.getTreePanel();
        RouteListTreeObject newtreeObj = new RouteListTreeObject(refreshroutelist);
        treePanel.updateNode(newtreeObj);
        treePanel.setNodeSelected(newtreeObj);
        this.viewDefaultRouteList();
        view.refresh();
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 添加节点到树上，批量检出和撤销检出时使用
     * @param addroutelist
     */
    private void addRouteListInfo(TechnicsRouteListInfo addroutelist)
    {
        RouteTreePanel treePanel = view.getTreePanel();
        RouteListTreeObject newtreeObj = new RouteListTreeObject(addroutelist);
        treePanel.addNode(newtreeObj);
        treePanel.setNodeSelected(newtreeObj);
    }

    /**
     * 删除树节点，撤销检出时使用
     * @param delroutelist
     */
    private void delRouteListInfo(TechnicsRouteListInfo delroutelist)
    {
        RouteTreePanel treePanel = view.getTreePanel();
        RouteListTreeObject newtreeObj = new RouteListTreeObject(delroutelist);
        view.getTreePanel().removeNode(newtreeObj);
        treePanel.setNodeSelected(newtreeObj);
    }

    //CR5 begin
    /**
     * 清除
     * @throws QMRemoteException
     */
    private void clearAllObjects()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().removeAllSelectedNodes();
        view.getTaskPanel().setVisible(false);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 退出
     */
    private void exitSystem()
    {
        if(view.isExitSystem)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.IS_EXIT_SYSTEM, null);
            int result = JOptionPane.showConfirmDialog(view, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch(result)
            {
            case JOptionPane.YES_OPTION:
            {
                System.exit(0);
            }
            }
        }else
        {
            view.dispose();
        }
    }

    //CR5 begin
    /**
     * 批量检入
     * @throws QMRemoteException
     */
//    private void checkInRouteList()
//    {
//        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        Vector checkin = new Vector();
//        Vector isInTreeVec = new Vector();
//        //RLCheckInController c = new RLCheckInController(view);
//        try
//        {
//        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//            BatchCheckInTask checkin_task = new BatchCheckInTask(this.getParentJFrame());
//            WorkableIfc workingCopy = null;
//            WorkableIfc originalCopy = null;
//            //            c.setCheckinItem((WorkableIfc)this.getSelectedObject());
//            //            c.checkin();
//            //begin CR2
//
//            // BaseValueInfo obj = getSelectedObject();
//            TechnicsRouteListInfo obj = (TechnicsRouteListInfo)getSelectedObject();
//            Class[] class2 = {TechnicsRouteListInfo.class};
//            Object[] param2 = {obj};
//            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb");
//            //判断零部件是否都有路线
//            boolean flag = (Boolean)RequestHelper.request("consTechnicsRouteService", "haveroutelist", class2, param2);
//            if(flag)
//            {
//            	System.out.println("cccccccccccccccccccccccccccccccc");
//                if(obj instanceof WorkableIfc)
//                {
//                    WorkableIfc workableIfc = (WorkableIfc)obj;
//                    BaseValueIfc baseIfc = (BaseValueIfc)workableIfc;
//                    Class[] class1 = {BaseValueIfc.class};
//                    Object[] param = {(BaseValueIfc)baseIfc};
//                    workableIfc = (WorkableIfc)RequestHelper.request("PersistService", "refreshInfo", class1, param);
//                    //                    if(workableIfc instanceof TechnicsRouteListInfo)
//                    //                    {
//                    boolean isWorkingCopy = WorkInProgressUtil.isWorkingCopy(workableIfc);
//                    if(isWorkingCopy)
//                    {
//                    	System.out.println("ddddddddddddddddddddddd");
//                        workingCopy = workableIfc;
//                        originalCopy = CheckInOutHelper.getOriginalCopy(workableIfc);
//                    }else
//                    {
//                    	System.out.println("eeeeeeeeeeeeeee");
//                        originalCopy = workableIfc;
//                        workingCopy = CheckInOutHelper.getWorkingCopy(workableIfc);
//                    }
//                    //TechnicsRouteListInfo obj1 = (TechnicsRouteListInfo)originalCopy;
//                    //isInTreeVec.add(new Boolean(isInTree(obj1)));
//                    //                    }else
//                    //                    {
//                    //                        isInTreeVec.add(new Boolean(false));
//                    //                    }
//                    // 设置检入的对象。
//                    checkin.add(workableIfc);
//                    checkin.add(originalCopy);
//                    checkin.add(workingCopy);
//                    System.out.println("fffffffffffffffffffffffffff");
//                    //                if(!CappClientUtil.isCheckinAllowed((WorkableIfc)obj[i],view))
//                    //                {
//                    //                    return;
//                    //                }
//                    //                System.out.println("obj99999999===" + obj);
//                    //                CheckInControl checkInTask = new CheckInControl(view, (WorkableIfc)obj[i]);
//                    //                checkInTask.setCheckInTask(new RouteCheckInTask());
//                    //                checkInTask.showAndCheckIn();
//                }
//            }
//            //checkin_task.setIsInTree(isInTreeVec);
//            checkin_task.setCheckinItem(checkin);
//            System.out.println("ggggggggggggggggggggggggggggggg");
//            checkin_task.checkIn();
//            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhh");
//            //end CR2
//        }catch(Exception ex)
//        {
//        	System.out.println("iiiiiiiiiiiiiiiiiiiii");
//        	ex.printStackTrace();
//        	System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjj");
////            String message = ex.getMessage();
//            String message = QMExceptionHandler.handle(ex);
//            DialogFactory.showInformDialog(view, message);
//        }
//        view.setCursor(Cursor.getDefaultCursor());
//    }

    //begin CR2
    //    class RouteCheckInTask implements CheckInTask
    //    {
    //        public void checkInAndDisplay(WorkableIfc checkInIfc, String foldLocation)
    //        {
    //            Class[] paraClass = {WorkableIfc.class, String.class};
    //            Object[] objs = {checkInIfc, foldLocation};
    //            TechnicsRouteListIfc techRoute = (TechnicsRouteListIfc)RequestHelper.request("TechnicsRouteService", "checkInTechRouteList", paraClass, objs);
    //            //            //删除旧节点对象
    //            //            view.removeNode((BaseValueInfo) checkInIfc);
    //            //            //更新节点
    //            //            view.addProcess((QMTechnicsInfo) techRoute);
    //            //            view.getProcessTreePanel().setNodeSelected(new RouteTreeObject((TechnicsRouteListIfc) techRoute));
    //            view.getTreePanel().removeNode(new RouteListTreeObject(checkInIfc));
    //            RouteListTreeObject treeObject = new RouteListTreeObject(techRoute);
    //            view.getTreePanel().addNode(treeObject);
    //            view.getTreePanel().setNodeSelected(treeObject);
    //        }
    //    }
    //CR5 end
    //    public boolean isCheckinAllowed(WorkableIfc workable)
    //    {
    //        if(verbose)
    //        {
    //            System.out.println("cappclients.capp.controller.CheckInCappController.isCheckinAllowed() begin...");
    //        }
    //        boolean flag = false;
    //        //对象并没有检出
    //        boolean flag1 = workable.getWorkableState().equals("c/i");
    //        boolean flag2 = CheckInOutCappTaskLogic.isInVault(workable);
    //        if(flag1 && flag2)
    //        {
    //            Object aobj[] = {getIdentity(workable)};
    //            //提示：您尚未检出当前对象
    //            String ss1 = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOU_CHECK_OUT_BY_YOU, aobj);
    //            showMessageDialog(ss1);
    //            return false;
    //
    //        }
    //        //对象还未检入
    //        else if(flag1 && !flag2)
    //        {
    //            return true;
    //        }
    //        //对象检出的情况
    //        else if(!flag1)
    //        {
    //
    //            UserIfc currentUser = (UserIfc)RequestHelper.request("SessionService", "getCurUserInfo", null, null);
    //            //是否被当前用户检出。如果是，则返回true; 否则，返回false。
    //            if(WorkInProgressHelper.isCheckedOut(workable, currentUser))
    //            {
    //                return true;
    //            }
    //            //如果不是当前用户检出的情况就判断操作者是不是管理员
    //            else
    //            {
    //                Class[] claPram2 = {UserIfc.class, WorkableIfc.class};
    //                Object[] obj2 = {currentUser, workable};
    //                //begin CR10
    //                Object[] result = (Object[])RequestHelper.request("StandardCappService", "currentUserIsAdministrator", claPram2, obj2);//end CR10
    //                // 是不是管理员
    //                Boolean flag3 = (Boolean)result[0];
    //                UserIfc checkoutUser = (UserIfc)result[1];
    //                if(flag3.booleanValue())
    //                {
    //                    Object aobj1[] = {getIdentity(workable), checkoutUser.getUsersName()};
    //                    String s1 = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.VERIFY_CHECKIN_NOT_OWNER, aobj1);
    //                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
    //                    int i = DialogFactory.showYesNoDialog(view, s1, ss1);
    //                    return(i == 0);
    //
    //                }else
    //                {
    //                    Object aobj1[] = {getIdentity(workable), checkoutUser.getUsersName()};
    //                    String s1 = QMMessage.getLocalizedMessage(RESOURCE, "27", aobj1);
    //                    showMessageDialog(s1);
    //                }
    //                return false;
    //            }
    //        }
    //
    //        if(verbose)
    //        {
    //            System.out.println("cappclients.capp.controller.CheckInCappController.isCheckinAllowed() end...return : " + flag);
    //        }
    //        return flag;
    //    }
    //
    //    /**
    //     * 警告窗口的构造
    //     */
    //    private void showMessageDialog(String s)
    //    {
    //
    //        DialogFactory.showInformDialog(view, s);
    //    }

    //end CR2
    //CR5 begin
    
    private void checkInRouteList() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RLCheckInController c = new RLCheckInController(view);
        try {
            c.setCheckinItem((WorkableIfc) this.getSelectedObject());
            c.checkin();
        } catch (QMException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        view.setCursor(Cursor.getDefaultCursor());
    }
    /**
     * 批量检出
     * @throws QMException 
     * @throws QMRemoteException
     */
    private boolean checkOutRouteList() throws QMException
    {
        if(verbose)
        {
            System.out.println("capproute.controller.CappRouteListManageController.checkOutRouteList() begin...");
        }
        String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
        TechnicsRouteListInfo[] obj = (TechnicsRouteListInfo[])getSelectedObjects();
        Object[] messageobj = new Object[1];//CR19
        StringBuffer messageStr = new StringBuffer();//CR19
        boolean flag = false;

        TechnicsRouteListIfc[] routelist = new TechnicsRouteListIfc[obj.length];
        // 将每个要检出的路线表刷新
        for(int i = 0;i < obj.length;i++)
        {
            TechnicsRouteListIfc workable = null;
            String objID = ((TechnicsRouteListInfo)obj[i]).getBsoID();
            Class[] theClass = {String.class};
            Object[] theObjs = {objID};
            workable = (TechnicsRouteListIfc)RequestHelper.request("PersistService", "refreshInfo", theClass, theObjs);
            routelist[i] = workable;

        }
        Class[] theClass = {TechnicsRouteListIfc[].class, boolean.class};
        Object[] theObjs = {routelist, false};
        // 调用WorkInProgressService中的checkOut方法执行检出操作
        Vector checkOuted = (Vector)RequestHelper.request("consTechnicsRouteService", "checkOutTechRouteList", theClass, theObjs);
        int vecSize = checkOuted.size();
        WorkingPair checkOutedObject = null;
        // 将检出后的信息添加到信息提示栏中
        for(int m = 0;m < vecSize;m++)
        {
            checkOutedObject = (WorkingPair)checkOuted.elementAt(m);
            // 判断是否操作成功
            flag = checkOutedObject.isOperateSuccess();
            if(flag)
            { // 获取原本
                WorkableIfc OriginalCopy = checkOutedObject.getOriginalCopy();
                // 获取副本
                WorkableIfc WorkingCopy = checkOutedObject.getWorkingCopy();
                messageStr.append(((TechnicsRouteListInfo)WorkingCopy).getIdentity() + "成功" + "\n");//CR19
                // 更新。
                dispatchRefreshEvent(OriginalCopy, 1);
                // 创建。
                dispatchRefreshEvent(WorkingCopy, 0);
            }else
            {
                Exception e = checkOutedObject.getException();
                e.printStackTrace();
                messageStr.append(((TechnicsRouteListInfo)obj[m]).getIdentity() + e.getMessage() + "\n");//CR19
            }
        }

        obj = null;
        view.setCursor(Cursor.getDefaultCursor());
        messageobj[0] = messageStr;//CR19
        DialogFactory.displayInformationMessage(this.getParentJFrame(), RESOURCE, "checkout*", messageobj);//CR19

        //        boolean successful = false;
        //        WorkableIfc workable = (WorkableIfc)this.getSelectedObject();
        //        try
        //        {
        //            //begin CR2
        //            UserIfc currentUser = (UserIfc)RequestHelper.request("SessionService", "getCurUserInfo", null, null);
        //            if(CheckInOutCappTaskLogic.isCheckedOutByOther(workable, currentUser))
        //            {
        //                Object[] objs = {getIdentity(workable)};
        //                String message1 = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.ALREADY_CHECKOUT_BY_OTHER, objs);
        //                DialogFactory.showInformDialog(view, message1);
        //                view.setCursor(Cursor.getDefaultCursor());
        //                return false;
        //            }
        //            CheckOutControl checkOutTask = new CheckOutControl(view, workable);
        //            checkOutTask.setCheckOutTask(new RouteCheckOutTask());
        //            checkOutTask.showAndCheckOut();
        //        }catch(Exception e)
        //        {
        //            String message = QMExceptionHandler.handle(e);
        //            DialogFactory.showWarningDialog(view, message);
        //        }

        if(verbose)
        {
            System.out.println("capproute.controller.CappRouteListManageController.checkOutRouteList() end...return void");
        }
        return flag;
    }

    //    class RouteCheckOutTask implements CheckOutTask
    //    {
    //        public void checkOutAndDisplay(WorkableIfc checkOutIfc)
    //        {
    //            if(!WorkInProgressUtil.isWorkingCopy((WorkableIfc)checkOutIfc) && WorkInProgressUtil.isCheckedOut((WorkableIfc)checkOutIfc))
    //            {
    //                checkOutIfc = (WorkableIfc)CheckInOutCappTaskLogic.getWorkingCopy((WorkableIfc)checkOutIfc);
    //            }
    //            Class[] paraclass = {TechnicsRouteListIfc.class};
    //            Object[] objs = {checkOutIfc};
    //            // 调用服务，执行检出工艺路线表操作
    //            WorkingPair workpair = (WorkingPair)RequestHelper.request("TechnicsRouteService", "checkOutTechRouteList", paraclass, objs);
    //            Exception e = workpair.getException();
    //            if(e != null)
    //            {
    //                String messages = QMExceptionHandler.handle(e);
    //                DialogFactory.showWarningDialog(view, messages);
    //                return;
    //            }
    //            RouteTreePanel treePanel = view.getTreePanel();
    //            //删除源本
    //            treePanel.removeNode(new RouteListTreeObject(checkOutIfc));
    //
    //            //创建副本节点
    //            RouteListTreeObject newtreeObj = new RouteListTreeObject(workpair.getWorkingCopy());
    //            treePanel.addNode(newtreeObj);
    //            treePanel.setNodeSelected(newtreeObj);
    //        }
    //    }

    //end CR2
    /**
     * 分发刷新信号。
     * @param i int
     * @param obj Object
     */
    private void dispatchRefreshEvent(Object obj, int i)
    {
        //事例化刷新事件
        RefreshEvent refreshevent = new RefreshEvent(this, i, obj);
        //调刷新服务分派刷新事件
        RefreshService.getRefreshService().dispatchRefresh(refreshevent);
    }

    /**
     * 撤消检出
     * @throws QMException 
     * @throws QMRemoteException
    
    private void undoCheckOut() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        StringBuffer messageStr = new StringBuffer();//CR19
        Object[] messageobj = new Object[1];//CR19
        Vector routelists = new Vector();
        TechnicsRouteListInfo[] obj = (TechnicsRouteListInfo[])getSelectedObjects();
        for(int i = 0;i < obj.length;i++)
        {
            if(obj[i] instanceof TechnicsRouteListInfo)
            {

                WorkableIfc workable = null;
                String objID = ((TechnicsRouteListInfo)obj[i]).getBsoID();
                Class[] theClass = {String.class};
                Object[] theObjs = {objID};
                workable = (WorkableIfc)RequestHelper.request("PersistService", "refreshInfo", theClass, theObjs);
                routelists.add(workable);
            }
        }
        if(routelists == null)
        {
            messageobj[0] = messageStr;
            DialogFactory.displayInformationMessage(this.getParentJFrame(), RESOURCE, "undocheckout*", messageobj);
            return;
        }
        WorkableIfc[] routelist = new WorkableIfc[routelists.size()];
        Iterator iterator = routelists.iterator();
        for(int i = 0;iterator.hasNext();i++)
        {
            routelist[i] = (WorkableIfc)iterator.next();
        }
        Class[] theClass = {WorkableIfc[].class, boolean.class};
        Object[] theObjs = {routelist, false};
        // 调用WorkInProgressService中的checkOut方法执行检出操作
        Vector undocheckOuted = (Vector)RequestHelper.request("WorkInProgressService", "undoCheckOut", theClass, theObjs);
        int vecSize = undocheckOuted.size();
        WorkingPair undocheckOutedObject = null;
        // 将检出后的信息添加到信息提示栏中
        for(int m = 0;m < vecSize;m++)
        {
            undocheckOutedObject = (WorkingPair)undocheckOuted.elementAt(m);
            //是否撤銷检出成功
            boolean flag = undocheckOutedObject.isOperateSuccess();
            if(flag)
            {
                // 获取副本
                WorkableIfc workableCopy = undocheckOutedObject.getWorkingCopy();
                // 获取原本
                WorkableIfc OriginalCopy = undocheckOutedObject.getOriginalCopy();
                //移除副本节点
                dispatchRefreshEvent(workableCopy, 2);
                //创建原本节点
                dispatchRefreshEvent(OriginalCopy, 0);
                messageStr.append(((TechnicsRouteListInfo)OriginalCopy).getIdentity() + "成功" + "\n");//CR19
            }else
            {
                if(obj[m] instanceof TechnicsRouteListInfo)
                {
                    //如果沒有撤銷检出成功，打出堆栈、将错误信息添加到显示栏中
                    Exception e = undocheckOutedObject.getException();
                    e.printStackTrace();
                    messageStr.append(((TechnicsRouteListInfo)obj[m]).getIdentity() + e.getMessage() + "\n");//CR19
                }
            }
        }

        obj = null;
        view.setCursor(Cursor.getDefaultCursor());
        messageobj[0] = messageStr;//CR19
        DialogFactory.displayInformationMessage(this.getParentJFrame(), RESOURCE, "undocheckout*", messageobj);//CR19

        //        BaseValueInfo obj = (BaseValueInfo)this.getSelectedObject();
        //        if(obj instanceof WorkableIfc)
        //        {
        //            //begin CR2
        //            //            RLUndoCheckOutController undo_checkout_task = new RLUndoCheckOutController(view, (WorkableIfc)obj);
        //            //            undo_checkout_task.undoCheckout();
        //            UndoCheckOutControl undoCheckOutTask = new UndoCheckOutControl(view, (WorkableIfc)obj);
        //            undoCheckOutTask.setUndoCheckOutTask(new RouteListUndoCheckOutTask());
        //            undoCheckOutTask.showAndUndoCheckOut();
        //            //end CR2
        //        }
        //        view.setCursor(Cursor.getDefaultCursor());
    }

    //begin CR2
    //    class RouteListUndoCheckOutTask implements UndoCheckOutTask
    //    {
    //        public void undoCheckOutAndDisplay(WorkableIfc undoCheckOutIfc)
    //        {
    //
    //            // 如果不允许检入，则向用户提示
    //            if(!CheckInOutCappTaskLogic.isCheckinAllowed(undoCheckOutIfc))
    //            {
    //                String user = ((UserIfc)CheckInOutCappTaskLogic.getCheckedOutBy(undoCheckOutIfc)).getUsersName();
    //                String s = "";
    //                if(user == null || user.equals(""))
    //                {
    //                    Object aobj[] = {getIdentity(undoCheckOutIfc)};
    //                    s = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOU_CHECK_OUT_BY_YOU, aobj);
    //                }else
    //                {
    //                    Object aobj[] = {getIdentity(undoCheckOutIfc), CheckInOutCappTaskLogic.getCheckedOutBy(undoCheckOutIfc)};
    //                    s = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_CHECKOUT_OWNER_DISPLAY, aobj);
    //
    //                }
    //                //showMessageDialog(s);
    //                DialogFactory.showInformDialog(view, s);
    //            }
    //            // 允许检入的情况下
    //            else
    //            {
    //                // 获得操作对象的工作副本
    //                WorkableIfc workable = CheckInOutCappTaskLogic.getWorkingCopy(undoCheckOutIfc);
    //                WorkableIfc original = CheckInOutCappTaskLogic.getOriginalCopy(workable);
    //                // 调用服务，删除指定的工艺路线表
    //                Class[] paraClass = {TechnicsRouteListIfc.class};
    //                Object[] obj = {workable};
    //                // 执行撤消检出的动作
    //                RequestHelper.request("TechnicsRouteService", "deleteRouteList", paraClass, obj);
    //                // 刷新原本
    //                BaseValueInfo info = CappClientUtil.refresh((BaseValueInfo)original);
    //                view.getTreePanel().removeNode(new RouteListTreeObject(workable));
    //                //((CappRouteListManageJFrame)parentFrame).getTreePanel().removeNode(new RouteListTreeObject(workable));
    //                RouteListTreeObject newobj = new RouteListTreeObject(info);
    //                view.getTreePanel().addNode(newobj);
    //                view.getTreePanel().setNodeSelected(newobj);
    //            }
    //        }
    //    }

    //end CR2
    //CR5 end

    //end CR2
 */
    
    private void undoCheckOut() throws QMException
    {

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
     * 查看版本历史
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void viewVersionHistory() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo)this.getSelectedObject();

        if(obj instanceof WorkableIfc)
        {
            WorkableIfc workable = (WorkableIfc)obj;
            String objID = workable.getBsoID();

            HashMap hashmap = new HashMap();
            //modify by guoxl on 2008.03.31(进入瘦客户时传入的bsoID错误)
            hashmap.put("bsoID", objID);
            //modify by guoxl end
            //转到"版本历史"页面
            RichToThinUtil.toWebPage("consroute_version_viewVersionHistory.screen", hashmap);

        }

        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 查看版序历史
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void viewIteratorHistory() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo)this.getSelectedObject();

        if(obj instanceof WorkableIfc)
        {
            WorkableIfc workable = (WorkableIfc)obj;
            String objID = null;
            HashMap hashmap = new HashMap();
            if(workable instanceof IteratedIfc)
            {

                /*
                 * modify by guoxl on 20080304(为了满足查看版序历史时只显示当前版本及它的前驱的功能，因此给route_version_viewIterationsHistory.jsp传入TechnicsRouteList的BsoID)
                 */
                // MasteredIfc master = ( (IteratedIfc) workable).getMaster();
                // objID = master.getBsoID();

                objID = ((IteratedIfc)workable).getBsoID();

                //modify end
                //modify by guoxl on 2008.03.31(进入瘦客户时传入的bsoID错误)
                hashmap.put("bsoID", objID);
                //modify by guoxl end
                //转到"版序历史"页面
                RichToThinUtil.toWebPage("consroute_version_viewIterationsHistory.screen", hashmap);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 关于路线表管理
     */
    private void helpManage()
    {

    }

    /**
     * 关于
     */
    private void helpAbout()
    {
        //CappRouteListManageJFrame_AboutBox dlg = new CappRouteListManageJFrame_AboutBox(view);
        IntroduceDialog dlg = new IntroduceDialog(view, "工艺路线管理器");
        dlg.setVisible();
    }

    /**
     * 帮助
     */
    private void help()
    {
        QMHelpSystem helpSystem = null;
        if(helpSystem == null)
        {
            try
            {
                helpSystem = new QMHelpSystem("Summary", null, "OnlineHelp", ResourceBundle.getBundle("com.faw_qm.cappclients.conscapproute.util.CappRouteRB", RemoteProperty.getVersionLocale()));
            }catch(Exception exception)
            {
                (new MessageDialog(this.view, true, exception.getLocalizedMessage())).setVisible(true);
            }
        }
        helpSystem.showHelp("QMSummary");
    }

    /**
     * 重新指定生命周期状态
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void resetLifeCycleState() throws QMException
    {
        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycleStateate() begin...");

        }
        TechnicsRouteListInfo info = this.getSelectedObject();
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SetLifeCycleStateDialog dialog = null;
        try
        {
            dialog = new SetLifeCycleStateDialog(view, info);
            dialog.setModal(true);
            dialog.setVisible(true);
            if(!dialog.isShowing() && dialog.getObject() != null)
            {
                Class[] paraclass = {BaseValueIfc.class, Boolean.TYPE};
                Object[] paraobj = {(TechnicsRouteListInfo)dialog.getObject(), new Boolean(false)};
                info = (TechnicsRouteListInfo)RequestHelper.request("PersistService", "updateValueInfo", paraclass, paraobj);
                RouteListTreeObject treeObject = new RouteListTreeObject(info);
                view.getTreePanel().updateNode(treeObject);
                view.getTaskPanel().setVisible(false);
                this.viewDefaultRouteList();
            }
        }catch(QMException ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(view, message);
        }
        view.setCursor(Cursor.getDefaultCursor());

        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycleStateate() end...return is void");
        }
    }

    /**
     * 查看生命周期历史
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void viewLifeCycleHistory() throws QMException
    {
        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.viewLifeCycleHistory() begin...");

        }
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String bsoID = this.getSelectedObject().getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //将technicsBsoID传到“查看生命周期历史记录”页面
        RichToThinUtil.toWebPage("consroute_look_lifeCyleHistory.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());

        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.viewLifeCycleHistory() end...return is void");
        }
    }

    /**
     * 重新指定生命周期
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void resetLifeCycle() throws QMException
    {
        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycle() begin...");

        }
        TechnicsRouteListInfo obj = this.getSelectedObject();
        String title = QMMessage.getLocalizedMessage(RESOURCE, "afreshAssignLifeCycle", null);
        LifeCycleStateDialog a = new LifeCycleStateDialog(view, title, true);
        String str = getIdentity(obj);
        a.setName(str);
        a.setLifeCycleManaged((LifeCycleManagedIfc)obj);
        a.setVisible(true);
        if(!a.isShowing() && a.getObject() != null)
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Class[] paraclass = {BaseValueIfc.class, Boolean.TYPE};
            Object[] paraobj = {(TechnicsRouteListInfo)a.getObject(), new Boolean(false)};
            try
            {
                obj = (TechnicsRouteListInfo)RequestHelper.request("PersistService", "updateValueInfo", paraclass, paraobj);
            }catch(Exception ex)
            {
                String message = ex.getMessage();
                DialogFactory.showInformDialog(view, message);
            }
            RouteListTreeObject treeObject = new RouteListTreeObject(obj);
            view.getTreePanel().updateNode(treeObject);
            view.getTaskPanel().setVisible(false);
            this.viewDefaultRouteList();
        }else
        {
            view.getTreePanel().updateNode(new RouteListTreeObject(obj));
        }

        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.resetLifeCycle() end...return is void");
        }
    }

    /**
     * 重新指定项目组
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void resetProject() throws QMException
    {
        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.resetProject() begin...");

        }
        TechnicsRouteListInfo obj = this.getSelectedObject();
        String title = QMMessage.getLocalizedMessage(RESOURCE, "afreshAssignProject", null);
        ProjectStateDialog a = new ProjectStateDialog(view, title, true);
        a.setSize(430, 120);
        String str = getIdentity(obj);
        a.setName(str);
        a.setLifeCycleManaged((LifeCycleManagedIfc)obj);
        a.setVisible(true);
        if(!a.isVisible() && a.getObject() != null)
        {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Class[] paraclass = {BaseValueIfc.class, Boolean.TYPE};
            Object[] paraobj = {(TechnicsRouteListInfo)a.getObject(), new Boolean(false)};
            //调用服务传入获得的值对象paraobj
            obj = (TechnicsRouteListInfo)RequestHelper.request("PersistService", "updateValueInfo", paraclass, paraobj);
            //构造封装了指定的业务对象的路线表节点对象
            RouteListTreeObject treeObject = new RouteListTreeObject(obj);
            //显示路线被更新后的节点
            view.getTreePanel().updateNode(treeObject);
            //获得路线表维护面板
            view.getTaskPanel().setVisible(false);
            //当在路线表管理器上选中一个路线表时，将调用此方法来显示零部件信息。
            this.viewDefaultRouteList();
        }else
        {
            view.getTreePanel().updateNode(new RouteListTreeObject(obj));
        }

        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.resetProject() end...return is void");
        }
    }
}
