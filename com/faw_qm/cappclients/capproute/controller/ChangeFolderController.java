/** ����ChangFolderController.java	1.0  2003/01/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: �������ϼп����ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * //CC by leixiao 2009-9-21 ԭ�򣺽����������·��,����"ת��" 
 * @version 1.0
 */

public class ChangeFolderController extends EffAction
{
    /**��ͨ������������ģ��*/
    private MoveObjectModel model = null;


    /**��ͨ�����������*/
    private ChangeFolderObject view = null;


    /**����������״̬,���Ϊ-1,��������*/
    /**��ʼ״̬����*/
    private static final int BEGIN = 0;


    /**����״̬����*/
    private static final int SAVE = 1;


    /**����״̬����*/
    private static final int END = -1;


    /**״ֵ̬*/
    private int state = BEGIN;

    private RevisionControlledIfc object;
    private RevisionControlledIfc object1;


    /**�洢���ļ���λ��*/
    SubFolderInfo folder = null;
    private String warn = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);;

    
    private static String RESOURCE =
        "com.faw_qm.cappclients.capproute.util.CappRouteRB";
    
    private CappRouteListManageJFrame mainJFrame;


    /**
     * ���캯����
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
     * �߳����к�����
     * <p>���ö���ִ�к���(perform())��</p>
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
     * ���水ť����������������
     * @param e �����¼���
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
     * ������Ч�Է�����������ִ�к�����
     */
    public void perform()
    {
        PartScreenParameter.centerWindow(view);
    }


    /**
     * ȡ���Ѿ���ʼ���Ķ���,����������״̬��ΪEND(-1)��</p>
     */
    public void cancel()
    {
        view.closeView();
        state = END;
    }


    /**
     * ��������ģ�͡�
     * @return �����ģ�͡�
     */
    public PartEffModel getModel()
    {
        return model;
    }


    /**
     * ����ס��������״̬��
     * @return ״̬(0:��ʼ,1:ִ��,-1:����)��
     */
//	CCBegin leixiao 2009-11-27 ���ڵײ�ĸ��ģ�getState->getEffState
    public int getEffState()
    {
        return state;
    }
//	CCEnd leixiao 2009-11-27 
    
    public void setObject(Object object)
    {

    }


    /**
     * ���±������
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
          //  System.out.println("-------���ĺ�����ϼ�-"+((TechnicsRouteListInfo)object1).getLocation());

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
