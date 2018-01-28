/** 
 * ���ɳ��� RLUndoCheckOutController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * Title:�����������·�߱����������
 * </p>
 * <p>
 * Description:
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
public class RLUndoCheckOutController extends CappRouteAction {
    /** ��Դ */
    private static ResourceBundle resources = null;

    /** ������ */
    private JFrame parentFrame;

    /** ��������Ķ��� */
    private WorkableIfc undoItem;

    /** ��ǰ�߳� */
    private ThreadGroup contextGroup;

    /** �û����в�����ʱ���Ƿ�����ʾ */
    private boolean prompt;

    /**
     * ���캯��
     * 
     * @param frame
     *            ���ñ�������Ĵ���
     * @param workable
     *            ִ�г�����������Ķ���
     * @roseuid 403161E80026
     */
    public RLUndoCheckOutController(JFrame frame, WorkableIfc workable) {
        prompt = true;
        contextGroup = QMCt.getContext().getThreadGroup();
        parentFrame = frame;
        undoItem = workable;
    }

    /**
     * ���õ����������Ĵ�����
     * 
     * @param frame
     */
    public void setParentJFrame(JFrame frame) {
        parentFrame = frame;
    }

    /**
     * �õ������������Ĵ�����
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
     * �����û����в�����ʱ���Ƿ�����ʾ
     * 
     * @param flag
     */
    public void setPromptOnUndo(boolean flag) {
        prompt = flag;
    }

    /**
     * ���ò����Ķ���
     * 
     * @param workable
     * @roseuid 4031620F014E
     */
    public void setUndoCheckOutItem(WorkableIfc workable) {
        undoItem = workable;
    }

    /**
     * ��ò����Ķ���
     * 
     * @return WorkableIfc
     * @roseuid 4031622403C5
     */
    public WorkableIfc getUndoCheckOutItem() {
        return undoItem;
    }

    /**
     * �����������
     * 
     * @return WorkableIfc: Ҫִ�г�����������Ķ���
     */
    public WorkableIfc undoCheckout() {
        if (verbose)
            System.out
                    .println("cappclients.capp.controller.UndoCheckOutController.undoCheckout() begin...");
        if (undoItem != null) {
            try {
                setContext(true);
                //�����������룬�����û���ʾ
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
                //�������������
                else {
                    boolean flag = true;
                    //��������ɵ�ǰ�û�����ģ�����ʾ���������Ѿ������˼��
                    if (!CheckInOutCappTaskLogic.isCheckedOutByUser(undoItem)) {//������ϼ�
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
                    //�ǵ�ǰ�û�����������
                    else
                    //�����Ѿ��ı�������
                    if (prompt && CheckInOutCappTaskLogic.hasChanged(undoItem)) {
                        //��������ǹ�������������Դ��
                        if (CheckInOutCappTaskLogic.isWorkingCopy(undoItem)) {
                            undoItem = CheckInOutCappTaskLogic
                                    .getOriginalCopy(undoItem);
                        }

                        //���û�ȷ�ϣ���ȷʵҪ�������������ʧ���б����
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
                    //ִ�г�������Ķ���
                    if (flag) {
                        //��ò�������Ĺ�������
                        WorkableIfc workable = CheckInOutCappTaskLogic
                                .getWorkingCopy(undoItem);
                        //ִ�г������
                        WorkableIfc workable1 = CheckInOutCappTaskLogic
                                .undoCheckout(undoItem);

                        //ˢ��ԭ��
                        BaseValueInfo info = CappClientHelper
                                .refresh((BaseValueInfo) workable1);
                        //ˢ�²�������
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
     * ��Դ��ʼ��
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
     * ���������� �����߳�
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
     * ������ʾ�쳣��Ϣ
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