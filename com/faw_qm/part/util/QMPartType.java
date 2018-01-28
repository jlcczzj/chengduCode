/** ���ɳ��� QMPartType.java    1.0    2003/02/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/01 �Եó� �޸�ԭ�� ���JDBC��ѯ����,���ͨ�������㲿���쳣 
 *                               ��ע �����岿��������
 * SS1 2013-1-21  ������ ԭ���㲿���ġ����͡����������ӣ����ܳɡ����Ӽ���ͬ��������ݡ���С��ܷ������������װ��ͼ������ҳ
 */

package com.faw_qm.part.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.io.Serializable;

import com.faw_qm.framework.service.EnumeratedType;


/**
 * �㲿�����͡�
 * @author ���ȳ�
 * @version 1.0
 */

public class QMPartType extends EnumeratedType implements Serializable
{

    private static final String RESOURCE = "com.faw_qm.part.util.QMPartTypeRB";
    private static EnumeratedType valueSet[] = null;
    private static HashMap localeSets;
    //CCBegin by zhangq 20080626
    //�ͽ�ŵ����л�ID��һ�£�Ϊ��ȷ����������ȷ�����л����޸����л�ID��
    //static final long serialVersionUID = -1455822243655650163L;
    static final long serialVersionUID = -2650689762383080836L;
    //CCEnd by zhangq 20080626
    /** ����������Ч�����͵ļ��ϡ�*/
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
     * ���캯����
     */
    public QMPartType()//CR1
    {

    }


    /**
     * ���ù��캯���������µĶ���(��������)��
     * @return QMPartType
     */
    public static QMPartType newQMPartType()
    {
        return new QMPartType();
    }


    /**
     * �����ַ����ָ�ƥ����㲿�����͡�
     * @param s String
     * @return QMPartType
     */
    public static QMPartType toQMPartType(String s)
    {
        //return (QMPartType) EnumeratedType.getEnumeratedType(s, valueSet);
        return (QMPartType)data.get(s);
    }


    /**
     * �õ�ȱʡ���㲿�����͡�
     * @return QMPartType
     */
    public static QMPartType getQMPartTypeDefault()
    {
        return (QMPartType) EnumeratedType.getDefaultEnumeratedType(valueSet);
    }


    /**
     * �õ����е��㲿�����͡�
     * @return QMPartType[]
     */
    public static QMPartType[] getQMPartTypeSet()
    {
        QMPartType source[] = new QMPartType[valueSet.length];
        System.arraycopy(valueSet, 0, source, 0, valueSet.length);
        return source;
    }


    /**
     * ����locale �õ����е��㲿�����͡�
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
     * �õ���ǰLocale �����е��㲿������(ʵ�ָ���ķ���)��
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getValueSet()
    {
        return getQMPartTypeSet();
    }


    /**
     * ����ָ����Locale ������е��㲿������(ʵ�ָ���ķ���)��
     * @param locale1 Locale
     * @return EnumeratedType[]
     */
    public EnumeratedType[] getLocaleSet(Locale locale1)
    {
        return getTypesByLocal(locale1);
    }


    /**
     * ����ָ����Locale ������е��㲿�����͡�
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
     * ��ʼ�����ػ���Ϣ���ϣ�����ָ����Locale ʵ���������㲿�����͡�
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
