package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.io.Serializable;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * ʹ�ýṹ�б���⡣
 * @author ������
 * @version 1.0
 * SS1 ����BOM��������� liuyang 2014-6-20
 */

public class PartUsageList extends EnumeratedType implements Serializable
{
    private static final String RESOURCE = "com.faw_qm.part.util.PartUsageListRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    static final long serialVersionUID = -1L;
    /** ���������㲿����Դ���͵ļ��ϡ�*/
    private static HashMap data = new HashMap();
    public static final PartUsageList NUMBER = toPartUsageList("number");
    public static final PartUsageList NAME = toPartUsageList("name");

    public static final PartUsageList VIEW = toPartUsageList("viewName");
    public static final PartUsageList STATE = toPartUsageList("state");
    public static final PartUsageList SOURCE = toPartUsageList("producedBy");
    public static final PartUsageList TYPE = toPartUsageList("type");
    public static final PartUsageList VERSION = toPartUsageList("iterationID");
    public static final PartUsageList UNIT = toPartUsageList("unitName");
    public static final PartUsageList QUANTITY = toPartUsageList("quantityString");
    //CCBegin SS1
    public static final PartUsageList BOMITEM = toPartUsageList("bomItem");
    public static final PartUsageList SUBUNITNUMBER = toPartUsageList("subUnitNumber");
    //CCEnd SS1
    
    static
    {
        try
        {
            valueSet = initializeLocaleSet(null);
            for(int i=0;i<valueSet.length;i++)
            {
              data.put(((PartUsageList)valueSet[i]).getValue(),valueSet[i]);
            }
        }
        catch (Exception ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }



    /**
     * ���캯����
     */
    protected PartUsageList()
    {

    }


    /**
     * ���ù��캯���������µĶ���(��������)��
     * @return ProducedBy
     */
    public static PartUsageList newPartUsageList()
    {
        return new PartUsageList();
    }


    /**
     * �����ַ����ָ�ƥ����������͡�
     * @param s Sring
     * @return ProducedBy
     */
    public static PartUsageList toPartUsageList(String s)
    {
        //return (ProducedBy) EnumeratedType.getEnumeratedType(s, valueSet);
        return (PartUsageList)data.get(s);
    }


    /**
     * �õ�ȱʡ���������͡�
     * @return ProducedBy
     */
    public static PartUsageList getPartUsageListDefault()
    {
        return (PartUsageList) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * �õ����е��������͡�
     * @return ProducedBy[]
     */
    public static PartUsageList[] getPartUsageListSet()
    {
    	PartUsageList source[] = new PartUsageList[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * ����locale �õ����е��������͡�
     * @return ProducedBy[]
     * @param locale1 Locale
     */
    public static PartUsageList[] getPartUsageListSet(Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        PartUsageList source[] = new PartUsageList[types1.length];
        System.arraycopy(types1, 0, source, 0, types1.length);
        return source;
    }


    /**
     * �õ���ǰLocale �����е���������(ʵ�ָ���ķ���)��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getPartUsageListSet();
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
        Method method1 = PartUsageList.class.getMethod("newPartUsageList", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }

}
