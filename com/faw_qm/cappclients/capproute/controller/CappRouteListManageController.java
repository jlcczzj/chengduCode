/**
 * ���ɳ��� CappRouteListManageController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 *CR1  2009/03/12   ������   ԭ���Ż�ɾ������·�߱��ܡ�
 * 
 *                            �������Ƚ����ڵ��ά�����涼��ʧ��Ȼ��������ɾ�������ں�̨���߳���
 *                                  �������ɾ��������ִ�е�ʱ��ܳ����û�ͬʱ������������������
 *                                  ���Ľ�������п��ܻ����������ڱ�ɾ����·�߱�Ϊ�˱���������
 *                                  ������ɾ��ʱ��·�߱��BSOID�ŵ������У�Ȼ���������Ľ�������
 *                                  �ϣ���������͸�����ʾ��
 *                            
 *                           ��ע: ���ܲ�����������"ɾ������·�߱�". 
 * 
 * SS1 ��������Ʋ���������Ա���������ⶨλ�� liunan 2014-4-14
 * SS2 A004-2016-3424 ȥ��SS1���жϴ�����Ȩ���жϡ� liunan 2016-9-7
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
 * Company: һ������
 * Title:����·�߱��������������
 * @author ����
 * @mender skybird
 * @mender zz
 * @version 1.0
 * ������һ��20061107 zz  ���Ѹ���ȷЩ start
 */
public class CappRouteListManageController extends CappRouteAction {
    public WorkThread theWorkThread;

    /** ����·�߱�ά�������� */
    private CappRouteListManageJFrame view;
    /** �㲿����*/
    PartTreePanel partTreePanel ;//= new PartTreePanel(view);

    private ConfigSpecItem configSpecItem = null;
    //��ɾ����·�߱���
    private  Vector deletedRouteListVec=new Vector();//CR1
    
    /** �߳��� */
    private ThreadGroup threadGroup = null;

    /** �˵�������ʶ */
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
    
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
    private final static int CHANGE = 28;
    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����" 
//  CCBegin by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"   
    private final static int CHANGEFOLDER = 29;
//  CCEnd by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��" 
    
    /** ���ɱ��������� */
    private ReportFormsJDialog reportDialog;

    /**
     * ���캯��
     *
     * @param frame
     *            ����·�߱�ά��������
     * @roseuid 4031638000E1
     */
    public CappRouteListManageController(CappRouteListManageJFrame frame) {
        this.view = frame;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        configInit();
        partTreePanel = new PartTreePanel(view);
    }

    /**
     * ��ʼ����Ʒ�ṹ�����������û���ǰ��ɸѡ���� ����û���ǰ��ɸѡ����ΪΪ������Ĭ��ֵ��
     * ��׼ ��ͼΪ�գ���������Ϊ�գ������������ϼ�
     */
    private void configInit() {
        try {
            //��ȡ�㲿�����ù淶
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper
                    .getConfigSpec();
            ViewObjectIfc vo = null;
            //���û�����ù淶������Ĭ�ϵġ���׼�����ù淶��
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
                            "������ͼ")) {
                        vo = (ViewObjectIfc) dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);;
                //��׼������
                PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
                //���ñ�׼ʱ����ͼΪ��
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //���÷������úõ�ɸѡ�������浽���ݿ���
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
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     *
     * @return TechnicsRouteListInfo ��ǰѡ��Ķ���
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
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     *
     * @return RouteListTreeObject ��ǰѡ��Ķ���
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
     * ����·�߱�������ӽڵ�
     * @param RouteListTreeObject obj
     */
    public void addNode(RouteListTreeObject obj) {
        view.getTreePanel().addNode(obj);
    }

    /**
     * ����"�½�"����
     * @see WorkThread
     */
    public void processCreateCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processUpdateCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * ����"ɾ��"����
     * @see WorkThread
     */
    public void processDeleteCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * ����"ˢ��"����
     * @see WorkThread
     */
    public void processRefreshCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESH);
        work.start();
    }

    /**
     * ����"���"����
     * @see WorkThread
     */
    public void processClearCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * ����"�˳�"����
     * @see WorkThread
     */
    public void processExitCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), EXIT);
        work.start();
    }
    /**
     * ����"������"����
     * 20061220 zz
     */
    public void processRenameCommand() {
             WorkThread work = new WorkThread(getThreadGroup(), RENAME);
             work.start();
         }

    /**
     * ����"�鿴"����
     * @see WorkThread
     */
    public void processViewCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    /**
     * �����޸����ù�������
     * @see WorkThread
     */
    public void processConfigRuleCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CONFIG);
        work.start();
    }


    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processBrowseCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), BROWSE);
        work.start();
    }

    /**
     * ����"�༭·��"����
     * @see WorkThread
     */
    public void processEditCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), EDIT);
        work.start();
    }

    /**
     * ����·�ߵ�λ�鿴�㲿����·��
     * @see WorkThread
     */
    public void processSearchByCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCHBY);
        work.start();
    }

    /**
     * �����ۺ�·�߲鿴
     * @see WorkThread
     */
    public void processCompositiveRouteCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), COMPOSITIVEROUTE);
        work.start();
    }

    /**
     * ����"���ɱ���"����
     * @see WorkThread
     */
    public void processReportCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), REPORT);
        work.start();
    }
    
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"     
    /**
     * ����"�����Ϣ����"����
     */
    public void processChangeCommand() {
      WorkThread work = new WorkThread(getThreadGroup(), CHANGE);
      work.start();
    }
    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
    
//  CCBegin by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"    
    /**
     * ����"�����Ϣ����"����
     */
    public void processChangeFolderCommand() {
      WorkThread work = new WorkThread(getThreadGroup(), CHANGEFOLDER);
      work.start();
    }
//  CCEnd by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"  

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processCheckInCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKIN);
        work.start();
    }

    /**
     * ����"���"����
     * @see WorkThread
     */
    public void processCheckOutCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKOUT);
        work.start();
    }

    /**
     * ����"�������"����
     * @see WorkThread
     */
    public void processUndoCheckOutCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), UNDOCHECKOUT);
        work.start();
    }

    /**
     * ����"�޶�"����
     * @see WorkThread
     */
    public void processReviseCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), REVISE);
        work.start();
    }

    /**
     * ����"�鿴�汾��ʷ"����
     * @see WorkThread
     */
    public void processViewVersionCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), VERSIONHIS);
        work.start();
    }

    /**
     * ����"�鿴������ʷ"����
     * @see WorkThread
     */
    public void processViewIteratorCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), ITERATORHIS);
        work.start();
    }

    /**
     * ����"����ָ����������״̬"����
     * @see WorkThread
     */
    public void processResetStateCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), RESETSTATE);
        work.start();
    }

    /**
     * ����"�鿴����������ʷ"����
     * @see WorkThread
     */
    public void processViewLifeCycleCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), LIFECYCLEHIS);
        work.start();
    }

    /**
     * ����"����ָ����������"����
     * @see WorkThread
     */
    public void processResetLifeCycleCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), RESETLIFECYCLE);
        work.start();
    }

    /**
     * ����"����ָ����Ŀ��"����
     * @see WorkThread
     */
    public void processResetProjectCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), RESETPROJECT);
        work.start();
    }

    /**
     * ����"����·�߱����"����
     * @see WorkThread
     */
    public void processHelpManageCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), HELPMANAGE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processHelpAboutCommand() {
        WorkThread work = new WorkThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processHelp1Command() {
        WorkThread work = new WorkThread(getThreadGroup(), HELP);
        work.start();
    }

    /**
     * <p>
     * Title:�����߳�
     * </p>
     * <p>
     * Description:����һ��������ڲ��Ĺ����߳���
     * </p>
     * <p>
     * Copyright: Copyright (c) 2004
     * </p>
     * <p>
     * Company: һ������
     * </p>
     * @see QMTread
     * @author ����
     * @version 1.0
     */

    class WorkThread extends QMThread {
        int myAction;

        //EJB�����ֵ����
        TechnicsRouteListInfo myRouteList;
        //EJB�����ֵ����
        TechnicsRouteListMasterInfo myRouteListMaster;

        /**
         * ���صĹ��캯��
         * @param ThreadGroup threadgroup
         * @param int action
         */
        public WorkThread(ThreadGroup threadgroup, int action) {
            super();
            CappRouteListManageController.this.threadGroup = threadgroup;
            this.myAction = action;
        }

        /**
         * ���صĹ��캯��
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
         * ���صĹ��캯��
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
         * WorkThread�����з���
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
                    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"      
                case CHANGE:
                    changeForms();
                    break;
                //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
//                  CCBegin by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"
                case CHANGEFOLDER:
                    changeFolder();
                    break;
//                  CCEnd by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"
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
     * �½�·�߱�
     *
     * @roseuid 403163BE0216
     */
    private void createRouteList() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getTaskPanel().setViewMode(RouteListTaskJPanel.CREATE_MODE);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ����·�߱�
     * @throws QMRemoteException
     * @roseuid 403163DA002C
     */
    private void updateRouteList() throws QMRemoteException {
        TechnicsRouteListInfo routelist = getSelectedObject();
        if (routelist instanceof WorkableIfc) {
            //�����ԭ��,����ʾ�����޸�

            if (((WorkableIfc) routelist).getWorkableState().equals("c/o")) {
                if (verbose) {
                    System.out
                            .println("CappRouteListManageController:508:����·�߱�Ĳ����������ԭ���ķ�֧");
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

                // ������һ��20061107 zz  ���Ѹ���ȷЩ start
                JOptionPane.showMessageDialog(view, message, title,
                        JOptionPane.INFORMATION_MESSAGE);
               // ������һ��20061107 zz  ���Ѹ���ȷЩ end
              //  JOptionPane.showMessageDialog(view,"����·�߱�"+routelist.getRouteListName()+"�ѱ��û�"+whocheckoutthelist+"���");
                return;
            }
            //����ڹ������ϼ�,����
            else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) routelist)) {
                if (verbose) {
                    System.out
                            .println("CappRouteListManageController:526:����·�߱�Ĳ���������ڹ������ϼ��еķ�֧");
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
                    if (checkOutRouteList()) //ʵ�����ж��Ƿ����ɹ���
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
            } else //�ڸ������ϼ�
            {
                if (verbose) {
                    System.out
                            .println("CappRouteListManageController:559:����·�߱�Ĳ���������ڸ������ϼ��еķ�֧");
                }

                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RouteListTaskJPanel p = view.getTaskPanel();
                p.setTechnicsRouteList(this.getSelectedObject());
                p.setViewMode(RouteListTaskJPanel.UPDATE_MODE);
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

//  CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·�ߣ��ѷ������ѱ��������׼֪ͨ�鲻��������
    /**
     * 20070103 add liuming
     * ������
     * @throws QMRemoteException
     */
    private void renameRouteList() throws QMRemoteException {
      TechnicsRouteListInfo obj = this.getSelectedObject();

      if (CheckInOutCappTaskLogic.isInVault( (WorkableIfc) obj)) {
        //CCBegin by liunan 2011-05-18 �ĳɹ���Ա���Ը�
        //if (isReleased(obj)) {
        //CCBegin SS2
        /*if (isReleased(obj)&&!inAdministrators()) {
        //CCEnd by liunan 2011-05-18
          String mm = obj.getRouteListNumber() + "�ѷ����������޸ģ�";
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
        JOptionPane.showMessageDialog(view,obj.getRouteListNumber()+"�ѱ�������޷���������");
      }

    }
    
    /**
     * �жϣ�·�߱��Ƿ��ڷ���״̬
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
//  CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��   

    /**
     * �鿴·�߱�(�C�ͻ�)
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
        //��bsoID�������鿴·�߱�ҳ��
        RichToThinUtil.toWebPage("route_look_routeList.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * �޸����ù���
     */
    private void makeConfigRule() {
        new ConfigSpecController(configSpecItem, SelectPartJDialog
                .getPartTreePanel(),this.view);

    }


    /**
     * �鿴·�߱�(�ʿͻ�)
     *
     * ����·�߱��������ѡ��һ��·�߱�ʱ�������ô˷�������ʾ�㲿����Ϣ��
     *
     * @roseuid 403163E60123
     */
    public void viewDefaultRouteList() {
        try {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RouteListTaskJPanel p = view.getTaskPanel();

            //����·�߱����
            p.setTechnicsRouteList(getSelectedObject());
            //�鿴����ģʽ
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
     * ɾ��·�߱�
     * @throws QMRemoteException
     * @roseuid 40316425019C
     */
    private void deleteRouteList() throws QMRemoteException {

        RouteListTreeObject treeObj = this.getSelectedTreeObject();
        TechnicsRouteListInfo info = (TechnicsRouteListInfo) treeObj
                .getObject();

            //modify by guoxl on 20080219(ˢ��·�߱�ڵ�)
            Class[] paraclass = {
                String.class};
            Object[] paraobj = {
                info.getBsoID()};
            info = (TechnicsRouteListInfo) useServiceMethod(
                "PersistService", "refreshInfo", paraclass, paraobj);

            //modify by guoxl end

        //�������ɾ������ʾ�Ƿ�ɾ��ѡ���
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
                //���ѡ�����Ϊ�������������ԭ��
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
				 treeObj.setNoteText("(��·�߱����ڱ�ɾ��...)");
				 view.getTreePanel().addNode(treeObj);
				// End CR1
                
                
                //ˢ�²�ɾ��info��ͬʱˢ�¹���������
                //info = (TechnicsRouteListInfo)CappClientHelper.refresh(info);
                //����Capp����ɾ��ָ����·�߱�
                Class[] paraClass = { TechnicsRouteListIfc.class };
                Object[] obj = { info };
                useServiceMethod("TechnicsRouteService",
                        "deleteRouteList", paraClass, obj);
                //���ԭ����Ϊ�գ�ˢ��ԭ��Ϊ����״̬
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
	 *  ���ɾ��ʱ�����·�߱�BSOID
	 * @return  Vector ���·�߱�BSOID
	 * CR1
	 */
	public Vector getDeleteRouteLisVec()
	{

		return this.deletedRouteListVec;

	}

    /**
     * �ж��Ƿ�����ɾ��ָ����·�߱�
     * <p>
     * ����Ƿ��������û����¸�·�߱�
     * </p>
     *
     * @param info TechnicsRouteListInfo
     *            Ҫɾ���Ĺ��տ�(����򹤲�)
     * @return �������ɾ�����򷵻�true
     */
    private boolean isDeleteAllowed(TechnicsRouteListInfo info) {
        //�����ԭ��������flase
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
     * ����·�߱��ʿͻ���
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
     * ����·�߱��C�ͻ���
     *
     * @roseuid 403164BB03DD
     */
    private void searchRouteListThin() {

    }

    /**
     * ���ɱ���
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
    
    //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
    /**
     *  �����Ϣ����
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
          JOptionPane.showMessageDialog(view, "��ǰ��׼֪ͨ��ֻ��һ����汾", title,
                                        JOptionPane.INFORMATION_MESSAGE);
          return;

        }
        HashMap hashmap = new HashMap();
        hashmap.put("BsoID", objID);
        //ת��"�汾��ʷ"ҳ��
        RichToThinUtil.toWebPage("route_version_change.screen",
                                 hashmap);
      }
      view.setCursor(Cursor.getDefaultCursor());
    }
    //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"�����Ϣ����"  
    
    /**
     * ����"�ƶ�"���
     */
    public void changeFolder()throws QMRemoteException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo)this.getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.NOT_SELECT_OBJECT, null);
         //   String message = "û��ѡ��Ҫ�������ϼеĶ���";
            JOptionPane.showMessageDialog(view, message, title,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        //�ж���ѡ��Ķ����Ƿ�ΪWorkableIfc����
        if(obj instanceof WorkableIfc)
        {
            //�ж���ѡ��Ķ����Ƿ��Ǽ��״̬��
            boolean flag = WorkInProgressHelper.isCheckedOut((WorkableIfc) obj);
            //���Ϊ���״̬����ʾ�쳣��Ϣ���ö����Ѿ�����������ܱ��ƶ�������
            if(flag)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
//                String message = QMMessage.getLocalizedMessage(RESOURCE,
//                        QMProductManagerRB.NOT_MOVE, null);
                String message="�ö����Ѿ�����������ܱ��ƶ���";
                JOptionPane.showMessageDialog(view, message, title,
                        JOptionPane.WARNING_MESSAGE);
                message = null;
                return;
            }
        }
        //�ж���ѡ��Ķ����Ƿ�ΪFolderedIfc����
        if(obj instanceof FolderedIfc)
        {
            FolderEntryIfc folderedIfc = (FolderEntryIfc) getSelectedObject();
            Class[] class1 = {FolderEntryIfc.class};
            Object[] param1 = {obj};
            //�����ļ��з���ķ�����ö������ڵ��ļ��С�
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
            //�����ļ��з���ķ����ж�folder�Ƿ�Ϊ�����ļ��С�
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
            //���obj���ڸ����ļ����У����ڹ����ļ����С�
            if(!flag1)
            {
                new ChangeFolderController((RevisionControlledIfc) folderedIfc,view);
            }
            //���obj�ڸ����ļ����У���ʾ�쳣��Ϣ��
            //���ö����ڹ������ϼ��У����ܱ��Ƶ��������ϼ��С�����
            else
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
//                String message = QMMessage.getLocalizedMessage(RESOURCE,
//                        QMProductManagerRB.NOT_IN_COMMON_FOLDER, null);
                String message="�ö����ڹ������ϼ��У����ܱ��Ƶ��������ϼ��С�";
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
    

    
//  CCBegin by leixiao 2009-9-20 ԭ�򣺽����������·��,����"ת��"

    /**
     * �༭·��
     * @throws QMRemoteException
     * @roseuid 4031705701F6
     */
    private void editCappRoute() throws QMRemoteException {
        TechnicsRouteListInfo routelist = getSelectedObject();
        if (routelist instanceof WorkableIfc) {
            //�����ԭ��,����ʾ�����޸�
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
            //����ڹ������ϼ�,����
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
                    if (checkOutRouteList()) //ʵ�����ж��Ƿ����ɹ���
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
            } else //�ڸ������ϼ�
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
     *��·�ߵ�λ����
     *@see  SearchPartsDialog
     */
    public void searchBy() {
        SearchPartsDialog searchPartsDialog = new SearchPartsDialog(this.view);
        searchPartsDialog.setSize(400, 300);
        searchPartsDialog.setVisible(true);
    }

    /**
     * ��ʾ�ۺ�·�߽���
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
     * ˢ��
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
          JOptionPane.showMessageDialog(view, "�����Ѿ�������", title,
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
     * ����
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
     * ���
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
//      CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,�ѷ�����·�߲��ܼ��
        TechnicsRouteListInfo info = this.getSelectedObject();
        //CCBegin SS1
        //if (isReleased(info)) {
        //CCBegin SS2
        /*if (isReleased(info)&&!inAdministrators()) {
        //CCEnd SS1	
            String mm = info.getRouteListNumber() + "�ѷ��������ܼ����";
            JOptionPane.showMessageDialog(view, mm);
            return false;
          }*/
        //CCEnd SS2
//      CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��  
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
     * �������
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
     * �޶�
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
     * �鿴�汾��ʷ
     * @throws QMRemoteException
     */
    private void viewVersionHistory() throws QMRemoteException {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BaseValueInfo obj = (BaseValueInfo) this.getSelectedObject();

        if (obj instanceof WorkableIfc) {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = workable.getBsoID();

            HashMap hashmap = new HashMap();
            //modify by guoxl on 2008.03.31(�����ݿͻ�ʱ�����bsoID����)
            hashmap.put("bsoID", objID);
            //modify by guoxl end
            //ת��"�汾��ʷ"ҳ��
            RichToThinUtil.toWebPage("route_version_viewVersionHistory.screen",
                    hashmap);

        }

        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * �鿴������ʷ
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

              /*modify by guoxl on 20080304(Ϊ������鿴������ʷʱֻ��ʾ��ǰ�汾������
               *ǰ���Ĺ��ܣ���˸�route_version_viewIterationsHistory.jsp����
               *TechnicsRouteList��BsoID)
               */
             // MasteredIfc master = ( (IteratedIfc) workable).getMaster();
             // objID = master.getBsoID();

                objID = ((IteratedIfc)workable).getBsoID();

              //modify end
              //modify by guoxl on 2008.03.31(�����ݿͻ�ʱ�����bsoID����)
                hashmap.put("bsoID", objID);
              //modify by guoxl end
                //ת��"������ʷ"ҳ��
                RichToThinUtil.toWebPage(
                        "route_version_viewIterationsHistory.screen", hashmap);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ����·�߱����
     */
    private void helpManage() {

    }

    /**
     * ����
     */
    private void helpAbout() {
        //CappRouteListManageJFrame_AboutBox dlg = new CappRouteListManageJFrame_AboutBox(view);
        IntroduceDialog dlg = new IntroduceDialog(view,"����·�߹�����");
        dlg.setVisible();
    }
    /**
     * ����
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
     * ����ָ����������״̬
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
     * �鿴����������ʷ
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
        //��technicsBsoID�������鿴����������ʷ��¼��ҳ��
        RichToThinUtil.toWebPage("route_look_lifeCyleHistory.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());

        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.viewLifeCycleHistory() end...return is void");
        }
    }

    /**
     * ����ָ����������
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
     * ����ָ����Ŀ��
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
                //���÷������õ�ֵ����paraobj
            obj = (TechnicsRouteListInfo) useServiceMethod("PersistService",
                    "updateValueInfo", paraclass, paraobj);
             //�����װ��ָ����ҵ������·�߱�ڵ����
            RouteListTreeObject treeObject = new RouteListTreeObject(obj);
            //��ʾ·�߱����º�Ľڵ�
            view.getTreePanel().updateNode(treeObject);
            //���·�߱�ά�����
            view.getTaskPanel().setVisible(false);
            //����·�߱��������ѡ��һ��·�߱�ʱ�������ô˷�������ʾ�㲿����Ϣ��
            this.viewDefaultRouteList();
        } else {
            view.getTreePanel().updateNode(new RouteListTreeObject(obj));
        }

        if (verbose) {
            System.out
                    .println("cappclients.capproute.controller.CappRouteListManageController.resetProject() end...return is void");
        }
    }
    
    //CCBegin by liunan 2011-05-18 �жϵ�ǰ�û��Ƿ��ǹ���Ա��
    public static boolean inAdministrators() throws QMRemoteException 
    {
    	Class[] paraclass = {};
    	Object[] paraobj = {};
    	return (Boolean)useServiceMethod("UsersService", "inAdministrators", paraclass, paraobj);
		}
}
