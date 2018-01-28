/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 20100119
 * CR1 2010/03/09 �촺Ӣ  ԭ�򣺲μ�TD����2883
 * CR2 2010/03/09 �촺Ӣ  ԭ�򣺲μ�TD����2881
 * CR3 2010/03/09 ����    ԭ��1.�μ�TD����2917��2920��
 *                             2.���ݡ������PDMϵͳ����滮_����ƽ(��ʾ�Ի������⼰����-�غ�Ӣ).doc����Ҫ���޸���ʾ�Ի���
 * cr4 20110725 ������ �޸�ԭ���������Լ�ɾ������ʱ��Ҫ��¼��־                            
 */
//package com.faw_qm.cappclients.foundry.test;
package com.faw_qm.cappclients.other;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.clients.rename.model.RenameIfc;
import com.faw_qm.clients.rename.model.RenameInfo;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.BsoDescript;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.version.model.VersionedIfc;

/**
 * �������ĶԻ����࣬�ṩ�ˣ�1��������ҵ�����Ĺ��ܣ�
 *                      2��Ч����������ַ����Ƿ�Ϸ��Ĺ��ܡ�
 * ˵�����öԻ���֧��ҵ��������������Ը�������3������3�����������
 * 
 * @author �δ��
 * @version 1.0
 */

public class RenameJDialog extends JDialog
{
	private JButton cancelbutton = new JButton();
	private JButton okbutton = new JButton();
	private JTextField numberTextField = new JTextField();
	private JTextField nameTextField = new JTextField();
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JLabel label_1 = new JLabel();
    //�����Ի�����Ҫ������������ҵ�����
	private RenameIfc renameIfc = null;
	//�Ƿ�����ȷ����ť
	private boolean isConfirm = false;
	//�Ի���ķ���ֵ
	private Object returnObj = null;
	//�Ի���ĸ�����
	private Window parentWindow = null;
	//Ч������ı�źϷ���ʱ������ַ����������󳤶�
	private int numberMaxLength = 30;
	//Ч����������ƺϷ���ʱ������ַ����������󳤶�
	private int nameMaxLength = 30;

	/**
	 * ���췽��
	 * 
	 * @param parent
	 *            ������
	 * @param ifc
	 *            Ҫ��������ҵ����󣬱���̳���RenameIfc�ӿ�
	 * @param numbermax
	 *            Ч������ı�źϷ���ʱ������ַ����������󳤶�
	 * @param namemax
	 *            Ч����������ƺϷ���ʱ������ַ����������󳤶ȣ����ֻ��������ҵ������һ�����ԣ�
	 *            ���ֵ�������ã�����������һ��intֵ����          
	 */
	public RenameJDialog(Window parent, RenameIfc ifc ,int numbermax, int namemax)
	{
		super(parent, "������");
		setModal(true);
		renameIfc = ifc;
		parentWindow = parent;
		numberMaxLength=numbermax;
		nameMaxLength=namemax;

		jbInit(ifc.isRenameTwoAttribute());
		setLocation();
	}

	/**
	 * �����ʼ������
     * @return void
	 */
	private void jbInit(boolean isTwo) {
		if (isTwo) {
			
			getContentPane().setLayout(null);

			panel.setLayout(new GridBagLayout());
			panel.setBounds(0, 0, 390, 173);
			getContentPane().add(panel);

			label.setText("*" + renameIfc.getNumberLabel());
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setText("*" + renameIfc.getNameLabel());
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);

			okbutton.setText("ȷ��");
			okbutton.setMaximumSize(new Dimension(80, 23));
			okbutton.setMinimumSize(new Dimension(80, 23));
			okbutton.setPreferredSize(new Dimension(80, 23));
			okbutton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	okbutton_actionPerformed(e);
		        }
		      });


			cancelbutton.setMaximumSize(new Dimension(80, 23));
			cancelbutton.setMinimumSize(new Dimension(80, 23));
			cancelbutton.setPreferredSize(new Dimension(80, 23));
			cancelbutton.setText("ȡ��");
			cancelbutton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	cancelbutton_actionPerformed(e);
		        }
		      });
			panel.add(label, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(10, 20, 0, 8), 40, 10));

			panel.add(label_1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(23, 20, 0, 8), 40, 10));

			panel.add(numberTextField, new GridBagConstraints(1, 0, 2, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(13, 6, 3, 70), 215, 0));

			panel.add(nameTextField, new GridBagConstraints(1, 1, 2, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(26, 6, 3, 70), 209, 0));

			panel.add(okbutton, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE,
					new Insets(27, 65, 26, 6), 0, 0));
			
			panel.add(cancelbutton, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE,
					new Insets(27, 0, 26, 70), 0, 0));


			this.setSize(360, 200);
		}
		else
		{
			getContentPane().setLayout(null);

			panel.setLayout(new GridBagLayout());
			panel.setBounds(0, 0, 390, 140);
			getContentPane().add(panel);
			label.setText("*" + renameIfc.getNumberLabel());
			label.setHorizontalAlignment(SwingConstants.RIGHT);

			okbutton.setText("ȷ��");
			okbutton.setMaximumSize(new Dimension(80, 23));
			okbutton.setMinimumSize(new Dimension(80, 23));
			okbutton.setPreferredSize(new Dimension(80, 23));
			okbutton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	okbutton_actionPerformed(e);
		        }
		      });
			
			cancelbutton.setMaximumSize(new Dimension(80, 23));
			cancelbutton.setMinimumSize(new Dimension(80, 23));
			cancelbutton.setPreferredSize(new Dimension(80, 23));
			cancelbutton.setText("ȡ��");
			cancelbutton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	cancelbutton_actionPerformed(e);
		        }
		      });
			panel.add(label, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(10, 20, 0, 0), 40, 10));

			panel.add(numberTextField, new GridBagConstraints(1, 0, 2, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(13, 6, 3, 70), 195, 0));

			panel.add(cancelbutton, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE,
					new Insets(27, 6, 26, 74), 0, 0));

			panel.add(okbutton, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE,
					new Insets(27, 65, 26, 0), 0, 0));		
		
			this.setSize(360, 160);
		}
		
	}
	
	/**
	 * ���öԻ����λ��
     * @return void
	 */
	private void setLocation()
	{
		Rectangle bounds = null;
		if(null==parentWindow)
		{
			Toolkit kit = Toolkit.getDefaultToolkit();
		    Dimension screenSize = kit.getScreenSize();
		    bounds=new Rectangle(0,0,screenSize.width,screenSize.height);
		}
		else
		{
			bounds = parentWindow.getBounds();
		}
		Rectangle abounds = getBounds();
		setLocation(bounds.x + (bounds.width - abounds.width)/ 2,
		bounds.y + (bounds.height - abounds.height)/2);
	}
	
	/**
	 * ��ȷ������ť���¼�����
     * @return void
	 */
	void okbutton_actionPerformed(ActionEvent e)
	{
		//cr4 begin
		String bsoName="";
		String oldName=renameIfc.getNameText();
		String oldNum=renameIfc.getNumberText();
		//cr4 end
		//RenameIfc ifc = renameIfc;
		if(renameIfc instanceof BaseValueIfc)
		{
			boolean access=false;
			try
			{
				access=checkRenameAccess((BaseValueIfc)renameIfc);
				//cr4 begin
				BsoDescript bso=JNDIUtil.getBsoDescript(((BaseValueIfc)renameIfc).getBsoName());
				bsoName=bso.getFeature("DisplayName");
				//cr4 end
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				String message = QMExceptionHandler.handle(ex);			
            	DialogFactory.showWarningDialog(parentWindow, message);//CR1
				return;
			}
			if(!access)
			{
				String message1 = "��ǰ�û��Ըö���û��������Ȩ�ޣ�";
				DialogFactory.showInformDialog(parentWindow, message1);//CR1
				return;
			}
		}
		
		String number = numberTextField.getText().trim();
		String name = nameTextField.getText().trim();

		if (number.equals(""))
		{
			String message = renameIfc.getNumberLabel() + "����Ϊ��";
			DialogFactory.showInformDialog(parentWindow, message);//CR1
			return;
		}

		try
		{
			TextValidCheck checkText = new TextValidCheck(renameIfc.getNumberLabel(),numberMaxLength);
			checkText.check(number, true);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			String message1 = QMExceptionHandler.handle(ex);
			DialogFactory.showWarningDialog(parentWindow, message1);//CR1
			return;
		}

		if (renameIfc.isRenameTwoAttribute())
		{
			if (name.equals(""))
			{
				String message = renameIfc.getNameLabel() + "����Ϊ��";
				DialogFactory.showInformDialog(parentWindow, message);//CR1
				return;
			}

			try
			{
				TextValidCheck checkText = new TextValidCheck(renameIfc.getNameLabel(),nameMaxLength);
				checkText.check(name, true);
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();
				String message1 = QMExceptionHandler.handle(ex);
				DialogFactory.showWarningDialog(parentWindow, message1);//CR1
				return;
			}
		} 
		else
		{
            name=null;
		}
		try
		{
			returnObj = renameIfc.rename(number, name);
			//cr4 begin
			if (renameIfc.isRenameTwoAttribute()) {
				Class[] cl = { String.class, String.class, String.class,
						String.class, String.class };
				Object[] param = { bsoName,oldNum,oldName,number,name };

				RequestHelper.request("LogManagement", "writeLog", cl,
						param);
			}else{
				Class[] cl = { String.class, String.class, String.class};
				Object[] param = { bsoName,oldNum,number };
				
				RequestHelper.request("LogManagement", "writeLog", cl,
						param);
			}
			//cr4 begin
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			returnObj = renameIfc;//CR2
			String message = QMExceptionHandler.handle(ex);
			DialogFactory.showWarningDialog(parentWindow, message);//CR1
			return;
		}
		isConfirm = true;
		setVisible(false);
	}

	/**
	 * ��ȡ������ť���¼�����
     * @return void
	 */
	void cancelbutton_actionPerformed(ActionEvent e)
	{
		isConfirm = false;
		setVisible(false);
	}

	/**
	 * �Ի�����ʾ�ķ���������ꡰȷ������ť��رնԻ�������û���Ҫ�Ի����ṩ����ֵ�Ļ�������ø÷�����
	 * �÷����ڹرնԻ����Ὣ������ҵ������rename�����ķ���ֵ���ظ��Ի���ĵ����ߡ�
     * @return Object ��������ȷ������ť�رնԻ����򷵻�������ҵ������rename�����ķ���ֵ
     *                ���ѡ�������Ĺرշ�ʽ������null
	 */
	public Object showDialog()
	{
		setVisible(true);
		if (!isConfirm)
			return null;
		else
			return returnObj;
	}

	public static void main(String args[])
	{
		RenameIfc data = new RenameInfo()
		{
			public Object rename(String number, String name)
			{
				String str=null;
				str.getBytes();
				return "Data";
			}
			//cr4 begin
			@Override
			public String getNameText() {
				return null;
			}

			@Override
			public String getNumberText() {
				return null;
			}
			//cr4 begin
			public String getNumberLabel()
			{
				return "���";
			}
			public boolean isRenameTwoAttribute()
			{
				return false;
			}
		};
		RenameJDialog dia = new RenameJDialog(null, data,14,15);
		System.out.println(dia.showDialog());
//		dia.setVisible(true);
	}
	
	/**
     * ����������Ķ�����û�й���Ȩ��
     * @param basev ��������ֵ����
     * @return true ��Ȩ�ޣ�false ��Ȩ�ޡ�
	 * @throws QMException 
     */
    public static boolean checkRenameAccess(BaseValueIfc basev) throws QMException
    {
 
        if(basev == null)
        {
            throw new QMException("Ҫ���������Ȩ�޵Ķ���Ϊ�գ�");
        }
        boolean hasAccess = false;
        ServiceRequestInfo info = new ServiceRequestInfo();
        if(basev instanceof VersionedIfc)
        {
            //��ȡ���°���
            Class[] theClass = {VersionedIfc.class};
            Object[] objs = {(VersionedIfc)basev};
            info.setServiceName("VersionControlService");
            info.setMethodName("getLatestVersion");
            info.setParaClasses(theClass);
            info.setParaValues(objs);
            BaseValueIfc bas = (BaseValueIfc)RequestHelper.request(info);
            
            //������Ȩ��
            Class[] theClass1 = {Object.class,String.class};
            Object[] objs1 = {bas,QMPermission.ADMINISTRATIVE};
            info.setServiceName("AccessControlService");
            info.setMethodName("checkAccess");
            info.setParaClasses(theClass1);
            info.setParaValues(objs1);
            
            
            hasAccess = (Boolean)RequestHelper.request(info);
 
        }else
        {
            //������Ȩ��
            Class[] theClass2 = {Object.class,String.class};
            Object[] objs2 = {basev,QMPermission.ADMINISTRATIVE};
            info.setServiceName("AccessControlService");
            info.setMethodName("checkAccess");
            info.setParaClasses(theClass2);
            info.setParaValues(objs2);
            
            
            hasAccess = (Boolean)RequestHelper.request(info);
        }
        return hasAccess;
 
    }

}