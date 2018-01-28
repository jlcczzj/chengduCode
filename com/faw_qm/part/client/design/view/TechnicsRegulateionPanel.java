/**
 * CR1 2009/11/26 王亮  原因：产品信息管理器所有Tab页信息一次全都初始化。
 *                      方案：关注哪个Tab页就初始化哪个Tab页。 
 */
package com.faw_qm.part.client.design.view;


import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.view.TechnicsRegulationsMainJFrame;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.framework.exceptions.QMException;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.faw_qm.part.client.design.controller.TechnicsRegulateionController;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.util.QMCt;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.clients.widgets.IBAUtility;
/**产品信息管理器关联工艺面板。
 * 
 * @author 刘志城
 *
 */
public class TechnicsRegulateionPanel extends HelperPanel 
{

	/**序列化ID*/
    static final long serialVersionUID = 1L;
	public String qmPartBosID;// partBosID
	private JLabel label;// 占位label
	private JPanel jPanel1 = new JPanel();// 按钮面板
	private JButton jButton1 = new JButton();// 显示设置按钮
	private JButton enterTechnics = new JButton();// 进入工艺浏览器按钮
	private JPanel jPanel2 = new JPanel();// 装载multilist的面板
	private PartForCappList partForcappList = new PartForCappList(6);// 包含5列不可多选白色背景的multilist
	private String[] regulationHeadings;
	private String[] regulationMethods;
	private int[] colw;
	private QMPartIfc part;
    private boolean isShown;//此Tab页的显示标志//CR1
	
	/**
	 * 控制类
	 */
	private TechnicsRegulateionController controller = new TechnicsRegulateionController();
	/**
	 * 布局
	 */
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	
	private static final String RESOURCE = "com.faw_qm.part.client.design.util.PartDesignViewRB";
	//default
	public TechnicsRegulateionPanel()
	{
		super();
		
		try {
			initResources();
			setRegulationHeadings();
			setRegulationMethods();
			colw = new int[6];
        	setColw(6);
			jbInit();
            isShown = false;//CR1
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	
	/**
	 * 初始化资源文件。
	 */
	public void initResources()
	{
		try
		{
			resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
					.getLocale());
			return;
		}
		catch (MissingResourceException msexception)
		{
		}
	}

	/**
	 * 获取绑定的资源。
	 * @return String
	 */
	protected ResourceBundle getResource()
	{
		if(null == resource)
		{
			initResources();
		}
		return resource;
	}
	/**
     * 该方法被线程WorkThread内部调用，以初始化实例变量。
     */
    public void setPartItem()
    {   
		PartItem partitem=getPartItem();
		part=partitem.getPart();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        try {
        	partForcappList.setHeadingMethods(regulationMethods);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		partForcappList.setListItems(getQMTechnicsInfo(part));
		 //设置默认选中第一行
        if (getQMTechnicsInfo(part) != null &&
        		getQMTechnicsInfo(part).length != 0)
        {
        	partForcappList.setSelectedRow(0);
        }
		  setCursor(Cursor.getDefaultCursor());
    }
	
    //获取关联part的工艺规程
    private QMTechnicsInfo[] getQMTechnicsInfo(QMPartIfc part)
	{
		QMTechnicsInfo[] info = null;
		PartItem partitem=getPartItem();
		   if(partitem!=null)
		   {
			   if(WorkInProgressHelper
                       .isWorkingCopy((WorkableIfc) part))
			   {
				 try{
				 Class[] classes = {WorkableIfc.class};
                 Object[] aobj = {(WorkableIfc)part};
                 WorkableIfc obj =(WorkableIfc)IBAUtility.invokeServiceMethod(
                         "WorkInProgressService", "originalCopyOf",
                         classes, aobj);
                 String bsoid=obj.getBsoID();
                 Collection collec = controller.findTechnicsByPartID(bsoid);
        		 info = new QMTechnicsInfo[collec.size()];
          		   int i=0;
          		   for(Iterator iter=collec.iterator();iter.hasNext();)
          			{
          				info[i]=(QMTechnicsInfo)iter.next();
          				i++;
          			}
				 }
				 catch(QMException e)
				 {
					 e.printStackTrace();
				 }
      		   }
		else{
		   part=partitem.getPart();
		   qmPartBosID =part.getBsoID();
		   Collection collec = controller.findTechnicsByPartID(qmPartBosID);
		   info = new QMTechnicsInfo[collec.size()];
		   int i=0;
		   for(Iterator iter=collec.iterator();iter.hasNext();)
			{
				info[i]=(QMTechnicsInfo)iter.next();
				i++;
			}
		   }
		   }
			return info;
	}


	/**
	 * 初始化界面：设置按钮，按钮监听，布局。
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		this.setLayout(gridBagLayout2);
		jPanel1.setMaximumSize(new Dimension(32767, 32767));
		jPanel1.setLayout(gridBagLayout1);
		jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
		jButton1.setPreferredSize(new Dimension(83, 23));
		jButton1.setSelected(false);
		jButton1.setText(resource.getString("showset"));
		enterTechnics.setAction(new EnterTechnics(resource.getString("enterregulateionH")));
		enterTechnics.setSelected(false);
		enterTechnics.setMnemonic('H');
		enterTechnics.setFont(new java.awt.Font("Dialog", 0, 12));
		enterTechnics.setPreferredSize(new Dimension(133, 23));
		jPanel2.setLayout(gridBagLayout3);
		this.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 5, 5), 0, 0));
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		jPanel1.add(getLabel(), gridBagConstraints);
		jPanel1.add(enterTechnics, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						5, 0, 5), 0, 0));
		jPanel1.add(jButton1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 5), 0, 0));
		this.add(jPanel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		jPanel2.add(partForcappList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 5, 0), 0, 0));
		
	
		partForcappList.setHeadings(regulationHeadings);
		partForcappList.setHeadingMethods(regulationMethods);
		partForcappList.setRelColWidth(colw);
		partForcappList.setCellEditable(false);
//		partForcappList.setMultipleMode(true);
		partForcappList.setMultipleMode(false);
		partForcappList.setListItems(partForcappList.getQMTechnicsInfo());
	}

	/**
	 * 进入工艺浏览器监听类
	 * 
	 * @author Administrator
	 * 
	 */
	class EnterTechnics extends AbstractAction {
		public EnterTechnics(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent e) {
			int i = partForcappList.getSelectedRow();
			if (i > -1) {
				String technisInfo = partForcappList.getCellText(i, partForcappList.getCol());
				// 掉刷新服务，刷新被选值对象。
				Class[] appDataC = { String.class };
				Object[] paraobj = { technisInfo };
				try {
					QMTechnicsInfo qmTechnisInfo = (QMTechnicsInfo) TechnicsAction
							.useServiceMethod("PersistService", "refreshInfo",
									appDataC, paraobj);
					//将工艺值对象传递给面板。
					controller.processTechnicsCommand(qmTechnisInfo);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			else if(i==-1)
				{
					TechnicsRegulationsMainJFrame tecJFrame =new TechnicsRegulationsMainJFrame(true);
			    	tecJFrame.setVisible(true);
				}

		}
	}

	
	/**
	 * 占位label
	 * 
	 * @return label
	 */
	protected JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
		}
		return label;
	}
	
	/**
	 * 设置列表头
	 *
	 */
	protected void setRegulationHeadings()
	{
		try
		{
			String[] head = (String[])PartServiceRequest.getListHead("regulation");
			regulationHeadings =new String[head.length];
			for(int i=0;i<head.length;i++)
    		{
				regulationHeadings[i]=head[i];
    		}
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	public String[] getRegulationHeadings()
	{
		return regulationHeadings;
	}
	
	/**
	 * 初始化方法
	 */
	protected void setRegulationMethods()
	{
		try
		{
			String[] headingMethod = PartServiceRequest.getListMethod("regulation");
			regulationMethods = new String[headingMethod.length];
			for(int i=0;i<headingMethod.length;i++)
    		{
				regulationMethods[i]=headingMethod[i];
    		}
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	public String[] getRegulationMethods()
	{
		return regulationMethods;
	}
	
	
	/**
     * 设置相对列宽
     * @param num
     */
    private void setColw(int num)
    {
        for(int i = 0;i<regulationHeadings.length;i++)
        {
        		colw[i]=1;
        }
        for(int j=regulationHeadings.length;j<6;j++)
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
    	return jButton1;
    }
    
    public PartForCappList getRegulationList()
    {
    	return partForcappList;
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


