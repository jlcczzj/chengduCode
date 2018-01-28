package com.faw_qm.cappclients.util;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class JCheckListBox extends JList {
//���boolean����װ������item�Ƿ�check����Ϣ��

private boolean[] checkedItems = null;

/**
* ����һ���򵥵�ListModel
*/
class CheckListBoxModel extends AbstractListModel {

private Object[] items = null;

CheckListBoxModel(Object[] items) {
this.items = items;
}

public int getSize() {
return items.length;
}

public Object getElementAt(int i) {
return items[i];
}

protected void fireCheckChanged(Object source, int index){
fireContentsChanged(source, index, index);
}

public Object getItem(int index) {
return items[index];
}
}

/**
* ����͸�����һ�����캯��������JList���Լ����ǰɣ�����superһ����init��OK�ˡ�
* @param items Object[]
*/
public JCheckListBox(Object[] items) {
setModel(new CheckListBoxModel(items));
init();
}

/**
* ��ʼ���ؼ���������ʼ��boolean���顢��װһ����Ⱦ������װһ������������
*/
protected void init() {
checkedItems = new boolean[this.getModel().getSize()];
class MyCellRenderer extends JCheckBox implements ListCellRenderer {

public MyCellRenderer() {
setOpaque(true);
}

public Component getListCellRendererComponent(
JList list,
Object value,
int index,
boolean isSelected,
boolean cellHasFocus) {
//����������ϴ�DefaultListCellRenderer.java�г�Ϯ�ġ�
setComponentOrientation(list.getComponentOrientation());
if (isSelected) {
setBackground(list.getSelectionBackground());
setForeground(list.getSelectionForeground());
} else {
setBackground(list.getBackground());
setForeground(list.getForeground());
}

if (value instanceof Icon) {
setIcon((Icon) value);
setText("");
} else {
setIcon(null);
setText((value==null)?"":value.toString());
}
setEnabled(list.isEnabled());
setFont(list.getFont());

//��Ȼ��Ϯ�����������������check��Ϣ��
this.setSelected(isChecked(index));
return this;
}
}

this.setCellRenderer(new MyCellRenderer());
//����һ������������������ĳ��item����ת��check״̬��
class CheckBoxListener extends MouseAdapter {

//@Override
public void mouseClicked(MouseEvent e) {
int index = locationToIndex(e.getPoint());
invertChecked(index);
}
}

this.addMouseListener(new CheckBoxListener());
}

/**
* ��תָ��item��check״̬��
* @param index int
*/
public void invertChecked(int index) {
checkedItems[index] = !checkedItems[index];
//�����˷���event��
CheckListBoxModel model = (CheckListBoxModel) getModel();
model.fireCheckChanged(this,index);
this.repaint();
}


public void setChecked(int index) {
	checkedItems[index] = true;
	CheckListBoxModel model = (CheckListBoxModel) getModel();
	model.fireCheckChanged(this,index);
	this.repaint();
	}

/**
* �Ƿ�ָ��item��check��
* @param index int
* @return boolean
*/
public boolean isChecked(int index) {
return checkedItems[index];
}

/**
* ���ѡ�е�item����
*/
public int getCheckedCount() {
int result = 0;
for (int i = 0; i < checkedItems.length; i++) {
if (checkedItems[i]) {
result++;
}
}
return result;
}





/**
* ����ѡ��item���������顣
*/
public int[] getCheckedIndices() {
int[] result = new int[getCheckedCount()];
int index = 0;
for (int i = 0; i < checkedItems.length; i++) {
if (checkedItems[i]) {
result[index] = i;
index++;
}
}
return result;
}

public static void main(String[] args) throws Exception {

	  Font font = new Font("΢���ź�", Font.PLAIN, 12);


JFrame frame = new JFrame("jchecklistbox");

final JCheckListBox list = new JCheckListBox(new Object[]{"B50","X80","B30","B70"});
list.setFont(font);
frame.getContentPane().add(new JScrollPane(list));
JButton button = new JButton("OK");
button.addActionListener(new ActionListener() {

public void actionPerformed(ActionEvent e) {
System.exit(0);
}
});
frame.getContentPane().add(button, BorderLayout.SOUTH);

final JLabel label = new JLabel("��ǰû��ѡ��");

label.setFont(font);
list.getModel().addListDataListener(new ListDataListener() {

public void intervalAdded(ListDataEvent e) {
}

public void intervalRemoved(ListDataEvent e) {
}

public void contentsChanged(ListDataEvent e) {
if (list.getCheckedCount() == 0) {
	label.setText("��ǰû��ѡ��");
} else {
	String text = "��ǰѡ��:";
int[] indices = list.getCheckedIndices();
for (int i = 0; i < indices.length; i++) {
	text += ((CheckListBoxModel) list.getModel()).getItem(indices[i]).toString() + ",";
}
label.setText(text);
               }
           }
       });
frame.getContentPane().add(label, BorderLayout.NORTH);
   frame.setBounds(300, 300, 400, 200);
   frame.setVisible(true);
   }
}
