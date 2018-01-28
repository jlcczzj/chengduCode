/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 修改TD8401 创建时，菜单保存不成功  文柳 2014-7-16
 * SS2 自动生成BOM发布单 lishu 2017-5-12
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
 * <p>Title:采用变更通知单的控制类 </p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright (c) 2014</p> 
 * <p>Company: 一汽启明</p>
 * @author 文柳
 * @version 1.0
 */

public class MainController extends QMThread
{

	GYBomNoticeMainJFrame view = null;
    /** 线程组 */
    private ThreadGroup threadGroup = null;
    public WorkThread theWorkThread;
    private static boolean verbose = GYNoticeHelper.VERBOSE;

    /** 菜单操作标识 */
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
     *用于存放加锁值对象的Vector 
     */
    private HashMap hashMap = new HashMap();
    
    

    /**
     * 主界面控制器
     * @param frame 主界面
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
     * <p>Title:工作线程 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: 一汽启明</p>
     * @author 文柳
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
                	createFrameAndCabNotice("驾驶室");
                    break;
                case CREATE_CARFRAME:
                	createFrameAndCabNotice("车架");
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
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     * @return 当前选择的对象
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
     * 刷新节点，确保树上的节点为最新节点
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
     * 获得当前选择的对象(不含标签节点)。 如果没有选择对象返回null,并抛出提示信息。
     * @return 当前选择的对象
     */
    public GYBomNoticeTreeObject getSelectedNoticeTreeObject() throws QMException
    {
    	GYBomNoticeTreeObject obj = (GYBomNoticeTreeObject)view.getBomNoticeListTreePanel().getSelectedObject();
        if(obj == null)
        {
            String message = "没有选择发布单!";
            throw new QMException(message);
        }else if(obj.getObject() == null)
        {
            String message = "选择的对象类型错误!";
            throw new QMException(message);
        }
        return obj;
    }

    /**
     * 更新发布单
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
                //更新前加锁
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
        	if(list.getPublishType().startsWith("整车")){
                ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
                panel.setBomAdoptNotice(list, ZCBomAdoptNoticeViewPanel.UPDATE_MODE);	
                panel.setViewMode(ZCBomAdoptNoticeViewPanel.UPDATE_MODE);
               
        	}else if(list.getPublishType().startsWith("车架")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("车架");
//        		System.out.println("车架1");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.UPDATE_MODE);	
                panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}else if(list.getPublishType().startsWith("驾驶室")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("驾驶室");
//        		System.out.println("驾驶室1");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.UPDATE_MODE);	
                panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}
        }
        view.setCursor(Cursor.getDefaultCursor());
    }
    
    //CCBegin SS2
    /**
     * 自动创建发布单
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
                //更新前加锁
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
            if(list.getPublishType().startsWith("整车")){
                ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
                panel.setBomAdoptNotice(list, ZCBomAdoptNoticeViewPanel.AUTOCREATE_MODE);   
                panel.setViewMode(ZCBomAdoptNoticeViewPanel.AUTOCREATE_MODE);
               
            }else if(list.getPublishType().startsWith("车架")){
                CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("车架");
//                System.out.println("车架2");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.AUTOCREATE_MODE);  
                panel.setViewMode(CJAdoptNoticeViewPanel.AUTOCREATE_MODE);
            }else if(list.getPublishType().startsWith("驾驶室")){
                CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("驾驶室");
//                System.out.println("驾驶室2");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.AUTOCREATE_MODE);  
                panel.setViewMode(CJAdoptNoticeViewPanel.AUTOCREATE_MODE);
            }
        }
        view.setCursor(Cursor.getDefaultCursor());
    }
    //CCEnd SS2

    /**
     * 创建整车发布单
     * @throws QMException 
     */
    private void createCarloadNotice() throws QMException
    {
    	ZCBomAdoptNoticeViewPanel panel = view.initCarloadNoticeRightPanel();
    	panel.setViewMode(ZCBomAdoptNoticeViewPanel.CREATE_MODE);
    }
    /**
     * 创建车架、驾驶室发布单
     * @throws QMException 
     */
    private void createFrameAndCabNotice(String type) throws QMException
    {
    	CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel(type);
    	panel.setViewMode(CJAdoptNoticeViewPanel.CREATE_MODE);
    }
 
    /**
     * 删除发布单
     * @throws QMException 
     */
    private void deleteGYBomNotice() throws QMException
    {
    	GYBomNoticeTreeObject treeobj = this.getSelectedNoticeTreeObject();
        GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)treeobj.getObject();
        String message = "删除将不可恢复，是否删除？";
        int result = JOptionPane.showConfirmDialog(view, message, "警告", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
                //删除树结点
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
     * 废弃发布单
     * @throws QMException 
     */
    private void disuseNotice() throws QMException
    {
    	GYBomNoticeTreeObject treeobj = this.getSelectedNoticeTreeObject();
        GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)treeobj.getObject();
        String message = "是否废弃此发布单？";
        int result = JOptionPane.showConfirmDialog(view, message, "警告", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
     * 瘦客户查看发布单
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
     * 保存发布单
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
//    		throw new QMException("未选择需要保存的发布单！");
//    	
//    	}
    	//CCEnd SS1
        view.setCursor(Cursor.getDefaultCursor());

    }
    
    /**
     * 另存为发布单
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
        	if(list.getPublishType().startsWith("整车")){
                ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
                panel.setBomAdoptNotice(list, ZCBomAdoptNoticeViewPanel.SAVE_AS_MODE);	
                //panel.setViewMode(ZCBomAdoptNoticeViewPanel.UPDATE_MODE);
               
        	}else if(list.getPublishType().startsWith("车架")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("车架");
//        		System.out.println("车架3");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.SAVE_AS_MODE);	
               // panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}else if(list.getPublishType().startsWith("驾驶室")){
        		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel("驾驶室");
//        		System.out.println("驾驶室3");
                panel.setBomAdoptNotice(list, CJAdoptNoticeViewPanel.SAVE_AS_MODE);	
               // panel.setViewMode(CJAdoptNoticeViewPanel.UPDATE_MODE);
        	}
        }
        view.setCursor(Cursor.getDefaultCursor());
    }


    /**
     * 搜索采用单，挂到采用单树上
     */
    public void searchNotice()
    {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SearchGYBomNoticeDialog searchDialog = new SearchGYBomNoticeDialog(view,false);
        searchDialog.setVisible(true);
        view.setCursor(Cursor.getDefaultCursor());

    } 

    /**
     * 查看采用单
     * @param GYBomAdoptNoticeIfc ifc
     * @throws QMException 
     */
    public void viewDefaultBomAdoptNotice(GYBomAdoptNoticeIfc ifc) throws QMException
    {
    	if(ifc.getPublishType().startsWith("整车")){
            ZCBomAdoptNoticeViewPanel panel = view.initZCNoticeRightPanel();
            panel.setBomAdoptNotice(ifc, ZCBomAdoptNoticeViewPanel.VIEW_MODE);	
    	}else if(ifc.getPublishType().startsWith("车架")||ifc.getPublishType().startsWith("驾驶室")){
    		CJAdoptNoticeViewPanel panel = view.initFrameAndCabRightPanel(ifc.getPublishType());
//    		System.out.println("查看采用单");
            panel.setBomAdoptNotice(ifc, ZCBomAdoptNoticeViewPanel.VIEW_MODE);	
    	}
    }
  
   /**
     * 退出界面
     */
    private void quit()
    {
        view.exit();
    }

   

    

    
     /**
     * 获得存放加锁对象的map
     * @return HashMap 
     */
    public HashMap getHashMap()
    {
        return hashMap;
    }
    
    //houhf add
    /**
     * 导出整车工艺BOM
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
    		JOptionPane.showMessageDialog(null, "导出成功!", "提示",
											JOptionPane.INFORMATION_MESSAGE);
    	}
    	else
    	{
    		return;
    	}
    }
    
    /**
	 * 向指定的excel文件写发布单下所有工艺BOM数据
	 * @param path Excel文件全路径
	 * @param ifc 发布单
	 * @author houhf
	 * @throws IOException,QMException 
	 */
	private void exportAllBOM(GYBomAdoptNoticeIfc ifc, String path)
			throws IOException, QMException {
		//返回的BOM集合
		Vector<Object[]> result = new Vector<Object[]>();
		//整合导出BOM信息
		StringBuffer backBuffer = new StringBuffer();
		String head = "发布单：" + ifc.getAdoptnoticenumber() + "("
				+ ifc.getAdoptnoticename() + ")" + " 的工艺BOM清单" + "\n";
		backBuffer.append(head);

//		String table = "级别,编号,名称,版本,数量,制造路线,装配路线,视图,艺准编号,生命周期状态,专用件,虚拟标示,"
//				+ "\n";
		String table = "编号,名称,版本,数量,制造路线,装配路线,视图,艺准编号,生命周期状态,专用件,虚拟标示,"
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
	 * 获得用户本地文件路径
	 * @return 路径
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
		chooser.setDialogTitle("输出整车工艺BOM至...");
		chooser.setFileFilter(filter);
		//删除系统自带的AcceptAllFileFilter
		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		//“打开”模式文件选择器中选择了批准按钮还是取消按钮
//		view.setCursor(Cursor.getDefaultCursor());
		int state = chooser.showSaveDialog(null);
		if (state == chooser.CANCEL_OPTION)
		{
			return null;
		}
		//获得选择的文件
		selectedFile = chooser.getSelectedFile();
		//如果选择了批准按钮,则获得所选择文件名
		if (selectedFile != null && state == JFileChooser.APPROVE_OPTION)
		{
			//文件格式转换
			selectedFile = this.translateFile(selectedFile, filter);

			//判断 1 未输入文件名,请输入文件名称  2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
			if (!filter.accept(selectedFile))
			{
				JOptionPane.showMessageDialog(null, "路径不存在!", "错误",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			if (selectedFile.exists())
			{
				JOptionPane.showMessageDialog
					(null, "文件已经存在！请选择一个全新文件进行导出操作！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return null;
			}
		}

		return selectedFile.getPath();
	}
    
    /**
     * 将文件转换成csv格式，这个是照着解放零部件导出做的。至于为什么这么转换不得而知
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
		 * 构造文本文件过滤器
		 */
		public TXTFileFilter()
		{
		}

		/**
		 * 判断指定的文件是否被本过滤器接受
		 * @param f 文件
		 * @return 如果接受，则返回true
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
		 * 获得本过滤器的描述信息
		 * @return Text Files(*.csv)
		 */
		public String getDescription()
		{
			return "Text Files(*.csv)";
		}

		/**
		 * 获得指定文件的后缀
		 * @param f File
		 * @return 文件的后缀
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
