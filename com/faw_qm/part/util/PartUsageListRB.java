package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * 使用结构列表标题资源文件(中文信息)。
 * @author 王海军
 * @version 1.0
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
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
                    "com.faw_qm.part.PartUsageList.NUMBER", "number,编号,编号 state,10,false,true"
                },
                {
                    "com.faw_qm.part.PartUsageList.NAME", "name,名称,名称 state,20,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.VIEW", "viewName,视图,视图 state,30,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.LIFECYCLESTATE", "state,状态,状态 state,40,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.TYPE", "type,类型,类型 state,50,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.SOURCE", "producedBy,来源,来源 state,60,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.VERSION", "iterationID,版本,版本 state,70,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.UNIT", "unitName,单位,单位 state,80,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.QUANTITY", "quantityString,数量,数量 state,90,false,true"
                }
                //CCBegin SS1
                ,
                {
                    "com.faw_qm.part.PartUsageList.BOMITEM", "bomItem,BOM行项,BOM行项 state,100,false,true"
                }
                ,
                {
                    "com.faw_qm.part.PartUsageList.SUBUNITNUMBER", "subUnitNumber,子组,子组 state,110,false,true"
                }
                //CCEnd SS1
		            //CCBegin SS2
		            ,
		            {
		                "com.faw_qm.part.PartUsageList.PROVERSION", "proVersion,生产版本,生产版本,120,false,true"
		            }
		            //CCEnd SS2
        });
    }
}
