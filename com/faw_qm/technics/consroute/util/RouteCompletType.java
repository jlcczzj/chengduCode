/** ���ɳ���RouteCompletType.java	1.0  2004/2
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * <p>Copyright: Copyright (c) 2004.2</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */

package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;

/**
 * ���Ϸ�ʽ
 * <p>Title:�ձ�֪ͨ��ͨ�����ַ�ʽ�����㲿�������㲿�����ϺͰ���׼���ϣ���ѡ��������������׼ </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm</p>
 * @author �ܴ�Ԫ
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
     * Ŀǰ�����װ���������͡�
     */
    public static RouteCompletType PART = toRouteCompletType("PART");
    public static RouteCompletType ROUTE = toRouteCompletType("ROUTE");

    /**
     * ��������
     */
    public static RouteCompletType newRouteCompletType()
    {
        return new RouteCompletType();
    }

    /**
     * �����ַ����ָ�ƥ�����������
     */
    public static RouteCompletType toRouteCompletType(String s)
    {
        return(RouteCompletType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * �õ�ȱʡ����������
     */
    public static RouteCompletType getRouteCompletTypeDefault()
    {
        return(RouteCompletType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����е���������
     */
    public static RouteCompletType[] getRouteCompletTypeSet()
    {
        RouteCompletType aRouteCompletType[] = new RouteCompletType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteCompletType, 0, valueSet.length);
        return aRouteCompletType;
    }

    /**
     * �õ���ǰLocale�����е���������
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
     * ����ָ����Locale������е���������
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
     * ����ָ����Localeʵ����������������
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
