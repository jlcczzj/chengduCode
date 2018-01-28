package com.faw_qm.cappclients.capp.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.*;

import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.view.ProcedureUsageDrawingPanel;
import com.faw_qm.cappclients.capp.view.ProcedureUsageEquipmentJPanel;
import com.faw_qm.cappclients.capp.view.ProcedureUsageToolJPanel;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.jview.chrset.SpeCharaterTextBean;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.codemanage.model.CodingInfo;

/**
 * Title: 获得容器中的所有控件，并给控件注册监听,并确定界面数据是否改变. Company: 一汽启明
 * 
 * @author 郭晓亮
 * CCBegin by liunan 2011-07-25 打补丁V4R3_P034
 * CR1 2011/06/27 吕航   原因：参见TD2417问题，原因是在上一个界面只获得焦点，就切换到下一个界面
 *                       潜在问题：1 焦点失去事件是监听，有延后现象
 *                                2 由于共用工序界面，焦点失去获得的值可能是下一个页面的值
 *                                这样就形成两个界面的值进行对比，即对比两个工序的值
 * CCEnd by liunan 2011-07-25
 * CCBegin by liunan 2011-08-25 打补丁P035
 * CR2 2011/07/14 吕航 参见TD2423
 * CCEnd by liunan 2011-08-25
 * @version 1.0
 */
public class AddFocusListener {

	private Vector<Component> componentsVec = new Vector<Component>();// 所有控件的集合
	private Vector<CappMultiList> multListVec = new Vector<CappMultiList>();// CappMultiList集合

	private Container jComponent;// 控件临时对象

	private String oldValue = "";// 控件中原有值

	private String newValue = "";// 更新后控件中的值

	private Component focusCom = null;// 获得焦点的控件

	private MyFocusListener myFocusList = new MyFocusListener();// Focus监听类对象

	private boolean isChange = false;// 焦点监听中控件内容是否改变标识，改变=true,没变=false.

	private boolean isHasComponentFocus = false;// 更新界面是否有默认焦点标识

	private boolean multiListisModified = false;// multiList是否“双击，添加或删除行”标识

	private String specialString = "";// 更新界面时用于存储工艺内容旧数据

	/**
	 * 获得容器中的所有控件,并添加监听
	 * 
	 * @param container 指定容器
	 */

	public void setCompsFocusListener(Container container) {

		// 每次执行更新操作后切换到更新界面时，
		//都将multListVec清空，同时将每个标识赋值为false
		initFlag();

		// 获得panel上的所有控件，同时将所有CappMultiList放到multListVec中，
		// 其它控件放到componentsVec中

		getComps(container);

		// 给基本控件加监听
		for (int i = 0; i < componentsVec.size(); i++) {

			jComponent = (Container) componentsVec.elementAt(i);

			if (!(jComponent instanceof JButton)) {
				int focusLenth = jComponent.getFocusListeners().length;

				if (focusLenth != 0) {

					for (int j = 0; j < focusLenth; j++) {

						FocusListener[] focus = jComponent.getFocusListeners();
						// 如果此控件加过焦点监听，则删除，避免重复添加
						if (focus[j] instanceof MyFocusListener) {

							jComponent.removeFocusListener(focus[j]);

						}
					}

					jComponent.addFocusListener(myFocusList);

				}

			}

		}
		// 给multList加监听
		for (int j = 0; j < multListVec.size(); j++) {

			CappMultiList cappMultiList = (CappMultiList) multListVec
					.elementAt(j);
			cappMultiList.addTableModelListener();

		}
		componentsVec.clear();

	}
	/**
	 * 更新操作后切换到更新界面时，
	 * 都将multListVec清空，同时将每个标识赋值为false
	 */
    public void initFlag(){
	
	     multListVec.clear();
	     isChange = false;
	     isHasComponentFocus = false;
	     multiListisModified = false;
     }

	/**
	 * 获得容器中的所有控件 同时将所有CappMultiList放到multListVec中， 
	 * 其它控件放到componentsVec中
	 */

	private void getComps(Container container) {

		
		Component[] components = container.getComponents();

		
		int length = components.length;

		for (int i = 0; i < length; i++) {

			// 将工艺内容的值得到，并赋给specialString，以供失去焦点处调用，解决第一次
			// 更新时工艺内容失去焦点时找不到真实的旧值
			if (components[i] instanceof SpeCharaterTextBean) {
				SpeCharaterTextBean SpeCharaterbean = (SpeCharaterTextBean) components[i];
				specialString = SpeCharaterbean.SpeCharaterTextPanel.save();
				
			}
			
			if (components[i] instanceof JPanel
					|| components[i] instanceof CappAssociationsPanel) {

				if (components[i] instanceof SpeCharaterTextPanel) {

					getComps((SpeCharaterTextPanel) components[i]);

				} else if (components[i] instanceof CappAssociationsPanel) {

					getComps((CappAssociationsPanel) components[i]);
				}

				else if (components[i] instanceof CappMultiList) {

					multListVec.add((CappMultiList) components[i]);

				} else {

					getComps((JPanel) components[i]);
				}

			} else if (components[i] instanceof JTabbedPane) {

				getComps((JTabbedPane) components[i]);

			} else if (components[i] instanceof JScrollPane) {

				getComps((JScrollPane) components[i]);
			} else if (components[i] instanceof JSplitPane) {

				getComps((JSplitPane) components[i]);

			} else if (components[i] instanceof JViewport) {

				getComps((JViewport) components[i]);

			} else {
				
				componentsVec.add(components[i]);
			}

		}

	}

	/**
	 * Focus监听的内部类
	 * 
	 */

	class MyFocusListener implements FocusListener {

		// 获得焦点
		public void focusGained(FocusEvent e) {
			focusCom = (Component) e.getSource();

			if (e.getSource() instanceof JTextField) {

				oldValue = ((JTextField) e.getSource()).getText();
				
				// 界面初始化时是否有焦点标记
				isHasComponentFocus = true;

			} else if (e.getSource() instanceof JComboBox) {
				
				if(((JComboBox) e.getSource())
						.getSelectedItem() instanceof CodingInfo){
					
					oldValue = (String) ((JComboBox) e.getSource())
					.getSelectedItem().toString();
					
					
				}else{
				    oldValue = (String) ((JComboBox) e.getSource())
						.getSelectedItem();
				    
				}
				isHasComponentFocus = true;

			} else if (e.getSource() instanceof JTextArea) {

				if (e.getSource() instanceof SpeCharaterTextBean) {

					oldValue = ((SpeCharaterTextBean) e.getSource()).SpeCharaterTextPanel
							.save();
					
				} else {

					oldValue = ((JTextArea) e.getSource()).getText();

				}
				isHasComponentFocus = true;

			}

		}

		// 失去焦点
		public void focusLost(FocusEvent e) {
      //CCBegin by liunan 2011-07-25 打补丁V4R3_P034
       //begin CR1
            boolean otherChange = otherIschange();
            if(!otherChange)
                return;
       //end CR1
      //CCEnd by liunan 2011-07-25

			if (!isChange) {
				if (e.getSource() instanceof JTextField) {

					newValue = ((JTextField) e.getSource()).getText();

					if (newValue.equals(oldValue)) {

						isChange = false;
						
					} else {

						isChange = true;
						
					}

				} else if (e.getSource() instanceof JComboBox) {
					if(((JComboBox) e.getSource())
							.getSelectedItem() instanceof CodingInfo){
						
						newValue = (String) ((JComboBox) e.getSource())
						.getSelectedItem().toString();
						
						
					}else{
					newValue = (String) ((JComboBox) e.getSource())
							.getSelectedItem();
					}
					if (newValue.equals(oldValue)) {

						isChange = false;
						
					} else {

						isChange = true;
						
					}

				} else if (e.getSource() instanceof JTextArea) {
					if (e.getSource() instanceof SpeCharaterTextBean) {

						newValue = ((SpeCharaterTextBean) e.getSource()).SpeCharaterTextPanel
								.save();
						// 界面更新时工艺内容获得焦点,但旧值为空时
						if (oldValue.equals("") || oldValue == null) {
							if (newValue.equals(specialString))
								isChange = false;
							else
								isChange = true;


						} else if (newValue.equals(oldValue)) {

							isChange = false;
							
						} else {

							isChange = true;
							
						}

					} else {

						newValue = ((JTextArea) e.getSource()).getText();
						if (newValue.equals(oldValue)) {

							isChange = false;
							
						} else {

							isChange = true;
							
						}
					}
				}

				System.out.println("value===" + isChange);

			}
		}
	}

	/**
	 * 焦点监听无效时用此方法 
	 * (原因:因为结点监听全部执行完毕并出现查看界面后才执行失去焦点的方法,
	 * 所以焦点监听无法正确的对获得焦点得到的旧值和失去焦点的新值进行比较)
	 * 此方法的作用是,在结点监听刚一执行时,得到获得焦点的控件的内容,此时的
	 * 控件内容可作为 一个新值和一开始得到焦点时获得的旧值进行比较.
	 */
	private boolean otherIschange() {
		boolean b = false;

		String newValue = "";
        //界面没有焦点，也没改返回false
		if (!isHasComponentFocus)
			return false;
		if (focusCom instanceof JTextField) {

			newValue = ((JTextField) focusCom).getText();
			b = !(newValue.equals(oldValue));

		} else if (focusCom instanceof SpeCharaterTextBean) {
			SpeCharaterTextBean bean = (SpeCharaterTextBean) focusCom;
            
			newValue = bean.SpeCharaterTextPanel.save();
			b = !(newValue.equals(oldValue));
		} else if (focusCom instanceof JComboBox) {
			if(((JComboBox)focusCom)
					.getSelectedItem() instanceof CodingInfo){
				
				newValue = (String) ((JComboBox) focusCom).getSelectedItem().toString();
            	
            }else{
			    newValue = (String) ((JComboBox) focusCom).getSelectedItem();
            }
			b = !((newValue.equals(oldValue)));
		}
		//CCBegin by liunan 2011-08-25 打补丁P035
		//CR2 begin
		//oldValue = newValue;
	  //CR2 end
		//CCEnd by liunan 2011-08-25

		return b;
	}

	/**
	 * 确定更新界面控件中的数据是否修改过,在弹出是否保存对话框处调用
	 * 
	 */
	public boolean finalChangeValue() {

		//CCBegin by liunan 2011-07-25 打补丁V4R3_P034
		//boolean changeValue = false;CR1
		//CCEnd by liunan 2011-07-25
		boolean otherChange = otherIschange();

		for (int i = 0; i < multListVec.size(); i++) {
			if (!multiListisModified) {


				multiListisModified = ((CappMultiList) multListVec.elementAt(i))
						.isValuesChanged();

			}

		}

System.out.println("isChange=="+isChange);
System.out.println("otherChange=="+otherChange);
System.out.println("multiListisModified=="+multiListisModified);

		if (isChange || otherChange || multiListisModified) {

			return true;

		} else
			return false;
	}

}
