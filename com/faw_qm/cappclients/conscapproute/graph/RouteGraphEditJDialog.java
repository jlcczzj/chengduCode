/**
 * 生成程序 RouteGraphEditJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 与产品最新代码比较，多出部分代码，注释掉。该部分会引起路线编辑后无法直接刷新零部件表界面路线。 liunan 2014-3-19
 * SS2 长特路线渐进式搜索 文柳  2015-4-13
 * SS3 成都提出，默认最后一个为装配路线，其余全为制造路线。 liunan 2016-10-13
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.faw_qm.cappclients.conscapproute.util.GraphRB;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.client.view.CodeManageTree;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.QMHelpContext;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.technics.consroute.util.RouteWrapData;

/**
 * <p> Title:编辑工艺路线图主界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public class RouteGraphEditJDialog extends JDialog 
{
    /** 工具条 */
    public StickyToolbar theStickyToolbar;

    /** 图板 */
    public GraphPanel graphPanel;

    private JScrollPane jScrollPane = new JScrollPane();

    /** 路线单位树 */
    private CodeManageTree departmentTree;

    private JLabel jLabel1 = new JLabel();

    private JPanel leftJPanel = new JPanel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JScrollPane graphViewPane = new JScrollPane();

    private JPanel buttonJPanel = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private JButton helpJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private JLabel statusJLabel = new JLabel();

    private JLabel jLabel2 = new JLabel();

    private JPanel rightJPanel = new JPanel();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    /**
     * 进行"指针"操作的动作标识
     */
    public static final String POINTER = "pointer";

    /**
     * 进行"连接"操作的动作标识
     */
    public static final String LINK = "link";

    /** 添加节点操作的标识 */
    public static final String HOME = "home";

    /** 更新节点属性的动作标识 */
    public static final String UPDATE = "update";

    /** 删除节点或连线的动作标识 */
    public static final String DELETE = "delete";

    /** 资源文件路径 */
    public static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 父窗口 */
    private Frame parentFrame;

    /** 当前工艺路线 */
    private TechnicsRouteIfc myRoute;

    /** 当前路线表 */
    private TechnicsRouteListIfc myRouteList;

    /** 当前零部件 */
    private QMPartMasterIfc myPart;

    /** 标记:是否保存数据 */
    public boolean isSave = false;

    /** 缓存:路线节点的集合 */
    public Vector routeNodeItemVector = new Vector();

    /** 缓存:路线连接的集合 */
    public Vector routeLinkItemVector = new Vector();

    /** 缓存:路线分支的对照表(键为TechnicsRouteBranchInfo,值为graphPanel.pathVector中的对应元素) */
    public Hashtable branchHashtable = new Hashtable();

    /** 标记:当前所选择的路线单位 */
    private String currentDepartment;

    //帮助上下文环境
    private QMHelpContext helpContext;

    //帮助系统
    private QMHelpSystem helpSystem;

    private String partMasterID;
    //存放路线串的列表
    private ComponentMultiList qMMultiList = new ComponentMultiList();

    private JSplitPane splitPane = new JSplitPane();
    private JPanel bottonJPanel = new JPanel();
    private JButton mainJButton = new JButton();
    /** 缓存:路线分支 key=路线分支的BsoID, value=路线分支值对象 */
    public HashMap branchMap = new HashMap();
    /** 缓存:路线分支 key=路线分支的BsoID, value=路线分支值对象 */
    public HashMap branchMap1 = new HashMap();
    //CCBegin SS2
    private JLabel searchJJLabel = new JLabel();
    private JTextField searchJJText = new JTextField();
    private JPanel searchJPanel = new JPanel();
    private String comp="";
    
    //CCEnd SS2
    /**
     * 构造函数
     * @param frame 父窗口
     * @param list 要编辑的路线所属的路线表
     * @param route 要编辑的路线
     * @param partMaster 要编辑路线的零部件
     * @roseuid 402A22D6014E
     */
    public RouteGraphEditJDialog(Frame frame, TechnicsRouteListIfc list, TechnicsRouteIfc route, RouteWrapData wrapData)
    {
        super(frame, "", true);

        this.parentFrame = frame;
        graphPanel = new GraphPanel(parentFrame, true, this);
        this.setRouteList(list);
        this.setTechnicsRoute(route, wrapData);
        this.partMasterID = wrapData.getPartMasterID();

        try
        {
        	//CCBegin SS2
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
        	//CCEnd SS2
        	
            jbInit();
            //CCBegin SS1
            /*try
            {
                routeNodeItemVector.clear();
                routeLinkItemVector.clear();
                routeNodeItemVector = graphPanel.getAllRouteNodeItems();
                routeLinkItemVector = graphPanel.getAllRouteNodeLinkItems();
                //if(verbose)
                {
                    System.out.println("节点的个数:" + routeNodeItemVector.size());
                    if(routeLinkItemVector != null)
                        System.out.println("连接的个数:" + routeLinkItemVector.size());
                }

                isSave = true;
                //生成新的路线串
                graphPanel.formRouteBranch();
                branchToMultiList(getRouteBranches());
            }catch(Exception ex)
            {
                String message =ex.getMessage();
                DialogFactory.showInformDialog(this, message);
                return;
            }*/
            //CCEnd SS1
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 界面初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.getContentPane().setLayout(gridBagLayout4);
        this.setSize(900, 650);
        this.addWindowListener(new RouteGraphEditJDialog_this_windowAdapter(this));
        this.setModal(true);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        theStickyToolbar = new StickyToolbar();
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("路线单位");
        leftJPanel.setLayout(gridBagLayout1);
        buttonJPanel.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定");
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
        cancelJButton.setText("取消");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        helpJButton.setMaximumSize(new Dimension(75, 23));
        helpJButton.setMinimumSize(new Dimension(75, 23));
        helpJButton.setPreferredSize(new Dimension(75, 23));
        helpJButton.setToolTipText("Help");
        helpJButton.setText("帮助");

        helpJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                helpJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(4, 22));
        statusJLabel.setMinimumSize(new Dimension(4, 22));
        statusJLabel.setPreferredSize(new Dimension(4, 22));
        statusJLabel.setText("编辑路线图");
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("路线图");
        rightJPanel.setLayout(gridBagLayout3);
        leftJPanel.setMaximumSize(new Dimension(150, 2147483647));
        leftJPanel.setMinimumSize(new Dimension(150, 42));
        leftJPanel.setPreferredSize(new Dimension(150, 150));
        theStickyToolbar.setMaximumSize(new Dimension(30, 190));
        theStickyToolbar.setMinimumSize(new Dimension(30, 190));
        theStickyToolbar.setPreferredSize(new Dimension(30, 190));

        graphPanel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                graphPanel_mouseEntered(e);
            }
        });
        mainJButton.setMaximumSize(new Dimension(105, 23));
        mainJButton.setMinimumSize(new Dimension(105, 23));
        mainJButton.setPreferredSize(new Dimension(105, 23));
        //mainJButton.setToolTipText("Ok");
        //mainJButton.setMnemonic('Y');
        mainJButton.setText("设置主要路线");
        mainJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainJButtonJButton_actionPerformed(e);
            }
        });
        //CCBegin SS2
        searchJJLabel.setText("搜索：");
        searchJJText.setMaximumSize(new Dimension(100, 22));
        searchJJText.setMinimumSize(new Dimension(100, 22));
        searchJJText.setPreferredSize(new Dimension(100, 22));
        searchJPanel.add(searchJJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
        searchJPanel.add(searchJJText, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
      
        searchJJText.addKeyListener(new KeyAdapter()//键盘监听按钮
        {
            public void keyReleased(KeyEvent e)
            {
        		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)departmentTree.getModel().getRoot();
            	
            	if(searchJJText.getText()!=null&&(!searchJJText.getText().equals(""))&&(searchJJText.getText().length()==1)){
            		//输入第一个字符，展开树
            		departmentTree.addDirectChild((BaseValueIfc)rootNode.getUserObject(),rootNode);
            		departmentTree.setSelectCoding((BaseValueIfc)rootNode.getUserObject());
            		departmentTree.setSelectionPath((TreePath)departmentTree.getPathForRow(0));
            		String inputStr = searchJJText.getText().toUpperCase();
            		//根据第一个字符匹配供应商
            		for(int i  = 0 ; i<rootNode.getChildCount();i++){
            			DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
            			Object childObject = childTreeNode.getUserObject();
            			TreePath treePath = new TreePath(childTreeNode.getPath()); 
            			if(childObject instanceof CodingInfo){
            				CodingInfo temCoding = (CodingInfo)childObject;
            				
            				if(temCoding.getSearchWord().startsWith(inputStr)){
            					departmentTree.setSelectCoding(temCoding);
            					departmentTree.setSelectionPath(treePath);
            					//搜索到即返回
            					return;
            				}
            			}
            			
            		}
            		//根据多个字符匹配供应商
            	}else if(searchJJText.getText().length()>1){
            		String inputStr = searchJJText.getText().toUpperCase();
            		for(int i  = 0 ; i<rootNode.getChildCount();i++){
            			DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
            			Object childObject = childTreeNode.getUserObject();
            			TreePath treePath = new TreePath(childTreeNode.getPath()); 
            			if(childObject instanceof CodingInfo){
            				CodingInfo temCoding = (CodingInfo)childObject;
            				if(temCoding.getSearchWord().startsWith(inputStr)){
            					departmentTree.setSelectCoding(temCoding);
            					departmentTree.setSelectionPath(treePath);
            					//搜索到即返回
            					return;
            				}
            			}
            			
            		}
            	}
            	
            }
       });

//        
//        textField.addKeyListener(new KeyAdapter() { 
//            
//            @Override 
//            public void keyPressed(KeyEvent e) { // keyPressed 与 keyReleased是有时间差的！！  
//                // TODO Auto-generated method stub  
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
//                    char[] text = textField.getText().toCharArray(); 
//                    for (char ch : text) { 
//                        if (!Character.isDigit(ch)) { 
////                          textField.removeKeyListener(this);  
//                            JOptionPane.showMessageDialog(container,  
//                                    "Your input contains non-digit characters !",  
//                                    "Warning", JOptionPane.WARNING_MESSAGE); 
//                            textField.setText(""); 
////                          textField.addKeyListener(this);  
//                            return; 
//                        } 
//                    } 
//                } 
//            } 
//        }); 
//    } 

        
        //CCEnd SS2
        //departmentTree.addMouseListener(new
        // RouteGraphEditJDialog_departmentTree_mouseAdapter(this));
        leftJPanel.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
        //CCBegin SS2
        if(comp.equals("ct")){
            leftJPanel.add(searchJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
            leftJPanel.add(jScrollPane, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));   
        }else{
        	  leftJPanel.add(jScrollPane, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));   
        }
        //CCEnd SS2
        //jScrollPane.getViewport().add(departmentTree, null);
        this.getContentPane().add(leftJPanel, new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 10, 0, 0), 0, 0));

        theStickyToolbar.addActionListener(new ToolsActionListener());
        this.getContentPane().add(theStickyToolbar, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(40, 10, 0, 10), 0, 0));
        this.getContentPane().add(buttonJPanel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 0, 20, 10), 0, 0));
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        //   buttonJPanel.add(helpJButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
        //   0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
        //   new Insets(0, 8, 0, 0), 0, 0));

        this.getContentPane().add(statusJLabel, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(splitPane, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 0, 0, 10), 0, 0));
        rightJPanel.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
        rightJPanel.add(graphViewPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        rightJPanel.add(mainJButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));

        graphViewPane.getViewport().add(graphPanel, null);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBorder(null);
        splitPane.setOneTouchExpandable(true);
        splitPane.add(rightJPanel, JSplitPane.TOP);
        splitPane.add(qMMultiList, JSplitPane.BOTTOM);
        splitPane.setLastDividerLocation(370);
        splitPane.setDividerLocation(370);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(1.0);
        ImageIcon image1 = new ImageIcon(getClass().getResource("/images/route_pointer.gif"));
        ImageIcon image2 = new ImageIcon(getClass().getResource("/images/route_link.gif"));
        ImageIcon image3 = new ImageIcon(getClass().getResource("/images/route_makeNode.gif"));
        ImageIcon image4 = new ImageIcon(getClass().getResource("/images/route_editNode.gif"));
        ImageIcon image5 = new ImageIcon(getClass().getResource("/images/public_delete.gif"));
        theStickyToolbar.add(POINTER, image1, "");
        theStickyToolbar.add(HOME, image3, QMMessage.getLocalizedMessage(RESOURCE, "node", null));
        theStickyToolbar.add(LINK, image2, QMMessage.getLocalizedMessage(RESOURCE, "link", null));
        theStickyToolbar.addJButton(UPDATE, image4, QMMessage.getLocalizedMessage(RESOURCE, "editNode", null));
        theStickyToolbar.addJButton(DELETE, image5, QMMessage.getLocalizedMessage(RESOURCE, "deleteNodeOrLink", null));
        theStickyToolbar.setSelected(POINTER);
        this.initializeHelp();
        localize();
        //添加键盘监听
        this.addKeyListener(new GraphComponentKeyListener(this));
        //初始化列表
        
        qMMultiList.setHeadings(new String[]{"id", "序号", "制造路线", "装配路线", "主要路线", "路线分支"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 1, 0});
        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[]{4}, true);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setCheckboxEditable(true);
    }

    /**
     * 本地化
     */
    private void localize()
    {
        try
        {
            //            this.setTitle(QMMessage.getLocalizedMessage(RESOURCE,
            //                    "editGraphTitle", null)
            //                    + " < "
            //                    + this.getRouteList().getRouteListNumber()
            //                    + " : "
            //                    + this.getPartMaster().getPartNumber() + " >");
            jLabel1.setText(QMMessage.getLocalizedMessage(RESOURCE, "department", null));
            jLabel2.setText(QMMessage.getLocalizedMessage(RESOURCE, "routeGraph", null));
            statusJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "editGraphTitle", null));
            okJButton.setText("确定(Y)");
            cancelJButton.setText("取消(C)");
            helpJButton.setText("帮助(H)");
            helpJButton.setMnemonic('H');
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 向本界面添加路线单位树
     * @param departmentTree com.faw_qm.codemanage.client.view.CodeManageTree
     */
    public void addDepartmentTree(CodeManageTree departmentTree)
    {
        if(departmentTree != null)
        {
            this.departmentTree = departmentTree;
            jScrollPane.getViewport().add(this.departmentTree, null);
            this.departmentTree.addMouseListener(new RouteGraphEditJDialog_departmentTree_mouseAdapter(this));
            this.departmentTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
            {
                public void valueChanged(TreeSelectionEvent e)
                {
                    departmentTree_valueChanged(e);
                }
            });
        }
    }

    /**
     * 初始化路线单位树
     */
    /**
     * private void initTree() { try { //初始化路线单位树 CodingClassificationIfc cc = null; if(this.getRouteList().getRouteListLevel().equals(RouteListLevelType.FIRSTROUTE.getDisplay())) { Class[] c =
     * {String.class,String.class}; Object[] obj = {"组织机构","代码分类"}; cc = (CodingClassificationIfc)RParentJPanel.useServiceMethod( "CodingManageService","findClassificationByName",c,obj); } else { cc =
     * (CodingClassificationIfc)RParentJPanel.refreshInfo( this.getRouteList().getRouteListDepartment()); } if(cc!=null) { departmentTree = new CodeManageTree(cc);
     * departmentTree.setShowsRootHandles(false); departmentTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() { public void valueChanged(TreeSelectionEvent e) {
     * departmentTree_valueChanged(e); } }); } } catch (QMRemoteException ex) { JOptionPane.showMessageDialog(this,ex.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE,"information",null),
     * JOptionPane.INFORMATION_MESSAGE); } }
     */

    /**
     * 选择路线单位
     * @param e TreeSelectionEvent javax.swing.event.TreeSelectionEvent
     */
    void departmentTree_valueChanged(TreeSelectionEvent e)
    {

    }

    /**
     * 当鼠标按下时，设置选中
     * @param e 鼠标事件
     */
    void departmentTree_mousePressed(MouseEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)departmentTree.getLastSelectedPathComponent();
        if(node == null)
            return;
        Object object = node.getUserObject();
        if(object instanceof CodingIfc || (object instanceof CodingClassificationIfc))
        {
            String departmentName = null;
            if(object instanceof CodingIfc)
            {
                CodingIfc bvi = (CodingIfc)object;
                departmentName = bvi.getShorten();
                graphPanel.setCurrentRouteDepartment(bvi.getBsoID(), departmentName);
            }else if(object instanceof CodingClassificationIfc)
            {
                CodingClassificationIfc bvi = (CodingClassificationIfc)object;
                departmentName = bvi.getClassSort();
                graphPanel.setCurrentRouteDepartment(bvi.getBsoID(), departmentName);
            }
            graphPanel.setMode(1);
        }
    }

    /**
     * 设置路线表
     * @param list 当前路线所属的路线表
     */
    private void setRouteList(TechnicsRouteListIfc list)
    {
        myRouteList = list;
    }

    /**
     * 获得当前路线表
     * @return 当前路线所属的路线表
     */
    public TechnicsRouteListIfc getRouteList()
    {
        return myRouteList;
    }

    /**
     * 设置要编辑路线的零部件主信息
     * @param partMaster 零部件主信息
     */
    private void setPartMaster(QMPartMasterIfc partMaster)
    {
        myPart = partMaster;
    }

    /**
     * 获得当前编辑路线的零部件主信息
     * @return 零部件主信息
     */
    public QMPartMasterIfc getPartMaster()
    {
        return myPart;
    }

    /**
     * 设置当前编辑的工艺路线对象
     * @param route 工艺路线对象
     */
    public void setTechnicsRoute(TechnicsRouteIfc route, RouteWrapData wrapData)
    {
        myRoute = route;
        graphPanel.setTechnicsRoute(route);
        try
        {
            setGraphData(wrapData);
        }catch(Exception ex)
        {

            String message = ex.getMessage();
            DialogFactory.showInformDialog(this, message);
        }
    }

    /**
     * 设置当前编辑的工艺路线对象
     * @param route 工艺路线对象
     */
    public void setTechnicsRoute(TechnicsRouteIfc route)
    {
        myRoute = route;
        graphPanel.setTechnicsRoute(route);
        try
        {
            setGraphData();
        }catch(Exception ex)
        {

            String message = ex.getMessage();
            DialogFactory.showInformDialog(this, message);
        }
    }

    /**
     * 获得并设置显示路线图的数据
     * @throws QMRemoteException
     */
    private void setGraphData()
    {
        if(myRoute == null || myRoute.getBsoID() == null)
            return;
        try
        {
            //调用服务,根据路线对象获得路线节点和连接
            Class[] c = {String.class};
            Object[] obj = {myRoute.getBsoID()};
            Object[] map;
            map = (Object[])RequestHelper.request("consTechnicsRouteService", "getRouteNodeAndNodeLink", c, obj);

            Vector nodeVector = (Vector)map[0];
            Vector linkVector = (Vector)map[1];
            DefaultGraphModel model = new DefaultGraphModel();
            //缓存所有的节点图元
            Vector gnv = new Vector();
            if(nodeVector != null && nodeVector.size() > 0)
            {
                for(int i = 0;i < nodeVector.size();i++)
                {
                    RouteNodeInfo nodeinfo = (RouteNodeInfo)nodeVector.elementAt(i);
                    RouteItem nodeitem = new RouteItem(nodeinfo);
                    DefaultGraphNode newnode = new DefaultGraphNode();
                    newnode.setRouteItem(nodeitem);
                    newnode.setDepartmentName(nodeinfo.getNodeDepartmentName());
                    newnode.setPosition(new Long(nodeinfo.getX()).intValue(), new Long(nodeinfo.getY()).intValue());
                    newnode.updateImage();
                    model.graphNodeVector.addElement(newnode);
                    gnv.addElement(newnode);
                }
            }

            if(linkVector != null && linkVector.size() > 0)
            {
                String leftNodeID, rightNodeID, curBsoID;
                for(int j = 0;j < linkVector.size();j++)
                {
                    RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo)linkVector.elementAt(j);
                    RouteItem linkitem = new RouteItem(linkinfo);
                    DefaultGraphLink newlink = new DefaultGraphLink();
                    newlink.setRouteItem(linkitem);
                    leftNodeID = linkinfo.getSourceNode().getBsoID();
                    rightNodeID = linkinfo.getDestinationNode().getBsoID();
                    for(int k = 0;k < gnv.size();k++)
                    {
                        DefaultGraphNode graphNode = (DefaultGraphNode)gnv.elementAt(k);
                        curBsoID = graphNode.getRouteItem().getObject().getBsoID();
                        if(leftNodeID.equals(curBsoID))
                        {
                            newlink.setInitPredecessor(graphNode);
                        }
                        if(rightNodeID.equals(curBsoID))
                        {
                            newlink.setInitSuccessor(graphNode);
                        }
                    }

                    model.graphLinkVector.addElement(newlink);
                }
            }

            graphPanel.setModel(model);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this.getParentFrame(), message);
            ex.printStackTrace();
        }
    }

    /**
     * 获得当前编辑的工艺路线对象
     * @return 当前编辑的工艺路线对象
     */
    public TechnicsRouteIfc getTechnicsRoute()
    {
        return myRoute;
    }

    /**
     * 获得并设置显示路线图的数据
     * @param wrapData 客户端获得的零件路线信息封装类
     */
    private void setGraphData(RouteWrapData wrapData)
    {
        Vector nodes = null;
        Vector links = null;
        if(wrapData != null)
        {
            HashMap map = wrapData.getRouteMap();
            if(map != null)
            {
                //获得节点集合
                nodes = (Vector)map.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
                //获得节点关联集合
                links = (Vector)map.get(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
            }
        }

        if(nodes == null || nodes.size() == 0)
        {
            return;
        }
        DefaultGraphModel model = new DefaultGraphModel();
        //缓存所有的节点图元
        Vector gnv = new Vector();
        //如果节点存在，将节点画到图形界面中
        if(nodes != null && nodes.size() > 0)
        {
            System.out.println("nodeVector = " + nodes.size());
            for(int i = 0;i < nodes.size();i++)
            {
                RouteItem nodeitem = (RouteItem)nodes.elementAt(i);
                RouteNodeInfo node = (RouteNodeInfo)nodeitem.getObject();
                //在这里设置一个bsoID值，画关联时用
                node.setBsoID("" + i);
                nodeitem.setState(RouteItem.UPDATE);
                DefaultGraphNode newnode = new DefaultGraphNode();
                newnode.setRouteItem(nodeitem);
                newnode.setDepartmentName(node.getNodeDepartmentName());
                newnode.updateImage();
                model.graphNodeVector.addElement(newnode);
                gnv.addElement(newnode);
            }
        }
        // 如果节点关联存在，画节点关联
        if(links != null && links.size() > 0)
        {
            String leftNodeID, rightNodeID, curBsoID;
            System.out.println("linkVector = " + links.size());
            for(int j = 0;j < links.size();j++)
            {
                RouteItem linkitem = (RouteItem)links.elementAt(j);
                RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo)linkitem.getObject();
                linkitem.setState(RouteItem.UPDATE);
                DefaultGraphLink newlink = new DefaultGraphLink();
                newlink.setRouteItem(linkitem);
                RouteNodeIfc source = linkinfo.getSourceNode();
                leftNodeID = linkinfo.getSourceNode().getBsoID();
                rightNodeID = linkinfo.getDestinationNode().getBsoID();
                for(int k = 0;k < gnv.size();k++)
                {
                    DefaultGraphNode graphNode = (DefaultGraphNode)gnv.elementAt(k);
                    curBsoID = graphNode.getRouteItem().getObject().getBsoID();
                    if(leftNodeID.equals(curBsoID))
                    {
                        newlink.setInitPredecessor(graphNode);
                    }
                    if(rightNodeID.equals(curBsoID))
                    {
                        newlink.setInitSuccessor(graphNode);
                    }
                }
                model.graphLinkVector.addElement(newlink);
            }
        }
        try
        {
            graphPanel.setModel(model);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 新建路线图元 从工艺路线管理器获得选中零件的工艺路线信息， 从而显示在工艺路线编辑界面上。
     */
    /*private void createAlterGraph()
    {
        System.out.println("进入createAlterGraph。。。");
        HashMap newMap = RouteTaskJPanel.createNewMap;
        if(newMap != null)
        {
            System.out.println("存在createAlterGraph。。。");
            Vector nodeVector = (Vector)newMap.get("RouteNode");
            Vector linkVector = (Vector)newMap.get("RouteNodeLink");
            DefaultGraphModel model = new DefaultGraphModel();
            //缓存所有的节点图元
            Vector gnv = new Vector();
            if(nodeVector != null && nodeVector.size() > 0)
            {
                if(verbose)
                {
                    System.out.println("RouteGraphEditJDialog:获得节点的个数：" + nodeVector.size());
                }
                for(int i = 0;i < nodeVector.size();i++)
                {
                    RouteItem nodeitem = (RouteItem)nodeVector.elementAt(i);
                    RouteNodeInfo nodeinfo = (RouteNodeInfo)nodeitem.getObject();
                    DefaultGraphNode newnode = new DefaultGraphNode();
                    newnode.setRouteItem(nodeitem);
                    newnode.setDepartmentName(nodeinfo.getNodeDepartmentName());
                    newnode.setPosition(new Long(nodeinfo.getX()).intValue(), new Long(nodeinfo.getY()).intValue());
                    newnode.updateImage();
                    model.graphNodeVector.addElement(newnode);
                    gnv.addElement(newnode);
                }
            }
            if(linkVector != null && linkVector.size() > 0)
            {
                int leftNodeID, rightNodeID, curBsoID;
                for(int j = 0;j < linkVector.size();j++)
                {
                    RouteItem linkitem = (RouteItem)linkVector.elementAt(j);
                    RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo)linkitem.getObject();
                    DefaultGraphLink newlink = new DefaultGraphLink();
                    newlink.setRouteItem(linkitem);
                    leftNodeID = linkinfo.getSourceNode().hashCode();
                    rightNodeID = linkinfo.getDestinationNode().hashCode();
                    for(int k = 0;k < gnv.size();k++)
                    {
                        DefaultGraphNode graphNode = (DefaultGraphNode)gnv.elementAt(k);
                        curBsoID = graphNode.getRouteItem().getObject().hashCode();//可以用hashCode进行比较吗
                        if(leftNodeID == curBsoID)
                        {
                            newlink.setInitPredecessor(graphNode);
                        }
                        if(rightNodeID == curBsoID)
                        {
                            newlink.setInitSuccessor(graphNode);
                        }
                    }
                    model.graphLinkVector.addElement(newlink);
                }
            }
            try
            {
                graphPanel.setModel(model);
                //把从路线管理器选种的零部件的工艺路线传给路线图编辑界面
                TechnicsRouteInfo route = (TechnicsRouteInfo)((RouteItem)newMap.get("TechnicsRoute")).getObject();
                System.out.println("route=====" + route);
                System.out.println("离开createAlterGraph。。。");
                graphPanel.setTechnicsRoute(route);
                myRoute = route;
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }*/

    /**
     * 获得父窗口
     * @return Frame 父窗口
     */
    public Frame getParentFrame()
    {
        return parentFrame;
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
     * 定义动作监听器内部类
     */
    class ToolsActionListener implements java.awt.event.ActionListener
    {
        /**
         * 内部类的构造器
         * @roseuid 3DD1AB9A0026
         */
        public ToolsActionListener()
        {}

        /**
         * 动作事件的处理方法
         * @param actionevent 动作事件
         * @roseuid 3DD1AB9A0030
         */
        public void actionPerformed(ActionEvent actionevent)
        {

            String s = actionevent.getActionCommand();
            if(s.equals(POINTER)) //进入编辑状态
            {
                graphPanel.setMode(0);
                return;
            }else if(s.equals(LINK)) //插入新连线
            {
                graphPanel.setMode(2);
                return;
            }else if(s.equals(HOME)) //插入新节点
            {
                graphPanel.setMode(1);
            }else if(s.equals(UPDATE)) //编辑路线节点
            {
                GraphNodeComponent node = graphPanel.getSelectedNodeComponent();
                if(node != null)
                {
                    graphPanel.updateSelectedNode(node);
                    theStickyToolbar.setSelected(POINTER);
                }
            }else if(s.equals(DELETE)) //删除节点或连线
            {
                processDeleteAction();
                theStickyToolbar.setSelected(POINTER);
            }
        }
    }

    /**
     * 删除所选择的所有节点和连接
     */
    public void processDeleteAction()
    {
        try
        {
            //如果abstractgraphview是不可编辑的,则直接返回
            if(!graphPanel.isEnabled())
                return;

            DefaultGraphModel definergraphmodel = (DefaultGraphModel)graphPanel.getModel();
            //存储在路线图编辑面板选中的内容
            GraphSelectionModel graphselectionmodel = graphPanel.getSelectionModel();
            //删除所选择的节点
            for(Enumeration enumeration = graphselectionmodel.allSelectedNodes();enumeration.hasMoreElements();)
            {
                DefaultGraphNode definergraphnode = (DefaultGraphNode)enumeration.nextElement();
                definergraphmodel.removeNode(definergraphnode);
            }

            DefaultGraphLink definergraphlink;
            //删除所选择的连接
            for(Enumeration enumeration1 = graphselectionmodel.allSelectedLinks();enumeration1.hasMoreElements();)
            {
                definergraphlink = (DefaultGraphLink)enumeration1.nextElement();
                definergraphmodel.removeLink(definergraphlink);
            }
            //清除选择
            graphselectionmodel.clearSelection();
        }catch(Exception ex)
        {
            String message =ex.getMessage();
            DialogFactory.showInformDialog(this, message);
        }finally
        {
            graphPanel.repaint();
        }
    }

    /**
     * 生成路线
     * @param e java.awt.event.ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        try
        {
            processOKCommond();
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(this, message);
            isSave = false;
            return;
        }
        this.setVisible(false);
    }

    /**
     * 执行“确定”操作
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void processOKCommond() throws QMException
    {
        if(branchMap == null || branchMap.size() == 0)
        {
            routeNodeItemVector.clear();
            routeLinkItemVector.clear();
            routeNodeItemVector = graphPanel.getAllRouteNodeItems();
            routeLinkItemVector = graphPanel.getAllRouteNodeLinkItems();
            //if(verbose)
            {
                System.out.println("节点的个数:" + routeNodeItemVector.size());
                if(routeLinkItemVector != null)
                    System.out.println("连接的个数:" + routeLinkItemVector.size());
            }

            isSave = true;
            //生成新的路线串
            graphPanel.formRouteBranch();
            getRouteBranches();
        }else
        {
            int rowCount = this.qMMultiList.getRowCount();
            for(int i = 0;i < rowCount;i++)
            {
                Object[] obj = (Object[])branchMap.get(String.valueOf(i));
                ((TechnicsRouteBranchInfo)obj[0]).setMainRoute(((Boolean)qMMultiList.getSelectedObject(i, 4)).booleanValue());
                //branchMap.put(String.valueOf(i), obj);
            }
        }
        isSave = true;
        this.setIsExit(true);
    }

    /**
     * 如果执行了“取消”，则以现有旧数据提交保存。
     * @param e java.awt.event.ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this_windowClosing(null);
    }

    /**
     * 连接到Web帮助页"编辑路线图"
     * @param e java.awt.event.ActionEvent
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {
        showHelp();
    }

    /**
     * 设置主要路线
     * @param e java.awt.event.ActionEvent
     */
    void mainJButtonJButton_actionPerformed(ActionEvent e)
    {
        try
        {
            routeNodeItemVector.clear();
            routeLinkItemVector.clear();
            routeNodeItemVector = graphPanel.getAllRouteNodeItems();
            routeLinkItemVector = graphPanel.getAllRouteNodeLinkItems();
            //if(verbose)
            {
                System.out.println("节点的个数:" + routeNodeItemVector.size());
                if(routeLinkItemVector != null)
                    System.out.println("连接的个数:" + routeLinkItemVector.size());
            }

            isSave = true;
            //生成新的路线串
            graphPanel.formRouteBranch();
            branchToMultiList(getRouteBranches());
        }catch(Exception ex)
        {
            String message =ex.getMessage();
            DialogFactory.showInformDialog(this, message);
            return;
        }
    }

    /**
     * 获得路线分支,并显示在路线串列表中
     */
    private void branchToMultiList(Hashtable hashtable)
    {
        if(verbose)
        {
            System.out.println("获得路线分支cappclients.capproute.view.RouteTaskJPanel.branchToMultiList() begin...");

        }
        qMMultiList.clear();
        branchMap.clear();
        if(hashtable == null || hashtable.size() == 0)
        {
            return;
        }
        //获得路线分支,并显示在路线串列表中
        Object[] keys = hashtable.keySet().toArray();
        TechnicsRouteBranchInfo branchinfo;
        for(int i = 0;i < hashtable.size();i++)
        {
            branchinfo = (TechnicsRouteBranchInfo)keys[i];
            System.out.println("branchinfo=====" + branchinfo.getRouteStr());
            //列表属性数组："BsoID","序号","制造路线","装配路线","主要路线"
            qMMultiList.addTextCell(i, 0, String.valueOf(i));
            qMMultiList.addTextCell(i, 1, String.valueOf(i + 1));
            if(i == 0)
            {
                qMMultiList.addRadioButtonCell(i, 4, true);
            }else
            {
                qMMultiList.addRadioButtonCell(i, 4, false);
            }
            //qMMultiList.aadCell(i, 5, branchinfo);

            String makeStr = "";
            String assemStr = "";
            Vector nodeVector = (Vector)hashtable.get(branchinfo);
            if(verbose)
            {
                System.out.println("提交前 分支" + i + "的节点个数：" + nodeVector.size());
            }
            DefaultGraphNode graphnode;
            RouteNodeInfo node;
            for(int j = 0;j < nodeVector.size();j++)
            {
                graphnode = (DefaultGraphNode)nodeVector.elementAt(j);
                node = (RouteNodeInfo)graphnode.getRouteItem().getObject();
                if(node.getRouteType().equals(RouteCategoryType.MANUFACTUREROUTE.getDisplay()))
                {
                    if(makeStr == "")
                    {
                        makeStr = makeStr + graphnode.getDepartmentName();
                    }else
                    {
                        makeStr = makeStr + "-" + graphnode.getDepartmentName();
                    }
                }else if(node.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.getDisplay()))
                {
                    if(assemStr == "")
                    {
                        assemStr = assemStr + graphnode.getDepartmentName();
                    }else
                    {
                        assemStr = assemStr + "-" + graphnode.getDepartmentName();
                    }
                }
            }
            //20111226 xucy 
            //            if(makeStr.equals(""))
            //            {
            //                makeStr = "无";
            //            }
            //            if(assemStr.equals(""))
            //            {
            //                assemStr = "无";
            //            }
            qMMultiList.addTextCell(i, 2, makeStr);
            qMMultiList.addTextCell(i, 3, assemStr);
            //（问题一）zz start  改表 将路线串字符串存入branch对象，方便瘦客户查询.@前是制造路线，之后是装配路线
            branchinfo.setRouteStr(makeStr + "=" + assemStr);
            Object[] objs = new Object[2];
            objs[0] = branchinfo;
            objs[1] = nodeVector;
            branchMap.put(String.valueOf(i), objs);
        }
        //        for(int i = 0;i < hashtable.size();i++)
        //        {
        //        qMMultiList.addRadioButtonCell(i, 4, false);
        //        }
    }

    /** 缓存:key=分支BsoID; value=分支值对象 */
    private HashMap tempMap = new HashMap();

    /**
     * 获得生成的路线串。
     * @return 路线的集合
     * @throws QMException 
     * @throws QMRemoteException
     */
    private Hashtable getRouteBranches() throws QMException
    {
        Vector pathVector = graphPanel.pathVector;
        //if(verbose)
        System.out.println("生成路线分支的个数：" + pathVector.size());
        branchHashtable = new Hashtable();
        // System.out.println("pathVector pathVector size  " + pathVector.size() +"pathVector "+ pathVector);
        for(int i = 0;i < pathVector.size();i++)
        {
            Vector curPath = (Vector)pathVector.elementAt(i);
            for(int j = 0;j < curPath.size();j++)
            {
                DefaultGraphNode grapNode = (DefaultGraphNode)curPath.elementAt(j);
                RouteItem item = (RouteItem)grapNode.getRouteItem();
                RouteNodeInfo info = (RouteNodeInfo)item.getObject();
                System.out.println("路线分支节点====" + info.getX());
            }
            isCorrectAssemDepartment(curPath);
            TechnicsRouteBranchInfo branch = new TechnicsRouteBranchInfo();
            branch.setRouteInfo(myRoute);
            branchHashtable.put(branch, curPath);
            //if (verbose)
            System.out.println("路线分支getRouteBranches" + i + "的节点个数：" + curPath.size());
        }
        //if (verbose)
        System.out.println(branchHashtable.size());
        return branchHashtable;
    }

    /**
     * 检验指定的路线串中装配单位的位置是否正确。
     * @param v 路线串中所有节点的集合
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void isCorrectAssemDepartment(Vector v) throws QMException
    {
        DefaultGraphNode graphnode;
        RouteNodeInfo node;
        int num = 0;
        // System.out.println(" v 是谁 " + v);
        
        //CCBegin SS3
        if (comp.equals("cd")&&v.size()>1)
        {
        	DefaultGraphNode lastnode = (DefaultGraphNode) v.lastElement();
        	RouteNodeInfo lastNode = (RouteNodeInfo) lastnode.getRouteItem().getObject();
        	if (!lastNode.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.getDisplay()))
        	{
        		lastNode.setRouteType(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
        	}
        }
        //CCEnd SS3
        
        if(v.size() > 1)
        {
            RouteNodeInfo preNode = (RouteNodeInfo)((DefaultGraphNode)v.elementAt(v.size() - 2)).getRouteItem().getObject();
            RouteNodeInfo sucNode = (RouteNodeInfo)((DefaultGraphNode)v.elementAt(v.size() - 1)).getRouteItem().getObject();
            if(preNode.getNodeDepartment().equals(sucNode.getNodeDepartment()) && preNode.getRouteType().equals(sucNode.getRouteType()))
            {
                //同一加工单位不能在同一位置重复出现！
                throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.DEPARTMENT_IS_RECYCLE, null));
            }

        }

        //        RouteNodeInfo   preNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-2)).getRouteItem().getObject();
        //        RouteNodeInfo   sucNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-1)).getRouteItem().getObject();
        //        if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
        //                && preNode.getRouteType().equals(sucNode.getRouteType())) {
        //            //同一加工单位不能在同一位置重复出现！
        //            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
        //                    GraphRB.DEPARTMENT_IS_RECYCLE, null));
        //        }

        if(v != null && v.size() > 0)
        {
            for(int i = 0;i < v.size();i++)
            {
                graphnode = (DefaultGraphNode)v.elementAt(i);
                node = (RouteNodeInfo)graphnode.getRouteItem().getObject();
                //如果当前节点是装配单位节点
                if(node.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.getDisplay()))
                {
                    num = num + 1;
                    //如果该装配节点不是该路线串的最后节点
                    if(graphnode.behindNodeVec.size() > 0)
                    {
                        //装配单位必须是路线串中的最后一个单位
                        throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.ASSEM_DEPARTMENT_MUST_BE_LASTED, null));
                    }
                }
            }
        }
        if(num > 1)
        {
            //一个路线串中只能存在一个装配型单位
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.A_BRANCH_CAN_HAVE_ONLYONE_ASSEM, null));
        }
    }

    /**
     * 鼠标进入区域后执行
     * @param e MouseEvent 鼠标事件
     */
    void graphPanel_mouseEntered(MouseEvent e)
    {
        statusJLabel.setText("路线图绘制区域");
    }

    //    public static void main(String[] args) {
    //        try {
    //            System.setProperty("swing.useSystemFontSettings", "0");
    //            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //            String sid = "AwbAXtAd8jydq1F3sni0H11hXGJ27uO5YWeksXdN4P4MiwWc8hIA!335423841!1076894656457"
    //                    .trim();
    //            CappClientRequestServer server = new CappClientRequestServer(
    //                    "pdmlm", "80", sid);
    //            RequestServerFactory.setRequestServer(server);
    //            RouteGraphEditJDialog testFrame = new RouteGraphEditJDialog(null,
    //                    null, null, null,null,null);
    //            testFrame.setVisible(true);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //
    //    }

    /**
     * 设置工具按钮条选中的按钮
     */
    public void setPointerMode(int i)
    {
        if(theStickyToolbar != null)
        {
            if(i == 0)
                theStickyToolbar.setSelected(POINTER);
            else if(i == 1)
                theStickyToolbar.setSelected(HOME);
            else if(i == 2)
                theStickyToolbar.setSelected(LINK);
            if(i != 1)
            {
                this.departmentTree.setSelectionPath(null);
            }
        }
    }

    private boolean isExit = false;

    public boolean getIsExit()
    {
        return isExit;
    }

    public void setIsExit(boolean flag)
    {
        isExit = flag;
    }

    /**
     * 如果要非法退出，则提示“您确实要丢弃当前路线图中的所有路线信息吗？”
     * @param e java.awt.event.WindowEvent
     */
    void this_windowClosing(WindowEvent e)
    {
        String message = QMMessage.getLocalizedMessage(RESOURCE, GraphRB.CONFIRM_STOP_EDIT_GRAPH, null);
        String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        int result = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch(result)
        {
        case JOptionPane.YES_OPTION:
        {
            graphPanel = new GraphPanel(parentFrame, true, this);
            try
            {
                this.setGraphData(null);
                //processOKCommond();
                isExit = true;
                this.branchMap.clear();
                branchHashtable.clear();
                this.dispose();
            }catch(Exception ex)
            {
                String message1 = ex.getMessage();
                DialogFactory.showInformDialog(this, message1);
            }
        }
        case JOptionPane.NO_OPTION:
            return;
        }
    }

    void departmentTree_mouseEntered(MouseEvent e)
    {
        statusJLabel.setText("路线单位选择区域");
    }

    /**
     * 显示帮助页
     * @roseuid 3DFD6B9403BE
     */
    private void showHelp()
    {
        if(helpSystem == null)
        {
            initializeHelp();
        }else
        {
            helpSystem.showHelp("EditRouteGraphHelp");

        }
    }

    /**
     * 初始化帮助
     */
    private void initializeHelp()
    {
        try
        {
            //定义帮助文件所在服务器的主机及端口
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //构造帮助系统。
            /*
             * 参数 helptest 表帮助的子类别（用户界面主要操作内容） url 帮助文件所在服务器的主机及端口 OnlineHelp 浏览器窗口名 ResourceBundle 帮助资源
             */
            helpSystem = new QMHelpSystem("capproute", url, "OnlineHelp", ResourceBundle.getBundle("com.faw_qm.cappclients.conscapproute.util.RouteHelpRB", RemoteProperty.getVersionLocale()));
            //System.out.println(helpSystem.getResources());
            //定义帮助内容ID，（用户界面的工作模式，如“Update”、“Create”、“View”）
            String s = "EditRouteGraph";
            //构造帮助上下文环境
            helpContext = new QMHelpContext(this.getParentFrame(), helpSystem, s);
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    void this_keyPressed(KeyEvent e)
    {
        //如果点击了“Delete”键
        if(e.getKeyCode() == 127)
        {
            processDeleteAction();
            theStickyToolbar.setSelected(RouteGraphEditJDialog.POINTER);
        }
    }
}

/**
 * <p> Title: 为图元组件（节点或连接）服务的键盘监听 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

class GraphComponentKeyListener extends java.awt.event.KeyAdapter
{
    //编辑工艺路线图主界面
    private RouteGraphEditJDialog adaptee;

    //构造函数
    public GraphComponentKeyListener(RouteGraphEditJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //键盘事件
    public void keyPressed(KeyEvent e)
    {
        adaptee.this_keyPressed(e);
    }
}

/**
 * <p> Title: 为图元组件（节点或连接）服务的窗口监听 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
class RouteGraphEditJDialog_this_windowAdapter extends java.awt.event.WindowAdapter
{
    //编辑工艺路线图主界面
    private RouteGraphEditJDialog adaptee;

    //构造函数
    RouteGraphEditJDialog_this_windowAdapter(RouteGraphEditJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //窗口关闭事件
    public void windowClosing(WindowEvent e)
    {
        adaptee.this_windowClosing(e);
    }
}

/**
 * <p> Title: 为图元组件（节点或连接）服务的单位树监听 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
class RouteGraphEditJDialog_departmentTree_mouseAdapter extends java.awt.event.MouseAdapter
{
    //编辑工艺路线图主界面
    private RouteGraphEditJDialog adaptee;

    //构造函数
    RouteGraphEditJDialog_departmentTree_mouseAdapter(RouteGraphEditJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //鼠标进入
    public void mouseEntered(MouseEvent e)
    {
        adaptee.departmentTree_mouseEntered(e);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    //鼠标按下
    public void mousePressed(MouseEvent e)
    {
        adaptee.departmentTree_mousePressed(e);
    }
}
