/** 生成程序CappSortingSelectedPanel.java      1.1  2005/3/7
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 轴齿工艺自动编号 2014-1-13 文柳
 */
package com.faw_qm.cappclients.capp.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;


/**
 * <p>Title: 继承了SortingSelectedPanel的分类组建</p>
 * <p>Description: 此类较父类增加了获取默认值,添加默认值的功能(setDefaultCoding()方法)
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 */

public class CappSortingSelectedPanel extends SortingSelectedPanel
{

    /**
     * 存放默认值.键:工序种类 值:默认值
     */
    HashMap table = new HashMap();
    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";

    /**
     * 参数化构造器
     * @param rootName 根结点名称
     * @param bsoName 业务对象的Bso名
     * @param propertyName 属性字段名
     */
    public CappSortingSelectedPanel(String rootName, String bsoName,
                                    String propertyName)
    {
        super(rootName, bsoName, propertyName);
    }
//CCBegin SS1
    /**
     * 参数化构造器
     * @param rootName 根结点名称
     * @param bsoName 业务对象的Bso名
     * @param propertyName 属性字段名
     * @param parentPanel 回传父panel
     */
    public CappSortingSelectedPanel(String rootName, String bsoName,
                                    String propertyName,TechnicsMasterJPanel parentPanel)
    {
        super(rootName, bsoName, propertyName,parentPanel);
    }  
//CCEnd SS1

    /**
     * 参数化构造器
     * @param codingClassificationIfc CodingClassificationIfc 根节点
     */
    public CappSortingSelectedPanel(CodingClassificationIfc
                                    codingClassificationIfc)
    {
        super(codingClassificationIfc);
    }


    /**
     * 设置默认值.根据传入的参数到属性文件中获取信息,根据信息得到默认值,设到本组建中,
     * 默认值在属性文件中配置,如工序类别的默认值:工序类别_冲压下料工艺=关键工序
     * @param name String 分类根节点名,如:工序类别
     * @param Type String 工艺种类或工序种类的显示名
     */
    public void setDefaultCoding(String name, String type)
    {
        //根据工序种类获取缓存的默认值
        BaseValueIfc coding = (BaseValueIfc) table.get(type);
        if (coding != null)
        {
            setCoding(coding);
            //缓存默认值为null,判断table中是否包含键type,如果包含,则说明没有默认值
            //否则读取默认值,将默认值缓存,如果读到的默认值为空,则缓存null
        }
        else if (!table.containsKey(type))
        {
            String key = name + "_" + type;
            String s = RemoteProperty.getProperty(key);
            if (s != null)
            {
                StringTokenizer toker = new StringTokenizer(s.trim(), ",");
                //获得代码项
                if (toker.countTokens() == 2)
                {
                    Class[] paraclass =
                            {String.class, String.class, String.class};
                    Object[] paraobj =
                            {toker.nextElement(), toker.nextElement(), null};

                    try
                    {
                        coding = (BaseValueIfc) CappClientHelper.
                                 useServiceMethod(
                                "CodingManageService"
                                , "findCodingByContent", paraclass, paraobj);
                    }
                    catch (QMRemoteException ex)
                    {
                        ex.printStackTrace();
                    }

                }
                //获得代码分类
                if (toker.countTokens() == 1)
                {
                    Class[] paraclass =
                            {String.class};
                    Object[] paraobj =
                            {toker.nextElement()};

                    try
                    {
                        Collection c = (Collection) CappClientHelper.
                                       useServiceMethod(
                                "CodingManageService"
                                , "findClassificationByName", paraclass,
                                paraobj);
                        if (c != null && c.size() != 0)
                        {
                            coding = (BaseValueIfc) c.iterator().next();
                        }
                    }
                    catch (QMRemoteException ex)
                    {
                        ex.printStackTrace();
                    }

                }

                table.put(type, coding);
                if (coding != null)
                {
                    setCoding(coding);
                }
            }
        }

    }

    public static void main(String[] args)
    {

        CappSortingSelectedPanel test2 = new CappSortingSelectedPanel("工序类别",
                "QMProcedure", "stepClassification");
        test2.setDefaultCoding("工序类别", "冲压下料工艺");
    }

}
