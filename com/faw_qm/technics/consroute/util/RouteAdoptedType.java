/** ���ɳ���RouteCategoryType.java	1.0  2004/2
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * <p>Copyright: Copyright (c) 2004.2</p>
 * @author ������
 * @version 1.0
 */

package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;

/**
 * <p>Title:RouteAdoptedType.java</p> <p>Description:·���Ƿ񱻲���ö���ࡣ�������á�ȡ�� </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
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
     * Ŀǰ�����װ���������͡�
     */
    public static RouteAdoptedType ADOPT = toRouteAdoptedType("ADOPT");
    public static RouteAdoptedType CANCEL = toRouteAdoptedType("CANCEL");
    public static RouteAdoptedType EXIST = toRouteAdoptedType("EXIST");
    public static RouteAdoptedType NOTHING = toRouteAdoptedType("NOTHING");

    /**
     * ��������
     */
    public static RouteAdoptedType newRouteAdoptedType()
    {
        return new RouteAdoptedType();
    }

    /**
     * �����ַ����ָ�ƥ�����������
     */
    public static RouteAdoptedType toRouteAdoptedType(String s)
    {
        return (RouteAdoptedType)EnumeratedType.getEnumeratedType(s, valueSet);
    }

    /**
     * �õ�ȱʡ����������
     */
    public static RouteAdoptedType getRouteAdoptedTypeDefault()
    {
        return (RouteAdoptedType)EnumeratedType.getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����е���������
     */
    public static RouteAdoptedType[] getRouteAdoptedTypeSet()
    {
        RouteAdoptedType aRouteAdoptedType[] = new RouteAdoptedType[valueSet.length];
        System.arraycopy(valueSet, 0, aRouteAdoptedType, 0, valueSet.length);
        return aRouteAdoptedType;
    }

    /**
     * �õ���ǰLocale�����е���������
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
        Method method1 = RouteAdoptedType.class.getMethod("newRouteAdoptedType", null);
        return EnumeratedType.instantiateSet(method1, CLASS_RESOURCE, locale);
    }

    public static String getValue(String display)
    {
        return RouteHelper.getValue(valueSet, display);
    }

}
