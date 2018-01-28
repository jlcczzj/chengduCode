/** 
 * ���ɳ���RLCheckInJFrame.java	1.1  2004/02/29
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 2009/06/08 ��ѧ��  ԭ�򣺽������빤��·�߱��Ľ����ɷ�ģ̬�޸�Ϊģ̬
 * SS1 �ɶ����ü���Ĭ�����ϼ� liuyuzhu 2016-10-12
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
 * Title: ���빤��·�߱����
 * </p>
 * <p>
 * ��һ�������������ɵĹ���·�߱������빲�����ϼ��У��´�������ͷ��Ϣ�״μ���ʱ
 * </p>
 * <p>
 * ��Ҫָ�����ϼ�·����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * 
 * @author ����
 * @version 1.0
 */

public class RLCheckInJFrame extends JDialog //CR1
{
    /** �����ǩ */
    private JLabel checkInJLabel = new JLabel();

    /** ��������ǩ */
    private JLabel objectJLabel = new JLabel();

    /** ���ϼ� */
    private FolderPanel folderPanel = new FolderPanel();

    /** ע�� */
    private JLabel descriJLabel = new JLabel();

    private JScrollPane descriJScrollPane = new JScrollPane();

    private JTextArea descriJTextArea = new JTextArea();

    /** ��ť��� */
    private JPanel buttonJPanel = new JPanel();

    /** ȷ����ť */
    private JButton okJButton = new JButton();

    /** ȡ����ť */
    private JButton cancelJButton = new JButton();

    /** ״̬�� */
    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private WorkableHandle checkInItemHandle;

    /** ���ϼж��� */
    private FolderIfc checkInFolder;

    /** ��ǣ��Ƿ��״μ��� */
    private boolean firstCheckIn;

    /** ��ǰ�߳� */
    private ThreadGroup contextGroup;

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** ���ڱ����Դ */
    protected static ResourceBundle resource = null;

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private JFrame parentJFrame;

    /**
     * ���캯��
     * 
     * @param frame
     *            ������
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
     * �����ʼ��
     * 
     * @throws Exception
     */
    private void jbInit() throws Exception {
        URL url = CappRouteListManageJFrame.class
                .getResource("/images/routeM.gif");
        if (url != null) {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));

        }

        //{{��ʼ��FolderPanel :�������ϼ� �޸�Ȩ��
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
        checkInJLabel.setText("���ڼ���");
        this.getContentPane().setLayout(gridBagLayout2);
        objectJLabel.setMaximumSize(new Dimension(48, 22));
        objectJLabel.setMinimumSize(new Dimension(48, 22));
        objectJLabel.setPreferredSize(new Dimension(48, 22));
        descriJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        descriJLabel.setText("ע��");
        buttonJPanel.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
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
        cancelJButton.setText("ȡ��(C)");
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
        statusJLabel.setText("���ڼ��빤��·�߱�");
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
     * ������Ϣ���ػ�
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
     * ���ü���Ķ���
     * 
     * @param workable
     *            ����Ķ���
     * @throws NotCheckedOutException
     * @throws QMException
     */
    public void setCheckInItem(WorkableIfc workable)
            throws NotCheckedOutException, QMException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.setCheckInItem(1) begin...");
        //�����������ǣ�����δ�����ǰ��������ǰ�������˼����
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
     * ����Ҫ����Ķ���
     * 
     * @param workable
     *            Ҫ����Ķ���
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
     * ����Ҫ����Ķ���
     * 
     * @param workable
     *            Ҫ����Ķ���
     * @param flag
     *            �Ƿ��ǵ�һ�μ���
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
     * ���ص�ǰ׼������Ķ���
     * 
     * @return WorkableIfc ׼������Ķ���
     */
    public WorkableIfc getCheckInItem() {
        WorkableIfc gg;
        if (checkInItemHandle == null)
            return null;
        else
            return (WorkableIfc) checkInItemHandle.getObject();
    }

    /**
     * �趨����׼�����뵽���ļ��У�ֻ�е�һ�μ����ʱ����Ҫ
     * 
     * @param folder
     *            ����׼��������ļ���
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
     * ���׼��������ļ���
     * 
     * @return FolderIfc ׼��������ļ���
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
     * ȷ����ť����
     */
    private void processOkCommand() {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processOkCommand() begin...");
        try {
            setContext(true);
            setCursor(Cursor.getPredefinedCursor(3));
            enableActions(false);
            //ִ�м���
            if (getCheckInItem() != null) {
                //��һ�μ���
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
     * ָ����ť�Ƿ���Ч
     * 
     * @param flag
     */
    private void enableActions(boolean flag) {
        okJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }

    /**
     * ִ�з��״μ���
     * 
     * @throws InterruptedException
     * @throws QMException
     */
    private void processCheckIn() throws InterruptedException, QMException {
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processCheckIn() begin..."
                            + "�����" + getCheckInItem());
        //��ü�����Ĺ�������
        WorkableIfc workable = CheckInOutCappTaskLogic
                .getWorkingCopy(getCheckInItem());
        //��ü�����Ĺ���ԭ��
        //WorkableIfc original =
        // CheckInOutCappTaskLogic.getOriginalCopy(getCheckInItem());
        //���flagʧȥ���������ã���skybird,2005,1,20
        boolean flag = true;

        if (getCheckInItem() instanceof FormatContentHolder) {
            setVisible(false);
            //flag = replacePrimaryContent();
        }

        if (flag) {
            WorkableIfc technics = getCheckInItem();
            //skybird,2005.1.21,����Ϊ������С�汾�ı��������

            setItem(CheckInOutCappTaskLogic.checkInObject(technics,
                    descriJTextArea.getText()));
            if (true) {
                System.out.println("����ǰ��С�汾��" + technics + "��������С�汾"
                        + getCheckInItem());
            }
            this.setVisible(false);

            //ɾ����������workable
            ((CappRouteListManageJFrame) parentJFrame).getTreePanel()
                    .removeNode(
                            new RouteListTreeObject((BaseValueInfo) workable));
            //ɾ��ԭ��
            //((CappRouteListManageJFrame)parentJFrame).getTreePanel().removeNode(
            //    new RouteListTreeObject((BaseValueInfo)original));
            //���½ڵ�
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
        //skybird,2005,1,20������֧��ԶҲ�����ߵ�
        else {
            Object[] aobj = (new Object[] { RParentJPanel
                    .getIdentity(getCheckInItem()) });
            throw new QMRemoteException(QMMessage.getLocalizedMessage(RESOURCE,
                    CappRouteRB.CHECK_IN_FAILURE, aobj));
        }
    }

    /**
     * ִ���״μ���
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
        //skybird,2005.1.20,��һ���Ǳ���ִ��
        if (flag) {
            if (checkInFolder != null)
                setItem(CheckInOutCappTaskLogic.checkInObject(getCheckInItem(),
                        checkInFolder, descriJTextArea.getText().trim()));
            else
                setItem(CheckInOutCappTaskLogic.checkInObject(getCheckInItem(),
                        folderPanel.getFolderLocation().trim(), descriJTextArea
                                .getText().trim()));

            //�����½ڵ�
            BaseValueInfo checkinInfo = (BaseValueInfo) getCheckInItem();
            //���������ǹ���·���б�
            if (checkinInfo instanceof TechnicsRouteListInfo) {

                //ɾ���ɽڵ����
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
        //������֧��Զ����ִ��
        else {
            throw new QMRemoteException();
        }
        if (verbose)
            System.out
                    .println("capproute.view.RLCheckInJFrame.processFirstTimeCheckin() end...return is void");
    }

    /**
     * ȷ����ť�Ĳ���
     * 
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        processOkCommand();
    }

    /**
     * ȡ����ť�Ĳ���
     * 
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        processCancelCommand();
    }

    /**
     * ִ��ȡ������
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
     * ��ø�����
     * 
     * @return ������
     */
    public JFrame getParentJFrame() {
        JFrame frame = null;
        if (getParent() instanceof JFrame)
            frame = (JFrame) getParent();

        return frame;
    }

    /**
     * ȷ����ť�ļ��̲���
     * 
     * @param e
     *            KeyEvent
     */
    void okJButton_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10)
            processOkCommand();
    }

    /**
     * ȡ����ť�ļ��̲���
     * 
     * @param e
     *            KeyEvent
     */
    void cancelJButton_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10)
            processCancelCommand();
    }

    /**
     * ���Ա���
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
     * ҵ��������ڲ���
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
     * �������
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
     * ��������
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
     * ��ʼ����ʹ�õ���Դ����Ϣ��
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
     * ����Ĭ�����ϼ�
     */
    public void setDefaultFolder(){
    	folderPanel.setLabelText("\\Root\\�ɶ��ֳ�\\�ɶ�����·��");
    }
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
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
         System.out.println("�û���===="+returnStr);
         return returnStr;
    }
    //CCEnd SS1
}