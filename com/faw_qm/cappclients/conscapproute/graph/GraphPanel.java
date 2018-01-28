/**
 * 生成程序 GraphPanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1  郭晓亮  2009/12/23 原因：TD问题，域v4r3FunctionTest,TD号：2537
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.Color;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import com.faw_qm.cappclients.conscapproute.util.GraphRB;
import com.faw_qm.cappclients.conscapproute.view.RParentJPanel;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteCategoryType;

/**
 * <p> Title:画路线图的界面组件 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public class GraphPanel extends AbstractGraphView
{
    /** 编辑路线节点的界面对象 */
    private NodeAttriEditJDialog nodeAttriEditJDialog;

    /** 连接线的鼠标监听 */
    private GraphLabelComponentMouseListener theGraphLabelComponentMouseListener;

    /** 路线节点的鼠标监听 */
    private GraphNodeComponentMouseListener theGraphNodeComponentMouseListener;

    /** 本界面组件的动作监听 */
    private GraphViewActionListener theGraphViewActionListener;

    /** 本界面组件的鼠标监听 */
    private GraphViewMouseListener theGraphViewMouseListener;

    /** 标记一个节点组件 */
    private GraphNodeComponent savedNode;

    /** 当前所选择的连接线 */
    private GraphLinkComponent selectedLinkComponent;

    /** 被插入的新节点 */
    private GraphNode insertedNode;

    /** 被插入的新连接 */
    private GraphLink insertedLink;

    /** 当前所选择的路线节点组件 */
    private GraphNodeComponent selectedNodeComponent;

    /** 本界面组件的尺寸 */
    private Dimension size;

    /** 缓存路线节点的哈西表（键为GraphNode,元素类型为GraphNodeComponent）,包含处于新建\更新状态的节点 */
    private Hashtable nodeHashtable;

    /** 缓存路线连接的哈西表（键为GraphLink,元素类型为GraphLinkComponent），包含处于新建\更新状态的连接 */
    private Hashtable linkHashtable;

    /** 缓存被删除的路线节点（元素类型为GraphNode），用于提交保存 */
    private Vector deletedNodeVector;

    /** 缓存被删除的路线连接（元素类型为GraphLink），用于提交保存 */
    private Vector deletedLinkVector;

    /** 标记：指针模式 */
    public static final int POINTER = 0;

    /** 标记：节点模式 */
    public static final int NODE = 1;

    /** 标记：连接模式 */
    public static final int LINK = 2;

    /** 标记界面模式 */
    private int mode;

    /** 标记前界面模式 */
    private int previousMode;

    /** 是否可编辑 */
    private boolean editMode;

    private String clickNodeAction;

    private String dblClickNodeAction;

    /** 动作监听 */
    transient java.awt.event.ActionListener actionListener;

    /** 标记：装载本界面组件GraphPanel的滚动面板 */
    private JScrollPane scrollPane;

    /** 本界面组件的背景 */
    private Image offscreen;

    /** 本界面组件背景的大小 */
    private Dimension offscreensize;

    /** 本界面组件的画笔 */
    private Graphics offgraphics;

    /** 装载本界面中所有处于选中状态的连接线组件(GraphLinkComponent) */
    private Vector selected;

    /** 起始点 */
    private Point start;

    /** 终点 */
    private Point end;

    /** 移动行为是否已经结束 */
    private boolean moveEnd;

    /** 是否发生了移动 */
    private boolean moved;

    /** 本界面的尺寸 */
    private Dimension canvasSize;

    /** 装载本界面组件中所有处于选中状态的节点组件（GraphNodeComponent） */
    private Vector movingNodes;

    /** 本界面的可视窗口 */
    private JViewport viewport;

    /** 资源文件路径 */
    public static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 父窗口 */
    private Frame parentFrame;

    /** 路径集合 */
    public Vector pathVector = new Vector();

    /** 单条路径向量 */
    private Vector pathOne = new Vector();

    /** 单条路径向量 */
    private Vector pathTwo = new Vector();

    /** 是否路线路径存在闭环 */
    private boolean isClosedLoop = false;

    /** 缓存最新的路线单位BsoID */
    private String currentDepartmentBsoID;

    /** 缓存最新的路线单位名称 */
    private String currentDepartmentName;

    /** 当前路线图所属的工艺路线 */
    private TechnicsRouteIfc myTechnicsRoute;

    /** 调用本面板的对话框 */
    private JDialog editDialog;

    /**
     * 构造函数
     * @param parent
     * @param flag 是否可编辑
     * @param dialog 调用本面板的对话框
     */
    public GraphPanel(Frame parent, boolean flag, JDialog dialog)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: GraphPanel() begin...");
        parentFrame = parent;
        editDialog = dialog;
        setBackground(Color.white);
        size = new Dimension(20000, 5000);
        nodeHashtable = new Hashtable();
        this.setAutoscrolls(true);
        linkHashtable = new Hashtable();
        deletedNodeVector = new Vector();
        deletedLinkVector = new Vector();
        mode = 0;
        previousMode = 0;
        editMode = true;
        selected = new Vector(5);
        start = new Point();
        end = new Point();
        moveEnd = false;
        moved = false;
        editMode = flag;

        //setLayout(null);

        this.setPreferredSize(new Dimension(2000, 1140));
        setMode(0);
        theGraphViewActionListener = new GraphViewActionListener();
        theGraphViewMouseListener = new GraphViewMouseListener();
        super.addMouseListener(theGraphViewMouseListener);
        addMouseMotionListener(theGraphViewMouseListener);

        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: GraphPanel() end.");
    }

    /**
     * 设置当前路线图所属的工艺路线
     * @param route 工艺路线
     */
    public void setTechnicsRoute(TechnicsRouteIfc route)
    {
        myTechnicsRoute = route;
    }

    /**
     * 获得当前路线图所属的工艺路线
     * @return TechnicsRouteIfc 工艺路线
     */
    public TechnicsRouteIfc getTechnicsRoute()
    {
        return myTechnicsRoute;
    }

    /**
     * 获得父窗口
     * @return JScrollPane 父窗口
     */
    public JScrollPane getParentJScrollPane()
    {
        Component parent = getParent();
        while(!(parent instanceof JScrollPane))
        {
            parent = parent.getParent();
        }
        return (JScrollPane)parent;
    }

    /**
     * 获得父窗口
     * @return Frame 父窗口
     */
    public Frame getParentFrame()
    {
        return this.parentFrame;
    }

    /**
     * 获得首选尺寸
     * @return Dimension
     */
    public Dimension getPreferredSize()
    {
        return size;
    }

    /**
     * 设置当前模式（指针、节点、连接）
     * @param i int 0——指针，1——节点，2——连接
     * @roseuid 4029FEC3032F
     */
    public void setMode(int i)
    {
        previousMode = mode;
        mode = i;
        if(editDialog != null && editDialog instanceof RouteGraphEditJDialog)
            ((RouteGraphEditJDialog)editDialog).setPointerMode(i);
    }

    /**
     * 获取模式，返回表示该模式的整数值
     * @return int 整型 模式值
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * 设置模式
     * @param graphmodel -图元模式（节点或连接）接口
     * @throws QMException 
     * @throws QMPropertyVetoException
     */
    public synchronized void setModel(GraphModel graphmodel) throws QMException
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:setModel(GraphModel) begin...");
        removeAll();//清空
        nodeHashtable = new Hashtable();
        linkHashtable = new Hashtable();
        super.setModel(graphmodel);
        for(Enumeration enumeration = getModel().allNodes();enumeration.hasMoreElements();insertGraphNode((GraphNode)enumeration.nextElement()))
            ;
        for(Enumeration enumeration1 = getModel().allLinks();enumeration1.hasMoreElements();insertGraphLink((GraphLink)enumeration1.nextElement()))
            ;
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:setModel(GraphModel) end.");
    }

    /**
     * 设置上一级的模式
     */
    public void setPreviousMode()
    {
        mode = previousMode;
        if(editDialog != null && editDialog instanceof RouteGraphEditJDialog)
            ((RouteGraphEditJDialog)editDialog).setPointerMode(mode);
    }

    /**
     * 把指定的连接添加入GraphPanel中
     * @param graphlink 指定的连接
     * @roseuid 4029FF7101B3
     */
    public void insertGraphLink(GraphLink graphlink)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:insertGraphLink() begin...");
        try
        {
            GraphLinkComponent graphlinkcomponent = new GraphLinkComponent(graphlink);
            add(graphlinkcomponent);
            //设置起始点
            graphlinkcomponent.setFrom((GraphNodeComponent)nodeHashtable.get(graphlink.getPredecessor()));
            //设置终点
            graphlinkcomponent.setTo((GraphNodeComponent)nodeHashtable.get(graphlink.getSuccessor()));

            linkHashtable.put(graphlink, graphlinkcomponent);
            //给连接添加监听器
            graphlinkcomponent.addActionListener(theGraphViewActionListener);
            return;
        }catch(Exception qmpropertyvetoexception)
        {
            String message = qmpropertyvetoexception.getMessage();
            DialogFactory.showWarningDialog(this, message);
            qmpropertyvetoexception.printStackTrace();
        }
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:insertGraphLink() end.");

    }

    /**
     * 把指定的节点添加到GraphPanel中
     * @param graphnode 图元节点接口
     * @roseuid 4029FF870092
     */
    public void insertGraphNode(GraphNode graphnode)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:insertGraphNode() begin...");

        GraphNodeComponent graphnodecomponent = new GraphNodeComponent(graphnode);
        GraphLabelComponent graphlabelcomponent = graphnodecomponent.getLabelComponent();
        add(graphnodecomponent);
        graphnodecomponent.updateDescription();
        add(graphlabelcomponent);

        nodeHashtable.put(graphnode, graphnodecomponent);

        graphnodecomponent.addActionListener(theGraphViewActionListener);

        theGraphNodeComponentMouseListener = new GraphNodeComponentMouseListener();
        graphnodecomponent.addMouseListener(theGraphNodeComponentMouseListener);
        graphnodecomponent.addMouseMotionListener(theGraphNodeComponentMouseListener);

        theGraphLabelComponentMouseListener = new GraphLabelComponentMouseListener();
        graphlabelcomponent.addMouseListener(theGraphLabelComponentMouseListener);
        graphlabelcomponent.addMouseMotionListener(theGraphLabelComponentMouseListener);
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:insertGraphNode() end.");
    }

    /**
     * 删除哈西表中的记录，在GraphPanel中删除显示的连接组件
     * @param graphlink 图元连接接口
     * @roseuid 4029FFA301FB
     */
    public void removeGraphLink(GraphLink graphlink)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphLink() begin...");
        GraphLinkComponent graphlinkcomponent = (GraphLinkComponent)linkHashtable.get(graphlink);
        if(graphlinkcomponent == null)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphLink() 没有连接被删除 end.");
            return;
        }else
        {
            linkHashtable.remove(graphlink);//删除哈西表中的记录
            graphlink.getRouteItem().setState(RouteItem.DELETE);
            //add by guoxl on 2008.3.20(判断deletedLinkVector里是否包含当前连接)
            if(!deletedLinkVector.contains(graphlink))
                //add by guoxl end
                deletedLinkVector.addElement(graphlink);
            remove(graphlinkcomponent);//在Panel中删除显示的图形链接组件
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphLink() end.");
            return;
        }

    }

    /**
     * 删除哈希表中的节点、图元节点组件及其标签组件
     * @param graphnode 图元节点接口
     * @roseuid 4029FFB7038A
     */
    public void removeGraphNode(GraphNode graphnode)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphNode() begin...");
        GraphNodeComponent graphnodecomponent = (GraphNodeComponent)nodeHashtable.get(graphnode);
        if(graphnodecomponent == null)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphNode() end(1).");
            return;
        }else
        {
            nodeHashtable.remove(graphnode);
            graphnode.getRouteItem().setState(RouteItem.DELETE);
            deletedNodeVector.addElement(graphnode);
            remove(graphnodecomponent.getLabelComponent());
            remove(graphnodecomponent);
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphNode() end(2).");
            return;
        }

    }

    /**
     * 设置当前被插入的节点为指定的节点
     * @param graphnode 指定的节点
     * @roseuid 402A001D01A6
     */
    public void setGraphNodeInsert(GraphNode graphnode)
    {
        selectedLinkComponent = null;
        insertedNode = graphnode;
    }

    /**
     * 获得当前被插入的节点
     * @return capproute.graph.GraphNode 图元节点接口
     * @roseuid 402A00340113
     */
    public GraphNode getGraphNodeInsert()
    {
        return insertedNode;
    }

    /**
     * 设置当前被插入的连接为指定的连接
     * @param graphlink 指定的连接
     * @roseuid 402A004D02A0
     */
    public void setGraphLinkInsert(GraphLink graphlink)
    {
        selectedLinkComponent = null;
        insertedLink = graphlink;
    }

    /**
     * 获得当前被插入的连接
     * @return capproute.graph.GraphLink 图元连接接口
     * @roseuid 402A005E0041
     */
    public GraphLink getGraphLinkInsert()
    {
        return insertedLink;
    }

    /**
     * 获取单击节点行为，返回字符串
     * @return String
     */
    public String getClickNodeAction()
    {
        return clickNodeAction;
    }

    /**
     * 设置单击节点行为
     * @param s String
     */
    public void setClickNodeAction(String s)
    {
        clickNodeAction = s;
    }

    /**
     * 得到双击节点事件的结果
     * @return String
     */
    public String getDblClickNodeAction()
    {
        return dblClickNodeAction;
    }

    /**
     * 设置双击节点事件
     * @param s -双击事件名称
     */
    public void setDblClickNodeAction(String s)
    {
        dblClickNodeAction = s;
    }

    /**
     * 删除被选中的所有节点和连接
     */
    public void deleteSelected()
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:deleteSelected() begin...");
        try
        {
            for(Enumeration enumeration = getSelectionModel().allSelectedNodes();enumeration.hasMoreElements();getModel().removeNode((GraphNode)enumeration.nextElement()))
                ;
            for(Enumeration enumeration1 = getSelectionModel().allSelectedLinks();enumeration1.hasMoreElements();getModel().removeLink((GraphLink)enumeration1.nextElement()))
                ;
            return;
        }
//        catch(NodeDoesNotExistException nodedoesnotexistexception)
//        {
//            nodedoesnotexistexception.printStackTrace();
//            return;
//        }
        catch(QMException e)
        {
            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
        }
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:deleteSelected() end.");
    }

    /**
     * 设置尺寸大小 i the new x-coordinate of this component j the new y-coordinate of this component k the new width of this component l the new height of this component
     */
    public void setBounds(int i, int j, int k, int l)
    {
        super.setBounds(i, j, k, l);
        repaint();
    }

    /**
     * 设置是否可激活 public void setEnabled(boolean enabled) { if(verbose) System.out.println("cappclients.capproute.graph.GraphPanel:setEnabled() begin..."); boolean oldEnabled = isEnabled();
     * super.setEnabled(enabled); if (!enabled && hasFocus()) { FocusManager.getCurrentManager().focusPreviousComponent(this); } firePropertyChange("enabled", oldEnabled, enabled); if (enabled !=
     * oldEnabled) { repaint(); } if(verbose) System.out.println("cappclients.capproute.graph.GraphPanel:setEnabled() end."); } /** 判断是否被激活
     * @return boolean
     */
    public boolean isEnabled()
    {
        return super.isEnabled();
    }

    /**
     * 设置链接行为监听器
     * @param actionlistener ActionListener
     */
    public synchronized void setLinkActionListener(java.awt.event.ActionListener actionlistener)
    {
        this.actionListener = actionlistener;
    }

    /**
     * 在界面上绘制图形节点和连接
     * @param g java.awt.Graphics
     * @roseuid 402A06740377
     */
    public void paint(Graphics g)
    {

        //用来清除背景，必须有的方法
        super.paint(g);
        if(scrollPane == null)
        {
            scrollPane = this.getParentJScrollPane();
        }
        Point point = scrollPane.getViewport().getViewPosition();
        Dimension dimension = scrollPane.getViewport().getSize();

        if(offscreen != null && (dimension.width != offscreensize.width || dimension.height != offscreensize.height))
            if(offscreen != null && (dimension.width != offscreensize.width || dimension.height != offscreensize.height))
            {
                offscreen.flush();//清除掉由该image使用的所有的对象
                offgraphics.dispose();
                offscreen = null;
            }
        if(offscreen == null)
        {
            offscreen = createImage(dimension.width, dimension.height);
            offscreensize = dimension;
        }
        offgraphics = offscreen.getGraphics();

        offgraphics.translate(-point.x, -point.y);
        offgraphics.clipRect(point.x, point.y, dimension.width, dimension.height);
        offgraphics.clearRect(point.x, point.y, dimension.width, dimension.height);
        //清除图形的背景，必须有的方法
        super.paint(offgraphics);

        if(mode == 2 && selectedLinkComponent != null)
        {
            selectedLinkComponent.drawLine(offgraphics);//该出刷新选中的链接显示的样式是直线还是折线
        }

        for(Enumeration enumeration = linkHashtable.elements();enumeration.hasMoreElements();)
        {
            GraphLinkComponent graphlinkcomponent = (GraphLinkComponent)enumeration.nextElement();
            if(graphlinkcomponent.isSelected())
                selected.addElement(graphlinkcomponent);
            else
                //刷新所有链接显示的样式 直线--------折线
                graphlinkcomponent.drawLine(offgraphics);
        }

        for(int i = 0;i < selected.size();i++)
        {
            GraphLinkComponent graphlinkcomponent1 = (GraphLinkComponent)selected.elementAt(i);
            graphlinkcomponent1.drawLine(offgraphics);
        }
        selected.removeAllElements();
        g.drawImage(offscreen, point.x, point.y, this);

    }

    /**
     * 将连接设置成直线
     * @param flag boolean 是否为直线
     */
    public void setStraitLines(boolean flag)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: setStraitLines() begin...");
        GraphLinkComponent graphlinkcomponent;
        for(Enumeration enumeration = linkHashtable.elements();enumeration.hasMoreElements();graphlinkcomponent.setStraitLine(flag))
            graphlinkcomponent = (GraphLinkComponent)enumeration.nextElement();

        repaint();//更新显示
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: setStraitLines() end.");

    }

    /**
     * 判断指定的点不是现有的
     * @param point 指定的点
     * @return boolean 总返回False
     * @roseuid 402A1F1E0181
     */
    public boolean isObjectExisting(Point point)
    {
        //System.out.println("Not implemented !");
        return false;
    }

    /**
     * 判断某节点上是否存在指定的连接，返回布尔型
     * @param graphlinkcomponent -画出连接的连线
     * @param mouseevent -鼠标事件
     * @return boolean 如果存在，返回True
     */
    private boolean isLinkAt(GraphLinkComponent graphlinkcomponent, MouseEvent mouseevent)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: isLinkAt() begin...");
        try
        {
            if(!mouseevent.isControlDown() && !mouseevent.isShiftDown())
                //在没有按下ctrl和shift键时，清空选择模型
                getSelectionModel().clearSelection();
            int i = graphlinkcomponent.getSelectedAt(mouseevent.getX(), mouseevent.getY());
            //节点在终端节点范围内
            if(i == 2)
            {
                if(!editMode)
                    return true;
                //在面板上显示链接
                getSelectionModel().add(graphlinkcomponent.getGraphLink());
                if(!mouseevent.isPopupTrigger() && mouseevent.getID() != 502)
                {

                    //所选链接的终端节点
                    //savedNode = selectedLinkComponent.getTo(); //whj
                    savedNode = graphlinkcomponent.getTo();//lm

                    //从所选模式中删除获得的链接
                    getSelectionModel().remove(graphlinkcomponent.getGraphLink());
                    getModel().removeLink(graphlinkcomponent.getGraphLink());
                    graphlinkcomponent.setSelected(true);
                    selectedLinkComponent = graphlinkcomponent;
                    moveEnd = true;
                }
                setMode(2);
                repaint();
                return true;
            }
            //节点在起始节点范围内
            if(i == 0)
            {
                if(!editMode)
                    return true;
                getSelectionModel().add(graphlinkcomponent.getGraphLink());
                if(!mouseevent.isPopupTrigger() && mouseevent.getID() != 502)
                {
                    savedNode = graphlinkcomponent.getFrom();
                    getSelectionModel().remove(graphlinkcomponent.getGraphLink());
                    getModel().removeLink(graphlinkcomponent.getGraphLink());
                    graphlinkcomponent.setSelected(true);
                    selectedLinkComponent = graphlinkcomponent;
                    moveEnd = false;
                }
                setMode(2);
                repaint();
                return true;
            }
            //节点在链接上
            if(i == 1)
            {
                getSelectionModel().add(graphlinkcomponent.getGraphLink());
                repaint();
                return true;
            }
        }catch(QMException e)
        {
            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }

    /**
     * 获得用户当前选择的路线单位
     * @return 路线单位的bsoID
     * @throws QMException 
     * @throws QMRemoteException
     */
    public String getCurrentRouteDepartment() throws QMException
    {
        if(currentDepartmentBsoID != null && !currentDepartmentBsoID.equals(""))
            return currentDepartmentBsoID;
        else
        {
            //请选择有效的路线单位
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.NO_SELECT_DEPARTMENT, null));
        }
    }

    /**
     * 设置当前所选择的路线单位
     * @param departmentBsoID 路线单位的bsoID
     * @param departmentName 路线单位的名称(要求是简称)
     */
    public void setCurrentRouteDepartment(String departmentBsoID, String departmentName)
    {
        currentDepartmentBsoID = departmentBsoID;
        currentDepartmentName = departmentName;
    }

    /**
     * 取得焦点
     * @param focusevent java.awt.event.FocusEvent
     * @roseuid 402A1F1E027C
     */
    public void focusGained(FocusEvent focusevent)
    {

    }

    /**
     * 失去焦点
     * @param focusevent java.awt.event.FocusEvent
     * @roseuid 402A1F1E0362
     */
    public void focusLost(FocusEvent focusevent)
    {

    }

    /**
     * 鼠标拖曳
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F1F006A
     */
    public void mouseDragged(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标移动
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F1F0165
     */
    public void mouseMoved(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标点中
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F1F0255
     */
    public void mouseClicked(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标进入
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F1F034F
     */
    public void mouseEntered(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标退出
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F200062
     */
    public void mouseExited(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标按下
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F200152
     */
    public void mousePressed(MouseEvent mouseevent)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mousePressed() begin...");
        AbstractGraphView.setFocusedComponent(this);
        boolean flag = false;
        //指针模式下
        if(mode == 0)
        {
            //存在选定的链接
            if(selectedLinkComponent != null)
                flag = isLinkAt(selectedLinkComponent, mouseevent);
            if(!flag)
            {
                for(Enumeration enumeration = linkHashtable.elements();enumeration.hasMoreElements();)
                {
                    GraphLinkComponent graphlinkcomponent = (GraphLinkComponent)enumeration.nextElement();
                    if(isLinkAt(graphlinkcomponent, mouseevent))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if(!flag)
                getSelectionModel().clearSelection();//清空所选模式
        }
        movingNodes = null;
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mousePressed() end.");
    }

    /**
     * 鼠标释放
     * @param mouseevent 鼠标事件
     * @roseuid 402A1F200238
     */
    public void mouseReleased(MouseEvent mouseevent)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mouseReleased() begin...");
        AbstractGraphView.setFocusedComponent(this);

        int i = mouseevent.getX();
        int j = mouseevent.getY();
        switch(mode)
        {
        default:
            break;

        case 0: // '\0'指针模式
            break;

        case 1: // '\001'节点模式
            try
            {
                //新建一个路线节点
                if(this.currentDepartmentName == null || this.currentDepartmentName.equals(""))
                {
                    throw new QMException("非法的路线单位！");
                }

                DefaultGraphNode newnode = new DefaultGraphNode(); //lm
                RouteNodeInfo nodeinfo = new RouteNodeInfo();
                nodeinfo.setRouteInfo(this.getTechnicsRoute());
                nodeinfo.setRouteType(RouteCategoryType.MANUFACTUREROUTE.getDisplay());
                nodeinfo.setNodeDepartment(getCurrentRouteDepartment());
                RouteItem nodeitem = new RouteItem(nodeinfo);
                nodeitem.setState(RouteItem.CREATE);
                newnode.setRouteItem(nodeitem);
                newnode.setDepartmentName(this.currentDepartmentName);
                //将新节点插入界面
                this.setGraphNodeInsert(newnode); //lm
                //在某位置插入节点
                insertedNode.setPosition(i - 16, j - 16);
                //获取模式添加节点
                getModel().addNode(insertedNode);
            }catch(Exception e)
            {
                String message = e.getMessage();
                DialogFactory.showInformDialog(this.getParentFrame(), message);
            }
            break;

        case 2: // '\002'
            if(!editMode)
                return;
            //如果在可编辑状态下存在选定的链接
            if(selectedLinkComponent != null)
            {
                for(Enumeration enumeration = nodeHashtable.elements();enumeration.hasMoreElements();)
                {
                    GraphNodeComponent graphnodecomponent = (GraphNodeComponent)enumeration.nextElement();
                    if(graphnodecomponent.containsGlobal(i, j))
                    {
                        try
                        {
                            //设置上一级模式
                            setPreviousMode();
                            if(moveEnd)
                                //设置为链接的终端节点
                                selectedLinkComponent.setTo(graphnodecomponent);
                            else
                                //设置为链接的起始节点
                                selectedLinkComponent.setFrom(graphnodecomponent);

                            DefaultGraphLink graphlink = (DefaultGraphLink)selectedLinkComponent.getGraphLink();
                            RouteItem linkitem = new RouteItem(graphlink.getRouteItem().getObject());
                            linkitem.setState(RouteItem.CREATE);
                            graphlink.setRouteItem(linkitem); //lm 20040420
                            // Modify
                            //GraphLink graphlink =
                            // selectedLinkComponent.getGraphLink();//lm
                            // 20040420 Delete
                            getModel().addLink(graphlink);
                            //清空所选模式的信息
                            getSelectionModel().clearSelection();
                            //为所选模式添加选定的链接
                            getSelectionModel().add(graphlink);
                        }catch(Throwable e)
                        {
                            JOptionPane.showMessageDialog(getParentFrame(), e.getLocalizedMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        }
                        //把存储的节点置空
                        savedNode = null;
                        //把选择的链接置空
                        selectedLinkComponent = null;
                    }
                }
                //如果保存的节点不为空
                if(savedNode != null)
                {
                    try
                    {
                        setPreviousMode();
                        if(moveEnd)
                            selectedLinkComponent.setTo(savedNode);
                        else
                            selectedLinkComponent.setFrom(savedNode);

                        DefaultGraphLink graphlink1 = (DefaultGraphLink)selectedLinkComponent.getGraphLink();
                        RouteItem linkitem = new RouteItem(graphlink1.getRouteItem().getObject());
                        linkitem.setState(RouteItem.CREATE);
                        graphlink1.setRouteItem(linkitem); //lm 20040420 Modify
                        //GraphLink graphlink1 =
                        // selectedLinkComponent.getGraphLink();
                        getModel().addLink(graphlink1);
                        getSelectionModel().clearSelection();
                        getSelectionModel().add(graphlink1);
                    }catch(Throwable e)
                    {
                        JOptionPane.showMessageDialog(getParentFrame(), e.getLocalizedMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                    }
                    savedNode = null;
                    selectedLinkComponent = null;
                }
            }
            //不存在选定的链接
            else
            {
                getSelectionModel().clearSelection();
                //清空所选模式的信息，设置为指针模式
                setMode(0);
            }

            break;
        }
        repaint();
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mouseReleased() end.");
    }

    /**
     * 把在图元模式事件里得到的节点添加到面板上
     * @param graphmodelevent 图元模型监听事件
     * @roseuid 402A1F200329
     */
    public void graphNodeInserted(GraphModelEvent graphmodelevent)
    {
        insertGraphNode(graphmodelevent.getNode());
    }

    /**
     * 删除图元模式事件中得到的节点，把模式设置为指针模式并重新描绘
     * @param graphmodelevent 图元模型监听事件
     * @roseuid 402A1F210031
     */
    public void graphNodeRemoved(GraphModelEvent graphmodelevent)
    {
        removeGraphNode(graphmodelevent.getNode());//删除节点
        setMode(0);//设置为指针模式
        repaint();//重新描绘组件
    }

    /**
     * 空方法---图形结构改变
     * @param graphmodelevent 图元模型监听事件
     * @roseuid 402A1F21012C
     */
    public void graphStructureChanged(GraphModelEvent graphmodelevent)
    {}

    /**
     * 把图元模式事件得到的链接添加到面板上
     * @param graphmodelevent 图元模型监听事件
     * @roseuid 402A1F210230
     */
    public void graphLinkInserted(GraphModelEvent graphmodelevent)
    {
        insertGraphLink(graphmodelevent.getLink());
    }

    /**
     * 删除连接并把模式设置为指针模式
     * @param graphmodelevent 图元模型监听事件
     * @roseuid 402A1F210334
     */
    public void graphLinkRemoved(GraphModelEvent graphmodelevent)
    {
        removeGraphLink(graphmodelevent.getLink());
        setMode(0);
    }

    /**
     * 获得被选中的节点组件
     * @return GraphNodeComponent 用来在GraphPanel上显示
     */
    public GraphNodeComponent getSelectedNodeComponent()
    {
        return selectedNodeComponent;
    }

    /**
     * 设置选中指定的节点组件
     * @param selectedNodeComponent GraphNodeComponent 用来在GraphPanel上显示
     */
    public void setSelectedNodeComponent(GraphNodeComponent selectedNodeComponent)
    {
        this.selectedNodeComponent = selectedNodeComponent;
    }

    /**
     * 接点被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D8016A
     */
    public void graphNodeSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        ((GraphNodeComponent)nodeHashtable.get(graphselectionmodelevent.getNode())).setSelected(true);
    }

    /**
     * 接点没有被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D8016C
     */
    public void graphNodeUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        ((GraphNodeComponent)nodeHashtable.get(graphselectionmodelevent.getNode())).setSelected(false);
    }

    /**
     * 链接被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D8016E
     */
    public void graphLinkSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        selectedLinkComponent = (GraphLinkComponent)linkHashtable.get(graphselectionmodelevent.getLink());
        if(selectedLinkComponent != null)
            selectedLinkComponent.setSelected(true);
    }

    /**
     * 链接没有被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D80170
     */
    public void graphLinkUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        selectedLinkComponent = null;
        ((GraphLinkComponent)linkHashtable.get(graphselectionmodelevent.getLink())).setSelected(false);

    }

    /**
     * 清空选择的节点或链接
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D80172
     */
    public void graphSelectionCleared(GraphSelectionModelEvent graphselectionmodelevent)
    {
        for(Enumeration enumeration = nodeHashtable.elements();enumeration.hasMoreElements();((GraphNodeComponent)enumeration.nextElement()).setSelected(false))
            ;
        for(Enumeration enumeration1 = linkHashtable.elements();enumeration1.hasMoreElements();((GraphLinkComponent)enumeration1.nextElement()).setSelected(false))
            ;
    }

    /**
     * 图元动作监听器内部类，监听图元组件的动作：添加节点或连接
     */
    class GraphViewActionListener implements java.awt.event.ActionListener
    {

        /**
         * @roseuid 402A1F95009C
         */
        public GraphViewActionListener()
        {

        }

        /**
         * 为选择的模式添加节点或链接
         * @param actionevent 鼠标事件
         * @roseuid 402A1F9500A6
         */
        public void actionPerformed(ActionEvent actionevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewActionListener.actionPerformed() begin...");
            //得到图元组件动作事件原文件
            GraphComponent graphcomponent = (GraphComponent)actionevent.getSource();
            //清除选择的模式
            getSelectionModel().clearSelection();
            //如果graphcomponent是节点组件
            if(graphcomponent instanceof GraphNodeComponent)
                //为选择的模式添加图元节点
                getSelectionModel().add(((GraphNodeComponent)graphcomponent).getGraphNode());
            else
            //如果graphcomponent是链接组件
            if(graphcomponent instanceof GraphLinkComponent)
                //为模式添加链接
                getSelectionModel().add(((GraphLinkComponent)graphcomponent).getGraphLink());
            //如果行为存在
            if(actionListener != null)
                //实现行为
                actionListener.actionPerformed(actionevent);
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewActionListener.actionPerformed() end.");

        }
    }

    /**
     * 图元视图鼠标监听器内部类:设置鼠标拖拽链接节点时的位置坐标
     */
    class GraphViewMouseListener extends MouseAdapter implements MouseMotionListener
    {

        /**
         * @roseuid 402A1F950178
         */
        public GraphViewMouseListener()
        {

        }

        /**
         * 鼠标拖曳
         * @param mouseevent 鼠标事件
         * @roseuid 402A1F95018C
         */
        public void mouseDragged(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewMouseListener.mouseDragged() begin...");

            if(!editMode)
                return;
            //可编辑模式
            switch(mode)
            {
            case 2: // '\002'
                //选定链接
                if(selectedLinkComponent != null)
                {
                    //移动到最后
                    if(moveEnd)
                        //设置选定终端节点的坐标
                        selectedLinkComponent.setEndPoint(mouseevent.getX(), mouseevent.getY());
                    else
                        //设置选定起始节点的坐标
                        selectedLinkComponent.setStartPoint(mouseevent.getX(), mouseevent.getY());

                    JScrollPane scrollpane = (JScrollPane)getParent().getParent();
                    JViewport viewport = scrollpane.getViewport();

                    Point point = scrollPane.getViewport().getViewPosition();
                    Dimension dimension = scrollPane.getViewport().getSize();

                    Point point1 = mouseevent.getPoint();
                    if(point1.x < point.x)
                        point.x = point1.x;
                    else if(point1.x > point.x + dimension.width)
                        point.x = point1.x - dimension.width;
                    if(point1.y < point.y)
                        point.y = point1.y;
                    else if(point1.y > point.y + dimension.height)
                        point.y = point1.y - dimension.height;
                    //scrollpane.setScrollPosition(point.x, point.y);
                    viewport.setViewPosition(new Point(point.x, point.y));

                    paint(getGraphics());
                    return;
                }
                break;
            }
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewMouseListener.mouseDragged() end.");

        }

        /**
         * 鼠标移动
         * @param mouseevent 鼠标事件
         * @roseuid 402A1F9501C9
         */
        public void mouseMoved(MouseEvent mouseevent)
        {

        }
    }

    /**
     * 图元标签（连接线）鼠标监听器(具体实现了鼠标拖动事件、鼠标释放事件)
     */
    class GraphLabelComponentMouseListener extends MouseAdapter implements MouseMotionListener
    {
        /**
         * 鼠标拖曳
         * @param mouseevent 鼠标事件
         * @roseuid 402A1F95018C
         */
        public void mouseDragged(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphLabelComponentMouseListener.mouseDragged() begin...");

            node = (GraphLabelComponent)mouseevent.getSource();
            if(!editMode)
                return;
            switch(mode)
            {
            case 1: // '\001'
            default:
                break;

            case 0: // '\0'
                if(movingNodes != null)
                {
                    moved = true;
                    boolean flag = false;
                    boolean flag1 = false;
                    for(int i = 0;i < movingNodes.size();i++)
                    {
                        GraphNodeComponent graphnodecomponent = (GraphNodeComponent)movingNodes.elementAt(i);
                        end = graphnodecomponent.getLocation();
                        end.x += mouseevent.getX() - start.x;
                        end.y += mouseevent.getY() - start.y;
                        if(!flag1 && end.x < 5 || end.x > canvasSize.width)
                            flag1 = true;
                        if(!flag && end.y < 5 || end.y > canvasSize.height)
                            flag = true;
                        if(flag && flag1)
                            return;
                    }

                    for(int j = 0;j < movingNodes.size();j++)
                    {
                        GraphNodeComponent graphnodecomponent1 = (GraphNodeComponent)movingNodes.elementAt(j);
                        end = graphnodecomponent1.getLocation();
                        if(!flag1)
                            end.x += mouseevent.getX() - start.x;
                        if(!flag)
                            end.y += mouseevent.getY() - start.y;
                        graphnodecomponent1.setLocation(end);
                    }

                    repaint(10L);
                    return;
                }
                break;

            case 2: // '\002'
                if(selectedLinkComponent == null)
                    break;
                if(moveEnd)
                {
                    end = node.getLocation();
                    selectedLinkComponent.setEndPoint(end.x + mouseevent.getX(), end.y + mouseevent.getY());
                }else
                {
                    end = node.getLocation();
                    selectedLinkComponent.setStartPoint(end.x + mouseevent.getX(), end.y + mouseevent.getY());
                }
                repaint();
                return;
            }
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphLabelComponentMouseListener.mouseDragged() end.");

        }

        /**
         * 空方法，鼠标移动事件
         * @param mouseevent MouseEvent 鼠标事件
         */
        public void mouseMoved(MouseEvent mouseevent)
        {}

        /**
         * 鼠标释放事件
         * @param mouseevent -鼠标事件
         */
        public void mouseReleased(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphLabelComponentMouseListener.mouseReleased() begin...");

            node = (GraphLabelComponent)mouseevent.getSource();
            switch(mode)
            {
            case 2: // '\002'
                if(!editMode)
                    return;
                if(selectedLinkComponent != null)
                {
                    end = node.getLocation();
                    end.x = end.x + mouseevent.getX();
                    end.y = end.y + mouseevent.getY();
                    /*
                     * 遍历所有标签接点
                     */
                    for(Enumeration enumeration = nodeHashtable.elements();enumeration.hasMoreElements();)
                    {
                        GraphNodeComponent graphnodecomponent = (GraphNodeComponent)enumeration.nextElement();
                        if(graphnodecomponent.containsGlobal(end.x, end.y))
                        {
                            try
                            {
                                setPreviousMode();
                                if(moveEnd)
                                    selectedLinkComponent.setTo(graphnodecomponent);
                                else
                                    selectedLinkComponent.setFrom(graphnodecomponent);
                                getModel().addLink(selectedLinkComponent.getGraphLink());
                            }

                            //                            catch(NodeDoesNotExistException e)
                            //                            {
                            //                                JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                            //                            }catch(LinkAlreadyExistException e)
                            //                            {
                            //                                JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                            //                            }catch(InvalidLinkException e)
                            //                            {
                            //                                JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                            //                            }
                            catch(Exception e)
                            {
                                String message = e.getMessage();
                                DialogFactory.showInformDialog(getParentFrame(), message);
                            }
                            selectedLinkComponent = null;
                            savedNode = null;
                            break;
                        }
                    }

                    if(savedNode != null && selectedLinkComponent != null)
                        try
                        {
                            setPreviousMode();
                            if(moveEnd)
                                selectedLinkComponent.setTo(savedNode);
                            else
                                selectedLinkComponent.setFrom(savedNode);
                            getSelectionModel().clearSelection();
                            getModel().addLink(selectedLinkComponent.getGraphLink());
                        }

                        //                        catch(NodeDoesNotExistException e)
                        //                        {
                        //                            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        //                        }catch(LinkAlreadyExistException e)
                        //                        {
                        //                            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        //                        }catch(InvalidLinkException e)
                        //                        {
                        //                            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        //                        }
                        catch(Exception e)
                        {
                            String message =e.getMessage();
                            DialogFactory.showInformDialog(getParentFrame(), message);
                        }
                    savedNode = null;
                    selectedLinkComponent = null;
                }
                repaint();
                return;
            }
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphLabelComponentMouseListener.mouseReleased() end.");
        }

        GraphLabelComponent node;

        GraphLabelComponentMouseListener()
        {}
    }

    /**
     * 图元节点鼠标监听器内部类(具体实现了鼠标按下事件、鼠标拖动事件、鼠标释放事件)
     */
    class GraphNodeComponentMouseListener extends MouseAdapter implements MouseMotionListener
    {

        /**
         * @roseuid 402A1F94033A
         */
        public GraphNodeComponentMouseListener()
        {}

        /**
         * 如果单击该节点，则设置该节点为被选中；如果双击，则弹出编辑节点界面
         * @param e MouseEvent 鼠标事件
         */
        public void mouseClicked(MouseEvent e)
        {
            node = (GraphNodeComponent)e.getSource();
            GraphPanel.this.setSelectedNodeComponent(node);
            if(e.getClickCount() == 2)
            {
                GraphPanel.this.updateSelectedNode(node);
                if(editDialog != null && editDialog instanceof RouteGraphEditJDialog)
                {
                    ((RouteGraphEditJDialog)editDialog).theStickyToolbar.setSelected(RouteGraphEditJDialog.POINTER);
                }
            }
        }

        /**
         * 鼠标按下
         * @param mouseevent 鼠标事件
         */
        public void mousePressed(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mousePressed() begin...");
            //设置焦点组件
            AbstractGraphView.setFocusedComponent(GraphPanel.this);
            //得到图元节点鼠标事件的原文件
            node = (GraphNodeComponent)mouseevent.getSource();
            //当模式为指针模式或节点模式时
            if(mode == 0 || mode == 1)
            {
                //设置指针模式
                setMode(0);
                canvasSize = getSize();
                canvasSize.width -= 40;
                canvasSize.height -= 40;
                //移动组件到一个新位置
                start.setLocation(mouseevent.getX(), mouseevent.getY());
                if(!mouseevent.isControlDown() && !mouseevent.isShiftDown() && !getSelectionModel().isSelected(node.getGraphNode()))
                    //清除模型中的全部信息
                    getSelectionModel().clearSelection();
                else
                    //删除模型中的某一节点信息
                    getSelectionModel().remove(node.getGraphNode());
                //得到选定模式的添加节点
                getSelectionModel().add(node.getGraphNode());
                //用所选模式的选定的节点总数建立向量
                movingNodes = new Vector(getSelectionModel().getNodesSelectedCount());

                for(Enumeration enumeration = getSelectionModel().allSelectedNodes();enumeration.hasMoreElements();movingNodes.addElement(nodeHashtable.get(enumeration.nextElement())))
                    ;
            }
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mousePressed() end.");

        }

        /**
         * 拖拽鼠标事件
         * @param mouseevent MouseEvent 鼠标事件
         */
        public void mouseDragged(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mouseDragged() begin...");
            if(verbose)
                System.out.println("%%%%%%%%%%%%%%%%%%%%%% mode = " + mode);
            node = (GraphNodeComponent)mouseevent.getSource();
            if(!editMode)
                return;
            switch(mode)
            {
            case 1: // '\001'节点模式
            default:
                break;

            case 0: // '\0'指针模式
                moved = true;
                if(movingNodes != null)
                {
                    boolean flag = false;
                    boolean flag1 = false;
                    for(int i = 0;i < movingNodes.size();i++)
                    {
                        node = (GraphNodeComponent)movingNodes.elementAt(i);
                        end = node.getLocation();
                        end.x += mouseevent.getX() - start.x;
                        end.y += mouseevent.getY() - start.y;
                        if(!flag1 && end.x < 5 || end.x > canvasSize.width)
                            flag1 = true;
                        if(!flag && end.y < 5 || end.y > canvasSize.height)
                            flag = true;
                        if(flag && flag1)
                            return;
                    }

                    for(int j = 0;j < movingNodes.size();j++)
                    {
                        node = (GraphNodeComponent)movingNodes.elementAt(j);
                        end = node.getLocation();
                        if(!flag1)
                            end.x += mouseevent.getX() - start.x;
                        if(!flag)
                            end.y += mouseevent.getY() - start.y;
                        node.setLocation(end);
                    }

                    JScrollPane scrollpane1 = (JScrollPane)getParent().getParent();
                    JViewport viewport1 = scrollpane1.getViewport();
                    Point point3 = scrollPane.getViewport().getViewPosition();
                    Dimension dimension1 = scrollPane.getViewport().getSize();

                    Point point4 = node.getLocation();
                    if(point4.x < point3.x)
                        point3.x = point4.x;
                    else if(point4.x + 32 > point3.x + dimension1.width)
                        point3.x = (point4.x + 32) - dimension1.width;
                    if(point4.y < point3.y)
                        point3.y = point4.y;
                    else if(point4.y + 32 > point3.y + dimension1.height)
                        point3.y = (point4.y + 32) - dimension1.height;
                    viewport1.setViewPosition(new Point(point3.x, point3.y));
                    paint(getGraphics());
                    return;
                }
                break;

            case 2: // '\002'连线模式
                if(selectedLinkComponent == null)
                {
                    moveEnd = true;
                    //插入一个新建的连接线
                    DefaultGraphLink newlink = new DefaultGraphLink(); //lm
                    RouteNodeLinkInfo linkInfo = new RouteNodeLinkInfo();
                    linkInfo.setRouteInfo(GraphPanel.this.getTechnicsRoute());
                    RouteItem linkitem = new RouteItem(linkInfo);
                    linkitem.setState(RouteItem.CREATE);
                    newlink.setRouteItem(linkitem);
                    GraphPanel.this.setGraphLinkInsert(newlink); //lm
                    selectedLinkComponent = new GraphLinkComponent(insertedLink);
                    try
                    {
                        selectedLinkComponent.setFrom(node);
                        selectedLinkComponent.setSelected(true);
                        start.setLocation(mouseevent.getX(), mouseevent.getY());
                    }catch(Exception e)
                    {
                        selectedLinkComponent = null;
                        String message =e.getMessage();
                        DialogFactory.showInformDialog(getParentFrame(), message);
                        mode = 1;
                        return;
                    }
                }
                if(selectedLinkComponent == null)
                    break;
                if(moveEnd)
                {
                    end = node.getLocation();
                    selectedLinkComponent.setEndPoint(end.x + mouseevent.getX(), end.y + mouseevent.getY());
                }else
                {
                    end = node.getLocation();
                    selectedLinkComponent.setStartPoint(end.x + mouseevent.getX(), end.y + mouseevent.getY());
                }
                JScrollPane scrollpane = (JScrollPane)getParent().getParent();
                JViewport viewport2 = scrollpane.getViewport();

                Point point = scrollPane.getViewport().getViewPosition();
                Dimension dimension = scrollPane.getViewport().getSize();

                Point point1 = node.getLocation();
                Point point2 = mouseevent.getPoint();
                point1.x += point2.x;
                point1.y += point2.y;
                if(point1.x < point.x)
                    point.x = point1.x;
                else if(point1.x > point.x + dimension.width)
                    point.x = point1.x - dimension.width;
                if(point1.y < point.y)
                    point.y = point1.y;
                else if(point1.y > point.y + dimension.height)
                    point.y = point1.y - dimension.height;

                viewport2.setViewPosition(new Point(point.x, point.y));

                paint(getGraphics());
                return;
            }
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mouseDragged() end.");

        }

        /**
         * 空方法，鼠标移动
         * @param mouseevent MouseEvent
         */
        public void mouseMoved(MouseEvent mouseevent)
        {}

        /**
         * 释放鼠标行为
         * @param mouseevent MouseEvent
         */
        public void mouseReleased(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mouseReleased() begin...");

            node = (GraphNodeComponent)mouseevent.getSource();

            String s = null;
            switch(mode)
            {
            case 0: // '\0'指针模式
                if(moved)
                {
                    for(int i = 0;i < movingNodes.size();i++)
                    {
                        node = (GraphNodeComponent)movingNodes.elementAt(i);
                        node.getGraphNode().setPosition(node.getLocation().x, node.getLocation().y);
                    }

                    movingNodes = null;
                }else
                {
                    if(!mouseevent.isControlDown() && !mouseevent.isShiftDown())
                        getSelectionModel().clearSelection();
                    else
                        getSelectionModel().remove(node.getGraphNode());
                    getSelectionModel().add(node.getGraphNode());
                }
                break;

            case 2: // '\002'连线模式
                if(!editMode)
                    return;
                if(selectedLinkComponent != null)
                {
                    end = node.getLocation();
                    end.x = end.x + mouseevent.getX();
                    end.y = end.y + mouseevent.getY();
                    for(Enumeration enumeration = nodeHashtable.elements();enumeration.hasMoreElements();)
                    {
                        GraphNodeComponent graphnodecomponent = (GraphNodeComponent)enumeration.nextElement();
                        if(graphnodecomponent.containsGlobal(end.x, end.y))
                        {
                            try
                            {
                                //System.out.println("BBBBBBBBBBB mode =
                                // "+mode);
                                //setPreviousMode(); lm
                                //System.out.println("CCCCCCCCCCC mode =
                                // "+mode);
                                if(moveEnd)
                                    selectedLinkComponent.setTo(graphnodecomponent);
                                else
                                    selectedLinkComponent.setFrom(graphnodecomponent);
                                getModel().addLink(selectedLinkComponent.getGraphLink());
                            }
                            //                            catch(NodeDoesNotExistException e)
                            //                            {
                            //                                JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                            //                            }catch(LinkAlreadyExistException e)
                            //                            {
                            //                                JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                            //                            }catch(InvalidLinkException e)
                            //                            {
                            //                                JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                            //                            }
                            catch(Exception e)
                            {
                                String message = e.getMessage();
                                DialogFactory.showInformDialog(getParentFrame(), message);
                            }
                            selectedLinkComponent = null;
                            savedNode = null;
                            break;
                        }
                    }

                    if(savedNode != null && selectedLinkComponent != null)
                        try
                        {
                            setPreviousMode();
                            if(moveEnd)
                                selectedLinkComponent.setTo(savedNode);
                            else
                                selectedLinkComponent.setFrom(savedNode);
                            getSelectionModel().clearSelection();
                            getModel().addLink(selectedLinkComponent.getGraphLink());
                        }

                        //                        catch(NodeDoesNotExistException e)
                        //                        {
                        //                            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        //                        }catch(LinkAlreadyExistException e)
                        //                        {
                        //                            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        //                        }catch(InvalidLinkException e)
                        //                        {
                        //                            JOptionPane.showMessageDialog(getParentFrame(), e.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        //                        }
                        catch(Exception e)
                        {
                            String message =e.getMessage();
                            DialogFactory.showInformDialog(getParentFrame(), message);
                        }
                    savedNode = null;
                    selectedLinkComponent = null;
                }else
                {
                    if(!mouseevent.isControlDown() && !mouseevent.isShiftDown())
                        getSelectionModel().clearSelection();
                    else
                        getSelectionModel().remove(node.getGraphNode());
                    getSelectionModel().add(node.getGraphNode());
                    setMode(0);
                    savedNode = null;
                    selectedLinkComponent = null;
                }
                repaint();
                break;
            }
            //if(s != null)
            //{
            // ActionEvent actionevent = new ActionEvent(GraphView.this, 1001,
            // s, mouseevent.getModifiers());
            //getActionLoader().actionPerformed(actionevent);
            //}
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mouseReleased() end.");

        }

        GraphNodeComponent node;
    }

    /**
     * 对指定的节点进行更新
     * @param curNode 指定的节点
     */
    public void updateSelectedNode(GraphNodeComponent curNode)
    {
        //Begin CR1
        //      nodeAttriEditJDialog = new NodeAttriEditJDialog(this.getParentFrame(),
        //              this.editMode);
        nodeAttriEditJDialog = new NodeAttriEditJDialog((RouteGraphEditJDialog)editDialog, this.getParentFrame(), this.editMode);
        //End CR1
        nodeAttriEditJDialog.setNodeComponent(curNode);
        nodeAttriEditJDialog.setVisible(true);

    }

    public void check(GraphNodeComponent curNode)
    {

    }

    /**
     * 获得界面中所有的路线节点
     * @return 集合元素为RouteItem
     */
    public Vector getAllRouteNodeItems()
    {
        if(verbose)
            System.out.println("capproute.graph.GraphPanel.getAllRouteNodeItems()::::");
        Vector nodes = new Vector();
        for(Enumeration e = nodeHashtable.elements();e.hasMoreElements();)
        {
            GraphNodeComponent nodecom = (GraphNodeComponent)e.nextElement();
            RouteItem item = nodecom.getGraphNode().getRouteItem();
            System.out.println("item===="+item.getObject());
            //if(RouteTaskJPanel.alterStatus == 0 &&
            // RouteTaskJPanel.getViewMode() == RouteTaskJPanel.UPDATE_MODE)
            //{
            //  RParentJPanel.setObjectNotPersist(item.getObject());
            //  item.setState(RouteItem.CREATE);
            //}
            //item.
            RouteNodeInfo node = (RouteNodeInfo)item.getObject();
            node.setNodeDepartmentName(((GraphNode)nodecom.getGraphNode()).getDepartmentName());
            RParentJPanel.setObjectNotPersist(item.getObject());
            item.setState(RouteItem.CREATE);
            nodes.addElement(item);
        }
        if(deletedNodeVector.size() > 0)
        {
            for(Enumeration e = deletedNodeVector.elements();e.hasMoreElements();)
            {
                DefaultGraphNode nodecom = (DefaultGraphNode)e.nextElement();
                //如果节点ID存在并且是真正存到数据库中的节点则将其加到集合中
                if(nodecom.getRouteItem().getObject().getBsoID() != null && nodecom.getRouteItem().getObject().getBsoID().startsWith("consRouteNode"))
                    nodes.addElement(nodecom.getRouteItem());
            }
        }
        return nodes;
    }

    /**
     * 获得界面中所有的路线连接
     * @return 集合元素为RouteItem
     */
    public Vector getAllRouteNodeLinkItems()
    {
        if(verbose)
            System.out.println("capproute.graph.GraphPanel.getAllRouteNodeLinkItems()::::");
        Vector links = new Vector();

        for(Enumeration e = linkHashtable.elements();e.hasMoreElements();)
        {
            GraphLinkComponent linkcom = (GraphLinkComponent)e.nextElement();
            RouteItem item = linkcom.getGraphLink().getRouteItem();
            //if(RouteTaskJPanel.alterStatus == 0 &&
            // RouteTaskJPanel.getViewMode() == RouteTaskJPanel.UPDATE_MODE)
            //{
            //  RParentJPanel.setObjectNotPersist(item.getObject());
            //   item.setState(RouteItem.CREATE);
            //}
            RParentJPanel.setObjectNotPersist(item.getObject());
            item.setState(RouteItem.CREATE);
            links.addElement(item);
        }

        if(deletedLinkVector.size() > 0)
        {
            //System.out.println("被删除的连接的个数："+deletedLinkVector.size());
            for(Enumeration e = deletedLinkVector.elements();e.hasMoreElements();)
            {
                DefaultGraphLink linkcom = (DefaultGraphLink)e.nextElement();
                RouteItem item = linkcom.getRouteItem();
                if(item.getObject().getBsoID() != null)
                {
                    //add by guoxl on 2008.3.20(更新状态下编辑路线节点并做闭环操作，保存后进行查看出现闭环现象)
                    // item.setState(RouteItem.DELETE);
                    //add by guoxl end
                    links.addElement(item);
                    //System.out.println("被删除的连接："+item.getObject().getBsoID());
                }
            }
        }
        return links;
    }

    /**
     * 生成路线串之前的准备工作
     */
    private void tranlateAllLinks()
    {
        if(verbose)
            System.out.println("capproute.graph.GraphPanel.tranlateAllLinks()::::");
        for(Enumeration e2 = nodeHashtable.keys();e2.hasMoreElements();)
        {
            DefaultGraphNode graphNode = (DefaultGraphNode)e2.nextElement();
            graphNode.frontNodeVec.clear();
            graphNode.behindNodeVec.clear();
        }
        for(Enumeration e = linkHashtable.elements();e.hasMoreElements();)
        {
            GraphLinkComponent linkcom = (GraphLinkComponent)e.nextElement();
            DefaultGraphLink graphlink = (DefaultGraphLink)linkcom.getGraphLink();
            DefaultGraphNode frontNode = (DefaultGraphNode)graphlink.getPredecessor();
            DefaultGraphNode behindNode = (DefaultGraphNode)graphlink.getSuccessor();
            frontNode.behindNodeVec.addElement(behindNode);
            behindNode.frontNodeVec.addElement(frontNode);
        }
    }

    /**
     * 检查是否存在闭环
     */
    private void checkIsClosedLoop(DefaultGraphNode button)
    {
        for(int i = 0;i < button.behindNodeVec.size();i++)
        {
            DefaultGraphNode ton = (DefaultGraphNode)(button.behindNodeVec.elementAt(i));
            ton.num++;
            if(ton.num > 30)
            {
                isClosedLoop = true;
                return;
            }
            checkIsClosedLoop(ton);
        }
    }

    /**
     * 分出路径
     * @param button 头节点
     */
    public void getPathAll(DefaultGraphNode curNode)
    {
        for(int i = 0;i < curNode.behindNodeVec.size();i++)
        {
            //当前头节点的某一后继节点ton
            DefaultGraphNode ton = (DefaultGraphNode)(curNode.behindNodeVec.elementAt(i));
            pathOne.addElement(ton);
            curNode.num++;

            //如果当前后继节点ton再没有后继节点
            if(ton.behindNodeVec.size() == 0)
            {
                pathVector.addElement(pathOne);
                //System.out.println("pathVector addElement");
                DefaultGraphNode tempButton;
                for(int x = pathOne.size() - 2;x >= 0;x--)
                {
                    tempButton = ((DefaultGraphNode)(pathOne.elementAt(x)));
                    if(tempButton.behindNodeVec.size() > tempButton.num)
                    {
                        for(int y = 0;y <= x;y++)
                        {
                            pathTwo.addElement((DefaultGraphNode)(pathOne.elementAt(y)));
                        }
                        pathOne = pathTwo;
                        pathTwo = new Vector();
                        break;
                    }else
                    {
                        tempButton.num = 0;
                    }
                }
            }
            getPathAll(ton);
        }
    }

    /**
     * 生成路线串
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void formRouteBranch() throws QMException
    {
        if(verbose)
            System.out.println("capproute.graph.GraphPanel.formRouteBranch()::::");
        Vector headNodeVec = new Vector();//局部变量-起始节点向量集合
        DefaultGraphNode graphNode;

        //生成路线串之前的准备工作
        tranlateAllLinks();

        //此双重循环判断路线连线是否存在闭环，如存在则弹出对话框
        for(Enumeration e = nodeHashtable.keys();e.hasMoreElements();)
        {
            graphNode = (DefaultGraphNode)e.nextElement();
            checkIsClosedLoop(graphNode);
            for(Enumeration e2 = nodeHashtable.keys();e2.hasMoreElements();)
            {
                graphNode = (DefaultGraphNode)e2.nextElement();
                graphNode.num = 0;
            }
            if(isClosedLoop)
            {
                isClosedLoop = false;
                //生成路线串失败，因为存在闭环。
                throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.CLOSED_LOOP, null));
            }
        }

        //清空全局变量-路径向量
        pathVector.removeAllElements();
        pathOne.removeAllElements();
        pathTwo.removeAllElements();

        for(Enumeration e = nodeHashtable.keys();e.hasMoreElements();)
        {
            graphNode = (DefaultGraphNode)e.nextElement();
            //找到所有的起始节点
            if(graphNode.frontNodeVec.size() == 0)
            {
                headNodeVec.addElement(graphNode);
            }
        }

        //此循环通过调用getPathAll方法找出每一条路线
        //并将每条路线添加进pathVector向量中
        for(int i = 0;i < headNodeVec.size();i++)
        {
            DefaultGraphNode nullHeadButton = new DefaultGraphNode();
            graphNode = (DefaultGraphNode)(headNodeVec.elementAt(i));
            nullHeadButton.behindNodeVec.addElement(graphNode);
            getPathAll(nullHeadButton);
            pathOne = new Vector();
            pathTwo = new Vector();
        }

    }
}
