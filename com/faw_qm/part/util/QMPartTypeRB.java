/** ���ɳ��� QMPartTypeRB.java    1.0    2003/03/03
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * �㲿��������Դ�ļ�(������Ϣ).
 * @author ���ȳ�
 * @version 1.0
 */

public class QMPartTypeRB extends ListResourceBundle
{
    public QMPartTypeRB()
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
                "com.faw_qm.part.QMPartType.COMPONENT", "Component,���,���״̬,10,true,true"
            },
            {
                "com.faw_qm.part.QMPartType.SEPARABLE", "Separable,�ܳ�,�ܳ�״̬,20,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.STANDARD", "Standard,��׼��,��׼��״̬,30,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.FITTING", "Fitting,װ�ü�,װ�ü�״̬,40,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.VIRTUAL", "Virtual,���,���״̬,50,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.LOGICAL", "Logical,�߼��ܳ�,�߼��ܳ�״̬,60,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.MODEL", "Model,����,����״̬,70,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.ENGINE", "Engine,����,����״̬,80,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.ASSEMBLY", "Assembly,���պϼ�,���պϼ�״̬,90,false,true"
            }  
        });
    }
}
