/** 程序LoadPart.java	1.0  2003/06/12
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.csm.navigation.ejb.service.ClassificationService;
import com.faw_qm.csm.navigation.liteview.ClassificationStructDefaultView;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.jferp.client.ejbaction.SplitMaterielEJBAction;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.constraint.ConstraintGroup;
import com.faw_qm.iba.constraint.model.AttributeConstraintIfc;
import com.faw_qm.iba.constraint.util.Immutable;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.definition.litedefinition.ReferenceDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.iba.value.util.LoadValue;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.lite.QMPropertyVetoException;
import com.faw_qm.load.util.LoadHelper;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.util.LockHelper;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

/**
 * <p>Title: 零部件数据的导入。</p>
 * <p>Description: 零部件导入的信息包括：
 * 零部件的基本属性；
 * 零部件的事物特性；
 * 零部件的结构使用关系；
 * 零部件与文档的参考关系。
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 李玉芬、谢斌
 * @version 1.0
 * SS1 添加BOM调整变更采用单过滤条件 刘家坤 2013-06-12
 */
public class LoadErp implements Serializable
{
    public LoadErp()
    {
        super();
    }

    static final long serialVersionUID = 1L;
    
    /**
     * 处理.csv文件中的"AddUsageLink"命令
     * 用哈西表中的参数为属性构造一个已经存在的零部件的使用关系并保存在数据库中
     * 要求所有涉及的零部件必须是在数据库中已经存在的，否则会发生异常；
     *
     * @param hashtable               部件属性的名/值 对
     *                         属性如下：
     * 父部件号码            parentNumber
     * 子零部件号码          childNumber
     * 使用单位              unitStr
     * 使用数量              quantityStr
     *
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合
     * @return boolean
     */
    public static boolean createPartUsageLink(Hashtable hashtable,
            Vector return_objects)
    {
        boolean flag = false;
        //创建是否成功的标志
        String parentNumber = "";
        String childNumber = "";
        String unitStr = "";
        String quantityStr = "";
        String childMasterBsoID = "";
        try
        {
            parentNumber = LoadHelper.getValue("parentNumber", hashtable, 1);
            childNumber = LoadHelper.getValue("childNumber", hashtable, 1);
            unitStr = LoadHelper.getValue("unitStr", hashtable, 1);
            quantityStr = LoadHelper.getValue("quantityStr", hashtable, 1);
            PartUsageLinkIfc usageLink = new PartUsageLinkInfo();
            //获得父部件的bsoID
            String parentBsoID = getPartIDByNumber(parentNumber);
            //获得子零部件对应最新版本和版序的零部件的BsoID
            String childBsoID = getPartIDByMasterNumber(childNumber);
            if(parentBsoID == null)
            {
                String s = "\ncreatePartUsageLink:部件" + parentNumber
                        + "在装载文件中不存在或未被成功创建。";
                LoadHelper.printMessage(s);
            }
            if(childBsoID == null)
            {
                String s1 = "\ncreatePartUsageLink:部件" + childNumber
                        + "在装载文件中不存在或未被成功创建。";
                LoadHelper.printMessage(s1);
            }
            if(parentBsoID != null && childBsoID != null)
            {
                /*搜索当前导入的零部件的所有父节点，并且判断所有父节点中是否有跟csv文件的父亲零件
                 *parentBsoID相同的。从而用来控制结构重复导入。
                 */
                //开始：
                QMQuery query = new QMQuery("QMPartMaster");
                QueryCondition condition = new QueryCondition("partNumber",
                        "=", childNumber);
                query.addCondition(condition);
                PersistService persistService = (PersistService) EJBServiceHelper
                        .getService("PersistService");
                Collection collection = persistService.findValueInfo(query);
                Iterator iterator = collection.iterator();
                if(iterator.hasNext())
                {
                    childMasterBsoID = ((BaseValueIfc) iterator.next())
                            .getBsoID();
                }
                QMQuery query2 = new QMQuery("PartUsageLink");
                QueryCondition condition2 = new QueryCondition("leftBsoID",
                        "=", childMasterBsoID);
                query2.addCondition(condition2);
                Collection collection2 = persistService.findValueInfo(query2);
                Iterator iterator2 = collection2.iterator();
                if(iterator2.hasNext())
                {
                    PartUsageLinkIfc partUsageLinkIfc = (PartUsageLinkIfc) iterator2
                            .next();
                    if(partUsageLinkIfc.getRightBsoID().equals(parentBsoID))
                        return true;
                }
                //结束。
                Unit unit = Unit.toUnit(unitStr);
                usageLink.setLeftBsoID(childBsoID);
                usageLink.setRightBsoID(parentBsoID);
                float quantity = (new Float(quantityStr)).floatValue();
                QMQuantity quantity1 = new QMQuantity();
                //重新设置使用数量。
                quantity1.setQuantity(quantity);
                quantity1.setDefaultUnit(unit);
                usageLink.setQuantity(quantity);
                usageLink.setDefaultUnit(unit);
                usageLink.setQuantity(quantity);
                usageLink.setQMQuantity(quantity1);
                //在数据库中保存usageLink对象
                usageLink = (PartUsageLinkIfc) PartServiceRequest
                        .savePartUsageLink(usageLink);
                flag = true;
            }
        }
        catch (QMException exception)
        {
            String message = parentNumber + " - " + childNumber;
            LoadHelper.printMessage("创建" + message + "时发生错误");
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception e)
        {
            String message = parentNumber + " - " + childNumber;
            LoadHelper.printMessage("创建" + message + "时发生错误");
            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据零部件主信息的号码获得最新大版本下的最新版序的零部件的BosID。
     * @param partMasterNumber   零部件主信息的号码。
     * @return partBsoID         最新大版本下的最新版序的零部件的BosID。
     */
    protected static String getPartIDByNumber(String partMasterNumber)
    {
        String partBsoID = null;
        try
        {
            QMQuery query = new QMQuery("QMPartMaster");
            QueryCondition condition = new QueryCondition("partNumber", "=",
                    partMasterNumber);
            query.addCondition(condition);
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            Collection collection = persistService.findValueInfo(query);
            Iterator iterator = collection.iterator();
            QMPartMasterIfc master = null;
            if(iterator.hasNext())
            {
                master = (QMPartMasterIfc) iterator.next();
                //调用版本控制服务
                VersionControlService vcService = (VersionControlService) EJBServiceHelper
                        .getService("VersionControlService");
                //获得master下的所有小版本，按最新版序排列，
                //则第一个元素即为最新版本版序的零部件对象
                Collection collection1 = vcService.allVersionsOf(master);
                Iterator iterator1 = collection1.iterator();
                if(iterator1.hasNext())
                {
                    QMPartIfc part = (QMPartIfc) iterator1.next();
                    partBsoID = part.getBsoID();
                }
            }
            return partBsoID;
        }
        catch (QMException e)
        {
            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据零部件的号码获得零部件的BosID。
     * @param partNumber   零部件的号码。
     * @return partBsoID   零部件的BosID。
     */
    protected static String getPartIDByMasterNumber(String partNumber)
    {
        String partBsoID = null;
        try
        {
            QMQuery query = new QMQuery("QMPartMaster");
            QueryCondition condition = new QueryCondition("partNumber", "=",
                    partNumber);
            query.addCondition(condition);
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            Collection collection = persistService.findValueInfo(query);
            Iterator iterator = collection.iterator();
            if(iterator.hasNext())
            {
                partBsoID = ((BaseValueIfc) iterator.next()).getBsoID();
            }
            return partBsoID;
        }
        catch (QMException e)
        {
            e.printStackTrace();
            LoadHelper.printExceptionStatckTrace(e);
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LoadHelper.printExceptionStatckTrace(e);
            return null;
        }
    }
    /**
     * 通过基线的名字得到基线
     * 如果没有则创建新基线
     * @param name String
     * @param num String
     * @return ManagedBaselineIfc
     */
    private static ManagedBaselineIfc getBaselineByName(String name) {
      try {
    	  PersistService   ps = (PersistService) EJBServiceHelper.getService(
          "PersistService");
        QMQuery query = new QMQuery("ManagedBaseline");
        QueryCondition con = new QueryCondition("baselineName", "=", name);
        query.addCondition(con);
        
        	
       
        Collection col = ps.findValueInfo(query, false);
        
          for (Iterator i = col.iterator(); i.hasNext(); ) {
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
     * 处理.csv文件中的"AddErpBaseLine"命令。
     * 用哈西表中的参数为属性构造一个已经存在的零部件的参考关系并保存在数据库中
     * 要求所有涉及的零部件和文档必须是在数据库中已经存在的，否则会发生异常。
     * @param nv               部件属性的名/值 对
     *                         属性如下：
     * 零部件号码            partNumber
     * 基线名称          baseLineName
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合。
     * @return boolean
     */
    public static boolean createErpBaseLine(Hashtable nv,
            Vector return_objects)
    {
        boolean flag = false;
        String partNumber = "";
        String baseLineName = "";
        QMPartIfc partifc=null;
        Collection coll1;
        SplitMaterielEJBAction ejbAction=new SplitMaterielEJBAction();
        try
        {
        	 PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            partNumber = LoadHelper.getValue("partNumber", nv, 1);
            baseLineName = LoadHelper.getValue("baseLineName", nv, 1);
            //版本、路线，masterid
            System.out.println("partNumberpartNumber========"+partNumber);
            System.out.println("baseLineNamebaseLineName========"+baseLineName);
            ManagedBaselineIfc baselineifc= getBaselineByName(baseLineName);
            //获得零部件的BsoID
            if(baseLineName.toString().equals("按最新版发布")){
            String partBsoID = getPartIDByNumber(partNumber);
            Collection ac=new Vector();
            if(!partBsoID.equals("")){
            ac.add(partBsoID);
            }
            if(ac.size()==0){
	        	  throw new QMException("reference object not found "
                        + partNumber);
	           }
            if(partBsoID != null)
            {
                if(baseLineName == null)
                {
                    throw new QMException("reference object not found "
                            + baseLineName);
                }
              //CCBegin SS1
                ejbAction.publishPartsToERP(baselineifc.getBsoID(),ac,false,null,"1");
                flag = true;
            }
            }else if(!baseLineName.toString().equals("按最新版发布")&&!baseLineName.toString().equals("")){
            	QMQuery query = new QMQuery("QMPart");
 			   int j = query.appendBso("BaselineLink", false);
 			   int m = query.appendBso("QMPartMaster", false);
 			   
 			   query.addCondition(j, new QueryCondition("rightBsoID", "=", baselineifc.getBsoID()));
 			   query.addAND();
 			   query.addCondition(0, j, new QueryCondition("bsoID", "leftBsoID"));
 			   query.addAND();
 			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
 			   query.addAND();
 			   query.addCondition(m, new QueryCondition("partNumber", "=", partNumber));
 			    coll1 = pservice.findValueInfo(query);
 			   System.out.println("coll1coll1coll1000000000000000========"+coll1);
 			    if(coll1.size()!=0){
 			    	
 			    
 			     Iterator iter = coll1.iterator();
 	            while (iter.hasNext()) {
 	            partifc = (QMPartIfc) iter.next();     
 	            }
 	           System.out.println("partifc.getBsoID()partifc.getBsoID()111111========"+partifc.getBsoID());
 	           Collection ac1=new Vector();
 	            if(!partifc.getBsoID().equals("")){
 	            ac1.add(partifc.getBsoID());
 	            }
 	           System.out.println("ttttttttttttttttttt========"+ac1);
 	           if(ac1.size()==0){
 	        	  throw new QMException("reference object not found "
                          + partNumber);
 	           }
 	        //CCBegin SS1
 	           ejbAction.publishPartsToERP(baselineifc.getBsoID(),ac1,false,null,"1");
 	          //CCEnd SS1
               flag = true;
 			    }else{
 			    	throw new QMException("reference object not found "
                            + partNumber);
 			    }
            }
            else
            {
                String s1 = "\ncreatePartDocReference:没有当前基线。必须首先成功创建零部件基线。";
                LoadHelper.printMessage(s1);
            }
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            LoadHelper.printExceptionStatckTrace(ex);
        }
        return flag;
    }
    public static Collection searchRoute(String routeListNumber)throws QMException
    {
    	try
    	{
    		Collection coll;
    		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
    		QMQuery query=new QMQuery("TechnicsRouteList");
    	      QueryCondition condition = new QueryCondition("routeListNumber", "=", routeListNumber);
    	      query.addCondition(condition);
    	      query.addAND();
      		query.addCondition(new QueryCondition("iterationIfLatest",true));
    	      coll = pservice.findValueInfo(query);
    	      System.out.println("collcollcoll=============)0000000000====="+coll.size());
    		return coll;
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		throw new QMException(ex);
    	}
    }
    /**
     * 处理.csv文件中的"AddErpBaseLine"命令。
     * 用哈西表中的参数为属性构造一个已经存在的零部件的参考关系并保存在数据库中
     * 要求所有涉及的零部件和文档必须是在数据库中已经存在的，否则会发生异常。
     * @param nv               部件属性的名/值 对
     *                         属性如下：
     * 零部件号码            partNumber
     * 基线名称          baseLineName
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合。
     * @return boolean
     */
    public static boolean createErpRoute(Hashtable nv,
            Vector return_objects)
    {
        boolean flag = false;
        String routeListNumber = "";
        TechnicsRouteListIfc  routeifc=null;
        SplitMaterielEJBAction ejbAction=new SplitMaterielEJBAction();
        try
        {
            routeListNumber = LoadHelper.getValue("routeListNumber", nv, 1);
            System.out.println("routeListNumberrouteListNumber=========="+routeListNumber);
            Collection route= searchRoute(routeListNumber);
            if(route.size()==0){
            	throw new QMException("reference object not found "
                        + routeListNumber);
            }
            System.out.println("route.size()route.size()11111111111======"+route.size());
            if(route.size()!=0){
			     Iterator iter = route.iterator();
	            while (iter.hasNext()) {
	            	  routeifc = (TechnicsRouteListIfc) iter.next();     
	            }
	            System.out.println("routeifcrouteifcrouteifc2222222222221======"+routeifc.getBsoID());
              ejbAction.publishPartsToERP(null,null,true,routeifc.getBsoID(),"2");
              flag = true;
              System.out.println("flagflagflag====="+flag);
            }
            	
            
           
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            LoadHelper.printExceptionStatckTrace(ex);
        }
        return flag;
    }
    /**
     * 根据文档主信息的号码获得文档的BosID。
     * @param docMasterNumber   文档主信息的号码。
     * @return docBsoID         主文档的BosID。
     */
    protected static String getDocIDByMasterNumber(String docMasterNumber)
    {
        String docBsoID = null;
        try
        {
            QMQuery query = new QMQuery("DocMaster");
            QueryCondition condition = new QueryCondition("docNum", "=",
                    docMasterNumber);
            query.addCondition(condition);
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            Collection collection = persistService.findValueInfo(query);
            Iterator iterator = collection.iterator();
            DocMasterIfc master = null;
            if(iterator.hasNext())
            {
                master = (DocMasterIfc) iterator.next();
                docBsoID = master.getBsoID();
            }
            return docBsoID;
        }
        catch (QMException e)
        {
            e.printStackTrace();
            LoadHelper.printExceptionStatckTrace(e);
            return null;
        }
    }

    /**
     * 创建没有事物特性的零部件。
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     */
    public static boolean createPart(Hashtable hashtable, Hashtable hashtable1,
            Vector vector)
    {
        return createPartObject(hashtable, hashtable1, vector);
    }

    /**
     * 创建没有事物特性的零部件。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     */
    public static boolean createPart(Hashtable hashtable, Vector vector)
    {
        return createPartObject(hashtable, new Hashtable(), vector);
    }

    private static boolean createPartObject(Hashtable hashtable,
            Hashtable hashtable1, Vector vector)
    {
        boolean flag = false;
        try
        {
            LoadHelper.removeCacheValue("Current Part");
            LoadHelper.removeCacheValue("Current ContentHolder");
            QMPartIfc part = setPartAttributes(hashtable);
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            part = (QMPartIfc) pService.storeValueInfo(part);
            if(!LoadValue.establishCurrentIBAHolder(part))
                throw new QMException("Unable to establish Current IBAHolder");
            cacheNewPart(part);
            LoadHelper.setCacheValue("Current ContentHolder", part);
            vector.addElement(part);
            flag = true;
        }
        catch (QMException qmexception)
        {
            LoadHelper.printMessage("\ncreatePart: "
                    + qmexception.getLocalizedMessage());
            qmexception.printStackTrace();
        }
        catch (Exception exception)
        {
            LoadHelper.printMessage("\ncreatePart: " + exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }

    /**
     * 开始创建可以有事物特性的零部件。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     */
    public static boolean beginCreateQMPart(Hashtable hashtable, Vector vector)
    {
        Hashtable hash = new Hashtable();
        return beginCreateQMPart(hashtable, hash, vector);
    }

    /**
     * 开始创建可以有事物特性的零部件。
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     */
    public static boolean beginCreateQMPart(Hashtable hashtable,
            Hashtable hashtable1, Vector vector)
    {
        boolean flag = false;
        try
        {
            LoadHelper.removeCacheValue("Current Part");
            LoadHelper.removeCacheValue("Current ContentHolder");
            LoadHelper.removeCacheValue("com.faw_qm.load.LoadPart.TempQMPart");
            QMPartIfc part = setPartAttributes(hashtable);
            LoadHelper.setCacheValue("com.faw_qm.load.LoadPart.TempQMPart",
                    part);
            flag = LoadValue.beginIBAContainer();
        }
        catch (QMException exception)
        {
            LoadHelper.printMessage("\nbeginCreateQMPart: " + exception.getLocalizedMessage());
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception exception)
        {
            LoadHelper.printMessage("\nbeginCreateQMPart: " + exception.getMessage());
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        return flag;
    }

    /**
     * 结束创建可以有事物特性的零部件。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     */
    public static boolean endCreateQMPart(Hashtable hashtable, Vector vector)
    {
        Hashtable hash = new Hashtable();
        return endCreateQMPart(hashtable, hash, vector);
    }

    /**
     * 结束创建可以有事物特性的零部件。
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     */
    public static boolean endCreateQMPart(Hashtable hashtable,
            Hashtable hashtable1, Vector vector)
    {
        boolean flag = false;
        try
        {
            IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                    .getService("IBAValueService");
            LoadValue.endIBAContainer();
            AttributeContainer attributecontainer = LoadValue
                    .extractDefaultIBAContainer();
            QMPartIfc part = (QMPartIfc) LoadHelper
                    .getCacheValue("com.faw_qm.load.LoadPart.TempQMPart");
            if(attributecontainer != null)
            {
                AttributeContainer attributecontainer1 = part
                        .getAttributeContainer();
                if(attributecontainer1 != null)
                {
                    AttributeDefDefaultView aattributedefdefaultview[] = ((DefaultAttributeContainer) attributecontainer1)
                            .getAttributeDefinitions();
                    for (int i = 0; i < aattributedefdefaultview.length; i++)
                    {
                        AbstractValueView aabstractvalueview[] = ((DefaultAttributeContainer) attributecontainer)
                                .getAttributeValues(aattributedefdefaultview[i]);
                        if(aabstractvalueview == null
                                || aabstractvalueview.length == 0)
                        {
                            AbstractValueView aabstractvalueview1[] = ((DefaultAttributeContainer) attributecontainer1)
                                    .getAttributeValues(aattributedefdefaultview[i]);
                            for (int j = 0; j < aabstractvalueview1.length; j++)
                                ((DefaultAttributeContainer) attributecontainer)
                                        .addAttributeValue(aabstractvalueview1[j]);
                        }
                    }
                }
                part.setAttributeContainer(attributecontainer);
            }
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            part = (QMPartIfc) pService.storeValueInfo(part);
            String s = getValue(hashtable, "publishFlag", false);
            if(s != null && s.equalsIgnoreCase("true"))
            {
                ibaValueService.publishIBAToPartMaster(part);
            }
            cacheNewPart(part);
            LoadHelper.setCacheValue("Current ContentHolder", part);
            LoadHelper.removeCacheValue("com.faw_qm.load.LoadPart.TempQMPart");
            vector.addElement(part);
            flag = true;
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        return flag;
    }

    /**
     * 判断是否允许被检出。
     * @param workable 被检出对象。
     * @return 标识。
     * @throws LockException
     * @throws QMException
     */
    public static boolean isCheckoutAllowed(WorkableIfc workable)
            throws LockException, QMException
    {
        boolean flag = false;
        if(!WorkInProgressHelper.isWorkingCopy(workable))
        {
            flag = !WorkInProgressHelper.isCheckedOut(workable)
                    && !LockHelper.isLocked(workable);
        }
        return flag;
    }

    /**
     * 判断是否允许被撤销检出。
     * @param workable 被撤销检出对象。
     * @return
     */
    public static boolean isUndoCheckoutAllowed(WorkableIfc workable)
    {
        boolean flag = false;
        try
        {
            if(WorkInProgressHelper.isWorkingCopy(workable))
            {
                flag = WorkInProgressHelper.isCheckedOut(workable);
            }
        }
        catch (Exception _ex)
        {
            _ex.printStackTrace();
        }
        return flag;
    }

    /**
     * 获得被检出对象的工作副本。
     * @param workable 被检出对象。
     * @return 被检出对象的工作副本。
     * @throws LockException
     * @throws QMException
     */
    public static WorkableIfc getCheckOutObject(WorkableIfc workable)
            throws LockException, QMException
    {
        boolean flag = isCheckoutAllowed(workable);
        if(flag)
        {
            WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            FolderIfc folder = wService.getCheckoutFolder();
            try
            {
                wService.checkout(workable, folder, "");
            }
            catch (QMException exception)
            {
                exception.printStackTrace();
                throw exception;
            }
            return wService.workingCopyOf(workable);
        }
        else
        {
            throw new QMException(
                    "com.faw_qm.part.LoadPart.getCheckOutObject Exception");
        }
    }

    /**
     * 开始创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     * @throws QMException
     */
    public static boolean beginCreateOrUpdateQMPart(Hashtable hashtable,
            Vector vector) throws QMException
    {
        Hashtable hash = new Hashtable();
        return beginCreateOrUpdateQMPart(hashtable, hash, vector);
    }

    /**
     * 开始创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     * @throws QMException
     */
    public static boolean beginCreateOrUpdateQMPart(Hashtable hashtable,
            Hashtable hashtable1, Vector vector) throws QMException
    {
        boolean flag = false;
        QMPartIfc part = null;
        String s = null;
        String s1 = null;
        try
        {
            LoadHelper.removeCacheValue("CURRENT_LOAD_MODE");
            LoadHelper.removeCacheValue("Current Part");
            LoadHelper.removeCacheValue("Current ContentHolder");
            LoadHelper.removeCacheValue("com.faw_qm.load.LoadPart.TempQMPart");
            Object obj = hashtable.get("version");
            if(obj == null)
                hashtable.put("version", "");
            obj = hashtable.get("iteration");
            if(obj == null)
                hashtable.put("iteration", "");
            s = getValue(hashtable, "partNumber", true);
            //获得最新版本的 partBsoID
            String partID = getPartIDByNumber(s);
            if(partID != null)
            {
                PersistService pService = (PersistService) EJBServiceHelper
                        .getPersistService();
                IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                        .getService("IBAValueService");
                QMPartIfc part1 = (QMPartIfc) pService.refreshInfo(partID);
                part = (QMPartIfc) getCheckOutObject(part1);
                LoadHelper.setCacheValue("com.faw_qm.load.LoadPart.TempQMPart",
                        part);
                s1 = "UPDATE";
                LoadHelper.setCacheValue("CURRENT_LOAD_MODE", s1);
                part = (QMPartIfc) ibaValueService.refreshAttributeContainer(
                        part, null, null, null);
                DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) part
                        .getAttributeContainer();
                AttributeDefDefaultView aattributedefdefaultview[] = defaultattributecontainer
                        .getAttributeDefinitions();
                String s2 = "com.faw_qm.part.model.QMPartIfc";
                ClassificationStructDefaultView classificationstructdefaultview = null;
                try
                {
                    ClassificationService cService = (ClassificationService) EJBServiceHelper
                            .getService("ClassificationService");
                    classificationstructdefaultview = cService
                            .getClassificationStructDefaultView(s2);
                }
                catch (Exception exception1)
                {
                }
                if(classificationstructdefaultview != null)
                {
                    ReferenceDefView referencedefview = classificationstructdefaultview
                            .getReferenceDefView();
                    List list1 = (List) defaultattributecontainer
                            .getConstraintGroups();
                    List list2 = new ArrayList();
                    try
                    {
                        for (int i = 0; i < list1.size(); i++)
                        {
                            ConstraintGroup constraintgroup = (ConstraintGroup) list1
                                    .get(i);
                            if(constraintgroup != null)
                                if(!constraintgroup.getConstraintGroupLabel()
                                        .equals("Sourcing Factor"))
                                {
                                    list2.add(constraintgroup);
                                }
                                else
                                {
                                    Iterator iter = constraintgroup
                                            .getConstraints();
                                    ConstraintGroup constraintgroup1 = new ConstraintGroup();
                                    constraintgroup1
                                            .setConstraintGroupLabel(constraintgroup
                                                    .getConstraintGroupLabel());
                                    while (iter.hasNext())
                                    {
                                        AttributeConstraintIfc attributeconstraint1 = (AttributeConstraintIfc) iter
                                                .next();
                                        AttributeConstraintIfc attributeconstraint;
                                        if(attributeconstraint1
                                                .appliesToAttrDef(referencedefview)
                                                && (attributeconstraint1
                                                        .getValueConstraint() instanceof Immutable))
                                            attributeconstraint = attributeconstraint1;
                                        else
                                            constraintgroup1
                                                    .addConstraint(attributeconstraint1);
                                    }
                                    list2.add(constraintgroup1);
                                }
                        }
                        defaultattributecontainer.setConstraintGroups(list2);
                    }
                    catch (QMPropertyVetoException qmpropertyvetoexception)
                    {
                        qmpropertyvetoexception.printStackTrace();
                    }
                }
                if(defaultattributecontainer == null)
                    flag = LoadValue.beginIBAContainer();
                else
                    flag = LoadValue
                            .setCurrentCachedContainer(defaultattributecontainer);
            }
            else if(partID == null)
            {
                part = setPartAttributes(hashtable);
                LoadHelper.setCacheValue("com.faw_qm.load.LoadPart.TempQMPart",
                        part);
                s1 = "CREATE";
                LoadHelper.setCacheValue("CURRENT_LOAD_MODE", s1);
                flag = LoadValue.beginIBAContainer();
            }
            else
            {
                flag = false;
                String as[] = {s};
                LoadHelper.printMessage("删除" + as[0]);
                System.out
                        .println("com.faw_qm.part.LoadPart.beginCreateOrUpdateQMPart :  There should not be more than 1 latest iteration for the part with part number "
                                + s);
            }
        }
        catch (QMException exception)
        {
            LoadHelper.printMessage("\nbeginUpdateQMPart: "
                    + exception.getLocalizedMessage());
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception exception1)
        {
            LoadHelper.printMessage("\nbeginUpdateQMPart: "
                    + exception1.getMessage());
            LoadHelper.printExceptionStatckTrace(exception1);
        }
        finally
        {
            if(!flag && s1 != null && s1.compareTo("UPDATE") == 0)
            {
                try
                {
                    WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                            .getService("WorkInProgressService");
                    wService.undoCheckout(part);
                }
                catch (Exception exception3)
                {
                    String as1[] = {s};
                    LoadHelper.printMessage("定义替换件" + as1[0]);
                    exception3.printStackTrace();
                    throw new PartException(exception3);
                }
            }
        }
        return flag;
    }

    /**
     * 结束创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     * @throws PartException
     */
    public static boolean endCreateOrUpdateQMPart(Hashtable hashtable,
            Vector vector) throws PartException
    {
        Hashtable hash = new Hashtable();
        return endCreateOrUpdateQMPart(hashtable, hash, vector);
    }

    /**
     * 结束创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     * @throws PartException
     */
    public static boolean endCreateOrUpdateQMPart(Hashtable hashtable,
            Hashtable hashtable1, Vector vector) throws PartException
    {
        QMPartIfc part = null;
        boolean flag = false;
        String s = null;
        WorkInProgressService wService = null;
        try
        {
            s = (String) LoadHelper.getCacheValue("CURRENT_LOAD_MODE");
            LoadValue.endIBAContainer();
            AttributeContainer attributecontainer = LoadValue
                    .extractDefaultIBAContainer();
            part = (QMPartIfc) LoadHelper
                    .getCacheValue("com.faw_qm.load.LoadPart.TempQMPart");
            if(attributecontainer != null)
            {
                AttributeContainer attributecontainer1 = part
                        .getAttributeContainer();
                if(attributecontainer1 != null)
                {
                    AttributeDefDefaultView aattributedefdefaultview[] = ((DefaultAttributeContainer) attributecontainer1)
                            .getAttributeDefinitions();
                    for (int i = 0; i < aattributedefdefaultview.length; i++)
                    {
                        AbstractValueView aabstractvalueview[] = ((DefaultAttributeContainer) attributecontainer)
                                .getAttributeValues(aattributedefdefaultview[i]);
                        if(aabstractvalueview == null
                                || aabstractvalueview.length == 0)
                        {
                            AbstractValueView aabstractvalueview1[] = ((DefaultAttributeContainer) attributecontainer1)
                                    .getAttributeValues(aattributedefdefaultview[i]);
                            for (int j = 0; j < aabstractvalueview1.length; j++)
                                ((DefaultAttributeContainer) attributecontainer)
                                        .addAttributeValue(aabstractvalueview1[j]);
                        }
                    }
                }
                part.setAttributeContainer(attributecontainer);
            }
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            if(s.compareTo("CREATE") == 0)
                pService.storeValueInfo(part);
            else if(s.compareTo("UPDATE") == 0)
            {
                pService.updateValueInfo(part);
                wService.checkin(part,
                        "updated IBA value(s) or modeled attributes");
            }
            String s1 = getValue(hashtable, "publishFlag", false);
            IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                    .getService("IBAValueService");
            if(s1 != null && s1.equalsIgnoreCase("true"))
                ibaValueService.publishIBAToPartMaster(part);
            cacheNewPart(part);
            LoadHelper.setCacheValue("Current ContentHolder", part);
            LoadHelper.removeCacheValue("com.faw_qm.load.LoadPart.TempQMPart");
            vector.addElement(part);
            flag = true;
        }
        catch (QMException qmexception)
        {
            qmexception.printStackTrace();
        }
        finally
        {
            if(!flag && s != null && s.compareTo("UPDATE") == 0
                    && isUndoCheckoutAllowed(part))
                try
                {
                    wService.undoCheckout(part);
                }
                catch (Exception exception1)
                {
                    String as[] = {""};
                    LoadHelper.printMessage("定义替换件" + as[0]);
                    exception1.printStackTrace();
                    throw new PartException(exception1);
                }
            try
            {
                LoadHelper.removeCacheValue("CURRENT_LOAD_MODE");
            }
            catch (QMException qmexception1)
            {
            }
        }
        return flag;
    }

    /**
     * 开始创建零部件基本信息对象。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     */
    public static boolean beginQMPM(Hashtable hashtable, Vector vector)
    {
        Hashtable hash = new Hashtable();
        return beginQMPM(hashtable, hash, vector);
    }

    /**
     * 开始创建零部件基本信息对象。
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     */
    public static boolean beginQMPM(Hashtable hashtable, Hashtable hashtable1,
            Vector vector)
    {
        boolean flag = true;
        try
        {
            String s = LoadHelper.getValue("partNumber", hashtable, hashtable1,
                    0);
            String s1 = LoadHelper.getValue("partName", hashtable, hashtable1,
                    0);
            LoadHelper
                    .removeCacheValue("com.faw_qm.load.LoadPart.TempQMPartMaster");
            QMPartMasterIfc partmaster = new QMPartMasterInfo();
            partmaster.setPartName(s1);
            partmaster.setPartNumber(s);
            LoadHelper.setCacheValue(
                    "com.faw_qm.load.LoadPart.TempQMPartMaster", partmaster);
            flag = LoadValue.beginIBAContainer();
        }
        catch (QMPropertyVetoException propertyvetoexception)
        {
            propertyvetoexception.printStackTrace();
            flag = false;
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 结束创建零部件基本信息对象。
     * @param hashtable
     * @param vector
     * @return 成功标识。
     */
    public static boolean endQMPM(Hashtable hashtable, Vector vector)
    {
        Hashtable hash = new Hashtable();
        return endQMPM(hashtable, hash, vector);
    }

    public static boolean endQMPM(Hashtable hashtable, Hashtable hashtable1,
            Vector vector)
    {
        boolean flag = false;
        try
        {
            LoadValue.endIBAContainer();
            AttributeContainer attributecontainer = LoadValue
                    .extractDefaultIBAContainer();
            QMPartMasterIfc partmaster = (QMPartMasterIfc) LoadHelper
                    .getCacheValue("com.faw_qm.load.LoadPart.TempQMPartMaster");
            if(attributecontainer != null)
            {
                partmaster.setAttributeContainer(attributecontainer);
            }
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            partmaster = (QMPartMasterIfc) persistService
                    .saveValueInfo(partmaster);
            LoadHelper
                    .removeCacheValue("com.faw_qm.load.LoadPart.TempQMPartMaster");
            cacheNewMaster(partmaster);
            vector.addElement(partmaster);
            flag = true;
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        return flag;
    }

    /**
     * 设置新建零部件的基本属性。
     * @param hashtable
     * @return 零部件。
     * @throws QMException
     */
    private static QMPartIfc setPartAttributes(Hashtable hashtable)
            throws QMException
    {
        String partName = "";
        String partNumber = "";
        String defaultUnitStr = "";
        String producedByStr = "";
        String partTypeStr = "";
        String projectName = "";
        String lifeCycleName = "";
        String viewName = "";
        String location = "";
        String lifeCycleStateStr = "";
        QMPartIfc part = null;
        try
        {
            //获得零部件的基本属性信息
            partName = getValue(hashtable, "partName", true);
            partNumber = getValue(hashtable, "partNumber", true);
            defaultUnitStr = getValue(hashtable, "defaultUnitStr", true);
            producedByStr = getValue(hashtable, "producedByStr", true);
            partTypeStr = getValue(hashtable, "partTypeStr", true);
            projectName = getValue(hashtable, "projectName", false);
            lifeCycleName = getValue(hashtable, "lifeCycleName", false);
            viewName = getValue(hashtable, "viewName", false);
            location = getValue(hashtable, "location", true);
            lifeCycleStateStr = getValue(hashtable, "lifeCycleStateStr", false);
            part = new QMPartInfo();
            //设置名称
            part.setPartName(partName);
            //设置号码
            part.setPartNumber(partNumber);
            //设置默认单位
            Unit unit = Unit.toUnit(defaultUnitStr);
            part.setDefaultUnit(unit);
            //设置来源
            ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            part.setProducedBy(produce);
            //设置类型
            QMPartType type = QMPartType.toQMPartType(partTypeStr);
            part.setPartType(type);
            //设置项目
            part.setProjectName(projectName);
            //获得生命周期服务
            LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                    .getService("LifeCycleService");
            //根据生命周期模板名称获得生命周期模板
            LifeCycleTemplateIfc templateIfc = lifeCycleService
                    .getLifeCycleTemplate(lifeCycleName);
            //为零部件设置生命周期模板
            part = (QMPartIfc) lifeCycleService.setLifeCycle(
                    (LifeCycleManagedIfc) part, templateIfc);
            //设置视图
            part.setViewName(viewName);
            //获得资料夹服务
            FolderService folderService = (FolderService) EJBServiceHelper
                    .getService("FolderService");
            //为零部件设置存储位置
            part = (QMPartIfc) folderService.assignFolder(
                    (FolderEntryIfc) part, location);
            //设置生命周期状态
            LifeCycleState state = LifeCycleState.toLifeCycleState(lifeCycleStateStr);
            part = (QMPartIfc) lifeCycleService.setLifeCycleState(
                    (LifeCycleManagedIfc) part, state);
        }
        catch (QMPropertyVetoException exception)
        {
            LoadHelper.printMessage("\nsetPartAttributes: "
                    + exception.getLocalizedMessage());
            exception.printStackTrace();
            throw new PartException(exception);
        }
        return part;
    }

    public static String getValue(Hashtable hashtable, String s, boolean flag)
            throws QMException
    {
        String s1 = (String) hashtable.get(s);
        if(s1 == null)
        {
            String as[] = {s};
            String s2 = as[0] + "是无效的字段名称。在 csvmapfile.txt 中检查正确的名称。";
            throw new QMException(s2);
        }
        if(flag && s1.equals(""))
        {
            String as1[] = {s};
            String s3 = "在输入文件中未找到必需的字段" + as1[0] + "的值。";
            throw new QMException(s3);
        }
        if(!flag && s1.equals(""))
        {
            s1 = null;
        }
        return s1;
    }

    /**
     * 缓存当前导入的零部件信息。
     * @param part 要缓存的零部件。
     * @throws QMException
     */
    private static void cacheNewPart(QMPartIfc part) throws QMException
    {
//        System.out.println("LoadPart.cacheNewPart: " + part);
        String s = part.getPartNumber();
        String s1 = part.getVersionValue();
        LoadHelper.setCacheValue("Current Part", part);
        LoadHelper.setCacheValue("Part Number" + s, part);
        LoadHelper.setCacheValue("Part Number Version" + s + "|" + s1, part);
        cacheNewMaster((QMPartMasterIfc) part.getMaster());
    }

    /**
     * 缓存当前导入的零部件基本记录对象的信息。。
     * @param partmaster 要缓存的零部件基本记录对象。
     * @throws QMException
     */
    private static void cacheNewMaster(QMPartMasterIfc partmaster)
            throws QMException
    {
//        System.out.println("LoadPart.cacheNewMaster: " + partmaster);
        LoadHelper.setCacheValue("Master Number" + partmaster.getPartNumber(),
                partmaster);
    }
}
