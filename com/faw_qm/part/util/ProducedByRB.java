/** 生成程序 ProducedByRB.java    1.0    2003/03/03
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * 零部件来源资源文件(中文信息).
 * @author 吴先超
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
                "com.faw_qm.part.ProducedBy.MAKE", "make,自制,自制状态,10,true,true"
            },
            {
                "com.faw_qm.part.ProducedBy.BUY", "buy,外购,外购状态,20,false,true"
            },
            {
                "com.faw_qm.part.ProducedBy.OTHER", "other,其它,其它状态,30,false,true"
            }
        });
    }
}
