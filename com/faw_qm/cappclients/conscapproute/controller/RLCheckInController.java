/**
 * ���ɳ��� RLCheckInController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * Title:���빤��·�߱����������
 * </p>
 * <p>
 * Description:���빤��·�߱����������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */
public class RLCheckInController extends CappRouteAction {
    /** ��Դ */
    private static ResourceBundle resources = null;

    /** ������ */
    private JFrame parentFrame = null;

    /** ������ */
    private WorkableIfc checkinItem;

    /**
     * �Ƿ��״μ��� �����״μ����ʱ�����û�ѡ�� ���ϼ�
     */
    private boolean firstCheckIn;

    /** ��ǰ�߳� */
    private ThreadGroup contextGroup;

    /** ����·�������ϼУ� */
    private FolderIfc checkInFolder;

    /**
     * ���캯��
     *
     * @param frame
     *            ������
     * @roseuid 40315DF803C0
     */
    public RLCheckInController(JFrame frame) {
        firstCheckIn = false;
        contextGroup = QMCt.getContext().getThreadGroup();
        parentFrame = frame;
    }

    /**
     * ���ø�����
     *
     * @param frame
     *            ������
     */
    public void setParentFrame(JFrame frame) {
        parentFrame = frame;
    }

    /**
     * ��ø�����
     *
     * @return javax.swing.JFrame
     * @roseuid 403160650246
     */
    public JFrame getParentJFrame() {
        return parentFrame;
    }

    /**
     * ���ü�����ļ���
     *
     * @param folder
     *            ������ļ���
     */
    public void setCheckInFolder(FolderIfc folder) {
        checkInFolder = folder;
    }

    /**
     * �õ�������ļ���
     *
     * @return FolderIfc
     * @throws QMPropertyVetoException
     */

    public FolderIfc getCheckInFolder() throws Exception {
        return checkInFolder;
    }

    /**
     * ������Ҫ����Ķ���.��������Ƿ���������
     *
     * @param workable:������Ķ���
     */
    public void setCheckinItem(WorkableIfc workable)
            throws NotCheckedOutException, QMRemoteException {
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.setCheckinItem() begin...");
        workable = CheckInOutCappTaskLogic.refresh(workable);

        //�жϵ�ǰ�����Ƿ��ѱ���ǰ�û����������ǣ��򷵻�true; ���򣬷���false��
        boolean flag = CheckInOutCappTaskLogic.isCheckedOutByUser(workable);
        //�Ƿ�����ǰ�û����롣
        boolean flag1 = CheckInOutCappTaskLogic.isCheckinAllowed(workable);

        //��������Ƿ��ڹ����ļ��У��Ƿ���true��
        boolean flag2 = CheckInOutCappTaskLogic.isInVault(workable);
        //���������������������,���Ҷ������˼��,�����ڹ����ļ���
        if (!flag1 && (CheckInOutCappTaskLogic.isCheckedOut(workable) || flag2)) {
            String user = CheckInOutCappTaskLogic.getCheckedOutBy(workable,
                    getContext().getLocale());
            String ss1 = "";
            if (user != null && !user.trim().equals("")) {
                Object aobj[] = { getIdentity(workable), user };
                //��ʾ������δ�����ǰ��������ǰ�������˼����
                ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.NOT_CHECKOUT_OWNER_DISPLAY, aobj);
            } else {

                Object aobj[] = { getIdentity(workable) };
                //��ʾ������δ�����ǰ����
                ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                        CappRouteRB.NOU_CHECK_OUT_BY_YOU, aobj);
            }
            throw new NotCheckedOutException(ss1);
        }

        //�ǵ�ǰ�û���һ�μ��룺�����ǵ�ǰ�û�����ģ������ڹ����ļ����У��������ѱ����˼����
        firstCheckIn = !flag && !flag2;

        checkinItem = workable;
        //�����ǰ�û����ǵ�һ��ִ�м��룬���õ�ǰ����Ĺ�������(�����ǰ��������Ǹ������򷵻�������)
        if (!firstCheckIn) {
            checkinItem = CheckInOutCappTaskLogic.getWorkingCopy(checkinItem);
        }
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.setCheckinItem() end...return is void");
    }

    /**
     * �õ�����Ķ���
     *
     * @return WorkableIfc
     */
    public WorkableIfc getCheckinItem() {
        return checkinItem;
    }

    /**
     * �������
     * ʵ���������ܣ�
     *
     * 1.���м���ǰ�ļ��飻
     *
     * 2.���ü������ȥ���ʵ�ʵļ��������
     * @return WorkableIfc ������Ķ���
     */
    public WorkableIfc checkin() {
        if (verbose)
            System.out
                    .println("capproute.controller.RLCheckInController.checkin() begin...");
        try {
            setContext(true);
            //������ǵ�һ�μ��룬������Ҫ����Ķ���������룬����ʾ:����δ�����ǰ��������ǰ�������˼����
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
                //����flag��ֵ
                //������ǵ�һ�μ��룬����û�б���ǰ�û����������ʾ����ǰ�����Ѿ������˼������ȷʵҪ��������
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
                        //�趨��Ҫ�����Ķ���
                        f.setCheckInItem(checkinItem, firstCheckIn);
                        //�趨������ļ���
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
     * �õ������Ļ��� ��õ�ǰ�߳�
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
     * ���������Ļ��� �����߳�
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
