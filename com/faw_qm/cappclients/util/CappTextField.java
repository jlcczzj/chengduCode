package com.faw_qm.cappclients.util;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.util.TextValidCheck;


/**
 * <p>Title:�����ı��� </p>
 * <p>Description: ���ı�������ж�������ı��Ƿ�Ϸ�,����ͨ�������󳤶ȣ�
 * �Ƿ����Ϊ�ա�check()���������жϣ�����ʾ��</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 */

public class CappTextField extends JTextField
{
    /**������*/
    private Component parentComponent;

    /**��󳤶�*/
    private int maxLength = -1;

    /**�Ƿ�����Ϊ��*/
    private boolean nullAllowed;

    /**������ʾ��*/
    private String nameDisp;


    /**
     * ���췽��
     * @param parent Component ������
     * @param nameDisp String ������ʾ��
     * @param maxlength int ��󳤶�
     * @param nullAllowed boolean �Ƿ�����Ϊ��,����Ϊtrue,����Ϊfalse
     */
    public CappTextField(Component parent, String nameDisp, int maxlength,
                         boolean nullAllowed)
    {
        super();
        parentComponent = parent;
        this.nameDisp = nameDisp;
        this.maxLength = maxlength;
        this.nullAllowed = nullAllowed;
    }
    
    //CCBegin by leixiao 2009-12-16 ���Ӳ��ж������ַ��ķ���
    /**
     * ��������Ƿ�Ϸ�
     * @return boolean �Ϸ�����true�����򷵻�false
     */
    public boolean check()
    {
    	return check(true);
    }
    
    public boolean check(boolean flag1){
        boolean flag = true;
        //����ı�
        
        String s = this.getText().trim();
        String title = "ȱ�ٱ�Ҫ���ֶ�";
        String title1 = "�����ʽ����";
        String message = null;
        //modify by wangh on 20070529
        //CCBeginby liuzc 2009-12-19 ԭ�򣺹��ձ�Ų���Ϊ�գ��μ�DefectID=2658
//        try{
//        TextValidCheck checkText = new TextValidCheck(nameDisp, maxLength);
//        checkText.check(s, flag1);
//        }
//        catch(QMRemoteException ex){
//        JOptionPane.showMessageDialog(parentComponent, ex.getClientMessage(),
//                                      title1,
//                                      JOptionPane.INFORMATION_MESSAGE);
//        flag = false;
//        return flag;
//        }
        //CCBeginby liuzc 2009-12-19 ԭ�򣺹��ձ�Ų���Ϊ�գ��μ�DefectID=2658
//        if (s.indexOf("*") != -1 || s.indexOf("%") != -1 ||
//            s.indexOf("?") != -1)
//        {
//            message = nameDisp + "�к��зǷ��ַ���*%?��";
//            flag = false;
//        }
        if (!nullAllowed && s.length() == 0)
        {
            message = nameDisp + "����Ϊ�գ�";
            flag = false;
        }
//        else
//        	
//        if (maxLength != -1 && s.getBytes().length > maxLength)
//        {
//            message = "�������" + nameDisp + "��Ϣ��������ȷ�����Ȳ�����" + maxLength+"�ַ���";
//            flag = false;
//        }
        if (!flag)
        {
            grabFocus();
            JOptionPane.showMessageDialog(parentComponent, message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        //modify end
        return flag;

    }
    //CCEnd by leixiao 2009-12-16 
    public static void main(String[] args)
    {
    }
}
