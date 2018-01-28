/**
 * 生成程序MaterialDataPublisher.java	1.0              2007-9-28
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 零部件生命周期状态为编制中的零件也可以发布到ERP 刘家坤 2014-03-03
 * SS2 物料结构表格中列出了生命周期状态为编制中的子件 刘家坤 2014-03-05
 * SS3 生成xml文件中 要求零件号和物料号中成品号保持一致
 * SS4 新建一个集合用来存储 零部件号和成品号
 * SS5 过滤的集合包含路线中的零件以及子件
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.ejb.service.PromulgateNotifyService;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.FilterPartIfc;
import com.faw_qm.erp.model.FilterPartInfo;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.model.MaterialStructureIfc;
import com.faw_qm.erp.model.MaterialStructureInfo;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.erp.model.*;

//
/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class MaterialDataPublisher extends BaseDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MaterialDataPublisher.class);

    /**
     * 物料拆分的管理域。
     */
    private static String mSplitDefaultDomainName = (String) RemoteProperty
            .getProperty("materialSplitDefaultDomain", "System");

    /**
     * 存放标记为修改和重发的物料对应的filterPart。
     * key是过滤的零部件filterPart的编号，value是过滤的零部件值对象filterPart。
     */
    private HashMap filterPartMap = new HashMap();

    /**
     * 存放已经设置过发布标记的XMLMaterialSplit。
     * key是物料的物料号，value是物料值对象的XML形式。
     */
    private HashMap hasSetSplitPubTypeMap = new HashMap();

    /**
     * 存放已经设置过发布标记的XMLMaterialStructure。
     * key是物料结构的值对象的BsoID，value是物料结构值对象的XML形式。
     */
    private final HashMap hasSetStrutPublishType = new HashMap();

    /**
     * 存放物料的顶级物料。
     * key是零部件Master的BsoID，value是该零部件拆分物料的顶级物料List。
     */
    private final HashMap rootMatSplitMap = new HashMap();

    /**
     * 存放发布的物料XMLMaterialSplitIfc。
     */
    private List xmlMatSplitList = new ArrayList();

    /**
     * 存放发布的物料结构XMLMaterialStructureIfc。
     */
    private List xmlMatStructList = new ArrayList();

    /**
     * 发布数据的来源类型。
     */
    private String sourceType = "";

    /**
     * 分号分隔符。用于分隔路线。
     */
    private String semicolonDelimiter = ";";
    /**
     * 破折号分隔符。用于分隔路线代码。
     */
    private String dashDelimiter = "-";
    
    /**
     * 存储，成品编号 和 partnumber
     */
    private final HashMap partNumberMap = new HashMap();
    

    /**
     * @throws QMXMLException
     */
    protected synchronized final void invoke() throws Exception
    {
    	try{
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke() - start");   
        }
        filterMaterials();
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke() - end");
        }}catch (Exception e){
        	e.printStackTrace();
        	throw e;
        	
        }
    }

    /**
     * 保存筛选结果。
     * @throws QMXMLException
     */
    protected final void saveFilterPart() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - start");
        }
        //设置好的要保存的筛选结果的集合。
        PersistService pservice=null;
        try
        {
        pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	throw new QMXMLException(ex);
        }
       
        List filterPartList = new ArrayList();
        HashMap hasUpdatePartMap = new HashMap();
        //CCBegin by dikefeng 20100426,如果需要保存的xmlPartList集合为空，不再循环
        if(xmlPartList==null||xmlPartList.size()==0)
        	return;
        //CCEnd by dikefeng 20100426
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            final QMXMLMaterialSplit xmlMatSplit = (QMXMLMaterialSplit) xmlPartList
                    .get(i);
            FilterPartIfc filterPartIfc = (FilterPartIfc) filterPartMap
                    .get(xmlMatSplit.getPartNumber());
            String filterPartIdentity = xmlMatSplit.getPartNumber()
                    + xmlMatSplit.getPartVersionID()+ xmlMatSplit.getRoute();;
            if(!hasUpdatePartMap.containsKey(filterPartIdentity))
            {
                if(filterPartIfc != null
                        && xmlMatSplit.getPublishType().equals("Z"))
                {
                    //更新状态。
                    filterPartIfc.setState(xmlMatSplit.getState());
                    filterPartIfc.setRoute(xmlMatSplit.getRoute());
                    filterPartList.add(filterPartIfc);
                    hasUpdatePartMap.put(filterPartIdentity, filterPartIfc);
                    continue;
                }
                filterPartIfc = new FilterPartInfo();
                if(publishSourseObject instanceof PromulgateNotifyIfc)
                {
                    filterPartIfc
                            .setNoticeNumber(((PromulgateNotifyIfc) publishSourseObject)
                                    .getPromulgateNotifyNumber());
                    filterPartIfc.setNoticeType("采用");
                }
                else if(publishSourseObject instanceof QMChangeRequestIfc)
                {
                    filterPartIfc
                            .setNoticeNumber(((QMChangeRequestIfc) publishSourseObject)
                                    .getNumber());
                    filterPartIfc.setNoticeType("变更");
                }
                else if(publishSourseObject instanceof ManagedBaselineIfc)
                {
                	filterPartIfc.setNoticeNumber(((ManagedBaselineIfc)publishSourseObject).getBaselineName() + ((ManagedBaselineIfc)publishSourseObject).getBaselineNumber());
                    String nu=getXmlName();
                    if(nu.equalsIgnoreCase("caiyong"))
                	{
                    	filterPartIfc.setNoticeType("采用通知");
                	}
                	else if(nu.equalsIgnoreCase("biangeng")) 
                	{
                		filterPartIfc.setNoticeType("变更通知");
                	}
                	else
                	{
                		filterPartIfc.setNoticeType(nu);
                	}
                }
                else if(publishSourseObject instanceof TechnicsRouteListIfc)
                {
                    filterPartIfc.setNoticeNumber(((TechnicsRouteListIfc)publishSourseObject).getRouteListName() + ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber());
                    String nu = getXmlName();
                    if(nu.equalsIgnoreCase("caiyong"))
                    {
                        filterPartIfc.setNoticeType("采用通知");
                    }
                    else if(nu.equalsIgnoreCase("biangeng"))
                    {
                        filterPartIfc.setNoticeType("变更通知");
                    }
                    else
                    {
                        filterPartIfc.setNoticeType(nu);
                    }
                }

                filterPartIfc.setPartNumber(xmlMatSplit.getPartNumber());
                filterPartIfc.setState(xmlMatSplit.getState());
                filterPartIfc.setVersionValue(xmlMatSplit.getPartVersionID());
                filterPartIfc.setRoute(xmlMatSplit.getRoute());
                filterPartList.add(filterPartIfc);
                hasUpdatePartMap.put(filterPartIdentity, filterPartIfc);
                if(logger.isDebugEnabled())
                {
                    logger.debug(filterPartIfc.getNoticeNumber());
                    logger.debug(filterPartIfc.getNoticeType());
                    logger.debug(filterPartIfc.getPartNumber());
                    logger.debug(filterPartIfc.getState());
                    logger.debug(filterPartIfc.getVersionValue());
                }
            }
        }
        for (int i = 0;  i < filterPartList.size(); i++)
        {
            try
            {
                pservice.saveValueInfo((BaseValueIfc)filterPartList.get(i));
            }
            catch (QMException e)
            {
                //"保存对象*时出错！"
                logger.error(Messages.getString("Util.16",
                        new Object[]{((FilterPartIfc) filterPartList.get(i))
                                .getIdentity()})
                        + e);
                throw new QMXMLException(e);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 执行物料的筛选功能。并将过滤后物料结果集合设置给基本发布器，为保存筛选结果做准备。
     * 筛选过程中将物料的信息和结构关联的信息设置到QMXMLMaterial和QMXMLMaterialStructure中，
     * 并操作这两个对象完成逻辑。
     *
     * 筛选结果表记录保存规则：
     * 1.先根据筛选的唯一标识过滤零部件集合，重复的记录被筛选掉，不再发布。
     * 2.零部件号和版本号相同的数据只在筛选结果表中存在一条记录，状态变化，修改其它信息。
     *
     * 注：不对结构进行递归处理，只处理第一级结构，递归部分已经在数据提取时完成。
     * @throws QMXMLException
     */
    private final void filterMaterials() throws Exception
    {

        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - start"); //$NON-NLS-1$
        }
        //通过采用通知书或更改采用通知书获取关联的零部件。
        List partList = new ArrayList();
        List partList1 = new ArrayList();
     
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("PromulgateNotifyService");
            if(publishSourseObject instanceof PromulgateNotifyIfc)
            {
            	partList = (List)pnservice.getPartsByProId((PromulgateNotifyIfc)publishSourseObject);
                sourceType = "采用通知书";
            }
          
            else if(publishSourseObject instanceof TechnicsRouteListIfc)
            {
            	//CCBegin by chudaming 20100920  dele
//                partList = (List)msservice.getPartByRouteList((TechnicsRouteListIfc)publishSourseObject);
            	//CCEnd by chudaming 20100920  dele
            	//CCBegin by chudaming 20100920
            	partList = (List)getPartByRouteList((TechnicsRouteListIfc)publishSourseObject);
            	
                String nu = getXmlName();
              
                sourceType = "";
                
            }
 
            logger.debug("publishSourseObject222222==============="
                    + publishSourseObject);
        }
        catch (Exception e)
        {
            //"通过采用通知书或更改采用通知书获取关联的零部件失败！"
            logger.error(Messages.getString("Util.22"), e);
            throw e;
        }
//      CCBegin SS5
//      刘家坤20140102 获得一级子件
        HashMap partTempNew = new HashMap();
        HashMap partTemp = new HashMap();
        
       // MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
		Iterator it = partList.iterator();
		PartHelper partHelper = new PartHelper();
		while (it.hasNext()) {
			QMPartIfc part = (QMPartIfc) it.next();
//			System.out.println("11111111111111111111111111111part1=============="+part);
//			System.out.println("collection000000000000000000000000000=============="+collection);
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
							partList1.add(subpart);
//							System.out.println("collection11111111111111111111111111111=============="+collection);
//							System.out.println("subpart=============="+subpart);
						}
					}
					
				}
				
				
			}
			//System.out.println("11111111111111111111111111111part2=============="+part);
			if(!partTempNew.containsKey(part.getBsoID())){
				partList1.add(part);
				partTempNew.put(part.getBsoID(), part);
			}
			
			
		//	System.out.println("collection222222222222222222222222=============="+collection);
		}
		
        System.out.println("partList1********************"+partList1);
        System.out.println("partList********************"+partList1.size());
        //"关联*条零部件信息。"
        logger.fatal(sourceType
                + Messages.getString("Util.61", new Object[]{String
                        .valueOf(partList.size())}));
        if(logger.isDebugEnabled())
        {
            logger.debug("partList==" + partList);
        }
        //刘家坤20140108 应该不需要刷新最新版本
       // partList = getLatestParts(partList);
        //根据筛选结果表记录保存规则处理零部件。并将筛选后的QMXMLMaterialSplit集合存入xmlMaterialSplitList中。
        //根据物料处理规则处理物料。并将标记为修改的零部件对应的filterPart放到filterPartMap中。
        //根据结构处理规则处理结构，并将筛选后的QMXMLStructure集合存入xmlStructureList中。
        try
        {
        	//filterMaterials(partList);
            filterMaterials(partList1);
        }//CCEnd SS5
        catch (Exception e)
        {
            //"处理物料的发布数据时出错！"
            logger.error(Messages.getString("Util.68"), e);
            throw e;
        }
        System.out.println("xmlMatSplitList********************"+xmlMatSplitList.size());
        System.out.println("filterPartMap********************"+filterPartMap.size());
        System.out.println("xmlMatStructList********************"+xmlMatStructList.size());
        if(logger.isDebugEnabled())
        {
            logger.debug("物料处理规则后xmlPartList＝＝" + xmlMatSplitList);
            logger.debug("物料处理规则后updatePartMap＝＝" + filterPartMap);
            logger.debug("结构处理规则过滤后xmlStructureList＝＝" + xmlMatStructList);  
        }
        
        //将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
        setDataRecord(xmlMatSplitList, xmlMatStructList);
        //将过滤后零部件结果集合设置给基本发布器，为保存筛选结果做准备。
        BaseDataPublisher.xmlPartList = xmlMatSplitList;
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - end"); //$NON-NLS-1$
        }
    }
	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		PartHelper partHelper =  new PartHelper();
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
//		System.out.println("query**********************="+query.getDebugSQL());
//		System.out.println("coll***********************="+coll);
//		System.out.println("list***********************="+list);
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			//System.out.println("linkInfoaaaa***********************="+linkInfo);
			Collection cc = getPartByListRoutepart(linkInfo);
			//System.out.println("cc***********************="+cc);
			QMPartIfc partInfo = null;
			Iterator ii = cc.iterator();
			 while (ii.hasNext()) {
				 partInfo = (QMPartIfc)ii.next();
				 //CCBegin SS1
				 partInfo=partHelper.filterLifeState(partInfo);
				 if(partInfo==null)
			    	 continue;
				//CCEnd SS1
				// System.out.println("101010110111171777777ccccchhhhuuuu11111111partInfo==11111111="+partInfo.getPartNumber());

				 Vector aaa=new Vector();   
				 aaa=RequestHelper.getRouteBranchs(partInfo, null);
				 //CCBegin by chudaming 20100920
				 QMQuery qmquerykk1 = new QMQuery("FilterPart");
	 			 QueryCondition condition121 = new QueryCondition("versionValue", "=",
	 					partInfo.getVersionID());
	 			qmquerykk1.addCondition(condition121);
	 			qmquerykk1.addAND();
	 			qmquerykk1.addCondition(new QueryCondition("partNumber", "=",
	 					partInfo.getPartNumber()));
	 			//CCBegin by chudaming 20101021 保证和最后一次的FilterPart记录进行对比
	 			qmquerykk1.addOrderBy("createTime",true);
	 			
	 			//CCEnd by chudaming 20101021 保证和最后一次的FilterPart记录进行对比
	 			//CCBegin by chudaming 20101022
	 			//System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+aaa);
//	 			System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+aaa.size());
	 	          Collection col1 = ps.findValueInfo(qmquerykk1, false);
	 	         
	 	        //System.out.println("col12014===="+col1);
                 if(col1.size()>0){
    	 	         for (Iterator ii22 = col1.iterator();ii22.hasNext();) {
    		 	        	FilterPartIfc   aaaaaa=(FilterPartIfc)ii22.next();
   		 	        	//System.out.println("aaaaaa2014===="+aaaaaa.getRoute());
    		 	        	//System.out.println("aaa.get(1).toString()aaa.get(1).toString()888888888===="+aaa.get(1).toString());
   		 	       // System.out.println("aaa2014"+aaa);	
   		 	        	if(aaa.size()!=0&&aaaaaa.getRoute()!=null){
    		 	        		//CCBegin by chudaming 20101017
    		 	        	
    		 	        	String cgbs=(String)aaa.get(7);
    		 	        	//System.out.println("cgbs2014"+cgbs);
    		 	        	String routeAllCode = aaa.get(1).toString();
    		 	        	if (cgbs != null&&cgbs != "") {
    							if (cgbs.equals("X")) {
    								routeAllCode = "毛坯-" + routeAllCode;
    							} else if (cgbs.equals("X-1")) {
    								routeAllCode = "半成品-" + routeAllCode;
    							}
    						}
    		 	        	//System.out.println("routeAllCode2014"+routeAllCode);
    		 	        	 if(!(aaaaaa.getRoute().equals(routeAllCode))){
    		 	        		// System.out.println("1021111111111111111111111112012111111111====");
    		 	        		c.add(partInfo);
    		 	        	 }
    		 	        	}else if(aaa.size()!=0&&aaaaaa.getRoute()==null){
    		 	        		//System.out.println("ddddddddddddddddddddddddd===");
    		 	        			c.add(partInfo);
//    		 	        		
    		 	        	}else if(aaa.size()==0&&aaaaaa.getRoute()!=null){
    		 	        		c.add(partInfo);
    		 	        	}
    		 	        	break;
    		 	        	
    		 	         }
                 }else{//刘家坤20140108 如果filter中没有查到则加进集合中去
                	 c.add(partInfo);
                 }

	 	         
				
				 
			 }
//				for(Iterator ii = cc.iterator(); ii.hasNext(); c.add(partInfo))
//					partInfo = (QMPartIfc)ii.next();

		}
		//System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee222222222222222222===="+c);
		return c;
	}
//	liujiakun 20131224 这里getCurrentEffctive有错误屏蔽
	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		QMPartInfo part = (QMPartInfo)ps.refreshInfo(linkInfo.getRightBsoID());
		QMQuery query = new QMQuery("QMPart");
		QueryCondition qc = new QueryCondition("bsoID", "=", part.getBsoID());
		query.addCondition(qc);
		// 做a件A版本，发a版本bom，做a件A版本路线，修订a件，发a件的B版本，再发a版本的艺准，要求带着B版本的结构拆分。
		//刘家坤获取路线关联零件版本，轴齿路线关联零件具体版本 20131227
		String partVersion = part.getVersionID();
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
//	          System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1);
//	          System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1.size());
	          if(col1!=null){
	        	  if(col1.size()>0){
 	        	 while (iter22.hasNext()) {
 	        		ifc=(FilterPartInfo)iter22.next();
// 	        		a.add(ifc);
 	        	 }
 	        	 }
                 }
	          //System.out.println("ssssssss===");
	          if(ifc!=null){
	        	//  System.out.println("ssssssss==ffffffffffffffffffff=");
	          if(partVersion
	     	         .compareTo(
	     	        		ifc.getVersionValue()) < 0)
	          {
	        	  //System.out.println("ssssssss==gggggggggggggggggggggggggg=");
	        	  QueryCondition qc1 = new QueryCondition("versionID", "=", ifc.getVersionValue());
	        	 // System.out.println("ssssssss==hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh=");
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
		//CCEnd by chudaming 20101019 for 做a件A版本，发a版本bom，做a件A版本路线，修订a件，发a件的B版本，再发a版本的艺准，要求带着B版本的结构拆分。
		QueryCondition qc2 = new QueryCondition("iterationIfLatest", true);
		query.addAND();
		query.addCondition(qc2);
		//System.out.println("queryVVVVVVVVVVVVVV="+query.getDebugSQL());
		return ps.findValueInfo(query, false);
		}catch(QMException e){
			e.printStackTrace();
			throw e;
		}
	}
	
    /**
     * 根据基线得到受基线管理的零部件集合
     * @param baseline 基线
     * @param coll 要发布的零部件的集合
     * @return List 受基线管理的零部件的集合
     * @throws QMException
     */
    private List getpartsByBaseline(ManagedBaselineIfc baseline,Collection partIDs) throws QMException
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
			   int j = query.appendBso("BaselineLink", false);
			   int m = query.appendBso("QMPartMaster", false);
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
			   //query.addCondition(i, new QueryCondition("rightBsoID", "IN", masterids));
			   query.addAND();
			   query.addCondition(m, i, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(j, new QueryCondition("rightBsoID", "=", baseline.getBsoID()));
			   query.addAND();
			   query.addCondition(0, j, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
			   coll1 = pservice.findValueInfo(query);
			   return coll1;

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

	/**
     * 根据筛选结果表记录保存规则处理零部件。并将筛选后的QMXMLMaterialSplit集合存入xmlMatSplitList中。
     * 根据物料处理规则处理物料。并将标记为修改的零部件对应的filterPart放到filterPartMap中。
     * 根据结构处理规则处理结构，并将筛选后的QMXMLStructure集合存入xmlStructureList中。
     * @param partList 待筛选的零部件集合。
     * @return 筛选后的QMXMLMaterialSplit集合。
     * @throws QMXMLException
     */
    private final void filterMaterials(final List partList) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials(List) - start"); //$NON-NLS-1$
        }
        //零部件的唯一标识
        String partIdentity;
        //零部件的值对象
        QMPartIfc partIfc;
        //存放通过唯一标识筛选后的零部件。key是零部件的唯一标识，value是零部件的值对象。
        HashMap partsMap = new HashMap();
       // System.out.println("这里即将要处理的零部件数量为："+partList.size());
        for (int i = 0; i < partList.size(); i++)
        {
            partIfc = (QMPartIfc) partList.get(i);
            //1 筛选结果表记录保存规则1：先根据筛选的唯一标识过滤零部件集合，重复的记录被筛选掉，不再发布。
            //比如在更改采用通知书可以记录同一个版本的不同版序，这样就需要过滤了，将相同版本的只发一次
            //获取零部件的唯一标识
            //CCBegin SS3
            String[] aa = new String[2];
            aa = getPartIdentity(partIfc);
            partIdentity=aa[0];
            String partnumber = aa[1];
            //CCEnd SS3
           // if(partIfc.getPartNumber().equals("1701110-A4K")){
            System.out.println("partIdentity********************"+partIdentity);
            System.out.println("partsMap********************"+partsMap);
            System.out.println("partIfc********************"+partIfc.getPartNumber());
      //  }
            if(!partsMap.containsKey(partIdentity))
            {
            	
                //2 获取发布标识
            	//CCBegin by dikefeng 20100423,因为通过记录已经记录了本次拆分的和要发布的零部件集合，所以这里就不再去判断发布类型
//                String publishType = getPublishType(partIfc);
//                if(publishType != null)
//                {
                    partsMap.put(partIdentity, partIfc);
                    //设置下级物料和物料结构的发布数据
                    setSubMaterial(partIfc, null, partsMap,partnumber);
//                }
      
            }
        }
        //"需要发布*条零部件信息。"
        logger.fatal(sourceType
                + Messages.getString("Util.62", new Object[]{String
                        .valueOf(partsMap.values().size())}));
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 根据物料处理规则处理物料。
     *
     * 物料处理规则：查询筛选结果表中数据
     * 1.表中数据不存在，标记为新增N。
     * 2.表中数据存在，检查版本，a版本变化，标记为修改U，对结构进行处理；
     *                        b版本没有变化，1状态变化，标记为重发Z，不处理结构；
     *                                     2状态没有变化，不处理。
     * 注意：方法中修改了filterPartMap。
     * @param partIfc 要检查的零部件的值对象
     * @return 该零部件拆分后的物料发布标记
     * @throws QMXMLException
     */
    private String getPublishType(QMPartIfc partIfc) throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPublishType(QMPartIfc) - start");
        }
        //零部件拆分后的物料发布标记
        //CCBegin by dikefeng 20090210
        //经20090205下午与孙玉红和杜建光确认，发布物料数据时，无论是否已经发布过，都进行发布，即在PDM系统中不进行过滤
        //再20090209下午经与杜建光进行确认，ERP接收PDM数据时，并不读取PDM发布数据时的更改标记，所以决定对更改标记默认
        //值进行修改，以便方便的修改后需程序
        String publishType =null;
        //CCEnd by dikefeng 20090210
        List tempPartList = new ArrayList();
        //过滤发布零部件值对象
        FilterPartIfc filterPartIfc = null;
        List filterPartList = new ArrayList();
        //1 用编号作为查询条件，查找是否有符合条件的FilterPart。
        try
        {
            filterPartList = getFilterPart(partIfc.getPartNumber());
        }
        catch (QMException e)
        {
            //"查找编号为*的FilterPart时出错！"
            logger.error(Messages.getString("Util.15", new Object[]{partIfc
                    .getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterPartList==" + filterPartList);
        }
        //2 比较新数据与filterpart的版本，如果新的版本低于旧的则不再发布。
        FilterPartIfc tempFilterPart = null;
        if(filterPartList != null && filterPartList.size() > 0)
        {
            for (int j = 0; j < filterPartList.size(); j++)
            {
                filterPartIfc = (FilterPartIfc) filterPartList.get(j);
                if(partIfc.getVersionID().compareTo(
                        filterPartIfc.getVersionValue()) < 0)
                {
                    tempPartList.add(partIfc);
                    break;
                }
                else
                {
                    if(tempFilterPart == null)
                    {
                        tempFilterPart = filterPartIfc;
                    }
                    else if(tempFilterPart.getVersionValue().compareTo(
                            filterPartIfc.getVersionValue()) < 0)
                    {
                        tempFilterPart = filterPartIfc;
                    }
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("xmlPart.getPartNumber()==" + partIfc.getPartNumber());
        }
        //3 判断发布标记
        //物料处理规则1。表中数据不存在，标记为新增N。
        if(tempFilterPart == null)
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------物料处理规则1");
            }
//            System.out.println("tempPartListtempPartList========"+tempPartList);
//            System.out.println("tempPartListtempPartList========"+partIfc.getPartNumber());
            if(!tempPartList.contains(partIfc))
            {
                publishType = "N";
            }
        }
        //物料处理规则2。表中数据存在，检查版本。
        else
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------物料处理规则2");
            }
            //物料处理规则2.b。版本没有变化。
            if(partIfc.getVersionID().equals(tempFilterPart.getVersionValue()))
            {
                if(logger.isDebugEnabled())
                    logger.debug("---------物料处理规则2.b");
                if(!partIfc.getLifeCycleState().getDisplay().equals(tempFilterPart.getState()))
                {
                    if(logger.isDebugEnabled())
                        logger.debug("---------物料处理规则2.b.1");
                    filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                    publishType = "Z";
                }
                try
                {
                    MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
                    Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
                    String routeAsString = "";
                    String routeAllCode = "";
                    String routeAssemStr = "";
                    if(routevec.size() != 0)
                    {
                        routeAsString = (String)routevec.elementAt(0);
                        routeAllCode = (String)routevec.elementAt(1);
                    }
                    String aaa = tempFilterPart.getRoute();
                    boolean flag1 = aaa == null || aaa == "";
                    boolean flag2 = routeAllCode == null || routeAllCode == "";
                    if(!flag2 || !flag1)
                        if(!flag2 && flag1)
                        {
                            filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                            publishType = "Z";
                        } else
                        if(flag2 && !flag1)
                        {
                            filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                            publishType = "Z";
                        } else
                        if(!routeAllCode.equals(tempFilterPart.getRoute()))
                        {
                            filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                            publishType = "Z";
                        }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    throw new QMXMLException(ex);
                }
            }
            //物料处理规则2.a。版本变化，标记为修改U，对结构进行处理。
            else
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------物料处理规则2.a");
                }
                filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                //设置子物料的更改标记
                publishType = "U";
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPublishType(QMPartIfc) - end" + publishType);
        }
        return publishType;
    }

    /**
     *  获取该零部件分解后的物料集合。
     *  如果isXML为true，则同时将物料值对象封装成一条XML记录。
     * @param partIfc 拆分的零部件。
     * @param matPubType 发布标识。
     * @param partMap 零部件集合。
     * @return Collection 该零部件的物料集合。
     * @throws QMXMLException
     */
    private final void setSubMaterial(QMPartIfc partIfc, String matPubType,
            HashMap partMap,String partnumber) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMaterial(String, String, String , HashMap) - start");
        }
      //  System.out.println("qqqqqqqqqqqqqqqqqqqqq==============");
        MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        //物料值对象
        MaterialSplitIfc matSplitIfc;
        //物料值对象的XML形式
        QMXMLMaterialSplit xmlMatSplit;
        //零部件拆分的物料的最顶级物料
        //CCBegin SS3
        //Collection rootList=(Collection)msservice.getRootMSplit(partIfc.getPartNumber());
        Collection rootList=(Collection)msservice.getRootMSplit(partnumber);
        //CCEND SS3
        if(partIfc.getPartNumber().equals("1701516A8A")){
        System.out.println("rootList1111111********************"+rootList);
        }
        Iterator rootMatSplitIter = rootList.iterator();
        //20080122 begin
        HashMap notPublishMat=new HashMap();
        //20080122 end
     
        //获得顶级物料
        while (rootMatSplitIter.hasNext())
        {
            matSplitIfc = (MaterialSplitIfc) rootMatSplitIter.next();
            //20080122 begin
            boolean flag=isParentHasSpeRoute(matSplitIfc,partIfc);
//            System.out.println("flag********************"+flag);
//            System.out.println("matSplitIfc.getPartNumber()********************"+matSplitIfc.getPartNumber());
//            System.out.println("partIfc.getPartNumber()********************"+partIfc.getPartNumber());
            logger.debug("flag is "+flag);
            logger.debug("matSplitIfc.getPartNumber() is "+matSplitIfc.getPartNumber());
            logger.debug("partIfc.getPartNumber() is "+partIfc.getPartNumber());
            //不发布的零件
            if(notPublishMat.containsKey(matSplitIfc.getPartNumber())||
            		!flag){
            	// System.out.println("matSplitIfc.getPartNumber()==========="+matSplitIfc.getPartNumber());
            	notPublishMat.put(matSplitIfc.getPartNumber(), matSplitIfc.getPartNumber());
            	continue;
            }
           // System.out.println("notPublishMat==========="+notPublishMat);
            
            //20080122 end
            matSplitIfc.setPartVersion(partIfc.getVersionID());
//            System.out.println("flagflagflagflag==========="+flag);
            if(partIfc.getPartNumber().equals("1701516A8A")){
            	System.out.println("matSplitIfc.getMaterialNumber()==========="+matSplitIfc.getMaterialNumber());
            }
            //System.out.println("matSplitIfc.getMaterialNumber()==========="+matSplitIfc.getMaterialNumber());
            if(!hasSetSplitPubTypeMap.containsKey(matSplitIfc
                    .getMaterialNumber()))
            {
                xmlMatSplit = new QMXMLMaterialSplit(matSplitIfc);
                xmlMatSplit.setPartIfc(partIfc);
//                System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getBsoID());
//                System.out.println("xmlMatSplitxmlMatSplit0000000000000000022222222222222222222222=============="+matSplitIfc.getPartVersion());
//                System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getMaterialSplitType());
                xmlMatSplit.setPublishType(matSplitIfc.getMaterialSplitType());
                //CCEnd by chudaming 20100329
                xmlMatSplit.setPartVersionID(partIfc.getVersionID());
                xmlMatSplitList.add(xmlMatSplit);
                hasSetSplitPubTypeMap.put(matSplitIfc.getMaterialNumber(),
                        xmlMatSplit);
//              CCBegin SS3
//                System.out.println("nizhenniu555555555555555555555555555555------------------------buxinzhaobudaoni--------------");
//                setSubMatStructPubType(partIfc.getPartNumber(), partIfc
//                        .getVersionID(), xmlMatSplit, partMap);
                setSubMatStructPubType(partnumber, partIfc
                        .getVersionID(), xmlMatSplit, partMap);
//              CCEND SS3
//                System.out.println("nizhenniu-666666666666666666666666-----------------------buxinzhaobudaoni--------------");
            }
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMaterial(String, String, String , HashMap) - end");
        }
    }

    //如果一个零部件的路线中带采，且所有的父件的路线中带有采，则不发布这个零部件。
    private boolean isParentHasSpeRoute(MaterialSplitIfc matSplitIfc,
			QMPartIfc subPartIfc ) throws QMException {
    	return true;
	}

    /**
	 * 设置下级物料结构和物料的发布标记。
	 *
	 * @param rootPartNumber：顶级物料的零部件编号。
	 * @param rootPartVersionID：顶级物料的零部件版本。
	 * @param matSplitIfc：物料值对象的XML形式
	 * @param partMap：存放零部件值对象的HashMap
	 * @throws QMException
	 */
    private void setSubMatStructPubType(String rootPartNumber,
            String rootPartVersionID, QMXMLMaterialSplit xmlMatSplit,
            HashMap partMap) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMatStructPubType(QMXMLMaterialSplit, HashMap) - start");
        }
        // 设置下级物料结构和下级物料的发布标记
        //1-有下级物料，2—此物料下要挂接原零部件的子件，0-最底层物料
        if(xmlMatSplit.getMaterialSplit().getStatus() == 1)
        {
            //发布标记
            String publishType = xmlMatSplit.getPublishType();
            //得到该物料的下级物料，这个地方只是获得了顶级物料的下一级物料
            List list = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit
                    .getMaterialNumber());
            //物料结构值对象
            MaterialStructureIfc matStructIfc;
            //物料结构值对象XML形式
            QMXMLMaterialStructure xmlMatStruct;
            //下级物料值对象
            MaterialSplitIfc subMatSplitIfc;
            //下级物料值对象XML形式
            QMXMLMaterialSplit subXMlMatSplit;
            //零部件值对象
            QMPartIfc partIfc = null;
            String partIdentity = "";
            for (int i = 0; i < list.size(); i++)
            {
                matStructIfc = (MaterialStructureIfc) list.get(i);
                if(!hasSetStrutPublishType.containsKey(matStructIfc.getBsoID()))
                {
                    //设置下级物料结构的发布标记
                    xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
                    if(matStructIfc.getParentPartNumber().equals(
                            xmlMatSplit.getPartNumber()))
                    {
                        xmlMatStruct.setParentPartVersion(rootPartVersionID);
                    }
                    //当物料的发布标记为"Z"时，物料结构的发布标记为"U"questions
                    //CCBegin by dikefeng 20100422,将这种旧的设置方式去掉
//                    if(publishType.equals("Z"))
//                    {
//                        xmlMatStruct.setStructurePublishType("U");
//                    }
//                    else
//                    {
//                        xmlMatStruct.setStructurePublishType(publishType);
//                    }
                    xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                    //CCEnd by dikefeng 20100422
                    hasSetStrutPublishType.put(matStructIfc.getBsoID(),
                            xmlMatStruct);
                    xmlMatStructList.add(xmlMatStruct);
                    subMatSplitIfc = getMatSplitIfc(matStructIfc.getChildNumber());    
                    if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                    {
                        subMatSplitIfc.setPartVersion(rootPartVersionID);
                    }
                    //设置下级物料的发布标记
                    if(!hasSetSplitPubTypeMap.containsKey(subMatSplitIfc
                            .getMaterialNumber()))
                    {
                        partIfc = getPartIfc(subMatSplitIfc, partMap);
                        partIdentity = getPartIdentity(subMatSplitIfc);
                        partMap.put(partIdentity, partIfc);
                        //CCBegin SS4
                        if(!partNumberMap.containsKey(partIfc.getPartNumber())){
                        	partNumberMap.put(partIfc.getPartNumber(), subMatSplitIfc.getPartNumber());
                        }
                        //CCEnd SS4
                        subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                       // subXMlMatSplit.setPublishType(publishType);
                        //CCBegin by chudaming 20100331questions？？？
//                        System.out.println("fdsfsdfdsfsdfsdf");
                        subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                        //CCEnd by chudaming 20100331
                        subXMlMatSplit.setPartIfc(partIfc);
                        if(subXMlMatSplit.getPartNumber()
                                .equals(rootPartNumber))
                        {
                            subXMlMatSplit.setPartVersionID(rootPartVersionID);
                        }
//                        subXMlMatSplit.setPartVersionID(partVersionID);
                        hasSetSplitPubTypeMap.put(subMatSplitIfc
                                .getMaterialNumber(), subXMlMatSplit);
                        xmlMatSplitList.add(subXMlMatSplit);
                        //循环查询下一级物料
                        setSubMatStructPubType(rootPartNumber,
                                rootPartVersionID, subXMlMatSplit, partMap);
                    }
                }
            }
            //CCBegin by dikefeng 20100421，当前物料状态标记为1时，说明，他是有属于本零件的下一级物料，那么只要他曾经有与下级零件的关系
            //则删除,如果状态标记为2或0则说明无属于本零件的下级物料。为2时，应该将当前子件结构与旧结构进行比较，将本次没有的结构标记为D
            //本次有的数量没有变化，标记为o，本次有但是数量变化了的标记为U；为0时，说明没有下级零件，则如果之前的发布中有下级路线
            //直接将所有结构标记为D
            //此处没看懂。删除不就没了吗
            
            QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
            materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
            materialPartStruQuery.addAND();
            materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
            PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
            Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
            Iterator mpsIte=mpsColl.iterator();
            while(mpsIte.hasNext())
            {
            	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//            	System.out.println("kuku啦啦啦啦来了么？-------------------------------------------");
            	setDeleteMaterialPartStructure(mpsIfc);
            	pService.deleteValueInfo(mpsIfc);
            }
            //CCEnd by dikefeng 20100421
        }
        //设置零部件使用结构的发布标记
        //CCBegin by dikefeng 20090619,发布子件结构时，需要发布被删除的结构信息
        else if(xmlMatSplit.getMaterialSplit().getStatus() == 2||xmlMatSplit.getMaterialSplit().getStatus() == 0)
        //CCEnd by dikefeng 20090619
        {
            filterByStructureRule(rootPartNumber, rootPartVersionID,
                    xmlMatSplit,partMap);
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMatStructPubType(QMXMLMaterialSplit, HashMap) - end");
        }
    }

    /**
     * 得到物料值对象拆分的零部件信息，从parts中得到或者刷新数据库得到。
     * @param matSplitIfc:物料值对象
     * @param partMap
     * @return 物料值对象拆分的零部件信息
     * @throws QMException
     */
    private QMPartIfc getPartIfc(MaterialSplitIfc matSplitIfc, HashMap partMap)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIfc(MaterialSplitIfc, HashMap) - start");
        }
        //零部件值对象
        QMPartIfc partIfc = null;
        //零部件的唯一标识
        String partIdentity = getPartIdentity(matSplitIfc);
        //如果零部件在parts中
//        System.out.println("partMap====="+partMap);
//        System.out.println("partIdentity====="+partIdentity);
        if(partMap.containsKey(partIdentity))
        {
            partIfc = (QMPartIfc) partMap.get(partIdentity);
           
        }
        //如果零部件不在parts中，则从数据库中刷新
        else
        {
        	//CCBegin SS3
        	String[] a = matSplitIfc.getPartNumber().split("/");
        	String partnumber= a[0];
            Collection partCol = getPartCol(partnumber,
                    matSplitIfc.getPartVersion());
            //CCEnd SS3
            Iterator partIter = partCol.iterator();
            if(partIter.hasNext())
            {
                try
                {
                    partIfc = (QMPartIfc) getLatestIteration((QMPartIfc) partIter
                            .next());
                }
                catch (QMException e)
                {
                    //"查找编号为*，版本为*的最新零部件版序时出错！"
                    logger.error(Messages.getString("Util.63", new Object[]{
                            matSplitIfc.getPartNumber(),
                            matSplitIfc.getPartVersion()})
                            + e);
                    throw new QMException(e);
                }
            }
        }
        if(partIfc == null)
        {
            //"无法得到物料号为*的物料拆分的零部件的信息!"
            logger.error(Messages.getString("Util.64", new Object[]{matSplitIfc
                    .getMaterialNumber()}));
            throw new QMException(Messages.getString("Util.64",
                    new Object[]{matSplitIfc.getMaterialNumber()}));
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIfc(MaterialSplitIfc, HashMap) - end");
        }
        if(!partNumberMap.containsKey(partIfc.getPartNumber())){
        	partNumberMap.put(partIfc.getPartNumber(), matSplitIfc.getPartNumber());
        }
        
        return partIfc;
    }

    /**
     * 根据结构处理规则，处理结构，并将筛选后的QMXMLStructure集合存入xmlMatStructList中。
     *
     * 注1：由于在使用结构中，同一级可能存在多个不同数量的相同子件，所以在处理结构前，
     * 要先对该问题进行处理。首先将原结构中的子件数量合并，新结构中的子件则保留原样。
     * （特殊增加规则）然后原结构中的子件再与新结构中的子件进行比较，
     * 头一个子件的数量按下面的第二条规则处理，其后的所有相同子件都作为新增子件处理。
     *
     * 结构处理规则，在零部件的版本变化时使用此规则：
     * 1.数据为新增时，结构信息中所有子件发布，结构关系标记为新增N；
     * 2.数据为版本变化时，将数据的子件与表中该数据的版本的子件进行比较：
     *   a新的子零件在原数据中不存在，结构数据标记为新增N；
     *   b新的子零件在原数据中存在，1且使用数量相同，标记为沿用O；
     *                           2且使用数量不同，标记为数量更改U；
     *   c原的子零件在新数据中不存在，标记为取消D。
     * 注2：不对结构进行递归处理，只处理第一级结构。
     * @param rootPartNumber 顶级物料的零部件编号
     * @param rootPartVersionID 顶级物料的零部件版本
     * @param xmlMatSplit 要设置零部件使用结构发布标记的物料值对象的XML形式。
     * @throws QMException
     */
    public final void filterByStructureRule(String rootPartNumber,
            String rootPartVersionID, QMXMLMaterialSplit xmlMatSplit,HashMap partMap)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(QMXMLMaterialSplit) - start");
        }
        //发布标记
        final String publishType = xmlMatSplit.getPublishType();
        //CCBegin by dikefeng 20100421
        PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
        //CCEnd by dikefeng 20100421
//        System.out.println("publishTypepublishType====="+publishType+"xmlMatSplitxmlMatSplitxmlMatSplit222"+xmlMatSplit.getMaterialNumber());
        //新的使用关系的值对象
        PartUsageLinkIfc newUsageLinkIfc;
        //原的使用关系的值对象
        PartUsageLinkIfc oldUsageLinkIfc;
        //临时的使用关系的值对象
        PartUsageLinkIfc tempUsageLinkIfc;
        //发布的零部件的使用结构
        HashMap newUsageLinkMap = getUsageLinkMap(xmlMatSplit.getPartIfc());
        //结构处理规则1。数据为新增时，结构信息中所有子件发布，结构关系标记为新增N。questions
        //全部发布为 N没有问题，只不过，如果物料是因为路线变化而发布为N，则应该将原子节点路线所带结构删除
        if(publishType.equals("N"))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------结构处理规则1");
            }
            //设置零部件结构的更改标记:
            //当物料的更改标记为新增时，零部件结构的更改标记和物料的更改标记一致。
            if(xmlMatSplit.getMaterialSplit().getStatus()==2)
            {
            Iterator usageLinkIter = newUsageLinkMap.values().iterator();
            while (usageLinkIter.hasNext())
            {
                newUsageLinkIfc = (PartUsageLinkIfc) usageLinkIter.next();
                setPartStructPubType(rootPartNumber, rootPartVersionID, "N",
                        xmlMatSplit, newUsageLinkIfc);
            //在这里需要为新的结构创建缓存关联
            //CCBegin by dikefeng  20100421,缓存关联
             MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
             mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
             mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
             mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
             mpsInfo.setLevelNumber("0");
             mpsInfo.setDefaultUnit("个");
             mpsInfo.setMaterialStructureType("O");
             mpsInfo.setOptionFlag(false);
             mpsInfo.setQuantity(newUsageLinkIfc.getQuantity());
             List rootMatNumberList = getRootMatSplit(newUsageLinkIfc
                     .getLeftBsoID(), xmlMatSplit.getRouteCode());
//         	System.out.println("00000000000000000000kuku啦啦啦啦来了么？-------------------------------------------");
             for (int i = 0; i < rootMatNumberList.size(); i++)
             {
                 String childNumber = (String) rootMatNumberList.get(i);
                 mpsInfo.setChildNumber(childNumber);
                 pService.saveValueInfo(mpsInfo);
             }
            //CCEnd by dikefeng 20100421
            }
            }
        }
        else if(publishType.equals("D"))
        {
        	//CCBegin by dikefeng 20100421，当前物料状态标记为1时，说明，他是有属于本零件的下一级物料，那么只要他曾经有与下级零件的关系
            //则删除,如果状态标记为2或0则说明无属于本零件的下级物料。为2时，应该将当前子件结构与旧结构进行比较，将本次没有的结构标记为D
            //本次有的数量没有变化，标记为o，本次有但是数量变化了的标记为U；为0时，说明没有下级零件，则如果之前的发布中有下级路线
            //直接将所有结构标记为D
        	if(xmlMatSplit.getMaterialSplit().getStatus()==2)
        	{
            QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
            materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
            materialPartStruQuery.addAND();
            materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
            Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
            Iterator mpsIte=mpsColl.iterator();
            while(mpsIte.hasNext())
            {
            	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//           	System.out.println("111122222222222kuku啦啦啦啦来了么？-------------------------------------------");
            	setDeleteMaterialPartStructure(mpsIfc);
            	pService.deleteValueInfo(mpsIfc);
            	//questions这里添加发布删除信息的程序
            }
        	}
            //CCEnd by dikefeng 20100421
            
        }
        //结构处理规则2。数据为版本变化时，将数据的子件与表中该数据的版本的子件进行比较。
        //CCBegin by chudaming 20100928 
        else if(publishType.equals("U"))
//        		||publishType.equals("Z")
       
        {
        	 //CCEnd by chudaming 20100928 
//        	System.out.println("jinru____________UUUUUU============"+xmlMatSplit.getMaterialSplit().getStatus());
            if(logger.isDebugEnabled())
            {
                logger.debug("---------结构处理规则2");
            }
            //CCBegin by dikefeng 20100421，当前物料状态标记为1时，说明，他是有属于本零件的下一级物料，那么只要他曾经有与下级零件的关系
            //则删除,如果状态标记为2或0则说明无属于本零件的下级物料。为2时，应该将当前子件结构与旧结构进行比较，将本次没有的结构标记为D
            //本次有的数量没有变化，标记为o，本次有但是数量变化了的标记为U；为0时，说明没有下级零件，则如果之前的发布中有下级路线
            //直接将所有结构标记为D
            if(xmlMatSplit.getMaterialSplit().getStatus()==0)
            {
//            	System.out.println("xmlMatSplit.getMaterialSplit().getStatus()=========0000000");
            	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA这个部分是为了处理原有的本物料内结构
            	MaterialStructureIfc matStructIfc;
                //物料结构值对象XML形式
                QMXMLMaterialStructure xmlMatStruct;
                //下级物料值对象
                MaterialSplitIfc subMatSplitIfc;
                //下级物料值对象XML形式
                QMXMLMaterialSplit subXMlMatSplit;
                //零部件值对象
                QMPartIfc partIfc = null;
                String partIdentity = "";
            	List oldMaterialList = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit.getMaterialNumber());
            	Iterator oldMaterialIte=oldMaterialList.iterator();
            	while(oldMaterialIte.hasNext())
            	{
            		matStructIfc = (MaterialStructureIfc)oldMaterialIte.next();
                        //设置下级物料结构的发布标记
                        xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
                        if(matStructIfc.getParentPartNumber().equals(
                                xmlMatSplit.getPartNumber()))
                        {
                            xmlMatStruct.setParentPartVersion(rootPartVersionID);
                        }
                        //当物料的发布标记为"Z"时，物料结构的发布标记为"U"questions
                        //CCBegin by dikefeng 20100422,将这种旧的设置方式去掉
//                        if(publishType.equals("Z"))
//                        {
//                            xmlMatStruct.setStructurePublishType("U");
//                        }
//                        else
//                        {
//                            xmlMatStruct.setStructurePublishType(publishType);
//                        }
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        //CCEnd by dikefeng 20100422
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
                        if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                        {
                            subMatSplitIfc.setPartVersion(rootPartVersionID);
                        }
                            partIfc = getPartIfc(subMatSplitIfc, partMap);
                            partIdentity = getPartIdentity(subMatSplitIfc);
                            partMap.put(partIdentity, partIfc);
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                           // subXMlMatSplit.setPublishType(publishType);
                            //CCBegin by chudaming 20100331questions？？？
//                            System.out.println("cccccccccccccvvvvvvvvvvvvvvvvvvcbn");
                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                            //CCEnd by chudaming 20100331
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//                            subXMlMatSplit.setPartVersionID(partVersionID);
                            xmlMatSplitList.add(subXMlMatSplit);
//                            System.out.println("nizhenniu------------------------buxinzhaobudaoni--------------");
                            setSubMatStructPubType(rootPartNumber,
                                    rootPartVersionID, subXMlMatSplit, partMap);
//                            System.out.println("11111111111111111111111nizhenniu------------------------buxinzhaobudaoni--------------");
            	}
            	//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB这个部分是为了删除原有的跨零件结构
            	QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator mpsIte=mpsColl.iterator();
                while(mpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//                	System.out.println("333333333333333333333333333333333啦啦啦啦来了么？-------------------------------------------");
                	setDeleteMaterialPartStructure(mpsIfc);
                	pService.deleteValueInfo(mpsIfc);
                	//questions这里添加发布删除信息的程序
                }
                
            }else if(xmlMatSplit.getMaterialSplit().getStatus()==2)
            {
//            	System.out.println("44444444444444444444kuku啦啦啦啦来了么？-------------------------------------------");
            	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA这个部分是为了处理原有的本物料内结构
            	MaterialStructureIfc matStructIfc;
                //物料结构值对象XML形式
                QMXMLMaterialStructure xmlMatStruct;
                //下级物料值对象
                MaterialSplitIfc subMatSplitIfc;
                //下级物料值对象XML形式
                QMXMLMaterialSplit subXMlMatSplit;
                //零部件值对象
                QMPartIfc partIfc = null;
                String partIdentity = "";
            	List oldMaterialList = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit.getMaterialNumber());
//            	System.out.println("xmlMatSplit.getMaterialSplit().getStatus()============22222222222========"+oldMaterialList);
            	Iterator oldMaterialIte=oldMaterialList.iterator();
            	while(oldMaterialIte.hasNext())
            	{
            		matStructIfc = (MaterialStructureIfc)oldMaterialIte.next();
                        //设置下级物料结构的发布标记
                        xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
//                        System.out.println("matStructIfc.getParentPartNumber()========"+matStructIfc.getParentPartNumber());
//                        System.out.println("xmlMatSplit.getPartNumber()========"+xmlMatSplit.getPartNumber());
                        if(matStructIfc.getParentPartNumber().equals(
                                xmlMatSplit.getPartNumber()))
                        {
                            xmlMatStruct.setParentPartVersion(rootPartVersionID);
                            
                        }
                        //当物料的发布标记为"Z"时，物料结构的发布标记为"U"questions
                        //CCBegin by dikefeng 20100422,将这种旧的设置方式去掉
//                        if(publishType.equals("Z"))
//                        {
//                            xmlMatStruct.setStructurePublishType("U");
//                        }
//                        else
//                        {
//                            xmlMatStruct.setStructurePublishType(publishType);
//                        }
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        //CCEnd by dikefeng 20100422
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
//                        System.out.println("subMatSplitIfc.getPartNumber()========"+subMatSplitIfc.getPartNumber());
//                        System.out.println("rootPartNumber"+rootPartNumber);
                        if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                        {
                            subMatSplitIfc.setPartVersion(rootPartVersionID);
                        }
                            partIfc = getPartIfc(subMatSplitIfc, partMap);
                            partIdentity = getPartIdentity(subMatSplitIfc);
                            partMap.put(partIdentity, partIfc);
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                           // subXMlMatSplit.setPublishType(publishType);
                            //CCBegin by chudaming 20100331questions？？？
//                            System.out.println("fdsasdasdasdasdasdasdasd");
                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                            //CCEnd by chudaming 20100331
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//                            subXMlMatSplit.setPartVersionID(partVersionID);
                            xmlMatSplitList.add(subXMlMatSplit);
//                            System.out.println("nizhenniu222222222222222222222222222222------------------------buxinzhaobudaoni--------------");
                            setSubMatStructPubType(rootPartNumber,
                                    rootPartVersionID, subXMlMatSplit, partMap);
//                            System.out.println("nizhenniu33333333333333333333333333333------------------------buxinzhaobudaoni--------------");
            	}
            	//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB从这里往下，处理跨零件物料结构的发布标识 
                //得到该零部件的上一个发布的版本，因为旧版发布过程中物料到零部件的结构进行了记录，所以这里就不再进行重复提取旧版零件的结构
//            	System.out.println("wwwwwwwwwwwwww"+xmlMatSplit.getPartNumber());
//            	System.out.println("wwwwwwwwwwwwww11111111111"+xmlMatSplit.getPartVersionID());
//            	
//            	System.out.println("wwwwwwwwwwwwww22222222222222222"+xmlMatSplit.getPartVersionID());
            	QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                Collection oldMpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator oldMpsIte=oldMpsColl.iterator();
                HashMap oldMpsMap=new HashMap();
//                System.out.println("xmlMatSplit.getPartNumber())fffffffffffffffffffff"+xmlMatSplit.getPartNumber());
//                System.out.println("oldMpsIte1111fffffffffffffffffffff"+oldMpsIte);
//                System.out.println("xmlMatSplit.getMaterialNumber()1111fffffffffffffffffffff"+xmlMatSplit.getMaterialNumber());
                while(oldMpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIte.next();
                	oldMpsMap.put(mpsIfc.getChildNumber(),mpsIfc);
                }
//                System.out.println("oldMpsMapoldMpsMapoldMpsMap======="+oldMpsMap);
                //如果新状态下有子件，则用当前的子件与上次发布内容进行比较，分别设置删除，新增、修改和沿用标记
                //如果新状态下没有子件，则将上次发布的所有子件结构删除（实际上因为当前发布的状态为2，所以不可能没有子件）
//                System.out.println("newUsageLinkMapnewUsageLinkMapnewUsageLinkMap======="+newUsageLinkMap);
                if(newUsageLinkMap.size() > 0)
                {
                	//新数据的结构关联数组，对每一个新的数据关联进行循环处理 
                    Iterator newUsageLinksIte = newUsageLinkMap.keySet().iterator();
                    while(newUsageLinksIte.hasNext())
                    {
                    	PartUsageLinkIfc newLink=(PartUsageLinkIfc)newUsageLinkMap.get(newUsageLinksIte.next());
                    	QMPartMasterIfc partMaster=(QMPartMasterIfc)pService.refreshInfo(newLink.getLeftBsoID());
                    	//如果当前关联在旧结构中不存在，则当前关联为新关联
                    	if(oldMpsMap.get(partMaster.getPartNumber())==null)
                    	{
                    		
                    		MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
                            mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
                            mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                            mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
                            mpsInfo.setLevelNumber("0");
                            mpsInfo.setDefaultUnit("个");
                            //???
                            mpsInfo.setMaterialStructureType("O");
                            mpsInfo.setOptionFlag(false);
                            mpsInfo.setQuantity(newLink.getQuantity());
                            //CCBegin SS4
                            String partnumber = (String) partNumberMap.get(partMaster.getPartNumber());
                            if(partnumber==null||partnumber.length()==0){
                            	PartHelper helper = new PartHelper();
                            	partnumber=helper.getPartNumber(partMaster);
                            }
                            
                            mpsInfo.setChildNumber(partnumber);
                            //CCEnd SS4
//                            在物料零件关联表中存储数据
                            pService.saveValueInfo(mpsInfo);
//                            System.out.println("sssssssssss==="+mpsInfo.getMaterialStructureType());
//                            发布新增的物料零件关联
                            setPartStructPubType(rootPartNumber, rootPartVersionID,
                                    "N", xmlMatSplit, newLink);
                            
                    	}else{
                    		
                    		//如果存在对当前子件的关联，则有可能是o或者u
                    		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsMap.get(partMaster.getPartNumber());
//                    		如果使用数量相同，则为o，否则为U
//                    		System.out.println("ooooooooooooo1111111==="+newLink.getQuantity());
//                    		System.out.println("ooooooooooooo222222222==="+mpsIfc.getQuantity());
                    		if(newLink.getQuantity()==mpsIfc.getQuantity())
                    		{
                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "O", xmlMatSplit, newLink);
                    			oldMpsMap.remove(partMaster.getPartNumber());
                    		}else{
                    			mpsIfc.setQuantity(newLink.getQuantity());
                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "U", xmlMatSplit, newLink);
                    			oldMpsMap.remove(partMaster.getPartNumber());
                    		}
                    	}
                    	
                    }
                    Iterator remainderIte=oldMpsMap.keySet().iterator();
                    //222222
//                    System.out.println("DDDDDDDDDDDDDDDDDD====="+oldMpsMap);
                	while(remainderIte.hasNext())
                	{
                		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsMap.get((String)remainderIte.next());
//                		System.out.println("1111111111111111111111三四三1啦啦啦啦来了么？-------------------------------------------");
                		setDeleteMaterialPartStructure(mpsIfc);
                    	pService.deleteValueInfo(mpsIfc);
                	}
                }else
                {
               //	System.out.println("oldMpsMapfffffffffffffffffffff"+oldMpsMap);
               // System.out.println("oldMpsIte2222fffffffffffffffffffff"+oldMpsIte.);
                	Iterator keyIte=oldMpsMap.keySet().iterator();
                	while(keyIte.hasNext())
                	{
                		String key=(String) keyIte.next();
                		//System.out.println("keyfffffffffffffffffffff"+key);
                		//此处出现异常所以加入了if(oldMpsIte.hasNext()){
                		if(oldMpsIte.hasNext()){
                			MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIte.next();
//                    		System.out.println("222222222222222222222222啦啦啦啦来了么？-------------------------------------------");
                    		setDeleteMaterialPartStructure(mpsIfc);
                        	pService.deleteValueInfo(mpsIfc);
                		}
                		
                	}
                }

            }
            //CCEnd by dikefeng 20100421
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(QMXMLMaterialSplit) - end");
        }
    }

    /**
     * 合并结构中使用子件的数量。
     * @param usageLinkMap 待合并数量的使用结构Map，值为PartUsageLinkIfc。
     * @return HashMap 合并后的使用结构Map，键为子件主信息BsoID，值为PartUsageLinkIfc。
     */
    private final HashMap uniteQuantity(HashMap usageLinkMap)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - start"); //$NON-NLS-1$
        }
        //合并后的使用结构关联集合。
        List usageLinkList = new ArrayList();
        Iterator iter = usageLinkMap.values().iterator();
        PartUsageLinkIfc usageLinkIfc = null;
        PartUsageLinkIfc usageLinkIfc2 = null;
        float quantity = 0;
        String leftBsoID = "";
        boolean flag = false;
        while (iter.hasNext())
        {
            usageLinkIfc = (PartUsageLinkIfc) iter.next();
            quantity = usageLinkIfc.getQuantity();
            leftBsoID = usageLinkIfc.getLeftBsoID();
            //标识在集合usageLinkList中是否存在当前被循环的这个零部件,初始情况下,认为不存在:
            flag = false;
            //对已有的合并完毕的集合进行循环:
            for (int i = 0; i < usageLinkList.size(); i++)
            {
                usageLinkIfc2 = (PartUsageLinkIfc) usageLinkList.get(i);
                float oldQuantity = usageLinkIfc2.getQuantity();
                String oldLeftBsoID = usageLinkIfc2.getLeftBsoID();
                //如果使用的是同一个零部件的话,合并数量。
                if(leftBsoID.equals(oldLeftBsoID))
                {
                    usageLinkIfc2.setQuantity(quantity + oldQuantity);
                    //找到了这个零部件:
                    flag = true;
                    usageLinkList.set(i, usageLinkIfc2);
                    break;
                }
            }
            if(!flag)
                usageLinkList.add(usageLinkIfc);
        }
        usageLinkMap = new HashMap();
        //将合并后的结果集合加到Map中，键为子件主信息BsoID，值为PartUsageLinkIfc。
        for (int i = 0; i < usageLinkList.size(); i++)
        {
            usageLinkIfc = (PartUsageLinkIfc) usageLinkList.get(i);
            usageLinkMap.put(usageLinkIfc.getLeftBsoID(), usageLinkIfc);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - end"); //$NON-NLS-1$
        }
        return usageLinkMap;
    }

    /**
     * 将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
     * @param xmlMaterialSplitList 过滤后xmlPartList。
     * @param xmlStructureList 过滤后xmlStructureList。
     * @throws QMXMLException
     */
    private final void setDataRecord(final List xmlMaterialSplitList,
            final List xmlStructureList) throws Exception
    {
    	try{
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - start"); //$NON-NLS-1$
        }
        for (int j = 0; j < dataList.size(); j++)
        {
            final QMXMLData data = (QMXMLData) dataList.get(j);
            if(logger.isDebugEnabled())
            {
                logger.debug("data.getName==" + data.getName());
            }
            if(data.getName().equals("mpart"))
            {
                final List materialSplitRecordList = new ArrayList();
                for (int i = 0; i < xmlMaterialSplitList.size(); i++)
                {
                    final QMXMLMaterialSplit xmlMaterialSplit = (QMXMLMaterialSplit) xmlMaterialSplitList
                            .get(i);
                    xmlMaterialSplit.setPropertyList(data.getPropertyList());
                    materialSplitRecordList.add(xmlMaterialSplit.getRecord());
                }
                data.setRecordList(materialSplitRecordList);
                setDescNote(Messages.getString("Util.34", new Object[]{
                        Integer.toString(materialSplitRecordList.size()),
                        data.getName()}));
                continue;
            }
            else if(data.getName().equals("structure"))
            {
                final List structureRecordList = new ArrayList();
                for (int i = 0; i < xmlStructureList.size(); i++)
                {
                    final QMXMLMaterialStructure xmlStructure = (QMXMLMaterialStructure) xmlStructureList
                            .get(i);
                    xmlStructure.setPropertyList(data.getPropertyList());
                    structureRecordList.add(xmlStructure.getRecord());
                }
                data.setRecordList(structureRecordList);
                setDescNote(Messages.getString("Util.34", new Object[]{
                        Integer.toString(structureRecordList.size()),
                        data.getName()}));
                continue;
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - end"); //$NON-NLS-1$
        }
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    		
    	}
    }

    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
     * @param partIfc 零部件。
     * @throws QMXMLException
     */
    private final HashMap getUsageLinkMap(QMPartIfc partIfc)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - start");
        }
        final HashMap usageLinkMap = new HashMap();
        PartHelper helper = new PartHelper();
        //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
        List usesPartList = new ArrayList();
        try
        {
        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
            //然后得到该零部件关联的PartUsageLink
        }
        catch (QMException e)
        {
            //"获取名为*的零部件结构时出错！"
            logger.error(Messages.getString("Util.17", new Object[]{partIfc
                    .getBsoID()})
                    + e);
            throw new QMXMLException(e);
        }
        //将要发布的子关联放到HashMap中。
        Iterator iter = usesPartList.iterator();
       // System.out.println("usesPartList========"+usesPartList);
        while (iter.hasNext())
        {
            Object obj = iter.next();
           // System.out.println("obj========"+obj);
            PartUsageLinkIfc link =  (PartUsageLinkIfc)obj;
            String masterID = link.getLeftBsoID();
		
           
            try {
            	//CCBegin SS2
            	//首先获得最新小版本，然后按生命周期过滤 刘家坤 20140303 
            	// 获得最新小版本QMPartIfc
    			QMPartIfc qmpartifc = helper.getPartInfoByMasterBsoID(masterID);
    			//System.out.println("qmpartifc========1111111"+qmpartifc);
    			QMPartIfc part = helper.filterLifeState(qmpartifc);
    			 //System.out.println("part========1111111"+part);
    			 if(part==null)
    	        		continue;
    			//CCEnd SS2
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//System.out.println("part22========2222222");
        	
            usageLinkMap.put(obj, obj);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - end");
        }
        return usageLinkMap;
    }

    /**
     * 获取旧零部件对应的子件结构关联。
     * @param xmlMaterialSplit
     * @return
     * @throws QMXMLException
     */
    public QMPartIfc getOldPartIfc(String partNumber) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getOldUsageLinkMap(QMXMLMaterialSplit) - start"); //$NON-NLS-1$
        }
        VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
        QMPartIfc latestPartIfc = null;
        //根据filterPart记录的零部件名称和版本号获取最新版序，
        final FilterPartIfc filterPartIfc = (FilterPartIfc) filterPartMap
                .get(partNumber);
        List partMasterList = new ArrayList();
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            final QMQuery query = new QMQuery("QMPartMaster");
            //CCBegin by dikefeng 20090619,这里修改的是为了在重新发布的时候所有的零部件都发,为了都发
            //所有设置默认标识为"U",在获取旧版本的结构时有可能没有filterPartIfc
            final QueryCondition condition2 = new QueryCondition("partNumber",
                    QueryCondition.EQUAL, partNumber);
            //CCEnd by dikefeng 20090619
            query.addCondition(condition2);
            //暂时不考虑广义部件的情况。
            query.setChildQuery(false);
            partMasterList=(List)pservice.findValueInfo(query);
        }
        catch (QueryException e)
        {
            //"构造查询条件时出错！"
            logger.error(Messages.getString("Util.19") + e);
            throw new QMXMLException(e);
        }
        catch (QMException e)
        {
            //"获取编号为*的零部件的基本信息时出错！"
            logger.error(Messages.getString("Util.20",
                    new Object[]{filterPartIfc.getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        if(partMasterList != null && partMasterList.size() > 0)
        {
            List versionList = new ArrayList();
            try
            {
            	versionList=(List)vcservice.allVersionsOf((QMPartMasterIfc)partMasterList.get(0));
            }
            catch (QMException e)
            {
                //"获取编号为*的零部件的小版本对象时出错！"
                logger.error(Messages.getString("Util.21",
                        new Object[]{filterPartIfc.getPartNumber()})
                        + e);
                throw new QMXMLException(e);
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("versionList==" + versionList);
            }
            for (int j = 0; j < versionList.size(); j++)
            {
                //如果新数据版本号与旧数据的大版本号一致，停止。questions
                //CCBegin by dikefeng 20090619,在结构中将删除的结构标识出来
                if(filterPartIfc==null&&versionList.size()>0)
                {
                  latestPartIfc = (QMPartIfc) versionList.get(0);
                }
                else
                //CCEnd by dikefeng 20090619
                if(((QMPartIfc) versionList.get(j)).getVersionID().equals(
                        filterPartIfc.getVersionValue()))
                {
                    latestPartIfc = (QMPartIfc) versionList.get(j);
                    break;
                }
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("latestPartIfc==" + latestPartIfc);
            }
            //返回当前版本的最新版序对象。
            //CCBegin by dikefeng 20090619,由于散热器存在直接对结构进行修改,而不修订的情况,
            //所以将获取最新小版本的代码去掉
//            if(latestPartIfc != null)
//            {
//                latestPartIfc = (QMPartIfc) getLatestIteration(latestPartIfc);
//            }
            //CCEnd by dikefeng 20060619
            if(latestPartIfc == null)
            {
                //"获取编号为*的零部件上一个发布版本时出错！"
                throw new QMException(Messages.getString("Util.72",
                        new Object[]{partNumber}));
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getOldUsageLinkMap(QMXMLMaterialSplit) - end"); //$NON-NLS-1$
        }
        return latestPartIfc;
    }

    /**
     * 获得最新版序值对象
     * @param bsoID
     * @return
     * @throws QMException
     */
    private BaseValueIfc getLatestIteration(IteratedIfc iteratedIfc)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestIteration(IteratedIfc) - start"); //$NON-NLS-1$
        }
        BaseValueIfc baseIfc = null;
        VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
        // 刷新最新版序
        baseIfc=(BaseValueIfc)vcservice.getLatestIteration(iteratedIfc);
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestIteration(IteratedIfc) - end"); //$NON-NLS-1$
        }
        return baseIfc;
    }

    /**
     *  获取零部件拆分的物料结构集合。
     * @param parentPartNumber： 父件号。
     * @param parentNumber： 父物料号。
     * @return List 该零部件拆分的物料集合。
     * @throws QMXMLException
     */
    private final List getMatStruct(String parentPartNumber, String parentNumber)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getMatStruct(String, String) - start"); //$NON-NLS-1$
        }
        List resultList = new ArrayList();
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        	resultList=(List)msservice.getMStructure(parentPartNumber, parentNumber);
        }
        catch (QMException e)
        {
            //"查找编号为*，父物料号为*的物料结构时出错！"
            logger.error(Messages.getString("Util.71", new Object[]{
                    parentPartNumber, parentNumber})
                    + e);
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getMatStruct(String, String) - end"); //$NON-NLS-1$
        }
        return resultList;
    }

    /**
     * 根据物料号获取物料。
     * @param materialNumber：物料号
     * @return
     * @throws QMException
     */
    private MaterialSplitIfc getMatSplitIfc(String materialNumber)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getMaterialSplitIfc(String) - start"); //$NON-NLS-1$
        }
        MaterialSplitIfc filterMaterialSplit = null;
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
            //查询该子件的顶级物料集合
        	//System.out.println("materialNumber********************"+materialNumber);
        	filterMaterialSplit=(MaterialSplitIfc)msservice.getMSplit(materialNumber);
        	//System.out.println("filterMaterialSplit********************"+filterMaterialSplit);
        }
        catch (QMException e)
        {
            //"刷新物料号为*的物料时出错！"
            logger.error(Messages.getString("Util.66",
                    new Object[]{materialNumber})
                    + e);
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getMaterialSplitIfc(String) - end"); //$NON-NLS-1$
        }
        return filterMaterialSplit;
    }

    /**
     * 根据零部件号和版本获取零部件。
     * @param partNumber：零部件的编号
     * @param partVersionid：零部件的版本
     * @return
     * @throws QMException
     */
    private Collection getPartCol(String partNumber, String partVersionid)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getQMPart(String, String) - start"); //$NON-NLS-1$
        }
        Collection filterMaterialSplitCol = null;
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            QMQuery query = new QMQuery("QMPart", "QMPartMaster");
            query
                    .addCondition(0, 1, new QueryCondition("masterBsoID",
                            "bsoID"));
            query.addAND();
            query.addCondition(1, new QueryCondition("partNumber",
                    QueryCondition.EQUAL, partNumber));
            query.addAND();
            query.addCondition(0, new QueryCondition("versionID",
                    QueryCondition.EQUAL, partVersionid));
            query.setVisiableResult(1);
            filterMaterialSplitCol=(Collection)pservice.findValueInfo(query);
        }
        catch (QMException e)
        {
            //"查找编号为*，版本为*的零部件时出错！"
            logger.error(Messages.getString("Util.65", new Object[]{partNumber,
                    partVersionid})
                    + e);
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getQMPart(String, String) - end"); //$NON-NLS-1$
        }
        return filterMaterialSplitCol;
    }

    /**
     * 根据零部件号查询过滤表的记录。
     * @param partNumber：零部件编号
     * @return
     * @throws QMException
     */
    private List getFilterPart(String partNumber) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getFilterPartByPartNumber(String) - start"); //$NON-NLS-1$
        }
        List result = null;
        //用编号作为查询条件，查找是否有符合条件的FilterPart。
        PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("FilterPart");
        final QueryCondition condition = new QueryCondition("partNumber",
                QueryCondition.EQUAL, partNumber);
        query.addCondition(condition);
        result=(List)pservice.findValueInfo(query);
        if(logger.isDebugEnabled())
        {
            logger.debug("getFilterPartByPartNumber(String) - end"); //$NON-NLS-1$
        }
        return result;
    }

    /**
     * 获得更改采用通知书关联的零部件。
     * @param bsoID String：更改采用通知书的ID
     * @throws QMException
     * @return List:更改采用通知书关联的零部件的集合
     * @throws QMException
     * @throws QMException
     */
    private List getPartsByAdoptNoticeId(String adoptNoticeBsoID)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByAdoptNoticeId(String) - start"); //$NON-NLS-1$
        }
        //更改采用通知书关联的零部件的集合
        List partList = new ArrayList(0);
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            //1 获得更改采用通知书关联的零部件
            QMQuery query = new QMQuery("QMPart", "AdoptNoticePartLink");
            query.addCondition(0, 1, new QueryCondition("bsoID", "rightBsoID"));
            query.addAND();
            query.addCondition(1, new QueryCondition("leftBsoID",
                    QueryCondition.EQUAL, adoptNoticeBsoID));
            //结果中只返回零部件的信息
            query.setVisiableResult(1);
            //暂时不查询广义部件
            query.setChildQuery(false);
            //更改采用通知书关联的零部件集合
            Collection partCol=(Collection)pservice.findValueInfo(query);
            //2 数据集合放在partList
            if(partCol != null & partCol.size() > 0)
            {
                Iterator partIter = partCol.iterator();
                while (partIter.hasNext())
                {
                    partList.add((BaseValueIfc) partIter.next());
                }
            }
        }
        catch (QueryException e)
        {
            //"构造更改采用通知书*关联的零部件的查询条件时出错！"
            logger.error(Messages.getString("Util.69",
                    new Object[]{adoptNoticeBsoID})
                    + e);
            throw new QMException(e);
        }
        catch (QMException e)
        {
            //"查询更改采用通知书*关联的零部件时出错！"
            logger.error(Messages.getString("Util.70",
                    new Object[]{adoptNoticeBsoID})
                    + e);
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByAdoptNoticeId(String) - end"); //$NON-NLS-1$
        }
        return partList;
    }

    /**
     * 获取原来关联的事物集合中零部件的最新版序集合。
     * 注意：暂时不包括广义部件。
     * @param oldPartList：原来关联的事物集合。
     * @return List：原来零部件的最新版序集合。
     * @throws QMException
     */
    private List getLatestParts(List oldPartList) throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestParts(List) - start"); //$NON-NLS-1$
        }
        //最新版序的零部件集合
        List newPartList = new ArrayList();
        Iterator oldPartIter = oldPartList.iterator();
        BaseValueIfc tempBaseIfc;
        while (oldPartIter.hasNext())
        {
            //1 过滤数据,只提取part,去掉广义部件等其他数据
            tempBaseIfc = (BaseValueIfc) oldPartIter.next();
            if((tempBaseIfc instanceof QMPartIfc)
                    && !(tempBaseIfc instanceof GenericPartIfc))
            {
                try
                {
                    //2 刷新最新版序
                    tempBaseIfc = getLatestIteration((IteratedIfc) tempBaseIfc);
                    //3 数据集合放在newPartList
                    newPartList.add((QMPartIfc) tempBaseIfc);
                }
                catch (QMException e)
                {
                    //"查找编号为*，版本为*的最新零部件版序时出错！"
                    logger.error(Messages.getString("Util.63", new Object[]{
                            ((QMPartIfc) tempBaseIfc).getPartNumber(),
                            ((QMPartIfc) tempBaseIfc).getVersionID()})
                            + e);
                    throw new QMXMLException(e);
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestParts(List) - end"); //$NON-NLS-1$
        }
        return newPartList;
    }

    /**
     *  获取筛选的唯一标识。
     * @param part 零部件。
     * @return String 筛选的唯一标识。
     */
    private final String[] getPartIdentity(final QMPartIfc part)throws QMException
    {
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - start");
        PartHelper partHelper =  new PartHelper();
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
       // if(part.getPartNumber().equals("1701110-A4K")){
            System.out.println("lsbb11111111111111========"+lsbb);
       
//		CCBegin SS3
        String[] aa = new String[2];
//		 获取当前零件的资料夹
       
		String folder = part.getLocation();
		// 技术中心源版本
		String jsbb = partHelper.getPartVersion(part);

       String partnumber = partHelper.getPartNumber(part,folder,lsbb,jsbb);
       if(partNumberMap.containsKey(part.getPartNumber())){
    	   partNumberMap.put(part.getPartNumber(), partnumber);
       }
       
       
        String returnString = partnumber + part.getLifeCycleState().getDisplay() + routeAllCode;
//		CCBEnd SS3
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - end");
        aa[0]=returnString;;
        aa[1]=partnumber;
        return aa;
    }

    /**
     *  获取物料值对象中零部件的唯一标识。
     * @param matSplitIfc 物料值对象。
     * @return String 唯一标识。
     */
    private final String getPartIdentity(final MaterialSplitIfc matSplitIfc)
    {
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - start");
        //CCBegin SS3
        //String returnString = matSplitIfc.getPartNumber() + matSplitIfc.getPartVersion() + matSplitIfc.getState() + matSplitIfc.getRoute();
        String returnString = matSplitIfc.getPartNumber()  + matSplitIfc.getState() + matSplitIfc.getRoute();
        //CCEnd SS3
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - end");
        return returnString;
    }
    /**
     * 设置零部件使用结构发布时的物料结构信息。
     * added by dikefeng 20100421,在这里添加一个物料到零部件关联的方法
     * @param rootPartNumber 最顶级物料的零部件编号
     * @param rootPartVersionID 最顶级物料的零部件版本
     * @param strctPublishType 物料结构发布标识
     * @param xmlMatSplit 父物料的XML形式
     * @param partUsageLinkIfc 零部件使用关系
     * @throws QMException
     */
    public void setDeleteMaterialPartStructure(MaterialPartStructureIfc mpsi)
            throws QMException
    {
    	
        //物料结构值对象
        MaterialStructureIfc matStructIfc = new MaterialStructureInfo();
        matStructIfc.setParentPartNumber(mpsi.getParentPartNumber());
        matStructIfc.setParentPartVersion(mpsi.getParentPartVersion());
        matStructIfc.setParentNumber(mpsi.getParentNumber());
        matStructIfc.setLevelNumber("0");
        matStructIfc.setDefaultUnit(mpsi.getDefaultUnit());
        matStructIfc.setQuantity(mpsi.getQuantity());
        matStructIfc.setOptionFlag(mpsi.getOptionFlag());
        QMXMLMaterialStructure xmlMatStruct = new QMXMLMaterialStructure();
        matStructIfc.setChildNumber(mpsi.getChildNumber());
        xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
//        System.out.println("设置上了么");
        xmlMatStruct.setStructurePublishType("D");
//        System.out.println("那还用说么");
        xmlMatStructList.add(xmlMatStruct);
//        System.out.println("创建删除。。。。");
    }
    /**
     * 设置零部件使用结构发布时的物料结构信息。
     * @param rootPartNumber 最顶级物料的零部件编号
     * @param rootPartVersionID 最顶级物料的零部件版本
     * @param strctPublishType 物料结构发布标识
     * @param xmlMatSplit 父物料的XML形式
     * @param partUsageLinkIfc 零部件使用关系
     * @throws QMException
     */
    public void setPartStructPubType(String rootPartNumber,
            String rootPartVersionID, String strctPublishType,
            QMXMLMaterialSplit xmlMatSplit, PartUsageLinkIfc partUsageLinkIfc)
            throws QMException
    {
        //物料结构值对象
        MaterialStructureIfc matStructIfc = new MaterialStructureInfo();
        matStructIfc.setParentPartNumber(xmlMatSplit.getPartNumber());
        if(xmlMatSplit.getPartNumber().equals(rootPartNumber))
        {
            matStructIfc.setParentPartVersion(rootPartVersionID);
        }
        else
        {
            matStructIfc.setParentPartVersion(xmlMatSplit.getPartVersionID());
        }
        matStructIfc.setParentNumber(xmlMatSplit.getMaterialNumber());
        matStructIfc.setLevelNumber("0");
        matStructIfc.setDefaultUnit(partUsageLinkIfc.getDefaultUnit()
                .getDisplay());
        matStructIfc.setQuantity(partUsageLinkIfc.getQuantity());
        matStructIfc.setOptionFlag(partUsageLinkIfc.getOptionFlag());
        //查询顶级子件的顶级物料
        List rootMatNumberList = getRootMatSplit(partUsageLinkIfc
                .getLeftBsoID(), xmlMatSplit.getRouteCode());
        //零部件结构的XML形式
        QMXMLMaterialStructure xmlMatStruct = new QMXMLMaterialStructure();
        String childNumber = "";
        for (int i = 0; i < rootMatNumberList.size(); i++)
        {
            childNumber = (String) rootMatNumberList.get(i);
            matStructIfc.setChildNumber(childNumber);
            xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
            xmlMatStruct.setStructurePublishType(strctPublishType);
            xmlMatStructList.add(xmlMatStruct);
        }
    }

    /**
     * 获取最顶层物料
     * @param childBsoID：子件的MasterID
     * @param parentRouteCode：父物料的路线代码
     * @return
     * @throws QMException
     */
    public List getRootMatSplit(String childBsoID, String parentRouteCode)
            throws QMException
    {
        List rootMatNumberList = new ArrayList();
        if(!rootMatSplitMap.containsKey(childBsoID))
        {
            //查询子件的Master值对象
            try
            {
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
                //1. 首先查询到子件最新版本
                QMPartMasterIfc childPartIfc = null;
                final QMQuery query = new QMQuery("QMPartMaster");
                final QueryCondition condition2 = new QueryCondition("bsoID",
                        QueryCondition.EQUAL, childBsoID);
                query.addCondition(condition2);
                //暂时不考虑广义部件的情况。
                query.setChildQuery(false);
                List partMasterList = (List) pservice.findValueInfo(query);
                if(partMasterList != null && partMasterList.size() > 0)
                {
                    childPartIfc = (QMPartMasterIfc) partMasterList.get(0);
                }
                else
                {
                    //"获取BsoID为*的零部件主信息时出错！"
                    logger.error(Messages.getString("Util.67",
                            new Object[]{childBsoID}));
                    throw new QMException(Messages.getString("Util.67",
                            new Object[]{childBsoID}));
                }
                //2 查询该子件的顶级物料集合
                //CCBegin SS4
                String partnumber = (String) partNumberMap.get(childPartIfc.getPartNumber());
                if(partnumber==null||partnumber.length()==0){
                	PartHelper helper = new PartHelper();
                	partnumber=helper.getPartNumber(childPartIfc);
                }
                
                Collection rootList = (Collection)msservice.getRootMSplit(partnumber);
//              CCEnd SS4
                Iterator iter = rootList.iterator();
                //是否需要匹配的标志
                boolean isMatching = RemoteProperty.getProperty(
                        "com.faw_qm.erp.isMatching", "false").equalsIgnoreCase(
                        "true");
                MaterialSplitIfc matSplitIfc;
                while (iter.hasNext())
                {
                    matSplitIfc = (MaterialSplitIfc) iter.next();
                    //如果需要匹配，则该物料下挂接的子件的路线代码必须与父物料的路线代码一致，
                    //如果子物料的所有顶级物料都不满足这个条件，则不挂接该子件
                    if(isMatching)
                    {
                        if(matSplitIfc.getRouteCode().equals(parentRouteCode))
                        {
                            rootMatNumberList.add(matSplitIfc
                                    .getMaterialNumber());
                            break;
                        }
                    }
                    else
                    {
                        rootMatNumberList.add(matSplitIfc.getMaterialNumber());
                    }
                }
                //3 当不需要匹配且子物料的顶级物料（没有拆分子件）时，则子物料号为零部件号
                if(!isMatching && rootMatNumberList.size() <= 0)
                {
                    rootMatNumberList.add(childPartIfc.getPartNumber());
                }
                rootMatSplitMap.put(childBsoID, rootMatNumberList);
            }
            catch (QMException e)
            {
                throw new QMException(e);
            }
        }
        else
        {
            rootMatNumberList = (List) rootMatSplitMap.get(childBsoID);
        }
        return rootMatNumberList;
    }
    //CCbegin SSS
//  获得一级子件
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
				throw new PartException("零件号不存在", "CP00001", null);
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
	 //
}
