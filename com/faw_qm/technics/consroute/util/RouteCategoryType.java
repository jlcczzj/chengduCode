/** 生成程序RouteCategoryType.java	1.0  2004/2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;

/**
 * 路线类型枚举类。包括制造路线、装配路线。
 */
public class RouteCategoryType extends EnumeratedType
{
    private static final String CLASS_RESOURCE = "com.faw_qm.technics.consroute.util.RouteCategoryTypeRB";
    private static final EnumeratedType valueSet[];
    private static Hashtable localeSets;
    private static final long serialVersionUID = 1L;

    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
        }catch(Exception exception)
        {
            throw new ExceptionInInitializerError(exception);
        }
    }

    /**
     * 目前制造和装配两种类型。
     */
    public static RouteCategoryType MANUFACTUREROUTE = toRouteCategoryType("MANUFACTUREROUTE");
    public static RouteCategoryType ASSEMBLYROUTE = toRouteCategoryType("ASSEMBLYROUTE");

    /**
     * 工厂方法
     */
    public static RouteCategoryType newRouteCategoryType()
    {
        return new RouteCategoryType();
    }

    /**
     * 根据字符串恢复匹配的内容类型
     */
    public static RouteCategoryType toRouteCategoryType(String s)
    {
        return (RouteCategoryType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * 得到缺省的内容类型
     */
    public static RouteCategoryType getRouteCategoryTypeDefault()
    {
        return (RouteCategoryType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * 得到所有的内容类型
     */
    public static RouteCategoryType[] getRouteCategoryTypeSet()
    {
        RouteCategoryType aRouteCategoryType[] = new RouteCategoryType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteCategoryType, 0, valueSet.length);
        return aRouteCategoryType;
    }

    /**
     * 得到当前Locale的所有的内容类型
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteCategoryTypeSet();
    }

    protected EnumeratedType[] valueSet()
    {
        return valueSet;
    }

    /**
     * 根据指定的Locale获得所有的内容类型
     */
    protected EnumeratedType[] getLocaleSet(Locale locale)
    {
        EnumeratedType aenumeratedtype[] = null;
        if(localeSets == null)
        {
            localeSets = new Hashtable();
        }else
        {
            aenumeratedtype = (EnumeratedType[])localeSets.get(locale);
        }
        if(aenumeratedtype == null)
        {
            try
            {
                aenumeratedtype = initializeLocaleSet(locale);
            }catch(Exception _ex)
            {}
            localeSets.put(locale, aenumeratedtype);
        }
        return aenumeratedtype;
    }

    /**
     * 根据指定的Locale实例化所有内容类型
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale) throws Exception
    {
        Method method1 = RouteCategoryType.class.getMethod("newRouteCategoryType", null);
        return EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);
    }

    public static String getValue(String display)
    {
        return RouteHelper.getValue(valueSet, display);
    }

}
