package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 *使用结构列表标题资源文件(英文信息)。
 * @author 王海军
 * @version 1.0
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

public class PartUsageListRB_en_US extends ListResourceBundle
{
    public PartUsageListRB_en_US()
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
                "com.faw_qm.part.PartUsageList.PARTNUMBER", "partnumber,PartNumber,PartNumber state,10,false,true"
            },
            {
                "com.faw_qm.part.PartUsageList.PARTNAME", "partname,PartName,PartName state,20,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.VIEW", "view,View,View state,30,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.LIFECYCLESTATE", "lifecyclestate,LifecycleState,LifecycleState state,40,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.TYPE", "type,Type,type state,50,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.SOURCE", "source,Source,Source state,60,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.VERSION", "version,Version,Version state,70,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.UNIT", "unit,Unit,Unit state,80,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.QUANTITY", "quantity,Quantity,Quantity state,90,false,true"
            }
            //CCBegin SS1
            ,
            {
                "com.faw_qm.part.PartUsageList.BOMITEM", "bomItem,BomItem,BomItem state,100,false,true"
            }
            ,
            {
                "com.faw_qm.part.PartUsageList.SUBGROUP", "subUnitNumber,subUnitNumber,subUnitNumber state,110,false,true"
            }
            //CCEnd SS1
            //CCBegin SS2
            ,
            {
                "com.faw_qm.part.PartUsageList.PROVERSION", "proVersion,proVersion,proVersion state,120,false,true"
            }
            //CCEnd SS2
            
        });
    }
}
