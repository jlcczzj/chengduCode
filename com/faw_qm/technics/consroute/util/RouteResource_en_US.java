/**
 * ���ɳ��� RouteResource_en_US.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteResource_en_US.java</p> <p>Description:·����Դ�ļ�(Ӣ��) </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteResource_en_US extends ListResourceBundle
{

    /**
     * ���캯��
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
            {"4", "The part has had a route.If you want copy other route,Please delete the existed route firstly."}, {"5", "ProductNumber:\"*\" is not correct��Please input product number afresh."},
            {"6", "*(*) TechnicsRouteList Report"}, {"7", "UsedBy product:* *"}, {"8", "Report's date : * year * month * day"}, {"9", "*: *(*)'s secondRoute Report"},
            {"10", "You can't be permit to revise * ."}, {"11", "You can't be permit to upgrade the small version of route list."}

    };

}
