/** 生成程序TechnicsContentJPanel.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2010/01/07 徐春英  原因：第一次进入合并工艺界面很慢
 * SS1 变速箱资源清单一览表 zhaoqiuying 2013-01-23
 * SS2 区分不同组织调用的工序工步界面  xudezheng 2013-02-17
 * SS3 轴齿所见即所得 guoxiaoliang 2014-03-20
 * SS4 增加轴齿中心工艺派生文件 pante 2014-02-19
 * SS5 变速箱查看不了工步。 liunan 2014-5-19
 * SS6 轴齿用户新需求,要求取消选择工序种类步骤 pante 2014-06-19
 * SS7 变速箱一览表显示版次。 liunan 2014-8-4
 * SS8 变速箱一览表由于历史数据没有每车数量引起的异常 liunan 2014-8-26
 * SS9 长特增加工装明细，设备清单，模具清单。 guoxiaoliang 2014-08-22
 * SS10 增加合并工艺功能 徐德政 2014-12-15
 * SS11 成都检查作业指导书工艺和工序更改维护界面 guoxiaoliang 2016-11-23
 * SS12 成都质量检查表工艺去掉，改为检查作业指导书工艺 guoxiaoliang 2016-11-13
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTypeObject;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.technics.view.ModelProcedureMasterJPanel;
import com.faw_qm.cappclients.technics.view.ModelTechMasterJPanel;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.cappclients.capp.util.*;

/** 
 * <p>Title: 工艺内容面板</p>
 * <p>用于工艺维护主界面。在本面板内，设置界面的显示内容：工艺卡、工序或工步</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 * 问题（1）20081226 xucy  修改原因：优化更新时保存工序
 */

public class TechnicsContentJPanel extends JPanel
{
	
	//add by guoxl(监听类)
	public static AddFocusListener addFocusLis=new AddFocusListener();
	//add end 
    /**工艺主信息面板*/
    private TechnicsMasterJPanel technicsMasterJPanel = null;
    //CCBegin SS11
    //成都质量检查表工艺维护面板
    private CdTechnicsMasterJPanel cdTechnicsMasterJPanel = null;
     //CCEnd SS11

    /**工序面板*/
    private TechnicsStepJPanel stepJPanel = null;


    /**工步面板*/
    private TechnicsPaceJPanel paceJPanel = null;


    /**典型工艺主信息面板*/
    private ModelTechMasterJPanel modelTechJPanel = null;


    /**典型工序主信息面板*/
    private ModelProcedureMasterJPanel modelStepJPanel = null;
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**合并工艺规程面板*/
    private UniteTechnicsReguJPanel uniteTechnicsJPanel = null;
    
//    CCBegin SS10
    /**长特合并工艺规程面板*/
    private UniteTechnicsReguJPanelForCT uniteTechnicsJPanelForCT = null;
//    CCEnd SS10


    /**界面显示模式（更新模式）标记*/
    public final static int UPDATE_MODE = 0;


    /**界面显示模式（创建模式）标记*/
    public final static int CREATE_MODE = 1;


    /**界面显示模式（查看模式）标记*/
    public final static int VIEW_MODE = 2;


    /**界面显示模式（另存为模式）标记*/
    public final static int SAVEAS_MODE = 3;


    /**所选择的树节点对象*/
    private BaseValueInfo selectedObject = null;


    /**父窗口*/
    private JFrame parentJFrame;


    /**所选择的工艺树节点*/
    private CappTreeNode selectedNode;


    /**工艺种类选择框*/
    private SelectTechnicsTypeJDialog technicsSelectDialog;
    private Hashtable stepTypetable = null;
    private CodingIfc technicsType;


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**用于标记资源文件路径*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private int mode;


    /**
     * 构造函数
     */
    public TechnicsContentJPanel(JFrame parent)
    {
        try
        {
            parentJFrame = parent;
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
    }


    /**
     * 设置所选择的节点
     * @param parent
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
    }


    /**
     * 获得调用本类的父窗口
     * @return 父窗口
     */
    public JFrame getParentJFrame()
    {
        return parentJFrame;
    }


    /**
     * 设置当前界面所选择的业务对象
     * @param obj 工艺树上的节点对象（工艺卡、工序或工步）
     */
    public void setSelectedObject(BaseValueInfo obj)
    {
        selectedObject = obj;
    }


    /**
     * 获得当前界面所选择的业务对象
     * @return 工艺树上的节点对象（工艺卡、工序或工步）
     */
    protected BaseValueInfo getSelectedObject()
    {
        return selectedObject;
    }


    /**
     * 设置显示工艺卡维护面板
     * @param optionMode  界面显示模式（创建、更新、查看、另存为）
     */
    public void setTechnicsMode(int optionMode)
    {
        //工艺种类
        CodingIfc technicsType = null;
        //问题（2）20090106 xucy 修改  修改原因：保存更新工序（工步）之后界面保持更新状态，再点击查看此节点，
    	//出现两次是否保存更新的工序提示框
        if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        //创建模式下,获得选择的工艺种类
        if (optionMode == CREATE_MODE)
        {
            //获得树上选择的工艺种类
            CappTreeObject obj = ((TechnicsRegulationsMainJFrame)
                                  parentJFrame).
                                 getSelectedObject();
            if (obj != null && obj instanceof ResourceTypeObject)
            {
                technicsType = (CodingIfc) ((ResourceTypeObject) obj).getObject();
                
                
            }
            //弹出选择框
            else
            {
                if (technicsSelectDialog == null)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.TECHNICSTYPE, null);
                    technicsSelectDialog = new SelectTechnicsTypeJDialog(
                            parentJFrame);
                }
                technicsSelectDialog.setVisible(true);
                CodingIfc type = technicsSelectDialog.getCoding();
                if (type == null)
                {
                    return;
                }
                else
                {
                    technicsType = type;
                }
            }
          
        }
        
        //CCBegin SS12

      	if(technicsType!=null&&technicsType.getCodeContent().equals("成都检查作业指导书工艺"))
        {
      		    
      		    setTechnicsModeForCd(1,technicsType);
          		return;
          		
        }
       else {
    	   //CCEnd SS12
        //实例化面板
        if (technicsMasterJPanel == null)
        {
        	//CCBegin SS11
        	if(cdTechnicsMasterJPanel!=null){
        		
        		cdTechnicsMasterJPanel.setVisible(false);
        	}
        	//CCEnd SS11
            technicsMasterJPanel = new TechnicsMasterJPanel(parentJFrame);
            add(technicsMasterJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
            
        }
        //CCBegin SS11
        else{
        	
       	 if(cdTechnicsMasterJPanel!=null){
        		
       		   cdTechnicsMasterJPanel.setVisible(false);
        	}
       }
        //CCEnd SS11
        

        mode = optionMode;
        //此段目的是避免界面重叠,不是好办发
        if (optionMode == 2)
        {
            setNullMode();
            //如果选择了节点，则为更新、查看、另存为工艺界面；否则为新建工艺界面
        }
        try
        {
            if (optionMode != CREATE_MODE)
            {
                QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
                technicsMasterJPanel.setTechnics((QMFawTechnicsInfo) info);
                technicsMasterJPanel.setSelectedNode(selectedNode);
            }
            //创建模式,设置工艺种类
            else
            {
                technicsMasterJPanel.setTechnicsType(technicsType);
            }
            technicsMasterJPanel.setViewMode(optionMode);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //此段的目的是避免界面重叠
        if (optionMode != 1)
        {
            if (selectedObject instanceof QMTechnicsInfo)
            {
                technicsMasterJPanel.setVisible(true);
            }
        }
        else
        {
            technicsMasterJPanel.setVisible(true);
        }
        technicsMasterJPanel.validate();
        technicsMasterJPanel.updateUI();
        technicsMasterJPanel.requestFocus();
        refresh();
    
       
       }
    }
    //CCBegin SS12
    /**
     * 设置显示工艺卡维护面板
     * @param optionMode  界面显示模式（创建、更新、查看、另存为）
     */
    public void setTechnicsModeForCd(int optionMode,CodingIfc technicsType)
    {
    	
        //工艺种类
//        CodingIfc technicsType = null;
        //问题（2）20090106 xucy 修改  修改原因：保存更新工序（工步）之后界面保持更新状态，再点击查看此节点，
    	//出现两次是否保存更新的工序提示框
        if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        //创建模式下,获得选择的工艺种类
        if (optionMode == CREATE_MODE)
        {
            //获得树上选择的工艺种类
//            CappTreeObject obj = ((TechnicsRegulationsMainJFrame)
//                                  parentJFrame).
//                                 getSelectedObject();
//            if (obj != null && obj instanceof ResourceTypeObject)
//            {
//                technicsType = (CodingIfc) ((ResourceTypeObject) obj).getObject();
//            }
            //弹出选择框
//            else
//            {
//                if (technicsSelectDialog == null)
//                {
//                    String title = QMMessage.getLocalizedMessage(RESOURCE,
//                            CappLMRB.TECHNICSTYPE, null);
//                    technicsSelectDialog = new SelectTechnicsTypeJDialog(
//                            parentJFrame);
//                }
//                technicsSelectDialog.setVisible(true);
//                CodingIfc type = technicsSelectDialog.getCoding();
//                if (type == null)
//                {
//                    return;
//                }
//                else
//                {
//                    technicsType = type;
//                }
//            }
            
            
           
        }
        //实例化面板
        if (cdTechnicsMasterJPanel == null)
        {
            if(technicsMasterJPanel!=null){
        		
            	technicsMasterJPanel.setVisible(false);
        	}
        	cdTechnicsMasterJPanel = new CdTechnicsMasterJPanel(parentJFrame);
            add(cdTechnicsMasterJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }else{
        	
        	 if(technicsMasterJPanel!=null){
         		
             	technicsMasterJPanel.setVisible(false);
         	}
        }
        
        

        mode = optionMode;
        //此段目的是避免界面重叠,不是好办发
        if (optionMode == 2)
        {
            setNullMode();
            //如果选择了节点，则为更新、查看、另存为工艺界面；否则为新建工艺界面
        }
        try
        {
            if (optionMode != CREATE_MODE)
            {
                QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
                cdTechnicsMasterJPanel.setTechnics((QMFawTechnicsInfo) info);
                cdTechnicsMasterJPanel.setSelectedNode(selectedNode);
            }
            //创建模式,设置工艺种类
            else
            {
            	cdTechnicsMasterJPanel.setTechnicsType(technicsType);
            }
            cdTechnicsMasterJPanel.setViewMode(optionMode);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //此段的目的是避免界面重叠
        if (optionMode != 1)
        {
            if (selectedObject instanceof QMTechnicsInfo)
            {
            	cdTechnicsMasterJPanel.setVisible(true);
            }
        }
        else
        {
        	cdTechnicsMasterJPanel.setVisible(true);
        }
        cdTechnicsMasterJPanel.validate();
        cdTechnicsMasterJPanel.updateUI();
        cdTechnicsMasterJPanel.requestFocus();
        refresh();
    }
    
    
    /**
     * 设置显示工艺卡维护面板
     * @param optionMode  界面显示模式（创建、更新、查看、另存为）
     */
    public void setTechnicsModeForCd(int optionMode)
    {
    	
        //工艺种类
        CodingIfc technicsType = null;
        //问题（2）20090106 xucy 修改  修改原因：保存更新工序（工步）之后界面保持更新状态，再点击查看此节点，
    	//出现两次是否保存更新的工序提示框
        if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        //创建模式下,获得选择的工艺种类
        if (optionMode == CREATE_MODE)
        {
            //获得树上选择的工艺种类
            CappTreeObject obj = ((TechnicsRegulationsMainJFrame)
                                  parentJFrame).
                                 getSelectedObject();
            if (obj != null && obj instanceof ResourceTypeObject)
            {
                technicsType = (CodingIfc) ((ResourceTypeObject) obj).getObject();
            }
            //弹出选择框
            else
            {
                if (technicsSelectDialog == null)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.TECHNICSTYPE, null);
                    technicsSelectDialog = new SelectTechnicsTypeJDialog(
                            parentJFrame);
                }
                technicsSelectDialog.setVisible(true);
                CodingIfc type = technicsSelectDialog.getCoding();
                if (type == null)
                {
                    return;
                }
                else
                {
                    technicsType = type;
                }
            }
            
            
           
        }
        //实例化面板
        if (cdTechnicsMasterJPanel == null)
        {
            if(technicsMasterJPanel!=null){
        		
            	technicsMasterJPanel.setVisible(false);
        	}
        	cdTechnicsMasterJPanel = new CdTechnicsMasterJPanel(parentJFrame);
            add(cdTechnicsMasterJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }else{
        	
        	 if(technicsMasterJPanel!=null){
         		
             	technicsMasterJPanel.setVisible(false);
         	}
        }
        
        

        mode = optionMode;
        //此段目的是避免界面重叠,不是好办发
        if (optionMode == 2)
        {
            setNullMode();
            //如果选择了节点，则为更新、查看、另存为工艺界面；否则为新建工艺界面
        }
        try
        {
            if (optionMode != CREATE_MODE)
            {
                QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
                cdTechnicsMasterJPanel.setTechnics((QMFawTechnicsInfo) info);
                cdTechnicsMasterJPanel.setSelectedNode(selectedNode);
            }
            //创建模式,设置工艺种类
            else
            {
            	cdTechnicsMasterJPanel.setTechnicsType(technicsType);
            }
            cdTechnicsMasterJPanel.setViewMode(optionMode);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //此段的目的是避免界面重叠
        if (optionMode != 1)
        {
            if (selectedObject instanceof QMTechnicsInfo)
            {
            	cdTechnicsMasterJPanel.setVisible(true);
            }
        }
        else
        {
        	cdTechnicsMasterJPanel.setVisible(true);
        }
        cdTechnicsMasterJPanel.validate();
        cdTechnicsMasterJPanel.updateUI();
        cdTechnicsMasterJPanel.requestFocus();
        refresh();
    }
    
  //CCEnd SS12
    
    
/**
 * 生成派生文件
 * @param type
 * 
 * void
 * @throws Exception 
 */
    public boolean schedule(Vector type) throws Exception
    {
    //CCBegin SS9
    	if(getUserFromCompany().equals("ct")){
    		
    		if((selectedObject != null) && (selectedObject instanceof QMFawTechnicsInfo))
    		{
    			String temp = "";
    			HashMap map = new HashMap();
    			QMFawTechnicsInfo techInfo = (QMFawTechnicsInfo)selectedObject;
    			
    			if(techInfo.getWorkShop() != null)
    			{
    				if(techInfo.getWorkShop() instanceof CodingClassificationInfo)
    					temp = ((CodingClassificationInfo)techInfo.getWorkShop()).getCodeSort();
    				else if(techInfo.getWorkShop() instanceof CodingInfo)
    					temp = ((CodingInfo)techInfo.getWorkShop()).getCodeContent();
    				map.put("车间", temp);
    			}else
    			{
    				map.put("车间", "");
    			}
    			map.put("工艺编号", techInfo.getTechnicsNumber());
    			
    			temp=techInfo.getTechnicsType().getCodeContent();
    			map.put("种类", temp);
    			
    			temp=techInfo.getVersionValue();
    			map.put("版次", temp);
    			
    			if(techInfo.getExtendAttributes().findExtendAttModel("teamProductLine").getAttValue()!=null)
    			{
    				temp = techInfo.getExtendAttributes().findExtendAttModel("teamProductLine").getAttValue().toString();
    				map.put("生产线", temp);
    			}
    			
    			if(technicsMasterJPanel!=null)
    			{
    				PartUsageTechLinkJPanel partLink = technicsMasterJPanel.getPartPanel();
    				Vector vec = partLink.getAllLinks();
    				BinaryLinkIfc link;
    				PartUsageQMTechnicsLinkInfo partLinkInfo;
    				if(vec!=null&&vec.size()>0)
    				{
    					for(int i=0;i<vec.size();i++)
    					{
    						link = (BinaryLinkIfc)vec.get(i);
    						if(link instanceof PartUsageQMTechnicsLinkInfo)
    						{
    							partLinkInfo = (PartUsageQMTechnicsLinkInfo)link;
    							if(partLinkInfo.getMajorpartMark())
    							{
    								String partBsoid = partLinkInfo.getLeftBsoID();
    								QMPartInfo partInfo = (QMPartInfo)CappClientHelper.refresh(partBsoid);
    								if(partInfo!=null)
    								{
    									map.put("零件编号", partInfo.getPartNumber());
    									map.put("零件名称", partInfo.getPartName());                                   
    								}
    							}
    						}
    					}
    				}
    				
    				TechUsageMaterialLinkJPanel materLink = technicsMasterJPanel.getMaterialPanel();
    				Vector materVec=materLink.getAllLinks();
    				QMTechnicsQMMaterialLinkInfo materialLinkInfo;
    				
    				for(int j=0;j<materVec.size();j++){
    					link = (BinaryLinkIfc)materVec.get(j);
    					
    					if(link instanceof QMTechnicsQMMaterialLinkInfo)
						{
    						materialLinkInfo = (QMTechnicsQMMaterialLinkInfo)link;
    						
    						if(materialLinkInfo.getMmMark()){
    							
    							
    							QMMaterialInfo materInfo = (QMMaterialInfo)CappClientHelper.refresh(materialLinkInfo.getRightBsoID());
    							if(materInfo!=null){
    								
    								map.put("材料编号", materInfo.getMaterialNumber());
    								map.put("材料名称", materInfo.getMaterialName());
    							}
    						}
    						
						}
    				}
    				
    			}
    			
    			
    			Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
    			int year = c.get(Calendar.YEAR); 
    			int month = c.get(Calendar.MONTH)+1; 
    			int date = c.get(Calendar.DATE); 
    		    temp=String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(date);
    		    map.put("发放日期",temp);
    		    
    		    int size = type.size();
    			String[] arry = new String[size];
    			for(int i=0;i<size;i++)
    			{
    				arry[i] = (String)type.get(i);
    			}
    			return CappClientHelper.createExcel(techInfo.getBsoID(),arry,map);
    		    
    		}else
    		{
    			JOptionPane.showMessageDialog(null, "请选择工艺！", "",
    					JOptionPane.INFORMATION_MESSAGE);
    			return false;
    		}
    		
    	}
    	//CCEnd SS9
    	//CCBegin SS4
    	if(getUserFromCompany().equals("zczx")){
    		if((selectedObject != null) && (selectedObject instanceof QMFawTechnicsInfo))
    		{
    			HashMap map = new HashMap();
    			QMFawTechnicsInfo techInfo = (QMFawTechnicsInfo)selectedObject;
    			String temp = "";
    			temp = techInfo.getTechnicsNumber();
    			map.put("工艺编号", temp);
    			temp = techInfo.getTechnicsName();
    			map.put("工艺名称", temp);
    			if(techInfo.getWorkShop() != null)
    			{
    				if(techInfo.getWorkShop() instanceof CodingClassificationInfo)
    					temp = ((CodingClassificationInfo)techInfo.getWorkShop()).getCodeSort();
    				else if(techInfo.getWorkShop() instanceof CodingInfo)
    					temp = ((CodingInfo)techInfo.getWorkShop()).getCodeContent();
    				map.put("车间", temp);
    			}else
    			{
    				map.put("车间", "");
    			}
    			if(technicsMasterJPanel!=null)
    			{
    				PartUsageTechLinkJPanel partLink = technicsMasterJPanel.getPartPanel();
    				Vector vec = partLink.getAllLinks();
    				BinaryLinkIfc link;
    				PartUsageQMTechnicsLinkInfo partLinkInfo;
    				if(vec!=null&&vec.size()>0)
    				{
    					for(int i=0;i<vec.size();i++)
    					{
    						link = (BinaryLinkIfc)vec.get(i);
    						if(link instanceof PartUsageQMTechnicsLinkInfo)
    						{
    							partLinkInfo = (PartUsageQMTechnicsLinkInfo)link;
    							if(partLinkInfo.getMajorpartMark())
    							{
    								String partBsoid = partLinkInfo.getLeftBsoID();
    								QMPartInfo partInfo = (QMPartInfo)CappClientHelper.refresh(partBsoid);
    								if(partInfo!=null)
    								{
    									map.put("零件图号", partInfo.getPartNumber());
    									map.put("零件名称", partInfo.getPartName());   
    								}
    							}
    						}
    					}
    				}
    			}
    			if(techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile")!=null)
    			{
    				temp = techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile").getAttValue().toString();
    				map.put("每车数量", temp);
    			}
    			temp = techInfo.getVersionValue();
    			map.put("版次", temp);
    			temp = CappClientHelper.getDate(techInfo.getCreateTime());
    			map.put("编制日期", temp);
    			temp = techInfo.getSeparableNumber();
    			map.put("总成型号", temp);
    			int size = type.size();
    			String[] arry = new String[size];
    			for(int i=0;i<size;i++)
    			{
    				arry[i] = (String)type.get(i);
    			}
    			//                    String[] arry = {type};
    			return CappClientHelper.createExcel(techInfo.getBsoID(),arry,map);
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null, "请选择工艺！", "",
    					JOptionPane.INFORMATION_MESSAGE);
    			return false;
    		}
    	}
    	else{
    		//CCEnd SS4
    		if((selectedObject != null) && (selectedObject instanceof QMFawTechnicsInfo))
    		{
    			HashMap map = new HashMap();
    			QMFawTechnicsInfo techInfo = (QMFawTechnicsInfo)selectedObject;
    			String temp = "";
    			temp = techInfo.getFemaNum();
    			map.put("产品平台", temp);
    			if(techInfo.getWorkShop() != null)
    			{
    				if(techInfo.getWorkShop() instanceof CodingClassificationInfo)
    					temp = ((CodingClassificationInfo)techInfo.getWorkShop()).getCodeSort();
    				else if(techInfo.getWorkShop() instanceof CodingInfo)
    					temp = ((CodingInfo)techInfo.getWorkShop()).getCodeContent();
    				map.put("车间", temp);
    			}else
    			{
    				map.put("车间", "");
    			}
    			//CCBegin SS8
    			//if(techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile")!=null)
    			if(techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile")!=null&&techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile").getAttValue()!=null)
    			//CCEnd SS8
    			{
    				temp = techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile").getAttValue().toString();
    				map.put("每车数量", temp);
    			}
    			if(technicsMasterJPanel!=null)
    			{
    				PartUsageTechLinkJPanel partLink = technicsMasterJPanel.getPartPanel();
    				Vector vec = partLink.getAllLinks();
    				BinaryLinkIfc link;
    				PartUsageQMTechnicsLinkInfo partLinkInfo;
    				if(vec!=null&&vec.size()>0)
    				{
    					for(int i=0;i<vec.size();i++)
    					{
    						link = (BinaryLinkIfc)vec.get(i);
    						if(link instanceof PartUsageQMTechnicsLinkInfo)
    						{
    							partLinkInfo = (PartUsageQMTechnicsLinkInfo)link;
    							if(partLinkInfo.getMajorpartMark())
    							{
    								String partBsoid = partLinkInfo.getLeftBsoID();
    								QMPartInfo partInfo = (QMPartInfo)CappClientHelper.refresh(partBsoid);
    								if(partInfo!=null)
    								{
    									map.put("零件号", partInfo.getPartNumber());
    									map.put("零件名称", partInfo.getPartName());                                   
    								}
    							}
    						}
    					}
    				}
    			}
    			
    			//CCBegin SS7
    			temp = techInfo.getVersionValue();
    			map.put("版次", temp);
    			//CCEnd SS7
    			
    			int size = type.size();
    			String[] arry = new String[size];
    			for(int i=0;i<size;i++)
    			{
    				arry[i] = (String)type.get(i);
    			}
    			//                String[] arry = {type};
    			return CappClientHelper.createExcel(techInfo.getBsoID(),arry,map);
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null, "请选择工艺！", "",
    					JOptionPane.INFORMATION_MESSAGE);
    			return false;
    		}
    	}
    }
  //CCEnd SS1
    /**
     * 设置显示工序维护面板
     * @param optionMode  界面显示模式（创建、更新、查看）
     */
    public void setStepMode(int optionMode)
    {
    	stepJPanel = null;
    	//问题（2）20081230 xucy 修改  修改原因：保存更新工序（工步）之后界面保持更新状态，再点击查看此节点，
    	//出现两次是否保存更新的工序提示框
    	if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        String stepType = null;
        if (optionMode == CREATE_MODE)
        {
            if (stepTypetable == null)
            {
                stepTypetable = new Hashtable();
            }
            String type = ((QMFawTechnicsInfo) selectedObject).
                          getTechnicsType().
                          getCodeContent();
            //CCBegin SS6
            if(!type.contains("轴齿")){
            	//CCEnd SS6
            SelectStepTypeJDialog procedureSelectDialog;
            procedureSelectDialog = (SelectStepTypeJDialog) stepTypetable.
                                    get(type);
            if (procedureSelectDialog == null)
            {
                procedureSelectDialog = new SelectStepTypeJDialog(
                        parentJFrame, type);
                stepTypetable.put(type, procedureSelectDialog);
            }
            procedureSelectDialog.setVisible(true);
            stepType = procedureSelectDialog.getCodeContent();
            if (stepType == null)
            {
                return;
            }

            //CCBegin SS6
            }
            else{
            	stepType = "轴齿工序";
            }
          //CCEnd SS6
        }
        if (stepJPanel == null)
        {
//        	CCBegin SS2
        	if (selectedObject != null)
            {
                //如果选择了工序节点，则为更新、查看工序界面
        		if(stepType == null)
                if (selectedObject instanceof QMProcedureInfo)
                {
                	QMProcedureInfo info = (QMProcedureInfo) selectedObject;
                	stepType = info.getTechnicsType().getCodeContent();
                }else if(selectedObject instanceof QMFawTechnicsInfo){
                	QMFawTechnicsInfo ti = (QMFawTechnicsInfo) selectedObject;
                	stepType = ti.getTechnicsType().getCodeContent();
                }
                	if(stepType.contains("变速箱")){
                		System.out.println("变速箱");
                		stepJPanel = new TechnicsStepJPanelForBSX(parentJFrame);
                	}
                	//CCBegin SS3
                	
                	else if(stepType.contains("轴齿")){
                		System.out.println("轴齿");
                		stepJPanel = new TechnicsStepJPanelForZC(parentJFrame);
                	}
                	
                	//CCEnd SS3
                	
                	//CCBegin SS11
                	else if(stepType.contains("成都检查作业指导书工序")){
                		System.out.println("成都检查作业指导书工序");
                		stepJPanel = new CdTechnicsStepJPanel(parentJFrame);
                	}
                	//CCEnd SS11
                	
             	
                	
                	else{
                		System.out.println("解放");
                		stepJPanel = new TechnicsStepJPanel(parentJFrame);
                	}
//                }
//                //如果选择了工艺节点，则为新建工序界面
//                else if (selectedObject instanceof QMFawTechnicsInfo)
//                {
//                	
//                }
            }
//           CCEnd SS2
            add(stepJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        mode = optionMode;
        if (optionMode == 2)
        {
            setNullMode();
        }
        if (selectedObject != null)
        {
            //如果选择了工序节点，则为更新、查看工序界面
            if (selectedObject instanceof QMProcedureInfo)
            {
            	
                QMProcedureInfo info = (QMProcedureInfo) selectedObject;
                
                if (info.getIsProcedure())
                {
                    stepJPanel.setProcedure(info);
                    stepJPanel.setSelectedNode(selectedNode);
                    try
                    {
                        stepJPanel.setViewMode(optionMode);
                    }
                    catch (PropertyVetoException ex)
                    {
                        ex.printStackTrace();
                    }
                    if (selectedObject instanceof QMProcedureInfo && (
                            (QMProcedureInfo) selectedObject).getIsProcedure())
                    {
                        stepJPanel.setVisible(true);
                    }
                }
            }
            //如果选择了工艺节点，则为新建工序界面
            else if (selectedObject instanceof QMFawTechnicsInfo)
            {
                stepJPanel.setStepType(stepType);
                stepJPanel.setSelectedNode(selectedNode);
                try
                {
                    stepJPanel.setViewMode(optionMode);
                }
                catch (PropertyVetoException ex)
                {
                    ex.printStackTrace();
                }
                if (selectedObject instanceof QMFawTechnicsInfo)
                {
                    stepJPanel.setVisible(true);

                }
            }

        }
        validate();
        stepJPanel.requestFocus();
        refresh();

    }


    /**
     * 设置显示工步维护面板
     * @param optionMode 界面显示模式（创建、更新、查看）
     */
    public void setPaceMode(int optionMode)
    {
    	//问题（2）20090106 xucy 修改  修改原因：保存更新工序（工步）之后界面保持更新状态，再点击查看此节点，
    	//出现两次是否保存更新的工序提示框
    	if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        if (paceJPanel == null)
        {
//        CCBegin SS2
        	String type = ((QMProcedureInfo) selectedObject).
            getTechnicsType().
            getCodeContent();
        	if(type.contains("变速箱")){
        		paceJPanel = new TechnicsPaceJPanelForBSX(parentJFrame);
        	}
        	//CCBegin SS3
        	//CCBegin SS5
        	//if(type.contains("轴齿")){
        	else if(type.contains("轴齿")){
        	//CCEnd SS5
        		paceJPanel = new TechnicsPaceJPanelForZC(parentJFrame);
        	}
        	//CCEnd SS3
        	else{
            paceJPanel = new TechnicsPaceJPanel(parentJFrame);
        	}
//        CCEnd	SS2
            add(paceJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));

        }

        mode = optionMode;
        if (optionMode == 2)
        {
            setNullMode();
        }
        if (selectedObject != null)
        {
            QMProcedureInfo info = (QMProcedureInfo) selectedObject;
            if (optionMode != 1)
            {
                paceJPanel.setProcedure(info);
            }
            paceJPanel.setSelectedNode(selectedNode);
            try
            {
                paceJPanel.setViewMode(optionMode);
            }
            catch (PropertyVetoException ex)
            {
                ex.printStackTrace();
                return;
            }
            if (optionMode != 1)
            {
                if (selectedObject instanceof QMProcedureInfo && !
                    ((QMProcedureInfo) selectedObject).getIsProcedure())
                {
                    paceJPanel.setVisible(true);
                }
            }
            else
            {
                paceJPanel.setVisible(true);
            }
        }

        refresh();
        validate();
    }


    /**
     * 设置显示典型工艺主信息面板
     * 本方法用于生成典型工艺操作
     */
    public void setModelTechMode()
    {

        if (modelTechJPanel == null)
        {
            modelTechJPanel = new ModelTechMasterJPanel(parentJFrame);
            add(modelTechJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        if (selectedObject != null)
        {
            //创建一个与选定工艺规程内容相同的典型工艺。相当于对工艺规程的复制操作
            QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
            modelTechJPanel.setQMTechnics(info);
            try
            {
                modelTechJPanel.setViewMode(4);
            }
            catch (PropertyVetoException ex)
            {
                ex.printStackTrace();
            }

            modelTechJPanel.validate();
            modelTechJPanel.requestFocus();
            modelTechJPanel.setVisible(true);

        }
        //refresh();
         updateUI();//CR1
    }


    /**
     * 设置显示典型工序主信息面板
     * 本方法用于生成典型工序操作
     */
    public void setModelStepMode()
    {

        if (modelStepJPanel == null)
        {
            modelStepJPanel = new ModelProcedureMasterJPanel(parentJFrame);
            add(modelStepJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));

        }

        if (selectedObject != null)
        {
            QMProcedureInfo info = (QMProcedureInfo) selectedObject;
            modelStepJPanel.setQMProcedure(info);
            modelStepJPanel.setTechNode(selectedNode.getP());
            try
            {
                modelStepJPanel.setViewMode(4);
            }
            catch (PropertyVetoException ex)
            {
                ex.printStackTrace();
            }
            modelStepJPanel.validate();
            modelStepJPanel.requestFocus();
            modelStepJPanel.setVisible(true);
        }
        refresh();
    }


//    CCBegin SS10
    /**
     * 设置显示长特合并工艺规程面板
     */
    public void setCTUniteTechnicsJPanel()
    {
        mode = 1;
    	//this.setTechnicsMode(1);
        CodingIfc technicsType = null;
        QMFawTechnicsInfo tempTechnics = null;
        CappTreeObject obj = ((TechnicsRegulationsMainJFrame) parentJFrame).
                             getSelectedObject();

//        if (obj != null && obj instanceof TechnicsTreeObject)
//        {
//        	
//        	tempTechnics = (QMFawTechnicsInfo) ((TechnicsTreeObject)
//                                        obj).getObject();
//        	technicsType = tempTechnics.getTechnicsType();
//        	System.out.println("AAAAAAAAAA-----1--------select------"+technicsType);
//        }
//        else
//        {
        
        	Class[] paraclass =
                {
                String.class, String.class,String.class};
        Object[] paraobj =
                {
                "27", "工艺种类","工艺主信息"};
        try{
        	technicsType = (CodingIfc) CappClientHelper.useServiceMethod(
                "CodingManageService", "findCodingByCode", paraclass, paraobj);
        	//System.out.println("AAAAAAAAAAA==========="+technicsType);
        }catch(Exception exc ){
        	exc.printStackTrace();
        }
            
            if (technicsType == null)
            {
                return;
            }

        
        //System.out.println("BBBBBBBBBBBBBBBB============"+uniteTechnicsJPanelForCT);
        
        if (uniteTechnicsJPanelForCT == null)
        {

        	uniteTechnicsJPanelForCT = new UniteTechnicsReguJPanelForCT(parentJFrame);
            add(uniteTechnicsJPanelForCT,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        uniteTechnicsJPanelForCT.clear();
        uniteTechnicsJPanelForCT.setTechnicsType(technicsType);
        refresh();
        try
        {
        	uniteTechnicsJPanelForCT.setRelatedTechnics();
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        uniteTechnicsJPanelForCT.validate();
        uniteTechnicsJPanelForCT.requestFocus();
        uniteTechnicsJPanelForCT.setVisible(true);
        refresh();
    }
//CCEnd SS10
    
    /**
     * 设置显示合并工艺规程面板
     */
    public void setUniteTechnicsJPanel()
    {
        mode = 1;
        CodingIfc technicsType = null;

        CappTreeObject obj = ((TechnicsRegulationsMainJFrame) parentJFrame).
                             getSelectedObject();

        if (obj != null && obj instanceof ResourceTypeObject)
        {
            technicsType = (CodingIfc) ((ResourceTypeObject)
                                        obj).getObject();
        }
        else
        {
            if (technicsSelectDialog == null)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.TECHNICSTYPE, null);
                technicsSelectDialog = new SelectTechnicsTypeJDialog(
                        parentJFrame);
            }
            technicsSelectDialog.setVisible(true);
            technicsType = technicsSelectDialog.getCoding();
            if (technicsType == null)
            {
                return;
            }

        }
        if (uniteTechnicsJPanel == null)
        {
            uniteTechnicsJPanel = new UniteTechnicsReguJPanel(parentJFrame);
            add(uniteTechnicsJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        uniteTechnicsJPanel.clear();
        uniteTechnicsJPanel.setTechnicsType(technicsType);
        try
        {
            uniteTechnicsJPanel.setRelatedTechnics();
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        uniteTechnicsJPanel.validate();
        uniteTechnicsJPanel.requestFocus();
        uniteTechnicsJPanel.setVisible(true);
        refresh();
    }
    

    /**
     * 刷新界面
     */
    private void refresh()
    {
        doLayout();
        repaint();
    }


    /**
     * 设置本面板为空。
     * @return 当前界面是否执行了保存操作。如果执行了保存，则返回真。
     */
    public boolean setNullMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsContentJPanel.setNullMode() begin...");
        }
        boolean isSave = true;
        if (technicsMasterJPanel != null && technicsMasterJPanel.isShowing())
        {
            isSave = technicsMasterJPanel.quit();
        }
        //CCBegin SS11
        else if(cdTechnicsMasterJPanel != null && cdTechnicsMasterJPanel.isShowing()){
        	
        	 isSave = cdTechnicsMasterJPanel.quit();
        }
        //CCEnd SS11
        else if (uniteTechnicsJPanel != null && uniteTechnicsJPanel.isShowing())
        {
            isSave = uniteTechnicsJPanel.quit();
        }
        else if (stepJPanel != null && stepJPanel.isShowing())
        {
            isSave = stepJPanel.quit();
        }
        else if (paceJPanel != null && paceJPanel.isShowing())
        {
            isSave = paceJPanel.quit();
        }
        else if (modelStepJPanel != null && modelStepJPanel.isShowing())
        {
            isSave = modelStepJPanel.quit();
        }
        else if (modelTechJPanel != null && modelTechJPanel.isShowing())
        {
            isSave = modelTechJPanel.quit();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsContentJPanel.setNullMode() end...return: " +
                    isSave);
        }
        return isSave;
    }

    
    //问题（1）20081226 xucy  修改原因：优化更新时保存工序 begin
	public void setStepJpanel(boolean flag)
	{
		stepJPanel.setVisible(flag);
	}
	
	public void setPaceJpanel(boolean flag)
	{
		paceJPanel.setVisible(flag);
	}
	//问题（1）20081226 xucy  修改原因：优化更新时保存工序 begin
	
	
   /**
     * 把指定的资源对象添加到对应关联列表中
     * @param info 资源对象（设备、工装、材料）或工艺对象
     */
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsContentJPanel.addObjectToTable() begin...");
        }
        if (technicsMasterJPanel != null && technicsMasterJPanel.isShowing() &&
            technicsMasterJPanel.getMode() != 2)
        {
            technicsMasterJPanel.addObjectToTable(info);
        }
        //CCBegin SS11
        
        else if (cdTechnicsMasterJPanel != null && cdTechnicsMasterJPanel.isShowing() &&
        		cdTechnicsMasterJPanel.getMode() != 2)
            {
            	cdTechnicsMasterJPanel.addObjectToTable(info);
            }
        //CCEnd SS11
        
        else if (stepJPanel != null && stepJPanel.isShowing() &&
                 stepJPanel.getMode() != 2)
        {
            stepJPanel.addObjectToTable(info);
        }
        else if (paceJPanel != null && paceJPanel.isShowing() &&
                 paceJPanel.getMode() != 2)
        {
            paceJPanel.addObjectToTable(info);
        }
        else if (uniteTechnicsJPanel != null && uniteTechnicsJPanel.isShowing())
        {
            uniteTechnicsJPanel.addObjectToTable(info[0]);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsContentJPanel.addObjectToTable() end...return is void");
        }
    }


    /**
     * 主信息面板是否可见
     * @return boolean 主信息面板是否可见
     */
    public boolean techMasterIsShowing()
    {
        if (technicsMasterJPanel != null && technicsMasterJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    //CCBegin SS11
    /**
     * 主信息面板是否可见
     * @return boolean 主信息面板是否可见
     */
    public boolean techMasterIsShowingForCD()
    {
        if (cdTechnicsMasterJPanel != null && cdTechnicsMasterJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
//CCEnd SS11

    /**
     * 工序面板是否可见
     * @return boolean 工序面板是否可见
     */
    public boolean techStepIsShowing()
    {
        if (stepJPanel != null && stepJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * 工步面板是否可见
     * @return boolean 工步面板是否可见
     */
    public boolean techPaceIsShowing()
    {
        if (paceJPanel != null && paceJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * 合并面板是否显示
     * @return boolean 合并面板是否显示
     */
    public boolean uniteTechnicsJPanelIsShowing()
    {
        if (uniteTechnicsJPanel != null && uniteTechnicsJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * 获得主信息面板
     * @return TechnicsMasterJPanel 主信息面板
     */
    public TechnicsMasterJPanel getTechnicsMasterJPanel()
    {
        return technicsMasterJPanel;
    }

    //CCBegin SS11
    /**
     * 获得主信息面板
     * @return TechnicsMasterJPanel 主信息面板
     */
    public TechnicsMasterJPanel getTechnicsMasterJPanelForCD()
    {
        return cdTechnicsMasterJPanel;
    }
//CCEnd SS11
    /**
     * 设置工艺树选择的工艺种类
     * @param code CodingIfc 工艺种类
     */
    public void setResourceTypeObject(CodingIfc code)
    {
        technicsType = code;
    }


    /**
     * 获得模式(查看,更新,新建)
     * @return int 模式
     */
    public int getMode()
    {
        return mode;
    }
    
  //CCBegin SS4
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public static String getUserFromCompany() throws QMException {
 		String returnStr = "";
 		RequestServer server = RequestServerFactory.getRequestServer();
 		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
 		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
 		info.setMethodName("getUserFromCompany");
 		Class[] paraClass = {};
 		info.setParaClasses(paraClass);
 		Object[] obj = {};
 		info.setParaValues(obj);
 		boolean flag = false;
 		try {
 			returnStr = ((String) server.request(info));
 		} catch (QMRemoteException e) {
 			throw new QMException(e);
 		}
 		return returnStr;
 	}
  //CCEnd SS4
}
