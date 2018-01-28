/** 生成程序 Unit.java    1.0    2003/02/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * 零部件单位。
 * @author 吴先超
 * @version 1.0
 */

public class Unit extends EnumeratedType
{
    /**
     * 构造函数。
     */
    protected Unit()
    {

    }


    /**
     * 调用构造函数，创建新的对象。(工厂方法)。
     * @return Unit
     */
    public static Unit newUnit()
    {
        return new Unit();
    }


    /**
     * 根据字符串恢复匹配的内容类型。
     * @param s String
     * @return Unit
     */
    public static Unit toUnit(String s)
    {
        //return (Unit) EnumeratedType.getEnumeratedType(s, valueSet);
        return (Unit)data.get(s);
    }


    /**
     * 得到缺省的内容类型。
     * @return Unit
     */
    public static Unit getUnitDefault()
    {
        return (Unit) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * 得到所有的内容类型。
     * @return Unit[]
     */
    public static Unit[] getUnitSet()
    {
        Unit source[] = new Unit[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * 根据locale 得到所有的内容类型。
     * @param locale1 Locale
     * @return Unit[]
     */
    public static Unit[] getUnitSet(Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        Unit source[] = new Unit[types1.length];
        System.arraycopy(types1, 0, source, 0, types1.length);
        return source;
    }


    /**
     * 得到当前Locale 的所有的内容类型(实现父类的方法)。
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getUnitSet();
    }


    /**
     * 根据指定的Locale 获得所有的内容类型(实现父类的方法)。
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }


    /**
     * 根据指定的Locale 获得所有的内容类型。
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    protected static EnumeratedType[] getTypesByLocal(Locale locale1)
    {
        EnumeratedType aenumeratedtype[] = null;
        if (localeSets == null)
        {
            localeSets = new HashMap();
        }
        else
        {
            aenumeratedtype = (EnumeratedType[]) localeSets.get(locale1);
        }
        if (aenumeratedtype == null)
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
     * 初始化本地化信息集合。
     * @param locale Locale
     * @return EnumeratedType[]
     * @throws Exception
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale)
            throws Exception
    {
        Method method1 = Unit.class.getMethod("newUnit", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }

    private static final String RESOURCE = "com.faw_qm.part.util.UnitRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    /** 用来缓存有效性类型的集合。*/
    private static HashMap data = new HashMap();

    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
            for(int i=0;i<valueSet.length;i++)
            {
              data.put(((Unit)valueSet[i]).getValue(),valueSet[i]);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final Unit EA = toUnit("ea");
    public static final Unit AS_NEEDED = toUnit("as_needed");
    public static final Unit KG = toUnit("kg");
    public static final Unit M = toUnit("m");
    public static final Unit L = toUnit("l");
    //CCBegin by zhangq 20080626
    //和解放的序列化ID不一致，为了确保对象能正确的序列化，修改序列化ID。
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = -4402890107646386000L; 
    //CCEnd by zhangq 20080626
}
