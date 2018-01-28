/** 
 * 生成程序 RoutePasteToJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.CheckboxMultiList;

import javax.swing.*;

import java.util.*;

import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.technics.consroute.util.RouteWrapData;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.part.model.*;

import java.awt.event.*;

/**
 * <p> Title:粘贴到零部件列表界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class RoutePasteToJDialog extends JDialog
{
    private CheckboxMultiList qMMultiList = new CheckboxMultiList();

    private JPanel jPanel1 = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private Vector vec = new Vector();
    //显示新路线串信息
    private JLabel label1 = new JLabel();
    //新路线串值
    private String routeStr;
    //是否点击确定按钮
    public static boolean isSave = false;
    //存放替换标识为true的行 20120118 徐春英 add
    private Vector vector = new Vector();
    //父界面
    private JFrame parentFrame;
    
    /**
     * @roseuid 4031A77200CE
     */
    public RoutePasteToJDialog()
    {
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

//    /**
//     * 构造函数
//     * @param parent 父窗口
//     * @粘贴路线时应用
//     */
//    public RoutePasteToJDialog(JPanel panel, Vector vec,RouteWrapData wrapdata,int[] index)
//    {
//        //super(panel, "", true);
//        this.vec = vec;
//        //this.panel = (RouteListPartLinkJPanel)panel;
//        this.wrapdata=wrapdata;
//        this.index=index;
//        try
//        {
//            jbInit();
//            showPart(vec);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    /**
     * 构造函数
     * @param parent 父窗口
     * @粘贴路线时应用 20120118 xucy modify
     */
    public RoutePasteToJDialog(JFrame frame, Vector vec,String routeStr)
    {
        super(frame, "", true);
        parentFrame = frame;
        this.vec = vec;
        this.routeStr = routeStr;
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
     */
    private void jbInit()
    {
        this.setTitle("覆盖零件路线");
        this.setSize(900, 500);
        this.setModal(true);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new RoutePasteToJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new RoutePasteToJDialog_cancelJButton_actionAdapter(this));
        //statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        label1.setText("新路线串信息 "+"<"+routeStr+">");
        label1.setFont(new Font("Dialog", 1, 14));
        statusJLabel.setText("所选零部件原路线信息");
        statusJLabel.setFont(new Font("Dialog", 0, 12));
        qMMultiList.addItemListener(new RoutePasteToJDialog_qMMultiList_itemAdapter(this));
        jPanel1.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 8, 0, 0), 0, 0));
        this.getContentPane().add(statusJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 8, 0, 0), 0, 0));
        this.getContentPane().add(qMMultiList, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

        qMMultiList.setHeadings(new String[]{"id", "标识", "零部件编号", "零部件名称", "主要路线", "次要路线"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 3, 3, 4, 5});
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
        for(int i = 0;i < vec.size();i++)
        {
            RouteWrapData wrapdata1 = (RouteWrapData)vec.elementAt(i);
            Class[] c = {String.class};
            Object[] obj = {wrapdata1.getPartMasterID()};
            ImageIcon image = new ImageIcon(getClass().getResource("/images/partMaster.gif"));
            qMMultiList.addTextCell(i, 0, wrapdata1.getPartMasterID());
            qMMultiList.setCheckboxSelected(i, 1, true);
            qMMultiList.addCell(i, 2, wrapdata1.getPartNum(), image.getImage());
            qMMultiList.addTextCell(i, 3, wrapdata1.getPartName());
            qMMultiList.addTextCell(i, 4, wrapdata1.getMainStr());
            qMMultiList.addTextCell(i, 5, wrapdata1.getSecondStr());
        }
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
        RoutePasteToJDialog d = new RoutePasteToJDialog();
        d.setVisible(true);
    }

    /**
     * 执行确定操作
     * @param e ActionEvent
     * 20120118 徐春英  modify
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        for(int i = 0;i < vec.size();i++)
        {
            RouteWrapData wrapdata1 = (RouteWrapData)vec.elementAt(i);
            if(qMMultiList.isCheckboxSelected(i, 1) == true)
            {
                //设置替换标识为true的行号，以便将替换信息设置到艺准零件关联表中
                vector.add(wrapdata1.getRowNum());
            }
        }
        setIsSave(true);
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

    /**
     * 当选中零部件时，确定按钮有效
     * @param e ItemEvent
     */
    void qMMultiList_itemStateChanged(ItemEvent e)
    {
//        Object[] objs = qMMultiList.getSelectedObjects();
//        if(objs != null && objs.length > 0)
//        {
//            okJButton.setEnabled(true);
//        }
    }
    
    public boolean getIsSave()
    {
        return isSave;
    }

    public void setIsSave(boolean arg)
    {
        isSave = arg;
    }

    
    public Vector getReplaceRows()
    {
        return vector;
    }
}

/**
 * <p>Title:确定按钮适配器</p> <p>Description: </p>
 */
class RoutePasteToJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private RoutePasteToJDialog adaptee;

    RoutePasteToJDialog_okJButton_actionAdapter(RoutePasteToJDialog adaptee)
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
class RoutePasteToJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private RoutePasteToJDialog adaptee;

    RoutePasteToJDialog_cancelJButton_actionAdapter(RoutePasteToJDialog adaptee)
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
class RoutePasteToJDialog_qMMultiList_itemAdapter implements java.awt.event.ItemListener
{
    private RoutePasteToJDialog adaptee;

    RoutePasteToJDialog_qMMultiList_itemAdapter(RoutePasteToJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e)
    {
        adaptee.qMMultiList_itemStateChanged(e);
    }
}