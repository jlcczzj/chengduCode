/** 生成程序UsesJPanel.java	1.1  2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 添加结构件时使用新的搜索界面，利用视图属性区分是中心的件还是解放的件。 guoxiaoliang 2016-4-20
 */
package com.faw_qm.part.client.design.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.prodmgmt.Spinner;
import com.faw_qm.clients.util.EnumeratedChoice;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.part.client.design.model.UsageInterfaceItem;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsesInterfaceList;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.Unit;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.other.view.BasicSearchJDialog;
import com.faw_qm.persist.util.PersistHelper;


/**
 * <p>Title:编辑使用结构面板。 </p>
 * <p>Description: 零部件使用结构关联的可视化界面。在本面板可以对零部件的</p>
 * <P>使用关联进行创建、更新、删除、查看等维护工作。</P>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class UsesJPanel extends HelperPanel
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**使用结构列表的ID列标记*/
    public static final int ID_COLUMN = 0;


    /**使用结构列表的编号列标记*/
    public static final int NUMBER_COLUMN = 1;


    /**使用结构列表的名称列标记*/
    public static final int NAME_COUMN = 2;


    /**使用结构列表的数量列标记*/
    public static final int QTY_COLUMN = 3;


    /**使用结构列表的单位列标记*/
    public static final int UNIT_COLUMN = 4;


    /**用于标记资源文件路径*/
    protected static final String RESOURCE
            = "com.faw_qm.part.client.design.util.PartDesignViewRB";


    /**使用添加和删除的容器*/
    protected UsesInterfaceList usesList = null;


    /**判断界面模式的布尔变量*/
    protected boolean updateMode = false;


    /**添加按钮*/
    private JButton addUsageJB = new JButton();


    /**移去按钮*/
    private JButton removeUsageJB = new JButton();


    /**查看按钮*/
    private JButton viewUsageJB = new JButton();


    /**界面上放置使用列表的面板*/
    private JPanel usesPartJP = new JPanel();


    /**界面上放置按钮(添加、移去、查看)的面板*/
    private JPanel usesButtonJP = new JPanel();


    /**界面上放置usesButtonJPanel和updateUsageJPanel的面板*/
    private JPanel actionJP = new JPanel();


    /**界面上放置数量微调器和单位选择框的面板*/
    private JPanel updateUsageJP = new JPanel();


    /**数量标签*/
    private JLabel quantityJL = new JLabel();


    /**单位标签*/
    private JLabel unitJL = new JLabel();


    /**使用列表*/
    private QMMultiList usesMultiList = new QMMultiList();


    /**数量微调器*/
    private Spinner quantitySpinner = new Spinner();


    /**界面的网格包布局*/
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();


    /**当前创建或更新的零部件值对象*/
    private QMPartIfc partIfc1 = null;


    /**单位选择框*/
    private EnumeratedChoice unitChoice = new EnumeratedChoice();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    
    //CCBegin SS1
    private MyQuery cappQuery = new MyQuery();
//CCEnd SS1

    /**
     * 构造函数。
     */
    public UsesJPanel()
    {
        super();
        try
        {
            this.jbInit();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


    /**
     * 初始化。
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        this.setSize(new Dimension(620, 206));
        this.setLayout(gridBagLayout5);
        //JLabel
        unitJL.setFont(new java.awt.Font("Dialog", 0, 12));
        unitJL.setHorizontalAlignment(SwingConstants.RIGHT);
        unitJL.setHorizontalTextPosition(SwingConstants.RIGHT);
        unitJL.setText("Unit:");
        unitJL.setBounds(new Rectangle(5, 34, 41, 18));
        quantityJL.setFont(new java.awt.Font("Dialog", 0, 12));
        quantityJL.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityJL.setHorizontalTextPosition(SwingConstants.RIGHT);
        quantityJL.setText("Quantity:");
        quantityJL.setBounds(new Rectangle(5, 9, 41, 18));
        //JButton
        addUsageJB.setBounds(new Rectangle(19, 0, 59, 23));
        addUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
        addUsageJB.setMaximumSize(new Dimension(65, 23));
        addUsageJB.setMinimumSize(new Dimension(65, 23));
        addUsageJB.setPreferredSize(new Dimension(83, 23));
        addUsageJB.setMnemonic('A');
        addUsageJB.setText("Add");
        removeUsageJB.setBounds(new Rectangle(19, 42, 59, 23));
        removeUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
        removeUsageJB.setMaximumSize(new Dimension(65, 23));
        removeUsageJB.setMinimumSize(new Dimension(65, 23));
        removeUsageJB.setPreferredSize(new Dimension(83, 23));
        removeUsageJB.setMnemonic('R');
        removeUsageJB.setText("Remove");
        viewUsageJB.setBounds(new Rectangle(19, 84, 59, 23));
        viewUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
        viewUsageJB.setMaximumSize(new Dimension(65, 23));
        viewUsageJB.setMinimumSize(new Dimension(65, 23));
        viewUsageJB.setPreferredSize(new Dimension(83, 23));
        viewUsageJB.setMnemonic('V');
        viewUsageJB.setText("View");

        //设置列数
        usesMultiList.setNumberOfCols(5);
        usesMultiList.setMultipleMode(true);
        //设置范围
        usesMultiList.setBounds(new Rectangle(0, 0, 493, 192));
        usesMultiList.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                usesMultiList_itemStateChanged(e);
            }
        });
        usesMultiList.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                usesMultiList_actionPerformed(e);
            }
        });
        //设置表头
        usesMultiList.setHeadings(new String[]
                                  {"ID", "Number", "Name", "Qty",
                                  "Unit"});
        //设置列对齐
        usesMultiList.setColumnAlignments(new String[]
                                          {"left", "left", "left",
                                          "right", "left"});
        //设置列宽
        usesMultiList.setRelColWidth(new int[]
                                     {0, 3, 3, 2, 2});
        //添加列表选择监听
        usesMultiList.getSelectModel().addListSelectionListener(new
                UsesSelection());
        //设置单元格不可编辑
        usesMultiList.setCellEditable(false);
        //设置最小值
        quantitySpinner.setMin(0);
        //设置最大值
        quantitySpinner.setMax(99999);

        quantitySpinner.addMinListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                quantitySpinner_propertyChange(e);
            }
        });
        quantitySpinner.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quantitySpinner_actionPerformed(e);
            }
        });
        //设置数量的增量
        quantitySpinner.setIncrement(1);
        //添加键盘操作监听
        quantitySpinner.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                quantitySpinner_keyReleased(e);
            }
        });
        //设置允许回滚
        quantitySpinner.setWrappable(false);

        //JPanel
        usesPartJP.setLayout(gridBagLayout4);
        unitChoice.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                unitChoice_itemStateChanged(e);
            }
        });
        usesButtonJP.setLayout(gridBagLayout1);
        actionJP.setLayout(gridBagLayout3);
        updateUsageJP.setLayout(gridBagLayout2);

        usesButtonJP.add(removeUsageJB,
                         new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 15, 0));
        usesButtonJP.add(viewUsageJB,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 15, 0));
        usesButtonJP.add(addUsageJB,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(2, 8, 0, 8), 15, 0));
        updateUsageJP.add(unitJL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.5
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(1, 2, 0, 0), 5, 0));
        updateUsageJP.add(quantitySpinner,
                          new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 5, 0, 0), 24, -6));
        updateUsageJP.add(quantityJL,
                          new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 2, 0, 0), 5, 0));
        updateUsageJP.add(unitChoice,
                          new GridBagConstraints(1, 1, 1, 1, 0.0, 0.5
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 5, 0, 0), -66,
                                                 -2));
        actionJP.add(usesButtonJP, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.SOUTH, GridBagConstraints.NONE,
                new Insets(3, 6, 0, 32), -14, 0));
        actionJP.add(updateUsageJP, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(0, 1, 0, 8), 0, 43));
        this.add(actionJP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 0, 0, 0), 0, 0));
        this.add(usesPartJP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 6, 5, 0), 0, 0));
        usesPartJP.add(usesMultiList,
                       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 2, 5, 0), 0, 0));

        //}}

        //设置单位的枚举值
        unitChoice.setEnumeratedTypeClassName("com.faw_qm.part.util.Unit");

        localize();
        addUsageJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addUsageJButton_actionPerformed(e);
            }
        });
        removeUsageJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                removeUsageJButton_actionPerformed(e);
            }
        });
        viewUsageJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewUsageJButton_actionPerformed(e);
            }
        });
       
    }
//CCBegin SS1
    /**扩展父类的目的是使查询结果列表支持鼠标双击功能*/
    class MyQuery extends CappQuery {
        public CappMultiList getResultList() {
            return this.getMyList();
        }
    }
    
    //CCEnd SS1
    
    /**
     * 实现了列表选取监听，当选中了使用结构列表中的一个零部件时，可对该零部件进行修改。
     */
    class UsesSelection implements ListSelectionListener
    {
        /**
         * 如果已选中了使用结构列表中的一个零部件，则激活数量微调器、单位选择器、移去
         * 按钮和查看按钮，将数量微调器的值设置为使用结构列表中当前所选中零部件的数量
         * 值，将单位选择器的值选择为使用结构列表中当前所选中零部件的单位值；否则，数
         * 量微调器、单位选择器、移去按钮和查看按钮处于未激活状态。
         * @param e ListSelectionEvent
         */
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting())
            {
                PartDebug.debug("valueChanged（event） begin ...", this,
                                PartDebug.PART_CLIENT);
                int i = usesMultiList.getSelectedRow();
                if (i != -1)
                {
                    if (updateMode)
                    {
                        quantitySpinner.setEnabled(true);
                        quantitySpinner.setEditable(true);
                        unitChoice.setEnabled(true);
                        removeUsageJB.setEnabled(true);
                    }
                    viewUsageJB.setEnabled(true);

                    try
                    {
                        //获取当前所选中零部件的数量值的十进制数据格式值
                        Number number = UsageItem.getDecimalFormat().parse(
                                usesMultiList.getCellText(i, QTY_COLUMN));
                        //获得当前所选中零部件的数量的双精度值
                        double d = number.doubleValue();
                        //将数量微调器的值设置为使用结构列表中当前所选中零部件的数量值
                        quantitySpinner.setCurrentDoubleValue(d);
                        Object obj = (Object) usesMultiList.getCellText(i,
                                UNIT_COLUMN);
                        //将单位选择器的值选择为使用结构列表中当前所选中零部件的单位值
                        unitChoice.setSelectedItem(obj);
                        //设置再次选中已选中的零部件
                        usesMultiList.setSelectedRow(i);
                    }
                    catch (PropertyVetoException pve)
                    {
                        pve.printStackTrace();
                    }
                    catch (java.text.ParseException pe)
                    {
                        pe.printStackTrace();
                    }

                }
                else
                {
                    quantitySpinner.setEnabled(false);
                    quantitySpinner.setEditable(false);
                    unitChoice.setEnabled(false);
                    viewUsageJB.setEnabled(false);
                    removeUsageJB.setEnabled(false);
                }
                PartDebug.debug("valueChanged（event） end...return is void", this,
                                PartDebug.PART_CLIENT);
            }
        }
    }


    /**
     * 该方法被线程WorkThread内部调用，以初始化变量。
     */
    public void setPartItem()
    {
        PartDebug.debug("setPartItem() begin....", this, PartDebug.PART_CLIENT);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //获得当前PartItem 使用的零部件的列表。
        //性能瓶颈。
        usesList = getPartItem().getUsesInterfaceList();
        //对属性进行初始化,更新容器
        usesList.startFrame();
        //把当前PartItem 使用的所有零部件加入使用结构列表中
        usesMultiList.setListItems(usesList.toMultiList());
        //设置默认选中第一行
        if (usesList.toMultiList() != null &&
            usesList.toMultiList().length != 0)
        {
            usesMultiList.setSelectedRow(0);
            usesMultiList_itemStateChanged(null);
        }
        setCursor(Cursor.getDefaultCursor());
        PartDebug.debug("setPartItem() end....return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 保存所有已发生的变化。
     * @return  如果保存成功，则返回为真,否则返回false。
     * @exception QMRemoteException
     */
    public boolean save()
            throws QMRemoteException
    {
        PartDebug.debug("save() begin ....", this, PartDebug.PART_CLIENT);
        //设置使用结构列表中的所有零部件的数量和单位
        setValues();

        PartDebug.debug("save() end ....return is boolean", this,
                        PartDebug.PART_CLIENT);
        //对容器内的所有对象更新，如果从上一次保存后有所改变返回true，否则返回false。
        return usesList.saveFrame();

    }


    /**
     * 设置使用结构列表中的每个零部件的数量和单位。
     */
    protected void setValues()
    {
        PartDebug.debug("setValues() begin ....", this, PartDebug.PART_CLIENT);
        //使用列表的行数
        int size = usesMultiList.getNumberOfRows();
        //设置使用列表中的数量和单位值
        for (int row = 0; row < size; row++)
        {
            //根据当前零部件的ID获取集合中的对应的使用关联项UsageInterfaceItem
            UsageInterfaceItem usage =
                    usesList.interfaceElementAt(usesMultiList.getCellText(row,
                    0));

            float gui_quantity = 0; //数量的浮点值
            //设置数量值
            try
            {
                //获取当前零部件的数量值的十进制数据格式值
                Number number = UsageItem.getDecimalFormat().parse(
                        usesMultiList.getCellText(row, QTY_COLUMN));
                gui_quantity = number.floatValue();
            }
            catch (java.text.ParseException pe)
            {
                pe.printStackTrace();
            }
            //如果使用关联项中的数量不等于当前零部件的数量值
            if (usage.getQuantity() != gui_quantity)
            {
                //设置数量为当前零部件的数量
                usage.setQuantity(gui_quantity);
            }

            //设置单位值
            //当前所选中的单位
            Unit gui_unit = getSelectedUnit(row);
            //使用关联项中的数量单位
            Unit saved_unit = usage.getUnits();
            //如果使用关联项中的数量单位为空,或者不等于当前零部件的单位，则设置
            //使用关联项中的数量单位为当前零部件的单位
            if (saved_unit == null)
            {
                //设置单位为当前零部件的单位
                usage.setUnit(gui_unit);
            }
            else if (!saved_unit.equals(gui_unit))
            {
                usage.setUnit(gui_unit);
            }

        }
        PartDebug.debug("setValues() end ....return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 获得使用结构列表中指定行的单位。
     * @param row 使用结构列表中指定的行。
     * @return foundUnit 使用结构列表中指定行的单位。
     */
    public Unit getSelectedUnit(int row)
    {
        PartDebug.debug("getSelectedUnit(int row) begin ...", this,
                        PartDebug.PART_CLIENT);
        Unit foundUnit = null;
        //指定行的单位值（字符串）
        String stringUnit = usesMultiList.getCellText(row, UNIT_COLUMN);
        //获得单位的所有内容类型
        Unit[] types = Unit.getUnitSet();
        //如果指定零部件的单位与某单位内容类型相符，则获得该单位内容类型
        for (int i = 0; i < types.length; i++)
        {
            if (stringUnit.equals(types[i].getDisplay()))
            {
                foundUnit = types[i];
                break;
            }
        }
        PartDebug.debug("getSelectedUnit(int row) end...return " + foundUnit, this,
                        PartDebug.PART_CLIENT);
        return foundUnit;
    }


    /**
     * 本地化方法。
     */
    protected void localize()
    {
        try
        {
            // labels
            quantityJL.setText(getLabelsRB().getString("quantityJLabel"));
            unitJL.setText(getLabelsRB().getString("unitJLabel"));

            // buttons
            viewUsageJB.setText(getLabelsRB().getString("viewUsageJButton"));
            addUsageJB.setText(getLabelsRB().getString("addUsageJButton"));
            removeUsageJB.setText(getLabelsRB().getString("removeUsageJButton"));

            // headings
            usesMultiList.setHeadings(new String[]
                                      {
                                      getLabelsRB().getString("idHeading"),
                                      getLabelsRB().getString("numberHeading"),
                                      getLabelsRB().getString("nameHeading"),
                                      getLabelsRB().getString("quantityHeading"),
                                      getLabelsRB().getString("unitHeading")});

        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }


    /**
     * 初始化帮助系统。
     */
    public void initializeHelp()
    {
    }


    /**
     * 查看使用结构列表中被选中的项。
     */
    public void viewSelectedItem()
    {
        PartDebug.debug("viewSelectedItem() begin....", this,
                        PartDebug.PART_CLIENT);
        try
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            usesMultiList.setCursor(Cursor.getPredefinedCursor(Cursor.
                    WAIT_CURSOR));
            //使用结构列表中被选中的项
            int i = usesMultiList.getSelectedRow();

            if (i != -1)
            {
            	//////////////////////begin I//////////////////////////////
            	//add by muyp 20080507
            	//修改原因：对零部件主信息对象进行查看，页面显示不正确(TD1754)
            	BaseValueIfc bvi = usesMultiList.getSelectedObject();
            	if(bvi instanceof QMPartMasterIfc)
            	{
            		 QMPartMasterIfc master = (QMPartMasterIfc) bvi;
                     String masterBsoID = master.getBsoID();
                     ///////////////////begin II///////////////
                     //调用服务
                     ServiceRequestInfo info = new ServiceRequestInfo();
                     info.setServiceName("StandardPartService");
                     info.setMethodName("findPart");
                     Class[] theClass = {QMPartMasterIfc.class};
                     info.setParaClasses(theClass);
                     Object[] objs = {master};
                     info.setParaValues(objs);
                     /////////////////end II///////////////
                     RequestServer server = RequestServerFactory.getRequestServer();
                     try {
         				Collection links = (Collection) server.request(info);
         				if(links != null)
         				{
                             Iterator iter = links.iterator();
                             String partID = "";
                             if (iter.hasNext())//如果有值，则取出值
                             {
                             	QMPartIfc link = (QMPartIfc) iter.next();
                                 partID = link.getBsoID();
                             }
                             HashMap hashmap = new HashMap();
                             hashmap.put("BsoID", masterBsoID);
                             hashmap.put("bsoID", partID);
                             RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
                             		hashmap);
                             masterBsoID = null;
                             partID = null;
                         }//end if 1
         			} catch (QMRemoteException e) {
         				
         				e.printStackTrace();
         			}
            	}///end if(bvi instanceof QMPartMasterIfc)
            	else
            	{
            		/////////////////////////end I/////////////////////////////////
//            		根据使用列表中所选项的ID获得容器中对应的使用关联项
                    UsageInterfaceItem usageInterfaceItem =
                            usesList.interfaceElementAt(usesMultiList.getCellText(i,
                            0));
                    //获得所选零部件的主信息
                    QMPartMasterIfc master =
                            (QMPartMasterIfc) usageInterfaceItem.getUsedBy();
                    //获得当前的筛选条件项
                    ConfigSpecItem configSpecItem = getPartItem().getConfigSpecItem();

                    //如果筛选条件项为空，获得当前客户端的筛选条件
                    if (configSpecItem == null)
                    {
                        PartConfigSpecIfc partConfigSpecIfc = null;

                        // 获得最近保存的筛选条件
                        try
                        {
                            partConfigSpecIfc =
                                    (PartConfigSpecIfc) PartHelper.getConfigSpec();
                        }
                        catch (QMRemoteException qre)
                        {
                            String title = QMMessage.getLocalizedMessage(RESOURCE,
                                    "exception", null);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    qre.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        //如果筛选条件不为空，用筛选条件构造筛选条件项，否则新建筛选条件项
                        if (partConfigSpecIfc != null)
                        {
                            configSpecItem = new ConfigSpecItem(partConfigSpecIfc);
                        }
                        else
                        {
                            configSpecItem = new ConfigSpecItem();
                        }

                    }

                    //根据零部件主信息获取该零部件的所有符合配置规范的版本
                    Vector partInfoVector = PartHelper.getAllVersions(master,
                            configSpecItem.getConfigSpecInfo());
                    PartDebug.debug("*** 该零部件的所有符合配置规范的版本:" + partInfoVector,
                                    PartDebug.PART_CLIENT);
                    Object[] qmPartIfc = partInfoVector.toArray();
                    PartDebug.debug("****************" + qmPartIfc, this,
                                    PartDebug.PART_CLIENT);
                    //如果为空，显示信息：“没有符合条件的版本”
                    if (qmPartIfc == null)
                    {
                        try
                        {
                            Object[] params =
                                              {
                                              ((QMPartMasterInfo) master).
                                              getIdentifyObject().
                                              getIdentity()};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.NO_QUA_VERSION, params);
                            String title = getLocalizedValue("errorTitle");
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message,
                                    title,
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        catch (QMException ex)
                        {
                            String title = QMMessage.getLocalizedMessage(RESOURCE,
                                    "exception", null);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    ex.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else if (qmPartIfc.length == 0)
                    {
                        try
                        {
                            Object[] params =
                                              {
                                              ((QMPartMasterInfo) master).
                                              getIdentifyObject().
                                              getIdentity()};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.NO_QUA_VERSION, params);
                            String title = getLocalizedValue("errorTitle");
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message,
                                    title,
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        catch (QMException ex)
                        {
                            String title = QMMessage.getLocalizedMessage(RESOURCE,
                                    "exception", null);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    ex.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    //获取所选中零部件的最新版本
                    QMPartIfc part = null;
                    PartDebug.debug("$$$$$$$$ 所选中零部件的最新版本: " + qmPartIfc[0],
                                    PartDebug.PART_CLIENT);

                    if (qmPartIfc[0] instanceof Object[])
                    {
                        Object[] partObj = (Object[]) qmPartIfc[0];
                        part = (QMPartIfc) partObj[0];
                        PartDebug.debug("+++++++ 所选中零部件的最新版本:" + part,
                                        PartDebug.PART_CLIENT);
                    }

                    //如果不为空，查看该零部件（最新版本）的属性（转到查看零部件属性页面）
                    if (part != null)
                    {
                        PartDebug.debug("-----------进入查看零部件属性页面------------",
                                        PartDebug.PART_CLIENT);
                        String partID = part.getBsoID();
                        HashMap map = new HashMap();
                        map.put("bsoID", partID);
                        map.put("flag", "0");
                        RichToThinUtil.toWebPage(
                                "Part-Other-PartLookOver-001-0A.do", map);
                        PartDebug.debug("UsesJPanel:RichToThinUtil Successed !",
                                        PartDebug.PART_CLIENT);
                    }
            	}
            }
        }

        catch (QMRemoteException qre)
        {
            qre.printStackTrace();
            String title = getLocalizedValue("informationTitle");
            JOptionPane.showMessageDialog(getParentFrame(),
                                          qre.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        finally
        {
            usesMultiList.setCursor(Cursor.getDefaultCursor());
            setCursor(Cursor.getDefaultCursor());
        }
        PartDebug.debug("viewSelectedItem() end ....return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 界面刷新通知。
     */
    public void addNotify()
    {
        super.addNotify();
    }

   
    /**
     * 运行搜索器搜索要使用的新零部件。
     * @param event the action event
     */
    void addUsageJButton_actionPerformed(ActionEvent event)
    {
    	
    	//CCBegin SS1
    	
//        PartDebug.debug("addUsageJButton_actionPerformed(e) begin...", this,
//                        PartDebug.PART_CLIENT);
//        //获得父窗口
//        JFrame frame = getParentFrame();
//        String partBsoName = QMMessage.getLocalizedMessage(RESOURCE,
//                PartDesignViewRB.QMPM_BSONAME, null);
//        String title = QMMessage.getLocalizedMessage(RESOURCE,
//                "findPartTitle", null);
//
//        //定义搜索器
//        QmChooser qmChooser = new QmChooser(partBsoName, title, frame);
//        qmChooser.setChildQuery(false);
//        qmChooser.setRelColWidth(new int[]
//                                 {1, 1});
//        PartDebug.debug("************创建搜索零部件成功！———qmChooser = " + qmChooser,
//                        PartDebug.PART_CLIENT);
//        //按照给定条件，执行搜索
//        qmChooser.addListener(new QMQueryListener()
//        {
//
//            public void queryEvent(QMQueryEvent e)
//            {
//                qmChooser_queryEvent(e);
//            }
//        });
//
//        qmChooser.setVisible(true);
//        PartDebug.debug(
//                "addUsageJButton_actionPerformed(e) end...return is void", this,
//                PartDebug.PART_CLIENT);
    	
//    	CappSchema mySchema=null;
//    	
//        String SCHEMA = "C:QMPart; G:搜索条件;A:partNumber;A:partName;A:versionValue;A:viewName";
//        //定义查询方案
//        try {
//        	 mySchema = new CappSchema(SCHEMA);
//        }
//        catch (QMRemoteException ex) {
//          String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
//                  null);
//          JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
//                  JOptionPane.INFORMATION_MESSAGE);
//         return ;
//        }
//        //设置查询方案
//        cappQuery.setSchema(mySchema);
////        cappQuery.notAcessInPersonalFolder();
//        cappQuery.setLastIteration(true);
//        
//        cappQuery.setVisible(true);

        
    	 AddSearchJDialog searchDialog = new AddSearchJDialog( getParentFrame());
         searchDialog.setSize(700, 650);
         //使界面在屏幕中央显示
         PartScreenParameter.setLocationCenter(searchDialog);
         searchDialog.setVisible(true);

         
         //CCEnd SS1
    	
    	
    }
    
  //CCBegin SS1
    
    /**
     * 搜索零部件监听事件方法。
     * @param e 搜索监听事件。
     */
    public void qmChooser_queryEvent2(CappQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                        PartDebug.PART_CLIENT);
        //当前创建或更新的零部件值对象
        //2003/12/18
        if (this.getPartItem() != null)
        {
            partIfc1 = this.getPartItem().getPart();
        }
        PartDebug.debug("当前创建或更新的零部件值对象 partIfc1 = " + partIfc1,
                        PartDebug.PART_CLIENT);
        if (e.getType().equals(CappQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(CappQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
            	 MyQuery c = (MyQuery) e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("获得所选择的所有零部件 bvi = " + bvi,
                                PartDebug.PART_CLIENT);
                if (bvi != null)
                {
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                        PartDebug.PART_CLIENT);
                        //所选择的某一零部件
                        BaseValueIfc newPart = bvi[i];
                        //获得零部件的主信息
//                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)newPart;
                        //获得要添加的零部件的最新小版本
                        QMPartIfc partIfc2 = (QMPartIfc)newPart;
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)partIfc2.getMaster();
                        PartDebug.debug("获得要添加的零部件的最新小版本 partIfc2 = " +
                                        partIfc2, PartDebug.PART_CLIENT);
                        //用于判断partIfc1和partIfc2是否使用结构嵌套
                        boolean flag = false;
                        //如果当前是更新零部件界面，则检验partIfc1和partIfc2是否使用结构嵌套
                        if (partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = this.isParentPart(partIfc1, partIfc2);
                        }

                        //如果返回为真，提示使用结构嵌套。否则，进一步检查是否重复添加
                        if (flag == true)
                        {
                            //通过标识工厂获得与给定值对象对应的显示标识对象。
                            DisplayIdentity displayidentity = IdentityFactory.
                                    getDisplayIdentity(partIfc2);
                            //获得对象类型 + 标识
                            String s = displayidentity.getLocalizedMessage(null);
                            Object[] params ={s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.
                                    PART_CF_NESTING,
                                    params);
                            String title = getLocalizedValue(
                                    PartDesignViewRB.
                                    USE_CF_NESTING);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message, title,
                                    JOptionPane.WARNING_MESSAGE);
                            

                        }
                        else
                        {
                            //处理零部件使用关系的模型类
                            UsageInterfaceItem usageInterfaceItem =
                                    new UsageInterfaceItem(getPartItem(),
                                    newPartMaster);
                            //为被添加的零部件设置默认单位
                            usageInterfaceItem.setUnit(newPartMaster.
                                    getDefaultUnit());
                            //把通过检查的零部件加入容器
                            usesList.insertElementAt(usageInterfaceItem, 0);
                        }
                    }
                }
                else
                {
                    return;
                }
                setValues();
                //清空使用结构列表
                usesMultiList.clear();
                //把搜索获得的零部件添加入使用结构列表中
                usesMultiList.setListItems(usesList.toMultiList());
                //设置默认选中第一项
                usesMultiList.setSelectedRow(0);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                        PartDebug.PART_CLIENT);
    }
    //CCEnd SS1

    /**
     * 根据零部件的主信息，获得零部件的最新小版本。
     * @param partMaster  零部件的主信息。
     * @return  零部件的最新小版本。
     */
    private QMPartIfc getLastedIterations(QMPartMasterIfc partMaster)
    {
        PartDebug.debug("getLastedIterations() begin...", this,
                        PartDebug.PART_CLIENT);
        QMPartIfc partIfc = null;
        //调用服务方法，获得零部件的所有小版本
        Class[] paraClass =
                            {
                            MasteredIfc.class};
        Object[] objs =
                        {
                        partMaster};
        Collection collection = null;
        try
        {
            collection = (Collection) IBAUtility.invokeServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(getParentFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //获得零部件的最新小版本
            partIfc = (QMPartIfc) iterator.next();
        }
        PartDebug.debug("getLastedIterations() end...return " + partIfc, this,
                        PartDebug.PART_CLIENT);
        return partIfc;
    }


    /**
     * 检验零部件part2是否是零部件part1的祖先部件或是part1本身。
     * @param part1  指定的零部件的值对象。
     * @param part2  被检验的零部件的值对象。
     * @return 如果零部件part2是零部件part1的祖先部件或是part1本身，则返回true；否则返回false。
     */
    private boolean isParentPart(QMPartIfc part1, QMPartIfc part2)
    {
        PartDebug.debug("isParentPart() begin...", this, PartDebug.PART_CLIENT);
        Boolean flag1 = new Boolean(false);
        try
        {
            //调用服务方法，判断partIfc1和partIfc2是否使用结构嵌套
            flag1 = Boolean.valueOf(PartServiceRequest.isParentPart(part1,
                    part2));
            PartDebug.debug("是否使用结构嵌套(Boolean) flag1 = " + flag1,
                            PartDebug.PART_CLIENT);
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(getParentFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        boolean flag = flag1.booleanValue();
        PartDebug.debug("isParentPart() end...return is " + flag, this,
                        PartDebug.PART_CLIENT);
        return flag;

    }


    /**
     * 搜索零部件监听事件方法。
     * @param e 搜索监听事件。
     */
    public void qmChooser_queryEvent(QMQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                        PartDebug.PART_CLIENT);
        //当前创建或更新的零部件值对象
        //2003/12/18
        if (this.getPartItem() != null)
        {
            partIfc1 = this.getPartItem().getPart();
        }
        PartDebug.debug("当前创建或更新的零部件值对象 partIfc1 = " + partIfc1,
                        PartDebug.PART_CLIENT);
        if (e.getType().equals(QMQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser) e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("获得所选择的所有零部件 bvi = " + bvi,
                                PartDebug.PART_CLIENT);
                if (bvi != null)
                {
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                        PartDebug.PART_CLIENT);
                        //所选择的某一零部件
                        BaseValueIfc newPart = bvi[i];
                        //获得零部件的主信息
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)
                                newPart;
                        //获得要添加的零部件的最新小版本
                        QMPartIfc partIfc2 = this.getLastedIterations(
                                newPartMaster);
                        PartDebug.debug("获得要添加的零部件的最新小版本 partIfc2 = " +
                                        partIfc2, PartDebug.PART_CLIENT);
                        //用于判断partIfc1和partIfc2是否使用结构嵌套
                        boolean flag = false;
                        //如果当前是更新零部件界面，则检验partIfc1和partIfc2是否使用结构嵌套
                        if (partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = this.isParentPart(partIfc1, partIfc2);
                        }

                        //如果返回为真，提示使用结构嵌套。否则，进一步检查是否重复添加
                        if (flag == true)
                        {
                            //通过标识工厂获得与给定值对象对应的显示标识对象。
                            DisplayIdentity displayidentity = IdentityFactory.
                                    getDisplayIdentity(partIfc2);
                            //获得对象类型 + 标识
                            String s = displayidentity.getLocalizedMessage(null);
                            Object[] params =
                                              {s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.
                                    PART_CF_NESTING,
                                    params);
                            String title = getLocalizedValue(
                                    PartDesignViewRB.
                                    USE_CF_NESTING);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message, title,
                                    JOptionPane.WARNING_MESSAGE);

                        }
                        else
                        {
                            //处理零部件使用关系的模型类
                            UsageInterfaceItem usageInterfaceItem =
                                    new UsageInterfaceItem(getPartItem(),
                                    newPartMaster);
                            //为被添加的零部件设置默认单位
                            usageInterfaceItem.setUnit(newPartMaster.
                                    getDefaultUnit());
                            //把通过检查的零部件加入容器
                            usesList.insertElementAt(usageInterfaceItem, 0);
                        }
                    }
                }
                else
                {
                    return;
                }
                setValues();
                //清空使用结构列表
                usesMultiList.clear();
                //把搜索获得的零部件添加入使用结构列表中
                usesMultiList.setListItems(usesList.toMultiList());
                //设置默认选中第一项
                usesMultiList.setSelectedRow(0);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 设置界面显示模式。
     * @param update_mode 如果为真，则显示添加、移去、查看三按钮。否则，上述三按钮
     * 及数量、单位操作都不可见。
     */
    public void setUpdateMode(boolean update_mode)
    {
        PartDebug.debug("setUpdateMode() begin...updateMode=" + update_mode, this,
                        PartDebug.PART_CLIENT);
        this.updateMode = update_mode;

        if (update_mode)
        {
            updateUsageJP.setVisible(true); //数量，单位
            updateUsageJP.setEnabled(false);
            addUsageJB.setVisible(true);
            removeUsageJB.setVisible(true);
            removeUsageJB.setEnabled(false);
            viewUsageJB.setVisible(true);
            viewUsageJB.setEnabled(false);
            unitChoice.setEnabled(false);
            quantitySpinner.setEnabled(false);
        }
        else
        {
            addUsageJB.setEnabled(false);
            removeUsageJB.setEnabled(false);
            updateUsageJP.setEnabled(false);
            updateUsageJP.setVisible(false);
            addUsageJB.setVisible(false);
            removeUsageJB.setVisible(false);
            usesButtonJP.remove(addUsageJB);
            usesButtonJP.remove(removeUsageJB);
        }
        PartDebug.debug("setUpdateMode() end...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 获得界面显示模式。
     * @return updateMode
     */

    public boolean getUpdateMode()
    {
        return updateMode;
    }


    /**
     * 获得单位选择器中所选择的单位。
     * @return 所选择的单位。
     */
    public Unit getSelectedUnit()
    {
        Unit foundUnit = (Unit) unitChoice.getSelectedEnumeratedType();
        return foundUnit;
    }


    /**
     * 点击移去按钮实现的功能：删除选中的零部件，如果没有选中，则移去和查看按钮
     * 失效，数量和单位操作失效，数量为0。
     * @param event ActionEvent
     */
    void removeUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        PartDebug.debug("removeUsageJButton_actionPerformed（event） begin ...", this,
                        PartDebug.PART_CLIENT);
        //获得所选中的零部件
        //modify by skx 2004.9.21 支持批量删除使用关系
        int[] i = usesMultiList.getSelectedRows();
        int m = 0;
        for (int j = 0; j < i.length; j++)
        {
            if (i[j] != -1)
            {
                //将选中的零部件从容器里移出
                usesList.removeElementAt(usesMultiList.getCellText(i[j] - m, 0));
                //将选中的零部件从使用结构列表上删除
                usesMultiList.removeRow(i[j] - m);
                m++;
                //如果没有选中零部件,移去和查看按钮失效，数量和单位操作失效，数量为0
                if (usesMultiList.getSelectedRow() == -1)
                {
                    if (updateMode)
                    {
                        quantitySpinner.setEnabled(false);
                        quantitySpinner.setEditable(false);
                        unitChoice.setEnabled(false);
                        removeUsageJB.setEnabled(false);

                        try
                        {
                            quantitySpinner.setCurrentDoubleValue(0.0);
                        }
                        catch (PropertyVetoException pve)
                        {
                            pve.printStackTrace();
                        }

                    }

                    viewUsageJB.setEnabled(false);
                }

            }
        }
        PartDebug.debug(
                "removeUsageJButton_actionPerformed（event） end...return is void", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * 查看零部件属性。
     * @param event ActionEvent
     */
    void viewUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        viewSelectedItem();
    }


    /**
     * 选中某一零部件后，改变数量，选中的零部件的数量随之改变。
     * @param event ActionEvent
     */
    void quantitySpinner_actionPerformed(java.awt.event.ActionEvent event)
    {
        int i = usesMultiList.getSelectedRow(); //所选中的行
        if (i != -1)
        {
            //向使用结构列表中的数量值赋予微调器中的当前值。
            usesMultiList.addTextCell(i, QTY_COLUMN,
                                      quantitySpinner.getCurrentText());
            usesMultiList.setSelectedRow(i);
        }
    }


    /**
     * 如果已选中了使用结构列表中的一个零部件，则激活数量微调器、单位选择器、移去
     * 按钮和查看按钮，将数量微调器的值设置为使用结构列表中当前所选中零部件的数量
     * 值，将单位选择器的值选择为使用结构列表中当前所选中零部件的单位值；否则，数
     * 量微调器、单位选择器、移去按钮和查看按钮处于未激活状态。
     * @param event ItemEvent
     */
    void usesMultiList_itemStateChanged(java.awt.event.ItemEvent event)
    {
        PartDebug.debug("usesMultiList_itemStateChanged（event） begin ...", this,
                        PartDebug.PART_CLIENT);
        int i = usesMultiList.getSelectedRow();
        if (i != -1)
        {
            if (updateMode)
            {
                quantitySpinner.setEnabled(true);
                quantitySpinner.setEditable(true);
                unitChoice.setEnabled(true);
                removeUsageJB.setEnabled(true);
            }

            viewUsageJB.setEnabled(true);

            try
            {
                Number number = UsageItem.getDecimalFormat().parse(
                        usesMultiList.getCellText(i, QTY_COLUMN));
                double d = number.doubleValue();
                //将数量微调器的值设置为使用结构列表中当前所选中零部件的数量值
                quantitySpinner.setCurrentDoubleValue(d);
                Object obj = (Object) usesMultiList.getCellText(i, UNIT_COLUMN);
                //将单位选择器的值选择为使用结构列表中当前所选中零部件的单位值
                unitChoice.setSelectedItem(obj);
                usesMultiList.setSelectedRow(i);
            }
            catch (PropertyVetoException pve)
            {
            }
            catch (java.text.ParseException pe)
            {
            }

        }
        else
        {
            quantitySpinner.setEnabled(false);
            quantitySpinner.setEditable(false);
            unitChoice.setEnabled(false);
            viewUsageJB.setEnabled(false);
            removeUsageJB.setEnabled(false);
        }
        PartDebug.debug(
                "usesMultiList_itemStateChanged（event） end...return is void", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * 点击列表中的某一项，查看该项。
     * @param event  ActionEvent
     */
    void usesMultiList_actionPerformed(java.awt.event.ActionEvent event)
    {
        viewSelectedItem();
    }


    /**
     * 选中某一零部件后，改变单位,选中的零部件的单位随之改变。
     * @param event ItemEvent
     */
    void unitChoice_itemStateChanged(java.awt.event.ItemEvent event)
    {
        int i = usesMultiList.getSelectedRow();
        if (i != -1)
        {
            //向使用结构列表中的单位值赋予单位选择器中的当前值。
            String s = unitChoice.getSelectedItem().toString();
            usesMultiList.addTextCell(i, UNIT_COLUMN, s);
            usesMultiList.setSelectedRow(i);

        }
    }


    /**
     * 选中一个零部件后，改变数量微调器的值，则使用结构列表中的数量值相应改变。
     * @param event PropertyChangeEvent
     */
    void quantitySpinner_propertyChange(java.beans.PropertyChangeEvent event)
    {
        int selected_row = usesMultiList.getSelectedRow();
        if (selected_row != -1)
        {
            //向使用结构列表中的数量值赋予微调器中的当前值。
            usesMultiList.addTextCell(selected_row, QTY_COLUMN,
                                      quantitySpinner.getCurrentText());
            usesMultiList.setSelectedRow(selected_row);
        }
    }


    /**
     * 当手工输入数量时，向使用结构列表中的数量值赋予微调器中的当前值。
     * @param e KeyEvent
     */
    void quantitySpinner_keyReleased(KeyEvent e)
    {
        int selected_row = usesMultiList.getSelectedRow();
        if (selected_row != -1)
        {
            //向使用结构列表中的数量值赋予微调器中的当前值。
            usesMultiList.addTextCell(selected_row, QTY_COLUMN,
                                      quantitySpinner.getCurrentText());
            usesMultiList.setSelectedRow(selected_row);
        }

    }


    /**
     * 移除按钮。
     */
    public void removeButtons()
    {
        usesButtonJP.remove(addUsageJB);
        usesButtonJP.remove(removeUsageJB);
        actionJP.remove(updateUsageJP);
    }
    //CCBegin SS1
    class AddSearchJDialog extends JDialog {
        /** 查询类 */
        private MyQuery cappQuery = new MyQuery();

        /** 要查询的业务对象 */
        public  String SCHEMA;

        /** 查询方案 */
        private CappSchema mySchema;

        private GridBagLayout gridBagLayout1 = new GridBagLayout();

        /**产品结构管理器主界面*/
        private JFrame managerJFrame = new JFrame();

        /** 用于标记资源文件路径 */
        protected  String RESOURCE = "com.faw_qm.cappclients.capprouye.util.CappRouteRB";

        /** 测试变量 */
        private  boolean verbose = (RemoteProperty.getProperty(
                "com.faw_qm.cappclients.verbose", "true")).equals("true");
        
        Hashtable copyCodingClassifications=null;
        
        public AddSearchJDialog(JFrame frame){
        	
        	  super(frame, "", true);
              
              
              SCHEMA = "C:QMPart; G:搜索条件;A:partNumber;A:partName;A:versionValue;A:viewName";
              //定义查询方案
              try {
                mySchema = new CappSchema(SCHEMA);
              }
              catch (QMRemoteException ex) {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                        null);
                JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
               return ;
              }
              //设置查询方案
              cappQuery.setSchema(mySchema);
//              cappQuery.notAcessInPersonalFolder();
              cappQuery.setLastIteration(true);

              
              
              setSingleSelectMode();
              
              managerJFrame = frame;
              try {
                  jbInit();
                  
                  addDefaultListener();
                  
              } catch (Exception e) {
                  e.printStackTrace();
              }

        	
        }

      

        /**
         * 初始化
         *
         * @throws Exception
         */
        private void jbInit() throws Exception {
            this.setTitle("搜索零件");
            this.setSize(650, 500);
            this.getContentPane().setLayout(gridBagLayout1);
            //界面布局管理
            this.getContentPane().add(
                    cappQuery,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 10, 0, 10), 0, 0));
        }

        /**
         * 添加默认查询监听
         */
        public void addDefaultListener() {
            cappQuery.addListener(new CappQueryListener() {
                public void queryEvent(CappQueryEvent e) {
                    cappQuery_queryEvent(e);
                }
            });
        }

        /**
         * 添加查询监听
         *
         * @param s
         *            查询监听
         */
        public void addQueryListener(CappQueryListener s) {
            cappQuery.addListener(s);
        }

        /**
         * 删除查询监听
         *
         * @param s
         *            查询监听
         */
        public void removeQueryListener(CappQueryListener s) {
            cappQuery.removeListener(s);
        }
  

        /**
         * 搜索监听事件方法
         *
         * @param e
         *            搜索监听事件
         */
        public void cappQuery_queryEvent(CappQueryEvent e) {
        	
        	UsesJPanel.this.qmChooser_queryEvent2(e);
          

            
        }
        
       
        

        /**
         * 设置列表为单选模式
         */
        public void setSingleSelectMode() {
            try {
                cappQuery.setMultipleMode(false);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
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



        /**
         * MultiList添加事件
         * @param lis
         */
        public void addMultiListActionListener(ActionListener lis) {
            this.cappQuery.getResultList().addActionListener(lis);
        }

    }
  //CCEnd SS1
    
    
}



