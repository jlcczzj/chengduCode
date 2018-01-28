/** 
 * 生成程序 RLUndoCheckOutController.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.controller;

import com.faw_qm.wip.model.*;
import javax.swing.*;
import com.faw_qm.cappclients.capp.util.*;
import java.util.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.framework.exceptions.*;
import com.faw_qm.cappclients.capp.controller.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.cappclients.conscapproute.view.*;
import com.faw_qm.util.QMCt;

/**
 * <p>
 * Title:撤销检出工艺路线表任务控制类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * 
 * @author 刘明
 * @version 1.0
 */
public class RLUndoCheckOutController extends CappRouteAction {
    /** 资源 */
    private static ResourceBundle resources = null;

    /** 父窗口 */
    private JFrame parentFrame;

    /** 撤销检出的对象 */
    private WorkableIfc undoItem;

    /** 当前线程 */
    private ThreadGroup contextGroup;

    /** 用户进行操作的时候是否有提示 */
    private boolean prompt;

    /**
     * 构造函数
     * 
     * @param frame
     *            调用本控制类的窗口
     * @param workable
     *            执行撤销检出操作的对象
     * @roseuid 403161E80026
     */
    public RLUndoCheckOutController(JFrame frame, WorkableIfc workable) {
        prompt = true;
        contextGroup = QMCt.getContext().getThreadGroup();
        parentFrame = frame;
        undoItem = workable;
    }

    /**
     * 设置调用这个任务的窗口类
     * 
     * @param frame
     */
    public void setParentJFrame(JFrame frame) {
        parentFrame = frame;
    }

    /**
     * 得到调用这个任务的窗口类
     * 
     * @return
     */
    public JFrame getParentJFrame() {
        JFrame frame = null;
        if (parentFrame != null)
            frame = parentFrame;
        return frame;
    }

    /**
     * 设置用户进行操作的时候是否有提示
     * 
     * @param flag
     */
    public void setPromptOnUndo(boolean flag) {
        prompt = flag;
    }

    /**
     * 设置操作的对象
     * 
     * @param workable
     * @roseuid 4031620F014E
     */
    public void setUndoCheckOutItem(WorkableIfc workable) {
        undoItem = workable;
    }

    /**
     * 获得操作的对象
     * 
     * @return WorkableIfc
     * @roseuid 4031622403C5
     */
    public WorkableIfc getUndoCheckOutItem() {
        return undoItem;
    }

    /**
     * 撤消检出操作
     * 
     * @return WorkableIfc: 要执行撤消检出操作的对象
     */
    public WorkableIfc undoCheckout() {
        if (verbose)
            System.out
                    .println("cappclients.capp.controller.UndoCheckOutController.undoCheckout() begin...");
        if (undoItem != null) {
            try {
                setContext(true);
                //如果不允许检入，则向用户提示
                if (!CheckInOutCappTaskLogic.isCheckinAllowed(undoItem)) {
                    if (resources == null)
                        initResources();
                    String user = CheckInOutCappTaskLogic.getCheckedOutBy(
                            undoItem, getContext().getLocale());
                    String s = "";
                    if (user != null && !user.equals("")) {
                        Object aobj[] = {
                                getIdentity(undoItem),
                                CheckInOutCappTaskLogic.getCheckedOutBy(
                                        undoItem, getContext().getLocale()) };
                        s = QMMessage.getLocalizedMessage(RESOURCE,
                                CappRouteRB.NOT_CHECKOUT_OWNER_DISPLAY, aobj,
                                getContext().getLocale());
                    } else {
                        Object aobj[] = { getIdentity(undoItem) };
                        s = QMMessage.getLocalizedMessage(RESOURCE,
                                CappRouteRB.NOU_CHECK_OUT_BY_YOU, aobj,
                                getContext().getLocale());
                    }
                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    showMessageDialog(s, ss1);
                }
                //允许检入的情况下
                else {
                    boolean flag = true;
                    //如果不是由当前用户检出的，则提示操作对象已经被他人检出
                    if (!CheckInOutCappTaskLogic.isCheckedOutByUser(undoItem)) {//检出资料夹
                        Object aobj1[] = {
                                getIdentity(undoItem),
                                CheckInOutCappTaskLogic.getCheckedOutBy(
                                        undoItem, getContext().getLocale()) };

                        String s1 = QMMessage.getLocalizedMessage(RESOURCE,
                                CappRouteRB.VERIFY_UNDO_CHECKOUT_NOT_OWNER,
                                aobj1, null);
                        String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        int i = JOptionPane.showConfirmDialog(
                                getParentJFrame(), s1, ss1,
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        flag = (i == 0);
                    }
                    //是当前用户检出的情况下
                    else
                    //对象已经改变的情况下
                    if (prompt && CheckInOutCappTaskLogic.hasChanged(undoItem)) {
                        //如果对象是工作副本，则获得源本
                        if (CheckInOutCappTaskLogic.isWorkingCopy(undoItem)) {
                            undoItem = CheckInOutCappTaskLogic
                                    .getOriginalCopy(undoItem);
                        }

                        //请用户确认：您确实要撤消检出，并丢失所有变更吗？
                        Object aobj2[] = { getIdentity(undoItem) };
                        String s2 = QMMessage.getLocalizedMessage(RESOURCE,
                                CappRouteRB.VERIFY_UNDO_CHECKOUT, aobj2,
                                getContext().getLocale());
                        String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        int i = JOptionPane.showConfirmDialog(
                                getParentJFrame(), s2, ss1,
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        flag = (i == 0);
                    }
                    //执行撤消检出的动作
                    if (flag) {
                        //获得操作对象的工作副本
                        WorkableIfc workable = CheckInOutCappTaskLogic
                                .getWorkingCopy(undoItem);
                        //执行撤销检出
                        WorkableIfc workable1 = CheckInOutCappTaskLogic
                                .undoCheckout(undoItem);

                        //刷新原本
                        BaseValueInfo info = CappClientHelper
                                .refresh((BaseValueInfo) workable1);
                        //刷新操作对象
                        ((CappRouteListManageJFrame) parentFrame)
                                .getTreePanel().removeNode(
                                        new RouteListTreeObject(workable1));
                        ((CappRouteListManageJFrame) parentFrame)
                                .getTreePanel().removeNode(
                                        new RouteListTreeObject(workable));
                        RouteListTreeObject newobj = new RouteListTreeObject(
                                info);
                        ((CappRouteListManageJFrame) parentFrame)
                                .getTreePanel().addNode(newobj);
                        ((CappRouteListManageJFrame) parentFrame)
                                .getTreePanel().setNodeSelected(newobj);
                    }
                }
            } catch (MissingResourceException e) {
                e.printStackTrace();
            } catch (QMException e) {
                e.printStackTrace();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "exception", null);
                showMessageDialog(e.getClientMessage(), title);
            } finally {
                setContext(false);
            }
        }
        if (verbose)
            System.out
                    .println("cappclients.capp.controller.UndoCheckOutController.undoCheckout() end...return: "
                            + undoItem);
        return undoItem;
    }

    /**
     * 资源初始化
     */
    private void initResources() {
        try {
            resources = ResourceBundle.getBundle(RESOURCE, getContext()
                    .getLocale());
            return;
        } catch (MissingResourceException missingresourceexception) {
            String s = "";
            String title = "Exception";
            if (resources != null) {
                s = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.INIT_RESOURCE_ERROR, null);
                title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                        null);
            }
            showMessageDialog(s
                    + missingresourceexception.getLocalizedMessage(), title);

            return;
        }
    }

    /**
     * 得到上下文环境 获得当前线程
     * @return QMCt
     */
    private QMCt getContext() {
        QMCt qmcontext = null;
        if (contextGroup != null)
            qmcontext = QMCt.getContext(contextGroup);

        if (qmcontext == null)
            qmcontext = QMCt.getContext();
        return qmcontext;
    }

    /**
     * 设置上下文 设置线程
     * 
     * @param flag
     */
    private void setContext(boolean flag) {
        if (flag) {
            if (contextGroup != null) {
                QMCt.setContextGroup(contextGroup);
                return;
            }

            if (parentFrame != null) {
                QMCt.setContext(parentFrame);
                return;
            }
        } else {
            QMCt.setContext(null);
        }
    }

    /**
     * 用于显示异常信息
     * 
     * @param s
     */
    private void showMessageDialog(String message, String title) {
        JFrame frame = getParentJFrame();
        if (frame != null) {
            JOptionPane.showMessageDialog(frame, message, title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            System.err.println(message);
            return;
        }
    }

}