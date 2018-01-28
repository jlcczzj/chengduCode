/** 生成程序PartUsageTechLinkJPanel.java	1.1  2003/08/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 成都添加从结构和工艺添加件  guoxiaoliang 2016-7-14
 * SS2 A004-2016-3437 对相同版本的零件增加只允许创建一个同类型工艺的限制 liunan 2017-1-13
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.beans.cappassociationspanel.PartListPanel;
import com.faw_qm.cappclients.capp.controller.PartUsageTechLinkController;
import com.faw_qm.cappclients.capp.controller.PartUseTechDelegate;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;


/**
 * <p>Title:零部件使用工艺卡的关联维护面板 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 */

public class PartUsageTechLinkJPanel extends ParentJPanel
{
    /***
     * 关联bean
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    
    //CCBegin SS2
    private CodingIfc techTypeIfc;
    //CCEnd SS2
    
//CCBegin SS1
    
    private PartListPanel partListPanel = new  PartListPanel();
    String techtype = "";
    /**
     * 列表中选中得行
     */
    int row = -1;

    /**
     * 列表中选中得列
     */
    int col = -1;
    
//CCEnd SS1
    /**
     * 控制类
     */
    private PartUsageTechLinkController controller;


    /**
     * 代理类
     */
    private PartUseTechDelegate delegate;


    /**
     * 构造方法
     * @param technicsType String 工艺种类
     */
    public PartUsageTechLinkJPanel(String technicsType)
    {
    	//CCBegin SS1
    	 techtype = technicsType;
    	 //CCEnd SS1
    	
        delegate = PartUseTechDelegate.getDelegate();
        try
        {
            controller = delegate.getController(technicsType);
        }
        catch (QMException ex1)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex1.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        System.out.println("controller=========11111111111=========="+controller);
        
        if (controller != null)
        {
            controller.setLinkPanel(this);

            try
            {
                jbInit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

//  CCBeginby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成 
    public PartUsageTechLinkJPanel(Component parent,String technicsType)
    {
        delegate = PartUseTechDelegate.getDelegate();
        try
        {
            controller = delegate.getController(technicsType);
        }  
        catch (QMException ex1)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex1.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }   
        System.out.println("controller=========22222222222=========="+controller);
        
        if (controller != null)
        {
            controller.setLinkPanel(this,parent);

            try
            {
            	 System.out.println("controller=========3333333333333=========="+controller);
                jbInit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    	
    }
//  CCEndby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成

    /**
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
    	//CCBegin SS1
    	 

	        
	        //cappAssociationsPanel.setIsStruct(true, 3);
	        //cappAssociationsPanel.setIsRouteList(true, 4);
	        cappAssociationsPanel.setIsInsert(true, 5);
	        
	        partListPanel.setStructButtonMnemonic('S');
	        partListPanel.setRouteListButtonMnemonic('R');
	        partListPanel.addActionListener(new java.awt.event.
	                                                ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	            cappAssociationsPanel_actionPerformed(e);
	          }
	        });
	        
	  //设置使用数量列可以编辑
	        if(techtype.equals("软化件工艺（裁剪）")||techtype.equals("软化件工艺（注塑、吸塑、发泡）")){
	          int[] is = {
	                0, 3, 4, 5,6};
	          partListPanel.setColsEnabled(is, true);

	        }
	        else{
	          int[] is = {
	              0, 3, 4, 5};
	          partListPanel.setColsEnabled(is, true);
	        }

	      
    	 
    	 
    	 //CCEnd SS1
    	
        setLayout(gridBagLayout1);
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));

    }
//CCBegin SS1
    
    /**
     * 设置工艺种类
     * @param info
     */
    public void setCoding(CodingIfc info) {
  	  partListPanel.setTechTypeCodingIfc(info);
  	  //CCBegin SS2
  	  techTypeIfc = info;
  	  //CCEnd SS2
    }
    
//CCEnd SS1

    /**
     * 设置界面显示模式
     * @param m  界面显示模式(编辑模式、查看模式)  EDIT_MODE or VIEW_MODE
     */
    public void setMode(String m)
    {
        cappAssociationsPanel.setMode(m);
    }


    /**
     * 设置当前业务对象
     * @param info 工艺卡值对象
     */
    public void setTechnics(QMFawTechnicsInfo info)
    {
        cappAssociationsPanel.setObject(info);
        //CCBegin SS2
        cappAssociationsPanel.setTechTypeCodingIfc(techTypeIfc);
        //CCEnd SS2
    }

    public void setLinks(Collection links)
    {
        cappAssociationsPanel.setLinks(links);
    }


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
//      CCBeginby leixiao 2009-9-14 原因：获取材料定额
        cappAssociationsPanel.setAssociatpart(null);
//      CCEndby leixiao 2009-9-14 原因：获取材料定额
        cappAssociationsPanel.clear();
        controller.clear();
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
//CCBegin SS1
    
    public void cappAssociationsPanel_actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String actionCommand = e.getActionCommand();
        int t = actionCommand.indexOf(";");
        if (t != -1) {
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
        //add by ll in 20080116 ------start
        //if (col == 0 && stepType.equals("装配工序"))
        if (col == 0) {
          if (obj instanceof JTextField) {
        	  partListPanel.setEnabled(false);
            JTextField textField = (JTextField) obj;
            if (textField.getText().trim() != null &&
                !textField.getText().trim().equals("")) {
              handworkAddPartNumber(textField);
            }
            else {
            	partListPanel.setEnabled(true);
            }
          }
        }
        //add by ll in 20080116 ------end
      }
    
    
    private void handworkAddPartNumber(JTextField textField) {
        String number = textField.getText().trim();
        QMPartIfc part = checkPartIsExist(number);
        if (part != null) {
          try {
        	  partListPanel.addObjectToRow(part, row);
          }
          catch (InvocationTargetException ex) {
            ex.printStackTrace();
          }
          catch (ClassNotFoundException ex) {
            ex.printStackTrace();
          }
          catch (InstantiationException ex) {
            ex.printStackTrace();
          }
          catch (NoSuchMethodException ex) {
            ex.printStackTrace();
          }
          catch (IllegalAccessException ex) {
            ex.printStackTrace();
          }
          partListPanel.setEnabled(true);
        }
        else {
          JOptionPane.showMessageDialog(getParentJFrame(),
                                        "此零部件不存在，请重新输入",
                                        "提示", JOptionPane.WARNING_MESSAGE);
          partListPanel.setEnabled(true);
          partListPanel.undoCell();
        }
      }
    
    
    private QMPartIfc checkPartIsExist(String partNumber) {
        Class[] c = {
            String.class};
        Object[] obj = {
            partNumber};
        QMPartIfc part = null;
        try {
          part = (QMPartIfc) useServiceMethod(
              "TotalService", "getPartID", c, obj);
        }
        catch (QMRemoteException ex) {
          ex.printStackTrace();
        }
        return part;
      }
    
//CCEnd SS1

    /**
     * 添加监听者
     * @param e ActionListener
     */
    public void addListener(ActionListener e)
    {
        controller.addListener(e);
    }


    /**
     * 设置材料关联面板
     * @param materialPanel TechUsageMaterialLinkJPanel 材料关联面板
     */
    public void setMaterialLinkJPanel(TechUsageMaterialLinkJPanel materialPanel)
    {
        controller.setMaterialLinkJPanel(materialPanel);
    }


    /**
     * 获得关联bean
     * @return CappAssociationsPanel 关联bean
     */
    public CappAssociationsPanel getCappAssociationsPanel()
    {
        return cappAssociationsPanel;
    }

    /**
     * 获得关联bean
     * @return CappAssociationsPanel 关联bean
     */
    public PartListPanel getPartListPanel() {
      return partListPanel;
    }

}
