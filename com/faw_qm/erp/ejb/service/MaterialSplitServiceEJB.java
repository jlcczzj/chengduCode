/**
 * 生成程序MaterialSplitServiceEJB.java	1.0              2007-10-7
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 编辑二级路线采购标识为空时，执行ERP发布XML。执行工作任务到批准后，系统没有向ERP 刘家坤 2014-02-28
 * SS2 物料结构表格中列出了生命周期状态为编制中的子件 刘家坤 2014-03-05
 * SS3 平台问题A034-2015-0223 资料夹变更后，调整处理代码。 liunan 2015-4-15
 * SS4 平台问题A034-2015-0224 取消集成接口发布规则里根据二级路线“采购标识”生成物料的规则 liunan 2015-4-15
 * SS5 生成xml文件中 要求零件号和物料号中成品号保持一致
 * SS6 获取发布源版本出错
 */
package com.faw_qm.erp.ejb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.erp.exception.ERPException;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.FilterPartInfo;
import com.faw_qm.erp.model.InterimMaterialSplitIfc;
import com.faw_qm.erp.model.InterimMaterialSplitInfo;
import com.faw_qm.erp.model.InterimMaterialStructureIfc;
import com.faw_qm.erp.model.InterimMaterialStructureInfo;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.model.MaterialSplitInfo;
import com.faw_qm.erp.model.MaterialStructureIfc;
import com.faw_qm.erp.model.MaterialStructureInfo;
import com.faw_qm.erp.model.SameMaterialIfc;
import com.faw_qm.erp.util.BaseDataPublisher;
import com.faw_qm.erp.util.Messages;
import com.faw_qm.erp.util.PartHelper;
import com.faw_qm.erp.util.RequestHelper;
import com.faw_qm.erp.util.RouteCodeIBAName;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.definition.AttributeHierarchyChild;
import com.faw_qm.iba.definition.ejb.service.IBADefinitionObjectsFactory;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.iba.value.model.StringValueIfc;

// CCEnd by dikfeng 20090217

/**
 * <p>
 * Title: 物料拆分服务。
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * 
 * </p>
 * <p>
 * Company: 启明信息技术股份有限公司
 * </p>
 * 
 * @author 谢斌
 * @version 1.0
 */
public class MaterialSplitServiceEJB extends BaseServiceImp {
	private static final long serialVersionUID = 1L;

	
	/**
	 * 获取服务名。
	 * 
	 * @return String "MaterialSplitService"。
	 * 
	 */
	public String getServiceName() {
		return "MaterialSplitService";
	}

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
	.getLogger(MaterialSplitServiceEJB.class);

	/**
	 * 分号分隔符。用于分隔路线和采用产品的BsoId。
	 */
	private String semicolonDelimiter = ";";

	/**
	 * 破折号分隔符。用于分隔路线代码。
	 */
	private String dashDelimiter = "-";

	
	/**
	 * 根据属性文件得到顶级物料号的构成方式，true：零件号+路线代码；false：零件号。
	 */
	private static boolean addRouteCode = RemoteProperty.getProperty(
			"addRouteCode", "false").equalsIgnoreCase("true");

	/**
	 * 根据属性文件得到定义的路线事物特性名称。
	 */
	private static String routeIBA = RemoteProperty.getProperty("routeIBA",
	"路线1;路线2");

	/**
	 * 物料拆分的管理域。
	 */
	private static String mSplitDefaultDomainName = (String) RemoteProperty
	.getProperty("materialSplitDefaultDomain", "System");

	private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";

	/**
	 * 根据属性文件得到是否发布回头件。
	 */
	private static boolean publicCyclePart = RemoteProperty.getProperty(
			"publicCyclePart", "false").equalsIgnoreCase("true");

	// 20080103 begin
	/**
	 * 拆分物料时针对特殊的路线代码有特殊的物料编码规则，如：散热器的“采购”代码，拆分含有这个代码的物料时，“采购”代码对应的物料编号使用零件号，其它物料编号使用：零件号+“-”+路线代码。
	 */
	private static final String specialRouteCode = RemoteProperty.getProperty(
			"specialRouteCode", "采购");

	/**
	 * 没有过程流程信息的工艺部门代码字符串，以“，”作为分隔符。发布该工艺时，不进行工序的编号是否在工艺的过程流程信息中的校验。
	 */
	private static final String noProcessRouteCode = RemoteProperty
	.getProperty("noProcessRouteCode", "CY");
	//刘家坤 20130102
	/**
	 * 获得工艺路线采购标记。
	 */
	private static HashMap CGBJMap = null;

	/**
	 * 以“，”作为分隔符，拆分物料时需要进行如果有多个路线代码且该路线代码不是在第一个，则在拆分时没有该路线代码的物料，
	 * 即在路线中去掉该路线代码后再拆分，如：散热器的“采购”代码，拆分含有这个代码的零部件时，如果路线为SX-DH-CG，
	 * 则当作路线SX-DH处理。如果路线为SX-CG-DH，则当作路线SX-DH处理。如果路线为CG-SX-DH，
	 * 则仍然当作路线CG-SX-DH处理。如果路线只是CG，则仍当作路线CG处理。
	 */
	private static final String deleteWhenSplitRouteCode = RemoteProperty
	.getProperty("deleteWhenSplitRouteCode", "采购");

	/**
	 * deleteWhenSplitRouteCode的集合。
	 */
	private static HashMap specRCHashMap = null;

	/**
	 * 根据属性文件得到定义的默认选择的路线事物特性名称。
	 */
	private static String defRouteIBA = RemoteProperty.getProperty(
			"defRouteIBA", "路线1");

	/**
	 * 逗号分隔符。用于分隔useProcessPartRouteCode。
	 */
	private String delimiter = "，";
	private String delimiter1 = ",";
	//获得一级子件
	public Collection getAllSubParts(QMPartIfc partIfc) throws QMException {
		Collection result = new Vector();
		//Collection filterIndex = new ArrayList();
		if (partIfc != null) {
			//将当前件加入到列表
			//result.add(partIfc);
			result = getSubParts(partIfc);
			//将一级子件加入到列表
			//result.add(col);
		}
		return result;
	}
	//获得一级子件
	 public Collection getSubParts(QMPartIfc partIfc) throws QMException {
			PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubParts begin ....");
			// 如果条件成立，抛出PartEception异常"参数不能为空"
			if (partIfc == null)
				throw new PartException(RESOURCE, "CP00001", null);
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			Collection collection = pservice.navigateValueInfo(partIfc, "usedBy",
					"PartUsageLink");
			Object[] tempArray = (Object[]) collection.toArray();
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper
					.getService("VersionControlService");
			Vector result = new Vector();
			Vector tempResult = new Vector();
			for (int i = 0; i < tempArray.length; i++) {
				tempResult = new Vector(vcservice
						.allVersionsOf((QMPartMasterIfc) tempArray[i]));
				if (tempResult != null && (tempResult.iterator()).hasNext())
					result.addElement((tempResult.iterator()).next());
			}
			PartDebug.trace(this, PartDebug.PART_SERVICE,
					"getSubParts end....return is Collection ");
			return result;
		}
	/**
	 * 拆分物料，ERP集成专用
	 * @param coll 零部件的集合
	 * @param baselineiD 零部件存放的基线
	 * @param isPublishRoute 是否由路线发布
	 * @param routeID 发布的路线
	 * @throws QMException
	 */
	public Vector split(String routeID)throws QMException
	{
		HashMap hamap;
		PersistService pservice;
		Vector filterPartMap = new Vector();
		QMPartIfc partIfc;
		String filterPartBsoIDs[];
		PartHelper partHelper =  new PartHelper();
		// 用来标识是按照艺准发布还是按照，bom发布
		// 获得二级路线 routepartlink集合
		HashMap partLinkMap = new HashMap();
		// 存储父子关系
		HashMap partTemp = new HashMap();
		HashMap partTempNew = new HashMap();
		try {
			RequestHelper.initRouteHashMap();
			hamap = new HashMap();
			Collection collection = new Vector();
			pservice = (PersistService) EJBServiceHelper
					.getService("PersistService");
			StandardPartService sp = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");

			// 如果是按照艺准发布，则如果当前部件有子件的话需要对当前部件的叶子物料与子件物料的关系进行处理questions
			if (routeID == null)
				throw new QMException("要发布的路线不存在，不能进行数据发布！！");
			TechnicsRouteListIfc routelistifc = (TechnicsRouteListIfc) pservice
					.refreshInfo(routeID);
			//获得路线关联的零部件
			Collection coll1 = getPartByRouteList(routelistifc, partLinkMap);
			//System.out.println("11111111111111111111111111111coll1=============="+coll1);
			if (coll1 == null)
				throw new QMException("没有获得关联的零件");
			// collection.addAll(coll1);
			// 刘家坤20140102 获得一级子件
			Iterator it = coll1.iterator();
			
			while (it.hasNext()) {
				QMPartIfc part = (QMPartIfc) it.next();
//				System.out.println("11111111111111111111111111111part1=============="+part);
//				System.out.println("collection000000000000000000000000000=============="+collection);
				Collection colPart = getAllSubParts(part);
				Iterator iter = colPart.iterator();
				while (iter.hasNext()) {
					QMPartIfc subpart = (QMPartIfc) iter.next();
					subpart=partHelper.filterLifeState(subpart);
					if(subpart!=null){
						if(!partTempNew.containsKey(subpart.getBsoID())){
							if (!partTemp.containsKey(subpart.getBsoID())) {
								partTemp.put(subpart.getBsoID(), part);
								partTempNew.put(subpart.getBsoID(), subpart);
								collection.add(subpart);
//								System.out.println("collection11111111111111111111111111111=============="+collection);
//								System.out.println("subpart=============="+subpart);
							}
						}
						
					}
					
					
				}
				//System.out.println("11111111111111111111111111111part2=============="+part);
				if(!partTempNew.containsKey(part.getBsoID())){
					collection.add(part);
					partTempNew.put(part.getBsoID(), part);
				}
				
				
			//	System.out.println("collection222222222222222222222222=============="+collection);
			}

			/// System.out.println("collection3333333333333333333=============="+collection);
			// System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz1111111111111=============="+partLinkMap);
			// 获得需要拆分的零部件的集合
			filterPartMap = filterParts(collection, partLinkMap);
			//System.out.println("11111111111111111111111111111filterPartMap=============="+filterPartMap);
			if (filterPartMap == null || filterPartMap.size() == 0) {
				//logger.debug("经过过滤没有需要拆分的零部件。");
				System.out.println("经过过滤没有需要拆分的零部件。");
				return null;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new QMException(ex);
		}
		partIfc = null;
		filterPartBsoIDs = new String[filterPartMap.size()];
		// 获得所有需要拆分的零部件的id
		for (int mm = 0; mm < filterPartMap.size(); mm++) {
			String partid = (String) filterPartMap.elementAt(mm);
			filterPartBsoIDs[mm] = partid;
		}

//		 System.out.println("filterPartBsoIDs.lengthzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz=============="+filterPartBsoIDs.length);
		// System.out.println("filterPartMapzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+filterPartMap);
		// 获得所有零件的路线
		// 所有路线存储对象，包括一级路线和二级路线
		HashMap routeTempMap = new HashMap();

		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			Object[] objRoute = new Object[2];
			partIfc = (QMPartIfc) pservice.refreshInfo(filterPartBsoIDs[i]);

			Vector routevec = new Vector();
			// 一级路线
			Vector routevec1 = new Vector();

			ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) partLinkMap
					.get(partIfc.getBsoID());
			if (partLinkMap.get(partIfc.getBsoID()) == null) {
				routevec = RequestHelper.getRouteBranchs(partIfc, null);
			} else {
				routevec = RequestHelper.getRouteBranchs(partIfc, link);
			}
			// System.out.println("routevecczzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+routevec);
			routevec1 = PartHelper.getRouteBranchs(partIfc, null);
			objRoute[0] = routevec;
			objRoute[1] = routevec1;
			routeTempMap.put(partIfc.getBsoID(), objRoute);

		}
		// 针对每一个零部件进行处理
		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			partIfc = (QMPartIfc) pservice.refreshInfo(filterPartBsoIDs[i]);
			// System.out.println("partIfczzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+partIfc);

			List usageLinkList = getUsageLinks(partIfc);
			Object[] objRoute = new Object[2];
			Vector routevec = new Vector();
			// 一级路线
			Vector routevec1 = new Vector();
			objRoute = (Object[]) routeTempMap.get(partIfc.getBsoID());
			routevec = (Vector) objRoute[0];
			routevec1 = (Vector) objRoute[1];
			// CCBegin by dikefeng
			// 20100624，判断是不是通过工艺路线发布，如果是通过工艺路线，则将前期缓存的listroutepartlinkinfo传过去，以便
			// 取到正确的路线
			// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa00000000000=============="+partIfc.getBsoID()+"sssssssssss"+partIfc.getPartNumber());
			// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa=============="+partLinkMap);
			// //艺准下的件发布过就不再发了 dele
			ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) partLinkMap
					.get(partIfc.getBsoID());

			// 制造路线
			String routeAsString = "";
			// 一级路线
			String routeAsString1 = "";
			String routeAllCode1 = "";
			String routeAssemStr1 = "";
			// 全路线串
			String routeAllCode = "";
			// 装配路线   
			String routeAssemStr = "";
			String lsbb = "";
			//System.out.println("routevecuuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa=============="+routevec);
			boolean isMoreRoute = false;
			if (routevec.size() != 0) {
				if (routevec.size() > 0) {
					routeAsString = (String) routevec.elementAt(5);
					routeAllCode = (String) routevec.elementAt(1);
					routeAssemStr = (String) routevec.elementAt(2);
					isMoreRoute = (new Boolean((String) routevec.elementAt(4)))
							.booleanValue();
					//lsbb = (String) routevec.elementAt(6);
				}

				if (routevec1.size() > 0) {
					routeAsString1 = (String) routevec1.elementAt(5);
					routeAllCode1 = (String) routevec1.elementAt(1);
					routeAssemStr1 = (String) routevec1.elementAt(2);
				}

				// 刘家坤 20140102
				// 1) 采购标识属性选择“X”，则在制造路线前添加“毛坯 ”路线单位，再进行拆分。
				// 2) 采购标识属性选择“X-1”，则在制造路线前添加“半成品”路线单位，再进行拆分。
				String cgbs = "";
				if(link!=null){
					cgbs = link.getStockID();
					lsbb = link.getAttribute1();
				}
				 
				// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa00000000000=============="+link);
				// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa00000000000=============="+cgbs);
//CCBegin SS1
				//CCBegin SS4
				/*
				if (cgbs != null&&cgbs != "") {
					//CCEnd SS1
					if (cgbs.equals("X")) {
						routeAsString = "毛坯-" + routeAsString;
						routeAllCode = "毛坯-" + routeAllCode;
					} else if (cgbs.equals("X-1")) {
						routeAsString = "半成品-" + routeAsString;
						routeAllCode = "半成品-" + routeAllCode;

					}
				}
				*/
				//CCEnd SS4

				 //System.out.println("routeAllCode111111111111111111111111111111111111111111111111111111111wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+routeAllCode);

			}
//			CCBegin SS5
//			CCBegin SS6
//			 获取当前零件的资料夹
			String folder = partIfc.getLocation(); 
			// 技术中心源版本
			String jsbb = partHelper.getPartVersion(partIfc);
//			CCBEnd SS5
			//System.out.println("partnumber000000000000000000"+partIfc.getPartNumber());
            String partnumber = partHelper.getPartNumber(partIfc,folder,lsbb,jsbb);
            //if(partIfc.getPartNumber().equals("1701110-A4K")){
              System.out.println("partnumber11111111111111========"+partnumber);
              System.out.println("lsbb11111111111111========"+lsbb);
            //}

			// 根据零部件编号，删除所有的物料拆分和物料拆分结构here
			// 为了下发删除标记，在物料表和结构表中都记录了关于物料和物料结构的应该删除的记录，这里在重新拆分之前先行删除

			deleteMaterialStructure(partIfc, true,partnumber);
			List oldMSplitList = deleteMaterialSplit(partIfc, true,partnumber);
			
			List mSplitListBiaoJi = new ArrayList();
			// 获得零件已发布的物料
			mSplitListBiaoJi = getAllMSplit(partnumber);
//			CCBEnd SS5
			// 直接物料，不再缓存物料编号
			HashMap oldMaterialHashMap = new HashMap();
			for (int dd = 0; dd < mSplitListBiaoJi.size(); dd++) {
				MaterialSplitIfc a = (MaterialSplitIfc) mSplitListBiaoJi
						.get(dd);
				oldMaterialHashMap.put(a.getMaterialNumber(), a);

			}
			// System.out.println("dddddddddddddddddddssssssss=========="+oldMaterialHashMap);

			// 根据零部件编号，获得所有物料拆分结构，放到mStructureListBaoJi中
			List mStructureListBaoJi = new ArrayList();
			//degug
			mStructureListBaoJi = getMStructure(partIfc.getPartNumber());
			// 直接缓存结构，不再缓存编号
			HashMap oldMaterialStructrue = new HashMap();
			for (int ff = 0; ff < mStructureListBaoJi.size(); ff++) {
				MaterialStructureIfc s = (MaterialStructureIfc) mStructureListBaoJi
						.get(ff);
				oldMaterialStructrue.put(s.getParentNumber() + ";;;"
						+ s.getChildNumber(), s);
			}

			// 如果零部件的一级路线的制造路线最后一个单位不是“轴” ，并且制造路线包含“轴”
			// 则该件不向ERP系统发布
		//	 System.out.println("routeAsString1ssssssssssss=========="+routeAsString1);
			List routeCodeListTemp = getRouteCodeList(routeAsString1);
			boolean flag1 = true;
			for (int m = routeCodeListTemp.size() - 1; m >= 0; m--) {

				// 如果零部件的一级路线的制造路线最后一个单位不是“轴” ，并且制造路线包含“轴”，则该件不向ERP系统发布
				if (m == routeCodeListTemp.size() - 1) { 
					String routeLast = (String) routeCodeListTemp.get(m);
					//System.out.println("routeLastssssssssssss=========="+routeLast);
					if (!routeLast.equals("轴")
							&& (routeCodeListTemp.contains("轴"))) {
						//System.out.println("进来了==========");
						flag1 = false;
					}
				}
			}

			//如果零部件的二级路线为空、一级路线为“协=轴”并且该件没有父零部件，则此零部件不做发布
		
			//此处的意思是，这个零件是路线关联的零件
			
			if (!partTemp.containsKey(partIfc.getBsoID())) {
				if (routeAllCode == null || routeAllCode.length() == 0) {
					if (routeAllCode1.equals("协=轴")) {
						flag1 = false;
					}
				}
			}   
			//System.out.println("flag1ssssssssssss=========="+flag1);
			if (!flag1) {
				filterPartMap.remove(filterPartBsoIDs[i]);
				continue;
			}
			
			// 如果艺准关联的零部件的一级子件二级路线为空，一级路线为“协=轴”
			// 则该件的二级路线制造单位虚拟为“协”；装配路线单位和父件的二级路线中轴齿中心
			// 的第一个加工车间一致（即制造路线第一个单位是“轴”，或者第一个是“协”第二个是轴）
			//此处的意思是，这个零件是路线关联的零件的子件
			if (partTemp.containsKey(partIfc.getBsoID())) {
				if (routeAllCode == null || routeAllCode.length() == 0) {
					if (routeAllCode1.equals("协=轴")) {
						Vector routev = new Vector();

						routeAsString = "协";
						QMPartIfc parentPart = (QMPartIfc) partTemp.get(partIfc
								.getBsoID());
						Object[] objRout = (Object[]) routeTempMap.get(parentPart
								.getBsoID());
						routev = (Vector) objRoute[0];
						String parentAss = (String) routev.elementAt(2);
						if (parentAss == null || parentAss.length() == 0)
							continue;
						String[] aa = parentAss.split(parentAss);
						if (aa[0].equals("协")) {
							routeAssemStr = aa[1];
						} else { 
							routeAssemStr = aa[0];
						}
						routeAllCode = "协=" + routeAssemStr;

					}
				}
			
			}

			// 需要进行物料拆分的情况下，首先将制造路线分解为一个个路线字符

			List routeCodeList = getRouteCodeList(routeAsString);
//			 System.out.println("routeAsString==" + routeAsString);
//			 System.out.println("routeCodeList==" + routeCodeList);
			if(partIfc.getPartNumber().equals("1701516A8A")){
			 System.out.println("routeCodeListrouteCodeListww======="+routeCodeList);
			}
			HashMap routeCodeMap = new HashMap(5);
			Vector mSplitList = new Vector();
			Vector co = new Vector();

			for (int m = routeCodeList.size() - 1; m >= 0; m--) {
				String routeCode = (String) routeCodeList.get(m);
				String makeCodeNameStr = "";
				makeCodeNameStr = RemoteProperty
						.getProperty("com.faw_qm.erp.routecode." + routeCode);
//				System.out.println("routeCode==" + routeCode);
//				System.out.println("makeCodeNameStr==" + makeCodeNameStr);
				// CCBegin by chudaming 20100428 回头件+1的规则调换
				int n = 0;
				for (int k = m - 1; k >= 0; k--) {
					String routeCode1 = (String) routeCodeList.get(k);
					String makeCodeNameStr1 = RemoteProperty
							.getProperty("com.faw_qm.erp.routecode."
									+ routeCode1);
					if (makeCodeNameStr != null) {
						if (makeCodeNameStr.equals(makeCodeNameStr1)) {
							n++;

						}
					}

				}
				
				// System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn============================================"+jsbb);
				// CCEnd by chudaming 20100428 回头件+1的规则调换
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				//System.out.println("lsbb==" + lsbb);
		

				if (makeCodeNameStr == null)
					makeCodeNameStr = "";
				if (m == routeCodeList.size() - 1) {
					// 如果是路线串中的最后一个路线单位
					mSplitIfc.setVirtualFlag(false);

					// 根据配置文件获取零部件制造路线最后一个路线单位的编码方式，如果是零件号+路线单位，则直接设置
					//如果是最后一个路线单位则，不需要加路线单位
					String materialNumber = "";
	
					materialNumber=partnumber;

					//System.out.println("materialNumber11111111=="+ materialNumber);
//					CCBegin SS6
					mSplitIfc.setMaterialNumber(materialNumber);
//					CCBEnd SS5
					//根物料
					mSplitIfc.setRootFlag(true);
					hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
				}
				// 如果不是最后一个路线单位：
				// 获取顶级物料构成规则，如果是加路线单位（青岛为不加），则判断当前路线是否已经出现过
				// 没有则设置为零部件编号+路线单位；否则设置为零部件编号+路线单位+数字
				// 如果规则为顶级物料不加路线单位（其实这个判断和当前的这个分支没有什么关系），这个地方唯一的区别就是判断是不是有特殊路线
				// 单位 以及是否有回头见现象
				else {
					String materialNumber = "";
					if(makeCodeNameStr.equals("")){
						materialNumber=partnumber;
					}else{
						materialNumber=partnumber+ dashDelimiter + makeCodeNameStr;;
					}
				
		

//					System.out.println("materialNumber22222222=="
//							+ materialNumber);
					if (n == 0) {
					
						mSplitIfc.setMaterialNumber(materialNumber);
					} else {
					
						mSplitIfc.setMaterialNumber(materialNumber + dashDelimiter + n);
						// routeCodeMap.get(makeCodeNameStr));
					}

				}
				//CCBegin SS5
				mSplitIfc.setPartNumber(partnumber);
//				CCBEnd SS5
				if (jsbb != null && jsbb.length() > 0) {
					mSplitIfc.setPartVersion(jsbb);
				} else {
					mSplitIfc.setPartVersion(partIfc.getVersionID());
				}

				mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
				mSplitIfc.setSplited(true);
				//如果是最下级物料
				if (m == 0) {
					
					if (usageLinkList != null && usageLinkList.size() > 0)
						mSplitIfc.setStatus(2);//并且零部件有下一级结构
					else
						mSplitIfc.setStatus(0);//没有下级结构
				} else {
					mSplitIfc.setStatus(1);//中级层
				}
				mSplitIfc.setRouteCode(routeCode);
				mSplitIfc.setRoute(routeAllCode.toString());
				// 根据零部件设置物料其他的需要发布的属性信息

				setMaterialSplit(partIfc, mSplitIfc);

				// // 同料尺功能取消
				// mSplitIfc.setIsMoreRoute(isMoreRoute);

				mSplitList.add(mSplitIfc);

			}

			Object[] objs = mSplitList.toArray();

			// System.out.println("mSplitList==" + mSplitList);
			for (Iterator iter = mSplitList.iterator(); iter.hasNext();) {
				// questions，如果零部件由于路线变化需要重新拆分，但是其中某几个物料的内容没有发生变化，那么也将这些物料设置为U标识
				MaterialSplitIfc ifc = (MaterialSplitIfc) iter.next();
//				System.out
//						.println("dsadasdasdasdasd" + ifc.getMaterialNumber());

				//查看系统中是否存在该零件的物料，如果没有打N，如果有，判断旧物料是否包含该物料，如果没有打N，有则打U
				//System.out.println("old1111111111111111=========="+oldMaterialHashMap);
				if (oldMaterialHashMap != null && oldMaterialHashMap.size() > 0) {
					// System.out.println("00000=========="+ifc.getMaterialNumber());
					// System.out.println("old1111111111111111=========="+oldMaterialHashMap.get(ifc.getMaterialNumber()));
					if (oldMaterialHashMap.get(ifc.getMaterialNumber()) != null) {
						
						ifc.setMaterialSplitType("U");
						// }
						// CCEnd by chudaming 20100928
						// System.out.println("dele====================================="+oldMaterialHashMap.get(ifc.getMaterialNumber()));
						pservice
								.deleteValueInfo((MaterialSplitIfc) oldMaterialHashMap
										.get(ifc.getMaterialNumber()));
						oldMaterialHashMap.remove(ifc.getMaterialNumber());
						// System.out.println("deleh========================"+oldMaterialHashMap);
					} else {
						ifc.setMaterialSplitType("N");
					}
				} else {
					// System.out.println("bunengba----------");
					ifc.setMaterialSplitType("N");
				}
				// 这里进行了所有拆分物料的保存
				ifc = (MaterialSplitIfc) pservice.saveValueInfo(ifc);
//				System.out.println("getMaterialNumber=="
//						+ ifc.getMaterialNumber());

				// System.out.println("shenmenea ========"+ifc.getRoute());
			}
			// 将旧物料中数据都打删除标记
			Iterator oldMaterialIter = oldMaterialHashMap.keySet().iterator();
			while (oldMaterialIter.hasNext()) {
				String key = (String) oldMaterialIter.next();
				MaterialSplitIfc oldMaterialSplitIfc = (MaterialSplitIfc) oldMaterialHashMap
						.get(key);
				oldMaterialSplitIfc.setMaterialSplitType("D");
				// System.out.println("222222222222222222"+oldMaterialSplitIfc.getMaterialNumber());
				pservice.saveValueInfo(oldMaterialSplitIfc);
			}
			// CCEnd by dikefeng 20100419
			//保存同一个零件拆分后物料直接的关系
			for (int p = 0; p < mSplitList.size(); p++) {
				MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
						.get(p);
				MaterialSplitIfc childMSfc = null;
				if (p != mSplitList.size() - 1)//如果不是最后一级，则获取最下一级物料
					childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
				if (parentMSIfc != null) {
					MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
					if (childMSfc != null && parentMSIfc.getStatus() == 1) {
						// if(!hasSplitedStructure(parentMSIfc, childMSfc))
						// {
						logger.debug("1-有下级物料。");

						mStructureIfc.setParentPartNumber(parentMSIfc
								.getPartNumber());
						mStructureIfc.setParentPartVersion(parentMSIfc
								.getPartVersion());
						mStructureIfc.setParentNumber(parentMSIfc
								.getMaterialNumber());
						if (oldMaterialStructrue.get(parentMSIfc
								.getMaterialNumber()
								+ ";;;" + childMSfc.getMaterialNumber()) != null) {
							MaterialStructureIfc oldStructrue = (MaterialStructureIfc) oldMaterialStructrue
									.get(parentMSIfc.getMaterialNumber()
											+ ";;;"
											+ childMSfc.getMaterialNumber());
							mStructureIfc.setMaterialStructureType("O");
							pservice.deleteValueInfo(oldStructrue);
							oldMaterialStructrue.remove(parentMSIfc
									.getMaterialNumber()
									+ ";;;" + childMSfc.getMaterialNumber());
						} else {
							mStructureIfc.setMaterialStructureType("N");
						}
						// CCEnd by dikefeng 200100419
						mStructureIfc.setChildNumber(childMSfc
								.getMaterialNumber());
						mStructureIfc.setQuantity(1.0F);
						mStructureIfc.setLevelNumber(String.valueOf(p));
						mStructureIfc
								.setDefaultUnit(childMSfc.getDefaultUnit());
						// 这里进行了物料拆分结构的保存 dikef
						mStructureIfc = (MaterialStructureIfc) pservice
								.saveValueInfo(mStructureIfc);
						// }
					} else if (parentMSIfc.getStatus() == 2)
						logger.debug("2—此物料下要挂接原零部件的子件。");
					else if (parentMSIfc.getStatus() == 0)
						logger.debug("0-最底层物料。");
				}
			}
			// 给使用的物料结构打删除标记
			Iterator oldStruIte = oldMaterialStructrue.keySet().iterator();
			while (oldStruIte.hasNext()) {

				MaterialStructureIfc oldStru = (MaterialStructureIfc) oldMaterialStructrue
						.get(oldStruIte.next());

				oldStru.setMaterialStructureType("D");
				pservice.saveValueInfo(oldStru);
				// System.out.println("------------------------------------sssssssss--------------"+oldStru.get);
			}

		}
		if (logger.isDebugEnabled())
			logger.debug("split(String, boolean) - end");
		// CCBegin by dikefeng
		// 20090422，为了使得在发布的时候知道本次新发的物料都有哪些，需要在这里把过滤后的零件清单返回一个
	
		return filterPartMap;
		// CCEnd by dikefeng 20090422
	}

	

	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		Collection c = new Vector();
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
		query.addCondition(cond);
		Collection coll = ps.findValueInfo(query);
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			//刘家坤由于路线零件表中版本属性现在还没有，所以这个方法暂时隐藏，等浏阳加入这个版本之后再打开
			Collection cc = getPartByListRoutepart(linkInfo);
//			QMPartIfc partInfo = null;
//			for(Iterator ii = cc.iterator(); ii.hasNext(); c.add(partInfo))
//				partInfo = (QMPartIfc)ii.next();

		}

		return c;
	}
	
	

	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		//刘家坤获取路线关联零件版本，轴齿路线关联零件具体版本 20131227
		String partBsoid = linkInfo.getRightBsoID();
		
		QMPartIfc part = (QMPartIfc) ps.refreshInfo(partBsoid);
		//System.out.println("part==="+part);
		QMQuery query = new QMQuery("QMPart");
		QueryCondition qc = new QueryCondition("bsoID", "=", part.getBsoID());
		query.addCondition(qc);
//		
		String partVersion = part.getVersionID();
		//System.out.println("partVersion==="+partVersion);
		if(partVersion != null && partVersion.length() > 0)
		{
//			Vector a=new Vector();
			QMQuery qmquery = new QMQuery("FilterPart");
			 
	          qmquery.addCondition(new QueryCondition("partNumber", "=",
	        		  part.getPartNumber()));
	          
	          qmquery.addOrderBy("versionValue",false);
	          Collection col1 = ps.findValueInfo(qmquery, false);
	          Iterator iter22 = col1.iterator();
	          FilterPartInfo ifc =null;
	        //  System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1);
//	          System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1.size());
	          if(col1!=null){
	        	  if(col1.size()>0){
 	        	 while (iter22.hasNext()) {
 	        		ifc=(FilterPartInfo)iter22.next();
// 	        		a.add(ifc);
 	        	 }
 	        	 }
                 }
	         // System.out.println("ifc==="+ifc);
	          if(ifc!=null){
	        	 // System.out.println("ssssssss==ffffffffffffffffffff=");
	          if(partVersion
	     	         .compareTo(
	     	        		ifc.getVersionValue()) < 0)
	          {
//	        	    System.out.println("ssssssss==gggggggggggggggggggggggggg=");
	        	    QueryCondition qc1 = new QueryCondition("versionID", "=", ifc.getVersionValue());
//	        	    System.out.println("ssssssss==hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh=");
		  			query.addAND();
		  			query.addCondition(qc1);
	          }else{
	        	  	QueryCondition qc1 = new QueryCondition("versionID", "=", partVersion);
		  			query.addAND();
		  			query.addCondition(qc1);
	          }
	          }else{
					QueryCondition qc1 = new QueryCondition("versionID", "=", partVersion);
					query.addAND();
					query.addCondition(qc1);
	          }
		}
	//	刘家坤轴齿 20131231 关联的是当前版本所以不是最后一个版本
//		QueryCondition qc2 = new QueryCondition("iterationIfLatest", true);
//		query.addAND();
//		query.addCondition(qc2);
	
		return ps.findValueInfo(query, false);
		}catch(QMException e){
			e.printStackTrace();
			throw e;
		}
	}
	//父件的直接子件大于1000时发布的数据不全。CCBegin by chudaming  20091221
	private Collection getSubParts(Collection coll, ManagedBaselineIfc baseline)
	throws QMException
	{
		try
		{
			Collection coll1;
			   PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			   String masterids[] = new String[coll.size()];
			   Object objs[] = coll.toArray();
//			   System.out.println("coll==============="+coll);
//			   System.out.println("objsobjsobjs==============="+objs.length);
//			   System.out.println("baselinebaseline000000==============="+baseline.getBsoID());
			   for(int i = 0; i < objs.length; i++)
			   {
			    if(objs[i] instanceof QMPartIfc)
			    {
			     QMPartIfc part = (QMPartIfc)objs[i];
			     masterids[i] = part.getBsoID();
//			     System.out.println("part.getBsoID()part.getBsoID()==111111111111111============="+part.getBsoID());
			    }
			    if(objs[i] instanceof String)
			    {
			    	
			     String partid = (String)objs[i];
			     QMPartIfc part = (QMPartIfc)pservice.refreshInfo(partid);
//			     System.out.println("part.getBsoID()part.getBsoID()==2222222222222222222222222222222============="+part.getBsoID());
			     String masterid = part.getBsoID();
			     masterids[i] = masterid;
			    }
			   }
//			   System.out.println("sssssssssssssssssssssss"+masterids);
			   int a=(int)masterids.length/500;
			   int b=masterids.length%500;
			   if(b>0)
			   {
			    a=a+1;
			   }
			   Vector vec=new Vector();
			   for(int j=0;j<a;j++)
			   {
			    String[] mas;
			    if(j!=a-1)
			    {
			    	
			     mas=new String[500];
//			     System.out.println("masmasmas1111111111111"+mas);
			    }
			    else if(j==a-1 && b>0)
			    {
			     mas=new String[b];
//			     System.out.println("masmasmas2222222222222222"+mas);
			    }
			    else
			    {
			     mas=new String[500];
//			     System.out.println("masmasmas3333333333333333"+mas);
			    }
//			    System.out.println("masmasmas44444444444444444444"+mas.length);
			    for(int k=0;k<mas.length;k++)
			    {
			     mas[k]=masterids[j*500+k];
			    }
			    vec.add(mas);
			   }
			   QMQuery query = new QMQuery("QMPart");
			   int i = query.appendBso("PartUsageLink", false);
			   int j = query.appendBso("BaselineLink", false);
			   int m = query.appendBso("QMPartMaster", false);
			   //query.addCondition(i, new QueryCondition("rightBsoID", "IN", masterids));
			   query.addCondition(m, i, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(j, new QueryCondition("rightBsoID", "=", baseline.getBsoID()));
			   query.addAND();
			   query.addCondition(0, j, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
			   query.addAND();
			   query.addLeftParentheses();
//			   System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwww==============="+vec.size());
			   for(int k=0;k<vec.size();k++)
			   {
				   
			    String[] mast=(String[])vec.elementAt(k);
			    query.addCondition(i, new QueryCondition("rightBsoID", "IN", mast));
			    if(k!=vec.size()-1)
			    {
			     query.addOR();
			    }
			   }
			   query.addRightParentheses();
//			   System.out.println("queryquery========="+query.getDebugSQL());
			   coll1 = pservice.findValueInfo(query);
			   return coll1;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Collection getSubPartsErp(Collection coll)
	throws QMException
	{
		try
		{
			Collection coll1;
			   PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			   String masterids[] = new String[coll.size()];
			   Object objs[] = coll.toArray();
			   
			   for(int i = 0; i < objs.length; i++)
			   {
			    if(objs[i] instanceof QMPartIfc)
			    {
			     QMPartIfc part = (QMPartIfc)objs[i];
			     masterids[i] = part.getBsoID();
			    }
			    if(objs[i] instanceof String)
			    {
			     String partid = (String)objs[i];
			     QMPartIfc part = (QMPartIfc)pservice.refreshInfo(partid);
			     String masterid = part.getBsoID();
			     masterids[i] = masterid;
			    }
			   }
			   
			   int a=(int)masterids.length/500;
			   int b=masterids.length%500;
			   if(b>0)
			   {
			    a=a+1;
			   }
			   Vector vec=new Vector();
			   for(int j=0;j<a;j++)
			   {
			    String[] mas;
			    if(j!=a-1)
			    {
			     mas=new String[500];
			    }
			    else if(j==a-1 && b>0)
			    {
			     mas=new String[b];
			    }
			    else
			    {
			     mas=new String[500];
			    }
			    for(int k=0;k<mas.length;k++)
			    {
			     mas[k]=masterids[j*500+k];
			    }
			    vec.add(mas);
			   }
			   QMQuery query = new QMQuery("QMPart");
			   int i = query.appendBso("PartUsageLink", false);
			   int m = query.appendBso("QMPartMaster", false);
			   query.addCondition(m, i, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
			   query.addAND();
			   query.addLeftParentheses();
			   for(int k=0;k<vec.size();k++)
			   {
			    String[] mast=(String[])vec.elementAt(k);
			    query.addCondition(i, new QueryCondition("rightBsoID", "IN", mast));
			    if(k!=vec.size()-1)
			    {
			     query.addOR();
			    }
			   }
			   query.addRightParentheses();
			   coll1 = pservice.findValueInfo(query);
//			   System.out.println("coll1coll1coll1============"+coll1);
			   return coll1;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Collection getAllSubParts(Collection partIDs, ManagedBaselineIfc baseline)
	throws QMException
	{
		try
		{
			Collection collection;
			Vector resultVector;
			collection = getSubParts(partIDs, baseline);
			resultVector = new Vector();
			if(collection == null || collection.size() == 0)
				return resultVector;
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructure(collection, baseline);
			resultVector.addAll(tempVector);
			return resultVector;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Collection getAllSubPartsErp(Collection partIDs)
	throws QMException
	{
		try
		{
			Collection collection;
			Vector resultVector;
			collection = getSubPartsErp(partIDs);
			resultVector = new Vector();
			if(collection == null || collection.size() == 0)
				return resultVector;
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructureErp(collection);
			resultVector.addAll(tempVector);
			return resultVector;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Vector productStructure(Collection coll, ManagedBaselineIfc baseline)
	throws QMException
	{
		Vector resultVector = new Vector();
		Collection collection = getSubParts(coll, baseline);
		if(collection == null || collection.size() < 1)
		{
			return resultVector;
		} else
		{
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructure(collection, baseline);
			resultVector.addAll(tempVector);
			return resultVector;
		}
	}
	private Vector productStructureErp(Collection coll)
	throws QMException
	{
		Vector resultVector = new Vector();
		Collection collection = getSubPartsErp(coll);
		if(collection == null || collection.size() < 1)
		{
			return resultVector;
		} else
		{
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructureErp(collection);
			resultVector.addAll(tempVector);
			return resultVector;
		}
	}



	private final Vector filterParts(Collection parts)
	throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("filterParts(String) - start");
			logger.debug("参数：partBsoIDs==" + parts);
		}
		List routeList = new ArrayList();
		logger.debug("参数：routeList==" + routeList);
		logger.debug("参数：routeList.size()==" + routeList.size());
		logger.debug("要过滤的零部件的集合==" + parts);
		Object lalatestPartBsoIDs[] = parts.toArray();
//		将同一个集合中的重复件取消
		List filterPartList = filterByIdentity(lalatestPartBsoIDs);
//		System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== ");
		
		filterBySplitRule(filterPartList);
//		System.out.println("100304++++++++++++++++++++++=======chuqu===filterBySplitRule======== ");
		Vector filterPartMap = new Vector();
		for(int i = 0; i < filterPartList.size(); i++)
		{
			QMPartIfc filterPartIfc = (QMPartIfc)filterPartList.get(i);
			filterPartMap.add(filterPartIfc.getBsoID());
		}

		if(logger.isDebugEnabled())
			logger.debug("filterParts(String) - end     " + filterPartMap);
		return filterPartMap;
	}
	private final Vector filterParts1(Collection parts,String partroute)
	throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("filterParts(String) - start");
			logger.debug("参数：partBsoIDs==" + parts);
		}
		List routeList = new ArrayList();
		logger.debug("参数：routeList==" + routeList);
		logger.debug("参数：routeList.size()==" + routeList.size());
		logger.debug("要过滤的零部件的集合==" + parts);
		Object lalatestPartBsoIDs[] = parts.toArray();
//		将同一个集合中的重复件取消
		List filterPartList = filterByIdentity1(lalatestPartBsoIDs,partroute);
//		System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== ");
		
		filterBySplitRule1(filterPartList,partroute);
//		System.out.println("100304++++++++++++++++++++++=======chuqu===filterBySplitRule======== ");
		Vector filterPartMap = new Vector();
		for(int i = 0; i < filterPartList.size(); i++)
		{
			QMPartIfc filterPartIfc = (QMPartIfc)filterPartList.get(i);
			filterPartMap.add(filterPartIfc.getBsoID());
		}

		if(logger.isDebugEnabled())
			logger.debug("filterParts(String) - end     " + filterPartMap);
		return filterPartMap;
	}
	private final void filterBySplitRule(final List filterPartList)
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("参数：filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // 不需拆分的零部件集合。
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
//	   System.out.println("cacacalalalalalala==="+partIfc.getPartNumber());
	   List mSplitList = new ArrayList();
	   try {
	    // 根据零件号获取已拆分的物料。
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber());
	   } catch (QMException e) 
	   {
	    Object[] aobj = new Object[] { partIfc.getPartNumber() };
	    // "查找编号为*的零部件对应的物料时出错！"
	    logger.error(Messages.getString("Util.51", aobj) + e);
	    throw new ERPException(e, RESOURCE, "Util.51", aobj);
	   }
	   // 相同唯一标识的物料只保留一个。
	   HashMap maHashMap = new HashMap(5);
//	   System.out.println("100304++++++++mSplitList.size()"+mSplitList.size()+"============pppp"+mSplitList);
	   for (int k = 0; k < mSplitList.size(); k++) 
	   {
	    MaterialSplitIfc ma = (MaterialSplitIfc) mSplitList.get(k);
	    String objectIdectity = getObjectIdentity(ma);
//	    System.out.println("100304++++++++maHashMamaHashMap"+maHashMap);
//	    System.out.println("100304++++++++objectIdectityobjectIdectity"+objectIdectity);
	    if (!maHashMap.containsKey(objectIdectity)) 
	    {
//	    	System.out.println("100304++++++++fffffffffffffffffffffffffffffffffffffffffffffffffffffffp");
	     maHashMap.put(objectIdectity, ma);
	     
	    }
	   }
	   Object[] materialSplitIfcs = maHashMap.values().toArray();
	   // 是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。");
	    }
//	    System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
	      if (partIfc.getVersionID().compareTo(
	        materialSplitIfc.getPartVersion()) < 0) 
	      {
//	    	  System.out.println("100304++++++++0000000000-------------"+materialSplitIfcs.length);
	       removePartList.add(partIfc);
	       break;
	      } else 
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
//	        System.out.println("100304++++++++11111111111111-------------"+materialSplitIfcs.length);
	       } else if (tempMaterialSplit.getPartVersion()
	         .compareTo(
	           materialSplitIfc.getPartVersion()) < 0) 
	       {
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length);
	        tempMaterialSplit = materialSplitIfc;
	       }
	      }
	     }
	    }
	   }
	   if (logger.isDebugEnabled()) 
	   {
	    logger.debug("partIfc.getPartNumber()=="
	      + partIfc.getPartNumber());
	   }
	   // 是否需要拆分处理规则2.表中数据不存在，需要拆分。
	   if (tempMaterialSplit == null) 
	   {
//		   System.out.println("100304++++++++33333333333333333-------------"+materialSplitIfcs.length);
	    if (logger.isDebugEnabled()) 
	    {
	     logger
	     .debug("---------是否需要拆分处理规则2.表中数据不存在，需要拆分。或版本是旧的，不需拆分。");
	    }
	    
	   }
	   // 是否需要拆分处理规则3。表中数据存在，检查版本。
	   else 
	   {
	    if (logger.isDebugEnabled()) 
	    {
	     logger.debug("---------是否需要拆分处理规则3。表中数据存在，检查版本。");
	    }
	    // 是否需要拆分处理规则3.b。版本没有变化。
	    if (partIfc.getVersionID().equals(
	      tempMaterialSplit.getPartVersion())) 
	    {
	     if (logger.isDebugEnabled()) 
	     {
	      logger.debug("---------是否需要拆分处理规则3.b。版本没有变化。");
	     }
	     // 是否需要拆分处理规则3.b.2。状态没有变化，废弃此记录。
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {
	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
	      String routeAsString = "";
	      String routeAllCode = "";
	      String routeAssemStr = "";
	      if(routevec.size() != 0)
	      {
	       routeAsString = (String)routevec.elementAt(0);
	       routeAllCode = (String)routevec.elementAt(1);
	      }
	      boolean flag1=false;
	      boolean flag2=false;
	      if(routeAllCode==null || routeAllCode=="")
	      {
	       flag1=true;
	      }
	      if(tempMaterialSplit.getRoute()==null || tempMaterialSplit.getRoute()=="")
	      {
	       flag2=true;
	      }
//	      System.out.println("100304++++++++flag1flag1flag1flag1flag1-------------"+flag1);
//	      System.out.println("100304++++++++flag2flag2flag2flag2flag2-------------"+flag2);
	      if(flag1&&flag2)
	      {
//	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {
//	    	   System.out.println("100304++++++++flag222222222222222222222222222222222222-------------");
	        removePartList.add(partIfc);
	       } else
	       {
//	    	   System.out.println("100304++++++++mSplitList.size()mSplitList.size()-------------"+mSplitList.size());
	        for(int k = 0; k < mSplitList.size(); k++)
	        {
	         MaterialSplitIfc mSplitIfc = (MaterialSplitIfc)mSplitList.get(k);
//	         System.out.println("100304++++++++flag333333333333333333333-------------"+mSplitIfc.getRoute());
//	         System.out.println("100304++++++++flag9999999-------------"+tempMaterialSplit.getRoute());
//	         System.out.println("100304++++++++flaghhhhhhhhhhhhhhhhhh-------------"+mSplitIfc.getState());
//	         System.out.println("100304++++++++oooooooooooooooiiiiiii-------------"+routeAllCode);
//	         System.out.println("0610 ++++++++zzzzzzzz-------------"+mSplitIfc.getRoute().equals(routeAllCode));
	         //CCBegin by chudaming 20090304
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()) && mSplitIfc.getRoute().equals(routeAllCode))
	        	 //CCEnd by chudaming 20090304
	         {
//	        	 System.out.println("100304++++++++flag444444444444444444444-------------");
		          mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
		          setMaterialSplit(partIfc, mSplitIfc);
		          mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
		          removePartList.add(partIfc);
//		          System.out.println("100304++++++++flag5555555555555-------------");
	         }
	        }
	 
	       }
	      } 
	      
	     }
	     // 是否需要拆分处理规则3.a版本变化，需要拆分；
	     else 
	     {
	      if (logger.isDebugEnabled()) 
	      {
	       logger.debug("---------是否需要拆分处理规则3.a版本变化，需要拆分；");
	      }
	     }
	    }
	   }
	   // 将未改变的零部件和只改变状态的零部件从拆分列表中除去。
//	   System.out.println("100304++++++++flag66666666666666-------------"+removePartList);
	   //CCBegin by chudaming 20100609  dele.循环里remove的，实际上就删除了filterPartList奇数位的数据
	   //filterPartList.removeAll(removePartList);
//	   System.out.println("100304++++++++flag7777777777777777777-------------"+removePartList);
	   if (logger.isDebugEnabled()) {
	    logger.debug("filterBySplitRule(List) - end"); //$NON-NLS-1$
	   }
	  }
	 
	  filterPartList.removeAll(removePartList);
	  //CCEnd by chudaming 20100609
	 } 
	
	
	private final void filterBySplitRule1(final List filterPartList,String partroute)
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("参数：filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // 不需拆分的零部件集合。
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
//	   System.out.println("cacacalalalalalala==="+partIfc.getPartNumber());
	   List mSplitList = new ArrayList();
	   try {
	    // 根据零件号获取已拆分的物料。
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber());
	   } catch (QMException e) 
	   {
	    Object[] aobj = new Object[] { partIfc.getPartNumber() };
	    // "查找编号为*的零部件对应的物料时出错！"
	    logger.error(Messages.getString("Util.51", aobj) + e);
	    throw new ERPException(e, RESOURCE, "Util.51", aobj);
	   }
	   // 相同唯一标识的物料只保留一个。
	   HashMap maHashMap = new HashMap(5);
//	   System.out.println("100304++++++++mSplitList.size()"+mSplitList.size()+"============pppp"+mSplitList);
	   for (int k = 0; k < mSplitList.size(); k++) 
	   {
	    MaterialSplitIfc ma = (MaterialSplitIfc) mSplitList.get(k);
	    String objectIdectity = getObjectIdentity(ma);
//	    System.out.println("100304++++++++maHashMamaHashMap"+maHashMap);
//	    System.out.println("100304++++++++objectIdectityobjectIdectity"+objectIdectity);
	    if (!maHashMap.containsKey(objectIdectity)) 
	    {
//	    	System.out.println("100304++++++++fffffffffffffffffffffffffffffffffffffffffffffffffffffffp");
	     maHashMap.put(objectIdectity, ma);
	     
	    }
	   }
	   Object[] materialSplitIfcs = maHashMap.values().toArray();
	   // 是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。");
	    }
//	    System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
	      if (partIfc.getVersionID().compareTo(
	        materialSplitIfc.getPartVersion()) < 0) 
	      {
//	    	  System.out.println("100304++++++++0000000000-------------"+materialSplitIfcs.length);
	       removePartList.add(partIfc);
	       break;
	      } else 
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
//	        System.out.println("100304++++++++11111111111111-------------"+materialSplitIfcs.length);
	       } else if (tempMaterialSplit.getPartVersion()
	         .compareTo(
	           materialSplitIfc.getPartVersion()) < 0) 
	       {
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length);
	        tempMaterialSplit = materialSplitIfc;
	       }
	      }
	     }
	    }
	   }
	   if (logger.isDebugEnabled()) 
	   {
	    logger.debug("partIfc.getPartNumber()=="
	      + partIfc.getPartNumber());
	   }
	   // 是否需要拆分处理规则2.表中数据不存在，需要拆分。
	   if (tempMaterialSplit == null) 
	   {
//		   System.out.println("100304++++++++33333333333333333-------------"+materialSplitIfcs.length);
	    if (logger.isDebugEnabled()) 
	    {
	     logger
	     .debug("---------是否需要拆分处理规则2.表中数据不存在，需要拆分。或版本是旧的，不需拆分。");
	    }
	    
	   }
	   // 是否需要拆分处理规则3。表中数据存在，检查版本。
	   else 
	   {
	    if (logger.isDebugEnabled()) 
	    {
	     logger.debug("---------是否需要拆分处理规则3。表中数据存在，检查版本。");
	    }
	    // 是否需要拆分处理规则3.b。版本没有变化。
	    if (partIfc.getVersionID().equals(
	      tempMaterialSplit.getPartVersion())) 
	    {
	     if (logger.isDebugEnabled()) 
	     {
	      logger.debug("---------是否需要拆分处理规则3.b。版本没有变化。");
	     }
	     // 是否需要拆分处理规则3.b.2。状态没有变化，废弃此记录。
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {
	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
	      String routeAsString = "";
	      String routeAllCode = "";
	      String routeAssemStr = "";
//	      if(routevec.size() != 0)
//	      {
//	       routeAsString = (String)routevec.elementAt(0);
	       routeAllCode =partroute;
//	      }
	      boolean flag1=false;
	      boolean flag2=false;
	      if(routeAllCode==null || routeAllCode=="")
	      {
	       flag1=true;
	      }
	      if(tempMaterialSplit.getRoute()==null || tempMaterialSplit.getRoute()=="")
	      {
	       flag2=true;
	      }
//	      System.out.println("100304++++++++flag1flag1flag1flag1flag1-------------"+flag1);
//	      System.out.println("100304++++++++flag2flag2flag2flag2flag2-------------"+flag2);
	      if(flag1&&flag2)
	      {
//	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {
//	    	   System.out.println("100304++++++++flag222222222222222222222222222222222222-------------");
	        removePartList.add(partIfc);
	       } else
	       {
//	    	   System.out.println("100304++++++++mSplitList.size()mSplitList.size()-------------"+mSplitList.size());
	        for(int k = 0; k < mSplitList.size(); k++)
	        {
	         MaterialSplitIfc mSplitIfc = (MaterialSplitIfc)mSplitList.get(k);
//	         System.out.println("100304++++++++flag333333333333333333333-------------"+mSplitIfc.getRoute());
//	         System.out.println("100304++++++++flag9999999-------------"+tempMaterialSplit.getRoute());
//	         System.out.println("100304++++++++flaghhhhhhhhhhhhhhhhhh-------------"+mSplitIfc.getState());
//	         System.out.println("100304++++++++oooooooooooooooiiiiiii-------------"+routeAllCode);
//	         System.out.println("0610 ++++++++zzzzzzzz-------------"+mSplitIfc.getRoute().equals(routeAllCode));
	         //CCBegin by chudaming 20090304
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()) && mSplitIfc.getRoute().equals(routeAllCode))
	        	 //CCEnd by chudaming 20090304
	         {
//	        	 System.out.println("100304++++++++flag444444444444444444444-------------");
		          mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
		          setMaterialSplit(partIfc, mSplitIfc);
		          mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
		          removePartList.add(partIfc);
//		          System.out.println("100304++++++++flag5555555555555-------------");
	         }
	        }
	 
	       }
	      } 
	      
	     }
	     // 是否需要拆分处理规则3.a版本变化，需要拆分；
	     else 
	     {
	      if (logger.isDebugEnabled()) 
	      {
	       logger.debug("---------是否需要拆分处理规则3.a版本变化，需要拆分；");
	      }
	     }
	    }
	   }
	   // 将未改变的零部件和只改变状态的零部件从拆分列表中除去。
//	   System.out.println("100304++++++++flag66666666666666-------------"+removePartList);
	   //CCBegin by chudaming 20100609  dele.循环里remove的，实际上就删除了filterPartList奇数位的数据
	   //filterPartList.removeAll(removePartList);
//	   System.out.println("100304++++++++flag7777777777777777777-------------"+removePartList);
	   if (logger.isDebugEnabled()) {
	    logger.debug("filterBySplitRule(List) - end"); //$NON-NLS-1$
	   }
	  }
	  //CCBegin by chudaming 20100609 循环里remove的，实际上就删除了filterPartList奇数位的数据
	  filterPartList.removeAll(removePartList);
	  //CCEnd by chudaming 20100609
	 } 
	private final List filterByIdentity(final Object[] partBsoIDs)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
			logger.debug("参数：parts==" + partBsoIDs);
		}
		PersistService pservice =(PersistService)EJBServiceHelper.getService("PersistService");
		final List filterPartList = new ArrayList();
		// 存放通过唯一标识过滤后的零部件。
		final HashMap tempPartMap = new HashMap();
		for (int i = 0; i < partBsoIDs.length; i++) {
			
			QMPartIfc partIfc=null;
			if(partBsoIDs[i] instanceof String)
			{
				partIfc= (QMPartIfc)  pservice.refreshInfo((String)partBsoIDs[i]);
			}
			else if(partBsoIDs[i] instanceof BaseValueIfc)
			{
				partIfc=(QMPartIfc)pservice.refreshInfo((BaseValueIfc)partBsoIDs[i]);
			}
			String partIdentity = getObjectIdentity(partIfc);
			// 根据零部件的唯一标识过滤零部件集合，重复的记录被过滤掉，不再拆分。
			if (!tempPartMap.containsKey(partIdentity)) {
				tempPartMap.put(partIdentity, partIfc);
				filterPartList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - end      " + filterPartList); //$NON-NLS-1$
		}
		return filterPartList;
	}
	private final List filterByIdentity1(final Object[] partBsoIDs,String partroute)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
			logger.debug("参数：parts==" + partBsoIDs);
		}
		PersistService pservice =(PersistService)EJBServiceHelper.getService("PersistService");
		final List filterPartList = new ArrayList();
		// 存放通过唯一标识过滤后的零部件。
		final HashMap tempPartMap = new HashMap();
		for (int i = 0; i < partBsoIDs.length; i++) {
			
			QMPartIfc partIfc=null;
			if(partBsoIDs[i] instanceof String)
			{
				partIfc= (QMPartIfc)  pservice.refreshInfo((String)partBsoIDs[i]);
			}
			else if(partBsoIDs[i] instanceof BaseValueIfc)
			{
				partIfc=(QMPartIfc)pservice.refreshInfo((BaseValueIfc)partBsoIDs[i]);
			}
			String partIdentity = getObjectIdentity1(partIfc,partroute);
			// 根据零部件的唯一标识过滤零部件集合，重复的记录被过滤掉，不再拆分。
			if (!tempPartMap.containsKey(partIdentity)) {
				tempPartMap.put(partIdentity, partIfc);
				filterPartList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - end      " + filterPartList); //$NON-NLS-1$
		}
		return filterPartList;
	}
	private final String getObjectIdentity(final BaseValueIfc baseValueIfc) throws QMException{
		if(logger.isDebugEnabled())
		{
			logger.debug("getObjectIdentity(BaseValueIfc) - start");
			logger.debug("\u53C2\u6570\uFF1AbaseValueIfc==" + baseValueIfc);
		}
		String returnString = "";
		if(baseValueIfc instanceof QMPartIfc)
		{
			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
			String routeAsString = "";
			String routeAllCode = "";
			String routeAssemStr = "";
			if(routevec.size() != 0)
			{
				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = (String)routevec.elementAt(1);
			}
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			returnString = part.getPartNumber() + part.getVersionID() + part.getLifeCycleState().getDisplay() + routeAllCode;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute();
			}
		if(logger.isDebugEnabled())
			logger.debug("getObjectIdentity(BaseValueIfc) - end     " + returnString);
		return returnString;
	}
	private final String getObjectIdentity1(final BaseValueIfc baseValueIfc,String partroute) throws QMException{
		if(logger.isDebugEnabled())
		{
			logger.debug("getObjectIdentity(BaseValueIfc) - start");
			logger.debug("\u53C2\u6570\uFF1AbaseValueIfc==" + baseValueIfc);
		}
		String returnString = "";
		if(baseValueIfc instanceof QMPartIfc)
		{
//			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
//			String routeAsString = "";
			String routeAllCode = "";
//			String routeAssemStr = "";
//			if(routevec.size() != 0)
//			{
//				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = partroute;
//			}
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			returnString = part.getPartNumber() + part.getVersionID() + part.getLifeCycleState().getDisplay() + routeAllCode;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute();
			}
		if(logger.isDebugEnabled())
			logger.debug("getObjectIdentity(BaseValueIfc) - end     " + returnString);
		return returnString;
	}
	/**
	 * 执行物料拆分前的过滤零部件功能。得到符合拆分条件，并需要进行拆分处理的零部件集合。
	 * 1.先根据零部件唯一标识规则过滤零部件。唯一标识为：编号+大版本号+生命周期状态。重复的记录被废弃掉，不再拆分。
	 * 2.根据是否需要拆分规则处理零部件。 是否需要拆分处理规则：查询物料拆分表中数据。
	 * 1.比较需要拆分的零部件与物料拆分表里对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的数据，则不再拆分，废弃此零部件。
	 * 2.表中数据不存在，需要拆分。 3.表中数据存在，检查版本，a版本变化，需要拆分；
	 * b版本没有变化，1状态变化，找到物料拆分表中相应数据，替换属性，不拆分； 2状态没有变化，废弃此记录。
	 * @param parts
	 *            存放零部件的集合
	 * @return 经过过滤后，符合拆分条件，并需要进行拆分处理的零部件集合。
	 * @throws QMException
	 */
	
	private final Vector filterParts(Collection parts,HashMap hashMap)
	throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("filterParts(String) - start");
			logger.debug("参数：partBsoIDs==" + parts);
		}
		List routeList = new ArrayList();
		logger.debug("参数：routeList==" + routeList);
		logger.debug("参数：routeList.size()==" + routeList.size());
		logger.debug("要过滤的零部件的集合==" + parts);
		Object lalatestPartBsoIDs[] = parts.toArray();
//		将同一个集合中的重复件取消
		List filterPartList = filterByIdentity(lalatestPartBsoIDs,hashMap);
		//System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== "+filterPartList);
		
		filterBySplitRule(filterPartList,hashMap);
		//System.out.println("100304++++++++++++++++++++++=======chuqu===filterBySplitRule======== "+filterPartList);
		Vector filterPartMap = new Vector();
		for(int i = 0; i < filterPartList.size(); i++)
		{
			QMPartIfc filterPartIfc = (QMPartIfc)filterPartList.get(i);
			filterPartMap.add(filterPartIfc.getBsoID());
		}

		if(logger.isDebugEnabled())
			logger.debug("filterParts(String) - end     " + filterPartMap);
//		System.out.println("kuleeeeeeeeeeeeeeeeeeee++++++++++++++++++++++==== "+filterPartMap);
		return filterPartMap;
	}



	/**
	 * 拆分物料。
	 * noted by dikefeng 20100419,因为青岛没有物料拆分过程，所以这个方法好像是用不上，就先不改了，如果需要的话再改
	 * @param partBsoIDs
	 *            以“;”为分隔符的零部件BsoID集合。
	 * @param routes
	 *            需要拆分的路线。
	 * @param doSplit
	 *            为处理“若零件旧版本已拆分为物料，本次拆分是否重新拆分”的情况准备的boolean变量。true：重新拆分；false：不重新拆分。
	 * @throws QMException
	 */
	public void split(String partBsoIDs, String routes, boolean doSplit)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("split(String, boolean) - start"); //$NON-NLS-1$
			logger.debug("参数：partBsoIDs==" + partBsoIDs);
			logger.debug("参数：doSplit==" + doSplit);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		// 执行物料拆分前的过滤零部件功能。得到符合拆分条件，并需要进行拆分处理的零部件集合。
		HashMap filterPartMap = filterParts(partBsoIDs, routes);
		if (filterPartMap == null || filterPartMap.size() == 0) {
			// 经过过滤没有需要拆分的零部件。
			logger.debug("经过过滤没有需要拆分的零部件。");   
			return;
		}
		// 需要拆分的零部件。
		QMPartIfc partIfc = null;
		Object[] filterPartBsoIDs = filterPartMap.keySet().toArray();
		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			partIfc = (QMPartIfc)pservice.refreshInfo((String)filterPartBsoIDs[i]);
			// 获得该零部件的一级子结构。
			List usageLinkList = getUsageLinks(partIfc);
			// 物料拆分的路线属性。如：04－02－01;04-03-01
			String routeAsString = (String) filterPartMap.get(partIfc
					.getBsoID());

			// 若准备拆分的零部件的旧版本已拆分为已转换的物料，并在界面上选择了不重新拆分，就不重新拆分物料，
			// 但要替换旧版本号为新零部件的版本号。包括物料拆分表和物料结构表。
			// 但如果物料表里的记录是未转换的物料，还是需要重新拆分。
			List sPlitedMSplitList = getSplitedMSplit(partIfc.getPartNumber());
			if (sPlitedMSplitList != null && sPlitedMSplitList.size() > 0
					&& !doSplit) {
				replaceMaterialStructure(partIfc);
				replaceMaterialSplit(partIfc);
				continue;
			}
			// 删除零部件对应的物料结构表中已经存在的旧版本的物料结构关系。如果拆分未成功，则无法回滚。
			deleteMaterialStructure(partIfc);
			// 删除零部件对应的物料拆分表中已经存在的旧版本的物料拆分对象。同时返回删除掉的旧版本的物料对应的记录集合。为替换物料结构表中子物料为旧版本物料时使用。
			List oldMSplitList = deleteMaterialSplit(partIfc);
			// 路线为空，即不需拆分零部件。
			if (routeAsString != null && routeAsString.toString().equals("")) {
				logger.debug("路线为空，即不需拆分零部件。");
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				mSplitIfc.setRootFlag(true);
				mSplitIfc.setPartNumber(partIfc.getPartNumber());
				mSplitIfc.setPartVersion(partIfc.getVersionID());
				mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
				mSplitIfc.setMaterialNumber(partIfc.getPartNumber());
				mSplitIfc.setSplited(false);
				// 如果无子件则设置层级状态为0，有子件就设置为2。物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
				if (usageLinkList != null && usageLinkList.size() > 0) {
					mSplitIfc.setStatus(2);
				} else {
					mSplitIfc.setStatus(0);
					// 不设置路线代码，与此物料有关的物料拆分后的路线代码。
					// msIfc.setRouteCode("");
					// 不设置路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
					// msIfc.setRoute("");
				}
				setMaterialSplit(partIfc, mSplitIfc);
				mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
			}
			// 路线不为空，需要拆分零部件。
			else {  
				logger.debug("路线不为空，需要拆分零部件。" + routeAsString);
				boolean hasSpecialRouteCode = false;
				// 20081205 zhangq begin
				// 路线，如：04－02－01。
				// String route = routeAsString;
				// logger.debug("route==" + route);
				// StringTokenizer st = new StringTokenizer(route,
				// dashDelimiter);
				// List routeCodeList = new ArrayList();
				// while (st.hasMoreTokens())
				// {
				// routeCodeList.add(st.nextToken());
				// }
				// 路线代码集合，如：[04,02,01]。
				List routeCodeList = getRouteCodeList(routeAsString);
				// 20081205 zhangq end
				logger.debug("routeCodeList==" + routeCodeList);
				// 放置路线代码，为判断回头件做准备。
				HashMap routeCodeMap = new HashMap(5);
				// 放置根据路线拆分好的物料拆分对象。
				List mSplitList = new ArrayList();
				if (routeCodeList.contains(specialRouteCode)) {
					hasSpecialRouteCode = true;
					// 根据路线代码拆分零部件。
				}
				for (int m = routeCodeList.size() - 1; m >= 0; m--) {
					String routeCode = (String) routeCodeList.get(m);
					// 处理回头件。
					if (!routeCodeMap.containsKey(routeCode)) {
						routeCodeMap.put(routeCode, "0");
					}
					MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
					// 设置物料号，拆分后的物料号，由零件号+“-”+路线代码组成,回头件第二次出现加尾号"-1"。
					if (m == routeCodeList.size() - 1) {
						// 其它企业中顶级物料号＝零件号+“-”+路线代码。
						if (addRouteCode) {
							mSplitIfc.setMaterialNumber(partIfc.getPartNumber()
									+ dashDelimiter + routeCode);
							// 散热器公司情况，分两种，一种是有特殊工艺路线代码，另一种是没有特殊路线代码。
						} else {
							// 有特殊路线代码情况，分两种，当前路线代码就是特殊路线代码，否则不是。
							if (hasSpecialRouteCode) {
								// 当前路线代码是特殊路线代码，物料号＝零件号。
								if (routeCode.equals(specialRouteCode)) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber());
								}
								// 否则，物料号＝零件号+“-”+路线代码。
								else {
									// CCBegin zhangq 20090324
									// 当拆分的零部件的类型不为产品时，最后一个路线代码的物料的物料号=零件号+路线代码
									if (partIfc.getPartType().toString()
											.equalsIgnoreCase("product")) {
										mSplitIfc.setMaterialNumber(partIfc
												.getPartNumber());
									} else {
										mSplitIfc.setMaterialNumber(partIfc
												.getPartNumber()
												+ dashDelimiter + routeCode);
									}
									// mSplitIfc.setMaterialNumber(partIfc
									// .getPartNumber()
									// + dashDelimiter + routeCode);
									// CCEnd zhangq 20090324
								}
							}
							// 没有特殊路线代码情况，散热器中顶级物料号＝零件号。
							else {
								// CCBegin zhangq 20090324
								// 当拆分的零部件的类型不为产品时，最后一个路线代码的物料的物料号=零件号+路线代码
								if (partIfc.getPartType().toString()
										.equalsIgnoreCase("product")) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber());
								} else {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter + routeCode);
								}

								// mSplitIfc.setMaterialNumber(partIfc
								// .getPartNumber());
								// CCEnd zhangq 20090324
							}
							mSplitIfc.setRootFlag(true);
						}
					} else {
						// 其它企业，物料号＝零件号+“-”+路线代码。。
						if (addRouteCode) {
							// 处理回头件。第一次出现不加尾号。
							if (routeCodeMap.get(routeCode).equals("0")) {
								mSplitIfc.setMaterialNumber(partIfc
										.getPartNumber()
										+ dashDelimiter + routeCode);
							}
							// 回头件第二次出现加尾号“-”+出现次数。第二次出现为1，第三次出现为2，依此类推。
							else {
								mSplitIfc.setMaterialNumber(partIfc
										.getPartNumber()
										+ dashDelimiter
										+ routeCode
										+ dashDelimiter
										+ routeCodeMap.get(routeCode));
							}
						}
						// 散热器公司情况，分两种，一种是有特殊工艺路线代码，另一种是没有特殊路线代码。
						else {
							// 有特殊路线代码并且当前路线代码就是特殊路线代码，物料号＝零件号。
							if (hasSpecialRouteCode
									&& routeCode.equals(specialRouteCode)) {
								// 处理回头件。第一次出现不加尾号。
								if (routeCodeMap.get(routeCode).equals("0")) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber());
								}
								// 回头件第二次出现加尾号“-”+出现次数。第二次出现为1，第三次出现为2，依此类推。
								else {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter
											+ routeCodeMap.get(routeCode));
								}
							}
							// 否则，物料号＝零件号+“-”+路线代码。
							else {
								// 处理回头件。第一次出现不加尾号。
								if (routeCodeMap.get(routeCode).equals("0")) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter + routeCode);
								}
								// 回头件第二次出现加尾号“-”+出现次数。第二次出现为1，第三次出现为2，依此类推。
								else {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter
											+ routeCode
											+ dashDelimiter
											+ routeCodeMap.get(routeCode));
								}
							}
						}
					}
					// 缓存回头件出现次数。
					routeCodeMap.put(routeCode, String
							.valueOf(Integer.parseInt((String) routeCodeMap
									.get(routeCode)) + 1));
					// 判断该拆分出来的物料是否唯一，如果原物料拆分表已存在该条记录，就废弃掉，不保存。继续下一个循环。但处理结构时还是需要用到的。
					MaterialSplitIfc tempMSplit = getMSplit(mSplitIfc
							.getMaterialNumber());
					if (tempMSplit != null) {
						mSplitList.add(tempMSplit);
						continue;
					}
					mSplitIfc.setPartNumber(partIfc.getPartNumber());
					mSplitIfc.setPartVersion(partIfc.getVersionID());
					mSplitIfc
					.setState(partIfc.getLifeCycleState().getDisplay());
					// 设置已转换，零件是否转换为物料，0—未转换，1—已转换。
					mSplitIfc.setSplited(true);
					// 设置层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
					if (m == 0) {
						if (usageLinkList != null && usageLinkList.size() > 0) {
							mSplitIfc.setStatus(2);
						} else {
							mSplitIfc.setStatus(0);
						}
					} else {
						mSplitIfc.setStatus(1);
					}
					// 设置路线代码，与此物料有关的物料拆分后的路线代码。
					mSplitIfc.setRouteCode(routeCode);
					// 设置路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
					mSplitIfc.setRoute(routeAsString.toString());
					setMaterialSplit(partIfc, mSplitIfc);
					mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
					logger.debug("mSplitIfc==" + mSplitIfc);
					mSplitList.add(mSplitIfc);
				}
				logger.debug("mSplitList==" + mSplitList);
				// 处理结构时，已经过滤掉了旧的版本，不需考虑版本比较的问题，直接处理新拆分的子物料和下面需要挂接的零部件就行。
				for (int p = 0; p < mSplitList.size(); p++) {
					MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
					.get(p);
					logger.debug("parentMSIfc==" + parentMSIfc);
					logger.debug("parentMSIfc.getStatus=="
							+ parentMSIfc.getStatus());
					MaterialSplitIfc childMSfc = null;
					if (p != mSplitList.size() - 1) {
						childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
					}
					logger.debug("childMSfc==" + childMSfc);
					if (parentMSIfc != null) {
						MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
						// 1-有下级物料。
						if (childMSfc != null && parentMSIfc.getStatus() == 1) {
							if (!hasSplitedStructure(parentMSIfc, childMSfc)) {
								logger.debug("1-有下级物料。");
								mStructureIfc.setParentPartNumber(parentMSIfc
										.getPartNumber());
								mStructureIfc.setParentPartVersion(parentMSIfc
										.getPartVersion());
								mStructureIfc.setParentNumber(parentMSIfc
										.getMaterialNumber());
								mStructureIfc.setChildNumber(childMSfc
										.getMaterialNumber());
								mStructureIfc.setQuantity(1);
								// xieb应该设置为int型。
								mStructureIfc.setLevelNumber(String.valueOf(p));
								mStructureIfc.setDefaultUnit(childMSfc
										.getDefaultUnit());
								// 不设置选装策略码。
								// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
								mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
							}
						}
						// 2—此物料下要挂接原零部件的子件。
						else if (parentMSIfc.getStatus() == 2) {
							logger.debug("2—此物料下要挂接原零部件的子件。");
							// 去掉原需求中处理子结构的逻辑。
						}
						// 0-最底层物料。
						else if (parentMSIfc.getStatus() == 0) {
							logger.debug("0-最底层物料。");
						}
					} // if(parentMSIfc != null)
				} // for (int p = 0; p < mSplitList.size(); p++)
			} // 路线不为空，需要拆分零部件。
			// 拆分物料时，如果旧版本物料作为改制件使用时，处理被改制件的结构信息时，需要删除该结构信息。否则会导致找不到业务对象的异常。
			for (int j = 0; j < oldMSplitList.size(); j++) {
				MaterialSplitIfc oldMSplitIfc = (MaterialSplitIfc) oldMSplitList
				.get(j);
				List mStructureList = getMStructureByChild(oldMSplitIfc
						.getMaterialNumber());
				for (int k = 0; k < mStructureList.size(); k++) {
					MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) mStructureList
					.get(k);
					if (mStructureIfc.getParentPartNumber() != partIfc
							.getPartNumber()) {
						pservice.deleteValueInfo(mStructureIfc);
					}
				}
			}
		} // for (int i = 0; i < filterPartList.size(); i++)
		if (logger.isDebugEnabled()) {
			logger.debug("split(String, boolean) - end"); //$NON-NLS-1$
		}
	}

	// 20080103 end

	/**
	 * 替换物料拆分表里的版本。版本不为空时才替换新版本。
	 * 
	 * @param partIfc
	 *            新拆分的零部件。
	 * @throws QMException
	 */
	private void replaceMaterialSplit(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialSplit(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List mSplitList = getAllMSplit(partIfc.getPartNumber());
		if (mSplitList != null && mSplitList.size() > 0) {
			MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) mSplitList.get(0);
			if (mSplitIfc.getPartVersion() != null
					&& partIfc.getVersionID().compareTo(
							mSplitIfc.getPartVersion()) > 0) {
				Iterator iter = mSplitList.iterator();
				while (iter.hasNext()) {
					mSplitIfc = (MaterialSplitIfc) iter.next();
					mSplitIfc.setPartVersion(partIfc.getVersionID());
					mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialSplit(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * 替换物料结构表里的版本。版本不为空时才替换新版本。
	 * 
	 * @param partIfc
	 *            新拆分的零部件。
	 * @throws QMException
	 */
	private void replaceMaterialStructure(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialStructure(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		// 根据指定的父件号条件，找到结构表中所有符合条件的结构信息。
		List mStructureList = getMStructure(partIfc.getPartNumber());
		if (mStructureList != null && mStructureList.size() > 0) {
			MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) mStructureList
			.get(0);
			if (mStructureIfc.getParentPartVersion() != null
					&& partIfc.getVersionID().compareTo(
							mStructureIfc.getParentPartVersion()) > 0) {
				Iterator iter = mStructureList.iterator();
				while (iter.hasNext()) {
					mStructureIfc = (MaterialStructureIfc) iter.next();
					mStructureIfc.setParentPartVersion(partIfc.getVersionID());
					mStructureIfc = (MaterialStructureIfc)pservice.saveValueInfo(mStructureIfc);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialStructure(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * 删除零部件对应的物料拆分表中已经存在的旧版本的物料拆分对象。
	 * 
	 * @param partIfc
	 *            零部件。
	 * @return 删除掉的旧版本的物料对应的记录集合。
	 * @throws QMException
	 */
	private List deleteMaterialSplit(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialSplit(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List mSplitList = getAllMSplit(partIfc.getPartNumber());
		for (int i = 0; i < mSplitList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mSplitList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger
			.debug("deleteMaterialSplit(QMPartIfc) - end      " + mSplitList); //$NON-NLS-1$
		}
		return mSplitList;
	}
	/**
	 * 删除零部件对应的物料拆分表中已经存在的旧版本的物料拆分对象。
	 * added by dikefeng 20100419
	 * @param partIfc
	 *            零部件。
	 * @return 删除掉的旧版本的物料对应的记录集合。
	 * @throws QMException
	 */
	private List deleteMaterialSplit(QMPartIfc partIfc,boolean flag,String partNumber) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialSplit(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		final QMQuery query = new QMQuery("MaterialSplit");
		//		CCBegin SS5
		QueryCondition condition = new QueryCondition("partNumber",
				QueryCondition.EQUAL, partNumber);
		//CCBegin SS5
		query.addCondition(condition);
		if(flag)
		{
			query.addAND();
			query.addCondition(new QueryCondition("materialSplitType","=","D"));
		}
		List mSplitList=(List)pservice.findValueInfo(query);
		for (int i = 0; i < mSplitList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mSplitList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger
			.debug("deleteMaterialSplit(QMPartIfc) - end      " + mSplitList); //$NON-NLS-1$
		}
		return mSplitList;
	}
	/**
	 * 删除零部件对应的物料结构表中已经存在的旧版本的物料结构关系。
	 *  added by dikefeng 20100419
	 * @param partIfc
	 *            零部件。
	 * @throws QMException
	 */
	private void deleteMaterialStructure(QMPartIfc partIfc,boolean flag,String partNumber) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("MaterialStructure");
//		CCBegin SS5
		QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
				partNumber);
//		CCEnd SS5
		query.addCondition(condition1);
		if(flag)
		{
			query.addAND();
			query.addCondition(new QueryCondition("materialStructureType","=","D"));
		}
		List mStructureList=(List)pservice.findValueInfo(query);
		// 根据指定的父件号条件，找到结构表中所有符合条件的结构信息。
		for (int i = 0; i < mStructureList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mStructureList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}
	/**
	 * 删除零部件对应的物料结构表中已经存在的旧版本的物料结构关系。
	 * 
	 * @param partIfc
	 *            零部件。
	 * @throws QMException
	 */
	private void deleteMaterialStructure(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		// 根据指定的父件号条件，找到结构表中所有符合条件的结构信息。
		List mStructureList = getMStructure(partIfc.getPartNumber());
		for (int i = 0; i < mStructureList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mStructureList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}
	/**
	 * 根据父物料和子物料，判断物料挂接原零部件的逻辑是否执行过。
	 * 
	 * @param parentMSIfc
	 *            父物料。
	 * @param childMSIfc
	 *            子物料。
	 * @return 标识。
	 * @throws QMException
	 */
	private boolean hasSplitedStructure(MaterialSplitIfc parentMSIfc,
			MaterialSplitIfc childMSIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger
			.debug("hasSplitedStructure(MaterialSplitIfc, MaterialSplitIfc) - start"); //$NON-NLS-1$
		}
		// 根据指定的父件号和父物料号条件，找到结构表中所有符合条件的结构信息。
		List mStructureList = getMStructure(parentMSIfc.getPartNumber(),
				parentMSIfc.getMaterialNumber(), childMSIfc.getMaterialNumber());
		boolean flag = false;
		if (mStructureList != null && mStructureList.size() > 0) {
			flag = true;
		}
		if (logger.isDebugEnabled()) {
			logger
			.debug("hasSplitedStructure(MaterialSplitIfc, MaterialSplitIfc) - end       "
					+ flag); //$NON-NLS-1$
		}
		return flag;
	}

	/**
	 * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
	 * 
	 * @param partIfc
	 *            零部件。
	 * @throws QMException
	 *             return PartUsageLinkIfc的对象集合。
	 */
	private final List getUsageLinks(final QMPartIfc partIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		// 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
		List usesPartList = new ArrayList();
		try {
			StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			usesPartList = (List)spservice.getUsesPartMasters(partIfc);
		} catch (QMException e) {
			Object[] aobj = new Object[] { partIfc.getPartNumber() };
			// "获取名为*的零部件结构时出错！"
			logger.error(Messages.getString("Util.17", aobj) + e);
			throw new ERPException(e, RESOURCE, "Util.17", aobj);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - end"); //$NON-NLS-1$
		}
		return usesPartList;
	}

	/**
	 * 设置其它需要发布的属性，与物料拆分及结构无关，在零件拆分时复制到每个拆分后的物料记录行。
	 * 
	 * @param partIfc
	 * @param mSplitIfc
	 * @throws QMException
	 */
	private void setMaterialSplit(QMPartIfc partIfc, MaterialSplitIfc mSplitIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger
			.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
			logger.debug("参数：mSplitIfc==" + mSplitIfc);
		}
		mSplitIfc.setPartName(partIfc.getPartName());
		mSplitIfc.setDefaultUnit(partIfc.getDefaultUnit().getDisplay());
		mSplitIfc.setProducedBy(partIfc.getProducedBy().getDisplay());
		mSplitIfc.setPartType(partIfc.getPartType().getDisplay());
		mSplitIfc.setPartModifyTime(partIfc.getModifyTime());
		mSplitIfc.setOptionCode(partIfc.getOptionCode());
//		修改这个地方更改颜色件标识的信息 dikef
//		CCBegin by dikefeng 20100416,青岛汽车厂颜色件标识在零部件的IBA属性中，属性名为part_colorflag，在这里通过获取零部件IBA属性并将
//		该属性设置到物料中作为物料的颜色见标识
		PersistService pSerivce=(PersistService)EJBServiceHelper.getPersistService();
		QMQuery query = new QMQuery("StringValue");
	    int j = query.appendBso("StringDefinition", false);
	      QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=",partIfc.getBsoID());
	      query.addCondition(qc);
	      query.addAND();
	      QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
	      query.addCondition(0, j, qc1);
	      query.addAND();
	      QueryCondition qc2 = new QueryCondition("name", " = ", "part_colorflag");
	      query.addCondition(j, qc2);
	      Collection colorCollection = pSerivce.findValueInfo(query, false);
	      if(colorCollection==null||colorCollection.size()==0)
	    	  mSplitIfc.setColorFlag(false);
	      else{
	    	  StringValueIfc colorValue=(StringValueIfc)colorCollection.iterator().next();
	    	  //CCBegin by chudaming 20100608  颜色件按照路线拆分完毕后，其子件也被标注成颜色件
	    	  if(colorValue.getValue().equalsIgnoreCase("true")&&mSplitIfc.getRootFlag())
	    		  //CCEnd by chudaming 20100608
	    		  mSplitIfc.setColorFlag(true);
	    	  else
	    		  mSplitIfc.setColorFlag(false);
	      }
//		mSplitIfc.setColorFlag(partIfc.getColorFlag());
//		CCEnd by dikefeng 20100416
		mSplitIfc.setDomain(DomainHelper.getDomainID(mSplitDefaultDomainName));
		if (logger.isDebugEnabled()) {
			logger.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - end"); //$NON-NLS-1$
		}
	}

	// 20080103 begin
	/**
	 * 获取路线属性定义。
	 * 
	 * @return 路线属性定义的轻量级对象的集合。
	 * @throws QMException
	 */
	public List getRouteDefList() throws QMException
	// 20080103 end
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getRouteDefList() - start"); //$NON-NLS-1$
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List routeDefList = new ArrayList();
		List tempRouteDefList = new ArrayList();
		ArrayList list = new ArrayList();
		// 操作解析字符串
		StringTokenizer st = new StringTokenizer(routeIBA, ";");
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		for (int i = 0; i < list.size(); i++) {
			String routeName = (String) list.get(i);
			// 用编号作为查询条件，查找是否有符合条件的StringDefinition。
			final QMQuery query = new QMQuery("StringDefinition");
			QueryCondition condition = new QueryCondition("name",
					QueryCondition.EQUAL, routeName);
			query.addCondition(condition);
			tempRouteDefList.addAll((List)pservice.findValueInfo(query));
		}
		logger.debug("tempRouteDefList==" + tempRouteDefList);
		for (int i = 0; i < tempRouteDefList.size(); i++) {
			routeDefList
			.add(IBADefinitionObjectsFactory
					.newAttributeDefDefaultView((AttributeHierarchyChild) tempRouteDefList
							.get(i)));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getRouteDefList() - end" + routeDefList); //$NON-NLS-1$
		}
		return routeDefList;
	}

	// 20080103 begin
	/**
	 * 执行物料拆分前的过滤零部件功能。得到符合拆分条件，并需要进行拆分处理的零部件集合。
	 * 1.先根据零部件唯一标识规则过滤零部件。唯一标识为：编号+大版本号+生命周期状态。重复的记录被废弃掉，不再拆分。
	 * 2.根据是否需要拆分规则处理零部件。 是否需要拆分处理规则：查询物料拆分表中数据。
	 * 1.比较需要拆分的零部件与物料拆分表里对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的数据，则不再拆分，废弃此零部件。
	 * 2.表中数据不存在，需要拆分。 3.表中数据存在，检查版本，a版本变化，需要拆分；
	 * b版本没有变化，1状态变化，找到物料拆分表中相应数据，替换属性，不拆分； 2状态没有变化，废弃此记录。
	 * 
	 * @param partBsoIDs
	 *            以“;”为分隔符的零部件BsoID集合。
	 * @param routes
	 *            以“;”为分隔符的路线集合。
	 * @return 经过过滤后，符合拆分条件，并需要进行拆分处理的零部件集合。
	 * @throws QMException
	 */
	private final HashMap filterParts(String partBsoIDs, String routes)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterParts(String) - start"); //$NON-NLS-1$
			logger.debug("参数：partBsoIDs==" + partBsoIDs);
			logger.debug("参数：routes==" + routes);
		}
		VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
		List routeList = new ArrayList();
		if (routes != null && !routes.equals("")) {
			StringTokenizer st = new StringTokenizer(routes, semicolonDelimiter);
			// 20080102 zhangq add for srq begin:散热器的部门代码是汉字，需要转换，比如＂冷＂转换为＂ＬＱ＂．
			String routeString = "";
			while (st.hasMoreTokens()) {
				// routeList.add(st.nextToken());
				routeString = changeRoute(st.nextToken());
				routeList.add(routeString);
			}
			// 20080102 zhangq add for srq end
		}
		// 通过采用通知书或更改采用通知书获取关联的零部件。
		List partList = new ArrayList();
		try {
			partList = getAllPartByBsoID(partBsoIDs);
		} catch (QMException e) {
			// "通过采用通知书或更改采用通知书获取关联的零部件失败！"
			logger.error(Messages.getString("Util.22"), e);
			throw new ERPException(e, RESOURCE, "Util.22", null);
		}
		// 通过最新版序配置规范过滤零部件。
		HashMap latestIterationMap = new HashMap();
		logger.debug("参数：routeList==" + routeList);
		logger.debug("参数：partList==" + partList);
		logger.debug("参数：routeList.size()==" + routeList.size());
		logger.debug("参数：partList.size()==" + partList.size());
		for (int i = 0; i < partList.size(); i++) {
			QMPartIfc partIfc = (QMPartIfc) partList.get(i);
			partIfc = (QMPartIfc) vcservice.getLatestIteration(partIfc);
			latestIterationMap.put(partIfc.getBsoID(), routeList.get(i));
		}
		logger.debug("通过最新版序配置规范过滤零部件。latestIterationList=="
				+ latestIterationMap);
		Object[] lalatestPartBsoIDs = latestIterationMap.keySet().toArray();
		// 根据零部件唯一标识规则过滤零部件。唯一标识为：编号+大版本号+生命周期状态。并将过滤后的QMPartIfc集合存入filterPartList中。
		final List filterPartList = filterByIdentity(lalatestPartBsoIDs);
		// 根据是否需要拆分规则处理零部件。
		filterBySplitRule(filterPartList);
		HashMap filterPartMap = new HashMap();
		for (int i = 0; i < filterPartList.size(); i++) {
			QMPartIfc filterPartIfc = (QMPartIfc) filterPartList.get(i);
			filterPartMap.put(filterPartIfc.getBsoID(), latestIterationMap
					.get(filterPartIfc.getBsoID()));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterParts(String) - end     " + filterPartMap); //$NON-NLS-1$
		}
		return filterPartMap;
	}

	// 20080103 end
	/**
	 * 将部门代码从汉字转换为简称。
	 * 
	 * @param tempRouteCodes：路线，以dashDelimiter作为分隔符。
	 */
	public String changeRoute(String routeCodes) throws QMException {
		String routeID;
		CodingIfc codeIfc;
		String routeCode;
		String routeString = "";
		CodingManageService cmservice=(CodingManageService)EJBServiceHelper.getService("CodingManageService");
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		StringTokenizer routesTok = new StringTokenizer(routeCodes,
				dashDelimiter);
		boolean isFirst = true;
		while (routesTok.hasMoreTokens()) {
			routeCode = routesTok.nextToken();
			routeID = (String)cmservice.getIDbySort(routeCode);
			logger.debug("routeID is " + routeID);
			if (routeID != null) {
				codeIfc = (CodingIfc)pservice.refreshInfo(routeID);
				routeCode = codeIfc.getSearchWord();
				logger.debug("codeIfc.getSearchWord() is "
						+ codeIfc.getSearchWord());
				if (routeCode == null || routeCode.trim().length() <= 0) {
					logger
					.debug("tempRouteCode==null||tempRouteCode.trim().length()<=0 ");
					routeCode = codeIfc.getCodeContent();
				}
				logger.debug("codeIfc.getCodeContent() is "
						+ codeIfc.getCodeContent());
				logger.debug("tempRouteCode is " + routeCode);
			}
			if (isFirst) {
				routeString = routeCode;
				isFirst = false;
			} else {
				routeString += dashDelimiter + routeCode;
			}

		}
		return routeString;
	}

	/**
	 * 根据瘦客户端传递的以“;”作为分隔符的零部件BsoID字符串获取所有的零部件集合。
	 * 
	 * @param partBsoIDs
	 *            零部件BsoID字符串。
	 * @return 零部件集合。
	 * @throws QMException
	 */
	// 20080103 begin
	public List getAllPartByBsoID(String partBsoIDs) throws QMException
	// 20080103 end
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getAllPartByBsoID(String) - start"); //$NON-NLS-1$
			logger.debug("参数：partBsoIDs==" + partBsoIDs);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List bsoIDList = new ArrayList();
		List partList = new ArrayList();
		if (partBsoIDs != null && !partBsoIDs.equals("")) {
			StringTokenizer st = new StringTokenizer(partBsoIDs,
					semicolonDelimiter);
			while (st.hasMoreTokens()) {
				bsoIDList.add(st.nextToken());
			}
			for (int i = 0; i < bsoIDList.size(); i++) {
				QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo((String)bsoIDList.get(i));
				partList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getAllPartByBsoID(String) - end       " + partList); //$NON-NLS-1$
		}
		return partList;
	}

	// 20080103 begin
	/**
	 * 根据零部件唯一标识规则过滤零部件。唯一标识为：编号+大版本号+生命周期状态+路线串。
	 * 
	 * @param partBsoIDs
	 *            待过滤的零部件集合。
	 * @return 过滤后的QMPartIfc集合。
	 * @throws QMException
	 */
	private final List filterByIdentity(final Object[] partBsoIDs,HashMap hashMap)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
			logger.debug("参数：parts==" + partBsoIDs);
		}
		PersistService pservice =(PersistService)EJBServiceHelper.getService("PersistService");
		final List filterPartList = new ArrayList();
		// 存放通过唯一标识过滤后的零部件。
		final HashMap tempPartMap = new HashMap();
		for (int i = 0; i < partBsoIDs.length; i++) {
			
			QMPartIfc partIfc=null;
			if(partBsoIDs[i] instanceof String)
			{
				partIfc= (QMPartIfc)  pservice.refreshInfo((String)partBsoIDs[i]);
			}
			else if(partBsoIDs[i] instanceof BaseValueIfc)
			{
				partIfc=(QMPartIfc)pservice.refreshInfo((BaseValueIfc)partBsoIDs[i]);
			}
			String partIdentity = getObjectIdentity(partIfc,hashMap);
			// 根据零部件的唯一标识过滤零部件集合，重复的记录被过滤掉，不再拆分。
			if (!tempPartMap.containsKey(partIdentity)) {
				tempPartMap.put(partIdentity, partIfc);
				filterPartList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - end      " + filterPartList); //$NON-NLS-1$
		}
		return filterPartList;
	}

	// 20080103 end

	/**
	 * 获取业务对象的唯一标识。
	 * 
	 * @param baseValueIfc
	 *            业务对象，包括零部件和物料拆分。
	 * @return String 业务对象的唯一标识。
	 */
	private final String getObjectIdentity(final BaseValueIfc baseValueIfc,HashMap hashMap) throws QMException{
		if(logger.isDebugEnabled())
		{
			logger.debug("getObjectIdentity(BaseValueIfc) - start");
			logger.debug("\u53C2\u6570\uFF1AbaseValueIfc==" + baseValueIfc);
		}
//		System.out.println("hashMaphashMaphashMap22222222222222222222222222222222222222============"+hashMap);
		String returnString = "";
		if(baseValueIfc instanceof QMPartIfc)
		{
			QMPartIfc part1 = (QMPartIfc)baseValueIfc;
			 Vector routevec=null;
	    	 if(hashMap.get(part1.getBsoID())==null){
	    		 routevec=RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
	    	 }
			else{
//					System.out.println("hashMaphashMaphashMap3333333333333333333333============"+hashMap);
				routevec=RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, (ListRoutePartLinkInfo)hashMap.get(part1.getBsoID()));
			}
//			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);

			
			String routeAsString = "";
			String routeAllCode = "";
			String routeAssemStr = "";
			if(routevec.size() != 0)
			{
				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = (String)routevec.elementAt(1);
			}
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			returnString = part.getPartNumber() + part.getVersionID() + part.getLifeCycleState().getDisplay() + routeAllCode;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute();
//				System.out.println("materialSplitIfcmaterialSplitIfcsssssssssaaaaaaaaaaaaaaaaaaa========"+materialSplitIfc	);
			}
		if(logger.isDebugEnabled())
			logger.debug("getObjectIdentity(BaseValueIfc) - end     " + returnString);
//		System.out.println("returnStringreturnString100304========"+returnString);
		return returnString;
	}

	/**
	 * 根据是否需要拆分规则处理零部件。
	 * 
	 * 是否需要拆分处理规则：查询物料拆分表中数据。
	 * 1.比较需要拆分的零部件与物料拆分表里对应的已拆分的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的数据，则不再拆分，废弃此零部件。
	 * 2.表中数据不存在，需要拆分。 3.表中数据存在，检查版本，a版本变化，需要拆分；
	 * b版本没有变化，1状态变化，找到物料拆分表中相应数据，替换属性，不拆分； 2状态没有变化，废弃此记录。
	 * 
	 * @param filterPartList
	 *            筛选后的QMPartIfc集合。
	 */
	private final void filterBySplitRule(final List filterPartList,HashMap hashmap)
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("参数：filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // 不需拆分的零部件集合。
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
//	   System.out.println("cacacalalalalalala==="+partIfc.getPartNumber());
	   List mSplitList = new ArrayList();
	   try {
	    // 根据零件号获取已拆分的物料。
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber());
	   } catch (QMException e) 
	   {
		   
	    Object[] aobj = new Object[] { partIfc.getPartNumber() };
	    // "查找编号为*的零部件对应的物料时出错！"
	    logger.error(Messages.getString("Util.51", aobj) + e);
	    throw new ERPException(e, RESOURCE, "Util.51", aobj);
	   }
	   // 相同唯一标识的物料只保留一个。
	   HashMap maHashMap = new HashMap(5);
//	   System.out.println("100304++++++++mSplitList.size()"+mSplitList.size()+"============pppp"+mSplitList);
	   for (int k = 0; k < mSplitList.size(); k++) 
	   {
	    MaterialSplitIfc ma = (MaterialSplitIfc) mSplitList.get(k);
	    String objectIdectity = getObjectIdentity(ma,hashmap);
//	    System.out.println("100304++++++++maHashMamaHashMap"+maHashMap);
//	    System.out.println("100304++++++++objectIdectityobjectIdectity"+objectIdectity);
	    if (!maHashMap.containsKey(objectIdectity)) 
	    {
//	    	System.out.println("100304++++++++fffffffffffffffffffffffffffffffffffffffffffffffffffffffp");
	     maHashMap.put(objectIdectity, ma);
	     
	    }
	   }
	   Object[] materialSplitIfcs = maHashMap.values().toArray();
	   // 是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。");
	    }
	    System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
	    	 System.out.println("100304++++++++partIfc.getVersionID()-------------"+partIfc.getVersionID());
	    	 System.out.println("100304++++++++ materialSplitIfc.getPartVersion()-------------"+ materialSplitIfc.getPartVersion());
	      if (partIfc.getVersionID().compareTo(
	        materialSplitIfc.getPartVersion()) < 0) 
	      {
	    	  
	       removePartList.add(partIfc);
	       System.out.println("100304++++++++removePartListremovePartList-------------"+removePartList);
	       break;
	      } else 
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
//	        System.out.println("100304++++++++11111111111111-------------"+materialSplitIfcs.length+"ccccccccc"+materialSplitIfc);
	       } else if (tempMaterialSplit.getPartVersion()
	         .compareTo(
	           materialSplitIfc.getPartVersion()) < 0) 
	       {
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length+"ddddddddddd"+materialSplitIfc);
	        tempMaterialSplit = materialSplitIfc;
	       }
	      }
	     }
	    }
	   }
	   if (logger.isDebugEnabled()) 
	   {
	    logger.debug("partIfc.getPartNumber()=="
	      + partIfc.getPartNumber());
	   }
	   // 是否需要拆分处理规则2.表中数据不存在，需要拆分。
	   if (tempMaterialSplit == null) 
	   {
//		   System.out.println("100304++++++++33333333333333333-------------"+materialSplitIfcs.length);
	    if (logger.isDebugEnabled()) 
	    {
	     logger
	     .debug("---------是否需要拆分处理规则2.表中数据不存在，需要拆分。或版本是旧的，不需拆分。");
	    }
	    
	   }
	   // 是否需要拆分处理规则3。表中数据存在，检查版本。
	   else 
	   {
	    if (logger.isDebugEnabled()) 
	    {
	     logger.debug("---------是否需要拆分处理规则3。表中数据存在，检查版本。");
	    }
	    // 是否需要拆分处理规则3.b。版本没有变化。
	    if (partIfc.getVersionID().equals(
	      tempMaterialSplit.getPartVersion())) 
	    {
	     if (logger.isDebugEnabled()) 
	     {
	      logger.debug("---------是否需要拆分处理规则3.b。版本没有变化。");
	     }
	     System.out.println("100304++++++++partIfc.getLifeCycleState().getDisplay()-------------"+partIfc.getLifeCycleState().getDisplay());
	     System.out.println("100304++++++++tempMaterialSplit.getState()-------------"+tempMaterialSplit.getState());
	     // 是否需要拆分处理规则3.b.2。状态没有变化，废弃此记录。
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {
	    	 Vector routevec=null;
//	    	 System.out.println("ssssssssssssssssssssssssssssssss00000000000======="+hashmap.get(partIfc.getBsoID()));
//	    	 System.out.println("ssssssssssssssssssssssssssssssss======="+(ListRoutePartLinkInfo)hashmap.get(partIfc.getBsoID()));
	    	 
	    	 if(hashmap.get(partIfc.getBsoID())==null){
	    		 routevec=RequestHelper.getRouteBranchs(partIfc, null);
	    	 }
				else{
					routevec=RequestHelper.getRouteBranchs(partIfc, (ListRoutePartLinkInfo)hashmap.get(partIfc.getBsoID()));
				}
//	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
//	      System.out.println("081000000000000000000000==="+routevec);
	      
	      String routeAsString = "";
	      String routeAllCode = "";
	      String routeAssemStr = "";
	      //CCBegin SS3
	      String cgbs="";
	      if(routevec.size() != 0)
	      {
	       routeAsString = (String)routevec.elementAt(0);
	       routeAllCode = (String)routevec.elementAt(1);
	       cgbs=(String)routevec.elementAt(7);
	       //CCEnd SS2
	      }
	      boolean flag1=false;
	      boolean flag2=false;
	      if(routeAllCode==null || routeAllCode=="")
	      {
	       flag1=true;
	      }
	      if(tempMaterialSplit.getRoute()==null || tempMaterialSplit.getRoute()=="")
	      {
	       flag2=true;
	      }
//	      System.out.println("100304++++++++flag1flag1flag1flag1flag1-------------"+flag1);
//	      System.out.println("100304++++++++flag2flag2flag2flag2flag2-------------"+flag2);
	      if(flag1&&flag2)
	      {
//	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
			System.out.println("1034=============="+cgbs);

	      if (cgbs != null&&cgbs != "") {
				if (cgbs.equals("X")) {
					routeAsString = "毛坯-" + routeAsString;
					routeAllCode = "毛坯-" + routeAllCode;
				} else if (cgbs.equals("X-1")) {
					routeAsString = "半成品-" + routeAsString;
					routeAllCode = "半成品-" + routeAllCode;
  
				}
			}
	      if(!flag1 && !flag2)
	      {
	    	  System.out.println("100304++++++++routeAllCode-------------"+routeAllCode+ "   tempMaterialSplit.getRoute()"+tempMaterialSplit.getRoute()+"ddddddddddddd"+materialSplitIfc);
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {
	    	   System.out.println("1");
	        removePartList.add(partIfc);
//	        System.out.println("100304++++++++flag222222222222222222222222222222222222-------------"+removePartList);
	       } else
	       {
	    	   System.out.println("2");
//	    	   System.out.println("100304++++++++mSplitList.size()mSplitList.size()-------------"+mSplitList.size());
	        for(int k = 0; k < mSplitList.size(); k++)
	        {
	         MaterialSplitIfc mSplitIfc = (MaterialSplitIfc)mSplitList.get(k);
//	         System.out.println("100304++++++++flag333333333333333333333-------------"+mSplitIfc.getRoute());
//	         System.out.println("100304++++++++flag9999999-------------"+tempMaterialSplit.getRoute());
//	         System.out.println("100304++++++++flaghhhhhhhhhhhhhhhhhh-------------"+mSplitIfc.getState());
//	         System.out.println("100304++++++++oooooooooooooooiiiiiii-------------"+routeAllCode);
//	         System.out.println("0610 ++++++++zzzzzzzz-------------"+mSplitIfc.getRoute().equals(routeAllCode));
	         //CCBegin by chudaming 20090304
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()) && mSplitIfc.getRoute().equals(routeAllCode))
	        	 //CCEnd by chudaming 20090304
	         {
	        	 System.out.println("3");
//	        	 System.out.println("100304++++++++flag444444444444444444444-------------");
		          mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
		          setMaterialSplit(partIfc, mSplitIfc);
		          mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
		          removePartList.add(partIfc);
//		          System.out.println("100304++++++++flag5555555555555-------------"+removePartList);
	         }
	        }
	 
	       }
	      } 
	      
	     }
	     // 是否需要拆分处理规则3.a版本变化，需要拆分；
	     else 
	     {
	      if (logger.isDebugEnabled()) 
	      {
	       logger.debug("---------是否需要拆分处理规则3.a版本变化，需要拆分；");
	      }
	     }
	    }
	   }
	   // 将未改变的零部件和只改变状态的零部件从拆分列表中除去。
//	   System.out.println("100304++++++++flag66666666666666-------------"+removePartList);
	   //CCBegin by chudaming 20100609  dele.循环里remove的，实际上就删除了filterPartList奇数位的数据
	   //filterPartList.removeAll(removePartList);
//	   System.out.println("100304++++++++flag7777777777777777777-------------"+removePartList);
	   if (logger.isDebugEnabled()) {
	    logger.debug("filterBySplitRule(List) - end"); //$NON-NLS-1$
	   }
	  }
	  //CCBegin by chudaming 20100609 循环里remove的，实际上就删除了filterPartList奇数位的数据
	  filterPartList.removeAll(removePartList);
	  //CCEnd by chudaming 20100609
	 } 
	 
		/**
		 * 根据零件号获取物料。为瘦客户显示使用。
		 * 
		 * @param partNumberList
		 *            零件号集合。
		 * @return 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
		 * @throws QMException
		 */
		public HashMap getAllMaterial(List partNumberList) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMaterial(List) - start" + partNumberList); //$NON-NLS-1$
				logger.debug("参数：partNumberList==" + partNumberList);
			}
			// 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
			HashMap materialMap = new HashMap();
			for (int i = 0; i < partNumberList.size(); i++) {
				// 获取该零部件分解后的物料集合。
				List resultList = getResultMSplitList((String) partNumberList
						.get(i));
				// 物料信息数组集合，该集合中的每个元素都是数组。
				List mSplitList = new ArrayList();
				// 存放一组物料共有信息的数组。现在为：零件名、转换标识、零件号、零件大版本号、工艺路线、生命周期状态、零件号集合的字符形式。
				String[] commonField = null;
				HashMap partNumberMap = new HashMap();
				for (int j = 0; j < resultList.size(); j++) {
					// 物料、供显示用的物料层级、父物料号。
					List mSplitAndLevelList = (List) resultList.get(j);
					MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) mSplitAndLevelList
					.get(0);
					if (mSplitIfc != null) {
						partNumberMap.put(mSplitIfc.getPartNumber(), mSplitIfc
								.getPartNumber());
						if (commonField == null) {
							commonField = new String[] { mSplitIfc.getPartName(),
									String.valueOf(mSplitIfc.getSplited()),
									mSplitIfc.getPartNumber(),
									mSplitIfc.getPartVersion(),
									mSplitIfc.getRoute(), mSplitIfc.getState(), "" };
						}
						if (j == resultList.size() - 1) {
							String partNumberString = "";
							Iterator iter = partNumberMap.values().iterator();
							while (iter.hasNext()) {
								if (partNumberString.equals("")) {
									partNumberString = partNumberString
									+ iter.next();
								} else {
									partNumberString = partNumberString
									+ semicolonDelimiter + iter.next();
								}
							}
							commonField[6] = partNumberString;
						}
						String parentNumber = null;
						if (mSplitAndLevelList.size() == 3) {
							parentNumber = (String) mSplitAndLevelList.get(2);
							// 物料信息。现在为：物料BsoID、显示层级、物料号、层级状态、工艺路线代码、父物料号。
						}
						String[] mStrings = new String[] { mSplitIfc.getBsoID(),
								(String) mSplitAndLevelList.get(1),
								mSplitIfc.getMaterialNumber(),
								String.valueOf(mSplitIfc.getStatus()),
								mSplitIfc.getRouteCode(), parentNumber };
						mSplitList.add(mStrings);
					}
				}
				if (commonField != null) {
					materialMap.put(commonField, mSplitList);
					for (int m = 0; m < commonField.length; m++) {
						logger.debug(commonField[m]);
					}
				}
				for (int j = 0; j < mSplitList.size(); j++) {
					String[] m = (String[]) mSplitList.get(j);
					for (int n = 0; n < m.length; n++) {
						logger.debug(m[n]);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMaterial(List) - end        " + materialMap); //$NON-NLS-1$
			}
			return materialMap;
		}

		/**
		 * 获取该零件分解后的物料集合。
		 * 
		 * @param String
		 *            零件号。
		 * @return List 该零部件的物料集合。
		 * @throws QMException
		 */
		private final List getResultMSplitList(String partNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getResultMSplitList(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			// 物料结果集合。
			List resultMSplitList = new ArrayList();
			// 所有顶级物料集合。
			List rootMSplitList = getRootMSplit(partNumber);
			for (int i = 0; i < rootMSplitList.size(); i++) {
				MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) rootMSplitList
				.get(i);
				List mSplitAndLevelList = new ArrayList();
				mSplitAndLevelList.add(mSplitIfc);
				mSplitAndLevelList.add("0");
				resultMSplitList.add(mSplitAndLevelList);
				// 得到物料的子物料。只在层级状态为1的情况下才执行。
				if (mSplitIfc.getStatus() == 1) {
					getSubResultMSplitList(mSplitIfc, resultMSplitList, 0);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getResultMSplitList(String) - end      " + resultMSplitList); //$NON-NLS-1$
			}
			return resultMSplitList;
		}

		/**
		 * 根据零件号获取顶级物料。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 顶级物料集合。
		 * @throws QMException
		 */
		public List getRootMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query2 = new QMQuery("MaterialSplit");
			QueryCondition condition2 = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query2.addCondition(condition2);
			query2.addAND();
			QueryCondition condition3 = new QueryCondition("rootFlag", true);
			query2.addCondition(condition3);
			List returnList=(List)pservice.findValueInfo(query2);
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - end       " + returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * 获取子物料结果集合。
		 * 
		 * @param mSplitIfc
		 *            父物料。
		 * @param resultMSplitList
		 *            最终的结果集合。
		 * @throws QMException
		 */
		private void getSubResultMSplitList(MaterialSplitIfc mSplitIfc,
				List resultMSplitList, int level) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List, int) - start"); //$NON-NLS-1$
				logger.debug("参数：mSplitIfc==" + mSplitIfc);
				logger.debug("参数：resultMSplitList==" + resultMSplitList);
				logger.debug("参数：level==" + level);
			}
			// 获取指定父物料号的物料结构集合。
			List mStructureList = getMStructure(mSplitIfc.getPartNumber(),
					mSplitIfc.getMaterialNumber());
			Iterator iter = mStructureList.iterator();
			while (iter.hasNext()) {
				MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) iter
				.next();
				// 根据物料号获取物料。
				MaterialSplitIfc subMSplitIfc = getMSplit(mStructureIfc
						.getChildNumber());
				// 记录物料、供显示用的物料层级，物料的父物料号。
				List mSplitAndLevelList = new ArrayList();
				mSplitAndLevelList.add(subMSplitIfc);
				mSplitAndLevelList.add(String.valueOf(level + 1));
				mSplitAndLevelList.add(mStructureIfc.getParentNumber());
				resultMSplitList.add(mSplitAndLevelList);
				if (subMSplitIfc != null && subMSplitIfc.getStatus() == 1) {
					// 递归获取子物料，放入filterMSplitList中。
					getSubResultMSplitList(subMSplitIfc, resultMSplitList,
							level + 1);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List, int) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 根据零件号获取已转换的物料。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 物料集合。
		 * @throws QMException
		 */
//		private List getSplitedMSplit(String partNumber) throws QMException {
//			if (logger.isDebugEnabled()) {
//				logger.debug("getSplitedMSplit(String) - start"); //$NON-NLS-1$
//				logger.debug("参数：partNumber==" + partNumber);
//			}
//			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
////			int aa=1;
//			final QMQuery query = new QMQuery("MaterialSplit");
//			QueryCondition condition = new QueryCondition("partNumber",
//					QueryCondition.EQUAL, partNumber);
//			query.addCondition(condition);
//			//CCBegin by chudaming 20100610 dele 新发布车型所形成XML文件的零部件表中既存在‘N’标识，也存在‘U’标识，结构表中既存在‘N’标识，也存在‘O’标识
//			//CCBegin by chudaming 20101216
////			query.addAND();
////			QueryCondition condition1 = new QueryCondition("rootFlag",
////					true);
////			query.addCondition(condition1);
//			//CCEnd by chudaming 20101216
////			//??splited感觉路线为空则为false
////			QueryCondition condition4 = new QueryCondition("splited", true);
////			query.addCondition(condition4);
//			//CCEnd by chudaming 20100610 dele
//			List mSplitList=(List)pservice.findValueInfo(query);
//			if (logger.isDebugEnabled()) {
//				logger.debug("getSplitedMSplit(String) - end"); //$NON-NLS-1$
//			}
////			System.out.println("wwwwwwwwwwwwwwwwsssssssssssssssssssssssssssssssssmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy================="+mSplitList);
//			return mSplitList;
//		}
		private List getSplitedMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
//			int aa=1;
			final QMQuery query = new QMQuery("MaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			//CCBegin by chudaming 20100610 dele 新发布车型所形成XML文件的零部件表中既存在‘N’标识，也存在‘U’标识，结构表中既存在‘N’标识，也存在‘O’标识
			//CCBegin by chudaming 20101216
			query.addAND();
			QueryCondition condition1 = new QueryCondition("rootFlag",
					true);
			query.addCondition(condition1);
			//CCEnd by chudaming 20101216
//			//??splited感觉路线为空则为false
//			QueryCondition condition4 = new QueryCondition("splited", true);
//			query.addCondition(condition4);
			//CCEnd by chudaming 20100610 dele
			List mSplitList=(List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - end"); //$NON-NLS-1$
			}
//			System.out.println("wwwwwwwwwwwwwwwwsssssssssssssssssssssssssssssssssmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy================="+mSplitList);
			return mSplitList;
		}

		/**
		 * 根据零件号获取物料拆分表里的记录。包括已转换的和未转换的。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 物料集合。
		 * @throws QMException
		 */
		private List getAllMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("MaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			List mSplitList=(List)pservice.findValueInfo(query);
			//System.out.println("mSplitListmSplitListmSplitListmSplitList======="+mSplitList);
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMSplit(String) - end        " + mSplitList); //$NON-NLS-1$
			}
			return mSplitList;
		}

		/**
		 * 根据物料号获取物料。
		 * 
		 * @param materialNumber
		 *            物料号。
		 * @return 物料。
		 * @throws QMException
		 */
		public MaterialSplitIfc getMSplit(String materialNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：materialNumber==" + materialNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("MaterialSplit");
			QueryCondition condition = new QueryCondition("materialNumber",
					QueryCondition.EQUAL, materialNumber);
			query.addCondition(condition);
			List mSplitList=(List)pservice.findValueInfo(query);
			MaterialSplitIfc mSplitIfc = null;
			if (mSplitList != null && mSplitList.size() > 0) {
				mSplitIfc = (MaterialSplitIfc) mSplitList.get(0);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplit(String) - end       " + mSplitList); //$NON-NLS-1$
			}
			return mSplitIfc;
		}

		/**
		 * 获取指定父件号的物料结构集合。
		 * 
		 * @param String
		 *            父件号。
		 * @return List 指定父件号的物料结构集合。
		 * @throws QMException
		 */
		private final List getMStructure(String parentPartNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructureByNum(String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			QMQuery query = new QMQuery("MaterialStructure");
			QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
					parentPartNumber);
			query.addCondition(condition1);
			List mStructureList=(List)pservice.findValueInfo(query);
//			System.out.println("mStructureListmStructureListmStructureListmStructureList========"+mStructureList);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getMStructureByNum(String) - end      " + mStructureList); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * 根据指定的父件号和父物料号条件，找到结构表中所有符合条件的结构信息。
		 * 
		 * @param parentPartNumber
		 *            父件号。
		 * @param parentNumber
		 *            父物料号。
		 * @return 结构信息。
		 * @throws QMException
		 */
		public List getMStructure(String parentPartNumber, String parentNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
				logger.debug("参数：parentNumber==" + parentNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("MaterialStructure");
			QueryCondition condition = new QueryCondition("parentPartNumber",
					QueryCondition.EQUAL, parentPartNumber);
			query.addCondition(condition);
			query.addAND();
			QueryCondition condition3 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition3);
			List mStructureList=(List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String) - end"); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * 根据指定的父件号和父物料号和子物料号条件，找到结构表中所有符合条件的结构信息。
		 * 
		 * @param parentPartNumber
		 *            父件号。
		 * @param parentNumber
		 *            父物料号。
		 * @param childNumber
		 *            子物料号。
		 * @return 结构信息。
		 * @throws QMException
		 */
		private List getMStructure(String parentPartNumber, String parentNumber,
				String childNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String, String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
				logger.debug("参数：parentNumber==" + parentNumber);
				logger.debug("参数：childNumber==" + childNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("MaterialStructure");
			QueryCondition condition = new QueryCondition("parentPartNumber",
					QueryCondition.EQUAL, parentPartNumber);
			query.addCondition(condition);
			query.addAND();
			QueryCondition condition2 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition2);
			query.addAND();
			QueryCondition condition3 = new QueryCondition("childNumber",
					QueryCondition.EQUAL, childNumber);
			query.addCondition(condition3);
			List mStructureList = (List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String, String) - end"); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * 根据指定的子物料号条件，找到结构表中所有符合条件的结构信息。
		 * 
		 * @param childNumber
		 *            子物料号。
		 * @return 结构信息。
		 * @throws QMException
		 */
		private List getMStructureByChild(String childNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructureByChild(String) - start"); //$NON-NLS-1$
				logger.debug("参数：childNumber==" + childNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("MaterialStructure");
			QueryCondition condition3 = new QueryCondition("childNumber",
					QueryCondition.EQUAL, childNumber);
			query.addCondition(condition3);
			List mStructureList = (List) pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructureByChild(String) - end"); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * 正式执行更新。更新当前用户的临时表中的信息。完成更新后清空临时表中属于该用户的所有记录。
		 * 
		 * @param updateMap
		 *            键：临时表bsoID。值：修改的物料号。
		 * @throws QMException
		 */
		public void updateMaterial(HashMap updateMap) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("updateMaterial(HashMap) - start"); //$NON-NLS-1$
				logger.debug("参数：updateMap==" + updateMap);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			Object[] bsoIDs = updateMap.keySet().toArray();
			for (int i = 0; i < bsoIDs.length; i++) {
				String bsoID = (String) bsoIDs[i];
				// 临时物料。
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc)pservice.refreshInfo(bsoID);
				// 原物料号。
				String oldMaterialNumber = imSplitIfc.getMaterialNumber();
				// 设置修改的物料号。
				imSplitIfc.setMaterialNumber((String) updateMap.get(bsoID));
				imSplitIfc.setUpdateFlag("U");
				pservice.saveValueInfo(imSplitIfc);
				List imStructureList = getInterimMStructure(imSplitIfc
						.getPartNumber(), false);
				for (int m = 0; m < imStructureList.size(); m++) {
					InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) imStructureList
					.get(m);
					if (imStructureIfc.getParentNumber().equals(oldMaterialNumber)) {
						imStructureIfc.setParentNumber(imSplitIfc
								.getMaterialNumber());
						imStructureIfc.setUpdateFlag("U");
						pservice.saveValueInfo(imStructureIfc);
					}
					if (imStructureIfc.getChildNumber().equals(oldMaterialNumber)) {
						imStructureIfc.setChildNumber(imSplitIfc
								.getMaterialNumber());
						imStructureIfc.setUpdateFlag("U");
						pservice.saveBso(imStructureIfc);
					}
				}
			}
			List imSplitList = getAllInterimMSplit();
			for (int i = 0; i < imSplitList.size(); i++) {
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) imSplitList
				.get(i);
				if (imSplitIfc.getUpdateFlag() != null
						&& imSplitIfc.getUpdateFlag().equals("U")) {
					MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) pservice.refreshInfo(imSplitIfc.getSourceBsoID());
					mSplitIfc.setMaterialNumber(imSplitIfc.getMaterialNumber());
					mSplitIfc.setStatus(imSplitIfc.getStatus());
					pservice.saveValueInfo(mSplitIfc);
				}
			}
			List imStructureList = getAllInterimMStructure();
			for (int i = 0; i < imStructureList.size(); i++) {
				InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(i);
				if (imStructureIfc.getUpdateFlag() != null) {
					if (imStructureIfc.getUpdateFlag().equals("D")) {
						pservice.deleteBso(imStructureIfc.getSourceBsoID());
					} else if (imStructureIfc.getUpdateFlag().equals("U")) {
						MaterialStructureIfc mStructureIfc = (MaterialStructureIfc)pservice.refreshInfo(imStructureIfc.getSourceBsoID());
						mStructureIfc.setParentNumber(imStructureIfc
								.getParentNumber());
						mStructureIfc.setChildNumber(imStructureIfc
								.getChildNumber());
						mStructureIfc.setLevelNumber(imStructureIfc
								.getLevelNumber());
						pservice.saveValueInfo(mStructureIfc);
					}
				}
			}
			deleteAllInterimData();
			if (logger.isDebugEnabled()) {
				logger.debug("updateMaterial(HashMap) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 删除所有临时表中当前用户的数据。
		 * 
		 * @throws QMException
		 */
		public void deleteAllInterimData() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("deleteAllInterimData() - start"); //$NON-NLS-1$
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			List imSplitList = getAllInterimMSplit();
			for (int i = 0; i < imSplitList.size(); i++) {
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) imSplitList
				.get(i);
				pservice.deleteValueInfo(imSplitIfc);
			}
			List imStructureList = getAllInterimMStructure();
			for (int j = 0; j < imStructureList.size(); j++) {
				InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(j);
				pservice.deleteValueInfo(imStructureIfc);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("deleteAllInterimData() - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 只删结构关系。将临时物料结构更新标识设置为“D”。
		 * 
		 * @param parentPartNumber
		 *            父件号。
		 * @param parentNumber
		 *            父物料号。
		 * @param childBsoID
		 *            被删除临时子物料的bsoID。
		 * @throws QMException
		 */
		public void delete(String parentPartNumber, String parentNumber,
				String childBsoID) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("delete(String, String, String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
				logger.debug("参数：parentNumber==" + parentNumber);
				logger.debug("参数：childBsoID==" + childBsoID);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			logger.debug("parentPartNumber==" + parentPartNumber);
			logger.debug("parentNumber==" + parentNumber);
			logger.debug("childBsoID==" + childBsoID);
			InterimMaterialSplitIfc childIMSplitIfc = (InterimMaterialSplitIfc)pservice.refreshInfo(childBsoID);
			List iMStructureList = getInterimMStructure(parentPartNumber,
					parentNumber, childIMSplitIfc.getMaterialNumber());
			for (int i = 0; i < iMStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iMStructureList
				.get(i);
				logger.debug("将临时物料结构更新标识设置为“D”。");
				iMStructureIfc.setUpdateFlag("D");
				pservice.saveValueInfo(iMStructureIfc);
			}
			if (iMStructureList != null && iMStructureList.size() > 0) {

				// 删除中间层时，下层层级依次上升，层级状态不变。
				if (childIMSplitIfc.getStatus() == 1) {
					logger.debug("删除中间层时，下层层级依次上升，层级状态不变。");
					setAllIMStructure(childIMSplitIfc, parentNumber);
				}
				// 删除最底层时，上层层级不变，层级状态变：同被删除的层级状态。
				else {
					logger.debug("删除最底层时，上层层级不变，层级状态变：同被删除的层级状态。");
					InterimMaterialSplitIfc parentIMSplitIfc = getInterimMSplit(parentNumber);
					iMStructureList = getInterimMStructure(parentPartNumber,
							parentNumber);
					// 如果父级物料上挂接多于一个子物料。上层层级状态不变。
					if (iMStructureList != null && iMStructureList.size() > 0) {
						logger.debug("上层层级状态不变。删除该结构记录。");
						// 父级物料上挂接多于一个子物料。上层层级状态不变。
					} else {
						logger.debug("上层层级状态同被删除的层级状态。");
						// 上层层级状态变：同被删除的层级状态。
						parentIMSplitIfc.setStatus(childIMSplitIfc.getStatus());
						parentIMSplitIfc.setUpdateFlag("U");
					}
					pservice.saveValueInfo(parentIMSplitIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("delete(String, String, String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 设置给定临时父物料的结构信息。将临时物料结构更新标识设置为“U”。
		 * 
		 * @param iMSplitIfc
		 *            临时父物料。
		 * @param parentNumber
		 *            临时父物料的父物料号。
		 * @throws QMException
		 */
		private void setAllIMStructure(InterimMaterialSplitIfc iMSplitIfc,
				String parentNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("setAllIMStructure(InterimMaterialSplitIfc, String) - start"); //$NON-NLS-1$
				logger.debug("参数：iMSplitIfc==" + iMSplitIfc);
				logger.debug("参数：parentNumber==" + parentNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			// 获取指定父物料号的物料结构集合。
			List mStructureList = getInterimMStructure(iMSplitIfc.getPartNumber(),
					iMSplitIfc.getMaterialNumber());
			Iterator iter = mStructureList.iterator();
			while (iter.hasNext()) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iter
				.next();
				// 层级。
				InterimMaterialSplitIfc subIMSplitIfc = getInterimMSplit(iMStructureIfc
						.getChildNumber());
				int subLevel = Integer.valueOf(iMStructureIfc.getLevelNumber())
				.intValue();
				iMStructureIfc.setParentNumber(parentNumber);
				iMStructureIfc.setLevelNumber(String.valueOf(subLevel - 1));
				iMStructureIfc.setUpdateFlag("U");
				pservice.saveValueInfo(iMStructureIfc);
				// 根据物料号获取物料。
				if (subIMSplitIfc != null && subIMSplitIfc.getStatus() == 1) {
					// 设置给定临时父物料的结构信息。
					setAllSubIMStructure(subIMSplitIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("setAllIMStructure(InterimMaterialSplitIfc, String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 设置给定临时父物料的结构信息。将临时物料结构更新标识设置为“U”。
		 * 
		 * @param iMSplitIfc
		 *            临时父物料。
		 * @throws QMException
		 */
		private void setAllSubIMStructure(InterimMaterialSplitIfc iMSplitIfc)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("setAllSubIMStructure(InterimMaterialSplitIfc) - start"); //$NON-NLS-1$
				logger.debug("参数：iMSplitIfc==" + iMSplitIfc);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			// 获取指定父物料号的物料结构集合。
			List iMStructureList = getInterimMStructure(iMSplitIfc.getPartNumber(),
					iMSplitIfc.getMaterialNumber());
			Iterator iter = iMStructureList.iterator();
			while (iter.hasNext()) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iter
				.next();
				// 层级。
				InterimMaterialSplitIfc subIMSplitIfc = getInterimMSplit(iMStructureIfc
						.getChildNumber());
				int subLevel = Integer.valueOf(iMStructureIfc.getLevelNumber())
				.intValue();
				iMStructureIfc.setLevelNumber(String.valueOf(subLevel - 1));
				iMStructureIfc.setUpdateFlag("U");
				pservice.saveValueInfo(iMStructureIfc);
				// 根据物料号获取物料。
				if (subIMSplitIfc != null && subIMSplitIfc.getStatus() == 1) {
					// 递归设置给定临时父物料的结构信息。
					setAllSubIMStructure(subIMSplitIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("setAllSubIMStructure(InterimMaterialSplitIfc) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 只改结构关系中的子物料号。层级、层级状态都不变。将临时物料结构更新标识设置为“U”。需要将替换的新物料的信息添加到临时表中。
		 * 
		 * @param parentPartNumber
		 *            父件号。
		 * @param parentNumber
		 *            父物料号。
		 * @param childBsoID
		 *            临时表子物料bsoID。
		 * @param replaceBsoID
		 *            替换物料的bsoID。
		 * @throws QMException
		 */
		public void replace(String parentPartNumber, String parentNumber,
				String childBsoID, String replaceBsoID) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("replace(String, String, String, String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
				logger.debug("参数：parentNumber==" + parentNumber);
				logger.debug("参数：childBsoID==" + childBsoID);
				logger.debug("参数：replaceBsoID==" + replaceBsoID);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			InterimMaterialSplitIfc childIMSplitIfc = (InterimMaterialSplitIfc) pservice.refreshInfo(childBsoID);
			MaterialSplitIfc replaceIMSplitIfc = (MaterialSplitIfc) pservice.refreshInfo(replaceBsoID);
			List iMStructureList = getInterimMStructure(parentPartNumber,
					parentNumber, childIMSplitIfc.getMaterialNumber());
			for (int i = 0; i < iMStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iMStructureList
				.get(i);
				iMStructureIfc
				.setChildNumber(replaceIMSplitIfc.getMaterialNumber());
				iMStructureIfc.setUpdateFlag("U");
				pservice.saveValueInfo(iMStructureIfc);
			}
			if (iMStructureList != null && iMStructureList.size() > 0) {
				// 将替换的新物料的信息添加到临时表中。
				createInterimMaterial(replaceIMSplitIfc.getPartNumber());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("replace(String, String, String, String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 根据零件号集合的字符串形式创建临时物料记录。
		 * 
		 * @param partNumberString
		 *            零件号集合的字符串形式。p01;p02
		 * @throws QMException
		 */
		public void createInterimMaterial(String partNumberString)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("createInterimMaterial(String) - start" + partNumberString); //$NON-NLS-1$
				logger.debug("参数：partNumberString==" + partNumberString);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			StringTokenizer st = new StringTokenizer(partNumberString,
					semicolonDelimiter);
			// 零件号集合。如：p01,p02
			List partNumberList = new ArrayList();
			while (st.hasMoreTokens()) {
				partNumberList.add(st.nextToken());
			}
			logger.debug("partNumberList==" + partNumberList);
			for (int i = 0; i < partNumberList.size(); i++) {
				List imSplitList = getAllInterimMSplit((String) partNumberList
						.get(i));
				UserIfc owner=(UserIfc)sservice.getCurUserInfo();
				if (imSplitList == null || imSplitList.isEmpty()) {
					List mSplitList = getAllMSplit((String) partNumberList.get(i));
					for (Iterator ite = mSplitList.iterator(); ite.hasNext();) {
						Object obj = ite.next();
						MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) obj;
						InterimMaterialSplitIfc imSplitIfc = new InterimMaterialSplitInfo();
						imSplitIfc.setColorFlag(mSplitIfc.getColorFlag());
						imSplitIfc.setDefaultUnit(mSplitIfc.getDefaultUnit());
						imSplitIfc.setMaterialNumber(mSplitIfc.getMaterialNumber());
						imSplitIfc.setOptionCode(mSplitIfc.getOptionCode());
						imSplitIfc.setPartModifyTime(mSplitIfc.getPartModifyTime());
						imSplitIfc.setPartName(mSplitIfc.getPartName());
						imSplitIfc.setPartNumber(mSplitIfc.getPartNumber());
						imSplitIfc.setPartType(mSplitIfc.getPartType());
						imSplitIfc.setPartVersion(mSplitIfc.getPartVersion());
						imSplitIfc.setProducedBy(mSplitIfc.getProducedBy());
						imSplitIfc.setRoute(mSplitIfc.getRoute());
						imSplitIfc.setRouteCode(mSplitIfc.getRouteCode());
						imSplitIfc.setSplited(mSplitIfc.getSplited());
						imSplitIfc.setState(mSplitIfc.getState());
						imSplitIfc.setStatus(mSplitIfc.getStatus());
						imSplitIfc.setSourceBsoID(mSplitIfc.getBsoID());
						imSplitIfc.setOwner(owner.getBsoID());
						imSplitIfc.setRootFlag(mSplitIfc.getRootFlag());
						pservice.saveValueInfo(imSplitIfc);
					}
				}
				List imStructureList = getInterimMStructure((String) partNumberList
						.get(i), true);
				if (imStructureList == null || imStructureList.isEmpty()) {
					List mStructureList = getMStructure((String) partNumberList
							.get(i));
					for (Iterator iter = mStructureList.iterator(); iter.hasNext();) {
						MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) iter
						.next();
						InterimMaterialStructureIfc imStructureIfc = new InterimMaterialStructureInfo();
						imStructureIfc.setChildNumber(mStructureIfc
								.getChildNumber());
						imStructureIfc.setDefaultUnit(mStructureIfc
								.getDefaultUnit());
						imStructureIfc.setLevelNumber(mStructureIfc
								.getLevelNumber());
						imStructureIfc.setOptionFlag(mStructureIfc.getOptionFlag());
						imStructureIfc.setParentNumber(mStructureIfc
								.getParentNumber());
						imStructureIfc.setParentPartNumber(mStructureIfc
								.getParentPartNumber());
						imStructureIfc.setParentPartVersion(mStructureIfc
								.getParentPartVersion());
						imStructureIfc.setQuantity(mStructureIfc.getQuantity());
						imStructureIfc.setSourceBsoID(mStructureIfc.getBsoID());
						imStructureIfc.setOwner(owner.getBsoID());
						pservice.saveValueInfo(imStructureIfc);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("createInterimMaterial(String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 根据零件号获取临时物料。为瘦客户更新物料时使用。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 临时物料Map。键：一组临时物料共有信息数组；值：临时物料信息数组集合。
		 * @throws QMException
		 */
		public HashMap getAllInterimMaterial(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMaterial(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			// 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
			HashMap iMaterialMap = new HashMap();
			// 获取该零部件分解后的物料集合。
			List resultList = getResultIMSplitList(partNumber);
			// 物料信息数组集合，该集合中的每个元素都是数组。
			List imSplitList = new ArrayList();
			// 存放一组物料共有信息的数组。现在为：零件名、转换标识、零件号、零件大版本号、工艺路线、生命周期状态。
			String[] commonField = null;
			for (int j = 0; j < resultList.size(); j++) {
				// 记录物料、供显示用的物料层级，物料的父物料号。
				List imSplitAndLevelList = (List) resultList.get(j);
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) imSplitAndLevelList
				.get(0);
				if (imSplitIfc != null) {
					if (commonField == null) {
						commonField = new String[] { imSplitIfc.getPartName(),
								String.valueOf(imSplitIfc.getSplited()),
								imSplitIfc.getPartNumber(),
								imSplitIfc.getPartVersion(), imSplitIfc.getRoute(),
								imSplitIfc.getState() };
					}
					String parentNumber = null;
					if (imSplitAndLevelList.size() == 3) {
						parentNumber = (String) imSplitAndLevelList.get(2);
					}
					logger.debug("mSplitIfc==" + imSplitIfc);
					// 物料信息。现在为：物料BsoID、显示层级、物料号、层级状态、工艺路线代码、父物料号。
					String[] mStrings = new String[] { imSplitIfc.getBsoID(),
							(String) imSplitAndLevelList.get(1),
							imSplitIfc.getMaterialNumber(),
							String.valueOf(imSplitIfc.getStatus()),
							imSplitIfc.getRouteCode(), parentNumber };
					imSplitList.add(mStrings);
				}
			}
			if (commonField != null) {
				iMaterialMap.put(commonField, imSplitList);
				for (int m = 0; m < commonField.length; m++) {
					logger.debug(commonField[m]);
				}
			}
			for (int j = 0; j < imSplitList.size(); j++) {
				String[] m = (String[]) imSplitList.get(j);
				for (int n = 0; n < m.length; n++) {
					logger.debug(m[n]);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getAllInterimMaterial(String) - end       " + iMaterialMap); //$NON-NLS-1$
			}
			return iMaterialMap;
		}

		/**
		 * 获取该零件拆分后的临时物料集合。
		 * 
		 * @param String
		 *            零件号。
		 * @return List 该零部件的临时物料集合。
		 * @throws QMException
		 */
		private final List getResultIMSplitList(String partNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getResultIMSplitList(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			// 物料结果集合。
			List resultIMSplitList = new ArrayList();
			// 所有顶级物料集合。
			List rootIMSplitList = getRootInterimMSplit(partNumber);
			for (int i = 0; i < rootIMSplitList.size(); i++) {
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) rootIMSplitList
				.get(i);
				// 记录物料、供显示用的物料层级，物料的父物料号。
				List imSplitAndLevelList = new ArrayList();
				imSplitAndLevelList.add(imSplitIfc);
				imSplitAndLevelList.add("0");
				resultIMSplitList.add(imSplitAndLevelList);
				// 得到物料的子物料。只在层级状态为1的情况下才执行。
				if (imSplitIfc.getStatus() == 1) {
					getSubResultIMSplitList(imSplitIfc, resultIMSplitList, 0);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getResultIMSplitList(String) - end        "
						+ resultIMSplitList); //$NON-NLS-1$
			}
			return resultIMSplitList;
		}

		/**
		 * 获取临时子物料结果集合。
		 * 
		 * @param imSplitIfc
		 *            临时父物料。
		 * @param resultIMSplitList
		 *            最终的结果集合。
		 * @param level
		 *            层级。
		 * @throws QMException
		 */
		private void getSubResultIMSplitList(InterimMaterialSplitIfc imSplitIfc,
				List resultIMSplitList, int level) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultIMSplitList(InterimMaterialSplitIfc, List, int) - start"); //$NON-NLS-1$
				logger.debug("参数：imSplitIfc==" + imSplitIfc);
				logger.debug("参数：resultIMSplitList==" + resultIMSplitList);
				logger.debug("参数：level==" + level);
			}
			// 获取指定父物料号的物料结构集合。
			List imStructureList = getInterimMStructure(imSplitIfc.getPartNumber(),
					imSplitIfc.getMaterialNumber());
			Iterator iter = imStructureList.iterator();
			while (iter.hasNext()) {
				InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) iter
				.next();
				// 根据物料号获取物料。
				InterimMaterialSplitIfc subIMSplitIfc = getInterimMSplit(imStructureIfc
						.getChildNumber());
				// 记录物料、供显示用的物料层级，物料的父物料号。
				List imSplitAndLevelList = new ArrayList();
				imSplitAndLevelList.add(subIMSplitIfc);
				imSplitAndLevelList.add(String.valueOf(level + 1));
				imSplitAndLevelList.add(imStructureIfc.getParentNumber());
				resultIMSplitList.add(imSplitAndLevelList);
				if (subIMSplitIfc != null && subIMSplitIfc.getStatus() == 1) {
					// 递归获取子物料，放入filterMSplitList中。
					getSubResultIMSplitList(subIMSplitIfc, resultIMSplitList,
							level + 1);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultIMSplitList(InterimMaterialSplitIfc, List, int) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 根据零件号获取当前用户的顶级物料。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 顶级物料集合。
		 * @throws QMException
		 */
		private List getRootInterimMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getRootInterimMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query2 = new QMQuery("InterimMaterialSplit");
			QueryCondition condition2 = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query2.addCondition(condition2);
			query2.addAND();
			QueryCondition condition3 = new QueryCondition("rootFlag", true);
			query2.addCondition(condition3);
			query2.addAND();
			UserIfc owner = (UserIfc) sservice.getCurUserInfo();
			QueryCondition condition4 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query2.addCondition(condition4);
			List returnList = (List) pservice.findValueInfo(query2);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getRootInterimMSplit(String) - end        " + returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * 根据物料号获取当前用户的临时表物料。
		 * 
		 * @param materialNumber
		 *            物料号。
		 * @return 物料。
		 * @throws QMException
		 */
		private InterimMaterialSplitIfc getInterimMSplit(String materialNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：materialNumber==" + materialNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialSplit");
			QueryCondition condition = new QueryCondition("materialNumber",
					QueryCondition.EQUAL, materialNumber);
			query.addCondition(condition);
			query.addAND();
			UserIfc owner = (UserIfc) sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imSplitList = (List) pservice.findValueInfo(query);
			InterimMaterialSplitIfc imSplitIfc = null;
			if (imSplitList != null && imSplitList.size() > 0) {
				imSplitIfc = (InterimMaterialSplitIfc) imSplitList.get(0);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMSplit(String) - end        " + imSplitIfc); //$NON-NLS-1$
			}
			return imSplitIfc;
		}

		/**
		 * 找到临时物料表中所有当前用户的物料信息。
		 * 
		 * @return 物料集合。
		 * @throws QMException
		 */
		private List getAllInterimMSplit() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMSplit() - start"); //$NON-NLS-1$
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			final QMQuery query = new QMQuery("InterimMaterialSplit");
			QueryCondition condition = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition);
			List imSplitList = (List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMSplit() - end      " + imSplitList); //$NON-NLS-1$
			}
			return imSplitList;
		}

		/**
		 * 根据零件号获取当前用户的临时表物料。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 临时表物料集合。
		 * @throws QMException
		 */
		public List getAllInterimMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imSplitList = (List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getAllInterimMSplit(String) - end     " + imSplitList); //$NON-NLS-1$
			}
			return imSplitList;
		}

		/**
		 * 找到临时结构表中所有当前用户的结构信息。包括标识为删除的。
		 * 
		 * @return 结构信息。
		 * @throws QMException
		 */
		private List getAllInterimMStructure() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMStructure() - start"); //$NON-NLS-1$
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			final QMQuery query = new QMQuery("InterimMaterialStructure");
			QueryCondition condition1 = new QueryCondition("owner", "=", owner
					.getBsoID());
			query.addCondition(condition1);
			List imStructureList = (List) pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getAllInterimMStructure() - end     " + imStructureList); //$NON-NLS-1$
			}
			return imStructureList;
		}

		/**
		 * 获取指定父零件号的当前用户的临时物料结构集合。可以选择包括或不包括标识为删除的。
		 * 
		 * @param String
		 *            父零件号。
		 * @param boolean
		 *            是否包含标记为删除的记录，true：包含、false：不包含。
		 * @return List 指定父零件号的临时物料结构集合。
		 * @throws QMException
		 */
		private final List getInterimMStructure(String parentPartNumber,
				boolean isAll) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMStructure(String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			QMQuery query = new QMQuery("InterimMaterialStructure");
			QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
					parentPartNumber);
			query.addCondition(condition1);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imStructureList = (List) pservice.findValueInfo(query);
			List returnList = new ArrayList();
			if (isAll) {
				returnList = imStructureList;
			} else {
				for (int i = 0; i < imStructureList.size(); i++) {
					InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) imStructureList
					.get(i);
					if (iMStructureIfc.getUpdateFlag() == null
							|| !iMStructureIfc.getUpdateFlag().equals("D")) {
						returnList.add(iMStructureIfc);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getInterimMStructure(String) - end       " + returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * 根据指定的父件号和父物料号条件，找到临时结构表中当前用户的所有符合条件的结构信息。不包括标识为删除的。
		 * 
		 * @param parentPartNumber
		 *            父件号。
		 * @param parentNumber
		 *            父物料号。
		 * @return 结构信息。
		 * @throws QMException
		 */
		private List getInterimMStructure(String parentPartNumber,
				String parentNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMStructure(String, String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
				logger.debug("参数：parentNumber==" + parentNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialStructure");
			// QueryCondition condition = new QueryCondition("parentPartNumber",
			// QueryCondition.EQUAL, parentPartNumber);
			// query.addCondition(condition);
			// query.addAND();
			QueryCondition condition3 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition3);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imStructureList = (List)pservice.findValueInfo(query);
			List returnList = new ArrayList();
			for (int i = 0; i < imStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(i);
				if (iMStructureIfc.getUpdateFlag() == null
						|| !iMStructureIfc.getUpdateFlag().equals("D")) {
					returnList.add(iMStructureIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMStructure(String, String) - end        "
						+ returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * 根据指定的父件号和父物料号和子物料号条件，找到临时结构表中当前用户的所有符合条件的结构信息。不包括标识为删除的。
		 * 
		 * @param parentPartNumber
		 *            父件号。
		 * @param parentNumber
		 *            父物料号。
		 * @param childNumber
		 *            子物料号。
		 * @return 结构信息。
		 * @throws QMException
		 */
		private List getInterimMStructure(String parentPartNumber,
				String parentNumber, String childNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getInterimMStructure(String, String, String) - start"); //$NON-NLS-1$
				logger.debug("参数：parentPartNumber==" + parentPartNumber);
				logger.debug("参数：parentNumber==" + parentNumber);
				logger.debug("参数：childNumber==" + childNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialStructure");
			// QueryCondition condition = new QueryCondition("parentPartNumber",
			// QueryCondition.EQUAL, parentPartNumber);
			// query.addCondition(condition);
			// query.addAND();
			QueryCondition condition2 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition2);
			query.addAND();
			QueryCondition condition3 = new QueryCondition("childNumber",
					QueryCondition.EQUAL, childNumber);
			query.addCondition(condition3);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition4 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition4);
			List imStructureList = (List)pservice.findValueInfo(query);
			List returnList = new ArrayList();
			for (int i = 0; i < imStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(i);
				if (iMStructureIfc.getUpdateFlag() == null
						|| !iMStructureIfc.getUpdateFlag().equals("D")) {
					returnList.add(iMStructureIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getInterimMStructure(String, String, String) - end        "
						+ returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * 根据零部件标号和部门代码、是否包括回头件标志获取对应的物料。
		 * 
		 * 首先根据零部件和工艺规程的部门代码查询查找过程代码。 然后判断该工艺规程下的工序的编号与过程代码是否有交集：
		 * a.有交集，则根据工艺规程下的工序的部门代码查找该零部件对应的物料。 b.没有交集，不发布该零部件。
		 * 注意：如果一个工艺规程没有需要发布的零部件，则不发布该工艺规程。
		 * 
		 * @param processRC：工艺规程的部门代码
		 * @param partIfc：零部件值对象
		 * @param stepNumberList：工序的编号
		 * @param stepRCList：工序的与工艺规程部门代码不同的部门代码
		 * @return List：注意，最后一个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
		 * @throws QMException
		 */
		public List getMaterialByPro(String processRC, QMPartIfc partIfc,
				List stepNumberList, List stepRouteCodeList) throws QMException {
			// 符合条件的物料集合
			List proPartList = new ArrayList();
			// 是否有回头件的标志
			boolean hasCyclePart = false;
			// 判断该工艺规程下的工序的编号与该零部件的该工艺规程所在部门的过程代码是否有交集。
			// 20080110 begin
			// 如果工艺为特殊的工艺,则工序关联的零部件采用工艺中关联的零部件.
			StringTokenizer stringToken = new StringTokenizer(noProcessRouteCode,
					delimiter);
			// 该工艺是否有过程流程信息的标志．
			boolean flag1 = true;
			// 工艺下的工序与工艺的过程流程信息是否有交集的标志．
			boolean flag2 = false;
			String processRouteName;
			while (stringToken.hasMoreTokens()) {
				processRouteName = stringToken.nextToken();
				if (processRC != null && processRouteName != null
						&& processRC.equalsIgnoreCase(processRouteName)) {
					flag1 = false;
					break;
				}
			}
			if (flag1) {
				flag2 = isNumInProRoute(processRC, stepNumberList, partIfc);
			}

			// if(isNumInProRoute(processRC, stepNumberList, partIfc))
			// 20080110 end
			if (!flag1 || (flag1 && flag2)) {
				// 该零部件对应的物料集合
				List matList = getMSplitList(partIfc.getPartNumber());
				// 部门代码
				String routeCode = null;
				// 临时物料List
				List tempMatList = new ArrayList();
				// 保存工序的部门代码的HashMap，将重复的工序部门代码去掉
				HashMap stepRCMap = new HashMap();
				// 回头件的标识
				Boolean hasCyclePartFlag;
				for (int index1 = 0; index1 < stepRouteCodeList.size(); index1++) {
					routeCode = (String) stepRouteCodeList.get(index1);
					if (!stepRCMap.containsKey(routeCode)) {
						stepRCMap.put(routeCode, routeCode);
						tempMatList = getMaterialByRC(routeCode, matList);
						// 最后一个是回头件的标识
						for (int i = 0; i < tempMatList.size() - 1; i++) {
							proPartList.add((MaterialSplitIfc) tempMatList.get(i));
						}
						hasCyclePartFlag = (Boolean) tempMatList.get(tempMatList
								.size() - 1);
						hasCyclePart = hasCyclePart
						|| hasCyclePartFlag.booleanValue();
					}
				}
			}
			proPartList.add(Boolean.valueOf(hasCyclePart));
			return proPartList;
		}

		/**
		 * 根据零部件标号和部门代码、是否包括回头件标志获取对应的物料。
		 * 
		 * @param partNumber：零部件编号
		 * @param processRC：工序的部门代码
		 * @return List：注意，最后一个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
		 * @throws QMException
		 */
		public List getMaterialByStep(String partNumber, String processRC)
		throws QMException {
			// 该物料对应的物料集合
			List matList = getMSplitList(partNumber);
			// 该物料对应的物料集合过滤后的结果
			List resultList = getMaterialByRC(processRC, matList);
			MaterialSplitIfc mat;
			// 如果找不到工序的对应的物料，则临时创建一个物料
			if (resultList.size() <= 1) {
				mat = new MaterialSplitInfo();
				mat.setRouteCode(processRC);
				// 20080107 begin
				matList = getRootMSplit(partNumber);
				if (matList.size() > 0) {
					MaterialSplitIfc matSplit = (MaterialSplitIfc) matList.get(0);
					mat.setMaterialNumber(matSplit.getMaterialNumber());
				} else {
					mat.setMaterialNumber(partNumber);
				}
				// 20080107 end

				mat.setSplited(false);
				resultList.add(0, mat);
			}
			// 20070110 begin
			else {
				// 判断该工艺是否为特殊工艺．如果为特殊工艺，则取得的物料要用上一个部门的物料．
				boolean flag = true;
				// 如果工艺为特殊的工艺,则工序关联的零部件采用工艺中关联的零部件.
				// StringTokenizer stringToken = new
				// StringTokenizer(useProcessPartRouteCode, delimiter);
				// //是否有特殊工艺的标志．
				// boolean flag2=false;
				// String processName;
				// while(stringToken.hasMoreTokens()){
				// processName=stringToken.nextToken();
				// logger.debug("processName is "+processName);
				// if(technicShop != null
				// && processName != null &&
				// technicShop.equalsIgnoreCase(processName)){
				// flag2=true;
				// logger.debug("99999 technicShop.equalsIgnoreCase(processName) ");
				// break;
				// }
				// }
				if (flag) {
					List tempResultList = new ArrayList();
					for (int i = 0; i < resultList.size() - 1; i++) {
						mat = (MaterialSplitIfc) resultList.get(i);
						if (mat.getStatus() == 1) {
							List childList = this.getMStructure(
									mat.getPartNumber(), mat.getMaterialNumber());
							tempResultList.add((MaterialSplitIfc) childList.get(0));
						}

					}
					tempResultList.add((Boolean) resultList
							.get(resultList.size() - 1));
					resultList = tempResultList;
				}

			}
			// 20070110 end
			return resultList;
		}

		// 20070110 begin
		/**
		 * 根据零部件标号和部门代码、工艺的种类获取对应的物料。
		 * 
		 * @param partNumber：零部件编号
		 * @param stepRC：工序的部门代码
		 * @param processType：工艺的种类
		 * @return List：注意，最后一个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
		 * @throws QMException
		 */
		public List getMaterialByStep(String partNumber, String stepRC,
				String processType) throws QMException {
			logger.debug("4444444 partNumber is " + partNumber);
			logger.debug("4444444 stepRC is " + stepRC);
			logger.debug("4444444 processType is " + processType);
			// 该物料对应的物料集合
			List matList = getMSplitList(partNumber);
			// 该物料对应的物料集合过滤后的结果
			List resultList = getMaterialByRC(stepRC, matList);
			MaterialSplitIfc mat;
			// 如果找不到工序的对应的物料，则临时创建一个物料
			if (resultList.size() <= 1) {
				mat = new MaterialSplitInfo();
				mat.setRouteCode(stepRC);
				// 20080107 begin
				matList = getRootMSplit(partNumber);
				logger.debug("66666:" + matList.size());
				if (matList.size() > 0) {
					MaterialSplitIfc matSplit = (MaterialSplitIfc) matList.get(0);
					mat.setMaterialNumber(matSplit.getMaterialNumber());
				} else {
					mat.setMaterialNumber(partNumber);
				}
				logger.debug("66666 processRC:" + stepRC);
				logger.debug("66666:" + mat.getMaterialNumber());
				// 20080107 end

				mat.setSplited(false);
				resultList.add(0, mat);
			}
			// 20070110 begin
			else {
				// 20080122 begin
				// 该工艺是否为特殊工艺的标志.
				boolean flag = false;
				// 判断该工艺是否为特殊工艺．
				// StringTokenizer stringToken = new StringTokenizer(hasSubpartTech,
				// delimiter);
				// String processName;
				// while (stringToken.hasMoreTokens()) {
				// processName = stringToken.nextToken();
				// if (processType != null && processName != null
				// && processType.equalsIgnoreCase(processName)) {
				// flag=false;
				// break;
				// }
				// }
				// 20080122 end
				// 如果工艺为特殊的工艺,则工序关联的物料要用上一个部门的物料.
				if (flag) {
					List tempResultList = new ArrayList();
					for (int i = 0; i < resultList.size() - 1; i++) {
						mat = (MaterialSplitIfc) resultList.get(i);
						if (mat.getStatus() == 1) {
							List childList = this.getMStructure(
									mat.getPartNumber(), mat.getMaterialNumber());
							MaterialStructureIfc matStruct = (MaterialStructureIfc) childList
							.get(0);
							tempResultList
							.add(getMSplit(matStruct.getChildNumber()));
						} else {
							tempResultList.add(mat);
						}

					}
					tempResultList.add((Boolean) resultList
							.get(resultList.size() - 1));
					resultList = tempResultList;
				}

			}
			// 20070110 end
			return resultList;
		}

		// 20070110 end

		/**
		 * 获取物料集合中特定物料代码的物料。
		 * 
		 * @param routeCode：部门代码
		 * @param matList：该零部件编号对应的物料集合
		 * @return List：注意，最后一个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
		 * @throws QMException
		 */
		private List getMaterialByRC(String routeCode, List matList) {
			logger.debug("routeCode is " + routeCode);
			logger.debug("matList.size() is " + matList.size());
			// 符合条件的物料集合
			List resultList = new ArrayList();
			// 物料值对象
			MaterialSplitIfc matSplit = null;
			// 是否有回头件的标志
			boolean hasCyclePart = false;
			// 是否找到符合部门代码的物料的标志
			boolean isFind = false;
			if (matList.size() > 0) {
				// 首先查找工艺规程对应的物料
				for (int index = 0; index < matList.size(); index++) {
					matSplit = (MaterialSplitIfc) matList.get(index);
					if (matSplit.getSplited()) {
						if (matSplit.getRouteCode().trim().equals(routeCode.trim())) {
							// 如果是第一次找到，则添加
							if (!isFind) {
								resultList.add(matSplit);
								isFind = true;
							} else {
								// 如果是第二次找到，说明是回头件，需要经过判断是否添加
								if (publicCyclePart) {
									resultList.add(matSplit);
								}
								hasCyclePart = true;
							}
						}
					}
				}
			}
			resultList.add(Boolean.valueOf(hasCyclePart));
			return resultList;
		}

		/**
		 * 获取子物料结果集合。
		 * 
		 * @param mSplitIfc
		 *            父物料。
		 * @param resultMSplitList
		 *            最终的结果集合。
		 * @throws QMException
		 */
		private void getSubResultMSplitList(MaterialSplitIfc mSplitIfc,
				List resultMSplitList) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List) - start"); //$NON-NLS-1$
				logger.debug("参数：mSplitIfc==" + mSplitIfc);
				logger.debug("参数：resultMSplitList==" + resultMSplitList);
			}
			// 获取指定父物料号的物料结构集合。
			List mStructureList = getMStructure(mSplitIfc.getPartNumber(),
					mSplitIfc.getMaterialNumber());
			Iterator iter = mStructureList.iterator();
			// 物料结构值对象
			MaterialStructureIfc mStructureIfc;
			// 物料值对象
			MaterialSplitIfc subMSplitIfc;
			while (iter.hasNext()) {
				mStructureIfc = (MaterialStructureIfc) iter.next();
				// 根据物料号获取物料。
				subMSplitIfc = getMSplit(mStructureIfc.getChildNumber());
				resultMSplitList.add(subMSplitIfc);
				if (subMSplitIfc != null && subMSplitIfc.getStatus() == 1) {
					// 递归获取子物料，放入resultMSplitList中。
					getSubResultMSplitList(subMSplitIfc, resultMSplitList);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * 获取零部件拆分的物料。
		 * 
		 * @param partNumber
		 * @return
		 * @throws QMException
		 */
		private List getMSplitList(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplitList(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			List matSplitList = new ArrayList();
			// 所有顶级物料集合。
			List rootMSplitList = getRootMSplit(partNumber);
			// 物料值对象
			MaterialSplitIfc mSplitIfc;
			for (int i = 0; i < rootMSplitList.size(); i++) {
				mSplitIfc = (MaterialSplitIfc) rootMSplitList.get(i);
				matSplitList.add(mSplitIfc);
				// 得到物料的子物料。只在层级状态为1的情况下才执行。
				if (mSplitIfc.getStatus() == 1) {
					getSubResultMSplitList(mSplitIfc, matSplitList);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplitList(String) - end" + matSplitList); //$NON-NLS-1$
			}
			return matSplitList;
		}

		/**
		 * 发布工艺数据。
		 * 
		 * @param techs
		 * @throws QMException
		 */
		public void publicTechnics(String techs) throws Exception {
			try{
			List bsoIDList = new ArrayList();
			StringTokenizer st = new StringTokenizer(techs, semicolonDelimiter);
			while (st.hasMoreTokens()) {
				bsoIDList.add(st.nextToken());
			}
			// 获取持久化服务
			PersistService pService = (PersistService) EJBServiceHelper
			.getService("PersistService");
			for (int i = 0; i < bsoIDList.size(); i++) {
				// 发布数据
				BaseDataPublisher.publish(pService.refreshInfo((String) bsoIDList
						.get(i)));
			}
			}catch(Exception e){
				e.printStackTrace();
				
				
			}
		}

		/**
		 * 根据给定的事物特性持有者,获得特定iba属性值。 return String 为该iba属性的值。
		 * 
		 * @throws QMXMLException
		 */
		public String getPartIBA(QMPartIfc partIfc, String ibaDisplayName)
		throws QMXMLException {
			if (logger.isDebugEnabled()) {
				logger.debug("getPartIBA() - start"); //$NON-NLS-1$
			}
			String ibaValue = "";
			final HashMap nameAndValue = new HashMap();
			try {
				IBAValueService ibservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
				partIfc = (QMPartIfc)ibservice.refreshAttributeContainerWithoutConstraints(partIfc);
			} catch (QMException e) {
				// "刷新属性容器时出错！"
				logger.error(Messages.getString("Util.7"), e); //$NON-NLS-1$
				throw new QMXMLException(e);
			}
			DefaultAttributeContainer container = (DefaultAttributeContainer) partIfc
			.getAttributeContainer();
			if (container == null) {
				container = new DefaultAttributeContainer();
			}
			final AbstractValueView[] values = container.getAttributeValues();
			for (int i = 0; i < values.length; i++) {
				final AbstractValueView value = values[i];
				final AttributeDefDefaultView definition = value.getDefinition();
				if (definition.getDisplayName().trim()
						.equals(ibaDisplayName.trim())) {
					if (value instanceof AbstractContextualValueDefaultView) {
						MeasurementSystemCache.setCurrentMeasurementSystem("SI");
						final String measurementSystem = MeasurementSystemCache
						.getCurrentMeasurementSystem();
						if (value instanceof UnitValueDefaultView) {
							DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
							String ss = "";
							try {
								ss = defaultunitrenderer
								.renderValue(
										((UnitValueDefaultView) value)
										.toUnit(),
										((UnitValueDefaultView) value)
										.getUnitDisplayInfo(measurementSystem));
							} catch (UnitFormatException e) {
								// "计量单位格式出错！"
								logger.error(Messages.getString("Util.8"), e); //$NON-NLS-1$
								throw new QMXMLException(e);
							} catch (IncompatibleUnitsException e) {
								// "出现不兼容单位！"
								logger.error(Messages.getString("Util.9"), e); //$NON-NLS-1$
								throw new QMXMLException(e);
							}
							final String ddd = ((UnitValueDefaultView) value)
							.getUnitDefinition()
							.getDefaultDisplayUnitString(measurementSystem);
							// nameAndValue.put(definition.getDisplayName(), ss +
							// ddd);
							ibaValue = ss + ddd;
						} else {
							// nameAndValue.put(definition.getDisplayName(),
							// ((AbstractContextualValueDefaultView) value)
							// .getValueAsString());
							ibaValue = ((AbstractContextualValueDefaultView) value)
							.getValueAsString();
						}
					} else {
						// nameAndValue.put(definition.getDisplayName(),
						// ((ReferenceValueDefaultView) value)
						// .getLocalizedDisplayString());
						ibaValue = ((ReferenceValueDefaultView) value)
						.getLocalizedDisplayString();
					}
					break;
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getPartIBA() - end" + nameAndValue.size()); //$NON-NLS-1$
			}
			return ibaValue;
		}

		
		/**
		 * 判断工序的编号是否在零部件的过程代码中：如果在，则发布该零部件的物料，否则不发布该零部件的物料。
		 * 
		 * @param processRC:工艺的部门代码
		 * @param processNum:工序的编号
		 * @param part：工艺关联的零部件值对象
		 * @return
		 * @throws QMException
		 */
		private boolean isNumInProRoute(String processRC, List processNumList,
				QMPartIfc partIfc) throws QMException {
			logger.debug("processRC is " + processRC);
			logger.debug("processNumList.size() is " + processNumList.size());
			logger.debug("partIfc.getPartNumber() is " + partIfc.getPartNumber());
			boolean isNumInProRoute = false;
			// 首先获取该零部件的过程代码。
			RouteCodeIBAName routeCodeIBAName = RouteCodeIBAName
			.toRouteCodeIBAName(processRC);
			// 20080125 begin
			logger.debug("routeCodeIBAName is " + routeCodeIBAName);
			if (routeCodeIBAName != null) {
				String display = routeCodeIBAName.getDisplay();
				String processCode = "";
				processCode = getPartIBA(partIfc, display);
				String processNum;
				int index;
				// 然后判断该工序的编号是否在零部件的过程代码中：
				for (int i = 0; i < processNumList.size(); i++) {
					processNum = (String) processNumList.get(i);
					if (processCode != null && processCode.trim().length() > 0) {
						index = processCode.indexOf(processNum);
						if (index >= 0) {
							isNumInProRoute = true;
							break;
						}
					}
				}
			}
			// 20080125 end
			return isNumInProRoute;
		}

		// 20081205 zhangq begin
		/**
		 * 将路线中的路线代码转化为List,并且根据属性文件的配置去掉路线中的特殊代码。
		 * 
		 * @param routeStr
		 * @return
		 */
		public List getRouteCodeList(String routeStr) {
			StringTokenizer routeTok = new StringTokenizer(routeStr, dashDelimiter);
			String routeCode = "";
			HashMap specRCHashMap = getDleteWhenSplitRC();
			List routeCodeList = new ArrayList();
			boolean isFirst = true;
			while (routeTok.hasMoreTokens()) {
				routeCode = routeTok.nextToken();
				if (isFirst || specRCHashMap.get(routeCode) == null) {
					routeCodeList.add(routeCode);
					isFirst = false;
				}
			}
			logger.debug("getRouteCodeList end routeCodeList is " + routeCodeList);
			return routeCodeList;
		}

		private HashMap getDleteWhenSplitRC() {
			if (specRCHashMap == null || specRCHashMap.keySet().size() <= 0) {
				specRCHashMap = new HashMap();
				String routeCode = "";
				logger.debug("getDleteWhenSplitRC deleteWhenSplitRouteCode is "
						+ deleteWhenSplitRouteCode);
				StringTokenizer stringToken = new StringTokenizer(
						deleteWhenSplitRouteCode, delimiter);
				while (stringToken.hasMoreTokens()) {
					routeCode = stringToken.nextToken();
					specRCHashMap.put(routeCode, routeCode);
				}
			}
			logger.debug("getDleteWhenSplitRC specRouteCode end is "
					+ specRCHashMap);
			return specRCHashMap;
		}

		/**
		 * 根据零部件的编号和路线创建采用通知书，然后利用这个采用通知书拆分物料，创建集成包并发布。 过程： 1.创建采用通知书 2.拆分物料 3.创建集成包
		 * 4.发布物料
		 * 
		 * @param partBsoId
		 *            String:采用通知书采用的产品的BsoId
		 * @param promulgateNotifyFlag
		 *            String:采用通知书的采用标识
		 * @throws QMException
		 */
		/*public void publishMaterial(String partBsoId, String promulgateNotifyFlag)
			throws QMException {
		// 第一步：创建采用通知书
		StandardPartService standardPartService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		PersistService PersistService = (PersistService) EJBServiceHelper
				.getService("PersistService");
		PromulgateNotifyService promulgateNotifyService = (PromulgateNotifyService) EJBServiceHelper
				.getService("PromulgateNotifyService");
		QMPartIfc partIfc = (QMPartIfc) PersistService.refreshInfo(partBsoId);
		String partNumber = partIfc.getPartNumber();
		// findPartMaster
		// Collection coll = standardPartService.getAllPartMasters("",
		// partNumber);
		// if(coll == null || coll.size() != 1)
		// {
		// throw new QMException("零部件编号"+partNumber+"不唯一！");
		// }
		partIfc = standardPartService.getPartByConfigSpec(
				(QMPartMasterIfc) partIfc.getMaster(), standardPartService
						.findPartConfigSpecIfc());
		if (partIfc == null) {
			throw new QMException(partNumber + "没有符合配置规范的零部件!");
		}
		// CCBegin by dikefeng 20090217
		// String promulgateNotifyNumber = getPromulgateNotifyNumber(partIfc
		// .getPartNumber(), promulgateNotifyFlag);
		StandardAdoptNoticeService sanService = (StandardAdoptNoticeService) EJBServiceHelper
				.getService("StandardAdoptNoticeService");
		String promulgateNotifyNumber = "";
		int sortnumber = sanService.getNextSortNumber("PromulgateNotify",
				partIfc.getPartNumber() + promulgateNotifyFlag, false);
		promulgateNotifyNumber = getPromulgateNotifyNumber(partIfc
				.getPartNumber(), promulgateNotifyFlag, sortnumber);
		// System.out.println("here get the promugatenotify
		// number="+sortnumber+",and the number="+promulgateNotifyNumber);
		// CCEnd by dikefeng 20090217
		PromulgateNotifyInfo promulgateNotifyInfo = new PromulgateNotifyInfo();
		// 设置采用通知书的名称:与编号一致
		promulgateNotifyInfo.setPromulgateNotifyName(promulgateNotifyNumber);
		// 设置采用通知书的编号
		promulgateNotifyInfo.setPromulgateNotifyNumber(promulgateNotifyNumber);
		// 设置采用标识
		promulgateNotifyInfo.setPromulgateNotifyFlag(PromulgateNotifyFlag
				.toPromulgateNotifyFlag(promulgateNotifyFlag).toString());
		// 设置采用通知书的说明
		promulgateNotifyInfo.setPromulgateNotifyDescription("");
		// 设置采用通知书的发布标记:0是没有发布，1是发布过。
		promulgateNotifyInfo.setHasPromulgate("0");
		// 特别设置,预留字段，准备以后扩展
		promulgateNotifyInfo.setPromulgateNotifyType("PromulgateNotify");
		// 采用通知书的采用产品的集合
		ArrayList refPartList = new ArrayList();
		refPartList.add(partIfc.getMasterBsoID());
		// 采用通知书关联的参考文档的集合
		ArrayList refDocList = new ArrayList();
		promulgateNotifyInfo = (PromulgateNotifyInfo) promulgateNotifyService
				.createPromulgateNotify(promulgateNotifyInfo, refPartList,
						refDocList);
		// 第二步：拆分物料
		// 采用通知书的采用部件的集合
		Vector parts = promulgateNotifyService
				.getPartsByProId(promulgateNotifyInfo.getBsoID());
		StringBuffer partBsoIdBuffer = new StringBuffer();
		QMPartIfc temPartIfc = null;
		boolean isFirst = true;
		for (int index = 0; index < parts.size(); index++) {
			temPartIfc = (QMPartIfc) parts.get(index);
			if (isFirst) {
				partBsoIdBuffer.append(temPartIfc.getBsoID());
			} else {
				partBsoIdBuffer.append(semicolonDelimiter
						+ temPartIfc.getBsoID());
			}
			isFirst = false;
		}
		RequestHelper reuqestHelper = new RequestHelper();
		Object[] obj = reuqestHelper.getPartAndRoute(
				partBsoIdBuffer.toString(), true);
		List mutPartAndRouteList = (List) obj[0];
		if (mutPartAndRouteList.size() > 0) {
			throw new QMException("采用通知书的关联部件中存在多路线！");
		}
		split((String) obj[1], (String) obj[2], true);
		// 第三步：创建集成包
		String sourceid = promulgateNotifyInfo.getBsoID();
		IntePackSourceType sourcetype = IntePackSourceType
				.toIntePackSourceType("promulgateNotify");
		// 建立新的集成包值对象。
		IntePackIfc intePackIfc = new IntePackInfo();
		// intePackIfc.setName(reuqestHelper.getSourceInfo(sourceid)[1]);
		intePackIfc.setName(getIntePackName(promulgateNotifyInfo));
		intePackIfc.setSourceType(sourcetype);
		intePackIfc.setSource(sourceid);
		// 调用服务创建集成包，集成包其他属性在服务中默认给出。
		IntePackService itservice = (IntePackService) EJBServiceHelper
				.getService("IntePackService");
		intePackIfc = itservice.createIntePack(intePackIfc);
		// 第四步：发布物料
		itservice.publishIntePack(intePackIfc.getBsoID());
	}*/

		/**
		 * 获取采用通知书的编号。 采用通知书自动编号规则：采用的产品的编号+采用标识+三位数字编号。 其中最后的三位数字编号从1开始，逐渐增加。
		 * 
		 * @param partNumber
		 * @param promulgateNotifyFlag
		 * @return
		 * @throws QMException
		 */
		private String getPromulgateNotifyNumber(String partNumber,
				String promulgateNotifyFlag, int j) throws QMException {
			// CCBegin by dikefeng 20090217
			// QMQuery query = new QMQuery("PromulgateNotify");
			// query.addCondition(new QueryCondition("promulgateNotifyNumber",
			// QueryCondition.LIKE, partNumber + promulgateNotifyFlag));
			// PersistService ps = (PersistService) EJBServiceHelper
			// .getService("PersistService");
			// Collection coll = ps.findValueInfo(query);
			// TODO:暂时采用这种方式，需要修改为狄哥提供的方法。
			String str = "000" + Integer.toString(j);
			// CCEnd by dikefeng 20090217
			return partNumber + promulgateNotifyFlag
			+ str.substring(str.length() - 3);
		}

		/**
		 * 获取采用通知书作为数据源的集成包的名称。 采用通知书作为数据源的集成包名称的编码规则：AN-采用通知书的编号-年-月-日-三位数字编号。
		 * 数字编号不一定存在，只有当不带尾号的集成包名称存在时才加。从2开始，逐渐增加。
		 * 
		 * @param promulgateNotifyIfc
		 * @return
		 * @throws QMException
		 */
		/*private String getIntePackName(PromulgateNotifyIfc promulgateNotifyIfc)
			throws QMException {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String number = "AN-" + promulgateNotifyIfc.getPromulgateNotifyNumber()
				+ "-" + year + "-" + month + "-" + day;
		// CCBegin by dikefeng 20090217
		// QMQuery query = new QMQuery("IntePack");
		// query.addCondition(new QueryCondition("name", QueryCondition.LIKE,
		// number));
		// PersistService ps = (PersistService) EJBServiceHelper
		// .getService("PersistService");
		// Collection coll = ps.findValueInfo(query);
		StandardAdoptNoticeService sanService = (StandardAdoptNoticeService) EJBServiceHelper
				.getService("StandardAdoptNoticeService");
		int sortnumber = sanService
				.getNextSortNumber("IntePack", number, false);
		String num = "";
		String str = "000" + Integer.toString(sortnumber);
		// CCEnd by dikefeng 20090217
		num = "-" + str.substring(str.length() - 3);

		return number + num;
	}*/

		/**
		 * 获取默认的路线属性定义。
		 * 
		 * @return 路线属性定义的轻量级对象的集合。
		 * @throws QMException
		 */
		public List getDefaultRouteDefList() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getDefaultRouteDefList() - start"); //$NON-NLS-1$
			}
			List routeDefList = new ArrayList();
			List tempRouteDefList = new ArrayList();
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			// 用编号作为查询条件，查找是否有符合条件的StringDefinition。
			final QMQuery query = new QMQuery("StringDefinition");
			QueryCondition condition = new QueryCondition("name",
					QueryCondition.EQUAL, defRouteIBA);
			query.addCondition(condition);
			tempRouteDefList.addAll((List)pservice.findValueInfo(query));
			logger.debug("tempRouteDefList==" + tempRouteDefList);
			for (int i = 0; i < tempRouteDefList.size(); i++) {
				routeDefList
				.add(IBADefinitionObjectsFactory
						.newAttributeDefDefaultView((AttributeHierarchyChild) tempRouteDefList
								.get(i)));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getDefaultRouteDefList() - end" + routeDefList); //$NON-NLS-1$
			}
			return routeDefList;
		}

		public Collection getPartByRouteList(TechnicsRouteListIfc list,HashMap hashmap)
		throws Exception
		{
			try {
			PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
			Collection c = new Vector();
		
			QMQuery query = new QMQuery("consListRoutePartLink");
			QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
			query.addCondition(cond);
			//CCBegin by chudaming 20101222
			query.addAND();
			QueryCondition condition11 = new QueryCondition("adoptStatus",
					QueryCondition.NOT_EQUAL, "CANCEL");
			query.addCondition(condition11);
			
			Collection coll = ps.findValueInfo(query);
//			System.out.println("query = "+query.getDebugSQL());
//			System.out.println("coll = "+coll);
			PartHelper partHelper =  new PartHelper();
			for(Iterator iter = coll.iterator(); iter.hasNext();)
			{
				ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();

				Collection cc = getPartByListRoutepart(linkInfo);
				QMPartIfc partInfo = null;
				for(Iterator ii = cc.iterator(); ii.hasNext();)
				{
					partInfo = (QMPartIfc)ii.next();
					partInfo=partHelper.filterLifeState(partInfo);
					//此处是清代码，估计应该去掉
		
//					QMQuery qmquery = new QMQuery("FilterPart");
//		 			 QueryCondition condition1 = new QueryCondition("versionValue", "=",
//		 					partInfo.getVersionID());
//		 	          qmquery.addCondition(condition1); 
//		 	          qmquery.addAND();
//		 	          qmquery.addCondition(new QueryCondition("partNumber", "=",
//		 	        		 partInfo.getPartNumber()));
////		 	         qmquery.addOrderBy("createTime",true);
//		 	          Collection col1 = ps.findValueInfo(qmquery, false);
//						System.out.println("ssssssss0000000000dddddddddddddddddddd==="+col1.size());
//
//		 	          if(col1.size()!=0){
//
					     if(partInfo==null)
					    	 continue;
						 c.add(partInfo);
						// System.out.println("ssssssss0000000000dwwwwwwwwwwwwwwwwww==="+c);
						 
						 hashmap.put(partInfo.getBsoID(), linkInfo);
//		 	          }
				}

			}

			return c;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		/**
		 * added by dikefeng for sanrq 20090226
		 * 
		 * @param part
		 *            QMPartIfc
		 * @throws QMException
		 * @return String
		 */
		private String getCurrentRoute(QMPartIfc part) throws QMException {
			String route = "";
			try {
				PersistService pService = (PersistService) EJBServiceHelper
				.getPersistService();
				QMQuery query = new QMQuery("StringValue");
				int j = query.appendBso("StringDefinition", false);
				QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=", part
						.getBsoID());
				query.addCondition(qc);
				query.addAND();
				QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
				query.addCondition(0, j, qc1);
				query.addAND();
				QueryCondition qc2 = new QueryCondition("name", " = ", "路线1");
				query.addCondition(j, qc2);
				Collection col = pService.findValueInfo(query, false);
				if (col != null && col.size() > 0) {
					StringValueIfc value = (StringValueIfc) col.iterator().next();
					route = value.getValue();
					if (route != null && route.length() > 0)
						route = changeRoute(route);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new QMException(e);
			}
			return route;
		}

		// 20081205 zhangq end
	}
