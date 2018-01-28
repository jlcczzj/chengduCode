/**
 * 生成程序MaterialSplitServiceEJB.java	1.0              2007-10-7
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 在工艺bom里给零件添加生产版本，如果有生产版本则获取生产版本 刘家坤 2014-10-08
 * SS2 发布bom时，xml只输出bom信息，工艺路线发布，只输出物料信息 2014-11-20
 * SS4 如果是车型发bom，由于车型不编辑路线。所以需要单独生成车型物料
 * SS5 如果制造路线是“川料-川冲”则拆分成物料为，当前单位是“川冲”，发往单位是装配路线 刘家坤 2017-12-11
 */
package com.faw_qm.cderp.ejb.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.cderp.exception.ERPException;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.FilterPartInfo;
import com.faw_qm.cderp.model.IntePackIfc;
import com.faw_qm.cderp.model.IntePackInfo;
import com.faw_qm.cderp.model.MaterialSplitIfc;
import com.faw_qm.cderp.model.MaterialSplitInfo;
import com.faw_qm.cderp.model.MaterialStructureIfc;
import com.faw_qm.cderp.model.MaterialStructureInfo;
import com.faw_qm.cderp.util.IntePackSourceType;
import com.faw_qm.cderp.util.Messages;
import com.faw_qm.cderp.util.PartHelper;
import com.faw_qm.cderp.util.PublishBaseLine;
import com.faw_qm.cderp.util.PublishData;
import com.faw_qm.cderp.util.RequestHelper;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.domain.util.DomainHelper;
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
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;



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
 * @author 刘家坤
 * @version 1.0
 *  查找子件产品默认查part，而解放有广义部件 刘家坤 2014-06-12
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

	public Collection djbm=null;
	

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

	private static final String RESOURCE = "com.faw_qm.cderp.util.ERPResource";

	

	/**
	 * 逗号分隔符。用于分隔useProcessPartRouteCode。
	 */
	private String delimiter = "，";
    //为了外购件和自制件分堆，主要是因为系统切换。需要先切换外购件，在切换自制件
	HashMap mapcglx = new HashMap();


		
		/**
		 * 发布工艺bom
		 * @param coll 零部件的集合
		 * @param baselineiD 零部件存放的基线
		 * @param lx 发布类型，“1”工艺bom发布，“2”路线发布
		 * @param routeID 发布的路线
		 * @throws QMException
		 */
		 public void publishPartsToERP(Collection coll ,String routeID ,Collection vec,int i)
	        throws Exception
	    {
	        try
	        {
	        	PartHelper partHelper = new PartHelper();
	        	PublishData publish = new PublishData();
	     	  System.out.println("jinru-------publishPartsToERPlc");
	        	
	            String xmlName = "";
	            //路线表时间
	           xmlName = publish.getVirtualPartNumber();
	         //   MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
	           if(coll!=null){
	        	   coll = partHelper.filterLifeCycle1(coll);
	           }
	           if(coll==null||coll.size()==0){
	        	   return;
	           }
	 

	            //拆分零部件
	           
	            Vector filterPartVec = split( coll, "1",  routeID);
	       
	            System.out.println("filterPartVec-------"+filterPartVec);
	        
	
	           //将数据打集成包
	            String packid = createIntePack(xmlName, "", true, routeID);             
	            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("CDIntePackService");;
	            //发布数据
	            packservice.publishIntePack(packid, xmlName,coll,filterPartVec,vec,i);

	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	            throw e;
	        }
	    }
		/**
		 * 发布工艺路线
		 * @param coll 零部件的集合
		 * @param baselineiD 零部件存放的基线
		 * @param lx 发布类型，“1”工艺bom发布，“2”路线发布
		 * @param routeID 发布的路线
		 * @throws QMException
		 */
		 public void publishPartsToERPlc(Collection coll,String routeID)
	        throws Exception
	    {
	        try
	        {
	        	PartHelper partHelper = new PartHelper();
	        	PublishData publish = new PublishData();	     
	        	
	            String xmlName = "";
	            //生成路线表名称
	           xmlName = publish.getVirtualPartNumber();

	         
	            //拆分零部件
	            Vector filterPartVec = split( null, "2",  routeID);
	           // Vector filterPartVec = new Vector();
	           System.out.println("filterPartVec-------"+filterPartVec);
	            if(filterPartVec==null||filterPartVec.size()==0){
	          	  return;     
	            }
	            System.out.println("qqq-------"+filterPartVec.size());
	           //将数据打集成包
	            String packid = createIntePack(xmlName, "", true, routeID);             
	            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("CDIntePackService");;
	            //packservice.publishIntePack(packid, xmlName,al,filterPartVec);
	            //发布数据
	            packservice.publishIntePack(packid, xmlName,coll,filterPartVec,null,0);

	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	            throw e;
	        }
	    }
		    private String createIntePack(String xmlName, String baselineID, boolean flag, String routeID)
		    throws QMException
		{
		    String name = xmlName;
		    String sourceid = "";
		    IntePackSourceType sourcetype = null;
		    if(flag)
		    {
		        sourceid = routeID;
		        sourcetype = IntePackSourceType.technicsRouteList;
		    } else
		    {
		        sourceid = "";
		        sourcetype = IntePackSourceType.BASELINE;
		    }
		    IntePackInfo intepack = new IntePackInfo();
		    intepack.setName(name);
		    intepack.setSourceType(sourcetype);
		    intepack.setSource(sourceid);
		    IntePackService itservice = (IntePackService)EJBServiceHelper.getService("CDIntePackService");
		    IntePackIfc intepackifc = itservice.createIntePack(intepack);
		    String bsoid = intepackifc.getBsoID();
		    return bsoid;
		}

	/**
	 * 拆分物料，解放ERP集成专用
	 * @param coll 零部件的集合
	 * @param baselineiD 零部件存放的基线
	 * @param lx 发布类型，“1”工艺bom发布，“2”路线发布
	 * @param routeID 发布的路线
	 * @throws QMException
	 */
	public Vector split(Collection coll, String inputLx, String routeID)throws QMException
	{
	
		HashMap hamap;
		PersistService pservice;
		Vector filterPartMap = new Vector();
		Vector filterPartMap1 = new Vector();
		QMPartIfc partIfc;
		String filterPartBsoIDs[];
		HashMap partLinkMap=new HashMap();
		//将发布类型传递给实例变量
		 PartHelper partHelper = new PartHelper();
		 PublishBaseLine publishBase = new PublishBaseLine();
	
		try
		{
			RequestHelper.initRouteHashMap();
			//hamap = new HashMap();
			Collection collection = new Vector();
			pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
			ManagedBaselineIfc baseline = null;
			PartConfigSpecIfc configspecifc = null;
			//System.out.println("inputLx="+inputLx);	
			
			 if(inputLx.equals("2"))//路线发布
			{
				// System.out.println("inputLx="+inputLx);	
//			如果是按照艺准发布，则如果当前部件有子件的话需要对当前部件的叶子物料与子件物料的关系进行处理questions
				if(routeID == null)
					throw new QMException("要发布的路线不存在，不能进行数据发布！！");
				TechnicsRouteListIfc routelistifc = (TechnicsRouteListIfc)pservice.refreshInfo(routeID);
				Collection coll1 = getPartByRouteListJF(routelistifc,partLinkMap);
				collection.addAll(coll1);
			}  
			 //车型号
			 QMPartIfc cxifc = null;
		   if(inputLx.equals("1"))//BOM单发布
			{  
				QMPartIfc qmpartifc = null;
				Iterator iter = coll.iterator();
				while(iter.hasNext()){
					QMPartIfc qmpartifc1 = (QMPartIfc) iter.next();
					   //?零部件类型是装置图，该件及其子件不向ERP发布
	                   String partType = qmpartifc1.getPartType().getDisplay().toString();
	                   String partnumber = qmpartifc1.getPartNumber();
	                  // System.out.println("partType="+partType);
	                   if(partType.equals("装置图"))
	                   continue;
	                  // qmpartifc = partHelper.filterLifeState(qmpartifc1);
	                   //CCBegin SS4
	                   //collection.add(qmpartifc1);
	                   if(partType.equals("车型")&&(partnumber.indexOf("5000990")<0)){
	                	   collection.add(qmpartifc1);
	                   }
					  //CCEnd SS4
					   
				}
					
			}

			System.out.println("cccccccccccccccccccccccccccccbbbbbbcccc11111111111111111111111111111=============="+collection);
//    		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz1111111111111=============="+partLinkMap);
		 
		   Vector tempVec = new Vector();
		   Vector resVec = new Vector();
	
	
		  
		   //获得需要拆分的零部件的集合  ,查看是否需要发布
		   filterPartMap = filterParts(collection,partLinkMap);
		//	System.out.println("filterPartMap===" + filterPartMap);
//			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz000000000000000=============="+filterPartMapOld);
//			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz22222222222222=============="+partLinkMap);
			if(filterPartMap == null || filterPartMap.size() == 0)
			{
				System.out.println("经过过滤没有需要拆分的零部件");
				logger.debug("经过过滤没有需要拆分的零部件。");
//				System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz3333333333333============="+filterPartMap);
				return null;
				
			}
			 
			 if(inputLx.equals("1"))//BOM单发布
				{ 
//				 filterPartMap1 = filterPartMap;
//				 filterPartMap = new Vector();
				// filterPartMap.add(cxifc.getBsoID());
//				 
//				 return filterPartMap;
				}
			
			System.out.println("filterPartMap="+filterPartMap);
			
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
		
		partIfc = null;
		filterPartBsoIDs = new String[filterPartMap.size()];
		//获得所有需要拆分的零部件的id
		for(int mm = 0; mm < filterPartMap.size(); mm++)
		{
			String partid = (String)filterPartMap.elementAt(mm);
			filterPartBsoIDs[mm] = partid;
		}
		//针对每一个零部件进行处理
		// 针对每一个零部件进行处理

		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			partIfc = (QMPartIfc) pservice.refreshInfo(filterPartBsoIDs[i]);

			// System.out.println("partIfczzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+partIfc);
            //gybom
			//List usageLinkList = getUsageLinks(partIfc);
			Vector routevec = new Vector();

			//判断是不是通过工艺路线发布，如果是通过工艺路线，则将前期缓存的listroutepartlinkinfo传过去，以便
			//取到正确的路线
		//	System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa/00000000000=============="+partIfc.getBsoID()+"sssssssssss"+partIfc.getPartNumber());
			//根据界面上“多重路线合并”复选框，如果打挑将装配路线合并 
             //partLinkMap是获取工艺路线关联零件时候，已经刷新出来的路线link
				if(partLinkMap.get(partIfc.getBsoID())==null)
					routevec=RequestHelper.getRouteBranchs(partIfc, null);
				else
					routevec=RequestHelper.getRouteBranchs(partIfc, (ListRoutePartLinkInfo)partLinkMap.get(partIfc.getBsoID()));

		//	System.out.println("routevec1111111111111111=============="+partLinkMap);
			//			制造路线
			String routeAsString = "";
//			全路线串
			String routeAllCode = "";
//			装配路线
			String routeAssemStr = "";
			//颜色件标识
			boolean colorFlag = false;
			boolean isMoreRoute=false;
			String lx_y = "";
			if(routevec.size() != 0)
			{
				
				routeAsString = (String)routevec.elementAt(5);
				
				routeAllCode = (String)routevec.elementAt(1);
				//System.out.println("routeAllCode11111111111111"+routeAllCode);
				routeAssemStr=(String)routevec.elementAt(2);


				//System.out.println("routeAssemStr1111111111111w"+routeAssemStr);
				isMoreRoute=(new Boolean((String)routevec.elementAt(4))).booleanValue();
			    String scolorFlag = (String)routevec.elementAt(6);
			    if(scolorFlag.equals("1")){
			    	colorFlag = true;
			    }
			    lx_y =  (String)routevec.elementAt(7);

			}
//			查看是否需要发布	
			boolean aa = ifPublish(partIfc,routeAsString,routeAssemStr);
			if(!aa){

				filterPartMap.remove(filterPartBsoIDs[i]);
			
				continue;
			}
				
//			新加逻辑总成判断规则 （1）编号第五位是“G“，（1）制造路线含用、无装配路线（2）制造路线不含用，有装配路线（3）逻辑总成均不拆分半成品
			boolean islogic =partHelper.isLogical(partIfc,routeAsString,routeAssemStr);	


	       
//			解放公司集成接口发布的零部件数据添加到“发布基线”中，方便业务人员通过该基线查看零部件数据
			publishBase.putPartToBaseLine(partIfc,"发布基线");
			

//			根据零部件编号，删除所有的物料拆分和物料拆分结构here
			//为了下发删除标记，在物料表和结构表中都记录了关于物料和物料结构的应该删除的记录，这里在重新拆分之前先行删除		
			//解放 刘家坤 20140624erp要求编号带版本
			// 技术中心源版本

			String partnumber = partIfc.getPartNumber();
			String partVersion = "";
			//System.out.println("scbb="+scbb);
		
		     partVersion = PartHelper.getPartVersion(partIfc);
		
			
			 partnumber =partHelper.getMaterialNumber(partIfc,partVersion);
		

		//	 erp要求partnumber显示成品编号
			String partnumberold = partnumber;
			
			
//
			System.out.println("start partnumber ========"+partnumber);
			//删除条件零件编号+版本
			deleteMaterialStructure(partIfc,true,partnumber);
			List oldMSplitList = deleteMaterialSplit(partIfc, true,partnumber);
			List mSplitListBiaoJi = new ArrayList();
			// 获得零件已发布的物料

			mSplitListBiaoJi = getAllMSplit(partnumber);

			// 直接物料，不再缓存物料编号 
			HashMap oldMaterialHashMap = new HashMap();
			//将历史物料信息，放到一个map中，为以后查看该零件是否发布过，进行打标记
			for (int dd = 0; dd < mSplitListBiaoJi.size(); dd++) {
				MaterialSplitIfc a = (MaterialSplitIfc) mSplitListBiaoJi
						.get(dd);
				oldMaterialHashMap.put(a.getMaterialNumber(), a);

			}
			// System.out.println("dddddddddddddddddddssssssss=========="+oldMaterialHashMap);

			// 根据零部件编号，获得所有物料拆分结构，放到mStructureListBaoJi中
			List mStructureListBaoJi = new ArrayList();
			mStructureListBaoJi = getMStructure(partnumber);
			// 直接缓存结构，不再缓存编号
			HashMap oldMaterialStructrue = new HashMap();
			for (int ff = 0; ff < mStructureListBaoJi.size(); ff++) {
				MaterialStructureIfc s = (MaterialStructureIfc) mStructureListBaoJi
						.get(ff);
				oldMaterialStructrue.put(s.getParentNumber() + ";;;"
						+ s.getChildNumber(), s);
			}
			//CCbegin ss5
			if(routeAsString.toString().startsWith("川料")){
				routeAsString=routeAsString.substring(3);
				routeAllCode=routeAllCode.substring(3);
//				System.out.println("routeAllCode999999999=="+routeAllCode);
//				System.out.println("routeAsString999999999=="+routeAsString);
				//continue;
			}
			
//			CCEnd SS5
			String partType = partIfc.getPartType().getDisplay().toString();
			//CCBegin SS4
			if(partType.equals("车型")&&(partnumber.indexOf("5000990")<0)){
				routeAsString = "川总(装)";
				routeAllCode = "川总(装)";
				colorFlag = true;
			}
			//CCEnd SS4
          //如果路线为空，或者零部件是逻辑总成，"川料-川冲"不进行拆分
//			CCbegin ss5
		    System.out.println("routeAsString==="+routeAsString);
			if((routeAsString != null && routeAsString.toString().equals("")||routeAsString.toString().equals("川料-川冲"))||routeAsString.toString().equals("川料-川生")||(islogic))
			{//CCEnd SS4
				System.out.println("路线为空或者路线是逻辑总成，即不需拆分零部件");
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				mSplitIfc.setRootFlag(true);
				//hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
				String materialNumber =  partnumber;
				mSplitIfc.setMaterialNumber(materialNumber); 
				mSplitIfc.setPartNumber(partnumberold);
				mSplitIfc.setPartVersion(partVersion);
				mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
				mSplitIfc.setSplited(false);
//				CCBegin SS5
				System.out.println("routeAsString000000000==="+routeAsString);
				if(routeAsString.toString().equals("川料-川冲")){
					System.out.println("进来1===");
					mSplitIfc.setRoute(routeAllCode);	
				    mSplitIfc.setRCode("川冲");
					mSplitIfc.setRouteCode("川冲");
				}else{
					System.out.println("进来2===");
					mSplitIfc.setRoute(routeAllCode);
				    mSplitIfc.setRCode(routeAsString);
					mSplitIfc.setRouteCode(routeAsString);
				}
				if(routeAsString.toString().equals("川料-川生")){
					mSplitIfc.setRoute(routeAllCode);					
				    mSplitIfc.setRCode("川生");
					mSplitIfc.setRouteCode("川生");
				}else{
					mSplitIfc.setRoute(routeAllCode);
				    mSplitIfc.setRCode(routeAsString);
					mSplitIfc.setRouteCode(routeAsString);
				}
			
				//CCEnd SS6
				 String cgbs = partHelper.getCgbs(routeAsString);
				 System.out.println("cgbs0000==============="+cgbs);
				 mapcglx.put(partIfc.getBsoID(), cgbs);
				mSplitIfc.setIsMoreRoute(isMoreRoute);
				 
//				如果获得子件列表不为空，则将status设置为2，否则设置为0	
			//路线阶段无法判断是否有子件
				mSplitIfc.setStatus(0);
				
				setMaterialSplit(partIfc, mSplitIfc);
				//虚拟件标识
				 //System.out.println("startvirture===============");
				 boolean virtualFlag= false;
			     if(partType.equals("车型")&&(partnumber.indexOf("5000990")<0)){
			    	 
			     }else{
			    	  virtualFlag=partHelper.getJFVirtualIdentity( partIfc, routeAsString, routeAssemStr,islogic,colorFlag);
			     }
				
//				 System.out.println("routeAsString==============="+routeAsString+"ddd"+routeAssemStr);
//				 System.out.println("partIfc0000==============="+partIfc);
//				System.out.println("virtualFlag0000==============="+virtualFlag);
				 mSplitIfc.setVirtualFlag(virtualFlag);
				
               //采购标识
				mSplitIfc.setProducedBy("Y");
				//颜色件标识
			    mSplitIfc.setColorFlag(colorFlag);
				

			  //  System.out.println("oldMaterialHashMap==============="+oldMaterialHashMap);
				if(oldMaterialHashMap!=null&&oldMaterialHashMap.size()>0){
				 //   System.out.println("mSplitIfc.getMaterialNumber()==============="+mSplitIfc.getMaterialNumber());

					if(oldMaterialHashMap.get(mSplitIfc.getMaterialNumber())!=null){
						
							mSplitIfc.setMaterialSplitType("U");
							//删除历史物料
							pservice.deleteValueInfo((MaterialSplitIfc)oldMaterialHashMap.get(mSplitIfc.getMaterialNumber()));
							oldMaterialHashMap.remove(mSplitIfc.getMaterialNumber());
							
					
							Iterator oldMaterialIter=oldMaterialHashMap.keySet().iterator();
		//					System.out.println("meiyongde---========================"+oldMaterialHashMap);
							//此处程序没看懂。为什么要设置D标记前面已经删除了
							while(oldMaterialIter.hasNext())
							{
								
								System.out.println("此处应该进不来======================11111111111=="+oldMaterialHashMap);
								String key=(String)oldMaterialIter.next();
								MaterialSplitIfc oldMaterialSplitIfc=(MaterialSplitIfc)oldMaterialHashMap.get(key);
								oldMaterialSplitIfc.setMaterialSplitType("D");
								pservice.saveValueInfo(oldMaterialSplitIfc);
							}
							Iterator oldStruIte=oldMaterialStructrue.keySet().iterator();
							while(oldStruIte.hasNext())
							{
								MaterialStructureIfc oldStru=(MaterialStructureIfc)oldMaterialStructrue.get(oldStruIte.next());
								oldStru.setMaterialStructureType("D");
								pservice.saveValueInfo(oldStru);
							}
					}
					else {
						mSplitIfc.setMaterialSplitType("N");
					}
//					System.out.println("3333333333333333333333333333======"+oldMaterialHashMap);
				
				}
				else{
//					System.out.println("zzzzzzzzzzzzzzhhhhhwaadasdhhhhhhhh"+mSplitIfc.getMaterialNumber());
					mSplitIfc.setMaterialSplitType("N");
				}
//				mSplitList.add(mSplitIfc);
				
//				System.out.println("zzzzzzzzzzzzzzhhhhhwaadasdhhhhhhhh"+mSplitIfc.getMaterialNumber());
				mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
				
				
			} else{
				// 需要进行物料拆分的情况下，首先将制造路线分解为一个个路线字符
				//System.out.println("routeAsString==" + routeAsString);
				List routeCodeList = getRouteCodeList(routeAsString);
				
		
			
				
				// System.out.println("routeCodeListrouteCodeListww======="+routeCodeList);
				HashMap routeCodeMap = new HashMap(5);
				Vector mSplitList = new Vector();
				Vector co = new Vector();
				
				for (int m = routeCodeList.size() - 1; m >= 0; m--) {
					String routeCode = (String) routeCodeList.get(m);
					String makeCodeNameStr = "";
					String makeCodeNameStr0 = RemoteProperty
							.getProperty("com.faw_qm.cderp.routecode." + routeCode);
//					System.out.println("routeCode====="+routeCode);
//					System.out.println("makeCodeNameStr0====="+makeCodeNameStr0);
//					if(routeCode.equals("用")){
//						continue;
//					}
					if(makeCodeNameStr0==null||makeCodeNameStr0.equals("")){
						String routeCode1 = partHelper.getLXCode(routeCodeList,routeCode);
						//System.out.println("routeCode1====="+routeCode1);
						 makeCodeNameStr = RemoteProperty.getProperty("com.faw_qm.cderp.routecode." + routeCode1);
					}else{
						makeCodeNameStr = makeCodeNameStr0;
					}
					//System.out.println("makeCodeNameStr====="+makeCodeNameStr);
					// 获取当前零件的资料夹
					String folder = partIfc.getLocation();
			
				//	 System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn============================================"+jsbb);
					MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
					String materialNumber = "";
					// 如果是路线串中的最后一个路线单位
					if (makeCodeNameStr == null)
						makeCodeNameStr = "";
					    materialNumber =  partnumberold;
					// 根据配置文件获取零部件制造路线最后一个路线单位的编码方式，如果是零件号+路线单位，则直接设置
					//如果是最后一个 物料则，不需要加路线单位
					if (m == routeCodeList.size() - 1) {
						materialNumber = materialNumber;				
						mSplitIfc.setRootFlag(true);
						
						//hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
					}
					
					else {
						if(makeCodeNameStr==null||makeCodeNameStr.length()==0){
							materialNumber = materialNumber;
						}else{
							materialNumber =  materialNumber + dashDelimiter + makeCodeNameStr;	
						}
						colorFlag = false;
									
					}
					
					mSplitIfc.setMaterialNumber(materialNumber);
					mSplitIfc.setPartNumber(partnumberold);
					mSplitIfc.setPartVersion(partVersion);

					mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
					mSplitIfc.setSplited(true);
					//如果是第一个节点，说明是最末级，需要查看当前零件是否有关联的子件，如果有说明它有子件。
					//设置层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
					if (m == 0) {
//						路线阶段无法判断是否有子件
					    mSplitIfc.setStatus(0);
					} else {
						mSplitIfc.setStatus(1);
					}
					//虚拟件标识
					//System.out.println("startvirture===============");
					 boolean virtualFlag= false;
				     if(partType.equals("车型")&&(partnumber.indexOf("5000990")<0)){
				    	  
				     }else{
				    	 virtualFlag=partHelper.getJFVirtualIdentity( partIfc, routeCode, routeAssemStr,islogic,colorFlag);
				     }
//				   判断是否是采购件
				   //  System.out.println("routeCode==============="+routeCode);
					 String cgbs = partHelper.getCgbs(routeCode);
					// boolean virtualFlag=partHelper.getJFVirtualIdentity( partIfc, routeAsString, routeAssemStr,islogic,colorFlag);
	
					
//					 System.out.println("partIfc000==============="+partIfc);
//					System.out.println("virtualFlag0000==============="+virtualFlag);
					 System.out.println("cgbs1111==============="+cgbs);
					 mapcglx.put(partIfc.getBsoID(), cgbs);
					 mSplitIfc.setVirtualFlag(virtualFlag);
					
	                //采购标识
					mSplitIfc.setProducedBy(cgbs);
					//颜色件标识
				    mSplitIfc.setColorFlag(colorFlag);
				    mSplitIfc.setRCode(routeCode);
					mSplitIfc.setRouteCode(routeCode);
					mSplitIfc.setRoute(routeAllCode.toString());
					//erp回导零件号
				    // System.out.println("")
					
					// 根据零部件设置物料其他的需要发布的属性信息
					setMaterialSplit(partIfc, mSplitIfc);
					mSplitList.add(mSplitIfc);

				}

				Object[] objs = mSplitList.toArray();
				//设置标记
				// System.out.println("mSplitList==" + mSplitList);
				for (Iterator iter = mSplitList.iterator(); iter.hasNext();) {
					// questions，如果零部件由于路线变化需要重新拆分，但是其中某几个物料的内容没有发生变化，那么也将这些物料设置为U标识
					MaterialSplitIfc ifc = (MaterialSplitIfc) iter.next();
//					System.out
//							.println("dsadasdasdasdasd" + ifc.getMaterialNumber());

					//System.out.println("old1111111111111111=========="+oldMaterialHashMap);
					if (oldMaterialHashMap != null && oldMaterialHashMap.size() > 0) {
//						 System.out.println("00000=========="+ifc.getMaterialNumber());
//						 System.out.println("old1111111111111111=========="+oldMaterialHashMap.get(ifc.getMaterialNumber()));
						if (oldMaterialHashMap.get(ifc.getMaterialNumber()) != null) {
							
							ifc.setMaterialSplitType("U");
							// }
							//解放刘家坤 20140624 erp要求旧版本数据也保留，此处部进行只删除当前版本的数据。
							//只保留当前版本的最新数据
							MaterialSplitIfc mifc = (MaterialSplitIfc) oldMaterialHashMap.get(ifc.getMaterialNumber());
							String oldPartVersion = mifc.getPartVersion();
						
								pservice.deleteValueInfo(mifc);
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
//					System.out.println("getMaterialNumber=="
//							+ ifc.getMaterialNumber());

					// System.out.println("shenmenea ========"+ifc.getRoute());
				}
				// 此处不明白,上面已经删除。还怎么打删除标记
				Iterator oldMaterialIter = oldMaterialHashMap.keySet().iterator();
				while (oldMaterialIter.hasNext()) {
					String key = (String) oldMaterialIter.next();
					MaterialSplitIfc oldMaterialSplitIfc = (MaterialSplitIfc) oldMaterialHashMap
							.get(key);
					oldMaterialSplitIfc.setMaterialSplitType("D");
					// System.out.println("222222222222222222"+oldMaterialSplitIfc.getMaterialNumber());
					pservice.saveValueInfo(oldMaterialSplitIfc);
				}

				for (int p = 0; p < mSplitList.size(); p++) {
					MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
							.get(p);
					MaterialSplitIfc childMSfc = null;
					if (p != mSplitList.size() - 1)
						childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
					if (parentMSIfc != null) {
						MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
						//如果有下级物料
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
		                    //如果系统中存在该物料的结构关系，删除就结构。新结构打D标记。
							if (oldMaterialStructrue.get(parentMSIfc
									.getMaterialNumber()
									+ ";;;" + childMSfc.getMaterialNumber()) != null) {
								MaterialStructureIfc oldStructrue = (MaterialStructureIfc) oldMaterialStructrue
										.get(parentMSIfc.getMaterialNumber()
												+ ";;;"
												+ childMSfc.getMaterialNumber());
								mStructureIfc.setMaterialStructureType("O");
								////解放刘家坤 此处不做修改
								
								pservice.deleteValueInfo(oldStructrue);
								oldMaterialStructrue.remove(parentMSIfc
										.getMaterialNumber()
										+ ";;;" + childMSfc.getMaterialNumber());
							} else {
								mStructureIfc.setMaterialStructureType("N");
							}

							mStructureIfc.setChildNumber(childMSfc
									.getMaterialNumber());
							mStructureIfc.setQuantity(1.0F);
							mStructureIfc.setLevelNumber(String.valueOf(p));
			
							mStructureIfc
									.setDefaultUnit("个");
						
							mStructureIfc = (MaterialStructureIfc) pservice
									.saveValueInfo(mStructureIfc);
							
							// }
						} else if (parentMSIfc.getStatus() == 2)
							logger.debug("2—此物料下要挂接原零部件的子件。");
						else if (parentMSIfc.getStatus() == 0)
							logger.debug("0-最底层物料。");
					}
				}
				//物料结构打删除标记
				Iterator oldStruIte = oldMaterialStructrue.keySet().iterator();
				while (oldStruIte.hasNext()) {

					MaterialStructureIfc oldStru = (MaterialStructureIfc) oldMaterialStructrue
							.get(oldStruIte.next());

					oldStru.setMaterialStructureType("D");
					pservice.saveValueInfo(oldStru);
					// System.out.println("------------------------------------sssssssss--------------"+oldStru.get);
				}

			}
			
		}
		if (logger.isDebugEnabled())
			logger.debug("split(String, boolean) - end");
		// 20090422，为了使得在发布的时候知道本次新发的物料都有哪些，需要在这里把过滤后的零件清单返回一个
		//filterPartMap.addAll(filterPartMap1);
		return filterPartMap;
	}
	
	
	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		  System.out.println("list---------="+list);
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		Collection c = new Vector();
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
		query.addCondition(cond);
		Collection coll = ps.findValueInfo(query);
		// System.out.println("coll---------="+coll);
//		添加制造视图最新版本
        PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("工艺视图");
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			Collection cc = getPartByListRoutepart(linkInfo);
			QMPartIfc partInfo = null;
			for(Iterator ii = cc.iterator(); ii.hasNext(); c.add(partInfo))
			{//CCBegin SS1
				QMPartIfc partInfo_old = (QMPartIfc)ii.next();
				//System.out.println("partInfo_old="+partInfo_old);
	            partInfo = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)partInfo_old.getMaster() , configSpecGY);
	           // System.out.println("partInfo="+partInfo);
			}//CCEnd SS1
				
			

		}
	//	System.out.println("c="+c);
		return c;
	}
	
	

	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		Vector coll = new Vector();
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
	    String partBsoid = linkInfo.getRightBsoID();
		
		QMPartIfc part = (QMPartIfc) ps.refreshInfo(partBsoid);
		 
		coll.add(part);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
		return coll;
	}
	//父件的直接子件大于1000时发布的数据不全。
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
	private final void filterBySplitRule(final List filterPartList )
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("参数：filterPartList==" + filterPartList);
	  }
	  PartHelper partHelper = new PartHelper();
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // 不需拆分的零部件集合。
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
		 String partVersion ="";
	
		 partVersion = PartHelper.getPartVersion(partIfc);
		 

	   List mSplitList = new ArrayList();
	   try {
	    // 根据零件号获取已拆分的物料。
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber(),partVersion);
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
//	      if (partIfc.getVersionID().compareTo(
//	        materialSplitIfc.getPartVersion()) < 0) 
    	 int compare = partHelper.compareVersion(partIfc.getVersionID(), materialSplitIfc.getPartVersion());
          if(compare==2)
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
	       } else if (partHelper.compareVersion(tempMaterialSplit.getPartVersion(),materialSplitIfc.getPartVersion())==2)
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
//	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
//	      String routeAsString = "";
//	      String routeAllCode = "";
//	      String routeAssemStr = "";
//	      if(routevec.size() != 0)
//	      {
//	       routeAsString = (String)routevec.elementAt(0);
//	       routeAllCode = (String)routevec.elementAt(1);
//	       routeAssemStr= (String)routevec.elementAt(2);
//	      }
//	      boolean flag1=false;
//	      boolean flag2=false;
//	      if(routeAllCode==null || routeAllCode=="")
//	      {
//	       flag1=true;
//	      }
//	      if(tempMaterialSplit.getRoute()==null || tempMaterialSplit.getRoute()=="")
//	      {
//	       flag2=true;
//	      }
//	      System.out.println("100304++++++++flag1flag1flag1flag1flag1-------------"+flag1);
//	      System.out.println("100304++++++++flag2flag2flag2flag2flag2-------------"+flag2);
//	      if(flag1&&flag2)
//	      {
////	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
//	       removePartList.add(partIfc);
//	      }
//	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
	    	 System.out.println("100304++++++++flag11111111111111111111111-------------"); 
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()))
	       {
	    	   System.out.println("100304++++++++flag222222222222222222222222222222222222-------------");  
	    	 //刘家坤20140609,如果虚拟件发生变化，也需要拆分
	    	   String xnjMaterial = "N";
	    	   String xnj ="";
	    	   if(xnj==null){
	    		   xnj = "N";
	    	   }
				 boolean virtualFlag1=tempMaterialSplit.getVirtualFlag();
				 if(virtualFlag1){
					 xnjMaterial = "Y";
				 }
				 System.out.println("100304++++++++flag222222222222222222222222222222222222-------------xnf"+xnj);  

				 System.out.println("100304++++++++flag222222222222222222222222222222222222-------------xnjMaterial"+xnjMaterial);  
				 if(xnj.equals(xnjMaterial)){
					 removePartList.add(partIfc);
				 }
	        
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
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()))
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
		//刘家坤20140609
		String xnj = "N";
		if(baseValueIfc instanceof QMPartIfc)
		{
			PartHelper partHelper = new PartHelper();
//			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
//			String routeAsString = "";
//			String routeAllCode = "";
//			String routeAssemStr = "";
//			if(routevec.size() != 0)
//			{
//				routeAsString = (String)routevec.elementAt(0);
//				routeAllCode = (String)routevec.elementAt(1);
//				routeAssemStr = (String)routevec.elementAt(2);
//			}
//			
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			String partVersion = PartHelper.getPartVersion(part);
			
			String partnumber = part.getPartNumber() + "/" + partVersion;
			//解放刘家坤20140609
			// xnj = PartHelper.getPartIBA(part, "虚拟件","virtualPart");
			 if(xnj==null){
				 xnj = "N";
			 }
			 //编号+版本+生命周期状态+路线+虚拟件
			returnString = partnumber + part.getLifeCycleState().getDisplay()  + xnj;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				//刘家坤20140609
				 if( materialSplitIfc.getVirtualFlag()){
					 xnj = "Y";
				 }
				returnString = materialSplitIfc.getPartNumber()  + materialSplitIfc.getState()  + xnj;;
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
	//	System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== "+filterPartList);

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
		PartHelper partHelper = new PartHelper();
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List mSplitList = getAllMSplit(partIfc.getPartNumber());
		if (mSplitList != null && mSplitList.size() > 0) {
			MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) mSplitList.get(0);
			////
//		if (mSplitIfc.getPartVersion() != null
//					&& partIfc.getVersionID().compareTo(
//							mSplitIfc.getPartVersion()) > 0) 
		  int compare = partHelper.compareVersion(partIfc.getVersionID(),mSplitIfc.getPartVersion());
		  if(mSplitIfc.getPartVersion() != null){
			  if(compare==1)
				{
					Iterator iter = mSplitList.iterator();
					while (iter.hasNext()) {
						mSplitIfc = (MaterialSplitIfc) iter.next();
						mSplitIfc.setPartVersion(partIfc.getVersionID());
						mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
					}
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
		final QMQuery query = new QMQuery("CDMaterialSplit");
		QueryCondition condition = new QueryCondition("partNumber",
				QueryCondition.EQUAL, partNumber);
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
		QMQuery query = new QMQuery("CDMaterialStructure");
		QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
				partNumber);
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
        String partType = partIfc.getPartType().getDisplay();
        String partNumber = partIfc.getPartNumber();
        if(partType.equals("车型")&&(partNumber.indexOf("5000990")<0)){
        	mSplitIfc.setDefaultUnit("辆");
        }else{
        	mSplitIfc.setDefaultUnit("个");
        }
		

		mSplitIfc.setPartType(partType);
		mSplitIfc.setPartModifyTime(partIfc.getModifyTime());
		
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
	 * 根据零部件唯一标识规则过滤零部件。唯一标识为：编号+大版本号+生命周期状态+路线+虚拟件。
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

//		System.out.println("hashMaphashMaphashMap22222222222222222222222222222222222222============"+hashMap);
		String returnString = "";
		String xnj = "N";
		if(baseValueIfc instanceof QMPartIfc)
		{
			QMPartIfc part1 = (QMPartIfc)baseValueIfc;
			//获取零件的虚拟件
			PartHelper partHelper = new PartHelper();

			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
			if(hashMap.get(part1.getBsoID())==null)
				routevec=RequestHelper.getRouteBranchs(part1, null);
			else
				routevec=RequestHelper.getRouteBranchs(part1, (ListRoutePartLinkInfo)hashMap.get(part1.getBsoID()));
			String routeAsString = "";
			String routeAllCode = "";
			String routeAssemStr = "";
			String lx_y = "";
			boolean colorFlag = false;
			if(routevec.size() != 0)
			{
				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = (String)routevec.elementAt(1);
				routeAssemStr = (String)routevec.elementAt(2);
				 String scolorFlag = (String)routevec.elementAt(6);
				 
				    if(scolorFlag.equals("1")){
				    	colorFlag = true;
				    }
				 lx_y =  (String)routevec.elementAt(7);
			}
			boolean islogic =partHelper.isLogical(part1,routeAsString,routeAssemStr);	
			 boolean virtualFlag=partHelper.getJFVirtualIdentity( part1, routeAsString, routeAssemStr,islogic,colorFlag);
			 if(virtualFlag){
				 xnj = "Y";
			 }

			//QMPartIfc part = (QMPartIfc)baseValueIfc;
			String partVersion = "";

			 if(partVersion.equals("")){
				 partVersion = PartHelper.getPartVersion(part1);
			 }
			//String partVersion = PartHelper.getPartVersion(part);
	    	String partnumber = part1.getPartNumber() + "/" + partVersion;
			returnString = partnumber + part1.getLifeCycleState().getDisplay()  + xnj;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				 if( materialSplitIfc.getVirtualFlag()){
					 xnj = "Y";
				 }
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute() + xnj;
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
		PartHelper partHelper = new PartHelper();
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("参数：filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // 不需拆分的零部件集合。
	  final List removePartList = new ArrayList();
	 // System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
	   //System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
		 String partVersion ="";

		 if(partVersion.equals("")){
			 partVersion = PartHelper.getPartVersion(partIfc);
		 }
		 String partnumber ="";

	     partnumber =getMaterialNumber(partIfc,partVersion);
		 
  	    
//  	  System.out.println("partVersioncacacalalalalalala1111111==="+partVersion);
	 //  System.out.println("partnumbercacacalalalalalala11111==="+partnumber);
	   List mSplitList = new ArrayList();
	   try {
	    // 根据零件号获取已拆分的物料。
	    mSplitList = getSplitedMSplit(partnumber,partVersion);
	   } catch (QMException e) 
	   {
		   
	    Object[] aobj = new Object[] { partIfc.getPartNumber() };
	    // "查找编号为*的零部件对应的物料时出错！"
	    logger.error(Messages.getString("Util.51", aobj) + e);
	    throw new ERPException(e, RESOURCE, "Util.51", aobj);
	   }
	 //  System.out.println("mSplitList==="+mSplitList.size());
	   // 相同唯一标识的物料只保留一个。
	   HashMap maHashMap = new HashMap(5);
	  // System.out.println("100304++++++++mSplitList.size()"+mSplitList.size()+"============pppp"+mSplitList);
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
	
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------是否需要拆分处理规则1.比较新的需要拆分的零部件与物料拆分表里的对应的物料的版本，如果需要拆分的零部件的版本低于物料拆分表里的记录，则不再拆分。");
	    }
	  //  System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
  
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
	       } else if(partHelper.compareVersion(tempMaterialSplit.getPartVersion(),materialSplitIfc.getPartVersion())==2)
	       {//此处应该是为了去掉重复数据，取高版本数据，好像也没什么用
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length+"ddddddddddd"+materialSplitIfc);
	        tempMaterialSplit = materialSplitIfc;
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
 	// System.out.println("tempMaterialSplits00000000000======="+tempMaterialSplit);

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
	    
	     // 是否需要拆分处理规则3.b.2。状态没有变化，废弃此记录。
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {//如果零件件状态都没变、需要比较路线是否发生变化
		 // System.out.println("100304++++++++tempMaterialSplit.getState()"+partIfc.getLifeCycleState().getDisplay());

	      Vector routevec = new Vector();
	      if(hashmap.get(partIfc.getBsoID())==null)
				routevec=RequestHelper.getRouteBranchs(partIfc, null);
			else
				routevec=RequestHelper.getRouteBranchs(partIfc, (ListRoutePartLinkInfo)hashmap.get(partIfc.getBsoID()));

	     // System.out.println("081000000000000000000000==="+routevec);
	      
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
	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
//	    	  System.out.println("100304++++++++routeAllCode222222222222222222222222222222222222-------------"+routeAllCode);
//	    	  System.out.println("100304++++++++tempMaterialSplit.getRoute()222222222222222222222222222222222222-------------"+tempMaterialSplit.getRoute());
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {	    	   
	        removePartList.add(partIfc);
	       }

//	
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
	  //CCBegin by chudaming 20100609 循环里remove的，实际上就删除了filterPartList奇数位的数据
	  filterPartList.removeAll(removePartList);
	  //System.out.println("100304++++++++filterPartList7777777777777777777-------------"+filterPartList);
	  //CCEnd by chudaming 20100609
	 } 
	 }
	

		/**
		 * 根据零件号获取顶级物料。
		 * 
		 * @param partNumber
		 *            零件号。
		 * @return 顶级物料集合。
		 * @throws QMException
		 */
		public List getRootMSplit(String partNumber,String partVersion) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query2 = new QMQuery("CDMaterialSplit");
			
		
			QueryCondition condition = new QueryCondition("partVersion",
					QueryCondition.EQUAL, partVersion);
			query2.addCondition(condition);
			query2.addAND();
		
			QueryCondition condition2 = new QueryCondition("materialNumber",
					QueryCondition.EQUAL, partNumber);
			query2.addCondition(condition2);
			query2.addAND();
			QueryCondition condition3 = new QueryCondition("rootFlag", true);
			query2.addCondition(condition3);
			List returnList=(List)pservice.findValueInfo(query2);
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - end       " + returnList); //$NON-NLS-1$
			}
			System.out.println("query2==="+query2);
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

	
		private List getSplitedMSplit(String partNumber,String partVersion) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("参数：partNumber==" + partNumber);
			}
//			System.out.println("partNumber1111111="+partNumber);
//			System.out.println("partVersion1111111="+partVersion);
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
//			int aa=1;//去掉原来的 final
			 QMQuery query = new QMQuery("CDMaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			//CCBegin by chudaming 20100610 dele 新发布车型所形成XML文件的零部件表中既存在‘N’标识，也存在‘U’标识，结构表中既存在‘N’标识，也存在‘O’标识
			//CCBegin by chudaming 20101216
			query.addAND();
			
			QueryCondition condition2 = new QueryCondition("partVersion",
					QueryCondition.EQUAL, partVersion);
			query.addCondition(condition2);
			query.addAND();
		
			QueryCondition condition1 = new QueryCondition("rootFlag",
					true);
			query.addCondition(condition1);
			//CCEnd by chudaming 20101216
			//??splited感觉路线为空则为false
//			QueryCondition condition4 = new QueryCondition("splited", true);
//			query.addCondition(condition4);
			//CCEnd by chudaming 20100610 dele
			List mSplitList=(List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - end"); //$NON-NLS-1$
			}
			//System.out.println("query2222="+query.getDebugSQL());
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
			final QMQuery query = new QMQuery("CDMaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			List mSplitList=(List)pservice.findValueInfo(query);
			//System.out.println("query======="+query.getDebugSQL());
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
			final QMQuery query = new QMQuery("CDMaterialSplit");
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
			QMQuery query = new QMQuery("CDMaterialStructure");
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
			final QMQuery query = new QMQuery("CDMaterialStructure");
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
			System.out.println("parentPartNumber="+parentPartNumber);
			System.out.println("childNumber="+childNumber);
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("CDMaterialStructure");
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
			final QMQuery query = new QMQuery("CDMaterialStructure");
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
		 * 将路线中的路线代码转化为List,并且根据属性文件的配置去掉路线中的特殊代码。
		 * 
		 * @param routeStr
		 * @return
		 */
		public List getRouteCodeList(String routeStr) {
			StringTokenizer routeTok = new StringTokenizer(routeStr, dashDelimiter);
			String routeCode = "";
	
			List routeCodeList = new ArrayList();
			//boolean isFirst = true;
			while (routeTok.hasMoreTokens()) {
				routeCode = routeTok.nextToken();
				//System.out.println("routeCode=============="+routeCode);
			//	System.out.println("isFirst=============="+isFirst);
				//if (isFirst ) {
					routeCodeList.add(routeCode);
				//	isFirst = false;
				//}
			}
			logger.debug("getRouteCodeList end routeCodeList is " + routeCodeList);
			return routeCodeList;
		}

		

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

			String str = "000" + Integer.toString(j);
			// CCEnd by dikefeng 20090217
			return partNumber + promulgateNotifyFlag
			+ str.substring(str.length() - 3);
		}

	

		 //解放刘家坤获取路线关联零件

		public Collection getPartByRouteListJF(TechnicsRouteListIfc list,HashMap hashmap)
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
			System.out.println("query = "+query.getDebugSQL());
			System.out.println("coll = "+coll);
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
	 	 *  物料编号生成规则
	 	 * @param QMPartIfc partIfc
	 	 * @param String partVersion
	 	 * @param String makeCodeNameStr
	 	 * @param String dashDelimiter
	 	 * @return boolean;
		 * @throws QMXMLException 
	 	 * @throws QMException
	 	 */
		public String getMaterialNumber(QMPartIfc partIfc,String partVersion  ) throws QMException{
	        String partNumber = partIfc.getPartNumber();
	        String partType = partIfc.getPartType().getDisplay().toString();
	        String materialNumber="";
	        //?	如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
	        //?	如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
	        if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))||partType.equals("车型")){
	        	materialNumber =  partIfc.getPartNumber();
	        	return materialNumber;
	        }
	        //驾驶室零件号包含“5000990”、 发动机零件号包含“1000940”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
	        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
	        	materialNumber =  partIfc.getPartNumber()  ;
	        	return materialNumber;
	        }
	        //零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
	        //“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
	        if(partNumber.indexOf("/")>=0){
	        	int a = partNumber.indexOf("/");
	        	//System.out.println("a="+a);
	        	String temp = partNumber.substring(a);
	        	//System.out.println("temp="+temp);
	        	//完全匹配型a
	        	String[] array1 = {"0","ZBT","AH","BQ"};
	        	//在中间型×a×
	        	String[] array2 = {"L01","J0","J1"};
	        	//在结尾a×
	        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
	        	//完全匹配型a
	        	for (int i1 = 0; i1 < array1.length; i1++){
	        		String str = array1[i1];
	        		if(str.equals(temp)){
	        			materialNumber =  partIfc.getPartNumber()  ;
	        			return materialNumber;
	        		}
	        	}
	        	//在中间型×a×
	        	for (int i1 = 0; i1 < array2.length; i1++){
	        		String str = array2[i1];
	        		if(temp.indexOf(str)>=0){
	        			materialNumber =  partIfc.getPartNumber()  ;
	        			return materialNumber;
	        		}
	        	}
	        	//在结尾a×
	        	for (int i1 = 0; i1 < array3.length; i1++){
	        		String str = array3[i1];
	        		if(temp.endsWith(str)){
	        			materialNumber =  partIfc.getPartNumber() ;
	        			return materialNumber;
	        		}
	        	}
	        }
	        //获取零部件的“ERP回导零部件号”属性，如果该属性“/”后面的字符串含有“-”，取“-”后面的字符串，
	        //取到的字符串和“L01”、“0”，“1”、“2”、“3”、“4”、“ZBT”、“L”、“AH”、“J0”、“J1”、“SF”、
	        //“BQ”、“ZC”比较，如果不属于以上列出的字符串，把ERP回导零部件号“/”后版本更新为新版本作为
	        //PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号。如果零部件的“ERP回导零部件号”
	        //属性为空，则按照规则○1形成物料编码。
	        //正常的物料编码
	        String str = getERPHD(partNumber+"/"+partVersion);
	        if(str!=""){
	        	if(partNumber.indexOf("/")>=0){
	        		int a = partNumber.indexOf("/");
	            	String temp = partNumber.substring(a);
	            	if(temp.indexOf("-")>=0){
	            		String[] array1 = {"0","ZBT","AH","BQ","L01","J0","J1","0","1","2","3","4","(L)","-SF","-ZC"};
	         
	            		int b = partNumber.indexOf("-");
	            		String temp1 = partNumber.substring(b);
	            		for (int i1 = 0; i1 < array1.length; i1++){
	                		String strtemp = array1[i1];
	                		if(strtemp.equals(temp1)){
	                			materialNumber =  partIfc.getPartNumber()  ;
	                			return materialNumber;
	                		}
	                	}
	            		//如果“/”后“-”之后字符串不再集合array1里
	            		//把ERP回导零部件号“/”后版本更新为新版本作为PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号
	            		//零件号=原来的erp回导零件号，将原来的版本替换为最新的版本
	            		String oldPartNumber = str.substring(0, a)+"/"+partVersion+"-"+temp1;
	            		materialNumber =  oldPartNumber  ;
	            		return materialNumber;
	            	}
	        	}
	        }
			materialNumber =  partIfc.getPartNumber() + "/" + partVersion  ;

	return materialNumber;
	}
//		获取erp回导属性
		private String getERPHD(String partNumber)  throws QMException{
			//Collection col = query("CDMaterialSplit","partNumber","=",partNumber);
	
			Collection col = null;
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			 QMQuery query = new QMQuery("CDMaterialSplit");
			 QueryCondition qc1=new QueryCondition("partNumber","=",partNumber);
             query.addCondition(qc1);
            // System.out.println("query="+query.getDebugSQL());
             col=pservice.findValueInfo(query);

			if(col==null)
				return "";
			  Iterator ite=col.iterator();
			  while(ite.hasNext()){
				  MaterialSplitIfc ifc=(MaterialSplitIfc)ite.next();
				  String str = ifc.getOptionCode();
				  if(str!=null&&str.length()!=0){
					  return str;
				  }
			  }
		     
		      return "";
		}
		//CCBegin SS8
		/**
		 * 根据路线，判断是否需要发布，
		 * 如果包含卡车厂路线则可以发布
		 * 装置图不向ERP发布，装置图判断规则：零件号第5、6位为0，且不是标准件，制造路线含“用”，装配路线为空
		 * 技术条件不向ERP发布，技术条件判断规则：零件名称含“技术条件”，制造路线含“用”，装配路线为空
		 * 路线单位（包括制造路线和装配路线）均不含在卡车厂路线单位范围内的零部件不向ERP发布。

		 * @return 零部件编号。
		 * @throws QMException
		 */
		public boolean ifPublish(QMPartIfc part,String makeStr,String assembStr) throws QMException {
			boolean flag = true;
			PartHelper helper = new PartHelper();
			//是否是装置图
			String partNum = part.getPartNumber();
//			System.out.println("makeStr="+makeStr);
//			System.out.println("assembStr="+assembStr);
			if(partNum.length()>=6){
    			
    			String subNumber = partNum.substring(4,6);
    		//	System.out.println("subNumber="+subNumber);
	    		String partType = part.getPartType().getDisplay();
	    		if(subNumber.equals("00")&&(!partType.equals("标准件"))&&makeStr.contains("用")&&assembStr.equals("")){
	    			//System.out.println("partNumberVer1="+partNumberVer);
	    			System.out.println("是装置图=");
	    			return false;
	    		}
    		}
			
			//是否是技术条件
			String partName = part.getPartName();
//			System.out.println("partName="+partName);
//			System.out.println("lx_y="+lx_y);
//			System.out.println("assembStr="+assembStr);
			if(partName.contains("技术条件")&&makeStr.contains("用")&&assembStr.equals("")){
    			//System.out.println("partNumberVer2="+partNumberVer);
				System.out.println("技术条件=");
    			return false;
    		}

		

			return true;
		
		}
		
	//CCEnd SS8
	
	}
