/**
 * 生成程序 SelectPartJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 2009/06/08 刘学宇  原因：将“选择零部件”的界面由非模态修改为模态
 * SS1 改为传递qmpart，而不是master，因为零部件列表保存的是qmpart liunan 2014-2-21
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.faw_qm.cappclients.beans.processtreepanel.PartTreePanel;
import com.faw_qm.cappclients.conscapproute.controller.CappRouteAction;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.util.EnumeratedChoice;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.ejb.entity.CodingClassification;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartHelper;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;

/**
 * <p> 从产品结构中添加零部件时,调用本界面,选择零部件 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class SelectPartJDialog extends JDialog //CR1
{

    /**
     * 标记：是否执行了保存操作
     */
    public boolean isSave = false;

    /**
     * 装载用户最终选择的零部件
     */
    private Vector results = new Vector();

    /**
     * 业务对象:路线表
     */
    private TechnicsRouteListIfc myRouteList;

    /**
     * 产品ID
     */
    private String productID = "";

    /**
     * 父窗口
     */
    private Frame parentFrame;

    /**
     * 资源文件路径
     */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * 缓存:可选择的所有零部件
     */
    private HashMap partsMap = new HashMap();

    /**
     * 配置规则
     */
    private ConfigSpecItem configSpecItem = null;

    /** 零部件树 */

    /**
     * 代码测试变量
     */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    JButton rightJButton = new JButton();

    JButton rightAllJButton = new JButton();

    JButton leftJButton = new JButton();

    JButton leftAllJButton = new JButton();

    JButton okJButton = new JButton();

    JButton cancelJButton = new JButton();

    JScrollPane jScrollPane1 = new JScrollPane();

    JScrollPane jScrollPane2 = new JScrollPane();

    static PartTreePanel partTreePanel = new PartTreePanel(null);

    JLabel productStructureJLabel = new JLabel();

    JLabel selectedPartJLabel = new JLabel();

    JLabel statusJLabel = new JLabel();

    JPanel jPanel2 = new JPanel();

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    JPanel jPanel3 = new JPanel();

    GridBagLayout gridBagLayout2 = new GridBagLayout();

    JPanel jPanel4 = new JPanel();

    JPanel jPanel5 = new JPanel();

    GridBagLayout gridBagLayout3 = new GridBagLayout();

    GridBagLayout gridBagLayout4 = new GridBagLayout();

    GridBagLayout gridBagLayout5 = new GridBagLayout();

    protected Object lock = new Object();

    protected Object lock2 = new Object();

    private QMPartMasterIfc partMasterIfc = null;

    QMMultiList qMMultiList1 = new QMMultiList();

    private Vector v = new Vector();

    JPanel jPanel1 = new JPanel();

    JCheckBox sourceCheckBox = new JCheckBox();

    EnumeratedChoice sourceComboBox = new EnumeratedChoice();

    GridBagLayout gridBagLayout6 = new GridBagLayout();

    JCheckBox typeCheckBox = new JCheckBox();

    EnumeratedChoice typeComboBox = new EnumeratedChoice();

    JCheckBox jCheckBox3 = new JCheckBox();

  private  RouteListTaskJPanel listPanel;
    /**
     * 构造函数
     * @param frame 父窗口
     */

    public SelectPartJDialog(Frame frame,RouteListTaskJPanel listPanel)
    {
        //begin CR1
        super(frame, "", true);
        //end CR1
        parentFrame = frame;
        this.listPanel=listPanel;
        try
        {
            jbInit();
            //  partTreePanel = new PartTreePanel((JFrame)parentFrame);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * 界面初始化
     * @throws Exception
     */
    private void jbInit() throws Exception
    {
        URL url = CappRouteListManageJFrame.class.getResource("/images/routeM.gif");
        if(url != null)
        {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));

        }
        this.setTitle("选择零部件");
        this.setSize(650, 500);
        this.getContentPane().setLayout(gridBagLayout4);
        this.addWindowListener(new SelectPartJDialog_this_windowAdapter(this));

        localize();
        rightJButton.setText(">");
        rightJButton.addActionListener(new SelectPartJDialog_rightJButton_actionAdapter(this));
        rightJButton.setPreferredSize(new Dimension(65, 23));
        rightJButton.setMinimumSize(new Dimension(65, 23));
        rightJButton.setMaximumSize(new Dimension(65, 23));
        rightAllJButton.setText(">>");
        rightAllJButton.addActionListener(new SelectPartJDialog_rightAllJButton_actionAdapter(this));
        rightAllJButton.setPreferredSize(new Dimension(65, 23));
        rightAllJButton.setMinimumSize(new Dimension(65, 23));
        rightAllJButton.setMaximumSize(new Dimension(65, 23));
        leftJButton.setText("<");
        leftJButton.addActionListener(new SelectPartJDialog_leftJButton_actionAdapter(this));
        leftJButton.setPreferredSize(new Dimension(65, 23));
        leftJButton.setMinimumSize(new Dimension(65, 23));
        leftJButton.setMaximumSize(new Dimension(65, 23));
        leftAllJButton.setText("<<");
        leftAllJButton.addActionListener(new SelectPartJDialog_leftAllJButton_actionAdapter(this));
        leftAllJButton.setPreferredSize(new Dimension(65, 23));
        leftAllJButton.setMinimumSize(new Dimension(65, 23));
        leftAllJButton.setMaximumSize(new Dimension(65, 23));
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new SelectPartJDialog_okJButton_actionAdapter(this));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("确定");
        okJButton.setMnemonic('Y');
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new SelectPartJDialog_cancelJButton_actionAdapter(this));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        productStructureJLabel.setMaximumSize(new Dimension(160, 18));
        productStructureJLabel.setMinimumSize(new Dimension(50, 18));
        productStructureJLabel.setOpaque(false);
        productStructureJLabel.setPreferredSize(new Dimension(50, 18));
        productStructureJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        productStructureJLabel.setText("产品结构");
        selectedPartJLabel.setText("选中零部件");
        selectedPartJLabel.setPreferredSize(new Dimension(66, 18));
        selectedPartJLabel.setOpaque(false);
        selectedPartJLabel.setMinimumSize(new Dimension(65, 18));
        selectedPartJLabel.setMaximumSize(new Dimension(65, 18));
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(200, 23));
        statusJLabel.setMinimumSize(new Dimension(41, 23));
        statusJLabel.setPreferredSize(new Dimension(41, 23));
        statusJLabel.setText("");
        String items[] = {"标识", "编号", "名称"};
        int[] head = {0, 1, 1};
        qMMultiList1.setRelColWidth(head);
        int[] cols = {1, 2};
        qMMultiList1.setColsEnabled(cols, false);
        qMMultiList1.setHeadings(items);
        jPanel2.setLayout(gridBagLayout1);
        jPanel3.setLayout(gridBagLayout2);
        jPanel4.setLayout(gridBagLayout5);
        jPanel5.setLayout(gridBagLayout3);
        jPanel2.setBorder(null);
        jPanel4.setBorder(null);
        jPanel3.setBorder(null);
        jScrollPane2.setPreferredSize(new Dimension(199, 152));
        // partTreePanel.addChangeConfigItemListener(new
        // SelectPartJDialog_partTreePanel_changeConfigItemAdapter(this));
        sourceComboBox.setEnumeratedTypeClass(Class.forName("com.faw_qm.part.util.ProducedBy"));
        sourceComboBox.addActionListener(new SelectPartJDialog_sourceComboBox_actionAdapter(this));

        typeComboBox.setEnumeratedTypeClass(Class.forName("com.faw_qm.part.util.QMPartType"));

        sourceCheckBox.setText("来源");
        jPanel1.setLayout(gridBagLayout6);
        typeCheckBox.setText("类型");
        jCheckBox3.setText("未编辑路线零部件");
        this.getContentPane().add(statusJLabel, new GridBagConstraints(0, 3, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(jPanel2, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 8, 0, 0), 0, 0));
        jPanel2.add(productStructureJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
        jPanel2.add(partTreePanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(jPanel3, new GridBagConstraints(2, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 8), 0, 0));
        jPanel3.add(selectedPartJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
        jPanel3.add(jScrollPane2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jScrollPane2.getViewport().add(qMMultiList1, null);
        jPanel4.add(rightAllJButton, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        jPanel4.add(leftJButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        jPanel4.add(leftAllJButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        jPanel4.add(rightJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(jPanel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 181));
        jPanel1.add(sourceCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        jPanel1.add(sourceComboBox, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), 0, 0));
        jPanel1.add(typeCheckBox, new GridBagConstraints(0, 1, 1, 2, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        jPanel1.add(typeComboBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), 0, 0));
        jPanel1.add(jCheckBox3, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
        this.getContentPane().add(jPanel5, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 10, 8), 0, 0));
        jPanel5.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel5.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        this.getContentPane().add(jPanel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(44, 15, 0, 15), 0, 0));
        //jScrollPane1.getViewport().add(partTreePanel, null);

        partTreePanel.addChangeConfigItemListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addProductToTree(partMasterIfc);
            }
        });
        qMMultiList1.getTable().setShowHorizontalLines(true);
        qMMultiList1.getTable().setShowVerticalLines(true);
    }

    /**
     * 设置配置规则
     * @param configSpecItem 配置规则
     */
    public void setConfigSpec(ConfigSpecItem configSpecItem)
    {
        this.configSpecItem = configSpecItem;
    }

    /**
     * 设置当前的筛选条件.
     * @param refresh = true :根据设置的筛选条件刷新管理器页面内的零部件 =false :不刷新管理器页面内的零部件
     */
    public void setConfigSpecCommand()
    {
        try
        {
            //获取零部件配置规范
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo)PartHelper.getConfigSpec();
            ViewObjectIfc vo = null;
            ConfigSpecItem config = null;

            //如果没有配置规范，构造默认的“标准”配置规范。
            if(configSpecInfo == null)
            {

                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                try
                {
                    dd = (Vector)RequestHelper.request(info1);

                }catch(Exception e)
                {
                    String message = e.getMessage();
                    DialogFactory.showWarningDialog(this, message);
                }
                //dd = null;
                if(dd == null || dd.size() == 0)
                {
                    return;
                }
                for(int i = 0;i < dd.size();i++)
                {
                    if(((ViewObjectIfc)dd.elementAt(i)).getViewName().equals("制造视图"))
                    {
                        vo = (ViewObjectIfc)dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                //标准配置项
                PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
                //设置标准时的视图
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //调用服务将设置好的筛选条件保存到数据库中
                RequestServer server = RequestServerFactory.getRequestServer();
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("ExtendedPartService");
                info.setMethodName("savePartConfigSpec");
                Class[] paramClass = {PartConfigSpecIfc.class};
                info.setParaClasses(paramClass);
                Object[] paramValue = {configSpecInfo};
                info.setParaValues(paramValue);
                try
                {
                    configSpecInfo = (PartConfigSpecInfo)RequestHelper.request(info);
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                    String message = ex.getMessage();
                    DialogFactory.showWarningDialog(this, message);
                    return;
                }
                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpec(config);
            }else
            {

                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpec(config);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            String message = QMExceptionHandler.handle(e);
            DialogFactory.showWarningDialog(this, message);
        }
    }

    /**
     * 配置规则
     */
    private void configureCriterion()
    {
        //显示“编辑结构筛选条件”页面并将数据库中存在的筛选条件传给该页面。
        partTreePanel.configureCriterion();
    }

    /**
     * 本地化
     */
    private void localize()
    {

    }

    /**
     * 设置界面的显示位置
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
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * 设置路线表
     * @param list 路线表
     */
    public void setRouteList(TechnicsRouteListIfc list)
    {
        try
        {
            this.myRouteList = list;
            QMPartMasterIfc productInfo = (QMPartMasterIfc)RParentJPanel.refreshInfo(list.getProductMasterID());

            partTreePanel.getRoot().setLabel(productInfo.getPartNumber() + "_" + productInfo.getPartName() + "的产品结构");
            this.addProductToTree(productInfo);
        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
        }
    }

    /**
     * 设置路线表
     * @param list 路线表
     * 20120109 xucy add
     */
    public void setProductStruct(QMPartMasterIfc partMaster)
    {
        try
        {
            //this.myRouteList = list;
            //QMPartMasterIfc productInfo = (QMPartMasterIfc)RParentJPanel.refreshInfo(partMaster);

            partTreePanel.getRoot().setLabel(partMaster.getPartNumber() + "_" + partMaster.getPartName() + "的产品结构");
            this.addProductToTree(partMaster);
        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
        }
    }
    
    /**
     * 设置产品ID added by skybird 2005.3.16
     * @param product
     */
    public void setProductID(String arg)
    {
        try
        {
            this.productID = arg;
            QMPartMasterIfc productInfo = (QMPartMasterIfc)RParentJPanel.refreshInfo(this.productID);
            partTreePanel.getRoot().setLabel(productInfo.getPartNumber() + "_" + productInfo.getPartName() + "的产品结构");
            this.addProductToTree(productInfo);

        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
        }
    }

    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     * @param parts 零部件值对象集合
     */
    private void addProductToTree(QMPartMasterIfc product)
    {
        partMasterIfc = product;
        QMNode pNode = (QMNode)partTreePanel.getRoot();
        if(pNode.getChildCount() > 0)
            partTreePanel.removeNode((QMNode)pNode.getChildAt(0));
        partTreePanel.addPartMasters(new QMPartMasterInfo[]{(QMPartMasterInfo)product});
        /*
         * Class[] c = { MasteredIfc.class}; Object[] obj = { product}; Collection cc = (Collection) RParentJPanel.useServiceMethod("VersionControlService", "allVersionsOf", c, obj); if (cc != null &&
         * cc.size() != 0) { QMPartMasterInfo[] partMasters=new QMPartMasterInfo[cc.size()]; int i=0; for (Iterator iter = cc.iterator(); iter.hasNext(); ) { partMasters[i] = (QMPartMasterInfo) (
         * (QMPartIfc) iter.next()). getMaster(); } System.out.println("零件主信息partMasters="+partMasters); }
         */

    }

    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     * @param parts 零部件值对象集合
     */
    private void addPartsToList(Vector parts)
    {
        if(parts != null && parts.size() > 0)
        {
            String as[] = new String[parts.size()];
            for(int i = 0;i < parts.size();i++)
            {
                QMPartMasterInfo info = (QMPartMasterInfo)parts.elementAt(i);
                as[i] = info.getBsoID() + ";" + info.getPartNumber() + ";" + info.getPartName();
                partsMap.put(info.getBsoID(), info);
            }

        }
    }

    /**
     * 调用服务,获得所有符合条件的零部件
     * @return 所有符合条件的零部件
     */
    private Vector getSubParts()
    {
        boolean flag = false;
        Vector vec = new Vector();
        Class[] cla = {String.class, QMPartMasterIfc.class};
        BaseValueIfc part = (BaseValueIfc)partTreePanel.getSelected().getObj().getObject();
        if(part instanceof QMPartIfc)
        {
            part = ((QMPartIfc)part).getMaster();
        }
        Object[] obj = {this.myRouteList.getRouteListDepartment(), part};

        try
        {
            flag = ((Boolean)RequestHelper.request("consTechnicsRouteService", "getOptionPart", cla, obj)).booleanValue();
        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
            // return null;
        }
        if(flag)
        {
            vec.add(part);
        }
        return vec;
    }

    /**
     * 获得路线表
     * @return 路线表
     */
    public TechnicsRouteListIfc getRouteList()
    {
        return this.myRouteList;
    }

    /**
     * 获得父窗口
     * @return 父窗口
     */
    /**
     * public Frame getParentFrame() { return this.parentFrame; }
     */

    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     * @param parts 零部件值对象集合
     */
    /**
     * private void addProductToTree(QMPartMasterIfc product) { try { Class[] c = {QMPartMasterIfc.class}; Object[] obj = {product}; Collection cc =
     * (Collection)RParentJPanel.useServiceMethod("TechnicsRouteService","getSubPartMasters",c,obj); for(Iterator iter = cc.iterator();iter.hasNext();) { PartMasterTreeObject treeobj = new
     * PartMasterTreeObject((QMPartMasterIfc)iter.next()); partTreeJPanel.addNode(treeobj); } } catch(QMException ex) { String title= QMMessage.getLocalizedMessage(RESOURCE,"exception",null);
     * JOptionPane.showMessageDialog(this, ex.getClientMessage(), title, JOptionPane.INFORMATION_MESSAGE); } } /** 确认操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        //获得List的part
        Vector vect = new Vector();
        for(int i = 0;i < qMMultiList1.getNumberOfRows();i++)
        {
            String patListBsoId = qMMultiList1.getCellText(i, 0);
            System.out.println("_____________" + qMMultiList1.getCellText(i, 0));
            //调用服务
            Class[] string = {String.class};
            String[] bsoId = {patListBsoId};
            try
            {
                //CCBegin SS1
                //partMasterIfc = (QMPartMasterIfc)RequestHelper.request("PersistService", "refreshInfo", string, bsoId);
                //partMasterIfc=(QMPartMasterIfc)part.getMaster();
                //vect.addElement(partMasterIfc);
                QMPartIfc part = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", string, bsoId);
                vect.addElement(part);
                //CCEnd SS1

            }catch(Exception ex)
            {
                String message = QMExceptionHandler.handle(ex);
                DialogFactory.showWarningDialog(this, message);
            }
        }
        this.setVisible(false);
        RefreshService.getRefreshService().dispatchRefresh("addSelectedParts", 0, vect);
    }

    /***************************************************************************
     * public String[] getPartDetails() { if(part!=null) { String partId = part.getBsoID(); String partNumber = part.getPartNumber(); String partName = part.getPartName(); String partRouteStatus =
     * "无"; String[] partDetails = { partId, partNumber, partName, partRouteStatus}; System.out.println("partId=" + partDetails[0] + "=partNumber=" + partDetails[1] + "=partName=" + partDetails[2] +
     * "=partRouteStatus=" + partDetails[3]); return partDetails; } else return null; }
     */
    /**
     * 取消操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     * 获得用户已选择的所有零部件
     * @return 零部件值对象集合
     */
    /**
     * public Vector getSelectedParts() { Vector v = new Vector(); //获得树上所有被选中的节点 PartMasterTreeNode[] nodes = partTreeJPanel.getSelectedNodes(); if(nodes!=null && nodes.length>0) { for(int i=0;i
     * <nodes.length;i++) { if(nodes[i].getObject()!=null) { QMPartMasterInfo masterInfo = (QMPartMasterInfo)nodes[i].getObject().getObject(); v.add(masterInfo); } } //如果是二级路线，需进行检查
     * if(this.myRouteList.getRouteListLevel().equals(RouteListLevelType.SENCONDROUTE.getDisplay())) { Class[] c = {String.class, String.class,Collection.class}; Object[] objs =
     * {myRouteList.getRouteListDepartment(),myRouteList.getProductMasterID(),v}; Object[] array = null; try { array =
     * (Object[])RParentJPanel.useServiceMethod("TechnicsRouteService","checkSubParts",c,objs); } catch (QMRemoteException ex) { String title= QMMessage.getLocalizedMessage(RESOURCE,"exception",null);
     * JOptionPane.showMessageDialog(this, ex.getClientMessage(), title, JOptionPane.INFORMATION_MESSAGE); return null; } if(array!=null) { Vector v1 = (Vector)array[0]; //符合条件的零部件主信息 Vector v2 =
     * (Vector)array[1]; //不符合条件的零部件主信息 if(v2!=null && v2.size()>0) { String message = "零部件"; for(int j=0;j <v2.size();j++) { String partNumber = ((QMPartMasterInfo)v2.elementAt(j)).getPartNumber();
     * if(j <v2.size()-1) message = message + partNumber+"、"; else message = message + partNumber + "在当前路线表中不能编辑路线。"; }
     * JOptionPane.showMessageDialog(this,message,"通知",JOptionPane.INFORMATION_MESSAGE); } return v1; } } } return v; } /** 获得用户已选择的所有零部件
     * @return 零部件值对象集合
     */
    public Vector getSelectedParts()
    {
        return results;
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SelectPartJDialog frame = new SelectPartJDialog(null,null);
            frame.setViewLocation();
            frame.setVisible(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    void this_windowClosing(WindowEvent e)
    {
        this.isSave = false;
        this.setVisible(false);
    }

    /**
     * Added by Ginger 2005/05/11 用这个方法来判断来源 类型 是否编辑路线的选中情况
     * @param partInfo QMPartIfc
     * @return boolean
     */

    private boolean dofilter(QMPartIfc partInfo)
    {
        if(!dofilterNoRoute(partInfo))
            return false;
        if(jCheckBox3.isSelected())
        {
            System.out.println("isHasRoute(partInfo)==========" + isHasRoute(partInfo));
            return isHasRoute(partInfo);
        }
        return true;

    }

    /**
     * Added by Ginger 2005/05/11 用这个方法来判断来源 类型 是否编辑路线的选中情况
     * @param partInfo QMPartIfc
     * @return boolean
     */
    private boolean dofilterNoRoute(QMPartIfc partInfo)
    {

        if(this.typeCheckBox.isSelected())
        {
            if(!partInfo.getPartType().toString().equals(this.typeComboBox.getSelectedEnumeratedType().toString()))
            {
                return false;
            }
        }
        if(this.sourceCheckBox.isSelected())
        {
            if(!partInfo.getProducedBy().toString().equals(this.sourceComboBox.getSelectedEnumeratedType().toString()))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否已经做了路线
     * @param part
     * @return boolean
     * @throws QMRemoteException
     */
    private boolean isHasRoute(QMPartIfc part)
    {

        RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info = new ServiceRequestInfo();
        info.setServiceName("consTechnicsRouteService");
        info.setMethodName("isHasRoute");
        Class[] paramClass = {String.class};
        info.setParaClasses(paramClass);
        Object[] paramValue = {part.getMasterBsoID()};
        info.setParaValues(paramValue);
        try
        {

            return ((Boolean)RequestHelper.request(info)).booleanValue();
        }catch(Exception ex)
        {
            ex.printStackTrace();

            return false;//xucy 20111213
        }

    }

    /**
     * 判断已经做了路线
     * @param parts
     * @return Vector[]
     * @throws QMRemoteException
     */
    private Vector[] isHasRoute(Vector parts)
    {

        RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info = new ServiceRequestInfo();
        info.setServiceName("consTechnicsRouteService");
        info.setMethodName("isHasRoute");
        Class[] paramClass = {Vector.class};
        info.setParaClasses(paramClass);
        Object[] paramValue = {parts};
        info.setParaValues(paramValue);
        try
        {

            return (Vector[])RequestHelper.request(info);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * 使用配置规范
     * @param vec
     * @return Vector[]
     * @throws QMRemoteException
     */
    private Vector[] doallfilter(Vector vec)
    {
        Vector successVec = new Vector();
        Vector failVec = new Vector();
        QMPartIfc part;
        for(int j = 0;j < vec.size();j++)
        {
            part = (QMPartIfc)vec.elementAt(j);

            if(!dofilterNoRoute(part))
            {
                failVec.add(part);
            }else
            {
                successVec.add(part);
            }
        }
        if(this.jCheckBox3.isSelected())
        {
            Vector[] temp = isHasRoute(successVec);

            for(int i = 0;i < temp[0].size();i++)
                failVec.add(temp[0].elementAt(i));
            successVec = temp[1];
        }

        Vector[] vector = {successVec, failVec};
        return vector;
    }

    /**
     * 把用户在左边零件数中选择的单个零部件添加到列表中
     * @param e
     */
    /*
     * void rightJButton_actionPerformed(ActionEvent e) { QMNode selectedNode = partTreePanel.getSelected(); Vector[] vecArray = null;
     * 
     * if (selectedNode == null) { return; } if (selectedNode.equals(partTreePanel.getRoot()) || selectedNode.getObj().getObject() instanceof MasterIfc) { //||
     * partTreePanel.getRoot().getChildAt(0).equals(selectedNode)) { return; } QMPartInfo partInfo = (QMPartInfo) partTreePanel.getSelected().getObj().getObject();
     * 
     * try { if (!dofilter(part)) {
     * 
     * return;
     * 
     * } } catch (QMRemoteException ex) { JOptionPane.showMessageDialog(this, ex.getClientMessage(), "异常信息", JOptionPane.INFORMATION_MESSAGE); return; }
     * 
     * if (myRouteList.getRouteListLevel().equals("一级路线")) { int j = qMMultiList1.getNumberOfRows(); for (int jj = 0; jj < j; jj++) { String id1 = qMMultiList1.getCellText(jj, 0); if
     * (partInfo.getMasterBsoID().equals(id1)) { return; } } qMMultiList1.addTextCell(j, 0, partInfo.getMasterBsoID()); qMMultiList1.addTextCell(j, 1, partInfo.getPartNumber());
     * qMMultiList1.addTextCell(j, 2, partInfo.getPartName());
     * 
     * } if (myRouteList.getRouteListLevel().equals("二级路线")) { Vector vector = getSubParts(); QMPartMasterIfc part = null; if (vector.size() > 0) {
     * 
     * for (int i = 0; i < vector.size(); i++) { part = (QMPartMasterIfc) vector.elementAt(i); int ii = qMMultiList1.getNumberOfRows(); for (int iii = 0; iii < ii; iii++) { String id1 =
     * qMMultiList1.getCellText(iii, 0); if (part.getBsoID().equals(id1)) { return; } } qMMultiList1.addTextCell(ii, 0, part.getBsoID()); qMMultiList1.addTextCell(ii, 1, part.getPartNumber());
     * qMMultiList1.addTextCell(ii, 2, part.getPartName()); } } }
     * 
     * }
     */
    //add by guoxl on 20080222(把用户在左边零件数中选择的单个零部件添加到列表中，这一功能逻辑错误，所以重新编写)
    void rightJButton_actionPerformed(ActionEvent e)
    {
        QMNode selectedNode = partTreePanel.getSelected();

        Vector[] vecArray = null;

        if(selectedNode == null)
        {
            return;
        }
        if(selectedNode.equals(partTreePanel.getRoot()) || selectedNode.getObj().getObject() instanceof MasterIfc)
        {

            return;
        }
        QMPartInfo partInfo = (QMPartInfo)partTreePanel.getSelected().getObj().getObject();
        Vector partVec = new Vector();
        partVec.add(partInfo);

        try
        {
            vecArray = doallfilter(partVec);

        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
            return;
        }
        QMPartMasterIfc master = null;
        for(int i = 0;i < vecArray[0].size();i++)
        {

            QMPartIfc part = (QMPartIfc)vecArray[0].elementAt(i);
            master = (QMPartMasterIfc)part.getMaster();
            int ii = qMMultiList1.getNumberOfRows();
            boolean isExist = false;
            for(int iii = 0;iii < ii;iii++)
            {
                String id1 = qMMultiList1.getCellText(iii, 0);
                if(master.getBsoID().equals(id1))
                {
                    isExist = true;
                    break;
                }
            }
            if(isExist)
                continue;
            //CCBegin SS1
            //qMMultiList1.addTextCell(ii, 0, master.getBsoID());
            qMMultiList1.addTextCell(ii, 0, part.getBsoID());
            //CCEnd SS1
            qMMultiList1.addTextCell(ii, 1, master.getPartNumber());
            qMMultiList1.addTextCell(ii, 2, master.getPartName());

        }

        if(vecArray[1].size() > 0)
        {
            for(int i = 0;i < vecArray[1].size();i++)
            {
                QMPartIfc partNum = (QMPartIfc)vecArray[1].elementAt(i);

                JOptionPane.showMessageDialog(this, "所选零件" + partNum.getPartNumber() + "不符合要求", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    //add by guoxl end

    /**
     * 左移按钮
     * @param e
     */
    void leftJButton_actionPerformed(ActionEvent e)
    {
        int i = qMMultiList1.getSelectedRow();
        if(i == -1)
        {
            return;
        }
        qMMultiList1.removeRow(i);
    }

    /**
     * 全部右移
     * @param e
     */
    void rightAllJButton_actionPerformed(ActionEvent e)
    {

        Vector vec = getChild();

        Vector[] vecArray = null;
        try
        {
            vecArray = doallfilter(vec);

        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
            return;
        }

        QMPartMasterIfc master = null;
        for(int i = 0;i < vecArray[0].size();i++)
        {

            QMPartIfc part = (QMPartIfc)vecArray[0].elementAt(i);
            master = (QMPartMasterIfc)part.getMaster();
            int ii = qMMultiList1.getNumberOfRows();
            boolean isExist = false;
            for(int iii = 0;iii < ii;iii++)
            {
                String id1 = qMMultiList1.getCellText(iii, 0);
                if(master.getBsoID().equals(id1))
                {
                    isExist = true;
                    break;
                }
            }
            if(isExist)
                continue;
            //CCBegin SS1
            //qMMultiList1.addTextCell(ii, 0, master.getBsoID());
            qMMultiList1.addTextCell(ii, 0, part.getBsoID());
            //CCEnd SS1
            qMMultiList1.addTextCell(ii, 1, master.getPartNumber());
            qMMultiList1.addTextCell(ii, 2, master.getPartName());

        }
        if(vecArray[1].size() > 0)
        {
            JOptionPane.showMessageDialog(this, "有" + vecArray[1].size() + "个零部件不符合要求", "提示", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    /**
     * 递归方法找出所有合格的子节点
     * @return Vector
     */
    private Vector getChild()
    {
        Vector vec = new Vector();
//        if(listPanel.levelJComboBox.getSelectedItem().equals("一级路线"))
//        {
            Class[] paraclass = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
            Object[] paraObj = {partMasterIfc, (PartConfigSpecIfc)partTreePanel.getConfigSpecItem().getConfigSpecInfo()};
            try
            {
                QMPartIfc[] qmPartInfos = (QMPartIfc[])RequestHelper.request("EnterprisePartService", "getAllSubPartsByConfigSpec", paraclass, paraObj);
                QMNode parentNode = (QMNode)partTreePanel.getRoot().getChildAt(0);
                if(parentNode != null && parentNode.getObj().getObject() instanceof QMPartIfc)
                    vec.add(parentNode.getObj().getObject());
                if(qmPartInfos != null)
                {
                    for(int j = 0;j < qmPartInfos.length;j++)
                    {
                        if(qmPartInfos[j].getMaster().getBsoID().equals(this.partMasterIfc.getBsoID()))
                        {
                            continue;
                        }
                        vec.add(qmPartInfos[j]);
                    }
                }

            }catch(Exception ex)
            {
                String message = QMExceptionHandler.handle(ex);
                DialogFactory.showWarningDialog(this, message);
            }

//        }else if(listPanel.levelJComboBox.getSelectedItem().equals("二级路线"))
//        {
//            String bsoID = (listPanel.departmentSelectedPanel.getCoding()).getBsoID();
//            Class[] paraclass = {QMPartMasterIfc.class, PartConfigSpecIfc.class, String.class};
//            Object[] paraObj = {partMasterIfc, (PartConfigSpecIfc)partTreePanel.getConfigSpecItem().getConfigSpecInfo(),bsoID};
//            try
//            {
//                vec = (Vector)RequestHelper.request("TechnicsRouteService", "getAllSubPart", paraclass, paraObj);
//
//            }catch(Exception ex)
//            {
//                String message = QMExceptionHandler.handle(ex);
//                DialogFactory.showWarningDialog(this, message);
//            }
//
//        }

        return vec;
    }

    /**
     * 取得零部件树
     * @return PartTreePanel
     */
    public static PartTreePanel getPartTreePanel()
    {
        return partTreePanel;
    }

    /**
     * 全部左移
     * @param e
     */
    void leftAllJButton_actionPerformed(ActionEvent e)
    {
        qMMultiList1.clear();
    }

    /**
     * 帮助按钮
     * @param e
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {
    //System.out.println("help");
    }

    void sourceComboBox_actionPerformed(ActionEvent e)
    {

    }
}

/**
 * <p>Title:窗口事件监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_this_windowAdapter extends java.awt.event.WindowAdapter
{
    private SelectPartJDialog adaptee;

    SelectPartJDialog_this_windowAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e)
    {
        adaptee.this_windowClosing(e);
    }
}

/**
 * <p>Title:右移事件监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_rightJButton_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_rightJButton_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.rightJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:左移事件监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_leftJButton_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_leftJButton_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.leftJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:全部右移事件监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_rightAllJButton_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_rightAllJButton_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.rightAllJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:全部左移时间监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_leftAllJButton_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_leftAllJButton_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.leftAllJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:确定按钮监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_okJButton_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:取消按钮监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_cancelJButton_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

/*
 * class SelectPartJDialog_partTreePanel_changeConfigItemAdapter implements java.awt.event.ActionListener { SelectPartJDialog adaptee;
 * 
 * SelectPartJDialog_partTreePanel_changeConfigItemAdapter(SelectPartJDialog adaptee) { this.adaptee = adaptee; } public void actionPerformed(ActionEvent e) { adaptee.partTreePanel_actionPerformed(e);
 * } }
 */

/**
 * <p>Title:来源Combo事件监听器</p> <p>Description: </p>
 */
class SelectPartJDialog_sourceComboBox_actionAdapter implements java.awt.event.ActionListener
{
    SelectPartJDialog adaptee;

    SelectPartJDialog_sourceComboBox_actionAdapter(SelectPartJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.sourceComboBox_actionPerformed(e);
    }
}
