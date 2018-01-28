/**
 * 生成程序 RouteGraphEditJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *CR1  郭晓亮  2009/12/23 原因：TD问题，域v4r3FunctionTest,TD号：2537
 *
 */
package com.faw_qm.cappclients.capproute.graph;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.cappclients.capproute.util.GraphRB;
import com.faw_qm.cappclients.capproute.view.RouteTaskJPanel;
import com.faw_qm.clients.util.QMPropertyVetoException;
import com.faw_qm.codemanage.client.view.CodeManageTree;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.QMHelpContext;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.RouteNodeLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.util.RouteCategoryType;

/**
 * <p>
 * Title:编辑工艺路线图主界面
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @version 1.0
 */
public class RouteGraphEditJDialog extends JDialog {
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
    public static final String RESOURCE = "com.faw_qm.cappclients.capproute.util.GraphRB";

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

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
    
	 //CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,是否最后一个单位为装配路线
	//最后一个单位是否为装配路线
	JCheckBox jCheckBox1 = new JCheckBox();
	 //CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,最后一个单位为装配路线

    /*
    public RouteGraphEditJDialog(Frame frame, TechnicsRouteListIfc list,
            TechnicsRouteIfc route, QMPartMasterIfc partMaster) {
        super(frame, "", true);

        this.parentFrame = frame;
        graphPanel = new GraphPanel(parentFrame, true, this);
        this.setRouteList(list);
        this.setTechnicsRoute(route);
        this.setPartMaster(partMaster);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
    /**
     * 构造函数
     *
     * @param frame
     *            父窗口
     * @param list
     *            要编辑的路线所属的路线表
     * @param route
     *            要编辑的路线
     * @param partMaster
     *            要编辑路线的零部件
     * @roseuid 402A22D6014E
     */
    public RouteGraphEditJDialog(JDialog prent,Frame frame, TechnicsRouteListIfc list,
            TechnicsRouteIfc route, QMPartMasterIfc partMaster) { 
        //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2537      	
       // super(frame, "", true);
        super((Dialog)prent, "", true);//CR1
    //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2537
        this.parentFrame = frame;
        graphPanel = new GraphPanel(parentFrame, true, this);
        this.setRouteList(list);
        this.setTechnicsRoute(route);
        this.setPartMaster(partMaster);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 界面初始化
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(gridBagLayout4);
        this.setSize(650, 500);
        this.addWindowListener(new RouteGraphEditJDialog_this_windowAdapter(
                this));
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
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });

        helpJButton.setMaximumSize(new Dimension(75, 23));
        helpJButton.setMinimumSize(new Dimension(75, 23));
        helpJButton.setPreferredSize(new Dimension(75, 23));
        helpJButton.setToolTipText("Help");
        helpJButton.setText("帮助");

        helpJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
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

        graphPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                graphPanel_mouseEntered(e);
            }
        });

        //departmentTree.addMouseListener(new
        // RouteGraphEditJDialog_departmentTree_mouseAdapter(this));
		 //CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,是否最后一个单位为装配路线
       // jCheckBox1.setSelected(true);
		jCheckBox1.setText("最后路线默认为装配接点");
		 //CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,是否最后一个单位为装配路线
        leftJPanel.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));
        leftJPanel.add(jScrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        //jScrollPane.getViewport().add(departmentTree, null);
        this.getContentPane().add(
                leftJPanel,
                new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(15, 10, 0, 0), 0, 0));

        theStickyToolbar.addActionListener(new ToolsActionListener());
        this.getContentPane().add(
                theStickyToolbar,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 30, 0, 10), 0, 0));
        this.getContentPane().add(
                buttonJPanel,
                new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(20, 0, 20, 10), 0, 0));
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
     //   buttonJPanel.add(helpJButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
             //   0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
             //   new Insets(0, 8, 0, 0), 0, 0));

        this.getContentPane().add(
                statusJLabel,
                new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                rightJPanel,
                new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(15, 0, 0, 10), 0, 0));
        rightJPanel.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));
        rightJPanel.add(graphViewPane, new GridBagConstraints(0, 1, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        graphViewPane.getViewport().add(graphPanel, null);

        ImageIcon image1 = new ImageIcon(getClass().getResource(
                "/images/route_pointer.gif"));
        ImageIcon image2 = new ImageIcon(getClass().getResource(
                "/images/route_link.gif"));
        ImageIcon image3 = new ImageIcon(getClass().getResource(
                "/images/route_makeNode.gif"));
        ImageIcon image4 = new ImageIcon(getClass().getResource(
                "/images/route_editNode.gif"));
        ImageIcon image5 = new ImageIcon(getClass().getResource(
                "/images/public_delete.gif"));
        theStickyToolbar.add(POINTER, image1, "");
        theStickyToolbar.add(HOME, image3, QMMessage.getLocalizedMessage(
                RESOURCE, "node", null));
        theStickyToolbar.add(LINK, image2, QMMessage.getLocalizedMessage(
                RESOURCE, "link", null));
        theStickyToolbar.addJButton(UPDATE, image4, QMMessage
                .getLocalizedMessage(RESOURCE, "editNode", null));
        theStickyToolbar.addJButton(DELETE, image5, QMMessage
                .getLocalizedMessage(RESOURCE, "deleteNodeOrLink", null));
        theStickyToolbar.setSelected(POINTER);
        this.initializeHelp();
        localize();
        //添加键盘监听
        this.addKeyListener(new GraphComponentKeyListener(this));
    }

    /**
     * 本地化
     */
    private void localize() {
        try {
            this.setTitle(QMMessage.getLocalizedMessage(RESOURCE,
                    "editGraphTitle", null)
                    + " < "
                    + this.getRouteList().getRouteListNumber()
                    + " : "
                    + this.getPartMaster().getPartNumber() + " >");
            jLabel1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "department", null));
            jLabel2.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "routeGraph", null));
            statusJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "editGraphTitle", null));
            okJButton.setText("确定(Y)");
            cancelJButton.setText("取消(C)");
            helpJButton.setText("帮助(H)");
            helpJButton.setMnemonic('H');
			 //CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,默认最后一个单位为装配路线
			this.getContentPane().add(
					jCheckBox1,
					new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE,
							new Insets(0, 0, 0, 0), 0, 0));
//			CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,默认最后一个单位为装配路线
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 向本界面添加路线单位树
     *
     * @param departmentTree com.faw_qm.codemanage.client.view.CodeManageTree
     */
    public void addDepartmentTree(CodeManageTree departmentTree) {
        if (departmentTree != null) {
            this.departmentTree = departmentTree;
            jScrollPane.getViewport().add(this.departmentTree, null);
            this.departmentTree
                    .addMouseListener(new RouteGraphEditJDialog_departmentTree_mouseAdapter(
                            this));
            this.departmentTree
                    .addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
                        public void valueChanged(TreeSelectionEvent e) {
                            departmentTree_valueChanged(e);
                        }
                    });
//			CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,默认使组织机构树展开		
		      //使组织机构树展开
		      DefaultMutableTreeNode node = (DefaultMutableTreeNode) departmentTree.
		          getModel().getRoot();
		      CodingClassificationIfc aa = (CodingClassificationIfc) node.getUserObject();
		      departmentTree.addDirectChild(aa, node);
//				CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,默认使组织机构树展开
        }
    }

    /**
     * 初始化路线单位树
     */
    /**
     * private void initTree() { try { //初始化路线单位树 CodingClassificationIfc cc =
     * null;
     * if(this.getRouteList().getRouteListLevel().equals(RouteListLevelType.FIRSTROUTE.getDisplay())) {
     * Class[] c = {String.class,String.class}; Object[] obj = {"组织机构","代码分类"};
     * cc = (CodingClassificationIfc)RParentJPanel.useServiceMethod(
     * "CodingManageService","findClassificationByName",c,obj); } else { cc =
     * (CodingClassificationIfc)RParentJPanel.refreshInfo(
     * this.getRouteList().getRouteListDepartment()); } if(cc!=null) {
     * departmentTree = new CodeManageTree(cc);
     * departmentTree.setShowsRootHandles(false);
     * departmentTree.addTreeSelectionListener(new
     * javax.swing.event.TreeSelectionListener() { public void
     * valueChanged(TreeSelectionEvent e) { departmentTree_valueChanged(e); }
     * }); } } catch (QMRemoteException ex) {
     * JOptionPane.showMessageDialog(this,ex.getClientMessage(),
     * QMMessage.getLocalizedMessage(RESOURCE,"information",null),
     * JOptionPane.INFORMATION_MESSAGE); } }
     */

    /**
     * 选择路线单位
     *
     * @param e
     *            TreeSelectionEvent javax.swing.event.TreeSelectionEvent
     */
    void departmentTree_valueChanged(TreeSelectionEvent e) {

    }

    /**
     * 当鼠标按下时，设置选中
     *
     * @param e 鼠标事件
     */
    void departmentTree_mousePressed(MouseEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) departmentTree
                .getLastSelectedPathComponent();
        if (node == null)
            return;
        Object object = node.getUserObject();
        if (object instanceof CodingIfc
                || (object instanceof CodingClassificationIfc)) {
            String departmentName = null;
            if (object instanceof CodingIfc) {
                CodingIfc bvi = (CodingIfc) object;
                departmentName = bvi.getShorten();
                graphPanel.setCurrentRouteDepartment(bvi.getBsoID(),
                        departmentName);
            } else if (object instanceof CodingClassificationIfc) {
                CodingClassificationIfc bvi = (CodingClassificationIfc) object;
                departmentName = bvi.getClassSort();
                graphPanel.setCurrentRouteDepartment(bvi.getBsoID(),
                        departmentName);
            }
            graphPanel.setMode(1);
        }
    }

    /**
     * 设置路线表
     *
     * @param list
     *            当前路线所属的路线表
     */
    private void setRouteList(TechnicsRouteListIfc list) {
        myRouteList = list;
    }

    /**
     * 获得当前路线表
     *
     * @return 当前路线所属的路线表
     */
    public TechnicsRouteListIfc getRouteList() {
        return myRouteList;
    }

    /**
     * 设置要编辑路线的零部件主信息
     *
     * @param partMaster
     *            零部件主信息
     */
    private void setPartMaster(QMPartMasterIfc partMaster) {
        myPart = partMaster;
    }

    /**
     * 获得当前编辑路线的零部件主信息
     *
     * @return 零部件主信息
     */
    public QMPartMasterIfc getPartMaster() {
        return myPart;
    }

    /**
     * 设置当前编辑的工艺路线对象
     *
     * @param route
     *            工艺路线对象
     */
    private void setTechnicsRoute(TechnicsRouteIfc route) {
        myRoute = route;
        graphPanel.setTechnicsRoute(route);
        try {
            setGraphData();
        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 获得当前编辑的工艺路线对象
     *
     * @return 当前编辑的工艺路线对象
     */
    public TechnicsRouteIfc getTechnicsRoute() {
        return myRoute;
    }

    /**
     * 获得并设置显示路线图的数据
     * @throws QMRemoteException
     */
    private void setGraphData() throws QMRemoteException {
        //这个判断是做什么用的呢？为什么RouteTaskJPanel.alterStatus == 0
        if (RouteTaskJPanel.getViewMode() == RouteTaskJPanel.UPDATE_MODE
                && RouteTaskJPanel.alterStatus == 0) {
            createAlterGraph();
            return;
        }

        if (myRoute.getBsoID() == null)
            return;
        //调用服务,根据路线对象获得路线节点和连接
        Class[] c = { String.class };
        Object[] obj = { myRoute.getBsoID() };
        Object[] map;
        if (verbose)
            System.out.println("RouteGraphEditJDialog: routeID = "
                    + myRoute.getBsoID());
        map = (Object[]) CappRouteAction.useServiceMethod(
                "TechnicsRouteService", "getRouteNodeAndNodeLink", c, obj);

        Vector nodeVector = (Vector) map[0];
        Vector linkVector = (Vector) map[1];

        DefaultGraphModel model = new DefaultGraphModel();
        //缓存所有的节点图元
        Vector gnv = new Vector();
        if (nodeVector != null && nodeVector.size() > 0) {
            for (int i = 0; i < nodeVector.size(); i++) {
                RouteNodeInfo nodeinfo = (RouteNodeInfo) nodeVector
                        .elementAt(i);
                RouteItem nodeitem = new RouteItem(nodeinfo);
                nodeitem.setState(RouteItem.UPDATE);
                DefaultGraphNode newnode = new DefaultGraphNode();
                newnode.setRouteItem(nodeitem);
                newnode.setDepartmentName(nodeinfo.getNodeDepartmentName());
                newnode.setPosition(new Long(nodeinfo.getX()).intValue(),
                        new Long(nodeinfo.getY()).intValue());
                newnode.updateImage();
                model.graphNodeVector.addElement(newnode);
                gnv.addElement(newnode);
            }
        }

        if (linkVector != null && linkVector.size() > 0) {
            String leftNodeID, rightNodeID, curBsoID;
            for (int j = 0; j < linkVector.size(); j++) {
                RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo) linkVector
                        .elementAt(j);
                RouteItem linkitem = new RouteItem(linkinfo);
                linkitem.setState(RouteItem.UPDATE);
                DefaultGraphLink newlink = new DefaultGraphLink();
                newlink.setRouteItem(linkitem);
                leftNodeID = linkinfo.getSourceNode().getBsoID();
                rightNodeID = linkinfo.getDestinationNode().getBsoID();
                for (int k = 0; k < gnv.size(); k++) {
                    DefaultGraphNode graphNode = (DefaultGraphNode) gnv
                            .elementAt(k);
                    curBsoID = graphNode.getRouteItem().getObject().getBsoID();
                    if (leftNodeID.equals(curBsoID)) {
                        newlink.setInitPredecessor(graphNode);
                    }
                    if (rightNodeID.equals(curBsoID)) {
                        newlink.setInitSuccessor(graphNode);
                    }
                }

                model.graphLinkVector.addElement(newlink);
            }
        }
        try {
            graphPanel.setModel(model);
        } catch (QMPropertyVetoException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 新建路线图元 从工艺路线管理器获得选中零件的工艺路线信息，
     * 从而显示在工艺路线编辑界面上。
     */
    private void createAlterGraph() {
        HashMap newMap = RouteTaskJPanel.createNewMap;
        if (newMap != null) {
            Vector nodeVector = (Vector) newMap.get("RouteNode");
            Vector linkVector = (Vector) newMap.get("RouteNodeLink");
            DefaultGraphModel model = new DefaultGraphModel();
            //缓存所有的节点图元
            Vector gnv = new Vector();
            if (nodeVector != null && nodeVector.size() > 0) {
                if (verbose) {
                    System.out.println("RouteGraphEditJDialog:获得节点的个数："
                            + nodeVector.size());
                }
                for (int i = 0; i < nodeVector.size(); i++) {
                    RouteItem nodeitem = (RouteItem) nodeVector.elementAt(i);
                    RouteNodeInfo nodeinfo = (RouteNodeInfo) nodeitem
                            .getObject();
                    DefaultGraphNode newnode = new DefaultGraphNode();
                    newnode.setRouteItem(nodeitem);
                    newnode.setDepartmentName(nodeinfo.getNodeDepartmentName());
                    newnode.setPosition(new Long(nodeinfo.getX()).intValue(),
                            new Long(nodeinfo.getY()).intValue());
                    newnode.updateImage();
                    model.graphNodeVector.addElement(newnode);
                    gnv.addElement(newnode);
                }
            }
            if (linkVector != null && linkVector.size() > 0) {
                int leftNodeID, rightNodeID, curBsoID;
                for (int j = 0; j < linkVector.size(); j++) {
                    RouteItem linkitem = (RouteItem) linkVector.elementAt(j);
                    RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo) linkitem
                            .getObject();
                    DefaultGraphLink newlink = new DefaultGraphLink();
                    newlink.setRouteItem(linkitem);
                    leftNodeID = linkinfo.getSourceNode().hashCode();
                    rightNodeID = linkinfo.getDestinationNode().hashCode();
                    for (int k = 0; k < gnv.size(); k++) {
                        DefaultGraphNode graphNode = (DefaultGraphNode) gnv
                                .elementAt(k);
                        curBsoID = graphNode.getRouteItem().getObject()
                                .hashCode();//可以用hashCode进行比较吗
                        if (leftNodeID == curBsoID) {
                            newlink.setInitPredecessor(graphNode);
                        }
                        if (rightNodeID == curBsoID) {
                            newlink.setInitSuccessor(graphNode);
                        }
                    }
                    model.graphLinkVector.addElement(newlink);
                }
            }
            try {
                graphPanel.setModel(model);
                //把从路线管理器选种的零部件的工艺路线传给路线图编辑界面
                TechnicsRouteInfo route = (TechnicsRouteInfo) ((RouteItem) newMap
                        .get("TechnicsRoute")).getObject();
                graphPanel.setTechnicsRoute(route);
                myRoute = route;
            } catch (QMPropertyVetoException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获得父窗口
     *
     * @return Frame 父窗口
     */
    public Frame getParentFrame() {
        return parentFrame;
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
     * 定义动作监听器内部类
     */
    class ToolsActionListener implements java.awt.event.ActionListener {
        /**
         * 内部类的构造器
         *
         * @roseuid 3DD1AB9A0026
         */
        public ToolsActionListener() {
        }

        /**
         * 动作事件的处理方法
         *
         * @param actionevent
         *            动作事件
         * @roseuid 3DD1AB9A0030
         */
        public void actionPerformed(ActionEvent actionevent) {

            String s = actionevent.getActionCommand();
            if (s.equals(POINTER)) //进入编辑状态
            {
                graphPanel.setMode(0);
                return;
            } else if (s.equals(LINK)) //插入新连线
            {
                graphPanel.setMode(2);
                return;
            } else if (s.equals(HOME)) //插入新节点
            {
                graphPanel.setMode(1);
            } else if (s.equals(UPDATE)) //编辑路线节点
            {
                GraphNodeComponent node = graphPanel.getSelectedNodeComponent();
                if (node != null) {
                    graphPanel.updateSelectedNode(node);
                    theStickyToolbar.setSelected(POINTER);
                }
            } else if (s.equals(DELETE)) //删除节点或连线
            {
                processDeleteAction();
                theStickyToolbar.setSelected(POINTER);
            }
        }
    }

    /**
     * 删除所选择的所有节点和连接
     */
    public void processDeleteAction() {
        try {
            //如果abstractgraphview是不可编辑的,则直接返回
            if (!graphPanel.isEnabled())
                return;

            DefaultGraphModel definergraphmodel = (DefaultGraphModel) graphPanel
                    .getModel();
            //存储在路线图编辑面板选中的内容
            GraphSelectionModel graphselectionmodel = graphPanel
                    .getSelectionModel();
            //删除所选择的节点
            for (Enumeration enumeration = graphselectionmodel
                    .allSelectedNodes(); enumeration.hasMoreElements();) {
                DefaultGraphNode definergraphnode = (DefaultGraphNode) enumeration
                        .nextElement();
                definergraphmodel.removeNode(definergraphnode);
            }

            DefaultGraphLink definergraphlink;
            //删除所选择的连接
            for (Enumeration enumeration1 = graphselectionmodel
                    .allSelectedLinks(); enumeration1.hasMoreElements();) {
                definergraphlink = (DefaultGraphLink) enumeration1
                        .nextElement();
                definergraphmodel.removeLink(definergraphlink);
            }
            //清除选择
            graphselectionmodel.clearSelection();
        } catch (QMRemoteException qmremoteexception) {
            JOptionPane.showMessageDialog(this, qmremoteexception
                    .getClientMessage(), QMMessage.getLocalizedMessage(
                    RESOURCE, "error", null), JOptionPane.ERROR_MESSAGE);
            qmremoteexception.printStackTrace();
        } finally {
            graphPanel.repaint();
        }
    }

    /**
     * 生成路线
     *
     * @param e java.awt.event.ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        try {
            processOKCommond();
        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    QMMessage.getLocalizedMessage(RESOURCE, "error", null),
                    JOptionPane.ERROR_MESSAGE);
            isSave = false;
            return;
        }
        this.setVisible(false);
    }

    /**
     * 执行“确定”操作
     *
     * @throws QMRemoteException
     */
    public void processOKCommond() throws QMRemoteException {
        routeNodeItemVector.clear();
        routeLinkItemVector.clear();
        routeNodeItemVector = graphPanel.getAllRouteNodeItems();
        routeLinkItemVector = graphPanel.getAllRouteNodeLinkItems();
        if (verbose) {
            System.out.println("节点的个数:" + routeNodeItemVector.size());
            if (routeLinkItemVector != null)
                System.out.println("连接的个数:" + routeLinkItemVector.size());
        }

        isSave = true;
        //生成新的路线串
        graphPanel.formRouteBranch();
        getRouteBranches();
    }

    /**
     * 如果执行了“取消”，则以现有旧数据提交保存。
     *
     * @param e
     *            java.awt.event.ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this_windowClosing(null);
    }

    /**
     * 连接到Web帮助页"编辑路线图"
     *
     * @param e java.awt.event.ActionEvent
     */
    void helpJButton_actionPerformed(ActionEvent e) {
        showHelp();
    }

    /**
     * 获得生成的路线串。
     *
     * @return 路线的集合
     * @throws QMRemoteException
     */
    private void getRouteBranches() throws QMRemoteException {
        Vector pathVector = graphPanel.pathVector;
        if (verbose)
            System.out.println("生成路线分支的个数：" + pathVector.size());
        branchHashtable = new Hashtable();
         // System.out.println("pathVector pathVector size  " + pathVector.size() +"pathVector "+ pathVector);
        for (int i = 0; i < pathVector.size(); i++) {
            Vector curPath = (Vector) pathVector.elementAt(i);

            isCorrectAssemDepartment(curPath);
            TechnicsRouteBranchInfo branch = new TechnicsRouteBranchInfo();
            branch.setRouteInfo(myRoute);
            branchHashtable.put(branch, curPath);
            if (verbose)
                System.out.println("路线分支" + i + "的节点个数：" + curPath.size());
        }
        if (verbose)
            System.out.println(branchHashtable.size());
    }

    /**
     * 检验指定的路线串中装配单位的位置是否正确。
     *
     * @param v
     *            路线串中所有节点的集合
     * @throws QMRemoteException
     */
    private void isCorrectAssemDepartment(Vector v) throws QMRemoteException {
        DefaultGraphNode graphnode;
        RouteNodeInfo node;
        int num = 0;
       // System.out.println(" v 是谁 " + v);
        
//		CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,默认最后一个单位为装配路线
	    //如果选则默认节点为装配节点
	    if (jCheckBox1.isSelected()) {
	      DefaultGraphNode lastnode = (DefaultGraphNode) v.lastElement();
	      RouteNodeInfo lastNode = (RouteNodeInfo) lastnode.getRouteItem().
	          getObject();
	      if (!lastNode.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.
	                                          getDisplay())) {
	        lastNode.setRouteType(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
	      }
	    }
//	  CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,默认最后一个单位为装配路线
        
//		CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,去掉同一加工单位不能在同一位置重复出现判断        
    if(v.size()>1){
          RouteNodeInfo   preNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-2)).getRouteItem().getObject();
           RouteNodeInfo   sucNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-1)).getRouteItem().getObject();
           if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
                   && preNode.getRouteType().equals(sucNode.getRouteType())) {
               //同一加工单位不能在同一位置重复出现！
               throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                       GraphRB.DEPARTMENT_IS_RECYCLE, null));
           }

        }
//	CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线,默认最后一个单位为装配路线
//        RouteNodeInfo   preNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-2)).getRouteItem().getObject();
//        RouteNodeInfo   sucNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-1)).getRouteItem().getObject();
//        if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
//                && preNode.getRouteType().equals(sucNode.getRouteType())) {
//            //同一加工单位不能在同一位置重复出现！
//            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
//                    GraphRB.DEPARTMENT_IS_RECYCLE, null));
//        }

        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                graphnode = (DefaultGraphNode) v.elementAt(i);
                node = (RouteNodeInfo) graphnode.getRouteItem().getObject();
                //如果当前节点是装配单位节点
                if (node.getRouteType().equals(
                        RouteCategoryType.ASSEMBLYROUTE.getDisplay())) {
                    num = num + 1;
                    //如果该装配节点不是该路线串的最后节点
                    if (graphnode.behindNodeVec.size() > 0) {
                        //装配单位必须是路线串中的最后一个单位
                        throw new QMRemoteException(
                                QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                GraphRB.ASSEM_DEPARTMENT_MUST_BE_LASTED,
                                                null));
                    }
                }
            }
        }
        if (num > 1) {
            //一个路线串中只能存在一个装配型单位
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.A_BRANCH_CAN_HAVE_ONLYONE_ASSEM, null));
        }

    }
    /**
     * 鼠标进入区域后执行
     * @param e MouseEvent 鼠标事件
     */
    void graphPanel_mouseEntered(MouseEvent e) {
        statusJLabel.setText("路线图绘制区域");
    }

    public static void main(String[] args) {
        try {
            System.setProperty("swing.useSystemFontSettings", "0");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            String sid = "AwbAXtAd8jydq1F3sni0H11hXGJ27uO5YWeksXdN4P4MiwWc8hIA!335423841!1076894656457"
                    .trim();
            CappClientRequestServer server = new CappClientRequestServer(
                    "pdmlm", "80", sid);
            RequestServerFactory.setRequestServer(server);
            RouteGraphEditJDialog testFrame = new RouteGraphEditJDialog(null,
                    null, null, null,null);
            testFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置工具按钮条选中的按钮
     */
    public void setPointerMode(int i) {
        if (theStickyToolbar != null) {
            if (i == 0)
                theStickyToolbar.setSelected(POINTER);
            else if (i == 1)
                theStickyToolbar.setSelected(HOME);
            else if (i == 2)
                theStickyToolbar.setSelected(LINK);
            if (i != 1) {
                this.departmentTree.setSelectionPath(null);
            }
        }
    }

    public boolean isExit = false;

    /**
     * 如果要非法退出，则提示“您确实要丢弃当前路线图中的所有路线信息吗？”
     *
     * @param e java.awt.event.WindowEvent
     */
    void this_windowClosing(WindowEvent e) {
        String message = QMMessage.getLocalizedMessage(RESOURCE,
                GraphRB.CONFIRM_STOP_EDIT_GRAPH, null);
        String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        int result = JOptionPane.showConfirmDialog(this, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (result) {
        case JOptionPane.YES_OPTION: {
            graphPanel = new GraphPanel(parentFrame, true, this);
            try {
                this.setGraphData();
                processOKCommond();
                isExit = true;
                this.dispose();
            } catch (QMRemoteException ex) {
                JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                        QMMessage.getLocalizedMessage(RESOURCE, "error", null),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        case JOptionPane.NO_OPTION:
            return;
        }
    }

    void departmentTree_mouseEntered(MouseEvent e) {
        statusJLabel.setText("路线单位选择区域");
    }

    /**
     * 显示帮助页
     *
     * @roseuid 3DFD6B9403BE
     */
    private void showHelp() {
        if (helpSystem == null) {
            initializeHelp();
        } else {
            helpSystem.showHelp("EditRouteGraphHelp");

        }
    }

    /**
     * 初始化帮助
     */
    private void initializeHelp() {
        try {
            //定义帮助文件所在服务器的主机及端口
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //构造帮助系统。
            /*
             * 参数 helptest 表帮助的子类别（用户界面主要操作内容） url 帮助文件所在服务器的主机及端口 OnlineHelp
             * 浏览器窗口名 ResourceBundle 帮助资源
             */
            helpSystem = new QMHelpSystem(
                    "capproute",
                    url,
                    "OnlineHelp",
                    ResourceBundle
                            .getBundle(
                                    "com.faw_qm.cappclients.capproute.util.RouteHelpRB",
                                    RemoteProperty.getVersionLocale()));
            //System.out.println(helpSystem.getResources());
            //定义帮助内容ID，（用户界面的工作模式，如“Update”、“Create”、“View”）
            String s = "EditRouteGraph";
            //构造帮助上下文环境
            helpContext = new QMHelpContext(this.getParentFrame(), helpSystem,
                    s);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void this_keyPressed(KeyEvent e) {
        //如果点击了“Delete”键
        if (e.getKeyCode() == 127) {
            processDeleteAction();
            theStickyToolbar.setSelected(RouteGraphEditJDialog.POINTER);
        }
    }
}

/**
 * <p>
 * Title: 为图元组件（节点或连接）服务的键盘监听
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @version 1.0
 */

class GraphComponentKeyListener extends java.awt.event.KeyAdapter {
    //编辑工艺路线图主界面
    private RouteGraphEditJDialog adaptee;
    //构造函数
    public GraphComponentKeyListener(RouteGraphEditJDialog adaptee) {
        this.adaptee = adaptee;
    }
    //键盘事件
    public void keyPressed(KeyEvent e) {
        adaptee.this_keyPressed(e);
    }
}
/**
 * <p>
 * Title: 为图元组件（节点或连接）服务的窗口监听
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @version 1.0
 */
class RouteGraphEditJDialog_this_windowAdapter extends
        java.awt.event.WindowAdapter {
    //编辑工艺路线图主界面
    private RouteGraphEditJDialog adaptee;
    //构造函数
    RouteGraphEditJDialog_this_windowAdapter(RouteGraphEditJDialog adaptee) {
        this.adaptee = adaptee;
    }
    //窗口关闭事件
    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }
}
/**
 * <p>
 * Title: 为图元组件（节点或连接）服务的单位树监听
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @version 1.0
 */
class RouteGraphEditJDialog_departmentTree_mouseAdapter extends
        java.awt.event.MouseAdapter {
    //编辑工艺路线图主界面
    private RouteGraphEditJDialog adaptee;
    //构造函数
    RouteGraphEditJDialog_departmentTree_mouseAdapter(
            RouteGraphEditJDialog adaptee) {
        this.adaptee = adaptee;
    }
    //鼠标进入
    public void mouseEntered(MouseEvent e) {
        adaptee.departmentTree_mouseEntered(e);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    //鼠标按下
    public void mousePressed(MouseEvent e) {
        adaptee.departmentTree_mousePressed(e);
    }
}
