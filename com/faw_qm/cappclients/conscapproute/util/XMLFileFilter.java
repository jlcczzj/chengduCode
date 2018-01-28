/** 
 * ���ɳ��� XMLFileFilter.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <p> Title: XML�ļ������� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class XMLFileFilter extends FileFilter
{

    /**
     * ����XML�ļ�������
     */
    public XMLFileFilter()
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
                accept = suffix.equals("xml");
            }

        }
        return accept;
    }

    /**
     * ��ñ���������������Ϣ
     * @return XML Files(*.xml)
     */
    public String getDescription()
    {
        return "XML Files(*.xml)";
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