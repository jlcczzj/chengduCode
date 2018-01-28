/**
 * 生成程序 CompositiveRouteJFrame 1.0 2005.3.16
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.beans.*;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.faw_qm.cappclients.capp.view.*;
import com.faw_qm.cappclients.conscapproute.controller.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.part.model.*;

/**
 * <p> Title:综合路线用 </p> <p> Copyright: Copyright (c) 2005 </p> <p> Company: 一汽启明 </p>
 * @author skybird
 * @version 1.0
 */
public class CompositiveRouteJFrame extends JFrame
{
    //界面元素
    JPanel contentPane;

    JFrame parent;

    JPanel jPanel5 = new JPanel();

    JButton cancel1JButton = new JButton();

    JButton okJButton = new JButton();

    //handful
    private QMMultiList qMMultiListL = new QMMultiList();

    private QMMultiList qMMultiListR = new QMMultiList();

    /** 用于标记资源文件路径 */
    protected static String RESOURCEP = "com.faw_qm.part.client.other.util.OtherRB";

    /** 要查询的业务对象 */
    public static String SCHEMA = QMMessage.getLocalizedMessage(RESOURCEP, "schema", null);

    /** 查询方案 */
    private QMSchema mySchema;

    //逻辑元素
    /** 资源文件路径 */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    //Added by Ginger 2005/05/11
    //制造路线ID
    //  private String mumaRouteID="";
    //装配路线ID
    // private String asseRouteID="";
    //保存选中
    private String state = "您选中的是";

    //End
    //缓存选中的产品
    HashMap productsMap = new HashMap();

    //缓存选中的零部件
    HashMap partsMap = new HashMap();

    //从产品结构选择零部件对话框
    SelectPartJDialog selectPartDialog;

    //状态栏
    GridBagLayout gridBagLayout1 = new GridBagLayout();

    GridBagLayout gridBagLayout2 = new GridBagLayout();

    GridBagLayout gridBagLayout3 = new GridBagLayout();

    JLabel manujLabel = new JLabel();

    CappSortingSelectedPanel manujSelectedPanel;

    JLabel assejLabel = new JLabel();

    CappSortingSelectedPanel asseSelectedPanel;

    JPanel jPanel1 = new JPanel();

    JLabel productjLabel = new JLabel();

    JLabel partjLabel = new JLabel();

    JButton productjButton = new JButton();

    JButton partjButton = new JButton();

    GridBagLayout gridBagLayout4 = new GridBagLayout();

    GridBagLayout gridBagLayout5 = new GridBagLayout();

    JButton productDelButton = new JButton();

    JButton subDelButton = new JButton();

    /**
     * 构造函数
     * @param arg
     */
    public CompositiveRouteJFrame(JFrame arg)
    {
        //enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.parent = arg;
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /** 组件初始化 */
    private void jbInit()
    {
        URL url = CappRouteListManageJFrame.class.getResource("/images/routeM.gif");
        if(url != null)
        {
            setIconImage(Toolkit.getDefaultToolkit().createImage(url));

        }
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(gridBagLayout5);
        this.setEnabled(true);
        this.setResizable(false);
        this.setSize(new Dimension(679, 474));
        this.setTitle("综合路线");
        cancel1JButton.setMaximumSize(new Dimension(75, 23));
        cancel1JButton.setMinimumSize(new Dimension(75, 23));
        cancel1JButton.setPreferredSize(new Dimension(75, 23));
        cancel1JButton.setMnemonic('C');
        cancel1JButton.setText("取消(C)");
        cancel1JButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancel1JButton_actionPerformed(e);
            }
        });
        okJButton.setText("生成(Y)");
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setMnemonic('Y');
        okJButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
        jPanel5.setLayout(gridBagLayout1);
        jPanel5.setInputVerifier(null);
        contentPane.setInputVerifier(null);
        manujLabel.setText("制造路线");
        manujSelectedPanel = new CappSortingSelectedPanel("单位", "TechnicsRouteList", "routeListDepartment");
        manujSelectedPanel.setIsSelectCC(true);
        manujSelectedPanel.transferFocus();
        manujSelectedPanel.setSelectBMnemonic('M');
        assejLabel.setToolTipText("");
        assejLabel.setText("装配路线");
        jPanel1.setLayout(gridBagLayout4);
        productjLabel.setText("选择产品");
        partjLabel.setText("选择零件");
        productjButton.setMaximumSize(new Dimension(89, 23));
        productjButton.setMinimumSize(new Dimension(89, 23));
        productjButton.setPreferredSize(new Dimension(89, 23));
        productjButton.setMnemonic('P');
        productjButton.setText("搜索(P)...");
        productjButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                productjButton_actionPerformed(e);
            }
        });
        partjButton.setMaximumSize(new Dimension(89, 23));
        partjButton.setMinimumSize(new Dimension(89, 23));
        partjButton.setPreferredSize(new Dimension(89, 23));
        partjButton.setMnemonic('S');
        partjButton.setText("搜索(S)...");
        partjButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                partjButton_actionPerformed(e);
            }
        });

        asseSelectedPanel = new CappSortingSelectedPanel("单位", "TechnicsRouteList", "routeListDepartment");
        asseSelectedPanel.setIsSelectCC(true);
        asseSelectedPanel.transferFocus();

        productDelButton.setMaximumSize(new Dimension(89, 25));
        productDelButton.setMinimumSize(new Dimension(89, 25));
        productDelButton.setPreferredSize(new Dimension(89, 25));
        productDelButton.setMnemonic('D');
        productDelButton.setText("删除(D)");
        productDelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                productDelButton_actionPerformed(e);
            }
        });
        subDelButton.setMaximumSize(new Dimension(89, 25));
        subDelButton.setMinimumSize(new Dimension(89, 25));
        subDelButton.setOpaque(true);
        subDelButton.setPreferredSize(new Dimension(89, 25));
        subDelButton.setMnemonic('R');
        subDelButton.setText("删除(R)");
        subDelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                subDelButton_actionPerformed(e);
            }
        });
        contentPane.add(qMMultiListL, new GridBagConstraints(0, 2, 1, 3, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 8, 0, 0), 0, 0));
        qMMultiListL.setHeadings(new String[]{"id", "编号", "名称", "版本"});
        qMMultiListL.setRelColWidth(new int[]{0, 1, 1, 1});
        qMMultiListL.addActionListener(new compositiveRouteJFrame_qMMultiListL_actionAdapter(this));
        qMMultiListL.setCellEditable(false);
        qMMultiListL.setMultipleMode(true);

        contentPane.add(qMMultiListR, new GridBagConstraints(2, 2, 1, 3, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 8, 0, 0), 0, 0));

        qMMultiListR.setHeadings(new String[]{"id", "编号", "名称", "版本"});
        qMMultiListR.setRelColWidth(new int[]{0, 1, 1, 1});
        qMMultiListR.addActionListener(new compositiveRouteJFrame_qMMultiListR_actionAdapter(this));
        qMMultiListR.setCellEditable(false);
        qMMultiListR.setMultipleMode(true);
        qMMultiListR.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                setOkButtonEnabled();
            }
        });

        contentPane.add(jPanel5, new GridBagConstraints(0, 5, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 11, 8), -32, 0));
        jPanel5.add(cancel1JButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 3, 0, 257), 0, 0));
        jPanel5.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 220, 0, 3), 0, 0));
        contentPane.add(jPanel1, new GridBagConstraints(0, 0, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(13, 8, 0, 8), 258, 0));
        jPanel1.add(manujSelectedPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 4, 0, 0), 0, 0));
        jPanel1.add(assejLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 4, 0, 0), 0, 0));
        jPanel1.add(asseSelectedPanel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 0), 0, 0));
        jPanel1.add(manujLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        contentPane.add(productjLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 8, 0, 0), 0, 0));
        contentPane.add(productjButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 8, 0, 0), 0, 0));
        contentPane.add(partjButton, new GridBagConstraints(3, 2, 1, 2, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 8, 0, 8), 0, 0));
        contentPane.add(partjLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 8, 0, 0), 0, 0));
        contentPane.add(productDelButton, new GridBagConstraints(1, 3, 1, 2, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 8, 0, 0), 0, 0));
        contentPane.add(subDelButton, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 8, 0, 8), 0, 0));

    }

    /**
     * 设置确定按钮的状态
     */
    private void setOkButtonEnabled()
    {}

    public JFrame getParentJFrame()
    {
        return this.parent;
    }

    /** Overridden so we can exit when window is closed */
    protected void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            this.dispose();
        }
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
     * 取消按钮的操作方法
     * @param e
     */
    public void cancel1JButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     * 生成按钮执行操作
     * @param e
     */
    public void okJButton_actionPerformed(ActionEvent e)
    {
        String mumaRouteID = "";
        String asseRouteID = "";
        BaseValueIfc makeV = manujSelectedPanel.getCoding();
        if(makeV != null)
            mumaRouteID = makeV.getBsoID();
        BaseValueIfc constructV = this.asseSelectedPanel.getCoding();
        if(constructV != null)
            asseRouteID = constructV.getBsoID();
        if((this.productsMap == null || this.productsMap.size() == 0) && (this.partsMap == null || this.partsMap.size() == 0))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);

            JOptionPane.showMessageDialog(this, "请选择零部件！", title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(this.partsMap == null || this.partsMap.size() == 0)
        {
            int result = JOptionPane.showConfirmDialog(this, "汇总时间可能会很长，您继续吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch(result)
            {
            case JOptionPane.NO_OPTION:
            {
                return;
            }
            }
        }
        if((this.productsMap != null && this.productsMap.size() != 0) && (this.partsMap != null && this.partsMap.size() != 0))
        {
            try
            {
                Vector errParts = getPartNoInProduct();
                if(errParts.size() != 0)
                {
                    StringBuffer buff = new StringBuffer();
                    QMPartIfc errPart = (QMPartIfc)errParts.get(0);
                    buff.append(errPart.getPartNumber() + "(" + errPart.getVersionValue() + ")");
                    for(int i = 1;i < errParts.size();i++)
                    {
                        errPart = (QMPartIfc)errParts.get(i);
                        buff.append(", ");
                        buff.append(errPart.getPartNumber() + "(" + errPart.getVersionValue() + ")");
                    }
                    int result = JOptionPane.showConfirmDialog(this, "零部件" + buff.toString() + "不在你选择的产品中，您继续吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch(result)
                    {
                    case JOptionPane.NO_OPTION:
                    {
                        return;
                    }
                    }

                }
            }catch(Exception ex)
            {

                String message = ex.getMessage();
                DialogFactory.showWarningDialog(parent, message);
                return;
            }

        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        HashMap hashmap = new HashMap();
        hashmap.put("mDepartment", mumaRouteID);
        hashmap.put("cDepartment", asseRouteID);
        hashmap.put("products", getProductIDs());
        hashmap.put("parts", getPartIDs());
        //        System.out.println("开始请求route_compositive.screen" + "asseRouteID="
        //                + asseRouteID + "mumaRouteID=" + mumaRouteID + "products="
        //                + getProductIDs() + "getPartIDs=" + getPartIDs());
        RichToThinUtil.toWebPage("route_compositive.screen", hashmap);
        this.setCursor(Cursor.getDefaultCursor());

    }

    /**
     * 取得产品中的零件编号
     * @return Vector
     * @throws QMRemoteException
     */
    private Vector getPartNoInProduct()
    {
        Iterator i = partsMap.keySet().iterator();
        Vector result = new Vector();
        try
        {
            QMPartIfc part = null;
            while(i.hasNext())
            {
                part = (QMPartIfc)partsMap.get(i.next());
                Iterator pi = productsMap.keySet().iterator();
                QMPartIfc product = null;
                boolean isIn = false;
                while(pi.hasNext())
                {
                    product = (QMPartIfc)productsMap.get(pi.next());
                    if(this.isParentPart(part, product))
                        isIn = true;
                }
                if(!isIn)
                    result.add(part);
            }
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(parent, message);
        }
        return result;
    }

    /**
     * 判断一个零件是不是父件
     * @param part
     * @param parent
     * @return 是否是父件
     * @throws QMRemoteException
     */
    private boolean isParentPart(QMPartIfc part, QMPartIfc parent)
    {
        Class[] classes = {QMPartIfc.class, QMPartIfc.class};
        Object[] objs = {part, parent};
        try {
			return ((Boolean)RequestHelper.request("StandardPartService", "isParentPart", classes, objs)).booleanValue();
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		
		return false;

    }

    /**
     * 返回产品ID
     * @return String
     */
    private String getProductIDs()
    {
        if(this.productsMap == null || this.productsMap.size() == 0)
            return null;
        Iterator i = productsMap.keySet().iterator();
        StringBuffer buff = new StringBuffer();
        if(i.hasNext())
            buff.append((String)i.next());
        while(i.hasNext())
        {
            buff.append(";");
            buff.append((String)i.next());
        }
        return buff.toString();
    }

    /**
     * 返回零部件ID
     * @return String
     */
    private String getPartIDs()
    {
        if(this.partsMap == null || this.partsMap.size() == 0)
            return null;
        Iterator i = partsMap.keySet().iterator();
        StringBuffer buff = new StringBuffer();
        if(i.hasNext())
        {
            buff.append((String)i.next());
        }
        while(i.hasNext())
        {
            buff.append(";");
            buff.append((String)i.next());
        }
        return buff.toString();
    }

    /**
     * 搜索产品监听事件方法
     * @param e 搜索监听事件
     */
    private void qmChooser_queryEventPart(QMQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("capproute.view.CompositiveRouteJFrame:qmChooser_queryEvent(e) begin...");
        }
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser)e.getSource();
                BaseValueIfc[] bvis = c.getSelectedDetails();
                if(bvis != null)
                    addPartsToListR(bvis);
            }
        }
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

    /**
     * 搜索产品监听事件方法
     * @param e 搜索监听事件
     */
    private void qmChooser_queryEvent(QMQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("capproute.view.CompositiveRouteJFrame:qmChooser_queryEvent(e) begin...");
        }
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser)e.getSource();
                BaseValueIfc[] bvis = c.getSelectedDetails();
                if(bvis != null)
                    addPartsToListL(bvis);
            }
        }
        if(verbose)
        {
            System.out.println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }

    /**
     * 把所有符合条件的零部件添加入左边产品列表中
     * @param partLinks 零部件关联值对象集合
     */
    private void addPartsToListL(BaseValueIfc[] parts)
    {
        if(parts != null && parts.length > 0)
        {
            for(int i = 0;i < parts.length;i++)
            {
                int row = qMMultiListL.getNumberOfRows();
                QMPartInfo part = (QMPartInfo)parts[i];
                if(!productsMap.containsKey(part.getBsoID()))
                {
                    productsMap.put(part.getBsoID(), part);
                    qMMultiListL.addTextCell(row, 0, part.getBsoID());
                    qMMultiListL.addTextCell(row, 1, part.getPartNumber());
                    qMMultiListL.addTextCell(row, 2, part.getPartName());
                    qMMultiListL.addTextCell(row, 3, part.getVersionValue());
                }

            }
        }
    }

    public static void main(String[] args)
    {
        CompositiveRouteJFrame frame = new CompositiveRouteJFrame(null);
        frame.setViewLocation();
        frame.setVisible(true);
    }

    /**
     * 把所有符合条件的零部件添加入右部的零部件列表中
     * @param partLinks 零部件关联值对象集合
     */
    private void addPartsToListR(BaseValueIfc[] parts)
    {
        if(parts != null && parts.length > 0)
        {
            // Object[] tmp = null;
            // String as[] = new String[parts.length];
            for(int i = 0;i < parts.length;i++)
            {
                int row = qMMultiListR.getNumberOfRows();
                QMPartInfo part = (QMPartInfo)parts[i];
                if(!partsMap.containsKey(part.getBsoID()))
                {
                    partsMap.put(part.getBsoID(), part);
                    qMMultiListR.addTextCell(row, 0, part.getBsoID());
                    qMMultiListR.addTextCell(row, 1, part.getPartNumber());
                    qMMultiListR.addTextCell(row, 2, part.getPartName());
                    qMMultiListR.addTextCell(row, 3, part.getVersionValue());
                }

            }
        }

    }

    /**
     * 左边列表的监听方法
     * @param e
     */
    public void qMMultiListL_actionPerformed(ActionEvent e)
    {
    //左列表监听
    }

    /**
     * 右边列表的监听方法
     * @param e
     */
    public void qMMultiListR_actionPerformed(ActionEvent e)
    {
    //右列表监听
    }

    void productjButton_actionPerformed(ActionEvent e)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle", null);
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPart", title, this);
        mySchema = new QMSchema(SCHEMA);
        //设置查询方案
        qmChooser.setSchema(mySchema);
        qmChooser.setLastIteration(true);
        qmChooser.setChildQuery(false);
        try
        {
            qmChooser.setMultipleMode(true);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener()
        {

            public void queryEvent(QMQueryEvent e)
            {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);

    }

    /**
     * 零部件按钮事件处理
     * @param e
     */
    void partjButton_actionPerformed(ActionEvent e)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle", null);
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPart", title, this);
        mySchema = new QMSchema(SCHEMA);
        //设置查询方案
        qmChooser.setSchema(mySchema);
        qmChooser.setLastIteration(true);
        qmChooser.setChildQuery(false);
        try
        {
            qmChooser.setMultipleMode(true);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener()
        {

            public void queryEvent(QMQueryEvent e)
            {
                qmChooser_queryEventPart(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * 产品删除按钮处理事件
     * @param e
     */
    void productDelButton_actionPerformed(ActionEvent e)
    {
        int[] rows = qMMultiListL.getSelectedRows();
        if(rows != null)
            for(int i = 0;i < rows.length;i++)
            {
                int row = rows[i];
                if(row != -1)
                {
                    String partID = qMMultiListL.getCellText(row - i, 0);
                    this.productsMap.remove(partID);
                    qMMultiListL.removeRow(row - i);
                }
            }
    }

    /**
     * 子部件删除处理
     * @param e
     */
    void subDelButton_actionPerformed(ActionEvent e)
    {
        int[] rows = qMMultiListR.getSelectedRows();
        if(rows != null)
            for(int i = 0;i < rows.length;i++)
            {
                int row = rows[i];
                if(row != -1)
                {
                    String partID = qMMultiListR.getCellText(row - i, 0);
                    partsMap.remove(partID);
                    qMMultiListR.removeRow(row - i);
                }
            }
    }
}

/**
 * <p> Title:compositiveRouteJFrame_qMMultiListL_actionAdapter </p> <p> Description:综合路线JFrame的左qMMultiList事件处理器 </p> <p> Package:com.faw_qm.cappclients.conscapproute.view </p> <p> Copyright: Copyright
 * (c) 2005 </p> <p> Company:一汽启明 </p>
 * @author @version 1.0
 */

class compositiveRouteJFrame_qMMultiListL_actionAdapter implements java.awt.event.ActionListener
{
    //综合路线用JFrame
    private CompositiveRouteJFrame adaptee;

    //构造函数
    compositiveRouteJFrame_qMMultiListL_actionAdapter(CompositiveRouteJFrame adaptee)
    {
        this.adaptee = (CompositiveRouteJFrame)adaptee;
    }

    //事件处理
    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiListL_actionPerformed(e);
    }
}

/**
 * <p> Title:compositiveRouteJFrame_qMMultiListL_actionAdapter </p> <p> Description:综合路线JFrame的右qMMultiList事件处理器 </p> <p> Package:com.faw_qm.cappclients.conscapproute.view </p> <p> Copyright: Copyright
 * (c) 2005 </p> <p> Company:一汽启明 </p>
 * @author @version 1.0
 */

class compositiveRouteJFrame_qMMultiListR_actionAdapter implements java.awt.event.ActionListener
{
    //  综合路线用JFrame
    private CompositiveRouteJFrame adaptee;

    //  构造函数
    compositiveRouteJFrame_qMMultiListR_actionAdapter(CompositiveRouteJFrame adaptee)
    {
        this.adaptee = (CompositiveRouteJFrame)adaptee;
    }

    //事件处理
    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiListR_actionPerformed(e);
    }
}
