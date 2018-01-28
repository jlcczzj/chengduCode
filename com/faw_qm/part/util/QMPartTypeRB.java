/** 生成程序 QMPartTypeRB.java    1.0    2003/03/03
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * 零部件类型资源文件(中文信息).
 * @author 吴先超
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
                "com.faw_qm.part.QMPartType.COMPONENT", "Component,零件,零件状态,10,true,true"
            },
            {
                "com.faw_qm.part.QMPartType.SEPARABLE", "Separable,总成,总成状态,20,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.STANDARD", "Standard,标准件,标准件状态,30,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.FITTING", "Fitting,装置件,装置件状态,40,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.VIRTUAL", "Virtual,组件,组件状态,50,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.LOGICAL", "Logical,逻辑总成,逻辑总成状态,60,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.MODEL", "Model,车型,车型状态,70,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.ENGINE", "Engine,机型,机型状态,80,false,true"
            },
            {
                "com.faw_qm.part.QMPartType.ASSEMBLY", "Assembly,工艺合件,工艺合件状态,90,false,true"
            }  
        });
    }
}
