package com.faw_qm.part.client.design.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;

/**
 * <p>Title: ��������Ӧ�����жԻ���</p>
 * <p>Description: ��Դ�㲿���Ļ�������Ӧ�õ�Ŀ���㲿���У�
 * ��ҪӦ�õ�Դ�㲿���Ļ������Կ��������ļ��н������á�</p>
 * @author ������
 *CR1 2009/06/04 ��� ԭ��:��������ڲ��ܱ�������TD-2162
 * ����:��JDialog��ΪJFrame�Ϳ������������ڣ�Ϊ�˱���ά����������ȻΪPartDialog, ��ʵ������ΪJFrame
 *
 */
//CR1 begin
//public class BaseAttrApplyAllJDialog extends JDialog
public class BaseAttrApplyAllJDialog extends JFrame//CR1end
{
	/** ���л�ID	 */
	private static final long serialVersionUID = 1L;
	// ����������Ӧ��������ͬ���ֵ����
	private ApplyAllSameJPanel applyAllSameJPanel = new ApplyAllSameJPanel();
	// �����ͬ���ֵ����
	private JPanel addSameJPanel = new JPanel();
	// ���û������������б�����
	private JPanel choiceAttrJPanel = new JPanel();
	// ����Ӧ�����С�ȷ����ȡ����ť�����
	private JPanel applyButtonJPanel = new JPanel();
	// ����������Ŀ�ı�ǩ
	private JLabel baseAttrJLabel = new JLabel();
	// �������������б��		
	private JComboBox[] jComboBox;
	
	// Ӧ�����а�ť
	private JButton applyAllJButton = new JButton();
	// ȷ����ť
	private JButton okJButton = new JButton();
	// ȡ����ť
	private JButton cancelJButton = new JButton();
	// ���岼��
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private GridBagLayout addSameGridBagLayout = new GridBagLayout();
	private GridBagLayout choiceAttrGridBagLayout = new GridBagLayout();
	private GridBagLayout applyButtonGridBagLayout = new GridBagLayout();
	//Դ�㲿���㲿��ֵ����(��������еõ���)
	private QMPartIfc partIfc1 ;
	//Ŀ���㲿������
	private BaseValueIfc[] suc_obj = null;
	//��Դ�ļ�·��
    private static String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";
    private ResourceBundle resourcebundle =
        ResourceBundle.getBundle(RESOURCE,
                                 RemoteProperty.getVersionLocale());
    //private String[] s = {"", "viewName", "defaultUnit", "location", "producedBy", "partType", "lifeCycleState"};
    private String[] s=null;
	//Դ�㲿�����Զ���ļ���
	private int count = 0;	
	private String[] attr_value;
    private Vector attrs=new Vector();
    
	/**
	 * ���캯��
	 */
	public BaseAttrApplyAllJDialog(QMPartIfc part,Component component)
	{
//		super((JFrame)component,true);//CR1
//		//�����ã����𻷾�ע��begin
//		java.util.Properties prop=new Properties();
//        FileInputStream fis = null;
//		try
//		{
//			fis = new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//			prop.load(fis);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//        String s1=prop.getProperty( "part.apply");
//        //end
        String s1=RemoteProperty.getProperty("part.apply");
        StringTokenizer st=new StringTokenizer(s1,",");
        int j=st.countTokens();			  
        s=new String[j];
        for(int i=0;st.hasMoreTokens();i++)
        {
        	String nt=st.nextToken();
			s[i]=nt;
        }      
		partIfc1=part;
		count = s.length;
		attr_value=new String[count+1];
		jComboBox=new JComboBox[count];
		applyAllSameJPanel.setPartOfAttr(part);
		this.setAttr_value();
		try
		{			
			jbInit();
			localize();			
			this.partIfc1 = part;
			//ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
			DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(partIfc1);
			//��ö������� + ��ʶ
			String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {s};
			String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "BaseAttrApplyAll", params);
			if(partIfc1!=null)
			{				
				setTitle(title);
			}			
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * ������Է�����ͨ��get����
	 * @param obj
	 * @param s
	 * @return ������
	 */
	public String getAttrValue(Object obj,String s)
	{
		String attrMethod ="get"+s.substring(0,1).toUpperCase()+s.substring(1);
		 try
         {
             Class partClass = obj.getClass();
             Method method1 = partClass.getMethod(attrMethod, null);
             Object object = method1.invoke(obj, null);
             return object.toString();
         }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }				  
		return attrMethod;
	}
	
	/**
	 * ��ʼ��
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setLayout(gridBagLayout);
		this.setSize(new Dimension(650, 500));
//CR1 begin		this.setResizable(false);
		String iconImage = QMMessage.getLocalizedMessage(
                "com.faw_qm.part.client.main.util.QMProductManagerRB",
                QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());//CR 1end
		addSameJPanel.setLayout(addSameGridBagLayout);
		choiceAttrJPanel.setLayout(choiceAttrGridBagLayout);
		applyButtonJPanel.setLayout(applyButtonGridBagLayout);
		this.add(addSameJPanel, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.5,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		addSameJPanel.add(applyAllSameJPanel, new GridBagConstraints(0, 0, 1,
				1, 1.0, 0.5, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(choiceAttrJPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 6, 10, 110), 0, 0));
		choiceAttrJPanel.add(baseAttrJLabel, new GridBagConstraints(0, 1, 2, 1,
				1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 8, 10, 0), 0, 0));
		baseAttrJLabel.setText("choiceAttr");
		for(int i=0;i<count;i++)
		{
			jComboBox[i] = new JComboBox(attr_value);
		    choiceAttrJPanel.add(jComboBox[i], new GridBagConstraints(i, 2, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 4, 0, 0), 0, 0));
		}
		this.add(applyButtonJPanel, new GridBagConstraints(1, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 0, 15, 20), 0, 0));
		applyButtonJPanel.add(applyAllJButton, new GridBagConstraints(1, 0, 1,
				1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 1));
		applyAllJButton.setFont(new Font("Dialog", 0, 12));
		applyAllJButton.setBounds(new Rectangle(10, 5, 85, 20));
		applyAllJButton.setMaximumSize(new Dimension(65, 23));
		applyAllJButton.setMinimumSize(new Dimension(85, 23));
		applyAllJButton.setPreferredSize(new Dimension(83, 23));
		applyAllJButton.setText("applyAll");
		applyAllJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				applyAllJButton_actionPerformed(e);
			}
		});
		applyButtonJPanel.add(okJButton, new GridBagConstraints(2, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 6, 0, 0), 0, 1));
		okJButton.setFont(new Font("Dialog", 0, 12));
		okJButton.setBounds(new Rectangle(105, 5, 85, 20));
		okJButton.setMaximumSize(new Dimension(65, 23));
		okJButton.setMinimumSize(new Dimension(85, 23));
		okJButton.setPreferredSize(new Dimension(83, 23));
		okJButton.setText("ok");
		okJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				okJButton_actionPerformed(e);
			}
		});
		applyButtonJPanel.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 6, 0, 0), 0, 1));
		cancelJButton.setFont(new Font("Dialog", 0, 12));
		cancelJButton.setBounds(new Rectangle(200, 5, 85, 20));
		cancelJButton.setMaximumSize(new Dimension(65, 23));
		cancelJButton.setMinimumSize(new Dimension(85, 23));
		cancelJButton.setPreferredSize(new Dimension(83, 23));
		cancelJButton.setText("cancel");
		cancelJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancelJButton_actionPerformed(e);
			}
		});
	}
	
	 /**
     * Ӧ�����а�ť�Ķ�����
     * @param event  ActionEvent
     */
	protected void applyAllJButton_actionPerformed(ActionEvent event)
	{		
		setAim_part();
		try
		{
		save();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
     * ȷ����ť�Ķ�����
     * @param event  ActionEvent
     */
	protected void okJButton_actionPerformed(ActionEvent event)
	{
		setAim_part();
		try
		{
		save();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		dispose();
	}	
	
	/**
	 * Ӧ�����еı��淽������ȡԴ�㲿��������ֵ,
	 * ͨ��set�������־û�����Ŀ���㲿����
	 * @throws Exception
	 */
	private void save()
	throws Exception
	{		
		RequestHelper helper = new RequestHelper();
		Vector method=getPartAttributes();
		for(int i=0;i<suc_obj.length;i++)
		{
			QMPartIfc part=(QMPartIfc)suc_obj[i];
			for(Iterator ite=method.iterator();ite.hasNext();)
			{
				String s=(String)ite.next();
				String attrMethod ="set"+s.substring(0,1).toUpperCase()+s.substring(1);			
				Class partClass = part.getClass();
				if(s.equals("defaultUnit"))
				{
					Unit unit = Unit.toUnit(getAttrValue(partIfc1,s));
					Class[] cs={Unit.class};
					Object[] objs={unit};
		            Method method1 = partClass.getMethod(attrMethod, cs);
		            Object object = method1.invoke(part, objs);					 
				}
				else if(s.equals("producedBy"))
				{
					ProducedBy producedBy = ProducedBy.toProducedBy(getAttrValue(partIfc1,s));
					Class[] cs={ProducedBy.class};
					Object[] objs={producedBy};
		            Method method1 = partClass.getMethod(attrMethod, cs);
		            Object object = method1.invoke(part, objs);
				}
				else if(s.equals("partType"))
				{
					QMPartType partType=QMPartType.toQMPartType(getAttrValue(partIfc1,s));
					Class[] cs={QMPartType.class};
					Object[] objs={partType};
		            Method method1 = partClass.getMethod(attrMethod, cs);
		            Object object = method1.invoke(part, objs);			
				}
				else if(s.equals("lifeCycleState"))
				{
					LifeCycleState state =LifeCycleState.toLifeCycleState(getAttrValue(partIfc1,s));
					Class[] cs={LifeCycleState.class};
					Object[] objs={state};
		            Method method1 = partClass.getMethod(attrMethod, cs);
		            Object object = method1.invoke(part, objs);
				}
			}			 
		
		//ˢ��Ӧ�ú���㲿����������
		RefreshService.getRefreshService().dispatchRefresh(this, 1, part);
		//�־û�����
		 Class[] paraClass1 = {BaseValueIfc.class};
	 	 Object[] para1 = {part};
		 part=(QMPartIfc)  helper.request("PersistService","saveValueInfo", paraClass1, para1);
		}
	}
		
	/**
	 * �õ�Դ�㲿��������ֵ����
	 */
	private Vector getPartAttributes()
	{
		int size=jComboBox.length;
		for(int i=0;i<size;i++)
		{
			JComboBox jc=jComboBox[i];
			int k=jc.getSelectedIndex();
			if(k!=0)
			{
				attrs.addElement(s[k-1]);
			}
		}
		return attrs;
		
	}
	
	//���Ŀ���㲿��
	private void setAim_part()
	{
		List partList =applyAllSameJPanel.getSucPart();
        //��ӳɹ����㲿������    	
		suc_obj = new QMPartIfc[applyAllSameJPanel.getSucPart().size()];
    	for(int i=0;i<partList.size();i++)
    	{
    		suc_obj[i]=(QMPartIfc)partList.get(i);
    	}
	}
       
	/**
     * ����Ӧ�����гɹ����㲿��
     * @return
     */
    public BaseValueIfc[] getApplySucObj()
    {
    	return suc_obj;
    }
	
	 /**
     * ȡ����ť�Ķ�����
     * @param event  ActionEvent
     */
    protected void cancelJButton_actionPerformed(ActionEvent event)
    {
    	dispose();
    }
	
	 /**
	  * ���ػ���
	  */
	 protected void localize() 
	 {
		try
		{			
			baseAttrJLabel.setText(resourcebundle.getString("choiceAttr"));
			applyAllJButton.setText(resourcebundle.getString("applyAll"));
			okJButton.setText(resourcebundle.getString("ok"));
			cancelJButton.setText(resourcebundle.getString("cancel"));			
		}
		catch (Exception exception)
        {
            exception.printStackTrace();
        }
	 }	 
	 
	 /**
	  * ��ȡ��������ֵ�ļ���
	  */
	 public String[] getAttr_value()
	 {
		 return attr_value;
	 }
	 
	 protected void setAttr_value()
	 {
		 attr_value[0]="";
		 for(int i=0;i<s.length;i++)
		 {					 
			 if(s[i].equals("defaultUnit"))
			 {
				 attr_value[i+1]=resourcebundle.getString(s[i])+" : "+(Unit.toUnit(getAttrValue(partIfc1,s[i])).getDisplay()) ;					
			 }
			 else if(s[i].equals("producedBy"))
			 {
				 attr_value[i+1]=resourcebundle.getString(s[i])+" : "+(ProducedBy.toProducedBy(getAttrValue(partIfc1,s[i])).getDisplay());
			 }
			 else if(s[i].equals("partType"))
			 {
				 attr_value[i+1]=resourcebundle.getString(s[i])+" : "+(QMPartType.toQMPartType(getAttrValue(partIfc1,s[i])).getDisplay());
			 }
			 else if(s[i].equals("lifeCycleState"))
			 {
				 attr_value[i+1]=resourcebundle.getString(s[i])+" : "+(LifeCycleState.toLifeCycleState(getAttrValue(partIfc1,s[i])).getDisplay());
			 }
			 else
			 {
				 attr_value[i+1]=resourcebundle.getString(s[i])+" : "+getAttrValue(partIfc1, s[i]) ;
			 } 
		 }
	 }
}
