/** ���ɳ���RouteCategoryType.java	1.0  2004/2
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
 * ·������ö���ࡣ��������·�ߡ�װ��·�ߡ�
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
     * Ŀǰ�����װ���������͡�
     */
    public static RouteCategoryType MANUFACTUREROUTE = toRouteCategoryType("MANUFACTUREROUTE");
    public static RouteCategoryType ASSEMBLYROUTE = toRouteCategoryType("ASSEMBLYROUTE");

    /**
     * ��������
     */
    public static RouteCategoryType newRouteCategoryType()
    {
        return new RouteCategoryType();
    }

    /**
     * �����ַ����ָ�ƥ�����������
     */
    public static RouteCategoryType toRouteCategoryType(String s)
    {
        return (RouteCategoryType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * �õ�ȱʡ����������
     */
    public static RouteCategoryType getRouteCategoryTypeDefault()
    {
        return (RouteCategoryType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����е���������
     */
    public static RouteCategoryType[] getRouteCategoryTypeSet()
    {
        RouteCategoryType aRouteCategoryType[] = new RouteCategoryType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteCategoryType, 0, valueSet.length);
        return aRouteCategoryType;
    }

    /**
     * �õ���ǰLocale�����е���������
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
     * ����ָ����Locale������е���������
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
