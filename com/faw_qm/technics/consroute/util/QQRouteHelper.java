package com.faw_qm.technics.consroute.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import java.io.Serializable;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.framework.service.*;
import com.faw_qm.technics.consroute.ejb.service.*;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.ejb.service.CodingManageService;

import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserInfo;

import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.framework.exceptions.*;

import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.project.util.RolePrincipalTable;

import com.faw_qm.part.model.*;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.cappclients.conscapproute.web.ViewRouteListPartsUtil;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.framework.remote.RemoteProperty;

import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import org.apache.poi.hssf.usermodel.*;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.part.ejb.enterpriseService.*;
/**
 * <p>Title: </p>
 * <p>Description: 工艺路线服务端类</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @author 狄科峰
 * @version 1.0
 */

public class QQRouteHelper
    implements Serializable {
  private static final String ROUTE_SERVICE = "consTechnicsRouteService";
  private static final String routesearchresult = RemoteProperty.getProperty(
      "routesearchresult",
      "/qmcappapp/qmcapp/phosphor/support/loadFiles/xml/routesearchresult/");
  private static final String allpartsearchresult = RemoteProperty.getProperty(
      "allpartsearchresult",
      "/qmcappapp/qmcapp/phosphor/support/loadFiles/xml/allpartsearchresult/");
  private static TechnicsRouteService trService = null;
  public QQRouteHelper() {
  }

  public static void setRouteEffective(BaseValueIfc routelist) throws
      QMException {
    try {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      if (trService == null) {
        trService = (TechnicsRouteService) EJBServiceHelper.
            getService("consTechnicsRouteService");
      }
      TechnicsRouteListInfo routelistinfo = (TechnicsRouteListInfo) routelist;
      Collection col = trService.getRouteListLinkParts(routelistinfo);
      Iterator ite = col.iterator();
      QMQuery query;
      while (ite.hasNext()) {
        ListRoutePartLinkInfo lrplxin = (ListRoutePartLinkInfo) ite.next();
        query = new QMQuery("consListRoutePartLink");
        QueryCondition qc1 = new QueryCondition("rightBsoID", "=",
                                                lrplxin.getRightBsoID());
        QueryCondition qc2 = new QueryCondition("releaseIdenty","=",1);
        query.addCondition(qc1);
        query.addAND();
        query.addCondition(qc2);
        Collection coljiu = pService.findValueInfo(query);
        Iterator itejiu = coljiu.iterator();
        String jiuid = "";
        while (itejiu.hasNext()) {
          ListRoutePartLinkInfo lrpljiu = (ListRoutePartLinkInfo) itejiu.next();
          lrpljiu.setReleaseIdenty(0);
          jiuid = lrpljiu.getBsoID();
          pService.saveValueInfo(lrpljiu);
        }
        lrplxin = (ListRoutePartLinkInfo)pService.refreshInfo(lrplxin.getBsoID());
        lrplxin.setLastEffRoute(jiuid);
        lrplxin.setReleaseIdenty(1);
        pService.saveValueInfo(lrplxin);
      }
    }
    catch (QMException e) {
      e.printStackTrace();
    }

  }

  public static void setState(BaseValueIfc pbo, String state) throws
      QMException {
    try {
      StandardPartService spService = (StandardPartService) EJBServiceHelper.
          getService("StandardPartService");
      QMPartIfc change = (QMPartIfc) pbo;
      Collection linkPart = spService.getAllSubParts(change);
      Iterator linkpartite = linkPart.iterator();
      setPartState(change, state);
      while (linkpartite.hasNext()) {
        QMPartIfc part = (QMPartIfc) linkpartite.next();
        setPartState(part, state);
      }

    }
    catch (QMException e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据传入的状态设置零件的状态,如果当前零件的状态比传入的状态靠前,则不设置
   * @param bsoid String
   * @return Vector
   */
  public static void setPartState(QMPartIfc part, String state) throws
      QMException {
    try {
      LifeCycleService lcService = (LifeCycleService) EJBServiceHelper.
          getService("LifeCycleService");
      String curState = part.getLifeCycleState().toString();
      if (curState.equalsIgnoreCase(state)) {
        return;
      }
      if (curState.equalsIgnoreCase("SHIZHI")) {
        lcService.setLifeCycleState(part, LifeCycleState.toLifeCycleState(state));
      }
      if (curState.equalsIgnoreCase("GONGYIZHUNBEI") &&
          !state.equalsIgnoreCase("SHIZHI")) {
        lcService.setLifeCycleState(part, LifeCycleState.toLifeCycleState(state));
      }
    }
    catch (QMException e) {
      e.printStackTrace();
    }
  }

  public static void changeState(BaseValueIfc pbo) throws QMException {
    try {
      StandardPartService spService = (StandardPartService) EJBServiceHelper.
          getService("StandardPartService");
      LifeCycleService lcService = (LifeCycleService) EJBServiceHelper.
          getService("LifeCycleService");
      QMPartIfc change = (QMPartIfc) pbo;
      Collection linkPart = spService.getAllSubParts(change);
      Iterator linkpartite = linkPart.iterator();
      String pdmstate = getIBAValue(change, "part_state");
      if (pdmstate != null && pdmstate.trim().length() != 0) {
          //CCBegin by liuzhicheng 2012-01-01 原因：青气升级成都兼容问题   
        lcService.setLifeCycleState(change, LifeCycleState.toLifeCycleState(pdmstate), false);
      }
      while (linkpartite.hasNext()) {
        QMPartIfc part = (QMPartIfc) linkpartite.next();
        String pdmsubstate = getIBAValue(part, "part_state");
        if (pdmsubstate != null && pdmsubstate.trim().length() != 0) {
          lcService.setLifeCycleState(part, LifeCycleState.toLifeCycleState(pdmsubstate));
          //CCEnd by liuzhicheng 2012-01-01 原因：青气升级成都兼容问题   
        }
      }

    }
    catch (QMException e) {
      e.printStackTrace();
    }
  }

  /**
   * 得到IBA属性的值
   * @param holder IBAHolderIfc
   * @param attrName String
   * @return String
   */
  private static String getIBAValue(IBAHolderIfc holder, String attrName) {
    try {
      String attObj = null;
      IBAValueService is = (IBAValueService) EJBServiceHelper.
          getService("IBAValueService");
      HashMap hashMap = is.getIBADefinitionAndValues(holder);
      ArrayList list = (ArrayList) hashMap.get(attrName);
      if (list == null) {
        return null;
      }
      for (int i = 0; i < list.size(); i++) {
        attObj = (String) list.get(i);
      }
      return attObj;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * 通过零件的id获得零件的当前路线
   * @param partBsoID String
   * @throws QMException
   * @return String
   */
  public String[] getCurrentRouteByPartId(String partBsoID) throws QMException {
    String[] route = new String[6];
    try {
      PersistService pService = (PersistService) EJBServiceHelper.
          getPersistService();
      ViewRouteListPartsUtil vrlputil = new ViewRouteListPartsUtil();
      QMPartIfc part = (QMPartIfc) pService.refreshInfo(partBsoID, false);
      ListRoutePartLinkInfo lrpli = getCurrentRouteLinkByPart(part);
      if (lrpli == null) {
    	
        return route;
      }

      if (lrpli.getRouteID() != null && lrpli.getRouteID().length() > 0) {

        route[0] = lrpli.getBsoID();
       
        String[] linshi = vrlputil.getRouteBranches2(lrpli.getRouteID());
        route[1] = linshi[0];
        route[4] = linshi[1];
        TechnicsRouteInfo routeinfo = (TechnicsRouteInfo) pService.
            refreshInfo(lrpli.getRouteID(), false);
        route[5]=routeinfo.getRouteDescription();

      }
      if (lrpli.getLastEffRoute() != null &&
          lrpli.getLastEffRoute().length() > 0) {
        route[2] = lrpli.getLastEffRoute();
        ListRoutePartLinkInfo lastroutepartlink = null;
        try {
          lastroutepartlink = (ListRoutePartLinkInfo)
              pService.refreshInfo(lrpli.getLastEffRoute());
        }
        catch (Exception a) {
          a.printStackTrace();
          lastroutepartlink = null;
        }
        if (lastroutepartlink != null) {
          route[3] = vrlputil.getRouteBranches(lastroutepartlink.getRouteID());
        }
      }

    }
    catch (QMException e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return route;
  }

  public ListRoutePartLinkInfo getCurrentRouteLinkByPart(QMPartIfc part) throws
      QMException {
    ListRoutePartLinkInfo lrpli = null;
    try {
      PersistService pService = (PersistService) EJBServiceHelper.
          getPersistService();
      QMQuery query = new QMQuery("consListRoutePartLink");
      QueryCondition qc = new QueryCondition("rightBsoID", "=",
                                             part.getBsoID());
      query.addCondition(qc);
      QueryCondition qc1 = new QueryCondition("releaseIdenty","=",1);
      query.addAND();
      query.addCondition(qc1);
      Collection col = pService.findValueInfo(query, false);
      if (col == null || col.size() == 0) {
        return null;
      }
      Iterator ite = col.iterator();
      if (ite.hasNext()) {
        lrpli = (ListRoutePartLinkInfo) ite.next();
      }

    }
    catch (QMException e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return lrpli;
  }
  

  public void addAcceptorByPartRoute(QMPartInfo part, WfProcessIfc process,
                                     String acceptor) throws
      QMException {
    try {
      PersistService pService = (PersistService) EJBServiceHelper.
          getPersistService();
      if (trService == null) {
        trService = (TechnicsRouteService) EJBServiceHelper.getService(
            "consTechnicsRouteService");
      }
      UsersService uService = (UsersService) EJBServiceHelper.getService(
          "UsersService");
      QMQuery query = new QMQuery("consListRoutePartLink");
      QueryCondition qc = new QueryCondition("rightBsoID", "=",
                                             part.getBsoID());
      query.addCondition(qc);
      QueryCondition qc1 = new QueryCondition("releaseIdenty", "=",1);
      query.addAND();
      query.addCondition(qc1);
      Collection col = pService.findValueInfo(query);
      Iterator ite = col.iterator();
      Vector userVector = new Vector();
      String identifyString = "";
      while (ite.hasNext()) {
        //当前有效路线的
        ListRoutePartLinkInfo lrpl = (ListRoutePartLinkInfo) ite.next();
        QMQuery querybranch = new QMQuery("RouteNode");
        QueryCondition qcbranch = new QueryCondition("routeID", "=",
            lrpl.getRouteID());
        querybranch.addCondition(qcbranch);
        Collection colbranch = pService.findValueInfo(querybranch);
        if (lrpl.getLastEffRoute() != null &&
            lrpl.getLastEffRoute().length() > 0) {
          ListRoutePartLinkInfo lastefflink = (ListRoutePartLinkInfo) pService.
              refreshInfo(lrpl.getLastEffRoute());
          querybranch = new QMQuery("RouteNode");
          qcbranch = new QueryCondition("routeID", "=", lastefflink.getRouteID());
          querybranch.addCondition(qcbranch);
          Collection lastEffCol = pService.findValueInfo(querybranch);
          colbranch.addAll(lastEffCol);
        }
        Iterator nodeite= colbranch.iterator();
          while (nodeite.hasNext()) {
            RouteNodeInfo routeNode = (RouteNodeInfo) nodeite.next();
            if (identifyString.indexOf(routeNode.getNodeDepartment() + ";") >=
                0) {
              continue;
            }
            identifyString = identifyString + routeNode.getNodeDepartment() +
                ";";
            BaseValueIfc base = (BaseValueIfc) pService.refreshInfo(routeNode.
                getNodeDepartment());
            if (base instanceof CodingIfc) {
              CodingIfc coding = (CodingIfc) base;
              String acceptorName = coding.getSearchWord();
              Enumeration enu = uService.findLikeGroup(acceptorName);
              if (enu.hasMoreElements()) {
                GroupInfo group = (GroupInfo) enu.nextElement();
                Enumeration en = uService.groupMembers(group, true);
                while (en.hasMoreElements()) {
                  UserInfo in = (UserInfo) en.nextElement();
                  userVector.add(in.getBsoID());
                }
                //userVector.add(group.getBsoID());
              }
            }
            else {
              CodingClassificationIfc codingclass = (CodingClassificationIfc)
                  base;
              if (codingclass.getCodeNote().indexOf("$$$") > 0) {
                int i = codingclass.getCodeNote().indexOf("$$$");
                String acceptorName = codingclass.getCodeNote().substring(i + 3);
                //System.out.println("in the classification the acceptor group name="+acceptorName);
                Enumeration enu = uService.findLikeGroup(acceptorName);
                if (enu.hasMoreElements()) {
                  GroupInfo group = (GroupInfo) enu.nextElement();
                  Enumeration en = uService.groupMembers(group, true);
                  while (en.hasMoreElements()) {
                    UserInfo in = (UserInfo) en.nextElement();
                    userVector.add(in.getBsoID());
                  }
                  //userVector.add(group.getBsoID());
                }
              }
            }
          }
      }
      process = (WfProcessIfc) pService.refreshInfo(process.getBsoID());
      RolePrincipalTable table1 = (RolePrincipalTable) process.
          getRolePrincipalMap();
      if (table1 == null) {
        table1 = new RolePrincipalTable();
      }
      Vector olduser = (Vector) table1.get(acceptor);
      if (olduser != null && olduser.size() > 0) {
        for (int i = 0; i < olduser.size(); i++) {
          String oldid = (String) olduser.get(i);
          boolean flag = true;
          for (int j = 0; j < userVector.size(); j++) {
            String newid = (String) userVector.get(j);
            if (newid.equalsIgnoreCase(oldid)) {
              flag = false;
              break;
            }
          }
          if (flag) {
            userVector.add(oldid);
          }
        }
      }
      table1.put(acceptor, userVector);
      process.setRolePrincipalMap(table1);
      pService.saveValueInfo(process);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e);
    }
  }

  /**
   * 设置流程中艺准通知书的接收者
   * @param primaryObject BaseValueIfc
   * @param self BaseValueIfc
   * @throws QMException
   */
  public void setRouteListAcceptor(BaseValueIfc primaryObject,
                                   BaseValueIfc self) throws QMException {
    try {
      PersistService pService = (PersistService) EJBServiceHelper.
          getPersistService();
      if (trService == null) {
        trService = (TechnicsRouteService) EJBServiceHelper.
            getService("consTechnicsRouteService");
      }
      UsersService uService = (UsersService) EJBServiceHelper.getService(
          "UsersService");
      //tangjinyu20110111 获得配置文件内容
      String specialacceptor = RemoteProperty.getProperty(
          "com.faw_qm.qq.specialRouteAcceptor", "");
      String[][] specialacceptorA=null;
      if(specialacceptor!=null&&specialacceptor.length()>0){
        StringTokenizer tok=new StringTokenizer(specialacceptor,",");
        int count=tok.countTokens();
        int row=count/2;
        if (row * 2 != count) {
          System.out.println(
              "In setRouteListAcceptor : 注意，变量com.faw_qm.qq.specialRouteAcceptor中的数据不完整");
        }
        else {
          specialacceptorA = new String[row][2];
          int i = 0;
          while (tok.hasMoreTokens()) {
            specialacceptorA[i][0] = tok.nextToken().trim();
            specialacceptorA[i][1] = tok.nextToken().trim();
            i++;
          }
        }
      }
      //end tangjinyu20110111
      TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) primaryObject;
      String creator = routelist.getCreator();
      QMQuery query = new QMQuery("consListRoutePartLink");
      QueryCondition qc = new QueryCondition("leftBsoID", "=",
                                             routelist.getBsoID());
      query.addCondition(qc);
      Collection col = pService.findValueInfo(query);
      //System.out.println("查询语句："+query.toString());
      //System.out.println("获得的集合的长度为"+col.size());
      Iterator ite = col.iterator();
      Iterator nodeite = null;
      String identifyString = "";
      Vector userVector = new Vector();
      //这一级的循环获得的是对应于路线分支的循环
      while (ite.hasNext()) {
        ListRoutePartLinkInfo lrpl = (ListRoutePartLinkInfo) ite.next();
        QMQuery query1 = new QMQuery("RouteNode");
        QueryCondition qc1 = new QueryCondition("routeID", "=", lrpl.getRouteID());
        query1.addCondition(qc1);
        Collection colbranch = pService.findValueInfo(query1);
        //
        query1 = new QMQuery("RouteNode");
        String effroute = lrpl.getLastEffRoute();
        ListRoutePartLinkInfo lastefflink =null;
        if (effroute != null) {
          lastefflink = (ListRoutePartLinkInfo) pService.
              refreshInfo(effroute);
          qc1 = new QueryCondition("routeID", "=", lastefflink.getRouteID());
          query1.addCondition(qc1);
          Collection lastEffCol = pService.findValueInfo(query1);
          //将两次获得的路线的分支合并在一起
          colbranch.addAll(lastEffCol);
        }
          nodeite = colbranch.iterator();
          //这一级的循环对每一个分支中的节点进行循环，也就是对于每一个节点作：
          while (nodeite.hasNext()) {
            RouteNodeInfo routeNode = (RouteNodeInfo) nodeite.next();
            //如果这个代码项已经遍历过则不在遍历
            if (identifyString.indexOf(routeNode.getNodeDepartment() + ";") >=
                0) {
              continue;
            }
            identifyString = identifyString + routeNode.getNodeDepartment() +
                ";";
            BaseValueIfc base = (BaseValueIfc) pService.refreshInfo(routeNode.
                getNodeDepartment());
            if (base instanceof CodingIfc) {
              CodingIfc coding = (CodingIfc) base;
              String acceptorName = coding.getSearchWord();
              Enumeration enu = uService.findLikeGroup(acceptorName);
              if (enu.hasMoreElements()) {
                GroupInfo group = (GroupInfo) enu.nextElement();
                Enumeration en = uService.groupMembers(group, true);
                while (en.hasMoreElements()) {
                  UserInfo in = (UserInfo) en.nextElement();
                  if (!userVector.contains(in.getBsoID())) {
                    userVector.add(in.getBsoID());
                  }
                }
              }
            }
            else {
              CodingClassificationIfc codingclass = (CodingClassificationIfc)
                  base;
              if (codingclass.getCodeNote().indexOf("$$$") > 0) {
                int i = codingclass.getCodeNote().indexOf("$$$");
                String acceptorName = codingclass.getCodeNote().substring(i + 3);
                Enumeration enu = uService.findLikeGroup(acceptorName);
                if (enu.hasMoreElements()) {
                  GroupInfo group = (GroupInfo) enu.nextElement();
                  Enumeration en = uService.groupMembers(group, true);
                  while (en.hasMoreElements()) {
                    UserInfo in = (UserInfo) en.nextElement();
                    if (!userVector.contains(in.getBsoID())) {
                      userVector.add(in.getBsoID());
                    }
                  }
                }
              }
            }
          }
          //tangjinyu 20110106 路线自动接收者特殊要求
          //根据某路线串组合确定需要发到某车间的接收人员。 如‘料-生’路线需要冲压车间路线接收人员接收。路线串和车间可配置。
         //获得当前零件路线串
         if(specialacceptorA!=null&&specialacceptorA.length>0){
          String[] routeall=ViewRouteListPartsUtil.getRouteBranches2(lrpl.getRouteID());
          String routestr = "";
          if (routeall[0] != null) {
            routestr = routestr + ";" + routeall[0];
          }
          if (routeall[1] != null) {
            routestr = routestr + ";" + routeall[1];
          }
          if(lastefflink!=null){
            String lastroutestr = ViewRouteListPartsUtil.getRouteBranches(lrpl.
                getRouteID());
            routestr=routestr+";"+lastroutestr;
          }
          for(int i=0;i<specialacceptorA.length;i++){
            if(routestr.indexOf(specialacceptorA[i][0])>=0){
              Enumeration enu = uService.findLikeGroup(specialacceptorA[i][1]);
              if (enu.hasMoreElements()) {
                GroupInfo group = (GroupInfo) enu.nextElement();
                Enumeration en = uService.groupMembers(group, true);
                while (en.hasMoreElements()) {
                  UserInfo in = (UserInfo) en.nextElement();
                  if (!userVector.contains(in.getBsoID())) {
                    userVector.add(in.getBsoID());
                  }
                }
              }

            }
          }
         }
          //end tangjinyu 20110106

      }
      userVector.add(creator);
      //剔除重复用户名。
      WfProcessIfc process = (WfProcessIfc) self;
      process = (WfProcessIfc) pService.refreshInfo(process.getBsoID());
      RolePrincipalTable table1 = (RolePrincipalTable) process.
          getRolePrincipalMap();
      if (table1 == null) {
        table1 = new RolePrincipalTable();
      }
      Vector olduser = (Vector) table1.get("ACCEPT");
      if (olduser != null && olduser.size() > 0) {
        for (int i = 0; i < olduser.size(); i++) {
          String oldid = (String) olduser.get(i);
          if (!userVector.contains(oldid)) {
            userVector.add(oldid);
          }
        }
      }
    //tang 20060719 把‘固定路线接收组’里的用户加进来。同时需要在流程中的接收活动中去掉

    Enumeration gudingenu =  uService.findLikeGroup("路线固定接收组");
    if (gudingenu.hasMoreElements()) {
      GroupInfo gudinggroup = (GroupInfo) gudingenu.nextElement();
      Enumeration gudingen = uService.groupMembers(gudinggroup, true);
      while (gudingen.hasMoreElements()) {
        UserInfo in = (UserInfo) gudingen.nextElement();
        if (!userVector.contains(in.getBsoID()))
          userVector.add(in.getBsoID());
      }
    }
    //end tang
      if (routelist.getRouteListState().trim().equalsIgnoreCase("艺准")) {
        Enumeration enu = uService.findLikeGroup("艺准图纸发放组");
        if (enu.hasMoreElements()) {
          GroupInfo group = (GroupInfo) enu.nextElement();
          Enumeration en = uService.groupMembers(group, true);
          while (en.hasMoreElements()) {
            UserInfo in = (UserInfo) en.nextElement();
            if (!userVector.contains(in.getBsoID())) {
              userVector.add(in.getBsoID());
            }
          }
          //userVector.add(group.getBsoID());
        }
      }
      if (routelist.getRouteListState().trim().equalsIgnoreCase("试制")) {
        Enumeration enu = uService.findLikeGroup("试制图纸发放组");
        if (enu.hasMoreElements()) {
          GroupInfo group = (GroupInfo) enu.nextElement();
          Enumeration en = uService.groupMembers(group, true);
          while (en.hasMoreElements()) {
            UserInfo in = (UserInfo) en.nextElement();
            if (!userVector.contains(in.getBsoID())) {
              userVector.add(in.getBsoID());
            }
          }
          //userVector.add(group.getBsoID());
        }
      }
      if (routelist.getRouteListState().trim().equalsIgnoreCase("临准")) {
        Enumeration enu = uService.findLikeGroup("临准图纸发放组");
        if (enu.hasMoreElements()) {
          GroupInfo group = (GroupInfo) enu.nextElement();
          Enumeration en = uService.groupMembers(group, true);
          while (en.hasMoreElements()) {
            UserInfo in = (UserInfo) en.nextElement();
            if (!userVector.contains(in.getBsoID())) {
              userVector.add(in.getBsoID());
            }
          }
          //userVector.add(group.getBsoID());
        }
      }
      if (routelist.getRouteListState().trim().equalsIgnoreCase("试准")) {
        Enumeration enu = uService.findLikeGroup("试准图纸发放组");
        if (enu.hasMoreElements()) {
          GroupInfo group = (GroupInfo) enu.nextElement();
          Enumeration en = uService.groupMembers(group, true);
          while (en.hasMoreElements()) {
            UserInfo in = (UserInfo) en.nextElement();
            if (!userVector.contains(in.getBsoID())) {
              userVector.add(in.getBsoID());
            }
          }
          //userVector.add(group.getBsoID());
        }
      }

      //tang 20061116 显示艺准及艺准接受者ID
      System.out.println("艺准的接受者ID－－艺准编号："+routelist.getRouteListNumber());
      System.out.println("接受者ID:"+userVector);
      table1.put("ACCEPT", userVector);
      process.setRolePrincipalMap(table1);
      pService.saveValueInfo(process);

    }
    catch (QMException e) {
      e.printStackTrace();
      throw new QMException(e);
    }
  }
 /**
     * 设置流程中艺准通知书的接收者
     * @param primaryObject BaseValueIfc
     * @param self BaseValueIfc
     * @throws QMException
     */
    public void setRouteListAcceptor_ForComplete(BaseValueIfc primaryObject,
                                     BaseValueIfc self) throws QMException {
      try {
        PersistService pService = (PersistService) EJBServiceHelper.
            getPersistService();
        if (trService == null) {
          trService = (TechnicsRouteService) EJBServiceHelper.
              getService("consTechnicsRouteService");
        }
        UsersService uService = (UsersService) EJBServiceHelper.getService(
            "UsersService");
        TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) primaryObject;
        //String creator = routelist.getCreator();
        QMQuery query = new QMQuery("consListRoutePartLink");
        QueryCondition qc = new QueryCondition("leftBsoID", "=",
                                               routelist.getBsoID());
        query.addCondition(qc);
        Collection col = pService.findValueInfo(query);
        //System.out.println("查询语句："+query.toString());
        //System.out.println("获得的集合的长度为"+col.size());
        Iterator ite = col.iterator();
        Iterator nodeite = null;
        String identifyString = "";
        Vector userVector = new Vector();
        //这一级的循环获得的是对应于路线分支的循环
        while (ite.hasNext()) {
          ListRoutePartLinkInfo lrpl = (ListRoutePartLinkInfo) ite.next();
          QMQuery query1 = new QMQuery("RouteNode");
          QueryCondition qc1 = new QueryCondition("routeID", "=", lrpl.getRouteID());
          query1.addCondition(qc1);
          Collection colbranch = pService.findValueInfo(query1);
          //
          query1 = new QMQuery("RouteNode");
          String effroute = lrpl.getLastEffRoute();
          if (effroute != null) {
            ListRoutePartLinkInfo lastefflink = (ListRoutePartLinkInfo) pService.
                refreshInfo(effroute);
            qc1 = new QueryCondition("routeID", "=", lastefflink.getRouteID());
            query1.addCondition(qc1);
            Collection lastEffCol = pService.findValueInfo(query1);
            //将两次获得的路线的分支合并在一起
            colbranch.addAll(lastEffCol);
          }
            nodeite = colbranch.iterator();
            //这一级的循环对每一个分支中的节点进行循环，也就是对于每一个节点作：
            while (nodeite.hasNext()) {
              RouteNodeInfo routeNode = (RouteNodeInfo) nodeite.next();
              //如果这个代码项已经遍历过则不在遍历
              if (identifyString.indexOf(routeNode.getNodeDepartment() + ";") >=
                  0) {
                continue;
              }
              identifyString = identifyString + routeNode.getNodeDepartment() +
                  ";";
              BaseValueIfc base = (BaseValueIfc) pService.refreshInfo(routeNode.
                  getNodeDepartment());
              if (base instanceof CodingIfc) {
                CodingIfc coding = (CodingIfc) base;
                String acceptorName = coding.getSearchWord();
                Enumeration enu = uService.findLikeGroup(acceptorName);
                if (enu.hasMoreElements()) {
                  GroupInfo group = (GroupInfo) enu.nextElement();
                  Enumeration en = uService.groupMembers(group, true);
                  while (en.hasMoreElements()) {
                    UserInfo in = (UserInfo) en.nextElement();
                    if(!userVector.contains(in.getBsoID()))
                    userVector.add(in.getBsoID());
                  }
                }
              }
              else {
                CodingClassificationIfc codingclass = (CodingClassificationIfc)
                    base;
                if (codingclass.getCodeNote().indexOf("$$$") > 0) {
                  int i = codingclass.getCodeNote().indexOf("$$$");
                  String acceptorName = codingclass.getCodeNote().substring(i + 3);
                  Enumeration enu = uService.findLikeGroup(acceptorName);
                  if (enu.hasMoreElements()) {
                    GroupInfo group = (GroupInfo) enu.nextElement();
                    Enumeration en = uService.groupMembers(group, true);
                    while (en.hasMoreElements()) {
                      UserInfo in = (UserInfo) en.nextElement();
                      if(!userVector.contains(in.getBsoID()))
                      userVector.add(in.getBsoID());
                    }
                  }
                }
              }
            }
        }
        //userVector.add(creator);
        WfProcessIfc process = (WfProcessIfc) self;
        process = (WfProcessIfc) pService.refreshInfo(process.getBsoID());
        RolePrincipalTable table1 = (RolePrincipalTable) process.
            getRolePrincipalMap();
        if (table1 == null) {
          table1 = new RolePrincipalTable();
        }
        Vector olduser = (Vector) table1.get("ACCEPT");
        if (olduser != null && olduser.size() > 0) {
          for (int i = 0; i < olduser.size(); i++) {
            String oldid = (String) olduser.get(i);
            if(!userVector.contains(oldid))
              userVector.add(oldid);
          }
        }
        if (routelist.getRouteListState().trim().equalsIgnoreCase("艺准")) {
          Enumeration enu = uService.findLikeGroup("艺准图纸发放组");
          if (enu.hasMoreElements()) {
            GroupInfo group = (GroupInfo) enu.nextElement();
            Enumeration en = uService.groupMembers(group, true);
            while (en.hasMoreElements()) {
              UserInfo in = (UserInfo) en.nextElement();
              if(!userVector.contains(in.getBsoID()))
              userVector.add(in.getBsoID());
            }
            //userVector.add(group.getBsoID());
          }
        }
        if (routelist.getRouteListState().trim().equalsIgnoreCase("试制")) {
          Enumeration enu = uService.findLikeGroup("试制图纸发放组");
          if (enu.hasMoreElements()) {
            GroupInfo group = (GroupInfo) enu.nextElement();
            Enumeration en = uService.groupMembers(group, true);
            while (en.hasMoreElements()) {
              UserInfo in = (UserInfo) en.nextElement();
              if(!userVector.contains(in.getBsoID()))
              userVector.add(in.getBsoID());
            }
            //userVector.add(group.getBsoID());
          }
        }
        if (routelist.getRouteListState().trim().equalsIgnoreCase("临准")) {
          Enumeration enu = uService.findLikeGroup("临准图纸发放组");
          if (enu.hasMoreElements()) {
            GroupInfo group = (GroupInfo) enu.nextElement();
            Enumeration en = uService.groupMembers(group, true);
            while (en.hasMoreElements()) {
              UserInfo in = (UserInfo) en.nextElement();
              if(!userVector.contains(in.getBsoID()))
              userVector.add(in.getBsoID());
            }
            //userVector.add(group.getBsoID());
          }
        }
        if (routelist.getRouteListState().trim().equalsIgnoreCase("试准")) {
          Enumeration enu = uService.findLikeGroup("试准图纸发放组");
          if (enu.hasMoreElements()) {
            GroupInfo group = (GroupInfo) enu.nextElement();
            Enumeration en = uService.groupMembers(group, true);
            while (en.hasMoreElements()) {
              UserInfo in = (UserInfo) en.nextElement();
              if(!userVector.contains(in.getBsoID()))
              userVector.add(in.getBsoID());
            }
            //userVector.add(group.getBsoID());
          }
        }

        table1.put("ACCEPT", userVector);
        process.setRolePrincipalMap(table1);
        pService.saveValueInfo(process);

      }
      catch (QMException e) {
        e.printStackTrace();
        throw new QMException(e);
      }
    }
  /**
   * 获得发往单位
   * @param routelist TechnicsRouteListInfo
   * @throws QMException
   * @return String
   */
  public String getPublishDepart(TechnicsRouteListInfo routelist) throws
      QMException {
    String result = "";
    try {
      PersistService pService = (PersistService) EJBServiceHelper.
          getPersistService();
      if (trService == null) {
        trService = (TechnicsRouteService) EJBServiceHelper.
            getService("consTechnicsRouteService");
        //------------------------------------
      }
      QMQuery query = new QMQuery("consListRoutePartLink");
      QueryCondition qc = new QueryCondition("leftBsoID", "=",
                                             routelist.getBsoID());
      query.addCondition(qc);
//获得路线表对应的所有的关联
      Collection col = pService.findValueInfo(query);
//System.out.println("查询语句："+query.toString());
//System.out.println("获得的集合的长度为"+col.size());
      Iterator ite = col.iterator();
      Iterator nodeite = null;
      String identifyString = "";
//Vector userVector = new Vector();
//这一级的循环获得的是对应于路线分支的循环
//对于每一个关联
      while (ite.hasNext()) {
        //获得当前有效路线
        ListRoutePartLinkInfo lrpl = (ListRoutePartLinkInfo) ite.next();
        QMQuery query1 = new QMQuery("TechnicsRouteBranch");
        QueryCondition qc1 = new QueryCondition("routeID", "=", lrpl.getRouteID());
        query1.addCondition(qc1);
        Collection colbranch = pService.findValueInfo(query1);
        //获得的是取消路线
        query1 = new QMQuery("TechnicsRouteBranch");
        String effroute = lrpl.getLastEffRoute();
        if (effroute != null) {
          ListRoutePartLinkInfo lastefflink = (ListRoutePartLinkInfo) pService.
              refreshInfo(effroute);
          qc1 = new QueryCondition("routeID", "=", lastefflink.getRouteID());
          query1.addCondition(qc1);
          Collection lastEffCol = pService.findValueInfo(query1);
          //将两次获得的路线的分支合并在一起
          colbranch.addAll(lastEffCol);
        }

        Iterator branchIte = colbranch.iterator();
        while (branchIte.hasNext()) {
          TechnicsRouteBranchInfo branch = (TechnicsRouteBranchInfo) branchIte.
              next();
          Collection nodecollection = trService.getBranchRouteNodes(branch);
          nodeite = nodecollection.iterator();
          //这一级的循环对每一个分支中的节点进行循环，也就是对于每一个节点作：
          while (nodeite.hasNext()) {
            RouteNodeInfo routeNode = (RouteNodeInfo) nodeite.next();
            //如果这个代码项已经遍历过则不在遍历
            if (identifyString.indexOf(routeNode.getNodeDepartment() + ";") >=
                0) {
              continue;
            }
            String depart = "";
            identifyString = identifyString + routeNode.getNodeDepartment() +
                ";";
            BaseValueIfc base = (BaseValueIfc) pService.refreshInfo(routeNode.
                getNodeDepartment());
            //如果是代码项
            if (base instanceof CodingIfc) {
              CodingIfc coding = (CodingIfc) base;
              CodingClassificationIfc codingClass =
                  (CodingClassificationIfc) pService.refreshInfo(coding.
                  getCodingClassification());
              depart = codingClass.getClassSort();
            }
            //对于代码分类
            else {
              CodingClassificationIfc codingclass = (CodingClassificationIfc)
                  base;
              depart = codingclass.getClassSort();
            }
            if ( (result.indexOf(depart) != -1) || depart.equals("生产") ||
                depart.equals("检") || depart.equals("川") || depart.equals("财务")) {}
            else {
              if (result.trim().length() == 0) {
                result = depart;
              }
              else {
                result = result + "，" + depart;
              }
            }
          }
        }
      }
      result = "生产，检，财务，川，" + result;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return result;

  }

  public String searchAllRouteByRouteDep(String routetype, String routedep) throws
      QMException {
    String result = "true";
    try {
      System.out.println("导入开始");
      long begintime = System.currentTimeMillis();
      PersistService pService = (PersistService) EJBServiceHelper.
          getPersistService();
      QMQuery query = new QMQuery("consListRoutePartLink");
      QueryCondition qc = new QueryCondition("releaseIdenty", "=",1);
      query.addCondition(qc);
      Collection col = pService.findValueInfo(query, false);
      System.out.println("here get all the route,route count is :" + col.size());
      Iterator ite = col.iterator();
      String filename = getFileName(routetype, routedep);

      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet("searchresult");
      HSSFRow row = sheet.createRow(0);
      //1
      HSSFCell cell = row.createCell( (short) 0);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("序号");
      //2
      cell = row.createCell( (short) 1);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("编号");
      //3
      cell = row.createCell( (short) 2);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("名称");
      //4
      cell = row.createCell( (short) 3);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("制造路线");
      //5
      cell = row.createCell( (short) 4);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("装配路线");
      //6
      cell = row.createCell( (short) 5);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("临时制造路线");
      //7
      cell = row.createCell( (short) 6);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("临时装配路线");
      //8
      cell = row.createCell( (short) 7);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("取消制造路线");
      //9
      cell = row.createCell( (short) 8);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("取消装配路线");
      //10
      cell = row.createCell( (short) 9);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("发布日期");
      //ViewRouteListUtil vrlu=new  ViewRouteListUtil();

      int i = 1;
      ListRoutePartLinkInfo lrpl = null;
      HashMap hash = null;
      QMPartMasterInfo partmaster = null;
      while (ite.hasNext()) {
        //System.out.println("here is:"+i);
        lrpl = (ListRoutePartLinkInfo) ite.next();
        if (lrpl.getRouteID() == null || lrpl.getRouteID().length() == 0) {
          continue;
        }
        hash = getRoutebyRouteID(lrpl.getRouteID());
        //如果不包含该路线单位,则继续
        if (!isIncludeTheRouteDep(routetype, routedep, hash)) {
          continue;
        }
        partmaster = (QMPartMasterInfo) pService.refreshInfo(
            lrpl.getRightBsoID(), false);
        row = sheet.createRow(i);
        //输出序号
        cell = row.createCell( (short) 0);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(i);
        //输出零件编号
        cell = row.createCell( (short) 1);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(partmaster.getPartNumber());
        //输出零件名称
        cell = row.createCell( (short) 2);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(partmaster.getPartName());

        //制造路线
        cell = row.createCell( (short) 3);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue( (String) hash.get("制造路线"));

        cell = row.createCell( (short) 4);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue( (String) hash.get("装配路线"));

        cell = row.createCell( (short) 5);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue( (String) hash.get("临时制造路线"));

        cell = row.createCell( (short) 6);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue( (String) hash.get("临时装配路线"));
        //发布时间
        cell = row.createCell( (short) 9);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        java.sql.Timestamp time = lrpl.getModifyTime();
        String year = (new Integer(time.getYear() + 1900)).toString();
        String month = (new Integer(time.getMonth() + 1)).toString();
        String date = (new Integer(time.getDate())).toString();
        String publishtime = year + "-" + month + "-" + date;
        cell.setCellValue(publishtime);
        if (lrpl.getLastEffRoute() != null &&
            lrpl.getLastEffRoute().length() > 0) {
          ListRoutePartLinkInfo listroutelink = (ListRoutePartLinkInfo)
              pService.refreshInfo(lrpl.getLastEffRoute());
          if (listroutelink.getRouteID() != null &&
              listroutelink.getRouteID().length() > 0) {
            HashMap oldhash = getRoutebyRouteID(listroutelink.getRouteID());
            cell = row.createCell( (short) 7);
            
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //cell.setCellValue((String)oldhash.get("装配路线"));
            String allroute = "";
            String makeroute = (String) oldhash.get("制造路线");
            String temproute = (String) oldhash.get("临时制造路线");
            if (makeroute.length() == 0) {
              allroute = temproute;
            }
            else {
              if (temproute.length() == 0) {
                allroute = makeroute;
              }
              else {
                allroute = makeroute + "/" + temproute;
              }
            }
            cell.setCellValue(allroute);

            cell = row.createCell( (short) 8);
            
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String allassemroute = "";
            String assemroute = (String) oldhash.get("装配路线");
            String tempassemroute = (String) oldhash.get("临时装配路线");
            if (assemroute.length() == 0) {
              allassemroute = tempassemroute;
            }
            else {
              if (tempassemroute.length() == 0) {
                allassemroute = assemroute;
              }
              else {
                allassemroute = assemroute + "/" + tempassemroute;
              }
            }
            cell.setCellValue(allassemroute);
          }
        }
        i++;
      }
      System.out.println("处理完成,正在生成文件");
      File file = new File(routesearchresult + filename);
      FileOutputStream fos = new FileOutputStream(file);
      wb.write(fos);
      fos.close();
      long endtime = System.currentTimeMillis();
      System.out.println("耗费的总时间为:" + (endtime - begintime));
    }
    catch (Exception e) {
      e.printStackTrace();
      return "false";
    }
    return result;
  }

  public HashMap getRoutebyRouteID(String routeid) throws QMException {
    HashMap hash = new HashMap();
    try {

      if (trService == null) {
        trService = (TechnicsRouteService) EJBServiceHelper.
            getService("consTechnicsRouteService");
      }
      HashMap map = (HashMap) trService.getRouteBranchs(routeid);
      String routeText = "";
      //装配路线串
      String assRouteText = "";
      String temproutetext = "";
      String tempassroutetext = "";
      Iterator values = map.values().iterator();
      //获得路线分支的方法,返回值是hashmap,值是一个数组,数组的第一个元素是vector
      while (values.hasNext()) {
        boolean isTemp = false;
        String makeStr = "";
        Object[] objs1 = (Object[]) values.next();
        Vector makeNodes = (Vector) objs1[0]; //制造节点
        RouteNodeInfo asseNode = (RouteNodeInfo) objs1[1]; //装配节点
        //首先判断装配结点是不是
        if (asseNode != null && asseNode.getIsTempRoute()) {
          isTemp = true;
        }
        if (makeNodes != null && makeNodes.size() > 0) {
          //对每一个制造节点做
          for (int m = 0; m < makeNodes.size(); m++) {
            RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
            if (node.getIsTempRoute()) {
              isTemp = true;
            }
            if (makeStr == "") {
              makeStr = makeStr + node.getNodeDepartmentName();
            }
            else {
              makeStr = makeStr + "-" + node.getNodeDepartmentName();
            }
          }
        }
        //如果是临时路线,则在制造路线旁边加上临时字样
        if (isTemp) {
          //makeStr = makeStr + "(临时)";
          if (makeStr == null || makeStr.equals("")) {
            makeStr = "";
          }
          if (!temproutetext.trim().equals("")) {
            temproutetext = temproutetext + "/" + makeStr;
          }
          else {
            temproutetext = temproutetext + makeStr;
          }

          if (asseNode != null) {
            if (!tempassroutetext.trim().equals("")) {
              tempassroutetext = tempassroutetext + "/" +
                  asseNode.getNodeDepartmentName();
            }
            else {
              tempassroutetext = tempassroutetext +
                  asseNode.getNodeDepartmentName();
            }
          }
          continue;
        }
        //如果制造路线为空
        if (makeStr == null || makeStr.equals("")) {
          makeStr = "";
        }
        //
        if (!routeText.trim().equals("")) {
          routeText = routeText + "/" + makeStr;
        }
        else {
          routeText = routeText + makeStr;
        }
        if (asseNode != null) {
          if (!assRouteText.trim().equals("")) {
            assRouteText = assRouteText + "/" +
                asseNode.getNodeDepartmentName();
          }
          else {
            assRouteText = assRouteText +
                asseNode.getNodeDepartmentName();
          }
        }
      }
      hash.put("制造路线", routeText);
      hash.put("装配路线", assRouteText);
      hash.put("临时制造路线", temproutetext);
      hash.put("临时装配路线", tempassroutetext);
      //hash.put("ALL",routeText+"/"+assRouteText+"/"+temproutetext+"/"+tempassroutetext+"/");

    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return hash;
  }

  private String getFileName(String routetype, String routedep) throws
      QMException {
    String filename = "";
    try {
      String year = (new Integer(Calendar.getInstance().get(Calendar.YEAR))).
          toString();
      String month = (new Integer(Calendar.getInstance().get(Calendar.MONTH) +
                                  1)).toString();
      String date = (new Integer(Calendar.getInstance().get(Calendar.DATE))).
          toString();
      filename = routetype + "_" + routedep + "_" + year + "-" + month + "-" +
          date + ".xls";

    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return filename;
  }

  private boolean isIncludeTheRouteDep(String routetype, String routedep,
                                       HashMap hash) throws QMException {
    boolean flag = false;
    try {
      if (routetype.equalsIgnoreCase("制造路线")) {
        String makeroute = (String) hash.get("制造路线");
        String tempmakeroute = (String) hash.get("临时制造路线");
        String temp = makeroute + "/" + tempmakeroute + "/";
        temp = temp.replace('/', '-');
        if (temp.indexOf(routedep + "-") > 0) {
          return true;
        }

      }
      if (routetype.equalsIgnoreCase("装配路线")) {
        String makeroute = (String) hash.get("装配路线");
        String tempmakeroute = (String) hash.get("临时装配路线");
        String temp = makeroute + "/" + tempmakeroute + "/";
        temp = temp.replace('/', '-');
        if (temp.indexOf(routedep + "-") > 0) {
          return true;
        }

      }
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return flag;
  }
  public String searchAllPartAndRoute(String partnumber, String partname,
                                      String routetype, String routedep) throws
      QMException {
    String result = "true";
    String comma = ",";
    String newline = "\n";
    try {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      QMQuery query = new QMQuery("QMPartMaster");
      int tableindex=query.appendBso("consListRoutePartLink",true);
      int partindex=query.appendBso("QMPart",false);
      if (partnumber != null && partnumber.length() > 0) {
        QueryCondition cond = null;
        if (partnumber.indexOf("*") == -1 && partnumber.indexOf("%") == -1) {
          cond = new QueryCondition("partNumber", QueryCondition.EQUAL,
                                    partnumber);
        }
        else {
          if (partnumber.indexOf("*") != -1) {
            partnumber = partnumber.replace('*', '%');
          }
          cond = new QueryCondition("partNumber", QueryCondition.LIKE,
                                    partnumber);
        }
        query.addCondition(cond);
      }
      if (partname != null && partname.length() > 0) {
        QueryCondition cond = null;
        if (partname.indexOf("*") == -1 && partname.indexOf("%") == -1) {
          cond = new QueryCondition("partName", QueryCondition.EQUAL, partname);
        }
        else {
          if (partname.indexOf("*") != -1) {
            partname = partname.replace('*', '%');
          }
          cond = new QueryCondition("partName", QueryCondition.LIKE, partname);
        }
        if (partnumber != null && partnumber.length() > 0) {
          query.addAND();
        }
        query.addCondition(cond);
      }
      QueryCondition connectCon=new QueryCondition("bsoID","rightBsoID");
      if(query.getConditionCount()>0)
      {
        query.addAND();
        query.addCondition(partindex,tableindex,connectCon);
      }
      QueryCondition  qclistroutepartlink = new QueryCondition("releaseIdenty","=",1);
      query.addAND();
      query.addCondition(tableindex,qclistroutepartlink);;
      
      
      QueryCondition  partandmaster = new QueryCondition("bsoID","masterBsoID");
      query.addAND();
      query.addCondition(0,partindex,partandmaster);;

      query.addOrderBy("partNumber");
      Collection col = pService.findValueInfo(query, false);
      if (col == null || col.size() == 0) {
        return "搜索结果为空，请修改搜索条件";
      }
      Iterator ite = col.iterator();

      //生成文件信息
      String aa = getFileNamebyuser();
      String filename=aa+".csv";
      StringBuffer datastream = new StringBuffer();
      //datastream.append("序号"+comma);
      datastream.append("编号"+comma);
      datastream.append("名称"+comma);
      //datastream.append("生命周期状态"+comma);
      datastream.append("制造路线"+comma);
      datastream.append("装配路线"+comma);
      datastream.append("临时制造路线"+comma);
      datastream.append("临时装配路线"+comma);
      datastream.append("更改说明"+comma);
      datastream.append("备注");
      datastream.append(newline);
      //int index = 1;
      while (ite.hasNext()) {
        //row = sheet.createRow(index);
        BaseValueIfc[] basevalueifc = (BaseValueIfc[])ite.next();
        QMPartMasterIfc partmaster = (QMPartMasterIfc) basevalueifc[0];
        ListRoutePartLinkInfo lrpli = (ListRoutePartLinkInfo)basevalueifc[1];
        if (partmaster.getPartName().indexOf("逻辑") >= 0 || partmaster.getPartName().indexOf("模块") >= 0||
            partmaster.getPartName().indexOf("虚拟件")>=0)
        {
        	continue;
        }
        //tang 20061204 在导出的文件中增加零件最新版本的生命周期状态。
        String[] infomation = new String[6];
        if (lrpli != null) {
          String routeid = lrpli.getRouteID();
          if (routeid != null && routeid.trim().length() > 0)
          infomation = getRouteInfomation(routeid);
        }
        if(routedep.trim().length()>0)
        {
          if(routetype.trim().equalsIgnoreCase("制造路线"))
          {
            if((infomation[0]==null||infomation[0].indexOf(routedep)<0)&&(infomation[2]==null||infomation[2].indexOf(routedep)<0))
            continue;
          }
          if(routetype.trim().equalsIgnoreCase("装配路线"))
          {
            if((infomation[1]==null||infomation[1].indexOf(routedep)<0)&&(infomation[3]==null||infomation[3].indexOf(routedep)<0))
            continue;
          }
        }
        //tip==null?"":tip;
        datastream.append(partmaster.getPartNumber()+comma);
        datastream.append(partmaster.getPartName().replaceAll("\"","")+comma);
        //datastream.append(lifeState+comma);
        datastream.append((infomation[0]==null?"":infomation[0])+comma);
        datastream.append((infomation[1]==null?"":infomation[1])+comma);
        datastream.append((infomation[2]==null?"":infomation[2])+comma);
        datastream.append((infomation[3]==null?"":infomation[3])+comma);
        datastream.append((infomation[4]==null?"":infomation[4])+comma);
        datastream.append((infomation[5]==null?"":infomation[5]));
        datastream.append(newline);
      }
      System.out.println("处理完成,正在生成文件");
      FileWriter fw = new FileWriter(allpartsearchresult + filename);
      fw.write(datastream.toString());
      fw.close();
    }
    catch (Exception e) {
      e.printStackTrace();
      return "false";
    }
    return result;
  }

  public String searchAllPartAndRoutetoExcel(String partnumber, String partname,
                                      String routetype, String routedep) throws
      QMException {
    String result = "true";
    try {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      QMQuery query = new QMQuery("QMPartMaster");
      if (partnumber != null && partnumber.length() > 0) {
        QueryCondition cond = null;
        if (partnumber.indexOf("*") == -1 && partnumber.indexOf("%") == -1) {
          cond = new QueryCondition("partNumber", QueryCondition.EQUAL,
                                    partnumber);
        }
        else {
          if (partnumber.indexOf("*") != -1) {
            partnumber = partnumber.replace('*', '%');
          }
          cond = new QueryCondition("partNumber", QueryCondition.LIKE,
                                    partnumber);
        }
        query.addCondition(cond);
      }
      if (partname != null && partname.length() > 0) {
        QueryCondition cond = null;
        if (partname.indexOf("*") == -1 && partname.indexOf("%") == -1) {
          cond = new QueryCondition("partName", QueryCondition.EQUAL, partname);
        }
        else {
          if (partname.indexOf("*") != -1) {
            partname = partname.replace('*', '%');
          }
          cond = new QueryCondition("partName", QueryCondition.LIKE, partname);
        }
        if (partnumber != null && partnumber.length() > 0) {
          query.addAND();
        }
        query.addCondition(cond);
      }
      Collection col = pService.findValueInfo(query, false);
      if (col == null || col.size() == 0) {
        return "搜索结果为空，请修改搜索条件";
      }
      Iterator ite = col.iterator();
      QMQuery listroutequery = null;
      QueryCondition qc1 = null;
      QueryCondition qc2 = null;
      //生成文件信息
      String filename = getFileNamebyuser()+".xls";
      //生成文件信息

      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet("searchresult");
      HSSFRow row = sheet.createRow(0);
      //1
      HSSFCell cell = row.createCell( (short) 0);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("序号");
      //2
      cell = row.createCell( (short) 1);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("编号");
      //3
      cell = row.createCell( (short) 2);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("名称");
      //4
      cell = row.createCell( (short) 3);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("制造路线");
      //5
      cell = row.createCell( (short) 4);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("装配路线");
      //6
      cell = row.createCell( (short) 5);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("临时制造路线");
      //7
      cell = row.createCell( (short) 6);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("临时装配路线");
      //8
      cell = row.createCell( (short) 7);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("更改说明");
      //9
      cell = row.createCell( (short) 8);
      
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("备注");

      int index = 1;
      while (ite.hasNext()) {
        //row = sheet.createRow(index);
        QMPartMasterIfc partmaster = (QMPartMasterIfc) ite.next();
        listroutequery = new QMQuery("consListRoutePartLink");
        qc1 = new QueryCondition("rightBsoID", QueryCondition.EQUAL,
                                 partmaster.getBsoID());
        qc2 = new QueryCondition("releaseIdenty","=",1);
        listroutequery.addCondition(qc1);
        listroutequery.addAND();
        listroutequery.addCondition(qc2);

        String[] infomation = new String[6];
        Collection listrouteres = pService.findValueInfo(listroutequery);
        if (listrouteres != null && listrouteres.size() > 0) {
          Iterator linshi = listrouteres.iterator();
          ListRoutePartLinkInfo lrpli = (ListRoutePartLinkInfo) linshi.next();
          String routeid = lrpli.getRouteID();
          if (routeid != null && routeid.trim().length() > 0)
          infomation = getRouteInfomation(routeid);
        }
        if(routedep.trim().length()>0)
        {
          if(routetype.trim().equalsIgnoreCase("制造路线"))
          {
            if((infomation[0]==null||infomation[0].indexOf(routedep)<0)&&(infomation[2]==null||infomation[2].indexOf(routedep)<0))
            continue;
          }
          if(routetype.trim().equalsIgnoreCase("装配路线"))
          {
            if((infomation[1]==null||infomation[1].indexOf(routedep)<0)&&(infomation[3]==null||infomation[3].indexOf(routedep)<0))
            continue;
          }
        }
        //写零件信息
        row = sheet.createRow(index);
        cell = row.createCell( (short) 0);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(index);
        //2
        cell = row.createCell( (short) 1);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(partmaster.getPartNumber());
        //3
        cell = row.createCell( (short) 2);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(partmaster.getPartName());
        //写零件信息

        //写路线信息
        cell = row.createCell( (short) 3);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(infomation[0]);

        cell = row.createCell( (short) 4);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(infomation[1]);

        cell = row.createCell( (short) 5);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(infomation[2]);

        cell = row.createCell( (short) 6);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(infomation[3]);

        cell = row.createCell( (short) 7);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(infomation[4]);

        cell = row.createCell( (short) 8);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(infomation[5]);
        //写路线信息
        index = index + 1;
      }
      System.out.println("处理完成,正在生成文件");
      File file1 = new File(allpartsearchresult + filename);
      FileOutputStream fos = new FileOutputStream(file1);
      wb.write(fos);
      fos.close();

    }
    catch (Exception e) {
      e.printStackTrace();
      return "false";
    }
    return result;
  }

  public String getFileNamebyuser() throws QMException {
    String filename = "";
    try {
      SessionService sService = (SessionService) EJBServiceHelper.getService(
          "SessionService");
      UserInfo uu = (UserInfo) sService.getCurUserInfo();

      String year = (new Integer(Calendar.getInstance().get(Calendar.YEAR))).
          toString();
      String month = (new Integer(Calendar.getInstance().get(Calendar.MONTH) +
                                  1)).toString();
      String date = (new Integer(Calendar.getInstance().get(Calendar.DATE))).
          toString();
      String hour=(new Integer(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))).toString();
      String minute=(new Integer(Calendar.getInstance().get(Calendar.MINUTE))).toString();
      filename = uu.getUsersDesc() + "_" + year + "-" + month + "-" +
          date+"-"+hour+"-"+minute;

    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e);
    }
    return filename;

  }

  public String[] getRouteInfomation(String routeid) throws QMException {
    PersistService pService = (PersistService) EJBServiceHelper.
        getPersistService();
    TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
        getService("consTechnicsRouteService");
    String[] att = new String[6];
    try {

      HashMap map = (HashMap) trService.getRouteBranchs(routeid);
      Iterator values = map.values().iterator();
      String routeText = "";
      String assRouteText = "";
      String temproutetext = "";
      String tempassroutetext = "";
      while (values.hasNext()) {
        boolean isTemp = false;
        String makeStr = "";
        Object[] objs1 = (Object[]) values.next();
        Vector makeNodes = (Vector) objs1[0]; //制造节点
        RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
        //首先判断装配结点是不是
        if (asseNode != null && asseNode.getIsTempRoute()) {
          isTemp = true;
        }
        if (makeNodes != null && makeNodes.size() > 0) {
          //对每一个制造节点做
          for (int m = 0; m < makeNodes.size(); m++) {
            RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
            if (node.getIsTempRoute()) {
              isTemp = true;
            }
            if (makeStr == "") {
              makeStr = makeStr + node.getNodeDepartmentName();
            }
            else {
              makeStr = makeStr + "-" + node.getNodeDepartmentName();
            }
          }
        }
        //如果是临时路线,则在制造路线旁边加上临时字样
        if (isTemp) {
          //makeStr = makeStr + "(临时)";
          if (makeStr == null || makeStr.equals("")) {
            makeStr = "";
          }
          if (!temproutetext.trim().equals("")) {
            temproutetext = temproutetext + "/" + makeStr;
          }
          else {
            temproutetext = temproutetext + makeStr;
          }

          if (asseNode != null) {
            if (!tempassroutetext.trim().equals("")) {
              tempassroutetext = tempassroutetext + "/" +
                  asseNode.getNodeDepartmentName();
            }
            else {
              tempassroutetext = tempassroutetext +
                  asseNode.getNodeDepartmentName();
            }
          }
          continue;
        }
        //如果制造路线为空
        if (makeStr == null || makeStr.equals("")) {
          makeStr = "";
        }
        //
        if (!routeText.trim().equals("")) {
          routeText = routeText + "/" + makeStr;
        }
        else {
          routeText = routeText + makeStr;
        }
        if (asseNode != null) {
          if (!assRouteText.trim().equals("")) {
            assRouteText = assRouteText + "/" +
                asseNode.getNodeDepartmentName();
          }
          else {
            assRouteText = assRouteText +
                asseNode.getNodeDepartmentName();
          }
        }
      }

      TechnicsRouteInfo route = (TechnicsRouteInfo) pService.
          refreshInfo(routeid, false);
      if(routeText!=null)
      att[0] = routeText;
      if(assRouteText!=null)
      att[1] = assRouteText;
      if(temproutetext!=null)
      att[2] = temproutetext;
      if(tempassroutetext!=null)
      att[3] = tempassroutetext;
      String modifyflag = route.getModifyIdenty();
      if(modifyflag!=null)
      att[4] = modifyflag;
      if(route.getRouteDescription()!=null)
      att[5] = route.getRouteDescription();

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return att;
  }

  public CodingClassificationIfc findClassificationByName(String name)
            throws
            QMException
    {

        Collection col = null;
        CodingClassificationIfc ifc=null;
        try
        {
            QMQuery query = new QMQuery("CodingClassification");
            QueryCondition cond = new QueryCondition("classSort", "=", name);
            query.addCondition(cond);
            PersistService service = (PersistService)
                                     EJBServiceHelper.getService(
                    "PersistService");

            col = service.findValueInfo(query,false);
        }
        catch (Exception e)
        {
            throw new QMException(e);
        }
        if(col!=null&&col.size()>0)
        {
          Iterator ite=col.iterator();
          ifc=(CodingClassificationIfc)ite.next();
        }
        return ifc;
    }

    public CodingIfc findCodingByName(String name)
            throws
            QMException
    {

        Collection col = null;
        CodingIfc cod=null;
        try
        {
            QMQuery query = new QMQuery("Coding");
            QueryCondition cond = new QueryCondition("shorten", "=", name);
            query.addCondition(cond);
            PersistService service = (PersistService)
                                     EJBServiceHelper.getService(
                    "PersistService");

            col = service.findValueInfo(query);
        }
        catch (Exception e)
        {
            throw new QMException(e);
        }
        if(col!=null&&col.size()>0)
        {
          Iterator ite=col.iterator();
          cod=(CodingIfc)ite.next();
        }
        return cod;
    }
//tang20061019 start for batch search
public Vector searchRouteByParts(String partlist){
  try{
    //返回值
    Vector returnVector=new Vector();
    if(partlist!=null&&partlist.length()>0){
    PersistService pService = (PersistService) EJBServiceHelper.getService(
        "PersistService");
    StringTokenizer st = new StringTokenizer(partlist, ";");
    QMQuery query = new QMQuery("QMPartMaster");
    QueryCondition cond = null;
    if(st.hasMoreTokens()){
    	String s = st.nextToken().toString().trim();
      cond = new QueryCondition("partNumber", QueryCondition.EQUAL,
                                s);
      query.addCondition(cond);
    }
    while(st.hasMoreTokens()){
      query.addOR();
      String s = st.nextToken().toString().trim();
      cond = new QueryCondition("partNumber", QueryCondition.EQUAL,
                                s);
      query.addCondition(cond);
    }
    query.addOrderBy("partNumber");
    Collection col=pService.findValueInfo(query);
    if (col == null || col.size() == 0) {
      return null;
    }
    Iterator ite = col.iterator();
    while (ite.hasNext()) {

        QMPartMasterIfc partmaster = (QMPartMasterIfc) ite.next();
        VersionControlService vcs = (VersionControlService)
            EJBServiceHelper.getService("VersionControlService");
        //获得master下的所有小版本，按最新版序排列,则第一个元素即为最新版本版序的零部件对象
        Collection col1 = vcs.allVersionsOf(partmaster);
        Iterator iterator1 = col1.iterator();
        if (iterator1.hasNext()) {
          QMPartInfo part=(QMPartInfo) iterator1.next();
          //route[0]为routeid，route[1]为路线字符串，route[3]为取消路线，route[4]为临时路线,route[5]为备注。
          String[] route= getCurrentRouteByPartId(part.getBsoID());
          if(route!=null&&route.length>0){
            //零件编号 零件名称 路线 取消路线 临时路线 备注
            String[] partroute=new String[6];
            partroute[0]=partmaster.getPartNumber();//零件编号
            partroute[1]=partmaster.getPartName();//零件名称   
            partroute[2]=route[1];//路线
            partroute[3]=route[3];//取消路线
            partroute[4]=route[4];//临时路线
            partroute[5]=route[5];//备注
            returnVector.add(partroute);
          }
        }
      }
    }
    return returnVector;
  }catch(Exception e){
    e.printStackTrace();
    return null;
  }
}

  public static String[][] getmodelroutelist()
  {
    try
    {
      long starttime = System.currentTimeMillis();
      System.out.println("查看典型路线");
      Connection conn = PersistUtil.getConnection();
      Statement select = conn.createStatement(1004, 1007);
      ResultSet result = select.executeQuery("select leftBsoID,rightBsoID from consModelRoute where leftBsoID not like '%QMPart%'");
      if (trService == null)
        trService = (TechnicsRouteService)
          EJBServiceHelper.getService("consTechnicsRouteService");

      PersistService ps = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      result.last();
      int totalrow = result.getRow();
      System.out.println("共有" + totalrow + "条数据！");
      result.first();
      String[][] modelroutelist = new String[totalrow][2];
      for (int i = 0; i < totalrow; ++i)
      {
        String partid = result.getString("leftBsoID");
        String routeid = result.getString("rightBsoID");
        if (routeid != null)
        {
        	if (routeid.trim().length() == 0)
            continue;
          String routeStr = trService.getRouteTextByRouteID(routeid);
          /*String partnum = "";
          if(partid.startsWith("QMPart_"))
          {
          	partnum = ((QMPartIfc)ps.refreshInfo(partid)).getPartNumber();
          }
          else if (partid.startsWith("QMPartMaster_"))
          {
          	partnum = ((QMPartMasterIfc)ps.refreshInfo(partid)).getPartNumber();
          }
          modelroutelist[i][0] = partnum;*/
          modelroutelist[i][0] = partid;
          modelroutelist[i][1] = routeStr;
          result.next(); }
      }
      result.close();
      select.close();
      conn.close();
      long enttime = System.currentTimeMillis();
      System.out.println("查看典型路线成功结束！用时：" + (enttime - starttime));
      return modelroutelist;
    }
    catch (Exception e)
    {
      System.out.println("查看典型路线时出错！");
      e.printStackTrace(); }
    return null;
  }

}
