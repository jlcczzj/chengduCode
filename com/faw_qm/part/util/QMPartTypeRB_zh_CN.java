/** 生成程序 QMPartTypeRB_zh_CN.java    1.0    2003/03/03
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * SS1 2013-1-21  刘家坤 原因：零部件的“类型”属性需增加：分总成、零杂件、同步器、轴齿、轴承、密封件、电器件、装置图、参数页
  * SS2 2014-7-16 liunan 序号90重复，将后加的分总成改为95
  * SS3 2014-8-11 增加零件类型:“半成品”和“锻件”
  * SS4 2018-1-8 增加零件类型:带版本工艺合件
  */
package com.faw_qm.part.util;

import java.util.ListResourceBundle;


/**
 * 零部件类型资源文件(中文信息).
 * @author 吴先超
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
                //CCBegin SS2
                //"com.faw_qm.part.QMPartType.", "Assembly,工艺合件,工艺合件状态,90,false,true"
                "com.faw_qm.part.QMPartType.ASSEMBLY", "Assembly,工艺合件,工艺合件状态,90,false,true"
                //CCEnd SS2
            },   //CCBegin  SS1
            //CCBegin SS2
            //{"com.faw_qm.part.QMPartType.BSXSEPARABLE", "bsxseparable,分总成,分总成状态,90,false,true"},
            {"com.faw_qm.part.QMPartType.BSXSEPARABLE", "bsxseparable,分总成,分总成状态,95,false,true"},
            //CCEnd SS2
						{"com.faw_qm.part.QMPartType.BSXSTANDARD", "bsxstandard,零杂件,零杂件状态,100,false,true"},
						{"com.faw_qm.part.QMPartType.BSXVIRTUAL", "bsxvirtual,同步器,同步器状态,110,false,true"},
						{"com.faw_qm.part.QMPartType.BSXLOGICAL", "bsxlogical,轴齿,轴齿状态,120,false,true"},
						{"com.faw_qm.part.QMPartType.BSXASSEMBLY", "bsxassembly,轴承,轴承状态,130,false,true"},
						{"com.faw_qm.part.QMPartType.SEALED", "Sealed,密封件,密封件状态,140,false,true"},
						{"com.faw_qm.part.QMPartType.ELECTRIC", "Electric,电器件,电器件状态,150,false,true"},
						{"com.faw_qm.part.QMPartType.EQUIPMENT", "Equipment,装置图,装置图状态,160,false,true"},
						{"com.faw_qm.part.QMPartType.CANSHUYE", "canshuye,参数页,参数页状态,170,false,true"}
						//CCEnd  SS1
		   //CCBegin SS3  
		   ,
           {
				 "com.faw_qm.part.QMPartType.BANCHENGPIN","Banchengpin,半成品,半成品状态,180,false,true"      	
		   },
		  {
			     "com.faw_qm.part.QMPartType.DUANJIAN","Duanjian,锻件,锻件状态,190,false,true"      	
		   }
			//CCEnd SS3
		   ,
		   {
			     "com.faw_qm.part.QMPartType.ASSEMBLYBB","Assemblybb,带版本工艺合件,带版本工艺合件状态,190,false,true"      	
		   }
        });
    }
}
