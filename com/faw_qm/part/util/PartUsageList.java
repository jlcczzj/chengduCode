package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.io.Serializable;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * 使用结构列表标题。
 * @author 王海军
 * @version 1.0
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 */

public class PartUsageList extends EnumeratedType implements Serializable
{
    private static final String RESOURCE = "com.faw_qm.part.util.PartUsageListRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    static final long serialVersionUID = -1L;
    /** 用来缓存零部件来源类型的集合。*/
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
     * 构造函数。
     */
    protected PartUsageList()
    {

    }


    /**
     * 调用构造函数，创建新的对象。(工厂方法)。
     * @return ProducedBy
     */
    public static PartUsageList newPartUsageList()
    {
        return new PartUsageList();
    }


    /**
     * 根据字符串恢复匹配的内容类型。
     * @param s Sring
     * @return ProducedBy
     */
    public static PartUsageList toPartUsageList(String s)
    {
        //return (ProducedBy) EnumeratedType.getEnumeratedType(s, valueSet);
        return (PartUsageList)data.get(s);
    }


    /**
     * 得到缺省的内容类型。
     * @return ProducedBy
     */
    public static PartUsageList getPartUsageListDefault()
    {
        return (PartUsageList) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * 得到所有的内容类型。
     * @return ProducedBy[]
     */
    public static PartUsageList[] getPartUsageListSet()
    {
    	PartUsageList source[] = new PartUsageList[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * 根据locale 得到所有的内容类型。
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
     * 得到当前Locale 的所有的内容类型(实现父类的方法)。
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getPartUsageListSet();
    }


    /**
     * 根据指定的Locale 获得所有的内容类型(实现父类的方法)。
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }


    /**
     * 根据指定的Locale 获得所有的内容类型。
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
     * 初始化本地化信息集合。
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
