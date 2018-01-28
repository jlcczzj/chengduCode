package com.faw_qm.cappclients.util;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.util.TextValidCheck;


/**
 * <p>Title:工艺文本框 </p>
 * <p>Description: 此文本框可以判断输入的文本是否合法,包括通配符，最大长度，
 * 是否可以为空。check()方法负责判断，并提示。</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 */

public class CappTextField extends JTextField
{
    /**父窗口*/
    private Component parentComponent;

    /**最大长度*/
    private int maxLength = -1;

    /**是否允许为空*/
    private boolean nullAllowed;

    /**属性显示名*/
    private String nameDisp;


    /**
     * 构造方法
     * @param parent Component 父窗口
     * @param nameDisp String 属性显示名
     * @param maxlength int 最大长度
     * @param nullAllowed boolean 是否允许为空,允许为true,否则为false
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
    
    //CCBegin by leixiao 2009-12-16 增加不判断特殊字符的方法
    /**
     * 检查输入是否合法
     * @return boolean 合法返回true，否则返回false
     */
    public boolean check()
    {
    	return check(true);
    }
    
    public boolean check(boolean flag1){
        boolean flag = true;
        //获得文本
        
        String s = this.getText().trim();
        String title = "缺少必要的字段";
        String title1 = "输入格式错误";
        String message = null;
        //modify by wangh on 20070529
        //CCBeginby liuzc 2009-12-19 原因：工艺编号不能为空，参见DefectID=2658
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
        //CCBeginby liuzc 2009-12-19 原因：工艺编号不能为空，参见DefectID=2658
//        if (s.indexOf("*") != -1 || s.indexOf("%") != -1 ||
//            s.indexOf("?") != -1)
//        {
//            message = nameDisp + "中含有非法字符（*%?）";
//            flag = false;
//        }
        if (!nullAllowed && s.length() == 0)
        {
            message = nameDisp + "不能为空！";
            flag = false;
        }
//        else
//        	
//        if (maxLength != -1 && s.getBytes().length > maxLength)
//        {
//            message = "您输入的" + nameDisp + "信息过长，请确保长度不大于" + maxLength+"字符。";
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
