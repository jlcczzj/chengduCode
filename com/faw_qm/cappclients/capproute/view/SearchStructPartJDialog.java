/**
 * 生成程序 SearchStructPartJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 2009/06/08 刘学宇  原因：将“选择零部件”的界面由非模态修改为模态
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.faw_qm.cappclients.beans.processtreepanel.PartTreePanel;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;



/**
 * <p>
 * 按总成结构中添加零部件时,调用本界面,选择零部件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 雷晓
 * @version 1.0
 */

public class SearchStructPartJDialog extends JDialog 
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
     * 父窗口
     */
    private Frame parentFrame;

    /**
     * 资源文件路径
     */
    private static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

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
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private JButton browseJButton = new JButton();

    JButton okJButton = new JButton();

    JButton cancelJButton = new JButton();

    JScrollPane jScrollPane1 = new JScrollPane();

    JScrollPane jScrollPane2 = new JScrollPane();

//    static PartTreePanel partTreePanel = new PartTreePanel(null);
    PartTreePanel partTreePanel=null;

    JLabel productStructureJLabel = new JLabel();

    JLabel selectedPartJLabel = new JLabel();

    JLabel statusJLabel = new JLabel();
    
    JPanel jPanel1 = new JPanel();

    JPanel jPanel2 = new JPanel();

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    JPanel jPanel3 = new JPanel();

    GridBagLayout gridBagLayout2 = new GridBagLayout();


    JPanel jPanel5 = new JPanel();

    GridBagLayout gridBagLayout3 = new GridBagLayout();

    GridBagLayout gridBagLayout4 = new GridBagLayout();

    private QMPartMasterIfc partMasterIfc = null;
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    private String  partid;
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    QMMultiList qMMultiList1 = new QMMultiList();

    private Vector v = new Vector();
    
    private static final String SCREEN_ID = "com.faw_qm.clients.richtothin."; //注册文件中的掩码头
    
    private static final String PARAM = "param"; //注册文件中的掩码尾
    
//  CCEnd by leixiao 2008-10-14 原因：解放升级,增加查看零部件功能

    /**
     * 构造函数
     *
     * @param frame
     *            父窗口
     */

    public SearchStructPartJDialog(Frame frame) {
    	
    	//begin CR1
        super(frame, "", true);
        //end CR1
        parentFrame = frame;
        try {
        	partTreePanel = new PartTreePanel(null);
            partTreePanel.getRoot().setLabel("结构");
            jbInit();
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
  
    /**
     * 界面初始化
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setTitle("选择零部件");
        this.setSize(800, 600);
        this.getContentPane().setLayout(gridBagLayout4);
        this.addWindowListener(new SearchStructPartJDialog_this_windowAdapter(this));      
        
        localize();

        browseJButton.setMaximumSize(new Dimension(91, 23));
        browseJButton.setMinimumSize(new Dimension(91, 23));
        browseJButton.setPreferredSize(new Dimension(91, 23));
        browseJButton.setMnemonic('R');
        browseJButton.setText("搜索(R). . .");
        browseJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseJButton_actionPerformed(e);
            }
        });
        okJButton.setText("确定(Y)");
        okJButton
                .addActionListener(new SearchStructPartJDialog_okJButton_actionAdapter(
                        this));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("确定");
        okJButton.setMnemonic('Y');
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setText("取消(C)");
        cancelJButton
                .addActionListener(new SearchStructPartJDialog_cancelJButton_actionAdapter(
                        this));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        productStructureJLabel.setMaximumSize(new Dimension(160, 18));
        productStructureJLabel.setMinimumSize(new Dimension(50, 18));
        productStructureJLabel.setOpaque(false);
        productStructureJLabel.setPreferredSize(new Dimension(50, 18));
        productStructureJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        productStructureJLabel.setText("请搜索要添加的总成");
        selectedPartJLabel.setText("选中零部件");
        selectedPartJLabel.setPreferredSize(new Dimension(100, 18));
        selectedPartJLabel.setOpaque(false);
        selectedPartJLabel.setMinimumSize(new Dimension(65, 18));
        selectedPartJLabel.setMaximumSize(new Dimension(65, 18));
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(200, 23));
        statusJLabel.setMinimumSize(new Dimension(41, 23));
        statusJLabel.setPreferredSize(new Dimension(41, 23));
        statusJLabel.setText("");
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        String items[] = { "标识", "编号", "名称" ,"版本","id","状态"};
        int[] head = { 0, 1, 1, 1, 0 ,1};
//      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        qMMultiList1.setRelColWidth(head);
        int[] cols = { 1, 2 };
        qMMultiList1.setColsEnabled(cols, false);
        qMMultiList1.setHeadings(items);
        jPanel2.setLayout(gridBagLayout1);
        jPanel3.setLayout(gridBagLayout2);
        jPanel5.setLayout(gridBagLayout3);
        jPanel2.setBorder(null);
        jPanel3.setBorder(null);
        jScrollPane2.setPreferredSize(new Dimension(199, 152));
      
        this.getContentPane().add(
                statusJLabel,
                new GridBagConstraints(0, 3, 3, 1, 1.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                jPanel2,
                new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 8, 0, 0), 0, 0));
        jPanel2.add(productStructureJLabel, new GridBagConstraints(0, 0, 2, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 0, 5, 0), 0, 0));
        jPanel2.add(browseJButton, new GridBagConstraints(1, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(5, 0, 5, 20), 0, 0));
        jPanel2.add(partTreePanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                jPanel3,
                new GridBagConstraints(2, 0, 1, 2, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 0, 0, 8), 0, 0));
        jPanel3.add(selectedPartJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 0, 5, 0), 0, 0));
        jPanel3.add(jScrollPane2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        jScrollPane2.getViewport().add(qMMultiList1, null);
       
        this.getContentPane().add(
                jPanel5,
                new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(10, 0, 10, 8), 0, 0));
        jPanel5.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel5.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        8, 0, 0), 0, 0));
        qMMultiList1.getTable().setShowHorizontalLines(true);
        qMMultiList1.getTable().setShowVerticalLines(true);
    }

    /**
     * 设置配置规则
     *
     * @param configSpecItem
     *            配置规则
     */
   public void setConfigSpec(ConfigSpecItem configSpecItem) {
        this.configSpecItem = configSpecItem;
    }

    /**
     * 设置当前的筛选条件.
     *
     * @param refresh =
     *            true :根据设置的筛选条件刷新管理器页面内的零部件 =false :不刷新管理器页面内的零部件
     */
    public void setConfigSpecCommand() {
        try {
            //获取零部件配置规范
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper
                    .getConfigSpec();
            ViewObjectIfc vo = null;
            ConfigSpecItem config = null;


            //如果没有配置规范，构造默认的“标准”配置规范。
            if (configSpecInfo == null) {

                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                try {
                    dd = (Vector) RequestServerFactory.getRequestServer()
                            .request(info1);

                } catch (QMRemoteException e) {
                    JOptionPane.showMessageDialog(this, e.getClientMessage());
                }
                //dd = null;
                if (dd == null || dd.size() == 0) {
                    return;
                }
                for (int i = 0; i < dd.size(); i++) {
                    if (((ViewObjectIfc) dd.elementAt(i)).getViewName().equals(
                            "制造视图")) {
                        vo = (ViewObjectIfc) dd.elementAt(i);
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
                Class[] paramClass = { PartConfigSpecIfc.class };
                info.setParaClasses(paramClass);
                Object[] paramValue = { configSpecInfo };
                info.setParaValues(paramValue);
                try {
                    configSpecInfo = (PartConfigSpecInfo) server.request(info);
                } catch (QMRemoteException ex) {
                    ex.printStackTrace();
                    return;
                }
                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpec(config);
            } else {

                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpec(config);
            }

        } catch (QMRemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置规则
     */
    private void configureCriterion() {
        //显示“编辑结构筛选条件”页面并将数据库中存在的筛选条件传给该页面。
        partTreePanel.configureCriterion();
    }

    /**
     * 本地化
     */
    private void localize() {

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
     * 设置产品ID added by skybird 2005.3.16
     *
     * @param product
     */
    public void setProductID(QMPartIfc productInfo) {
        try {
            partTreePanel.getRoot().setLabel(
                    productInfo.getPartNumber() + "_"
                            + productInfo.getPartName() + "的结构");
            this.addProductToTree(productInfo);

        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
//                    QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
//                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     *
     * @param parts
     *            零部件值对象集合
     */
    private void addProductToTree(QMPartIfc product) {
        QMNode pNode = (QMNode) partTreePanel.getRoot();
        if (pNode.getChildCount() > 0)
            partTreePanel.removeNode((QMNode) pNode.getChildAt(0));
        partTreePanel.addPart((QMPartInfo)product);       
//                .addPartMasters(new QMPartMasterInfo[] { (QMPartMasterInfo) product });
    }

    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     *
     * @param parts
     *            零部件值对象集合
     */
    private void addPartsToList(Vector parts) {
        if (parts != null && parts.size() > 0) {
            String as[] = new String[parts.size()];
            for (int i = 0; i < parts.size(); i++) {
                QMPartMasterInfo info = (QMPartMasterInfo) parts.elementAt(i);
                as[i] = info.getBsoID() + ";" + info.getPartNumber() + ";"
                        + info.getPartName();
                partsMap.put(info.getBsoID(), info);
            }


        }
    }

    /**
     * 调用服务,获得所有符合条件的零部件
     *
     * @return 所有符合条件的零部件
     */
//    private Vector getSubParts() {
//        boolean flag = false;
//        Vector vec = new Vector();
//        Class[] cla = { String.class, QMPartMasterIfc.class };
//        BaseValueIfc part = (BaseValueIfc) partTreePanel.getSelected().getObj()
//                .getObject();
//        if (part instanceof QMPartIfc) {
//            part = ((QMPartIfc) part).getMaster();
//        }
//        Object[] obj = { this.myRouteList.getRouteListDepartment(), part };
//
//        try {
//            flag = ((Boolean) CappRouteAction.useServiceMethod(
//                    "TechnicsRouteService", "getOptionPart", cla, obj))
//                    .booleanValue();
//        } catch (QMRemoteException ex) {
//            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
//                    QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
//                    JOptionPane.INFORMATION_MESSAGE);
//            // return null;
//        }
//        if (flag) {
//            vec.add(part);
//        }
//        return vec;
//    }



    /**
     * 把产品结构中所有符合条件的零部件添加入列表中
     *
     * @param parts
     *            零部件值对象集合
     */
    /**
     * JOptionPane.INFORMATION_MESSAGE); } } /** 确认操作
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        //获得List的part
        Vector vect = new Vector();
        for (int i = 0; i < qMMultiList1.getNumberOfRows(); i++) {
            String patListBsoId = qMMultiList1.getCellText(i, 0);
//          CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
            partid = qMMultiList1.getCellText(i, 4);
//          CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id         

//            System.out
//                    .println("_____________" + qMMultiList1.getCellText(i, 0));
            //调用服务
            Class[] string = { String.class };
            String[] bsoId = { patListBsoId };
//          CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
            Class[] string1 = { String.class };
            String[] bsoId1 = { partid };
            try {
                partMasterIfc = (QMPartMasterIfc) CappRouteAction
                        .useServiceMethod("PersistService", "refreshInfo",
                                string, bsoId);

                QMPartIfc partIfc = (QMPartIfc) CappRouteAction
                .useServiceMethod("PersistService", "refreshInfo",
                		string1, bsoId1);
                
                //partMasterIfc=(QMPartMasterIfc)part.getMaster();
                Object [] part={partMasterIfc,partIfc};
                vect.addElement(part);
               // vect.addElement(partMasterIfc);
//              CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id


            } catch (QMRemoteException ex) {
                JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                        QMMessage.getLocalizedMessage(RESOURCE, "exception",
                                null), JOptionPane.INFORMATION_MESSAGE);
            }
        }
        this.setVisible(false);
        RefreshService.getRefreshService().dispatchRefresh("addSelectedParts",
                0, vect);
    }

    /***************************************************************************
     * public String[] getPartDetails() { if(part!=null) { String partId =
     * part.getBsoID(); String partNumber = part.getPartNumber(); String
     * partName = part.getPartName(); String partRouteStatus = "无"; String[]
     * partDetails = { partId, partNumber, partName, partRouteStatus};
     * System.out.println("partId=" + partDetails[0] + "=partNumber=" +
     * partDetails[1] + "=partName=" + partDetails[2] + "=partRouteStatus=" +
     * partDetails[3]); return partDetails; } else return null; }
     */
    /**
     * 取消操作
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    public Vector getSelectedParts() {
        return results;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SearchStructPartJDialog frame = new SearchStructPartJDialog(null);
            frame.setViewLocation();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void this_windowClosing(WindowEvent e) {
        this.isSave = false;
        this.setVisible(false);
    }

 

    /**
     * 使用配置规范
     * @param vec
     * @return Vector[]
     * @throws QMRemoteException
     */
    private Vector[] doallfilter(Vector vec) throws QMRemoteException {
        Vector successVec = new Vector();
        Vector failVec = new Vector();
        QMPartIfc part;
        for (int j = 0; j < vec.size(); j++) {
            part = (QMPartIfc) vec.elementAt(j);

                successVec.add(part);

        }
        Vector[] vector = { successVec, failVec };
        return vector;
    }



//leix
    /**
     * 添加到右边列表
     * @param e
     */
    void rightAllJButton_actionPerformed(QMPartIfc partinfo ) {

        Vector vec = getChild(partinfo);

        Vector[] vecArray = null;
        try {
            vecArray = doallfilter(vec);

        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), "异常信息",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        QMPartMasterIfc master = null;
        for (int i = 0; i < vecArray[0].size(); i++) {

            QMPartIfc part = (QMPartIfc) vecArray[0].elementAt(i);
            master = (QMPartMasterIfc) part.getMaster();
            int ii = qMMultiList1.getNumberOfRows();
            boolean isExist = false;
            for (int iii = 0; iii < ii; iii++) {
                String id1 = qMMultiList1.getCellText(iii, 0);
                if (master.getBsoID().equals(id1)) {
                    isExist = true;
                    break;
                }
            }
            if (isExist)
                continue;
            qMMultiList1.addTextCell(ii, 0, master.getBsoID());
            qMMultiList1.addTextCell(ii, 1, master.getPartNumber());
            qMMultiList1.addTextCell(ii, 2, master.getPartName());
            //CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
            qMMultiList1.addTextCell(ii, 3, part.getVersionValue());
            qMMultiList1.addTextCell(ii, 4, part.getBsoID());
            qMMultiList1.addTextCell(ii, 5, part.getLifeCycleState().getDisplay());
            //CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线

        }
        if (vecArray[1].size() > 0) {
            JOptionPane.showMessageDialog(this, "有" + vecArray[1].size()
                    + "个零部件不符合要求", "提示", JOptionPane.INFORMATION_MESSAGE);

        }

    }


    /**
     * 递归方法找出所有合格的子节点
     * @return Vector
     */
    private Vector getChild(QMPartIfc partinfo) {
        Vector vec = new Vector();

        Class[] paraclass = { QMPartIfc.class,
                PartConfigSpecIfc.class };
        Object[] paraObj = {
        		(QMPartIfc)partinfo,
                (PartConfigSpecIfc) partTreePanel.getConfigSpecItem()
                        .getConfigSpecInfo() };
        try {
            Vector qmPartInfos = (Vector) RParentJPanel
                    .useServiceMethod("EnterprisePartService",
                            "getAllSubPartsByConfigSpec", paraclass,
                            paraObj);
            QMNode parentNode = (QMNode) partTreePanel.getRoot()
                    .getChildAt(0);
            if (parentNode != null
                    && parentNode.getObj().getObject() instanceof QMPartIfc){
            	QMPartIfc rootpart=(QMPartIfc)parentNode.getObj().getObject();
            	if(rootpart.getPartNumber().length()>5&&rootpart.getPartNumber().substring(4,5).equals("G")){
            	}
            	else{
                vec.add(parentNode.getObj().getObject());
            	}
            }
            if (qmPartInfos != null) {
                for (int j = 0; j < qmPartInfos.size(); j++) {
                	QMPartIfc part=(QMPartIfc)qmPartInfos.get(j);
                	
//                    if (part.getMaster().getBsoID().equals(
//                            this.partMasterIfc.getBsoID())) {
//                        continue;
//                    }
                	if(part.getPartNumber().length()>5&&part.getPartNumber().substring(4,5).equals("G")){
                	}
                	//	System.out.println("----"+part.getPartNumber());
                	else{
                    vec.add(part);
                	}
                }
            }

        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    "异常信息", JOptionPane.INFORMATION_MESSAGE);
        }

    

        return vec;
    }
    
    /**
     * 搜索“用于产品”的零部件
     *
     * @param e
     *            ActionEvent
     */
    void browseJButton_actionPerformed(ActionEvent e) {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle",
                null);
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPart", title, this.getParent()
                );
//        qmChooser.setRelColWidth(new int[] { 1, 1,1,1,1 });
//      CCBegin by leixiao 2009-1-4 原因：解放升级工艺路线,
        qmChooser.setChildQuery(true);
//      CCEnd by leixiao 2009-1-4 原因：解放升级工艺路线, 
        try {
            qmChooser.setMultipleMode(false);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener() {

            public void queryEvent(QMQueryEvent e) {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * 搜索零部件监听事件方法
     *
     * @param e
     *            搜索监听事件
     */
    private void qmChooser_queryEvent(QMQueryEvent e) {
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if (e.getType().equals(QMQueryEvent.COMMAND)) {
            if (e.getCommand().equals(QmQuery.OkCMD)) {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser) e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if (bvi != null) {
                    setProductID((QMPartIfc)bvi);
                    rightAllJButton_actionPerformed((QMPartIfc)bvi);
                }
            }
        }
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }
    
    


    /**
     * 帮助按钮
     * @param e
     */
    void helpJButton_actionPerformed(ActionEvent e) {
        //System.out.println("help");
    }


}

 /**
  * <p>Title:窗口事件监听器</p>
  * <p>Description: </p>
  */
class SearchStructPartJDialog_this_windowAdapter extends java.awt.event.WindowAdapter {
    private SearchStructPartJDialog adaptee;

    SearchStructPartJDialog_this_windowAdapter(SearchStructPartJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }
}




 /**
  * <p>Title:确定按钮监听器</p>
  * <p>Description: </p>
  */
class SearchStructPartJDialog_okJButton_actionAdapter implements
        java.awt.event.ActionListener {
    SearchStructPartJDialog adaptee;

    SearchStructPartJDialog_okJButton_actionAdapter(SearchStructPartJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.okJButton_actionPerformed(e);
    }
}

 /**
  * <p>Title:取消按钮监听器</p>
  * <p>Description: </p>
  */
class SearchStructPartJDialog_cancelJButton_actionAdapter implements
        java.awt.event.ActionListener {
    SearchStructPartJDialog adaptee;

    SearchStructPartJDialog_cancelJButton_actionAdapter(SearchStructPartJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

