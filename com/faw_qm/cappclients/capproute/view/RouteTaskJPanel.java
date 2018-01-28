/**
 * 生成程序 RouteTaskJPanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1  郭晓亮  200/5/25  原因:组织单位不能与代码管理的增，删，改同步。
 *                        方案:在点"路线图"按钮时重新构造组织单位树。
 * 
 * CR2  郭晓亮  2009/12/23 原因：TD问题，域v4r3FunctionTest,TD号：2537
 * SS1 工艺路线的更改标记增加批处理功能 zhangmingyuan 2011-11-01
 * SS2 主要路线复选框只能选中一个 liuyang 2014-7-15
 * SS3 如果是回导的创建的路线，更新时没能自动获取到源版本。 liunan 2014-12-15
 * SS4 将“采用路线表的有效性说明”复选框名称更改为“多重路线合并”，同时不按艺准通知书类型判断是否可编辑。 郭晓亮  20151015
 * SS5 A004-2015-3224 非卡车厂路线（川、岛）如果是主要路线，要给出提示。 liunan 2015-12-3
 * SS6 回导路线，如果没有源版本，就取本身版本。 liunan 2016-3-22
 * SS7 A004-2016-3433 新增路线提醒功能。并且将SS5内容移植过来。 liunan 2016-10-27
 * SS8 A004-2016-3450 路线编辑时主要路线默认卡车厂路线 liunan 2016-12-20
 */
package com.faw_qm.cappclients.capproute.view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.cappclients.capproute.graph.*;
import com.faw_qm.cappclients.capproute.util.*;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.codemanage.client.view.*;
import com.faw_qm.codemanage.model.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.clients.beans.explorer.ProgressDialog;

//CCBegin SS1
import com.faw_qm.framework.service.BaseValueIfc;
//CCEnd SS1

/**
 * Title:维护路线
 * Description:
 * Copyright: Copyright (c) 2004
 * Company: 一汽启明
 * @author 刘明
 * @version 1.0
* （问题一）zz start  改表 将路线串字符串存入branch对象，方便瘦客户查询.@前是制造路线，之后是装配路线
* （问题二）zz 20060925 换乘新版进度条
 */
public class RouteTaskJPanel extends RParentJPanel {
    private JLabel jLabel1 = new JLabel();

    private JLabel listNumberJLabel = new JLabel();

    private JLabel jLabel3 = new JLabel();

    private JLabel partNumberJLabel = new JLabel();

    private JLabel jLabel5 = new JLabel();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private JTextArea descriJTextArea = new JTextArea();

    private JButton editJButton = new JButton();

    private JSplitPane splitPane = new JSplitPane();

    private JPanel jPanel1 = new JPanel();

    private JPanel jpanel3 = new JPanel();

    private ComponentMultiList qMMultiList = new ComponentMultiList();

    private JLabel jLabel6 = new JLabel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel2 = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    /** 当前路线表的零部件关联 */
    private ListRoutePartLinkIfc myPartLink;

    /** 当前路线所属的路线表 */
    private TechnicsRouteListIfc myRouteList;

    /** 业务对象:当前工艺路线 */
    private TechnicsRouteIfc myRoute;

    /** 界面显示模式（更新模式）标记 */
    public final static int UPDATE_MODE = 0;

    /** 界面显示模式（创建模式）标记 */
    public final static int CREATE_MODE = 1;

    /** 界面显示模式（查看模式）标记 */
    public final static int VIEW_MODE = 2;

    /** 界面模式--查看 */
    public static int mode = VIEW_MODE;

    /** 当前编辑路线的零部件 */
    private QMPartMasterIfc myPart;

    //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
    private QMPartIfc myPartInfo;
    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 缓存:路线分支 key=路线分支的BsoID, value=路线分支值对象 */
    private HashMap branchMap = new HashMap();

    /** 编辑路线图界面对象 */
    private RouteGraphEditJDialog editDialog;

    /** 查看路线图界面对象 */
    private RouteGraphViewJDialog viewDialog;

    /** 标记是否执行了保存操作 */
    protected boolean isSave = false;

    /**
     * 缓存:界面初始化时从零部件关联获得的路线分支信息 key=工艺路线分枝值对象,
     * value=路线节点数组，obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
     */
    private Map originalBranchesMap;

    /** 标记：当该状态值为0时，该路线需复制新建；为1时，则为更新路线 */
    public static int alterStatus = 0;

    /** 缓存:路线分支 key=路线分支值对象, value= 集合（该分支中的所有路线节点） */
    private Hashtable branchHashtable = new Hashtable();

    /** 缓存:路线分支与节点的关联的集合(元素为RouteBranchNodeLinkInfo) */
    public Vector branchNodeLinkVector = new Vector();

    /** 父窗口 */
    private JFrame parentJFrame;

    /** 父对话框 */
    private JDialog parentDialog;

    /** 路线单位树（代码管理的部分） */
    private CodeManageTree departmentTree;
    private final static int OK = 1;
     //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
     JLabel changeLabel = new JLabel();
     JComboBox changeBombox = new JComboBox();
     JCheckBox isAdoptRLCheckBox = new JCheckBox();
     JLabel validLabel = new JLabel();
     JScrollPane jScrollPane2 = new JScrollPane();
     JTextArea validArea = new JTextArea();
     JLabel changLabel = new JLabel();
     JLabel jLabel2 = new JLabel();
     private Hashtable changeHash;
     //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线 
     
     //CCBegin SS1     
     private JButton applyAllButton = new JButton();
     //CCEnd SS1

    /**
     * 构造函数
     *
     * @param frame
     *            父窗口
     * @param dialog
     *            父对话框
     */
    public RouteTaskJPanel(JFrame frame, JDialog dialog) {
        try {
            parentJFrame = frame;
            parentDialog = dialog;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see com.faw_qm.cappclients.capproute.view.RParentJPanel#getParentJFrame()
     */
    public JFrame getParentJFrame() {
        return parentJFrame;
    }

    /**
     * 界面初始化
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        //CCBegin by leixiao 2010-1-27 将路线表改为艺准/艺毕
        jLabel1.setText("艺准/艺毕编号");
      //CCEnd by leixiao 2010-1-27 将路线表改为艺准/艺毕
        this.setLayout(gridBagLayout3);
        listNumberJLabel.setText("number1");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("零部件编号");
        partNumberJLabel.setText("number2");
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText("说明");
        editJButton.setMaximumSize(new Dimension(100, 23));
        editJButton.setMinimumSize(new Dimension(100, 23));
        editJButton.setPreferredSize(new Dimension(100, 23));
        editJButton.setToolTipText("进入路线图");
        editJButton.setSelected(true);
        editJButton.setText("路线图...");
        editJButton
                .addMouseListener(new RouteTaskJPanel_editJButton_mouseAdapter(
                        this));
        editJButton.setFocusPainted(false);
        ImageIcon image3 = new ImageIcon(getClass().getResource(
                "/images/route_editGraph.gif"));
        editJButton.setIcon(image3);
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editJButton_actionPerformed(e);
            }
        });
        jPanel1.setLayout(gridBagLayout1);
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setText("路线串");
        jPanel2.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
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
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBorder(null);
        splitPane.setOneTouchExpandable(true);
        jpanel3.setLayout(gridBagLayout4);
        splitPane.add(jPanel1, JSplitPane.TOP);
        splitPane.add(jpanel3, JSplitPane.BOTTOM);
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        changeLabel.setMaximumSize(new Dimension(55, 16));
        changeLabel.setMinimumSize(new Dimension(55, 16));
        changeLabel.setPreferredSize(new Dimension(55, 16));
        changeLabel.setText("更改标记");
        //CCBegin SS4
        isAdoptRLCheckBox.setText("多重路线合并");
        //CCEnd SS4
        isAdoptRLCheckBox.addItemListener(new RouteTaskJPanel_isAdoptRLCheckBox_itemAdapter(this));
        validLabel.setText("有效性说明");
        validArea.setText("");
        changLabel.setText("更改标记");
        
        //CCBegin SS1
        applyAllButton.setText("应用全部"); 
        applyAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
					try {
						applyAllButton_actionPerformed(e);
					} catch (QMRemoteException e1) {
						e1.printStackTrace();
					}}
            });        	       
        //CCEnd SS1

        changeBombox.addItemListener(new RouteTaskJPanel_changeBombox_itemAdapter(this));
        jLabel2.setText("");
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        jpanel3.add(jLabel6, new GridBagConstraints(0, 1, 1, 2, 0.0, 0.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(0, 33, 0, 0), 0, 0));
        jpanel3.add(qMMultiList, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 8, 0, 10), 0, 0));
        jpanel3.add(editJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
                        10, 8, 10, 0), 0, 0));
        splitPane.setLastDividerLocation(270);
        splitPane.setDividerLocation(270);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(1.0);

       /* jPanel1.add(jLabel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5,
                        0, 5, 0), 0, 0));
        jPanel1.add(partNumberJLabel, new GridBagConstraints(1, 1, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 8, 5, 10), 0, 0));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        10, 5, 0), 0, 0));
        jPanel1.add(jScrollPane1, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        7, 8, 10, 10), 0, 0));
        jPanel1.add(listNumberJLabel, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 5, 10), 0, 0));
        jPanel1.add(jLabel5, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(5, 0, 0, 0), 0, 0));
        this.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
                        10, 0, 10, 10), 0, 0));
        jPanel2.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 0, 0, 0), 0, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(descriJTextArea, null);
        this.add(splitPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        10, 0, 0, 0), 0, 0));*/
//      CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        jPanel1.add(jLabel3,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 9, 0, 0), 0, 0));
        jPanel1.add(partNumberJLabel,   new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 8, 0, 10), 273, 0));
        jPanel1.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 9, 0, 0), 0, 0));
        jPanel1.add(jScrollPane1,   new GridBagConstraints(1, 5, 2, 1, 1.0, 1.0
                ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 8, 10, 10), 311, 35));
        jPanel1.add(listNumberJLabel,   new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 10), 273, 0));
        this.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 0, 10, 10), 0, 0));
        jPanel2.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                      , GridBagConstraints.CENTER,
                                                      GridBagConstraints.NONE,
                                                      new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            , GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 8, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(descriJTextArea, null);
        this.add(splitPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                   , GridBagConstraints.CENTER,
                                                   GridBagConstraints.BOTH,
                                                   new Insets(10, 0, 0, 0), 0, 0));
        jPanel1.add(changeBombox,    new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 8, 0, 0), 68, 0));
        
        //CCBegin SS1
        jPanel1.add(applyAllButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, 
        		GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        //CCEnd SS1

        jPanel1.add(isAdoptRLCheckBox,    new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 8, 0, 154), 16, 0));
        jPanel1.add(validLabel,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(8, 9, 28, 8), 0, 0));
        jPanel1.add(jScrollPane2,   new GridBagConstraints(1, 4, 2, 1, 1.0, 1.0
                ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 7, 0, 10), 312, 29));
        jPanel1.add(jLabel5,   new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(8, 0, 0, 8), 0, 0));
        jPanel1.add(changLabel,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
        jPanel1.add(jLabel2,  new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        jScrollPane2.getViewport().add(validArea, null);
//      CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

        qMMultiList.setHeadings(new String[] { "id", "序号", "制造路线", "装配路线",
                "主要路线" });
        qMMultiList.setRelColWidth(new int[] { 0, 1, 4, 2, 2 });
        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 4 }, true);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setCheckboxEditable(true);
//      CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线   
        initChangeJComboBox();
//      CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
    }
    
    //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
    /**
     * 初始化工艺路线表状态复选框的值
     * st skybird 2005.2.23
     */
    private void initChangeJComboBox()
    {
        Class[] params={String.class,String.class};
        Object[] values={"更改标记",
            "工艺路线"};
        Collection result = null;
        try
        {
            result = (Collection)RouteListTaskJPanel.invokeRemoteMethodWithException(this,
                "CodingManageService","getCoding",params,values);
        }
        catch(QMRemoteException ex)
        {
            //输出异常信息：
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "ERROR", null),
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(result != null && result.size() > 0)
        {
        	changeHash = new Hashtable();
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
                CodingIfc info1 = (CodingIfc) iterator.next();

            	changeHash.put(info1.getCodeContent(),
                        info1);
                this.changeBombox.addItem(info1.getCodeContent());
            }
            changeBombox.setSelectedIndex(0);
        }
    }
    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
    

    /**
     * 设置当前路线所属的路线表
     *
     * @param routelist
     *            路线表对象
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
        myRouteList = routelist;
        initTree();//zz 周茁修改 （注释掉，移到构造函数。）红塔性能问题
    }

    /**
     * 获得当前路线所属路线表
     *
     * @return 路线表
     */
    public TechnicsRouteListIfc getTechnicsRouteList() {
        return myRouteList;
    }

    /**
     * 设置当前编辑的零部件关联 零件，路线等显示在右边界面上
     *
     * @param link
     *            路线表的零部件关联对象
     * @throws QMRemoteException
     */
    public void setListPartLink(ListRoutePartLinkIfc link)
            throws QMRemoteException {
        alterStatus = link.getAlterStatus();
        //skybird？
        isChangeRoute = true;
        myPartLink = link;
        setPart(link.getPartMasterInfo());
//      CCBeginby leixiao 2009-2-21 原因：解放升级工艺路线,路线添加具体版本
        setPartInfo(link.getPartBranchInfo());
//      CCEndby leixiao 2009-2-21 原因：解放升级工艺路线,路线添加具体版本
        String routeID = link.getRouteID();
        if (routeID != null) {
            if (verbose) {
                System.out
                        .println("RouteTaskJPanel:setListPartLink() listRoutePartLink = "
                                + link.getBsoID());
                System.out
                        .println("RouteTaskJPanel:setListPartLink() routeID = "
                                + routeID);
            }
            Class[] c = { String.class };
            Object[] obj = { routeID };
            Object[] objs = (Object[]) useServiceMethod(
                    "TechnicsRouteService", "getRouteAndBrach", c, obj);
            if (objs != null && objs.length > 0) {
            	
            	//CCBegin SS4
            	if(myRoute!=null)
            	  ((TechnicsRouteIfc) objs[0]).setIsAdopt(myRoute.getIsAdopt());
            	//CCEn SS4
                setTechnicsRoute((TechnicsRouteIfc) objs[0]);
                setRouteBranches((Map) objs[1]);
            }
        }
    }

    /**
     * 获得当前编辑的零部件关联
     *
     * @return 路线表的零部件关联对象
     */
    public ListRoutePartLinkIfc getListPartLink() {
        return myPartLink;
    }

    /**
     * 设置当前业务对象
     *
     * @param route
     *            工艺路线对象
     */
    private void setTechnicsRoute(TechnicsRouteIfc route) {
        myRoute = route;
    }

    /**
     * 获得当前业务对象
     *
     * @return 当前工艺路线
     */
    public TechnicsRouteIfc getTechnicsRoute() {
        return myRoute;
    }

    /**
     * 设置当前要编辑路线的零部件
     *
     * @param part
     *            零部件
     */
    private void setPart(QMPartMasterIfc part) {
        myPart = part;
    }

    /**
     * 获得当前要编辑路线的零部件
     *
     * @return 零部件
     */
    public QMPartMasterIfc getPart() {
        return myPart;
    }

//  CCBeginby leixiao 2009-2-21 原因：解放升级工艺路线,路线添加具体版本
    private void setPartInfo(QMPartIfc part) {
        myPartInfo = part;
    }

//  CCEndby leixiao 2009-2-21 原因：解放升级工艺路线,路线添加具体版本

    /**
     * 界面初始化时从零部件关联获得的路线分支信息
     *
     * @param map
     *            HashMap
     */
    private void setRouteBranches(Map map) {
        originalBranchesMap = map;
    }

    /**
     * 获得界面初始化时从零部件关联获得的路线分支信息
     *
     * @return HashMap
     */
    public Map getRouteBranches() {
        return originalBranchesMap;
    }

    /**
     * 设置界面模式（创建、更新或查看）。
     *
     * @param aMode
     *            新界面模式
     */
    public void setViewMode(int aMode) {
        if (!this.isShowing()) {
            this.setVisible(true);
        }

        if ((aMode == UPDATE_MODE) || (aMode == CREATE_MODE)
                || (aMode == VIEW_MODE)) {
            mode = aMode;
        }

        switch (aMode) {

        case CREATE_MODE: { //创建模式
            this.setCreateModel();
            break;
        }

        case UPDATE_MODE: { //更新模式
            this.setUpdateModel();
            break;
        }

        case VIEW_MODE: { //查看模式
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
    public static int getViewMode() {
        return mode;
    }
    
    /**
     *         //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
     * 根据给定零QMPartMasterIfc获得最新小版本QMPartIfc(暂未考虑权限)
     * @param partMasterIfc QMPartMasterIfc 零部件主对象.
     * @return QMPartIfc 最新小版本的零部件值对象.
     */
    protected String getibaPartVersion(QMPartMasterIfc partMasterIfc)
    {
        Class[] paraClass ={QMPartMasterIfc.class};
        Object[] objs ={partMasterIfc};
        String result = null;
        try
        {
//        	CCBegin by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
            result = (String) useServiceMethod(
                    "TechnicsRouteService", "getibaPartVersion", paraClass, objs);
           // System.out.println("-----------result="+result);
//          CCEnd by leixiao 2009-1-8原因：解放升级工艺路线          
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            return null;
        }

        return result;
    }
    
    protected String getibaPartVersion(QMPartIfc partIfc)
    {
        Class[] paraClass ={QMPartIfc.class};
        Object[] objs ={partIfc};
        String result = null;
        try
        {
//        	CCBegin by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
            result = (String) useServiceMethod(
                    "TechnicsRouteService", "getibaPartVersion", paraClass, objs);
           // System.out.println("-----------result="+result);
//          CCEnd by leixiao 2009-1-8原因：解放升级工艺路线          
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            return null;
        }

        return result;
    }
    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

    /**
     * 设置界面为新建模式
     */
    private void setCreateModel() {
        editDialog = null;
        this.isSave = false;
        listNumberJLabel.setText(myRouteList.getRouteListNumber());
        partNumberJLabel.setText(myPart.getPartNumber());
        descriJTextArea.setText("");
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        //modify for cq by liunan 2007.08.10
        //在说明中添加 大版本 信息，用于生成报表时在编号中合并显示。
        
      //  descriJTextArea.setText("("+(String)(getQMPartIfc(myPart).getVersionID()+")"));
//    	CCBegin by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
        if(myPartInfo!=null){
        	//System.out.println("---myPartInfo="+myPartInfo);
        	descriJTextArea.setText("("+getibaPartVersion(myPartInfo)+")");
        }
        else
        descriJTextArea.setText("("+getibaPartVersion(myPart)+")");
//    	CCEnd by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本      
        //end modifying
        this.validArea.setText("");
        if(this.myRouteList.getRouteListState().equals("临准"))
        {
        	//CCBegin SS4
//           this.isAdoptRLCheckBox.setSelected(true);
//           this.isAdoptRLCheckBox.setEnabled(true);
           //CCEnd SS4
           this.validArea.setText(this.myRouteList.getDefaultDescreption());
           this.validArea.setEditable(false);
        }
         else
         {
        	//CCBegin SS4
//             this.isAdoptRLCheckBox.setSelected(false);
//             this.isAdoptRLCheckBox.setEnabled(false);
           //CCEnd SS4
             this.validArea.setEditable(true);
         }

        this.changeBombox.setSelectedIndex(0); 
        
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        branchMap.clear();
        qMMultiList.clear();
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        descriJTextArea.setEditable(true);
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        this.changeBombox.setEnabled(true);
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

        //CCBegin SS1
        applyAllButton.setVisible(false);
        //CCEnd SS1

        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 4 }, true);
        qMMultiList.setCheckboxEditable(true);
        //CCBegin SS2
        qMMultiList.getTable().getModel().addTableModelListener(new TableModelListener()
        {
			public void tableChanged(TableModelEvent arg0)
			{
				CB_tableChanged(arg0);
			
			}
        });
        //CCEnd SS2
        //新建路线对象
        TechnicsRouteInfo newRoute = new TechnicsRouteInfo();
        this.setTechnicsRoute(newRoute);
        if (!this.isShowing()) {
            this.setVisible(true);
        }
        repaint();
    }

    /**
     * 设置界面为更新模式
     */
    private void setUpdateModel() {
        editDialog = null;
        this.isSave = false;
        listNumberJLabel.setText(myRouteList.getRouteListNumber());
        partNumberJLabel.setText(myPart.getPartNumber());
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        //descriJTextArea.setText(myRoute.getRouteDescription());
        //modify for cq by liunan 2007.08.10
        //在说明中添加 大版本 信息，用于生成报表时在编号中合并显示。
        String describ= myRoute.getRouteDescription();
       // String preString="("+(String)(getQMPartIfc(myPart).getVersionValue()).substring(0,1)+")";
       // String preString="("+(String)(getQMPartIfc(myPart).getVersionID())+")";
//    	CCBegin by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
        String preString="";
        if(myPartInfo!=null){
        	//System.out.println("---------myPartInfo="+myPartInfo);
        	preString ="("+getibaPartVersion(myPartInfo)+")";	
        }
        else
        preString ="("+getibaPartVersion(myPart)+")";
//    	CCEnd by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
        if(describ!=null){
            if(describ.startsWith("(")&&describ.indexOf(")")!=-1){
            	describ=preString+describ.substring(describ.indexOf(")")+1,describ.length()); //anan
            }
            //CCBegin SS3
            if(describ.equals("BOM回导路线"))
            {
            	//CCBegin SS6
            	if(preString.equals(""))
            	{
            		preString = "("+myPartInfo.getVersionID()+")";
            	}
            	//CCEnd SS6
            	describ = preString+describ;
            }
            //CCEnd SS3
        }
        else{
        	describ="";
        }
        //describ=preString+describ;  anan
       // System.out.println("-----describ="+describ);
        descriJTextArea.setText(describ);
        //end modifying
        this.validArea.setText(myRoute.getDefaultDescreption());
        this.isAdoptRLCheckBox.setSelected(myRoute.getIsAdopt());
        this.changeBombox.setSelectedItem(myRoute.getModefyIdenty().getCodeContent());
        if(this.myRouteList.getRouteListState().equals("临准"))
       {
        	//CCBegin SS4
//          this.isAdoptRLCheckBox.setEnabled(true);
//          if(this.isAdoptRLCheckBox.isSelected())
//          this.validArea.setEditable(false);
//          else
        	  //CCEnd SS4
          this.validArea.setEditable(true);  
       }
        else
        {
        	//CCBegin SS4
//            this.isAdoptRLCheckBox.setEnabled(false);
        	 //CCEnd SS4
            this.validArea.setEditable(true);
        }
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        branchToMultiList();
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        descriJTextArea.setEditable(true);
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        this.changeBombox.setEnabled(true);
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

      //CCBegin SS1
      applyAllButton.setVisible(true);
      //CCEnd SS1

        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 4 }, true);
        qMMultiList.setCheckboxEditable(true);
        //Begin SS2
        qMMultiList.getTable().getModel().addTableModelListener(new TableModelListener()
        {
			public void tableChanged(TableModelEvent arg0)
			{
				CB_tableChanged(arg0);
			
			}
        });
        //CCEnd SS2
        if (!this.isShowing()) {
            this.setVisible(true);
        }
        repaint();
        if (alterStatus == 0) {
            try {
                Class[] c = { String.class, Collection.class };
                Object[] objs = { myRoute.getBsoID(), null };
                createNewMap = (HashMap) CappRouteAction.useServiceMethod(
                        "TechnicsRouteService", "getRouteContainer", c, objs);
            } catch (QMRemoteException e) {
                JOptionPane.showMessageDialog(null, e.getClientMessage(),
                        QMMessage.getLocalizedMessage(RESOURCE, "exception",
                                null), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * 缓存去掉持久化信息后的新业务对象
     */
    public static HashMap createNewMap = null;

    /**
     * 设置界面为查看模式
     */
    private void setViewDisplayModel() {
        listNumberJLabel.setText(myRouteList.getRouteListNumber());
        partNumberJLabel.setText(myPart.getPartNumber());
        descriJTextArea.setText(myRoute.getRouteDescription());
        descriJTextArea.setEditable(false);
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        this.validArea.setText(myRoute.getDefaultDescreption());
        this.isAdoptRLCheckBox.setSelected(myRoute.getIsAdopt());
        this.changeBombox.setSelectedItem(myRoute.getModefyIdenty().getCodeContent());
        descriJTextArea.setEditable(false);
        validArea.setEditable(false);
        //CCBegin SS4
//        isAdoptRLCheckBox.setEnabled(false);
        isAdoptRLCheckBox.setEnabled(true);
        //CCEnd SS4
        changeBombox.setEnabled(false);
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 0, 1, 2, 3, 4 }, false);
        qMMultiList.setCheckboxEditable(false);
        branchToMultiList();
        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        //CCBegin SS1
        applyAllButton.setVisible(false);
        //CCEnd SS1
        if (!this.isShowing()) {
            this.setVisible(true);
        }
        repaint();
    }

    /**
     * 获得路线分支,并显示在路线串列表中
     */
    private void branchToMultiList() {
        if (verbose) {
            System.out
                    .println("获得路线分支cappclients.capproute.view.RouteTaskJPanel.branchToMultiList() begin...");

        }
        qMMultiList.clear();
        tempMap.clear();
        if (this.getRouteBranches() == null
                || this.getRouteBranches().size() == 0) {
            return;
        }

        //获得路线分支,给分支对象进行排序并显示在路线串列表中
        Object[] branchs = RouteHelper.sortedInfos(
                this.getRouteBranches().keySet()).toArray();
        if (verbose) {
            System.out
                    .println(">>>>>>>>>>>>>>>>>  获得 分支 的个数：" + branchs.length);
        }
        for (int i = 0; i < branchs.length; i++) {
            TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) branchs[i];
            if (verbose) {
               // System.out.println("分支：" + branchinfo.getBsoID());
                //列表属性数组："BsoID","序号","制造路线","装配路线","主要路线"
            }
            qMMultiList.addTextCell(i, 0, branchinfo.getBsoID());
            qMMultiList.addTextCell(i, 1, String.valueOf(i + 1));
            //在列表中第4列添加选择框
           qMMultiList.addCheckboxCell(i, 4, branchinfo.getMainRoute());         
            tempMap.put(branchinfo.getBsoID(), branchinfo);
            String makeStr = "";
            String assemStr = "";
            Object[] nodes = (Object[]) getRouteBranches().get(branchinfo);
            Vector makeNodes = (Vector) nodes[0];
            RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

            if (makeNodes != null && makeNodes.size() > 0) {
                if (verbose) {
                    System.out.println(">>>>>>>>>>>>>>>>>  获得 分支"
                            + branchinfo.getBsoID() + "的制造节点 个数："
                            + makeNodes.size());
                }
                for (int m = 0; m < makeNodes.size(); m++) {
                    RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
                    if (makeStr == "") {
                        makeStr = makeStr + node.getNodeDepartmentName();
                    } else {
                        makeStr = makeStr + "→" + node.getNodeDepartmentName();
                    }
                }
            }

            if (asseNode != null) {
                assemStr = asseNode.getNodeDepartmentName();
            }

            if (makeStr.equals("")) {
                makeStr = "无";
            }
            if (assemStr.equals("")) {
                assemStr = "无";
            }
            qMMultiList.addTextCell(i, 2, makeStr);
            qMMultiList.addTextCell(i, 3, assemStr);

        }

        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteTaskJPanel.branchToMultiList() end...return is void");
        }
    }
//CCBegin SS2
    void CB_tableChanged(TableModelEvent arg0){
    	int row = arg0.getFirstRow();//行
		int col = arg0.getColumn();//列	
		if(col==4){
		  if((Boolean)qMMultiList.getCellAt(row, 4).getValue()){
		
		    int count = qMMultiList.getTable().getRowCount();
		    for(int n=0;n<count;n++){
		    	if(n!=row){
		    		qMMultiList.setCheckboxSelected(n, 4, false);
		    	}
		    }	  
		 }
		}
    }
    //CCEnd SS2
    /** 缓存:key=分支BsoID; value=分支值对象 */
    private HashMap tempMap = new HashMap();

    /**
     * 在更新模式时,如果只更改分支的"是否主要路线"属性,而不打开路线图,则保存时,需调用 本方法提交路线分支.
     *
     * @return 所有路线分支的集合
     */
    private Vector getBranchesOnlyUpdate() {
        Vector v = new Vector();
        int size = qMMultiList.getNumberOfRows();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                TechnicsRouteBranchInfo branch = (TechnicsRouteBranchInfo) tempMap
                        .get(qMMultiList.getCellText(i, 0));
                if (qMMultiList.isCheckboxSelected(i, 4)) {
                    branch.setMainRoute(true);
                } else {
                    branch.setMainRoute(false);
                }
                v.add(branch);
            }
        }
        return v;
    }

    /**
     * 执行编辑路线图操作
     *
     * @param e
     *            ActionEvent
     */
    void editJButton_actionPerformed(ActionEvent e) {
        processEditRouteGraph();
    }

    /**
     * 标记：是否显示为另一路线的信息
     */
    private boolean isChangeRoute = true;

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    /**
     * 编辑或查看路线图
     */
    private void processEditRouteGraph() {
        if (getViewMode() == VIEW_MODE) {
            if (isChangeRoute || viewDialog == null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                viewDialog = new RouteGraphViewJDialog(this.getParentJFrame());
                viewDialog.setTechnicsRoute(this.getTechnicsRoute());
                isChangeRoute = false;
                setCursor(Cursor.getDefaultCursor());
            }
            viewDialog.setVisible(true);

        } else {
            if (isChangeRoute || editDialog == null || editDialog.isExit) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                this.initTree();//CR1 
                 //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2537               
                /*Begin CR2
                editDialog = new RouteGraphEditJDialog(this.getParentJFrame(),
                        this.getTechnicsRouteList(), this.getTechnicsRoute(),
                        this.getPart());
                */
                 editDialog = new RouteGraphEditJDialog((CappRouteManageJDialog) parentDialog,this.getParentJFrame(),
                        this.getTechnicsRouteList(), this.getTechnicsRoute(),
                        this.getPart());
                //End CR2
                 //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2537
                editDialog.addDepartmentTree(this.departmentTree);
                isChangeRoute = false;
                setCursor(Cursor.getDefaultCursor());
            }
            editDialog.setVisible(true);
            if (editDialog.isSave) {
                this.branchHashtable.clear();
                this.branchHashtable = editDialog.branchHashtable;
                //if(branchHashtable !=null && branchHashtable.size()>0)
                formBranchToMultiList(branchHashtable);
            }
        }
    }

    /**
     * 生成新的路线串并添加到列表中
     *
     * @param pathHashtable
     *            Hashtable
     */
    private void formBranchToMultiList(Hashtable pathHashtable) {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteTaskJPanel.formBranchToMultiList() begin...");
        }
        qMMultiList.clear();
        branchMap.clear();
        if (pathHashtable == null || pathHashtable.size() == 0) {
            return;
        }
        //获得路线分支,并显示在路线串列表中
        Object[] keys = pathHashtable.keySet().toArray();
        TechnicsRouteBranchInfo branchinfo;
        //CCBegin SS8
        boolean lastFlag = true;
        //CCEnd SS8

        for (int i = 0; i < pathHashtable.size(); i++) {
            branchinfo = (TechnicsRouteBranchInfo) keys[i];
            //列表属性数组："BsoID","序号","制造路线","装配路线","主要路线"
            qMMultiList.addTextCell(i, 0, String.valueOf(i));
            qMMultiList.addTextCell(i, 1, String.valueOf(i + 1));
            
            //CCBegin SS8
            //qMMultiList.addCheckboxCell(i, 4, true);
            //CCEnd SS8
            
            Object[] objs = new Object[2];
            objs[0] = branchinfo;
            objs[1] = pathHashtable.get(branchinfo);
            branchMap.put(String.valueOf(i), objs);
            String makeStr = "";
            String assemStr = "";
            Vector nodeVector = (Vector) pathHashtable.get(branchinfo);
            if (verbose) {
                System.out.println("提交前 分支" + i + "的节点个数：" + nodeVector.size());
            }
            DefaultGraphNode graphnode;
            RouteNodeInfo node;
            for (int j = 0; j < nodeVector.size(); j++) {
                graphnode = (DefaultGraphNode) nodeVector.elementAt(j);
                node = (RouteNodeInfo) graphnode.getRouteItem().getObject();
                if (node.getRouteType().equals(
                        RouteCategoryType.MANUFACTUREROUTE.getDisplay())) {
                    if (makeStr == "") {
                        makeStr = makeStr + graphnode.getDepartmentName();
                    } else {
                        makeStr = makeStr + "→" + graphnode.getDepartmentName();
                    }
                } else if (node.getRouteType().equals(
                        RouteCategoryType.ASSEMBLYROUTE.getDisplay())) {
                    if (assemStr == "") {
                        assemStr = assemStr + graphnode.getDepartmentName();
                    } else {
                        assemStr = assemStr + "→"
                                + graphnode.getDepartmentName();
                    }
                }
            }
            if (makeStr.equals("")) {
                makeStr = "无";
            }
            if (assemStr.equals("")) {
                assemStr = "无";
            }
            qMMultiList.addTextCell(i, 2, makeStr);
            qMMultiList.addTextCell(i, 3, assemStr);
            
            //CCBegin SS8
            //如果包含卡车厂路线，则为主要路线
            if(checkMainRoute(makeStr+assemStr))
            {
            	qMMultiList.addCheckboxCell(i, 4, true);
            	lastFlag = false;
            }
            //如果都不包含卡车厂路线，则最后一个为主要路线
            else if(lastFlag&&(pathHashtable.size()-1)==i)
            {
            	qMMultiList.addCheckboxCell(i, 4, true);
            }
            //其他的为非主要路线
            else
            {
            	qMMultiList.addCheckboxCell(i, 4, false);
            }
            //CCEnd SS8
            
            //（问题一）zz start  改表 将路线串字符串存入branch对象，方便瘦客户查询.@前是制造路线，之后是装配路线
         branchinfo.setRouteStr(makeStr + "@"+assemStr);
        }

        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteTaskJPanel.formBranchToMultiList() end...return is void");
        }
    }

    void okJButton_actionPerformed(ActionEvent e) {
           WorkThread work = new WorkThread(OK);
            work.start();

    }
    
    
    //CCBegin SS1
    /**
     * 当界面为更新模式时"应用全部"按钮的动作事件
     * @param e
     * @throws QMRemoteException
     */ 
	public void applyAllButton_actionPerformed(ActionEvent e)throws QMRemoteException
	{
			CodingIfc mark =(CodingIfc)(changeHash.get(this.changeBombox.getSelectedItem())); // 获得更改标记
			Vector coll = ((CappRouteManageJDialog) parentDialog).getPartLinks();// 获得路线管理器缓存中所有的零部件编号
			if (coll != null && coll.size() != 0)
			{
				String currentId=myRoute.getBsoID();//当前路线
				for (Iterator i = coll.iterator(); i.hasNext();)
				{
					ListRoutePartLinkInfo Info = (ListRoutePartLinkInfo) useServiceMethod(
						"PersistService", "refreshInfo",new Class[] { String.class }, new Object[] { i.next().toString() });
					String bsoID = Info.getRouteID();
					if(bsoID == null || bsoID.equals("") || bsoID.equals(currentId))
					  continue;
					TechnicsRouteInfo TechnicsRoutInfo = (TechnicsRouteInfo) useServiceMethod(
						"PersistService", "refreshInfo",new Class[] { String.class },new Object[] { bsoID });
				  TechnicsRoutInfo.setModefyIdenty(mark);
				    useServiceMethod("PersistService", "saveValueInfo",new Class[] { BaseValueIfc.class },new Object[] { TechnicsRoutInfo });
			  }
			  
			  //System.out.println("111111111");
			  RouteTreePanel treePanel = ((CappRouteManageJDialog) parentDialog).getTreePanel();
			  RouteTreeObject selectobj = treePanel.getSelectedObject();
			  //System.out.println("selectobj=="+selectobj);
			  //System.out.println("selectobj.getObject()=="+selectobj.getObject());
        for (int i = 0; i < coll.size(); i++) 
        {
        	ListRoutePartLinkIfc partlink = (ListRoutePartLinkIfc) coll.elementAt(i);
        	RoutePartTreeObject subobj = new RoutePartTreeObject(partlink);
        	//System.out.println("subobj=="+subobj);
        	//System.out.println("subobj.getObject()=="+subobj.getObject());
        	if(!((BaseValueIfc)(subobj.getObject())).getBsoID().equals(((BaseValueIfc)(selectobj.getObject())).getBsoID()))
        	{
        		treePanel.refreshNode(subobj);
        	}
        }
      }
  }
  //CCEnd SS1

   public void okPress(){
      ProgressDialog progressDialog = null;
          try {
            if(descriJTextArea.getText().getBytes().length>2000)
          {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                     null);
             String message = QMMessage
                     .getLocalizedMessage(RESOURCE, "54", null);
             JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                     JOptionPane.WARNING_MESSAGE);
             this.descriJTextArea.grabFocus();
             return ;
          }
    	//CCBegin SS5
    	if(checkPartRoute())
    	{
    		return;
    	}
    	//CCEnd SS5
              //设置鼠标形状为等待状态
              setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
              this.okJButton.setEnabled(false);
              this.cancelJButton.setEnabled(false);
              this.editJButton.setEnabled(false);
              //显示保存进度
//            ProgressService.setProgressText(QMMessage.getLocalizedMessage(
//                    RESOURCE, CappRouteRB.SAVING, null));
//            ProgressService.showProgress();
              //（问题二）zz 20060925 换乘新版进度条
              progressDialog = new ProgressDialog(parentJFrame);
              progressDialog.startProcess();
              save();
              isSave = true;              
          } catch (QMRemoteException ex) {
              isSave = false;
              //ProgressService.hideProgress();
            //  progressDialog.startProcess();
             progressDialog.stopProcess();
              String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                      null);
              JOptionPane
                      .showMessageDialog(getParentJFrame(),
                              ex.getClientMessage(), title,
                              JOptionPane.INFORMATION_MESSAGE);
          }
          //ProgressService.hideProgress();
         // progressDialog.startProcess();
          progressDialog.stopProcess();
          this.okJButton.setEnabled(true);
          this.cancelJButton.setEnabled(true);
          this.editJButton.setEnabled(true);
          setCursor(Cursor.getDefaultCursor());

}
    /**
     * 保存(新建或更新的)工艺路线
     *
     * @throws QMRemoteException
     */
    private void save() throws QMRemoteException {
                
        //在更新模式下,如果没有打开路线图,则直接将路线串更新后提交保存
        if (getViewMode() == UPDATE_MODE && editDialog == null) {
            //lm modify 20040827 “路线说明”问题
            if (verbose) {
                System.out
                        .println("RouteTalkJPanel:在更新模式下,如果没有打开路线图,则直接将路线串更新后提交保存");
            }
            this.getTechnicsRoute().setRouteDescription(
                    descriJTextArea.getText());
            //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线，增加默认有效期字段
            this.getTechnicsRoute().setDefaultDescreption(this.validArea.getText());
            this.getTechnicsRoute().setIsAdopt(this.isAdoptRLCheckBox.isSelected());
            Object cc=changeHash.get(this.changeBombox.getSelectedItem());
            this.getTechnicsRoute().setModefyIdenty((CodingIfc)cc);
         //   this.getTechnicsRoute().setModefyIdenty((CodingIfc)changeHash.get(this.changeBombox.getSelectedItem()));
            //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线，增加默认有效期字段

            if (alterStatus == 0) {
                //调用服务,保存路线
                Class[] c = { ListRoutePartLinkIfc.class,
                        TechnicsRouteIfc.class, Collection.class };
                Object[] obj = { this.getListPartLink(),
                        this.getTechnicsRoute(), this.getBranchesOnlyUpdate() };
               useServiceMethod("TechnicsRouteService",
                        "createRouteByBranch", c, obj);
            } else if (alterStatus == 1) {
                Vector v = this.getBranchesOnlyUpdate();
                if (v != null) {
                    v.add(this.getTechnicsRoute());
                }
                Class[] c = { Collection.class };
                Object[] obj = { v };
                useServiceMethod("TechnicsRouteService",
                        "updateBranchInfos", c, obj);

                //红塔机器性能不好 修改 zz  start
                  v= null;
                //红塔机器性能不好 修改 zz  end

            }
            //CCBegin SS4
            isAdoptRLCheckBox.setSelected(myRoute.getIsAdopt());
            //CCEnd SS4
            
        } else {
            HashMap saveMap = new HashMap();
            this.getTechnicsRoute().setRouteDescription(
                    descriJTextArea.getText());
            //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
            this.getTechnicsRoute().setDefaultDescreption(this.validArea.getText());
            this.getTechnicsRoute().setIsAdopt(this.isAdoptRLCheckBox.isSelected());
            Object cc=changeHash.get(this.changeBombox.getSelectedItem());
            this.getTechnicsRoute().setModefyIdenty((CodingIfc)cc);
            //this.getTechnicsRoute().setModefyIdenty((CodingIfc)changeHash.get(this.changeBombox.getSelectedItem()));
            //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

            RouteItem routeItem = new RouteItem(this.getTechnicsRoute());
            if (getViewMode() == CREATE_MODE) {
                routeItem.setState(RouteItem.CREATE);
            } else if (getViewMode() == UPDATE_MODE && alterStatus == 0) {
                //this.setObjectNotPersist(routeItem.getObject());
                //routeItem.setState(RouteItem.CREATE);
                if (createNewMap != null) {
                    routeItem = (RouteItem) createNewMap.get(this
                            .getTechnicsRoute().getBsoName());
                }
            } else {
                routeItem.setState(RouteItem.UPDATE);

            }
            saveMap.put(this.getTechnicsRoute().getBsoName(), routeItem);

            //如果在新建模式下,或者如果路线图发生了变化,则重新获得新生成的路线分支并提交保存
            Vector v2 = (Vector) commitUpdatedBranches();
            if (v2 != null && v2.size() > 0) {
                if (verbose) {
                    System.out.println(":::::::::::::::: 生成 分支 的个数："
                            + v2.size());
                }
                saveMap.put("TechnicsRouteBranch", v2);
            }
      //红塔机器性能不好 修改 zz  start
                         v2 = null;
                        //红塔机器性能不好 修改 zz  end

            if (editDialog != null) {
                Vector v3 = this.branchNodeLinkVector; //永远为新建
                if (v3 != null && v3.size() > 0) {
                    if (verbose) {
                        System.out.println(":::::::::::::::: 生成 分支—节点 关联的个数："
                                + v3.size());
                    }
                    saveMap.put("RouteBranchNodeLink", v3);
                }
                 //红塔机器性能不好 修改 zz  start
                           v3 = null;
                          //红塔机器性能不好 修改 zz  end

                Vector v4 = editDialog.routeNodeItemVector;
                if (v4 != null && v4.size() > 0) {
                    if (verbose) {
                        System.out.println(":::::::::::::::: 生成 节点 的个数："
                                + v4.size());
                    }
                    saveMap.put("RouteNode", v4);
                }
        //红塔机器性能不好 修改 zz  start
                           v4 = null;
                          //红塔机器性能不好 修改 zz  end

                Vector v5 = editDialog.routeLinkItemVector;
                if (v5 != null && v5.size() > 0) {
                    if (verbose) {
                        System.out.println(":::::::::::::::: 生成 连线 的个数："
                                + v4.size());
                    }
                    saveMap.put("RouteNodeLink", v5);
                }
                //红塔机器性能不好 修改 zz  start
                   v5 = null;
                  //红塔机器性能不好 修改 zz  end

            }

            //调用服务,保存路线
            Class[] c = { ListRoutePartLinkIfc.class, HashMap.class };
            Object[] obj = { this.getListPartLink(), saveMap };
            //返回值: 数组第一个元素--工艺路线值对象,数组第二个元素--HashMap
            useServiceMethod("TechnicsRouteService", "saveRoute", c, obj);
            //红塔机器性能不好 修改 zz  start
                  saveMap = null;
                 //红塔机器性能不好 修改 zz  end
        }

        //最后,将界面刷新为查看状态
        descriJTextArea.setEditable(false);
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        this.isAdoptRLCheckBox.setEnabled(false);
        this.changeBombox.setEnabled(false);
        //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 0, 1, 2, 3, 4 }, false);
        qMMultiList.setCheckboxEditable(false);

        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        this.isChangeRoute = true;
        mode = VIEW_MODE;
        //刷新树节点
        RoutePartTreeObject treeobj = new RoutePartTreeObject(this
                .getListPartLink());
        RouteTreePanel treePanel = ((CappRouteManageJDialog) parentDialog)
                .getTreePanel();
        treePanel.refreshNode(treeobj);
        ((CappRouteManageJDialog) parentDialog).enableMenuItems(treeobj);
        ListRoutePartLinkIfc newlink = (ListRoutePartLinkIfc) treePanel
                .getSelectedObject().getObject();
        this.setListPartLink(newlink);
        
        
    }

    /**
     * 处理被更新的路线分支
     *
     * @return 更新后的路线分支集合(元素为RouteItem)
     */
    private Vector commitUpdatedBranches() {
        Vector v = new Vector();
        branchNodeLinkVector.clear();
        if (branchMap.size() > 0) {
            for (int i = 0; i < branchMap.size(); i++) {
                String bsoid = qMMultiList.getCellText(i, 0);
                if (verbose) {
                    System.out.println("提交时 路线分支 ID = " + bsoid);
                }
                Object[] objs2 = (Object[]) branchMap.get(bsoid);
                TechnicsRouteBranchInfo branch = (TechnicsRouteBranchInfo) objs2[0];
                Vector curPathNodes = (Vector) objs2[1];
                if (verbose) {
                    System.out.println("提交时 路线分支" + i + "的节点个数："
                            + curPathNodes.size());
                }
                if (qMMultiList.isCheckboxSelected(i, 4)) {
                    branch.setMainRoute(true);
                } else {
                    branch.setMainRoute(false);
                }
                setObjectNotPersist(branch); //路线分支永远为新建

                for (int j = 0; j < curPathNodes.size(); j++) {
                    RouteBranchNodeLinkInfo linkinfo = new RouteBranchNodeLinkInfo();
                    linkinfo.setRouteBranchInfo(branch);
                    DefaultGraphNode node = (DefaultGraphNode) curPathNodes
                            .elementAt(j);
                    linkinfo.setRouteNodeInfo((RouteNodeInfo) node
                            .getRouteItem().getObject());
                    RouteItem item = new RouteItem(linkinfo);
                    item.setState(RouteItem.CREATE);
                    branchNodeLinkVector.addElement(item);
                }

                RouteItem item = new RouteItem(branch);
                item.setState(RouteItem.CREATE);
                v.addElement(item);
            }
        }
        return v;
    }

    /**
     * 执行取消操作
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        try {
            processCancelCommond();
        } catch (QMRemoteException ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 执行取消
     *
     * @return 是否保存成功
     * @throws QMRemoteException
     */
    protected boolean processCancelCommond() throws QMRemoteException {
        if (getViewMode() == CREATE_MODE) {
            this.quitWhenCreate();
        } else if (getViewMode() == UPDATE_MODE) {
            this.quitWhenUpdate();
        }
        return isSave;
    }

    /**
     * 创建模式下，取消按钮的执行方法. 如果当前界面模式为创建,如果尚未保存,则提示用户是否保存;如果不保存,则退出界面.
     *
     * @throws QMRemoteException
     */
    private void quitWhenCreate() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.IS_SAVE_CREATE_ROUTE, null);
        if (this.confirmAction(s)) {
    	//CCBegin SS5
    	if(checkPartRoute())
    	{
    		return;
    	}
    	//CCEnd SS5
            this.save();
            isSave = true;
        } else {
            this.setVisible(false);
            isSave = false;
        }
        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenCreate() end...return is void ");

        }
    }

    /**
     * 更新模式下，取消按钮的执行方法. 如果当前界面模式为更新,如果尚未保存则提示用户是否保存;如果不保存,则将界面刷新为查看状态
     *
     * @throws QMRemoteException
     */
    private void quitWhenUpdate() throws QMRemoteException {
        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                CappRouteRB.IS_SAVE_UPDATE_ROUTE, null);
        if (this.confirmAction(s)) {
    	//CCBegin SS5
    	if(checkPartRoute())
    	{
    		return;
    	}
    	//CCEnd SS5
            this.save();
            isSave = true;
        } else {
            this.setViewMode(2);
            isSave = false;
        }
        if (verbose) {
            System.out
                    .println("cappclients.capproute.view.RouteListTaskJPanel.QuitWhenUpdate() end...return is void");

        }
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

    void editJButton_mouseEntered(MouseEvent e) {
        editJButton.setForeground(SystemColor.blue);
    }

    void editJButton_mouseExited(MouseEvent e) {
        editJButton.setForeground(SystemColor.black);
    }

    /**
     * 初始化路线单位树
     */
//  CCBegin by leixiao 2008-8-6 原因：解放升级工艺路线 ,改为路线组织机构



    private void initTree() {

        try {
          //初始化路线单位树
          CodingClassificationIfc cc = null;
          if (this.getTechnicsRouteList().getRouteListLevel().equals(
              RouteListLevelType.FIRSTROUTE.getDisplay())) {
            Class[] c = {
                String.class, String.class};
            Object[] obj = {
                "路线组织机构", "代码分类"};
            cc = (CodingClassificationIfc) RParentJPanel.useServiceMethod(
                "CodingManageService", "findClassificationByName", c, obj);
          }
          else {
            cc = (CodingClassificationIfc) RParentJPanel.refreshInfo(
                this.getTechnicsRouteList().getRouteListDepartment());
          }

          if (cc != null) {
            departmentTree = new CodeManageTree(cc);
            departmentTree.setShowsRootHandles(false);
          }
        }
        catch (QMRemoteException ex) {
          JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "information", null),
                                        JOptionPane.INFORMATION_MESSAGE);
        }

      }
//  CCEnd by leixiao 2008-8-6 原因：解放升级工艺路线
    
//  CCBegin by leixiao 2008-8-6 原因：解放升级工艺路线 
    void changeBombox_itemStateChanged(ItemEvent e) {

    }
    
    void isAdoptRLCheckBox_itemStateChanged(ItemEvent e) {
        if(this.isAdoptRLCheckBox.isSelected())
         {
           this.validArea.setEditable(false);
           this.validArea.setText(this.myRouteList.getDefaultDescreption());
         }else
         {
             this.validArea.setEditable(true);
         }
      }
//  CCEnd by leixiao 2008-8-6 原因：解放升级工艺路线
    //WorkThread
    class WorkThread extends QMThread {
         int myAction;
         public WorkThread(int action) {
             super();
             this.myAction = action;
         }


         /**
          * WKTread运行方法
          */
         public void run() {

                 switch (myAction) {
                 case OK:
                      okPress();
                     break;
             }
         }
     }
     
    //CCBegin SS7
    /**
     * 1、零部件（不含标准件,标准件编号规则：CQ、Q、T开头的零部件）编制装配路线时，
     *    如其上一级逻辑总成（逻辑总成编号规则：“XXXXGXX”如2800G01零部件第五位为“G”）不包含该零部件装配路线（装配路线不包含“协”）时，请系统给予提醒。
     * 2、逻辑总成编制制造路线时，如其不包含其子件（标准件除外）的装配路线（装配路线不包含“协”），请系统给予提醒。
     */
    private boolean checkPartRoute()
    {
    	if(true)
    	{
    		return false;
    	}
    	int size = qMMultiList.getNumberOfRows();
    	String partnum = this.myPart.getPartNumber().trim();
    	if (size > 0)
    	{
    		for (int i = 0; i < size; i++)
    		{
    			if (qMMultiList.isCheckboxSelected(i, 4))
    			{
    				String zp = qMMultiList.getCellText(i, 3);
    				System.out.println("zp=="+zp);
    				//CCBegin SS5
    				if(zp.indexOf("川")!=-1||zp.indexOf("岛")!=-1)
    				{
    					if (this.confirmAction("装配路线中含有“川”或“岛”路线，是否保存？"))
    					{
    						
    					}
    					else
    					{
    						return true;
    					}
    				}
    				//CCEnd SS5
    				
    				System.out.println("partnum=="+partnum);
    				//规则一，检验逻辑总成父件
    				//不是标准件
    				if(!(partnum.startsWith("CQ")||partnum.startsWith("Q")||partnum.startsWith("T")))
    				{
    					System.out.println("规则一");
    					//得到父件路线信息，检验逻辑总成件的路线
    					Class[] params = {QMPartMasterIfc.class,String.class};
    					Object[] values = {this.myPart,this.myRouteList.getBsoID()};
    					Vector result = null;
    					try
    					{
    						result = (Vector) RouteListTaskJPanel.invokeRemoteMethodWithException(this,"TechnicsRouteService", "findParentsAndRoutes", params, values);
    					}
    					catch (QMRemoteException ex)
    					{
    						JOptionPane.showMessageDialog(this, ex.getClientMessage(), "错误", JOptionPane.ERROR_MESSAGE);
    						return true;
    					}
    					int size1 = result.size();
    					System.out.println("size1=="+size1);
    					for (int ii = 0; ii < size1; ii++)
    					{
    						Object[] objs = (Object[]) result.elementAt(ii);
    						QMPartInfo parent = (QMPartInfo) objs[0];
    						System.out.println("parent=="+parent.getPartNumber());
    						//如果是逻辑总成，查看路线是否包含选中零部件的装配路线（不包含“协”），如果不包含，提示
    						if(parent.getPartNumber().trim().length()>5&&parent.getPartNumber().trim().substring(4,5).equals("G"))
    						{
    							String branch = (String) objs[3];
    							System.out.println("branch==="+branch);
    							if(branch==null)
    							{
    								if (this.confirmAction("父逻辑总成"+parent.getPartNumber().trim()+"的制造路线为空，不包含当前件的装配路线，是否保存？"))
    								{
    								}
    								else
    								{
    									return true;
    								}
    							}
    							else
    							{
    								if(zp!=null&&!zp.equals("无")&&!zp.equals("协"))
    								{
    									if(branch.indexOf(zp)==-1)
    									{
    										if (this.confirmAction("父逻辑总成"+parent.getPartNumber().trim()+"的制造路线不包含当前件的装配路线，是否保存？"))
    										{
    										}
    										else
    										{
    											return true;
    										}
    									}
    								}
    							}
    						}
    					}
    				}
    				
    				//规则二，如果该件是逻辑总成，检验子件
    				if(partnum.length()>5&&partnum.substring(4,5).equals("G"))
    				{
    					System.out.println("规则二");
    					String zz = qMMultiList.getCellText(i, 2);
    					System.out.println("zz=="+zz);
    					//获取子件路线
    					Class[] params = {QMPartIfc.class};
    					Object[] values = {this.myPartInfo};
    					HashMap map = null;
    					try
    					{
    						map = (HashMap) RouteListTaskJPanel.invokeRemoteMethodWithException(this,"TechnicsRouteService", "getSubPartRoute", params, values);
    					}
    					catch (QMRemoteException ex)
    					{
    						JOptionPane.showMessageDialog(this, ex.getClientMessage(), "错误", JOptionPane.ERROR_MESSAGE);
    						return true;
    					}
    					for (Iterator iter = map.keySet().iterator(); iter.hasNext(); )
    					{
    						String subnum = (String)iter.next();
    						String[] tmp = (String[]) map.get(subnum);
    						//逻辑总成编制制造路线时，如其不包含其子件（标准件除外）的装配路线（装配路线不包含“协”），请系统给予提醒。
    						if(tmp[1]!=null&&!tmp[1].equals("")&&!tmp[1].equals("无")&&!tmp[1].equals("协"))
    						{
    							if(zz.indexOf(tmp[1])==-1)
    							{
    								if (this.confirmAction("当前逻辑总成的制造路线，不包含子件"+subnum+"的装配路线("+tmp[1]+")，是否保存？"))
    								{
    								}
    								else
    								{
    									return true;
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	
    	return false;
    }
    //CCEnd SS7
    
    //CCBegin SS8
    /**
     * 检查路线是否包含卡车厂路线
     * 总、饰、架、薄、厚、涂、焊、箱
     */
    private boolean checkMainRoute(String route)
    {
    	String routestr = RemoteProperty.getProperty("KaCheChangRoute", "总,饰,架,薄,厚,涂,焊,箱1");
    	System.out.println("routestr==="+routestr);
    	String[] str = routestr.split(",");
    	boolean flag = false;
    	for(int i=0;i<str.length;i++)
    	{
    		if(route.indexOf(str[i])!=-1)
    		{
    			flag = true;
    			break;
    		}
    	}
    	System.out.println("checkMainRoute result="+flag);
    	return flag;
    }
    //CCEnd SS8
}

 /**
  * <p>Title:编辑按钮鼠标适配器</p>
  * <p>Description: </p>
  */
class RouteTaskJPanel_editJButton_mouseAdapter extends
        java.awt.event.MouseAdapter {
    private RouteTaskJPanel adaptee;

    RouteTaskJPanel_editJButton_mouseAdapter(RouteTaskJPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseEntered(MouseEvent e) {
        adaptee.editJButton_mouseEntered(e);
    }

    public void mouseExited(MouseEvent e) {
        adaptee.editJButton_mouseExited(e);
    }
}
//  CCBegin by leixiao 2008-8-6 原因：解放升级工艺路线
    class RouteTaskJPanel_changeBombox_itemAdapter implements java.awt.event.ItemListener {
    	  RouteTaskJPanel adaptee;

    	  RouteTaskJPanel_changeBombox_itemAdapter(RouteTaskJPanel adaptee) {
    	    this.adaptee = adaptee;
    	  }
    	  public void itemStateChanged(ItemEvent e) {
    	    adaptee.changeBombox_itemStateChanged(e);
    	  }
    	}

    class RouteTaskJPanel_isAdoptRLCheckBox_itemAdapter implements java.awt.event.ItemListener {
    	  RouteTaskJPanel adaptee;

    	  RouteTaskJPanel_isAdoptRLCheckBox_itemAdapter(RouteTaskJPanel adaptee) {
    	    this.adaptee = adaptee;
    	  }
    	  public void itemStateChanged(ItemEvent e) {
    	    adaptee.isAdoptRLCheckBox_itemStateChanged(e);
    	  }
    	}
//    CCBEnd by leixiao 2008-8-6 原因：解放升级工艺路线


