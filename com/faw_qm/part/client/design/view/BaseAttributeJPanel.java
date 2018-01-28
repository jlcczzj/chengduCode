/** 生成程序DescribedByJPanel.java	1.1  2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/06/19 谢斌 原因：基本属性界面“状态”处没有对齐。TD-2233
 *                     方案：由于生命周期和其状态使用的是一个面板，界面元素变化时无法做出统一响应，现改为状态为单独面板，即可统一响应界面变化。
 * CR2 2009/07/03 马辉 原因：TD2515 进入产品信息管理器点击树的根节点产品结构,右边基本属性页资料夹处显示“\Root\Administrator"，
 *                          从零部件上把焦点移回跟焦点，基本属性面板有遗留信息。
 *                     方案：焦点在根结点时，清空基本属性面板的信息 
  //CCBegin by leixiao 2009-12-16 打补丁v4r3_p001_20091215
 * CR3 2009/11/26 王亮  原因：产品信息管理器所有Tab页信息一次全都初始化。
 *                     方案：关注哪个Tab页就初始化哪个Tab页。
 * CR4 2009/11/30 王亮 原因：修改"状态"值来回乱跳的现象。
 * 
 * CR5 2009/12/02 王亮 原因：没有对零部件进行修改也会弹出对话框”是否保存对*零部件的修改“。                  
  //CCEnd by leixiao 2009-12-16 打补丁v4r3_p001_20091215
 *CCBegin By leixiao　2009-12-08 将定制的颜色件标识从parttaskjpanel移到BaseAttributeJPanel
 */
package com.faw_qm.part.client.design.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.MissingResourceException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.faw_qm.clients.beans.ViewChoice;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.clients.beans.lifecycle.LifeCyclePanel;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.prodmgmt.PartAttributesPanel;
import com.faw_qm.clients.util.EnumeratedChoice;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.QMPropertyException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.Unit;
import com.faw_qm.viewmanage.model.ViewObjectInfo;


/**
 * <p>Title: 描述关联面板。</p>
 * <p>Description:零部件描述关联的可视化界面。在本面板对零部件的描述关联进行</p>
 * <p>创建、更新、删除等操作。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 * @see DescribedByAssociationsModel,AssociationsPanel
 */

public class BaseAttributeJPanel extends HelperPanel
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;   

    /**用于标记资源信息路径*/
    protected static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**属性面板（生命周期和项目组）*/
    private LifeCycleInfo lifeCycleInfoPanel = new LifeCycleInfo();
    
    private JPanel lifeCycleStatePanel = new JPanel(); 
    
    private JLabel viewStateLabel = new JLabel();
    
    private JLabel viewStateValue = new JLabel();

    /**属性面板（编号、名称、来源、类型）*/
    private PartAttributesPanel beanAttributesPanel;

    /**视图标签*/
    private JLabel viewJLabel = new JLabel();

    /**更新零部件时，用于显示零部件视图*/
    private JLabel viewDisplayJLabel = new JLabel();

    /**视图选择器*/
    private ViewChoice viewChoice = new ViewChoice();

    /**资料夹面板*/
    private FolderPanel folderPanel = new FolderPanel();
    
    /**用于显示版本号、视图和单位的面板*/
    private JPanel changeLabelJPanel = new JPanel(new GridBagLayout());

    /**单位选择框*/
    private EnumeratedChoice unitChoice = new EnumeratedChoice();

    /**单位标签*/
    private JLabel unitJLabel = new JLabel();

    /**更新零部件时，用于显示零部件单位*/
    private JLabel unitDisplayJLabel = new JLabel();

    /**用于获得系统的当前版本*/
    private static String PART_CLIENT = RemoteProperty.getProperty(
            "part_client_customize_earmark", "A");

    /**版本标签*/
    private JLabel versionJLabel = new JLabel();

    /**更新零部件时，用于显示版本的标签*/
    private JLabel displayVersionJLabel = new JLabel();
    
    //liyz add 创建者和创建时间的信息面板 
    //用户信息面板
	private JPanel userJPanel = new JPanel(new GridBagLayout());
	//时间信息面板
	private JPanel dateJPanel = new JPanel(new GridBagLayout());
	//创建者标签
	private JLabel createUserJLabel = new JLabel();
	//更新者标签
	private JLabel updateUserJLabel = new JLabel();
	//创建时间标签
	private JLabel createDateJLabel = new JLabel();
	//更新时间标签
	private JLabel updateDateJLabel = new JLabel();
	//显示创建者标签
	private JLabel createUserValue = new JLabel();
	//显示更新者标签
	private JLabel updateUserValue = new JLabel();
	//显示创建时间标签
	private JLabel createDateValue = new JLabel();
	//显示更新时间标签
	private JLabel updateDateValue = new JLabel();
	
	//liyz add 变更信息面板
	private PartChangePanel partChangePanel = new PartChangePanel();
	private JScrollPane jScrollpane=new JScrollPane();
	
	//liyz add 工作状态面板
	private JPanel workableStateJPanel = new JPanel(new GridBagLayout());
	//工作状态标签
	private JLabel workableStateJLabel = new JLabel();
    
	//显示工作状态标签
	private JLabel workableStateValue = new JLabel();
    
    private boolean mainPart=true;  

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    //CCBegin by leixiao 2009-12-16 打补丁v4r3_p001_20091215
    private boolean isShown;//此Tab页是否显示的标志//CR3
    //CCEnd by leixiao 2009-12-16 打补丁v4r3_p001_20091215
    
    //CCBegin by liunan 2008-07-24
    JLabel colorFlag = new JLabel();
    JCheckBox colorFlagCheckBox = new JCheckBox();
    //CCEnd by liunan 2008-07-24

  
    /**
     * 构造函数。
     */
    public BaseAttributeJPanel(boolean flag)
    {
        super();
        mainPart=flag;
        //CCBegin by leixiao 2009-12-16 打补丁v4r3_p001_20091215
        isShown = false;//CR3
        //CCEnd by leixiao 2009-12-16 打补丁v4r3_p001_20091215
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 初始化。
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        this.setSize(620, 206);
        this.setLayout(gridBagLayout1);
        //初始化PartAttributesPanel Bean
        beanAttributesPanel = new PartAttributesPanel();
        beanAttributesPanel
                .setObjectClassName("com.faw_qm.part.client.design.model.PartItem");
        beanAttributesPanel.setAttributes(new String[]{"Number", "Name",
                "Source", "Type"});
        beanAttributesPanel.setLabels(new String[]{
                getLabelsRB().getString("required")
                        + getLabelsRB().getString("numberLbl"),
                getLabelsRB().getString("required")
                        + getLabelsRB().getString("nameLbl"),
                getLabelsRB().getString("sourceLbl"),
                getLabelsRB().getString("typeLbl")});
        beanAttributesPanel.setEdits(new String[]{"true", "true", "true",
                "true"});
        viewJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        viewJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        viewJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        viewJLabel.setText("View:");
        viewJLabel.setBounds(new Rectangle(7, 30, 29, 18));
        
        //CCBegin by liunan 2008-07-24
        colorFlag.setHorizontalAlignment(SwingConstants.RIGHT);
        colorFlag.setHorizontalTextPosition(SwingConstants.RIGHT);
        colorFlag.setBounds(new Rectangle(7,30,29,18));
        colorFlag.setText("");
        //CCEnd by liunan 2008-07-24
        //CCBegin by liunan 2008-07-24
        colorFlagCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12));
        colorFlagCheckBox.setText("颜色件标识");
        //CCEnd by liunan 2008-07-24
        //CCBegin by liunan 2008-07-24
        //设置缺省值，默认为未选中。
        colorFlagCheckBox.setSelected(false);
        //CCEnd by liunan 2008-07-24
        
        viewStateLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        viewStateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        viewStateLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        viewStateLabel.setText("State:");
        viewStateLabel.setBounds(new Rectangle(7, 30, 29, 18));
        
        viewStateValue.setFont(new java.awt.Font("Dialog", 0, 12));
         //CCBegin by leixiao 2009-12-16 打补丁v4r3_p001_20091215
        viewStateValue.setHorizontalAlignment(SwingConstants.LEFT);//修改"状态"值来回乱跳的现象。设置标签内容沿X轴的对齐方式:RIGHT->LEFT//CR4
         //CCEnd by leixiao 2009-12-16 打补丁v4r3_p001_20091215
        viewStateValue.setHorizontalTextPosition(SwingConstants.RIGHT);
        viewStateValue.setText("");
        viewStateValue.setBounds(new Rectangle(7, 30, 29, 18));
        
        unitJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        unitJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        unitJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        unitJLabel.setText("Unit:");
        unitJLabel.setBounds(new Rectangle(7, 56, 29, 18));
        versionJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        versionJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        versionJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        versionJLabel.setText("Version:");
        versionJLabel.setBounds(new Rectangle(7, 4, 29, 18));
        
        lifeCycleStatePanel.setLayout(new GridBagLayout());  
        lifeCycleStatePanel.add(viewStateLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, 
                new Insets(0, 8, 0, 0), 0, 0));
        lifeCycleStatePanel.add(viewStateValue, new GridBagConstraints(1, 0, 1,
                1, 1.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
                new Insets(0,8, 0, 0), 0, 0));
        lifeCycleStatePanel.add(new JLabel(), new GridBagConstraints(0, 1, 2,
                1, 1.0, 1.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
                new Insets(0,8, 0, 0), 0, 0));
      
        //liyz 添加创建者和创建时间标签
        createUserJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        createUserJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        createUserJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        createUserJLabel.setText("CreateUser:");
        createUserJLabel.setBounds(new Rectangle(7, 4, 29, 18));
        updateUserJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        updateUserJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        updateUserJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        updateUserJLabel.setText("UpdateUser:");
        updateUserJLabel.setBounds(new Rectangle(7, 30, 29, 18));
        createDateJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        createDateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        createDateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        createDateJLabel.setText("CreateDate:");
        createDateJLabel.setBounds(new Rectangle(7, 4, 29, 18));
        updateDateJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        updateDateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        updateDateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        updateDateJLabel.setText("UpdateDate:");
        updateDateJLabel.setBounds(new Rectangle(7, 30, 29, 18));//end
        //liyz 添加工作状态标签
        workableStateJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        workableStateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workableStateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        workableStateJLabel.setText("WorkableState:");
        workableStateJLabel.setBounds(new Rectangle(7, 4, 29, 18));//end
        lifeCycleInfoPanel.setLayout(new GridBagLayout());
        lifeCycleInfoPanel.resetPanelInterVal(3);
        lifeCycleInfoPanel.setBsoName("QMPart");
        viewChoice.setBlankChoiceAllowed(false);
        viewChoice.setFont(new java.awt.Font("SansSerif", 0, 12));
        viewChoice.setPreferredSize(new Dimension(52, 22));
    if(mainPart)
    {
        //CCBegin by liunan 2008-07-24
        colorFlagCheckBox.setEnabled(false);
        //CCEnd by liunan 2008-07-24
    changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 0, 1,
            1, 0.0, 0.0, GridBagConstraints.NONE,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5, 0
    changeLabelJPanel.add(viewChoice,
            new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));    
    changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 1, 1,
            1, 0.0, 0.0, GridBagConstraints.NONE,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5
    changeLabelJPanel.add(unitChoice,
            new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));//119
    changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 2,
            1, 1, 0.0, 0.0, GridBagConstraints.NONE,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//0
    changeLabelJPanel.add(displayVersionJLabel,
            new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));//119    
    //CCBegin by liunan 2008-07-24
    changeLabelJPanel.add(colorFlag,
                          new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 0), 0, 0));
    changeLabelJPanel.add(colorFlagCheckBox,
                          new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 8, 0, 0), 0, 0));
    //CCEnd by liunan 2008-07-24
    userJPanel.add(createUserJLabel, new GridBagConstraints(0, 0, 1,
			1, 0.0, 0.0,GridBagConstraints.EAST, GridBagConstraints.NONE, 
			new Insets(0, 8, 0, 1), 0, 0));
    userJPanel.add(createUserValue, new GridBagConstraints(1, 0,1,
            1,1.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
            new Insets(0, 8, 0, 0), 0, 0));
    userJPanel.add(updateUserJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.NONE, 
			new Insets(0, 8, 0, 0), 0, 0));
    userJPanel.add(updateUserValue, new GridBagConstraints(1, 1, 1,
			1, 1.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
			new Insets(0,8, 0, 0), 0, 0));    
    
    dateJPanel.add(createDateJLabel, new GridBagConstraints(0, 0, 1,
            1, 0.0, 0.0, GridBagConstraints.EAST,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5, 0
    dateJPanel.add(createDateValue,
            new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));    
    dateJPanel.add(updateDateJLabel, new GridBagConstraints(0, 1, 1,
            1, 0.0, 0.0, GridBagConstraints.EAST,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5
    dateJPanel.add(updateDateValue,
            new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));//119

    workableStateJPanel.add(workableStateJLabel, new GridBagConstraints(0, 0, 1,
			1, 0.0, 0.0,GridBagConstraints.EAST, GridBagConstraints.NONE,
			new Insets(0, 8, 0, 0), 0, 0));
    workableStateJPanel.add(workableStateValue, new GridBagConstraints(1, 0,1,
            1,1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 8, 0, 0), 0, 0));
    //为界面布局增加一组件
    workableStateJPanel.add(new JLabel(""), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 120), 0, 0));
    add(beanAttributesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 71, 0, 5), 0, 0));//155,5 2, 31, 0, 15
    add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 5), 0, 0));//2,10,38,15
    add(lifeCycleInfoPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 45, 0, 5), 0, 0));//-1,7,0,15  0, 7, -2, 15
    add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 20, 0, 5), 0, 0));//-1,7,0,15  0, 7, -2, 15)
    
    add(userJPanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 55, 0, 5), 0, 0));
    add(dateJPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
    
    add(folderPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 55, 0, 5), 0, 0));//0,15,3,15
    add(workableStateJPanel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));//end
    
    jScrollpane.getViewport().add(partChangePanel);
    add(jScrollpane, new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 0, 10), 0, 0));
    }
    else
    {
    	 //此布局管理不可随意改变，如果确需改变，必须同时保证与enableUpdateFields()方
        //法中对视图和单位布局管理的一致。
        changeLabelJPanel.add(unitChoice, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 0, 0), 119, 0));
        changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 7, 0, 0), 5, 0));
        changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 7, 0, 0), 5, 0));
        changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 96, 18));
        changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 5, 0));
        changeLabelJPanel.add(viewChoice, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 0, 0), 0, 0));
        //CCBegin by liunan 2008-07-24
        changeLabelJPanel.add(colorFlag,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE,
                        new Insets(0, 8, 0, 0), 0, 0));
        changeLabelJPanel.add(colorFlagCheckBox,
                new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 8, 0, 0), 0, 0));
        //CCEnd by liunan 2008-07-24
        
        add(beanAttributesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 31, 0, 15), 155, 5));
        add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 20, 0, 15), 0, 0));
        add(lifeCycleInfoPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 7, 0, 15), 0, 0));
        add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 5), 0, 0));
        add(folderPanel, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 3, 15), 0, 0));        
    }
 
    initialize();
    }

    /**
     * 初始化。
     */
    protected void initialize()
    {
        PART_CLIENT = "C"; //暂时
        localize();
      
        //设置单位枚举值
        try
        {
            unitChoice.setEnumeratedTypeClassName("com.faw_qm.part.util.Unit");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //如果当前系统是标准版,则视图无效，项目组和生命周期失效，
        if(PART_CLIENT == "A")
        {
            viewChoice.setEnabled(false);
            lifeCycleInfoPanel.setEnabled(false);
        }
        //如果当前系统是增强版，则视图有效，项目组和生命周期失效，
        if(PART_CLIENT == "B")
        {
            viewChoice.setEnabled(true);
            lifeCycleInfoPanel.setEnabled(false);
        }
        //如果当前系统是企业版，视图有效,项目组和生命周期有效，
        if(PART_CLIENT == "C")
        {
            viewChoice.setEnabled(true);
            lifeCycleInfoPanel.setEnabled(true);
        }
    }
    /**
     * 资源信息本地化。
     */
    protected void localize()
    {
    	try
        {
            versionJLabel.setText(getLabelsRB().getString("revisionLbl"));
            viewJLabel.setText(getLabelsRB().getString("viewLbl"));
            unitJLabel.setText(getLabelsRB().getString("unitJLabel"));
            folderPanel.setFolderJLabelName(getLabelsRB().getString(
                    "locationLbl"));
            folderPanel.setBrowseJButtonName(getLabelsRB().getString(
                    "browseJButton"));
            folderPanel.setButtonMnemonic(70);
            folderPanel.setLabelModel(false);
            
            viewStateLabel.setText(getLabelsRB().getString("lifeCycleState"));
           
            //liyz add 创建者和创建时间标签
            createUserJLabel.setText(getLabelsRB().getString("createUserJLabel"));
            updateUserJLabel.setText(getLabelsRB().getString("updateUserJLabel"));
            createDateJLabel.setText(getLabelsRB().getString("createDateJLabel"));
            updateDateJLabel.setText(getLabelsRB().getString("updateDateJLabel"));//end
            //liyz add 工作状态标签
            workableStateJLabel.setText(getLabelsRB().getString("workableStateJLabel"));//end
            
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }

    /**
     * 使界面属于创建零部件状态。
     */
    protected void enableCreateFields()
    {
        PartDebug.debug("enableCreateFields() begin....", this,
                PartDebug.PART_CLIENT);
        //使版本标签不可见
        versionJLabel.setForeground(getBackground());
        //使folderPanel处于可编辑状态
        folderPanel.setLabelModel(false);
        //使lifeCycleInfoPanel处于创建模式
        if(PART_CLIENT.equals("C"))
        {
            lifeCycleInfoPanel.setMode(LifeCyclePanel.CREATE_MODE);
            lifeCycleStatePanel.setVisible(false);
            add(lifeCycleInfoPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 7, 0, 15), 0, 0));
        }
        try
        {
            beanAttributesPanel.setAttributes(new String[]{"Number", "Name",
                    "Source", "Type"});
            beanAttributesPanel.setLabels(new String[]{
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("numberLbl"),
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("nameLbl"),
                    getLabelsRB().getString("sourceLbl"),
                    getLabelsRB().getString("typeLbl")});
            beanAttributesPanel.setEdits(new String[]{"true", "true", "true",
                    "true"});
        }
        catch (QMPropertyException qmpropertyexception)
        {
            qmpropertyexception.printStackTrace();
        }
        beanAttributesPanel.initializeHelp();
       
        PartDebug.debug("enableCreateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    /**
     * 使界面处于查看状态(空方法)。
     */
    protected void enableViewFields()
    {
        if(mainPart)
        {
            PartDebug.debug("enableUpdateFields() begin....", this,
                    PartDebug.PART_CLIENT);
            //使folderPanel处于查看模式
            folderPanel.setLabelModel(true);
            folderPanel.getFolderPanelLabel();
            folderPanel.setEnabled(false);
            //使unitChoice和viewChoice以标签出现(更换组件，重新布局)
            unitChoice.setEnabled(false);
            viewChoice.setEnabled(false);
            changeLabelJPanel.remove(unitChoice);
            changeLabelJPanel.remove(viewChoice);
            changeLabelJPanel.setEnabled(false);
            viewDisplayJLabel.setSize(100, 22);
            viewDisplayJLabel.setEnabled(true);
            unitDisplayJLabel.setSize(100, 22);
            unitDisplayJLabel.setEnabled(true);
            displayVersionJLabel.setSize(100, 22);
            displayVersionJLabel.setEnabled(true);
            //CCBegin by liunan 2008-07-24
            colorFlagCheckBox.setEnabled(false);
            //CCEnd by liunan 2008-07-24
            
            viewStateValue.setSize(100, 22);
            viewStateValue.setEnabled(true);
            //liyz add 创建者名字和创建日期
            createUserValue.setSize(100, 22);
            createUserValue.setEnabled(true);
            updateUserValue.setSize(100, 22);
            updateUserValue.setEnabled(true);
            createDateValue.setSize(100, 22);
            createDateValue.setEnabled(true);
            updateDateValue.setSize(100, 22);
            updateDateValue.setEnabled(true);//end
            //liyz add 工作状态
            workableStateValue.setSize(100, 22);
            workableStateValue.setEnabled(true);//end
           
            changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(viewDisplayJLabel, new GridBagConstraints(1,
                    0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(unitDisplayJLabel, new GridBagConstraints(1,
                    1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 2,
                    1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(
                    1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            //CCBegin by liunan 2008-07-24
            changeLabelJPanel.add(colorFlag,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
            changeLabelJPanel.add(colorFlagCheckBox,
                                  new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
            //CCEnd by liunan 2008-07-24
//            attributeJPanel.add(changeLabelJPanel, new GridBagConstraints(0, 1,
//                    2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
//                    GridBagConstraints.BOTH, new Insets(0, 28, 0, 15), -1, 0));//2,10,38,15
            //liyz 重新布局
            //CCBegin by leixiao 2009-12-25 new Insets(5, 20, 0, 15)->new Insets(5, 0, 0, 15)
            add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 15), -1, 0));
          //CCEnd by leixiao 2009-12-25 
            //add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 15), -1, 0));
            add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 15), -1, 0));
            add(dateJPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
            add(workableStateJPanel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));//end
            if(PART_CLIENT.equals("C"))
            {
                //使lifeCycleInfoPanel处于查看模式
                lifeCycleInfoPanel.setMode(3);
                lifeCycleStatePanel.setVisible(true);
            }
           
            try
            {
                beanAttributesPanel.setAttributes(new String[]{"Number",
                        "Name", "Source", "Type"});
                beanAttributesPanel.setLabels(new String[]{
                        getLabelsRB().getString("required")
                                + getLabelsRB().getString("numberLbl"),
                        getLabelsRB().getString("required")
                                + getLabelsRB().getString("nameLbl"),
                        getLabelsRB().getString("sourceLbl"),
                        getLabelsRB().getString("typeLbl")});
                beanAttributesPanel.setEdits(new String[]{"false", "false",
                        "false", "false"});
            }
            catch (QMPropertyException qmpropertyexception)
            {
                qmpropertyexception.printStackTrace();
            }
            beanAttributesPanel.initializeHelp();
            PartDebug.debug("enableUpdateFields() end...return is void", this,
                    PartDebug.PART_CLIENT);
        }
    }


    //CR3 Begin zhangq 修改原因：TD2515
    /**
     * 使界面处于更新零部件状态。
     */
    protected void enableUpdateFields()
    {
        PartDebug.debug("enableUpdateFields() begin....", this,
                PartDebug.PART_CLIENT);
        //使folderPanel处于查看模式
        folderPanel.setLabelModel(true);
        folderPanel.getFolderPanelLabel();
        //使unitChoice和viewChoice以标签出现(更换组件，重新布局)
        unitChoice.setEnabled(false);
        viewChoice.setEnabled(false);
        changeLabelJPanel.remove(unitChoice);
        changeLabelJPanel.remove(viewChoice);
        viewDisplayJLabel.setSize(100, 22);
        unitDisplayJLabel.setSize(100, 22);
        if(mainPart)
        {
            //由于更换了组件重新布局
            changeLabelJPanel.add(viewDisplayJLabel,
                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.HORIZONTAL, new Insets(2, 8, 3,
                                    0), 0, 0));//209
            changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//(2, 8, 2, 0)5
            changeLabelJPanel.add(unitDisplayJLabel,
                    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.HORIZONTAL, new Insets(2, 8, 3,
                                    0), 0, 0));//(2, 8, 2, 6)137
            changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//2, 8, 2, 0 5
            changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 2,
                    1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//2, 8, 2, 0 5
            changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(
                    1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//2, 8, 2, 6 137
                        //CCBegin by liunan 2008-07-24
            changeLabelJPanel.add(colorFlag,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
            changeLabelJPanel.add(colorFlagCheckBox,
                                  new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
            //CCEnd by liunan 2008-07-24
            //CCBegin by leixiao 2009-12-28 界面调整
            //liyz 重新布局
            add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 40, 0, 15), -1, 0));
           // add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 62, 0, 15), -1, 0));
          //CCEnd by leixiao 2009-12-28 
            add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 62, 0, 15), -1, 0));
            add(dateJPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 40, 0, 5), 0, 0));
            add(workableStateJPanel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 40, 0, 5), 0, 0));
        }
        else
        {
            //add 0428 在创建零部件点击保存后显示版本标签
            //使版本标签可见
            versionJLabel.setForeground(getForeground());
            //end
            //由于更换了组件重新布局
            changeLabelJPanel.add(viewDisplayJLabel, new GridBagConstraints(1,
                    1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 6), 209,
                    0));
            changeLabelJPanel.add(unitDisplayJLabel, new GridBagConstraints(1,
                    2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 6), 137,
                    0));
            changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 2, 1,
                    1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(2, 21, 2, 0), 5, 0));
            changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(2, 21, 2, 0), 5, 0));
            changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(
                    1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 6), 259,
                    0));
            changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 0,
                    1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(2, 21, 2, 0), 5, 0));
            //CCBegin by leixiao 2009-12-28 界面调整
//            add(changeLabelJPanel, new GridBagConstraints(1, 0,
//                    1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
//                    GridBagConstraints.HORIZONTAL, new Insets(0, 20, 30, 30),
//                    -1, 0));
            add(changeLabelJPanel, new GridBagConstraints(1, 0,
                    1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 20, 5, 30),
                    -1, 0));
            //CCEnd by leixiao 2009-12-28 界面调整
            add(lifeCycleStatePanel, new GridBagConstraints(1, 1,
                    1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 40, 30, 30),
                    -1, 0));
        }
        if(PART_CLIENT.equals("C"))
        {
            //使lifeCycleInfoPanel处于查看模式
            lifeCycleInfoPanel.setMode(3);
            lifeCycleStatePanel.setVisible(true);
        }      
        try
        {
            beanAttributesPanel.setEdits(new String[]{"false", "false", "true",
                    "true"});
            //CCBegin by leixiao 2009-12-10 
//            colorFlagCheckBox.setEnabled(true);
            //CCEnd by liunan 2009-12-10
        }
        catch (QMPropertyException qmpropertyexception)
        {
            qmpropertyexception.printStackTrace();
        }
        beanAttributesPanel.initializeHelp();
        PartDebug.debug("enableUpdateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }
     //CCBegin by leixiao 2009-12-16 打补丁v4r3_p001_20091215
      /**
     * 设置编号、名称、来源、类型。
     *
     */
    public void setBeanAttributes(PartItem part)//初始化的时候要设置零部件最初的来源和类型，用于判断基本属性面板是否更改。//CR5
    {
        beanAttributesPanel.setObject(part);
    }
     //CCEnd by leixiao 2009-12-16 打补丁v4r3_p001_20091215  

    public void setPartItem(PartItem part,boolean isDispalyEmpty)
    {
        PartDebug.debug("setPartItem() begin....", this,
                PartDebug.PART_CLIENT);
        beanAttributesPanel.setObject(part);
        if(mainPart)
        {
            //liyz add 创建者名字和创建日期                
            createUserValue.setText(part.getCreatedByPersonName());                
            updateUserValue.setText(part.getModifiedByPersonName());
            if(part.getCreationDate()!=null)
            createDateValue.setText(part.getCreationDate().toString());
            else{
                if(isDispalyEmpty){
                    createDateValue.setText("");
                }
            }
            if(part.getLastUpdated()!=null)
            updateDateValue.setText(part.getLastUpdated().toString());//end
            else{
                if(isDispalyEmpty){
                    updateDateValue.setText("");
                }
            }
            //liyz add 工作状态
            try {
               workableStateValue.setText(part.getStatusText());
           } catch (QMException e) {
               e.printStackTrace();
           }//end
        }
        
        String version = part.getIterationID();
        displayVersionJLabel.setText(version);
        viewDisplayJLabel.setText(part.getViewName());
        unitChoice.setSelectedEnumeratedType(part.getDefaultUnit());
                    //CCBegin by liunan 2008-07-24
            if(part.getPart().getColorFlag())
            {
              colorFlagCheckBox.setSelected(true);
            }
            else
            {
                colorFlagCheckBox.setSelected(false);
            }
            //viewChoice.setSelectedView(part.getView());
            //CCEnd by liunan 2008-07-24
        if(isDispalyEmpty){
            unitDisplayJLabel.setText("");
        }
        else if(part.getPart().getPartNumber() != null)
        {
            unitDisplayJLabel.setText(part.getDefaultUnit().getDisplay());
        }
        try
        {
            if(isDispalyEmpty){
                folderPanel.setLabelText("");
            }
            else{
                folderPanel.setLabelText(part.getLocation());
            }
              
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(getParentFrame(), ex
                    .getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
            ex.printStackTrace();
        }
        if(PART_CLIENT.equals("C"))
        {
            viewChoice.setEnabled(false);
            unitChoice.setEnabled(false);
            lifeCycleInfoPanel.setObject((LifeCycleManagedIfc) part
                    .getObject());
            viewStateValue.setText(part.getState());
            viewChoice.setEnabled(true);
            unitChoice.setEnabled(true);
            beanAttributesPanel.setEnabled(true);
            lifeCycleInfoPanel.setEnabled(true);
            partChangePanel.setPartItem(part);
           
        }            
      
        PartDebug.debug("setPartItem() end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    
    /**
     * 设置 PartItem 这是一个回叫事务，被内部线程调用。
     * @param part PartItem
     */
    public void setPartItem(PartItem part)
    {
        setPartItem(part,false);
    }
    //CR3 End zhangq
    /** CR2 Begin
     * 将基本属性面板上的所有显示信息清空
     * 
     */  
    public void   clearBaseAttributeJPanel()
    {
    	createUserValue.setText(null);        
        updateUserValue.setText(null);
        createDateValue.setText(null);
        updateDateValue.setText(null);
        workableStateValue.setText(null);
        displayVersionJLabel.setText(null);
        viewDisplayJLabel.setText(null);
        unitChoice.setSelectedEnumeratedType(null);
        unitDisplayJLabel.setText(null);
        folderPanel.setLabelText("");
        viewStateValue.setText(null);
        lifeCycleInfoPanel.clear();
    }   
//  CR2 End   
    /**
     * 检验是否已指定资料夹。
     * @return 如果已指定资料夹路径，则返回资料夹。
     * @throws QMRemoteException
     */
    protected SubFolderInfo checkFolderLocation() throws QMRemoteException
    {
        PartDebug.debug("checkFolderLocation() begin ....", this,
                PartDebug.PART_CLIENT);
        String location = "";
        SubFolderInfo folderInfo = null;
        //获得资料夹路径
        location = folderPanel.getFolderLocation();
        if(location != null && location.length() != 0)
        {
            //调用资料夹服务，根据获得的资料夹路径获得资料夹
            Class[] paraClass = {String.class};
            Object[] objs = {location};
            folderInfo = (SubFolderInfo) IBAUtility.invokeServiceMethod(
                    "FolderService", "getFolder", paraClass, objs);
            //修改1 begin ，2007-10-29 By muyp
            //取其有效路径，屏蔽不存在的资料夹的情况
            folderPanel.setLabelText(folderInfo.getPath());
            //修改1 end
        }
        PartDebug.debug("FolderService.getFolder(String)...return "
                + folderInfo, this, PartDebug.PART_CLIENT);
        if(folderInfo != null)
        {
            //调用资料夹服务，判断指定的文件夹是否是个人文件夹
            Class[] paraClass2 = {FolderIfc.class};
            Object[] objs2 = {folderInfo};
            Boolean flag1 = null;
            try
            {
                flag1 = (Boolean) IBAUtility.invokeServiceMethod(
                        "FolderService", "isPersonalFolder", paraClass2, objs2);
            }
            catch (QMRemoteException ex)
            {
                String title = getLocalizedMessage(
                        PartDesignViewRB.LOCAL_NOT_VALID, null);
                JOptionPane.showMessageDialog(getParentFrame(), ex
                        .getClientMessage(), title,
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            boolean flag = false;
            if(flag1 != null)
            {
                flag = flag1.booleanValue();
            }
            PartDebug.debug("FolderService.isPersonalFolder() end ... return "
                    + flag, this, PartDebug.PART_CLIENT);
            if(!flag)
            {
                //抛出异常信息：所指定的资料夹不是个人文件夹
                throw new QMRemoteException(
                        getLocalizedValue(PartDesignViewRB.LOCAL_NOT_CAB));
            }
        }
        if(folderInfo != null&&getPartItem()!=null)
        {
            getPartItem().setFolder(folderInfo);
        }
        PartDebug.debug("checkFolderLocation() end ... return " + folderInfo,
                this, PartDebug.PART_CLIENT);
        return folderInfo;
    }

    
    /**
     * 在窗口中清除零部件，将界面设置为最开始的界面。
     */
    public void clear()
    {
       
        try
        {
            setPartItem(new PartItem());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        unitDisplayJLabel.setText("");
        viewDisplayJLabel.setText("");
        lifeCycleInfoPanel.setMode(3);
        viewStateValue.setText("");
        folderPanel.setLabelText("");
    }
    
    
    /**
     * 设置编号、名称、来源和类型的值。
     * @param part PartItem 将要显示的零部件。
     */
    public void setBeanAttribute(PartItem part)
    {
        beanAttributesPanel.setObject(part);
        try
        {
            beanAttributesPanel.setAttributes(new String[]{"Number", "Name",
                    "Source", "Type"});
            beanAttributesPanel.setLabels(new String[]{
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("numberLbl"),
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("nameLbl"),
                    getLabelsRB().getString("sourceLbl"),
                    getLabelsRB().getString("typeLbl")});
            beanAttributesPanel.setEdits(new String[]{"false", "false",
                    "false", "false"});
        }
        catch (QMPropertyException qmpropertyexception)
        {
            qmpropertyexception.printStackTrace();
        }
        beanAttributesPanel.initializeHelp();
    }
    
    public boolean ischange(PartItem partItem)
    {
        /**零部件是否改变的标志*/
        boolean isChange = false;
        String source = beanAttributesPanel.getSource();
        String type = beanAttributesPanel.getType();
        /**首先检查来源和类型是否改变*/
        String sr = partItem.getSource().getDisplay();
        String st = partItem.getType().getDisplay();
        if(!sr.equals(source) || !st.equals(type))
        {
            isChange = true;
          
        }
        return isChange;
            
        }
    
    /**
     * 运行线程，保存被修改的零部件。
     * 点“保存”按钮或在提示保存对话框中选择保存，参数isDialog区分这两种情况。
     * @param isDialog boolean
     */
    protected PartItem save( PartItem partItem,int model)
    throws QMException
    {    	
    	  beanAttributesPanel.setObjectAttributeValues();
          //CCBegin by leixiao 2009-12-10颜色件标识
    	 // System.out.println("颜色标识－－－－－"+colorFlagCheckBox.isSelected());
    	  partItem.getPart().setColorFlag(colorFlagCheckBox.isSelected());
    	  //CCEnd by liunan 2008-07-24
    	  if(model == 1)
          {
    		  
              ViewObjectInfo view = (ViewObjectInfo) viewChoice
                      .getSelectedView();
              if(view==null)
              {
              	return partItem;
//   	           setButtonWhenSave(true);
//   	            return;
              }
              partItem.setView(view);
              Unit unit = (Unit) unitChoice.getSelectedEnumeratedType();
              partItem.setDefaultUnit(unit);
              String folder = folderPanel.getFolderLocation();
              partItem.setLocation(folder);
              if(PART_CLIENT == "C")
              {
//            	  if(getPartItem()!=null)
//            	  {
//                  LifeCycleManagedIfc lifeCycleManagedIfc = (LifeCycleManagedIfc) getPartItem().getObject();
            	  //liyz modify 创建零部件时不能保存生命周期和工作组的问题 2009/05/25
            	  LifeCycleManagedIfc lifeCycleManagedIfc = (LifeCycleManagedIfc) partItem.getObject();
                  LifeCycleManagedIfc returnIfc = lifeCycleInfoPanel
                          .assign(lifeCycleManagedIfc);
                  partItem.setObject(returnIfc);
//            	  }
              }
          }
       return partItem;
    }
    public ViewObjectInfo getViewObject()
    {
    	return (ViewObjectInfo) viewChoice
        .getSelectedView();
    }
     //CCBegin by leixiao 2009-12-16 打补丁v4r3_p001_20091215
        /**
     * 设置Tab页显示标志
     * @param isShown
     */
    public void setIsShown(boolean isShown)//CR3
    {
        this.isShown = isShown;
    }
    /**
     * 获取Tab页显示标志
     * @return
     */
    public boolean getIsShown()
    {
        return isShown;
    }
     //CCEnd by leixiao 2009-12-16 打补丁v4r3_p001_20091215
}
