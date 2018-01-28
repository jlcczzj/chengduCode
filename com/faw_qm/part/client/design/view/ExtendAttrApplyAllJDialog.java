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
   * ��������Ӧ�����н���.
   * (������ӷ���ڵ�֮ǰ)
   * @author ������
   *CR1 2009/06/04 ��� ԭ��:��������ڲ��ܱ�������TD-2162
   * ����:��JDialog��ΪJFrame�Ϳ������������ڣ�Ϊ�˱���ά����������ȻΪPartDialog, ��ʵ������ΪJFrame
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
    
    //��ť���(ȷ����ȡ����Ӧ������) 
    private JPanel buttonJP = new JPanel();

    //Ŀ���㲿����ʾ���
    private ApplyAllSameJPanel samepanel = new ApplyAllSameJPanel();
    
    //��������ѡ�����
    private ApplyAttrToOthersPartsJPanel attrpanel = null;
    
    //��ӳɹ����㲿���ļ���*/
    private BaseValueIfc[] suc_obj=null;
    
    //���ֹ���
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    //��ȷ������ť
    private JButton okJB = new JButton();

    //��ȡ������ť
    private JButton cancelJB = new JButton();
    
    //��Ӧ�����С���ť
    private JButton applyJButton = new JButton();
    
    //��Ӧ�����С���ť�Ƿ񱻰��±��
    private boolean applyflag = false;
    
    private IBAContainerEditor IBACon;

	private JLabel ibaAttr= new JLabel();
    
	//����Դ�㲿��ֵ����-����������HashMap
	private HashMap part_con_map = new HashMap();
	//���ڱ����Դ�ļ�·��
    protected static final String RESOURCE
            = "com.faw_qm.part.client.design.util.PartDesignViewRB";
    /**Դ�㲿������������*/
	DefaultAttributeContainer defAttrcon = null;
	/**���������ֵ���Ƿ������Ŀ���㲿����booleanֵ�Ľ�ֵ��*/
	private HashMap beadded = new HashMap();//
	private String t ="1";
	private String f="0";
	/**Դ�㲿����ѡ�еĽ�ҪӦ�õ����Զ���*/
	private AttributeDefDefaultView[] applydef =null;
    /**�쳣��Ϣ����:"����"*/
    static String exceptionTitle = QMMessage.getLocalizedMessage(RESOURCE,
    		PartDesignViewRB.MESSAGE, null);
    
	

    
    /**
     * ���캯��
     * @param frame JFrame ��Ʒ������������
     * @param frame1 JFrame ������
     * @param part QMPartIfc Դ�㲿��ֵ����ӿ�
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
		//��ť���
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
        //Ӧ�����й����������
		this.getContentPane().add(samepanel,
				new GridBagConstraints(0, 0, 2, 1, 0.0, 0.5,
		                GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
		//��������ѡ���������
		this.getContentPane().add(attrpanel,
                new GridBagConstraints(0, 2, 2, 1, 1.0, 0.5,
                        GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5,8,10,105),0,0));
		
		localize();
	}//jbInit


    /*
     *���ػ���Ϣ��
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
	 * ִ�С�ȷ������ť������
	 * @param e ��ť���������¼���
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
     * ִ�С�ȡ������ť������
     * @param e ��ť���������¼���
     */
	protected void cancelJButton_actionPerformed(ActionEvent e)
    {
		this.dispose();
    }

	/**
	 * ִ�С�Ӧ�����С���ť��������ԭ�㲿��ѡ��������Ӧ�õ�Ŀ���㲿����
	 * ���ǲ��رմ���,�����浽���ݿ⡣
	 * @param e ��ť���������¼���
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
	 * Ӧ������
	 * @throws IBAConstraintException 
	 *
	 */
	private void applyToAll() throws IBAConstraintException
	{
		/*������������TABҳ��Ӧ�����С���Ŀ���㲿������ѭ������
		 * ����ѡ���HashMap
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
			//���Ŀ���㲿��������������������Ϊ�գ���ˢ��Ŀ���㲿����������������������
			if(((DefaultAttributeContainer)info.getAttributeContainer())==null)
			{
				try
                {
                    //Ҫ���õķ����еĲ�������ļ���
                    Class[] classes = {IBAHolderIfc.class, Object.class,
                            Locale.class, MeasurementSystemDefaultView.class};
                    //Ҫ���õķ����еĲ����ļ���
                    Object[] objects = {(IBAHolderIfc) suc_obj[i], null,
                            RemoteProperty.getVersionLocale(), null};
                    //����񷢳�����,ȡ�ý�����ϣ��û�ֵ����UserInfo�ļ��ϣ�
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
			
			//���Ŀ���㲿������������
            DefaultAttributeContainer defaultattributecontainer 
                = (DefaultAttributeContainer) info.getAttributeContainer();
            //Դ�㲿������Ӹ�Ŀ���㲿���������������Զ����б�
            List list= attrpanel.get_Added_attrdef();
            //Դ�㲿������Ӹ�Ŀ���㲿���������������Զ��������
            applydef = new AttributeDefDefaultView[list.size()];
			for(int len=0;len<list.size();len++)
			{
				applydef[len]=(AttributeDefDefaultView)list.get(len);				
			}

			//Ŀ���㲿�����������Զ���
			AttributeDefDefaultView[] topartdef=defaultattributecontainer.getAttributeDefinitions();
			//���Ŀ���㲿��û�����Զ���
			if(topartdef==null||topartdef.length==0)
			{
				AbstractValueView[] aabvalue = defAttrcon.getAttributeValues();
				for(int k=0;k<aabvalue.length;k++)
				{
					defaultattributecontainer.addAttributeValue(aabvalue[k]);
				}
				
			}
			else//�����Զ���
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
						//Ŀ���㲿���и�����
						if(valueExists(topartdef,applydef[h]))
						{
							
							//delete
							AbstractValueView[] partvalue0=defAttrcon.getAttributeValues(applydef[h]);
							for(int m=0;m<partvalue0.length;m++)
							{
								//ɾ��Ŀ���㲿�����������к��е�Դ�㲿��Ҫ��ӵ����Զ������������ֵ
								defaultattributecontainer.deleteAttributeValue(partvalue0[m]);
							}

							//���
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
		
		}//Ŀ���㲿��ѭ������
	}
		
	  /**
	   * �ж�����dingyi�Ƿ�����ڸ���������dingyi�����С�
	   * @param abstractvalueview AbstractValueView ���Զ��塣
	   * @see AbstractValueView
	   * @param values AbstractValueView[] ���������Զ���
	   * @return boolean ����ֵΪtrue������ֵ���ڣ�Ϊfalse����ֵ�����ڡ�
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
	 * ��������
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
	        if (flag)//�㲿��ֵ����Ϊ��������״̬
	        {
	            try
	            {
	                ibaholder.setAttributeContainer(attributecontainer);
	                if (PersistHelper.isPersistent(ibaholder))//���󱻳־û�
	                {
	                    flag = updateIBAHolder(ibaholder,(DefaultAttributeContainer)attributecontainer);
	                }
	                else//����û�б��־û�
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
     * �����������Գ����ߡ�
     * @param ibaholder IBAHolderIfc �������Գ����ߡ�
     * @param attributecontainer Ҫ���µ���������
     * @throws ServiceLocatorException
     * @return boolean �ɹ���ʶ��trueΪ�ɹ���falseΪʧ�ܡ�
     */
    private  boolean updateIBAHolder(IBAHolderIfc ibaholder,DefaultAttributeContainer attributecontainer)
    {
    	boolean flag = true;
        try
        {
  		    Class[] paraClass0 = {BaseValueIfc.class};
		    Object[] para0 = {ibaholder};
		    ibaholder=(IBAHolderIfc)  helper.request("PersistService","refreshInfo", paraClass0, para0);
	
		    
        	//ˢ�¸�����������ߵ���������
        	 Class[] paraClass1 = {IBAHolderIfc.class,Object.class,Locale.class,MeasurementSystemDefaultView.class};
     		Object[] para1 = {ibaholder,null,null,null};
     		ibaholder = (IBAHolderIfc)helper.request("IBAValueService","refreshAttributeContainer", paraClass1, para1);
     		//�����������
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
            //Ӧ�����е������������Ƿ��з���ڵ�ı�־
            boolean classNodeFlag=false;
            //����ڵ���Ӧ�����е����������е�λ�ã����û����Ϊ-1
            int location=-1;
            for(int si=0;si<list.size();si++)
            {
            	absValue[si]=(AbstractValueView)list.get(si);
            	if(isClassNode(absValue[si])){
            		classNodeFlag=true;
            		location=si;
            	}
            }

            //������������(����������)
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
     *  ����û������ֵ����������
     * @param valueView ���ڸ��µ�����ֵ����
     * @param container �����µ���������
     * @return
     * @throws QMException
     */
    private DefaultAttributeContainer createNewValueAndSetContainer(int location,AbstractValueView[] absValue,
            DefaultAttributeContainer container) throws QMException
    {

    	
    	//Ŀ���㲿�����Բ�Ϊ��
    	if(!beadded.isEmpty())
    	{
        	for(int i = 0; i < applydef.length; i++)
        	{
        		//Ŀ�������㲿������Դ�㲿��Ҫ��ӵ�����
          	    if(((String)beadded.get(applydef[i]))=="0")
          	    {
          	    	//���Ҫɾ��������ֵ
        			AbstractValueView[] partvalue=container.getAttributeValues(applydef[i]);
        		    for(int l=0;l<partvalue.length;l++)
      			    {
             		    //ɾ��Ŀ���㲿��������������������Դ�㲿��������
        				container.deleteAttributeValue(partvalue[l]);
      			    }
          	    }
        	}
    	}

  	    //��Ŀ���㲿���������������Դ�㲿��������
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
          if (absValue[i] instanceof IntegerValueDefaultView)//����
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
          if (absValue[i] instanceof RatioValueDefaultView)//����
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
          if (absValue[i] instanceof ReferenceValueDefaultView)//�ο�
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
          if (absValue[i] instanceof StringValueDefaultView)//�ַ�
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
          if (absValue[i] instanceof TimestampValueDefaultView)//ʱ���
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
          if (absValue[i] instanceof UnitValueDefaultView)//��λ
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
     * ������ӳɹ����㲿��
     * @return
     */
    private void setSuc_obj()
    {
    	List iterator =samepanel.getSucPart();
        //��ӳɹ����㲿������
    	suc_obj = new QMPartIfc[samepanel.getSucPart().size()];
    	for(int i=0;i<iterator.size();i++)
    	{
    		suc_obj[i]=(QMPartIfc)iterator.get(i);
    	}
    }

	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɷ������
		
	}
    

    /**
	 * �жϸ�������ֵ�Ƿ��Ƿ���ڵ�
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
	 * �жϸ�������ֵ�Ƿ��Ƿ���ڵ�
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
			// ����ڵ�
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
     * ��ȡ����ڵ��ϵ������������ԡ�
     * @param cnDefaultView
     * @return
     */
    public List getAttrDefOfClassificationNode(
            ClassificationNodeIfc classificationNodeIfc)
    {
        List attrOfNodelist = new ArrayList();
    	//ˢ�¸�����������ߵ��������� muyp 1028 begin
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
        // ����ڵ㺬�е�������
        final DefaultAttributeContainer defAttrCont = (DefaultAttributeContainer) classificationNodeIfc
                .getAttributeContainer();
        
        // ����ڵ㺬�е��������ԡ�
        final AttributeDefDefaultView attrDefDefView[] = defAttrCont
                .getAttributeDefinitions();
        
        // ������ڵ㺬�е���������ѭ����ӵ�attrOfNodelist�ϡ�
        for (int i1 = 0; i1 < attrDefDefView.length; i1++)
        {
            attrOfNodelist.add(attrDefDefView[i1]);
        }
        return attrOfNodelist;
    }
 
    /**
     * �ж�����������������������Ƿ������ڵ��ϵ��������ԣ����ذ������������Եļ��ϡ�
     * @param defAttrContainer ��������
     * @param attrOfNodelist ����ڵ��ϰ�������������
     */
    public List getTogetherAttribute(
            DefaultAttributeContainer defAttrContainer, List attrOfNodelist)
    {
        List attrDefList = new ArrayList();
        // ��ǰ�����Ѻ��е��������Լ��ϡ�
        final AttributeDefDefaultView aAttributeDefDV2[] = defAttrContainer
                .getAttributeDefinitions();
        final boolean aflag[] = new boolean[aAttributeDefDV2.length];
        for (int j1 = 0; j1 < aflag.length; j1++)
        {
            aflag[j1] = false;
        }
        for (int k1 = 0; k1 < attrOfNodelist.size(); k1++)
        {
            // ����ڵ��Ϻ��е��������ԡ�
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
            // ����ǰ�������з���ڵ��ϵ�����������ӵ������С�
            if(aflag[i2])
            {
//            	attrDefList.add(aAttributeDefDV2[i2]);
            	ExtendAttrApplyAllHelper.addDefToCollection(attrDefList, aAttributeDefDV2[i2]);
            }
        }
        return attrDefList;
    }
    
    /**
     * ��ʾ������Ϣ��
     * @param message String ��ʾ�Ĵ�����Ϣ��
     * @param title String ��Ϣ��ı��⡣
     */
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.WARNING_MESSAGE);
        
    }
    
    /**
     * ����Ӧ�����гɹ����㲿��
     * @return
     */
    public BaseValueIfc[] getApplySucObj()
    {
    	return suc_obj;
    }

    
}//class
