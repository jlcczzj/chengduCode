/**
 * ���ɳ��� GraphPanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1  ������  2009/12/23 ԭ��TD���⣬��v4r3FunctionTest,TD�ţ�2537
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
 * <p> Title:��·��ͼ�Ľ������ </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class GraphPanel extends AbstractGraphView
{
    /** �༭·�߽ڵ�Ľ������ */
    private NodeAttriEditJDialog nodeAttriEditJDialog;

    /** �����ߵ������� */
    private GraphLabelComponentMouseListener theGraphLabelComponentMouseListener;

    /** ·�߽ڵ�������� */
    private GraphNodeComponentMouseListener theGraphNodeComponentMouseListener;

    /** ����������Ķ������� */
    private GraphViewActionListener theGraphViewActionListener;

    /** ����������������� */
    private GraphViewMouseListener theGraphViewMouseListener;

    /** ���һ���ڵ���� */
    private GraphNodeComponent savedNode;

    /** ��ǰ��ѡ��������� */
    private GraphLinkComponent selectedLinkComponent;

    /** ��������½ڵ� */
    private GraphNode insertedNode;

    /** ������������� */
    private GraphLink insertedLink;

    /** ��ǰ��ѡ���·�߽ڵ���� */
    private GraphNodeComponent selectedNodeComponent;

    /** ����������ĳߴ� */
    private Dimension size;

    /** ����·�߽ڵ�Ĺ�������ΪGraphNode,Ԫ������ΪGraphNodeComponent��,���������½�\����״̬�Ľڵ� */
    private Hashtable nodeHashtable;

    /** ����·�����ӵĹ�������ΪGraphLink,Ԫ������ΪGraphLinkComponent�������������½�\����״̬������ */
    private Hashtable linkHashtable;

    /** ���汻ɾ����·�߽ڵ㣨Ԫ������ΪGraphNode���������ύ���� */
    private Vector deletedNodeVector;

    /** ���汻ɾ����·�����ӣ�Ԫ������ΪGraphLink���������ύ���� */
    private Vector deletedLinkVector;

    /** ��ǣ�ָ��ģʽ */
    public static final int POINTER = 0;

    /** ��ǣ��ڵ�ģʽ */
    public static final int NODE = 1;

    /** ��ǣ�����ģʽ */
    public static final int LINK = 2;

    /** ��ǽ���ģʽ */
    private int mode;

    /** ���ǰ����ģʽ */
    private int previousMode;

    /** �Ƿ�ɱ༭ */
    private boolean editMode;

    private String clickNodeAction;

    private String dblClickNodeAction;

    /** �������� */
    transient java.awt.event.ActionListener actionListener;

    /** ��ǣ�װ�ر��������GraphPanel�Ĺ������ */
    private JScrollPane scrollPane;

    /** ����������ı��� */
    private Image offscreen;

    /** ��������������Ĵ�С */
    private Dimension offscreensize;

    /** ����������Ļ��� */
    private Graphics offgraphics;

    /** װ�ر����������д���ѡ��״̬�����������(GraphLinkComponent) */
    private Vector selected;

    /** ��ʼ�� */
    private Point start;

    /** �յ� */
    private Point end;

    /** �ƶ���Ϊ�Ƿ��Ѿ����� */
    private boolean moveEnd;

    /** �Ƿ������ƶ� */
    private boolean moved;

    /** ������ĳߴ� */
    private Dimension canvasSize;

    /** װ�ر�������������д���ѡ��״̬�Ľڵ������GraphNodeComponent�� */
    private Vector movingNodes;

    /** ������Ŀ��Ӵ��� */
    private JViewport viewport;

    /** ��Դ�ļ�·�� */
    public static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ������ */
    private Frame parentFrame;

    /** ·������ */
    public Vector pathVector = new Vector();

    /** ����·������ */
    private Vector pathOne = new Vector();

    /** ����·������ */
    private Vector pathTwo = new Vector();

    /** �Ƿ�·��·�����ڱջ� */
    private boolean isClosedLoop = false;

    /** �������µ�·�ߵ�λBsoID */
    private String currentDepartmentBsoID;

    /** �������µ�·�ߵ�λ���� */
    private String currentDepartmentName;

    /** ��ǰ·��ͼ�����Ĺ���·�� */
    private TechnicsRouteIfc myTechnicsRoute;

    /** ���ñ����ĶԻ��� */
    private JDialog editDialog;

    /**
     * ���캯��
     * @param parent
     * @param flag �Ƿ�ɱ༭
     * @param dialog ���ñ����ĶԻ���
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
     * ���õ�ǰ·��ͼ�����Ĺ���·��
     * @param route ����·��
     */
    public void setTechnicsRoute(TechnicsRouteIfc route)
    {
        myTechnicsRoute = route;
    }

    /**
     * ��õ�ǰ·��ͼ�����Ĺ���·��
     * @return TechnicsRouteIfc ����·��
     */
    public TechnicsRouteIfc getTechnicsRoute()
    {
        return myTechnicsRoute;
    }

    /**
     * ��ø�����
     * @return JScrollPane ������
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
     * ��ø�����
     * @return Frame ������
     */
    public Frame getParentFrame()
    {
        return this.parentFrame;
    }

    /**
     * �����ѡ�ߴ�
     * @return Dimension
     */
    public Dimension getPreferredSize()
    {
        return size;
    }

    /**
     * ���õ�ǰģʽ��ָ�롢�ڵ㡢���ӣ�
     * @param i int 0����ָ�룬1�����ڵ㣬2��������
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
     * ��ȡģʽ�����ر�ʾ��ģʽ������ֵ
     * @return int ���� ģʽֵ
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * ����ģʽ
     * @param graphmodel -ͼԪģʽ���ڵ�����ӣ��ӿ�
     * @throws QMException 
     * @throws QMPropertyVetoException
     */
    public synchronized void setModel(GraphModel graphmodel) throws QMException
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel:setModel(GraphModel) begin...");
        removeAll();//���
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
     * ������һ����ģʽ
     */
    public void setPreviousMode()
    {
        mode = previousMode;
        if(editDialog != null && editDialog instanceof RouteGraphEditJDialog)
            ((RouteGraphEditJDialog)editDialog).setPointerMode(mode);
    }

    /**
     * ��ָ�������������GraphPanel��
     * @param graphlink ָ��������
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
            //������ʼ��
            graphlinkcomponent.setFrom((GraphNodeComponent)nodeHashtable.get(graphlink.getPredecessor()));
            //�����յ�
            graphlinkcomponent.setTo((GraphNodeComponent)nodeHashtable.get(graphlink.getSuccessor()));

            linkHashtable.put(graphlink, graphlinkcomponent);
            //��������Ӽ�����
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
     * ��ָ���Ľڵ���ӵ�GraphPanel��
     * @param graphnode ͼԪ�ڵ�ӿ�
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
     * ɾ���������еļ�¼����GraphPanel��ɾ����ʾ���������
     * @param graphlink ͼԪ���ӽӿ�
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
                System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphLink() û�����ӱ�ɾ�� end.");
            return;
        }else
        {
            linkHashtable.remove(graphlink);//ɾ���������еļ�¼
            graphlink.getRouteItem().setState(RouteItem.DELETE);
            //add by guoxl on 2008.3.20(�ж�deletedLinkVector���Ƿ������ǰ����)
            if(!deletedLinkVector.contains(graphlink))
                //add by guoxl end
                deletedLinkVector.addElement(graphlink);
            remove(graphlinkcomponent);//��Panel��ɾ����ʾ��ͼ���������
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel:removeGraphLink() end.");
            return;
        }

    }

    /**
     * ɾ����ϣ���еĽڵ㡢ͼԪ�ڵ���������ǩ���
     * @param graphnode ͼԪ�ڵ�ӿ�
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
     * ���õ�ǰ������Ľڵ�Ϊָ���Ľڵ�
     * @param graphnode ָ���Ľڵ�
     * @roseuid 402A001D01A6
     */
    public void setGraphNodeInsert(GraphNode graphnode)
    {
        selectedLinkComponent = null;
        insertedNode = graphnode;
    }

    /**
     * ��õ�ǰ������Ľڵ�
     * @return capproute.graph.GraphNode ͼԪ�ڵ�ӿ�
     * @roseuid 402A00340113
     */
    public GraphNode getGraphNodeInsert()
    {
        return insertedNode;
    }

    /**
     * ���õ�ǰ�����������Ϊָ��������
     * @param graphlink ָ��������
     * @roseuid 402A004D02A0
     */
    public void setGraphLinkInsert(GraphLink graphlink)
    {
        selectedLinkComponent = null;
        insertedLink = graphlink;
    }

    /**
     * ��õ�ǰ�����������
     * @return capproute.graph.GraphLink ͼԪ���ӽӿ�
     * @roseuid 402A005E0041
     */
    public GraphLink getGraphLinkInsert()
    {
        return insertedLink;
    }

    /**
     * ��ȡ�����ڵ���Ϊ�������ַ���
     * @return String
     */
    public String getClickNodeAction()
    {
        return clickNodeAction;
    }

    /**
     * ���õ����ڵ���Ϊ
     * @param s String
     */
    public void setClickNodeAction(String s)
    {
        clickNodeAction = s;
    }

    /**
     * �õ�˫���ڵ��¼��Ľ��
     * @return String
     */
    public String getDblClickNodeAction()
    {
        return dblClickNodeAction;
    }

    /**
     * ����˫���ڵ��¼�
     * @param s -˫���¼�����
     */
    public void setDblClickNodeAction(String s)
    {
        dblClickNodeAction = s;
    }

    /**
     * ɾ����ѡ�е����нڵ������
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
     * ���óߴ��С i the new x-coordinate of this component j the new y-coordinate of this component k the new width of this component l the new height of this component
     */
    public void setBounds(int i, int j, int k, int l)
    {
        super.setBounds(i, j, k, l);
        repaint();
    }

    /**
     * �����Ƿ�ɼ��� public void setEnabled(boolean enabled) { if(verbose) System.out.println("cappclients.capproute.graph.GraphPanel:setEnabled() begin..."); boolean oldEnabled = isEnabled();
     * super.setEnabled(enabled); if (!enabled && hasFocus()) { FocusManager.getCurrentManager().focusPreviousComponent(this); } firePropertyChange("enabled", oldEnabled, enabled); if (enabled !=
     * oldEnabled) { repaint(); } if(verbose) System.out.println("cappclients.capproute.graph.GraphPanel:setEnabled() end."); } /** �ж��Ƿ񱻼���
     * @return boolean
     */
    public boolean isEnabled()
    {
        return super.isEnabled();
    }

    /**
     * ����������Ϊ������
     * @param actionlistener ActionListener
     */
    public synchronized void setLinkActionListener(java.awt.event.ActionListener actionlistener)
    {
        this.actionListener = actionlistener;
    }

    /**
     * �ڽ����ϻ���ͼ�νڵ������
     * @param g java.awt.Graphics
     * @roseuid 402A06740377
     */
    public void paint(Graphics g)
    {

        //������������������еķ���
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
                offscreen.flush();//������ɸ�imageʹ�õ����еĶ���
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
        //���ͼ�εı����������еķ���
        super.paint(offgraphics);

        if(mode == 2 && selectedLinkComponent != null)
        {
            selectedLinkComponent.drawLine(offgraphics);//�ó�ˢ��ѡ�е�������ʾ����ʽ��ֱ�߻�������
        }

        for(Enumeration enumeration = linkHashtable.elements();enumeration.hasMoreElements();)
        {
            GraphLinkComponent graphlinkcomponent = (GraphLinkComponent)enumeration.nextElement();
            if(graphlinkcomponent.isSelected())
                selected.addElement(graphlinkcomponent);
            else
                //ˢ������������ʾ����ʽ ֱ��--------����
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
     * ���������ó�ֱ��
     * @param flag boolean �Ƿ�Ϊֱ��
     */
    public void setStraitLines(boolean flag)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: setStraitLines() begin...");
        GraphLinkComponent graphlinkcomponent;
        for(Enumeration enumeration = linkHashtable.elements();enumeration.hasMoreElements();graphlinkcomponent.setStraitLine(flag))
            graphlinkcomponent = (GraphLinkComponent)enumeration.nextElement();

        repaint();//������ʾ
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: setStraitLines() end.");

    }

    /**
     * �ж�ָ���ĵ㲻�����е�
     * @param point ָ���ĵ�
     * @return boolean �ܷ���False
     * @roseuid 402A1F1E0181
     */
    public boolean isObjectExisting(Point point)
    {
        //System.out.println("Not implemented !");
        return false;
    }

    /**
     * �ж�ĳ�ڵ����Ƿ����ָ�������ӣ����ز�����
     * @param graphlinkcomponent -�������ӵ�����
     * @param mouseevent -����¼�
     * @return boolean ������ڣ�����True
     */
    private boolean isLinkAt(GraphLinkComponent graphlinkcomponent, MouseEvent mouseevent)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: isLinkAt() begin...");
        try
        {
            if(!mouseevent.isControlDown() && !mouseevent.isShiftDown())
                //��û�а���ctrl��shift��ʱ�����ѡ��ģ��
                getSelectionModel().clearSelection();
            int i = graphlinkcomponent.getSelectedAt(mouseevent.getX(), mouseevent.getY());
            //�ڵ����ն˽ڵ㷶Χ��
            if(i == 2)
            {
                if(!editMode)
                    return true;
                //���������ʾ����
                getSelectionModel().add(graphlinkcomponent.getGraphLink());
                if(!mouseevent.isPopupTrigger() && mouseevent.getID() != 502)
                {

                    //��ѡ���ӵ��ն˽ڵ�
                    //savedNode = selectedLinkComponent.getTo(); //whj
                    savedNode = graphlinkcomponent.getTo();//lm

                    //����ѡģʽ��ɾ����õ�����
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
            //�ڵ�����ʼ�ڵ㷶Χ��
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
            //�ڵ���������
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
     * ����û���ǰѡ���·�ߵ�λ
     * @return ·�ߵ�λ��bsoID
     * @throws QMException 
     * @throws QMRemoteException
     */
    public String getCurrentRouteDepartment() throws QMException
    {
        if(currentDepartmentBsoID != null && !currentDepartmentBsoID.equals(""))
            return currentDepartmentBsoID;
        else
        {
            //��ѡ����Ч��·�ߵ�λ
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.NO_SELECT_DEPARTMENT, null));
        }
    }

    /**
     * ���õ�ǰ��ѡ���·�ߵ�λ
     * @param departmentBsoID ·�ߵ�λ��bsoID
     * @param departmentName ·�ߵ�λ������(Ҫ���Ǽ��)
     */
    public void setCurrentRouteDepartment(String departmentBsoID, String departmentName)
    {
        currentDepartmentBsoID = departmentBsoID;
        currentDepartmentName = departmentName;
    }

    /**
     * ȡ�ý���
     * @param focusevent java.awt.event.FocusEvent
     * @roseuid 402A1F1E027C
     */
    public void focusGained(FocusEvent focusevent)
    {

    }

    /**
     * ʧȥ����
     * @param focusevent java.awt.event.FocusEvent
     * @roseuid 402A1F1E0362
     */
    public void focusLost(FocusEvent focusevent)
    {

    }

    /**
     * �����ҷ
     * @param mouseevent ����¼�
     * @roseuid 402A1F1F006A
     */
    public void mouseDragged(MouseEvent mouseevent)
    {

    }

    /**
     * ����ƶ�
     * @param mouseevent ����¼�
     * @roseuid 402A1F1F0165
     */
    public void mouseMoved(MouseEvent mouseevent)
    {

    }

    /**
     * ������
     * @param mouseevent ����¼�
     * @roseuid 402A1F1F0255
     */
    public void mouseClicked(MouseEvent mouseevent)
    {

    }

    /**
     * ������
     * @param mouseevent ����¼�
     * @roseuid 402A1F1F034F
     */
    public void mouseEntered(MouseEvent mouseevent)
    {

    }

    /**
     * ����˳�
     * @param mouseevent ����¼�
     * @roseuid 402A1F200062
     */
    public void mouseExited(MouseEvent mouseevent)
    {

    }

    /**
     * ��갴��
     * @param mouseevent ����¼�
     * @roseuid 402A1F200152
     */
    public void mousePressed(MouseEvent mouseevent)
    {
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mousePressed() begin...");
        AbstractGraphView.setFocusedComponent(this);
        boolean flag = false;
        //ָ��ģʽ��
        if(mode == 0)
        {
            //����ѡ��������
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
                getSelectionModel().clearSelection();//�����ѡģʽ
        }
        movingNodes = null;
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mousePressed() end.");
    }

    /**
     * ����ͷ�
     * @param mouseevent ����¼�
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

        case 0: // '\0'ָ��ģʽ
            break;

        case 1: // '\001'�ڵ�ģʽ
            try
            {
                //�½�һ��·�߽ڵ�
                if(this.currentDepartmentName == null || this.currentDepartmentName.equals(""))
                {
                    throw new QMException("�Ƿ���·�ߵ�λ��");
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
                //���½ڵ�������
                this.setGraphNodeInsert(newnode); //lm
                //��ĳλ�ò���ڵ�
                insertedNode.setPosition(i - 16, j - 16);
                //��ȡģʽ��ӽڵ�
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
            //����ڿɱ༭״̬�´���ѡ��������
            if(selectedLinkComponent != null)
            {
                for(Enumeration enumeration = nodeHashtable.elements();enumeration.hasMoreElements();)
                {
                    GraphNodeComponent graphnodecomponent = (GraphNodeComponent)enumeration.nextElement();
                    if(graphnodecomponent.containsGlobal(i, j))
                    {
                        try
                        {
                            //������һ��ģʽ
                            setPreviousMode();
                            if(moveEnd)
                                //����Ϊ���ӵ��ն˽ڵ�
                                selectedLinkComponent.setTo(graphnodecomponent);
                            else
                                //����Ϊ���ӵ���ʼ�ڵ�
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
                            //�����ѡģʽ����Ϣ
                            getSelectionModel().clearSelection();
                            //Ϊ��ѡģʽ���ѡ��������
                            getSelectionModel().add(graphlink);
                        }catch(Throwable e)
                        {
                            JOptionPane.showMessageDialog(getParentFrame(), e.getLocalizedMessage(), QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
                        }
                        //�Ѵ洢�Ľڵ��ÿ�
                        savedNode = null;
                        //��ѡ��������ÿ�
                        selectedLinkComponent = null;
                    }
                }
                //�������Ľڵ㲻Ϊ��
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
            //������ѡ��������
            else
            {
                getSelectionModel().clearSelection();
                //�����ѡģʽ����Ϣ������Ϊָ��ģʽ
                setMode(0);
            }

            break;
        }
        repaint();
        if(verbose)
            System.out.println("cappclients.capproute.graph.GraphPanel: mouseReleased() end.");
    }

    /**
     * ����ͼԪģʽ�¼���õ��Ľڵ���ӵ������
     * @param graphmodelevent ͼԪģ�ͼ����¼�
     * @roseuid 402A1F200329
     */
    public void graphNodeInserted(GraphModelEvent graphmodelevent)
    {
        insertGraphNode(graphmodelevent.getNode());
    }

    /**
     * ɾ��ͼԪģʽ�¼��еõ��Ľڵ㣬��ģʽ����Ϊָ��ģʽ���������
     * @param graphmodelevent ͼԪģ�ͼ����¼�
     * @roseuid 402A1F210031
     */
    public void graphNodeRemoved(GraphModelEvent graphmodelevent)
    {
        removeGraphNode(graphmodelevent.getNode());//ɾ���ڵ�
        setMode(0);//����Ϊָ��ģʽ
        repaint();//����������
    }

    /**
     * �շ���---ͼ�νṹ�ı�
     * @param graphmodelevent ͼԪģ�ͼ����¼�
     * @roseuid 402A1F21012C
     */
    public void graphStructureChanged(GraphModelEvent graphmodelevent)
    {}

    /**
     * ��ͼԪģʽ�¼��õ���������ӵ������
     * @param graphmodelevent ͼԪģ�ͼ����¼�
     * @roseuid 402A1F210230
     */
    public void graphLinkInserted(GraphModelEvent graphmodelevent)
    {
        insertGraphLink(graphmodelevent.getLink());
    }

    /**
     * ɾ�����Ӳ���ģʽ����Ϊָ��ģʽ
     * @param graphmodelevent ͼԪģ�ͼ����¼�
     * @roseuid 402A1F210334
     */
    public void graphLinkRemoved(GraphModelEvent graphmodelevent)
    {
        removeGraphLink(graphmodelevent.getLink());
        setMode(0);
    }

    /**
     * ��ñ�ѡ�еĽڵ����
     * @return GraphNodeComponent ������GraphPanel����ʾ
     */
    public GraphNodeComponent getSelectedNodeComponent()
    {
        return selectedNodeComponent;
    }

    /**
     * ����ѡ��ָ���Ľڵ����
     * @param selectedNodeComponent GraphNodeComponent ������GraphPanel����ʾ
     */
    public void setSelectedNodeComponent(GraphNodeComponent selectedNodeComponent)
    {
        this.selectedNodeComponent = selectedNodeComponent;
    }

    /**
     * �ӵ㱻ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D8016A
     */
    public void graphNodeSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        ((GraphNodeComponent)nodeHashtable.get(graphselectionmodelevent.getNode())).setSelected(true);
    }

    /**
     * �ӵ�û�б�ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D8016C
     */
    public void graphNodeUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        ((GraphNodeComponent)nodeHashtable.get(graphselectionmodelevent.getNode())).setSelected(false);
    }

    /**
     * ���ӱ�ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D8016E
     */
    public void graphLinkSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        selectedLinkComponent = (GraphLinkComponent)linkHashtable.get(graphselectionmodelevent.getLink());
        if(selectedLinkComponent != null)
            selectedLinkComponent.setSelected(true);
    }

    /**
     * ����û�б�ѡ��
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
     * @roseuid 4028D3D80170
     */
    public void graphLinkUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        selectedLinkComponent = null;
        ((GraphLinkComponent)linkHashtable.get(graphselectionmodelevent.getLink())).setSelected(false);

    }

    /**
     * ���ѡ��Ľڵ������
     * @param graphselectionmodelevent - ����GraphSelectionModelEvent�Ķ���
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
     * ͼԪ�����������ڲ��࣬����ͼԪ����Ķ�������ӽڵ������
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
         * Ϊѡ���ģʽ��ӽڵ������
         * @param actionevent ����¼�
         * @roseuid 402A1F9500A6
         */
        public void actionPerformed(ActionEvent actionevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewActionListener.actionPerformed() begin...");
            //�õ�ͼԪ��������¼�ԭ�ļ�
            GraphComponent graphcomponent = (GraphComponent)actionevent.getSource();
            //���ѡ���ģʽ
            getSelectionModel().clearSelection();
            //���graphcomponent�ǽڵ����
            if(graphcomponent instanceof GraphNodeComponent)
                //Ϊѡ���ģʽ���ͼԪ�ڵ�
                getSelectionModel().add(((GraphNodeComponent)graphcomponent).getGraphNode());
            else
            //���graphcomponent���������
            if(graphcomponent instanceof GraphLinkComponent)
                //Ϊģʽ�������
                getSelectionModel().add(((GraphLinkComponent)graphcomponent).getGraphLink());
            //�����Ϊ����
            if(actionListener != null)
                //ʵ����Ϊ
                actionListener.actionPerformed(actionevent);
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewActionListener.actionPerformed() end.");

        }
    }

    /**
     * ͼԪ��ͼ���������ڲ���:���������ק���ӽڵ�ʱ��λ������
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
         * �����ҷ
         * @param mouseevent ����¼�
         * @roseuid 402A1F95018C
         */
        public void mouseDragged(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphViewMouseListener.mouseDragged() begin...");

            if(!editMode)
                return;
            //�ɱ༭ģʽ
            switch(mode)
            {
            case 2: // '\002'
                //ѡ������
                if(selectedLinkComponent != null)
                {
                    //�ƶ������
                    if(moveEnd)
                        //����ѡ���ն˽ڵ������
                        selectedLinkComponent.setEndPoint(mouseevent.getX(), mouseevent.getY());
                    else
                        //����ѡ����ʼ�ڵ������
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
         * ����ƶ�
         * @param mouseevent ����¼�
         * @roseuid 402A1F9501C9
         */
        public void mouseMoved(MouseEvent mouseevent)
        {

        }
    }

    /**
     * ͼԪ��ǩ�������ߣ���������(����ʵ��������϶��¼�������ͷ��¼�)
     */
    class GraphLabelComponentMouseListener extends MouseAdapter implements MouseMotionListener
    {
        /**
         * �����ҷ
         * @param mouseevent ����¼�
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
         * �շ���������ƶ��¼�
         * @param mouseevent MouseEvent ����¼�
         */
        public void mouseMoved(MouseEvent mouseevent)
        {}

        /**
         * ����ͷ��¼�
         * @param mouseevent -����¼�
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
                     * �������б�ǩ�ӵ�
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
     * ͼԪ�ڵ����������ڲ���(����ʵ������갴���¼�������϶��¼�������ͷ��¼�)
     */
    class GraphNodeComponentMouseListener extends MouseAdapter implements MouseMotionListener
    {

        /**
         * @roseuid 402A1F94033A
         */
        public GraphNodeComponentMouseListener()
        {}

        /**
         * ��������ýڵ㣬�����øýڵ�Ϊ��ѡ�У����˫�����򵯳��༭�ڵ����
         * @param e MouseEvent ����¼�
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
         * ��갴��
         * @param mouseevent ����¼�
         */
        public void mousePressed(MouseEvent mouseevent)
        {
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mousePressed() begin...");
            //���ý������
            AbstractGraphView.setFocusedComponent(GraphPanel.this);
            //�õ�ͼԪ�ڵ�����¼���ԭ�ļ�
            node = (GraphNodeComponent)mouseevent.getSource();
            //��ģʽΪָ��ģʽ��ڵ�ģʽʱ
            if(mode == 0 || mode == 1)
            {
                //����ָ��ģʽ
                setMode(0);
                canvasSize = getSize();
                canvasSize.width -= 40;
                canvasSize.height -= 40;
                //�ƶ������һ����λ��
                start.setLocation(mouseevent.getX(), mouseevent.getY());
                if(!mouseevent.isControlDown() && !mouseevent.isShiftDown() && !getSelectionModel().isSelected(node.getGraphNode()))
                    //���ģ���е�ȫ����Ϣ
                    getSelectionModel().clearSelection();
                else
                    //ɾ��ģ���е�ĳһ�ڵ���Ϣ
                    getSelectionModel().remove(node.getGraphNode());
                //�õ�ѡ��ģʽ����ӽڵ�
                getSelectionModel().add(node.getGraphNode());
                //����ѡģʽ��ѡ���Ľڵ�������������
                movingNodes = new Vector(getSelectionModel().getNodesSelectedCount());

                for(Enumeration enumeration = getSelectionModel().allSelectedNodes();enumeration.hasMoreElements();movingNodes.addElement(nodeHashtable.get(enumeration.nextElement())))
                    ;
            }
            if(verbose)
                System.out.println("cappclients.capproute.graph.GraphPanel: GraphNodeComponentMouseListener.mousePressed() end.");

        }

        /**
         * ��ק����¼�
         * @param mouseevent MouseEvent ����¼�
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
            case 1: // '\001'�ڵ�ģʽ
            default:
                break;

            case 0: // '\0'ָ��ģʽ
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

            case 2: // '\002'����ģʽ
                if(selectedLinkComponent == null)
                {
                    moveEnd = true;
                    //����һ���½���������
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
         * �շ���������ƶ�
         * @param mouseevent MouseEvent
         */
        public void mouseMoved(MouseEvent mouseevent)
        {}

        /**
         * �ͷ������Ϊ
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
            case 0: // '\0'ָ��ģʽ
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

            case 2: // '\002'����ģʽ
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
     * ��ָ���Ľڵ���и���
     * @param curNode ָ���Ľڵ�
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
     * ��ý��������е�·�߽ڵ�
     * @return ����Ԫ��ΪRouteItem
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
                //����ڵ�ID���ڲ����������浽���ݿ��еĽڵ�����ӵ�������
                if(nodecom.getRouteItem().getObject().getBsoID() != null && nodecom.getRouteItem().getObject().getBsoID().startsWith("consRouteNode"))
                    nodes.addElement(nodecom.getRouteItem());
            }
        }
        return nodes;
    }

    /**
     * ��ý��������е�·������
     * @return ����Ԫ��ΪRouteItem
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
            //System.out.println("��ɾ�������ӵĸ�����"+deletedLinkVector.size());
            for(Enumeration e = deletedLinkVector.elements();e.hasMoreElements();)
            {
                DefaultGraphLink linkcom = (DefaultGraphLink)e.nextElement();
                RouteItem item = linkcom.getRouteItem();
                if(item.getObject().getBsoID() != null)
                {
                    //add by guoxl on 2008.3.20(����״̬�±༭·�߽ڵ㲢���ջ��������������в鿴���ֱջ�����)
                    // item.setState(RouteItem.DELETE);
                    //add by guoxl end
                    links.addElement(item);
                    //System.out.println("��ɾ�������ӣ�"+item.getObject().getBsoID());
                }
            }
        }
        return links;
    }

    /**
     * ����·�ߴ�֮ǰ��׼������
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
     * ����Ƿ���ڱջ�
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
     * �ֳ�·��
     * @param button ͷ�ڵ�
     */
    public void getPathAll(DefaultGraphNode curNode)
    {
        for(int i = 0;i < curNode.behindNodeVec.size();i++)
        {
            //��ǰͷ�ڵ��ĳһ��̽ڵ�ton
            DefaultGraphNode ton = (DefaultGraphNode)(curNode.behindNodeVec.elementAt(i));
            pathOne.addElement(ton);
            curNode.num++;

            //�����ǰ��̽ڵ�ton��û�к�̽ڵ�
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
     * ����·�ߴ�
     * @throws QMException 
     * @throws QMRemoteException
     */
    public void formRouteBranch() throws QMException
    {
        if(verbose)
            System.out.println("capproute.graph.GraphPanel.formRouteBranch()::::");
        Vector headNodeVec = new Vector();//�ֲ�����-��ʼ�ڵ���������
        DefaultGraphNode graphNode;

        //����·�ߴ�֮ǰ��׼������
        tranlateAllLinks();

        //��˫��ѭ���ж�·�������Ƿ���ڱջ���������򵯳��Ի���
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
                //����·�ߴ�ʧ�ܣ���Ϊ���ڱջ���
                throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.CLOSED_LOOP, null));
            }
        }

        //���ȫ�ֱ���-·������
        pathVector.removeAllElements();
        pathOne.removeAllElements();
        pathTwo.removeAllElements();

        for(Enumeration e = nodeHashtable.keys();e.hasMoreElements();)
        {
            graphNode = (DefaultGraphNode)e.nextElement();
            //�ҵ����е���ʼ�ڵ�
            if(graphNode.frontNodeVec.size() == 0)
            {
                headNodeVec.addElement(graphNode);
            }
        }

        //��ѭ��ͨ������getPathAll�����ҳ�ÿһ��·��
        //����ÿ��·����ӽ�pathVector������
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
