/**
 * 生成程序RouteCodeIBAName.java	1.0              2007-11-27
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import com.faw_qm.framework.service.EnumeratedType;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class RouteCodeIBAName extends EnumeratedType implements Serializable
{
    private static final String RESOURCE = "com.faw_qm.jferp.util.RouteCodeIBANameRB";

    private static EnumeratedType valueSet[] = null;

    private static HashMap localeSets;

    static final long serialVersionUID = 1L;

    /** 用来缓存部门代码对应的过程代码IBA属性名称的集合。*/
    private static HashMap data = new HashMap();
    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
            for (int i = 0; i < valueSet.length; i++)
            {
                data.put(((RouteCodeIBAName) valueSet[i]).getValue(),
                        valueSet[i]);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final RouteCodeIBAName SX = toRouteCodeIBAName("SX");

    public static final RouteCodeIBAName LQ = toRouteCodeIBAName("LQ");

    public static final RouteCodeIBAName CY = toRouteCodeIBAName("CY");

    public static final RouteCodeIBAName DH = toRouteCodeIBAName("DH");
    
    public static final RouteCodeIBAName SZ = toRouteCodeIBAName("DH");

    /**
     * 构造函数。
     */
    protected RouteCodeIBAName()
    {
    }

    /**
     * 调用构造函数，创建新的对象。(工厂方法)。
     * @return RouteCodeIBAName
     */
    public static RouteCodeIBAName newRouteCodeIBAName()
    {
        return new RouteCodeIBAName();
    }

    /**
     * 根据字符串恢复匹配的零部件的过程代码IBA属性名称。
     * @param s String
     * @return RouteCodeIBAName
     */
    public static RouteCodeIBAName toRouteCodeIBAName(String s)
    {
        return (RouteCodeIBAName) data.get(s);
    }

    /**
     * 得到缺省的零部件的过程代码IBA属性名称。
     * @return RouteCodeIBAName
     */
    public static RouteCodeIBAName getRouteCodeIBANameDefault()
    {
        return (RouteCodeIBAName) EnumeratedType
                .getDefaultEnumeratedType(valueSet);
    }

    /**
     * 得到所有的零部件的过程代码IBA属性名称。
     * @return RouteCodeIBAName[]
     */
    public static RouteCodeIBAName[] getRouteCodeIBANameSet()
    {
        RouteCodeIBAName source[] = new RouteCodeIBAName[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }

    /**
     * 根据locale 得到所有的零部件的过程代码IBA属性名称。
     * @param locale1 Locale
     * @return RouteCodeIBAName[]
     */
    public static RouteCodeIBAName[] getRouteCodeIBANameSet(Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        RouteCodeIBAName source[] = new RouteCodeIBAName[types1.length];
        System.arraycopy(types1, 0, source, 0, types1.length);
        return source;
    }

    /**
     * 得到当前Locale 的所有的零部件的过程代码IBA属性名称(实现父类的方法)。
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteCodeIBANameSet();
    }

    /**
     * 根据指定的Locale 获得所有的零部件的过程代码IBA属性名称(实现父类的方法)。
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }

    /**
     * 根据指定的Locale 获得所有的零部件的过程代码IBA属性名称。
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    protected static EnumeratedType[] getTypesByLocal(Locale locale1)
    {
        EnumeratedType aenumeratedtype[] = null;
        if(localeSets == null)
        {
            localeSets = new HashMap();
        }
        else
        {
            aenumeratedtype = (EnumeratedType[]) localeSets.get(locale1);
        }
        if(aenumeratedtype == null)
        {
            try
            {
                aenumeratedtype = initializeLocaleSet(locale1);
            }
            catch (Exception _ex)
            {
                _ex.printStackTrace();
            }
            localeSets.put(locale1, aenumeratedtype);
        }
        return aenumeratedtype;
    }

    /**
     * 初始化本地化信息集合，根据指定的Locale 实例化所有零部件的过程代码IBA属性名称。
     * @param locale Locale
     * @return EnumeratedType[]
     * @throws Exception
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale)
            throws Exception
    {
        Method method1 = RouteCodeIBAName.class.getMethod(
                "newRouteCodeIBAName", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }
}
