/**
 * 程序LoadPart.java 1.0 2003/06/12 版权归一汽启明公司所有 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用 可在本公司授权范围内，使用本程序 保留所有权利
 * CR1 2010/08/25 马辉 原因:TD2286零部件数据导入功能，缺少单独导入零部件结构功能
 *                     方案:实施要求把独导入零部件结构功能再加上
 * CR2 2010/09/17 马辉 原因:导入零部件的时候如果部件已经存在，修订功能不好使
 * CCBegin by liunan 2011-08-25 打补丁P035
 * CR3 2011/05/31 马辉 修改原因:TD2405零部件导入功能问题
 *                     错误原因：导入已存在的零部件时，如果来源与类型属性被修改的话，新的来源与类型没有被设置进入新的partIfc中，而造成错误。
 *                     修改方案：再导入已存在的零部件时，将来源与类型属性往partIfc中重新设置一遍再保存
 * CCEnd by liunan 2011-08-25
 * CCBegin by liunan 2011-08-29 打补丁P036
 * CR4 2011/08/05 王亮 原因：TD2429，零部件导入状态不对。
 * CCEnd by liunan 2011-08-29
 * SS1 导入BOM不好使 徐德政 2014-09-27 
 * SS2 A004-2015-3143修订时，未设置生命周期状态。 liunan 2015-6-10
 */
package com.faw_qm.part.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.csm.constraint.ejb.service.CSMConstraintService;
import com.faw_qm.csm.constraint.model.AbstractCSMAttributeConstraintView;
import com.faw_qm.csm.constraint.model.CSMContainerConstraintDefaultView;
import com.faw_qm.csm.constraint.model.CSMSingleDefConstraintDefaultView;
import com.faw_qm.csm.constraint.model.CSMSingleDefConstraintIfc;
import com.faw_qm.csm.navigation.ClassificationNodeLoader;
import com.faw_qm.csm.navigation.ejb.service.ClassificationObjectsFactory;
import com.faw_qm.csm.navigation.ejb.service.ClassificationService;
import com.faw_qm.csm.navigation.exception.CSMClassificationNavigationException;
import com.faw_qm.csm.navigation.liteview.ClassificationNodeDefaultView;
import com.faw_qm.csm.navigation.liteview.ClassificationNodeNodeView;
import com.faw_qm.csm.navigation.liteview.ClassificationStructDefaultView;
import com.faw_qm.csm.navigation.model.ClassificationNodeIfc;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.client.container.main.NewValueCreator;
import com.faw_qm.iba.constraint.ConstraintGroup;
import com.faw_qm.iba.constraint.ValueConstraint;
import com.faw_qm.iba.constraint.exception.IBAConstraintException;
import com.faw_qm.iba.constraint.model.AttributeConstraintIfc;
import com.faw_qm.iba.constraint.util.Immutable;
import com.faw_qm.iba.constraint.util.ValueRequired;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.definition.litedefinition.ReferenceDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.BooleanValueDefaultView;
import com.faw_qm.iba.value.litevalue.FloatValueDefaultView;
import com.faw_qm.iba.value.litevalue.IntegerValueDefaultView;
import com.faw_qm.iba.value.litevalue.LiteIBAReferenceable;
import com.faw_qm.iba.value.litevalue.RatioValueDefaultView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.litevalue.TimestampValueDefaultView;
import com.faw_qm.iba.value.litevalue.URLValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.iba.value.util.LoadValue;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.lite.QMPropertyVetoException;
import com.faw_qm.load.util.LoadDataException;
import com.faw_qm.load.util.LoadHelper;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.util.LockHelper;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;
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
 */
public class LoadPart implements Serializable
{
    /**用于标记资源信息路径*/
    protected static final String RESOURCE = "com.faw_qm.part.client.main.util.QMProductManagerRB";

    /** 分类结构。*/
    private static String CLASSIFICATION_STRUCTURE = "Classification Struct";

    private static String LOAD_CLASSIFICATION = "Load Part With Classification";

    //导入零部件增加的参考文档集合
    private static String CURRENT_PART_REFERENCE_DOC_LINK = "CURRENT-PART-REFERENCE-DOC-LINK";

    //当前导入的零部件
    private static String CURRENT_PART = "Current Part";

    //当前导入的零部件
    private static String CURRENT_CONTENT_HOLDER = "Current ContentHolder";

    //临时的零部件
    private static String TEMP_PART = "com.faw_qm.load.LoadPart.TempQMPart";

    //载入的模式（创建还是更新）
    private static String CURRENT_LOAD_MODE = "CURRENT_LOAD_MODE";

    //导入零部件增加的使用关系集合
    private static String CURRENT_PART_USAGE_LINK = "CURRENT-PART-USAGE-LINK";

    //是否允许检出
    private static String CHECKOUT_ALLOWED = "CHECKOUT_ALLOWED";

    //当系统中存在相同编号的零部件时的操作（不导入或者小版本升级或者修订）
    private static String BIG_VERSION = "bigversion";

    public LoadPart()
    {
        super();
    }

    static final long serialVersionUID = 1L;
    

    /**CR1 begin
     * 只导入零部件结构
     * 处理.csv文件中的"AddUsageLink"命令
     * 用哈西表中的参数为属性构造一个已经存在的零部件的使用关系并保存在数据库中
     * 要求所有涉及的零部件必须是在数据库中已经存在的，否则会发生异常；
     * @param nv               部件属性的名/值 对
     * 父部件号码            parentNumber
     * 子零部件号码          childNumber
     * 使用单位              unitStr
     * 使用数量              quantityStr
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合
     * @return boolean
     */ 
    public static boolean createPartUsageLinkonly(Hashtable nv,
            Vector return_objects)
    {
        // 创建是否成功的标志
        String parentNumber = "";
        String childNumber = "";
        String unitStr = "";
        String quantityStr = "";
        String childMasterBsoID = "";
        try
        {
//        	SS1 Begin
        	Vector<String> values = (Vector<String>)nv.get("values");
            /*parentNumber = LoadHelper.getValue("parentNumber", nv, 0);
            childNumber = LoadHelper.getValue("childNumber", nv, 0);
            unitStr = LoadHelper.getValue("unitStr", nv, 0);
            quantityStr = LoadHelper.getValue("quantityStr", nv, 0);*/
        	parentNumber = values.get(0);
        	childNumber = values.get(1);
        	unitStr = values.get(2);
        	quantityStr = values.get(3);
//        	SS1 End
            PartUsageLinkIfc usageLink = new PartUsageLinkInfo();
            // 获得父部件的bsoID
            String parentBsoID = getPartIDByNumber(parentNumber);
            // 获得子零部件对应最新版本和版序的零部件的BsoID
            String childBsoID = getPartIDByMasterNumber(childNumber);
            if((parentBsoID != null) && (childBsoID != null))
            {
                /*
                 * 搜索当前导入的零部件的所有父节点，并且判断所有父节点中是否有跟csv文件的父亲零件 parentBsoID相同的。从而用来控制结构重复导入。
                 */
                // begin
                try
                {
                    QMQuery query = new QMQuery("QMPartMaster");
                    QueryCondition condition = new QueryCondition("partNumber", "=", childNumber);
                    query.addCondition(condition);
                    PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
                    Collection collection = persistService.findValueInfo(query);
                    Iterator iterator = collection.iterator();
                    if(iterator.hasNext())
                    {
                        childMasterBsoID = ((BaseValueIfc)iterator.next()).getBsoID();
                    }
                }catch(QMException e)
                {
                    e.printStackTrace();
                    return false;
                }
                try
                {
                    QMQuery query = new QMQuery("PartUsageLink");
                    QueryCondition condition = new QueryCondition("leftBsoID", "=", childMasterBsoID);
                    query.addCondition(condition);
                    PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
                    Collection collection = persistService.findValueInfo(query);
                    Iterator iterator = collection.iterator();
                    if(iterator.hasNext())
                    {
                        PartUsageLinkIfc partUsageLinkIfc = (PartUsageLinkIfc)iterator.next();
                        if(partUsageLinkIfc.getRightBsoID().equals(parentBsoID))
                            return true;
                    }
                }catch(QMException e)
                {
                    e.printStackTrace();
                    return false;
                }
                //end
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
                usageLink = (PartUsageLinkIfc)PartServiceRequest.savePartUsageLink(usageLink);
                return true;
            }else
            {
            	System.out.println("0000+++++"+"ID都为空");
                return false;
            }
        }catch(QMException exception)
        {
            String message = parentNumber + " - " + childNumber;
            LoadHelper.printMessage("创建" + message + "时发生错误");
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
            return false;
        }catch(Exception e)
        {
            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
            return false;
        }

    }//CR1End
    
    /**
     * 处理.csv文件中的"AddUsageLink"命令
     * 用哈西表中的参数为属性构造一个已经存在的零部件的使用关系并保存在数据库中
     * 要求所有涉及的零部件必须是在数据库中已经存在的，否则会发生异常；
     *
     * @param attributes               部件属性的名/值 对
     *                         属性如下：
     * 父部件号码            parentNumber
     * 子零部件号码          childNumber
     * 使用单位              unitStr
     * 使用数量              quantityStr
     *
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合
     * @return boolean
     */
    public static boolean createPartUsageLink(Hashtable attributes,
            Vector return_objects)
    {
        //创建是否成功的标志
        boolean flag = false;
        //导入父零部件的编号
        String parentNumber = "";
        //导入子零部件的
        String childNumber = "";
        //使用单位
        String unitStr = "";
        //使用数量
        String quantityStr = "";
        try
        {
            parentNumber = LoadHelper.getValue("parentNumber", attributes, 1);
            childNumber = LoadHelper.getValue("childNumber", attributes, 1);
            unitStr = LoadHelper.getValue("unitStr", attributes, 1);
            quantityStr = LoadHelper.getValue("quantityStr", attributes, 1);
            PartUsageLinkIfc usageLink = new PartUsageLinkInfo();
            QMPartIfc exitedPartIfc = getPartIfcByNumber(parentNumber);
            //获得父部件的bsoID
            String parentBsoID = null;
            if(exitedPartIfc != null)
            {
                parentBsoID = exitedPartIfc.getBsoID();
            }
            if(parentBsoID == null)
            {
                String s = "\ncreatePartUsageLink:部件" + parentNumber
                        + "在装载文件中不存在或未被成功创建。";
                //                LoadHelper.printMessage(s);
                throw new QMException(s);
            }
            //获得子零部件对应最新版本和版序的零部件的BsoID
            String childMasterBsoID = getPartIDByMasterNumber(childNumber);
            if(childMasterBsoID == null)
            {
                String s1 = "\ncreatePartUsageLink:部件" + childNumber
                        + "在装载文件中不存在或未被成功创建。";
                //                LoadHelper.printMessage(s1);
                throw new QMException(s1);
            }
            if(parentBsoID != null && childMasterBsoID != null)
            {
                /*搜索当前导入的零部件的所有父节点，并且判断所有父节点中是否有跟csv文件的父亲零件
                 *parentBsoID相同的。从而用来控制结构重复导入。
                 */
                //开始：
                try
                {
                    boolean exitFlag = isHasPartUsageLink(parentBsoID,
                            childNumber);
                    //结束。
                    //如果系统中不存在相同的结构
                    //根据选择的不同，做不同的事情。
                    int bigVersion = (Integer) attributes.get(BIG_VERSION);
                    //如果选择忽略，则不导入结构信息。
                    if((exitFlag && (bigVersion == 1 || bigVersion == 2)))
                    {
                        //已存在零部件的新版本值对象
                        QMPartIfc newExitingPart = getNewPart(exitedPartIfc,
                                attributes);
                        if(newExitingPart != null)
                        {
                            parentBsoID = newExitingPart.getBsoID();
                        }
                        Unit unit = Unit.toUnit(unitStr);
                        usageLink.setLeftBsoID(childMasterBsoID);
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
                catch (Exception e)
                {
                    e.printStackTrace();
                }
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
     * 搜索当前导入的零部件的所有父节点，并且判断所有父节点中是否有跟csv文件的父亲零件
     * parentBsoID相同的。从而用来控制结构重复导入。
     * @param parentBsoID
     * @param childNumber
     * @return
     * @throws QMException
     */
    private static boolean isHasPartUsageLink(String parentBsoID,
            String childNumber) throws QMException
    {
        boolean isHasPartUsageLink = false;
        String childMasterBsoID = "";
        QMQuery query = new QMQuery("QMPartMaster");
        QueryCondition condition = new QueryCondition("partNumber", "=",
                childNumber);
        query.addCondition(condition);
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        Collection collection = persistService.findValueInfo(query);
        Iterator iterator = collection.iterator();
        if(iterator.hasNext())
        {
            childMasterBsoID = ((BaseValueIfc) iterator.next()).getBsoID();
        }
        QMQuery query2 = new QMQuery("PartUsageLink");
        QueryCondition condition2 = new QueryCondition("leftBsoID", "=",
                childMasterBsoID);
        query2.addCondition(condition2);
        Collection collection2 = persistService.findValueInfo(query2);
        if(collection2 != null)
        {
            Iterator iterator2 = collection2.iterator();
            while (iterator2.hasNext())
            {
                PartUsageLinkIfc partUsageLinkIfc = (PartUsageLinkIfc) iterator2
                        .next();
                if(partUsageLinkIfc.getRightBsoID().equals(parentBsoID))
                {
                    isHasPartUsageLink = true;
                    break;
                }
            }
        }
        return isHasPartUsageLink;
    }

    /**
     * 对传入的零部件，进行小版本的升级或者修订工作，返回新的零部件值对象。
     * @param partIfc
     * @param hashtable
     * @return
     * @throws QMException
     */
    private static QMPartIfc getNewPart(QMPartIfc partIfc, Hashtable hashtable)
            throws QMException
    {
        QMPartIfc newPartIfc = null;
        int bigVersion = (Integer) hashtable.get(BIG_VERSION);
        //小版本的升级
        if(bigVersion == 1)
        {
            // 零部件的检出
            //			QMPartIfc checkingIn = (QMPartIfc) getCheckingInObject(partIfc);
            //
            //			// 根据导入文件中的零部件类型和来源修改系统中已经存在的零部件
            //			String producedByStr = (String) hashtable.get("producedByStr");
            //			String partTypeStr = (String) hashtable.get("partTypeStr");
            //			// 检入或者修订时的说明信息
            //			String versionDes = (String) hashtable.get("versionDes");
            //			if (versionDes == null) {
            //				versionDes = "";
            //			}
            //			// 设置来源
            //			if (producedByStr != null && !producedByStr.equals("")) {
            //				ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            //				partIfc.setProducedBy(produce);
            //			}
            //			// 设置类型
            //			if (partTypeStr != null && !partTypeStr.equals("")) {
            //				QMPartType type = QMPartType.toQMPartType(partTypeStr);
            //				partIfc.setPartType(type);
            //			}
            //			// 零部件的检入
            //			PersistService pService = (PersistService) EJBServiceHelper
            //					.getPersistService();
            //			WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
            //					.getService("WorkInProgressService");
            //			pService.updateValueInfo(checkingIn);
            //			if(isCheckoutAllowed(checkingIn)){
            //				newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            //			}
            //			else{
            //	        	//判断是否为首次检入
            ////	            Object object = IBAUtility.invokeServiceMethod("SessionService",
            ////	                    "getCurUserID", new Class[]{}, new Object[]{});
            //	            SessionService sessionService=(SessionService) EJBServiceHelper
            //	            .getService("SessionService");
            //	            //获得文件夹服务
            //	            FolderService  folderService = (FolderService)EJBServiceHelper.getService("FolderService");
            //	            Object object = sessionService.getCurUserID();
            //	            //是否被当前用户拥有
            //	        	boolean isOwnFlag = OwnershipHelper.isOwnedBy((OwnableIfc) checkingIn, (String) object);
            //	        	boolean isInPerson=folderService.inPersonalFolder(checkingIn);
            //	        	if(isOwnFlag&&isInPerson){
            //	        		newPartIfc= (QMPartIfc)pService.saveValueInfo(checkingIn);
            //	        	}
            //			}
            ////			newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            newPartIfc = saveOrCheckIn(partIfc, hashtable);
        }
        // 修订。
        else if(bigVersion == 2)
        {
            StandardPartService partService = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            //修订
            newPartIfc = partService.revisePart(partIfc);
            String lifeCycleName = (String) hashtable.get("lifeCycleName");
            String location = (String) hashtable.get("location");
            String projectName = (String) hashtable.get("projectName");
            //CCBegin SS2
            String lifeCycleStateStr = (String) hashtable.get("lifeCycleStateStr");
            //CCEnd SS2
            if(lifeCycleName != null && !lifeCycleName.equals(""))
            {
                // 获得生命周期服务
                LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                        .getService("LifeCycleService");
                // 根据生命周期模板名称获得生命周期模板
                //CCBegin SS2
                /*LifeCycleTemplateInfo templateIfc = lifeCycleService
                        .getLifeCycleTemplate(lifeCycleName);
                Iterator iter = lifeCycleService.findStates(templateIfc)
                        .iterator();
                if(iter.hasNext())
                {
                    LifeCycleState state = (LifeCycleState) iter.next();
                    partIfc.setLifeCycleState(state);
                }*/
                
                //设置生命周期状态
                LifeCycleState state = LifeCycleState.toLifeCycleState(lifeCycleStateStr);
                newPartIfc = (QMPartIfc) lifeCycleService.setLifeCycleState((LifeCycleManagedIfc) newPartIfc, state);
                newPartIfc.setLifeCycleState(state);
                //CCEnd SS2
            }
            // 为零部件设置存储位置
            if(location != null && !location.equals(""))
            {
                // 获得资料夹服务
                FolderService folderService = (FolderService) EJBServiceHelper
                        .getService("FolderService");
                partIfc = (QMPartIfc) folderService.assignFolder(
                        (FolderEntryIfc) partIfc, location);
            }
            // 设置工作组
            if(projectName != null && !projectName.equals(""))
            {
                partIfc.setProjectName(projectName);
            }
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            newPartIfc = (QMPartIfc) pService.saveValueInfo(newPartIfc);
        }
        return newPartIfc;
    }

    /**
     * 对传入的零部件，进行小版本的升级或者修订工作，返回新的零部件值对象。
     * @param partIfc
     * @param hashtable
     * @return
     * @throws QMException
     */
    private static QMPartIfc getNewPartByCache(QMPartIfc partIfc, int bigVersion)
            throws QMException
    {
        QMPartIfc newPartIfc = null;
        //小版本的升级
        if(bigVersion == 1)
        {
            // 零部件的检出
            //			QMPartIfc checkingIn = (QMPartIfc) getCheckingInObject(partIfc);
            //
            //			// 根据导入文件中的零部件类型和来源修改系统中已经存在的零部件
            //			String producedByStr = (String) hashtable.get("producedByStr");
            //			String partTypeStr = (String) hashtable.get("partTypeStr");
            //			// 检入或者修订时的说明信息
            //			String versionDes = (String) hashtable.get("versionDes");
            //			if (versionDes == null) {
            //				versionDes = "";
            //			}
            //			// 设置来源
            //			if (producedByStr != null && !producedByStr.equals("")) {
            //				ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            //				partIfc.setProducedBy(produce);
            //			}
            //			// 设置类型
            //			if (partTypeStr != null && !partTypeStr.equals("")) {
            //				QMPartType type = QMPartType.toQMPartType(partTypeStr);
            //				partIfc.setPartType(type);
            //			}
            //			// 零部件的检入
            //			PersistService pService = (PersistService) EJBServiceHelper
            //					.getPersistService();
            //			WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
            //					.getService("WorkInProgressService");
            //			pService.updateValueInfo(checkingIn);
            //			if(isCheckoutAllowed(checkingIn)){
            //				newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            //			}
            //			else{
            //	        	//判断是否为首次检入
            ////	            Object object = IBAUtility.invokeServiceMethod("SessionService",
            ////	                    "getCurUserID", new Class[]{}, new Object[]{});
            //	            SessionService sessionService=(SessionService) EJBServiceHelper
            //	            .getService("SessionService");
            //	            //获得文件夹服务
            //	            FolderService  folderService = (FolderService)EJBServiceHelper.getService("FolderService");
            //	            Object object = sessionService.getCurUserID();
            //	            //是否被当前用户拥有
            //	        	boolean isOwnFlag = OwnershipHelper.isOwnedBy((OwnableIfc) checkingIn, (String) object);
            //	        	boolean isInPerson=folderService.inPersonalFolder(checkingIn);
            //	        	if(isOwnFlag&&isInPerson){
            //	        		newPartIfc= (QMPartIfc)pService.saveValueInfo(checkingIn);
            //	        	}
            //			}
            ////			newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            newPartIfc = saveOrCheckInByCache(partIfc);
        }
        // 修订。
        else if(bigVersion == 2)
        {
            StandardPartService partService = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            //修订
            newPartIfc = partService.revisePart(partIfc);
            String lifeCycleName = (String) LoadHelper
                    .getCacheValue("lifeCycleName");
            String location = (String) LoadHelper.getCacheValue("location");
            String projectName = (String) LoadHelper
                    .getCacheValue("projectName");
            //CCBegin SS2
            String lifeCycleStateStr = (String) LoadHelper.getCacheValue("lifeCycleStateStr");
            //CCEnd SS2
            if(lifeCycleName != null && !lifeCycleName.equals(""))
            {
                // 获得生命周期服务
                LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                        .getService("LifeCycleService");
                // 根据生命周期模板名称获得生命周期模板
                LifeCycleTemplateInfo templateIfc = lifeCycleService
                        .getLifeCycleTemplate(lifeCycleName);
                newPartIfc.setLifeCycleTemplate(templateIfc.getBsoID());//CR2
                //CCBegin SS2
                /*Iterator iter = lifeCycleService.findStates(templateIfc)
                        .iterator();
                if(iter.hasNext())
                {
                    LifeCycleState state = (LifeCycleState) iter.next();
                    newPartIfc.setLifeCycleState(state);//CR2
                }*/
                
                //设置生命周期状态
                LifeCycleState state = LifeCycleState.toLifeCycleState(lifeCycleStateStr);
                newPartIfc = (QMPartIfc) lifeCycleService.setLifeCycleState((LifeCycleManagedIfc) newPartIfc, state);
                newPartIfc.setLifeCycleState(state);
                //CCEnd SS2
            }
            // 为零部件设置存储位置
            if(location != null && !location.equals(""))
            {
                // 获得资料夹服务
                FolderService folderService = (FolderService) EJBServiceHelper
                        .getService("FolderService");
                newPartIfc = (QMPartIfc) folderService.assignFolder(
                        (FolderEntryIfc) newPartIfc, location);//CR2
            }
            // 设置工作组
            if(projectName != null && !projectName.equals(""))
            {
                newPartIfc.setProjectName(projectName);//CR2
            }
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            newPartIfc = (QMPartIfc) pService.saveValueInfo(newPartIfc);
            // 为零部件设置存储位置
            if(location != null && !location.equals(""))
            {
                // 获得资料夹服务
                FolderService folderService = (FolderService) EJBServiceHelper
                        .getService("FolderService");
                newPartIfc = (QMPartIfc) folderService.assignFolder(
                        (FolderEntryIfc) newPartIfc, location);
            }
        }
        return newPartIfc;
    }

    /**
     * 根据零部件主信息的号码获得最新大版本下的最新版序的零部件的BosID。
     * 
     * @param partMasterNumber
     *            零部件主信息的号码。
     * @return partBsoID 最新大版本下的最新版序的零部件的BosID。
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
     * 根据零部件主信息的号码获得最新大版本下的最新版序的零部件的BosID。
     * @param partMasterNumber   零部件主信息的号码。
     * @return partBsoID         最新大版本下的最新版序的零部件的BosID。
     */
    protected static QMPartIfc getPartIfcByNumber(String partMasterNumber)
    {
        QMPartIfc part = null;
        try
        {
            QMQuery query = new QMQuery("QMPartMaster");
            QueryCondition condition = new QueryCondition("partNumber", "=",
                    partMasterNumber);
            query.addCondition(condition);
            query.setChildQuery(false);
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
                    part = (QMPartIfc) iterator1.next();
                }
            }
            return part;
        }
        catch (QMException e)
        {
            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据零部件的号码获得零部件的Master的BosID。
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
     * 处理.csv文件中的"AddRefernceLink"命令。
     * 用哈西表中的参数为属性构造一个已经存在的零部件的参考关系并保存在数据库中
     * 要求所有涉及的零部件和文档必须是在数据库中已经存在的，否则会发生异常。
     * @param nv               部件属性的名/值 对
     *                         属性如下：
     * 零部件号码            partNumber
     * 参考文档号码          docNumber
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合。
     * @return boolean
     */
    public static boolean createPartRefernceLink(Hashtable nv,
            Vector return_objects)
    {
        boolean flag = false;
        String partNumber = "";
        String docNumber = "";
        try
        {
            partNumber = LoadHelper.getValue("partNumber", nv, 1);
            docNumber = LoadHelper.getValue("docNumber", nv, 1);
            //获得零部件的BsoID
            String partBsoID = getPartIDByNumber(partNumber);
            //获得主文档BsoID
            String docBsoID = getDocIDByMasterNumber(docNumber);
            PartReferenceLinkIfc referenceLink = new PartReferenceLinkInfo();
            if(partBsoID != null)
            {
                if(docBsoID == null)
                {
                    throw new QMException("reference object not found "
                            + docNumber);
                }
                referenceLink.setRightBsoID(partBsoID);
                referenceLink.setLeftBsoID(docBsoID);
                //调用零部件标准服务
                //在数据库中保存referenceLink对象
                referenceLink = (PartReferenceLinkIfc) PartServiceRequest
                        .savePartReferenceLink(referenceLink);
                flag = true;
            }
            else
            {
                String s1 = "\ncreatePartDocReference:没有当前零部件。必须首先成功创建零部件。";
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


//anan
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
    	System.out.println("1111111111111111111");
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
    	System.out.println("222222222222");
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
//anan


    /**
     * 开始创建可以有事物特性的零部件。
     * @param attributes Hashtable 属性集合
     * @param vector
     * @return 成功标识。
     */
    public static boolean beginCreateQMPart(Hashtable attributes, Vector vector)
    {
        return beginCreateQMPart(attributes);
    }

    /**
     * 开始创建可以有事物特性的零部件。
     * @param attributes Hashtable 属性集合
     * @return 成功标识。
     */
    private static boolean beginCreateQMPart(Hashtable attributes)
    {
        boolean flag = false;
        try
        {
            LoadHelper.removeCacheValue(CURRENT_PART);
            LoadHelper.removeCacheValue(CURRENT_CONTENT_HOLDER);
            LoadHelper.removeCacheValue(TEMP_PART);
            QMPartIfc part = setPartAttributes(attributes);
            LoadHelper.setCacheValue(TEMP_PART, part);
            flag = LoadValue.beginIBAContainer();
        }
        catch (QMException exception)
        {
            LoadHelper.printMessage("\nbeginCreateQMPart: "
                    + exception.getLocalizedMessage());
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception exception)
        {
            LoadHelper.printMessage("\nbeginCreateQMPart: "
                    + exception.getMessage());
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        return flag;
    }

    /**
     * 结束创建可以有事物特性的零部件。
     * @param attributes
     * @param vector
     * @return 成功标识。
     */
    public static boolean endCreateQMPart(Hashtable attributes, Vector vector)
    {
        return endCreateQMPart(attributes, new Hashtable(), vector);
    }

    /**
     * 结束创建可以有事物特性的零部件。
     * 
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     */
    private static boolean endCreateQMPart(Hashtable hashtable,
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
            QMPartIfc part = (QMPartIfc) LoadHelper.getCacheValue(TEMP_PART);
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
            LoadHelper.setCacheValue(CURRENT_CONTENT_HOLDER, part);
            LoadHelper.removeCacheValue(TEMP_PART);
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
    private static boolean isCheckoutAllowed(WorkableIfc workable)
            throws LockException, QMException
    {
        boolean flag = false;
        //获得文件夹服务
        FolderService folderService = (FolderService) EJBServiceHelper
                .getService("FolderService");
        if(!WorkInProgressHelper.isWorkingCopy(workable))
        {
            flag = !WorkInProgressHelper.isCheckedOut(workable)
                    && !LockHelper.isLocked(workable)
                    && !folderService.inPersonalFolder(workable);
        }
        return flag;
    }

    /**
     * 判断是否允许被撤销检出。
     * @param workable 被撤销检出对象。
     * @return
     */
    private static boolean isUndoCheckoutAllowed(WorkableIfc workable)
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
     * 检入或者保存零部件。公共资料夹中的零部件进行检出再检入，已经被当前用户检出的零部件进行检入操作，
     * 当前用户创建的零部件保存到公共资料夹，被他人检出或者他人创建的零部件抛出异常。
     * @param workable 被检出对象。
     * @return 被检出对象的工作副本。
     * @throws LockException
     * @throws QMException
     */
    private static QMPartIfc saveOrCheckIn(QMPartIfc partIfc,
            Hashtable hashtable) throws LockException, QMException
    {
        boolean isCheckoutAllowed = isCheckoutAllowed(partIfc);
        //是否被当前用户拥有
        boolean isOwnFlag = false;
        // 零部件的检出
        QMPartIfc checkingIn = null;
        QMPartIfc newPartIfc = null;
        if(isCheckoutAllowed)
        {
            WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            FolderIfc folder = wService.getCheckoutFolder();
            wService.checkout(partIfc, folder, "");
            checkingIn = (QMPartIfc) wService.workingCopyOf(partIfc);
        }
        else
        {
            //判断是否为首次检入
            //            Object object = IBAUtility.invokeServiceMethod("SessionService",
            //                    "getCurUserID", new Class[]{}, new Object[]{});
            SessionService sessionService = (SessionService) EJBServiceHelper
                    .getService("SessionService");
            Object object = sessionService.getCurUserID();
            isOwnFlag = OwnershipHelper.isOwnedBy((OwnableIfc) partIfc,
                    (String) object);
            if(isOwnFlag)
            {
                checkingIn = partIfc;
            }
            else
            {
                throw new QMException(
                        "com.faw_qm.part.LoadPart.getCheckOutObject Exception");
            }
        }
        //        checkingIn=getPersonPart(partIfc,hashtable);
        // 根据导入文件中的零部件类型和来源修改系统中已经存在的零部件
        String producedByStr = (String) hashtable.get("producedByStr");
        String partTypeStr = (String) hashtable.get("partTypeStr");
        // 检入或者修订时的说明信息
        String versionDes = (String) hashtable.get("versionDes");
        if(versionDes == null)
        {
            versionDes = "";
        }
        // 设置来源
        if(producedByStr != null && !producedByStr.equals(""))
        {
            ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            partIfc.setProducedBy(produce);
            //			checkingIn.setProducedBy(produce);
        }
        // 设置类型
        if(partTypeStr != null && !partTypeStr.equals(""))
        {
            QMPartType type = QMPartType.toQMPartType(partTypeStr);
            partIfc.setPartType(type);
            //			checkingIn.setPartType(type);
        }
        // 零部件的检入
        PersistService pService = (PersistService) EJBServiceHelper
                .getPersistService();
        pService.updateValueInfo(checkingIn);
        if(isCheckoutAllowed)
        {
            WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            newPartIfc = (QMPartIfc) pService.saveValueInfo(newPartIfc);
        }
        else
        {
            //判断是否为首次检入
            //获得文件夹服务
            FolderService folderService = (FolderService) EJBServiceHelper
                    .getService("FolderService");
            boolean isInPerson = folderService.inPersonalFolder(checkingIn);
            if(isOwnFlag && isInPerson)
            {
                newPartIfc = (QMPartIfc) pService.saveValueInfo(checkingIn);
                String location = (String) hashtable.get("location");
                //为零部件设置存储位置
                if(location != null && !location.equals(""))
                {
                    FolderIfc folderIfc = folderService.getFolder(location);
                    newPartIfc = (QMPartIfc) folderService.changeFolder(
                            (FolderEntryIfc) newPartIfc, folderIfc);
                }
            }
        }
        return newPartIfc;
    }

    /**
     * 检入或者保存零部件。公共资料夹中的零部件进行检出再检入，已经被当前用户检出的零部件进行检入操作，
     * 当前用户创建的零部件保存到公共资料夹，被他人检出或者他人创建的零部件抛出异常。
     * @param workable 被检出对象。
     * @return 被检出对象的工作副本。
     * @throws LockException
     * @throws QMException
     */
    private static QMPartIfc saveOrCheckInByCache(QMPartIfc partIfc)
            throws LockException, QMException
    {
        boolean isCheckoutAllowed = (Boolean) LoadHelper
                .getCacheValue(CHECKOUT_ALLOWED);
        //是否被当前用户拥有
        boolean isOwnFlag = false;
        // 零部件的检出
        QMPartIfc checkingIn = null;
        QMPartIfc newPartIfc = null;
        if(isCheckoutAllowed)
        {
            WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            FolderIfc folder = wService.getCheckoutFolder();
            wService.checkout(partIfc, folder, "");
            checkingIn = (QMPartIfc) wService.workingCopyOf(partIfc);
            //CCBegin by liunan 2011-08-25 打补丁P035
            checkingIn.setProducedBy(partIfc.getProducedBy());//CR3
            checkingIn.setPartType(partIfc.getPartType());//CR3
            //CCEnd by liunan 2011-08-25
        }
        else
        {
            //判断是否为首次检入
            //            Object object = IBAUtility.invokeServiceMethod("SessionService",
            //                    "getCurUserID", new Class[]{}, new Object[]{});
            SessionService sessionService = (SessionService) EJBServiceHelper
                    .getService("SessionService");
            Object object = sessionService.getCurUserID();
            isOwnFlag = OwnershipHelper.isOwnedBy((OwnableIfc) partIfc,
                    (String) object);
            if(isOwnFlag)
            {
                checkingIn = partIfc;
            }
            else
            {
                throw new QMException(
                        "com.faw_qm.part.LoadPart.getCheckOutObject Exception");
            }
        }
        //        checkingIn=getPersonPart(partIfc,hashtable);
        // 根据导入文件中的零部件类型和来源修改系统中已经存在的零部件
        String producedByStr = (String) LoadHelper
                .getCacheValue("producedByStr");
        String partTypeStr = (String) LoadHelper.getCacheValue("partTypeStr");
        // 检入或者修订时的说明信息
        String versionDes = (String) LoadHelper.getCacheValue("versionDes");
        if(versionDes == null)
        {
            versionDes = "";
        }
        // 设置来源
        if(producedByStr != null && !producedByStr.equals(""))
        {
            ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            partIfc.setProducedBy(produce);
            //			checkingIn.setProducedBy(produce);
        }
        // 设置类型
        if(partTypeStr != null && !partTypeStr.equals(""))
        {
            QMPartType type = QMPartType.toQMPartType(partTypeStr);
            partIfc.setPartType(type);
            //			checkingIn.setPartType(type);
        }
        // 零部件的检入
        PersistService pService = (PersistService) EJBServiceHelper
                .getPersistService();
        pService.updateValueInfo(checkingIn);
        if(isCheckoutAllowed)
        {
            WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            newPartIfc = (QMPartIfc) pService.saveValueInfo(newPartIfc);
        }
        else
        {
            //判断是否为首次检入
            //获得文件夹服务
            FolderService folderService = (FolderService) EJBServiceHelper
                    .getService("FolderService");
            boolean isInPerson = folderService.inPersonalFolder(checkingIn);
            if(isOwnFlag && isInPerson)
            {
                newPartIfc = (QMPartIfc) pService.saveValueInfo(checkingIn);
                String location = (String) LoadHelper.getCacheValue("location");
                //为零部件设置存储位置
                if(location != null && !location.equals(""))
                {
                    FolderIfc folderIfc = folderService.getFolder(location);
                    newPartIfc = (QMPartIfc) folderService.changeFolder(
                            (FolderEntryIfc) newPartIfc, folderIfc);
                }
            }
        }
        return newPartIfc;
    }

    /**
     * 开始创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param attributes
     * @param vector
     * @return 成功标识。
     * @throws QMException
     */
    public static boolean beginCreateOrUpdateQMPart(Hashtable attributes,
            Vector vector) throws QMException
    {
        return beginCreateOrUpdateQMPart(attributes);
    }

    /**
     * 开始创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param attributes
     * @return 成功标识。
     * @throws QMException
     */
    private static boolean beginCreateOrUpdateQMPart(Hashtable attributes)
            throws QMException
    {
        boolean flag = false;
        QMPartIfc partIfc = null;
        //载入零部件的编号
        String partNumber = null;
        //载入模式（创建或者更新）
        String loadMode = null;
        boolean isCheckoutAllowed = false;
        try
        {
            LoadHelper.removeCacheValue(CURRENT_PART);
            LoadHelper.removeCacheValue(CURRENT_CONTENT_HOLDER);
            LoadHelper.removeCacheValue(CURRENT_LOAD_MODE);
            LoadHelper.removeCacheValue(TEMP_PART);
            LoadHelper.removeCacheValue(CURRENT_PART_USAGE_LINK);
            LoadHelper.removeCacheValue(CURRENT_PART_REFERENCE_DOC_LINK);
            LoadHelper.removeCacheValue(CHECKOUT_ALLOWED);
            Object obj = attributes.get("version");
            if(obj == null)
                attributes.put("version", "");
            obj = attributes.get("iteration");
            if(obj == null)
                attributes.put("iteration", "");
            partNumber = getValue(attributes, "partNumber", true);
            //缓存导入零部件的信息
            cacheLoadPartInfo(attributes);
            //获得最新版本的零部件值对象
            partIfc = getPartIfcByNumber(partNumber);
            if(partIfc != null)
            {
                IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                        .getService("IBAValueService");
                //判断是否检出
                isCheckoutAllowed = isCheckoutAllowed(partIfc);
                //                LoadHelper.setCacheValue(TEMP_PART, part);
                //载入模式是更新
                loadMode = "UPDATE";
                partIfc = (QMPartIfc) ibaValueService
                        .refreshAttributeContainer(partIfc, null, null, null);
                DefaultAttributeContainer attrContainer = null;
                //可以考虑去掉该方法，因为在下面总是设置空的属性容器
                attrContainer = getNewDefaultAttributeContainer(partIfc);
                if(attrContainer == null)
                {
                    flag = LoadValue.beginIBAContainer();
                }
                else
                {
                    //                    flag = LoadValue
                    //                    .setCurrentCachedContainer(defaultattributecontainer);
                    flag = LoadValue
                            .setCurrentCachedContainer(new DefaultAttributeContainer());
                }
                //CCBegin by liunan 2011-08-25 打补丁P035
                //CR3 begin
                String producedByStr = "";
                String partTypeStr = "";
                producedByStr = getValue(attributes, "producedByStr", true);
                partTypeStr = getValue(attributes, "partTypeStr", true);
//              设置来源
                ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
                partIfc.setProducedBy(produce);
                //设置类型
                QMPartType type = QMPartType.toQMPartType(partTypeStr);
                partIfc.setPartType(type);//CR3 end
                //CCEnd by liunan 2011-08-25
            }
            else
            {
                partIfc = setPartAttributes(attributes);
                //                LoadHelper.setCacheValue(TEMP_PART, part);
                loadMode = "CREATE";
                flag = LoadValue.beginIBAContainer();
            }
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
            LoadHelper.printMessage("\nbeginUpdateQMPart: "
                    + exception.getLocalizedMessage());
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception exception1)
        {
            exception1.printStackTrace();
            LoadHelper.printMessage("\nbeginUpdateQMPart: "
                    + exception1.getMessage());
            LoadHelper.printExceptionStatckTrace(exception1);
        }
        finally
        {
            LoadHelper.setCacheValue(CURRENT_LOAD_MODE, loadMode);
            LoadHelper.setCacheValue(TEMP_PART, partIfc);
            LoadHelper.setCacheValue(CURRENT_PART_USAGE_LINK, new ArrayList());
            LoadHelper.setCacheValue(CURRENT_PART_REFERENCE_DOC_LINK,
                    new ArrayList());
            LoadHelper.setCacheValue(CHECKOUT_ALLOWED, Boolean
                    .valueOf(isCheckoutAllowed));
            if(!flag && loadMode != null && loadMode.compareTo("UPDATE") == 0)
            {
                try
                {
                    WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                            .getService("WorkInProgressService");
                    wService.undoCheckout(partIfc);
                }
                catch (Exception exception3)
                {
                    //                    String as1[] = {partNumber};
                    //                    LoadHelper.printMessage("定义替换件" + as1[0]);
                    exception3.printStackTrace();
                    throw new PartException(exception3);
                }
            }
        }
        return flag;
    }

    /**
     * 结束创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * @param attributes
     * @param vector
     * @return 成功标识。
     * @throws PartException
     */
    public static boolean endCreateOrUpdateQMPart(Hashtable attributes,
            Vector vector) throws PartException
    {
        return endCreateOrUpdateQMPart(attributes, new Hashtable(), vector);
    }

    /**
     * 结束创建或更新可以有事物特性的零部件。适用于无法知道系统中是否含有该零部件的情况。
     * 
     * @param attributes
     * @param hashtable1
     * @param vector
     * @return 成功标识。
     * @throws PartException
     */
    public static boolean endCreateOrUpdateQMPart(Hashtable attributes,
            Hashtable hashtable1, Vector vector) throws PartException
    {
        QMPartIfc partIfc = null;
        boolean flag = false;
        //载入模式（创建或者更新）
        String loadMode = null;
        WorkInProgressService wService = null;
        try
        {
            LoadValue.endIBAContainer();
            partIfc = (QMPartIfc) LoadHelper.getCacheValue(TEMP_PART);
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            int bigVersion = (Integer) attributes.get(BIG_VERSION);
            loadMode = (String) LoadHelper.getCacheValue(CURRENT_LOAD_MODE);
            if(loadMode.compareTo("CREATE") == 0)
            {
                QMPartIfc myPart = (QMPartIfc) pService.storeValueInfo(partIfc);
                partIfc = myPart;
            }
            else if(loadMode.compareTo("UPDATE") == 0)
            {
                // getNewPartByCache(part,bigVersion);
                String partNumber = partIfc.getPartNumber();
                Collection partCol = getPartIfcColByNumber(partNumber);
                QMPartIfc exitingPart = null;
                Iterator iterator1 = null;
                if(partCol != null)
                {
                    iterator1 = partCol.iterator();
                    if(iterator1.hasNext())
                    {
                        exitingPart = (QMPartIfc) iterator1.next();
                        //CCBegin by liunan 2011-08-25 打补丁P035
                        //CR3 begin
                        exitingPart.setProducedBy(partIfc.getProducedBy());
                        exitingPart.setPartType(partIfc.getPartType());
                        //CCEnd by liunan 2011-08-25
                    }
                }
                // 如果系统中不存在相同编号的零部件，则保存零部件。
                if(exitingPart == null)
                {
                    // loadPartIfc = (QMPartIfc)
                    // pService.storeValueInfo(loadPartIfc);
                }
                // 系统中存在相同编号的零部件
                else
                {
                    // String versionDes=getValue(hashtable, "versionDes",
                    // false);
                    QMPartIfc tempPartIfc = null;
                    ViewService viewService = (ViewService) EJBServiceHelper
                            .getService("ViewService");
                    // String loadPartViewName = loadPartIfc.getViewName();
                    String loadPartViewName = (String) LoadHelper
                            .getCacheValue("viewName");
                    String exitingPartViewName = exitingPart.getViewName();
                    exitingPart = null;
                    iterator1 = partCol.iterator();
                    while (iterator1.hasNext())
                    {
                        tempPartIfc = (QMPartIfc) iterator1.next();
                        if(tempPartIfc.getViewName() != null
                                && tempPartIfc.getViewName().equals(
                                        loadPartViewName))
                        {
                            exitingPart = tempPartIfc;
                            break;
                        }
                    }
                    // 1.2 如果系统中已存在导入的零部件数据，对存在的数据进行小版本的升级。
                    // 方法：getPartIDByNumber(String );
                    // 1.2.1 首先根据零部件编号查询零部件值对象，判断系统中是否存在相同编号的零部件。
                    // 1.2.2 如果系统中存在相同编号的零部件，则进行小版本的升级。
                    // 系统中存在相同编号和视图的零部件
                    if(exitingPart != null)
                    {
                        partIfc = getNewPartByCache(exitingPart, bigVersion);
                    }
                    else
                    {
                        // 系统中不存在相同编号和视图的零部件
                        // 选择升级小版本，则抛出异常
                        if(bigVersion == 1)
                        {
                            throw new QMException("系统中已经存在零部件编号为"
                                    + partIfc.getPartNumber() + "的"
                                    + exitingPartViewName + "零部件");
                        }
                        // 选择修订
                        else if(bigVersion == 2)
                        {
                            ViewObjectInfo loadPartViewIfc = (ViewObjectInfo) viewService
                                    .getView(loadPartViewName);
                            Vector viewVec = viewService
                                    .getAllParents(loadPartViewIfc);
                            iterator1 = partCol.iterator();
                            if(viewVec != null)
                            {
                                // ViewObjectIfc aview1[] = new
                                // ViewObjectIfc[viewVec.size()];
                                // ((Collection) viewVec).toArray(aview1);
                                String[] viewName = new String[viewVec.size()];
                                for (int i = 0; i < viewVec.size(); i++)
                                {
                                    ViewObjectIfc viewIfc = (ViewObjectIfc) viewVec
                                            .get(i);
                                    viewName[i] = viewIfc.getViewName();
                                }
                                boolean exitFlag = false;
                                while (iterator1.hasNext() && !exitFlag)
                                {
                                    tempPartIfc = (QMPartIfc) iterator1.next();
                                    String sysPartViewName = tempPartIfc
                                            .getViewName();
                                    for (int i = 0; i < viewName.length; i++)
                                    {
                                        if(sysPartViewName != null
                                                && sysPartViewName
                                                        .equals(viewName[i]))
                                        {
                                            exitingPart = tempPartIfc;
                                            //CCBegin by liunan 2011-08-25 打补丁P035
                                            exitingPart.setProducedBy(partIfc.getProducedBy());
                                            exitingPart.setPartType(partIfc.getPartType());//CR3 end
                                            //CCEnd by liunan 2011-08-25
                                            exitFlag = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            // 如果系统中存在，则发布视图版本
                            if(exitingPart != null)
                            {
                                QMPartIfc newPartIfc = (QMPartIfc) viewService
                                        .newBranchForView(exitingPart,
                                                loadPartViewIfc);
                                String lifeCycleName = (String) LoadHelper
                                        .getCacheValue("lifeCycleName");
                                String location = (String) LoadHelper
                                        .getCacheValue("location");
                                // 获得生命周期服务
                                LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                                        .getService("LifeCycleService");
                                // 根据生命周期模板名称获得生命周期模板
                                LifeCycleTemplateInfo templateIfc = lifeCycleService
                                        .getLifeCycleTemplate(lifeCycleName);
                                Vector vec = lifeCycleService
                                        .findStates(templateIfc);
                                Iterator ite = vec.iterator();
                                if(ite.hasNext())
                                {
                                    LifeCycleState state = (LifeCycleState) ite
                                            .next();
                                    newPartIfc.setLifeCycleState(state);
                                }
                                // 为零部件设置生命周期模板
                                // exitingPart = (QMPartIfc)
                                // lifeCycleService.setLifeCycle(
                                // (LifeCycleManagedIfc) exitingPart,
                                // templateIfc);
                                // 获得资料夹服务
                                FolderService folderService = (FolderService) EJBServiceHelper
                                        .getService("FolderService");
                                // 为零部件设置存储位置
                                newPartIfc = (QMPartIfc) folderService
                                        .assignFolder(
                                                (FolderEntryIfc) newPartIfc,
                                                location);
                                // 设置工作组
                                String projectName = (String) LoadHelper
                                        .getCacheValue("projectName");
                                // newPartIfc.setProjectName(loadPartIfc.getProjectName());
                                newPartIfc.setProjectName(projectName);
                                partIfc = (QMPartIfc) pService
                                        .saveValueInfo(newPartIfc);
                            }
                            // 如果系统中不存在，则抛出异常
                            else
                            {
                                throw new QMException("系统中已存在的编号为"
                                        + partIfc.getPartNumber()
                                        + "的零部件，无法发布到" + loadPartViewName
                                        + "，请检查导入文件的视图！");
                            }
                        }
                    }
                }
                // pService.updateValueInfo(part);
                // wService.checkin(part,
                // "updated IBA value(s) or modeled attributes");
            }
            // part=(QMPartIfc)pService.updateValueInfo(part);
            if(loadMode.compareTo("CREATE") == 0
                    || (bigVersion == 1 || bigVersion == 2))
            {
                if(partIfc != null)
                {
                    AttributeContainer addAttributecontainer = LoadValue
                            .extractDefaultIBAContainer();
                    IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                            .getService("IBAValueService");
                    partIfc = (QMPartIfc) ibaValueService
                            .refreshAttributeContainer(partIfc, null, null,
                                    null);
                    AttributeContainer partAttributecontainer1 = partIfc
                            .getAttributeContainer();
                    if(addAttributecontainer != null)
                    {
                        if(partAttributecontainer1 != null)
                        {
                            DefaultAttributeContainer newAttri = (DefaultAttributeContainer) ((DefaultAttributeContainer) partAttributecontainer1)
                                    .clone();
                            //							newAttri.clearContainer();
                            AttributeDefDefaultView oldAbstractvalueview1[] = newAttri
                                    .getAttributeDefinitions();
                            for (int index = 0; index < oldAbstractvalueview1.length; index++)
                            {
                                //								if(oldAbstractvalueview1[index] instanceof AttributeDefDefaultView){
                                //TODO 删除分类的方法到底是用这个还是下面的？	
                                newAttri.deleteAttributeValues(
                                        oldAbstractvalueview1[index], true);
                                //								else{
                                //									newAttri.deleteAttributeValues(oldAbstractvalueview1[index]);
                                newAttri
                                        .deleteAttributeValues(oldAbstractvalueview1[index]);
                            }
                            AbstractValueView addAbstractvalueview1[] = ((DefaultAttributeContainer) addAttributecontainer)
                                    .getAttributeValues();
                            for (int i = 0; i < addAbstractvalueview1.length; i++)
                            {
                                newAttri
                                        .addAttributeValue(addAbstractvalueview1[i]);
                            }
                            partIfc.setAttributeContainer(newAttri);
                        }
                        else
                        {
                            DefaultAttributeContainer newAttri = new DefaultAttributeContainer();
                            AbstractValueView addAbstractvalueview1[] = ((DefaultAttributeContainer) addAttributecontainer)
                                    .getAttributeValues();
                            for (int i = 0; i < addAbstractvalueview1.length; i++)
                            {
                                newAttri
                                        .addAttributeValue(addAbstractvalueview1[i]);
                            }
                            partIfc.setAttributeContainer(newAttri);
                            // part.setAttributeContainer(partAttributecontainer1);
                        }
                    }
                    endLoadPartWithClassification(partIfc);
                    partIfc = (QMPartIfc) pService.updateValueInfo(partIfc);
                }
                clearPartUsageLink(partIfc);
                clearPartRefDocLink(partIfc);
                clearPartDesDocLink(partIfc);
                List partUsageLinkList = (List) LoadHelper
                        .getCacheValue(CURRENT_PART_USAGE_LINK);
                if(partUsageLinkList != null)
                {
                    Iterator iter = partUsageLinkList.iterator();
                    while (iter.hasNext())
                    {
                        PartUsageLinkIfc usageLink = (PartUsageLinkIfc) iter
                                .next();
                        usageLink.setRightBsoID(partIfc.getBsoID());
                        // 在数据库中保存usageLink对象
                        usageLink = (PartUsageLinkIfc) PartServiceRequest
                                .savePartUsageLink(usageLink);
                    }
                }
                List partRefDocLinkList = (List) LoadHelper
                        .getCacheValue(CURRENT_PART_REFERENCE_DOC_LINK);
                if(partRefDocLinkList != null)
                {
                    Iterator iter = partRefDocLinkList.iterator();
                    while (iter.hasNext())
                    {
                        PartReferenceLinkIfc referenceLink = (PartReferenceLinkIfc) iter
                                .next();
                        referenceLink.setRightBsoID(partIfc.getBsoID());
                        // 在数据库中保存usageLink对象
                        referenceLink = (PartReferenceLinkIfc) PartServiceRequest
                                .savePartReferenceLink(referenceLink);
                    }
                }
                // boolean isCheckoutAllowed=((Boolean)
                // LoadHelper.getCacheValue(CHECKOUT_ALLOWED)).booleanValue();
                String s1 = getValue(attributes, "publishFlag", false);
                IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                        .getService("IBAValueService");
                if(s1 != null && s1.equalsIgnoreCase("true"))
                    ibaValueService.publishIBAToPartMaster(partIfc);
                cacheNewPart(partIfc);
                LoadHelper.setCacheValue(CURRENT_CONTENT_HOLDER, partIfc);
                LoadHelper.removeCacheValue(TEMP_PART);
                vector.addElement(partIfc);
            }
            flag = true;
        }
        catch (QMException qmexception)
        {
            qmexception.printStackTrace();
        }
        finally
        {
            if(!flag && loadMode != null && loadMode.compareTo("UPDATE") == 0
                    && isUndoCheckoutAllowed(partIfc))
                try
                {
                    wService.undoCheckout(partIfc);
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
                LoadHelper.removeCacheValue(CURRENT_LOAD_MODE);
            }
            catch (QMException qmexception1)
            {
            }
        }
        return flag;
    }

    /**
     * 清除零部件的使用关系。
     * @param partIfc
     * @throws QMException 
     */
    private static void clearPartUsageLink(QMPartIfc partIfc)
            throws QMException
    {
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        QMQuery query2 = new QMQuery("PartUsageLink");
        QueryCondition condition2 = new QueryCondition("rightBsoID", "=",
                partIfc.getBsoID());
        query2.addCondition(condition2);
        Collection collection2 = persistService.findValueInfo(query2);
        if(collection2 != null)
        {
            Iterator iterator2 = collection2.iterator();
            while (iterator2.hasNext())
            {
                PartUsageLinkIfc partUsageLinkIfc = (PartUsageLinkIfc) iterator2
                        .next();
                PartServiceRequest.deletePartUsageLink(partUsageLinkIfc);
            }
        }
    }

    /**
     * 清除零部件的参考关系。
     * 
     * @param partIfc
     * @throws QMException
     */
    private static void clearPartRefDocLink(QMPartIfc partIfc)
            throws QMException
    {
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        QMQuery query2 = new QMQuery("PartReferenceLink");
        QueryCondition condition2 = new QueryCondition("rightBsoID", "=",
                partIfc.getBsoID());
        query2.addCondition(condition2);
        Collection collection2 = persistService.findValueInfo(query2);
        if(collection2 != null)
        {
            Iterator iterator2 = collection2.iterator();
            while (iterator2.hasNext())
            {
                PartReferenceLinkIfc partRefLinkIfc = (PartReferenceLinkIfc) iterator2
                        .next();
                PartServiceRequest.deletePartReferenceLink(partRefLinkIfc);
            }
        }
    }

    /**
     * 清除零部件的参考关系。
     * 
     * @param partIfc
     * @throws QMException
     */
    private static void clearPartDesDocLink(QMPartIfc partIfc)
            throws QMException
    {
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        QMQuery query2 = new QMQuery("PartDescribeLink");
        QueryCondition condition2 = new QueryCondition("leftBsoID", "=",
                partIfc.getBsoID());
        query2.addCondition(condition2);
        Collection collection2 = persistService.findValueInfo(query2);
        if(collection2 != null)
        {
            Iterator iterator2 = collection2.iterator();
            while (iterator2.hasNext())
            {
                PartDescribeLinkIfc partDesLinkIfc = (PartDescribeLinkIfc) iterator2
                        .next();
                PartServiceRequest.deletePartDescribeLink(partDesLinkIfc);
            }
        }
    }

    private static void cacheLoadPartInfo(Hashtable hashtable)
            throws QMException
    {
        LoadHelper.removeCacheValue("producedByStr");
        LoadHelper.removeCacheValue("partTypeStr");
        LoadHelper.removeCacheValue("location");
        LoadHelper.removeCacheValue("versionDes");
        LoadHelper.removeCacheValue("lifeCycleName");
        LoadHelper.removeCacheValue("projectName");
        LoadHelper.removeCacheValue("viewName");
        //CCBegin SS2
        LoadHelper.removeCacheValue("lifeCycleStateStr");
        //CCEnd SS2
        // 获得零部件的基本属性信息
        // String producedByStr = getValue(hashtable, "producedByStr", true);
        String partTypeStr = getValue(hashtable, "partTypeStr", true);
        String projectName = getValue(hashtable, "projectName", false);
        String lifeCycleName = getValue(hashtable, "lifeCycleName", false);
        String viewName = getValue(hashtable, "viewName", false);
        String location = getValue(hashtable, "location", true);
        String versionDes = getValue(hashtable, "versionDes", false);
        //CCBegin SS2
        String lifeCycleStateStr = getValue(hashtable, "lifeCycleStateStr", false);
        //CCEnd SS2
        if(versionDes == null)
        {
            versionDes = "";
        }
        if(versionDes != null)
        {
            LoadHelper.setCacheValue("versionDes", versionDes);
        }
        // if(producedByStr!=null){
        // LoadHelper.setCacheValue("producedByStr",producedByStr);
        // }
        if(partTypeStr != null)
        {
            LoadHelper.setCacheValue("partTypeStr", partTypeStr);
        }
        if(location != null)
        {
            LoadHelper.setCacheValue("location", location);
        }
        if(lifeCycleName != null)
        {
            LoadHelper.setCacheValue("lifeCycleName", lifeCycleName);
        }
        if(projectName != null)
        {
            LoadHelper.setCacheValue("projectName", projectName);
        }
        if(viewName != null)
        {
            LoadHelper.setCacheValue("viewName", viewName);
        }
        //CCBegin SS2
        if(lifeCycleStateStr != null)
        {
            LoadHelper.setCacheValue("lifeCycleStateStr", lifeCycleStateStr);
        }
        //CCEnd SS2
        //      LoadHelper.setCacheValue("versionDes",versionDes);	
        //		LoadHelper.setCacheValue("producedByStr",partTypeStr);
        //		LoadHelper.setCacheValue("partTypeStr",partTypeStr);
        //		LoadHelper.setCacheValue("location",location);	
        //		LoadHelper.setCacheValue("lifeCycleName",lifeCycleName);
        //		LoadHelper.setCacheValue("projectName",projectName);    
        //		LoadHelper.setCacheValue("viewName",viewName);   
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
        part = (QMPartIfc) folderService.assignFolder((FolderEntryIfc) part,
                location);
        //设置生命周期状态
        LifeCycleState state = LifeCycleState
                .toLifeCycleState(lifeCycleStateStr);
        part = (QMPartIfc) lifeCycleService.setLifeCycleState(
                (LifeCycleManagedIfc) part, state);
        //CCBegin by liunan 2011-08-29 打补丁P036
        part.setLifeCycleState(state);//CR4
        //CCEnd by liunan 2011-08-29

                }
                catch (QMPropertyVetoException exception)
                {
        //            LoadHelper.printMessage("\nsetPartAttributes: "
        //                    + exception.getLocalizedMessage());
                    exception.printStackTrace();
        //            throw new PartException(exception);
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
        String s = part.getPartNumber();
        String s1 = part.getVersionValue();
        LoadHelper.setCacheValue(CURRENT_PART, part);
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
        LoadHelper.setCacheValue("Master Number" + partmaster.getPartNumber(),
                partmaster);
    }

    public static QMPartIfc getPartIfc(String bsoID)
    {
        QMQuery query;
        QMPartIfc partIfc = null;
        try
        {
            query = new QMQuery("QMPart");
            query.setChildQuery(false);
            QueryCondition condition1 = new QueryCondition("bsoID", "=", bsoID);
            query.addCondition(condition1);
            Collection filterMaterialSplitCol = (Collection) request(
                    "PersistService", "findValueInfo",
                    new Class[]{QMQuery.class}, new Object[]{query});
            if(filterMaterialSplitCol.size() > 0)
            {
                partIfc = (QMPartIfc) filterMaterialSplitCol.iterator().next();
            }
        }
        catch (QueryException e)
        {
            e.printStackTrace();
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        return partIfc;
    }

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
                final BaseService baseService = EJBServiceHelper
                        .getService(serviceName);
                final Method method = baseService.getClass().getMethod(
                        methodName, paraClass);
                obj = method.invoke(baseService, para);
            }
            catch (InvocationTargetException exception)
            {
                final Throwable ex = exception.getTargetException();
                if(ex instanceof QMException)
                    throw (QMException) ex;
                else
                    throw new QMException(ex.getMessage());
            }
            catch (Exception ex)
            {
                throw new QMException(ex, ex.getMessage());
            }
            return obj;
        }
    }

    /**
     * 处理.csv文件中的"AddPartUsageLink"命令
     * 用哈西表中的参数为属性构造一个已经存在的零部件的使用关系并保存在数据库中
     * 要求所有涉及的零部件必须是在数据库中已经存在的，否则会发生异常；
     *
     * @param hashtable               部件属性的名/值 对
     *                         属性如下：
     * 子零部件号码          childNumber
     * 使用单位              unitStr
     * 使用数量              quantityStr
     *
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合
     * @return boolean
     */
    public static boolean addPartUsage(Hashtable hashtable,
            Vector return_objects)
    {
        //创建是否成功的标志
        boolean flag = false;
        //父零部件的编号
        //        String parentNumber = "";
        //子件的编号
        String childNumber = "";
        //使用单位
        String unitStr = "";
        //使用数量
        String quantityStr = "";
        try
        {
            childNumber = LoadHelper.getValue("childNumber", hashtable, 1);
            unitStr = LoadHelper.getValue("unitStr", hashtable, 1);
            quantityStr = LoadHelper.getValue("quantityStr", hashtable, 1);
            PartUsageLinkIfc usageLink = new PartUsageLinkInfo();
            //            QMPartIfc exitingPart = (QMPartIfc) LoadHelper
            //                    .getCacheValue(TEMP_PART);
            //            parentNumber = exitingPart.getPartNumber();
            //获得子零部件对应最新版本和版序的零部件的BsoID
            String childMasterBsoID = getPartIDByMasterNumber(childNumber);
            if(childMasterBsoID == null)
            {
                String s1 = "\ncreatePartUsageLink:部件" + childNumber
                        + "在装载文件中不存在或未被成功创建。";
                //                LoadHelper.printMessage(s1);
                throw new QMException(s1);
            }
            if(childMasterBsoID != null)
            {
                Unit unit = Unit.toUnit(unitStr);
                usageLink.setLeftBsoID(childMasterBsoID);
                float quantity = (new Float(quantityStr)).floatValue();
                QMQuantity quantity1 = new QMQuantity();
                //重新设置使用数量。
                quantity1.setQuantity(quantity);
                quantity1.setDefaultUnit(unit);
                usageLink.setQuantity(quantity);
                usageLink.setDefaultUnit(unit);
                usageLink.setQuantity(quantity);
                usageLink.setQMQuantity(quantity1);
                List list = (List) LoadHelper
                        .getCacheValue(CURRENT_PART_USAGE_LINK);
                list.add(usageLink);
                LoadHelper.removeCacheValue(CURRENT_PART_USAGE_LINK);
                LoadHelper.setCacheValue(CURRENT_PART_USAGE_LINK, list);
                flag = true;
            }
        }
        catch (QMException exception)
        {
            //            String message = parentNumber + " - " + childNumber;
            //            LoadHelper.printMessage("创建" + message + "时发生错误");
            exception.printStackTrace();
            //            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception e)
        {
            //            String message = parentNumber + " - " + childNumber;
            //            LoadHelper.printMessage("创建" + message + "时发生错误");
            //            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 处理.csv文件中的"AddRefernceDocLink"命令。
     * 用哈西表中的参数为属性构造一个已经存在的零部件的参考关系并保存在数据库中
     * 要求所有涉及的零部件和文档必须是在数据库中已经存在的，否则会发生异常。
     * @param nv               部件属性的名/值 对
     *                         属性如下：
     * 零部件号码            partNumber
     * 参考文档号码          docNumber
     * @param return_objects   <code>Vector</code> 正确创建对象的返回集合。
     * @return boolean
     */
    public static boolean createPartRefernceDocLink(Hashtable nv,
            Vector return_objects)
    {
        //创建是否成功的标志
        boolean flag = false;
        String docNumber = "";
        try
        {
            docNumber = LoadHelper.getValue("docNumber", nv, 1);
            //获得主文档BsoID
            String docBsoID = getDocIDByMasterNumber(docNumber);
            PartReferenceLinkIfc referenceLink = new PartReferenceLinkInfo();
            if(docBsoID == null)
            {
                throw new QMException("reference object not found " + docNumber);
            }
            List referenceLinkList = (List) LoadHelper
                    .getCacheValue(CURRENT_PART_REFERENCE_DOC_LINK);
            referenceLink.setLeftBsoID(docBsoID);
            referenceLinkList.add(referenceLink);
            LoadHelper.removeCacheValue(CURRENT_PART_REFERENCE_DOC_LINK);
            LoadHelper.setCacheValue(CURRENT_PART_REFERENCE_DOC_LINK,
                    referenceLinkList);
            flag = true;
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
    private static String getDocIDByMasterNumber(String docMasterNumber)
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
     * 根据零部件编号得到零部件所有版本的零部件值对象。
     * @param partNumber
     * @return
     * @throws QMException
     */
    private static Collection getPartIfcColByNumber(String partNumber)
            throws QMException
    {
        Collection collection1 = null;
        QMQuery query = new QMQuery("QMPartMaster");
        QueryCondition condition = new QueryCondition("partNumber", "=",
                partNumber);
        query.addCondition(condition);
        query.setChildQuery(false);
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        Collection collection = persistService.findValueInfo(query);
        Iterator iterator = collection.iterator();
        QMPartMasterIfc master = null;
        if(iterator.hasNext())
        {
            master = (QMPartMasterIfc) iterator.next();
            // 调用版本控制服务
            VersionControlService vcService = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");
            collection1 = vcService.allVersionsOf(master);
        }
        return collection1;
    }

    /**
     * 获取新的属性容器，该属性容器中只有零部件的属性容器中的约束条件。
     * @param partIfc
     * @return
     * @throws QMException 
     */
    private static DefaultAttributeContainer getNewDefaultAttributeContainer(
            QMPartIfc partIfc) throws QMException
    {
        IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                .getService("IBAValueService");
        partIfc = (QMPartIfc) ibaValueService.refreshAttributeContainer(
                partIfc, null, null, null);
        DefaultAttributeContainer attrContainer = (DefaultAttributeContainer) partIfc
                .getAttributeContainer();
        ClassificationStructDefaultView classStructDefaultView = null;
        try
        {
            ClassificationService cService = (ClassificationService) EJBServiceHelper
                    .getService("ClassificationService");
            classStructDefaultView = cService
                    .getClassificationStructDefaultView("com.faw_qm.part.model.QMPartIfc");
        }
        catch (Exception exception1)
        {
            exception1.printStackTrace();
        }
        if(classStructDefaultView != null)
        {
            ReferenceDefView referenceDefView = classStructDefaultView
                    .getReferenceDefView();
            List constraintGroupsList = (List) attrContainer
                    .getConstraintGroups();
            List newConstraintGroupsList = new ArrayList();
            try
            {
                for (int index = 0; index < constraintGroupsList.size(); index++)
                {
                    ConstraintGroup constraintGroup = (ConstraintGroup) constraintGroupsList
                            .get(index);
                    if(constraintGroup != null)
                        if(!constraintGroup.getConstraintGroupLabel().equals(
                                "Sourcing Factor"))
                        {
                            newConstraintGroupsList.add(constraintGroup);
                        }
                        else
                        {
                            Iterator constraintsIter = constraintGroup
                                    .getConstraints();
                            ConstraintGroup newConstraintGroup = new ConstraintGroup();
                            newConstraintGroup
                                    .setConstraintGroupLabel(constraintGroup
                                            .getConstraintGroupLabel());
                            while (constraintsIter.hasNext())
                            {
                                AttributeConstraintIfc attributeconstraint1 = (AttributeConstraintIfc) constraintsIter
                                        .next();
                                if(!attributeconstraint1
                                        .appliesToAttrDef(referenceDefView)
                                        || !(attributeconstraint1
                                                .getValueConstraint() instanceof Immutable))
                                    newConstraintGroup
                                            .addConstraint(attributeconstraint1);
                            }
                            newConstraintGroupsList.add(newConstraintGroup);
                        }
                }
                attrContainer.setConstraintGroups(newConstraintGroupsList);
            }
            catch (QMPropertyVetoException qmpropertyvetoexception)
            {
                qmpropertyvetoexception.printStackTrace();
            }
        }
        return attrContainer;
    }

    /** 约束条件标识。 */
    private static final String SOURCING_FACTOR = "Sourcing Factor";

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(LoadPart.class);

    /** 分隔符。 */
    public static final char CSV_DELIMITER = 44;

    /** 子分隔符。 */
    public static final char CSV_DELIMITER_SUB = 255;

    /** 零部件BSOID。 */
    public static String PART_BSOID = "Part BsoID";

    /** 节点名。 */
    private static String NODE_NAME = "Classification Node Name";

    /** 父节点。 */
    private static String PARENT_NODE = "Parent Classification Node";

    /**
     * 开始导入零部件的分类节点（方法1）。调用方法2。
     * @param hashtable Hashtable 属性表。
     * @param collection Vector 未用。
     * @throws LoadDataException
     * @return boolean 导入成功标识。true为成功，false为失败。
     */
    public static boolean loadPartWithClassification(Hashtable hashtable,
            Vector collection) throws QMException
    {
        return beginLoadPartWithClassification(hashtable);
    }

    /**
     * 开始导入零部件的分类节点（方法2）。
     * @param hashtable Hashtable 属性表。
     * @throws LoadDataException
     * @return boolean 导入成功标识。true为成功，false为失败。
     */
    public static boolean beginLoadPartWithClassification(Hashtable hashtable)
            throws LoadDataException
    {
        Hashtable hashtable1 = new Hashtable();
        // 根据不同的属性名获得属性值。
        String structureClassName = LoadHelper.getValue("structureClassName",
                hashtable, hashtable1, 0);
        // 分类节点的名称
        String nodeName = LoadHelper.getValue("nodeName", hashtable,
                hashtable1, 0);
        // 分类节点的父节点名称
        String parentID = LoadHelper.getValue("parentNodeName", hashtable,
                hashtable1, 1);
        // 分类结构对应的参考事物特性名称
        String refDefName = LoadHelper.getValue("refDefName", hashtable,
                hashtable1, 1);
        // 实例化一个参考定义。
        ReferenceDefView referenceDefView = null;
        if(structureClassName == null)
        {
            System.out
                    .println("Error: The ClassificationStructureName in the CSV file cannot be null!");
            return false;
        }
        if(refDefName != null)
        {
            referenceDefView = ClassificationNodeLoader
                    .getReferenceDefView(refDefName);
            if(referenceDefView == null)
            {
                return false;
            }
        }
        ClassificationStructDefaultView classificationStructDefaultView = ClassificationNodeLoader
                .getClassificationStructDefaultView(structureClassName,
                        referenceDefView);
        if(classificationStructDefaultView == null)
        {
            return false;
        }
        ClassificationNodeDefaultView classificationNodeDefaultView = null;
        if(parentID != null)
        {
            parentID = parentID.replace('\377', ',');
            classificationNodeDefaultView = ClassificationNodeLoader
                    .findParentClassificationNode(parentID,
                            classificationStructDefaultView);
            if(classificationNodeDefaultView == null)
            {
                System.out.println("Cannot find Parent Classification Node");
                return false;
            }
        }
        try
        {
            LoadHelper.removeCacheValue(NODE_NAME);
            LoadHelper.removeCacheValue(PARENT_NODE);
            LoadHelper.removeCacheValue(CLASSIFICATION_STRUCTURE);
            LoadHelper.setCacheValue(CLASSIFICATION_STRUCTURE,
                    classificationStructDefaultView);
            if(nodeName != null)
            {
                nodeName = nodeName.replace('\377', ',');
                LoadHelper.setCacheValue(NODE_NAME, nodeName);
            }
            if(classificationNodeDefaultView != null)
            {
                LoadHelper.setCacheValue(PARENT_NODE,
                        classificationNodeDefaultView);
            }
            LoadHelper.setCacheValue(LOAD_CLASSIFICATION, true);
        }
        catch (QMException qmexception)
        {
            qmexception.printStackTrace();
            System.out.println("Exception Clear/Set Cache!");
            return false;
        }
        //        return LoadValue.beginIBAContainer();
        return true;
    }

    /**
     * 结束导入零部件的分类节点（方法2）。
     * @return boolean true时成功，false时失败。
     */
    public static boolean endLoadPartWithClassification(
            IBAHolderIfc ibaHolderIfc)
    {
        // 导入的零部件的分类节点名称
        String nodeName = null;
        Object cachedObject = null;
        nodeName = ClassificationNodeLoader.getCachedString(NODE_NAME);
        if(nodeName == null)
        {
            System.out
                    .println("ERROR(endLoadPartWithClassification): the Node Name is null!");
            return false;
        }
        try
        {
            boolean load = (Boolean) LoadHelper
                    .getCacheValue(LOAD_CLASSIFICATION);
            if(!load)
            {
                return false;
            }
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        ClassificationNodeDefaultView classificationNodeDefaultView = null;
        cachedObject = getCachedObject(PARENT_NODE);
        if(cachedObject != null)
        {
            if(!(cachedObject instanceof ClassificationNodeDefaultView))
            {
                System.out
                        .println("ERROR(endLoadPartWithClassification): Wrong data type for PARENT CLASSIFICATION NODE!");
                return false;
            }
            classificationNodeDefaultView = (ClassificationNodeDefaultView) cachedObject;
        }
        ClassificationStructDefaultView classificationStructDefView = null;
        //        classificationStructDefView = ClassificationNodeLoader
        //                .getCachedClassificationStructDefaultView();
        classificationStructDefView = (ClassificationStructDefaultView) 
        getCachedObject(CLASSIFICATION_STRUCTURE);
        if(classificationStructDefView == null)
        {
            System.out
                    .println("endLoadPartWithClassification: Classification structure null!");
            return false;
        }
        try
        {
            String classificationBsoID = null;
            if(classificationNodeDefaultView != null)
            {
                classificationBsoID = classificationNodeDefaultView.getBsoID();
            }
            ClassificationNodeIfc ifc = getClassificationNodeIfc(nodeName,
                    classificationBsoID, classificationStructDefView.getBsoID());
            addClassificationOfPart(ibaHolderIfc, ifc,classificationStructDefView);
            LoadHelper.removeCacheValue(NODE_NAME);
            LoadHelper.removeCacheValue(PARENT_NODE);
            LoadHelper.removeCacheValue(CLASSIFICATION_STRUCTURE);
            LoadHelper.setCacheValue(LOAD_CLASSIFICATION, false);
            return true;
        }
        catch (QMException qmexception)
        {
            System.out.println("Exception load part with classification node: "
                    + qmexception);
            qmexception.printStackTrace();
            return false;
        }
    }

    
    /**
     * 为零部件添加分类节点。
     * @param ibaHolderIfc IBAHolderIfc：事物特性拥有者。
     * @param classificationNodeIfc ClassificationNodeIfc：分类节点值对象。
     */
    private static void addClassificationOfPart(IBAHolderIfc ibaHolderIfc,
            ClassificationNodeIfc classificationNodeIfc,ClassificationStructDefaultView classificationStructDefView)
            throws IBAConstraintException, QMException
    {
//        ClassificationStructDefaultView classificationStructDef = (ClassificationStructDefaultView) NavigationUtil
//                .getClassificationStructure(QMPartInfo.class.getName());
        // 分类结构对应的参考型事物特性。
        final ReferenceDefView referenceDefView = classificationStructDefView
                .getReferenceDefView();
        DefaultAttributeContainer defAttrContainer = (DefaultAttributeContainer) ibaHolderIfc
                .getAttributeContainer();
        if(defAttrContainer == null)
        {
            IBAValueService ibaService = (IBAValueService) EJBServiceHelper
                    .getService("IBAValueService");
            ibaHolderIfc = (IBAHolderIfc) ibaService.refreshAttributeContainer(
                    ibaHolderIfc, null, null, null);
            defAttrContainer = (DefaultAttributeContainer) ibaHolderIfc
                    .getAttributeContainer();
        }
        defAttrContainer.setConstraintParameter("CSM");
        // 判断当前容器是否已有分类标识。
        boolean cfAttrflag = false;
        //        cfAttrflag = isHasClassificationAttribute(defAttrContainer,
        //                classificationStructDef);
        ReferenceValueDefaultView referenceValueDV = new ReferenceValueDefaultView(
                referenceDefView);
        ClassificationNodeNodeView classificationNodeNodeView = ClassificationObjectsFactory
                .newClassificationNodeNodeView(classificationNodeIfc);
        ClassificationService classficationService = (ClassificationService) EJBServiceHelper
                .getService("ClassificationService");
        ClassificationNodeDefaultView cnDefaultView = (ClassificationNodeDefaultView) classficationService
                .getClassificationNodeDefaultView(classificationNodeNodeView);
        referenceValueDV.setLiteIBAReferenceable(cnDefaultView);
        referenceValueDV.setState(3);
        // 分类节点的参考属性值。
        boolean flag2 = true;
        // 事物特性集合。
        final List attrDefList = new ArrayList(5);
        // 分类节点的属性的集合。
        final List attrOfNodelist = new ArrayList(5);
        processValueAndConstraint(cnDefaultView, attrDefList, attrOfNodelist);
        // 处理容器和分类节点都有的事物特性。
        removeTogetherAttribute(defAttrContainer, attrDefList, attrOfNodelist);
        List list9 = createNewValue(cfAttrflag, referenceDefView, flag2,
                attrDefList, referenceValueDV, true, cnDefaultView,
                attrOfNodelist, defAttrContainer);
        if(list9.size() > 0)
        {
            removeCSMPersistedConstraint(defAttrContainer);
            // attributeCIfc=
            // removeClassificationAttributeConstraint(defAttrContainer,referenceDefView);
            final AttributeConstraintIfc attributeCIfc = removeClassificationAttributeConstraint(
                    defAttrContainer, classificationStructDefView);
            if(cfAttrflag)
            {
                defAttrContainer
                        .deleteAttributeValues((AttributeDefDefaultView) referenceDefView);
            }
            for (int i = 0; i < list9.size(); i++)
            {
                if(list9.get(i) instanceof AbstractValueView)
                {
                    defAttrContainer.addAttributeValue(
                            (AbstractValueView) list9.get(i), true);
                }
            }
            addAttributeValueConstraints(cnDefaultView, defAttrContainer);
            addClassificationAttributeConstraint(attributeCIfc,
                    defAttrContainer);
        }
    }

    /**
     * 删除属性容器和分类节点共同具有的属性。
     * @param defAttrContainer
     * @param attrDefList
     * @param attrOfNodelist
     */
    private static void removeTogetherAttribute(
            DefaultAttributeContainer defAttrContainer, List attrDefList,
            List attrOfNodelist)
    {
        // 当前容器已含有的事物特性集合。
        final AttributeDefDefaultView aAttributeDefDV2[] = defAttrContainer
                .getAttributeDefinitions();
        final boolean aflag[] = new boolean[aAttributeDefDV2.length];
        for (int j1 = 0; j1 < aflag.length; j1++)
        {
            aflag[j1] = false;
        }
        for (int k1 = 0; k1 < attrOfNodelist.size(); k1++)
        {
            // 分类节点上含有的事物特性。
            final AttributeDefDefaultView attributeDefDV = (AttributeDefDefaultView) attrOfNodelist
                    .get(k1);
            for (int j2 = 0; j2 < aAttributeDefDV2.length; j2++)
            {
                if(attributeDefDV.isPersistedObjectEqual(aAttributeDefDV2[j2])
                        && !aflag[j2])
                {
                    aflag[j2] = true;
                }
            }
        }
        for (int i2 = 0; i2 < aflag.length; i2++)
        {
            // 从分类节点上移除当前容器含有的事物特性。
            if(aflag[i2])
            {
                removeDefFromCollection(attrOfNodelist, aAttributeDefDV2[i2]);
            }
        }
        for (int k2 = 0; k2 < aAttributeDefDV2.length; k2++)
        {
            // 从attrDefList中移除当前容器含有的事物特性。
            removeDefFromCollection(attrDefList, aAttributeDefDV2[k2]);
        }
    }

    /**
     * 获取分类节点值对象。
     * @param classificationNodeName String：分类节点名称。
     * @param parentID String：该分类节点父节点的BsoID。
     * @param classificationStructBsoID String：分类结构的BsoID。
     * @return
     */
    public static ClassificationNodeIfc getClassificationNodeIfc(
            String classificationNodeName, String parentID,
            String classificationStructBsoID)
    {
        ClassificationNodeIfc classificationNodeIfc = null;
        QMQuery query = null;
        try
        {
            query = new QMQuery("ClassificationNode");
            query.addCondition(new QueryCondition("name", "=",
                    classificationNodeName));
            query.addAND();
            if(parentID != null)
            {
                query
                        .addCondition(new QueryCondition("parentID", "=",
                                parentID));
            }
            else
            {
                query.addCondition(new QueryCondition("parentID",
                        QueryCondition.IS_NULL));
                query.addAND();
                query.addCondition(new QueryCondition("classificationStructID",
                        "=", classificationStructBsoID));
            }
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            Collection filterMaterialSplitCol = pService.findValueInfo(query);
            if(filterMaterialSplitCol != null
                    && filterMaterialSplitCol.size() > 0)
            {
                classificationNodeIfc = (ClassificationNodeIfc) filterMaterialSplitCol
                        .iterator().next();
            }
        }
        catch (QueryException e)
        {
            e.printStackTrace();
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        return classificationNodeIfc;
    }

    /**
     * 是否拥有分类属性。
     * @param defAttContainer DefaultAttributeContainer：属性容器。
     * @param clfStructDefView ClassificationStructDefaultView：分类结构
     * @return boolean 拥有标志。
     */
    protected static boolean isHasClassificationAttribute(
            DefaultAttributeContainer defAttContainer,
            ClassificationStructDefaultView clfStructDefView)
    {
        logger.debug("**********start hasClassificationAttribute**********");
        final AttributeDefDefaultView attributeDefDV[] = defAttContainer
                .getAttributeDefinitions();
        // 容器中是否含有分类结构的参考特性。
        boolean flag = false;
        // 容器中是否含有分类结构的参考值。
        boolean flag2 = false;
        if(clfStructDefView == null)
        {
            flag2 = false;
        }
        else
        {
            final ReferenceDefView referenceDefView = clfStructDefView
                    .getReferenceDefView();
            for (int i = 0; !flag && i < attributeDefDV.length; i++)
            {
                if(referenceDefView.isPersistedObjectEqual(attributeDefDV[i]))
                {
                    flag = true;
                    final AbstractValueView abstractValueV[] = defAttContainer
                            .getAttributeValues(attributeDefDV[i]);
                    if(abstractValueV != null)
                    {
                        flag2 = true;
                    }
                }
            }
        }
        logger.debug("**********stop hasClassificationAttribute**********");
        return flag2;
    }

    /**
     * 根据操作的属性值创建新的属性值。
     * @param flag1 boolean 判断当前容器是否已有分类标识。
     * @param referenceDefView ReferenceDefView 分类结构对应的参考型事物特性。
     * @param flag2 boolean 分类节点的参考属性值标志。
     * @param list1 List 事物特性集合。
     * @param referenceValueDV ReferenceValueDefaultView 分类节点的参考属性值。
     * @param flag5 boolean 是否选取了分类节点标志。
     * @param cnDefaultView ClassificationNodeDefaultView 分类节点
     * @param list7 从列表上添加的事物特性集合。 转换后的ClassificationNodeDefaultView类型的分类节点的集合。
     * @param aDefListSelector AttrDefListSelector 选中的分类节点的事物特性的选择器。
     */
    private static List createNewValue(final boolean flag1,
            final ReferenceDefView referenceDefView, final boolean flag2,
            final List list1, final ReferenceValueDefaultView referenceValueDV,
            final boolean flag5,
            final ClassificationNodeDefaultView cnDefaultView,
            final List list7,
            DefaultAttributeContainer defaultattributecontainer)
    {
        // 是否添加分类节点上的属性的标志
        // boolean isAddFlag = true;
        // 处理列表上的事物特性对应的属性值。
        final int count = list7.size();
        // 操作的属性值集合。
        final Collection collection6 = new ArrayList(5);
        // 分类节点的容器。
        final DefaultAttributeContainer defaultAC = (DefaultAttributeContainer) cnDefaultView
                .getAttributeContainer();
        if(count > 0)
        {
            final int index[] = new int[count];
            for (int k3 = 0; k3 < index.length; k3++)
            {
                index[k3] = k3;
            }
            for (int i4 = 0, listSize8 = list7.size(); i4 < listSize8; i4++)
            {
                // 列表上的事物特性对应的容器中的属性值。
                final AbstractValueView aAbstractValueV2[] = defaultAC
                        .getAttributeValues((AttributeDefDefaultView) list7
                                .get(i4));
                for (int i5 = 0; i5 < aAbstractValueV2.length; i5++)
                {
                    // if(isAddFlag)
                    collection6.add(aAbstractValueV2[i5]);
                }
                removeDefFromCollection(list1, (AttributeDefDefaultView) list7
                        .get(i4));
            }
        }
        // 处理约束条件为必选项的事物特性对应的属性值。
        for (int l3 = 0, listSize1 = list1.size(); l3 < listSize1; l3++)
        {
            final AbstractValueView aAbstractValueV1[] = defaultAC
                    .getAttributeValues((AttributeDefDefaultView) list1.get(l3));
            for (int l4 = 0; l4 < aAbstractValueV1.length; l4++)
            {
                collection6.add(aAbstractValueV1[l4]);
            }
        }
        // 根据操作的属性值，删除容器原有属性值和约束条件，添加新属性值和约束条件。
        // 新属性值集合。
        List collection9 = new ArrayList(5);
        if(collection6.size() > 0)
        {
            collection9 = (List) createValues(collection6,
                    defaultattributecontainer);
        }
        if(flag2)
        {
            collection9.add(referenceValueDV);
        }
        return collection9;
    }

    /**
     * 创建值。
     * @param collection Collection 操作集合。
     * @return Collection 值集合。
     */
    protected static Collection createValues(final Collection collection,
            DefaultAttributeContainer defaultAttributeC)
    {
        final List list = (List) collection;
        Map map = new HashMap();
        map = getReferenceValues(map, defaultAttributeC);
        final Collection collection2 = createNewValue(list, map);
        for (int k = 0, listSize = list.size(); k < listSize; k++)
        {
            if(list.get(k) instanceof AbstractValueView)
            {
                final AbstractValueView abstractValueV = (AbstractValueView) list
                        .get(k);
                try
                {
                    if(abstractValueV instanceof AbstractContextualValueDefaultView)
                    {
                        logger
                                .debug("===> valueTemplate is AbstractContextualValueDefaultView");
                        final ReferenceValueDefaultView referenceValueDV = ((AbstractContextualValueDefaultView) abstractValueV)
                                .getReferenceValueDefaultView();
                        if(referenceValueDV != null)
                        {
                            logger.debug("===> dependentObject = "
                                    + referenceValueDV
                                    + " name = "
                                    + referenceValueDV
                                            .getLocalizedDisplayString()
                                    + " OID = " + referenceValueDV.getBsoID());
                            final LiteIBAReferenceable liteIBAR = referenceValueDV
                                    .getLiteIBAReferenceable();
                            ((AbstractContextualValueDefaultView) map
                                    .get(abstractValueV.getBsoID()))
                                    .setReferenceValueDefaultView((ReferenceValueDefaultView) map
                                            .get(liteIBAR
                                                    .getReferencedLiteObject()
                                                    .getBsoID()));
                        }
                    }
                    else
                    {
                        logger
                                .warn("===> valueTemplate is NOT AbstractContextualValueDefaultView");
                    }
                }
                catch (QMPropertyVetoException qmPVException)
                {
                    logger.error(qmPVException);
                    // DialogFactory.showErrorDialog(this,
                    // MessageManager.chop(
                    // qmPVException.getLocalizedMessage()));
                }
            }
        }
        return collection2;
    }

    /**
     * 创建新值。
     * @param list List 值集合。
     * @param map Map 值映射。
     * @return Collection 新值集合。
     */
    private static Collection createNewValue(final List list, final Map map)
    {
        final Collection collection2 = new ArrayList(5);
        try
        {
            for (int j = 0, listSize = list.size(); j < listSize; j++)
            {
                if(list.get(j) instanceof AbstractValueView)
                {
                    final AbstractValueView abstractValueV = (AbstractValueView) list
                            .get(j);
                    final AttributeDefDefaultView attributeDefDV = ((AbstractValueView) list
                            .get(j)).getDefinition();
                    final AbstractValueView abstractValueV2 = (AbstractValueView) NewValueCreator
                            .createNewValueObject(attributeDefDV);
                    if(abstractValueV instanceof ReferenceValueDefaultView)
                    {
                        ((ReferenceValueDefaultView) abstractValueV2)
                                .setLiteIBAReferenceable(((ReferenceValueDefaultView) abstractValueV)
                                        .getLiteIBAReferenceable());
                    }
                    else if(abstractValueV instanceof BooleanValueDefaultView)
                    {
                        ((BooleanValueDefaultView) abstractValueV2)
                                .setValue(((BooleanValueDefaultView) abstractValueV)
                                        .isValue());
                    }
                    else if(abstractValueV instanceof RatioValueDefaultView)
                    {
                        final RatioValueDefaultView rValueDefaultView = (RatioValueDefaultView) abstractValueV;
                        final RatioValueDefaultView ratiovalueDV1 = (RatioValueDefaultView) abstractValueV2;
                        ratiovalueDV1.setValue(rValueDefaultView.getValue());
                        ratiovalueDV1.setDenominator(rValueDefaultView
                                .getDenominator());
                    }
                    else if(abstractValueV instanceof IntegerValueDefaultView)
                    {
                        ((IntegerValueDefaultView) abstractValueV2)
                                .setValue(((IntegerValueDefaultView) abstractValueV)
                                        .getValue());
                    }
                    else if(abstractValueV instanceof FloatValueDefaultView)
                    {
                        final FloatValueDefaultView floatValueDV = (FloatValueDefaultView) abstractValueV;
                        final FloatValueDefaultView floatValueDV1 = (FloatValueDefaultView) abstractValueV2;
                        floatValueDV1.setValue(floatValueDV.getValue());
                        floatValueDV1.setPrecision(floatValueDV.getPrecision());
                    }
                    else if(abstractValueV instanceof URLValueDefaultView)
                    {
                        final URLValueDefaultView urlValueDV = (URLValueDefaultView) abstractValueV;
                        final URLValueDefaultView urlValueDV1 = (URLValueDefaultView) abstractValueV2;
                        urlValueDV1.setValue(urlValueDV.getValue());
                        urlValueDV1.setDescription(urlValueDV.getDescription());
                    }
                    else if(abstractValueV instanceof UnitValueDefaultView)
                    {
                        final UnitValueDefaultView unitValueDV = (UnitValueDefaultView) abstractValueV;
                        final UnitValueDefaultView unitValueDV1 = (UnitValueDefaultView) abstractValueV2;
                        unitValueDV1.setValue(unitValueDV.getValue());
                        unitValueDV1.setPrecision(unitValueDV.getPrecision());
                    }
                    else if(abstractValueV instanceof TimestampValueDefaultView)
                    {
                        ((TimestampValueDefaultView) abstractValueV2)
                                .setValue(((TimestampValueDefaultView) abstractValueV)
                                        .getValue());
                    }
                    else if(abstractValueV instanceof StringValueDefaultView)
                    {
                        ((StringValueDefaultView) abstractValueV2)
                                .setValue(((StringValueDefaultView) abstractValueV)
                                        .getValue());
                    }
                    if(abstractValueV instanceof AbstractContextualValueDefaultView)
                    {
                        logger
                                .debug("===> valueTemplate is AbstractContextualValueDefaultView ");
                        map.put(abstractValueV.getBsoID(), abstractValueV2);
                    }
                    else
                    {
                        logger
                                .debug("===> valueTemplate is NOT AbstractContextualValueDefaultView");
                        final LiteIBAReferenceable liteIBAR = ((ReferenceValueDefaultView) abstractValueV)
                                .getLiteIBAReferenceable();
                        map.put(liteIBAR.getReferencedLiteObject().getBsoID(),
                                abstractValueV2);
                    }
                    collection2.add(abstractValueV2);
                }
            }
        }
        catch (QMPropertyVetoException qmPVException)
        {
            logger.error(qmPVException);
            // DialogFactory.showErrorDialog(this,
            // MessageManager.chop(qmPVException.
            // getLocalizedMessage()));
        }
        return collection2;
    }

    /**
     * 获取参考型值集合。
     * @param map Map 映射。
     * @return Map 值映射
     */
    private static Map getReferenceValues(final Map map,
            DefaultAttributeContainer defaultAttributeC)
    {
        logger.debug("**********start getReferenceValues**********" + map);
        final AbstractValueView abstractValueV[] = defaultAttributeC
                .getAttributeValues();
        for (int i = 0; i < abstractValueV.length; i++)
        {
            if(abstractValueV[i] instanceof ReferenceValueDefaultView)
            {
                final LiteIBAReferenceable liteIBAR = ((ReferenceValueDefaultView) abstractValueV[i])
                        .getLiteIBAReferenceable();
                map.put(liteIBAR.getReferencedLiteObject().getBsoID(),
                        abstractValueV[i]);
                logger.debug("===> add ReferecnceValue = "
                        + abstractValueV[i]
                        + " name = "
                        + ((ReferenceValueDefaultView) abstractValueV[i])
                                .getLocalizedDisplayString()
                        + " OID = "
                        + ((ReferenceValueDefaultView) abstractValueV[i])
                                .getBsoID());
                logger.debug("===> add liteIBAR = " + liteIBAR + " name = "
                        + liteIBAR.getIBAReferenceableDisplayString()
                        + " OID = "
                        + liteIBAR.getReferencedLiteObject().getBsoID());
            }
        }
        logger.debug("**********stop getReferenceValues**********" + map);
        return map;
    }

    /**
     * 处理分类节点的事物特性和约束条件。
     * @param cnDefaultView ClassificationNodeDefaultView 分类节点
     * @param attrConstraintList List 事物特性集合。
     * @param attrOfNodelist List 分类节点上含有的事物特性。
     * @throws CSMClassificationNavigationException
     * @throws QMRemoteException
     */
    private static void processValueAndConstraint(
            ClassificationNodeDefaultView cnDefaultView,
            final List attrConstraintList, List attrOfNodelist)
            throws CSMClassificationNavigationException, QMRemoteException
    {
        // 分类节点含有的容器。
        final DefaultAttributeContainer defAttrCont = (DefaultAttributeContainer) cnDefaultView
                .getAttributeContainer();
        // 分类节点含有的事物特性。
        final AttributeDefDefaultView attrDefDefView[] = defAttrCont
                .getAttributeDefinitions();
        // 将分类节点含有的事物特性循环添加到attrOfNodelist上。
        for (int i1 = 0; i1 < attrDefDefView.length; i1++)
        {
            attrOfNodelist.add(attrDefDefView[i1]);
        }
        // 分类节点含有的约束条件。
        final AbstractCSMAttributeConstraintView abstractCSMACV[] = cnDefaultView
                .getCSMAttributeConstraints();
        // 对约束条件进行处理。
        for (int l1 = 0; l1 < abstractCSMACV.length; l1++)
        {
            final ValueConstraint valueCons = abstractCSMACV[l1]
                    .getValueConstraint();
            // 如果约束条件类型为必选项。
            if(valueCons instanceof ValueRequired)
            {
                // 节点作用范围的约束条件。
                if(abstractCSMACV[l1] instanceof CSMContainerConstraintDefaultView)
                {
                    for (int l2 = 0; l2 < attrDefDefView.length; l2++)
                    {
                        addDefToCollection(attrConstraintList, attrDefDefView[l2]);
                    }
                }
                // 单个属性作用范围的约束条件。
                else if(abstractCSMACV[l1] instanceof CSMSingleDefConstraintDefaultView)
                {
                    final AttributeDefDefaultView attributeDefDV1 = ((CSMSingleDefConstraintDefaultView) abstractCSMACV[l1])
                            .getAttributeDefDefaultView();
                    addDefToCollection(attrConstraintList, attributeDefDV1);
                }
            }
        }
    }

    /**
     * 添加事物特性到容器
     * @param list List 容器。
     * @param attrDefDV AttributeDefDefaultView 事物特性轻量级对象。
     */
    public static void addDefToCollection(final List attrList,
            final AttributeDefDefaultView attrDefDV)
    {
        // 是否拥有该事物特性的标志
        boolean flag = false;
        for (int i = 0, listSize = attrList.size(); !flag && i < listSize; i++)
        {
            final AttributeDefDefaultView attributeDefDV1 = (AttributeDefDefaultView) attrList
                    .get(i);
            if(attributeDefDV1.isPersistedObjectEqual(attrDefDV))
            {
                flag = true;
            }
        }
        if(!flag)
        {
            attrList.add(attrDefDV);
        }
    }

    /**
     * 从容器中移除事物特性。
     * @param collection Collection 容器。
     * @param attributeDefDV AttributeDefDefaultView 事物特性轻量级对象。
     */
    public static void removeDefFromCollection(final Collection collection,
            final AttributeDefDefaultView attributeDefDV)
    {
        final List list = (List) collection;
        boolean flag = false;
        AttributeDefDefaultView attributeDefDV1 = null;
        for (int i = 0, listSize = list.size(); !flag && i < listSize; i++)
        {
            attributeDefDV1 = (AttributeDefDefaultView) list.get(i);
            if(attributeDefDV1.isPersistedObjectEqual(attributeDefDV))
            {
                flag = true;
            }
        }
        if(flag)
        {
            list.remove(attributeDefDV1);
        }
    }

    /**
     * 添加分类属性约束。
     * @param attributeCIfc AttributeConstraintIfc 属性约束值对象。
     */
    protected static void addClassificationAttributeConstraint(
            final AttributeConstraintIfc attributeCIfc,
            DefaultAttributeContainer defaultAttributeC)
    {
        if(attributeCIfc != null)
        {
            final List list = (List) defaultAttributeC.getConstraintGroups();
            final Collection collection = new ArrayList(5);
            for (int i = 0, listSize = list.size(); i < listSize; i++)
            {
                final ConstraintGroup constraintgroup = (ConstraintGroup) list
                        .get(i);
                if(constraintgroup != null)
                {
                    if(constraintgroup.getConstraintGroupLabel().equals(
                            SOURCING_FACTOR))
                    {
                        constraintgroup.addConstraint(attributeCIfc);
                    }
                    collection.add(constraintgroup);
                }
            }
            defaultAttributeC.setConstraintGroups(collection);
        }
    }

    /**
     * 添加属性值约束。
     * @param collection Collection 操作集合。
     */
    protected static void addAttributeValueConstraints(
            final ClassificationNodeDefaultView cfNodeDefView,
            DefaultAttributeContainer defaultAttributeC) throws QMException
    {
        final ClassificationNodeDefaultView cnDefaultView[] = new ClassificationNodeDefaultView[1];
        cnDefaultView[0] = cfNodeDefView;
        AttributeConstraintIfc[] attributeCIfc = null;
        CSMConstraintService csmConService = (CSMConstraintService) EJBServiceHelper
                .getService("CSMConstraintService");
        attributeCIfc = (AttributeConstraintIfc[]) csmConService
                .getCSMConstraints(cnDefaultView);
        final List list = (List) defaultAttributeC.getConstraintGroups();
        final Collection collection2 = new ArrayList(5);
        for (int i = 0, listSize = list.size(); i < listSize; i++)
        {
            final ConstraintGroup constraintgroup = (ConstraintGroup) list
                    .get(i);
            if(constraintgroup != null)
            {
                if(constraintgroup.getConstraintGroupLabel().equals(
                        SOURCING_FACTOR))
                {
                    for (int j = 0; j < attributeCIfc.length; j++)
                    {
                        constraintgroup.addConstraint(attributeCIfc[j]);
                    }
                }
                collection2.add(constraintgroup);
            }
        }
        defaultAttributeC.setConstraintGroups(collection2);
    }

    /**
     * 删除属性容器的分类持久化约束。
     * @param defaultAttributeC DefaultAttributeContainer：欲删除约束的属性容器
     */
    private static void removeCSMPersistedConstraint(
            DefaultAttributeContainer defaultAttributeC)
    {
        final List list = (List) defaultAttributeC.getConstraintGroups();
        final Collection collection = new ArrayList(5);
        final ConstraintGroup constraintgroup1 = new ConstraintGroup();
        Iterator iterator = null;
        try
        {
            for (int i = 0, listSize = list.size(); i < listSize; i++)
            {
                final ConstraintGroup constraintgroup = (ConstraintGroup) list
                        .get(i);
                if(constraintgroup != null)
                {
                    if(constraintgroup.getConstraintGroupLabel().equals(
                            SOURCING_FACTOR))
                    {
                        iterator = constraintgroup.getConstraints();
                        constraintgroup1
                                .setConstraintGroupLabel(constraintgroup
                                        .getConstraintGroupLabel());
                        AttributeConstraintIfc attributeCIfc = null;
                        while (iterator.hasNext())
                        {
                            attributeCIfc = (AttributeConstraintIfc) iterator
                                    .next();
                            if(isPersistedConstraint(attributeCIfc))
                            {
                            }
                            else
                            {
                                constraintgroup1.addConstraint(attributeCIfc);
                            }
                        }
                        collection.add(constraintgroup1);
                    }
                    else
                    {
                        collection.add(constraintgroup);
                    }
                }
            }
            defaultAttributeC.setConstraintGroups(collection);
        }
        catch (QMPropertyVetoException qmPVException)
        {
            logger.error(qmPVException);
        }
    }

    /**
     * 判断是否是持久化约束。
     * @param attributeCIfc AttributeConstraintIfc 属性约束值对象。
     * @return boolean 持久化标志。
     */
    protected static boolean isPersistedConstraint(
            final AttributeConstraintIfc attributeCIfc)
    {
        return PersistHelper.isPersistent(attributeCIfc);
    }

    /**
     * 移除分类属性约束。(添加分类 和 删除分类 时都调用了此方法)
     * @param defaultAttributeC DefaultAttributeContainer
     * @param structure ClassificationStructDefaultView
     * @return AttributeConstraintIfc 属性约束值对象。
     */
    private static AttributeConstraintIfc removeClassificationAttributeConstraint(
            DefaultAttributeContainer defaultAttributeC,
            ClassificationStructDefaultView structure)
            throws QMPropertyVetoException
    {
        final ReferenceDefView referencedefview = structure
                .getReferenceDefView();
        final List list = (List) defaultAttributeC.getConstraintGroups();
        final Collection collection = new ArrayList(5);
        final ConstraintGroup constraintgroup1 = new ConstraintGroup();
        AttributeConstraintIfc attributeCIfc = null;
        for (int i = 0, listSize = list.size(); i < listSize; i++)
        {
            final ConstraintGroup constraintgroup = (ConstraintGroup) list
                    .get(i);
            if(constraintgroup != null)
            {
                if(constraintgroup.getConstraintGroupLabel().equals(
                        SOURCING_FACTOR))
                {
                    final Iterator iterator = constraintgroup.getConstraints();
                    constraintgroup1.setConstraintGroupLabel(constraintgroup
                            .getConstraintGroupLabel());
                    // ///////////////////////////////
                    // 把single约束放在vector的最后，从而确保此方法的返回值attributeCIfc 为
                    // single约束，而不是容器约束，
                    // 当添加分类时，在后面的执行过程中会把attributeCIfc设置到属性容器中。
                    // (添加分类时，必须添加一个single约束)
                    Vector vector = new Vector();
                    Vector vec = new Vector();
                    while (iterator.hasNext())
                    {
                        Object obj = iterator.next();
                        if(obj instanceof CSMSingleDefConstraintIfc)
                        {
                            vec.add(obj);
                        }
                        else
                        {
                            vector.add(obj);
                        }
                    }
                    for (int j = 0; j < vec.size(); j++)
                    {
                        Object obj = vec.get(j);
                        vector.add(obj);
                    }
                    //
                    // ////////////////////////////////
                    // while (iterator.hasNext())
                    for (int k = 0; k < vector.size(); k++)
                    {
                        final AttributeConstraintIfc attributeCIfc1 = (AttributeConstraintIfc) vector
                                .get(k);
                        // iterator.next();
                        if(attributeCIfc1.appliesToAttrDef(referencedefview)
                                && (attributeCIfc1.getValueConstraint() instanceof Immutable))
                        {
                            attributeCIfc = attributeCIfc1;
                        }
                        else
                        {
                            constraintgroup1.addConstraint(attributeCIfc1);
                        }
                    }
                    collection.add(constraintgroup1);
                }
                else
                {
                    collection.add(constraintgroup);
                }
            }
        }
        defaultAttributeC.setConstraintGroups(collection);
        return attributeCIfc;
    }

    /**
     * 获得缓存对象。
     * @param s String 属性名。
     * @return Object 缓存的对象。
     */
    public static Object getCachedObject(String s)
    {
        Object obj = null;
        try
        {
            obj = LoadHelper.getCacheValue(s);
        }
        catch (QMException qmexception)
        {
            qmexception.printStackTrace();
            System.out.println("Exception getting cache value: " + qmexception);
        }
        return obj;
    }
}
