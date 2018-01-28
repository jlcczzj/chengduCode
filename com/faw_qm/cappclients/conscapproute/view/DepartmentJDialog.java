/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * SS1 代码管理中变速箱原来用的“组织机构”改为“组织机构-bsx”，变速箱编辑路线时显示“组织机构-bsx” liunan 2014-6-17
 * SS2 代码管理中成都使用“组织机构-cd liunan 2016-8-29
 */

package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.client.view.CodeManageTree;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.remote.RequestHelper;

//CCBegin SS1
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS1

/**
 * <p>Title:组织机构呈现界面</p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: 一汽启明公司</p>
 * @author skybird
 * @version 1.0
 */

public class DepartmentJDialog extends JDialog
{
    //逻辑元素
    private CodeManageTree departmentTree;
    /** 父窗口 */
    private JFrame parentFrame;
    /** 标记:是否保存数据 */
    public boolean isSave = false;
    //记录被选中的单位名称和bsoid
    public static Vector result = null;
    //被选中单位的名称
    public static String departmentName = null;
    //保存被选中的单位的bsoid
    public static String departmentNameid = null;

    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JButton okJButton = new JButton();
    JButton cancelJButton = new JButton();
    JLabel departmentJLabel = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    GridBagLayout gridBagLayout3 = new GridBagLayout();

    //CCBegin SS1
    private String comp="";
    //CCEnd SS1
    
    public DepartmentJDialog(JFrame frame)
    {
        super(frame, "", true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        result = null;
        try
        {
        	//CCBegin SS1
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
            //CCEnd SS1
            jbInit();
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
        this.getRootPane().setDefaultButton(this.okJButton);
        this.setSize(400, 380);
        this.getContentPane().setLayout(gridBagLayout3);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jPanel1.setLayout(gridBagLayout2);
        jPanel2.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setRequestFocusEnabled(true);
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
        cancelJButton.setVerifyInputWhenFocusTarget(true);
        cancelJButton.setMargin(new Insets(2, 14, 2, 14));
        cancelJButton.setText("取消(Y)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        departmentJLabel.setText("路线单位");
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
        this.getContentPane().add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(9, 225, 14, 10), 4, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(9, 4, 20, 3), 16, 0));
        jPanel2.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(9, 3, 20, 2), 16, 0));
        jPanel1.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(6, 0, 10, 4), 372, 253));
        jPanel1.add(departmentJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 251), 85, 4));
        initTree();
        addDepartmentTree();
    }

    /**
     * 本地化工作
     */
    private void localize()
    {}

    private void initTree()
    {
        try
        {
            //初始化路线单位树
            CodingClassificationIfc cc = null;
            Class[] c = {String.class, String.class};
            System.out.println("画路线 comp=="+comp);
            //CCBegin SS1
            //Object[] obj = {"组织机构", "代码分类"};
            //cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            if(comp.equals("zczx"))
            {
            	Object[] obj = {"组织机构", "代码分类"};
            	cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            }
            //CCBegin SS2
            else if(comp.equals("cd"))
            {
            	Object[] obj = {"组织机构-cd", "代码分类"};
            	cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            }
            //CCEnd SS2
            else
            {
            	Object[] obj = {"组织机构-bsx", "代码分类"};
            	cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            }
            //CCEnd SS1

            if(cc != null)
            {
                departmentTree = new CodeManageTree(cc);
                departmentTree.setShowsRootHandles(false);
            }
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(parentFrame, message);
        }
    }

    /**
     * 向本界面添加路线单位树
     * @param departmentTree
     */
    public void addDepartmentTree()
    {
        if(this.departmentTree != null)
        {
            this.departmentTree = departmentTree;
            jScrollPane1.getViewport().add(this.departmentTree, null);
            this.departmentTree.addMouseListener(new DepartmentJDialog_departmentTree_mouseAdapter(this));
        }
    }

    /**
     * 在组织结构树上点击鼠标
     * @param e 鼠标事件
     */
    void departmentTree_mousePressed(MouseEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)departmentTree.getLastSelectedPathComponent();
        if(node == null)
        {
            return;
        }
        Object object = node.getUserObject();
        if(object instanceof CodingIfc || (object instanceof CodingClassificationIfc))
        {
            //String departmentName = null;
            if(object instanceof CodingIfc)
            {
                CodingIfc bvi = (CodingIfc)object;
                departmentName = bvi.getCodeContent();
                departmentNameid = bvi.getBsoID();
                //graphPanel.setCurrentRouteDepartment(bvi.getBsoID(),departmentName);
            }else if(object instanceof CodingClassificationIfc)
            {
                CodingClassificationIfc bvi = (CodingClassificationIfc)object;
                departmentName = bvi.getClassSort();
                departmentNameid = bvi.getBsoID();
            }
            this.departmentName = departmentName;
        }
    }

    /**
     * 确定按钮处理事件
     * @param e
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        if(departmentName != null)
        {
            isSave = true;
        }
        this.setVisible(false);
    }

    /**
     * 如果执行了“取消”，则以现有旧数据提交保存。
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e)
    {
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            cancel();
        }
        super.processWindowEvent(e);
    }

    //Close the dialog
    void cancel()
    {
        dispose();
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
}

/**
 * <p>Title:DepartmentJDialog_departmentTree_mouseAdapter</p> <p>Description: 鼠标监听类</p> <p>Package:com.faw_qm.cappclients.conscapproute.view</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author
 * @version 1.0
 */
class DepartmentJDialog_departmentTree_mouseAdapter extends java.awt.event.MouseAdapter
{ //组织机构呈现界面
    private DepartmentJDialog adaptee;

    //构造函数
    DepartmentJDialog_departmentTree_mouseAdapter(DepartmentJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e)
    {
        adaptee.departmentTree_mousePressed(e);
    }
}
