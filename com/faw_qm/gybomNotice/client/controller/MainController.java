/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �޸�TD8401 ����ʱ���˵����治�ɹ�  ���� 2014-7-16
 * SS2 �Զ�����BOM������ lishu 2017-5-12
 */

package com.faw_qm.gybomNotice.client.controller;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.faw_qm.bomNotice.client.util.NoticeClientUtil;
import com.faw_qm.bomNotice.client.view.BomAdoptNoticeViewPanel;
import com.faw_qm.bomNotice.client.view.BomSubAdoptNoticeViewPanel;
import com.faw_qm.bomNotice.client.view.SearchBomAdoptNoticeDialog;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.bomNotice.util.NoticeHelper;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.client.util.GYBomNoticeTreeObject;
import com.faw_qm.gybomNotice.client.view.CJAdoptNoticeViewPanel;
import com.faw_qm.gybomNotice.client.view.GYBomNoticeMainJFrame;
import com.faw_qm.gybomNotice.client.view.SearchGYBomNoticeDialog;
import com.faw_qm.gybomNotice.client.view.ZCAdoptNoticeAdjustPanel;
import com.faw_qm.gybomNotice.client.view.ZCBomAdoptNoticeViewPanel;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.util.QMCt;

/**
 * <p>Title:���ñ��֪ͨ���Ŀ����� </p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright (c) 2014</p> 
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class MainController extends QMThread
{

	GYBomNoticeMainJFrame view = null;
    /** �߳��� */
    private ThreadGroup threadGroup = null;
    public WorkThread theWorkThread;
    private static boolean verbose = GYNoticeHelper.VERBOSE;

    /** �˵�������ʶ */
    private final static int CREATE_CARLOAD = 1;
    private final static int CREATE_CARBODY = 2;
    private final static int CREATE_CARFRAME = 3;
    private final static int SEARCH_NOTICE = 4;
    private final static int SAVE_NOTICE = 5;
    private final static int SAVEAS_NOTICE = 6;
    private final static int DISUSE_NOTICE = 7;
    private final static int UPDATE_NOTICE = 8;
    private final static int ZhuanYongJian = 9;
    private final static int EXPORT_BOM = 10;
    private final static int VIEW_BOM = 11;
    private final static int EXPORT_CARLOADBOM = 12;
    private final static int VIEW_CARLOADBOM = 13;
    private final static int VIEW_NOTICE = 14;
    private final static int RIGHT_DELETE = 15;
    private final static int RIGHT_UPDATE = 16;
    //CCBegin SS2
    private final static int AUTOCREATE_MODE = 17;
    //CCEnd SS2
   /** 
     *���ڴ�ż���ֵ�����Vector 
     */
    private HashMap hashMap = new HashMap();
    
    

    /**
     * �����������
     * @param frame ������
     */
    public MainController(GYBomNoticeMainJFrame frame)
    {
        view = frame;
        this.threadGroup = QMCt.getContext().getThreadGroup();
    }

    public void processCreateCarloadCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_CARLOAD);
        work.start();
    }

    public void processCreateCarBodyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_CARBODY);
        work.start();
    }
    public void processCreateCarFrameCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_CARFRAME);
        work.start();
    }
    public void processSearchNoticeCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SEARCH_NOTICE);
        work.start();
    }
    public void processSaveNoticeCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SAVE_NOTICE);
        work.start();
    }
    public void processSaveAsNoticeCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SAVEAS_NOTICE);
        work.start();
    }
    
    public void processDisuseNoticeCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DISUSE_NOTICE);
        work.start();
    }
    public void processUpdateCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE_NOTICE);
        work.start();
    }
    public void processZhuanYongJianCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ZhuanYongJian);
        work.start();
    }
    public void processExportBomCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXPORT_BOM);
        work.start();
    }
    public void processViewBomCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_BOM);
        work.start();
    }
    public void processExportCarloadBomCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), EXPORT_CARLOADBOM);
        work.start();
    }
    public void processViewCarloadBOMCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_CARLOADBOM);
        work.start();
    }
    public void processViewNoticeCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_NOTICE);
        work.start();
    }
    public void processRightDeleteCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RIGHT_DELETE);
        work.start();
    }
    public void processRightUpdateCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), RIGHT_UPDATE);
        work.start();
    }
    //CCBegin SS2
    public void processAutoCreateCommond()
    {
        WorkThread work = new WorkThread(getThreadGroup(), AUTOCREATE_MODE);
        work.start();
    }
    //CCEnd SS2
    /**
     * <p>Title:�����߳� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: һ������</p>
     * @author ����
     * @version 1.0
     */
    class WorkThread extends QMThread
    {
        int myAction;

        public WorkThread(ThreadGroup threadgroup, int action)
        {
            super();
            MainController.this.threadGroup = threadgroup;
            this.myAction = action;
        }

        public void run()
        {
            try
            {
                switch(myAction)
                {
                case CREATE_CARLOAD:
                	createCarloadNotice();
                    break;
                case CREATE_CARBODY:
                	createFrameAndCabNotice("��ʻ��");
                    break;
                case CREATE_CARFRAME:
                	createFrameAndCabNotice("����");
                    break;
                case SEARCH_NOTICE:
                	searchNotice();
                    break;
                case SAVE_NOTICE:
                	saveGYBomNotice();
                    break;
                case SAVEAS_NOTICE:
                	saveAsGYBomNotice();
                    break;
                case DISUSE_NOTICE:
                	disuseNotice();
                    break;
                case UPDATE_NOTICE:
                	updateGYBomNotice();
                    break;
                case ZhuanYongJian:
                	
                    break;
                case EXPORT_BOM:
                	
                	break;
                case VIEW_BOM:
                
                	break;
                case EXPORT_CARLOADBOM:
                	exportAllBOM();
                	break;
                case VIEW_CARLOADBOM:
                	
                	break;
                case VIEW_NOTICE:
                	viewNotice();
                    break;
                case RIGHT_DELETE:
                	deleteGYBomNotice();
                    break;
                case RIGHT_UPDATE:
                	updateGYBomNotice();
                    break;
                //CCBegin SS2
                case AUTOCREATE_MODE:
                    autoCreateGYBomNotice();
                    break;
                //CCEnd SS2
                }
            }catch(QMException e)
            {
                DialogFactory.showInformDialog(view, e.getClientMessage());
            } catch (IOException e) {
            	DialogFactory.showInformDialog(view, e.getLocalizedMessage());
			}finally
            {
                view.setCursor(Cursor.getDefaultCursor());
                QMCt.setContextGroup(null);

            }
        }
    }

    /**
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     * @return ��ǰѡ��Ķ���
     */
    public GYBomAdoptNoticeInfo getSelectedNoticeObject() throws QMException
    {
    	GYBomAdoptNoticeInfo list = null;
    	GYBomNoticeTreeObject treeObject = getSelectedNoticeTreeObject();
    	if(treeObject != null)
        {
           
            list = (GYBomAdoptNoticeInfo)treeObject.getObject();
            list = (GYBomAdoptNoticeInfo)refreshNode(list);
           
        }
        return list;
    }

    /**
     * ˢ�½ڵ㣬ȷ�����ϵĽڵ�Ϊ���½ڵ�
     * @param resource BaseValueIfc
     * @return BaseValueIfc
     */
    public BaseValueIfc refreshNode(BaseValueIfc resource)
    {
        if(resource == null)
        {
            return null;
        }
        Class[] aa = {String.class};
        Object[] oo = {resource.getBsoID()};
        try
        {
            NoticeHelper.requestServer("PersistService", "refreshInfo", aa, oo);
            return resource;
        }catch(QMException ex)
        {
            view.setCursor(Cursor.DEFAULT_CURSOR);
            DialogFactory.showInformDialog(view, ex.getClientMessage());
            return null;
        }

    }
  
   

    /**
     * ��õ�ǰѡ��Ķ���(������ǩ�ڵ�)�� ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     * @return ��ǰѡ��Ķ���
     */
    public GYBomNoticeTreeObject getSelectedNoticeTreeObject() throws QMException
    {
    	GYBomNoticeTreeObject obj = (GYBomNoticeTreeObject)view.getBomNoticeListTreePanel().getSelectedObject();
        if(obj == null)
        {
            String message = "û��ѡ�񷢲���!";
            throw new QMException(message);
        }else if(obj.getObject() == null)
        {
            String message = "ѡ��Ķ������ʹ���!";
            throw new QMException(message);
        }
        return obj;
    }

    /**
     * ���·�����
     * @throws QMRemoteException
     */
    private void updateGYBomNotice() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        GYBomAdoptNoticeInfo list =this.getSelectedNoticeObject();

        if(list == null)
        {
            return;
        }else
        {
            try
            {
                hashMap.clear();
                //����ǰ����
                Class[] listClass = {LockIfc.class,String.class};
                Object[] listLock = {list,NoticeClientUtil.getCurrentUser().getBsoID()};
                list = (GYBomAdoptNoticeInfo)NoticeHelper.requestServer("GYBomNoticeService", "lock", listClass, listLock);
                hashMap.put(list.getBsoID(), list);
            }catch(QMException e)
            {
                view.setCursor(Cursor.getDefaultCursor());
                DialogFactory.showInformDialog(view, e.getClientMessage());
                return;
            }
        	if(list.getPublishType().startsWith("����")){
                ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
                panel.setBomAdoptNotice(list, ZCBomAdoptNoticeViewPanel.UPDATE_MODE);	
                panel.setViewMode(ZCBomAdoptNoticeViewPanel.UPDATE_MODE);
               
        	}else if(list.getPublishType().startsWith("����")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("����");
//        		System.out.println("����1");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.UPDATE_MODE);	
                panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}else if(list.getPublishType().startsWith("��ʻ��")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("��ʻ��");
//        		System.out.println("��ʻ��1");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.UPDATE_MODE);	
                panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}
        }
        view.setCursor(Cursor.getDefaultCursor());
    }
    
    //CCBegin SS2
    /**
     * �Զ�����������
     * @throws QMRemoteException
     */
    private void autoCreateGYBomNotice() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        GYBomAdoptNoticeInfo list =this.getSelectedNoticeObject();

        if(list == null)
        {
            return;
        }else
        {
            try
            {
                hashMap.clear();
                //����ǰ����
                Class[] listClass = {LockIfc.class,String.class};
                Object[] listLock = {list,NoticeClientUtil.getCurrentUser().getBsoID()};
                list = (GYBomAdoptNoticeInfo)NoticeHelper.requestServer("GYBomNoticeService", "lock", listClass, listLock);
                hashMap.put(list.getBsoID(), list);
            }catch(QMException e)
            {
                view.setCursor(Cursor.getDefaultCursor());
                DialogFactory.showInformDialog(view, e.getClientMessage());
                return;
            }
            if(list.getPublishType().startsWith("����")){
                ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
                panel.setBomAdoptNotice(list, ZCBomAdoptNoticeViewPanel.AUTOCREATE_MODE);   
                panel.setViewMode(ZCBomAdoptNoticeViewPanel.AUTOCREATE_MODE);
               
            }else if(list.getPublishType().startsWith("����")){
                CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("����");
//                System.out.println("����2");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.AUTOCREATE_MODE);  
                panel.setViewMode(CJAdoptNoticeViewPanel.AUTOCREATE_MODE);
            }else if(list.getPublishType().startsWith("��ʻ��")){
                CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("��ʻ��");
//                System.out.println("��ʻ��2");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.AUTOCREATE_MODE);  
                panel.setViewMode(CJAdoptNoticeViewPanel.AUTOCREATE_MODE);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());
    }
    //CCEnd SS2

    /**
     * ��������������
     * @throws QMException 
     */
    private void createCarloadNotice() throws QMException
    {
    	ZCBomAdoptNoticeViewPanel panel = view.initCarloadNoticeRightPanel();
    	panel.setViewMode(ZCBomAdoptNoticeViewPanel.CREATE_MODE);
    }
    /**
     * �������ܡ���ʻ�ҷ�����
     * @throws QMException 
     */
    private void createFrameAndCabNotice(String type) throws QMException
    {
    	CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel(type);
    	panel.setViewMode(CJAdoptNoticeViewPanel.CREATE_MODE);
    }
 
    /**
     * ɾ��������
     * @throws QMException 
     */
    private void deleteGYBomNotice() throws QMException
    {
    	GYBomNoticeTreeObject treeobj = this.getSelectedNoticeTreeObject();
        GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)treeobj.getObject();
        String message = "ɾ�������ɻָ����Ƿ�ɾ����";
        int result = JOptionPane.showConfirmDialog(view, message, "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch(result)
        {
        case JOptionPane.YES_OPTION:
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Class[] c = {GYBomAdoptNoticeInfo.class};
            Object[] obj = {list};
            try
            {
                GYNoticeHelper.requestServer("GYBomNoticeService", "deleteGYBomAdoptNotice", c, obj);
                //ɾ�������
                view.getBomNoticeListTreePanel().removeNode(treeobj);
                view.getTaskPanel().removeAll();
                view.closeContentPanel();
                view.setCursor(Cursor.getDefaultCursor());
            }catch(QMException ex)
            {
                ex.printStackTrace();
                view.setCursor(Cursor.getDefaultCursor());
                DialogFactory.showInformDialog(view, ex.getClientMessage());
                
            }
        }
        }
    }
    /**
     * ����������
     * @throws QMException 
     */
    private void disuseNotice() throws QMException
    {
    	GYBomNoticeTreeObject treeobj = this.getSelectedNoticeTreeObject();
        GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)treeobj.getObject();
        String message = "�Ƿ�����˷�������";
        int result = JOptionPane.showConfirmDialog(view, message, "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch(result)
        {
        case JOptionPane.YES_OPTION:
        {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Class[] c = {GYBomAdoptNoticeInfo.class};
            Object[] obj = {list};
            try
            {
                GYNoticeHelper.requestServer("GYBomNoticeService", "disuseNotice", c, obj);
                view.setCursor(Cursor.getDefaultCursor());
            }catch(QMException ex)
            {
                ex.printStackTrace();
                view.setCursor(Cursor.getDefaultCursor());
                DialogFactory.showInformDialog(view, ex.getClientMessage());
                
            }
        }
        }
    }
    
    /**
     * �ݿͻ��鿴������
     * @throws QMException 
     */
    private void viewNotice() throws QMException
    {
    	GYBomNoticeTreeObject treeobj = this.getSelectedNoticeTreeObject();
        GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)treeobj.getObject();
        if(list!=null){
            String bsoID = list.getBsoID();
       	 	HashMap map = new HashMap();
            map.put("bsoID", bsoID);    
            RichToThinUtil.toWebPage("View_GYBomAdoptNotice.screen", map);
        }
    }
    
    /**
     * ���淢����
     * @throws QMException 
     */
    private void saveGYBomNotice() throws QMException
    {
    	view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    	if(view.getTaskPanel()!=null){
    		if(view.getTaskPanel() instanceof ZCBomAdoptNoticeViewPanel){
    			((ZCBomAdoptNoticeViewPanel)view.getTaskPanel()).save();
    		}else if(view.getTaskPanel() instanceof ZCAdoptNoticeAdjustPanel){
    			((ZCAdoptNoticeAdjustPanel)view.getTaskPanel()).save();
    		}
    	}
    	//CCBegin SS1
//    	if(view.getBomNoticeListTreePanel().getSelectedNode()==null||view.getBomNoticeListTreePanel().getSelectedNode().isRoot()){
//    		view.setCursor(Cursor.getDefaultCursor());
//    		throw new QMException("δѡ����Ҫ����ķ�������");
//    	
//    	}
    	//CCEnd SS1
        view.setCursor(Cursor.getDefaultCursor());

    }
    
    /**
     * ���Ϊ������
     * @throws QMRemoteException
     */
    private void saveAsGYBomNotice() throws QMException
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        GYBomAdoptNoticeInfo list =this.getSelectedNoticeObject();

        if(list == null)
        {
            return;
        }else
        {
        	if(list.getPublishType().startsWith("����")){
                ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
                panel.setBomAdoptNotice(list, ZCBomAdoptNoticeViewPanel.SAVE_AS_MODE);	
                //panel.setViewMode(ZCBomAdoptNoticeViewPanel.UPDATE_MODE);
               
        	}else if(list.getPublishType().startsWith("����")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("����");
//        		System.out.println("����3");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.SAVE_AS_MODE);	
               // panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}else if(list.getPublishType().startsWith("��ʻ��")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("��ʻ��");
//        		System.out.println("��ʻ��3");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.SAVE_AS_MODE);	
               // panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}
        }
        view.setCursor(Cursor.getDefaultCursor());
    }


    /**
     * �������õ����ҵ����õ�����
     */
    public void searchNotice()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SearchGYBomNoticeDialog searchDialog = new SearchGYBomNoticeDialog(view,false);
        searchDialog.setVisible(true);
        view.setCursor(Cursor.getDefaultCursor());

    } 

    /**
     * �鿴���õ�
     * @param GYBomAdoptNoticeIfc ifc
     * @throws QMException 
     */
    public void viewDefaultBomAdoptNotice(GYBomAdoptNoticeIfc ifc) throws QMException
    {
    	if(ifc.getPublishType().startsWith("����")){
            ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
            panel.setBomAdoptNotice(ifc, ZCBomAdoptNoticeViewPanel.VIEW_MODE);	
    	}else if(ifc.getPublishType().startsWith("����")||ifc.getPublishType().startsWith("��ʻ��")){
    		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel(ifc.getPublishType());
//    		System.out.println("�鿴���õ�");
            panel.setBomAdoptNotice(ifc, ZCBomAdoptNoticeViewPanel.VIEW_MODE);	
    	}
    }
  
   /**
     * �˳�����
     */
    private void quit()
    {
        view.exit();
    }

   

    

    
     /**
     * ��ô�ż��������map
     * @return HashMap 
     */
    public HashMap getHashMap()
    {
        return hashMap;
    }
    
    //houhf add
    /**
     * ������������BOM
     * @author houhf
     * @throws QMException 
     * @throws IOException 
     */
    private void exportAllBOM() throws IOException, QMException
    {
    	GYBomNoticeTreeObject treeobj = this.getSelectedNoticeTreeObject();
		GYBomAdoptNoticeIfc ifc = (GYBomAdoptNoticeIfc)treeobj.getObject();
    	String path = getSelectedPath();
    	if(path != null && path.trim().length()>0)
    	{
    		exportAllBOM(ifc,path);
    		JOptionPane.showMessageDialog(null, "�����ɹ�!", "��ʾ",
											JOptionPane.INFORMATION_MESSAGE);
    	}
    	else
    	{
    		return;
    	}
    }
    
    /**
	 * ��ָ����excel�ļ�д�����������й���BOM����
	 * @param path Excel�ļ�ȫ·��
	 * @param ifc ������
	 * @author houhf
	 * @throws IOException,QMException 
	 */
	private void exportAllBOM(GYBomAdoptNoticeIfc ifc, String path)
			throws IOException, QMException {
		//���ص�BOM����
		Vector<Object[]> result = new Vector<Object[]>();
		//���ϵ���BOM��Ϣ
		StringBuffer backBuffer = new StringBuffer();
		String head = "��������" + ifc.getAdoptnoticenumber() + "("
				+ ifc.getAdoptnoticename() + ")" + " �Ĺ���BOM�嵥" + "\n";
		backBuffer.append(head);

//		String table = "����,���,����,�汾,����,����·��,װ��·��,��ͼ,��׼���,��������״̬,ר�ü�,�����ʾ,"
//				+ "\n";
		String table = "���,����,�汾,����,����·��,װ��·��,��ͼ,��׼���,��������״̬,ר�ü�,�����ʾ,"
				+ "\n";
		backBuffer.append(table);

		Class[] theClass = {GYBomAdoptNoticeIfc.class};
        Object[] obj1 = {ifc};
        result = (Vector<Object[]>) GYNoticeHelper
        	.requestServer("GYBomNoticeService","exportAllBOM",theClass,obj1);
        
//		result = exportAllBOM(ifc);
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				String value = "";
				Object obj[] = result.get(i);
//				value = (String) obj[0] + ",";
				value += (String) obj[1] + ",";
				value += (String) obj[2] + ",";
				value += (String) obj[3] + ",";
				value += (String) obj[4] + ",";
				value += (String) obj[5] + ",";
				value += (String) obj[6] + ",";
				value += (String) obj[7] + ",";
				value += (String) obj[8] + ",";
				value += (String) obj[9] + ",";
				value += (String) obj[10] + ",";
				value += (String) obj[11] + ",";
				value += "\n";
				backBuffer.append(value);
			}
		}

		FileWriter fw = new FileWriter(path, false);
		fw.write(backBuffer.toString());
		fw.close();

	}
	
	/**
	 * ����û������ļ�·��
	 * @return ·��
	 * @author houhf
	 */
    private String getSelectedPath()
    {
		FileFilter filter;
		File selectedFile = null;
//		view.setCursor(Cursor.WAIT_CURSOR);
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(false);
		filter = new TXTFileFilter();
		if (selectedFile != null) {
			chooser.setSelectedFile(selectedFile);
		}
		chooser.setDialogTitle("�����������BOM��...");
		chooser.setFileFilter(filter);
		//ɾ��ϵͳ�Դ���AcceptAllFileFilter
		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		//���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
//		view.setCursor(Cursor.getDefaultCursor());
		int state = chooser.showSaveDialog(null);
		if (state == chooser.CANCEL_OPTION)
		{
			return null;
		}
		//���ѡ����ļ�
		selectedFile = chooser.getSelectedFile();
		//���ѡ������׼��ť,������ѡ���ļ���
		if (selectedFile != null && state == JFileChooser.APPROVE_OPTION)
		{
			//�ļ���ʽת��
			selectedFile = this.translateFile(selectedFile, filter);

			//�ж� 1 δ�����ļ���,�������ļ�����  2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
			if (!filter.accept(selectedFile))
			{
				JOptionPane.showMessageDialog(null, "·��������!", "����",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			if (selectedFile.exists())
			{
				JOptionPane.showMessageDialog
					(null, "�ļ��Ѿ����ڣ���ѡ��һ��ȫ���ļ����е���������", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				return null;
			}
		}

		return selectedFile.getPath();
	}
    
    /**
     * ���ļ�ת����csv��ʽ����������Ž���㲿���������ġ�����Ϊʲô��ôת�����ö�֪
     * @param file
     * @param filter
     * @return
     * @author houhf
     */
    private File translateFile(File file, FileFilter filter)
    {
		String path = file.getPath();
		if (!path.endsWith(".csv"))
		{
			path = path + ".csv";
		}
		return new File(path);
	}

	public class TXTFileFilter extends FileFilter
	{
		/**
		 * �����ı��ļ�������
		 */
		public TXTFileFilter()
		{
		}

		/**
		 * �ж�ָ�����ļ��Ƿ񱻱�����������
		 * @param f �ļ�
		 * @return ������ܣ��򷵻�true
		 */
		public boolean accept(File f)
		{
			boolean accept = f.isDirectory();
			if (!accept) {
				String suffix = getSuffix(f);
				if (suffix != null) {
					accept = suffix.equals("csv");
				}
			}
			return accept;
		}

		/**
		 * ��ñ���������������Ϣ
		 * @return Text Files(*.csv)
		 */
		public String getDescription()
		{
			return "Text Files(*.csv)";
		}

		/**
		 * ���ָ���ļ��ĺ�׺
		 * @param f File
		 * @return �ļ��ĺ�׺
		 */
		private String getSuffix(File f)
		{
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
			{
				suffix = s.substring(i + 1).toLowerCase();
			}
			return suffix;
		}
	}
	//houhf add end

}
