/** ���ɳ���RouteListImportExportHandler.java	1.1  2004/05/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �������·�ߵ��빦�ܸ��� 2014-1-6 ����
 * SS2 �������·�ߵ���,·�ߴ�ֻ����֯����-����������µĴ������� 2014-2-18 ����
 * SS3 ���·�ߵ��룬���ӡ��㲿���汾�����Ե��룬ԭ�С���ʷ�汾��Ĭ��Ϊ��  2014-2-26 ����
 * SS4 ·�ߵ���ʱ,��Ҫ���Ϊ���ж� pante 2014-03-25
 * SS5 �޸ģ����·�ߵ��룬���ݿͻ��鿴һ����֧�������м�¼  2014-3-27 ���� 
 * SS6 �������ʹ�õ����ĵ��뷽��,��Ϊ·�߱�˵����Ϣ��ʽ���� pante 2014-04-29
 * SS7 ˵�����з����� pante 2014-05-06
 * SS8 ��źͱ��������غż�����ȡ��ż���û�еĻ�ȡ��������� liunan 2016-2-29
 * SS9 �ɶ�����ϲ������ָ���λ��֯���� liunan 2016-9-14
 * SS10 A004-2016-3439 �������·�������û���Ա��ô����ߺ�������ָ���ˡ� liunan 2016-10-31
 * SS11 A032-2017-0144 �ɶ�·�ߵ���ʱ�򰴰汾���е��� liunan 2017-1-11
 * SS12 A004-2017-3484 ����·���޷���ʾ����ΪY����ֵ̫���޸�Y����ֵ���� liunan 2017-3-29
 */
package com.faw_qm.technics.consroute.ejb.service;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.conscapproute.graph.RouteItem;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.load.util.LoadDataException;
import com.faw_qm.load.util.LoadHelper;

import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.ejb.service.ProjectService;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.project.model.ProjectInfo;
import com.faw_qm.resource.util.ResourceExportImportHandler;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterInfo;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title:�������ļ����뵼������·�߱� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: һ������</p>
 * @author ������ ����
 * @version 1.0
 */

public class RouteListImportExportHandler
{
    private static final String CACHE_ROUTELIST = "routeList";
    private static final String CACHE_LISTLINK = "listLink";
    private static final String CACHE_ROUTE_MAP = "routeMap";
    private static final String CACHE_ROUTE = "route";
    private final static String RESOURCE = "com.faw_qm.technics.consroute.util.RouteResource";

    /** ���ÿ��·���м�����֧ */
    private static int count = 0;
    private static RouteListLevelType[] levelTypes;
    public RouteListImportExportHandler()
    {}
    //CCBegin SS2
    //CCBegin SS9
    //private static HashMap codingMap = null;
    private static HashMap codingMap = new HashMap();
    //CCEnd SS2
    private static String comp = "";
    //CCEnd SS9
    /**
     * ���湤��·�߱�
     * @param hashtable ���Ա� ���У���š����ơ����ڲ�Ʒ�����ϼС��������ڲ���Ϊ�ա�
     * @param vector Ҫ�������־��Ϣ
     * @return boolean ���������Ƿ���ȷ�� @
     * @throws ServiceLocatorException 
     * @throws LoadDataException 
     */
    public static boolean saveRouteList(Hashtable hashtable, Vector vector) throws QMException
    {

        /*
         * bigversion1==0���������ݿ����е����ݣ� bigversion1==1 ����С�汾 bigversion1==2 �޶�
         */
        Integer bigversion = (Integer)hashtable.get("bigversion");
        int bigversion1 = bigversion.intValue();

        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListImportExportHandler.saveRouteList attributes = " + hashtable);
        }
        //  try {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.
        //   getService("TechnicsRouteService");
        
        TechnicsRouteListInfo listInfo = new TechnicsRouteListInfo();
        //CCBegin SS10
        changePrincipal(LoadHelper.getValue("user", hashtable, LoadHelper.NOT_REQUIRED));
        //CCEnd SS10
        //���
        listInfo.setRouteListNumber(LoadHelper.getValue("routeListNumber", hashtable, LoadHelper.REQUIRED));
        //����
        listInfo.setRouteListName(LoadHelper.getValue("routeListName", hashtable, LoadHelper.REQUIRED));
        //˵��
        listInfo.setRouteListDescription(LoadHelper.getValue("routeListDescription", hashtable, LoadHelper.NOT_REQUIRED));
        String routeListLevel = LoadHelper.getValue("routeListLevel", hashtable, LoadHelper.REQUIRED);
        //����
        listInfo.setRouteListLevel(routeListLevel);
        String routeListDepartment = LoadHelper.getValue("routeListDepartment", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListDepartmentParent = LoadHelper.getValue("routeListDepartmentParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListDepartment != null && !routeListDepartment.trim().equals(""))
        {

            //��λ
            listInfo.setRouteListDepartment(ResourceExportImportHandler.importCode(routeListDepartment, routeListDepartmentParent).getBsoID());
        }
        String routeListState = LoadHelper.getValue("routeListState", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListStateParent = LoadHelper.getValue("routeListStateParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListState != null && !routeListState.trim().equals(""))
        {

            //״̬
            listInfo.setRouteListState(ResourceExportImportHandler.importCode(routeListState, routeListStateParent).getCodeContent());

            //��Ʒ
        }
      //CCBegin SS4
        String productNumber = LoadHelper.getValue("productNumber", hashtable, LoadHelper.NOT_REQUIRED);
        if(productNumber != null && !productNumber.trim().equals(""))
        	listInfo.setProductMasterID(findPartMaster(productNumber).getBsoID());
      //CCEnd SS4
        //λ��
        String location = LoadHelper.getValue("location", hashtable, LoadHelper.REQUIRED);
        FolderService folderService = (FolderService)EJBServiceHelper.getService("FolderService");
        //��ҵ�������ŵ����ϼ���
        listInfo = (TechnicsRouteListInfo)folderService.assignFolder(listInfo, location);
        //��������ģ��
        String lifecycleTemplate = (LoadHelper.getValue("lifecycleTemplate", hashtable, LoadHelper.REQUIRED));
        LifeCycleService lservice = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
        listInfo = (TechnicsRouteListInfo)lservice.setLifeCycle(listInfo, lservice.getLifeCycleTemplate(lifecycleTemplate));
        //��������״̬
        String stateStr = LoadHelper.getValue("lifecycleState", hashtable, LoadHelper.NOT_REQUIRED); //����
        if(stateStr != null && !stateStr.equals(""))
        {
            stateStr = RouteHelper.getValue(LifeCycleState.getLifeCycleStateSet(), stateStr); //Ӣ��
            LifeCycleState state = LifeCycleState.toLifeCycleState(stateStr);
            listInfo.setLifeCycleState(state);
        }
        //��Ŀ
        String projectName = LoadHelper.getValue("projectName", hashtable, LoadHelper.NOT_REQUIRED);
        if(projectName != null && !projectName.equals(""))
        {
            ProjectService projectService = (ProjectService)EJBServiceHelper.getService("ProjectService");
            ProjectIfc projectInfo = projectService.getProject(projectName);
            if(projectInfo != null)
            {
                listInfo.setProjectId(projectInfo.getBsoID());
            }
        }
        //�Ƿ��а汾.����а汾�������޶������û�汾�����½���
        String isHaveVersion = LoadHelper.getValue("isHasVersion", hashtable, LoadHelper.NOT_REQUIRED);

        //add by guoxl                                         
        TechnicsRouteService routeListService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        TechnicsRouteListInfo oldRoutListInfo = routeListService.findRouteListByNum(listInfo.getRouteListNumber());

        if(bigversion1 == 0)
        {//������

            if(oldRoutListInfo == null)
            {

                //�����룬���ݿ���û�д����ݣ��򴴽�

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //�����룬���ݿ����д����ݣ�������������Ѿ����ڡ���Ϣ
                vector.add("�����Ѿ����ڣ�");

            }

        }else if(bigversion1 == 1)
        {//С�汾����

            if(oldRoutListInfo == null)
            {

                //����С�汾�����ݿ���û�д����ݣ��򴴽�

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //����С�汾,�����ݣ�������С�汾
                listInfo = (TechnicsRouteListInfo)checkInRouteList(listInfo);

            }

        }else if(bigversion1 == 2)
        {//�޶�
            if(oldRoutListInfo == null)
            {

                //�޶������ݿ�û�д˼�¼���򴴽�
                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //�޶�,���������޶�

                listInfo = (TechnicsRouteListInfo)reviseRouteList(listInfo);

            }

        }
        LoadHelper.setCacheValue(CACHE_ROUTELIST, listInfo);
        return true;
        //add by guoxl

        //      //�޶�
        //      if (isHaveVersion != null && !isHaveVersion.equals("") &&
        //          Boolean.getBoolean(isHaveVersion)) {
        //        listInfo = (TechnicsRouteListInfo) reviseRouteList(listInfo);
        //        if (TechnicsRouteServiceEJB.VERBOSE) {
        //          System.out.println("�޶������Ĺ���·�߱�" + listInfo);
        //          if (listInfo != null) {
        //            System.out.println("�޶������Ĺ���·�߱�" + listInfo.getBsoID());
        //          }
        //        }
        //      }
        //      else {//���޶�
        //      //  System.out.println("����ǰ�Ĺ���·�߱�" + listInfo.getBsoID());
        //        listInfo = (TechnicsRouteListInfo) pservice.saveValueInfo(listInfo);
        //        //  listInfo =  (TechnicsRouteListInfo)routeService.storeRouteList(listInfo);
        //        if (TechnicsRouteServiceEJB.VERBOSE) {
        //          System.out.println("�����Ĺ���·�߱�" + listInfo);
        //          if (listInfo != null) {
        //            System.out.println("�����Ĺ���·�߱�" + listInfo.getBsoID());
        //          }
        //        }
        //      }
        //      LoadHelper.setCacheValue(CACHE_ROUTELIST, listInfo);
        //      vector.add(listInfo);
        //    }
        //    catch (Exception e) {
        //      e.printStackTrace();
        //      return false;
        //    }
        //    return true;
    }

    /**
     * ����С�汾
     * @param routeInfo �⵼���δ�־û���·�߱�
     * @return TechnicsRouteListInfo С�汾�������ֵ����
     * @author ������
     * @throws QMException 
     * @throws  
     */
    private static TechnicsRouteListInfo checkInRouteList(TechnicsRouteListInfo routeInfo) throws  QMException
    {
        //�����⵼���·�߱���ļ�������Ǹ������ϼ�����������
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)routeInfo))
        {

            String routeListNum = routeInfo.getRouteListNumber();
            throw new QMException("�⵼���·�߱�(" + routeListNum + ")�Ĵ��λ�ò�Ӧ�ڸ������ϼУ�");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        QMQuery query = new QMQuery("TechnicsRouteListMaster");
        QueryCondition condition1 = new QueryCondition("routeListNumber", "=", routeInfo.getRouteListNumber());
        query.addCondition(condition1);

        Collection c = pservice.findValueInfo(query);

        TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)c.toArray()[0];

        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("find master is :" + masterinfo);
        }

        TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        //������е�����С�汾
        TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo)routeService.getLatestVesion(masterinfo);

        //���������С�汾��������
        //����ö����ڸ������ϼл��ѱ����������������
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)oldInfo) || CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)oldInfo))
        {
            Object[] obj = {oldInfo.getRouteListNumber()};
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "11", obj));
        }
        //��ð汾����
        VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        //����С�汾
        TechnicsRouteListInfo newVersionInfo = (TechnicsRouteListInfo)vcService.newIteration((VersionedIfc)oldInfo);

        newVersionInfo = (TechnicsRouteListInfo)vcService.supersede(oldInfo, newVersionInfo);

        //������������ģ��
        newVersionInfo.setLifeCycleTemplate(routeInfo.getLifeCycleTemplate());
        //������������״̬
        newVersionInfo.setLifeCycleState(routeInfo.getLifeCycleState());
        //���ù�����
        newVersionInfo.setProjectId(routeInfo.getProjectId());
        newVersionInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(newVersionInfo);

        return newVersionInfo;
    }

    /**
     * ����·�߱������Ĺ�����
     * @param hashtable ���Ա�
     * @param vector Ҫ�������־��Ϣ
     * @return ���������Ƿ���ȷ
     */
    public static boolean saveListRoutePartLink(Hashtable hashtable, Vector vector)
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListImportExportHandler.saveListRoutePartLink attributes = " + hashtable);
        }
        try
        {
            //���������ʼ
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            String partNumber = LoadHelper.getValue("partNumber", hashtable, LoadHelper.REQUIRED);
            //CCBegin SS4
//            String parentPartNum = LoadHelper.getValue("parentPartNum", hashtable, LoadHelper.REQUIRED);
            String parentPartNum = LoadHelper.getValue("parentPartNum", hashtable, LoadHelper.NOT_REQUIRED);
          //CCEnd SS4
            String count = LoadHelper.getValue("count", hashtable, LoadHelper.REQUIRED);
            //CCBegin SS1
            //String partMasterID = findPartMaster(partNumber).getBsoID();
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            //CCBegin SS8
            //QMPartMasterIfc partMaster = findPartMaster(partNumber);
            //QMPartIfc part = routeService.filteredIterationsOfByDefault(partMaster);
            //��ȡ��ŵ����ķ����㲿����û�о�ȡ����������㲿����
            QMPartIfc part = findPart(partNumber);
            //CCEnd SS8
            String partBsoID = part.getBsoID();
            //�ɹ���ʶ
          //CCBegin SS4
//            String stockID = LoadHelper.getValue("stockID", hashtable, LoadHelper.REQUIRED);
            String stockID = LoadHelper.getValue("stockID", hashtable, LoadHelper.NOT_REQUIRED);
          //CCEnd SS4
            if(stockID==null||stockID.equals("")){
            	stockID = "";
            }
            //CCBegin SS3
            //Ԥ������----�����ʷ�汾
            String attribute1 = LoadHelper.getValue("attribute1", hashtable, LoadHelper.REQUIRED);
            String partVersion = part.getVersionValue();
            	//LoadHelper.getValue("partVersion", hashtable, LoadHelper.REQUIRED);
            //CCEnd SS3
            TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)LoadHelper.getCacheValue(CACHE_ROUTELIST);
            if(TechnicsRouteServiceEJB.VERBOSE)
            {
                System.out.println("routeService = " + routeService);
                System.out.println("routeList = " + routeList);
                //System.out.println("partMasterID = " + partMasterID);
            }
            //�����·�߱����㲿���Ĺ����Ѵ��ڣ������½��ù�����
            if(routeService.getListRoutePartLink(routeList.getBsoID(), partBsoID, parentPartNum) == null)
            {
                //û�й������½�����
                ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.newListRoutePartLinkInfo(routeList, partBsoID);
                //CCBegin SS4
////              if(parentPartNum != null && !parentPartNum.trim().equals(""))
////              {
//              QMPartMasterIfc parent = findPartMaster(parentPartNum);
//              if(parent!=null){
//                  QMPartIfc partParent = routeService.filteredIterationsOfByDefault(parent);
//                  listLinkInfo.setParentPartID(partParent.getBsoID());
                QMPartMasterIfc parent = null;
                if(parentPartNum != null && !parentPartNum.trim().equals(""))
                {
                	//CCBegin SS8
                	//parent = findPartMaster(parentPartNum);
                	//                if(parent!=null){
                	//QMPartIfc partParent = routeService.filteredIterationsOfByDefault(parent);
                	//��ȡ��ŵ����ķ����㲿����û�о�ȡ����������㲿����
                	QMPartIfc partParent = findPart(parentPartNum);
                	//CCEnd SS8
                	
                	if(partParent!=null){
                		listLinkInfo.setParentPartID(partParent.getBsoID());
                		listLinkInfo.setParentPartNum(parentPartNum);
                		listLinkInfo.setParentPartName(partParent.getPartName());
                	}
                	//CCEnd SS4
                }

                listLinkInfo.setStockID(stockID);//�洢�ɹ���ʶ
                listLinkInfo.setModifyIdenty("����");
                //CCBegin SS3
                //�洢Ԥ������---�����ʷ�汾
                if(attribute1!=null&&!attribute1.equals("")&&!attribute1.equals("null")){
                	listLinkInfo.setAttribute1(attribute1);
                }
	              if(partVersion!=null&&!partVersion.equals("")&&!partVersion.equals("null")){
	            	listLinkInfo.setPartVersion(partVersion);
	            }
                //CCEnd SS3
//                }
                if(count != null && !count.trim().equals(""))
                {
                    //listLinkInfo.setCount(new Integer(count).intValue());
                	listLinkInfo.setProductCount(new Integer(count).intValue());
                }
                //CCEnd SS1

                //�������
                pservice.saveValueInfo(listLinkInfo);
                //����ù���
                LoadHelper.setCacheValue(partNumber + parentPartNum, listLinkInfo);
                vector.add(listLinkInfo);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static RouteItem setRouteItem(BaseValueIfc info)
    {
        RouteItem item = new RouteItem();
        item.setObject(info);
        item.setState(RouteItem.CREATE);
        return item;
    }
    /**
     * ����·�߷�֧��
     * @param hashtable ���Ա�
     * @param vector Ҫ�������־��Ϣ
     * @return ���������Ƿ���ȷ
     */
    public static boolean saveRouteBranch(Hashtable hashtable, Vector vector)
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListImportExportHandler.saveRouteBranch attributes = " + hashtable);
        }
        try
        {
        	//CCBegin SS9
        	comp = DocServiceHelper.getUserFromCompany();
        	System.out.println("comp==="+comp);
        	//CCEnd SS9
            count = count + 1;
            TechnicsRouteIfc routeInfo = null;
            HashMap routeMap = null;
            if(LoadHelper.getCacheValue(CACHE_ROUTE_MAP) == null)
            {
                String description = LoadHelper.getValue("routeDescription", hashtable, LoadHelper.NOT_REQUIRED);
                routeInfo = new TechnicsRouteInfo();
                routeInfo.setRouteDescription(description);
                RouteItem routeItem = setRouteItem(routeInfo);
                routeMap = new HashMap();
                routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME, routeItem);
                //����·��ӳ���
                LoadHelper.setCacheValue(CACHE_ROUTE_MAP, routeMap);
            }else
            {
                routeMap = (HashMap)LoadHelper.getCacheValue(CACHE_ROUTE_MAP);
                RouteItem routeItem = (RouteItem)routeMap.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
                routeInfo = (TechnicsRouteIfc)routeItem.getObject();
            }

            String isMainRoute = LoadHelper.getValue("isMainRoute", hashtable, LoadHelper.NOT_REQUIRED);
            TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
            if(isMainRoute != null && isMainRoute.equalsIgnoreCase("true"))
            {
                routeBranchInfo.setMainRoute(true);
            }else
            {
                routeBranchInfo.setMainRoute(false);

            }
            routeBranchInfo.setRouteInfo(routeInfo);
            //��÷�֧���ϡ�
            Collection branchs = (Collection)routeMap.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
            //���÷�֧
            if(branchs == null)
            {
                branchs = new Vector();
                routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME, branchs);
            }
            //CCBegin SS5
//            RouteItem branchItem = setRouteItem(routeBranchInfo);
//            branchs.add(branchItem);
            //CCEnd SS5

            //���ýڵ㡣
            Collection nodes = (Collection)routeMap.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
            if(nodes == null)
            {
                nodes = new Vector();
                routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_BSONAME, nodes);
            }

            Vector tempNodeVector = new Vector();
            //CCBegin SS12
            //int y = 60 * count;
            int y = 200 + count;
            //CCEnd SS12
            int x = 0;
            //��������·�߽ڵ㡣
            String manuString = LoadHelper.getValue("manuRoute", hashtable, LoadHelper.NOT_REQUIRED);
            String routeBranchStr = ""; //TD 2338 
            if(manuString != null)
            {
                routeBranchStr = manuString.replaceAll("--", "��"); //TD 2338 Begin
                Collection nodeString = new Vector();
                StringTokenizer token = new StringTokenizer(manuString, "--");
                while(token.hasMoreTokens())
                {
                    nodeString.add(token.nextToken());
                }
                for(Iterator manuIterator = nodeString.iterator();manuIterator.hasNext();)
                {
                    String manu = (String)manuIterator.next();
                    RouteNodeIfc nodeInfo = new RouteNodeInfo();
                    nodeInfo.setRouteType(RouteCategoryType.MANUFACTUREROUTE.getDisplay());
                    nodeInfo.setNodeDepartment(getDepartmentID(manu));
                    nodeInfo.setRouteInfo(routeInfo);
                    x = x + 100;
                    nodeInfo.setX(new Long(x).longValue());
                    nodeInfo.setY(new Long(y).longValue());
                    RouteItem nodeItem = setRouteItem(nodeInfo);
                    nodes.add(nodeItem);
                    tempNodeVector.add(nodeItem);
                }
            }else
            { //TD 2338 
                routeBranchStr = "��";

            }
            //����װ��·�߽ڵ㡣
            String assemString = LoadHelper.getValue("assemRoute", hashtable, LoadHelper.NOT_REQUIRED);

            if(assemString != null)
            {
                routeBranchStr = routeBranchStr + "@" + assemString; //TD 2338 
                RouteNodeIfc nodeInfo = new RouteNodeInfo();
                nodeInfo.setRouteType(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
                nodeInfo.setNodeDepartment(getDepartmentID(assemString));
                nodeInfo.setRouteInfo(routeInfo);
                RouteItem nodeItem = setRouteItem(nodeInfo);
                x = x + 100;
                nodeInfo.setX(new Long(x).longValue());
                nodeInfo.setY(new Long(y).longValue());
                nodes.add(nodeItem);
                tempNodeVector.add(nodeItem);
            }else
            { //TD 2338 Begin
                routeBranchStr = routeBranchStr + "@��";
            }
            //TD 2338 Begin
            if(routeBranchStr != null && !routeBranchStr.equals("") && !routeBranchStr.equals("��@��"))
            {
                routeBranchInfo.setRouteStr(routeBranchStr);
                //CCBegin SS5
                RouteItem branchItem = setRouteItem(routeBranchInfo);
                //CCEnd SS5
                branchs.add(branchItem);
            }
            //CCBegin SS5 ������жϴ�������
            else{
                routeBranchInfo.setRouteStr(routeBranchStr);
                RouteItem branchItem = setRouteItem(routeBranchInfo);
                branchs.add(branchItem);
            }
            //CCEnd SS5
            //TD 2338 end
            //���ýڵ����
            Collection nodeLinks = (Collection)routeMap.get(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
            if(nodeLinks == null)
            {
                nodeLinks = new Vector();
                routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME, nodeLinks);
            }
            RouteNodeIfc temp = null;
            for(Iterator nodeIter = tempNodeVector.iterator();nodeIter.hasNext();)
            {
                RouteItem destinationItem = (RouteItem)nodeIter.next();
                RouteNodeIfc destinationInfo = (RouteNodeIfc)destinationItem.getObject();
                if(temp != null)
                {
                    RouteNodeLinkIfc nodeLinkInfo1 = new RouteNodeLinkInfo();
                    nodeLinkInfo1.setSourceNode(temp);
                    nodeLinkInfo1.setDestinationNode(destinationInfo);
                    nodeLinkInfo1.setRouteInfo(routeInfo);
                    RouteItem nodelinkItem = setRouteItem(nodeLinkInfo1);
                    nodeLinks.add(nodelinkItem);
                }
                temp = destinationInfo;
            }
            //���÷�֧��ڵ����
            Collection branchNodeLinks = (Collection)routeMap.get(TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
            if(branchNodeLinks == null)
            {
                branchNodeLinks = new Vector();
                routeMap.put(TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME, branchNodeLinks);
            }
            for(Iterator nodeIter = tempNodeVector.iterator();nodeIter.hasNext();)
            {
                RouteItem nodeInfo1Item = (RouteItem)nodeIter.next();
                RouteNodeIfc nodeInfo1 = (RouteNodeIfc)nodeInfo1Item.getObject();
                RouteBranchNodeLinkIfc branchNodeLinkInfo = new RouteBranchNodeLinkInfo();
                branchNodeLinkInfo.setRouteBranchInfo(routeBranchInfo);
                branchNodeLinkInfo.setRouteNodeInfo(nodeInfo1);
                RouteItem branchNodeLinkItem = setRouteItem(branchNodeLinkInfo);
                branchNodeLinks.add(branchNodeLinkItem);
            }
            //vector.add(listLinkInfo);
            String partNumber = LoadHelper.getValue("routePartNumber", hashtable, LoadHelper.REQUIRED);
            //CCBegin SS4
//            String parentPartNum = LoadHelper.getValue("parentPartNum", hashtable, LoadHelper.REQUIRED);
            String parentPartNum = LoadHelper.getValue("parentPartNum", hashtable, LoadHelper.NOT_REQUIRED);
          //CCEnd SS4
            String routeEnd = LoadHelper.getValue("routeEnd", hashtable, LoadHelper.NOT_REQUIRED);
            if(routeEnd != null && routeEnd.equalsIgnoreCase("routeEnd"))
            {
                try
                {
                    saveRoute(hashtable, partNumber, parentPartNum);
                    LoadHelper.removeCacheValue(partNumber + parentPartNum);
                    LoadHelper.removeCacheValue(CACHE_ROUTE_MAP);
                    LoadHelper.removeCacheValue(CACHE_ROUTELIST);
                    count = 0;

                }catch(Exception e)
                {
                    LoadHelper.removeCacheValue(partNumber + parentPartNum);
                    LoadHelper.removeCacheValue(CACHE_ROUTE_MAP);
                    LoadHelper.removeCacheValue(CACHE_ROUTELIST);
                    count = 0;

                    e.printStackTrace();
                    return false;
                }

            }

        }catch(Exception e)
        {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ���ݵ�λ��ƻ�õ�λbsoID
     * @param departmentName ���
     * @return ��λbsoID
     */
    private static String getDepartmentID(String departmentName) throws QMException
    {
        CodingManageService service = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
    	//CCBegin SS2
//    	if(DocServiceHelper.getUserFromCompany().equals("zczx")){
    		//CCBegin SS9
    		//return (String)getCodingMap().get(departmentName);
    		return (String)(((HashMap)getCodingMap().get(comp)).get(departmentName));
    		//CCEnd SS9
//    	}else{
//            return service.getIDbySort(departmentName);
//    	}
    	//CCEnd SS2
    }
    //CCBegin SS2
    /**
     * ������֯�����£�������ĵ����д�������
     * @return �������ݼ���
     * @author wenliu
     */
    private static HashMap getCoding() throws QMException
    {
    	HashMap codeMap = new HashMap();
        CodingManageService service = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
//    	if(DocServiceHelper.getUserFromCompany().equals("zczx")){
    		//CCBegin SS9
    		//Collection col = service.getCoding("�������","��֯����");
    		Collection col = null;
    		if(comp.equals("zczx"))
    		{
    			col = service.getCoding("�������","��֯����");
    		}
    		else if(comp.equals("cd"))
    		{
    			col = service.getCoding("��֯����-cd","�������");
    		}
    		//CCEnd SS9
    		if(col!=null&&col.size()>0){
    			codeMap =  new HashMap();
    			for(Iterator ite = col.iterator();ite.hasNext();){
    				CodingIfc code = (CodingIfc)ite.next();
    				codeMap.put(code.getShorten(), code.getBsoID());//key�Ǵ����ƣ�
    			}
    		}
//    	}
    	return codeMap;
    }
    /**
     * �õ���֯�����£�������ĵĴ��뼯��
     * @return HashMap ���뼯��
     */
    private static HashMap getCodingMap()
    {
        try {
        	//CCBegin SS9
        	/*if(codingMap!=null)
        	{
        		return codingMap;
        	}
        	else
        	{
        		codingMap=getCoding();
        		return codingMap;
        	}*/
        	if(codingMap.containsKey(comp))
        	{
        		return codingMap;
        	}
        	else
        	{
        		codingMap.put(comp,getCoding());
        		return codingMap;
        	}
        	//CCEnd SS9
		} catch (QMException e) {
			e.printStackTrace();
			return new HashMap();
		}
    }
  //CCEnd SS2
    /**
     * ����·��
     * @param hashtable
     * @param partNumber @
     * @throws QMException 
     */
    private static void saveRoute(Hashtable hashtable, String partNumber, String parentPartNum) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListImportExportHandler.saveRoute attributes = " + hashtable);

        }
        //CCBegin SS1
        HashMap routeMap = (HashMap)LoadHelper.getCacheValue(CACHE_ROUTE_MAP);
        ListRoutePartLinkInfo listLinkInfo = (ListRoutePartLinkInfo)LoadHelper.getCacheValue(partNumber + parentPartNum);
        if(listLinkInfo == null)
        {
            throw new QMException("�����ļ������޷�Ϊ�㲿��" + partNumber + "����·�ߡ�");
        }
        String manuRoute = LoadHelper.getValue("manuRoute", hashtable, LoadHelper.REQUIRED);
        String assemRoute = LoadHelper.getValue("assemRoute", hashtable, LoadHelper.NOT_REQUIRED);
        //CCBegin SS4
//        String routeDescription = LoadHelper.getValue("routeDescription", hashtable, LoadHelper.REQUIRED);
        String routeDescription = LoadHelper.getValue("routeDescription", hashtable, LoadHelper.NOT_REQUIRED);
      //CCEnd SS4
        String isMainRoute = LoadHelper.getValue("isMainRoute", hashtable, LoadHelper.NOT_REQUIRED);
        if(manuRoute==null||manuRoute.equals("")||manuRoute.equals("null")){
        	manuRoute  = "";
        }
        if(assemRoute==null||assemRoute.equals("")||assemRoute.equals("null")){
        	assemRoute  = "";
        }
        if(routeDescription==null||routeDescription.equals("")||routeDescription.equals("null")){
        	routeDescription  = "";
        }
        
        //�����ַ���
        if(isMainRoute != null && isMainRoute.equalsIgnoreCase("true"))
        {
        	listLinkInfo.setMainStr(manuRoute+"="+assemRoute);
        }else
        {
        	listLinkInfo.setSecondStr(manuRoute+"="+assemRoute);

        }
        listLinkInfo.setRouteDescription(routeDescription);
        //CCEnd SS1


        RouteHelper.getRouteService().saveRoute(listLinkInfo, routeMap);

        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("exit RouteListImportExportHandler.saveRoute�� ����·�߳ɹ���");

        }
    }

    /**
     * �����㲿����Ż���㲿��ֵ����
     * @param partMasterNumber �㲿�����
     * @return �㲿��ֵ���� @
     */
    private static QMPartMasterInfo findPartMaster(String partMasterNumber) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        QueryCondition condition1 = new QueryCondition("partNumber", "=", partMasterNumber);
        query.addCondition(condition1);
        Collection c = pservice.findValueInfo(query);
        QMPartMasterInfo obj = (QMPartMasterInfo)c.toArray()[0];
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("RouteListImportExportHandler.findPartMaster :" + obj);
        }
        return obj;
    }
    
    
    //CCBegin SS8
    private static QMPartIfc findPart(String partMasterNumber) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        
        //CCBegin SS11
        if(partMasterNumber.indexOf("/")>-1)
        {
        	int l = partMasterNumber.length();
        	int i = partMasterNumber.indexOf("/");
        	if(l-i==2)
        	{
        		String ver = partMasterNumber.substring(l-1);
        		partMasterNumber = partMasterNumber.substring(0, i);
        		
        		QMPartIfc part = null;
        		try
        		{
        			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
        			QMQuery query = new QMQuery("QMPartMaster");
        			QueryCondition condition1 = new QueryCondition("partNumber", "=", partMasterNumber);
        			query.addCondition(condition1);
        			Collection col = pservice.findValueInfo(query);
        			if(col==null||col.size()==0)
        			{
        			}
        			else if(col.size()==1)
        			{
        				QMPartMasterIfc partmaster=(QMPartMasterIfc)col.iterator().next();
        				Collection co=vcService.allVersionsOf(partmaster);
        				for(Iterator iter = co.iterator();iter.hasNext();)
        				{
        					QMPartIfc part1=(QMPartIfc)iter.next();
        					//����а汾��ͬ��������ѭ��������ȡ���°�
        					if(ver!=""&&ver.equals(part1.getVersionID()))
        					{
        						part = part1;
        						break;
        					}
        				}
        			}
        			else if(col.size()>1)
        			{
        				Iterator ite=col.iterator();
        				while(ite.hasNext())
        				{
        					QMPartMasterIfc partmaster=(QMPartMasterIfc)ite.next();
        					if(partmaster.getBsoID().indexOf("BSXUP")!=-1)
        					{
        						continue;
        					}
        					Collection co=vcService.allVersionsOf(partmaster);
        					for(Iterator iter = co.iterator();iter.hasNext();)
        					{
        						QMPartIfc part1=(QMPartIfc)iter.next();
        						//����а汾��ͬ��������ѭ��������ȡ���°�
        						if(ver!=""&&ver.equals(part1.getVersionID()))
        						{
        							part = part1;
        							break;
        						}
        					}
        				}
        			}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        		if(part!=null)
        		{
        			return part;
        		}
        	}
        }
        //CCEnd SS11
        
        QMQuery query = new QMQuery("QMPartMaster");
        QueryCondition condition1 = new QueryCondition("partNumber", "=", partMasterNumber);
        query.addCondition(condition1);
        Collection c = pservice.findValueInfo(query);
        QMPartIfc jfpart = null;
        QMPartIfc bsxpart = null;
        for(Iterator it = c.iterator();it.hasNext();)
        {
        	  QMPartMasterInfo master = (QMPartMasterInfo)it.next();
            QMPartIfc part = routeService.filteredIterationsOfByDefault(master);
            if(part==null)
            {
            	continue;//��master�������ù淶û�еõ�part
            }
            if(part.getViewName().equals("���������ͼ"))
            {
            	  jfpart = part;
            }
            else if(part.getViewName().equals("�����ͼ")||part.getViewName().equals("������ͼ"))
            {
            	  bsxpart = part;
            }
        }
        if(jfpart!=null)
        {
        	  return jfpart;
        }
        else if(bsxpart!=null)
        {
        	  return bsxpart;
        }
        else
        {
        	  return null;
        }
    }
    //CCEnd SS8

    /**
     * ��ָ����·�߱�����޶�
     * @param originalInfo �⵼���δ�־û���·�߱�
     * @return �°汾��·�߱� @
     * @throws QMException 
     * @throws  
     */
    private static TechnicsRouteListIfc reviseRouteList(TechnicsRouteListInfo originalInfo) throws  QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("����RouteListImportExportHandler.reviseRouteList:");
        }
        //�����⵼���·�߱���ļ�������Ǹ������ϼ��������޶�
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)originalInfo))
        {

            String routeListNum = originalInfo.getRouteListNumber();

            throw new QMException("�⵼���·�߱�(" + routeListNum + ")�Ĵ��λ�ò�Ӧ�ڸ������ϼУ�");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("TechnicsRouteListMaster");
        QueryCondition condition1 = new QueryCondition("routeListNumber", "=", originalInfo.getRouteListNumber());
        query.addCondition(condition1);
        Collection c = pservice.findValueInfo(query);
        TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)c.toArray()[0];
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("find master is :" + masterinfo);
        }
        TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        //������е�����С�汾
        TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo)routeService.getLatestVesion(masterinfo);
        //���������С�汾�����޶�
        //����ö����ڸ������ϼл��ѱ�������������޶�
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)oldInfo) || CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)oldInfo))
        {
            Object[] obj = {oldInfo.getRouteListNumber()};
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "10", obj));
        }
        //���ð汾���������°汾
        VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        TechnicsRouteListIfc newVersionInfo = (TechnicsRouteListIfc)vcService.newVersion((VersionedIfc)oldInfo);
        //������λ��
        FolderService folderService = (FolderService)EJBServiceHelper.getService("FolderService");
        folderService.assignFolder(newVersionInfo, originalInfo.getLocation());
        //������������ģ��
        //    LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService(
        //        "LifeCycleService");
        //    lservice.setLifeCycle(newVersionInfo,
        //                          lservice.getLifeCycleTemplate(originalInfo.
        //        getLifeCycleTemplate()));
        newVersionInfo.setLifeCycleTemplate(originalInfo.getLifeCycleTemplate());
        //������������״̬
        newVersionInfo.setLifeCycleState(originalInfo.getLifeCycleState());
        //���ù�����
        newVersionInfo.setProjectId(originalInfo.getProjectId());

        return routeService.newVersion(newVersionInfo);
    }

    public static void exportRouteList(ArrayList technics, String filename, boolean flag) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("CappExportImportHandler. exportTechnics " + " (ArrayList technics,String filename, boolean flag) begin technics=" + technics + " filename=" + filename + " flag="
                    + flag);
        }
        if(technics == null || filename == null)
        {
            return;
        }

        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0, n = technics.size();i < n;i++)
        {
            TechnicsRouteListIfc tech = (TechnicsRouteListIfc)technics.get(i);
            // ����������������Ϣ
            stringbuffer = exportRouteListAttr(tech, stringbuffer);
            //�������㲿���Լ���������·��
            stringbuffer = exportPartByList(tech, stringbuffer);
        }
        writeFile(stringbuffer, filename, flag);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("CappExportImportHandler. exportTechnics end");
        }
    }

    private static StringBuffer exportPartByList(TechnicsRouteListIfc tech, StringBuffer stringbuffer) throws QMException
    {
        TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection links = routeService.getRouteListLinkParts(tech);
        if(links == null || links.size() == 0)
        {
            return stringbuffer;
        }
        for(Iterator it = links.iterator();it.hasNext();)
        {
            ListRoutePartLinkIfc docMaster = (ListRoutePartLinkIfc)it.next();
            stringbuffer.append("PartMaster,");
            stringbuffer.append(docMaster.getPartMasterInfo().getPartNumber() + ",");
            stringbuffer.append(docMaster.getParentPartNum() + ",");
            stringbuffer.append(docMaster.getCount());
            stringbuffer.append("\n");
        }
        for(Iterator it = links.iterator();it.hasNext();)
        {
            ListRoutePartLinkIfc docMaster = (ListRoutePartLinkIfc)it.next();
            if(docMaster.getRouteID() != null)
            {
                Collection branchs = getRouteBranchs(docMaster.getRouteID());
                TechnicsRouteIfc route = (TechnicsRouteIfc)service.refreshInfo(docMaster.getRouteID());
                Object[] objs = branchs.toArray();
                int size = objs.length;
                if(size == 0)
                {
                    continue;
                }
                for(int i = 0;i < size - 1;i++)
                {
                    TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)objs[i];
                    stringbuffer.append("RouteBranch,");
                    stringbuffer.append(docMaster.getPartMasterInfo().getPartNumber() + ",");
                    stringbuffer.append(docMaster.getParentPartNum() + ",");
                    stringbuffer.append(route.getRouteDescription() + ",");
                    //�Ƿ�����Ҫ·��
                    stringbuffer.append(branch.getMainRoute() + ",");
                    //���֦���������٣�δ�����������鷽ʽ��
                    Collection branchNodes = routeService.getBranchRouteNodes(branch);
                    //obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
                    Object[] obj = routeService.getNodeType(branchNodes);
                    appendOnlyOneBranch(obj, stringbuffer);
                    stringbuffer.append(",");
                    stringbuffer.append("\n");
                }
                TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)objs[size - 1];
                stringbuffer.append("RouteBranch,");
                stringbuffer.append(docMaster.getPartMasterInfo().getPartNumber() + ",");
                stringbuffer.append(docMaster.getParentPartNum() + ",");
                stringbuffer.append(route.getRouteDescription() + ",");
                //�Ƿ�����Ҫ·��
                stringbuffer.append(branch.getMainRoute() + ",");
                //���֦���������٣�δ�����������鷽ʽ��
                Collection branchNodes = routeService.getBranchRouteNodes(branch);
                //obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
                Object[] obj = routeService.getNodeType(branchNodes);
                appendOnlyOneBranch(obj, stringbuffer);
                stringbuffer.append("routeEnd");
                stringbuffer.append("\n");
            }
        }
        return stringbuffer;
    }

    /**
     * @roseuid 40399137004C
     * @J2EE_METHOD -- getRouteBranchs ���ݹ���·��ID��ù���·�߷�֦��ض���
     * @return HashMap key=����·�߷�ֵ֦����, value=·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
     * @throws QMException 
     */
    public static Collection getRouteBranchs(String routeID) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    private static void appendOnlyOneBranch(Object[] obj, StringBuffer result)
    {

        Collection manuColl = (Collection)obj[0];
        int k = 0;
        for(Iterator iter2 = manuColl.iterator();iter2.hasNext();)
        {
            k++;
            RouteNodeIfc manuNode = (RouteNodeIfc)iter2.next();
            if(manuColl.size() == k)
            {
                result.append(manuNode.getNodeDepartmentName() + ",");
            }else
            {
                result.append(manuNode.getNodeDepartmentName() + "--");
            }
        }
        if(manuColl.size() == 0)
        {
            result.append(",");
        }
        RouteNodeIfc assemNode = (RouteNodeIfc)obj[1];
        if(assemNode != null)
        {
            result.append(assemNode.getNodeDepartmentName());
        }
        result.append(",");
    }

    private static StringBuffer exportCode(StringBuffer string, String codeID) throws QMException
    {
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        BaseValueIfc code = service.refreshInfo(codeID);
        if(code != null)
        {
            if(code instanceof CodingIfc)
            {
                string.append(((CodingIfc)code).toString() + "," + ((CodingIfc)code).getCodingClassification());
            }else if(code instanceof CodingClassificationIfc)
            {
                string.append(((CodingClassificationIfc)code).toString() + "," + ((CodingClassificationIfc)code).getParent());
            }
        }else
        {
            string.append(",");
        }

        return string;
    }

    /**
     * ����·�߱�Ļ���������Ϣ
     * @param tech QMTechnicsIfc ·�߱�ֵ����
     * @param stringbuffer StringBuffer �������ַ���
     * @return StringBuffer �������ַ���
     * @throws QMException 
     */
    private static StringBuffer exportRouteListAttr(TechnicsRouteListIfc tech, StringBuffer stringbuffer) throws QMException
    {
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        stringbuffer.append("RouteList,");
        stringbuffer.append(tech.getRouteListNumber() + ",");
        stringbuffer.append(tech.getRouteListName() + ",");
        stringbuffer.append(tech.getRouteListDescription() + ",");
        stringbuffer.append(tech.getRouteListLevel() + ",");
        String depID = tech.getRouteListDepartment();
        exportCode(stringbuffer, depID);
        stringbuffer.append(",");
        String staID = tech.getRouteListState();
        exportCode(stringbuffer, staID);
        stringbuffer.append(",");
        QMPartMasterIfc partmaster = (QMPartMasterIfc)service.refreshInfo(tech.getProductMasterID());
        stringbuffer.append(partmaster.getPartNumber() + ",");
        stringbuffer.append(tech.getLocation() + ",");
        //��������������
        LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)service.refreshInfo(tech.getLifeCycleTemplate());
        stringbuffer.append(template.getLifeCycleName() + ",");
        stringbuffer.append(tech.getLifeCycleState().toString() + ",");
        //��Ŀ��
        String id = tech.getProjectId();
        if(id != null)
        {

            ProjectInfo project = (ProjectInfo)service.refreshInfo(id);
            stringbuffer.append(project.getName() + ",");
        }else
        {
            stringbuffer.append("null,");
            //���ϼ�
        }
        stringbuffer.append("FALSE");
        stringbuffer.append("\n");
        return stringbuffer;
    }

    /**
     * �������ɵ���Ϣд���ļ�
     * @param buf �����ɵ���Ϣ
     * @param filename �ļ���
     * @param flag �Ƿ�ȡ��ԭ�е��ļ����� @
     * @throws QMException 
     */
    public static void writeFile(StringBuffer buf, String filename, boolean flag) throws QMException
    {
        try
        {
            File file = new File(filename + ".csv");
            if((!file.exists()) || (!flag))
            {
                StringBuffer head = createHeadMessage();
                buf = head.append(buf);
            }
            FileWriter filewriter = new FileWriter(filename + ".csv", flag);
            if(TechnicsRouteServiceEJB.VERBOSE)
            {
                System.out.println("Writing data to the " + filename + ".csv" + " file");
            }
            filewriter.write(buf.toString());
            filewriter.flush();
            filewriter.close();
        }catch(Exception e)
        {
            throw new QMException(e);
        }
    }

    /**
     * ����ͷ��Ϣ
     * @return StringBuffer �����õ�ͷ��Ϣ @
     * @throws QMException 
     */
    protected static StringBuffer createHeadMessage() throws QMException
    {
        StringBuffer stringbuffer = new StringBuffer();
        try
        {
            SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
            //����ͷ��Ϣ
            String s1 = sservice.getCurUserInfo().getUsersName();
            stringbuffer.append("#--This file was generated from QM " + "\n");
            stringbuffer.append("#--By  " + s1 + "\n");
            stringbuffer.append("#--On  " + (new Date()).toGMTString() + "\n");
        }catch(Exception e)
        {
            throw new QMException(e);
        }
        return stringbuffer;
    }
    
    //CCBegin SS6
    /**
     * ������ݹ���·�߱�
     * @param hashtable ���Ա� ���У���š����ơ����ڲ�Ʒ�����ϼС��������ڲ���Ϊ�ա�
     * @param vector Ҫ�������־��Ϣ
     * @return boolean ���������Ƿ���ȷ�� @
     * @throws ServiceLocatorException 
     * @throws LoadDataException 
     */
    public static boolean saveZCRouteList(Hashtable hashtable, Vector vector) throws QMException
    {

        /*
         * bigversion1==0���������ݿ����е����ݣ� bigversion1==1 ����С�汾 bigversion1==2 �޶�
         */
        Integer bigversion = (Integer)hashtable.get("bigversion");
        int bigversion1 = bigversion.intValue();

        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListImportExportHandler.saveRouteList attributes = " + hashtable);
        }
        //  try {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.
        //   getService("TechnicsRouteService");
        TechnicsRouteListInfo listInfo = new TechnicsRouteListInfo();
        //���
        listInfo.setRouteListNumber(LoadHelper.getValue("routeListNumber", hashtable, LoadHelper.REQUIRED));
        //����
        listInfo.setRouteListName(LoadHelper.getValue("routeListName", hashtable, LoadHelper.REQUIRED));
        //˵��
        //CCBegin SS7
        //System.out.println("·��˵������------------------------------");
        String zc = "���ݽ�Ź�˾�滮��չ��һ��·���ļ���        �����ƴ˶���·�ߡ�\r\n���ݣ�PDM��������˵����     ������׼��������׼����\r\n˵����                   \r\n·�ߴ��룺\r\n������λ���滮��չ���������ƻ������ɹ�����������֤�������첿������";
        //System.out.println("·��˵������------------------------------"+zc);
        //CCEnd SS7
        listInfo.setRouteListDescription(zc);
        String routeListLevel = LoadHelper.getValue("routeListLevel", hashtable, LoadHelper.REQUIRED);
        //����
        listInfo.setRouteListLevel(routeListLevel);
        String routeListDepartment = LoadHelper.getValue("routeListDepartment", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListDepartmentParent = LoadHelper.getValue("routeListDepartmentParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListDepartment != null && !routeListDepartment.trim().equals(""))
        {

            //��λ
            listInfo.setRouteListDepartment(ResourceExportImportHandler.importCode(routeListDepartment, routeListDepartmentParent).getBsoID());
        }
        String routeListState = LoadHelper.getValue("routeListState", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListStateParent = LoadHelper.getValue("routeListStateParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListState != null && !routeListState.trim().equals(""))
        {

            //״̬
            listInfo.setRouteListState(ResourceExportImportHandler.importCode(routeListState, routeListStateParent).getCodeContent());

            //��Ʒ
        }
      //CCBegin SS4
        String productNumber = LoadHelper.getValue("productNumber", hashtable, LoadHelper.NOT_REQUIRED);
        if(productNumber != null && !productNumber.trim().equals(""))
        	listInfo.setProductMasterID(findPartMaster(productNumber).getBsoID());
      //CCEnd SS4
        //λ��
        String location = LoadHelper.getValue("location", hashtable, LoadHelper.REQUIRED);
        FolderService folderService = (FolderService)EJBServiceHelper.getService("FolderService");
        //��ҵ�������ŵ����ϼ���
        listInfo = (TechnicsRouteListInfo)folderService.assignFolder(listInfo, location);
        //��������ģ��
        String lifecycleTemplate = (LoadHelper.getValue("lifecycleTemplate", hashtable, LoadHelper.REQUIRED));
        LifeCycleService lservice = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
        listInfo = (TechnicsRouteListInfo)lservice.setLifeCycle(listInfo, lservice.getLifeCycleTemplate(lifecycleTemplate));
        //��������״̬
        String stateStr = LoadHelper.getValue("lifecycleState", hashtable, LoadHelper.NOT_REQUIRED); //����
        if(stateStr != null && !stateStr.equals(""))
        {
            stateStr = RouteHelper.getValue(LifeCycleState.getLifeCycleStateSet(), stateStr); //Ӣ��
            LifeCycleState state = LifeCycleState.toLifeCycleState(stateStr);
            listInfo.setLifeCycleState(state);
        }
        //��Ŀ
        String projectName = LoadHelper.getValue("projectName", hashtable, LoadHelper.NOT_REQUIRED);
        if(projectName != null && !projectName.equals(""))
        {
            ProjectService projectService = (ProjectService)EJBServiceHelper.getService("ProjectService");
            ProjectIfc projectInfo = projectService.getProject(projectName);
            if(projectInfo != null)
            {
                listInfo.setProjectId(projectInfo.getBsoID());
            }
        }
        //�Ƿ��а汾.����а汾�������޶������û�汾�����½���
        String isHaveVersion = LoadHelper.getValue("isHasVersion", hashtable, LoadHelper.NOT_REQUIRED);

        //add by guoxl                                         
        TechnicsRouteService routeListService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        TechnicsRouteListInfo oldRoutListInfo = routeListService.findRouteListByNum(listInfo.getRouteListNumber());

        if(bigversion1 == 0)
        {//������

            if(oldRoutListInfo == null)
            {

                //�����룬���ݿ���û�д����ݣ��򴴽�

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //�����룬���ݿ����д����ݣ�������������Ѿ����ڡ���Ϣ
                vector.add("�����Ѿ����ڣ�");

            }

        }else if(bigversion1 == 1)
        {//С�汾����

            if(oldRoutListInfo == null)
            {

                //����С�汾�����ݿ���û�д����ݣ��򴴽�

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //����С�汾,�����ݣ�������С�汾
                listInfo = (TechnicsRouteListInfo)checkInRouteList(listInfo);

            }

        }else if(bigversion1 == 2)
        {//�޶�
            if(oldRoutListInfo == null)
            {

                //�޶������ݿ�û�д˼�¼���򴴽�
                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //�޶�,���������޶�

                listInfo = (TechnicsRouteListInfo)reviseRouteList(listInfo);

            }

        }
        LoadHelper.setCacheValue(CACHE_ROUTELIST, listInfo);
        return true;
        //add by guoxl

        //      //�޶�
        //      if (isHaveVersion != null && !isHaveVersion.equals("") &&
        //          Boolean.getBoolean(isHaveVersion)) {
        //        listInfo = (TechnicsRouteListInfo) reviseRouteList(listInfo);
        //        if (TechnicsRouteServiceEJB.VERBOSE) {
        //          System.out.println("�޶������Ĺ���·�߱�" + listInfo);
        //          if (listInfo != null) {
        //            System.out.println("�޶������Ĺ���·�߱�" + listInfo.getBsoID());
        //          }
        //        }
        //      }
        //      else {//���޶�
        //      //  System.out.println("����ǰ�Ĺ���·�߱�" + listInfo.getBsoID());
        //        listInfo = (TechnicsRouteListInfo) pservice.saveValueInfo(listInfo);
        //        //  listInfo =  (TechnicsRouteListInfo)routeService.storeRouteList(listInfo);
        //        if (TechnicsRouteServiceEJB.VERBOSE) {
        //          System.out.println("�����Ĺ���·�߱�" + listInfo);
        //          if (listInfo != null) {
        //            System.out.println("�����Ĺ���·�߱�" + listInfo.getBsoID());
        //          }
        //        }
        //      }
        //      LoadHelper.setCacheValue(CACHE_ROUTELIST, listInfo);
        //      vector.add(listInfo);
        //    }
        //    catch (Exception e) {
        //      e.printStackTrace();
        //      return false;
        //    }
        //    return true;
    }
    //CCEnd SS6
    
    //CCBegin SS10
    /**
     * �ı�Ự�Ĳ�����
     * 
     * @param principal
     *            ������
     * @throws QMException
     */
    public static void changePrincipal(String principal) throws QMException {
        if (principal == null || principal.equals("")) {
            return;
        }
        SessionService sessionService = (SessionService) EJBServiceHelper
                .getService("SessionService");
        sessionService.freeAdministrator();
        sessionService.setCurUser(principal);
//        sessionService.setAdministrator();
    }
    //CCEnd SS10

}

