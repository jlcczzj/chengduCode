/** 
 * 生成程序 ViewRoute.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title:查看工艺路线 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class ViewRoute
{

    /** 路线串集合 */
    private static Vector nodeIndexVector = new Vector();

    /**
     * 构造函数
     */
    public ViewRoute()
    {}

    /**
     * 得到瘦客户端头部的必要显示信息
     * @param bsoID 路线表与零部件的关联对象ListRoutePartLink的BsoID
     * @return 编号,名称,级别,单位,版本,用于产品
     */
    public static String[] getRouteListHead(String bsoID)
    {
        String id = bsoID.trim();
        String number = "";
        String name = "";
        String level = "";
        String department = "";
        ListRoutePartLinkIfc link;

        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            link = (ListRoutePartLinkIfc)pService.refreshInfo(id);
            id = link.getRouteListID();
            TechnicsRouteListInfo list = (TechnicsRouteListInfo)pService.refreshInfo(id);
            number = list.getRouteListNumber();
            name = list.getRouteListName();
            level = list.getRouteListLevel();
            String partNum = link.getPartMasterInfo().getPartNumber() + "_" + link.getPartMasterInfo().getPartName();
            String dep = list.getDepartmentName();
            if(dep != null)
                department = dep;

            TechnicsRouteIfc route = (TechnicsRouteIfc)pService.refreshInfo(link.getRouteID());
            String routeDescri = "";
            String descri = route.getRouteDescription();
            if(descri != null)
                routeDescri = descri;
            //构造数组
            String[] myVersionArray1 = {number, name, level, department, descri, partNum};
            return myVersionArray1;

        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * 获得指定路线的路线分支信息
     * @param linkBsoID 路线表与零部件的关联对象ListRoutePartLink的BsoID
     * @return 返回集合的元素为数组，每个数组的元素依次为序号、制造路线串、装配路线串、是/否主要路线
     */
    public static Collection getRoutes(String linkBsoID)
    {
        nodeIndexVector.clear();
        Vector v = new Vector();
        PersistService pService = null;
        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)pService.refreshInfo(linkBsoID);
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getRouteBranchs(link.getRouteID());
            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for(int i = 0;i < branchs.length;i++)
            {
            	
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
                String isMainRoute = "是";
                if(branchinfo.getMainRoute())
                    isMainRoute = "是";
                else
                    isMainRoute = "否";

                String makeStr = "";
                String assemStr = "";
                Object[] nodes = (Object[])map.get(branchinfo);
                Vector makeNodes = (Vector)nodes[0]; //制造节点集合
                RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1]; //装配节点

                //{{{为生成节点表作准备
                Vector nodeVector = new Vector();
                if(makeNodes != null && makeNodes.size() > 0)
                    nodeVector.addAll(makeNodes);
                if(asseNode != null)
                    nodeVector.addElement(asseNode);
                Object[] indexObjs = new Object[2];
                indexObjs[0] = String.valueOf(i + 1);
                indexObjs[1] = nodeVector;
                nodeIndexVector.addElement(indexObjs);
                //}}}}

                //Vector makeNodeVector = new Vector();
                if(makeNodes != null && makeNodes.size() > 0)
                {
                    //System.out.println(">>>>>>>>>>>>>>>>>  获得 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
                    for(int m = 0;m < makeNodes.size();m++)
                    {
                        RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                        if(makeStr == "")
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "→" + node.getNodeDepartmentName();
                        //makeNodeVector.addElement(node.getNodeDepartmentName());
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

                Object[] array = {String.valueOf(i + 1), makeStr, assemStr, isMainRoute};
                v.addElement(array);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }

    /**
     * 获得路线中所有节点的信息
     * @return 返回集合的元素为数组returnElement，一个路线串对应一个returnElement； 其中returnElement[0] = 路线串编号； returnElement[1] = 集合（元素为该路线串中所有节点的属性信息）
     */
    public static Vector getNodeDepartment()
    {
        Vector v = new Vector();
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            if(nodeIndexVector.size() > 0)
            {
                for(int k = 0;k < nodeIndexVector.size();k++)
                {
                    Object[] indexObj = (Object[])nodeIndexVector.elementAt(k); //当前路线串
                    Vector nodeVector = (Vector)indexObj[1]; //当前路线串中的所有节点的集合
                    Vector tempV = new Vector();
                    if(nodeVector.size() > 0)
                    {
                        for(int i = 0;i < nodeVector.size();i++)
                        {
                            RouteNodeInfo node = (RouteNodeInfo)nodeVector.elementAt(i);
                            String code = node.getNodeDepartmentName();
                            String departemntName = pService.refreshInfo(node.getNodeDepartment()).toString();
                            String type = node.getRouteType();
                            String descri = node.getNodeDescription();
                            //节点信息：代码（简称），单位名，类别，说明
                            String[] array = {code, departemntName, type, descri};
                            tempV.add(array);
                        }
                    }
                    Object[] returnElement = new Object[2];
                    returnElement[0] = indexObj[0]; //路线串编号
                    returnElement[1] = tempV; //当前路线串中所有节点的信息
                    v.add(returnElement);
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }

}