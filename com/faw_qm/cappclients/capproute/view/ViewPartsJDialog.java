/**
 * 生成程序 ViewPartsJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.cappclients.capproute.util.CappRouteRB;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;

/**
 * <p>
 * Title:显示零部件表界面(启动工艺路线管理器界面)
 * </p>
 * 本界面应用： 在路线表管理器界面选中一个工艺路线表，执行编辑路线操作，即进入本届面，随后启动工艺路线管理器界面。
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

public class ViewPartsJDialog extends JDialog {
    private JPanel panel1 = new JPanel();

    private JLabel jLabel1 = new JLabel();

    private JLabel numberJLabel = new JLabel();

    private JLabel jLabel3 = new JLabel();

    private JLabel levelJLabel = new JLabel();

    private JLabel jLabel5 = new JLabel();

    private JLabel nameJLabel = new JLabel();

    private JLabel jLabel7 = new JLabel();

    private JLabel departmentJLabel = new JLabel();

    private JLabel jLabel9 = new JLabel();

    private JLabel productJLabel = new JLabel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private QMMultiList qMMultiList = new QMMultiList();

    private JPanel jPanel1 = new JPanel();

    private JLabel jLabel2 = new JLabel();

    private JButton selectAllJButton = new JButton();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    /** 业务对象:路线表 */
    private TechnicsRouteListIfc myRouteList;

    /** 父窗口 */
    private JFrame parentFrame;

    /** 资源文件路径 */
    private static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 缓存：键为零部件的BsoID，值为零部件QMPartMasterInfo */
    private HashMap partsMap = new HashMap();

    /** 线程组 */
    private static ThreadGroup theThreadGroup = Thread.currentThread()
            .getThreadGroup();

    private CappRouteManageJDialog routeManageJDialog;

    /**
     * 构造函数
     *
     * @param frame
     *            父窗口
     */
    public ViewPartsJDialog(JFrame frame) {
        super(frame, "", true);
        try {
            parentFrame = frame;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 组件初始化
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setSize(650, 500);
        panel1.setLayout(gridBagLayout1);
        this.setTitle("显示零部件表");
        this.getContentPane().setLayout(gridBagLayout3);
        panel1.setMaximumSize(new Dimension(201, 76));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        //CCBegin by leixiao 2010-1-27 将路线表改为艺准/艺毕
        jLabel1.setText("艺准/艺毕编号");
        //CCEnd by leixiao 2010-1-27 将路线表改为艺准/艺毕
        numberJLabel.setText("jLabel2");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("级别");
        levelJLabel.setText("jLabel4");
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        //CCBegin by leixiao 2010-1-27 将路线表改为艺准/艺毕
        jLabel5.setText("艺准/艺毕名称");
        //CCEnd by leixiao 2010-1-27 将路线表改为艺准/艺毕
        nameJLabel.setText("jLabel6");
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setText("单位");
        jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel9.setText("用于产品");
        productJLabel.setText("jLabel10");
        jPanel1.setLayout(gridBagLayout2);
        jLabel2.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabel2.setText("显示零部件表");
        selectAllJButton.setMaximumSize(new Dimension(75, 23));
        selectAllJButton.setMinimumSize(new Dimension(75, 23));
        selectAllJButton.setPreferredSize(new Dimension(75, 23));
        selectAllJButton.setToolTipText("Select All");
        selectAllJButton.setText("全选(A)");
        selectAllJButton.setMnemonic('A');
        selectAllJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAllJButton_actionPerformed(e);
            }
        });
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
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
        this.getContentPane().add(
                panel1,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(10, 14, 10,
                                14), 0, 0));
        panel1.add(jLabel9, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3,
                        0, 3, 0), 0, 0));
        panel1.add(levelJLabel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jLabel3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                        8, 0, 0), 0, 0));
        panel1.add(numberJLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3,
                        0, 3, 0), 0, 0));
        panel1.add(nameJLabel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(productJLabel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(departmentJLabel, new GridBagConstraints(3, 1, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3,
                        0, 3, 0), 0, 0));
        panel1.add(jLabel7, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                        8, 0, 0), 0, 0));
        this.getContentPane().add(
                qMMultiList,
                new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 10, 0, 10), 0, 0));
        this.getContentPane().add(
                jPanel1,
                new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(20, 0, 20, 10), 0, 0));
        jPanel1.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        jPanel1.add(selectAllJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                        0, 8, 0, 0), 0, 0));
        this.getContentPane().add(
                jLabel2,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));

        qMMultiList.setHeadings(new String[] { "id", "零部件编号", "零部件名称", "父件编号",
                "父件名称", "路线状态" });
        qMMultiList.setRelColWidth(new int[] { 0, 1, 1, 1, 1, 1 });
        qMMultiList.setCellEditable(false);
        qMMultiList.setMultipleMode(true);
    }

    /**
     * 设置业务对象
     *
     * @param routelist
     *            指定的路线表
     */
    public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
        myRouteList = routelist;
        this.setDataDisplay();
    }

    /**
     * 获得业务对象
     *
     * @return 当前路线表
     */
    public TechnicsRouteListIfc getTechnicsRouteList() {
        return myRouteList;
    }

    /**
     * 调用服务，获得当前编辑的路线表的所有零部件关联
     *
     * @return 零部件关联的集合
     * @throws QMRemoteException
     */
    private Vector getRouteListPartLinks() throws QMRemoteException {
        Class[] c = { TechnicsRouteListIfc.class };
        Object[] obj = { myRouteList };
        return (Vector) CappRouteAction.useServiceMethod(
                "TechnicsRouteService", "getRouteListLinkParts", c, obj);
    }

    /**
     * 设置显示界面信息
     */
    private void setDataDisplay() {
        try {
            numberJLabel.setText(myRouteList.getRouteListNumber());
            if(myRouteList.getRouteListName().length()>30)
              nameJLabel.setText(myRouteList.getRouteListName().substring(0,30)+"...");
            else
              nameJLabel.setText(myRouteList.getRouteListName());
            levelJLabel.setText(myRouteList.getRouteListLevel());

            String department = ((TechnicsRouteListInfo) myRouteList)
                    .getDepartmentName();
            //单位也许为空，一级路线无单位，二级路线有单位
            if (department != null && !department.equals("")) {
                departmentJLabel.setText(department);
            }
            //获得路线表所对应的产品
            QMPartMasterInfo part = (QMPartMasterInfo) RParentJPanel
                    .refreshInfo(myRouteList.getProductMasterID());
            productJLabel.setText(part.getPartNumber() + "_"
                    + part.getPartName());
            this.addPartsToList(this.getRouteListPartLinks());
        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 把所有符合条件的零部件添加入列表中
     *
     * @param partLinks
     *            零部件关联值对象集合
     */
    private void addPartsToList(Vector partLinks) {
        if (partLinks != null && partLinks.size() > 0) {
            String as[] = new String[partLinks.size()];
            String adoptStatus;
            for (int i = 0; i < partLinks.size(); i++) {
                ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) partLinks
                        .elementAt(i);
                QMPartMasterInfo info = (QMPartMasterInfo) link
                        .getPartMasterInfo();
                String parentNum = "";
                String parentName = "";
                if(link.getParentPartID()!=null)
                {
                  parentNum = link.getParentPartNum();
                  parentName = link.getParentPartName();
                }
                as[i] = link.getBsoID() + ";" + info.getPartNumber() + ";"
                        + info.getPartName() + ";" + parentNum
                        + ";" + parentName + ";"
                        + link.getAdoptStatus(); ///路线状态
                partsMap.put(link.getBsoID() + parentNum, link);
            }
            qMMultiList.setListItems(as);
        }
    }

    /**
     * 获得被选择的零部件
     */
    private void getSelectedParts() {
        int[] indexes = qMMultiList.getSelectedRows();
        for (int i = 0; i < indexes.length; i++) {
            int x = indexes[i];
            if (x != -1) {
                String bsoid = qMMultiList.getCellAt(x, 0).toString();
                String parentNum = qMMultiList.getCellAt(x, 3).toString();
                ListRoutePartLinkIfc curinfo = (ListRoutePartLinkIfc) partsMap
                        .get(bsoid + parentNum);
                selectedPartLinks.addElement(curinfo);
            }
        }
    }

    /**
     * 缓存用户所选择的零部件关联(键为bsoID,值为关联值对象)
     */
    private Vector selectedPartLinks = new Vector();

    /**
     * 执行"全选"操作,选中列表中的所有零部件
     *
     * @param e
     *            ActionEvent
     */
    void selectAllJButton_actionPerformed(ActionEvent e) {
        if (qMMultiList.getNumberOfRows() > 0)
            qMMultiList.selectAll();
        else {
            JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(
                    RESOURCE, CappRouteRB.NOT_HAVE_PART, null), QMMessage
                    .getLocalizedMessage(RESOURCE, "notice", null),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 执行"确定"操作
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        try {
            confirmOperation();
        } catch (QMRemoteException ex) {
            setCursor(Cursor.getDefaultCursor());
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 执行"取消"操作
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    /**
     * 将执行者选择的路线表和执行者选中的零部件显示在“工艺路线管理器”界面 注意判断父窗口不同,执行也有所不同
     *
     * @throws QMRemoteException
     */
    private void confirmOperation() throws QMRemoteException {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.getSelectedParts();
        //启动“工艺路线管理器”界面
        if (selectedPartLinks.size() > 0) {
            this.setVisible(false);
            routeManageJDialog = new CappRouteManageJDialog(parentFrame,
                    selectedPartLinks);
            routeManageJDialog.setVisible(true);
            if (!routeManageJDialog.isShowing()) {
                ((CappRouteListManageJFrame) parentFrame).controller.refresh();
            }
        } else {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(
                    RESOURCE, CappRouteRB.CAN_NOT_EDIT_ROUTE, null), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        setCursor(Cursor.getDefaultCursor());
    }

     /**
      * <p>Title:内部查询线程</p>
      * <p>Description: </p>
      */
    class MyThread extends QMThread {
        /**
         * 构造查询实例
         *
         * @param threadgroup
         *            线程组
         */
        public MyThread(ThreadGroup threadgroup) {
            super(threadgroup);
        }

        /**
         * 线程运行方法，执行搜索。
         */
        public void run() {
            if (verbose) {
                System.out.println("ViewPartsJDialog.MyThread: run() begin...");
            }
            try {
                //执行搜索
                //ViewPartsJDialog.this.routeManageJFrame.setRoute(ViewPartsJDialog.this.getRoute());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                //ViewPartsJDialog.this.routeManageJFrame.threadEnd = true;
                if (verbose) {
                    System.out.println("Inside run finally......");
                }
            }
            if (verbose) {
                System.out
                        .println("ViewPartsJDialog.MyThread: run() end...return is void");
            }
        }
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
