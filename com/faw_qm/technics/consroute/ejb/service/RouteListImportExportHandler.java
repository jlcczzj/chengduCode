/** 生成程序RouteListImportExportHandler.java	1.1  2004/05/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 轴齿中心路线导入功能改造 2014-1-6 文柳
 * SS2 轴齿中心路线导入,路线串只读组织机构-》轴齿中心下的代码内容 2014-2-18 文柳
 * SS3 轴齿路线导入，增加“零部件版本”属性导入，原有“历史版本”默认为空  2014-2-26 文柳
 * SS4 路线导入时,主要零件为空判断 pante 2014-03-25
 * SS5 修改：轴齿路线导入，在瘦客户查看一个分支出现两行记录  2014-3-27 文柳 
 * SS6 轴齿中心使用单独的导入方法,因为路线表说明信息格式特殊 pante 2014-04-29
 * SS7 说明换行符错误 pante 2014-05-06
 * SS8 解放和变速箱有重号件，先取解放件，没有的话取变速箱件。 liunan 2016-2-29
 * SS9 成都代码合并，区分各单位组织机构 liunan 2016-9-14
 * SS10 A004-2016-3439 导入二级路线增加用户项，以便让创建者和任务是指定人。 liunan 2016-10-31
 * SS11 A032-2017-0144 成都路线导入时候按版本进行导入 liunan 2017-1-11
 * SS12 A004-2017-3484 导入路线无法显示，因为Y坐标值太大，修改Y坐标值处理。 liunan 2017-3-29
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
 * <p>Title:用数据文件导入导出工艺路线表 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: 一汽启明</p>
 * @author 赵立斌 刘明
 * @version 1.0
 */

public class RouteListImportExportHandler
{
    private static final String CACHE_ROUTELIST = "routeList";
    private static final String CACHE_LISTLINK = "listLink";
    private static final String CACHE_ROUTE_MAP = "routeMap";
    private static final String CACHE_ROUTE = "route";
    private final static String RESOURCE = "com.faw_qm.technics.consroute.util.RouteResource";

    /** 标记每条路线有几个分支 */
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
     * 保存工艺路线表。
     * @param hashtable 属性表 其中：编号、名称、用于产品、资料夹、生命周期不能为空。
     * @param vector 要输出的日志信息
     * @return boolean 创建过程是否正确。 @
     * @throws ServiceLocatorException 
     * @throws LoadDataException 
     */
    public static boolean saveRouteList(Hashtable hashtable, Vector vector) throws QMException
    {

        /*
         * bigversion1==0不导入数据库已有的数据， bigversion1==1 升级小版本 bigversion1==2 修订
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
        //编号
        listInfo.setRouteListNumber(LoadHelper.getValue("routeListNumber", hashtable, LoadHelper.REQUIRED));
        //名称
        listInfo.setRouteListName(LoadHelper.getValue("routeListName", hashtable, LoadHelper.REQUIRED));
        //说明
        listInfo.setRouteListDescription(LoadHelper.getValue("routeListDescription", hashtable, LoadHelper.NOT_REQUIRED));
        String routeListLevel = LoadHelper.getValue("routeListLevel", hashtable, LoadHelper.REQUIRED);
        //级别
        listInfo.setRouteListLevel(routeListLevel);
        String routeListDepartment = LoadHelper.getValue("routeListDepartment", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListDepartmentParent = LoadHelper.getValue("routeListDepartmentParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListDepartment != null && !routeListDepartment.trim().equals(""))
        {

            //单位
            listInfo.setRouteListDepartment(ResourceExportImportHandler.importCode(routeListDepartment, routeListDepartmentParent).getBsoID());
        }
        String routeListState = LoadHelper.getValue("routeListState", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListStateParent = LoadHelper.getValue("routeListStateParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListState != null && !routeListState.trim().equals(""))
        {

            //状态
            listInfo.setRouteListState(ResourceExportImportHandler.importCode(routeListState, routeListStateParent).getCodeContent());

            //产品
        }
      //CCBegin SS4
        String productNumber = LoadHelper.getValue("productNumber", hashtable, LoadHelper.NOT_REQUIRED);
        if(productNumber != null && !productNumber.trim().equals(""))
        	listInfo.setProductMasterID(findPartMaster(productNumber).getBsoID());
      //CCEnd SS4
        //位置
        String location = LoadHelper.getValue("location", hashtable, LoadHelper.REQUIRED);
        FolderService folderService = (FolderService)EJBServiceHelper.getService("FolderService");
        //将业务类对象放到资料夹中
        listInfo = (TechnicsRouteListInfo)folderService.assignFolder(listInfo, location);
        //生命周期模板
        String lifecycleTemplate = (LoadHelper.getValue("lifecycleTemplate", hashtable, LoadHelper.REQUIRED));
        LifeCycleService lservice = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
        listInfo = (TechnicsRouteListInfo)lservice.setLifeCycle(listInfo, lservice.getLifeCycleTemplate(lifecycleTemplate));
        //生命周期状态
        String stateStr = LoadHelper.getValue("lifecycleState", hashtable, LoadHelper.NOT_REQUIRED); //中文
        if(stateStr != null && !stateStr.equals(""))
        {
            stateStr = RouteHelper.getValue(LifeCycleState.getLifeCycleStateSet(), stateStr); //英文
            LifeCycleState state = LifeCycleState.toLifeCycleState(stateStr);
            listInfo.setLifeCycleState(state);
        }
        //项目
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
        //是否有版本.如果有版本，则需修订；如果没版本，则新建。
        String isHaveVersion = LoadHelper.getValue("isHasVersion", hashtable, LoadHelper.NOT_REQUIRED);

        //add by guoxl                                         
        TechnicsRouteService routeListService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        TechnicsRouteListInfo oldRoutListInfo = routeListService.findRouteListByNum(listInfo.getRouteListNumber());

        if(bigversion1 == 0)
        {//不导入

            if(oldRoutListInfo == null)
            {

                //不导入，数据库中没有此数据，则创建

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //不导入，数据库中有此数据，则输出“数据已经存在”信息
                vector.add("数据已经存在！");

            }

        }else if(bigversion1 == 1)
        {//小版本升级

            if(oldRoutListInfo == null)
            {

                //升级小版本，数据库中没有此数据，则创建

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //升级小版本,有数据，则升级小版本
                listInfo = (TechnicsRouteListInfo)checkInRouteList(listInfo);

            }

        }else if(bigversion1 == 2)
        {//修订
            if(oldRoutListInfo == null)
            {

                //修订，数据库没有此记录，则创建
                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //修订,有数据则修订

                listInfo = (TechnicsRouteListInfo)reviseRouteList(listInfo);

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
     * @param routeInfo 拟导入的未持久化的路线表
     * @return TechnicsRouteListInfo 小版本升级后的值对象
     * @author 郭晓亮
     * @throws QMException 
     * @throws  
     */
    private static TechnicsRouteListInfo checkInRouteList(TechnicsRouteListInfo routeInfo) throws  QMException
    {
        //检验拟导入的路线表的文件夹如果是个人资料夹则不允许升级
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)routeInfo))
        {

            String routeListNum = routeInfo.getRouteListNumber();
            throw new QMException("拟导入的路线表(" + routeListNum + ")的存放位置不应在个人资料夹！");
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

        //获得现有的最新小版本
        TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo)routeService.getLatestVesion(masterinfo);

        //对这个最新小版本进行升级
        //如果该对象在个人资料夹或已被检出，则不允许升级
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)oldInfo) || CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)oldInfo))
        {
            Object[] obj = {oldInfo.getRouteListNumber()};
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "11", obj));
        }
        //获得版本服务
        VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        //升级小版本
        TechnicsRouteListInfo newVersionInfo = (TechnicsRouteListInfo)vcService.newIteration((VersionedIfc)oldInfo);

        newVersionInfo = (TechnicsRouteListInfo)vcService.supersede(oldInfo, newVersionInfo);

        //设置生命周期模板
        newVersionInfo.setLifeCycleTemplate(routeInfo.getLifeCycleTemplate());
        //设置生命周期状态
        newVersionInfo.setLifeCycleState(routeInfo.getLifeCycleState());
        //设置工作组
        newVersionInfo.setProjectId(routeInfo.getProjectId());
        newVersionInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(newVersionInfo);

        return newVersionInfo;
    }

    /**
     * 保存路线表和零件的关联。
     * @param hashtable 属性表
     * @param vector 要输出的日志信息
     * @return 创建过程是否正确
     */
    public static boolean saveListRoutePartLink(Hashtable hashtable, Vector vector)
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListImportExportHandler.saveListRoutePartLink attributes = " + hashtable);
        }
        try
        {
            //保存关联开始
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
            //先取解放的中心发布零部件，没有就取变速箱设计零部件。
            QMPartIfc part = findPart(partNumber);
            //CCEnd SS8
            String partBsoID = part.getBsoID();
            //采购标识
          //CCBegin SS4
//            String stockID = LoadHelper.getValue("stockID", hashtable, LoadHelper.REQUIRED);
            String stockID = LoadHelper.getValue("stockID", hashtable, LoadHelper.NOT_REQUIRED);
          //CCEnd SS4
            if(stockID==null||stockID.equals("")){
            	stockID = "";
            }
            //CCBegin SS3
            //预留属性----轴齿历史版本
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
            //如果该路线表与零部件的关联已存在，则不再新建该关联。
            if(routeService.getListRoutePartLink(routeList.getBsoID(), partBsoID, parentPartNum) == null)
            {
                //没有关联则新建关联
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
                	//先取解放的中心发布零部件，没有就取变速箱设计零部件。
                	QMPartIfc partParent = findPart(parentPartNum);
                	//CCEnd SS8
                	
                	if(partParent!=null){
                		listLinkInfo.setParentPartID(partParent.getBsoID());
                		listLinkInfo.setParentPartNum(parentPartNum);
                		listLinkInfo.setParentPartName(partParent.getPartName());
                	}
                	//CCEnd SS4
                }

                listLinkInfo.setStockID(stockID);//存储采购标识
                listLinkInfo.setModifyIdenty("新增");
                //CCBegin SS3
                //存储预留属性---轴齿历史版本
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

                //保存关联
                pservice.saveValueInfo(listLinkInfo);
                //缓存该关联
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
     * 保存路线分支。
     * @param hashtable 属性表
     * @param vector 要输出的日志信息
     * @return 创建过程是否正确
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
                //设置路线映射表。
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
            //获得分支集合。
            Collection branchs = (Collection)routeMap.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
            //设置分支
            if(branchs == null)
            {
                branchs = new Vector();
                routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME, branchs);
            }
            //CCBegin SS5
//            RouteItem branchItem = setRouteItem(routeBranchInfo);
//            branchs.add(branchItem);
            //CCEnd SS5

            //设置节点。
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
            //设置制造路线节点。
            String manuString = LoadHelper.getValue("manuRoute", hashtable, LoadHelper.NOT_REQUIRED);
            String routeBranchStr = ""; //TD 2338 
            if(manuString != null)
            {
                routeBranchStr = manuString.replaceAll("--", "→"); //TD 2338 Begin
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
                routeBranchStr = "无";

            }
            //设置装配路线节点。
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
                routeBranchStr = routeBranchStr + "@无";
            }
            //TD 2338 Begin
            if(routeBranchStr != null && !routeBranchStr.equals("") && !routeBranchStr.equals("无@无"))
            {
                routeBranchInfo.setRouteStr(routeBranchStr);
                //CCBegin SS5
                RouteItem branchItem = setRouteItem(routeBranchInfo);
                //CCEnd SS5
                branchs.add(branchItem);
            }
            //CCBegin SS5 上面的判断存在问题
            else{
                routeBranchInfo.setRouteStr(routeBranchStr);
                RouteItem branchItem = setRouteItem(routeBranchInfo);
                branchs.add(branchItem);
            }
            //CCEnd SS5
            //TD 2338 end
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
     * 根据单位简称获得单位bsoID
     * @param departmentName 简称
     * @return 单位bsoID
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
     * 根据组织机构下，轴齿中心的所有代码内容
     * @return 代码内容集合
     * @author wenliu
     */
    private static HashMap getCoding() throws QMException
    {
    	HashMap codeMap = new HashMap();
        CodingManageService service = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
//    	if(DocServiceHelper.getUserFromCompany().equals("zczx")){
    		//CCBegin SS9
    		//Collection col = service.getCoding("轴齿中心","组织机构");
    		Collection col = null;
    		if(comp.equals("zczx"))
    		{
    			col = service.getCoding("轴齿中心","组织机构");
    		}
    		else if(comp.equals("cd"))
    		{
    			col = service.getCoding("组织机构-cd","代码分类");
    		}
    		//CCEnd SS9
    		if(col!=null&&col.size()>0){
    			codeMap =  new HashMap();
    			for(Iterator ite = col.iterator();ite.hasNext();){
    				CodingIfc code = (CodingIfc)ite.next();
    				codeMap.put(code.getShorten(), code.getBsoID());//key是代码简称；
    			}
    		}
//    	}
    	return codeMap;
    }
    /**
     * 得到组织机构下，轴齿中心的代码集合
     * @return HashMap 代码集合
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
     * 保存路线
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
            throw new QMException("导入文件有误，无法为零部件" + partNumber + "创建路线。");
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
        
        //构造字符串
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
            System.out.println("exit RouteListImportExportHandler.saveRoute： 保存路线成功。");

        }
    }

    /**
     * 根据零部件编号获得零部件值对象
     * @param partMasterNumber 零部件编号
     * @return 零部件值对象 @
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
        					//如果有版本相同的则跳出循环，否则取最新版
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
        						//如果有版本相同的则跳出循环，否则取最新版
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
            	continue;//该master根据配置规范没有得到part
            }
            if(part.getViewName().equals("中心设计视图"))
            {
            	  jfpart = part;
            }
            else if(part.getViewName().equals("设计视图")||part.getViewName().equals("工程视图"))
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
     * 对指定的路线表进行修订
     * @param originalInfo 拟导入的未持久化的路线表
     * @return 新版本的路线表 @
     * @throws QMException 
     * @throws  
     */
    private static TechnicsRouteListIfc reviseRouteList(TechnicsRouteListInfo originalInfo) throws  QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("进入RouteListImportExportHandler.reviseRouteList:");
        }
        //检验拟导入的路线表的文件夹如果是个人资料夹则不允许修订
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)originalInfo))
        {

            String routeListNum = originalInfo.getRouteListNumber();

            throw new QMException("拟导入的路线表(" + routeListNum + ")的存放位置不应在个人资料夹！");
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
        //获得现有的最新小版本
        TechnicsRouteListInfo oldInfo = (TechnicsRouteListInfo)routeService.getLatestVesion(masterinfo);
        //对这个最新小版本进行修订
        //如果该对象在个人资料夹或已被检出，则不允许修订
        if(!CheckInOutCappTaskLogic.isInVault((WorkableIfc)oldInfo) || CheckInOutCappTaskLogic.isCheckedOutByOther((WorkableIfc)oldInfo))
        {
            Object[] obj = {oldInfo.getRouteListNumber()};
            throw new QMException(QMMessage.getLocalizedMessage(RESOURCE, "10", obj));
        }
        //调用版本服务，生成新版本
        VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        TechnicsRouteListIfc newVersionInfo = (TechnicsRouteListIfc)vcService.newVersion((VersionedIfc)oldInfo);
        //设置新位置
        FolderService folderService = (FolderService)EJBServiceHelper.getService("FolderService");
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
            // 导出工基本属性信息
            stringbuffer = exportRouteListAttr(tech, stringbuffer);
            //导出与零部件以及关联工艺路线
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
                TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)objs[size - 1];
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
     * @J2EE_METHOD -- getRouteBranchs 根据工艺路线ID获得工艺路线分枝相关对象。
     * @return HashMap key=工艺路线分枝值对象, value=路线节点数组，obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
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
     * 导出路线表的基本属性信息
     * @param tech QMTechnicsIfc 路线表值对象
     * @param stringbuffer StringBuffer 导出的字符串
     * @return StringBuffer 导出的字符串
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
        //保存生命周期名
        LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)service.refreshInfo(tech.getLifeCycleTemplate());
        stringbuffer.append(template.getLifeCycleName() + ",");
        stringbuffer.append(tech.getLifeCycleState().toString() + ",");
        //项目组
        String id = tech.getProjectId();
        if(id != null)
        {

            ProjectInfo project = (ProjectInfo)service.refreshInfo(id);
            stringbuffer.append(project.getName() + ",");
        }else
        {
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
     * @param flag 是否取代原有的文件数据 @
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
     * 创建头信息
     * @return StringBuffer 创建好的头信息 @
     * @throws QMException 
     */
    protected static StringBuffer createHeadMessage() throws QMException
    {
        StringBuffer stringbuffer = new StringBuffer();
        try
        {
            SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
            //建立头信息
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
     * 保存轴齿工艺路线表。
     * @param hashtable 属性表 其中：编号、名称、用于产品、资料夹、生命周期不能为空。
     * @param vector 要输出的日志信息
     * @return boolean 创建过程是否正确。 @
     * @throws ServiceLocatorException 
     * @throws LoadDataException 
     */
    public static boolean saveZCRouteList(Hashtable hashtable, Vector vector) throws QMException
    {

        /*
         * bigversion1==0不导入数据库已有的数据， bigversion1==1 升级小版本 bigversion1==2 修订
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
        //编号
        listInfo.setRouteListNumber(LoadHelper.getValue("routeListNumber", hashtable, LoadHelper.REQUIRED));
        //名称
        listInfo.setRouteListName(LoadHelper.getValue("routeListName", hashtable, LoadHelper.REQUIRED));
        //说明
        //CCBegin SS7
        //System.out.println("路线说明导入------------------------------");
        String zc = "根据解放公司规划发展部一级路线文件《        》编制此二级路线。\r\n根据：PDM部件更改说明单     及本艺准进行生产准备。\r\n说明：                   \r\n路线代码：\r\n发往单位：规划发展部、生产计划部、采购部、质量保证部、制造部、管理部";
        //System.out.println("路线说明导入------------------------------"+zc);
        //CCEnd SS7
        listInfo.setRouteListDescription(zc);
        String routeListLevel = LoadHelper.getValue("routeListLevel", hashtable, LoadHelper.REQUIRED);
        //级别
        listInfo.setRouteListLevel(routeListLevel);
        String routeListDepartment = LoadHelper.getValue("routeListDepartment", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListDepartmentParent = LoadHelper.getValue("routeListDepartmentParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListDepartment != null && !routeListDepartment.trim().equals(""))
        {

            //单位
            listInfo.setRouteListDepartment(ResourceExportImportHandler.importCode(routeListDepartment, routeListDepartmentParent).getBsoID());
        }
        String routeListState = LoadHelper.getValue("routeListState", hashtable, LoadHelper.NOT_REQUIRED);
        String routeListStateParent = LoadHelper.getValue("routeListStateParent", hashtable, LoadHelper.NOT_REQUIRED);
        if(routeListState != null && !routeListState.trim().equals(""))
        {

            //状态
            listInfo.setRouteListState(ResourceExportImportHandler.importCode(routeListState, routeListStateParent).getCodeContent());

            //产品
        }
      //CCBegin SS4
        String productNumber = LoadHelper.getValue("productNumber", hashtable, LoadHelper.NOT_REQUIRED);
        if(productNumber != null && !productNumber.trim().equals(""))
        	listInfo.setProductMasterID(findPartMaster(productNumber).getBsoID());
      //CCEnd SS4
        //位置
        String location = LoadHelper.getValue("location", hashtable, LoadHelper.REQUIRED);
        FolderService folderService = (FolderService)EJBServiceHelper.getService("FolderService");
        //将业务类对象放到资料夹中
        listInfo = (TechnicsRouteListInfo)folderService.assignFolder(listInfo, location);
        //生命周期模板
        String lifecycleTemplate = (LoadHelper.getValue("lifecycleTemplate", hashtable, LoadHelper.REQUIRED));
        LifeCycleService lservice = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
        listInfo = (TechnicsRouteListInfo)lservice.setLifeCycle(listInfo, lservice.getLifeCycleTemplate(lifecycleTemplate));
        //生命周期状态
        String stateStr = LoadHelper.getValue("lifecycleState", hashtable, LoadHelper.NOT_REQUIRED); //中文
        if(stateStr != null && !stateStr.equals(""))
        {
            stateStr = RouteHelper.getValue(LifeCycleState.getLifeCycleStateSet(), stateStr); //英文
            LifeCycleState state = LifeCycleState.toLifeCycleState(stateStr);
            listInfo.setLifeCycleState(state);
        }
        //项目
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
        //是否有版本.如果有版本，则需修订；如果没版本，则新建。
        String isHaveVersion = LoadHelper.getValue("isHasVersion", hashtable, LoadHelper.NOT_REQUIRED);

        //add by guoxl                                         
        TechnicsRouteService routeListService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        TechnicsRouteListInfo oldRoutListInfo = routeListService.findRouteListByNum(listInfo.getRouteListNumber());

        if(bigversion1 == 0)
        {//不导入

            if(oldRoutListInfo == null)
            {

                //不导入，数据库中没有此数据，则创建

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //不导入，数据库中有此数据，则输出“数据已经存在”信息
                vector.add("数据已经存在！");

            }

        }else if(bigversion1 == 1)
        {//小版本升级

            if(oldRoutListInfo == null)
            {

                //升级小版本，数据库中没有此数据，则创建

                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //升级小版本,有数据，则升级小版本
                listInfo = (TechnicsRouteListInfo)checkInRouteList(listInfo);

            }

        }else if(bigversion1 == 2)
        {//修订
            if(oldRoutListInfo == null)
            {

                //修订，数据库没有此记录，则创建
                listInfo = (TechnicsRouteListInfo)pservice.saveValueInfo(listInfo);

            }else
            {

                //修订,有数据则修订

                listInfo = (TechnicsRouteListInfo)reviseRouteList(listInfo);

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
    //CCEnd SS6
    
    //CCBegin SS10
    /**
     * 改变会话的参与者
     * 
     * @param principal
     *            参与者
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

