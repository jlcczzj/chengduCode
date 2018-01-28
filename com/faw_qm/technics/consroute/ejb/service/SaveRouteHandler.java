/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
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
 * ���湤��·�߱���������ͻ��˴��������·�߱���Ϣ������·�ߺ����Ӧ�Ľڵ㼰�ڵ� ��Ĺ����� if(assertion instanceof ReplaceAssertion) { ��ʱ���ҵ�����״̬��wrkʱ����������ҵ����󡣷��򣬳������ʱ��ҵ�������Ҫ �ĳ����°汾�� MasterIfc masterinfo = (MasterIfc)info.getMaster();
 * pservice.updateBso(masterinfo, false); } ·�ߴ�������� 1.��һ��·�ߴ��г��ֶ��װ�䵥λ,������·�ߴ�������� 2.��һ��·�ߴ�װ�䵥λ�������һ���ڵ�,������·�ߴ�������򣬴��������ڿͻ���? ���ж�: 3.һ��·�ߵ�λ��ͬ����λ���ظ�����,�ڵ�������ͬ,������·�ߴ�������� ��getRouteNodes��ء�
 * ע�⣺����������ҵ���ʼ�ڵ㣬�������á� 1.�˲���ÿ�α���ʱ��������Ϊ������µ���ʼ�ڵ�������ʼ�ڵ�ǰ�����ʼ�ڵ㡣 2.����͸��´�������ڲ鿴���٣����Դ˲��������ڲ鿴ʱ����
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
        //����·�ߡ�
        // System.out.println("1");
        RouteItem routeItem = (RouteItem)routeRelaventObejts.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
        //����Ǹ���·�ߣ�ɾ����֦��
        //  System.out.println("2");
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)routeItem.getObject();
        if(PersistHelper.isPersistent(routeInfo))
        {
            
            routeService.deleteBranchRelavent(routeInfo.getBsoID());
        }
        TechnicsRouteIfc route = handleRoute(listLinkInfo, routeItem);
        //����ڵ㡣
        Collection node = (Collection)routeRelaventObejts.get("consRouteNode");
        
        handleRouteItem(node, route);
        //����ڵ������
        Collection nodelink = (Collection)routeRelaventObejts.get("consRouteNodeLink");
       
        handleRouteItem(nodelink, route);
        //�����֦��
        Collection branch = (Collection)routeRelaventObejts.get("consTechnicsRouteBranch");
       
        handleRouteItem(branch, route);
        //�����֦������
        Collection branchNodeLink = (Collection)routeRelaventObejts.get("consRouteBranchNodeLink");
       
        handleRouteItem(branchNodeLink, route);
        return route;
    }

    //    /**
    //     * ����·�������Ϣ��������֧���ڵ㼰�������
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
    //        //����·�ߡ�
    //        // System.out.println("1");
    //        RouteItem routeItem = (RouteItem)routeRelaventObejts.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
    //        //����Ǹ���·�ߣ�ɾ����֦��
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
    //        //����ڵ㡣
    //        Collection node = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("��ʼ���룺 handleRouteItem(node);");
    //        }
    //        handleRouteItem(node);
    //        //����ڵ������
    //        Collection nodelink = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("��ʼ���룺 handleRouteItem(nodelink);");
    //        }
    //        handleRouteItem(nodelink);
    //
    //        //�����֦��
    //        Collection branch = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("��ʼ���룺 handleRouteItem(branch);");
    //        }
    //        handleRouteItem(branch);
    //        //�����֦������
    //        Collection branchNodeLink = (Collection)routeRelaventObejts.get(TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
    //        if(TechnicsRouteServiceEJB.VERBOSE)
    //        {
    //            System.out.println("��ʼ���룺 handleRouteItem(branchNodeLink);");
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
            throw new QMException("ɾ��·����ר�÷��������ڴ˴�����");
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
        //���»򴴽�����·�ߡ�
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
                //20120117 xucy add ������µ���׼ʱӦ��
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
                    throw new QMException("ɾ��������û��bsoid");
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
                    throw new QMException("ɾ��������û��bsoid");
                }
                /*
                 * try { pservice.deleteValueInfo(info); } catch(QMException e) { //System.out.println("################���쳣���������󣬲�Ӱ��ϵͳ�������С�"); }
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
