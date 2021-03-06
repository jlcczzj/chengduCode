/**
 * 生成程序RouteCodeIBANameRB_zh_CN.java	1.0              2007-11-27
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class RouteCodeIBANameRB_zh_CN extends ListResourceBundle
{
    public RouteCodeIBANameRB_zh_CN()
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
                    "com.faw_qm.jferp.util.SX", "SX,水箱车间过程流程,水箱车间,10,true,true"
               },
               {
                    "com.faw_qm.jferp.util.LQ", "LQ,中冷器车间过程流程,中冷器车间,20,false,true"
               },
               {
                    "com.faw_qm.jferp.util.CY", "CY,冲压车间过程流程,冲压车间,30,false,true"
               },
               {
                   "com.faw_qm.jferp.util.DH", "DH,镀焊车间过程流程,镀焊车间,40,false,true"
               },
               {
                   "com.faw_qm.jferp.util.SZ", "SZ,工装制造车间过程流程,工装制造车间,5,false,true"
               }
               ,
               {
                   "com.faw_qm.jferp.util.SR", "SR,散热器分公司过程流程,散热器分公司,6,false,true"
               }
        });
    }
}
