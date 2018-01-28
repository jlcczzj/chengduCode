package com.faw_qm.part.client.design.view;
//SS1 增加BOM行项和子组 liuyang 2014-6-20
//SS2 增加生产版本 xianglx 2014-8-12
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.clients.beans.AssociationsPanel;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.view.QMPartList;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.model.PartAttrSetInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartDescriptionList;
import com.faw_qm.part.util.PartReferenceList;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartUsageList;
import com.faw_qm.part.util.TechnicsRegulateionList;
import com.faw_qm.part.util.TechnicsSummaryList;
import com.faw_qm.part.util.TechnicsRouteList;

/**
 * <p>Title:tab页显示属性设置对话框</p>
 * <p>Description:此界面根据tab页名称，显示相关列标题
 * 可根据需要实现属性的定制和刷新显示。</p>
 * @author 李延则，穆勇鹏
 *
 */
public class PartAttrSetJDialog extends JDialog
{
	/** 序列化ID */
	static final long serialVersionUID = 1L;
	/** 主面板 */
	private JPanel mainJPanel = new JPanel();

	/** 可选择属性滚动面板 */
	private JScrollPane mayOutPutJScrollPane = new JScrollPane();

	/** 定制属性滚动面板 */
	private JScrollPane outPutJScrollPane = new JScrollPane();

	/** “可选择属性”标签 */
	private JLabel mayOutputJLabel = new JLabel();

	/** “定制属性”标签 */
	private JLabel outPutJLabel = new JLabel();

	/**添加删除按钮面板 */
	private JPanel add_deleteJPanel = new JPanel();
	
	/** 添加属性按钮（右移） */
	private JButton addAttributeJButton = new JButton();

	/** 全部添加按钮（右移） */
	private JButton addAllJButton = new JButton();

	/** 删除属性按钮（左移） */
	private JButton deleteAttriJButton = new JButton();

	/** 删除全部按钮（左移） */
	private JButton deleteAllJButton = new JButton();

	/** 可选择属性列表 */
	private JList mayOutPutJList = new JList();

	/** 定制属性列表 */
	private JList outPutJList = new JList();
	
	/**上下移位按钮面板 */
	private JPanel up_downJPanel = new JPanel();

	/** 属性上移按钮 */
	private JButton upMoveJButton = new JButton();

	/** 属性下移按钮 */
	private JButton downMoveJButton = new JButton();
	
	/**零部件编号*/
    private String number = PartUsageList.toPartUsageList("number").getDisplay();
    
	/**零部件视图名称*/
    private String viewName = PartUsageList.toPartUsageList("viewName").getDisplay();
    
    /**零部件版本*/
    private String version = PartUsageList.toPartUsageList("iterationID").getDisplay();
    
    /**零部件数量*/
    private String quantity = PartUsageList.toPartUsageList("quantityString").getDisplay();
    
    /**零部件单位*/
    private String unit = PartUsageList.toPartUsageList("unitName").getDisplay();
    
    /**描述文档编号*/
    private String descNum = PartDescriptionList.toPartDescriptionList("docNum").getDisplay();
    
    /**描述文档版本*/
    private String descVersion = PartDescriptionList.toPartDescriptionList("versionID").getDisplay();
    
	/**参考文档编号*/
    private String refNum = PartReferenceList.toPartReferenceList("number").getDisplay();
    
    /**工艺路线编号*/
    private String routeNum = TechnicsRouteList.toTechnicsRouteList("routeListNumber").getDisplay();
    
    /**工艺汇总编号*/
    private String summaryNum = TechnicsSummaryList.toTechnicsSummaryList("tecTotalNumber").getDisplay();
    
    /**工艺规程编号*/
    private String regulationNum = TechnicsRegulateionList.toTechnicsRegulateionList("technicsNumber").getDisplay();
    //CCBegin SS1
    /**零部件子组号*/
    private String subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber").getDisplay();
    
    /**BOM行项*/
    private String bomItem = PartUsageList.toPartUsageList("bomItem").getDisplay();
    //CCEnd SS1
    //CCBegin SS2
    private String proVersion = PartUsageList.toPartUsageList("proVersion").getDisplay();
    //CCEnd SS2
	/**控制按钮面板*/
    private JPanel buttonJPanel = new JPanel();
    
    /**确定按钮*/
    private JButton okJButton = new JButton();

    /**取消按钮*/
    private JButton cancelJButton = new JButton();
    
    /**布局管理器*/
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();    
    
    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";
    /**枚举类*/
	private EnumeratedType[] partTabList ;	
	
    /**设置Tab页的名称*/
    private String TabName;
    /**可选择属性的名称*/
    private String[] mayOutPutAttriName;
    
    /**定制属性的默认值（编号，视图，版本，数量）*/

	private String[] outPutAttriName ={number,viewName,version,quantity,unit,subUnitNumber,bomItem};


	/**可选择属性名称的缓存*/
    private Vector leftName=new Vector();
	/**定制属性名称的缓存*/
    private Vector rightName=new Vector();
	/**浏览器*/
    protected QMExplorer myExplorer = null;
	/**使用结构列表*/
    private QMPartList usageList = null;
    /**描述文档和参考文档列表*/
    private PartMultiList refList = null;
    private AssociationsPanel descPanel =null;
    /**工艺相关列表*/
    private PartForCappList cappList = null;
    
    
    /**
     * 默认构造函数
     */
	public PartAttrSetJDialog()
	{
		this("",null);
	}
  
	/**
     * 判断具体tab页的构造函数
     * @param s tab名称
     */
	public PartAttrSetJDialog(String s,Component component)
	{
		super((JFrame)component,true);
		myExplorer=((QMProductManagerJFrame)component).getMyExplorer().getExplorer();
		TabName =s ;
		if(TabName.equals("usage"))
		{
			usageList = (QMPartList) myExplorer.getList();
		}
		if(TabName.equals("description"))
		{
			descPanel = myExplorer.getPartTaskJPanel().getDescJPanel().getDescPanel();		
		}
		if(TabName.equals("reference"))
		{
			refList = myExplorer.getPartTaskJPanel().getRefJPanel().getRefDocList();
		}
		if(TabName.equals("route"))
		{
			cappList = myExplorer.getPartTaskJPanel().getRouteJPanel().getRouteList();
		}
		if(TabName.equals("summary"))
		{
			cappList = myExplorer.getPartTaskJPanel().getSummaryJPanel().getSummaryList();
		}
		if(TabName.equals("regulation"))
		{
			cappList = myExplorer.getPartTaskJPanel().getRegulationJPanel().getRegulationList();
		}
		try
		{
			getListCollectAttributes();			
        	jbInit();
		}
		 catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	}
	
    
    /**
     * 获得显示设置所有属性名称
     */
    private void getListCollectAttributes()
    throws QMException
    {
    	String[] TabList=null;
    	if(TabName.equals("usage"))
    	{
    		partTabList=PartUsageList.getPartUsageListSet();
    		TabList=PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("description"))
    	{
    		partTabList= PartDescriptionList.getPartDescriptionListSet();
			TabList=PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("reference"))
    	{
    		partTabList= PartReferenceList.getPartReferenceListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("route"))
    	{
    		partTabList= TechnicsRouteList.getTechnicsRouteListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("summary"))
    	{
    		partTabList= TechnicsSummaryList.getTechnicsSummaryListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("regulation"))
    	{
    		partTabList= TechnicsRegulateionList.getTechnicsRegulateionListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	
    	outPutAttriName=TabList;
    	Vector ls=new Vector();
    	for(int m=0;m<TabList.length;m++)
    	{  
    		rightName.add(m, TabList[m]);
    	}
        int collectSize = partTabList.length;
       for(int a=0;a<collectSize;a++)
       {
    	   ls.addElement(partTabList[a].getValue());
       }
        for (int i = 0; i < collectSize; i++)
        {   
        	for(int j=0;j<TabList.length;j++)
        	{
        		String name=TabList[j];
        		if(partTabList[i].getValue().equals(name))
        			ls.removeElement(name);
        	}        	
        }
        mayOutPutAttriName = new String[ls.size()];
        Iterator ite=ls.iterator();
        for(int n=0;n<ls.size();n++)
        {
        	String s=ite.next().toString();
        	mayOutPutAttriName[n]=s;
        	leftName.add(n, s);
        }
       
    }	
	
	/**
     * 初始化
     * @throws Exception
     */
    private void jbInit()throws Exception
    {
    	mayOutputJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        outPutJLabel.setFont(new java.awt.Font("Dialog", 0, 12));

        this.setLayout(gridBagLayout);
        this.setSize(600,400);
        mainJPanel.setLayout(gridBagLayout1);
        mayOutputJLabel.setText("mayOutput");
        mayOutputJLabel.setBounds(new Rectangle(20, 10, 75, 19));
        outPutJLabel.setText("outPut");
        outPutJLabel.setBounds(new Rectangle(236, 10, 75, 19));
        addAttributeJButton.setBounds(new Rectangle(180, 29, 50, 29));
        addAttributeJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        addAttributeJButton.setMaximumSize(new Dimension(100, 23));
        addAttributeJButton.setMinimumSize(new Dimension(100, 23));
        addAttributeJButton.setPreferredSize(new Dimension(100, 23));
        addAttributeJButton.setText("");
        addAttributeJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addAttributeJButton_actionPerformed(e);
            }
        });
        addAllJButton.setBounds(new Rectangle(180, 78, 50, 29));
        addAllJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        addAllJButton.setMaximumSize(new Dimension(100, 23));
        addAllJButton.setMinimumSize(new Dimension(100, 23));
        addAllJButton.setPreferredSize(new Dimension(100, 23));
        addAllJButton.setText("");
        addAllJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addAllJButton_actionPerformed(e);
            }
        });
        deleteAttriJButton.setBounds(new Rectangle(180, 127, 50, 29));
        deleteAttriJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        deleteAttriJButton.setMaximumSize(new Dimension(100, 23));
        deleteAttriJButton.setMinimumSize(new Dimension(100, 23));
        deleteAttriJButton.setPreferredSize(new Dimension(100, 23));
        deleteAttriJButton.setMnemonic('0');
        deleteAttriJButton.setText("");
        deleteAttriJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteAttriJButton_actionPerformed(e);
            }
        });
        deleteAllJButton.setBounds(new Rectangle(180, 176, 50, 29));
        deleteAllJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        deleteAllJButton.setMaximumSize(new Dimension(100, 23));
        deleteAllJButton.setMinimumSize(new Dimension(100, 23));
        deleteAllJButton.setPreferredSize(new Dimension(100, 23));
        deleteAllJButton.setText("");
        deleteAllJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteAllJButton_actionPerformed(e);
            }
        });
        upMoveJButton.setBounds(new Rectangle(400, 90, 50, 29));
        upMoveJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        upMoveJButton.setMaximumSize(new Dimension(100, 23));
        upMoveJButton.setMinimumSize(new Dimension(100, 23));
        upMoveJButton.setPreferredSize(new Dimension(100, 23));
        upMoveJButton.setToolTipText("");
        upMoveJButton.setText("up");
        upMoveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                upMoveJButton_actionPerformed(e);
            }
        });
        downMoveJButton.setBounds(new Rectangle(400, 139, 51, 29));
        downMoveJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        downMoveJButton.setMaximumSize(new Dimension(100, 23));
        downMoveJButton.setMinimumSize(new Dimension(100, 23));
        downMoveJButton.setPreferredSize(new Dimension(100, 23));
        downMoveJButton.setText("down");
        downMoveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                downMoveJButton_actionPerformed(e);
            }
        });
//        mainJPanel.setMinimumSize(new Dimension(1, 1));
//        mainJPanel.setPreferredSize(new Dimension(1, 1));
        mainJPanel.add(mayOutPutJScrollPane,
                       new GridBagConstraints(0, 1, 1, 4, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(0, 10, 0, 0), 500, 500));
        mainJPanel.add(mayOutputJLabel,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 10, 5, 0), 0, 0));
        
        this.add(mainJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0,
                0));
        mayOutPutJScrollPane.getViewport().add(mayOutPutJList, null);
        mainJPanel.add(outPutJScrollPane,
                       new GridBagConstraints(2, 1, 1, 4, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(0, 0, 0, 8), 500, 500));
        outPutJScrollPane.getViewport().add(outPutJList, null);
        mainJPanel.add(outPutJLabel,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 0, 5, 0), 0, 0));
        mainJPanel.add(add_deleteJPanel,
                new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.NONE,
                                       new Insets(10, 5, 0, 5), 0, 0));
        add_deleteJPanel.setLayout(gridBagLayout2);
        
        add_deleteJPanel.add(addAttributeJButton,
                       new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(8, 10, 0, 10), 0, 0));
        add_deleteJPanel.add(addAllJButton,
                       new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(8, 10, 0, 10), 0, 0));
        add_deleteJPanel.add(deleteAttriJButton,
                       new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(8, 10, 0, 10), 0, 0));
        add_deleteJPanel.add(deleteAllJButton,
                      new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTH,
                                       GridBagConstraints.NONE,
                                       new Insets(8, 10, 0, 10), 0, 0));
        up_downJPanel.setLayout(gridBagLayout3);
        mainJPanel.add(up_downJPanel,
                new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 0, 0, 0), 0, 0));
        up_downJPanel.add(upMoveJButton,
                       new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 10, 5, 10), 0, 0));
        up_downJPanel.add(downMoveJButton,
                       new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 10, 0, 10), 0, 0));
        buttonJPanel.setLayout(gridBagLayout4);
        this.add(buttonJPanel,
        		new GridBagConstraints(0, 2, 1, 1, 1.0,
                        0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 10), 0, 0));
        okJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("OK");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		okJButton_actionPerformed(e);
        	}
        });
        cancelJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("Cancel");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
			public void actionPerformed(ActionEvent e)
			{
				cancelJButton_actionPerformed(e);
				
			}
        	
        });
        buttonJPanel.add(okJButton,
        		new GridBagConstraints(0, 0, 1, 1, 0.0,
                        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(10, 0, 10, 8), 0, 0));
        buttonJPanel.add(cancelJButton,
        		new GridBagConstraints(1, 0, 1, 1, 0.0,
                        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(10, 0, 10, 10), 0, 0));

        localize();
        //初始化可选择属性列表和定制属性列表
        populateList();
        //按界面使用规则刷新界面
        refresh();
        
    }
    
    /**
     * 本地化
     */
    public void localize()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "localize begin ....");
        mayOutputJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "mayOutput", null));
        outPutJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "outPut", null));
        addAttributeJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "addAttribute", null));
        addAllJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "addAll", null));
        deleteAttriJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "deleteAttri", null));
        deleteAllJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "deleteAll", null));
        upMoveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "upMove", null));
        downMoveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "downMove", null));
        okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "ok", null));
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "cancel", null));
        String title = QMMessage.getLocalizedMessage(RESOURCE,"AttrSet", null);
        this.setTitle(title);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "localize end....return is void");		
    }
	
    /**
     * 可选择属性列表和定制属性列表的初始化方法
     * @throws QMException 
     */
    public void populateList() throws QMException
    {
    	//可选择属性列表模型
        DefaultListModel mayExpModel = new DefaultListModel();    	
     
        for(int i = 0;i < mayOutPutAttriName.length;i++)
        {
        	
        	if(TabName.equals("usage"))
        	{
        		mayExpModel.addElement(PartUsageList.toPartUsageList(mayOutPutAttriName[i]).getDisplay()); 
        	}
        	else if(TabName.equals("description"))
        	{
        		mayExpModel.addElement(PartDescriptionList.toPartDescriptionList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("reference"))
        	{
        		mayExpModel.addElement(PartReferenceList.toPartReferenceList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("route"))
        	{
        		mayExpModel.addElement(TechnicsRouteList.toTechnicsRouteList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("summary"))
        	{
        		mayExpModel.addElement(TechnicsSummaryList.toTechnicsSummaryList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("regulation"))
        	{
        		mayExpModel.addElement(TechnicsRegulateionList.toTechnicsRegulateionList(mayOutPutAttriName[i]).getDisplay());
        	}
        	
        }
        //设置可选择属性列表数据模型
        mayOutPutJList.setModel(mayExpModel);
        mayOutPutJList.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                mayOutPutJList_keyPressed(e);
            }
        });
        //可选择属性列表鼠标单击监听
        mayOutPutJList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                mayOutPutJList_mouseClicked(e);
            }
        });
        //给可选择属性列表添加属性改变监听
        mayOutPutJList.addPropertyChangeListener(new java.beans.
                                                 PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                mayOutPutJList_propertyChange(e);
            }
        });
        //给定制属性列表添加属性改变监听
        outPutJList.addPropertyChangeListener(new java.beans.
                                                 PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
            	outPutJList_propertyChange(e);
            }
        });
        //给可选择属性列表添加项目选择监听
        mayOutPutJList.addListSelectionListener(new javax.swing.event.
                                                ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                mayOutPutJList_valueChanged(e);
            }
        });
        //定制属性列表的数据模型
        DefaultListModel expModel = new DefaultListModel();       
        
        for(int i =0;i<outPutAttriName.length;i++)
        {
        	if(TabName.equals("usage"))
        	{
        		expModel.addElement(PartUsageList.toPartUsageList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("description"))
        	{
        		expModel.addElement(PartDescriptionList.toPartDescriptionList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("reference"))
        	{
        		expModel.addElement(PartReferenceList.toPartReferenceList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("route"))
        	{
        		expModel.addElement(TechnicsRouteList.toTechnicsRouteList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("summary"))
        	{
        		expModel.addElement(TechnicsSummaryList.toTechnicsSummaryList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("regulation"))
        	{
        		expModel.addElement(TechnicsRegulateionList.toTechnicsRegulateionList(outPutAttriName[i]).getDisplay());
        	}
        	
        }
        //设置定制属性列表的数据模型
        outPutJList.setModel(expModel);
        //给定制属性列表添加鼠标单击监听
        outPutJList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                outPutJList_mouseClicked(e);
            }
        });

        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "populateList() end....return is void");
    }
    
    /**
     * 刷新界面
     */
    public void refresh()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "refresh() begin ....");
        //获得两列表的数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        /**可选择属性列表中有可选项时,全部添加按钮有效，否则无效
         *可选择属性列表中无可选项时,添加按钮无效
         */
        if (mayExpModel.isEmpty() == true)
        {
            addAllJButton.setEnabled(false);
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
            addAllJButton.setEnabled(true);
        }

        /**可选择属性列表中有选项被选中时,添加按钮有效，否则无效*/
        for (int i = 0; i < mayExpModel.size(); i++)
        {
            if (mayOutPutJList.isSelectedIndex(i) == true)
            {
                addAttributeJButton.setEnabled(true);
            }
            else
            {
                addAttributeJButton.setEnabled(false);
            }
        }

        /**定制属性列表中有可选项(除编号，名称)时,删除按钮和全部删除按钮有效，否则无效
         */ 
        for (int i = 0; i < expModel.size(); i++)
        {
            String a = expModel.getElementAt(i).toString();
            if(TabName.equals("usage"))
            {
            	if (a != viewName && a != number && a != version && a != unit &&
            		a != quantity && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }
            }
            
            if(TabName.equals("description"))
            {
            	if (a != descNum && a != descVersion && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }
            }
            if(TabName.equals("reference"))
            {
            	if (a != refNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }            	
            }
            if(TabName.equals("route"))
            {
            	if (a != routeNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }             	
            }
            if(TabName.equals("summary"))
            {
            	if (a != summaryNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                } 
            }
            if(TabName.equals("regulation"))
            {
            	if (a != regulationNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }             	
            }
        }

        /**定制属性列表中有选项被选中时,删除按钮有效，否则无效*/
        for (int i = 0; i < expModel.size(); i++)
        {
            if (outPutJList.isSelectedIndex(i) == true)
            {
                deleteAttriJButton.setEnabled(true);
            }
            else
            {
                deleteAttriJButton.setEnabled(false);
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "refresh() end....return is void");
    }
    
    /**
     * 可选择属性列表属性改变监听事件方法
     * <p>只有选中列表中某一项，添加按钮才被激活</p>
     * @param e 可选择属性列表属性改变事件
     */
    void mayOutPutJList_propertyChange(PropertyChangeEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_propertyChange() begin ....");        

        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_propertyChange() end....return is void");
    }

    /**
     * 定制属性列表属性改变监听事件方法
     * <p>只有选中列表中某一项，删除按钮才被激活</p>
     * @param e 可选择属性列表属性改变事件
     */
    void outPutJList_propertyChange(PropertyChangeEvent e)
    {
        if (outPutJList.isSelectionEmpty() == true)
        {
        	deleteAttriJButton.setEnabled(false);
        }
        else
        {
        	deleteAttriJButton.setEnabled(true);
        }        
    }

    /**
     * 可选择属性列表值改变监听事件方法
     * <p>该监听事件实现了可选择属性列表中有选项被选中时,添加按钮有效，否则无效</p>
     * @param e 列表选取事件
     */
    void mayOutPutJList_valueChanged(ListSelectionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_valueChanged() begin ....");
        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_valueChanged() end....return is void");
    }


    /**
     * 定制属性列表鼠标单击事件方法     
     * @param e 鼠标单击事件
     */
    void outPutJList_mouseClicked(MouseEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "outPutJList_mouseClicked() begin ....");
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        Object[] selectedValues = outPutJList.getSelectedValues();
        //获得输出属性的行索引
        int index = outPutJList.locationToIndex(e.getPoint());
        //根据属性索引获得属性名
        String indexString = (String) expModel.getElementAt(index);
        //如果选中“零部件编号“和“零部件名称“，则删除按钮失效，否则有效
        boolean isDelete = true;
        for(int i=0;i<selectedValues.length;i++)
        {
        	indexString = (String)selectedValues[i];
        	if(TabName.equals("usage"))
            {
            	if (indexString == viewName || indexString == number ||
            		indexString == version || indexString == quantity ||
            		indexString == unit)
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            	
            	if(indexString == number || indexString == quantity || indexString == unit)
            	{
            		upMoveJButton.setEnabled(false);
            		downMoveJButton.setEnabled(false);
            	}
            	else
            	{
            		upMoveJButton.setEnabled(true);
            		downMoveJButton.setEnabled(true);
            	}
            	
            }
            
            if(TabName.equals("description"))
            {
            	if (indexString == descNum || indexString == descVersion)
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            
            if(TabName.equals("reference"))
            {
            	if (indexString == refNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            if(TabName.equals("route"))
            {
            	if (indexString == routeNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            if(TabName.equals("summary"))
            {
            	if (indexString == summaryNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            if(TabName.equals("regulation"))
            {
            	if (indexString == regulationNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "outPutJList_mouseClicked() end....return is void ");
    }


    /**
     * “全部添加”按钮的行为事件方法
     * @param e 行为事件
     */
    void addAllJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "addAllJButton_actionPerformed（） begin ....");
        //获得可选择属性列表数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.
                                       getModel();
        //获得定制属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //把所有属性添加入定制属性列表
        for (int i = 0; i < mayExpModel.getSize(); i++)
        {
            expModel.addElement(mayExpModel.getElementAt(i));
            
            rightName.addElement(leftName.elementAt(i));
        }
        //把所有属性从可选择属性列表删除
        mayExpModel.removeAllElements();
        leftName.removeAllElements();
        //刷新界面
        refresh();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addAllJButton_actionPerformed() end ....return is void");
    }


    /**
     * 添加按钮的行为事件方法
     * @param e 行为事件
     */
    void addAttributeJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "addAttributeJButton_actionPerformed() begin ....");
        //获得可选择属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = mayOutPutJList.getSelectedIndices();
        //获得可选择属性列表数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
        //获得定制属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //向定制属性列表添加所选中的属性，同时将该属性从可选择属性列表中删除
        for (int i = 0; i < selected.length; i++)
		{
			String temp = mayExpModel.getElementAt(selected[i] - i).toString();

			mayExpModel.removeElementAt(selected[i] - i);			

			expModel.insertElementAt(temp, expModel.size());
			rightName.addElement(leftName.elementAt(selected[i] - i));
			leftName.removeElementAt(selected[i] - i);
		}

		// 刷新界面
        refresh();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addAttributeJButton_actionPerformed() end ....return is void");
    }


    /**
     * 删除按钮的行为事件方法
     * @param e 行为事件
     */
    void deleteAttriJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "deleteAttriJButton_actionPerformed（） begin ....");
        //获得定制属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = outPutJList.getSelectedIndices();
        //获得可选择属性列表数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.
                                       getModel();
        //获得定制属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //向可选择属性列表添加所选中的属性，同时将该属性从定制属性列表中删除
        for (int i = 0; i < selected.length; i++)
		{
			String temp = expModel.getElementAt(selected[i] - i).toString();
			mayExpModel.insertElementAt(temp, mayExpModel.size());
			expModel.removeElementAt(selected[i] - i);
			leftName.add(leftName.size(), rightName.elementAt(selected[i] - i));
			rightName.removeElementAt(selected[i] - i);
			
		}
        refresh();       
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "deleteAttriJButton_actionPerformed（） end....return is void");
    }


    /**
     * 全部删除按钮的行为事件方法
     * @param e 行为事件
     */
    void deleteAllJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "deleteAllJButton_actionPerformed（） begin ....");
        //获得可选择属性列表数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.
                                       getModel();
        //获得定制属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //把定制属性列表中的所有属性（除去编号和名称）填加入可选择属性列表
        for (int i = 0; i < expModel.size(); i++)
		{
        	String a = expModel.getElementAt(i).toString();
        	if(TabName.equals("usage"))
        	{
        		if (a != viewName && a != number &&
        			a !=version && a != quantity && a!= unit)
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
            
        	if(TabName.equals("description"))
        	{
        		if (a != descNum && a != descVersion)
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	
        	if(TabName.equals("reference"))
        	{
        		if (a != refNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	if(TabName.equals("route"))
        	{
        		if (a != routeNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	if(TabName.equals("summary"))
        	{
        		if (a != summaryNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	if(TabName.equals("regulation"))
        	{
        		if (a != regulationNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
		}        
        //删除定制属性列表中的所有属性
        expModel.removeAllElements();
        rightName.removeAllElements();
        //重新加上编号和名称属性，因为这两项是必选项
        if(TabName.equals("usage"))
        {
        	expModel.addElement(number);            
            expModel.addElement(quantity);
            expModel.addElement(unit);
            expModel.addElement(viewName);
            expModel.addElement(version);
            rightName.addElement("number");            
            rightName.addElement("quantityString");
            rightName.addElement("unitName");
            rightName.addElement("viewName");
            rightName.addElement("iterationID");
        }
        if(TabName.equals("description"))
        {
        	expModel.addElement(descNum);
            expModel.addElement(descVersion);
            rightName.addElement("docNum");
            rightName.addElement("versionID");
        }
        if(TabName.equals("reference"))
        {
        	expModel.addElement(refNum);
        	rightName.addElement("number");
        }
        if(TabName.equals("route"))
        {
        	expModel.addElement(routeNum);
        	rightName.addElement("routeListNumber");
        }
        if(TabName.equals("summary"))
        {
        	expModel.addElement(summaryNum);
        	rightName.addElement("tecTotalNumber");
        }
        if(TabName.equals("regulation"))
        {
        	expModel.addElement(regulationNum);
        	rightName.addElement("technicsNumber");
        }
        
        refresh();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "deleteAllJButton_actionPerformed（） end....return is void");
    }


    /**
     * 向上移动按钮的行为事件方法
     * @param e 行为事件
     */
    void upMoveJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "upMoveJButton_actionPerformed() begin ....");
        //获得定制属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = outPutJList.getSelectedIndices();
        //获得定制属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] > 0)
            {

                /**获得要改变位置的两个对象*/
                String a = expModel.getElementAt(selected[i]).toString();
                String b = expModel.getElementAt(selected[i] - 1).toString();
                //a1,b1为缓存中元素
                String a1 = rightName.elementAt(selected[i]).toString();
                String b1 = rightName.elementAt(selected[i] - 1).toString();
                if( b != number && b !=quantity && b != unit)
                {
                	/**二者的值对象内容交换*/
                    String temp;
                    temp = b;
                    b = a;
                    a = temp;
                    String temp1;
                    temp1 = b1;
                    b1 = a1;
                    a1 = temp1;
                    /**二者的值对象位置交换*/
                    expModel.setElementAt(a, selected[i]);
                    expModel.setElementAt(b, selected[i] - 1);
                    rightName.setElementAt(a1, selected[i]);
                    rightName.setElementAt(b1, selected[i] - 1);
                    /**将鼠标选择位置上移一个单位*/
                    outPutJList.setSelectedIndex(selected[i] - 1);
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "upMoveJButton_actionPerformed() end....return is void");
    }

    /**
     * 向下移动按钮的行为事件方法
     * @param e 行为事件
     */
    void downMoveJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "downMove_jButton_actionPerformed begin ....");
        //获得定制属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = outPutJList.getSelectedIndices();
        //获得定制属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] + 1 <= expModel.size() - 1)
            {
                /**获得要改变位置的两个对象*/
                String a = expModel.getElementAt(selected[i]).toString();
                String b = expModel.getElementAt(selected[i] + 1).toString();
                //a1,b1为缓存中元素
                String a1 = rightName.elementAt(selected[i]).toString();
                String b1 = rightName.elementAt(selected[i] + 1).toString();
                if ( a != number && a != quantity && a != unit)
                {
                	/**二者的值对象内容交换*/
                    String temp;
                    temp = b;
                    b = a;
                    a = temp;
                    String temp1;
                    temp1 = b1;
                    b1 = a1;
                    a1 = temp1;
                    /**二者的值对象位置交换*/
                    expModel.setElementAt(a, selected[i]);
                    expModel.setElementAt(b, selected[i] + 1);
                    rightName.setElementAt(a1, selected[i]);
                    rightName.setElementAt(b1, selected[i] + 1);
                    /**将鼠标选择位置下移一个单位*/
                    outPutJList.setSelectedIndex(selected[i] + 1);
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "downMove_jButton_actionPerformed end....return is void");
    }
    
    /**
     * 取消按钮的行为事件方法
     * @param e 行为事件
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
    	dispose();
    }
        
    /**
     * 确定按钮的行为事件方法
     * @param e 行为事件
     */   
    void okJButton_actionPerformed(ActionEvent event)
    {	     
        RequestHelper helper = new RequestHelper();
        String STANDARD = "StandardPartService";
    	Class[] paraClass = {};
	    Object[] para = {};
	    try
		{
			PartAttrSetInfo info=(PartAttrSetInfo)  helper.request(STANDARD,"getCurUserInfo", paraClass, para);
			String title=null;			
	        for(Iterator ite=rightName.iterator();ite.hasNext();)
	        {			        	
	        	if(title==null)
	        		title=ite.next().toString();
	        	else
	        		title =title+","+ite.next().toString();		        	
	        }
	        if(TabName.equals("usage"))
	        {
	        	info.setUsageAttr(title);
	        }
	        if(TabName.equals("description"))
	        {
	        	info.setDescriptionAttr(title);
	        }
	        if(TabName.equals("reference"))
	        {
	        	info.setReferenceAttr(title);
	        }
	        if(TabName.equals("route"))
	        {
	        	info.setCappRoute(title);
	        }
	        if(TabName.equals("summary"))
	        {
	        	info.setCappSummary(title);
	        }
	        if(TabName.equals("regulation"))
	        {
	        	info.setCappRegulation(title);
	        }
	        //持久化保存数据
	    	Class[] paraClass1 = {BaseValueIfc.class};
	 	    Object[] para1 = {info};
	        info=(PartAttrSetInfo)  helper.request("PersistService","saveValueInfo", paraClass1, para1);		        
		} 
	    catch (QMException e)
		{
			e.printStackTrace();
		}	    
	    try
		{
			refreshList();
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	    dispose();
    }
    
    /**
     * 刷新tab页的方法,重新调用获取列表的方法
     * @throws QMException
     */
    public void refreshList() throws QMException
    {    	
    	if(TabName.equals("usage"))
    	{
    		String[] listHead=PartServiceRequest.getListHead("usage");
    		String[] headMethod=PartServiceRequest.getListMethod("usage");    		
    		int i = listHead.length;
    		//CCBegin SS1
    		//CCBegin SS2
    	//	int[] in=new int[10];
    		//int[] in=new int[12];
    		int[] in=new int[13];
    		//CCEnd SS2
    		//CCEnd SS1
    		for(int j=0;j<i;j++)    
            {
            	in[j]=4;
            }
    		//CCBegin SS1
    		//CCBegin SS2
          //  for(int k=i;k<10;k++)
            //for(int k=i;k<12;k++)
            for(int k=i;k<13;k++)
            	//CCEnd SS2
            	//CCEnd SS1
            {
            	
           	 in[k]=0;             
            }
            usageList.setRelColWidth(in);	
            usageList.adjustWidth();
            usageList.setHeadings(listHead);
    		try
    		{
    			//myExplorer.setListMethods(headMethod);    		
    			usageList.setHeadingMethods(headMethod);    		
    			Explorable myexplorable[]=usageList.getMyExplorable(); 
    			if(myexplorable!=null)
        		{    
//    				myExplorer.getPartTaskJPanel().setListEditor();
//        			myExplorer.getPartTaskJPanel().setValues();
        			for(int l=0;l<myexplorable.length;l++)    		
        			usageList.removeDetail(myexplorable[l]);    		
        			usageList.addDetail(myexplorable);
        			
        		}    		
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	usageList.getTable().getColumnModel().getColumn(i).setMaxWidth(0);
        	usageList.getTable().getColumnModel().getColumn(i).setPreferredWidth(0);
        	usageList.getTable().getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);     
            
    	} 
    	
    	if(TabName.equals("description"))
    	{
    		String[] head=PartServiceRequest.getListHead("description");    		
    		String[] headMethod=PartServiceRequest.getListMethod("description");
    		//epmMethod
    		String[] headingMethodEPM = null;
    		int m = head.length;
            try
			{
				descPanel.setLabels(head);
			} catch (PropertyVetoException e1)
			{
				e1.printStackTrace();
			}
            descPanel.getMultiList().adjustWidth();            

    		try
			{   
    			//对epm文档单独处理，获取其对应属性值
    			headingMethodEPM = new String[headMethod.length];
    			for(int i=0;i<headMethod.length;i++)
    	         {
    	        	 if(headMethod[i].equals("getDocNum"))
    	        	 {
    	        		 headingMethodEPM[i]="getDocNumber";
    	        	 }
    	        	 else if(headMethod[i].equals("getDocName")||headMethod[i].equals("getVersionID"))
    	        	 {
    	        		 headingMethodEPM[i]=headMethod[i];
    	        	 }
    	         }
    			descPanel.setHeadingMethodsEPM(headingMethodEPM);
    			//end
    			descPanel.setOtherSideAttributes(headMethod);    			
    			descPanel.refreshDesc();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
			int size=descPanel.getMultiList().getTable().getTableHeader().getColumnModel().getColumn(0).getWidth();	        
			for(int j=0;j<m;j++)
			{
				descPanel.getMultiList().getTable().getColumnModel().getColumn(j).setMaxWidth(size);
				descPanel.getMultiList().getTable().getColumnModel().getColumn(j).setPreferredWidth(size);
				descPanel.getMultiList().getTable().getTableHeader().getColumnModel().getColumn(j).setMinWidth(size);
			}	
			descPanel.getMultiList().getTable().getColumnModel().getColumn(m).setMaxWidth(0);
			descPanel.getMultiList().getTable().getColumnModel().getColumn(m).setPreferredWidth(0);
			descPanel.getMultiList().getTable().getTableHeader().getColumnModel().getColumn(m).setMinWidth(0);
    	}
    	
    	if(TabName.equals("reference"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("reference");
    		String[] headMethod=PartServiceRequest.getListMethod("reference");
    		int[] colw =new int[3];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            refList.setRelColWidth(colw);
            refList.adjustWidth();
            refList.setHeadings(listHead);
    		try
    		{   			
    			refList.setHeadingMethods(headMethod);
    			refList.setListItems(refList.getDocumentItems());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}
    		int size=refList.getTable().getTableHeader().getColumnModel().getColumn(0).getWidth();
			for(int j=0;j<listHead.length;j++)
			{
				refList.getTable().getColumnModel().getColumn(j).setMaxWidth(size);
				refList.getTable().getColumnModel().getColumn(j).setPreferredWidth(size);
				refList.getTable().getTableHeader().getColumnModel().getColumn(j).setMinWidth(size);					
			}	
    		refList.getTable().getColumnModel().getColumn(listHead.length).setMaxWidth(0);
        	refList.getTable().getColumnModel().getColumn(listHead.length).setPreferredWidth(0);
        	refList.getTable().getTableHeader().getColumnModel().getColumn(listHead.length).setMinWidth(0);
    	}
    	
    	if(TabName.equals("route"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("route");
    		String[] headMethod=PartServiceRequest.getListMethod("route");
    		int[] colw =new int[6];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            cappList.setRelColWidth(colw);
            cappList.adjustWidth();
            cappList.setHeadings(listHead);
            try
    		{   			
    			cappList.setHeadingMethods(headMethod);
    			cappList.setListItems(cappList.getTechnicsRouteListInfo());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}
    		cappList.getTable().getColumnModel().getColumn(listHead.length).setMaxWidth(0);
        	cappList.getTable().getColumnModel().getColumn(listHead.length).setPreferredWidth(0);
        	cappList.getTable().getTableHeader().getColumnModel().getColumn(listHead.length).setMinWidth(0);
    	}
    	
    	if(TabName.equals("summary"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("summary");
    		String[] headMethod = PartServiceRequest.getListMethod("summary");
    		int[] colw =new int[5];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            cappList.setRelColWidth(colw);
            cappList.adjustWidth();
            cappList.setHeadings(listHead);
            try
    		{   			
    			cappList.setHeadingMethods(headMethod);
    			cappList.setListItems(cappList.getTotalSchemaItemInfo());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}	
    		cappList.getTable().getColumnModel().getColumn(listHead.length).setMaxWidth(0);
        	cappList.getTable().getColumnModel().getColumn(listHead.length).setPreferredWidth(0);
        	cappList.getTable().getTableHeader().getColumnModel().getColumn(listHead.length).setMinWidth(0);
    	}
    	
    	if(TabName.equals("regulation"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("regulation");
    		String[] headMethod=PartServiceRequest.getListMethod("regulation");
    		int[] colw =new int[6];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            cappList.setRelColWidth(colw);
            cappList.adjustWidth();
            cappList.setHeadings(listHead);
            try
    		{   			
    			cappList.setHeadingMethods(headMethod);
    			cappList.setListItems(cappList.getQMTechnicsInfo());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    }    
    
    void mayOutPutJList_mouseClicked(MouseEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_mouseClicked() begin ....");
        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_mouseClicked() end....return is void ");
    }

    void mayOutPutJList_keyPressed(KeyEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_keyPressed() begin ....");
        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_keyPressed() end....return is void ");
    }

}
