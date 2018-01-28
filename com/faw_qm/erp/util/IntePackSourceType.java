/**
 * ���ɳ���QMCompositiveType.java	1.0              2007-9-24
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.util;

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
public class IntePackSourceType extends EnumeratedType implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String RESOURCE = "com.faw_qm.erp.util.IntePackSourceTypeRB";

    private static EnumeratedType valueSet[] = null;

    private static HashMap localeSets;

    /** �������漯�ɰ���Դ���͵ļ��ϡ�*/
    private static HashMap data = new HashMap();
    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
            for (int i = 0; i < valueSet.length; i++)
            {
                data.put(((IntePackSourceType) valueSet[i]).getValue(),
                        valueSet[i]);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static final IntePackSourceType PROMULGATENOTIFY = toIntePackSourceType("promulgateNotify");

    public static final IntePackSourceType ADOPTNOTICE = toIntePackSourceType("adoptNotice");
    
    //��������ERP���ݲ��û��ߵķ�ʽ
    public static final IntePackSourceType BASELINE = toIntePackSourceType("baseLine");
    public static final IntePackSourceType technicsRouteList = toIntePackSourceType("technicsRouteList");

    /**
     * ���캯����
     */
    protected IntePackSourceType()
    {
    }

    /**
     * ���ù��캯���������µĶ���(��������)��
     * @return IntePackSourceType
     */
    public static IntePackSourceType newIntePackSourceType()
    {
        return new IntePackSourceType();
    }

    /**
     * �����ַ����ָ�ƥ��ļ��ɰ���Դ���͡�
     * @param s String
     * @return IntePackSourceType
     */
    public static IntePackSourceType toIntePackSourceType(String s)
    {
        return (IntePackSourceType) data.get(s);
    }

    /**
     * �õ�ȱʡ�ļ��ɰ���Դ���͡�
     * @return QMPartType
     */
    public static IntePackSourceType getIntePackSourceTypeDefault()
    {
        return (IntePackSourceType) EnumeratedType
                .getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����еļ��ɰ���Դ���͡�
     * @return IntePackSourceType[]
     */
    public static IntePackSourceType[] getIntePackSourceTypeSet()
    {
        IntePackSourceType source[] = new IntePackSourceType[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }

    /**
     * ����locale �õ����еļ��ɰ���Դ���͡�
     * @param locale1 Locale
     * @return IntePackSourceType[]
     */
    public static IntePackSourceType[] getIntePackSourceTypeSet(Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        IntePackSourceType source[] = new IntePackSourceType[types1.length];
        System.arraycopy(types1, 0, source, 0, types1.length);
        return source;
    }

    /**
     * �õ���ǰLocale �����еļ��ɰ���Դ����(ʵ�ָ���ķ���)��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getIntePackSourceTypeSet();
    }

    /**
     * ����ָ����Locale ������еļ��ɰ���Դ����(ʵ�ָ���ķ���)��
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }

    /**
     * ����ָ����Locale ������еļ��ɰ���Դ���͡�
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
     * ��ʼ�����ػ���Ϣ���ϣ�����ָ����Locale ʵ�������м��ɰ���Դ���͡�
     * @param locale Locale
     * @return EnumeratedType[]
     * @throws Exception
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale)
            throws Exception
    {
        Method method1 = IntePackSourceType.class.getMethod(
                "newIntePackSourceType", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }
}
