/** 程序ChangFolderController.java	1.0  2003/01/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.controller;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.faw_qm.cappclients.capproute.util.RouteListTreeObject;
import com.faw_qm.cappclients.capproute.util.RouteTreeNode;
import com.faw_qm.cappclients.capproute.view.CappRouteListManageJFrame;
import com.faw_qm.cappclients.capproute.view.ChangeFolderObject;
import com.faw_qm.cappclients.capproute.view.RouteListTaskJPanel;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.model.PartEffModel;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.other.model.MoveObjectModel;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;



/**
 * <p>Title: 更改资料夹控制类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 雷晓
 * //CC by leixiao 2009-9-21 原因：解放升级工艺路线,增加"转库" 
 * @version 1.0
 */

public class ChangeFolderController extends EffAction
{
    /**普通搜索界面数据模型*/
    private MoveObjectModel model = null;


    /**普通搜索界面对象*/
    private ChangeFolderObject view = null;


    /**本控制器的状态,如果为-1,则代表结束*/
    /**开始状态常量*/
    private static final int BEGIN = 0;


    /**创建状态常量*/
    private static final int SAVE = 1;


    /**结束状态常量*/
    private static final int END = -1;


    /**状态值*/
    private int state = BEGIN;

    private RevisionControlledIfc object;
    private RevisionControlledIfc object1;


    /**存储的文件夹位置*/
    SubFolderInfo folder = null;
    private String warn = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);;

    
    private static String RESOURCE =
        "com.faw_qm.cappclients.capproute.util.CappRouteRB";
    
    private CappRouteListManageJFrame mainJFrame;


    /**
     * 构造函数。
     * @param version RevisionControlledIfc
     */
    public ChangeFolderController(RevisionControlledIfc version,Frame frame)
    {
    	mainJFrame = (CappRouteListManageJFrame) frame;
        object = version;
        view = new ChangeFolderObject(this, object);
        model = new MoveObjectModel();
        model.addObserver(view);
        start();
    }


    /**
     * 线程运行函数。
     * <p>调用动作执行函数(perform())。</p>
     */
    public void run()
    {
        perform();
        while (state != END)
        {
            try
            {
                sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (state == SAVE)
            {
                state = BEGIN;
                reSave();
            }
        }
    }


    /**
     * 界面按钮动作监听处理函数。
     * @param e 动作事件。
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("CANCEL"))
        {
            cancel();
        }
        else if (e.getActionCommand().equals("OK"))
        {
            view.passivateView();
            folder = view.getOutputText();
            if (folder == null)
            {
                view.activateView();
            }
            else
            {
                state = SAVE;
            }
        }
    }


    /**
     * 创建有效性方案控制器的执行函数。
     */
    public void perform()
    {
        PartScreenParameter.centerWindow(view);
    }


    /**
     * 取消已经初始化的动作,并将控制器状态置为END(-1)。</p>
     */
    public void cancel()
    {
        view.closeView();
        state = END;
    }


    /**
     * 返回数据模型。
     * @return 界面的模型。
     */
    public PartEffModel getModel()
    {
        return model;
    }


    /**
     * 返回住控制器的状态。
     * @return 状态(0:开始,1:执行,-1:结束)。
     */
//	CCBegin leixiao 2009-11-27 由于底层的更改，getState->getEffState
    public int getEffState()
    {
        return state;
    }
//	CCEnd leixiao 2009-11-27 
    
    public void setObject(Object object)
    {

    }


    /**
     * 重新保存对象。
     */
    public void reSave()
    {
        if (object instanceof FolderEntryIfc)
        {
            FolderEntryIfc feIfc = (FolderEntryIfc) object;
            Class[] paramClass = {BaseValueIfc.class};
            Object[] paramValue = {feIfc};
            try
            {
                Object object2 = IBAUtility.invokeServiceMethod("PersistService", "refreshInfo", paramClass, paramValue);
                if (object2 instanceof FolderEntryIfc)
                {
                    feIfc = (FolderEntryIfc) object2;
                }
            }
            catch (QMException e)
            {
                JOptionPane.showMessageDialog(this.view, e.getMessage(), warn,
                                              JOptionPane.WARNING_MESSAGE);
                return;
            }
            Class[] paramClass1 = {FolderEntryIfc.class, FolderIfc.class};
            Object[] paramValue1 = {feIfc, (FolderIfc) folder};
            try
            {
                object1 = (RevisionControlledIfc)IBAUtility.invokeServiceMethod("FolderService", "changeFolder", paramClass1, paramValue1);
                RouteListTreeObject treeObject = new RouteListTreeObject(object1);
                mainJFrame.getTreePanel().updateNode(treeObject);
            }
            catch (QMException e)
            {
                JOptionPane.showMessageDialog(this.view, e.getClientMessage(), warn,
                                              JOptionPane.WARNING_MESSAGE);
                view.activateView();
                return;
            }            
          //  System.out.println("-------更改后的资料夹-"+((TechnicsRouteListInfo)object1).getLocation());

			//dispatchRefreshEvent(object1, 1);

            viewDefaultRouteList();
            
            this.cancel();
        }
    }
    
    public void viewDefaultRouteList() {
        try {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RouteListTaskJPanel p = mainJFrame.getTaskPanel();
            p.setTechnicsRouteList((TechnicsRouteListInfo)object1);
            p.setViewMode(RouteListTaskJPanel.VIEW_MODE);
            view.setCursor(Cursor.getDefaultCursor());
        } catch (Exception ex) {

        }
    }

//    private void dispatchRefreshEvent(Object obj, int i)
//    {
//        RefreshEvent refreshevent = new RefreshEvent(this, i, obj);
//        RefreshService.getRefreshService().dispatchRefresh(refreshevent);
//    }

    public void mouseClicked(MouseEvent e)
    {}

    public void mouseEntered(MouseEvent e)
    {}

    public void mouseExited(MouseEvent e)
    {}

    public void mousePressed(MouseEvent e)
    {}

    public void mouseReleased(MouseEvent e)
    {}
}
