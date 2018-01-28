/**
 * ���ɳ��� RouteTaskJPanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1  ������  200/5/25  ԭ��:��֯��λ�����������������ɾ����ͬ����
 *                        ����:�ڵ�"·��ͼ"��ťʱ���¹�����֯��λ����
 * 
 * CR2  ������  2009/12/23 ԭ��TD���⣬��v4r3FunctionTest,TD�ţ�2537
 * SS1 ����·�ߵĸ��ı�������������� zhangmingyuan 2011-11-01
 * SS2 ��Ҫ·�߸�ѡ��ֻ��ѡ��һ�� liuyang 2014-7-15
 * SS3 ����ǻص��Ĵ�����·�ߣ�����ʱû���Զ���ȡ��Դ�汾�� liunan 2014-12-15
 * SS4 ��������·�߱����Ч��˵������ѡ�����Ƹ���Ϊ������·�ߺϲ�����ͬʱ������׼֪ͨ�������ж��Ƿ�ɱ༭�� ������  20151015
 * SS5 A004-2015-3224 �ǿ�����·�ߣ����������������Ҫ·�ߣ�Ҫ������ʾ�� liunan 2015-12-3
 * SS6 �ص�·�ߣ����û��Դ�汾����ȡ����汾�� liunan 2016-3-22
 * SS7 A004-2016-3433 ����·�����ѹ��ܡ����ҽ�SS5������ֲ������ liunan 2016-10-27
 * SS8 A004-2016-3450 ·�߱༭ʱ��Ҫ·��Ĭ�Ͽ�����·�� liunan 2016-12-20
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
 * Title:ά��·��
 * Description:
 * Copyright: Copyright (c) 2004
 * Company: һ������
 * @author ����
 * @version 1.0
* ������һ��zz start  �ı� ��·�ߴ��ַ�������branch���󣬷����ݿͻ���ѯ.@ǰ������·�ߣ�֮����װ��·��
* ���������zz 20060925 �����°������
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

    /** ��ǰ·�߱���㲿������ */
    private ListRoutePartLinkIfc myPartLink;

    /** ��ǰ·��������·�߱� */
    private TechnicsRouteListIfc myRouteList;

    /** ҵ�����:��ǰ����·�� */
    private TechnicsRouteIfc myRoute;

    /** ������ʾģʽ������ģʽ����� */
    public final static int UPDATE_MODE = 0;

    /** ������ʾģʽ������ģʽ����� */
    public final static int CREATE_MODE = 1;

    /** ������ʾģʽ���鿴ģʽ����� */
    public final static int VIEW_MODE = 2;

    /** ����ģʽ--�鿴 */
    public static int mode = VIEW_MODE;

    /** ��ǰ�༭·�ߵ��㲿�� */
    private QMPartMasterIfc myPart;

    //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
    private QMPartIfc myPartInfo;
    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** ����:·�߷�֧ key=·�߷�֧��BsoID, value=·�߷�ֵ֧���� */
    private HashMap branchMap = new HashMap();

    /** �༭·��ͼ������� */
    private RouteGraphEditJDialog editDialog;

    /** �鿴·��ͼ������� */
    private RouteGraphViewJDialog viewDialog;

    /** ����Ƿ�ִ���˱������ */
    protected boolean isSave = false;

    /**
     * ����:�����ʼ��ʱ���㲿��������õ�·�߷�֧��Ϣ key=����·�߷�ֵ֦����,
     * value=·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
     */
    private Map originalBranchesMap;

    /** ��ǣ�����״ֵ̬Ϊ0ʱ����·���踴���½���Ϊ1ʱ����Ϊ����·�� */
    public static int alterStatus = 0;

    /** ����:·�߷�֧ key=·�߷�ֵ֧����, value= ���ϣ��÷�֧�е�����·�߽ڵ㣩 */
    private Hashtable branchHashtable = new Hashtable();

    /** ����:·�߷�֧��ڵ�Ĺ����ļ���(Ԫ��ΪRouteBranchNodeLinkInfo) */
    public Vector branchNodeLinkVector = new Vector();

    /** ������ */
    private JFrame parentJFrame;

    /** ���Ի��� */
    private JDialog parentDialog;

    /** ·�ߵ�λ�����������Ĳ��֣� */
    private CodeManageTree departmentTree;
    private final static int OK = 1;
     //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
     JLabel changeLabel = new JLabel();
     JComboBox changeBombox = new JComboBox();
     JCheckBox isAdoptRLCheckBox = new JCheckBox();
     JLabel validLabel = new JLabel();
     JScrollPane jScrollPane2 = new JScrollPane();
     JTextArea validArea = new JTextArea();
     JLabel changLabel = new JLabel();
     JLabel jLabel2 = new JLabel();
     private Hashtable changeHash;
     //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·�� 
     
     //CCBegin SS1     
     private JButton applyAllButton = new JButton();
     //CCEnd SS1

    /**
     * ���캯��
     *
     * @param frame
     *            ������
     * @param dialog
     *            ���Ի���
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
     * �����ʼ��
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        //CCBegin by leixiao 2010-1-27 ��·�߱��Ϊ��׼/�ձ�
        jLabel1.setText("��׼/�ձϱ��");
      //CCEnd by leixiao 2010-1-27 ��·�߱��Ϊ��׼/�ձ�
        this.setLayout(gridBagLayout3);
        listNumberJLabel.setText("number1");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("�㲿�����");
        partNumberJLabel.setText("number2");
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText("˵��");
        editJButton.setMaximumSize(new Dimension(100, 23));
        editJButton.setMinimumSize(new Dimension(100, 23));
        editJButton.setPreferredSize(new Dimension(100, 23));
        editJButton.setToolTipText("����·��ͼ");
        editJButton.setSelected(true);
        editJButton.setText("·��ͼ...");
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
        jLabel6.setText("·�ߴ�");
        jPanel2.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
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
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        changeLabel.setMaximumSize(new Dimension(55, 16));
        changeLabel.setMinimumSize(new Dimension(55, 16));
        changeLabel.setPreferredSize(new Dimension(55, 16));
        changeLabel.setText("���ı��");
        //CCBegin SS4
        isAdoptRLCheckBox.setText("����·�ߺϲ�");
        //CCEnd SS4
        isAdoptRLCheckBox.addItemListener(new RouteTaskJPanel_isAdoptRLCheckBox_itemAdapter(this));
        validLabel.setText("��Ч��˵��");
        validArea.setText("");
        changLabel.setText("���ı��");
        
        //CCBegin SS1
        applyAllButton.setText("Ӧ��ȫ��"); 
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
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
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
//      CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
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
//      CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

        qMMultiList.setHeadings(new String[] { "id", "���", "����·��", "װ��·��",
                "��Ҫ·��" });
        qMMultiList.setRelColWidth(new int[] { 0, 1, 4, 2, 2 });
        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 4 }, true);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setCheckboxEditable(true);
//      CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��   
        initChangeJComboBox();
//      CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
    }
    
    //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
    /**
     * ��ʼ������·�߱�״̬��ѡ���ֵ
     * st skybird 2005.2.23
     */
    private void initChangeJComboBox()
    {
        Class[] params={String.class,String.class};
        Object[] values={"���ı��",
            "����·��"};
        Collection result = null;
        try
        {
            result = (Collection)RouteListTaskJPanel.invokeRemoteMethodWithException(this,
                "CodingManageService","getCoding",params,values);
        }
        catch(QMRemoteException ex)
        {
            //����쳣��Ϣ��
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
    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
    

    /**
     * ���õ�ǰ·��������·�߱�
     *
     * @param routelist
     *            ·�߱����
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
        myRouteList = routelist;
        initTree();//zz �����޸� ��ע�͵����Ƶ����캯������������������
    }

    /**
     * ��õ�ǰ·������·�߱�
     *
     * @return ·�߱�
     */
    public TechnicsRouteListIfc getTechnicsRouteList() {
        return myRouteList;
    }

    /**
     * ���õ�ǰ�༭���㲿������ �����·�ߵ���ʾ���ұ߽�����
     *
     * @param link
     *            ·�߱���㲿����������
     * @throws QMRemoteException
     */
    public void setListPartLink(ListRoutePartLinkIfc link)
            throws QMRemoteException {
        alterStatus = link.getAlterStatus();
        //skybird��
        isChangeRoute = true;
        myPartLink = link;
        setPart(link.getPartMasterInfo());
//      CCBeginby leixiao 2009-2-21 ԭ�򣺽����������·��,·����Ӿ���汾
        setPartInfo(link.getPartBranchInfo());
//      CCEndby leixiao 2009-2-21 ԭ�򣺽����������·��,·����Ӿ���汾
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
     * ��õ�ǰ�༭���㲿������
     *
     * @return ·�߱���㲿����������
     */
    public ListRoutePartLinkIfc getListPartLink() {
        return myPartLink;
    }

    /**
     * ���õ�ǰҵ�����
     *
     * @param route
     *            ����·�߶���
     */
    private void setTechnicsRoute(TechnicsRouteIfc route) {
        myRoute = route;
    }

    /**
     * ��õ�ǰҵ�����
     *
     * @return ��ǰ����·��
     */
    public TechnicsRouteIfc getTechnicsRoute() {
        return myRoute;
    }

    /**
     * ���õ�ǰҪ�༭·�ߵ��㲿��
     *
     * @param part
     *            �㲿��
     */
    private void setPart(QMPartMasterIfc part) {
        myPart = part;
    }

    /**
     * ��õ�ǰҪ�༭·�ߵ��㲿��
     *
     * @return �㲿��
     */
    public QMPartMasterIfc getPart() {
        return myPart;
    }

//  CCBeginby leixiao 2009-2-21 ԭ�򣺽����������·��,·����Ӿ���汾
    private void setPartInfo(QMPartIfc part) {
        myPartInfo = part;
    }

//  CCEndby leixiao 2009-2-21 ԭ�򣺽����������·��,·����Ӿ���汾

    /**
     * �����ʼ��ʱ���㲿��������õ�·�߷�֧��Ϣ
     *
     * @param map
     *            HashMap
     */
    private void setRouteBranches(Map map) {
        originalBranchesMap = map;
    }

    /**
     * ��ý����ʼ��ʱ���㲿��������õ�·�߷�֧��Ϣ
     *
     * @return HashMap
     */
    public Map getRouteBranches() {
        return originalBranchesMap;
    }

    /**
     * ���ý���ģʽ�����������»�鿴����
     *
     * @param aMode
     *            �½���ģʽ
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

        case CREATE_MODE: { //����ģʽ
            this.setCreateModel();
            break;
        }

        case UPDATE_MODE: { //����ģʽ
            this.setUpdateModel();
            break;
        }

        case VIEW_MODE: { //�鿴ģʽ
            this.setViewDisplayModel();
            break;
        }
        }
    }

    /**
     * ��õ�ǰ����ģʽ
     *
     * @return ��ǰ����ģʽ
     */
    public static int getViewMode() {
        return mode;
    }
    
    /**
     *         //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
     * ���ݸ�����QMPartMasterIfc�������С�汾QMPartIfc(��δ����Ȩ��)
     * @param partMasterIfc QMPartMasterIfc �㲿��������.
     * @return QMPartIfc ����С�汾���㲿��ֵ����.
     */
    protected String getibaPartVersion(QMPartMasterIfc partMasterIfc)
    {
        Class[] paraClass ={QMPartMasterIfc.class};
        Object[] objs ={partMasterIfc};
        String result = null;
        try
        {
//        	CCBegin by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
            result = (String) useServiceMethod(
                    "TechnicsRouteService", "getibaPartVersion", paraClass, objs);
           // System.out.println("-----------result="+result);
//          CCEnd by leixiao 2009-1-8ԭ�򣺽����������·��          
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
//        	CCBegin by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
            result = (String) useServiceMethod(
                    "TechnicsRouteService", "getibaPartVersion", paraClass, objs);
           // System.out.println("-----------result="+result);
//          CCEnd by leixiao 2009-1-8ԭ�򣺽����������·��          
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            return null;
        }

        return result;
    }
    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

    /**
     * ���ý���Ϊ�½�ģʽ
     */
    private void setCreateModel() {
        editDialog = null;
        this.isSave = false;
        listNumberJLabel.setText(myRouteList.getRouteListNumber());
        partNumberJLabel.setText(myPart.getPartNumber());
        descriJTextArea.setText("");
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        //modify for cq by liunan 2007.08.10
        //��˵������� ��汾 ��Ϣ���������ɱ���ʱ�ڱ���кϲ���ʾ��
        
      //  descriJTextArea.setText("("+(String)(getQMPartIfc(myPart).getVersionID()+")"));
//    	CCBegin by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
        if(myPartInfo!=null){
        	//System.out.println("---myPartInfo="+myPartInfo);
        	descriJTextArea.setText("("+getibaPartVersion(myPartInfo)+")");
        }
        else
        descriJTextArea.setText("("+getibaPartVersion(myPart)+")");
//    	CCEnd by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾      
        //end modifying
        this.validArea.setText("");
        if(this.myRouteList.getRouteListState().equals("��׼"))
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
        
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
        branchMap.clear();
        qMMultiList.clear();
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        descriJTextArea.setEditable(true);
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        this.changeBombox.setEnabled(true);
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

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
        //�½�·�߶���
        TechnicsRouteInfo newRoute = new TechnicsRouteInfo();
        this.setTechnicsRoute(newRoute);
        if (!this.isShowing()) {
            this.setVisible(true);
        }
        repaint();
    }

    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setUpdateModel() {
        editDialog = null;
        this.isSave = false;
        listNumberJLabel.setText(myRouteList.getRouteListNumber());
        partNumberJLabel.setText(myPart.getPartNumber());
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        //descriJTextArea.setText(myRoute.getRouteDescription());
        //modify for cq by liunan 2007.08.10
        //��˵������� ��汾 ��Ϣ���������ɱ���ʱ�ڱ���кϲ���ʾ��
        String describ= myRoute.getRouteDescription();
       // String preString="("+(String)(getQMPartIfc(myPart).getVersionValue()).substring(0,1)+")";
       // String preString="("+(String)(getQMPartIfc(myPart).getVersionID())+")";
//    	CCBegin by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
        String preString="";
        if(myPartInfo!=null){
        	//System.out.println("---------myPartInfo="+myPartInfo);
        	preString ="("+getibaPartVersion(myPartInfo)+")";	
        }
        else
        preString ="("+getibaPartVersion(myPart)+")";
//    	CCEnd by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
        if(describ!=null){
            if(describ.startsWith("(")&&describ.indexOf(")")!=-1){
            	describ=preString+describ.substring(describ.indexOf(")")+1,describ.length()); //anan
            }
            //CCBegin SS3
            if(describ.equals("BOM�ص�·��"))
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
        if(this.myRouteList.getRouteListState().equals("��׼"))
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
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
        branchToMultiList();
        okJButton.setVisible(true);
        cancelJButton.setVisible(true);
        descriJTextArea.setEditable(true);
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        this.changeBombox.setEnabled(true);
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

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
     * ����ȥ���־û���Ϣ�����ҵ�����
     */
    public static HashMap createNewMap = null;

    /**
     * ���ý���Ϊ�鿴ģʽ
     */
    private void setViewDisplayModel() {
        listNumberJLabel.setText(myRouteList.getRouteListNumber());
        partNumberJLabel.setText(myPart.getPartNumber());
        descriJTextArea.setText(myRoute.getRouteDescription());
        descriJTextArea.setEditable(false);
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
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
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

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
     * ���·�߷�֧,����ʾ��·�ߴ��б���
     */
    private void branchToMultiList() {
        if (verbose) {
            System.out
                    .println("���·�߷�֧cappclients.capproute.view.RouteTaskJPanel.branchToMultiList() begin...");

        }
        qMMultiList.clear();
        tempMap.clear();
        if (this.getRouteBranches() == null
                || this.getRouteBranches().size() == 0) {
            return;
        }

        //���·�߷�֧,����֧�������������ʾ��·�ߴ��б���
        Object[] branchs = RouteHelper.sortedInfos(
                this.getRouteBranches().keySet()).toArray();
        if (verbose) {
            System.out
                    .println(">>>>>>>>>>>>>>>>>  ��� ��֧ �ĸ�����" + branchs.length);
        }
        for (int i = 0; i < branchs.length; i++) {
            TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) branchs[i];
            if (verbose) {
               // System.out.println("��֧��" + branchinfo.getBsoID());
                //�б��������飺"BsoID","���","����·��","װ��·��","��Ҫ·��"
            }
            qMMultiList.addTextCell(i, 0, branchinfo.getBsoID());
            qMMultiList.addTextCell(i, 1, String.valueOf(i + 1));
            //���б��е�4�����ѡ���
           qMMultiList.addCheckboxCell(i, 4, branchinfo.getMainRoute());         
            tempMap.put(branchinfo.getBsoID(), branchinfo);
            String makeStr = "";
            String assemStr = "";
            Object[] nodes = (Object[]) getRouteBranches().get(branchinfo);
            Vector makeNodes = (Vector) nodes[0];
            RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

            if (makeNodes != null && makeNodes.size() > 0) {
                if (verbose) {
                    System.out.println(">>>>>>>>>>>>>>>>>  ��� ��֧"
                            + branchinfo.getBsoID() + "������ڵ� ������"
                            + makeNodes.size());
                }
                for (int m = 0; m < makeNodes.size(); m++) {
                    RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
                    if (makeStr == "") {
                        makeStr = makeStr + node.getNodeDepartmentName();
                    } else {
                        makeStr = makeStr + "��" + node.getNodeDepartmentName();
                    }
                }
            }

            if (asseNode != null) {
                assemStr = asseNode.getNodeDepartmentName();
            }

            if (makeStr.equals("")) {
                makeStr = "��";
            }
            if (assemStr.equals("")) {
                assemStr = "��";
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
    	int row = arg0.getFirstRow();//��
		int col = arg0.getColumn();//��	
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
    /** ����:key=��֧BsoID; value=��ֵ֧���� */
    private HashMap tempMap = new HashMap();

    /**
     * �ڸ���ģʽʱ,���ֻ���ķ�֧��"�Ƿ���Ҫ·��"����,������·��ͼ,�򱣴�ʱ,����� �������ύ·�߷�֧.
     *
     * @return ����·�߷�֧�ļ���
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
     * ִ�б༭·��ͼ����
     *
     * @param e
     *            ActionEvent
     */
    void editJButton_actionPerformed(ActionEvent e) {
        processEditRouteGraph();
    }

    /**
     * ��ǣ��Ƿ���ʾΪ��һ·�ߵ���Ϣ
     */
    private boolean isChangeRoute = true;

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    /**
     * �༭��鿴·��ͼ
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
                 //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2537               
                /*Begin CR2
                editDialog = new RouteGraphEditJDialog(this.getParentJFrame(),
                        this.getTechnicsRouteList(), this.getTechnicsRoute(),
                        this.getPart());
                */
                 editDialog = new RouteGraphEditJDialog((CappRouteManageJDialog) parentDialog,this.getParentJFrame(),
                        this.getTechnicsRouteList(), this.getTechnicsRoute(),
                        this.getPart());
                //End CR2
                 //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2537
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
     * �����µ�·�ߴ�����ӵ��б���
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
        //���·�߷�֧,����ʾ��·�ߴ��б���
        Object[] keys = pathHashtable.keySet().toArray();
        TechnicsRouteBranchInfo branchinfo;
        //CCBegin SS8
        boolean lastFlag = true;
        //CCEnd SS8

        for (int i = 0; i < pathHashtable.size(); i++) {
            branchinfo = (TechnicsRouteBranchInfo) keys[i];
            //�б��������飺"BsoID","���","����·��","װ��·��","��Ҫ·��"
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
                System.out.println("�ύǰ ��֧" + i + "�Ľڵ������" + nodeVector.size());
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
                        makeStr = makeStr + "��" + graphnode.getDepartmentName();
                    }
                } else if (node.getRouteType().equals(
                        RouteCategoryType.ASSEMBLYROUTE.getDisplay())) {
                    if (assemStr == "") {
                        assemStr = assemStr + graphnode.getDepartmentName();
                    } else {
                        assemStr = assemStr + "��"
                                + graphnode.getDepartmentName();
                    }
                }
            }
            if (makeStr.equals("")) {
                makeStr = "��";
            }
            if (assemStr.equals("")) {
                assemStr = "��";
            }
            qMMultiList.addTextCell(i, 2, makeStr);
            qMMultiList.addTextCell(i, 3, assemStr);
            
            //CCBegin SS8
            //�������������·�ߣ���Ϊ��Ҫ·��
            if(checkMainRoute(makeStr+assemStr))
            {
            	qMMultiList.addCheckboxCell(i, 4, true);
            	lastFlag = false;
            }
            //�����������������·�ߣ������һ��Ϊ��Ҫ·��
            else if(lastFlag&&(pathHashtable.size()-1)==i)
            {
            	qMMultiList.addCheckboxCell(i, 4, true);
            }
            //������Ϊ����Ҫ·��
            else
            {
            	qMMultiList.addCheckboxCell(i, 4, false);
            }
            //CCEnd SS8
            
            //������һ��zz start  �ı� ��·�ߴ��ַ�������branch���󣬷����ݿͻ���ѯ.@ǰ������·�ߣ�֮����װ��·��
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
     * ������Ϊ����ģʽʱ"Ӧ��ȫ��"��ť�Ķ����¼�
     * @param e
     * @throws QMRemoteException
     */ 
	public void applyAllButton_actionPerformed(ActionEvent e)throws QMRemoteException
	{
			CodingIfc mark =(CodingIfc)(changeHash.get(this.changeBombox.getSelectedItem())); // ��ø��ı��
			Vector coll = ((CappRouteManageJDialog) parentDialog).getPartLinks();// ���·�߹��������������е��㲿�����
			if (coll != null && coll.size() != 0)
			{
				String currentId=myRoute.getBsoID();//��ǰ·��
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
              //���������״Ϊ�ȴ�״̬
              setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
              this.okJButton.setEnabled(false);
              this.cancelJButton.setEnabled(false);
              this.editJButton.setEnabled(false);
              //��ʾ�������
//            ProgressService.setProgressText(QMMessage.getLocalizedMessage(
//                    RESOURCE, CappRouteRB.SAVING, null));
//            ProgressService.showProgress();
              //���������zz 20060925 �����°������
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
     * ����(�½�����µ�)����·��
     *
     * @throws QMRemoteException
     */
    private void save() throws QMRemoteException {
                
        //�ڸ���ģʽ��,���û�д�·��ͼ,��ֱ�ӽ�·�ߴ����º��ύ����
        if (getViewMode() == UPDATE_MODE && editDialog == null) {
            //lm modify 20040827 ��·��˵��������
            if (verbose) {
                System.out
                        .println("RouteTalkJPanel:�ڸ���ģʽ��,���û�д�·��ͼ,��ֱ�ӽ�·�ߴ����º��ύ����");
            }
            this.getTechnicsRoute().setRouteDescription(
                    descriJTextArea.getText());
            //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�ߣ�����Ĭ����Ч���ֶ�
            this.getTechnicsRoute().setDefaultDescreption(this.validArea.getText());
            this.getTechnicsRoute().setIsAdopt(this.isAdoptRLCheckBox.isSelected());
            Object cc=changeHash.get(this.changeBombox.getSelectedItem());
            this.getTechnicsRoute().setModefyIdenty((CodingIfc)cc);
         //   this.getTechnicsRoute().setModefyIdenty((CodingIfc)changeHash.get(this.changeBombox.getSelectedItem()));
            //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·�ߣ�����Ĭ����Ч���ֶ�

            if (alterStatus == 0) {
                //���÷���,����·��
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

                //�����������ܲ��� �޸� zz  start
                  v= null;
                //�����������ܲ��� �޸� zz  end

            }
            //CCBegin SS4
            isAdoptRLCheckBox.setSelected(myRoute.getIsAdopt());
            //CCEnd SS4
            
        } else {
            HashMap saveMap = new HashMap();
            this.getTechnicsRoute().setRouteDescription(
                    descriJTextArea.getText());
            //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
            this.getTechnicsRoute().setDefaultDescreption(this.validArea.getText());
            this.getTechnicsRoute().setIsAdopt(this.isAdoptRLCheckBox.isSelected());
            Object cc=changeHash.get(this.changeBombox.getSelectedItem());
            this.getTechnicsRoute().setModefyIdenty((CodingIfc)cc);
            //this.getTechnicsRoute().setModefyIdenty((CodingIfc)changeHash.get(this.changeBombox.getSelectedItem()));
            //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

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

            //������½�ģʽ��,�������·��ͼ�����˱仯,�����»�������ɵ�·�߷�֧���ύ����
            Vector v2 = (Vector) commitUpdatedBranches();
            if (v2 != null && v2.size() > 0) {
                if (verbose) {
                    System.out.println(":::::::::::::::: ���� ��֧ �ĸ�����"
                            + v2.size());
                }
                saveMap.put("TechnicsRouteBranch", v2);
            }
      //�����������ܲ��� �޸� zz  start
                         v2 = null;
                        //�����������ܲ��� �޸� zz  end

            if (editDialog != null) {
                Vector v3 = this.branchNodeLinkVector; //��ԶΪ�½�
                if (v3 != null && v3.size() > 0) {
                    if (verbose) {
                        System.out.println(":::::::::::::::: ���� ��֧���ڵ� �����ĸ�����"
                                + v3.size());
                    }
                    saveMap.put("RouteBranchNodeLink", v3);
                }
                 //�����������ܲ��� �޸� zz  start
                           v3 = null;
                          //�����������ܲ��� �޸� zz  end

                Vector v4 = editDialog.routeNodeItemVector;
                if (v4 != null && v4.size() > 0) {
                    if (verbose) {
                        System.out.println(":::::::::::::::: ���� �ڵ� �ĸ�����"
                                + v4.size());
                    }
                    saveMap.put("RouteNode", v4);
                }
        //�����������ܲ��� �޸� zz  start
                           v4 = null;
                          //�����������ܲ��� �޸� zz  end

                Vector v5 = editDialog.routeLinkItemVector;
                if (v5 != null && v5.size() > 0) {
                    if (verbose) {
                        System.out.println(":::::::::::::::: ���� ���� �ĸ�����"
                                + v4.size());
                    }
                    saveMap.put("RouteNodeLink", v5);
                }
                //�����������ܲ��� �޸� zz  start
                   v5 = null;
                  //�����������ܲ��� �޸� zz  end

            }

            //���÷���,����·��
            Class[] c = { ListRoutePartLinkIfc.class, HashMap.class };
            Object[] obj = { this.getListPartLink(), saveMap };
            //����ֵ: �����һ��Ԫ��--����·��ֵ����,����ڶ���Ԫ��--HashMap
            useServiceMethod("TechnicsRouteService", "saveRoute", c, obj);
            //�����������ܲ��� �޸� zz  start
                  saveMap = null;
                 //�����������ܲ��� �޸� zz  end
        }

        //���,������ˢ��Ϊ�鿴״̬
        descriJTextArea.setEditable(false);
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        this.isAdoptRLCheckBox.setEnabled(false);
        this.changeBombox.setEnabled(false);
        //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
        qMMultiList.setCellEditable(false);
        qMMultiList.setColsEnabled(new int[] { 0, 1, 2, 3, 4 }, false);
        qMMultiList.setCheckboxEditable(false);

        okJButton.setVisible(false);
        cancelJButton.setVisible(false);
        this.isChangeRoute = true;
        mode = VIEW_MODE;
        //ˢ�����ڵ�
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
     * �������µ�·�߷�֧
     *
     * @return ���º��·�߷�֧����(Ԫ��ΪRouteItem)
     */
    private Vector commitUpdatedBranches() {
        Vector v = new Vector();
        branchNodeLinkVector.clear();
        if (branchMap.size() > 0) {
            for (int i = 0; i < branchMap.size(); i++) {
                String bsoid = qMMultiList.getCellText(i, 0);
                if (verbose) {
                    System.out.println("�ύʱ ·�߷�֧ ID = " + bsoid);
                }
                Object[] objs2 = (Object[]) branchMap.get(bsoid);
                TechnicsRouteBranchInfo branch = (TechnicsRouteBranchInfo) objs2[0];
                Vector curPathNodes = (Vector) objs2[1];
                if (verbose) {
                    System.out.println("�ύʱ ·�߷�֧" + i + "�Ľڵ������"
                            + curPathNodes.size());
                }
                if (qMMultiList.isCheckboxSelected(i, 4)) {
                    branch.setMainRoute(true);
                } else {
                    branch.setMainRoute(false);
                }
                setObjectNotPersist(branch); //·�߷�֧��ԶΪ�½�

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
     * ִ��ȡ������
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
     * ִ��ȡ��
     *
     * @return �Ƿ񱣴�ɹ�
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
     * ����ģʽ�£�ȡ����ť��ִ�з���. �����ǰ����ģʽΪ����,�����δ����,����ʾ�û��Ƿ񱣴�;���������,���˳�����.
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
     * ����ģʽ�£�ȡ����ť��ִ�з���. �����ǰ����ģʽΪ����,�����δ��������ʾ�û��Ƿ񱣴�;���������,�򽫽���ˢ��Ϊ�鿴״̬
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
     * ��ʾȷ�϶Ի���
     *
     * @param s
     *            �ڶԻ�������ʾ����Ϣ
     * @return ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
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
     * ��ʼ��·�ߵ�λ��
     */
//  CCBegin by leixiao 2008-8-6 ԭ�򣺽����������·�� ,��Ϊ·����֯����



    private void initTree() {

        try {
          //��ʼ��·�ߵ�λ��
          CodingClassificationIfc cc = null;
          if (this.getTechnicsRouteList().getRouteListLevel().equals(
              RouteListLevelType.FIRSTROUTE.getDisplay())) {
            Class[] c = {
                String.class, String.class};
            Object[] obj = {
                "·����֯����", "�������"};
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
//  CCEnd by leixiao 2008-8-6 ԭ�򣺽����������·��
    
//  CCBegin by leixiao 2008-8-6 ԭ�򣺽����������·�� 
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
//  CCEnd by leixiao 2008-8-6 ԭ�򣺽����������·��
    //WorkThread
    class WorkThread extends QMThread {
         int myAction;
         public WorkThread(int action) {
             super();
             this.myAction = action;
         }


         /**
          * WKTread���з���
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
     * 1���㲿����������׼��,��׼����Ź���CQ��Q��T��ͷ���㲿��������װ��·��ʱ��
     *    ������һ���߼��ܳɣ��߼��ܳɱ�Ź��򣺡�XXXXGXX����2800G01�㲿������λΪ��G�������������㲿��װ��·�ߣ�װ��·�߲�������Э����ʱ����ϵͳ�������ѡ�
     * 2���߼��ܳɱ�������·��ʱ�����䲻�������Ӽ�����׼�����⣩��װ��·�ߣ�װ��·�߲�������Э��������ϵͳ�������ѡ�
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
    				if(zp.indexOf("��")!=-1||zp.indexOf("��")!=-1)
    				{
    					if (this.confirmAction("װ��·���к��С������򡰵���·�ߣ��Ƿ񱣴棿"))
    					{
    						
    					}
    					else
    					{
    						return true;
    					}
    				}
    				//CCEnd SS5
    				
    				System.out.println("partnum=="+partnum);
    				//����һ�������߼��ܳɸ���
    				//���Ǳ�׼��
    				if(!(partnum.startsWith("CQ")||partnum.startsWith("Q")||partnum.startsWith("T")))
    				{
    					System.out.println("����һ");
    					//�õ�����·����Ϣ�������߼��ܳɼ���·��
    					Class[] params = {QMPartMasterIfc.class,String.class};
    					Object[] values = {this.myPart,this.myRouteList.getBsoID()};
    					Vector result = null;
    					try
    					{
    						result = (Vector) RouteListTaskJPanel.invokeRemoteMethodWithException(this,"TechnicsRouteService", "findParentsAndRoutes", params, values);
    					}
    					catch (QMRemoteException ex)
    					{
    						JOptionPane.showMessageDialog(this, ex.getClientMessage(), "����", JOptionPane.ERROR_MESSAGE);
    						return true;
    					}
    					int size1 = result.size();
    					System.out.println("size1=="+size1);
    					for (int ii = 0; ii < size1; ii++)
    					{
    						Object[] objs = (Object[]) result.elementAt(ii);
    						QMPartInfo parent = (QMPartInfo) objs[0];
    						System.out.println("parent=="+parent.getPartNumber());
    						//������߼��ܳɣ��鿴·���Ƿ����ѡ���㲿����װ��·�ߣ���������Э�������������������ʾ
    						if(parent.getPartNumber().trim().length()>5&&parent.getPartNumber().trim().substring(4,5).equals("G"))
    						{
    							String branch = (String) objs[3];
    							System.out.println("branch==="+branch);
    							if(branch==null)
    							{
    								if (this.confirmAction("���߼��ܳ�"+parent.getPartNumber().trim()+"������·��Ϊ�գ���������ǰ����װ��·�ߣ��Ƿ񱣴棿"))
    								{
    								}
    								else
    								{
    									return true;
    								}
    							}
    							else
    							{
    								if(zp!=null&&!zp.equals("��")&&!zp.equals("Э"))
    								{
    									if(branch.indexOf(zp)==-1)
    									{
    										if (this.confirmAction("���߼��ܳ�"+parent.getPartNumber().trim()+"������·�߲�������ǰ����װ��·�ߣ��Ƿ񱣴棿"))
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
    				
    				//�����������ü����߼��ܳɣ������Ӽ�
    				if(partnum.length()>5&&partnum.substring(4,5).equals("G"))
    				{
    					System.out.println("�����");
    					String zz = qMMultiList.getCellText(i, 2);
    					System.out.println("zz=="+zz);
    					//��ȡ�Ӽ�·��
    					Class[] params = {QMPartIfc.class};
    					Object[] values = {this.myPartInfo};
    					HashMap map = null;
    					try
    					{
    						map = (HashMap) RouteListTaskJPanel.invokeRemoteMethodWithException(this,"TechnicsRouteService", "getSubPartRoute", params, values);
    					}
    					catch (QMRemoteException ex)
    					{
    						JOptionPane.showMessageDialog(this, ex.getClientMessage(), "����", JOptionPane.ERROR_MESSAGE);
    						return true;
    					}
    					for (Iterator iter = map.keySet().iterator(); iter.hasNext(); )
    					{
    						String subnum = (String)iter.next();
    						String[] tmp = (String[]) map.get(subnum);
    						//�߼��ܳɱ�������·��ʱ�����䲻�������Ӽ�����׼�����⣩��װ��·�ߣ�װ��·�߲�������Э��������ϵͳ�������ѡ�
    						if(tmp[1]!=null&&!tmp[1].equals("")&&!tmp[1].equals("��")&&!tmp[1].equals("Э"))
    						{
    							if(zz.indexOf(tmp[1])==-1)
    							{
    								if (this.confirmAction("��ǰ�߼��ܳɵ�����·�ߣ��������Ӽ�"+subnum+"��װ��·��("+tmp[1]+")���Ƿ񱣴棿"))
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
     * ���·���Ƿ����������·��
     * �ܡ��Ρ��ܡ�������Ϳ��������
     */
    private boolean checkMainRoute(String route)
    {
    	String routestr = RemoteProperty.getProperty("KaCheChangRoute", "��,��,��,��,��,Ϳ,��,��1");
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
  * <p>Title:�༭��ť���������</p>
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
//  CCBegin by leixiao 2008-8-6 ԭ�򣺽����������·��
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
//    CCBEnd by leixiao 2008-8-6 ԭ�򣺽����������·��


