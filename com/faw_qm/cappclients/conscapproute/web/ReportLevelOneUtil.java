/**
 * 生成程序 ReportLevelOneUtil.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.sql.Timestamp;
import java.util.*;

import com.faw_qm.persist.ejb.service.*;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.users.model.ActorInfo;
import com.faw_qm.util.*;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.workflow.engine.ejb.service.WfEngineHelper;
import com.faw_qm.workflow.engine.model.WfActivityIfc;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.work.model.AssignmentBallotLinkInfo;
import com.faw_qm.workflow.work.model.WfBallotInfo;
import com.faw_qm.workflow.work.model.WorkItemIfc;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.*;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.consroute.ejb.entity.ListRoutePartLink;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.codemanage.ejb.entity.CodingEJB;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.config.util.ConfigSpec;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jfpublish.receive.PublishHelper;
//CCBegin SS18
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
//CCEnd SS18
//CCBegin SS40
import com.jf.util.jfuputil;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
//CCEnd SS40

//SS1 变速箱路线升级为R5后,查看报表数据显示有误  pante 20130729
//SS2 变速箱路线装配路线没有时不显示"无",改为显示空"" pante 20130729
//SS3 变速箱路线报表显示解放发布源版本,如是变速箱的件则显示部件版本,都没有的时候才显示零件本身版本 pante 2013-11-01
//SS4 变速箱路线报表模版错误修改 maxt 20131203
//SS5 工艺路线表使用数量串行 pante 2013-12-23
//SS6 变速箱用户要求:改图标识零件如果使用数量为0,则不显示在报表中 pante 2013-12-26
//SS7 修改报表中有零件显示不出来的问题 pante 2013-12-27
//SS8 修改试制路线查看时显示废弃模版问题 pante 2013-12-27
//SS9 变速箱用户要求:所有使用数量为0的都显示为空  pante 2013-12-27
//SS10 轴齿中心增加毛坯状态属性 Liuyang 2014-1-9
//SS11 轴齿中心路线说明 liuyang 2014-1-10
//SS12 轴齿中心获取路线单位 liuyang 2014-1-10
//SS13 轴齿中心查看报表零部件查看链接为关联零部件，不是最新零部件  liuyang 2014-2-28
//SS14 历史工艺路线数量是存在partindex中，不在关联中。对于数量，先去partindex的，如果为0或为""，则取关联的。 liunan 2014-2-26
//SS15 轴齿中心查看报表版本为关联零部件版本 Liuyang 2014-4-8
//SS16 轴齿中心说明每行单独一行 Liuyang2014-4-17
//SS17 变速箱说明内容取工艺路线实际内容，不再取固定全值。 liunan 2014-5-9
//SS18 对历史数据中存qmpartmaster情况的处理 liunan 2014-5-30
//SS19 变速箱艺废，不再重复显示“废弃上述零部件”描述。 liunan 2014-8-5
//SS20 变速箱查看路线时，版本也要显示当初保存的版本。 liunan 2014-9-12
//SS21 ss20引起了历史数据的空指针错误。 liunan 2014-9-24
//SS22 变速箱查看路线时，没有获取保存那一版零部件的源版本，而是最新版的源版本。 liunan 2014-10-13
//SS23 轴齿中心要求零件版本带版序 pante 2014-10-09
//SS24 变速箱零部件查不到源版本的出现空指针错误 liunan 2014-11-11
//SS25 变速箱历史数据中版本是记录在备注中，现在查看存在问题，这类艺准需要从备注中获取版本。 liunan 2014-11-20
//SS26 长特增加会签功能 liuyang 2014-9-1
//SS27 长特路线报表用供应商信息代替“专<协>” 文柳 2014-12-18
//SS28 变速箱双版本时，报表中只获取了第一个大版本，要求显示两个大版本，如B.3.C.4，显示为B.C liunan 2014-12-25
//SS29 A005-2014-3025 版本获取不对，没有获取当时关联零部件的版本，而是取得最新零部件版本，并且无法获取iba发布源版本，修改获取iba方法。 liunan 2014-12-31
//SS30 长特路线报表用供应商信息按照顺序代替“专<协>” 文柳 2014-12-18
//SS31 长特路线报表用,零件号后面需要显示零件版本   文柳 2015-2-6
//SS32 长特一级路线报表，零件版本号显示不正确  文柳  2015-3-9
//SS33 变速箱查看报表时，零部件链接到最新版，而不是二级工艺路线所关联的零部件。 liunan 2015-3-25
//SS34 长特路线报表，去掉供应商替换。在编辑路线上实现功能 。 文柳  2015-4-15
//SS35 长特路线报表，零件版本去掉版序号 文柳  2015-4-15
//SS36 轴齿报表要求把零部件编号和版本合到一个单元格中，版本先取汽研源版本，再取零部件版本。 liunan 2015-4-29
//SS37 变速箱前准、临准报表，根据的内容获取方式调整，用户可能随意编辑根据内容，根据要求获取“说明：”前内容。 liunan 2015-7-8
//SS38 A037-2015-0182以S、2S开头的零部件导出时不显示零部件版本。 liuzhicheng 2015-12-8
//SS39 艺毕有可能不根据艺准来发放，所以说明中不会带有“及艺准”字样，导致处理说明字符串异常。 liunan 2016-3-22
//SS40 成都工艺路线整合，工艺路线5种报表 liunan 2016-8-15
//SS41 A004-2017-3474 变速箱工艺路线报表获取版本要求跟编辑界面一致。因此从关联中获取版本。 liunan 2017-3-19
/**
 * <p>
 * Title:生成一级路线报表
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * (问题一)20060629 周茁修改 修改原因:工艺路线生成报表操作速度慢
 * @author 刘明
 * @version 1.0
 */

public class ReportLevelOneUtil {

	private static TechnicsRouteListIfc myRouteList;
	private static Object lock = new Object(); //yanqi-20060918
	private static Collection sendToColl;//yanqi-20060918
	//CCBegin SS12
	private static Collection routeListUnit =new ArrayList();
	//CCEnd SS12
	public ReportLevelOneUtil() {
	}

	/**
	 * 获得界面头部信息
	 *
	 * @param routeListID
	 *            路线表的BsoID
	 * @return 路线表的编号、名称、产品、日期
	 */
	public static String[] getHeader(TechnicsRouteListIfc routelist) throws QMException {
		PersistService pService = (PersistService) EJBServiceHelper.getService(
				"PersistService");
		try {

			String name = routelist.getRouteListName();
			//liunan
			String partNumber = "";

			if (routelist.getProductMasterID() != null) {
				QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
							.refreshInfo(routelist.getProductMasterID());
				partNumber = partmaster.getPartNumber();
			}
	
			String product = routelist.getRouteListNumber();
			Timestamp stamp = routelist.getCreateTime();
			String year = String.valueOf(stamp.getYear()+1900);
			String month = String.valueOf(stamp.getMonth() + 1);
			String day = String.valueOf(stamp.getDate());
			String date = year + "年" + month + "月" + day + "日";
			String[] c = {
					//CCBegin SS40
					name, product, date, partNumber, routelist.getRouteListState()};
					//CCEnd SS40
			return c;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得界面头部信息
	 *
	 * @param routeListID
	 *            路线表的BsoID
	 * @return 路线表的编号、名称、产品、日期
	 */
	public static String[] getHeader(String routeListID) {
		routeListID = routeListID.trim();
		PersistService pService = null;
		try {
			pService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			TechnicsRouteListIfc routelist = (TechnicsRouteListIfc) pService
					.refreshInfo(routeListID);
			myRouteList = routelist;
			String number = routelist.getRouteListNumber();
			String name = routelist.getRouteListName();
			String list = number + "（" + name + "）" + "的一级工艺路线报表";

			QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
					.refreshInfo(routelist.getProductMasterID());
			String product = partmaster.getPartNumber() + "_"
					+ partmaster.getPartName();

			String year = String.valueOf(Calendar.getInstance().get(
					Calendar.YEAR));
			String month = String.valueOf(Calendar.getInstance().get(
					Calendar.MONTH) + 1);
			String day = String.valueOf(Calendar.getInstance().get(
					Calendar.DATE));
			String date = year + "年" + month + "月" + day + "日";
			String[] c = {
					list, product, date};
			return c;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * 获得指定路线表的一级路线报表的数据
	 *
	 * @return 返回集合的元素为数组arrayObjs。其元素依次为序号、零部件编号、零部件名、
	 *         一级路线串（字符串数组array的集合。array[0]为制造路线串；array[1]为装配路线串）
	 */
	public static Collection getFirstLeveRouteListReport() {
//		System.out.println("222222222222222222222222222222222222222222222222222222222");
		Vector v = new Vector();
		Vector resultVector = new Vector();
		try {
			TechnicsRouteService routeService = (TechnicsRouteService)
					EJBServiceHelper
					.getService("consTechnicsRouteService");
			CodeManageTable map = routeService
					.getFirstLeveRouteListReport(myRouteList);
			Enumeration enum1 = map.keys();
			int i = 1;
			HashMap resultsMap = new HashMap();
			while (enum1.hasMoreElements()) {
				QMPartMasterIfc partmaster = (QMPartMasterIfc) enum1
						.nextElement();
				//(问题一)20060629 周茁修改 修改原因:工艺路线生成报表操作速度慢,
				//从technicsrouteBranch对象中直接取出路线串字符串，不用恢复node对象 begin
				Collection branches = (Collection) map.get(partmaster);
				Vector strVector = new Vector();
				if (branches != null && branches.size() > 0) {
					Iterator ite = branches.iterator();
					while (ite.hasNext()) {
						String makeStr = "";
						String assemStr = "";
						String unionStr = (String) ite.next();
//						System.out.println("unionStr====================="+unionStr);
						if (unionStr != null) {
							StringTokenizer hh = new StringTokenizer(unionStr, "@");
							if (hh.hasMoreTokens()) {
								makeStr = hh.nextToken();
								assemStr = hh.nextToken();
							}
						}
						String[] array = {
								makeStr, assemStr};
						strVector.add(array);
					}
				} //end
				else {
					String[] array = {
							"", ""};
					strVector.add(array);
				}
				//(问题一)20060629 周茁修改 begin
				Object[] arrayObjs = {
						String.valueOf(i++),
						partmaster.getPartNumber(), partmaster.getPartName(),
						"", strVector};

				//add end

				//modify by guoxl on 20080310(一级路线生成报表显示时，序号排列错误)

				// resultsMap.put(partmaster.getBsoID(),arrayObjs);
				resultsMap.put(partmaster.getPartNumber(), arrayObjs);
				v.add(partmaster);

			}
			//获得最新值对象集合

			HashMap result = routeService.getLatestsVersion1(v);

			Vector tempVec = new Vector();
			int temp = 1;
			for (int iii = 0; iii < v.size(); iii++) {
				QMPartMasterIfc part = (QMPartMasterIfc) v.elementAt(iii);
				if (tempVec.contains(part.getPartNumber())) {
					continue;
				}

				Object[] objs = ( (Object[]) resultsMap.get(part.getPartNumber()));
				objs[0] = String.valueOf(temp++);
				objs[3] = ( (QMPartIfc) (result.get(part.getPartNumber()))).
						getVersionValue();
				resultVector.add(objs);
				tempVec.addElement(part.getPartNumber());

			}
		} //modify end
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultVector;
		//(问题一)20060629 周茁修改 end
	}

	/**
	 * 获得指定路线表的一级路线报表的数据
	 *
	 * @return 返回集合的元素为数组arrayObjs。其元素依次为序号、零部件编号、零部件名、
	 *         一级路线串（字符串数组array的集合。array[0]为制造路线串；array[1]为装配路线串）
	 */
	public static Collection getFirstLeveRouteListReport(TechnicsRouteListIfc routelist) throws QMException {
//		System.out.println("33333333333333333333333333333333333333333333333333333333333");
		///// 20070525 liuming add
		ArrayList v = new ArrayList();

		HashMap countMap = new HashMap();
		Vector indexVector = routelist.getPartIndex();
//		System.out.println("   ReportLevelOneUtil getFirstLeveRouteListReport ()indexVector = "+indexVector);
		if (indexVector != null && indexVector.size() > 0) {
			int size2 = indexVector.size();
			String key = null;
			for (int k = 0; k < size2; k++) {
				String[] ids = (String[]) indexVector.elementAt(k);
				if(ids.length==3){
//					System.out.println("       ids .length="+ids.length+" 0:"+ids[0]+" 1:"+ids[1]+" 2:"+ids[2]);
					if (countMap.containsKey(ids[0])) {
						key = ids[0] + "K" + k;
					}
					else {
						key = ids[0];
					}
//					System.out.println("key = "+key+".........count = "+ids[2]);
					countMap.put(key, ids[2]); //关联零件有重复的可能，需要加以区别
				}
			}
		}

		try {
			PersistService pservice = (PersistService) EJBServiceHelper.
					getPersistService();
			TechnicsRouteService routeService =
					(TechnicsRouteService) EJBServiceHelper.getService(
							"consTechnicsRouteService");
			//获得当前用户的配置规范
			PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
			if(configSpecIfc==null ||configSpecIfc.getStandard()==null||configSpecIfc.getStandard().getViewObjectIfc()==null)
			{
				//SSBegin SS1
				configSpecIfc = getPartConfigSpecByViewName("设计视图");
				//SSEnd SS1
			}

			CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);
			//liunan
			Enumeration enum1 = map.keys();
			int i = 0;
			String countInProduct = null;

			/////////////循环对象：每个关联零部件
			while (enum1.hasMoreElements())
			{
				//CCBegin SS7
				QMPartMasterIfc partmaster = null;
				Object[] obj = null;
				Object o = enum1.nextElement();
				if(o instanceof QMPartMasterIfc){
					partmaster = (QMPartMasterIfc) o;
					 obj = (Object[])map.get(partmaster);
				}
				else if(o instanceof QMPartIfc){
					QMPartIfc part = (QMPartIfc) o;
					partmaster = (QMPartMasterIfc) pservice.refreshInfo(part.getMasterBsoID());
					 obj = (Object[])map.get(part);
				}
				//CCEnd SS7
				ListRoutePartLinkIfc listPartRoute =(ListRoutePartLinkIfc)obj[1];
				
				String s = (String)obj[0];
				//(问题一)20060629 周茁修改 修改原因:工艺路线生成报表操作速度慢,
				//从technicsrouteBranch对象中直接取出路线串字符串，不用恢复node对象 begin
				//            Collection branches = (Collection) obj[0];
				//            Object[] branches = ((Collection) obj[0]).toArray();
				//            String[] hhhh = getPartMakeAndAssRouteInRouteList(branches);
				String partMakeRoute = "";;
				String myAssRoute = "";
				//SSBegin SS1
				String sub = "";
				sub = s.substring(1, s.length()-1);
				String str[] = sub.split(",");
				for(int e = 0;e<str.length;e++){
					if(sub.contains("=")){
						String str1[] = str[e].split("=");
						if(e==str.length-1){
							if(str1.length>1){
								partMakeRoute += str1[0];
								//SSBegin SS2
								if(str1[1].equals("无"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1];
							}
							else if(str1.length==1){
								partMakeRoute += str1[0];
 								myAssRoute += " ";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
						else{
							if(str1.length>1){
								partMakeRoute += str1[0]+"/";
								//SSBegin SS2
								if(str1[1].equals("无"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1){
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
					}
					if(sub.contains("@")){
						String str1[] = str[e].split("@");
						if(e==str.length-1){
							if(str1.length>1){
								partMakeRoute += str1[0];
								//SSBegin SS2
								if(str1[1].equals("无"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1];
							}
							else if(str1.length==1){
								partMakeRoute += str1[0];
								myAssRoute += " ";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
						else{
							if(str1.length>1){
								partMakeRoute += str1[0]+"/";
								//SSBegin SS2
								if(str1[1].equals("无"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1){
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
					}
//					if(s.contains("=")){
//						String[] ss = s.split("=");
//						partMakeRoute = ss[0];
//						if(ss.length>1){
//							myAssRoute = ss[1];
//						}
//					}
//					if(s.contains("@")){
//						String[] ss = s.split("@");
//						partMakeRoute = ss[0];
//						if(ss.length>1){
//							myAssRoute = ss[1];
//						}
//					}	
					//SSEnd SS1
				}

				//CCBegin SS33
				/*QMPartIfc part = routeService.getLastedPartByConfig(partmaster,configSpecIfc); //liuming 20061215
				if(part ==null)
				{
					continue;
				}*/
				//CCEnd SS33

				countInProduct = "";
				String masterID = partmaster.getBsoID();
				if (countMap.containsKey(masterID)) {
					countInProduct = countMap.get(masterID).toString();
					countMap.remove(masterID);
				}
				else {
					String keya = null;
					for (Iterator jt = countMap.keySet().iterator(); jt.hasNext(); ) {
						keya = jt.next().toString();
						if (keya.startsWith(masterID)) {
							masterID = keya;
							countInProduct = countMap.get(masterID).toString();
							countMap.remove(masterID);
							break;
						}
					}
				}
				//System.out.println(">>> masterID = "+masterID+".....countInProduct = "+countInProduct);
				if (countInProduct.trim().equals("0")) {
					countInProduct = "";
				}


				///////处理“每车数量”完毕
				/////////////////////////////////////////////////////////////



				//设置更改标识和备注
				TechnicsRouteIfc route = null;
				String change = "";
				StringBuffer remark = new StringBuffer("") ;
				if (listPartRoute != null)
				{
					String routeID = listPartRoute.getRouteID();
					//System.out.println("routeID is " + routeID );
					if (routeID != null)
					{
						route = (TechnicsRouteIfc) pservice.refreshInfo(routeID,false);
					}
				}
				String version = "";
		    	//CCBegin SS15
				String partBsoID=listPartRoute.getRightBsoID();
				//CCBegin SS18
				//QMPartIfc partInfo = (QMPartIfc) pservice.refreshInfo(partBsoID);
				QMPartIfc partInfo = null;
				if(partBsoID.startsWith("QMPartMaster"))
				{
					QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) pservice.refreshInfo(partBsoID);
					VersionControlService vs = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
					Vector nv = (Vector)vs.allIterationsOf((MasteredIfc)partMasterInfo);
					partInfo = (QMPartIfc) nv.get(0);
				}
				else
				{
					partInfo = (QMPartIfc) pservice.refreshInfo(partBsoID);
				}
				//CCEnd SS18
			    String comp =RouteClientUtil.getUserFromCompany();
				//CCEnd SS15
//				System.out.println("listPartRoute.getRouteID()=========="+listPartRoute.getRouteID());

				if(listPartRoute.getRouteID()!=null){
	
					if (listPartRoute != null)
					{//liunan
						//Begin by chudaming 20090716
						String des1 ="";
						//SSBegin SS1
						if(listPartRoute.getModifyIdenty()!=null){
//							change = route.getRouteDescription().substring(route.getRouteDescription().indexOf(":")+1,route.getRouteDescription().indexOf(":")+2);
							//SS4 BEGIN
//							change = listPartRoute.getModifyIdenty().trim();
							change = listPartRoute.getModifyIdenty().trim();

							if(change.equals("改图")){
								change = "G";
							}else if(change.equals("采用")){
								change = "C";
							}else if(change.equals("取消")){
								change = "Q";
							}else if(change.equals("新增")){
								change = "X";
							}else{
								change = "F";
							}
							//SS4 END
//							des1 = route.getRouteDescription().substring(route.getRouteDescription().indexOf(")")+1,route.getRouteDescription().length());
							des1 = listPartRoute.getRouteDescription();
//							System.out.println("--------"+change+"--------"+des1+"--------"+part.getVersionValue());
							//SSEnd SS1
							//End by chudaming 20090716
							if(des1==null)
							{
								des1= "";
							}
						}
						remark.append(des1) ;
					}
		
					if (listPartRoute != null)
					{
						//CCBegin SS15
					    if(comp.equals("zczx")){
					    	//CCBegin SS36
					    	/*IBAValueService ibaService = null;
							ibaService = (IBAValueService) PublishHelper
										.getEJBService("IBAValueService");

							DefaultAttributeContainer attc = (DefaultAttributeContainer) partInfo
									.getAttributeContainer();
							if (attc!= null) {
								AbstractValueView[] valueview = attc
										.getAttributeValues();
								int m = valueview.length;
								for (int ii = 0; ii < m; ii++) {
									if ((valueview[ii].getDefinition()
											.getName()).toLowerCase().trim()
											.equals("sourceversion")) {
										AbstractValueIfc value = IBAValueObjectsFactory
												.newAttributeValue(
														valueview[ii], partInfo);
										version = ((StringValueIfc) value)
												.getValue().toString();
									} else if ((valueview[ii].getDefinition()
											.getName()).toLowerCase().trim()
											.equals("partVersion")) {
										AbstractValueIfc value = IBAValueObjectsFactory
												.newAttributeValue(
														valueview[ii], partInfo);
										version = ((StringValueIfc) value)
												.getValue().toString();
									}
								}
							}else{
								version = listPartRoute.getPartVersion();
//								version=version.trim().substring(0, 1);////SS23
							}
							
							if(version==""||version.equals("")){
								version = listPartRoute.getPartVersion();
//								version=version.trim().substring(0, 1);////SS23
							}*/
					  version = getPartSourceVersion(partInfo.getBsoID());
						if(version==""||version.equals(""))
						{
							version = listPartRoute.getPartVersion();
							if(version!=null)
							{
								String[] vv = version.trim().split("[.]");//split无法处理.  因此[.]
								if(vv.length==2)//单版本情况
								{
									version=vv[0];
								}
								else if(vv.length==4)//双版本情况
								{
									version=vv[0]+"."+vv[2];
								}
							}
							else
							{
								version = partInfo.getVersionID();
							}
						}
						else
						{
							version = version.trim().substring(0, version.indexOf("."));
						}
						if(!version.equals(""))
						{
							version = "/"+version;
						}
						//CCEnd SS36
	                    }
					//CCBegin SS41
					else if(comp.equals("bsx"))
					{
						version = listPartRoute.getPartVersion();
						if(version!=null&&(version==""||version.equals("")))
						{
							//CCBegin SS25
							if(remark!=null)
							{
								if(remark.indexOf(":"+change)!=-1)
								{
									version = remark.substring(1,remark.indexOf(":"));
								}
							}
							//CCEnd SS25
						}
						if(version!=null && version.indexOf(".")!=-1)
						{
							version = version.trim().substring(0, version.indexOf("."));
						}
					}
					//CCEnd SS41
	                    else{
	                   //CCEnd SS15
						//Begin by chudaming 20090716
//						if(listPartRoute.getRightBsoID()!=null)
						//CCBegin SS3
						//CCBegin SS29
						/*IBAValueService ibaService = null;
						ibaService = (IBAValueService) PublishHelper
									.getEJBService("IBAValueService");
						//CCBegin SS22
						//part = (QMPartInfo) ibaService
								//.refreshAttributeContainerWithoutConstraints(part);
						//DefaultAttributeContainer attc = (DefaultAttributeContainer) part
						DefaultAttributeContainer attc = (DefaultAttributeContainer) partInfo
								.getAttributeContainer();
						//CCEnd SS22
						//CCBegin SS24
						//System.out.println("partInfo==="+partInfo+"  and attc=="+attc);
						if (attc!= null)
						{
						//CCEnd SS24
						AbstractValueView[] valueview = attc.getAttributeValues();
						int m = valueview.length;
						for (int ii = 0; ii < m; ii++) {
							if ((valueview[ii].getDefinition().getName())
									.toLowerCase().trim().equals("sourceversion")) {
								AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
										//CCBegin SS22
										//valueview[ii], part);
										valueview[ii], partInfo);
										//CCEnd SS22
								version = ((StringValueIfc) value).getValue().toString();
							}
							else if((valueview[ii].getDefinition().getName())
									.toLowerCase().trim().equals("partVersion")) {
								AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
										//CCBegin SS22
										//valueview[ii], part);
										valueview[ii], partInfo);
										//CCEnd SS22
								version = ((StringValueIfc) value).getValue().toString();
							}
						}
						//CCBegin SS24
					}*/
					version = getPartSourceVersion(partInfo.getBsoID());
					//CCEnd SS29
					//System.out.println("version==="+version);
						//CCEnd SS24
						if(version==""||version.equals("")){
							//CCBegin SS20
							//version = part.getVersionID();//0716
							version = listPartRoute.getPartVersion();
							//System.out.println("version 111==="+version);
							//CCBegin SS21
							//version=version.trim().substring(0, 1);
							if(version!=null)
							{
								//CCBegin SS28
								//version=version.trim().substring(0, 1);
								String[] vv = version.trim().split("[.]");//split无法处理.  因此[.]
								if(vv.length==2)//单版本情况
								{
									version=vv[0];
								}
								else if(vv.length==4)//双版本情况
								{
									version=vv[0]+"."+vv[2];
								}
								//CCEnd SS28
							}
							else
							{
								//CCBegin SS29
								//version = part.getVersionID();
								version = partInfo.getVersionID();
								//CCEnd SS29
							}
							//CCEnd SS21
							//CCEnd SS20
							//System.out.println("version 222==="+version);
						}
						//CCBegin SS29
						else
						{
							version = version.trim().substring(0, version.indexOf("."));
						}
						//CCEnd SS29
						//CCEnd SS3	
						//End by chudaming 20090716
	                    }
					}

				}

//				else{
//					if (listPartRoute != null)
//					{//liunan
//						//Begin by chudaming 20090716
//						String des1 ="";
//						if(listPartRoute.getRouteDescription()!=null){
//							change = listPartRoute.getRouteDescription().substring(listPartRoute.getRouteDescription().indexOf(":")+1,listPartRoute.getRouteDescription().indexOf(":")+2);
//							des1 = listPartRoute.getRouteDescription().substring(listPartRoute.getRouteDescription().indexOf(")")+1,listPartRoute.getRouteDescription().length());
//							//End by chudaming 20090716
//							if(des1==null)
//							{
//								des1= "";
//							}
//						}
//						remark.append(des1) ;
//					}
//
//
//					if (listPartRoute != null)
//					{
//						//Begin by chudaming 20090716
//						if(listPartRoute.getRouteDescription()!=null)
//							version = listPartRoute.getRouteDescription().substring(1,listPartRoute.getRouteDescription().indexOf(":"));//0716
//						//End by chudaming 20090716
//
//					}
//				}

				/*System.out.println(">>> partmaster = "+partmaster.getPartNumber()+".....version = "+version
                             +".....countInProduct = "+countInProduct+".....partMakeRoute = "+partMakeRoute+".....part.getBsoID() = "+part.getBsoID()
                             +".....remark.toString() = "+remark.toString());*/
				/////添加了备注 20070122
				//CCBegin SS5
//				Object[] arrayObjs =
//					{
//						change, partmaster.getPartNumber(),
//						version,
//						partmaster.getPartName(), countInProduct, partMakeRoute,
//						myAssRoute, part.getBsoID(),remark.toString()
//					};
				//CCBegin SS10
				String sockID=listPartRoute.getStockID();
				//CCEnd SS10
				//CCBegin SS6
				//CCBegin SS14
				if(countInProduct.equals("0")||countInProduct.equals(""))
				//CCEnd SS14
				countInProduct = String.valueOf(listPartRoute.getProductCount());

				//CCBegin SS9
				//CCBegin SS14
				//if(listPartRoute.getProductCount()==0)
				if(countInProduct.equals("0"))
				//CCEnd SS14
					countInProduct = "";
				//CCEnd SS9
				//CCEnd SS6
				//CCBegin SS12
//                String comp =RouteClientUtil.getUserFromCompany();
				if (comp.equals("zczx")) {
					if (partMakeRoute != null && !partMakeRoute.equals("")) {
						if (partMakeRoute.indexOf("/") != -1) {
							String[] r1 = partMakeRoute.split("/");
							for (int m = 0; m < r1.length; m++) {
								if (r1[m] != null && !r1[m].equals("")) {
									String[] route1 = r1[m].split("-");
									for (int n = 0; n < route1.length; n++) {
										if (routeListUnit.contains(route1[n])) {
										} else {
											routeListUnit.add(route1[n]);
										}
									}
								}
							}
						} else {
							String[] route1 = partMakeRoute.split("-");
							for (int n = 0; n < route1.length; n++) {
								if (routeListUnit.contains(route1[n])) {
								} else {
									routeListUnit.add(route1[n]);
								}
							}
						}
					}
					if (myAssRoute != null && !myAssRoute.equals("")) {
						if (myAssRoute.indexOf("/") != -1) {
							String[] r2 = myAssRoute.split("/");
							for (int m = 0; m < r2.length; m++) {
								if (r2[m] != null && !r2[m].equals("")) {
									String[] route2 = r2[m].split("-");
									for (int n = 0; n < route2.length; n++) {
										if (routeListUnit.contains(route2[n])) {
										} else {
											routeListUnit.add(route2[n]);
										}
									}
								}
							}
					 	} else {
							String[] route2 = myAssRoute.split("-");
							for (int n = 0; n < route2.length; n++) {
								if (routeListUnit.contains(route2[n])) {

								} else {
									routeListUnit.add(route2[n]);
								}
							}
						}
					}
				}
				//CCEnd SS12
				//CCBegin SS10
//				Object[] arrayObjs =
//					{
//						change, partmaster.getPartNumber(),
//						version,
//						partmaster.getPartName(), countInProduct, partMakeRoute,
//						myAssRoute, part.getBsoID(),remark.toString()
//					};
	           //CCBegin SS13
				   
//				String partBsoID=listPartRoute.getRightBsoID();
//				QMPartIfc partInfo = (QMPartIfc) pservice.refreshInfo(partBsoID);
				if(comp.equals("zczx")){
					//CCBegin SS36
					//Object[] arrayObjs ={change,partInfo.getPartNumber(),version,partInfo.getPartName(),countInProduct, partMakeRoute,
					i++;
					Object[] arrayObjs ={Integer.toString(i),change,partInfo.getPartNumber()+version,partInfo.getPartName(),countInProduct, partMakeRoute,
					//CCEnd SS36
							myAssRoute, partInfo.getBsoID(),remark.toString(),sockID				
					};
					v.add(arrayObjs);
				}else{
				//CCEnd SS13
				
				//CCBegin SS27
				//CCBegin SS30
				if(comp.equals("ct")){
					//处理当制造路线或者装配路线为单一路线时，去掉“/”
					partMakeRoute = partMakeRoute.trim();
					if(!partMakeRoute.equals("")){
						//判断第一个和最后一个字符，是不是"/"
						String be = partMakeRoute.substring(0,1);
						String le = partMakeRoute.substring(partMakeRoute.length()-1,partMakeRoute.length());
						if(partMakeRoute.equals("/")){//制造路线只是"/"的情况
							partMakeRoute = "";
						}else{
							if(be.equals("/")){//开始是"/"，去掉第一个"/"
								partMakeRoute=partMakeRoute.substring(1,partMakeRoute.length());
							}
							if(le.equals("/")){//结束是"/"，去掉最后一个"/"
								partMakeRoute=partMakeRoute.substring(0,partMakeRoute.length()-1);
							}
						}
						//CCBegin SS34
						if(partMakeRoute.contains("/ /")){
							String[] tempMake = partMakeRoute.split("/ /");
							StringBuffer tempMakeBuffer = new StringBuffer();
							for(int j = 0 ; j<tempMake.length;j++){
								if(j!=tempMake.length-1){
									tempMakeBuffer.append(tempMake[j]+"/");
								}else{
									tempMakeBuffer.append(tempMake[j]);
								}
								
							}
							partMakeRoute= tempMakeBuffer.toString();
						}
						//CCEnd SS34
						
					}
					myAssRoute = myAssRoute.trim();
					if(!myAssRoute.equals("")){
						String be = myAssRoute.substring(0,1);
						String le = myAssRoute.substring(myAssRoute.length()-1,myAssRoute.length());
						if(myAssRoute.equals("/")){
							myAssRoute = "";
						}else{
							if(be.equals("/")){
								myAssRoute=myAssRoute.substring(1,myAssRoute.length());
							}
							if(le.equals("/")){
								myAssRoute=myAssRoute.substring(0,myAssRoute.length()-1);
							}
						}
						
						//CCBegin SS34
						
						if(myAssRoute.contains("/ /")){
							String[] tempAss = myAssRoute.split("/ /");
							StringBuffer tempAssBuffer = new StringBuffer();
							for(int j = 0 ; j<tempAss.length;j++){
								if(j!=tempAss.length-1){
									tempAssBuffer.append(tempAss[j]+"/");	
								}else{
									tempAssBuffer.append(tempAss[j]);
								}
								
							}
							myAssRoute = tempAssBuffer.toString();
						}
						//CCEnd SS34
					}
					//多个供应商开关
					//多个供应商，如果制造和装配都包含专<协>，则最后一个供应商替换装配路线的专<协>，其它供应商替换制造路线的专<协>
					//一个供应商，如果制造路线或者装配路线包含专<协>，则用供应商替换路线单位专<协>
					//CCBegin SS34
//					boolean isManySupper = false;
//					
//					String supper=listPartRoute.getSupplier();
//					if(supper!=null){
//						supper = supper.trim();
//					}
//					if(supper!=null&&(!supper.equals(""))){
//						
//						String assSupper = "";
//						StringBuffer makeSupper = new StringBuffer();;
//					
//						if(supper.contains(";")){
//							isManySupper = true;
//							String[] subSup = supper.split(";");
//							assSupper = subSup[subSup.length-1];
//							for(int j = 0 ; j<subSup.length-1;j++){
//								if(j==subSup.length-2){
//									makeSupper.append(subSup[j]) ;
//								}else{
//									makeSupper.append(subSup[j]+";") ;
//								}
//								
//							}
//						}else{
//							isManySupper = false;
//							assSupper = supper;
//						}
//						
//						//制造和装配都不是空
//						if((!partMakeRoute.equals(""))&&(!myAssRoute.equals(""))){
//							//制造和装配都存在专协
//							if(partMakeRoute.contains("专<协>")&&myAssRoute.contains("专<协>")){
//								if(isManySupper){
//									partMakeRoute = partMakeRoute.replaceAll("专<协>",makeSupper.toString());
//									myAssRoute = myAssRoute.replaceAll("专<协>",assSupper);
//								}else{
//									if(supper!=null&&(!supper.equals(""))){
//										partMakeRoute = partMakeRoute.replaceAll("专<协>",supper);
//										myAssRoute = myAssRoute.replaceAll("专<协>",supper);
//									}
//								}
//							}else{
//								if(partMakeRoute.contains("专<协>")){
//									partMakeRoute = partMakeRoute.replaceAll("专<协>",supper);
//								}
//								if(myAssRoute.contains("专<协>")){
//									myAssRoute = myAssRoute.replaceAll("专<协>",supper);
//								}
//							}
//							
//							//制造是空，装配不是空，给装配替换
//						}else if((partMakeRoute.equals(""))&&(!myAssRoute.equals(""))){
//							if(myAssRoute.contains("专<协>")){
//								if(supper!=null&&!(supper.equals(""))){
//									myAssRoute = myAssRoute.replaceAll("专<协>",supper);
//								}
//							
//							}
//							//装配是空，制造不是空，给制造替换
//						}else if((!partMakeRoute.equals(""))&&(myAssRoute.equals(""))){
//							if(partMakeRoute.contains("专<协>")){
//								if(supper!=null&&!(supper.equals(""))){
//									partMakeRoute = partMakeRoute.replaceAll("专<协>",supper);
//								}
//							
//							}
//						}
//						
//						
//					}	
					//CCEnd SS34
				//CCBegin SS31
				//CCBegin SS35 SS38
					String partVersionValue = listPartRoute.getPartVersion();
					String partVer = partVersionValue.substring(0, 1);
					String partNumVer = partmaster.getPartNumber()+"/"+partVer;
					if(partmaster.getPartNumber().startsWith("S")||partmaster.getPartNumber().indexOf("S")== 1)//以S、2S开头的零部件导出时不显示零部件版本
					{
						partNumVer = partmaster.getPartNumber();
					}
					Object[] arrayObjs ={
							//CCBegin SS32
							change, partNumVer,
			    //CCEnd SS35 SS38
							//CCEnd SS32
							version,
							partmaster.getPartName(), countInProduct, partMakeRoute,
							//CCBegin SS33
							//myAssRoute, part.getBsoID(),remark.toString(),sockID
							myAssRoute, partInfo.getBsoID(),remark.toString(),sockID
							//CCEnd SS33
						};
						v.add(arrayObjs);
				//CCEnd SS31
				}
				//CCBegin　SS31
				else{
				//CCEnd SS31
					//CCEnd SS30
					//CCEnd SS27	
						
					Object[] arrayObjs ={
						change, partmaster.getPartNumber(),
						version,
						partmaster.getPartName(), countInProduct, partMakeRoute,
						//CCBegin SS33
						//myAssRoute, part.getBsoID(),remark.toString(),sockID
						myAssRoute, partInfo.getBsoID(),remark.toString(),sockID
						//CCEnd SS33
					};
					v.add(arrayObjs);
					}
				//CCBegin　SS31
				}
				//CCEnd SS31
					//CCEnd SS10
					//CCEnd SS5
					
			}
		}
		catch (QMException ex) {
			ex.printStackTrace();
			//yanqi_20060922
			throw ex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			//yanqi_20060922
			QMException e = new QMException(ex);
			throw e;
		}
		return v;
	}
	/*  public static Collection getFirstLeveRouteListReport(TechnicsRouteListIfc routelist) throws QMException {

      ///// 20070525 liuming add
      ArrayList v = new ArrayList();

       HashMap countMap = new HashMap();
      Vector indexVector = routelist.getPartIndex();
    //  System.out.println("   ReportLevelOneUtil getFirstLeveRouteListReport ()indexVector = "+indexVector);
      if (indexVector != null && indexVector.size() > 0) {
        int size2 = indexVector.size();
        System.out.println("size2============"+size2);
        String key = null;
        for (int k = 0; k < size2; k++) {
          String[] ids = (String[]) indexVector.elementAt(k);
          System.out.println("1111111============"+ids[0]);
          System.out.println("2222222============"+ids[1]);
//          System.out.println("       ids .length="+ids.length+" 0:"+ids[0]+" 1:"+ids[1]);
          if (countMap.containsKey(ids[0])) {
            key = ids[0] + "K" + k;
            System.out.println("iiiiiiiiiiiiii============");
            countMap.put(key, ids[0]); //关联零件有重复的可能，需要加以区别
          }
          else {
            key = ids[0];
            System.out.println("eeeeeeeeeeeeeee============");
//            countMap.put(key, ids[0]);
             //关联零件有重复的可能，需要加以区别
          }
          countMap.put(key, ids[2]);
//          System.out.println("kkkkkkkkk============"+ids[k].toString());
//          System.out.println("key = "+key+".........count = "+ids[2]);

        }
      }

      try {
        PersistService pservice = (PersistService) EJBServiceHelper.
            getPersistService();
        TechnicsRouteService routeService =
            (TechnicsRouteService) EJBServiceHelper.getService(
            "consTechnicsRouteService");
        //获得当前用户的配置规范
        PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
        if(configSpecIfc==null ||configSpecIfc.getStandard()==null||configSpecIfc.getStandard().getViewObjectIfc()==null)
        {
          configSpecIfc = getPartConfigSpecByViewName("工程视图");
        }

        CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);
        //liunan
        System.out.println("map================"+map.size());
        Enumeration enum1 = map.keys();
        int i = 0;
        String countInProduct = null;

        /////////////循环对象：每个关联零部件
        while (enum1.hasMoreElements())
        {
            QMPartMasterIfc partmaster = (QMPartMasterIfc) enum1.nextElement();
            System.out.println("------------"+map.get(partmaster));
            System.out.println("------------"+map.values());
            Object obj = (Object)map.get(partmaster);
            ListRoutePartLinkIfc listPartRoute =(ListRoutePartLinkIfc)obj;

            //(问题一)20060629 周茁修改 修改原因:工艺路线生成报表操作速度慢,
            //从technicsrouteBranch对象中直接取出路线串字符串，不用恢复node对象 begin
//            Collection branches = (Collection) obj[0];
            Object[] branches = ((Collection) obj).toArray();
            String[] hhhh = getPartMakeAndAssRouteInRouteList(branches);
            String partMakeRoute = hhhh[0];
            String myAssRoute = hhhh[1];

//            if (branches != null && branches.size() > 0)
//            {
//                Iterator  ite  = branches.iterator();
//                while (ite.hasNext())
//                {
//                    String unionStr = (String)ite.next();
//                    if(unionStr!=null)
//                    {
//                        StringTokenizer hh = new StringTokenizer(unionStr,"@");
//                        if(hh.hasMoreTokens())
//                        {
//                            partMakeRoute = hh.nextToken();
//                            myAssRoute = hh.nextToken();
//                        }
//                    }
//                }
//            }


          QMPartIfc part = routeService.getLastedPartByConfig(partmaster,configSpecIfc); //liuming 20061215
          if(part ==null)
          {
              continue;
          }

          ////////处理“每车数量”////////////////////////////////////////////////////
          //String countInProduct = Integer.toString(listPartRoute.getCount());
          //String masterID = partmaster.getBsoID();
//          System.out.println(">>> masterID = "+masterID+".....countInProduct = "+countInProduct);
          //if (countInProduct.trim().equals("0"))
          //{
            //countInProduct = "";
          //}

        countInProduct = "";
        String masterID = partmaster.getBsoID();
        if (countMap.containsKey(masterID)) {
          countInProduct = countMap.get(masterID).toString();
          countMap.remove(masterID);
        }
        else {
          String keya = null;
          for (Iterator jt = countMap.keySet().iterator(); jt.hasNext(); ) {
            keya = jt.next().toString();
            if (keya.startsWith(masterID)) {
              masterID = keya;
              countInProduct = countMap.get(masterID).toString();
              countMap.remove(masterID);
              break;
            }
          }
        }
        //System.out.println(">>> masterID = "+masterID+".....countInProduct = "+countInProduct);
        if (countInProduct.trim().equals("0")) {
          countInProduct = "";
        }


          ///////处理“每车数量”完毕
          /////////////////////////////////////////////////////////////



          //设置更改标识和备注
          TechnicsRouteIfc route = null;
          String change = "";
          StringBuffer remark = new StringBuffer("") ;
          if (listPartRoute != null)
          {
            String routeID = listPartRoute.getRouteID();
            //System.out.println("routeID is " + routeID );
            if (routeID != null)
            {
              route = (TechnicsRouteIfc) pservice.refreshInfo(routeID,false);
            }
          }
          if (route != null)
          {//liunan
        	  //Begin by chudaming 20090716
            change = route.getRouteDescription().substring(route.getRouteDescription().indexOf(":")+1,route.getRouteDescription().indexOf(":")+2);
            String des1 = route.getRouteDescription().substring(route.getRouteDescription().indexOf(")")+1,route.getRouteDescription().length());
            //End by chudaming 20090716
            if(des1==null)
            {
              des1= "";
            }
            remark.append(des1) ;
//            String des2 = route.getRouteDescription();xs
//            if(des2==null || des2.equals("") )
//            {
//            }
//            else
//            {
//              if(des1.equals(""))
//              {
//              }
//              else
//              {
//                remark.append(" ");
//              }
//              remark.append(des2);
//            }
          }

          String version = "";
          version = getPartSourceVersion(part.getBsoID());
          if (version == null || version.equals(""))
          {
              version = part.getVersionValue();
              if (version == null || version.equals(""))
              {
                  version = "  ";
              }
          }
          version = version.substring(0, 1);
          if (route != null)
          {
        	  //Begin by chudaming 20090716
            version = route.getRouteDescription().substring(1,route.getRouteDescription().indexOf(":"));//0716
            //End by chudaming 20090716

          }
          //CCBegin  by wanghonglian 2008-06-05
          //版本信息前不用加上“/”
          if(!version.trim().equals(""))
          {
            version = "/"+version;
          }
          //CCEnd by wanghonglian 2008-06-05

System.out.println(">>> partmaster = "+partmaster.getPartNumber()+".....version = "+version
                             +".....countInProduct = "+countInProduct+".....partMakeRoute = "+partMakeRoute+".....part.getBsoID() = "+part.getBsoID()
                             +".....remark.toString() = "+remark.toString());
          /////添加了备注 20070122
          Object[] arrayObjs =
              {
              change, partmaster.getPartNumber(),
              version,
              partmaster.getPartName(), countInProduct, partMakeRoute,
              myAssRoute, part.getBsoID(),remark.toString()
          };
          v.add(arrayObjs);
        }
      }
      catch (QMException ex) {
        ex.printStackTrace();
        //yanqi_20060922
        throw ex;
      }
      catch (Exception ex) {
        ex.printStackTrace();
        //yanqi_20060922
        QMException e = new QMException(ex);
        throw e;
      }
      return v;
   }*/


	/**
	 * yanqi-20061010
	 * 获取零部件part在车型root上的使用数量，递规
	 * @param part QMPartIfc
	 * @param root 车型
	 * @param parentMap key=partID value=以数组(PartUsageLinkIfc, QMPartIfc)为元素的集合
	 * @param useQuanMap key=partID  value=零部件在车型上的使用数量
	 * @throws ServiceLocatorException
	 * @throws QMException
	 */
	public static float getUseQuantity(QMPartIfc part, QMPartIfc root,
			Map theparentMap, Map useQuanMap,PartConfigSpecIfc configSpec) throws
			ServiceLocatorException, QMException
			{
//		System.out.println("44444444444444444444444444444444444444444444444444444444");
		Float f = (Float) useQuanMap.get(part.getBsoID());
		if (f != null) {
			return f.floatValue();
		}

		if (part.getBsoID().equals(root.getBsoID()))
		{
			return 1f;
		}


		float useCount = 0f;
		StandardPartService partService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
		Collection parentPartColl = (Collection) theparentMap.get(part.getBsoID());
		if (parentPartColl == null)
		{

			//在数据库中寻找使用了(partIfc所对应的)PartMasterIfc的
			//所有的父件(QMPartIfc)。返回值是以数组(PartUsageLinkIfc, QMPartIfc)为元素的集合。
			parentPartColl = getParentPartsByConfigSpec(part,configSpec);
			if (parentPartColl != null)
			{
				theparentMap.put(part.getBsoID(), parentPartColl);
			}
			else
			{
				//这里的useCount=0
				useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));
				return useCount;
			}
		}


		//如果有父件
		if (parentPartColl.size() != 0)
		{
			for (Iterator it = parentPartColl.iterator(); it.hasNext(); )
			{
				Object[] obj1 = (Object[]) it.next();
				PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) obj1[0];
				QMPartIfc parentPart = (QMPartIfc) obj1[1];
				useCount = useCount + usageLinkIfc.getQuantity() *
						getUseQuantity(parentPart, root, theparentMap, useQuanMap,configSpec);
			}
		}
		useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));

		return useCount;
			}


	/**
	 * liuming 20070116 add
	 * 从StandPartService复制来的同名方法，目的是将获得的父件中不是最新小版本的去掉
	 * 通过指定的配置规范，在数据库中寻找使用了(partIfc所对应的)PartMasterIfc的
	 * 所有符合配置规范的部件(QMPartIfc)。
	 * 返回值是以Object[] = (PartUsageLinkIfc, QMPartIfc)为元素的集合。
	 * @param partIfc 零部件值对象
	 * @param partConfigSpecIfc 零部件配置规范
	 * @return Collection
	 * @throws QMException
	 */
	private static Collection getParentPartsByConfigSpec(QMPartIfc partIfc,
			PartConfigSpecIfc
			partConfigSpecIfc) throws
			QMException {
//		System.out.println("5555555555555555555555555555555555555555555555555");
		//调用navigateUsedByToIteration(partIfc, partConfigSpecIfc)
		//对结果集合进行过滤，只保留是QMPartIfc的对象
		Vector result = new Vector();
		Collection collection = navigateUsedByToIteration(partIfc,
				new PartConfigSpecAssistant(partConfigSpecIfc));
		if ( (collection == null) || (collection.size() == 0)) {

			return null;
		}
		else {
			Object[] array = new Object[collection.size()];
			array = (Object[]) collection.toArray(array);
			for (int i = 0; i < array.length; i++) {
				if (array[i] instanceof QMPartIfc) {
					result.addElement(array[i]);
				}
			}

			PersistService pService = (PersistService) EJBServiceHelper.getService(
					"PersistService");
			Vector resultVector = new Vector();
			for (int i = 0; i < result.size(); i++)
			{
				QMPartIfc parentPartIfc = (QMPartIfc) result.elementAt(i);

				TechnicsRouteService routeService = (TechnicsRouteService)
						EJBServiceHelper.getService("consTechnicsRouteService");
				QMPartIfc newPart = routeService.getLastedPartByConfig( (
						QMPartMasterIfc) parentPartIfc.getMaster(), partConfigSpecIfc);
				//判断是否是最新版本,去掉不是最新小版本的Part
				if (parentPartIfc.getBsoID().equals(newPart.getBsoID()))
				{
					//leftBsoID是被使用的零部件的QMPartMaster的BsoID
					//rightBsoID是使用者零部件的BsoID::
					String leftBsoID = partIfc.getMasterBsoID();
					String rightBsoID = parentPartIfc.getBsoID();
					Collection coll = null;
					//需要根据leftBsoID和rightBsoID找到PartUsageLinkIfc对象。应该只有一个：（不一定只有一个，因为有多次添加同一子件的情况 skx）
					if(rightBsoID.startsWith("GenericPart"))
					{
						coll = pService.findLinkValueInfo("GenericPartUsageLink",
								leftBsoID,
								"uses", rightBsoID);
					}
					else
					{
						coll = pService.findLinkValueInfo("PartUsageLink",
								leftBsoID,
								"uses", rightBsoID);
					}
					//modify by skx 若有多次添加的情况要在被用于产品界面中把每一条路径都显示出来
					if (coll != null && coll.size() > 0) {
						Iterator iterator = coll.iterator();
						while (iterator.hasNext()) {
							PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) iterator.next();
							Object[] obj1 = new Object[2];
							obj1[0] = usageLinkIfc;
							obj1[1] = parentPartIfc;
							resultVector.addElement(obj1);
						}
					}
				}
			}

			return resultVector;
		}
	}

	/**
	 * liuming 20070116 add
	 * 从StandPartService复制来的同名方法（完全相同）
	 * 根据配置规范获取使用了(指定的零部件所对应的)QMPartMaster的所有零部件的集合。
	 * @param partIfc 指定的零部件值对象
	 * @param configSpec 过滤配置规范
	 * @return Vector 零部件的集合
	 * @throws QMException
	 */
	private static Vector navigateUsedByToIteration(QMPartIfc partIfc,
			ConfigSpec configSpec) throws
			QMException {
//		System.out.println("6666666666666666666666666666666666666666666666");
		PersistService pService = (PersistService) EJBServiceHelper.getService(
				"PersistService");
		//返回的结果集合:
		Vector resultVector = new Vector();
		QMPartMasterIfc masterIfc = (QMPartMasterIfc) partIfc.getMaster();
		QMQuery qmQuery = new QMQuery("QMPart", "PartUsageLink");
		qmQuery = configSpec.appendSearchCriteria(qmQuery);
		//根据查询条件获得相应的结果集:
		Collection colAll = pService.navigateValueInfo(masterIfc, "uses", qmQuery, true);
		Collection resultCollection = null;
		if (colAll != null || colAll.size() > 0) {
			resultCollection = configSpec.process(colAll);
		}
		//构造结果集合:
		if (resultCollection != null && resultCollection.size() > 0) {
			Iterator iterator = resultCollection.iterator();

			while (iterator.hasNext()) {
				Object object0 = iterator.next();
				if (object0 instanceof Object[]) {
					Object[] objArray = (Object[]) object0;
					resultVector.addElement( (QMPartIfc) objArray[0]);
				}
				else {
					resultVector.addElement(object0);
				}
				//end if(object0 instanceof Object[])
			}
			//end while(iterator.hasNext())
		}
		//end if(resultCollection != null && resultCollection.size() > 0)

		return resultVector;
	}

	/**
	 * 获取当前用户的配置规范，如果用户是首次登陆系统，则构造默认的“工程视图标准”配置规范。yanqi-20060918-生成工艺路线表时使用
	 * @throws QMException 使用ExtendedPartService时会抛出。
	 * @return PartConfigSpecIfc 标准配置规范。
	 */
	public static PartConfigSpecIfc getCurrentConfigSpec_enViewDefault() throws
	QMException {
		synchronized (lock) {
			StandardPartService spService = (StandardPartService) EJBServiceHelper.
					getService("StandardPartService");
			PartConfigSpecIfc configSpec = (PartConfigSpecIfc) spService.
					findPartConfigSpecIfc();
			if (configSpec == null) {
				configSpec = new PartConfigSpecInfo();
				configSpec.setStandardActive(true);
				configSpec.setBaselineActive(false);
				configSpec.setEffectivityActive(false);
				PartStandardConfigSpec partStandardConfigSpec = new
						PartStandardConfigSpec();
				ViewService viewService = (ViewService) EJBServiceHelper.getService(
						"ViewService");
				ViewObjectIfc view = viewService.getView("设计视图");
				partStandardConfigSpec.setViewObjectIfc(view);
				partStandardConfigSpec.setLifeCycleState(null);
				partStandardConfigSpec.setWorkingIncluded(true);
				configSpec.setStandard(partStandardConfigSpec);
				ExtendedPartService extendedPartService = (ExtendedPartService)
						EJBServiceHelper.getService("ExtendedPartService");
				return extendedPartService.savePartConfigSpec(configSpec);
			}
			else {
				return configSpec;
			}
		}
	}

	/**
	 * 显示报表时调用(持久化报表)
	 * 首先判断艺准是否在个人资料夹.如果在个人资料夹,则现生成报表,不存入数据库;
	 * 如果在公共资料夹,则进一步判断其在数据库ReportResult表中是否已有记录,如果有就直
	 * 接提取,如果没有则现生成报表并存入数据库.
	 * liuming 20061108
	 * @param routeListID String
	 * @throws QMException
	 */
	public static ArrayList getAllInformation2(String routeListID) throws
	QMException

	{
//		System.out.println("88888888888888888888888888888888888888888888888888888");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
				routeListID, false);
		FolderService folderService = (FolderService) EJBServiceHelper.getService(
				"FolderService");
		FolderIfc folder = folderService.getFolder(listInfo.getLocation());
//		System.out.println("-----------------------------------------------"+folder.getName()+"             "+listInfo.getLocation());
		if(folder==null)
		{
			throw new QMException("您对资料夹"+listInfo.getLocation()+"没有读权限!");
		}
		return getAllInformationColl(routeListID);
	}

	/**
	 * 显示报表时，客户端调用。(不持久化报表)
	 * @param routeListID String
	 * @return ArrayList
	 * @throws QMException
	 */
	public static ArrayList getAllInformationColl(String routeListID) throws
	QMException {
//		System.out.println("999999999999999999999999999999999999999999999");
		QMQuery query = new QMQuery("consListRoutePartLink");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		query.addCondition(new QueryCondition("leftBsoID", "=",routeListID.trim()));
		Collection coll = pservice.findValueInfo(query);
		PersistService pService = (PersistService) EJBServiceHelper.getService(
				"PersistService");
		TechnicsRouteListIfc myRouteList1 = (TechnicsRouteListIfc) pService.
				refreshInfo(routeListID.trim());
		ArrayList allinfomationColl = new ArrayList(5);
		//CCBegin SS40
		String comp = RouteClientUtil.getUserFromCompany();
		//CCEnd SS40
		sendToColl = new ArrayList();
		allinfomationColl.add(getHeader(myRouteList1));
		//CCBegin SS40
		if(comp.equals("cd"))
		{
			System.out.println("aaaaaaaaaa");
			allinfomationColl.add(getFirstLeveRouteListReportCD(myRouteList1, coll));
			allinfomationColl.add(getTail1(sendToColl));  //发往单位
		}
		else
		{
			allinfomationColl.add(getFirstLeveRouteListReport(myRouteList1));
			allinfomationColl.add(getTail1(myRouteList1));  //发往单位
		}
		//CCEnd SS40
		//SS4 BEGIN 
		allinfomationColl.add(getTail2(myRouteList1,routeListID));  //说明
		//SS4 END
		allinfomationColl.add(getWFProcessDetails2(myRouteList1)); //流程信息
		sendToColl = null;
		return allinfomationColl;
	}
	
	//CCBegin SS40
	    public static String getTail1(Collection sendToColl) {
      if (sendToColl == null) {
        return "";
      }
      sendToColl.remove("用");
      String haha = "";
      for (Iterator it1 = sendToColl.iterator(); it1.hasNext(); ) {
        String jj = (String) it1.next();
        haha += jj + "、";
      }
      if (haha.endsWith("、")) {
        haha = haha.substring(0, haha.length() - 1);
      }
      return haha;
    }
  //CCEnd SS40

	public static String getTail1(TechnicsRouteListIfc listInf)
	{
//		System.out.println("00000000000000000000000000000000000000000000000000000000");
		if(sendToColl==null)
		{
			return "";
		}

		String haha = "";
		//CCBegin by wanghonglian 2008-06-04  
		//删除发往单位的说明信息
		String tip = listInf.getRouteListDescription();
		if(tip != null)
		{
			if(tip.indexOf("发往单位：")!= -1)
			{
				System.out.println("tip-------------="+tip);
				tip = tip.substring(tip.indexOf("发往单位：")+5);
				System.out.println("tip-----发往单位--------="+tip);
				haha += tip;
			}
		}
		//CCEnd by wanghonglian  2008-06-04
		//CCBegin by wanghonglian 2008-09-05
		//去掉发往单位的路线单位显示，用户自己手动维护
		//源代码如下：
		/*  for (Iterator it1 = sendToColl.iterator(); it1.hasNext(); ) {
    String jj = (String) it1.next();
    if(jj.equals("协"))//liunan 路线为“协”的 不显示在发往单位中。
    continue;
    //CCBegin by wanghonglian 2008-06-04
    //调整顿号的显示
    if(haha.length()!= 0)
    {
  	  haha += "、"+jj ;
    }  
    else
        haha += jj + "、";
    //CCEnd by wanghonglian 2008-06-04

  }
  if (haha.endsWith("、")) {
    haha = haha.substring(0, haha.length() - 1);
  }*/
		//CCEnd by wanghonglian 2008-09-05
		//System.out.println("whl test sent to ===== " + haha);
		return haha;
	}

	/**
	 * 获得经过处理的工艺路线表说明
	 * @return Collection
	 * @throws QMException 
	 */
	//SS4 BEGIN
//	public static Collection getTail2(TechnicsRouteListIfc listInf) throws QMException {
	public static Collection getTail2(TechnicsRouteListIfc listInf,String routeListID) throws QMException {
	//SS4 END
		Collection coll = new ArrayList();
		String tip = listInf.getRouteListDescription();
	    System.out.println("whl test routelist description === " + tip);
		if (tip == null) {
			return coll;
		}
		///过滤掉“说明”中的分割符   liuming  add 20061212
		String dd = String.valueOf((char)1);
		int ddt = tip.indexOf(dd);
		if(ddt != -1)
		{
			String b1 = tip.substring(0,ddt);
			String b2 = tip.substring(ddt+1);
			tip = b1 + b2;
		}
		///////////////////////
		//CCBegin by wanghonglian 2008-06-04 
		// 删除发往单位的说明信息 
		if(tip.indexOf("发往单位：")!= -1)
		{
			tip = tip.substring(0,tip.indexOf("发往单位："));
		}
		//SS4 BEGIN
		//获得路线表状态
		int isYSZ= isYshizhun(routeListID);
		//SS4 END
		//CCEnd by wanghonglian 2008-06-04 
		String tip1 = "";
		String tip2 = "";
		String tip3 = "";
	
		if (tip.indexOf("说明：") != -1) {
			// CCBegin SS11	
			String comp = RouteClientUtil.getUserFromCompany();
			String ss="";
			if (comp.equals("zczx")) {
				tip1 = tip.substring(0, tip.indexOf("说明："));
				//CCBegin SS16
				if (tip1.indexOf("根据：") != -1) {
					 //CCEnd SS16
					tip1=tip1.substring(0,tip1.indexOf("根据："));				
					if (routeListUnit != null) {
						Iterator it = routeListUnit.iterator();
						while (it.hasNext()) {
							String ss0 = (String) it.next();
							if (!ss0.trim().equals("") && ss0 != null) {
								if (!ss.equals("") && ss != null) {
									ss = ss + ";" + ss0;
								} else {
									ss = ss0;
								}
							}
						}
					}
					//CCBegin SS16
					tip2= tip.substring(tip.indexOf("根据："), tip.indexOf("说明："));
					String tip4 = "";
					String tip5 = "";
				
					tip4= tip.substring(tip.indexOf("说明："), tip.indexOf("路线代码："));
					
		            tip5="路线代码："+ss;   
		            //CCEnd SS16
					routeListUnit.clear();
					coll.add(tip1);
					coll.add(tip2);
					//CCBegin SS16
					coll.add(tip4);
					coll.add(tip5);
					 //CCEnd SS16
					
				}
			} else {
		// CCEnd SS11
				// SS4 BEGIN
				// tip1 = tip.substring(0, tip.indexOf("说明："));
				// tip2 = tip.substring(tip.indexOf("说明："));
				if (isYSZ == 1) {
					// 艺试准
					tip1 = tip.substring(0, tip.indexOf("说明："));
				} else if (isYSZ == 2) {
					// 前准
					//CCBegin SS37
					/*String str = tip.substring(0, tip.indexOf("及"))
							+ "及本前准进行生产准备;";
					String str1 = tip.substring(tip.indexOf("备") + 2, tip
							.indexOf("说明："));
					tip1 = str + " " + str1 + "完成准备。";*/
					tip1 = tip.substring(0, tip.indexOf("说明："));
					//CCEnd SS37
				} else if (isYSZ == 3) {
					// 艺准
					tip1 = tip.substring(0, tip.indexOf("说明："));
				} else if (isYSZ == 4) {
					// 临准
					//CCBegin SS37
					/*String str = tip.substring(0, tip.indexOf("及"))
							+ "及本前准进行生产准备;";
					String str1 = tip.substring(tip.indexOf("备") + 2, tip
							.indexOf("说明："));
					tip1 = str + " " + str1 + "完成准备。";*/
					tip1 = tip.substring(0, tip.indexOf("说明："));
					//CCEnd SS37
				} else if (isYSZ == 5) {
					// 艺毕
					//CCBegin SS39
					//tip1 = tip.substring(0, tip.indexOf("及"))
					//		+ "及艺准     所列内容生产准备完毕,可投入生产。";
					if(tip.indexOf("及")!=-1)
					{
						tip1 = tip.substring(0, tip.indexOf("及"))
							+ "及艺准     所列内容生产准备完毕,可投入生产。";
					}
					else
					{
						tip1 = tip.substring(0, tip.indexOf("所列内容生产准备完毕"))
							+ "所列内容生产准备完毕,可投入生产。";
					}
					//CCEnd SS39
				} else {
					// 艺废
					//CCBegin SS19
					//tip1 = tip.substring(0, tip.indexOf("说明：")) + "废弃上述零部件。";
					tip1 = tip.substring(0, tip.indexOf("说明："));
					//CCEnd SS19
				}

				//CCBegin SS17
				//tip2 = "说明:路线代码  “采”为采购部，“变”为装配车间，“零”为零件车间，“协”为解放公司采购部，“轴”为轴齿中心，“销”为营销部。";
				tip2 = tip.substring(tip.indexOf("说明："),tip.length());
				//CCEnd SS17
				// SS4 END

				coll.add(tip1);
				coll.add(tip2);
			}
		}
		else {
			coll.add(tip);
		}
	
		return coll;
	}


	/**
	 * 获取工作流程参与者信息。根据解放要求添加此方法。   20061201 liuming add
	 * 要去除“驳回”之前的所有参与者记录。
	 * @param routeListID String
	 * @return Collection
	 * @throws QMException
	 */
	public static Collection getWFProcessDetails2(TechnicsRouteListIfc routeListInfo) throws
	QMException
	{
//		System.out.println("=========================================================");
		//CCBegin SS26
		String comp=RouteClientUtil.getUserFromCompany();
		//CCEnd SS26
		Vector vec = new Vector();
		vec.add(new ArrayList());
		vec.add(new ArrayList());
		vec.add(new ArrayList());
		//CCBegin SS26 SS40
		if(comp.equals("ct")||comp.equals("cd")){
			vec.add(new ArrayList());
		}
		//CCEnd SS26 SS40
		vec.add("");
		QMQuery query = new QMQuery("WfProcess");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		query.addCondition(new QueryCondition("businessObjBsoID", "=",
				WfEngineHelper.getPBOIdentity(routeListInfo)));
		query.addOrderBy("startTime", true);
		Collection coll = pservice.findValueInfo(query);
		if (coll != null && coll.size() > 0)
		{
			Iterator it = coll.iterator();
			WfProcessIfc process = (WfProcessIfc) it.next(); //取最新的相关进程
			Vector infoVector = new Vector();
			//获取所有活动
			ArrayList list = getAllActivities(process.getBsoID());
			for (int i = 0; i < list.size(); i++)
			{
				WfActivityIfc activity = (WfActivityIfc) list.get(i);
				infoVector.addAll(getVoteInformation(activity));
			}
			//流程顺序：审核，可能有“驳回”行为
			//遇到“驳回”，就将之前的参与者去掉
			if(infoVector.size()>0)
			{

				int oldSize = infoVector.size();
				Timestamp rejectTime = null;
				////////////查看是否有“驳回”
				for(int i=0;i<oldSize;i++)
				{
					Object[] array = (Object[])infoVector.elementAt(i);
					String vote = array[2].toString();
					if(vote.indexOf("驳回") > -1)
					{
						Timestamp tempTime = (Timestamp) array[3];
//						System.out.println("驳回时间1 = " + rejectTime);
						if (rejectTime == null) {
							rejectTime = tempTime;
						}
						else {
							if (tempTime != null && tempTime.after(rejectTime)) {
								rejectTime = tempTime;
							}
						}
//						System.out.println("驳回时间2 = " + rejectTime);
					}
				}

				Vector tv = new Vector();
				////////////如果有“驳回”，则过滤
				if(rejectTime!=null)
				{
					//System.out.println("--------------有驳回-------------");
//					System.out.println("驳回时间 = "+rejectTime);
					for(int j=0;j<oldSize;j++)
					{
						Object[] array = (Object[])infoVector.elementAt(j);
						Timestamp time = (Timestamp)array[3];
						//String name = array[0].toString();
						//String user = array[1].toString();
						//System.out.println("进程o = "+name+user+time);
						if(time.after(rejectTime))  //只保留晚于驳回时间的记录
						{
							tv.add(array);
						}
					}
				}
				else
				{
//					System.out.println("--------------无驳回-------------");
					tv = infoVector;
				}
				///////////////////////////////////////////

				ArrayList shenheVector = new ArrayList();
				//CCBegin by wanghonglian 2008-06-04 
				//在生成报表中增加显示批准者和校对者的信息
				ArrayList pizhunVector = new ArrayList();
				ArrayList jiaoduiVector = new ArrayList();
				/////////////对活动信息进行分类（校对、会签、审核、批准）
				//CCBegin SS26
				ArrayList huiqianVector = new ArrayList();
				//CCEnd SS26
				if(tv.size()>0)
				{
					//System.out.println("--------------有剩余-------------");
					int newSize = tv.size();
					for(int k =0 ; k<newSize; k++)
					{
						Object[] array = (Object[])tv.elementAt(k);
						String name = array[0].toString();
						String user = array[1].toString();
						//CCBegin by wanghonglian 2008-04-28
						//在角色后加上日期 
						user += array[3].toString().substring(0,10);
						//CCEnd by wanghonglian 2008-04-28
						if(name.indexOf("批准")>-1)
						{
							if(!pizhunVector.contains(user))
								pizhunVector.add(user);
						}
						else
							if(name.indexOf("审核")>-1)
							{
								if(!shenheVector.contains(user))
									shenheVector.add(user);
							}
							else
								if(name.indexOf("校对")>-1)
								{
									if(!jiaoduiVector.contains(user))
										jiaoduiVector.add(user);
								}
								//CCBegin SS26 SS40
						if(comp.equals("ct")||comp.equals("cd")){
							if(name.indexOf("会签")>-1){
								if(!huiqianVector.contains(user))
									huiqianVector.add(user);
							}
						}
						//CCEnd SS26 SS40
					}
				}///////////////////////分类完毕!
				vec.setElementAt(pizhunVector,0);
				vec.setElementAt(shenheVector,1);
				vec.setElementAt(jiaoduiVector,2);
				//CCBegin SS26  SS40
				if(comp.equals("ct")||comp.equals("cd")){
					vec.setElementAt(huiqianVector,3);
				}
				//CCEnd SS26 SS40
			}
		}
		ActorInfo userinfo = (ActorInfo) pservice.refreshInfo(routeListInfo.
				getCreator());
				//CCBegin SS26 SS40
		if(comp.equals("ct")||comp.equals("cd")){
			vec.setElementAt(userinfo.getUsersDesc(),4);
		}else{
			vec.setElementAt(userinfo.getUsersDesc(), 3);
		}
		//CCend SS26 SS40
		return vec;
	}

	/**
	 * 返回给定工作流中的所有过期的的活动
	 * @param   process    给定的工作流对象
	 * @return Iterator    工作流活动值对象集合
	 * @throws WfException
	 */
	public static ArrayList getAllActivities(String bsoID) throws QMException
	{
//		System.out.println("```````````````````````````````````````````````````````");
		ArrayList list = new ArrayList();
		QMQuery query = new QMQuery("WfAssignedActivity");
		query.addCondition(new QueryCondition("parentProcessBsoID", "=", bsoID));
		list.addAll( ( (PersistService) EJBServiceHelper.getPersistService()).
				findValueInfo(query));
		return list;
	}


	/**
	 * 获得指定活动的信息（用户、投票信息、投票时间）    20061201 liuming add
	 * @param waInfo WfActivityIfc
	 * @return Vector中每个元素都是一个数组。array[0]=用户名；array[1]=投票信息；array[2]=投票时间
	 * @throws QMException
	 */
	public static Vector getVoteInformation(WfActivityIfc waInfo) throws QMException
	{
//		System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		Vector v = new Vector();
		String activityID = waInfo.getBsoID();
		PersistService persistService = (PersistService) EJBServiceHelper.
				getService(
						"PersistService");
		QMQuery query = new QMQuery("WorkItem");
		query.addCondition(new QueryCondition("sourceID", "=", activityID));
		Iterator iterator = (persistService.findValueInfo(query)).iterator();
		String name = waInfo.getName();
		Object[] tempInfo = null ;
		while (iterator.hasNext())
		{
			WorkItemIfc workitemIfc = (WorkItemIfc) iterator.next();
			String ownerid = workitemIfc.getOwner();
			if (workitemIfc.getCompletedBy() != null)
			{
				ActorInfo userinfo = (ActorInfo) persistService.refreshInfo(ownerid);
				String user = userinfo.getUsersDesc();
				String WfAssignmentID = workitemIfc.getParentWA();
				QMQuery query1 = new QMQuery("AssignmentBallotLink");
				query1.addCondition(new QueryCondition("leftBsoID", "=", WfAssignmentID));
				Collection coll = persistService.findValueInfo(query1);
				if (coll.size() == 0)
				{
					continue;
				}

				Timestamp modifyTime = workitemIfc.getModifyTime();
				String vote = "";
				for (Iterator iterator1 = coll.iterator(); iterator1.hasNext(); )
				{
					AssignmentBallotLinkInfo assignmentBallotLink = (
							AssignmentBallotLinkInfo) iterator1.next();
					String voteID = assignmentBallotLink.getRightBsoID();
					WfBallotInfo ballot = (WfBallotInfo) persistService.refreshInfo(
							voteID);
					Vector vec = ballot.getEventList();
					for (int i = 0; i < vec.size(); i++)
					{
						vote += vec.elementAt(i) + "、";
					}
				}
				vote = vote.endsWith("、") ? vote.substring(0, vote.length() - 1) : vote;
				if (vote.length() > 0)
				{
					tempInfo = new Object[4] ;
					tempInfo[0] = name;
					tempInfo[1] = user;
					tempInfo[2] = vote;
					tempInfo[3] = modifyTime;
					v.add(tempInfo);
					tempInfo = null;
				}
			}
		}
		return v;
	}

	/**
	 * 从IBA属性，获取零件的大版本
	 * @param ID PartBsoID
	 * @return 该零件IBA属性中的版本
	 * @throws QMException
	 */
	private static String getPartSourceVersion(String ID) throws
	QMException
	{
//		System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		String defintionID =null;

		QMQuery query11 = new QMQuery("StringDefinition");
		query11.addCondition(new QueryCondition("name", "=", "sourceVersion"));
		Collection collf = pservice.findValueInfo(query11);
		for (Iterator iterator = collf.iterator(); iterator.hasNext(); )
		{
			com.faw_qm.iba.definition.model.StringDefinitionInfo dd = (com.faw_qm.
					iba.
					definition.model.StringDefinitionInfo) iterator.next();
			defintionID = dd.getBsoID();
		}

		QMQuery query = new QMQuery("StringValue");
		query.addCondition(new QueryCondition("definitionBsoID", "=",
				defintionID));

		query.addAND();
		query.addCondition(new QueryCondition("iBAHolderBsoID", "=", ID));

		Collection collf1 = pservice.findValueInfo(query);
		for (Iterator iterator = collf1.iterator(); iterator.hasNext(); )
		{
			StringValueInfo a = (StringValueInfo) iterator.next();
			return a.getValue().trim();
		}
		return "";
	}


	/**
	 * liuming 20070116 add
	 * 取父件的制造路线的第一个制造单位作为该件的装配路线，
	 * 如果找到的上级件有多条制造路线(多路线)，则取每条制造路线的第一个制造单位(该件具有装配路线的多路线)。
	 * 如果零件没有制造路线，则不生成其装配路线。
	 * @param part 父件
	 * @return 装配路线
	 * @throws QMException
	 */
	private static String getAssisRoute(QMPartIfc part) throws QMException
	{
//		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		TechnicsRouteService routeService =
				(TechnicsRouteService) EJBServiceHelper.getService(
						"consTechnicsRouteService");

		String[] prouts = routeService.getRouteString(part);
		String myAssRoute = prouts[0];
		StringTokenizer yy = new StringTokenizer(myAssRoute, "/");
		String parentMakeRouteFirst = "";
		while (yy.hasMoreTokens()) {
			String aa = yy.nextToken();
			StringTokenizer MM = new StringTokenizer(aa, "--");
			parentMakeRouteFirst += MM.nextToken() + "/";
		}
		if (parentMakeRouteFirst.endsWith("/")) {
			parentMakeRouteFirst = parentMakeRouteFirst.substring(0,
					parentMakeRouteFirst.length() - 1);
		}
		return parentMakeRouteFirst;
	}
	/**
	 * 过滤掉父件中的广义部件（GenericPart）
	 * yanqi-20061010
	 * 对原来的获取路线表相关零件父件的方法进行修改，减少遍历次数
	 * @param part QMPartIfc 子件
	 * @param count float 数量基数
	 * @param result Collection 返回的父件及使用数量集合
	 * @param parentMap key=partID value=以数组(PartUsageLinkIfc, QMPartIfc)为元素的集合
	 * @throws ServiceLocatorException
	 * @throws QMException
	 */
	private static void getParentPartsNew(QMPartIfc part, float count,
			Collection result, Map parentMap,PartConfigSpecIfc configSpec) throws
			ServiceLocatorException, QMException
			{
//		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		Collection parentColl = (Collection) parentMap.get(part.getBsoID());
		if (parentColl == null) {
			parentColl = getParentPartsByConfigSpec(part,configSpec);
			if (parentColl != null) {
				parentMap.put(part.getBsoID(), parentColl);
			}
		}

		if (parentColl != null)
		{
			for (Iterator it = parentColl.iterator(); it.hasNext(); )
			{
				Object[] obj1 = (Object[]) it.next();
				PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) obj1[0];
				QMPartIfc parentPart = (QMPartIfc) obj1[1];
				/** if (parentPart instanceof com.faw_qm.pcfg.family.model.
          GenericPartInfo)
      {
        continue; /////////根据解放要求 Gpart不要!!
      }*/

				float newCount = usageLinkIfc.getQuantity() * count;
				//对于“逻辑总成”，做普通零件处理，不再用此判断 （肖立艳征得李萍同意） liuming 20061212
				/** if (parentPart.getPartType().toString().equalsIgnoreCase("Logical"))
      {
        getParentPartsNew(parentPart, newCount, result, parentMap);
      }
      else { */
				String beyond = parentPart.getBsoID() + "..." +
						new Integer( (new Float(newCount)).intValue());
				result.add(beyond);
				// }
			}
		}
			}

	/**
	 * 根据视图名称返回零部件配置规范  liuming 20061207 add
	 * @param viewName String
	 * @throws QMException
	 * @return PartConfigSpecIfc
	 */
	private static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
	QMException {
//		System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
		ViewService viewService = (ViewService) EJBServiceHelper.getService("ViewService");
		ViewObjectIfc view = viewService.getView(viewName);
		//若根据指定的视图名称没有获取到相应的值对象则返回当前配置规范
		if(view==null)
		{
			StandardPartService spService = (StandardPartService) EJBServiceHelper.
					getService("StandardPartService");
			return (PartConfigSpecIfc) spService.
					findPartConfigSpecIfc();
		}
		PartConfigSpecIfc partConfigSpecIfc = new PartConfigSpecInfo();
		partConfigSpecIfc = new PartConfigSpecInfo();
		partConfigSpecIfc.setStandardActive(true);
		partConfigSpecIfc.setBaselineActive(false);
		partConfigSpecIfc.setEffectivityActive(false);
		PartStandardConfigSpec partStandardConfigSpec_en = new
				PartStandardConfigSpec();
		partStandardConfigSpec_en.setViewObjectIfc(view);
		partStandardConfigSpec_en.setLifeCycleState(null);
		partStandardConfigSpec_en.setWorkingIncluded(true);
		partConfigSpecIfc.setStandard(partStandardConfigSpec_en);
		return  partConfigSpecIfc;
	}

	/**
	 * 获得指定零件在当前路线表中的制造路线和装配路线
	 * @param listPartRoute 零件关联
	 * @param map CodeManageTable
	 * @return String[] 第一个元素是制造路线,第二个元素是装配路线
	 * @throws QMException
	 */
	private static  String[] getPartMakeAndAssRouteInRouteList(Object[] branches) throws
	QMException
	{
//		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		String makeStr = "";
		String assStr = "";
		ArrayList makeList = new ArrayList(); //制造路线集合,为了保证已出现的制造节点不再出现,如 总/总---->总
		ArrayList assList = new ArrayList(); //同上
		if(sendToColl==null)
		{
			sendToColl = new ArrayList();
		}
		if (branches != null && branches.length > 0)
		{
			for (int j = 0; j < branches.length; j++)
			{
				if (makeStr.length() > 0 && !makeStr.endsWith("/"))
				{
					makeStr += "/";
				}
				if (assStr.length() > 0 && !assStr.endsWith("/"))
				{
					assStr += "/";
				}
				Object[] objs = (Object[]) branches[j];
				Vector makeNodes = (Vector) objs[0]; //制造节点
				RouteNodeIfc asseNode = (RouteNodeIfc) objs[1]; //装配节点
				if (makeNodes != null && makeNodes.size() > 0)
				{
					String makeStrBranch = "";
					for (int m = 0; m < makeNodes.size(); m++)
					{
						RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
						String departmentName = node.getNodeDepartmentName().toString();
						if(departmentName==null)
						{
							throw new QMException("路线单位："+node.getNodeDepartment()+"没有名称！");
						}
						if (!sendToColl.contains(departmentName) && departmentName != "")
						{
							sendToColl.add(departmentName);
						}
						if (makeStrBranch == "")
						{
							if (node.getIsTempRoute())
							{
								makeStrBranch = makeStrBranch + departmentName + "*";
							}
							else {
								makeStrBranch = makeStrBranch + departmentName;
							}
						}
						else
						{
							if (node.getIsTempRoute())
							{
								makeStrBranch = makeStrBranch + "-" + departmentName + "*";
							}
							else {
								makeStrBranch = makeStrBranch + "-" + departmentName;
							}
						}
					}
					if (!makeList.contains(makeStrBranch))
					{
						makeList.add(makeStrBranch);
						makeStr = makeStr + makeStrBranch + "/";
					}
				}
				String assDepartment = "";
				if (asseNode != null)
				{
					assDepartment = asseNode.getNodeDepartmentName().toString();
					if (!sendToColl.contains(assDepartment) &&
							assDepartment != "")
					{
						sendToColl.add(assDepartment);
					}
					if (!assList.contains(assDepartment))
					{
						assList.add(assDepartment);
						if (asseNode.getIsTempRoute())
						{
							assStr = assStr + assDepartment + "*" + "/";
						}
						else {
							assStr = assStr + assDepartment + "/";
						}
					}
				}
			}
		}
		makeStr = makeStr.endsWith("/") ?
				makeStr.substring(0, makeStr.length() - 1) :
					makeStr;
				assStr = assStr.endsWith("/") ? assStr.substring(0, assStr.length() - 1) :
					assStr;
				String[] aaaa = new String[2];
				aaaa[0] = makeStr;
				aaaa[1] = assStr;
				return aaaa;
	}

	/**
	 * yanqi-20061010
	 * 判断零部件part是否是root的子件
	 * @param part QMPartIfc
	 * @param root QMPartIfc
	 * @param parentMap Map 缓存，用来缓存零部件id－该零部件的父件集合，减少按配置规范获取父件的次数
	 * @param isSonMap Map a件是否b件的子件的缓存,键:a的bsoID+"_"+b的bsoID,值:Boolean
	 * @throws ServiceLocatorException
	 * @throws QMException
	 * @return boolean
	 */
	private static boolean isSonOf(QMPartIfc part, QMPartIfc root, Map parentMap,
			Map isSonMap,PartConfigSpecIfc configSpec) throws
			ServiceLocatorException, QMException {
//		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		//System.out.println("=====>enter isSonOf,son: " + part.getPartNumber() +
		//  " ," + "root: " + root.getPartNumber() + "," +
		//  new Timestamp(System.currentTimeMillis()));
		if (part == null || root == null) {
			return false;
		}
		String key = part.getBsoID() + "_" + root.getBsoID();
		Boolean flag = (Boolean) isSonMap.get(key);
		if (flag != null) {
			return flag.booleanValue();
		}
		if (part.getBsoID().equals(root.getBsoID())) {
			isSonMap.put(key, Boolean.TRUE);
			return true;
		}
		StandardPartService partService = (StandardPartService)
				EJBServiceHelper.getService("StandardPartService");
		Collection parentPartColl = (Collection) parentMap.get(part.getBsoID());
		if (parentPartColl == null)
		{
			parentPartColl = getParentPartsByConfigSpec(part,configSpec);
			if (parentPartColl != null) {
				parentMap.put(part.getBsoID(), parentPartColl);
			}
			else {
				isSonMap.put(key, Boolean.FALSE);
				return false;
			}
		}
		if (parentPartColl.size() != 0) {
			for (Iterator it = parentPartColl.iterator(); it.hasNext(); ) {
				Object[] obj1 = (Object[]) it.next();
				QMPartIfc parentPart = (QMPartIfc) obj1[1];
				if (isSonOf(parentPart, root, parentMap, isSonMap,configSpec)) {
					isSonMap.put(key, Boolean.TRUE);
					return true;
				}
			}
		}
		//System.out.println("=====>leave isSonOf, isOrNot: " + result +
		//  new Timestamp(System.currentTimeMillis()));
		isSonMap.put(key, Boolean.FALSE);
		return false;
	}


	/**
	 * 根据给定路线获得它的更改标记。
	 * 从描述中分解出来。
	 * @param route TechnicsRouteIfc
	 * @return String
	 */
	private static String getModefyIdenty(TechnicsRouteIfc route)
	{
//		System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllll");
		String desc = route.getRouteDescription();
		System.out.println("-----------------------------------------------"+desc);
		return desc.substring(3,4);
	}
	
	private static String newgetModefyIdenty(ListRoutePartLinkInfo route) throws QMException
	{
//		System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllll");
//		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
		System.out.println("--------------------------------"+route.getModifyIdenty());
//		CodingIfc code = (CodingIfc)pservice.refreshInfo(route.getModifyIdenty(), false);
//		String mi = code.getCodeContent();
//		System.out.println("-----------------------------------------------"+mi);
		return route.getModifyIdenty();
	}

	//SS4 BEGIN
	/**
	 * 根据给定路线，获得它对应的状态
	 * 判断它的状态是否为艺试准状态
	 * @author 王红莲
	 * @version 1.0 
	 */

//	public static boolean isYshizhun(String routeListID)throws
//	QMException
//	{
//		boolean isYsz = false ;
//		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
//		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
//				routeListID, false);
//		String state = listInfo.getRouteListState();
//		if(state != null && state.equalsIgnoreCase("艺试准"))
//		{
//			isYsz = true ;
//		}
//		return isYsz ;
//	}
	
	
	public static int isYshizhun(String routeListID)throws
	QMException
	{
		int isYsz = 0;
		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
				routeListID, false);
		String state = listInfo.getRouteListState();
		//CCBegin SS8
		if(state != null && (state.equalsIgnoreCase("试制")||state.equalsIgnoreCase("艺试准"))){
			//CCEnd SS8
			isYsz = 1;
		}else if(state != null && state.equalsIgnoreCase("前准")){
			isYsz = 2;
		}else if(state != null && state.equalsIgnoreCase("艺准")){
			isYsz = 3;
		}else if(state != null && state.equalsIgnoreCase("临准")){
			isYsz = 4;
		}else if(state != null && state.equalsIgnoreCase("艺毕")){
			isYsz = 5;
		}else{
			isYsz = 6;
		}
	
		return isYsz ;
	}
	//SS4 END



	/**
	 * 根据给定路线，获得它对应的状态
	 * @author liunan 2012-5-28
	 * @version 1.0 
	 */

	public static String getRouteListType(String routeListID)throws
	QMException
	{
//		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
				routeListID, false);
		String state = listInfo.getRouteListState();
		//System.out.println("工艺路线类型是："+state);
		return state ;
	}
	
	
	//CCBegin SS40
  public static Collection getFirstLeveRouteListReportCD(TechnicsRouteListIfc routelist, Collection coll) throws QMException
  {
  	boolean iscomplete=false;
  	boolean expandByProduct = true;
  	if( routelist.getRouteListState().equals("艺废"))
  	{
  		iscomplete=true;
  	}
    //CCEnd SS21
		// 1.从数据库艺准表中直接取出关联零件的数量
		jfuputil jf = new jfuputil();
		HashMap countMap = new HashMap();
		int tempxh = 0;
		for (Iterator it = coll.iterator(); it.hasNext(); )
		{
			ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)it.next();
			if (countMap.containsKey(link.getRightBsoID()))
			{
				//System.out.println("countmap 22："+(link.getRightBsoID()+"K"+tempxh)+"===="+link.getProductCount());
				countMap.put(link.getRightBsoID()+"K"+tempxh, link.getProductCount());
				tempxh++;
			}
			else
			{
				//System.out.println("countmap 11："+link.getRightBsoID()+"===="+link.getProductCount());
				countMap.put(link.getRightBsoID(), link.getProductCount());
			}
		}

		ArrayList v = new ArrayList();
		// a件是否b件的子件的缓存,键:a的bsoID+"_"+b的bsoID,值:Boolean,yanqi-20061010
		HashMap isSonMap = new HashMap();

		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
				.getService("consTechnicsRouteService");
		// 获得当前用户的配置规范
		PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
		if (configSpecIfc == null || configSpecIfc.getStandard() == null
				|| configSpecIfc.getStandard().getViewObjectIfc() == null) {
			configSpecIfc = getPartConfigSpecByViewName("工程视图");
		}
		
		//2.找到对应艺准的路线串
		CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);// 找到对应艺准的路线串

		QMPartMasterIfc rootmaster = (QMPartMasterIfc) pservice.refreshInfo(routelist.getProductMasterID());
		// 整车产品的最新版本
		QMPartIfc rootPart = routeService.getLastedPartByConfig(rootmaster,
				configSpecIfc);
		Collection partIDCountMap = null;
		HashMap parentMap2000 = null;
		HashMap parentMap = new HashMap();
		HashMap parentMap1 = new HashMap();
		// 如果关联零件数量大于150，则统计该车型的所有子件的数量，采用第一种方案
		boolean flag = (map.size() > 150);
		System.out.println("零件数量＝" + map.size());

		Enumeration enum2 = map.keys();
		String exchangiba = "";
		String safetyiba = "";
		String docnoteiba = "";
		if(iscomplete)
		{
		}
		else
		{
			QMQuery query = new QMQuery("StringDefinition");
			QueryCondition qc = new QueryCondition("displayName", " = ", "影响互换");
			query.addCondition(qc);
			Collection col = pservice.findValueInfo(query, false);
			Iterator iba = col.iterator();
			if (iba.hasNext())
			{
				StringDefinitionIfc s = (StringDefinitionIfc) iba.next();
				exchangiba = s.getBsoID();
			}
			
			QMQuery query1 = new QMQuery("StringDefinition");
			QueryCondition qc1 = new QueryCondition("displayName", " = ", "保安重要");
			query1.addCondition(qc1);
			Collection col1 = pservice.findValueInfo(query1, false);
			Iterator iba1 = col1.iterator();
			if (iba1.hasNext())
			{
				StringDefinitionIfc s = (StringDefinitionIfc) iba1.next();
				safetyiba = s.getBsoID();
			}
			
			QMQuery query2 = new QMQuery("StringDefinition");
			QueryCondition qc2 = new QueryCondition("displayName", " = ", "文档信息");
			query2.addCondition(qc2);
			Collection col2 = pservice.findValueInfo(query2, false);
			Iterator iba2 = col2.iterator();
			if (iba2.hasNext())
			{
				StringDefinitionIfc s = (StringDefinitionIfc) iba2.next();
				docnoteiba = s.getBsoID();
			}
		}
			
			ArrayList partlist = new ArrayList();
			ArrayList partmasterList = new ArrayList();
			ArrayList allpartmasterList = new ArrayList();
			HashMap masterlinkmap = new HashMap();
			ListRoutePartLinkIfc listPartRoute=null;
			QMPartMasterIfc partmaster=null;
			ArrayList partholer = new ArrayList();
			while (enum2.hasMoreElements())
			{
				Object[] obj = null;
				Object o = enum2.nextElement();
				if(o instanceof QMPartMasterIfc)
				{
					partmaster = (QMPartMasterIfc) o;
					obj = (Object[])map.get(partmaster);
				}
				else if(o instanceof QMPartIfc)
				{
					QMPartIfc part = (QMPartIfc) o;
					partmaster = (QMPartMasterIfc) pservice.refreshInfo(part.getMasterBsoID());
					obj = (Object[])map.get(part);
				}
				listPartRoute =(ListRoutePartLinkIfc)obj[1];
				partmaster = listPartRoute.getPartMasterInfo();
				QMPartIfc part1= (QMPartIfc) pservice.refreshInfo(listPartRoute.getRightBsoID());
				if(part1!=null)
				{//如果ListRoutePartLinkIfc关联了零件，直接从里面取
					if (part1.getPartNumber().startsWith("Q") || part1.getPartNumber().startsWith("CQ"))
					{
						flag = true;//如果有标准件，则采用第一种方案
					}
					String masterid = part1.getMasterBsoID();
					masterlinkmap.put(masterid, part1);
					partholer.add(part1.toString());
				}
				else
				{
					partmasterList.add(partmaster); //将partmaster放到集合中供一次性调用数据库
				}
				allpartmasterList.add(partmaster);
				listPartRoute=null;
				partmaster=null;
			}
			enum2 = null;
			//3.获取最新零件集合
			Collection lastedpart = jf.getLastedPartByConfig(partmasterList, configSpecIfc);
			partlist.addAll(lastedpart);
			// End 获取最新零件集合
			
			// begin 获取零件id iba
			for (int j = 0; j < partlist.size(); j++)
			{
				QMPartIfc last = (QMPartIfc) ((Object[]) partlist.get(j))[0];
				if (last.getPartNumber().startsWith("Q") || last.getPartNumber().startsWith("CQ"))
				{
					flag = true;//如果有标准件，则采用第一种方案
				}
				String masterid = last.getMasterBsoID();
				masterlinkmap.put(masterid, last);
				partholer.add(last.toString());
			}
			partlist.clear();
			
			
      //4.获取零件id 
      HashMap returnList=null;
      HashMap returnSafetyList = null;
      HashMap returnDocnoteList = null;
      if(!iscomplete)
      {
      	returnList = jf.getiba(partholer, exchangiba);
      	returnSafetyList = jf.getiba(partholer, safetyiba);
      	returnDocnoteList = jf.getiba(partholer, docnoteiba);
      }
      partholer.clear();
      // end 获取零件id iba
      
      
      //5.找父件
      //艺毕输出报表时不需要父件信息
      ArrayList parentpartlist = null;
      if(!iscomplete)
      {
      	if (expandByProduct && flag)
      	{
      		// 获得在指定车型下的所有子部件，第一种方案找父件
      		ArrayList rootPartlist = new ArrayList();
      		parentMap2000 = new HashMap();
      		rootPartlist.add(rootPart.getBsoID());
      		jf.getSubParts(rootPartlist, parentMap2000, configSpecIfc);
      		rootPartlist.clear();
      	}
      	
      	// 获取父件及数量
      	if (expandByProduct && flag)
      	{
      	}
      	else
      	{
      		//第二种方案找父件
      		parentpartlist = jf.getparentparts(allpartmasterList, parentMap, rootPart, configSpecIfc);
      	}
      }
      
      allpartmasterList=null;
      parentpartlist = null;
      int i = 0;
      ArrayList informationlist = null;
      StringBuffer remark = null;
      String change = null;
      String countInProduct = null;
      String version = null;
      
      
      //循环对象：每个关联零部件
      //6.每个关联零部件数据整理组装
      Enumeration enum3 = map.keys();
      String masterID=null;
      QMPartIfc part=null;
      ArrayList yblist = null;
      if(iscomplete)
      {
      	ArrayList rootPartlist = new ArrayList();
      	HashMap ybmap = new HashMap();
      	rootPartlist.add(rootPart.getBsoID());
      	yblist = jf.getSubParts(rootPartlist, ybmap, configSpecIfc);
      }
      while (enum3.hasMoreElements())
      {
				Object[] obj = null;
				Object o = enum3.nextElement();
				if(o instanceof QMPartMasterIfc)
				{
					partmaster = (QMPartMasterIfc) o;
					obj = (Object[])map.get(partmaster);
				}
				else if(o instanceof QMPartIfc)
				{
					QMPartIfc part1 = (QMPartIfc) o;
					partmaster = (QMPartMasterIfc) pservice.refreshInfo(part1.getMasterBsoID());
					obj = (Object[])map.get(part1);
				}
				listPartRoute =(ListRoutePartLinkIfc)obj[1];
				partmaster = listPartRoute.getPartMasterInfo();
				masterID = partmaster.getBsoID();
				part=(QMPartIfc) pservice.refreshInfo(listPartRoute.getRightBsoID());
				if(part==null)
				{
					part = (QMPartIfc) masterlinkmap.get(masterID);
				}
				if (part == null)
				{
					continue;
				}
				String s = (String)obj[0];
				String partMakeRoute = "";;
				String myAssRoute = "";
				String sub = "";
				sub = s.substring(1, s.length()-1);
				String str[] = sub.split(",");
				for(int e = 0;e<str.length;e++)
				{
					if(sub.contains("="))
					{
						//CCBegin SS40
						if(str[e].endsWith("="))
						{
							str[e] = str[e] + "无";
						}
					//CCEnd SS40
						String str1[] = str[e].split("=");
						if(e==str.length-1)
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0];
								if(str1[1].equals("无"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1];
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0];
 								myAssRoute += " ";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
							System.out.println(str1[0]+"=======111======"+str1[1]);
							String[] makes = str1[0].split("-");
							for(int f = 0;f<makes.length;f++)
							{
								System.out.println("=======111 makes======"+makes[f]);
								if (!sendToColl.contains(makes[f]) && makes[f] != "")
								{
									sendToColl.add(makes[f]);
								}
							}
							if (!sendToColl.contains(str1[1]) && str1[1] != "")
							{
								sendToColl.add(str1[1]);
							}
						}
						else
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0]+"/";
								if(str1[1].equals("无"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
							System.out.println(str1[0]+"=======222======"+str1[1]);
							if (!sendToColl.contains(str1[0]) && str1[0] != "")
							{
								sendToColl.add(str1[0]);
							}
							if (!sendToColl.contains(str1[1]) && str1[1] != "")
							{
								sendToColl.add(str1[1]);
							}
						}
					}
					else if(sub.contains("@"))
					{
						String str1[] = str[e].split("@");
						if(e==str.length-1)
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0];
								if(str1[1].equals("无"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1];
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0];
								myAssRoute += " ";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
						else
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0]+"/";
								if(str1[1].equals("无"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
					}
				}
				
				// //////处理“每车数量”////////////////////
				countInProduct = "";
				String masterID1 = part.getBsoID();
				//System.out.println("masterID1:"+masterID1);
        if (countMap.containsKey(masterID1))
        {
        	countInProduct = countMap.get(masterID1).toString();
        	countMap.remove(masterID1);
        }
        else
        {
        	String keya = null;
        	for (Iterator jt = countMap.keySet().iterator(); jt.hasNext();)
        	{
        		keya = (String)jt.next();
        		if(keya==null)
        		{
        			continue;
        		}
        		if (keya.startsWith(masterID1))
        		{
        			masterID1 = keya;
        			countInProduct = countMap.get(masterID1).toString();
        			countMap.remove(masterID1);
        			break;
        		}
        	}
        }
        if (countInProduct.trim().equals("0"))
        {
        	countInProduct = "";
        }
        //System.out.println("countInProduct===="+countInProduct);
        // ////////“每车数量”处理完毕////////////////////
        
        // //根据解放要求路线中有"用"放在后面////////////
        if (partMakeRoute.indexOf("用") != -1 && (!partMakeRoute.endsWith("用")))
        {
        	String sortedRoute = "";
        	StringTokenizer yyy = new StringTokenizer(partMakeRoute, "/");
        	while (yyy.hasMoreTokens())
        	{
        		String haha = yyy.nextToken();
        		if (!haha.equals("用"))
        		{
        			sortedRoute += haha + "/";
        		}
        	}
        	sortedRoute += "用";
        	partMakeRoute = sortedRoute;
        }
        
        // 三个元素：分别为“装配路线”、“合件数量”和“上级件号”
        informationlist = new ArrayList();
        String[] informationStr = null;
        // 包含“用”且零件名含有“装置图”的不出现父件 liuming 20061212
        if(iscomplete)
        {
        	// 如果没有装配路线,则寻找父件的制造路线
        	if (myAssRoute == null || myAssRoute.equals(""))
        	{
        		// 对于逻辑总成或者制造路线含"用的",就不获取父件的制造路线了
        		if (part.getPartType().toString().equalsIgnoreCase("Logical") || (partMakeRoute.indexOf("用") != -1))
						{
							informationStr = new String[3];
							informationStr[0] = myAssRoute;
							informationStr[1] = "";
							informationStr[2] = "";
							informationlist.add(informationStr);
							informationStr = null;
						}
						else
						{
							Collection parentcollection = getParentPartsByConfigSpec(part, configSpecIfc);
							if(parentcollection!=null)
							{
								Iterator i1=parentcollection.iterator();
								while(i1.hasNext())
								{
									QMPartIfc parentpart=(QMPartIfc)(((Object[])i1.next())[1]);
									if(!yblist.contains(parentpart.getBsoID()))
									{
										continue;
									}
									myAssRoute = getAssisRoute(parentpart);
									if(myAssRoute == null || myAssRoute.equals(""))
									{
										continue;
									}
									// 将路线单位加入发往单位中.
									StringTokenizer qq = new StringTokenizer(myAssRoute, "/");
									while (qq.hasMoreTokens())
									{
										String departmentName = qq.nextToken();
										System.out.println("=======333======"+departmentName);
										if (!sendToColl.contains(departmentName) && departmentName != "")
										{
											sendToColl.add(departmentName);
										}
									}
									qq = null;
									informationStr = new String[3];
									informationStr[0] = myAssRoute;
									informationStr[1] = "";
									informationStr[2] = "";
									informationlist.add(informationStr);
								}
							}
						}
					}
			}
			else if (partMakeRoute == "" || ((partMakeRoute.indexOf("用") != -1) && partmaster.getPartName().indexOf("装置图") != -1))
			{
				informationStr = new String[3];
				informationStr[0] = myAssRoute;
				informationStr[1] = "";
				informationStr[2] = "";
				informationlist.add(informationStr);
				informationStr = null;
			}
			else
			{
				String parentPartList = null;
				if (expandByProduct && flag)
				{
					parentPartList = (String) parentMap2000.get(part.getMasterBsoID());
				}
				else
				{
					parentPartList = (String) parentMap.get(masterID);
				}
				
				//map32: key=partID value=合件数量
				HashMap map32 = new HashMap();
				if (parentPartList != null)
				{
					StringTokenizer yyy = new StringTokenizer(parentPartList, ";");
					while (yyy.hasMoreTokens())
					{
						String parentcount = yyy.nextToken();
						StringTokenizer yy = new StringTokenizer(parentcount, "...");
						if (yy.hasMoreTokens())
						{
							String parepartid = yy.nextToken();
							String partcount = yy.nextToken();
							if(map32.get(parepartid)!=null)
							{
								//System.out.println("----有多个关联的情况");
								int countnum1=Integer.parseInt(partcount);
								String count2=(String)map32.get(parepartid);
								int countnum2=Integer.parseInt(count2);
								int partcountnum=countnum2+countnum1;
								partcount=String.valueOf(partcountnum);
								//System.out.println("---数量＝　"+partcount);
							}
							map32.put(parepartid, partcount);
						}
						yy = null;
					}
					yyy = null;
				}
				parentPartList=null;

				Collection keySet = map32.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();)
				{
					String partID = (String) it.next();
					String count = (String) map32.get(partID);
					QMPartIfc parentpart = (QMPartIfc) pservice.refreshInfo(partID);
					// 如果按车展开，但这个父件不在该车上，则不显示此父件的信息
					if (expandByProduct)
					{
						if (flag)
						{
							//如果零件数大于2000，则直接从root展开的的结构中找是否有该件
							// if (parentMap2000.get(partID) == null) {
							// continue;
							// }
						}
						else if(!isSonOf(parentpart, rootPart, parentMap1, isSonMap, configSpecIfc))
						{
							continue;
						}
					}
					// 如果没有装配路线,则寻找父件的制造路线
					if (myAssRoute == null || myAssRoute.equals(""))
					{
						// 对于逻辑总成或者制造路线含"用的",就不获取父件的制造路线了
						if (part.getPartType().toString().equalsIgnoreCase("Logical") || (partMakeRoute.indexOf("用") != -1))
						{
						}
						else
						{
							myAssRoute = getAssisRoute(parentpart);
							// 将路线单位加入发往单位中.
							StringTokenizer qq = new StringTokenizer(myAssRoute, "/");
							while (qq.hasMoreTokens())
							{
								String departmentName = qq.nextToken();
								System.out.println("=======444======"+departmentName);
								if (!sendToColl.contains(departmentName) && departmentName != "")
								{
									sendToColl.add(departmentName);
								}
							}
							qq = null;
						}
					}
					informationStr = new String[3];
					informationStr[2] = parentpart.getPartNumber();
					informationStr[1] = count;
					informationStr[0] = myAssRoute;
					informationlist.add(informationStr);
					informationStr = null;
				}
				map32.clear();
			}

			if (informationlist.size() == 0)
			{
				informationStr = new String[3];
				informationStr[0] = myAssRoute;
				informationStr[1] = "";
				informationStr[2] = "";
				informationlist.add(informationStr);
				informationStr = null;
			}

			// 设置更改标识和备注
			TechnicsRouteIfc route = null;
			change = "";
			remark = new StringBuffer("");
			if (listPartRoute != null)
			{
				String routeID = listPartRoute.getRouteID();
				if (routeID != null)
				{
					route = (TechnicsRouteIfc) pservice.refreshInfo(routeID, false);
				}
			}
			//if (route != null)
			{
				String modifyidenty = listPartRoute.getModifyIdenty();
				//System.out.println("modifyidenty==="+modifyidenty);
				if(modifyidenty==null)
				{
					modifyidenty = "";
				}
				if(modifyidenty.equals("采用"))
				{
					change = "C";
				}
				else if(modifyidenty.equals("新增"))
				{
					change = "X";
				}
				else if(modifyidenty.equals("改图"))
				{
					change = "G";
				}
				else if(modifyidenty.equals("取消"))
				{
					change = "Q";
				}
				else if(modifyidenty.equals("废弃"))
				{
					change = "F";
				}
				/*String des1 = route.getDefaultDescreption();
				if (des1 == null)
				{
					des1 = "";
				}
				remark.append(des1);*/
				String des2 = listPartRoute.getRouteDescription();
				if (des2 != null && des2.startsWith("(") && des2.indexOf(")") != -1)
				{
					des2 = des2.substring(des2.indexOf(")") + 1, des2.length());
				}
				if (des2 == null || des2.equals(""))
				{
				}
				else
				{
					remark.append(des2);
				}
			}
			version = "";
			String partNum = "";
			// 对于编号以Q,CQ,T开头的标准件不显示版本号
			partNum = part.getPartNumber();
			if (partNum.startsWith("Q") || partNum.startsWith("CQ") || partNum.startsWith("T"))
			{
			}
			else
			{
				if (route != null && route.getRouteDescription() != null)
				{
					String routeStr1 = route.getRouteDescription();
					if (routeStr1 != null && routeStr1.startsWith("(") && routeStr1.indexOf(")") != -1)
					{
						version = routeStr1.substring(routeStr1.indexOf("(") + 1, routeStr1.indexOf(")"));
					}
				}
			}

			if (version.trim().equals(""))
			{
			}
			else
			{
				version = "/" + version;
			}
			
			String exchangvalue="";
			String safetyvalue = "";
			String docnotevalue = "";
			String colorFlag = "";
			if(iscomplete)
			{
				//废弃通知书 时 exchangvalue 对应属性 “首用车型”
				if( routelist.getRouteListState().equals("艺废"))
				{
					//根据零部件获得最新 艺准 编号。
					Collection trlcol = (Collection)routeService.getListsByPart(part.getMasterBsoID());
					for (Iterator ittrl = trlcol.iterator(); ittrl.hasNext(); )
					{
						TechnicsRouteListIfc trl = (TechnicsRouteListIfc) ittrl.next();
						if(trl.getRouteListState().equals("艺准"))
						{
							exchangvalue = trl.getRouteListNumber();
							break;
						}
					}
				}
			}
			else
			{
				exchangvalue = (String) returnList.get(part.getBsoID());
				safetyvalue = (String) returnSafetyList.get(part.getBsoID());
				docnotevalue = (String) returnDocnoteList.get(part.getBsoID());
				if(docnotevalue==null)
				{
					docnotevalue = "";
				}
				if(!docnotevalue.equals(""))
				{
					docnotevalue = "("+docnotevalue+")";
				}
			}
			
			if(listPartRoute.getColorFlag()!=null&&listPartRoute.getColorFlag().trim().equals("1"))
			{
				colorFlag="是";
			}
			
			//序号、更改标记、编号、版本、名称+见xxx、数量、制造路线、（装配路线、数量、父件）、bsoID、备注、影响互换、？？、保安重要
			Object[] arrayObjs = { String.valueOf(++i), change,
					partmaster.getPartNumber(), version,
					partmaster.getPartName()+docnotevalue, countInProduct, partMakeRoute,
					informationlist, part.getBsoID(), remark.toString(),
			        exchangvalue,"", safetyvalue, colorFlag};
			v.add(arrayObjs);
			listPartRoute=null;
			partmaster=null; 
			masterID=null;
			part=null;
		}
		enum3=null;
		masterlinkmap = null;
		countMap.clear();
		parentMap = null;
		parentMap2000=null;
		parentMap1=null;
		partmasterList=null;
		isSonMap=null;
		map.clear();
		returnList=null;
		informationlist = null;
		return v;
	}
	
	
	private static String[] getPartMakeAndAssRouteInRouteList(ListRoutePartLinkIfc listPartRoute, CodeManageTable map, ArrayList sendToColl) throws QMException
	{
		Object[] branches = ( (Collection) map.get(listPartRoute)).toArray();
		String makeStr = "";
		String assStr = "";
		ArrayList makeList = new ArrayList(); //制造路线集合,为了保证已出现的制造节点不再出现,如 总/总---->总
		ArrayList assList = new ArrayList(); //同上
		if (sendToColl == null)
		{
			sendToColl = new ArrayList();
		}
		if (branches != null && branches.length > 0)
		{
			for (int j = 0; j < branches.length; j++)
			{
				if (makeStr.length() > 0 && !makeStr.endsWith("/"))
				{
					makeStr += "/";
				}
				if (assStr.length() > 0 && !assStr.endsWith("/"))
				{
					assStr += "/";
				}
				//解放升级工艺路线,路线从路线串缓存取
				String routeString=(String)branches[j];
				String makeStrBranch="";
				String assDepartment="";
				if(routeString!=null&&routeString.length()>0)
				{
          String route[]= routeString.split(";");
          for(int k=0;k<route.length;k++)
          {
        	  String r=route[k];
        	  int s=r.indexOf("@");
        	  String  longdepartmentName=r.substring(0,s);
        	  if(longdepartmentName.equals("无"))
        	  {
        	  	longdepartmentName="";
        	  }
        	  //System.out.println("制造单位＝"+longdepartmentName);
        	  longdepartmentName = longdepartmentName.replaceAll("-","→");
        	  String [] de=longdepartmentName.split("→");
        	  for(int i=0;i<de.length;i++)
        	  {
        	  	String departmentName=de[i];
        	  	if (makeStrBranch == "")
        	  	{
        	  		makeStrBranch = makeStrBranch + departmentName;
        	  	}
        	  	else
        	  	{
        	  		makeStrBranch = makeStrBranch + "-" + departmentName;
        	  	}
        	  	if (!sendToColl.contains(departmentName) && departmentName != "")
        	  	{
        	  		sendToColl.add(departmentName);
        	  	}
        	  }
        	  if (!makeList.contains(makeStrBranch))
        	  {
              makeList.add(makeStrBranch);
              makeStr = makeStr + makeStrBranch + "/";
            }
            
            assDepartment=r.substring(s+1,r.length());
            if(assDepartment.equals("无"))
            {
            	assDepartment="";
            }
            if (!sendToColl.contains(assDepartment) && assDepartment != "")
            {
            	sendToColl.add(assDepartment);
            }
          }
          if (!assList.contains(assDepartment))
          {
          	assList.add(assDepartment);
          	assStr = assStr + assDepartment + "/";
          }
        }
      }
    }
    makeStr = makeStr.endsWith("/") ? makeStr.substring(0, makeStr.length() - 1) : makeStr;
    String[] ss = assStr.split("/");
    int cc = ss.length;
    if(cc==0)
    {
    	assStr = "";
    }
    for(int ii=0;ii<cc;ii++)
    {
    	assStr = assStr.endsWith("/") ? assStr.substring(0, assStr.length() - 1) : assStr;
    	assStr = assStr.startsWith("/") ? assStr.substring(1, assStr.length()) : assStr;
    }
    String[] aaaa = new String[2];
    aaaa[0] = makeStr;
    aaaa[1] = assStr;
    makeList = null;
    assList = null;
    return aaaa;
  }
}
