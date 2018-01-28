/** 
 * 生成程序RLCheckInJFrame.java	1.1  2004/02/29
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 2009/06/08 刘学宇  原因：将“检入工艺路线表”的界面由非模态修改为模态
 * SS1 成都设置检入默认资料夹 liuyuzhu 2016-10-12
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.util.NotCheckedOutException;
import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.RouteListTreeObject;
import com.faw_qm.clients.beans.explorer.ReferenceHolder;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.content.ejb.service.FormatContentHolder;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.util.QMCt;
import com.faw_qm.version.exception.VersionControlException;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>
 * Title: 检入工艺路线表界面
 * </p>
 * <p>
 * 把一个创建或更新完成的工艺路线表对象放入共享资料夹中，新创建工艺头信息首次检入时
 * </p>
 * <p>
 * 需要指定资料夹路径。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * 
 * @author 刘明
 * @version 1.0
 */

public class RLCheckInJFrame extends JDialog //CR1
{
    /** 检入标签 */
    private JLabel checkInJLabel = new JLabel();

    /** 检入对象标签 */
    private JLabel objectJLabel = new JLabel();

    /** 资料夹 */
    private FolderPanel folderPanel = new FolderPanel();

    /** 注释 */
    private JLabel descriJLabel = new JLabel();

    private JScrollPane descriJScrollPane = new JScrollPane();

    private JTextArea descriJTextArea = new JTextArea();

    /** 按钮面板 */
    private JPanel buttonJPanel = new JPanel();

    /** 确定按钮 */
    private JButton okJButton = new JButton();

    /** 取消按钮 */
    private JButton cancelJButton = new JButton();

    /** 状态栏 */
    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private WorkableHandle checkInItemHandle;

    /** 资料夹对象 */
    private FolderIfc checkInFolder;

    /** 标记：是否首次检入 */
    private boolean firstCheckIn;

    /** 当前线程 */
    private ThreadGroup contextGroup;

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 用于标记资源 */
    protected static ResourceBundle resource = null;

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private JFrame parentJFrame;

    /**
     * 构造函数
     * 
     * @param frame
     *            父窗口
     */
    public RLCheckInJFrame(JFrame frame) {
    	//begin CR1
    	super(frame,"", true);
    	//end CR1
        try {
            jbInit();
            this.parentJFrame = frame;
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
        URL url = CappRouteListManageJFrame.class
                .getResource("/images/routeM.gif");
        if (url != null) {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));

        }

        //{{初始化FolderPanel :公共资料夹 修改权限
        folderPanel.setIsPersonalFolder(false);
        folderPanel.setIsPublicFolders(true);
        folderPanel.setPermission("modify");
        //}}
        firstCheckIn = false;
        contextGroup = QMCt.getContext().getThreadGroup();
        this.setSize(400, 300);
        String sTitle = QMMessage.getLocalizedMessage(RESOURCE, "checkInTitle",
                null);
        this.setTitle(sTitle);
        checkInJLabel.setMaximumSize(new Dimension(48, 22));
        checkInJLabel.setMinimumSize(new Dimension(48, 22));
        checkInJLabel.setPreferredSize(new Dimension(48, 22));
        checkInJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        checkInJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        checkInJLabel.setText("正在检入");
        this.getContentPane().setLayout(gridBagLayout2);
        objectJLabel.setMaximumSize(new Dimension(48, 22));
        objectJLabel.setMinimumSize(new Dimension(48, 22));
        objectJLabel.setPreferredSize(new Dimension(48, 22));
        descriJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        descriJLabel.setText("注释");
        buttonJPanel.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                okJButton_keyPressed(e);
            }
        });
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cancelJButton_keyPressed(e);
            }
        });
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(4, 22));
        statusJLabel.setMinimumSize(new Dimension(4, 22));
        statusJLabel.setPreferredSize(new Dimension(4, 22));
        statusJLabel.setText("正在检入工艺路线表");
        buttonJPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                descriJLabel,
                new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                        new Insets(8, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                descriJScrollPane,
                new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(8, 8, 0, 10), 0, 0));
        this.getContentPane().add(
                folderPanel,
                new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(8, 17, 0, 10), 0, 0));
        this.getContentPane().add(
                objectJLabel,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.SOUTHWEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(20, 8, 0, 10), 0, 0));
        this.getContentPane().add(
                checkInJLabel,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                        new Insets(20, 10, 0, 0), 0, 0));
        descriJScrollPane.getViewport().add(descriJTextArea, null);
        this.getContentPane().add(
                statusJLabel,
                new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                buttonJPanel,
                new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(30, 0, 30, 10), 0, 0));
        localize();
        //CCBegin SS1
        String userdesc = getUserFromCompany();
        if(userdesc.equals("cd")){
        	setDefaultFolder();
        }
        //CCEnd SS1
    }

    /**
     * 界面信息本地化
     */
    private void localize() {
        if (resource == null)
            this.initResources();
        try {
            checkInJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "checking in", null));
            descriJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "describe", null));
            okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "OkJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
        } catch (Exception ex) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(this, message, title,
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * 设置检入的对象
     * 
     * @param workable
     *            检入的对象
     * @throws NotCheckedOutException
     * @throws QMException
     */
    public void setCheckInItem(WorkableIfc workable)
            throws NotCheckedOutException, QMException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInItem(1) begin...");
        //返回真的情况是：您尚未检出当前对象。它当前正被他人检出。
        if (!firstCheckIn
                && !CheckInOutCappTaskLogic.isCheckinAllowed(workable)) {
            Object aobj[] = {
                    RParentJPanel.getIdentity(workable),
                    RParentJPanel.getIdentity(workable),
                    CheckInOutCappTaskLogic.getCheckedOutBy(workable,
                            getContext().getLocale()) };
            throw new NotCheckedOutException(RESOURCE,
                    CappRouteRB.NOT_CHECKOUT_OWNER_DISPLAY, aobj);
        }

        setItem(workable);

        objectJLabel.setText(RParentJPanel.getIdentity(getCheckInItem()));

        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInItem(1) end...return is void");
    }

    /**
     * 设置要检入的对象
     * 
     * @param workable
     *            要检入的对象
     */
    protected void setItem(WorkableIfc workable) {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setItem() begin...");
        if (checkInItemHandle == null) {
            checkInItemHandle = new WorkableHandle(workable);
        } else {
            checkInItemHandle.setObject(workable);
        }
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setItem() end...return is void");
    }

    /**
     * 设置要检入的对象
     * 
     * @param workable
     *            要检入的对象
     * @param flag
     *            是否是第一次检入
     * @throws NotCheckedOutException
     * @throws QMException
     */
    public void setCheckInItem(WorkableIfc workable, boolean flag)
            throws NotCheckedOutException, QMException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInItem(2) begin...");
        firstCheckIn = flag;
        if (!firstCheckIn) {
            remove(folderPanel);
            doLayout();
        }
        setCheckInItem(workable);
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInItem(2) end...return is void");
    }

    /**
     * 返回当前准备检入的对象
     * 
     * @return WorkableIfc 准备检入的对象
     */
    public WorkableIfc getCheckInItem() {
        WorkableIfc gg;
        if (checkInItemHandle == null)
            return null;
        else
            return (WorkableIfc) checkInItemHandle.getObject();
    }

    /**
     * 设定对象准备检入到的文件夹，只有第一次检入的时候需要
     * 
     * @param folder
     *            对象准备检入的文件夹
     */
    public void setCheckInFolder(FolderIfc folder) {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInFolder() begin...");
        try {
            checkInFolder = folder;
            Class[] theClass = { FolderIfc.class };
            Object[] theObjs = { checkInFolder };
            String pathString = (String) CheckInOutCappTaskLogic
                    .useServiceMethod("FolderService", "getPath", theClass,
                            theObjs);
            folderPanel.setLabelText(pathString);
        } catch (QMRemoteException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInFolder() end...return is void");
    }

    /**
     * 获得准备检入的文件夹
     * 
     * @return FolderIfc 准备检入的文件夹
     * @throws QMRemoteException
     */
    public FolderIfc getCheckInFolder() throws QMRemoteException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.getCheckInFolder() begin...");
        FolderIfc folder = checkInFolder;
        if (folder == null && getCheckInItem() != null) {
            Class[] theClass = { FolderIfc.class };
            Object[] theObjs = { folder };
            folder = (FolderIfc) CheckInOutCappTaskLogic.useServiceMethod(
                    "FolderService", "getFolder", theClass, theObjs);
        }
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.getCheckInFolder() end...return: "
                            + folder);
        return folder;
    }

    /**
     * 确定按钮操作
     */
    private void processOkCommand() {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processOkCommand() begin...");
        try {
            setContext(true);
            setCursor(Cursor.getPredefinedCursor(3));
            enableActions(false);
            //执行检入
            if (getCheckInItem() != null) {
                //第一次检入
                if (firstCheckIn)
                    processFirstTimeCheckin();
                else
                    processCheckIn();
            }

            dispose();
        } catch (InterruptedException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), e
                    .getLocalizedMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (QMException e) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane
                    .showMessageDialog(this.getParentJFrame(), e
                            .getClientMessage(), title,
                            JOptionPane.INFORMATION_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
            enableActions(true);
            setContext(false);
        }
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processOkCommand() end...return is void");
    }

    /**
     * 指定按钮是否有效
     * 
     * @param flag
     */
    private void enableActions(boolean flag) {
        okJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }

    /**
     * 执行非首次检入
     * 
     * @throws InterruptedException
     * @throws QMException
     */
    private void processCheckIn() throws InterruptedException, QMException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processCheckIn() begin..."
                            + "检入项：" + getCheckInItem());
        //获得检入项的工作副本
        WorkableIfc workable = CheckInOutCappTaskLogic
                .getWorkingCopy(getCheckInItem());
        //获得检入项的工作原本
        //WorkableIfc original =
        // CheckInOutCappTaskLogic.getOriginalCopy(getCheckInItem());
        //这个flag失去了它的作用！！skybird,2005,1,20
        boolean flag = true;

        if (getCheckInItem() instanceof FormatContentHolder) {
            setVisible(false);
            //flag = replacePrimaryContent();
        }

        if (flag) {
            WorkableIfc technics = getCheckInItem();
            //skybird,2005.1.21,我认为检入检出小版本的变更在这里

            setItem(CheckInOutCappTaskLogic.checkInObject(technics,
                    descriJTextArea.getText()));
            if (true) {
                System.out.println("检入前的小版本：" + technics + "，检入后的小版本"
                        + getCheckInItem());
            }
            this.setVisible(false);

            //删除工作副本workable
            ((CappRouteListManageJFrame) parentJFrame).getTreePanel()
                    .removeNode(
                            new RouteListTreeObject((BaseValueInfo) workable));
            //删除原本
            //((CappRouteListManageJFrame)parentJFrame).getTreePanel().removeNode(
            //    new RouteListTreeObject((BaseValueInfo)original));
            //更新节点
            WorkableIfc newObj = getCheckInItem();
            RouteListTreeObject newTreeObj = new RouteListTreeObject(
                    (TechnicsRouteListInfo) newObj);
            ((CappRouteListManageJFrame) parentJFrame).getTreePanel().addNode(
                    newTreeObj);
            ((CappRouteListManageJFrame) parentJFrame).getTreePanel()
                    .setNodeSelected(newTreeObj);
            if (verbose)
                System.out
                        .println("capproute.view.RLCheckInJFrame.processCheckIn() end...return is void");
            return;
        }
        //skybird,2005,1,20这条分支永远也不会走到
        else {
            Object[] aobj = (new Object[] { RParentJPanel
                    .getIdentity(getCheckInItem()) });
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.CHECK_IN_FAILURE, aobj));
        }
    }

    /**
     * 执行首次检入
     * 
     * @throws PropertyVetoException
     * @throws VersionControlException
     * @throws QMRemoteException
     */
    private void processFirstTimeCheckin() throws PropertyVetoException,
            VersionControlException, QMRemoteException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processFirstTimeCheckin1() begin...");
        if (checkInFolder == null && folderPanel.getFolderLocation() == null) {
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.NO_LOCATION_ENTERED, null));
        }

        WorkableIfc workable = getCheckInItem();
        boolean flag = true;
        if (getCheckInItem() instanceof FormatContentHolder) {
            setVisible(false);
            //flag = replacePrimaryContent();
        }
        //skybird,2005.1.20,这一条是必须执行
        if (flag) {
            if (checkInFolder != null)
                setItem(CheckInOutCappTaskLogic.checkInObject(getCheckInItem(),
                        checkInFolder, descriJTextArea.getText().trim()));
            else
                setItem(CheckInOutCappTaskLogic.checkInObject(getCheckInItem(),
                        folderPanel.getFolderLocation().trim(), descriJTextArea
                                .getText().trim()));

            //创建新节点
            BaseValueInfo checkinInfo = (BaseValueInfo) getCheckInItem();
            //如果检入的是工艺路线列表
            if (checkinInfo instanceof TechnicsRouteListInfo) {

                //删除旧节点对象
                ((CappRouteListManageJFrame) parentJFrame).getTreePanel()
                        .removeNode(new RouteListTreeObject(workable));
                RouteListTreeObject treeObject = new RouteListTreeObject(
                        checkinInfo);
                ((CappRouteListManageJFrame) parentJFrame).getTreePanel()
                        .addNode(treeObject);
                ((CappRouteListManageJFrame) parentJFrame).getTreePanel()
                        .setNodeSelected(treeObject);
            }
        }
        //这条分支永远不会执行
        else {
            throw new QMRemoteException();
        }
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processFirstTimeCheckin() end...return is void");
    }

    /**
     * 确定按钮的操作
     * 
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        processOkCommand();
    }

    /**
     * 取消按钮的操作
     * 
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        processCancelCommand();
    }

    /**
     * 执行取消操作
     */
    private void processCancelCommand() {
        try {
            setContext(true);
            dispose();
        } finally {
            setContext(false);
        }
    }

    /**
     * 获得父窗口
     * 
     * @return 父窗口
     */
    public JFrame getParentJFrame() {
        JFrame frame = null;
        if (getParent() instanceof JFrame)
            frame = (JFrame) getParent();

        return frame;
    }

    /**
     * 确定按钮的键盘操作
     * 
     * @param e
     *            KeyEvent
     */
    void okJButton_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10)
            processOkCommand();
    }

    /**
     * 取消按钮的键盘操作
     * 
     * @param e
     *            KeyEvent
     */
    void cancelJButton_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10)
            processCancelCommand();
    }

    /**
     * 测试本类
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        RLCheckInJFrame checkInCappJFrame = new RLCheckInJFrame(null);
        checkInCappJFrame.setVisible(true);
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
     * 业务对象处理内部类
     */
    class WorkableHandle implements ReferenceHolder {
        WorkableIfc workable;

        public void setObject(Object obj) {
            if (obj instanceof WorkableIfc)
                workable = (WorkableIfc) obj;
        }

        public Object getObject() {
            return workable;
        }

        public WorkableHandle(WorkableIfc workable1) {
            workable = workable1;
        }
    }

    /**
     * 获得在线
     * 
     * @return
     */
    private QMCt getContext() {
        QMCt qmcontext = null;
        if (contextGroup != null)
            qmcontext = QMCt.getContext(contextGroup);

        if (qmcontext == null)
            qmcontext = QMCt.getContext();
        return qmcontext;
    }

    /**
     * 设置在线
     * 
     * @param flag
     */
    private void setContext(boolean flag) {
        if (flag) {
            if (contextGroup != null) {
                QMCt.setContextGroup(contextGroup);
                return;
            }

            QMCt.setContext(this);
            return;

        } else {
            QMCt.setContext(null);
            return;
        }
    }

    /**
     * 初始化所使用的资源绑定信息类
     */
    protected void initResources() {
        try {
            if (resource == null) {
                resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
                        .getLocale());

            }
        } catch (MissingResourceException mre) {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                    null);
            JOptionPane.showMessageDialog(null, QMMessage.getLocalizedMessage(
                    RESOURCE, CappRouteRB.MISSING_RESOURCER, null), title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
    //CCBegin SS1
    /**
     * 设置默认资料夹
     */
    public void setDefaultFolder(){
    	folderPanel.setLabelText("\\Root\\成都分厂\\成都工艺路线");
    }
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	RequestServer server = RequestServerFactory.getRequestServer();;
    	String returnStr = "";
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         System.out.println("用户组===="+returnStr);
         return returnStr;
    }
    //CCEnd SS1
}