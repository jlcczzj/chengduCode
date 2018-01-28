/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 20100119
 * CR1 2010/03/09 徐春英  原因：参见TD问题2883
 * CR2 2010/03/09 徐春英  原因：参见TD问题2881
 * CR3 2010/03/09 王亮    原因：1.参见TD问题2917，2920；
 *                             2.根据《整理后PDM系统界面规划_徐雷平(提示对话框问题及规则-关宏英).doc》的要求修改提示对话框。
 * cr4 20110725 吕正超 修改原因：重命名以及删除对象时需要记录日志                            
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
 * 重命名的对话框类，提供了：1、重命名业务对象的功能；
 *                      2、效验所输入的字符串是否合法的功能。
 * 说明：该对话框不支持业务对象重命名属性个数超过3个（含3个）的情况。
 * 
 * @author 宋大刚
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
    //传到对话框中要进行重命名的业务对象
	private RenameIfc renameIfc = null;
	//是否点击了确定按钮
	private boolean isConfirm = false;
	//对话框的返回值
	private Object returnObj = null;
	//对话框的父窗口
	private Window parentWindow = null;
	//效验输入的编号合法性时，编号字符串允许的最大长度
	private int numberMaxLength = 30;
	//效验输入的名称合法性时，编号字符串允许的最大长度
	private int nameMaxLength = 30;

	/**
	 * 构造方法
	 * 
	 * @param parent
	 *            父窗口
	 * @param ifc
	 *            要重命名的业务对象，必须继承自RenameIfc接口
	 * @param numbermax
	 *            效验输入的编号合法性时，编号字符串允许的最大长度
	 * @param namemax
	 *            效验输入的名称合法性时，编号字符串允许的最大长度，如果只是重命名业务对象的一个属性，
	 *            则该值不起作用，即传入任意一个int值即可          
	 */
	public RenameJDialog(Window parent, RenameIfc ifc ,int numbermax, int namemax)
	{
		super(parent, "重命名");
		setModal(true);
		renameIfc = ifc;
		parentWindow = parent;
		numberMaxLength=numbermax;
		nameMaxLength=namemax;

		jbInit(ifc.isRenameTwoAttribute());
		setLocation();
	}

	/**
	 * 界面初始化方法
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

			okbutton.setText("确定");
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
			cancelbutton.setText("取消");
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

			okbutton.setText("确定");
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
			cancelbutton.setText("取消");
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
	 * 设置对话框的位置
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
	 * “确定”按钮的事件方法
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
				String message1 = "当前用户对该对象没有重命名权限！";
				DialogFactory.showInformDialog(parentWindow, message1);//CR1
				return;
			}
		}
		
		String number = numberTextField.getText().trim();
		String name = nameTextField.getText().trim();

		if (number.equals(""))
		{
			String message = renameIfc.getNumberLabel() + "不能为空";
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
				String message = renameIfc.getNameLabel() + "不能为空";
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
	 * “取消”按钮的事件方法
     * @return void
	 */
	void cancelbutton_actionPerformed(ActionEvent e)
	{
		isConfirm = false;
		setVisible(false);
	}

	/**
	 * 对话框显示的方法，点击完“确定”按钮会关闭对话框，如果用户需要对话框提供返回值的话，则调用该方法，
	 * 该方法在关闭对话框后会将重命名业务对象的rename方法的返回值返回给对话框的调用者。
     * @return Object 如果点击“确定”按钮关闭对话框，则返回重命名业务对象的rename方法的返回值
     *                如果选择其它的关闭方式，返回null
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
				return "编号";
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
     * 检查重命名的对象有没有管理权限
     * @param basev 重命名的值对象
     * @return true 有权限，false 无权限。
	 * @throws QMException 
     */
    public static boolean checkRenameAccess(BaseValueIfc basev) throws QMException
    {
 
        if(basev == null)
        {
            throw new QMException("要检查重命名权限的对象为空！");
        }
        boolean hasAccess = false;
        ServiceRequestInfo info = new ServiceRequestInfo();
        if(basev instanceof VersionedIfc)
        {
            //获取最新版序
            Class[] theClass = {VersionedIfc.class};
            Object[] objs = {(VersionedIfc)basev};
            info.setServiceName("VersionControlService");
            info.setMethodName("getLatestVersion");
            info.setParaClasses(theClass);
            info.setParaValues(objs);
            BaseValueIfc bas = (BaseValueIfc)RequestHelper.request(info);
            
            //检查管理权限
            Class[] theClass1 = {Object.class,String.class};
            Object[] objs1 = {bas,QMPermission.ADMINISTRATIVE};
            info.setServiceName("AccessControlService");
            info.setMethodName("checkAccess");
            info.setParaClasses(theClass1);
            info.setParaValues(objs1);
            
            
            hasAccess = (Boolean)RequestHelper.request(info);
 
        }else
        {
            //检查管理权限
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