/**
* CR1 2009/11/26 ����  ԭ�򣺲�Ʒ��Ϣ����������Tabҳ��Ϣһ��ȫ����ʼ����
*                      ��������ע�ĸ�Tabҳ�ͳ�ʼ���ĸ�Tabҳ��
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
	
	/**���л�ID*/
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
    private boolean isShown;//��Tabҳ����ʾ��־//CR1

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
	 * �����ʼ��
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
		    
		    
		    
		    //����ťע�������
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
     * �÷������߳�WorkThread�ڲ����ã��Գ�ʼ��ʵ��������
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
		 //����Ĭ��ѡ�е�һ��
        if (getTechnicsRouteListInfo(partInfo) != null &&
        		getTechnicsRouteListInfo(partInfo).length != 0)
        {
        	cappList.setSelectedRow(0);
        }
		  setCursor(Cursor.getDefaultCursor());
    }
	
    //��ȡ����part�Ĺ���·��
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
	 * ��ť������
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
	 * �����б�ͷ
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
	 * ��ñ�ͷ
	 * @return
	 */
	public String[] getRouteHeadings()
	{
		return routeHeadings;
	}
	
	/**
	 * ��ʼ������
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
	 * ���getXX�ķ���
	 * @return
	 */
	public String[] getrouteMethods()
	{
		return routeMethods;
	}
	
	
	/**
    * ��������п�
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
     * �����ʾ���ð�ť
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
     * ����Tabҳ��ʾ��־
     * @param isShown
     */
    public void setIsShown(boolean isShown)//CR1
    {
        this.isShown = isShown;
    }
    /**
     * ��ȡTabҳ��ʾ��־
     * @return
     */
    public boolean getIsShown()
    {
        return isShown;
    }
}

