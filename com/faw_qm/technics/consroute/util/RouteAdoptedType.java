/** 生成程序RouteCategoryType.java	1.0  2004/2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * <p>Copyright: Copyright (c) 2004.2</p>
 * @author 赵立彬
 * @version 1.0
 */

package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;

/**
 * <p>Title:RouteAdoptedType.java</p> <p>Description:路线是否被采用枚举类。包括采用、取消 </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteAdoptedType extends EnumeratedType
{
    private static final String CLASS_RESOURCE = "com.faw_qm.technics.consroute.util.RouteAdoptedTypeRB";
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
    public static RouteAdoptedType ADOPT = toRouteAdoptedType("ADOPT");
    public static RouteAdoptedType CANCEL = toRouteAdoptedType("CANCEL");
    public static RouteAdoptedType EXIST = toRouteAdoptedType("EXIST");
    public static RouteAdoptedType NOTHING = toRouteAdoptedType("NOTHING");

    /**
     * 工厂方法
     */
    public static RouteAdoptedType newRouteAdoptedType()
    {
        return new RouteAdoptedType();
    }

    /**
     * 根据字符串恢复匹配的内容类型
     */
    public static RouteAdoptedType toRouteAdoptedType(String s)
    {
        return (RouteAdoptedType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * 得到缺省的内容类型
     */
    public static RouteAdoptedType getRouteAdoptedTypeDefault()
    {
        return (RouteAdoptedType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * 得到所有的内容类型
     */
    public static RouteAdoptedType[] getRouteAdoptedTypeSet()
    {
        RouteAdoptedType aRouteAdoptedType[] = new RouteAdoptedType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteAdoptedType, 0, valueSet.length);
        return aRouteAdoptedType;
    }

    /**
     * 得到当前Locale的所有的内容类型
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteAdoptedTypeSet();
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
        Method method1 = RouteAdoptedType.class.getMethod("newRouteAdoptedType", null);
        return EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);
    }

    public static String getValue(String display)
    {
        return RouteHelper.getValue(valueSet, display);
    }

}
