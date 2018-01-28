/** ���ɳ��� Unit.java    1.0    2003/02/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * �㲿����λ��
 * @author ���ȳ�
 * @version 1.0
 */

public class Unit extends EnumeratedType
{
    /**
     * ���캯����
     */
    protected Unit()
    {

    }


    /**
     * ���ù��캯���������µĶ���(��������)��
     * @return Unit
     */
    public static Unit newUnit()
    {
        return new Unit();
    }


    /**
     * �����ַ����ָ�ƥ����������͡�
     * @param s String
     * @return Unit
     */
    public static Unit toUnit(String s)
    {
        //return (Unit) EnumeratedType.getEnumeratedType(s, valueSet);
        return (Unit)data.get(s);
    }


    /**
     * �õ�ȱʡ���������͡�
     * @return Unit
     */
    public static Unit getUnitDefault()
    {
        return (Unit) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * �õ����е��������͡�
     * @return Unit[]
     */
    public static Unit[] getUnitSet()
    {
        Unit source[] = new Unit[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * ����locale �õ����е��������͡�
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
     * �õ���ǰLocale �����е���������(ʵ�ָ���ķ���)��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getUnitSet();
    }


    /**
     * ����ָ����Locale ������е���������(ʵ�ָ���ķ���)��
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }


    /**
     * ����ָ����Locale ������е��������͡�
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
     * ��ʼ�����ػ���Ϣ���ϡ�
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
    /** ����������Ч�����͵ļ��ϡ�*/
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
    //�ͽ�ŵ����л�ID��һ�£�Ϊ��ȷ����������ȷ�����л����޸����л�ID��
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = -4402890107646386000L; 
    //CCEnd by zhangq 20080626
}
