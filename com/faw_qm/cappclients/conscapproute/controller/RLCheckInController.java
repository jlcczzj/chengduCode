/**
 * 生成程序 RLCheckInController.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.controller;

import java.util.*;
import javax.swing.*;

import com.faw_qm.cappclients.capp.controller.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.folder.model.*;
import com.faw_qm.cappclients.conscapproute.view.*;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.framework.exceptions.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.wip.model.*;
import com.faw_qm.util.QMCt;

/**
 * <p>
 * Title:检入工艺路线表任务控制类
 * </p>
 * <p>
 * Description:检入工艺路线表任务控制类
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
public class RLCheckInController extends CappRouteAction {
    /** 资源 */
    private static ResourceBundle resources = null;

    /** 父窗口 */
    private JFrame parentFrame = null;

    /** 检入项 */
    private WorkableIfc checkinItem;

    /**
     * 是否首次检入 当是首次检入的时候，让用户选择 资料夹
     */
    private boolean firstCheckIn;

    /** 当前线程 */
    private ThreadGroup contextGroup;

    /** 检入路径（资料夹） */
    private FolderIfc checkInFolder;

    /**
     * 构造函数
     *
     * @param frame
     *            父窗口
     * @roseuid 40315DF803C0
     */
    public RLCheckInController(JFrame frame) {
        firstCheckIn = false;
        contextGroup = QMCt.getContext().getThreadGroup();
        parentFrame = frame;
    }

    /**
     * 设置父界面
     *
     * @param frame
     *            父界面
     */
    public void setParentFrame(JFrame frame) {
        parentFrame = frame;
    }

    /**
     * 获得父窗口
     *
     * @return javax.swing.JFrame
     * @roseuid 403160650246
     */
    public JFrame getParentJFrame() {
        return parentFrame;
    }

    /**
     * 设置检入的文件夹
     *
     * @param folder
     *            检入的文件夹
     */
    public void setCheckInFolder(FolderIfc folder) {
        checkInFolder = folder;
    }

    /**
     * 得到检入的文件夹
     *
     * @return FolderIfc
     * @throws QMPropertyVetoException
     */

    public FolderIfc getCheckInFolder() throws Exception {
        return checkInFolder;
    }

    /**
     * 设置需要检入的对象.检验对象是否允许被检入
     *
     * @param workable:被检入的对象
     */
    public void setCheckinItem(WorkableIfc workable)
            throws NotCheckedOutException, QMRemoteException {
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.setCheckinItem() begin...");
        workable = CheckInOutCappTaskLogic.refresh(workable);

        //判断当前对象是否已被当前用户检出。如果是，则返回true; 否则，返回false。
        boolean flag = CheckInOutCappTaskLogic.isCheckedOutByUser(workable);
        //是否允许当前用户检入。
        boolean flag1 = CheckInOutCappTaskLogic.isCheckinAllowed(workable);

        //检验对象是否在公共文件夹（是返回true）
        boolean flag2 = CheckInOutCappTaskLogic.isInVault(workable);
        //例外的情况：对象不允许检入,并且对象被他人检出,对象在公共文件夹
        if (!flag1 && (CheckInOutCappTaskLogic.isCheckedOut(workable) || flag2)) {
            String user = CheckInOutCappTaskLogic.getCheckedOutBy(workable,
                    getContext().getLocale());
            String ss1 = "";
            if (user != null && !user.trim().equals("")) {
                Object aobj[] = { getIdentity(workable), user };
                //提示：您尚未检出当前对象。它当前正被他人检出。
                ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.NOT_CHECKOUT_OWNER_DISPLAY, aobj);
            } else {

                Object aobj[] = { getIdentity(workable) };
                //提示：您尚未检出当前对象
                ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.NOU_CHECK_OUT_BY_YOU, aobj);
            }
            throw new NotCheckedOutException(ss1);
        }

        //是当前用户第一次检入：对象不是当前用户检出的，对象不在公共文件夹中（即对象已被他人检出）
        firstCheckIn = !flag && !flag2;

        checkinItem = workable;
        //如果当前用户不是第一次执行检入，则获得当前对象的工作副本(如果当前对象本身就是副本，则返回其自身)
        if (!firstCheckIn) {
            checkinItem = CheckInOutCappTaskLogic.getWorkingCopy(checkinItem);
        }
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.setCheckinItem() end...return is void");
    }

    /**
     * 得到检入的对象
     *
     * @return WorkableIfc
     */
    public WorkableIfc getCheckinItem() {
        return checkinItem;
    }

    /**
     * 检入对象。
     * 实现两个功能：
     *
     * 1.进行检入前的检验；
     *
     * 2.调用检入界面去完成实际的检入操作。
     * @return WorkableIfc 被检入的对象
     */
    public WorkableIfc checkin() {
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.checkin() begin...");
        try {
            setContext(true);
            //如果不是第一次检入，并且需要检入的对象不允许检入，则提示:您尚未检出当前对象。它当前正被他人检出。
            if (!firstCheckIn
                    && !CheckInOutCappTaskLogic.isCheckinAllowed(checkinItem)) {
                Object aobj[] = {
                        getIdentity(checkinItem),
                        getIdentity(checkinItem),
                        CheckInOutCappTaskLogic.getCheckedOutBy(checkinItem,
                                getContext().getLocale()) };
                String s = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.NOT_CHECKOUT_OWNER_DISPLAY, aobj);
                throw new QMRemoteException(s);
            } else {
                boolean flag = true;
                //决定flag的值
                //如果不是第一次检入，并且没有被当前用户检出，则提示：当前对象已经被他人检出，您确实要检入它吗？
                if (!firstCheckIn
                        && !CheckInOutCappTaskLogic
                                .isCheckedOutByUser(checkinItem)) {
                    Object aobj1[] = {
                            getIdentity(checkinItem),
                            CheckInOutCappTaskLogic.getCheckedOutBy(
                                    checkinItem, getContext().getLocale()) };
                    String s1 = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.VERIFY_CHECKIN_NOT_OWNER, aobj1);
                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(getParentJFrame(),
                            s1, ss1, JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);
                    flag = (i == 0);
                }

                if (flag) {
                    JFrame frame = getParentJFrame();

                    if (frame != null) {
                        RLCheckInJFrame f = new RLCheckInJFrame(
                                getParentJFrame());
                        //设定需要操作的对象
                        f.setCheckInItem(checkinItem, firstCheckIn);
                        //设定检入的文件夹
                        if (checkInFolder != null) {
                            f.setCheckInFolder(checkInFolder);
                        }
                        f.setVisible(true);
                    }
                }
            }
        } catch (QMRemoteException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(parentFrame, e.getClientMessage(),
                    title, JOptionPane.INFORMATION_MESSAGE);
        } catch (QMException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(parentFrame, e.getLocalizedMessage(),
                    title, JOptionPane.INFORMATION_MESSAGE);
        } finally {
            setContext(false);
        }
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.checkin() end...return :"
                            + checkinItem);
        return checkinItem;
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
     * 设置上下文环境 设置线程
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

}
