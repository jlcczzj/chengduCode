/**
 * 生成程序 CappRouteManageController.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.controller;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.cappclients.capproute.view.*;
import com.faw_qm.cappclients.capproute.util.*;
import com.faw_qm.clients.util.MessageDialog;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.help.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.util.QMCt;

/**
 * <p>
 * Title:工艺路线管理器主控制类
 * </p>
 * <p>
 * Description:在路线管理器中选择零件树中的一个接点时， 将调用这个控制类来显示路线的内容
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * @author 刘明
 * @version 1.0
 */
public class CappRouteManageController extends CappRouteAction {
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 工艺路线维护主界面 */
    private CappRouteManageJDialog view;

    /** 线程组 */
    private ThreadGroup threadGroup = null;

    /** 记录:执行复制的源路线 */
    public String originalRouteID;

    /** 记录:当前路线管理器所属的路线表 */
    private TechnicsRouteListIfc myRouteList;

    /** 执行路线复制的控制类 */
    private RouteCopyController copyController = new RouteCopyController();

    /** 执行路线"复制自"操作的界面对象 */
    private RouteCopyFromJDialog copyDialog;

    /** 执行"粘贴到"操作的界面对象 */
    private RoutePasteToJDialog pastetoDialog;

    /** 显示零部件表界面 */
    private RouteSelectPartJDialog viewPartsJDialog;

    /** 菜单操作标识 */
    private final static int CREATE = 1;

    private final static int UPDATE = 2;

    private final static int DELETE = 3;

    private final static int REFRESH = 4;

    private final static int CLEAR = 5;

    private final static int EXIT = 6;

    private final static int COPY = 7;

    private final static int COPYFROM = 8;

    private final static int PASTE = 9;

    private final static int PASTETO = 10;

    private final static int VIEW = 11;

    private final static int SELECT = 12;

    private final static int HELPMANAGE = 13;

    private final static int ABOUT = 14;
    
    //  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
    private final static int VIEWPARENT = 16;
    private final static int VIEWPART = 17;
     //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
    /**
     * 构造函数
     *
     * @param frame
     *            路线管理器
     * @roseuid 4031874E002C
     */
    public CappRouteManageController(CappRouteManageJDialog frame) {
        this.view = frame;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        myRouteList = view.getRouteList();
    }

    /**
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     *
     * @return 当前选择的对象
     * @throws QMRemoteException
     */
    public ListRoutePartLinkIfc getSelectedObject() throws QMRemoteException {
        RoutePartTreeObject obj = (RoutePartTreeObject) view.getTreePanel()
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
        return (ListRoutePartLinkIfc) obj.getObject();
    }

    /**
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     *
     * @return 当前选择的对象
     * @throws QMRemoteException
     */
    public RoutePartTreeObject getSelectedTreeObject() throws QMRemoteException {
        RoutePartTreeObject obj = (RoutePartTreeObject) view.getTreePanel()
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
     * 向工艺路线树上添加零部件节点
     * @param RoutePartTreeObject obj 工艺路线树中的零部件节点对象
     */
    public void addNode(RoutePartTreeObject obj) {
        view.getTreePanel().addNode(obj);
    }

    /**
     * Title: 工作线程
     * Description:内部工作线程类
     * Copyright: Copyright (c) 2004
     * Company: 一汽启明
     * @author 刘明
     * @version 1.0
     */
    class WKThread extends QMThread {
        int myAction;
        //Part值
        QMPartMasterInfo myPart;

        /**
         * 重载WKTread
         * @param threadgroup
         * @param action
         */
        public WKThread(ThreadGroup threadgroup, int action) {
            super();
            CappRouteManageController.this.threadGroup = threadgroup;
            this.myAction = action;
        }

        /**
         * 重载WKTread
         * @param threadgroup
         * @param action
         * @param list
         */
        public WKThread(ThreadGroup threadgroup, int action,
                QMPartMasterInfo list) {
            super();
            CappRouteManageController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myPart = list;
        }
        /**
         * WKTread运行方法
         */
        public void run() {
            try {
                switch (myAction) {
                case CREATE:
                    createRoute();
                    break;
                case UPDATE:
                    updateRoute();
                    break;
                case DELETE:
                    deleteRoute();
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

                case COPY:
                    copy();
                    break;
                case COPYFROM:
                    copyFrom();
                    break;
                case PASTE:
                    paste();
                    break;
                case PASTETO:
                    pasteTo();
                    break;
                case VIEW:
                    viewRoute();
                    break;
                    //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线    
                case VIEWPARENT:
                  viewRouteParent();
                  break;
                case VIEWPART:
                  viewPart();
                break;                    
                 //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线  
                case SELECT:
                    selectParts();
                    break;
                case HELPMANAGE:
                    helpManage();
                    break;
                case ABOUT:
                    helpAbout();

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
     * 处理"新建"命令
     * @see WKTread
     */
    public void processCreateCommand() {
        WKThread work = new WKThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * 处理"更新"命令
     * @see WKTread
     */
    public void processUpdateCommand() {
        WKThread work = new WKThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * 处理"删除"命令
     * @see WKTread
     */
    public void processDeleteCommand() {
        WKThread work = new WKThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * 处理"刷新"命令
     * @see WKTread
     */
    public void processRefreshCommand() {
        WKThread work = new WKThread(getThreadGroup(), REFRESH);
        work.start();
    }

    /**
     * 处理"清除"命令
     * @see WKTread
     */
    public void processClearCommand() {
        WKThread work = new WKThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * 处理"退出"命令
     * @see WKTread
     */
    public void processExitCommand() {
        WKThread work = new WKThread(getThreadGroup(), EXIT);
        work.start();
    }


    /**
     * 处理"查看"命令
     * @see WKTread
     */
    public void processViewCommand() {
        WKThread work = new WKThread(getThreadGroup(), VIEW);
        work.start();
    }
    
    //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
    public void processViewParentCommand() {
     WKThread work = new WKThread(getThreadGroup(), VIEWPARENT);
     work.start();
   }

   public void processViewPartCommand() {
     WKThread work = new WKThread(getThreadGroup(), VIEWPART);
     work.start();
   }
//   CCEndby leixiao 2008-7-31 原因：解放升级工艺路线

    /**
     * 处理"粘贴到"命令
     * @see WKTread
     */
    public void processPasteToCommand() {
        WKThread work = new WKThread(getThreadGroup(), PASTETO);
        work.start();
    }

    /**
     * 处理"复制"命令
     * @see WKTread
     */
    public void processCopyCommand() {
        WKThread work = new WKThread(getThreadGroup(), COPY);
        work.start();
    }

    /**
     * 处理"复制自"命令
     * @see WKTread
     */
    public void processCopyFromCommand() {
        WKThread work = new WKThread(getThreadGroup(), COPYFROM);
        work.start();
    }

    /**
     * 处理"粘贴"命令
     * @see WKTread
     */
    public void processPasteCommand() {
        WKThread work = new WKThread(getThreadGroup(), PASTE);
        work.start();
    }

    /**
     * 处理"选择"命令
     * @see WKTread
     */
    public void processSelectCommand() {
        WKThread work = new WKThread(getThreadGroup(), SELECT);
        work.start();
    }

    /**
     * 处理"关于工艺路线管理"命令
     * @see WKTread
     */
    public void processHelpManageCommand() {
        WKThread work = new WKThread(getThreadGroup(), HELPMANAGE);
        work.start();
    }

    /**
     * 处理"关于"命令
     * @see WKTread
     */
    public void processAboutCommand() {
        WKThread work = new WKThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * 新建路线
     * @throws QMRemoteException
     * @roseuid 4031875A019C
     */
    private void createRoute() throws QMRemoteException {
        ListRoutePartLinkIfc info = this.getSelectedObject();
        if (info.getRouteID() != null) {
            Object[] objs = { info.getPartMasterInfo().getPartNumber() };
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.CAN_NOT_COPY_ROUTE, objs, null));
        }
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RouteTaskJPanel task = view.getTaskPanel();
        task.setTechnicsRouteList(myRouteList);
        task.setListPartLink(info);
        task.setViewMode(RouteTaskJPanel.CREATE_MODE);
        view.setCursor(Cursor.getDefaultCursor());

    }

    /**
     * 更新路线
     * 此方法由CappRouteManageJDialog568行的更新菜单或工具所引发
     * 首先检测所欲更新的零件是否有路线，
     * 无，不显示更新截面，
     * 有，则显示 相应的更应的更新界面。
     *
     * @throws QMRemoteException
     * @roseuid 403187650062
     */
    private void updateRoute() throws QMRemoteException {
        if (verbose || true) {
            System.out.println("进入CappRouteManagerController，启动响应的更新界面");
        }
        ListRoutePartLinkIfc info = this.getSelectedObject();
        RouteTaskJPanel task = view.getTaskPanel();
        if (info.getRouteID() == null) {
            if (task.isShowing())
                task.setVisible(false);
            Object[] objs = { info.getPartMasterInfo().getPartNumber() };
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.PART_NOT_HAVE_ROUTE, objs, null));
        }
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task.setTechnicsRouteList(myRouteList);
        task.setListPartLink(info);
        task.setViewMode(RouteTaskJPanel.UPDATE_MODE);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 被CappRouteManageJDialog调用 查看路线(肥客户)
     *
     * @roseuid 403163E60123
     */
    public void viewDefaultRoute() {
        try {
            RouteTaskJPanel task = view.getTaskPanel();
            if (this.getSelectedObject().getRouteID() == null) {
                if (task.isShowing())
                    task.setVisible(false);
                return;
            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task.setTechnicsRouteList(myRouteList);
            task.setListPartLink(this.getSelectedObject());
            task.setViewMode(RouteTaskJPanel.VIEW_MODE);
            view.setCursor(Cursor.getDefaultCursor());
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 查看路线(WEB)
     * @throws QMRemoteException
     * @roseuid 4031876E03B8
     */
    private void viewRoute() throws QMRemoteException {
        ListRoutePartLinkIfc info = this.getSelectedObject();
        TechnicsRouteListIfc routListIfc=this.myRouteList;
        if (info.getRouteID() == null) {
            Object[] objs = { info.getPartMasterInfo().getPartNumber() };
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.PART_NOT_HAVE_ROUTE, objs, null));
        }
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        HashMap hashmap = new HashMap();
        hashmap.put("linkbsoID", info.getBsoID());
        //add by guoxl on 2008.03.20(扭转到瘦客户时没传bsoID)
        hashmap.put("bsoID",routListIfc.getBsoID());
        //add by guoxl end
        //将bsoID传到“查看路线”页面
        RichToThinUtil.toWebPage("route_view_route.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());
    }
    
    //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线 
    /**
 * B8
 */
private void viewRouteParent() throws QMRemoteException {
  ViewParentJDialog vpDialog = new ViewParentJDialog(view.getParentJFrame(),
      "", true);
  vpDialog.setSize(650, 500);
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  vpDialog.setLocation( (int) (screenSize.getWidth() - 650) / 2,
                       (int) (screenSize.getHeight() - 500) / 2);
  RoutePartTreeObject treeObj = this.getSelectedTreeObject();
  ListRoutePartLinkIfc info = (ListRoutePartLinkIfc) treeObj.getObject();
  vpDialog.setPartMaster(info.getPartMasterInfo(), myRouteList.getBsoID());
  vpDialog.setVisible(true);
}
//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线

    /**
     * 删除路线
     * @throws QMRemoteException
     * @roseuid 403187850004
     */
    private void deleteRoute() throws QMRemoteException {
        RoutePartTreeObject treeObj = this.getSelectedTreeObject();
        ListRoutePartLinkIfc info = (ListRoutePartLinkIfc) treeObj.getObject();
        if (info.getRouteID() == null) {
            Object[] objs = { info.getPartMasterInfo().getPartNumber() };
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.PART_NOT_HAVE_ROUTE, objs, null));
        }
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information",
                null);
        String message = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.CONFIRM_DELETE_OBJECT, null);
        int result = JOptionPane.showConfirmDialog(view, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (result) {
        case JOptionPane.YES_OPTION: {
            //调用服务，删除指定的路线
            Class[] paraClass = { ListRoutePartLinkIfc.class };
            Object[] obj = { info };
            useServiceMethod("TechnicsRouteService", "deleteRoute",
                    paraClass, obj);
            //将节点刷新为无路线状态
            view.getTreePanel().refreshNode(treeObj);
            view.getTaskPanel().setVisible(false);

        }
        }

        view.setCursor(Cursor.getDefaultCursor());

    }

    /**
     * 刷新
     * @throws QMRemoteException
     */
    private void refresh() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().refreshNode(this.getSelectedTreeObject());
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
        view.dispose();
    }

    /**
     * 重新选择零部件
     * @see RouteSelectPartJDialog
     * @roseuid 403187B2038E
     */
    private void selectParts() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        viewPartsJDialog = new RouteSelectPartJDialog(view);
        viewPartsJDialog.setTechnicsRouteList(myRouteList);
        viewPartsJDialog.setVisible(true);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 复制
     * @throws QMRemoteException
     * @roseuid 403187D200B9
     */
    private void copy() throws QMRemoteException {
        originalRouteID = this.getSelectedObject().getRouteID();
        copyController.setOriginalRoute(originalRouteID);
    }

    /**
     * 路线复制自
     * @throws QMRemoteException
     */
    private void copyFrom() throws QMRemoteException {
        copyDialog = new RouteCopyFromJDialog(view);
        copyDialog.setRouteList(myRouteList);
        copyDialog.setListPartLink(this.getSelectedObject());
        copyDialog.setVisible(true);
        if (!copyDialog.isShowing()) {
            String routeid = copyDialog.getSelectedRoute();
            if (routeid != null && !routeid.equals("")) {
                originalRouteID = routeid;
                copyController.setOriginalRoute(routeid);
            }
        }
    }

    /**
     * 粘贴
     * @throws QMRemoteException
     */
    private void paste() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ListRoutePartLinkIfc partlink = this.getSelectedObject();
        //检查目标零部件的工艺路线是否存在
        if (partlink.getRouteID() != null) {
            Object[] obj = { partlink.getPartMasterInfo().getPartNumber() };
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.CAN_NOT_COPY_ROUTE, obj, null));
        }
        copyController.setObjectPartLink(partlink);
        if (copyController.copy()) {
            RoutePartTreeObject newTreeObj = new RoutePartTreeObject(partlink);
            view.getTreePanel().refreshNode(newTreeObj);
            this.viewDefaultRoute();
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 粘贴到
     * @throws QMRemoteException
     */
    private void pasteTo() throws QMRemoteException {

        pastetoDialog = new RoutePasteToJDialog(view);
        pastetoDialog.setRouteList(myRouteList);
        pastetoDialog.setVisible(true);
        if (!pastetoDialog.isShowing() && pastetoDialog.isSave) {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Vector links = pastetoDialog.getSelectedParts();
            if (links != null && links.size() > 0) {
                for (int i = 0; i < links.size(); i++) {
                    ListRoutePartLinkIfc partlink = (ListRoutePartLinkIfc) links
                            .elementAt(i);
                    copyController.setObjectPartLink(partlink);
                    if (copyController.copy()) {
                        RoutePartTreeObject newTreeObj = new RoutePartTreeObject(
                                partlink);
                        view.getTreePanel().refreshNode(newTreeObj);
                    }
                }
            }
            this.viewDefaultRoute();
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }

    /**
     * 路线表管理 帮助
     * @see QMHelpSystem
     */
    private void helpManage() {
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
     * 关于
     * @see CappRouteManageJFrame_AboutBox
     */
    private void helpAbout() {
        CappRouteManageJFrame_AboutBox dlg = new CappRouteManageJFrame_AboutBox(
                view);
        dlg.setModal(true);
        dlg.setVisible(true);
    }
    //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线   
    private void viewPart() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ListRoutePartLinkIfc info = this.getSelectedObject();
        String partMasterID = info.getPartMasterID();
        QMPartIfc part = null;
        QMPartMasterIfc master = null;
        ServiceRequestInfo info2 = new ServiceRequestInfo();
        info2.setServiceName("PersistService");
        info2.setMethodName("refreshInfo");
        Class[] paramClass2 = {
            String.class};
        info2.setParaClasses(paramClass2);
        String[] value2 = {
            partMasterID};
        info2.setParaValues(value2);
        try {
          master = (QMPartMasterIfc) RequestServerFactory.getRequestServer().
              request(info2);
        }
        catch (QMRemoteException e) {
          view.setCursor(Cursor.getDefaultCursor());
          JOptionPane.showMessageDialog(view, e.getClientMessage());
        }

        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("TechnicsRouteService");
        info1.setMethodName("filteredIterationsOfByDefault");
        Class[] paramClass = {
            QMPartMasterIfc.class};
        info1.setParaClasses(paramClass);
        Object[] value = {
            master};
        info1.setParaValues(value);
        try {
          part = (QMPartIfc) RequestServerFactory.getRequestServer().
              request(info1);
        }
        catch (QMRemoteException e) {
          view.setCursor(Cursor.getDefaultCursor());
          JOptionPane.showMessageDialog(view, e.getClientMessage());
        }

        if (part == null) {
          JOptionPane.showMessageDialog(view, "未找到零部件最新小版本，无法继续操作！");
          view.setCursor(Cursor.getDefaultCursor());
          return;
        }
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", part.getBsoID());
//    将bsoID传到“查看路线”页面
        RichToThinUtil.toWebPage("Part-Other-PartLookOver-001.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());

      }
    //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线 
}
