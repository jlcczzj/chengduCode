/** 生成程序 PartHelper.java    1.0    2003/02/24
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/06/19 谢斌 原因：基本属性界面“状态”处没有对齐。TD-2233
 *                     方案：由于生命周期和其状态使用的是一个面板，界面元素变化时无法做出统一响应，现改为状态为单独面板，即可统一响应界面变化。
 * CR2 2009/10/23  马辉  原因：操作EJB的地方改为操作值对象  
 * CR3 2009/10/27  马辉 原因:统一客户端或工具类调用服务的方式   
 * CR4 2010/02/09  王彪 原因：TD2142，去掉提示信息。  
 * CR5 2010/04/01  王彪 原因：TD3179更新某些文档，在描述页面添加零部件无法添加，有异常页面  
 */
package com.faw_qm.part.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.config.util.ConfigSpec;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.enterprise.client.vc.util.CheckInOutHelper;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipUtil;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.model.PartMasterItem;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsageMasterItem;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.ejb.entity.Part;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.users.model.ActorIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.QMCt;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressUtil;
import com.faw_qm.wip.util.WorkingPair;


/**
 * 客户端零部件封装类的辅助类。
 * @author 吴先超
 * @version 1.0
 */
public class PartHelper
{
    /**绑定的资源信息*/
    private static ResourceBundle resource = null;
    static String resource1 = "com.faw_qm.part.util.PartResource";

    /**资源信息路径*/
    private static String RESOURCE = "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**
     * 构造函数。
     */
    public PartHelper()
    {}

    /**
     * 初始化资源信息。
     * @throws QMException 
     */
    protected void initResources() throws QMException
    {
        try
        {
            if(null == resource)
            {
                resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext().getLocale());
            }
        }catch(MissingResourceException ex)
        {
            throw new QMException(ex);
        }
    }

    /**
     * 获取资源信息。
     * @return ResourceBundle 绑定的资源信息。
     * @throws QMException 
     */
    protected ResourceBundle getResource() throws QMException
    {
        if(null == resource)
        {
            initResources();
        }
        return resource;
    }

    /**
     * 根据零部件的标识bsoID查询数据库，获取零部件值对象。
     * @param s :String 零部件BsoID。
     * @throws QMException 
     * @
     * @return QMPartIfc 根据标识bsoID得到的零部件值对象。
     */
    public static QMPartIfc getPartForID(String s) throws QMException 
    {
        Class[] paraClass = {String.class};
        Object[] objs = {s};
        QMPartIfc qmPartIfc = null;
            qmPartIfc = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", paraClass, objs);
        //        QMPartIfc qmPartIfc = (QMPartIfc) IBAUtility.invokeServiceMethod(
        //                "PersistService", "refreshInfo", paraClass, objs);
        return qmPartIfc;
    }

    /**
     * 根据持久化信息刷新数据库。
     * 返回更新完后的值对象。
     * @param baseValueIfc BaseValueIfc 进行更新的值对象。
     * @return Object 更新后的值对象。
     * @throws QMException 
     * @
     */
    public static Object refresh(BaseValueIfc baseValueIfc) throws QMException 
    {
        Class[] paraClass = {BaseValueIfc.class};
        Object[] objs = {baseValueIfc};
        QMPartIfc qmPartIfc = null;
//        try
//        {
            qmPartIfc = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", paraClass, objs);
//        }catch(QMException e)
//        {
//            e.printStackTrace();
//        }
        //        QMPartIfc qmPartIfc = (QMPartIfc) IBAUtility.invokeServiceMethod(
        //                "PersistService", "refreshInfo", paraClass, objs);
        return qmPartIfc;
    }

    /**
     * 根据输入的零部件值对象参数，对数据库进行更新，返回更新后的零部件值对象。
     * @param qmPartIfc QMPartIfc 输入的零部件值对象参数。
     * @return QMPartIfc 更新后的零部件值对象。
     * @throws QMException 
     * @
     */
    public static QMPartIfc refresh(QMPartIfc qmPartIfc) throws QMException 
    {
        //根据值对象信息更新数据库中的纪录。
        Class[] paraClass = {BaseValueIfc.class};
        Object[] objs = {qmPartIfc};
//        try
//        {
            qmPartIfc = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", paraClass, objs);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
        //        qmPartIfc = (QMPartIfc) IBAUtility.invokeServiceMethod("PersistService",
        //                "refreshInfo", paraClass, objs);
        return qmPartIfc;
    }

    /**
     * 获取零部件配置规范。
     * @throws QMException 
     * @
     * @return PartConfigSpecIfc 当前的零部件配置规范。
     */
    public static PartConfigSpecIfc getConfigSpec() throws QMException 
    {
        PartConfigSpecIfc partConfigSpecIfc = null;
            partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
        return partConfigSpecIfc;
    }

    /**
     * 设置零部件的配置规范。
     * 调用savePartConfigSpec(PartConfigSpecIfc configSpecIfc)方法。
     * @param partConfigSpecIfc PartConfigSpecIfc 设置的零部件配置规范。
     * @throws QMException 
     * @
     * @return PartConfigSpecIfc 设置好的零部件配置规范。
     */
    public static PartConfigSpecIfc setConfigSpec(PartConfigSpecIfc partConfigSpecIfc) throws QMException 
    {
            partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.savePartConfigSpecIfc(partConfigSpecIfc);
        return partConfigSpecIfc;
    }

    /**
     * 获取零部件的状态集合。
     * @
     * @return LifeCycleState[] 状态集合。
     */
    public static LifeCycleState[] getStates() 
    {
        LifeCycleState astate[] = LifeCycleState.getLifeCycleStateSet();
        return astate;
    }

    /**
     * 检查用户权限，获取所有可见的视图。
     * @throws QMException 
     * @
     * @return ViewObjectIfc[] 视图值对象集合。
     */
    public static ViewObjectIfc[] getViews() throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getViews() begin ....");

        //需要调用ViewService中的方法，获取所有视图。
        //参数类型的集合。
        Class[] paraClass = {};

        //参数值的集合。
        Object[] objs = {};
        Vector tempView = new Vector();
            tempView = (Vector)RequestHelper.request("ViewService", "getAllViewInfos", paraClass, objs);
            //            tempView = (Vector) IBAUtility.invokeServiceMethod("ViewService",
            //                    "getAllViewInfos", paraClass, objs);
        if((null == tempView) || (tempView.size() == 0))
        {
            PartDebug.trace(PartDebug.PART_CLIENT, "getViews() end" + "....return is null");
            return null;
        }
        ViewObjectIfc[] aview = new ViewObjectIfc[tempView.size()];
        for(int i = 0, j = tempView.size();i < j;i++)
        {
            aview[i] = (ViewObjectIfc)tempView.elementAt(i);
        }

        PartDebug.trace(PartDebug.PART_CLIENT, "getViews() end" + "....return is ViewObjectIfc[]");
        return aview;
    }

    /**
     * 获取零部件和参考文档关联关系的集合。
     * @param partIfc QMPartIfc 目标零部件值对象。
     * @return PartReferenceLinkIfc[] 关联的值对象集合。
     * @throws QMException 
     * @
     */
    public static PartReferenceLinkIfc[] getReferences(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getReferences() begin ....");
        PartReferenceLinkIfc partreferencelinkifc[] = null;

        //如果传进来的参数已经是持久化的了，才继续下面的操作：
        if(PersistHelper.isPersistent(partIfc))
        {
            //调用StructService中的方法查找参考关系,navigateReferences
            //(IteratedIfc iteratedIfc) :Collection，
            //参数类型的集合。
            Class[] paraClass = {IteratedIfc.class, Boolean.TYPE};

            //参数值的集合。
            Object[] objs = {partIfc, new Boolean(false)};
                Collection tempCollection = (Collection)RequestHelper.request("StructService", "navigateReferences", paraClass, objs);
                partreferencelinkifc = new PartReferenceLinkIfc[tempCollection.size()];
                Iterator iterator = tempCollection.iterator();
                for(int i = 0, j = tempCollection.size();i < j;i++)
                {
                    partreferencelinkifc[i] = (PartReferenceLinkIfc)(iterator.next());
                }
            PartDebug.trace(PartDebug.PART_CLIENT, "getReferences()" + " end....return is PartReferenceLinkIfc[] ");
            return partreferencelinkifc;
        } 
        else
        {
            partreferencelinkifc = new PartReferenceLinkIfc[0];
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "getReferences()" + " end....return is PartReferenceLinkIfc[] ");
        return partreferencelinkifc;
    }

    /**
     * 获取本零部件被哪些部件所使用的"使用关系"的集合。
     * @param partIfc :QMPartIfc 零部件值对象。
     * @return PartUsageLinkIfc[] 使用关系的集合。
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc[] getUsedBy(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsedBy() begin ....");
        PartUsageLinkIfc partusagelinkifc[] = null;
        QMPartMasterIfc partmasterifc = (QMPartMasterIfc)partIfc.getMaster();

        //调用StructService中的navigateUsedBy方法，获取使用关系的集合。
        //navigateUsedBy(QMPartMasterIfc, false):Collection，
        //参数类型的集合。
        Class[] paraClass = {MasterIfc.class, Boolean.TYPE};

        //参数值的集合。
        Object[] objs = {partmasterifc, new Boolean(false)};
            Collection tempCollection = (Collection)RequestHelper.request("StructService", "navigateUsedBy", paraClass, objs);
            //            Collection tempCollection = (Collection) IBAUtility.
            //            invokeServiceMethod("StructService",
            //"navigateUsedBy", paraClass, objs);
            partusagelinkifc = new PartUsageLinkIfc[tempCollection.size()];

            //现在需要把tempCollection转化为PartUsageLinkIfc[]类型。
            Iterator iterator = tempCollection.iterator();
            for(int i = 0, j = tempCollection.size();i < j;i++)
            {
                partusagelinkifc[i] = (PartUsageLinkIfc)iterator.next();
            }
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsedBy() " + "end....return is PartUsageLinkIfc[]");
        return partusagelinkifc;
    }

    /**
     * 创建新的零部件配置规范。
     * @param flag :boolean
     * @param state :LifeCycleState 生命周期状态。
     * @param viewObjectIfc :ViewObjectIfc 视图值对象。
     * @return PartConfigSpecInfo 零部件配置规范值对象。
     * @
     */
    public static PartConfigSpecInfo newConfigSpec(boolean flag, LifeCycleState state, ViewObjectIfc viewObjectIfc) 
    {
        return new PartConfigSpecInfo();
    }

    /**
     * 根据配置规范查询当前零部件使用了哪些零部件。
     * @param partIfc :QMPartIfc 零部件值对象。
     * @param configSpecIfc :PartConfigSpecIfc 零部件配置规范值对象。
     * @throws QMException 
     * @
     * @return Explorable[] 使用的零部件值对象集合。
     */
    public static Explorable[] getUses(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getUses begin ....");
        Explorable aexplorable[] = null;

        //参数类型的集合。
        Class[] paraClass = {IteratedIfc.class, Boolean.TYPE, ConfigSpec.class};

        //参数值的集合。
        Object[] objs = {partIfc, new Boolean(false), new PartConfigSpecAssistant(configSpecIfc)};
        Collection tempCollection = null;
            //            tempCollection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "StructService", "navigateUsesToIteration", paraClass, objs);
            tempCollection = (Collection)RequestHelper.request("StructService", "navigateUsesToIteration", paraClass, objs);
            aexplorable = new Explorable[tempCollection.size()];
        Object[] tempResult = new Object[aexplorable.length];
        tempResult = (Object[])tempCollection.toArray(tempResult);
        for(int i = 0, j = aexplorable.length;i < j;i++)
        {
            Object obj1 = tempResult[i];
            if(obj1 instanceof QMPartMasterIfc)
            {
                aexplorable[i] = new PartMasterItem((QMPartMasterIfc)obj1);
            }else if(obj1 instanceof QMPartIfc)
            {
                aexplorable[i] = new PartItem((QMPartIfc)obj1);
                ((PartItem)aexplorable[i]).setConfigSpecItem(new ConfigSpecItem(configSpecIfc));
            }else if(obj1 instanceof Object[])
            {
                Object[] tempArray = (Object[])obj1;
                PartUsageLinkIfc partusagelinkifc = (PartUsageLinkIfc)tempArray[0];
                Part part = (Part)tempArray[1];
                if(part instanceof QMPartIfc)
                {
                    PartItem partitem = new PartItem((QMPartIfc)part);
                    PartItem partitem1 = new PartItem(partIfc);
                    ConfigSpecItem specItem = new ConfigSpecItem(configSpecIfc);
                    partitem.setConfigSpecItem(specItem);
                    partitem1.setConfigSpecItem(specItem);
                    aexplorable[i] = new UsageItem(partitem, partitem1, partusagelinkifc);
                }else if(part instanceof QMPartMasterIfc)
                {
                    aexplorable[i] = new UsageMasterItem(new PartMasterItem((QMPartMasterIfc)part), partusagelinkifc);
                }
            }
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "getUses() end....return is Explorable[] ");
        return aexplorable;
    }

    /**
     * 获取指定零部件的PartUsageLinkIfc的集合。
     * @param partIfc :QMPartIfc 指定的零部件。
     * @return PartUsageLinkIfc[] 零部件使用关系的集合。
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc[] getUsesInterface(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsesInterface() begin ....");
        PartUsageLinkIfc partusagelinkifc[] = null;

        //调用了StructService中的navigateUses(IteratedIfc, boolean)方法。
        //参数类型的集合。
        Class[] paraClass = {IteratedIfc.class, Boolean.TYPE};

        //参数值的集合。
        Object[] objs = {partIfc, new Boolean(false)};
            //            Collection tempCollection = (Collection) IBAUtility.
            //                                        invokeServiceMethod("StructService",
            //                    "navigateUses", paraClass, objs);
            Collection tempCollection = (Collection)RequestHelper.request("StructService", "navigateUses", paraClass, objs);
            partusagelinkifc = new PartUsageLinkIfc[tempCollection.size()];
            Iterator iterator = tempCollection.iterator();
            for(int i = 0, j = tempCollection.size();i < j;i++)
            {
                partusagelinkifc[i] = (PartUsageLinkIfc)iterator.next();
            }
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsesInterface() " + "end....return is PartUsageLinkIfc[]");
        return partusagelinkifc;
    }

    /**
     * 保存零部件。
     * @param partIfc :QMPartIfc 零部件值对象。
     * @exception QMRemoteException
     * @return QMPartIfc 零部件值对象。
     * @throws QMException 
     * @
     */
    public static QMPartIfc savePart(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "savePart() begin ....");
        Class[] paraClass = {BaseValueIfc.class};
        Object[] objs = {partIfc};
        
            //            partIfc = (QMPartIfc) IBAUtility.invokeServiceMethod(
            //                    "PersistService", "saveValueInfo", paraClass, objs);
            partIfc = (QMPartIfc)RequestHelper.request("PersistService", "saveValueInfo", paraClass, objs);
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "savePart() " + "end....return is QMPartIfc");
        return partIfc;
    }

    /**
     * 删除零部件。
     * @param partIfc :QMPartIfc 被删除的零部件。
     * @throws QMException 
     * @exception QMRemoteException
     */
    public static void deletePart(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePart() begin ....");

        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partIfc};
        
            RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);
            //            IBAUtility.invokeServiceMethod("PersistService", "deleteValueInfo",
            //                    paraClass, objs);
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePart() end....return is void ");
    }

    /**
     * 修订零部件。
     * @param partIfc :QMPartIfc 被修订的零部件。
     * @exception QMRemoteException
     * @return QMPartIfc 修订后的零部件值对象。
     * @throws QMException 
     */
    public static QMPartIfc revisePart(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "revisePart() begin ....");

        //调用VersionControlService的newVersion(VersionedIfc):VersionedIfc，
        //参数类型的集合。
        Class[] paraClass = {VersionedIfc.class};

        //参数值的集合。
        Object[] objs = {partIfc};
        
            QMPartIfc partIfc1 = (QMPartIfc)RequestHelper.request("VersionControlService", "newVersion", paraClass, objs);
            //            QMPartIfc partIfc1 = (QMPartIfc) IBAUtility.invokeServiceMethod(
            //                    "VersionControlService", "newVersion", paraClass, objs);
            partIfc = partIfc1;
        
        //        catch (QMRemoteException e)
        //        {
        //            e.printStackTrace();
        //            throw e;
        //        } //end try-catch
        
        PartDebug.trace(PartDebug.PART_CLIENT, "revisePart() " + "end....return is QMPartIfc");
        return partIfc;
    }

    /**
     * 删除指定的零部件和参考文档的关联关系。
     * @param partreferencelinkifc :PartReferenceLinkIfc 要删除的零部件和参考文档
     * 的关联值关系对象。
     * @throws QMException 
     * @
     */
    public static void deletePartReferenceLink(PartReferenceLinkIfc partreferencelinkifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePartReferenceLink() begin ....");

        //调用PersistService中的deleteValueInfo(BaseValueIfc):BaseValueIfc方法。
        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partreferencelinkifc};
        
            //            IBAUtility.invokeServiceMethod("PersistService", "deleteValueInfo",
            //                                           paraClass, objs);
            RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "delete" + "PartReferenceLink() end....return is void");
    }

    /**
     * 保存零部件和参考文档的关联关系。
     * @param partReferenceLinkIfc :PartReferenceLinkIfc 零部件和参考文档的关联关系。
     * @return PartReferenceLinkIfc 零部件和参考文档的关联关系值对象。
     * @throws QMException 
     * @
     */
    public static PartReferenceLinkIfc savePartReferenceLink(PartReferenceLinkIfc partReferenceLinkIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "savePartReferenceLink() begin ....");

        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partReferenceLinkIfc};
        
            PartReferenceLinkIfc partReferenceLinkIfc1 = (PartReferenceLinkIfc)
            //                    IBAUtility.invokeServiceMethod("PersistService",
            //                    "saveValueInfo", paraClass, objs);
            RequestHelper.request("PersistService", "saveValueInfo", paraClass, objs);
            partReferenceLinkIfc = partReferenceLinkIfc1;
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "savePartReferenceLink() end....return is PartReferenceLinkIfc ");
        return partReferenceLinkIfc;
    }

    /**
     * 保存零部件间的使用关系。
     * @param partusagelinkifc :PartUsageLinkIfc 零部件使用关系值对象。
     * @return PartUsageLinkIfc 零部件使用关系值对象。
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc saveUsageLink(PartUsageLinkIfc partusagelinkifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "---------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, "输入的参数为：：partusagelinkifc是:::");
        PartDebug.trace(PartDebug.PART_CLIENT, "   " + partusagelinkifc.getLeftBsoID() + "   " + partusagelinkifc.getRightBsoID() + "  quantity: " + partusagelinkifc.getQuantity() + "   Unit:"
                + partusagelinkifc.getDefaultUnit());
        PartDebug.trace(PartDebug.PART_CLIENT, "--------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, "saveUsageLink() begin ....");

        //需要调用PersistService中的saveValueInfo(BaseValueIfc):BaseValueIfc，
        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partusagelinkifc};
        
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)RequestHelper.request("PersistService", "saveValueInfo", paraClass, objs);
            //            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc) IBAUtility.
            //            invokeServiceMethod(
            //"PersistService", "saveValueInfo", paraClass, objs);
            partusagelinkifc = partUsageLinkIfc1;
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
       
        PartDebug.trace(PartDebug.PART_CLIENT, "saveUsageLink()" + "end....return is PartUsageLinkIfc");
        PartDebug.trace(PartDebug.PART_CLIENT, "-----------------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, "保存后的是：partusagelinkifc是:::");
        PartDebug.trace(PartDebug.PART_CLIENT, "   " + partusagelinkifc.getLeftBsoID() + "   " + partusagelinkifc.getRightBsoID() + "  quantity: " + partusagelinkifc.getQuantity() + "   Unit:"
                + partusagelinkifc.getDefaultUnit());
        PartDebug.trace(PartDebug.PART_CLIENT, "-----------------------------------------------------------------");
        return partusagelinkifc;
    }

    /**
     * 删除零部件间的使用关系。
     * @param partusagelinkifc :PartUsageLinkIfc 待保存的零部件的使用关系。
     * @throws QMException 
     * @
     */
    public static void deleteUsageLink(PartUsageLinkIfc partusagelinkifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deleteUsageLink() begin ....");

        //调用PersistService中的deleteValueInfo(BaseValueIfc):BaseValueIfc方法，
        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partusagelinkifc};
        
            //            IBAUtility.invokeServiceMethod("PersistService", "deleteValueInfo",
            //                                           paraClass, objs);
            RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "deleteUsageLink()" + " end....return is void ");
    }

    /**
     * 保存指定的零部件间的使用关系。
     * @param partUsageLinkIfc :PartUsageLinkIfc 指定的零部件间的使用关系。
     * @return PartUsageLinkIfc  指定的零部件间保存后的使用关系。
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc partUsageLinkIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "savePartUsageLink() begin ....");

        //调用PersistService中的saveValueInfo(BaseValueIfc):BaseValueIfc方法。
        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partUsageLinkIfc};
        
            //            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)
            //                                                 IBAUtility.invokeServiceMethod(
            //                    "PersistService", "saveValueInfo", paraClass, objs);
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)RequestHelper.request("PersistService", "saveValueInfo", paraClass, objs);
            partUsageLinkIfc = partUsageLinkIfc1;
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "savePartUsageLink()" + " end....return is PartUsageLinkIfc");
        return partUsageLinkIfc;
    }

    /**
     * 删除指定的零部件间的使用关系。
     * @param partUsageLinkIfc :PartUsageLinkIfc 指定的零部件间的使用关系。
     * @throws QMException 
     * @
     */
    public static void deletePartUsageLink(PartUsageLinkIfc partUsageLinkIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePartUsageLink() begin ....");

        //调用PersistService中的deleteValueInfo(BaseValueIfc):BaseValueIfc方法。
        //参数类型的集合。
        Class[] paraClass = {BaseValueIfc.class};

        //参数值的集合。
        Object[] objs = {partUsageLinkIfc};
        
            //            IBAUtility.invokeServiceMethod("PersistService", "deleteValueInfo",
            //                                           paraClass, objs);
            RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePartUsageLink()" + " end....return is void");
    }

    /**
     * 获取指定零部件的生命周期名称。
     * @param partIfc :QMPartIfc 指定的零部件值对象。
     * @throws QMException 
     * @
     * @return String 该零部件的生命周期名称。
     */
    public static String getLifeCycle(QMPartIfc partIfc) throws QMException 
    {
        String lifeCycleTemplate = partIfc.getLifeCycleTemplate();
        if(null == lifeCycleTemplate)
        {
            return null;
        }else
        {
            Class[] paraClass = {String.class};
            Object[] obj = {lifeCycleTemplate};
            LifeCycleTemplateIfc templateIfc = null;
            
                templateIfc = (LifeCycleTemplateIfc)
                //                                               IBAUtility.invokeServiceMethod(
                //                    "PersistService", "refreshInfo", paraClass, obj);
                RequestHelper.request("PersistService", "refreshInfo", paraClass, obj);
            
            return templateIfc.getLifeCycleName();
        }
    }

    /**
     * 获取零部件的项目组。
     * @param partIfc QMPartIfc 指定的零部件值对象。
     * @return String 该零部件的项目组名称。
     * @
     */
    public static String getProject(QMPartIfc partIfc) 
    {
        return partIfc.getProjectName();
    }

    /**
     * 获取指定的零部件的生命周期状态。
     * @param partIfc :QMPartIfc 指定的零部件值对象。
     * @
     * @return String 该零部件的生命周期状态。
     */
    public static String getLifeCycleState(QMPartIfc partIfc) 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getLifeCycleState() begin ....");
        String s = "";
        LifeCycleState state = partIfc.getLifeCycleState();
        if(null != state)
        {
            s = state.getDisplay();//CR1
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "getLifeCycleState()" + " end....return is String ");
        return s;
    }

    /**
     * 根据零部件主信息和配置规范，获取所有版本的零部件值对象。
     * 返回值Vector是Object[]的集合，即Vector中的每个元素都是Object[]形式的
     * 这样的每个Object[]对应了每个输入的QMPartMasterIfc[]的每个QMPartMasterIfc元素所对应的
     * 所有符合配置规范的零部件值对象。
     * @param partmasterIfc :QMPartMasterIfc[] 零部件主信息值对象的集合。
     * @param partconfigspecifc :PartConfigSpecIfc 零部件配置规范值对象。
     * @return Vector 指定零部件主信息的所有版本的零部件值对象集合。
     * @throws QMException 
     * @
     */
    public static Vector getAllVersions(QMPartMasterIfc partmasterIfc[], PartConfigSpecIfc partconfigspecifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersions() begin ....");
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, " 输入的参数为：  ");
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            PartDebug.trace(PartDebug.PART_CLIENT, "     " + partmasterIfc[i]);
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "输入的零部件配置规范为：");
        PartEffectivityConfigSpec effConfigSpec = partconfigspecifc.getEffectivity();
        if(null != effConfigSpec)
        {
            ViewObjectIfc viewObjectIfc = effConfigSpec.getViewObjectIfc();
            if(null != viewObjectIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "   视图名称：   " + viewObjectIfc.getViewName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "有效性配置规范的视图为空！");
            }
            QMConfigurationItemIfc effItemIfc = effConfigSpec.getEffectiveConfigItemIfc();
            if(null != effItemIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "配置规范的有效性配置项：" + effItemIfc.getConfigItemName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "配置规范的有效性配置项为空！");
            }
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");
        Vector result = new Vector();
        Vector tempResult = new Vector();
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            tempResult.add(partmasterIfc[i]);
        }
        //过滤符合配置规范的零部件小版本。
        //调用ConfigService中的filteredIterationsOf(Collection, ConfigSpec):Collection，
        //由原来的调用方法ConfigService.filteredIterationsOf修改为：filterIterationsOf()，
        //参数类型的集合。
        Class[] paraClass = {Collection.class, ConfigSpec.class};

        //参数值的集合
        Object[] objs = {(Collection)tempResult, (ConfigSpec)(new PartConfigSpecAssistant(partconfigspecifc))};
            //            Collection collection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "ConfigService", "filteredIterationsOf", paraClass, objs);
            Collection collection = (Collection)RequestHelper.request("ConfigService", "filteredIterationsOf", paraClass, objs);
            if((null == collection) || (collection.size() == 0))
            {
                //抛出异常，或者是直接返回空。
                return new Vector();
            }
            //需要把collection中的所有元素都放到result中来。
            if(collection.size() > 0)
            {
                Iterator iterator = collection.iterator();
                while(iterator.hasNext())
                {
                    result.addElement(iterator.next());
                }
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersionsOf()" + " end....return is null");
                return null;
            }
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersionsOf()" + " end....return is Vector");
        PartDebug.trace(PartDebug.PART_CLIENT, " 返回值为：：：  ");
        PartDebug.trace(PartDebug.PART_CLIENT, "-----------------------------------------------");
        for(int i = 0, ii = result.size();i < ii;i++)
        {
            Object[] obj = (Object[])result.elementAt(i);
            for(int j = 0, jj = obj.length;j < jj;j++)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "   " + obj[j]);
            }
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "-----------------------------------------------");
        return result;
    }

    /**
     * 根据零部件主信息获取该零部件的所有符合配置规范的版本。
     * 返回值Vector是含有一个元素(该元素为Object[]类型)的数组，
     * 这个Object[]中的所有元素都是QMPartIfc类型的，
     * 它们对应同一个QMPartMasterIfc，即是输入的参数。
     * @param partmasterIfc :QMPartMasterIfc 零部件主信息值对象。
     * @param partconfigspecIfc :PartConfigSpecIfc 零部件配置规范。
     * @return Vector 符合要求的版本的零部件的集合。
     * @throws QMException 
     * @
     */
    public static Vector getAllVersions(QMPartMasterIfc partmasterIfc, PartConfigSpecIfc partconfigspecIfc) throws QMException 
    {
        QMPartMasterIfc partmasterIfc1[] = {partmasterIfc};
        return getAllVersions(partmasterIfc1, partconfigspecIfc);
    }

    /**
     * 根据零部件主信息集合和配置规范，获取每个零部件主信息对应的符合配置规范的最新版本的零部件。
     * 返回值是Hashtable，该Hashtable中有两个key:"part", "partmaster", 分别对应了
     * 两个Vector：即查找到的零部件的集合，和没有找到合适版本的QMPartMasterIfc的集合。
     * @param partmasterIfc :零部件主信息值对象的集合。
     * @param partconfigspecifc :零部件配置规范值对象。
     * @return Hashtable 符合配置规范的零部件集合。
     * @
     */
    public static Hashtable getAllVersionsNow(QMPartMasterIfc partmasterIfc[], PartConfigSpecIfc partconfigspecifc) 
    {
        try {
			return getAllVersionsNow(partmasterIfc, partconfigspecifc, true);
		} catch (QMException e) {
			e.printStackTrace();
			
			return null;
		}
    }

    /**
     * 根据零部件主信息集合和配置规范，如果isLatest为true，获取每个零部件主信息对应的符合配置规范的最新版本最新版序的零部件。
     * 如果isLatest为false，获取每个零部件主信息对应的符合配置规范的各个大版本最新版序的零部件。
     * 返回值是Hashtable，该Hashtable中有两个key:"part", "partmaster", 分别对应了
     * 两个Vector：即查找到的零部件的集合，和没有找到合适版本的QMPartMasterIfc的集合。
     * @param partmasterIfc :零部件主信息值对象的集合。
     * @param partconfigspecifc :零部件配置规范值对象。
     * @param isLatest :最新版本的标志。
     * @return Hashtable 符合配置规范的零部件集合。
     * @throws QMException 
     * @
     */
    public static Hashtable getAllVersionsNow(QMPartMasterIfc partmasterIfc[], PartConfigSpecIfc partconfigspecifc, boolean isLatest) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersionsNow() begin ....");
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, " 输入的参数为：  ");
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            PartDebug.trace(PartDebug.PART_CLIENT, "     " + partmasterIfc[i]);
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "输入的零部件配置规范为：");
        PartEffectivityConfigSpec effConfigSpec = partconfigspecifc.getEffectivity();
        if(null != effConfigSpec)
        {
            ViewObjectIfc viewObjectIfc = effConfigSpec.getViewObjectIfc();
            if(null != viewObjectIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "   视图名称：   " + viewObjectIfc.getViewName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "有效性配置规范的视图为空！");
            }
            QMConfigurationItemIfc effItemIfc = effConfigSpec.getEffectiveConfigItemIfc();
            if(null != effItemIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "配置规范的有效性配置项：" + effItemIfc.getConfigItemName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "配置规范的有效性配置项为空！");
            }
        } //end if (null != effConfigSpec)
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");

        //保存中间结果值：
        Vector result = new Vector();
        Vector tempResult = new Vector();

        //构造一个Collection的子类Vector作为调用filterIterationsOf的参数：
        for(int i = 0;i < partmasterIfc.length;i++)
        {
            tempResult.add(partmasterIfc[i]);

        }
        //过滤符合配置规范的零部件小版本。
        //调用ConfigService中的filteredIterationsOf(Collection, ConfigSpec):Collection，
        //由原来的调用方法ConfigService.filterIterationsOf修改为：filterIterationsOf()，
        //参数类型的集合。
        Class[] paraClass = {Collection.class, ConfigSpec.class};

        //参数值的集合。
        Object[] objs = {(Collection)tempResult, (ConfigSpec)(new PartConfigSpecAssistant(partconfigspecifc, isLatest))};
            //            Collection collection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "ConfigService", "partFilterIterationsOf", paraClass, objs);
            Collection collection = (Collection)RequestHelper.request("ConfigService", "filterIterationsOf", paraClass, objs);

            //需要把collection中的所有元素都放到result中来。
            if((null != collection) && (collection.size() > 0))
            {
                Iterator iterator = collection.iterator();
                while(iterator.hasNext())
                {
                    result.addElement(iterator.next());
                }
            }else
            {
                return null;
            }
        //现在需要对result集合中的元素进行处理：
        ArrayList partVector = new ArrayList();
        ArrayList partMasterVector = new ArrayList();
        for(int i = 0;i < result.size();i++)
        {
            Object object = result.elementAt(i);
            if(object instanceof QMPartIfc)
            {
                partVector.add(object);
            }else
            {
                if(object instanceof QMPartMasterIfc)
                {
                    partMasterVector.add(object);
                }
            }
        } //end for(int i=0; i<result.size(); i++)

        //组装最后的返回值：
        Hashtable hashtable = new Hashtable();
        hashtable.put("part", partVector);
        hashtable.put("partmaster", partMasterVector);
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersionsNow()" + " end....return is Hashtable");

        //变量复位。
        partVector = null;
        partMasterVector = null;
        return hashtable;
    }

    /**
     * 根据零部件主信息和配置规范，获取所有版本的零部件值对象。
     * 返回值Vector是Object[]的集合，即Vector中的每个元素都是Object[]形式的
     * 这样的每个Object[]对应了每个输入的QMPartMasterIfc[]的每个QMPartMasterIfc元素所对应的
     * 所有符合配置规范的零部件值对象。
     * @param partmasterIfc :QMPartMasterIfc[] 零部件主信息值对象的集合。
     * @param partconfigspecifc :PartConfigSpecIfc 零部件配置规范值对象。
     * @return Vector 符合配置规范的零部件值对象集合。
     * @throws QMException 
     * @
     */
    public static Hashtable getAllVersionsOnProcess(QMPartMasterIfc partmasterIfc[], PartConfigSpecIfc partconfigspecifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersions" + "OnProcess() begin ....");
        Vector result = new Vector();
        Vector paramsForCapp = new Vector();
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            paramsForCapp.add(partmasterIfc[i]);
        }

        //过滤符合配置规范的零部件小版本。
        //调用ConfigService中的cappFilteredIterationsOf(Collection, ConfigSpec):Collection，
        //参数类型的集合。
        Class[] paraClass = {Collection.class, ConfigSpec.class};

        //参数值的集合。
        Object[] objs = {(Collection)paramsForCapp, (ConfigSpec)(new PartConfigSpecAssistant(partconfigspecifc))};
            //            Collection collection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "ConfigService", "cappFilterIterationsOf", paraClass, objs);
            Collection collection = (Collection)RequestHelper.request("ConfigService", "cappFilterIterationsOf", paraClass, objs);

            //需要把collection中的所有元素都放到result中来。
            if(collection.size() > 0)
            {
                Iterator iterator = collection.iterator();
                while(iterator.hasNext())
                {
                    result.addElement(iterator.next());
                }
            }else
            {
                return null;
            }
        ArrayList partVector = new ArrayList();
        ArrayList partMasterVector = new ArrayList();
        for(int i = 0, j = result.size();i < j;i++)
        {
            Object object = result.elementAt(i);
            if(object instanceof QMPartIfc)
            {
                partVector.add(object);
            }else
            {
                if(object instanceof QMPartMasterIfc)
                {
                    partMasterVector.add(object);
                }
            }
        }
        Hashtable hashtable = new Hashtable();
        hashtable.put("part", partVector);
        hashtable.put("partmaster", partMasterVector);

        //变量复位。
        partVector = null;
        partMasterVector = null;

        return hashtable;
    }

    /**
     * 根据输入的时间戳参数，截取其"年-月-日"部分。
     * @param timestamp 输入的时间戳。
     * @return String 字符串格式的"年-月-日"。
     */
    public static String getDate(Timestamp timestamp)
    {
        if(null == timestamp)
        {
            return "";
        }
        String timeString = timestamp.toString().trim();
        String resultString = "";
        for(int i = 0, j = timeString.length();i < j;i++)
        {
            if(timeString.substring(i, i + 1).equals(" "))
            {
                break;
            }else
            {
                resultString = resultString + timeString.substring(i, i + 1);
            }
        }
        return resultString;
    }

    /**
     * 获取指定类里面的指定属性的最大长度。
     * @param class1 指定的类。
     * @param s 指定类的指定属性。
     * @return int 最大长度。
     */
    public static int getMaxLength(Class class1, String s)
    {
        int i = 0;
        return i;
    }

    /**
     * 判断当前用户是否具有访问权限。
     * @param className String 访问的类名。
     * @param permission String 访问控制权限。
     * @return boolean ＝true表示有访问权限，否则无访问权限。
     * @throws QMException 
     * @
     */
    public static Boolean hasPermission(String className, String permission) throws QMException 
    {
        //参数类型的集合。
        Class[] paraClass = {};

        //参数值的集合。
        Object[] objs = {};
        UserIfc user = null;//CR2
        
            user = (UserIfc)RequestHelper.request("SessionService",//CR2
                    "getCurUser", paraClass, objs);
            //            user = (UserIfc) IBAUtility.invokeServiceMethod("SessionService",//CR2
            //                    "getCurUser", paraClass, objs);
            //        }
            //        catch (QMRemoteException e)
            //        {
            //            e.printStackTrace();
            //            throw e;
        
        PartDebug.trace(PartDebug.PART_CLIENT, "--- user is :     " + user);

        //调用访问控制服务判断当前用户对指定的类、指定的域、
        //指定的生命周期状态是否具有指定的访问控制权限，
        //参数类型的集合。
        Class[] paraClass1 = {ActorIfc.class,//CR2
                String.class, String.class, LifeCycleState.class, String.class};
        //参数值的集合。
        Object[] objs1 = {user, //用户。
                className, //类名。
                DomainHelper.SYSTEM_DOMAIN, //域。
                null, //状态。
                permission //访问控制权限。
        };
        Boolean flag = null;
        //            flag = (Boolean) IBAUtility.invokeServiceMethod(
		//                    "AccessControlService", "hasAccess", paraClass1, objs1);
		flag = (Boolean)RequestHelper.request("AccessControlService", "hasAccess", paraClass1, objs1);
        return flag;
    }

    /**
     * 根据用户BsoID获取用户名全称：
     * @param userID 用户的BsoID
     * @return String 用户名
     * @throws QMException 
     * @
     */
    public static String getUserNameByID(String userID) throws QMException 
    {
        Class[] paraClass1 = {String.class};
        Object[] objs1 = {userID};
        UserInfo userInfo = null;
        
            userInfo = (UserInfo)RequestHelper.request("PersistService", "refreshInfo", paraClass1, objs1);
        
        //      UserInfo userInfo = (UserInfo) IBAUtility.invokeServiceMethod(
        //              "PersistService", "refreshInfo", paraClass1, objs1);
        String userName = userInfo.getUsersDesc();
        return userName;
    }

    /**
     * 判断该零部件是否可以更新。
     * @param selectObject QMPartIfc 
     * @return boolean 如果该零部件可以更新返回true，否则返回false。
     * @throws QMException 
     */
    public boolean isAllowUpdate(QMPartIfc selectObject) throws QMException
    {
        boolean isupdate = true;
            if(null == selectObject)
            {
                isupdate = false;
            }else
            {
                if(selectObject instanceof WorkableIfc)
                {
                    if(((QMPartIfc)selectObject).getBsoName().equals("GenericPart"))
                    {
                        isupdate = false;
                    }
                    //if 1:如果被其他用户检出并是零部件,提示"部件已经被他人检出!"。
                    if(CheckInOutHelper.isCheckedOutByOther((WorkableIfc)selectObject))
                    {
                        isupdate = false;
                    } //end if 12:如果被当前用户检出。
                    else if(CheckInOutHelper.isCheckedOutByCur((WorkableIfc)selectObject))
                    {
                        //if 2:如果是被当前用户检出但不是工作副本,就获得工作副本。
                        if(!WorkInProgressUtil.isWorkingCopy((WorkableIfc)selectObject))
                        {
                            isupdate = false;
                        }
                    }
                    //if 3:判断对象是否允许修改。
                    else if(!isUpdateAllowed((FolderedIfc)selectObject))
                    {
                        isupdate = false;
                    }
                }
            }
        return isupdate;
    }

    /**
     * 判断对象是否允许修改。
     * @param obj FolderedIfc 资料夹值对象。
     * @throws QMException 
     * @
     * @
     * @return boolean 返回true则允许修改，否则不允许。
     */
    public boolean isUpdateAllowed(FolderedIfc obj) throws QMException 
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "idUpdateAllowed()..begin ....");
        boolean flag = true;
        if((obj instanceof WorkableIfc) && (obj instanceof OwnableIfc) && !WorkInProgressUtil.isWorkingCopy((WorkableIfc)obj))
        {
            Object object = null;
            
                object = RequestHelper.request("SessionService", "getCurUserID", null, null);
            
            //            Object object = IBAUtility.invokeServiceMethod("SessionService",
            //                    "getCurUserID", null, null);
            flag = OwnershipUtil.isOwnedBy((OwnableIfc)obj, (String)object);
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "------  flag is:    " + flag);
        PartDebug.trace(this, PartDebug.PART_CLIENT, "isUpdateAllowed()..end ....");
        return flag;
    }
    /**
     * 在创建/更新文档时，将文档添加为零部件的描述文档。
     * @param parts 零部件BsoID集合。
     * @param docBsoID 文档值对象BsoID。
     * @return String 提示信息。
     * @throws QMException 
     * @
     */
    public static void createDescribeLink(Vector parts, String docBsoID) throws QMException
            
    {
//        RequestHelper helper = new RequestHelper();
        QMPartIfc partIfc = null;
        String partBsoID = "";
        PartDescribeLinkIfc linkIfc = null;
//        String message = "";//CR4
        for (int i = 0; i < parts.size(); i++)
        {
            
                partIfc = (QMPartIfc) RequestHelper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{parts.get(i)});
            
            partBsoID = partIfc.getBsoID();
            //零部件已被当前用户检出或在个人资料夹中。
            if(CheckInOutHelper.isCheckedOutByCur(partIfc)
                    || !CheckInOutHelper.isInVault(partIfc))
            {
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partBsoID);
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                //"成功添加为 * 的描述文档。\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M002", new Object[] {partIfc.getIdentity()});//CR4
            }
            //零部件已被其他用户检出。
            else if(CheckInOutHelper.isCheckedOutByOther(partIfc))
            {
                //"* 已被用户 * 检出！\n"
//                message = message + QMMessage.getLocalizedMessage(resource1, "M003",
//                        new Object[]{partIfc.getIdentity(),
//                        getUserNameByID(partIfc.getLocker())});//CR4
            }
            //当前用户有修改零部件的权限。检出零部件，创建关联，检入零部件。
            else if(WorkInProgressUtil.isCheckoutAllowed(partIfc))
            {
                WorkingPair workingPair = CheckInOutHelper.checkOutObject(partIfc);
                if(!workingPair.isOperateSuccess())//CR5
                {
                    throw new QMException(workingPair.getException());
                }
                // 工作副本BsoID。
                String workableID = workingPair.getWorkingCopy().getBsoID();
                Class[] theClass = {String.class};
                Object[] theObjs = {workableID};
                partIfc = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", theClass, theObjs);
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partIfc.getBsoID());
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                WorkingPair workableIfc1 = CheckInOutHelper.checkInObject(partIfc, "");
                WorkableIfc workableIfc = workableIfc1.getWorkingCopy();
                // "成功添加为 * 的描述文档。\n"
//                message = message + QMMessage.getLocalizedMessage(resource1, "M002", new Object[]{workableIfc.getIdentity()});//CR4
            }
            // 当前用户没有修改零部件的权限。
            else
            {
                // "您没有修改 * 的权限，不能为此零部件添加描述文档！\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M005", new Object[] {partIfc.getIdentity()});//CR4
            }
        }
//        return message;
    }
    /**
     * 在创建/更新文档时，将文档添加为零部件的参考文档。
     * @param parts 零部件BsoID集合。
     * @param docMasterBsoID 文档主信息BsoID。
     * @return String 提示信息。
     * @throws QMException 
     * @
     */
    public static void createReferenceLink(Vector parts, String docMasterBsoID) throws QMException
            
    {
//        RequestHelper helper = new RequestHelper();
        QMPartIfc partIfc = null;
        String partBsoID = "";
        PartReferenceLinkIfc linkIfc = null;
//        String message = "";//CR4
        for (int i = 0; i < parts.size(); i++)
        {
            
                partIfc = (QMPartIfc) RequestHelper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{parts.get(i)});
           
            partBsoID = partIfc.getBsoID();
            //零部件已被当前用户检出或在个人资料夹中。
            if(CheckInOutHelper.isCheckedOutByCur(partIfc)
                    || !CheckInOutHelper.isInVault(partIfc))
            {
                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partBsoID);
                PartServiceRequest.savePartReferenceLink(linkIfc);
                //"成功添加为 * 的参考文档。\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M001", new Object[] {partIfc.getIdentity()});//CR4
            }
            //零部件已被其他用户检出。
            else if(CheckInOutHelper.isCheckedOutByOther(partIfc))
            {
                //"* 已被用户 * 检出！\n"
//                message = message
//                        + QMMessage.getLocalizedMessage(resource1, "M003",
//                                new Object[]{partIfc.getIdentity(),
//                                        getUserNameByID(partIfc.getLocker())});//CR4
            }
            //当前用户有修改零部件的权限。检出零部件，创建关联，检入零部件。
            else if(WorkInProgressUtil.isCheckoutAllowed(partIfc))
            {
                WorkingPair workingPair = CheckInOutHelper.checkOutObject(partIfc);
                // 工作副本BsoID。
                String workableID = workingPair.getWorkingCopy().getBsoID();
                partIfc = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", new Class[]{String.class}, new Object[]{workableID});

                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partIfc.getBsoID());
                PartServiceRequest.savePartReferenceLink(linkIfc);
                WorkingPair workableIfc1 = CheckInOutHelper.checkInObject(partIfc, "");
                WorkableIfc workableIfc=workableIfc1.getWorkingCopy();
                // "成功添加为 * 的参考文档。\n"
//                message = message + QMMessage.getLocalizedMessage(resource1, "M001", new Object[]{workableIfc.getIdentity()});//CR4
            }
            // 当前用户没有修改零部件的权限。
            else
            {
                // "您没有修改 * 的权限，不能为此零部件添加参考文档！\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M004", new Object[] {partIfc.getIdentity()});//CR4
            }
        }
//        return message;//CR4
    }
    /**
     * 在更新文档时，删除文档和零部件的参考文档。
     * @param parts 零部件BsoID集合。
     * @param docMasterBsoID 文档主信息BsoID。
     * @return String 提示信息。
     * @throws QMException 
     */
    public static void deleteReferenceLink(Vector parts, String docMasterBsoID) throws QMException
    {
//        RequestHelper helper = new RequestHelper();
//        String message = "";//CR4
        for (int i = 0; i < parts.size(); i++)
        {
            String partBsoID = (String) parts.get(i);
            QMPartIfc partIfc = null;
            Collection coll = new ArrayList(1);
                partIfc = (QMPartIfc) RequestHelper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{partBsoID});
                QMQuery query = new QMQuery("PartReferenceLink");
                QueryCondition condition = new QueryCondition("rightBsoID",
                        QueryCondition.EQUAL, partBsoID);
                query.addCondition(condition);
                query.addAND();
                QueryCondition condition2 = new QueryCondition("leftBsoID",
                        QueryCondition.EQUAL, docMasterBsoID);
                query.addCondition(condition2);
                coll = (Collection) RequestHelper.request("PersistService",
                        "findValueInfo", new Class[]{QMQuery.class},
                        new Object[]{query});
            //只含有一个对象。
            Iterator iter = coll.iterator();
            while (iter.hasNext())
            {
                    RequestHelper.request("PersistService", "deleteValueInfo",
                            new Class[]{BaseValueIfc.class}, new Object[]{iter
                                    .next()});
//                    message += QMMessage.getLocalizedMessage(resource1, "M006",
//                            new Object[]{partIfc.getIdentity()});//CR4
            }
        }
//       return message;//CR4
    }

    /**
     * 在更新文档时，删除文档和零部件的描述文档。
     * @param parts 零部件BsoID集合。
     * @param docBsoID 文档值对象BsoID。
     * @return String 提示信息。
     * @throws QMException */
    public static void deleteDescribeLink(Vector parts, String docBsoID)throws QMException
    {
//        RequestHelper helper = new RequestHelper();
//        String message = "";//CR4
        for (int i = 0; i < parts.size() ; i++)
        {
            String partBsoID = (String) parts.get(i);
            QMPartIfc partIfc = null;
            Collection coll = new ArrayList(1);
                partIfc = (QMPartIfc) RequestHelper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{partBsoID});
                QMQuery query = new QMQuery("PartDescribeLink");
                QueryCondition condition = new QueryCondition("rightBsoID",
                        QueryCondition.EQUAL, docBsoID);
                query.addCondition(condition);
                query.addAND();
                QueryCondition condition2 = new QueryCondition("leftBsoID",
                        QueryCondition.EQUAL, partBsoID);
                query.addCondition(condition2);
                coll = (Collection) RequestHelper.request("PersistService",
                        "findValueInfo", new Class[]{QMQuery.class},
                        new Object[]{query});
            Iterator iter = coll.iterator();
            while (iter.hasNext())
            {
                    RequestHelper.request("PersistService", "deleteValueInfo",
                            new Class[]{BaseValueIfc.class}, new Object[]{iter
                                    .next()});
//                    message += QMMessage.getLocalizedMessage(resource1, "M007",
//                            new Object[]{partIfc.getIdentity()});//CR4
            }
        }
//        return message;//CR4
    }
    
    
  
}
