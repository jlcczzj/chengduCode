/**
 *   修改记录
 *   
 *   CR1 郭晓亮　2009/04/21   原因:工序关联的所有资源不能进行多选删除.
 *                            方案:将资源列表修改为可以选择多行数据,而且修改了删除按钮的
 *                                 逻辑.
 *                            备注:变更记录标记"CRSS-009"
 *                
 * SS1 变速箱可编辑列与解放不同 pante 20130729  
 * SS2 增加新功能工步中设备工装材料零件的数量是否累加到序 leixiao 2013-9-23      
 * SS3 工步中增加关联“累加到工序”并且不在工序中显示 leixiao 2013-10-14      
 * SS4 成都工序添加获得子件功能  guoxiaoliang 2016-7-28         
 * SS5 成都工步关联资源添加工序资源功能 guoxiaoliang 2016-8-2     
 * SS6 成都添加到工步功能  guoxiaoliang 2016-8-3  
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;


/**
 * <p>Title:工序使用零部件关联面板 </p>
 * <p>Description: 维护工序用零件</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 * 问题（1）20080728  xucy add  设置可编辑列数
 */

public class ProcedureUsagePartJPanel extends ParentJPanel
{
    /**
     * 关联面板
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**
     * 工序种类
     */
    private String stepType;


    /**
     * 界面模式
     */
    private String mode = "";


    /**
     * 使用数量列
     */
    private int useCountColum = -1;


    /**
     * 列表中选中得行
     */
    int row = -1;

    /**
     * 列表中选中得列
     */
    int col = -1;
    
    
    //CCBegin SS4
    private boolean isProcedure = false;
    private BaseValueIfc dikefTechnics;
    //CCEnd SS4

    
    //CCBegin SS5
    
    /**
     * 构造方法
     */
    public ProcedureUsagePartJPanel(boolean isProcedure)
    {
    	
    	
    	
      this.isProcedure = isProcedure;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //CCEnd SS5
    
    /**
     * 构造方法
     * @param stepType String 工序种类
     */
    public ProcedureUsagePartJPanel(String stepType)
    {
    	
        this.stepType = stepType;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
//CCBegin SS4
    
    /**
     * 构造方法
     * @param stepType String 工序种类
     */
    public ProcedureUsagePartJPanel(String stepType,BaseValueIfc base)
    {
       this(stepType,true,base);
    }
    
    public ProcedureUsagePartJPanel(String stepType,boolean isProcedure)
    {
      this(stepType,isProcedure,null);
    }
    
    
    /**
     * 构造方法
     * @param stepType String 工序种类
     */
    public ProcedureUsagePartJPanel(String stepType,boolean isProcedure,BaseValueIfc base)
    {
        this.stepType = stepType;
        this.isProcedure= isProcedure;
        this.dikefTechnics=base;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //CCEnd SS4

    /**
     * 界面初始化
     * @throws Exception
     * 问题（1）20080728  xucy add  设置可编辑列数
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
        //CCBegin by liuzc 2009.12.12 原因：增加上移
        cappAssociationsPanel.getMultiList().setAllowSorting(false);
        cappAssociationsPanel.setIsUpDown(true, 4, 5);
        //CCBegin SS6
        cappAssociationsPanel.setProcedureUsagePartJPanel(this);
        //CCEnd SS6
        
        
        //CCEnd by liuzc 2009.12.12 原因：增加上移
        //根据工序种类从资源文件得到字符串
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + stepType);
        if (allProperties.trim().equals("null") || allProperties.equals(""))
        {
            return;
        }
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));

        //localize();
        //设置角色名procedure
        cappAssociationsPanel.setRole("procedure");
        cappAssociationsPanel.setObject(null);
        //设置关联类名
        try
        {
            cappAssociationsPanel.setLinkClassName(
                    "com.faw_qm.capp.model.QMProcedureQMPartLinkInfo");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //设置关联业务对象类名
        try
        {
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.part.model.QMPartInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

        StringTokenizer stringToken = new StringTokenizer(allProperties, ";");
        //得到allProperties中第一个分号前的字符串，为零部件的属性
        String partProperties = stringToken.nextToken();
        //得到allProperties中第一个分号后的字符串，为关联属性
        String linkProperties = stringToken.nextToken();
        System.out.println("linkProperties========"+linkProperties);
        //关联是否有扩展属性
        boolean flag = stringToken.nextToken().equals("true");
        //标题
        String labels = stringToken.nextToken();
        
       
        StringTokenizer partToken = new StringTokenizer(partProperties, ",");
        StringTokenizer linkToken = new StringTokenizer(linkProperties, ",");
        if (flag)
        {
            cappAssociationsPanel.setSecondTypeValue(stepType);
        }
        StringTokenizer LabelToken = new StringTokenizer(labels, ",");
        
        String s = null;
        //把零部件属性放入partProperties[]中
        int partL = partToken.countTokens();
        String[] part = new String[partL];
        for (int i = 0; i < partL; i++)
        {
            part[i] = partToken.nextToken();
        }
        //把关联属性放入linkProperties[]中
        int linkL = linkToken.countTokens();
        String[] link = new String[linkL];
        for (int i = 0; i < linkL; i++)
        {
            link[i] = linkToken.nextToken();
            if (link[i].equals("usageCount"))
            {
                useCountColum = partL + i;
            }
            if (verbose)
            {
                System.out.println("useCount=" + useCountColum);
            }
        }
        
//        System.out.println("link========"+link.length+"    "+link[0]+"      "+link[1]);
        //标题
        int labelsL = LabelToken.countTokens();
        String[] label = new String[labelsL];
        for (int i = 0; i < labelsL; i++)
        {
            label[i] = LabelToken.nextToken();

            //设置将显示在多列列表中关联的业务对象的属性集
        }
        System.out.println("label========"+label.length+"    "+label[0]+"      "+label[1]);
        cappAssociationsPanel.setOtherSideAttributes(part);

        try
        {
            cappAssociationsPanel.setMultiListLinkAttributes(link);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        try
        { //标题
            cappAssociationsPanel.setLabels(label);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //设置是否只保存更改过的
        cappAssociationsPanel.setSaveUpdatesOnly(true);
        //将AssociationsPanel下方的AttributeForm部分删除
        cappAssociationsPanel.removeAttributeForm();
        //设置只获得零部件的每个大版本的最新版本
        cappAssociationsPanel.setLastIteration(true);     
        //SSBegin SS1
        if(this.stepType.contains("变速箱")){
        	 //问题（1）20080728  xucy add  设置可编辑列数  begin
            // Sysem.out.println("stringToken====="+stringToken);
             String editProperties = stringToken.nextToken();
             StringTokenizer editToken = new StringTokenizer(editProperties, ",");
             //System.out.println("editTokeneditToken====="+editToken);
             int editL = editToken.countTokens();
             //System.out.println("editLeditL====="+editL);
             int[] editCols = new int[editL];

             for (int i = 0; i < editL; i++)
             {
                 editCols[i] = Integer.parseInt(editToken.nextToken());
             }
             cappAssociationsPanel.setColsEnabled(editCols, true);
             //问题（1）20080728  xucy add  设置可编辑列数  endt
        }
        else{
        	cappAssociationsPanel.setMutliSelectedModel(true);//CR1

        	//设置使用数量列可以编辑
        	//CCBegin SS2
        	int[] is =
        		{partL,partL+1};
        	cappAssociationsPanel.setColsEnabled(is, true);
        	//CCEnd SS2
        }

      //SSEnd SS1
        //添加监听,目的是检查装配工序的使用数量是否超出值域
        cappAssociationsPanel.addActionListener(new java.awt.event.
                                                ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cappAssociationsPanel_actionPerformed(e);
            }
        });
        
        //CCBegin SS4
        
        if(isProcedure)
        {
        	
        	
          cappAssociationsPanel.setIsInsert(true, 3);
          cappAssociationsPanel.setIsStruct(true, 4);
          //added by dikef
          cappAssociationsPanel.setHasSon(true);
          cappAssociationsPanel.setLinkedTechnics(dikefTechnics);
          //added by dikef end
        }
        else{
        
        cappAssociationsPanel.setIsInsert(true, 3);
        cappAssociationsPanel.setIsStruct(true, 7);
        
        cappAssociationsPanel.setIsPartadd(true,8);
        cappAssociationsPanel.setIsGetResouceByProcedure(true,9);
        

        
       } 
        
        
        //CCEnd SS4
        
       
        
        
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
//      CCBegin by liuzhicheng 20010.01.12 原因：去掉添加按钮  参见TD=2800
//        cappAssociationsPanel.setInsertButtonMnemonic('I');
//      CCEnd by liuzhicheng 2009.01.12
        cappAssociationsPanel.setUpButtonMnemonic('U');
        cappAssociationsPanel.setDownButtonMnemonic('N');
    }


    /**
     * 获得界面模式
     * @return 界面显示模式(编辑模式、查看模式)  EDIT_MODE or VIEW_MODE
     */
    public String getMode()
    {
        return mode;
    }


    /**
     * 本地化
     */
    protected void localize()
    {
        //设置列表标题名
        String args1[] = new String[2];
        args1[0] = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
        args1[1] = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
        try
        {
            cappAssociationsPanel.setLabels(args1);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 设置界面显示模式
     * @param m  界面显示模式(编辑模式、查看模式)  EDIT_MODE or VIEW_MODE
     */
    public void setMode(String m)
    {
        cappAssociationsPanel.setMode(m);
        mode = m;
    }
    


    /**
     * 设置当前业务对象
     * @param info 工艺卡值对象
     */
    public void setProcedure(QMProcedureInfo info)
    {
        if (info.getBsoID() != null)
        {
            String classPath = RemoteProperty.getProperty("instance" +
                    info.getTechnicsType().getCodeContent());
            try
            {
                cappAssociationsPanel.setObjectClassName(classPath);
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        cappAssociationsPanel.setObject(info);

    }
    //CCBegin SS3
    public void setIsProcedure(boolean flag){
    	if(!this.stepType.contains("变速箱")){
    		cappAssociationsPanel.setIsProcedure(flag);
    	}
    }
//CCEnd SS3
    
    
    /**
     * 获得所有关联
     * @return 所有关联类对象的集合
     */
    public Vector getAllLinks()
            throws Exception
    {
        if (!cappAssociationsPanel.check())
        {
            throw new Exception("关联属性输入错误！");
        }
        else
        {
            return cappAssociationsPanel.getLinks();
        }
    }


    /**
     * 获得要被删除的关联
     * @return 关联类对象的集合
     */
    public Vector getDeletedLinks()
    {
        Vector delVector = new Vector();
        for (Enumeration e = cappAssociationsPanel.getRemoveLinks();
                             e.hasMoreElements(); )
        {
            delVector.addElement(e.nextElement());
        }
        return delVector;
    }

    public void clear()
    {
        cappAssociationsPanel.clear();
        useCountColum = -1;
    }


    /**
     * 添加零部件
     */
    public void addPartToTable(QMPartIfc part)
    {
        try
        {
            cappAssociationsPanel.addSelectedObject(part);
        }
        catch (InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch (NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 检查装配工序零部件使用数量
     * @param e
     */
    public void cappAssociationsPanel_actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();
        String actionCommand = e.getActionCommand();
        int t = actionCommand.indexOf(";");
        if (t != -1)
        {
            //得到行
            String rowString = actionCommand.substring(0, t);
            int t1 = rowString.indexOf(":");
            row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
            //得到列
            String colString = actionCommand.substring(t + 1,
                    actionCommand.length());
            int t2 = colString.indexOf(":");
            col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));

        }

        //如果选中的是使用数量列
        if (col == useCountColum
            && stepType.equals("装配工序"))
        {
            if (obj instanceof JTextField)
            {
                JTextField textField = (JTextField) obj;
                String number = textField.getText().trim();
                try
                {
                    int f = Integer.parseInt(number);
                    if (f > 99 || f < 0)
                    {
                        textField.setText("1");
                        cappAssociationsPanel.addToList(row, col, "1");
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.USECOUNT_INVALID, null);
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.USECOUNT_TOOLONG, null);
                        JOptionPane.showMessageDialog(getParentJFrame(),
                                message,
                                title, JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (NumberFormatException ex)
                {
                    cappAssociationsPanel.addToList(row, col, "1");
                    //提示使用数量类型不正确
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.USECOUNT_INVALID, null);
                    Object[] obj1 =
                            {"使用数量", "数字"};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "114", obj1);
                    JOptionPane.showMessageDialog(getParentJFrame(), message,
                                                  title,
                                                  JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }
    
    //CCBegin SS4
    
    public void setLinkedTechnics(BaseValueIfc base)
    {
      dikefTechnics=base;
    }
    //CCEnd SS4
    
    //CCBegin SS5
    public void setProcedurePace(QMProcedureIfc info){
    	   cappAssociationsPanel.setProcedurePace(info);
    	  }
    //CCEnd SS5
    
    //CCBegin SS6
    
    public void setTextValue(String s){
        tpjPanel.setTechnicsPaceText(s);
      }
    
    private TechnicsPaceJPanel tpjPanel = null;
    public void setTechnicsPaceJPanel(TechnicsPaceJPanel tpjPanel){
      this.tpjPanel = tpjPanel;
    }
    //CCEnd SS6

}
