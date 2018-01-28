/** 
 * 生成程序 XMLFileFilter.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <p> Title: XML文件过滤器 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class XMLFileFilter extends FileFilter
{

    /**
     * 构造XML文件过滤器
     */
    public XMLFileFilter()
    {}

    /**
     * 判断指定的文件是否被本过滤器接受
     * @param f 文件
     * @return 如果接受，则返回true
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
     * 获得本过滤器的描述信息
     * @return XML Files(*.xml)
     */
    public String getDescription()
    {
        return "XML Files(*.xml)";
    }

    /**
     * 获得指定文件的后缀
     * @param f File
     * @return 文件的后缀
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