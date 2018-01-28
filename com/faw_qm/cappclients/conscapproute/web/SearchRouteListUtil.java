/**
 * 生成程序 SearchRouteListUtil.java    1.0    2004/02/19

 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/15 徐春英    原因：修改获取图标的方法
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.*;

import com.faw_qm.enterprise.util.PDMIcons;
import com.faw_qm.framework.exceptions.*;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.lifecycle.util.*;
import com.faw_qm.technics.consroute.ejb.service.*;
import com.faw_qm.util.*;
import com.faw_qm.persist.ejb.service.*;
import com.faw_qm.part.model.*;
import com.faw_qm.wip.model.*;

/**
 * <p> Title:搜索工艺路线表 </p> <p> Description:本类提供的方法为廋客户界面使用 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class SearchRouteListUtil
{

    public SearchRouteListUtil()
    {}

    /**
     * 搜索符合指定条件的路线表
     * @param textRouteListName
     * @param textRouteListNum
     * @param selectlifeCState
     * @param selectFolder
     * @param textLevel
     * @param textVersion
     * @param textProduct
     * @param textDescription
     * @param textCreator
     * @param textCreateTime
     * @param textUpdateTime
     * @return @throws QMException
     */
    public static Vector findAllRouteList(String textRouteListName, String textRouteListNum, String selectlifeCState, String selectFolder, String textLevel, String textVersion, String textProduct,
            String textDescription, String textCreator, String textCreateTime, String textUpdateTime) throws QMException
    {
        Vector v = new Vector();
        TechnicsRouteListInfo routeList = new TechnicsRouteListInfo();
        if(textRouteListName != null && !textRouteListName.equals(""))
            routeList.setRouteListName(textRouteListName);
        if(textRouteListNum != null && !textRouteListNum.equals(""))
            routeList.setRouteListNumber(textRouteListNum);
        if(selectlifeCState != null && !selectlifeCState.equals(""))
            routeList.setLifeCycleState(LifeCycleState.toLifeCycleState(selectlifeCState));
        if(selectFolder != null && !selectFolder.equals(""))
            routeList.setLocation(selectFolder);
        if(textLevel != null && !textLevel.equals(""))
            routeList.setRouteListLevel(textLevel);
        if(textVersion != null && !textVersion.equals(""))
            routeList.setVersionID(textVersion);
        if(textDescription != null && !textDescription.equals(""))
            routeList.setRouteListDescription(textDescription);
        if(textCreator != null && !textCreator.equals(""))
            routeList.setIterationCreator(textCreator);
        String createTime = "";
        if(textCreateTime != null && !textCreateTime.equals(""))
            createTime = textCreateTime;
        String updateTime = "";
        if(textUpdateTime != null && !textUpdateTime.equals(""))
            updateTime = textUpdateTime;
        TechnicsRouteService projService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        v = (Vector)projService.findRouteLists(routeList, textProduct, createTime, updateTime);
        return v;
    }

    /**
     * 得到路线表的“用于产品”的编号和路线表的创建者
     * @param bsoid 路线表
     * @return 字符串数组：masterID、产品编号、路线表的创建者、路线表的图标路径
     */
    public static String[] getProduct(String bsoid)
    {
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo)pService.refreshInfo(bsoid);
            String id = info.getProductMasterID();

            QMPartMasterInfo part = (QMPartMasterInfo)pService.refreshInfo(id);

            String creator = "";
            String ic = info.getIterationCreator();
            if(ic != null)
                creator = RouteWebHelper.getUserNameByID(ic);
            //begin CR1
            String iconUrl = PDMIcons.getWebBsoIcon(info);
            //            if(((WorkableIfc)info).getWorkableState().equals("c/o"))
            //            {
            //                iconUrl = "images/routeList_originalIcon.gif";
            //            }else if(((WorkableIfc)info).getWorkableState().equals("c/i"))
            //            {
            //                iconUrl = "images/routeList.gif";
            //            }else if(((WorkableIfc)info).getWorkableState().equals("wrk"))
            //            {
            //                iconUrl = "images/routeList_workingIcon.gif";
            //            }
            //end CR1
            String[] array = {info.getMaster().getBsoID(), part.getPartNumber(), creator, iconUrl};
            return array;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

    }

}