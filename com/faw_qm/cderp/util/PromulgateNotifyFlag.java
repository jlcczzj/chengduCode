/**
 * 生成程序PromulgateNotifyFlag.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import com.faw_qm.framework.service.EnumeratedType;

/**
 * 采用标识
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyFlag extends EnumeratedType
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 默认构造函数
     */
    protected PromulgateNotifyFlag()
    {
    }

    /**
     * 工厂方法
     * @return PromulgateNotifyFlag
     */
    public static PromulgateNotifyFlag newPromulgateNotifyFlag()
    {
        return new PromulgateNotifyFlag();
    }

    /**
     * 根据字符串恢复匹配的类型
     * @param s 字符串
     * @return PromulgateNotifyFlag
     */
    public static PromulgateNotifyFlag toPromulgateNotifyFlag(String s)
    {
        return (PromulgateNotifyFlag) EnumeratedType.getEnumeratedType(s,
                valueSet);
    }

    /**
     * 得到缺省的类型
     * @return PromulgateNotifyFlag
     */
    public static PromulgateNotifyFlag getPromulgateNotifyFlagDefault()
    {
        return (PromulgateNotifyFlag) EnumeratedType
                .getDefaultEnumeratedType(valueSet);
    }

    /**
     * 得到所有的类型
     * @return PromulgateNotifyFlag[]
     */
    public static PromulgateNotifyFlag[] getPromulgateNotifyFlagSet()
    {
        PromulgateNotifyFlag aPromulgateNotifyFlag[] = new PromulgateNotifyFlag[valueSet.length];
        System
                .arraycopy(valueSet, 0, aPromulgateNotifyFlag, 0,
                        valueSet.length);
        return aPromulgateNotifyFlag;
    }

    /**
     * 根据locale得到所有的类型
     * @param locale1 时区
     * @return PromulgateNotifyFlag[]
     */
    public static PromulgateNotifyFlag[] getPromulgateNotifyFlagSet(
            Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        PromulgateNotifyFlag adocumenttype[] = new PromulgateNotifyFlag[types1.length];
        System.arraycopy(types1, 0, adocumenttype, 0, types1.length);
        return adocumenttype;
    }

    /**
     * 得到当前Locale的所有的类型
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getPromulgateNotifyFlagSet();
    }

    /**
     * 根据指定的Locale获得所有的类型
     * @param locale1 时区
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }

    /**
     * 根据指定的Locale获得所有的类型
     * @param locale1 时区
     * @return EnumeatedType[]
     */
    protected static EnumeratedType[] getTypesByLocal(Locale locale1)
    {
        EnumeratedType aenumeratedtype[] = null;
        if(localeSets == null)
            localeSets = new HashMap();
        else
            aenumeratedtype = (EnumeratedType[]) localeSets.get(locale1);
        if(aenumeratedtype == null)
        {
            try
            {
                aenumeratedtype = initializeLocaleSet(locale1);
            }
            catch (Exception _ex)
            {
            }
            localeSets.put(locale1, aenumeratedtype);
        }
        return aenumeratedtype;
    }

    /**
     * 根据指定的Locale实例化所有类型
     * @param locale 时区
     * @return EnumeratedType[]
     * @throws Exception
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale)
            throws Exception
    {
        Method method1 = PromulgateNotifyFlag.class.getMethod(
                "newPromulgateNotifyFlag", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //资源文件
    private static final String RESOURCE = "com.faw_qm.cderp.util.PromulgateNotifyFlagRB";

    //所有类型
    private static EnumeratedType valueSet[] = null;

    //所有类型的集合(按照Local存放)
    private static HashMap localeSets;
    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
        }
        catch (Exception exception)
        {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
