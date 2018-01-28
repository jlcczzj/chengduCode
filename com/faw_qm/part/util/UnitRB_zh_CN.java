/** 生成程序 UnitRB_zh_CN.java    1.0    2003/03/03
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * SS1 2013-1-21  刘家坤 原因：零部件的“单位”属性需增加：件、台、按需。
  * SS2 增加单位“个” liunan 2017-6-5
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * 零部件使用数量的单位资源文件(中文信息).
 * @author 吴先超
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
                "com.faw_qm.part.util.Unit.EA", "ea,必装,必装状态,10,true,true"
            },
            {
                "com.faw_qm.part.util.Unit.$TH", "$TH,替换,替换状态,20,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$BP", "$BP,必装匹配,必装匹配状态,30,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$BX", "$BX,必装选择,必装选择状态,40,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$XP", "$XP,选装匹配,选装匹配状态,50,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$XR", "$XR,任意选装,任意选装状态,60,false,true"
            },
            {
                "com.faw_qm.part.util.Unit.$XT", "$XT,替换选装,替换选装状态,70,false,true"
            },//CCBegin  SS1
            {"com.faw_qm.part.util.Unit.BSXEA", "bsxea,件,每件状态,11,true,true"},
						{"com.faw_qm.part.util.Unit.STAGE", "stage,台,每台状态,21,false,true"},
						{"com.faw_qm.part.util.Unit.AS_NEEDED", "as_needed,按需,按照所需状态,31,false,true" }
							//CCEnd  SS1
						//CCBegin SS2
						,
						{"com.faw_qm.part.util.Unit.GE", "ge,个,个,80,false,true" }
						//CCEnd SS2
        });
    }
}
