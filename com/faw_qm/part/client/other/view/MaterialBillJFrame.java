/** 生成程序MaterialBillJFrame.java	1.1  2003/05/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.part.client.other.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.view.EffClientViewIfc;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;


/**
 * <p>Title: 定制物料清单界面类。</p>
 * <p>用户根据需要对选定产品或部件定制BOM内容和输出方式，输出BOM表。<p>
 * <p>生成物料清单时，按当前产品结构管理器的配置规范确定零部件使用结构中<p>
 * <p>子件使用的版本，如果子件中没有符合当前配置规范的版本，则该件不出现<p>
 * <p>在物料清单中。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 2.0
 */

public class MaterialBillJFrame extends JFrame implements EffClientViewIfc
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;


    /**主面板*/
    private JPanel contentPane;

    /**用于显示输出属性面板*/
    private JPanel exportJPanel = new JPanel();

    /**功能面板*/
    private JPanel actionJPanel = new JPanel();

    /**条件选择面板*/
    private JPanel selectJPanel = new JPanel();
    private JPanel selectsubJPanel= new JPanel();
    
    private JPanel classeJPanel = new JPanel();

    /**控制按钮面板*/
    private JPanel buttonJPanel = new JPanel();

    /**“全部”选择按钮*/
    private JRadioButton allJRadioButton = new JRadioButton();

    /**“分级“选择按钮*/
    private JRadioButton gradeJRadioButton = new JRadioButton();

    /**“统计表”选择按钮*/
    private JRadioButton statisticsJRadioButton = new JRadioButton();

    /**“分类”选择按钮*/
    private JRadioButton classifyJRadioButton = new JRadioButton();

    /**“来源”选择框*/
    private JComboBox sourceJComboBox = new JComboBox();

    /**“类型”选择框*/
    private JComboBox typeJomboBox = new JComboBox();

    /**来源标签*/
    private JLabel sourceJLabel = new JLabel();

    /**类型标签*/
    private JLabel typeJLabel = new JLabel();

    /**状态条*/
    private JLabel statusJLabel = new JLabel();

    /**确定按钮*/
    private JButton okJButton = new JButton();

    /**取消按钮*/
    private JButton cancelJButton = new JButton();

    private TitledBorder titledBorder1;

    /**属性输出面板*/
    private ExportAttributesJPanel exportAttributeJPanel;

    /**控制类*/
    private EffAction controller;

    /**"全部"是否被选中*/
    private boolean all_flag;

    /**“全部”是否被选中的字符串表示*/
    private String all_String;

    /**“分级”是否被选中*/
    private boolean grade_flag;
    private String grade_String;

    /**“统计表”是否被选中*/
    private boolean statistics_flag;
    private String statistics_String;

    /**“分类”是否被选中*/
    private boolean classify_flag;
    private String classify_String;

    /**“来源”是否被选中*/
    private boolean source_flag;
    private String source_String;

    /**“类型”是否被选中*/
    private boolean type_flag;
    private String type_String;

    /**“统计表”是否被激活*/
    private String statistics_Enable;
    private boolean enable_statistics;

    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.part.client.other.util.OtherRB";

    /**零部件对象接口*/
    private QMPartIfc partIfc;

    /**系统定义的所有零部件类型的集合*/
    private QMPartType[] partType = QMPartType.getQMPartTypeSet();

    /**系统定义的所有零部件来源的集合*/
    private ProducedBy[] producedBy = ProducedBy.getProducedBySet();

    /**放置参数的哈西表*/
    private HashMap hashMap = new HashMap();

    /**来源的哈西表*/
    private HashMap produceMap = new HashMap();
    
    private JSeparator horizontalLine;

    /**界面布局管理器*/
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private GridBagLayout gridBagLayout7 = new GridBagLayout();
    
    //CCBegin by liunan 2008-08-12
    JButton exportButton = new JButton();
    //CCEnd by liunan 2008-08-12

    /**
     * 构造函数。
     * @param c  控制器。
     * @param part  零部件对象接口。
     */
    public MaterialBillJFrame(EffAction c, QMPartIfc part)
    {
        try
        {
            controller = c;
            this.partIfc = part;
            //实例化属性输出面板
            exportAttributeJPanel = new ExportAttributesJPanel();
            jbInit();
            //给按钮添加监听
            initView(null);
            //设置界面动态标题
            if (partIfc != null)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "MaterialTitle", null);
                this.setTitle(title + partIfc.getPartNumber() + " " +
                              partIfc.getPartName());
                String iconImage = QMMessage.getLocalizedMessage(
                        "com.faw_qm.part.client.main.util.QMProductManagerRB",
                        QMProductManagerRB.ICONIMAGE, null);
                setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
 //CCBegin by liunan 2008-08-12
    /**
     * 构造函数
     * @param c  控制器
     * @param part  零部件对象接口
     * @author 
     */
    public MaterialBillJFrame(EffAction c,QMPartIfc part,boolean bool)
    {
      try
      {
        controller =c;
        this.partIfc = part;
        //实例化属性输出面板
        exportAttributeJPanel = new ExportAttributesJPanel(bool);//add by liun
        jbInit();
        //给按钮添加监听
        initView(null);
        //设置界面动态标题
        if(partIfc!=null)
        {
          String title = QMMessage.getLocalizedMessage(RESOURCE,"MaterialTitle",null);
          this.setTitle(title + partIfc.getPartNumber()+" "+partIfc.getPartName());
          String iconImage = QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB", QMProductManagerRB.ICONIMAGE, null);
          setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    //CCEnd by liunan 2008-08-12

    /**
     * 初始化。
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {

        //Button
        horizontalLine = new JSeparator();
        cancelJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        okJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        classifyJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        classifyJRadioButton.setActionCommand("CLASSIFY");
        statisticsJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        statisticsJRadioButton.setActionCommand("STATISTICS");
        gradeJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        gradeJRadioButton.setActionCommand("GRADE");
        allJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        allJRadioButton.setActionCommand("ALL");
        okJButton.setText("Ok");
        cancelJButton.setText("Cancel");
        allJRadioButton.setText("All");
        gradeJRadioButton.setText("Grade");
        statisticsJRadioButton.setText("Statistics");
        classifyJRadioButton.setText("Classify");

        //JLabel
        sourceJLabel.setText("Source");
        typeJLabel.setText("Type");
        statusJLabel.setText("  ");
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setDoubleBuffered(true);
        typeJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        typeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        sourceJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        sourceJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sourceJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

        //this
        contentPane = (JPanel)this.getContentPane();
        titledBorder1 = new TitledBorder(QMMessage.getLocalizedMessage(RESOURCE,
                "out", null));
        contentPane.setLayout(gridBagLayout1);
        this.setSize(new Dimension(650, 500));
        setSelected(true, false, false, false, false, false, true);

        sourceJComboBox.addItem("");
        typeJomboBox.addItem("");
        actionJPanel.setBorder(titledBorder1);
        selectsubJPanel.setBorder(new TitledBorder(""));
        horizontalLine.setBounds(10, 247, 580, 2);

        //界面布局
        actionJPanel.setLayout(gridBagLayout3);
        selectJPanel.setLayout(gridBagLayout5);
        selectsubJPanel.setLayout(gridBagLayout7);
        classeJPanel.setLayout(gridBagLayout6);
        buttonJPanel.setLayout(gridBagLayout4);
        exportJPanel.setLayout(gridBagLayout2);
        
        //CCBegin by liunan 2008-08-12
        exportButton.setMaximumSize(new Dimension(75, 23));
        exportButton.setMinimumSize(new Dimension(75, 23));
        exportButton.setPreferredSize(new Dimension(75, 23));
        exportButton.setActionCommand("EXPORT");
        exportButton.setSelectedIcon(null);
        exportButton.setText("输出");
        //CCEnd by liunan 2008-08-12
        contentPane.add(exportJPanel,
                        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               //CCBegin by liunan 2011-06-20 扩大可选属性面积，美观。
                                               //new Insets(10, 10, 10, 10), 0, 0));
                                               new Insets(10, 10, 3, 10), 0, 0));
                                               //CCEnd by liunan 2011-06-20
        exportJPanel.add(exportAttributeJPanel,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
        contentPane.add(actionJPanel,
                        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               //CCBegin by liunan 2011-06-20 扩大可选属性面积，美观。
                                               //new Insets(8, 10, 7, 10), 0, 41));anan
                                               //CCEnd by liunan 2011-06-20
                                               new Insets(3, 10, 3, 10), 0, 41));
        actionJPanel.add(selectJPanel,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 104), 0, 0));
        actionJPanel.add(horizontalLine,
                new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER,
                        //CCBegin by liunan 2011-06-20 扩大可选属性面积，美观。
                        //GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 10, 3,
                        //CCEnd by liunan 2011-06-20
                                10), 0, 0));
        actionJPanel.add(classeJPanel, new GridBagConstraints(0, 2, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        selectJPanel.add(allJRadioButton, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE, new Insets(0, 1, 0, 70), 0, 0));
        selectJPanel.add(selectsubJPanel, new GridBagConstraints(1, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE, new Insets(0, -2, 0, 52), 0, 0));
        selectsubJPanel.add(gradeJRadioButton, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE, new Insets(0, -2, 0, 52), 0, 0));
        selectsubJPanel.add(statisticsJRadioButton, new GridBagConstraints(1, 0,
                1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0, 50, 0, 116), 0, 0));
        classeJPanel.add(classifyJRadioButton, new GridBagConstraints(0, 0, 1,
                1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        classeJPanel.add(sourceJLabel, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 10, 0, 0), 0, 0));
        classeJPanel.add(typeJLabel, new GridBagConstraints(3, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 8), 0, 0));
        classeJPanel.add(sourceJComboBox, new GridBagConstraints(2, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 0, 0));
        classeJPanel.add(typeJomboBox, new GridBagConstraints(4, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
       //CCBegin by liunan 2008-08-12
        //原代码如下
     /*   buttonJPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 8), 0, 0));*/
       buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0 ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, 
                new Insets(0, 0, 0, 8), 0, 0));
        buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0 ,GridBagConstraints.CENTER, GridBagConstraints.NONE, 
                new Insets(0, 0, 10, 8), 0, 0));
        buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0,
                0.0 ,GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));                
        //CCEnd by liunan 2008-08-12              
        contentPane.add(statusJLabel, new GridBagConstraints(0, 3, 1, 1, 1.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        contentPane.add(buttonJPanel, new GridBagConstraints(0, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 10), 0, 0));
        localize();
    }


    /**
     * 本地化。
     */
    public void localize()
    {
        //读取资源文件
        allJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "all", null));
        gradeJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "grade", null));
        statisticsJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "statistics", null));
        classifyJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "classify", null));
        sourceJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "source", null));
        typeJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "type", null));
        okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "ok", null));
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "cancel", null));
        //读取系统中的所有零部件类型
        for (int i = 0; i < partType.length; i++)
        {
            hashMap.put(partType[i],
                        partType[i].getDisplay(RemoteProperty.getVersionLocale()));
        }
        //向类型选择框添加零部件类型
        for (int i = 0; i < hashMap.size(); i++)
        {
            typeJomboBox.addItem(hashMap.get(partType[i]));

        }
        //读取系统中的所有零部件来源
        for (int i = 0; i < producedBy.length; i++)
        {
            produceMap.put(producedBy[i],
                           producedBy[i].getDisplay(RemoteProperty.
                    getVersionLocale()));
        }
        //向来源选择框添加零部件来源
        for (int i = 0; i < produceMap.size(); i++)
        {
            sourceJComboBox.addItem(produceMap.get(producedBy[i]));
        }

    }


    /**
     * 控制各选择按钮、选择框的显示状态。
     * @param s1 代表“全部”选择按钮的显示状态。
     * @param s2 代表“分级”选择按钮的显示状态。
     * @param s3 代表“统计表”选择按钮的显示状态。
     * @param s4 代表“分类”选择按钮的显示状态。
     * @param s5 代表“来源”选择框的显示状态。
     * @param s6 代表“类型”选择框的显示状态。
     * @param s7 代表“统计表”是否被激活。
     */
    public void setFlag(String s1, String s2, String s3, String s4, String s5,
                        String s6, String s7)
    {

        if (s1.equals("True"))
        {
            all_flag = true;
        }
        if (s1.equals("False"))
        {
            all_flag = false;
        }
        if (s2.equals("True"))
        {
            grade_flag = true;
        }
        if (s2.equals("False"))
        {
            grade_flag = false;
        }
        if (s3.equals("True"))
        {
            statistics_flag = true;
        }
        if (s3.equals("False"))
        {
            statistics_flag = false;
        }
        if (s4.equals("True"))
        {
            classify_flag = true;
        }
        if (s4.equals("False"))
        {
            classify_flag = false;
        }
        if (s5.equals("True"))
        {
            source_flag = true;
        }
        if (s5.equals("False"))
        {
            source_flag = false;
        }
        if (s6.equals("True"))
        {
            type_flag = true;
        }
        if (s6.equals("False"))
        {
            type_flag = false;
        }
        if (s7.equals("True"))
        {
            enable_statistics = true;
        }
        if (s7.equals("False"))
        {
            enable_statistics = false;
        }
    }


    /**
     * 获取可输出的属性的字符串,用来显示。
     * @return 返回一个字符串。
     */
    public String getAttribute()
    {
        return exportAttributeJPanel.getAttribute()[0];
    }


    /**
     * 获取可输出的属性的字符串，用来调服务。
     * @return 返回一个字符串。
     */
    public String getAttribute1()
    {
        return exportAttributeJPanel.getAttribute()[1];
    }


    /**
     * 设置状态栏。
     */
    public void setStatusBar()
    {
        String s = exportAttributeJPanel.getAttribute()[0];
        if(s==null||s.length()==0||s.equals(""))
        {
            s = " ";
        }
        statusJLabel.setText(s);
    }


    /**
     * 设置组件的选择状态。
     * @param s1  "全部"是否被选中。
     * @param s2  "分级"是否被选中。
     * @param s3  "统计表"是否被选中。
     * @param s4  "分类"是否被选中。
     * @param s5  "来源"是否被激活。
     * @param s6  "类型"是否被激活。
     * @param s7  "统计表"是否被激活。
     */
    public void setSelected(boolean s1, boolean s2, boolean s3, boolean s4,
                            boolean s5, boolean s6, boolean s7)
    {
        allJRadioButton.setSelected(s1); //(all_flag);
        gradeJRadioButton.setSelected(s2); //(grade_flag);
        statisticsJRadioButton.setSelected(s3); //(statistics_flag);
        classifyJRadioButton.setSelected(s4); //(classify_flag);
        sourceJComboBox.setEnabled(s5); //(source_flag);
        typeJomboBox.setEnabled(s6); //(type_flag);
        statisticsJRadioButton.setEnabled(s7); //(enable_statistics);
    }


    /**
     * 显示界面。
     */
    public void showView()
    {
        setVisible(true);
    }


    /**
     * 隐藏界面。
     */
    public void hideView()
    {
        setVisible(false);
    }


    /**
     * 激活界面。
     */
    public void activateView()
    {
        setEnabled(true);
    }


    /**
     * 冷冻界面。
     */
    public void passivateView()
    {
        setEnabled(false);
    }


    /**
     * 关闭界面。
     */
    public void closeView()
    {
        dispose();
    }


    /**
     * 获得界面上的录入信息。
     * @return 录入信息的集合。
     */
    public Collection getOutput()
    {
        Vector v = new Vector();
        //获得新选择的来源
        String source = sourceJComboBox.getSelectedItem().toString();
        PartDebug.trace(PartDebug.PART_CLIENT,
                        ">>>>>>>>>>>>!!!!!!!! source = " + source);
        PartDebug.trace(PartDebug.PART_CLIENT,
                        ":::::::::::::::::: size = " + producedBy.length);

        //如果选的是空白项（默认），则向集合中加一个空字符串，并退出本循环；
        //如果选择了某一来源，则向集合加入该来源
        for (int i = 0; i < producedBy.length; i++)
        {
            if (source.equals(""))
            {
                PartDebug.trace(PartDebug.PART_CLIENT,
                                "------s-----null--------------");
                v.add("");
                break;
            }
            else
            {
                if (source == producedBy[i].getDisplay(Locale.CHINA))
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    ">>>>>>>>>>>>!!!!!!!! source = " +
                                    producedBy[i]);
                    v.add(producedBy[i]);
                }
            }

        }

        //获得当前选中的类型
        String type = typeJomboBox.getSelectedItem().toString();
        PartDebug.trace(PartDebug.PART_CLIENT,
                        ">>>>>>>>>>>>!!!!!!!! type = " + type);

        //如果选的是空白项（默认），则向集合中加一个空字符串，并退出本循环；
        //如果选中了某一类型，则向集合中加入该类型
        for (int i = 0; i < partType.length; i++)
        {
            if (type.equals(""))
            {
                PartDebug.trace(PartDebug.PART_CLIENT,
                                "------t-----null--------------");
                v.add("");
                break;
            }
            else
            {
                if (type == partType[i].getDisplay(Locale.CHINA))
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    ">>>>>>>>>>>>!!!!!!!! type = " + partType[i]);
                    v.add(partType[i]);
                }
            }
        }

        return v;
    }


    /**
     * 更新界面(模型发生变化时通知界面，该函数被调用)。
     * @param observable 被观察者对象。
     * @param obj 被观察对象发来的参数。
     */
    public void update(Observable observable, Object obj)
    {
        Vector c = (Vector) obj;
        Iterator i = c.iterator();
        all_String = (String) i.next();
        grade_String = (String) i.next();
        statistics_String = (String) i.next();
        classify_String = (String) i.next();
        source_String = (String) i.next();
        type_String = (String) i.next();
        statistics_Enable = (String) i.next();
        setFlag(all_String, grade_String, statistics_String, classify_String,
                source_String, type_String, statistics_Enable);
        setSelected(all_flag, grade_flag, statistics_flag, classify_flag,
                    source_flag, type_flag, enable_statistics);
    }


    /**
     * 界面初始化函数。
     * @param params 输入参数的集合。
     */
    public void initView(Collection params)
    {
        //设置按钮的监听者
        allJRadioButton.addActionListener(controller);
        gradeJRadioButton.addActionListener(controller);
        statisticsJRadioButton.addActionListener(controller);
        classifyJRadioButton.addActionListener(controller);
        okJButton.addActionListener(controller);
        cancelJButton.addActionListener(controller);
        //CCBegin by liunan 2008-08-12
        exportButton.addActionListener(controller);
        //CCEnd by liunan 2008-08-12
    }
}
