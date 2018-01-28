/** 
 * 生成程序 SelectPartTreeJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.faw_qm.cappclients.conscapproute.util.PartMasterTreeNode;
import com.faw_qm.cappclients.conscapproute.util.PartMasterTreeObject;
import com.faw_qm.cappclients.conscapproute.util.PartMasterTreePanel;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.util.RouteListLevelType;

/**
 * <p> 从产品结构中添加零部件时,调用本届面,选择零部件 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class SelectPartTreeJDialog extends JDialog
{
    private JPanel panel1 = new JPanel();

    private JPanel buttonPanel = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    /** 标记：是否执行了保存操作 */
    public boolean isSave = false;

    /** 业务对象:路线表 */
    private TechnicsRouteListIfc myRouteList;

    /** 父窗口 */
    private Frame parentFrame;

    /** 资源文件路径 */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 零部件树 */
    private PartMasterTreePanel partTreeJPanel;

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * 构造函数
     * @param frame 父窗口
     */
    public SelectPartTreeJDialog(Frame frame)
    {
        super(frame, "", true);
        parentFrame = frame;
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 构造函数
     */
    public SelectPartTreeJDialog()
    {
        this(null);
    }

    /**
     * 界面初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("选择零部件");
        this.setSize(650, 500);
        this.addWindowListener(new SelectPartTreeJDialog_this_windowAdapter(this));
        panel1.setLayout(gridBagLayout2);
        buttonPanel.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(41, 22));
        statusJLabel.setMinimumSize(new Dimension(41, 22));
        statusJLabel.setPreferredSize(new Dimension(41, 22));
        statusJLabel.setText("从产品结构中选择欲编辑路线的零部件");

        buttonPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        buttonPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(statusJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        getContentPane().add(panel1);

        panel1.add(buttonPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 0, 20, 10), 0, 0));
        localize();

    }

    /**
     * 本地化
     */
    private void localize()
    {

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
     * 设置路线表
     * @param list 路线表
     */
    public void setRouteList(TechnicsRouteListIfc list)
    {
        try
        {
            this.myRouteList = list;
            QMPartMasterIfc productInfo = (QMPartMasterIfc)RParentJPanel.refreshInfo(list.getProductMasterID());
            partTreeJPanel = new PartMasterTreePanel(RParentJPanel.getIdentity(productInfo) + "的产品结构");
            panel1.add(partTreeJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
            this.addProductToTree(productInfo);
        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
        }
    }

    /**
     * 获得路线表
     * @return 路线表
     */
    public TechnicsRouteListIfc getRouteList()
    {
        return this.myRouteList;
    }

    /**
     * 获得父窗口
     * @return 父窗口
     */
    public Frame getParentFrame()
    {
        return this.parentFrame;
    }

    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     * @param parts 零部件值对象集合
     */
    private void addProductToTree(QMPartMasterIfc product) throws QMException
    {
        Class[] c = {QMPartMasterIfc.class};
		Object[] obj = {product};
		Collection cc = (Collection)RequestHelper.request("consTechnicsRouteService", "getSubPartMasters", c, obj);
		for(Iterator iter = cc.iterator();iter.hasNext();)
		{
		    PartMasterTreeObject treeobj = new PartMasterTreeObject((QMPartMasterIfc)iter.next());
		    partTreeJPanel.addNode(treeobj);
		}
    }

    /**
     * 确认操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        isSave = true;
        this.setVisible(false);
    }

    /**
     * 取消操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        isSave = false;
        this.setVisible(false);
    }

    /**
     * 获得用户已选择的所有零部件
     * @return 零部件值对象集合
     */
    public Vector getSelectedParts()
    {
        Vector v = new Vector();
        //获得树上所有被选中的节点
        PartMasterTreeNode[] nodes = partTreeJPanel.getSelectedNodes();
        if(nodes != null && nodes.length > 0)
        {

            for(int i = 0;i < nodes.length;i++)
            {
                if(nodes[i].getObject() != null)
                {
                    QMPartMasterInfo masterInfo = (QMPartMasterInfo)nodes[i].getObject().getObject();
                    v.add(masterInfo);
                }
            }
            //如果是二级路线，需进行检查
            if(this.myRouteList.getRouteListLevel().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
            {
                Class[] c = {String.class, String.class, Collection.class};
                Object[] objs = {myRouteList.getRouteListDepartment(), myRouteList.getProductMasterID(), v};
                Object[] array = null;
                try
                {
                    array = (Object[])RequestHelper.request("consTechnicsRouteService", "checkSubParts", c, objs);
                }catch(Exception ex)
                {
                    String message = QMExceptionHandler.handle(ex);
                    DialogFactory.showWarningDialog(this, message);
                    return null;
                }
                if(array != null)
                {
                    Vector v1 = (Vector)array[0]; //符合条件的零部件主信息
                    Vector v2 = (Vector)array[1]; //不符合条件的零部件主信息
                    if(v2 != null && v2.size() > 0)
                    {
                        String message = "零部件";
                        for(int j = 0;j < v2.size();j++)
                        {
                            String partNumber = ((QMPartMasterInfo)v2.elementAt(j)).getPartNumber();
                            if(j < v2.size() - 1)
                                message = message + partNumber + "、";
                            else
                                message = message + partNumber + "在当前路线表中不能编辑路线。";
                        }
                        JOptionPane.showMessageDialog(this, message, "通知", JOptionPane.INFORMATION_MESSAGE);
                    }
                    return v1;
                }
            }
        }
        return v;
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
        ///SelectPartJDialog d = new SelectPartJDialog();
        //d.setVisible(true);
    }

    void this_windowClosing(WindowEvent e)
    {
        this.isSave = false;
        this.setVisible(false);
    }

}

/**
 * <p>Title:窗口事件监听器</p> <p>Description: </p>
 */
class SelectPartTreeJDialog_this_windowAdapter extends java.awt.event.WindowAdapter
{
    private SelectPartTreeJDialog adaptee;

    SelectPartTreeJDialog_this_windowAdapter(SelectPartTreeJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e)
    {
        adaptee.this_windowClosing(e);
    }
}