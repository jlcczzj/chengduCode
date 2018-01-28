/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.service;

import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.CheckoutLinkInfo;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkingPair;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: 启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */

public class ServiceTestHandler
{
    //PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    private static final boolean VERBOSE = true;

    public ServiceTestHandler()
    {}

    public static void main(String[] args)
    {
        ServiceTestHandler testHandler1 = new ServiceTestHandler();
    }

    ///////////////第一个用例的测试开始。
    public Object a_storeRouteList() throws QMException
    {
        TechnicsRouteListIfc routeListInfo1 = (TechnicsRouteListIfc)RouteHelper.refreshInfo("TechnicsRouteList_734");
        TechnicsRouteListMasterIfc masterInfo1 = (TechnicsRouteListMasterIfc)routeListInfo1.getMaster();
        //构造参数。
        TechnicsRouteListInfo routeListInfo = (TechnicsRouteListInfo)((TechnicsRouteListInfo)routeListInfo1).duplicate();
        TechnicsRouteListMasterInfo masterInfo = (TechnicsRouteListMasterInfo)((TechnicsRouteListMasterInfo)masterInfo1).duplicate();
        routeListInfo.setMaster(masterInfo);
        routeListInfo.setRouteListName("zlb_1工艺录像表");
        routeListInfo.setRouteListNumber("111");
        routeListInfo.setRouteListDescription("zlb_1路线表");
        routeListInfo.setMasterBsoID(null);
        routeListInfo.setRouteListLevel(RouteListLevelType.toRouteListLevelType("SECONDROUTE").getDisplay());
        TechnicsRouteService tservice = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        TechnicsRouteListIfc info = tservice.storeRouteList(routeListInfo);
        return info;
    }

    ///////////////第一个用例的测试结束。

//guoxl delete    public Object a_copyRouteList() throws QMException
//    {
//    	TechnicsRouteListIfc original = (TechnicsRouteListIfc)RouteHelper.refreshInfo("TechnicsRouteList_1146");
//        TechnicsRouteListIfc copy = null;
//        WorkInProgressService workservice = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
//        FolderIfc folderIfc = workservice.getCheckoutFolder();
//        //CheckoutLinkInfo checkLinkInfo = workservice.checkOut(original);
//        //副本；
//        //copy = (TechnicsRouteListIfc)RouteHelper.refreshInfo(checkLinkInfo.getLeftBsoID());
//        WorkingPair workPair = workservice.checkout(original);
//        copy = (TechnicsRouteListIfc)workPair.getWorkingCopy();
//        return copy;
//    }

    public Object a_checkIn() throws QMException
    {
        FolderService ff;
        //ff.changeFolder(FolderEntryIfc, FolderIfc);
        WorkInProgressService workService = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
        TechnicsRouteListIfc workable = (TechnicsRouteListIfc)RouteHelper.refreshInfo("TechnicsRouteList_1146");
        String s = "dddd";
        TechnicsRouteListIfc checkin = (TechnicsRouteListIfc)workService.checkin(workable, s);
        return checkin;
    }
}
