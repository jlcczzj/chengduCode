/** ���ɳ��� UnitRB_zh_CN.java    1.0    2003/03/03
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * SS1 2013-1-21  ������ ԭ���㲿���ġ���λ�����������ӣ�����̨�����衣
  * SS2 ���ӵ�λ������ liunan 2017-6-5
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * �㲿��ʹ�������ĵ�λ��Դ�ļ�(������Ϣ).
 * @author ���ȳ�
 * @version 1.0
 */

public class UnitRB_zh_CN extends ListResourceBundle
{
    public UnitRB_zh_CN()
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
                "com.faw_qm.part.util.Unit.EA", "ea,��װ,��װ״̬,10,true,true"
            },
            {
                "com.faw_qm.part.util.Unit.$TH", "$TH,�滻,�滻״̬,20,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$BP", "$BP,��װƥ��,��װƥ��״̬,30,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$BX", "$BX,��װѡ��,��װѡ��״̬,40,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$XP", "$XP,ѡװƥ��,ѡװƥ��״̬,50,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$XR", "$XR,����ѡװ,����ѡװ״̬,60,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$XT", "$XT,�滻ѡװ,�滻ѡװ״̬,70,false,true"
            },//CCBegin  SS1
            {"com.faw_qm.part.util.Unit.BSXEA", "bsxea,��,ÿ��״̬,11,true,true"},
						{"com.faw_qm.part.util.Unit.STAGE", "stage,̨,ÿ̨״̬,21,false,true"},
						{"com.faw_qm.part.util.Unit.AS_NEEDED", "as_needed,����,��������״̬,31,false,true" }
							//CCEnd  SS1
						//CCBegin SS2
						,
						{"com.faw_qm.part.util.Unit.GE", "ge,��,��,80,false,true" }
						//CCEnd SS2
        });
    }
}
