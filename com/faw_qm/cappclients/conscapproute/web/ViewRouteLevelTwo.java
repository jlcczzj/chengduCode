/** 
 * 生成程序 ViewRouteLevelTwo.java    1.0    2004/02/19
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

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title:查看二级路线 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class ViewRouteLevelTwo
{

    private RouteWebHelper helper = new RouteWebHelper();
    /** 代码测试变量 */

    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * 构造函数
     */
    public ViewRouteLevelTwo()
    {}

    /**
     * 获得指定零部件的基本属性
     * @param partID 零部件的BsoID
     * @return 字符串数组:编号\名称\版本\视图\图标URL
     */
    public static String getPartHeader(String partID)
    {
        try
        {
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");
            QMPartIfc info = (QMPartIfc)service1.refreshInfo(partID);
            String number = info.getPartNumber();
            String name = info.getPartName();
            String versionID = info.getVersionValue();
            String viewName = info.getViewName();
            String view = "";
            if(viewName != null)
                view = viewName;
            String myHeadInfo1 = number + " " + name + " " + versionID + " " + view;
            return myHeadInfo1;
        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * 获得指定零部件的二级路线
     * @param partMasterID 指定零部件
     * @return 二级路线信息
     */
    public static Collection getPartLevelRoutes(String partMasterID)
    {
        Vector v = new Vector();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");

            Vector v2 = (Vector)routeService.getPartLevelRoutes(partMasterID, RouteListLevelType.SENCONDROUTE.getDisplay());
            if(v2 != null && v2.size() > 0)
            {
                for(int i = 0;i < v2.size();i++)
                {
                    Object[] objs = (Object[])v2.elementAt(i);
                    TechnicsRouteListInfo list = (TechnicsRouteListInfo)objs[0];
                    TechnicsRouteIfc route = (TechnicsRouteIfc)objs[1];
                    Map map = (Map)objs[2];
                    ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)objs[3];
                    String number = list.getRouteListNumber();
                    String name = list.getRouteListName();
                    String level = list.getRouteListLevel();

                    String departmentName = "";
                    String department = list.getDepartmentName();
                    if(department != null)
                        departmentName = department;

                    String version = list.getVersionValue();
                    QMPartMasterIfc info = (QMPartMasterIfc)service1.refreshInfo(list.getProductMasterID());
                    String product = info.getPartNumber() + "_" + info.getPartName();
                    String state = "";
                    LifeCycleState st = list.getLifeCycleState();
                    //获得对象的状态
                    if(st != null)
                        state = st.getDisplay();

                    String creator = "";
                    String ic = list.getIterationCreator();
                    if(ic != null)
                        creator = RouteWebHelper.getUserNameByID(ic);

                    //获得创建时间
                    String createTime = list.getCreateTime().toString();
                    //路线状态
                    String routeState = link.getAdoptStatus();

                    String[] array = {link.getBsoID(), number, name, level, departmentName, version, product, routeState, state, creator, createTime};
                    Vector branchVector = getRouteBranches(map);
                    Object[] element = {array, branchVector};
                    v.addElement(element);
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }

    /**
     * 获得所有路线分支的路线串
     * @param map 路线分支的集合
     * @return 所有路线分支的路线串
     * @throws QMException 
     */
    public static Vector getRouteBranches(Map map) throws QMException
    {
        Vector v = new Vector();
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
            Vector makeNodes = (Vector)nodes[0]; //制造节点
            RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1]; //装配节点

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

            String[] array = {String.valueOf(i + 1), makeStr, assemStr, isMainRoute};
            v.addElement(array);
        }
        return v;
    }

}