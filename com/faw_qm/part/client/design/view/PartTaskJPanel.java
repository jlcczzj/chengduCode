/** 生成程序PartTaskJPanel.java	1.1  2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/02 谢斌 原因:在零部件更新状态时构造UsesInterfaceList，其它状态不构造
 *                     方案:优化初始化右侧面板
 *                     备注:解放v3r11-展开零部件树节点性能优化
 * CR2 2009/04/02 谢斌 原因:发现多次调用相同方法，可以复用
 *                     方案:去掉重复的方法
 *                     备注:解放v3r11-展开零部件树节点性能优化
 * CR3 2009/06/19 王亮  TD：2443在产品信息管理中，选中零部件，在右侧事物特性TAB页上，“分类”按钮和“删除分类”按钮不显示
 *                      方案：重绘事务特性TAB页。
 *                      
 * CR4 2009/06/21 谢斌 原因：创建一个来自部件的广义部件，添加搜索部件的搜索条件，输入状态为中文，不能正确添加。TD-2330
 *                    方案：由于将dialog的父组件设置为根Frame，导致使用中文输入法后该dialog被其父窗口覆盖的问题；现在将dialog的父组件设置为父窗口即可解决。
 * CR5 2009/07/02 谢斌 原因：正常操作零部件，切换不同零部件事物特性TAB页，分类和删除按钮还是重复出现。TD-2443
 *                    方案：该问题是树节点操作过快，组件被重复初始化并添加。先将已有的组件移除再添加即可。
 * CR6 2009/07/06 马辉 原因:TD2543在产品信息管理器中菜单工艺和界面右侧面板底下的还原按钮快捷键设置重复
 *                     方案:右侧面板底下的还原按钮快捷键设置为AIT+Z 
 * CR7 2009/07/20 王亮 原因：产品信息管理器右侧TAB页描述文档和参考文档与更新界面的TAB页描述关系和参考关系不一致。
 *                    方案：将更新界面的TAB页描述关系和参考关系改为描述文档和参考文档。
 * CR8 2009/11/25 王亮 原因：产品信息管理器所有Tab页信息一次全都初始化。
 *                     方案：关注哪个Tab页就初始化哪个Tab页。
 * CR9 2009/11/26 王亮 原因：没有对零部件进行修改也会弹出对话框”是否保存对*零部件的修改“。   
 * SS1  增加BOM行项和子组 liuyang 2014-6-20             
 *  SS2 增加生产版本 xianglx 2014-8-12
 *  SS3 添加结构件时增加了搜索条件从视图属性中分辨是中心的件还是解放的件 guoxiaoliang 2016-4-20
 */
package com.faw_qm.part.client.design.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMStatusBar;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.csm.navigation.NavigationUtil;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.client.container.main.ClassifierControl;
import com.faw_qm.iba.client.container.main.IBAContainerEditor;
import com.faw_qm.iba.client.container.main.IBADialog;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.lite.AbstractLiteObject;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.model.PartMasterItem;
import com.faw_qm.part.client.design.model.ProgressService;
import com.faw_qm.part.client.design.model.UsageInterfaceItem;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsesInterfaceList;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.design.util.QuantityEditor;
import com.faw_qm.part.client.design.util.QuantityRenderer;
import com.faw_qm.part.client.design.util.UnitEditor;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.view.QMPartList;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartUsageList;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.units.util.MeasurementSystemDefaultView;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title: 创建（更新）零部件界面的主面板。</p>
 * <p>Description:PartTaskJPanel是一个对零部件进行操作的界面类；</p>
 * <p>实现零部件的创建、更新和查看。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 * @see UsesJPanel,DescribedByJPanel,ReferenceJPanel
 * 修改1，2007-10-29，By 穆勇鹏
 * 问题原因：创建零部件时对资料夹处输入的不存在的资料夹没有进行过滤。
 * 解决方案：在保存零部件时，直接取其有效路径，屏蔽可能出现的不存在的资料夹的情况。
 * 问题(2)20080306 张强修改 修改原因:当不保存先前修改的零部件时，描述文档面板的更改标志仍然是true（TD-1516）。
 */

public class PartTaskJPanel extends HelperPanel
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**主界面显示模式（更新模式）标记*/
    public final static int UPDATE_MODE = 0;

    /**主界面显示模式（创建模式）标记*/
    public final static int CREATE_MODE = 1;

    /**主界面显示模式（查看模式）标记*/
    public final static int VIEW_MODE = 2;

    /**用于判断是否关闭视窗*/
    protected boolean disposeWindow = false;

    /**用于判断界面模式*/
    protected boolean updateMode = false;

    protected boolean isOK = false;

    /**界面模式--查看*/
    protected int mode = VIEW_MODE;

    /**关联关系面板（使用结构），在编辑界面中使用。*/
    private UsesJPanel usesJPanel = null;

//  modify by muyp 20081118 begin
    /**关联关系面板（参考关系）*/
    private ReferenceJPanel referencesJPanel = new ReferenceJPanel(true);

    /**关联关系面板（描述关系）*/
    private DescribedByJPanel describedByJPanel = new DescribedByJPanel(true);
    //end

    /**确定按钮*/
    private JButton okJButton = new JButton();

    /**保存按钮*/
    private JButton saveJButton = new JButton();

    /**取消按钮*/
    private JButton cancelJButton = new JButton();

    /**编辑特性按钮*/
    private JButton editAttributesJButton = new JButton();

    /**零部件基本属性面板*/
    private BaseAttributeJPanel attributeJPanel ;

    /**用于放置零部件关联属性面板*/
    private JTabbedPane contentsJTabbedPane = new JTabbedPane();

    /**零部件关联属性（使用结构、参考关系、描述关系）面板*/
    private JPanel relationsJPanel = new JPanel(new GridBagLayout());

    /**放置按钮（确定、保存、取消、编辑特性）的面板*/
    private JPanel createButtonJPanel = new JPanel(new GridBagLayout());

    /**用于添加使用结构面板*/
    private JPanel addUsesJPanel = null;

    /**用于添加参考关系面板*/
    private JPanel addRefJPanel = new JPanel(new GridBagLayout());

    /**用于添加描述关系面板*/
    private JPanel addDesJPanel = new JPanel(new GridBagLayout());

    /**用于获得系统的当前版本*/
    private static String PART_CLIENT = RemoteProperty.getProperty(
            "part_client_customize_earmark", "A");
    /**状态条*/
    private QMStatusBar statusBar = new QMStatusBar();


    private IBAHolderIfc ibaHolder = null;

    /**如果是true，表示这个界面显示在产品信息管理器界面中*/
    private boolean mainPart = false;

    /**用于添加显示事物特性的面板*/
    private JPanel addEditAttributesJPanel = null;

    /**用于显示事物特性的面板*/
    private IBAContainerEditor ibaAttributesJPanel = null;

    /**用于add使用结构的面板，在右侧面板中使用。*/
    private JPanel addStructJPanel = null;

    /**按钮面板*/
    JPanel useButtonJPanel = null;

    /**添加按钮*/
    private JButton addUsageJB = null;

    /**移去按钮*/
    private JButton removeUsageJB = null;

    /**查看按钮*/
    private JButton viewUsageJB = null;

    AbstractLiteObject classificationstructdefaultview = null;

    private ClassifierControl classifiercontrol;

    private ClassifierControl classifiercontrol2;

    /**设置 PartItem的锁*/
    private Object cursorLock = new Object();

    private boolean usagechange = false;

    private boolean ibachange = false;

    private boolean basechange = false;

    private boolean descrichange = false;

    private boolean refchange = false;
    private boolean isReference=false;
  

	//显示设置按钮面板
	private JPanel partAttrSetJPanel = new JPanel();
	//显示设置按钮
	private JButton partAttrSetJButton = null;
	//工艺面板
	private TechnicsRoutePanel routeJPanel = new TechnicsRoutePanel();
	private JPanel addRouteJPanel = new JPanel(new GridBagLayout());
	private TechnicsSummaryPanel summaryJPanel = new TechnicsSummaryPanel();
	private JPanel addSummaryJPanel = new JPanel(new GridBagLayout());
	private TechnicsRegulateionPanel regulateionJPanel = new TechnicsRegulateionPanel();	
	private JPanel addRegulateionJPanel = new JPanel(new GridBagLayout());
	//tab页索引
	private int usageIndex=0;
	private int baseIndex=0;
	private int ibaIndex=0;
	private int descIndex=0;
	private int refIndex=0;
    private TabListener tablistener = new TabListener();//Tab页监听//CR8
    private boolean isCreate = false;//用于在创建和更新时能显示描述文档和参考文档。//CR8
	    
    /**
     * 构造函数。
     */
    public PartTaskJPanel()
    {
        super();
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
     * 构造函数。
     */
    public PartTaskJPanel(boolean flag)
    {
        super();
        mainPart = flag;
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
    private void jbInit() throws Exception
    {
        setLayout(new GridBagLayout());
        setSize(650, 520);
        //初始化PartAttributesPanel Bean
        attributeJPanel = new BaseAttributeJPanel(mainPart);
        attributeJPanel.setName("attributeJPanel");//CR8
        okJButton.setBounds(new Rectangle(93, 0, 65, 23));
        okJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okJButton.setMaximumSize(new Dimension(65, 23));
        okJButton.setMinimumSize(new Dimension(65, 23));
        okJButton.setPreferredSize(new Dimension(83, 23));
        okJButton.setMnemonic('Y');
        okJButton.setText("Ok");
        saveJButton.setBounds(new Rectangle(26, 0, 65, 23));
        saveJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        saveJButton.setMaximumSize(new Dimension(65, 23));
        saveJButton.setMinimumSize(new Dimension(65, 23));
        saveJButton.setOpaque(true);
        saveJButton.setPreferredSize(new Dimension(83, 23));
        saveJButton.setMnemonic('S');
        cancelJButton.setText("Cancel");
        cancelJButton.setBounds(new Rectangle(160, 0, 65, 23));
        cancelJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJButton.setMaximumSize(new Dimension(65, 23));
        cancelJButton.setMinimumSize(new Dimension(65, 23));
        cancelJButton.setPreferredSize(new Dimension(83, 23));
//CR6 Begin        
        cancelJButton.setMnemonic('Z');
        cancelJButton.setMnemonic('Z');
//CR6 End         
        editAttributesJButton.setText("Edit Attributes");
        editAttributesJButton.setBounds(new Rectangle(67, 3, 83, 22));
        editAttributesJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        editAttributesJButton.setMaximumSize(new Dimension(90, 25));
        editAttributesJButton.setMinimumSize(new Dimension(83, 25));
        editAttributesJButton.setPreferredSize(new Dimension(98, 23));
        editAttributesJButton.setMnemonic('I');
       
        //other
        statusBar.setText("  ");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.setMaximumSize(new Dimension(40, 18));
        statusBar.setMinimumSize(new Dimension(40, 18));
       
        contentsJTabbedPane.doLayout();
        contentsJTabbedPane.addChangeListener(tablistener);//CR8
        //如果是作为右侧面板。
        if(mainPart)
        {
            ibaAttributesJPanel = new IBAContainerEditor(false, true);
            addEditAttributesJPanel = new JPanel(new GridBagLayout());
            addEditAttributesJPanel.setName("addEditAttributesJPanel");//CR8
            addEditAttributesJPanel.add(ibaAttributesJPanel,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0)); 
            addStructJPanel = new JPanel(new GridBagLayout());
            viewUsageJB = new JButton();
            viewUsageJB.setBounds(new Rectangle(19, 84, 59, 23));
            viewUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
            viewUsageJB.setMaximumSize(new Dimension(65, 23));
            viewUsageJB.setMinimumSize(new Dimension(65, 23));
            viewUsageJB.setPreferredSize(new Dimension(83, 23));
            viewUsageJB.setMnemonic('V');
            viewUsageJB.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    viewUsageJButton_actionPerformed(e);
                }
            });
            addUsageJB = new JButton();
            addUsageJB.setBounds(new Rectangle(19, 0, 59, 23));
            addUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
            addUsageJB.setMaximumSize(new Dimension(65, 23));
            addUsageJB.setMinimumSize(new Dimension(65, 23));
            addUsageJB.setPreferredSize(new Dimension(83, 23));
            addUsageJB.setMnemonic('A');
            addUsageJB.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    addUsageJButton_actionPerformed(e);
                }
            });
            removeUsageJB = new JButton();
            removeUsageJB.setBounds(new Rectangle(19, 42, 59, 23));
            removeUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
            removeUsageJB.setMaximumSize(new Dimension(65, 23));
            removeUsageJB.setMinimumSize(new Dimension(65, 23));
            removeUsageJB.setPreferredSize(new Dimension(83, 23));
            removeUsageJB.setMnemonic('R');
            removeUsageJB.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    removeUsageJButton_actionPerformed(e);
                }
            });
            //liyz add
            partAttrSetJButton = new JButton();
            partAttrSetJButton.setBounds(new Rectangle(19, 126, 59, 23));
            partAttrSetJButton.setFont(new java.awt.Font("Dialog", 0, 12));
            partAttrSetJButton.setMaximumSize(new Dimension(65, 23));
            partAttrSetJButton.setMinimumSize(new Dimension(65, 23));
            partAttrSetJButton.setPreferredSize(new Dimension(83, 23));
            partAttrSetJButton.setMnemonic('P');
            partAttrSetJButton.addActionListener(new AttrSetListener("usage"));//end
            //liyz add 描述文档和参考文档中显示设置的监听
            describedByJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("description"));
            referencesJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("reference"));
            regulateionJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("regulation"));
            summaryJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("summary"));
            routeJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("route"));
            //end
            useButtonJPanel = new JPanel(new GridBagLayout());
//            useButtonJPanel.add(partAttrSetJButton, new GridBagConstraints(0, 0, 1, 1,
//                    0.0, 0.0, GridBagConstraints.CENTER,
//                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));
            useButtonJPanel.add(addUsageJB, new GridBagConstraints(1, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));
            useButtonJPanel.add(removeUsageJB, new GridBagConstraints(2, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));//(7, 0, 10, 13)
            useButtonJPanel.add(viewUsageJB, new GridBagConstraints(3, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));
            
            addStructJPanel.add(useButtonJPanel, new GridBagConstraints(0, 1,
                    2, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));//(0, 0, 0, 20)
            addDesJPanel.add(describedByJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addDesJPanel.setName("addDesJPanel");//CR8
            addRefJPanel.add(referencesJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addRefJPanel.setName("addRefJPanel");//CR8
            addRegulateionJPanel.add(regulateionJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addRegulateionJPanel.setName("addRegulateionJPanel");//CR8
            addSummaryJPanel.add(summaryJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addSummaryJPanel.setName("addSummaryJPanel");//CR8
            addRouteJPanel.add(routeJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addRouteJPanel.setName("addRouteJPanel");//CR8
            //liyz add
            partAttrSetJPanel = new JPanel(new GridBagLayout());
            addStructJPanel.add(partAttrSetJPanel, new GridBagConstraints(0, 1,
                    1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            partAttrSetJPanel.add(partAttrSetJButton, new GridBagConstraints(0, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(0, 0, 5, 15), 0, 0));
            
            
            //end           
            
            int i = 0;
//            //测试用，部署环境注释begin
//            java.util.Properties   prop=new Properties();
//            FileInputStream fis = 
//                new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//            prop.load(fis);
//            //end
            while (true)
            {

                String s = RemoteProperty.getProperty(
                        "com.faw_qm.part.manager.JTab." +
                        String.valueOf(i));            	
             
//                  String s=prop.getProperty( "com.faw_qm.part.manager.JTab." +
//                        String.valueOf(i));            
              
                if (s == null)
                {
                    break;
                }
                if(s.equals("usage"))
                {
                	contentsJTabbedPane.add(addStructJPanel, getLabelsRB().getString(
                             "usage"), i);
                	usageIndex=i;
                }                
                if(s.equals("base"))
                {
			        contentsJTabbedPane.add(attributeJPanel, getLabelsRB().getString(
			                "attribute"),i);
                    baseIndex=i;
                }
                if(s.equals("iba"))
                {
				    contentsJTabbedPane.add(addEditAttributesJPanel, getLabelsRB()
				                .getString("editAttributes"), i);
                    ibaIndex=i;
                }
                if(s.equals("description"))
                {
                	contentsJTabbedPane.add(addDesJPanel, getLabelsRB().getString(
            		"describedDoc"),i);
                	descIndex=i;
                }                	
                if(s.equals("reference"))
                {
                	contentsJTabbedPane.add(addRefJPanel, getLabelsRB().getString(
            		"referenceDoc"),i);
                	refIndex=i;
                }                	
                if(s.equals("capproute"))
                	contentsJTabbedPane.add(addRouteJPanel, getLabelsRB().getString(
                    "cappRoute"),i);
                if(s.equals("cappsummary"))
                	contentsJTabbedPane.add(addSummaryJPanel, getLabelsRB().getString(
                    "cappSummary"),i);
                if(s.equals("cappregulation"))
                	contentsJTabbedPane.add(addRegulateionJPanel, getLabelsRB().getString(
                    "cappRegulation"),i);
                i++;

            }
            
          
            createButtonJPanel.add(new JLabel(),
                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                            GridBagConstraints.SOUTHEAST,
                            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
                                    0), 0, 0));//(0, 0, 0, 13)21, 1
            createButtonJPanel.add(saveJButton, new GridBagConstraints(2, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));//(0, 0, 0, 13)21, 1
            createButtonJPanel.add(cancelJButton, new GridBagConstraints(3, 0,
                    1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));//(0, 0, 0, 13)21, 1
            add(createButtonJPanel,
                    new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0,
                            GridBagConstraints.SOUTHEAST,
                            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
                                    3), 0, 0));//CENTER BOTH(5, 0, 5, 15), 51, 0
            //修改，把标签页设置为主面板
            add(contentsJTabbedPane, new GridBagConstraints(0, 1, 2, 1, 1.0,
                    1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        else
        {
        	  /**关联关系面板（参考关系）*/
            referencesJPanel = new ReferenceJPanel(false);

            /**关联关系面板（描述关系）*/
            describedByJPanel = new DescribedByJPanel(false);
            usesJPanel = new UsesJPanel();
            addUsesJPanel = new JPanel(new GridBagLayout());
            addUsesJPanel
                    .add(usesJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0,
                            1.0, GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(-3, -10, -5, -3), 0, 0));
            addDesJPanel.add(describedByJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));//(-3, -10, -5, 2)
            addRefJPanel.add(referencesJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));//(-3, -10, -5, -10)
            contentsJTabbedPane.add(addUsesJPanel, getLabelsRB().getString(
                    "usage"));
            contentsJTabbedPane.add(addDesJPanel, getLabelsRB().getString(
                    "describedDoc"));//CR7
            contentsJTabbedPane.add(addRefJPanel, getLabelsRB().getString(
                    "referenceDoc"));//CR7
            createButtonJPanel.add(saveJButton, new GridBagConstraints(1, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 21,
                    1));
            createButtonJPanel.add(cancelJButton, new GridBagConstraints(3, 0,
                    1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                    GridBagConstraints.BOTH, new Insets(0, 6, 0, 0), 21, 1));
            createButtonJPanel.add(okJButton, new GridBagConstraints(2, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 6, 0, 0), 21, 1));
            /**用于标记“是否等待”，控制线程的运行秩序，防止线程之间出现调用冲突*/
            createButtonJPanel.add(new JLabel("    "),
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                            GridBagConstraints.EAST,
                            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
                                    0), 0, 0));
          
            relationsJPanel.add(contentsJTabbedPane, new GridBagConstraints(0,
                    0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 6, 0, 13), 0, 0));
           
            add(createButtonJPanel, new GridBagConstraints(1, 2, 1, 1, 0.0,
                    0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                    new Insets(5, 0, 5, 15), 51, 0));
            add(attributeJPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 2));
            add(relationsJPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(statusBar, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 520, 0));
        }
        //注册监听
        PTJAction ptjAction = new PTJAction();
        saveJButton.addActionListener(ptjAction);
        cancelJButton.addActionListener(ptjAction);
        okJButton.addActionListener(ptjAction);
        editAttributesJButton.addActionListener(ptjAction);
       
        initialize();
    }

    //liyz add 显示设置按钮的监听
    void partAttrSetJButton_actionPerformed(ActionEvent event,String tab)
    {
    	 PartItem part = getPartItem();    	
    	 boolean flag = false;
    	 if(part==null)
    	 {
    		 JFrame parentFrame = getFrame();
    	     PartAttrSetJDialog dialog = new PartAttrSetJDialog(tab,parentFrame);
    	     PartScreenParameter.setLocationCenter(dialog);
    	     dialog.setVisible(true); 
    	 }
    	 else 
    		 {    		
    		 boolean isChange = isChange();
             if(part != null && part.getPart().getPartNumber() != null && partData != null && dtm != null)
             {                
                 //通过标识工厂获得与给定值对象对应的显示标识对象。
                 DisplayIdentity displayidentity = IdentityFactory
                         .getDisplayIdentity(part.getPart());
                 //获得对象类型 + 标识
                 String s = displayidentity.getLocalizedMessage(null);
                 Object[] params = {s};
                 String confirmExitMsg = QMMessage.getLocalizedMessage(RESOURCE,
                         "confirmsave", params);
                 String warningDialogTitle = QMMessage.getLocalizedMessage(
                         RESOURCE, "errorTitle", null);
                 /**如果改变，提示是否保存*/
                 if(isChange)
                 {
                     int i = DialogFactory.showYesNoDialog(this, confirmExitMsg,
                             warningDialogTitle);
                     //问题(2)20080306 zhangq begin:当不保存先前修改的零部件时，
                     //描述文档面板的更改标志仍然是true（TD-1516）。
                     if(i == DialogFactory.YES_OPTION)
                     {
                         flag = true;
                         saveInThread(isChange);
                         //describedByJPanel.setChanged(false);
                     }
                     describedByJPanel.setChanged(false);
                     //问题(2)20080306 zhangq end
                 }
             }
    		 
    		 if(flag)
    		 {
    			 JFrame parentFrame = getFrame();
    		     PartAttrSetJDialog dialog = new PartAttrSetJDialog(tab,parentFrame);
    		     PartScreenParameter.setLocationCenter(dialog);
    		     dialog.setVisible(true); 
    		 }    	
    		else if(!isChange)
    		{
    			JFrame parentFrame = getFrame();
    	    	PartAttrSetJDialog dialog = new PartAttrSetJDialog(tab,parentFrame);
    	    	PartScreenParameter.setLocationCenter(dialog);
    	    	dialog.setVisible(true);
    		}
    	}
    }
    /**
     * 获得父窗格。
     * @return JFrame 父窗口。
     */
    protected JFrame getFrame()
    {
        Component parent = getParent();        
        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        JFrame returnJFrame = (JFrame) parent;
        return returnJFrame;
    }
    //end

    /**
     * 获得界面模式
     * @return 创建模式或更新模式
     */
    public int getUpdateMode()
    {
        return mode;
    }

    /**
     * 设置界面模式（创建、更新或查看）。
     * @param aMode 新界面模式。
     * @exception java.beans.PropertyVetoException 如果模式Mode无效，则抛出异常。
     */
    public void setUpdateMode(int aMode) throws PropertyVetoException
    {
        PartDebug.debug("setUpdateMode(int aMode) begin ....", this,
                PartDebug.PART_CLIENT);
        if((aMode == PartTaskJPanel.UPDATE_MODE) || (aMode == PartTaskJPanel.CREATE_MODE)
                || (aMode == PartTaskJPanel.VIEW_MODE))
        {
            mode = aMode;
        }
        else
        {
            //信息：无效模式
            throw (new PropertyVetoException(
                    getLocalizedValue(PartDesignViewRB.INVALID_MODE),
                    new PropertyChangeEvent(this, "mode", new Integer(
                            getUpdateMode()), new Integer(aMode))));
        }
        switch (aMode)
        {
            case UPDATE_MODE:
            { //更新模式
                enableUpdateFields();
                updateMode = true;
                break;
            }

            case CREATE_MODE:
            { //创建模式
                updateMode = true;
                enableCreateFields();
                try
                {
                    isCreate = true;//CR8
                    setPartItem(new PartItem());
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "errorTitle", null);
                    JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                            title, JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
            }

            case VIEW_MODE:
            { //查看模式
                updateMode = false;
                enableViewFields();
                break;
            }
        }
        PartDebug.debug("setUpdateMode(int aMode) end ....return is void",
                this, PartDebug.PART_CLIENT);
    }


    /**
     * 界面刷新通知。
     */
    public void addNotify()
    {
        super.addNotify();
    }


    /**
     * 检验必填区域是否已有有效值。
     * @return  如果必填区域已有有效值，则返回为真。
     */
    protected boolean checkRequiredFields()
    {
        PartDebug.debug("checkRequiredFields() begin....", this,
                PartDebug.PART_CLIENT);
        isOK = true;
        String message = "fell through ";
        String title = "";
        if((getUpdateMode() == CREATE_MODE))
        { //创建模式
            //检验编号是否为空,并且不含有通配符
            try
            {
                //2007.05.29 whj
                // 检验编号是否为空，并且不含有通配符
            	   //	CCBegin by leixiao 2010-9-28  放开对编号名称的字数限制,限制成与数据库一样200
                TextValidCheck validate = new TextValidCheck(getLabelsRB()
                        .getString("numberLbl"), 1, 200);
                validate.check(getPartItem().getNumber().trim(), true);
                //              检验名称是否为空，并且不含有通配符
                TextValidCheck validatename = new TextValidCheck(getLabelsRB()
                        .getString("nameLbl"), 1, 200);
                validatename.check(getPartItem().getName().trim(), true);
                //	CCEnd by leixiao 2010-9-28  放开对编号名称的字数限制,限制成与数据库一样200
//            }
//            catch (QMRemoteException re)
//            {
//                message = re.getClientMessage();
//                isOK = false;
//            }
//            try
//            {
                //检验资料夹是否为空
                if(attributeJPanel.checkFolderLocation() == null)
                {
                    message = getLocalizedValue(PartDesignViewRB.NO_LOCAL_ENTER);
                    isOK = false;
                }
            }
            catch (QMRemoteException qre)
            {
                //显示信息：所指定的资料夹不是个人资料夹
//                title = getLocalizedValue("errorTitle");
//                JOptionPane.showMessageDialog(getParentFrame(), qre
//                        .getClientMessage(), title,
//                        JOptionPane.INFORMATION_MESSAGE);
                qre.printStackTrace();
                message=qre.getClientMessage();
                isOK = false;
            }
        }
        if(!isOK)
        {
            //显示信息：缺少必需的字段
            title = getLocalizedValue("errorTitle");
            JOptionPane.showMessageDialog(getParentFrame(), message, title,
                    JOptionPane.ERROR_MESSAGE);
        }
        PartDebug.debug("checkRequiredFields() end...return " + isOK, this,
                PartDebug.PART_CLIENT);
        return isOK;
    }

    /**
     * 使界面属于创建零部件状态。
     */
    protected void enableCreateFields()
    {
        PartDebug.debug("enableCreateFields() begin....", this,
                PartDebug.PART_CLIENT);
        attributeJPanel.enableCreateFields();
        if(usesJPanel != null)
        {
            usesJPanel.setUpdateMode(true);
        }
        if(referencesJPanel != null)
        {
            referencesJPanel.setUpdateMode(true);
        }
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
            attributeJPanel.enableViewFields();
           
            if(mainPart)
            {
                viewUsageJB.setEnabled(false);
                addUsageJB.setEnabled(false);
                removeUsageJB.setEnabled(false);
                //liyz add
                //partAttrSetJButton.setEnabled(false);//end
            }
            setButtonWhenSave(false);
            structList.setCellEditable(false);
          
            if(usesJPanel != null)
            {
                usesJPanel.setUpdateMode(false);
            }
            if(referencesJPanel != null)
            {
                referencesJPanel.setUpdateMode(false);
            }
            if(describedByJPanel != null)
            {
                describedByJPanel.setUpdateMode(false);
            }
            if(ibaAttributesJPanel != null)
            {
                ibaAttributesJPanel.updateEditor(null, false);
                ibaAttributesJPanel.setUpdateMode(false);
            }
           
            PartDebug.debug("enableUpdateFields() end...return is void", this,
                    PartDebug.PART_CLIENT);
        }
    }


    /**
     * 使界面处于更新零部件状态。
     */
    protected void enableUpdateFields()
    {
        PartDebug.debug("enableUpdateFields() begin....", this,
                PartDebug.PART_CLIENT);
        attributeJPanel.enableUpdateFields();
        if(mainPart)
        {
            viewUsageJB.setEnabled(false);
            
            QMProductManager explor = ((QMProductManagerJFrame) this
                    .getParentFrame()).getMyExplorer();
            /**
             * set for capp 屏蔽调补充需求，2007.06.29 王海军
             * 
             */
            referencesJPanel.setManager(explor);
            describedByJPanel.setManager(explor);
            if(explor.getFromCapp())
            {
            	 addUsageJB.setEnabled(false);
            }
            //update whj 07/12/6
//            else
//           addUsageJB.setEnabled(true);
//          update whj 07/12/6
            removeUsageJB.setEnabled(false);
            usesMultiList_itemStateChanged(null);
            setButtonWhenSave(true);
          
            if(quantityEditor != null)
            {
                quantityEditor.cancelCellEditing();
            }
            if(unitEditor != null)
            {
                unitEditor.cancelCellEditing();
            }
            //CCBegin SS1
            
            //CCEnd SS1
            if(describedByJPanel != null)
            {
                describedByJPanel.setUpdateMode(true);
            }
            if(ibaAttributesJPanel != null)
            {
                ibaAttributesJPanel.updateEditor(null, true);
                ibaAttributesJPanel.setUpdateMode(true);
            }
        }
       
        if(usesJPanel != null)
        {
            usesJPanel.setUpdateMode(true);
        }
        if(referencesJPanel != null)
        {
            referencesJPanel.setUpdateMode(true);
        }
      
        PartDebug.debug("enableUpdateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }

    //CR7 Begin zhangq 修改原因：TD2515
    public void setPartItem(PartItem part,boolean isDispalyEmpty){
        synchronized (cursorLock)
        {
            PartDebug.debug("setPartItem() begin....", this,
                    PartDebug.PART_CLIENT);
            super.setPartItem(part);
            attributeJPanel.setBeanAttributes(part);//设置编号、名称、来源和类型。//CR8
            JPanel panel = (JPanel)contentsJTabbedPane.getSelectedComponent();//CR8
            if(panel.getName() == "attributeJPanel" || isCreate == true)
            {
                attributeJPanel.setPartItem(part, isDispalyEmpty);
                attributeJPanel.setIsShown(true);
            }
            if(mainPart)
            {
                //如果选择的是树的根节点，则设置面板为查看模式。
                if(part.getPart().getPartNumber() == null)
                    try
                    {
                        setUpdateMode(PartTaskJPanel.VIEW_MODE);
                    }
                    catch (PropertyVetoException e)
                    {
                        e.printStackTrace();
                    }
                if(panel.getName() == "addEditAttributesJPanel" || isCreate == true)//CR8
                {
                    IBAHolderIfc ibaHolder = (IBAHolderIfc) part.getPart();
                    if(ibaHolder.getAttributeContainer() == null)
                    {
                        try
                        {
                            //要调用的方法中的参数的类的集合
                            Class[] classes = {IBAHolderIfc.class, Object.class,
                                    Locale.class,
                                    MeasurementSystemDefaultView.class};
                            //要调用的方法中的参数的集合
                            Object[] objects = {ibaHolder, null,
                                    RemoteProperty.getVersionLocale(), null};
                            //向服务发出请求,取得结果集合（用户值对象UserInfo的集合）
                            ibaHolder = (IBAHolderIfc) IBAUtility
                                    .invokeServiceMethod("IBAValueService",
                                            "refreshAttributeContainer", classes,
                                            objects);
                        }
                        catch (QMRemoteException remoteexception)
                        {
                            remoteexception.printStackTrace();
                            ibaHolder
                                    .setAttributeContainer(new DefaultAttributeContainer());
                        }
                    }
                    
                    ibaAttributesJPanel.updateEditor(ibaHolder, mode == VIEW_MODE);
                    
                    addClassificationButtons();
                    refreshClassificationControl();
                    
                    ibaAttributesJPanel.setUpdateMode(updateMode);
                    ibaAttributesJPanel.setIsShown(true);//CR8
                }
                //获得当前PartItem 使用的零部件的列表。
                //性能瓶颈。
                if (updateMode) {//Begin CR1
                    usesList = part.getUsesInterfaceList();

                    //对属性进行初始化,更新容器
                    usesList.startFrame();
                    //设置默认选中第一行                
                    String[] usesMultiList = usesList.toMultiList();//Begin:CR2 
                    if (structList != null && usesMultiList != null && usesMultiList.length != 0)//End:CR2
                    {
                        usesMultiList_itemStateChanged(null);
                    }

                    if (quantityEditor != null) {
                        quantityEditor.cancelCellEditing();
                    }
                    if (unitEditor != null) {
                        unitEditor.cancelCellEditing();
                    }
                    if(structList != null)
                    {
                        JTable table1 = structList.getTable();
                        DefaultTableModel dtm1 = (DefaultTableModel) table1
                                .getModel();
                        int rowCount = dtm1.getRowCount();
                        //CCBegin SS1
                        //CCBegin SS2
                       // partData = new Object[rowCount][3];
                        //partData = new Object[rowCount][5];
                        partData = new Object[rowCount][6];
                        //CCEnd SS2
                        //CCEnd SS1
                        for (int row = 0; row < rowCount; row++)
                        {
                            //保存旧的数据，在判断时使用。
                            //保存使用结构中的数量、单位和子部件的BsoID。                      
//                            partData[row][0] = dtm1.getValueAt(row, 4);
//                            partData[row][1] = dtm1.getValueAt(row, 5);
//                            partData[row][2] = dtm1.getValueAt(row, 9);
                            //liyz add
                            PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                            String[] quanHead = structList.getHeadings();
                            int quanCol = 0;
                            for(int m=0;m<quanHead.length;m++)
                            {
                                String s = quanHead[m];
                                if(s.equals(quantity.getDisplay()))
                                {
                                    quanCol = m;
                                }
                            }
                            PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                            String[] unitHead = structList.getHeadings();
                            int unitCol = 0;
                            for(int n=0;n<unitHead.length;n++)
                            {
                                String s =unitHead[n];
                                if(s.equals(unit.getDisplay()))
                                {
                                    unitCol = n;
                                }
                            }
                            //CCBegin SS1
                            PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                            String[] subHead=structList.getHeadings();
                            int sunCol = 0;
                            for(int m=0;m<subHead.length;m++)
            	            {
            	            	String s =subHead[m];
            	               if(s.equals(subUnitNumber.getDisplay()))
            	            	{
            	            		sunCol=m;
            	            	}  
            	            	
            	            } 
                            PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                            String[] bomHead=structList.getHeadings();
            	            int bomCol = 0;
            	            for(int m=0;m<bomHead.length;m++)
            	            {
            	            	String s =bomHead[m];
            	               if(s.equals(bomItem.getDisplay()))
            	            	{
            	            		bomCol=m;
            	            	}   
            	            	
            	            } 
                            //CCEnd SS1
                            //CCBegin SS2
                            PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                            String[] proHead=structList.getHeadings();
                            int proCol = 0;
                            for(int m=0;m<proHead.length;m++)
            	            {
            	            	String s =proHead[m];
            	               if(s.equals(proVersion.getDisplay()))
            	            	{
            	            		proCol=m;
            	            	}  
            	            	
            	            } 
                            //CCEnd SS2
                            String[] head=structList.getHeadings();
                            int col = 0;
                            for(int k=0;k<head.length;k++)
                            {                                   
                              col=k;                    
                            }
                            partData[row][0] = dtm1.getValueAt(row, quanCol);
                            partData[row][1] = dtm1.getValueAt(row, unitCol);                        
                            partData[row][2] = dtm1.getValueAt(row, col+1);
                            //end
                            //CCBegin SS1
                            partData[row][3] = dtm1.getValueAt(row, sunCol);
                            partData[row][4] = dtm1.getValueAt(row, bomCol);
                            //CCEnd SS1
                            //CCBegin SS2
                            partData[row][5] = dtm1.getValueAt(row, proCol);
                            //CCEnd SS2
                        }
                    }
                }//End CR1
            }
            else if(usesJPanel != null)
            {   
                //性能瓶颈。
                usesJPanel.setPartItem(part);
            }
           
           
            if(PART_CLIENT.equals("C"))
            {
               
                if(mainPart)
                {
                    if(updateMode)
                    {
                        setButtonWhenSave(true);
                    }
                }
                else
                {
                    setButtonWhenSave(true);
                }
            }            
            //初始化描述关联的变量
            if(describedByJPanel != null)
            {
                if(panel.getName() == "addDesJPanel" || isCreate == true)//CR8
                {
                    describedByJPanel.setReference(getReference());
                    describedByJPanel.setPartItem(part);
                    describedByJPanel.setIsShown(true);
                }
            }
            //初始化参考关联的变量
            if(referencesJPanel != null)
            {
                if(panel.getName() == "addRefJPanel" || isCreate == true)//CR8
                {
                    referencesJPanel.setPartItem(part);
                    if(mainPart)
                    {
                        // 保存参考文档号，在判断时使用
                        docBsoID = referencesJPanel.getDocumentBsoID();
                    }
                    referencesJPanel.setIsShown(true);
                }
            }
            //liyz add 工艺相关面板
            if(summaryJPanel != null)
            {
                if(panel.getName() == "addSummaryJPanel")//CR8
                {
                    summaryJPanel.setPartItem(part);
                    summaryJPanel.setIsShown(true);
                }
            }
            if(regulateionJPanel != null)
            {
                if(panel.getName() == "addRegulateionJPanel")//CR8
                {
                    regulateionJPanel.setPartItem(part);
                    regulateionJPanel.setIsShown(true);
                }
            }
            if(routeJPanel!=null&&part.getPart().getMasterBsoID()!=null)
            {
                if(panel.getName() == "addRouteJPanel")//CR8
                {
                    routeJPanel.setPartItem(part);
                    routeJPanel.setIsShown(true);
                }
            }
            //end
            PartDebug.debug("setPartItem() end...return is void", this,
                    PartDebug.PART_CLIENT);            
        }
    }
    
    /**
     * 设置 PartItem 这是一个回叫事务，被内部线程调用。
     * @param part PartItem
     */
    public void setPartItem(PartItem part)
    {
        setPartItem(part,false);
    }
    //CR7 End zhangq
    
    /**
     * 启动一个新线程，以保存已修改的零部件。
     */
    //2003/12/15
    public void save()
    {
        saveInThread();
    }

    /**
     * 运行线程，保存被修改的零部件。
     */
    protected void saveInThread()
    {
        saveInThread(false);
    }

    /**
     * 运行线程，保存被修改的零部件。
     * 点“保存”按钮或在提示保存对话框中选择保存，参数isDialog区分这两种情况。
     * @param isDialog boolean
     */
    protected void saveInThread(boolean isDialog)
    {
        /*
         * 070511王海军
         * 保存前校验那些属性改变，只有在结构和事务特性发生改变时才分配刷新事件进行整个节点的刷新
         */
        PartItem partItem = getPartItem();
        if(!isDialog)
        {
            boolean per = PersistHelper.isPersistent(partItem.getPart());
            if(per)
            {
                boolean flag = false;
                try
                {
                    Class[] paraClass = {BaseValueIfc.class};
                    Object[] objs = {(BaseValueIfc) partItem.getPart()};
                    QMPartIfc partIfc = (QMPartIfc) IBAUtility
                            .invokeServiceMethod("PersistService",
                                    "refreshInfo", paraClass, objs);
                    flag = CheckInOutTaskLogic.isInVault((WorkableIfc) partIfc);
                }
                catch (QMException e)
                {
                    flag = true;
                }
                if(flag)
                {
                    partItem.dispatchRefresh();
                    if(getParentFrame() instanceof QMProductManagerJFrame)
                    {
                        QMProductManager explor = ((QMProductManagerJFrame) this
                                .getParentFrame()).getMyExplorer();
                        explor.refreshSelectedObject();
                    }
                    String message = "所选保存件没有检出或被他人检入，保存失败！";
                    String title2 = getLocalizedValue("informationTitle");
                    JOptionPane.showMessageDialog(getParentFrame(), message,
                            title2, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            isChange();
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        PartDebug.debug("saveInThread() begin ....", this,
                PartDebug.PART_CLIENT);
        setButtonWhenSave(false);
        boolean requiredFieldsFilled;
        //是否更新成功，即是否保存成功。
        boolean updated = false;
        try
        {
        	attributeJPanel.save(partItem, getUpdateMode());
            if(getUpdateMode() == CREATE_MODE)
            {
                requiredFieldsFilled = checkRequiredFields();
                if(!requiredFieldsFilled)
                {
                    setCursor(Cursor.getDefaultCursor());
                    setButtonWhenSave(true);
                    return;
                }
                updated = true;
                ViewObjectInfo view = attributeJPanel.getViewObject();
                if(view==null)
                {
                	 String message ="零部件视图不能为空，请确认是否有视图的读取权限！";
     	            String title2 = this.getLocalizedValue("informationTitle");
     	            JOptionPane.showMessageDialog(getParentFrame(), message, title2,
     	                    JOptionPane.INFORMATION_MESSAGE);
     	           setButtonWhenSave(true);
     	            return;
                }
               
              
            }
            if(ibaHolder != null)
            {
                partItem.getPart().setAttributeContainer(
                        ibaHolder.getAttributeContainer());
                ibaHolder = null;
            }
            else
            {
                updated = true;
            }
            partItem.setFields();
            ProgressService
                    .setProgressText(getLocalizedValue(PartDesignViewRB.SAVING));
            ProgressService.showProgress();
            if(mainPart)
            {
                /*
                 * 070511王海军
                 * 优化补充需求的保存方法，保存part 和事务特性的修改。
                 */
                if(ibachange || basechange)
                {
                    ibaAttributesJPanel.currentItem = partItem.getPart();
                    ibaAttributesJPanel.getItem();
                    QMPartIfc partIfc = (QMPartIfc) IBAUtility
                            .invokeServiceMethod("PersistService",
                                    "saveValueInfo",
                                    new Class[]{BaseValueIfc.class},
                                    new Object[]{partItem.getPart()});
                    partItem.setPartSimply(partIfc);
                    ibaAttributesJPanel.setUpdateMode(true);
                    updated = true;
                }
                //保存使用结构
                if(usagechange)
                {
                    //设置使用结构列表中的所有零部件的数量和单位
                    setValues();
                    updated = usesList.saveFrame() || updated;
                    usesList.startFrame();                   
//                    //liyz modify 保存使用结构时，对是否存在数量和单位列进行判断
//                    String[] heads=structList.getHeadings();
//                    boolean flag1=false; 
//                    boolean flag2=false;
//                	for(int k=0;k<heads.length;k++)
//                	{
//                		String list = heads[k];
//                		if(list.equals(PartUsageList.toPartUsageList("unitName").getDisplay()))
//                		{
//                	      flag1 = true;                	             	      
//                		}
//                		if(list.equals(PartUsageList.toPartUsageList("quantityString").getDisplay()))
//                		{
//                			flag2 = true;       	      
//                		}
//                	}
//                	//存在数量或者单位
//                	if(flag1 || flag2)
//                	{                   		
//                		usesList.setUnitExist(flag1);
//                		usesList.setQuantityExist(flag2);
//                        updated = usesList.saveFrame() || updated;
//                        usesList.startFrame();
//                	}
//                	//数量和单位都不存在
//                	else if((!flag1)&&!(flag2))
//                	{                		
//                		updated = usesList.saveFrame() || updated;
//                        usesList.startFrame();
//                	}
//                	//end
                    if(quantityEditor != null)
                    {
                        quantityEditor.cancelCellEditing();
                    }
                    if(unitEditor != null)
                    {
                        unitEditor.cancelCellEditing();
                    }                	
                }
                if(refchange)
                    updated = referencesJPanel.save() || updated;
                if(descrichange)
                    updated = describedByJPanel.save(partItem) || updated;
            }
            else
            {
                partItem.update();
                //保存使用结构
                updated = usesJPanel.save() || updated;
                //保存参考关系
                updated = referencesJPanel.save() || updated;
                //保存描述关系
                updated = describedByJPanel.save(partItem) || updated;
            }
            if(updated)
            {
                if(isDialog)
                {
                    //刷新刚才更改的节点。由于节点改变，不能分配刷新事件。
                    ((QMExplorer) getRootPane()).getManager().refreshPart(
                            partItem.getPart());
                }
                else
                {
                    //分配刷新事件
                    if(mainPart)
                    {
                        if(usagechange || ibachange || basechange || descrichange || refchange)
                            partItem.dispatchRefresh();
                        docBsoID = referencesJPanel.getDocumentBsoID();
                    }
                    else
                        partItem.dispatchRefresh();
                }
                if(disposeWindow)
                {
                    //在更新界面中，点确定之后。
                    getParentFrame().dispose();
                }
                else
                {
                    //add by 0428 修改保存后多一组分类按钮的问题
                    if(!isDialog && !mainPart)
                    {
                        //end
                        if(getUpdateMode() == CREATE_MODE)
                        {
                            setUpdateMode(UPDATE_MODE);
                            partItem.setNew(false);
                        }
                        partItem.setObject(PartHelper.refresh(partItem.getPart()));
                        setPartItem(partItem);
                    }
                }
            }
            ProgressService.hideProgress();
            basechange = false;
            ibachange = false;
            usagechange = false;
            descrichange = false;
            refchange = false;
            //add 0428 创建后标题由“创建零部件”换为“更新零部件”
            String title = getLocalizedValue("updatePartTitle");
            if(!(getParentFrame() instanceof QMProductManagerJFrame))
            getParentFrame().setTitle(title);
            setCursor(Cursor.getDefaultCursor());
        }
        catch (QMException ex)
        {
            ProgressService.hideProgress();
            setButtonWhenSave(true);            
            ex.printStackTrace();
            DisplayIdentity displayidentity = IdentityFactory
                    .getDisplayIdentity(getPartItem().getPart());
            String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {s};
            String message = "";
            if(ex.getLocalizedMessage() != null)
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + ex.getLocalizedMessage();
            }
            else
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + getLocalizedMessage(PartDesignViewRB.CONF_ER_OPER,
                                null);
            }
            String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(getParentFrame(), message, title2,
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            ProgressService.hideProgress();
            setButtonWhenSave(true);
            ex.printStackTrace();
            DisplayIdentity displayidentity = IdentityFactory
                    .getDisplayIdentity(getPartItem().getPart());
            String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {s};
            String message = "";
            if(ex.getLocalizedMessage() != null)
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + ex.getLocalizedMessage();
            }
            else
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + getLocalizedMessage(PartDesignViewRB.CONF_ER_OPER,
                                null);
            }
            String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(getParentFrame(), message, title2,
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (Throwable t)
        {
            setButtonWhenSave(true);
            t.printStackTrace();
        }
        finally
        {
            //若操作失败，取消事务处理
            basechange = false;
            ibachange = false;
            usagechange = false;
            descrichange = false;
            refchange = false;
            ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
        }
        //add by 0428 修改在创建的零部件被别人检入后，保存和还原按钮有效的问题。
        if(mainPart)
            setButtonWhenSave(updateMode);
        //end
        if(mainPart && !isDialog)
        {
            //设置描述文档的改变标志
            describedByJPanel.setChanged(false);
        }
        PartDebug.debug("saveInThread() end ....return is void", this,
                PartDebug.PART_CLIENT);
       
    }


    /**
     * 初始化帮助系统
     */
    public void initializeHelp()
    {
        try
        {
            super.initializeHelp();
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * 初始化。
     */
    protected void initialize()
    {
        PART_CLIENT = "C"; //暂时
        localize();
       
        //使编辑特性按钮不可见
        editAttributesJButton.setVisible(true);

      
        if(!mainPart)
        {
            add(editAttributesJButton, new GridBagConstraints(0, 2, 1, 1,
                    0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(5, 7, 5, 5), 25, 0));
        }

    }


    /**
     * 实现动作监听，调用保存、确定、取消和编辑特性按钮的动作事件方法。
     */
    class PTJAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Object object = event.getSource();
            if(object == saveJButton)
            {
                saveJButton_actionPerformed(event);
            }
            else if(object == cancelJButton)
            {
                cancelJButton_actionPerformed(event);
            }
            else if(object == okJButton)
            {
                okJButton_actionPerformed(event);
            }
            else if(object == editAttributesJButton)
            {
                editAttributesJButton_actionPerformed(event);
            }
        }
    }


    /**
     * 确定按钮的动作。
     * @param event  ActionEvent
     */
    protected void okJButton_actionPerformed(ActionEvent event)
    {
        if(!mainPart)
        {
            disposeWindow = true;
        }
        save();

    }


    /**
     * 保存按钮的动作。
     * @param event  ActionEvent
     */
    protected void saveJButton_actionPerformed(ActionEvent event)
    {
        if(!mainPart)
        {
            disposeWindow = false;
        }
        save();

    }


    /**
     * 设置按钮的显示状态（有效或失效）。
     * @param flag  flag为True，按钮有效；否则按钮失效。
     */
    protected void setButtonWhenSave(boolean flag)
    {
        okJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }


    /**
     * 取消按钮的动作。
     * @param event  ActionEvent
     */
    protected void cancelJButton_actionPerformed(ActionEvent event)
    {
        if(!mainPart)
        {
            getParentFrame().setVisible(false);
        }
        else
        {
            PartItem part = getPartItem();
            //对属性进行初始化,更新容器
            usesList.startFrame();
            if(quantityEditor != null)
            {
                quantityEditor.cancelCellEditing();
            }
            if(unitEditor != null)
            {
                unitEditor.cancelCellEditing();
            }
            part.dispatchRefresh();
            try
            {
                part.setObject(PartHelper.refresh(part.getPart()));
            }
            catch (QMRemoteException qre)
            {
                qre.printStackTrace();
                DisplayIdentity displayidentity = IdentityFactory
                        .getDisplayIdentity(getPartItem().getPart());
                String s = displayidentity.getLocalizedMessage(null);
                Object[] params = {s};
                String title = getLocalizedMessage(
                        PartDesignViewRB.SAVE_PART_FAIL, params);
                JOptionPane.showMessageDialog(getParentFrame(), qre
                        .getClientMessage(), title,
                        JOptionPane.ERROR_MESSAGE);
            }
            setListEditor();
        }
    }


    /**
     * 编辑特性按钮的动作。
     *
     * @param e ActionEvent
     */
    void editAttributesJButton_actionPerformed(ActionEvent e)
    {
        if(ibaHolder == null)
        {
            ibaHolder = (IBAHolderIfc) getPartItem().getPart();
            if(ibaHolder.getAttributeContainer() == null)
            {
                try
                {
                    //要调用的方法中的参数的类的集合
                    Class[] classes = {IBAHolderIfc.class, Object.class,
                            Locale.class, MeasurementSystemDefaultView.class};
                    //要调用的方法中的参数的集合
                    Object[] objects = {ibaHolder, null,
                            RemoteProperty.getVersionLocale(), null};
                    //向服务发出请求,取得结果集合（用户值对象UserInfo的集合）
                    ibaHolder = (IBAHolderIfc) IBAUtility.invokeServiceMethod(
                            "IBAValueService", "refreshAttributeContainer",
                            classes, objects);
                }
                catch (QMRemoteException remoteexception)
                {
                    remoteexception.printStackTrace();
                    ibaHolder
                            .setAttributeContainer(new DefaultAttributeContainer());
                }
            }
        }
        AbstractLiteObject classificationstructdefaultview = null;
        try
        {
            classificationstructdefaultview = NavigationUtil
                    .getClassificationStructure(getPartItem().getPart()
                            .getClass().getName());
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            Component parentComponent = IBAUtility.getParentComponent(this);//Begin CR4
            IBADialog ibadialog = null;
            if(parentComponent instanceof JFrame)
                ibadialog = new IBADialog((JFrame)parentComponent, ResourceBundle.getBundle("com.faw_qm.iba.value.util.ValueResource", Locale.getDefault()), ibaHolder,
                        classificationstructdefaultview, mode != VIEW_MODE);
            else
                ibadialog = new IBADialog((JDialog)parentComponent, ResourceBundle.getBundle("com.faw_qm.iba.value.util.ValueResource", Locale.getDefault()), ibaHolder,
                        classificationstructdefaultview, mode != VIEW_MODE);//End CR4
            ibaHolder = (IBAHolderIfc)ibadialog.showDialog();
        }catch(Throwable _throw)
        {
            _throw.printStackTrace();
        }
    }


   

    /**
     * 本地化。
     */
    protected void localize()
    {
        try
        {
           
            // command buttons
            okJButton.setText(getLabelsRB().getString("okJButton"));
            saveJButton.setText(getLabelsRB().getString("saveJButton"));            
            if(mainPart)
            {
                viewUsageJB
                        .setText(getLabelsRB().getString("viewUsageJButton"));
                addUsageJB.setText(getLabelsRB().getString("addUsageJButton"));
                removeUsageJB.setText(getLabelsRB().getString(
                        "removeUsageJButton"));
                cancelJButton.setText(getLabelsRB().getString("revertJButton"));
                //liyz add 显示设置按钮
                partAttrSetJButton.setText(getLabelsRB().getString("partAttrSetJButton"));//end
            }
            else
            {
                cancelJButton.setText(getLabelsRB().getString("cancelJButton"));
            }
            editAttributesJButton.setText(getLabelsRB().getString(
                    "editAttributesJButton"));
           
            
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }


    /**
     * 设置最大长度。
     */
    protected void setMaxLengths()
    {
    }


    /**
     * 获得零部件的来源属性的类名。
     * @return  类名。
     */
    protected String getSourceClassName()
    {
        return getLocalizedValue(PartDesignViewRB.QMPART_SOURCE);
    }


    /**
     * 获得零部件的类型属性的类名。
     * @return  类名。
     */
    protected String getTypeClassName()
    {
        return getLocalizedValue(PartDesignViewRB.QMPART_TYPE);
    }


    /**
     * 获得零部件的属性的面板。
     * @return  标签页面板。
     */
    public JPanel getTab()
    {
        return this;
    }

    /**
     * 添加分类按钮。
     */
    public synchronized void addClassificationButtons()
    {
        try
        {
            classificationstructdefaultview = NavigationUtil
                    .getClassificationStructure(QMPartInfo.class.getName());
            if(classificationstructdefaultview != null)
            {
                classifiercontrol = (ClassifierControl) Class
                        .forName(
                                "com.faw_qm.csm.client.classification.model.ClassifierClassifyButton")
                        .newInstance();
                classifiercontrol2 = (ClassifierControl) Class
                        .forName(
                                "com.faw_qm.csm.client.classification.model.ClassifierRemoveButton")
                        .newInstance();
                classifiercontrol.setStructure(classificationstructdefaultview);
                classifiercontrol2
                        .setStructure(classificationstructdefaultview);
                ibaAttributesJPanel.addControl(classifiercontrol, classifiercontrol2);//CR5
                contentsJTabbedPane.repaint();
//                ibaAttributesJPanel.refreshControls();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /**
     * 刷新分类控制。
     */
    public synchronized void refreshClassificationControl()
    {
        ibaAttributesJPanel.refreshControls();
    }
//    /**
//     * 初始化分类控制
//     */
//    public synchronized void initializeClassificationControl()
//    {
//        ibaAttributesJPanel.initializeControls();
//    }

    /**
     * 将list加到使用结构面板中，在list中包含使用结构的数据。
     *
     * @param  list QMPartList
     */
    public void initList(QMPartList list, boolean b)
    {
        structList = list;
        structList.setTaskPanel(this);
        if(b)
        {
            setListEditor();
        }
        addStructJPanel.add(structList, new GridBagConstraints(0, 0, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        addStructJPanel.add(useButtonJPanel, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHEAST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    public void addList(QMPartList list)
    {
        structList = list;
        structList.setTaskPanel(this);
        addStructJPanel.add(structList, new GridBagConstraints(0, 0, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        addStructJPanel.add(useButtonJPanel, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHEAST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**保存使用结构中的数量、单位和子部件的BsoID*/
    private Object[][] partData;

    /**保存参考关系中文档的BsoID*/
    private String[] docBsoID;

    /**零部件的使用列表*/
    private QMPartList structList = new QMPartList(10);   

    /**
     * 改变显示的标签页。
     *
     * @param i int:要显示的标签页号码（从0开始）。
     */
    public void updateTabbed(int i)
    {
        contentsJTabbedPane.setSelectedIndex(i);
    }   

    /**
     * 运行搜索器搜索要使用的新零部件。
     * @param event the action event
     */
    void addUsageJButton_actionPerformed(ActionEvent event)
    {
    	
    	//CCBegin SS3
    	
//        PartDebug.debug("addUsageJButton_actionPerformed(e) begin...", this,
//                PartDebug.PART_CLIENT);
//        //获得父窗口
//        JFrame frame = getParentFrame();
//        String partBsoName = QMMessage.getLocalizedMessage(RESOURCE,
//                PartDesignViewRB.QMPM_BSONAME, null);
//        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle",
//                null);
//        //定义搜索器
//        QmChooser qmChooser = new QmChooser(partBsoName, title, frame);
//        qmChooser.setChildQuery(false);
//        qmChooser.setRelColWidth(new int[]{1, 1});
//        PartDebug.debug("************创建搜索零部件成功！———qmChooser = " + qmChooser,
//                PartDebug.PART_CLIENT);
//        //按照给定条件，执行搜索
//        qmChooser.addListener(new QMQueryListener()
//        {
//            public void queryEvent(QMQueryEvent e)
//            {
//                qmChooser_queryEvent(e);
//            }
//        });
//        qmChooser.setVisible(true);
//        PartDebug.debug(
//                "addUsageJButton_actionPerformed(e) end...return is void",
//                this, PartDebug.PART_CLIENT);
    	
    	
    	
    	 AddSearchJDialog searchDialog = new AddSearchJDialog( getParentFrame());
         searchDialog.setSize(700, 650);
         //使界面在屏幕中央显示
         PartScreenParameter.setLocationCenter(searchDialog);
         searchDialog.setVisible(true);

    	//CCEnd SS3
    	
    	
    	
    }
    
    

    
  //CCBegin SS3
    
    /**扩展父类的目的是使查询结果列表支持鼠标双击功能*/
    class MyQuery extends CappQuery {
        public CappMultiList getResultList() {
            return this.getMyList();
        }
    }
    
    //CCEnd SS3
    
    /**
     * 搜索零部件监听事件方法。
     * @param e 搜索监听事件。
     */
    public void qmChooser_queryEvent2(CappQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                PartDebug.PART_CLIENT);
        //当前创建或更新的零部件值对象
        //2003/12/18
        if(getPartItem() != null)
        {
            partIfc1 = getPartItem().getPart();
        }
        PartItem[] partItem;
        Explorable[] usageItem;
        String[][] result = null;
        int begin = usesList.size();
        int len = 0;
        int m = 0;
        PartDebug.debug("当前创建或更新的零部件值对象 partIfc1 = " + partIfc1,
                PartDebug.PART_CLIENT);
        if(e.getType().equals(CappQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(CappQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
            	MyQuery c = (MyQuery) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("获得所选择的所有零部件 bvi = " + bvi,
                        PartDebug.PART_CLIENT);
                if(bvi != null)
                {
                    partItem = new PartItem[bvi.length];
                    //CCBegin SS1
                    //CCBegin SS2
                    //add by zhangqiang 070104 begin
                   // result = new String[bvi.length][10];
                    //result = new String[bvi.length][12];
                    result = new String[bvi.length][13];
                    //CCEnd SS2
                    //CCEnd SS1
                    //add by zhangqiang 070104 end
                      usageItem = new Explorable[bvi.length];
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                PartDebug.PART_CLIENT);
                        //所选择的某一零部件
                        BaseValueIfc newPart = bvi[i];
                        
                        
                        QMPartIfc partIfc2 = (QMPartIfc)newPart;
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)partIfc2.getMaster();
                        
                        
                        
                        PartDebug.debug("获得要添加的零部件的最新小版本 partIfc2 = "
                                + partIfc2, PartDebug.PART_CLIENT);
                        //用于判断partIfc1和partIfc2是否使用结构嵌套
                        boolean flag = false;
                        //如果当前是更新零部件界面，则检验partIfc1和partIfc2是否使用结构嵌套
                        if(partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = isParentPart(partIfc1, partIfc2);
                        }
                        //如果返回为真，提示使用结构嵌套。否则，进一步检查是否重复添加
                        if(flag == true)
                        {
                            //通过标识工厂获得与给定值对象对应的显示标识对象。
                            DisplayIdentity displayidentity = IdentityFactory
                                    .getDisplayIdentity(partIfc2);
                            //获得对象类型 + 标识
                            String s = displayidentity
                                    .getLocalizedMessage(null);
                            Object[] params = {s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.PART_CF_NESTING, params);
                            String title = getLocalizedValue(PartDesignViewRB.USE_CF_NESTING);
                            JOptionPane
                                    .showMessageDialog(getParentFrame(),
                                            message, title,
                                            JOptionPane.WARNING_MESSAGE);
                            m++;
                        }
                        else
                        {
                            //处理零部件使用关系的模型类
                            UsageInterfaceItem usageInterfaceItem = new UsageInterfaceItem(
                                    getPartItem(), newPartMaster);
                            //为被添加的零部件设置默认单位
                            usageInterfaceItem.setUnit(newPartMaster
                                    .getDefaultUnit());
                            //把通过检查的零部件加入容器
                            usesList.insertElementAt(usageInterfaceItem, 0);
                            //add 0429 begin ：修改添加Master为空的情况。
                          if(partIfc2 == null)
                          {
                              usageItem[i - m]= new PartMasterItem(
                                          newPartMaster);
//                                  result[i - m][0] = newPartMaster.getPartNumber();
//                                  result[i - m][1] = newPartMaster.getPartName();
//                                  result[i - m][5] = usageInterfaceItem.getUnits()
//                                          .getDisplay();
//                                  result[i - m][9] = usageInterfaceItem.getId();
                              
                              /********liyz add begin*******/ 
                              //在使用结构列表中添加零部件时,列表头和所在列的位置相对应
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              
                              /************** liyz add end ******************/
                          }
                          else
                          {
                              partItem[i - m] = new PartItem(partIfc2);
                              PartItem partItem1 = new PartItem(partIfc1);
                              usageItem[i - m] = new UsageItem(partItem[i - m],
                                      partItem1);
                              ((UsageItem)usageItem[i - m]).setUnit(newPartMaster
                                      .getDefaultUnit());

                              /********liyz add begin*******/ 
                              //在使用结构列表中添加零部件时,列表头和所在列的位置相对应
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add version
                              PartUsageList version = PartUsageList.toPartUsageList("iterationID");
                              String[] versionHead=structList.getHeadings();
                              int versionCol = 0;
                              for(int ver=0;ver<versionHead.length;ver++)
                              {
                              	String s =versionHead[ver];
                              	if(s.equals(version.getDisplay()))
                              	{
                              		versionCol=ver;
                              	}            	                	
                              }
                              result[i - m][versionCol] = partIfc2.getVersionValue();
                              //add view
                              PartUsageList view = PartUsageList.toPartUsageList("viewName");
                              String[] viewHead = structList.getHeadings();
                              int viewCol =0;
                              for(int vi=0;vi<viewHead.length;vi++)
                              {
                            	String s = viewHead[vi];
                            	if(s.equals(view.getDisplay()))
                            	{
                            		viewCol = vi;
                            	}
                              }
                              result[i - m][viewCol] = partIfc2.getViewName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add type
                              PartUsageList type = PartUsageList.toPartUsageList("type");
                              String[] typeHead = structList.getHeadings();
                              int typeCol = 0;
                              for(int t=0;t<typeHead.length;t++)
                              {
                            	  String s =typeHead[t];
                            	  if(s.equals(type.getDisplay()))
                            	  {
                            		  typeCol = t;
                            	  }
                              }
                              result[i - m][typeCol] = partIfc2.getPartType().getDisplay();
                              //add producedBy
                              PartUsageList produced =PartUsageList.toPartUsageList("producedBy");
                              String[] producedHead = structList.getHeadings();
                              int producedCol = 0;
                              for(int p=0;p<producedHead.length;p++)
                              {
                            	  String s = producedHead[p];
                            	  if(s.equals(produced.getDisplay()))
                            	  {
                            		  producedCol =p;
                            	  }
                              }
                              result[i - m][producedCol] = partIfc2.getProducedBy().getDisplay();
                              //add lifeCycleState
                              PartUsageList state =PartUsageList.toPartUsageList("state");
                              String[] stateHead = structList.getHeadings();
                              int stateCol = 0;
                              for(int l=0;l<stateHead.length;l++)
                              {
                            	  String s = stateHead[l];
                            	  if(s.equals(state.getDisplay()))
                            	  {
                            		  stateCol =l;
                            	  }
                              }
                              result[i - m][stateCol] = partIfc2.getLifeCycleState().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              /************** liyz add end ******************/
                              //CCBegin SS1
                              PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                              String[] bomHead = structList.getHeadings();
                              int bomCol =0;
                              for(int b=0;b<bomHead.length;b++)
                              {
                            	String s = bomHead[b];
                            	if(s.equals(bomItem.getDisplay()))
                            	{
                            		bomCol = b;
                            	}
                              }
                              result[i - m][bomCol] = usageInterfaceItem.getBomItem();
                              PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                              String[] subHead = structList.getHeadings();
                              int subCol =0;
                              for(int z=0;z<subHead.length;z++)
                              {
                            	String s = bomHead[z];
                            	if(s.equals(subUnitNumber.getDisplay()))
                            	{
                            		subCol = z;
                            	}
                              }
                              result[i - m][subCol] = usageInterfaceItem.getSubUnitNumber();
                              //CCEnd SS1
                               //CCBegin SS2
                              PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                              String[] proHead = structList.getHeadings();
                              int proCol =0;
                              for(int b=0;b<proHead.length;b++)
                              {
                            	String s = proHead[b];
                            	if(s.equals(proVersion.getDisplay()))
                            	{
                            		proCol = b;
                            	}
                              }
                              result[i - m][proCol] = usageInterfaceItem.getProVersion();
                              //CCEnd SS2
                         }
                           String quan = String.valueOf(usageInterfaceItem
                                   .getQuantityString());
                           if(quan.endsWith(".0"))
                           {
                               quan = quan.substring(0, quan.length() - 2);
                           }
                           //result[i - m][4] = quan;
                           //liya add quantity
                           PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                           String[] quanHead=structList.getHeadings();
                           int quanCol = 0;
                           for(int q=0;q<quanHead.length;q++)
                           {
                           	String s =quanHead[q];
                           	if(s.equals(quantity.getDisplay()))
                           	{
                           		quanCol=q;
                           	}            	                	
                           }
                           result[i - m][quanCol] = quan;
                           //end
                        }
                    }
                    len = bvi.length - m;
                }
                else
                {
                    return;
                }
                setValues();
                	try
					{
						setMethods();
					} catch (QMException e1)
					{						
						e1.printStackTrace();
					}
                structList.addDetail(usageItem, result, begin, len);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    //CCBegin SS3
    class AddSearchJDialog extends JDialog {
        /** 查询类 */
        private MyQuery cappQuery = new MyQuery();

        /** 要查询的业务对象 */
        public  String SCHEMA;

        /** 查询方案 */
        private CappSchema mySchema;

        private GridBagLayout gridBagLayout1 = new GridBagLayout();

        /**产品结构管理器主界面*/
        private JFrame managerJFrame = new JFrame();

        /** 用于标记资源文件路径 */
        protected  String RESOURCE = "com.faw_qm.cappclients.capprouye.util.CappRouteRB";

        /** 测试变量 */
        private  boolean verbose = (RemoteProperty.getProperty(
                "com.faw_qm.cappclients.verbose", "true")).equals("true");
        
        Hashtable copyCodingClassifications=null;
        
        public AddSearchJDialog(JFrame frame){
        	
        	  super(frame, "", true);
              
              
              SCHEMA = "C:QMPart; G:搜索条件;A:partNumber;A:partName;A:versionValue;A:viewName";
              //定义查询方案
              try {
                mySchema = new CappSchema(SCHEMA);
              }
              catch (QMRemoteException ex) {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                        null);
                JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
               return ;
              }
              //设置查询方案
              cappQuery.setSchema(mySchema);
//              cappQuery.notAcessInPersonalFolder();
              cappQuery.setLastIteration(true);

              
              
              setSingleSelectMode();
              
              managerJFrame = frame;
              try {
                  jbInit();
                  
                  addDefaultListener();
                  
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
            this.setTitle("搜索零件");
            this.setSize(650, 500);
            this.getContentPane().setLayout(gridBagLayout1);
            //界面布局管理
            this.getContentPane().add(
                    cappQuery,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 10, 0, 10), 0, 0));
        }

        /**
         * 添加默认查询监听
         */
        public void addDefaultListener() {
            cappQuery.addListener(new CappQueryListener() {
                public void queryEvent(CappQueryEvent e) {
                    cappQuery_queryEvent(e);
                }
            });
        }

        /**
         * 添加查询监听
         *
         * @param s
         *            查询监听
         */
        public void addQueryListener(CappQueryListener s) {
            cappQuery.addListener(s);
        }

        /**
         * 删除查询监听
         *
         * @param s
         *            查询监听
         */
        public void removeQueryListener(CappQueryListener s) {
            cappQuery.removeListener(s);
        }
  

        /**
         * 搜索监听事件方法
         *
         * @param e
         *            搜索监听事件
         */
        public void cappQuery_queryEvent(CappQueryEvent e) {
        	
        	PartTaskJPanel.this.qmChooser_queryEvent2(e);
          

            
        }
        
       
        

        /**
         * 设置列表为单选模式
         */
        public void setSingleSelectMode() {
            try {
                cappQuery.setMultipleMode(false);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * 设置界面的显示位置
         */
        private void setViewLocation() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = this.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            this.setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);

        }

        /**
         * 重载父类方法，使界面显示在屏幕中央
         *
         * @param flag
         */
        public void setVisible(boolean flag) {
            this.setViewLocation();
            super.setVisible(flag);
        }



        /**
         * MultiList添加事件
         * @param lis
         */
        public void addMultiListActionListener(ActionListener lis) {
            this.cappQuery.getResultList().addActionListener(lis);
        }

    }
  //CCEnd SS3
    

    /**
     * 根据零部件的主信息，获得零部件的最新小版本。
     * @param partMaster  零部件的主信息。
     * @return  零部件的最新小版本。
     */
    private QMPartIfc getLastedIterations(QMPartMasterIfc partMaster)
    {
        PartDebug.debug("getLastedIterations() begin...", this,
                PartDebug.PART_CLIENT);
        QMPartIfc partIfc = null;
        //调用服务方法，获得零部件的所有小版本
        Class[] paraClass = {MasteredIfc.class};
        Object[] objs = {partMaster};
        Collection collection = null;
        try
        {
            collection = (Collection) IBAUtility
                    .invokeServiceMethod("VersionControlService",
                            "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane
                    .showMessageDialog(getParentFrame(), ex.getClientMessage(),
                            title, JOptionPane.ERROR_MESSAGE);
        }
        Iterator iterator = collection.iterator();
        if(iterator.hasNext())
        {
            //获得零部件的最新小版本
            partIfc = (QMPartIfc) iterator.next();
        }
        PartDebug.debug("getLastedIterations() end...return " + partIfc, this,
                PartDebug.PART_CLIENT);
        return partIfc;
    }

    /**
     * 检验零部件part2是否是零部件part1的祖先部件或是part1本身。
     * @param part1  指定的零部件的值对象。
     * @param part2  被检验的零部件的值对象。
     * @return 如果零部件part2是零部件part1的祖先部件或是part1本身，则返回true；否则返回false。
     */
    private  boolean isParentPart(QMPartIfc part1, QMPartIfc part2)
    {
        PartDebug.debug("isParentPart() begin...", this, PartDebug.PART_CLIENT);
        Boolean flag1 = new Boolean(false);
        try
        {
            //调用服务方法，判断partIfc1和partIfc2是否使用结构嵌套
            flag1 = Boolean.valueOf(PartServiceRequest.isParentPart(part1,
                    part2));
            PartDebug.debug("是否使用结构嵌套(Boolean) flag1 = " + flag1,
                    PartDebug.PART_CLIENT);
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane
                    .showMessageDialog(getParentFrame(), ex.getClientMessage(),
                            title, JOptionPane.ERROR_MESSAGE);
        }
        boolean flag = flag1.booleanValue();
        PartDebug.debug("isParentPart() end...return is " + flag, this,
                PartDebug.PART_CLIENT);
        return flag;
    }

    /**
     * 设置使用结构列表中的每个零部件的数量和单位。
     */
    protected void setValues()
    {
        PartDebug.debug("setValues() begin ....", this, PartDebug.PART_CLIENT);
        //使用列表的行数
        int size = structList.getNumberOfRows();
        //设置使用列表中的数量和单位值
        for (int row = 0; row < size; row++)
        {
            //根据当前零部件的ID获取集合中的对应的使用关联项UsageInterfaceItem
//            UsageInterfaceItem usage = usesList.interfaceElementAt(structList
//                    .getCellText(row, 9));
            //liyz add            
            String[] head=structList.getHeadings();
            int col = 0;
            for(int k=0;k<head.length;k++)
            {            	                	
            	col=k;                	
            }
            UsageInterfaceItem usage = usesList.interfaceElementAt(structList
                    .getCellText(row, col+1));            
            //end
            float gui_quantity = 0; //数量的浮点值
            //设置数量值
            //gui_quantity = Float.parseFloat(dtm.getValueAt(row, 4).toString());
            //liyz add
            PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
            String[] quanHead=structList.getHeadings();
            int quanCol = 0;
            boolean flag = false;
            for(int m=0;m<quanHead.length;m++)
            {
            	String s =quanHead[m];
            	if(s.equals(quantity.getDisplay()))
            	{
            		flag = true;
            		quanCol=m;
            	} 
            
            }
            //CCBegin SS1
            boolean sunflag = false;
            PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
            String[] subHead=structList.getHeadings();
            int sunCol = 0;
            for(int m=0;m<subHead.length;m++)
            {
            	String s =subHead[m];
               if(s.equals(subUnitNumber.getDisplay()))
            	{
            	   sunflag=true;
            	   sunCol=m;
            	}  
            	
            } 
            PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
            String[] bomHead=structList.getHeadings();
            int bomCol = 0;
            boolean  bomflag = false;
            for(int m=0;m<bomHead.length;m++)
            {
            	String s =bomHead[m];
               if(s.equals(bomItem.getDisplay()))
            	{
            	   bomflag=true;
            		bomCol=m;
            	}   
            	
            } 
         
           
            
          //CCend SS1
            //CCBegin SS2
            boolean proflag = false;
            PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
            String[] proHead=structList.getHeadings();
            int proCol = 0;
            for(int m=0;m<proHead.length;m++)
            {
            	String s =proHead[m];
               if(s.equals(proVersion.getDisplay()))
            	{
            	   proflag=true;
            	   proCol=m;
            	}  
            	
            } 
          //CCend SS2
            //判断使用结构列表中是否有数量列
            if(flag)
            {
            	gui_quantity = Float.parseFloat(dtm.getValueAt(row, quanCol).toString());
            }            
            //end
            //如果使用关联项中的数量不等于当前零部件的数量值
            if(usage.getQuantity() != gui_quantity)
            {
                //设置数量为当前零部件的数量
                usage.setQuantity(gui_quantity);
            }
            //设置单位值
            //当前所选中的单位
            Unit gui_unit = getSelectedUnit(row);
            //          Unit gui_unit = Unit.toUnit(dtm.getValueAt(row, 5).toString().trim());
            //使用关联项中的数量单位
            Unit saved_unit = usage.getUnits();
            //如果使用关联项中的数量单位为空,或者不等于当前零部件的单位，则设置
            //使用关联项中的数量单位为当前零部件的单位
            if(saved_unit == null)
            {
                //设置单位为当前零部件的单位
                usage.setUnit(gui_unit);
            }
            else if(!saved_unit.equals(gui_unit))
            {
                usage.setUnit(gui_unit);
            }
            //CCBegin SS1
            String gui_sun ="";
            Object value = null;
            if(sunflag)
            {
            	value = dtm.getValueAt(row, sunCol);
            	if(value!=null){
            		gui_sun = value.toString();
            	}
            }
            String gui_bomItem ="";
            if(bomflag)
            {
            	 value = dtm.getValueAt(row, bomCol);
              	if(value!=null){
              		gui_bomItem = value.toString();
              	}
            }

        
            if(usage.getSubUnitNumber() != gui_sun)
            {
                usage.setSubUnitNumber(gui_sun);
            }
            if(usage.getBomItem() != gui_bomItem)
            {
                usage.setBomItem(gui_bomItem);
            }
            //CCEnd SS1
             //CCBegin SS2
            String gui_pro ="";
            if(proflag)
            {
            	value = dtm.getValueAt(row, proCol);
            	if(value!=null){
            		gui_pro = value.toString();
            	}
            }
             if(usage.getProVersion() != gui_pro)
            {
                usage.setProVersion(gui_pro);
            }
           //CCEnd SS2
        }
        PartDebug.debug("setValues() end ....return is void", this,
                PartDebug.PART_CLIENT);
    }

    /**
     * 获得使用结构列表中指定行的单位。
     * @param row 使用结构列表中指定的行。
     * @return foundUnit 使用结构列表中指定行的单位。
     */
    public Unit getSelectedUnit(int row)
    {
        PartDebug.debug("getSelectedUnit(int row) begin ...", this,
                PartDebug.PART_CLIENT);
        Unit foundUnit = null;
        //指定行的单位值（字符串）
        //String stringUnit = dtm.getValueAt(row, 5).toString();
        //liyz add
        PartUsageList unit =PartUsageList.toPartUsageList("unitName");
        String[] unitHead = structList.getHeadings();
        int unitCol = 0;
        boolean flag = false;
        for(int un=0;un<unitHead.length;un++)
        {
        	String s =unitHead[un];
        	if(s.equals(unit.getDisplay()))
        	{
        		flag = true;
        		unitCol = un;
        	}        	
        }
        if(flag)
        {
        String stringUnit = dtm.getValueAt(row, unitCol).toString();
        //end
        
        //获得单位的所有内容类型
        Unit[] types = Unit.getUnitSet();
        //如果指定零部件的单位与某单位内容类型相符，则获得该单位内容类型
        for (int i = 0; i < types.length; i++)
        {
            if(stringUnit.equals(types[i].getDisplay()))
            {
                foundUnit = types[i];
                break;
            }
        }
        }
        PartDebug.debug("getSelectedUnit(int row) end...return " + foundUnit,
                this, PartDebug.PART_CLIENT);
        return foundUnit;
    }

    /**当前创建或更新的零部件值对象*/
    private QMPartIfc partIfc1 = null;

    /**使用添加和删除的容器*/
    protected UsesInterfaceList usesList = null;

    /**
     * 搜索零部件监听事件方法。
     * @param e 搜索监听事件。
     */
    public void qmChooser_queryEvent(QMQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                PartDebug.PART_CLIENT);
        //当前创建或更新的零部件值对象
        //2003/12/18
        if(getPartItem() != null)
        {
            partIfc1 = getPartItem().getPart();
        }
        PartItem[] partItem;
        Explorable[] usageItem;
        String[][] result = null;
        int begin = usesList.size();
        int len = 0;
        int m = 0;
        PartDebug.debug("当前创建或更新的零部件值对象 partIfc1 = " + partIfc1,
                PartDebug.PART_CLIENT);
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("获得所选择的所有零部件 bvi = " + bvi,
                        PartDebug.PART_CLIENT);
                if(bvi != null)
                {
                    partItem = new PartItem[bvi.length];
                    //CCBegin SS1
                    //CCBegin SS2
                    //add by zhangqiang 070104 begin
                   // result = new String[bvi.length][10];
                    //result = new String[bvi.length][12];
                    result = new String[bvi.length][13];
                    //CCEnd SS2
                    //CCEnd SS1
                    //add by zhangqiang 070104 end
                      usageItem = new Explorable[bvi.length];
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                PartDebug.PART_CLIENT);
                        //所选择的某一零部件
                        BaseValueIfc newPart = bvi[i];
                        //获得零部件的主信息
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc) newPart;
                        //获得要添加的零部件的最新小版本
                        QMPartIfc partIfc2 = this
                                .getLastedIterations(newPartMaster);
                        PartDebug.debug("获得要添加的零部件的最新小版本 partIfc2 = "
                                + partIfc2, PartDebug.PART_CLIENT);
                        //用于判断partIfc1和partIfc2是否使用结构嵌套
                        boolean flag = false;
                        //如果当前是更新零部件界面，则检验partIfc1和partIfc2是否使用结构嵌套
                        if(partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = isParentPart(partIfc1, partIfc2);
                        }
                        //如果返回为真，提示使用结构嵌套。否则，进一步检查是否重复添加
                        if(flag == true)
                        {
                            //通过标识工厂获得与给定值对象对应的显示标识对象。
                            DisplayIdentity displayidentity = IdentityFactory
                                    .getDisplayIdentity(partIfc2);
                            //获得对象类型 + 标识
                            String s = displayidentity
                                    .getLocalizedMessage(null);
                            Object[] params = {s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.PART_CF_NESTING, params);
                            String title = getLocalizedValue(PartDesignViewRB.USE_CF_NESTING);
                            JOptionPane
                                    .showMessageDialog(getParentFrame(),
                                            message, title,
                                            JOptionPane.WARNING_MESSAGE);
                            m++;
                        }
                        else
                        {
                            //处理零部件使用关系的模型类
                            UsageInterfaceItem usageInterfaceItem = new UsageInterfaceItem(
                                    getPartItem(), newPartMaster);
                            //为被添加的零部件设置默认单位
                            usageInterfaceItem.setUnit(newPartMaster
                                    .getDefaultUnit());
                            //把通过检查的零部件加入容器
                            usesList.insertElementAt(usageInterfaceItem, 0);
                            //add 0429 begin ：修改添加Master为空的情况。
                          if(partIfc2 == null)
                          {
                              usageItem[i - m]= new PartMasterItem(
                                          newPartMaster);
//                                  result[i - m][0] = newPartMaster.getPartNumber();
//                                  result[i - m][1] = newPartMaster.getPartName();
//                                  result[i - m][5] = usageInterfaceItem.getUnits()
//                                          .getDisplay();
//                                  result[i - m][9] = usageInterfaceItem.getId();
                              
                              /********liyz add begin*******/ 
                              //在使用结构列表中添加零部件时,列表头和所在列的位置相对应
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              
                              /************** liyz add end ******************/
                          }
                          else
                          {
                              partItem[i - m] = new PartItem(partIfc2);
                              PartItem partItem1 = new PartItem(partIfc1);
                              usageItem[i - m] = new UsageItem(partItem[i - m],
                                      partItem1);
                              ((UsageItem)usageItem[i - m]).setUnit(newPartMaster
                                      .getDefaultUnit());
//                              result[i - m][0] = newPartMaster.getPartNumber();
//                              result[i - m][1] = newPartMaster.getPartName();
//                              result[i - m][2] = partIfc2.getVersionValue();
//                              result[i - m][3] = partIfc2.getViewName();
//                              result[i - m][5] = usageInterfaceItem.getUnits()
//                                      .getDisplay();
//                              result[i - m][6] = partIfc2.getPartType()
//                                      .getDisplay();
//                              result[i - m][7] = partIfc2.getProducedBy()
//                                      .getDisplay();
//                              result[i - m][8] = partIfc2.getLifeCycleState()
//                                      .getDisplay();
//                              result[i - m][9] = usageInterfaceItem.getId();
                              /********liyz add begin*******/ 
                              //在使用结构列表中添加零部件时,列表头和所在列的位置相对应
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add version
                              PartUsageList version = PartUsageList.toPartUsageList("iterationID");
                              String[] versionHead=structList.getHeadings();
                              int versionCol = 0;
                              for(int ver=0;ver<versionHead.length;ver++)
                              {
                              	String s =versionHead[ver];
                              	if(s.equals(version.getDisplay()))
                              	{
                              		versionCol=ver;
                              	}            	                	
                              }
                              result[i - m][versionCol] = partIfc2.getVersionValue();
                              //add view
                              PartUsageList view = PartUsageList.toPartUsageList("viewName");
                              String[] viewHead = structList.getHeadings();
                              int viewCol =0;
                              for(int vi=0;vi<viewHead.length;vi++)
                              {
                            	String s = viewHead[vi];
                            	if(s.equals(view.getDisplay()))
                            	{
                            		viewCol = vi;
                            	}
                              }
                              result[i - m][viewCol] = partIfc2.getViewName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add type
                              PartUsageList type = PartUsageList.toPartUsageList("type");
                              String[] typeHead = structList.getHeadings();
                              int typeCol = 0;
                              for(int t=0;t<typeHead.length;t++)
                              {
                            	  String s =typeHead[t];
                            	  if(s.equals(type.getDisplay()))
                            	  {
                            		  typeCol = t;
                            	  }
                              }
                              result[i - m][typeCol] = partIfc2.getPartType().getDisplay();
                              //add producedBy
                              PartUsageList produced =PartUsageList.toPartUsageList("producedBy");
                              String[] producedHead = structList.getHeadings();
                              int producedCol = 0;
                              for(int p=0;p<producedHead.length;p++)
                              {
                            	  String s = producedHead[p];
                            	  if(s.equals(produced.getDisplay()))
                            	  {
                            		  producedCol =p;
                            	  }
                              }
                              result[i - m][producedCol] = partIfc2.getProducedBy().getDisplay();
                              //add lifeCycleState
                              PartUsageList state =PartUsageList.toPartUsageList("state");
                              String[] stateHead = structList.getHeadings();
                              int stateCol = 0;
                              for(int l=0;l<stateHead.length;l++)
                              {
                            	  String s = stateHead[l];
                            	  if(s.equals(state.getDisplay()))
                            	  {
                            		  stateCol =l;
                            	  }
                              }
                              result[i - m][stateCol] = partIfc2.getLifeCycleState().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              /************** liyz add end ******************/
                              //CCBegin SS1
                              PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                              String[] bomHead = structList.getHeadings();
                              int bomCol =0;
                              for(int b=0;b<bomHead.length;b++)
                              {
                            	String s = bomHead[b];
                            	if(s.equals(bomItem.getDisplay()))
                            	{
                            		bomCol = b;
                            	}
                              }
                              result[i - m][bomCol] = usageInterfaceItem.getBomItem();
                              PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                              String[] subHead = structList.getHeadings();
                              int subCol =0;
                              for(int z=0;z<subHead.length;z++)
                              {
                            	String s = bomHead[z];
                            	if(s.equals(subUnitNumber.getDisplay()))
                            	{
                            		subCol = z;
                            	}
                              }
                              result[i - m][subCol] = usageInterfaceItem.getSubUnitNumber();
                              //CCEnd SS1
                               //CCBegin SS2
                              PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                              String[] proHead = structList.getHeadings();
                              int proCol =0;
                              for(int b=0;b<proHead.length;b++)
                              {
                            	String s = proHead[b];
                            	if(s.equals(proVersion.getDisplay()))
                            	{
                            		proCol = b;
                            	}
                              }
                              result[i - m][proCol] = usageInterfaceItem.getProVersion();
                              //CCEnd SS2
                         }
                           String quan = String.valueOf(usageInterfaceItem
                                   .getQuantityString());
                           if(quan.endsWith(".0"))
                           {
                               quan = quan.substring(0, quan.length() - 2);
                           }
                           //result[i - m][4] = quan;
                           //liya add quantity
                           PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                           String[] quanHead=structList.getHeadings();
                           int quanCol = 0;
                           for(int q=0;q<quanHead.length;q++)
                           {
                           	String s =quanHead[q];
                           	if(s.equals(quantity.getDisplay()))
                           	{
                           		quanCol=q;
                           	}            	                	
                           }
                           result[i - m][quanCol] = quan;
                           //end
                        }
                    }
                    len = bvi.length - m;
                }
                else
                {
                    return;
                }
                setValues();
                	try
					{
						setMethods();
					} catch (QMException e1)
					{						
						e1.printStackTrace();
					}
                structList.addDetail(usageItem, result, begin, len);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    private void setMethods() throws QMException
    {
    	String[] listhead=PartServiceRequest.getListHead("usage");
		String[] headMethod=PartServiceRequest.getListMethod("usage");
		structList.setHeadings(listhead);
    	try
		{			
			structList.setHeadingMethods(headMethod);
		} catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
		
    }
    
    
    /**
     * 点击移去按钮实现的功能：删除选中的零部件，如果没有选中，则移去和查看按钮
     * 失效，数量和单位操作失效，数量为0。
     * @param event ActionEvent
     */
    void removeUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        removeUsage();
    }

    public void removeUsage()
    {
        PartDebug.debug("removeUsageJButton_actionPerformed（event） begin ...",
                this, PartDebug.PART_CLIENT);
        //获得所选中的零部件
        //modify by skx 2004.9.21 支持批量删除使用关系
        int[] i = structList.getSelectedRows();
        int m = 0;
        for (int j = 0; j < i.length; j++)
        {
            if(i[j] != -1)
            {
                //将选中的零部件从容器里移出
                //usesList.removeElementAt(structList.getCellText(i[j] - m, 9));
                //liyz add 列表头对应要删除的列                
                String[] head=structList.getHeadings();
                int col = 0;
                for(int k=0;k<head.length;k++)
                {               	                	
                	col=k;                	
                }
                usesList.removeElementAt(structList.getCellText(i[j] - m, col+1));                
                //end
                //将选中的零部件从使用结构列表上删除
                dtm.removeRow(i[j] - m);
                m++;
            }
        }
        usesMultiList_itemStateChanged(null);
        PartDebug
                .debug(
                        "removeUsageJButton_actionPerformed（event） end...return is void",
                        this, PartDebug.PART_CLIENT);
    }

    /**
     * 查看零部件属性。
     * @param event ActionEvent
     */
    void viewUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        viewSelectedItem();
    }

    /**
     * 查看使用结构列表中被选中的项。
     */
    public void viewSelectedItem()
    {
        PartDebug.debug("viewSelectedItem() begin....", this,
                PartDebug.PART_CLIENT);
        try
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            structList
                    .setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //使用结构列表中被选中的项
            int i = structList.getSelectedRow();
            if(i != -1)
            {
                //根据使用列表中所选项的ID获得容器中对应的使用关联项
                Object ob = structList.getDetail(i).getObject();
                QMPartMasterIfc master = null;
                if(ob instanceof QMPartIfc)
                {
                    master = (QMPartMasterIfc) ((QMPartIfc) ob).getMaster();
                }
                if(ob instanceof QMPartMasterIfc)
                {
                    master = (QMPartMasterIfc) ob;
		            String masterBsoID = master.getBsoID();
		            ///////////////////////modify begin////////////////////////////
		            //modify by muyp 20080507
		            //修改原因：对零部件主信息对象进行查看，页面显示不正确(TD1754)
		            ServiceRequestInfo info = new ServiceRequestInfo();
		            info.setServiceName("StandardPartService");
		            info.setMethodName("findPart");
		            Class[] theClass = {QMPartMasterIfc.class};
		            info.setParaClasses(theClass);
		            Object[] objs = {master};
		            info.setParaValues(objs);
		            RequestServer server = RequestServerFactory.getRequestServer();
		            try {
						Collection links = (Collection) server.request(info);
						if(links != null)
						{
		                    Iterator iter = links.iterator();
		                    String partID = "";
		                    if (iter.hasNext())
		                    {
		                    	QMPartIfc link = (QMPartIfc) iter.next();
		                        partID = link.getBsoID();
		                    }
		                    HashMap hashmap = new HashMap();
                            //modify by shf 2003/09/13
        		            hashmap.put("BsoID", masterBsoID);;
                            hashmap.put("bsoID", partID);
                            //转入页面查看零部件主信息的属性。
        		            //modify by shf 2003/09/13
        		            RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
        		                    hashmap);
        		            master = null;
        		            masterBsoID = null;
                            partID = "";
                            hashmap = null;
		                }
					} catch (QMRemoteException e) {
						// TODO 自动生成 catch 块
						e.printStackTrace();
					}
                    //////////////////////////modify end////////////////////////////////
		            return;
                    
                }
                //获得当前的筛选条件项
                ConfigSpecItem configSpecItem = getPartItem()
                        .getConfigSpecItem();
                //如果筛选条件项为空，获得当前客户端的筛选条件
                if(configSpecItem == null)
                {
                    PartConfigSpecIfc partConfigSpecIfc = null;
                    // 获得最近保存的筛选条件
                    try
                    {
                        partConfigSpecIfc = (PartConfigSpecIfc) PartHelper
                                .getConfigSpec();
                    }
                    catch (QMRemoteException qre)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "errorTitle", null);
                        JOptionPane.showMessageDialog(getParentFrame(), qre
                                .getClientMessage(), title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                    //如果筛选条件不为空，用筛选条件构造筛选条件项，否则新建筛选条件项
                    if(partConfigSpecIfc != null)
                    {
                        configSpecItem = new ConfigSpecItem(partConfigSpecIfc);
                    }
                    else
                    {
                        configSpecItem = new ConfigSpecItem();
                    }
                }
                //根据零部件主信息获取该零部件的所有符合配置规范的版本
                Vector partInfoVector = PartHelper.getAllVersions(master,
                        configSpecItem.getConfigSpecInfo());
                PartDebug.debug("*** 该零部件的所有符合配置规范的版本:" + partInfoVector,
                        PartDebug.PART_CLIENT);
                Object[] qmPartIfc = partInfoVector.toArray();
                PartDebug.debug("****************" + qmPartIfc, this,
                        PartDebug.PART_CLIENT);
                //如果为空，显示信息：“没有符合条件的版本”
                if(qmPartIfc == null)
                {
                    try
                    {
                        Object[] params = {((QMPartMasterInfo) master)
                                .getIdentifyObject().getIdentity()};
                        String message = getLocalizedMessage(
                                PartDesignViewRB.NO_QUA_VERSION, params);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "errorTitle", null);
                        JOptionPane.showMessageDialog(getParentFrame(),
                                message, title, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch (QMException ex)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "exception", null);
                        JOptionPane.showMessageDialog(getParentFrame(), ex
                                .getClientMessage(), title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(qmPartIfc.length == 0)
                {
                	
                    try
                    {
                        Object[] params = {((QMPartMasterInfo) master)
                                .getIdentifyObject().getIdentity()};
                        String message = getLocalizedMessage(
                                PartDesignViewRB.NO_QUA_VERSION, params);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "errorTitle", null);
                        JOptionPane.showMessageDialog(getParentFrame(),
                                message, title, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch (QMException ex)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "exception", null);
                        JOptionPane.showMessageDialog(getParentFrame(), ex
                                .getClientMessage(), title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                //获取所选中零部件的最新版本
                QMPartIfc part = null;
                PartDebug.debug("$$$$$$$$ 所选中零部件的最新版本: " + qmPartIfc[0],
                        PartDebug.PART_CLIENT);
                if(qmPartIfc[0] instanceof Object[])
                {
                    Object[] partObj = (Object[]) qmPartIfc[0];
                    part = (QMPartIfc) partObj[0];
                    PartDebug.debug("+++++++ 所选中零部件的最新版本:" + part,
                            PartDebug.PART_CLIENT);
                }
                //如果不为空，查看该零部件（最新版本）的属性（转到查看零部件属性页面）
                if(part != null)
                {
                    PartDebug.debug("-----------进入查看零部件属性页面------------",
                            PartDebug.PART_CLIENT);
                    String partID = part.getBsoID();
                    HashMap map = new HashMap();
                    map.put("bsoID", partID);
                    map.put("flag", "0");
                    RichToThinUtil.toWebPage(
                            "Part-Other-PartLookOver-001-0A.do", map);
                    PartDebug.debug("UsesJPanel:RichToThinUtil Successed !",
                            PartDebug.PART_CLIENT);
                }
            }
        }
        catch (QMRemoteException qre)
        {
            qre.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane
                    .showMessageDialog(getParentFrame(),
                            qre.getClientMessage(), title,
                            JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            structList.setCursor(Cursor.getDefaultCursor());
            setCursor(Cursor.getDefaultCursor());
        }
        PartDebug.debug("viewSelectedItem() end ....return is void", this,
                PartDebug.PART_CLIENT);
    }

    private JTable table = structList.getTable();

    private DefaultTableModel dtm;

    /**单位的列*/
    private TableColumn unitColumn;

    /**使用数量的列*/
    private TableColumn quantityColumn;

    /**使用数量的单元格编辑器*/
    private QuantityEditor quantityEditor;

    /**单位的单元格编辑器*/
    private UnitEditor unitEditor;

    /**
     * 在更新模式下，将单位和数量的列变为可编辑的，
     * 并且分别可用单位选择框和数量微调器进行编辑。
     */
    public void setListEditor()
    {
        table = structList.getTable();        
        dtm = (DefaultTableModel) table.getModel();
        /*liyz modify 在使用结构列表中可更新状态下，对有无单位和数量进行判断，
        并使单位和数量所在列和编辑器对应*/
        String[] heads=structList.getHeadings();
        int col1=0;//单位列
        int col2=0;//数量列
        //CCBegin SS1
        int col3=0;
        int col4=0;
        //CCEnd SS1
        //CCBegin SS2
        int col5=0;
        //CCEnd SS2
        if(updateMode)
        {
        	boolean unitflag=false;
        	boolean quanflag=false;
        	//CCBegin SS1
        	boolean snflag=false;
        	boolean bomflag=false;
        	//CCend SS1
        	//CCBegin SS2
        	boolean proflag=false;
        	//CCend SS2
        	for(int k=0;k<heads.length;k++)
        	{
        		String list = heads[k];
        		if(list.equals(getLabelsRB().getString("unitHeading")))
        		{
        	      unitflag=true;
        	      col1=k;        	      
        		}
        		if(list.equals(getLabelsRB().getString("quantityHeading")))
        		{
        			quanflag=true;
        			col2=k;        	      
        		}
        		//CCBegin SS1
        		if(list.equals(PartUsageList.toPartUsageList("subUnitNumber").getDisplay()))
        		{
        			snflag=true;
        			col3=k;        	      
        		}
        		if(list.equals(PartUsageList.toPartUsageList("bomItem").getDisplay()))
        		{
        			bomflag=true;
        			col4=k;        	      
        		}
        		//CCEnd SS1
         		//CCBegin SS2
        		if(list.equals(PartUsageList.toPartUsageList("proVersion").getDisplay()))
        		{
        			proflag=true;
        			col5=k;        	      
        		}
        		//CCEnd SS2
       	}
        	if(unitflag)
        	{
            DefaultTableCellRenderer unitColumnRenderer = new DefaultTableCellRenderer()
            {
                /**序列化ID*/
                static final long serialVersionUID = 1L;

                public void setValue(Object value)
                {
                    setText((value == null) ? "" : value.toString());
                }
            };
            
            unitColumnRenderer.setHorizontalAlignment(JLabel.LEFT);            
            unitColumn = table.getColumn(getLabelsRB().getString("unitHeading"));
            //设置"单位"一列采用单位选择框作为编辑器。
            unitEditor = new UnitEditor(new JComboBox(), structList);
            unitColumn.setCellEditor(unitEditor);
            unitColumn.setCellRenderer(unitColumnRenderer);
        	}
//            if(col1!=0)
//            {
//            	int[] cols={col1};
//            	structList.setColsEnabled(cols, true);            
//            }            
            if(quanflag)
            {
            quantityColumn = table.getColumn(getLabelsRB().getString("quantityHeading"));
            //设置"数量"一列采用数量微调器作为编辑器。
            quantityEditor = new QuantityEditor(new JComboBox(), structList);
            quantityColumn.setCellEditor(quantityEditor);
            quantityColumn.setCellRenderer(new QuantityRenderer());
            }
            
            //CCBegin SS1
            //CCBegin SS2
            //	int[] cols={col1,col2};
            //int[] cols={col1,col2,col3,col4}; 	
            int[] cols={col1,col2,col3,col4,col5}; 	
            //CCEnd SS2
            //CCEnd SS1
            	structList.setColsEnabled(cols, true);            
                      
        	
        }
        //end
        else
        {
            structList.setCellEditable(false);
            int[] coloumns = {0, 1, 2, 3, 4, 5, 6, 7, 8};
            structList.setColsEnabled(coloumns, false);
        }
    }

    /**
     * 如果选中一个零部件，则使移去按钮和查看按钮有效，否则失效。
     * @param event ItemEvent 未使用。
     */
    public void usesMultiList_itemStateChanged(java.awt.event.ItemEvent event)
    {
        PartDebug.debug("usesMultiList_itemStateChanged（event） begin ...",
                this, PartDebug.PART_CLIENT);
        int i = structList.getSelectedRow();
        if(i != -1)
        {
            viewUsageJB.setEnabled(true);
            //liyz add 选中零部件，显示设置按钮可用
            //partAttrSetJButton.setEnabled(true);//end
            if(updateMode)
            {
            	QMProductManager explor = ((QMProductManagerJFrame) this
                        .getParentFrame()).getMyExplorer();
            	boolean fromcapp=explor.getFromCapp();
            	if(!fromcapp)
                removeUsageJB.setEnabled(true);
            }
        }
        else
        {
            viewUsageJB.setEnabled(false);
            removeUsageJB.setEnabled(false);
            //liyz add
            //partAttrSetJButton.setEnabled(false);
        }
        PartDebug.debug(
                "usesMultiList_itemStateChanged（event） end...return is void",
                this, PartDebug.PART_CLIENT);
    }

    /**
     * 在窗口中清除零部件，将界面设置为最开始的界面。
     */
    public void clear()
    {
        if(ibaAttributesJPanel != null)
        {
            ibaAttributesJPanel.updateEditor(null, false);
            ibaAttributesJPanel.setUpdateMode(false);
        }
        try
        { 
            setPartItem(new PartItem());
            describedByJPanel.setPartItem(new PartItem());//用于清除参考文档Tab页和描述文档Tab页。//CR9
            referencesJPanel.setPartItem(new PartItem());
            
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        attributeJPanel.clear();
    }

  
    /**
     * 检查是否改变，如果改变提示是否保存：
     * 选择‘保存’则保存，选择‘取消’则不保存。
     */
    public boolean saveChange()
    {
        synchronized (cursorLock)
        {
            boolean flag = false;
            PartItem part = getPartItem();
            if(part != null && part.getPart().getPartNumber() != null && partData != null && dtm != null)
            {
                //通过标识工厂获得与给定值对象对应的显示标识对象。
                DisplayIdentity displayidentity = IdentityFactory
                        .getDisplayIdentity(part.getPart());
                //获得对象类型 + 标识
                String s = displayidentity.getLocalizedMessage(null);
                Object[] params = {s};
                String confirmExitMsg = QMMessage.getLocalizedMessage(RESOURCE,
                        "confirmsave", params);
                String warningDialogTitle = QMMessage.getLocalizedMessage(
                        RESOURCE, "errorTitle", null);
                /**提示是否保存*/
                int i = DialogFactory.showYesNoDialog(this, confirmExitMsg,
                        warningDialogTitle);
                //问题(2)20080306 zhangq begin:当不保存先前修改的零部件时，
                //描述文档面板的更改标志仍然是true（TD-1516）。
                if(i == DialogFactory.YES_OPTION)
                {
                    flag = true;
                    saveInThread(true);
                    //describedByJPanel.setChanged(false);
                }
                describedByJPanel.setChanged(false);
                //问题(2)20080306 zhangq end
            }
            return flag;
        }
    }

    /*
     * 070511王海军
     *  将判断是否改变方法从chang()中提取出来，在保存时进行校验。
     */
    public boolean isChange()
    {
        /**零部件是否改变的标志*/
        boolean isChange = false;
        PartItem partItem = getPartItem();
        if(partItem != null && partData != null && dtm != null)
        {
                isChange = attributeJPanel.ischange(partItem);
                basechange = attributeJPanel.ischange(partItem);
           
            /**然后检查使用结构是否改变*/
            boolean ss = partData.length != dtm.getRowCount();
            if(ss)
            {
                isChange = true;
                usagechange = true;
            }
            else
            {
                for (int row = 0; row < dtm.getRowCount(); row++)
                {
                    boolean in = false;
                    for (int i = 0; i < dtm.getRowCount(); i++)
                    {
//                        boolean hh = partData[row][2].toString().equals(
//                                dtm.getValueAt(i, 9).toString());
//                        if(hh)
//                        {
//                            if(!partData[row][0].toString().trim().equals(
//                                    dtm.getValueAt(i, 4).toString().trim()))
//                            {
//                                isChange = true;
//                                usagechange = true;
//                                break;
//                            }
//                            else if(!partData[row][1].toString().trim().equals(
//                                    dtm.getValueAt(i, 5).toString().trim()))
//                            {
//                                isChange = true;
//                                usagechange = true;
//                                break;
//                            }
                    	
                    	//liyz add
                    	PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                    	String[] quanHead = structList.getHeadings();
                    	int quanCol = 0;
                    	for(int m=0;m<quanHead.length;m++)
                    	{
                    		String s = quanHead[m];
                    		if(s.equals(quantity.getDisplay()))
                    		{
                    			quanCol = m;
                    		}
                    	}
                    	//CCBegin SS1
                        PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                        String[] subHead=structList.getHeadings();
                        int sunCol = 0;
                        for(int m=0;m<subHead.length;m++)
        	            {
        	            	String s =subHead[m];
        	               if(s.equals(subUnitNumber.getDisplay()))
        	            	{
        	            		sunCol=m;
        	            	}  
        	            	
        	            } 
                        PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                        String[] bomHead=structList.getHeadings();
        	            int bomCol = 0;
        	            for(int m=0;m<bomHead.length;m++)
        	            {
        	            	String s =bomHead[m];
        	               if(s.equals(bomItem.getDisplay()))
        	            	{
        	            		bomCol=m;
        	            	}   
        	            	
        	            } 
                    	//CCend SS1
                    	//CCBegin SS2
                        PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                        String[] proHead=structList.getHeadings();
                        int proCol = 0;
                        for(int m=0;m<proHead.length;m++)
        	            {
        	            	String s =proHead[m];
        	               if(s.equals(proVersion.getDisplay()))
        	            	{
        	            		proCol=m;
        	            	}  
        	            	
        	            } 
                    	//CCend SS2
                    	PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                    	String[] unitHead = structList.getHeadings();
                    	int unitCol = 0;
                    	for(int n=0;n<unitHead.length;n++)
                    	{
                    		String s =unitHead[n];
                    		if(s.equals(unit.getDisplay()))
                    		{
                    			unitCol = n;
                    		}
                    	}                    	
                        String[] head=structList.getHeadings();
                        int col = 0;
                        for(int k=0;k<head.length;k++)
                        {            	                	
                          col=k;                	
                        }
                        boolean hh = partData[row][2].toString().equals(
                        		dtm.getValueAt(i, col+1).toString());
                        if(hh)
                        {
                    	if(!partData[row][0].toString().trim().equals(
                                dtm.getValueAt(i, quanCol).toString().trim()))
                       	{
                       		isChange = true;
                       		usagechange = true;
                       		break;
                       	}
                    	else if(!partData[row][1].toString().trim().equals(
                                dtm.getValueAt(i, unitCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }else if(!partData[row][3].toString().trim().equals(
                                dtm.getValueAt(i, sunCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }else if(!partData[row][4].toString().trim().equals(
                                dtm.getValueAt(i, bomCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }
                     	//CCBegin SS2
                        else if(!partData[row][5].toString().trim().equals(
                                dtm.getValueAt(i, proCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }
                    	//CCEnd SS2
                           //end
                            
                            in = true;
                            break;
                        }
                    }
                    if(in == false)
                    {
                        isChange = true;
                        usagechange = true;
                        break;
                    }
                }
            }
            if(isChange)
            {
                /**接着检查事物特性是否改变*/
                if(ibaAttributesJPanel.isChanged())
                    ibachange = true;
                /**检查描述文档是否改变*/
                if(describedByJPanel.isChanged())
                    descrichange = true;
                /**检查参考文档是否改变*/
                if(docBsoID != null && referencesJPanel.isChanged(docBsoID))
                    refchange = true;
            }
            else
            {
                
                isChange = ibaAttributesJPanel.isChanged() || isChange;
                if(ibaAttributesJPanel.isChanged())
                    ibachange = true;
                isChange = describedByJPanel.isChanged() || isChange;
                if(describedByJPanel.isChanged())
                    descrichange = true;
                if(docBsoID != null)
                {
                    isChange = referencesJPanel.isChanged(docBsoID) || isChange;
                    if(referencesJPanel.isChanged(docBsoID))
                        refchange = true;
                }
            }
        }
        return isChange;
    }
//  update whj 07/12/6
    public void setButtonUpdate()
    {
    	 QMProductManager explor = ((QMProductManagerJFrame) this
                 .getParentFrame()).getMyExplorer();
         /**
          * set for capp 屏蔽调补充需求，2007.06.29 王海军
          * 
          */
        
         if(explor.getFromCapp())
         {
         	 addUsageJB.setEnabled(false);
         }
         else
         addUsageJB.setEnabled(true);
         removeUsageJB.setEnabled(false);
    }
    public void setButtonFalse()
    {
    	 addUsageJB.setEnabled(false);
    }
//  update whj 07/12/6
    public void setReference(boolean flag)
    {
    	isReference=flag;
    }
    public boolean getReference()
    {
    	return isReference;
    }
    
    //liyz add 显示设置监听
    class AttrSetListener implements ActionListener
    {
    	private String tab;
    	AttrSetListener(String s)
    	{
    		tab=s;
    	}
    	public void actionPerformed(ActionEvent e)
        {
        	partAttrSetJButton_actionPerformed(e,tab);
        }
    }
    
//  muyp 20080618 begin
    /**
     * 获取当前选择TAB页的索引(默认情况)
     * 0是使用结构，1是基本属性，2是事物特性，3是描述文档，
     * 4是参考文档,5是工艺路线,6是工艺汇总,7是工艺规程
     */
    public int getSelectTabbed()
    {
    	return contentsJTabbedPane.getSelectedIndex();
    }
    
    public DescribedByJPanel getDescJPanel()
    {
    	return describedByJPanel;
    }
    
    public ReferenceJPanel getRefJPanel()
    {
    	return referencesJPanel;
    }
    
    public IBAContainerEditor getIBAAttributesJPanel()
    {
    	return ibaAttributesJPanel;
    }
    //end
    
    //liyz add 工艺相关面板
    
    public TechnicsRegulateionPanel getRegulationJPanel()
    {
    	return regulateionJPanel;
    }
    
    public TechnicsSummaryPanel getSummaryJPanel()
    {
    	return summaryJPanel;
    }
    
    public TechnicsRoutePanel getRouteJPanel()
    {
    	return routeJPanel;
    }
    //liyz add 获取使用结构基本属性、事物特性、描述文档和参考文档的tab页索引
    public int getUsageIndex()
    {
    	return usageIndex;
    }
    public int getBaseIndex()
    {
    	return baseIndex;
    }
    public int getIbaIndex()
    {
    	return ibaIndex;
    }
    public int getDescIndex()
    {
    	return descIndex;
    }
    public int getRefIndex()
    {
    	return refIndex;
    }
    //Begin CR8
    /**
     * 产品信息管理器Tab页监听类
     * @author 王亮
     */
    class TabListener implements ChangeListener
    {
        TabListener()
        {
            super();
        }

        public void stateChanged(ChangeEvent e)
        {
            PartItem partitem = getPartItem();
            if(partitem != null)
            {
                if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "attributeJPanel")
                {
                    if(attributeJPanel.getIsShown() == false)
                    {
                        attributeJPanel.setPartItem(partitem, false);
                        attributeJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addEditAttributesJPanel")
                {
                    if(ibaAttributesJPanel.getIsShown() == false)
                    {
                        IBAHolderIfc ibaHolder = (IBAHolderIfc)partitem.getPart();
                        if(ibaHolder.getAttributeContainer() == null)
                        {
                            try
                            {
                                Class[] classes = {IBAHolderIfc.class, Object.class, Locale.class, MeasurementSystemDefaultView.class};
                                Object[] objects = {ibaHolder, null, RemoteProperty.getVersionLocale(), null};
                                ibaHolder = (IBAHolderIfc)IBAUtility.invokeServiceMethod("IBAValueService", "refreshAttributeContainer", classes, objects);
                            }catch(QMRemoteException remoteexception)
                            {
                                remoteexception.printStackTrace();
                                ibaHolder.setAttributeContainer(new DefaultAttributeContainer());
                            }
                        }
                        ibaAttributesJPanel.updateEditor(ibaHolder, mode == VIEW_MODE);

                        addClassificationButtons();//CR9
                        refreshClassificationControl();

                        ibaAttributesJPanel.setUpdateMode(updateMode);
                        ibaAttributesJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addDesJPanel")
                {
                    if(describedByJPanel.getIsShown() == false)
                    {
                        describedByJPanel.setReference(getReference());
                        describedByJPanel.setPartItem(partitem);
                        describedByJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addRefJPanel")
                {
                    if(referencesJPanel.getIsShown() == false)
                    {
                        referencesJPanel.setPartItem(partitem);
                        if(mainPart)
                        {
                            docBsoID = referencesJPanel.getDocumentBsoID();
                        }
                        referencesJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addRouteJPanel")
                {
                    if(routeJPanel.getIsShown() == false)
                    {
                        if(routeJPanel != null && partitem.getPart().getMasterBsoID() != null)
                            routeJPanel.setPartItem(partitem);
                        routeJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addSummaryJPanel")
                {
                    if(summaryJPanel.getIsShown() == false)
                    {
                        if(summaryJPanel != null)
                            summaryJPanel.setPartItem(partitem);
                        summaryJPanel.setIsShown(true);
                    }
                }else
                {
                    if(regulateionJPanel.getIsShown() == false)
                    {
                        if(regulateionJPanel != null)
                            regulateionJPanel.setPartItem(partitem);
                        regulateionJPanel.setIsShown(true);
                    }
                }
            }
        }
    }
    /**
     * 设置产品信息管理器中所有Tab页的显示标志
     * @param flag
     */
    public void setIsShown(boolean flag)
    {
        if(attributeJPanel != null)
            attributeJPanel.setIsShown(flag);
        if(ibaAttributesJPanel != null)
            ibaAttributesJPanel.setIsShown(flag);
        if(describedByJPanel != null)
            describedByJPanel.setIsShown(flag);
        if(referencesJPanel != null)
            referencesJPanel.setIsShown(flag);
        if(routeJPanel != null)
            routeJPanel.setIsShown(flag);
        if(summaryJPanel != null)
            summaryJPanel.setIsShown(flag);
        if(regulateionJPanel != null)
            regulateionJPanel.setIsShown(flag);
    }
    /**
     * 设置IsCreate标志,用于更新时设置要显示的Tab页信息要包括使用结构、参考文档、描述文档。
     * @param flag
     */
    public void setIsCreate(boolean flag)
    {
        this.isCreate = flag;
    }
    //End CR8
}
