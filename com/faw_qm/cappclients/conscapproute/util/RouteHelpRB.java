/**
 * 生成程序 PartMasterTreePanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * Title:路线帮助资源 Description: Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @version 1.0
 */

public class RouteHelpRB extends ListResourceBundle
{

    public RouteHelpRB()
    {}

    /**
     * 获得资源内容
     * @return Object[][] 对象2维数组
     * @roseuid 4031A67503CD
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {{"Help/capproute/EditRouteGraphHelp", "help/zh_cn/capproute/aboutEditRouteGraph.html"},
            {"Help/capproute/EditRouteNodeHelp", "help/zh_cn/capproute/aboutEditRouteNode.html"}, {"Help/capproute/ReportRouteListHelp", "help/zh_cn/capproute/aboutReportRouteList.html"},};

}
