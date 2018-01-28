/**
 * ���ɳ��� RouteGraphEditJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *CR1  ������  2009/12/23 ԭ��TD���⣬��v4r3FunctionTest,TD�ţ�2537
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
 * Title:�༭����·��ͼ������
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */
public class RouteGraphEditJDialog extends JDialog {
    /** ������ */
    public StickyToolbar theStickyToolbar;

    /** ͼ�� */
    public GraphPanel graphPanel;

    private JScrollPane jScrollPane = new JScrollPane();

    /** ·�ߵ�λ�� */
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
     * ����"ָ��"�����Ķ�����ʶ
     */
    public static final String POINTER = "pointer";

    /**
     * ����"����"�����Ķ�����ʶ
     */
    public static final String LINK = "link";

    /** ��ӽڵ�����ı�ʶ */
    public static final String HOME = "home";

    /** ���½ڵ����ԵĶ�����ʶ */
    public static final String UPDATE = "update";

    /** ɾ���ڵ�����ߵĶ�����ʶ */
    public static final String DELETE = "delete";

    /** ��Դ�ļ�·�� */
    public static final String RESOURCE = "com.faw_qm.cappclients.capproute.util.GraphRB";

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ������ */
    private Frame parentFrame;

    /** ��ǰ����·�� */
    private TechnicsRouteIfc myRoute;

    /** ��ǰ·�߱� */
    private TechnicsRouteListIfc myRouteList;

    /** ��ǰ�㲿�� */
    private QMPartMasterIfc myPart;

    /** ���:�Ƿ񱣴����� */
    public boolean isSave = false;

    /** ����:·�߽ڵ�ļ��� */
    public Vector routeNodeItemVector = new Vector();

    /** ����:·�����ӵļ��� */
    public Vector routeLinkItemVector = new Vector();

    /** ����:·�߷�֧�Ķ��ձ�(��ΪTechnicsRouteBranchInfo,ֵΪgraphPanel.pathVector�еĶ�ӦԪ��) */
    public Hashtable branchHashtable = new Hashtable();

    /** ���:��ǰ��ѡ���·�ߵ�λ */
    private String currentDepartment;

    //���������Ļ���
    private QMHelpContext helpContext;

    //����ϵͳ
    private QMHelpSystem helpSystem;
    
	 //CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,�Ƿ����һ����λΪװ��·��
	//���һ����λ�Ƿ�Ϊװ��·��
	JCheckBox jCheckBox1 = new JCheckBox();
	 //CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,���һ����λΪװ��·��

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
     * ���캯��
     *
     * @param frame
     *            ������
     * @param list
     *            Ҫ�༭��·��������·�߱�
     * @param route
     *            Ҫ�༭��·��
     * @param partMaster
     *            Ҫ�༭·�ߵ��㲿��
     * @roseuid 402A22D6014E
     */
    public RouteGraphEditJDialog(JDialog prent,Frame frame, TechnicsRouteListIfc list,
            TechnicsRouteIfc route, QMPartMasterIfc partMaster) { 
        //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2537      	
       // super(frame, "", true);
        super((Dialog)prent, "", true);//CR1
    //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2537
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
     * �����ʼ��
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
        jLabel1.setText("·�ߵ�λ");
        leftJPanel.setLayout(gridBagLayout1);
        buttonJPanel.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��");
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
        cancelJButton.setText("ȡ��");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });

        helpJButton.setMaximumSize(new Dimension(75, 23));
        helpJButton.setMinimumSize(new Dimension(75, 23));
        helpJButton.setPreferredSize(new Dimension(75, 23));
        helpJButton.setToolTipText("Help");
        helpJButton.setText("����");

        helpJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(4, 22));
        statusJLabel.setMinimumSize(new Dimension(4, 22));
        statusJLabel.setPreferredSize(new Dimension(4, 22));
        statusJLabel.setText("�༭·��ͼ");
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("·��ͼ");
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
		 //CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,�Ƿ����һ����λΪװ��·��
       // jCheckBox1.setSelected(true);
		jCheckBox1.setText("���·��Ĭ��Ϊװ��ӵ�");
		 //CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,�Ƿ����һ����λΪװ��·��
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
        //��Ӽ��̼���
        this.addKeyListener(new GraphComponentKeyListener(this));
    }

    /**
     * ���ػ�
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
            okJButton.setText("ȷ��(Y)");
            cancelJButton.setText("ȡ��(C)");
            helpJButton.setText("����(H)");
            helpJButton.setMnemonic('H');
			 //CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ�����һ����λΪװ��·��
			this.getContentPane().add(
					jCheckBox1,
					new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE,
							new Insets(0, 0, 0, 0), 0, 0));
//			CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ�����һ����λΪװ��·��
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * �򱾽������·�ߵ�λ��
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
//			CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ��ʹ��֯������չ��		
		      //ʹ��֯������չ��
		      DefaultMutableTreeNode node = (DefaultMutableTreeNode) departmentTree.
		          getModel().getRoot();
		      CodingClassificationIfc aa = (CodingClassificationIfc) node.getUserObject();
		      departmentTree.addDirectChild(aa, node);
//				CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ��ʹ��֯������չ��
        }
    }

    /**
     * ��ʼ��·�ߵ�λ��
     */
    /**
     * private void initTree() { try { //��ʼ��·�ߵ�λ�� CodingClassificationIfc cc =
     * null;
     * if(this.getRouteList().getRouteListLevel().equals(RouteListLevelType.FIRSTROUTE.getDisplay())) {
     * Class[] c = {String.class,String.class}; Object[] obj = {"��֯����","�������"};
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
     * ѡ��·�ߵ�λ
     *
     * @param e
     *            TreeSelectionEvent javax.swing.event.TreeSelectionEvent
     */
    void departmentTree_valueChanged(TreeSelectionEvent e) {

    }

    /**
     * ����갴��ʱ������ѡ��
     *
     * @param e ����¼�
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
     * ����·�߱�
     *
     * @param list
     *            ��ǰ·��������·�߱�
     */
    private void setRouteList(TechnicsRouteListIfc list) {
        myRouteList = list;
    }

    /**
     * ��õ�ǰ·�߱�
     *
     * @return ��ǰ·��������·�߱�
     */
    public TechnicsRouteListIfc getRouteList() {
        return myRouteList;
    }

    /**
     * ����Ҫ�༭·�ߵ��㲿������Ϣ
     *
     * @param partMaster
     *            �㲿������Ϣ
     */
    private void setPartMaster(QMPartMasterIfc partMaster) {
        myPart = partMaster;
    }

    /**
     * ��õ�ǰ�༭·�ߵ��㲿������Ϣ
     *
     * @return �㲿������Ϣ
     */
    public QMPartMasterIfc getPartMaster() {
        return myPart;
    }

    /**
     * ���õ�ǰ�༭�Ĺ���·�߶���
     *
     * @param route
     *            ����·�߶���
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
     * ��õ�ǰ�༭�Ĺ���·�߶���
     *
     * @return ��ǰ�༭�Ĺ���·�߶���
     */
    public TechnicsRouteIfc getTechnicsRoute() {
        return myRoute;
    }

    /**
     * ��ò�������ʾ·��ͼ������
     * @throws QMRemoteException
     */
    private void setGraphData() throws QMRemoteException {
        //����ж�����ʲô�õ��أ�ΪʲôRouteTaskJPanel.alterStatus == 0
        if (RouteTaskJPanel.getViewMode() == RouteTaskJPanel.UPDATE_MODE
                && RouteTaskJPanel.alterStatus == 0) {
            createAlterGraph();
            return;
        }

        if (myRoute.getBsoID() == null)
            return;
        //���÷���,����·�߶�����·�߽ڵ������
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
        //�������еĽڵ�ͼԪ
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
     * �½�·��ͼԪ �ӹ���·�߹��������ѡ������Ĺ���·����Ϣ��
     * �Ӷ���ʾ�ڹ���·�߱༭�����ϡ�
     */
    private void createAlterGraph() {
        HashMap newMap = RouteTaskJPanel.createNewMap;
        if (newMap != null) {
            Vector nodeVector = (Vector) newMap.get("RouteNode");
            Vector linkVector = (Vector) newMap.get("RouteNodeLink");
            DefaultGraphModel model = new DefaultGraphModel();
            //�������еĽڵ�ͼԪ
            Vector gnv = new Vector();
            if (nodeVector != null && nodeVector.size() > 0) {
                if (verbose) {
                    System.out.println("RouteGraphEditJDialog:��ýڵ�ĸ�����"
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
                                .hashCode();//������hashCode���бȽ���
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
                //�Ѵ�·�߹�����ѡ�ֵ��㲿���Ĺ���·�ߴ���·��ͼ�༭����
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
     * ��ø�����
     *
     * @return Frame ������
     */
    public Frame getParentFrame() {
        return parentFrame;
    }

    /**
     * ���ý������ʾλ��
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
     * ���ظ��෽����ʹ������ʾ����Ļ����
     *
     * @param flag
     */
    public void setVisible(boolean flag) {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * ���嶯���������ڲ���
     */
    class ToolsActionListener implements java.awt.event.ActionListener {
        /**
         * �ڲ���Ĺ�����
         *
         * @roseuid 3DD1AB9A0026
         */
        public ToolsActionListener() {
        }

        /**
         * �����¼��Ĵ�����
         *
         * @param actionevent
         *            �����¼�
         * @roseuid 3DD1AB9A0030
         */
        public void actionPerformed(ActionEvent actionevent) {

            String s = actionevent.getActionCommand();
            if (s.equals(POINTER)) //����༭״̬
            {
                graphPanel.setMode(0);
                return;
            } else if (s.equals(LINK)) //����������
            {
                graphPanel.setMode(2);
                return;
            } else if (s.equals(HOME)) //�����½ڵ�
            {
                graphPanel.setMode(1);
            } else if (s.equals(UPDATE)) //�༭·�߽ڵ�
            {
                GraphNodeComponent node = graphPanel.getSelectedNodeComponent();
                if (node != null) {
                    graphPanel.updateSelectedNode(node);
                    theStickyToolbar.setSelected(POINTER);
                }
            } else if (s.equals(DELETE)) //ɾ���ڵ������
            {
                processDeleteAction();
                theStickyToolbar.setSelected(POINTER);
            }
        }
    }

    /**
     * ɾ����ѡ������нڵ������
     */
    public void processDeleteAction() {
        try {
            //���abstractgraphview�ǲ��ɱ༭��,��ֱ�ӷ���
            if (!graphPanel.isEnabled())
                return;

            DefaultGraphModel definergraphmodel = (DefaultGraphModel) graphPanel
                    .getModel();
            //�洢��·��ͼ�༭���ѡ�е�����
            GraphSelectionModel graphselectionmodel = graphPanel
                    .getSelectionModel();
            //ɾ����ѡ��Ľڵ�
            for (Enumeration enumeration = graphselectionmodel
                    .allSelectedNodes(); enumeration.hasMoreElements();) {
                DefaultGraphNode definergraphnode = (DefaultGraphNode) enumeration
                        .nextElement();
                definergraphmodel.removeNode(definergraphnode);
            }

            DefaultGraphLink definergraphlink;
            //ɾ����ѡ�������
            for (Enumeration enumeration1 = graphselectionmodel
                    .allSelectedLinks(); enumeration1.hasMoreElements();) {
                definergraphlink = (DefaultGraphLink) enumeration1
                        .nextElement();
                definergraphmodel.removeLink(definergraphlink);
            }
            //���ѡ��
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
     * ����·��
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
     * ִ�С�ȷ��������
     *
     * @throws QMRemoteException
     */
    public void processOKCommond() throws QMRemoteException {
        routeNodeItemVector.clear();
        routeLinkItemVector.clear();
        routeNodeItemVector = graphPanel.getAllRouteNodeItems();
        routeLinkItemVector = graphPanel.getAllRouteNodeLinkItems();
        if (verbose) {
            System.out.println("�ڵ�ĸ���:" + routeNodeItemVector.size());
            if (routeLinkItemVector != null)
                System.out.println("���ӵĸ���:" + routeLinkItemVector.size());
        }

        isSave = true;
        //�����µ�·�ߴ�
        graphPanel.formRouteBranch();
        getRouteBranches();
    }

    /**
     * ���ִ���ˡ�ȡ�������������о������ύ���档
     *
     * @param e
     *            java.awt.event.ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this_windowClosing(null);
    }

    /**
     * ���ӵ�Web����ҳ"�༭·��ͼ"
     *
     * @param e java.awt.event.ActionEvent
     */
    void helpJButton_actionPerformed(ActionEvent e) {
        showHelp();
    }

    /**
     * ������ɵ�·�ߴ���
     *
     * @return ·�ߵļ���
     * @throws QMRemoteException
     */
    private void getRouteBranches() throws QMRemoteException {
        Vector pathVector = graphPanel.pathVector;
        if (verbose)
            System.out.println("����·�߷�֧�ĸ�����" + pathVector.size());
        branchHashtable = new Hashtable();
         // System.out.println("pathVector pathVector size  " + pathVector.size() +"pathVector "+ pathVector);
        for (int i = 0; i < pathVector.size(); i++) {
            Vector curPath = (Vector) pathVector.elementAt(i);

            isCorrectAssemDepartment(curPath);
            TechnicsRouteBranchInfo branch = new TechnicsRouteBranchInfo();
            branch.setRouteInfo(myRoute);
            branchHashtable.put(branch, curPath);
            if (verbose)
                System.out.println("·�߷�֧" + i + "�Ľڵ������" + curPath.size());
        }
        if (verbose)
            System.out.println(branchHashtable.size());
    }

    /**
     * ����ָ����·�ߴ���װ�䵥λ��λ���Ƿ���ȷ��
     *
     * @param v
     *            ·�ߴ������нڵ�ļ���
     * @throws QMRemoteException
     */
    private void isCorrectAssemDepartment(Vector v) throws QMRemoteException {
        DefaultGraphNode graphnode;
        RouteNodeInfo node;
        int num = 0;
       // System.out.println(" v ��˭ " + v);
        
//		CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ�����һ����λΪװ��·��
	    //���ѡ��Ĭ�Ͻڵ�Ϊװ��ڵ�
	    if (jCheckBox1.isSelected()) {
	      DefaultGraphNode lastnode = (DefaultGraphNode) v.lastElement();
	      RouteNodeInfo lastNode = (RouteNodeInfo) lastnode.getRouteItem().
	          getObject();
	      if (!lastNode.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.
	                                          getDisplay())) {
	        lastNode.setRouteType(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
	      }
	    }
//	  CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ�����һ����λΪװ��·��
        
//		CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,ȥ��ͬһ�ӹ���λ������ͬһλ���ظ������ж�        
    if(v.size()>1){
          RouteNodeInfo   preNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-2)).getRouteItem().getObject();
           RouteNodeInfo   sucNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-1)).getRouteItem().getObject();
           if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
                   && preNode.getRouteType().equals(sucNode.getRouteType())) {
               //ͬһ�ӹ���λ������ͬһλ���ظ����֣�
               throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                       GraphRB.DEPARTMENT_IS_RECYCLE, null));
           }

        }
//	CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��,Ĭ�����һ����λΪװ��·��
//        RouteNodeInfo   preNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-2)).getRouteItem().getObject();
//        RouteNodeInfo   sucNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-1)).getRouteItem().getObject();
//        if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
//                && preNode.getRouteType().equals(sucNode.getRouteType())) {
//            //ͬһ�ӹ���λ������ͬһλ���ظ����֣�
//            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
//                    GraphRB.DEPARTMENT_IS_RECYCLE, null));
//        }

        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                graphnode = (DefaultGraphNode) v.elementAt(i);
                node = (RouteNodeInfo) graphnode.getRouteItem().getObject();
                //�����ǰ�ڵ���װ�䵥λ�ڵ�
                if (node.getRouteType().equals(
                        RouteCategoryType.ASSEMBLYROUTE.getDisplay())) {
                    num = num + 1;
                    //�����װ��ڵ㲻�Ǹ�·�ߴ������ڵ�
                    if (graphnode.behindNodeVec.size() > 0) {
                        //װ�䵥λ������·�ߴ��е����һ����λ
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
            //һ��·�ߴ���ֻ�ܴ���һ��װ���͵�λ
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    GraphRB.A_BRANCH_CAN_HAVE_ONLYONE_ASSEM, null));
        }

    }
    /**
     * �����������ִ��
     * @param e MouseEvent ����¼�
     */
    void graphPanel_mouseEntered(MouseEvent e) {
        statusJLabel.setText("·��ͼ��������");
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
     * ���ù��߰�ť��ѡ�еİ�ť
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
     * ���Ҫ�Ƿ��˳�������ʾ����ȷʵҪ������ǰ·��ͼ�е�����·����Ϣ�𣿡�
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
        statusJLabel.setText("·�ߵ�λѡ������");
    }

    /**
     * ��ʾ����ҳ
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
     * ��ʼ������
     */
    private void initializeHelp() {
        try {
            //��������ļ����ڷ��������������˿�
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //�������ϵͳ��
            /*
             * ���� helptest �������������û�������Ҫ�������ݣ� url �����ļ����ڷ��������������˿� OnlineHelp
             * ����������� ResourceBundle ������Դ
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
            //�����������ID�����û�����Ĺ���ģʽ���硰Update������Create������View����
            String s = "EditRouteGraph";
            //������������Ļ���
            helpContext = new QMHelpContext(this.getParentFrame(), helpSystem,
                    s);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void this_keyPressed(KeyEvent e) {
        //�������ˡ�Delete����
        if (e.getKeyCode() == 127) {
            processDeleteAction();
            theStickyToolbar.setSelected(RouteGraphEditJDialog.POINTER);
        }
    }
}

/**
 * <p>
 * Title: ΪͼԪ������ڵ�����ӣ�����ļ��̼���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */

class GraphComponentKeyListener extends java.awt.event.KeyAdapter {
    //�༭����·��ͼ������
    private RouteGraphEditJDialog adaptee;
    //���캯��
    public GraphComponentKeyListener(RouteGraphEditJDialog adaptee) {
        this.adaptee = adaptee;
    }
    //�����¼�
    public void keyPressed(KeyEvent e) {
        adaptee.this_keyPressed(e);
    }
}
/**
 * <p>
 * Title: ΪͼԪ������ڵ�����ӣ�����Ĵ��ڼ���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */
class RouteGraphEditJDialog_this_windowAdapter extends
        java.awt.event.WindowAdapter {
    //�༭����·��ͼ������
    private RouteGraphEditJDialog adaptee;
    //���캯��
    RouteGraphEditJDialog_this_windowAdapter(RouteGraphEditJDialog adaptee) {
        this.adaptee = adaptee;
    }
    //���ڹر��¼�
    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }
}
/**
 * <p>
 * Title: ΪͼԪ������ڵ�����ӣ�����ĵ�λ������
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */
class RouteGraphEditJDialog_departmentTree_mouseAdapter extends
        java.awt.event.MouseAdapter {
    //�༭����·��ͼ������
    private RouteGraphEditJDialog adaptee;
    //���캯��
    RouteGraphEditJDialog_departmentTree_mouseAdapter(
            RouteGraphEditJDialog adaptee) {
        this.adaptee = adaptee;
    }
    //������
    public void mouseEntered(MouseEvent e) {
        adaptee.departmentTree_mouseEntered(e);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    //��갴��
    public void mousePressed(MouseEvent e) {
        adaptee.departmentTree_mousePressed(e);
    }
}
