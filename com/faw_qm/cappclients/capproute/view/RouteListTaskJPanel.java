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
 *
 * SS1 问题：更新路线产生丢件情况，导致添加零件界面和编辑路线选择零件界面个数不一致。
 *     原因：此问题在用户切换路线进行编辑时，且只进行基本属性修改没有进入修改零部件关联界面时，会将切换的路线零部件关联混淆。
 *     解决：去掉CR1中一处初始化设置代码的注释即可。每次进入更新界面都刷新零部件关联。
 * liunan 2012-8-1 01
 * SS2 修改前准的说明信息。 liunan 2013-1-24
 * SS3 添加路线是否自动获取最新路线的复选框标识，默认选中，即自动添加最新路线，然后在路线编辑界面由用户决定是否修改。 liunan 2013-4-17
 * SS4 艺准通知书增加类别为“工艺合件”的工艺路线 liuyang 2014-6-3
 * SS5 取消检验资料夹 Liuyang 2014-6-3
 * SS6 另存为路线 liuyang 2014-6-9  
 * SS7 前准、艺准、试制、临准4个类别的通知书去掉艺准零部件列表中原来的按钮“更改通知单号添加”和“采用解放添加”，新增按钮“更改通知书”和“采用通知书” 和"另存为"复选框 liuyang 2014-6-18
 * SS8 ?
 * SS9 A004-2015-3109艺准自动修改说明内容。 liunan 2015-5-6
 * SS10 添加附件。 liunan 2015-6-18
 * SS11 编号限制由30改为50 liunan 2015-9-1
 * SS12 艺准保存时去掉编号头尾的多余空格 liunan 2016-10-19
 * SS13 A004-2017-3580 修改临准说明的文字 liunan 2017-7-6
 * SS14 A004-2017-3618 需改试制的说明文字 liunan 2017-11-17
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.faw_qm.clients.beans.folderPanel.*;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.users.model.*;
import com.faw_qm.folder.model.*;
import com.faw_qm.lifecycle.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.cappclients.capproute.util.*;
import com.faw_qm.cappclients.resource.view.*;
import com.faw_qm.technics.route.exception.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCBegin SS10
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//CCEnd SS10
/**
 * Title:维护路线表
 * Description:
 * Copyright: Copyright (c) 2004
 * Company: 一汽启明
 * @author 刘明
 * @mender skybird
* @mender zz
 * @version 1.0
* （问题一）zz 20060925 换乘新版进度条 周茁添加
* （问题二）防止下面重设树焦点时再次出发保存提示 zz 20061106 周茁添加
 */
public class RouteListTaskJPanel extends RParentJPanel {
    private JTabbedPane jTabbedPane = new JTabbedPane();

    private JPanel jPanel1 = new JPanel();

    /***/
    private RouteListPartLinkJPanel partLinkJPanel = new RouteListPartLinkJPanel();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel attriJPanel = new JPanel();

    private JLabel numberJLabel = new JLabel();
    private JTextField numberJTextField = new JTextField();

    private JLabel nameJLabel = new JLabel();

    private JTextField nameJTextField = new JTextField();

    private JLabel levelJLabel = new JLabel();

    private JLabel departmentJLabel = new JLabel();

    private JComboBox levelJComboBox = new JComboBox();
    
    //begin CR5
    private JLabel workstateJLabel = new JLabel();
    
    private JLabel workstateJLabel1 = new JLabel();
    //end CR5

    /** 单位选择面版 */
    private SortingSelectedPanel departmentSelectedPanel;

    private JPanel jPanel3 = new JPanel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel4 = new JPanel();

    private JLabel productJLabel = new JLabel();

    private JTextField productJTextField = new JTextField();

    private JButton browseJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private FolderPanel folderPanel = new FolderPanel();

    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();

    private JLabel descriJLabel = new JLabel();

    private JScrollPane jScrollPane2 = new JScrollPane();

    //CCBegin SS9
    //private JTextArea descriJTextArea = new JTextArea();
    public JTextArea descriJTextArea = new JTextArea();
    //CCEnd SS9

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
    //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线，增加默认有效期字段
    private JLabel defaultJLabel = new JLabel();
    private JScrollPane defaultJScrollPane = new JScrollPane();
    private JTextArea defaultJTextArea = new JTextArea();
    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线，增加默认有效期字段

    /** 界面显示模式（更新模式）标记 */
    public final static int UPDATE_MODE = 0;

    /** 界面显示模式（创建模式）标记 */
    public final static int CREATE_MODE = 1;

    /** 界面显示模式（查看模式）标记 */
    public final static int VIEW_MODE = 2;

    private final static int OKOPTION = 3;
    private final static  int SAVE = 4;
    private final static  int SAVAAFTERCANEL = 5;
    /** 界面模式--查看 */
    private int mode = VIEW_MODE;
    /** 业务对象 */
    private TechnicsRouteListIfc myRouteList;

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 标记是否执行了保存操作 */
    protected boolean isSave = false;

    private JLabel jLabel1 = new JLabel();

    private JLabel versionJLabel = new JLabel();

    /** 缓存:产品标识 */
    private String productID = "";
    
    //CCBegin by liunan 2011-04-28 增加类别监听是否执行的标识。
    public static boolean stateJComboBoxFlag = true;
    //CCEnd by liunan 2011-04-28
    
//CCBegin SS10
      private UpFilePanel upFilePanel;
      private JFrame fFrame;
      static boolean fileVaultUsed = (RemoteProperty.getProperty(
              "registryFileVaultStoreMode", "true")).equals("true");
    private JLabel fujianJLabel = new JLabel();
//CCEnd SS10
    
   TextValidCheck textheck  = new TextValidCheck("工艺路线表",30);
    /**
     * 构造函数
     *
     * @roseuid 4031A737030E
     */
    public RouteListTaskJPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
   private void jbInit() throws Exception {
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
        nameJLabel.setText("*项目名称");
        nameJLabel.setBounds(new Rectangle(15, 53, 41, 18));
        nameJTextField.setMaximumSize(new Dimension(2147483647, 22));
        nameJTextField.setBounds(new Rectangle(64, 50, 63, 22));
        levelJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        levelJLabel.setText("级别");
        levelJLabel.setBounds(new Rectangle(232, 14, 41, 18));
        departmentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        departmentJLabel.setText("*单位");
        levelJComboBox.setMaximumSize(new Dimension(50, 22));
        levelJComboBox.setMinimumSize(new Dimension(50, 22));
        levelJComboBox.setPreferredSize(new Dimension(50, 22));
        levelJComboBox.setBounds(new Rectangle(291, 10, 126, 22));
        
        //begin CR5
        workstateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workstateJLabel.setText("工作状态");
        workstateJLabel1.setText("sm");
        workstateJLabel1.setMaximumSize(new Dimension(200, 22));
        workstateJLabel1.setMinimumSize(new Dimension(200, 22));
        workstateJLabel1.setPreferredSize(new Dimension(200, 22));
        //end CR5
        
        departmentSelectedPanel = new SortingSelectedPanel("单位",
                "TechnicsRouteList", "routeListDepartment");
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
        productJLabel.setText("*用于产品");
        productJTextField.setMaximumSize(new Dimension(2147483647, 22));
        productJTextField.setEditable(false);
        browseJButton.setMaximumSize(new Dimension(91, 23));
        browseJButton.setMinimumSize(new Dimension(91, 23));
        browseJButton.setPreferredSize(new Dimension(91, 23));
        browseJButton.setMnemonic('R');
        browseJButton.setText("搜索(R). . .");
        browseJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseJButton_actionPerformed(e);
            }
        });
        descriJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriJLabel.setText("说明");
       //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        defaultJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        defaultJLabel.setText("默认有效期");
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        partLinkJPanel.setBorder(BorderFactory.createEtchedBorder());
        partLinkJPanel.setMaximumSize(new Dimension(338, 32767));
        partLinkJPanel.setMinimumSize(new Dimension(338, 10));
        partLinkJPanel.setPreferredSize(new Dimension(338, 10));
        //CCBegin SS9
        partLinkJPanel.setRLTJPanel(this);
        //CCEnd SS9
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
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveJButton_actionPerformed(e);
            }
        });
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OKROUTELIST");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });
        //CCBegin SS4
        stateJComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
          
					stateJComboBox_actionPerformed(e);
		
            }
        });
        //CCEnd SS4
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
        lifeCycleInfo.getLifeCyclePanel().setBrowseButtonSize(
                new Dimension(83, 23));
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(
                new Dimension(83, 23));
        lifeCycleInfo.setBsoName("TechnicsRouteList");
        lifeCycleInfo.getProjectPanel().setButtonMnemonic('P');
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(
                new Dimension(91, 23));
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        stateLabel.setText("类别");
        stateJComboBox.addItemListener(new
                RouteListTaskJPanel_stateJComboBox_itemAdapter(this));
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        buttonJPanel.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel4.add(browseJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2,
                        8, 2, 0), 0, 0));
        jPanel4.add(productJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2,
                        0, 2, 0), 0, 0));
        jPanel4.add(productJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 8, 2, 0), 0, 0));
//      CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线        
//        attriJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 3, 2, 1, 1.0,
//                0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                new Insets(4, 10, 4, 10), 0, 10));
        attriJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(4, 34, 4, 10), 0, 10));
//      CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线        
        //begin CR5
        attriJPanel.add(workstateJLabel, new GridBagConstraints(0, 4, 4, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 40, 4, 10), 0, 0));
        attriJPanel.add(workstateJLabel1, new GridBagConstraints(1, 4, 4, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        //end CR5

        attriJPanel.add(descriJLabel, new GridBagConstraints(0, 5, 1, 1, 0.0,
                0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(5, 40, 0, 0), 0, 0));
        attriJPanel.add(jScrollPane2, new GridBagConstraints(1, 5, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 8, 10, 10), 0, 0));
       // jScrollPane2.setHorizontalScrollBarPolicy( jScrollPane2.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.getViewport().add(descriJTextArea, null);
//      CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
       attriJPanel.add(defaultJLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
          , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
          new Insets(5, 28, 0, 0), 0, 0));
       attriJPanel.add(defaultJScrollPane,
                      new GridBagConstraints(1, 6, 1, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(5, 8, 10, 10), 0, 0));
       defaultJScrollPane.getViewport().add(defaultJTextArea, null);  
//     CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
      //  descriJTextArea.setColumns(128);
        descriJTextArea.setLineWrap(true);
        attriJPanel.add(folderPanel, new GridBagConstraints(1, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(4, 7, 4, 10), 0, 0));
        attriJPanel.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        attriJPanel.add(locationJLabel, new GridBagConstraints(1, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 10), 0, 0));
        this.add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        folderPanel.removeLabel();
        folderPanel.setButtonMnemonic('O');
        folderPanel.setButtonSize(91, 23);
        
        jTabbedPane.add(jPanel1, "属性");
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jTabbedPane.add(partLinkJPanel,    "零部件表");
        
      //Begin CR1
        class WorkThread extends Thread               
      	{
      		public void run()
      		{
      			ProgressDialog d = new ProgressDialog(getParentJFrame());
      			d.setMessage("正在处理数据，请稍候...");
      			d.startProcess();

      			partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
      			d.stopProcess();
      		}
      	}
        jTabbedPane.addChangeListener(new ChangeListener(){               

    		public void stateChanged(ChangeEvent cevent)
            {
    			if(jTabbedPane.getSelectedIndex()==1){
    				
    				  WorkThread t = new WorkThread();
    				   t.start();
             }
            }
        	
        });       
      //End CR1   
        
        jScrollPane1.getViewport().add(attriJPanel, null);

        jPanel3.add(numberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel3.add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel3.add(numberJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJComboBox, new GridBagConstraints(3, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(levelJLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        18, 0, 0), 0, 0));
        jPanel3.add(levelDisplayJLabel, new GridBagConstraints(3, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentDisplayJLabel, new GridBagConstraints(3, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(departmentJLabel, new GridBagConstraints(2, 1, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 18, 0, 0), 0, 0));
        jPanel3.add(departmentSelectedPanel, new GridBagConstraints(3, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 0, 4, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4,
                        0, 2, 0), 0, 0));
        jPanel3.add(versionJLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 2, 0), 0, 0));
        jPanel3.add(stateJComboBox, new GridBagConstraints(3, 2, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateDisplayJLabel, new GridBagConstraints(3, 2, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel3.add(stateLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));

        attriJPanel.add(jPanel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        7, 34, 0, 10), 0, 0));
//      CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线     
//        attriJPanel.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
//                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//                new Insets(4, 10, 4, 10), 0, 0));
        attriJPanel.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
          new Insets(4, 34, 4, 10), 0, 0));
//      CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线   
        this.add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
                        10, 0, 10, 0), 0, 0));

	      //CCBegin SS10
        fujianJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fujianJLabel.setText("附件");
        upFilePanel = new UpFilePanel((JFrame)this.getFrame());
        attriJPanel.add(fujianJLabel, new GridBagConstraints(0, 7, 1, 1, 0.0,
                0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(5, 40, 0, 0), 0, 0));
        attriJPanel.add(upFilePanel, new GridBagConstraints(1, 7, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 8, 10, 10), 0, 0));
	      //CCEnd SS10
                
        departmentSelectedPanel.setVisible(false);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        localize();
        
    }
    
    //CCBegin SS10
    public void setFrame(JFrame f)
    {
    	fFrame = f;
    }
    public JFrame getFrame()
    {
    	return fFrame;
    }
    //CCEnd SS10


    /**
     * 本地化
     */
    private void localize() {
        RouteListLevelType[] levelTypes = RouteListLevelType.getRouteListLevelTypeSet();
        for (int i = 0; i < levelTypes.length; i++) {
            levelJComboBox.addItem(levelTypes[i].getDisplay());
        }
        levelJComboBox.setSelectedItem(RouteListLevelType.getRouteListLevelTypeDefault().getDisplay());
        levelJComboBox
                .addActionListener(new RouteListTaskJPanel_levelJComboBox_actionAdapter(
                        this));
        initStateJComboBox();
    }

    /**
     * 初始化工艺路线表状态复选框的值
     */
    private void initStateJComboBox() {
        Class[] params = { String.class, String.class };
        Object[] values = { "状态", "工艺路线表" };
        Collection result = null;
        try {
            result = (Collection) invokeRemoteMethodWithException(this,
                    "CodingManageService", "findDirectClassificationByName",
                    params, values);
                //System.out.println("找到状态数是  " + result.size());
        } catch (QMRemoteException ex) {
            //输出异常信息：
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    QMMessage.getLocalizedMessage(RESOURCE, "ERROR", null),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (result != null && result.size() > 0) {
            Iterator iterator = result.iterator();
            while (iterator.hasNext()) {
                stateJComboBox.addItem((iterator.next()).toString());
            }
            stateJComboBox.setSelectedIndex(0);
        }
    }

    /**
     * 构造一个方法，专门用于远程调用服务端的方法：
     *
     * @param component
     *            表示调用该方法的当前界面对象，可以为空
     * @param serviceName
     *            被调用的服务端服务类名(ServiceName)
     * @param methodName
     *            被调用的方法名
     * @param paramTypes
     *            被调用的方法的参数类型集合，需要按照方法的顺序
     * @param paramValues
     *            传输的各个参数的值
     * @throws QMRemoteException
     *             抛出远程异常
     * @return 结果对象
     */
    public static Object invokeRemoteMethodWithException(Component component,
            String serviceName, String methodName, Class[] paramTypes,
            Object[] paramValues) throws QMRemoteException {
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
        try {
            result = server.request(info);
        } catch (QMRemoteException ex) {
            //对异常进行处理，输出异常信息，并再抛。
            //ex.printStackTrace();
            throw ex;
        }
        //end try-catch
        return result;
    }


    /**
     * 设置业务对象
     *
     * @param routelist
     *            工艺路线表对象
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
        myRouteList = routelist;
    }

    /**
     * 获得业务对象
     *
     * @return 工艺路线表对象
     */
    public TechnicsRouteListIfc getTechnicsRouteList() {
        return myRouteList;
    }

    /**
     * 设置默认显示新建指定产品的路线表界面(暂时不用本方法)
     *
     * @param product
     *            指定产品
     */
    public void setDefaultProductView(QMPartMasterIfc product) {
        this.setViewMode(CREATE_MODE);
        productJTextField.setText( getIdentity(product));
        productID = product.getBsoID();
    }

    /**
     * 设置界面模式（创建、更新或查看）。
     *
     * @param aMode
     *            新界面模式
     */
    public void setViewMode(int aMode) {
        if ((aMode == UPDATE_MODE) || (aMode == CREATE_MODE)
                || (aMode == VIEW_MODE)) {
            mode = aMode;
        }

        switch (aMode) {

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
     *
     * @return 当前界面模式
     */
    public int getViewMode() {
        return mode;
    }

    /**
     * 设置界面为新建模式
     */
    private void setCreateModel() {
        ((CappRouteListManageJFrame) this.getParentJFrame()).getTreePanel()
                .clearSelection();
        isSave = false;
        jTabbedPane.setEnabledAt(1, false);
        jTabbedPane.setSelectedIndex(0);
        levelDisplayJLabel.setVisible(false);
        stateDisplayJLabel.setVisible(false);
        levelJComboBox.setVisible(true);
        levelJComboBox.setSelectedItem(RouteListLevelType
                .getRouteListLevelTypeDefault().getDisplay());
//      CCBegin by leixiao 2010-1-8 解放只用一级路线，让用户不能选择二级路线
        levelJComboBox.setEnabled(false);
//      CCEnd by leixiao 2010-1-8
        stateJComboBox.setVisible(true);
//      CCBegin by leixiao 2010-2-23 默认为艺准
        stateJComboBox.setSelectedIndex(2);
//      CCEnd by leixiao 2010-2-23 
        //partLinkJPanel.setEditModel();
        this.setTextFieldVisible(numberJTextField);
        numberJTextField.setText("");
        //CCBegin SS4
        numberJTextField.setVisible(true);
        numberJTextField.setEnabled(true);
        //CCend SS4
        this.setTextFieldVisible(nameJTextField);
        nameJTextField.setText("");
        this.setTextFieldVisible(productJTextField);
        productJTextField.setText("");
        productJTextField.setEditable(false);
        descriJTextArea.setEditable(true);
//      CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加说明默认字段
        defaultJTextArea.setEditable(false);
        descriJTextArea.setText("根据：技术中心PDM部件更改说明单      及本艺准进行生产准备。\n" + "说明：");
        defaultJTextArea.setText("");
//      CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加说明默认字段
        browseJButton.setVisible(true);
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        departmentJLabel.setVisible(false);
        departmentDisplayJLabel.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(false);
        workstateJLabel1.setVisible(false);
        workstateJLabel1.setText(null);
        //end CR5
        TechnicsRouteListInfo routeList = new TechnicsRouteListInfo();
        this.setTechnicsRouteList(routeList);

        versionJLabel.setText("A.1");
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
        lifeCycleInfo.getProjectPanel().setObject(null);
        //lifeCycleInfo.setObject((LifeCycleManagedIfc)this.getTechnicsRouteList());
        locationJLabel.setVisible(false);
        folderPanel.setVisible(true);
        folderPanel.setSelectModel(true);
        folderPanel.setButtonSize(91, 23);
        try {

            folderPanel.setLabelText(this.getPersionalFolder());
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }

         //CCBegin SS10
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(false);
         this.getUpFilePanel().getMultiList().clear();
         this.getUpFilePanel().setRow(0);
         //CCEnd SS10
         
        if (!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * 设置界面为更新模式
     */
    private void setUpdateModel() {
        isSave = false;
        jTabbedPane.setEnabledAt(1, true);
//      CCBegin by leixiao 2009-3-30 原因：解放升级工艺路线,因为艺毕的判断，将setEditModel放在setTechnicsRouteList后面
//      partLinkJPanel.setEditModel();
//    CCEnd by leixiao 2009-3-30 原因：解放升级工艺路线
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        productJTextField.setEditable(false);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        //CCBegin by liunan 2011-04-12 李萍要求更新是允许更改“类别”
        //stateJComboBox.setVisible(false);
        stateJComboBox.setVisible(true);
        //CCBegin by liunan 2011-04-28 setSelectedItem时，默认是前准，如果根据当前艺准类别，有改变的话就会引发监听，改变说明的内容。
        //因此添加一个标识stateJComboBoxFlag，只有此处监听不起作用。
    	  stateJComboBoxFlag = false;
        stateJComboBox.setSelectedItem(this.getTechnicsRouteList().getRouteListState());
        stateJComboBoxFlag = true;
        //CCEnd by liunan 2011-04-28
        //CCEnd by liunan 2011-04-12
        stateDisplayJLabel.setVisible(true);
        //end
//      CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加默认有效期
        if (this.getTechnicsRouteList().getRouteListState().equals("临准")) {
            defaultJTextArea.setEditable(true);
          }
          else {
            defaultJTextArea.setEditable(false);
          }
//      CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加默认有效期
        descriJTextArea.setEditable(true);
        browseJButton.setVisible(false);
        saveJButton.setVisible(true);
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5
        try {
            numberJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListLevel());
            stateDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListState());
            versionJLabel
                    .setText(this.getTechnicsRouteList().getVersionValue());
            //CCBegin SS1
            partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());//CR1
            //CCEnd SS1
            //CCBegin SS8
            if(this.getTechnicsRouteList().getRouteListState().equals("前准")||this.getTechnicsRouteList().getRouteListState().equals("艺准")||
            		this.getTechnicsRouteList().getRouteListState().equals("试准")
        				|| this.getTechnicsRouteList().getRouteListState().equals("临准")) {
            	partLinkJPanel.setPartLinkJPanel();
            }
            //CCEnd SS8
            this.partLinkJPanel.getAddedPartLinks().clear();//Begin CR2
            this.partLinkJPanel.getDeletedPartLinks().clear();//End CR2
//          CCBegin by leixiao 2009-3-30 原因：解放升级工艺路线,因为艺毕的判断，将setEditModel放在setTechnicsRouteList后面
            partLinkJPanel.setEditModel(this.getTechnicsRouteList());
//          CCEnd by leixiao 2009-3-30 原因：解放升级工艺路线
            //判断是否显示单位
            String department = ((TechnicsRouteListInfo) this
                    .getTechnicsRouteList()).getDepartmentName();
            if (department != null && !department.equals("")) {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            } else {
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
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle(
                (LifeCycleManagedIfc) getTechnicsRouteList());
            lifeCycleInfo.getProjectPanel().setObject(
                (LifeCycleManagedIfc) getTechnicsRouteList());
            //modify by guoxl end

            //lifeCycleInfo.getLifeCyclePanel().setLifeCycle((LifeCycleManagedIfc)getTechnicsRouteList());
            String productID = this.getTechnicsRouteList().getProductMasterID();
            this.productID = productID;
            QMPartMasterInfo partinfo = null;

            partinfo = (QMPartMasterInfo) refreshInfo(productID);
            productJTextField.setText(getIdentity(partinfo));
            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            try {
				String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
				workstateJLabel1.setText(str);
			} catch (QMException e) {
				e.printStackTrace();
			}
			//end CR5
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }

         //CCBegin SS10
         setUpFileAccessaryName((TechnicsRouteListIfc) getTechnicsRouteList());
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(true);
         //CCEnd SS10
         
        if (!this.isShowing())
            this.setVisible(true);
        repaint();
    }
    //CCBegin SS4
    
    void stateJComboBox_actionPerformed(ActionEvent e) 
    {
		if (stateJComboBox.getSelectedItem().toString().equals("工艺合件")) {
			lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("工艺合件路线生命周期");
			numberJTextField.setText("自动编号");
			numberJTextField.setEditable(false);
			numberJTextField.setEnabled(false);
        	String folder = RemoteProperty.getProperty("hechengRouteFolder", "\\Root\\工艺合件艺准通知书");   
        	folderPanel.setLabelText(folder);
		}
     
    }
  //CCEnd SS4  

    /**
     * 设置界面为查看模式
     */
    private void setViewDisplayModel() {
    	
    	if (verbose)
            {
                System.out.println("查看模式"
                        + getTechnicsRouteList().getPartIndex().size());
                        
                            Vector vv = getTechnicsRouteList().getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                            	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]);
                            }}
                            
    	jTabbedPane.setSelectedIndex(0);//CR1
        jTabbedPane.setEnabledAt(1, true);
        partLinkJPanel.setViewModel();
        this.setTextFieldToLabel(numberJTextField);
        this.setTextFieldToLabel(nameJTextField);
        this.setTextFieldToLabel(productJTextField);
        levelJComboBox.setVisible(false);
        levelDisplayJLabel.setVisible(true);
        //st skybird 2005.2.24
        stateJComboBox.setVisible(false);
        stateDisplayJLabel.setVisible(true);
        //ed
        browseJButton.setVisible(false);
        descriJTextArea.setEditable(false);
//      CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加默认有效期
        defaultJTextArea.setEditable(false);
//      CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加默认有效期
        saveJButton.setVisible(false);
        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        departmentSelectedPanel.setVisible(false);
        //begin CR5
        workstateJLabel.setVisible(true);
        workstateJLabel1.setVisible(true);
        //end CR5

        try {
            numberJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListNumber());
            nameJTextField.setText(this.getTechnicsRouteList()
                    .getRouteListName());
            levelDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListLevel());
            //st skybird 2005.2.24
            stateDisplayJLabel.setText(this.getTechnicsRouteList()
                    .getRouteListState());
            //ed
            versionJLabel
                    .setText(this.getTechnicsRouteList().getVersionValue());
            descriJTextArea.setText(this.getTechnicsRouteList()
                    .getRouteListDescription());
//          CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加默认有效期
            defaultJTextArea.setText(this.getTechnicsRouteList().
                    getDefaultDescreption());
//          CCEnd by leixiao 2009-7-31 原因：解放升级工艺路线,增加默认有效期
//          CCBegin by leixiao 2009-12-04 原因：解放升级工艺路线初使化时已刷新过了，查看成不再刷新
           // partLinkJPanel.setTechnicsRouteList(this.getTechnicsRouteList());
//          CCEnd by leixiao 2008-12-04 原因：解放升级工艺路线,增加默认有效期
            //判断是否显示单位
            String department = ((TechnicsRouteListInfo) this
                    .getTechnicsRouteList()).getDepartmentName();
            if (department != null && !department.equals("")) {
                departmentJLabel.setVisible(true);
                departmentDisplayJLabel.setVisible(true);
                departmentDisplayJLabel.setText(department);
            } else {
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
            if (verbose)
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!"
                        + this.getTechnicsRouteList().getProjectId());
           // lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            //CCBegin By leixiao 2009-12-10 状态不显示
            lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
            //CCEnd By leixiao 2009-12-10 状态不显示
            lifeCycleInfo.getLifeCyclePanel().setLifeCycle(
                    (LifeCycleManagedIfc) getTechnicsRouteList());
            //modify by guoxl on 20080214(当对路线表重新指定项目组时界面不能刷新更改后的项目组信息，特别项目组指定为空时)
            //if (getTechnicsRouteList().getProjectId() != null)
                lifeCycleInfo.getProjectPanel().setObject(
                        (LifeCycleManagedIfc) getTechnicsRouteList());
            //else
                //lifeCycleInfo.getProjectPanel().setObject(null);
                //modify by guoxl end
            String productID = this.getTechnicsRouteList().getProductMasterID();
            QMPartMasterInfo partinfo = null;

            partinfo = (QMPartMasterInfo) refreshInfo(productID);
            productJTextField.setText(getIdentity(partinfo));
            //begin CR5
            WorkInProgressHelper wiphelp = new WorkInProgressHelper();
            try {
				String str = wiphelp.getStatus((WorkableIfc)getTechnicsRouteList());
				workstateJLabel1.setText(str);
			} catch (QMException e) {
				e.printStackTrace();
			}
            //end CR5
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //CCBegin SS10
        setUpFileAccessaryName((TechnicsRouteListIfc) getTechnicsRouteList());
        this.getUpFilePanel().setAButtonVisable(false);
        this.getUpFilePanel().setDButtonVisable(false);
        this.getUpFilePanel().setVButtonVisable(false);
        this.getUpFilePanel().setDLButtonVisable(true);
        //CCEnd SS10

        if (!this.isShowing())
            this.setVisible(true);
        repaint();
    }

    /**
     * 获得当前用户的个人资料夹位置
     *
     * @return 当前用户的个人资料夹位置
     * @throws QMRemoteException
     */
    public String getPersionalFolder() throws QMRemoteException {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() begin...");
        Class[] c = {};
        Object[] objs = {};
        UserInfo user = (UserInfo) useServiceMethod("SessionService",
                "getCurUserInfo", c, objs);
        Class[] c1 = { UserInfo.class };
        Object[] objs1 = { user };
        SubFolderInfo folder = (SubFolderInfo) useServiceMethod(
                "FolderService", "getPersonalFolder", c1, objs1);
        if (verbose)
            System.out.println("cappclients.capproute.view.RouteListTaskJPanel.getPersionalFolder() end...return: "
                            + folder.getPath());
        return folder.getPath();

    }

    /**
     * 设置把指定的单行文本域显示为标签
     *
     * @param textfield
     *            指定的单行文本域
     */
    private void setTextFieldToLabel(JTextField textfield) {
        textfield.setBorder(null);
        textfield.setBackground(SystemColor.control);
        textfield.setEditable(false);
    }

    /**
     * 设置把指定的单行文本域显示为可编辑
     *
     * @param textfield
     *            指定的单行文本域
     */
    private void setTextFieldVisible(JTextField textfield) {
        textfield.setBorder(javax.swing.plaf.basic.BasicBorders
                .getTextFieldBorder());
        textfield.setBackground(Color.white);
        textfield.setEditable(true);
    }

    /**
     * 级别默认为“一级”,此时单位从代码管理器中读取. 级别选择为“二级”时，单位标签和下拉列表框显示，否则单位标签和下拉列表不可见
     *
     * @param e
     *            ItemEvent
     */
    void levelJComboBox_itemStateChanged(ItemEvent e) {

    }

    /**
     * 检验必填区域是否已有有效值
     *
     * @return 如果必填区域已有有效值，则返回为真
     */
    private boolean checkRequiredFields() {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() begin...");
        boolean isOK = false;
        String message = "fell through ";
        String title = "";
        try {

            if ((getViewMode() == CREATE_MODE)) {
                //检验编号是否为空
                if (numberJTextField.getText().trim().length() == 0) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_NUMBER_ENTERED, null);
                    this.numberJTextField.grabFocus();
                    isOK = false;
                }
                //检验名称是否为空
                else if (nameJTextField.getText().trim().length() == 0) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_NAME_ENTERED, null);
                    this.nameJTextField.grabFocus();
                    isOK = false;
                }
                //检验资料夹是否为空
                else if (checkFolderLocation() == null) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_LOCATION_ENTERED, null);
                    this.folderPanel.grabFocus();
                    isOK = false;
                }
                //检验"用于产品"是否为空
                else if (productJTextField.getText().trim().length() == 0) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_PRODUCT_ENTERED, null);
                    this.productJTextField.grabFocus();
                    isOK = false;
                } else if (departmentSelectedPanel.isShowing()
                        && departmentSelectedPanel.getCoding() == null) {
                    message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappRouteRB.NO_DEPARTMENT_ENTER, null);
                    departmentSelectedPanel.grabFocus();
                    isOK = false;
                } else {
                    isOK = true;
                }
            } else {
                isOK = true;
            }
            if (!isOK) {
                //显示信息：缺少必需的字段
                title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(), message,
                        title, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (QMRemoteException qre) {
            //显示信息：所指定的资料夹不是个人资料夹
            title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(), qre
                            .getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
              this.folderPanel.grabFocus();
        }
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() end...return: "
                            + isOK);
        return isOK;

    }

    /**
     * 检查文本框的数值有效性
     *
     * @return boolean
     */
    private boolean check() {
      if (numberJTextField.getText().indexOf("*") != -1 || numberJTextField.getText().indexOf("%") != -1 ||
            numberJTextField.getText().indexOf("?") != -1)
        {
            String message = "编号" + "含有非法字符eg:*%?";
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                  null);
          JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                  JOptionPane.WARNING_MESSAGE);
          this.numberJTextField.grabFocus();
            return false;
        }

        if (nameJTextField.getText().indexOf("*") != -1 || nameJTextField.getText().indexOf("%") != -1 ||
           nameJTextField.getText().indexOf("?") != -1)
       {
           String message = "名称" + "含有非法字符eg:*%?";
           String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                 null);
         JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                 JOptionPane.WARNING_MESSAGE);
         this.numberJTextField.grabFocus();
           return false;
       }
        if (numberJTextField.getText().trim().getBytes().length > 200) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            String message = QMMessage
                    .getLocalizedMessage(RESOURCE, "52", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            this.numberJTextField.grabFocus();
            return false;
        }
        if (this.nameJTextField.getText().trim().getBytes().length > 200) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            String message = QMMessage
                    .getLocalizedMessage(RESOURCE, "53", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            this.nameJTextField.grabFocus();
            return false;
        }
        if (this.descriJTextArea.getText().trim().getBytes().length > 2000) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            String message = QMMessage
                    .getLocalizedMessage(RESOURCE, "54", null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
           this.descriJTextArea.grabFocus();
            return false;
        }
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线，增加默认有效期字段
        if (this.defaultJTextArea.getText().trim().length() > 4000) {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                "warning",
                null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                "54",
                null);
            JOptionPane.showMessageDialog(null, message, title,
                                          JOptionPane.WARNING_MESSAGE);
            return false;
          }
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线，增加默认有效期字段
        return true;
    }
    private boolean checkText (){
      try {
       //CCBegin SS11
       //textheck.setMax(30); //CR3
       textheck.setMax(100); //CR3
       //CCEnd SS11
       textheck.check(numberJTextField, true);
      

     }
     catch (QMRemoteException ex) {
           String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                 null);
         JOptionPane.showMessageDialog(this.getParentJFrame(), ex.getClientMessage(), title,
                 JOptionPane.WARNING_MESSAGE);
         this.numberJTextField.grabFocus();
           return false;

     }
     try {textheck.setMax(200);
           textheck.check(nameJTextField, true);
         }
         catch (QMRemoteException ex) {
               String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                     null);
             JOptionPane.showMessageDialog(this.getParentJFrame(), ex.getClientMessage(), title,
                     JOptionPane.WARNING_MESSAGE);
             this.nameJTextField.grabFocus();
               return false;

         }

        try {textheck.setMax(2000);
                   textheck.check(descriJTextArea, false);
                 }
                 catch (QMRemoteException ex) {
                       String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                             null);
                     JOptionPane.showMessageDialog(this.getParentJFrame(),  ex.getClientMessage(), title,
                             JOptionPane.WARNING_MESSAGE);
                     this.descriJTextArea.grabFocus();
                       return false;

                 }

         return true;
    }
    /**
     * 检验是否已指定资料夹
     *
     * @return 如果已指定资料夹路径，则返回资料夹。
     * @throws QMRemoteException
     */
    private SubFolderInfo checkFolderLocation() throws QMRemoteException {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() begin...");
        String location = "";
        SubFolderInfo folderInfo = null;
        //获得资料夹路径

        location = folderPanel.getFolderLocation();

        if (location != null && location.length() != 0) {
            //调用资料夹服务，根据获得的资料夹路径获得资料夹
            Class[] paraClass = { String.class };
            Object[] objs = { location };
            try {
                folderInfo = (SubFolderInfo) useServiceMethod(
                        "FolderService", "getFolder", paraClass, objs);
            } catch (QMRemoteException ex) {
                throw ex;
            }

        }
//CCBegin SS5
//        if (folderInfo != null) {
//            //调用资料夹服务，判断指定的文件夹是否是个人文件夹
//            Class[] paraClass2 = { FolderIfc.class };
//            Object[] objs2 = { folderInfo };
//            Boolean flag1 = null;
//            try {
//                flag1 = (Boolean) useServiceMethod("FolderService",
//                        "isPersonalFolder", paraClass2, objs2);
//            } catch (QMRemoteException ex) {
//                String title = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappRouteRB.LOCATION_NOT_VALID, null);
//                JOptionPane.showMessageDialog(getParentJFrame(), ex
//                        .getClientMessage(), title,
//                        JOptionPane.INFORMATION_MESSAGE);
//                this.folderPanel.grabFocus();
//
//            }
//
//            boolean flag = false;
//            if (flag1 != null) {
//                flag = flag1.booleanValue();
//            }
//
//            if (!flag) {
//                //抛出异常信息：所指定的资料夹不是个人文件夹
//                throw new QMRemoteException(QMMessage.getLocalizedMessage(
//                        RESOURCE, CappRouteRB.LOCATION_NOT_PERSONAL_CABINET,
//                        null));
//            }
//        }
//CCEnd SS5
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.checkFolderLocation() end...return : "
                            + folderInfo);
        return folderInfo;

    }

    /**
     * 执行保存操作
     *
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e) {
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
     * 系统根据业务规则PHOS-CAPP-BR201检查要求非空的属性是否为空(E1)，
     * 根据业务规则PHOS-CAPP-BR202检查工艺路线表编号是否唯一(E2)，
     * 如果当前为创建模式,则系统创建此新工艺路线表，把新创建的工艺路线表信息添加到路线表管理器的路线表列表中，
     * 把创建界面刷新为更新界面(如果选择了"确定",则刷新为查看模式). 如果当前为更新模式,则更新此路线表,刷新树节点,将界面刷新为查看模式.
     */
    private void processSaveCommond() {

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
        if (!requiredFieldsFilled) {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave=false;
            return;
        }
        if (!flag) {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave=false;
            return;
        }
        //显示保存进度
//        ProgressService.setProgressText(QMMessage.getLocalizedMessage(RESOURCE,
//                CappRouteRB.SAVING, null));
//        ProgressService.showProgress();
    //（问题一）zz 20060925 换乘新版进度条
                progressDialog = new ProgressDialog(getParentJFrame());
                progressDialog.startProcess();


        try {
   /*add by guoxl on 2008.4.3(保存路线表时提示“操作正在进行中，请稍后重试”的异常信息，
    原因是 在保存时没刷新，导致时间戳不一致(四环车厢厂实施问题))
   */
if (this.getViewMode() == UPDATE_MODE) {
       

  try {
   this.setTechnicsRouteList(
     (TechnicsRouteListIfc)(CappClientHelper.refresh(this.getTechnicsRouteList().getBsoID())));

      }
    catch (QMRemoteException ex) {

          JOptionPane.showMessageDialog(getParentJFrame(), ex.getClientMessage());
          CappRouteListManageJFrame  f = (CappRouteListManageJFrame)this.getParentJFrame();
          f.getTreePanel().removeNode(new RouteListTreeObject(getTechnicsRouteList()));
          setCursor(Cursor.getDefaultCursor());
          setButtonWhenSave(true);
           this.setVisible(false);
          return ;
        }
}
    //add end by guoxl on 2008.4.3
          //提交所有属性
            this.commitAllAttributes();
            if (verbose)
            {
                System.out.println("setPartIndex前顺序"
                        + getTechnicsRouteList().getPartIndex().size());
                        
                            Vector vv = getTechnicsRouteList().getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                            	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]);
                            }}
            if (this.partLinkJPanel != null)
            {
                getTechnicsRouteList().setPartIndex(
                        partLinkJPanel.getPartIndex());
            }
            if (verbose)
            {
                System.out.println("保存前的顺序"
                        + getTechnicsRouteList().getPartIndex().size());
                        
                            Vector vv = getTechnicsRouteList().getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                            	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]+"=="+idd[3]);
                            }}
            if (this.getViewMode() == CREATE_MODE) {


              //CCBegin SS10
              //获得附件信息
              ArrayList arrayList = getArrayList();
              //调用服务，保存工艺路线表
                //Class[] paraClass = { TechnicsRouteListIfc.class };
                Class[] paraClass = { TechnicsRouteListIfc.class , ArrayList.class};
                 //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线,编号转成大写
                getTechnicsRouteList().setRouteListNumber(getTechnicsRouteList().
                                                          getRouteListNumber().
                                                          toUpperCase());
              //CCEnd SS10
                 //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
                //Object[] obj = { this.getTechnicsRouteList() };
                Object[] obj = { this.getTechnicsRouteList(), arrayList };
                TechnicsRouteListIfc returnRouteList = null;

                   returnRouteList = (TechnicsRouteListIfc)
                      useServiceMethod(
                      "TechnicsRouteService", "storeRouteList", paraClass,
                      obj);
        
                // （问题二）防止下面重设树焦点时再次出发保存提示 zz 20061106
                isSave = true;
                this.setTechnicsRouteList(returnRouteList);
              //String ct = returnRouteList.getCreateTime().toString();
             // System.out.println("创建时间======"+ ct.substring(0,ct.lastIndexOf(".")));

                //把新创建的工艺路线表信息添加到路线表管理器的路线表列表中
                RouteTreePanel treePanel = ((CappRouteListManageJFrame) this
                        .getParentJFrame()).getTreePanel();
                RouteListTreeObject newObj = new RouteListTreeObject(
                        returnRouteList);
                treePanel.addNode(newObj);
                ((CappRouteListManageJFrame) getParentJFrame()).isViewRouteList = false;
                treePanel.setNodeSelected(newObj);
                ((CappRouteListManageJFrame) getParentJFrame()).isViewRouteList = true;
                //把创建界面刷新为更新界面
                jTabbedPane.setEnabledAt(1, true);
                this.setTechnicsRouteList(returnRouteList);
                this.setViewMode(UPDATE_MODE);
               
                if (commond.equals("OKROUTELIST")) {

                    //将界面刷新为查看模式
                    partLinkJPanel.setViewModel();
                    descriJTextArea.setEditable(false);
                    saveJButton.setVisible(false);
                    okJButton.setVisible(false);
                    cancelJButton.setVisible(false);
                    mode = VIEW_MODE;
                }

            } else if (this.getViewMode() == UPDATE_MODE) {
            	//CCBegin SS10
              //获得附件信息
        ArrayList arrayList = getArrayListupdate();
        Collection deleteContentCol = (Collection)getUpFilePanel().
		                            getDeleteContentMap().values();
		    Collection vec = new Vector(deleteContentCol);
		    getUpFilePanel().getDeleteContentMap().clear();  
                //保存路线表与零部件的关联
                //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线 调用解放的方法
                Class[] c = { HashMap.class, ArrayList.class,HashMap.class,
                        //CCBegin SS3
                        //TechnicsRouteListIfc.class };
                	
                       // TechnicsRouteListIfc.class, boolean.class };
                //TechnicsRouteListIfc.class, boolean.class , boolean.class};
                TechnicsRouteListIfc.class, boolean.class , boolean.class, ArrayList.class, Vector.class};
                        //CCEnd SS3
   
                Object[] objs = {
                    partLinkJPanel.getAddedPartLinks(),
                    partLinkJPanel.getUpdateLinks(),
                    partLinkJPanel.getDeletedPartLinks(),
                    //CCBegin SS3
                    //this.getTechnicsRouteList()};
                    this.getTechnicsRouteList(),
                    partLinkJPanel.addLastRouteCheckBox.isSelected(),
                    //CCEnd SS3
                //CCBegin SS6
                    //partLinkJPanel.saveAs.isSelected()};
                    partLinkJPanel.saveAs.isSelected(), arrayList, vec};
                    //CCEnd SS10
                //CCEnd SS6
                    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
                if (verbose)
                    System.out.println("删除集合 =="
                            + partLinkJPanel.getDeletedPartLinks());
                if (verbose)
                    System.out.println("添加集合 =="
                            + partLinkJPanel.getAddedPartLinks());
                useServiceMethod("TechnicsRouteService",
                        "saveListRoutePartLink", c, objs);
             //   isSave = true;//CR4
               partLinkJPanel.clearPartLinks();
                //更新零部件的父件编号 added by skybird 2005.3.4
                //c = new Class[]{Collection.class,TechnicsRouteListIfc.class};
                Class[] c1 = { String.class };
                String theBeforedBsoid = this.getTechnicsRouteList().getBsoID();
                Object[] obj1 = { theBeforedBsoid };
                if (verbose)
                    System.out.println("原有的路线表id" + theBeforedBsoid);
                TechnicsRouteListIfc technicsRouteIfc = (TechnicsRouteListIfc) CappRouteAction
                        .useServiceMethod("PersistService", "refreshInfo", c1,
                                obj1);
                theBeforedBsoid = technicsRouteIfc.getBsoID();
                if (verbose){
                    System.out.println("更新后的partIndexs"
                            + technicsRouteIfc.getPartIndex().size());
                            Vector vv = technicsRouteIfc.getPartIndex();
                            for(int ii=0;ii<vv.size();ii++)
                            {
                               	String[] idd = (String[])vv.elementAt(ii);
                            	System.out.println(idd[0]+"=="+idd[1]+"=="+idd[2]+"=="+idd[3]);
                            }};
                this.myRouteList = technicsRouteIfc;
                //CCBegin by liunan 2011-04-12 李萍要求更新是允许更改“类别”
                //this.setTechnicsRouteList(technicsRouteIfc);//anan
                //System.out.println("anan 后"+this.myRouteList.getPartIndex().size());
                //CCEnd by liunan 2011-04-12
                // System.out.println("要改的零部件");
                // Vector tmp = (Vector)partLinkJPanel.getPartsToChange();
                //  System.out.println(tmp.size());
                // for(int i=0;i<tmp.size();i++)
                // {
                //   Object[] a = (Object[])tmp.elementAt(i);
                /// System.out.println("part bsoid"+a[0]+"parentPartID"+a[1]);
                //  }
                //  objs = new
                // Object[]{partLinkJPanel.getPartsToChange(),this.getTechnicsRouteList()};
                //  useServiceMethod(
                //  "TechnicsRouteService","updateListRoutePartLink",c,objs);
                //刷新树节点
                ((CappRouteListManageJFrame) this.getParentJFrame())
                        .getTreePanel()
                        .updateNode(
                                new RouteListTreeObject(getTechnicsRouteList()));
                if (commond.equals("OKROUTELIST")) {
                  //将界面刷新为查看模式

                  partLinkJPanel.setViewModel();
                  descriJTextArea.setEditable(false);
                  saveJButton.setVisible(false);
                  okJButton.setVisible(false);
                  cancelJButton.setVisible(false);
                  //CCBegin by liunan 2011-04-12 李萍要求更新是允许更改“类别”
                  stateJComboBox.setVisible(false);
                  this.setViewMode(2);
                  //CCEnd by liunan 2011-04-12
                  mode = VIEW_MODE;
                }
              //CCBegin by leixiao 2011-1-12 原因:解放路线新需求
                //因为艺毕自动加载了每个零件的路线,所有刷新让其显示路线状态
                if (this.getTechnicsRouteList().getRouteListState().equals("艺毕")){
                partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
                }
                //CCBegin by liunan 2011-09-21 艺废通知书。
                else if (this.getTechnicsRouteList().getRouteListState().equals("艺废")){
                partLinkJPanel.setTechnicsRouteList(getTechnicsRouteList());
                }
                //CCEnd by liunan 2011-09-21
              //CCBegin by leixiao 2011-1-12 原因：
            }

            //isSave=true;//CR4
        } catch (TechnicsRouteException ex) {

            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            isSave = false;
            mode = CREATE_MODE;
        } catch (QMRemoteException ex) {
            progressDialog.stopProcess();
            System.out.println(getParentJFrame());
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
            isSave = false;
            mode = CREATE_MODE;
        }

        //ProgressService.hideProgress();
        progressDialog.stopProcess();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);

    }

    /**
     * 执行确定操作
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        isSave = true;
        commond = e.getActionCommand();
      //  processSaveCommond();

      WKThread work = new WKThread(OKOPTION);
       work.start();

    }

    /**
     * 提交路线表的所有属性以供保存
     *
     * @throws TechnicsRouteException
     * @throws QMRemoteException
     */
    private void commitAllAttributes() throws TechnicsRouteException,
            QMRemoteException {
        //说明
        this.getTechnicsRouteList().setRouteListDescription(
                descriJTextArea.getText());
        //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线，增加默认有效期
        this.getTechnicsRouteList().setDefaultDescreption(defaultJTextArea.getText());
        //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线
        
        //CCBegin by liunan 2011-04-12 根据李萍要求，更新时可以更改“类别”。
        this.getTechnicsRouteList().setRouteListState(stateJComboBox.getSelectedItem().toString());
        //CCEnd by liunan 2011-04-12

        if (this.getViewMode() == CREATE_MODE) {
            //编号.名称
            //CCBegin SS12
            this.getTechnicsRouteList().setRouteListNumber(
                    //numberJTextField.getText());
                    numberJTextField.getText().trim());
            //CCEnd SS12
            this.getTechnicsRouteList().setRouteListName(
                    nameJTextField.getText());
            //级别
            this.getTechnicsRouteList().setRouteListLevel(
                    levelJComboBox.getSelectedItem().toString());
            //CCBegin by liunan 2011-04-12 根据李萍要求，更新时可以更改“类别”。挪到if外此处注释。
            //状态
            //this.getTechnicsRouteList().setRouteListState(
                    //stateJComboBox.getSelectedItem().toString());
            //CCEnd by liunan 2011-04-12
            //单位
            if (departmentSelectedPanel.isShowing())
                this.getTechnicsRouteList().setRouteListDepartment(
                        departmentSelectedPanel.getCoding().getBsoID());
            //用于产品
            this.getTechnicsRouteList().setProductMasterID(this.productID);
            //设置资料夹
            Class[] theClass = { FolderEntryIfc.class, String.class };
            Object[] objs = { this.getTechnicsRouteList(),
                    folderPanel.getFolderLocation() };
            TechnicsRouteListInfo info = (TechnicsRouteListInfo)
                    useServiceMethod("FolderService", "assignFolder",
                            theClass, objs);
            this.setTechnicsRouteList(info);
            //设置生命周期和项目组
            LifeCycleManagedIfc lcm = lifeCycleInfo
                    .assign((LifeCycleManagedIfc) getTechnicsRouteList());
            this.setTechnicsRouteList((TechnicsRouteListInfo) lcm);
        }
    }

    /**
     * 取消操作
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        processCancelCommond();
    }

    /**
     * 如果当前界面模式为创建,如果尚未保存,则提示用户是否保存;如果不保存,则退出界面.
     * 如果当前界面模式为更新,如果尚未保存则提示用户是否保存;如果不保存,则将界面刷新为查看状态
     */
    protected boolean processCancelCommond() {
        if (this.getViewMode() == CREATE_MODE) {
           //System.out.println("CREATE_MODE");
            if (!isSave)
                this.quitWhenCreate();
            else
                this.setVisible(false);
        } else if (this.getViewMode() == UPDATE_MODE) {
          //System.out.println("UPDATE_MODE");
            if (!isSave)
                this.quitWhenUpdate();
            else {
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
    private void quitWhenCreate() {
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.IS_SAVE_CREATE_ROUTELIST, null);
        if (this.confirmAction(s)) {
           // this.processSaveCommond();
            //（问题一）zz 20061107 进度条必须起线程
//             WKThread work = new WKThread(SAVAAFTERCANEL);
//             work.start();
            processSaveCommond();
            //防止下面重设树焦点时再次出发保存提示 zz 20061106 start
            //isSave = true;     // end
        } else {
            this.setVisible(false);
            isSave = true;
        }
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() end...return is void ");

    }

    /**
     * 更新模式下，取消按钮的执行方法. 如果当前界面模式为更新,如果尚未保存则提示用户是否保存;如果不保存,则将界面刷新为查看状态
     */
    private void quitWhenUpdate() {
      //System.out.println(" routelisttaskjpanel 1440");
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() begin...");
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.IS_SAVE_UPDATE_ROUTELIST, null);
        if (this.confirmAction(s)) {
            this.processSaveCommond();
            isSave = true;
        } else {
            this.setViewMode(2);
            isSave = true;
        }
        if (verbose)
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() end...return is void");

    }

    /**
     * 显示确认对话框
     *
     * @param s
     *            在对话框中显示的信息
     * @return 如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private boolean confirmAction(String s) {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information",
                null);
        JOptionPane okCancelPane = new JOptionPane();
        return okCancelPane.showConfirmDialog(getParentJFrame(), s, title,
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * 搜索“用于产品”的零部件
     *
     * @param e
     *            ActionEvent
     */
    void browseJButton_actionPerformed(ActionEvent e) {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle",
                null);
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPartMaster", title, this
                .getParentJFrame());
        qmChooser.setRelColWidth(new int[] { 1, 1 });
//      CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,
        qmChooser.setChildQuery(true);
//      CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线, 
        try {
            qmChooser.setMultipleMode(false);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener() {

            public void queryEvent(QMQueryEvent e) {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * 搜索零部件监听事件方法
     *
     * @param e
     *            搜索监听事件
     */
    private void qmChooser_queryEvent(QMQueryEvent e) {
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if (e.getType().equals(QMQueryEvent.COMMAND)) {
            if (e.getCommand().equals(QmQuery.OkCMD)) {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser) e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if (bvi != null) {
                    productID = bvi.getBsoID();
                    productJTextField.setText(getIdentity(bvi));
                }
            }
        }
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

    /**
     * 设置按钮的显示状态（有效或失效）
     *
     * @param flag
     *            flag为True，按钮有效；否则按钮失效
     */
    private void setButtonWhenSave(boolean flag) {
        okJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
        //CCBegin SS10
        this.getUpFilePanel().setDLButtonVisable(flag);
        //CCEnd SS10
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
     * 级别Combo响应事件
     * @param e
     */
    void levelJComboBox_actionPerformed(ActionEvent e) {
        if (this.levelJComboBox.getSelectedItem().toString().equals(
                RouteListLevelType.SENCONDROUTE.getDisplay())) {
            departmentSelectedPanel.setVisible(true);
            departmentJLabel.setVisible(true);
        } else {
            departmentSelectedPanel.setVisible(false);
            departmentJLabel.setVisible(false);
        }
    }
    
    //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加默认有效期说明
    void stateJComboBox_itemStateChanged(ItemEvent e) {
    	//CCBegin by liunan 2011-04-28 根据stateJComboBoxFlag判断是否监听。只有第一次更新艺准时会走这。
    	if(stateJComboBoxFlag)
    	{
    	//CCEnd by liunan 2011-04-28
        if (this.stateJComboBox.getSelectedItem().equals("临准")) {
          this.defaultJTextArea.setEditable(true);
        }
        else {
          this.defaultJTextArea.setEditable(false);
        }
//      CCBegin by leixiao 2009-5-4 原因：解放升级工艺路线,增加艺毕
        if(this.stateJComboBox.getSelectedItem().equals("艺毕")) {
        //CCBegin by liunan 2011-04-12 根据李萍要求，更新时可以更改“类别”，但生命周期不能随着修改，由用户手工修改。
        if(this.getViewMode() != UPDATE_MODE)
        //CCEnd by liunan 2011-04-12
        lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(
                "艺毕通知书");
     //   descriJTextArea.setText("根据：技术中心PDM部件更改说明单      \n" + "说明：");
        descriJTextArea.setText("根据：技术中心采用通知书（PDM部件更改说明单）       及艺准     所列内容生产准备完毕,可投入生产。\n" + "说明：");
        }
        //CCBegin SS2
        else if(this.stateJComboBox.getSelectedItem().equals("前准"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("艺准通知书（需会签）");
          }
          descriJTextArea.setText("根据：技术中心PDM部件更改说明单/技术问题通知单/产品采用及更改通知书      及本前准进行生产准备。\n" + "说明：");
        }
        //CCEnd SS2
        //CCBegin by liunan 2011-09-21 艺废通知书。
        else if(this.stateJComboBox.getSelectedItem().equals("艺废"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("艺废通知书");
          }
          descriJTextArea.setText("根据：技术问题通知单（采用通知书）    废弃所列内容。\n" + "说明：");
        }
        //CCBegin SS13
        else if(this.stateJComboBox.getSelectedItem().equals("临准"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("艺准通知书（需会签）");
          }
          descriJTextArea.setText("根据：技术中心技术问题通知单      及本临准进行生产准备。\n" + "说明：");
        }
        //CCEnd SS13
        //CCBegin SS14
        else if(this.stateJComboBox.getSelectedItem().equals("试制"))
        {
        	if(this.getViewMode() != UPDATE_MODE)
        	{
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("艺准通知书（需会签）");
          }
          descriJTextArea.setText("根据：技术中心技术问题通知单      及本艺试准进行生产准备。\n" + "说明：");
        }
        //CCEnd SS14
        else{
            //CCBegin by liunan 2011-04-12 根据李萍要求，更新时可以更改“类别”，但生命周期不能随着修改，由用户手工修改。
        if(this.getViewMode() != UPDATE_MODE)
        //CCEnd by liunan 2011-04-12
            lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(
            "艺准通知书（需会签）");
            descriJTextArea.setText("根据：技术中心PDM部件更改说明单      及本艺准进行生产准备。\n" + "说明：");
        }
//      CCEnd by leixiao 2009-5-4 原因：解放升级工艺路线
      //CCBegin by liunan 2011-04-28 根据stateJComboBoxFlag判断是否监听。只有第一次更新艺准时会走这。
      }
      //CCEnd by liunan 2011-04-28
      }
    //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线

    class WKThread extends QMThread {
        int myAction;
        public WKThread(int action) {
            super();
            this.myAction = action;
        }


        /**
         * WKTread运行方法
         */
        public void run() {

                switch (myAction) {
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

	 //CCBegin SS10
    /**
     * 获得所有的附件信息
     */
    private ArrayList getArrayList() 
    {
	    /**内容项值对象*/
	    ApplicationDataInfo applicationData = null;
	    ArrayList arrayList = new ArrayList();
      int j = getUpFilePanel().getMultiList().getTable().getRowCount();
	  if(fileVaultUsed)
	  {
		  ContentClientHelper helper = new ContentClientHelper();
	      for (int i = 0; i < j; i++)
	      {
	    	  String path = (String)getUpFilePanel().getMultiList().getCellText(i, 1);
	    	  try 
	    	  {
				applicationData =  helper.requestUpload(new File(path));
	    	  } catch (QMException e) 
	    	  {
				e.printStackTrace();
	    	  }
	    	  arrayList.add(applicationData);
	      }
	  }
	  else
	  {
	      for (int i = 0; i < j; i++)
	      {
	    	//问题(3)2007.01.17 徐春英修改  修改原因:字节流没有保存到数据库,
	          //构造二维数组存放ApplicationDataInfo对象和字节流.
	          Object[] object = new Object[2];
	          String fileName = (String)getUpFilePanel().
	                            getMultiList().getCellText(i, 0);
	          String path = (String)getUpFilePanel().
	                        getMultiList().
	                        getCellText(i, 1);
	          String length = (String) getUpFilePanel().
	                          getMultiList().
	                          getCellText(i, 2);
	          applicationData = new ApplicationDataInfo();
	          applicationData.setFileName(fileName);
	          applicationData.setUploadPath(path);
	          applicationData.setFileSize(Long.parseLong(length));
	          //问题(3)2007.01.17 徐春英修改 获得文件字节流
	          byte[] byteStream = getFileByte(path);
	          object[0] = applicationData;
	          object[1] = byteStream;
	          //问题(3)2007.01.17 徐春英修改  将object数组放到arrayList1里,供客户端保存用
	          arrayList.add(object);
	      }
	  }
      return arrayList;
    }
    
    /**
     * 获得更新时所有的附件信息，都是新增的附件。
     */
    private ArrayList getArrayListupdate() 
    {
	    /**内容项值对象*/
	    ApplicationDataInfo applicationData = null;

        int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        ArrayList arrayList1 = new ArrayList();
    	if(fileVaultUsed)
   	  	{
    		ContentClientHelper helper = new ContentClientHelper();
    		for (int i = 0; i < j; i++)
            {
                String appDataID = (String) getUpFilePanel().
                                   getMultiList().getCellText(i, 3);
                if (appDataID == null)
                {
                    String path = (String) getUpFilePanel().
                                  getMultiList().
                                  getCellText(i, 1);
                    try 
      	    	  	{
	      				applicationData =  helper.requestUpload(new File(path));
	      	    	} catch (QMException e) 
	      	    	{
	      	    		e.printStackTrace();
	      	    	}
	      	    	arrayList1.add(applicationData);
                }
            }
   	  	}
    	else
    	{
    		for (int i = 0; i < j; i++)
            {
                String appDataID = (String) getUpFilePanel().
                                   getMultiList().getCellText(i, 3);
                if (appDataID == null)
                {
                    //问题(3)2007.01.17 徐春英修改  修改原因:字节流没有保存到数据库,
                    //构造二维数组存放ApplicationDataInfo对象和字节流.
                    Object[] object = new Object[2];
                    String fileName = (String)getUpFilePanel().
                                      getMultiList().
                                      getCellText(i, 0);
                    String path = (String) getUpFilePanel().
                                  getMultiList().
                                  getCellText(i, 1);
                    String length = (String)getUpFilePanel().
                                    getMultiList().
                                    getCellText(i, 2);
                    applicationData = new ApplicationDataInfo();
                    applicationData.setFileName(fileName);
                    applicationData.setUploadPath(path);
                    applicationData.setFileSize(Long.parseLong(length));
                    //问题(3)2007.01.17 徐春英修改 获得文件字节流
                    byte[] byteStream = getFileByte(path);
                    object[0] = applicationData;
                    object[1] = byteStream;
                    //问题(3)2007.01.17 徐春英修改  将object数组放到arrayList1里,供客户端保存用
                    arrayList1.add(object);
                }
            }
    	}
      return arrayList1;
    }
    
    /**
     * 获得另存为时原工艺卡所拥有的附件信息。
     */
    private Vector getVectorSaveAs() 
    {
    	  int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        Vector vec = new Vector();
        for (int i = 0; i < j; i++)
        {
            String appDataID = (String) getUpFilePanel().
                               getMultiList().getCellText(i, 3);
            if (appDataID != null)
            {
                vec.add(appDataID);
            }
        }
      return vec;
    }
    
    /**
     * 根据文件路径获得文件流
     * @param path String
     * @return byte[]
     */
    private byte[] getFileByte(String path)
    {
      File file = new File(path);
      long length = file.length();
      byte[] b = new byte[(int) length];
      try
      {
          FileInputStream in = new FileInputStream(file);
          in.read(b);
          in.close();
      }
      catch (FileNotFoundException ex1)
      {
      	ex1.printStackTrace();
      }
      catch (IOException ex2)
      {
      	ex2.printStackTrace();
      }
      return b;
    }
    
    
    /**
     * 设置定额界面中添加附件的信息
     * @param equip QMEquipmentInfo
     */                                 
    private void setUpFileAccessaryName(TechnicsRouteListIfc upFileList)
    {
    	this.getUpFilePanel().getMultiList().clear();
      Vector vec = null;
      try 
      {
      	vec = getContents(upFileList);
		  }
		  catch (QMRemoteException e)
		  {
		  	e.printStackTrace();
		  }
		  if (vec == null)
		  {
		  	return;
		  }
		  int size = vec.size();
		  for (int m = 0; m < size; m++)
		  {
          ApplicationDataInfo applicationData = (ApplicationDataInfo)
                                                vec.elementAt(m);
          this.getUpFilePanel().getMultiList().addTextCell(m, 0,
                  applicationData.getFileName());
          this.getUpFilePanel().getMultiList().addTextCell(m, 1,
                  applicationData.getUploadPath());
          this.getUpFilePanel().getMultiList().addTextCell(m, 2,
                  String.valueOf(applicationData.getFileSize()));
          this.getUpFilePanel().getMultiList().addTextCell(m, 3,
                  applicationData.getBsoID());
          this.getUpFilePanel().setApplication(
                  applicationData);
      }
      this.getUpFilePanel().setRow(size);
    }
    
    /**
     * 得到内容容器中指定的数据项
     * @param priInfo TechnicsRouteListIfc 内容容器
     * @return Vector ApplicationDataInfo 内容项
     * @throws RationException 
     */
    private Vector getContents(TechnicsRouteListIfc priInfo) throws QMRemoteException
    {
    	Class[] paraClass ={ContentHolderIfc.class};
    	Object[] obj ={priInfo};
    	Vector  c = (Vector) useServiceMethod(
                    "ContentService", "getContents", paraClass, obj);
		  return c;
    }
    
    private UpFilePanel getUpFilePanel()
    {
      return upFilePanel;
    }
    //CCEnd SS10
}

 /**
  * <p>Title:级别Combo事件响应适配器</p>
  * <p>Description: </p>
  */
class RouteListTaskJPanel_levelJComboBox_actionAdapter implements
        java.awt.event.ActionListener {
    private RouteListTaskJPanel adaptee;

    RouteListTaskJPanel_levelJComboBox_actionAdapter(RouteListTaskJPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.levelJComboBox_actionPerformed(e);
    }
    
    
}

//CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加默认有效期说明
class RouteListTaskJPanel_stateJComboBox_itemAdapter
implements java.awt.event.ItemListener {
RouteListTaskJPanel adaptee;

RouteListTaskJPanel_stateJComboBox_itemAdapter(RouteListTaskJPanel adaptee) {
this.adaptee = adaptee;
}

public void itemStateChanged(ItemEvent e) {
adaptee.stateJComboBox_itemStateChanged(e);
}
}    //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线
