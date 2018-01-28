/**
 * 生成程序 RouteListIterationsCompare.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2012/03/29 吕航参见TD 5962
 * CR2 2012/04/06 吕航参见TD5963
 * CR3 2012/04/09 吕航参见TD6021
 * SS1 A032-2016-0083 版序比较结果不正确。 liunan 2016-9-12
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.Vector;

import java.util.Map;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.project.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.consroute.ejb.service.*;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.capp.util.*;
import java.util.*;

/**
 * <p> Title:路线表的版序比较 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class RouteListIterationsCompare
{
    /** 所选择对象的ID */
    private String[] selectBsoID;

    /** 持久化服务 */
    private PersistService service;

    /** 路线表对象 */
    private TechnicsRouteListIfc myObjectInfo;

    public boolean flag = false;

    /** 路线表的版本号 */
    public String technicsVersionID;

    /** 版本号集合 */
    public Vector myVersionIDCollection = new Vector();

    /** 路线表对象集合 */
    public Vector myTechnicsCollection = new Vector();

    /***/
    private RouteWebHelper helper = new RouteWebHelper();

    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * 构造函数
     */
    public RouteListIterationsCompare()
    {}

    /**
     * 设置选择的所有对象的BsoID
     * @param sBsoID 对象的BsoID的数组
     */
    public void setSelectBsoID(String[] sBsoID)
    {
        if(verbose)
            System.out.println("capproute.web.TechnicsVersionCompareUtil.setSelectBsoID() begin...");
        //把bsoID按照升序排列后返回
        selectBsoID = RouteWebHelper.sortedIterate(sBsoID);
        flag = true;
        if(verbose)
            System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.setSelectBsoID() end...return is void");
    }

    /**
     * 得到瘦客户端头部的必要显示信息(路线表编号、名称、版本号、图标)
     * @param technicsBsoID 对象的BsoID
     */
    public String[] getHeadInfo(String technicsBsoID)
    {
        if(verbose)
            System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.getHeadInfo() begin...");
        try
        {

            service = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListIfc technicsInfo = (TechnicsRouteListIfc)service.refreshInfo(technicsBsoID);

            String technicsNumber = technicsInfo.getRouteListNumber();
            String technicsName = technicsInfo.getRouteListName();
            String versionID = technicsInfo.getVersionValue();
            String headIconUrl = helper.getStandardIconName(technicsInfo);
            String[] myHeadInfo1 = {technicsNumber, technicsName, versionID, headIconUrl};
            if(verbose)
                System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.getHeadInfo() end...return: " + myHeadInfo1.length);
            return myHeadInfo1;
        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * 得到选中的对象的集合，页面上选中的是BsoID通过这个方法转成对象
     * @return Vector 选中的对象的集合
     */
    public Vector getSelectObject()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getSelectObject() begin...");
        if(flag)
        {
            for(int i = 0;i < selectBsoID.length;i++)
            {
                try
                {
                    service = (PersistService)EJBServiceHelper.getService("PersistService");
                    myObjectInfo = (TechnicsRouteListIfc)service.refreshInfo(selectBsoID[i].trim());
                }catch(Exception sle)
                {
                    System.out.println(sle.toString());
                }

                technicsVersionID = myObjectInfo.getVersionValue();
                myVersionIDCollection.add(technicsVersionID);
                myTechnicsCollection.add(myObjectInfo);
            }
            if(verbose)
                System.out.println("cappclients.capp.web.RouteListVersionCompare.getSelectObject() end...return: " + myTechnicsCollection);
            return myTechnicsCollection;
        }else
            return null;
    }

    /**
     * 得到选中的对象的版本号的集合
     * @return Vector
     */
    public Vector getMyVersionIDCollection()
    {
        return myVersionIDCollection;
    }

    /**
     * 获得选中的对象的基本属性的集合，页面上选中的是BsoID通过这个方法转成对象
     * @return Vector 必要的属性的集合，集合的元素是数组对象
     */
    public Vector getAttVector()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getAttVector() begin...");
        try
        {
            Vector av = new Vector();
            //Vector tav=new Vector();
            String[][] obj = new String[myTechnicsCollection.size() + 1][9];

            obj[0][0] = "编号";
            obj[0][1] = "名称";
            obj[0][2] = "级别";
            obj[0][3] = "单位";
            obj[0][4] = "用于产品";
            obj[0][5] = "生命周期状态";
            obj[0][6] = "工作组";
            obj[0][7] = "资料夹";
            obj[0][8] = "说明";

            for(int j = 1;j < myTechnicsCollection.size() + 1;j++)
            {
                obj[j][0] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListNumber();
                obj[j][1] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListName();
                obj[j][2] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListLevel();
                //单位
                String department = ((TechnicsRouteListInfo)(myTechnicsCollection.get(j - 1))).getDepartmentName();
                if(department != null)
                    obj[j][3] = department;
                else
                    obj[j][3] = "";

                //用于产品
                String productID = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getProductMasterID();
                //CR1 begin
                if(productID!=null && !productID.equals("") )
                {
                service = (PersistService)EJBServiceHelper.getService("PersistService");
                obj[j][4] = ((QMPartMasterIfc)service.refreshInfo(productID)).getPartNumber();
                }else
                {
                    obj[j][4]="";
                }
              //CR1 end
                //状态
                LifeCycleState sta = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getLifeCycleState();
                if(sta != null)
                    obj[j][5] = sta.getDisplay();
                else
                    obj[j][5] = "";

                //获得项目组名
                String projectID = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getProjectId();
                if(projectID != null)
                {
                    ProjectIfc project = (ProjectIfc)service.refreshInfo(projectID);
                    obj[j][6] = CappServiceWebHelper.getIdentity(project);
                }else
                    obj[j][6] = "";

                //资料夹
                obj[j][7] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getLocation();

                //说明
                String description = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListDescription();
                if(description != null)
                    obj[j][8] = description;
                else
                    obj[j][8] = "";

            }

            boolean flag = false;

            for(int l = 0;l < 9;l++)
            {
                for(int k = 2;k < selectBsoID.length + 1;k++)
                {
                    if(!((obj[k][l]).equals((obj[1][l]))))
                    {
                        flag = true;
                        break;
                    }
                }
                String[] tempArray = new String[selectBsoID.length + 1];
                if(flag)
                {
                    for(int m = 0;m < selectBsoID.length + 1;m++)
                    {
                        tempArray[m] = obj[m][l];
                    }
                    av.add(tempArray);
                    flag = false;
                }
            }

            if(verbose)
                System.out.println("cappclients.capp.web.RouteListVersionCompare.getAttVector() end...return: " + av);
            return av;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得路线表的关联零部件的差异情况
     * @return 返回集合的元素为字符串数组;各数组的第一个元素为零部件编号，其他元素为各零部件关联的路线串
     */
    public Vector getdePartVector()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getdePartVector() begin...");
        Vector v = new Vector();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.compareIterate(myTechnicsCollection);
            if(map != null && map.size() > 0)
            {
                Object[] keys = map.keySet().toArray();
                for(int i = 0;i < keys.length;i++)
                {
                    String value = keys[i].toString();
                    StringTokenizer st = new StringTokenizer(value, ";");
                    String id;
                    String parentNum = null;
                    id = st.nextToken();
                    if(st.hasMoreTokens())
                        parentNum = st.nextToken();
                    
                    //CCBegin SS1
                    //String partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
                    String partNumber = "";
                    if(id.indexOf("QMPartMaster_")!=-1)
                    {
                    	partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
                    }
                    else if(id.indexOf("QMPart_")!=-1)
                    {
                    	partNumber = ((QMPartInfo)service.refreshInfo(id)).getPartNumber();
                    }
                    //CCEnd SS1
                    
                    Vector partlinks = (Vector)map.get(value);
                    if(partlinks != null && partlinks.size() > 0)
                    {
                        //CR2
                        String[] temp = new String[partlinks.size() + 2];
                        //temp[0] = partNumber + "(父件" + parentNum + ")";
                        temp[0] = partNumber;
                        //CR3 begin
                        temp[1]= id;
                        System.out.println("temp[1]"+id);
                        for(int j = 0;j < partlinks.size();j++)
                        {
                            ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)partlinks.elementAt(j);
                            String routeStr = " ";
                            if(link != null)
                            {
                                String routeID = link.getRouteID();
                                if(routeID != null)
                                    routeStr = getRouteBranches(routeID);
                            }
                            temp[j + 2] = routeStr;
                        }
                        v.add(temp);
                    }
                    //CR3 end
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getdePartVector() end...return: " + v);
        return v;
    }

    /**
     * 获得路线表的所有零部件关联的BsoID
     * @return 该方法的返回值恰好与getdePartVector()的返回值相匹配。
     */
    public Vector getLinkIDVector()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getLinkIDVector() begin...");
        Vector v = new Vector();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.compareIterate(myTechnicsCollection);
            if(map != null && map.size() > 0)
            {
                Object[] partmasters = map.keySet().toArray();
                for(int i = 0;i < partmasters.length;i++)
                {
                    String value = partmasters[i].toString();
                    StringTokenizer st = new StringTokenizer(value, ";");
                    String id;
                    String parentNum = null;
                    id = st.nextToken();
                    if(st.hasMoreTokens())
                        parentNum = st.nextToken();

                    //CCBegin SS1
                    //String partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
                    String partNumber = "";
                    if(id.indexOf("QMPartMaster_")!=-1)
                    {
                    	partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
                    }
                    else if(id.indexOf("QMPart_")!=-1)
                    {
                    	partNumber = ((QMPartInfo)service.refreshInfo(id)).getPartNumber();
                    }
                    //CCEnd SS1
                    
                    Vector partlinks = (Vector)map.get(value);
                    if(partlinks != null && partlinks.size() > 0)
                    {
                        String[] temp = new String[partlinks.size() + 1];
                        temp[0] = partNumber;
                        for(int j = 0;j < partlinks.size();j++)
                        {
                            ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)partlinks.elementAt(j);
                            String routeStr = null;
                            if(link != null)
                            {
                                routeStr = link.getBsoID();
                            }
                            temp[j + 1] = routeStr;
                        }
                        v.add(temp);
                    }

                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getLinkIDVector() end...return: " + v);
        return v;
    }

    /**
     * 获得指定路线的路线串
     * @param routeID 工艺路线的BsoID
     * @return 路线串
     */
    public static String getRouteBranches(String routeID)
    {
        String result = "";
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getRouteBranchs(routeID);

            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for(int i = 0;i < branchs.length;i++)
            {
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
                String makeStr = "";
                String assemStr = "";
                String routeStr = "";
                Object[] nodes = (Object[])map.get(branchinfo);
                Vector makeNodes = (Vector)nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];

                if(makeNodes != null && makeNodes.size() > 0)
                {
                    //System.out.println(">>>>>>>>>>>>>>>>> 获得
                    // 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
                    for(int m = 0;m < makeNodes.size();m++)
                    {
                        RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                        if(makeStr.equals(""))
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "-" + node.getNodeDepartmentName();
                    }
                }

                if(asseNode != null)
                {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if(!makeStr.equals("") && !assemStr.equals(""))
                    routeStr = makeStr + "-" + assemStr;
                else if(makeStr.equals("") && !assemStr.equals(""))
                    routeStr = assemStr;
                else if(!makeStr.equals("") && assemStr.equals(""))
                    routeStr = makeStr;
                else if(makeStr.equals("") && assemStr.equals(""))
                    routeStr = "";

                if(routeStr == null || routeStr.equals(""))
                {
                    routeStr = " ";
                }

                if(result.equals(""))
                    result = result + routeStr;
                else
                    result = result + ";" + routeStr;

                if(result == null || result.equals(""))
                    result = " ";
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

}
