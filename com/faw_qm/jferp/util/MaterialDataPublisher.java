/**
 * 生成程序MaterialDataPublisher.java	1.0              2007-9-28
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 partnumber不带版本 刘家坤 2014-09-12
 * SS2 由于解放存在多个版本。所以只查询partnumber
 * 找顶级物料不正确，需要找materialNumber并且带版本号 刘家坤 2014-09-29
 * SS3 在工艺bom里给零件添加生产版本，如果有生产版本则获取生产版本 刘家坤 2014-10-08
 * SS4 在工艺bom里获取零部件下级结构按，单级bom方式获取 2014-11-22
 * SS5 发布bom时，xml只输出bom信息，工艺路线发布，只输出物料信息 2014-11-20
 * SS6 集合中唯一性判断去掉路线和生命周期状态 2014-12-15
 * SS7 BOM不发半成品关系 2014-12-20
 * SS8 不论物料是U还是N都需要进行判断结构是否存在 2014-12-20
 * SS9 发布路线列表中，有被过滤掉的数据，也显示出来了。 2015-09-15
 */
package com.faw_qm.jferp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
//import com.faw_qm.adoptnotice.model.AdoptNoticeIfc;
import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.jferp.ejb.service.MaterialSplitService;
import com.faw_qm.jferp.ejb.service.PromulgateNotifyService;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.FilterPartIfc;
import com.faw_qm.jferp.model.FilterPartInfo;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.jferp.model.MaterialSplitInfo;
import com.faw_qm.jferp.model.MaterialStructureIfc;
import com.faw_qm.jferp.model.MaterialStructureInfo;
import com.faw_qm.jferp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
//CCBegin by dikefeng 20100421
import com.faw_qm.jferp.model.*;
//CCEnd by dikefeng 20100421
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
       
        StandardPartService spService = null;
        TechnicsRouteService tsService = null;
		try {
			spService = (StandardPartService) EJBServiceHelper.getService("StandardPartService");
		    tsService = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
		    boolean bool = false;
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
            
                String partNumber = xmlMatSplit.getPartNumber();
                int a = partNumber.indexOf("/");
                if(a>=0){
                	partNumber= partNumber.substring(0, a);
                }
                filterPartIfc.setPartNumber(partNumber);
                filterPartIfc.setState(xmlMatSplit.getState());
                filterPartIfc.setVersionValue(xmlMatSplit.getPartVersionID());
                filterPartIfc.setRoute(xmlMatSplit.getRoute());
//              CCBegin SS3
                String materialRoute = xmlMatSplit.getRoute();
                //System.out.println("materialRoute1111111111==="+materialRoute);
               
                //获取零部件值对象
                PartHelper helper = new PartHelper();
                QMPartIfc part = helper.getPartByPartNumber(partNumber);
                // 刘家坤解放 20140628 屏蔽此处，修改代码

              //  System.out.println("parttttttttttttt==="+part);
                String routeStr  = null;
                Vector routevec = new Vector();
              	routevec=RequestHelper.getRouteBranchs(part, null);
              	//System.out.println("routevec1111111==="+routevec);
	                //最新路线串
              	if(routevec.size() != 0)
	                 routeStr  = (String)routevec.elementAt(1);
	               
               // System.out.println("routeStr1111111111==="+routeStr);
                //判断路线串是否需要保存
                //System.out.println("100bool111111111==="+bool);
                if(((routeStr!=null)&&(materialRoute!=null)&&materialRoute.equals(routeStr))||
                		((routeStr==null||routeStr.length()==0)&&(materialRoute==null||materialRoute.length()==0))){
//                	bool = true;
                	 //System.out.println("该路路线串可以进行保存");
                	 filterPartList.add(filterPartIfc);
                }
               
                
               
//              CCEnd SS3
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
            {//CCBegin SS3
//            	if(bool){
                pservice.saveValueInfo((BaseValueIfc)filterPartList.get(i));
//            	}//CCEnd SS3
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
		} catch (QMException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
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
    	//System.out.println("publishSourseObject=============+++=="+ publishSourseObject);
    	
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - start"); //$NON-NLS-1$
        }
        //通过采用通知书或更改采用通知书获取关联的零部件。
        List partList = new ArrayList();
        logger
                .debug("publishSourseObject==============="
                        + publishSourseObject);
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
        	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
          if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
            {
                //返回的结果集是第一个位置为Arraylist变更后的结果,第二个位置变更前的结果。
            	QMPartIfc qmpartifc;
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
				
            	for(Iterator iter = coll.iterator(); iter.hasNext(); partList.add(qmpartifc))
				{
					 qmpartifc = (QMPartIfc)iter.next();
					//qmpartifc = (QMPartIfc)pservice.refreshInfo(partID);
				}
				//CCBegin by dikefeng 20100423,，为了使得在发布的时候知道本次新发的物料都有哪些
           
				if(this.filterParts!=null&&filterParts.size()>0)
				{
					System.out.println("按照工艺bom发布走到了正确的分支里，需要新发布的零部件数量为："+filterParts.size());
					PersistService pService=(PersistService)EJBServiceHelper.getService("PersistService");
					for(int i=0;i<filterParts.size();i++)
					{
						partList.add((QMPartIfc)pService.refreshInfo((String)filterParts.get(i)));
					}
				}else
				{
					return;
//                partList.addAll(getpartsByBaseline((ManagedBaselineIfc)publishSourseObject,this.coll));
				}
                //CCEnd by dikefeng20100423
                String nu=getXmlName();
                if(nu.equalsIgnoreCase("caiyong"))
            	{
                	sourceType="采用通知";
            	}
            	else if(nu.equalsIgnoreCase("biangeng")) 
            	{
            		sourceType="变更通知";
            	}
            	else
            	{
            		sourceType="";
            	}
            }
            else if(publishSourseObject instanceof TechnicsRouteListIfc)
            {
	//CCBegin SS9
            	
            	partList = (List)getPartbyFilter(filterParts); 
            	//partList = (List)getPartByRouteListJF((TechnicsRouteListIfc)publishSourseObject);
            	 System.out.println("partList="+partList);
            	//CCEnd SS9

//                System.out.println("hainengzoume??????????????????????????????????????????????");
                String nu = getXmlName();
                if(nu.equalsIgnoreCase("caiyong"))
                {
                    sourceType = "采用通知";
                }
                else if(nu.equalsIgnoreCase("biangeng"))
                {
                    sourceType = "变更通知";
                }
                else
                {
                    sourceType = "";
                }
            }
            else{
            	QMPartIfc qmpartifc;
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
//            	System.out.println("coll=++++++++++"+coll);
//             	System.out.println("filterParts=++++++++++"+filterParts);
            	for(Iterator iter = coll.iterator(); iter.hasNext(); partList.add(qmpartifc))
				{
					 qmpartifc = (QMPartIfc)iter.next();
					//qmpartifc = (QMPartIfc)pservice.refreshInfo(partID);
				}
				//CCBegin by dikefeng 20100423,，为了使得在发布的时候知道本次新发的物料都有哪些
				if(this.filterParts!=null&&filterParts.size()>0)
				{
					//System.out.println("按照虚拟件发布走到了正确的分支里，需要新发布的零部件数量为："+filterParts.size());
					PersistService pService=(PersistService)EJBServiceHelper.getService("PersistService");
					for(int i=0;i<filterParts.size();i++)
					{
						partList.add((QMPartIfc)pService.refreshInfo((String)filterParts.get(i)));
					}
				}else
				{
					return;
//                partList.addAll(getpartsByBaseline((ManagedBaselineIfc)publishSourseObject,this.coll));
				}
				//System.out.println("partList=++++++++++"+partList);
                //CCEnd by dikefeng20100423
                String nu=getXmlName();  
            		sourceType="";
            	
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
        sourceType = "";
        //"关联*条零部件信息。"
        logger.fatal(sourceType
                + Messages.getString("Util.61", new Object[]{String
                        .valueOf(partList.size())}));
        if(logger.isDebugEnabled())
        {
            logger.debug("partList==" + partList);
        }
        //解放 刘家坤 20140624刷新最新版本questions,不需要刷最新版本吧，
       // partList = getLatestParts(partList);
        //根据筛选结果表记录保存规则处理零部件。并将筛选后的QMXMLMaterialSplit集合存入xmlMaterialSplitList中。
        //根据物料处理规则处理物料。并将标记为修改的零部件对应的filterPart放到filterPartMap中。
        //根据结构处理规则处理结构，并将筛选后的QMXMLStructure集合存入xmlStructureList中。
        try
        {
            filterMaterials(partList);
        }
        catch (Exception e)
        {
            //"处理物料的发布数据时出错！"
            logger.error(Messages.getString("Util.68"), e);
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("物料处理规则后xmlPartList＝＝" + xmlMatSplitList);
            logger.debug("物料处理规则后updatePartMap＝＝" + filterPartMap.size());
            logger.debug("结构处理规则过滤后xmlStructureList＝＝" + xmlMatStructList);
        }
        //将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
        //CCBegin SS5
        if(publishSourseObject instanceof GYBomAdoptNoticeIfc){
        	xmlMatSplitList = new ArrayList();
        	
        }
//        System.out.println("物料处理规则后xmlPartList＝＝" + xmlMatSplitList);
//        System.out.println("结构处理规则过滤后xmlStructureList＝＝" + xmlMatStructList);
        setDataRecord(xmlMatSplitList, xmlMatStructList);
        //CCEnd SS5
        //将过滤后零部件结果集合设置给基本发布器，为保存筛选结果做准备。
        BaseDataPublisher.xmlPartList = xmlMatSplitList;
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - end"); //$NON-NLS-1$
        }
    }
    //解放刘家坤获取路线关联零件
    public Collection getPartByRouteListJF(TechnicsRouteListIfc list)
	throws QMException
	{
    	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		Collection c = new Vector();
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
		query.addCondition(cond);
	    //CCBegin by chudaming 20101222
		query.addAND();
		QueryCondition condition11 = new QueryCondition("adoptStatus",
				QueryCondition.NOT_EQUAL, "CANCEL");
		query.addCondition(condition11);
		//CCEnd by chudaming 20101222
		 PartHelper helper = new PartHelper();
//		添加制造视图最新版本
       // PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("工艺视图");
		Collection coll = ps.findValueInfo(query);
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			QMPartInfo partInfo = (QMPartInfo)linkInfo.getPartBranchInfo();
			c.add(partInfo);
		}
		return c;
	}
	  //CCBegin SS9
    /**
     * 对过滤后的路线关联零件进行处理，转换为值对象
     *
     * @param Collection
     *            过滤后的零件集合
     * @return Collection 获得的值对象集合
     */
    public Collection getPartbyFilter(Collection coll)
	throws QMException
	{
    	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    	Collection c = new Vector();
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			String partID = (String)iter.next();
			QMPartInfo partInfo = (QMPartInfo)ps.refreshInfo(partID);
			c.add(partInfo);
		}
		return c;
	}
    //CCEnd SS9
	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		Collection c = new Vector();
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
		query.addCondition(cond);
	    //CCBegin by chudaming 20101222
		query.addAND();
		QueryCondition condition11 = new QueryCondition("adoptStatus",
				QueryCondition.NOT_EQUAL, "CANCEL");
		query.addCondition(condition11);
		//CCEnd by chudaming 20101222
		 PartHelper helper = new PartHelper();
//		添加制造视图最新版本
        PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("工艺视图");
		Collection coll = ps.findValueInfo(query);
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			Collection cc = getPartByListRoutepart(linkInfo);
			QMPartIfc partInfo = null;
			Iterator ii = cc.iterator();
			 while (ii.hasNext()) {
				 //CCBegin SS1
				 QMPartIfc partInfo_old = (QMPartIfc)ii.next();
					partInfo_old = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)partInfo_old.getMaster() , configSpecGY);
		            partInfo = helper.filterLifeState(partInfo_old);
//				 System.out.println("101010110111171777777ccccchhhhuuuu11111111==11111111="+partInfo.getVersionID());
		            //CCEnd SS1
				 Vector aaa=new Vector();
				 aaa=RequestHelper.getRouteBranchs(partInfo, null);
				 //CCBegin by chudaming 20100920
				 QMQuery qmquerykk1 = new QMQuery("JFFilterPart");
	 			 QueryCondition condition121 = new QueryCondition("versionValue", "=",
	 					partInfo.getVersionID());
	 			qmquerykk1.addCondition(condition121);
	 			qmquerykk1.addAND();
	 			//0727
//	 			QueryCondition condition1211 = new QueryCondition("route", "=",
//	 					aaa.get(1).toString());
//	 			qmquerykk1.addCondition(condition1211);
//	 			qmquerykk1.addAND();
	 			qmquerykk1.addCondition(new QueryCondition("partNumber", "=",
	 					partInfo.getPartNumber()));
	 			//CCBegin by chudaming 20101021 保证和最后一次的FilterPart记录进行对比
	 			qmquerykk1.addOrderBy("createTime",true);
	 			
	 			//CCEnd by chudaming 20101021 保证和最后一次的FilterPart记录进行对比
	 			//CCBegin by chudaming 20101022
//	 			System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+aaa);
//	 			System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+aaa.size());
	 	          Collection col1 = ps.findValueInfo(qmquerykk1, false);
	 	         
//	 	        System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+col1);

	 	         for (Iterator ii22 = col1.iterator();ii22.hasNext();) {
	 	        	FilterPartIfc   aaaaaa=(FilterPartIfc)ii22.next();
//	 	        	System.out.println("1009aaaaaa===="+aaaaaa.getRoute());
//	 	        	System.out.println("1009aaa===="+aaa.get(1).toString());
	 	        	if(aaa.size()!=0&&aaaaaa.getRoute()!=null){
	 	        		//CCBegin by chudaming 20101017
	 	        	//System.out.println("wwwwwwwwwwwwwwww"+aaa);
	 	        	 if(!(aaaaaa.getRoute().equals(aaa.get(1).toString()))){
	 	        		c.add(partInfo);
	 	        	 }
	 	        	}else if(aaa.size()!=0&&aaaaaa.getRoute()==null){
//	 	        		if(aaaaaa.getRoute()!=null){
	 	        			c.add(partInfo);
//	 	        		}
	 	        	}else if(aaa.size()==0&&aaaaaa.getRoute()!=null){
	 	        		c.add(partInfo);
	 	        	}
	 	        	//CCBegin by chudaming 20101021 保证和最后一次的FilterPart记录进行对比
	 	        	break;
	 	        	//CCEnd by chudaming 20101021 保证和最后一次的FilterPart记录进行对比
	 	    
	 	         }
	 	         
				
				 
			 }


		}
		//System.out.println("1006cccccccccccccccccccccccccccccc===="+c);
		return c;
	}
    
	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		QMPartInfo part = (QMPartInfo)linkInfo.getPartBranchInfo();
		QMQuery query = new QMQuery("QMPart");
		QueryCondition qc = new QueryCondition("bsoID", "=", part.getBsoID());
		query.addCondition(qc);
		// 做a件A版本，发a版本bom，做a件A版本路线，修订a件，发a件的B版本，再发a版本的艺准，要求带着B版本的结构拆分。
		//刘家坤获取路线关联零件版本，轴齿路线关联零件具体版本 20131227
		String partVersion = part.getVersionID();
		if(partVersion != null && partVersion.length() > 0)
		{
//			Vector a=new Vector();
			QMQuery qmquery = new QMQuery("JFFilterPart");
			 
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
	
//	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
//	throws QMException
//	{
//		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
//		QMPartMasterInfo partmaster = (QMPartMasterInfo)ps.refreshInfo(linkInfo.getRightBsoID());
//		QMQuery query = new QMQuery("QMPart");
//		QueryCondition qc = new QueryCondition("masterBsoID", "=", partmaster.getBsoID());
//		query.addCondition(qc);
//		if(linkInfo.getPartVersion() != null && linkInfo.getPartVersion().length() > 0)
//		{
//			QueryCondition qc1 = new QueryCondition("versionID", "=", linkInfo.getPartVersion());
//			query.addAND();
//			query.addCondition(qc1);
//		}
//		QueryCondition qc2 = new QueryCondition("iterationIfLatest", true);
//		query.addAND();
//		query.addCondition(qc2);
//		return ps.findValueInfo(query, false);
//	}
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
            partIdentity = getPartIdentity(partIfc);
//            System.out.println("djbom000000000==========="+djbom);
//            System.out.println("partsMap000000000==========="+partsMap);
//            System.out.println("partsMap000000000==========="+partsMap+"***partIdentity000000000==========="+partIdentity);
            if(!partsMap.containsKey(partIdentity))
            {
                    partsMap.put(partIdentity, partIfc);
                    //设置下级物料和物料结构的发布数据
                    setSubMaterial(partIfc, null, partsMap);

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
        PartHelper partHelper = new PartHelper();
        if(filterPartList != null && filterPartList.size() > 0)
        {
            for (int j = 0; j < filterPartList.size(); j++)
            {
                filterPartIfc = (FilterPartIfc) filterPartList.get(j);
                //CCBegin SS2
                int compare = partHelper.compareVersion(partIfc.getVersionID(), filterPartIfc.getVersionValue());
                if(compare == 2)
                {
                    tempPartList.add(partIfc);
                    break;
                } //CCEnd SS2
                else
                {
                    if(tempFilterPart == null)
                    {
                        tempFilterPart = filterPartIfc;
                    }//CCBegin SS2
                    else if(partHelper.compareVersion(tempFilterPart.getVersionValue(),filterPartIfc.getVersionValue()) ==2)
                    { //CCEnd SS2
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
                    MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
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
            HashMap partMap) throws QMException
    {
    	PartHelper parthelper =  new PartHelper();
//    	CCBegin SS2
    	 String partVersion ="";
    	 //System.out.println("scbb=============="+scbb);
		 if(scbb.size()>0){
			 partVersion = (String) scbb.get(partIfc.getPartNumber());
			 if(partVersion==null){
				 partVersion = "";
			 }
		 }
		 if(partVersion==null||partVersion.length()==0){
			 partVersion = PartHelper.getPartVersion(partIfc);
			 if(partVersion==null){
				 partVersion = "";
			 }
		 }
//		CCEnd SS2
//    	// 获取源版本
//		String partVersion = PartHelper.getPartVersion(partIfc);
		//CCBegin SS2
		 //此处partnumber不正确就可能导致生成不了xml文件
		 //String partnumber =parthelper.getMaterialNumber(partIfc,partVersion);
		 String partnumber ="";
		 //根据串进来的单级bom数据获取物料编号
		 if(djbom!=null&&djbom.size()>0){
			//  partnumber =getMaterialNumber(partIfc,partVersion);
			  Iterator iter = djbom.iterator();
		        while (iter.hasNext())
		        {
		            Object[] obj = new Object[5];
			    	obj = (Object[])iter.next();
			    	partnumber=(String) obj[8]; //编号带版本
		            QMPartIfc partIfcnew = (QMPartIfc)obj[0]; 
		            if(partIfcnew.getPartNumber().equals(partIfc.getPartNumber()))

		              break;
		           
		           
		        }
		 }else{
			  partnumber =parthelper.getMaterialNumber(partIfc,partVersion);
		 }
		 //String partnumber = partIfc.getPartNumber() + "/" + partVersion;
    	//String partnumber = partIfc.getPartNumber();
    	//CCEnd SS2
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMaterial(String, String, String , HashMap) - start");
        }
       // System.out.println("partnumber111111111=============="+partnumber);
        MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
        //物料值对象
        MaterialSplitIfc matSplitIfc;
        //物料值对象的XML形式
        QMXMLMaterialSplit xmlMatSplit;
        //零部件拆分的物料的最顶级物料
        Collection rootList=(Collection)msservice.getRootMSplit(partnumber,partVersion);
       //System.out.println("rootList11111111111=============="+rootList);
        Iterator rootMatSplitIter = rootList.iterator();
        HashMap notPublishMat=new HashMap();
        //加上该零部件拆分的物料
        while (rootMatSplitIter.hasNext())
        {
            matSplitIfc = (MaterialSplitIfc) rootMatSplitIter.next();
            //20080122 begin
            boolean flag=isParentHasSpeRoute(matSplitIfc,partIfc);
            logger.debug("flag is "+flag);
            logger.debug("matSplitIfc.getPartNumber() is "+matSplitIfc.getPartNumber());
            logger.debug("partIfc.getPartNumber() is "+partIfc.getPartNumber());
            if(notPublishMat.containsKey(matSplitIfc.getPartNumber())||
            		!flag){
            	notPublishMat.put(matSplitIfc.getPartNumber(), matSplitIfc.getPartNumber());
            	continue;
            }
             matSplitIfc.setPartVersion(partVersion);
            // System.out.println("matSplitIfc1111111111==========="+matSplitIfc);
            // System.out.println("hasSetSplitPubTypeMap1111111111==========="+hasSetSplitPubTypeMap);
            if(!hasSetSplitPubTypeMap.containsKey(matSplitIfc
                    .getMaterialNumber()))
            {
                xmlMatSplit = new QMXMLMaterialSplit(matSplitIfc);
                xmlMatSplit.setPartIfc(partIfc);
//                System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getBsoID());
//                System.out.println("xmlMatSplitxmlMatSplit0000000000000000022222222222222222222222=============="+matSplitIfc.getPartVersion());
//                System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getMaterialSplitType());
                xmlMatSplit.setPublishType(matSplitIfc.getMaterialSplitType());
                xmlMatSplit.setPartVersionID(matSplitIfc.getPartVersion());
                xmlMatSplitList.add(xmlMatSplit);
                hasSetSplitPubTypeMap.put(matSplitIfc.getMaterialNumber(),
                        xmlMatSplit);
               // System.out.println("xmlMatSplitList==========="+xmlMatSplitList);
//                System.out.println("nizhenniu555555555555555555555555555555------------------------buxinzhaobudaoni--------------");
               //解放刘家坤 20140624 原来是partIfc.getversionid();修改为matSplitIfc.getPartVersion()
              //  System.out.println("xmlMatSplit==========="+xmlMatSplit);
                setSubMatStructPubType(partnumber, matSplitIfc
                        .getPartVersion(), xmlMatSplit, partMap);
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
    
        if(xmlMatSplit.getMaterialSplit().getStatus() == 1)//
        {
        	//CCBegin SS7
        	//BOM不发半成品关系
        	if(djbom==null){
//        		CCEnd SS7
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

                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        //CCEnd by dikefeng 20100422
                        hasSetStrutPublishType.put(matStructIfc.getBsoID(),
                                xmlMatStruct);
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
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
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                           // subXMlMatSplit.setPublishType(publishType);
                            //CCBegin by chudaming 20100331questions？？？
//                            System.out.println("fdsfsdfdsfsdfsdf");
                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                            //CCEnd by chudaming 20100331
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//                            subXMlMatSplit.setPartVersionID(partVersionID);
                            hasSetSplitPubTypeMap.put(subMatSplitIfc
                                    .getMaterialNumber(), subXMlMatSplit);
                            xmlMatSplitList.add(subXMlMatSplit);
                            setSubMatStructPubType(rootPartNumber,
                                    rootPartVersionID, subXMlMatSplit, partMap);
                        }
                    }
                }
                //CCBegin by dikefeng 20100421，当前物料状态标记为1时，说明，他是有属于本零件的下一级物料，那么只要他曾经有与下级零件的关系
                //则删除,如果状态标记为2或0则说明无属于本零件的下级物料。为2时，应该将当前子件结构与旧结构进行比较，将本次没有的结构标记为D
                //本次有的数量没有变化，标记为o，本次有但是数量变化了的标记为U；为0时，说明没有下级零件，则如果之前的发布中有下级路线
                //直接将所有结构标记为D
                //路线变了，所以需要将历史结构进行删除
                QMQuery materialPartStruQuery=new QMQuery("JFMaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
                Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator mpsIte=mpsColl.iterator();
                while(mpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//                	System.out.println("kuku啦啦啦啦来了么？-------------------------------------------");
                	setDeleteMaterialPartStructure(mpsIfc);
                	pService.deleteValueInfo(mpsIfc);
                }
                
        	}
           
        }
        //设置零部件使用结构的发布标记
        //CCBegin by dikefeng 20090619,发布子件结构时，需要发布被删除的结构信息
        else if(xmlMatSplit.getMaterialSplit().getStatus() == 2||xmlMatSplit.getMaterialSplit().getStatus() == 0)
        //CCEnd by dikefeng 20090619
        {
        	if(djbom!=null){
        		filterByStructureRule(rootPartNumber, rootPartVersionID,
                        xmlMatSplit,partMap);
        	}
            
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
        //刘家坤此处partMap中没有就会出现问题
//    	System.out.println("partIdentity="+partIdentity); 
//    	System.out.println("partMap="+partMap); 
        if(partMap.containsKey(partIdentity))
        {
            partIfc = (QMPartIfc) partMap.get(partIdentity);

        }
        //如果零部件不在parts中，则从数据库中刷新
        else
        {
//        	System.out.println("matSplitIfc.getPartNumber()="+matSplitIfc.getPartNumber()); 
//        	System.out.println("matSplitIfc.getPartVersion()="+matSplitIfc.getPartVersion()); 
        	String[] a = matSplitIfc.getPartNumber().split("/");
        	String partnumber= a[0];
        	//System.out.println("partnumber="+partnumber); 
            Collection partCol = getPartCol(partnumber,
                    matSplitIfc.getPartVersion());
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
      //  System.out.println("publishTypepublishType====="+publishType+"xmlMatSplitxmlMatSplitxmlMatSplit222"+xmlMatSplit.getMaterialNumber());
        //新的使用关系的值对象
        PartUsageLinkIfc newUsageLinkIfc;
        //原的使用关系的值对象
        PartUsageLinkIfc oldUsageLinkIfc;
        //临时的使用关系的值对象
        PartUsageLinkIfc tempUsageLinkIfc;
        //发布的零部件的使用结构
        //CCBegin SS1
        HashMap newUsageLinkMap = getUsageLinkMap(xmlMatSplit.getPartIfc());
  
//    	   System.out.println("xmlMatSplit.getPartIfc()11111111111="+xmlMatSplit.getPartIfc().getPartNumber());
//          
        //  System.out.println("newUsageLinkMap="+newUsageLinkMap);
        //如果没有下级件不查询。刘家坤20141222
          if(newUsageLinkMap==null||newUsageLinkMap.size()==0)
        	  return;
//           System.out.println("publishType111111="+publishType);
         
     //CCBEgin SS8
        //CCEnd SS1
        //结构处理规则1。数据为新增时，结构信息中所有子件发布，结构关系标记为新增N。questions
        //全部发布为 N没有问题，只不过，如果物料是因为路线变化而发布为N，则应该将原子节点路线所带结构删除
    //   if(publishType.equals("N"))
//        {
//            if(logger.isDebugEnabled())
//            {
//                logger.debug("---------结构处理规则1");
//            }
//            //设置零部件结构的更改标记:
//            //当物料的更改标记为新增时，零部件结构的更改标记和物料的更改标记一致。
//           
//            if(xmlMatSplit.getMaterialSplit().getStatus()==2)
//            {
//            Iterator usageLinkIter = newUsageLinkMap.values().iterator();
//            while (usageLinkIter.hasNext())
//            {
//              //  newUsageLinkIfc = (PartUsageLinkIfc) usageLinkIter.next();
//            	String[] aa = (String[]) usageLinkIter.next();
//                setPartStructPubType(rootPartNumber, rootPartVersionID, "N",
//                        xmlMatSplit, aa);
//            //在这里需要为新的结构创建缓存关联
//            //CCBegin by dikefeng  20100421,缓存关联
//             MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
//             mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
//             mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
//             mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
//             mpsInfo.setLevelNumber("0");
//             mpsInfo.setDefaultUnit("件");
//             mpsInfo.setMaterialStructureType("O"); 
//             mpsInfo.setOptionFlag(false);
////             System.out.println("xmlMatSplit.getMaterialSplit().getPartNumber()11111111111="+xmlMatSplit.getMaterialSplit().getPartNumber());
////             System.out.println("aa[0]222222="+aa[0]);
////             System.out.println("aa[3]222222="+aa[3]);
//
//             mpsInfo.setQuantity(Float.parseFloat(aa[3]));
//             //刘家坤解放  修改 20140609
////             mpsInfo.setSubGroup(newUsageLinkIfc.subGroup);
////             mpsInfo.setBOMLine(newUsageLinkIfc.BOMLine);
//             List rootMatNumberList = getRootMatSplit(aa, xmlMatSplit.getRouteCode());
//         //	System.out.println("00000000000000000000kuku啦啦啦啦来了么？-------------------------------------------");
//             for (int i = 0; i < rootMatNumberList.size(); i++)
//             {
//                 String childNumber = (String) rootMatNumberList.get(i);
//               //  System.out.println("childNumber11111111111="+childNumber);
//                 mpsInfo.setChildNumber(childNumber);
//                 pService.saveValueInfo(mpsInfo); 
//             }
//            //CCEnd by dikefeng 20100421
//            }
//            }
//        }//CCEnd SS8
         if(publishType.equals("D"))
        {
        	//CCBegin by dikefeng 20100421，当前物料状态标记为1时，说明，他是有属于本零件的下一级物料，那么只要他曾经有与下级零件的关系
            //则删除,如果状态标记为2或0则说明无属于本零件的下级物料。为2时，应该将当前子件结构与旧结构进行比较，将本次没有的结构标记为D
            //本次有的数量没有变化，标记为o，本次有但是数量变化了的标记为U；为0时，说明没有下级零件，则如果之前的发布中有下级路线
            //直接将所有结构标记为D
        	if(xmlMatSplit.getMaterialSplit().getStatus()==2)
        	{
            QMQuery materialPartStruQuery=new QMQuery("JFMaterialPartStructure");
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
            
        }
        //结构处理规则2。数据为版本变化时，将数据的子件与表中该数据的版本的子件进行比较。
        else if(publishType.equals("U")||publishType.equals("N"))
       
        {
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
            	//System.out.println("xmlMatSplit.getMaterialSplit().getStatus()=========0000000");
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

                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
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
            	////解放刘家坤 20140624 erp要求旧版本数据也保留，此处部进行删除
            	QMQuery materialPartStruQuery=new QMQuery("JFMaterialPartStructure");
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
            	//System.out.println("44444444444444444444kuku啦啦啦啦来了么？----------------------2222222222");
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
            	//System.out.println("xmlMatSplit.getMaterialSplit().getStatus()============22222222222========"+oldMaterialList);
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
  
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
                        //如果子物料为空，则不发布
                        if(subMatSplitIfc==null)
                        	continue;
//                        System.out.println("matStructIfc.getChildNumber()========"+matStructIfc.getChildNumber());
//                        System.out.println("subMatSplitIfc========"+subMatSplitIfc);
//                        System.out.println("subMatSplitIfc.getPartNumber()========"+subMatSplitIfc.getPartNumber());
                        //System.out.println("rootPartNumber"+rootPartNumber);
                        if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                        {
                            subMatSplitIfc.setPartVersion(rootPartVersionID);
                        }
                            partIfc = getPartIfc(subMatSplitIfc, partMap);
                            partIdentity = getPartIdentity(subMatSplitIfc);
                            partMap.put(partIdentity, partIfc);
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
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
            	QMQuery materialPartStruQuery=new QMQuery("JFMaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                Collection oldMpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator oldMpsIte=oldMpsColl.iterator();
                HashMap oldMpsMap=new HashMap();
                while(oldMpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIte.next();
                	oldMpsMap.put(mpsIfc.getChildNumber(),mpsIfc);
                }
//                System.out.println("xmlMatSplit.materialPartStruQuery()======="+materialPartStruQuery.getDebugSQL());
//                System.out.println("xmlMatSplit.getPartNumber()======="+xmlMatSplit.getPartNumber());
//                System.out.println("xmlMatSplit.getMaterialNumber()======="+xmlMatSplit.getMaterialNumber());
                System.out.println("oldMpsMap======="+oldMpsMap);
                //如果新状态下有子件，则用当前的子件与上次发布内容进行比较，分别设置删除，新增、修改和沿用标记
                //如果新状态下没有子件，则将上次发布的所有子件结构删除（实际上因为当前发布的状态为2，所以不可能没有子件）
            //    System.out.println("newUsageLinkMapnewUsageLinkMapnewUsageLinkMap======="+newUsageLinkMap);
                if(newUsageLinkMap.size() > 0)
                {
                	//新数据的结构关联数组，对每一个新的数据关联进行循环处理 
                    Iterator newUsageLinksIte = newUsageLinkMap.keySet().iterator();
                    while(newUsageLinksIte.hasNext())
                    {
                    	//PartUsageLinkIfc newLink=(PartUsageLinkIfc)newUsageLinkMap.get(newUsageLinksIte.next());
                    	String[] aa = (String[]) newUsageLinksIte.next();
                    	//QMPartMasterIfc partMaster=(QMPartMasterIfc)pService.refreshInfo(newLink.getLeftBsoID());
                    	//如果当前关联在旧结构中不存在，则当前关联为新关联
                    	
                    	if(oldMpsMap.get(aa[0])==null)
                    	{
                    		
                    		MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
                            mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
                            mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                            mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
                            mpsInfo.setLevelNumber("0");
                            mpsInfo.setDefaultUnit("件");
                            //???
                            mpsInfo.setMaterialStructureType("O");
                            mpsInfo.setOptionFlag(false);
                            mpsInfo.setQuantity(Float.parseFloat(aa[3]));
                            mpsInfo.setChildNumber(aa[0]);
//                            在物料零件关联表中存储数据
                            pService.saveValueInfo(mpsInfo);
//                            System.out.println("sssssssssss==="+mpsInfo.getMaterialStructureType());
//                            发布新增的物料零件关联
                            setPartStructPubType(rootPartNumber, rootPartVersionID,
                                    "N", xmlMatSplit, aa);
                            
                    	}else{
                    		
                    		//如果存在对当前子件的关联，则有可能是o或者u
                    		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsMap.get(aa[0]);
//                    		如果使用数量相同，则为o，否则为U
//                    		System.out.println("ooooooooooooo1111111==="+newLink.getQuantity());
//                    		System.out.println("ooooooooooooo222222222==="+mpsIfc.getQuantity());
                    		if(Float.parseFloat(aa[3])==mpsIfc.getQuantity())
                    		{
                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "O", xmlMatSplit, aa);
                    			
                    		oldMpsMap.remove(aa[0]);
                    		}else{
   
                    			mpsIfc.setQuantity(Float.parseFloat(aa[3]));
                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "U", xmlMatSplit, aa);
                    			oldMpsMap.remove(aa[0]);
                    		}
                    	}
                    	
                    }
                    Iterator remainderIte=oldMpsMap.keySet().iterator();
                    //222222
                   // System.out.println("DDDDDDDDDDDDDDDDDD====="+oldMpsMap);
                	while(remainderIte.hasNext())
                	{
                		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsMap.get((String)remainderIte.next());
                		//System.out.println("1111111111111111111111三四三1啦啦啦啦来了么？-----------------------------"+mpsIfc);
                		setDeleteMaterialPartStructure(mpsIfc);
                    	pService.deleteValueInfo(mpsIfc);
                	}
                }else
                {
                	//System.out.println("fffffffffffffffffffff"+oldMpsMap);
                	Iterator keyIte=oldMpsMap.keySet().iterator();
                	while(keyIte.hasNext())
                	{
                		//if(oldMpsIte.hasNext()){
                			String key=(String) keyIte.next();
                    		
                    		//System.out.println("oldMpsIte.next()===="+oldMpsIte);
                    	
                    		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIte.next();
                    	//	System.out.println("mpsIfc===="+mpsIfc);
                    	//	System.out.println("222222222222222222222222啦啦啦啦来了么？-------------------------------------------"+mpsIfc);
                    		setDeleteMaterialPartStructure(mpsIfc);
                        	pService.deleteValueInfo(mpsIfc);
                		//}
                		
                	}
                }
         	
            }
       
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
//  CCBegin SS4
//    /**
//     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
//     * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
//     * @param partIfc 零部件。
//     * @throws QMXMLException
//     */
//    private final HashMap getUsageLinkMap(QMPartIfc partIfc)
//            throws QMXMLException
//    {
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - start");
//        }
//        final HashMap usageLinkMap = new HashMap();
//        //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
//        List usesPartList = new ArrayList();
//        try
//        {
//        	
//      
//        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
//        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
//        
//            //然后得到该零部件关联的PartUsageLink
//        }
//        catch (QMException e)
//        {
//            //"获取名为*的零部件结构时出错！"
//            logger.error(Messages.getString("Util.17", new Object[]{partIfc
//                    .getBsoID()})
//                    + e);
//            throw new QMXMLException(e);
//        }
//        //将要发布的子关联放到HashMap中。
//        Iterator iter = usesPartList.iterator();
//        while (iter.hasNext())
//        {
//            Object obj = iter.next();
//            usageLinkMap.put(obj, obj);
//        }
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - end");
//        }
//        return usageLinkMap;
//    }
    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
     * @param partIfc 零部件。
     * @throws QMXMLException
     */
    private final HashMap getUsageLinkMap(QMPartIfc partIfc)
            throws QMXMLException
    {
    	//Collection vec = null;
    	
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - start");
        }
        final HashMap usageLinkMap = new HashMap();
        //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
        List usesPartList = new ArrayList();
//        try
//        {
//        	GYBomNoticeService bomservice = (GYBomNoticeService)EJBServiceHelper.getService("GYBomNoticeService");
//        	
////        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
////        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
//        	 vec = bomservice.getReleaseBomDJ(partIfc);
//        	
//        	
//            //然后得到该零部件关联的PartUsageLink
//        }
//        catch (QMException e)
//        {
//            //"获取名为*的零部件结构时出错！"
//            logger.error(Messages.getString("Util.17", new Object[]{partIfc
//                    .getBsoID()})
//                    + e);
//            throw new QMXMLException(e);
//        }
        //将要发布的子关联放到HashMap中。
       // PartUsageLinkIfc
       // System.out.println("partIfc.getPartNumber()11111111111="+partIfc.getPartNumber());
        Iterator iter = djbom.iterator();
        while (iter.hasNext())
        {
            Object[] obj = new Object[5];
	    	obj = (Object[])iter.next();
	    	String[] aa = new String[5];
            aa[0]=(String) obj[8]; //编号带版本
            aa[1]=(String)obj[7]; //子组
            String bb[]=aa[0].split("/");
            aa[2] = bb[bb.length-1];; //版本
            aa[3]=(String)obj[4]; // 数量
            aa[4]=(String)obj[5]; // 父件编号
//            System.out.println("aa[0]11111111111="+aa[0]);
//            System.out.println("aa[3]11111111111="+aa[3]);
           // System.out.println("aa[4]11111111111="+aa[4]);
            
            if(aa[4].equals(partIfc.getPartNumber())){
            	 usageLinkMap.put(aa, aa);
            	// System.out.println("ininini=");
            }
           
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - end");
        }
        return usageLinkMap;
    }
	//CCEnd SS4
  //CCBegin SS1
    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
     * @param partIfc 零部件。
     * @throws QMXMLException
     */
//    private final HashMap getUsageLinkMapNew(QMPartIfc partIfc)
//            throws QMXMLException
//    {
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - start");
//        }
//        System.out.println("getUsageLinkMapNew=start");
//        PartHelper helper = new PartHelper();
//        final HashMap usageLinkMap = new HashMap();
//        //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
//        List usesPartList = new ArrayList();
//        Collection collection = null;
//        try
//        {
////        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
////        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
//        	 collection = helper.getUsesPartIfcs(partIfc);
//            //然后得到该零部件关联的PartUsageLink
//        
//	      
//	        //将要发布的子关联放到HashMap中。
//	        Iterator iter = collection.iterator();
//	        System.out.println("collection="+collection);
//	        while (iter.hasNext())
//	        {
//	        	//obj = new Object[3];
//	        	Object[] objnew = (Object[]) iter.next();
//	        	
//	        	QMPartIfc part1 = (QMPartIfc) objnew[1];
//	        	Object obj = objnew[0];
//	        	//QMPartIfc part2 = helper.filterLifeState(part1);
//	        	System.out.println("part2="+part2);
//	        	if(part2==null)
//	        		continue;
//	            usageLinkMap.put(obj, obj);
//	        }
//        } catch (QMException e)
//        {
//            //"获取名为*的零部件结构时出错！"
//            logger.error(Messages.getString("Util.17", new Object[]{partIfc
//                    .getBsoID()})
//                    + e);
//            throw new QMXMLException(e);
//        }
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - end");
//        }
//        System.out.println("getUsageLinkMapNew=end");
//        return usageLinkMap;
//    }
  //CCEnd SS1
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
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
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
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
            //查询该子件的顶级物料集合
        	filterMaterialSplit=(MaterialSplitIfc)msservice.getMSplit(materialNumber);
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
    	PartHelper helper = new PartHelper();
    	partNumber = helper.getPartNumber(partNumber);
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
        QMQuery query = new QMQuery("JFFilterPart");
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
     * @return String 筛选的唯一标识。编号+版本+生命周期状态+路线+虚拟件
     */
    private final String getPartIdentity(final QMPartIfc part)throws QMException
    {
    	PartHelper partHelper = new PartHelper();
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - start");
        //MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
        Vector routevec = RequestHelper.getRouteBranchs(part, null);

        String routeAsString = "";
        String routeAllCode = "";
        String routeAssemStr = "";
        if(routevec.size() != 0)
        {
            routeAsString = (String)routevec.elementAt(5);
            routeAssemStr = (String)routevec.elementAt(2);
            routeAllCode = (String)routevec.elementAt(1);
        }
      //  System.out.println("routevec222222222222222222==============="+routevec);
      //解放刘家坤20140609
        String xnj = "";
		boolean virtualFlag=partHelper.getJFVirtualIdentity( part, routeAsString, routeAssemStr);
//		System.out.println("routeAsString1111==============="+routeAsString);
//		System.out.println("routeAssemStr1111==============="+routeAssemStr);
//		 System.out.println("part1111==============="+part.getPartNumber());
//		  System.out.println("virtualFlag1111==============="+virtualFlag);
		 if(virtualFlag){
			 xnj = "Y";
		 }
		 //CCBegin SS3
		 String partVersion ="";
		 if(scbb.size()>0){
			 partVersion = (String) scbb.get(part.getPartNumber());
		 }
		 if(partVersion.equals("")){
			 partVersion = PartHelper.getPartVersion(part);
		 }
		 //CCEnd SS3
    	//String partnumber = part.getPartNumber() + "/" + partVersion;
		 
    	String partnumber ="";
		 //根据串进来的单级bom数据获取物料编号
		 if(djbom!=null&&djbom.size()>0){
			//  partnumber =getMaterialNumber(partIfc,partVersion);
			  Iterator iter = djbom.iterator();
		        while (iter.hasNext())
		        {
		            Object[] obj = new Object[5];
			    	obj = (Object[])iter.next();
			    	partnumber=(String) obj[8]; //编号带版本
		            QMPartIfc partIfcnew = (QMPartIfc)obj[0]; 
		            if(partIfcnew.getPartNumber().equals(part.getPartNumber()))

		              break;
		           
		           
		        }
		 }else{
			  partnumber =partHelper.getMaterialNumber(part,partVersion);
		 }
//			CCBegin SS6
       // String returnString = partnumber + part.getLifeCycleState().getDisplay() + routeAllCode + xnj;
        String returnString = partnumber   + xnj;
//		CCEnd SS6
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - end");
        return returnString;
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
        String xnj = "";
		boolean virtualFlag=matSplitIfc.getVirtualFlag();
		 if(virtualFlag){
			 xnj = "Y";
		 }
       // System.out.println("getPartIfc xnj======="+xnj);
		 //CCBegin SS1
        //String returnString = matSplitIfc.getPartNumber() ++matSplitIfc.getState() + matSplitIfc.getRoute()+xnj;
//		CCBegin SS6
		 // String returnString = matSplitIfc.getPartNumber() +matSplitIfc.getState() + matSplitIfc.getRoute()+xnj;
		//不需要判断生命周期状态，获取物料的时候
		 String returnString = matSplitIfc.getPartNumber()+xnj;
//		CCEnd SS6
		 //CCEnd SS1
		// System.out.println("returnString======"+returnString);
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
        matStructIfc.setDefaultUnit("件");
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
            QMXMLMaterialSplit xmlMatSplit, String[] a)
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
        matStructIfc.setDefaultUnit("件");
        matStructIfc.setQuantity(Float.parseFloat(a[3]));
        matStructIfc.setOptionFlag(false);
        //查询顶级子件的顶级物料
        List rootMatNumberList = getRootMatSplit(a, xmlMatSplit.getRouteCode());
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
    public List getRootMatSplit(String[] a, String parentRouteCode)
            throws QMException
    {
    	PartHelper partHelper =  new PartHelper();
        List rootMatNumberList = new ArrayList();
        if(!rootMatSplitMap.containsKey(a[0]))
        {
            //查询子件的Master值对象
            try
            {
            	//PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
                //1. 首先查询到子件最新版本
                QMPartMasterIfc childPartIfc = null;
//                final QMQuery query = new QMQuery("QMPartMaster");
//                final QueryCondition condition2 = new QueryCondition("bsoID",
//                        QueryCondition.EQUAL, childBsoID);
//                query.addCondition(condition2);
//                //暂时不考虑广义部件的情况。
//                query.setChildQuery(false);
//                List partMasterList = (List) pservice.findValueInfo(query);
//                if(partMasterList != null && partMasterList.size() > 0)
//                {
//                    childPartIfc = (QMPartMasterIfc) partMasterList.get(0);
//                }
//                else
//                {
//                    //"获取BsoID为*的零部件主信息时出错！"
//                    logger.error(Messages.getString("Util.67",
//                            new Object[]{childBsoID}));
//                    throw new QMException(Messages.getString("Util.67",
//                            new Object[]{childBsoID}));
//                }
                //2 查询该子件的顶级物料集合
              //  QMPartIfc part = partHelper.getZZPartInfoByMasterBsoID(childPartIfc.getBsoID());
                //String partVersion = (String) scbb.get(part.getPartNumber());

//				 if(partVersion==null||partVersion.length()==0){
//					 partVersion = PartHelper.getPartVersion(part);
//				 }
                Collection rootList = (Collection)msservice.getRootMSplit(a[0],a[2]);
                Iterator iter = rootList.iterator();
                //是否需要匹配的标志
                boolean isMatching = RemoteProperty.getProperty(
                        "com.faw_qm.jferp.isMatching", "false").equalsIgnoreCase(
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
                	
                //	String partVersion = (String) scbb.get(part.getPartNumber());

//    				 if(partVersion==null||partVersion.length()==0){
//    					 partVersion = PartHelper.getPartVersion(part);
//    				 }
    				//String childNumber =partHelper.getMaterialNumber(part,partVersion);
                	//String partVersion = PartHelper.getPartVersion(part);
                	String childNumber =a[0];
                    rootMatNumberList.add(childNumber);
                }
                rootMatSplitMap.put(a[0], rootMatNumberList);
            }
            catch (QMException e)
            {
                throw new QMException(e);
            }
        }
        else
        {
            rootMatNumberList = (List) rootMatSplitMap.get(a[0]);
        }
        return rootMatNumberList;
    }
}
