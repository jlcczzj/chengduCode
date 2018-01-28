/**
 * 生成程序 RouteCopyFromJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * 从其它路线表复制路线界面
 * </p>
 * 界面中显示此零部件被列入零部件表的同级别路线表(不包括本次路线表), 如果用户当前正在编辑的路线表是二级路线表，则显示路线表的过滤条件应包括路线单位,
 * 即：列表中只显示本单位的二级路线表。
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

    /** 当前零部件 */
    private QMPartMasterIfc myPart;

    /** 当前路线表 */
    private TechnicsRouteListIfc myRouteList;

    /** 当前路线表的零部件关联 */
    private ListRoutePartLinkIfc myPartLink;

    /**
     * 缓存路线表对象（键为BsoID）
     */
    private HashMap routelistMap = new HashMap();

    /** 缓存选中的路线对象的BsoID */
    private String selectedRouteID = "";

    /** 标记:是否保存 */
    public boolean isSave = false;

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    private HashMap largeMap = new HashMap();

    /**
     * 构造函数
     *
     * @roseuid 4031A7710376
     */
    public RouteCopyFromJDialog() {
        this(null);
    }

    /**
     * 构造函数
     *
     * @param parent
     *            父窗口
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
     * 初始化
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setTitle("路线复制自...");
        this.setSize(650, 500);
        this.getContentPane().setLayout(gridBagLayout5);
        jPanel1.setLayout(gridBagLayout1);
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("零部件编号");
        partNumberJLabel.setText("<零部件编号>");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("零部件名称");
        partNameJLabel.setText("<零部件名称>");
        jPanel2.setLayout(gridBagLayout2);
        jLabel2.setText("路线表");
        levelJLabel.setToolTipText("");
        levelJLabel.setText("<级别>");
        jPanel3.setLayout(gridBagLayout3);
        jLabel5.setText("路线");
        jPanel4.setLayout(gridBagLayout4);
        okJButton.setEnabled(false);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("OK");
        okJButton.setActionCommand("确定");
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
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setText("选择路线复制自路线表...");

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

        //  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
        routeListMultiList.setHeadings(new String[] {"id", "标记", "编号", "名称", "版本",
                                       "用于产品"});
        routeListMultiList.setRelColWidth(new int[] {0, 1, 1, 1, 1, 1});
     //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
        routeListMultiList.setCellEditable(false);
        routeListMultiList.setMultipleMode(false);

        branchMultiList
                .setHeadings(new String[] { "序号", "制造路线", "装配路线", "主要路线" });
        branchMultiList.setRelColWidth(new int[] { 1, 4, 2, 2 });
        branchMultiList.setCellEditable(false);
        branchMultiList.setMultipleMode(false);
    }

    /**
     * 设置当前路线表的零部件关联
     *
     * @param link
     *            零部件关联
     */
    public void setListPartLink(ListRoutePartLinkIfc link) {
        myPartLink = link;
        setPart(link.getPartMasterInfo());
    }

    /**
     * 设置当前零部件
     *
     * @param myPart
     *            当前零部件
     */
    private void setPart(QMPartMasterIfc myPart) {
        this.myPart = myPart;
        setDataDisplay();
    }

    /**
     * 获得路线管理器所属的路线表
     *
     * @return 路线管理器所属的路线表
     */
    public TechnicsRouteListIfc getRouteList() {
        return myRouteList;
    }

    /**
     * 设置路线管理器所属的路线表
     *
     * @param myRouteList
     *            所属的路线表
     */
    public void setRouteList(TechnicsRouteListIfc myRouteList) {
        this.myRouteList = myRouteList;
    }

    /**
     * 获得被选择的路线对象的BsoID
     *
     * @return 被选择的路线对象的BsoID
     */
    public String getSelectedRoute() {
        String s = "";
        if (isSave)
            s = selectedRouteID;
        return s;
    }

    /**
     * 显示界面信息
     */
    private void setDataDisplay() {
        largeMap.clear();
        partNumberJLabel.setText(myPart.getPartNumber());
        partNameJLabel.setText(myPart.getPartName());
        levelJLabel.setText(myRouteList.getRouteListLevel());

        try {
          //调用服务,获得同级路线表
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
          //把获得的路线表填入列表
          addRouteListToMultiList(collection);
        }
        catch (QMRemoteException ex) {
          JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "information", null),
                                        JOptionPane.INFORMATION_MESSAGE);
        }
      }
    //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线  
    /**
     * 把路线表添加入可选列表中
     *
     * @param collection
     *            路线表值对象的集合
     * @throws QMRemoteException
     */
    //  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线  
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
    //改为显示最新版本
    //routeListMultiList.addTextCell(i, 4, newList.getVersionValue());
    //显示原版本 20070829 liuming modify
    routeListMultiList.addTextCell(i, 4, routelist.getVersionValue());
    QMPartMasterIfc part = (QMPartMasterIfc) RParentJPanel.refreshInfo(
        routelist.getProductMasterID());
    routeListMultiList.addTextCell(i, 5, RParentJPanel.getIdentity(part));
    routelistMap.put(routelistpart.getBsoID(), routelist);
  }
}
    //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线

    /**
     * 将路线分支填入列表
     *
     * @param map
     *            键为路线分支对象,值为该分支中的所有路线单位节点
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
                //列表属性数组："序号","制造路线","装配路线","主要路线"
                branchMultiList.addTextCell(i, 0, String.valueOf(i + 1));
                if (branchinfo.getMainRoute())
                    branchMultiList.addTextCell(i, 3, "是");
                else
                    branchMultiList.addTextCell(i, 3, "否");
                String makeStr = "";
                String assemStr = "";
                Object[] nodes = (Object[]) newMap.get(branchinfo);
                Vector makeNodes = (Vector) nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

                if (makeNodes != null && makeNodes.size() > 0) {
                    //System.out.println(">>>>>>>>>>>>>>>>> 获得
                    // 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
                    for (int m = 0; m < makeNodes.size(); m++) {
                        RouteNodeInfo node = (RouteNodeInfo) makeNodes
                                .elementAt(m);
                        if (makeStr == "")
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "→"
                                    + node.getNodeDepartmentName();
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
     * 选择某路线表后,获得相应的路线串并加入列表,使"确定"按钮有效,更新状态栏.
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
            //根据选择的路线表和当前零部件获得其对应的路线和路线分支
            //  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线      
//            HashMap map = (HashMap) largeMap.get(routelistMap
//                    .get(routelistBsoID));
            HashMap map = (HashMap) largeMap.get(routelistBsoID);
            //  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
            addBranchToMultiList(map);
            okJButton.setEnabled(true);
            statusJLabel.setText("选择路线复制自 "
                    + RParentJPanel
                            .getIdentity((TechnicsRouteListInfo) routelistMap
                                    .get(routelistBsoID)));
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * MultiList支路鼠标进入事件
     * @param e
     */
    void branchMultiList_mouseEntered(MouseEvent e) {
        statusJLabel.setText("路线串列表（只读）");
    }

    /**
     * 路线MultiList进入事件
     * @param e
     */
    void routeListMultiList_mouseEntered(MouseEvent e) {
        statusJLabel.setText("选择路线复制自路线表...");
    }

    /**
     * 确定操作
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        this.isSave = true;
        this.dispose();
    }

    /**
     * 取消操作
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this.isSave = false;
        this.dispose();
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

}
