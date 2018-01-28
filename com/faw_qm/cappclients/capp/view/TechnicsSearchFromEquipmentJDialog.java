/** 生成程序TechnicsSearchJDialog.java  1.1 
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CCBegin by liunan 2012-04-06 打补丁 v4r3_p043
 * CR1 2012/02/22 吕航 原因：参见TD2497
 * CCEnd by liunan 2012-04-06
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.clients.beans.query.QMStatus;
import com.faw_qm.clients.util.MultiList;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.util.TextAndImageCell;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.util.QMCt;
import com.faw_qm.wip.model.WorkableIfc;
import java.awt.*;



/**
 * <p>Title:按工装搜索典型工艺界面 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 */

public class TechnicsSearchFromEquipmentJDialog extends JDialog
{
    private JPanel panel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private TitledBorder titledBorder1;
    private JPanel queryConditionPanel = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    //modify by sdg on 20061114 (按设备编号,工装编号,材料编号搜索,输入带*等编号时,如*1时要有相应提示.)
    //private JTextField equipmentNumTextField = new JTextField();
    private CappTextField equipmentNumTextField;
    //modify end
    private JTextField versionTextField = new JTextField();
    private JComboBox stateComboBox = new JComboBox();
    private JCheckBox typeCheckBox = new JCheckBox();
    private JCheckBox versionCheckBox = new JCheckBox();
    private JCheckBox stateCheckBox = new JCheckBox();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel searchButtonPanel = new JPanel();
    private TitledBorder titledBorder2;
    private JButton searchButton = new JButton();
    private JPanel okButtonPanel = new JPanel();
    private TitledBorder titledBorder3;
    private JButton okButton = new JButton();
    private JButton quitButton = new JButton();
    private TitledBorder titledBorder4;
    private JButton stopButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private MultiList multiList1 = new MultiList();
    private JCheckBox appendResultCheckBox = new JCheckBox();
    //2007.05.21 xucy add
    private JCheckBox ignoreCaseCheckBox = new JCheckBox();
    private JPanel appendPanel = new JPanel();


    /**用于标记资源文件路径*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**用于标记资源信息*/
    protected static ResourceBundle resource = null;


    /**中止进程标志*/
    protected boolean cancelInProgress = false;
    private QMStatus qMStatus = new QMStatus();


    /**检索控制类*/
    private ToolLinkSearchThread searchThread;


    /**线程组*/
    private static ThreadGroup theThreadGroup =
            Thread.currentThread().getThreadGroup();


    /**用于存放生命周期状态，关键字：状态的中文名，对象：状态*/
    private Hashtable table;
    private TechnicsRegulationsMainJFrame view;


    /** 标准图标*/
    private static ImageIcon startandImage;


    /** 检出图标*/
    private static ImageIcon checkOutImage;


    /** 原本图标*/
    private static ImageIcon workingImage;
    private SortingSelectedPanel technicsTypeSortingSelectedPanel = null;
    private PaginatePanel paginatePanel = new PaginatePanel();


    /**存放multiList中的对象*/
    private Vector resultVector = new Vector();


    /**记录上一次查询结果的数量*/
    private int lastNum = 0;
  GridBagLayout gridBagLayout6 = new GridBagLayout();
    public TechnicsSearchFromEquipmentJDialog(TechnicsRegulationsMainJFrame
                                              frame)
    {
        super(frame, "", true);
        view = frame;
        try
        {
            jbInit();
            pack();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void jbInit()
            throws Exception
    {
        String title1 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.TECHNICSTYPE, null);
        technicsTypeSortingSelectedPanel = new SortingSelectedPanel(title1,
                "QMTechnicsMaster", "technicsType");
        technicsTypeSortingSelectedPanel.setDialogTitle(title1);
        technicsTypeSortingSelectedPanel.setButtonSize(89, 23);
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder("");
        panel1.setLayout(gridBagLayout4);
        getContentPane().setLayout(gridBagLayout5);
        panel1.setToolTipText("");
        jLabel1.setText("*设备编号");
        queryConditionPanel.setLayout(gridBagLayout1);
        jLabel2.setText("工艺种类");
        jLabel3.setText("工艺版本");
        jLabel4.setText("状态");
        queryConditionPanel.setToolTipText("");
        typeCheckBox.setText("非");
        versionCheckBox.setText("非");
        stateCheckBox.setText("非");
        searchButtonPanel.setLayout(gridBagLayout2);
        searchButton.setMaximumSize(new Dimension(75, 23));
        searchButton.setMinimumSize(new Dimension(75, 23));
        searchButton.setPreferredSize(new Dimension(75, 23));
        searchButton.setMnemonic('F');
        searchButton.setText("搜索(F)");
        searchButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                searchButton_actionPerformed(e);
            }
        });
        //add by sdg on 20061114 (按设备编号,工装编号,材料编号搜索,输入带*等编号时,如*1时要有相应提示.)
        equipmentNumTextField=new CappTextField(view,(QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentNumber", null)),16,false);
        //add end
        okButtonPanel.setLayout(gridBagLayout3);
        okButton.setMaximumSize(new Dimension(75, 23));
        okButton.setMinimumSize(new Dimension(75, 23));
        okButton.setPreferredSize(new Dimension(75, 23));
        okButton.setMnemonic('Y');
        okButton.setText("确定(Y)");
        okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okButton_actionPerformed(e);
            }
        });
        quitButton.setMaximumSize(new Dimension(75, 23));
        quitButton.setMinimumSize(new Dimension(75, 23));
        quitButton.setPreferredSize(new Dimension(75, 23));
        quitButton.setMnemonic('Q');
        quitButton.setText("退出(Q)");
        quitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitButton_actionPerformed(e);
            }
        });
        stopButton.setMaximumSize(new Dimension(75, 23));
        stopButton.setMinimumSize(new Dimension(75, 23));
        stopButton.setPreferredSize(new Dimension(75, 23));
        stopButton.setMnemonic('S');
        stopButton.setText("停止(S)");
        stopButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                stopButton_actionPerformed(e);
            }
        });
        appendResultCheckBox.setText("附加结果");
        ignoreCaseCheckBox.setText("忽略大小写");
        appendPanel.setLayout(gridBagLayout6);
    panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(8, 0, 4, 0), 0, 0));
        panel1.add(searchButtonPanel,
                   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.CENTER,
                                          GridBagConstraints.VERTICAL,
                                          new Insets(16, 0, 0, 0), 1, 1));
        jTabbedPane1.add(queryConditionPanel, "搜索条件");
        searchButtonPanel.add(stopButton,
                              new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 8, 8, 8), 0, 0));
        searchButtonPanel.add(searchButton,
                              new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(16, 8, 8, 8), 1, 1));
        panel1.add(okButtonPanel, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
                , GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 8, 0), 0, 0));
        okButtonPanel.add(paginatePanel,
                          new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(4, 0, 0, 0), 0, 0));
        okButtonPanel.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 0, 0), 0, 0));
        okButtonPanel.add(quitButton,
                          new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(4, 8, 0, 8), 0, 0));
        panel1.add(multiList1, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(4, 0, 0, 8), 0, 0));
        //2007.05.23 xucy ADD
        panel1.add(appendPanel,
                   new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
                                          , GridBagConstraints.WEST,
                                          GridBagConstraints.NONE,
                                          new Insets(0, 0, 0, 0), 0, 0));
        appendPanel.add(appendResultCheckBox,
                   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.WEST,
                                          GridBagConstraints.NONE,
                                          new Insets(0, 0, 0, 0), 0, 0));
       //2007.05.23 END
       appendPanel.add(ignoreCaseCheckBox,
                   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.WEST,
                                          GridBagConstraints.NONE,
                                          new Insets(0, 0, 0, 0), 0, 0));


        getContentPane().add(qMStatus,
                             new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        getContentPane().add(panel1,
                             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 8, 0, 0), 0, 0));
        queryConditionPanel.add(jLabel1,
                                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 4), 0, 0));
        queryConditionPanel.add(jLabel2,
                                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 4), 0, 0));
        queryConditionPanel.add(jLabel3,
                                new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 4), 0, 0));
        queryConditionPanel.add(jLabel4,
                                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 4), 0, 0));
        queryConditionPanel.add(stateComboBox,
                                new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 4, 0), 0, 0));
        queryConditionPanel.add(versionTextField,
                                new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 8, 0, 0), 0, 0));
        queryConditionPanel.add(equipmentNumTextField,
                                new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(8, 8, 0, 0), 0, 0));
        queryConditionPanel.add(typeCheckBox,
                                new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(7, 11, 0, 0), 0, 0));
        queryConditionPanel.add(versionCheckBox,
                                new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(7, 11, 0, 0), 0, 0));
        queryConditionPanel.add(stateCheckBox,
                                new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(7, 11, 0, 0), 0, 0));
        queryConditionPanel.add(technicsTypeSortingSelectedPanel,
                                new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 0, 5, 0), 0, 0));
        multiList1.setCellEditable(false);
        multiList1.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                setOkButtton();
            }
        });
        //CR1 begin
        String[] headings = {"工艺编号", "工艺名称", "工艺种类", "版本号", "工序号", "工序名称","工步号"};
        multiList1.setHeadings(headings);
        multiList1.setRelColWidth(new int[]{1, 1, 1, 1, 1, 1,1});
        //CR1 end
        multiList1.setAllowSorting(true);
        multiList1.setMultipleMode(true);
        setTitle("搜索工艺规程");
        okButton.setEnabled(false);
        paginatePanel.addListener(new PaginateListener()
        {
            public void paginateEvent(PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
        versionTextField.setVisible(false);
        versionCheckBox.setVisible(false);
        jLabel3.setVisible(false);
        localize();
    }


    /**
     * 本地化
     */
    private void localize()
            throws QMRemoteException
    {
        LifeCycleState[] stateArray = LifeCycleState.getLifeCycleStateSet();
        table = new Hashtable();
        stateComboBox.addItem("");
        if (stateArray != null && stateArray.length > 0)
        {
            for (int i = 0; i < stateArray.length; i++)
            {
                table.put(stateArray[i].getDisplay(), stateArray[i]);
                stateComboBox.addItem(stateArray[i].getDisplay());
            }
        }
        if (resource == null)
        {
            initResources();
        }

        try
        {
            okButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "OkJButton", null));
            quitButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "QuitJButton", null));
        }
        catch (MissingResourceException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this,
                                          CappLMRB.MISSING_RESOURCER,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
    }


    /**
     *初始化所使用的资源绑定信息类
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
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this,
                                          CappLMRB.MISSING_RESOURCER,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }


    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        setSize(650, 500);
        setViewLocation();
        super.setVisible(flag);
    }

    public static void main(String[] args)
    {
        String sid =
                "1mD2iUhncqLC1tskIg58aeOUj7IM9mwWg28ZwLHGxVWGGyCJ3zja!1704522824!1072071614571".
                trim();
        CappClientRequestServer server = new CappClientRequestServer("xj", "80",
                sid);
        RequestServerFactory.setRequestServer(server);
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
        }
        TechnicsSearchFromEquipmentJDialog frame = new
                TechnicsSearchFromEquipmentJDialog(null);
        frame.setSize(650, 450);
        frame.setVisible(true);
    }


    /**
     * 获得属性文件中的工装类型
     * @return 工装类型(字符串形式)集合
     */
    public Vector getTechnicsType()
            throws QMRemoteException
    {
        Class[] paramclass = new Class[]
                             {String.class, String.class};
        Object[] paramobject = new Object[]
                               {"工艺种类", "工艺主信息"};
        Collection c = (Collection) TechnicsAction.useServiceMethod(
                "CodingManageService", "getCoding", paramclass, paramobject);
        Vector v = new Vector(c);
        return v;
    }


    /**
     * 搜索按钮
     * @param e ActionEvent
     */
    void searchButton_actionPerformed(ActionEvent e)
    {
        //add by sdg on 20061114 (按设备编号,工装编号,材料编号搜索,输入带*等编号时,如*1时要有相应提示.)
        if(!equipmentNumTextField.check())
            return ;
        //add end

        //检查必添项
        if (equipmentNumTextField.getText() == null ||
            equipmentNumTextField.getText().trim().equals(""))
        {
            String message = new String("设备编号不能为空");
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(this, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        setButtons(false);
        //启动搜索线程
        searchThread = new ToolLinkSearchThread(theThreadGroup, this,
                                                appendResultCheckBox.isSelected(), ignoreCaseCheckBox.isSelected());
        searchThread.start();
    }


    /**
     * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
     * @param flag 若flag=true，则清除上次查询结果，否则在上次查询结果上添加
     */
    public void processToolLinkSearch(boolean flag, boolean flag1)
    {
        if (verbose)
        {
            System.out.println(
                    "part.client.other.view.ExtendSearchJDialog: processExtendSearch()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setStatus(QMMessage.getLocalizedMessage(RESOURCE,
                                                "jMenuSearchTechnics", null));
        if (!flag)
        {
            resultVector.clear();
            //获得查询条件
        }
        Vector condition = getCondition();
        if (verbose)
        {
            System.out.println("------查询条件数目：------  " + condition.size());
        }
        //调用服务方法查询数据
        Class[] paraClass =
                {Vector.class, Integer.TYPE, Boolean.TYPE};
        Object[] paraObj =
                {condition, new Integer(1), new Boolean(flag1)};
        Vector queryresult = null;
        try
        {
            setStatus(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSearchTechnics", null));
            queryresult = (Vector) CappClientHelper.useServiceMethod(
                    "StandardCappService", "technicsSearchFromResource",
                    paraClass, paraObj);

        }
        catch (QMRemoteException e)
        {
            if (verbose)
            {
                System.out.println("Exception processing the query - Cancel = " +
                                   cancelInProgress + "--" +
                                   e.getLocalizedMessage());
            }
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if (!isCancelInProgress())
            {
                return;
            }
        }
        postHandleResult(queryresult);
        setCursor(Cursor.getDefaultCursor());
        if (verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: processExtendSearch() end...return is void ");
        }
    }


    /**
     *获得查询条件
     */
    public Vector getCondition()
    {
        Vector v = new Vector();
        v.add(equipmentNumTextField.getText().trim());
        if (technicsTypeSortingSelectedPanel.getCoding() == null)
        {
            v.add("");
        }
        else
        {
            v.add(technicsTypeSortingSelectedPanel.getCoding().getBsoID());
        }
        v.add(versionTextField.getText().trim());
        if (stateComboBox.getSelectedItem().toString().equals(""))
        {
            v.add("");
        }
        else
        {
            v.add(table.get(stateComboBox.getSelectedItem()).toString());
        }
        v.add(new Boolean(typeCheckBox.isSelected()));
        v.add(new Boolean(versionCheckBox.isSelected()));
        v.add(new Boolean(stateCheckBox.isSelected()));
        if (verbose)
        {
            System.out.println("v=" + v);
        }
        return v;
    }


    /**
     * 清空结果域
     * @param flag 如果flag = true,则清空，否则不清
     */
    public void clearResults(boolean flag)
    {
        if (verbose)
        {
            System.out.println(
                    "part.client.other.view.ExtendSearchJDialog: clearResults() begin...");
        }
        if (flag)
        {
            multiList1.clear();
            okButton.setEnabled(false);
        }
        setStatus(" ");
        if (verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: clearResults() end...return is void");
        }
    }


    /**
     * 设置状态条信息
     * @param text 状态条要显示的文本
     */
    public void setStatus(String text)
    {
        if (qMStatus != null)
        {
            qMStatus.setValue(text);
        }

    }


    /**
     * 判断是否中止检索线程
     * @return 如果返回True，则中止线程。
     */
    public boolean isCancelInProgress()
    {
        return cancelInProgress;
    }


    /**
     * 设置是否中止线程
     * @param flag  如果flag=True，则中止线程。
     */
    public synchronized void setCancelInProgress(boolean flag)
    {
        if (verbose)
        {
            System.out.println("Setting cancelInProgress to: " + flag);
        }
        cancelInProgress = flag;
    }


    /**
     * 结果集后处理,将搜索结果加入结果列表中
     * @param searchResult 搜索结果
     */
    protected void postHandleResult(Vector searchResult)
    {
        if (verbose)
        {
            System.out.println(
                    "part.client.other.view.ExtendSearchJDialog: postHandleResult() begin...");
        }
        if (searchResult == null || searchResult.size() == 0)
        {
            qMStatus.setValue("找到0条纪录");
            return;
        }

        //结果列表中的工艺总数
        if (verbose)
        {
            System.out.println("the result size is:::" + searchResult.size());
        }
        Vector tempVector = new Vector();
        tempVector.addAll(resultVector);
        for (int j = 0; j < searchResult.size(); j++)
        {
            Object[] resultobject = (Object[]) searchResult.elementAt(j);
            //判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果集合中
            boolean flag = false;
            for (int i = 0; i < tempVector.size(); i++)
            {   //CR1
                Object[] oldobject = (Object[]) tempVector.elementAt(i);
                if (((QMTechnicsInfo) resultobject[7]).getBsoID().equals(
                        ((QMTechnicsInfo) oldobject[7]).getBsoID()))
                {
                    flag = true;
                    break;
                }
            }
            if (flag)
            {
                continue;
            }
            else
            {
                resultVector.addElement(resultobject);
            }
        }

        if (verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: postHandleResult() end...return is void");
        }
    }


    /**
     * 中止检索进程
     */
    private synchronized void cancel()
    {
        if (verbose)
        {
            System.out.println(
                    "part.client.other.view.ExtendSearchJDialog: cancel() begin...");
        }
        if (searchThread != null && searchThread.isAlive())
        {
            setCancelInProgress(true);
            //searchThread.interrupt(); //将这句去掉，是因为它使界面退出了，这不合业务
            searchThread = null;
        }
        if (verbose)
        {
            System.out.println(
                    "part.client.other.view.ExtendSearchJDialog: cancel() end...return is void");
        }
    }


    /**
     * 设置按钮的编辑状态，仅供内部使用
     * @param flag
     */
    public void setButtons(boolean flag)
    {
        if (verbose)
        {
            System.out.println(
                    "part.client.other.view.ExtendSearchJDialog: setButtons() begin...");
        }
        searchButton.setEnabled(flag);
        stopButton.setEnabled(!flag);

        if (getSelectedDetails() == null)
        {
            okButton.setEnabled(false);
        }

        quitButton.setEnabled(flag);

    }

    public void setOkButtton()
    {
        if (getSelectedDetails() == null)
        {
            okButton.setEnabled(false);
        }
        else
        {
            okButton.setEnabled(true);
        }
    }


    /**
     * <p>Title: 内部工作线程</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: 一汽启明</p>
     * @author 刘明
     * @version 1.0
     */
    class ToolLinkSearchThread extends QMThread
    {

        /**判断是否保留原来的搜索结果*/
        private boolean isLeave;
        //2007.05.23 xucy add
        private boolean isIgnoreCase;

        /**按工装属性搜索界面对象*/
        private TechnicsSearchFromEquipmentJDialog myDialog;


        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public ToolLinkSearchThread(ThreadGroup threadgroup,
                                    TechnicsSearchFromEquipmentJDialog dialog,
                                    boolean flag, boolean  flag1)
        {
            super(threadgroup);
            myDialog = dialog;
            isLeave = flag;
            isIgnoreCase = flag1;
        }


        /**
         * 使线程中断，停止搜索进程
         */
        public void interrupt()
        {
            if (verbose)
            {
                System.out.println("Inside Interrupt......");
            }
            interrupt();
        }


        /**
         * 线程运行方法，执行搜索。
         */
        public void run()
        {
            if (verbose)
            {
                System.out.println(
                        "part.client.other.controller.ExtendSearchThread: run() begin...");
            }
            try
            {
                //执行搜索
                myDialog.processToolLinkSearch(isLeave, isIgnoreCase);
            }
            catch (Throwable throwable)
            {
                if (!myDialog.isCancelInProgress())
                {
                    throwable.printStackTrace();
                }
            }
            finally
            {
                if (verbose)
                {
                    System.out.println("Inside run finally......");
                }
                myDialog.addResultToMultiList();
                //设置界面按钮的显示状态
                myDialog.setButtons(true);
                //设置没有处于中断状态
                myDialog.setCancelInProgress(false);
            }
            if (verbose)
            {
                System.out.println(
                        "part.client.other.controller.ExtendSearchThread: run() end...return is void");
            }
        }
    }


    void stopButton_actionPerformed(ActionEvent e)
    {
        stopButton.setEnabled(false);
        cancel();
    }


    /**
     * 确定按钮
     * @param e ActionEvent
     */
    void okButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        dispose();
        //获得选择的所有工艺
        BaseValueIfc[] objs = getSelectedDetails();
        Vector v = new Vector();
        if (verbose)
        {
            System.out.println("&&&&&&&&&&&&&& 选择的结果数目: " + objs.length);
            //把从最新版本的工艺卡加入产品结构树中
        }
        for (int i = 0; i < objs.length; i++)
        {
            QMTechnicsInfo info = (QMTechnicsInfo) objs[i];
            TechnicsTreeObject treeObject = new TechnicsTreeObject(info);
            v.addElement(treeObject);
        }
        view.getProcessTreePanel().addProcesses(v);
        view.getProcessTreePanel().setNodeSelected((TechnicsTreeObject) v.
                elementAt(0));
        setCursor(Cursor.getDefaultCursor());
    }


    /**
     * 获得结果域选中的业务对象
     * @return 选中的业务对象
     */
    public BaseValueIfc[] getSelectedDetails()
    {

        int[] rows = multiList1.getSelectedRows();
        BaseValueIfc[] values = new BaseValueIfc[rows.length];
        for (int i = 0; i < rows.length; i++)
        {
            values[i] = (BaseValueIfc) ((TextAndImageCell) multiList1.
                                        getCellAt(rows[i], 0)).getObject();
        }
        return values;
    }


    /**
     * 退出
     * @param e ActionEvent
     */
    void quitButton_actionPerformed(ActionEvent e)
    {
        dispose();
    }

    private void processKeyAction(KeyEvent e)
    {
        if (e.getKeyChar() == '\n')
        { //如果multiList1中没有选中的行，则执行搜索
            if (multiList1.getSelectedRows().length == 0)
            {
                searchButton_actionPerformed(null);
            }
            else
            {
                okButton_actionPerformed(null);
            }
        }
    }

    private void paginatePanel_paginateEvent(PaginateEvent e)
    {
        //获得当前页的所有业务对象;
        Vector tempVector = paginatePanel.getCurrentObjects();
        //将当前页的业务对象集tempVector添加到界面中（需使用者自己实现），例：
        addObjectToMultiList(tempVector);
    }

    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector);
        Vector firstPageVector = paginatePanel.paginate(newV, 100);
        addObjectToMultiList(firstPageVector);
    }


    /**
     * 将设备加入列表
     * @param vec Vector 设备集合
     */
    private void addObjectToMultiList(Vector vec)
    {
        multiList1.clear();
        if (vec != null && vec.size() > 0)
        {
            for (int i = 0; i < vec.size(); i++)
            {
                Object[] resultobject = (Object[]) vec.elementAt(i);
                //将搜索结果加入结果列表中
               //CR1 begin
                QMTechnicsIfc technics = (QMTechnicsIfc) resultobject[7];
                if (((WorkableIfc) technics).getWorkableState().equals("c/o"))
                {
                    if (checkOutImage == null)
                    {
                        checkOutImage = new ImageIcon(getClass().getResource(
                                technics.getIconName("CheckOutIcon")));
                    }
                    TextAndImageCell tic = new TextAndImageCell(resultobject[0].
                            toString(), checkOutImage, resultobject[7]);
                    multiList1.addCell(i, 0, tic);
                }
                else
                if (((WorkableIfc) technics).getWorkableState().equals("c/i"))
                {
                    if (startandImage == null)
                    {
                        startandImage = new ImageIcon(getClass().getResource(
                                technics.getIconName("StandardIcon")));
                    }
                    TextAndImageCell tic = new TextAndImageCell(resultobject[0].
                            toString(), startandImage, resultobject[7]);
                    multiList1.addCell(i, 0, tic);
                }
                else
                if (((WorkableIfc) technics).getWorkableState().equals("wrk"))
                {
                    if (workingImage == null)
                    {
                        workingImage = new ImageIcon(getClass().getResource(
                                technics.getIconName("WorkingIcon")));
                    }
                    TextAndImageCell tic = new TextAndImageCell(resultobject[0].
                            toString(), workingImage, resultobject[7]);
                    multiList1.addCell(i, 0, tic);
                }
                for (int j = 1; j < 7; j++)
                {
                    multiList1.addTextCell(i, j, resultobject[j].toString());
                }
                //CR1 end
            }
            if (!appendResultCheckBox.isSelected())
            {
                qMStatus.setValue("找到" + resultVector.size() + "条纪录");
            }
            else
            {
                qMStatus.setValue("附加找到" + (resultVector.size() - lastNum) +
                                  "条纪录");
            }
            lastNum = resultVector.size();
        }
    }

}
