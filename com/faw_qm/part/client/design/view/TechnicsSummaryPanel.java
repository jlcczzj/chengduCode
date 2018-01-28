/**
 * CR1 2009/07/02 wangl  修改原因：详见TD2489，在产品信息管理中零部件带有汇总信息，将零部件检出，零部件副本不带有汇总结果信息。
 * CR2 2009/11/26 王亮  原因：产品信息管理器所有Tab页信息一次全都初始化。
 *                      方案：关注哪个Tab页就初始化哪个Tab页。                                  
 */
package com.faw_qm.part.client.design.view;



import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.part.client.design.controller.TechnicsSummaryController;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.summary.model.TotalSchemaItemInfo;
import com.faw_qm.util.QMCt;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

public class TechnicsSummaryPanel extends HelperPanel
{
	/**序列化ID*/
    static final long serialVersionUID = 1L;
   public PartForCappList partForcappList; 
   private QMPartIfc part;
   private TechnicsSummaryController summaryController;
  
   private String[] summaryHeadings;
   private String[] summaryMethods;
   private int[] colw;
   final JButton viewdesign = new JButton();
   protected static final String RESOURCE
   = "com.faw_qm.part.client.design.util.PartDesignViewRB";
	
	private static ResourceBundle resource = null;
    private boolean isShown;//此Tab页的显示标志//CR2
   //default
   public TechnicsSummaryPanel()
   {
	   super();
     try {
    	 setSummaryHeadings();
    	 setSummaryMethods();
    	 colw = new int[5];
    	 setColw(5);
		jbInit();
        isShown = false;//CR2
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
   
   public TechnicsSummaryPanel(QMPartIfc part)
   {	   
    this.part = part;	
     try {		
		jbInit();
        isShown = false;//CR2
	} catch (Exception e) {
		e.printStackTrace();
	}	
   }
   
    
   /**
	 * 初始化界面：设置按钮，按钮监听，布局。
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
				.getLocale());
		partForcappList = new PartForCappList(5);
//		partForcappList.setMultipleMode(true);//不能多选
		partForcappList.setMultipleMode(false);//不能多选
		partForcappList.setHeadings(summaryHeadings);
		partForcappList.setCellEditable(false);
		partForcappList.setRelColWidth(colw);
		partForcappList.setHeadingMethods(summaryMethods);
		setLayout(new GridBagLayout());

	   	final JPanel panel = new JPanel();
	   	panel.setLayout(new GridBagLayout());
	   	final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
	   	gridBagConstraints_4.fill = GridBagConstraints.HORIZONTAL;
	   	gridBagConstraints_4.weightx = 1.0;
	   	gridBagConstraints_4.gridx = 0;
	   	gridBagConstraints_4.gridy = 1;
	   	gridBagConstraints_4.insets = new Insets(0, 0, 5, 5);
	   	add(panel, gridBagConstraints_4);
	   	
	   	viewdesign.setPreferredSize(new Dimension(83, 23));
	   	viewdesign.setMinimumSize(new Dimension(83, 23));
	   	viewdesign.setMaximumSize(new Dimension(83, 23));
	   	viewdesign.setActionCommand("design");
	   	viewdesign.setText(resource.getString("showset"));
	   	final GridBagConstraints gridBagConstraints = new GridBagConstraints();
	   	gridBagConstraints.gridx = 0;
	   	gridBagConstraints.gridy = 0;
	   	gridBagConstraints.insets = new Insets(5, 0, 0, 5);
	   	panel.add(viewdesign, gridBagConstraints);

	   	final JLabel label = new JLabel();
	   	final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
	   	gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
	   	gridBagConstraints_1.weightx = 1.0;
	   	gridBagConstraints_1.gridx = 1;
	   	gridBagConstraints_1.gridy = 0;
	   	gridBagConstraints_1.insets = new Insets(5, 0, 5, 5);
	   	panel.add(label, gridBagConstraints_1);

	   	final JButton summarybutton = new JButton();
	   	summarybutton.setPreferredSize(new Dimension(145, 23));
	   	summarybutton.setMinimumSize(new Dimension(145, 23));
	   	summarybutton.setMaximumSize(new Dimension(145, 23));
	   	summarybutton.setActionCommand("summary");
	   	summarybutton.setText(resource.getString("entersummary"));
	   	final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
	   	gridBagConstraints_2.gridx = 2;
	   	gridBagConstraints_2.gridy = 0;
	   	gridBagConstraints_2.insets = new Insets(5, 0, 0, 5);
	   	panel.add(summarybutton, gridBagConstraints_2);

	   	final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
	   	gridBagConstraints_3.fill = GridBagConstraints.BOTH;
	   	gridBagConstraints_3.weighty = 1.0;
	   	gridBagConstraints_3.weightx = 1.0;
	   	gridBagConstraints_3.gridx = 0;
	   	gridBagConstraints_3.gridy = 0;
	   	gridBagConstraints_3.insets = new Insets(0, 0, 0, 0);
	   	add(partForcappList, gridBagConstraints_3);
	     	    

	     //声明监听类并注册
	     summaryController = new TechnicsSummaryController(this);	     
	     viewdesign.addActionListener(summaryController);
	     summarybutton.addActionListener(summaryController);
	     this.addMouseListener(summaryController);
	}
   
	
	 /**
     * 该方法被线程WorkThread内部调用，以初始化实例变量。
     */
    public void setPartItem()
    {   
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        try {
        	partForcappList.setHeadingMethods(summaryMethods);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		partForcappList.setListItems(getTotalSchemaItem());
		 //设置默认选中第一行
        if (getTotalSchemaItem() != null &&
        		getTotalSchemaItem().length != 0)
        {
        	partForcappList.setSelectedRow(0);
        }
		  setCursor(Cursor.getDefaultCursor());
    }
    
    //获取关联part的汇总信息
   private TotalSchemaItemInfo[] getTotalSchemaItem() 
   {
	   
	   TotalSchemaItemInfo[] toItem =null;
	   PartItem partitem=this.getPartItem();
	   if(partitem!=null)
	   {
		   part=partitem.getPart();
		   String bsoid = "";//Begin CR1
		   if(WorkInProgressHelper
                   .isWorkingCopy((WorkableIfc) part))
		   {
			   try {
					Class[] classes = { WorkableIfc.class };
					Object[] aobj = { (WorkableIfc) part };
					WorkableIfc obj = (WorkableIfc) IBAUtility
							.invokeServiceMethod("WorkInProgressService",
									"originalCopyOf", classes, aobj);
					bsoid = obj.getBsoID();
				}
			   catch(QMException e)
				 {
					 e.printStackTrace();
				 }
		   }
		   else
		   {
			   bsoid = part.getBsoID();
		   }//End CR1
	   ServiceRequestInfo info = new ServiceRequestInfo();
	   info.setServiceName("ProductManagerOfTechnicsService");
	   info.setMethodName("getSummaryByPart");
	   Class[] cla = {String.class};
	   info.setParaClasses(cla);
	   Object[] obj = {bsoid};
//	   Object[] obj = {"QMPart_43101"};
	   info.setParaValues(obj);
	   RequestServer server = null;
       Object myObject = null;
       Collection coll = null;
       try
       {
           //通过请求服务器的工厂获得server
           server = RequestServerFactory.getRequestServer();
           myObject = server.request(info);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       if(myObject!=null)
       {
    	   if(myObject instanceof Collection)
    	   {
    		   coll = (Collection)myObject;
    	   }
       }
       if(coll!=null && coll.size()>0)
       {
    	   Object[] objs = coll.toArray();
    	    toItem = new TotalSchemaItemInfo[coll.size()];
    	   for(int i=0;i<objs.length;i++)
    	   {
    		   if(objs[i] instanceof TotalSchemaItemInfo)
    		   {
    			   toItem[i] =(TotalSchemaItemInfo)objs[i];
    		   }
    	   }
       }
	   }
       return toItem;
   }
   
   
   /**
	 * 设置列表头
	 *
	 */
	protected void setSummaryHeadings()
	{
		try
		{
			String[] head =PartServiceRequest.getListHead("summary");
			summaryHeadings =new String[head.length];
			for(int i=0;i<head.length;i++)
   		{
				summaryHeadings[i]=head[i];
   		}
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	public String[] getSummaryHeadings()
	{
		return summaryHeadings;
	}
	
	/**
	 * 初始化方法
	 */
	protected void setSummaryMethods()
	{
		try
		{
			String[] headingMethod = PartServiceRequest.getListMethod("summary");
			summaryMethods = new String[headingMethod.length];
			for(int i=0;i<headingMethod.length;i++)
   		{
				summaryMethods[i]=headingMethod[i];
   		}
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	public String[] getSummaryMethods()
	{
		return summaryMethods;
	}
	
	
	/**
    * 设置相对列宽
    * @param num
    */
   private void setColw(int num)
   {
       for(int i = 0;i<summaryHeadings.length;i++)
       {
       		colw[i]=1;
       }
       for(int j=summaryHeadings.length;j<5;j++)
       {
       colw[j]=0;
       }
   }
   
   /**
    * 获得显示设置按钮
    * @return
    */
   public JButton getShowAttrSettedJButton()
   {
	   return viewdesign;
   }
   
   public PartForCappList getSummaryList()
   {
	   return partForcappList;
   }
   /**
    * 设置Tab页显示标志
    * @param isShown
    */
   public void setIsShown(boolean isShown)//CR2
   {
       this.isShown = isShown;
   }
   /**
    * 获取Tab页显示标志
    * @return
    */
   public boolean getIsShown()
   {
       return isShown;
   }
}
