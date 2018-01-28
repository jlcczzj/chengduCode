/**
 * 生成程序SplitMaterielEJBAction.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.client.ejbaction;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.ejb.service.BaselineServiceEJB;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.jferp.client.event.SplitMaterielEvent;
import com.faw_qm.jferp.client.event.SplitMaterielEventResponse;
import com.faw_qm.jferp.ejb.service.IntePackService;
import com.faw_qm.jferp.ejb.service.MaterialSplitService;
import com.faw_qm.jferp.model.IntePackIfc;
import com.faw_qm.jferp.model.IntePackInfo;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.jferp.model.SameMaterialInfo;
import com.faw_qm.jferp.util.IntePackSourceType;
import com.faw_qm.jferp.util.MaterialServiceHelper;
import com.faw_qm.jferp.util.RequestHelper;
import com.faw_qm.framework.controller.ejb.action.EJBActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventException;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.jferp.util.PublishData;
/**
 * <p>Title: 物料拆分ejbaction处理类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
 * @version 1.0
 * SS1 添加BOM调整变更采用单过滤条件 刘家坤 2013-06-12
 * SS2 修改发布时写XML文件错误的问题 侯焊锋 2014-02-19
 */
public class SplitMaterielEJBAction extends EJBActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SplitMaterielEJBAction.class);

    private static final long serialVersionUID = 1L;
    private String RESOURCE = "com.faw_qm.part.util.PartResource";
    /**
     * 默认构造器
     */
    public SplitMaterielEJBAction()
    {
    }

    /**
     * @param e - 执行请求。
     * @return com.faw_qm.framework.event.EventResponse
     */
    public EventResponse perform(Event e) throws EventException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(Event) - start"); //$NON-NLS-1$
        }
        SplitMaterielEvent de = (SplitMaterielEvent) e;
        SplitMaterielEventResponse eventResponse = null;
        try
        {
            //获得持久化服务。
            if(de.getActionType() == SplitMaterielEvent.SPLIT)
            {
            	//20080103 begin 
                //获取拆分零部件的id字串。
                String parts = de.getPartIDs();
                //获取是否重新拆分。
                String resplit = de.getResplit();
                //获取是否通知。
                String notice = de.getSplitResult();
                //获取拆分零部件的id字串。
                String routes = de.getRoutes();
                //数据源的BsoId
                String sourceId=de.getSourceId();
                logger.debug("perform(Event) - ==>parts:" + parts);
                logger.debug("perform(Event) - ==>routes:" + routes);
                boolean flag = Boolean.valueOf(resplit).booleanValue();
                MaterialServiceHelper.split(parts,routes, flag);
                if(!notice.equals("show"))
                {
                    RequestHelper.getSplitMateHTML(parts);
                }
                eventResponse = new SplitMaterielEventResponse("");
                eventResponse.setPartIDs(parts);
                eventResponse.setSourceId(sourceId);
                //20080103 end
            }
            if(de.getActionType() == SplitMaterielEvent.UPDATE)
            {
                Vector dataVector = (Vector) de.getVector();
                String partnum=de.getPartid();
                HashMap updateMap = (HashMap) dataVector.get(0);
                //校验修改唯一性。
                verifyIndentity(updateMap);
                MaterialServiceHelper.updateMaterial(updateMap);
                eventResponse = new SplitMaterielEventResponse("");
                eventResponse.setPartid(partnum);
            }
            if(de.getActionType() == SplitMaterielEvent.DELETE)
            {
                Vector dataVector = (Vector) de.getVector();
                String deleteBsoid = (String) dataVector.get(0);
                String partnum = (String) dataVector.get(1);
                String pmatenum = (String) dataVector.get(2);
                //调用服务删除物料。
                deleteMaterial(partnum, pmatenum, deleteBsoid);
                eventResponse = new SplitMaterielEventResponse("");
                eventResponse.setPartid(partnum);
            }
            if(de.getActionType() == SplitMaterielEvent.REPLACE)
            {
                Vector dataVector = (Vector) de.getVector();
                String partnum = (String) dataVector.get(0);
                String mateid = (String) dataVector.get(1);
                String mateoldid = (String) dataVector.get(2);
                String pmatenum = (String) dataVector.get(3);
                String partnumbers = (String) dataVector.get(4);
                //调用服务替换物料。
                replaceMaterial(partnum, pmatenum, mateoldid, mateid);
                eventResponse = new SplitMaterielEventResponse("");
                eventResponse.setPartid(partnum);
                eventResponse.setPartIDs(partnumbers);
            }
            //20080103 begin
            if(de.getActionType() == SplitMaterielEvent.CHOOSE)
            {
                //获取拆分零部件的id字串。
                String parts = de.getPartIDs();
                //获取是否重新拆分。
                String resplit = de.getResplit();
                //获取是否立即显示拆分结果界面。
                String notice = de.getSplitResult();
                //数据源的BsoId
                String sourceId=de.getSourceId();
                eventResponse = new SplitMaterielEventResponse("");
                eventResponse.setPartIDs(parts);
                eventResponse.setResplit(resplit);
                eventResponse.setShow(notice);
                eventResponse.setSourceId(sourceId);
            }
            //20080103 end
            if(de.getActionType()==SplitMaterielEvent.SAMEMATERIAL)
            {
            	Vector datavec=(Vector) de.getVector();
            	String partNumber=(String)datavec.get(0);
            	String routeCode=(String)datavec.get(1);
            	String sameMaterialNumber=(String)datavec.get(2);
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            	SameMaterialInfo info=new SameMaterialInfo();
            	info.setPartNumber(partNumber);
            	info.setRouteCode(routeCode);
            	info.setSameMaterialNumber(sameMaterialNumber);
            	info=(SameMaterialInfo)pservice.saveValueInfo(info);
            }
            if(de.getActionType()==SplitMaterielEvent.DELETESAMEMATERIAL)
            {
            	Vector datavec=(Vector)de.getVector();
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            	for(Iterator iterator=datavec.iterator();iterator.hasNext();)
            	{
            		String sameBsoID=(String)iterator.next();
            		pservice.deleteBso(sameBsoID);
            	}
            }
            if(de.getActionType()==SplitMaterielEvent.PUBLISHBYBASELINE)
            {
            	Vector data=de.getVector();
            	String baselineid=(String)data.get(0);
            	Collection partids=(Collection)data.get(1);
            	//CCBegin SS1
            	publishPartsToERP(baselineid,partids,false,null,"1");
            	  //CCEnd SS1
            }
            if(de.getActionType()==SplitMaterielEvent.PUBLISHBYROUTE)
            {
            	Vector data=de.getVector();
            	Collection routev=(Collection)data.get(0);
            	String routeid="";
            	for(Iterator iter=routev.iterator();iter.hasNext();)
            	{
            		routeid=(String)iter.next();
            	}
            	//CCBegin SS1
            	publishPartsToERP(null,null,true,routeid,"2");
            	  //CCEnd SS1
            }
            if(de.getActionType() == SplitMaterielEvent.CANCLE)
            {
                cancleUpdate();
                
            }
        }
        catch (QMException ue)
        {
            logger.error("perform(Event)", ue); //$NON-NLS-1$
            Object[] objs = {de.getEventName()};
            ue.printStackTrace();
            throw new EventException(ue, "22", objs);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(Event) - ==>return res:" + eventResponse); //$NON-NLS-1$
        }
        return eventResponse;
    }
    private void getAllSubParts(QMPartIfc partIfc, Collection result,
			Collection indexID) throws QMException {
		Collection col = getSubParts(partIfc);
		Iterator iter = col.iterator();
		while (iter.hasNext()) {
			QMPartIfc part = (QMPartIfc) iter.next();
			String bsoID = part.getBsoID();
			if (!indexID.contains(bsoID)) {
				result.add(part);
				indexID.add(bsoID);
				getAllSubParts(part, result, indexID);
			}
		}

	}
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
	 * 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
	 * 
	 * @param partIfc
	 *            :QMPartIfc 零部件的值对象。
	 * @return collection:Collection partIfc使用的下级子件的最新版本的值对象(QMPartIfc)集合。
	 * @throws QMException
	 * @see QMPartInfo
	 */
	public Collection getAllSubParts(QMPartIfc partIfc) throws QMException {
		Collection result = new Vector();
		Collection filterIndex = new ArrayList();
		if (partIfc != null) {
			result.add(partIfc);
			filterIndex.add(partIfc.getBsoID());
			getAllSubParts(partIfc, result, filterIndex);
		}
		return result;
	}
	//CCBegin by chudaming 20100113
	public void publishPartsToERP(String baselineiD, Collection al,boolean isPublishRoute, 
            String routeID,String lx)
        throws QMException
    {
        try
        {
        	PublishData publishdata =  new PublishData();
        	String aaa="";
        	String partid="";
        	ManagedBaselineIfc baseline = null;
        	 SessionService sessions = null;
        	if(baselineiD!=null)
        	{
        		aaa="基线发布";
        	}
        	else if(routeID!=null)
        	{
        		aaa="路线发布";
        	}
            String xmlName = publishdata.getVirtualPartNumber();
//            Date now = new Date(); 
//            DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
//            String str8 = d8.format(now);
//            xmlName=str8+xmlName;
//            xmlName=xmlName.trim();
            MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
            BaselineService baseLineEjb = (BaselineService)EJBServiceHelper.getService("BaselineService");
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            //可以优化1111
            if(baselineiD!=null){
            baseline = (ManagedBaselineIfc)pservice.refreshInfo(baselineiD);
            
//            if(baseline.getBaselineName().toString().equals("按最新版发布")){
//            	 
//            	sessions = (SessionService) EJBServiceHelper.getService("SessionService");
//                sessions.setAdministrator();
//            Iterator iter = al.iterator();
//            //针对零部件列表进行循环，把当前零件及所有子件添加到当前的基线中questions
//            while (iter.hasNext()) {
//            	try {
//                 partid=(String)iter.next();
//                 QMPartIfc partifc = (QMPartIfc)pservice.refreshInfo(partid);
//                 Collection parts= getAllSubParts(partifc);
//                 baseLineEjb.addToBaseline(parts,baseline);
//            	}catch(Exception e) {
//          	      e.printStackTrace();
//           	 }
//           	 finally {
//           	      try {
//           	        sessions.freeAdministrator();
//           	      }
//           	      catch (QMException e) {
//           	        e.printStackTrace();
//           	      }
//           	    }
//            }
//            }
            //CCBegin SS2
            if(lx.equalsIgnoreCase("1"))
            {
            	if(al!=null && al.size()>0)
            	{
            		QMPartIfc qmpartifc = null;
            		Collection coll = new Vector();
            		for(Iterator iter = al.iterator(); iter.hasNext();)
            		{
            			String partID = (String)iter.next();
            		//	qmpartifc = (QMPartIfc)msservice.publishViewPart(partID);
            			if(qmpartifc!=null)
            			{
            				coll.add(qmpartifc.getBsoID());
            			}
            			else
            			{
            				coll.add(partID);
            			}
            		}
            		al = coll;
            	}
            }
			//CCEnd SS2
          //CCBegin by dikefeng 20100423，为了使得在发布的时候知道本次新发的物料都有哪些
            Vector filterPartVec=msservice.split(al, lx, routeID);
            if(filterPartVec==null||filterPartVec.size()==0)
             return;
            String packid = createIntePack(xmlName, baselineiD, isPublishRoute, routeID);
            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
           // packservice.publishIntePack(packid, xmlName,al,filterPartVec);
//            if(baseline.getBaselineName().toString().equals("按最新版发布")){
//            	 try {
//            Collection partss =baseLineEjb.getBaselineItems(baseline);
//            baseLineEjb.removeFromBaseline(partss,baseline);
//            	 }catch(Exception e) {
//            	      e.printStackTrace();
//            	 }
//            	 finally {
//            	      try {
//            	        sessions.freeAdministrator();
//            	      }
//            	      catch (QMException e) {
//            	        e.printStackTrace();
//            	      }
//            	    }
//            }
            }
            else{

//            	Vector filterPartVec=msservice.split(al, baselineiD, isPublishRoute, routeID);
//
//            	if(filterPartVec == null || filterPartVec.size() == 0)
//    			{
//    				
//    			}
//            	else{
//                String packid = createIntePack(xmlName, baselineiD, isPublishRoute, routeID);
//                IntePackService packservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
//                packservice.publishIntePack(packid, xmlName,al,filterPartVec);
//    			}
            	//CCBegin by dikefeng 20100624，即便是通过艺准通知书发布，发布过的数据也不再重新发布
            	Vector filterPartVec=msservice.split(al, lx, routeID);
            	System.out.println("filterPartVecfilterPartVecfilterPartVecfilterPartVec=================================================="+filterPartVec);
//            	if(filterPartVec==null||filterPartVec.size()==0)
//                    return;
                String packid = createIntePack(xmlName, baselineiD, isPublishRoute, routeID);
                IntePackService packservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
             //   packservice.publishIntePack(packid, xmlName,al,filterPartVec);
                //CCEnd by dikefeng 20100624
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
    }
    
	
	public void publishPartsToERP1(String baselineiD, Collection al,Collection al1,boolean isPublishRoute, 
            String routeID ,String partVersion,String partRoute,String masterbsoid)
        throws QMException
    {
        try
        {
        	PublishData publishdata = new PublishData();
        	String aaa="";
        	String partid="";
        	ManagedBaselineIfc baseline = null;
        	 SessionService sessions = null;
        	if(baselineiD!=null)
        	{
        		aaa="基线发布";
        	}
        	else if(routeID!=null)
        	{
        		aaa="路线发布";
        	}
            String xmlName = publishdata.getVirtualPartNumber();
            MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
            BaselineService baseLineEjb = (BaselineService)EJBServiceHelper.getService("BaselineService");
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            if(baselineiD!=null){
            baseline = (ManagedBaselineIfc)pservice.refreshInfo(baselineiD);
            
            if(baseline.getBaselineName().toString().equals("按最新版发布")){
            	 
            	sessions = (SessionService) EJBServiceHelper.getService("SessionService");
                sessions.setAdministrator();
            //针对零部件列表进行循环，把当前零件及所有子件添加到当前的基线中questions
            	try {
            		 
            		baseLineEjb.addToBaseline(al,baseline);
            	}catch(Exception e) {
          	      e.printStackTrace();
           	 }
           	 finally {
           	      try {
           	        sessions.freeAdministrator();
           	      }
           	      catch (QMException e) {
           	        e.printStackTrace();
           	      }
           	    }

            }
//          //CCBegin by dikefeng 20100423，为了使得在发布的时候知道本次新发的物料都有哪些
//            Vector filterPartVec=msservice.split1(al1, isPublishRoute, routeID,partVersion,partRoute ,masterbsoid);
//            
//            if(filterPartVec==null||filterPartVec.size()==0)
//             return;
////            String packid = createIntePack(xmlName, baselineiD, isPublishRoute, routeID);
////            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
////            packservice.publishIntePack(packid, xmlName,al1,filterPartVec);
//            if(baseline.getBaselineName().toString().equals("按最新版发布")){
//            	 try {
//            Collection partss =baseLineEjb.getBaselineItems(baseline);
//            baseLineEjb.removeFromBaseline(partss,baseline);
//            	 }catch(Exception e) {
//            	      e.printStackTrace();
//            	 }
//            	 finally {
//            	      try {
//            	        sessions.freeAdministrator();
//            	      }
//            	      catch (QMException e) {
//            	        e.printStackTrace();
//            	      }
//            	    }
//            }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
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
        sourceid = baselineID;
        sourcetype = IntePackSourceType.BASELINE;
    }
    IntePackInfo intepack = new IntePackInfo();
    intepack.setName(name);
    intepack.setSourceType(sourcetype);
    intepack.setSource(sourceid);
    IntePackService itservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
    IntePackIfc intepackifc = itservice.createIntePack(intepack);
    String bsoid = intepackifc.getBsoID();
    return bsoid;
}
    
//    private String getVirtualPartNumber(String base) {
//        String partNumber = "";
//        try {
//          QQChangeService cs = (QQChangeService) EJBServiceHelper.getService(
//              "QQChangeService");
//          int year = Calendar.getInstance().get(Calendar.YEAR);
//          int month = Calendar.getInstance().get(Calendar.MONTH);
//          String yearStr = (new Integer(year)).toString();
//          String monthStr = "";
//          if (month < 9) {
//            monthStr = "0" + (new Integer(month + 1)).toString();
//          }
//          else {
//            monthStr = (new Integer(month + 1)).toString();
//
//          }
//          int i = cs.getNextSortNumber(base, yearStr + monthStr, true);
//          if (i < 10) {
//            partNumber = "000" + (new Integer(i)).toString();
//          }
//          else if (i < 100) {
//            partNumber = "00" + (new Integer(i)).toString();
//          }
//          else if (i < 1000) {
//            partNumber = "0" + (new Integer(i)).toString();
//          }
//          else {
//            partNumber = (new Integer(i)).toString();
//          }
//          partNumber = yearStr + monthStr + "-" + partNumber;
//        }
//        catch (QMException e) {
//          e.printStackTrace();
//        }
//
//        return base+"-" + partNumber;
//      }

    private void replaceMaterial(String partnum, String parentmatenum,
            String mateoldid, String mateid) throws QMException
    {
        MaterialServiceHelper
                .replace(partnum, parentmatenum, mateoldid, mateid);
    }

    private void deleteMaterial(String partnum, String parentmatenum,
            String imateid) throws QMException
    {
        MaterialServiceHelper.delete(partnum, parentmatenum, imateid);
    }

    private void cancleUpdate() throws QMException
    {
        MaterialServiceHelper.deleteAllInterimData();
    }

    private void verifyIndentity(HashMap map) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("verifyIndentity(HashMap) - start"); //$NON-NLS-1$
            logger.debug("参数：" + map);
        }
        Collection coll = map.values();
        MaterialSplitService msservice = (MaterialSplitService) EJBServiceHelper
                .getService("JFMaterialSplitService");
        for (Iterator ite = coll.iterator(); ite.hasNext();)
        {
            String matenum = (String) ite.next();
            if(matenum == null || matenum.trim().equals(""))
                throw new QMException("物料号为空！");
            MaterialSplitIfc msifc = msservice.getMSplit(matenum);
            if(msifc != null)
            {
                throw new QMException("物料号" + matenum + "不唯一！");
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("verifyIndentity(HashMap) - end"); //$NON-NLS-1$
        }
    }
}
