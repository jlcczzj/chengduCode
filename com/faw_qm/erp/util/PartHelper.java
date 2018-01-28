/**
 * 生成程序 PartHelper.java    1.0    2013/06/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 物料结构表格中列出了生命周期状态为编制中的子件 刘家坤 2014-03-05
 * SS2 平台问题：A004-2015-3135。状态改为代码管理方式维护可以添加的生命周期状态。 liunan 2015-6-10
 * SS3 生成xml文件中 要求零件号和物料号中成品号保持一致
 */
package com.faw_qm.erp.util;


import java.util.*;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.resource.util.ResourceExportImportHandler;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;

import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
//CCBegin SS2
import com.faw_qm.codemanage.ejb.service.CodingManageService;
//CCEnd SS2


/**
* <p>向ERP发数据用工具类</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2013</p>
* <p>Company: 一汽启明</p>
* @author 刘家坤
* @version 1.0
*/
public class PartHelper   
{
	 private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";
	 PartConfigSpecInfo partConfigSpecIfc = null;
	 //CCBegin SS2
	 //String[] State={"试制","生产准备","生产"};
	 String[] State=null;
	 //CCEnd SS2
	 private static HashMap routeHashMap;
    public PartHelper()  
    {     
    	routeHashMap = new HashMap();
    	//CCBegin SS2
    	try
    	{
    		CodingManageService service = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
    		Collection coll = service.findDirectClassificationByName("轴齿中心发布状态","轴齿中心代码配置");
    		State = new String[coll.size()];
    		Iterator iterator = coll.iterator();
    		int i=0;
    		while(iterator.hasNext())
    		{
    			State[i] = (iterator.next()).toString();
    			i++;
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
      
   
    /**
     * 过滤生命周期状况，不满足的向前追溯
     * 并以试制以及试制以后的状态才添加为过滤条件，如果制造视图最新版本零部件状态不满足条件
     * 需追溯该件制造视图之前满足条件的版本。
     * @param vec 零部件集合 或 结构件集合
     * @return
     * 
     * Vector
     * @throws QMException 
     */
    public QMPartIfc filterLifeState(QMPartIfc part1) throws QMException
    {
        QMPartIfc part = null;
        Object[] obj;
        Vector result = new Vector();
        if(part1 instanceof QMPartIfc)
        { 
        	 part = part1;
               
                String state = part.getLifeCycleState().getDisplay();

                for(int j=0;j<State.length;j++)
                {
                	// System.out.println("state = "+state);
                    if(state.contains(State[j]))
                    {                       
                    	 return part;
                    }
                }

              // System.out.println("最终版本 = "+part);
                //如果当前件不符合条件，则追溯前一个版本
               
                	part=getPart(part1);
                
      
        }
        return part;
    }
    /**
     * 根据生命周期状态追溯当前大版本零部件
     * @param curPart
     * @return
     * 
     * QMPartIfc
     * @throws QMException 
     */
    public QMPartIfc getPart(QMPartIfc curPart) throws QMException
    {
        String preID = curPart.getPredecessorID();
        PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
       
       // System.out.println("curPart.getPredecessorID() = "+curPart.getPredecessorID());
        if(preID==null||preID.length()==0)
        {
        	return null;
        }
        QMPartIfc prePart = (QMPartIfc)persistService.refreshInfo(preID, false);
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              //  System.out.println("==================根据生命周期状态追溯当前大版本零部件 state = "+state);                
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================根据生命周期状态追溯当前大版本零部件 prePart = "+prePart); 
            return getPart(prePart);
//        }
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
//			System.out.println("linkkkkkkkkkkkkkkkkkkkkkkkkkkkkk======="+link);
			//如果缓存了当前零件的路线信息，则直接返回
			Vector objs = new Vector();
			if(routeHashMap.containsKey(part))
			{
				objs = (Vector)routeHashMap.get(part);
				return objs;
			}
			//获得当前有效的路线关联
			TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
			//这里上来就去执行搜索没有必要questions
			Collection c = getRoutesAndLinks(part);
			//System.out.println("cssssssssssss=========="+c);
			Iterator i = c.iterator();
			//这里只可能有一个
			ListRoutePartLinkInfo info = null;

			if(!i.hasNext() && link == null){
				return new Vector();
			}
		
			if(link == null)
			{
				 info = (ListRoutePartLinkInfo)i.next();
			
//				System.out.println("info1info1info1====="+info1);

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
//					}
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
	
	private static Collection getRoutesAndLinks(QMPartIfc part)
	throws QMException
	{
		
//		CodingIfc code = ResourceExportImportHandler.importCode("取消","更改标记");
//		String qx = "";
//		if(code!=null)
//			qx = code.getBsoID();
//		System.out.println("qx="+qx);
		 
		PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("ListRoutePartLink");
		int i = query.appendBso("TechnicsRouteList", false);
		QueryCondition qc = new QueryCondition("leftBsoID", "bsoID");
	    query.addCondition(0, i, qc);
		QueryCondition qc1 = new QueryCondition("rightBsoID", "=", part.getMasterBsoID());
		query.addAND();
		query.addCondition(qc1);
		QueryCondition qc2 = new QueryCondition("lifeCycleState" ,"=","RELEASED");
		query.addAND();
		query.addCondition(i,qc2);
		query.addOrderBy("modifyTime",true);
		return pservice.findValueInfo(query, false);
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
	/**
     * 判断字符串是否全是大写
     * @param word 字符串
     * @return boolean
     */
	public static boolean isUpperCase(String word){
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (!Character.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}
	 //CCBegin SS1
	/**
	*通过masterBsoID获得零部件值对象
	*@param masterid
	*@return QMPartInfo
	*@throws QMException
	*/
	public QMPartInfo getPartInfoByMasterBsoID(String masterid)
			throws QMException {
		try {
			PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
			Collection col = vcservice.allVersionsOf((QMPartMasterIfc) pService.refreshInfo(masterid));
			if (col != null && col.iterator().hasNext()) {
				return (QMPartInfo) col.iterator().next();
			}
		} catch (QMException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	 //CCEnd SS1
	//CCBegin SS3
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
	public String getPartNumber(QMPartIfc partIfc,String folder,String lsbb,String jsbb )throws QMException{
		String materialNumber = "";
		if (lsbb == null || lsbb.length() == 0) {
			// 艺准通知书和零部件关联表 “历史版本”属性值为空：
			if (folder.contains("变速箱零部件")
//					
//					|| folder.contains("车桥有版本零部件")) {
				|| folder.contains("桥齿零部件")) {
//				CCEnd SS3
				// 1) 零部件所在资料夹节点包含 “变速箱零部件”或者
				// “车桥有版本零部件”，并且“技术中心源版本”有值，
				// 则输出：<零件编号>+“/”+“技术中心源版本”+“-”+路线简称；
				// 如果“技术中心源版本”无值，则输出：
				// <零件编号>+“/”+“PDM部件版本”+“-”+路线简称；
				if (jsbb != null && jsbb.length() > 0) {
					materialNumber = partIfc.getPartNumber()
							+ "/" + jsbb ;
				} else {
					
					materialNumber = partIfc.getPartNumber() 
							+ "/" + partIfc.getVersionID();
						
				}
//			CCBegin SS3
//			} else if (folder.contains("轴齿中心")
//					|| folder.contains("车桥无版本零部件")) {
			} else if (folder.contains("轴齿中心")
					&& folder.contains("轿车零部件")) {
//				CCEnd SS3
				// 2) 零部件所在资料夹节点包含为“轴齿中心”，则输出：
				// <零件编号>+“/” +“-”+路线简称；
				// 3) 零部件所在资料夹包含“车桥无版本零部件”节点，则输出：
				// <零件编号>+“/” +“-”+路线简称；
				materialNumber =  partIfc.getPartNumber();
				
			} else if (!folder.contains("轴齿中心")
//					CCBegin SS3
//					&& !folder.contains("车桥无版本零部件")
					&& !folder.contains("轿车零部件")
//					CCEnd SS3
					&& !folder.contains("变速箱零部件")
//					CCBegin SS3
//					&& !folder.contains("车桥有版本零部件")) {
					&& !folder.contains("桥齿零部件")) {
//				CCEnd SS3
				// 4) 零部件所在资料夹节点不包含“车桥无版本零部件”、
				// “轴齿中心”、“变速箱零部件”和“车桥有版本零部件”，
				// 则输出：<零件编号>+“/”+“技术中心源版本”+“-”+路线简称；
				// 如果“技术中心源版本”无值，则输出：
				// <零件编号>+“/”+“PDM部件版本”+“-”+路线简称；
				if (jsbb != null && jsbb.length() > 0) {
					materialNumber = partIfc.getPartNumber()
							+ "/" + jsbb;
					
				} else {
				
					materialNumber = 
							partIfc.getPartNumber();
				}
			}

		} else if (lsbb.equals("历史")) {
			// ? 历史版本属性值为“历史”，则输出：<零件编号>+ “-”+路线简称；
			materialNumber =  partIfc.getPartNumber()
					;
			
		} else {
			// 艺准通知书和零部件关联表 “历史版本”属性值为“A”等大写字母，
			// 则输出：<零件编号>+“/”+“历史版本”+“-”+路线简称；
		
				materialNumber = partIfc.getPartNumber()
				 + "/" + lsbb;
	
		}
		return materialNumber;
	}
	public  String getPartVersion(QMPartIfc partIfc){
		// 技术中心源版本
		String jsbb;
		String partVersion = "";
		try {
			jsbb = getPartIBA(partIfc, "发布源版本","sourceVersion");
	
			//1)	零部件“技术中心源版本”属性有值，则零部件版本属性为技术中心源版本。
			//2)	零部件“技术中心源版本”属性为空，则零部件版本属性为PDM部件版本。
			if (jsbb != null && jsbb.length() > 0) {
				int index = jsbb.indexOf(".");
				if(index>=0){
					jsbb = jsbb.substring(0,index);
				}
				partVersion = jsbb ;	
			} else {
				partVersion = partIfc.getVersionID();
				
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return partVersion;
	}
	/**
	 * 根据给定的事物特性持有者,获得特定iba属性值。 return String 为该iba属性的值。
	 * 
	 * @throws QMXMLException
	 */
	public String getPartIBA(QMPartIfc partIfc, String ibaDisplayName,String sName)
	throws QMXMLException {

		String ibaValue = "";
		final HashMap nameAndValue = new HashMap();
		try {
			IBAValueService ibservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
			partIfc = (QMPartIfc)ibservice.refreshAttributeContainerWithoutConstraints(partIfc);
		} catch (QMException e) {
			// "刷新属性容器时出错！"
			//logger.error(Messages.getString("Util.7"), e); //$NON-NLS-1$
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
					.equals(ibaDisplayName.trim())&&definition.getName().trim()
					.equals(sName.trim())) {
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
							//logger.error(Messages.getString("Util.8"), e); //$NON-NLS-1$
							throw new QMXMLException(e);
						} catch (IncompatibleUnitsException e) {
							// "出现不兼容单位！"
							//logger.error(Messages.getString("Util.9"), e); //$NON-NLS-1$
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
	
		return ibaValue;
	}
	 /**
     *  获取筛选的唯一标识。
     * @param part 零部件。
     * @return String 筛选的唯一标识。
     */
    public  String getPartNumber(final QMPartMasterIfc masterpart)throws QMException
    {
    	QMPartIfc part=getPartInfoByMasterBsoID(masterpart.getBsoID());
        MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        Vector routevec = RequestHelper.getRouteBranchs(part, null);
        String routeAsString = "";
        String routeAllCode = "";
        String routeAssemStr = "";
        String lsbb = "";
        
        if(routevec.size() != 0)
        {
            routeAsString = (String)routevec.elementAt(0);
            routeAllCode = (String)routevec.elementAt(1);
            lsbb = (String)routevec.elementAt(6);
        }
        String[] aa = new String[2];
//		 获取当前零件的资料夹
        PartHelper partHelper =  new PartHelper();
		String folder = part.getLocation();
		// 技术中心源版本
		String jsbb = getPartVersion(part);

       String partnumber = partHelper.getPartNumber(part,folder,lsbb,jsbb);
        return partnumber;
    }
//	CCEnd SS3
    /**
     * 调试信息
     */

          private static final boolean VERBOSESYSTEM = (RemoteProperty.getProperty(
	        "com.faw_qm.bomchange.verbose","true")).equals("true");
}

