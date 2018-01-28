/**
 * 生成程序 ViewRouteListPartsUtil.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

/* CR1 2009/04/21  李磊   原因: 优化工艺路线表瘦客户查看。
 *                       方案: 1.显示的时候直接从工艺路线表分支表中将路线串显示
 *                       备注: 性能测试用例名称"瘦客户查看工艺路线表". 
 *                       
 * CR2 2009/06/08  郭晓亮  参见:测试域:v4r3FunctionTest;TD号2268                 
 * CR3 2012/01/12  吕航 原因 如果产品没有时出现错误                       
 * SS1 轴齿工艺路线增加采购标识显示 pante 2014-05-22
 * SS2 长特增加供应商 liuyang 2014-8-23
 * SS3 零部件列表显示顺序问题 liuzhicheng 2015-6-17 
 * SS4 成都工艺路线整合 liunan 2016-8-12
 * SS5 A032-2016-0068 零部件列表显示路线错误。 liunan 2016-9-2
 *                        */

package com.faw_qm.cappclients.conscapproute.web;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
//CCBegin SS5
import java.util.HashMap;
//CCEnd SS5

/**
 * <p>Title: 查看路线表的零部件表</p> <p>Description: 查看瘦路线表的零部件表</p> <p>Copyright: Copyright (c) 2004</p> <p>Company: </p>
 * @author
 * @version 1.0
 */

public class ViewRouteListPartsUtil
{
    private static PersistService pService;

    public ViewRouteListPartsUtil()
    {}

    /**
     * 得到瘦客户端头部的必要显示信息
     * @param bsoID 路线表的BsoID
     * @return 路线表的BsoID,编号,名称,级别,单位,版本,用于产品
     */
    public static String[] getRouteListHead(String bsoID)
    {
        String id = bsoID.trim();
        String number = "";
        String name = "";
        String product = "";
        String level = "";
        String department = "";
        String version = "";
        TechnicsRouteListInfo routelist;

        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            routelist = (TechnicsRouteListInfo)pService.refreshInfo(id);
            number = routelist.getRouteListNumber();
            name = routelist.getRouteListName();
            level = routelist.getRouteListLevel();
            String dep = routelist.getDepartmentName();
            if(dep != null)
            {
                department = dep;
            }
            version = routelist.getVersionValue();
            //CR3 lvh begin
            if(routelist.getProductMasterID()!=null)
            {
            QMPartMasterIfc partmaster = (QMPartMasterIfc)pService.refreshInfo(routelist.getProductMasterID());
            product = partmaster.getPartNumber() + "_" + partmaster.getPartName();
            }
            //CR3 lvh end
            //构造数组
            String[] myVersionArray1 = {bsoID, number, name, product, level, department, version,};
            return myVersionArray1;

        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * 获得指定路线表中的所有零部件及其路线分支,路线状态
     * @param routelistBsoID
     * @return
     */
    public static Collection getParts(String routelistBsoID)
    {
        Vector c = new Vector();
        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteService routeServer = (TechnicsRouteService)EJBServiceHelper//CR2
                    .getService("consTechnicsRouteService");
            //CR1 BEGIN
            QMQuery query = new QMQuery("consTechnicsRouteList");
            int a = query.appendBso("consListRoutePartLink", true);
            int b = query.appendBso("consTechnicsRoute", false);
            int d = query.appendBso("consTechnicsRouteBranch", true);
            query.addCondition(a, new QueryCondition("leftBsoID", "=", routelistBsoID));
            query.addAND();
            query.addLeftParentheses();
            query.addCondition(a, new QueryCondition("alterStatus", "=", 1));
            query.addOR();
            query.addCondition(a, new QueryCondition("alterStatus", "=", 0));
            query.addRightParentheses();
            query.addAND();
            QueryCondition qu = new QueryCondition("routeID", "bsoID");
            qu.setOuterJoin(1);
            query.addCondition(d, b, qu);

            query.addAND();
            QueryCondition qu2 = new QueryCondition("routeID", "bsoID");
            qu2.setOuterJoin(2);
            query.addCondition(a, b, qu2);
            query.addAND();
            query.addCondition(new QueryCondition("bsoID", "=", routelistBsoID));

            Collection coll = pService.findValueInfo(query);
            //Vector links = (Vector) routeService
            //.getRouteListLinkParts(routelist);
            
            //CCBegin SS3
            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(routelistBsoID);
            UserIfc user = (UserIfc)pService.refreshInfo(routelist.getIterationCreator());
            System.out.println("user.getUsersName()getParts;__________________________________________________________"+user.getUsersName());
            String comp=RouteClientUtil.getUserFromCompany();
            if (coll != null && coll.size() > 0) {
            	TechnicsRouteListIfc routeList = null;
            	TechnicsRouteBranchIfc branch  = null;
            	Vector vecListRoutePartLinkBefore = new Vector();
            	Vector vecListRoutePartLinkAfter = new Vector();
            	//CCBegin SS5
            	HashMap branchMap = new HashMap();
            	Vector tempvec = new Vector();
            	//CCEnd SS5
                for(Iterator it = coll.iterator();it.hasNext();)
                {
                    BaseValueIfc[] bvis = (BaseValueIfc[]) it.next();
                    routeList=(TechnicsRouteListIfc)bvis[0];//CR2  
                    ListRoutePartLinkIfc linkinfoOld = (ListRoutePartLinkIfc)bvis[1];
                    branch = (TechnicsRouteBranchIfc)bvis[2];
                    //CCBegin SS5
                    if(tempvec.contains(linkinfoOld.getBsoID()))
                    {
                    	Vector vv = (Vector)branchMap.get(linkinfoOld.getBsoID());
                    	vv.add(branch);
                    	branchMap.put(linkinfoOld.getBsoID(),vv);
                    	continue;
                    }
                    Vector branchvec = new Vector();
                    branchvec.add(branch);
                    //branchMap.put(linkinfoOld.getBsoID(),branch);
                    branchMap.put(linkinfoOld.getBsoID(),branchvec);
                    tempvec.add(linkinfoOld.getBsoID());
                    //CCEnd SS5
                    vecListRoutePartLinkBefore.add(linkinfoOld);
                }
                if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
                {
                	vecListRoutePartLinkAfter = sortLinks(routeList , vecListRoutePartLinkBefore);
                }else
                {
                	vecListRoutePartLinkAfter = vecListRoutePartLinkBefore;
                }
                for(int ii = 0; ii < vecListRoutePartLinkAfter.size(); ii++)  
                {
                    //CCBegin SS1
//                    TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)bvis[2];
                    //CCEnd SS1
                	ListRoutePartLinkIfc linkinfo = (ListRoutePartLinkIfc)vecListRoutePartLinkAfter.elementAt(ii);
                    String linkID = linkinfo.getBsoID();
                    String partNum = linkinfo.getPartMasterInfo().getPartNumber();
                    String partName = linkinfo.getPartMasterInfo().getPartName();
                    String routeID = linkinfo.getRouteID();
                    String routeStr = "";
                    String routeState = "";
                    String iconURL = "";

                    if(routeID == null)
                    { //Begin CR2

                        String level = routeList.getRouteListLevel();
                        String level2 = new String("二级路线");
                        String status = null;
                        if(level.equals(level2))
                        {
                            String departmentBsoid = routeList.getRouteListDepartment();
                            status = routeServer.getStatus2(linkinfo.getPartMasterID(), level, departmentBsoid);
                        }else
                        {
                            status = routeServer.getStatus(linkinfo.getPartMasterID(), routeList.getRouteListLevel());
                        }
                        linkinfo.setAdoptStatus(status);

                    } //End CR2

                    routeState = linkinfo.getAdoptStatus();//路线状态
                    if(routeID != null)
                    {
                        //routeStr = getRouteBranches(link.getRouteID());
                        //CCBegin SS5
                        //branch = (TechnicsRouteBranchIfc)branchMap.get(linkID);
                        //routeStr = branch.getRouteStr();
                        Vector vv = (Vector)branchMap.get(linkID);
                        for(int iii = 0;iii<vv.size();iii++)
                        {
                        	branch = (TechnicsRouteBranchIfc)vv.elementAt(iii);
                        	if(iii==0)
                        	{
                        		routeStr = branch.getRouteStr();
                        	}
                        	else
                        	{
                        		routeStr = routeStr + ";" + branch.getRouteStr();
                        	}
                        }
                        //CCEnd SS5
                        //END CR1
                        iconURL = "images/route.gif";
                    }else
                    {
                        routeStr = "";
                        iconURL = "images/route_emptyRoute.gif";
                    }
                    //CCBegin SS1
                    //CCBegin SS2
                    String[] array = {linkID, partNum, partName, routeStr, routeState, iconURL, linkinfo.getStockID(),linkinfo.getSupplier()};
                    //CCEnd SS2
                    //CCEnd SS1
                    c.addElement(array);
                }
            }
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        return c;
    }

        
        
        //CCBegin SS3
    	private static Vector sortLinks(TechnicsRouteListIfc  routeListInfo , Vector v) {

    		Vector indexs = routeListInfo.getPartIndex();
    		if (indexs == null || indexs.size() == 0) {
    			 System.out.println("排序集合为空");
    			return v;
    		}
    		Vector result = new Vector();
    		String partid;
    		String partNum;
    		String[] index;

    		for (int i = 0; i < indexs.size(); i++) {
    			index = (String[]) indexs.elementAt(i);
    			partid = index[0];
    			System.out.println("顺序里的" + partid);
    			partNum = index[1];
    			ListRoutePartLinkIfc link;
    			for (int j = 0; j < v.size(); j++) {
    				link = (ListRoutePartLinkIfc) v.elementAt(j);
    				System.out.println("关联里的" + link.getRightBsoID());
    				if (link.getRightBsoID().equals(partid)) {
    					result.add(link);
    					v.remove(link);
    					break;
    				}
    			}
    		}
    		return result;
    	}
        //CCEnd SS3
        
        
        
    /**
     * 获得指定路线的路线串
     * @param routeID 工艺路线的BsoID
     * @return 路线串
     */
    public static String getRouteBranches(String routeID)
    {
        //long starttime = System.currentTimeMillis();

        String result = "";
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getRouteBranchs(routeID);

            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for(int i = 0;i < branchs.length;i++)
            {
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
                String makeStr = "";
                String assemStr = "";
                String routeStr = "";
                Object[] nodes = (Object[])map.get(branchinfo);
                Vector makeNodes = (Vector)nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];

                if(makeNodes != null && makeNodes.size() > 0)
                {
                    // 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
                    for(int m = 0;m < makeNodes.size();m++)
                    {
                        RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                        if(makeStr.equals(""))
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "→" + node.getNodeDepartmentName();
                    }
                }

                if(asseNode != null)
                {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if(!makeStr.equals("") && !assemStr.equals(""))
                    routeStr = makeStr + "→" + assemStr;
                else if(makeStr.equals("") && !assemStr.equals(""))
                    routeStr = assemStr;
                else if(!makeStr.equals("") && assemStr.equals(""))
                    routeStr = makeStr;
                else if(makeStr.equals("") && assemStr.equals(""))
                    routeStr = "";

                if(routeStr.equals(""))
                {
                    routeStr = "无";
                }

                if(result.equals(""))
                    result = result + routeStr;
                else
                    result = result + "；" + routeStr;

                if(result.equals(""))
                    result = "无";
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //        long endtime = System.currentTimeMillis();
        //          System.out.println(" kankan time  end  " +( endtime- starttime));
        return result;
    }
    
  //CCBegin SS4
  public static String[] getRouteBranches2(String routeID) {
    String result ="";
    String temproute="";
    try {
      TechnicsRouteService routeService =
          (TechnicsRouteService) EJBServiceHelper.getService(
          "consTechnicsRouteService");
      Map map = routeService.getRouteBranchs(routeID);

      //Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
      Collection sortedVec = map.keySet();
      for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
      //for (int i = 0; i < branchs.length; i++) {
        boolean istemp=false;
        //TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) branchs[i];
        TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) iter.next();
        //CCBegin 20101117 add by 徐德政，原因标识出主要路线
        boolean mainRouteFlag = branchinfo.getMainRoute();
        //CCEnd 20101117 add by mario
        String makeStr = "";
        String assemStr = "";
        String routeStr = "";
        Object[] nodes = (Object[]) map.get(branchinfo);
        Vector makeNodes = (Vector) nodes[0];
        RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

        if (makeNodes != null && makeNodes.size() > 0) {
          //System.out.println(">>>>>>>>>>>>>>>>>  获得 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
          for (int m = 0; m < makeNodes.size(); m++) {
            RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
            if(node.getIsTempRoute()){
              istemp=true;
            }
            if (makeStr.equals("")) {
              makeStr = makeStr + node.getNodeDepartmentName();
            }
            else {
              makeStr = makeStr + "-" + node.getNodeDepartmentName();
            }
          }
        }

        if (asseNode != null) {
          assemStr = asseNode.getNodeDepartmentName();
          if(asseNode.getIsTempRoute()){
            istemp=true;
          }
        }
        if (!makeStr.equals("") && !assemStr.equals("")) {
          routeStr = makeStr + "=" + assemStr;
        }
        else if (makeStr.equals("") && !assemStr.equals("")) {
          routeStr = "="+assemStr;
        }
        else if (!makeStr.equals("") && assemStr.equals("")) {
          routeStr = makeStr+"=";
        }
        else if (makeStr.equals("") && assemStr.equals("")) {
          routeStr = "";
        }
        if (!routeStr.equals("")) {
          if(istemp==true){
            if (temproute.equals("")) {
              temproute = temproute + routeStr;
            }
            else {
              temproute = temproute + "；" + routeStr;
            }
          }else{
            if (result.equals("")) {
              result = result + routeStr;
            }
            else {
            	//CCBegin 20101117 add by 徐德政，原因标识出主要路线
            	if(mainRouteFlag)
            		result =   routeStr+ "；" +result;
            	else
            		result = result + "；" + routeStr;
            	//CCEnd 20101117 add by mario
            }
          }
        }

      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    if (result.equals("")) {
      result = "无";
    }
    String[] returnresult={result,temproute};
    return returnresult;
  }
  //CCEnd SS4
}
