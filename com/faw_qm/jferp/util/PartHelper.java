/**
 * 生成程序 PartHelper.java    1.0    2013/06/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 过滤生命周期按层级过滤，而不是结束时候一起过滤 刘家坤 2013-09-03
 * SS2 过滤生命周规则错误，不需要判断branchid 刘家坤 2013-09-03
 * SS3 新加逻辑总成判断规则 （1）编号第五位是“G“，（1）制造路线含用、无装配路线（2）制造路线不含用，有装配路线（3）逻辑总成均不拆分半成品。刘家坤 2014-12-17
 * SS4 路线拆分时，出现自己挂自己现象，由于拆分路线代码一致引起 liujiakun 20150129
 * SS5 如果是广义部件，则需要装配路线是卡车厂路线 刘家坤 2015-10-30
 * SS6 调整广义部件判断方法。先判断编号位数大于5位。 liunan 2017-3-3
 * SS7 根据卡车驾驶室新整理的路线拆分规则，对程序进行调整 徐德政 2017-12-13
 */
package com.faw_qm.jferp.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.model.LifeCycleTemplateMasterInfo;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.queue.exception.QueueException;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewManageableIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;


/**
 * <p>
 * 向ERP发数据用工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * 
 * @author 刘家坤
 * @version 1.0
 */
public class PartHelper
{
	 private static final String RESOURCE = "com.faw_qm.jferp.util.ERPResource";
	 PartConfigSpecInfo partConfigSpecIfc = null;
	 String[] State={"生产准备"};
    public PartHelper()
    {
    }
    public  Collection getSubParts(QMPartIfc partIfc) throws QMException {
    	if(VERBOSESYSTEM)
		System.out.println("getSubParts begin ....");
    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
    	 // PartConfigSpecIfc configSpecIf =
			// getPartConfigSpecByViewName("制造视图");
    	 PartUsageLinkIfc partlink=new PartUsageLinkInfo();
    	 Vector result=new Vector();
    	 Vector vec=new Vector();
// System.out.println("getSubParts begin 11111111111...."+partlink.getBsoID());
// System.out.println("getSubParts begin 2222222222...."+partIfc.getBsoID());
    	 vec = getAllParts(partIfc,partlink,vec);
// QMPartIfc part1 = filterLifeState(partIfc);
// result.add(part1);
    	// System.out.println("getSubParts vec="+vec);
         if(vec!=null&&vec.size()>0){
        	 for(int i = 0;i<vec.size();i++){
        		 Object[] obj=(Object[])vec.elementAt(i);
        		 QMPartIfc partInfo = (QMPartIfc)obj[1];
        		 result.add(partInfo);
        	 }
         }
         // 过滤生命周期
        // System.out.println("getSubParts result="+result);
         
         Vector resultVector = result;
        // System.out.println("getSubParts resultVector="+resultVector);
    	 return resultVector;
    	 
	    }
    /**
	 * 根据指定配置规范，获得指定部件的使用结构： 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
	 * 
	 * @param partIfc
	 *            零部件值对象。
	 * @param configSpecIfc
	 *            零部件配置规范。
	 * @throws QMException
	 * @see QMPartInfo
	 * @see PartConfigSpecInfo
	 * @return Collection 集合Collection的每个元素是一个数组对象:<br>
	 */
    private Vector getAllParts(QMPartIfc partifc_old,PartUsageLinkIfc partlink,Vector result)
    throws QMException
    {

      float quan=partlink.getQuantity();
      Object[] obj = null;
       // 第一步：调用getUsesPartIfcs方法，返回Collection，如果Collection为空，或者长度为0,抛出异常
     // System.out.println("getAllParts partifc_old="+partifc_old);
     // QMPartIfc partifc = filterLifeState(partifc_old);

      
     // System.out.println("getAllParts partifc="+partifc);
       Collection collection = getUsesPartIfcs(partifc_old);
     // System.out.println("getAllParts collection="+collection.size());
       if((collection.size() == 0) || (collection == null))
       {
           // "未找到符合当前筛选条件的版本"
           // throw new PartException(RESOURCE, "E03014", null);
           return result;
       }
       // 第二步: 对collection中的每个元素进行循环，需要另外声明一个递归的方法productStructure()
       // productStructure(subPartIfc, configSpecIfc, parentBsoID,
		// partUsageLinkIfc)
       else
       {
           Object[] tempArray = new Object[collection.size()];
           collection.toArray(tempArray);
          
           PartUsageLinkIfc partUsageLinkIfc1=null;
           // 先把tempArray中的所有元素都放到resultVector中来
           for (int i = 0; i < tempArray.length; i++)
           {
        	   QMPartIfc partIfc1 = null;
               QMPartIfc part1 = null;
              // QMPartIfc part2 = null;
               Object[] tempObj = (Object[]) tempArray[i];
               if(tempObj[1] instanceof QMPartIfc
                       && tempObj[0] instanceof PartUsageLinkIfc)
               {
                   obj = new Object[3];
                   partIfc1 = (QMPartIfc) tempObj[1];
                 // ?零部件类型是装置图，该件及其子件不向ERP发布
                   String partType = partIfc1.getPartType().getDisplay().toString();
                   if(partType.equals("装置图"))
                   continue;
                   part1 = filterLifeState(partIfc1);
                 // System.out.println("getAllParts part2="+part2);
                   if(part1!=null)
                   {
             
                   partUsageLinkIfc1 = (PartUsageLinkIfc) tempObj[0];
                  // System.out.println("getAllParts part1="+part1);
                   obj[0] = part1.getPartNumber();

                       obj[1] = part1;
                       quan=quan*partUsageLinkIfc1.getQuantity();
                       obj[2]=Float.toString(quan);
                       result.add(obj);
                   }

               }
               
               if(part1 != null&&partUsageLinkIfc1!=null)
                   getAllParts(part1, partUsageLinkIfc1,
                            result);
            }
        }
        return result;
    }
  
    /**
	 * 根据指定配置规范，获得指定部件的使用结构： 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
	 * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象， 如果没有匹配对象，记录mastered对象。
	 * 
	 * @param partIfc
	 *            零部件值对象。
	 * @return
	 * @throws QMException
	 */
    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException {
        Collection links = null;
        PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
        if (partIfc.getBsoName().equals("GenericPart"))
            links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
        else if (partIfc.getBsoName().equals("QMPart"))
            links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
        if (links == null || links.size() == 0)
            return new Vector();
        return getUsesPartsFromLinks(links);
    }
 
    /**
	 * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
	 * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
	 * 
	 * @param collection
	 *            是PartUsageLinkIfc对象的集合。
	 * @return 每个元素为一个数组.
	 *         数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
	 * @throws QMException
	 */
    public Collection getUsesPartsFromLinks(Collection collection) throws QMException
    {
        Collection masterCollection = mastersOf(collection);
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
        if(partConfigSpecIfc == null)
        {
            partConfigSpecIfc = new PartConfigSpecInfo();
            PartStandardConfigSpec pStandardcs = new PartStandardConfigSpec();
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            QMQuery query = new QMQuery("ViewObject");
            QueryCondition cond = new QueryCondition("viewName", "=", "制造视图");
            query.addCondition(cond);
            Collection col = pservice.findValueInfo(query);
            if(col == null || col.isEmpty())
                throw new QMException("没有制造视图！");
            pStandardcs.setViewObjectIfc((ViewObjectIfc)col.iterator().next());
            pStandardcs.setWorkingIncluded(true);
            partConfigSpecIfc.setStandard(pStandardcs);
            partConfigSpecIfc.setStandardActive(true);
            partConfigSpecIfc.setBaselineActive(false);
            partConfigSpecIfc.setEffectivityActive(false);
        }
        PartConfigSpecAssistant pcon = new PartConfigSpecAssistant(partConfigSpecIfc);
        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, pcon);
        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection, iteratedCollection);
        return ConfigSpecHelper.buildConfigResultFromLinks(collection, "uses", allCollection);
    }
    /**
	 * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的 mastered对象（指定角色master）的结果集
	 * 
	 * @param links
	 *            关联类值对象集合
	 * @param role
	 *            角色名
	 * @exception com.faw_qm.config.exception.ConfigException
	 * @return 对应关联类值对象的Mastered对象集合。
	 */
    /**
	 * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的 mastered对象（指定角色master）的结果集
	 * 
	 * @param links
	 *            关联类值对象集合
	 * @param role
	 *            角色名
	 * @exception com.faw_qm.config.exception.ConfigException
	 * @return 对应关联类值对象的Mastered对象集合。
	 */
    public Collection mastersOf(Collection links) throws QMException
    {

        Vector vector = (Vector)links;// CR2
        Vector resultVector = new Vector();

        for(int i = 0;i < vector.size();i++)
        {
            PartUsageLinkIfc obj = (PartUsageLinkIfc)vector.elementAt(i);
            String bsoID;
            try
            {
                bsoID = obj.getRoleBsoID("uses");
            }catch(QMException e)
            {
                throw new QMException(e, "根据角色名获得其BsoID时出错");
            }
            BaseValueIfc bsoObj;

            PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
            bsoObj = (BaseValueIfc)persistService.refreshInfo(bsoID, false);

            if(!(bsoObj instanceof QMPartMasterIfc))
            {
                throw new QMException();
            }// endif 如果collection中的元素左连接对象object不是MasteredIfc的
            // 实例，抛出角色无效例外
            resultVector.addElement(bsoObj);
        }// endfor i
        return removeDuplicates(resultVector);
    }
    /**
	 * 将指定的结果集中重复的元素排除。
	 * 
	 * @param collection
	 *            结果集
	 * @return Collection 排除了重复数据的集合 Collection中每一个元素为一Object数组
	 *         该Object数组中的第0个元素为一值对象
	 */
    private Vector removeDuplicates(Collection collection) throws QMException
    {
        Hashtable hashtable = new Hashtable();
        Vector resultVector = new Vector();
        for(Iterator ite = collection.iterator();ite.hasNext();)
        {
            BaseValueInfo obj = (BaseValueInfo)ite.next();
            String objBsoID = obj.getBsoID();
            boolean flag = hashtable.containsKey(objBsoID);
            if(flag == true)
                continue;
            hashtable.put(objBsoID, "");// 将BsoID做为标志放入Hash表
            resultVector.addElement(obj);
        }// endfor i
        return resultVector;
    }
    public Collection filterLifeCycle(Collection al){
    	  PartHelper helper = new PartHelper();
    	  
    	  Vector temp = new Vector();
    	  try {
    		  PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
    		  Iterator iter = al.iterator();
    	  while(iter.hasNext()){
    		  String partID = (String)iter.next();
    		  QMPartIfc partIfc1 = (QMPartIfc)pservice.refreshInfo(partID);
    		  // 获取制造视图最新版本
    		  PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("工艺视图");
    		  QMPartIfc partIfc =  (QMPartInfo) PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc) partIfc1.getMaster() , configSpec);
    		  QMPartIfc part1 = helper.filterLifeState(partIfc);
    		  if(part1==null)
    			  continue;
    		 // QMPartIfc part2 = helper.publishViewPart(part1.getBsoID());
    		  temp.add(part1.getBsoID());
    	  }
    	  } catch (QMException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	  return (Collection)temp;
    	 
      }
    public Collection filterLifeCycle1(Collection al){
  	  PartHelper helper = new PartHelper();
  	  
  	  Vector temp = new Vector();
  	  try {
  		  PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  		  Iterator iter = al.iterator();
  	  while(iter.hasNext()){
  		QMPartIfc partIfc1 = (QMPartIfc)iter.next();
  		  // 获取制造视图最新版本
  		  PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("工艺视图");
  		  QMPartIfc partIfc =  (QMPartInfo) PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc) partIfc1.getMaster() , configSpec);
  		  QMPartIfc part1 = helper.filterLifeState(partIfc);
  		  if(part1==null)
  			  continue;
  		 // QMPartIfc part2 = helper.publishViewPart(part1.getBsoID());
  		  temp.add(part1);
  	  }
  	  } catch (QMException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	  return (Collection)temp;
  	 
    }
    /**
	 * 过滤生命周期状况，不满足的向前追溯 并以试制以及试制以后的状态才添加为过滤条件，如果制造视图最新版本零部件状态不满足条件
	 * 需追溯该件制造视图之前满足条件的版本。
	 * 
	 * @param vec
	 *            零部件集合 或 结构件集合
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
                // System.out.println("state========1"+state);
                for(int j=0;j<State.length;j++)
                {
                    if(state.equals(State[j]))
                    {                       
                    	 return part;
                    }
                }
             // 追溯,先获取制造视图最新版本，如果符合条件则取出，如果不符合条件则获取工艺视图最新件。
              // part = getZZPartInfoByMasterBsoID(part1.getMasterBsoID());
               // System.out.println("制造视图版本 = "+part);
                if(part==null){
// System.out.println("开始获取工艺视图最新版本");
// System.out.println("part1 = "+part1);
// 添加工艺视图最新版本
                    PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("工艺视图");
                    part = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)part1.getMaster() , configSpecGY);
                  // part = getPart(part);
                }
                
                // result.add(part);
             
              // System.out.println("最终版本 = "+part);
      
        }
        return part;
    }
    /**
	 * 根据生命周期状态追溯当前大版本零部件
	 * 
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
       
       // System.out.println("curPart.getPredecessorID() =
		// "+curPart.getPredecessorID());
        if(preID==null||preID.length()==0)
        {
        	return null;
        }
        QMPartIfc prePart = (QMPartIfc)persistService.refreshInfo(preID, false);
        // CCBegin SS2
// if(!prePart.getBranchID().equals(curPart.getBranchID()))
// {
// // 最新版本
// VersionControlService vservice =
// (VersionControlService)EJBServiceHelper.getService("VersionControlService");
// return (QMPartIfc)vservice.getLatestIteration(curPart);
// }else
// {//CCEnd SS2
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              // System.out.println("==================根据生命周期状态追溯当前大版本零部件
				// state = "+state);
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================根据生命周期状态追溯当前大版本零部件 prePart
			// = "+prePart);
            return getPart(prePart);
// }
    }
    /**
	 * 获取制造视图最新小版本，并且生命周期状态是试制、生产、生产准备、
	 * 
	 * @param masterid
	 * @return QMPartInfo
	 * @throws QMException
	 */
    public QMPartInfo getZZPartInfoByMasterBsoID(String masterid)
    		throws QMException {
    	try {
    		PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
    		VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
    		Collection col = vcservice.allVersionsOf((QMPartMasterIfc) pService.refreshInfo(masterid));
    		// System.out.println("col = "+col);
    		if(col != null ){
    			Iterator iter = col.iterator();
    			if (iter.hasNext()) {
        			QMPartInfo part = (QMPartInfo) iter.next();
        			return part;
    		    }
    		
    		}
    	} catch (QMException ex) {
    		ex.printStackTrace();
    		throw ex;
    	}
    	return null;
    }
  
    /**
	 * 根据输入的零部件主信息BsoID，和零部件配置规范, 返回符合零部件配置规范的，受该QMPartMasterIfc管理的所有零部件的集合。
	 * 
	 * @param partMasterID
	 *            零部件主信息BsoID。
	 * @return Vector 过滤出来的零部件值对象的集合，如果没有合格的零部件返回new Vector()。
	 * @exception QMException
	 */
    public static Vector filterIterations(QMPartMasterIfc partMasterIfc )
        throws QMException
    {
    	
      // 第二步：零部件配置规范值对象：
      PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
      // 第三步：根据两个参数调用StandardPartService中的方法filterdIterationsOf方法
      Vector paraVector = new Vector();
      paraVector.addElement(partMasterIfc);
    // System.out.println("paraVector="+paraVector);
      Collection result = PartServiceRequest.filteredIterationsOf(paraVector, getPartConfigSpecByViewName("工艺视图"));
      // System.out.println("result="+result);
      Vector resultVector = new Vector();
      if(result == null | result.size() == 0)
      {
       
        return resultVector;
      }
      else
      {
        Iterator iterator = result.iterator();
        while(iterator.hasNext())
        {
          resultVector.addElement(iterator.next());
        }
        return resultVector;
      }
    }
    /**
	 * 根据视图名称返回零部件配置规范
	 * 
	 * @param viewName
	 *            String
	 * @throws QMException
	 * @return PartConfigSpecIfc
	 * @author liunan 2008-08-01
	 */
    public static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
        QMException {
      ViewService viewService = (ViewService) EJBServiceHelper.getService(
          "ViewService");
      ViewObjectIfc view = viewService.getView(viewName);
      // 若根据指定的视图名称没有获取到相应的值对象则返回当前配置规范
      if (view == null) {
        return (PartConfigSpecIfc) PartServiceRequest.
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
      return partConfigSpecIfc;
    }

    /**
	 * 获取管理员密码
	 */
    public String initPassword()
    {
        // 管理员用户和密码设置在PasswordTable表中
    	String adminPassword = "";
        String passwordTable = RemoteProperty.getProperty(
                "com.faw_qm.queue.passwordTable", "PasswordTable");
        String sql = "Select password from " + passwordTable +
                     " where username=?";
        String adminUser = DomainHelper.ADMINISTRATOR_NAME;
        java.sql.Connection conn = null;
        ResultSet result = null;
        PreparedStatement pm = null;
        try
        {
            conn = PersistUtil.getConnection();
            pm = conn.prepareStatement(sql);
            pm.setString(1, adminUser);
            result = pm.executeQuery();
            String s = null;
            if (result.next())
            {
                s = result.getString(1);
                // if(s!=null)
                // {
                // s=new String(fromb64(s));
                // }
            }
            if (s == null)
            {
                throw new QueueException("2", null);
            }
            adminPassword = s;
        }
        catch (Exception se)
        {
            throw new QMRuntimeException(new QueueException(se, "4", null));
        }
        finally
        {
            try
            {
                if (result != null)
                {
                    result.close();
                }
                if (pm != null)
                {
                    pm.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
     return adminPassword;
    }
    public ManagedBaselineIfc getBaselineByName(String name) {
        try {
          QMQuery query = new QMQuery("ManagedBaseline");
          QueryCondition con = new QueryCondition("baselineName", "=", name);
          query.addCondition(con);
   
          PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
          Collection col = pservice.findValueInfo(query, false);
          Iterator i = col.iterator();
          if (i.hasNext()) {
            ManagedBaselineIfc line = (ManagedBaselineIfc) i.next();
            return line;
          }

        }
        catch (QMException e) {
          e.printStackTrace();
        }
        return null;
      }
    /**
	 * 比较两个版本的大小
	 * 
	 * @param version1
	 * @param version2
	 * @return QMPartInfo
	 * @throws QMException
	 */
     public static int compareVersion(String version1,String version2)
     		 {
// System.out.println("version1="+version1);
// System.out.println("version2="+version2);
            int bool = 1;
            // 获得版本1和版本2的位置
            int dot1 = version1.indexOf(".");
            int dot2 = version2.indexOf(".");
            // System.out.println("dot1="+dot1+" dot2="+dot1);
            // 如果版本1和版本2都不含点
            if(dot1<0&&dot2<0){
            	// System.out.println("dot1111111111");
            	if(version1.length()==version2.length()){
   		          if(version1.compareTo(version2) < 0){
   		        	bool = 2;
   		          }else{
   		        	bool = 1;
   		          }
            	 }else if(version1.length()<version2.length()){
            		 bool = 2;
            	 }else{
            		 bool = 1;
            	 }
            }
            // 如果版本1和版本2都含点
            if(dot1>=0&&dot2>=0){
            	// System.out.println("dot22222222");
            	String gyVersion1 = version1.substring(0,dot1);
            	String gyVersion2 = version2.substring(0,dot2);
// System.out.println("gyVersion1="+gyVersion1);
// System.out.println("gyVersion2="+gyVersion2);
            	// 对“.”前进行比较
            	// 如果点前版本不相同
            	if(!gyVersion1.equals(gyVersion2)){
            		
            		if(version1.length()==version2.length()){
	     		          if(gyVersion1.compareTo(gyVersion2) < 0){
	     		        	bool = 2;
	     		          }else{
	     		        	bool = 1;
	     		          }
	              	 }else if(gyVersion1.length()<gyVersion2.length()){
	              		 bool = 2;
	              	 }else{
	              		 bool = 1;
	              	 }
            	}// 如果点前版本相同,则比较点后版本
            	else{
	            		String zzVersion1 = version1.substring(dot1+1);
	                	String zzVersion2 = version2.substring(dot2+1);
// System.out.println("zzVersion1="+zzVersion1);
// System.out.println("zzVersion2="+zzVersion2);
	                	if(zzVersion1.length()==zzVersion2.length()){
		     		          if(zzVersion1.compareTo(zzVersion2) < 0){
		     		        	bool = 2;
		     		          }else{
		     		        	bool = 1;
		     		          }
		              	 }else if(zzVersion1.length()<zzVersion2.length()){
		              		 bool = 2;
		              	 }else{
		              		 bool = 1;
		              	 }
            	    }
            }
            if(dot1>=0&&dot2<0){
            	bool = 1;
            }
            if(dot1<0&&dot2>=0){
               bool = 3;	
            }
     	return bool;
     }
     /**
		 * 通过零部件编号获取最新版本的零部件。
		 * 
		 * @param partNumber
		 *            partNumber
		 * @return QMPartInfo 最新版本的零部件(QMPart);
		 * @throws QMException
		 * @see QMPartInfo
		 */
 	public QMPartInfo getPartByPartNumber(String partNumber) throws QMException
 	{
         PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
 		QMQuery query = new QMQuery("QMPartMaster");
 		QueryCondition qc1 = new QueryCondition("partNumber", "=", partNumber);
 		query.addCondition(qc1);
 		QMPartInfo partInfo = null;
 		Collection collection = pservice.findValueInfo(query);
 		if (collection != null && collection.size() > 0) 
 		{
 			Iterator iterator = collection.iterator();
 			while (iterator.hasNext()) 
 			{
 				QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)iterator.next();
 		        PartServiceHelper pshelper= new PartServiceHelper();
 				partInfo = pshelper.getPartByMasterID(partMasterIfc.getBsoID());
 			}
 		}
// PartServiceHelper pshelper= new PartServiceHelper();
// QMPartInfo partInfo = pshelper.getPartByMasterID(partIfc);
 		return partInfo;
 	}
	/**
	 * liujiakun 20140226 * 根据给定的事物特性持有者,获得特定iba属性值。 return String 为该iba属性的值。
	 * 
	 * @param part
	 *            QMPartIfc
	 * @throws QMException
	 * @return String
	 */
	public static String getPartIBA(QMPartIfc part, String ibaDisplayName,String sName) throws QMException {
		String ibavalue = "";
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
			QueryCondition qc2 = new QueryCondition("name", " = ", sName);
			query.addCondition(j, qc2);
			query.addAND();
			QueryCondition qc3 = new QueryCondition("ibaDisplayName", " = ", ibaDisplayName);
			query.addCondition(j, qc2);
			// System.out.println("getPartIBA query="+query.getDebugSQL());
			Collection col = pService.findValueInfo(query, false);
			if (col != null && col.size() > 0) {
				StringValueIfc value = (StringValueIfc) col.iterator().next();
				ibavalue = value.getValue();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new QMException(e);
		}
		
		return ibavalue;
	}
	public static String getPartVersion(QMPartIfc partIfc){
		// 技术中心源版本
		String jsbb;
		String partVersion = "";
		try {
			jsbb = getPartIBA(partIfc, "发布源版本","sourceVersion");
	
			// 1) 零部件“技术中心源版本”属性有值，则零部件版本属性为技术中心源版本。
			// 2) 零部件“技术中心源版本”属性为空，则零部件版本属性为PDM部件版本。
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
// public static String getPartIBA(QMPartIfc partIfc, String
// ibaDisplayName,String sName)
// throws QMException {
//
// String ibaValue = "";
// final HashMap nameAndValue = new HashMap();
// try {
// IBAValueService
// ibservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
// partIfc =
// (QMPartIfc)ibservice.refreshAttributeContainerWithoutConstraints(partIfc);
// } catch (QMException e) {
// // "刷新属性容器时出错！"
// throw new QMException(e);
// }
// DefaultAttributeContainer container = (DefaultAttributeContainer) partIfc
// .getAttributeContainer();
// if (container == null) {
// container = new DefaultAttributeContainer();
// }
// final AbstractValueView[] values = container.getAttributeValues();
// for (int i = 0; i < values.length; i++) {
// final AbstractValueView value = values[i];
// final AttributeDefDefaultView definition = value.getDefinition();
// if (definition.getDisplayName().trim()
// .equals(ibaDisplayName.trim())&&definition.getName().trim()
// .equals(sName.trim())) {
// if (value instanceof AbstractContextualValueDefaultView) {
// MeasurementSystemCache.setCurrentMeasurementSystem("SI");
// final String measurementSystem = MeasurementSystemCache
// .getCurrentMeasurementSystem();
// if (value instanceof UnitValueDefaultView) {
// DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
// String ss = "";
// try {
// ss = defaultunitrenderer
// .renderValue(
// ((UnitValueDefaultView) value)
// .toUnit(),
// ((UnitValueDefaultView) value)
// .getUnitDisplayInfo(measurementSystem));
// } catch (UnitFormatException e) {
// // "计量单位格式出错！"
// throw new QMXMLException(e);
// } catch (IncompatibleUnitsException e) {
// // "出现不兼容单位！"
// throw new QMXMLException(e);
// }
// final String ddd = ((UnitValueDefaultView) value)
// .getUnitDefinition()
// .getDefaultDisplayUnitString(measurementSystem);
// // nameAndValue.put(definition.getDisplayName(), ss +
// // ddd);
// ibaValue = ss + ddd;
// } else {
//				
// ibaValue = ((AbstractContextualValueDefaultView) value)
// .getValueAsString();
// }
//			
// ibaValue = ((ReferenceValueDefaultView) value).getLocalizedDisplayString();
// }
// break;
// }
// }
//	
// return ibaValue;
// }
	  /**
		 * 零部件的制造路线单位中只要不包含卡车厂路线单位，“采购件标识”设置为“Y”，其他设置为“N”。
		 * 卡车厂制造路线单位：总、饰、饰（长）、架(漆)、架(装)、架(纵)、架(钻)、架(横)、架(横抛)、厚(纵)、厚(试制)、厚(焊)、协(抛丸)、薄、焊、涂
		 * 
		 * @param List
		 *            routeCodeList
		 * @return String 采购标识;
		 * @throws QMException
		 */
	public String getCgbs(List routeCodeList){
		 String cgbs = "Y";
		 Vector coll = new Vector();
		 String kcc = "";
			kcc = RemoteProperty.getProperty("kcc");
			for (int m = routeCodeList.size() - 1; m >= 0; m--) {
				String routeCode = (String) routeCodeList.get(m);
				// System.out.println("cgbs="+cgbs);
				
				  String[] array = kcc.split("、");
				  for(int ii=0;ii<array.length;ii++){
					  String objID =  array[ii];
					  coll.add(objID);
				    }
// System.out.println("coll="+coll);
// System.out.println("routeCode="+routeCode);
				  if(coll.contains(routeCode)){
					  cgbs = "N";
				  }
			}
			return cgbs;
	}
	  /**
		 * 根据下一个路线判断当前路线代码
		 * 
		 * @param List
		 *            routeCodeList
		 * @return String 采购标识;
		 * @throws QMException
		 */
	public String getLXCode(List routeCodeList,String routeCode,int index,HashMap backCount){
		String cgbs = "Y";
		Collection coll = null;
		String routeKey = "";
		int i = 0;
		// CCBegin SS7
		// 回头次数
		boolean flag = false;
		int hz = 0;
		//HashMap count = new HashMap();

		String[] codeList={"薄","厚(焊)","厚(试制)","厚(纵)","架(横)","架(漆)","鞍","宝","架(钻)","架(横抛)","架(装)","架(纵)"};
		// CCEnd SS7
		String nextCode = "";
		//System.out.println("routeCodeList.size()："+routeCodeList.size()+"========="+index);


		if(!(index == routeCodeList.size()-1))
			nextCode = (String)routeCodeList.get(index+1);
		System.out.println("nextCode-------------------------------------------------"+nextCode);
		//只要下一个路线是架(漆)或者涂，那么该装成品为“-T”
		if(nextCode.equals("架(漆)")||nextCode.equals("涂")){
			return "T";
		}
		if(ifCodeHT1(routeCodeList,index,routeCode,backCount)){
			//System.out.println("路线====="+routeCode+"====是回头件，是第"+((Integer)backCount.get(routeCode)).intValue()+"次回头");
			String makeCodeNameStr = "";
			//if(((Integer)backCount.get(routeCode)).intValue()==1)
				//makeCodeNameStr = "B";
			//else
				makeCodeNameStr = "B"+(((Integer)backCount.get(routeCode)).intValue());
			return makeCodeNameStr;
		}
		// 对物种特殊的路线进行处理
		for(int j=0;j<codeList.length;j++)
		{
			//System.out.println("当前路线："+routeCode);
			//System.out.println("下一个路线："+nextCode);
			//System.out.println("集合循环到："+codeList[j]);
			if(routeCode.equals(codeList[j]))
			{   
				if(routeCode.trim().equals("薄")){
					// 如果是"薄"，下个制造路线单位为“架(漆)”和“涂”，路线单位是“T”
					// 下个制造路线单位为“厚(焊)”路线单位是“B”
					//if(!count.keySet().contains(nextCode)){
					if(nextCode.equals("架(漆)")||nextCode.equals("涂")||nextCode.equals("专")){
						// CCEnd SS7
						routeKey = "薄1";
					}
//					CCBegin SS7
					else if(nextCode.equals("厚(焊)")){
//						CCEnd SS7
						routeKey = "薄2";
					}else{
						routeKey = "";
					}
				}
				//}//
				else if(routeCode.equals("厚(焊)")){
					// 如果下个制造路线单位为“架(漆)”、“涂”和“专”，路线单位是“T”
					// 下个制造路线单位为“架(横抛)”，路线单位是“P”
					// 下个制造路线单位为“架(装)”，路线单位是“B”
					//if(!count.keySet().contains(nextCode)){
					if(nextCode.equals("架(漆)")||nextCode.equals("涂")||nextCode.equals("专")){
						routeKey = "厚(焊)1";
					}
					else if(nextCode.equals("架(横抛)")){
						routeKey = "厚(焊)2";
					}
					else if(nextCode.equals("架(装)")){
						routeKey = "厚(焊)3";
					}
					//}
				} 
				else if(routeCode.equals("厚(试制)")){
					// 下个制造路线单位为“架(漆)”、“涂”，路线单位是“T”
					// 下个制造路线单位为“架(横抛)”、“协(抛丸)，路线单位是“P”
					// 制造路线单位有回头件，路线单位是“B1”

					if(nextCode.equals("架(漆)")||nextCode.equals("涂")){
						routeKey = "厚(试制)1";
					}
					else if(nextCode.equals("架(横抛)")||nextCode.equals("协(抛丸)")||nextCode.equals("协(抛)")||nextCode.equals("架(抛)")){
						routeKey = "厚(试制)2";
					}
					// CCBegin SS7
					else if((nextCode.equals("厚(纵)") || nextCode.equals("架(装)") || nextCode.equals("架(纵)"))&&ifCodeHT1(routeCodeList,index,nextCode,backCount)){
						//if(((Integer)backCount.get(nextCode)) != null)
							return routeKey = "B"+(((Integer)backCount.get(nextCode)).intValue());
						//else
							//return routeKey = "B";
					}
					//}// CCEnd SS7
				}
				else if(routeCode.equals("厚(纵)")){

					// 下个制造路线单位为“协(抛丸)”、“架(横抛)，路线单位是“P”
					// 下个制造路线单位为“厚(试制)”、“架(钻)”，路线单位是“B”
					// 下个制造路线单位为“协”，路线单位是“Q”

					if(nextCode.equals("架(横抛)")||nextCode.equals("协(抛丸)")||nextCode.equals("协(抛)")||nextCode.equals("架(抛)")){
						routeKey = "厚(纵)1";
					}
					else if(nextCode.equals("厚(试制)")||nextCode.equals("架(钻)")){
						routeKey = "厚(纵)2";
					}
					// CCBegin SS7
					else if(nextCode.equals("协")){
						routeKey = "厚(纵)3";
					}
					// CCEnd SS7

				}
				else if(routeCode.equals("架(横)")){
					// 下个制造路线单位为“架(漆)”、“涂”、“协”，路线单位是“T”
					// 下个制造路线单位为“架(横抛)”、“协(抛)”，路线单位是“P”
					// 下个制造路线单位为“厚(试制)”、“厚(焊)”，路线单位是“B”
					//if(!count.keySet().contains(nextCode)){
					if(nextCode.equals("架(漆)")||nextCode.equals("涂")||nextCode.equals("协")){
						routeKey = "架(横)1";
					}
					else if(nextCode.equals("架(横抛)")||nextCode.equals("协(抛丸)")||nextCode.equals("协(抛)")||nextCode.equals("架(抛)")){
						routeKey = "架(横)2";
					}
					else if(nextCode.equals("厚(试制)")||nextCode.equals("厚(焊)")){
						routeKey = "架(横)3";
					}
					//}
				}// CCBegin SS4
				else if(routeCode.equals("架(漆)")){

					// 下个制造路线单位为“涂”、“架(钻)”，路线单位是“B”，否则路线单位是“T”
					if(nextCode.equals("涂")){
						routeKey = "架(漆)2";
					}
					else {
						routeKey = "架(漆)1";
					}

				}// CCEnd SS4
				// CCBegin SS7
				else if(routeCode.equals("架(横抛)")){

					// 下个制造路线单位为“架(装)”，路线单位是“P”
					if(nextCode.equals("架(装)")){
						routeKey = "架(横抛)1";
					}
					//}
				}else if(routeCode.equals("架(钻)")){						
					// 下个制造路线单位为“厚(试制)”，路线单位是“B”
					if(nextCode.equals("厚(试制)")){
						routeKey = "架(钻)1";
					}
				}else if(routeCode.equals("宝")){						
					// 下个制造路线单位为“厚(试制)”，路线单位是“B”
					// 下个制造路线单位为“架（横）”或“薄”，路线单位是“M”
					if(nextCode.equals("厚(试制)")){
						routeKey = "宝1";
					}
					else if(nextCode.equals("架(横)") || nextCode.equals("薄")){
						routeKey = "宝2";
					}
				}else if(routeCode.equals("鞍")){
					// 下个制造路线单位为“厚(试制)”或“厚(纵)”，路线单位是“M”
					if(nextCode.equals("厚(试制)") || nextCode.equals("厚(纵)")){
						routeKey = "鞍";
					}
				}else if(routeCode.equals("架(装)")){
					// 下个制造路线单位为“架(漆)”，路线单位是“T”
					if(nextCode.equals("架(漆)")){
						routeKey = "架(装)";
					}
				}else if(routeCode.equals("架(纵)")){
					// 下个制造路线单位为“架(装)”或“厚(试制)”，路线单位是“B”
					// 下个制造路线单位为“协”，路线单位是“Q”
					if(nextCode.equals("架(装)")||nextCode.equals("厚(试制)")){
						routeKey = "架(纵)1";
					}else if(nextCode.equals("协")){
						routeKey = "架(纵)2";
					}
				}else if(routeCode.equals("协")){
					// 下个制造路线单位为“架(漆)”或“总”或“厚(纵)”，路线单位是“H”
					// 下个制造路线单位为“架(装)”或“厚(试制)”，路线单位是“B”
					if(nextCode.equals("架(漆)")||nextCode.equals("厚(纵)")){
						routeKey = "协1"; 
					}else if(nextCode.equals("架(装)")||nextCode.equals("厚(试制)")){
						routeKey = "协2";
					}
				}else if(routeCode.equals("协(抛丸)")){
					// 下个制造路线单位为“架(漆)”，路线单位是“B”
					// 下个制造路线单位为“架(装)”，路线单位是“H”
					if(nextCode.equals("架(漆)")){
						routeKey = "架(纵)1";
					}else if(nextCode.equals("架(装)")){
						routeKey = "架(纵)2";
					}
				}else if(routeCode.equals("架(漆)")){
					// 下个制造路线单位为“架(漆)”，路线单位是“B”
					if(nextCode.equals("涂")){
						routeKey = "架(漆)2";
					}
				}
			}
		}
		// CCEnd SS7
		return RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + routeKey);
	}
	  /**
		 * 判断路线中是否存在回头件
		 * 
		 * @param List
		 *            routeCodeList
		 * @return boolean;
		 * @throws QMException
		 */
	public boolean ifCodeHT(List routeCodeList,int index,String code){
		while(index>0){
			String currentCode = "";
			currentCode = (String)routeCodeList.get(--index);
			if(code.equals(currentCode)){
				return true;
			}
		}
		return false;
	}
	
	  /**
		 * 判断路线中是否存在回头件
		 * 
		 * @param List
		 *            routeCodeList
		 * @return boolean;
		 * @throws QMException
		 */
	public boolean ifCodeHT1(List routeCodeList,int index,String code,HashMap backCount){
		// String routeCode = makeNodes[j];
		while(index>0){
			String currentCode = "";
			currentCode = (String)routeCodeList.get(--index);
			//System.out.println("1---------"+currentCode+"------2--------"+(String)routeCodeList.get(x)+"----4---"+currentCode.equals((String)routeCodeList.get(x)));
			if(code.equals(currentCode)){
				int c1 = 0;
				if(backCount.keySet().contains(code)){
					Integer c = (Integer)backCount.get(code);
					c1 = c.intValue()+1;
				}else{
					c1=1;
				}
				backCount.put(code, new Integer(c1));
				return true;
			}
			//System.out.println("1---------"+currentCode+"-------3--------------"+j);
		}
		return false;

	}
	
	  /**
		 * 判断 是否是虚拟件
		 * 
		 * @param QMPartIfc
		 *            partIfc
		 * @return boolean;
		 * @throws QMXMLException
		 * @throws QMException
		 */
	public boolean getJFVirtualIdentity(QMPartIfc partIfc,String routeAsString,String routeAssemStr) throws QMException{
		Vector vec = new Vector();
		String xnj =getPartIBA(partIfc, "虚拟件","virtualPart");
		// System.out.println("getPartIBA xnj="+xnj);
		boolean re = false;
		if(xnj==null||xnj.length()==0){
			String partNumber = partIfc.getPartNumber();
			String lx         = partIfc.getPartType().toString();
			if(lx.equals("Logical")&&((routeAssemStr==null)||routeAssemStr.length()==0)){
				// System.out.println("111111111==============="+lx);
				re=true;
			}if(routeAsString.equals(routeAssemStr)&&!partNumber.contains("1000940")){
				// System.out.println("2222222222===============");
				re=true;
			}
		}else{
			if(xnj.equals("Y")){
				// System.out.println("33333333==============="+xnj);
				re=true;
			}
			
		}

		return re;
	}
	  /**
		 * 物料编号生成规则
		 * 
		 * @param QMPartIfc
		 *            partIfc
		 * @param String
		 *            partVersion
		 * @param String
		 *            makeCodeNameStr
		 * @param String
		 *            dashDelimiter
		 * @return boolean;
		 * @throws QMXMLException
		 * @throws QMException
		 */
	public String getMaterialNumber(QMPartIfc partIfc,String partVersion  ) throws QMException{
        String partNumber = partIfc.getPartNumber();
        String partType = partIfc.getPartType().getDisplay().toString();
        String materialNumber="";
        // ? 如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
        // ? 如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
        if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))||partType.equals("车型")){
        	materialNumber =  partIfc.getPartNumber();
        	return materialNumber;
        }
        // 驾驶室零件号包含“5000990”、 发动机零件号包含“1000940”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
        // 零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
        // “*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
        if(partNumber.indexOf("/")>=0){
        	int a = partNumber.indexOf("/");
        	// System.out.println("a="+a);
        	String temp = partNumber.substring(a);
        	// System.out.println("temp="+temp);
        	// 完全匹配型a
        	String[] array1 = {"0","ZBT","AH","BQ"};
        	// 在中间型×a×
        	String[] array2 = {"L01","J0","J1"};
        	// 在结尾a×
        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
        	// 完全匹配型a
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(str.equals(temp)){
        			materialNumber =  partIfc.getPartNumber()  ;
        			return materialNumber;
        		}
        	}
        	// 在中间型×a×
        	for (int i1 = 0; i1 < array2.length; i1++){
        		String str = array2[i1];
        		if(temp.indexOf(str)>=0){
        			materialNumber =  partIfc.getPartNumber()  ;
        			return materialNumber;
        		}
        	}
        	// 在结尾a×
        	for (int i1 = 0; i1 < array3.length; i1++){
        		String str = array3[i1];
        		if(temp.endsWith(str)){
        			materialNumber =  partIfc.getPartNumber() ;
        			return materialNumber;
        		}
        	}
        }
        // 获取零部件的“ERP回导零部件号”属性，如果该属性“/”后面的字符串含有“-”，取“-”后面的字符串，
        // 取到的字符串和“L01”、“0”，“1”、“2”、“3”、“4”、“ZBT”、“L”、“AH”、“J0”、“J1”、“SF”、
        // “BQ”、“ZC”比较，如果不属于以上列出的字符串，把ERP回导零部件号“/”后版本更新为新版本作为
        // PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号。如果零部件的“ERP回导零部件号”
        // 属性为空，则按照规则○1形成物料编码。
        // 正常的物料编码
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
            		// 如果“/”后“-”之后字符串不再集合array1里
            		// 把ERP回导零部件号“/”后版本更新为新版本作为PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号
            		// 零件号=原来的erp回导零件号，将原来的版本替换为最新的版本
            		String oldPartNumber = str.substring(0, a)+"/"+partVersion+"-"+temp1;
            		materialNumber =  oldPartNumber  ;
            		return materialNumber;
            	}
        	}
        }
		materialNumber =  partIfc.getPartNumber() + "/" + partVersion  ;

return materialNumber;
}
	  /**
		 * 物料编号生成规则
		 * 
		 * @param QMPartIfc
		 *            partIfc
		 * @param String
		 *            partVersion
		 * @param String
		 *            makeCodeNameStr
		 * @param String
		 *            dashDelimiter
		 * @return boolean;
		 * @throws QMXMLException
		 * @throws QMException
		 */
	public String getMaterialNumberHistory(String[] partQuan) throws QMException{
		String partNumber ="";
		String partVersionValue="";
		// 零部件编号
		partNumber=partQuan[0];
		// 零部件版本
		partVersionValue=partQuan[1];

		// 类型
		String partType = partQuan[8];
        String materialNumber="";
        // ? 如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
        // ? 如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
        if(partType.equals("标准件")||partType.equals("车型")){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        // 驾驶室零件号包含“5000990”、 发动机零件号包含“1000990”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        // 零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
        // “*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
        if(partNumber.indexOf("/")>=0){
        	int a = partNumber.indexOf("/");
        	String temp = partNumber.substring(a);
        	// 完全匹配型a
        	String[] array1 = {"0","ZBT","AH","BQ"};
        	// 在中间型×a×
        	String[] array2 = {"L01","J0","J1"};
        	// 在结尾a×
        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
        	// 完全匹配型a
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(str.equals(temp)){
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	// 在中间型×a×
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.indexOf(str)>=0){
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	// 在结尾a×
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.endsWith(str)){
        			materialNumber =  partNumber ;
        			return materialNumber;
        		}
        	}
        }
        // 获取零部件的“ERP回导零部件号”属性，如果该属性“/”后面的字符串含有“-”，取“-”后面的字符串，
        // 取到的字符串和“L01”、“0”，“1”、“2”、“3”、“4”、“ZBT”、“L”、“AH”、“J0”、“J1”、“SF”、
        // “BQ”、“ZC”比较，如果不属于以上列出的字符串，把ERP回导零部件号“/”后版本更新为新版本作为
        // PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号。如果零部件的“ERP回导零部件号”
        // 属性为空，则按照规则○1形成物料编码。
        // 正常的物料编码
        String str = getERPHD(partNumber+"/"+partVersionValue);
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
                			materialNumber =  partNumber ;
                			return materialNumber;
                		}
                	}
            		// 如果“/”后“-”之后字符串不再集合array1里
            		// 把ERP回导零部件号“/”后版本更新为新版本作为PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号
            		// 零件号=原来的erp回导零件号，将原来的版本替换为最新的版本
            		String oldPartNumber = str.substring(0, a)+"/"+partVersionValue+"-"+temp1;
            		materialNumber =  oldPartNumber  ;
            		return materialNumber;
            	}
        	}
        }
		materialNumber =  partNumber + "/" + partVersionValue  ;

return materialNumber;
}
	// 获取erp回导属性
	private String getERPHD(String partNumber){
		// Collection col =
		// query("JFMaterialSplit","partNumber","=",partNumber);
		Collection col = null;
		PersistService pService;
		try {
// CCBegin SS7
			pService = (PersistService)EJBServiceHelper.getService("PersistService");
// QMQuery query = new QMQuery("JFMaterialSplit");
// System.out.println("query="+query.getSQLSelf());
// QueryCondition condition = new QueryCondition("partNumber",
// QueryCondition.EQUAL, partNumber);
			 QMQuery query = new QMQuery("JFMaterialSplit");
			 QueryCondition qc1=new QueryCondition("partNumber","=",partNumber);
             query.addCondition(qc1);
             col=pService.findValueInfo(query);
// CCEnd SS7
		} catch (ServiceLocatorException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (QueryException e) {  
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (QMException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
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
	 /**
		 * 用于query查询
		 * 
		 * @param tableName
		 *            return Iterator
		 */


	public static Collection query(String tableName,String attrName,String operator,String condition)
				
		{
		 Collection partCondition= null;
            PersistService pService;
			try {
				pService = (PersistService)EJBServiceHelper.getService("PersistService");
				 QMQuery query=new QMQuery(tableName);
			     QueryCondition condition1=new QueryCondition(attrName,operator,condition);
			    query.addCondition(condition1);
			     partCondition=pService.findValueInfo(query,false);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		    return partCondition;
		}
	/**
	 * 将零件编号拆分出来
	 * 
	 * @return 零部件编号。
	 * @throws QMException
	 */
	public String getPartNumber(String materPartNumber) throws QMException {
		int i = materPartNumber.lastIndexOf("/");
		
		if(i>0){
			String partNumber = materPartNumber.substring(0,i);
		// System.out.println("partNumber="+partNumber);
			return partNumber;
		}
		return materPartNumber;
	}
// CCBegin SS3
	/**
	 * 是否是逻辑总成
	 * 
	 * @return boolean
	 * @throws QMException
	 */
	public boolean isLogical(QMPartIfc part,String lx_y ,String assembStr)throws QMException{
		boolean result=false;
		// 第5位为G并且装配路线为空并且制造路线含“用”
		//CCBegin SS6
		//if (part.getPartNumber().substring(4, 5).equals("G"))
		if (part.getPartNumber().length()>5&&part.getPartNumber().substring(4, 5).equals("G"))
		//CCEnd SS6
		{
			if(lx_y.contains("1")&&assembStr.equals("")){
				result=true;
				System.out.println("虚件");
			}
			if(!lx_y.contains("1")&&!assembStr.equals("")){
				result=true;
				System.out.println("实件");
			}
		}
		return result;
	}
// CCEnd SS3
// CCBegin SS5
	  /**
		 * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
		 * 
		 * @param partIfc
		 *            :QMPartIfc 零部件的值对象。
		 * @return collection:Collection 与partIfc关联的PartUsageLinkIfc的对象集合。
		 * @throws QMException
		 */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters begin ....");
        // 如果条件成立，则抛出PartException"参数不能为空"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters end....return is Colletion ");
        if(partIfc.getBsoName().equals("GenericPart"))
        	return pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
          else 
        	  return pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
      
    }
// CCEnd SS5
    /**
	 * 调试信息
	 */

          private static final boolean VERBOSESYSTEM = (RemoteProperty.getProperty(
	        "com.faw_qm.bomchange.verbose","true")).equals("true");
}

