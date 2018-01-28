package com.faw_qm.cappclients.conscapproute.view;

//SS1 按典型路线搜索功能搜不到结果问题 pante 2014-09-03
//SS2 成都典型路线合并。 liunan 2016-10-17

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.faw_qm.cappclients.resource.view.CheckboxMultiList;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.ModelRouteInfo;
import com.faw_qm.technics.consroute.util.RouteWrapData;
//CCBegin SS1
import com.faw_qm.part.model.QMPartInfo;
//CCEnd SS1
//CCBegin SS2
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS2

public class CoverModelRouteJDialog extends JDialog
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ComponentMultiList qMMultiList = new ComponentMultiList();

    private JPanel jPanel1 = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private Vector vec = new Vector();
    private static RouteListPartLinkJPanel panel;
    private HashMap nowModelRotueMap;
    private static final String modelRoutedomain = RemoteProperty.getProperty("modelRoutedomain");

    //    private int[] index;
    //CCBegin SS2
    private String comp="";
    //CCEnd SS2

    /**
     * @roseuid 4031A77200CE
     */
    public CoverModelRouteJDialog()
    {
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数
     * @param parent 父窗口
     * @roseuid 40317EEC01C1
     */
    public CoverModelRouteJDialog(JFrame frame, JPanel panel, Vector vec, HashMap nowModelRotueMap)
    {
        //super(panel, "", true);
        super(frame);
        this.vec = vec;
        this.nowModelRotueMap = nowModelRotueMap;
        this.panel = (RouteListPartLinkJPanel)panel;
        try
        {
            jbInit();
            showPart(vec);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("覆盖典型路线");
        this.setSize(650, 500);
        this.setModal(true);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setLayout(gridBagLayout1);
        //okJButton.setEnabled(false);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new CoverModelRouteJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new CoverModelRouteJDialog_cancelJButton_actionAdapter(this));
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setText("需要覆盖典型路线的零部件");
        // qMMultiList.addItemListener(new CoverModelRouteJDialog_qMMultiList_itemAdapter(this));
        jPanel1.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(statusJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

        qMMultiList.setHeadings(new String[]{"id", "标识", "零部件编号", "零部件名称", "现典型路线", "原典型路线", "路线ID"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 2, 0});
        
        //CCBegin SS2
        try
        {
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.doc.util.DocServiceHelper");
        	info1.setMethodName("getUserFromCompany");
        	Class[] classes = {};
        	info1.setParaClasses(classes);
        	Object[] objs = {};
        	info1.setParaValues(objs);
        	comp=(String)server.request(info1);
        	if(comp.equals("cd"))
        	{
        		qMMultiList.setRelColWidth(new int[]{0, 1, 2, 0, 2, 2, 0});
        	}
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        //CCEnd SS2
        
        int col[] = {1};
        qMMultiList.setColsEnabled(col, true);
        qMMultiList.setMultipleMode(false);
        
    }

    /**
     * 将零件显示到列表里
     * @param v
     */
    private void showPart(Vector vec)
    {
        String routeStr = "";
        String nowrouteStr = "";
        Class[] c1 = {Vector.class};
        Object[] obj1 = {vec};
        HashMap wrapdataMap=null;
		try {
			wrapdataMap = (HashMap)RequestHelper.request("consTechnicsRouteService", "getRouteInformation", c1, obj1);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
        for(int i = 0;i < vec.size();i++)
        {
            ModelRouteInfo modelroute = (ModelRouteInfo)vec.elementAt(i);
            //CCBegin SS2
            String keyid = "";
            /*Class[] c = {String.class};
            Object[] obj = {modelroute.getLeftBsoID()};
            QMPartMasterInfo part=null;
			try {
				part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.setCheckboxSelected(i, 1, true);
            qMMultiList.addCell(i, 2, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 3, part.getPartName());*/
            if(comp.equals("cd"))
            {
            	qMMultiList.addTextCell(i, 0, modelroute.getLeftBsoID());
            	qMMultiList.setCheckboxSelected(i, 1, true);
            	qMMultiList.addTextCell(i, 2, modelroute.getLeftBsoID());
            	qMMultiList.addTextCell(i, 3, "");
            	keyid = modelroute.getLeftBsoID();
            }
            else
            {
            	Class[] c = {String.class};
            	Object[] obj = {modelroute.getLeftBsoID()};
            	QMPartMasterInfo part=null;
            	try
            	{
            		part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
            	}
            	catch (QMRemoteException e)
            	{
            		e.printStackTrace();
            	}
            	Image image = this.getStandardIcon(part);
            	qMMultiList.addTextCell(i, 0, part.getBsoID());
            	qMMultiList.setCheckboxSelected(i, 1, true);
            	qMMultiList.addCell(i, 2, part.getPartNumber(), image);
            	qMMultiList.addTextCell(i, 3, part.getPartName());
            	keyid = part.getBsoID();
            }
            //现有典型路线
            //RouteWrapData wrapdata = (RouteWrapData)nowModelRotueMap.get(part.getBsoID());
            RouteWrapData wrapdata = (RouteWrapData)nowModelRotueMap.get(keyid);
            System.out.println("现有1："+nowModelRotueMap.get(keyid));
            //CCEnd SS2

            if(wrapdata != null && !wrapdata.getSecondStr().equals(""))
            {
                nowrouteStr = wrapdata.getMainStr() + ";" + wrapdata.getSecondStr();
            }else
            {
                nowrouteStr = wrapdata.getMainStr();
            }

            qMMultiList.addTextCell(i, 4, nowrouteStr);
            //CCBegin SS2
            //原有典型路线
            //RouteWrapData wrapdata1 = (RouteWrapData)wrapdataMap.get(part.getBsoID());
            RouteWrapData wrapdata1 = (RouteWrapData)wrapdataMap.get(keyid);
            System.out.println("原有1："+wrapdataMap.get(keyid));
            //CCEnd SS2

            if(wrapdata != null && wrapdata1.getSecondStr() != null)
            {
                routeStr = wrapdata1.getMainStr() + ";" + wrapdata1.getSecondStr();
            }else
            {
                routeStr = wrapdata1.getMainStr();
            }
            qMMultiList.addTextCell(i, 5, routeStr);
            qMMultiList.addTextCell(i, 6, modelroute.getRightBsoID());
        }
    }

    /**
     * 获得图标
     * @param basevalueinfo
     * @return
     */
    public Image getStandardIcon(BaseValueIfc basevalueinfo)
    {
        Image image = null;
        if(basevalueinfo != null)
        {
            try
            {
                image = QM.getStandardIcon(basevalueinfo.getBsoName());
            }catch(Exception _ex)
            {
                image = null;
            }
        }
        return image;
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        CoverModelRouteJDialog d = new CoverModelRouteJDialog();
        d.setVisible(true);
    }

    /**
     * 执行确定操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        Vector dataVec = new Vector();
        Vector routeVec = new Vector();

        for(int i = 0;i < vec.size();i++)
        {
            if(qMMultiList.isCheckboxSelected(i, 1) == true)
            {
              //  wrapdata = panel.getOnePartSaveData(i);
                routeVec.add(qMMultiList.getCellText(i, 6));

                RouteWrapData wrapdata = (RouteWrapData)nowModelRotueMap.get(qMMultiList.getCellText(i, 0));
                dataVec.add(wrapdata);
//                if(qMMultiList.getCellText(i, 0).equals(wrapdata.getPartMasterID()))
//                {
//                    dataVec.add(wrapdata);
//                }
            }
        }

        Class[] param2 = {Vector.class};
        Object[] value2 = {dataVec};
        HashMap modelroutemap;
		try {
			modelroutemap = (HashMap)RequestHelper.request("consTechnicsRouteService", "saveAsRoute", param2, value2);
		
        for(int j = 0;j < dataVec.size();j++)
        {
            RouteWrapData wrapdata = (RouteWrapData)dataVec.get(j);
            ModelRouteInfo modelroute = new ModelRouteInfo();
            
            //CCBegin SS2
            String partnum ="";
            //CCEnd SS2
            
            //CCBegin SS1
            if(wrapdata.getPartMasterID().toString().contains("QMPart_")){
				Class[] c = {String.class};
				Object[] obj = {wrapdata.getPartMasterID().toString()};
				QMPartInfo part = (QMPartInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
				modelroute.setLeftBsoID(part.getMasterBsoID());
						  //CCBegin SS2
						  partnum = part.getPartNumber().trim();
						  //CCEnd SS2
            }
            else{
            	modelroute.setLeftBsoID(wrapdata.getPartMasterID());
						  //CCBegin SS2
						  Class[] c = {String.class};
						  Object[] obj = {wrapdata.getPartMasterID().toString()};
						  QMPartMasterInfo part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
						  partnum = part.getPartNumber().trim();
						  //CCEnd SS2
            }
            //CCEnd SS1
            
            //CCBegin SS2
            if(comp.equals("cd"))
            {
            	if (partnum.length() >= 7)
            	{
            		partnum = partnum.substring(0, 7);
            	}
            	else
            	{
            		partnum = partnum;
            	}
            	modelroute.setLeftBsoID(partnum);
            }
            //CCEnd SS2
            
            modelroute.setRightBsoID(modelroutemap.get(wrapdata.getPartMasterID()).toString());
            
            modelroute.setDomain(modelRoutedomain);
            Class[] param = {ModelRouteInfo.class};
            Object[] value = {modelroute};
            RequestHelper.request("consTechnicsRouteService", "saveModelRoute", param, value);
        }

        Class[] param1 = {Vector.class};
        Object[] value1 = {routeVec};
        RequestHelper.request("consTechnicsRouteService", "delrouteobject", param1, value1);
		} catch (QMRemoteException e1) {
			e1.printStackTrace();
		}
        this.setVisible(false);
    }

    /**
     * 执行取消操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    //    /**
    //     * 当选中零部件时，确定按钮有效
    //     * @param e ItemEvent
    //     */
    //    void qMMultiList_itemStateChanged(ItemEvent e)
    //    {
    //        Object[] objs = qMMultiList.getSelectedObjects();
    //        if(objs != null && objs.length > 0)
    //        {
    //            okJButton.setEnabled(true);
    //        }
    //    }

}

/**
 * <p>Title:确定按钮适配器</p> <p>Description: </p>
 */
class CoverModelRouteJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CoverModelRouteJDialog adaptee;

    CoverModelRouteJDialog_okJButton_actionAdapter(CoverModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:取消按钮适配器</p> <p>Description: </p>
 */
class CoverModelRouteJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CoverModelRouteJDialog adaptee;

    CoverModelRouteJDialog_cancelJButton_actionAdapter(CoverModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

///**
// * <p>Title:qMMultiList的ITEM适配器</p> <p>Description: </p>
// */
//class CoverModelRouteJDialog_qMMultiList_itemAdapter implements java.awt.event.ItemListener
//{
//    private CoverModelRouteJDialog adaptee;
//
//    CoverModelRouteJDialog_qMMultiList_itemAdapter(CoverModelRouteJDialog adaptee)
//    {
//        this.adaptee = adaptee;
//    }
//
//    public void itemStateChanged(ItemEvent e)
//    {
//        adaptee.qMMultiList_itemStateChanged(e);
//    }
//}