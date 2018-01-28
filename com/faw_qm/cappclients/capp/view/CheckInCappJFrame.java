/** ���ɳ���CheckInCappJFrame.java	1.1  2003/08/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ������Ĺ����ļ������ pante 2014-03-27
 * SS2 ��SS1���������ֻ����������û���ȥ���������� liunan 2014-11-3
 * SS3 ƽ̨���⣺A034-2015-0227 ȡ��������Ĺ����ļ�������Զ����ɡ� liunan 2015-4-13
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.NotCheckedOutException;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.clients.beans.explorer.ReferenceHolder;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.content.ejb.service.FormatContentHolder;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.zczx.util.ZCZXUtil;

/**
 * <p>Title: ���빤�ս���</p>
 * <p>��һ�������������ɵĹ���ͷ������빲�����ϼ��У��´�������ͷ��Ϣ�״μ���ʱ</p>
 * <p>��Ҫָ�����ϼ�·���� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 */

public class CheckInCappJFrame extends JDialog implements TechnicsViewIfc
{
	static boolean fileVaultUsed = (RemoteProperty.getProperty("registryFileVaultStoreMode", "true")).equals("true");
    /**�����ǩ*/
    private JLabel checkInJLabel = new JLabel();


    /**��������ǩ*/
    private JLabel objectJLabel = new JLabel();


    /**���ϼ�*/
    private FolderPanel folderPanel = new FolderPanel();


    /**ע��*/
    private JLabel descriJLabel = new JLabel();
    private JScrollPane descriJScrollPane = new JScrollPane();
    private JTextArea descriJTextArea = new JTextArea();


    /**��ť���*/
    private JPanel buttonJPanel = new JPanel();


    /**ȷ����ť*/
    private JButton okJButton = new JButton();


    /**ȡ����ť*/
    private JButton cancelJButton = new JButton();


    /**״̬��*/
    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private WorkableHandle checkInItemHandle;


    /**���ϼж���*/
    private FolderIfc checkInFolder;


    /**��ǣ��Ƿ��״μ���*/
    private boolean firstCheckIn;


    /**��ǰ�߳�*/
    private ThreadGroup contextGroup;


    /**���ڱ����Դ�ļ�·��*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**���ڱ����Դ*/
    protected static ResourceBundle resource = null;


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JFrame parentJFrame;
    private CappTreeNode parentNode;
    private QMTechnicsInfo parentTechnics;
   // private ProgressDialog progressDialog;

    /**
     * ���캯��
     */
    public CheckInCappJFrame(JFrame frame)
    {
        super(frame, true);
        try
        {
            jbInit();
            parentJFrame = frame;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * ҵ��������ڲ���
     */
    class WorkableHandle implements ReferenceHolder
    {
        WorkableIfc workable;
        public void setObject(Object obj)
        {
            if (obj instanceof WorkableIfc)
            {
                workable = (WorkableIfc) obj;
            }
        }

        public Object getObject()
        {
            return workable;
        }

        public WorkableHandle(WorkableIfc workable1)
        {
            workable = workable1;
        }
    }


    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        //{{��ʼ��FolderPanel :�������ϼ� �޸�Ȩ��
        folderPanel.setIsPersonalFolder(false);
        folderPanel.setIsPublicFolders(true);
        folderPanel.setPermission("modify");
        //}}
        firstCheckIn = false;
        contextGroup = QMCt.getContext().getThreadGroup();
        setSize(400, 300);
        String sTitle = QMMessage.getLocalizedMessage(RESOURCE,
                "checkInTitle", null);
        setTitle(sTitle);
        checkInJLabel.setMaximumSize(new Dimension(48, 22));
        checkInJLabel.setMinimumSize(new Dimension(48, 22));
        checkInJLabel.setPreferredSize(new Dimension(48, 22));
        checkInJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        checkInJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        checkInJLabel.setText("���ڼ���");
        getContentPane().setLayout(gridBagLayout2);
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
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��");
        okJButton.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                okJButton_keyPressed(e);
            }
        });
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
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��");
        cancelJButton.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                cancelJButton_keyPressed(e);
            }
        });
        cancelJButton.addActionListener(new java.awt.event.
                                        ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        buttonJPanel.add(cancelJButton, new GridBagConstraints(1, 0,
                1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        getContentPane().add(descriJLabel,
                             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(8, 0, 0, 0), 0, 0));
        getContentPane().add(descriJScrollPane,
                             new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(8, 8, 0, 18), 0, 0));
        getContentPane().add(folderPanel,
                             new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(8, 24, 0, 18),
                0, 0));
        getContentPane().add(objectJLabel,
                             new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.SOUTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(20, 8, 0, 18),
                0, 0));
        getContentPane().add(checkInJLabel,
                             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                new Insets(20, 17, 0, 0), 0, 0));
        descriJScrollPane.getViewport().add(descriJTextArea, null);
        getContentPane().add(statusJLabel,
                             new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(40, 0, 0, 0), 0, 14));
        getContentPane().add(buttonJPanel,
                             new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(20, 148, 0, 18), 0, 0));
        //setIconImage(new ImageIcon(getClass().getResource(
        //          "/images/public_checkInObj.gif")).getImage());
        localize();
    }


    /**
     * ������Ϣ���ػ�
     */
    private void localize()
    {
        if (resource == null)
        {
            initResources();
        }
        try
        {
            checkInJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "checking in", null));
            descriJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "describe", null));
            okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "OkJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "CancelJButton", null));
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(this, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }


    /**
     *��ʼ����ʹ�õ���Դ����Ϣ��
     */
    protected void initResources()
    {
        try
        {
            if (resource == null)
            {
                resource = ResourceBundle.getBundle(RESOURCE,
                        QMCt.getContext().getLocale());

            }
        }
        catch (MissingResourceException mre)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(null,
                                          QMMessage.getLocalizedMessage(
                    RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }


    /**
     * ȷ����ť�Ĳ���
     * @param e
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        WorkThread thread = new WorkThread();
        thread.start();
    }


    /**
     * ȡ����ť�Ĳ���
     * @param e
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        processCancelCommand();
    }


    /**
     * �������
     * @return
     */
    private QMCt getContext()
    {
        QMCt qmcontext = null;
        if (contextGroup != null)
        {
            qmcontext = QMCt.getContext(contextGroup);

        }
        if (qmcontext == null)
        {
            qmcontext = QMCt.getContext();
        }
        return qmcontext;
    }


    /**
     * ��������
     * @param flag
     */
    private void setContext(boolean flag)
    {
        if (flag)
        {
            if (contextGroup != null)
            {
                QMCt.setContextGroup(contextGroup);
                return;
            }

            QMCt.setContext(this);
            return;

        }
        else
        {
            QMCt.setContext(null);
            return;
        }
    }


    /**
     * ִ��ȡ������
     */
    private void processCancelCommand()
    {
        try
        {
            setContext(true);
            dispose();
        }
        finally
        {
            setContext(false);
        }
    }


    /**
     * ���빤��ʱ����Ҫ���ñ���������ø��ڵ㡣
     * @param pnode ���빤��ĸ��ڵ�
     */
    public void setSelectedProcedureParent(CappTreeNode pnode)
    {
        parentNode = pnode;
    }


    /**
     * ���ü���Ķ���
     * @param workable ����Ķ���
     * @throws QMPropertyVetoException
     */
    public void setCheckInItem(WorkableIfc workable)
            throws NotCheckedOutException, QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setCheckInItem(1) begin...");
        }
        setItem(workable);

        objectJLabel.setText(getIdentity(getCheckInItem()));

        //�ǲ����ĵ���������
        if (getCheckInItem() instanceof FormatContentHolder)
        {
            //initPrimaryContentsPanel(firstCheckIn);
        }
        else
        {
            doLayout();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setCheckInItem(1) end...return is void");
        }
    }


    /**
     * ����Ҫ����Ķ���
     * @param workable Ҫ����Ķ���
     */
    protected void setItem(WorkableIfc workable)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setItem() begin...");
        }
        if (checkInItemHandle == null)
        {
            checkInItemHandle = new WorkableHandle(workable);
        }
        else
        {
            checkInItemHandle.setObject(workable);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setItem() end...return is void");
        }
    }


    /**
     * ����Ҫ����Ķ���
     * @param workable Ҫ����Ķ���
     * @param flag �Ƿ��ǵ�һ�μ���
     * @throws QMPropertyVetoException
     */
    public void setCheckInItem(WorkableIfc workable, boolean flag)
            throws NotCheckedOutException, QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setCheckInItem(2) begin...");
        }
        firstCheckIn = flag;
        if (!firstCheckIn)
        {
            remove(folderPanel);
            doLayout();
        }
        setCheckInItem(workable);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setCheckInItem(2) end...return is void");
        }
    }


    /**
     * ���ص�ǰ׼������Ķ���
     * @return WorkableIfc ׼������Ķ���
     */
    public WorkableIfc getCheckInItem()
    {

        if (checkInItemHandle == null)
        {
            return null;
        }
        else
        {
            return (WorkableIfc) checkInItemHandle.getObject();
        }
    }


    /**
     * �趨����׼�����뵽���ļ��У�ֻ�е�һ�μ����ʱ����Ҫ
     * @param folder ����׼��������ļ���
     * */
    public void setCheckInFolder(FolderIfc folder)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.setCheckInFolder() begin...");
        }
        try
        {
            checkInFolder = folder;
            Class[] theClass =
                    {
                    FolderIfc.class};
            Object[] theObjs =
                    {
                    checkInFolder};
            String pathString = (String) CheckInOutCappTaskLogic.
                                useServiceMethod(
                    "FolderService", "getPath", theClass, theObjs);
            folderPanel.setLabelText(pathString);
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(this, e.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.CheckInCappJFrame.setCheckInFolder() end...return is void");
        }
    }


    /**
     * ���׼��������ļ���
     * @return FolderIfc ׼��������ļ���
     */
    public FolderIfc getCheckInFolder()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.getCheckInFolder() begin...");
        }
        FolderIfc folder = checkInFolder;
        if (folder == null && getCheckInItem() != null)
        {
            Class[] theClass =
                    {
                    FolderIfc.class};
            Object[] theObjs =
                    {
                    folder};
            folder = (FolderIfc) CheckInOutCappTaskLogic.
                     useServiceMethod(
                    "FolderService", "getFolder", theClass, theObjs);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.getCheckInFolder() end...return: " +
                    folder);
        }
        return folder;
    }


    /**
     * ȷ����ť����
     */
    private void processOkCommand()
    {
         if(this.firstCheckIn&&this.folderPanel.getFolderLocation()==null)
         {
             String message= QMMessage.getLocalizedMessage(RESOURCE,"6",null);
             JOptionPane.showMessageDialog(this,message,
                                           QMMessage.getLocalizedMessage(RESOURCE,"information",null),
                                           JOptionPane.INFORMATION_MESSAGE);
             return;
         }
        setVisible(false);
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.processOkCommand() begin...");
        }
        try
        {
            setContext(true);
            ((TechnicsRegulationsMainJFrame) parentJFrame).setCursor(Cursor.
                    WAIT_CURSOR);
            enableActions(false);
            //ִ�м���
            if (getCheckInItem() != null)
            {
                //��һ�μ���
                if (firstCheckIn)
                {
                    processFirstTimeCheckin(getCheckInItem());
                }
                else
                {
                    processCheckIn();
                }
            }

        }
        catch (InterruptedException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          e.getLocalizedMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        catch (PropertyVetoException e)
        {
            e.printStackTrace();
        }
        catch (QMException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          e.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        finally
        {

            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            ((TechnicsRegulationsMainJFrame) parentJFrame).setCursor(Cursor.
                    getDefaultCursor());
            dispose();
            // setContext(false);
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.CheckInCappJFrame.processOkCommand() end...return is void");
        }
    }


    /**
     * ˢ��ҵ�����
     * @param i
     * @param obj
     */
    private void dispatchRefresh(int i, Object obj)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.dispatchRefresh() begin...");
        }
        if (obj != null)
        {
            RefreshEvent refreshevent = new RefreshEvent(this, i, obj);
            RefreshService.getRefreshService().dispatchRefresh(
                    refreshevent);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.dispatchRefresh() end...return is void");
        }
    }


    /**
     * ָ����ť�Ƿ���Ч
     * @param flag
     */
    private void enableActions(boolean flag)
    {
        okJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }


    /**
     * �������
     * @return
     */
    private String getType()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.getType() begin...");
        }
        String s = "";
        PropertyDescriptor propertydescriptor =
                getReadPropertyDescriptor("type");
        if (propertydescriptor != null)
        {
            Method method = propertydescriptor.getReadMethod();
            if (method != null)
            {
                try
                {
                    Object obj = method.invoke(getCheckInItem(), null);
                    if (obj instanceof String)
                    {
                        s = (String) obj;
                    }
                    else
                    {
                        s = obj.toString();
                    }
                }
                catch (Exception _ex)
                {
                    s = "---";
                }
            }
        }
        else
        {
            StringTokenizer stringtokenizer = new StringTokenizer(
                    getCheckInItem().getClass().getName(), ".");
            int i = 0;
            for (int j = stringtokenizer.countTokens(); i < j; i++)
            {
                s = stringtokenizer.nextToken();

            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.getType() end...return: " +
                    s);
        }
        return s;
    }


    /**
     * �����������
     * @param s
     * @return
     */
    private PropertyDescriptor getReadPropertyDescriptor(String s)
    {
        PropertyDescriptor propertydescriptor = null;
        try
        {
            Class classinfo = getCheckInItem().getClass();
            if (classinfo != null)
            {
                propertydescriptor = new PropertyDescriptor(s,
                        classinfo);
            }
        }
        catch (Exception _ex)
        {
            propertydescriptor = null;
        }
        return propertydescriptor;
    }


    /**
     * ȷ����ť�ļ��̲���
     * @param e
     */
    void okJButton_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == 10)
        {
            WorkThread thread = new WorkThread();
            thread.start();
        }
    }


    /**
     * ȡ����ť�ļ��̲���
     * @param e
     */
    void cancelJButton_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == 10)
        {
            processCancelCommand();
        }
    }


    /**
     * ִ�з��״μ���
     * @throws InterruptedException
     * @throws QMException
     */
    private void processCheckIn()
            throws InterruptedException, QMException
    {
//        if (verbose)
//        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.processCheckIn() begin..."
                    + "�����" + getCheckInItem());
            //��ü�����Ĺ�������
//        }
        WorkableIfc workable = CheckInOutCappTaskLogic.getWorkingCopy(
                getCheckInItem());
        //��ü�����Ĺ���ԭ��
        //WorkableIfc original = CheckInOutCappTaskLogic.
        //                    getOriginalCopy(getCheckInItem());
        boolean flag = true;

        if (flag)
        {
            // WorkableIfc technics = getCheckInItem();
            if (verbose)
            {
                System.out.println("���տ���" +
                                   getParentTechnics().getBsoID());
            }

            Class[] paraClass =
                    {
                    WorkableIfc.class, String.class};
            Object[] objs =
                    {
                    workable, descriJTextArea.getText()};

            QMFawTechnicsIfc tech = (QMFawTechnicsIfc) TechnicsAction.
                                    useServiceMethod(
                    "StandardCappService", "checkIn",
                    paraClass, objs);
            if (verbose)
            {
                System.out.println("cccccccccc ������" + getCheckInItem());
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            setVisible(false);

            //ɾ����������workable
            ((TechnicsRegulationsMainJFrame) parentJFrame).removeNode((
                    BaseValueInfo) workable);

            //���½ڵ�

            ((TechnicsRegulationsMainJFrame) parentJFrame).addProcess((
                    QMTechnicsInfo) tech);
            ((TechnicsRegulationsMainJFrame) parentJFrame).
                    getProcessTreePanel()
                    .setNodeSelected(new TechnicsTreeObject((
                    QMTechnicsInfo) tech));
            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.view.CheckInCappJFrame.processCheckIn() end...return is void");
            }
            //CCBegin SS1
            //CCBegin SS2
            //CCBegin SS3
            /*if(CappClientHelper.getUserFromCompany().equals("zczx"))
            {
            //CCEnd SS2
            try {
            	System.out.println("111111111111111111111111111111111");
            	Class[] paraclass = {QMTechnicsIfc.class};
            	Object[] paraobj = {tech};
            	Vector vc = (Vector) useServiceMethod("StandardCappService", "createCompare", paraclass,paraobj);
            	System.out.println("222222222222222222222222222222222");
            	CappClientHelper.createCompare(vc,tech);
            } catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            //CCBegin SS2
            }*/
            //CCEnd SS3
            //CCEnd SS2
          //CCEnd SS1
            return;
        }
        else
        {
            Object[] aobj = (new Object[]
                             {
                             getIdentity(getCheckInItem())
            });
            throw new QMRemoteException(
                    QMMessage.getLocalizedMessage(RESOURCE,
                                                  CappLMRB.CHECK_IN_FAILURE,
                                                  aobj));
        }
    }


    /**
     * ִ���״μ���
     * @param workable
     * @throws PropertyVetoException
     * @throws VersionControlException
     * @throws QMException
     */
    private void processFirstTimeCheckin(WorkableIfc workable)
            throws PropertyVetoException, QMException
    {

        try
        {

            Class[] paraClass =
                    {
                    WorkableIfc.class, String.class, String.class};
            Object[] objs =
                    {
                    workable, folderPanel.getFolderLocation(),
                    descriJTextArea.getText()};

            QMFawTechnicsIfc tech = (QMFawTechnicsIfc) TechnicsAction.
                                    useServiceMethod(
                    "StandardCappService", "firstTimeCheckIn",
                    paraClass, objs);
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //ɾ���ɽڵ����
            ((TechnicsRegulationsMainJFrame) parentJFrame).
                    removeNode(
                    (BaseValueInfo) workable);
            ((TechnicsRegulationsMainJFrame) parentJFrame).
                    addProcess(
                    (QMTechnicsInfo) tech);
            ((TechnicsRegulationsMainJFrame) parentJFrame).
                    getProcessTreePanel()
                    .setNodeSelected(new TechnicsTreeObject((
                    QMTechnicsInfo) tech));

        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            throw e;
        }

    }


    /**
     * ��ø�����
     * @return ������
     */
    public JFrame getParentJFrame()
    {
        JFrame frame = null;
        if (getParent() instanceof JFrame)
        {
            frame = (JFrame) getParent();

        }
        return frame;
    }


    /**
     * ���½���(ģ�ͷ����仯ʱ֪ͨ���棬�ú���������)
     * ���ý�����¼�������
     * @param observable ���۲��߶���
     * @param obj ���۲�������Ĳ���
     */
    public void update(Observable observable, Object obj)
    {
    }


    /**
     * ���ָ��ҵ�����Ķ������ͺͱ�ʶ
     * @param obj ҵ�����
     * @return �������ͺͱ�ʶ
     */
    public static String getIdentity(Object obj)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.getIdentity() begin...");
        }
        String s = "";
        if (obj instanceof BaseValueInfo)
        {
            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
            DisplayIdentity displayidentity = IdentityFactory.
                                              getDisplayIdentity(obj);
            //��ö������� + ��ʶ
            s = displayidentity.getLocalizedMessage(null).trim();

        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.CheckInCappJFrame.getIdentity() end...return: " +
                    s);
        }
        return s;
    }


    /**
     * ���Ա���
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.
                                     getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
        }
        CheckInCappJFrame checkInCappJFrame = new CheckInCappJFrame(null);
        checkInCappJFrame.setVisible(true);
    }


    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().
                               getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);

    }


    /**
     * ���ظ��෽����ʹ������ʾ����Ļ����
     * @param flag
     */
    public void setVisible()
    {
        setViewLocation();
        setVisible(true);
    }


    /**
     * ��ü�����������Ĺ��տ�
     * @return QMTechnicsInfo ������������Ĺ��տ�
     */
    public QMTechnicsInfo getParentTechnics()
    {
        return parentTechnics;
    }
    
    public static Object useServiceMethod(String serviceName, String methodName,Class[] paraClass,Object[] paraObject) throws QMRemoteException
    {
    	if (verbose)
    	{
    		System.out.println(
    				"cappclients.capp.util.CappClientHelper.useServiceMethod() begin...");
    	}
    	RequestServer server = RequestServerFactory.getRequestServer();
    	ServiceRequestInfo info1 = new ServiceRequestInfo();
    	info1.setServiceName(serviceName);
    	info1.setMethodName(methodName);
    	Class[] paraClass1 = paraClass;
    	info1.setParaClasses(paraClass1);
    	Object[] objs1 = paraObject;
    	info1.setParaValues(objs1);
    	Object obj = null;
    	obj = server.request(info1);
    	if (verbose)
    	{
    		System.out.println(
    				"cappclients.capp.util.CappClientHelper.useServiceMethod() end...return : " +
    						obj);
    	}
    	return obj;
    }
    
    /**
     *
     * <p>Title: �����߳� </p>
     * <p>Description: �����߳�</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author not attributable
     * @version 1.0
     */
    class WorkThread extends Thread
    {
        public void run()
        {
            processOkCommand();
        }
    }
}
