/** ���ɳ��� QMPartTypeRB_zh_CN.java    1.0    2003/03/03
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * SS1 2013-1-21  ������ ԭ���㲿���ġ����͡����������ӣ����ܳɡ����Ӽ���ͬ��������ݡ���С��ܷ������������װ��ͼ������ҳ
  * SS2 2014-7-16 liunan ���90�ظ�������ӵķ��ܳɸ�Ϊ95
  * SS3 2014-8-11 �����������:�����Ʒ���͡��ͼ���
  * SS4 2018-1-8 �����������:���汾���պϼ�
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * �㲿��������Դ�ļ�(������Ϣ).
 * @author ���ȳ�
 * @version 1.0
 */

public class QMPartTypeRB_zh_CN extends ListResourceBundle
{
    public QMPartTypeRB_zh_CN()
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
                //CCBegin SS2
                //"com.faw_qm.part.QMPartType.", "Assembly,���պϼ�,���պϼ�״̬,90,false,true"
                "com.faw_qm.part.QMPartType.ASSEMBLY", "Assembly,���պϼ�,���պϼ�״̬,90,false,true"
                //CCEnd SS2
            },   //CCBegin  SS1
            //CCBegin SS2
            //{"com.faw_qm.part.QMPartType.BSXSEPARABLE", "bsxseparable,���ܳ�,���ܳ�״̬,90,false,true"},
            {"com.faw_qm.part.QMPartType.BSXSEPARABLE", "bsxseparable,���ܳ�,���ܳ�״̬,95,false,true"},
            //CCEnd SS2
						{"com.faw_qm.part.QMPartType.BSXSTANDARD", "bsxstandard,���Ӽ�,���Ӽ�״̬,100,false,true"},
						{"com.faw_qm.part.QMPartType.BSXVIRTUAL", "bsxvirtual,ͬ����,ͬ����״̬,110,false,true"},
						{"com.faw_qm.part.QMPartType.BSXLOGICAL", "bsxlogical,���,���״̬,120,false,true"},
						{"com.faw_qm.part.QMPartType.BSXASSEMBLY", "bsxassembly,���,���״̬,130,false,true"},
						{"com.faw_qm.part.QMPartType.SEALED", "Sealed,�ܷ��,�ܷ��״̬,140,false,true"},
						{"com.faw_qm.part.QMPartType.ELECTRIC", "Electric,������,������״̬,150,false,true"},
						{"com.faw_qm.part.QMPartType.EQUIPMENT", "Equipment,װ��ͼ,װ��ͼ״̬,160,false,true"},
						{"com.faw_qm.part.QMPartType.CANSHUYE", "canshuye,����ҳ,����ҳ״̬,170,false,true"}
						//CCEnd  SS1
		   //CCBegin SS3  
		   ,
           {
				 "com.faw_qm.part.QMPartType.BANCHENGPIN","Banchengpin,���Ʒ,���Ʒ״̬,180,false,true"      	
		   },
		  {
			     "com.faw_qm.part.QMPartType.DUANJIAN","Duanjian,�ͼ�,�ͼ�״̬,190,false,true"      	
		   }
			//CCEnd SS3
		   ,
		   {
			     "com.faw_qm.part.QMPartType.ASSEMBLYBB","Assemblybb,���汾���պϼ�,���汾���պϼ�״̬,190,false,true"      	
		   }
        });
    }
}