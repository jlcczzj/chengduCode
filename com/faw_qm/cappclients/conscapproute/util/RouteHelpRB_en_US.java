/**
 * ���ɳ��� PartMasterTreePanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * <p> Title:������Դ(����Ӣ��) </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class RouteHelpRB_en_US extends ListResourceBundle
{

    public RouteHelpRB_en_US()
    {}

    /**
     * �����Դ����
     * @return Object[][] ����2ά����
     * @roseuid 4031A67503CD
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {{"Help/capproute/EditRouteGraphHelp", "help/zh_cn/capproute/aboutEditRouteGraph.html"},
            {"Help/capproute/EditRouteNodeHelp", "help/zh_cn/capproute/aboutEditRouteNode.html"}, {"Help/capproute/ReportRouteListHelp", "help/zh_cn/capproute/aboutReportRouteList.html"},};
}