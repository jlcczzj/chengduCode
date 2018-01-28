package com.faw_qm.technics.route.ejb.service;

import java.util.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.RouteBranchNodeLinkIfc;

public class AddData {

  public AddData() throws QMException {
    getTechnicsBranch();
  }

  PersistService pService = (PersistService) EJBServiceHelper.
      getPersistService();
  TechnicsRouteBranchIfc routeBranch = null;
  public void getTechnicsBranch() {
    try {
      //查TechnicsRouteBranch对象
      QMQuery query = new QMQuery("TechnicsRouteBranch");
      Collection coll = pService.findValueInfo(query);
      getRouteNode(coll);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //找到每个路线串的所有节点
  private void getRouteNode(Collection technicsBranchColl) {
    try {
      Iterator iterator = technicsBranchColl.iterator();
      while (iterator.hasNext()) {
        routeBranch = (TechnicsRouteBranchIfc) iterator.
            next();
        //String routeBranchBsoid = routeBranch.getBsoID();
        // System.out.println("routeBranchBsoid=====" + routeBranchBsoid);
        //已知routeBranchBsoid查routeNodeBsoid
        QMQuery query = new QMQuery("RouteBranchNodeLink");
        QueryCondition cond2 = new QueryCondition("leftBsoID",
                                                  QueryCondition.EQUAL,
                                                  routeBranch.getBsoID());
        query.addCondition(cond2);
        query.addOrderBy("bsoID", false);
        Collection coll = pService.findValueInfo(query, false);
        RouteBranchNodeLinkIfc linkInfo = null;
        String makeStr = "";
        String assemStr = "";
        for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
          linkInfo = (RouteBranchNodeLinkIfc) iter.next();
//         String nodeID = linkInfo.getRouteNodeInfo().getBsoID();
          String nodeName = linkInfo.getRouteNodeInfo().getNodeDepartmentName();
//         System.out.println("nodeID====" + nodeID);
//         System.out.println("name======" + nodeName);
          String routeType = linkInfo.getRouteNodeInfo().getRouteType();

          if (routeType.equals(RouteCategoryType.MANUFACTUREROUTE.getDisplay())) {

            if (makeStr == "") {
              makeStr = makeStr + nodeName;
            }
            else {
              makeStr = makeStr + "→" + nodeName;
            }

          }
          else if (routeType.equals(RouteCategoryType.ASSEMBLYROUTE.getDisplay())) {
            if (assemStr == "") {
              assemStr = assemStr + nodeName;
            }
            else {
              assemStr = assemStr + "→" + nodeName;
            }

          }

        }
        if (makeStr.equals("")) {
          makeStr = "无";
        }
        if (assemStr.equals("")) {
          assemStr = "无";
        }
        routeBranch.setRouteStr(makeStr + "@" + assemStr);
        pService.saveValueInfo(routeBranch);
      }
      System.out.println("完成！！！！！！");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
