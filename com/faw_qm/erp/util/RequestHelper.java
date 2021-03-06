/** 生成程序 RequestHelper.java    1.0    2004/11/02
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 物料结构表格中列出了生命周期状态为编制中的子件 刘家坤 2014-03-05
 */
package com.faw_qm.erp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;

import com.faw_qm.erp.ejb.service.IntePackService;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.ejb.service.PromulgateNotifyService;
import com.faw_qm.erp.exception.ERPException;
import com.faw_qm.erp.model.IntePackIfc;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.erp.model.InterimMaterialSplitIfc;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.erp.model.SameMaterialIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.resource.util.ResourceExportImportHandler;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;



import com.faw_qm.users.model.ActorIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;

/**
 *
 * <p>Title: 调用服务工具类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @author 王海军
 * @version 1.0
 */
public final class RequestHelper
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RequestHelper.class);

	private static String[] subHead = {"层级", "物料号", "状态", "路线代码", "零件名称",
		"零件号", "版本", "路线"};

	//20080103 begin
	/**
	 * 分号分隔符。用于分隔路线。
	 */
	private String semicolonDelimiter = ";";

	private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";

	private static HashMap routeHashMap;
	
	private static HashMap sameMaterial;
	//20080103 end

	/**
	 * 构造函数。
	 */
	public RequestHelper()
	{
		super();
	}

	public static void initRouteHashMap()throws QMException
	{
		routeHashMap = new HashMap();
		sameMaterial=new HashMap();
		//setSameMaterial();
	}
	


	/**
	 * 这个类的详细注释有时间添加一下
	 * @param part
	 * @param link
	 * @return
	 * @throws QMException
	 */
	public static Vector getRouteBranchs(QMPartIfc part, ListRoutePartLinkInfo link)
	throws QMException
	{
		try
		{
			//System.out.println("linkkkkkkkkkkkkkkkkkkkkkkkkkkkkk======="+link);
			//如果缓存了当前零件的路线信息，则直接返回
			Vector objs = new Vector();
			if(routeHashMap.containsKey(part))
			{
				objs = (Vector)routeHashMap.get(part);
				return objs;
			}
			//获得当前有效的路线关联
			TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
			//这里上来就去执行搜索没有必要questions
			Collection c = getRoutesAndLinks(part);
			Iterator i = c.iterator();
			//这里只可能有一个
			ListRoutePartLinkInfo info = null;

			if(!i.hasNext() && link == null){
				return new Vector();
			}
			//如果是按照基线搜索则执行if语句否则执行else，仅仅是为了得到适合的关联
			if(link == null)
			{
				ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo)i.next();
			
					info = info1;
				System.out.println("caocaocaocaocaocoacoacoaoca====="+info);
			} 
			else
			{
				info = link;
			}

			HashMap map = (HashMap)tr.getRouteBranchs(info.getRouteID());

			if(map == null || map.size() == 0)
				return new Vector();
			Collection coll = getRouteBranches(map);
//			制造路线
			String routeAsString = "";
//			路线全串
			String routeAllCode = "";
//			装配路线
			String routeAssemStr = "";
//			其他路线
			
			String routeOtherCode="";
//			如果路线中有酸则把酸剃掉
			String jHRoute="";
//			是否多路线
			String isMoreRoute="false";
			//历史版本
			String lsbb = info.getAttribute1();
			int ii=coll.size();	
			if(ii>1)
			{
				isMoreRoute="true";
			}
			for(Iterator routeiter = coll.iterator(); routeiter.hasNext();)
			{
				String routeAs[] = (String[])routeiter.next();
				String makeStr = routeAs[1];
//				for(int k=0;k<makeStr.length();k++){
//					
//				}
				String assemStr = routeAs[2];
				String isMainRoute = routeAs[3];
				String otherMake="";
				String otherAss="";
				String otherCode="";
				if(isMainRoute.equals("是"))
				{
					jHRoute=makeStr;
					
					routeAsString = makeStr;
					routeAssemStr = assemStr;
					if(routeAssemStr != "")
						routeAllCode = routeAsString + ";" + routeAssemStr;
					else
						routeAllCode = routeAsString;
				}
				//处理除主要路线外的其他路线
				else
				{
					//其它路线中的制造路线
					if(makeStr!=null && makeStr!="")
					{
						otherMake=makeStr;
					}
					else
					{
						otherMake="";
					}
					//其它路线中的装配路线
					if(assemStr!=null && assemStr!="")
					{
						otherAss=assemStr;
					}
					else
					{
						otherAss="";
					}
					//组合其它路线
					if(otherMake!="" && otherAss!="")
					{
						otherCode=otherMake+";"+otherAss;
					}
					else if(otherMake=="" && otherAss!="")
					{
						otherCode=otherAss;
					}
					else if(otherMake!="" && otherAss=="")
					{
						otherCode=otherMake;
					}
					else
					{
						otherCode="";
					}
				}
				//如果有多个其它路线，将多个其它路线组合，用“：”分割
				if(otherCode!="")
				{
					routeOtherCode=routeOtherCode+":"+otherCode;
				}
			}
			objs.add(routeAsString);
			objs.add(routeAllCode);
			objs.add(routeAssemStr);
			objs.add(routeOtherCode);
			objs.add(isMoreRoute);
			//CCBegin by chudaming 20100322
			objs.add(jHRoute);
			objs.add(lsbb);
			//CCBegin SS1
			objs.add(info.getStockID());
			//CCEnd SS1
			//CCEnd by chudaming 20100322
			routeHashMap.put(part, objs);
//			System.out.println("objsobjsobjsobjs=============="+objs);
//			System.out.println("routeAllCoderouteAllCoderouteAllCoderouteAllCode100304=============="+routeAllCode);
			return objs;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	/**
	 * 这个类的详细注释有时间添加一下
	 * @param part
	 * @param link
	 * @return
	 * @throws QMException
	 */
	public static Vector getConsRouteBranchs(QMPartIfc part, ListRoutePartLinkInfo link)
	throws QMException
	{
		try
		{
			System.out.println("linkkkkkkkkkkkkkkkkkkkkkkkkkkkkk======="+link);
			//如果缓存了当前零件的路线信息，则直接返回
			Vector objs = new Vector();
			if(routeHashMap.containsKey(part))
			{
				objs = (Vector)routeHashMap.get(part);
				return objs;
			}
			//获得当前有效的路线关联
			TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
			//这里上来就去执行搜索没有必要questions
			Collection c = getRoutesAndLinks(part);
			System.out.println("c1111111111111111111======="+c);
			Iterator i = c.iterator();
			//这里只可能有一个
			ListRoutePartLinkInfo info = null;

			if(!i.hasNext() && link == null){
				return new Vector();
			}
			//liujiakun 20131224 这里getCurrentEffctive有错误所以去掉了
			//如果是按照基线搜索则执行if语句否则执行else，仅仅是为了得到适合的关联
			if(link == null)

				info = link;

			HashMap map = (HashMap)tr.getRouteBranchs(info.getRouteID());

			if(map == null || map.size() == 0)
				return new Vector();
			Collection coll = getRouteBranches(map);
//			制造路线
			String routeAsString = "";
//			路线全串
			String routeAllCode = "";
//			装配路线
			String routeAssemStr = "";
//			其他路线
			
			String routeOtherCode="";
//			如果路线中有酸则把酸剃掉
			String jHRoute="";
//			是否多路线
			String isMoreRoute="false";
			int ii=coll.size();	
			if(ii>1)
			{
				isMoreRoute="true";
			}
			for(Iterator routeiter = coll.iterator(); routeiter.hasNext();)
			{
				String routeAs[] = (String[])routeiter.next();
				String makeStr = routeAs[1];
//				for(int k=0;k<makeStr.length();k++){
//					
//				}
				String assemStr = routeAs[2];
				String isMainRoute = routeAs[3];
				String otherMake="";
				String otherAss="";
				String otherCode="";
				if(isMainRoute.equals("是"))
				{
					//CCBegin by chudaming 20100322
					if(makeStr.indexOf("酸")>0&&makeStr.indexOf("(酸)")<0){
						String a=makeStr.substring(0,makeStr.indexOf("酸")-1);
						String b=makeStr.substring(makeStr.indexOf("酸")+1,makeStr.length());
						jHRoute=a+b;
					}else if(makeStr.indexOf("酸")==0){ 
						jHRoute=makeStr.substring(2,makeStr.length());
					}else{
						jHRoute=makeStr;
					}
					//CCEnd by chudaming 20100322
					routeAsString = makeStr;
					routeAssemStr = assemStr;
					if(routeAssemStr != "")
						routeAllCode = routeAsString + ";" + routeAssemStr;
					else
						routeAllCode = routeAsString;
				}
				//处理除主要路线外的其他路线
				else
				{
					//其它路线中的制造路线
					if(makeStr!=null && makeStr!="")
					{
						otherMake=makeStr;
					}
					else
					{
						otherMake="";
					}
					//其它路线中的装配路线
					if(assemStr!=null && assemStr!="")
					{
						otherAss=assemStr;
					}
					else
					{
						otherAss="";
					}
					//组合其它路线
					if(otherMake!="" && otherAss!="")
					{
						otherCode=otherMake+";"+otherAss;
					}
					else if(otherMake=="" && otherAss!="")
					{
						otherCode=otherAss;
					}
					else if(otherMake!="" && otherAss=="")
					{
						otherCode=otherMake;
					}
					else
					{
						otherCode="";
					}
				}
				//如果有多个其它路线，将多个其它路线组合，用“：”分割
				if(otherCode!="")
				{
					routeOtherCode=routeOtherCode+":"+otherCode;
				}
			}
			objs.add(routeAsString);
			objs.add(routeAllCode);
			objs.add(routeAssemStr);
			objs.add(routeOtherCode);
			objs.add(isMoreRoute);
			//CCBegin by chudaming 20100322
			objs.add(jHRoute);
			//CCEnd by chudaming 20100322
			routeHashMap.put(part, objs);
//			System.out.println("objsobjsobjsobjs=============="+objs);
//			System.out.println("routeAllCoderouteAllCoderouteAllCoderouteAllCode100304=============="+routeAllCode);
			return objs;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private static Collection getRouteBranches(HashMap map)
	throws QMException
	{
		Collection v = new Vector();
		Object branchs[] = map.keySet().toArray();
		for(int i = 0; i < branchs.length; i++)
		{
			PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
			String isMainRoute = "是";
			if(branchinfo.getMainRoute())
				isMainRoute = "是";
			else
				isMainRoute = "否";
			String makeStr = "";
			String assemStr = "";
			String tempcode = "";
			Object nodes[] = (Object[])map.get(branchinfo);
			Vector makeNodes = (Vector)nodes[0];
//			System.out.println("100224makeNodes========="+makeNodes);
			RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];
//			System.out.println("100224asseNodeasseNode========="+asseNode.getBsoID());
			if(makeNodes != null && makeNodes.size() > 0)
			{
				for(int m = 0; m < makeNodes.size(); m++)
				{
					RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
					String departid = node.getNodeDepartment();
					BaseValueIfc codeInfo = pservice.refreshInfo(departid);
					String makeCodeStr = "";
					if(codeInfo instanceof CodingIfc)
						//CCBegin by chudaming20100121
						makeCodeStr = ((CodingIfc)codeInfo).getShorten();
					//CCEnd by chudaming20100121
					if(codeInfo instanceof CodingClassificationIfc)
						makeCodeStr = ((CodingClassificationIfc)codeInfo).getClassSort();
					if(makeStr == "")
					{
						makeStr = makeStr + makeCodeStr;
						tempcode = makeCodeStr;
					} else
						if(!tempcode.equals(makeCodeStr))
						{
							makeStr = makeStr + "-" + makeCodeStr;
							tempcode = makeCodeStr;
						}
				}

			}
			if(asseNode != null)
			{
				String departid = asseNode.getNodeDepartment();
				BaseValueIfc codeInfo = pservice.refreshInfo(departid);
				String assemcode = "";
				if(codeInfo instanceof CodingIfc)
					//CCBegin by chudaming20100121
					assemcode = ((CodingIfc)codeInfo).getShorten();
				//CCEnd by chudaming20100121
				if(codeInfo instanceof CodingClassificationIfc)
					assemcode = ((CodingClassificationIfc)codeInfo).getClassSort();
				assemStr = assemcode;
			}
			if(makeStr == null || makeStr.equals(""))
				makeStr = "";
			if(assemStr == null || assemStr.equals(""))
				assemStr = "";
			String array[] = {
					String.valueOf(i + 1), makeStr, assemStr, isMainRoute
			};
			v.add(array);
		}

		return v;
	}

	private static Collection getRoutesAndLinks(QMPartIfc part)
	throws QMException
	{
		
//		CodingIfc code = ResourceExportImportHandler.importCode("取消","更改标记");
//		String qx = "";
//		if(code!=null)
//			qx = code.getBsoID();
//		System.out.println("qx="+qx);
		 
		PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("consListRoutePartLink");
		int i = query.appendBso("consTechnicsRouteList", false);
		QueryCondition qc = new QueryCondition("leftBsoID", "bsoID");
	    query.addCondition(0, i, qc);
		QueryCondition qc1 = new QueryCondition("rightBsoID", QueryCondition.EQUAL, part.getBsoID());
		query.addAND();
		query.addCondition(qc1);
		QueryCondition qc2 = new QueryCondition("lifeCycleState" ,QueryCondition.EQUAL,"RELEASED");
		query.addAND();
		query.addCondition(i,qc2);
		query.addOrderBy("modifyTime",true);
//		System.out.println("part.getBsoID()1111111111111111111======="+part.getBsoID());
//		System.out.println("query1111111111111111111======="+query.getDebugSQL());
	
		return pservice.findValueInfo(query, false);
	}
	
	
//	public Collection searchSameMaterial(String partNumber,String routeCode,String sameMaterialNumber)throws QMException
//	{
//		try
//		{
//			PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
//			QMQuery query=new QMQuery("SameMaterial");
//			//query.addCondition(new QueryCondition("bsoID",QueryCondition.LIKE,getLikeSearchString("*")));
//			if(partNumber!=null&&partNumber.trim().length()>0)
//			{
//				if(query.getConditionCount()>0)
//				{
//					query.addAND();
//				}
//				query.addCondition(new QueryCondition("partNumber",QueryCondition.LIKE,getLikeSearchString(partNumber)));
//			}
//			if(routeCode!=null&&routeCode.trim().length()>0)
//			{
//				if(query.getConditionCount()>0)
//				{
//					query.addAND();
//				}
//				query.addCondition(new QueryCondition("routeCode",QueryCondition.LIKE,getLikeSearchString(routeCode)));
//			}
//			
//			if(sameMaterialNumber!=null&&sameMaterialNumber.trim().length()>0)
//			{
//				if(query.getConditionCount()>0)
//				{
//					query.addAND();
//				}
//				query.addCondition(new QueryCondition("sameMaterialNumber",QueryCondition.LIKE,getLikeSearchString(sameMaterialNumber)));
//			}
//			Collection coll=(Collection)pservice.findValueInfo(query);
//			return coll;
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			throw new QMException(ex);
//		}
//	}

	/**
	 * 调用服务的方法。
	 * 用于判断当前的调用是在服务端还是客户端，如果是服务端即按照服务端的方式调用服务；如果是客户端即按照客户端的方式调用服务。
	 * @param serviceName String 服务名称。
	 * @param methodName String 方法名称。
	 * @param paraClass Class[] 参数所属类集合。
	 * @param para Object[] 参数集合。
	 * @throws QMException
	 * @return Object
	 */
	public static final Object request(final String serviceName,
			final String methodName, Class[] paraClass, Object[] para)
	throws QMException
	{
		if(paraClass == null)
			paraClass = new Class[]{};
		if(para == null)
			para = new Object[]{};
		Object obj = null;
		final RequestServer server = RequestServerFactory.getRequestServer();
		if(server != null)
		{
			ServiceRequestInfo info = new ServiceRequestInfo();
			info.setServiceName(serviceName);
			info.setMethodName(methodName);
			info.setParaClasses(paraClass);
			info.setParaValues(para);
			obj = server.request(info);
			return obj;
		}
		else
		{
			try
			{
				//20080103 begin
				logger.error("request(String, String, Class[], Object[])"+EJBServiceHelper
						.getService(serviceName).getClass()); //$NON-NLS-1$
				//20080103 end
				final BaseService baseService = EJBServiceHelper
				.getService(serviceName);
				final Method method = baseService.getClass().getMethod(
						methodName, paraClass);
				obj = method.invoke(baseService, para);
			}
			catch (InvocationTargetException exception)
			{
				logger
				.error(
						"request(String, String, Class[], Object[])", exception); //$NON-NLS-1$
				final Throwable ex = exception.getTargetException();
				//20080103 begin
				ex.printStackTrace();
				//20080103 end
				if(ex instanceof QMException)
					throw (QMException) ex;
				else
					throw new QMException(ex.getMessage());
			}
			catch (Exception ex)
			{
				logger.error("request(String, String, Class[], Object[])", ex); //$NON-NLS-1$
				throw new QMException(ex, ex.getMessage());
			}
			return obj;
		}
	}

	/**
	 * 通过采用通知书或更改采用通知书获取要拆分物料的零部件
	 * @param caiyongid
	 * @param noticeid
	 * 
	 * @return
	 */
	/*public static Vector getSplitParts(String caiyongid, String noticeid)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getSplitParts(String, String) - start"); //$NON-NLS-1$
        }
        Vector vector = new Vector();
        try
        {
            PromulgateNotifyService noticeService = (PromulgateNotifyService) EJBServiceHelper
                    .getService("PromulgateNotifyService");
            if((noticeid == null || noticeid.length() <= 0)
                    && (caiyongid != null && caiyongid.length() > 0))
            {
                vector = noticeService.getPartsByProId(caiyongid);
            }
            if((caiyongid == null || caiyongid.length() <= 0)
                    && (noticeid != null && noticeid.length() > 0))
            {
                PersistService pservice = (PersistService) EJBServiceHelper
                        .getPersistService();
                AdoptNoticeIfc adopt = (AdoptNoticeIfc) pservice
                        .refreshInfo(noticeid);
                adopt.getAdoptNumber();
                Collection coll = AdoptNoticeServiceHelper
                        .findAllPartByReference(noticeid);
                vector.addAll(coll);
            }
        }
        catch (QMException e)
        {
            logger.error("getSplitParts(String, String)", e); //$NON-NLS-1$
            e.printStackTrace();
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getSplitParts(String, String) - end"); //$NON-NLS-1$
        }
        return vector;
    }*/

	/**
	 * 通过一个包含有多个零部件id的字串（以";"(分号)分隔）获取所有零部件的拆分物料
	 * @param partids
	 * @return 返回结果是一个数组集合，数组第一个元素是：零部件名称；第二个元素：是否可更新(字串：true,false;)；第三个元素：零部件编号；
	 * 第四个元素：版本；第五个元素：更改标记；第六个元素：路线；第七个元素：一个二维数组，一维是数组元素个数，二维第一个元素：物料层级；第二个元素：物料号；第三个元素：物料状态
	 * 第四个元素：物料路线代码
	 * 第八个元素：零部件id.
	 * 第九个元素：物料id
	 */
	public static Vector getSplitMateriel(String partids)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getSplitMateriel(String) - start"); //$NON-NLS-1$
		}
		Vector returnVector = new Vector();
		if(logger.isDebugEnabled())
		{
			logger.debug("getSplitMateriel(String) - end"); //$NON-NLS-1$
		}
		return returnVector;
	}

	/**
	 * 通过零部件id获取零部件对应的属性
	 * @param partid
	 * @return 名称、 版本、路线、状态
	 */
	public static String[] getPartAttr(String partid)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getPartAttr(String) - start"); //$NON-NLS-1$
		}
		String[] returnStringArray = new String[5];
		if(logger.isDebugEnabled())
		{
			logger.debug("getPartAttr(String) - end"); //$NON-NLS-1$
		}
		return returnStringArray;
	}

	public static String isMaterialUpdate(String partNumber) throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("isMaterialUpdate(String) - start"); //$NON-NLS-1$
		}
		boolean flag = true;
		MaterialSplitService ms = (MaterialSplitService) EJBServiceHelper
		.getService("MaterialSplitService");
		List list = ms.getAllInterimMSplit(partNumber);
		String owerid = "";
		if(list != null && !list.isEmpty())
		{
			if(list.iterator().hasNext())
				owerid = ((InterimMaterialSplitIfc) list.iterator().next())
				.getOwner();
			SessionService session = (SessionService) EJBServiceHelper
			.getService("SessionService");
			String curuserid = session.getCurUserID();
			flag = owerid.equals(curuserid);
			String returnString = Boolean.toString(flag);
			if(logger.isDebugEnabled())
			{
				logger.debug("isMaterialUpdate(String) - end"); //$NON-NLS-1$
			}
			return returnString;
		}
		else
		{
			String returnString = Boolean.toString(flag);
			if(logger.isDebugEnabled())
			{
				logger.debug("isMaterialUpdate(String) - end"); //$NON-NLS-1$
			}
			return returnString;
		}
	}

	/**
	 * 通过零部件id获取零部件的拆分物料
	 * @param partid
	 * @return 拆分物料对象数组 层级、物料号、状态、路线代码、物料id
	 */
	public static Vector getPartSplit(String partNumber, String partnums)
	throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getPartSplit(String, String) - start"); //$NON-NLS-1$
		}
		Vector vector = new Vector();
		MaterialSplitService ms = (MaterialSplitService) EJBServiceHelper
		.getService("MaterialSplitService");
		ms.createInterimMaterial(partnums);
		HashMap map = ms.getAllInterimMaterial(partNumber);
		Set set = map.keySet();
		if(set != null && !set.isEmpty())
		{
			Object obj = set.iterator().next();
			if(obj instanceof Object[])
			{
				Object[] a = (Object[]) obj;
				vector.add(a[0]);
				vector.add(a[1]);
				vector.add(a[2]);
				vector.add(a[3]);
				vector.add(a[4]);
				vector.add(a[5]);
			}
			List mSplitList = (List) map.get(obj);
			int i = mSplitList.size();
			Object[][] b = new Object[i][5];
			for (int j = 0; j < i; j++)
			{
				Object[] m = (Object[]) mSplitList.get(j);
				b[j] = m;
			}
			vector.add(b);
		}
		if(logger.isDebugEnabled())
		{
			logger.debug("getPartSplit(String, String) - end"); //$NON-NLS-1$
		}
		return vector;
	}

	public static BaseValueIfc getBaseValueIfc(String id)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getBaseValueIfc(String) - start"); //$NON-NLS-1$
		}
		try
		{
			PersistService pservice = (PersistService) EJBServiceHelper
			.getPersistService();
			BaseValueIfc base = pservice.refreshInfo(id);
			if(logger.isDebugEnabled())
			{
				logger.debug("getBaseValueIfc(String) - end"); //$NON-NLS-1$
			}
			return base;
		}
		catch (QMException e)
		{
			logger.error("getBaseValueIfc(String)", e); //$NON-NLS-1$
			if(logger.isDebugEnabled())
			{
				logger.debug("getBaseValueIfc(String) - end"); //$NON-NLS-1$
			}
			return null;
		}
	}

	public static IntePackIfc findIntegrationIfc(String s) throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("findIntegrationIfc(String) - start"); //$NON-NLS-1$
		}
		IntePackIfc pack = (IntePackIfc) getBaseValueIfc(s);
		if(logger.isDebugEnabled())
		{
			logger.debug("findIntegrationIfc(String) - end"); //$NON-NLS-1$
		}
		return pack;
	}

	/**
	 * 将零部件获取的拆分物料结果通过页面形式组织数据
	 * @param partid
	 * @param flag 如果true :返回完整的HTML页面形式;false:只返回body体部分
	 * @return
	 * @throws QMException
	 */
	public static void getSplitMateHTML(String partids)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getSplitMateHTML(String) - start"); //$NON-NLS-1$
		}
		String logpath = RemoteProperty.getProperty("logpath");
		try
		{
			//ERP用户要求将日志文件存放到当前用户的文件夹下
			RequestHelper requestHelper = new RequestHelper();
			Class[] theClass = {};
			Object[] theObjs = {};
			// 获得当前用户ID
			UserIfc curUserIfc = (UserIfc) requestHelper.request("SessionService",
					"getCurUserInfo", theClass, theObjs);
			File f = new File(logpath);
			if(!f.exists())
			{
				f.mkdir();
			}
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String timestr = time.toString();
			int r = timestr.indexOf(" ");
			timestr = timestr.substring(0, r);
			File f1 = new File(logpath + "/" + timestr + ".log");
			if(!f1.exists() || !f1.isFile())
			{
				f1.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(f1, true), true);
			StringBuffer buffer = new StringBuffer();
			buffer
			.append("<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\" style=\"border-collapse: collapse\" bordercolor=\"#111111\" width=\"94%\" align=\"center\">");
			buffer.append("<tr>");
			enter(buffer);
			for (int i = 0; i < subHead.length; i++)
			{
				String head = subHead[i];
				buffer.append("<td>" + head + "</td>");
				enter(buffer);
			}
			buffer.append("</tr>");
			enter(buffer);
			Vector mates = materialSplitResult(partids);
			for (Iterator ite = mates.iterator(); ite.hasNext();)
			{
				Object[] a = (Object[]) ite.next();
				Object name = a[0];
				Object num = a[2];
				Object vers = "";
				if(a[3] != null)
					vers = a[3];
				Object road = "";
				if(a[4] != null)
					road = a[4];
				Object[][] aa = (Object[][]) a[7];
				int m = aa.length;
				for (int j = 0; j < m; j++)
				{
					Object cj = aa[j][1];
					Object wlh = aa[j][2];
					Object zt = aa[j][3];
					Object lxdm = "";
					if(aa[j][4] != null)
						lxdm = aa[j][4];
					buffer.append("<tr>");
					enter(buffer);
					if(j == 0)
					{
						buffer.append("<td>" + cj + "</td>");
						enter(buffer);
						buffer.append("<td>" + wlh + "</td>");
						enter(buffer);
						buffer.append("<td>" + zt + "</td>");
						enter(buffer);
						buffer.append("<td>" + lxdm + "</td>");
						enter(buffer);
						buffer
						.append("<td rowspan=" + m + ">" + name
								+ "</td>");
						enter(buffer);
						buffer.append("<td rowspan=" + m + ">" + num + "</td>");
						enter(buffer);
						buffer
						.append("<td rowspan=" + m + ">" + vers
								+ "</td>");
						enter(buffer);
						buffer
						.append("<td rowspan=" + m + ">" + road
								+ "</td>");
						enter(buffer);
						buffer.append("</tr>");
						enter(buffer);
					}
					else
					{
						buffer.append("<td>" + cj + "</td>");
						enter(buffer);
						buffer.append("<td>" + wlh + "</td>");
						enter(buffer);
						buffer.append("<td>" + zt + "</td>");
						enter(buffer);
						buffer.append("<td>" + lxdm + "</td>");
						enter(buffer);
						buffer.append("</tr>");
						enter(buffer);
					}
				}
			}
			buffer.append("</table>");
			enter(buffer);
			out.println(buffer);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			logger.error("getSplitMateHTML(String)", e); //$NON-NLS-1$
			e.printStackTrace();
		}
		if(logger.isDebugEnabled())
		{
			logger.debug("getSplitMateHTML(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * 回车
	 * 
	 * @param buffer
	 *            StringBuffer 内容缓存
	 */
	private static void enter(StringBuffer buffer)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("enter(StringBuffer) - start"); //$NON-NLS-1$
		}
		buffer.append("\n");
		if(logger.isDebugEnabled())
		{
			logger.debug("enter(StringBuffer) - end"); //$NON-NLS-1$
		}
	}


	/**
	 * 获取所有的集成包类型
	 * @return
	 */
	public static IntePackSourceType[] getIntePackTypes()
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getIntePackTypes() - start"); //$NON-NLS-1$
		}
		IntePackSourceType[] returnIntePackSourceTypeArray = IntePackSourceType
		.getIntePackSourceTypeSet();
		if(logger.isDebugEnabled())
		{
			logger.debug("getIntePackTypes() - end"); //$NON-NLS-1$
		}
		return returnIntePackSourceTypeArray;
	}

	public static Vector searchAllIntePacks(String name, String type,
			String sourcenum, String state, String creator, String createTime,
			String publisher, String publishTime)
	{
		if(logger.isDebugEnabled())
		{
			logger
			.debug("searchAllIntePacks(String, String, String, String, String, String, String, String) - start"); //$NON-NLS-1$
		}
		logger
		.debug("************** ent search all inte pack *****************");
		Vector vector = new Vector();
		try
		{
			IntePackService ipservice = (IntePackService) EJBServiceHelper
			.getService("IntePackService");
			Collection coll = ipservice.searchIntePackByID(name, type,
					sourcenum, state, creator, createTime, publisher,
					publishTime);
			logger.debug("****************** the search result is :" + coll);
			vector.addAll(coll);
		}
		catch (QMException e)
		{
			logger
			.error(
					"searchAllIntePacks(String, String, String, String, String, String, String, String)", e); //$NON-NLS-1$
			e.printStackTrace();
		}
		if(logger.isDebugEnabled())
		{
			logger
			.debug("searchAllIntePacks(String, String, String, String, String, String, String, String) - end"); //$NON-NLS-1$
		}
		return vector;
	}

	/**
	 * 匹配字符查询处理。将字符串oldStr中的"/*"转化成"*"，"*"转化成"%"，"%"不处理。
	 * 例 "shf/*pdm%cax*"  转化成 "shf*pdm%cax%"
	 * @param oldStr
	 * @return 转化后的匹配字符查询串
	 */
	private static String getLikeSearchString(String oldStr)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getLikeSearchString(String) - start"); //$NON-NLS-1$
		}
		if(oldStr == null || oldStr.trim().equals(""))
		{
			if(logger.isDebugEnabled())
			{
				logger.debug("getLikeSearchString(String) - end"); //$NON-NLS-1$
			}
			return oldStr;
		}
		char ac[] = oldStr.toCharArray();
		int acLength = ac.length;
		for (int j = 0; j < acLength; j++)
		{
			if(ac[j] == '*')
			{
				if(j > 0 && ac[j - 1] == '/')
				{
					for (int k = j - 1; k < acLength - 1; k++)
					{
						ac[k] = ac[k + 1];
					} //end for k
					acLength--;
					ac[acLength] = ' ';
				}
				else
				{
					ac[j] = '%';
				}
			}
			if(ac[j] == '?')
			{
				ac[j] = '_';
			}
		} //end for j
		String resultStr = (new String(ac)).trim();
		if(logger.isDebugEnabled())
		{
			logger.debug("getLikeSearchString(String) - end"); //$NON-NLS-1$
		}
		return resultStr;
	}

	/**
	 * 获取拆分后结果
	 * @param parts 以“;”隔开的partid 集合
	 * @return
	 */
	public static Vector materialSplitResult(String parts) throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("materialSplitResult(String) - start"); //$NON-NLS-1$
		}
		Vector vector = new Vector();
		PersistService pservice = (PersistService) EJBServiceHelper
		.getPersistService();
		ArrayList list = new ArrayList();
		if(parts != null)
		{
			StringTokenizer toke = new StringTokenizer(parts, ";");
			while (toke.hasMoreTokens())
			{
				QMPartIfc part = (QMPartIfc) pservice.refreshInfo(toke
						.nextToken());
				if(!list.contains(part.getPartNumber()))
					list.add(part.getPartNumber());
			}
		}
		HashMap map = MaterialServiceHelper.getAllMaterial(list);
		Set set = map.keySet();
		for (Iterator ite = set.iterator(); ite.hasNext();)
		{
			Object[] object = new Object[8];
			Object obj = ite.next();
			if(obj instanceof Object[])
			{
				Object[] a = (Object[]) obj;
				object[0] = a[0];
				object[1] = a[1];
				object[2] = a[2];
				object[3] = a[3];
				object[4] = a[4];
				object[5] = a[5];
				object[6] = a[6];
			}
			List mSplitList = (List) map.get(obj);
			int i = mSplitList.size();
			Object[][] b = new Object[i][5];
			for (int j = 0; j < i; j++)
			{
				Object[] m = (Object[]) mSplitList.get(j);
				b[j] = m;
			}
			object[7] = b;
			vector.add(object);
		}
		if(logger.isDebugEnabled())
		{
			logger.debug("materialSplitResult(String) - end"); //$NON-NLS-1$
		}
		return vector;
	}

	public static Vector searchMaterielResult(String partnum, String partname,
			String partversion, String matenum) throws QMException
			{
		if(logger.isDebugEnabled())
		{
			logger
			.debug("searchMaterielResult(String, String, String, String) - start"); //$NON-NLS-1$
		}
		logger.debug("********* enter search materiel start*******************"
				+ new Timestamp(System.currentTimeMillis()));
		PersistService ps = (PersistService) EJBServiceHelper
		.getService("PersistService");
		Vector vector = new Vector();
		Collection materesult = new Vector();
		List list = new ArrayList();
		QMQuery query1 = new QMQuery("MaterialSplit");
		if(partnum != null && partnum.trim().length() > 0)
		{
			QueryCondition cond1 = new QueryCondition("partNumber",
					QueryCondition.LIKE, getLikeSearchString(partnum));
			query1.addCondition(cond1);
		}
		else
		{
			//如果查询条件物料号不是空
			if(partname != null && partname.trim().length() > 0)
			{
				QueryCondition cond = new QueryCondition("partName",
						QueryCondition.LIKE, getLikeSearchString(partname));
				query1.addCondition(cond);
			}
			if(partversion != null && partversion.trim().length() > 0)
			{
				if(query1.getConditionCount() > 0)
				{
					query1.addAND();
				}
				QueryCondition cond1 = new QueryCondition("partVersion",
						QueryCondition.LIKE, getLikeSearchString(partversion));
				query1.addCondition(cond1);
			}
			if(matenum != null && matenum.trim().length() > 0)
			{
				if(query1.getConditionCount() > 0)
				{
					query1.addAND();
				}
				QueryCondition cond2 = new QueryCondition("materialNumber",
						QueryCondition.LIKE, getLikeSearchString(matenum));
				query1.addCondition(cond2);
			}
		}
		materesult = ps.findValueInfo(query1);
		for (Iterator ite = materesult.iterator(); ite.hasNext();)
		{
			MaterialSplitIfc mate = (MaterialSplitIfc) ite.next();
			String partnumber = mate.getPartNumber();
			if(!list.contains(partnumber))
				list.add(partnumber);
		}
		logger.debug("********* enter search materiel 2 *******************"
				+ new Timestamp(System.currentTimeMillis()));
		HashMap map = MaterialServiceHelper.getAllMaterial(list);
		logger.debug("********* enter search materiel 3 *******************"
				+ new Timestamp(System.currentTimeMillis()));
		Set set = map.keySet();
		for (Iterator ite = set.iterator(); ite.hasNext();)
		{
			Object[] object = new Object[8];
			Object obj = ite.next();
			if(obj instanceof Object[])
			{
				Object[] a = (Object[]) obj;
				object[0] = a[0];
				object[1] = a[1];
				object[2] = a[2];
				object[3] = a[3];
				object[4] = a[4];
				object[5] = a[5];
				object[6] = a[6];
			}
			List mSplitList = (List) map.get(obj);
			int i = mSplitList.size();
			Object[][] b = new Object[i][5];
			for (int j = 0; j < i; j++)
			{
				Object[] m = (Object[]) mSplitList.get(j);
				b[j] = m;
			}
			object[7] = b;
			vector.add(object);
		}
		logger.debug("********* enter search materiel 4 *******************"
				+ new Timestamp(System.currentTimeMillis()));
		if(logger.isDebugEnabled())
		{
			logger
			.debug("searchMaterielResult(String, String, String, String) - end"); //$NON-NLS-1$
		}
		return vector;
			}

	public static Vector getMaterialByInte(String inte) throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("getMaterialByInte(String) - start"); //$NON-NLS-1$
		}
		Vector vector = new Vector();
		PersistService ps = (PersistService) EJBServiceHelper
		.getService("PersistService");
		IntePackIfc inteifc = (IntePackIfc) ps.refreshInfo(inte);
		String id = inteifc.getSource();
		BaseValueIfc base = getBaseValueIfc(id);
		String partsid = "";
		/*if(base instanceof AdoptNoticeIfc)
        {
            Collection coll = AdoptNoticeServiceHelper
                    .findAllPartByReference(base.getBsoID());
            for (Iterator ite = coll.iterator(); ite.hasNext();)
            {
                QMPartIfc part = (QMPartIfc) ite.next();
                if(partsid.length() == 0)
                    partsid = part.getBsoID();
                else
                    partsid = partsid + ";" + part.getBsoID();
            }
        }*/
		if(base instanceof PromulgateNotifyIfc)
		{
			PromulgateNotifyService noticeService = (PromulgateNotifyService) EJBServiceHelper
			.getService("PromulgateNotifyService");
			Collection coll = noticeService.getPartsByProId(base.getBsoID());
			for (Iterator ite = coll.iterator(); ite.hasNext();)
			{
				QMPartIfc part = (QMPartIfc) ite.next();
				if(partsid.length() == 0)
					partsid = part.getBsoID();
				else
					partsid = partsid + ";" + part.getBsoID();
			}
		}
		vector = materialSplitResult(partsid);
		if(logger.isDebugEnabled())
		{
			logger.debug("getMaterialByInte(String) - end"); //$NON-NLS-1$
		}
		return vector;
	}

	public String findUserNameByID(String userID)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("findUserNameByID(String) - start"); //$NON-NLS-1$
		}
		String userName = null;
		if(userID != null)
		{
			try
			{
				PersistService persistService = (PersistService) EJBServiceHelper
				.getService("PersistService");
				ActorIfc userInfo = (ActorIfc) persistService.refreshInfo(
						userID, false);
				userName = userInfo.getUsersDesc();
				if(userName == null || userName.trim().length() == 0)
				{
					userName = userInfo.getUsersName();
				}
			}
			catch (Exception e)
			{
				logger.error("findUserNameByID(String)", e); //$NON-NLS-1$
			} //end catch
		}
		if(logger.isDebugEnabled())
		{
			logger.debug("findUserNameByID(String) - end"); //$NON-NLS-1$
		}
		return userName;
	}

	/**
	 * 判断集成包是否能发布。
	 * @param intePackIfc：将要发布的集成包
	 * @return
	 */
	public static String publishOption(String intePackIfcID)
	{
		String result = "can";
		try
		{
			IntePackIfc intePackIfc = findIntegrationIfc(intePackIfcID);
			//获取Session服务
			SessionService sService;
			sService = (SessionService) EJBServiceHelper
			.getService("SessionService");
			//得到当前用户
			UserIfc user = (UserIfc) sService.getCurUserInfo();
			//集成包的创建者才能发布集成包
			if(!user.getBsoID().equals(intePackIfc.getCreator()))
			{
				result = "cannot";
			}
			//来源为空的集成包不能再发布
			else if(getBaseValueIfc(intePackIfc.getSource()) == null)
			{
				result = "cannot";
			}
			//已经发布过的集成包不允许再发布
			else if(intePackIfc.getState() == 9)
			{
				result = "unallowed";
			}
		}
		catch (Exception e)
		{
			logger.error("ispublish(IntePackIfc)", e); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * 获取集成包关联的零部件。
	 * 
	 * @param intePackID
	 * @return
	 */
	public Vector getProductsByIntePack(String intePackID) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProductsByIntePack(String) - start"); //$NON-NLS-1$
		}
		Vector parts = new Vector();
		IntePackInfo intePackInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
			.getService("PersistService");
			intePackInfo = (IntePackInfo) persistService.refreshInfo(
					intePackID, false);
			if (intePackInfo != null) {
				IntePackSourceType intePackType = intePackInfo.getSourceType();
				if (intePackType != null) {
					if (intePackType
							.equals(IntePackSourceType.PROMULGATENOTIFY)) {
						parts = PromulgateNotifyHelper
						.getProductsByProID(intePackInfo.getSource());
					} /*else if (intePackType
							.equals(IntePackSourceType.ADOPTNOTICE)) {
						Collection coll = AdoptNoticeServiceHelper
								.findAllPartByReference(intePackInfo
										.getSource());
						Iterator iter = coll.iterator();
						while (iter.hasNext()) {
							parts.add(iter.next());
						}
					}*/
				}
			}
		} catch (Exception ex) {
			logger.error("getProductsByIntePack(String)", ex); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getProductsByIntePack(String) - end"); //$NON-NLS-1$
		}
		return parts;
	}

	//20080103 begin
	/**
	 *  根据零部件的编号得到零部件的值对象和相应的路线（多路线拆分成单路线的）。
	 * @param partIDs：零部件的BsoID，中间用“;”作为分割符
	 */
	public Object[] getPartAndRoute(String partIDs) throws QMException
	{
		return getPartAndRoute(partIDs,false);
	}
	//20080103 end

	/**
	 *  根据零部件的编号得到零部件的值对象和相应的路线（多路线拆分成单路线的）。
	 * @param partIDs：零部件的BsoID，中间用“;”作为分割符
	 * @param isDef：是否采用默认的路线的标识
	 */
	public Object[] getPartAndRoute(String partIDs,boolean usedDefaultRoute) throws QMException
	{
		logger.debug("getPartAndRoute begin partIDs==" + partIDs +" isDef == "+usedDefaultRoute );
		//多路线的零部件的集合，元素是数组：
		//数组中第一个是零部件的的值对象，第二个是零部件的路线（多路线中的一个），第三个是选中的标志。
		List mutPartAndRouteList = new ArrayList();
		//单路线零部件的BsoID集合。
		String singlePartIDs = "";
		//单路线零部件的路线集合。
		String singlePartRoutes = "";
		//物料拆分服务。
		MaterialSplitService matService = (MaterialSplitService) EJBServiceHelper
		.getService("MaterialSplitService");
		//零部件值对象的集合。
		List partList = matService.getAllPartByBsoID(partIDs);
		logger.debug("getPartAndRoute partList.size()==" + partList.size());
		//获取路线的属性定义的轻量级对象的集合。
		List routeDefList = null;
		if(usedDefaultRoute){
			routeDefList = matService.getDefaultRouteDefList();
		}
		else{
			routeDefList = matService.getRouteDefList();
		}
		//零部件值对象。
		QMPartIfc partIfc = null;
		//零部件的属性容器。
		DefaultAttributeContainer container = null;
		HashMap mutPart=new HashMap();
		Object[] obj = null;
		String route;
		boolean isFirstAdd = true;
		for (int i = 0; i < partList.size(); i++)
		{
			partIfc = new QMPartInfo();
			try
			{
				partIfc = (QMPartIfc) RequestHelper.request("IBAValueService",
						"refreshAttributeContainerWithoutConstraints",
						new Class[]{IBAHolderIfc.class}, new Object[]{partList
						.get(i)});
			}
			catch (QMException e)
			{
				//"刷新属性容器时出错！"
				logger.error(Messages.getString("Util.7"), e); //$NON-NLS-1$
				throw new ERPException(e, RESOURCE, "Util.7", null);
			}
			//20080114 begin
			if(mutPart.containsKey(partIfc.getPartNumber())){
				//20080215 begin
				//              break;
				continue;
				//20080215 end
			}
			//            mutPart.put(partIfc.getPartNumber(), partIfc.getPartNumber());
			//20080114 end 
			container = (DefaultAttributeContainer) partIfc
			.getAttributeContainer();
			//物料拆分的路线属性。如：04－02－01;04-03-01
			StringBuffer routeAsString = new StringBuffer();
			for (int j = 0; j < routeDefList.size(); j++)
			{
				AbstractValueView[] values = container
				.getAttributeValues((AttributeDefDefaultView) routeDefList
						.get(j));
				if(!routeAsString.toString().equals("") && values.length > 0)
				{
					logger.debug("routeAsString1111111111==" + routeAsString);
					routeAsString.append(semicolonDelimiter);
				}
				for (int k = 0; k < values.length; k++)
				{
					routeAsString.append(((StringValueDefaultView) values[k])
							.getValue());
					logger.debug("k==" + k + "  values.length=="
							+ values.length);
					if(k < values.length - 1)
					{
						logger.debug("routeAsString2222222222=="
								+ routeAsString);
						routeAsString.append(semicolonDelimiter);
						logger.debug("routeAsString3333333333=="
								+ routeAsString);
					}
				}
			}
			StringTokenizer st = new StringTokenizer(routeAsString.toString(),
					semicolonDelimiter);
			//零部件路线的数目。
			int count = 0;
			while (st.hasMoreTokens() && count < 2)
			{
				route = st.nextToken();
				logger.debug("routeAsString444444444 route==" + route);
				count++;
			}
			st = new StringTokenizer(routeAsString.toString(),
					semicolonDelimiter);
			//如果为多路线
			if(count >= 2)
			{
				boolean isFirst = true;
				while (st.hasMoreTokens())
				{
					route = st.nextToken();
					logger.debug("routeAsString444444444 route==" + route);
					//                    logger.debug("routeAsString444444444 mutPart.containsKey(partIfc.getPartNumber())==" +
					//                          mutPart.containsKey(partIfc.getPartNumber()));
					obj = new Object[3];
					obj[0] = partIfc;
					obj[1] = route;
					if (isFirst) {
						obj[2] = "false";
					} else {
						obj[2] = "true";
					}
					mutPartAndRouteList.add(obj);
					isFirst = false;
					mutPart.put(partIfc.getPartNumber(), partIfc
							.getPartNumber());

				}
			}
			else
			{
				while (st.hasMoreTokens())
				{
					route = st.nextToken();
					logger.debug("routeAsString444444444 route==" + route);
					if(isFirstAdd)
					{
						singlePartIDs = partIfc.getBsoID();
						singlePartRoutes = route;
						isFirstAdd = false;
					}
					else
					{
						singlePartIDs += semicolonDelimiter
						+ partIfc.getBsoID();
						singlePartRoutes += semicolonDelimiter + route;
					}
				}
			}
			logger.debug("routeAsString444444444 partIfc==" + partIfc);
			logger.debug("routeAsString444444444 partIfc.getPartNumber()=="
					+ partIfc.getPartNumber());
		}
		obj = new Object[3];
		obj[0] = mutPartAndRouteList;
		obj[1] = singlePartIDs;
		obj[2] = singlePartRoutes;
		logger.debug("routeAsString 123456 singlePartIds==" + singlePartIDs);
		logger.debug("routeAsString 123456 singlePartRoutes)==" + singlePartRoutes);
		logger.debug("getPartAndRoute 123456 end obj==" + obj);
		return obj;
	}
	/**
	 * 获取集成包关联的零部件。
	 * 
	 * @param intePackID
	 * @return
	 */
	public Vector getPartsByIntePack(String intePackID) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProductsByIntePack(String) - start"); //$NON-NLS-1$
		}
		Vector parts = new Vector();
		IntePackInfo intePackInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
			.getService("PersistService");
			intePackInfo = (IntePackInfo) persistService.refreshInfo(
					intePackID, false);
			if (intePackInfo != null) {
				IntePackSourceType intePackType = intePackInfo.getSourceType();
				if (intePackType != null) {
					if (intePackType
							.equals(IntePackSourceType.PROMULGATENOTIFY)) {
						parts = PromulgateNotifyHelper 
						.getPartsByProId(intePackInfo.getSource());
					} /*else if (intePackType
							.equals(IntePackSourceType.ADOPTNOTICE)) {
						Collection coll = AdoptNoticeServiceHelper
								.findAllPartByReference(intePackInfo
										.getSource());
						Iterator iter = coll.iterator();
						while (iter.hasNext()) {
							parts.add(iter.next());
						}
					}*/
				}
			}
		} catch (Exception ex) {
			logger.error("getProductsByIntePack(String)", ex); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getProductsByIntePack(String) - end"); //$NON-NLS-1$
		}
		return parts;
	}

	
	/**
	 * 自动发布零部件的物料。
	 * @param partNumber
	 * @param promulgateNotifyFlag
	 * @throws QMException
	 */
	public void publishMaterial(String partNumber, String promulgateNotifyFlag)
	throws QMException
	{
		// MaterialSplitService materialSplitService = (MaterialSplitService) EJBServiceHelper
		//         .getService("MaterialSplitService");
		// materialSplitService.publishMaterial(partNumber,promulgateNotifyFlag);
	}
}
