/** 生成程序 ProducedBy.java    1.0    2003/02/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/01 丛得成 修改原因 添加JDBC查询功能,解决通用搜索零部件异常 
 *                               备注 《广义部件搜索》
 * SS1 2013-1-21  刘家坤 原因：零部件“来源”属性增加双重路线、参数页
 */

package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.io.Serializable;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * 零部件来源。
 * @author 吴先超
 * @version 1.0
 */

public class ProducedBy extends EnumeratedType implements Serializable
{
    private static final String RESOURCE = "com.faw_qm.part.util.ProducedByRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    //CCBegin by zhangq 20080626
    //和解放的序列化ID不一致，为了确保对象能正确的序列化，修改序列化ID。
    //static final long serialVersionUID = -1455822243655650163L;
    static final long serialVersionUID = -1997436666052404596L;
    //CCEnd by zhangq 20080626
    /** 用来缓存零部件来源类型的集合。*/
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
     * 构造函数。
     */
    public ProducedBy()//CR1
    {

    }


    /**
     * 调用构造函数，创建新的对象。(工厂方法)。
     * @return ProducedBy
     */
    public static ProducedBy newProducedBy()
    {
        return new ProducedBy();
    }


    /**
     * 根据字符串恢复匹配的内容类型。
     * @param s Sring
     * @return ProducedBy
     */
    public static ProducedBy toProducedBy(String s)
    {
        //return (ProducedBy) EnumeratedType.getEnumeratedType(s, valueSet);
        return (ProducedBy)data.get(s);
    }


    /**
     * 得到缺省的内容类型。
     * @return ProducedBy
     */
    public static ProducedBy getProducedByDefault()
    {
        return (ProducedBy) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * 得到所有的内容类型。
     * @return ProducedBy[]
     */
    public static ProducedBy[] getProducedBySet()
    {
        ProducedBy source[] = new ProducedBy[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * 根据locale 得到所有的内容类型。
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
     * 得到当前Locale 的所有的内容类型(实现父类的方法)。
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getProducedBySet();
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
        Method method1 = ProducedBy.class.getMethod("newProducedBy", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }

}
