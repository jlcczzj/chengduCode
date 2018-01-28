/**
 * ���ɳ��� RouteGraphEditJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���Ʒ���´���Ƚϣ�������ִ��룬ע�͵����ò��ֻ�����·�߱༭���޷�ֱ��ˢ���㲿�������·�ߡ� liunan 2014-3-19
 * SS2 ����·�߽���ʽ���� ����  2015-4-13
 * SS3 �ɶ������Ĭ�����һ��Ϊװ��·�ߣ�����ȫΪ����·�ߡ� liunan 2016-10-13
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
 * <p> Title:�༭����·��ͼ������ </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class RouteGraphEditJDialog extends JDialog 
{
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
    public static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

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

    private String partMasterID;
    //���·�ߴ����б�
    private ComponentMultiList qMMultiList = new ComponentMultiList();

    private JSplitPane splitPane = new JSplitPane();
    private JPanel bottonJPanel = new JPanel();
    private JButton mainJButton = new JButton();
    /** ����:·�߷�֧ key=·�߷�֧��BsoID, value=·�߷�ֵ֧���� */
    public HashMap branchMap = new HashMap();
    /** ����:·�߷�֧ key=·�߷�֧��BsoID, value=·�߷�ֵ֧���� */
    public HashMap branchMap1 = new HashMap();
    //CCBegin SS2
    private JLabel searchJJLabel = new JLabel();
    private JTextField searchJJText = new JTextField();
    private JPanel searchJPanel = new JPanel();
    private String comp="";
    
    //CCEnd SS2
    /**
     * ���캯��
     * @param frame ������
     * @param list Ҫ�༭��·��������·�߱�
     * @param route Ҫ�༭��·��
     * @param partMaster Ҫ�༭·�ߵ��㲿��
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
                    System.out.println("�ڵ�ĸ���:" + routeNodeItemVector.size());
                    if(routeLinkItemVector != null)
                        System.out.println("���ӵĸ���:" + routeLinkItemVector.size());
                }

                isSave = true;
                //�����µ�·�ߴ�
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
     * �����ʼ��
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
        jLabel1.setText("·�ߵ�λ");
        leftJPanel.setLayout(gridBagLayout1);
        buttonJPanel.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��");
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
        cancelJButton.setText("ȡ��");
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
        helpJButton.setText("����");

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
        mainJButton.setText("������Ҫ·��");
        mainJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainJButtonJButton_actionPerformed(e);
            }
        });
        //CCBegin SS2
        searchJJLabel.setText("������");
        searchJJText.setMaximumSize(new Dimension(100, 22));
        searchJJText.setMinimumSize(new Dimension(100, 22));
        searchJJText.setPreferredSize(new Dimension(100, 22));
        searchJPanel.add(searchJJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
        searchJPanel.add(searchJJText, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
      
        searchJJText.addKeyListener(new KeyAdapter()//���̼�����ť
        {
            public void keyReleased(KeyEvent e)
            {
        		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)departmentTree.getModel().getRoot();
            	
            	if(searchJJText.getText()!=null&&(!searchJJText.getText().equals(""))&&(searchJJText.getText().length()==1)){
            		//�����һ���ַ���չ����
            		departmentTree.addDirectChild((BaseValueIfc)rootNode.getUserObject(),rootNode);
            		departmentTree.setSelectCoding((BaseValueIfc)rootNode.getUserObject());
            		departmentTree.setSelectionPath((TreePath)departmentTree.getPathForRow(0));
            		String inputStr = searchJJText.getText().toUpperCase();
            		//���ݵ�һ���ַ�ƥ�乩Ӧ��
            		for(int i  = 0 ; i<rootNode.getChildCount();i++){
            			DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
            			Object childObject = childTreeNode.getUserObject();
            			TreePath treePath = new TreePath(childTreeNode.getPath()); 
            			if(childObject instanceof CodingInfo){
            				CodingInfo temCoding = (CodingInfo)childObject;
            				
            				if(temCoding.getSearchWord().startsWith(inputStr)){
            					departmentTree.setSelectCoding(temCoding);
            					departmentTree.setSelectionPath(treePath);
            					//������������
            					return;
            				}
            			}
            			
            		}
            		//���ݶ���ַ�ƥ�乩Ӧ��
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
            					//������������
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
//            public void keyPressed(KeyEvent e) { // keyPressed �� keyReleased����ʱ���ģ���  
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
        //��Ӽ��̼���
        this.addKeyListener(new GraphComponentKeyListener(this));
        //��ʼ���б�
        
        qMMultiList.setHeadings(new String[]{"id", "���", "����·��", "װ��·��", "��Ҫ·��", "·�߷�֧"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 1, 0});
        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[]{4}, true);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setCheckboxEditable(true);
    }

    /**
     * ���ػ�
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
            okJButton.setText("ȷ��(Y)");
            cancelJButton.setText("ȡ��(C)");
            helpJButton.setText("����(H)");
            helpJButton.setMnemonic('H');
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * �򱾽������·�ߵ�λ��
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
     * ��ʼ��·�ߵ�λ��
     */
    /**
     * private void initTree() { try { //��ʼ��·�ߵ�λ�� CodingClassificationIfc cc = null; if(this.getRouteList().getRouteListLevel().equals(RouteListLevelType.FIRSTROUTE.getDisplay())) { Class[] c =
     * {String.class,String.class}; Object[] obj = {"��֯����","�������"}; cc = (CodingClassificationIfc)RParentJPanel.useServiceMethod( "CodingManageService","findClassificationByName",c,obj); } else { cc =
     * (CodingClassificationIfc)RParentJPanel.refreshInfo( this.getRouteList().getRouteListDepartment()); } if(cc!=null) { departmentTree = new CodeManageTree(cc);
     * departmentTree.setShowsRootHandles(false); departmentTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() { public void valueChanged(TreeSelectionEvent e) {
     * departmentTree_valueChanged(e); } }); } } catch (QMRemoteException ex) { JOptionPane.showMessageDialog(this,ex.getClientMessage(), QMMessage.getLocalizedMessage(RESOURCE,"information",null),
     * JOptionPane.INFORMATION_MESSAGE); } }
     */

    /**
     * ѡ��·�ߵ�λ
     * @param e TreeSelectionEvent javax.swing.event.TreeSelectionEvent
     */
    void departmentTree_valueChanged(TreeSelectionEvent e)
    {

    }

    /**
     * ����갴��ʱ������ѡ��
     * @param e ����¼�
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
     * ����·�߱�
     * @param list ��ǰ·��������·�߱�
     */
    private void setRouteList(TechnicsRouteListIfc list)
    {
        myRouteList = list;
    }

    /**
     * ��õ�ǰ·�߱�
     * @return ��ǰ·��������·�߱�
     */
    public TechnicsRouteListIfc getRouteList()
    {
        return myRouteList;
    }

    /**
     * ����Ҫ�༭·�ߵ��㲿������Ϣ
     * @param partMaster �㲿������Ϣ
     */
    private void setPartMaster(QMPartMasterIfc partMaster)
    {
        myPart = partMaster;
    }

    /**
     * ��õ�ǰ�༭·�ߵ��㲿������Ϣ
     * @return �㲿������Ϣ
     */
    public QMPartMasterIfc getPartMaster()
    {
        return myPart;
    }

    /**
     * ���õ�ǰ�༭�Ĺ���·�߶���
     * @param route ����·�߶���
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
     * ���õ�ǰ�༭�Ĺ���·�߶���
     * @param route ����·�߶���
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
     * ��ò�������ʾ·��ͼ������
     * @throws QMRemoteException
     */
    private void setGraphData()
    {
        if(myRoute == null || myRoute.getBsoID() == null)
            return;
        try
        {
            //���÷���,����·�߶�����·�߽ڵ������
            Class[] c = {String.class};
            Object[] obj = {myRoute.getBsoID()};
            Object[] map;
            map = (Object[])RequestHelper.request("consTechnicsRouteService", "getRouteNodeAndNodeLink", c, obj);

            Vector nodeVector = (Vector)map[0];
            Vector linkVector = (Vector)map[1];
            DefaultGraphModel model = new DefaultGraphModel();
            //�������еĽڵ�ͼԪ
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
     * ��õ�ǰ�༭�Ĺ���·�߶���
     * @return ��ǰ�༭�Ĺ���·�߶���
     */
    public TechnicsRouteIfc getTechnicsRoute()
    {
        return myRoute;
    }

    /**
     * ��ò�������ʾ·��ͼ������
     * @param wrapData �ͻ��˻�õ����·����Ϣ��װ��
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
                //��ýڵ㼯��
                nodes = (Vector)map.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
                //��ýڵ��������
                links = (Vector)map.get(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
            }
        }

        if(nodes == null || nodes.size() == 0)
        {
            return;
        }
        DefaultGraphModel model = new DefaultGraphModel();
        //�������еĽڵ�ͼԪ
        Vector gnv = new Vector();
        //����ڵ���ڣ����ڵ㻭��ͼ�ν�����
        if(nodes != null && nodes.size() > 0)
        {
            System.out.println("nodeVector = " + nodes.size());
            for(int i = 0;i < nodes.size();i++)
            {
                RouteItem nodeitem = (RouteItem)nodes.elementAt(i);
                RouteNodeInfo node = (RouteNodeInfo)nodeitem.getObject();
                //����������һ��bsoIDֵ��������ʱ��
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
        // ����ڵ�������ڣ����ڵ����
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
     * �½�·��ͼԪ �ӹ���·�߹��������ѡ������Ĺ���·����Ϣ�� �Ӷ���ʾ�ڹ���·�߱༭�����ϡ�
     */
    /*private void createAlterGraph()
    {
        System.out.println("����createAlterGraph������");
        HashMap newMap = RouteTaskJPanel.createNewMap;
        if(newMap != null)
        {
            System.out.println("����createAlterGraph������");
            Vector nodeVector = (Vector)newMap.get("RouteNode");
            Vector linkVector = (Vector)newMap.get("RouteNodeLink");
            DefaultGraphModel model = new DefaultGraphModel();
            //�������еĽڵ�ͼԪ
            Vector gnv = new Vector();
            if(nodeVector != null && nodeVector.size() > 0)
            {
                if(verbose)
                {
                    System.out.println("RouteGraphEditJDialog:��ýڵ�ĸ�����" + nodeVector.size());
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
                        curBsoID = graphNode.getRouteItem().getObject().hashCode();//������hashCode���бȽ���
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
                //�Ѵ�·�߹�����ѡ�ֵ��㲿���Ĺ���·�ߴ���·��ͼ�༭����
                TechnicsRouteInfo route = (TechnicsRouteInfo)((RouteItem)newMap.get("TechnicsRoute")).getObject();
                System.out.println("route=====" + route);
                System.out.println("�뿪createAlterGraph������");
                graphPanel.setTechnicsRoute(route);
                myRoute = route;
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }*/

    /**
     * ��ø�����
     * @return Frame ������
     */
    public Frame getParentFrame()
    {
        return parentFrame;
    }

    /**
     * ���ý������ʾλ��
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
     * ���ظ��෽����ʹ������ʾ����Ļ����
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * ���嶯���������ڲ���
     */
    class ToolsActionListener implements java.awt.event.ActionListener
    {
        /**
         * �ڲ���Ĺ�����
         * @roseuid 3DD1AB9A0026
         */
        public ToolsActionListener()
        {}

        /**
         * �����¼��Ĵ�����
         * @param actionevent �����¼�
         * @roseuid 3DD1AB9A0030
         */
        public void actionPerformed(ActionEvent actionevent)
        {

            String s = actionevent.getActionCommand();
            if(s.equals(POINTER)) //����༭״̬
            {
                graphPanel.setMode(0);
                return;
            }else if(s.equals(LINK)) //����������
            {
                graphPanel.setMode(2);
                return;
            }else if(s.equals(HOME)) //�����½ڵ�
            {
                graphPanel.setMode(1);
            }else if(s.equals(UPDATE)) //�༭·�߽ڵ�
            {
                GraphNodeComponent node = graphPanel.getSelectedNodeComponent();
                if(node != null)
                {
                    graphPanel.updateSelectedNode(node);
                    theStickyToolbar.setSelected(POINTER);
                }
            }else if(s.equals(DELETE)) //ɾ���ڵ������
            {
                processDeleteAction();
                theStickyToolbar.setSelected(POINTER);
            }
        }
    }

    /**
     * ɾ����ѡ������нڵ������
     */
    public void processDeleteAction()
    {
        try
        {
            //���abstractgraphview�ǲ��ɱ༭��,��ֱ�ӷ���
            if(!graphPanel.isEnabled())
                return;

            DefaultGraphModel definergraphmodel = (DefaultGraphModel)graphPanel.getModel();
            //�洢��·��ͼ�༭���ѡ�е�����
            GraphSelectionModel graphselectionmodel = graphPanel.getSelectionModel();
            //ɾ����ѡ��Ľڵ�
            for(Enumeration enumeration = graphselectionmodel.allSelectedNodes();enumeration.hasMoreElements();)
            {
                DefaultGraphNode definergraphnode = (DefaultGraphNode)enumeration.nextElement();
                definergraphmodel.removeNode(definergraphnode);
            }

            DefaultGraphLink definergraphlink;
            //ɾ����ѡ�������
            for(Enumeration enumeration1 = graphselectionmodel.allSelectedLinks();enumeration1.hasMoreElements();)
            {
                definergraphlink = (DefaultGraphLink)enumeration1.nextElement();
                definergraphmodel.removeLink(definergraphlink);
            }
            //���ѡ��
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
     * ����·��
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
     * ִ�С�ȷ��������
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
                System.out.println("�ڵ�ĸ���:" + routeNodeItemVector.size());
                if(routeLinkItemVector != null)
                    System.out.println("���ӵĸ���:" + routeLinkItemVector.size());
            }

            isSave = true;
            //�����µ�·�ߴ�
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
     * ���ִ���ˡ�ȡ�������������о������ύ���档
     * @param e java.awt.event.ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this_windowClosing(null);
    }

    /**
     * ���ӵ�Web����ҳ"�༭·��ͼ"
     * @param e java.awt.event.ActionEvent
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {
        showHelp();
    }

    /**
     * ������Ҫ·��
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
                System.out.println("�ڵ�ĸ���:" + routeNodeItemVector.size());
                if(routeLinkItemVector != null)
                    System.out.println("���ӵĸ���:" + routeLinkItemVector.size());
            }

            isSave = true;
            //�����µ�·�ߴ�
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
     * ���·�߷�֧,����ʾ��·�ߴ��б���
     */
    private void branchToMultiList(Hashtable hashtable)
    {
        if(verbose)
        {
            System.out.println("���·�߷�֧cappclients.capproute.view.RouteTaskJPanel.branchToMultiList() begin...");

        }
        qMMultiList.clear();
        branchMap.clear();
        if(hashtable == null || hashtable.size() == 0)
        {
            return;
        }
        //���·�߷�֧,����ʾ��·�ߴ��б���
        Object[] keys = hashtable.keySet().toArray();
        TechnicsRouteBranchInfo branchinfo;
        for(int i = 0;i < hashtable.size();i++)
        {
            branchinfo = (TechnicsRouteBranchInfo)keys[i];
            System.out.println("branchinfo=====" + branchinfo.getRouteStr());
            //�б��������飺"BsoID","���","����·��","װ��·��","��Ҫ·��"
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
                System.out.println("�ύǰ ��֧" + i + "�Ľڵ������" + nodeVector.size());
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
            //                makeStr = "��";
            //            }
            //            if(assemStr.equals(""))
            //            {
            //                assemStr = "��";
            //            }
            qMMultiList.addTextCell(i, 2, makeStr);
            qMMultiList.addTextCell(i, 3, assemStr);
            //������һ��zz start  �ı� ��·�ߴ��ַ�������branch���󣬷����ݿͻ���ѯ.@ǰ������·�ߣ�֮����װ��·��
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

    /** ����:key=��֧BsoID; value=��ֵ֧���� */
    private HashMap tempMap = new HashMap();

    /**
     * ������ɵ�·�ߴ���
     * @return ·�ߵļ���
     * @throws QMException 
     * @throws QMRemoteException
     */
    private Hashtable getRouteBranches() throws QMException
    {
        Vector pathVector = graphPanel.pathVector;
        //if(verbose)
        System.out.println("����·�߷�֧�ĸ�����" + pathVector.size());
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
                System.out.println("·�߷�֧�ڵ�====" + info.getX());
            }
            isCorrectAssemDepartment(curPath);
            TechnicsRouteBranchInfo branch = new TechnicsRouteBranchInfo();
            branch.setRouteInfo(myRoute);
            branchHashtable.put(branch, curPath);
            //if (verbose)
            System.out.println("·�߷�֧getRouteBranches" + i + "�Ľڵ������" + curPath.size());
        }
        //if (verbose)
        System.out.println(branchHashtable.size());
        return branchHashtable;
    }

    /**
     * ����ָ����·�ߴ���װ�䵥λ��λ���Ƿ���ȷ��
     * @param v ·�ߴ������нڵ�ļ���
     * @throws QMException 
     * @throws QMRemoteException
     */
    private void isCorrectAssemDepartment(Vector v) throws QMException
    {
        DefaultGraphNode graphnode;
        RouteNodeInfo node;
        int num = 0;
        // System.out.println(" v ��˭ " + v);
        
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
                //ͬһ�ӹ���λ������ͬһλ���ظ����֣�
                throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.DEPARTMENT_IS_RECYCLE, null));
            }

        }

        //        RouteNodeInfo   preNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-2)).getRouteItem().getObject();
        //        RouteNodeInfo   sucNode = (RouteNodeInfo)((DefaultGraphNode) v.elementAt(v.size()-1)).getRouteItem().getObject();
        //        if (preNode.getNodeDepartment().equals(sucNode.getNodeDepartment())
        //                && preNode.getRouteType().equals(sucNode.getRouteType())) {
        //            //ͬһ�ӹ���λ������ͬһλ���ظ����֣�
        //            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
        //                    GraphRB.DEPARTMENT_IS_RECYCLE, null));
        //        }

        if(v != null && v.size() > 0)
        {
            for(int i = 0;i < v.size();i++)
            {
                graphnode = (DefaultGraphNode)v.elementAt(i);
                node = (RouteNodeInfo)graphnode.getRouteItem().getObject();
                //�����ǰ�ڵ���װ�䵥λ�ڵ�
                if(node.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.getDisplay()))
                {
                    num = num + 1;
                    //�����װ��ڵ㲻�Ǹ�·�ߴ������ڵ�
                    if(graphnode.behindNodeVec.size() > 0)
                    {
                        //װ�䵥λ������·�ߴ��е����һ����λ
                        throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.ASSEM_DEPARTMENT_MUST_BE_LASTED, null));
                    }
                }
            }
        }
        if(num > 1)
        {
            //һ��·�ߴ���ֻ�ܴ���һ��װ���͵�λ
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, GraphRB.A_BRANCH_CAN_HAVE_ONLYONE_ASSEM, null));
        }
    }

    /**
     * �����������ִ��
     * @param e MouseEvent ����¼�
     */
    void graphPanel_mouseEntered(MouseEvent e)
    {
        statusJLabel.setText("·��ͼ��������");
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
     * ���ù��߰�ť��ѡ�еİ�ť
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
     * ���Ҫ�Ƿ��˳�������ʾ����ȷʵҪ������ǰ·��ͼ�е�����·����Ϣ�𣿡�
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
        statusJLabel.setText("·�ߵ�λѡ������");
    }

    /**
     * ��ʾ����ҳ
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
     * ��ʼ������
     */
    private void initializeHelp()
    {
        try
        {
            //��������ļ����ڷ��������������˿�
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //�������ϵͳ��
            /*
             * ���� helptest �������������û�������Ҫ�������ݣ� url �����ļ����ڷ��������������˿� OnlineHelp ����������� ResourceBundle ������Դ
             */
            helpSystem = new QMHelpSystem("capproute", url, "OnlineHelp", ResourceBundle.getBundle("com.faw_qm.cappclients.conscapproute.util.RouteHelpRB", RemoteProperty.getVersionLocale()));
            //System.out.println(helpSystem.getResources());
            //�����������ID�����û�����Ĺ���ģʽ���硰Update������Create������View����
            String s = "EditRouteGraph";
            //������������Ļ���
            helpContext = new QMHelpContext(this.getParentFrame(), helpSystem, s);
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    void this_keyPressed(KeyEvent e)
    {
        //�������ˡ�Delete����
        if(e.getKeyCode() == 127)
        {
            processDeleteAction();
            theStickyToolbar.setSelected(RouteGraphEditJDialog.POINTER);
        }
    }
}

/**
 * <p> Title: ΪͼԪ������ڵ�����ӣ�����ļ��̼��� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

class GraphComponentKeyListener extends java.awt.event.KeyAdapter
{
    //�༭����·��ͼ������
    private RouteGraphEditJDialog adaptee;

    //���캯��
    public GraphComponentKeyListener(RouteGraphEditJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //�����¼�
    public void keyPressed(KeyEvent e)
    {
        adaptee.this_keyPressed(e);
    }
}

/**
 * <p> Title: ΪͼԪ������ڵ�����ӣ�����Ĵ��ڼ��� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
class RouteGraphEditJDialog_this_windowAdapter extends java.awt.event.WindowAdapter
{
    //�༭����·��ͼ������
    private RouteGraphEditJDialog adaptee;

    //���캯��
    RouteGraphEditJDialog_this_windowAdapter(RouteGraphEditJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //���ڹر��¼�
    public void windowClosing(WindowEvent e)
    {
        adaptee.this_windowClosing(e);
    }
}

/**
 * <p> Title: ΪͼԪ������ڵ�����ӣ�����ĵ�λ������ </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
class RouteGraphEditJDialog_departmentTree_mouseAdapter extends java.awt.event.MouseAdapter
{
    //�༭����·��ͼ������
    private RouteGraphEditJDialog adaptee;

    //���캯��
    RouteGraphEditJDialog_departmentTree_mouseAdapter(RouteGraphEditJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    //������
    public void mouseEntered(MouseEvent e)
    {
        adaptee.departmentTree_mouseEntered(e);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    //��갴��
    public void mousePressed(MouseEvent e)
    {
        adaptee.departmentTree_mousePressed(e);
    }
}
