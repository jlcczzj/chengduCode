/**
 * 生成程序 RouteListTaskJPanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 2009/02/26  郭晓亮  原因：优化检出工艺路线表功能
 *  
 *                         方案：1.不调用duplicate()方法去复制原本关联对象，直接将关联对象的
 *                                 bsoid,createTime,modifyTime属性设为null；
 *                                 
 *                               2.将getRouteListLinkParts()方法合并到copyRouteList()方法中，
 *                                 减少对同一个集合的循环次数，并将持久化服务的
 *                                 查询和保存方法替换为不发信号。
 *                               
 *                               3.在显示被检出的路线表时将查找关联的逻辑在检出操作中分离出去,
 *                                 只有选择"零部件"TAB页时才去查关联.
 *                         
 *                         备注: 性能测试用例名称"检出工艺路线表".  
 *                         
 *CR2  2009/05/14 郭晓亮    原因：TD问题：在创建完一个工艺路线表后，选择到“属性”的
 *                               TAB页然后进行更新操作，在保存时抛出了异常信息。
 *                               
 *                         方案：在更新模式中将添加零件和删除零件的缓存清空。　　　　
 *                         
 *CR3  2009/05/14 郭晓亮   原因:产品测试时发现路线表编号输入超过15个中文字符时弹出异常信息,这是因为
 *                              调用底层的校验逻辑中没把一个中文字符算做两个字节.
 *                         方案:设置编号的输入范围;
 *                              主要的修改已经转给base;     
 *                              
 *CR4 2009/06/04  郭晓亮  参见：测试域:v4r3FunctionTest;TD号2307
 *                                                 
 *CR5 2009/07/02  刘学宇  参见：测试域:v4r3FunctionTest;TD号2498        
 *CR6 2011/12/14  徐春英   原因：folder专题更改，参见folder专题更改说明_谢斌_20091019.doc  
 *CR7 2011/07/07 郭晓亮   参见：测试域:v4r3FunctionTest;TD号4733 
 * CR8 2011/07/08 郭晓亮   参见：测试域:v4r3FunctionTest;TD号4709
 * CR9 2011/07/15 郭晓亮  参见：测试域:v4r3FunctionTest;TD号4733
 * CR10 2011/12/28 吕航 原因，为路线与结构有关和路线仅与本件有关时用于产品不是必填项
 * CR11 2012/01/04 徐春英  原因：修改保存艺准零件关联的方法
 * CR12 2012/03/31 吕航 参见TD6000
 * CR13 20140303 吕航 参见TD2636
 * SS1 2013-03-01 刘家坤 原因:当用户选择通知书状态为“艺毕”时，系统认为该通知书为艺毕通知书
 * SS2 2013-11-26 马晓彤 原因:创建工艺路线时，生命周期默认值改为“变速箱艺准通知书”
 * SS3 3014-1-10 刘扬  增加轴齿中心用户判断 
 * SS4 2014-1-10 刘扬 轴齿中心隐藏级别，默认一级路线
 * SS5 2014-2-7 刘扬 轴齿中心路线表说明属性中发往单位 
 * SS6 2014-2-27 刘扬 轴齿中心按照编制的工艺准备通知书类型进行自动编号
 * SS7 调整变速箱各类型工艺路线默认说明信息 liunan 2014-5-12
 * SS8 将SS7中调整的 “发往单位:” 都改为 “发往单位：” liunan 2014-5-30
 * SS9 轴齿要求修改路线说明中"发往单位“ pante 2014-10-09
 * SS10 轴齿再次要求修改路线说明中"发往单位“：生产管理室改生产计划室，并去掉试制车间 pante 2014-11-06
 * SS11 轴齿要求增加清除用于产品功能 pante 2014-11-11
 * SS12 长特路线表说明信息 liuyang 2014-8-21
 * SS13 创建时长特选择路线级别界面 liuyang 2014-8-27
 * SS14 创建长特二级路线时显示“单位” liuyang 2014-8-27
 * SS15 长特根据不同模式和路线级别刷新零部件表 liuyang 2014-9-19
 * SS16 长特去掉变速箱默认生命周期 文柳 2014-12-22
 * SS17 长特修改路线编号30个字符限制，变成40个字符 文柳 2015-3-13 再扩大为100 liunan 2016-11-14
 * SS18 修改长特路线选择“状态”则清空路线编号的问题 文柳 2015-3-24
 * SS19 长特新建路线，默认状态为“艺准” 文柳 2015-4-22
 * SS20 长特增加默认发往单位信息 liuzhicheng 2015-12-17
 * SS21 2016-05-26 刘家坤 如果是轴齿用户则创建工艺路线时，生命周期默认值改为“轴齿中心工艺准备通知书生命周期”
 * SS22 成都工艺路线整合，自动编号，用于产品必填，默认属性调整 liunan 2016-8-5
 * SS23 成都用户验证是否有重复名称路线，有重复的不保存 liuyuzhu 2016-10-12
 * SS24 更改SS7 SS8内容，调整变速箱各类型工艺路线默认说明信息 liunan 2017-2-7
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.faw_qm.clients.beans.folderPanel.*;
import com.faw_qm.clients.beans.lifecycle.*;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.users.model.*;
import com.faw_qm.folder.model.*;
import com.faw_qm.lifecycle.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.cappclients.resource.view.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.cappclients.conscapproute.controller.*;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

/**
 * Title:维护路线表 Description: Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @mender skybird
 * @mender zz
 * @version 1.0 （问题一）zz 20060925 换乘新版进度条 周茁添加 （问题二）防止下面重设树焦点时再次出发保存提示 zz 20061106 周茁添加
 */
public class RouteListTaskJPanel extends RParentJPanel
{
    private JTabbedPane jTabbedPane = new JTabbedPane();

    private JPanel jPanel1 = new JPanel();

    /***/
    private RouteListPartLinkJPanel partLinkJPanel;

    private JScrollPane jScrollPane1 = new JScrollPane();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel attriJPanel = new JPanel();

    private JLabel numberJLabel = new JLabel();

    private JTextField numberJTextField = new JTextField();

    private JLabel nameJLabel = new JLabel();

    private JTextField nameJTextField = new JTextField();

    private JLabel levelJLabel = new JLabel();

    private JLabel departmentJLabel = new JLabel();

    public JComboBox levelJComboBox = new JComboBox();

    //begin CR5
    private JLabel workstateJLabel = new JLabel();

    private JLabel workstateJLabel1 = new JLabel();
    //end CR5

    /** 单位选择面版 */
    public SortingSelectedPanel departmentSelectedPanel;

    private JPanel jPanel3 = new JPanel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel4 = new JPanel();

    private JLabel productJLabel = new JLabel();

    private JTextField productJTextField = new JTextField();

    private JButton browseJButton = new JButton();
    //    CCBegin SS11
    private JButton clearJButton = new JButton();
//    CCEnd SS11
    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private FolderPanel folderPanel = new FolderPanel();

    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();

    private JLabel descriJLabel = new JLabel();

    private JScrollPane jScrollPane2 = new JScrollPane();

    private JTextArea descriJTextArea = new JTextArea();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    private JPanel buttonJPanel = new JPanel();

    private JButton saveJButton = new JButton();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    private JLabel levelDisplayJLabel = new JLabel();

    private JLabel stateDisplayJLabel = new JLabel();

    private JLabel departmentDisplayJLabel = new JLabel();

    /** 界面显示模式（更新模式）标记 */
    public final static int UPDATE_MODE = 0;

    /** 界面显示模式（创建模式）标记 */
    public final static int CREATE_MODE = 1;

    /** 界面显示模式（查看模式）标记 */
    public final static int VIEW_MODE = 2;

    private final static int OKOPTION = 3;
    private final static int SAVE = 4;
    private final static int SAVAAFTERCANEL = 5;
    /** 界面模式--查看 */
    private int mode = VIEW_MODE;
    /** 业务对象 */
    private TechnicsRouteListIfc myRouteList;

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 标记是否执行了保存操作 */
    protected boolean isSave = false;

    private JLabel jLabel1 = new JLabel();

    private JLabel versionJLabel = new JLabel();

    /** 缓存:产品标识 */
    private String productID = "";

    TextValidCheck textheck = new TextValidCheck("工艺路线表", 30);

    String routemanagermode = RemoteProperty.getProperty("routeManagerMode");
	//CCBegin SS3
    String comp ="";
  //CCEnd SS3
   //CCBegin SS13
    boolean flag=false;
    //CCEnd SS13
    /**
     * 构造函数
     * @roseuid 4031A737030E
     */
    public RouteListTaskJPanel()
    {
        try
        {
         	//CCBegin SS3
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
            //CCEnd SS3
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
    	
        partLinkJPanel = new RouteListPartLinkJPanel(this);
        this.setLayout(gridBagLayout5);
        this.setSize(new Dimension(500, 478));
        jPanel1.setLayout(borderLayout1);
        attriJPanel.setLayout(gridBagLayout3);
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setText("*编号");
        numberJLabel.setBounds(new Rectangle(12, 14, 41, 18));
        numberJTextField.setMaximumSize(new Dimension(2147483647, 22));
        numberJTextField.setBounds(new Rectangle(65, 13, 63, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setText("*名称");
        nameJLabel.setBounds(new Rectangle(15, 53, 41, 18));
        nameJTextField.setMaximumSize(new Dimension(2147483647, 22));
        nameJTextField.setBounds(new Rectangle(64, 50, 63, 22));
        //CCBegin SS4
		if (!comp.equals("zczx")) {
			levelJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			levelJLabel.setText("级别");
			levelJLabel.setBounds(new Rectangle(232, 14, 41, 18));
		}
        //CCEnd SS4
        departmentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        departmentJLabel.setText("*单位");
        //CCBegin SS4
		if (!comp.equals("zczx")) {
			levelJComboBox.setMaximumSize(new Dimension(50, 22));
			levelJComboBox.setMinimumSize(new Dimension(50, 22));
			levelJComboBox.setPreferredSize(new Dimension(50, 22));
			levelJComboBox.setBounds(new Rectangle(291, 10, 126, 22));
		}
        //CCEnd SS4
        //begin CR5
        workstateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workstateJLabel.setText("工作状态");
        workstateJLabel1.setText("sm");
        workstateJLabel1.setMaximumSize(new Dimension(200, 22));
        workstateJLabel1.setMinimumSize(new Dimension(200, 22));
        workstateJLabel1.setPreferredSize(new Dimension(200, 22));
        //end CR5

        departmentSelectedPanel = new SortingSelectedPanel("单位", "TechnicsRouteList", "routeListDepartment");
        departmentSelectedPanel.setMaximumSize(new Dimension(91, 22));
        departmentSelectedPanel.setMinimumSize(new Dimension(91, 22));
        departmentSelectedPanel.setPreferredSize(new Dimension(91, 22));
        departmentSelectedPanel.setButtonSize(91, 23);
        departmentSelectedPanel.setDialogTitle("选择单位");
        departmentSelectedPanel.setIsOnlyCC(true);
        departmentSelectedPanel.setIsSelectCC(true);
        jPanel3.setLayout(gridBagLayout1);
        jPanel4.setLayout(gridBagLayout2);
        productJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //CR10 begin
        if(routemanagermode.equals("parentRelative") || routemanagermode.equals("partRelative"))
        {
        	//CCBegin SS22
        	if(comp.equals("cd"))
        	{
        		productJLabel.setText("*用于产品");
        	}
        	else
        	//CCEnd SS22
            productJLabel.setText("用于产品");
        }else
        {
            productJLabel.setText("*用于产品");
        }
        //CR10 end
        productJTextField.setMaximumSize(new Dimension(2147483647, 22));
        productJTextField.setEditable(false);
        browseJButton.setMaximumSize(new Dimension(91, 23));
        browseJButton.setMinimumSize(new Dimension(91, 23));
        browseJButton.setPreferredSize(new Dimension(91, 23));
        browseJButton.setMnemonic('R');
        browseJButton.setText("搜索(R). . .");
        browseJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                browseJButton_actionPerformed(e);
            }
        });
        //        CCBegin SS11
        clearJButton.setMaximumSize(new Dimension(91, 23));
        clearJButton.setMinimumSize(new Dimension(91, 23));
        clearJButton.setPreferredSize(new Dimension(91, 23));
        clearJButton.setText("清除. . .");
        clearJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	productJTextField.setText("");
            	productID = "";
            	partLinkJPanel.setProductIfc(null);
            }
        });
//        CCEnd SS11
        descriJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriJLabel.setText("说明");
        partLinkJPanel.setBorder(BorderFactory.createEtchedBorder());
        partLinkJPanel.setMaximumSize(new Dimension(338, 32767));
        partLinkJPanel.setMinimumSize(new Dimension(338, 10));
        partLinkJPanel.setPreferredSize(new Dimension(338, 10));
        attriJPanel.setBorder(null);
        attriJPanel.setMaximumSize(new Dimension(338, 2147483647));
        attriJPanel.setPreferredSize(new Dimension(338, 233));
        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane1.setMaximumSize(new Dimension(338, 32767));
        jScrollPane1.setMinimumSize(new Dimension(338, 24));
        jScrollPane1.setPreferredSize(new Dimension(338, 253));
        jPanel1.setMaximumSize(new Dimension(338, 2147483647));
        jTabbedPane.setMaximumSize(new Dimension(343, 32767));
        buttonJPanel.setLayout(gridBagLayout4);
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setActionCommand("SAVEROUTELIST");
        saveJButton.setMnemonic('S');
        saveJButton.setText("保存(S)");
        saveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveJButton_actionPerformed(e);
            }
        });
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OKROUTELIST");
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
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        //CCBegin SS1
        stateJComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	stateJComboBox_actionPerformed(e);
            }
        });
        //CCEnd SS1
        buttonJPanel.setMaximumSize(new Dimension(211, 23));
        departmentDisplayJLabel.setMaximumSize(new Dimension(41, 22));
        departmentDisplayJLabel.setMinimumSize(new Dimension(41, 22));
        departmentDisplayJLabel.setPreferredSize(new Dimension(41, 22));
        departmentDisplayJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        levelDisplayJLabel.setMaximumSize(new Dimension(41, 22));
        levelDisplayJLabel.setMinimumSize(new Dimension(41, 22));
        levelDisplayJLabel.setPreferredSize(new Dimension(41, 22));
        levelDisplayJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //st
        stateDisplayJLabel.setMaximumSize(new Dimension(41, 22));
        stateDisplayJLabel.setMinimumSize(new Dimension(41, 22));
        stateDisplayJLabel.setPreferredSize(new Dimension(41, 22));
        stateDisplayJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //ed
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("版本");
        versionJLabel.setText("A.1");
        jLabel2.setText("*资料夹");
        locationJLabel.setMaximumSize(new Dimension(0, 22));
        locationJLabel.setMinimumSize(new Dimension(0, 22));
        locationJLabel.setPreferredSize(new Dimension(0, 22));
        lifeCycleInfo.getLifeCyclePanel().setBrowseButtonSize(new Dimension(83, 23));
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new Dimension(83, 23));
        lifeCycleInfo.setBsoName("consTechnicsRouteList");
        lifeCycleInfo.getProjectPanel().setButtonMnemonic('P');
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new Dimension(91, 23));
        stateLabel.setText("状态");
        buttonJPanel.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel4.add(browseJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 8, 2, 0), 0, 0));
        //        CCBegin SS11
        if(comp.equals("zczx")){
        	jPanel4.add(clearJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 8, 2, 0), 0, 0));
        }
//        CCEnd SS11
        if(routemanagermode.equals("parentRelative") || routemanagermode.equals("partRelative"))
        {
            jPanel4.add(productJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 4, 2, 0), 0, 0));
        }else
        {
            jPanel4.add(productJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
        }
        jPanel4.add(productJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 0), 0, 0));
        attriJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 10, 4, 8), 0, 10));
        //begin CR5
        attriJPanel.add(workstateJLabel, new GridBagConstraints(0, 4, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 15, 4, 10), 0, 0));
        attriJPanel.add(workstateJLabel1, new GridBagConstraints(1, 4, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 8, 4, 0), 0, 0));
        //end CR5

        attriJPanel.add(descriJLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 40, 0, 0), 0, 0));
        attriJPanel.add(jScrollPane2, new GridBagConstraints(1, 5, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 8, 10, 10), 0, 0));
        // jScrollPane2.setHorizontalScrollBarPolicy( jScrollPane2.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.getViewport().add(descriJTextArea, null);
        //  descriJTextArea.setColumns(128);
        descriJTextArea.setLineWrap(true);
        attriJPanel.add(folderPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 7, 4, 10), 0, 0));
        attriJPanel.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        attriJPanel.add(locationJLabel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 10), 0, 0));
        this.add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        folderPanel.removeLabel();
        folderPanel.setButtonMnemonic('O');
        folderPanel.setButtonSize(91, 23);
        jTabbedPane.add(jPanel1, "属性");
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jTabbedPane.add(partLinkJPanel, "零部件表");

        //        //Begin CR1
        //        class WorkThread extends Thread
        //        {
        //            public void run()
        //            {
        //                //20120113 xucy modify 查看艺准关联信息无需加进度条
        ////                ProgressDialog d = new ProgressDialog(getParentJFrame());
        ////                d.setMessage("正在处理数据，请稍候...");
        ////                d.startProcess();
        //                partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
        //                //d.stopProcess();
        //            }
        //        }
        //        jTabbedPane.addChangeListener(new ChangeListener()
        //        {
        //
        //            public void stateChanged(ChangeEvent cevent)
        //            {
        //                //CR9 Begin
        //                partLinkJPanel.setIschangFlag(true);
        //                //CR9 End
        //                if(jTabbedPane.getSelectedIndex() == 1 && mode == VIEW_MODE)
        //                {//CR7
        //
        //                    WorkThread t = new WorkThread();
        //                    t.start();
        //                }
        //            }
        //
        //        });
        //        //End CR1   

        jScrollPane1.getViewport().add(attriJPanel, null);

        jPanel3.add(numberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(numberJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJComboBox, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 18, 0, 0), 0, 0));
        jPanel3.add(levelDisplayJLabel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentDisplayJLabel, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentJLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 18, 0, 0), 0, 0));
        jPanel3.add(departmentSelectedPanel, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 0, 2, 0), 0, 0));
        jPanel3.add(versionJLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 2, 0), 0, 0));
        jPanel3.add(stateJComboBox, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateDisplayJLabel, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        
        attriJPanel.add(jPanel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 34, 0, 10), 0, 0));
        attriJPanel.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 10, 4, 10), 0, 0));
        this.add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));

        departmentSelectedPanel.setVisible(false);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        localize();

    }

    /**
     * 本地化
     */
    private void localize()
    {
        RouteListLevelType[] levelTypes = RouteListLevelType.getRouteListLevelTypeSet();
        for(int i = 0;i < levelTypes.length;i++)
        {
            levelJComboBox.addItem(levelTypes[i].getDisplay());
        }
        levelJComboBox.setSelectedItem(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        levelJComboBox.addActionListener(new RouteListTaskJPanel_levelJComboBox_actionAdapter(this));
        initStateJComboBox();
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
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
            	 //CCBegin SS19
            	String stateStr = (iterator.next()).toString();
                stateJComboBox.addItem(stateStr);
               
                if(comp.equals("ct")){
                	if(stateStr.equals("艺准")){
                		stateJComboBox.setSelectedItem(stateStr)	;
                	}
                }
           
            }
           
            if(!comp.equals("ct")){
            	   stateJComboBox.setSelectedIndex(0);
            }
            //CCEnd SS19
         
        }
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
     * 设置业务对象
     * @param routelist 工艺路线表对象
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist)
    {
        myRouteList = routelist;
        //CCBegin SS15
        partLinkJPanel.routeListInfo=routelist;
        //CCEnd SS15
    }

    /**
     * 获得业务对象
     * @return 工艺路线表对象
     */
    public TechnicsRouteListIfc getTechnicsRouteList()
    {
        return myRouteList;
    }

    /**
     * 设置默认显示新建指定产品的路线表界面(暂时不用本方法)
     * @param product 指定产品
     */
    public void setDefaultProductView(QMPartMasterIfc product)
    {
        this.setViewMode(CREATE_MODE);
        productJTextField.setText(getIdentity(product));
        productID = product.getBsoID();
    }

    /**
     * 设置界面模式（创建、更新或查看）。
     * @param aMode 新界面模式
     */
    public void setViewMode(int aMode)
    {
        if((aMode == UPDATE_MODE) || (aMode == CREATE_MODE) || (aMode == VIEW_MODE))
        {
            mode = aMode;
        }

        switch(aMode)
        {

        case CREATE_MODE: //创建模式
        {
            this.setCreateModel();
            break;
        }

        case UPDATE_MODE: //更新模式
        {
            this.setUpdateModel();
            break;
        }

        case VIEW_MODE: //查看模式
        {
            this.setViewDisplayModel();
            break;
        }
        }
    }

    /**
     * 获得当前界面模式
     * @return 当前界面模式
     */
    public int getViewMode()
    {
        return mode;
    }

    /**
     * 设置界面为新建模式
     */
    private void setCreateModel()
    {
        //CCBegin SS13
    	if(comp.equals("ct")){
    		SelectLevelRouteJDialog p=new SelectLevelRouteJDialog(this
					.getParentJFrame(), this);
    		p.setVisible(true);		  
			partLinkJPanel.routeListInfo=null;
   		    partLinkJPanel.setMultilistInforms(); 
   		   
    	}   
    	//CCEnd SS13
        // CR13 Begin
        //((CappRouteListManageJFrame)this.getParentJFrame()).getTreePanel().clearSelection();
        // CR13 end
        isSave = false;  
        // CR12
   
        departmentSelectedPanel.setCoding(null);
  
        //begin 20120109 xucy add  创建模式下，允许编辑零部件表   
        //jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.setEditModel();
        partLinkJPanel.setViewMode(CREATE_MODE);
        //end 20120109 xucy add  创建模式下，允许编辑零部件表
        jTabbedPane.setSelectedIndex(0);
        levelDisplayJLabel.setVisible(false);
        stateDisplayJLabel.setVisible(false);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelJComboBox.setVisible(false);
        }else{ 
        	levelJComboBox.setVisible(true);          
        }
        //CCEnd SS4
//        System.out.println("pppppppppppp=====" + RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
       //CCBegin SS13
        if(!comp.equals("ct")){
            //CCEnd SS13
         levelJComboBox.setSelectedItem(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        }


             //20120113 xucy add
        partLinkJPanel.setRouteLevel(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        stateJComboBox.setVisible(true);
        this.setTextFieldVisible(numberJTextField);
        //CCBegin SS6
        if(comp.equals("zczx")){
        	
        	java.util.Date date = new java.util.Date();
        	String year = Integer.toString(date.getYear()+1900);
        	 Class[] paraClass={String.class,int.class};
        	 Component com=null;
        	if(stateJComboBox.getSelectedItem().toString().equals("艺准")){
        		Object[] aa={"艺准-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);		
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("试制")){
        		Object[] aa={"艺试准-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("前准")){
        		Object[] aa={"前准-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("临准")){
        		Object[] aa={"临准-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("艺毕")){
        		Object[] aa={"艺毕-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
        	if(stateJComboBox.getSelectedItem().toString().equals("艺废")){
        		Object[] aa={"艺废-ZCZX-"+year+"-",3};
        		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
        		numberJTextField.setText(num);
        	}
     
        }
        //CCBegin SS22
        else if(comp.equals("cd"))
        {
        	numberJTextField.setText("自动编号");
        }
        //CCEnd SS22
        else{
           numberJTextField.setText("");
        }
        //CCEnd SS6
        this.setTextFieldVisible(nameJTextField);
        nameJTextField.setText("");
        this.setTextFieldVisible(productJTextField); 
        productJTextField.setText("");
        productJTextField.setEditable(false);
        descriJTextArea.setEditable(true);
      //CCBegin by wanghonglian 2008-06-04
        //更改说明对话框中的内容显示
        //descriJTextArea.setText("");
        //CCBegin SS7
        /*if(stateJComboBox.getSelectedItem().toString().equals("艺准")){
          descriJTextArea.setText(
              "根据：PDM部件更改说明单     及本艺准进行生产准备。\n说明：路线代码\n发往单位：生产部、采购部、质保部、控制部、管理部、技术部（装配）"); //modify for cq by liunan 2007.08.08
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺试准")){
          descriJTextArea.setText(
              "根据：PDM部件更改说明单     及本艺试准进行试制生产准备。\n说明：路线代码\n发往单位：生产部、采购部、质保部、控制部、管理部、技术部（装配）");
        }else{
          descriJTextArea.setText(
              "根据：PDM部件更改说明单     及本艺准进行生产准备。\n说明：路线代码\n发往单位：生产部、采购部、质保部、控制部、管理部、技术部（装配）");
        }*/
        //CCBegin SS8 SS24
        if(stateJComboBox.getSelectedItem().toString().equals("前准")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单       及本前准进行生产准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("临准")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单       及本临准进行生产准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺准")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单       及本艺准进行生产准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("试制")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单                    及本艺试准进行试制准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺毕")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单        及艺准     所列内容生产准备完毕,可投入生产。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺废")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单             废弃上述零部件。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }else{
          descriJTextArea.setText("");
        }
        //CCEnd SS8 SS24
        //CCEnd SS7
        //CCEnd by wanghonglian 2008-06-04
        //CCBeign SS5
        if(comp.equals("zczx")){
        	//CCBegin SS9
//        	descriJTextArea.setText("根据解放公司规划发展部一级路线文件《        》编制此二级路线。\n根据：PDM部件更改说明单     及本艺准进行生产准备。\n说明：                   \n路线代码：\n发往单位：规划发展部、生产计划部、采购部、质量保证部、制造部、管理部");
        	//CCBegin SS10
        	//descriJTextArea.setText("根据解放公司规划发展部一级路线文件《        》编制此二级路线。\n根据：PDM部件更改说明单     及本艺准进行生产准备。\n说明：                   \n路线代码：\n发往单位：技术发展室、资源管理室、生产管理室、质量保证室、制造技术室、轴齿一车间、轴齿二车间、热处理车间、试制车间");
        	descriJTextArea.setText("根据解放公司规划发展部一级路线文件《        》编制此二级路线。\n根据：PDM部件更改说明单     及本艺准进行生产准备。\n说明：                   \n路线代码：\n发往单位：技术发展室、资源管理室、生产计划室、质量保证室、制造技术室、轴齿一车间、轴齿二车间、热处理车间");
//        CCEnd SS10
//        CCEnd SS9
        }
        //CCEnd SS5
        //CCBegin SS12 SS20
        if(comp.equals("ct")){
        	if(flag){
        		descriJTextArea.setText("根据装配清单  和一级路线  及本艺准进行生产准备\n发往单位：专、集（专）、吉城机械、亚（大）、灯泡电线、新泰非标");
        	}else{
        		descriJTextArea.setText("根据：PDM部件更改说明单     及本艺准进行生产准备\n发往单位：专、集（专）、吉城机械、亚（大）、灯泡电线、新泰非标");	
        	}
        }
       
        //CCend SS12   SS20
        //CCBegin SS22
        if(comp.equals("cd"))
        {
        	descriJTextArea.setText("根据：技术中心PDM部件更改说明单/技术问题通知单/产品采用及更改通知书及本艺准进行生产准备；\n说明：\n发往单位：");	
        }
        //CCEnd SS22
        browseJButton.setVisible(true);
//      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(true);
        }
//        CCEnd SS11
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(false);
        workstateJLabel1.setText(null);
        //end CR5
        //begin 20120110 xucy add 创建模式下清空关联数据
        this.partLinkJPanel.clearHashMap();
        this.partLinkJPanel.cleanMutlist();
        //end 20120110 xucy add
        TechnicsRouteListInfo routeList = new TechnicsRouteListInfo();
        this.setTechnicsRouteList(routeList);
        //20120105 xucy  add
        partLinkJPanel.setProductIfc(null);
        this.productID = "";
        //20120106 xucy add
        partLinkJPanel.getBranchMap().clear();
        versionJLabel.setText("A.1");
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
        lifeCycleInfo.getProjectPanel().setObject(null);
        //CCBegin SS16
        if(comp.equals("ct")){
        	String ctRoutelistLevel = levelDisplayJLabel.getText();	
        	if(this.flag){
        		 lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("长特二级路线工艺准备通知书生命周期");
        	}else{
        		 lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("长特一级路线工艺准备通知书生命周期");
        	}
        	 //CCBegin SS21
        }else if(comp.equals("zczx")){
        	lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("轴齿中心工艺准备通知书生命周期");//CCEnd SS21
        }
        //CCBegin SS22
        else if(comp.equals("cd"))
        {
        	lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("成都工艺准备通知书生命周期");
        }
        //CCEnd SS22
        else{
        
        	//SS2 BEGIN
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("变速箱艺准通知书");
         	//SS2 END
        }
        //CCEnd SS16
        
     
        //lifeCycleInfo.setObject((LifeCycleManagedIfc)this.getTechnicsRouteList());
        locationJLabel.setVisible(false);
        folderPanel.setVisible(true);
        folderPanel.setSelectModel(true);
        folderPanel.setButtonSize(91, 23);
        //CCBegin SS14
       
        if(flag){
			 departmentSelectedPanel.setVisible(true);
	         departmentJLabel.setVisible(true);
	         departmentSelectedPanel.setViewTextField("");

		 } 
        //CCEnd SS14
        try
        {
            folderPanel.setLabelText(this.getPersionalFolder());
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * 设置界面为更新模式
     */
    private void setUpdateModel()
    {
        isSave = false;
        //jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.clearHashMap();
        partLinkJPanel.setEditModel();
        partLinkJPanel.setViewMode(UPDATE_MODE);
        //CCBegin SS15
        partLinkJPanel.setMultilistInforms(); 
        //CCEnd SS15
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        productJTextField.setEditable(false);
        levelJComboBox.setVisible(false);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelDisplayJLabel.setVisible(false);
        }else{ 
        	levelDisplayJLabel.setVisible(true);
        }
        //CCEnd SS4
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //ed
        descriJTextArea.setEditable(true);
        browseJButton.setVisible(false);
        //      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(false);
        }
//      CCEnd SS11
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        setButtonWhenSave(true);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try
        {
            numberJTextField.setText(this.getTechnicsRouteList().getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList().getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListLevel());
            stateDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListState());
            versionJLabel.setText(this.getTechnicsRouteList().getVersionValue());
            // partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());//CR1
            //this.partLinkJPanel.getAddedPartLinks().clear();//Begin CR2
            //this.partLinkJPanel.getDeletedPartLinks().clear();//End CR2

            //CR9 Begin
            this.partLinkJPanel.cleanMutlist();
            String productID = this.getTechnicsRouteList().getProductMasterID();
            if(productID == null || productID.equals(""))
            {
                productJTextField.setText("");
                partLinkJPanel.setProductIfc(null);
            }else
            {
                this.productID = productID;
                QMPartMasterInfo partinfo = null;
                partinfo = (QMPartMasterInfo)refreshInfo(productID);
                productJTextField.setText(getIdentity(partinfo));
                //20120105 xucy add
                //QMPartIfc product = ()refreshInfo(getTechnicsRouteList().getProductMasterID());
                partLinkJPanel.setProductIfc(partinfo);
            }
            this.partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
            //CR9 End
            //判断是否显示单位
            String department = ((TechnicsRouteListInfo)this.getTechnicsRouteList()).getDepartmentName();
            if(department != null && !department.equals(""))
            {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            }else
            {
                departmentJLabel.setVisible(false);
                departmentDisplayJLabel.setVisible(false);
            }
            //folderPanel.setSelectModel(false);
            //folderPanel.getFolderPanelLabel();
            //folderPanel.setLabelText(this.getTechnicsRouteList().getLocation());
            folderPanel.setVisible(false);
            locationJLabel.setText(this.getTechnicsRouteList().getLocation());
            locationJLabel.setVisible(true);
            //lifeCycleInfo.setObject((LifeCycleManagedIfc)getTechnicsRouteList());

            // modify by guoxl on 20080214(更新路线表时生命周期和项目组的信息不显示)
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            lifeCycleInfo.getProjectPanel().setObject((LifeCycleManagedIfc)getTechnicsRouteList());

            //modify by guoxl end

            //lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
           

            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
			workstateJLabel1.setText(str);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * 设置界面为更新模式
     */
    private void setListUpdateModel()
    {
        isSave = false;
        //jTabbedPane.setEnabledAt(1, true);
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        productJTextField.setEditable(false);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelDisplayJLabel.setVisible(false);
        }else{ 
        	levelDisplayJLabel.setVisible(true);
        }
        //CCEnd SS4
        //ed
        descriJTextArea.setEditable(true);
        browseJButton.setVisible(false);
        //      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(false);
        }
//      CCEnd SS11
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        setButtonWhenSave(true);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try
        {
            numberJTextField.setText(this.getTechnicsRouteList().getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList().getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListLevel());
            stateDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListState());
            versionJLabel.setText(this.getTechnicsRouteList().getVersionValue());
            // partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());//CR1
            //this.partLinkJPanel.getAddedPartLinks().clear();//Begin CR2
            //this.partLinkJPanel.getDeletedPartLinks().clear();//End CR2
            //CR9 Begin
            //this.partLinkJPanel.cleanMutlist();
            //this.partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
            //CR9 End
            //判断是否显示单位
            String department = ((TechnicsRouteListInfo)this.getTechnicsRouteList()).getDepartmentName();
            if(department != null && !department.equals(""))
            {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            }else
            {
                departmentJLabel.setVisible(false);
                departmentDisplayJLabel.setVisible(false);
            }
            //folderPanel.setSelectModel(false);
            //folderPanel.getFolderPanelLabel();
            //folderPanel.setLabelText(this.getTechnicsRouteList().getLocation());
            folderPanel.setVisible(false);
            locationJLabel.setText(this.getTechnicsRouteList().getLocation());
            locationJLabel.setVisible(true);
            //lifeCycleInfo.setObject((LifeCycleManagedIfc)getTechnicsRouteList());

            // modify by guoxl on 20080214(更新路线表时生命周期和项目组的信息不显示)
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            lifeCycleInfo.getProjectPanel().setObject((LifeCycleManagedIfc)getTechnicsRouteList());
            //modify by guoxl end

            //lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            String productID = this.getTechnicsRouteList().getProductMasterID();
            if(productID == null || productID.equals(""))
            {
                productJTextField.setText("");
                //partLinkJPanel.setProductIfc(null);
            }else
            {
                this.productID = productID;
                QMPartMasterInfo partinfo = null;
                partinfo = (QMPartMasterInfo)refreshInfo(productID);
                productJTextField.setText(getIdentity(partinfo));
                //20120105 xucy add
                //QMPartIfc product = ()refreshInfo(getTechnicsRouteList().getProductMasterID());
                //partLinkJPanel.setProductIfc(partinfo);
            }

            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
			workstateJLabel1.setText(str);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * 设置界面为查看模式
     */
    public void setViewDisplayModel()
    {
        jTabbedPane.setSelectedIndex(0);//CR1
        //jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.setViewModel();
        //CCBegin SS15
        partLinkJPanel.setMultilistInforms(); 
        //CCEnd SS15
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //CCBegin SS4
        if(comp.equals("zczx")){
        	levelDisplayJLabel.setVisible(false);
        }else{ 
        	levelDisplayJLabel.setVisible(true);
        }
        //CCEnd SS4
        //ed
        browseJButton.setVisible(false);
        //      CCBegin SS11
        if(comp.equals("zczx")){
        	clearJButton.setVisible(false);
        }
//      CCEnd SS11
        descriJTextArea.setEditable(false);
        saveJButton.setVisible(false);
        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try
        {
            numberJTextField.setText(this.getTechnicsRouteList().getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList().getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListLevel());
            //st skybird 2005.2.24
            stateDisplayJLabel.setText(this.getTechnicsRouteList().getRouteListState());
            //ed
            versionJLabel.setText(this.getTechnicsRouteList().getVersionValue());
            descriJTextArea.setText(this.getTechnicsRouteList().getRouteListDescription());
            String productID = this.getTechnicsRouteList().getProductMasterID();
            if(productID == null || productID.equals(""))
            {
                productJTextField.setText("");
                partLinkJPanel.setProductIfc(null);
            }else
            {
                this.productID = productID;
                QMPartMasterInfo partinfo = null;
                partinfo = (QMPartMasterInfo)refreshInfo(productID);
                productJTextField.setText(getIdentity(partinfo));
                //20120105 xucy add
                //QMPartIfc product = partLinkJPanel.filterPart(getTechnicsRouteList().getProductMasterID());
                partLinkJPanel.setProductIfc(partinfo);
            }
            partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());
            //判断是否显示单位
            String department = ((TechnicsRouteListInfo)this.getTechnicsRouteList()).getDepartmentName();
            if(department != null && !department.equals(""))
            {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            }else
            {
                departmentJLabel.setVisible(false);
                departmentDisplayJLabel.setVisible(false);
            }
            //folderPanel.setSelectModel(false);
            //folderPanel.getFolderPanelLabel();
            //folderPanel.setLabelText(this.getTechnicsRouteList().getLocation());
            folderPanel.setVisible(false);
            locationJLabel.setText(this.getTechnicsRouteList().getLocation());
            locationJLabel.setVisible(true);
            //lifeCycleInfo.setObject((LifeCycleManagedIfc)getTechnicsRouteList());
            if(verbose)
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + this.getTechnicsRouteList().getProjectId());
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            //modify by guoxl on 20080214(当对路线表重新指定项目组时界面不能刷新更改后的项目组信息，特别项目组指定为空时)
            //if (getTechnicsRouteList().getProjectId() != null)
            lifeCycleInfo.getProjectPanel().setObject((LifeCycleManagedIfc)getTechnicsRouteList());
            //else
            //lifeCycleInfo.getProjectPanel().setObject(null);
            //modify by guoxl end
           
            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            try
            {
                String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
                workstateJLabel1.setText(str);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            //end CR5
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            return;
        }

        if(!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * 获得当前用户的个人资料夹位置
     * @return 当前用户的个人资料夹位置
     * @throws QMRemoteException
     */
    public String getPersionalFolder()
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() begin...");
        Class[] c = {};
        Object[] objs = {};
        UserInfo user=null;
		try {
			user = (UserInfo)RequestHelper.request("SessionService", "getCurUserInfo", c, objs);
		
        Class[] c1 = {UserInfo.class};
        Object[] objs1 = {user};
        SubFolderInfo folder = (SubFolderInfo)RequestHelper.request("FolderService", "getPersonalFolder", c1, objs1);
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() end...return: " + folder.getPath());
        return folder.getPath();
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		
		return null;
    }

    /**
     * 设置把指定的单行文本域显示为标签
     * @param textfield 指定的单行文本域
     */
    private void setTextFieldToLabel(JTextField textfield)
    {
        textfield.setBorder(null);
        textfield.setBackground(SystemColor.control);
        textfield.setEditable(false);
    }

    /**
     * 设置把指定的单行文本域显示为可编辑
     * @param textfield 指定的单行文本域
     */
    private void setTextFieldVisible(JTextField textfield)
    {
        textfield.setBorder(javax.swing.plaf.basic.BasicBorders.getTextFieldBorder());
        textfield.setBackground(Color.white);
        textfield.setEditable(true);
    }

    /**
     * 级别默认为“一级”,此时单位从代码管理器中读取. 级别选择为“二级”时，单位标签和下拉列表框显示，否则单位标签和下拉列表不可见
     * @param e ItemEvent
     */
    void levelJComboBox_itemStateChanged(ItemEvent e)
    {

    }

    /**
     * 检验必填区域是否已有有效值
     * @return 如果必填区域已有有效值，则返回为真
     */
    private boolean checkRequiredFields()
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() begin...");
        boolean isOK = false;
        String message = "fell through ";
        String title = "";
        System.out.println("check comp=="+comp);
        try
        {

            if((getViewMode() == CREATE_MODE))
            {
                //检验编号是否为空
                if(numberJTextField.getText().trim().length() == 0)
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_NUMBER_ENTERED, null);
                    this.numberJTextField.grabFocus();
                    isOK = false;
                }
                //检验名称是否为空
                else if(nameJTextField.getText().trim().length() == 0)
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_NAME_ENTERED, null);
                    this.nameJTextField.grabFocus();
                    isOK = false;
                }
                //检验资料夹是否为空
                else if(checkFolderLocation() == null)
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_LOCATION_ENTERED, null);
                    this.folderPanel.grabFocus();
                    isOK = false;
                }
                //CR10 begin
                //检验"用于产品"是否为空
                //CCBegin SS22
                //else if(productJTextField.getText().trim().length() == 0 && (routemanagermode.equals("productRelative") || routemanagermode.equals("productAndparentRelative")))
                else if(productJTextField.getText().trim().length() == 0 && (routemanagermode.equals("productRelative") || routemanagermode.equals("productAndparentRelative")||comp.equals("cd")))
                //CCEnd SS22
                {

                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_PRODUCT_ENTERED, null);
                    this.productJTextField.grabFocus();
                    isOK = false;

                    //CR10 end
                    //CR12 
                }else if(departmentSelectedPanel.getCoding() == null && this.levelJComboBox.getSelectedItem().toString().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
                {
                    message = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NO_DEPARTMENT_ENTER, null);
                    departmentSelectedPanel.grabFocus();
                    isOK = false;
                }else
                {
                    isOK = true;
                }
            }else
            {
                isOK = true;
            }
            if(!isOK)
            {
                //显示信息：缺少必需的字段
                title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception qre)
        {
            //显示信息：所指定的资料夹不是个人资料夹
            String message1 = qre.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message1);
            this.folderPanel.grabFocus();
        }
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() end...return: " + isOK);
        return isOK;

    }

    /**
     * 检查文本框的数值有效性
     * @return boolean
     */
    private boolean check()
    {
        if(numberJTextField.getText().indexOf("*") != -1 || numberJTextField.getText().indexOf("%") != -1 || numberJTextField.getText().indexOf("?") != -1)
        {
            String message = "编号" + "含有非法字符eg:*%?";
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.numberJTextField.grabFocus();
            return false;
        }

        if(nameJTextField.getText().indexOf("*") != -1 || nameJTextField.getText().indexOf("%") != -1 || nameJTextField.getText().indexOf("?") != -1)
        {
            String message = "名称" + "含有非法字符eg:*%?";
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.nameJTextField.grabFocus();
            return false;
        }
        if(numberJTextField.getText().trim().getBytes().length > 30)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, "52", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.numberJTextField.grabFocus();
            return false;
        }
        if(this.nameJTextField.getText().trim().getBytes().length > 200)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, "53", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.nameJTextField.grabFocus();
            return false;
        }
        if(this.descriJTextArea.getText().trim().getBytes().length > 2000)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE, "54", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            this.descriJTextArea.grabFocus();
            return false;
        }
        return true;
    }

    private boolean checkText()
    {
        try
        {
//CCBegin SS17
            textheck.setMax(100); //CR3
//CCEnd SS17
            textheck.check(numberJTextField, true);

        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            this.numberJTextField.grabFocus();
            return false;

        }
        try
        {
            textheck.setMax(200);
            textheck.check(nameJTextField, true);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            this.nameJTextField.grabFocus();
            return false;

        }

        try
        {
            textheck.setMax(2000);
            textheck.check(descriJTextArea, false);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
            this.descriJTextArea.grabFocus();
            return false;

        }

        return true;
    }

    /**
     * 检验是否已指定资料夹
     * @return 如果已指定资料夹路径，则返回资料夹。
     * @throws QMException 
     * @throws QMRemoteException
     */
    private SubFolderInfo checkFolderLocation() throws QMException
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() begin...");
        String location = "";
        SubFolderInfo folderInfo = null;
        //获得资料夹路径
        location = folderPanel.getFolderLocation();

        if(location != null && location.length() != 0)
        {
            //调用资料夹服务，根据获得的资料夹路径获得资料夹
            Class[] paraClass = {String.class};
            Object[] objs = {location};
            try
            {
                folderInfo = (SubFolderInfo)RequestHelper.request("FolderService", "getFolder", paraClass, objs);
            }catch(Exception ex)
            {
                String message = ex.getMessage();
                DialogFactory.showInformDialog(this.getParentJFrame(), message);
            }

        }

        if(folderInfo != null)
        {
            //            //调用资料夹服务，判断指定的文件夹是否是个人文件夹
            //            Class[] paraClass2 = {FolderIfc.class};
            //            Object[] objs2 = {folderInfo};
            Boolean flag1 = null;
            try
            {
                //flag1 = (Boolean)RequestHelper.request("FolderService", "isPersonalFolder", paraClass2, objs2);
                flag1 = folderInfo.isPersonalFolder();//CR6
            }catch(Exception ex)
            {
                //String title = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.LOCATION_NOT_VALID, null);
                String message =ex.getMessage();
                DialogFactory.showInformDialog(this.getParentJFrame(), message);
                this.folderPanel.grabFocus();

            }

            boolean flag = false;
            if(flag1 != null)
            {
                flag = flag1.booleanValue();
            }

            if(!flag)
            {
                //抛出异常信息：所指定的资料夹不是个人文件夹
                throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.LOCATION_NOT_PERSONAL_CABINET, null));
            }
        }

        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() end...return : " + folderInfo);
        return folderInfo;

    }

    /**
     * 执行保存操作
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        isSave = true;
        commond = e.getActionCommand();
        //  processSaveCommond();
        WKThread work = new WKThread(SAVE);
        work.start();

    }

    private String commond = "";

    private JLabel jLabel2 = new JLabel();

    private JLabel locationJLabel = new JLabel();

    JLabel stateLabel = new JLabel();

    JComboBox stateJComboBox = new JComboBox();

    /**
     * 系统根据业务规则PHOS-CAPP-BR201检查要求非空的属性是否为空(E1)， 根据业务规则PHOS-CAPP-BR202检查工艺路线表编号是否唯一(E2)， 如果当前为创建模式,则系统创建此新工艺路线表，把新创建的工艺路线表信息添加到路线表管理器的路线表列表中， 把创建界面刷新为更新界面(如果选择了"确定",则刷新为查看模式).
     * 如果当前为更新模式,则更新此路线表,刷新树节点,将界面刷新为查看模式.
     */
    private void processSaveCommond()
    {
        ProgressDialog progressDialog = null;
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        boolean flag;

        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkRequiredFields();
        //检查数据的有效性
        //  flag = this.check();
        flag = this.checkText();
        if(!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        if(!flag)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //20120113 xucy add
        if(!partLinkJPanel.checkLinkAttrs())
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        ArrayList list = partLinkJPanel.getSaveRouteMap();
        //检查路线单位是否合法
        if(!checkRouteDepartment(list))
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //显示保存进度
        //        ProgressService.setProgressText(QMMessage.getLocalizedMessage(RESOURCE,
        //                CappRouteRB.SAVING, null));
        //        ProgressService.showProgress();
        //（问题一）zz 20060925 换乘新版进度条
        progressDialog = new ProgressDialog(getParentJFrame());
        progressDialog.startProcess();

        try
        {
            /*
             * add by guoxl on 2008.4.3(保存路线表时提示“操作正在进行中，请稍后重试”的异常信息， 原因是 在保存时没刷新，导致时间戳不一致(四环车厢厂实施问题))
             */
            if(this.getViewMode() == UPDATE_MODE)
            {
                try
                {
                    this.setTechnicsRouteList((TechnicsRouteListIfc)(refreshInfo(getTechnicsRouteList().getBsoID())));

                }catch(Exception ex)
                {

                    String message = ex.getMessage();
                    DialogFactory.showWarningDialog(this.getParentJFrame(), message);
                    CappRouteListManageJFrame f = (CappRouteListManageJFrame)this.getParentJFrame();
                    f.getTreePanel().removeNode(new RouteListTreeObject(getTechnicsRouteList()));
                    setCursor(Cursor.getDefaultCursor());
                    setButtonWhenSave(true);
                    this.setVisible(false);
                    return;
                }
            }
            //提交所有属性
            this.commitAllAttributes();         
            if(this.partLinkJPanel != null)
                getTechnicsRouteList().setPartIndex(partLinkJPanel.getPartIndex());
            if(verbose)
                System.out.println("保存前的顺序" + getTechnicsRouteList().getPartIndex());
         
            if(this.getViewMode() == CREATE_MODE)
            {
            	//CCBegin SS23
            	String userdesc = getUserFromCompany();
            	if(userdesc.equals("cd")){
            		boolean isSameNameList = searchSameNameList();
            		if(isSameNameList){
            			DialogFactory.showWarningDialog(this.getParentJFrame(), "系统中有相同名称的路线表，请修改名称后再保存！");
            			progressDialog.stopProcess();
            			setCursor(Cursor.getDefaultCursor());
                        setButtonWhenSave(true);
            			return;
            		}
            	}
            	//CCEnd SS23
                //调用服务，保存工艺路线表
                Class[] paraClass = {TechnicsRouteListIfc.class};
                Object[] obj = {this.getTechnicsRouteList()};
                // （问题二）防止下面重设树焦点时再次出发保存提示 zz 20061106
                isSave = true;
               
                //20120109 xucy add 保存艺准零件关联
                Class[] c = {TechnicsRouteListIfc.class, ArrayList.class};
                Object[] objs = {this.getTechnicsRouteList(), list};
                Object[] objaa = (Object[])RequestHelper.request("consTechnicsRouteService", "createLinksAndRoutes", c, objs);
                TechnicsRouteListIfc returnRouteList = (TechnicsRouteListIfc)objaa[0];
                //20120112 xucy add
                partLinkJPanel.clearPartLinks();
                //把新创建的工艺路线表信息添加到路线表管理器的路线表列表中
                RouteTreePanel treePanel = ((CappRouteListManageJFrame)this.getParentJFrame()).getTreePanel();
                RouteListTreeObject newObj = new RouteListTreeObject(returnRouteList);
                treePanel.addNode(newObj);
                ((CappRouteListManageJFrame)getParentJFrame()).isViewRouteList = false;
                treePanel.setNodeSelected(newObj);
                ((CappRouteListManageJFrame)getParentJFrame()).isViewRouteList = true;
                //把创建界面刷新为更新界面
                //jTabbedPane.setEnabledAt(1, true);
                this.setTechnicsRouteList(returnRouteList);
                //20120113 xucy add 设置表格路线信息和关联ID
                partLinkJPanel.setMultiListAttrs((ArrayList)objaa[1]);
                //Begin CR8
                if(commond.equals("SAVEROUTELIST"))
                {
                    partLinkJPanel.setEditModel();
                    partLinkJPanel.setViewMode(UPDATE_MODE);
                    mode = UPDATE_MODE;
                    //this.setViewMode(mode);
                    
                    //设置艺准为更新模式 20120405
                    setListUpdateModel();
                }else if(commond.equals("OKROUTELIST"))
                {
                    //将界面刷新为查看模式
                    mode = VIEW_MODE;
                    this.setViewMode(mode);
                    //partLinkJPanel.setViewModel();
                    descriJTextArea.setEditable(false);
                    saveJButton.setVisible(false);
                    okJButton.setVisible(false);
                    cancelJButton.setVisible(false);

                }else
                {
                    mode = VIEW_MODE;
                    this.setViewMode(mode);
                    //partLinkJPanel.setViewModel();
                }
                //End CR8
               

            }else if(this.getViewMode() == UPDATE_MODE)
            {
                //begin CR11
                //保存路线表与零部件的关联及其保存路线对象
                Class[] c = {HashMap.class, TechnicsRouteListIfc.class, ArrayList.class};
                Object[] objs = {partLinkJPanel.getDeletedPartLinks(), this.getTechnicsRouteList(), list};
                if(verbose)
                    System.out.println("删除集合 ==" + partLinkJPanel.getDeletedPartLinks());
                if(verbose)
                    System.out.println("添加集合 ==" + partLinkJPanel.getAddedPartLinks());
                //20120116 xucy add 增加返回值
                Object[] obj = (Object[])RequestHelper.request("consTechnicsRouteService", "updateLinksAndRoutes", c, objs);
                //end CR11
                //   isSave = true;//CR4
                partLinkJPanel.clearPartLinks();
                //更新零部件的父件编号 added by skybird 2005.3.4
                //c = new Class[]{Collection.class,TechnicsRouteListIfc.class};
                //                Class[] c1 = {String.class};
                //                String theBeforedBsoid = this.getTechnicsRouteList().getBsoID();
                //                Object[] obj1 = {theBeforedBsoid};
                //                if(verbose)
                //                    System.out.println("原有的路线表id" + theBeforedBsoid);
                TechnicsRouteListIfc technicsRouteIfc = (TechnicsRouteListIfc)obj[0];
                //theBeforedBsoid = technicsRouteIfc.getBsoID();
                if(verbose)
                    System.out.println("更新后的partIndexs" + technicsRouteIfc.getPartIndex());
                this.setTechnicsRouteList(technicsRouteIfc);
                //20120113 xucy add 保存后需要重新设置界面信息，以便继续更新时用
                System.out.println("测试1===="+((ArrayList)obj[1]).size());
                partLinkJPanel.setMultiListAttrs((ArrayList)obj[1]);
                //刷新树节点
                ((CappRouteListManageJFrame)this.getParentJFrame()).getTreePanel().updateNode(new RouteListTreeObject(technicsRouteIfc));
                if(commond.equals("OKROUTELIST"))
                {
                    //将界面刷新为查看模式
                    mode = VIEW_MODE;
                    this.setViewMode(mode);
                    partLinkJPanel.setViewModel();
                    descriJTextArea.setEditable(false);
                    saveJButton.setVisible(false);
                    okJButton.setVisible(false);
                    cancelJButton.setVisible(false);
                }
            }
        }catch(Exception ex)
        {
            progressDialog.stopProcess();
            ex.printStackTrace();
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this.getParentJFrame(), message);
            isSave = false;
            //mode = CREATE_MODE;
            setCursor(Cursor.getDefaultCursor());
            //return;
        }
        //ProgressService.hideProgress();
        progressDialog.stopProcess();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
    }

    //CCBegin SS23
    /**
     * 查找系统中是否有相同名称的路线表
     * @return
     * @throws QMException 
     */
    private boolean searchSameNameList() throws QMException {
    	//调用服务，查找系统中是否有相同名称的路线表
        Class[] paraClass = {TechnicsRouteListIfc.class};
        Object[] obj = {this.getTechnicsRouteList()};
        Class[] c = {TechnicsRouteListIfc.class};
        Object[] objs = {this.getTechnicsRouteList()};
        boolean objaa = (Boolean)RequestHelper.request("consTechnicsRouteService", "searchSameNameList", c, objs);
		return objaa;
	}
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	RequestServer server = RequestServerFactory.getRequestServer();;
    	String returnStr = "";
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         System.out.println("用户组===="+returnStr);
         return returnStr;
    }
    //CCEnd SS23

	/**
     * 检查路线单位是否合法
     * @param list
     */
    private boolean checkRouteDepartment(ArrayList list)
    {

        //如果是一级路线，只判断路线单位在数据库中是否存在即可；如果是二级路线单位要判断所输入的单位是否为二级单位的子单位
        //20120210 xucy add 如果路线单位在数据库中不存在，给出提示信息
        StringBuffer aa = new StringBuffer();
        for(int i = 0, j = list.size();i < j;i++)
        {
            RouteWrapData routeData = (RouteWrapData)list.get(i);
            String partInform = routeData.getPartNum() + "(" + routeData.getPartName() + ")";
            Object[] depVec = routeData.getDepartmentVec();
            if(depVec!=null)
            {
            aa.append("零部件" + partInform + "：" + "\n");
            
            String str = (String)depVec[0];
            if(str != null && !str.trim().equals(""))
                aa.append(str + "不存在"+"\n");
            if((String)depVec[1] != null && !((String)depVec[1]).trim().equals(""))
            {
                aa.append((String)depVec[1] + "不是二级路线单位的子单位"+"\n");
            }
            }
        }
        //提示哪些零件的哪些路线单位不存在的对话框
        if(aa.length() > 0)
        {
            MessageJDialog dia = new MessageJDialog(this.getParentJFrame());
            dia.setTextArea(aa.toString());
            dia.setVisible(true);
            return false;
        }
        return true;
    }

    /**
     * 执行确定操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        isSave = true;
        commond = e.getActionCommand();
        //  processSaveCommond();

        WKThread work = new WKThread(OKOPTION);
        work.start();

    }

    /**
     * 提交路线表的所有属性以供保存
     * @throws TechnicsRouteException
     * @throws QMRemoteException
     */
    private void commitAllAttributes()
    {
        //说明
        try  
        {
            this.getTechnicsRouteList().setRouteListDescription(descriJTextArea.getText());
            System.out.println("pppp===" + this.getViewMode());
            if(this.getViewMode() == CREATE_MODE)
            {
                //编号.名称
                this.getTechnicsRouteList().setRouteListNumber(numberJTextField.getText());
                this.getTechnicsRouteList().setRouteListName(nameJTextField.getText());
                //级别
                this.getTechnicsRouteList().setRouteListLevel(levelJComboBox.getSelectedItem().toString());

                //状态
                this.getTechnicsRouteList().setRouteListState(stateJComboBox.getSelectedItem().toString());
                //单位
                //CR12 
                if(departmentSelectedPanel.getCoding()!=null)
                    this.getTechnicsRouteList().setRouteListDepartment(departmentSelectedPanel.getCoding().getBsoID());
                //用于产品
                this.getTechnicsRouteList().setProductMasterID(this.productID);
                //设置资料夹
                Class[] theClass = {FolderEntryIfc.class, String.class};
                Object[] objs = {this.getTechnicsRouteList(), folderPanel.getFolderLocation()};
                TechnicsRouteListInfo info = (TechnicsRouteListInfo)RequestHelper.request("FolderService", "assignFolder", theClass, objs);
                this.setTechnicsRouteList(info);
                //设置生命周期和项目组
                LifeCycleManagedIfc lcm = lifeCycleInfo.assign((LifeCycleManagedIfc)getTechnicsRouteList());
                this.setTechnicsRouteList((TechnicsRouteListInfo)lcm);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showWarningDialog(this.getParentJFrame(), message);
        }
    }

    /**
     * 取消操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        processCancelCommond();
    }
  //CCBegin SS1
    /**
     * 增加判断，如选项为“艺毕”则将生命周期设置为“艺毕通知书”，否则生命周期为“艺准通知书”。
     * @param e ActionEvent
     */
    void stateJComboBox_actionPerformed(ActionEvent e)
    {
    	 if(stateJComboBox.getSelectedItem().toString().equals("艺毕")){
    			lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("艺毕通知书");
    	 }else{
    		 lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("艺准通知书");
    	 }
         //CCBegin SS6
         if(comp.equals("zczx")){
         	java.util.Date date = new java.util.Date();
         	String year = Integer.toString(date.getYear()+1900);
         	 Class[] paraClass={String.class,int.class};
        	 Component com=null;
        		if(stateJComboBox.getSelectedItem().toString().equals("艺准")){
            		Object[] aa={"艺准-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);		
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("试制")){
            		Object[] aa={"艺试准-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("前准")){
            		Object[] aa={"前准-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("临准")){
            		Object[] aa={"临准-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("艺毕")){
            		Object[] aa={"艺毕-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	if(stateJComboBox.getSelectedItem().toString().equals("艺废")){
            		Object[] aa={"艺废-ZCZX-"+year+"-",3};
            		String num=(String)invokeRemoteMethodWithException(com,"numService","buildSerialNum",paraClass,aa);
            		numberJTextField.setText(num);
            	}
            	       
         }
         //CCBegin SS18
         else if(comp.equals("ct")){
         	descriJTextArea.setText("");
         }
         //CCEnd SS18
         //CCBegin SS22
         else if(comp.equals("cd"))
         {
         	numberJTextField.setText("自动编号");
         }
         //CCEnd SS22
         else{
            numberJTextField.setText("");
            
            //CCBegin SS7
            //CCBegin SS8 SS24
        if(stateJComboBox.getSelectedItem().toString().equals("前准")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单       及本前准进行生产准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("临准")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单       及本临准进行生产准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺准")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单       及本艺准进行生产准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("试制")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单                    及本艺试准进行试制准备；       完成准备。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺毕")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单        及艺准     所列内容生产准备完毕,可投入生产。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }
        else if(stateJComboBox.getSelectedItem().toString().equals("艺废")){
          descriJTextArea.setText(
              "根据PDM部件更改说明单/技术问题通知单             废弃上述零部件。\n说明：路线代码“协”为解放公司采购部，“采”为变速箱分公司生产部，“变”为装配车间，“零”为零件车间，“轴”为轴齿中心，“销”为营销部，“总”为解放公司卡车厂，“岛”为青岛汽车公司，“柳”为柳特公司，“专”为长特分公司，“川”为成都分公司。\n发往单位：生产部、质保部、技术部、财控部、人力资源部");
        }else{
          descriJTextArea.setText("");
        }
        //CCEnd SS8 SS24
         //CCEnd SS7   
         }
         //CCEnd SS6
    }
  //CCEnd SS1
    /**
     * 如果当前界面模式为创建,如果尚未保存,则提示用户是否保存;如果不保存,则退出界面. 如果当前界面模式为更新,如果尚未保存则提示用户是否保存;如果不保存,则将界面刷新为查看状态
     */
    protected boolean processCancelCommond()
    {
        if(this.getViewMode() == CREATE_MODE)
        {
            if(!isSave)
                this.quitWhenCreate();
            else
                this.setVisible(false);
        }else if(this.getViewMode() == UPDATE_MODE)
        {
            if(!isSave)
                this.quitWhenUpdate();
            else
            {
                this.setViewMode(2);
                isSave = false;
            }

        }
        //System.out.println("isSave isSave isSave " + isSave);
        return isSave;
    }

    /**
     * 创建模式下，取消按钮的执行方法. 如果当前界面模式为创建,如果尚未保存,则提示用户是否保存;如果不保存,则退出界面.
     */
    private void quitWhenCreate()
    {
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.IS_SAVE_CREATE_ROUTELIST, null);
        if(this.confirmAction(s))
        {
            // this.processSaveCommond();
            //（问题一）zz 20061107 进度条必须起线程
            //             WKThread work = new WKThread(SAVAAFTERCANEL);
            //             work.start();
            processSaveCommond();
            isSave = true;//add by guoxl for TD3642  on 2011/2/21
            //防止下面重设树焦点时再次出发保存提示 zz 20061106 start
            //isSave = true;     // end
        }else
        {
            //20120112 xucy add
            this.partLinkJPanel.getAddedPartLinks().clear();
            this.partLinkJPanel.clearPartLinks();
            this.setVisible(false);
            isSave = true;
        }
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() end...return is void ");

    }

    /**
     * 更新模式下，取消按钮的执行方法. 如果当前界面模式为更新,如果尚未保存则提示用户是否保存;如果不保存,则将界面刷新为查看状态
     */
    private void quitWhenUpdate()
    {
        //System.out.println(" routelisttaskjpanel 1440");
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.IS_SAVE_UPDATE_ROUTELIST, null);
        if(this.confirmAction(s))
        {
            this.processSaveCommond();
            isSave = true;
        }else
        {
            this.setViewMode(2);
            isSave = true;
        }
        if(verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() end...return is void");

    }

    /**
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return 如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        JOptionPane okCancelPane = new JOptionPane();
        return okCancelPane.showConfirmDialog(getParentJFrame(), s, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * 搜索“用于产品”的零部件
     * @param e ActionEvent
     */
    void browseJButton_actionPerformed(ActionEvent e)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle", null);
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPartMaster", title, this.getParentJFrame());
        qmChooser.setRelColWidth(new int[]{1, 1});
        qmChooser.setChildQuery(false);
        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener()
        {

            public void queryEvent(QMQueryEvent e)
            {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * 搜索零部件监听事件方法
     * @param e 搜索监听事件
     */
    private void qmChooser_queryEvent(QMQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser)e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if(bvi != null)
                {
                    productID = bvi.getBsoID();
                    productJTextField.setText(getIdentity(bvi));
                    //20120109 xucy add
                    partLinkJPanel.setProductIfc((QMPartMasterIfc)bvi);
                }
            }
        }
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

    /**
     * 设置按钮的显示状态（有效或失效）
     * @param flag flag为True，按钮有效；否则按钮失效
     */
    private void setButtonWhenSave(boolean flag)
    {
        okJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }

    /**
     * 设置本面板为空。
     * @return 当前界面是否执行了保存操作。如果执行了保存，则返回真。
     */
    public boolean setNullMode()
    {

        return isSave;
    }

    /**
     * 20120112 xucy add 获取关联面板
     * @return
     */
    public RouteListPartLinkJPanel getPartLinkJPanel()
    {
        return this.partLinkJPanel;
    }

    /**
     * 获得路线级别 //20120113 xucy add
     */
    public String getRouteLevel()
    {
        return levelJComboBox.getSelectedItem().toString();
    }

    /**
     * 获得部门ID
     */
    public String getDepartmentID()
    {
        if(departmentSelectedPanel.getCoding() != null)
        {
            return departmentSelectedPanel.getCoding().getBsoID();
        }else
        {
            return "";
        }
    }

    /**
     * 级别Combo响应事件
     * @param e
     */
    void levelJComboBox_actionPerformed(ActionEvent e)
    {
        partLinkJPanel.setRouteLevel(this.levelJComboBox.getSelectedItem().toString());
        if(this.levelJComboBox.getSelectedItem().toString().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            departmentSelectedPanel.setVisible(true);
            departmentJLabel.setVisible(true);
            if(this.mode == CREATE_MODE)
            {
                departmentSelectedPanel.setViewTextField("");
            }
        }else
        {
            departmentSelectedPanel.setVisible(false);
            departmentJLabel.setVisible(false);
        }
    }

    class WKThread extends QMThread
    {
        int myAction;

        public WKThread(int action)
        {
            super();
            this.myAction = action;
        }

        /**
         * WKTread运行方法
         */
        public void run()
        {

            switch(myAction)
            {
            case SAVE:
                processSaveCommond();
                break;
            case OKOPTION:
                processSaveCommond();
                break;
            case SAVAAFTERCANEL:
                processSaveCommond();
                break;

            }
        }

    }

}

/**
 * <p>Title:级别Combo事件响应适配器</p> <p>Description: </p>
 */
class RouteListTaskJPanel_levelJComboBox_actionAdapter implements java.awt.event.ActionListener
{
    private RouteListTaskJPanel adaptee;

    RouteListTaskJPanel_levelJComboBox_actionAdapter(RouteListTaskJPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.levelJComboBox_actionPerformed(e);
    }
}
