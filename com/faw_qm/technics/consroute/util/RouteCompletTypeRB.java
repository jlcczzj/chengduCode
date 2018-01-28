/** 生成程序RouteCompletTypeRB.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * <p>Copyright: Copyright (c) 2004.2</p>
 * @author 管春元
 * @version 1.0
 */


package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * 报毕方式资源文件
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm </p>
 * @author 管春元
 * @version 1.0
 */
public class RouteCompletTypeRB
    extends ListResourceBundle
{


    public RouteCompletTypeRB()
    {

    }

    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents =
        {
        {
        "PART", "PART,零件,零件,10,true,true"
    }
        ,
        {
        "ROUTE", "ROUTE,艺准,艺准,20,false,true"
    }
    };
}
