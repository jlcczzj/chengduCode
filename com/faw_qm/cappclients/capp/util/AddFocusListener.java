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
 * Title: ��������е����пؼ��������ؼ�ע�����,��ȷ�����������Ƿ�ı�. Company: һ������
 * 
 * @author ������
 * CCBegin by liunan 2011-07-25 �򲹶�V4R3_P034
 * CR1 2011/06/27 ����   ԭ�򣺲μ�TD2417���⣬ԭ��������һ������ֻ��ý��㣬���л�����һ������
 *                       Ǳ�����⣺1 ����ʧȥ�¼��Ǽ��������Ӻ�����
 *                                2 ���ڹ��ù�����棬����ʧȥ��õ�ֵ��������һ��ҳ���ֵ
 *                                �������γ����������ֵ���жԱȣ����Ա����������ֵ
 * CCEnd by liunan 2011-07-25
 * CCBegin by liunan 2011-08-25 �򲹶�P035
 * CR2 2011/07/14 ���� �μ�TD2423
 * CCEnd by liunan 2011-08-25
 * @version 1.0
 */
public class AddFocusListener {

	private Vector<Component> componentsVec = new Vector<Component>();// ���пؼ��ļ���
	private Vector<CappMultiList> multListVec = new Vector<CappMultiList>();// CappMultiList����

	private Container jComponent;// �ؼ���ʱ����

	private String oldValue = "";// �ؼ���ԭ��ֵ

	private String newValue = "";// ���º�ؼ��е�ֵ

	private Component focusCom = null;// ��ý���Ŀؼ�

	private MyFocusListener myFocusList = new MyFocusListener();// Focus���������

	private boolean isChange = false;// ��������пؼ������Ƿ�ı��ʶ���ı�=true,û��=false.

	private boolean isHasComponentFocus = false;// ���½����Ƿ���Ĭ�Ͻ����ʶ

	private boolean multiListisModified = false;// multiList�Ƿ�˫������ӻ�ɾ���С���ʶ

	private String specialString = "";// ���½���ʱ���ڴ洢�������ݾ�����

	/**
	 * ��������е����пؼ�,����Ӽ���
	 * 
	 * @param container ָ������
	 */

	public void setCompsFocusListener(Container container) {

		// ÿ��ִ�и��²������л������½���ʱ��
		//����multListVec��գ�ͬʱ��ÿ����ʶ��ֵΪfalse
		initFlag();

		// ���panel�ϵ����пؼ���ͬʱ������CappMultiList�ŵ�multListVec�У�
		// �����ؼ��ŵ�componentsVec��

		getComps(container);

		// �������ؼ��Ӽ���
		for (int i = 0; i < componentsVec.size(); i++) {

			jComponent = (Container) componentsVec.elementAt(i);

			if (!(jComponent instanceof JButton)) {
				int focusLenth = jComponent.getFocusListeners().length;

				if (focusLenth != 0) {

					for (int j = 0; j < focusLenth; j++) {

						FocusListener[] focus = jComponent.getFocusListeners();
						// ����˿ؼ��ӹ������������ɾ���������ظ����
						if (focus[j] instanceof MyFocusListener) {

							jComponent.removeFocusListener(focus[j]);

						}
					}

					jComponent.addFocusListener(myFocusList);

				}

			}

		}
		// ��multList�Ӽ���
		for (int j = 0; j < multListVec.size(); j++) {

			CappMultiList cappMultiList = (CappMultiList) multListVec
					.elementAt(j);
			cappMultiList.addTableModelListener();

		}
		componentsVec.clear();

	}
	/**
	 * ���²������л������½���ʱ��
	 * ����multListVec��գ�ͬʱ��ÿ����ʶ��ֵΪfalse
	 */
    public void initFlag(){
	
	     multListVec.clear();
	     isChange = false;
	     isHasComponentFocus = false;
	     multiListisModified = false;
     }

	/**
	 * ��������е����пؼ� ͬʱ������CappMultiList�ŵ�multListVec�У� 
	 * �����ؼ��ŵ�componentsVec��
	 */

	private void getComps(Container container) {

		
		Component[] components = container.getComponents();

		
		int length = components.length;

		for (int i = 0; i < length; i++) {

			// ���������ݵ�ֵ�õ���������specialString���Թ�ʧȥ���㴦���ã������һ��
			// ����ʱ��������ʧȥ����ʱ�Ҳ�����ʵ�ľ�ֵ
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
	 * Focus�������ڲ���
	 * 
	 */

	class MyFocusListener implements FocusListener {

		// ��ý���
		public void focusGained(FocusEvent e) {
			focusCom = (Component) e.getSource();

			if (e.getSource() instanceof JTextField) {

				oldValue = ((JTextField) e.getSource()).getText();
				
				// �����ʼ��ʱ�Ƿ��н�����
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

		// ʧȥ����
		public void focusLost(FocusEvent e) {
      //CCBegin by liunan 2011-07-25 �򲹶�V4R3_P034
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
						// �������ʱ�������ݻ�ý���,����ֵΪ��ʱ
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
	 * ���������Чʱ�ô˷��� 
	 * (ԭ��:��Ϊ������ȫ��ִ����ϲ����ֲ鿴������ִ��ʧȥ����ķ���,
	 * ���Խ�������޷���ȷ�ĶԻ�ý���õ��ľ�ֵ��ʧȥ�������ֵ���бȽ�)
	 * �˷�����������,�ڽ�������һִ��ʱ,�õ���ý���Ŀؼ�������,��ʱ��
	 * �ؼ����ݿ���Ϊ һ����ֵ��һ��ʼ�õ�����ʱ��õľ�ֵ���бȽ�.
	 */
	private boolean otherIschange() {
		boolean b = false;

		String newValue = "";
        //����û�н��㣬Ҳû�ķ���false
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
		//CCBegin by liunan 2011-08-25 �򲹶�P035
		//CR2 begin
		//oldValue = newValue;
	  //CR2 end
		//CCEnd by liunan 2011-08-25

		return b;
	}

	/**
	 * ȷ�����½���ؼ��е������Ƿ��޸Ĺ�,�ڵ����Ƿ񱣴�Ի��򴦵���
	 * 
	 */
	public boolean finalChangeValue() {

		//CCBegin by liunan 2011-07-25 �򲹶�V4R3_P034
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
