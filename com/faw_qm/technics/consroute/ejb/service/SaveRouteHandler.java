/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.service;

import java.util.Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.faw_qm.cappclients.conscapproute.graph.RouteItem;
import com.faw_qm.cappclients.conscapproute.view.RParentJPanel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 保存工艺路线表。负责解析客户端传入的整个路线表信息。包括路线和其对应的节点及节点 间的关联。 if(assertion instanceof ReplaceAssertion) { 此时如果业务对象状态是wrk时，不更新主业务对象。否则，撤销检出时主业务对象需要 改成最新版本。 MasterIfc masterinfo = (MasterIfc)info.getMaster();
 * pservice.updateBso(masterinfo, false); } 路线串构造规则： 1.在一个路线串中出现多个装配单位,不符合路线串构造规则。 2.在一个路线串装配单位不是最后一个节点,不符合路线串构造规则，此项规则可在客户端? 行判断: 3.一个路线单位在同相邻位置重复出现,节点类型相同,不符合路线串构造规则 与getRouteNodes相关。
 * 注意：做保存后处理。找到起始节点，进行设置。 1.此操作每次保存时都做，因为可添加新的起始节点且在起始节点前添加起始节点。 2.保存和更新次数相对于查看较少，所以此操作不放在查看时做。
 * @author
 * @version 1.0
 */
public class SaveRouteHandler
{
    /**
     * @roseuid 4039F29E00FB
     */
    private SaveRouteHandler()
    {}

    protected static TechnicsRouteIfc doSave(ListRoutePartLinkIfc listLinkInfo, HashMap routeRelaventObejts) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("-> RouteListHandler.doSave - IN ***************");
        }
        TechnicsRouteService routeService = RouteHelper.getRouteService();
        //处理路线。
        // System.out.println("1");
        RouteItem routeItem = (RouteItem)routeRelaventObejts.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
        //如果是更新路线，删除分枝。
        //  System.out.println("2");
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)routeItem.getObject();
        if(PersistHelper.isPersistent(routeInfo))
        {
            
            routeService.deleteBranchRelavent(routeInfo.getBsoID());
        }
        TechnicsRouteIfc route = handleRoute(listLinkInfo, routeItem);
        //处理节点。
        Collection node = (Collection)routeRelaventObejts.get("consRouteNode");
        
        handleRouteItem(node, route);
        //处理节点关联。
        Collection nodelink = (Collection)routeRelaventObejts.get("consRouteNodeLink");
       
        handleRouteItem(nodelink, route);
        //处理分枝。
        Collection branch = (Collection)routeRelaventObejts.get("consTechnicsRouteBranch");
       
        handleRouteItem(branch, route);
        //处理分枝关联。
        Collection branchNodeLink = (Collection)routeRelaventObejts.get("consRouteBranchNodeLink");
       
        handleRouteItem(branchNodeLink, route);
        return route;
    }

    //    /**
    //     * 保存路线相关信息，包括分支、节点及其关联等
    //     * @param listLinkInfo
    //     * @param routeRelaventObejts
    //     */
    //    protected static void doSaveRoute(ListRoutePartLinkIfc listLinkInfo, HashMap routeRelaventObejts)
    //    {
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("-> RouteListHandler.doSave - IN ***************");
    //        }
    //        TechnicsRouteService routeService = RouteHelper.getRouteService();
    //        //处理路线。
    //        // System.out.println("1");
    //        RouteItem routeItem = (RouteItem)routeRelaventObejts.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
    //        //如果是更新路线，删除分枝。
    //        //  System.out.println("2");
    //        if(routeItem != null)
    //        {
    //            TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)routeItem.getObject();
    //            if(PersistHelper.isPersistent(routeInfo))
    //            {
    //                if(TechnicsRouteServiceEJB.VERBOSE)
    //                {
    //                    System.out.println("RouteListHandler : routeID = " + routeInfo.getBsoID());
    //                }
    //                routeService.deleteBranchRelavent(routeInfo.getBsoID());
    //            }
    //            handleRoute(listLinkInfo, routeItem);
    //        }else
    //        {
    //            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    //            pservice.saveValueInfo(listLinkInfo);
    //        }
    //        //处理节点。
    //        Collection node = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("开始进入： handleRouteItem(node);");
    //        }
    //        handleRouteItem(node);
    //        //处理节点关联。
    //        Collection nodelink = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("开始进入： handleRouteItem(nodelink);");
    //        }
    //        handleRouteItem(nodelink);
    //
    //        //处理分枝。
    //        Collection branch = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("开始进入： handleRouteItem(branch);");
    //        }
    //        handleRouteItem(branch);
    //        //处理分枝关联。
    //        Collection branchNodeLink = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("开始进入： handleRouteItem(branchNodeLink);");
    //        }
    //        handleRouteItem(branchNodeLink);
    //        
    //    }

    private static TechnicsRouteIfc handleRoute(ListRoutePartLinkIfc listLinkInfo, RouteItem routeItem) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter the SaveRouteHandler:handleRoute():110 row");
        }
        if(routeItem.getState().equalsIgnoreCase(RouteItem.DELETE))
        {
            throw new QMException("删除路线有专用方法，不在此处处理");
        }
        //begin 20120116 xucy add
        TechnicsRouteIfc routeInfo = null;
        if(listLinkInfo == null)
        {
            routeInfo = (TechnicsRouteIfc)routeItem.getObject();
        }else
        {
            boolean flag = listLinkInfo.getRouteID() != null && !listLinkInfo.getRouteID().equals("");
            if(flag)
            {
                routeInfo = listLinkInfo.getRouteInfo();
            }else
            {
                routeInfo = (TechnicsRouteIfc)routeItem.getObject();
            }
        }
        //end 20120116 xucy add
        TechnicsRouteService routeService = RouteHelper.getRouteService();
        //更新或创建工艺路线。
        routeService.saveRoute(listLinkInfo, routeInfo);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("exit the SaveRouteHandler:handleRoute():120 row");
        }
        return routeInfo;
    }

    private static void handleRouteItem(Collection routeContainObj, TechnicsRouteIfc route) throws QMException
    {
        if(routeContainObj == null)
        {
            return;
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter routeListHandler's handleRouteItem........");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        for(Iterator iter = routeContainObj.iterator();iter.hasNext();)
        {
            RouteItem routeItem = (RouteItem)iter.next();
            BaseValueIfc info = (BaseValueIfc)routeItem.getObject();
            RParentJPanel.setObjectNotPersist(info);
            if(routeItem.getState().equals(RouteItem.CREATE) || routeItem.getState().equals(RouteItem.UPDATE))
            {
                //20120117 xucy add 保存更新的艺准时应用
                if(info instanceof RouteNodeInfo)
                {
                    ((RouteNodeInfo)info).setRouteInfo(route);
                }else if(info instanceof RouteNodeLinkInfo)
                {
                    ((RouteNodeLinkInfo)info).setRouteInfo(route);
                }else if(info instanceof TechnicsRouteBranchInfo)
                {

                    ((TechnicsRouteBranchInfo)info).setRouteInfo(route);
                }
                //20120117 xucy add
                pservice.saveValueInfo(info);

                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    System.out.println(" enter create clause, routeListHandler's handleRouteItem , save success ,bsoid = " + info.getBsoID());
                }
            }
            if(routeItem.getState().equals(RouteItem.DELETE))
            {
                if(info.getBsoID() == null || info.getBsoID().trim().length() < 1)
                {
                    throw new QMException("删除对象不能没有bsoid");
                }
                pservice.removeValueInfo(info);
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    System.out.println("enter delete clause, routeListHandler's handleRouteItem , delete success ,bsoid = " + info.getBsoID());
                }
            }
        }
    }

    //xucy 20111228 add
    private static void handleRouteItem(ArrayList routeContainObj) throws QMException
    {
        if(routeContainObj == null)
        {
            return;
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter routeListHandler's handleRouteItem........");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        for(int i = 0, j = routeContainObj.size();i < j;i++)
        {
            RouteItem routeItem = (RouteItem)routeContainObj.get(i);
            BaseValueIfc info = (BaseValueIfc)routeItem.getObject();
            if(routeItem.getState().equals(RouteItem.CREATE) || routeItem.getState().equals(RouteItem.UPDATE))
            {
                pservice.saveValueInfo(info);
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    System.out.println(" enter create clause, routeListHandler's handleRouteItem , save success ,bsoid = " + info.getBsoID());
                }
            }
            if(routeItem.getState().equals(RouteItem.DELETE))
            {
                if(info.getBsoID() == null || info.getBsoID().trim().length() < 1)
                {
                    throw new QMException("删除对象不能没有bsoid");
                }
                /*
                 * try { pservice.deleteValueInfo(info); } catch(QMException e) { //System.out.println("################此异常是正常现象，不影响系统正常运行。"); }
                 */
                pservice.removeValueInfo(info);
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    System.out.println("enter delete clause, routeListHandler's handleRouteItem , delete success ,bsoid = " + info.getBsoID());
                }
            }
        }
    }
    /*
     * private void handleNode(Collection node) throws QMException { for(Iterator iter = node.iterator(); iter.hasNext();) { RouteItem routeItem = (RouteItem)iter.next();
     * if(routeItem.getState().equals(RouteItem.ROUTEALTER) || routeItem.getState().equals(RouteItem.UPDATE)) {
     * 
     * } if(routeItem.getState().equals(RouteItem.DELETE)) {
     * 
     * } } }
     * 
     * private void handleNodeLink(Collection nodelink) throws QMException {
     * 
     * }
     * 
     * private void handleBranch(Collection branch) throws QMException {
     * 
     * }
     * 
     * private void handleBranchNodeLink(Collection branchNodeLink) throws QMException {
     * 
     * }
     */
}
