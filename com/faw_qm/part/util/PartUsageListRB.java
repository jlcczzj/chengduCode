package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * ʹ�ýṹ�б������Դ�ļ�(������Ϣ)��
 * @author ������
 * @version 1.0
 * SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
 */

public class PartUsageListRB extends ListResourceBundle
{
    public PartUsageListRB()
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
                    "com.faw_qm.part.PartUsageList.NUMBER", "number,���,��� state,10,false,true"
                },
                {
                    "com.faw_qm.part.PartUsageList.NAME", "name,����,���� state,20,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.VIEW", "viewName,��ͼ,��ͼ state,30,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.LIFECYCLESTATE", "state,״̬,״̬ state,40,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.TYPE", "type,����,���� state,50,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.SOURCE", "producedBy,��Դ,��Դ state,60,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.VERSION", "iterationID,�汾,�汾 state,70,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.UNIT", "unitName,��λ,��λ state,80,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.QUANTITY", "quantityString,����,���� state,90,false,true"
                }
                //CCBegin SS1
                ,
                {
                    "com.faw_qm.part.PartUsageList.BOMITEM", "bomItem,BOM����,BOM���� state,100,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.SUBUNITNUMBER", "subUnitNumber,����,���� state,110,false,true"
                }
                //CCEnd SS1
		            //CCBegin SS2
		            ,
		            {
		                "com.faw_qm.part.PartUsageList.PROVERSION", "proVersion,�����汾,�����汾,120,false,true"
		            }
		            //CCEnd SS2
        });
    }
}
