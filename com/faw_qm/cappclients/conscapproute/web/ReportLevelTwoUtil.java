/**
 *  生成程序 ReportLevelTwoUtil.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.util.EJBServiceHelper;
import java.util.StringTokenizer;

/**
 * <p> Title:生成二级路线报表 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class ReportLevelTwoUtil
{

    private static TechnicsRouteListIfc myRouteList;

    public ReportLevelTwoUtil()
    {}

    /**
     * 获得界面头部信息
     * @param routeListID 路线表的BsoID
     * @return 路线表的编号、名称、产品、日期
     */
    public static String[] getHeader(String routeListID)
    {
        routeListID = routeListID.trim();
        PersistService pService = null;
        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(routeListID);
            myRouteList = routelist;
            String department = routelist.getDepartmentName();
            String number = routelist.getRouteListNumber();
            String name = routelist.getRouteListName();
            String list = department + number + "（" + name + "）" + "的二级工艺路线报表";

            QMPartMasterIfc partmaster = (QMPartMasterIfc)pService.refreshInfo(routelist.getProductMasterID());
            String product = partmaster.getPartNumber() + "_" + partmaster.getPartName();

            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
            String day = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
            String date = year + "年" + month + "月" + day + "日";
            String[] c = {list, product, date};
            return c;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获得指定路线表的二级路线报表的数据
     * @param routeListID 路线表BsoID
     * @return 返回集合的元素为数组arrayObjs。其元素依次为序号、零部件编号、零部件名、一级路线串、二级路线串
     */
    public static Collection getSecondLeveRouteListReport()
    {
        Vector v = new Vector();
        TechnicsRouteService routeService = null;
        try
        {
            routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getSecondLeveRouteListReport(myRouteList);
            Object[] parts = map.keySet().toArray();
            if(parts != null && parts.length > 0)
            {
                for(int i = 0;i < parts.length;i++) //for A:
                {
                    QMPartMasterIfc partmaster = (QMPartMasterIfc)parts[i];
                    Object[] branches = (Object[])map.get(partmaster);
                    if(branches.length > 1) //有一级路线
                    {
                        Object[] branches1 = ((Collection)branches[0]).toArray(); //一级路线节点值对象集合
                        Object[] branches2 = ((Collection)branches[1]).toArray(); //二级路线节点值对象集合
                        //                        Vector v1 = getNodeStr(branches1);
                        //                        Vector v2 = getNodeStr(branches2);
                        Vector v1 = getRouteStr(branches1);
                        Vector v2 = getRouteStr(branches2);
                        handleTowBranchs(v1, v2);
                        int num = v1.size();
                        if(v1.size() <= v2.size())
                            num = v2.size();
                        QMPartIfc part = (QMPartIfc)routeService.getLatestVesion(partmaster);
                        Object[] arrayObjs = {String.valueOf(i + 1), partmaster.getPartNumber(), partmaster.getPartName(), part.getVersionValue(), v1, v2, String.valueOf(num)};
                        v.add(arrayObjs);
                    }
                    /**
                     * else //没有一级路线的情况 { Object[] branches2 = ( (Collection) branches[0]).toArray(); //二级路线节点值对象集合 Vector v1 = new Vector(); String[] array = {"（无路线）","（无路线）"}; v1.add(array); Vector
                     * v2 = getNodeStr(branches2); int num = v2.size(); Object[] arrayObjs = { String.valueOf(i + 1), partmaster.getPartNumber(), partmaster.getPartName(), v1, v2,
                     * String.valueOf(num)}; v.add(arrayObjs); }
                     */
                    //lm modify 20040826
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return v;
    }

    /**
     * 获得指定路线表的二级路线报表的数据
     * @param routeListID 路线表BsoID
     * @return 返回集合的元素为数组arrayObjs。其元素依次为序号、零部件编号、零部件名、一级路线串、二级路线串
     */
    public static Collection getSecondLeveRouteListReport2()
    {
        Vector v = new Vector();
        TechnicsRouteService routeService = null;
        try
        {
            routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getSecondLeveRouteListReport(myRouteList);
            Object[] parts = map.keySet().toArray();
            if(parts != null && parts.length > 0)
            {
                for(int i = 0;i < parts.length;i++) //for A:
                {
                    QMPartMasterIfc partmaster = (QMPartMasterIfc)parts[i];
                    Object[] branches = (Object[])map.get(partmaster);
                    if(branches.length > 1) //有一级路线
                    {
                        Object[] branches1 = ((Collection)branches[0]).toArray(); //一级路线串字符串集合
                        Object[] branches2 = ((Collection)branches[1]).toArray(); //二级路线串字符串集合
                        Vector v1 = getRouteStr(branches1);
                        Vector v2 = getRouteStr(branches2);
                        handleTowBranchs(v1, v2);
                        int num = v1.size();
                        if(v1.size() <= v2.size())
                            num = v2.size();
                        QMPartIfc part = (QMPartIfc)routeService.getLatestVesion(partmaster);
                        Object[] arrayObjs = {String.valueOf(i + 1), partmaster.getPartNumber(), partmaster.getPartName(), part.getVersionValue(), v1, v2, String.valueOf(num)};
                        v.add(arrayObjs);
                    }

                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return v;
    }

    /**
     * 处理两个分支
     * @param v1 路线集合1
     * @param v2 路线集合2
     */
    private static void handleTowBranchs(Vector v1, Vector v2)
    {
        int i1 = v1.size();
        int i2 = v2.size();
        if(i1 != i2)
        {
            if(i1 > i2)
            {
                for(int i = i2;i < i1;i++)
                {
                    String[] array = {"", ""};
                    v2.add(array);
                }
            }else
            {
                for(int i = i1;i < i2;i++)
                {
                    String[] array = {"", ""};
                    v1.add(array);
                }
            }
        }
    }

    private static Vector getRouteStr(Object[] nodes)
    {
        Vector strVector = new Vector();
        if(nodes != null && nodes.length > 0)
        {
            for(int j = 0;j < nodes.length;j++)
            {
                String makeStr = "";
                String assemStr = "";
                String unionStr = (String)nodes[j];
                if(unionStr != null)
                {
                    StringTokenizer hh = new StringTokenizer(unionStr, "@");
                    if(hh.hasMoreTokens())
                    {
                        makeStr = hh.nextToken();
                        assemStr = hh.nextToken();
                    }
                }
                String[] array = {makeStr, assemStr};
                strVector.add(array);
            }
        }

        else
        {
            String[] array = {"", ""};
            strVector.add(array);
        }
        return strVector;

    }

    /**
     * 获得路线串
     * @param nodes 路线节点值对象集合
     * @return 字符串数组array的集合。array[0]为制造路线串；array[1]为装配路线串
     */
    private static Vector getNodeStr(Object[] nodes)
    {
        Vector strVector = new Vector();
        if(nodes != null && nodes.length > 0)
        {
            for(int j = 0;j < nodes.length;j++) //for B:
            {
                String makeStr = "";
                String assemStr = "";
                Object[] objs = (Object[])nodes[j];
                Vector makeNodes = (Vector)objs[0]; //制造节点
                RouteNodeIfc asseNode = (RouteNodeIfc)objs[1]; //装配节点
                if(makeNodes != null && makeNodes.size() > 0)
                {
                    for(int m = 0;m < makeNodes.size();m++)
                    {
                        RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                        if(makeStr == "")
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "→" + node.getNodeDepartmentName();
                    }
                }
                if(asseNode != null)
                {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if(makeStr == null || makeStr.equals(""))
                {
                    makeStr = "";
                }
                if(assemStr == null || assemStr.equals(""))
                {
                    assemStr = "";
                }
                String[] array = {makeStr, assemStr};
                strVector.add(array);
            } ////for B: END
        }else
        {
            String[] array = {"", ""};
            strVector.add(array);
        }
        return strVector;
    }

}
