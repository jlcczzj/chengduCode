/**
 * 生成程序 ParentPartJDialog 1.0 2005.3.2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 2009/06/08 刘学宇  原因：将“按路线单位搜索”对话框由非模态修改为模态 
 * SS1 代码管理中变速箱原来用的“组织机构”改为“组织机构-bsx”，变速箱编辑路线时显示“组织机构-bsx” liunan 2014-6-17
 * SS2 代码管理中成都使用“组织机构-cd liunan 2016-8-29
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.beans.*;

import java.io.BufferedWriter;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.FileWriter;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.capp.view.*;
import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.XLSFileFilter;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.cappclients.util.ComponentMultiList;

//CCBegin SS1
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
//CCEnd SS1

/**
 * <p> Title:按路线单位搜索零部件界面类 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2005 </p> <p> Company: 一汽启明 </p>
 * @author skybird
 * @version 1.0
 */

public class SearchPartsDialog extends JDialog //CR1
{
    //界面组件
    private JComboBox comboBox;
    JPanel panel1 = new JPanel();

    JPanel jPanel2 = new JPanel();

    JPanel jPanel3 = new JPanel();

    JLabel makeJLabel = new JLabel();

    JLabel constructJLabel = new JLabel();
    
    JLabel stateLabel = new JLabel();

    CappSortingSelectedPanel makeSelectPanel;

    CappSortingSelectedPanel contructSelectPanel;
    
    final JPanel panel = new JPanel();

    JButton okJButton = new JButton();

    JButton cancelJButton = new JButton();
    /** 列表 */
    private ComponentMultiList qMMultiList = new ComponentMultiList();

    //逻辑元素
    private String makeDepartmentid = "";

    private String constructDepartmentid = "";

    private JFrame parentFrame;

    private String state = "您选中的 ";

    private String stateMes = "欢迎进入按路线单位搜索. . .";

    /** 用于标记资源文件路径 */
    protected static String RESOURCEP = "com.faw_qm.part.client.other.util.OtherRB";

    /** 要查询的业务对象 */
    public static String SCHEMA = QMMessage.getLocalizedMessage(RESOURCEP, "schema", null);

    /** 查询方案 */
    private CappSchema mySchema;

    /** 资源文件路径 */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    TitledBorder titledBorder1;

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    JLabel partNoLabel = new JLabel();
    
    JLabel partNameLabel = new JLabel();

    JTextField partNoTextField = new JTextField();
    JTextField partNameTextField = new JTextField();

    JButton serchPartNoButton = new JButton();

    JLabel sourceLabel = new JLabel();

    EnumeratedChoice sourceComboBox = new EnumeratedChoice();

    EnumeratedChoice typeComboBox = new EnumeratedChoice();

    JLabel typeLabel = new JLabel();

    String productID = "";

    GridBagLayout gridBagLayout2 = new GridBagLayout();

    GridBagLayout gridBagLayout3 = new GridBagLayout();
    JButton exportButton = new JButton();

    JPanel jPanel4 = new JPanel();

    // JComboBox jComboBox2 = new JComboBox();
    //JComboBox jComboBox1 = new JComboBox();

    //CCBegin SS1
    private String comp="";
    //CCEnd SS1
    
    public SearchPartsDialog(JFrame frame)
    {
    	
        //begin CR1
        super(frame, "", true);
        getContentPane().setLayout(new GridBagLayout());
        setSize(new Dimension(801, 600));
        //end CR1
        this.parentFrame = frame;
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
            pack();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

       
       
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit() throws Exception
    {
        URL url = CappRouteListManageJFrame.class.getResource("/images/routeM.gif");
        if(url != null)
        {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));

        }
        
        
        
        qMMultiList.setDebugGraphicsOptions(0);
        qMMultiList.setMaximumSize(new Dimension(380, 240));
        qMMultiList.setInputVerifier(null);
        qMMultiList.setHeadings(new String[]{"id", "零件编号", "零件名称", "状态", "数量", "版本", "制造路线", "装配路线", "路线单号", "编制日期"});
        qMMultiList.setRelColWidth(new int[]{0, 2, 2, 1, 1, 1, 3, 3, 2, 1});
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
       
        final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
        gridBagConstraints_1.gridx = 0;
        gridBagConstraints_1.gridy = 1;
        gridBagConstraints_1.ipadx = 450;
        gridBagConstraints_1.ipady = 15;
        getContentPane().add(panel, gridBagConstraints_1);
        panel.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(8, 5, 25, 0), 290, 240));

        
        exportButton.setText("导  出");
        final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
        gridBagConstraints_2.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints_2.insets = new Insets(0, 0, 20, 30);
        gridBagConstraints_2.anchor = GridBagConstraints.EAST;
        gridBagConstraints_2.gridy = 1;
        gridBagConstraints_2.gridx = 0;
        panel.add(exportButton, gridBagConstraints_2);
       
        
        
        this.setResizable(false);
        this.setTitle("按路线单位搜索");
        titledBorder1 = new TitledBorder("");
        panel1.setLayout(gridBagLayout3);
        panel1.setBorder(new BevelBorder(BevelBorder.RAISED));
     
        jPanel2.setLayout(gridBagLayout1);
        makeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        makeJLabel.setText("制造单位");
        constructJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        constructJLabel.setText("装配单位");
        stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stateLabel.setText("状态");
        okJButton.setText("搜索(S)");
        okJButton.setMnemonic('S');
        //初始化的时候ok键不能使动
        okJButton.setEnabled(true);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("通过选择的制造单位和装配单位来搜索零部件. . .");
        okJButton.addMouseListener(new MyMouseListener());
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
        cancelJButton.setMnemonic('C');
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        cancelJButton.setText("取消(C)");
        System.out.println("画路线11 comp=="+comp);
        //CCBegin SS1
        Class[] c = { String.class, String.class };
        Object[] obj = { "组织机构-bsx", "代码分类" };
        CodingClassificationIfc cc = (CodingClassificationIfc) RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
        
        //makeSelectPanel = new CappSortingSelectedPanel("单位", "consTechnicsRouteList", "routeListDepartment");
        if(comp.equals("zczx"))
        {
        	makeSelectPanel = new CappSortingSelectedPanel("单位", "consTechnicsRouteList", "routeListDepartment");
        }
        //CCBegin SS2
        else if(comp.equals("cd"))
        {
        	Class[] c1 = { String.class, String.class };
        	Object[] obj1 = { "组织机构-cd", "代码分类" };
        	CodingClassificationIfc cc1 = (CodingClassificationIfc) RequestHelper.request("CodingManageService", "findClassificationByName", c1, obj1);
        System.out.println("cc1======"+cc1);
        	makeSelectPanel = new CappSortingSelectedPanel(cc1);
        	contructSelectPanel = new CappSortingSelectedPanel(cc1);
        }
        //CCEnd SS2
        else
        {
        	makeSelectPanel = new CappSortingSelectedPanel(cc);
        }
        //CCEnd SS1
        
        //add by guoxl on 2008.3.27(为按路线单位搜索中制造单位后的浏览按钮所弹出的界面添加标题)
        makeSelectPanel.setDialogTitle("制造单位");
        //add by guoxl end
        makeSelectPanel.setSelectBMnemonic('M');
        makeSelectPanel.setIsSelectCC(true);
        makeSelectPanel.transferFocus();
        
        //CCBegin SS1
        //contructSelectPanel = new CappSortingSelectedPanel("单位", "consTechnicsRouteList", "routeListDepartment");
        if(comp.equals("zczx"))
        {
        	contructSelectPanel = new CappSortingSelectedPanel("单位", "consTechnicsRouteList", "routeListDepartment");
        }
        //CCBegin SS2
        else if(comp.equals("cd"))
        {
        }
        //CCEnd SS2
        else
        {
        	contructSelectPanel = new CappSortingSelectedPanel(cc);
        }
        //CCEnd SS1
        
        //add by guoxl on 2008.3.27(为按路线单位搜索中装配单位后的浏览按钮所弹出的界面添加标题)
        contructSelectPanel.setDialogTitle("装配单位");
        //add by guoxl end
        contructSelectPanel.setIsSelectCC(true);

        jPanel3.setLayout(gridBagLayout2);

        partNoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partNoLabel.setText("零件编号");
        partNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partNameLabel.setText("零件名称");
        serchPartNoButton.setMaximumSize(new Dimension(89, 23));
        serchPartNoButton.setMinimumSize(new Dimension(89, 23));
        serchPartNoButton.setPreferredSize(new Dimension(89, 23));
        serchPartNoButton.setMnemonic('P');
        serchPartNoButton.setSelected(false);
        serchPartNoButton.setText("搜索(P)...");
        serchPartNoButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
					serchPartNoButton_actionPerformed(e);
				} catch (QMRemoteException e1) {
					e1.printStackTrace();
					return;
				}
            }
        });
        exportButton.addActionListener(new java.awt.event.ActionListener(){
        
        
        public void actionPerformed(ActionEvent e)
        {
            try {
				exportButton_actionPerformed(e);
			} catch (QMRemoteException e1) {
				e1.printStackTrace();
				return;
			}
        }
        
        
        } );
        sourceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sourceLabel.setText("来源");
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeLabel.setText("类型");
        partNoTextField.setEnabled(true);
        partNoTextField.setMinimumSize(new Dimension(220, 22));
        partNoTextField.setPreferredSize(new Dimension(220, 22));
        partNoTextField.setText("");
        
       
        partNameTextField.setEnabled(true);
        partNameTextField.setMinimumSize(new Dimension(220, 22));
        partNameTextField.setPreferredSize(new Dimension(220, 22));
        partNameTextField.setText("");
        
        sourceComboBox.setMinimumSize(new Dimension(220, 22));
        sourceComboBox.setPreferredSize(new Dimension(220, 22));
        typeComboBox.setMinimumSize(new Dimension(220, 22));
        typeComboBox.setPreferredSize(new Dimension(220, 22));

        sourceComboBox.setEnumeratedTypeClass(Class.forName("com.faw_qm.part.util.ProducedBy"));
        sourceComboBox.addItem("");
        sourceComboBox.setSelectedItem("");
        typeComboBox.setEnumeratedTypeClass(Class.forName("com.faw_qm.part.util.QMPartType"));
        typeComboBox.addItem("");
        typeComboBox.setSelectedItem("");
        panel1.setPreferredSize(new Dimension(411, 153));
        panel1.add(jPanel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(30, 0, 20, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
        jPanel3.add(partNoLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(partNoTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 8, 4, 10), 0, 0));
//        jPanel3.add(serchPartNoButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(partNameLabel,     new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 7), 0, 0));
        jPanel3.add(partNameTextField, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 8, 4, 10), 0, 0));
        
        
        //  jPanel3.add(sourceLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        //       ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0,
        // 0, 0), 0, 0));
        //jPanel3.add(sourceComboBox, new GridBagConstraints(1, 1, 2, 1, 1.0,
        // 0.0
        //       ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new
        // Insets(4, 8, 4, 0), 0, 0));
        //  jPanel3.add(typeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        //      ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0,
        // 0, 0), 0, 0));
        // jPanel3.add(typeComboBox, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
        //        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new
        // Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(makeSelectPanel, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 0), 0, 0));
        jPanel3.add(makeJLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 16, 6));
        jPanel3.add(stateLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 16, 6));
        jPanel3.add(contructSelectPanel , new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 10), 0, 0));
        jPanel3.add(constructJLabel, new GridBagConstraints(2, 3, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 10), 0, 0));

        comboBox = new JComboBox();
        jPanel3.add(comboBox, new GridBagConstraints(1, 4, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 5, 4, 0), 0, 0));

        jPanel2.add(okJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(jPanel4, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.ipady = 35;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 175;
        this.getContentPane().add(panel1, gridBagConstraints);
        
        initStateJComboBox();
    }
    /**
     * 构造一个方法，专门用于远程调用服务端的方法：
     * @param component 表示调用该方法的当前界面对象，可以为空
     * @param serviceName 被调用的服务端服务类名(ServiceName)
     * @param methodName 被调用的方法名
     * @param paramTypes 被调用的方法的参数类型集合，需要按照方法的顺序
     * @param paramValues 传输的各个参数的值
     * @throws QMRemoteException 抛出远程异常
     * @return 结果对象
     */
    public static Object invokeRemoteMethodWithException(Component component, String serviceName, String methodName, Class[] paramTypes, Object[] paramValues)
    {
    	RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info = new ServiceRequestInfo();
        info.setServiceName(serviceName);
        info.setMethodName(methodName);
        //参数类型的集合
        Class[] paraClass = paramTypes;
        info.setParaClasses(paraClass);
        //参数值的集合
        Object[] objs = paramValues;
        info.setParaValues(objs);
        Object result = null;
        try
        {
            result = RequestHelper.request(info);
        }catch(Exception ex)
        {
            //对异常进行处理，输出异常信息，并再抛。
            ex.printStackTrace();

        }
        //end try-catch
        return result;
    }
    /**
     * 初始化工艺路线表状态复选框的值
     */
    private void initStateJComboBox()
    {
        Class[] params = {String.class, String.class};
        Object[] values = {"状态", "工艺路线表"};
        Collection result = null;
        try
        {
            result = (Collection)invokeRemoteMethodWithException(this, "CodingManageService", "findDirectClassificationByName", params, values);
            //System.out.println("找到状态数是  " + result.size());
        }catch(Exception ex)
        {
            //输出异常信息：
            String message = ex.getMessage();
            DialogFactory.showErrorDialog(this, message);
            return;
        }

        if(result != null && result.size() > 0)
        {
        	comboBox.addItem("");
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
            	comboBox.addItem((iterator.next()).toString());
            }
            comboBox.setSelectedItem("");
        }
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
     * 执行“搜索”操作
     * @param e
     */
    private void okJButton_actionPerformed(ActionEvent e)
    {
        try
        {
        	 setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            confirmOperation();
            setCursor(Cursor.getDefaultCursor());
        }catch(Exception ex)
        {
            setCursor(Cursor.getDefaultCursor());
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this, message);
        }
    }

    /**
     * 执行"取消"操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     * 在这里应该检验搜索条件，然后调用相应 服务端的方法，然后用瘦客户把结果显示 给用户 确定键的执行逻辑
     * @throws QMRemoteException
     */
    public void confirmOperation()
    {
    	qMMultiList.clear();
    	Collection muResult;
        BaseValueIfc makeV = makeSelectPanel.getCoding();
        String partNum=this.partNoTextField.getText().trim();
        String partName=this.partNameTextField.getText().trim();
        String state=this.comboBox.getSelectedItem().toString();
       
        if(makeV != null)
        {
            this.makeDepartmentid = makeV.getBsoID();
        }else
            this.makeDepartmentid = "";
        BaseValueIfc constructV = this.contructSelectPanel.getCoding();
        if(constructV != null)
        {
            this.constructDepartmentid = constructV.getBsoID();
        }else
            this.constructDepartmentid = "";
        if((this.makeDepartmentid == null || this.makeDepartmentid.equals("")) && (this.constructDepartmentid == null || this.constructDepartmentid.equals("")))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, "请选择路线单位", title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(partNum == null || partNum.trim().equals(""))
        {
            int result = JOptionPane.showConfirmDialog(this, "汇总时间可能会很长，您继续吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch(result)
            {
            case JOptionPane.NO_OPTION:
            {
                return;
            }
            }

        }
        //以下是调用服务的方法
        //  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        HashMap hashmap = new HashMap();
        // System.out.println("makeDepartment" + this.makeDepartmentid);
        // System.out.println("constructDepartment" + this.constructDepartmentid);
        // System.out.println("productID" + this.productID);
        EnumeratedType sourceET = this.sourceComboBox.getSelectedEnumeratedType();
        if(sourceET != null)
        {
            // System.out.println("source=" + sourceET.toString());
            hashmap.put("source", sourceET.toString());
        }else
        {
            hashmap.put("source", null);
        }
        EnumeratedType typeET = this.typeComboBox.getSelectedEnumeratedType();
        if(typeET != null)
        {
            //  System.out.println("type=" + typeET.toString());
            hashmap.put("type", typeET.toString());
        }else
        {
            hashmap.put("type", null);
        }
//        hashmap.put("productID", this.productID);
//        hashmap.put("mDepartment", makeDepartmentid);
//        hashmap.put("cDepartment", constructDepartmentid);
        //将bsoID传到“查看路线表”页面
//        RichToThinUtil.toWebPage("route_look_partsListBy.screen", hashmap);
        //view.setCursor(Cursor.getDefaultCursor());
        Class[] params = {String.class, String.class,String.class,String.class,String.class};
        Object[] values = {makeDepartmentid,constructDepartmentid,partNum,partName,state};
        muResult = (Collection)invokeRemoteMethodWithException(this, "consTechnicsRouteService", "getAllPartsRoutes_new", params, values);
   
        if(muResult==null||muResult.size()==0){
        	
        	
        	 JOptionPane.showMessageDialog(this, "没有找到对应的结果信息，请重新输入搜索条件!",
        			 "提示", JOptionPane.INFORMATION_MESSAGE);
        	 return;
        }else{
            addInfoToList((Vector)muResult);
        }
        
        
       
        
    }

	private void addInfoToList(Vector v) {
		
		if (v != null && v.size() != 0) {
			for (int i = 0; i < v.size(); i++) {

				Vector v2 = (Vector) v.get(i);
				for (int j = 0; j < v2.size(); j++) {
					Object obj = (Object) v2.get(j);
					
					if(obj instanceof String ){
						
						String value=(String)obj;
						
						this.qMMultiList.addTextCell(i, j+1, value);
						
					} else if (obj instanceof Integer) {
						Integer value = (Integer) obj;

						this.qMMultiList
								.addTextCell(i, j + 1, value.toString());

					}
					
					
					
				}
			}
		}

	}
    void makeJTextField_actionPerformed(ActionEvent e)
    {

    }

    /**
     * <p>Title:鼠标处理事件适配器</p> <p>Description: </p>
     */
    class MyMouseListener extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            Object obj = e.getSource();
            //工具条按钮
            if(obj instanceof JButton)
            {
                JButton button = (JButton)obj;

            }
            //菜单项
            /*
             * if (obj instanceof QMMenuItem) { QMMenuItem item = (QMMenuItem) obj; String s = item.getExplainText(); statusBar.setText(s); } //菜单 if (obj instanceof QMMenu) { QMMenu item = (QMMenu)
             * obj; statusBar.setText(item.getExplainText()); }
             */
        }

        public void mouseExited(MouseEvent e)
        {
        //鼠标移出时，状态栏显示缺省信息
        //statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
        // "default_status", null));

        }
    }
    class FileFilter extends javax.swing.filechooser.FileFilter
    {

        /**
         * 构造Excel文件过滤器
         */
        public FileFilter()
        {}

        /**
         * 判断指定的文件是否被本过滤器接受
         * @param f 文件
         * @return 如果接受，则返回true
         */
        public boolean accept(File f)
        {
            boolean accept = f.isDirectory();
            if(!accept)
            {
                String suffix = getSuffix(f);
                if(suffix != null)
                {
                    accept = suffix.equals("csv");
                }

            }
            return accept;
        }

        /**
         * 获得本过滤器的描述信息
         * @return Excel Files(*.csv)
         */
        public String getDescription()
        {
            return "Excel Files(*.csv)";
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
            if(i > 0 && i < s.length() - 1)
                suffix = s.substring(i + 1).toLowerCase();
            return suffix;
        }
    }
    /**
     * 导出excel
     * 
     */
     private void exportButton_actionPerformed(ActionEvent e)throws QMRemoteException{
	
    	 //文件选择器
         JFileChooser chooser = new JFileChooser();
         //设置文件选取模式：文件和路径
         chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         //设置不可多选
         chooser.setMultiSelectionEnabled(false);
         
         FileFilter filter = new FileFilter();
         
         chooser.setDialogTitle("输出报表至...");
         chooser.setFileFilter(filter);
         //删除系统自带的AcceptAllFileFilter
         chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());

         //“打开”模式文件选择器中选择了批准按钮还是取消按钮
         int state = chooser.showSaveDialog(this);// zz 指定了父界面 2006-11
         //获得选择的文件
         File selectedFile = chooser.getSelectedFile();
         //如果选择了批准按钮,则获得所选择文件名
         if(selectedFile != null && state == JFileChooser.APPROVE_OPTION)
         {
             //文件格式转换
             selectedFile = this.translateFile(selectedFile, filter);

             //判断 1 未输入文件名,请输入文件名称 2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
             if(!filter.accept(selectedFile))
             {
                 JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.PATH_NOT_EXIST, null), QMMessage.getLocalizedMessage(RESOURCE, "error", null),
                         JOptionPane.ERROR_MESSAGE);
                 return;
             }
             if(selectedFile.exists())
             {
                 JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.FILE_ALREADY_EXIST, null), QMMessage.getLocalizedMessage(RESOURCE, "warning", null),
                         JOptionPane.WARNING_MESSAGE);
                 return;
             }
            
             try
             {
            	 
            	 String tableTitle="零件编号"+","+"零件名称"+","+"状态"+","+"数量"+","+"版本"+","+"制造路线"+","+"装配路线"+","+"路线单号"+","+"编制日期";
            	 
            	int rows= this.qMMultiList.getRowCount();
            	int colum= this.qMMultiList.getTable().getColumnCount();
            	 
                 BufferedWriter fw = new BufferedWriter(new FileWriter(selectedFile.getPath(), true));
                 fw.write(tableTitle+"\n");
                 for(int i=0;i<rows;i++){
                	 
                   for(int k=1;k<colum;k++){
                	  
                	  String value=this.qMMultiList.getCellText(i, k);
                	  if(k==colum-1){
                		 
                		  value=value.replaceAll(" ", "--");
                	  }
                	  fw.write(value+",");
                  }
                   fw.write("\n");
                 }
                 fw.flush();
                 fw.close();
             }catch(Exception ex)
             {
            	
                 String message = ex.getMessage();
                 DialogFactory.showWarningDialog(this, message);
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(this, "导出过程出错!",
            			 "提示", JOptionPane.INFORMATION_MESSAGE);
                 return;
             }
             JOptionPane.showMessageDialog(this, "导出成功!",
        			 "提示", JOptionPane.INFORMATION_MESSAGE);
        	 
             this.setVisible(false);
         }
	
     }
     /**
      * 如果file没有扩展名，则加扩展名
      * @param file 文件
      * @param filter 文件过滤器
      * @return 格式转换后的文件
      */
     private File translateFile(File file, FileFilter filter)
     {
         String path = file.getPath();
    if(filter instanceof FileFilter)
         {
             if(!path.endsWith(".csv"))
             {
                 path = path + ".csv";
             }
         }
         return new File(path);
     }
    /**
     * 搜索零部件编号
     * @param e
     * @throws QMRemoteException 
     */
    void serchPartNoButton_actionPerformed(ActionEvent e) throws QMRemoteException
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle", null);
        //定义搜索器
        CappChooser qmChooser = new CappChooser("QMPart", title, this);
        mySchema = new CappSchema(SCHEMA);
        //设置查询方案
       
			qmChooser.setSchema(mySchema);
		
        qmChooser.setLastIteration(true);
        //qmChooser.setRelColWidth(new int[]{1,1});
        qmChooser.setChildQuery(false);
        qmChooser.setSplitDividerLocation(360);
        //按照给定条件，执行搜索
        qmChooser.addListener(new CappQueryListener()
        {

            public void queryEvent(CappQueryEvent e)
            {
                qmChooser_queryEvent(e);
            }
        });
        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }

        qmChooser.setVisible(true);

    }

    /**
     * 搜索零部件监听事件方法
     * @param e 搜索监听事件
     */
    private void qmChooser_queryEvent(CappQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if(e.getType().equals(CappQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(CappQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                CappChooser c = (CappChooser)e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if(bvi != null)
                {
                    productID = bvi.getBsoID();
                    partNoTextField.setText(RParentJPanel.getIdentity(bvi));
                }
            }
            if(e.getCommand().equals(CappQuery.QuitCMD))
            {
                productID = "";
                partNoTextField.setText("");
            }
        }

        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

   

}
