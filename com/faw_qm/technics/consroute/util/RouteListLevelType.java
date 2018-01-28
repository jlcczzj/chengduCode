/**
 * ���ɳ��� RouteListLevelType.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;

/**
 * ·�߼���ö���ࡣ����һ��·�ߺͶ���·�ߡ�
 * @author not attributable
 * @version 1.0
 */
public class RouteListLevelType extends EnumeratedType
{

    /**
     * ���캯��
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
     * Ŀǰ��һ��·�ߺͶ���·���������͡�
     */
    public static RouteListLevelType FIRSTROUTE = toRouteListLevelType("FIRSTROUTE");
    public static RouteListLevelType SENCONDROUTE = toRouteListLevelType("SECONDROUTE");

    /**
     * ��������
     * @return ·�߼���ö���ࣨRouteListLevelType��
     */
    public static RouteListLevelType newRouteListLevelType()
    {
        return new RouteListLevelType();
    }

    /**
     * �����ַ����ָ�ƥ�����������
     * @param s String �ַ���
     * @return RouteListLevelType �μ�EnumeratedType.getEnumeratedType(s, valueSet);����
     */
    public static RouteListLevelType toRouteListLevelType(String s)
    {
        return (RouteListLevelType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * �õ�ȱʡ����������
     * @return RouteListLevelType �μ�EnumeratedType.getDefaultEnumeratedType( valueSet);����
     */
    public static RouteListLevelType getRouteListLevelTypeDefault()
    {
        return (RouteListLevelType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����е���������
     * @return RouteListLevelType[] <br> �μ�System.arraycopy(valueSet, 0, aRouteCategoryType, 0, valueSet.length);
     */
    public static RouteListLevelType[] getRouteListLevelTypeSet()
    {
        RouteListLevelType aRouteCategoryType[] = new RouteListLevelType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteCategoryType, 0, valueSet.length);
        return aRouteCategoryType;
    }

    /**
     * �õ���ǰLocale�����е���������
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteListLevelTypeSet();
    }

    /**
     * ���EnumeratedType���͵�����
     * @return EnumeratedType[] ����һ�������͵ı�����
     */
    protected EnumeratedType[] valueSet()
    {
        return valueSet;
    }

    /**
     * ����ָ����Locale������е���������
     * @param locale Locale ָ����Locale
     * @return EnumeratedType[] �������ͣ�Hashtable ��localeSets<br> key:locale; value:aenumeratedtype(��EnumeratedType[])��
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
     * ����ָ����Localeʵ����������������
     * @param locale Locale ָ����Locale
     * @throws Exception
     * @return EnumeratedType[] ʵ����������������<br> ���μ�EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);��
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale) throws Exception
    {
        Method method1 = RouteListLevelType.class.getMethod("newRouteListLevelType", null);
        return EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);
    }

    /**
     *��ö��������ȡ��display���洢��ֵ
     * @param display String
     * @return String display���洢��ֵ
     */
    public static String getValue(String display)
    {
        return RouteHelper.getValue(valueSet, display);
    }
}
