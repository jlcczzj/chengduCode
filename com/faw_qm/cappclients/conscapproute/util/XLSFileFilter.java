/** 
 * ���ɳ��� XLSFileFilter.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.io.*;

/**
 * <p> Title: Excel�ļ������� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class XLSFileFilter extends javax.swing.filechooser.FileFilter
{

    /**
     * ����Excel�ļ�������
     */
    public XLSFileFilter()
    {}

    /**
     * �ж�ָ�����ļ��Ƿ񱻱�����������
     * @param f �ļ�
     * @return ������ܣ��򷵻�true
     */
    public boolean accept(File f)
    {
        boolean accept = f.isDirectory();
        if(!accept)
        {
            String suffix = getSuffix(f);
            if(suffix != null)
            {
                accept = suffix.equals("xls");
            }

        }
        return accept;
    }

    /**
     * ��ñ���������������Ϣ
     * @return Excel Files(*.xls)
     */
    public String getDescription()
    {
        return "Excel Files(*.xls)";
    }

    /**
     * ���ָ���ļ��ĺ�׺
     * @param f File
     * @return �ļ��ĺ�׺
     */
    private String getSuffix(File f)
    {
        String s = f.getPath(), suffix = null;
        int i = s.lastIndexOf('.');
        if(i > 0 && i < s.length() - 1)
            suffix = s.substring(i + 1).toLowerCase();
        return suffix;
    }
}