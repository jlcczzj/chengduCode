/** 生成程序RouteListImportExportHandler.java	1.1  2004/05/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 20131108 XUCY 原因：一个零件不能导入多条路线，参见product域下TD2606
 * SS1 支持导入不同更改状态的零部件，零部件列表中，新增更改状态列。 liunan 2014-6-11
 * SS2 装配路线为空时，导入出错。 liunan 2014-6-11
 * SS3 处理历史路线中特殊的路线分隔符 liunan 2014-6-12
 * SS4 艺准导入属性增加创建者 liunan 2015-3-30
 * SS5 A004-2017-3484 导入路线无法显示，因为Y坐标值太大，修改Y坐标值处理。 liunan 2017-3-29
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
 * <p>Title:用数据文件导入导出工艺路线表 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 赵立斌 刘明
 * @version 1.0
 */

public class RouteListImportExportHandler {
  private static final String CACHE_ROUTELIST = "routeList";
  private static final String CACHE_LISTLINK = "listLink";
  private static final String CACHE_ROUTE_MAP = "routeMap";
  private static final String CACHE_ROUTE = "route";
  private final static String RESOURCE =
      "com.faw_qm.technics.route.util.RouteResource";

  /**标记每条路线有几个分支*/
  private static int count = 0;
  private static RouteListLevelType[] levelTypes;
  
  private static Vector partLinkVec = new Vector();
  public RouteListImportExportHandler() {
  }

  /**
   * 保存工艺路线表。
   * @param hashtable 属性表  其中：编号、名称、用于产品、资料夹、生命周期不能为空。
   * @param vector 要输出的日志信息
   * @return boolean 创建过程是否正确。
   * @throws QMException 
   */
  public static boolean saveRouteList(Hashtable hashtable, Vector vector) throws QMException {
    
	  /*bigversion1==0不导入数据库已有的数据，
	   * bigversion1==1 升级小版本
	   * bigversion1==2 修订
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
      //编号
      listInfo.setRouteListNumber(LoadHelper.getValue("routeListNumber",
          hashtable, LoadHelper.REQUIRED));
      //名称
      listInfo.setRouteListName(LoadHelper.getValue("routeListName", hashtable,
          LoadHelper.REQUIRED));
      //说明
      listInfo.setRouteListDescription(LoadHelper.getValue(
          "routeListDescription", hashtable, LoadHelper.NOT_REQUIRED));
      String routeListLevel = LoadHelper.getValue("routeListLevel", hashtable,
                                                  LoadHelper.REQUIRED);
      //级别
      listInfo.setRouteListLevel(routeListLevel);
      String routeListDepartment = LoadHelper.getValue("routeListDepartment",
          hashtable, LoadHelper.NOT_REQUIRED);
      String routeListDepartmentParent = LoadHelper.getValue(
          "routeListDepartmentParent", hashtable, LoadHelper.NOT_REQUIRED);
      if (routeListDepartment != null && !routeListDepartment.trim().equals("")) {

        //单位
        listInfo.setRouteListDepartment(ResourceExportImportHandler.importCode(
            routeListDepartment, routeListDepartmentParent).getBsoID());
      }
      String routeListState = LoadHelper.getValue("routeListState", hashtable,
                                                  LoadHelper.NOT_REQUIRED);
      String routeListStateParent = LoadHelper.getValue("routeListStateParent",
          hashtable, LoadHelper.NOT_REQUIRED);
      if (routeListState != null && !routeListState.trim().equals("")) {

        //状态
        listInfo.setRouteListState(ResourceExportImportHandler.importCode(
            routeListState, routeListStateParent).getCodeContent());
       
        //产品
      }
      String productNumber = LoadHelper.getValue("productNumber", hashtable,
                                                 LoadHelper.REQUIRED);
      listInfo.setProductMasterID(findPartMaster(productNumber).getBsoID());
      //位置
      String location = LoadHelper.getValue("location", hashtable,
                                            LoadHelper.REQUIRED);
      FolderService folderService = (FolderService) EJBServiceHelper.getService(
          "FolderService");
      //将业务类对象放到资料夹中
      listInfo = (TechnicsRouteListInfo) folderService.assignFolder(listInfo,
          location);
      //生命周期模板
      String lifecycleTemplate = (LoadHelper.getValue("lifecycleTemplate",
          hashtable, LoadHelper.REQUIRED));
      LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
          getService("LifeCycleService");
      listInfo = (TechnicsRouteListInfo) lservice.setLifeCycle(listInfo,
          lservice.getLifeCycleTemplate(lifecycleTemplate));
      //生命周期状态
      String stateStr = LoadHelper.getValue("lifecycleState", hashtable,
                                            LoadHelper.NOT_REQUIRED); //中文
      if (stateStr != null && !stateStr.equals("")) {
        stateStr = RouteHelper.getValue(LifeCycleState.getLifeCycleStateSet(), stateStr); //英文
        LifeCycleState state = LifeCycleState.toLifeCycleState(stateStr);
        listInfo.setLifeCycleState(state);
      }
      
      //CCBegin SS4
      String userName = LoadHelper.getValue("user", hashtable, 1);
      //CCEnd SS4
      
      //项目
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
      //是否有版本.如果有版本，则需修订；如果没版本，则新建。
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
      
      if(bigversion1==0) {//不导入
    	  
    	 if(oldRoutListInfo==null) {
    		 
    		//不导入，数据库中没有此数据，则创建
    		 listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);
   
    		 
    	 }else{
    		 
           //不导入，数据库中有此数据，则输出“数据已经存在”信息
    		 vector.add("数据已经存在！");
    		 
    		 
    	 }
    	 
    	 
      }else if(bigversion1==1) {//小版本升级
    	  
    	  if(oldRoutListInfo==null) {
    		  
    		  //升级小版本，数据库中没有此数据，则创建
     		 
    		  listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);
     		 

             }else {
    			  
    			 //升级小版本,有数据，则升级小版本
    			  listInfo = (TechnicsRouteListInfo) checkInRouteList(listInfo);

    	  }
    	  
      }else if(bigversion1==2){//修订
    	  if(oldRoutListInfo==null) {
    		  
    		  //修订，数据库没有此记录，则创建
    		  listInfo = (TechnicsRouteListInfo) pservice.saveValueInfo(listInfo);
    	  
    	  }else {
    		 
    			  //修订,有数据则修订
    			  
    			  listInfo = (TechnicsRouteListInfo) reviseRouteList(listInfo);

    	  }
    	
         
      }
      LoadHelper.setCacheValue(CACHE_ROUTELIST, listInfo);
	  return true;
      //add by guoxl
 
      

//      //修订
//      if (isHaveVersion != null && !isHaveVersion.equals("") &&
//          Boolean.getBoolean(isHaveVersion)) {
//        listInfo = (TechnicsRouteListInfo) reviseRouteList(listInfo);
//        if (TechnicsRouteServiceEJB.VERBOSE) {
//          System.out.println("修订保存后的工艺路线表：" + listInfo);
//          if (listInfo != null) {
//            System.out.println("修订保存后的工艺路线表：" + listInfo.getBsoID());
//          }
//        }
//      }
//      else {//不修订
//      //  System.out.println("保存前的工艺路线表：" + listInfo.getBsoID());
//        listInfo = (TechnicsRouteListInfo) pservice.saveValueInfo(listInfo);
//        //  listInfo =  (TechnicsRouteListInfo)routeService.storeRouteList(listInfo);
//        if (TechnicsRouteServiceEJB.VERBOSE) {
//          System.out.println("保存后的工艺路线表：" + listInfo);
//          if (listInfo != null) {
//            System.out.println("保存后的工艺路线表：" + listInfo.getBsoID());
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
   * 升级小版本
   * @param  routeInfo 拟导入的未持久化的路线表
   * @return TechnicsRouteListInfo 小版本升级后的值对象
   * @author 郭晓亮
   */
  private static TechnicsRouteListInfo  checkInRouteList(TechnicsRouteListInfo routeInfo)
  throws QMException {
	  //检验拟导入的路线表的文件夹如果是个人资料夹则不允许升级
	  if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) routeInfo)) {
	          
	        		  String routeListNum= routeInfo.getRouteListNumber();
	               throw new TechnicsRouteException("拟导入的路线表("+routeListNum+")的存放位置不应在个人资料夹！");
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
      
     //获得现有的最新小版本
     TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo) routeService.
      getLatestVesion(masterinfo);
     
     //对这个最新小版本进行升级
     //如果该对象在个人资料夹或已被检出，则不允许升级
     if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) oldInfo) ||
           CheckInOutCappTaskLogic.isCheckedOut( (WorkableIfc) oldInfo)) {
           Object[] obj = {
                  oldInfo.getRouteListNumber()};
               throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "11", obj));
  }
	  //获得版本服务
        VersionControlService vcService = (VersionControlService) EJBServiceHelper.
         getService("VersionControlService");
        //升级小版本
        TechnicsRouteListInfo newVersionInfo = (TechnicsRouteListInfo) vcService.
            newIteration( (VersionedIfc) oldInfo);
        
         newVersionInfo=(TechnicsRouteListInfo)vcService.supersede(oldInfo,newVersionInfo);

            //设置生命周期模板
            newVersionInfo.setLifeCycleTemplate(routeInfo.getLifeCycleTemplate());
            //设置生命周期状态
            newVersionInfo.setLifeCycleState(routeInfo.getLifeCycleState());
            //设置工作组
            newVersionInfo.setProjectId(routeInfo.getProjectId());
            newVersionInfo=(TechnicsRouteListInfo)pservice.saveValueInfo(newVersionInfo);
            
	  return newVersionInfo;
  }

  /**
   * 保存路线表和零件的关联。
   * @param hashtable 属性表
   * @param vector 要输出的日志信息
   * @return 创建过程是否正确
   */
  public static boolean saveListRoutePartLink(Hashtable hashtable,
                                              Vector vector) {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println(
          "enter RouteListImportExportHandler.saveListRoutePartLink attributes = " +
          hashtable);
    }
    try {
    	//保存关联开始
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
      //如果该路线表与零部件的关联已存在，则不再新建该关联。
     //CCBegin SS1
     // if (routeService.getListRoutePartLink(routeList.getBsoID(), partMasterID,
     //                                       parentPartNum) == null) {
     //CCEnd SS1
    	 //没有关联则新建关联
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
        //保存关联
        pservice.saveValueInfo(listLinkInfo);
        //缓存该关联
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
   * 保存路线分支。
   * @param hashtable 属性表
   * @param vector 要输出的日志信息
   * @return 创建过程是否正确
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
        //CCBegin by liunan 2011-04-13 需要设置更改标识。 modefyIdenty
        String modefyIdenty = LoadHelper.getValue("modefyIdenty", hashtable,
                                                 LoadHelper.NOT_REQUIRED);
        routeInfo.setModefyIdenty(getCodingIfc(modefyIdenty));
        //CCEnd by liunan 2011-04-13
        RouteItem routeItem = setRouteItem(routeInfo);
        routeMap = new HashMap();
        routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME, routeItem);
        //设置路线映射表。
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
      //获得分支集合。
      Collection branchs = (Collection) routeMap.get(TechnicsRouteServiceEJB.
          TECHNICSROUTEBRANCH_BSONAME);
      //设置分支
      if (branchs == null) {
        branchs = new Vector();
        routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME,
                     branchs);
      }
      RouteItem branchItem = setRouteItem(routeBranchInfo);
      branchs.add(branchItem);

      //设置节点。
      Collection nodes = (Collection) routeMap.get(TechnicsRouteServiceEJB.
          ROUTENODE_BSONAME);
      if (nodes == null) {
        nodes = new Vector();
        routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_BSONAME, nodes);
      }

      Vector tempNodeVector = new Vector();
      int y = 60 * count;
      int x = 0;
      //设置制造路线节点。
      String manuString = LoadHelper.getValue("manuRoute", hashtable,
                                              LoadHelper.NOT_REQUIRED);
      //CCBegin by liunan 2011-04-13 打补丁
      String routeBranchStr=""; //TD 2338
      //CCEnd by liunan 2011-04-13
      if (manuString != null) {
      	//CCBegin by liunan 2011-04-13 打补丁
      	routeBranchStr=manuString.replaceAll("--","→"); //TD 2338 Begin
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
      //CCBegin by liunan 2011-04-13 打补丁
      else{ //TD 2338 
    	  routeBranchStr="无";    	  
      }
      //CCEnd by liunan 2011-04-13
      //设置装配路线节点。
      String assemString = LoadHelper.getValue("assemRoute", hashtable,
                                               LoadHelper.NOT_REQUIRED);
      if (assemString != null) {
      	//CCBegin by liunan 2011-04-13 打补丁
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
      //CCBegin by liunan 2011-04-13 打补丁
      else{ //TD 2338 Begin
    	  routeBranchStr=routeBranchStr+"@无";
      }
      //TD 2338 Begin
      if (routeBranchStr != null && !routeBranchStr.equals("")&&!routeBranchStr.equals("无@无")) {
				routeBranchInfo.setRouteStr(routeBranchStr);
				branchItem = setRouteItem(routeBranchInfo);
				branchs.add(branchItem);
			}
      //TD 2338 end
      //CCEnd by liunan 2011-04-13
      //设置节点关联
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
      //设置分支与节点关联
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
          //获得路线分支信息，可能是多个分支，两个路线分支之间用;分隔，
          //制造路线和装配路线用=分隔，
          //制造路线之间用--分隔，
          //是否主要路线用@分隔
          String routeBranch = LoadHelper.getValue("routeBranch", hashtable, LoadHelper.NOT_REQUIRED);
          int tokenCount = 0;
          StringTokenizer branchToken = null;
          //设置自定义属性
          if(routeBranch != null && !routeBranch.equals(""))
          {
              branchToken = new StringTokenizer(routeBranch, ";");
              tokenCount = branchToken.countTokens();
              for(int j = 0;j < tokenCount;j++)
              {
                  //循环获得每一个路线分支
                  String branch = branchToken.nextToken();
                  
                  String isMainRoute = branch.split("@")[0];
                  //路线串
                  String routeStr = branch.split("@")[1];
                  
                  //CCBegin SS3
                  String routeBranchStr = routeStr.replaceAll("-","→");
                  routeBranchStr = routeBranchStr.replaceAll("=","@");
                  if(routeBranchStr.endsWith("@"))
                  {
                  	routeBranchStr = routeBranchStr + "无";
                  }
                  if(routeBranchStr.startsWith("@"))
                  {
                  	routeBranchStr = "无" + routeBranchStr;
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
                  //获得分支集合。
                  Collection branchs = (Collection)routeMap.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
                  //设置分支
                  if(branchs == null)
                  {
                      branchs = new Vector();
                      routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME, branchs);
                  }
                  RouteItem branchItem = setRouteItem(routeBranchInfo);
                  branchs.add(branchItem);

                  //设置节点。
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
                  //制造路线
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
                  //装配路线
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
                  //设置节点关联
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
                  //设置分支与节点关联
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
   * 根据单位简称获得单位bsoID
   * @param departmentName 简称
   * @return 单位bsoID
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
   * 保存路线
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
      throw new QMRuntimeException("导入文件有误，无法为零部件" + partNumber + "创建路线。");
    }

    RouteHelper.getRouteService().saveRoute(listLinkInfo, routeMap);

    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("exit RouteListImportExportHandler.saveRoute： 保存路线成功。");

    }
  }

  /**
   * 根据零部件编号获得零部件值对象
   * @param partMasterNumber 零部件编号
   * @return 零部件值对象
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
   * 对指定的路线表进行修订
   * @param originalInfo 拟导入的未持久化的路线表
   * @return 新版本的路线表
   * @throws QMException
   */
  private static TechnicsRouteListIfc reviseRouteList(TechnicsRouteListInfo
      originalInfo) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("进入RouteListImportExportHandler.reviseRouteList:");
    }
  //检验拟导入的路线表的文件夹如果是个人资料夹则不允许修订
	  if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) originalInfo)) {
	          
	        		   String routeListNum=originalInfo.getRouteListNumber();
	           
	               throw new TechnicsRouteException("拟导入的路线表("+routeListNum+")的存放位置不应在个人资料夹！");
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
    //获得现有的最新小版本
    TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo) routeService.
        getLatestVesion(masterinfo);
    //对这个最新小版本进行修订
    //如果该对象在个人资料夹或已被检出，则不允许修订
    if (!CheckInOutCappTaskLogic.isInVault( (WorkableIfc) oldInfo) ||
        CheckInOutCappTaskLogic.isCheckedOut( (WorkableIfc) oldInfo)) {
      Object[] obj = {
          oldInfo.getRouteListNumber()};
      throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "10", obj));
    }
    //调用版本服务，生成新版本
    VersionControlService vcService = (VersionControlService) EJBServiceHelper.
        getService("VersionControlService");
    TechnicsRouteListIfc newVersionInfo = (TechnicsRouteListIfc) vcService.
        newVersion( (VersionedIfc) oldInfo);
    //设置新位置
    FolderService folderService = (FolderService) EJBServiceHelper.getService(
        "FolderService");
    folderService.assignFolder(newVersionInfo, originalInfo.getLocation());
    //设置生命周期模板
//    LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService(
//        "LifeCycleService");
//    lservice.setLifeCycle(newVersionInfo,
//                          lservice.getLifeCycleTemplate(originalInfo.
//        getLifeCycleTemplate()));
    newVersionInfo.setLifeCycleTemplate(originalInfo.getLifeCycleTemplate());
    //设置生命周期状态
    newVersionInfo.setLifeCycleState(originalInfo.getLifeCycleState());
    //设置工作组
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
      // 导出工基本属性信息
      stringbuffer = exportRouteListAttr(tech, stringbuffer);
      //导出与零部件以及关联工艺路线
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
          //是否是主要路线
          stringbuffer.append(branch.getMainRoute() + ",");
          //因分枝数据量较少，未采用两表联查方式。
          Collection branchNodes = routeService.getBranchRouteNodes(branch);
          //obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
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
        //是否是主要路线
        stringbuffer.append(branch.getMainRoute() + ",");
        //因分枝数据量较少，未采用两表联查方式。
        Collection branchNodes = routeService.getBranchRouteNodes(branch);
        //obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
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
   * 根据工艺路线ID获得工艺路线分枝相关对象。
   * @return HashMap key=工艺路线分枝值对象, value=路线节点数组，obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
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
   * 导出路线表的基本属性信息
   * @param tech QMTechnicsIfc 路线表值对象
   * @param stringbuffer StringBuffer  导出的字符串
   * @return StringBuffer 导出的字符串
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
    //保存生命周期名
    LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)
        service.refreshInfo(tech.
                            getLifeCycleTemplate());
    stringbuffer.append(template.getLifeCycleName() + ",");
    stringbuffer.append(tech.getLifeCycleState().toString() + ",");
    //项目组
    String id = tech.getProjectId();
    if (id != null) {

      ProjectInfo project = (ProjectInfo) service.refreshInfo(id);
      stringbuffer.append(project.getName() + ",");
    }
    else {
      stringbuffer.append("null,");
      //资料夹
    }
    stringbuffer.append("FALSE");
    stringbuffer.append("\n");
    return stringbuffer;
  }

  /**
   * 将所生成的信息写入文件
   * @param buf 新生成的信息
   * @param filename 文件名
   * @param flag 是否取代原有的文件数据
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
   * 创建头信息
   * @return StringBuffer 创建好的头信息
   * @throws QMException
   */
  protected static StringBuffer createHeadMessage() throws QMException {
    StringBuffer stringbuffer = new StringBuffer();
    try {
      SessionService sservice = (SessionService) EJBServiceHelper.
          getService("SessionService");
      //建立头信息
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
   * 工艺路线和零部件的关联保存完成后，整理partindex集合保存工艺路线表。
   * @param hashtable 属性表。
   * @param vector 要输出的日志信息。
   * @return boolean 创建过程是否正确。
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
	      ids[0] = listLinkInfo.getRightBsoID(); //零件masterID
	      ids[1] = listLinkInfo.getParentPartNum(); //父件编号  实际是产品编号
	      ids[2] = Integer.toString(listLinkInfo.getCount()); //数量
	      ids[3] = listLinkInfo.getPartBranchID(); //零件parid
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
   * 根据单位简称获得单位bsoID
   * @param departmentName 简称
   * @return 单位bsoID
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
