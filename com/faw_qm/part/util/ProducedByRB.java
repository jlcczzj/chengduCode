/** ���ɳ��� ProducedByRB.java    1.0    2003/03/03
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * �㲿����Դ��Դ�ļ�(������Ϣ).
 * @author ���ȳ�
 * @version 1.0
 */

public class ProducedByRB extends ListResourceBundle
{
    public ProducedByRB()
    {
    }
    protected Object[][] getContents()
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
        });
    }
}
