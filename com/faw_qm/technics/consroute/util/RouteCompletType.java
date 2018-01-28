/** 生成程序RouteCompletType.java	1.0  2004/2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * <p>Copyright: Copyright (c) 2004.2</p>
 * @author 管春元
 * @version 1.0
 */

package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;

/**
 * 报毕方式
 * <p>Title:艺毕通知书通过两种方式报毕零部件：按零部件报毕和按艺准报毕，可选项包括：零件、艺准 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm</p>
 * @author 管春元
 * @version 1.0
 */
public class RouteCompletType
    extends EnumeratedType
{
    private static final String CLASS_RESOURCE = "com.faw_qm.technics.consroute.util.RouteCompletTypeRB";
    private static final EnumeratedType valueSet[];
    private static Hashtable localeSets;
    private static final long serialVersionUID = 1L;

    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
        }
        catch(Exception exception)
        {
            throw new ExceptionInInitializerError(exception);
        }
    }

    /**
     * 目前制造和装配两种类型。
     */
    public static RouteCompletType PART = toRouteCompletType("PART");
    public static RouteCompletType ROUTE = toRouteCompletType("ROUTE");

    /**
     * 工厂方法
     */
    public static RouteCompletType newRouteCompletType()
    {
        return new RouteCompletType();
    }

    /**
     * 根据字符串恢复匹配的内容类型
     */
    public static RouteCompletType toRouteCompletType(String s)
    {
        return(RouteCompletType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * 得到缺省的内容类型
     */
    public static RouteCompletType getRouteCompletTypeDefault()
    {
        return(RouteCompletType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * 得到所有的内容类型
     */
    public static RouteCompletType[] getRouteCompletTypeSet()
    {
        RouteCompletType aRouteCompletType[] = new RouteCompletType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteCompletType, 0, valueSet.length);
        return aRouteCompletType;
    }

    /**
     * 得到当前Locale的所有的内容类型
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteCompletTypeSet();
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
        if(localeSets==null)
        {
            localeSets = new Hashtable();
        }
        else
        {
            aenumeratedtype = (EnumeratedType[])localeSets.get(locale);
        }
        if(aenumeratedtype==null)
        {
            try
            {
                aenumeratedtype = initializeLocaleSet(locale);
            }
            catch(Exception _ex)
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
        Method method1 = RouteCompletType.class.getMethod("newRouteCompletType", null);
        return EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);
    }

    public static String getValue(String display)
    {
        return RouteHelper.getValue(valueSet, display);
    }

}
