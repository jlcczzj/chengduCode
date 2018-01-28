/** ���ɳ���TechnicsRegulationsMainController.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  CR1  2009/04/27  Ѧ��   ԭ���Ż�������򡢹�������Ӧʱ��
 *                          �������ͻ������������ݹ�����������ȥ���ݿ�������ˢ������������
 *                          ��ע�����ܲ�������������"���չ��������򡢹���" 
 *  CR2 2009/04/27  �촺Ӣ  ԭ���Ż�ɾ�����յ���Ӧʱ�� 
 *                          �������ͻ����첽�������ɾ�����պ󣬹������ڵ�ͼ���Ϊɾ��ͼ�꣬�ͻ��˶˲����������ȴ���������������������
 *                          ��ע�����ܲ�������������"ɾ������"
 *  CR3 2009/04/28  ��ѧ��  ԭ�򣺹������������ɹ����ŵĹ���
 *                          ����������������������ɹ����ŵķ���formNewPaceNumber()��
 *                          ��ע�������¼��ʶΪCRSS-007
 *  CR4 2009/04/29  ��־��   ԭ�򣺹��չ�̱༭��������Ƴ���ť
 *                          ���������չ�̴ӹ������в�ȥ����ʾ����ɾ����
 *                          ��ע�������¼��ʶΪ"CRSS-012" 
 *  CR5 2009/05/05  �촺Ӣ  �μ�DefectID=2095    
 *  
 *  CR6 2009/05/22  ������   ԭ�򣺹�������Ϣ��û�й���ʱ����Ȼ�����������ɹ����
 *                          �����������Ի�����ʾ�����������ɹ����
 *                          
 *  CR7 2009/06/02  ������   �μ�:������:v4r3FunctionTest;TD��2235 
 *  
 *  CR8 2009/06/03  ��ѧ��   �μ�:������:v4r3FunctionTest;TD��2253 
 *  CR9 2009/08/31  �촺Ӣ   �μ�:������:v4r3FunctionTest;TD��2598
 *  CR10 2009/12/29 ������   TD����2676
 *  CR11 2010/03/08 �ֺ���  ԭ�򣺲μ�TD2745���� 
 *  CR12 2010/04/13 �촺Ӣ  ԭ��:�μ�TD����2253
 *  SS1 ��������Դ�嵥һ���� zhaoqiuying 2013-01-23
 *  SS2 ���ӱ������ӡԤ����� liuyang 2013.2.4
 *  SS3 20130712 ��ż��빤�ղ�Ҫ��ʾ���������ļ� 
 *  SS4 ������Ĺ��������ļ� pante 2014-02-19
 *  SS5 ���������������嵥���͡���λ�����嵥������һ���� liunan 2014-7-28
 *  SS6 TD2646���⣬Ѧ�������޸�  liunan 2014-8-7
 *  SS7 ����������������ӵ�����һ���� pante 2014-09-10
 *  SS8 �������ӹ�װ��ϸ���豸�嵥��ģ���嵥�� guoxiaoliang 2014-08-22
 *  SS9 ���ӳ��غϲ����չ��� ����� 2014-12-15
 *  SS10 �ɶ�������й��򹤲� guoxiaoliang 2016-7-13
 *  SS11 �ɶ��ṹ�������չ��  guoxiaoliang 2016-7-13
 *  SS12 �ɶ��ؼ�������  guoxiaoliang 2016-8-6
 *  SS13 ��ݵ���������Ƽƻ� liunan 2016-11-30
 
 */
package com.faw_qm.cappclients.capp.controller;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.cappclients.beans.processtreepanel.OperationTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.ProcessTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.util.AlreadyCheckedOutException;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappFileFilter;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.CheckedOutByOtherException;
import com.faw_qm.cappclients.capp.util.NotCheckedOutException;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.capp.util.QMThread;
import com.faw_qm.cappclients.capp.view.ConsTemplateSelectJDialog;
import com.faw_qm.cappclients.capp.view.ObjectSelectJDialog;
import com.faw_qm.cappclients.capp.view.TSearchMProcedureJDialog;
import com.faw_qm.cappclients.capp.view.TSearchMTechnicsJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsChangeLocationJFrame;
import com.faw_qm.cappclients.capp.view.TechnicsImportJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsReguRenameJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsRegulationsMainJFrame;
import com.faw_qm.cappclients.capp.view.TechnicsSearchFromEquipmentJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsSearchFromMaterialJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsSearchFromToolJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsSearchJDialog;
import com.faw_qm.cappclients.capp.view.TechnicsStructSearchDialog;
import com.faw_qm.cappclients.capp.view.TemplateSelectJDialog;
import com.faw_qm.cappclients.util.BusinessTreeObject;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.lifecycle.client.view.LifeCycleStateDialog;
import com.faw_qm.lifecycle.client.view.ProjectStateDialog;
import com.faw_qm.lifecycle.client.view.SetLifeCycleStateDialog;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.print.clients.util.TPrintController;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.wip.model.WorkableIfc;

//CCBegin by liunan 2012-03-06 ������������ض����ѷ���״̬�����޸ģ�����������ġ�
import java.util.Enumeration;

import com.faw_qm.users.model.GroupIfc;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserInfo;
//CCEnd by liunan 2012-03-07
import com.faw_qm.util.EJBServiceHelper;

//CCBegin SS13
import java.io.FileWriter;
//CCEnd SS13

/**
 * <p>Title: ���չ��ά�������������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * ��1��20060815Ѧ���޸ģ�������������״̬
 * ��2��20061201�����޸�deleteSelectedObject()������ȥ���Ը��ڵ㸱���Ļ�ã�ɾ��û���õĶ���cto
 * ��3��20061201�����޸�deleteSelectedObject()������ȥ��ȷ��ɾ��ʱ������ж�
 * ��4��20061201�����޸�deleteSelectedObject()������ȥ���Ը��ڵ�ֵ����Ļ��
 *  (5) delete by wangh on 20061206(ȥ��������û�б����õĲ����ͷ���������ɾ��λ���ڳ������ѱ���)
 * ��6��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
 *  ����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
 */

public class TechnicsRegulationsMainController extends TechnicsAction
{

    /**���չ��ά��������*/
    private TechnicsRegulationsMainJFrame view;


    /**�߳���*/
    private ThreadGroup threadGroup = null;


    /**��Ŀ������*/
    protected ItemListener itemListener = null;


    /**����������*/
    protected Vector myListener = null;

    //delete by wangh on 20061206(ȥ��û�����õ��ֶ�)
    /**���߰���*/
    //private QMHelpContext helpContext = null;


    /**����ϵͳ*/
    //private QMHelpSystem helpSystem = null;


    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private int waitingOnCursor = 0;
  //CCBegin by leixiao 2010-5-7 �򲹶���v4r3_p015_20100415 td2745  
    //Begin CR11
    /**�Ƿ�ɾ�����°汾��֧�й���δ���������й��տ���ʶ*/
    private static String isDeleteAllUnlinkedVersions = RemoteProperty.getProperty(
            "isDeleteAllUnlinkedVersions", "true");
   //End CR11
  //CCEnd by leixiao 2010-5-7 �򲹶���v4r3_p015_20100415 td2745  

    //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
    /**���ƶ���Դ�ڵ㣩*/
    private BaseValueInfo copyOriginalInfo;


    /**���ƶ���Դ�ڵ㣩*/
    private CappTreeObject copyOriginalTreeObject;


    //���Ƶ�ԭ�ڵ�Ĺ��տ�BsoID
    private String oriTechnicsID;
    //CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
    /**�������չ�̵Ľ���*/
    private TechnicsSearchJDialog d;


    /**�˵�������ʶ*/
    private final static int CREATE_TECHNICS = 1;
    private final static int CREATE_PROCEDURE_STEP = 2;
    private final static int CREATE_PROCEDURE_PACE = 3;
    private final static int COLLECT_TECHNICS = 4;
    private final static int SEARCH_ALL = 5;
    private final static int REFRESH = 6;
    private final static int VIEW = 7;
    private final static int UPDATE = 8;
    private final static int DELETE = 9;
    private final static int FORM_NEW_STEP_NUM = 10;
    private final static int PRINT_BROWSE = 11;
    private final static int RENAME = 12;
    private final static int CHANGE_LOCATION = 13;
    private final static int SAVE_AS = 14;
    private final static int USAGE_MODELTECHNICS = 15;
    private final static int USAGE_MODELPROCEDURE = 16;
    private final static int BUILD_MODELTECHNICS = 17;
    private final static int BUILD_MODELPROCEDURE = 18;
    private final static int COPY = 19;
    private final static int PASTE = 20;
    private final static int CHECK_IN = 21;
    private final static int CHECK_OUT = 22;
    private final static int CANCEL_CHECK_OUT = 23;
    private final static int REVISE = 24;
    private final static int VIEW_VERSION_HISTORY = 25;
    private final static int VIEW_ITERATION_HISTORY = 26;

    //CR3 begin
    private final static int FORM_NEW_PACE_NUM = 27;
    //CR3 end
    //private final static int COMMIT_LIFECYCLE = 27;
    private final static int AFRESH_APPOINT_LIFECYCLE = 28;
    private final static int VIEW_LIFECYCLE_HISTORY = 29;
    private final static int AFRESH_APPOINT_PROJECT = 30;
    private final static int PART_CAPP_DERIVE = 31;
    private final static int MACHINE_CAPP_DERIVE = 32;
    private final static int SEARCH_IN_EQUIPMENT = 33;
    private final static int SEARCH_IN_TOOLS = 34;
    private final static int SEARCH_IN_MATERIAL = 35;
    private final static int SEARCH_TECHNICS = 36;
    private final static int HELP_TECHNICS_MANAGER = 37;
    private final static int IMPORT = 41;
    private final static int EXPORT = 42;
    private final static int CREATE_TOOL = 43;
    private final static int SEARCH_TREE_OBJECT = 44;
    private final static int REFRESH_TECHNICS = 45;
    private final static int SET_LIFECYCLE_STATE = 46;
    private final static int CONFIGURE_CRITERION = 47;
    private final static int EXPORTALL = 48;
  //CCBegin SS1
    private final static int ITEM_JIAJU = 50;
    private final static int ITEM_WANNENG = 51;
    private final static int ITEM_EQUIP = 52;
    private final static int ITEM_MOJU = 53;
    private final static int ITEM_DAOJU = 54;
    private final static int ITEM_JIAFUJU = 55;
    private final static int ITEM_ZHUANGPEI = 56;
  //CCEnd SS1
    
    //CCBegin SS4
    private final static int ITEM_ZCJJ = 57;
    private final static int ITEM_ZCWNGJ = 58;
    private final static int ITEM_ZCMJ = 59;
    private final static int ITEM_ZCDJ = 60;
    private final static int ITEM_ZCJFJ = 61;
    private final static int ITEM_ZCLJ = 62;
    private final static int ITEM_ZCWNLJ = 63;
    private final static int ITEM_ZCJIANJ = 64;
    private final static int ITEM_ZCSB = 65;
    private final static int ITEM_ZCJIANFJ = 66;
    //CCEnd SS4
    
    //CCBegin SS8
    private final static int ITEM_CTTOOL = 70;
    private final static int ITEM_CTSHEBEI = 71;
    private final static int ITEM_CTMOJU = 72;
    //CCEnd SS8
    
    //CCBegin SS5
    private final static int ITEM_WANNENGLIANGJU = 67;
    private final static int ITEM_GONGWEIQIJU = 68;
    //CCEnd SS5
    //    CCBegin SS7
    private final static int ITEM_ZCDFJ = 69;
//    CCEnd SS7
//    CCBegin SS9
    private final static int CTCOLLECT_TECHNICS = 73;
//    CCEnd SS9
    //CCBegin SS10
    private final static int CHECKALL = 80;
    //CCEnd SS10
    
    //CCBegin SS11
    private final static int STRUCT_SEARCH_TECHNICS = 81;
    //CCEnd SS11
    
    //CCBegin SS12
    private final static int FINDMAINSTEP=82;
    //CCEnd SS12
    
    //delete by wangh on 20061206(ȥ��û�����õ��ֶ�)
    //private Vector threadVec = new Vector();
    //20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷����ϰ�סCtrl�����ѡ��2�������Ĺ���
    //����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
    //list���ڴ��Ҫ���Ƶ�Դ�ڵ���Ϣ
    private ArrayList list = new ArrayList();
    
    private Vector vector = new Vector();//CR1
    
    
    /**
     * ���캯��
     * @param mainFrame ���չ��ά��������
     */
    public TechnicsRegulationsMainController(TechnicsRegulationsMainJFrame
                                             mainFrame)
    {
        this.view = mainFrame;
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void jbInit()
    {
        this.threadGroup = QMCt.getContext().getThreadGroup();
        setConfigSpecCommand();
    }


    /**
     * ����ˢ�·��������
     */
    class MainRefreshListener implements RefreshListener
    {
        //ʵ�ֽӿ��еĳ��󷽷�fefreshObject()
        public void refreshObject(RefreshEvent refreshevent)
        {
            Object obj = refreshevent.getTarget();

            int i = refreshevent.getAction();

            if (obj instanceof QMTechnicsInfo)
            {
                switch (i)
                {
                    case RefreshEvent.CREATE:
                    { // '\0'
                        //addExplorerPart( (QMPartInfo) obj);
                        return;
                    }

                    case RefreshEvent.UPDATE:
                    { // '\001'
                        refreshExplorerPart((QMTechnicsInfo) obj);
                        return;
                    }
                    case RefreshEvent.DELETE:
                    { // '\002'
                        //deleteExplorerPart( (QMPartInfo)obj );
                        return;
                    }
                }
            }
        }

        MainRefreshListener()
        {
        }
    }


    /**
     * <p>Title: �ڲ������߳�</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: һ������</p>
     * @author ����
     * @version 1.0
     */
    class WorkThread extends QMThread
    {
        int myAction;
        QMTechnicsInfo myTechnics;
        QMTechnicsMasterInfo myTechnicsMaster;

        public WorkThread(ThreadGroup threadgroup, int action)
        {
            super();
            TechnicsRegulationsMainController.this.threadGroup = threadgroup;
            myAction = action;
        }

        public WorkThread(ThreadGroup threadgroup, int action,
                          QMTechnicsInfo technics)
        {
            super();
            TechnicsRegulationsMainController.this.threadGroup = threadgroup;
            myAction = action;
            myTechnics = technics;
        }

        public WorkThread(ThreadGroup threadgroup, int action,
                          QMTechnicsMasterInfo master)
        {
            super();
            TechnicsRegulationsMainController.this.threadGroup = threadgroup;
            this.myAction = action;
            this.myTechnicsMaster = master;
        }
       
        
        //CCEnd 

        public void run()
        {
            try
            {
                //view.setCursor(Cursor.WAIT_CURSOR);
                //  synchronized(TechnicsRegulationsMainController.this.cursorLock)
                //{
                if (waitingOnCursor == 0)
                {
                    //  view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                }
                waitingOnCursor++;

                switch (myAction)
                {
                    case CREATE_TOOL:
                        createObject();
                        break;

                    case CREATE_TECHNICS:
                        createTechnics();
                        break;

                    case CREATE_PROCEDURE_STEP:
                        createProcedureStep();
                        break;

                    case CREATE_PROCEDURE_PACE:
                        createProcedurePace();
                        break;

                    case COLLECT_TECHNICS:
                        collectTechnics();
                        break;

//                        CCBegin SS9
                    case CTCOLLECT_TECHNICS:
                        ctCollectTechnics();
                        break;
//                        CCEnd SS9
                        
                    case IMPORT:
                        importTechnics();
                        break;

                    case EXPORT:
                        ExportWorkThread exportWorkThread = new
                                ExportWorkThread();
                        exportWorkThread.start();
                        break;
                    case EXPORTALL:
                        ExportAllWorkThread exportAllWorkThread = new
                                ExportAllWorkThread();
                        exportAllWorkThread.start();
                        break;

                    case SEARCH_ALL:
                        searchAll();
                        break;

                    case REFRESH:
                    {
                    	//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2676
                    	CappTreeNode node = view.getSelectedNode();//begin CR10
                    	view.closeNode(node);//end CR10
                    	//CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2676
                        refreshSelectedObject();
                        break;
                    }

                    case VIEW:
                        viewSelectedObject();
                        break;

                    case UPDATE:
                        updateSelectedObject();
                        break;

                    case DELETE:
                        deleteSelectedObject();
                        break;

                    case FORM_NEW_STEP_NUM: //�������ɹ����

                        FormNewStepThread thread = new FormNewStepThread();
                        thread.start();
                        break;
                   
                    //CR3 begin   
					case FORM_NEW_PACE_NUM: // �������ɹ�����
	                    
						FormNewPaceThread thread1 = new FormNewPaceThread();
						thread1.start();
						break;
					//CR3 end
					//CCBegin SS10	
					case CHECKALL:
						checkAllStepAndPace();
						break;
                   //CCEnd SS10
					
				  //CCBegin SS12	
					case FINDMAINSTEP:
						findMainStep();
						break;
                   //CCEnd SS12
						
						
                    case PRINT_BROWSE: //��ӡԤ��
                        browsePrintObject();
                        break;

                    case CHANGE_LOCATION: //���Ĵ洢λ��
                        changeTechnicsLocation();
                        break;

                    case RENAME: //���������տ�
                        renameTechnics();
                        break;

                    case SAVE_AS:
                        saveAsTechnics(); //���տ����Ϊ
                        break;

                    case USAGE_MODELTECHNICS: //Ӧ�õ��͹���
                        usageModelTechnics();
                        break;

                    case USAGE_MODELPROCEDURE: //Ӧ�õ��͹���
                        usageModelProcedure();
                        break;

                    case BUILD_MODELTECHNICS: //���ɵ��͹���
                        buildModelTechnics();
                        break;

                    case BUILD_MODELPROCEDURE: //���ɵ��͹���
                        buildModelProcedure();
                        break;

                    case COPY: //����
                        copy();
                        break;

                    case PASTE: //ճ��
                        paste();
                        break;

                    case CHECK_IN: //����
                        checkIn();
                        break;

                    case CHECK_OUT: //���
                        checkOut();
                        break;

                    case CANCEL_CHECK_OUT: //�������
                        cancelCheckOut();
                        break;

                    case REVISE: //�޶�
                        revise();
                        break;

                    case VIEW_VERSION_HISTORY: //�鿴�汾��ʷ
                        viewVersionHistory();
                        break;

                    case VIEW_ITERATION_HISTORY: //�鿴������ʷ
                        viewIterationHistory();
                        break;

                    case SET_LIFECYCLE_STATE:
                        setLifeCycleState(); //������������״̬
                        break;

                    case AFRESH_APPOINT_LIFECYCLE: //����ָ����������
                        afreshAppointLifeCycle();
                        break;

                    case VIEW_LIFECYCLE_HISTORY: //�鿴����������ʷ
                        viewLifeCycleHistory();
                        break;

                    case AFRESH_APPOINT_PROJECT: //����ָ��������
                        afreshAppointProject();
                        break;

                    case PART_CAPP_DERIVE: //����幤������
                        partCappDerive();
                        break;

                    case MACHINE_CAPP_DERIVE: //�ӹ�·�߹�������
                        machineCappDerive();
                        break;

                    case CONFIGURE_CRITERION: //���ù淶
                        configureCriterion();
                        break;
                    case SEARCH_IN_EQUIPMENT: //���豸��������
                        searchInEquipment();
                        break;

                    case SEARCH_IN_TOOLS: //����װ��������
                        searchInTools();
                        break;

                    case SEARCH_IN_MATERIAL: //��������������
                        searchInMaterial();
                        break;

                    case SEARCH_TECHNICS: //�������չ��
                        searchTechnics();
                        break;
                        
                        //CCBegin SS11
                    case STRUCT_SEARCH_TECHNICS:
    					searchStructTechnics();
    					break;
                        //CCEnd SS11

                    case HELP_TECHNICS_MANAGER: //���չ�̹���
                        helpTechnicsManager();
                        break;
                    case SEARCH_TREE_OBJECT:
                        searchTreeObject();
                    case REFRESH_TECHNICS:
                        refreshQMTechnics(myTechnics);
                      //CCBegin SS1
                    case ITEM_JIAJU:
                        schedule("�о���ϸ��");
                        break;
                    case ITEM_WANNENG:
                        schedule("���ܹ����嵥");
                        break;
                    //CCBegin SS5
                    case ITEM_WANNENGLIANGJU:
                        schedule("���������嵥");
                        break;
                    case ITEM_GONGWEIQIJU:
                        schedule("��λ�����嵥");
                        break;
                    //CCEnd SS5
                    case ITEM_EQUIP:
                        schedule("�豸�嵥");
                        break;
                    case ITEM_MOJU:
                        schedule("ĥ��һ����");
                        break;
                    case ITEM_DAOJU:
                        schedule("����һ����");
                        break;
                    case ITEM_JIAFUJU:
                        schedule("�и���һ����");
                        break;
                    case ITEM_ZHUANGPEI:
                        schedule("װ�乤��һ����");
                        break;
                      //CCEnd SS1
                      //CCBegin SS4
                    case ITEM_ZCJJ:
                        schedule("��ݼо���ϸ��");
                        break;
                    case ITEM_ZCWNGJ:
                        schedule("������ܹ�����ϸ��");
                        break;
                    case ITEM_ZCMJ:
                        schedule("���ĥ��һ����");
                        break;
                    case ITEM_ZCDJ:
                        schedule("��ݵ���һ����");
                        break;
                        //                        CCBegin SS7
                    case ITEM_ZCDFJ:
                        schedule("��ݵ�����һ����");
                        break;
//                        CCEnd SS7
                    case ITEM_ZCJFJ:
                        schedule("��ݼи���һ����");
                        break;
                    case ITEM_ZCLJ:
                        schedule("�������һ����");
                        break;
                    case ITEM_ZCWNLJ:
                        schedule("�������������ϸ��");
                        break;
                    case ITEM_ZCJIANJ:
                        schedule("��ݼ����ϸ��");
                        break;
                    case ITEM_ZCSB:
                        schedule("����豸�嵥");
                        break;
                    case ITEM_ZCJIANFJ:
                        schedule("��ݼ츨��һ����");
                        break;
                      //CCEnd SS4
                      
                    //CCBegin SS8
                    case ITEM_CTTOOL:
                        schedule("���ع�װ��ϸ��");
                        break;
                    case ITEM_CTSHEBEI:
                        schedule("�����豸�嵥");
                        break;
                    case ITEM_CTMOJU:
                        schedule("����ģ���嵥");
                        break;
                      //CCEnd SS8
                }
                // }
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            finally
            {

            }
        } //end run()

    } //end WorkThread


    /**
     * ��õ�ǰѡ��Ķ���(����ǩ�ڵ�)��
     * ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     * @return ��ǰѡ��Ķ���
     * @throws QMRemoteException
     */
    /*  public CappTreeObject getSelectedObject() throws QMRemoteException
      {

        if(view.getSelectedObject() == null)
        {
          String message = QMMessage.getLocalizedMessage(
              RESOURCE,
              CappLMRB.NOT_SELECT_OBJECT,
              null);
          throw new QMRemoteException(message);
        }
        else
        {
          return view.getSelectedObject();
        }
      }*/

    /**
     * ���������ϡ��½�����ť����
     */
    public void processCreateObjectCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_TOOL);
        work.start();
    }


    /**
     * ����"�½���������Ϣ"����
     */
    public void processCreateTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_TECHNICS);
        work.start();
    }


    /**
     * ����"�½�����"����
     */
    public void processCreateProcedureStepCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         CREATE_PROCEDURE_STEP);
        work.start();
    }


    /**
     * ����"�½�����"����
     */
    public void processCreateProcedurePaceCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         CREATE_PROCEDURE_PACE);
        work.start();
    }


    /**
     * ����"�ϲ�"����
     */
    public void processCollectTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COLLECT_TECHNICS);
        work.start();
    }

//    CCBegin SS9
    /**
     * ����"�ϲ�"����
     */
    public void processCTCollectTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CTCOLLECT_TECHNICS);
        work.start();
    }
//CCEnd SS9
    
    /**
     * �������빤�չ�̡�����
     */
    public void processImportCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), IMPORT);
        work.start();
    }


    /**
     *  �����������չ�̡�����
     */
    public void processExportCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXPORT);
        work.start();
    }


    /**
     *  ��������ȫ�����չ�̡�����
     */
    public void processExportAllCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXPORTALL);
        work.start();
    }


    /**
     * ����"����"����
     */
    public void processSearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_ALL);
        work.start();
    }


    /**
     * ����"ˢ��"����
     */
    public void processRefreshCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESH);
        work.start();
    }


    /**
     * ����"�鿴"����
     */
    public void processViewCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    //CCBegin SS10
    /**
     * ������й��򹤲�
     */
    public void processCheckAllCommond() {
    	
		WorkThread work = new WorkThread(getThreadGroup(), CHECKALL);
		work.start();
	}
    //CCEnd SS10
    
    //CCBegin SS12
    public void processFindMainStepCommond (){
    	
		WorkThread work = new WorkThread(getThreadGroup(), FINDMAINSTEP);
		work.start();
	}
    
    //CCEnd SS12
    
    
    /**
     * ����"����"����
     */
    public void processUpdateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }
    
    //CCBegin SS11
    
    public void processStructSearchTechnicsCommand() {
		WorkThread work = new WorkThread(getThreadGroup(),
				STRUCT_SEARCH_TECHNICS);
		work.start();
	}
    //CCCEnd SS11


    /**
     * ����"ɾ��"����
     */
    public void processDeleteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }


    /**
     * ����"�������ɹ����"����
     */
    public void processFormNewStepnumCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), FORM_NEW_STEP_NUM);
        work.start();
    }
   
    //CR3 begin
    /**
	 * ����"�������ɹ�����"����
	 */
	
	public void processFormNewPacenumCommand()
	{
		WorkThread work = new WorkThread(getThreadGroup(), FORM_NEW_PACE_NUM);
		work.start();
	}
    //CR3 end

    /**
     * ����"��ӡԤ��"����
     */
    public void processPrintBrowseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PRINT_BROWSE);
        work.start();
    }


    /**
     * ����"������"����
     */
    public void processRenameCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RENAME);
        work.start();
    }


    /**
     * ����"���Ĵ洢λ��"����
     */
    public void processChangeLocationCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHANGE_LOCATION);
        work.start();
    }


    /**
     * ����"���Ϊ"����
     */
    public void processSaveAsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SAVE_AS);
        work.start();
    }


    /**
     * ����"Ӧ�õ��͹���"����
     */
    public void processUsageModelTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), USAGE_MODELTECHNICS);
        work.start();
    }


    /**
     * ����"Ӧ�õ��͹���"����
     */
    public void processUsageModelProcedureCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), USAGE_MODELPROCEDURE);
        work.start();
    }


    /**
     * ����"���ɵ��͹���"����
     */
    public void processBuildModelTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_MODELTECHNICS);
        work.start();
    }


    /**
     * ����"���ɵ��͹���"����
     */
    public void processBuildModelProcedureCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_MODELPROCEDURE);
        work.start();
    }


    /**
     * ����"����"����
     */
    public void processCopyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COPY);
        work.start();
    }


    /**
     * ����"ճ��"����
     */
    public void processPasteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PASTE);
        work.start();
    }


    /**
     * ����"����"����
     */
    public void processCheckInCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECK_IN);
        work.start();
    }


    /**
     * ����"���"����
     */
    public void processCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECK_OUT);
        work.start();
    }


    /**
     * ����"�������"����
     */
    public void processCancelCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CANCEL_CHECK_OUT);
        work.start();
    }


    /**
     * ����"�޶�"����
     */
    public void processReviseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REVISE);
        work.start();
    }


    /**
     * ����"�鿴�汾��ʷ"����
     */
    public void processViewVersionHistoryCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_VERSION_HISTORY);
        work.start();
    }


    /**
     * ����"�鿴������ʷ"����
     */
    public void processViewIterationHistoryCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         VIEW_ITERATION_HISTORY);
        work.start();
    }


    /**
     * ����"�ύ"����
     */
    public void processSetLifeCycleStateCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SET_LIFECYCLE_STATE);
        work.start();
    }


    /**
     * ����"����ָ����������"����
     */
    public void processAfreshAppointLifecycleCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         AFRESH_APPOINT_LIFECYCLE);
        work.start();
    }


    /**
     * ����"�鿴����������ʷ"����
     */
    public void processViewLifecycleHistoryCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         VIEW_LIFECYCLE_HISTORY);
        work.start();
    }


    /**
     * ����"����ָ��������"����
     */
    public void processAfreshAppointProjectCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         AFRESH_APPOINT_PROJECT);
        work.start();
    }


    /**
     * ����"����幤������"����
     */
    public void processPartCappDeriveCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PART_CAPP_DERIVE);
        work.start();
    }


    /**
     * ����"�ӹ�·�߹�������"����
     */
    public void processMachineCappDeriveCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), MACHINE_CAPP_DERIVE);
        work.start();
    }


    /**
     * ����"���ù淶"����
     */
    public void processConfigureCriterionCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CONFIGURE_CRITERION);
        work.start();
    }


    /**
     * ����"���豸��������"����
     */
    public void processSearchInEquipmentCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_IN_EQUIPMENT);
        work.start();
    }


    /**
     * ����"����װ��������"����
     */
    public void processSearchInToolsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_IN_TOOLS);
        work.start();
    }


    /**
     * ����"��������������"����
     */
    public void processSearchInMaterialCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_IN_MATERIAL);
        work.start();
    }


    /**
     * ����"�������չ��"����
     */
    public void processSearchTechnicsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_TECHNICS);
        work.start();
    }


    /**
     * ����"���չ�̹���"����
     */
    public void processHelpManagerCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                                         HELP_TECHNICS_MANAGER);
        work.start();
    }
  //CCBegin SS1    
    public void jMItemjiaju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_JIAJU);
        work.start();
    }
    public void jMItemwanneng()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_WANNENG);
        work.start();
    }
    //CCBegin SS5
    public void jMItemwannengliangju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_WANNENGLIANGJU);
        work.start();
    }
    public void jMItemgongweiqiju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_GONGWEIQIJU);
        work.start();
    }
    //CCEnd SS5
    public void jMItemequip()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_EQUIP);
        work.start();
    }
    public void jMItemmoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_MOJU);
        work.start();
    }
    public void jMItemdaoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_DAOJU);
        work.start();
    }
    public void jMItemjiafuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_JIAFUJU);
        work.start();
    }
    public void jMItemzhuangpei()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZHUANGPEI);
        work.start();
    }
  // CCEnd SS1
    
    //CCBegin SS4
    public void jMItemzcjiaju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJJ);
        work.start();
    }
    public void jMItemzcwangong()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCWNGJ);
        work.start();
    }
    public void jMItemzcmoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCMJ);
        work.start();
    }
    public void jMItemzcdaoju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCDJ);
        work.start();
    }
    //    CCBegin SS7
    public void jMItemzcdaofuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCDFJ);
        work.start();
    }
//    CCEnd SS7
    public void jMItemzcjiafuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJFJ);
        work.start();
    }
    public void jMItemzcliangju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCLJ);
        work.start();
    }
    public void jMItemzcwanliang()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCWNLJ);
        work.start();
    }
    public void jMItemzcjianju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJIANJ);
        work.start();
    }
    public void jMItemzcsb()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCSB);
        work.start();
    }
    public void jMItemzcjianfuju()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ITEM_ZCJIANFJ);
        work.start();
    }
    //CCEnd SS4
    
    //CCBegin SS8
    public void jMItemCtTool(){
    	
    	  WorkThread work = new WorkThread(getThreadGroup(), ITEM_CTTOOL);
          work.start();
    }
    
    public void jMItemCtSheBei(){
    	
  	  WorkThread work = new WorkThread(getThreadGroup(), ITEM_CTSHEBEI);
        work.start();
  }
    
    public void jMItemCtMoJu(){
    	
  	  WorkThread work = new WorkThread(getThreadGroup(), ITEM_CTMOJU);
        work.start();
  }
    //CCEnd SS8
    
    
    /**
     * ����"����"��ť����
     */
    public void processSearchTreeObjectCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_TREE_OBJECT);
        work.start();
    }


    /**
     * �½����տ�
     */
    private void createTechnics()
            throws QMException
    {
    	
    	view.getContentJPanel().setTechnicsMode(1);
    	
    	
    }


    /**
     * �½�����
     */
    private void createProcedureStep()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedureStep() begin...");
        }
        try
        {

            CappTreeNode selectedNode = view.getSelectedNode();
            CappTreeObject obj = selectedNode.getObject();
            CappTreeNode parentTechnicsNode = view.getParentTechnicsNode(
                    selectedNode);
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//              delete by wangh on 20061206(ȥ��û�����õ��ֶ�)
               // BaseValueInfo info = (BaseValueInfo) obj.getObject();
                //�ж��Ƿ������½�����
                BaseValueInfo newinfo = isAllowedCreateProcedure(selectedNode
                        );

                //���û�б���ǰ�û�����������ڹ������ϼ��У��������ڼ���״̬��������
                //if (!CheckInOutCappTaskLogic.isCheckedOutByUser((WorkableIfc)
                //      newinfo)
                //   && CheckInOutCappTaskLogic.isInVault((WorkableIfc) newinfo))
                if (CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc)
                        newinfo))

                {
                    Object[] identity =
                            {
                            getIdentity(newinfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }

                    newinfo = checkOutParentNode(selectedNode,
                                                 parentTechnicsNode);
                }

                //������ѡ��Ķ��󣨸�����
                view.getContentJPanel().setSelectedObject(newinfo);
                CappTreeNode node = view.findNode(new TechnicsTreeObject(
                        newinfo));
                view.getContentJPanel().setSelectedNode(node);
                //���ѡ��Ķ����ǹ���������ԭ�������ø����ڵ�
                /* if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info) &&
                 CheckInOutCappTaskLogic.isInVault((WorkableIfc) info)) //�ڹ������ϼ�
                 {
                     if (verbose)
                     {
                         System.out.println("ѡ��Ķ����ǹ���������ԭ����");
                     }

                     info = (BaseValueInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy(
                             (WorkableIfc) info);
                     CappTreeNode node = view.findNode(new TechnicsTreeObject(
                             info));
                     view.getContentJPanel().setSelectedNode(node);
                 }
                 else
                 {
                     if (verbose)
                     {
                         System.out.println("ѡ��Ķ����ǹ�������,���δ�������");
                     }
                     view.getContentJPanel().setSelectedNode(selectedNode);
                 }*/

                view.getContentJPanel().setStepMode(1);
                view.setCursor(Cursor.getDefaultCursor());
            }

        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setCursor(Cursor.getDefaultCursor());
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedureStep() end...return is void");
        }
    }


    /**
     * �ж��Ƿ�������ָ���Ķ������½�����򹤲���
     * ֻ���ڵ�ǰָ���Ķ����ѱ����������δ��ִ�м������������£��������½��乤�򣨲�����
     * @param node ָ���Ķ��󣨹��տ������򡢹�����
     * @return ָ������Ĺ������������ָ��������δ��ִ�м����������ֱ�ӷ���ָ������
     */
    private BaseValueInfo isAllowedCreateProcedure(CappTreeNode node)
            throws
            QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isAllowedCreateProcedure() begin...");
        }
        BaseValueInfo info = (BaseValueInfo) node.getObject().getObject();
        if (((WorkableIfc) info).getWorkableState().equals("c/i"))
        {
            if (((WorkableIfc) info).getOwner() != null &&
                !((WorkableIfc) info).getOwner().equals(TechnicsRegulationsMainJFrame.currentUser.
                    getBsoID()))
            {
                Object[] objs =
                        {
                        getIdentity(info)};
                String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                        "creatByOther", objs);
                throw new QMRemoteException(message1);

            }
            else
            {
                return info;
            }
        }

        //if 1:����������û����,��ʾ"�Ѿ������˼��!"
        if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc) info,
                TechnicsRegulationsMainJFrame.currentUser))
        {
            Object[] objs =
                    {
                    getIdentity(info)};
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
            throw new QMRemoteException(message1);
        } //end if 1
        //2:�������ǰ�û����
        else
        {

            //����Ǳ���ǰ�û���������ǹ�������,�ͻ�ù�������
            if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info))
            {
                info = (BaseValueInfo) CheckInOutCappTaskLogic.getWorkingCopy(
                        (WorkableIfc) info);
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isAllowedCreateProcedure() end...return : "
                               + info);
        }
        return info;
    }


    /**
     * �½�����
     */
    private void createProcedurePace()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedurePace() begin...");
        }
        try
        {
            CappTreeNode selectedNode = view.getSelectedNode();
            CappTreeNode parentTechNode = view.getParentTechnicsNode(
                    selectedNode);
            CappTreeObject obj = selectedNode.getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BusinessTreeObject selectedObject = (BusinessTreeObject) obj;
                QMProcedureInfo procedureinfo = (QMProcedureInfo)
                                                selectedObject.
                                                getObject();
                //����й������գ��������ڸù����´������������������ڸù����´�������
                if (procedureinfo.getRelationCardBsoID() != null
                    && !(procedureinfo.getRelationCardBsoID()).equals(""))
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.CAN_NOT_CREATE_PACE, null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, message, title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return;
                }

                //�ж��Ƿ������½�����
                BaseValueInfo newinfo = isAllowedCreateProcedure(selectedNode);
                //���û�б���ǰ�û�����������ڹ������ϼ��У��������ڼ���״̬��������
                if (CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc)
                        newinfo))

                {
                    Object[] identity =
                            {
                            getIdentity(newinfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        view.setCursor(Cursor.getDefaultCursor());
                        return;
                    }

                    newinfo = checkOutParentNode(selectedNode,
                                                 parentTechNode);
                }

                //������ѡ��Ķ��󣨸�����
                view.getContentJPanel().setSelectedObject(newinfo);

                //���ѡ��Ķ����ǹ���������ԭ�������ø����ڵ�
//                if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)
//                        newinfo) &&
//                    CheckInOutCappTaskLogic.isInVault((WorkableIfc) newinfo)) //�ڹ������ϼ�
//                {
//                    if (verbose)
//                    {
//                        System.out.println("ѡ��Ķ����ǹ���������ԭ����");
//                    }
//                    newinfo = (QMProcedureInfo) CheckInOutCappTaskLogic.
//                              getWorkingCopy(
//                            (WorkableIfc) newinfo);
//                }

                //{{������ѡ������ڵ�
                CappTreeNode node;
                if (((QMProcedureInfo) newinfo).getIsProcedure())
                {
                    node = view.findNode(new StepTreeObject(newinfo));
                }
                else
                {
                    node = view.findNode(new OperationTreeObject(newinfo));
                }
                view.getContentJPanel().setSelectedNode(node);
                view.getContentJPanel().setPaceMode(1);
                view.setCursor(Cursor.getDefaultCursor());

            }
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setCursor(Cursor.getDefaultCursor());
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.createProcedurePace() end...return is void");
        }
    }


    /**
     * ���빤�չ��
     */
    private void importTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.importTechnics() begin...");
        }
        TechnicsImportJDialog d = new TechnicsImportJDialog();
        d.setViewLocation();
        d.setVisible(true);
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.importTechnics() end...return is void");
        }
    }

    class ExportWorkThread extends Thread
    {
        public void run()
        {

            try
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                exportTechnics();
                view.setCursor(Cursor.getDefaultCursor());
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.
                                              INFORMATION_MESSAGE);
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());

            }

        }
    }


    class ExportAllWorkThread extends Thread
    {
        public void run()
        {

            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try
            {
                exportAllTechnics();
            }
            catch (QMRemoteException ex)
            {
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.
                                              INFORMATION_MESSAGE);

            }
            view.setCursor(Cursor.getDefaultCursor());

        }
    }


    /**
     * �������չ��
     */
    private void exportTechnics()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() begin...");

        }
        CappTreeNode[] nodes = view.getProcessTreePanel().getSelectedNodes();
        if (nodes != null && nodes.length != 0)
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ArrayList list = new ArrayList();
            CappTreeObject treeobj = null;
            for (int i = 0, l = nodes.length; i < l; i++)
            {
                treeobj = view.update(nodes[i].getObject());
                QMFawTechnicsIfc tech = (QMFawTechnicsIfc) treeobj.
                                        getObject();
                list.add(tech);
            }
            //�ļ�ѡ����
            JFileChooser chooser = new JFileChooser();
            //�����ļ�ѡȡģʽ���ļ���·��
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            //���ò��ɶ�ѡ
            chooser.setMultiSelectionEnabled(false);
            chooser.setCurrentDirectory(new File("D:\\"));
            //�ļ�ѡ����
            CappFileFilter filter = new CappFileFilter();
            chooser.setFileFilter(filter);
            //�����桱ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
            int state = chooser.showSaveDialog(null);

            //���ѡ����ļ�
            File file = chooser.getSelectedFile();

            //���ѡ������׼��ť,����ϵͳ�Ĺ��չ�����ݣ����չ���е�����Ϣ�����򡢹����ȣ���
            //��ҵ�����CAPP-BR014��������ָ��·�������ֵ��ļ��С�
            if (file != null && state == JFileChooser.APPROVE_OPTION)
            {
                chooser.setVisible(false);
                view.startProgress();
                Class[] paraclass =
                        {
                        ArrayList.class, String.class,
                        Boolean.TYPE};
                Object[] paraobj =
                        {
                        list, file.getPath(), new Boolean(false)};
                useServiceMethod("StandardCappService", "exportTechnics",
                                 paraclass, paraobj);
                view.stopProgress();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "exportSuccessful", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);

                //���÷���˷�����ִ�е���
                //������ɺ�ϵͳ�����ɹ���Ϣ:�����Ѿ��ɹ���
                //�����������ԭ������˴����ԭ���µ����޷���ɣ�ϵͳӦ������Ӧ����ʾ��Ϣ:����
                //���ϻ�������˳��ִ��󣬵����޷�����
            }
            view.setCursor(Cursor.getDefaultCursor());

        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() end...return is void");
        }
    }


    /**
     * �������չ��
     */
    private void exportAllTechnics()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() begin...");

        }

        JFileChooser chooser = new JFileChooser();
        //�����ļ�ѡȡģʽ���ļ���·��
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //���ò��ɶ�ѡ
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File("D:\\"));
        //�ļ�ѡ����
        CappFileFilter filter = new CappFileFilter();
        chooser.setFileFilter(filter);
        //�����桱ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
        int state = chooser.showSaveDialog(null);

        //���ѡ����ļ�
        File file = chooser.getSelectedFile();
        //���ѡ������׼��ť,����ϵͳ�Ĺ��չ�����ݣ����չ���е�����Ϣ�����򡢹����ȣ���
        //��ҵ�����CAPP-BR014��������ָ��·�������ֵ��ļ��С�
        if (file != null && state == JFileChooser.APPROVE_OPTION)
        {
            chooser.setVisible(false);
            view.startProgress();
            Class[] paraclass =
                    {
                    String.class};
            Object[] paraobj =
                    {
                    file.getPath()};
            useServiceMethod("StandardCappService", "exportAllTechnics",
                             paraclass, paraobj);
            view.stopProgress();
            //���÷���˷�����ִ�е���
            //������ɺ�ϵͳ�����ɹ���Ϣ:�����Ѿ��ɹ���
            //�����������ԭ������˴����ԭ���µ����޷���ɣ�ϵͳӦ������Ӧ����ʾ��Ϣ:����
            //���ϻ�������˳��ִ��󣬵����޷�����
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    "exportSuccessful", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.exportTechnics() end...return is void");
        }
    }


    /**
     * ����
     */
    private void searchAll()
    {
        try
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            TechnicsSearchJDialog d = new TechnicsSearchJDialog(view);
            d.addDefaultListener();
            d.setVisible(true);
            view.setCursor(Cursor.getDefaultCursor());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }

    }

    public void refreshSelectedObject()

    {
        CappTreeNode node = view.getSelectedNode();
        try
        {
            refreshSelectedObject(node);
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setCursor(Cursor.getDefaultCursor());

        }
    }


    /**
     * ˢ����ѡ��Ķ���
     */
    public void refreshSelectedObject(CappTreeNode node)
            throws QMException
    {
        if (verbose)

        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.refreshSelectedObject() begin...");
        }
        //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 
        view.closeNode(node);//CR10
        //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 
        BaseValueInfo info = (BaseValueInfo) node.getObject().getObject();//Begin CR1
		if (vector.contains(info.getBsoID()))
		{
			JOptionPane.showMessageDialog(view, "�˹�������ɾ�������ܶ��������������", "���棡",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}//End CR1
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Collection vec = (Collection) useServiceMethod("StandardCappService",
                "doAction",
                new Class[]
                {BaseValueIfc.class}
                ,
                new Object[]
                {node.getObject().getObject()});

        if (vec != null && vec.size() == 2)
        {
            BaseValueInfo latestinfo = (BaseValueInfo) vec.toArray()[0];
            //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 
             //Begin CR10
            UserIfc userIfc = this.getCurrentUser();
            if((((WorkableIfc)latestinfo).getWorkableState()).equals("c/o"))
            {
                if(userIfc.getUsersName().equals("Administrator") || 
                        userIfc.getBsoID().equals(((QMTechnicsInfo)latestinfo).getLocker()))
                {
                    latestinfo = (BaseValueInfo)CheckInOutCappTaskLogic.getWorkingCopy((WorkableIfc)latestinfo);
                }
            }//End CR10
            //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 
            CappTreeObject object = null;
            if (latestinfo instanceof QMTechnicsIfc)
            {
                object = new TechnicsTreeObject(latestinfo);
                //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 
                node.setObject(object);//CR10
                //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 
            }
            else if (latestinfo instanceof QMProcedureIfc)
            {
                if (((QMProcedureIfc) latestinfo).getIsProcedure())
                {
                    object = new StepTreeObject(latestinfo);
                }
                else
                {
                    object = new OperationTreeObject(latestinfo);
                }
            }

            if (object != null)
            {
                ((ProcessTreeObject) object).setParentID(((ProcessTreeObject)
                        node.getObject()).getParentID());
                view.getProcessTreePanel().updateNode(object);
            }
            view.enableMenuItems(object,
                                 ((Boolean) vec.toArray()[1]).booleanValue());
            viewSelectedObject(object);
        }

        view.setCursor(Cursor.getDefaultCursor());
    }

//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2253
     /**
     * ˢ����ѡ��Ķ���
     */

    //begin CR12
    public void refreshSelectedObject1(CappTreeNode node) throws QMException
    {
        if (verbose)

        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.refreshSelectedObject() begin...");
        }
        BaseValueInfo info = (BaseValueInfo) node.getObject().getObject();
        if (vector.contains(info.getBsoID()))
		{
        	DialogFactory.showInformDialog(view, "�˹�������ɾ�������ܶ��������������");
//			JOptionPane.showMessageDialog(view, "�˹�������ɾ�������ܶ��������������", "���棡",
//					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        Collection vec = (Collection) useServiceMethod("StandardCappService",
                "doAction1",
                new Class[]
                {BaseValueIfc.class}
                ,
                new Object[]
                {node.getObject().getObject()});

        if (vec != null && vec.size() == 2)
        {
            BaseValueInfo latestinfo = (BaseValueInfo) vec.toArray()[0];
            //begin CR25
//            UserIfc userIfc = this.getCurrentUser();
//            if((((WorkableIfc)latestinfo).getWorkableState()).equals("c/o"))
//            {
//                if(userIfc.getUsersName().equals("Administrator") || 
//                        userIfc.getBsoID().equals(((WorkableIfc)latestinfo).getIterationCreator()))
//                {
//                    latestinfo = (BaseValueInfo)CheckInOutCappTaskLogic.getWorkingCopy((WorkableIfc)latestinfo);
//                }
//            }
            //end CR25
            CappTreeObject object = null;
            if (latestinfo instanceof QMTechnicsIfc)
            {
                
                object = new TechnicsTreeObject(latestinfo);
                viewSelectedObject(object);
            }
            else if (latestinfo instanceof QMProcedureIfc)
            {
                if (((QMProcedureIfc) latestinfo).getIsProcedure())
                {
                    object = new StepTreeObject(latestinfo);
                }
                else
                {
                    object = new OperationTreeObject(latestinfo);
                }
                if(((QMProcedureIfc) latestinfo).getOwner() != null)
                {
                    updateSelectedObject();
                }
                else
                {
                	viewSelectedObject(object);
                }
            }

            if (object != null)
            {
                ((ProcessTreeObject) object).setParentID(((ProcessTreeObject)
                        node.getObject()).getParentID());
                view.getProcessTreePanel().updateNode(object);
            }
            view.enableMenuItems(object,
                                 ((Boolean) vec.toArray()[1]).booleanValue());
        }

        view.setCursor(Cursor.getDefaultCursor());
    }
    //end CR12
    //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2253

//  delete by wangh on 20061206(ȥ��û�����õķ���)
//    /**
//     * ���½ڵ�����ɽڵ�
//     * @param latestinfo BaseValueInfo �½ڵ�
//     * @param node CappTreeNode �ɽڵ�
//     * @param parentID String �µĹ�������Ϣ��id
//     */
//    private CappTreeObject refreshNode(BaseValueInfo latestinfo,
//                                       CappTreeNode node, CappTreeObject Upper)
//    {
//
//        CappTreeObject object = null;
//        if (latestinfo instanceof QMTechnicsIfc)
//        {
//            object = new TechnicsTreeObject(latestinfo);
//            ((ProcessTreeObject) object).setParentID(latestinfo.getBsoID());
//        }
//        else if (latestinfo instanceof QMProcedureIfc &&
//                 ((QMProcedureIfc) latestinfo).getIsProcedure())
//        {
//            object = new StepTreeObject(latestinfo);
//            ((ProcessTreeObject) object).setParentID(((BaseValueInfo) Upper.
//                    getObject()).getBsoID());
//        }
//        else if (latestinfo instanceof QMProcedureIfc &&
//                 !((QMProcedureIfc) latestinfo).getIsProcedure())
//        {
//            object = new OperationTreeObject(latestinfo);
//            ((ProcessTreeObject) object).setParentID(((BaseValueInfo) Upper.
//                    getObject()).getBsoID());
//        }
//        CappTreeNode newnode = view.getProcessTreePanel().findNode(object);
//        if (newnode != null)
//        {
//            view.getProcessTreePanel().updateNode(object);
//        }
//        else
//        {
//            CappTreeObject upnobj = null;
//            if (object instanceof TechnicsTreeObject)
//            {
//                upnobj = view.getProcessTreePanel().findNode(node.getObject()).
//                         getP().getObject();
//            }
//            else
//            {
//                upnobj = Upper;
//            }
//            view.getProcessTreePanel().addNode(upnobj, object);
//            if (view.getProcessTreePanel().findNode(node.getObject()) != null)
//            {
//                view.getProcessTreePanel().removeNode(node.getObject());
//            }
//        }
//        return object;
//
//    }


    /**
     * �ϲ����չ��
     */
    private void collectTechnics()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getContentJPanel().setUniteTechnicsJPanel();
        view.setCursor(Cursor.getDefaultCursor());
    }
    
//CCBegin SS9
    /**
     * 
     * ���غϲ����չ��
     */
    private void ctCollectTechnics()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.getContentJPanel().setCTUniteTechnicsJPanel();
        view.setCursor(Cursor.getDefaultCursor());
    }
//CCEnd SS9

    private void viewSelectedObject(CappTreeObject obj)
    {
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setViewMode(2, obj);
            view.setCursor(Cursor.getDefaultCursor());
        }

    }

    
    /**
     * //CCBegin by liunan 2009-09-22 ��Ӳ鿴���ݿͻ�jsp����ķ�����
     */
    private void viewSelectedObjectJsp()
    {
      CappTreeObject curobj = view.getSelectedNode().getObject();
      BaseValueInfo curinfo = (BaseValueInfo) curobj.getObject();
      //��ǰ�ڵ��ǹ��չ�̡�
      if (curinfo instanceof QMFawTechnicsInfo)
      {
        QMFawTechnicsInfo curtinfo = (QMFawTechnicsInfo) curinfo;
        String bsoID = curtinfo.getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //���鿴���չ�̡����ݿͻ�ҳ��
        RichToThinUtil.toWebPage("capp_viewTechnics.screen", hashmap);
      }
      //��ǰ�ڵ��ǹ�����߹�����
      if (curinfo instanceof QMProcedureInfo)
      {
        QMProcedureInfo curpinfo = (QMProcedureInfo) curinfo;
        String bsoID = curpinfo.getBsoID();
        CappTreeNode pnode = view.getSelectedNode().getP();
        CappTreeObject pobj = pnode.getObject();
        BaseValueInfo pinfo = (BaseValueInfo) pobj.getObject();
        //���ڵ��ǹ��չ�̣����Ե�ǰ�ڵ��ǹ���
        if (pinfo instanceof QMFawTechnicsInfo)
        {
          QMFawTechnicsInfo curpftinfo = (QMFawTechnicsInfo) pinfo;
          HashMap hashmap = new HashMap();
          hashmap.put("bsoID", bsoID);
          hashmap.put("technicsBsoID", curpftinfo.getBsoID());
          hashmap.put("techNumber", curpftinfo.getTechnicsNumber());
          hashmap.put("techVersion", curpftinfo.getVersionValue());
          hashmap.put("techName", curpftinfo.getTechnicsName());
          //���鿴���򡱵��ݿͻ�ҳ��
          RichToThinUtil.toWebPage("capp_viewProcedure.screen", hashmap);
        }
        //���ڵ��ǹ������Ե�ǰ�ڵ��ǹ�����
        if (pinfo instanceof QMProcedureInfo)
        {
          CappTreeNode ppnode = pnode.getP();
          CappTreeObject ppobj = ppnode.getObject();
          BaseValueInfo ppinfo = (BaseValueInfo) ppobj.getObject();
          if (ppinfo instanceof QMFawTechnicsInfo)
          {
            QMFawTechnicsInfo curppftinfo = (QMFawTechnicsInfo) ppinfo;
            QMProcedureInfo curppinfo = (QMProcedureInfo) pinfo;
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", bsoID);
            hashmap.put("stepNum", Integer.toString(curppinfo.getStepNumber()));
            hashmap.put("stepName", curppinfo.getStepName());
            hashmap.put("stepVersion", curppinfo.getVersionValue());
            hashmap.put("technicsBsoID", curppftinfo.getBsoID());
            hashmap.put("techNumber", curppftinfo.getTechnicsNumber());
            hashmap.put("techVersion", curppftinfo.getVersionValue());
            hashmap.put("techName", curppftinfo.getTechnicsName());
            //���鿴���������ݿͻ�ҳ��
            RichToThinUtil.toWebPage("capp_viewPace.screen", hashmap);
          }
        }
      }
    }
    

    /**
     * �鿴
     */
    private void viewSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewSelectedObject() begin...");

        }
        //CCBegin by liunan 2009-09-22 ��Ӳ鿴���ݿͻ�jsp����ķ�����
        viewSelectedObjectJsp();
        //CCEnd by liunan 2009-09-22
        CappTreeObject obj = view.getSelectedNode().getObject();
        try
        {
            obj = view.update(obj);
        }
        catch (QMRemoteException ex1)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view,
                                          ex1.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;

        }

        viewSelectedObject(obj);

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewSelectedObject() end...return is void");
        }
    }


    /**
     * ����
     */
    private void updateSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.updateSelectedObject() begin...");
        }
        CappTreeNode selectedNode = view.getSelectedNode();
        CappTreeObject obj = selectedNode.getObject();
        CappTreeNode parentTechNode = view.getParentTechnicsNode(selectedNode);
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {

            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex1)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view,
                                              ex1.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            BaseValueInfo baseValueInfo = (BaseValueInfo) obj.getObject();
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try
            {
                if (baseValueInfo instanceof WorkableIfc)
                {

                    if (CheckInOutCappTaskLogic.isCheckInAndInVault((
                            WorkableIfc)
                            baseValueInfo))

                    {
                        Object[] identity =
                                {
                                getIdentity(baseValueInfo)};
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                "40", identity);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        int i = JOptionPane.showConfirmDialog(view, message,
                                title,
                                JOptionPane.YES_NO_OPTION);
                        if (i != 0)
                        {
                            view.setCursor(Cursor.getDefaultCursor());
                            return;
                        }
                        //���ѡ�нڵ㼰�����ϼ����ڵ�
//                      CCBegin by leixiao 2009-9-4 ԭ�򣺽����������·��,�ѷ�����·�߲��ܼ��
                        //CCBegin by liunan 2012-03-06 ������������ض����ѷ���״̬�����޸ģ�����������ġ�
                        //if ((baseValueInfo instanceof QMTechnicsIfc)&&(((LifeCycleManagedIfc)baseValueInfo).getLifeCycleState().toString().equals("RELEASED"))) {
                        if ((baseValueInfo instanceof QMTechnicsIfc)&&(((LifeCycleManagedIfc)baseValueInfo).getLifeCycleState().toString().equals("RELEASED"))&&!isCappSupperGroup()) {
                        //CCEnd by liunan 2012-03-06
                            String mm = ((QMTechnicsIfc)baseValueInfo).getTechnicsNumber() + "�ѷ��������ܼ����";
                            JOptionPane.showMessageDialog(view, mm);
                            return ;
                          }
//                      CCEnd by leixiao 2009-9-4 ԭ�򣺽����������·��
                        baseValueInfo = checkOutParentNode(selectedNode,
                                parentTechNode);
                       //System.out.println("baseValueInfo="+baseValueInfo);

                    }
                    else
                    if (CheckInOutCappTaskLogic.isCheckedOutByOther((
                            WorkableIfc) baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                    {
                        Object[] objs =
                                {
                                getIdentity(baseValueInfo)};
                        String message1 = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(view,
                                message1,
                                title,
                                JOptionPane.INFORMATION_MESSAGE);
                        view.setCursor(Cursor.getDefaultCursor());
                        return;

                    }
                }
            }
            catch (QMException e)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                view.setCursor(Cursor.getDefaultCursor());
                return;
            }
            view.getContentJPanel().setSelectedObject(baseValueInfo);
            if (obj instanceof TechnicsTreeObject)
            {
                selectedNode = view.findNode(new TechnicsTreeObject(
                        baseValueInfo));
                view.getContentJPanel().setSelectedNode(selectedNode);
                view.getContentJPanel().setTechnicsMode(0);
                view.setMenuAndToolBar(false,true);//CR7
            }
            else if (obj instanceof StepTreeObject)
            {
                selectedNode = view.findNode(new StepTreeObject(baseValueInfo));
                view.getContentJPanel().setSelectedNode(selectedNode);
                view.getContentJPanel().setStepMode(0);
            }
            else if (obj instanceof OperationTreeObject)
            {
                selectedNode = view.findNode(new OperationTreeObject(
                        baseValueInfo));
                view.getContentJPanel().setSelectedNode(selectedNode);
                view.getContentJPanel().setPaceMode(0);
            }
            view.setCursor(Cursor.getDefaultCursor());
            view.refresh();
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.updateSelectedObject() end...return is void");
        }
    }


    /**
     * ɾ����ѡ��Ķ���(����ѡ��Ķ����ύ�����ɾ��)
     */
    private void deleteSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteSelectedObject() begin...");

        }
        CappTreeObject treeObj = view.getSelectedNode().getObject();
        if (treeObj != null && treeObj instanceof BusinessTreeObject)
        {
            //��ʾ�Ƿ�ɾ��
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            //modify by wangh on 20080804
            String message = "";
            if(treeObj instanceof TechnicsTreeObject)
            {message = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "119",
                    null);}
            else{
            message = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    CappLMRB.CONFIRM_DELETE_OBJECT,
                    null);}
            //modify end
            int result = JOptionPane.showConfirmDialog(view, message, title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            //20061201�����޸ģ�ȥ��ȷ��ɾ��ʱ������жϡ�
//            switch (result)
//            {
//                case JOptionPane.CLOSED_OPTION:
//                    return;
//                case JOptionPane.CANCEL_OPTION:
//                    return;
//                case JOptionPane.NO_OPTION:
//                    return;
//                case JOptionPane.YES_OPTION:
//                {
            if (result == JOptionPane.YES_OPTION)
            {
                {
                    //view.setCursor(Cursor.getPredefinedCursor(Cursor.
                     //       WAIT_CURSOR));//CR1
                    //20061201�����޸ģ�ȥ���Ը��ڵ�ֵ����Ļ��
//          BaseValueInfo fatherInfo =
//              (BaseValueInfo) view.getSelectedNode().getP().
//              getObject().
//              getObject();
                    BaseValueInfo obj;
                    try
                    {
                        treeObj = view.update(treeObj);
                        obj = (BaseValueInfo) treeObj.getObject();
                        //�ж��Ƿ����˼��
                        if (CheckInOutCappTaskLogic.isCheckedOutByOther((
                                WorkableIfc)
                                obj))
                        {
                            Object[] objs =
                                    {
                                    getIdentity(obj)};
                            String message1 = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                            title = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    "information", null);
                            JOptionPane.showMessageDialog(view,
                                    message1,
                                    title,
                                    JOptionPane.
                                    INFORMATION_MESSAGE);
                            //20060815Ѧ���޸ģ�������������״̬
                            view.setCursor(Cursor.getDefaultCursor());
                            return;

                        }
                    }
                    catch (QMRemoteException ex2)
                    {
                        title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(view,
                                ex2.getClientMessage(),
                                title,
                                JOptionPane.INFORMATION_MESSAGE);
                        //20060815Ѧ���޸ģ�������������״̬
                        view.setCursor(Cursor.getDefaultCursor());
                        return;

                    }
                    QMTechnicsInfo technics;
                    QMProcedureInfo procedure;
                    if (obj instanceof QMTechnicsInfo)
                    {
                    	technics = (QMTechnicsInfo) obj;
                    	//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
//						vector.add(technics.getBsoID());//Begin CR1
//						view.getProcessTreePanel().removeNode(treeObj);
//						treeObj = new TechnicsTreeObject(
//								technics);
//		
//						 java.net.URL url = TechnicsRegulationsMainJFrame.class.getResource(
//					        "/images/public_deleteObj.gif");
//					   
//						 treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
//						 treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
//						 treeObj.setNoteText("(�˹������ڱ�ɾ��...)");
//						view.getProcessTreePanel().addProcess(treeObj);// End CR1
                    	//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667

						if (deleteQMTechnics((QMFawTechnicsInfo) technics))
						{
							view.getProcessTreePanel().removeNode(treeObj);//CR1
						}
                    }
                    else if (obj instanceof QMProcedureInfo)
                    {
                        CappTreeNode parentNode = (CappTreeNode) view.
                                                  getParentTechnicsNode();
                        QMTechnicsInfo parentTechnics = (QMTechnicsInfo)
                                parentNode.
                                getObject().getObject();
                        procedure = (QMProcedureInfo) obj;
                        if (deleteQMProcedure(procedure, parentTechnics))
                        {

                            //20061201�����޸ģ�ɾ��û���õĶ���cto
                            /*
                             fatherInfo = CappClientHelper.refresh(
                                    fatherInfo);
                                                             //������ڵ���ԭ�����ø���
                             if (!CheckInOutCappTaskLogic.isWorkingCopy((
                                    WorkableIfc) fatherInfo)
                                &&
                                CheckInOutCappTaskLogic.isCheckedOut((
                                    WorkableIfc)
                                    fatherInfo))
                                                             {
                                fatherInfo = (BaseValueInfo)
                                             CheckInOutCappTaskLogic.
                                             getWorkingCopy(
                                        (WorkableIfc) fatherInfo);
                                                             }
                             CappTreeObject cto = null;
                             if (fatherInfo instanceof QMTechnicsInfo)
                                                             {
                                cto = new TechnicsTreeObject(fatherInfo);
                                                             }
                             if (fatherInfo instanceof QMProcedureInfo)
                                                             {
                                if (((QMProcedureInfo) fatherInfo).
                                    getIsProcedure())
                                {
                                    cto = new StepTreeObject(fatherInfo);
                                }
                                else
                                {
                                    cto = new OperationTreeObject(
                                            fatherInfo);
                                }
                                                             }*/

                            view.getProcessTreePanel().removeNode(treeObj);
                        }
                    }
                    else
                    {
                        message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                CappLMRB.WRONG_TYPE_OBJECT,
                                null);
                        title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(view, message, title,
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                return;
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteSelectedObject() end...return is void");
        }
    }
    
    
    //CCBegin SS12
    private void findMainStep(){
    	
    	CappTreeObject object = view.getSelectedNode().getObject();
    	
    	if (object != null) {
    		
			if (object instanceof TechnicsTreeObject) {
				
				try {
					object = view.update(object);
				} catch (QMRemoteException ex) {
					String title = QMMessage.getLocalizedMessage(RESOURCE,
							"information", null);
					JOptionPane.showMessageDialog(view, ex.getClientMessage(),
							title, JOptionPane.INFORMATION_MESSAGE);

				}
				
				BaseValueInfo info = (BaseValueInfo) object.getObject();
				QMFawTechnicsIfc technics = (QMFawTechnicsIfc) info;
				
				Class[] paraclass = { String.class ,String.class,Boolean.TYPE};
				Object[] paraobj = { technics.getBsoID(),technics.getBsoID(),true};
				try {
					Vector allStepVec=(Vector)useServiceMethod("StandardCappService",
							"browseProcedures", paraclass,
							paraobj);
					
					if(allStepVec!=null&&allStepVec.size()!=0){
						
						for(int i=0;i<allStepVec.size();i++){
							
							QMProcedureInfo stepInfo=(QMProcedureInfo)allStepVec.get(i);
							
							view.getProcessTreePanel().removeNode(
			                        new StepTreeObject((QMProcedureInfo) stepInfo));
							
							String sType=stepInfo.getStepClassification().getCodeContent();
							
							if(sType.equals("�ؼ�����")){

							  
						       view.addProcedureNode(view.getSelectedNode(), stepInfo);
						       
							  
							}
						}
					}
					
					
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view,
							"���ҹؼ�����ʱ���ִ���,�����¼��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					view.stopProgress();
					return;

				}
				
				
			}
			
    	}
    }
    
    //CCEnd SS12

    //CCBegin SS10
    /**
	 * ������й��򹤲�
	 */
	private void checkAllStepAndPace() {
		if (verbose) {
			System.out
					.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() begin...");
		}
		WorkableIfc copyinfo = null;
		boolean flag = true;
		CappTreeObject object = view.getSelectedNode().getObject();
		if (object != null) {
			if (object instanceof TechnicsTreeObject) {
				try {
					object = view.update(object);
				} catch (QMRemoteException ex) {
					String title = QMMessage.getLocalizedMessage(RESOURCE,
							"information", null);
					JOptionPane.showMessageDialog(view, ex.getClientMessage(),
							title, JOptionPane.INFORMATION_MESSAGE);
					// return null;

				}
				BaseValueInfo info = (BaseValueInfo) object.getObject();
				QMFawTechnicsIfc technics = (QMFawTechnicsIfc) info;
				view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				view.startProgress();
				// ��ü����Ķ���
				if (technics.getOwner() != null
						&& technics.getOwner().length() > 0) {
					copyinfo = technics;
					flag = false;
					try {
						// �жϹ����Ƿ��Ѿ������,����Ѿ������,���ж��ǲ��Ǳ����˼���,���������ʾ����
						if (CheckInOutCappTaskLogic.isCheckedOutByOther(
								(WorkableIfc) technics, view.currentUser)) {
							Object[] objs = { getIdentity(technics) };
							String message1 = QMMessage.getLocalizedMessage(
									RESOURCE,
									CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
							String title = QMMessage.getLocalizedMessage(
									RESOURCE, "information", null);
							JOptionPane.showMessageDialog(view, message1,
									title, JOptionPane.INFORMATION_MESSAGE);
							view.setCursor(Cursor.DEFAULT_CURSOR);
							view.stopProgress();
							return;
						}

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(view, e
								.getLocalizedMessage(), "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);
						view.stopProgress();
						return;
					}
				} else {
					copyinfo = getCheckOutObject((WorkableIfc) info);
				}
				Class[] paraclass = { QMFawTechnicsIfc.class };
				Object[] paraobj = { (QMFawTechnicsIfc) copyinfo };
				try {
					useServiceMethod("StandardCappService",
							"checkoutAllStepAndPaceInTechnics", paraclass,
							paraobj);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view,
							"��������¹��򼰹����ǳ��ִ���,�����¼��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					view.stopProgress();
					return;

				}

				view.stopProgress();
				if (copyinfo != null) {
					// ɾ��ԭ��
					view.removeNode(info);
					if (!flag) {
						Object oo[] = { copyinfo };
						view.addTechnics(oo);
					}
					view.getProcessTreePanel().setNodeSelected(
							view.getProcessTreePanel().findNode(
									new TechnicsTreeObject(
											(BaseValueInfo) copyinfo))
									.getObject());

				}
				view.setCursor(Cursor.getDefaultCursor());
			} else {
				// ��ʾ���������ʹ���
				String message = QMMessage.getLocalizedMessage(RESOURCE,
						CappLMRB.WRONG_TYPE_OBJECT, null);
				String title = QMMessage.getLocalizedMessage(RESOURCE,
						"information", null);
				JOptionPane.showMessageDialog(view, message, title,
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (verbose) {
			System.out
					.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() end...return is void");
		}
		// return copyinfo;

	}
    
    //CCEnd SS10

    /**
     * ɾ��ָ���Ĺ��տ�(���÷���)
     * @param info ���տ�
     */
    private boolean deleteQMTechnics(QMFawTechnicsInfo info)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMTechnics() begin...");
        }
        boolean isDeleted = false;
        if (!(info.equals(null)))
        {
            //�������ɾ������ʾ�Ƿ�ɾ��ѡ���
            if (isDeleteAllowed(info))
            {
            	//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
            	CappTreeObject treeObj = new TechnicsTreeObject(info);//CR9
            	//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                QMTechnicsIfc original = null;
                try
                {
                    //���ѡ�����Ϊ�������������ԭ��
                    if (CheckInOutCappTaskLogic.isWorkingCopy((
                            WorkableIfc) info))
                    {
                    	original = (QMFawTechnicsIfc)
                                   CheckInOutCappTaskLogic.
                                   getOriginalCopy(
                                (WorkableIfc) info);
                    }
                    //view.startProgress();//CR1
                    //view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));//CR1
                    //����Capp����ɾ��ָ���Ĺ��տ�
                  //CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2745  
                 //begin CR11
                    Class[] paraClass=null;
                    Object[] obj=null;
                    if(isDeleteAllUnlinkedVersions.equals("false"))
                    {
                        Class[] paraClass1 ={String.class};
                        Object[] obj1 ={info.getBsoID()};
                        paraClass= paraClass1;  
                        obj=obj1;
                    }else if(isDeleteAllUnlinkedVersions.equals("true"))
                    {
                        Class[] paraClass2 ={QMFawTechnicsIfc.class};
                        Object[] obj2 ={info};
                        paraClass= paraClass2;  
                        obj=obj2;
                    }//end CR11 
                    //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2745
                    //CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
                    vector.add(info.getBsoID());//Begin CR9
					view.getProcessTreePanel().removeNode(treeObj);
					//treeObj = new TechnicsTreeObject( 
					//		info);
					java.net.URL url = TechnicsRegulationsMainJFrame.class.getResource(
				        "/images/public_deleteObj.gif");
					treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setNoteText("(�˹������ڱ�ɾ��...)");
					view.getProcessTreePanel().addProcess(treeObj);// End CR9
					//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
	//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415 TD2745  
		//begin CR11
                    Collection relationTechnics = null;
                    if(isDeleteAllUnlinkedVersions.equals("false"))
                    relationTechnics = (Collection)
                                                  useServiceMethod(
                            "StandardCappService",
                            "deleteQMTechnics", paraClass, obj);//CR10
                    else if(isDeleteAllUnlinkedVersions.equals("true"))
                    relationTechnics = (Collection)
                                                  useServiceMethod(
                            "StandardCappService",
                             "deleteAllUnlinkedQMTechnics", paraClass, obj);
                    if(isDeleteAllUnlinkedVersions.equals("true"))
                    {
                        if(relationTechnics != null && relationTechnics.size()==1)
                        {                            
//                            String message1 = QMMessage.getLocalizedMessage(
//                                    RESOURCE,
//                                    CappLMRB.
//                                    CANNOT_DELETE_FOR_OLDVERSIONS,
//                                    null);
                        	String message1="�������°汾������ɾ��!";
                            String title1 = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                            JOptionPane.showMessageDialog(view, message1,title1,JOptionPane.INFORMATION_MESSAGE);
                            vector.clear();
                            view.setCursor(Cursor.getDefaultCursor());
                            return false;   
                        }
                        else if(relationTechnics != null && relationTechnics.size()>1)
                        {
                            Object[] results = relationTechnics.toArray();
                            Object[] object = new Object[2];
                            Object newTechnics=results[0];
                            Collection collection=(Collection)results[1];
                            Iterator i = collection.iterator();
                            Object[] techAndProcedure=(Object[])i.next();
                            QMTechnicsIfc tech=(QMTechnicsIfc)techAndProcedure[0];
                            QMProcedureIfc procedure=(QMProcedureIfc)techAndProcedure[1];
                            object[0] = getIdentity((QMTechnicsIfc)newTechnics);
                            object[1] = getIdentity(tech)+"��"+getIdentity(procedure);
//                            String message2 = QMMessage.getLocalizedMessage(
//                                    RESOURCE,
//                                    CappLMRB.
//                                    CANNOT_DELETE_FOR_LINKEDVERSIONS,
//                                    object);
                            String message2="�˹��ձ�"+object[1]+"�����������գ�����ɾ��!";
                            String title2 = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                            JOptionPane.showMessageDialog(view, message2,title2,JOptionPane.INFORMATION_MESSAGE);
                            vector.clear();
                            view.getProcessTreePanel().removeNode(treeObj);
                            QMFawTechnicsInfo te = (QMFawTechnicsInfo)newTechnics;
                            treeObj = new TechnicsTreeObject(te);
                            java.net.URL url1 = TechnicsRegulationsMainJFrame.class.getResource(
                                "/images/technics.gif");
                            treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
                            treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
                            treeObj.setNoteText(te.getTechnicsNumber()+"_"+te.getVersionValue()+"_"+DataDisplay.translate(te.getTechnicsName()));
                            view.getProcessTreePanel().addProcess(treeObj);
                            view.setCursor(Cursor.getDefaultCursor());
                            return false;
                        }
                        else if(relationTechnics == null)
                            isDeleted = true;
                    }
                    else if(isDeleteAllUnlinkedVersions.equals("false"))
                    {
                    //end CR11
                    //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2745  
                    //view.stopProgress();//CR1
                    //view.setCursor(Cursor.getDefaultCursor());//CR1
                    //����ֵ����Ϊnull,˵���й����Ĺ��տ�,��ʾ����ɾ��
                    if (relationTechnics != null && relationTechnics.size() > 0)
                    {
                        Object[] obj2 = new Object[1];

                        Iterator i = relationTechnics.iterator();
                        //20070111Ѧ���޸ģ�����˷��ص�ֵ�Ѹģ��ͻ�����Ӧ�����޸�
                        Object[] techAndProcedure=(Object[])i.next();
                        QMTechnicsIfc tech=(QMTechnicsIfc)techAndProcedure[0];
                        QMProcedureIfc procedure=(QMProcedureIfc)techAndProcedure[1];

                        obj2[0] = getIdentity(tech)+"��"+getIdentity(procedure);
                        String message2 = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                CappLMRB.
                                CANNOT_DELETE_FOR_RELATIONTECHNICS,
                                obj2);
                        String title2 = QMMessage.getLocalizedMessage(
                                RESOURCE, "information", null);
                        JOptionPane.showMessageDialog(view, message2,
                                title2,
                                JOptionPane.INFORMATION_MESSAGE);
                        //CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
                        vector.clear();//begin CR9
                        //view.getProcessTreePanel().removeNode(treeObj);
    					//treeObj = new TechnicsTreeObject( 
    					//		technics);
                        treeObj = new TechnicsTreeObject(info);
    					java.net.URL url1 = TechnicsRegulationsMainJFrame.class.getResource(
    				        "/images/technics.gif");
    					treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
    					treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url1));
    					treeObj.setNoteText(info.getTechnicsNumber()+"_"+info.getVersionValue()+"_"+DataDisplay.translate(info.getTechnicsName()));
    					//view.getProcessTreePanel().addProcess(treeObj);
    					view.getProcessTreePanel().setNodeSelected(treeObj);//end CR9
    					//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
                        return false;
                    }
                    RefreshService.getRefreshService().dispatchRefresh(
                            view, RefreshEvent.DELETE, info);
                    //���ԭ����Ϊ�գ�ˢ��ԭ��Ϊ����״̬
                    if (original != null)
                    {
                        original = CappClientHelper.refresh((
                                QMFawTechnicsInfo) original);
                        RefreshService.getRefreshService().
                                dispatchRefresh(
                                view, RefreshEvent.UPDATE,
                                original);
                        original.setWorkableState("c/i");
                        view.updateNode((BaseValueInfo) original, null);
                    }

                    isDeleted = true;
                }
                }//CR11
                catch (QMRemoteException ex)
                {
                    view.stopProgress();
                    String ti = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  ex.getClientMessage(), ti,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    //CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
                    vector.clear();//begin CR9
                    //view.getProcessTreePanel().removeNode(treeObj);
					//treeObj = new TechnicsTreeObject( 
					//		technics);
                    treeObj = new TechnicsTreeObject(info);  
					java.net.URL url = TechnicsRegulationsMainJFrame.class.getResource(
				        "/images/technics.gif");
					treeObj.setOpenImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setCloseImage(java.awt.Toolkit.getDefaultToolkit().createImage(url));
					treeObj.setNoteText(info.getTechnicsNumber()+"_"+info.getVersionValue()+"_"+DataDisplay.translate(info.getTechnicsName()));
					//view.getProcessTreePanel().addProcess(treeObj);
					view.getProcessTreePanel().setNodeSelected(treeObj);//end CR9
					//CCBeginby liuzc 2009-12-19 ԭ��û�н���ɾ�����������Ǹù���ͼ��һֱ��ʾΪɾ��״̬���μ�DefectID=2667
                }

                view.setCursor(Cursor.getDefaultCursor());
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMTechnics() end...return : " +
                               isDeleted);
        }
        return isDeleted;
    }


    /**
     * ɾ��ָ���Ĺ���򹤲������÷���
     * @param info ����򹤲�
     * @param parentTechnics �������򣨲����ĸ����տ�
     */
    private boolean deleteQMProcedure(QMProcedureInfo info,
                                      QMTechnicsInfo parentTechnics)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMProcedure() begin...");
        }
        boolean isDeleted = false;
        if (!(info.equals(null)))
        {
            //�������ɾ������ʾ�Ƿ�ɾ��ѡ���
            if (isDeleteAllowed(info))
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                QMProcedureInfo original = null;

                try
                {
                    //���ѡ�����Ϊ�������������ԭ��
                    if (CheckInOutCappTaskLogic.isWorkingCopy((
                            WorkableIfc) info))
                    {
                        original = (QMProcedureInfo)
                                   CheckInOutCappTaskLogic.
                                   getOriginalCopy(
                                (WorkableIfc) info);
                    }
                    //ˢ�²�ɾ��info��ͬʱˢ�¹���������
                    view.startProgress();
                    view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    CappTreeNode selNode = view.getSelectedNode();
                    //�Ƚ����д��ڼ���״̬�ĸ��ڵ���
                    checkOutParentNode(selNode.getP(),
                                       view.getParentTechnicsNode(selNode));

                    parentTechnics = (QMTechnicsInfo) CappClientHelper.
                                     refresh(
                            (QMTechnicsInfo) parentTechnics);

                    //���parentTechnics��ԭ�������ø���
                    if ((!CheckInOutCappTaskLogic.isWorkingCopy((
                            WorkableIfc) parentTechnics))
                        &&
                        CheckInOutCappTaskLogic.isCheckedOut(
                            parentTechnics))
                    {
                        parentTechnics = (QMTechnicsInfo)
                                         CheckInOutCappTaskLogic.
                                         getWorkingCopy(
                                (WorkableIfc) parentTechnics);
                    }
                    //����Capp����ɾ��ָ���Ĺ��򣨲���
                    Class[] paraClass =
                            {
                            String.class, String.class};
                    Object[] obj =
                            {
                            parentTechnics.getBsoID(), info.getBsoID()};
                    if (info.getIsProcedure())
                    {
                        useServiceMethod("StandardCappService",
                                         "deleteQMProcedure",
                                         paraClass, obj);
                    }
                    else
                    {
                        useServiceMethod("StandardCappService",
                                         "deletePace",
                                         paraClass, obj);

                    }
                    view.stopProgress();
                    view.setCursor(Cursor.getDefaultCursor());
                    RefreshService.getRefreshService().dispatchRefresh(
                            view, RefreshEvent.DELETE, info);
                    //���ԭ����Ϊ�գ�ˢ��ԭ���͹���������
                    if (original != null)
                    {
                        original.setWorkableState("c/i");
                        original.setLocker(null);
                        original.setNote(null);
                        original.setDate(null);
                        Class[] cla1 =
                                {
                                BaseValueIfc.class, Boolean.TYPE};
                        Object[] obj1 =
                                {
                                (BaseValueIfc) original, new Boolean(false)};
                        original = (QMProcedureInfo) useServiceMethod(
                                "PersistService", "updateValueInfo",
                                cla1,
                                obj1);
                        view.updateNode(original,
                                        parentTechnics.getBsoID());
                    }
                    isDeleted = true;
                }
                catch (QMException ex)
                {
                    view.stopProgress();
                    String ti = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  ex.getClientMessage(), ti,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.deleteQMProcedure() end...return: " +
                               isDeleted);
        }
        return isDeleted;
    }


    /**
     * �ж��Ƿ�����ɾ��ָ���Ĺ��տ�(����򹤲�)��
     * <p>����Ƿ����ɾ��Ȩ��</p>
     * <p>����Ƿ��������û����¸ù��չ�̵�����Ϣ���Լ����򡢹�����</p>
     * @param info Ҫɾ���Ĺ��տ�(����򹤲�)
     * @return �������ɾ�����򷵻�true
     */
    private boolean isDeleteAllowed(BaseValueInfo info)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isDeleteAllowed() begin...");
        }
        boolean allowed = false;

        //�����ԭ��������flase
        if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info) &&
            CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) info))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            Object[] obj =
                    {
                    getIdentity(info)};
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.CANNOT_MODIFY_ORIGINAL_OBJECT, obj);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            allowed = false;
        }
        else
        {
            allowed = true;

        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isDeleteAllowed() end...return : " +
                               allowed);
        }
        return allowed;
    }


    /**
     * �ж϶����Ƿ������޸�.��������ǹ�������
     * @param obj
     * @return
     */
    public boolean isUpdateAllowed(FolderedIfc obj)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isUpdateAllowed() begin...");
        }
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        boolean flag = true;
        if ((obj instanceof WorkableIfc)
            && (obj instanceof OwnableIfc)
            && !CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) obj))
        {
            Object object = null;
            try
            {
                object = useServiceMethod("SessionService", "getCurUserID", null, null);
            }
            catch (QMRemoteException ex)
            {
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            try
            {
                flag = OwnershipHelper.isOwnedBy((OwnableIfc) obj,
                                                 (String) object);
            }
            catch (QMException ex)
            {
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isUpdateAllowed() end...return: " +
                               flag);
        }
        return flag;
    }

//  delete by wangh on 20061206(ȥ��û�����õķ���)
//    /**
//     * ������������ϼ�·���Ƿ��Ǹ������ϼ�·����
//     * @return ������������ϼ�·���Ƿ��Ǹ������ϼ�·��������true
//     * @throws QMRemoteException
//     */
//    private boolean isPersonalLocation(String location)
//            throws
//            QMRemoteException
//    {
//        if (verbose)
//        {
//            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isPersonalLocation() begin...");
//        }
//        SubFolderInfo folderInfo = null;
//        boolean flag = false;
//        if (location != null && location.length() != 0)
//        {
//            //�������ϼз��񣬸���·��������ϼ�
//            Class[] paraClass =
//                    {
//                    String.class};
//            Object[] objs =
//                    {
//                    location};
//            try
//            {
//                folderInfo = (SubFolderInfo) useServiceMethod(
//                        "FolderService", "getFolder", paraClass, objs);
//            }
//            catch (QMRemoteException ex)
//            {
//                throw ex;
//            }
//
//        }
//
//        if (folderInfo != null)
//        {
//            //�������ϼз����ж�ָ�����ļ����Ƿ��Ǹ����ļ���
//            Class[] paraClass2 =
//                    {
//                    FolderIfc.class};
//            Object[] objs2 =
//                    {
//                    folderInfo};
//            Boolean flag1 = null;
//            try
//            {
//                flag1 = (Boolean) useServiceMethod(
//                        "FolderService", "isPersonalFolder", paraClass2, objs2);
//            }
//            catch (QMRemoteException ex)
//            {
//                String title = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.LOCATION_NOT_VALID,
//                        null);
//                JOptionPane.showMessageDialog(view,
//                                              ex.getClientMessage(),
//                                              title,
//                                              JOptionPane.INFORMATION_MESSAGE);
//
//            }
//
//            if (flag1 != null)
//            {
//                flag = flag1.booleanValue();
//            }
//        }
//        if (verbose)
//        {
//            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.isPersonalLocation() end...return: " +
//                               flag);
//        }
//        return flag;
//
//    }

    class FormNewStepThread extends Thread
    {
        public void run()
        {
            formNewStepNumber();
        }
    }


    /**
     * ��ָ���Ĺ��չ���������ɹ���š�
     * <p>ҵ���߼������������ǰ���չ���ڸ������ϼ���״̬�Ǽ���״̬,����÷���</p>
     * <p>�������ɹ���ţ������Ըù��չ�̽��м�����������������Ҫ��һ��</p>
     * <p>�жϸù��չ���Ƿ��ѱ����������ù��չ�̴��ڼ��״̬������ʾ���ܽ����������ɹ����</p>
     * <p>����������ù��չ�������ڼ���״̬������÷���ִ�У���������������ɹ���š������롣</p>
     */
    private void formNewStepNumber()
    {

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.formNewStepNumber() begin...");
        }
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {

                //
                BaseValueInfo technics = (BaseValueInfo) obj.getObject();

                if (technics instanceof QMTechnicsInfo)
                {
                    Class[] paraClass =
                            {
                            String.class, String.class};
                    Object[] objs =
                            {
                            technics.getBsoID(), technics.getBsoID()};

                    Collection procedures = (Collection) useServiceMethod(
                            "StandardCappService",
                            "browseProcedures",
                            paraClass, objs);
                   
					Class[] paraClass2 = { String.class };//Begin CR5
					Object[] objs2 = { ((QMTechnicsInfo) technics).getIterationCreator()};
					UserIfc creator = (UserIfc) useServiceMethod(
							"PersistService", "refreshInfo",
							paraClass2, objs2);
					UserIfc userIfc = this.getCurrentUser();//End CR5
					if (procedures != null && procedures.size() > 0)
					{
						for (Iterator it = procedures.iterator(); it.hasNext();)
						{
							//begin CR5
							if (creator.getBsoID().equals(userIfc.getBsoID()) || userIfc.getUsersName().equals("Administrator"))
							{
								break;
							}
							else
							//end CR5
							{
								String title = QMMessage.getLocalizedMessage(
										RESOURCE, "information", null);
								String message = QMMessage.getLocalizedMessage(
										RESOURCE, "120", null);//CR5
								JOptionPane.showMessageDialog(view, message,
										title,

										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
					}
					//Begin CR6
					else
					{
						String title = QMMessage.getLocalizedMessage(
								RESOURCE, "information", null);
						String message = QMMessage.getLocalizedMessage(
								RESOURCE, "122", null);
						JOptionPane.showMessageDialog(view, message,
								title,

								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					//End CR6

                    boolean isInVault = CheckInOutCappTaskLogic.isInVault(
                            (QMTechnicsInfo) technics);
                    view.startProgress();
                    view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //ֱ�ӵ��÷��񷽷�ʵ�֡�
                    String bsoID = technics.getBsoID();
                    Class[] paraClass1 =
                            {
                            String.class, String.class};
                    Object[] objs1 =
                            {
                            bsoID, bsoID};

                    useServiceMethod("StandardCappService",
                                     "updateAllProcedureNumofCard",
                                     paraClass1, objs1);
                    view.stopProgress();

                    if (!isInVault)
                    {
                        TechnicsTreeObject object = new TechnicsTreeObject(
                                technics);
                        view.getProcessTreePanel().removeNode(object);
                        view.getProcessTreePanel().addProcess(object);
                        CappTreeNode node = view.findNode(object);
                        view.getProcessTreePanel().nodeExpaned(node);

                    }

                    else if (((QMTechnicsInfo) technics).getWorkableState().
                             equals(
                            "c/i") &&
                             isInVault)
                    {
                        TechnicsTreeObject object = new TechnicsTreeObject(
                                technics);
                        view.getProcessTreePanel().addProcess(object);
                        CappTreeNode node = view.findNode(object);
                        view.getProcessTreePanel().nodeExpaned(node);
                    }

                    view.refresh();
                }

                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        catch (QMRemoteException e)
        {
            view.stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.formNewStepNumber() end...return is void");
        }
    }
    
    //CR3 begin
	class FormNewPaceThread extends Thread
	{
		public void run()
		{
			formNewPaceNumber();
		}
	}
	
    /**
	 * ��ָ���Ĺ��չ���������ɹ����š�
	 */
	private void formNewPaceNumber(){

		try
		{
			CappTreeObject obj = view.getSelectedNode().getObject();
			if ((obj != null) && (obj instanceof StepTreeObject))
			{
				QMProcedureInfo procedure = (QMProcedureInfo) obj.getObject();
				QMFawTechnicsInfo technics = (QMFawTechnicsInfo)view.getSelectedNode().getP().getObject().getObject();
				boolean isInVault = CheckInOutCappTaskLogic
							.isInVault((QMTechnicsInfo) technics);
					view.startProgress();
					view.setCursor(Cursor.WAIT_CURSOR);
					Class[] paraClass1 = { String.class, String.class };
					Object[] objs1 = { technics.getBsoID(), procedure.getBsoID() };
					useServiceMethod("StandardCappService",
							"FormNewPaceNum", paraClass1, objs1);
					view.stopProgress();
                    //�������ϼ�
					if (!isInVault)
					{
						if(procedure.getIsProcedure()){
							StepTreeObject object = new StepTreeObject(procedure);	
							CappTreeObject techObj = view.getSelectedNode().getP().getObject();
							view.getProcessTreePanel().removeNode(object);
							view.getProcessTreePanel().addNode(techObj, object);
							CappTreeNode node = view.findNode(object);
							
							object.setParentID(technics.getBsoID());
							view.getProcessTreePanel().setNodeSelected(object);
							view.getProcessTreePanel().nodeExpaned(node);							
						}
						/*else{
							OperationTreeObject object = new OperationTreeObject(procedure);
							view.getProcessTreePanel().removeNode(object);
							view.getProcessTreePanel().addProcess(object);
							CappTreeNode node = view.findNode(object);
							view.getProcessTreePanel().nodeExpaned(node);	
						}*/
						
					}
                    //�������ϼ�
					else if (procedure.getWorkableState()
							.equals("c/i")&& isInVault)
					{
						if(procedure.getIsProcedure()){
							StepTreeObject object = new StepTreeObject(procedure);	
							CappTreeObject techObj = view.getSelectedNode().getP().getObject();
							view.getProcessTreePanel().removeNode(object);
							view.getProcessTreePanel().addNode(techObj, object);
							CappTreeNode node = view.findNode(object);
							
							object.setParentID(technics.getBsoID());
							view.getProcessTreePanel().setNodeSelected(object);
							view.getProcessTreePanel().nodeExpaned(node);						
						}
						/*else{
							OperationTreeObject object = new OperationTreeObject(procedure);
							view.getProcessTreePanel().addProcess(object);
							CappTreeNode node = view.findNode(object);
							view.getProcessTreePanel().nodeExpaned(node);
						}*/
					}
				view.refresh();
				view.setCursor(Cursor.getDefaultCursor());
			}
		}
		catch (QMRemoteException e)
		{
			view.stopProgress();
			String title = QMMessage.getLocalizedMessage(RESOURCE,
					"information", null);
			JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
    //CR3 end





    /**
     * ��ӡԤ��ָ���Ķ���
     */
    private void browsePrintObject()throws QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.browsePrintObject() begin...");
        }
        try
        {
            CappTreeNode node = view.getSelectedNode();
            CappTreeObject obj = node.getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                obj = view.update(obj);                
                //CCBegin SS6
                view.setViewMode(view.getContentJPanel().getMode(), obj);
                //CCEnd SS6
                Class[] paramclass = new Class[]
                                     {
                                     String.class, String.class};
                Object[] paramobject = new Object[]
                                       {
                                       "ģ������", "ģ��"};
                Collection c = (Collection) TechnicsAction.useServiceMethod(
                        "CodingManageService", "findDirectClassificationByName",
                        paramclass, paramobject);
                if (c != null)
                {
                    Vector vv = new Vector(c);
                    if (c.size() >= 1)
                     {

                        view.setCursor(Cursor.getPredefinedCursor(Cursor.
                                WAIT_CURSOR));
                        BaseValueInfo info = (BaseValueInfo) obj.getObject();
                       // TemplateSelectJDialog d = new TemplateSelectJDialog(
                        //CCBegin by liunan 2011-07-15 �жϹ�������ѡ��Ĭ��ֽ�Ŵ�С��
                                //view);
                           //     view,info);
                        //CCEnd by liunan 2011-07-15
                        //d.setObject(info);
                       // d.setVisible(true);
                        //view.setCursor(Cursor.getDefaultCursor());
                        // CCBegin SS2
                        Boolean yy = false; 	
                    	try {
                	    	Class[] paraClass = {};
                	        Object[] objs = {};
                	        String className = "com.faw_qm.doc.util.DocServiceHelper";
                	        String methodName = "isBSXGroup";
                	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
                	        in.setClassName(className);
                	        in.setMethodName(methodName);
                	        in.setParaClasses(paraClass);
                	        in.setParaValues(objs);
                	        RequestServer server = null;
                	        //ͨ������������Ĺ������server
                	        server = RequestServerFactory.getRequestServer();
                		
                			yy = (Boolean) server.request(in);
                		} catch (QMRemoteException e) {
                			e.printStackTrace();
                		}
      
                        if (yy)
                        {
                        	ConsTemplateSelectJDialog d = new ConsTemplateSelectJDialog(view);      
                            d.setObject(info);
                            d.setVisible(true);
                            view.setCursor(Cursor.getDefaultCursor());
                        }
                        else
                        {
                        	TemplateSelectJDialog d = new TemplateSelectJDialog(
                                    //CCBegin by liunan 2011-07-15 �жϹ�������ѡ��Ĭ��ֽ�Ŵ�С��
                                            //view);
                                            view,info);
                       	 //CCEnd by liunan 2011-07-15
                            d.setObject(info);
                            d.setVisible(true);
                            view.setCursor(Cursor.getDefaultCursor());
                        	
                        }
                            
                       //CCend SS2
                    }
                    else if (c.size() == 1)
                    {
                        view.setCursor(Cursor.getPredefinedCursor(Cursor.
                                WAIT_CURSOR));
                        BaseValueInfo info = (BaseValueInfo) obj.getObject();
                        TPrintController tp = new TPrintController();
                        tp.setTreeNode(node);
                        tp.setMainFrame(view);
                        tp.setObject(info);
                        Vector v = new Vector();
                        v.add(vv.get(0).toString());
                        tp.setTemplateType(v);
                        tp.print();
                        view.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.browsePrintObject() end...return is void");
        }
    }


    /**
     * ���Ĺ��տ��Ĵ洢λ��
     */
    private void changeTechnicsLocation()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.changeTechnicsLocation() begin...");
        }
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();
            if ((obj != null) && (obj instanceof BusinessTreeObject))
            {
                obj = view.update(obj);
                BaseValueInfo info = (BaseValueInfo) obj.getObject();
                if (info instanceof QMTechnicsInfo)
                {
                    QMTechnicsInfo technics = (QMTechnicsInfo) info;

                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    String message = "";
                    //�����ǰ�����ѱ���������ڹ������ϼУ���������Ĵ洢λ��
//                    if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)
//                            technics))
                    if (!technics.getWorkableState().equals("c/i"))
                    {
                        Object[] objs =
                                {
                                getIdentity(technics)};
                        message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.
                                CANNOT_CHANGE_LOCATION_CHECK_OUT_OBJECT,
                                objs);
                        JOptionPane.showMessageDialog(view, message, title,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    else if (!CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                            technics))
                    {
                        Object[] objs =
                                {
                                getIdentity(technics)};
                        message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.CANNOT_CHANGE_LOCATION_INVAULT_OBJECT,
                                objs);
                        JOptionPane.showMessageDialog(view, message, title,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    view.setCursor(Cursor.getPredefinedCursor(Cursor.
                            WAIT_CURSOR));
                    TechnicsChangeLocationJFrame frame =
                            new TechnicsChangeLocationJFrame(technics, view);
                    frame.setVisible(true);
                    view.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.changeTechnicsLocation() end...return is void");
        }
    }


    /**
     * ���տ�������
     */
    private void renameTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.renameTechnics() begin...");
        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo info = (BaseValueInfo) obj.getObject();
            if (info instanceof QMTechnicsInfo)
            {
                QMTechnicsInfo technics = (QMTechnicsInfo) info;
                TechnicsReguRenameJDialog dialog = new
                        TechnicsReguRenameJDialog(
                        view, "", true);
                dialog.setTechnicsObject(technics);
                dialog.setVisible(true);
            }
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.renameTechnics() end...return is void");
        }
    }


    /**
     * ���տ����Ϊ
     */
    private void saveAsTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.saveAsTechnics() begin...");

        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setViewMode(3, obj);
            view.setCursor(Cursor.getDefaultCursor());
            view.refresh();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.saveAsTechnics() end...return is void");
        }
    }


    /**
     * Ӧ�õ��͹���(�ɵ��͹������ɹ��չ��)
     */
    private void usageModelTechnics()
    {
        //ʹ�õ��͹�����������ѡ������������������������ڽ����ѡ��һ�����͹���
        //���ִ���߶Թ����޴���Ȩ�޻�ǰ���չ������Ϣ�Ѿ���������������޸Ĺ��չ���е��κ���Ϣ��
        //��ѡ��ĵ��͹��ո��Ƶ���ǰ�Ĺ����У��������ݰ������չ�̵Ķ��й����¼��ڵ���Ϣ
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();

            if (obj != null && (obj instanceof TechnicsTreeObject))
            {
                obj = view.update(obj);
                BaseValueInfo baseValueInfo = (BaseValueInfo) obj.getObject();
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    Object[] objs =
                            {
                            getIdentity(baseValueInfo)};
                    String message1 = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  message1,
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
                if (CheckInOutCappTaskLogic.isCheckedOutByUser((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    //����Ǳ���ǰ�û���������ǹ�������,�ͻ�ù�������
                    if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)
                            baseValueInfo))
                    {
                        baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                                        getWorkingCopy(
                                (WorkableIfc) baseValueInfo);
                    }
                }
                // ����ڹ������ϼ�,������ǰ�ڵ�
                else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                        baseValueInfo))
                {
                    Object[] identity =
                            {
                            getIdentity(baseValueInfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        return;
                    }

                    //���ѡ�нڵ㼰�����ϼ����ڵ�
                    checkOut();
                    baseValueInfo = CappClientHelper.refresh(baseValueInfo);
                    baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                                    getWorkingCopy((WorkableIfc) baseValueInfo);
                    view.getProcessTreePanel().setNodeSelected(new
                            TechnicsTreeObject
                            (baseValueInfo));
                }
                final TSearchMTechnicsJDialog d = new TSearchMTechnicsJDialog(
                        view,
                        ((QMTechnicsIfc) baseValueInfo).getTechnicsType().
                        getCodeContent());
                d.addMultiListActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        CappMultiList c = (CappMultiList) e.getSource();
                        Object[] bvi = c.getSelectedObjects();
                        d.usageModelTechnics((BaseValueInfo) bvi[0]);
                        d.setVisible(false);
                    }
                });
                d.setObject((QMTechnicsInfo) baseValueInfo);
                d.setVisible(true);
                view.setCursor(Cursor.getDefaultCursor());

            }
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Ӧ�õ��͹���(�ɵ��͹������ɹ���)
     */
    private void usageModelProcedure()
    {
        //ʹ�õ��͹�����������ѡ������������������������ڽ����ѡ��һ�����͹���
        //���ִ���߶Թ����޴���Ȩ�޻�ǰ���չ������Ϣ�Ѿ���������������޸Ĺ��չ���е��κ���Ϣ��
        //��ѡ��ĵ��͹����Ʋ���ѡ��Ĺ�������Ϣ�´���һ������͹�����ͬ�Ĺ��򣬴˹����������������й���������
        try
        {
            CappTreeObject obj = view.getSelectedNode().getObject();
            if (obj != null && (obj instanceof TechnicsTreeObject))
            {
                obj = view.update(obj);
                BaseValueInfo baseValueInfo = (BaseValueInfo) obj.getObject();
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    Object[] objs =
                            {
                            getIdentity(baseValueInfo)};
                    String message1 = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                                                  message1,
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);

                    return;
                }

                if (CheckInOutCappTaskLogic.isCheckedOutByUser((WorkableIfc)
                        baseValueInfo, TechnicsRegulationsMainJFrame.currentUser))
                {
                    //����Ǳ���ǰ�û���������ǹ�������,�ͻ�ù�������
                    if (!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)
                            baseValueInfo))
                    {
                        baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                                        getWorkingCopy(
                                (WorkableIfc) baseValueInfo);
                    }
                }
                // ����ڹ������ϼ�,������ǰ�ڵ�
                else if (CheckInOutCappTaskLogic.isInVault((WorkableIfc)
                        baseValueInfo))
                {
                    Object[] identity =
                            {
                            getIdentity(baseValueInfo)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "40", identity);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    int i = JOptionPane.showConfirmDialog(view, message, title,
                            JOptionPane.YES_NO_OPTION);
                    if (i != 0)
                    {
                        return;
                    }
                    baseValueInfo = CappClientHelper.refresh(baseValueInfo);
                    //���ѡ�нڵ�
                    baseValueInfo = (BaseValueInfo) checkOut();

                    //baseValueInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                    //                getWorkingCopy((WorkableIfc) baseValueInfo);
                    view.getProcessTreePanel().setNodeSelected(new
                            TechnicsTreeObject
                            (baseValueInfo));
                }
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                String technicsType = ((QMTechnicsIfc) baseValueInfo).
                                      getTechnicsType().getCodeContent();
                int i = technicsType.indexOf("����");
                if (i != -1)
                {
                    technicsType = technicsType.substring(0, i);
                }
                technicsType = technicsType + "����";
                TSearchMProcedureJDialog d = new TSearchMProcedureJDialog(view,
                        technicsType);
                d.setObject((QMTechnicsInfo) baseValueInfo);
                d.setVisible(true);
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        catch (QMException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }


    /**
     * ���ɵ��͹���
     */
    private void buildModelTechnics()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelTechnics() begin...");

        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setModelTechnicsViewMode();
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelTechnics() end...return is void");
        }
    }


    /**
     * ���ɵ��͹���
     */
    private void buildModelProcedure()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelProcedure() begin...");

        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof BusinessTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            view.setModelTechnicsViewMode();
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.buildModelProcedure() end...return is void");
        }
    }


    /**
     * ����.
     * ���ڿ��ܳ���ͬһ��ҵ������ԭ���͸�����ͬʱ���Ƶ���������޶�ԭ���������ơ�
     */
    private void copy()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.controller.TechnicsRegulationsMainController.copy() begin...");
        }
        //���⣨6��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
        //����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
        CappTreeNode[]  nodes = view.getSelectedNodes();
        CappTreeObject object ;
    	BaseValueInfo info ;
    	//20081218  �촺Ӣ�޸�     �˱������ڴ�Ÿ��ƽڵ����Ϣ
    	Object[]  arrays;
        for(int i = 0; i < nodes.length; i++)
        {
	        object = nodes[i].getObject();
	        if ((object != null) && (object instanceof BusinessTreeObject))
	        {
	            try
	            {
	                object = view.update(object);
	            }
	            catch (QMRemoteException ex)
	            {
	                String title = QMMessage.getLocalizedMessage(RESOURCE,
	                        "information", null);
	                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
	                                              title,
	                                              JOptionPane.INFORMATION_MESSAGE);
	                return;
	            }
	            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	            info = (BaseValueInfo) object.getObject();
	            //ԭ����������
	            if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) info)
	                && !CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc) info))
	            {
	                Object[] obj =
	                        {
	                        getIdentity(info)};
	                String title = QMMessage.getLocalizedMessage(RESOURCE,
	                        "information", null);
	                String message = QMMessage.getLocalizedMessage(RESOURCE,
	                        CappLMRB.CANNOT_COPY_ORIGINAL_OBJECT, obj);
	                JOptionPane.showMessageDialog(view, message, title,
	                                              JOptionPane.INFORMATION_MESSAGE);
	                view.setCursor(Cursor.getDefaultCursor());
	                return;
	            }
	
	            //���ƹ��տ�
	            if (object instanceof TechnicsTreeObject)
	            {
	            	arrays = new Object[3];
	                arrays[0] = (QMTechnicsInfo) info;
	                arrays[1] = object;
	                arrays[2] = ((TechnicsTreeObject)object).getParentID();
	                list.add(arrays);
	                
	            }
	
	            //���ƹ���
	            if (object instanceof StepTreeObject)
	            {
	                //copyOriginalInfo = (QMProcedureInfo) info;
	                
	                //copyOriginalTreeObject = object;
	                //oriTechnicsID = ((StepTreeObject)object).getParentID();
	            	arrays = new Object[3];
	                arrays[0] = (QMProcedureInfo) info;
	                arrays[1] = object;
	                arrays[2] = ((StepTreeObject)object).getParentID();
	                list.add(arrays);
	            }
	            
	            //���ƹ���
	            if (object instanceof OperationTreeObject)
	            {
	            	arrays = new Object[3];
	                arrays[0] = (QMProcedureInfo) info;
	                arrays[1] = object;
	                arrays[2] = ((OperationTreeObject)object).getParentID();
	                list.add(arrays);
	            }
	
	            view.setCursor(Cursor.getDefaultCursor());
	        }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.copy() end...return is void");
        }
    }


    /**
     * ճ��
     * ���⣨6��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
     * ����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
     */
    private void paste()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.controller.TechnicsRegulationsMainController.paste() begin...");

        }
        //���⣨6��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
        //����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
    	BaseValueInfo  copyOriginalInfo = null;
    	CappTreeObject  copyOriginalTreeObject = null;
    	String  oriTechnicsID = "";
    	
        long d1 = System.currentTimeMillis();
        CappTreeNode selectedNode = view.getSelectedNode();
        CappTreeObject object = selectedNode.getObject();
        if ((object != null) && (object instanceof BusinessTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex2)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex2.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            //begin CR8
            BusinessTreeObject selectedObject = (BusinessTreeObject) object;
            if(selectedObject.getObject() instanceof QMProcedureInfo){             
                QMProcedureInfo procedureinfo = (QMProcedureInfo)selectedObject.getObject();
                //����й������գ��������ڸù�����ճ������
                if (procedureinfo.getRelationCardBsoID() != null
                    && !(procedureinfo.getRelationCardBsoID()).equals(""))
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.CAN_NOT_CREATE_PACE, null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, message, title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return;
                }
            }
           //end CR8

            CappTreeNode parentTechnicsNode = view.getParentTechnicsNode();
            for(int m = 0, n = list.size(); m < n; m++)
            {
            	Object[]  obj1 = (Object[])list.get(m);
            	copyOriginalTreeObject = (CappTreeObject)obj1[1];
	            if (parentTechnicsNode == null || copyOriginalTreeObject == null)
	            {
	                return;
	            }
            }
            //���Ŀ��ڵ�ĸ����տ�
            QMTechnicsInfo parentTechnics =
                    (QMTechnicsInfo) parentTechnicsNode.getObject().getObject();
//          delete by wangh on 20061206(ȥ���ظ��Ĵ����)

//            try
//            {
//                object = view.update(object);
//            }
//            catch (QMRemoteException ex1)
//            {
//                String title = QMMessage.getLocalizedMessage(RESOURCE,
//                        "information", null);
//                JOptionPane.showMessageDialog(view, ex1.getClientMessage(),
//                                              title,
//                                              JOptionPane.INFORMATION_MESSAGE);
//                return;
//            }
            BaseValueInfo bvi = (BaseValueInfo) object.getObject();

            try
            {
            	
                //���Ŀ��ڵ㱻���˼����
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)
                        bvi))
                {
                    Object[] objs =
                            {
                            getIdentity(bvi)};
                    String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    throw new QMRemoteException(message1);
                }
                // progressDialog.show();

                //���ƹ��տ�
                //���⣨6��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
                //����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
                if (object instanceof TechnicsTreeObject)
                {
                	for(int j = 0, k = list.size(); j < k; j++)
                	{
                		Object[]  obj = (Object[])list.get(j);
                		copyOriginalInfo = (BaseValueInfo)obj[0];
                		copyOriginalTreeObject = (CappTreeObject)obj[1];
                		oriTechnicsID = (String)obj[2];
                		//Դ�ڵ㣺���տ� �� Ŀ��ڵ㣺���տ�
                        if (copyOriginalTreeObject instanceof
                            TechnicsTreeObject)
                        {
                            boolean flag = CappClientHelper.isTypeSame((
                                    QMTechnicsInfo)
                                    copyOriginalTreeObject.getObject(),
                                    (QMTechnicsInfo) object.getObject());
                            if (!flag)
                            {
                                throwPasteNotAllowedException();
                            }
                            //modify by wangh on 20061205(�޸Ľ�������ʼʱ�䲻��ȷ)
                            //view.startProgress();
                            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            //���Ŀ��ڵ㴦�ڼ���״̬����ִ�м��
                            if (CheckInOutCappTaskLogic.isCheckInAndInVault((
                                    WorkableIfc)
                                    bvi))
                            {
                                Object[] identity =
                                        {
                                        getIdentity(bvi)};
                                String message = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        "40", identity);
                                String title = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        "information", null);
                                int i = JOptionPane.showConfirmDialog(view, message,
                                        title,
                                        JOptionPane.YES_NO_OPTION);
                                if (i != 0)
                                {
                                    //view.stopProgress();
                                    view.setCursor(Cursor.getDefaultCursor());
                                    return;
                                }
                                view.startProgress();
                                //����ڵ㱾�����ڵ�
                                bvi = checkOutParentNode(selectedNode,
                                        parentTechnicsNode);

                            }
                            QMTechnicsInfo technicsObj = (QMTechnicsInfo) bvi;
                            TechnicsCopyController c = new TechnicsCopyController();
                            c.setOriginalTechnics((QMTechnicsInfo)
                                                  copyOriginalInfo);
                            c.setObjectTechnics(technicsObj);
                            c.copy();
                            //ˢ��Ŀ��ڵ�
                            CappTreeNode node = view.getProcessTreePanel().findNode(new
                                    TechnicsTreeObject(technicsObj));
                            view.getProcessTreePanel().nodeExpaned(node);
                            view.stopProgress();
                            view.setCursor(Cursor.getDefaultCursor());
                        }
                        //Դ�ڵ㣺���� �� Ŀ��ڵ㣺���տ�
                        else if (copyOriginalTreeObject instanceof
                                 StepTreeObject)
                        {
                            boolean flag = CappClientHelper.isTypeSame((
                                    QMTechnicsInfo)
                                    CappClientHelper.refresh(oriTechnicsID),
                                    (QMTechnicsInfo) object.getObject());
                            if (!flag)
                            {
                                throwPasteNotAllowedException();
                            }
                            view.startProgress();
                            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            //���Ŀ��ڵ㴦�ڼ���״̬����ִ�м��
                            if (CheckInOutCappTaskLogic.isCheckInAndInVault((
                                    WorkableIfc)
                                    bvi))
                            {

                                //����ڵ㱾�����ڵ�
                                bvi = checkOutParentNode(selectedNode,
                                        parentTechnicsNode);
                                parentTechnics = (QMTechnicsInfo)
                                                 CheckInOutCappTaskLogic.
                                                 getWorkingCopy((WorkableIfc)
                                        parentTechnics);

                            }

                            QMTechnicsInfo procedureObj = (QMTechnicsInfo) bvi;
                            ProcedureCopyController c = new ProcedureCopyController();
                            c.setOriginalProcedure((QMProcedureInfo)
                                                   copyOriginalInfo);
                            c.setObjectProcedure(procedureObj);
                            c.setOriginalTechnicsID(oriTechnicsID);
                            //����Ŀ�����ĸ����տ�
                            c.setParentTechnics(parentTechnics);
                            c.copyProcedureToTech();
                            //ˢ��Ŀ��ڵ�
                            CappTreeNode node = view.getProcessTreePanel().findNode(new
                                    TechnicsTreeObject(bvi));
                            view.getProcessTreePanel().nodeExpaned(node);
                            view.setCursor(Cursor.getDefaultCursor());
                            //view.updateNode(procedureObj, null);
                        }
                			
                        else
                        {
                            //���ƵĽڵ���Ϣ��Ŀ��ڵ����ݲ�һ��
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
                            throw new QMRemoteException(message);

                        }
                	}
               
                }
                //���ƹ���
                else if (object instanceof StepTreeObject)
                {
                	for(int j = 0, k = list.size(); j < k; j++)
                	{
	                	Object[]  obj = (Object[])list.get(j);
	                	copyOriginalInfo = (BaseValueInfo)obj[0];
	                	System.out.println("ճ��ʱ��ԭ��"+copyOriginalInfo);
	                	copyOriginalTreeObject = (CappTreeObject)obj[1];
	                	oriTechnicsID = (String)obj[2];
	                    //Դ�ڵ㣺���� Ŀ��ڵ㣺����
	                    if (copyOriginalTreeObject instanceof StepTreeObject)
	                    {
	                        boolean flag = CappClientHelper.isTypeSame((
	                                QMProcedureInfo)
	                                copyOriginalTreeObject.getObject(),
	                                (QMProcedureInfo) object.getObject());
	                        if (!flag)
	                        {
	                            throwPasteNotAllowedException();
	                        }
	                        view.startProgress();
	                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	                        //���Ŀ��ڵ㴦�ڼ���״̬����ִ�м��
	                        if (CheckInOutCappTaskLogic.isCheckInAndInVault((
	                                WorkableIfc) bvi))
	                        {
	
	                            //����ڵ㱾�����ڵ�
	                            bvi = checkOutParentNode(selectedNode,
	                                    parentTechnicsNode);
	                            parentTechnics = (QMTechnicsInfo)
	                                             CheckInOutCappTaskLogic.
	                                             getWorkingCopy((WorkableIfc)
	                                    parentTechnics);
	
	                        }
	
	                        QMProcedureInfo procedureObj = (QMProcedureInfo) bvi;
	                        copyOriginalInfo = CappClientHelper.refresh(
	                                copyOriginalInfo);
	                        StepCopyController c = new StepCopyController();
	                        //���Դ�ڵ���ԭ�������ø�����ֻ����������ʱԴ�ڵ�ſ�����ԭ����,��Ŀ��ڵ�Ƚ�
	                        if (((QMProcedureInfo) copyOriginalInfo).
	                            getWorkableState().equals("c/o"))
	                        {
	                            copyOriginalInfo = (QMProcedureInfo)
	                                               CheckInOutCappTaskLogic.
	                                               getWorkingCopy
	                                               ((WorkableIfc)
	                                                copyOriginalInfo);
	                        }
	                        //Դ�ڵ�ĸ�����Ŀ��ڵ�һ��
	                        if (copyOriginalInfo.getBsoID().equals(procedureObj.
	                                getBsoID()))
	                        {
	                            c.setOriginalProcedure(procedureObj);
	                            c.setObjectProcedure(procedureObj);
	                            //����Դ�ڵ�ĸ����տ�ID������Դ�ڵ��Ѿ�������ʴ˴���Ϊ���տ��ĸ�����
	                            c.setOriginalTechnicsID(parentTechnics.getBsoID());
	                            //����Ŀ�����ĸ����տ�
	                            c.setParentTechnics(parentTechnics);
	                            c.copy();
	                        }
	                        else
	                        {
	                            c.setOriginalProcedure((QMProcedureInfo)
	                                    copyOriginalInfo);
	                            c.setObjectProcedure(procedureObj);
	                            //����Դ�ڵ�ĸ����տ�ID������Դ�ڵ��Ѿ�������ʴ˴���Ϊ���տ��ĸ�����
	                            c.setOriginalTechnicsID(oriTechnicsID);
	                            //����Ŀ�����ĸ����տ�
	                            c.setParentTechnics(parentTechnics);
	                            c.copy();
	                        }
	                        //ˢ��Ŀ��ڵ�
	                        CappTreeNode node = view.getProcessTreePanel().findNode(new
	                                StepTreeObject(procedureObj));
	                        view.getProcessTreePanel().nodeExpaned(node);
	                        view.setCursor(Cursor.getDefaultCursor());
	                        // view.updateNode(procedureObj, parentTechnics.getBsoID());
	                    }
	                    //Դ�ڵ㣺���� �� Ŀ��ڵ㣺����
	                    else if (copyOriginalTreeObject instanceof
	                             OperationTreeObject)
	                    {
	                        boolean flag = CappClientHelper.isTypeSame((
	                                QMProcedureInfo)
	                                copyOriginalTreeObject.getObject(),
	                                (QMProcedureInfo) object.getObject());
	                        if (!flag)
	                        {
	                            throwPasteNotAllowedException();
	                        }
	                        view.startProgress();
	                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	                        //���Ŀ��ڵ㴦�ڼ���״̬����ִ�м��
	                        if (CheckInOutCappTaskLogic.isCheckInAndInVault((
	                                WorkableIfc) bvi))
	                        {
	
	                            //����ڵ㱾�����ڵ�
	                            bvi = checkOutParentNode(selectedNode,
	                                    parentTechnicsNode);
	                            parentTechnics = (QMTechnicsInfo)
	                                             CheckInOutCappTaskLogic.
	                                             getWorkingCopy((WorkableIfc)
	                                    parentTechnics);
	
	                        }
	
	                        QMProcedureInfo procedureObj = (QMProcedureInfo) bvi;
	                        ProcedureCopyController c = new ProcedureCopyController();
	                        c.setOriginalProcedure((QMProcedureInfo)
	                                               copyOriginalInfo);
	                        c.setObjectProcedure(procedureObj);
	
	                        //����Դ�ڵ�ĸ����տ�ID
	                        c.setOriginalTechnicsID(oriTechnicsID);
	                        //����Ŀ�����ĸ����տ�
	                        c.setParentTechnics(parentTechnics);
	                        c.copyProcedureToProcedure();
	
	                        //ˢ��Ŀ��ڵ�
	                        CappTreeNode node = view.getProcessTreePanel().findNode(new
	                                StepTreeObject(procedureObj));
	                        view.getProcessTreePanel().nodeExpaned(node);
	                        view.setCursor(Cursor.getDefaultCursor());
	
	                    }
	                    else
	                    {
	                        //���ƵĽڵ���Ϣ��Ŀ��ڵ����ݲ�һ��
	                        String message = QMMessage.getLocalizedMessage(
	                                RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
	                        throw new QMRemoteException(message);
	
	                    }
	
	                }
                }

                //���ƹ���
                else if (object instanceof OperationTreeObject)
                {
	                for(int j = 0, k = list.size(); j < k; j++)
	                {
		                Object[]  obj = (Object[])list.get(j);
		                copyOriginalInfo = (BaseValueInfo)obj[0];
		                System.out.println("ճ��ʱ��ԭ��"+copyOriginalInfo);
		                copyOriginalTreeObject = (CappTreeObject)obj[1];
		                oriTechnicsID = (String)obj[2];
	                    if (copyOriginalTreeObject instanceof
	                        OperationTreeObject)
	                    {
	                        boolean flag = CappClientHelper.isTypeSame((
	                                QMProcedureInfo)
	                                copyOriginalTreeObject.getObject(),
	                                (QMProcedureInfo) object.getObject());
	                        if (!flag)
	                        {
	                            throwPasteNotAllowedException();
	                        }
	                        view.startProgress();
	                        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	                        //���Ŀ��ڵ㴦�ڼ���״̬����ִ�м��
	                        if (CheckInOutCappTaskLogic.isCheckInAndInVault((
	                                WorkableIfc) bvi))
	                        {
	
	                            //����ڵ㱾�����ڵ�
	                            bvi = checkOutParentNode(selectedNode,
	                                    parentTechnicsNode);
	                            parentTechnics = (QMTechnicsInfo)
	                                             CheckInOutCappTaskLogic.
	                                             getWorkingCopy((WorkableIfc)
	                                    parentTechnics);
	
	                        }
	
	                        QMProcedureInfo procedureObj = (QMProcedureInfo) bvi;
	                        ProcedureCopyController c = new ProcedureCopyController();
	                        //���Դ�ڵ���ԭ�������ø�����ֻ����������ʱԴ�ڵ�ſ�����ԭ����
	                        if (((QMProcedureInfo) copyOriginalInfo).
	                            getWorkableState().equals("c/o"))
	                        {
	
	                            copyOriginalInfo = (QMProcedureInfo)
	                                               CheckInOutCappTaskLogic.
	                                               getWorkingCopy
	                                               ((WorkableIfc)
	                                                copyOriginalInfo);
	                            //Դ�ڵ�ĸ�����Ŀ��ڵ�һ��
	                        }
	                        if (copyOriginalInfo.getBsoID().equals(procedureObj.
	                                getBsoID()))
	                        {
	                            c.setOriginalProcedure(procedureObj);
	                            c.setObjectProcedure(procedureObj);
	                            //����Դ�ڵ�ĸ����տ�ID������Դ�ڵ��Ѿ�������ʴ˴���Ϊ���տ��ĸ�����
	                            c.setOriginalTechnicsID(parentTechnics.getBsoID());
	                            //����Ŀ�����ĸ����տ�
	                            c.setParentTechnics(parentTechnics);
	                            c.copyProcedureToProcedure();
	                        }
	                        else
	                        {
	                            c.setOriginalProcedure((QMProcedureInfo)
	                                    copyOriginalInfo);
	                            c.setObjectProcedure(procedureObj);
	                            //����Դ�ڵ�ĸ����տ�ID
	                            c.setOriginalTechnicsID(oriTechnicsID);
	                            //����Ŀ�����ĸ����տ�
	                            c.setParentTechnics(parentTechnics);
	                            c.copyProcedureToProcedure();
	                        }
	                        //ˢ��Ŀ��ڵ�
	                        CappTreeNode node = view.getProcessTreePanel().findNode(new
	                                StepTreeObject(procedureObj));
	                        view.getProcessTreePanel().nodeExpaned(node);
	                        view.setCursor(Cursor.getDefaultCursor());
	                        // view.updateNode(procedureObj, parentTechnics.getBsoID());
	
	                    }
	                    else
	                    {
	                        //���ƵĽڵ���Ϣ��Ŀ��ڵ����ݲ�һ��
	                        String message = QMMessage.getLocalizedMessage(
	                                RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
	                        throw new QMRemoteException(message);
	
	                    }
	
	                  }
	                }
	                else
	                {
	                    //���ƵĽڵ���Ϣ��Ŀ��ڵ����ݲ�һ��
	                    String message = QMMessage.getLocalizedMessage(
	                            RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
	                    throw new QMRemoteException(message);
	
	                }

            }
            catch (QMException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = ex.getClientMessage();
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());

            }
            finally
            {
                view.stopProgress();
                view.setCursor(Cursor.getDefaultCursor());
            }

        }
        list.clear();
        long d2 = System.currentTimeMillis();
        System.out.println("ճ��ʱ��=" + (d2 - d1));
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.paste() end...return is void");
        }

    }

    private void throwPasteNotAllowedException()
            throws QMRemoteException
    {
        //���ƵĽڵ���Ϣ��Ŀ��ڵ����ݲ�һ��
        String message = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.COPY_CONTENT_ERROR, null);
        throw new QMRemoteException(message);

    }


    /**
     * �������
     * @throws QMException 
     */
    private void checkIn() throws QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkIn() begin...");

        }

        CappTreeObject obje = view.getSelectedNode().getObject();
        if (obje != null)
        {
            if (obje instanceof TechnicsTreeObject)
            {
                try
                {

                    obje = view.update(obje);
                    //CCBegin SS1
//                    int res = JOptionPane.showConfirmDialog(view, "�Ƿ����������ļ���", "ѡ��", JOptionPane.YES_NO_OPTION);
//                    switch(res)
//                    {
//                    case JOptionPane.YES_OPTION:
                      //CCBegin SS3
                    //CCBegin SS4
                    //        				if(isBsxGroupUser()||getUserFromCompany().equals("zczx"))
                    if(isBsxGroupUser())
                    	//CCEnd SS4
        				{
        					SchDlg dlg = new SchDlg(view);
        				}
        				//CCEnd SS3
//                    }
                  //CCEnd SS1
                    BaseValueInfo obj = (BaseValueInfo) obje.getObject();
                    if (obj instanceof WorkableIfc)
                    {

                        CheckInCappController checkin_task = new
                                CheckInCappController(view);
                        if (!checkin_task.isCheckinAllowed((WorkableIfc) obj))
                        {
                            return;
                        }

                        //���ü������
                        checkin_task.setCheckinItem((WorkableIfc) obj);
                        //����
                        checkin_task.checkin();
                    }
                    
                }
                catch (NotCheckedOutException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);

                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);

                }

            }
            else
            {
                //��ʾ���������ʹ���
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkIn() end...return is void");
        }
    }


    /**
     * ����ָ���Ķ����Ƿ��ѱ����˼��
     * @param workable ָ���Ķ���
     * @throws QMException
     */
    /*private void testCheckOutByOther(WorkableIfc workable)
            throws QMException
         {
       // workable = CheckInOutCappTaskLogic.refresh(workable);
        //�����ǰ�����Ѿ��������˼��
        if (CheckInOutCappTaskLogic.isCheckedOutByOther(workable))
        {
            String s = "";
            try
            {
                UserIfc qmprincipal = CheckInOutCappTaskLogic.getCheckedOutBy(
                        workable);
                s = qmprincipal.getUsersName();
            }
            catch (QMException _ex)
            {
                s = "";
            }
            Object aobj1[] =
                    {
                    getIdentity((BaseValueIfc) workable), s
            };
            throw new CheckedOutByOtherException(
                    RESOURCE, CappLMRB.ALREADY_BY_OTHERS_CHECK_OUT, aobj1);
        }
         }*/


    /**
     * ���ѡ��Ĺ��տ�
     */
    private WorkableIfc checkOut()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() begin...");

        }
        WorkableIfc copyinfo = null;
        CappTreeObject object = view.getSelectedNode().getObject();
        if (object != null)
        {
            if (object instanceof TechnicsTreeObject)
            {
                try
                {
                    object = view.update(object);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return null;

                }
                BaseValueInfo info = (BaseValueInfo) object.getObject();
//              CCBegin by leixiao 2009-9-4 ԭ�򣺽����������·��,�ѷ�����·�߲��ܼ��
                //CCBegin by liunan 2012-03-06 ������������ض����ѷ���״̬�����޸ģ�����������ġ�
                //if (info instanceof QMTechnicsIfc&&((LifeCycleManagedIfc)info).getLifeCycleState().toString().equals("RELEASED")) {
                try
                {
                if (info instanceof QMTechnicsIfc&&((LifeCycleManagedIfc)info).getLifeCycleState().toString().equals("RELEASED")&&!isCappSupperGroup()) {
                //CCEnd by liunan 2012-03-06
                    String mm = ((QMTechnicsIfc)info).getTechnicsNumber() + "�ѷ��������ܼ����";
                    JOptionPane.showMessageDialog(view, mm);
                    return null;
                  }
                //CCBegin by liunan 2012-03-06 ������������ض����ѷ���״̬�����޸ģ�����������ġ�
                }
                catch (QMRemoteException e) {

                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                                  JOptionPane.INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return null;
                }
                //CCEnd by liunan 2012-03-06
//              CCEnd by leixiao 2009-9-4 ԭ�򣺽����������·��
                //add by wangh on 20080222
                if(info instanceof WorkableIfc)
                {
                boolean canCheckOut = true;
                try{
                if (CheckInOutCappTaskLogic.isCheckedOutByOther((
                        WorkableIfc) info, TechnicsRegulationsMainJFrame.currentUser))
                {
                    Object[] objs =
                            {
                            getIdentity(info)};
                    String message1 = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            CappLMRB.ALREADY_CHECKOUT_BY_OTHER, objs);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view,
                            message1,
                            title,
                            JOptionPane.INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return null;
                }
                }
                catch (QMRemoteException e) {

                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                                  JOptionPane.INFORMATION_MESSAGE);
                    view.setCursor(Cursor.getDefaultCursor());
                    return null;
				}
                }
                //add end
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                view.startProgress();
                copyinfo = getCheckOutObject((WorkableIfc)
                                             info);
                view.stopProgress();
                if (copyinfo != null)
                {
                    view.removeNode(info);
                    view.getProcessTreePanel().setNodeSelected(view.
                            getProcessTreePanel().findNode(new
                            TechnicsTreeObject((BaseValueInfo)
                                               copyinfo)).
                            getObject());

                }
                view.setCursor(Cursor.getDefaultCursor());
            }
            else
            {
                //��ʾ���������ʹ���
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOut() end...return is void");
        }
        return copyinfo;
    }


    /**
     * ���ָ���ڵ�����д��ڼ���״̬�ĸ��ڵ㣨�½������¡�ɾ��ĳ���򡢹����ڵ�ʱ������ñ�������
     * @param node ָ���ڵ�
     * @param parentTechNode �����տ�ͷ�ڵ�
     * @throws QMException
     */
    private BaseValueInfo checkOutParentNode(CappTreeNode node,
                                             CappTreeNode parentTechNode)
            throws
            QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutParentNode() begin...");
        }
        QMTechnicsInfo pTech = (QMTechnicsInfo) parentTechNode.getObject().
                               getObject();
//      CCBegin by leixiao 2009-9-4 ԭ�򣺽����������·��,�ѷ�����·�߲��ܼ��
        //CCBegin by liunan 2012-03-06 ������������ض����ѷ���״̬�����޸ģ�����������ġ�
        //if (pTech.getLifeCycleState().toString().equals("RELEASED")) {
        if (pTech.getLifeCycleState().toString().equals("RELEASED")&&!isCappSupperGroup()) {
        //CCEnd by liunan 2012-03-06

            throw new CheckedOutByOtherException(pTech.getTechnicsNumber()+"�ѷ��������ܼ����");

          }
//      CCEnd by leixiao 2009-9-4 ԭ�򣺽����������·��
        //�������������տ�ͷ�Ƿ��ѱ����˼������������˼�������ܼ�����
        if (CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc) pTech,
                TechnicsRegulationsMainJFrame.currentUser))
        {
            Object[] objs =
                    {
                    getIdentity(pTech)};
            throw new CheckedOutByOtherException(
                    RESOURCE, CappLMRB.ALREADY_BY_OTHERS_CHECK_OUT, objs);
        }

        // testCheckOutByOther((WorkableIfc) pTech);
        //�õ���Ҫ����Ľڵ�
        Vector nodes = view.getCheckInStateNodes(node);
        if (nodes.size() == 0)
        {
            return null;
        }

        Collection infos = new ArrayList();
        for (int i = 0; i < nodes.size(); i++)
        {
        	System.out.println("����ӵ㣺"+((CappTreeNode) nodes.elementAt(i)).getObject().
                    getObject());
            infos.add(((CappTreeNode) nodes.elementAt(i)).getObject().
                      getObject());

            //��������infos�еĶ���,���ظ�������
        }
        Class[] cla =
                {
                Collection.class, QMTechnicsIfc.class};
        Object[] obj =
                {
                infos,
                (QMTechnicsIfc) (parentTechNode.getObject().getObject())};
        Collection c = (Collection) useServiceMethod("StandardCappService",
                "checkOutArray", cla, obj);

        //ԭ���ĵ�һ���ڵ�
        CappTreeNode oriNode = (CappTreeNode) nodes.elementAt(0);
        //ԭ����һ���ڵ�ĸ��ڵ�
        CappTreeNode parentOfFirstNode = oriNode.getP();
        //�����ϵĹ��տ���ԭ���ڵ�ɾ��
        //begin CR1
//        view.removeNode((BaseValueInfo) oriNode.getObject().getObject());
        //end CR1
        //��������
        Object copy[] = c.toArray();
      //begin CR2
		for(int i = 0;i<copy.length;i++)
	    {
	    	CappTreeNode oldNode = (CappTreeNode) nodes.elementAt(i);
	    	BaseValueInfo copyInfo = (BaseValueInfo) copy[i];
	    	if (copyInfo instanceof QMTechnicsInfo) 
	    	{
	    	    TechnicsTreeObject treeObject = new TechnicsTreeObject(copyInfo);
	    	    TechnicsTreeObject oldTreeObject = (TechnicsTreeObject)oldNode.getObject();
	    	    int count = oldNode.getChildCount();
	    	    for(int p = 0;p<count;p++)
	    	    {
	    	    	CappTreeNode cappTreeNode = (CappTreeNode)oldNode.getChildAt(p);
	    	    	StepTreeObject stepTreeObject = (StepTreeObject)cappTreeNode.getObject();
	    	    	stepTreeObject.setParentID(copyInfo.getBsoID());
	    	    }
	    	    treeObject.setParentID(oldTreeObject.getParentID());
	    	    oldNode.setObject(treeObject);
	    	}
	    	else if(copyInfo instanceof QMProcedureInfo)
	    	{
	    		QMProcedureInfo info = (QMProcedureInfo)copyInfo;
	    		if(info.getIsProcedure())
	    		{
	    			StepTreeObject stepObject = new StepTreeObject(copyInfo);
	    			StepTreeObject oldTreeObject = (StepTreeObject)oldNode.getObject();
		    	    int count = oldNode.getChildCount();
		    	    for(int p = 0;p<count;p++)
		    	    {
		    	    	CappTreeNode cappTreeNode = (CappTreeNode)oldNode.getChildAt(p);
		    	    	OperationTreeObject operationTreeObject = (OperationTreeObject)cappTreeNode.getObject();
		    	    	operationTreeObject.setParentID(copyInfo.getBsoID());
		    	    }
		    	    stepObject.setParentID(oldTreeObject.getParentID());
	    			oldNode.setObject(stepObject);
	    		}
	    		else
	    		{
	    			OperationTreeObject operationObject = new OperationTreeObject(copyInfo);
	    			OperationTreeObject oldTreeObject = (OperationTreeObject)oldNode.getObject();
		    	    int count = oldNode.getChildCount();
		    	    for(int p = 0;p<count;p++)
		    	    {
		    	    	CappTreeNode cappTreeNode = (CappTreeNode)oldNode.getChildAt(p);
		    	    	OperationTreeObject operationTreeObject = (OperationTreeObject)cappTreeNode.getObject();
		    	    	operationTreeObject.setParentID(copyInfo.getBsoID());
		    	    }
		    	    operationObject.setParentID(oldTreeObject.getParentID());
	    			oldNode.setObject(operationObject);
	    		}
	    	}
	    }
        //չ�����տ��ĸ���
//        BaseValueInfo copyInfo = null;
//        CappTreeNode copyNode = null;
//        for (int i = 0; i < copy.length; i++)
//        {
//            copyInfo = (BaseValueInfo) copy[i];
//            System.out.println("copyInfo="+ copy[i]);
//            if (copyInfo instanceof QMTechnicsInfo)
//            {
//                TechnicsTreeObject treeObject = new TechnicsTreeObject(copyInfo);
//                view.getProcessTreePanel().addProcess(treeObject);
//                copyNode = view.findNode(treeObject);
//            }
//            else if (((QMProcedureInfo) copyInfo).getIsProcedure())
//            {
//                //�����һ���ڵ��ǹ������������
//                if (i == 0)
//                { //�õ����տ��ĸ���
//
//                    WorkableIfc copyTech = CheckInOutCappTaskLogic.
//                                           getWorkingCopy(
//                            (WorkableIfc) parentOfFirstNode.getObject().
//                            getObject());
////                    System.out.println("copyInfo================="+copyInfo);
//                    view.addProcedureNode(view.findNode(new
//                            TechnicsTreeObject((
//                            BaseValueInfo) copyTech)),
//                                          (QMProcedureInfo) copyInfo);
//                }
//
//                StepTreeObject stepObject = new StepTreeObject(copyInfo);
//                copyNode = view.findNode(stepObject);
//                System.out.println("vvvvcopynode="+copyNode);
//
//            }
//            //����
//            else if (!((QMProcedureInfo) copyInfo).getIsProcedure())
//            {
//                //�����һ���ڵ��ǹ��������������
//                if (i == 0)
//                { //�õ��ϼ��ڵ�ĸ���
//                    WorkableIfc copyTech = CheckInOutCappTaskLogic.
//                                           getWorkingCopy(
//                            (WorkableIfc) parentOfFirstNode.getObject().
//                            getObject());
//                    if (((QMProcedureInfo) copyTech).getIsProcedure())
//                    {
//                        view.addProcedureNode(view.findNode(new
//                                StepTreeObject((BaseValueInfo)
//                                               copyTech)),
//                                              (QMProcedureInfo) copyInfo);
//                    }
//                    else
//                    if (!((QMProcedureInfo) copyTech).getIsProcedure())
//                    {
//                        view.addProcedureNode(view.findNode(new
//                                OperationTreeObject((
//                                BaseValueInfo) copyTech)),
//                                              (QMProcedureInfo) copyInfo);
//                    }
//                }
//
//                OperationTreeObject operationObject = new OperationTreeObject(
//                        copyInfo);
//                copyNode = view.findNode(operationObject);
//           
//            }
//        
//            //���һ�������ڵ�ǰ�Ľڵ�չ��
//            if (i < copy.length - 1)
//            {
//                view.getProcessTreePanel().nodeExpaned(copyNode);
//               // copyNode = view.findNode(new StepTreeObject((BaseValueInfo)copy[1]));
//                
//
//            }
//        }
      //end CR1
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutParentNode() end...return is void");
        }
        return (BaseValueInfo) copy[c.size() - 1];
    }


    /* public void moveChild(CappTreeNode node)
     {
         if (node.getChildCount() == 0)
         {
             return;
         }
         //Ҫճ���Ķ���
         BaseValueInfo copyInfo = null;
         //node�ǹ���ԭ�������ø���
         try
         {
             if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) (node.
                     getObject().getObject())) &&
     CheckInOutCappTaskLogic.isInVault((WorkableIfc) (node.getObject().
                     getObject())))
             {

                 copyInfo = (BaseValueInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy
                            ((WorkableIfc) (node.getObject().getObject()));

             }
             //node����ͨ״̬����ñ���
             else
             if (!CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) (node.
                     getObject().getObject())) &&
                 !CheckInOutCappTaskLogic.
                 isInVault((WorkableIfc) (node.getObject().getObject())))
             {
                 copyInfo = (BaseValueInfo) node.getObject().getObject();

                 //node�ǹ����������򷵻�
             }
             else
             if (CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc) (node.
                     getObject().getObject())) &&
                 !CheckInOutCappTaskLogic.
                 isInVault((WorkableIfc) (node.getObject().getObject())))
             {
                 return;
             }
         }
         catch (QMRemoteException ex)
         {
             if(verbose)
             ex.printStackTrace();
     String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
             JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                           JOptionPane.INFORMATION_MESSAGE);
         }

         //�����һ���ڵ�
         Vector v = new Vector();
         for (int i = 0; i < node.getChildCount(); i++)
         {
             v.add(node.getChildAt(i));
             //Ҫճ�������ڵ�
         }
         CappTreeNode copyNode = new CappTreeNode();
         if (copyInfo instanceof QMTechnicsInfo)
         {
     TechnicsTreeObject technicsObject = new TechnicsTreeObject(copyInfo);
             copyNode = view.getProcessTreePanel().findNode(technicsObject);

         }
         else if ((copyInfo instanceof QMProcedureIfc) &&
                  ((QMProcedureInfo) copyInfo).getIsProcedure())
         {
             StepTreeObject stepObject = new StepTreeObject(copyInfo);
             copyNode = view.getProcessTreePanel().findNode(stepObject);
         }
         else if ((copyInfo instanceof QMProcedureIfc) &&
                  !((QMProcedureInfo) copyInfo).getIsProcedure())
         {
             StepTreeObject stepObject = new StepTreeObject(copyInfo);
             copyNode = view.getProcessTreePanel().findNode(stepObject);
         }
         for (int j = 0; j < v.size(); j++)
         {
             view.removeNode((BaseValueInfo) (((CappTreeNode) v.elementAt(j)).
                                              getObject().getObject()));
             QMProcedureInfo procedure = (QMProcedureInfo) (((CappTreeNode) v.
                     elementAt(j)).getObject().getObject());
             view.addNode(copyNode, procedure);
         }
         for (int j = 0; j < v.size(); j++)
         {
             moveChild((CappTreeNode) v.elementAt(j));
         }

     }

     */
    /**
     * ������������������������������ڵ㣩
     * @param foldered_obj �������
     * @param parentNode �������ĸ��ڵ�
     * @return boolean �������ɹ�,����true,���򷵻�false
     */
    /* protected boolean checkOutObject(FolderedIfc foldered_obj,
                                      CappTreeNode parentNode)
     {
         if (verbose)
         {
             System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutObject() begin..."
                                + "�������:" + foldered_obj + " parentNode: " +
                                parentNode);
             //����Ƿ����ɹ�
         }
         boolean successful_checkout = false;

         if (foldered_obj instanceof WorkableIfc)
         {
             try
             {
                 CheckOutCappController checkout_task = new
                         CheckOutCappController(
                         view,
                         CheckInOutCappTaskLogic.getCheckoutFolder());
                 checkout_task.setItem((WorkableIfc) foldered_obj);
                 checkout_task.setSelectedNodeParent(parentNode);
                 checkout_task.checkout();
                 successful_checkout = true;

             }
             catch (QMRemoteException e)
             {
                 String title = QMMessage.getLocalizedMessage(
                         RESOURCE, "information", null);
     JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
     JOptionPane.INFORMATION_MESSAGE);
             }
             catch (Exception e)
             {
                 String message = QMMessage.getLocalizedMessage(
                         RESOURCE, CappLMRB.CHECK_OUT_FAILURE, null);
                 String title = QMMessage.getLocalizedMessage(
                         RESOURCE, "information", null);
                 JOptionPane.showMessageDialog(view, message, title,
     JOptionPane.INFORMATION_MESSAGE);
                 successful_checkout = false;
             }
         }

         if (verbose)
         {
             System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.checkOutObject() end...return: " +
                                successful_checkout);
         }
         return successful_checkout;
     }
     */

    /**
     * �������
     */
    private void cancelCheckOut()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.cancelCheckOut() begin...");
        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if (object != null)
        {
            if (object instanceof TechnicsTreeObject)
            {
                try
                {
                    object = view.update(object);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;

                }
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BaseValueInfo obj = (BaseValueInfo) object.getObject();
                if (obj instanceof WorkableIfc)
                {
                    UndoCheckOutController undo_checkout_task =
                            new UndoCheckOutController(view, (WorkableIfc) obj);
                    undo_checkout_task.undoCheckout();
                }
                view.setCursor(Cursor.getDefaultCursor());
            }
            else
            {
                //��ʾ���������ʹ���
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.cancelCheckOut() end...return is void");
        }
    }


    /**
     * �޶��汾
     */
    private void revise()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.revise() begin...");
        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof TechnicsTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
//          CCBegin by leixiao 2009-9-4 ԭ�򣺽����������·��,���ѷ�����·�߲����޶�
            if (!isReleased(obj)) {
                String mm = ((QMTechnicsIfc)obj).getTechnicsNumber() + "�����ѷ���״̬�������޶���";
                JOptionPane.showMessageDialog(view, mm);
                return ;
              }
//          CCEnd by leixiao 2009-9-4 ԭ�򣺽����������·��
            if (obj instanceof VersionedIfc)
            {
                VersionedIfc version = (VersionedIfc) obj;
                ReviseCappController revise_task = new ReviseCappController(
                        view, version);
                revise_task.revise();
            }
            view.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            //��ʾ���������ʹ���
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.revise() end...return is void");
        }
    }


//  CCBegin by leixiao 2009-9-4 ԭ�򣺽����������·��,���ѷ�����·�߲����޶�
    /**
     * �жϣ�·�߱��Ƿ��ڷ���״̬
     * @param info TechnicsRouteListIfc
     * @return boolean
     */
    private boolean isReleased(BaseValueInfo info)
    {
      LifeCycleManagedIfc lcm = (LifeCycleManagedIfc)info;
//Ϊ����״̬����ҵ�ڿص�״̬ʱ�����޶�
      if(lcm.getLifeCycleState().toString().equals("RELEASED")
    		  ||lcm.getLifeCycleState().toString().equals("FEIMI")
    		  ||lcm.getLifeCycleState().toString().equals("MIMI")
    		  ||lcm.getLifeCycleState().toString().equals("JIMI")
    		  ||lcm.getLifeCycleState().toString().equals("QIYENEIKONG"))
      {
        return true;
      }
      return false;
    }
//  CCEnd by leixiao 2009-9-4 ԭ�򣺽����������·��,���ѷ�����·�߲����޶�
    
    
    /**
     * �鿴���տ��İ汾��ʷ
     */
    private void viewVersionHistory()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewVersionHistory() begin...");
        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof TechnicsTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
            if (obj instanceof WorkableIfc)
            {
                WorkableIfc workable = (WorkableIfc) obj;
                String objID = workable.getBsoID();
                HashMap hashmap = new HashMap();
                hashmap.put("bsoID", objID);
                //ת��"�汾��ʷ"ҳ��
                RichToThinUtil.toWebPage(
                        "capp_version_viewTechnicsVersionHistory.screen",
                        hashmap);
            }

            view.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            //��ʾ���������ʹ���
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewVersionHistory() end...return is void");
        }
    }


    /**
     * �鿴���տ������򡢹�����������ʷ
     */
    private void viewIterationHistory()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewIterationHistory() begin...");

        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof BusinessTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();

            if (obj instanceof WorkableIfc)
            {
                WorkableIfc workable = (WorkableIfc) obj;
                String objID = null;
                HashMap hashmap = new HashMap();
                if (workable instanceof IteratedIfc)
                {
                    MasteredIfc master = ((IteratedIfc) workable).getMaster();
                    objID = master.getBsoID();
                    hashmap.put("masterBsoID", objID);
                }
                if (obj instanceof QMFawTechnicsInfo)
                {
                    //ת�����տ�"������ʷ"ҳ��
                	QMTechnicsInfo technics =
                        (QMTechnicsInfo) view.getParentTechnicsNode().
                        getObject().getObject();
                	String bsoID = technics.getBsoID();
                	hashmap.put("bsoID", bsoID);
                    RichToThinUtil.toWebPage(
                            "capp_version_viewTechnicsIterationsHistory.screen",
                            hashmap);
                }
                if (obj instanceof QMProcedureInfo)
                {

                    QMTechnicsInfo technics =
                            (QMTechnicsInfo) view.getParentTechnicsNode().
                            getObject().getObject();
                    String technicsID = technics.getBsoID();
                    String bsoID = obj.getBsoID();
                    hashmap.put("technicsBsoID", technicsID);
                    hashmap.put("bsoID", bsoID);
                    if (((QMProcedureInfo) obj).getIsProcedure())
                    {
                        if (verbose)
                        {
                            System.out.println("��������Ϊ��bsoID = " + objID +
                                               "  technicsBsoID = " +
                                               technicsID);
                        }
                        //ת������"������ʷ"ҳ��
                        RichToThinUtil.toWebPage(
                                "capp_version_viewProcedureStepIterationsHistory.screen",
                                hashmap);
                    }
                    else
                    {
                        if (verbose)
                        {
                            System.out.println("��������Ϊ��bsoID = " + objID +
                                               "  technicsBsoID = " +
                                               technicsID);
                        }
                        hashmap.put("BsoID", objID);
                        //ת������"������ʷ"ҳ��
                        RichToThinUtil.toWebPage(
                                "capp_version_viewProcedurePaceIterationsHistory.screen",
                                hashmap);
                    }
                }
            }
            view.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            //��ʾ���������ʹ���
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewIterationHistory() end...return is void");
        }
    }


    /**
     * ������������״̬
     */
    private void setLifeCycleState()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.setLifeCycleState() begin...");
        }
        CappTreeObject obj = view.getSelectedNode().getObject();
        if ((obj != null) && (obj instanceof TechnicsTreeObject))
        {
            try
            {
                obj = view.update(obj);
            }
            catch (QMRemoteException ex1)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(view, ex1.getClientMessage(),
                                              title,
                                              JOptionPane.
                                              INFORMATION_MESSAGE);
                return;

            }
            BaseValueInfo info = (BaseValueInfo) obj.getObject();
            if (info instanceof QMFawTechnicsInfo)
            {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SetLifeCycleStateDialog dialog = null;
                try
                {
                    dialog = new SetLifeCycleStateDialog(view,
                            (QMFawTechnicsInfo) info);
                    dialog.setModal(true);
                    dialog.setVisible(true);
                    Class[] paraclass =
                            {  
                            BaseValueIfc.class, Boolean.TYPE};
                    Object[] paraobj =
                            {
                            (QMTechnicsInfo) dialog.getObject(),
                            new Boolean(false)};
                    QMTechnicsInfo technics = (QMTechnicsInfo) useServiceMethod(
                            "PersistService", "updateValueInfo", paraclass,
                            paraobj);
                    TechnicsTreeObject treeObject = new TechnicsTreeObject(
                            technics);
                    view.getProcessTreePanel().updateNode(treeObject);
                }
                catch (QMException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }
                view.setCursor(Cursor.getDefaultCursor());
            }
        }
        else
        {
            //��ʾ���������ʹ���
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.setLifeCycleState() end...return is void");
        }
    }


    /**
     * ����ָ����������
     */
    private void afreshAppointLifeCycle()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointLifeCycle() begin...");

        }
        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof TechnicsTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
            QMTechnicsInfo technics;
            if (obj instanceof QMTechnicsInfo)
            {
                technics = (QMTechnicsInfo) obj;
            }
            else
            {
                //��ʾ���������ʹ���
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, message, title, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String title = QMMessage.getLocalizedMessage(
                    RESOURCE, "afreshAssignLifeCycle", null);
            LifeCycleStateDialog a = new LifeCycleStateDialog(view, title, true);
            String str = getIdentity(technics);
            a.setName(str);
            a.setLifeCycleManaged((LifeCycleManagedIfc) technics);
            a.setVisible(true);

            if (!a.isShowing())
            {
                if (a.getObject() != null)
                {
                    TechnicsTreeObject treeObject = new TechnicsTreeObject(
                            (QMTechnicsInfo) a.getObject());

                    view.getProcessTreePanel().updateNode(treeObject);

                }

            }
        }
        else
        {
            //��ʾ���������ʹ���
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointLifeCycle() end...return is void");
        }
    }


    /**
     * �鿴����������ʷ
     */
    private void viewLifeCycleHistory()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewLifeCycleHistory() begin...");
        }

        CappTreeObject object = view.getSelectedNode().getObject();
        if ((object != null) && (object instanceof BusinessTreeObject))
        {
            try
            {
                object = view.update(object);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BaseValueInfo obj = (BaseValueInfo) object.getObject();
            QMTechnicsInfo technics;
            if (obj instanceof QMTechnicsInfo)
            {
                technics = (QMTechnicsInfo) obj;
            }
            else
            {
                //��ʾ���������ʹ���
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(
                        view, message, title, JOptionPane.INFORMATION_MESSAGE);
                view.setCursor(Cursor.getDefaultCursor());
                return;
            }
            String technicsBsoID = technics.getBsoID();
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", technicsBsoID);
            String docName = technics.getTechnicsNumber() + "(���)" +
                             technics.getTechnicsName() + "(����)";
            hashmap.put("docName", docName);
            //��technicsBsoID�������鿴����������ʷ��¼��ҳ��
            RichToThinUtil.toWebPage("capp_look_lifeCyleHistory.screen",
                                     hashmap);
            view.setCursor(Cursor.getDefaultCursor());
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.viewLifeCycleHistory() end...return is void");
        }
    }


    /**
     * ����ָ��������
     */
    private void afreshAppointProject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointProject() begin...");
        }
        try
        {
            CappTreeObject object = view.getSelectedNode().getObject();
            if ((object != null) && (object instanceof BusinessTreeObject))
            {
                object = view.update(object);
                BaseValueInfo obj = (BaseValueInfo) object.getObject();
                QMTechnicsInfo technics;
                if (obj instanceof QMTechnicsInfo)
                {
                    technics = (QMTechnicsInfo) obj;
                }
                else
                {
                    //��ʾ���������ʹ���
                    String message = QMMessage.getLocalizedMessage(
                            RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(
                            view, message, title,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String title = QMMessage.getLocalizedMessage(
                        RESOURCE, "afreshAssignProject", null);
                ProjectStateDialog a = new ProjectStateDialog(view, title, true);
                String str = getIdentity(technics);
                a.setName(str);
                a.setLifeCycleManaged((LifeCycleManagedIfc) technics);
                a.setVisible(true);
                if (!a.isVisible())
                {
                    ;
                }
                {
                    Class[] paraclass =
                            {
                            BaseValueIfc.class, Boolean.TYPE};
                    Object[] paraobj =
                            {
                            (QMTechnicsInfo) a.getObject(),
                            new Boolean(false)};
                    technics = (QMTechnicsInfo) useServiceMethod(
                            "PersistService",
                            "updateValueInfo", paraclass, paraobj);
                    TechnicsTreeObject treeObject = new TechnicsTreeObject(
                            technics);
                    view.getProcessTreePanel().updateNode(treeObject);
                }
            }
        }
        catch (QMException ex)
        {
            if (verbose)
            {
                ex.printStackTrace();
            }
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.afreshAppointProject() end...return is void");
        }
    }


    /**
     * ����幤������
     */
    private void partCappDerive()
    {
    }


    /**
     * �ӹ�·�߹�������
     */
    private void machineCappDerive()
    {
    }


    /**
     * ���õ�ǰ��ɸѡ����.
     * @param refresh = true :�������õ�ɸѡ����ˢ�¹�����ҳ���ڵ��㲿��
     *                =false :��ˢ�¹�����ҳ���ڵ��㲿��
     */
    public void setConfigSpecCommand()
    {
        view.getPartTreePanel().setConfigSpecCommand();
    }


    /**
     * ���ù���
     */
    private void configureCriterion()
    {
        view.getPartTreePanel().configureCriterion();
    }


    /**
     * ���豸��������
     */
    private void searchInEquipment()
    {
        TechnicsSearchFromEquipmentJDialog dialog = new
                TechnicsSearchFromEquipmentJDialog(view);
        dialog.setVisible(true);
    }


    /**
     * ����װ��������
     */
    private void searchInTools()
    {
        TechnicsSearchFromToolJDialog dialog = new
                                               TechnicsSearchFromToolJDialog(
                view);
        dialog.setVisible(true);
    }


    /**
     * ��������������
     */
    private void searchInMaterial()
    {
        TechnicsSearchFromMaterialJDialog dialog = new
                TechnicsSearchFromMaterialJDialog(view);
        dialog.setVisible(true);
    }
//CCBegin SS11
    /**
	 * �ṹ�������չ��
	 */
	private void searchStructTechnics() {
		TechnicsStructSearchDialog searchByStructJDialog = null;
		try {
			searchByStructJDialog = new TechnicsStructSearchDialog(view);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		searchByStructJDialog.setModal(false);
		searchByStructJDialog.setVisible(true);

	}
    //CCEnd SS11

    /**
     * �������չ��
     */
    private void searchTechnics()
    {

        try
        {
            d = new TechnicsSearchJDialog(view);
            d.addMultiListActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    view.myList_actionPerformed(e);
                    d.setVisible(false);
                }
            });

            d.addDefaultListener();
            view.setMenuItemEnable(false, "public_search");
            d.setVisible(true);
            view.setMenuItemEnable(true, "public_search");
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(view, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }

    }


    /**
     * ���չ�̹���
     */
    private void helpTechnicsManager()
    {
    }

//  delete by wangh on 20061206(ȥ��û�����õķ���)
//    /**���ĳ����󣬻�����µ�ԭ���ڵ�*/
//    private CappTreeNode updateNode;

//    private CappTreeNode getUpdateNode()
//    {
//        return updateNode;
//    }


    /**
     * ��ü����Ķ���,�������ɹ�,���ؼ����Ķ���,������ʧ��,����null.
     * @param workable ��������
     * @return
     */
    protected WorkableIfc getCheckOutObject(WorkableIfc workable)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.getCheckOutObject() begin...");
        }
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        try
        {
            CheckOutCappController checkout_task = new CheckOutCappController(
                    view);
            //CheckInOutCappTaskLogic.getCheckoutFolder());
            if (view.getSelectedNode().getObject().getObject() instanceof
                QMProcedureInfo)
            {
                checkout_task.setSelectedNodeParent(view.getSelectedNode().
                        getP());
            }
            else
            {
                checkout_task.setSelectedNodeParent(view.getSelectedNode());
            }
            checkout_task.setCheckoutItem(workable);
            workable = checkout_task.checkout();
        }
        catch (AlreadyCheckedOutException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;

        }
        catch (CheckedOutByOtherException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;
        }
        catch (QMRemoteException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;
        }
        catch (LockException e)
        {
            JOptionPane.showMessageDialog(view, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            workable = null;
        }
        catch (QMException e)
        {
            e.printStackTrace();
            workable = null;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.controller.TechnicsRegulationsMainController.getCheckOutObject() end...return: " +
                               workable);
        }
        return workable;
    }


    /**
     * ����������ӽڵ�
     */
    public void addNode(CappTreeNode parentNode, CappTreeObject obj)
    {
        view.getProcessTreePanel().addNode(parentNode, obj);
    }


    /**
     * �½�����
     */
    private void createObject()
    {
        ObjectSelectJDialog dialog = new ObjectSelectJDialog(this, view);
        dialog.setVisible(true);
    }

    private void searchTreeObject()
    {
        view.searchTreeObject();
    }


    /**
     * ˢ�¹��տ�.
     * @param modifiedTechnics ��ˢ�µĹ��տ�.
     */
    protected void refreshExplorerPart(QMTechnicsInfo modifiedTechnics)
    {
        WorkThread work = new WorkThread(
                getThreadGroup(), REFRESH_TECHNICS, modifiedTechnics);
        work.start();
        try
        {
            work.join();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * �ڲ�Ʒ�������Ͻ��޸ĵĹ��տ�ˢ��
     * @param modifiedPart���޸ĵĹ��տ�
     */
    protected void refreshQMTechnics(QMTechnicsInfo modifiedTechnics)
    {

    }
    //CCBegin SS1
    /**
     * ���������ļ�
     * @throws Exception 
     */
    public void schedule(String type) throws Exception
    {
        Vector vec = new Vector();
        vec.add(type);
        if(view.getContentJPanel().schedule(vec))
        {
           System.out.println("schedule         ----------------");
            int res = JOptionPane.showConfirmDialog(view, "ϵͳ�������������ļ����Ƿ���Ҫ����У�ԣ�", "ѡ��", JOptionPane.YES_NO_OPTION);
            switch(res)
            {
            case JOptionPane.YES_OPTION:
                String clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
                String filePName = clientPath + "/"+type + ".xls";
                Desktop.getDesktop().open(new File(filePName)); 
            }

        }
        // JOptionPane.showMessageDialog(view, type + "�����ɹ�!", "", JOptionPane.INFORMATION_MESSAGE);

    }
    // CCEnd SS1
    /**
     * ��õ�ǰ�û�
     * @return String ��ǰ�û�
     */
    public UserIfc getCurrentUser()
            throws QMRemoteException
    {
        UserIfc sysUser = (UserIfc) useServiceMethod("SessionService",
                "getCurUserInfo", null, null);
        return sysUser;
    }
    
    //Begin CR4
    /**
     * �Ƴ���ѡ����
     * @param modifiedPart���޸ĵĹ��տ�
     */
    public void moveOutQMTechnics()
    {
    	CappTreeObject object=null;
        CappTreeNode[] nodes = view.getSelectedNodes();
    	if(nodes==null||nodes.length==0)
    	{
    		
    		return;
    	}
        for(int i = 0; i < nodes.length; i++)
        {
		    object = nodes[i].getObject();
	        if (object instanceof TechnicsTreeObject)
	        {
	            view.getProcessTreePanel().removeNode(object);
	
	        }
        }

    }
    //End CR4
    
    //CCBegin by liunan 2012-03-06 ������������ض����ѷ���״̬�����޸ģ�����������ġ�
    private boolean isCappSupperGroup() throws QMRemoteException
    {    	
    	Class[] paraclass = {};
    	Object[] paraobj = {};    	
    	UserInfo user = (UserInfo) useServiceMethod("SessionService", "getCurUserInfo", paraclass, paraobj);
    	//System.out.println("user��"+user.getUsersDesc());
    	Class[] paraclass1 = {UserInfo.class, boolean.class, boolean.class};
    	Object[] paraobj1 = {(UserInfo)user, Boolean.FALSE, Boolean.FALSE};
    	Enumeration enum1 = (Enumeration) useServiceMethod("UsersService","parentGroups", paraclass1,paraobj1);
    	while (enum1.hasMoreElements())
    	{
    		GroupInfo gp = (GroupInfo) enum1.nextElement();
    		//System.out.println("������"+gp.getUsersDesc());
    		if(gp.getUsersDesc().indexOf("���ճ���Ȩ����")!=-1)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    //CCEnd by liunan 2012-03-06
    //CCBegin SS1
    public void schedules(Vector types)
    {
        try
        {
            if(view.getContentJPanel().schedule(types))
            {
                String clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
                for(int i = 0;i < types.size();i++)
                {
                    String filePName = clientPath + "/" + (String)types.get(i) + ".xls";
                    Desktop.getDesktop().open(new File(filePName));
                }
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public class SchDlg extends JDialog
    {
        public SchDlg()
        {
            try
            {
                jbInit();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        public SchDlg(JFrame parent)
        {
            super(parent);
            try
            {                
                jbInit();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        private void jbInit() throws Exception
        {
            this.setModal(true);
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setResizable(false);
            //CCBegin SS5
            //setSize(300, 300);
            setSize(300, 400);
            //CCEnd SS5
            setLocation(300, 300);

            this.getContentPane().setLayout(gridBagLayout1);
            jj.setText("�о���ϸ��");
            wn.setText("���ܹ����嵥");
            //CCBegin SS5
            lj.setText("���������嵥");
            gw.setText("��λ�����嵥");
            //CCEnd SS5
            mj.setText("ĥ��һ����");
            jfj.setText("�и���һ����");
            zp.setText("װ�乤��һ����");
            dj.setText("����һ����");
            okBtn.setText("ȷ��");
            cancelBtn.setText("ȡ��");
            sb.setText("�豸�嵥");
            //CCBegin SS5
            /*this.getContentPane().add(zp, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 12, 0));
            this.getContentPane().add(dj, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 36, 0));
            this.getContentPane().add(jfj, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(mj, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 36, 0));
            this.getContentPane().add(sb, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 48, 0));
            this.getContentPane().add(wn, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(jj, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(26, 98, 0, 105), 36, 0));
            this.getContentPane().add(okBtn, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 57, 35, 0), 24, 0));
            this.getContentPane().add(cancelBtn, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 63, 35, 42), 24, 0));*/
            
            this.getContentPane().add(zp, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 12, 0));
            this.getContentPane().add(dj, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 36, 0));
            this.getContentPane().add(jfj, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(mj, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 36, 0));
            this.getContentPane().add(sb, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 48, 0));
            
            this.getContentPane().add(gw, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(lj, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 98, 0, 105), 20, 0));
            
            this.getContentPane().add(wn, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 98, 0, 105), 24, 0));
            this.getContentPane().add(jj, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(26, 98, 0, 105), 36, 0));
            this.getContentPane().add(okBtn, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 57, 35, 0), 24, 0));
            this.getContentPane().add(cancelBtn, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(37, 63, 35, 42), 24, 0));
            //CCEnd SS5
            cancelBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    setVisible(false);
                    dispose();
                }
            });
            okBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Vector types = new Vector();
                    if(jj.isSelected())
                        types.add("�о���ϸ��");
                    if(wn.isSelected())
                        types.add("���ܹ����嵥");
                    //CCBegin SS5
                    if(lj.isSelected())
                        types.add("���������嵥");
                    if(gw.isSelected())
                        types.add("��λ�����嵥");
                    //CCEnd SS5
                    if(sb.isSelected())
                        types.add("�豸�嵥");
                    if(mj.isSelected())
                        types.add("ĥ��һ����");
                    if(dj.isSelected())
                        types.add("����һ����");
                    if(jfj.isSelected())
                        types.add("�и���һ����");
                    if(zp.isSelected())
                        types.add("װ�乤��һ����");
                    schedules(types);
                    setVisible(false);
                    dispose();
                }
            });

            setVisible(true);
        }

        JCheckBox jj = new JCheckBox();
        JCheckBox wn = new JCheckBox();
        //CCBegin SS5
        JCheckBox lj = new JCheckBox();
        JCheckBox gw = new JCheckBox();
        //CCEnd SS5
        JCheckBox sb = new JCheckBox();
        JCheckBox mj = new JCheckBox();
        JCheckBox jfj = new JCheckBox();
        JCheckBox zp = new JCheckBox();
        JCheckBox dj = new JCheckBox();
        JButton okBtn = new JButton();
        JButton cancelBtn = new JButton();
        GridBagLayout gridBagLayout1 = new GridBagLayout();

    }   
    //CCEnd SS1
  //CCBegin SS3
  	private boolean isBsxGroupUser() throws QMException 
  	{
  		Vector groupVec = new Vector();  		
  		UserIfc sysUser = getCurrentUser();
  		Class[] paraclass1 = {UserInfo.class, boolean.class};
  		Object[] paraobj1 = {(UserInfo)sysUser, Boolean.TRUE};
  		Enumeration groups = (Enumeration) useServiceMethod("UsersService","userMembers", paraclass1,paraobj1);
  		for (; groups.hasMoreElements();) 
  		{			
  			GroupIfc group = (GroupIfc) groups.nextElement();
  			String groupName = group.getUsersName();
  			if (groupName.equals("�������û���")) 
  			{
  				return true;
  			}
  		}
  		return false;
  	}
  //CCEnd SS3
  //CCBegin SS4
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
  	//CCEnd SS4

    //CCBegin SS13
    public void exportQMProcedure()
    {
            QMProcedureIfc qmProcedureIfc = null;
            String technicsID = null;
            String qmProcedureId = null;
            File file = null;
            //
            JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("����");
        fileChooser.setApproveButtonText("����");
        int interval = fileChooser.showOpenDialog(view);
        if (interval == fileChooser.APPROVE_OPTION)
        {
            file = fileChooser.getSelectedFile();
            CappTreeObject obj = view.getSelectedNode().getObject();
                if(obj!=null)
                {
                        Object procedureObj = obj.getObject();
                        if(procedureObj instanceof QMProcedureIfc)
                    {
                            qmProcedureIfc = (QMProcedureIfc)procedureObj;
                        qmProcedureId = qmProcedureIfc.getBsoID();
                        if(qmProcedureIfc.getIsProcedure())
                        {
                                if(obj instanceof StepTreeObject)
                            {
                                    StepTreeObject parentObj = (StepTreeObject)obj;
                                    technicsID = parentObj.getParentID();
                            }
                        }
                    }
                    else if(procedureObj instanceof QMFawTechnicsIfc)
                    {
                    	QMFawTechnicsIfc ifc = (QMFawTechnicsIfc)procedureObj;
                    	qmProcedureId = ifc.getBsoID();
                    	technicsID = ifc.getBsoID();
                    }
                    
                try
                {
                  //
                  Class[] myClass = {String.class,String.class};
                  Object[] myObj = {technicsID,qmProcedureId};
                  System.out.println(technicsID+"==����=="+qmProcedureId);
                   Vector v = (Vector)useServiceMethod("StandardCappService","exportQMProcedureControlPlan",myClass,myObj);
                    //ͨ������������Ĺ������server
                            String s = "";
                            if(v!=null&& v.size()>0)
                            {
                                    for(int i=0;i<v.size();i++)
                                    {
                                            s = s + v.get(i).toString();
                                    }
                                    String filename = file.getAbsolutePath();
                                    if (!filename.endsWith(".csv"))
                            {
                                filename = filename + ".csv";
                            }
                                    FileWriter filewriter = new FileWriter(filename, false);
                            filewriter.write(s);
                            filewriter.flush();
                            filewriter.close();
                            }
                    }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
                }
        }
    }
    //CCEnd SS13
}
