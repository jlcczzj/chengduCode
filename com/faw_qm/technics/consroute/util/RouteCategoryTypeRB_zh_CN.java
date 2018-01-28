/**
 * 生成程序 RouteCategoryTypeRB_zh_CN.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteCategoryTypeRB_zh_CN.java</p> <p>Description: 路线种类资源(中文)</p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteCategoryTypeRB_zh_CN extends ListResourceBundle
{

    /**
     * 构造函数
     */
    public RouteCategoryTypeRB_zh_CN()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"manufactureRoute", "MANUFACTUREROUTE,制造路线,制造路线,10,true,true"}, {"assemblyRoute", "ASSEMBLYROUTE,装配路线,装配路线,20,false,true"}};

}
