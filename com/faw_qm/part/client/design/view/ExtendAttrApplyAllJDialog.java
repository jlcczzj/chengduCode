/**
 * 
 */
package com.faw_qm.part.client.design.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.widgets.ActionAbortedException;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.csm.navigation.model.ClassificationNodeIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.client.container.main.IBAContainerEditor;
import com.faw_qm.iba.constraint.exception.IBAConstraintException;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.definition.litedefinition.BooleanDefView;
import com.faw_qm.iba.definition.litedefinition.FloatDefView;
import com.faw_qm.iba.definition.litedefinition.IntegerDefView;
import com.faw_qm.iba.definition.litedefinition.RatioDefView;
import com.faw_qm.iba.definition.litedefinition.ReferenceDefView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.litedefinition.TimestampDefView;
import com.faw_qm.iba.definition.litedefinition.URLDefView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.BooleanValueDefaultView;
import com.faw_qm.iba.value.litevalue.DefaultLiteIBAReferenceable;
import com.faw_qm.iba.value.litevalue.FloatValueDefaultView;
import com.faw_qm.iba.value.litevalue.IntegerValueDefaultView;
import com.faw_qm.iba.value.litevalue.RatioValueDefaultView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.litevalue.TimestampValueDefaultView;
import com.faw_qm.iba.value.litevalue.URLValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.lite.QSFDebug;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.units.util.MeasurementSystemDefaultView;

  /**
   * 事物特性应用所有界面.
   * (可以添加分类节点之前)
   * @author 穆勇鹏
   *CR1 2009/06/04 马辉 原因:解决父窗口不能被锁定，TD-2162
   * 方案:将JDialog改为JFrame就可以锁定父窗口，为了便于维护，类名依然为PartDialog, 但实际类型为JFrame
   *
   */
//CR1 begin   public class ExtendAttrApplyAllJDialog extends JDialog implements ActionListener
   public class ExtendAttrApplyAllJDialog extends JFrame implements ActionListener//CR1end
   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  private static RequestHelper helper = new RequestHelper();
    
    //按钮面板(确定、取消、应用所有) 
    private JPanel buttonJP = new JPanel();

    //目标零部件显示面板
    private ApplyAllSameJPanel samepanel = new ApplyAllSameJPanel();
    
    //事物特性选择面板
    private ApplyAttrToOthersPartsJPanel attrpanel = null;
    
    //添加成功的零部件的集合*/
    private BaseValueIfc[] suc_obj=null;
    
    //布局管理
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    //“确定”按钮
    private JButton okJB = new JButton();

    //“取消”按钮
    private JButton cancelJB = new JButton();
    
    //“应用所有”按钮
    private JButton applyJButton = new JButton();
    
    //“应用所有”按钮是否被按下标记
    private boolean applyflag = false;
    
    private IBAContainerEditor IBACon;

	private JLabel ibaAttr= new JLabel();
    
	//保存源零部件值对象-属性容器的HashMap
	private HashMap part_con_map = new HashMap();
	//用于标记资源文件路径
    protected static final String RESOURCE
            = "com.faw_qm.part.client.design.util.PartDesignViewRB";
    /**源零部件的属性容器*/
	DefaultAttributeContainer defAttrcon = null;
	/**被添加属性值和是否存在于目标零部件的boolean值的健值对*/
	private HashMap beadded = new HashMap();//
	private String t ="1";
	private String f="0";
	/**源零部件被选中的将要应用的属性定义*/
	private AttributeDefDefaultView[] applydef =null;
    /**异常信息标题:"警告"*/
    static String exceptionTitle = QMMessage.getLocalizedMessage(RESOURCE,
    		PartDesignViewRB.MESSAGE, null);
    
	

    
    /**
     * 构造函数
     * @param frame JFrame 产品管理器主界面
     * @param frame1 JFrame 父窗格
     * @param part QMPartIfc 源零部件值对象接口
     */
    public ExtendAttrApplyAllJDialog(JFrame frame1,String title,IBAContainerEditor IBACon)
    {
// CR1 begin	  	super(frame1,title,true);
    	super(title);
    	this.IBACon = IBACon;
    	attrpanel = new ApplyAttrToOthersPartsJPanel(this.IBACon);
    	samepanel.setPartOfAttr((QMPartIfc)IBACon.currentItem);
    	jbInit();
        PartScreenParameter.setLocationCenter(this);
    }

	private void jbInit()
	{
	
		String iconImage = QMMessage.getLocalizedMessage(
                "com.faw_qm.part.client.main.util.QMProductManagerRB",
                QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
		this.setSize(650,500);
		this.getContentPane().setLayout(gridBagLayout1);
//		this.setResizable(false);//CR 1end
		//按钮面板
		buttonJP.setLayout(gridBagLayout2);
		
		ibaAttr.setText("IBAItem");
		
        //JButton
        cancelJB.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJB.setMaximumSize(new Dimension(85, 23));
        cancelJB.setMinimumSize(new Dimension(75, 23));
        cancelJB.setPreferredSize(new Dimension(83, 23));
        cancelJB.setActionCommand("CANCEL");
        cancelJB.setMnemonic('C');
        cancelJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        okJB.setFont(new java.awt.Font("Dialog", 0, 12));
        okJB.setMaximumSize(new Dimension(85, 23));
        okJB.setMinimumSize(new Dimension(75, 23));
        okJB.setPreferredSize(new Dimension(83, 23));
        okJB.setActionCommand("OK");
        okJB.setMnemonic('Y');
        okJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               
				okJButton_actionPerformed(e);

            }
        });
        applyJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        applyJButton.setMaximumSize(new Dimension(100, 23));
        applyJButton.setMinimumSize(new Dimension(75, 23));
        applyJButton.setPreferredSize(new Dimension(83, 23));
        applyJButton.setActionCommand("APPLY");
        applyJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                applyJButton_actionPerformed(e);
            }
        });
        this.getContentPane().add(ibaAttr,
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                       ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 5, 0), 0, 0));
        //buttonJP
        this.getContentPane().add(buttonJP,
                                     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 8, 22), 0, 0));
        buttonJP.add(cancelJB, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,new Insets(0, 5, 0, 0), 0, 0));
        buttonJP.add(okJB, 
        		new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, 
        				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0, 5, 0, 0), 0, 0));
        buttonJP.add(applyJButton, 
        		new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                        , GridBagConstraints.CENTER, GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));
        //应用所有公共部分面板
		this.getContentPane().add(samepanel,
				new GridBagConstraints(0, 0, 2, 1, 0.0, 0.5,
		                GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
		//事物特性选择属性面板
		this.getContentPane().add(attrpanel,
                new GridBagConstraints(0, 2, 2, 1, 1.0, 0.5,
                        GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5,8,10,105),0,0));
		
		localize();
	}//jbInit


    /*
     *本地化信息。
     */
    protected void localize()
    {
		ResourceBundle resourcebundle =
            ResourceBundle.getBundle("com.faw_qm.part.client.design.util.PartDesignViewRB",
                                     RemoteProperty.getVersionLocale());
		cancelJB.setText(resourcebundle.getString("cancel"));
		okJB.setText(resourcebundle.getString("ok"));
		applyJButton.setText(resourcebundle.getString("applyAll"));
		ibaAttr.setText(resourcebundle.getString("IBAItem"));
    }
	
	/**
	 * 执行“确定”按钮操作。
	 * @param e 按钮动作监听事件。
	 * @throws QMException 
	 */
	protected void okJButton_actionPerformed(ActionEvent e)
	{
		if(!applyflag)
		{
			setSuc_obj();
			try {
				applyToAll();
			} catch (IBAConstraintException e1) {
	            String message = QMMessage.getLocalizedMessage(RESOURCE,
	            		PartDesignViewRB.IBACONSTRAINTEXCEPTION,null);
	            showMessage(message, exceptionTitle);
	            message = null;
				e1.printStackTrace();
			}
		}

		save();
		applyflag = false;
		beadded.clear();
		this.dispose();
	}

	/**
     * 执行“取消”按钮操作。
     * @param e 按钮动作监听事件。
     */
	protected void cancelJButton_actionPerformed(ActionEvent e)
    {
		this.dispose();
    }

	/**
	 * 执行“应用所有”按钮操作。将原零部件选定的属性应用到目标零部件，
	 * 但是不关闭窗口,不保存到数据库。
	 * @param e 按钮动作监听事件。
	 */
	protected void applyJButton_actionPerformed(ActionEvent e) 
	{
		applyflag = true;
		setSuc_obj();
		try {
			applyToAll();
		} catch (IBAConstraintException e1) {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
            		PartDesignViewRB.IBACONSTRAINTEXCEPTION,null);
            showMessage(message, exceptionTitle);
            message = null;
			e1.printStackTrace();
		}
	}

	/**
	 * 应用所有
	 * @throws IBAConstraintException 
	 *
	 */
	private void applyToAll() throws IBAConstraintException
	{
		/*处理事物特性TAB页的应用所有。对目标零部件进行循环处理。
		 * 根据选择的HashMap
		 */
		for(int i=0;i<suc_obj.length;i++)
		{
			

			try {
				defAttrcon = (DefaultAttributeContainer)(((IBAHolderIfc)IBACon.getItem()).getAttributeContainer());
			} catch (ActionAbortedException e) {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                		PartDesignViewRB.GET_ATTRIBUTECONTAINER_FAILED,null);
                showMessage(message, exceptionTitle);
                message = null;
				e.printStackTrace();
			}
			
			IBAHolderIfc info = (IBAHolderIfc)suc_obj[i];
			//如果目标零部件的事物特性属性容器为空，则刷新目标零部件的事物特性属性容器。
			if(((DefaultAttributeContainer)info.getAttributeContainer())==null)
			{
				try
                {
                    //要调用的方法中的参数的类的集合
                    Class[] classes = {IBAHolderIfc.class, Object.class,
                            Locale.class, MeasurementSystemDefaultView.class};
                    //要调用的方法中的参数的集合
                    Object[] objects = {(IBAHolderIfc) suc_obj[i], null,
                            RemoteProperty.getVersionLocale(), null};
                    //向服务发出请求,取得结果集合（用户值对象UserInfo的集合）
                    info = (IBAHolderIfc) IBAUtility.invokeServiceMethod(
                            "IBAValueService", "refreshAttributeContainer",
                            classes, objects);
                    
                }
                catch (QMRemoteException remoteexception)
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                    		PartDesignViewRB.REFRESH_ATTRIBUTECONTAINER_FAILED,null);
                    showMessage(message, exceptionTitle);
                    message = null;
                    remoteexception.printStackTrace();
                }
			}
			
			//获得目标零部件的属性容器
            DefaultAttributeContainer defaultattributecontainer 
                = (DefaultAttributeContainer) info.getAttributeContainer();
            //源零部件将添加给目标零部件的事物特性属性定义列表
            List list= attrpanel.get_Added_attrdef();
            //源零部件将添加给目标零部件的事物特性属性定义的数组
            applydef = new AttributeDefDefaultView[list.size()];
			for(int len=0;len<list.size();len++)
			{
				applydef[len]=(AttributeDefDefaultView)list.get(len);				
			}

			//目标零部件的所有属性定义
			AttributeDefDefaultView[] topartdef=defaultattributecontainer.getAttributeDefinitions();
			//如果目标零部件没有属性定义
			if(topartdef==null||topartdef.length==0)
			{
				AbstractValueView[] aabvalue = defAttrcon.getAttributeValues();
				for(int k=0;k<aabvalue.length;k++)
				{
					defaultattributecontainer.addAttributeValue(aabvalue[k]);
				}
				
			}
			else//有属性定义
			{
				
				for(int h=0;h<applydef.length;h++)
				{
					boolean isClassNode=false;
					AbstractValueView[] absValueView=defAttrcon.getAttributeValues(applydef[h]);
					/////081027 begin
					for(int ii=0;ii<absValueView.length;ii++){
						try {
							if(isClassNode(absValueView[ii])){
								isClassNode=true;
								break;
							}
						} catch (QMException e) {
							e.printStackTrace();
						}
					}
					
					if(!isClassNode)
					{
						//目标零部件有该属性
						if(valueExists(topartdef,applydef[h]))
						{
							
							//delete
							AbstractValueView[] partvalue0=defAttrcon.getAttributeValues(applydef[h]);
							for(int m=0;m<partvalue0.length;m++)
							{
								//删除目标零部件属性容器中含有的源零部件要添加的属性定义的所有属性值
								defaultattributecontainer.deleteAttributeValue(partvalue0[m]);
							}

							//添加
							AbstractValueView[] partvalue=defAttrcon.getAttributeValues(applydef[h]);
							for(int g=0;g<partvalue.length;g++)
							{
								defaultattributecontainer.addAttributeValue(partvalue[g]);
								
								
							}
							beadded.put((Object)applydef[h], f);
						}
						else
						{
							AbstractValueView[] partvalue1=defAttrcon.getAttributeValues(applydef[h]);
							for(int k=0;k<partvalue1.length;k++)
							{
								defaultattributecontainer.addAttributeValue(partvalue1[k]);
							}
							beadded.put((Object)applydef[h], t);
						}
					}//end
					
					

				}
			}
			part_con_map.put((QMPartIfc)suc_obj[i], defaultattributecontainer);
		
		}//目标零部件循环结束
	}
		
	  /**
	   * 判断属性dingyi是否存在于给定的属性dingyi数组中。
	   * @param abstractvalueview AbstractValueView 属性定义。
	   * @see AbstractValueView
	   * @param values AbstractValueView[] 给定的属性定义
	   * @return boolean 返回值为true属性性值存在，为false属性值不存在。
	   */
	  protected boolean valueExists(AttributeDefDefaultView[] values,AttributeDefDefaultView abstractvalueview)
	  {
	    for(int i=0;i<values.length;i++)
	    {
	    	AttributeDefDefaultView abstractvalueview1 = values[i];
	      if(abstractvalueview.isPersistedObjectEqual(abstractvalueview1))
	      {
	        return true;
	      }
	      if(abstractvalueview.getBsoID() != null && abstractvalueview.getBsoID() == abstractvalueview1.getBsoID())
	      {
	        return true;
	      }
	    }
	    return false;
	  }
		
	
	/**
	 * 保存数据
	 * @throws QMException 
	 *
	 */
	private void save() 
	{
		for(int i=0;i<suc_obj.length;i++)
		{
	        boolean flag = true;
	        IBAHolderIfc ibaholder = null;
	        AttributeContainer attributecontainer = null;

	        if (flag)
	        {
	            attributecontainer = (DefaultAttributeContainer)part_con_map.get((QMPartIfc)suc_obj[i]);	            
	            if (attributecontainer == null)
	            {
	                flag = false;
	            }
	        }
	        if(flag)
	        {
	        	ibaholder = (IBAHolderIfc)suc_obj[i];
	        	if(ibaholder==null)
	        	{
	        		flag=false;
	        	}
	        }
	        if (flag)//零部件值对象不为空且它的状态
	        {
	            try
	            {
	                ibaholder.setAttributeContainer(attributecontainer);
	                if (PersistHelper.isPersistent(ibaholder))//对象被持久化
	                {
	                    flag = updateIBAHolder(ibaholder,(DefaultAttributeContainer)attributecontainer);
	                }
	                else//对象没有被持久化
	                {
	                
	          		    Class[] paraClass = {QMPartIfc.class};
	      		        Object[] para = {(QMPartIfc)ibaholder};
	      		        ibaholder=(IBAHolderIfc)  helper.request("PersistService","storeValueInfo", paraClass, para);
	                }
	            }
	            catch (QMException qmexception)
	            {
	            	Object[] params = {((QMPartIfc)suc_obj[i]).getPartNumber()
                            +"-"+ ((QMPartIfc)suc_obj[i]).getPartName()};
                    String message = QMMessage.getLocalizedMessage(
                            RESOURCE,
                            PartDesignViewRB.STORE_VALUEINFO_FAILED,
                            params);
                    showMessage(message, exceptionTitle);
	                qmexception.printStackTrace();
	                flag = false;
	            }
	        }
		}
	}
	
    /**
     * 更新事物特性持有者。
     * @param ibaholder IBAHolderIfc 事物特性持有者。
     * @param attributecontainer 要更新的属性容器
     * @throws ServiceLocatorException
     * @return boolean 成功标识。true为成功，false为失败。
     */
    private  boolean updateIBAHolder(IBAHolderIfc ibaholder,DefaultAttributeContainer attributecontainer)
    {
    	boolean flag = true;
        try
        {
  		    Class[] paraClass0 = {BaseValueIfc.class};
		    Object[] para0 = {ibaholder};
		    ibaholder=(IBAHolderIfc)  helper.request("PersistService","refreshInfo", paraClass0, para0);
	
		    
        	//刷新给定事物持有者的属性容器
        	 Class[] paraClass1 = {IBAHolderIfc.class,Object.class,Locale.class,MeasurementSystemDefaultView.class};
     		Object[] para1 = {ibaholder,null,null,null};
     		ibaholder = (IBAHolderIfc)helper.request("IBAValueService","refreshAttributeContainer", paraClass1, para1);
     		//获得属性容器
     		DefaultAttributeContainer attributecontainer1 =(DefaultAttributeContainer) ibaholder.
                    getAttributeContainer().clone();
            AbstractValueView[] absValue = null;
            List list = new ArrayList();
            for(int q=0;q<applydef.length;q++)
            {
            	AbstractValueView[] absValue1 = defAttrcon.getAttributeValues(applydef[q]);
            	for(int p=0;p<absValue1.length;p++)
            	{
            		list.add(absValue1[p]);
            	}
            }
            absValue = new AbstractValueView[list.size()];
            //应用所有的事物特性中是否有分类节点的标志
            boolean classNodeFlag=false;
            //分类节点在应用所有的事物特性中的位置，如果没有则为-1
            int location=-1;
            for(int si=0;si<list.size();si++)
            {
            	absValue[si]=(AbstractValueView)list.get(si);
            	if(isClassNode(absValue[si])){
            		classNodeFlag=true;
            		location=si;
            	}
            }

            //更新属性容器(不包含分类)
            attributecontainer1=createNewValueAndSetContainer(location,absValue,attributecontainer1);
            ibaholder.setAttributeContainer(attributecontainer1);
            //fenleijiegou
//        	ClassificationStructDefaultView classificationStructDef = (ClassificationStructDefaultView)
//            ClassificationNodeLoader.getCachedObject(CLASSIFICATION_STRUCTURE);
            if(classNodeFlag)
            {
        		BaseValueIfc baseValueIfc = getClassNode(absValue[location]);
        		if (baseValueIfc!=null&&baseValueIfc instanceof ClassificationNodeIfc) {
        			ExtendAttrApplyAllHelper.addClassificationOfPart(ibaholder,(ClassificationNodeIfc)baseValueIfc,defAttrcon);
        		}
            	
            }
     		
  		    Class[] paraClass = {BaseValueIfc.class};
		    Object[] para = {(BaseValueIfc)ibaholder};
		    ibaholder=(IBAHolderIfc)  helper.request("PersistService","saveValueInfo", paraClass, para);
        }
        catch (QMException qmexception)
        {
        	Object[] params = {((QMPartIfc)ibaholder).getPartNumber()
                    +"-"+ ((QMPartIfc)ibaholder).getPartName()};
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    PartDesignViewRB.UPDATE_VALUEINFO_FAILED,
                    params);
            showMessage(message, exceptionTitle);
            qmexception.printStackTrace();
            flag = false;
        }
    	return flag;
    }
	
    /**
     *  更新没有属性值的属性容器
     * @param valueView 用于更新的属性值集合
     * @param container 被更新的属性容器
     * @return
     * @throws QMException
     */
    private DefaultAttributeContainer createNewValueAndSetContainer(int location,AbstractValueView[] absValue,
            DefaultAttributeContainer container) throws QMException
    {

    	
    	//目标零部件属性不为空
    	if(!beadded.isEmpty())
    	{
        	for(int i = 0; i < applydef.length; i++)
        	{
        		//目标属性零部件中有源零部件要添加的属性
          	    if(((String)beadded.get(applydef[i]))=="0")
          	    {
          	    	//获得要删除的属性值
        			AbstractValueView[] partvalue=container.getAttributeValues(applydef[i]);
        		    for(int l=0;l<partvalue.length;l++)
      			    {
             		    //删除目标零部件属性容器中所包含的源零部件的属性
        				container.deleteAttributeValue(partvalue[l]);
      			    }
          	    }
        	}
    	}

  	    //向目标零部件属性容器中添加源零部件的属性
        for (int i = 0; i < absValue.length; i++)
        {

        	if(location>= 0&&location==i ){
        		continue;
        	}

          if (absValue[i] instanceof BooleanValueDefaultView)//boolean
          {
            BooleanValueDefaultView booleanvaluedefaultview = (BooleanValueDefaultView) absValue[i];
            BooleanValueDefaultView booleanvaluedefaultview1 = new
                BooleanValueDefaultView( (BooleanDefView)
                                        booleanvaluedefaultview.getDefinition(),
                                        booleanvaluedefaultview.isValue());
            if (booleanvaluedefaultview.getReferenceValueDefaultView() != null)
            {
              booleanvaluedefaultview1.setReferenceValueDefaultView(
                  booleanvaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(booleanvaluedefaultview1);
          }
          else
          if (absValue[i] instanceof FloatValueDefaultView)//float
          {
            FloatValueDefaultView floatvaluedefaultview = (FloatValueDefaultView) absValue[i];
            FloatValueDefaultView floatvaluedefaultview1 = new
                FloatValueDefaultView( (FloatDefView)
                                      floatvaluedefaultview.getDefinition(),
                                      floatvaluedefaultview.getValue(),
                                      floatvaluedefaultview.getPrecision());
            if (floatvaluedefaultview.getReferenceValueDefaultView() != null)
            {
              floatvaluedefaultview1.setReferenceValueDefaultView(
                  floatvaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(floatvaluedefaultview1);
          }
          else
          if (absValue[i] instanceof IntegerValueDefaultView)//整型
          {
            IntegerValueDefaultView integervaluedefaultview = (IntegerValueDefaultView) absValue[i];
            IntegerValueDefaultView integervaluedefaultview1 = new
                IntegerValueDefaultView( (IntegerDefView)
                                        integervaluedefaultview.getDefinition(),
                                        integervaluedefaultview.getValue());
            if (integervaluedefaultview.getReferenceValueDefaultView() != null)
            {
              integervaluedefaultview1. setReferenceValueDefaultView(
                  integervaluedefaultview. getReferenceValueDefaultView());
            }
            container.addAttributeValue(integervaluedefaultview1);
          }
          else
          if (absValue[i] instanceof RatioValueDefaultView)//比率
          {
            RatioValueDefaultView ratiovaluedefaultview = (RatioValueDefaultView) absValue[i];
            RatioValueDefaultView ratiovaluedefaultview1 = new
                RatioValueDefaultView( (RatioDefView)
                                      ratiovaluedefaultview.getDefinition(),
                                      ratiovaluedefaultview.getValue(),
                                      ratiovaluedefaultview.getDenominator());
            if (ratiovaluedefaultview.getReferenceValueDefaultView() != null)
            {
              ratiovaluedefaultview1.setReferenceValueDefaultView(ratiovaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(ratiovaluedefaultview1);
          }
          else
          if (absValue[i] instanceof ReferenceValueDefaultView)//参考
          {
            ReferenceValueDefaultView referencevaluedefaultview1 = (ReferenceValueDefaultView)absValue[i];
            String referenceID = referencevaluedefaultview1.getLiteIBAReferenceable().getReferenceID();
            if(referencevaluedefaultview1.getLiteIBAReferenceable() != null && referenceID.indexOf("Ranking") == -1)
            {
              ReferenceValueDefaultView referencevaluedefaultview2 =
                  new ReferenceValueDefaultView((ReferenceDefView)referencevaluedefaultview1.getDefinition(),
                                                referencevaluedefaultview1.getLiteIBAReferenceable(),
                                                referencevaluedefaultview1.getModifyTime());
              if(referenceID.indexOf("BusinessEntity") == -1)
              {
                  container.setConstraintParameter("CSM");
              }
              referencevaluedefaultview2.setState(3);
              container.addAttributeValue(referencevaluedefaultview2);
              QSFDebug.trace(referencevaluedefaultview2.getDefinition().getDisplayName());
            }
          }
          else
          if (absValue[i] instanceof StringValueDefaultView)//字符
          {
            StringValueDefaultView stringvaluedefaultview = (StringValueDefaultView) absValue[i];
            StringValueDefaultView stringvaluedefaultview1 = new
                StringValueDefaultView( (StringDefView)stringvaluedefaultview.getDefinition(),
                                       stringvaluedefaultview.getValue());
            if (stringvaluedefaultview.getReferenceValueDefaultView() != null)
            {
              stringvaluedefaultview1.setReferenceValueDefaultView(
                  stringvaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(stringvaluedefaultview1);
          }
          else
          if (absValue[i] instanceof TimestampValueDefaultView)//时间戳
          {
            TimestampValueDefaultView timestampvaluedefaultview = (TimestampValueDefaultView)absValue[i];
            TimestampValueDefaultView timestampvaluedefaultview1 = new
                TimestampValueDefaultView( (TimestampDefView)timestampvaluedefaultview.getDefinition(),
                                          timestampvaluedefaultview.getValue());
            if (timestampvaluedefaultview.getReferenceValueDefaultView() != null)
            {
              timestampvaluedefaultview1.setReferenceValueDefaultView(
                  timestampvaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(timestampvaluedefaultview1);
          }
          else
          if (absValue[i] instanceof UnitValueDefaultView)//单位
          {
            UnitValueDefaultView unitvaluedefaultview = (UnitValueDefaultView) absValue[i];
            UnitValueDefaultView unitvaluedefaultview1 = new
                UnitValueDefaultView( (UnitDefView) unitvaluedefaultview.getDefinition(),
                                     unitvaluedefaultview.getValue(),
                                     unitvaluedefaultview.getPrecision());
            if (unitvaluedefaultview.getReferenceValueDefaultView() != null)
            {
              unitvaluedefaultview1.setReferenceValueDefaultView(
                  unitvaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(unitvaluedefaultview1);
          }
          else
          if (absValue[i] instanceof URLValueDefaultView)//url
          {
            URLValueDefaultView urlvaluedefaultview = ( URLValueDefaultView) absValue[i];
            URLValueDefaultView urlvaluedefaultview1 = new
                URLValueDefaultView( (URLDefView) urlvaluedefaultview.getDefinition(),
                                    urlvaluedefaultview.getValue(),urlvaluedefaultview.getDescription());
            if (urlvaluedefaultview.getReferenceValueDefaultView() != null)
            {
              urlvaluedefaultview1. setReferenceValueDefaultView(
                  urlvaluedefaultview.getReferenceValueDefaultView());
            }
            container.addAttributeValue(urlvaluedefaultview1);
          }
        }
        return container;
     }
    
	
    /**
     * 设置添加成功的零部件
     * @return
     */
    private void setSuc_obj()
    {
    	List iterator =samepanel.getSucPart();
        //添加成功的零部件集合
    	suc_obj = new QMPartIfc[samepanel.getSucPart().size()];
    	for(int i=0;i<iterator.size();i++)
    	{
    		suc_obj[i]=(QMPartIfc)iterator.get(i);
    	}
    }

	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成方法存根
		
	}
    

    /**
	 * 判断给定属性值是否是分类节点
	 * 
	 * @param abstractValueView
	 * @return
	 * @throws QMException
	 */
	private boolean isClassNode(AbstractValueView abstractValueView)
			throws QMException {
		boolean flag = false;
		BaseValueIfc baseValueIfc = getClassNode(abstractValueView);
		if (baseValueIfc!=null&&baseValueIfc instanceof ClassificationNodeIfc) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断给定属性值是否是分类节点
	 * 
	 * @param abstractValueView
	 * @return
	 * @throws QMException
	 */
	private BaseValueIfc getClassNode(AbstractValueView abstractValueView)
			throws QMException {
		BaseValueIfc baseValueIfc = null;
		if (abstractValueView instanceof ReferenceValueDefaultView) {
			ReferenceValueDefaultView view = (ReferenceValueDefaultView) abstractValueView;
			// 分类节点
			DefaultLiteIBAReferenceable defLite = (DefaultLiteIBAReferenceable) view
					.getLiteIBAReferenceable();
			String classNodeBsoId = defLite.getBsoID();
			RequestHelper requestHelper = new RequestHelper();
			Class[] theClass = { String.class };
			Object[] theObjs = { classNodeBsoId };
			baseValueIfc = (BaseValueIfc) requestHelper.request(
					"PersistService", "refreshInfo", theClass, theObjs);
		}
		return baseValueIfc;
	}
    
    /**
     * 获取分类节点上的所有事物特性。
     * @param cnDefaultView
     * @return
     */
    public List getAttrDefOfClassificationNode(
            ClassificationNodeIfc classificationNodeIfc)
    {
        List attrOfNodelist = new ArrayList();
    	//刷新给定事物持有者的属性容器 muyp 1028 begin
   	 Class[] paraClass1 = {IBAHolderIfc.class,Object.class,Locale.class,MeasurementSystemDefaultView.class};
		Object[] para1 = {(IBAHolderIfc)classificationNodeIfc,null,null,null};
		try {
			classificationNodeIfc = (ClassificationNodeIfc)helper.request("IBAValueService","refreshAttributeContainer", paraClass1, para1);
		} catch (QMException e) {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
            		PartDesignViewRB.REFRESH_ATTRIBUTECONTAINER_FAILED,null);
            showMessage(message, exceptionTitle);
            message = null;
			e.printStackTrace();
		}//end 1028
        // 分类节点含有的容器。
        final DefaultAttributeContainer defAttrCont = (DefaultAttributeContainer) classificationNodeIfc
                .getAttributeContainer();
        
        // 分类节点含有的事物特性。
        final AttributeDefDefaultView attrDefDefView[] = defAttrCont
                .getAttributeDefinitions();
        
        // 将分类节点含有的事物特性循环添加到attrOfNodelist上。
        for (int i1 = 0; i1 < attrDefDefView.length; i1++)
        {
            attrOfNodelist.add(attrDefDefView[i1]);
        }
        return attrOfNodelist;
    }
 
    /**
     * 判断属性容器里面的事物特性是否包分类节点上的事物特性，返回包含的事物特性的集合。
     * @param defAttrContainer 属性容器
     * @param attrOfNodelist 分类节点上包含的事物特性
     */
    public List getTogetherAttribute(
            DefaultAttributeContainer defAttrContainer, List attrOfNodelist)
    {
        List attrDefList = new ArrayList();
        // 当前容器已含有的事物特性集合。
        final AttributeDefDefaultView aAttributeDefDV2[] = defAttrContainer
                .getAttributeDefinitions();
        final boolean aflag[] = new boolean[aAttributeDefDV2.length];
        for (int j1 = 0; j1 < aflag.length; j1++)
        {
            aflag[j1] = false;
        }
        for (int k1 = 0; k1 < attrOfNodelist.size(); k1++)
        {
            // 分类节点上含有的事物特性。
            final AttributeDefDefaultView attributeDefDV = (AttributeDefDefaultView) attrOfNodelist
                    .get(k1);
            for (int j2 = 0; j2 < aAttributeDefDV2.length; j2++)
            {
                if(attributeDefDV.isPersistedObjectEqual(aAttributeDefDV2[j2])
                        && !aflag[j2])
                {
                    aflag[j2] = true;
                }
            }
        }
        for (int i2 = 0; i2 < aflag.length; i2++)
        {
            // 将当前容器含有分类节点上的事物特性添加到集合中。
            if(aflag[i2])
            {
//            	attrDefList.add(aAttributeDefDV2[i2]);
            	ExtendAttrApplyAllHelper.addDefToCollection(attrDefList, aAttributeDefDV2[i2]);
            }
        }
        return attrDefList;
    }
    
    /**
     * 显示错误信息框。
     * @param message String 显示的错误信息。
     * @param title String 信息框的标题。
     */
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.WARNING_MESSAGE);
        
    }
    
    /**
     * 返回应用所有成功的零部件
     * @return
     */
    public BaseValueIfc[] getApplySucObj()
    {
    	return suc_obj;
    }

    
}//class
