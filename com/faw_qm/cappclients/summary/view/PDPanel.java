package com.faw_qm.cappclients.summary.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.cappclients.summary.controller.SummaryPDController;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.summary.model.TotalResultIfc;


/**
 * <p>Title:工序汇总界面 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 李磊
 * @version 1.0
 * 问题（1）2006.08.11 徐春英修改 清空分页显示的缓存结果
 * 问题（2）2006.08.11  徐春英修改  输出定制完成后，multilist里有很多空行，应该清空
 * 问题(3)20070625 徐春英修改 修改原因:汇总条件列表没有添加"工艺编号"这个条件
 * 问题(4)2007.07.19 徐春英修改 修改原因:如果工艺编号为空字符串则返回null
 * 问题(5)2007.11.05 薛凯修改 修改原因:汇总结果按数值排序不正确
* 问题（6）20080227 薛凯 修改 修改原因：工艺汇总条件显示错误
 * SS1 轴齿中心汇总，默认部门  2013-12-26 文柳 
 */

public class PDPanel extends JPanel
{
    private JPanel jPanel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel2 = new JPanel();
    private JButton summaryButton = new JButton();
    private JButton interruptButton = new JButton();
    private JButton customizeButton = new JButton();
    private JPanel jPanel4 = new JPanel();
    private JLabel partLabel = new JLabel();
    private JLabel techcategoryLabel = new JLabel();
    private JLabel departLabel = new JLabel();
    private JTextField partTextField = new JTextField();
    private JButton searchButton = new JButton();
    private JCheckBox partCheckBox = new JCheckBox();
    private JCheckBox nottechCheckBox = new JCheckBox();
    private JCheckBox notprocedureCheckBox = new JCheckBox();
    private JCheckBox notdepartmentCheckBox = new JCheckBox();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JPanel jPanel3 = new JPanel();
    private JButton saveButton = new JButton();
    private JButton exportButton = new JButton();
    private JButton exitButton = new JButton();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private JLabel pdtypeLabel = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private SortingSelectedPanel sortingSelectedPanel1 = null;
    private SummaryPDController pdControl;
    private CappMultiList multiList = new CappMultiList();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private TotalResultIfc totalResult;
    private String[] attrDisplayname;
    private String part;
    private int size;
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());


    //private static boolean verbose = (RemoteProperty.getProperty(
    //"com.faw_qm.summary.verbose", "true")).equals("true");
    private Vector vector;
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private SortingSelectedPanel sortingSelectedPanel2;
    private SortingSelectedPanel sortingSelectedPanel3;
    private PaginatePanel paginatePanel1 = new PaginatePanel();

    //2007.03.29
    private JLabel numberLabel = new JLabel();
    //2007.03.29 徐春英添加
    private JTextField technicField = new JTextField();

    //问题（7）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
    JCheckBox notTechnicsSelected = new JCheckBox();
    JButton searchTechnics = new JButton();



    /**
     * 不带参数的构造函数
     */
    public PDPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 带一个参数的构造函数
     * @param control ActionListener
     */
    public PDPanel(ActionListener control)
    {
        super();
        try
        {
            this.pdControl = (SummaryPDController) control;
            jbInit();
            localize();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 初始化函数
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        String procedureType = res.getString("procedure_type");
        sortingSelectedPanel1 = new SortingSelectedPanel(procedureType,
                "QMProcedure", "stepClassification");
        //sortingSelectedPanel1 = new SortingSelectedPanel("工序类别", "工艺内容");
        sortingSelectedPanel1.setIsSelectCC(true);
        sortingSelectedPanel1.setDialogTitle(procedureType);
        String department = res.getString("department");
        //CCBegin SS1
        String temCom = getUserFromCompany();
        if(temCom.equals("zczx")){
        	sortingSelectedPanel2 = new SortingSelectedPanel("轴齿中心","组织机构");
        	sortingSelectedPanel2.setIsSelectCC(true);
        	sortingSelectedPanel2.setDialogTitle(department);
        }else{
        	 sortingSelectedPanel2 = new SortingSelectedPanel(department
                     , "QMProcedure", "workShop");
             sortingSelectedPanel2.setIsSelectCC(true);
             sortingSelectedPanel2.setDialogTitle(department);
        }

        //CCEnd SS1
       
        String technicsType = res.getString("technics_category");
        sortingSelectedPanel3 = new SortingSelectedPanel(technicsType
                , "QMTechnicsMaster", "technicsType");
        sortingSelectedPanel3.setIsSelectCC(true);
        sortingSelectedPanel3.setDialogTitle(technicsType);
        this.setLayout(gridBagLayout5);
        jPanel1.setLayout(gridBagLayout2);
        summaryButton.setMaximumSize(new Dimension(114, 23));
        summaryButton.setMinimumSize(new Dimension(114, 23));
        summaryButton.setPreferredSize(new Dimension(114, 23));

        interruptButton.setMaximumSize(new Dimension(114, 23));
        interruptButton.setMinimumSize(new Dimension(114, 23));
        interruptButton.setPreferredSize(new Dimension(114, 23));
        interruptButton.setEnabled(false);

        customizeButton.setMaximumSize(new Dimension(114, 23));
        customizeButton.setMinimumSize(new Dimension(114, 23));
        customizeButton.setPreferredSize(new Dimension(114, 23));
        customizeButton.setHorizontalAlignment(SwingConstants.LEFT);
        customizeButton.setHorizontalTextPosition(SwingConstants.LEFT);

        jPanel2.setLayout(gridBagLayout1);
        jPanel4.setLayout(gridBagLayout3);

        searchButton.setMaximumSize(new Dimension(80, 23));
        searchButton.setMinimumSize(new Dimension(80, 23));
        searchButton.setPreferredSize(new Dimension(80, 23));

        jPanel3.setLayout(gridBagLayout4);
        saveButton.setMaximumSize(new Dimension(97, 23));
        saveButton.setMinimumSize(new Dimension(97, 23));
        saveButton.setPreferredSize(new Dimension(97, 23));
        saveButton.setEnabled(false);

        exportButton.setMaximumSize(new Dimension(97, 23));
        exportButton.setMinimumSize(new Dimension(97, 23));
        exportButton.setPreferredSize(new Dimension(97, 23));
        exportButton.setEnabled(false);

        exitButton.setMaximumSize(new Dimension(97, 23));
        exitButton.setMinimumSize(new Dimension(97, 23));
        exitButton.setPreferredSize(new Dimension(97, 23));

        customizeButton.addActionListener(pdControl);
        exitButton.addActionListener(pdControl);
        summaryButton.addActionListener(pdControl);
        interruptButton.addActionListener(pdControl);
        searchButton.addActionListener(pdControl);
        saveButton.addActionListener(pdControl);
        exportButton.addActionListener(pdControl);
        saveButton.setActionCommand("SAVE");
        saveButton.setMnemonic('V');
        customizeButton.setActionCommand("CUSTO");
        exitButton.setActionCommand("EXIT");
        exitButton.setMnemonic('X');
        summaryButton.setActionCommand("SUM");
        summaryButton.setMnemonic('T');
        interruptButton.setActionCommand("INTERRUPT");
        interruptButton.setMnemonic('B');
        searchButton.setActionCommand("SEARCH");
        exportButton.setActionCommand("EXPORT");
        exportButton.setMnemonic('R');
        sortingSelectedPanel1.setButtonSize(80, 23);
        sortingSelectedPanel2.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonName("浏览. . .");
        sortingSelectedPanel2.setButtonName("浏览. . .");
        sortingSelectedPanel1.setButtonName("浏览. . .");
        //2007.03.29
        technicField.setMaximumSize(new Dimension(80, 22));
        technicField.setMinimumSize(new Dimension(80, 22));
        technicField.setPreferredSize(new Dimension(80, 22));

        //问题（7）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
        notTechnicsSelected.setText(res.getString("not"));
        searchTechnics.setActionCommand("SearchTechnics");
        searchTechnics.setMaximumSize(new Dimension(80, 23));
        searchTechnics.setMinimumSize(new Dimension(80, 23));
        searchTechnics.setPreferredSize(new Dimension(80, 23));
        searchTechnics.setText(res.getString("search2"));
        searchTechnics.addActionListener(pdControl);
        //问题（7）结束

        paginatePanel1.addListener(new PaginateListener()
        {
            public void paginateEvent(PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
        this.add(jPanel1, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(8, 0, 0, 0), 0, 0));
        jPanel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 3, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 10, 0, 0), 0, 0));
        jTabbedPane1.add(jPanel2, res.getString("summary_condition"));
        jPanel2.add(partTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(partLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(techcategoryLabel,
                    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.EAST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(pdtypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(departLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));
        //2007.03.29
        jPanel2.add(numberLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));

        jPanel2.add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(nottechCheckBox,
                    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(notprocedureCheckBox,
                    new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(notdepartmentCheckBox,
                    new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(partCheckBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel1,
                    new GridBagConstraints(1, 2, 2, 1, 0.0, 1.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel2,
                    new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        //问题（7）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
        //2007.04.03
        jPanel2.add(technicField,
                    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));

        jPanel2.add(notTechnicsSelected,
                    new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(searchTechnics, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        //问题（7）结束


        jPanel2.add(sortingSelectedPanel3,
                    new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(pdtypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 8, 0, 0), 0, 0));
        jPanel1.add(summaryButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(24, 8, 0, 10), 0, 0));
        jPanel1.add(interruptButton,
                    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.NORTHWEST,
                                           GridBagConstraints.NONE,
                                           new Insets(8, 8, 0, 10), 0, 0));
        jPanel1.add(customizeButton,
                    new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.NORTHWEST,
                                           GridBagConstraints.NONE,
                                           new Insets(10, 8, 0, 10), 0, 0));
        this.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(10, 10, 0, 10), 0,
                                                 0));
        jPanel4.add(multiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        this.add(jPanel3, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(8, 0, 8, 10), 0, 0));
        jPanel3.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(exportButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(saveButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        this.add(paginatePanel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 10, 0, 8), 0, 0));
        this.multiList.setHeadings(pdControl.getSchemaheadings());
        this.setAveRow();
        multiList.setCellEditable(false);
    }
  //CCBegin SS1
    /**
     * 返回用户所属公司
     * @return Sting 用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	String returnStr = "";
    	RequestServer server = RequestServerFactory.getRequestServer();
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         boolean flag = false;
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         return returnStr;
    }
    //CCEnd SS1

    /**
     * 本地化
     */
    public void localize()
    {
        summaryButton.setText(res.getString("summary"));
        interruptButton.setText(res.getString("interrupt"));
        customizeButton.setText(res.getString("export_customize"));
        partLabel.setText(res.getString("part_number"));
        techcategoryLabel.setText(res.getString("technics_category"));
        departLabel.setText(res.getString("department"));
        //2007.03.29
        numberLabel.setText("工艺编号");
        searchButton.setText(res.getString("search2"));
        partCheckBox.setText(res.getString("structure"));
        nottechCheckBox.setText(res.getString("not"));
        notprocedureCheckBox.setText(res.getString("not"));
        notdepartmentCheckBox.setText(res.getString("not"));
        saveButton.setText(res.getString("save"));
        exportButton.setText(res.getString("export"));
        exitButton.setText(res.getString("exit"));
        pdtypeLabel.setText(res.getString("procedure_type"));
    }


    /**
     * paginatePanel的监听实现
     * @param e PaginateEvent
     */
    void paginatePanel_paginateEvent(PaginateEvent e)
    {
        Vector tempVector = paginatePanel1.getCurrentObjects();
        this.addMultiList(tempVector);
    }


    /**
     * 返回零部件号的值
     * @return String
     */
    public String getPartNum()
    {
        part = partTextField.getText().trim();
        if (part.equals(""))
        {
            return null;
        }
        else
        {
            return part;
        }
    }


    /**
     * 得到工序部门
     * @return BaseValueIfc
     */
    public BaseValueIfc getProcedureDepart()
    {
        if (sortingSelectedPanel2.getCoding() != null)
        {
            return sortingSelectedPanel2.getCoding();
        }
        else
        {
            return null;
        }
    }


    /**
     * 得到工艺类型
     * @return String
     */
    public String getTechType()
    {
        if (sortingSelectedPanel3.getCoding() != null)
        {
            return sortingSelectedPanel3.getCoding().getBsoID();
        }
        else
        {
            return null;
        }
    }

    //问题(4)2007.07.19 徐春英修改 修改原因:如果工艺编号为空字符串则返回null
     public String getTechNum()
     {
         String techNum = technicField.getText().trim();
         if (techNum.equals(""))
         {
             return null;
         }
         else
         {
             return techNum;
         }
     }


    /**
     * 结构复选框是否选取
     * @return boolean
     */
    public boolean isSelectPart()
    {
        return partCheckBox.isSelected();
    }


    /**
     * 非工艺种类复选框是否选取
     * @return boolean
     */
    public boolean isSelectTech()
    {
        return nottechCheckBox.isSelected();
    }


    /**
     * 非工序种类复选框是否选取
     * @return boolean
     */
    public boolean isSelectProcedure()
    {
        return notprocedureCheckBox.isSelected();
    }


    /**
     * 非部门复选框是否选取
     * @return boolean
     */
    public boolean isSelectDepartment()
    {
        return notdepartmentCheckBox.isSelected();
    }


    /**
     * 设定汇总信息
     * @return String
     */
    public String getSumInfo()
    {
        return size + res.getString("have been found");
    }


    /**
     * 设置汇总结果值对象
     * @param totalResult TotalResultIfc
     */
    public void setTotalResult(TotalResultIfc totalResult)
    {
        this.totalResult = totalResult;
    }


    /**
     * 将汇总结果导出到集合中
     * @return Vector
     */
    public Vector exportResult()
    {
        return vector;
    }


    /**
     * 把汇总结果放入Multilist
     * 问题（1）2006.08.11 徐春英修改 清空分页显示的缓存结果
     * 问题（5）2007.11.05 薛凯修改 汇总结果按数值排序不正确
     */
    public void setToMultilist()
    {
        if (totalResult == null)
        {
            return;
        }
        Collection col = totalResult.getContent().getContent();
        size = col.size();
        this.multiList.clear();
        vector = new Vector();
        Vector vec = new Vector();
        if (size > 0)
        {
            this.setUseState(true);
            vector.add(0, totalResult.getAttrDisplayList());
            Object[] results = col.toArray();
            for (int i = 0; i < results.length; i++)
            {
            	  //问题（5）2007.11.05 薛凯修改 汇总结果按数值排序不正确
                Object[] objs1 = (Object[]) results[i];
                //String[] strs = new String[objs1.length];
                //for (int j = 0; j < objs1.length; j++)
                //{
                 //   if (objs1[j] != null)
                //    {
                //        strs[j] = objs1[j].toString();
                 //   }
               //     else
                //    {
               //         strs[j] = "";
               //    }
               // }
               // vec.add(i, strs);
               // vector.add(i + 1, strs);
                vec.add(i, objs1);
               vector.add(i + 1, objs1);
            }
            //问题（1）2006.08.11 徐春英修改 清空分页显示的缓存结果
            paginatePanel1.clearResultCache();
            vec = paginatePanel1.paginate(vec);
            this.addMultiList(vec);
        }
        else
        {
            this.setUseState(false);
            paginatePanel1.paginate(null);
        }
    }


    /**
     * 设置汇总属性显示名
     * @param s String[]
     */
    public void setAttributes(String[] s)
    {
        this.attrDisplayname = s;
    }


    /**
     * 设置零部件的信息
     * @param info QMPartInfo
     */
    public void setPart(QMPartInfo info)
    {
    	//薛凯 20081014 修改
    	String value = partTextField.getText();
    	if(value.length()>0)
    	{
    		partTextField.setText(value + ";" + info.getPartNumber()); 
    	}
    	else
    	{
    		partTextField.setText(info.getPartNumber());
    	}
    	//薛凯 20081014 修改结束
    }


    /**
     * 得到工序类型
     * @return BaseValueIfc
     */
    public BaseValueIfc getPdType()
    {
        if (sortingSelectedPanel1.getCoding() != null)
        {
            return sortingSelectedPanel1.getCoding();
        }
        else
        {
            return null;
        }
    }


    /**
     * 得到汇总条件
     * @return String
     * 问题(3)20070625 徐春英修改 修改原因:汇总条件列表没有添加"工艺编号"这个条件
     * 问题（6）20080227 薛凯 修改 修改原因：工艺汇总条件显示错误
     */
    public String getSumCondition()
    {
        StringBuffer buffer = new StringBuffer();
        if (getPartNum() != null)
        {
            String partNum = res.getString("part_number");
            buffer.append(partNum).append(":").append(getPartNum());
        }
        if (sortingSelectedPanel3.getCoding() != null)
        {
            String technics = res.getString("technics_category");
            buffer.append(";").append(technics).append(":").append(
                    sortingSelectedPanel3.getCoding().toString());
            //薛凯 20080227 添加 添加原因：工艺汇总条件显示错误
            if(isSelectTech())
            {
                buffer.append("(非)");
            }
            //薛凯添加结束
        }
        if (sortingSelectedPanel2.getCoding() != null)
        {
            String procedure = res.getString("department");
            buffer.append(";").append(procedure).append(":").append(
                    sortingSelectedPanel2.getCoding().toString());
            //薛凯 20080227 添加 添加原因：工艺汇总条件显示错误
            if(isSelectDepartment())
            {
                buffer.append("(非)");
            }
            //薛凯添加结束
        }
        if (sortingSelectedPanel1.getCoding() != null)
        {
            String department = res.getString("procedure_type");
            buffer.append(";").append(department).append(":").append(
                    sortingSelectedPanel1.getCoding().toString());
            //薛凯 20080227 添加 添加原因：工艺汇总条件显示错误
            if(isSelectProcedure())
            {
                buffer.append("(非)");
            }
            //薛凯添加结束
        }
        //问题(3)20070625 徐春英修改 修改原因:汇总条件列表没有添加"工艺编号"这个条件
        if(getTechNum() != null)
        {
            String technicNum = res.getString("technicNum");
            buffer.append(";").append(technicNum).append(":").append(getTechNum());
            //问题（7）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
            if (this.isSelectTechnicsBso())
            {
                buffer.append("(非)");
            }
            //问题（7）结束
        }
        return buffer.toString();
    }


    /**
     * 更新汇总表头
     */
    public void updateHeadings()
    {
        multiList.updateHeadings(attrDisplayname);
    }


    /**
     * 设置分页的页数为1
     */
    public void newPaginatePanel()
    {
        paginatePanel1.paginate(null);
    }


    /**
     * 使表头均匀分布
     * 问题（2）2006.08.11  徐春英修改  输出定制完成后，multilist里有很多空行，应该清空
     */
    public void setMultilistRow()
    {
        int a = pdControl.getAttrCount();
        if (a > 10)
        {
            a = 10;
        }
        int[] aa = new int[a];
        for (int s = 0; s < a; s++)
        {
            aa[s] = 1;
        }
        this.multiList.setRelColWidth(aa);
        //问题（2）2006.08.11  徐春英修改  输出定制完成后，multilist里有很多空行，应该清空
        this.multiList.clear();
    }


    /**
     * 使默认表头均匀分布
     */
    public void setAveRow()
    {
        int a = pdControl.getHeadsCount();
        int[] aa = new int[a];
        for (int s = 0; s < a; s++)
        {
            aa[s] = 1;
        }
        this.multiList.setRelColWidth(aa);
    }


    /**
     * 设置导出按钮和保存的状态
     */
    private void setUseState(boolean flag)
    {
        saveButton.setEnabled(flag);
        exportButton.setEnabled(flag);
    }


    /**
     * 设置汇总按钮的状态
     */
    public void setSaveButtonstate(boolean flag)
    {
        summaryButton.setEnabled(flag);
        interruptButton.setEnabled(!flag);
    }


    /**
     * 将汇总结果加入到表格中
     * @param v Vector
     * 问题（5）2007.11.05 薛凯修改 汇总结果按数值排序不正确
     */
    public void addMultiList(Vector v)
    {
        int i = 0;
        this.multiList.clear();
        for (Iterator iterator = v.iterator(); iterator.hasNext(); )
        {
        	//问题（5）2007.11.05 薛凯修改 汇总结果按数值排序不正确
            //String[] objs = (String[]) iterator.next();
             Object[] objs = (Object[]) iterator.next();
            for (int j = 0; j < objs.length; j++)
            {
                if (objs[j] != null)
                {
                    //this.multiList.addTextCell(i, j, objs[j].toString());
                    this.multiList.addNumberTextCell(i, j, objs[j]);
                }
                else
                {
                    this.multiList.addTextCell(i, j, "");
                }
            }
            i++;
        }
    }
    public CappMultiList  getMultilist()
 {
     return multiList;
 }

 /**
     * 问题（7）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
     * 非工艺编号复选框是否选取
     * @return boolean
     */
    public boolean isSelectTechnicsBso()
    {
        return notTechnicsSelected.isSelected();
    }

    //问题（7）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
    public void setTechniceNumber(String technicsnumber)
    {
    	//薛凯 20081014 修改
    	String s = technicField.getText();
    	if(s.length()>0)
    	{
    		technicField.setText(s + ";" + technicsnumber);
    	}
    	else
    	{
    		technicField.setText(technicsnumber);
    	}
    	//薛凯 20081014 修改结束
    }
    //问题（7）结束
}
