/**
 * ���ɳ��� RouteCopyFromJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.*;
import javax.swing.*;
import com.faw_qm.clients.util.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.framework.remote.*;
import java.util.*;
import java.awt.event.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.enterprise.model.MasterIfc;

/**
 * <p>
 * ������·�߱���·�߽���
 * </p>
 * ��������ʾ���㲿���������㲿�����ͬ����·�߱�(����������·�߱�), ����û���ǰ���ڱ༭��·�߱��Ƕ���·�߱�����ʾ·�߱�Ĺ�������Ӧ����·�ߵ�λ,
 * �����б���ֻ��ʾ����λ�Ķ���·�߱�
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

public class RouteCopyFromJDialog extends JDialog {
    private JPanel jPanel1 = new JPanel();

    private JLabel jLabel1 = new JLabel();

    private JLabel partNumberJLabel = new JLabel();

    private JLabel jLabel3 = new JLabel();

    private JLabel partNameJLabel = new JLabel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel2 = new JPanel();

    private JLabel jLabel2 = new JLabel();

    private JLabel levelJLabel = new JLabel();

    private QMMultiList routeListMultiList = new QMMultiList();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private JPanel jPanel3 = new JPanel();

    private JLabel jLabel5 = new JLabel();

    private QMMultiList branchMultiList = new QMMultiList();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    private JPanel jPanel4 = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    /** ��ǰ�㲿�� */
    private QMPartMasterIfc myPart;

    /** ��ǰ·�߱� */
    private TechnicsRouteListIfc myRouteList;

    /** ��ǰ·�߱���㲿������ */
    private ListRoutePartLinkIfc myPartLink;

    /**
     * ����·�߱���󣨼�ΪBsoID��
     */
    private HashMap routelistMap = new HashMap();

    /** ����ѡ�е�·�߶����BsoID */
    private String selectedRouteID = "";

    /** ���:�Ƿ񱣴� */
    public boolean isSave = false;

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    private HashMap largeMap = new HashMap();

    /**
     * ���캯��
     *
     * @roseuid 4031A7710376
     */
    public RouteCopyFromJDialog() {
        this(null);
    }

    /**
     * ���캯��
     *
     * @param parent
     *            ������
     * @roseuid 40317F050290
     */
    public RouteCopyFromJDialog(JDialog parent) {
        super(parent, "", true);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setTitle("·�߸�����...");
        this.setSize(650, 500);
        this.getContentPane().setLayout(gridBagLayout5);
        jPanel1.setLayout(gridBagLayout1);
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("�㲿�����");
        partNumberJLabel.setText("<�㲿�����>");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("�㲿������");
        partNameJLabel.setText("<�㲿������>");
        jPanel2.setLayout(gridBagLayout2);
        jLabel2.setText("·�߱�");
        levelJLabel.setToolTipText("");
        levelJLabel.setText("<����>");
        jPanel3.setLayout(gridBagLayout3);
        jLabel5.setText("·��");
        jPanel4.setLayout(gridBagLayout4);
        okJButton.setEnabled(false);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("OK");
        okJButton.setActionCommand("ȷ��");
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
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setText("ѡ��·�߸�����·�߱�...");

        routeListMultiList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                routeListMultiList_itemStateChanged(e);
            }
        });
        branchMultiList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                branchMultiList_mouseEntered(e);
            }
        });
        routeListMultiList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                routeListMultiList_mouseEntered(e);
            }
        });
        this.getContentPane().add(
                jPanel1,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 10, 0, 10), 0, 0));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        5, 0, 5, 0), 0, 0));
        jPanel1.add(partNumberJLabel, new GridBagConstraints(1, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 8, 5, 0), 0, 0));
        jPanel1.add(jLabel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 5, 0), 0, 0));
        jPanel1.add(partNameJLabel, new GridBagConstraints(1, 1, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 5, 0), 0, 0));
        this.getContentPane().add(
                jPanel2,
                new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 10, 0, 10), 0, 0));
        jPanel2.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                        0, 3, 0), 0, 0));
        jPanel2.add(levelJLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 0, 3, 0), 0, 0));
        jPanel2.add(routeListMultiList, new GridBagConstraints(0, 1, 2, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                jPanel3,
                new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 10, 0, 10), 0, 0));
        jPanel3.add(jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 3, 0), 0, 0));
        jPanel3.add(branchMultiList, new GridBagConstraints(0, 1, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                jPanel4,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(15, 0, 15, 10), 0, 0));
        jPanel4.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 0, 0, 0), 0, 0));
        jPanel4.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        this.getContentPane().add(
                statusJLabel,
                new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));

        //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
        routeListMultiList.setHeadings(new String[] {"id", "���", "���", "����", "�汾",
                                       "���ڲ�Ʒ"});
        routeListMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 1});
     //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
        routeListMultiList.setCellEditable(false);
        routeListMultiList.setMultipleMode(false);

        branchMultiList
                .setHeadings(new String[] { "���", "����·��", "װ��·��", "��Ҫ·��" });
        branchMultiList.setRelColWidth(new int[] { 1, 4, 2, 2 });
        branchMultiList.setCellEditable(false);
        branchMultiList.setMultipleMode(false);
    }

    /**
     * ���õ�ǰ·�߱���㲿������
     *
     * @param link
     *            �㲿������
     */
    public void setListPartLink(ListRoutePartLinkIfc link) {
        myPartLink = link;
        setPart(link.getPartMasterInfo());
    }

    /**
     * ���õ�ǰ�㲿��
     *
     * @param myPart
     *            ��ǰ�㲿��
     */
    private void setPart(QMPartMasterIfc myPart) {
        this.myPart = myPart;
        setDataDisplay();
    }

    /**
     * ���·�߹�����������·�߱�
     *
     * @return ·�߹�����������·�߱�
     */
    public TechnicsRouteListIfc getRouteList() {
        return myRouteList;
    }

    /**
     * ����·�߹�����������·�߱�
     *
     * @param myRouteList
     *            ������·�߱�
     */
    public void setRouteList(TechnicsRouteListIfc myRouteList) {
        this.myRouteList = myRouteList;
    }

    /**
     * ��ñ�ѡ���·�߶����BsoID
     *
     * @return ��ѡ���·�߶����BsoID
     */
    public String getSelectedRoute() {
        String s = "";
        if (isSave)
            s = selectedRouteID;
        return s;
    }

    /**
     * ��ʾ������Ϣ
     */
    private void setDataDisplay() {
        largeMap.clear();
        partNumberJLabel.setText(myPart.getPartNumber());
        partNameJLabel.setText(myPart.getPartName());
        levelJLabel.setText(myRouteList.getRouteListLevel());

        try {
          //���÷���,���ͬ��·�߱�
          Class[] c = {
              ListRoutePartLinkIfc.class};
          Object[] objs = {
              myPartLink};
          Collection collection = (Collection) CappRouteAction.useServiceMethod(
              "TechnicsRouteService", "getAdoptRoutes", c, objs);
          if (collection != null && collection.size() > 0) {
            for (int i = 0; i < collection.size(); i++) {
              Object[] obj = (Object[]) ( (Vector) collection).elementAt(i);
              HashMap map = new HashMap();
              map.put(obj[1], obj[2]);
            //  System.out.println("----map1="+map);
             ListRoutePartLinkIfc link=(ListRoutePartLinkIfc)obj[0];
              largeMap.put(link.getBsoID(), map);
            }
          }
          //�ѻ�õ�·�߱������б�
          addRouteListToMultiList(collection);
        }
        catch (QMRemoteException ex) {
          JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "information", null),
                                        JOptionPane.INFORMATION_MESSAGE);
        }
      }
    //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��  
    /**
     * ��·�߱�������ѡ�б���
     *
     * @param collection
     *            ·�߱�ֵ����ļ���
     * @throws QMRemoteException
     */
    //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��  
    private void addRouteListToMultiList(Collection collection) throws
    QMRemoteException {
  routelistMap.clear();
  Object[] lists = collection.toArray();
  for (int i = 0; i < lists.length; i++) {
    Object[] objs = (Object[]) lists[i];
   // System.out.println("---------------leixleix objs="+objs[0]);
    ListRoutePartLinkIfc routelistpart = (ListRoutePartLinkIfc) objs[0];
    TechnicsRouteListIfc routelist = (TechnicsRouteListIfc) RParentJPanel.
        refreshInfo(routelistpart.getRouteListID());

    TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) RParentJPanel.
        refreshInfo(routelistpart.getRouteID());
    routeListMultiList.addTextCell(i, 0, routelistpart.getBsoID());
    routeListMultiList.addTextCell(i, 1, routeInfo.getModefyIdenty().getCodeContent());
    routeListMultiList.addTextCell(i, 2, routelist.getRouteListNumber());
    routeListMultiList.addTextCell(i, 3, routelist.getRouteListName());
    //��Ϊ��ʾ���°汾
    //routeListMultiList.addTextCell(i, 4, newList.getVersionValue());
    //��ʾԭ�汾 20070829 liuming modify
    routeListMultiList.addTextCell(i, 4, routelist.getVersionValue());
    QMPartMasterIfc part = (QMPartMasterIfc) RParentJPanel.refreshInfo(
        routelist.getProductMasterID());
    routeListMultiList.addTextCell(i, 5, RParentJPanel.getIdentity(part));
    routelistMap.put(routelistpart.getBsoID(), routelist);
  }
}
    //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��

    /**
     * ��·�߷�֧�����б�
     *
     * @param map
     *            ��Ϊ·�߷�֧����,ֵΪ�÷�֧�е�����·�ߵ�λ�ڵ�
     */
    private void addBranchToMultiList(HashMap map) {
        branchMultiList.clear();
        Object[] routes = (Object[]) map.keySet().toArray();
        for (int k = 0; k < routes.length; k++) {
            TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) routes[k];
            selectedRouteID = routeInfo.getBsoID();
            Map newMap = (Map) map.get(routeInfo);
            Object[] branchs = RouteHelper.sortedInfos(newMap.keySet())
                    .toArray();
            for (int i = 0; i < branchs.length; i++) {
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) branchs[i];
                //�б��������飺"���","����·��","װ��·��","��Ҫ·��"
                branchMultiList.addTextCell(i, 0, String.valueOf(i + 1));
                if (branchinfo.getMainRoute())
                    branchMultiList.addTextCell(i, 3, "��");
                else
                    branchMultiList.addTextCell(i, 3, "��");
                String makeStr = "";
                String assemStr = "";
                Object[] nodes = (Object[]) newMap.get(branchinfo);
                Vector makeNodes = (Vector) nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

                if (makeNodes != null && makeNodes.size() > 0) {
                    //System.out.println(">>>>>>>>>>>>>>>>> ���
                    // ��֧"+branchinfo.getBsoID()+"������ڵ� ������"+makeNodes.size());
                    for (int m = 0; m < makeNodes.size(); m++) {
                        RouteNodeInfo node = (RouteNodeInfo) makeNodes
                                .elementAt(m);
                        if (makeStr == "")
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "��"
                                    + node.getNodeDepartmentName();
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
                branchMultiList.addTextCell(i, 1, makeStr);
                branchMultiList.addTextCell(i, 2, assemStr);

            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RouteCopyFromJDialog d = new RouteCopyFromJDialog();
        d.setVisible(true);
    }

    /**
     * ѡ��ĳ·�߱��,�����Ӧ��·�ߴ��������б�,ʹ"ȷ��"��ť��Ч,����״̬��.
     *
     * @param e
     *            ItemEvent
     */
    void routeListMultiList_itemStateChanged(ItemEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int index = routeListMultiList.getSelectedRow();
        if (index != -1) {
            String routelistBsoID = routeListMultiList.getCellAt(index, 0)
                    .getStringValue();
            //����ѡ���·�߱�͵�ǰ�㲿��������Ӧ��·�ߺ�·�߷�֧
            //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��      
//            HashMap map = (HashMap) largeMap.get(routelistMap
//                    .get(routelistBsoID));
            HashMap map = (HashMap) largeMap.get(routelistBsoID);
            //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
            addBranchToMultiList(map);
            okJButton.setEnabled(true);
            statusJLabel.setText("ѡ��·�߸����� "
                    + RParentJPanel
                            .getIdentity((TechnicsRouteListInfo) routelistMap
                                    .get(routelistBsoID)));
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * MultiList֧·�������¼�
     * @param e
     */
    void branchMultiList_mouseEntered(MouseEvent e) {
        statusJLabel.setText("·�ߴ��б�ֻ����");
    }

    /**
     * ·��MultiList�����¼�
     * @param e
     */
    void routeListMultiList_mouseEntered(MouseEvent e) {
        statusJLabel.setText("ѡ��·�߸�����·�߱�...");
    }

    /**
     * ȷ������
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        this.isSave = true;
        this.dispose();
    }

    /**
     * ȡ������
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this.isSave = false;
        this.dispose();
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

}
