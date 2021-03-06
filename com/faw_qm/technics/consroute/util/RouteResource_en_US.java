/**
 * 生成程序 RouteResource_en_US.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteResource_en_US.java</p> <p>Description:路线资源文件(英文) </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteResource_en_US extends ListResourceBundle
{

    /**
     * 构造函数
     */
    public RouteResource_en_US()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {{"1", "Name of TechnicsRouteList can't be null."}, {"2", "number of TechnicsRouteList can't be null."},
            {"3", "TechnicsRouteList(*)'s number(*) had existed, Please input number afresh."},
            {"4", "The part has had a route.If you want copy other route,Please delete the existed route firstly."}, {"5", "ProductNumber:\"*\" is not correct，Please input product number afresh."},
            {"6", "*(*) TechnicsRouteList Report"}, {"7", "UsedBy product:* *"}, {"8", "Report's date : * year * month * day"}, {"9", "*: *(*)'s secondRoute Report"},
            {"10", "You can't be permit to revise * ."}, {"11", "You can't be permit to upgrade the small version of route list."}

    };

}
