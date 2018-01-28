/**
 * 生成程序 RouteListLevelType.java    1.0    2005/07/01
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
 * 路线级别枚举类。包括一级路线和二级路线。
 * @author not attributable
 * @version 1.0
 */
public class RouteListLevelType extends EnumeratedType
{

    /**
     * 构造函数
     */
    public RouteListLevelType()
    {}

    private static final String CLASS_RESOURCE = "com.faw_qm.technics.consroute.util.RouteListLevelTypeRB";
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
     * 目前有一级路线和二级路线两种类型。
     */
    public static RouteListLevelType FIRSTROUTE = toRouteListLevelType("FIRSTROUTE");
    public static RouteListLevelType SENCONDROUTE = toRouteListLevelType("SECONDROUTE");

    /**
     * 工厂方法
     * @return 路线级别枚举类（RouteListLevelType）
     */
    public static RouteListLevelType newRouteListLevelType()
    {
        return new RouteListLevelType();
    }

    /**
     * 根据字符串恢复匹配的内容类型
     * @param s String 字符串
     * @return RouteListLevelType 参见EnumeratedType.getEnumeratedType(s, valueSet);方法
     */
    public static RouteListLevelType toRouteListLevelType(String s)
    {
        return (RouteListLevelType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * 得到缺省的内容类型
     * @return RouteListLevelType 参见EnumeratedType.getDefaultEnumeratedType( valueSet);方法
     */
    public static RouteListLevelType getRouteListLevelTypeDefault()
    {
        return (RouteListLevelType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * 得到所有的内容类型
     * @return RouteListLevelType[] <br> 参见System.arraycopy(valueSet, 0, aRouteCategoryType, 0, valueSet.length);
     */
    public static RouteListLevelType[] getRouteListLevelTypeSet()
    {
        RouteListLevelType aRouteCategoryType[] = new RouteListLevelType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteCategoryType, 0, valueSet.length);
        return aRouteCategoryType;
    }

    /**
     * 得到当前Locale的所有的内容类型
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteListLevelTypeSet();
    }

    /**
     * 获得EnumeratedType类型的数组
     * @return EnumeratedType[] 返回一个此类型的变量。
     */
    protected EnumeratedType[] valueSet()
    {
        return valueSet;
    }

    /**
     * 根据指定的Locale获得所有的内容类型
     * @param locale Locale 指定的Locale
     * @return EnumeratedType[] 内容类型（Hashtable ：localeSets<br> key:locale; value:aenumeratedtype(是EnumeratedType[])）
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
     * @param locale Locale 指定的Locale
     * @throws Exception
     * @return EnumeratedType[] 实例化所有内容类型<br> （参见EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);）
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale) throws Exception
    {
        Method method1 = RouteListLevelType.class.getMethod("newRouteListLevelType", null);
        return EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);
    }

    /**
     *从枚举数组里取出display所存储的值
     * @param display String
     * @return String display所存储的值
     */
    public static String getValue(String display)
    {
        return RouteHelper.getValue(valueSet, display);
    }
}
