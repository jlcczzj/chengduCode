/** ���ɳ��� ProducedBy.java    1.0    2003/02/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/01 �Եó� �޸�ԭ�� ���JDBC��ѯ����,���ͨ�������㲿���쳣 
 *                               ��ע �����岿��������
 * SS1 2013-1-21  ������ ԭ���㲿������Դ����������˫��·�ߡ�����ҳ
 */

package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.io.Serializable;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * �㲿����Դ��
 * @author ���ȳ�
 * @version 1.0
 */

public class ProducedBy extends EnumeratedType implements Serializable
{
    private static final String RESOURCE = "com.faw_qm.part.util.ProducedByRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    //CCBegin by zhangq 20080626
    //�ͽ�ŵ����л�ID��һ�£�Ϊ��ȷ����������ȷ�����л����޸����л�ID��
    //static final long serialVersionUID = -1455822243655650163L;
    static final long serialVersionUID = -1997436666052404596L;
    //CCEnd by zhangq 20080626
    /** ���������㲿����Դ���͵ļ��ϡ�*/
    private static HashMap data = new HashMap();
    
    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
            for(int i=0;i<valueSet.length;i++)
            {
              data.put(((ProducedBy)valueSet[i]).getValue(),valueSet[i]);
            }
        }
        catch (Exception ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final ProducedBy MAKE = toProducedBy("make");
    public static final ProducedBy BUY = toProducedBy("buy");
    //CCBegin  SS1
    public static final ProducedBy SHUANG = toProducedBy("shuang");
    public static final ProducedBy LINGJIANBIAO = toProducedBy("lingjianbiao");
    //CCEnd  SS1


    /**
     * ���캯����
     */
    public ProducedBy()//CR1
    {

    }


    /**
     * ���ù��캯���������µĶ���(��������)��
     * @return ProducedBy
     */
    public static ProducedBy newProducedBy()
    {
        return new ProducedBy();
    }


    /**
     * �����ַ����ָ�ƥ����������͡�
     * @param s Sring
     * @return ProducedBy
     */
    public static ProducedBy toProducedBy(String s)
    {
        //return (ProducedBy) EnumeratedType.getEnumeratedType(s, valueSet);
        return (ProducedBy)data.get(s);
    }


    /**
     * �õ�ȱʡ���������͡�
     * @return ProducedBy
     */
    public static ProducedBy getProducedByDefault()
    {
        return (ProducedBy) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * �õ����е��������͡�
     * @return ProducedBy[]
     */
    public static ProducedBy[] getProducedBySet()
    {
        ProducedBy source[] = new ProducedBy[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * ����locale �õ����е��������͡�
     * @return ProducedBy[]
     * @param locale1 Locale
     */
    public static ProducedBy[] getProducedBySet(Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        ProducedBy source[] = new ProducedBy[types1.length];
        System.arraycopy(types1, 0, source, 0, types1.length);
        return source;
    }


    /**
     * �õ���ǰLocale �����е���������(ʵ�ָ���ķ���)��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getProducedBySet();
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
        Method method1 = ProducedBy.class.getMethod("newProducedBy", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }

}
