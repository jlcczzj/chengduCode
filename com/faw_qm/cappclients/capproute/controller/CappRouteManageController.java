/**
 * ���ɳ��� CappRouteManageController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * Title:����·�߹�������������
 * </p>
 * <p>
 * Description:��·�߹�������ѡ��������е�һ���ӵ�ʱ�� �������������������ʾ·�ߵ�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * @author ����
 * @version 1.0
 */
public class CappRouteManageController extends CappRouteAction {
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ����·��ά�������� */
    private CappRouteManageJDialog view;

    /** �߳��� */
    private ThreadGroup threadGroup = null;

    /** ��¼:ִ�и��Ƶ�Դ·�� */
    public String originalRouteID;

    /** ��¼:��ǰ·�߹�����������·�߱� */
    private TechnicsRouteListIfc myRouteList;

    /** ִ��·�߸��ƵĿ����� */
    private RouteCopyController copyController = new RouteCopyController();

    /** ִ��·��"������"�����Ľ������ */
    private RouteCopyFromJDialog copyDialog;

    /** ִ��"ճ����"�����Ľ������ */
    private RoutePasteToJDialog pastetoDialog;

    /** ��ʾ�㲿������� */
    private RouteSelectPartJDialog viewPartsJDialog;

    /** �˵�������ʶ */
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
    
    //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
    private final static int VIEWPARENT = 16;
    private final static int VIEWPART = 17;
     //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
    /**
     * ���캯��
     *
     * @param frame
     *            ·�߹�����
     * @roseuid 4031874E002C
     */
    public CappRouteManageController(CappRouteManageJDialog frame) {
        this.view = frame;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        myRouteList = view.getRouteList();
    }

    /**
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     *
     * @return ��ǰѡ��Ķ���
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
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     *
     * @return ��ǰѡ��Ķ���
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
     * ����·����������㲿���ڵ�
     * @param RoutePartTreeObject obj ����·�����е��㲿���ڵ����
     */
    public void addNode(RoutePartTreeObject obj) {
        view.getTreePanel().addNode(obj);
    }

    /**
     * Title: �����߳�
     * Description:�ڲ������߳���
     * Copyright: Copyright (c) 2004
     * Company: һ������
     * @author ����
     * @version 1.0
     */
    class WKThread extends QMThread {
        int myAction;
        //Partֵ
        QMPartMasterInfo myPart;

        /**
         * ����WKTread
         * @param threadgroup
         * @param action
         */
        public WKThread(ThreadGroup threadgroup, int action) {
            super();
            CappRouteManageController.this.threadGroup = threadgroup;
            this.myAction = action;
        }

        /**
         * ����WKTread
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
         * WKTread���з���
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
                    //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��    
                case VIEWPARENT:
                  viewRouteParent();
                  break;
                case VIEWPART:
                  viewPart();
                break;                    
                 //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��  
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
     * ����"�½�"����
     * @see WKTread
     */
    public void processCreateCommand() {
        WKThread work = new WKThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WKTread
     */
    public void processUpdateCommand() {
        WKThread work = new WKThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * ����"ɾ��"����
     * @see WKTread
     */
    public void processDeleteCommand() {
        WKThread work = new WKThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * ����"ˢ��"����
     * @see WKTread
     */
    public void processRefreshCommand() {
        WKThread work = new WKThread(getThreadGroup(), REFRESH);
        work.start();
    }

    /**
     * ����"���"����
     * @see WKTread
     */
    public void processClearCommand() {
        WKThread work = new WKThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * ����"�˳�"����
     * @see WKTread
     */
    public void processExitCommand() {
        WKThread work = new WKThread(getThreadGroup(), EXIT);
        work.start();
    }


    /**
     * ����"�鿴"����
     * @see WKTread
     */
    public void processViewCommand() {
        WKThread work = new WKThread(getThreadGroup(), VIEW);
        work.start();
    }
    
    //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
    public void processViewParentCommand() {
     WKThread work = new WKThread(getThreadGroup(), VIEWPARENT);
     work.start();
   }

   public void processViewPartCommand() {
     WKThread work = new WKThread(getThreadGroup(), VIEWPART);
     work.start();
   }
//   CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��

    /**
     * ����"ճ����"����
     * @see WKTread
     */
    public void processPasteToCommand() {
        WKThread work = new WKThread(getThreadGroup(), PASTETO);
        work.start();
    }

    /**
     * ����"����"����
     * @see WKTread
     */
    public void processCopyCommand() {
        WKThread work = new WKThread(getThreadGroup(), COPY);
        work.start();
    }

    /**
     * ����"������"����
     * @see WKTread
     */
    public void processCopyFromCommand() {
        WKThread work = new WKThread(getThreadGroup(), COPYFROM);
        work.start();
    }

    /**
     * ����"ճ��"����
     * @see WKTread
     */
    public void processPasteCommand() {
        WKThread work = new WKThread(getThreadGroup(), PASTE);
        work.start();
    }

    /**
     * ����"ѡ��"����
     * @see WKTread
     */
    public void processSelectCommand() {
        WKThread work = new WKThread(getThreadGroup(), SELECT);
        work.start();
    }

    /**
     * ����"���ڹ���·�߹���"����
     * @see WKTread
     */
    public void processHelpManageCommand() {
        WKThread work = new WKThread(getThreadGroup(), HELPMANAGE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WKTread
     */
    public void processAboutCommand() {
        WKThread work = new WKThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * �½�·��
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
     * ����·��
     * �˷�����CappRouteManageJDialog568�еĸ��²˵��򹤾�������
     * ���ȼ���������µ�����Ƿ���·�ߣ�
     * �ޣ�����ʾ���½��棬
     * �У�����ʾ ��Ӧ�ĸ�Ӧ�ĸ��½��档
     *
     * @throws QMRemoteException
     * @roseuid 403187650062
     */
    private void updateRoute() throws QMRemoteException {
        if (verbose || true) {
            System.out.println("����CappRouteManagerController��������Ӧ�ĸ��½���");
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
     * ��CappRouteManageJDialog���� �鿴·��(�ʿͻ�)
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
     * �鿴·��(WEB)
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
        //add by guoxl on 2008.03.20(Ťת���ݿͻ�ʱû��bsoID)
        hashmap.put("bsoID",routListIfc.getBsoID());
        //add by guoxl end
        //��bsoID�������鿴·�ߡ�ҳ��
        RichToThinUtil.toWebPage("route_view_route.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());
    }
    
    //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·�� 
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
//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��

    /**
     * ɾ��·��
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
            //���÷���ɾ��ָ����·��
            Class[] paraClass = { ListRoutePartLinkIfc.class };
            Object[] obj = { info };
            useServiceMethod("TechnicsRouteService", "deleteRoute",
                    paraClass, obj);
            //���ڵ�ˢ��Ϊ��·��״̬
            view.getTreePanel().refreshNode(treeObj);
            view.getTaskPanel().setVisible(false);

        }
        }

        view.setCursor(Cursor.getDefaultCursor());

    }

    /**
     * ˢ��
     * @throws QMRemoteException
     */
    private void refresh() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().refreshNode(this.getSelectedTreeObject());
        view.refresh();
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ���
     * @throws QMRemoteException
     */
    private void clearAllObjects() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTreePanel().removeAllSelectedNodes();
        view.getTaskPanel().setVisible(false);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * �˳�
     */
    private void exitSystem() {
        view.dispose();
    }

    /**
     * ����ѡ���㲿��
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
     * ����
     * @throws QMRemoteException
     * @roseuid 403187D200B9
     */
    private void copy() throws QMRemoteException {
        originalRouteID = this.getSelectedObject().getRouteID();
        copyController.setOriginalRoute(originalRouteID);
    }

    /**
     * ·�߸�����
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
     * ճ��
     * @throws QMRemoteException
     */
    private void paste() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ListRoutePartLinkIfc partlink = this.getSelectedObject();
        //���Ŀ���㲿���Ĺ���·���Ƿ����
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
     * ճ����
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
     * ·�߱���� ����
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
     * ����
     * @see CappRouteManageJFrame_AboutBox
     */
    private void helpAbout() {
        CappRouteManageJFrame_AboutBox dlg = new CappRouteManageJFrame_AboutBox(
                view);
        dlg.setModal(true);
        dlg.setVisible(true);
    }
    //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��   
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
          JOptionPane.showMessageDialog(view, "δ�ҵ��㲿������С�汾���޷�����������");
          view.setCursor(Cursor.getDefaultCursor());
          return;
        }
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", part.getBsoID());
//    ��bsoID�������鿴·�ߡ�ҳ��
        RichToThinUtil.toWebPage("Part-Other-PartLookOver-001.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());

      }
    //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·�� 
}
