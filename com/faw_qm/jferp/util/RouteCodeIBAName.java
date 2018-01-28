/**
 * ���ɳ���RouteCodeIBAName.java	1.0              2007-11-27
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import com.faw_qm.framework.service.EnumeratedType;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class RouteCodeIBAName extends EnumeratedType implements Serializable
{
    private static final String RESOURCE = "com.faw_qm.jferp.util.RouteCodeIBANameRB";

    private static EnumeratedType valueSet[] = null;

    private static HashMap localeSets;

    static final long serialVersionUID = 1L;

    /** �������沿�Ŵ����Ӧ�Ĺ��̴���IBA�������Ƶļ��ϡ�*/
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
     * ���캯����
     */
    protected RouteCodeIBAName()
    {
    }

    /**
     * ���ù��캯���������µĶ���(��������)��
     * @return RouteCodeIBAName
     */
    public static RouteCodeIBAName newRouteCodeIBAName()
    {
        return new RouteCodeIBAName();
    }

    /**
     * �����ַ����ָ�ƥ����㲿���Ĺ��̴���IBA�������ơ�
     * @param s String
     * @return RouteCodeIBAName
     */
    public static RouteCodeIBAName toRouteCodeIBAName(String s)
    {
        return (RouteCodeIBAName) data.get(s);
    }

    /**
     * �õ�ȱʡ���㲿���Ĺ��̴���IBA�������ơ�
     * @return RouteCodeIBAName
     */
    public static RouteCodeIBAName getRouteCodeIBANameDefault()
    {
        return (RouteCodeIBAName) EnumeratedType
                .getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����е��㲿���Ĺ��̴���IBA�������ơ�
     * @return RouteCodeIBAName[]
     */
    public static RouteCodeIBAName[] getRouteCodeIBANameSet()
    {
        RouteCodeIBAName source[] = new RouteCodeIBAName[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }

    /**
     * ����locale �õ����е��㲿���Ĺ��̴���IBA�������ơ�
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
     * �õ���ǰLocale �����е��㲿���Ĺ��̴���IBA��������(ʵ�ָ���ķ���)��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getRouteCodeIBANameSet();
    }

    /**
     * ����ָ����Locale ������е��㲿���Ĺ��̴���IBA��������(ʵ�ָ���ķ���)��
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }

    /**
     * ����ָ����Locale ������е��㲿���Ĺ��̴���IBA�������ơ�
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
     * ��ʼ�����ػ���Ϣ���ϣ�����ָ����Locale ʵ���������㲿���Ĺ��̴���IBA�������ơ�
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
