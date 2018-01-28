/** ���ɳ���RouteListImportExportHandler.java	1.1  2004/05/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 20131108 XUCY ԭ��һ��������ܵ������·�ߣ��μ�product����TD2606
 * SS1 ֧�ֵ��벻ͬ����״̬���㲿�����㲿���б��У���������״̬�С� liunan 2014-6-11
 * SS2 װ��·��Ϊ��ʱ��������� liunan 2014-6-11
 * SS3 ������ʷ·���������·�߷ָ��� liunan 2014-6-12
 * SS4 ��׼�����������Ӵ����� liunan 2015-3-30
 * SS5 A004-2017-3484 ����·���޷���ʾ����ΪY����ֵ̫���޸�Y����ֵ���� liunan 2017-3-29
 */
package com.faw_qm.technics.route.ejb.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capproute.graph.RouteItem;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
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
import com.faw_qm.resource.exception.ResourceException;
import com.faw_qm.resource.util.ResourceExportImportHandler;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.route.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.RouteNodeLinkIfc;
import com.faw_qm.technics.route.model.RouteNodeLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterInfo;
import com.faw_qm.technics.route.util.RouteCategoryType;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title:�������ļ����뵼������·�߱� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author ������ ����
 * @version 1.0
 */

public class RouteListImportExportHandler {
  private static final String CACHE_ROUTELIST = "routeList";
  private static final String CACHE_LISTLINK = "listLink";
  private static final String CACHE_ROUTE_MAP = "routeMap";
  private static final String CACHE_ROUTE = "route";
  private final static String RESOURCE =
      "com.faw_qm.technics.route.util.RouteResource";

  /**���ÿ��·���м�����֧*/
  private static int count = 0;
  private static RouteListLevelType[] levelTypes;
  
  private static Vector partLinkVec = new Vector();
  public RouteListImportExportHandler() {
  }

  /**
   * ���湤��·�߱�
   * @param hashtable ���Ա�  ���У���š����ơ����ڲ�Ʒ�����ϼС��������ڲ���Ϊ�ա�
   * @param vector Ҫ�������־��Ϣ
   * @return boolean ���������Ƿ���ȷ��
   * @throws QMException 
   */
  public static boolean saveRouteList(Hashtable hashtable, Vector vector) throws QMException {
    
	  /*bigversion1==0���������ݿ����е����ݣ�
	   * bigversion1==1 ����С�汾
	   * bigversion1==2 �޶�
	   */
	  Integer bigversion=(Integer)hashtable.get("bigversion");
	  int bigversion1=bigversion.intValue();
		  
	  
	  if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println(
          "enter RouteListImportExportHandler.saveRouteList attributes = " +
          hashtable);
    }
  //  try {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      //TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.
      //   getService("TechnicsRouteService");
      TechnicsRouteListInfo listInfo = new TechnicsRouteListInfo();
      //���
      listInfo.setRouteListNumber(LoadHelper.getValue("routeListNumber",
          hashtable, LoadHelper.REQUIRED));
      //����
      listInfo.setRouteListName(LoadHelper.getValue("routeListName", hashtable,
          LoadHelper.REQUIRED));
      //˵��
      listInfo.setRouteListDescription(LoadHelper.getValue(
          "routeListDescription", hashtable, LoadHelper.NOT_REQUIRED));
      String routeListLevel = LoadHelper.getValue("routeListLevel", hashtable,
                                                  LoadHelper.REQUIRED);
      //����
      listInfo.setRouteListLevel(routeListLevel);
      String routeListDepartment = LoadHelper.getValue("routeListDepartment",
          hashtable, LoadHelper.NOT_REQUIRED);
      String routeListDepartmentParent = LoadHelper.getValue(
          "routeListDepartmentParent", hashtable, LoadHelper.NOT_REQUIRED);
      if (routeListDepartment != null && !routeListDepartment.trim().equals("")) {

        //��λ
        listInfo.setRouteListDepartment(ResourceExportImportHandler.importCode(
            routeListDepartment, routeListDepartmentParent).getBsoID());
      }
      String routeListState = LoadHelper.getValue("routeListState", hashtable,
                                                  LoadHelper.NOT_REQUIRED);
      String routeListStateParent = LoadHelper.getValue("routeListStateParent",
          hashtable, LoadHelper.NOT_REQUIRED);
      if (routeListState != null && !routeListState.trim().equals("")) {

        //״̬
        listInfo.setRouteListState(ResourceExportImportHandler.importCode(
            routeListState, routeListStateParent).getCodeContent());
       
        //��Ʒ
      }
      String productNumber = LoadHelper.getValue("productNumber", hashtable,
                                                 LoadHelper.REQUIRED);
      listInfo.setProductMasterID(findPartMaster(productNumber).getBsoID());
      //λ��
      String location = LoadHelper.getValue("location", hashtable,
                                            LoadHelper.REQUIRED);
      FolderService folderService = (FolderService) EJBServiceHelper.getService(
          "FolderService");
      //��ҵ�������ŵ����ϼ���
      listInfo = (TechnicsRouteListInfo) folderService.assignFolder(listInfo,
          location);
      //��������ģ��
      String lifecycleTemplate = (LoadHelper.getValue("lifecycleTemplate",
          hashtable, LoadHelper.REQUIRED));
      LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
          getService("LifeCycleService");
      listInfo = (TechnicsRouteListInfo) lservice.setLifeCycle(listInfo,
          lservice.getLifeCycleTemplate(lifecycleTemplate));
      //��������״̬
      String stateStr = LoadHelper.getValue("lifecycleState", hashtable,
                                            LoadHelper.NOT_REQUIRED); //����
      if (stateStr != null && !stateStr.equals("")) {
        stateStr = RouteHelper.getValue(LifeCycleState.getLifeCycleStateSet(), stateStr); //Ӣ��
        LifeCycleState state = LifeCycleState.toLifeCycleState(stateStr);
        listInfo.setLifeCycleState(state);
      }
      
      //CCBegin SS4
      String userName = LoadHelper.getValue("user", hashtable, 1);
      //CCEnd SS4
      
      //��Ŀ
      String projectName = LoadHelper.getValue("projectName", hashtable,
                                               LoadHelper.NOT_REQUIRED);
      if (projectName != null && !projectName.equals("")) {
        ProjectService projectService = (ProjectService) EJBServiceHelper.
            getService("ProjectService");
        ProjectIfc projectInfo = projectService.getProject(projectName);
        if (projectInfo != null) {
          listInfo.setProjectId(projectInfo.getBsoID());
        }
      }
      //�Ƿ��а汾.����а汾�������޶������û�汾�����½���
      String isHaveVersion = LoadHelper.getValue("isHasVersion",
    		  hashtable, LoadHelper.NOT_REQUIRED);
     
    //add by guoxl                                         
      TechnicsRouteService routeListService = (TechnicsRouteService) EJBServiceHelper.
      getService("TechnicsRouteService");
     
      TechnicsRouteListInfo oldRoutListInfo= routeListService.findRouteListByNum(
    		  listInfo.getRouteListNumber());
     
      //CCBegin SS4
      System.out.println(userName);
      SessionService sessionService = (SessionService) EJBServiceHelper.getService("SessionService");
      sessionService.setCurUser(userName);
      //CCEnd SS4
      System.out.println(sessionService.getCurUser().getUsersName());
      
      if(bigversion1==0) {//������
    	  
    	 if(oldRoutListInfo==null) {
    		 
    		//�����룬���ݿ���û�д����ݣ��򴴽�
    		 listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);
   
    		 
    	 }else{
    		 
           //�����룬���ݿ����д����ݣ�������������Ѿ����ڡ���Ϣ
    		 vector.add("�����Ѿ����ڣ�");
    		 
    		 
    	 }
    	 
    	 
      }else if(bigversion1==1) {//С�汾����
    	  
    	  if(oldRoutListInfo==null) {
    		  
    		  //����С�汾�����ݿ���û�д����ݣ��򴴽�
     		 
    		  listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);
     		 

             }else {
    			  
    			 //����С�汾,�����ݣ�������С�汾
    			  listInfo = (TechnicsRouteListInfo) checkInRouteList(listInfo);

    	  }
    	  
      }else if(bigversion1==2){//�޶�
    	  if(oldRoutListInfo==null) {
    		  
    		  //�޶������ݿ�û�д˼�¼���򴴽�
    		  listInfo = (TechnicsRouteListInfo) pservice.saveValueInfo(listInfo);
    	  
    	  }else {
    		 
    			  //�޶�,���������޶�
    			  
    			  listInfo = (TechnicsRouteListInfo) reviseRouteList(listInfo);

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
   * @param  routeInfo �⵼���δ�־û���·�߱�
   * @return TechnicsRouteListInfo С�汾�������ֵ����
   * @author ������
   */
  private static TechnicsRouteListInfo  checkInRouteList(TechnicsRouteListInfo routeInfo)
  throws QMException {
	  //�����⵼���·�߱���ļ�������Ǹ������ϼ�����������
	  if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) routeInfo)) {
	          
	        		  String routeListNum= routeInfo.getRouteListNumber();
	               throw new TechnicsRouteException("�⵼���·�߱�("+routeListNum+")�Ĵ��λ�ò�Ӧ�ڸ������ϼУ�");
	  }
	  PersistService pservice = (PersistService) EJBServiceHelper.
      getPersistService();
	  
      QMQuery query = new QMQuery("TechnicsRouteListMaster");
      QueryCondition condition1 = new QueryCondition("routeListNumber", "=",
		  routeInfo.getRouteListNumber());
      query.addCondition(condition1);
      
      Collection c = pservice.findValueInfo(query);
      
      TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo) c.
      toArray()[0];
      
      if (TechnicsRouteServiceEJB.VERBOSE) {
                   System.out.println("find master is :" + masterinfo);
      }
      
      TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.
      getService("TechnicsRouteService");
      
     //������е�����С�汾
     TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo) routeService.
      getLatestVesion(masterinfo);
     
     //���������С�汾��������
     //����ö����ڸ������ϼл��ѱ����������������
     if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) oldInfo) ||
           CheckInOutCappTaskLogic.isCheckedOut( (WorkableIfc) oldInfo)) {
           Object[] obj = {
                  oldInfo.getRouteListNumber()};
               throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "11", obj));
  }
	  //��ð汾����
        VersionControlService vcService = (VersionControlService) EJBServiceHelper.
         getService("VersionControlService");
        //����С�汾
        TechnicsRouteListInfo newVersionInfo = (TechnicsRouteListInfo) vcService.
            newIteration( (VersionedIfc) oldInfo);
        
         newVersionInfo=(TechnicsRouteListInfo)vcService.supersede(oldInfo,newVersionInfo);

            //������������ģ��
            newVersionInfo.setLifeCycleTemplate(routeInfo.getLifeCycleTemplate());
            //������������״̬
            newVersionInfo.setLifeCycleState(routeInfo.getLifeCycleState());
            //���ù�����
            newVersionInfo.setProjectId(routeInfo.getProjectId());
            newVersionInfo=(TechnicsRouteListInfo)pservice.saveValueInfo(newVersionInfo);
            
	  return newVersionInfo;
  }

  /**
   * ����·�߱������Ĺ�����
   * @param hashtable ���Ա�
   * @param vector Ҫ�������־��Ϣ
   * @return ���������Ƿ���ȷ
   */
  public static boolean saveListRoutePartLink(Hashtable hashtable,
                                              Vector vector) {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println(
          "enter RouteListImportExportHandler.saveListRoutePartLink attributes = " +
          hashtable);
    }
    try {
    	//���������ʼ
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      String partNumber = LoadHelper.getValue("partNumber", hashtable,
                                              LoadHelper.REQUIRED);
      String parentPartNum = LoadHelper.getValue("parentPartNum", hashtable,
                                                 LoadHelper.REQUIRED);
      String count = LoadHelper.getValue("count", hashtable,
                                         LoadHelper.REQUIRED);
      //CCBegin SS1
      String modefyIdenty = LoadHelper.getValue("modefyIdenty", hashtable,
                                         LoadHelper.REQUIRED);
      //CCEnd SS1
      //CCBegin by liunan 2011-04-13
      //String partMasterID = findPartMaster(partNumber).getBsoID();
      QMPartMasterIfc partMasterInfo = findPartMaster(partNumber);
      String partMasterID = partMasterInfo.getBsoID();
      //CCEnd by liunan 2011-04-13
      TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)
          LoadHelper.getCacheValue(CACHE_ROUTELIST);
      TechnicsRouteService routeService = (TechnicsRouteService)
          EJBServiceHelper.getService("TechnicsRouteService");
    if (TechnicsRouteServiceEJB.VERBOSE) {
        System.out.println("routeService = " + routeService);
        System.out.println("routeList = " + routeList);
        System.out.println("partMasterID = " + partMasterID);
     }
      //�����·�߱����㲿���Ĺ����Ѵ��ڣ������½��ù�����
     //CCBegin SS1
     // if (routeService.getListRoutePartLink(routeList.getBsoID(), partMasterID,
     //                                       parentPartNum) == null) {
     //CCEnd SS1
    	 //û�й������½�����
        ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.
            newListRoutePartLinkInfo(routeList, partMasterID);
        if (parentPartNum != null && !parentPartNum.trim().equals("")) {
          QMPartMasterIfc parent = findPartMaster(parentPartNum);
          QMPartIfc part = routeService.filteredIterationsOfByDefault(parent);
          //CCBegin by liunan 2011-04-11 
          QMPartIfc partsub = routeService.filteredIterationsOfByDefault(partMasterInfo);
          //CCEnd by liunan 2011-04-11
          listLinkInfo.setParentPartNum(parentPartNum);
          listLinkInfo.setParentPartName(parent.getPartNumber());
          listLinkInfo.setParentPartID(part.getBsoID());
          //CCBegin by liunan 2011-04-11 
          listLinkInfo.setPartMasterInfo(partMasterInfo);
          listLinkInfo.setPartBranchID(partsub.getBsoID());
          //CCEnd by liunan 2011-04-11
        }
        if (count != null && !count.trim().equals("")) {
          listLinkInfo.setCount(new Integer(count).intValue());
        }
        //�������
        pservice.saveValueInfo(listLinkInfo);
        //����ù���
        //CCBegin SS1
        //System.out.println("saveListRoutePartLink  ==="+partNumber + parentPartNum+modefyIdenty+"===="+listLinkInfo);
        //LoadHelper.setCacheValue(partNumber + parentPartNum, listLinkInfo);
        LoadHelper.setCacheValue(partNumber + parentPartNum + modefyIdenty, listLinkInfo);
        //CCEnd SS1
        vector.add(listLinkInfo);
        partLinkVec.add(listLinkInfo);
      //CCBegin SS1
      //}
      //CCEnd SS1
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private static RouteItem setRouteItem(BaseValueIfc info) {
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
  public static boolean saveRouteBranch(Hashtable hashtable, Vector vector) {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println(
          "enter RouteListImportExportHandler.saveRouteBranch attributes = " +
          hashtable);
    }
    try {
      count = count + 1;
      TechnicsRouteIfc routeInfo = null;
      HashMap routeMap = null;
      if (LoadHelper.getCacheValue(CACHE_ROUTE_MAP) == null) {
        String description = LoadHelper.getValue("routeDescription", hashtable,
                                                 LoadHelper.NOT_REQUIRED);
        routeInfo = new TechnicsRouteInfo();
        routeInfo.setRouteDescription(description);
        //CCBegin by liunan 2011-04-13 ��Ҫ���ø��ı�ʶ�� modefyIdenty
        String modefyIdenty = LoadHelper.getValue("modefyIdenty", hashtable,
                                                 LoadHelper.NOT_REQUIRED);
        routeInfo.setModefyIdenty(getCodingIfc(modefyIdenty));
        //CCEnd by liunan 2011-04-13
        RouteItem routeItem = setRouteItem(routeInfo);
        routeMap = new HashMap();
        routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME, routeItem);
        //����·��ӳ���
        LoadHelper.setCacheValue(CACHE_ROUTE_MAP, routeMap);
      }
      else {
        routeMap = (HashMap) LoadHelper.getCacheValue(CACHE_ROUTE_MAP);
        RouteItem routeItem = (RouteItem) routeMap.get(TechnicsRouteServiceEJB.
            TECHNICSROUTE_BSONAME);
        routeInfo = (TechnicsRouteIfc) routeItem.getObject();
      }

      /*String isMainRoute = LoadHelper.getValue("isMainRoute", hashtable,
                                               LoadHelper.NOT_REQUIRED);
      TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
      if (isMainRoute != null && isMainRoute.equalsIgnoreCase("true")) {
        routeBranchInfo.setMainRoute(true);
      }
      else {
        routeBranchInfo.setMainRoute(false);

      }
      routeBranchInfo.setRouteInfo(routeInfo);
      //��÷�֧���ϡ�
      Collection branchs = (Collection) routeMap.get(TechnicsRouteServiceEJB.
          TECHNICSROUTEBRANCH_BSONAME);
      //���÷�֧
      if (branchs == null) {
        branchs = new Vector();
        routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME,
                     branchs);
      }
      RouteItem branchItem = setRouteItem(routeBranchInfo);
      branchs.add(branchItem);

      //���ýڵ㡣
      Collection nodes = (Collection) routeMap.get(TechnicsRouteServiceEJB.
          ROUTENODE_BSONAME);
      if (nodes == null) {
        nodes = new Vector();
        routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_BSONAME, nodes);
      }

      Vector tempNodeVector = new Vector();
      int y = 60 * count;
      int x = 0;
      //��������·�߽ڵ㡣
      String manuString = LoadHelper.getValue("manuRoute", hashtable,
                                              LoadHelper.NOT_REQUIRED);
      //CCBegin by liunan 2011-04-13 �򲹶�
      String routeBranchStr=""; //TD 2338
      //CCEnd by liunan 2011-04-13
      if (manuString != null) {
      	//CCBegin by liunan 2011-04-13 �򲹶�
      	routeBranchStr=manuString.replaceAll("--","��"); //TD 2338 Begin
      	//CCEnd by liunan 2011-04-13
        Collection nodeString = new Vector();
        StringTokenizer token = new StringTokenizer(manuString, "--");
        while (token.hasMoreTokens()) {
          nodeString.add(token.nextToken());
        }
        for (Iterator manuIterator = nodeString.iterator();
             manuIterator.hasNext(); ) {
          String manu = (String) manuIterator.next();
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
      }
      //CCBegin by liunan 2011-04-13 �򲹶�
      else{ //TD 2338 
    	  routeBranchStr="��";    	  
      }
      //CCEnd by liunan 2011-04-13
      //����װ��·�߽ڵ㡣
      String assemString = LoadHelper.getValue("assemRoute", hashtable,
                                               LoadHelper.NOT_REQUIRED);
      if (assemString != null) {
      	//CCBegin by liunan 2011-04-13 �򲹶�
      	routeBranchStr=routeBranchStr+"@"+assemString; //TD 2338 
      	//CCEnd by liunan 2011-04-13
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
      }
      //CCBegin by liunan 2011-04-13 �򲹶�
      else{ //TD 2338 Begin
    	  routeBranchStr=routeBranchStr+"@��";
      }
      //TD 2338 Begin
      if (routeBranchStr != null && !routeBranchStr.equals("")&&!routeBranchStr.equals("��@��")) {
				routeBranchInfo.setRouteStr(routeBranchStr);
				branchItem = setRouteItem(routeBranchInfo);
				branchs.add(branchItem);
			}
      //TD 2338 end
      //CCEnd by liunan 2011-04-13
      //���ýڵ����
      Collection nodeLinks = (Collection) routeMap.get(TechnicsRouteServiceEJB.
          ROUTENODE_LINKBSONAME);
      if (nodeLinks == null) {
        nodeLinks = new Vector();
        routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME, nodeLinks);
      }
      RouteNodeIfc temp = null;
      for (Iterator nodeIter = tempNodeVector.iterator(); nodeIter.hasNext(); ) {
        RouteItem destinationItem = (RouteItem) nodeIter.next();
        RouteNodeIfc destinationInfo = (RouteNodeIfc) destinationItem.getObject();
        if (temp != null) {
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
      Collection branchNodeLinks = (Collection) routeMap.get(
          TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
      if (branchNodeLinks == null) {
        branchNodeLinks = new Vector();
        routeMap.put(TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME,
                     branchNodeLinks);
      }
      for (Iterator nodeIter = tempNodeVector.iterator(); nodeIter.hasNext(); ) {
        RouteItem nodeInfo1Item = (RouteItem) nodeIter.next();
        RouteNodeIfc nodeInfo1 = (RouteNodeIfc) nodeInfo1Item.getObject();
        RouteBranchNodeLinkIfc branchNodeLinkInfo = new RouteBranchNodeLinkInfo();
        branchNodeLinkInfo.setRouteBranchInfo(routeBranchInfo);
        branchNodeLinkInfo.setRouteNodeInfo(nodeInfo1);
        RouteItem branchNodeLinkItem = setRouteItem(branchNodeLinkInfo);
        branchNodeLinks.add(branchNodeLinkItem);
      }*/
      //vector.add(listLinkInfo);
      
      
          //begin CR1
          //���·�߷�֧��Ϣ�������Ƕ����֧������·�߷�֧֮����;�ָ���
          //����·�ߺ�װ��·����=�ָ���
          //����·��֮����--�ָ���
          //�Ƿ���Ҫ·����@�ָ�
          String routeBranch = LoadHelper.getValue("routeBranch", hashtable, LoadHelper.NOT_REQUIRED);
          int tokenCount = 0;
          StringTokenizer branchToken = null;
          //�����Զ�������
          if(routeBranch != null && !routeBranch.equals(""))
          {
              branchToken = new StringTokenizer(routeBranch, ";");
              tokenCount = branchToken.countTokens();
              for(int j = 0;j < tokenCount;j++)
              {
                  //ѭ�����ÿһ��·�߷�֧
                  String branch = branchToken.nextToken();
                  
                  String isMainRoute = branch.split("@")[0];
                  //·�ߴ�
                  String routeStr = branch.split("@")[1];
                  
                  //CCBegin SS3
                  String routeBranchStr = routeStr.replaceAll("-","��");
                  routeBranchStr = routeBranchStr.replaceAll("=","@");
                  if(routeBranchStr.endsWith("@"))
                  {
                  	routeBranchStr = routeBranchStr + "��";
                  }
                  if(routeBranchStr.startsWith("@"))
                  {
                  	routeBranchStr = "��" + routeBranchStr;
                  }
                  //CCEnd SS3
                  
                  TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
                  if(isMainRoute != null && isMainRoute.equalsIgnoreCase("true"))
                  {
                      routeBranchInfo.setMainRoute(true);
                  }else
                  {
                      routeBranchInfo.setMainRoute(false);

                  }
                  
                  //CCBegin SS3
                  routeBranchInfo.setRouteStr(routeBranchStr);
                  //CCEnd SS3
                  
                  routeBranchInfo.setRouteInfo(routeInfo);
                  //��÷�֧���ϡ�
                  Collection branchs = (Collection)routeMap.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
                  //���÷�֧
                  if(branchs == null)
                  {
                      branchs = new Vector();
                      routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME, branchs);
                  }
                  RouteItem branchItem = setRouteItem(routeBranchInfo);
                  branchs.add(branchItem);

                  //���ýڵ㡣
                  Collection nodes = (Collection)routeMap.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
                  if(nodes == null)
                  {
                      nodes = new Vector();
                      routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_BSONAME, nodes);
                  }

                  Vector tempNodeVector = new Vector();
                  //CCBegin SS5
                  //int y = 60 * count;
                   int y = 200 + count;
                   //CCEnd SS5
                  int x = 0;
                  //����·��
                  String manuString = routeStr.split("=")[0];
                  if(manuString != null)
                  {
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
                  }
                  //װ��·��
                  //CCBegin SS2
                  //String assemString = routeStr.split("=")[1];
                  String tempr[] = routeStr.split("=");
                  String assemString = null;
                  if(tempr.length>1)
                  {
                  	assemString = tempr[1];
                  }
                  //CCEnd SS2
                  if(assemString != null)
                  {
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
                  }
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
              }
          }
          //end CR1
          
      String partNumber = LoadHelper.getValue("routePartNumber", hashtable,
                                              LoadHelper.REQUIRED);
      String parentPartNum = LoadHelper.getValue("parentPartNum", hashtable,
                                                 LoadHelper.REQUIRED);
      String routeEnd = LoadHelper.getValue("routeEnd", hashtable,
                                            LoadHelper.NOT_REQUIRED);
      //CCBegin SS1
      String modefyIdenty = LoadHelper.getValue("modefyIdenty", hashtable,
                                         LoadHelper.REQUIRED);
      //CCEnd SS1
      if (routeEnd != null && routeEnd.equalsIgnoreCase("routeEnd")) {
        try {
          //CCBegin SS1
          saveRoute(hashtable, partNumber, parentPartNum, modefyIdenty);
          LoadHelper.removeCacheValue(partNumber + parentPartNum + modefyIdenty);
          //CCEnd SS1
          LoadHelper.removeCacheValue(CACHE_ROUTE_MAP);
          LoadHelper.removeCacheValue(CACHE_ROUTELIST);
          count = 0;

        }
        catch (Exception e) {
          //CCBegin SS1
          LoadHelper.removeCacheValue(partNumber + parentPartNum + modefyIdenty);
          //CCEnd SS1
          LoadHelper.removeCacheValue(CACHE_ROUTE_MAP);
          LoadHelper.removeCacheValue(CACHE_ROUTELIST);
          count = 0;

          e.printStackTrace();
          return false;
        }

      }

    }
    catch (Exception e) {

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
  private static String getDepartmentID(String departmentName) throws
      QMException {
    CodingManageService service = (CodingManageService) EJBServiceHelper.
        getService("CodingManageService");

    String str = service.getIDbySort(departmentName);
    System.out.println(departmentName+"================"+str);
    return str;
  }

  /**
   * ����·��
   * @param hashtable
   * @param partNumber
   * @throws QMException
   */
  private static void saveRoute(Hashtable hashtable, String partNumber,
                                //CCBegin SS1
                                //String parentPartNum) throws QMException {
                                String parentPartNum, String modefyIdenty) throws QMException {
                                //CCEnd SS1
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println(
          "enter RouteListImportExportHandler.saveRoute attributes = " +
          hashtable);

    }
    HashMap routeMap = (HashMap) LoadHelper.getCacheValue(CACHE_ROUTE_MAP);
    //System.out.println("saveRoute  ==="+partNumber + parentPartNum + modefyIdenty);
    ListRoutePartLinkInfo listLinkInfo = (ListRoutePartLinkInfo) LoadHelper.
        //CCBegin SS1
        //getCacheValue(partNumber + parentPartNum);
        getCacheValue(partNumber + parentPartNum + modefyIdenty);
        //CCEnd SS1
    if (listLinkInfo == null) {
      throw new QMRuntimeException("�����ļ������޷�Ϊ�㲿��" + partNumber + "����·�ߡ�");
    }

    RouteHelper.getRouteService().saveRoute(listLinkInfo, routeMap);

    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("exit RouteListImportExportHandler.saveRoute�� ����·�߳ɹ���");

    }
  }

  /**
   * �����㲿����Ż���㲿��ֵ����
   * @param partMasterNumber �㲿�����
   * @return �㲿��ֵ����
   * @throws QMException
   */
  private static QMPartMasterInfo findPartMaster(String partMasterNumber) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery("QMPartMaster");
    QueryCondition condition1 = new QueryCondition("partNumber", "=",
        partMasterNumber);
    query.addCondition(condition1);
    Collection c = pservice.findValueInfo(query);
    System.out.println("partMasterNumber=="+partMasterNumber);
    System.out.println("c=="+c);
    QMPartMasterInfo obj = (QMPartMasterInfo) c.toArray()[0];
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("RouteListImportExportHandler.findPartMaster :" + obj);
    }
    return obj;
  }

  /**
   * ��ָ����·�߱�����޶�
   * @param originalInfo �⵼���δ�־û���·�߱�
   * @return �°汾��·�߱�
   * @throws QMException
   */
  private static TechnicsRouteListIfc reviseRouteList(TechnicsRouteListInfo
      originalInfo) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("����RouteListImportExportHandler.reviseRouteList:");
    }
  //�����⵼���·�߱���ļ�������Ǹ������ϼ��������޶�
	  if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) originalInfo)) {
	          
	        		   String routeListNum=originalInfo.getRouteListNumber();
	           
	               throw new TechnicsRouteException("�⵼���·�߱�("+routeListNum+")�Ĵ��λ�ò�Ӧ�ڸ������ϼУ�");
	  }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery("TechnicsRouteListMaster");
    QueryCondition condition1 = new QueryCondition("routeListNumber", "=",
        originalInfo.getRouteListNumber());
    query.addCondition(condition1);
    Collection c = pservice.findValueInfo(query);
    TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo) c.
        toArray()[0];
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("find master is :" + masterinfo);
    }
    TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.
        getService("TechnicsRouteService");
    //������е�����С�汾
    TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo) routeService.
        getLatestVesion(masterinfo);
    //���������С�汾�����޶�
    //����ö����ڸ������ϼл��ѱ�������������޶�
    if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) oldInfo) ||
        CheckInOutCappTaskLogic.isCheckedOut( (WorkableIfc) oldInfo)) {
      Object[] obj = {
          oldInfo.getRouteListNumber()};
      throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "10", obj));
    }
    //���ð汾���������°汾
    VersionControlService vcService = (VersionControlService) EJBServiceHelper.
        getService("VersionControlService");
    TechnicsRouteListIfc newVersionInfo = (TechnicsRouteListIfc) vcService.
        newVersion( (VersionedIfc) oldInfo);
    //������λ��
    FolderService folderService = (FolderService) EJBServiceHelper.getService(
        "FolderService");
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

  public static void exportRouteList(ArrayList technics, String filename,
                                     boolean flag) throws QMException,
      IOException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("CappExportImportHandler. exportTechnics "
                         +
                         " (ArrayList technics,String filename, boolean flag) begin technics=" +
                         technics +
                         " filename=" + filename + " flag=" + flag);
    }
    if (technics == null || filename == null) {
      return;
    }

    StringBuffer stringbuffer = new StringBuffer();
    for (int i = 0, n = technics.size(); i < n; i++) {
      TechnicsRouteListIfc tech = (TechnicsRouteListIfc) technics.get(i);
      // ����������������Ϣ
      stringbuffer = exportRouteListAttr(tech, stringbuffer);
      //�������㲿���Լ���������·��
      stringbuffer = exportPartByList(tech, stringbuffer);
    }
    writeFile(stringbuffer, filename, flag);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("CappExportImportHandler. exportTechnics end");
    }
  }

  private static StringBuffer exportPartByList(TechnicsRouteListIfc tech,
                                               StringBuffer stringbuffer) throws
      QMException {
    TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.
        getService("TechnicsRouteService");
    PersistService service = (PersistService)
        EJBServiceHelper.getService("PersistService");
    Collection links = routeService.getRouteListLinkParts(
        tech);
    if (links == null || links.size() == 0) {
      return stringbuffer;
    }
    for (Iterator it = links.iterator(); it.hasNext(); ) {
      ListRoutePartLinkIfc docMaster = (ListRoutePartLinkIfc) it.next();
      stringbuffer.append("PartMaster,");
      stringbuffer.append(docMaster.getPartMasterInfo().getPartNumber() + ",");
      stringbuffer.append(docMaster.getParentPartNum() + ",");
      stringbuffer.append(docMaster.getCount());
      stringbuffer.append("\n");
    }
    for (Iterator it = links.iterator(); it.hasNext(); ) {
      ListRoutePartLinkIfc docMaster = (ListRoutePartLinkIfc) it.next();
      if (docMaster.getRouteID() != null) {
        Collection branchs = getRouteBranchs(docMaster.getRouteID());
        TechnicsRouteIfc route = (TechnicsRouteIfc) service.refreshInfo(
            docMaster.getRouteID());
        Object[] objs = branchs.toArray();
        int size = objs.length;
        if (size == 0) {
          continue;
        }
        for (int i = 0; i < size - 1; i++) {
          TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc) objs[i];
          stringbuffer.append("RouteBranch,");
          stringbuffer.append(docMaster.getPartMasterInfo().getPartNumber() +
                              ",");
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
        TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc) objs[size - 1];
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
   * @J2EE_METHOD  --  getRouteBranchs
   * ���ݹ���·��ID��ù���·�߷�֦��ض���
   * @return HashMap key=����·�߷�ֵ֦����, value=·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
   */
  public static Collection getRouteBranchs(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(TechnicsRouteServiceEJB.
                                TECHNICSROUTEBRANCH_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    return coll;
  }

  private static void appendOnlyOneBranch(Object[] obj, StringBuffer result) {

    Collection manuColl = (Collection) obj[0];
    int k = 0;
    for (Iterator iter2 = manuColl.iterator(); iter2.hasNext(); ) {
      k++;
      RouteNodeIfc manuNode = (RouteNodeIfc) iter2.next();
      if (manuColl.size() == k) {
        result.append(manuNode.getNodeDepartmentName() + ",");
      }
      else {
        result.append(manuNode.getNodeDepartmentName() + "--");
      }
    }
    if (manuColl.size() == 0) {
      result.append(",");
    }
    RouteNodeIfc assemNode = (RouteNodeIfc) obj[1];
    if (assemNode != null) {
      result.append(assemNode.getNodeDepartmentName());
    }
    result.append(",");
  }

  private static StringBuffer exportCode(StringBuffer string, String codeID) throws
      QMException {
    PersistService service = (PersistService)
        EJBServiceHelper.getService("PersistService");
    BaseValueIfc code = service.refreshInfo(codeID);
    if (code != null) {
      if (code instanceof CodingIfc) {
        string.append( ( (CodingIfc) code).toString() + "," +
                      ( (CodingIfc) code).getCodingClassification());
      }
      else
      if (code instanceof CodingClassificationIfc) {
        string.append( ( (CodingClassificationIfc) code).toString() + "," +
                      ( (CodingClassificationIfc) code).getParent());
      }
    }
    else {
      string.append(",");
    }

    return string;
  }

  /**
   * ����·�߱�Ļ���������Ϣ
   * @param tech QMTechnicsIfc ·�߱�ֵ����
   * @param stringbuffer StringBuffer  �������ַ���
   * @return StringBuffer �������ַ���
   */
  private static StringBuffer exportRouteListAttr(TechnicsRouteListIfc tech,
                                                  StringBuffer stringbuffer) throws
      QMException {
    PersistService service = (PersistService)
        EJBServiceHelper.getService("PersistService");
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
    QMPartMasterIfc partmaster = (QMPartMasterIfc) service.refreshInfo(tech.
        getProductMasterID());
    stringbuffer.append(partmaster.getPartNumber() + ",");
    stringbuffer.append(tech.getLocation() + ",");
    //��������������
    LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)
        service.refreshInfo(tech.
                            getLifeCycleTemplate());
    stringbuffer.append(template.getLifeCycleName() + ",");
    stringbuffer.append(tech.getLifeCycleState().toString() + ",");
    //��Ŀ��
    String id = tech.getProjectId();
    if (id != null) {

      ProjectInfo project = (ProjectInfo) service.refreshInfo(id);
      stringbuffer.append(project.getName() + ",");
    }
    else {
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
   * @param flag �Ƿ�ȡ��ԭ�е��ļ�����
   * @throws QMException
   */
  public static void writeFile(StringBuffer buf, String filename, boolean flag) throws
      QMException {
    try {
      File file = new File(filename + ".csv");
      if ( (!file.exists()) || (!flag)) {
        StringBuffer head = createHeadMessage();
        buf = head.append(buf);
      }
      FileWriter filewriter = new FileWriter(filename + ".csv", flag);
      if (TechnicsRouteServiceEJB.VERBOSE) {
        System.out.println("Writing data to the " + filename + ".csv" +
                           " file");
      }
      filewriter.write(buf.toString());
      filewriter.flush();
      filewriter.close();
    }
    catch (IOException e) {
      throw new QMException(e);
    }
  }

  /**
   * ����ͷ��Ϣ
   * @return StringBuffer �����õ�ͷ��Ϣ
   * @throws QMException
   */
  protected static StringBuffer createHeadMessage() throws QMException {
    StringBuffer stringbuffer = new StringBuffer();
    try {
      SessionService sservice = (SessionService) EJBServiceHelper.
          getService("SessionService");
      //����ͷ��Ϣ
      String s1 = sservice.getCurUser().getUsersName();
      stringbuffer.append("#--This file was generated from QM " + "\n");
      stringbuffer.append("#--By  " + s1 + "\n");
      stringbuffer.append("#--On  " + (new Date()).toGMTString() + "\n");
    }
    catch (Exception e) {
      throw new QMException(e);
    }
    return stringbuffer;
  }
  
  /**
   * ����·�ߺ��㲿���Ĺ���������ɺ�����partindex���ϱ��湤��·�߱�
   * @param hashtable ���Ա�
   * @param vector Ҫ�������־��Ϣ��
   * @return boolean ���������Ƿ���ȷ��
   * @throws QMException 
   */
  public static boolean partMasterEnd(Hashtable hashtable, Vector vector) throws QMException 
  {
  	try
  	{
  		TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)LoadHelper.getCacheValue(CACHE_ROUTELIST);
  		//System.out.println("routeList======"+routeList);
  		//System.out.println("partLinkVec======"+partLinkVec);
  		if(partLinkVec.size()==0)
  		{
  			return true;
  		}
  		PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
  		TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
  		ListRoutePartLinkInfo listLinkInfo = null;
  		Vector vec = new Vector();
  		for(int i=0; i<partLinkVec.size(); i++)
  		{
  			listLinkInfo = (ListRoutePartLinkInfo)partLinkVec.elementAt(i);
  			String[] ids = new String[4];
	      ids[0] = listLinkInfo.getRightBsoID(); //���masterID
	      ids[1] = listLinkInfo.getParentPartNum(); //�������  ʵ���ǲ�Ʒ���
	      ids[2] = Integer.toString(listLinkInfo.getCount()); //����
	      ids[3] = listLinkInfo.getPartBranchID(); //���parid
	      //System.out.println(ids[0]+"==="+ids[1]+"==="+ids[2]+"==="+ids[3]);
	      vec.add(ids);
  		}
  		routeList.setPartIndex(vec);
  		
  		routeList=(TechnicsRouteListInfo)pservice.saveValueInfo(routeList);
  		
  		partLinkVec=new Vector();
    }
    catch (Exception e) 
    {
      e.printStackTrace();
      partLinkVec=new Vector();
      return false;
    }
    return true;
  }
    /**
   * ���ݵ�λ��ƻ�õ�λbsoID
   * @param departmentName ���
   * @return ��λbsoID
   */
  private static CodingIfc getCodingIfc(String codestr) throws QMException 
  {
  	CodingIfc codeifc = null;
  	try
  	{
  		String codeid = getDepartmentID(codestr);
  		PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
  		codeifc = (CodingIfc)pservice.refreshInfo(codeid);
  	}
    catch (Exception e) 
    {
      e.printStackTrace();
      return codeifc;
    }
  	return codeifc;
  }
  

}
