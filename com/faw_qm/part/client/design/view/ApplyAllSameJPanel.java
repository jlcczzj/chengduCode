package com.faw_qm.part.client.design.view;

/**
 * 应用所有界面的公共部分。用来显示目标零部件的JPanel。
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.PartShowMasterDialog;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.exception.WorkInProgressException;
import com.faw_qm.wip.model.WorkableIfc;


public class ApplyAllSameJPanel extends HelperPanel
{
	/** 序列化ID */
	private static final long serialVersionUID = 1L;

	// 添加按钮
	private JButton addJButton = new JButton();

	// 移去按钮
	private JButton removeJButton = new JButton();

	// 界面上放置使用列表的面板
	private JPanel usesPartJPanel = new JPanel();

	// 界面上放置按钮(添加、移去)的面板
	private JPanel usesButtonJPanel = new JPanel();

	// 使用列表表格
	private PartMultiList partMultiList = new PartMultiList(3,false);
	// 界面布局
	private GridBagLayout gridBagLayout = new GridBagLayout();

	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private GridBagLayout gridBagLayout2 = new GridBagLayout();
	
	//用于标记资源文件路径
    protected static final String RESOURCE
            = "com.faw_qm.part.client.design.util.PartDesignViewRB";
    
    /**异常信息标题:"警告"*/
    static String exceptionTitle = QMMessage.getLocalizedMessage(RESOURCE,
    		"errorTitle", null);
    //添加失败的零部件的集合*/
    private List failed_obj= new ArrayList();
    //添加成功的零部件的集合*/
    private List suc_obj=new ArrayList();
    private List suc_obj_ID = new ArrayList();//muyp 10-10
    //源零部件
    private QMPartIfc part;
    /**
	 * 构造函数。
	 */
	public ApplyAllSameJPanel() 
	{	
		super();
		try 
		{
			jbInit();
			localize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception 
	{
		this.setLayout(gridBagLayout);
		usesPartJPanel.setLayout(gridBagLayout1);
		usesButtonJPanel.setLayout(gridBagLayout2);
		addJButton.setBounds(new Rectangle(19, 0, 85, 23));
		addJButton.setFont(new java.awt.Font("Dialog", 0, 12));
		addJButton.setMaximumSize(new Dimension(65, 23));
		addJButton.setMinimumSize(new Dimension(85, 23));
		addJButton.setPreferredSize(new Dimension(83, 23));
		addJButton.setMnemonic('A');
		addJButton.setText("add");
		removeJButton.setBounds(new Rectangle(19, 42, 85, 23));
		removeJButton.setFont(new java.awt.Font("Dialog", 0, 12));
		removeJButton.setMaximumSize(new Dimension(65, 23));
		removeJButton.setMinimumSize(new Dimension(85, 23));
		removeJButton.setPreferredSize(new Dimension(83, 23));
		removeJButton.setMnemonic('R');
		removeJButton.setText("remove");
		
		usesButtonJPanel.add(removeJButton, new GridBagConstraints(0, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(8, 5, 0, 12), 0, 0));
		usesButtonJPanel.add(addJButton, new GridBagConstraints(0, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(15, 5, 0, 12), 0, 0));
		this.add(usesButtonJPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 0));
		this.add(usesPartJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 6, 5, 10), 0, 0));
		usesPartJPanel.add(partMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 2, 5, 0), 0, 0));
		
//		设置列数
        partMultiList.setNumberOfCols(2);
        partMultiList.setMultipleMode(true);
//      设置表头
        partMultiList.setHeadings(new String[]
                                  { "Number", "Name",""});
        //设置列对齐
        partMultiList.setColumnAlignments(new String[]
                                          {"left", "left","left"});
        //设置列宽
        partMultiList.setRelColWidth(new int[]{ 3, 3, 0});
        partMultiList.setFromCapp(true);
        //设置单元格不可编辑
        partMultiList.setCellEditable(false);        
        
		addJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e )
			{
				addJButton_actionPerformed(e);
			}
		});
		removeJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeJButton_actionPerformed(e);
			}
		});
		

	}
	
	/**
     * 本地化方法。
     */
    protected void localize()
    {
    	try
    	{
    		ResourceBundle resourcebundle =
                ResourceBundle.getBundle("com.faw_qm.part.client.design.util.PartDesignViewRB",
                                         RemoteProperty.getVersionLocale());
    		addJButton.setText(resourcebundle.getString("add"));
    		removeJButton.setText(resourcebundle.getString("remove"));
    		partMultiList.setHeadings(new String[]
    		                         {    				                  
    				                  getLabelsRB().getString("numberHeading"),
    				                  getLabelsRB().getString("nameHeading")
    		                                     });
    		
    		
    	
    	}
    	catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    
    /**
     * 运行搜索器搜索要使用的新零部件。
     * @param event the action event
     */
    void addJButton_actionPerformed(ActionEvent event)
    {
        PartDebug.debug("addUsageJButton_actionPerformed(e) begin...", this,
                        PartDebug.PART_CLIENT);
        //获得父窗口
        JFrame frame = getParentFrame();
        String partBsoName = QMMessage.getLocalizedMessage(RESOURCE,
                PartDesignViewRB.QMPM_BSONAME, null);
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "findPartTitle", null);

        //定义搜索器
        QmChooser qmChooser = new QmChooser(partBsoName, title, frame);
        qmChooser.setChildQuery(false);
        qmChooser.setRelColWidth(new int[]
                                 {1, 1});
        PartDebug.debug("************创建搜索零部件成功！———qmChooser = " + qmChooser,
                        PartDebug.PART_CLIENT);
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener()
        {

            public void queryEvent(QMQueryEvent e)
            {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
        PartDebug.debug(
                "addUsageJButton_actionPerformed(e) end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    /**
     * 搜索零部件监听事件方法。
     * @param event 搜索监听事件。
     */
    public void qmChooser_queryEvent(QMQueryEvent event)
    {
    	if(event.getType().equals(QMQueryEvent.COMMAND))
		{
			if(event.getCommand().equals(QmQuery.OkCMD))
			{
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser) event.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                if(bvi != null)
                {
                	for(int i=0;i<bvi.length;i++)
                	{
                		
                        //所选择的某一零部件
                        BaseValueIfc newPart = bvi[i];
                        //获得零部件的主信息
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)newPart;
                        //获得要添加的零部件的最新小版本
                        QMPartIfc partIfc = this.getLastedIterations(
                                newPartMaster);
                        if((part.getPartName()).equals(partIfc.getPartName()))
                        {
                        	Object[] params = {partIfc.getPartNumber()
                                    +"-"+ partIfc.getPartName()};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    PartDesignViewRB.PART_EQUAL_SELECTED,
                                    params);
                            showMessage(message, exceptionTitle);
                            message = null;
                        }
                        /////////muyp 10-10 begin
                        else if(suc_obj_ID.contains(partIfc.getBsoID()))
                        {
                        	Object[] params = {partIfc.getPartNumber()
                                    +"-"+ partIfc.getPartName()};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    PartDesignViewRB.PART_ALREADY_DISPLAYED,
                                    params);
                            showMessage(message, exceptionTitle);
                            message = null;
                        }
                        else
                        {//end
                        	 if(partIfc instanceof WorkableIfc)
                             {
                             	WorkableIfc workableIfc=(WorkableIfc)partIfc;
                             	try {
//                             		if(CheckInOutTaskLogic.isCheckedOut(workableIfc)&&
//     										CheckInOutTaskLogic.isCheckedOutByUser(workableIfc))
//                             		liyz add 对象是否在个人资料夹中 2009/05/25
                             		if((CheckInOutTaskLogic.isCheckedOut(workableIfc)&&
     										CheckInOutTaskLogic.isCheckedOutByUser(workableIfc))
     										||isInPersonal(workableIfc))
     								{
                             			suc_obj_ID.add(partIfc.getBsoID());
     									suc_obj.add(partIfc);
     								}
     								else
     								{
     									failed_obj.add(newPartMaster);
     								}
     							} catch (WorkInProgressException e1) 
     							{
     								e1.printStackTrace();
     							} catch (QMException e1) 
     							{
     								e1.printStackTrace();
     							}
                             }
                     	}
			        }
                 	String[] head = new String[suc_obj.size()];
                 	for(int j =0;j<suc_obj.size();j++)
                 	{
                 		head[j]=((QMPartIfc)suc_obj.get(j)).getPartNumber()+";"+((QMPartIfc)suc_obj.get(j)).getPartName()
                                 +";"+((QMPartIfc)suc_obj.get(j)).getBsoID();
                 	}
                 	partMultiList.setListItems(head);//.addObjects(suc_obj);
                 	if(!failed_obj.isEmpty())
                 	{
                     	PartShowMasterDialog dialog = new PartShowMasterDialog(
                     			failed_obj,this.getParentFrame());
                         dialog.setSize(400, 300);
                         PartScreenParameter.centerWindow(dialog);
                         failed_obj.clear();
                      }
                 }
		    }

    	}

    }
    
    /**
     * 是否在个人资料夹中
     * @param value
     * @return true 在个人资料夹中
     * @throws QMException
     */
    private boolean isInPersonal(BaseValueIfc value)
    throws QMException
    {
    	Object object = IBAUtility.invokeServiceMethod("SessionService",
            "getCurUserID", null, null);
    boolean flag = OwnershipHelper.isOwnedBy((OwnableIfc) value, (String) object);
    return flag;
    }
    
    /**
     * 根据零部件的主信息，获得零部件的最新小版本。
     * @param partMaster  零部件的主信息。
     * @return  零部件的最新小版本。
     */
    QMPartIfc getLastedIterations(QMPartMasterIfc partMaster)
    {
        PartDebug.debug("getLastedIterations() begin...", this,
                        PartDebug.PART_CLIENT);
        QMPartIfc partIfc = null;
        //调用服务方法，获得零部件的所有小版本
        Class[] paraClass = {MasteredIfc.class};
        Object[] objs = {partMaster};
        Collection collection = null;
        try
        {
            collection = (Collection) IBAUtility.invokeServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(getParentFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //获得零部件的最新小版本
            partIfc = (QMPartIfc) iterator.next();
        }
        PartDebug.debug("getLastedIterations() end...return " + partIfc, this,
                        PartDebug.PART_CLIENT);
        return partIfc;
    }
    
    /**
     * 点击移去按钮实现的功能：
     * @param event ActionEvent
     */
    public void removeJButton_actionPerformed(ActionEvent event)
    {    	
    	int[] i =partMultiList.getSelectedRows();//所有选择行
    	int length = partMultiList.getNumberOfRows();//列表的总行数
    	int k =0;
		if(i.length != 0)
		{
			for(int j= 0;j<i.length;j++)
			{
				partMultiList.removeRow(i[j]-k);
				suc_obj.remove(j);
				suc_obj_ID.remove(j);//muyp 10-10
				k++;
			}
		}
		else
		{
			for(int j= 0;j<length;j++)
			{
				partMultiList.removeRow(j-k);
				k++;
			}
			suc_obj.clear();
			suc_obj_ID.clear();//muyp 10-10
		}

    }
    
    public PartMultiList getPartMultiList() {
		// TODO 自动生成方法存根
		return partMultiList;
	}
    /**
     * 显示错误信息框。
     * @param message String 显示的错误信息。
     * @param title String 信息框的标题。
     */
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(this.getParentFrame(), message, title,
                JOptionPane.WARNING_MESSAGE);
        
    }
    public List getSucPart()
    {
    	return suc_obj;
    }
    
    public void setPartOfAttr(QMPartIfc part)
    {
    	this.part = part;
    }
}
