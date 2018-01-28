/** 生成程序 ProducedByRB_zh_CN.java    1.0    2003/03/03
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * SS1 2013-1-21  刘家坤 原因：零部件“来源”属性增加双重路线、参数页
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * 零部件来源资源文件(中文信息).
 * @author 吴先超
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
                "com.faw_qm.part.ProducedBy.MAKE", "make,自制,自制状态,10,true,true"
            },
            {
                "com.faw_qm.part.ProducedBy.BUY", "buy,外购,外购状态,20,false,true"
            },
            {
                "com.faw_qm.part.ProducedBy.OTHER", "other,其它,其它状态,30,false,true"
            }
            ,  //CCBegin  SS1
            {
                "com.faw_qm.part.ProducedBy.SHUANG", "shuang,双重路线,双重路线状态,30,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.CANSHUYE", "canshuye,参数页,参数页状态,40,false,true"
           } //CCEnd  SS1
        });
    }
}
