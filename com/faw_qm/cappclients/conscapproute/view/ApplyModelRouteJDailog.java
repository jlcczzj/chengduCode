package com.faw_qm.cappclients.conscapproute.view;

//SS1 成都典型路线合并。 liunan 2016-10-17

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
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS1

public class ApplyModelRouteJDailog extends JDialog
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

    // private Vector vec = new Vector();
    private RouteWrapData wrapdata;
    private HashMap nowMap;
    private HashMap wrapdataMap;
    private Vector partVec;
    private static final String modelRoutedomain = RemoteProperty.getProperty("modelRoutedomain");

    //CCBegin SS1
    private String comp="";
    //CCEnd SS1
    
    //    private int[] index;

    /**
     * @roseuid 4031A77200CE
     */
    public ApplyModelRouteJDailog()
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
    public ApplyModelRouteJDailog(JFrame frame, HashMap nowMap, HashMap wrapdataMap, Vector partVec)
    {
        super(frame);
        //  this.vec = vec;
        this.nowMap = nowMap;
        this.wrapdataMap = wrapdataMap;
        this.partVec = partVec;
        try
        {
            jbInit();
            showPart(nowMap, wrapdataMap, partVec);
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
        this.setTitle("应用典型路线");
        this.setSize(650, 500);
        this.setModal(true);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new ApplyModelRouteJDailog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new ApplyModelRouteJDailog_cancelJButton_actionAdapter(this));
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setText("需要应用典型路线的零部件");
        qMMultiList.addItemListener(new ApplyModelRouteJDailog_qMMultiList_itemAdapter(this));
        jPanel1.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(statusJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

        qMMultiList.setHeadings(new String[]{"id", "标识", "零部件编号", "零部件名称", "典型路线", "原路线"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 2});
        int col[] = {1};
        qMMultiList.setColsEnabled(col, true);
        qMMultiList.setMultipleMode(false);
        
        //CCBegin SS1
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
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        //CCEnd SS1

    }

    /**
     * 将零件显示到列表里
     * @param v
     */
    private void showPart(HashMap nowMap, HashMap wrapdataMap, Vector partVec)
    {
        String routeStr = "";
        String nowrouteStr = "";
        //        Class[] c1 = {Vector.class};
        //        Object[] obj1 = {vec};
        //        HashMap wrapdataMap =(HashMap)RequestHelper.request("TechnicsRouteService", "getRouteInformation", c1, obj1);
        for(int i = 0;i < partVec.size();i++)
        {
            String bsoID = (String)partVec.elementAt(i);
            //            ModelRouteInfo modelroute = (ModelRouteInfo)vec.elementAt(i);
            Class[] c = {String.class};
            Object[] obj = {bsoID};
            //CCBegin SS1
            //QMPartMasterInfo part=null;
            QMPartInfo part=null;
			try {
				//part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
				part = (QMPartInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
				//CCEnd SS1
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.setCheckboxSelected(i, 1, true);
            qMMultiList.addCell(i, 2, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 3, part.getPartName());
            //典型路线
            //CCBegin SS1
            //RouteWrapData wrapdata = (RouteWrapData)wrapdataMap.get(part.getBsoID());
            RouteWrapData wrapdata = null;
            if(comp.equals("cd"))
            {
            	String partnum = part.getPartNumber();
            	if (partnum.length() >= 7)
            	{
            		partnum = partnum.substring(0, 7);
            	}
            	else
            	{
            		partnum = partnum;
            	}
            	wrapdata = (RouteWrapData)wrapdataMap.get(partnum);
            }
            else
            {
            	wrapdata = (RouteWrapData)wrapdataMap.get(part.getBsoID());
            }
            //CCEnd SS1
            
            if(wrapdata != null && wrapdata.getSecondStr() != null)
            {
                nowrouteStr = wrapdata.getMainStr() + ";" + wrapdata.getSecondStr();
            }else
            {
                nowrouteStr = wrapdata.getMainStr();
            }
            qMMultiList.addTextCell(i, 4, nowrouteStr);
            //原路线
            RouteWrapData wrapdata1 = (RouteWrapData)nowMap.get(part.getBsoID());

            if(wrapdata1 != null && !wrapdata1.getSecondStr().equals(""))
            {
                routeStr = wrapdata1.getMainStr() + ";" + wrapdata1.getSecondStr();
            }else
            {
                routeStr = wrapdata1.getMainStr();
            }

            qMMultiList.addTextCell(i, 5, routeStr);
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
        ApplyModelRouteJDailog d = new ApplyModelRouteJDailog();
        d.setVisible(true);
    }

    /**
     * 执行确定操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        Vector dataVec = new Vector();

        for(int i = 0;i < partVec.size();i++)
        {
            boolean flag = qMMultiList.isCheckboxSelected(i, 1);
            if(flag == true)
            {
                //CCBegin SS1
                //RouteWrapData wrapdata1 = (RouteWrapData)wrapdataMap.get(qMMultiList.getCellText(i, 0));
                //dataVec.add(wrapdata1);
                if(comp.equals("cd"))
                {
                	String partnum = qMMultiList.getCellText(i, 2);
                	if (partnum.length() >= 7)
                	{
                		partnum = partnum.substring(0, 7);
                	}
                	else
                	{
                		partnum = partnum;
                	}
                	RouteWrapData wrapdata1 = (RouteWrapData)wrapdataMap.get(partnum);
                	wrapdata1.setPartMasterID(qMMultiList.getCellText(i, 0));
                	dataVec.add(wrapdata1);
                }
                else
                {
                	RouteWrapData wrapdata1 = (RouteWrapData)wrapdataMap.get(qMMultiList.getCellText(i, 0));
                	dataVec.add(wrapdata1);
                }
                //CCEnd SS1
            }
        }

        this.setDataVec(dataVec);
        this.setVisible(false);
    }

    private Vector vec;

    public void setDataVec(Vector vec)
    {
        this.vec = vec;
    }

    public Vector getDataVec()
    {
        return vec;
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

    /**
     * 当选中零部件时，确定按钮有效
     * @param e ItemEvent
     */
    void qMMultiList_itemStateChanged(ItemEvent e)
    {
    //            Object[] objs = qMMultiList.getSelectedObjects();
    //            if(objs != null && objs.length > 0)
    //            {
    //                okJButton.setEnabled(true);
    //            }
    }

}

/**
 * <p>Title:确定按钮适配器</p> <p>Description: </p>
 */
class ApplyModelRouteJDailog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ApplyModelRouteJDailog adaptee;

    ApplyModelRouteJDailog_okJButton_actionAdapter(ApplyModelRouteJDailog adaptee)
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
class ApplyModelRouteJDailog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ApplyModelRouteJDailog adaptee;

    ApplyModelRouteJDailog_cancelJButton_actionAdapter(ApplyModelRouteJDailog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:qMMultiList的ITEM适配器</p> <p>Description: </p>
 */
class ApplyModelRouteJDailog_qMMultiList_itemAdapter implements java.awt.event.ItemListener
{
    private ApplyModelRouteJDailog adaptee;

    ApplyModelRouteJDailog_qMMultiList_itemAdapter(ApplyModelRouteJDailog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e)
    {
        adaptee.qMMultiList_itemStateChanged(e);
    }
}