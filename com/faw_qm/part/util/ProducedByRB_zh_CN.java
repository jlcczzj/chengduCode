/** ���ɳ��� ProducedByRB_zh_CN.java    1.0    2003/03/03
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * SS1 2013-1-21  ������ ԭ���㲿������Դ����������˫��·�ߡ�����ҳ
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * �㲿����Դ��Դ�ļ�(������Ϣ).
 * @author ���ȳ�
 * @version 1.0
 */

public class ProducedByRB_zh_CN extends ListResourceBundle
{
    public ProducedByRB_zh_CN()
    {
    }
    public Object[][] getContents()
    {
        return contents;
    }
    static final Object contents[][];
    static
    {
        contents = (new Object[][]
        {
            {
                "com.faw_qm.part.ProducedBy.MAKE", "make,����,����״̬,10,true,true"
            },
            {
                "com.faw_qm.part.ProducedBy.BUY", "buy,�⹺,�⹺״̬,20,false,true"
            },
            {
                "com.faw_qm.part.ProducedBy.OTHER", "other,����,����״̬,30,false,true"
            }
            ,  //CCBegin  SS1
            {
                "com.faw_qm.part.ProducedBy.SHUANG", "shuang,˫��·��,˫��·��״̬,30,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.CANSHUYE", "canshuye,����ҳ,����ҳ״̬,40,false,true"
           } //CCEnd  SS1
        });
    }
}
