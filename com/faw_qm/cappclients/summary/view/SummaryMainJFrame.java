 /**
 *CR1 郭晓亮  2009/05/06    原因: 点工艺汇总树右键后，出现快捷菜单，但焦点与当前
 *                                鼠标位置不一致，容易造成用户误操作        
 *                          方案: 点右键时清除当前树节点的选择,通过坐标选中节
 *                                点并弹出快捷菜单. 
 *                                
 * SS1 成都机加零部件一览表汇总，软化件零件一览表汇总  guoxiaoliang 2016-8-4                             
 */                                
package com.faw_qm.cappclients.summary.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.faw_qm.cappclients.beans.resourcetreepanel.ReportTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTreePanel;
import com.faw_qm.cappclients.summary.controller.SummaryMainController;
import com.faw_qm.cappclients.util.CappTree;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.beans.explorer.QMMenu;
import com.faw_qm.clients.beans.explorer.QMMenuItem;
import com.faw_qm.clients.beans.explorer.QMToolBar;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.summary.model.TotalSchemaItemInfo;


/**
 * <p>Title:工艺汇总主界面 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 李磊
 * @version 1.0
 * 问题(1) 2006.08.09 徐春英修改  修改原因: 添加界面从一种状态到另一种状态之前的信息提示
 */
public class SummaryMainJFrame extends JFrame
{
    private JPanel contentPane;
    private JMenuBar jMenuBar1 = new JMenuBar();
    private QMMenu summaryMenu = null;
    private QMMenu chooseMenu = null;
    private QMMenuItem viewItem = null;
    private QMToolBar qmToolBar = new QMToolBar();
    private JLabel statusBar = new JLabel();
    private QMMenuItem aboutItem = null;
    private QMMenuItem helpItem = null;
    private QMMenuItem eqItem = null;
    private QMMenuItem toolItem = null;
    private QMMenuItem mtItem = null;
    private QMMenuItem mainmtItem = null;
    private QMMenuItem mhItem = null;
    private QMMenuItem procedureItem = null;
    private QMMenuItem updateItem = null;
    private QMMenuItem deleteItem = null;
    private QMMenuItem resummaryItem = null;
    private QMMenuItem saveAsItem = null;
    private QMMenuItem renameItem = null;
    private QMMenu searchMenu = null;
    private QMMenuItem searchResultItem = null;
    private JMenuItem searchConditionItem = new JMenuItem();
    private QMMenu HelpMenu = null;
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JPanel TreePanel = new JPanel();
    private JPanel TechSummaryPanel = new JPanel();
    private JLabel summarytreeLabel = new JLabel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JLabel summarycontentLabel = new JLabel();
    private JPanel summaryContentPanel = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    public SummaryMainController mainControl;
    private ResourceTreePanel summaryTree;
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());
    protected static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";
    private JScrollPane jScrollPane1 = new JScrollPane();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private QMMenu configbar = null;
    private QMMenuItem configspecItem = null;
//    private QMMenuItem customizeSummaryItem = null;   取消定制汇总功能
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPopupMenu menu = new JPopupMenu();
    private EditAction viewMenu = null;
    private EditAction updateMenu = null;
    private EditAction deleteMenu = null;
    private EditAction renameMenu = null;
    private EditAction resumMenu = null;
    private EditAction saveasMenu = null;
    
    //薛凯 添加 20081104  增加树表右键菜单
    private JPopupMenu summaryPopupMenu = new JPopupMenu();
    private EditAction eqMenu = null;
    private EditAction pdMenu = null;
    private EditAction toolMenu = null;
    private EditAction mtMenu = null;
    private EditAction mainMTMenu = null;
    private EditAction mhMenu = null;
    //薛凯 添加结束 20081104 

    //CCBegin SS1
    
    private QMMenuItem parttableItem = null;
    private QMMenuItem parttableItem1 = null;
    //CCEnd SS1

    /**
     * 构造器
     * @param control ActionListener
     */
    public SummaryMainJFrame(ActionListener control)
    {
        try
        {
            this.mainControl = (SummaryMainController) control;
            jbInit();
            this.localize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //鼠标监听类
    MouseListener ml = new MouseAdapter()
    {
        //鼠标移入
        public void mouseEntered(MouseEvent e)
        {
            if (e.getSource() instanceof QMMenuItem)
            {
                QMMenuItem item = (QMMenuItem) e.getSource();
                statusBar.setText(item.getExplainText());
            }
            if (e.getSource() instanceof QMMenu)
            {
                QMMenu item = (QMMenu) e.getSource();
                statusBar.setText(item.getExplainText());
            }
            if (e.getSource() instanceof JButton)
            {
                JButton tool = (JButton) e.getSource();
                statusBar.setText(qmToolBar.getButtonDescription(tool.
                        getActionCommand()));
            }

        }


        //鼠标移出
        public void mouseExited(MouseEvent e)
        {
            statusBar.setText("要获得帮助，请点击“帮助”菜单或点击F1 键。");
        }
    };

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        statusBar.setText("要获得帮助，请点击“帮助”菜单或点击F1 键。");
        //加载图标
        this.setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/total.gif")).getImage());
        //初始化汇总树
        summaryTree = new ResourceTreePanel(res.getString("summarytree"),
                                            "TotalSchemaItem");
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        String[] str1 = this.getValueSet(res, "toolbar.icons");
        String[] str2 = this.getValueSet(res, "toolbar.text");
        String[] str3 = this.getValueSet(res, "toolbar.text1");
        setToolBar(str1, str2, str3);
        setCheckButton("total_view", false);
        setCheckButton("total_update", false);
        setCheckButton("total_delete", false);
        statusBar.setBackground(Color.white);
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.setMaximumSize(new Dimension(4, 22));
        statusBar.setMinimumSize(new Dimension(4, 22));
        statusBar.setPreferredSize(new Dimension(4, 22));
        statusBar.setText("");

        jSplitPane1.setDebugGraphicsOptions(0);
        jSplitPane1.setOpaque(true);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setDividerLocation(260);
        TreePanel.setLayout(gridBagLayout2);
        summarytreeLabel.setBorder(BorderFactory.createEtchedBorder());
        TechSummaryPanel.setLayout(gridBagLayout3);
        summaryContentPanel.setLayout(gridLayout1);
        summarycontentLabel.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane1.setBorder(null);
        summaryContentPanel.setBorder(BorderFactory.createEtchedBorder());
        
        //CCBegin SS1
        parttableItem = new QMMenuItem("机加零件一览表汇总界面",
                ml);
        parttableItem.setMnemonic('L');
        parttableItem1 = new QMMenuItem("软化件零件一览表汇总界面",
                ml);
        parttableItem1.setMnemonic('O');
        
        //CCEnd SS1
        
        

        summaryMenu = new QMMenu(res.getString("gatherMenu"), ml);
        summaryMenu.setMnemonic('G');
        mhItem = new QMMenuItem(res.getString("hourInterface"), ml);
        mhItem.setMnemonic('K');
        toolItem = new QMMenuItem(res.getString("packsInterface"), ml);
        toolItem.setMnemonic('T');
        mtItem = new QMMenuItem(res.getString("materInterface"), ml);
        mtItem.setMnemonic('I');
        eqItem = new QMMenuItem(res.getString("equipInterface"), ml);
        eqItem.setMnemonic('Q');
        mainmtItem = new QMMenuItem(res.getString("mainmaterInterface"), ml);
        mainmtItem.setMnemonic('N');
        procedureItem = new QMMenuItem(res.getString("workprefaceInterface"),
                                       ml);
        procedureItem.setMnemonic('W');
//        customizeSummaryItem = new QMMenuItem(res.getString(
//                "maketoorderInterface"), ml);
//        customizeSummaryItem.setMnemonic('C');

        if (!RemoteProperty.getProperty(
                "com.faw_qm.cappclients.summary.equipment",
                "false").equals("true"))
        {
            eqItem.setVisible(false);
        }

        if (!RemoteProperty.getProperty("com.faw_qm.cappclients.summary.tool",
                                        "false").equals("true"))
        {
            toolItem.setVisible(false);
        }

        if (!RemoteProperty.getProperty(
                "com.faw_qm.cappclients.summary.material",
                "false").equals("true"))
        {
            mtItem.setVisible(false);
        }

        if (!RemoteProperty.getProperty(
                "com.faw_qm.cappclients.summary.procedure",
                "false").equals("true"))
        {
            procedureItem.setVisible(false);
        }

        if (!RemoteProperty.getProperty(
                "com.faw_qm.cappclients.summary.main_material",
                "false").equals("true"))
        {
            mainmtItem.setVisible(false);
        }
        if (!RemoteProperty.getProperty(
                "com.faw_qm.cappclients.summary.man_hour",
                "false").equals("true"))
        {
            mhItem.setVisible(false);
        }
//        if (!RemoteProperty.getProperty(
//                "com.faw_qm.cappclients.summary.customize",
//                "false").equals("true"))
//        {
//            customizeSummaryItem.setVisible(false);
//        }

        chooseMenu = new QMMenu(res.getString("pickoutMenu"), ml);
        chooseMenu.setMnemonic('P');
        viewItem = new QMMenuItem(res.getString("view5Interface"), ml);
        viewItem.setMnemonic('V');
        updateItem = new QMMenuItem(res.getString("update5Interface"), ml);
        updateItem.setMnemonic('U');
        deleteItem = new QMMenuItem(res.getString("delete5Interface"), ml);
        deleteItem.setMnemonic('D');
        KeyStroke ksD = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);
        deleteItem.setAccelerator(ksD);
        resummaryItem = new QMMenuItem(res.getString("afreshInterface"), ml);
        resummaryItem.setMnemonic('R');
        saveAsItem = new QMMenuItem(res.getString("anothersaveInterface"), ml);
        saveAsItem.setMnemonic('G');
        renameItem = new QMMenuItem(res.getString("assignnameInterface"), ml);
        renameItem.setMnemonic('M');
        searchMenu = new QMMenu(res.getString("search5Menu"), ml);
        searchMenu.setMnemonic('S');
        searchResultItem = new QMMenuItem(res.getString("search5Interface"), ml);
        searchResultItem.setMnemonic('F');
        configbar = new QMMenu(res.getString("installInterface"), ml);
        configbar.setMnemonic('O');
        configspecItem = new QMMenuItem(res.getString("installMenu"), ml);
        configspecItem.setMnemonic('Z');
        HelpMenu = new QMMenu(res.getString("help5Menu"), ml);
        HelpMenu.setMnemonic('H');

        helpItem = new QMMenuItem(res.getString("gathermanagementInterface"),
                                  ml);
        helpItem.setMnemonic('G');
        KeyStroke SS = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        helpItem.setAccelerator(SS);
        aboutItem = new QMMenuItem(res.getString("aboutInterface"), ml);
        aboutItem.setMnemonic('A');
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.add(TreePanel, JSplitPane.LEFT);
        TreePanel.add(summarytreeLabel,
                      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(0, 0, 0, 0), 0, 0));
        TreePanel.add(summaryTree, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        jSplitPane1.add(TechSummaryPanel, JSplitPane.RIGHT);
        TechSummaryPanel.add(summarycontentLabel,
                             new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        TechSummaryPanel.add(jScrollPane1,
                             new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(summaryContentPanel, null);
        contentPane.add(qmToolBar, BorderLayout.NORTH);

        summaryMenu.add(eqItem);
        summaryMenu.add(toolItem);
        summaryMenu.add(mtItem);
        summaryMenu.add(mainmtItem);
        summaryMenu.add(mhItem);
        summaryMenu.add(procedureItem);
//        summaryMenu.add(customizeSummaryItem);

        //CCBegin SS1
        
        summaryMenu.add(parttableItem);
        summaryMenu.add(parttableItem1);
        //CCEnd SS1
        
        chooseMenu.add(viewItem);
        chooseMenu.add(updateItem);
        chooseMenu.add(deleteItem);
        chooseMenu.add(resummaryItem);
        chooseMenu.addSeparator();
        chooseMenu.add(saveAsItem);
        chooseMenu.add(renameItem);

        HelpMenu.add(helpItem);
        HelpMenu.add(aboutItem);

        jMenuBar1.add(summaryMenu);
        jMenuBar1.add(chooseMenu);
        jMenuBar1.add(searchMenu);
        jMenuBar1.add(configbar);
        jMenuBar1.add(HelpMenu);
        this.setJMenuBar(jMenuBar1);

        viewMenu = new EditAction(res.getString("view1"));
        menu.add(viewMenu);
        updateMenu = new EditAction(res.getString("update1"));
        menu.add(updateMenu);
        deleteMenu = new EditAction(res.getString("delete2"));
        //KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);
        //deleteMenu.setAccelerator(ks1);
        menu.add(deleteMenu);
        renameMenu = new EditAction(res.getString("rename"));
        menu.add(renameMenu);
        resumMenu = new EditAction(res.getString("resummary1"));
        menu.add(resumMenu);
        saveasMenu = new EditAction(res.getString("saveas1"));
        menu.add(saveasMenu);
        
        //薛凯 20081104  添加 汇总增加右键菜单
        eqMenu = new EditAction("设备汇总");
        eqMenu.setEnabled(true);
        summaryPopupMenu.add(eqMenu);
        pdMenu = new EditAction("工序汇总");
        pdMenu.setEnabled(true);
        summaryPopupMenu.add(pdMenu);
        toolMenu = new EditAction("工装汇总");
        toolMenu.setEnabled(true);
        summaryPopupMenu.add(toolMenu);
        mtMenu = new EditAction("辅助材料汇总");
        mtMenu.setEnabled(true);
        summaryPopupMenu.add(mtMenu);
        mainMTMenu = new EditAction("主要材料汇总");
        mainMTMenu.setEnabled(true);
        summaryPopupMenu.add(mainMTMenu);
        mhMenu = new EditAction("工时汇总");
        mhMenu.setEnabled(true);
        summaryPopupMenu.add(mhMenu);
        //薛凯 20081104 添加结束

        searchMenu.add(searchResultItem);
        searchMenu.add(searchConditionItem);
        configbar.add(configspecItem);
        searchConditionItem.setVisible(false);
        //汇总
        eqItem.addActionListener(mainControl);
        toolItem.addActionListener(mainControl);
        mtItem.addActionListener(mainControl);
        mainmtItem.addActionListener(mainControl);
        mhItem.addActionListener(mainControl);
        procedureItem.addActionListener(mainControl);
//        customizeSummaryItem.addActionListener(mainControl);
        searchResultItem.addActionListener(mainControl);
        configspecItem.addActionListener(mainControl);

        eqItem.setActionCommand("EQ");
        toolItem.setActionCommand("TOOL");
        mtItem.setActionCommand("MT");
        mainmtItem.setActionCommand("MAINMT");
        mhItem.setActionCommand("MH");
        procedureItem.setActionCommand("PD");
//        customizeSummaryItem.setActionCommand("CS");
        searchResultItem.setActionCommand("RESULTSEARCH");
        configspecItem.setActionCommand("CONFIGSPEC");

        viewItem.addActionListener(mainControl);
        updateItem.addActionListener(mainControl);
        deleteItem.addActionListener(mainControl);
        resummaryItem.addActionListener(mainControl);
        saveAsItem.addActionListener(mainControl);
        renameItem.addActionListener(mainControl);
        helpItem.addActionListener(mainControl);
        aboutItem.addActionListener(mainControl);
        //CCBegin SS1
        parttableItem.addActionListener(mainControl);
        parttableItem1.addActionListener(mainControl);
        parttableItem.setActionCommand("MPT");
        parttableItem1.setActionCommand("RPT");
        //CCEnd SS1
        viewItem.setActionCommand("VIEW");
        updateItem.setActionCommand("UPDATE");
        deleteItem.setActionCommand("DELETE");
        resummaryItem.setActionCommand("RESUM");
        saveAsItem.setActionCommand("SAVEAS");
        renameItem.setActionCommand("RENAME");
        helpItem.setActionCommand("HELP");
        aboutItem.setActionCommand("ABOUT");
        summaryTree.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                CappTreeObject obj = summaryTree.getSelectedObject();
                if (obj != null)
                {
                    enableMenuItems(obj);
                    enableToolBar(obj);
                }
                else
                {
                    setState(false);
                    setCheckButton("total_view", false);
                    setCheckButton("total_update", false);
                    setCheckButton("total_delete", false);
                }
            }
        });
        summaryTree.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                summaryTree_mouseReleased(e);
                if(e.getSource()instanceof CappTree) //CR1
                {
                	CappTree tree = (CappTree) e.getSource();
					tree.getSelectionModel().clearSelection();
					int selRow = tree.getRowForLocation(e.getX(), e.getY());
					if (selRow != -1)
					{
						tree.addSelectionRow(selRow);
					}
                }                                     //CR1
            }
        });
        //应用程序关闭程序
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                this_windowClosing(e);
            }
        });
        setState(false);
    }


    /**
     * 设置菜单状态
     * @param obj Object
     */
    private void enableMenuItems(Object obj)
    {
        if (obj instanceof ReportTreeObject)
        {
            setState(true);
        }
        else
        {
            setState(false);
        }
    }


    /**
     * 设置工具条状态
     * @param obj Object
     */
    private void enableToolBar(Object obj)
    {
        if (obj instanceof ReportTreeObject)
        {
            setCheckButton("total_view", true);
            setCheckButton("total_update", true);
            setCheckButton("total_delete", true);
        }
        else
        {
            setCheckButton("total_view", false);
            setCheckButton("total_update", false);
            setCheckButton("total_delete", false);
        }
    }


    /**
     * 设置弹出菜单的状态
     * @param obj Object
     */
    private void enablePopMenu(Object obj)
    {
        if (obj instanceof ReportTreeObject)
        {
            setPopState(true);
        }
        else
        {
            setPopState(false);
        }
    }


    /**
     * 关闭应用程序
     * @param e 窗口事件
     */
    public void this_windowClosing(WindowEvent e)
    {
        if (mainControl.getViewState() == true)
        {
            // 显示确认对话框
            int reply = JOptionPane.showConfirmDialog(this,
                    res.getString(
                    "so this will exit system"),
                    res.getString("exit1"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.YES_OPTION)
            {
                Class[] theClass =
                        {
                        HashMap.class};
                Object[] obj =
                        {
                        mainControl.getHashMap()};
                try
                {
                    mainControl.getServiceObject(
                            "ResourceService",
                            "unlock", theClass, obj);
                    //mainControl.getHashMap().removeAllElements();
                }
                catch (QMException ex)
                {
                    String s1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "warning", null);
                    JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                                  s1,
                                                  JOptionPane.WARNING_MESSAGE);
                }
                System.exit(0);
            }
        }
        else
        {
            this.dispose();
        }
    }


    /**
     * 设置工具栏，获得按钮信息显示出来,带浮动文字
     * @param str1
     * @param str2
     */
    private void setToolBar(String[] str1, String[] str2, String[] str3)
    {
        for (int i = 0; i < str1.length; i++)
        {
            qmToolBar.addButton(str1[i], str2[i], str3[i], mainControl, ml);
        }
        for (int ii = 0; ii < qmToolBar.getComponentCount(); ii++)
        {
            if (qmToolBar.getComponentAtIndex(ii) instanceof JButton)
            {
                JButton jb = (JButton) (qmToolBar.getComponentAtIndex(ii));
                jb.setBorder(BorderFactory.createEtchedBorder());
            }
        }
        this.repaint();
    }


    /**
     * 从资源文件中得到图标的信息
     * @param rb
     * @param key
     * @return
     */
    private String[] getValueSet(ResourceBundle rb, String key)
    {
        String[] values = null;
        try
        {
            String value = rb.getString(key);
            StringTokenizer st = new StringTokenizer(value, ",");

            int count = st.countTokens();
            values = new String[count];

            for (int i = 0; i < count; i++)
            {
                values[i] = st.nextToken();
            }
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return values;
    }


    /**
     * 用资源文件进行本地初试化
     */
    private void localize()
    {
        this.setTitle(res.getString("technics_summary_browser"));
        eqItem.setText(res.getString("equipment"));
        toolItem.setText(res.getString("tool"));
        //CCBegin SS1
        parttableItem.setText("机加零件一览表(L)");
        parttableItem1.setText("软化件零件一览表(O)");
        //CCEnd SS1
        mtItem.setText(res.getString("material"));
        procedureItem.setText(res.getString("procedure"));
        mainmtItem.setText(res.getString("main_material"));
        mhItem.setText(res.getString("manhour"));
//        customizeSummaryItem.setText(res.getString("customizesummary1"));
        summaryMenu.setText(res.getString("summary1"));
        chooseMenu.setText(res.getString("choose"));
        viewItem.setText(res.getString("view"));
        updateItem.setText(res.getString("update"));
        deleteItem.setText(res.getString("delete1"));
        resummaryItem.setText(res.getString("resummary"));
        saveAsItem.setText(res.getString("saveas"));
        renameItem.setText(res.getString("rename1"));
        searchMenu.setText(res.getString("search1"));
        searchResultItem.setText(res.getString("search_summary_result"));
        searchConditionItem.setText(res.getString("search_summary_condition"));
        HelpMenu.setText(res.getString("help"));
        helpItem.setText(res.getString("summaryManage"));
        aboutItem.setText(res.getString("about"));
        configbar.setText(res.getString("configSpec"));
        configspecItem.setText(res.getString("configSpec1"));
        summarytreeLabel.setText(res.getString("technics_summary_tree"));
        summarycontentLabel.setText(res.getString("summary_content"));
    }


    /**
     * 设置菜单显示的状态
     * @param flag boolean
     */
    public void setState(boolean flag)
    {
        viewItem.setEnabled(flag);
        updateItem.setEnabled(flag);
        deleteItem.setEnabled(flag);
        resummaryItem.setEnabled(flag);
        saveAsItem.setEnabled(flag);
        renameItem.setEnabled(flag);
    }


    /**
     * 设置弹出菜单的状态
     * @param flag boolean
     */
    private void setPopState(boolean flag)
    {
        viewMenu.setEnabled(flag);
        updateMenu.setEnabled(flag);
        deleteMenu.setEnabled(flag);
        renameMenu.setEnabled(flag);
        resumMenu.setEnabled(flag);
        saveasMenu.setEnabled(flag);
    }


    /**
     * 添加一个汇总信息到树上
     * @param info TotalSchemaItemInfo
     */
    public void addTotalToTree(TotalSchemaItemInfo info)
    {
        summaryTree.addResource(new ReportTreeObject(info));
    }


    /**
     * 访问汇总树
     * @return ResourceTreePanel
     */
    public ResourceTreePanel getSummaryTree()
    {
        return summaryTree;
    }


    /**
     *加入具体的资源panel
     * @param panel JPanel
     */
    public void addSummaryPanel(JPanel panel)
    {
        summaryContentPanel.add(panel, null);
    }


    /**
     * 从SummaryContentPanel 上移掉具体的panel
     */
    public void removePanel()
    {
        summaryContentPanel.removeAll();
        this.repaint();
    }


    /**
     *根据汇总内容，设置summarycontentLabel的值
     */
    public void setSumLabel(String s)
    {
        summarycontentLabel.setText(s);
    }


    /**
     * 判断主面板上是否有控件
     * @return boolean
     */
    public boolean isVisiable()
    {
        return summaryContentPanel.getComponentCount() != 0;
    }


    /**
     * 设置状态栏的值
     * @param s String
     */
    public void setSumStatus(String s)
    {
        statusBar.setText(s);
    }


    /**
     * 设置图标的可用性
     * @param str String
     * @param flag boolean
     */
    public void setCheckButton(String str, boolean flag)
    {
        qmToolBar.setEnabled(str, flag);
    }


    /**
     * 树节点鼠标事件监听
     * @param e MouseEvent
     */
    private void summaryTree_mouseReleased(MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            if (e.getSource() instanceof CappTree)
            {
                CappTreeObject sumTreeObject = summaryTree.getSelectedObject();
                if (sumTreeObject != null &&
                    sumTreeObject instanceof ReportTreeObject)
                {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                    enablePopMenu(sumTreeObject);
                }
                //薛凯 20081104 添加 汇总增加右键菜单
                else if(sumTreeObject != null &&
                        (sumTreeObject instanceof com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTypeObject))
                {
                	summaryPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                else if(sumTreeObject == null)
                {
                	summaryPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                //薛凯 20081104 添加结束
            }
        }
    }


    /**
     * <p>Title: 内部类</p>
     * <p>Description: 工艺汇总树弹出菜单</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author 李磊
     * @version 1.0
     */
    class EditAction extends AbstractAction
    {

        String action = "";
        String view = res.getString("view1");
        String update = res.getString("update1");
        String delete = res.getString("delete2");
        String rename = res.getString("rename");
        String resum = res.getString("resummary1");
        String saveas = res.getString("saveas1");
        
        //薛凯 添加 20081104 汇总增加右键菜单
        String eq = "设备汇总";
        String tool = "工装汇总";
        String pd = "工序汇总";
        String mt = "辅助材料汇总";
        String mainMT = "主要材料汇总";
        String mh = "工时汇总";
        //薛凯 添加结束 20081104 
        

        /**
         * 构造器
         * 弹出式菜单选项
         * @param name String
         */
        public EditAction(String name)
        {
            super(name);
            action = name;
        }


        /**
         * 事件监听
         * @param e ActionEvent
         * 问题(1) 2006.08.09 徐春英修改  修改原因: 添加界面从一种状态到另一种状态之前的信息提示
         */
        public void actionPerformed(ActionEvent e)
        {
            if (action.equals(view))
            {
                if(mainControl.preprocess())
                {
                    mainControl.openVIEW();
                }
            }
            if (action.equals(update))
            {
                if(mainControl.preprocess())
                {
                    mainControl.openUPDATE();
                }
            }
            if (action.equals(delete))
            {
                if(mainControl.preprocess())
                {
                    mainControl.openDELETE();
                }
            }
            if (action.equals(rename))
            {
                if(mainControl.preprocess())
                {
                    mainControl.openRENAME();
                }
            }
            if (action.equals(resum))
            {
                if(mainControl.preprocess())
                {
                    mainControl.openRESUM();
                }
            }
            if (action.equals(saveas))
            {
                if(mainControl.preprocess())
                {
                    mainControl.openSAVEAS();
                }
            }
            
            //薛凯 添加 20081104 汇总增加右键菜单
            if (action.equals(eq))
            {
            	if(mainControl.preprocess())
                {
                    mainControl.openEquipment();
                }
            }
            if (action.equals(tool))
            {
            	if(mainControl.preprocess())
                {
                    mainControl.openTool();
                }
            }
            if (action.equals(pd))
            {
            	if(mainControl.preprocess())
                {
                    mainControl.openPD();
                }
            }
            if (action.equals(mt))
            {
            	if(mainControl.preprocess())
                {
                    mainControl.openMT();
                }
            }
            if (action.equals(mh))
            {
            	if(mainControl.preprocess())
                {
                    mainControl.openMH();
                }
            }
            if (action.equals(mainMT))
            {
            	if(mainControl.preprocess())
                {
                    mainControl.openMainMT();
                }
            }
            //薛凯 添加结束 20081104
        }


        /**
         * setAccelerator
         *
         * @param ks KeyStroke
         */
        private void setAccelerator(KeyStroke ks)
        {
        }
    }
}
