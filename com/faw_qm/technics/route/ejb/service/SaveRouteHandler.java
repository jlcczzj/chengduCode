/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
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
 * ���湤��·�߱���������ͻ��˴��������·�߱���Ϣ������·�ߺ����Ӧ�Ľڵ㼰�ڵ�
 * ��Ĺ�����
 *             if(assertion instanceof ReplaceAssertion)
 *             {
 * //��ʱ���ҵ�����״̬��wrkʱ����������ҵ����󡣷��򣬳������ʱ��ҵ�������Ҫ?
 * �ĳ����°汾��
 *                 MasterIfc masterinfo = (MasterIfc)info.getMaster();
 *                 pservice.updateBso(masterinfo, false);
 *             }
 * ·�ߴ��������
 * ����һ��·�ߴ��г��ֶ��װ�䵥λ,������·�ߴ��������
 * ����һ��·�ߴ�װ�䵥λ�������һ���ڵ�,������·�ߴ�������򣬴��������ڿͻ���?

 * ���жϡ�
 * ��һ��·�ߵ�λ��ͬ����λ���ظ�����,�ڵ�������ͬ,������·�ߴ��������
 *
 * ��getRouteNodes��ء�
 * ע�⣺����������ҵ���ʼ�ڵ㣬�������á�
 * 1.�˲���ÿ�α���ʱ��������Ϊ������µ���ʼ�ڵ�������ʼ�ڵ�ǰ�����ʼ�ڵ㡣
 * 2.����͸��´�������ڲ鿴���٣����Դ˲��������ڲ鿴ʱ����
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
    //����·�ߡ�
   // System.out.println("1");
    RouteItem routeItem = (RouteItem) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
    //����Ǹ���·�ߣ�ɾ����֦��
  //  System.out.println("2");
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) routeItem.getObject();
    if (PersistHelper.isPersistent(routeInfo)) {
      if (TechnicsRouteServiceEJB.VERBOSE) {
        System.out.println("RouteListHandler : routeID = " + routeInfo.getBsoID());
      }
      routeService.deleteBranchRelavent(routeInfo.getBsoID());
    }
    handleRoute(listLinkInfo, routeItem);
    //����ڵ㡣
    Collection node = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("��ʼ���룺 handleRouteItem(node);");
    }
    handleRouteItem(node);
    //����ڵ������
    Collection nodelink = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("��ʼ���룺 handleRouteItem(nodelink);");
    }
    handleRouteItem(nodelink);

    //�����֦��
    Collection branch = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("��ʼ���룺 handleRouteItem(branch);");
    }
    handleRouteItem(branch);
    //�����֦������
    Collection branchNodeLink = (Collection) routeRelaventObejts.get(
        TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("��ʼ���룺 handleRouteItem(branchNodeLink);");
    }
    handleRouteItem(branchNodeLink);
  }

  private static void handleRoute(ListRoutePartLinkIfc listLinkInfo,
                                  RouteItem routeItem) throws QMException {
    if (TechnicsRouteServiceEJB.VERBOSE) {
      System.out.println("enter the SaveRouteHandler:handleRoute():110 row");
    }
    if (routeItem.getState().equalsIgnoreCase(RouteItem.DELETE)) {
      throw new TechnicsRouteException("ɾ��·����ר�÷��������ڴ˴�����");
    }
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) routeItem.getObject();
    TechnicsRouteService routeService = RouteHelper.getRouteService();
    //���»򴴽�����·�ߡ�
//  CCBegin by leixiao 2008-9-4 ԭ�򣺽����������·�ߣ�����·��˵����Ϣ�д洢��ǰ����汾  090108�޸Ĵ�IBAȡ���з����汾 
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
//  CCEnd by leixiao 2008-9-4 ԭ�򣺽����������·��
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
          throw new TechnicsRouteException("ɾ��������û��bsoid");
        }
        /*try
                         {
            pservice.deleteValueInfo(info);
                         }
                         catch(QMException e)
                         {
            //System.out.println("################���쳣���������󣬲�Ӱ��ϵͳ�������С�");
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
  
  
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
  protected static void impoartDoSave(ListRoutePartLinkIfc listLinkInfo,
          HashMap routeRelaventObejts) throws
QMException {
PersistService service = (PersistService) EJBServiceHelper.
getPersistService();
String routeid = listLinkInfo.getRouteID();
TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) service.refreshInfo(routeid);
/////////////////quxg add for jiefang,����㲿���Ѵ��ڵ�·�ߺ�Ҫ�����·����ͬ�����ٵ���////////////////////////////////////////////////
TechnicsRouteService routeService = RouteHelper.getRouteService();
Map map = (Map) routeService.getRouteBranchs(routeid);
Iterator values = map.values().iterator();
String makeStr = "";
String beingImportMakeStr = "";//����·�ߺ�װ��·�ߴ���
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
//����ڵ㡣
Collection node = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
importHandleRouteItem(node, routeInfo);
//����ڵ������
Collection nodelink = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
importHandleRouteItem(nodelink, routeInfo);

//�����֦��
Collection branch = (Collection) routeRelaventObejts.get(
TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
importHandleRouteItem(branch, routeInfo);
//�����֦������
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
throw new TechnicsRouteException("ɾ��������û��bsoid");
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
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
}
