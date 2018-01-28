/**
 * 生成程序 RouteListLevelTypeRB_en_US.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteListLevelTypeRB_en_US.java</p> <p>Description:路线级别资源文件(英文) </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListLevelTypeRB_en_US extends ListResourceBundle
{

    /**
     * 构造函数
     */
    public RouteListLevelTypeRB_en_US()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"firstRoute", "FIRSTROUTE,firstRoute,firstRoute,10,true,true"}, {"secondRoute", "SECONDROUTE,firstRoute,firstRoute,20,false,true"}};

}
