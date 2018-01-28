/**
 * 生成程序 ViewRouteLevelOne.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 修改零部件界面查看不到工艺路线及工艺路线更改标记显示错误问题 pante 20130730
 * SS2 艺准BSX-1842.01-36”中的3802020-54B版本C 在工程视图配置下编制的路线，但在查看零部件路线时却看不见路线内容 leixiao 2013-9-10
 * SS3 修改次零部件界面工艺路线双重路线显示错误的问题 maxt 2013-12-17
 * SS4 修改变速箱二级路线中有零件显示不出来的问题，因为conslistpartlink表中rightbsoid里不一定都是qmpartmasterid了，新的是qmpartid了。 liunan 2014-1-23
 * SS5 轴齿中心路线增加采购标识显示 pante 2014-05-22
 * SS6 A005-2015-3096 零部件的二级路线列表中，只显示最新版的内容，不显示中间小版本。 liunan 2015-8-11
 * SS7 增加版本转换标识 xudezheng 2018-01-26
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
 * <p> Title: 查看一级路线 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class ViewRouteLevelOne
{

    private RouteWebHelper helper = new RouteWebHelper();

    /** 代码测试变量 */

    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    public ViewRouteLevelOne()
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

    /** 路线串集合 */
    //private static Vector nodeIndexVector = new Vector();
    
    /**
     * 获得指定零部件的一级路线
     * @param partMasterID
     * @return
     */
    public static Collection getPartLevelRoutes(String partMasterID)
    {
        Vector v = new Vector();
        //nodeIndexVector.clear();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");
           

            Vector v2 = (Vector)routeService.getPartLevelRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
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
                    //CCBegin SS2
                    String product="";
                    if(list.getProductMasterID()!=null&&!list.getProductMasterID().trim().equals("")){
                    QMPartMasterIfc info = (QMPartMasterIfc)service1.refreshInfo(list.getProductMasterID());
                     product = info.getPartNumber() + "_" + info.getPartName();
                    }
                    //CCEnd SS2
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
                    //SSBegin SS1
//                    Vector branchVector = getRouteBranches(map);
                    Vector branchVector = new Vector();
                    String makeStr = "";
                    String assemStr = "";
                    if(link.getMainStr()!=null){
                    	String[] s = link.getMainStr().split("=");
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID(),"转换"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                    if(link.getSecondStr()!=null){
                    	//SSBegin SS3
//                    	String[] s = link.getMainStr().split("=");
                    	String[] s = link.getSecondStr().split("=");
                    	//SSEnd SS3
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "否", link.getStockID(),"转换"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "否", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                  //SSEnd SS1
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
    
    
    //CCBegin SS4
    /**
     * 获得指定零部件的一级路线
     * @param partMasterID
     * @return
     */
    public static Collection getPartLevelRoutes(String partMasterID, String partID)
    {
    	System.out.println("partMasterID-----------11111-----------="+partMasterID);
        Vector v = new Vector();
        //nodeIndexVector.clear();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");
           

            Vector v2 = (Vector)routeService.getPartLevelRoutes(partMasterID, partID, RouteListLevelType.FIRSTROUTE.getDisplay());
            if(v2 != null && v2.size() > 0)
            {
                for(int i = 0;i < v2.size();i++)
                {
                    Object[] objs = (Object[])v2.elementAt(i);
                    TechnicsRouteListInfo list = (TechnicsRouteListInfo)objs[0];
                    //CCBegin SS6
                    if(!list.getIterationIfLatest())
                    {
                    	continue;
                    }
                    //CCEnd SS6
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
                    //CCBegin SS2
                    String product="";
                    if(list.getProductMasterID()!=null&&!list.getProductMasterID().trim().equals("")){
                    QMPartMasterIfc info = (QMPartMasterIfc)service1.refreshInfo(list.getProductMasterID());
                     product = info.getPartNumber() + "_" + info.getPartName();
                    }
                    //CCEnd SS2
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
                    //SSBegin SS1
//                    Vector branchVector = getRouteBranches(map);
                    Vector branchVector = new Vector();
                    String makeStr = "";
                    String assemStr = "";
                    if(link.getMainStr()!=null){
                    	String[] s = link.getMainStr().split("=");
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "是"};
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID(),"转换"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                    if(link.getSecondStr()!=null){
                    	//SSBegin SS3
//                    	String[] s = link.getMainStr().split("=");
                    	String[] s = link.getSecondStr().split("=");
                    	//SSEnd SS3
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "否"};
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "是", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "否", link.getStockID(),"转换"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "否", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                  //SSEnd SS1
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
    //CCEnd SS4
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
            System.out.println("------------------"+branchinfo.getBsoID());
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

                // 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());

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
