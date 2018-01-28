/** 生成程序 QMPartType.java    1.0    2003/02/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/01 丛得成 修改原因 添加JDBC查询功能,解决通用搜索零部件异常 
 *                               备注 《广义部件搜索》
 * SS1 2013-1-21  刘家坤 原因：零部件的“类型”属性需增加：分总成、零杂件、同步器、轴齿、轴承、密封件、电器件、装置图、参数页
 */

package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.io.Serializable;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * 零部件类型。
 * @author 吴先超
 * @version 1.0
 */

public class QMPartType extends EnumeratedType implements Serializable
{

    private static final String RESOURCE = "com.faw_qm.part.util.QMPartTypeRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    //CCBegin by zhangq 20080626
    //和解放的序列化ID不一致，为了确保对象能正确的序列化，修改序列化ID。
    //static final long serialVersionUID = -1455822243655650163L;
    static final long serialVersionUID = -2650689762383080836L;
    //CCEnd by zhangq 20080626
    /** 用来缓存有效性类型的集合。*/
    private static HashMap data = new HashMap();
    
    static
    {
        try 
        {
            valueSet = initializeLocaleSet(null);
            for(int i=0;i<valueSet.length;i++)
            {
              data.put(((QMPartType)valueSet[i]).getValue(),valueSet[i]);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }


    //public static final QMPartType ASSEMBLY = toQMPartType("assembly");
    //public static final QMPartType PART = toQMPartType("part");
    public static final QMPartType PRODUCT = toQMPartType("product");
    public static final QMPartType STANDARD = toQMPartType("standard");

    public static final QMPartType PUNCH = toQMPartType("punch");
    public static final QMPartType COMPONENT = toQMPartType("component");
    public static final QMPartType CASTING = toQMPartType("casting");
    public static final QMPartType PIPY = toQMPartType("pipy");
    public static final QMPartType NOMETAL = toQMPartType("nometal");
    public static final QMPartType SEPARABLE = toQMPartType("separable");
    public static final QMPartType FITTING = toQMPartType("fitting");
    public static final QMPartType VIRTUAL = toQMPartType("virtual");
    public static final QMPartType LOGICAL = toQMPartType("logical");
    public static final QMPartType MODEL = toQMPartType("model");
    public static final QMPartType ENGINE = toQMPartType("engine");
    public static final QMPartType WELD = toQMPartType("weld");
    public static final QMPartType ASSEMBLAGE = toQMPartType("assemblage");
    public static final QMPartType BITE = toQMPartType("bite");
    public static final QMPartType TRIALPRODUCT = toQMPartType("trialproduct");
    public static final QMPartType PAINTED = toQMPartType("painted");
  
    //CCBegin  SS1
        public static final QMPartType BSXSEPARABLE = toQMPartType("bsxseparable");
		public static final QMPartType BSXSTANDARD = toQMPartType("bsxstandard");
		public static final QMPartType BSXVIRTUAL = toQMPartType("bsxvirtual");
		public static final QMPartType BSXLOGICAL = toQMPartType("bsxlogical");
		public static final QMPartType ASSEMBLY = toQMPartType("assembly");
		public static final QMPartType SEALED = toQMPartType("Sealed");
		public static final QMPartType ELECTRIC = toQMPartType("Electric");
		public static final QMPartType EQUIPMENT = toQMPartType("Equipment");
		public static final QMPartType CANSHUYE = toQMPartType("canshuye");
   //CCEnd  SS1


    /**
     * 构造函数。
     */
    public QMPartType()//CR1
    {

    }


    /**
     * 调用构造函数，创建新的对象。(工厂方法)。
     * @return QMPartType
     */
    public static QMPartType newQMPartType()
    {
        return new QMPartType();
    }


    /**
     * 根据字符串恢复匹配的零部件类型。
     * @param s String
     * @return QMPartType
     */
    public static QMPartType toQMPartType(String s)
    {
        //return (QMPartType) EnumeratedType.getEnumeratedType(s, valueSet);
        return (QMPartType)data.get(s);
    }


    /**
     * 得到缺省的零部件类型。
     * @return QMPartType
     */
    public static QMPartType getQMPartTypeDefault()
    {
        return (QMPartType) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * 得到所有的零部件类型。
     * @return QMPartType[]
     */
    public static QMPartType[] getQMPartTypeSet()
    {
        QMPartType source[] = new QMPartType[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * 根据locale 得到所有的零部件类型。
     * @param locale1 Locale
     * @return QMPartType[]
     */
    public static QMPartType[] getQMPartTypeSet(Locale locale1)
    {
        EnumeratedType[] types1 = getTypesByLocal(locale1);
        QMPartType source[] = new QMPartType[types1.length];
        System.arraycopy(types1, 0, source, 0, types1.length);
        return source;
    }


    /**
     * 得到当前Locale 的所有的零部件类型(实现父类的方法)。
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getQMPartTypeSet();
    }


    /**
     * 根据指定的Locale 获得所有的零部件类型(实现父类的方法)。
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }


    /**
     * 根据指定的Locale 获得所有的零部件类型。
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
     * 初始化本地化信息集合，根据指定的Locale 实例化所有零部件类型。
     * @param locale Locale
     * @return EnumeratedType[]
     * @throws Exception
     */
    private static EnumeratedType[] initializeLocaleSet(Locale locale)
            throws Exception
    {
        Method method1 = QMPartType.class.getMethod("newQMPartType", null);
        return EnumeratedType.instantiateSet(method1, RESOURCE, locale);
    }
}
