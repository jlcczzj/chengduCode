/** 生成程序时间 2003/08/11
 * 程序文件名称 ExtendResource_en_US.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/06/04 徐春英 参见DefectID=2177
 * CR2 2009/06/30 徐春英 参见DefectID=2472
 * CR3 2009/07/03 刘玉柱 参见DefectID=2508
 * CCBegin by liunan 2011-03-15 打补丁v4r3_p028_20110216
 * CR5 2011/02/10 徐春英 参见TD问题2372
 * CCEnd by liunan 2011-03-15 、
 * SS1 成都向上添加和空表粘贴功能  guoxiaoliang  2016-7-24
 * SS2 成都新增质量检查工序的作业指导书定制   guoxiaoliang  2016-7-24
 * SS3 成都质量检查工艺检查作业指导书添加工装功能   guoxiaoliang  2016-10-24
 */

package com.faw_qm.cappclients.beans.cappexattrpanel;

import java.awt.*;
import javax.swing.*;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttGroup;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;

import java.util.Locale;
import java.util.Vector;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMToolInfo;

import java.util.Hashtable;
import com.faw_qm.codemanage.model.CodingIfc;
import java.util.ArrayList;

import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.view.*;
import com.faw_qm.cappclients.capp.web.ViewTechnicsAttrUtil;
import com.faw_qm.cappclients.capp.util.Variable;

/**
 * <p>Title:成组属性封装截面 </p>
 * <p>Description:成组属性面板,控制成组属性的显示,查询,创建,删除等功能 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: faw_qm</p>
 * @author 管春元
 * @version 1.0
 * 问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
 */
public class GroupPanel extends JPanel
{
    private JScrollPane jScrollPane1 = new JScrollPane();

    //属性显示的表格
    public ComponentMultiList multiList = new ComponentMultiList();
    
    
    
    //public CappExAttrPanel processFlowJPanel;
    //问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
    //过程流程multilist
    public  ComponentMultiList flowMultiList = new ComponentMultiList();
    
    //CCBegin SS1
    //添加上添加功能
    private JButton addUpButton = new JButton();

  //实现空表粘贴功能
    private JButton nullAffixButton = new JButton();
    //CCEnd SS1

    //添加按钮
    private JButton addButton = new JButton();

    //删除按钮
    private JButton deleteButton = new JButton();
    private Locale local = RemoteProperty.getVersionLocale();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    
    //CCBegin SS2
    private JLabel toolLabel=new JLabel();
    private JButton toolButton = new JButton(); 
    private JButton toolDecreaseButton = new JButton(); 
    //CCEnd SS2

    //属性组
    ExtendAttGroup group;
    //属性组所属于的容器
    ExtendAttContainer container;
    //属性组的大小
    int groupSize;
    
    //CCBegin SS2
    
    private static ResourceBundle resource = null;
    private static String RESOURCEOther = "com.faw_qm.clients.beans.BeansRB";
    //CCEnd SS2

    //是否是控制计划
    boolean isControlPlan = false;
    //20070321 xuejing add
    /**
     * 需计算的列数
     */
    //private ArrayList calculateCols;
    //父界面－－扩展属性总界面
    CappExAttrPanel parentPanel;
    //枚举类欢存
    HashMap etMaps = new HashMap();
    //是否有缺省属性值
    private boolean hasDefault;
    static boolean VERBOSE = new Boolean(RemoteProperty.getProperty(
            "com.faw_qm.extend.verbose", "true")).booleanValue();
    private int[] rds;
    private static boolean verbose = RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true").equals("true");
    /**
     * 20070321薛静添加，计算按钮
     */
    private JButton calculateButton = new JButton(); //debug标识
    //add by wangh on 20070531
       // CCBegin by leixiao 2008-10-27 原因：解放系统升级  继承按钮 实现过程流程,控制计划，过程FMEA的数据继承
   //添加行与行复制按钮
    private JButton inheritButton = new JButton();
    private JButton copyAddButton = new JButton();
    
    private String groupname = "";
  //  CCEnd by leixiao 2008-10-27 原因：解放系统升级 
    protected static String RESOURCE
    = "com.faw_qm.cappclients.capp.util.CappLMRB";
    /**父窗口*/
    private JFrame parentJFrame;

    /**
     * 20070627wangh添加,编辑按钮
     */
    private JButton editButton = new JButton();
    //问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
    private JButton attrButton = new JButton(); 
    /**
     * 构造函数
     * @param container ExtendAttContainer　扩展属性容器
     * @param groupName String　属性组名
     * @param isControlPlan boolean　　是否是控制计划
     * @see ExtendAttContainer
     */
    public GroupPanel(ExtendAttContainer container, String groupName,
                      boolean isControlPlan)
    {
  	    //CCBegin by leixiao 2008-10-27 原因：解放系统升级 
        groupname = groupName;
        //CCEnd by leixiao 2008-10-27 原因：解放系统升级 
        try
        {
            this.container = container;
            this.isControlPlan = isControlPlan;
            //如果是不同组
            if (!isControlPlan)
            {
                group = this.container.getAttGroupDes(groupName);
                groupSize = group.getCount();
            }
            //如果是控制计划
            else
            {
                group = this.container.getPlanGroupDes();
                groupSize = group.getCount();
            }
            // this.multiList.setCheckModel(true);
            String[] heads = new String[groupSize];
            for (int i = 0; i < groupSize; i++)
            {
                ExtendAttModel model = group.getAttributeAt(i);

                prepareDatas(model);
                //设置标题
                heads[i] = model.getAttDisplay();
            }

            this.multiList.setHeadings(heads);
            //add by wangh on 20070518(可以配置成组属性中不可编辑的列)
            //int r= multiList.getSelectedRow();
//  		CCBegin by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
            int n=this.getColByAttname("措施结果PRN");
//  		CCEnd by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
            int m=this.getColByAttname("风险顺序数(PRN)");
            multiList.setColsEnabled(new int[]{n,m}, false);
            //defaultColumnWidths();
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 根据具体的属性组织数据
     * @param model ExtendAttModel　具体一个属性的封装类
     * @see ExtendAttModel
     */
    private void prepareDatas(ExtendAttModel model)
    {
        HashMap map = model.getFeature();
        if (model.getAttType().equals("EnumeratedType"))
        {
            String classPath = (String) map.get("classpath");
            String newString = (String) map.get("newMethod");
            try
            {
                if (VERBOSE)
                {
                    System.out.println("类型路径==  " + classPath);
                }
                Class class1 = Class.forName(classPath);
                Method newMethod = class1.getMethod(newString, null);
                EnumeratedType et1 = (EnumeratedType) newMethod.invoke(class1, null);
                EnumeratedType[] ets = et1.getValueSet();
                this.etMaps.put(classPath, ets);
            }
            catch (Exception e)
            {
                if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
        else
        if (model.getAttType().equals("Coding"))
        {
            try
            {
                String sortType = (String) map.get("SortType");
                StringTokenizer ston = new StringTokenizer(sortType, ";");
                String para1 = ston.nextToken();
                if (VERBOSE)
                {
                    System.out.println("para1== " + para1);
                }
                String para2 = ston.nextToken();
                if (VERBOSE)
                {
                    System.out.println("para2==  " + para2);
                }
                String para3 = ston.nextToken();
                Collection sorts = CappTreeHelper.getCoding(para1, para2, para3);
                this.etMaps.put(sortType, sorts);
            }
            catch (QMRemoteException e)
            {
                if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.getParentJFrame(),
                                              e.getClientMessage());
            }
        }
        else
        if (model.getAttType().equals("String"))
        {
            String refType = (String) map.get("RefType");
            if (refType == null)
            {
                return;
            }
            if (refType.equals("EnumerateType"))
            {
                String classPath = (String) map.get("classpath");
                String newString = (String) map.get("newMethod");
                try
                {
                    Class class1 = Class.forName(classPath);
                    Method newMethod = class1.getMethod(newString, null);
                    EnumeratedType et1 = (EnumeratedType) newMethod.invoke(
                            class1, null);
                    EnumeratedType[] ets = et1.getValueSet();
                    this.etMaps.put(classPath, ets);
                }
                catch (Exception e)
                {
                    if(verbose)
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this.getParent(),
                                                  e.getMessage());
                }
            }
            else
            if (refType.equals("ComboAtts"))
            {
                try
                {
                    String sortType = (String) map.get("SortType");
                    StringTokenizer ston = new StringTokenizer(sortType, ";");
                    String para1 = ston.nextToken();
                    if (VERBOSE)
                    {
                        System.out.println("para1==  " + para1);
                    }
                    String para2 = ston.nextToken();
                    if (VERBOSE)
                    {
                        System.out.println("para2==  " + para2);
                    }
                    String para3 = ston.nextToken();
                    Collection sorts = CappTreeHelper.getCoding(para1, para2,
                            para3);
                    this.etMaps.put(sortType, sorts);

                }
                catch (QMRemoteException e)
                {
                      if(verbose)
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this.getParentJFrame(),
                                                  e.getClientMessage());
                }
            }
        }
    }



    /**
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        this.setLayout(gridBagLayout1);
        addButton.setMaximumSize(new Dimension(80, 23));
        addButton.setMinimumSize(new Dimension(80, 23));
        addButton.setPreferredSize(new Dimension(80, 23));
        addButton.setMnemonic('A');
        addButton.setText("添加(A)");
        addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addButton_actionPerformed(e);
            }
        });

        
        //CCBegin SS1
        //添加上添加功能
        addUpButton.setMaximumSize(new Dimension(80, 23));
        addUpButton.setMinimumSize(new Dimension(80, 23));
        addUpButton.setPreferredSize(new Dimension(80, 23));
        addUpButton.setToolTipText("");
        addUpButton.setMnemonic('U');
        addUpButton.setText("向上添加");
        addUpButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            addUpButton_actionPerformed(e);
          }
        });

       

        nullAffixButton.setMaximumSize(new Dimension(80, 23));
        nullAffixButton.setMinimumSize(new Dimension(80, 23));
        nullAffixButton.setPreferredSize(new Dimension(80, 23));
        nullAffixButton.setToolTipText("");
        nullAffixButton.setMnemonic('C');
        nullAffixButton.setText("空表粘贴");
        nullAffixButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  nullAffixButton_actionPerformed(e);
          }
        });
        
        //CCEnd SS1
        
        
        
        deleteButton.setMaximumSize(new Dimension(80, 23));
        deleteButton.setMinimumSize(new Dimension(80, 23));
        deleteButton.setPreferredSize(new Dimension(80, 23));
        deleteButton.setMnemonic('D');
        deleteButton.setText("删除(D)");
        deleteButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteButton_actionPerformed(e);
            }
        });
        calculateButton.setMaximumSize(new Dimension(80, 23));
    calculateButton.setMinimumSize(new Dimension(80, 23));
    calculateButton.setPreferredSize(new Dimension(80, 23));
    calculateButton.setRolloverEnabled(true);
    calculateButton.setText("计算");
    calculateButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        calculateButton_actionPerformed(e);
      }
    });
    //问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
    //CCBegin by leixiao 2008-10-27 原因：解放系统升级 
    inheritButton.setMaximumSize(new Dimension(80, 23));
    inheritButton.setMinimumSize(new Dimension(80, 23));
    inheritButton.setPreferredSize(new Dimension(80, 23));
    inheritButton.setRolloverEnabled(true);
    inheritButton.setText("传递");
    inheritButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	          inheritButton_actionPerformed(e);
      }
    });
    //CCEnd by leixiao 2008-10-27 原因：解放系统升级 
    //CCBegin by leixiao 2009-7-2 原因：添加复制按钮
    copyAddButton.setMaximumSize(new Dimension(80, 23));
    copyAddButton.setMinimumSize(new Dimension(80, 23));
    copyAddButton.setPreferredSize(new Dimension(80, 23));
    copyAddButton.setText("复制");
    copyAddButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	          copyAddButton_actionPerformed(e);
      }
    });
    //add by wangh on 20070627
    editButton.setMaximumSize(new Dimension(80, 23));
    editButton.setMinimumSize(new Dimension(80, 23));
    editButton.setPreferredSize(new Dimension(80, 23));
    editButton.setRolloverEnabled(true);  
    editButton.setText("编辑");
    editButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  editButton_actionPerformed(e);
      }
    });  
    //CCEnd by leixiao 2009-7-2
    
    //CCBegin SS2
    
    toolLabel.setMaximumSize(new Dimension(25, 23));
    toolLabel.setMinimumSize(new Dimension(25, 23));
    toolLabel.setPreferredSize(new Dimension(25, 23));
    toolLabel.setText("工装");
    
    toolButton.setMaximumSize(new Dimension(40, 23));
    toolButton.setMinimumSize(new Dimension(40, 23));
    toolButton.setPreferredSize(new Dimension(40, 23));
    toolButton.setText("+");
    toolButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
      	  toolButton_actionPerformed(e);
        }
      });
    
    toolDecreaseButton.setMaximumSize(new Dimension(40, 23));
    toolDecreaseButton.setMinimumSize(new Dimension(40, 23));
    toolDecreaseButton.setPreferredSize(new Dimension(40, 23));
    toolDecreaseButton.setText("-");
    toolDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	toolDecreaseButton_actionPerformed(e);
        }
      });
    //CCEnd SS2
    
    
    
        this.add(jScrollPane1,  new GridBagConstraints(0, 0, 1,8, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 113, 156));
        this.add(editButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 10, 0, 8), 0, 0));
        this.add(deleteButton,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        this.add(addButton,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        //CCBegin SS2
        if (!groupname.equals("检查作业指导书") ) {
        this.add(calculateButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        // CCBegin by leixiao 2008-10-27 原因：解放系统升级 
        this.add(copyAddButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
        this.add(inheritButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
        }
        
       
        if (groupname.equals("检查作业指导书") ) {
        this.add(toolLabel,     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 100), 0, 0));
        this.add(toolButton,     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 8), 0, 0));
        this.add(toolDecreaseButton,     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 100, 0, 8), 0, 0));
        }
        
            
  // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
        if (!groupname.equals("检查作业指导书") ) {
        //CCBegin SS1
        this.add(addUpButton, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH,GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
        }
        
        
        if ((!groupname.equals("焊接参数") && !groupname.equals("参数"))&&!groupname.equals("检查作业指导书")) {
        	this.add(nullAffixButton, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH,GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
        	
        }

        
        //CCEnd SS1
        //CCEnd SS2
        
        jScrollPane1.getViewport().add(multiList, null);
    }


    /**
     * 得到附加属性的搜索条件
     * @param conditions　由扩展属性总界面传递过来的集合
     * @return HashMap 由于现在的工艺不再使用以前的控制计划,所以只可能返回以普通成组属性说明和
     * 属性组名组成的字符串为键,搜索条件的集合为值的HashMap
     */
    public HashMap getCondition(HashMap conditions)
    {
        ExtendAttGroup group;
        Vector vec = new Vector();
        for (int i = 0; i < multiList.getNumberOfRows(); i++)
        {
            group = this.group.duplicate();
            ExtendAttModel model;
            HashMap conMap = new HashMap();
            for (int j = 0; j < this.groupSize; j++)
            {
                model = group.getAttributeAt(j);

                String temp = null;
                if (model.getAttType().equalsIgnoreCase("SpecChar"))
                  {
                      conMap.put(model.getAttName(), (Vector)this.multiList.getSelectedObject(i, j));
                  }
               else{
                   if (model.getAttType().equals("EnumeratedType"))
                   {
                       temp = (String)this.multiList.getSelectedObject(i, j);
                   }
                   else
                   if (model.getAttType().equals("Coding"))
                   {
                       temp = (String)this.multiList.getSelectedObject(i, j);
                   }
                   else
                   if (model.getAttType().equalsIgnoreCase("Boolean"))
                   {
                       if (((Boolean) multiList.getSelectedObject(i, j)).
                           booleanValue())
                       {
                           temp = "true";
                       }
                       else
                       {
                           temp = "false";
                       }
                   }
                   else
                   if (model.getAttType().equalsIgnoreCase("String"))
                   {
                       HashMap map = model.getFeature();
                       String refType = (String) map.get("RefType");
                       if (refType == null)
                       {
                           temp = (String)this.multiList.getSelectedObject(i,
                                   j);
                       }
                       else
                       if (refType.equalsIgnoreCase("EnumerateType"))
                       {
                           temp = (String)this.multiList.getSelectedObject(i,
                                   j);
                       }
                       else
                       if (refType.equalsIgnoreCase("ComboAtts"))
                       {
                           temp = multiList.getCellAt(i, j).getStringValue() +
                                  ";" +
                                  (String) multiList.getCellAt(i, j).getValue();
                       }
                   }

                   boolean check = parentPanel.checkValidity(model, temp,
                           model.getAttDisplay());
                   if (check)
                   {
                       if (temp != null)
                       {
                           if (temp.equals(""))
                           {
                               if (hasDefault)
                               {
                                   temp = model.getAttDefault();
                                   hasDefault = false;
                               }
                               else
                               {
                                   temp = null;
                               }
                           }
                       }
                   }
                   else
                   {
                       return null;
                   }

                   if (temp != null && temp.trim().equals(""))
                   {
                       Object value = change(model, temp);

                       conMap.put(model.getAttName(), value);
                   }
               }
                //  conditions.put("",null);
            }
            if (conMap.size() != 0)
            {
                vec.add(conMap);
            }
        }

        if (this.isControlPlan)
        {
            if (vec.size() > 0)
            {
                conditions.put(ExtendAttContainer.CONTROLPLAN, vec);
            }
        }
        else
        {
            if (vec.size() > 0)
            {
                conditions.put(ExtendAttContainer.NORMALGROUP + ";" +
                               this.group.getGroupName(), vec);
            }
        }
        return conditions;
    }


    /**
     * 得到附加属性
     * @return　扩展属性组的集合
     */
    public Vector getExAttr()
    {
        Vector groups = new Vector();
        ExtendAttGroup group;
        for (int i = 0; i < multiList.getNumberOfRows(); i++)
        {
            group = this.group.duplicate();
            boolean flag = false;
            for (int j = 0; j < this.groupSize; j++)
            {
                ExtendAttModel model = group.getAttributeAt(j);
                this.setAtts(model, i, j);
                if (model.getAttValue() != null)
                {
                    flag = true;
                }
            }
            if (flag)
            {
                groups.add(group);
            }
        }
        return groups;
    }


    /**
     * 有效性检查
     * @return boolean 返回值表示被检查的目标是否有效,true为有效
     */
    public boolean check()
    {
        ExtendAttGroup group;
        for (int i = 0; i < multiList.getNumberOfRows(); i++)
        {
            group = this.group.duplicate();
            for (int j = 0; j < this.groupSize; j++)
            {
                ExtendAttModel model = group.getAttributeAt(j);
                //System.out.println("model.getAttType()="+model.getAttType());
                String temp = null;
                if (model.getAttType().equals("EnumeratedType"))
                {
                    temp = (String)this.multiList.getSelectedObject(i, j);
                }else
                if (model.getAttType().equals("SpecChar"))
               {
                  continue;
               }
               else

                if (model.getAttType().equals("Coding"))
                {
                    temp = (String)this.multiList.getSelectedObject(i, j);
                    if(!model.getAllowNull()&&temp.equals(" "))
                    {
                          this.parentPanel.handleNull(model.getAttDisplay());
                           return false;
                    }
                }
                else
                if (model.getAttType().equalsIgnoreCase("Boolean"))
                {
                    if (((Boolean) multiList.getSelectedObject(i, j)).
                        booleanValue())
                    {
                        temp = "true";
                    }
                    else
                    {
                        temp = "false";
                    }
                }
                else
                if (model.getAttType().equalsIgnoreCase("String"))
                {
                    HashMap map = model.getFeature();
                    String refType = (String) map.get("RefType");
                    if (refType == null)
                    {
                        temp = (String)this.multiList.getSelectedObject(i, j);
                    }
                    else
                    if (refType.equalsIgnoreCase("EnumerateType"))
                    {
                        temp = (String)this.multiList.getSelectedObject(i, j);
                    }else
                    if (refType.equalsIgnoreCase("ComboAtts"))
                    {
                        temp = multiList.getCellAt(i, j).getStringValue() + ";" +
                               (String) multiList.getCellAt(i, j).getValue();
                    }
                }
                else
                {
                    temp = (String)this.multiList.getSelectedObject(i, j);
                }
                boolean check = parentPanel.checkValidity(model, temp,
                        model.getAttDisplay());
                if (!check)
                {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 设置父panel
     * @param panel CappExAttrPanel 附加属性panel
     */
    public void setParentPanel(CappExAttrPanel panel)
    {
        this.parentPanel = panel;
    }
    /**
     * 设置对应行和列的成组属性值
     * @param model ExtendAttModel 附加属性模型
     * @param i int 行号
     * @param j int 列号
     * @see ExtendAttModel
     */
    private void setAtts(ExtendAttModel model, int i, int j)
    {
        String temp = null;
        if (model.getAttType().equalsIgnoreCase("SpecChar"))
                {

                     model.setAttValue((Vector)this.multiList.getSelectedObject(i, j));
                }
      else
      {
          if (model.getAttType().equals("EnumeratedType"))
          {
              temp = (String)this.multiList.getSelectedObject(i, j);
          }
          else
          if (model.getAttType().equals("Coding"))
          {
              temp = (String)this.multiList.getSelectedObject(i, j);
          }
          else
          if (model.getAttType().equalsIgnoreCase("Boolean"))
          {
              if (((Boolean) multiList.getSelectedObject(i, j)).booleanValue())
              {
                  temp = "true";
              }
              else
              {
                  temp = "false";
              }
          }
          else
          if (model.getAttType().equalsIgnoreCase("String"))
          {
              HashMap map = model.getFeature();
              String refType = (String) map.get("RefType");
              if (refType == null)
              {
                  temp = (String)this.multiList.getSelectedObject(i, j);
              }
              else
              if (refType.equalsIgnoreCase("EnumerateType"))
              {
                  temp = (String)this.multiList.getSelectedObject(i, j);
              }
              else
              if (refType.equalsIgnoreCase("ComboAtts"))
              {

                  temp = multiList.getCellAt(i, j).getStringValue() +";"+
                         (String) multiList.getCellAt(i, j).getValue();
              }

          }
          else
          {
              temp = (String)this.multiList.getSelectedObject(i, j);
          }
          boolean check = parentPanel.checkValidity(model, temp,
                  model.getAttDisplay());
          if (check)
          {
              if (temp != null)
              {
                  if (temp.equals(""))
                  {
                      if (hasDefault)
                      {
                          temp = model.getAttDefault();
                          hasDefault = false;
                      }
                      else
                      {
                          temp = null;
                      }
                  }
              }
          }
          else
          {
              return;
          }
          if(temp!=null)
          {
              Object value = change(model, temp);
              model.setAttValue(value);
          }
      }
    }


    /**
     * 显示当前对象
     * @param info ExtendAttriedIfc　受扩展属性管理的值对象
     * @param groupName 扩展属性组名
     * @see ExtendAttriedInfo
     */
    public void show(ExtendAttriedIfc info, String groupName)
    {
        ExtendAttContainer container1 = info.getExtendAttributes();
        show(container1,groupName);
//        ExtendAttGroup group;
//        int count;
//        if (isControlPlan)
//        {
//            count = container1.getPlanGroupCount();
//            for (int i = 0; i < count; i++)
//            {
//                group = container1.getPlanGroupAt(i);
//                for (int j = 0; j < group.getCount(); j++)
//                {
//                    showAttsToTable(i, j, group.getAttributeAt(j));
//                }
//
//            }
//        }
//        else
//        {
//            Vector vec = container1.getAttGroups(groupName);
//            if (vec == null)
//            {
//                count = 0;
//            }
//            else
//            {
//                count = vec.size();
//            }
//            for (int i = 0; i < count; i++)
//            {
//                group = (ExtendAttGroup) vec.elementAt(i);
//                for (int j = 0; j < group.getCount(); j++)
//                {
//                    showAttsToTable(i, j, group.getAttributeAt(j));
//                }
//
//            }
//        }

    }

    /**
      * 显示当前对象
      * @param info container1　扩展属性容器
      * @param groupName 扩展属性组名
      * @see ExtendAttContainer
      */
    // 20070205薛静添加

     public void show(ExtendAttContainer container1, String groupName)
     {

         //CCBegin by liunan 2011-03-15 打补丁v4r3_p028_20110216
         //begin CR5
         //源码
         /*ExtendAttGroup group;
         int count;*/
         ExtendAttGroup group1 ;
         //行数
         int count;
         //原工序中属性列数
         int count1 = 0;
         //xml文件中比原来工序中多的属性数量
         int a = 0;
         //end CR5
         //CCEnd by liunan 2011-03-15 
         if (isControlPlan)
         {
             count = container1.getPlanGroupCount();
             for (int i = 0; i < count; i++)
             {
                 group = container1.getPlanGroupAt(i);
                 for (int j = 0; j < group.getCount(); j++)
                 {
                     showAttsToTable(i, j, group.getAttributeAt(j));
                 }

             }
         }
         else
         {
             Vector vec = container1.getAttGroups(groupName);
             if (vec == null)
             {
                 count = 0;
             }
             else
             {
                 count = vec.size();
             }
             //CCBegin by liunan 2011-03-15 打补丁v4r3_p028_20110216
             //begin CR5
             //源码
             /*for (int i = 0; i < count; i++)
             {
                 group = (ExtendAttGroup) vec.elementAt(i);
                 for (int j = 0; j < group.getCount(); j++)
                 {
                     showAttsToTable(i, j, group.getAttributeAt(j));
                 }

             }*/
             ArrayList list = new ArrayList();
             for (int i = 0; i < count; i++)
             {
                 group1 = (ExtendAttGroup) vec.elementAt(i);
                 count1 = group1.getCount();
                 //如果xml中的属性少于原工序的扩展属性，则直接将原工序中的属性值设置到列表中
                 if (groupSize <= count1)
                 {
                 	   for (int j = 0; j < groupSize; j++)
                 	   {
                 	   	   showAttsToTable(i, j, group1.getAttributeAt(j));
                 	   }
                 }
                 else
                 {
                	 for (int j = 0; j < count1; j++)
                     {
                    	 ExtendAttModel model1 = group1.getAttributeAt(j);
                    	 //将原工序中的属性设置到列表中
                         showAttsToTable(i, j, group1.getAttributeAt(j));
                         //将原工序中的扩展属性显示名放到集合里
                         list.add(model1.getAttDisplay());     
                     }
                 }
             }
             String head[] = new String[groupSize];
             //将xml中的属性显示名放到数组集合里
             for (int i = 0; i < groupSize; i++)
             {
             	   head[i] = group.getAttributeAt(i).getAttDisplay();
             }
             //如果xml中配置的属性多于原工序扩展属性，将多于的属性值用空字符串显示
             if(groupSize > count1)
             {
             	   a = groupSize - count1;
             	   for (int i = 0; i < count; i++)
             	   {
             	   	   for (int j = 0, k = groupSize; j < k; j++)
             	   	   {
             	   	   	   //将xml中多于的属性值用空字符串显示
             	   	   	   if (!list.contains(head[j]))
             	   	   	   {
             	   	   	   	   for (int m = 0; m < a; m++)
             	   	   	   	   {
             	   	   	   	   	   multiList.addTextCell(i, count1 + m, "");
             	   	   	   	   }
             	   	   	   }
             	   	   }
             	   }
             }
             //end CR5
             //CCEnd by liunan 2011-03-15 
         }

     }

    /**
     * 把对象的一个附加属性模型显示到指定的行列
     * @param i 行
     * @param j 列
     * @param model ExtendAttModel 附加属性模型
     * @see ExtendAttModel
     */
    private void showAttsToTable(int i, int j, ExtendAttModel model)
    {
        Object obj1 = model.getAttValue();
        if (model.getAttType().equals("SpecChar"))
         {

             if (obj1 != null)
             {

                 multiList.addSpeCharCell(i, j, (Vector)obj1);
             }
             else
             {
                 multiList.addSpeCharCell(i, j, new Vector());
             }

         }

       else
       if (model.getAttType().equalsIgnoreCase("EnumeratedType")) {
         EnumeratedType et1 = (EnumeratedType) obj1;
         if (et1 == null) {
           HashMap map = model.getFeature();
           String classPath = (String) map.get("classpath");
           String newString = (String) map.get("newMethod");
           try {
             Class class1 = Class.forName(classPath);
             Method newMethod = class1.getMethod(newString, null);
             et1 = (EnumeratedType) newMethod.invoke(class1, null);
           }
           catch (Exception e) {
                 if(verbose)
             e.printStackTrace();
             JOptionPane.showMessageDialog(getParentJFrame(), e.getMessage());
           }
         }
         EnumeratedType[] ets = et1.getValueSet();
         String value = et1.getDisplay(local);
         Vector values = new Vector();
         for (int k = 0; k < ets.length; k++) {
           values.add(ets[k].getDisplay(local));
         }
         this.multiList.addComboBoxCell(i, j, value, values);
       }
       else
       if (model.getAttType().equalsIgnoreCase("Coding")) {
         String et1 = (String) obj1;
         HashMap map = model.getFeature();
         String sorttype = (String) map.get("SortType");
         Collection sorts = (Collection)this.etMaps.get(sorttype);
         Iterator ite = sorts.iterator();
         Vector vec = new Vector();
          CodingIfc et2 = null;
          String cont = "";
         while (ite.hasNext()) {
           et2 = (CodingInfo) ite.next();
            if(et2.getCodeContent().equals(et1))
           {
             cont = et2.getCodeContent();
           }
           vec.add( et2.getCodeContent());
         }

         if (et1 != null) {

           this.multiList.addComboBoxCell(i, j, cont, vec);
         }
         else {
//           this.multiList.addComboBoxCell(i, j,
//                                          ( (CodingIfc) vec.elementAt(0)).
//                                          getCodeContent(), vec);
//        	  CCBeginby leixiao 2009-7-8 原因：增加上下移       	 
           this.multiList.addComboBoxCell(i, j,
                   vec.elementAt(0), vec);
//         CCEndby leixiao 2009-7-8 原因：增加上下移   
         }
       }
       else if (model.getAttType().equalsIgnoreCase("String")) {
         HashMap map = model.getFeature();
         String refType = (String) map.get("RefType");
         if (refType == null) {
           if (obj1 != null) {
             multiList.addTextCell(i, j, obj1.toString());
           }
           else {
             multiList.addTextCell(i, j, "");
           }
         }
         else
         if (refType.equals("EnumerateType")) {
           String classPath = (String) map.get("classpath");
           EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
           EnumeratedType et;
           Vector vec = new Vector();
           for (int k = 0; k < ets.length; k++) {
             et = (EnumeratedType) ets[k];
             vec.add(et.getDisplay(local));
           }
           if (obj1 != null) {
             this.multiList.addComboBoxCell(i, j, obj1.toString(), vec);
           }
           else {
             this.multiList.addComboBoxCell(i, j, "", vec);
           }
         }
         else
         if (refType.equals("ComboAtts")) {

           String sorttype = (String) map.get("SortType");
           Collection sorts = (Collection)this.etMaps.get(sorttype);
           String valueStr = "";
           String sortStr = "";
           if (obj1 != null) {
             StringTokenizer ston = new StringTokenizer( (String) obj1,
                 ";");
             if (ston.hasMoreTokens()) {
               valueStr = ston.nextToken();
             }
             if (ston.hasMoreTokens()) {
               sortStr = ston.nextToken();
             }
           }
           Iterator ite = sorts.iterator();
           Vector vec = new Vector();
           while (ite.hasNext()) {
             vec.add( ( (CodingInfo) ite.next()).getCodeContent());
           }
           this.multiList.addComboBoxCell(i, j, valueStr, sortStr, vec);
         }

       }
       else
       if (model.getAttType().equalsIgnoreCase("Boolean")) {
         boolean flag = true;
         if (rds != null) {
           for (int len = 0; len < rds.length; len++) {

             if (j == rds[len]) {
               flag = false;

             }
           }
         }
         if (obj1 != null) {
           if (flag) {
             multiList.addCheckboxCell(i, j,
                                       ( (Boolean) obj1).booleanValue());
           }
           else {
             multiList.addRadioButtonCell(i, j,
                                          ( (Boolean) obj1).booleanValue());
           }
         }
         else {
           multiList.addCheckboxCell(i, j, false);
         }
       }
       else {
         if (obj1 != null) {
           multiList.addTextCell(i, j, obj1.toString());
         }
         else {
           multiList.addTextCell(i, j, "");
         }
       }

    }


    /**
     * 设置哪一行是单选按钮1
     * @param cols　要设置的行号集合
     */
    public void setRadioButtonCols(int[] cols)
    {
        this.rds = cols;
    }


    /**
     * 设置表格各列的绝对宽度
     */
    public void setMulistAbsWidths()
    {
        int i = multiList.getNumberOfCols();
        String[] cols = new String[i];
        for (int j = 0; j < i; j++)
        {
            cols[j] = "80";
        }
        multiList.setColumnSizes(cols);
    }


    /**
     * 应用默认列宽
     * @throws PropertyVetoException
     */
    protected void defaultColumnWidths() //throws PropertyVetoException
    {
        int i = multiList.getNumberOfCols();
        int j = i - 1;
        int as[] = new int[i];
        for (int k = 0; k < j; k++)
        {
            as[k] = 1;
        }
        as[j] = 1;
        multiList.setRelColWidth(as);

    }


    /**
     * 添加按钮执行
     * @param e　按钮事件
     */
//    void addButton_actionPerformed(ActionEvent e)
//    {  
//        int row = this.multiList.getNumberOfRows();
//        for (int i = 0; i < group.getCount(); i++)
//        {
//            addDefalutAttsToTable(row, i, group.getAttributeAt(i));
//        }
//        this.attrButton.setEnabled(true);
//
//    }
    //CCBegin by liuzhicheng 2010-01-21 原因：“添加”按钮的执行方式，改为选中某行添加时，顺序添加到下一行。
    void addButton_actionPerformed(ActionEvent e)
    {
        //修改“添加”按钮的执行方式，改为选中某行添加时，顺序添加到下一行。
        int row = this.multiList.getNumberOfRows();
        int row1 = this.multiList.getSelectedRow();
        if (row1 < 0)
        {
            for (int i = 0; i < group.getCount(); i++)
            {
                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
                multiList.setSelectedRow(row);
            }
        }
        else
        {
            for (int i = 0; i < group.getCount(); i++)
            {
                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
            }
            for (int i = row - 1; i > row1; i--)
            {
                multiList.moveDown(i);
            }
//            if (groupname.equalsIgnoreCase("特性控制"))
//            {
//                for (int i = 0; i < multiList.getNumberOfRows(); i++)
//                {
//                	  multiList.addTextCell(i, 0, (new Integer(i + 1)).toString());
//                }
//            }
            multiList.setSelectedRow(row1 + 1);
        }  

    }
    //CCEnd by liuzhicheng 2010-01-21 原因：“添加”按钮的执行方式，改为选中某行添加时，顺序添加到下一行。

    //CCBegin SS1
    
    /**
     * Added by dikefeng 20090627,添加上添加功能
     * 添加按钮执行
     * @param e　
     */
    void addUpButton_actionPerformed(ActionEvent e) {

      int row = this.multiList.getNumberOfRows();
      //added by dikef
      int row1 = this.multiList.getSelectedRow();
      if (row1 < 0) {
        for (int i = 0; i < group.getCount(); i++) {
          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
        }
        for (int i = row - 1; i >= 0; i--) {
          multiList.moveDown(i);
        }
        multiList.setSelectedRow(0);
      }
      else {
        for (int i = 0; i < group.getCount(); i++) {
          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
        }
        for (int i = row - 1; i >= row1; i--) {
          multiList.moveDown(i);
        }
//      2009.03.01 徐德政改。原因：为控制计划添加动编号功能

        multiList.setSelectedRow(row1 + 1);
      }
      if (groupname.equalsIgnoreCase("特性控制") ||
      groupname.equalsIgnoreCase("控制计划")) {
    for (int i = 0; i < multiList.getNumberOfRows(); i++) {
      multiList.addTextCell(i, 0, (new Integer(i + 1)).toString());
      }
    }

  }
    
    
    /**
     * 空表粘贴功能方法
     */
    void nullAffixButton_actionPerformed(ActionEvent e){
  	  if(this.multiList.getNumberOfRows() > 0){
  		  JOptionPane.showMessageDialog(null, "该表内有内容，不能进行空表粘贴操作！");
  		  return;
  	  }
  	  if(Variable.contents != null && Variable.contents.size() > 0){
  			int selectRow = 0;
  			int colCount = this.multiList.getNumberOfCols();// 列数
  			int copyRowIndex = -1;
  			try {
  				if (Variable.contents.get(new Integer(0)) != null) {
  					for (int j = 0; j < Variable.contents.size(); j++) {
  						Object[] valueOfRow = (Object[]) Variable.contents
  								.get(new Integer(j));
  							if (colCount == 5) {
  								this.multiList.addTextCell(selectRow, 0, (new Integer(
  										j + 1)).toString());
  								this.multiList.addSpeCharCell(selectRow, 1, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 2, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 3, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 4, new Vector());
  							} else if (colCount == 19) {
  								this.multiList.addSpeCharCell(selectRow, 0, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 1, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 2, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 3, new Vector());
  								this.multiList.addTextCell(selectRow, 4, "");
  								this.multiList.addSpeCharCell(selectRow, 5, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 6, new Vector());
  								this.multiList.addTextCell(selectRow, 8, "");
  								this.multiList.addSpeCharCell(selectRow, 7, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 9, new Vector());
  								this.multiList.addTextCell(selectRow, 10, "");
  								this.multiList.addTextCell(selectRow, 11, "");
  								this.multiList.addSpeCharCell(selectRow, 12, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 13, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 14, new Vector());
  								this.multiList.addTextCell(selectRow, 15, "");
  								this.multiList.addTextCell(selectRow, 16, "");
  								this.multiList.addTextCell(selectRow, 17, "");
  								this.multiList.addTextCell(selectRow, 18, "");
  							} else if (colCount == 11) {
  								this.multiList.addTextCell(selectRow, 0, (new Integer(
  										j + 1)).toString());
  								this.multiList.addSpeCharCell(selectRow, 1, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 2, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 3, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 4, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 5, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 6, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 7, new Vector());
  								this.multiList.addSpeCharCell(selectRow, 8, new Vector());
  								this.multiList.addTextCell(selectRow, 9, "");
  								this.multiList.addSpeCharCell(selectRow, 10, new Vector());
  							}
  						for (int i = 0; i < colCount; i++) {
  							if (this.multiList.getComboBoxObject(selectRow, i) != null) {
  								// 获得下拉框的选中项
  								String comboxSelectedStr = (String) valueOfRow[i];
  								// 将下拉框中获得的选中项和所有可选项添加到指定单元格中
  								this.multiList.setComboBoxSelected(selectRow, i,
  										comboxSelectedStr, this.multiList.getCellAt(
  												copyRowIndex, i)
  												.getSelectableValues());
  								continue;

  							}
  							// 复制粘贴特殊字符
  							else if (this.multiList.getCellAt(selectRow, i).getType() == 5) {
  								Vector v = (Vector) valueOfRow[i];
  								if (v.size() > 0) {
  									// 清空单元格中原有的特殊字符
  									this.multiList.getCellAt(selectRow, i)
  											.getSpecialCharacter()
  											.clearAll();
  									// 修改原单元格，将获得的的特殊字符值加到指定的单元格中
  									this.multiList.getCellAt(selectRow, i)
  											.getSpecialCharacter()
  											.resumeData(v);
  									// addSpeCharCell(selectRow, i, v);
  								}
  							} else {
  								this.multiList.addTextCell(selectRow, i,
  										(String) valueOfRow[i]);
  							}
  						}
  						selectRow++;
  					}
  				}
  				if (colCount == 5 || colCount == 11) {
  			        for (int i = 0; i < this.multiList.getNumberOfRows(); i++) {
  			        	this.multiList.addTextCell(i, 0, (new Integer(i + 1)).toString());
  			        }
  			      }
  			} catch (Exception ex) {
  				ex.printStackTrace();
  				JOptionPane.showMessageDialog(null, "数据不正确不能粘贴!");
  			}
  	  }
    }
    
    //CCEnd SS1
    
    
    
    /**  
     * 设置默认值到行列
     * @param i　行号
     * @param j　列号
     * @param model　ExtendAttModel　属性封装类
     * @see ExtendAttModel
     */
    private void addDefalutAttsToTable(int i, int j, ExtendAttModel model)
    {

        Object obj1 = model.getAttDefault();
        if (model.getAttType().equalsIgnoreCase("EnumeratedType"))
        {
            HashMap map = model.getFeature();
            String classPath = (String) map.get("classpath");
            String newString = (String) map.get("newMethod");
            try
            {
                EnumeratedType[] ets = (EnumeratedType[])this.etMaps.get(
                        classPath);
                String value = "";
                Vector values = new Vector();
                for (int k = 0; k < ets.length; k++)
                {
                    values.add(ets[k].getDisplay(local));
                }
                multiList.addComboBoxCell(i, j, value, values);
            }
            catch (Exception e)
            {
                  if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(getParentJFrame(), e.getLocalizedMessage());
            }
        }
        else if (model.getAttType().equals("SpecChar"))
            {
                if (obj1 != null)
                {

                    multiList.addSpeCharCell(i, j, new Vector());
                }
                else
                {
                    multiList.addSpeCharCell(i, j, new Vector());
                }

            }

        else
        if (model.getAttType().equalsIgnoreCase("Coding"))
        {
            HashMap map = model.getFeature();
            String defaultValue = model.getAttDefault();
            boolean isValid = false;
            String sorttype = (String) map.get("SortType");
            Collection sorts = (Collection)this.etMaps.get(sorttype);
            Iterator ite = sorts.iterator();
            Vector vec = new Vector();
            while (ite.hasNext())
            {
                CodingInfo info = (CodingInfo)ite.next();
                //CCBegin by liunan 2009-09-14
                if(info.getCodeContent().equalsIgnoreCase("C"))
                  vec.add(0,info.getCodeContent());
                else
                //CCEnd by liunan
                vec.add(info.getCodeContent());
                if(info.getCodeContent().equals(defaultValue))
                  isValid = true;
            }
           if(isValid&&defaultValue!=null&&!defaultValue.equalsIgnoreCase(""))
                this.multiList.addComboBoxCell(i, j,
                                          defaultValue,
                                          vec);
           else
             //20070302xuejing modify
               this.multiList.addComboBoxCell(i, j,
                                           (String) vec.elementAt(0),
                                           vec);
        }
        else
        if (model.getAttType().equalsIgnoreCase("String"))
        {
            HashMap map = model.getFeature();
            String refType = (String) map.get("RefType");
            if (refType == null)
            {
                if (obj1 != null)
                {
                    multiList.addTextCell(i, j, obj1.toString());
                }
                else
                {
                    multiList.addTextCell(i, j, "");
                }
            }
            else
            if (refType.equals("EnumerateType"))
            {
                String classPath = (String) map.get("classpath");
                EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
                EnumeratedType et;
                Vector vec = new Vector();
                for (int k = 0; k < ets.length; k++)
                {
                    et = (EnumeratedType) ets[k];
                    vec.add(et.getDisplay(local));
                }
                if (obj1 != null)
                {
                    this.multiList.addComboBoxCell(i, j, obj1.toString(), vec);
                }
                else
                {
                    this.multiList.addComboBoxCell(i, j, "", vec);
                }
            }
            else
            if (refType.equals("ComboAtts"))
            {

                String sorttype = (String) map.get("SortType");
                Collection sorts = (Collection)this.etMaps.get(sorttype);
                String valueStr = "";
                String sortStr = "";
                if (obj1 != null)
                {
                    StringTokenizer ston = new StringTokenizer((String) obj1,
                            ";");
                    
                    if (ston.hasMoreTokens())
                    {
                        valueStr = ston.nextToken();
                    }
                    if (ston.hasMoreTokens())
                    {
                        sortStr = ston.nextToken();
                    }
                }
                Iterator ite = sorts.iterator();
                Vector vec = new Vector();
                while (ite.hasNext())
                {
                    vec.add(((CodingInfo) ite.next()).getCodeContent());
                }
                //begin CR2
                if(sortStr.equals(""))
                {
                	sortStr = (String)vec.elementAt(0);
                }//end CR2
                this.multiList.addComboBoxCell(i, j, valueStr, sortStr, vec);
            }

        }
        else
        if (model.getAttType().equalsIgnoreCase("Boolean"))
        {
            boolean flag = true;
            if (rds != null)
            {
                for (int len = 0; len < rds.length; len++)
                {

                    if (j == rds[len])
                    {
                        flag = false;

                    }
                }
            }
            if (obj1 != null)
            {
                if (flag)
                {
                    multiList.addCheckboxCell(i, j,
                                              (new Boolean(obj1.toString())).booleanValue());
                }
                else
                {
                    multiList.addRadioButtonCell(i, j,
                                                 (new Boolean(obj1.toString())).
                                                 booleanValue());
                }
            }
            else
            {
                multiList.addCheckboxCell(i, j, false);
            }
        }
        else
        {
            if (obj1 != null)
            {
                multiList.addTextCell(i, j, obj1.toString());
            }
            else
            {
                multiList.addTextCell(i, j, "");
            }
        }
    }
    /**
     * 删除按钮执行事件
     * @param e ActionEvent 按钮触发事件
     */
    void deleteButton_actionPerformed(ActionEvent e)
    {
      int row = this.multiList.getSelectedRow();
      if (row != -1)
        multiList.removeRow(row);
      if (row > 0) {
        this.multiList.selectRow(row - 1);
      }
      else
      if (this.multiList.getTable().getRowCount() > 0) {
        this.multiList.selectRow(row);
      }
    }


    /**
     * 把字符值转为附加属性类型对应的值
     * @param model　属性封装类
     * @param text　属性的字符串值
     * @return Object 字符值转为的附加属性类型对应的值
     * @see ExtendAttModel
     */
    Object change(ExtendAttModel model, String text)
    {
        String rstcType = model.getAttType();
        String defName = model.getAttDisplay();
        HashMap map = model.getFeature();
        if (rstcType.equalsIgnoreCase("EnumeratedType"))
        {
            String classPath = (String) map.get("classpath");
            EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
            EnumeratedType et;
            for (int i = 0; i < ets.length; i++)
            {
                et = (EnumeratedType) ets[i];
                if (et.getDisplay(local).equals(text))
                {
                    return et;
                }
            }
        }
        else
        if (rstcType.equalsIgnoreCase("Coding"))
        {
//            String sortType = (String) map.get("SortType");
//            Collection col = (Collection) etMaps.get(sortType);
//            Iterator i = col.iterator();
//            CodingIfc et;
//            while (i.hasNext())
//            {
//                et = (CodingIfc) i.next();
//                if (et.getCodeContent().equals(text))
//                {
//                    return et.getBsoID();
//                }
//            }
          return text;
        }
        else
        if (rstcType.equalsIgnoreCase("int"))
        {
            int textVal = 0;
            textVal = Integer.parseInt(text);
            return new Integer(textVal);
        }
        else
        if (rstcType.equalsIgnoreCase("double"))
        {
            double doubleVal = 0;
            doubleVal = Double.parseDouble(text);
            return new Double(doubleVal);
        }
        else
        if (rstcType.equalsIgnoreCase("float"))
        {
            float floatVal = 0;
            floatVal = Float.parseFloat(text);
            return new Float(floatVal);
        }
        else if (rstcType.equalsIgnoreCase("long"))
        {
            long longVal = 0;
            longVal = Long.parseLong(text);
            return new Long(longVal);
        }
        else if (rstcType.equalsIgnoreCase("string"))
        {
            return text;
        }
        else if (rstcType.equalsIgnoreCase("boolean"))
        {
            if (text.equalsIgnoreCase("true"))
            {
                return new Boolean(true);
            }
            else
            {
                return new Boolean(false);
            }

        }
        return null;
    }


    /**
     * 设置工作模式，有三种　，编辑，查看，搜索
     * @param model int
     */
    public void setModel(int model)
    {
        if (model == CappExAttrPanel.VIEW_MODEL)
        {
        	//CCBegin SS1
        	 this.addUpButton.setEnabled(false);
        	
        	//CCEnd SS1
            this.multiList.setCellEditable(false);
            this.addButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
            this.calculateButton.setEnabled(false);
            this.editButton.setEnabled(false);
//  		CCBegin by liuzc 2009-12-14 原因：解放升级 
            this.inheritButton.setEnabled(false);
            this.copyAddButton.setEnabled(false);
//  		CCBegin by liuzc 2009-12-14 原因：解放升级    
            //CCBegin SS2
            this.toolButton.setEnabled(false);
            this.toolDecreaseButton.setEnabled(false);
            //CCEnd SS2
            
        }
        else
        {
        	
        	//CCBegin SS1
       	 this.addUpButton.setEnabled(true);
       	
       	//CCEnd SS1
            this.multiList.setCellEditable(true);
            this.addButton.setEnabled(true);
            this.deleteButton.setEnabled(true);
            this.calculateButton.setEnabled(true);   
            this.editButton.setEnabled(true);
//  		CCBegin by liuzc 2009-12-14 原因：解放升级 
            this.inheritButton.setEnabled(true);
            this.copyAddButton.setEnabled(true);
//  		CCBegin by liuzc 2009-12-14 原因：解放升级 
            
            //CCBegin SS2
            
            this.toolButton.setEnabled(true);
            this.toolDecreaseButton.setEnabled(true);
            //CCEnd SS2
    }
    }


    /**
     * 获得上级frame
     * @return JFrame 上级frame
     */
    public JFrame getParentJFrame()
    {
    	Component parent = getParent();

        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        return (JFrame) parent;
    }

    /**
     * 设置添加按钮长度
     * @param i int 按钮长度
     */
    public void setAddButtonSize(int i)
     {
        addButton.setMaximumSize(new Dimension(i, 23));
        addButton.setMinimumSize(new Dimension(i, 23));
        addButton.setPreferredSize(new Dimension(i, 23));
       // addButton.setMnemonic('A');
        //addButton.setText("添加(A)");
     }
    
    //CCBegin SS1
    
    /**
     * 设置向上添加按钮长度
     * @param i int
     */
    public void setAddUpButtonSize(int i) {
      addUpButton.setMaximumSize(new Dimension(i, 23));
      addUpButton.setMinimumSize(new Dimension(i, 23));
      addUpButton.setPreferredSize(new Dimension(i, 23));
    }
    
    //CCEnd SS1

     /**
      * 设置删除按钮长度
      * @param i int 按钮长度
      */
     public void setDelButtonSize(int i)
  {
     deleteButton.setMaximumSize(new Dimension(i, 23));
     deleteButton.setMinimumSize(new Dimension(i, 23));
     deleteButton.setPreferredSize(new Dimension(i, 23));
    // addButton.setMnemonic('A');
     //addButton.setText("添加(A)");
  }

  /**
   * 设置添加按钮的助记符
   * @param mn char 助记符名
   */
  public void setAddButtonMnemonic(char mn)
  {
      this.addButton.setMnemonic(mn);
      this.addButton.setText("添加"+"("+mn+")");
  }

  /**
  * 设置删除按钮的助记符
  * @param mn char 助记符名
  */
  public void setDelButtonMnemonic(char mn)
  {
      this.deleteButton.setMnemonic(mn);
      this.deleteButton.setText("删除"+"("+mn+")");
  }


  /**
   * 上移按钮执行事件
   * @param e ActionEvent 按钮触发事件
   */
  void upButton_actionPerformed(ActionEvent e) {
       if( multiList.getTable().isEditing())
       multiList.getTable().getCellEditor().stopCellEditing();
        int selectRow = multiList.getSelectedRow();
        if(selectRow==0||selectRow==-1)
        {
          return;
        }
        else
        {
            int uprow=selectRow-1;
            int colums=multiList.getTable().getColumnCount();
            //存放上一行的数据
            CappTextAndImageCell tempobj;
            for(int i=0;i<colums;i++)
            {
               //缓存上一行第i列的数据
               tempobj=multiList.getCellAt(uprow,i);
               //将选中行的数据挪到上一行

               multiList.addCell(uprow,i,multiList.getCellAt(selectRow,i));
               //将缓存的数据放在选中行
               multiList.addCell(selectRow,i,tempobj);
            }
           multiList.setSelectedRow(uprow);
         }


  }


  /**
   * 下移按钮事件执行
   * @param e ActionEvent 按钮触发事件
   */
  void downButton_actionPerformed(ActionEvent e) {
    if( multiList.getTable().isEditing())
       multiList.getTable().getCellEditor().stopCellEditing();
    int selectRow = multiList.getSelectedRow();
    if (selectRow == (multiList.getRowCount() - 1) || selectRow == -1) {
      return;
    }
    else {
      int downrow = selectRow + 1;
      int colums = multiList.getTable().getColumnCount();
      //存放下一行的数据
      CappTextAndImageCell tempobj;
      for (int i = 0; i < colums; i++) {
        //缓存下一行第i列的数据
        tempobj = multiList.getCellAt(downrow, i);
        //将选中行的数据挪到下一行
        multiList.addCell(downrow, i, multiList.getCellAt(selectRow, i));
        //将缓存的数据放在选中行
        multiList.addCell(selectRow, i, tempobj);
      }
      multiList.setSelectedRow(downrow);
    }

  }


  /**
   * 计算按钮事件执行
   * @param e ActionEvent 按钮触发事件
   */
  void calculateButton_actionPerformed(ActionEvent e) {

    calculate();
  }
  
  //CCBegin SS2
  
  /**
   * 工装
   */
  void toolButton_actionPerformed(ActionEvent e){
	  
	  try {
		  
		launchChooserDialog("QMTool");
		
	} catch (QMRemoteException e1) {
		e1.printStackTrace();
	}
  }
  
  /**
   * 初始化资源
   */
  private void initResources()
  {
      try
      {
          if (resource == null)
          {
              resource = ResourceBundle.getBundle(RESOURCEOther, Locale.getDefault());
              return;
          }
      }
      catch (MissingResourceException _exception)
      {
          _exception.printStackTrace();
      }
  }
  /**
   * 获得本地化显示信息
   * @param s 资源文件中的key
   * @param aobj 参数集合
   * @return String 本地化显示信息
   */
  private String display(String s, Object aobj[])
  {
      String s1 = "";
      if (resource == null)
      {
    	  
          initResources();
      }
      s1 = QM.getLocalizedMessage(RESOURCEOther, s, aobj, null);

      return s1;
  }
  /**
   * 启动选择界面，用户可以选择业务对象加到多列列表中，其后编辑其关联
   * @throws QMRemoteException
   * 问题(5)20080602 徐春英修改  修改原因:添加工序关联文档时,普通用户能搜索出全部文档,因为
   * 搜索出来的是文档master没有权限控制,现在要求搜索结果显示文档,这样就有权限控制了
   * 
   */
  protected void launchChooserDialog(String bsoName)
  throws QMRemoteException
 {

		String s = display("20", null);
		String s1 = bsoName; //getOtherSideClassName();
		
		CappChooser qmchooser = new CappChooser(s1, s, this
				.getTopLevelAncestor());//CR8

		qmchooser.setLastIteration(false);
		qmchooser.setSize(650, 500);
		String s2 = display("57", null);
		try {
			
			qmchooser.setMultipleMode(false);
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		qmchooser.setChildQuery(true);
		qmchooser.addListener(new CappQueryListener() {
			public void queryEvent(CappQueryEvent qmqueryevent) {
				if (qmqueryevent.getType().equals(CappQueryEvent.COMMAND)
						&& qmqueryevent.getCommand().equals(CappQuery.OkCMD)) {
					CappChooser qmchooser1 = (CappChooser) qmqueryevent
							.getSource();
					BaseValueIfc awtobject[] = qmchooser1.getSelectedDetails();
					BaseValueIfc finalawtobject[];
					//问题(5) 20080602 徐春英修改
					ArrayList list = new ArrayList();
					try {
						int selectRow=multiList.getSelectedRow();
						
						if (awtobject != null&&multiList.getSelectedRow()!=-1) {
							int length = awtobject.length;
							for (int j = 0; j < length; j++) {
								
								Object obj =awtobject[j];
								
								if (obj instanceof QMEquipmentInfo) {
									
									

									QMEquipmentInfo equiInfo = (QMEquipmentInfo) obj;
									insertEq(equiInfo,selectRow,1);


									
								}else if(obj instanceof QMToolInfo){
									
									QMToolInfo tool = (QMToolInfo) obj;
									
									String toolName=tool.getToolName();
									
								
									
									System.out.println("tool.getToolCf().getCodeContent()==========="+tool.getToolCf().getCodeContent());
									
									//CCBegin SS3
									insertTool(tool,selectRow, 3);
									
									/*  去掉工具类型过滤
									if(tool.getToolCf().getCodeContent().equals("量具")||
											tool.getToolCf().getCodeContent().equals("万能量具")||
											tool.getToolCf().getCodeContent().equals("专用量具")||
											tool.getToolCf().getCodeContent().equals("检具")||
											tool.getToolCf().getCodeContent().equals("检验辅具")||
											tool.getToolCf().getCodeContent().equals("量仪")||
											tool.getToolCf().getCodeContent().equals("检验夹具")||
											tool.getToolCf().getCodeContent().equals("检验量辅具")) 
									{

										
										insertTool(tool,selectRow, 3);
										

									}
									else {
										
										 String message = "请选择量/检具类型的工装(如:量具、万能量具、专用量具、检具、检验辅具、量仪、检验夹具、检验量辅具)!";
						                  String title = QMMessage.getLocalizedMessage(RESOURCE,
						                          "information", null);
						                  JOptionPane.showMessageDialog(parentJFrame,
						                                                message,
						                                                title,
						                                                JOptionPane.INFORMATION_MESSAGE);
						                  return;
										
									}
									
									*/
									
									//CCEnd SS3
									
//									else{
//										
//										
//										multiList.addTextCell(multiList
//												.getSelectedRow(), 2, toolName);
//										
//										multiList.addTextCell(multiList
//												.getSelectedRow(), 3, tool
//												.getToolNum());
//										//CCBegin SS9
//										String count=multiList.getCellText(multiList
//												.getSelectedRow(), 5);
//										if(count==null&&count.equals(""))
//										  multiList.addTextCell(multiList
//												.getSelectedRow(), 5,"1");
//										//CCEnd SS9
//										
//										multiList.addTextCell(multiList
//												.getSelectedRow(), 4, tool.getToolSpec());
//										multiList.addTextCell(selectRow, 23, tool.getBsoID());
//									}
									

									
								}
							}
							

						}else{
							 String message = "请选择要编辑的行!";
			                  String title = QMMessage.getLocalizedMessage(RESOURCE,
			                          "information", null);
			                  JOptionPane.showMessageDialog(parentJFrame,
			                                                message,
			                                                title,
			                                                JOptionPane.INFORMATION_MESSAGE);
			                  return;
						}
						int k = multiList.getNumberOfRows() - 1;
						multiList.selectRow(k);
						return;
					} catch (Exception _e) {
						_e.printStackTrace();
						return;
					}
				} else {
					return;
				}
			}
			

		});
		setLocation(qmchooser);
		qmchooser.setVisible(true);
		setEnabled(true);
}
  
  /**
   * 插入设备
   * @param eq
   * @param row
   * @param col
   * //20140616
   * void
   */
  private void insertEq(QMEquipmentInfo eq,int row,int col)
  {
      String no = "--";
      String id = multiList.getCellText(row, 20);
      String mid = multiList.getCellText(row, 21);
      if((id == null || id.isEmpty()) && (mid == null || mid.isEmpty()))
      {
          


          int allrow = emptyRows(row,col,3);
          downRow(row, col, 3-allrow,false);

     
          multiList.addTextCell(row, 20, eq.getBsoID());

          multiList.addTextCell(row, col, eq.getEqNum());
          //CCBegin SS16
          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
              multiList.addTextCell(row +1, col, no);
          else
              multiList.addTextCell(row + 1, col, eq.getEqModel());
          
          if(eq.getEqName() == null || eq.getEqName().length() == 0)
              multiList.addTextCell(row + 2, col, no);
          else
              multiList.addTextCell(row + 2, col, eq.getEqName());
         //CCEnd SS16
          if(eq.getEqManu() == null || eq.getEqManu().length() == 0)
              multiList.addTextCell(row + 3, col, no);
          else
              multiList.addTextCell(row + 3, col, eq.getEqManu());
          
      }else if(!id.isEmpty())
      {
          int allrow = multiList.getRowCount();
          if(allrow-row-1<3)
            downRow(row, col, 3-(allrow-row-1),false);
          
          multiList.addTextCell(row, 20, eq.getBsoID());
          if(multiList.getCellText(row + 1, 20) != null)
          multiList.addTextCell(row+1, 20, "");
          if(multiList.getCellText(row + 2, 20) != null)
          multiList.addTextCell(row+2, 20, "");
          if(multiList.getCellText(row + 3, 20) != null)
          multiList.addTextCell(row+3, 20, "");
          multiList.addTextCell(row, col, eq.getEqNum());
          //CCBegin SS16
          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
              multiList.addTextCell(row + 1, col, no);
          else
              multiList.addTextCell(row + 1, col, eq.getEqModel());
          if(eq.getEqName() == null || eq.getEqName().length() == 0)
              multiList.addTextCell(row + 2, col, no);
          else
              multiList.addTextCell(row + 2, col, eq.getEqName());
          //CCEnd SS16
          
          if(eq.getEqManu() == null || eq.getEqManu().length() == 0)
              multiList.addTextCell(row + 3, col, no);
          else
              multiList.addTextCell(row + 3, col, eq.getEqManu());
      }else if(!mid.isEmpty())
      {
          multiList.addTextCell(row, 21, "");

          int allrow = emptyRows(row,col,3);
          downRow(row, col, 3-allrow,false);


          multiList.addTextCell(row, 20, eq.getBsoID());
          multiList.addTextCell(row, col, eq.getEqNum());
          //CCBeign SS16
          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
              multiList.addTextCell(row + 1, col, no);
          else
              multiList.addTextCell(row + 1, col, eq.getEqModel());
          if(eq.getEqName() == null || eq.getEqName().length() == 0)
              multiList.addTextCell(row + 2, col, no);
          else
              multiList.addTextCell(row + 2, col, eq.getEqName());
          //CCEnd SS16
         
          if(eq.getEqManu() == null || eq.getEqManu().length() == 0)
              multiList.addTextCell(row + 3, col, no);
          else
              multiList.addTextCell(row + 3, col, eq.getEqManu());
      }

  }
  /**
   * 设置组件的位置
   * @param comp 组件
   */
  private void setLocation(Component comp)
  {
      Dimension compSize = comp.getSize();
      int compH = compSize.height;
      int compW = compSize.width;
      Dimension screenS = Toolkit.getDefaultToolkit().getScreenSize();
      int screenH = screenS.height;
      int screenW = screenS.width;
      comp.setLocation(Math.abs((screenW - compW) / 2),
                       Math.abs((screenH - compH) / 2));
  }
  /**
   * 探测指定列的空行数
   * @param row
   * @param col
   * @param count
   * @return
   * //20140616 
   * int
   */
  private int emptyRows(int row,int col,int count)
  {
      String temp;
      int rows = 0;
      for(int i=1;i<=count;i++)
      {
          temp = multiList.getCellText(row+i, col);
          if(temp==null)
              return rows;
          if(!temp.isEmpty())
              return rows;
          rows++;
      }
      return rows;
  }
  
  /**
   * 下穿行
   * @param row
   * @param col void
   * 
   */
  private void downRow(int row, int col, int count,boolean isTool)
  {
      if(count<=0)
          return;
   // 下移数据集合
      Vector valueDownVec = new Vector();
      Vector idVec1 = new Vector();
      Vector idVec2 = new Vector();
      Vector idVec3 = new Vector();
      int allRowCount = multiList.getRowCount();
      for(int a = row + 1;a < allRowCount;a++)
      {
          String nexRowStrData = multiList.getCellAt(a, col).getStringValue();
          valueDownVec.add(nexRowStrData);
      }
      if(!isTool)
      {
          for(int c = row + 1;c < allRowCount;c++)
          {
              String nexRowStrData = multiList.getCellAt(c, 20).getStringValue();
              idVec1.add(nexRowStrData);
              multiList.addTextCell(c, 20, "");
          }
          for(int d = row + 1;d < allRowCount;d++)
          {
              String nexRowStrData = multiList.getCellAt(d, 21).getStringValue();
              idVec2.add(nexRowStrData);
              multiList.addTextCell(d, 21, "");
          }
      }
      if(isTool)
      {
          for(int e = row + 1;e < allRowCount;e++)
          {
              String nexRowStrData = multiList.getCellAt(e, 23).getStringValue();
              idVec3.add(nexRowStrData);
              multiList.addTextCell(e, 23, "");
          }
      }

      allRowCount = multiList.getRowCount();
      for(int b = valueDownVec.size();b > 0;b--)
      {
          String value = (String)valueDownVec.get(b - 1);
          multiList.addTextCell(allRowCount + b - valueDownVec.size() - 1, col, value);
      }
      if(!isTool)
      {
          for(int f = idVec1.size();f > 0;f--)
          {
              String value = (String)idVec1.get(f - 1);
              multiList.addTextCell(allRowCount + f - idVec1.size() - 1, 20, value);
          }
          for(int g = idVec2.size();g > 0;g--)
          {
              String value = (String)idVec2.get(g - 1);
              multiList.addTextCell(allRowCount + g - idVec2.size() - 1, 21, value);
          }
      }
      if(isTool)
      {
          for(int h = idVec3.size();h > 0;h--)
          {
              String value = (String)idVec3.get(h - 1);
              multiList.addTextCell(allRowCount + h - idVec3.size() - 1, 23, value);
          }
      }
  }
  /**
   * 插入量具
   * @param tool
   * @param row
   * @param col
   * //20140616
   * void
   */
  private void insertTool(QMToolInfo tool,int row,int col)
  {
      String no = "--";
      String id = multiList.getCellText(row, 9);
System.out.println("insertTool 添加量具 id=="+id);
      if(id == null || id.isEmpty())
      {

//          int allrow = emptyRows(row,col,2);
//          downRow(row, col, 2-allrow,false);
//          String temp = multiList.getCellText(row+1, col);
//          int allrow = multiList.getRowCount();
//          if(temp==null)
//              downRow(row, col, 2,true);
//          else if(!temp.isEmpty())
//              downRow(row, col, 2,true);
//          else if(allrow-row-1<2)
//              downRow(row, col, 2-(allrow-row-1),true);
    
          multiList.addTextCell(row, 9, tool.getBsoID());
//          multiList.addTextCell(row, col, tool.getToolNum());
        //CCBegin SS14
//          if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() == null || tool.getToolSpec().length() == 0))
//         	 multiList.addTextCell(row + 1, col, tool.getToolStdNum());
//          
//          else if((tool.getToolSpec() != null && tool.getToolSpec().length() != 0)&&(tool.getToolStdNum()==null||tool.getToolStdNum().equals("")))
//               multiList.addTextCell(row + 1, col, tool.getToolSpec());
//           
//          else if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() != null && tool.getToolSpec().length() != 0))
//        	  multiList.addTextCell(row + 1, col, tool.getToolSpec());
//          else
//               multiList.addTextCell(row + 1, col,no );
//          //CCEnd SS14
         
            multiList.addTextCell(row , col, tool.getToolName());
            multiList.addTextCell(row , col+1, tool.getToolSpec());

      }else if(!id.isEmpty())
      {
                   
      
          multiList.addTextCell(row, 9, "");
          
          multiList.addTextCell(row, 9, tool.getBsoID());
          multiList.addTextCell(row, col, tool.getToolName());
          multiList.addTextCell(row , col+1, tool.getToolSpec());
           
      }
  }
  
  
  //CCEnd SS2
 
//CCBegin by leixiao 2008-10-27 原因：解放系统升级 
  /**
   * 实现过程流程到控制计划的数据传递
   * @param e ActionEvent
   */
  void inheritButton_actionPerformed(ActionEvent e) {
	    if (groupname.equals("特性控制")) {
	        QMProcedureInfo info = container.getProIfc();
//          CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
	    	if(info == null || info.getProcessControl() == null)
	    	{
	    		return;    
	    	}
//          CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
	        ExtendAttContainer con = info.getProcessControl();

	       // ExtendAttContainer femacon = info.getFema();
	        //控制计划产品特性
	        Vector productIdentityValue = getExtendAttValue(con,
	            "productAttr");
	        //控制计划过程特性
	        Vector productCharacterValue = getExtendAttValue(con, "processAttr");
	        //控制计划分类
	        Vector sortValue = getExtendAttValue(con, "attrSort");
	        int ii=sortValue.size();
	        int row = this.multiList.getNumberOfRows();
	        if(row==0){
	        for (int i = row; i < row + ii; i++) {
	          for (int j = 0; j < group.getCount(); j++) {
	            addDefalutAttsToTable(i, j, group.getAttributeAt(j));
	          }
	        }
	        //传递内容      
	        for (int k = row, n = 0; k < row + ii; k++, n++) {

	      	  multiList.addTextCell(k, 0, (String) productIdentityValue.elementAt(n));
	      	  multiList.addTextCell(k, 1, (String) productCharacterValue.elementAt(n));
	      	  multiList.addTextCell(k, 2, (String) sortValue.elementAt(n));
	        }
	    	}
	        else{
	        int srow = this.multiList.getSelectedRow();
	    	if(srow >=0)
	    	{
		      	  multiList.addTextCell(srow, 0, (String) productIdentityValue.elementAt(srow));
		      	  multiList.addTextCell(srow, 1, (String) productCharacterValue.elementAt(srow));
		      	  multiList.addTextCell(srow, 2, (String) sortValue.elementAt(srow));
	    	}	
	        }

	        
	      	
	    }
    if (groupname.equals("控制计划")) {
      QMProcedureInfo info = container.getProIfc();
//    CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
      if(info == null || info.getProcessControl() == null)
      {
    	  return;    
      }
//    CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
      ExtendAttContainer con = info.getProcessFlow();

      ExtendAttContainer femacon = info.getFema();
      //特性编号
    //  Vector numberValue = getExtendAttValue(con, "characterNum");
      //产品特性
      Vector productIdentityValue = getExtendAttValue(con,
          "productIdentity");
      //过程特性
      Vector productCharacterValue = getExtendAttValue(con, "productCharacter");
      //分类
      Vector sortValue = getExtendAttValue(con, "sort");
      int ii=sortValue.size();


      //首先添加行
      int row = this.multiList.getNumberOfRows();
      if(row==0){
      for (int i = row; i < row + ii; i++) {
        for (int j = 0; j < group.getCount(); j++) {
          addDefalutAttsToTable(i, j, group.getAttributeAt(j));
        }
      }
      //传递内容      
      for (int k = row, n = 0; k < row + ii; k++, n++) {

    	  multiList.addTextCell(k, 1, (String) productIdentityValue.elementAt(n));
    	  multiList.addTextCell(k, 2, (String) productCharacterValue.elementAt(n));
    	  multiList.addTextCell(k, 3, (String) sortValue.elementAt(n));
      }
  	}
      else{
    	  int srow = this.multiList.getSelectedRow();
        	if(srow >=0)
        	{
      	      	  multiList.addTextCell(srow, 1, (String) productIdentityValue.elementAt(srow));
      	      	  multiList.addTextCell(srow, 2, (String) productCharacterValue.elementAt(srow));
      	      	  multiList.addTextCell(srow, 3, (String) sortValue.elementAt(srow));
        	}	
      }
    }
    if (groupname.equals("过程FMEA")) {
        QMProcedureInfo info = container.getProIfc();
//      CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
    	if(info == null || info.getProcessControl() == null)
    	{
    		return;    
    	}
//      CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
        ExtendAttContainer con = info.getProcessControl();

        //分类
        Vector sortValue = getExtendAttValue(con, "attrSort");
        if(sortValue==null||sortValue.size()==0){
         ExtendAttContainer con1 = info.getProcessFlow();         
         //分类
         sortValue = getExtendAttValue(con1, "sort");
        }
        //首先添加行
        int row = this.multiList.getNumberOfRows();
      //  System.out.println("-----"+row+"   "+sortValue);
        if(row==0){
          for (int i = row; i < row + sortValue.size(); i++) {
            for (int j = 0; j < group.getCount(); j++) {
              addDefalutAttsToTable(i, j, group.getAttributeAt(j));
            }
          }
          for (int k = 0; k < sortValue.size(); k++) {
        	  multiList.addTextCell(k + row, 4, (String) sortValue.elementAt(k));
          }
        }
        else{

        	 int srow = this.multiList.getSelectedRow();
        	 //System.out.println("-----------不为空"+srow);
        	if(srow >=0)
        	{
      	      	  multiList.addTextCell(srow, 4, (String) sortValue.elementAt(srow));
        	}	
        }

      }
  }

 // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
  // CCBegin by leixiao 2009-7-6 原因：解放系统升级 ,增加复制按钮
  /**
   * 添加属性复制功能。
   * @param e ActionEvent
   * @author l 2008-12-26
   */
  void copyAddButton_actionPerformed(ActionEvent e)
  {
    int srow = this.multiList.getSelectedRow();
    	if(srow >=0)
    	{
        int rows = this.multiList.getNumberOfRows();
        int row = this.multiList.getNumberOfRows();
        for (int i = 0; i < group.getCount(); i++)
        {
          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
          multiList.addCell(row,i,multiList.getCellAt(srow,i));
        }
    }
  }
  // CCEnd  by leixiao 2009-7-6 原因：解放系统升级 
  
  
  
  /**
   * 计算按钮事件执行
   * @param e ActionEvent 按钮触发事件
   * 问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
   */
  void attrButton_actionPerformed(ActionEvent e)
  {
      if (container.getSecondClassifyValue().equals("控制计划")) 
      {
    	  String[] processFlowHead = flowMultiList.getHeadings();//(String[])TechnicsStepJPanel.list.get(0);
    	  String[]  controlPlanHead = multiList.getHeadings();//(String[])TechnicsStepJPanel.list.get(1);
    	  Vector vec = compareTableHeader(processFlowHead,controlPlanHead);
    	  int  flowRows =flowMultiList.getRowCount();
    	  int planRows = multiList.getRowCount();
    	  if(planRows > 0)
    	  {
    		  if(planRows <= flowRows)
    		  {
    			  for(int d = planRows; d < flowRows; d++)
    		      {
    		          for (int m = 0; m < group.getCount(); m++)
        			  {
        			      addDefalutAttsToTable(d, m, group.getAttributeAt(m));
        			  }
    		      }
    		  }

    		  for(int i = 0; i < vec.size(); i++)
        	  {
        	      int[] str = (int[])vec.elementAt(i);
    		      {
    		          for(int n = 0; n < flowRows; n++)
    			      {
    			          String flowStr = flowMultiList.getCellText(n, str[0]);
    				      String planStr = multiList.getCellText(n, str[1]);
    				      if(planStr == null ||planStr.equals(""))
    				      {
    				          multiList.addTextCell(n, str[1], flowStr);
    				      }
    			      }
    				
    		      }
    	      }
    	  }	
    	  else
    	  {
    	      for(int p = 0; p < flowRows; p++)
    		  {
    		      //int row = this.multiList.getNumberOfRows();
    			  for (int q = 0; q < group.getCount(); q++)
    			  {
    			      addDefalutAttsToTable(p, q, group.getAttributeAt(q));
    			  }
    			  for(int i = 0; i < vec.size(); i++)
    			  {
    			      int[] str = (int[])vec.elementAt(i);
    			      String flowStr = flowMultiList.getCellText(p, str[0]);
    			      String planStr = multiList.getCellText(p, str[1]);
    			      if(planStr == null ||planStr.equals(""))
    			      {
    				      multiList.addTextCell(p, str[1], flowStr);
    			      }
    			  }
    		  }
    	  }
	  } 
  }

  //CCBegin SS2
     /**
	 * 清空工装
	 */
void toolDecreaseButton_actionPerformed(ActionEvent e){
	 
	 
	 int selectRow=this.multiList.getSelectedRow();
	  int rowCount=this.multiList.getRowCount();
	  //20140616
//	  int toolInfoRow=0;
	  int toolInfoRow=3;
	//20140616 end
	  if(selectRow<0){
		  
		  JOptionPane.showMessageDialog(getParentJFrame(),
					"请选择“量检具”工装编号行!", "提示",
					JOptionPane.INFORMATION_MESSAGE);

			return;
		  
	  }else{
		  
		  String toolBsoid=multiList.getCellText(selectRow, 9);
		  
		  
		  if(toolBsoid!=null&&!toolBsoid.equals("")){
			  

			  if(toolInfoRow!=0){
				  
				  
		           for(int j=0;j<toolInfoRow;j++ ){
			 
		        	   multiList.addTextCell(selectRow, 3, "");
		        	   multiList.addTextCell(selectRow, 4, "");
		        	   multiList.addTextCell(selectRow, 9, "");
		            }
		           
		           Hashtable toolMap=new Hashtable();
		           
					  if(!toolMap.containsKey(toolBsoid))
						  toolMap.put(toolBsoid, 1);
					  
//					  toolDeleteVec.add(toolMap);
		  }
			  
			  
			  
		  }else{
			  
			  JOptionPane.showMessageDialog(getParentJFrame(),
						"请选择“量检具”工装编号行!", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			  
		  }
		  
		  
	  }
	 
	  
	  
	  
}
  
  //CCEnd SS2

  //oneHeader和twoHeader分别是两个表格的表头字符串数组。
  //如果该方法返回的Vector中有两个元素，例如：[3,4]和[7,2]，则说明第一个表格的第3列和第二个表格的
  //第4列表头相同，第一个表格的第7列和第二个表格的第2列表头相同
  public  Vector compareTableHeader(String[] oneHeader,String[] twoHeader)
  {
      Vector vec=new Vector();
      if(null==oneHeader||null==twoHeader)
          return vec;
      for(int i=0;i<oneHeader.length;i++)
      {
          for(int j=0;j<twoHeader.length;j++)
          {
              if(null!=oneHeader[i]&&null!=twoHeader[j]&&oneHeader[i].equals(twoHeader[j]))
                  vec.addElement(new int[]{i,j});
          }
      }
      return vec;       
  }
  //add by wangh on 20070709 用于编辑按扭的监听方法.
  void editButton_actionPerformed(ActionEvent e){
	  ExtendAttGroup group;
	  
	  group = this.group.duplicate();
	  //得到所选择行的行号;
	  int row = multiList.getSelectedRow();

	  //得到总共列数;
	  int col = multiList.getNumberOfCols();
	  if(col*row>=0){
	  ArrayList rowObj = new ArrayList();
	  Vector nameVec = new Vector();
	  Vector rowVector = new Vector();
	  Vector contentVec = new Vector();
	  for(int i = 0;i<col;i++){
	  //得到选定一行的每个表格内的具体内容.
	  //begin CR1
	  ExtendAttModel model = group.getAttributeAt(i);
	  String type = model.getAttType();
	  if(type.equalsIgnoreCase("SpecChar"))
	  {
	      Vector vec = (Vector)multiList.getSelectedObject(row, i);
	      contentVec.add(vec);
	  }
	  else if(type.equalsIgnoreCase("String"))
	  {
		  HashMap map = model.getFeature();
          String refType = (String) map.get("RefType");
          String oneRow = "";
          if (refType == null)
          {
        	  oneRow = (String)this.multiList.getSelectedObject(row,
                      i);
          }
          else
          if (refType.equalsIgnoreCase("EnumerateType"))
          {
        	  oneRow = (String)this.multiList.getSelectedObject(row,
                      i);
          }
          else
          if (refType.equalsIgnoreCase("ComboAtts"))
          {
        	  oneRow = multiList.getCellAt(row, i).getStringValue() +
            ";" +
            (String) multiList.getCellAt(row, i).getValue();
          }
		  //String oneRow = multiList.getCellText(row, i);
		  contentVec.add(oneRow);
	  }
	  //Begin CR3
	  else 
	  {
		  String str = (String)this.multiList.getSelectedObject(row, i);
		  contentVec.add(str);
	  }
	  //End CR3
	  //end CR1
	  //得到所有表头.
	  String head = multiList.getHeading(i);
	  nameVec.addElement(head);

	  CappExAttrPanel cappExAttrPanel = new CappExAttrPanel();
	  //ExtendAttModel model = group.getAttributeAt(i);//CR1
	  //得到每个表格的值的控件
	  JComponent component = cappExAttrPanel.getCompoment(model);
	  rowVector.addElement(component);
	  //contentVec.add(oneRow);//CR1
	  }
	  rowObj.add(nameVec);
	  rowObj.add(rowVector);
	  rowObj.add(contentVec);

	  //获得自省文件中对应的一及分类名和二及分类名
	  String firstClassi = container.getFirstClassify();
	  String secondClassi = container.getSecondClassifyValue();

	  TParamJDialog p = new TParamJDialog(firstClassi,parentJFrame,secondClassi,rowObj);

	  Vector obj=(Vector)p.showDialog();

	  for(int j=0;j<col;j++){
		  ExtendAttModel model = group.getAttributeAt(j);
		  String type = model.getAttType();//CR1
		  //将JDialog中修改的数据传回multiList中显示。
		  if (type.equalsIgnoreCase("Coding")){
			         HashMap map = model.getFeature();
			         String sorttype = (String) map.get("SortType");
			         Collection sorts = (Collection)this.etMaps.get(sorttype);
			         Iterator ite = sorts.iterator();
			         Vector vec = new Vector();
			          CodingIfc et2 = null;
			         while (ite.hasNext()) {
			           et2 = (CodingInfo) ite.next();

			           vec.add( et2.getCodeContent());
			         }
			         multiList.addComboBoxCell(row, j,  obj.get(j), vec);
			         }
		  //begin CR1
		  else if(type.equalsIgnoreCase("String")){
			  //multiList.addTextCell(row, j, String.valueOf((String)obj.get(j)));
			  HashMap map = model.getFeature();
	            String refType = (String) map.get("RefType");
	            if (refType == null)
	            {
	                if (obj != null)
	                {
	                    multiList.addTextCell(row, j, (String)obj.get(j));
	                }
	                else
	                {
	                    multiList.addTextCell(row, j, "");
	                }
	            }
	            else
	            if (refType.equals("EnumerateType"))
	            {
	                String classPath = (String) map.get("classpath");
	                EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
	                EnumeratedType et;
	                Vector vec = new Vector();
	                for (int k = 0; k < ets.length; k++)
	                {
	                    et = (EnumeratedType) ets[k];
	                    vec.add(et.getDisplay(local));
	                }
	                if (obj != null)
	                {
	                    this.multiList.addComboBoxCell(row, j, obj.get(j).toString(), vec);
	                }
	                else
	                {
	                    this.multiList.addComboBoxCell(row, j, "", vec);
	                }
	            }
	            else
	            if (refType.equals("ComboAtts"))
	            {

	                String sorttype = (String) map.get("SortType");
	                Collection sorts = (Collection)this.etMaps.get(sorttype);
//	                String valueStr = "";
//	                String sortStr = "";
//	                if (obj != null)
//	                {
//	                    StringTokenizer ston = new StringTokenizer(String.valueOf((String)obj.get(j)),
//	                            ";");
//	                    if (ston.hasMoreTokens())
//	                    {
//	                        valueStr = ston.nextToken();
//	                    }
//	                    if (ston.hasMoreTokens())
//	                    {
//	                        sortStr = ston.nextToken();
//	                    }
//	                }
	                Iterator ite = sorts.iterator();
	                Vector vec = new Vector();
	                while (ite.hasNext())
	                {
	                    vec.add(((CodingInfo) ite.next()).getCodeContent());
	                }
                    String[] attr = (String[])obj.get(j);
	                this.multiList.addComboBoxCell(row, j, attr[0], attr[1], vec);
	            }

		  }
		  else if(type.equalsIgnoreCase("SpecChar")){
			  if (obj != null)
	             {
				     Vector vec = new Vector();
				     vec.addElement((String)obj.get(j));
	                 multiList.addSpeCharCell(row, j, vec);
	             }
	             else
	             {
	                 multiList.addSpeCharCell(row, j, new Vector());
	             }
			  
		  }//end CR1

	  }
  }
	  return;
  }

  /**
   * 根据属性名获得列
   * @param s String 属性名
   * @return int 属性所在列
   */
  public int getColByAttname(String s) {
    String[] heads = this.multiList.getHeadings();

    for (int i = 0; i < heads.length; i++) {
      if (heads[i].equals(s))
        return i;
    }
    return -1;
  }

  /**
   * 装配工序的计算
   *
   */
	  private void AssembleCalculate()
	  throws NumberFormatException
	  {

        //得到选中行
	      int row= multiList.getSelectedRow();
//		  CCBegin by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
		  int j1=this.getColByAttname("措施结果(S)");
	      int j2=this.getColByAttname("措施结果(O)");
	      int j3=this.getColByAttname("措施结果(D)");
	      int j4=this.getColByAttname("措施结果PRN");
//	      CCEnd by liuzc 2008-12-21 原因：解放升级,FMEA计算   参见TD号2685

//		    CCBegin by leixiao 2009-12-21 原因：解放升级,FMEA计算
	      //add by wangh on 20070516
	      int j5=this.getColByAttname("严重度(s)");
	      int j6=this.getColByAttname("频度数(o)");
	      int j7=this.getColByAttname("探测度(D)");
	      int j8=this.getColByAttname("风险顺序数(PRN)");
//	    CCEnd by leixiao 2009-12-21 原因：解放升级   

	      String arr55 = (String) multiList.getSelectedObject(row, j5);
          String arr66 = (String) multiList.getSelectedObject(row, j6);
          String arr77 = (String) multiList.getSelectedObject(row, j7);

          if (!arr55.equals("") && !arr66.equals("") && !arr77.equals(""))
          {
        	  int newnum1 = Integer.parseInt(arr55) * Integer.parseInt(arr66) *
              Integer.parseInt(arr77);
			  //将计算结果放到第3列
			  multiList.addTextCell(row, j8, String.valueOf(newnum1));
          }
          else
          {
        	  if(arr55.equals("")){
        		  String message = "严重度(s)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr66.equals("")){
        		  String message = "频度数(o)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr77.equals("")){
        		  String message = "探测度(D)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  }
        	  multiList.addTextCell(row, j8, "");
          }

 


	      String arr11 = (String) multiList.getSelectedObject(row, j1);
          //得到第2列的文本
          String arr22 = (String) multiList.getSelectedObject(row, j2);
          String arr33 = (String) multiList.getSelectedObject(row, j3);
//        计算
          if (!arr11.equals("") && !arr22.equals("") && !arr33.equals(""))
          {
        	  int newnum2 = Integer.parseInt(arr11) * Integer.parseInt(arr22) *
              Integer.parseInt(arr33);
			  //将计算结果放到第3列
			  multiList.addTextCell(row, j4, String.valueOf(newnum2));
          }
          else
          {
        	  if(arr11.equals("")){
//      		  CCBegin by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
        		  String message = "措施结果(S)为空";
//        	      CCEnd by liuzc 2009-12-21原因：解放升级,FMEA计算   参见TD号2685
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr22.equals("")){
//      		  CCBegin by liuzc 2009-11-21原因：解放升级,FMEA计算   参见TD号2685
        		  String message = "措施结果(O)为空";
//        	      CCEnd by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr33.equals("")){
//        	      CCEnd by liuzc 2009-12-21原因：解放升级,FMEA计算   参见TD号2685
        		  String message = "措施结果(D)为空";
//        	      CCEnd by liuzc 2009-12-21原因：解放升级,FMEA计算   参见TD号2685
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  }
        	  multiList.addTextCell(row, j4, "");
              
          }

	  }

  /**
   *  计算,此方法实现了计算功能,在实施过程中需要根据具体计算规则相应的修改此方法
   */
  private void calculate() {
    String firstClassi = container.getFirstClassify();
    if (firstClassi.equals("QMAssembleProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
    	  AssembleCalculate();
    	  }
    	  catch(NumberFormatException ex){
              String message = "你输入的需要计算的数据不是整形";
              String title = QMMessage.getLocalizedMessage(RESOURCE,
                      "information", null);
              JOptionPane.showMessageDialog(parentJFrame,
                                            message,
                                            title,
                                            JOptionPane.INFORMATION_MESSAGE);
              return;
              }
      }
    }
    else
    if (firstClassi.equals("QMPMProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMPushProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMWeldProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMGelaProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMVitrWashProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMDrillProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMMachineProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

    else
    if (firstClassi.equals("QMPaintProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

    else
    if (firstClassi.equals("QMCheckProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

    else
    if (firstClassi.equals("QMCheckProcedure")) {
      if (container.getSecondClassifyValue().equals("过程FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "你输入的需要计算的数据不是整形";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

  }
  //CCBegin by leixiao 2008-10-27 原因：解放系统升级  
  /**
   *  过程流程的属性带到控制计划中
   * 根据扩展属性名,获得业务对象的扩展属性值
   * @param extendattriedifc 可使用扩展属性的业务对象
   * @param attname 扩展属性名称
   * @return Object 指定扩展属性的属性值(对于成组的属性,返回主要数据值组的数据值)
   */
  private Vector getExtendAttValue(ExtendAttContainer container,
                                   String attname) {
    Vector obj = null;
    ExtendAttModel model = null;
    //获得业务对象的扩展属性容器
    if (container == null) {
      return null;
    }
    //从容器中获得指定的属性模型
    model = container.findExtendAttModel(attname);
    //如果非成组属性没有
    if (model == null) {
      //判断容器是否有成组属性
      if (container.isGroup()) {
        ExtendAttGroup group = null;
        Iterator names = container.getAttGroupNames().iterator();
        Vector vec = new Vector();
        Vector groups;
        while (names.hasNext()) {
          String name = (String) names.next();
          groups = container.getAttGroups(name);
          if (groups != null) {
            for (int i = 0; i < groups.size(); i++) {
              group = (ExtendAttGroup) groups.elementAt(i);
              model = group.findExtendAttModel(attname);
              if (model != null) {
                vec.add(model.getAttValue());
              }
            } //end for
          }
        }
        if (vec.size() > 0) {
          obj = vec;
        }
      } //end if

      return obj;
    } //end if
    return obj;
  }
 // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
}
