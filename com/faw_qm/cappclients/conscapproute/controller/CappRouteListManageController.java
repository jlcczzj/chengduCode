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
 *CR2  2011/12/15 �촺Ӣ    ԭ��ͳһ���롢���������������޶����ܵĽ���Ϳ�����   �μ���Wipר�����˵��1_��ǿ.doc��Versionר�����˵��1_��ǿ.doc
 *CR3  2011/12/20 ����  ԭ��ͳһ������
 *CR4 2011/12/28 ���� ԭ�� ���ӿ���ѡ�ж�����ڵ㷽��
 *CR5 2012/01/06 ���� ԭ�� ��Ϊ�������롢��������������ɾ��
 *CR6 2012/01/06 �촺Ӣ     ���ӷ���·�߹���
 *CR7 2012/03/29 ����ԭ��μ�TD5975
 *CR8 ����  ��������ﲻ����������
 *SS1 ���ع�Ӧ�� liuyang 2014-8-14
 * SS2 ���������ȣ�������30��Ϊ50�� liunan 2015-1-19
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
 * Copyright: Copyright (c) 2004 Company: һ������ Title:����·�߱��������������
 * @author ����
 * @mender skybird
 * @mender zz
 * @version 1.0 ������һ��20061107 zz ���Ѹ���ȷЩ start
 */
public class CappRouteListManageController extends CappRouteAction
{
    public WorkThread theWorkThread;

    /** ����·�߱�ά�������� */
    private CappRouteListManageJFrame view;
    /** �㲿���� */
    PartTreePanel partTreePanel;//= new PartTreePanel(view);
    private ConfigSpecItem configSpecItem = null;
    //��ɾ����·�߱���
    private Vector deletedRouteListVec = new Vector();//CR1

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
    /** �ڲ��� */
    private MainRefreshListener listener;
    //CR5 end
    /** ���ɱ��������� */
    private CompositiveShowPartsJDialog compositiveDialog;

    //20120111 xucy add
    /** ��Դ�ļ�·�� */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * ���캯��
     * @param frame ����·�߱�ά��������
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
     * ��ʼ����Ʒ�ṹ�����������û���ǰ��ɸѡ���� ����û���ǰ��ɸѡ����ΪΪ������Ĭ��ֵ�� ��׼ ��ͼΪ�գ���������Ϊ�գ������������ϼ�
     */
    private void configInit()
    {
        try
        {
            //��ȡ�㲿�����ù淶
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo)PartHelper.getConfigSpec();
            ViewObjectIfc vo = null;
            //���û�����ù淶������Ĭ�ϵġ���׼�����ù淶��
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
                    if(((ViewObjectIfc)dd.elementAt(i)).getViewName().equals("������ͼ"))
                    {
                        vo = (ViewObjectIfc)dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                ;
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
     * <p>Title: ����ˢ�·��������2003-08-27��</p> <p>Description: �ڲ��ࡣ</p> <p>Copyright: Copyright (c) 2003</p> <p>Company: һ������</p>
     * @author someone
     * @version 1.0
     */
    class MainRefreshListener implements RefreshListener
    {
        //ʵ�ֽӿ��еĳ��󷽷�fefreshObject()��
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
         * ���캯����
         */
        MainRefreshListener()
        {}
    }

    /**
     * ˢ�¹������е��㲿����
     * @param modifiedPart QMPartIfc ��ˢ�µ��㲿����
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
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     * @return TechnicsRouteListInfo ��ǰѡ��Ķ���
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
     * �������ѡ�еĹ���·�߱�ֵ����
     * @return TechnicsRouteListInfo[] ��ǰѡ��Ķ���
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
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     * @return RouteListTreeObject ��ǰѡ��Ķ���
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
     * ����·�߱�������ӽڵ�
     * @param RouteListTreeObject obj
     */
    public void addNode(RouteListTreeObject obj)
    {
        view.getTreePanel().addNode(obj);
    }

    /**
     * ����"�½�"����
     * @see WorkThread
     */
    public void processCreateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processUpdateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * ����"ɾ��"����
     * @see WorkThread
     */
    public void processDeleteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * ����"ˢ��"����
     * @see WorkThread
     */
    public void processRefreshCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESH);
        work.start();
    }

    /**
     * ����"���"����
     * @see WorkThread
     */
    public void processClearCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * ����"�˳�"����
     * @see WorkThread
     */
    public void processExitCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXIT);
        work.start();
    }

    /**
     * ����"������"���� 20061220 zz
     */
    public void processRenameCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RENAME);
        work.start();
    }

    /**
     * ����"�鿴"����
     * @see WorkThread
     */
    public void processViewCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    /**
     * �����޸����ù�������
     * @see WorkThread
     */
    public void processConfigRuleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CONFIG);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processBrowseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BROWSE);
        work.start();
    }

    /**
     * ����"�༭·��"����
     * @see WorkThread
     */
    public void processEditCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EDIT);
        work.start();
    }

    /**
     * ����·�ߵ�λ�鿴�㲿����·��
     * @see WorkThread
     */
    public void processSearchByCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCHBY);
        work.start();
    }

    /**
     * �����ۺ�·�߲鿴
     * @see WorkThread
     */
    public void processCompositiveRouteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COMPOSITIVEROUTE);
        work.start();
    }

    /**
     * ����"���ɱ���"����
     * @see WorkThread
     */
    public void processReportCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REPORT);
        work.start();
    }

    //begin CR6
    /**
     * ����"���ɱ���"����
     * @see WorkThread
     */
    public void processReleaseRouteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RELEASEROUTE);
        work.start();
    }

    //end CR6
    /**
     * ����"�鿴����·��"����
     * @see WorkThread
     */
    public void processViewModelRouteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEWMODELROUTE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processCheckInCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKIN);
        work.start();
    }

    /**
     * ����"���"����
     * @see WorkThread
     */
    public void processCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKOUT);
        work.start();
    }

    /**
     * ����"�������"����
     * @see WorkThread
     */
    public void processUndoCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UNDOCHECKOUT);
        work.start();
    }

    /**
     * ����"�鿴�汾��ʷ"����
     * @see WorkThread
     */
    public void processViewVersionCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VERSIONHIS);
        work.start();
    }

    /**
     * ����"�鿴������ʷ"����
     * @see WorkThread
     */
    public void processViewIteratorCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITERATORHIS);
        work.start();
    }

    /**
     * ����"����ָ����������״̬"����
     * @see WorkThread
     */
    public void processResetStateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RESETSTATE);
        work.start();
    }

    /**
     * ����"�鿴����������ʷ"����
     * @see WorkThread
     */
    public void processViewLifeCycleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), LIFECYCLEHIS);
        work.start();
    }

    /**
     * ����"����ָ����������"����
     * @see WorkThread
     */
    public void processResetLifeCycleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RESETLIFECYCLE);
        work.start();
    }

    /**
     * ����"����ָ����Ŀ��"����
     * @see WorkThread
     */
    public void processResetProjectCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RESETPROJECT);
        work.start();
    }

    /**
     * ����"����·�߱����"����
     * @see WorkThread
     */
    public void processHelpManageCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), HELPMANAGE);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processHelpAboutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * ����"����"����
     * @see WorkThread
     */
    public void processHelp1Command()
    {
        WorkThread work = new WorkThread(getThreadGroup(), HELP);
        work.start();
    }
//CCBegin SS1
    /**
     * ����������Ӧ�̡�
     */
    public void processCreateSuppler(){
    	 WorkThread work = new WorkThread(getThreadGroup(), CREATESUPPLIER);
         work.start();
    }
    /**
     * ����������Ӧ�̡�
     */
    public void processSearchSuppler(){
    	WorkThread work = new WorkThread(getThreadGroup(), SEARCHSUPPLIER);
        work.start();
    }
    /**
     * ���������㲿����
     */
    public void processSearchPart(){
    	WorkThread work = new WorkThread(getThreadGroup(), SEARCHPART);
        work.start();
    }
 //CCEnd SS1
    /**
     * <p> Title:�����߳� </p> <p> Description:����һ��������ڲ��Ĺ����߳��� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
     * @see QMTread
     * @author ����
     * @version 1.0
     */

    class WorkThread extends QMThread
    {
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
        public WorkThread(ThreadGroup threadgroup, int action)
        {
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
        public WorkThread(ThreadGroup threadgroup, int action, TechnicsRouteListInfo list)
        {
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
        public WorkThread(ThreadGroup threadgroup, int action, TechnicsRouteListMasterInfo master)
        {
            super();
            CappRouteListManageController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myRouteListMaster = master;
        }

        /**
         * WorkThread�����з���
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
     * �½�·�߱�
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
     * ����·�߱�
     * @throws QMException 
     * @throws QMRemoteException
     * @roseuid 403163DA002C
     */
    private void updateRouteList() throws QMException
    {
        TechnicsRouteListInfo routelist = getSelectedObject();
        if(routelist instanceof WorkableIfc)
        {
            //�����ԭ��,����ʾ�����޸�

            if(((WorkableIfc)routelist).getWorkableState().equals("c/o"))
            {
                if(verbose)
                {
                    System.out.println("CappRouteListManageController:508:����·�߱�Ĳ����������ԭ���ķ�֧");
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

                    // ������һ��20061107 zz  ���Ѹ���ȷЩ start
                    JOptionPane.showMessageDialog(view, message, title, JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception ex)
                {
                    String message = ex.getMessage();
                    DialogFactory.showInformDialog(view, message);
                }
                // ������һ��20061107 zz  ���Ѹ���ȷЩ end
                //  JOptionPane.showMessageDialog(view,"����·�߱�"+routelist.getRouteListName()+"�ѱ��û�"+whocheckoutthelist+"���");
                return;
            }
            //����ڹ������ϼ�,����
            else if(CheckInOutCappTaskLogic.isInVault((WorkableIfc)routelist))
            {
                if(verbose)
                {
                    System.out.println("CappRouteListManageController:526:����·�߱�Ĳ���������ڹ������ϼ��еķ�֧");
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
                    if(checkOutRouteList()) //ʵ�����ж��Ƿ����ɹ���
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
            //�ڸ������ϼ�
            {
                if(verbose)
                {
                    System.out.println("CappRouteListManageController:559:����·�߱�Ĳ���������ڸ������ϼ��еķ�֧");
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
     * �鿴·�߱�(�C�ͻ�)
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
        //��bsoID�������鿴·�߱�ҳ��
        RichToThinUtil.toWebPage("consroute_look_routeList.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());
    }
//CCBegin SS1
    /**
     * ������Ӧ��
     */
    private void createSupplier()throws QMException
    {
    	view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	RichToThinUtil.toWebPage("supCreate.screen");
    	view.setCursor(Cursor.getDefaultCursor());
    }
    /**
     * ������Ӧ��
     * @throws QMException
     */
    private void searchSupplier()throws QMException
    {
    	view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	RichToThinUtil.toWebPage("Search-001.screen?selectobject=Supplier&scheme=");
    	view.setCursor(Cursor.getDefaultCursor());
    }
    /**
     * �����㲿��
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
     * �޸����ù���
     */
    private void makeConfigRule()
    {
        new ConfigSpecController(configSpecItem, SelectPartJDialog.getPartTreePanel(), this.view);

    }

    /**
     * �鿴·�߱�(�ʿͻ�) ����·�߱��������ѡ��һ��·�߱�ʱ�������ô˷�������ʾ�㲿����Ϣ��
     * @roseuid 403163E60123
     */
    public void viewDefaultRouteList()
    {
        try
        {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RouteListTaskJPanel p = view.getTaskPanel();
            //����·�߱����
            p.setTechnicsRouteList(getSelectedObject());
            //�鿴����ģʽ
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
     * ɾ��·�߱�
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

            //modify by guoxl on 20080219(ˢ��·�߱�ڵ�)
            Class[] paraclass = {String.class};
            Object[] paraobj = {info.getBsoID()};
            info = (TechnicsRouteListInfo)RequestHelper.request("PersistService", "refreshInfo", paraclass, paraobj);
            //modify by guoxl end
            //�������ɾ������ʾ�Ƿ�ɾ��ѡ���
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

                    //���ѡ�����Ϊ�������������ԭ��
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
                    treeObj[j].setNoteText("(��·�߱����ڱ�ɾ��...)");
                    view.getTreePanel().addNode(treeObj[j]);
                    // End CR1
                    //ˢ�²�ɾ��info��ͬʱˢ�¹���������
                    //info = (TechnicsRouteListInfo)CappClientHelper.refresh(info);
                    //����Capp����ɾ��ָ����·�߱�
                    Class[] paraClass = {TechnicsRouteListIfc.class};
                    Object[] obj = {info};
                    RequestHelper.request("consTechnicsRouteService", "deleteRouteList", paraClass, obj);
                    //���ԭ����Ϊ�գ�ˢ��ԭ��Ϊ����״̬
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
     * ���ɾ��ʱ�����·�߱�BSOID
     * @return Vector ���·�߱�BSOID CR1
     */
    public Vector getDeleteRouteLisVec()
    {

        return this.deletedRouteListVec;

    }

    /**
     * �ж��Ƿ�����ɾ��ָ����·�߱� <p> ����Ƿ��������û����¸�·�߱� </p>
     * @param info TechnicsRouteListInfo Ҫɾ���Ĺ��տ�(����򹤲�)
     * @return �������ɾ�����򷵻�true
     */
    private boolean isDeleteAllowed(TechnicsRouteListInfo info)
    {
        //�����ԭ��������flase
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
     * ����·�߱��ʿͻ���
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
     * ����·�߱��C�ͻ���
     * @roseuid 403164BB03DD
     */
    private void searchRouteListThin()
    {

    }

    /**
     * ���ɱ���
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
     * ����·��
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
     * �鿴����·��
     */
    private void viewModelRoute()
    {
        ViewModelRouteJDialog viewmodelroutedialog = new ViewModelRouteJDialog();
        viewmodelroutedialog.setVisible(true);
    }

    /**
     * ������·�� 20120118 �촺Ӣ add
     */
    private void editCappRoute()
    {
        HashMap map = RouteClientUtil.getShortCutRoute();
        //��ʾ������·�߽���
        ShortCutRouteDialog shortCutDialog = new ShortCutRouteDialog(view, map);
        shortCutDialog.setSize(500, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        shortCutDialog.setLocation((int)(screenSize.getWidth() - 500) / 2, (int)(screenSize.getHeight() - 400) / 2);
        shortCutDialog.setVisible(true);
    }

    /**
     *��·�ߵ�λ����
     *@see SearchPartsDialog
     */
    public void searchBy()
    {
        SearchPartsDialog searchPartsDialog = new SearchPartsDialog(this.view);
        searchPartsDialog.setSize(800, 600);
        searchPartsDialog.setVisible(true);
    }

    /**
     * ��ʾ�ۺ�·�߽���
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
     * ˢ��
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
            JOptionPane.showMessageDialog(view, "�����Ѿ�������", title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        view.getTaskPanel().setVisible(false);
        this.viewDefaultRouteList();
        view.refresh();
        view.setCursor(Cursor.getDefaultCursor());
    }

    //20120105 begin
    /**
     * ˢ�����ڵ㣬��������ʱʹ��
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
     * ��ӽڵ㵽���ϣ���������ͳ������ʱʹ��
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
     * ɾ�����ڵ㣬�������ʱʹ��
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
     * ���
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
     * �˳�
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
     * ��������
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
//            //�ж��㲿���Ƿ���·��
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
//                    // ���ü���Ķ���
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
    //            //            //ɾ���ɽڵ����
    //            //            view.removeNode((BaseValueInfo) checkInIfc);
    //            //            //���½ڵ�
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
    //        //����û�м��
    //        boolean flag1 = workable.getWorkableState().equals("c/i");
    //        boolean flag2 = CheckInOutCappTaskLogic.isInVault(workable);
    //        if(flag1 && flag2)
    //        {
    //            Object aobj[] = {getIdentity(workable)};
    //            //��ʾ������δ�����ǰ����
    //            String ss1 = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOU_CHECK_OUT_BY_YOU, aobj);
    //            showMessageDialog(ss1);
    //            return false;
    //
    //        }
    //        //����δ����
    //        else if(flag1 && !flag2)
    //        {
    //            return true;
    //        }
    //        //�����������
    //        else if(!flag1)
    //        {
    //
    //            UserIfc currentUser = (UserIfc)RequestHelper.request("SessionService", "getCurUserInfo", null, null);
    //            //�Ƿ񱻵�ǰ�û����������ǣ��򷵻�true; ���򣬷���false��
    //            if(WorkInProgressHelper.isCheckedOut(workable, currentUser))
    //            {
    //                return true;
    //            }
    //            //������ǵ�ǰ�û������������жϲ������ǲ��ǹ���Ա
    //            else
    //            {
    //                Class[] claPram2 = {UserIfc.class, WorkableIfc.class};
    //                Object[] obj2 = {currentUser, workable};
    //                //begin CR10
    //                Object[] result = (Object[])RequestHelper.request("StandardCappService", "currentUserIsAdministrator", claPram2, obj2);//end CR10
    //                // �ǲ��ǹ���Ա
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
    //     * ���洰�ڵĹ���
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
     * �������
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
        // ��ÿ��Ҫ�����·�߱�ˢ��
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
        // ����WorkInProgressService�е�checkOut����ִ�м������
        Vector checkOuted = (Vector)RequestHelper.request("consTechnicsRouteService", "checkOutTechRouteList", theClass, theObjs);
        int vecSize = checkOuted.size();
        WorkingPair checkOutedObject = null;
        // ����������Ϣ��ӵ���Ϣ��ʾ����
        for(int m = 0;m < vecSize;m++)
        {
            checkOutedObject = (WorkingPair)checkOuted.elementAt(m);
            // �ж��Ƿ�����ɹ�
            flag = checkOutedObject.isOperateSuccess();
            if(flag)
            { // ��ȡԭ��
                WorkableIfc OriginalCopy = checkOutedObject.getOriginalCopy();
                // ��ȡ����
                WorkableIfc WorkingCopy = checkOutedObject.getWorkingCopy();
                messageStr.append(((TechnicsRouteListInfo)WorkingCopy).getIdentity() + "�ɹ�" + "\n");//CR19
                // ���¡�
                dispatchRefreshEvent(OriginalCopy, 1);
                // ������
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
    //            // ���÷���ִ�м������·�߱����
    //            WorkingPair workpair = (WorkingPair)RequestHelper.request("TechnicsRouteService", "checkOutTechRouteList", paraclass, objs);
    //            Exception e = workpair.getException();
    //            if(e != null)
    //            {
    //                String messages = QMExceptionHandler.handle(e);
    //                DialogFactory.showWarningDialog(view, messages);
    //                return;
    //            }
    //            RouteTreePanel treePanel = view.getTreePanel();
    //            //ɾ��Դ��
    //            treePanel.removeNode(new RouteListTreeObject(checkOutIfc));
    //
    //            //���������ڵ�
    //            RouteListTreeObject newtreeObj = new RouteListTreeObject(workpair.getWorkingCopy());
    //            treePanel.addNode(newtreeObj);
    //            treePanel.setNodeSelected(newtreeObj);
    //        }
    //    }

    //end CR2
    /**
     * �ַ�ˢ���źš�
     * @param i int
     * @param obj Object
     */
    private void dispatchRefreshEvent(Object obj, int i)
    {
        //������ˢ���¼�
        RefreshEvent refreshevent = new RefreshEvent(this, i, obj);
        //��ˢ�·������ˢ���¼�
        RefreshService.getRefreshService().dispatchRefresh(refreshevent);
    }

    /**
     * �������
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
        // ����WorkInProgressService�е�checkOut����ִ�м������
        Vector undocheckOuted = (Vector)RequestHelper.request("WorkInProgressService", "undoCheckOut", theClass, theObjs);
        int vecSize = undocheckOuted.size();
        WorkingPair undocheckOutedObject = null;
        // ����������Ϣ��ӵ���Ϣ��ʾ����
        for(int m = 0;m < vecSize;m++)
        {
            undocheckOutedObject = (WorkingPair)undocheckOuted.elementAt(m);
            //�Ƿ��N����ɹ�
            boolean flag = undocheckOutedObject.isOperateSuccess();
            if(flag)
            {
                // ��ȡ����
                WorkableIfc workableCopy = undocheckOutedObject.getWorkingCopy();
                // ��ȡԭ��
                WorkableIfc OriginalCopy = undocheckOutedObject.getOriginalCopy();
                //�Ƴ������ڵ�
                dispatchRefreshEvent(workableCopy, 2);
                //����ԭ���ڵ�
                dispatchRefreshEvent(OriginalCopy, 0);
                messageStr.append(((TechnicsRouteListInfo)OriginalCopy).getIdentity() + "�ɹ�" + "\n");//CR19
            }else
            {
                if(obj[m] instanceof TechnicsRouteListInfo)
                {
                    //����]�г��N����ɹ��������ջ����������Ϣ��ӵ���ʾ����
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
    //            // �����������룬�����û���ʾ
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
    //            // �������������
    //            else
    //            {
    //                // ��ò�������Ĺ�������
    //                WorkableIfc workable = CheckInOutCappTaskLogic.getWorkingCopy(undoCheckOutIfc);
    //                WorkableIfc original = CheckInOutCappTaskLogic.getOriginalCopy(workable);
    //                // ���÷���ɾ��ָ���Ĺ���·�߱�
    //                Class[] paraClass = {TechnicsRouteListIfc.class};
    //                Object[] obj = {workable};
    //                // ִ�г�������Ķ���
    //                RequestHelper.request("TechnicsRouteService", "deleteRouteList", paraClass, obj);
    //                // ˢ��ԭ��
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
     * �鿴�汾��ʷ
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
            //modify by guoxl on 2008.03.31(�����ݿͻ�ʱ�����bsoID����)
            hashmap.put("bsoID", objID);
            //modify by guoxl end
            //ת��"�汾��ʷ"ҳ��
            RichToThinUtil.toWebPage("consroute_version_viewVersionHistory.screen", hashmap);

        }

        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * �鿴������ʷ
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
                 * modify by guoxl on 20080304(Ϊ������鿴������ʷʱֻ��ʾ��ǰ�汾������ǰ���Ĺ��ܣ���˸�route_version_viewIterationsHistory.jsp����TechnicsRouteList��BsoID)
                 */
                // MasteredIfc master = ( (IteratedIfc) workable).getMaster();
                // objID = master.getBsoID();

                objID = ((IteratedIfc)workable).getBsoID();

                //modify end
                //modify by guoxl on 2008.03.31(�����ݿͻ�ʱ�����bsoID����)
                hashmap.put("bsoID", objID);
                //modify by guoxl end
                //ת��"������ʷ"ҳ��
                RichToThinUtil.toWebPage("consroute_version_viewIterationsHistory.screen", hashmap);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ����·�߱����
     */
    private void helpManage()
    {

    }

    /**
     * ����
     */
    private void helpAbout()
    {
        //CappRouteListManageJFrame_AboutBox dlg = new CappRouteListManageJFrame_AboutBox(view);
        IntroduceDialog dlg = new IntroduceDialog(view, "����·�߹�����");
        dlg.setVisible();
    }

    /**
     * ����
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
     * ����ָ����������״̬
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
     * �鿴����������ʷ
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
        //��technicsBsoID�������鿴����������ʷ��¼��ҳ��
        RichToThinUtil.toWebPage("consroute_look_lifeCyleHistory.screen", hashmap);
        view.setCursor(Cursor.getDefaultCursor());

        if(verbose)
        {
            System.out.println("cappclients.capproute.controller.CappRouteListManageController.viewLifeCycleHistory() end...return is void");
        }
    }

    /**
     * ����ָ����������
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
     * ����ָ����Ŀ��
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
            //���÷������õ�ֵ����paraobj
            obj = (TechnicsRouteListInfo)RequestHelper.request("PersistService", "updateValueInfo", paraclass, paraobj);
            //�����װ��ָ����ҵ������·�߱�ڵ����
            RouteListTreeObject treeObject = new RouteListTreeObject(obj);
            //��ʾ·�߱����º�Ľڵ�
            view.getTreePanel().updateNode(treeObject);
            //���·�߱�ά�����
            view.getTaskPanel().setVisible(false);
            //����·�߱��������ѡ��һ��·�߱�ʱ�������ô˷�������ʾ�㲿����Ϣ��
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
