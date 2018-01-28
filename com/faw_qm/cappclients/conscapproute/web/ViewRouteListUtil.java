/**
 * 生成程序 ViewRouteListUtil.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 20111229 吕航 原因瘦客户生成报表使用
 * CR2 20111230 吕航 原因 组合瘦客户查看使用 
 * CR3 20120112 吕航 原因查看零件时显示有效路线和取消路线
 * CR4 20120112 吕航 原因产品为空时没有判断
 * CR5 20120326 吕航 原因参见TD5919
 * CR6 20120327 吕航 原因参见TD5937
 * CR7 20120328 吕航 原因参见TD5951
 * CR8 20120329 吕航 原因参见TD5970
 * CR9 20120409 吕航 原因参见TD6024
 * SS1 修改工艺路线清单界面关联零部件显示错误问题 马晓彤  2013/12/19
 * SS2 二级路线查看界面零部件图标不显示的问题 liunan 2014-2-7
 * SS3 轴齿增加采购标识显示 pante 2014-05-22
 * SS4 长特增加供应商 liuyang 2014-8-22
 * SS5 判断是长特路线级别，查看不同报表 liuyang 2014-8-28
 * SS6 长特二级路线报表 liuyang 2014-8-29
 * SS7 零部件列表显示顺序问题 liuzhicheng 2015-6-17 
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.capp.util.CappServiceWebHelper;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p> Title:查看路线表 </p> <p> Description:本类提供的方法为廋客户界面使用 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @mender skybird
 * @version 1.0
 */

public class ViewRouteListUtil
{

    /** 用于代码测试 */
    public static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");
    private static final String routeMode = RemoteProperty.getProperty("routeManagerMode", "partRelative");
    public static final int PARTDELETE = 2;

    public ViewRouteListUtil()
    {}

    /**
     * 得到符合瘦客户端显示必须的内容的集合
     * @param BsoID 路线表的BsoID
     * @return 字符串数组，数组内容为 BsoID,编号,名称,级别,单位,版本,用于产品,说明,生命周期,项目组,资料夹,生命周期状态, 创建者,创建时间
     */
    public static String[] getMyCollection(String bsoID)
    {
        if(verbose)
            System.out.println("getMyCollection begin...BsoID=" + bsoID);
        bsoID = bsoID.trim();
        // 用于返回
        Collection myCollection = new Vector();
        // 调用服务，根据工艺卡的BsoID获得其值对象
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(bsoID);
            String number = routelist.getRouteListNumber();
            String name = routelist.getRouteListName();
            String level = routelist.getRouteListLevel();
            // st skybird 2005.2.25
            String state1 = routelist.getRouteListState();
            // ed
            String department = "";
            String dep = routelist.getDepartmentName();
            if(dep != null)
            {
                department = dep;
            }

            String version = routelist.getVersionValue();
            //CR4 lvh begin
            String product = "";
            if(routelist.getProductMasterID() != null)
            {
                QMPartMasterIfc partmaster = (QMPartMasterIfc)pService.refreshInfo(routelist.getProductMasterID());
                product = partmaster.getPartNumber() + "_" + partmaster.getPartName();
            }
            //CR4 lvh end
            String description = "";
            String des = routelist.getRouteListDescription();
            if(des != null)
                description = des;

            String lifecycle = "";
            String lc = routelist.getLifeCycleTemplate();
            if(lc != null)
            {
                LifeCycleTemplateInfo lct = (LifeCycleTemplateInfo)pService.refreshInfo(lc);
                lifecycle = lct.getLifeCycleName();
            }

            String projectName = "";
            ProjectIfc project = null;
            String projectID = ((LifeCycleManagedIfc)routelist).getProjectId();
            if(projectID != null)
                project = (ProjectIfc)pService.refreshInfo(projectID);
            // 获得项目组名
            if(project != null)
                // modify by guoxl on 2008.4.10(工作组只显示无类型的标识信息)
                // projectName = project.getName();
                projectName = CappServiceWebHelper.getIdentity(project);
            // modify by guoxl end

            String location = routelist.getLocation();

            String state = "";
            LifeCycleState st = routelist.getLifeCycleState();
            // 获得对象的状态
            if(st != null)
                state = st.getDisplay();

            String creator = "";
            String ic = routelist.getIterationCreator();
            if(ic != null)
                creator = RouteWebHelper.getUserNameByID(ic);

            // 获得创建时间
            String createTime = routelist.getCreateTime().toString();
            createTime = createTime.substring(0, createTime.lastIndexOf("."));
            // 构造数组
            String[] myVersionArray1 = {bsoID, number, name, level, department, version, product, description, lifecycle, projectName, location, state, creator, createTime, state1};
            return myVersionArray1;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得路线表主信息对象的bsoID
     * @param bsoID 路线表值对象的bsoID
     * @return 路线表主信息对象的bsoID
     */
    public static String getRouteListMasterID(String bsoID)
    {
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo)pService.refreshInfo(bsoID);
            return info.getMaster().getBsoID();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    //CR2 begin
    /**
     * 通过路线ID获得路线对象
     * @param routelistBsoID
     * @return
     */
    public static TechnicsRouteInfo getRouteByBsoID(String routeID)
    {
        Collection col = null;
        TechnicsRouteInfo techroute = null;
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            QMQuery query = new QMQuery("consTechnicsRoute");
            query.addCondition(0, new QueryCondition("bsoID", "=", routeID));
            col = pService.findValueInfo(query);
            Iterator ite = col.iterator();
            while(ite.hasNext())
            {
                techroute = (TechnicsRouteInfo)ite.next();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return techroute;
    }

    /**
     * 通过路线表ID获得关联
     * @param routelistBsoID
     * @return
     * @throws QMException 
     */
/*    public static Vector getListPartLink(String routelistBsoID) throws QMException
    {
        Collection col = null;
        Vector vec = new Vector();
        String productIdenty = "否";
        String structure = "否";
        String img = "";
        //        try
        //        {
        //            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        //            QMQuery query = new QMQuery("ListRoutePartLink");
        //            query.addCondition(new QueryCondition("leftBsoID", "=", routelistBsoID));
        //            col = pService.findValueInfo(query);
        //        }catch(Exception e)
        //        {
        //            e.printStackTrace();
        //        }
        //        return col;
        TechnicsRouteService routeservice = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)pservice.refreshInfo(routelistBsoID);
        col = routeservice.getRouteListLinkParts1(routelist);
        Iterator it = col.iterator();
        while(it.hasNext())
        {
            ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)it.next();
            if(link.getProductIdenty())
            {
                productIdenty = "是";
            }else
            {
                productIdenty = "否";
            }
            if(link.getParentPartNum() != null)
            {
                structure = "是";
            }else
            {
                structure = "否";
            }
            if(link.getAdoptStatus().equals("已编制"))
            {
                img = "images/route.gif";
            }else if(link.getAdoptStatus().equals("已存在"))
            {
                img = "images/route_partUsedPlace.gif";
            }

            else if(link.getAdoptStatus().equals("未编制"))
            { // 无路线
                img = "images/route_emptyRoute.gif";
            }
            //CR6
            //CR7
            Object[] obj = {link.getBsoID(), link.getModifyIdenty(), link.getPartMasterInfo().getPartNumber(), link.getPartMasterInfo().getPartName(), link.getProductCount(), link.getMainStr(),
                    link.getSecondStr(), link.getRouteDescription(), link.getParentPartNum(), link.getParentPartName(), productIdenty, structure, img,link.getPartMasterInfo().getBsoID()};
            vec.add(obj);
        }

        return vec;
    }*/
    
    public Vector getListPartLink(String routelistBsoID){
    	Vector c = new Vector();
    	 String productIdenty = "否";
         String structure = "否";
         String img = "";
        try {
        	PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
         TechnicsRouteService routeServer = (TechnicsRouteService) EJBServiceHelper//CR2
         .getService("consTechnicsRouteService");
         //CR1 BEGIN
          QMQuery query = new QMQuery("consTechnicsRouteList");
          int a = query.appendBso("consListRoutePartLink",true);
          int b = query.appendBso("consTechnicsRoute",false);
          //CCBegin SS1
//          int d = query.appendBso("consTechnicsRouteBranch",true);
          //CCEnd SS1
          query.addCondition(a,new QueryCondition("leftBsoID","=",
                                                   routelistBsoID));
          query.addAND();
          query.addLeftParentheses();
          query.addCondition(a,new QueryCondition("alterStatus", "=", 1));
          query.addOR();
          query.addCondition(a,new QueryCondition("alterStatus", "=", 0));
          query.addRightParentheses();
          //CCBegin SS1
//          query.addAND();
//          QueryCondition qu=new QueryCondition("routeID", "bsoID");
//          qu.setOuterJoin(1);
//          query.addCondition(d,b,qu);
          //CCEnd SS1fgetSencondRouteReport
          query.addAND();
          QueryCondition qu2= new QueryCondition("routeID", "bsoID");
          qu2.setOuterJoin(2);
          query.addCondition(a,b,qu2);
          query.addAND();
          query.addCondition(new QueryCondition("bsoID","=",
                                                   routelistBsoID));
          //CCBegin SS7
          TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(routelistBsoID);
          UserIfc user = (UserIfc)pService.refreshInfo(routelist.getIterationCreator());
          System.out.println("user.getUsersName();__________________________________________________________"+user.getUsersName());
          String comp=RouteClientUtil.getUserFromCompany();
          if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
          {
              query.addOrderBy(a,"modifyTime");    
          }
          //CCEnd SS7
          Collection coll = pService.findValueInfo(query); 
          //CCBegin SS7
            //Vector links = (Vector) routeService
                    //.getRouteListLinkParts(routelist);
            if (coll != null && coll.size() > 0) {
            	TechnicsRouteListIfc routeList = null;
            	Vector vecListRoutePartLinkBefore = new Vector();
            	Vector vecListRoutePartLinkAfter = new Vector();
                for (Iterator it = coll.iterator(); it.hasNext(); )
                {
                    BaseValueIfc[] bvis = (BaseValueIfc[]) it.next();
                    routeList=(TechnicsRouteListIfc)bvis[0];//CR2  
                    ListRoutePartLinkIfc linkinfoOld = (ListRoutePartLinkIfc)bvis[1];
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
                    //CCBegin SS1
//                        routeStr = branch.getRouteStr();
                    	routeStr= linkinfo.getMainStr();
                    //CCEnd SS1
                        //END CR1
                        iconURL = "images/route.gif";
                    } else {
                        routeStr = "";
                        iconURL = "images/route_emptyRoute.gif";
                    }
//                    String[] array = { linkID, partNum, partName, routeStr,
//                            routeState, iconURL };
                    String description = "";
                  //CCBegin SS1
//                    if(linkinfo.getRouteDescription() == null || linkinfo.getRouteDescription().equals("")){
//                    	description = branch.getRouteInfo().getRouteDescription();
//                    }else{
                    	description = linkinfo.getRouteDescription();
//                    }
                   //CCEnd SS1
                    if(linkinfo.getProductIdenty())
                    {
                        productIdenty = "是";
                    }else
                    {
                        productIdenty = "否";
                    }
                    if(linkinfo.getParentPartNum() != null)
                    {
                        structure = "是";
                    }else
                    {
                        structure = "否";
                    }
                    
                    //CCBegin SS2
                    /*System.out.println("linkinfo.getAdoptStatus()===="+linkinfo.getAdoptStatus());
                    if(linkinfo.getAdoptStatus().equals("已编制"))
                    {
                        img = "images/route.gif";
                    }else if(linkinfo.getAdoptStatus().equals("已存在"))
                    {
                        img = "images/route_partUsedPlace.gif";
                    }

                    else if(linkinfo.getAdoptStatus().equals("未编制"))
                    { // 无路线
                        img = "images/route_emptyRoute.gif";
                    }*/
                    if(linkinfo.getAdoptStatus().equals("采用"))
                    {
                        img = "images/route.gif";
                    }else if(linkinfo.getAdoptStatus().equals("取消"))
                    {
                        img = "images/route_emptyRoute.gif";
                    }
                    //CCEnd SS2
                    //CCBegin SS3
                    //CCBegin SS4
                    Object[] obj = {linkinfo.getBsoID(), linkinfo.getModifyIdenty(), linkinfo.getPartMasterInfo().getPartNumber(), linkinfo.getPartMasterInfo().getPartName(), linkinfo.getProductCount(), routeStr,
                    		linkinfo.getSecondStr(), description, linkinfo.getParentPartNum(), linkinfo.getParentPartName(), productIdenty, structure, img,linkinfo.getPartMasterInfo().getBsoID(),linkinfo.getStockID(),linkinfo.getSupplier()};
                  //CCEnd SS4
                    //CCEnd SS3
                    c.addElement(obj);
                }
                
                
                
                
            }
            
            //CCEnd SS7
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return c;
    }

    //CR2 end
    
    
    
    //CCBegin SS7
	private static Vector sortLinks(TechnicsRouteListIfc  routeListInfo , Vector v) {

		Vector indexs = routeListInfo.getPartIndex();
		if (verbose) {
			System.out.println("排序前 v= " + v + " 排序indexs = " + indexs);
		}
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
		if (verbose) {
			System.out.println("排序后 result= " + result);
		}
		return result;
	}
    //CCEnd SS7
    
    
    

    // CR1 begin
    /**
     * 刷新工艺路线表之对象，瘦客户生成报表使用
     * @param routelistbsoID
     * @return
     * @throws QMException 
     */
    public TechnicsRouteListInfo refreshRouteList(String routelistbsoID) throws QMException
    {
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        TechnicsRouteListInfo info = (TechnicsRouteListInfo)pService.refreshInfo(routelistbsoID);
        return info;
    }

    // CR1 end
    //CR3 begin
    /**
     * 获得生效路线
     */
    public Vector getEffectiveRouteList(String partid)
    {
        Collection result1 = null;
        ListRoutePartLinkInfo link = null;
        Vector vec=new Vector();
        // CR9 begin
        String partBsoID="";
        String partNum="";
        String partName="";
        // CR9 end
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            QMPartInfo part = (QMPartInfo)pservice.refreshInfo(partid);
            String partmasterid = part.getMasterBsoID();
            QMQuery query1 = new QMQuery("consListRoutePartLink");
            QueryCondition cond = new QueryCondition("rightBsoID", QueryCondition.EQUAL, partmasterid);
            query1.addCondition(cond);
            query1.addAND();
            QueryCondition cond1 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
            query1.addCondition(cond1);
            result1 = (Collection)pservice.findValueInfo(query1, false);
            Iterator ite1 = result1.iterator();
            while(ite1.hasNext())
            {
                link = (ListRoutePartLinkInfo)ite1.next();
                // CR9 begin
                if(link.getProductID()!=null)
                {
                    QMPartMasterInfo partmaster=getPartMasterID1(link.getProductID());
                    partBsoID=partmaster.getBsoID();
                    partNum=partmaster.getPartNumber();
                    partName=partmaster.getPartName();
                }
                TechnicsRouteListInfo info=this.refreshRouteList(link.getLeftBsoID());
                Object[] obj={link.getBsoID(),link.getMainStr(),link.getSecondStr(),link.getModifyIdenty(),info.getBsoID(),info.getRouteListNumber(),info.getRouteListName(),partBsoID,partNum,partName,link.getParentPartNum(),link.getParentPartName(),partmasterid};
                vec.add(obj);
                // CR9 end
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return vec;
    }

    /**
     * 获得取消路线
     */
    public Vector getDisEffectiveRouteList(String partid)
    {
        Collection result1 = null;
        ListRoutePartLinkInfo link = null;
        Vector vec=new Vector();
        // CR9 begin
        String partBsoID="";
        String partNum="";
        String partName="";
        // CR9 end
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            QMPartInfo part = (QMPartInfo)pservice.refreshInfo(partid);
            String partmasterid = part.getMasterBsoID();
            QMQuery query1 = new QMQuery("consListRoutePartLink");
            QueryCondition cond = new QueryCondition("rightBsoID", QueryCondition.EQUAL, partmasterid);
            query1.addCondition(cond);
            query1.addAND();
            QueryCondition cond1 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 0);
            query1.addCondition(cond1);
            // CR9 begin
            query1.addOrderBy("disabledDateTime", true);
            result1 = (Collection)pservice.findValueInfo(query1, false);
            Iterator ite1 = result1.iterator();
            if(ite1.hasNext())
            {
                link = (ListRoutePartLinkInfo)ite1.next();
                if(link.getProductID()!=null)
                {
                    QMPartMasterInfo partmaster=getPartMasterID1(link.getProductID());
                    partBsoID=partmaster.getBsoID();
                    partNum=partmaster.getPartNumber();
                    partName=partmaster.getPartName();
                }
                TechnicsRouteListInfo info=refreshRouteList(link.getLeftBsoID());
                Object[] obj={link.getBsoID(),link.getMainStr(),link.getSecondStr(),link.getModifyIdenty(),info.getBsoID(),info.getRouteListNumber(),info.getRouteListName(),partBsoID,partNum,partName,link.getParentPartNum(),link.getParentPartName(),partmasterid};
                vec.add(obj);
                // CR9 end
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return vec;
    }

    //CR3 end

    /**
     * 获取生成报表需要的信息
     * @throws QMException 
     */
    public String getReportInforms(String listID) throws QMException
    {
        //CR5 begin
        TechnicsRouteService routeservice=(TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        return routeservice.exportRouteList(listID);
      //CR5 end
    }

    /**
     * 获得产品表头
     * @param productIDs 产品id串
     * @return 产品对象集合
     * @throws QMException 
     */
    public List getProductHead(String productIDs) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        StringTokenizer token = new StringTokenizer(productIDs, ";");
        List list = new ArrayList();
        while(token.hasMoreTokens())
        {
            String productID = token.nextToken();
            QMPartMasterIfc product = (QMPartMasterIfc)pservice.refreshInfo(productID);
            list.add(product);
        }
        return list;
    }

    /**
     * 组装jsp中要显示的信息
     * @param productIDs 产品id串
     * @return
     * @throws QMException 
     */
    public Vector getProductInforms(String productIDs) throws QMException
    {
        TechnicsRouteService routeservice = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        //key:零件号 value:Map( key:产品id value:String[]{ 零件名，路线，数量})
        Map map = routeservice.getProductRelations(productIDs);
        Vector v = new Vector();
        //产品对象集合
        List list1 = getProductHead(productIDs);
        Iterator it = map.keySet().iterator();
        while(it.hasNext())
        {
            String childPartNum = (String)it.next();
            Map mapSub = (Map)map.get(childPartNum);
            List listR = new ArrayList();
            //子件号
            listR.add(childPartNum);
            boolean flag = false;
            for(int i = 0;i < list1.size();i++)
            {
                //产品对象
                QMPartMasterIfc ifc = (QMPartMasterIfc)list1.get(i);
                //产品对应的子件的编号、名称、路线、数量等信息
                String[] strs = (String[])mapSub.get(ifc.getPartNumber());
                if(strs != null)
                {
                    //路线
                    listR.add(strs[1]);
                    //数量
                    listR.add(strs[2]);
                    if(!flag)
                    {
                        //名称和版本，只加一次，在第一次有值的时候
                        listR.add(1, strs[0]);
                    }
                    flag = true;
                }else
                {
                    listR.add(" ");
                    listR.add(" ");
                }
            }
            v.add(listR);
        }
        return v;
    }
    //CR8 begin
    /**
     *通过零部件ID获得零部件Master
     */
    public static String getPartMasterID(String bsoID)
    {
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            QMPartInfo partinfo = (QMPartInfo)pService.refreshInfo(bsoID);
            return partinfo.getMaster().getBsoID();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    //CR8 end
    
    public  String getRouteManagerMode()
    {
        String routeMode = RemoteProperty.getProperty("consrouteManagerMode");
        return routeMode;
    }
    /**
     *通过零部件MasterID获得零部件Master
     */
    public static QMPartMasterInfo getPartMasterID1(String masterID)
    {
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            QMPartMasterInfo masterpartinfo = (QMPartMasterInfo)pService.refreshInfo(masterID);
            return masterpartinfo;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    //CCBegin SS5 
    /**
     * 判断长特路线级别
     */
    public String getCTRouteLevel(String bsoid)throws QMException{
    	String comp=RouteClientUtil.getUserFromCompany();
   	    String level="";
    	if(comp.equals("ct")){
    
    	 PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    	 TechnicsRouteListIfc info = (TechnicsRouteListIfc)pService.refreshInfo(bsoid);
    	 String levelroute=info.getRouteListLevel();
    	 System.out.println("levelroutelevelroute=="+levelroute);
    	 if(levelroute.equalsIgnoreCase("一级路线")){
    		 level="ctfrist";
    	 }else{
    		 level="ctsecond";
    	 }
    	} 
    	 return level;
    }
    //CCEnd SS5
    //CCBegin SS6
    /** 
     * 获得二级路线报表 
     */
    public static Vector getSencondRouteReport(String routeListID)throws QMException{
    	System.out.println("###############3");
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(routeListID);
        if(!routelist.getRouteListLevel().trim().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            throw new QMException("路线表应该是二级路线。");
        }
        QMQuery query = new QMQuery("consListRoutePartLink");
        QueryCondition cond1 = new QueryCondition("leftBsoID", QueryCondition.EQUAL, routeListID);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond2);
        //CCBegin SS7
        UserIfc user = (UserIfc)pService.refreshInfo(routelist.getIterationCreator());
        System.out.println("user.getUsersName()getSencondRouteReport__________________________________________________________"+user.getUsersName());
        String comp=RouteClientUtil.getUserFromCompany();
        if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))   
        {
        	query.addOrderBy("modifyTime");  
        }

        Collection coll = pService.findValueInfo(query);
        String departmentID = routelist.getRouteListDepartment();
        System.out.println("departmentIDdepartmentID=="+departmentID);
        if(departmentID == null || departmentID.trim().length() == 0)
        {
            throw new QMException("二级路线必须有单位。");
        }   
        System.out.println("collcoll22222222==="+coll.size());
        Vector vec=new Vector();
        if (coll != null && coll.size() > 0) 
        {
        	

        	Vector vecListRoutePartLinkBefore = new Vector();
        	Vector vecListRoutePartLinkAfter = new Vector();  
        	for(Iterator iter = coll.iterator();iter.hasNext();)
            {
				ListRoutePartLinkIfc linkInfoOld = (ListRoutePartLinkIfc) iter.next();
                vecListRoutePartLinkBefore.add(linkInfoOld);
            }
            if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
            {
            	vecListRoutePartLinkAfter = sortLinks(routelist , vecListRoutePartLinkBefore);
            }else
            {
            	vecListRoutePartLinkAfter = vecListRoutePartLinkBefore;
            }
        
            for(int ii = 0; ii < vecListRoutePartLinkAfter.size(); ii++)  
            {
            	String[] part=new String[11];
            	ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)vecListRoutePartLinkAfter.elementAt(ii);	
				String partID = linkInfo.getRightBsoID();
				System.out.println("@@@@@@@@@@==="+partID);
				QMPartIfc partInfo = (QMPartInfo) pService.refreshInfo(partID);
	           System.out.println("partInfopartInfo=="+partInfo.getPartNumber());
				String change = linkInfo.getModifyIdenty().trim();
	         System.out.println("changechange=="+change);
				if (change.equals("改图")) {
					change = "G";
				} else if (change.equals("采用")) {
					change = "C";
				} else if (change.equals("取消")) {
					change = "Q";
				} else if (change.equals("新增")) {
					change = "X";
				} else {
					change = "F";
				}
				String des1 = linkInfo.getRouteDescription();
	              System.out.println("des1des1=="+des1);
				if (des1 == null) {
					des1 = "";
				}
				String count = String.valueOf(linkInfo.getProductCount());
				if(count.equals("0"))
						count = "";
				String makeStr = "";
				String assemStr = "";
				if (linkInfo.getMainStr() != null) {
					String[] s = linkInfo.getMainStr().split("=");
					if (s.length > 1) {
						makeStr = s[0];
						assemStr = s[1];
					} else {
						makeStr = s[0];
						assemStr = "";
					}
				}
	
				TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
						.getService("consTechnicsRouteService");
				String[] fristRoute = routeService.findQMPartRouteLevelOne(partID);
				String fristMake = fristRoute[0];
				String fristAss = fristRoute[1];
				part[0]=partID;
				part[1]=change;
				part[2]=partInfo.getPartNumber();
				part[3]=partInfo.getPartName();
				part[4]=count;
				part[5]=fristMake;
				part[6]=fristAss;
				part[7]="生产部";
				part[8]=makeStr;
				part[9]=assemStr;
				part[10]=des1;
				System.out.println("	part[2]	part[2]=="+	part[2]);
				vec.add(part);
			}  
        }
        //CCEnd SS7
        return vec;
    }
    /**
     * 二级报表表头
     * @param routeListID
     * @return
     * @throws QMException
     */
    public static String getSencondRouteReportHead(String routeListID) throws QMException{
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(routeListID);
        String head=routelist.getRouteListNumber()+" "+routelist.getRouteListName();
        return head;
    }
/**
 * 获得二级报表适用车型
 * @param routeListID
 * @return
 * @throws QMException
 */
    public static String getSecondPartProduct(String routeListID)throws QMException{
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)pService.refreshInfo(routeListID);
        String partMasterID=routelist.getProductMasterID();
        String partNum="";
        if(partMasterID!=null&&!partMasterID.equals("")){
        	QMPartMasterInfo part=(QMPartMasterInfo)pService.refreshInfo(partMasterID);
        	partNum=part.getPartNumber();
        }
        return partNum;
    }
    //CCEndSS6
}
