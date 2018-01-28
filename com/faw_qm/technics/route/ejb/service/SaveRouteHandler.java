/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */


package com.faw_qm.technics.route.ejb.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.faw_qm.cappclients.capproute.graph.RouteItem;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.RouteNodeLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 保存工艺路线表。负责解析客户端传入的整个路线表信息。包括路线和其对应的节点及节点
 * 间的关联。
 *             if(assertion instanceof ReplaceAssertion)
 *             {
 * //此时如果业务对象状态是wrk时，不更新主业务对象。否则，撤销检出时主业务对象需要?
 * 改成最新版本。
 *                 MasterIfc masterinfo = (MasterIfc)info.getMaster();
 *                 pservice.updateBso(masterinfo, false);
 *             }
 * 路线串构造规则：
 * ！在一个路线串中出现多个装配单位,不符合路线串构造规则。
 * ！在一个路线串装配单位不是最后一个节点,不符合路线串构造规则，此项规则可在客户端?

 * 行判断。
 * ！一个路线单位在同相邻位置重复出现,节点类型相同,不符合路线串构造规则
 *
 * 与getRouteNodes相关。
 * 注意：做保存后处理。找到起始节点，进行设置。
 * 1.此操作每次保存时都做，因为可添加新的起始节点且在起始节点前添加起始节点。
 * 2.保存和更新次数相对于查看较少，所以此操作不放在查看时做。
 */
public class SaveRouteHandler {
  /**
   * @roseuid 4039F29E00FB
   */
  private SaveRouteHandler() {
  }

  protected static void doSave(ListRoutePartLinkIfc listLinkInfo,
                               HashMap routeRelaventObejts) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("-> RouteListHandler.doSave - IN ***************");
    }
    TechnicsRouteService routeService = RouteHelper.getRouteService();
    //处理路线。
   // System.out.println("1");
    RouteItem routeItem = (RouteItem) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
    //如果是更新路线，删除分枝。
  //  System.out.println("2");
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) routeItem.getObject();
    if (PersistHelper.isPersistent(routeInfo)) {
      if (TechnicsRouteServiceEJB.VERBOSE) {
        System.out.println("RouteListHandler : routeID = " + routeInfo.getBsoID());
      }
      routeService.deleteBranchRelavent(routeInfo.getBsoID());
    }
    handleRoute(listLinkInfo, routeItem);
    //处理节点。
    Collection node = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("开始进入： handleRouteItem(node);");
    }
    handleRouteItem(node);
    //处理节点关联。
    Collection nodelink = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("开始进入： handleRouteItem(nodelink);");
    }
    handleRouteItem(nodelink);

    //处理分枝。
    Collection branch = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("开始进入： handleRouteItem(branch);");
    }
    handleRouteItem(branch);
    //处理分枝关联。
    Collection branchNodeLink = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("开始进入： handleRouteItem(branchNodeLink);");
    }
    handleRouteItem(branchNodeLink);
  }

  private static void handleRoute(ListRoutePartLinkIfc listLinkInfo,
                                  RouteItem routeItem) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter the SaveRouteHandler:handleRoute():110 row");
    }
    if (routeItem.getState().equalsIgnoreCase(RouteItem.DELETE)) {
      throw new TechnicsRouteException("删除路线有专用方法，不在此处处理");
    }
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) routeItem.getObject();
    TechnicsRouteService routeService = RouteHelper.getRouteService();
    //更新或创建工艺路线。
//  CCBegin by leixiao 2008-9-4 原因：解放升级工艺路线，工艺路线说明信息中存储当前零件版本  090108修改从IBA取汽研发布版本 
    String version="";
    if(listLinkInfo.getPartBranchInfo()!=null){
    	version=routeService.getibaPartVersion(listLinkInfo.getPartBranchInfo());	
    }
    else{
    version =routeService.getibaPartVersion(listLinkInfo.getPartMasterInfo());
    }
    String preString ="("+version+")";
    String description =routeInfo.getRouteDescription();
    if(description!=null){
        if(description.startsWith("(")&&description.indexOf(")")!=-1){
        	description=preString+description.substring(description.indexOf(")")+1,description.length()); //anan
        }
    }
    else{
    	description="";
    }
    //description=preString+description;  anan
    routeInfo.setRouteDescription(description);
//  CCEnd by leixiao 2008-9-4 原因：解放升级工艺路线
    routeService.saveRoute(listLinkInfo, routeInfo);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("exit the SaveRouteHandler:handleRoute():120 row");
    }
  }

  private static void handleRouteItem(Collection routeContainObj) throws
      QMException {
    if (routeContainObj == null) {
      return;
    }
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter routeListHandler's handleRouteItem........");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    for (Iterator iter = routeContainObj.iterator(); iter.hasNext(); ) {
      RouteItem routeItem = (RouteItem) iter.next();
      BaseValueIfc info = (BaseValueIfc) routeItem.getObject();
      if (routeItem.getState().equals(RouteItem.CREATE) ||
          routeItem.getState().equals(RouteItem.UPDATE)) {
        pservice.saveValueInfo(info);
        if (TechnicsRouteServiceEJB.VERBOSE) {
          System.out.println(
              " enter create clause, routeListHandler's handleRouteItem , save success ,bsoid = " +
              info.getBsoID());
        }
      }
      if (routeItem.getState().equals(RouteItem.DELETE)) {
        if (info.getBsoID() == null || info.getBsoID().trim().length() < 1) {
          throw new TechnicsRouteException("删除对象不能没有bsoid");
        }
        /*try
                         {
            pservice.deleteValueInfo(info);
                         }
                         catch(QMException e)
                         {
            //System.out.println("################此异常是正常现象，不影响系统正常运行。");
                         }*/
        pservice.removeValueInfo(info);
        if (TechnicsRouteServiceEJB.VERBOSE) {
          System.out.println(
              "enter delete clause, routeListHandler's handleRouteItem , delete success ,bsoid = " +
              info.getBsoID());
        }
      }
    }
  }

  /*
      private void handleNode(Collection node) throws QMException
      {
          for(Iterator iter = node.iterator(); iter.hasNext();)
          {
              RouteItem routeItem = (RouteItem)iter.next();
              if(routeItem.getState().equals(RouteItem.ROUTEALTER) || routeItem.getState().equals(RouteItem.UPDATE))
              {

              }
              if(routeItem.getState().equals(RouteItem.DELETE))
              {

              }
          }
      }

      private void handleNodeLink(Collection nodelink) throws QMException
      {

      }

      private void handleBranch(Collection branch) throws QMException
      {

      }

   private void handleBranchNodeLink(Collection branchNodeLink) throws QMException
      {

      }*/
  
  
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
  protected static void impoartDoSave(ListRoutePartLinkIfc listLinkInfo,
          HashMap routeRelaventObejts) throws
QMException {
PersistService service = (PersistService) EJBServiceHelper.
getPersistService();
String routeid = listLinkInfo.getRouteID();
TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) service.refreshInfo(routeid);
/////////////////quxg add for jiefang,如果零部件已存在的路线和要导入的路线相同，则不再导入////////////////////////////////////////////////
TechnicsRouteService routeService = RouteHelper.getRouteService();
Map map = (Map) routeService.getRouteBranchs(routeid);
Iterator values = map.values().iterator();
String makeStr = "";
String beingImportMakeStr = "";//制造路线和装配路线串！
Collection nodes = (Collection) routeRelaventObejts.get("RouteNode");
for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
RouteItem nodeItem = (RouteItem) iter.next();
RouteNodeInfo node = (RouteNodeInfo) nodeItem.getObject();

String departmentID = node.getNodeDepartment();
CodingClassificationInfo code = (CodingClassificationInfo) service.
refreshInfo(departmentID);
//  String departmentName=codingService.
beingImportMakeStr += code.getClassSort() + "-";
}
beingImportMakeStr = beingImportMakeStr.endsWith("-") ?
beingImportMakeStr.substring(0, beingImportMakeStr.length() - 1) :
beingImportMakeStr;
boolean flag = false;
while (values.hasNext()) {
String kk = "";
Object[] objs1 = (Object[]) values.next();
Vector makeNodes = (Vector) objs1[0];
if (makeNodes != null && makeNodes.size() > 0) {
for (int m = 0; m < makeNodes.size(); m++) {
RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
if (node.getIsTempRoute()) {
kk += node.getNodeDepartmentName() + "*" + "-";
}
else {
kk += node.getNodeDepartmentName() + "-";
}
}
}

RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1];
if (asseNode != null) {
if (asseNode.getIsTempRoute()) {
kk += asseNode.getNodeDepartmentName() + "*" + "-";
}
else {
kk += asseNode.getNodeDepartmentName() + "-";
}
}

makeStr = kk.endsWith("-") ? kk.substring(0, kk.length() - 1) : kk;
// System.out.println("makeStr   " + makeStr);
// System.out.println("beingImportMakeStr   " + beingImportMakeStr);
if (makeStr.equals(beingImportMakeStr)) {
flag = true;
}
}

if (flag) {
return;
}
////////////////////////////////////////////////////////////////
//处理节点。
Collection node = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
importHandleRouteItem(node, routeInfo);
//处理节点关联。
Collection nodelink = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
importHandleRouteItem(nodelink, routeInfo);

//处理分枝。
Collection branch = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
importHandleRouteItem(branch, routeInfo);
//处理分枝关联。
Collection branchNodeLink = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
handleRouteItem(branchNodeLink);
}

private static void importHandleRouteItem(Collection routeContainObj,
                TechnicsRouteIfc routeInfo) throws
QMException {
if (routeContainObj == null) {
return;
}
if (TechnicsRouteServiceEJB.VERBOSE) {
System.out.println("enter routeListHandler's handleRouteItem........");
}
PersistService pservice = (PersistService) EJBServiceHelper.
getPersistService();
for (Iterator iter = routeContainObj.iterator(); iter.hasNext(); ) {
RouteItem routeItem = (RouteItem) iter.next();
Object ob = routeItem.getObject();
BaseValueIfc info = (BaseValueIfc) ob;

if (routeItem.getState().equals(RouteItem.CREATE) ||
routeItem.getState().equals(RouteItem.UPDATE)) {
if (info instanceof RouteNodeIfc) {
( (RouteNodeIfc) info).setRouteInfo(routeInfo);
}
if (info instanceof TechnicsRouteBranchIfc) {
( (TechnicsRouteBranchIfc) info).setRouteInfo(routeInfo);
}
if (info instanceof RouteNodeLinkIfc) {
( (RouteNodeLinkIfc) info).setRouteInfo(routeInfo);
}

pservice.saveValueInfo(info);
if (TechnicsRouteServiceEJB.VERBOSE) {
System.out.println(
" enter create clause, routeListHandler's handleRouteItem , save success ,bsoid = " +
info.getBsoID());
}
}
if (routeItem.getState().equals(RouteItem.DELETE)) {
if (info.getBsoID() == null || info.getBsoID().trim().length() < 1) {
throw new TechnicsRouteException("删除对象不能没有bsoid");
}
pservice.removeValueInfo(info);
if (TechnicsRouteServiceEJB.VERBOSE) {
System.out.println(
"enter delete clause, routeListHandler's handleRouteItem , delete success ,bsoid = " +
info.getBsoID());
}
}
}
}
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
}
