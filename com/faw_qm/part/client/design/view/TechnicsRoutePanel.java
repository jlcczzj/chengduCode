/**
* CR1 2009/11/26 王亮  原因：产品信息管理器所有Tab页信息一次全都初始化。
*                      方案：关注哪个Tab页就初始化哪个Tab页。
*/  
package com.faw_qm.part.client.design.view;
                   
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.faw_qm.part.client.design.controller.ProductManagerOfTechnicsController;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.util.QMCt;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.framework.exceptions.QMException;

public class TechnicsRoutePanel extends HelperPanel {
	
	/**序列化ID*/
    static final long serialVersionUID = 1L;
	private JLabel label = new JLabel();
	
	private JButton showRoutelistbutton = new JButton();;
	
	private JButton displayButton = new JButton();
	
	private JPanel buttonsPanel = new JPanel();
	
    public  PartForCappList cappList=new PartForCappList(6);;
    private String[] routeHeadings;
    private String[] routeMethods;
    private int[] colw;
    private QMPartIfc partInfo;

    protected static final String RESOURCE
    = "com.faw_qm.part.client.design.util.PartDesignViewRB";
	
	private static ResourceBundle resource = null;
	ProductManagerOfTechnicsController productTechControl=new ProductManagerOfTechnicsController(this);
    private boolean isShown;//此Tab页的显示标志//CR1

	//default
	
	public TechnicsRoutePanel()
	{
		super();
		try
		{
			setRouteHeadings();
	    	setRouteMethods();
	    	colw = new int[6];
	    	setColw(6);
			initialize();
            isShown = false;//CR1
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public  TechnicsRoutePanel(QMPartIfc partInfo) {
		
		this.partInfo = partInfo;
		try
		{	    	
			initialize();
            isShown = false;//CR1
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 界面初始化
	 */
	private void initialize() throws Exception {
		resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
				.getLocale());
		    this.setLayout(new GridBagLayout()); 
		    buttonsPanel.setLayout(new GridBagLayout());
			cappList.setDebugGraphicsOptions(0);
			cappList.setMultipleMode(false); 
		    cappList.setHeadings(routeHeadings);
		    cappList.setHeadingMethods(routeMethods);
	        cappList.setRelColWidth(colw);

	        cappList.setCellEditable(false);

			displayButton.setText(resource.getString("showset"));
			displayButton.setFont(new java.awt.Font("Dialog", 0, 12));
			displayButton.setPreferredSize(new Dimension(83, 23));
			showRoutelistbutton.setText(resource.getString("enterroute"));
			showRoutelistbutton.setFont(new java.awt.Font("Dialog", 0, 12));
			showRoutelistbutton.setPreferredSize(new Dimension(155, 23));
			buttonsPanel.setMaximumSize(new Dimension(500, 75));
			
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		   	gridBagConstraints.gridx = 0;
		   	gridBagConstraints.gridy = 0;
		   	gridBagConstraints.insets = new Insets(0, 0, 0, 5);
			
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		   	gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		   	gridBagConstraints_1.weightx = 1.0;
		   	gridBagConstraints_1.gridx = 1;
		   	gridBagConstraints_1.gridy = 0;
		   	gridBagConstraints_1.insets = new Insets(0, 0, 5, 5);
		   	
		   	final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		   	gridBagConstraints_2.gridx = 2;
		   	gridBagConstraints_2.gridy = 0;
		   	gridBagConstraints_2.insets = new Insets(0, 5, 0, 5);
		   	
			buttonsPanel.add(displayButton, gridBagConstraints);
		    buttonsPanel.add(showRoutelistbutton, gridBagConstraints_2);
		    buttonsPanel.add(label, gridBagConstraints_1);
		    
		    
		    
		    //给按钮注册监听类
//		    displayButton.addActionListener(new ButtonsAction());
		    showRoutelistbutton.addActionListener(new ButtonsAction());
		    
		    final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		   	gridBagConstraints_3.fill = GridBagConstraints.BOTH;
		   	gridBagConstraints_3.weighty = 1.0;
		   	gridBagConstraints_3.weightx = 1.0;
		   	gridBagConstraints_3.gridx = 0;
		   	gridBagConstraints_3.gridy = 0;
		   	gridBagConstraints_3.insets = new Insets(0, 0, 0, 0);
		   	
		   	final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		   	gridBagConstraints_4.fill = GridBagConstraints.HORIZONTAL;
		   	gridBagConstraints_4.weightx = 1.0;
		   	gridBagConstraints_4.gridx = 0;
		   	gridBagConstraints_4.gridy = 1;
		   	gridBagConstraints_4.insets = new Insets(5, 0, 5, 5);
		   	
		   
		   	this.add(cappList,gridBagConstraints_3);
		    this.add(buttonsPanel,gridBagConstraints_4);
		    
		    this.setVisible(true);
		
	}
	
	/**
     * 该方法被线程WorkThread内部调用，以初始化实例变量。
     */
    public void setPartItem()
    {   
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        try {
        	cappList.setHeadingMethods(routeMethods);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cappList.setListItems(getTechnicsRouteListInfo(this.partInfo));
		 //设置默认选中第一行
        if (getTechnicsRouteListInfo(partInfo) != null &&
        		getTechnicsRouteListInfo(partInfo).length != 0)
        {
        	cappList.setSelectedRow(0);
        }
		  setCursor(Cursor.getDefaultCursor());
    }
	
    //获取关联part的工艺路线
	protected TechnicsRouteListInfo[] getTechnicsRouteListInfo(QMPartIfc partInfo)
	{
		TechnicsRouteListInfo[]  routeListInfo=null;
		PartItem partitem=this.getPartItem();
		if(partitem!=null)
		{
			partInfo=partitem.getPart();
		String partMasterBsoid=partInfo.getMasterBsoID();		
		Collection routeListInfoColl=productTechControl.getRouteListInfo(partMasterBsoid);
		routeListInfo=new TechnicsRouteListInfo[routeListInfoColl.size()];
		int i=0;
		for(Iterator iter=routeListInfoColl.iterator();iter.hasNext();)
		{
			routeListInfo[i]=(TechnicsRouteListInfo)iter.next();
			i++;
		}
		}
		return routeListInfo;
	}
	
	class MyMouseEvent extends MouseAdapter {
		
			    public void mouseClicked(MouseEvent e) {
			    	
			    	if (e.getButton() == 2) {

			    		
			    	    }
			    	if (e.getButton() == 3) {

			    	      //listPopup.show(jTable1, e.getX(), e.getY());

			    	    }
			    	
			    }
		
	}
	
	/**
	 * 按钮监听类
	 * @author Administrator
	 *
	 */
	class ButtonsAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e)  {
		
			if(e.getSource()==displayButton) {
				
				productTechControl.processDisplayCommand();
				
			}else if(e.getSource()==showRoutelistbutton) {
				
				productTechControl.processShowCommand();
				
			}
			
			
		}
		
	}
	
	
	/**
	 * 设置列表头
	 *
	 */
	protected void setRouteHeadings()
	{
		try
		{
			String[] head = PartServiceRequest.getListHead("route");
			routeHeadings =new String[head.length];
			for(int i=0;i<head.length;i++)
   		{
				routeHeadings[i]=head[i];
   		}
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得表头
	 * @return
	 */
	public String[] getRouteHeadings()
	{
		return routeHeadings;
	}
	
	/**
	 * 初始化方法
	 */
	protected void setRouteMethods()
	{
		try
		{
			String[] headingMethod = PartServiceRequest.getListMethod("route");
			routeMethods = new String[headingMethod.length];
			for(int i=0;i<headingMethod.length;i++)
   		{
				routeMethods[i]=headingMethod[i];
   		}
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得getXX的方法
	 * @return
	 */
	public String[] getrouteMethods()
	{
		return routeMethods;
	}
	
	
	/**
    * 设置相对列宽
    * @param num
    */
   private void setColw(int num)
   {
       for(int i = 0;i<routeHeadings.length;i++)
       {
       		colw[i]=1;
       }
       for(int j=routeHeadings.length;j<6;j++)
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
    	return this.displayButton;
    }
    
    public PartForCappList getRouteList()
    {
    	return cappList;
    }
    /**
     * 设置Tab页显示标志
     * @param isShown
     */
    public void setIsShown(boolean isShown)//CR1
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

