/**
 * ���ɳ���PromulgateNotifyFlag.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import com.faw_qm.framework.service.EnumeratedType;

/**
 * ���ñ�ʶ
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyFlag extends EnumeratedType
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Ĭ�Ϲ��캯��
     */
    protected PromulgateNotifyFlag()
    {
    }

    /**
     * ��������
     * @return PromulgateNotifyFlag
     */
    public static PromulgateNotifyFlag newPromulgateNotifyFlag()
    {
        return new PromulgateNotifyFlag();
    }

    /**
     * �����ַ����ָ�ƥ�������
     * @param s �ַ���
     * @return PromulgateNotifyFlag
     */
    public static PromulgateNotifyFlag toPromulgateNotifyFlag(String s)
    {
        return (PromulgateNotifyFlag) EnumeratedType.getEnumeratedType(s,
                valueSet);
    }

    /**
     * �õ�ȱʡ������
     * @return PromulgateNotifyFlag
     */
    public static PromulgateNotifyFlag getPromulgateNotifyFlagDefault()
    {
        return (PromulgateNotifyFlag) EnumeratedType
                .getDefaultEnumeratedType(valueSet);
    }

    /**
     * �õ����е�����
     * @return PromulgateNotifyFlag[]
     */
    public static PromulgateNotifyFlag[] getPromulgateNotifyFlagSet()
    {
        PromulgateNotifyFlag aPromulgateNotifyFlag[] = new PromulgateNotifyFlag[valueSet.length];
        System
                .arraycopy(valueSet, 0, aPromulgateNotifyFlag, 0,
                        valueSet.length);
        return aPromulgateNotifyFlag;
    }

    /**
     * ����locale�õ����е�����
     * @param locale1 ʱ��
     * @return PromulgateNotifyFlag[]
     */
    public static PromulgateNotifyFlag[] getPromulgateNotifyFlagSet(
            Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        PromulgateNotifyFlag adocumenttype[] = new PromulgateNotifyFlag[types1.length];
        System.arraycopy(types1, 0, adocumenttype, 0, types1.length);
        return adocumenttype;
    }

    /**
     * �õ���ǰLocale�����е�����
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getPromulgateNotifyFlagSet();
    }

    /**
     * ����ָ����Locale������е�����
     * @param locale1 ʱ��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }

    /**
     * ����ָ����Locale������е�����
     * @param locale1 ʱ��
     * @return EnumeatedType[]
     */
    protected static EnumeratedType[] getTypesByLocal(Locale locale1)
    {
        EnumeratedType aenumeratedtype[] = null;
        if(localeSets == null)
            localeSets = new HashMap();
        else
            aenumeratedtype = (EnumeratedType[]) localeSets.get(locale1);
        if(aenumeratedtype == null)
        {
            try
            {
                aenumeratedtype = initializeLocaleSet(locale1);
            }
            catch (Exception _ex)
            {
            }
            localeSets.put(locale1, aenumeratedtype);
        }
        return aenumeratedtype;
    }

    /**
     * ����ָ����Localeʵ������������
     * @param locale ʱ��
     * @return EnumeratedType[]
     * @throws Exception
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale)
            throws Exception
    {
        Method method1 = PromulgateNotifyFlag.class.getMethod(
                "newPromulgateNotifyFlag", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //��Դ�ļ�
    private static final String RESOURCE = "com.faw_qm.cderp.util.PromulgateNotifyFlagRB";

    //��������
    private static EnumeratedType valueSet[] = null;

    //�������͵ļ���(����Local���)
    private static HashMap localeSets;
    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
        }
        catch (Exception exception)
        {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
