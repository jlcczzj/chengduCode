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
 *                        
 *                        */

package com.faw_qm.cappclients.capproute.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 查看路线表的零部件表</p>
 * <p>Description: 查看瘦路线表的零部件表</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */

public class ViewRouteListPartsUtil {
    private static PersistService pService;
    
//  CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线 
    private static HashMap  tempResult = new HashMap();
//  CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线     
    public ViewRouteListPartsUtil() {
    }

    /**
     * 得到瘦客户端头部的必要显示信息
     * @param bsoID 路线表的BsoID
     * @return 路线表的BsoID,编号,名称,级别,单位,版本,用于产品
     */
    public static String[] getRouteListHead(String bsoID) {
        String id = bsoID.trim();
        String number = "";
        String name = "";
        String product = "";
        String level = "";
        String department = "";
        String version = "";
        TechnicsRouteListInfo routelist;

        try {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            routelist = (TechnicsRouteListInfo) pService.refreshInfo(id);
            number = routelist.getRouteListNumber();
            name = routelist.getRouteListName();
            level = routelist.getRouteListLevel();
            String dep = routelist.getDepartmentName();
            if (dep != null) {
                department = dep;
            }
            version = routelist.getVersionValue();
            QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
                    .refreshInfo(routelist.getProductMasterID());
            product = partmaster.getPartNumber() + "_"
                    + partmaster.getPartName();

            //构造数组
            String[] myVersionArray1 = { bsoID, number, name, product, level,
                    department, version, };
            return myVersionArray1;

        } catch (Exception sle) {
            System.out.println(sle.toString());
            return null;
        }
    }
    
//  CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线   
    

    private static Collection doFilter(String routelistBsoID,String isall,String contand,String depart)
{
  Collection col = (Collection)tempResult.get(routelistBsoID);
  if(col==null)
   {
     //System.out.println("缓存中没有内容") ;
     return new Vector();
   }
   Vector result = new Vector();
   Iterator i = col.iterator();
   while(i.hasNext())
   {
    // String[] array = {linkID,partNum,partName,routeStr,routeState,iconURL};
    String[] array = (String[])i.next();
    String routeStr = array[3];
    if(isContaind(routeStr,isall,contand,depart))
       result.add(array);
   }
  return result;
}

private static boolean isContaind(String routeStr,String isall,String contand,String depart){
	//  System.out.println("routeStr="+routeStr+" isall="+isall+" contand="+contand+" depart="+depart);

    if(depart.trim().equals(""))
      return true;
     boolean flag = false;
     if(routeStr.trim().equals(""))
       return false;
//   CCBegin by leixiao 2008-11-6 原因：解放系统升级,按路线单位查询，选择制造路线，装配路线为条件不好用
     StringTokenizer st = new StringTokenizer(routeStr,"；");
     while(st.hasMoreTokens())
     {
     String filterStr = st.nextToken();
    // System.out.println("----filterStr="+filterStr);
      int i = filterStr.lastIndexOf("→");
      int size = filterStr.length();
      if(isall.equals("制造路线"))
       {
        if(i!=-1){
        filterStr =  filterStr.substring(0,i);
        }
       }
      else
       if(isall.equals("装配路线"))
       {
         if(i!=-1){
          filterStr = filterStr.substring(i+1,size);
         }
          else
            filterStr = "";           
       }
//    CCEnd by leixiao 2008-11-6 原因：解放系统升级

       int location =  filterStr.indexOf(depart);
       if(contand.equals("包含"))
       {
         if(location!=-1)
           return true;
       }
       if(contand.equals("始于"))
       {
       //  System.out.println("location=="+location);
         if(location==0)
           return true;
       }
       if(contand.equals("止于"))
       {
        if(filterStr.endsWith(depart))
          return true;
       }
      if(contand.equals("完全匹配"))
       {
        if(filterStr.equals(depart))
          return true;
       }
     }
    return flag;
}
//CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线   

    /**
     * 获得指定路线表中的所有零部件及其路线分支,路线状态
     * @param routelistBsoID
     * @return
     */
    public static Collection getParts(String routelistBsoID) {
      Vector c = new Vector();
      try {
       pService = (PersistService) EJBServiceHelper
                  .getService("PersistService");
       TechnicsRouteService routeServer = (TechnicsRouteService) EJBServiceHelper//CR2
       .getService("TechnicsRouteService");
       //CR1 BEGIN
        QMQuery query = new QMQuery("TechnicsRouteList");
        int a = query.appendBso("ListRoutePartLink",true);
        int b = query.appendBso("TechnicsRoute",false);
        int d = query.appendBso("TechnicsRouteBranch",true);
        query.addCondition(a,new QueryCondition("leftBsoID","=",
                                                 routelistBsoID));
        query.addAND();
        query.addLeftParentheses();
        query.addCondition(a,new QueryCondition("alterStatus", "=", 1));
        query.addOR();
        query.addCondition(a,new QueryCondition("alterStatus", "=", 0));
        query.addRightParentheses();
        query.addAND();
        QueryCondition qu=new QueryCondition("routeID", "bsoID");
        qu.setOuterJoin(1);
        query.addCondition(d,b,qu);
        
        query.addAND();
        QueryCondition qu2= new QueryCondition("routeID", "bsoID");
        qu2.setOuterJoin(2);
        query.addCondition(a,b,qu2);
        query.addAND();
        query.addCondition(new QueryCondition("bsoID","=",
                                                 routelistBsoID));
       
        Collection coll = pService.findValueInfo(query);
          //Vector links = (Vector) routeService
                  //.getRouteListLinkParts(routelist);
          if (coll != null && coll.size() > 0) {
          	
              for (Iterator it = coll.iterator(); it.hasNext(); ) {
                  BaseValueIfc[] bvis = (BaseValueIfc[]) it.next();
                  TechnicsRouteListIfc routeList=(TechnicsRouteListIfc)bvis[0];//CR2
                  ListRoutePartLinkIfc linkinfo = (ListRoutePartLinkIfc)bvis[1];
                  TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)bvis[2];
                  String linkID = linkinfo.getBsoID();
                  String partNum = linkinfo.getPartMasterInfo().getPartNumber();
                  String partName = linkinfo.getPartMasterInfo().getPartName();
                  String routeID = linkinfo.getRouteID();
                  String routeStr = "";
                  String routeState = "";
                  String iconURL = "";
                  
                  if (routeID==null) {                            //Begin CR2

                      String level = routeList.getRouteListLevel();
                      String level2 = new String("二级路线");
                      String status = null;
                      if (level.equals(level2)) {
                        String departmentBsoid = routeList.
                            getRouteListDepartment();
                        status = routeServer.getStatus2(linkinfo.getPartMasterID(),
                                            level, departmentBsoid);
                      }
                      else {
                        status =routeServer. getStatus(linkinfo.getPartMasterID(),
                        		routeList.getRouteListLevel());
                      }
                      linkinfo.setAdoptStatus(status);
                     
                    }                                         //End CR2
 
                  routeState = linkinfo.getAdoptStatus();//路线状态
                  if (routeID != null) {
                      //routeStr = getRouteBranches(link.getRouteID());
                      routeStr = branch.getRouteStr();
                      //END CR1
                      iconURL = "images/route.gif";
                  } else {
                      routeStr = "";
                      iconURL = "images/route_emptyRoute.gif";
                  }
                  String[] array = { linkID, partNum, partName, routeStr,
                          routeState, iconURL };
                  c.addElement(array);
              }
          }
      } catch (Exception ex) {
          System.out.println(ex.toString());
      }
      return c;
  }
    
  //  CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线 

    
    public static Collection getParts(String routelistBsoID,String isall,String contand,String depart)
    {
    	   
        //	System.out.println("getParts routelist="+routelistBsoID+" isall="+isall+" contand="+contand+" depart="+depart);

          Vector c = new Vector();
          try
          {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)pService.refreshInfo(routelistBsoID);
            TechnicsRouteService routeService =
                (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
            Vector links;
            if(isall.trim().equals(""))
             // links = (Vector)routeService.getRouteListLinkParts(routelist);
            links= (Vector)getRouteListLinkParts(routelistBsoID);
             else
             // links = (Vector)routeService.getRouteListLinkParts(routelist,isall,contand,depart);
            return doFilter(routelistBsoID,isall,contand,depart);
            if(links != null && links.size()>0)
            {
              for(int i=0;i<links.size();i++)
              {
                ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)links.elementAt(i);
                String linkID = link.getBsoID();
                String partNum = link.getPartMasterInfo().getPartNumber();
                String partName = link.getPartMasterInfo().getPartName();
                String routeID = link.getRouteID();
                String routeStr="";
                String routeState="";
                String iconURL = "";
                //CCBegin by leixiao 201-1-7 状态从route中取ModefyIdenty
                if(routeID!=null){
                TechnicsRouteIfc route = (TechnicsRouteIfc)pService.refreshInfo(routeID);
                routeState=route.getModefyIdenty().getCodeContent();
                }
                else{
                routeState = "无";//路线状态
                }
                //CCEnd by leixiao 201-1-7
                if(routeID!=null)
                {
                  routeStr = getRouteBranches(link.getRouteID());
                  iconURL = "images/route.gif";
                }
                else
                {
                  routeStr = "";
                  iconURL = "images/route_emptyRoute.gif";
                }
                String[] array = {linkID,partNum,partName,routeStr,routeState,iconURL};
                c.addElement(array);
              }
            }
          }
          catch (Exception ex)
          {
            System.out.println(ex.toString());
          }
          if(isall.trim().equals(""))
            tempResult.put(routelistBsoID,c);
          return c;
        }
    //  CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线 

    /**
	 * 获得指定路线的路线串
	 * 
	 * @param routeID
	 *            工艺路线的BsoID
	 * @return 路线串
	 */
    public static String getRouteBranches(String routeID) {
      //long starttime = System.currentTimeMillis();

        String result = "";
        try {
            TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
                    .getService("TechnicsRouteService");
            Map map = routeService.getRouteBranchs(routeID);

            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for (int i = 0; i < branchs.length; i++) {
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) branchs[i];
                String makeStr = "";
                String assemStr = "";
                String routeStr = "";
                Object[] nodes = (Object[]) map.get(branchinfo);
                Vector makeNodes = (Vector) nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

                if (makeNodes != null && makeNodes.size() > 0) {
                    // 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
                    for (int m = 0; m < makeNodes.size(); m++) {
                        RouteNodeInfo node = (RouteNodeInfo) makeNodes
                                .elementAt(m);
                        if (makeStr.equals(""))
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "→"
                                    + node.getNodeDepartmentName();
                    }
                }

                if (asseNode != null) {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if (!makeStr.equals("") && !assemStr.equals(""))
                    routeStr = makeStr + "→" + assemStr;
                else if (makeStr.equals("") && !assemStr.equals(""))
                    routeStr = assemStr;
                else if (!makeStr.equals("") && assemStr.equals(""))
                    routeStr = makeStr;
                else if (makeStr.equals("") && assemStr.equals(""))
                    routeStr = "";

                if (routeStr.equals("")) {
                    routeStr = "无";
                }

                if (result.equals(""))
                    result = result + routeStr;
                else
                    result = result + "；" + routeStr;

                if (result.equals(""))
                    result = "无";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        long endtime = System.currentTimeMillis();
//          System.out.println(" kankan time  end  " +( endtime- starttime));
        return result;
    }
    
//  CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线     
    public Vector getAllDeps()
    {
      try{
        TechnicsRouteService routeService =
            (TechnicsRouteService) EJBServiceHelper.getService(
            "TechnicsRouteService");
           return routeService.getAllDeps();
         }catch (Exception ex)
         {
           System.out.println("查找所有单位出错！！！");
           ex.printStackTrace();
         }
        return new Vector();
    }
//  CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线 
    
//  CCBegin by leixiao 2009-11-26 原因：解放升级工艺路线 　本来用产品性能优化方法（4表查询），后发现没用
    private static Collection getRouteListLinkParts(String routelistBsoID) {
    	try{
    	pService = (PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("ListRoutePartLink");
        query.addCondition(new QueryCondition("leftBsoID","=",
                                                 routelistBsoID));
        query.addAND();
        query.addLeftParentheses();
        query.addCondition(new QueryCondition("alterStatus", "=", 1));
        query.addOR();
        query.addCondition(new QueryCondition("alterStatus", "=", 0));
        query.addRightParentheses(); 
        return pService.findValueInfo(query);
    	}catch(QMException e){
    		e.printStackTrace();
    		return null;
    	}
    }
//  CCEnd by leixiao 2009-11-26 原因：解放升级工艺路线 
}
