/**
  * 生成程序 StandardPartServiceEJB.java    1.0    2003/02/19
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * CR1 2009/04/02 谢斌 原因:只需要值对象和图标信息即可
  *                     方案:重写方法
  *                     备注:解放v3r11-被用于产品性能优化
  *                      
  *CR2 2008/03/27 王海军 原因：优化输出统计物料清单
  *                       方案：通过添加缓存，减少临时对象等减少输出物料清单的响应时间和内存
  *                       用例：输出统计物料清单
  *CR3 2008/03/20 王海军 原因：优化输出分级物料清单
  *                       方案：通过添加缓存，减少临时对象等减少输出物料清单的响应时间和内存
  *                       用例：输出分级物料清单
  *CR4 2009/04/27 谢斌 原因:逻辑混乱
  *                     方案:重写服务端逻辑
  *                     备注:解放v3r11-分级物料清单性能优化-谢斌
  *CR5 2009/05/20  王亮 参见缺陷ID：2141（09CTAuditing）
  *CR51 2009/06/18 谢斌 原因：分级物料清单无法输出替换件、结构替换件、事物特性信息。TD-2154
  *                    方案：由于是移植解放性能优化代码，解放中没有对替换件、结构替换件做处理，现增加相关逻辑，出于性能考虑，产品中只显示字符串类型事物特性，其它类型如有需要实施阶段可自行增加。
  *CR6 2009/06/19 马辉 原因：TD2447 当零部件只有一个顶级父件的时候，顶级父件在查看该零部件的“被用于产品”信息的时候不显示
  *                    备注：当时是在setUsageList方法中刻意的在集合中只有一个元素的情况下去掉了那个元素，因为如果不做处理，当零部件没有父件的时候返回自己本身，“被用于产品”界面显示自己本身。
  *                    方案：在setUsageList方法中集合中只有一个元素的情况下判断bsoid是否和本身的零部件bsoid一致，如果一致删除集合中的元素
  * CR7 20090619 张强 修改原因：进行结构比较后，产品信息管理中配置规范显示为结构比较目标件的配置规范。（TD-2190） 
  *                   解决方案：进行结构比较后，产品信息管理中正确显示的配置规范。                    
  * CCBegin by liunan 2011-04-20 打补丁v4r3_p032
  *CR9 2011/03/24 马辉 原因：TD3687 查看零部件的被用于部件显示不正确(此处是修改被用于产品)
  *                    备注：以现在的查找父件的方式会查到错误的父件
  *                    方案：查找父件以后，再用符合配置规范的最新父件向下查找一级子件，如果查找的关联还存在救命是正确父件，如果不存在证明曾经是父件现在已经不用。                                                 
  * CCEnd by liunan 2011-04-20 
  * CCBegin by liunan 2012-03-21
  * CR10 2012/03/15 王亮 修改原因： 被用于部件和被用于产品改为用jdbc查询。 
  * CCEnd by liunan 2012-03-21
  * SS1 开发帮助优化代码。 liunan 2013-7-15
  * CR11 2012/07/15 王亮 修改原因：td2586，被用于产品引起系统死机。
  * SS2 2013-1-21 产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能，该部分代码为反编译所得。由于源代码已经丢失。
  * SS3 通过masterid获取最新版本的零部件。 liunan 2013-11-25
  * SS4 轴齿中心分子公司物料清单生成EXCEL过滤路线单位 liuyang 2013-12-2
  * SS5 增加产品信息管理器零件树定位搜索零件功能 pante 2015-01-146
  * SS6 A004-2015-3156 查看被用于部件时，权限搜索没有根据零部件状态过滤，导致权限判断不准。 liunan 2015-6-26
  * SS7 A004-2016-3286 整车一级件清单 liunan 2016-1-20
  * SS8  成都工序添加获得子件 guoxiaoliang 2016-7-28
  * SS9 A004-2017-3491 零部件不能被删除，提示有关联，实际被某个件的历史版本使用，但被用于部件看不到。 liunan 2017-5-16
  */

package com.faw_qm.part.ejb.standardService;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import com.bsx.util.BSXUtil;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.ConfigSpec;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.effectivity.util.EffectivityType;
import com.faw_qm.enterprise.model.MakeFromLinkIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.entity.PartMasterIdentity;
import com.faw_qm.part.ejb.entity.QMPartMaster;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.NormalPart;
import com.faw_qm.part.util.PartBaselineConfigSpec;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartEffectivityConfigSpec;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.query.DateHelper;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.struct.ejb.service.StructService;
import com.faw_qm.unique.ejb.service.IdentifyService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.viewmanage.model.ViewObjectIfc;

//CCBegin by liunan 2008-08-01
import com.faw_qm.version.model.MasteredIfc;
 import com.faw_qm.part.util.WfUtil;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.iba.definition.model.AbstractAttributeDefinitionIfc;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.BooleanValueIfc;
import com.faw_qm.iba.value.model.FloatValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.IBAReferenceableIfc;
import com.faw_qm.iba.value.model.IntegerValueIfc;
import com.faw_qm.iba.value.model.RatioValueIfc;
import com.faw_qm.iba.value.model.ReferenceValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.model.StringValueInfo;

//CCBegin by liunan 2008-08-01
import com.faw_qm.iba.value.model.TimestampValueIfc;
import com.faw_qm.iba.value.model.URLValueIfc;
import com.faw_qm.iba.value.model.UnitValueIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartAttrSetIfc;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;

//CCBegin SS3
import com.faw_qm.part.model.QMPartInfo;
//CCEnd SS3

/**
 * 零部件标准服务EJB实现类。
 * @author 吴先超
 * @version 1.0
 */
//问题(1)20080811 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）

public class StandardPartServiceEJB extends BaseServiceImp
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    private String RESOURCE = "com.faw_qm.part.util.PartResource";
    PartConfigSpecAssistant pcon=null;
    //CCBegin by liunan 2009-01-06 打补丁v3r11_p021_20090105
    private HashMap configSpecCach=new HashMap();
    //CCEnd by liunan 2009-01-06
    HashMap masterMap=new HashMap();//CR2
    String noHasSubParts = QMMessage.getLocalizedMessage(RESOURCE, "false", null);//CR2
    String isHasSubParts = QMMessage.getLocalizedMessage(RESOURCE, "true", null);//CR2
    /**
     * 构造函数。
     */
    public StandardPartServiceEJB()
    {
        super();
    }

    /**
     * 通过零部件的名字和号码查找零部件的主信息。返回的集合中只有一个QMPartMasterIfc对象。
     * @param partName :String 零部件的名称。
     * @param partNumber :String 零部件的号码。
     * @return collection:查找到的QMPartMasterIfc零部件主信息对象的集合，只有一个元素。
     * @throws QMException
     */
    public Collection findPartMaster(String partName, String partNumber)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartMaster begin ....");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        QueryCondition condition1 = new QueryCondition("partName", "=", partName);
        query.addCondition(condition1);
        query.addAND();
        QueryCondition condition2 = new QueryCondition("partNumber", "=", partNumber);
        query.addCondition(condition2);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartMaster end....return is Collection ");
        return pservice.findValueInfo(query);
    }

    /**
     * 通过一个零部件主信息找到对应的零部件的所有小版本(包括不同分支)值对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection :Collection所有对应于partMasterIfc的零部件对象的集合。
     * @exception QMException 持久化异常，或者版本服务异常。
     */
    public Collection findPart(QMPartMasterIfc partMasterIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPart begin ....");
        VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPart end....return is Collection ");
        return vcService.allIterationsOf(partMasterIfc);
    }

    /**
     * 删除参考文档与零部件的关联关系。
     * @throws PartException
     * @param linkIfc PartReferenceLinkIfc
     */
    public void deletePartReferenceLink(PartReferenceLinkIfc linkIfc) throws PartException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePartReferenceLink begin ....");
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            pService.deleteValueInfo(linkIfc);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePartReferenceLink end....return is void");
    }
    /**
     * 保存参考文档与零部件的关联关系。
     * @param linkIfc PartReferenceLinkIfc
     * @throws PartException
     * @return PartReferenceLinkIfc
     */
    public PartReferenceLinkIfc savePartReferenceLink(PartReferenceLinkIfc linkIfc) throws PartException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartReferenceLink begin ....");
        PartReferenceLinkIfc partReferenceLinkIfc = null;
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            partReferenceLinkIfc = (PartReferenceLinkIfc)pService.saveValueInfo(linkIfc);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartReferenceLink end....return is PartReferenceLinkIfc ");
        return partReferenceLinkIfc;
    }

    /**
     * 保存零部件与零部件主信息的关联关系。
     * @param linkIfc PartUsageLinkIfc
     * @throws PartException
     * @return PartUsageLinkIfc
     */
    public PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc linkIfc) throws PartException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartUsageLink begin ....");
        PartUsageLinkIfc partUsageLinkIfc = null;
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            partUsageLinkIfc = (PartUsageLinkIfc)pService.saveValueInfo(linkIfc);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartUsageLink end....return is PartUsageLinkIfc ");
        return partUsageLinkIfc;
    }

    /**
     * 删除零部件与零部件主信息的关联关系。
     * @param linkIfc PartUsageLinkIfc
     * @throws PartException
     */
    public void deletePartUsageLink(PartUsageLinkIfc linkIfc) throws PartException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePartUsageLink begin ....");
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            pService.deleteValueInfo(linkIfc);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePartUsageLink end....return is void ");
    }

    /**
     * 保存描述文档与零部件的关联关系。
     * @param linkIfc PartDescribeLinkIfc
     * @throws PartException
     * @return PartDescribeLinkIfc
     */
    public PartDescribeLinkIfc savePartDescribeLink(PartDescribeLinkIfc linkIfc) throws PartException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartDescribeLink begin ....");
        PartDescribeLinkIfc partDescribeLinkIfc = null;
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            partDescribeLinkIfc = (PartDescribeLinkIfc)pService.
                    saveValueInfo(linkIfc);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartDescribeLink end....return is PartDescribeLinkIfc ");
        return partDescribeLinkIfc;
    }

    /**
     * 删除描述文档与零部件的关联关系。
     * @param linkIfc PartDescribeLinkIfc
     * @throws PartException
     */
    public void deletePartDescribeLink(PartDescribeLinkIfc linkIfc) throws PartException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePartDescribeLink begin ....");
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            pService.deleteValueInfo(linkIfc);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePartDescribeLink end....return is void ");
    }

    /**
     * 修订零部件。重新生成一个新的大版本。
     * @param partIfc :QMPartIfc 修订前的零部件的值对象，必须为最新的迭代版本。
     * @return partIfc :QMPartIfc 修订后的零部件的值对象。
     * @throws QMException
     */
    public QMPartIfc revisePart(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "revisePart begin ....");
        //判断part是否允许修订。
        boolean flag = VersionControlHelper.isReviseAllowed(partIfc, true);
        VersionControlService vcservice = (VersionControlService)EJBServiceHelper.
            getService("VersionControlService");
        PartDebug.trace(this, PartDebug.PART_SERVICE, "revisePart end....return is QMPartIfc ");
        return (QMPartIfc)vcservice.newVersion(partIfc);
    }

    /**
     * 根据零部件值对象获得该零部件关联的所有参考文档(DocIfc)最新版本的值对象的集合。
     * 先根据零部件获取所有的参考文档主信息(DocMasterIfc)的集合,再对DocMasterIfc集合
     * 进行过滤，找出每个DocMasterIfc所对应的最新版本DocIfc。最后返回DocIfc的集合。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @return Vector 零部件参考文档(DocIfc)值对象的集合。
     * @throws QMException
     */
    public Vector getReferencesDocMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getReferencesDocMasters begin ....");
        if(partIfc == null)
        {
            Object[] obj = {"QMPartIfc"};
            throw new PartException(RESOURCE, "CP00001", obj);
        }
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection collection = pService.navigateValueInfo(partIfc,"referencedBy","PartReferenceLink");
        Vector vector = new Vector();
        Vector resultVector = new Vector();
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof DocMasterIfc)
                {
                    vector.addElement((DocMasterIfc)obj);
                }
            }
            //需要对vector中的所有的DocMasterIfc过滤出最新小版本::
            ConfigService cService = (ConfigService)EJBServiceHelper.getService("ConfigService");
            Collection collection1 = cService.filteredIterationsOf(vector, new LatestConfigSpec());
            Iterator iterator1 = collection1.iterator();
            while(iterator1.hasNext())
            {
                //把iterator1中的所有合格的DocIfc放到结果集合中::
                Object obj1 = iterator1.next();
                if(obj1 instanceof Object[])
                {
                    Object[] obj2 = (Object[])obj1;
                    resultVector.addElement((DocIfc)obj2[0]);
                }
            }
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getReferencesDocMasters end....return is Vector ");
        }
        //end if(collection != null && collection.size() > 0)
        return resultVector;
    }

    /**
     * 根据指定的零部件的值对象返回该零部件描述文档的值对象集合。如果标志flag为true,
     * 返回的结果Vector中是DocIfc的集合；如果flag为false，返回的Vector是
     * PartDescribeLinkIfc的集合。
     * @param partIfc :QMPartIfc零部件的值对象。
     * @param flag : boolean
     * @return vector:Vector 零部件描述文档(DocIfc)集合，或者是描述关联关系值对象的集合。
     * @throws QMException
     */
    public Vector getDescribedByDocs(QMPartIfc partIfc, boolean flag) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getDescribedByDocs begin ....");
        //如果条件成立，抛出PartException异常"参数不能为空。"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        StructService sservice = (StructService)EJBServiceHelper.getService("StructService");
        //获得part描述文档的值对象的集合，返回collection1
        Collection collection1 = sservice.navigateDescribedBy(partIfc, flag);
        Vector vector = new Vector();
        if(collection1 != null && collection1.size() > 0)
        {
            Iterator iterator = collection1.iterator();
            while (iterator.hasNext())
            {
                Object object = iterator.next();
                vector.addElement(object);
            }
        }
        //end if(collection1 != null && collection1.size() > 0)
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getDescribedByDocs end....return is Vector ");
        return vector;
    }

    /**
     * 根据主文档的值对象获得和该参考文档关联的的零部件的值对象的集合。
     * @param docMasterIfc :DocMasterIfc 参考的文档的值对象。
     * @return vector:Vector 被文档参考的零部件的值对象的集合。
     * @throws QMException
     */
    public Vector getReferencedByParts(DocMasterIfc docMasterIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getReferencedByParts begin ....");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getReferencedByParts end....return is Vector ");
        return (Vector)pservice.navigateValueInfo(docMasterIfc, "references", "PartReferenceLink");
    }

    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection 与partIfc关联的PartUsageLinkIfc的对象集合。
     * @throws QMException
     */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters begin ....");
        //如果条件成立，则抛出PartException"参数不能为空"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters end....return is Colletion ");
        return pservice.navigateValueInfo(partIfc,"usedBy","PartUsageLink",false);
    }

    /**
     * 根据指定的零部件返回下级零部件的最新版本的值对象的集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection partIfc使用的下级子件的最新版本的值对象集合。
     * @throws QMException
     */
    public Collection getSubParts(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubParts begin ....");
        //如果条件成立，抛出PartEception异常"参数不能为空"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //CCBegin by leixiao 2008-10-06 原因：解放升级,获取子件时 GenericPart 找不到子件
        Collection collection=null;
        if(partIfc.getBsoName().equals("GenericPart"))
         collection = pservice.navigateValueInfo(partIfc,"usedBy","GenericPartUsageLink");
        else if(partIfc.getBsoName().equals("QMPart"))
         collection = pservice.navigateValueInfo(partIfc,"usedBy","PartUsageLink");
        //CCEnd by leixiao 2008-10-06 原因：解放升级
        Object[] tempArray = (Object[])collection.toArray();
        VersionControlService vcservice = (VersionControlService)EJBServiceHelper.getService
            ("VersionControlService");
        Vector result = new Vector();
        Vector tempResult = new Vector();
        for (int i=0; i<tempArray.length; i++)
        {
        	  //CCBegin by leixiao 2008-10-06 原因：解放升级
            tempResult = new Vector(vcservice.allVersionsOf((MasteredIfc)tempArray[i]));
            //CCEnd by leixiao 2008-10-06 原因：解放升级
            if (tempResult != null && (tempResult.iterator()).hasNext())
                result.addElement((tempResult.iterator()).next());
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubParts end....return is Collection ");
        return result;
    }


    /**
     * 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @param result Collection  保存结果。
     * @param indexID Collection 保存ID。
     * @throws QMException
     */
     private void getAllSubParts(QMPartIfc partIfc,Collection result,Collection indexID)
         throws QMException
     {
         Collection col=getSubParts(partIfc);
         Iterator iter=col.iterator();
         while(iter.hasNext())
         {
             QMPartIfc part=(QMPartIfc)iter.next();
             String bsoID=part.getBsoID();
             if(!indexID.contains(bsoID))
             {
                 result.add(part);
                 indexID.add(bsoID);
                 getAllSubParts(part,result,indexID);
             }
         }

     }
    /**
     * 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection partIfc使用的下级子件的最新版本的值对象集合。
     * @throws QMException
     */
    public Collection getAllSubParts(QMPartIfc partIfc)
        throws QMException
    {
        Collection result=new Vector();
        Collection filterIndex=new ArrayList();
        if(partIfc!=null)
        {
            result.add(partIfc);
            filterIndex.add(partIfc.getBsoID());
            getAllSubParts(partIfc,result,filterIndex);
        }
        return result;
    }

    /**
     * 根据指定的QMPartMasterIfc对象，
     * 通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本
     * 的QMPartIfc对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。
     * @throws QMException
     */
    public Collection getUsedByParts(QMPartMasterIfc partMasterIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts begin ....");
        //CCBegin by liunan 2012-03-21
        /*QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        //按最新版本查询，返回条件cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //根据传入的数值确定表的位置，并向其添加查询条件,这里0表示是第一个表:是对第一个表添加查询条件
        query.addCondition(0,condition1);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts end....return is Collection");
        //根据一个值对象partMasterIfc和本值对象在关联类中的关联角色名"uses", 浏览关联的另一边的bso对象
        //query - 查询的过滤条件
        return pservice.navigateValueInfo(partMasterIfc, "uses", query);*/        
        QMQuery query = new QMQuery("QMPart");//Begin CR10
        query.appendBso("PartUsageLink", false);
        query.appendBso("QMPartMaster", false);
        query.setJdbc(true);
        Vector partAttr = new Vector();
        partAttr.add("versionValue");
        partAttr.add("viewName");
        //CCBegin SS6
        partAttr.add("lifeCycleState");
        //CCEnd SS6
        query.addAttribute(0, partAttr);
        Vector masterAttr = new Vector();
        masterAttr.add("partNumber");
        masterAttr.add("partName");
        query.addAttribute(2, masterAttr);
        //CCBegin SS9
        //QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //query.addCondition(0,condition1);
        //query.addAND();
        //CCEnd SS9
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts end....return is Collection");
        String bsoID = partMasterIfc.getBsoID();
        QueryCondition condition2 = new QueryCondition("leftBsoID", "=", bsoID);
        query.addCondition(1,condition2);
        query.addAND();
        QueryCondition condition3 = new QueryCondition("bsoID", "rightBsoID");
        query.addCondition(0,1, condition3);
        query.addAND();
        QueryCondition condition4 = new QueryCondition("masterBsoID", "bsoID");
        query.addCondition(0,2,condition4);
        return pservice.findValueInfo(query);//End CR10
        //CCEnd by liunan 2012-03-21
    }
    /**
     * 20070611 add whj for new request moth QMProductManager.isParentPart()
     * 根据指定的QMPartMasterIfc对象，
     * 通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本
     * 的QMPartIfc对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。
     * @throws QMException
     */
    public Collection getUsedByPParts(QMPartMasterIfc partMasterIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts begin ....");
        QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        if(!(partMasterIfc instanceof GenericPartMasterIfc))
        {
        	query.setChildQuery(false);
        }
        //按最新版本查询，返回条件cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //根据传入的数值确定表的位置，并向其添加查询条件,这里0表示是第一个表:是对第一个表添加查询条件
        query.addCondition(0,condition1);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts end....return is Collection");
        //根据一个值对象partMasterIfc和本值对象在关联类中的关联角色名"uses", 浏览关联的另一边的bso对象
        //query - 查询的过滤条件
        return pservice.navigateValueInfo(partMasterIfc, "uses", query);
    }
    /**
     * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
     * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
     * @param collection :Collection 是PartUsageLinkIfc对象的集合。
     * @param configSpecIfc :PartConfigSpecIfc 零部件的筛选条件。
     * @return collection:Collection 对象，每个元素为一个数组:
     * 数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，
     * 如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
     * @throws QMException
     */
    public Collection getUsesPartsFromLinks(Collection collection,
        PartConfigSpecIfc configSpecIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartsFromLinks begin ....");
        //获得collection中关联对象角色为"uses"的Mastered值对象的集合。
        //返回masterCollection
       // Collection masterCollection = ConfigSpecHelper.mastersOf(collection, "uses");
        Collection masterCollection =mastersOf(collection);
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
        //返回masterCollection中mastered对象管理的符合配置规范configSpec的
        //所有迭代版本的对象的集合，返回值为iteratedCollection
        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, new PartConfigSpecAssistant(configSpecIfc));
        //对于给定的master和这些master所管理的iteration对象的集合，
        //返回所有的iteration对象和所有在iteration集合中没有任何一个iteration对象
        //与之相匹配的master对象。返回对象集合为allCollection
        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection,iteratedCollection);
        //根据指定的关联集合（master与iteration之间）和对应master对象的iteration集合，
        //查找每个关联所连接的iteration对象，重建结果集（在每个结果数组的0位置存放关联对象，
        //在1位置存放iteration对象，如果没有对应的iteration对象，
        //则存放关联对象连接的master对象 。
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartsFromLinks end....return is Collection ");
        return ConfigSpecHelper.buildConfigResultFromLinks(collection,"uses",allCollection);
    }
    
    /**
     * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
     * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
     * @param collection 是PartUsageLinkIfc对象的集合。
     * @return 每个元素为一个数组.
     * 数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
     * @throws QMException
     */
    public Collection getUsesPartsFromLinks(Collection collection) throws QMException//Begin CR4：重写服务端逻辑。
    {
        Collection masterCollection = mastersOf(collection);
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
        if(pcon == null)
            pcon = new PartConfigSpecAssistant(findPartConfigSpecIfc());
        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, pcon);
        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection, iteratedCollection);
        return ConfigSpecHelper.buildConfigResultFromLinks(collection, "uses", allCollection);
    }//End CR4：重写服务端逻辑
    
    /**
     * 保存零部件。
     * @param partIfc :QMPartIfc 要保存的零部件的值对象。
     * @return partIfc:QMPartIfc 保存后的零部件的值对象。
     * @throws QMException
     */
    public QMPartIfc savePart(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePart begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePart end....return is QMPartIfc ");
        return (QMPartIfc)pService.saveValueInfo(partIfc);
    }

    /**
     * 删除指定的零部件，如果有其他部件使用该部件，
     * 则异常"该零部件已经被其他部件使用，不能删除！"。
     * @param partIfc :QMPartIfc 要删除的零部件的值对象。
     * @throws QMException
     */
    public void deletePart(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePart begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        pService.deleteValueInfo(partIfc);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deletePart end....return is void");
    }

    /**
     * 判断零部件partIfc2是否是零部件partIfc1的祖先部件或是partIfc1本身。
     * @param partIfc1 :QMPartIfc 指定的零部件的值对象。
     * @param partIfc2 :QMPartIfc 被检验的零部件的值对象。
     * @return flag:boolean。
     * flag==true:零部件part2是零部件part1的祖先部件或是part1本身。
     * flag==false:零部件part2不是零部件part1的祖先部件，也不是part1本身。
     * @throws QMException
     */
    public boolean isParentPart(QMPartIfc partIfc1, QMPartIfc partIfc2) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "isParentPart begin ....");
        QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)partIfc1.getMaster();
        QMPartMasterIfc partMasterIfc2 = (QMPartMasterIfc)partIfc2.getMaster();
        if (partMasterIfc1.getBsoID().equals(partMasterIfc2.getBsoID()))
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "isParentPart end....return is true");
            return true;
        }
        Vector temp = getAllParentParts(partIfc1);
        //如果partIfc1没有父亲节点，说明partIfc2永远不可能是partIfc1的父亲节点，所以方法
        //永远返回false
        if(temp == null || temp.size() == 0)
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "isParentPart end....return is false");
            return false;
        }
        for(int i=0; i<temp.size(); i++)
        {
            String bsoID1 = partMasterIfc2.getBsoID();
            String bsoID2 = ((QMPartMasterIfc)temp.elementAt(i)).getBsoID();
            //如果partMasterIfc2的BsoID和partIfc1的某个父亲节点的BsoID相等，返回true;
            if(bsoID1.equals(bsoID2))
            {
                PartDebug.trace(this, PartDebug.PART_SERVICE, "isParentPart end....return is true");
                return true;
            }
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "isParentPart end....return is false");
        return false;
    }

    /**
     * 获得指定零部件的所有父部件的主信息值对象集合。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @return vector:Vector 指定零部件的所有父部件(直到根部件)的主信息值对象的集合。
     * @throws QMException
     */
    public Vector getAllParentParts(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllParentParts begin ....");
        Vector tempresult = getParentParts(partIfc);
        Vector result = new Vector();
        if(tempresult != null)
        {
            for (int i=0; i<tempresult.size(); i++)
            {
                QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)((QMPartIfc)tempresult.elementAt(i)).getMaster();
                if(partMasterIfc1 != null)
                    result.addElement(partMasterIfc1);
                Vector temp = getAllParentParts((QMPartIfc)tempresult.elementAt(i));
                for (int j = 0; j<temp.size(); j++)
                {
                    result.addElement(temp.elementAt(j));
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllParentParts end....return is Vector");
        return result;
    }
    /**
     * 20070611 add whj for new request moth QMProductManager.isParentPart()
     * 获得指定零部件的所有父部件的主信息值对象集合。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @return vector:Vector 指定零部件的所有父部件(直到根部件)的主信息值对象的集合。
     * @throws QMException
     */
    public Vector getAllParentsByPart(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllParentParts begin ....");
        Vector tempresult = getParentsByPart(partIfc);
        Vector result = new Vector();
        if(tempresult != null)
        {
            for (int i=0; i<tempresult.size(); i++)
            {
                QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)((QMPartIfc)tempresult.elementAt(i)).getMaster();
                if(partMasterIfc1 != null)
                    result.addElement(partMasterIfc1);
                Vector temp = getAllParentsByPart((QMPartIfc)tempresult.elementAt(i));
                for (int j = 0; j<temp.size(); j++)
                {
                    result.addElement(temp.elementAt(j));
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllParentParts end....return is Vector");
        return result;
    }

    /**
     * 获取所有直接使用当前零件的零部件，
     * 即根据当前的传入的零件QMPartIfc找到对应的PartMaster，
     * 再调用getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * 方法找到所有的使用该零件的集合。
     * @param partIfc QMPartIfc
     * @throws QMException
     * @return Vector
     */
    public Vector getParentParts(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentParts begin ....");
        QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)partIfc.getMaster();
        Collection collection = getUsedByParts(partMasterIfc);
        Vector result = new Vector();
        Vector vector = new Vector();
        //collection中应该是QMPartIfc的集合
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof QMPartIfc)
                {
                    //在查看被用于界面，如果一个父件使用一个子件多次，则只需列出一条记录，不必每次都列出
                    QMPartIfc partIfc2 = (QMPartIfc)obj;
                    String string = partIfc2.getPartNumber() + partIfc2.getVersionValue();
                    if(!vector.contains(string))
                    {
                      vector.addElement(string);
                      result.addElement( partIfc2 );
                    }
                }
            }
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentParts end....return is Vector");
            return result;
        }
        else
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentParts end....return is null");
            return null;
        }
    }
    /**
     *  20070611 add whj for new request moth QMProductManager.isParentPart()
     * 获取所有直接使用当前零件的零部件，
     * 即根据当前的传入的零件QMPartIfc找到对应的PartMaster，
     * 再调用getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * 方法找到所有的使用该零件的集合。
     * @param partIfc QMPartIfc
     * @throws QMException
     * @return Vector
     */
    public Vector getParentsByPart(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentParts begin ....");
        QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)partIfc.getMaster();
        Collection collection = getUsedByPParts(partMasterIfc);
        Vector result = new Vector();
        Vector vector = new Vector();
        //collection中应该是QMPartIfc的集合
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof QMPartIfc)
                {
                    //在查看被用于界面，如果一个父件使用一个子件多次，则只需列出一条记录，不必每次都列出
                    QMPartIfc partIfc2 = (QMPartIfc)obj;
                    String string = partIfc2.getPartNumber() + partIfc2.getVersionValue();
                    if(!vector.contains(string))
                    {
                      vector.addElement(string);
                      result.addElement( partIfc2 );
                    }
                }
            }
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentParts end....return is Vector");
            return result;
        }
        else
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentParts end....return is null");
            return null;
        }
    }

    /**
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
     * 本方法调用了bianli()方法实现递归。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，是（否）可分（"true","false"）、编号、名称、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，是（否）可分、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
     * @throws QMException
     * @return Vector
     */
    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames,
                             String[] affixAttrNames, String source, String type,
                             PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "setBOMList begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Vector vector = new Vector();
        float parentQuantity = 1.0f;

        //记录数量和编号在排序中所在的位置。
        int quantitySite = 0;
        int numberSite = 0;
        for (int i=0; i<attrNames.length; i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if (attr != null && attr.length() > 0)
            {
                if (attr.equals("quantity"))
                {
                    quantitySite = 3 + i;
                }
                if (attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }
            }
        }
        PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
        vector = bianli(partIfc, attrNames, affixAttrNames, source, type,
                        configSpecIfc, parentQuantity, partLinkIfc);
        //下面对vector中的元素进行合并数量的处理。...........
        Vector resultVector = new Vector();
        for (int i=0; i<vector.size(); i++)
        {
            Object[] temp1 = (Object[])vector.elementAt(i);
            //2003.09.12为了防止"null"进入到返回值中：可以对temp1中的每个元素判断
            //其是否为null, 如果是null，就转化为""
            for(int j=0; j<temp1.length; j++)
            {
                if(temp1[j] == null)
                {
                    temp1[j] = "";
                }
            }
            //需求是按照partNumber进行合并的！！！
            String partNumber1 = (String)temp1[numberSite];
            boolean flag = false;
            for (int j=0; j<resultVector.size(); j++)
            {
                Object[] temp2 = (Object[])resultVector.elementAt(j);
                String partNumber2 = (String)temp2[numberSite];
                if (partNumber1.equals(partNumber2))
                {
                    flag = true;

                    //如果数量的位置大于0，说明输出的属性中有数量，然后将相同零部件
                    //的数量合并。
                    if(quantitySite>0)
                    {
                        //把temp2和temp1中的元素进行合并，放到resultVector中去。:::
                        float float1 = (new Float(temp1[quantitySite].toString())).
                                       floatValue();
                        float float2 = (new Float(temp2[quantitySite].toString())).
                                       floatValue();
                        String tempQuantity = String.valueOf(float1 + float2);
                        if (tempQuantity.endsWith(".0"))
                            tempQuantity = tempQuantity.substring(0,
                                    tempQuantity.length() - 2);
                        temp1[quantitySite] = tempQuantity;
                    }
                    resultVector.setElementAt(temp1, j);
                    break;
                }
                //end if (partNumber1.equals(partNumber2))
            }
            //end for (int j=0; j<resultVector.size(); j++)
            if(flag == false)
            {
                resultVector.addElement(temp1);
            }
            //end if(flag == false)
        }
        //end for (int i=0; i<vector.size(); i++)

        //需要对第一个元素进行判断，如果其，source，type都和输入的source, type相同的
        //就保留，否则，删除掉。
        //其实是由partIfc决定的:::
        boolean flag1 = false;
        boolean flag2 = false;
        String source1 = (partIfc.getProducedBy()).toString();
        String type1 = (partIfc.getPartType()).toString();
        if(source != null && source.length() > 0)
        {
            if(source.equals(source1))
            {
                flag1 = true;
            }
        }
        else
        {
            flag1 = true;
        }
        if(type != null && type.length() > 0)
        {
            if(type.equals(type1))
            {
                flag2 = true;
            }
        }
        else
        {
            flag2 = true;
        }
        if(!flag1 || !flag2)
        {
            resultVector.removeElementAt(0);
        }
        else
        {
            //把第一个元素的数量改成""
            Object[] firstObj = (Object[])resultVector.elementAt(0);

            //如果输出属性有数量，则将数量设置为空。
            if(quantitySite>0)
            {
                firstObj[quantitySite] = "";
            }
            resultVector.setElementAt(firstObj, 0);
        }
        //这里才保存最后最后的结果：
        Vector result = new Vector();
        //然后，这里还需要对最后的返回值集合按照当前的source和type进行过滤：
        for(int i=0; i<resultVector.size(); i++)
        {
            Object[] element = (Object[])resultVector.elementAt(i);
            QMPartIfc onePart = (QMPartIfc)pService.refreshInfo((String)element[0]);
            boolean flag11 = false;
            boolean flag22 = false;
            if(source != null && source.length() > 0)
            {
                if(onePart.getProducedBy().toString().equals(source))
                {
                    flag11 = true;
                }
            }
            else
            {
                flag11 = true;
            }
            if(type != null && type.length() > 0)
            {
                if(onePart.getPartType().toString().equals(type))
                {
                    flag22 = true;
                }
            }
            else
            {
                flag22 = true;
            }
            if(flag11 && flag22)
            {
                result.addElement(element);
            }
        }
        //还需要向该vector中添加一个元素：
        Vector firstElement = new Vector();
        firstElement.addElement("");
        firstElement.addElement("");
        String ssss = "";
//        String ssss = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
//        firstElement.addElement(ssss);
//        ssss = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
//        firstElement.addElement(ssss);
        ssss = QMMessage.getLocalizedMessage(RESOURCE, "isHasSubParts", null);
        firstElement.addElement(ssss);
//        ssss = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);
//        firstElement.addElement(ssss);
        //下面需要通过判断来确定firstElement的值:
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        //如果定制的普通属性为空：
        if(attrNullTrueFlag)
        {
            //如果定制的扩展属性也为空：
            if(affixAttrNullTrueFlag)
            {
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
//                firstElement.addElement(ssss);
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
//                firstElement.addElement(ssss);
            }
        }
        //如果定制的普通属性不为空的话：
        else
        {
            for(int i=0; i<attrNames.length; i++)
            {
                String attr = attrNames[i];
                ssss = QMMessage.getLocalizedMessage(RESOURCE, attr, null);
                firstElement.addElement(ssss);
            }
        }
        //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
        //再添加到vector中的第一个位置：
        Object[] tempArray = new Object[firstElement.size()];
        for(int i=0; i<firstElement.size(); i++)
        {
            tempArray[i] = firstElement.elementAt(i);
        }
        result.insertElementAt(tempArray, 0);
        return result;
    }

    /**
     * 本方法被setBOMList所调用，实现递归调用的功能。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，是（否）可分（"true","false"）、编号、名称、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，是（否）可分、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性的集合，可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
     * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量。
     * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象。
     * @throws QMException
     * @return Vector
     */
    private Vector bianli(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                         String source, String type, PartConfigSpecIfc configSpecIfc,
                         float parentQuantity,PartUsageLinkIfc partLinkIfc)
       throws QMException
   {
       //本方法的主要实现过程为:::
       //1：判断当前的零部件是否是可分的零部件，以方便在把该零部件放到最后结果集合中的时候，可以确定
       //该零部件的可分标志
       PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli begin ....");
       Vector resultVector = new Vector();
       //用来保存当前的零部件的所有合格的子零部件的集合：
       Vector hegeVector = new Vector();
       Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
       //这个时候就应该先判断collection是否是"null"
       if(collection != null && collection.size() > 0)
       {
           //需要对collection中的所有元素进行循环，如果有这样的元素
           //是QMPartIfc而且其来源和类型和输入的参数是一致的，
           //表明该输入的零部件是可分的.既是根据source, type来对子节点进行过滤:::
           Object[] resultArray = new Object[collection.size()];
           collection.toArray(resultArray);
           for (int i=0; i<resultArray.length; i++)
           {
               boolean isHasSubParts = true; //false
               Object obj = resultArray[i];
               if(obj instanceof Object[])
               {
                   Object[] obj1 = (Object[])obj;
                   if (obj1[1] instanceof QMPartIfc)
                   {
                       //这一步相当于增加了一个对当前零部件的所有儿子零部件的过滤条件.
                       if (isHasSubParts == true)
                       {
                           hegeVector.addElement(obj);
                       }
                       //end if(isHasSubParts == true)
                   }
                   //end if (obj1[1] instanceof QMPartIfc)
               }
               //end if(obj instanceof Object[])
           }
           //end for (int i=0; i<resultArray.length; i++)
       }
       //end if(collection != null && collection.size() > 0)

       //把本part->resultVector中;
       Object[] tempArray = null;
       boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
       boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
       if(attrNullTrueFlag)
       {
           //如果两个定制的属性集合都为空的时候：
           if(affixAttrNullTrueFlag)
           {
               tempArray = new Object[3];
//               tempArray = new Object[7];
           }
           //如果定制的普通属性为空，而定制的扩展属性不为空的时候：
           else
           {
               tempArray = new Object[3 + affixAttrNames.length];
           }
       }
       else
       {
           //如果定制的普通属性集合不为空，定制的扩展属性集合为空的时候：
           if(affixAttrNullTrueFlag)
           {
               tempArray = new Object[3 + attrNames.length];
           }
           //如果两个定制的属性集合都不为空的时候：
           else
           {
               tempArray = new Object[3 + affixAttrNames.length + attrNames.length];
           }
       }
       tempArray[0] = partIfc.getBsoID();
       int numberSite = 0;
        for (int i=0; i<attrNames.length; i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if (attr != null && attr.length() > 0)
            {
                if (attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }
            }
        }
        tempArray[1] = new Integer(numberSite);//编号属性所在的位。
//       tempArray[1] = partIfc.getPartNumber();
//       tempArray[2] = partIfc.getPartName();
       String isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "false", null);
       if(hegeVector != null && hegeVector.size() > 0)
       {
           isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "true", null);
       }
       tempArray[2] = isHasSubParts1;
       //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
       //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
//       if (partLinkIfc == null || !PersistHelper.isPersistent(partLinkIfc))
//       {
//           parentQuantity = 1.0f;
//       }
//       else
//       {
//           parentQuantity = parentQuantity * partLinkIfc.getQuantity();
//       }
//       String tempQuantity = String.valueOf(parentQuantity);
//       if (tempQuantity.endsWith(".0"))
//           tempQuantity = tempQuantity.substring(0,
//                                                 tempQuantity.length() - 2);
//       tempArray[4] = tempQuantity;
       //下面需要根据两个定制的属性集合来对最后的结果集合进行组织：
       if (attrNullTrueFlag)
       {
           //当两个定制的属性集合都为空的时候：
           if(affixAttrNullTrueFlag)
           {
//               tempArray[5] = partIfc.getVersionValue();
//               if (partIfc.getViewName() == null ||
//                   partIfc.getViewName().length() == 0)
//               {
//                   tempArray[6] = "";
//               }
//               else
//               {
//                   tempArray[6] = partIfc.getViewName();
//               }
           }
           //结束：当定制的普通属性为空，而定制的扩展属性集合不为空的时候：
       }
       //上面结束：当定制的普通属性集合为空的时候。
       //下面开始：当定制的普通属性集合不为空的时候：
       else
       {
           //先把所有的普通属性的值放到tempArray中：
           for (int i=0; i<attrNames.length; i++)
           {
               String attr = attrNames[i];
               attr = attr.trim();
               if(attr != null && attr.length() > 0)
               {
                   //modify by liun 2005.3.25 改为从关联中得到单位
                   if(attr.equals("defaultUnit"))
                   {
                       Unit unit = partLinkIfc.getDefaultUnit();
                       if (unit != null)
                       {
                           tempArray[3 + i] = unit.getDisplay();
                       }
                       else
                       {
                           tempArray[3 + i] = "";
                       }
                   }
                   else if(attr.equals("quantity"))
                   {
                       //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
                       //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
                       if (partLinkIfc == null ||
                           !PersistHelper.isPersistent(partLinkIfc))
                       {
                           parentQuantity = 1.0f;
                       }
                       else
                       {
                           parentQuantity = parentQuantity *
                                            partLinkIfc.getQuantity();
                       }
                       String tempQuantity = String.valueOf(parentQuantity);
                       if (tempQuantity.endsWith(".0"))
                           tempQuantity = tempQuantity.substring(0,
                                   tempQuantity.length() - 2);
                       tempArray[3 + i] = tempQuantity;
                   }
                   else
                   {
                       attr = (attr.substring(0, 1)).toUpperCase() +
                           attr.substring(1, attr.length());
                       attr = "get" + attr;
                       //现在的attr就是"getProducedBy"等固定的字符串了：
                       try
                       {
                           Class partClass = Class.forName(
                               "com.faw_qm.part.model.QMPartInfo");
                           Method method1 = partClass.getMethod(attr, null);
                           Object obj = method1.invoke(partIfc, null);
                           //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
                           //如果obj不为null, 而且是String, tempArray[i + 5] = (String)obj;
                           //如果obj是枚举类型，tempArray[i + 5] = (EnumerationType)obj.getDisplay();
                           if (obj == null)
                           {
                               tempArray[i + 3] = "";
                           }
                           else
                           {
                               if (obj instanceof String)
                               {
                                   String tempString = (String) obj;
                                   if (tempString != null &&
                                       tempString.length() > 0)
                                   {
                                       tempArray[i + 3] = tempString;
                                   }
                                   else
                                   {
                                       tempArray[i + 3] = "";
                                   }
                               }
                               else
                               {
                                   if (obj instanceof EnumeratedType)
                                   {
                                       EnumeratedType tempType = (
                                           EnumeratedType)
                                           obj;
                                       if (tempType != null)
                                       {
                                           tempArray[i +
                                               3] = tempType.getDisplay();
                                       }
                                       else
                                       {
                                           tempArray[i + 3] = "";
                                       }
                                   }
                               }
                           }
                       }
                       catch (Exception ex)
                       {
                           ex.printStackTrace();
                           throw new QMException(ex);
                       }
                   }
               }
           }
           //end for (int i=0; i<attrNames.length; i++)
       }
       //end if and else if (attrNames == null || attrNames.length == 0)
       resultVector.addElement(tempArray);
       //对已经过滤处理的当前输入的零部件的所有子零部件进行递归处理::::
       if (hegeVector != null && hegeVector.size() > 0)
       {
           for (int j=0; j<hegeVector.size(); j++)
           {
               Object obj = hegeVector.elementAt(j);
               if(obj instanceof Object[])
               {
                   Object[] obj2 = (Object[])obj;
                   if ((obj2[0] != null)&&(obj2[1] != null))
                   {
                       Vector tempVector = bianli((QMPartIfc)obj2[1], attrNames,
                           affixAttrNames, source, type, configSpecIfc,
                           parentQuantity, (PartUsageLinkIfc)obj2[0]);
                       for (int k=0; k<tempVector.size(); k++)
                           resultVector.addElement(tempVector.elementAt(k));
                   }
               }
           }
       }
       PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli end....return is Vector ");
       return resultVector;
   }


    /**
     * 分级物料清单的显示。
     * 返回结果是vector,其中vector中的每个元素都是一个集合：
     * 0：当前part的BsoID；
     * 1：当前part所在的级别，转化为字符型；
     * 2-...：可变的：如果没有定制属性，2：当前part的编号，3：当前part的名称
     *                              4：当前part被最顶层部件使用的数量，转化为字符型，
     *                              5：当前part的版本号，6：当前part的视图；
     *               如果定制了属性：按照所有定制的属性加到结果集合中。
     * 本方法调用了递归方法：
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc 最顶级的部件值对象。
     * @param attrNames :String[] 定制的属性，可以为空。
     * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames,
                                  String[] affixAttrNames,
                                  PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "setMaterialList begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        int level = 0;
        PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
        float parentQuantity = 1.0f;

        //记录数量和编号在排序中所在的位置。
        int quantitySite = 0;
        int numberSite = 0;
        for (int i=0; i<attrNames.length; i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if (attr != null && attr.length() > 0)
            {
                if (attr.equals("quantity"))
                {
                    quantitySite = 3 + i;
                }
                if (attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }
            }
        }
        Vector vector = null;
        vector = fenji(partIfc, attrNames, affixAttrNames, configSpecIfc,
                       level, partLinkIfc, parentQuantity);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "setMaterialList end....return is Vector");
        //把结果集合中的第一个元素的使用的数量变成""
        if(vector != null && vector.size() > 0)
        {
            Object[] first = (Object[])vector.elementAt(0);

            //如果输出属性有数量，则将数量设置为空。
            if(quantitySite>0)
            {
                first[quantitySite] = "";
            }
            vector.setElementAt(first, 0);
        }
        //还需要向该vector中添加一个元素：
        Vector firstElement = new Vector();
        firstElement.addElement("");
        firstElement.addElement("");
        String ssss = QMMessage.getLocalizedMessage(RESOURCE, "level", null);
        firstElement.addElement(ssss);
//        ssss = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
//        firstElement.addElement(ssss);
//        ssss = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
//        firstElement.addElement(ssss);
//        ssss = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);
//        firstElement.addElement(ssss);
        //下面需要通过判断来确定firstElement的值:
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        if(attrNullTrueFlag)
        {
            if(affixAttrNullTrueFlag)
            {
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
//                firstElement.addElement(ssss);
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
//                firstElement.addElement(ssss);
            }
        }
        else
        {
            for(int i=0; i<attrNames.length; i++)
            {
                String attr = attrNames[i];
                ssss = QMMessage.getLocalizedMessage(RESOURCE, attr, null);
                firstElement.addElement(ssss);
            }
        }
        //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
        //再添加到vector中的第一个位置：
        Object[] tempArray = new Object[firstElement.size()];
        for(int i=0; i<firstElement.size(); i++)
        {
            tempArray[i] = firstElement.elementAt(i);
        }
        vector.insertElementAt(tempArray, 0);
        //2003.09.12为了防止"null"进入到返回值中：可以对vector中的每个元素判断
        //其是否为null, 如果是null，就转化为""
        for(int i=0; i<vector.size(); i++)
        {
            Object[] temp = (Object[])vector.elementAt(i);
            for(int j=0; j<temp.length; j++)
            {
                if(temp[j] == null)
                {
                    temp[j] = "";
                }
            }
        }
        return vector;
    }

    /**
     * 私有方法。被setMaterialList()方法调用，实现定制分级物料清单的功能。
     * 返回结果是vector,其中vector中的每个元素都是一个集合：
     * 0：当前part的BsoID；
     * 1：当前part所在的级别，转化为字符型；
     * 2-...：可变的：如果没有定制属性，2：当前part的编号，3：当前part的名称
     *                              4：当前part被最顶层部件使用的数量，转化为字符型，
     *                              5：当前part的版本号，6：当前part的视图；
     *               如果定制了属性：按照所有定制的属性加到结果集合中。
     * @param partIfc :QMPartIfc 当前的部件。
     * @param attrNames :String[] 定制的属性集合，可以为空。
     * @param affixAttrNames :String[] 定制的扩展属性名集合，可以为空。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。
     * @param level :int 当前part所在的级别。
     * @param partLinkIfc :PartUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象。
     * @param parentQuantity :int 当前part的父亲节点被最顶级部件使用的数量。
     * @throws QMException
     * @return Vector
     */

    private Vector fenji(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
        PartConfigSpecIfc configSpecIfc, int level, PartUsageLinkIfc partLinkIfc,
        float parentQuantity) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji begin ....");
        //保存最后结果值。
        Vector resultVector = new Vector();
        Object[] tempArray = null;
        //标识定制的普通属性是否为空，如果为空，该标识为真，否则为假：
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        //标识定制的扩展属性是否为空，如果为空，该标识为真，否则为假：
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        //这个字符串数组中保存的是"头信息":
        if(attrNullTrueFlag)
        {
            if(affixAttrNullTrueFlag)
            {
                //如果两个的定制的属性集合都为空的话：
                tempArray = new Object[3];
//                tempArray = new Object[7];
            }
            else
            {
                //如果只有定制的扩展属性不为空的时候：
                tempArray = new Object[3 + affixAttrNames.length];
            }
        }
        else
        {
            if(affixAttrNullTrueFlag)
            {
                //如果只有定制的普通属性不为空的时候：
                tempArray = new Object[3 + attrNames.length];
            }
            else
            {
                //如果两个定制的属性集合都不为空的时候：
                tempArray = new Object[3 + affixAttrNames.length + attrNames.length];
            }
        }
        //end if and else (attrNames == null || attrNames.length == 0)
        tempArray[0] = partIfc.getBsoID();
        int numberSite = 0;
        for (int i=0; i<attrNames.length; i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if (attr != null && attr.length() > 0)
            {
                if (attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }
            }
        }
        tempArray[1] = new Integer(numberSite);//编号属性所在的位。
        tempArray[2] = new Integer(level);//level的初始值为0。
//        tempArray[2] = partIfc.getPartNumber();
//        tempArray[3] = partIfc.getPartName();
        //如果level = 0 说明是最顶级的部件。
       /**if (level == 0)
        {
            parentQuantity = 1f;
            String quan = "1";
            tempArray[4] = new String(quan);
        }
        else
        {
            //可不可以这样：不再保存parentBsoID,而是保存PartUsageLinkIfc，到循环参数中来。
            //这样可以省略再查找的过程。QMPartUsageLinkIfc partLinkIfc
            parentQuantity = partLinkIfc.getQuantity();//parentQuantity*partLinkIfc.getQuantity();
            String tempQuantity = String.valueOf(parentQuantity);
            if (tempQuantity.endsWith(".0"))
                tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
            tempArray[4] = tempQuantity;
        }*/
        //判断是否需要定制属性进行输出。
        if (attrNullTrueFlag)
        {
            //如果两个定制的属性集合都为空的话：
            if(affixAttrNullTrueFlag)
            {
//                tempArray[5] = partIfc.getVersionValue();
//                if (partIfc.getViewName() == null ||
//                    partIfc.getViewName().length() == 0)
//                {
//                    tempArray[6] = "";
//                }
//                else
//                {
//                    tempArray[6] = partIfc.getViewName();
//                }
            }
        }
        //结束：如果定制的普通属性为空的时候。
        //下面：如果定制的普通属性不为空的时候。
        else
        {
            //对定制的普通属性进行循环：
            for (int j=0; j<attrNames.length; j++)
            {
                String attr = attrNames[j];
                attr = attr.trim();
                if(attr != null && attr.length() > 0)
                {
                    //modify by liun 2005.3.25 改为从关联中得到单位
                    String temp = tempArray[1].toString();
                    if(attr.equals("defaultUnit")&&!temp.equals("0"))
                    {
                        Unit unit = partLinkIfc.getDefaultUnit();
                        if (unit != null)
                        {
                            tempArray[3 + j] = unit.getDisplay();
                        }
                        else
                        {
                            tempArray[3 + j] = "";
                        }
                    }
                    else if(attr.equals("quantity"))
                    {
                        //如果level = 0 说明是最顶级的部件。
                        if (level == 0)
                        {
                            parentQuantity = 1f;
                            String quan = "1";
                            tempArray[3 + j] = new String(quan);
                        }
                        else
                        {
                            //可不可以这样：不再保存parentBsoID,而是保存PartUsageLinkIfc，到循环参数中来。
                            //这样可以省略再查找的过程。QMPartUsageLinkIfc partLinkIfc
                            parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
                            String tempQuantity = String.valueOf(parentQuantity);
                            if (tempQuantity.endsWith(".0"))
                                tempQuantity = tempQuantity.substring(0,
                                        tempQuantity.length() - 2);
                            tempArray[3 + j] = tempQuantity;
                        }
                    }
                    else
                    {
                        attr = (attr.substring(0, 1)).toUpperCase() +
                            attr.substring(1, attr.length());
                        attr = "get" + attr;
                        //现在的attr就是"getProducedBy"等固定的字符串了：
                        try
                        {
                            Class partClass = Class.forName(
                                "com.faw_qm.part.model.QMPartInfo");
                            Method method1 = partClass.getMethod(attr, null);
                            Object obj = method1.invoke(partIfc, null);
                            //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
                            //如果obj不为null, 而且是String, attrNames[i] = (String)obj;
                            //如果obj是枚举类型，attrNames[i] = (EnumerationType)obj.getDisplay();
                            if (obj == null)
                            {
                                tempArray[3 + j] = "";
                            }
                            else
                            {
                                if (obj instanceof String)
                                {
                                    String tempString = (String) obj;
                                    if (tempString != null &&
                                        tempString.length() > 0)
                                    {
                                        tempArray[3 + j] = tempString;
                                    }
                                    else
                                    {
                                        tempArray[3 + j] = "";
                                    }
                                }
                                else
                                {
                                    if (obj instanceof EnumeratedType)
                                    {
                                        EnumeratedType tempType = (
                                            EnumeratedType)
                                            obj;
                                        if (tempType != null)
                                        {
                                            tempArray[3 +
                                                j] = tempType.getDisplay();
                                        }
                                        else
                                        {
                                            tempArray[3 + j] = "";
                                        }
                                    }
                                }
                            }
                            //end if(obj == null)
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            throw new QMException(ex);
                        }
                    }
                }
            }
            //end for (int j=0; j<attrNames.length; j++)
        }
        //end else (attrNames == null)
        resultVector.addElement(tempArray);
        Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
        if ((collection == null)||(collection.size() == 0))
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return is Vector");
            return resultVector;
        }
        else
        {
            Object[] temp = (Object[])collection.toArray();
            level++;
            for (int k=0; k<temp.length; k++)
            {
                if(temp[k] instanceof Object[])
                {
                    Object[] obj = (Object[])temp[k];
                    //取temp中的元素进行循环，temp[k][0]是PartUsageLinkIfc,
                    //temp[k][1]是QMPartIfc
                    Vector tempResult = new Vector();
                    if(obj[1] instanceof QMPartIfc && obj[0] instanceof PartUsageLinkIfc)
                    {
                        tempResult = fenji( (QMPartIfc) obj[1], attrNames,
                                            affixAttrNames,
                                           configSpecIfc, level,
                                           (PartUsageLinkIfc) obj[0],
                                           parentQuantity);
                    }
                    for (int m=0; m<tempResult.size(); m++)
                    {
                        resultVector.addElement(tempResult.elementAt(m));
                    }
                }
                //end if(temp[k] instanceof Object[])
            }
            //end for (int k=0; k<temp.length; k++)
            level--;
            PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return Vector ");
            return resultVector;
        }
    }

    /**
     * 根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。
	 * 返回值为vector，它的每个元素都是String[2]类型的。 <br>
	 * 这个String[2]分别记录了:String[0]：零部件值对象；String[1]：html格式的图标信息。
     * @param partIfc :QMPartIfc 指定的零部件值对象。
     * @param configSpecIfc :PartConfigSpecIfc 指定的筛选条件。
     * @return vector:Vector 被其他部件所使用的信息集合。
     * @throws QMException
     */
    public Vector setUsageList(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException//Begin CR1 重写服务端逻辑
{
    Vector resultVector = new Vector();
    pcon = new PartConfigSpecAssistant(configSpecIfc);
    Collection parentPartList = usage2(partIfc, configSpecIfc).values();
    //需要对parentPartList进行处理，如果parentPartList中只有一个元素，而且是partIfc，就清空:
    //CR6 begin 
    if (parentPartList.size() > 0) 
    {
        for (Iterator iterator = parentPartList.iterator(); iterator.hasNext();) 
        {
            BaseValueInfo partInfo = (BaseValueInfo) iterator.next();
            Object[] objects = new Object[2];
            objects[0] = partInfo;
            objects[1] = DocServiceHelper.getObjectImageHtml(partInfo);
            //CCBegin by liunan 2011-04-20 打补丁v4r3_p032
            // CR9begin
            //resultVector.add(objects);           
            String partBsoid=partIfc.getBsoID();
            String parentPartBsoid=partInfo.getBsoID();
            //if (resultVector.size() ==1&&partBsoid.equals(parentPartBsoid)){
            //	resultVector.removeElementAt(0);
            //}
            // 如果集合中存在自己本身，就不被添加到结果集中
            if(!partBsoid.equals(parentPartBsoid))
            {
            	resultVector.add(objects);
            }
            //CR9 end
            //CCEnd by liunan 2011-04-20
//CR6 end            
        }
    }
    return resultVector;
}//End CR1 重写服务端逻辑

    /**
     * 被setUsageList所调用的方法，实现递归调用。
     * 根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。
     * 使用结果保存在返回值vector中。
     * @param partIfc :QMPartIfc指定的零部件值对象。
     * @param configSpecIfc :PartConfigSpecIfc指定的筛选条件。
     * @param  partLinkIfc :PartUsageLinkIfc 记录当前的partIfc和其当前的父亲节点的使用关系。
     * @param vector :Vector 按被使用的关系的PartIfc的集合，即数组的前一个元素被后一个元素使用。
     * @return vector:Vector被其他部件所使用的信息集合，每个元素为一个字符传数组String[4]:：
     * String[0]:层次号；
     * String[1]:零部件编号(零部件名称) 版本(视图)
     * String[2]:零部件在此结构中（顶级件中）使用的数量，所以在同一结构下的记录使用数量是相同的，零部件下同一子件的使用数量合并。
     * String[3]:零部件的BsoID
     * @throws QMException
     */
    private Vector usage(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc,
        PartUsageLinkIfc partLinkIfc, Vector vector,PartConfigSpecAssistant pcon) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "usage begin ....");
        Vector resultVector = new Vector();
        //需要修改vector中保存元素的数据结构:(A)--->(A, partUsageLink)
        //因为如果是(A)的话，无法合理的利用该方法的参数partLinkIfc的使用数量了。
        //最顶层的节点的parUsageLinkIfc是，字符串："null"
        //可能用户只需要最底层节点被最顶层的节点的使用的数量。这样的话，最后只需要返回的是这个值。
        Object[] obj1 = new Object[2];
        obj1[0] = partIfc;
        if(partLinkIfc == null || !(PersistHelper.isPersistent(partLinkIfc)))
            obj1[1] = "null";
        else
            obj1[1] = partLinkIfc;
        vector.addElement(obj1);
        Collection coll1 = getParentPartsByConfigSpec(partIfc, configSpecIfc);
        //coll1为空的时候，相当于说一个分支已经结束了。
        //可以把vector中的所有元素加到mainVector中了。
        if ((coll1 == null)||(coll1.size() == 0))
        {
            //把vector中的所有元素按从后向前的顺序存放到mainVector中,先放到tempArray中。
            Vector tempArray = new Vector();
            int tempSize = vector.size();
            for (int i=0; i<tempSize; i++)
            {
                tempArray.addElement(vector.elementAt(tempSize-i-1));
            }
            Vector subVector = new Vector();
            for (int i=0; i<tempArray.size(); i++)
            {
                String[] temp = new String[4];
                Object obj2 = tempArray.elementAt(i);
                if(obj2 instanceof Object[])
                {
                    Object[] obj3 = (Object[])obj2;
                    //保存的相当于级别，从0开始:
                    temp[0] = (new Integer(i)).toString();

                    //2003/12/20
                    if(((QMPartIfc)obj3[0]).getViewName()==null)
                    temp[1] = ((QMPartIfc)obj3[0]).getPartNumber()+"("+((QMPartIfc)obj3[0]).getPartName()
                        +")"+((QMPartIfc)obj3[0]).getVersionValue();
                    else
                      temp[1] = ((QMPartIfc)obj3[0]).getPartNumber()+"("+((QMPartIfc)obj3[0]).getPartName()
                                              +")"+((QMPartIfc)obj3[0]).getVersionValue()+"("+((QMPartIfc)obj3[0]).getViewName()+")";

                    if (i == 0)
                    {
                        temp[2] = "1";
                    }
                    else
                    {
                        Object[] obj22 = (Object[])tempArray.elementAt(i-1);
                        float f2 = ((PartUsageLinkIfc)obj22[1]).getQuantity();
                        temp[2] = (new Float(f2)).toString();
                        //add by skx 2004.5.7
                        if(temp[2].endsWith(".0")){
                          temp[2] = temp[2].substring(0,temp[2].length()-2);
                        }
                    }
                    temp[3]=((QMPartIfc)obj3[0]).getBsoID();
                }
                subVector.addElement(temp);
            }
            resultVector.addElement(subVector);
            vector.remove(tempSize-1);
            return resultVector;
        }
        else
        {
            Object[] temp1 = new Object[coll1.size()];
            coll1.toArray(temp1);
            //CCBegin by liunan 2008-10-4 打补丁200816
            //问题(1)20080811 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
            HashMap hashMap=new HashMap();
            for (int i=0; i<temp1.length; i++)
            {
                Object[] obj11 = (Object[]) temp1[i];
				PartUsageLinkIfc partUsageLink = (PartUsageLinkIfc) obj11[0];
				if (hashMap.containsKey(partUsageLink.getRightBsoID())) {
					String quantityStr = (String) hashMap.get(partUsageLink
							.getRightBsoID());
					float quantity = Float.parseFloat(quantityStr);
					hashMap.put(partUsageLink.getRightBsoID(), Float
							.toString(partUsageLink.getQuantity() + quantity));
				} else {
					hashMap.put(partUsageLink.getRightBsoID(), Float
							.toString(partUsageLink.getQuantity()));
				}
            }
            for (int i=0; i<temp1.length; i++)
            {
                Object[] obj11 = (Object[])temp1[i];
                PartUsageLinkIfc partUsageLink=(PartUsageLinkIfc)obj11[0];
                if(hashMap.containsKey(partUsageLink.getRightBsoID())){
                	String quantityStr=(String)hashMap.get(partUsageLink.getRightBsoID());
                	partUsageLink.setQuantity(Float.parseFloat(quantityStr));
                	hashMap.remove(partUsageLink.getRightBsoID());
                }
                else{
                   continue;
                }
                Vector vector1 = usage((QMPartIfc)obj11[1], configSpecIfc, partUsageLink, vector,pcon);
                for(int j=0; j<vector1.size(); j++)
                {
                    Object obj = vector1.elementAt(j);
                    resultVector.addElement(obj);
                }
            }
            //问题(1)20080811 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
            //CCEnd by liunan 2008-10-4
            int tempSize1 = vector.size();
            vector.remove(tempSize1-1);
            PartDebug.trace(this, PartDebug.PART_SERVICE, "usage end....return is Vector ");
            return resultVector;
        }
    }
    
    /**
     * 被setUsageList所调用的方法，实现递归调用。
     * 根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。
     * 使用结果保存在返回值HashMap中。
     * @param partIfc QMPartIfc指定的零部件值对象。
     * @param configSpecIfc PartConfigSpecIfc指定的筛选条件。
     * @return 被其他部件所使用的信息集合。HashMap中键：bsoid；值：值对象。
     * @throws QMException
     */    
    private HashMap usage2(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException//Begin CR1  重写服务端逻辑
    {
        HashMap resultMap = new HashMap();
        Collection srcParentPartList = navigateUsedByToIteration((QMPartMasterIfc) partIfc.getMaster(), pcon);
        ArrayList parentPartList = new ArrayList();
        //CCBegin by liunan 2011-04-20 打补丁v4r3_p032
        //CR9 begin
        PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
        //CCBegin by liunan 2011-04-20
        if ((srcParentPartList != null) && (srcParentPartList.size() > 0)) 
        {
            for (Iterator iterator = srcParentPartList.iterator(); iterator.hasNext();) 
            {
                Object object = (Object) iterator.next();
                //只返回是QMPartIfc的对象。
                if (object instanceof QMPartIfc)
                //CCBegin by liunan 2011-04-20 打补丁v4r3_p032
                //CR9 begin
                {
                	//源码
                    //parentPartList.add(object);
                    ArrayList PartmasterbsoID = new ArrayList();
                    // 把通过关联获得的父件在通过配置规范，找到符合配置规范的最新版本，QMpartIfc为找到的符合配置规范的最新版本
                    QMPartIfc QMpartIfc = getPartByConfigSpec((QMPartMasterIfc)(((QMPartIfc)object).getMaster()), configSpecIfc);
                    // 此处构造sql查找QMpartIfc的下一层子件
                    QMQuery query = new QMQuery("PartUsageLink");
                    QueryCondition condition = new QueryCondition("rightBsoID", "=", QMpartIfc.getBsoID());
                    query.addCondition(condition);
                    //CCBegin SS1
                    Vector linkAttr = new Vector();//Begin CR11
                    linkAttr.add("leftBsoID");
                    query.addAttribute(0, linkAttr);
                    query.setJdbc(true);//End CR11
                    //CCEnd SS1
                    // 找到QMpartIfc下一级子件的link
                    Collection link = (Collection)pService.findValueInfo(query);
                    // 如果子件不为空，将子件循环放入一个临时缓存中报损起来
                    if(link != null && link.size() != 0)
                    {
                        Iterator iter = link.iterator();
                        while(iter.hasNext())
                        {
                        	  //CCBegin SS1
                        	  //PartUsageLinkIfc mlink = (PartUsageLinkIfc)iter.next();
                            //String partbsoID = mlink.getLeftBsoID();
                        	  BaseValueIfc[] mlink = (BaseValueIfc[])iter.next();
                            String partbsoID = ((PartUsageLinkIfc)mlink[0]).getLeftBsoID();
                            //CCEnd SS1
                            PartmasterbsoID.add(partbsoID);
                        }
                    }
                    // 如果子件集合中存在原有的目标件，证明现在找的父件是正确，他们的关联还存在，进行下一步查找，否则证明关联已经被断开了，查找结束
                    if(PartmasterbsoID.contains(partIfc.getMasterBsoID()))
                    {
                        parentPartList.add(object);
                    }else
                    {
                        resultMap.put(partIfc.getBsoID(), partIfc);
                        continue;
                    }
                }
                //CR9 end
                //CCBegin by liunan 2011-04-20
            }
        }
        //parentPartList为空的时候，相当于说一个分支已经结束了。可以把返回结果了。
        if (parentPartList.size() == 0) 
        {
            resultMap.put(partIfc.getBsoID(),partIfc);
            return resultMap;
        } 
        else 
        {
            for (Iterator parentPart = parentPartList.iterator(); parentPart.hasNext();) 
            {
                Collection resultParentList = usage2((QMPartIfc) parentPart.next(), configSpecIfc).values();
                for (Iterator resultParentPart = resultParentList.iterator(); resultParentPart.hasNext();) 
                {
                    QMPartIfc resultPartIfc = (QMPartIfc) resultParentPart.next();
                    if (!resultMap.containsKey(resultPartIfc.getBsoID()))
                        resultMap.put(resultPartIfc.getBsoID(),resultPartIfc);
                }
            }
            return resultMap;
        }
    }//End CR1  重写服务端逻辑

    /**
     * 通过指定的配置规范，在数据库中寻找使用了(partIfc所对应的)PartMasterIfc的
     * 所有符合配置规范的部件(QMPartIfc)。
     * 返回值是以Object[] = (PartUsageLinkIfc, QMPartIfc)为元素的集合。
     * @param partIfc 零部件值对象。
     * @param partConfigSpecIfc 零部件配置规范。
     * @return Collection
     * @throws QMException
     */
    public Collection getParentPartsByConfigSpec(QMPartIfc partIfc, PartConfigSpecIfc partConfigSpecIfc)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentPartsByConfigSpec() begin ....");
        //调用navigateUsedByToIteration(partIfc, partConfigSpecIfc)
        //对结果集合进行过滤，只保留是QMPartIfc的对象
        Vector result = new Vector();
        if(pcon==null)
        {
          pcon=new PartConfigSpecAssistant(partConfigSpecIfc);
        }
        Collection collection = navigateUsedByToIteration((QMPartMasterIfc)partIfc.getMaster(), pcon);
        if((collection == null)||(collection.size() == 0))
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentPartsByConfigSpec() end....return is null");
            return null;
        }
        else
        {
            Object[] array = new Object[collection.size()];
            array = (Object[])collection.toArray(array);
            for(int i=0; i<array.length; i++)
            {
                if(array[i] instanceof QMPartIfc)
                    result.addElement(array[i]);
            }
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentPartsByConfigSpec() end....return is Vector");
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            Vector resultVector = new Vector();
            for(int i=0; i<result.size(); i++)
            {
                QMPartIfc parentPartIfc = (QMPartIfc)result.elementAt(i);
                //leftBsoID是被使用的零部件的QMPartMaster的BsoID
                //rightBsoID是使用者零部件的BsoID::
                String leftBsoID = partIfc.getMasterBsoID();
                String rightBsoID = parentPartIfc.getBsoID();
                //需要根据leftBsoID和rightBsoID找到PartUsageLinkIfc对象。应该只有一个：（不一定只有一个，因为有多次添加同一子件的情况 skx）
                Collection coll = pService.findLinkValueInfo("PartUsageLink", leftBsoID, "uses", rightBsoID);

                //modify by skx 若有多次添加的情况要在被用于产品界面中把每一条路径都显示出来
                if(coll != null && coll.size() > 0)
                {
                    Iterator iterator = coll.iterator();
                    while(iterator.hasNext())
                    {
                        PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc)iterator.next();
                        Object[] obj1 = new Object[2];
                        obj1[0] = usageLinkIfc;
                        obj1[1] = parentPartIfc;
                        resultVector.addElement(obj1);
                    }
                }
            }
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentPartsByConfigSpec() end....return is Vector");
            return resultVector;
        }
    }

    /**
     * 根据指定的配置规范为查询空间添加查询条件。
     * @param partConfigSpecIfc PartConfigSpecIfc 零部件配置规范值对象。
     * @param query QMQuery
     * @return QMQuery
     * @throws QMException
     * @throws QueryException
     */
    public QMQuery appendSearchCriteria(PartConfigSpecIfc partConfigSpecIfc, QMQuery query)
        throws QMException,QueryException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "appendSearchCriteria begin ....");
        if(!partConfigSpecIfc.getEffectivityActive() && !partConfigSpecIfc.getBaselineActive()
            && !partConfigSpecIfc.getStandardActive())
        {
            //"当前没有选定一个有效的筛选条件"
            throw new PartException(RESOURCE, "12", null);
        }
        PartConfigSpecAssistant assistant = new PartConfigSpecAssistant(partConfigSpecIfc);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "appendSearchCriteria end....return is QMQuery");
        return assistant.appendSearchCriteria(query);
    }

    /**
     * 根据配置规范过滤出符合配置规范的collection中所有QMPartMasterIfc的管理的QMPartIfc对象的小版本。
     * @param collection Collection 待处理的集合。
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     */
    public Collection filteredIterationsOf(Collection collection,
        PartConfigSpecIfc configSpecIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "filteredIterationsOf begin ....");
        ConfigService service = (ConfigService)EJBServiceHelper.getService("ConfigService");
        PartDebug.trace(this, PartDebug.PART_SERVICE, "filteredIterationsOf end....return is Collection");
        return service.filteredIterationsOf(collection,new PartConfigSpecAssistant(configSpecIfc));
    }
    /**
     * 查询当前用户的配置规范。
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    public PartConfigSpecIfc findPartConfigSpecIfc() throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartConfigSpecIfc begin ....");
        QMQuery query = new QMQuery("PartConfigSpec");
        SessionService service = (SessionService)EJBServiceHelper.getService("SessionService");
        String curUserID = service.getCurUserID();
        //CCBegin by liunan 2009-01-06 打补丁v3r11_p021_20090105
        if(configSpecCach.containsKey(curUserID))
        {
        	PartConfigSpecIfc partcsIfc=(PartConfigSpecIfc)configSpecCach.get(curUserID);

        	return partcsIfc;
        }
        else
        {
        //CCEnd by liunan 2009-01-06
        if(curUserID != null && curUserID.length() > 0)
        {
            QueryCondition condition1 = new QueryCondition("owner", "=", curUserID);
            query.addCondition(condition1);
        }
        //end if(curUserID != null && curUserID.length() > 0)
        PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection collection = pservice.findValueInfo(query);

        if((collection.size() == 0)||(collection == null))
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartConfigSpecIfc end....return is null ");
            return null;
        }
        if(collection.size() == 1)
        {

            PartConfigSpecIfc partcsIfc = (PartConfigSpecIfc)((collection.iterator()).next());
            //CCBegin by liunan 2009-01-06 打补丁v3r11_p021_20090105
            configSpecCach.put(curUserID, partcsIfc);
            //CCEnd by liunan 2009-01-06
            PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartConfigSpecIfc end....return is PartConfigSpecIfc ");
            return partcsIfc;
        }
        else
        {

            //"期望获得一个产品配置规范，但是查询结果获得多个数据"
            throw new PartException(RESOURCE, "2", null);
        }
        //CCBegin by liunan 2009-01-06 打补丁v3r11_p021_20090105
        }
        //CCEnd by liunan 2009-01-06
    }

    /**
     * 根据指定配置规范，获得指定部件的使用结构：
     * 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
     * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象，
     * 如果没有匹配对象，记录mastered对象。
     * @param partIfc 零部件值对象。
     * @param configSpecIfc 零部件配置规范。
     * @throws QMException
     * @return Collection
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartIfcs begin ....");
        Collection links = null;
        if(configSpecIfc == null)
        {
        	  configSpecIfc = findPartConfigSpecIfc();
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        if(partIfc.getBsoName().equals("GenericPart"))
          links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
        else if(partIfc.getBsoName().equals("QMPart"))
          links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartIfcs end....return is Collection");
        if(links==null||links.size()==0)
          return new Vector();
        return getUsesPartsFromLinks(links, configSpecIfc);
    }
    
    /**
     * 根据指定配置规范，获得指定部件的使用结构：
     * 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
     * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象，
     * 如果没有匹配对象，记录mastered对象。
     * @param partIfc 零部件值对象。
     * @return
     * @throws QMException
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException {//Begin CR4：重写服务端逻辑
        Collection links = null;
        if (pcon == null)
            pcon = new PartConfigSpecAssistant(findPartConfigSpecIfc());
        PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
        if (partIfc.getBsoName().equals("GenericPart"))
            links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
        else if (partIfc.getBsoName().equals("QMPart"))
            links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
        if (links == null || links.size() == 0)
            return new Vector();
        return getUsesPartsFromLinks(links);
    }//End CR4：重写服务端逻辑

    /**
     * 根据配置规范中包含的信息过滤结果集和。
     * @param partConfigSpecIfc PartConfigSpecIfc
     * @param collection Collection
     * @return Collection
     * @throws QMException
     * @throws QueryException
     */
    public Collection process(PartConfigSpecIfc partConfigSpecIfc, Collection collection)
        throws QMException,QueryException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "progress begin ....");
        if(!partConfigSpecIfc.getEffectivityActive() && !partConfigSpecIfc.getBaselineActive()
            && !partConfigSpecIfc.getStandardActive())
            throw new QMException(RESOURCE, "12", null);
        PartConfigSpecAssistant assistant = new PartConfigSpecAssistant(partConfigSpecIfc);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "progress end....return is Collection");
        return assistant.process(collection);
    }

    /**
     * 保存零部件的配置规范。
     * @param configSpecIfc PartConfigSpecIfc 待保存的零部件配置规范值对象。
     * @throws QMException
     * @return PartConfigSpecIfc 保存后的零部件配置规范值对象。
     */
    public PartConfigSpecIfc savePartConfigSpecIfc(PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartConfigSpecIfc begin ....");
        if(configSpecIfc == null)
        {
            Object[] obj = {"PartConfigSpecIfc"};
            throw new PartException(RESOURCE, "CP00001", obj);
        }
        //CCBegin by liunan 2009-01-06 打补丁v3r11_p021_20090105
        SessionService sService = (SessionService)EJBServiceHelper.getService
        ("SessionService");
        String userID = sService.getCurUserID();
        if(!PersistHelper.isPersistent(configSpecIfc))
        {
            /*SessionService sService = (SessionService)EJBServiceHelper.getService
                ("SessionService");
            String userID = sService.getCurUserID();*/
            OwnershipHelper.setOwner(configSpecIfc, userID);
        }
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        configSpecIfc = (PartConfigSpecIfc)pService.saveValueInfo(configSpecIfc);
        if(configSpecCach.containsKey(userID))
        {
        	configSpecCach.remove(userID);
        	configSpecCach.put(userID, configSpecIfc);
        }
        else
        	configSpecCach.put(userID, configSpecIfc);
        //CCEnd by liunan 2009-01-06
        PartDebug.trace(this, PartDebug.PART_SERVICE, "savePartConfigSpec end....return is PartConfigSpecIfc");
        return configSpecIfc;
    }

    /**
     * 通过名称和号码查找主零部件。允许模糊查询。
     * 如果name为null，按号码查询；如果number为null，按名称查询；
     * 如果name和numnber都为null，获得所有主零部件的值对象。
     * @param name :String 模糊查询的零部件名称。
     * @param number :String 模糊查询的零部件的号码。
     * @return collection:Collection 符合查询条件的主零部件的值对象的集合。
     * @throws QMException
     */
    public Collection getAllPartMasters(String name, String number) throws QMException
    {

          PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllPartMasters(name, number) begin ....");
        //建议改进该方法，支持*和%两个通佩符，这样，当用户无论输入的是*或者是%,都可以
        //查找到结果。
        //修改如下：
        //第一步，先判断name的最后一个字符是否是*,或者%：
        //如果是*, 先去掉name中的*, 再在查询条件中增加*.同样，如果是％，则在
        //name中去掉%，再在查询条件中增加%.
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        boolean flag = false;
        if((name != null)&&(name.length()>0))
        {
            //需要先获取name中的最后一个字符：
            int length = name.length();
            String nameLast = name.substring(length -1 , length);
            QueryCondition condition1 = null;
            if(nameLast.equals("*"))
            {
                //需要去掉name中的最后一个字符：
                name = name.substring(0, length -1);
                condition1 = new QueryCondition("partName", "like", name + "*");
            }
            else
            {
                if(nameLast.equals("%"))
                {
                    //需要去掉name中的最后一个字符：
                    name = name.substring(0, length -1);
                    condition1 = new QueryCondition("partName", "like", name + "%");
                }
                else
                {
                    condition1 = new QueryCondition("partName", "=", name);
                }
            }
            query.addCondition(condition1);
            flag = true;
        }

        if((number != null)&&(number.length()>0))
        {

            if(flag == true)
            {
                query.addAND();
            }
            //需要先获取number中的最后一个字符：
            int length = number.length();
            String numberLast = number.substring(length -1 , length);
            QueryCondition condition2 = null;
            if(numberLast.equals("*"))
            {
                //需要去掉number中的最后一个字符：
                number = number.substring(0, length -1);
                condition2 = new QueryCondition("partNumber", "like", number + "*");
            }
            else
            {
                if(numberLast.equals("%"))
                {
                    //需要去掉number中的最后一个字符：
                    number = number.substring(0, length -1);
                    condition2 = new QueryCondition("partNumber", "like", number + "%");
                }
                else
                {
                    condition2 = new QueryCondition("partNumber", "=", number);
                }
            }
            query.addCondition(condition2);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllPartMasters(name, number) end....return is Collection ");
        return pservice.findValueInfo(query);
    }

    /**
    * 通过名称和号码查找主零部件。允许模糊查询。如果name为null，按号码查询；如果number
    * 为null，按名称查询；如果name和numnber都为null，获得所有主零部件的值对象。
    * 如果参数nameFlag为true, 查找零部件名称和name不相同的那些零部件，否则，查找零部件
    * 名称和name相同的零部件。对参数numFlag作同样的处理。
    * @param name 待查询的零部件名称。
    * @param nameFlag 过滤条件。
    * @param number 待查询的零部件编号。
    * @param numFlag 过滤条件。
    * @return Collection 查询出来的主零部件(QMPartMasterIfc)的集合。
    * @throws QMException
    */
    public Collection getAllPartMasters(String name, boolean nameFlag, String number,
        boolean numFlag) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllPartMasters(name, nameFlag, number, numFlag) begin ....");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        boolean flag = false;
        if((name != null)&&(name.length()>0))
        {
            //需要根据name和其中的*, % 来构造查询条件：
            int length = name.length();

            QueryCondition condition1 = null;
            String newname="";

            if(name.indexOf("*")!=-1||name.indexOf("?")!=-1||name.indexOf("%")!=-1)
            {
              newname=name.replace('*','%');
              if (!nameFlag)
               {
                   condition1 = new QueryCondition("partName", "LIKE",
                       newname);
               }
               else
               {
                   condition1 = new QueryCondition("partName", "NOT LIKE",
                       newname);
               }

            }

         else
            {
              if(!nameFlag)
                     {
                         condition1 = new QueryCondition("partName" , "=", name);
                     }
                     else
                     {
                         condition1 = new QueryCondition("partName", "<>",
                             name);
                     }

            }


            query.addCondition(condition1);
            flag = true;
        }

        if((number != null)&&(number.length()>0))
        {
            if(flag == true)
            {
                query.addAND();
            }
            int length = number.length();
            QueryCondition condition2 = null;
            String numberLast = number.substring(length - 1, length);
             String newnumber="";
            if(number.indexOf("*")!=-1||number.indexOf("?")!=-1||number.indexOf("%")!=-1)
            {
              newnumber=number.replace('*','%').replace('?','_');
              if (!numFlag)
                {
                    condition2 = new QueryCondition("partNumber", "LIKE",
                        newnumber);
                }
                else
                {
                    condition2 = new QueryCondition("partNumber", "NOT LIKE",
                        newnumber);
                }

            }


            else
            {
              if(!numFlag)
                     {
                         condition2 = new QueryCondition("partNumber", "=",
                             number);
                     }
                     else
                     {
                         condition2 = new QueryCondition("partNumber", "<>", number);
                     }

            }

            query.addCondition(condition2);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllPartMasters(name, number) end....return is Collection ");
        Collection result = pservice.findValueInfo(query);
        return result;
    }

    /**
     * 根据partIfc寻找对应的partMasterIfc,再查找partUsageLink，获取使用了该partMasterIfc
     * 的所有零部件。
     * @param partIfc :QMPartIfc 零部件值对象。
     * @exception QMException 持久化服务的异常。
     * @return Collection QMPartIfc的集合。
     */
    public Collection getPartsByUse(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getPartsByUse begin ....");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)partIfc.getMaster();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getPartsByUse end....return is Collection ");
        return pservice.navigateValueInfo(partMasterIfc, "PartUsageLink", "uses");
    }


    /**
     * 根据指定的零部件值对象和筛选条件获得零部件的子。返回Vector:
     * Vector中存放NormalPart的对象的集合，每个NormalPart对象中的属性包括：
     * 号码+名称+版本+视图+数量+单位。注意，返回值Vector中不包含当前传入的参数partIfc。
     * @param partIfc :当前零部件值对象。
     * @param configSpecIfc 当前零部件配置规范值对象。
     * @return Vector
     * @throws QMException
     */
    public Vector getSubProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubProductStructure() begin ....");
      Vector resultVector = new Vector();
      // 第一步：调用getUsesPartIfcs方法，返回Collection，如果Collection为空，或者长度为0,抛出异常
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      if((collection.size() == 0) || (collection == null))
      {
        //"未找到符合当前筛选条件的版本"
        //throw new PartException(RESOURCE, "E03014", null);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubProductStructure() end....return is new Vector()");
        return resultVector;
      }
      //第二步: 对collection中的每个元素进行循环，需要另外声明一个递归的方法productStructure()
      //productStructure(subPartIfc, configSpecIfc, parentBsoID, partUsageLinkIfc)
      else
      {
        PartServiceHelper pshelper= new PartServiceHelper();
        Vector tempVector = new Vector();
        Object[] tempArray = new Object[collection.size()];
        collection.toArray(tempArray);
        //先把tempArray中的所有元素都放到resultVector中来
        for(int i = 0; i < tempArray.length; i++)
        {
          Object[] tempObj = (Object[])tempArray[i];
          if(tempObj[1] instanceof QMPartIfc && tempObj[0] instanceof PartUsageLinkIfc)
          {
            QMPartIfc partIfc1 = (QMPartIfc)tempObj[1];
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)tempObj[0];
            NormalPart normalPart1 = new NormalPart();
            normalPart1.bsoID = partIfc1.getBsoID();
            normalPart1.parentBsoID = partIfc.getBsoID();
            normalPart1.partName = partIfc1.getPartName();
            normalPart1.partNumber = partIfc1.getPartNumber();
            normalPart1.versionID = partIfc1.getVersionID();
            normalPart1.iterationID = partIfc1.getIterationID();
            normalPart1.versionValue = partIfc1.getVersionValue();
            normalPart1.viewName = partIfc1.getViewName();
            normalPart1.quantity = partUsageLinkIfc1.getQuantity();
            normalPart1.defaultUnit = partUsageLinkIfc1.getDefaultUnit();
            normalPart1.partUsageLink = partUsageLinkIfc1.getBsoID();
            //PartServiceHelper pshelper= new PartServiceHelper();
            normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);


            //因为零部件主信息在查看结构中不会再展开。
            Collection collection1 = getUsesPartIfcs(partIfc1, configSpecIfc);

            //是否有子件的标识，初始化为0，即没有子件。用获得子件的方法
            //getUsesPartIfcs()，如果结果中含有子件，则设置为1。
            normalPart1.isHasSub = "0";
            if((collection1.size() > 0) || (collection1 != null))
            {
               Object[] resultArray = new Object[collection1.size()];
               collection1.toArray(resultArray);
               for (int ii=0; ii<resultArray.length; ii++)
               {
                   Object obj = resultArray[ii];
                  if(obj instanceof Object[])
                  {
                     Object[] obj1 = (Object[])obj;

                     if (obj1[1] instanceof QMPartIfc)
                     {
                      	normalPart1.isHasSub = "1";
                      	break;
                     }
                  }
                }
            }
            //搜索得到的子件要设置是否展开，初始化设置为0，即不需要展开。
            //因为在查看结构的界面中树节点是逐层展开，所以当前层都是不展开状态，
            //只有点击展开后，才会改变状态。
            normalPart1.isOpen = "0";

            resultVector.addElement(normalPart1);
          }
          //2003/12/16
          if(tempObj[1] instanceof QMPartMasterIfc && tempObj[0] instanceof PartUsageLinkIfc)
          {
            QMPartMasterIfc partIfc1 = (QMPartMasterIfc)tempObj[1];
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)tempObj[0];
            NormalPart normalPart1 = new NormalPart();
            normalPart1.bsoID = partIfc1.getBsoID();
            normalPart1.parentBsoID = partIfc.getBsoID();
            normalPart1.partName = partIfc1.getPartName();
            normalPart1.partNumber = partIfc1.getPartNumber();
            normalPart1.versionID = "";
            normalPart1.iterationID = "";
            normalPart1.versionValue = "";
            normalPart1.viewName = "";
            normalPart1.quantity = partUsageLinkIfc1.getQuantity();
            normalPart1.defaultUnit = partUsageLinkIfc1.getDefaultUnit();
            normalPart1.partUsageLink = partUsageLinkIfc1.getBsoID();
            normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);

            //搜索得到的子件要设置是否有子件和是否展开的标都为0，即都没有。
            //因为零部件主信息在查看结构中不会再展开。
            normalPart1.isHasSub = "0";
            normalPart1.isOpen = "0";
            resultVector.addElement(normalPart1);
          }
        }
      }
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubProductStructure() end....return is Vector");
      return resultVector;
    }


    /**
     * 根据指定的零部件值对象和筛选条件获得零部件的产品结构。返回Vector:
     * Vector中存放NormalPart的对象的集合，每个NormalPart对象中的属性包括：
     * 号码+名称+版本+视图+数量+单位。注意，返回值Vector中不包含当前传入的参数partIfc。
     * @param partIfc :当前零部件值对象。
     * @param configSpecIfc 当前零部件配置规范值对象。
     * @return Vector
     * @throws QMException
     */
    public Vector getProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getProductStructure() begin ....");
      Vector resultVector = new Vector();
      // 第一步：调用getUsesPartIfcs方法，返回Collection，如果Collection为空，或者长度为0,抛出异常
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      if((collection.size() == 0) || (collection == null))
      {
        //"未找到符合当前筛选条件的版本"
        //throw new PartException(RESOURCE, "E03014", null);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getProductStructure() end....return is new Vector()");
        return resultVector;
      }
      //第二步: 对collection中的每个元素进行循环，需要另外声明一个递归的方法productStructure()
      //productStructure(subPartIfc, configSpecIfc, parentBsoID, partUsageLinkIfc)
      else
      {
        Vector tempVector = new Vector();
        Object[] tempArray = new Object[collection.size()];
        collection.toArray(tempArray);
        //先把tempArray中的所有元素都放到resultVector中来
        for(int i = 0; i < tempArray.length; i++)
        {
          Object[] tempObj = (Object[])tempArray[i];
          if(tempObj[1] instanceof QMPartIfc && tempObj[0] instanceof PartUsageLinkIfc)
          {
            QMPartIfc partIfc1 = (QMPartIfc)tempObj[1];
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)tempObj[0];
            NormalPart normalPart1 = new NormalPart();
            normalPart1.bsoID = partIfc1.getBsoID();
            normalPart1.parentBsoID = partIfc.getBsoID();
            normalPart1.partName = partIfc1.getPartName();
            normalPart1.partNumber = partIfc1.getPartNumber();
            normalPart1.versionID = partIfc1.getVersionID();
            normalPart1.iterationID = partIfc1.getIterationID();
            normalPart1.versionValue = partIfc1.getVersionValue();
            normalPart1.viewName = partIfc1.getViewName();
            normalPart1.quantity = partUsageLinkIfc1.getQuantity();
            normalPart1.defaultUnit = partUsageLinkIfc1.getDefaultUnit();
            normalPart1.partUsageLink = partUsageLinkIfc1.getBsoID();
            resultVector.addElement(normalPart1);
          }
          //2003/12/16
          if(tempObj[1] instanceof QMPartMasterIfc && tempObj[0] instanceof PartUsageLinkIfc)
          {
            QMPartMasterIfc partIfc1 = (QMPartMasterIfc)tempObj[1];
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)tempObj[0];
            NormalPart normalPart1 = new NormalPart();
            normalPart1.bsoID = partIfc1.getBsoID();
            normalPart1.parentBsoID = partIfc.getBsoID();
            normalPart1.partName = partIfc1.getPartName();
            normalPart1.partNumber = partIfc1.getPartNumber();
            normalPart1.versionID = "";
            normalPart1.iterationID = "";
            normalPart1.versionValue = "";
            normalPart1.viewName = "";
            normalPart1.quantity = partUsageLinkIfc1.getQuantity();
            normalPart1.defaultUnit = partUsageLinkIfc1.getDefaultUnit();
            normalPart1.partUsageLink = partUsageLinkIfc1.getBsoID();
            resultVector.addElement(normalPart1);
          }
        }
        for(int i = 0; i < tempArray.length; i++)
        {
          Object[] tempObj = (Object[])tempArray[i];
          if(tempObj[1] instanceof QMPartIfc)
          {
            tempVector = productStructure((QMPartIfc)tempObj[1], configSpecIfc, partIfc.getBsoID(), (PartUsageLinkIfc)tempObj[0]);
            for(int j = 0; j < tempVector.size(); j++)
            {
              resultVector.addElement(tempVector.elementAt(j));
            }
          }
          //end if(tempObj[1] instanceof QMPartIfc)
        }
        //for(int i=0; i<tempArray.length; i++)
      }
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getProductStructure() end....return is Vector");
      return resultVector;
    }//完成!!!!

    /**
     * 递归方法，被getProductStructure()方法所调用，实现对当前partIfc按照partConfigSpecIfc筛选使用结构。
     * @param partIfc 零部件值对象。
     * @param configSpecIfc 零部件值对象。
     * @param parentBsoID 当前partIfc的父节点的bsoID。
     * @param partUsageLinkIfc 记录partIfc和其parentBsoID对应的parentPartIfc的关联关系。
     * @return Vector NormalPart对象的集合。
     * @throws QMException
     */
    private Vector productStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc,
                                    String parentBsoID, PartUsageLinkIfc partUsageLinkIfc)
        throws QMException
    {
      Vector resultVector = new Vector();
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      //如果当前partIfc没有子节点,就把当前的resultVector直接返回
      if((collection == null) || (collection.size() < 1))
        return resultVector;
      //否则，对collection中的所有元素进行循环，调用本方法productStructure()
      else
      {
        Vector tempVector = new Vector();
        //构造下一轮循环递归的参数
        Object[] tempArray = new Object[collection.size()];
        collection.toArray(tempArray);
        //现在修改，深度遍历为广度遍历
        for(int i = 0; i < tempArray.length; i++)
        {
          Object[] obj = (Object[])tempArray[i];
          if(obj[1] instanceof QMPartIfc && obj[0] instanceof PartUsageLinkIfc)
          {
            QMPartIfc partIfc1 = (QMPartIfc)obj[1];
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)obj[0];
            NormalPart normalPart1 = new NormalPart();
            normalPart1.bsoID = partIfc1.getBsoID();
            normalPart1.parentBsoID = partIfc.getBsoID();
            normalPart1.partName = partIfc1.getPartName();
            normalPart1.partNumber = partIfc1.getPartNumber();
            normalPart1.versionID = partIfc1.getVersionID();
            normalPart1.iterationID = partIfc1.getIterationID();
            normalPart1.versionValue = partIfc1.getVersionValue();
            normalPart1.viewName = partIfc1.getViewName();
            normalPart1.quantity = partUsageLinkIfc1.getQuantity();
            normalPart1.defaultUnit = partUsageLinkIfc1.getDefaultUnit();
            normalPart1.partUsageLink = partUsageLinkIfc1.getBsoID();
            resultVector.addElement(normalPart1);
          }
          //2003/12/16
          if(obj[1] instanceof QMPartMasterIfc && obj[0] instanceof PartUsageLinkIfc)
          {
            QMPartMasterIfc partIfc1 = (QMPartMasterIfc)obj[1];
            PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc)obj[0];
            NormalPart normalPart1 = new NormalPart();
            normalPart1.bsoID = partIfc1.getBsoID();
            normalPart1.parentBsoID = partIfc.getBsoID();
            normalPart1.partName = partIfc1.getPartName();
            normalPart1.partNumber = partIfc1.getPartNumber();
            normalPart1.versionID = "";
            normalPart1.iterationID = "";
            normalPart1.versionValue = "";
            normalPart1.viewName = "";
            normalPart1.quantity = partUsageLinkIfc1.getQuantity();
            normalPart1.defaultUnit = partUsageLinkIfc1.getDefaultUnit();
            normalPart1.partUsageLink = partUsageLinkIfc1.getBsoID();
            resultVector.addElement(normalPart1);
          }
        }
        for(int i = 0; i < tempArray.length; i++)
        {
          Object[] obj = (Object[])tempArray[i];
          //tempArray[i]中的tempArray[1]如果是QMPartIfc类型的话，就处理，如果是QMPartMasterIfc类型的话，不处理。
          if(obj[1] instanceof QMPartIfc)
          {
            tempVector = productStructure((QMPartIfc)obj[1], configSpecIfc, partIfc.getBsoID(), (PartUsageLinkIfc)obj[0]);
            for(int j = 0; j < tempVector.size(); j++)
              resultVector.addElement(tempVector.elementAt(j));
          }
          //end if(obj[1] instanceof QMPartIfc)
        }
        //end for (int i=0; i<tempArray.length; i++)
      }
      return resultVector;
    }

    /**
     * 修改唯一性表中零部件的唯一标识。
     * @param partMaster QMPartMaster对象。
     * @param number 更改后的零部件号码。
     * @throws QMException
     */
    protected void updatePartMasterKey(QMPartMaster partMaster, String number)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "updatePartMasterKey begin...... ");
        QMPartMasterInfo partMasterInfo = (QMPartMasterInfo) partMaster.
                                          getValueInfo();
        PartMasterIdentity identity = (PartMasterIdentity) (partMasterInfo.
                getIdentifyObject());
        identity.setPartNumber(number);
        IdentifyService iservice = (IdentifyService) EJBServiceHelper.
                                   getService("IdentifyService");
        iservice.changeIdentity(partMaster, identity);
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "updatePartMasterKey  end return is void");
    }


    /**
     * 修改零部件（此方法供客户端直接调用）的名称，编号。
     *
     * @param partMasterIfc QMPartMasterIfc值对象,
     *   其零部件名称，零部件编号是用户输入的新的名称和编号。
     * @param flag 是否修改零部件的编号,true为修改,false不修改。
     * @return partMasterIfc 修改后的零部件的值对象。
     * @throws QMException
     */
    public QMPartMasterIfc renamePartMaster(QMPartMasterIfc partMasterIfc,boolean flag) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "renamePartMaster begin......");
        //如果对象为空，抛出异常
        if(partMasterIfc== null)
        {
            java.lang.Object aobj[] = {"QMPartMasterInfo"};
            throw new PartException(RESOURCE, "CP00001", aobj);
        }
        PersistService persistService = (PersistService)EJBServiceHelper.getPersistService();
        //获取零部件主信息对象
        QMPartMaster master = (QMPartMaster)persistService.refreshBso(partMasterIfc.getBsoID());
        //这里的name和number都是用户输入的最新的零部件名称和编号：
        String number = partMasterIfc.getPartNumber();
        try {
          if (flag) {
            updatePartMasterKey(master, number);
          }
          Timestamp timestamp = master.getModifyTime();
          partMasterIfc.setModifyTime(timestamp);
          partMasterIfc = (QMPartMasterIfc) persistService.updateValueInfo(
              partMasterIfc);
        }
        catch (QMException ex) {
          this.setRollbackOnly();
          throw ex;
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "renamePartMaster end return is QMPartMasterInfo");
        return partMasterIfc;
    }
    
    //CR7 Begin 20090619 zhangq  修改原因：TD-2190。
    /**
     * 根据离散的数据信息构造PartConfigSpecIfc对象。
     * @param effectivityActive 是否是有效性配置规范 是1,否0。
     * @param baselineActive 是否是基准线配置规范 是1,否0。
     * @param standardActive 是否是标准配置规范 是1,否0。
     * @param baselineID 基准线的BsoID。
     * @param configItemID 配置规范的BsoID。
     * @param viewObjectID 视图对象的bsoID。
     * @param effectivityType 有效性类型。
     * @param effectivityUnit 有效性单位(有效性点值)。
     * @param state 状态。
     * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true。
     * @return PartConfigSpecIfc 封装好的零部件配置规范值对象。
     * @throws QMException
     * @see PartConfigSpecInfo
     */
    public PartConfigSpecIfc hashtableToPartConfigSpecIfc(
        String effectivityActive, String baselineActive, String standardActive, String baselineID,
        String configItemID, String viewObjectID, String effectivityType, String effectivityUnit,
        String state, String workingIncluded) throws QMException
    {
//        PartDebug.trace(this, PartDebug.PART_SERVICE, "hashtableToPartConfigSpecIfc() begin ....");
//        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
////      PartConfigSpecIfc partConfigSpecIfc = findPartConfigSpecIfc();
//        PartConfigSpecIfc partConfigSpecIfc = new PartConfigSpecInfo();
//        //如果是有效性配置规范:
//        if(standardActive.equals("0") && baselineActive.equals("0") && effectivityActive.equals("1"))
//        {
//            PartEffectivityConfigSpec pEffcs = new PartEffectivityConfigSpec();
//            QMConfigurationItemIfc configItemIfc = null;
//            if(configItemID !=null && configItemID.length()>0)
//            {
//                configItemIfc = (QMConfigurationItemIfc)pService.refreshInfo(configItemID);
//            }
//            ViewObjectIfc viewObjectIfc = null;
//            if(viewObjectID != null && viewObjectID.length()>0)
//            {
//                viewObjectIfc = (ViewObjectIfc)pService.refreshInfo(viewObjectID);
//            }
//            //问题(1)20080410 zhangq begin:在瘦客户端设置没有选择有效性方案名称的配置规范时，出现空指针异常。（TD-1390）
//            //将有效性类型由中文形式转换成英文形式。
//            if(EffectivityType.DATE.getDisplay().equals(effectivityType))
//                effectivityType = EffectivityType.DATE.toString();
//            else if(EffectivityType.LOT_NUM.getDisplay().equals(effectivityType))
//                effectivityType = EffectivityType.LOT_NUM.toString();
//            else if(EffectivityType.SERIAL_NUM.getDisplay().equals(effectivityType))
//                effectivityType = EffectivityType.SERIAL_NUM.toString();
//            if(configItemIfc != null)
//            {
//                if(configItemIfc.getEffectivityType()!=null) {
//                    effectivityType=configItemIfc.getEffectivityType().toString();
//                }
//                pEffcs.setEffectiveConfigItemIfc(configItemIfc);
//            }
//            pEffcs.setEffectivityType(EffectivityType.toEffectivityType(effectivityType));
//            //问题(1)20080410 zhangq end
//            if(viewObjectIfc != null)
//                pEffcs.setViewObjectIfc(viewObjectIfc);
//            pEffcs.setEffectiveUnit(effectivityUnit);
//            partConfigSpecIfc.setEffectivity(pEffcs);
//            partConfigSpecIfc.setEffectivityActive(true);
//            partConfigSpecIfc.setBaselineActive(false);
//            partConfigSpecIfc.setStandardActive(false);
//        }
//        //如果是基准线配置规范:
//        if(standardActive.equals("0") && baselineActive.equals("1") && effectivityActive.equals("0"))
//        {
//            BaselineIfc baselineIfc = (BaselineIfc)pService.refreshInfo(baselineID);
//            PartBaselineConfigSpec pBaselinecs = new PartBaselineConfigSpec(baselineIfc);
//            partConfigSpecIfc.setBaseline(pBaselinecs);
//            partConfigSpecIfc.setEffectivityActive(false);
//            partConfigSpecIfc.setBaselineActive(true);
//            partConfigSpecIfc.setStandardActive(false);
//
//        }
//        //如果是标准配置规范:
//        if(standardActive.equals("1") && baselineActive.equals("0") && effectivityActive.equals("0"))
//        {
//          HashMap hashMap = new HashMap();
//          LifeCycleState[] State_type = LifeCycleState.getLifeCycleStateSet(Locale.CHINA);
//          LifeCycleState lifeCycleState = new LifeCycleState();
//          for(int i = 0; i < State_type.length; i++)
//          {
//            hashMap.put(State_type[i].getDisplay(RemoteProperty.getVersionLocale()), State_type[i]);
//          }
//            PartStandardConfigSpec pStandardcs = new PartStandardConfigSpec();
//            if(viewObjectID != null && viewObjectID.length() > 0)
//            {
//                ViewObjectIfc viewObjectIfc = (ViewObjectIfc)pService.refreshInfo(viewObjectID);
//                pStandardcs.setViewObjectIfc(viewObjectIfc);
//            }
//            if(state != null && state.length() > 0)
//            {
//            	lifeCycleState = (LifeCycleState)hashMap.get(state);
//              if(lifeCycleState == null)
//                lifeCycleState = LifeCycleState.toLifeCycleState(state);
//              pStandardcs.setLifeCycleState(lifeCycleState);
//            }
//            boolean wInclude = workingIncluded.equals("0")?false:true;
//            pStandardcs.setWorkingIncluded(wInclude);
//            partConfigSpecIfc.setStandard(pStandardcs);
//            partConfigSpecIfc.setStandardActive(true);
//            partConfigSpecIfc.setBaselineActive(false);
//            partConfigSpecIfc.setEffectivityActive(false);
//        }
//        PartDebug.trace(this, PartDebug.PART_SERVICE, "hashtableToPartConfigSpecIfc() end....return is PartConfigSpecIfc ");
//        return partConfigSpecIfc;
		return hashtableToPartConfigSpecIfc("1", effectivityActive,
				baselineActive, standardActive, baselineID, configItemID,
				viewObjectID, effectivityType, effectivityUnit, state,
				workingIncluded);
    }
    
    /**
     * 根据离散的数据信息构造PartConfigSpecIfc对象。
     * @param isUseCache 是否用缓存的配置规范的标志 是1,否0。
     * @param effectivityActive 是否是有效性配置规范 是1,否0。
     * @param baselineActive 是否是基准线配置规范 是1,否0。
     * @param standardActive 是否是标准配置规范 是1,否0。
     * @param baselineID 基准线的BsoID。
     * @param configItemID 配置规范的BsoID。
     * @param viewObjectID 视图对象的bsoID。
     * @param effectivityType 有效性类型。
     * @param effectivityUnit 有效性单位(有效性点值)。
     * @param state 状态。
     * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true。
     * @return PartConfigSpecIfc 封装好的零部件配置规范值对象。
     * @throws QMException
     * @see PartConfigSpecInfo
     */
    public PartConfigSpecIfc hashtableToPartConfigSpecIfc(String isUseCache,
        String effectivityActive, String baselineActive, String standardActive, String baselineID,
        String configItemID, String viewObjectID, String effectivityType, String effectivityUnit,
        String state, String workingIncluded) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "hashtableToPartConfigSpecIfc() begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        PartConfigSpecIfc partConfigSpecIfc = null;
        if(isUseCache.equals("1")){
        	partConfigSpecIfc = findPartConfigSpecIfc();
        }
        else{
        	partConfigSpecIfc = new PartConfigSpecInfo();
        }
        //如果是有效性配置规范:
        if(standardActive.equals("0") && baselineActive.equals("0") && effectivityActive.equals("1"))
        {
            PartEffectivityConfigSpec pEffcs = new PartEffectivityConfigSpec();
            QMConfigurationItemIfc configItemIfc = null;
            if(configItemID !=null && configItemID.length()>0)
            {
                configItemIfc = (QMConfigurationItemIfc)pService.refreshInfo(configItemID);
            }
            ViewObjectIfc viewObjectIfc = null;
            if(viewObjectID != null && viewObjectID.length()>0)
            {
                viewObjectIfc = (ViewObjectIfc)pService.refreshInfo(viewObjectID);
            }
            //问题(1)20080410 zhangq begin:在瘦客户端设置没有选择有效性方案名称的配置规范时，出现空指针异常。（TD-1390）
            //将有效性类型由中文形式转换成英文形式。
            if(EffectivityType.DATE.getDisplay().equals(effectivityType))
                effectivityType = EffectivityType.DATE.toString();
            else if(EffectivityType.LOT_NUM.getDisplay().equals(effectivityType))
                effectivityType = EffectivityType.LOT_NUM.toString();
            else if(EffectivityType.SERIAL_NUM.getDisplay().equals(effectivityType))
                effectivityType = EffectivityType.SERIAL_NUM.toString();
            if(configItemIfc != null)
            {
                if(configItemIfc.getEffectivityType()!=null) {
                    effectivityType=configItemIfc.getEffectivityType().toString();
                }
                pEffcs.setEffectiveConfigItemIfc(configItemIfc);
            }
            pEffcs.setEffectivityType(EffectivityType.toEffectivityType(effectivityType));
            //问题(1)20080410 zhangq end
            if(viewObjectIfc != null)
                pEffcs.setViewObjectIfc(viewObjectIfc);
            pEffcs.setEffectiveUnit(effectivityUnit);
            partConfigSpecIfc.setEffectivity(pEffcs);
            partConfigSpecIfc.setEffectivityActive(true);
            partConfigSpecIfc.setBaselineActive(false);
            partConfigSpecIfc.setStandardActive(false);
        }
        //如果是基准线配置规范:
        if(standardActive.equals("0") && baselineActive.equals("1") && effectivityActive.equals("0"))
        {
            BaselineIfc baselineIfc = (BaselineIfc)pService.refreshInfo(baselineID);
            PartBaselineConfigSpec pBaselinecs = new PartBaselineConfigSpec(baselineIfc);
            partConfigSpecIfc.setBaseline(pBaselinecs);
            partConfigSpecIfc.setEffectivityActive(false);
            partConfigSpecIfc.setBaselineActive(true);
            partConfigSpecIfc.setStandardActive(false);

        }
        //如果是标准配置规范:
        if(standardActive.equals("1") && baselineActive.equals("0") && effectivityActive.equals("0"))
        {
          HashMap hashMap = new HashMap();
          LifeCycleState[] State_type = LifeCycleState.getLifeCycleStateSet(Locale.CHINA);
          LifeCycleState lifeCycleState = new LifeCycleState();
          for(int i = 0; i < State_type.length; i++)
          {
            hashMap.put(State_type[i].getDisplay(RemoteProperty.getVersionLocale()), State_type[i]);
          }
            PartStandardConfigSpec pStandardcs = new PartStandardConfigSpec();
            if(viewObjectID != null && viewObjectID.length() > 0)
            {
                ViewObjectIfc viewObjectIfc = (ViewObjectIfc)pService.refreshInfo(viewObjectID);
                pStandardcs.setViewObjectIfc(viewObjectIfc);
            }
            if(state != null && state.length() > 0)
            {
            	lifeCycleState = (LifeCycleState)hashMap.get(state);
              if(lifeCycleState == null)
                lifeCycleState = LifeCycleState.toLifeCycleState(state);
              pStandardcs.setLifeCycleState(lifeCycleState);
            }
            boolean wInclude = workingIncluded.equals("0")?false:true;
            pStandardcs.setWorkingIncluded(wInclude);
            partConfigSpecIfc.setStandard(pStandardcs);
            partConfigSpecIfc.setStandardActive(true);
            partConfigSpecIfc.setBaselineActive(false);
            partConfigSpecIfc.setEffectivityActive(false);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "hashtableToPartConfigSpecIfc() end....return is PartConfigSpecIfc ");
        return partConfigSpecIfc;
    }
    //CR7 End 20090619 zhangq

    /**
     * 根据文档值对象查询数据库，获取所有被该文档所描述的零部件的集合。
     * 如果flag = true, 表示只返回关联的另一边的零部件(QMPartIfc)的集合，如果flag = false
     * 返回关联类(PartDescribeLinkIfc)的集合。
     * @param docIfc DocIfc 文档值对象。
     * @param flag boolean 决定返回值的结构。
     * @return Collection
     * @throws QMException
     */
    public Collection getDescribesQMParts(DocIfc docIfc, boolean flag) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getDescribesQMParts(docIfc, flag) begin ....");
        StructService sService = (StructService)EJBServiceHelper.getService("StructService");
        Collection collection = sService.navigateDescribes(docIfc, flag);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getDescribesQMParts(docIfc, flag) end....return is Collection");
        return collection;
    }

    /**
     * 根据配置规范获取使用了(指定的零部件所对应的)QMPartMaster的所有零部件的集合。
     * @param partIfc 指定的零部件值对象。
     * @param configSpec 过滤配置规范。
     * @return Vector 零部件的集合。
     * @throws QMException
     */
    private Vector navigateUsedByToIteration(QMPartMasterIfc masterIfc, ConfigSpec configSpec)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "navigateUsedByToIteration() begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        //返回的结果集合:
        Vector resultVector = new Vector();

        //CCBegin by liunan 2012-03-21
        /*QMQuery qmQuery = new QMQuery("QMPart", "PartUsageLink");   //Begin CR10     
        qmQuery = configSpec.appendSearchCriteria(qmQuery);
        //根据查询条件获得相应的结果集:
        Collection colAll = pService.navigateValueInfo(masterIfc, "uses", qmQuery, true);*/
        QMQuery query = new QMQuery("QMPart");
        query = configSpec.appendSearchCriteria(query);
        query.appendBso("PartUsageLink", false);
        query.appendBso("QMPartMaster", false);
        query.setJdbc(true);
        Vector partAttr = new Vector();
        partAttr.add("versionValue");
        partAttr.add("versionID");
        partAttr.add("iterationID");
        partAttr.add("viewName");
        partAttr.add("createTime");//
        partAttr.add("workableState");
        partAttr.add("owner");
        partAttr.add("aclOwner");
        partAttr.add("location");
        partAttr.add("currentPhaseId");
        partAttr.add("lifeCycleState");
        partAttr.add("masterBsoID");
        partAttr.add("iterationIfLatest");
        partAttr.add("iterationState");
        partAttr.add("modifyTime");
        partAttr.add("predecessorID");
        partAttr.add("branchID");
        query.addAttribute(0, partAttr);
        Vector masterAttr = new Vector();
        masterAttr.add("partNumber");
        masterAttr.add("partName");
        query.addAttribute(2, masterAttr);
        query.addAND();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts end....return is Collection");
        String bsoID = masterIfc.getBsoID();
        QueryCondition condition2 = new QueryCondition("leftBsoID", "=", bsoID);
        query.addCondition(1,condition2);
        query.addAND();
        QueryCondition condition3 = new QueryCondition("bsoID", "rightBsoID");
        query.addCondition(0,1, condition3);
        query.addAND();
        QueryCondition condition4 = new QueryCondition("masterBsoID", "bsoID");
        query.addCondition(0,2,condition4);
        Collection colAll = pservice.findValueInfo(query);//End CR10
        //CCEnd by liunan 2012-03-21
        Collection resultCollection = null;
        if(colAll != null && colAll.size() > 0)
        {            
            resultCollection = configSpec.process(colAll);            
        }
        //构造结果集合:
        if(resultCollection != null && resultCollection.size() > 0)
        {
            Iterator iterator = resultCollection.iterator();
            while(iterator.hasNext())
            {
                Object object0 = iterator.next();
                if(object0 instanceof Object[])
                {
                    Object[] objArray = (Object[])object0;
                    resultVector.addElement((QMPartIfc)objArray[0]);
                }
                else
                {
                    resultVector.addElement(object0);
                }
                //end if(object0 instanceof Object[])
            }
            //end while(iterator.hasNext())
        }
        //end if(resultCollection != null && resultCollection.size() > 0)
        PartDebug.trace(this, PartDebug.PART_SERVICE, "navigateUsedByToIteration() end....return is Vector");
        return resultVector;
    }


    /**
     * add by 孙珂鑫 at 2004.5.24
     * 用于瘦客户显示另存为历史，循环地得到指定零部件的另存为历史部件
     * 例如零部件a由零部件b另存为得来，而b由c另存为得来，那么给定a的bsoID则可以获得b和c的相应信息
     * 返回值是Vector，其中每个元素是String类型的数组，以存放历史部件的信息。
     *
     * @param partID String
     * @return java.util.Collection
     */
    public Collection findAllPreParts(String partID)
    {
        Vector tempresult = new Vector();
        Vector result = new Vector();
        String[] string = new String[6];
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            BaseValueIfc value = pService.refreshInfo(partID);
            if(value instanceof QMPartIfc)
            {
                QMPartIfc part = (QMPartIfc) value;
                string[0] = part.getPartNumber() + "(" + part.getPartName() + ")";
                string[1] = getCreater(part).getUsersDesc();
                string[2] = part.getCreateTime().toString();
                string[3] = partID;
                string[4] = part.getVersionValue();
                string[5] = part.getViewName();
                tempresult.add(0, string);
                for (int i = 1; part != null; )
                {
                    QMPartIfc prePart1 = findPrePart(partID);
                    if (prePart1 instanceof QMPartIfc)
                    {
                        QMPartIfc prePart = (QMPartIfc) prePart1;
                        string = new String[6];
                        UserIfc owner1 = getCreater(prePart);
                        string[0] = prePart.getPartNumber() + "(" +
                            prePart.getPartName() + ")";
                        string[1] = owner1.getUsersDesc();
                        string[2] = prePart.getCreateTime().toString();
                        string[3] = prePart.getBsoID();
                        string[4] = prePart.getVersionValue();
                        string[5] = prePart.getViewName();
                        tempresult.add(i, string);

                        part = prePart;
                        partID = part.getBsoID();
                        i++;
                    }
                    else
                        part = null;
                }
                for (int i = 0; i < tempresult.size(); i++)
                {
                    String s[] = (String[]) tempresult.get(i);
                    Object aobj[] = new Object[7];
                    aobj[0] = Integer.toString(tempresult.size() - 1 - i);
                    aobj[1] = s[0];
                    aobj[2] = s[1];
                    aobj[3] = s[2];
                    aobj[4] = s[3];
                    aobj[5] = s[4];
                    aobj[6] = s[5];
                    result.add(i, aobj);
                }
                return result;
            }

        }
        catch(QMException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * add by 孙珂鑫 获得指定零部件的前一零部件，即该零部件是由哪个零部件另存为得来的。
     *
     * @param partID String
     * @return com.faw_qm.part.model.QMPartIfc
     */
    private QMPartIfc findPrePart(String partID)
    {
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                getService("PersistService");
            QMQuery query = new QMQuery("MakeFromLink");
            QueryCondition condition = new QueryCondition("leftBsoID","=",partID);
            query.addCondition(condition);
            Collection link = (Collection)pService.findValueInfo(query);
            if(link != null && (link.iterator()).hasNext())
            {
                Iterator iter = link.iterator();
                MakeFromLinkIfc mlink = (MakeFromLinkIfc)iter.next();
                String partbsoID = mlink.getRightBsoID();
                BaseValueIfc part = pService.refreshInfo(partbsoID);
                return (QMPartIfc)part;
            }

        }catch(QMException e){
            e.printStackTrace();
        }
        return null;
    }

    private UserIfc getCreater(QMPartIfc part)
    {
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                getService("PersistService");
            String ownerString = ((QMPartIfc)part).getIterationCreator();
            UserIfc user = (UserIfc)pService.refreshInfo(ownerString);
            return user;
        }catch(QMException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * add by 孙珂鑫 2004.6.14 获得由给定零部件衍生出的零部件
     * 即由该零部件另存为得到的所有零部件的相关信息，供瘦客户显示
     * 返回值为一Vector，其中每个元素是一字符串数组。
     * @param partID String
     * @return Collection
     */
    public Collection findAllSaveAsParts(String partID)
    {
        try
        {
            Vector result = new Vector();
            PersistService pService = (PersistService) EJBServiceHelper.
                getService("PersistService");
            QMQuery query = new QMQuery("MakeFromLink");
            QueryCondition condition = new QueryCondition("rightBsoID", "=", partID);
            query.addCondition(condition);
            Collection link = (Collection) pService.findValueInfo(query);
            if (link != null && link.size() != 0)
            {
                Iterator iter = link.iterator();
                while (iter.hasNext())
                {
                    MakeFromLinkIfc mlink = (MakeFromLinkIfc) iter.next();
                    String partbsoID = mlink.getLeftBsoID();
                    QMPartIfc part = (QMPartIfc)pService.refreshInfo(partbsoID);
                    UserIfc user = getCreater(part);
                    String[] string = new String[6];
                    string[0] = part.getBsoID();
                    string[1] = part.getPartNumber() + "(" + part.getPartName() + ")";
                    string[2] = part.getVersionValue();
                    string[3] = part.getViewName();
                    string[4] = user.getUsersDesc();
                    string[5] = part.getCreateTime().toString();
                    result.addElement(string);
                }
            }
            return result;
        }
        catch (QMException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;

    }


     /**
     * 根据指定的具体零部件获取其所有类型为产品的父级零部件的最新版本。
     * @param qmPartIfc QMPartIfc 具体零部件。
     * @throws QMRemoteException
     * @throws QMException
     * @return Collection 父级零部件的最新版本。
     */
    public Collection getParentProduct(QMPartIfc qmPartIfc)
            throws QMException
    {
    	List list = new ArrayList();
    	list.add(qmPartIfc);
        Collection result = getParentProduct(list);
        return result;
    }

    /**
     * 根据指定的具体零部件集合获取其所有类型为产品的父级零部件的最新版本。
     * @param Collection QMPartIfcs 具体零部件集合。
     * @throws QMRemoteException
     * @throws QMException
     * @return Collection 父级零部件的最新版本集合。
     */
    public Collection getParentProduct(Collection qmPartIfcs)
            throws QMException
    {
        //QMProperties
        String s = null;
        String s1 = RemoteProperty.getProperty("com.faw_qm.part.productType");
        //类型为产品的键值。
        List typeList = new ArrayList();
        StringTokenizer stringtokenizer = new StringTokenizer(s1, ",");
        while (stringtokenizer.countTokens() > 0)
        {
            s = ((String) stringtokenizer.nextElement()).trim();
            typeList.add(s);
        }
        //所有父级零部件的主信息集合。
        List parentPartMasters = new Vector();
        Iterator I = qmPartIfcs.iterator();

        while(I.hasNext())
        {
        	QMPartIfc part = (QMPartIfc) I.next();
        	List list = getAllParentParts(part);
        	parentPartMasters.addAll(list);
        }


        //所有父级零部件的最新版本集合。
        List parentParts = (List) filteredIterationsOf(
                parentPartMasters, findPartConfigSpecIfc());
        //类型为产品的父级零部件的结果集。
        List result = new ArrayList();
        Object[] parentPartsArray = parentParts.toArray();
        Object[] typeArray = typeList.toArray();
        //过滤出类型为产品的父级零部件。
        for (int i = 0; i < parentPartsArray.length; i++)
        {
            if (parentPartsArray[i] instanceof Object[])
            {
                Object[] parentPartArray = (Object[]) parentPartsArray[i];
                for (int j = 0; j < parentPartArray.length; j++)
                {
                    for (int k = 0; k < typeArray.length; k++)
                    {
                        if ((parentPartArray[j] instanceof QMPartIfc) &&
                            ((QMPartIfc) parentPartArray[j]).getPartType().
                            toString().equals(typeArray[k]))
                            result.add(parentPartArray[j]);
                        break;
                    }
                }
            }
        }
        return result;
    }


    /**
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param childPartIfc QMPartIfc 子零部件。
     * @throws QMException
     * @return String 使用数量。
     */
    public String getPartQuantity(QMPartIfc parentPartIfc, QMPartIfc childPartIfc)
            throws QMException
    {
        //获取当前用户的配置规范。
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                              findPartConfigSpecIfc();
        if(parentPartIfc.getPartNumber().equals(childPartIfc.getPartNumber()))
            return "1";
        //获取子零部件统计表。
        String[] attrNames = {"quantity"};
        List childParts = setBOMList(parentPartIfc, attrNames, null, null, null,
                                     partConfigSpecIfc);
        Object[] childPartsArray = childParts.toArray();
        //获取指定子零部件的使用数量。
        for (int i = 0; i < childPartsArray.length; i++)
        {
            if (childPartsArray[i] instanceof Object[])
            {
                Object[] childPart = (Object[]) childPartsArray[i];
                for (int j = 0; j < childPart.length; j++)
                {
                    if (childPart[0].equals(childPartIfc.getBsoID()))
                        return childPart[3].toString();
                }
            }
        }
        return null;
    }


    /**
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param middlePartMasterIfc QMPartMasterIfc 中间零部件的主信息。
     * @param childPartIfc QMPartIfc 子零部件。
     * @throws QMException
     * @return String 使用数量。
     */
    public String getPartQuantity(QMPartIfc parentPartIfc,
                                  QMPartMasterIfc middlePartMasterIfc,
                                  QMPartIfc childPartIfc)
            throws QMException
    {
        //获取当前用户的配置规范。
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                              findPartConfigSpecIfc();
        String middleQuantity = null;
        QMPartIfc middlePartIfc = null;
        if(parentPartIfc.getPartNumber().equals(middlePartMasterIfc.getPartNumber()))
        {
            middleQuantity = "1";
            middlePartIfc = parentPartIfc;
        }
        else
        {
            String[] attrNames = {"quantity"};
            //获取子零部件统计表。
            List childParts = setBOMList(parentPartIfc, attrNames, null, null, null,
                                         partConfigSpecIfc);
            Object[] childPartsArray = childParts.toArray();

            //获取中间零部件的使用数量。
            for (int i = 0; i < childPartsArray.length; i++)
            {
                if (childPartsArray[i] instanceof Object[])
                {
                    Object[] childPart = (Object[]) childPartsArray[i];
                    for (int j = 0; j < childPart.length; j++)
                    {
                        if (childPart[1].equals(middlePartMasterIfc.
                                                getPartNumber()))
                            middleQuantity = childPart[3].toString();
                    }
                }
            }
            if (middleQuantity == null || middleQuantity.equals(""))
                return null;
            middlePartIfc = getPartByConfigSpec(middlePartMasterIfc, partConfigSpecIfc);
        }
        String quantity = getPartQuantity(middlePartIfc, childPartIfc);
        if (quantity == null || quantity.equals(""))
            return null;
        float middleQuantity2 = Float.valueOf(middleQuantity).floatValue();
        float quantity2 = Float.valueOf(quantity).floatValue();
        String tempQuantity = String.valueOf(middleQuantity2 * quantity2);
        if (tempQuantity.endsWith(".0"))
            tempQuantity = tempQuantity.substring(0,
                                                  tempQuantity.length() - 2);
        return tempQuantity;
    }


    /**
     * 获取符合配置规范的零部件。
     * @param partMasterIfc QMPartMasterIfc 零部件主信息。
     * @param partConfigSpecIfc PartConfigSpecIfc 配置规范。
     * @throws QMException
     * @return QMPartIfc
     */
    public QMPartIfc getPartByConfigSpec(QMPartMasterIfc partMasterIfc,
                         PartConfigSpecIfc partConfigSpecIfc)
            throws QMException
    {
        Collection collection = new ArrayList();
        collection.add(partMasterIfc);
        Collection collection2 = filteredIterationsOf(collection,
                partConfigSpecIfc);
        Iterator iterator = collection2.iterator();
        Object[] obj2 = null;
        while (iterator.hasNext())
        {
            Object obj1 = iterator.next();
            if (obj1 instanceof Object[])
            {
                obj2 = (Object[]) obj1;
            }
        }
        if(obj2 == null || obj2.length == 0)
            return null;
        if(!(obj2[0] instanceof QMPartIfc))
            return null;
        return (QMPartIfc)obj2[0];
    }


    /**
     * 在指定产品中，获取指定子零部件的所有父零部件。
     * @param parentPartMasterIfc QMPartMasterIfc 产品的主信息。
     * @param childPartMasterIfc QMPartMasterIfc 该产品中的子零部件的主信息。
     * @throws QMException
     * @return HashMap 父零部件集合,键：PartNumber，值：值对象。
     */
    public HashMap getParentPartsFromProduct(QMPartMasterIfc parentPartMasterIfc,
                        QMPartMasterIfc childPartMasterIfc)
            throws QMException
    {
        PartConfigSpecIfc partConfigSpecIfc = findPartConfigSpecIfc();

        HashMap result = getParentPartsFromProduct(parentPartMasterIfc, partConfigSpecIfc,
                                         childPartMasterIfc, new ArrayList());
        return result;
    }


    /**
     * 在指定产品中，获取指定子零部件的所有父零部件。被同名公有方法调用。实现迭代获取父零部件。
     * @param parentPartMasterIfc QMPartMasterIfc 产品的主信息。
     * @param partConfigSpecIfc PartConfigSpecIfc 当前用户使用的配置规范。
     * @param childPartIfc QMPartIfc 该产品中的符合当前配置规范的子零部件的最新版本。
     * @param tempList List 临时缓存。
     * @throws QMException
     * @return HashMap 结果集。
     */
    private HashMap getParentPartsFromProduct(QMPartMasterIfc parentPartMasterIfc,
                      PartConfigSpecIfc partConfigSpecIfc, QMPartMasterIfc childPartMasterIfc, List tempList)
            throws QMException
    {
        //结果集使用HashMap来保证结果集中的元素唯一。
        HashMap result = new HashMap();
        List parentsList = (List) navigateUsedByToIteration(childPartMasterIfc,
                new PartConfigSpecAssistant(partConfigSpecIfc));
        Object[] parentsObject = parentsList.toArray();
        //对父零部件进行循环。
        for (int i = 0; i < parentsObject.length; i++)
        {
            //如果父零部件是指定产品。
            if (((QMPartIfc) parentsObject[i]).getPartNumber().equals(
                    parentPartMasterIfc.getPartNumber()))
            {
            	Object[] tempObject = tempList.toArray();
                for (int j = 0; j < tempObject.length; j++)
                {
                    //如果结果集中不存在则添加。
                    if (!result.containsKey(((QMPartIfc) tempObject[j]).getPartNumber()))
                        result.put(((QMPartIfc) tempObject[j]).getPartNumber(), tempObject[j]);
                }
                //清空临时缓存。
                tempList = new ArrayList();
            }
            //如果否，则继续向上迭代获取父零部件。
            else
            {
                tempList.add(parentsObject[i]);
                HashMap tempResult = getParentPartsFromProduct(parentPartMasterIfc, partConfigSpecIfc,
                                       (QMPartMasterIfc)((QMPartIfc) parentsObject[i]).getMaster(), tempList);
                //将结果添加到结果集中。
                for (int j = 0; j < tempResult.size(); j++)
                {
                    Collection col = tempResult.values();
                    Object[] obj = col.toArray();
                    for (int k = 0; k < obj.length; k++)
                    {
                        //如果结果集中不存在则添加。
                        if (!result.containsKey(((QMPartIfc) obj[k]).getPartNumber()))
                            result.put(((QMPartIfc) obj[k]).getPartNumber(), obj[k]);
                    }
                }
                //清空临时缓存。
                tempList = tempList.subList(0,tempList.indexOf(parentsObject[i]));
            }
        }
        return result;
    }


    public BaseValueIfc setLifeCycle(BaseValueIfc basevalue) throws QMException
    {
        LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService(
            "LifeCycleService");
        LifeCycleTemplateIfc template = lservice.getDefaultLifeCycleByBsoName(basevalue.getBsoName());
        basevalue = lservice.setLifeCycle((LifeCycleManagedIfc)basevalue,template);
        return basevalue;
    }

    /**
     * 获取服务名。
     * @return "StandardPartService"
     */
    public String getServiceName()
    {
        return "StandardPartService";
    }

    /**
     * 计算产品下所有子件在产品中使用的数量。
     * @param product
     * @param partConfigSpecIfc
     * @return HashMap 键是子件的bsoid，值是一个数组，第一位放子件的值对象，第2位
                        放子件在产品中的使用数量。
     * @throws QMException
     */
    public HashMap getAllSubCounts(QMPartIfc product,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException
    {
        HashMap hashmap = new HashMap();
        Object[] obj = null;
      PartUsageLinkIfc partlink=new PartUsageLinkInfo();
      partlink.setQuantity(1.0f);
      Vector result=new Vector();
      result=getAllParts(product,partConfigSpecIfc,partlink,result);
      Vector resultVector=new Vector();
      String partNum;
      float quan;
      for(Iterator ite=result.iterator();ite.hasNext();)
      {
        obj=(Object[])ite.next();
        partNum=(String)obj[0];

        boolean flag=false;
        for(int i=0;i<resultVector.size();i++)
        {
          Object[] obje=(Object[])resultVector.elementAt(i);
          String partNum1=(String)obje[0];
          if(partNum.equals(partNum1))
          {
            flag=true;
            float float1 = (new Float((String)obj[2])).
                                     floatValue();
            float float2 = (new Float((String)obje[2])).
                                     floatValue();
            quan=float1+float2;
            String tempQuantity = String.valueOf(quan);
                          if (tempQuantity.endsWith(".0"))
                              tempQuantity = tempQuantity.substring(0,
                                      tempQuantity.length() - 2);

           obj[2] =tempQuantity;
           resultVector.setElementAt(obj,i);
           break;
          }
        }
        if(!flag)
          resultVector.add(obj);

      }
      for(Iterator iterator=resultVector.iterator();iterator.hasNext();)
      {
        Object[] resultObj=new Object[2];
        Object[] obj2=(Object[])iterator.next();
        QMPartIfc part=(QMPartIfc)obj2[1];
        String partid=part.getBsoID();
        resultObj[0]=part;
        resultObj[1]=obj2[2];
        hashmap.put(partid,resultObj);
      }

        return hashmap;
    }
    private Vector getAllParts(QMPartIfc partifc,PartConfigSpecIfc partConfigSpecIfc,PartUsageLinkIfc partlink,Vector result)
    throws QMException
    {

      float quan=partlink.getQuantity();
      Object[] obj = null;
       // 第一步：调用getUsesPartIfcs方法，返回Collection，如果Collection为空，或者长度为0,抛出异常
       Collection collection = getUsesPartIfcs(partifc, partConfigSpecIfc);
       if((collection.size() == 0) || (collection == null))
       {
           //"未找到符合当前筛选条件的版本"
           //throw new PartException(RESOURCE, "E03014", null);
           PartDebug.trace(this, PartDebug.PART_SERVICE,
                   "getSubProductStructure() end....return is new Vector()");
           return result;
       }
       //第二步: 对collection中的每个元素进行循环，需要另外声明一个递归的方法productStructure()
       //productStructure(subPartIfc, configSpecIfc, parentBsoID, partUsageLinkIfc)
       else
       {
           Object[] tempArray = new Object[collection.size()];
           collection.toArray(tempArray);
           QMPartIfc partIfc1 = null;
           PartUsageLinkIfc partUsageLinkIfc1=null;
           //先把tempArray中的所有元素都放到resultVector中来
           for (int i = 0; i < tempArray.length; i++)
           {
               Object[] tempObj = (Object[]) tempArray[i];
               if(tempObj[1] instanceof QMPartIfc
                       && tempObj[0] instanceof PartUsageLinkIfc)
               {
                   obj = new Object[3];
                   partIfc1 = (QMPartIfc) tempObj[1];
                   partUsageLinkIfc1 = (PartUsageLinkIfc) tempObj[0];
                   obj[0] = partIfc1.getPartNumber();

                       obj[1] = partIfc1;
                       quan=quan*partUsageLinkIfc1.getQuantity();
                       obj[2]=Float.toString(quan);
                       result.add(obj);


               }
               if(partIfc1 != null&&partUsageLinkIfc1!=null)
                   getAllParts(partIfc1, partConfigSpecIfc, partUsageLinkIfc1,
                            result);
            }
        }
        return result;
    }

    /**
     * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的
     * mastered对象（指定角色master）的结果集
     * @param links 关联类值对象集合
     * @param role 角色名
     * @exception com.faw_qm.config.exception.ConfigException
     * @return 对应关联类值对象的Mastered对象集合。
     */
    private Collection mastersOf(Collection links) throws QMException
    {
        Vector vector = (Vector) links;//begin CR2
        Vector resultVector = new Vector();
       
        for (int i = 0; i < vector.size(); i++)
        {
            PartUsageLinkIfc obj = (PartUsageLinkIfc) vector.elementAt(i);
            String bsoID;
            try
            {
                bsoID = obj.getRoleBsoID("uses");
            }
            catch (QMException e)
            {
                throw new QMException(e, "根据角色名获得其BsoID时出错");
            }
            BaseValueIfc bsoObj;
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            if(masterMap.containsKey(bsoID))
            	bsoObj=(BaseValueIfc)masterMap.get(bsoID);
            else
            {
            bsoObj = (BaseValueIfc) persistService.refreshInfo(bsoID, false);
            
            if(!(bsoObj instanceof QMPartMasterIfc))
            {
                throw new QMException();
            }//endif 如果collection中的元素左连接对象object不是MasteredIfc的
            //实例，抛出角色无效例外
            masterMap.put(bsoID, bsoObj);
           
            }
            resultVector.addElement(bsoObj);
        }//endfor i
        return removeDuplicates(resultVector);//end CR2
    }//end mastersOf(Collection,String)

    /**
     * 将指定的结果集中重复的元素排除。
     * @param collection 结果集
     * @return Collection  排除了重复数据的集合
     * Collection中每一个元素为一Object数组
     * 该Object数组中的第0个元素为一值对象
     */
    private Collection removeDuplicates(Collection collection)
            throws QMException
    {
        Hashtable hashtable = new Hashtable();
        Vector resultVector = new Vector();
        for (Iterator ite = collection.iterator(); ite.hasNext();)
        {
            BaseValueInfo obj = (BaseValueInfo) ite.next();
            String objBsoID = obj.getBsoID();
            boolean flag = hashtable.containsKey(objBsoID);
            if(flag == true)
                continue;
            hashtable.put(objBsoID, "");//将BsoID做为标志放入Hash表
            resultVector.addElement(obj);
        }//endfor i
        return resultVector;
    }//end removeDuplicates(Collection)

    /**
     * 查找所有零部件。瘦客户端根据零部件的11个属性搜索零部件的功能，支持模糊查询和非查询。
     * 现用于文档反查零部件用例中，也适用于其它需要根据多属性查询零部件的用例。
     * @param partnumber
     * @param checkboxNum
     * @param partname
     * @param checkboxName
     * @param partver
     * @param checkboxVersion
     * @param partview
     * @param checkboxView
     * @param partstate
     * @param checkboxLifeCState
     * @param parttype
     * @param checkboxPartType
     * @param partby
     * @param checkboxProducedBy
     * @param partproject
     * @param checkboxProject
     * @param partfolder
     * @param checkboxFolder
     * @param partcreator
     * @param checkboxCreator
     * @param partupdatetime
     * @param checkboxModifyTime
     * @return 零部件值对象集合。
     * @throws QMException
     */
    public Collection findAllPartInfo(String partnumber, String checkboxNum,
            String partname, String checkboxName, String partver,
            String checkboxVersion, String partview, String checkboxView,
            String partstate, String checkboxLifeCState, String parttype,
            String checkboxPartType, String partby, String checkboxProducedBy,
            String partproject, String checkboxProject, String partfolder,
            String checkboxFolder, String partcreator, String checkboxCreator,
            String partupdatetime, String checkboxModifyTime)
            throws QMException
    {
        //  获得持久化服务
        PersistService ps = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //  创建新的查找对象query
        QMQuery query1 = new QMQuery("QMPart");
        //             根据零部件编号查询
        query1 = getFindPartInfoByNum(query1, partnumber, checkboxNum);
        //  根据零部件名称查询
        query1 = getFindPartInfoByName(query1, partname, checkboxName);
        query1 = getFindPartInfoByVersion(query1, partver, checkboxVersion);
        query1 = getFindPartInfoByView(query1, partview, checkboxView);
        //根据零部件生命周期查询
        query1 = getFindPartInfoByLifeCycle(query1, partstate,
                checkboxLifeCState);
        query1 = getFindPartInfoByType(query1, parttype, checkboxPartType);
        query1 = getFindPartInfoByProducedBy(query1, partby, checkboxProducedBy);
        //  根据零部件项目组查询
        query1 = getFindPartInfoByProject(query1, partproject, checkboxProject);
        //  根据文件夹查询
        query1 = getFindPartInfoByFolder(query1, partfolder, checkboxFolder);
        //  根据创建者查询
        query1 = getFindPartInfoByCreator(query1, partcreator, checkboxCreator);
        // 根据创建time查询
        query1 = getFindPartInfoByTime(query1, partupdatetime,
                checkboxModifyTime, "modifyTime");
        /*
         modify by ShangHaiFeng 2004.03.22 BE:根据新的版本范规,重新确定过滤条件.
         无论业务对象在和状态下，系统授权用户只能搜索到具有权限的最新版本（原本或副本）。
         业务对象在未检入时，非业务对象创建用户根据系统权限可以搜索出该业务对象，图标显示为正常版本的图标。
         业务对象检入状态，系统用户根据系统权限可以搜索出该业务对象，版本为最新版本，图标显示为正常版本的图标。
         业务对象在检出状态时，业务对象检出用户可以搜索出该业务对象，版本为最新版本（检出版本），
         不显示该业务对象的原本，图标显示为业务对象副本的图标。
         业务对象在检出状态时，非业务对象检出用户并且对检出用户的个人资料夹无访问权限的其他用户，
         可以根据系统的权限搜索出该业务对象，版本为该业务对象的原本，图标显示为原本的图标。
         业务对象在检出状态时，非业务对象检出用户并且对检出用户的个人资料夹有访问权限的其他用户，
         可以搜索出该业务对象攻，版本为最新版本（检出版本），不显示该业务对象的原本，图标显示为业务对象副本的图标
         */
        query1 = (new LatestConfigSpec()).appendSearchCriteria(query1);
        return ps.findValueInfo(query1);
    }

    /**
     * @param query1
     * @param partupdatetime
     * @param checkboxModifyTime
     * @param timeType
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByTime(QMQuery query1,
            String partupdatetime, String checkboxModifyTime, String timeType)
            throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partupdatetime != null && !partupdatetime.trim().equals(""))
        {
            String javaFormat = "yyyy:MM:dd" + ':' + "HH:mm:ss";
            String timeStr = partupdatetime;
            boolean betweenFlag = false;
            String beginTimeStr = "";
            String endTimeStr = "";
            String beginFormatTimeStr = null;
            DateHelper dateHelperBegin = null;
            DateHelper dateHelperEnd = null;
            Timestamp beginTime = null;
            Timestamp endTime = null;
            int i = timeStr.indexOf("-");
            if(i > -1)
            {
                //有明确的起止时间分隔符
                beginTimeStr = timeStr.substring(0, i);
                endTimeStr = timeStr.substring(i + 1);
                try
                {
                    if(!beginTimeStr.trim().equals(""))
                    {
                        dateHelperBegin = new DateHelper(beginTimeStr);
                    }
                    if(!endTimeStr.trim().equals(""))
                    {
                        dateHelperEnd = new DateHelper(endTimeStr);
                    }
                }
                catch (Exception ex)
                {
                    throw new QMException(ex);
                }
            }
            else
            {
                //无明确的起止时间分隔符
                beginTimeStr = timeStr;
                try
                {
                    dateHelperBegin = new DateHelper(beginTimeStr);
                }
                catch (Exception ex)
                {
                    throw new QMException(ex);
                }
            }
            //如果有明确的起止时间分隔符，获取起止时间，
            //如果没有明确的起止时间分隔符，进一步判断是否是完成时间（如2003-04-28 10:00:00.0）
            //如不是，则算出对应起止时间。
            //如指定搜索“2003-04-28 10”，
            //begin: 2003-04-28 10:00:00
            //end:   2003-04-28 10:59:59
            if(dateHelperBegin != null && dateHelperEnd != null)
            {
                betweenFlag = true;
                beginTime = new Timestamp(dateHelperBegin.getDate().getTime());
                endTime = new Timestamp(dateHelperEnd.getDate().getTime());
            }
            else if(dateHelperBegin != null && dateHelperEnd == null)
            {
                if(dateHelperBegin.fullDate())
                {
                    betweenFlag = false;
                    beginTime = new Timestamp(dateHelperBegin.getDate()
                            .getTime());
                    endTime = null;
                }
                else
                {
                    betweenFlag = true;
                    beginTime = new Timestamp(dateHelperBegin.instantOfDate(
                            DateHelper.firstInstant).getTime());
                    endTime = new Timestamp(dateHelperBegin.instantOfDate(
                            DateHelper.lastInstant).getTime());
                }
            }
            else if(dateHelperBegin == null && dateHelperEnd != null)
            {
                if(dateHelperEnd.fullDate())
                {
                    betweenFlag = false;
                    beginTime = new Timestamp(dateHelperEnd.getDate().getTime());
                    endTime = null;
                }
                else
                {
                    betweenFlag = true;
                    beginTime = new Timestamp(dateHelperEnd.instantOfDate(
                            DateHelper.firstInstant).getTime());
                    endTime = new Timestamp(dateHelperEnd.instantOfDate(
                            DateHelper.lastInstant).getTime());
                }
            }
            //比较两个时间大小，如果开始时间大于终止时间，时间要调换
            if(beginTime != null && endTime != null)
            {
                if(beginTime.compareTo(endTime) > 0)
                {
                    Timestamp temp = beginTime;
                    beginTime = endTime;
                    endTime = temp;
                }
            }
            if(beginTime != null)
            {
                beginFormatTimeStr = getDateString(beginTime, javaFormat);
            }
            if(beginFormatTimeStr == null)
            {
                return query;
            }
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询 +
            }
            if(checkboxModifyTime == null)
            {
                try
                {
                    if(betweenFlag)
                    {
                        query.addLeftParentheses();
                        QueryCondition cond = new QueryCondition(timeType,
                                ">=", beginTime);
                        query.addCondition(0, cond);
                        query.addAND();
                        cond = new QueryCondition(timeType, "<=", endTime);
                        query.addCondition(0, cond);
                        query.addRightParentheses();
                    }
                    else
                    {
                        QueryCondition cond = new QueryCondition(timeType, "=",
                                beginTime);
                        query.addCondition(0, cond);
                    }
                }
                catch (Exception ex)
                {
                    throw new QMException(ex);
                }
            }
            //  如果用户想查询 -
            else
            {
                try
                {
                    if(betweenFlag)
                    {
                        query.addLeftParentheses();
                        QueryCondition cond = new QueryCondition(timeType,
                                "<=", beginTime);
                        query.addCondition(0, cond);
                        query.addOR();
                        cond = new QueryCondition(timeType, ">=", endTime);
                        query.addCondition(0, cond);
                        query.addRightParentheses();
                    }
                    else
                    {
                        QueryCondition cond = new QueryCondition(timeType,
                                "<>", beginTime);
                        query.addCondition(0, cond);
                    }
                }
                catch (Exception ex)
                {
                    throw new QMException(ex);
                }
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param partcreator
     * @param checkboxCreator
     * @return
     */
    private QMQuery getFindPartInfoByCreator(QMQuery query1,
            String partcreator, String checkboxCreator) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partcreator != null && !partcreator.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
            }
            int j = query.appendBso("User", false);
            QueryCondition qc1 = new QueryCondition("iterationCreator", "bsoID");
            query.addCondition(0, j, qc1);
            query.addAND();
            //  如果用户查询的文档中不想包括此文档编号
            if(checkboxCreator == null)
            {
                //modify by ShangHaiFeng 2003.12.15
                query.addLeftParentheses();
                QueryCondition cond = new QueryCondition("usersName",
                        QueryCondition.LIKE, getLikeSearchString(partcreator));
                query.addCondition(j, cond);
                QueryCondition cond2 = new QueryCondition("usersDesc",
                        QueryCondition.LIKE, getLikeSearchString(partcreator));
                query.addOR();
                query.addCondition(j, cond2);
                query.addRightParentheses();
            }
            else
            {
                //modify by ShangHaiFeng 2003.12.15
                query.addLeftParentheses();
                QueryCondition cond = new QueryCondition("usersName",
                        QueryCondition.NOT_LIKE,
                        getLikeSearchString(partcreator));
                query.addCondition(j, cond);
                QueryCondition cond2 = new QueryCondition("usersDesc",
                        QueryCondition.NOT_LIKE,
                        getLikeSearchString(partcreator));
                query.addAND();
                query.addCondition(j, cond2);
                query.addRightParentheses();
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param partfolder
     * @param checkboxFolder
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByFolder(QMQuery query1, String partfolder,
            String checkboxFolder) throws QMException
    {
        QMQuery query = query1;
        //
        if(partfolder != null && !partfolder.trim().equals(""))
        {
            if(partfolder.equals("\\"))
            {
                partfolder = "\\Root";
            }
            if(partfolder.indexOf("\\Root") != 0)
            {
                //不以Root开头
                if(partfolder.indexOf("\\Root") == 0)
                {
                    partfolder = "\\Root" + partfolder;
                }
                else
                {
                    partfolder = "\\Root\\" + partfolder;
                }
            }
            if(partfolder.lastIndexOf("\\") == partfolder.length() - 1)
            {
                partfolder = partfolder.substring(0, partfolder.length() - 1);
            }
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxFolder == null)
            {
                QueryCondition cond = new QueryCondition("location", "=",
                        partfolder);
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("location", "<>",
                        partfolder);
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param partproject
     * @param checkboxProject
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByProject(QMQuery query1,
            String partproject, String checkboxProject) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partproject != null && !partproject.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxProject == null)
            {
                QueryCondition cond = new QueryCondition("projectId", "=",
                        partproject);
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("projectId", "<>",
                        partproject);
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param partby
     * @param checkboxProducedBy
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByProducedBy(QMQuery query1, String partby,
            String checkboxProducedBy) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partby != null && !partby.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxProducedBy == null)
            {
                QueryCondition cond = new QueryCondition("producedByStr", "=",
                        partby);
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("producedByStr", "<>",
                        partby);
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param parttype
     * @param checkboxPartType
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByType(QMQuery query1, String parttype,
            String checkboxPartType) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(parttype != null && !parttype.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxPartType == null)
            {
                QueryCondition cond = new QueryCondition("partTypeStr", "=", parttype);
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("partTypeStr", "<>",
                        parttype);
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param partstate
     * @param checkboxLifeCState
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByLifeCycle(QMQuery query1,
            String partstate, String checkboxLifeCState) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partstate != null && !partstate.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxLifeCState == null)
            {
                QueryCondition cond = new QueryCondition("lifeCycleState", "=",
                        partstate);
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("lifeCycleState",
                        "<>", partstate);
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * @param query1
     * @param partview
     * @param checkboxView
     * @return
     * @throws QMException
     */
    private QMQuery getFindPartInfoByView(QMQuery query1, String partview,
            String checkboxView) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partview != null && !partview.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxView == null)
            {
                QueryCondition cond = new QueryCondition("viewName", "like",
                        getLikeSearchString(partview));
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("viewName",
                        "not like", getLikeSearchString(partview));
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * @param query
     * @param partver
     * @param checkboxVersion
     * @return QMQuery
     * @throws QMException
     */
    public QMQuery getFindPartInfoByVersion(QMQuery query1, String partver,
            String checkboxVersion) throws QMException
    {
        QMQuery query = query1;
        //  如果文档编号不为空
        if(partver != null && !partver.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  如果用户查询的文档中不想包括此文档编号
            }
            if(checkboxVersion == null)
            {
                QueryCondition cond = new QueryCondition("versionValue",
                        "like", getLikeSearchString(partver));
                query.addCondition(0, cond);
            }
            //  如果用户想查询此文档编号
            else
            {
                QueryCondition cond = new QueryCondition("versionValue",
                        "not like", getLikeSearchString(partver));
                query.addCondition(0, cond);
            }
        }
        return query;
    }

    /**
     * 根据零部件名称查询数据库
     * @param QMQuery query,String partname,String checkboxName
     * @return QMQuery
     * @exception QMException
     */
    public QMQuery getFindPartInfoByName(QMQuery query, String partname,
            String checkboxName) throws QMException
    {
        QMQuery qu = query;
        try
        {
            //  如果零部件名称不为空，进行查询
            if(partname != null && !partname.trim().equals(""))
            {
                //追加业务对象表
                int i = 1;
                String bsoName = null;
                try
                {
                    bsoName = qu.getBsoNameAt(i);
                }
                catch (Exception ex)
                {
                }
                if(bsoName != null)
                {
                    if(qu.getConditionCount() > 0)
                    {
                        qu.addAND();
                    }
                }
                else
                {
                    if(qu.getConditionCount() > 0)
                    {
                        qu.addAND();
                    }
                    i = qu.appendBso("QMPartMaster", false);
                    //关联2个表
                    QueryCondition qc1 = new QueryCondition("masterBsoID",
                            "bsoID");
                    qu.addCondition(0, i, qc1);
                    qu.addAND();
                }
                //  如果用户查询的零部件中不想包括此零部件名称
                if(checkboxName == null)
                {
                    QueryCondition cond = new QueryCondition("partName",
                            QueryCondition.LIKE, getLikeSearchString(partname));
                    qu.addCondition(i, cond);
                }
                //  如果用户想查询此零部件名称
                else
                {
                    QueryCondition cond = new QueryCondition("partName",
                            QueryCondition.NOT_LIKE,
                            getLikeSearchString(partname));
                    qu.addCondition(i, cond);
                }
            }
        }
        catch (Exception e)
        {
            throw new QMException(e);
        } //end catch
        return qu;
    }

    /**
     * 根据零部件编号查询数据库
     * @param  QMQuery qu,String partnumber,String checkboxNum
     * @return QMQuery
     * @exception QMException
     */
    public QMQuery getFindPartInfoByNum(QMQuery qu, String partnumber,
            String checkboxNum) throws QMException
    {
        QMQuery query = qu;
        //  如果零部件编号不为空
        if(partnumber != null && !partnumber.trim().equals(""))
        {
            int i = 1;
            String bsoName = null;
            try
            {
                bsoName = qu.getBsoNameAt(i);
            }
            catch (Exception ex)
            {
            }
            if(bsoName != null)
            {
                if(query.getConditionCount() > 0)
                {
                    query.addAND();
                }
            }
            else
            {
                if(query.getConditionCount() > 0)
                {
                    query.addAND();
                }
                i = query.appendBso("QMPartMaster", false);
                //关联2个表
                QueryCondition qc1 = new QueryCondition("masterBsoID", "bsoID");
                query.addCondition(0, i, qc1);
                query.addAND();
            }
            //  如果用户查询的零部件中不想包括此零部件编号
            if(checkboxNum == null)
            {
                QueryCondition cond = new QueryCondition("partNumber",
                        QueryCondition.LIKE, getLikeSearchString(partnumber));
                query.addCondition(i, cond);
            }
            //  如果用户想查询此零部件编号
            else
            {
                QueryCondition cond = new QueryCondition("partNumber",
                        QueryCondition.NOT_LIKE,
                        getLikeSearchString(partnumber));
                query.addCondition(i, cond);
            }
        }
        return query;
    }

    /**
     * 匹配字符查询处理。将字符串oldStr中的"/*"转化成"*"，"*"转化成"%"，"%"不处理。
     * 例 "shf/*pdm%cax*"  转化成 "shf*pdm%cax%"
     * @param oldStr
     * @return 转化后的匹配字符查询串
     */
    private String getLikeSearchString(String oldStr)
    {
        if(oldStr == null || oldStr.trim().equals(""))
        {
            return oldStr;
        }
        char ac[] = oldStr.toCharArray();
        int acLength = ac.length;
        for (int j = 0; j < acLength; j++)
        {
            if(ac[j] == '*')
            {
                if(j > 0 && ac[j - 1] == '/')
                {
                    for (int k = j - 1; k < acLength - 1; k++)
                    {
                        ac[k] = ac[k + 1];
                    } //end for k
                    acLength--;
                    ac[acLength] = ' ';
                }
                else
                {
                    ac[j] = '%';
                }
            }
            if(ac[j] == '?')
            {
                ac[j] = '_';
            }
        } //end for j
        String resultStr = (new String(ac)).trim();
        return resultStr;
    }

    /**
     * 根据时间格式字符串，转化成指定格式的时间字符串
     * @param date 要转化的时间
     * @param javaFormat 时间格式字符串
     * @return 按指定格式转化后的时间字符串
     * @throws QMException
     */
    private String getDateString(Date date, String javaFormat)
            throws QMException
    {
        String resultStr = null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(javaFormat);
        resultStr = simpledateformat.format(date);
        return resultStr;
    }

    /**
     * 根据配置规范过滤出符合配置规范的collection中所有QMPartMasterIfc的管理的QMPartIfc对象的小版本。
     * @param masterIfc MasteredIfc 待处理的值对象。
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     * @author liunan 2008-08-01
     */
    public Collection filteredIterationsOf(MasteredIfc masterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "filteredIterationsOf begin ....");
        ConfigService service = (ConfigService)EJBServiceHelper.getService("ConfigService");
        PartDebug.trace(this, PartDebug.PART_SERVICE, "filteredIterationsOf end....return is Collection");
        return service.filteredIterationsOf(masterIfc,new PartConfigSpecAssistant(configSpecIfc));
    }

    /**
     * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
     * 产品中存在的该方法依然保留，以备使用。
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
     * 本方法调用了bianli()方法实现递归。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象.
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准                                                                                     版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
     * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-01
     */
    public Vector setBOMList(QMPartIfc partIfc, String attrNames[],
                             String affixAttrNames[], String[] routeNames,
                             String source, String type,
                             PartConfigSpecIfc configSpecIfc) throws QMException {

      try {
       
        PersistService pService = (PersistService) EJBServiceHelper.getService(
            "PersistService");
        Vector vector = new Vector();
        float parentQuantity = 1.0F;

        //记录数量和编号在排序中所在的位置。
        int quantitySite = 0;
        int numberSite = 0;
        for (int i = 0; i < attrNames.length; i++) {
          String attr = attrNames[i];
          attr = attr.trim();
          if (attr != null && attr.length() > 0) {
            if (attr.equals("quantity-0")) {
              quantitySite = 4 + i;
            }
            if (attr.equals("partNumber-0")) {
              numberSite = 4 + i;
            }
          }
        }
        Vector firstElement = new Vector();
        firstElement.addElement("");
        firstElement.addElement("");
        firstElement.addElement("");
//      String ssss = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
//      firstElement.addElement(ssss);
//      ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
//      firstElement.addElement(ssss);
//      ssss = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
//      firstElement.addElement(ssss);
        String ssss = QMMessage.getLocalizedMessage(RESOURCE, "isHasSubParts", null);
        firstElement.addElement(ssss);
//      ssss = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);
//      firstElement.addElement(ssss);

        for (int i = 0; i < attrNames.length; i++) {
          String attr = attrNames[i];
          if (attr.endsWith("-0")) {
            ssss = QMMessage.getLocalizedMessage(RESOURCE,
                                                 attr.substring(0,
                attr.length() - 2), null);
            firstElement.addElement(ssss);
          }
          else if (attr.endsWith("-1")) {
        	  AbstractAttributeDefinitionIfc sd=getDefByName(attr.substring(0,
    	                attr.length() - 2));
    	     //   ibadefMap.put(attr,sd);
              firstElement.addElement(sd.getDisplayName());
           
          }
          else if (attr.endsWith("-2")) {
            firstElement.addElement(attr.substring(0, attr.length() - 2));
          }
        }

        PartUsageLinkInfo partLinkInfo = new PartUsageLinkInfo();
        HashMap refreshIBAMap = new HashMap();
        HashMap ibaPositionMap = new HashMap();
        HashMap countMap = new HashMap();
        vector = bianli(partIfc, partIfc, attrNames, affixAttrNames, routeNames,
                source, type, configSpecIfc, parentQuantity, partLinkInfo,refreshIBAMap,ibaPositionMap,countMap);
        refreshWholeIBA(refreshIBAMap, ibaPositionMap, countMap);
       
        sortTongJiVector(vector, partIfc, numberSite);
        Vector resultVector = new Vector();
        for (int i = 0; i < vector.size(); i++) {
          Object[] temp1 = (Object[]) vector.elementAt(i);
          for (int j = 0; j < temp1.length; j++) {
            if (temp1[j] == null) {
              temp1[j] = "";
            }
          }
          String partNumber1 = (String) temp1[numberSite];
          boolean flag = false;
          for (int j = 0; j < resultVector.size(); j++) {
            Object[] temp2 = (Object[]) resultVector.elementAt(j);
            String partNumber2 = (String) temp2[numberSite];
            if (partNumber1.equals(partNumber2)) {
              flag = true;
              if (quantitySite > 0) {
                float float1 = (new Float(temp1[quantitySite].toString())).
                    floatValue();
                float float2 = (new Float(temp2[quantitySite].toString())).
                    floatValue();
                Float dikeflinshi = new Float(float1 + float2);
                temp1[quantitySite] = new Integer(dikeflinshi.intValue());
              }
              resultVector.setElementAt(temp1, j);
              break;
            }
          }
          if (!flag) {
            resultVector.addElement(temp1);
          }
        }
        boolean flag1 = false;
        boolean flag2 = false;
        String source1 = (partIfc.getProducedBy()).toString();
        String type1 = (partIfc.getPartType()).toString();
        if(source != null && source.length() > 0)
        {
            if(source.equals(source1))
            {
                flag1 = true;
            }
        }
        else
        {
            flag1 = true;
        }
        if(type != null && type.length() > 0)
        {
            if(type.equals(type1))
            {
                flag2 = true;
            }
        }
        else
        {
            flag2 = true;
        }
        if(!flag1 || !flag2)
        {
            resultVector.removeElementAt(0);
        }
        else
        {
            //把第一个元素的数量改成""
            Object[] firstObj = (Object[])resultVector.elementAt(0);

            //如果输出属性有数量，则将数量设置为空。
            if(quantitySite>0)
            {
                firstObj[quantitySite] = "";
            }
            resultVector.setElementAt(firstObj, 0);
        }
        //这里才保存最后最后的结果：
        Vector result = new Vector();
        //然后，这里还需要对最后的返回值集合按照当前的source和type进行过滤：
        for(int i=0; i<resultVector.size(); i++)
        {
            Object[] element = (Object[])resultVector.elementAt(i);
            QMPartIfc onePart=null;
            boolean flag11 = false;
            boolean flag22 = false;
            if(source != null && source.length() > 0)
            {
            	
            	  onePart = (QMPartIfc)pService.refreshInfo((String)element[0]);
                if(onePart.getProducedBy().toString().equals(source))
                {
                    flag11 = true;
                }
            }
            else
            {
                flag11 = true;
            }
            if(type != null && type.length() > 0)
            {
            	if(onePart==null)
            		 onePart = (QMPartIfc)pService.refreshInfo((String)element[0]);
                if(onePart.getPartType().toString().equals(type))
                {
                    flag22 = true;
                }
            }
            else
            {
                flag22 = true;
            }
            if(flag11 && flag22)
            {
                result.addElement(element);
            }
        }

        /**boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
               boolean affixAttrNullTrueFlag = affixAttrNames == null ||
            affixAttrNames.length == 0;
               if (attrNullTrueFlag) {
          if (affixAttrNullTrueFlag) {

            ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
            firstElement.addElement(ssss);
          }
          else {
            for (int i = 0; i < affixAttrNames.length; i++) {
              String affixName = affixAttrNames[i];
              firstElement.addElement(wfutil.getDisplayName(affixName));
            }

          }
               }
               else {
          for (int i = 0; i < attrNames.length; i++) {
            String attr = attrNames[i];
            ssss = QMMessage.getLocalizedMessage(RESOURCE, attr, null);
            firstElement.addElement(ssss);
          }

          if (!affixAttrNullTrueFlag) {
            for (int i = 0; i < affixAttrNames.length; i++) {
              String affixName = affixAttrNames[i];
              firstElement.addElement(wfutil.getDisplayName(affixName));
            }

          }
               }

               //如果有工艺路线，则添加。这里添加的是名称。
               if (routeNames != null && routeNames.length > 0) {
          for (int i = 0; i < routeNames.length; i++) {
            firstElement.addElement(routeNames[i]);
          }
               }*/

        String[] tempArray = new String[firstElement.size()];
        for (int i = 0; i < firstElement.size(); i++) {
          tempArray[i] = (String) firstElement.elementAt(i);

        }
        result.insertElementAt(tempArray, 0);
        masterMap.clear();
        routs.clear();
        return result;
      }
      catch (Exception e) {
        e.printStackTrace();
        throw new QMException(e.toString());
      }
    }

    /**
     * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
     * 产品中存在的该方法依然保留，以备使用。
     * 本方法被setBOMList所调用，实现递归调用的功能。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象.
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性的集合，可以为空。
     * @param routeNames : String[] 定制的要输出的工艺路线名的集合，可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
     * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量；
     * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象；
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-01
     */
private HashMap routs=new HashMap();//begin CR2
    private Vector bianli(QMPartIfc partProductIfc, QMPartIfc partIfc,
                          String attrNames[], String affixAttrNames[],
                          String[] routeNames, String source, String type,
                          PartConfigSpecIfc configSpecIfc, float parentQuantity,
                          PartUsageLinkIfc partLinkIfc,HashMap refreshIBAMap,
                         HashMap ibaPositionMap, HashMap countMap) throws
                          
        QMException {

      //    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      Vector resultVector = new Vector();
      Vector hegeVector = new Vector();
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
     
      if (collection != null && collection.size() > 0) {
        Object[] resultArray = new Object[collection.size()];
        collection.toArray(resultArray);
        for (int i = 0; i < resultArray.length; i++) {
          boolean isHasSubParts = true;
          Object obj = resultArray[i];
          if (obj instanceof Object[]) {
            Object[] obj1 = (Object[]) obj;
            if (obj1[1] instanceof QMPartIfc) {
              if (isHasSubParts) {
                hegeVector.addElement( (Object[]) obj);
              }
            }
          }
        }

      }
      Object[] tempArray = null;
      boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
      boolean affixAttrNullTrueFlag = affixAttrNames == null ||
          affixAttrNames.length == 0;
      boolean routeNullTrueFlag = routeNames == null || routeNames.length == 0;
HashMap routenamemap=new HashMap();
Vector routec=new Vector();
      //记录数量和编号在排序中所在的位置。
      int numberSite = 0;
      for (int i = 0; i < attrNames.length; i++) {
        String attr = attrNames[i];
        attr = attr.trim();
        if (attr != null && attr.length() > 0) {
          if (attr.equals("partNumber-0")) {
            numberSite = 4 + i;
          }
          if(attr.endsWith("-2"))
          {
        	  String rn=attr.substring(0,attr.length()-2);
        	  routenamemap.put(rn, String.valueOf(i));
        	  routec.add(rn);
          }
        }
      }
      tempArray = new Object[4 + attrNames.length];
      tempArray[2] = new Integer(numberSite);
      tempArray[0] = partIfc.getBsoID();
      tempArray[1] = new Integer(1);
    
      if (hegeVector != null && hegeVector.size() > 0) {//CR2
          
          tempArray[3] = isHasSubParts;
        }
        else
        tempArray[3] = noHasSubParts;//CR2
     
      //获得零部件的工艺路线。
      TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
          getService("TechnicsRouteService");

      String parentMakeRouteFirst = null;

      for (int j = 0; j < attrNames.length; j++) {
        String attrfull = attrNames[j];
        if (attrfull.endsWith("-0")) {
          String attr = attrfull.substring(0, attrfull.length() - 2);
          if (attr != null && attr.length() > 0) {
            String temp = tempArray[1].toString();
            if (attr.equals("defaultUnit") && !temp.equals("0")) {
              Unit unit = partLinkIfc.getDefaultUnit();
              if (unit != null) {
                tempArray[4 + j] = unit.getDisplay();
              }
              else {
                tempArray[4 + j] = "";
              }
            }
            else if (attr.equals("quantity")) {
            
            	boolean islq=false;
            	float lq=partLinkIfc.getQuantity();
            
            	if(lq!=0.0)
              parentQuantity =parentQuantity*(partLinkIfc.getQuantity()); //parentQuantity*partLinkIfc.getQuantity();
            	else
            		islq=true;
            
              String tempQuantity = String.valueOf(parentQuantity);
              if (tempQuantity.endsWith(".0")) {
                tempQuantity = tempQuantity.substring(0,
                    tempQuantity.length() - 2);
              }
              if(islq)
            	  tempArray[4 + j] = String.valueOf(0);
              else
              tempArray[4 + j] = tempQuantity;
            }
            else {

              attr = attr.substring(0, 1).toUpperCase() +
                  attr.substring(1, attr.length());
              attr = "get" + attr;
              try {
                Class partClass = Class.forName(
                    "com.faw_qm.part.model.QMPartInfo");
                Method method1 = partClass.getMethod(attr, null);
                Object obj = method1.invoke(partIfc, null);
                if (obj == null) {
                  tempArray[4 + j] = "";
                }
                else {
                  if (obj instanceof String) {
                    String tempString = (String) obj;
                    if (tempString != null && tempString.length() > 0) {
                      tempArray[4 + j] = tempString;
                    }
                    else {
                      tempArray[4 + j] = "";
                    }
                  }
                  else {
                    if (obj instanceof EnumeratedType) {
                      EnumeratedType tempType = (EnumeratedType) obj;
                      if (tempType != null) {
                        tempArray[4 + j] = tempType.getDisplay();
                      }
                      else {
                        tempArray[4 + j] = "";
                      }
                    }
                  }
                }
              }
              catch (Exception ex) {
                ex.printStackTrace();
                throw new QMException(ex);
              }
            }
          }
        }
        else if (attrfull.endsWith("-1")) {//CR2
//          affixAttrNames = new String[1];
//          affixAttrNames[0] = attrfull.substring(0, attrfull.length() - 2);
//          tempArray = wfutil.getIBAValue(tempArray, affixAttrNames, partIfc,
//                                         j - 3);
//          if (tempArray[4 + j] == null) {
//            tempArray[4 + j] = "";
//          }
        	int tempCount = 0;
	        String tempString = (String) countMap.get(partIfc.getBsoID());
	        if (tempString == null) {
	          tempCount++;
	          countMap.put(partIfc.getBsoID(), String.valueOf(tempCount));
	        }
	        else {
	          tempCount = Integer.valueOf(tempString).intValue();
	          tempCount++;
	          countMap.put(partIfc.getBsoID(), String.valueOf(tempCount));
	        }
          
	        attrfull = attrfull.substring(0, attrfull.length() - 2);
	        AbstractAttributeDefinitionIfc sd=(AbstractAttributeDefinitionIfc) IBAMap.get(attrfull);
	      
	        //同时缓存下IBA属性的位置及是第几次出现
	       if(! refreshIBAMap.containsKey(partIfc.getBsoID()))//CR2
	        refreshIBAMap.put(partIfc.getBsoID(), tempArray);
	        ibaPositionMap.put(sd.getBsoID(), String.valueOf(j + 4));

//	       
	        if (tempArray[j+4] == null) {
	          tempArray[j+4] = "";
	        }
        }//CR2
      
      }
     
          routeNames = new String[routec.size()];
          for(int i=0;i<routec.size();i++)
          {
        	  routeNames[i]=routec.get(i).toString();
          }

         
          Vector routeVec =null;//CR2
          if(routs.containsKey(partIfc.getBsoID()))
        	  routeVec=(Vector)routs.get(partIfc.getBsoID());
          else
          {
        	  routeVec= trService.getListAndBrances(partProductIfc, partIfc,
              routeNames, "");
        	  routs.put(partIfc.getBsoID(), routeVec);
          }//end CR2
       
          

          if (routeVec != null && routeVec.size() > 0) {
            tempArray[1] = new Integer(routeVec.size());
            HashMap routeMap = new HashMap();
          
            for (int i = 0; i < routeVec.size(); i++) {
              routeMap = (HashMap) routeVec.elementAt(i);
           
              for (int ii = 0; ii < routeNames.length; ii++) {
                  String name=routeNames[ii];
                Object aa = routeMap.get(name);
                String ind=(String)routenamemap.get(name);
                int index=Integer.valueOf(ind).intValue();

                if (aa != null) {
                	tempArray[4 + index] = aa.toString();
                }
                else {
                	tempArray[4 + index] = "";
                }
              }
            
            }//
          
          }
          else {
            Object[] temp1 = new Object[1];
            Object[] temp2 = new Object[routeNames.length];
            for (int i = 0; i < routeNames.length; i++) {
            	String name=routeNames[i];
               
                String ind=(String)routenamemap.get(name);
                int index=Integer.valueOf(ind).intValue();
                tempArray[4 + index] = "";
            }
//        temp1[0] = temp2;
           // tempArray[4 + j] = temp2[0];
          }

       
      resultVector.addElement(tempArray);
      if (hegeVector != null && hegeVector.size() > 0) {
        for (int j = 0; j < hegeVector.size(); j++) {
          Object obj = hegeVector.elementAt(j);
          if (obj instanceof Object[]) {
            Object[] obj2 = (Object[]) obj;
            if (obj2[0] != null && obj2[1] != null) {
              Vector tempVector = bianli(partProductIfc, (QMPartIfc) obj2[1],
                                         attrNames, affixAttrNames, routeNames,
                                         source, type, configSpecIfc,
                                         parentQuantity,
                                         (PartUsageLinkIfc) obj2[0],refreshIBAMap,ibaPositionMap,countMap);
              for (int k = 0; k < tempVector.size(); k++) {
                resultVector.addElement(tempVector.elementAt(k));
              }
            }
          }
        }

      }
      return resultVector;
    }
    private Vector sortTongJiVector(Vector hehe, QMPartIfc partIfc, int numSite) {

      Object[] mainObject = null;
      String mainPartNum = partIfc.getPartNumber();
      int b = hehe.size();
      for (int i = 0; i < b; i++) {
        for (int j = i; j < b; ) {
          Object[] aa = (Object[]) hehe.elementAt(i);
          String partNum = (String) aa[numSite];
          Object[] bb = (Object[]) hehe.elementAt(j);
          String partNum1 = (String) bb[numSite];

          if (mainObject == null && partNum1.equals(mainPartNum)) {
            mainObject = bb;
            hehe.remove(j);
            b = b - 1;
          }
          else {

            if (partNum.compareTo(partNum1) > 0) {
              //      Object[] cc = (Object[]) hehe.elementAt(i);
              hehe.setElementAt(bb, i);
              hehe.setElementAt(aa, j);
            }
            j++;
          }
        }
      }
      if (mainObject != null) {
        hehe.add(0, mainObject);
      }

      return hehe;
    }
    /**屈晓光添加
     * 根据numVec集合中的编号对objectVec进行排序，numVec中的每个编号与objectVec中的是对应的，用于生成物料清单
     * @param numVec
     * @param objectVec
     * @param pp true为升序，false为降序
     * @return Vector
     */
    public Vector sort(Vector numVec, Vector objectVec, boolean pp) {
      for (int i = 0; i < numVec.size(); i++) {
        for (int j = i; j < numVec.size(); j++) {
          String max = (String) numVec.elementAt(i);
          String hehe = (String) numVec.elementAt(j);
          if (!pp) {
            if (hehe.compareTo(max) > 0) {
              numVec.setElementAt(hehe, i);
              numVec.setElementAt(max, j);
              Object[] aa = (Object[]) objectVec.get(i);
              Object[] bb = (Object[]) objectVec.get(j);
              objectVec.setElementAt(bb, i);
              objectVec.setElementAt(aa, j);

            }
          }
          if (pp) {
            if (hehe.compareTo(max) < 0) {
              numVec.setElementAt(hehe, i);
              numVec.setElementAt(max, j);
              Object[] aa = (Object[]) objectVec.get(i);
              Object[] bb = (Object[]) objectVec.get(j);
              objectVec.setElementAt(bb, i);
              objectVec.setElementAt(aa, j);

            }
          }
        }
      }
      return objectVec;
    }


    /**
     * 分级物料清单的显示。包括IBA和工艺路线。
     * 返回结果是vector,其中vector中的每个元素都是一个Object[]：
     * 0：当前part的BsoID显示位置，为页面超链接使用；
     * 1：当前part的BsoID；
     * 2：当前part所在的级别；
     * 3-...：排序的输出属性；
     * 最后元素：当前part的父件编号。
     * 本方法调用了递归方法：fenji(String parentPartNum, QMPartIfc partIfc, int level, PartUsageLinkIfc partUsageLinkIfc);
     * 为Part-Other-classifylisting-001.jsp使用。
     * @param partIfc :QMPartIfc 最顶级的部件值对象。
     * @param attrNameArray :String[] 定制的属性，可以为空。
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialList(QMPartIfc partIfc, String attrNameArray[]) throws QMException//Begin CR4：重写服务端逻辑
      {
          //分级的结构信息，vector中每个元素都是Object[5]：当前partIfc、父件编号、数量、级别、单位。
          Vector allUsageVec = fenji("", partIfc, 0, new PartUsageLinkInfo());
          TechnicsRouteService trService = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
          PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
          WfUtil wfutil = new WfUtil();
          //临时缓存part值对象。使用Map的原因是可以把重复的零部件过滤掉。
          HashMap partIfcMap = new HashMap();
          for(int i = 0;i < allUsageVec.size();i++)
          {
              Object[] usageArray = (Object[])allUsageVec.get(i);
              partIfcMap.put((QMPartIfc)usageArray[0], (QMPartIfc)usageArray[0]);
          }
          //零部件集合，无重复。
          Collection parIfcCol = partIfcMap.values();
          //零部件BsoID集合，无重复。
          String[] partBsoIDArray = new String[parIfcCol.size()];
          //零部件集合，无重复。
          QMPartIfc[] partIfcArray = new QMPartIfc[parIfcCol.size()];
          int z = 0;
          for(Iterator iterator = parIfcCol.iterator();iterator.hasNext();)
          {
              Object object = (Object)iterator.next();
              partIfcArray[z] = (QMPartIfc)object;
              partBsoIDArray[z] = ((QMPartIfc)object).getBsoID();
              z++;
          }
          //编号在定制属性集合中的位置，为获得当前part的BsoID显示位置做准备。
          int partNumberSite = 0;
          //属性定义缓存。键：defIfc.getBsoID(), 值：ibaDisPlayName。
          HashMap defMap = new HashMap();
          //属性值缓存：partBsoID + ibaDisPlayName, 属性值。
          HashMap ibaMap = new HashMap();
          //属性定义显示名称缓存：attrNames[i]：全属性名, ibaDisPlayName。
          HashMap defDisplayMap = new HashMap();
          //路线属性名称集合。正常属性名。
          Collection routeNameCol = new ArrayList();
          //路线缓存：partIfcs[i].getBsoID() + routeNames[j], 属性值。
          HashMap routeMap = new HashMap();
          if(partBsoIDArray.length > 0 && attrNameArray != null)
          {
              //循环获得定制的属性名称，并缓存起来，为后续获得属性值做准备。
              for(int i = 0;i < attrNameArray.length;i++)
              {
                  String attrName = attrNameArray[i];
                  if(attrName.equals("partNumber-0"))
                  {
                      partNumberSite = i;
                  }else if(attrName.endsWith("-1"))
                  {
                      //针对解放定制：由于解放历史数据原因，认为显示名称相同的即为相同属性。不以属性名区分。产品还应取属性名。
                      String ibaDisPlayName = wfutil.getDisplayName(attrName.substring(0, attrName.length() - 2));
                      defDisplayMap.put(attrName, ibaDisPlayName);
                      QMQuery query = new QMQuery("StringDefinition");
                      query.addCondition(new QueryCondition("displayName", QueryCondition.EQUAL, ibaDisPlayName));
                      Collection col = pService.findValueInfo(query, false);
                      for(Iterator iterator = col.iterator();iterator.hasNext();)
                      {
                          StringDefinitionIfc defIfc = (StringDefinitionIfc)iterator.next();
                          defMap.put(defIfc.getBsoID(), ibaDisPlayName);
                      }
                  }else if(attrName.endsWith("-2"))
                  {
                      routeNameCol.add(attrName.substring(0, attrName.length() - 2));
                  }
              }
              //获得所有零部件的所有IBA属性值。
              if(defMap.size() > 0)
              {
                  Object[] obj = defMap.values().toArray();
                  //属性定义显示名称集合。
                  String[] displayNames = new String[obj.length];
                  System.arraycopy(defMap.values().toArray(), 0, displayNames, 0, displayNames.length);
                  QMQuery query = new QMQuery("StringDefinition", "StringValue");
                  query.addCondition(0, new QueryCondition("displayName", QueryCondition.IN, displayNames));
                  query.addAND();
                  query.addCondition(0, 1, new QueryCondition("bsoID", "definitionBsoID"));
                  query.addAND();
                  int length = partBsoIDArray.length;
                  int index = 0;
                  if(length == 1)
                      query.addCondition(1, new QueryCondition("iBAHolderBsoID", QueryCondition.EQUAL, partBsoIDArray[0]));
                  else if(length > 1000)
                  {
                      query.addLeftParentheses();
                      while(length > 1000)
                      {
                          String[] tempArray1 = new String[1000];
                          System.arraycopy(partBsoIDArray, index, tempArray1, 0, 1000);
                          length = length - 1000;
                          index = index + 1000;
                          query.addCondition(1, new QueryCondition("iBAHolderBsoID", "IN", tempArray1));
                          query.addOR();
                      }
                      String[] tempArray2 = new String[length];
                      System.arraycopy(partBsoIDArray, index, tempArray2, 0, length);
                      query.addCondition(1, new QueryCondition("iBAHolderBsoID", "IN", tempArray2));
                      query.addRightParentheses();
                  }else
                      query.addCondition(1, new QueryCondition("iBAHolderBsoID", QueryCondition.IN, partBsoIDArray));
                  //结果集只返回StringValue值对象。
                  query.setVisiableResult(0);
                  Collection col = pService.findValueInfo(query, false);
                  for(Iterator iterator2 = col.iterator();iterator2.hasNext();)
                  {
                      StringValueIfc valueIfc = (StringValueIfc)iterator2.next();
                      ibaMap.put(valueIfc.getIBAHolderBsoID() + defMap.get(valueIfc.getDefinitionBsoID()), valueIfc.getValue());
                  }
              }
              //获得所有零部件的所有工艺路线属性值。
              if(routeNameCol.size() > 0)
              {
                  String[] routeNameArray = new String[routeNameCol.size()];
                  System.arraycopy(routeNameCol.toArray(), 0, routeNameArray, 0, routeNameArray.length);
                  for(int i = 0;i < partIfcArray.length;i++)
                  {
                      Vector routeVec = trService.getListAndBrances(partIfc, partIfcArray[i], routeNameArray, "");
                      if(routeVec != null && routeVec.size() > 0)
                      {
                          //routeVec只有一个HashMap元素。键：属性名；值：属性值。
                          HashMap map = (HashMap)routeVec.get(0);
                          for(int j = 0;j < routeNameArray.length;j++)
                          {
                              if(map.get(routeNameArray[j]) == null)
                              {
                                  routeMap.put(partIfcArray[i].getBsoID() + routeNameArray[j], "");
                              }else
                              {
                                  routeMap.put(partIfcArray[i].getBsoID() + routeNameArray[j], map.get(routeNameArray[j]));
                              }
                          }
                      }
                  }
              }
          }
          Class partClass;
          try
          {
              partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
          }catch(ClassNotFoundException e)
          {
              e.printStackTrace();
              throw new QMException(e);
          }
          //方法缓存：attrFull, method。
          HashMap methodMap = new HashMap();
          //属性缓存：attrFull, attr。
          HashMap attrMap = new HashMap();
          //返回结果集。
          Vector resultVector = new Vector();
          for(Iterator iterator = allUsageVec.iterator();iterator.hasNext();)
          {
              //五个元素的数组：当前partIfc、父件编号、数量、级别、单位。
              Object[] usageArray = (Object[])iterator.next();
              String partBsoID = ((QMPartIfc)usageArray[0]).getBsoID();
              //只是定制属性值的数组。不是最终的返回结果。
              Object[] resultArray1 = new Object[attrNameArray.length];
              for(int j = 0;j < attrNameArray.length;j++)
              {
                  String attrFull = attrNameArray[j];
                  //获得基本属性值。
                  if(attrFull.endsWith("-0"))
                  {
                      String attr = null;
                      if(attrMap.containsKey(attrFull))
                          attr = (String)attrMap.get(attrFull);
                      else
                      {
                          attr = attrFull.substring(0, attrFull.length() - 2);
                          attrMap.put(attrFull, attr);
                      }
                      if(attr != null && attr.length() > 0)
                      {
                          if(attr.equals("defaultUnit") && !usageArray[2].equals(""))
                          {
                              resultArray1[j] = usageArray[4];
                          }else if(attr.equals("quantity"))
                          {
                              resultArray1[j] = usageArray[2];
                          }else
                          {
                              try
                              {
                                  Method method = null;
                                  if(methodMap.containsKey(attrFull))
                                  {
                                      method = (Method)methodMap.get(attrFull);
                                  }else
                                  {
                                      StringBuffer getAttr = new StringBuffer();
                                      getAttr = getAttr.append("get").append(attr.substring(0, 1).toUpperCase()).append(attr.substring(1, attr.length()));
                                      method = partClass.getMethod(getAttr.toString(), null);
                                      methodMap.put(attrFull, method);
                                  }
                                  if(method != null)
                                  {
                                      Object obj = method.invoke(usageArray[0], null);
                                      if(obj == null)
                                      {
                                          resultArray1[j] = "";
                                      }else if(obj instanceof EnumeratedType)
                                      {
                                          EnumeratedType tempType = (EnumeratedType)obj;
                                          if(tempType != null)
                                          {
                                              resultArray1[j] = tempType.getDisplay();
                                          }else
                                          {
                                              resultArray1[j] = "";
                                          }
                                      }else
                                          resultArray1[j] = obj;
                                  }else
                                  {
                                      resultArray1[j] = "";
                                  }
                              }catch(Exception ex)
                              {
                                  ex.printStackTrace();
                                  throw new QMException(ex);
                              }
                          }
                      }else
                          resultArray1[j] = "";
                  }
                  //获得IBA属性值。
                  else if(attrFull.endsWith("-1"))
                  {
                      if(ibaMap.containsKey(partBsoID + defDisplayMap.get(attrFull)))
                          resultArray1[j] = ibaMap.get(partBsoID + defDisplayMap.get(attrFull));
                      else
                          resultArray1[j] = "";
                  }
                  //获得工艺路线属性值。
                  else if(attrFull.endsWith("-2"))
                  {
                      String attr = attrFull.substring(0, attrFull.length() - 2);
                      if(routeMap.containsKey(partBsoID + attr))
                          resultArray1[j] = routeMap.get(partBsoID + attr);
                      else
                      {
                          resultArray1[j] = "";
                      }
                  }
              }
              //最终返回结果数组。增加了bsoid位置、当前bsoid、级别和父件编号信息。
              Object[] resultArray2 = new Object[attrNameArray.length + 4];
              System.arraycopy(resultArray1, 0, resultArray2, 3, resultArray1.length);
              //bsoid位置
              resultArray2[0] = new Integer(partNumberSite + 3);
              //当前bsoid
              resultArray2[1] = partBsoID;
              //级别
              resultArray2[2] = usageArray[3];
              //父件编号
              resultArray2[resultArray2.length - 1] = usageArray[1];
              resultVector.add(resultArray2);
          }
          //构造标题
          ArrayList firstList = new ArrayList(attrNameArray.length);
          firstList.add(QMMessage.getLocalizedMessage(RESOURCE, "level", null));
          for(int i = 0;i < attrNameArray.length;i++)
          {
              String attrName = attrNameArray[i];
              if(attrName.endsWith("-0"))
              {
                  firstList.add(QMMessage.getLocalizedMessage(RESOURCE, attrName.substring(0, attrName.length() - 2), null));
              }else if(attrName.endsWith("-1"))
              {
                  firstList.add(wfutil.getDisplayName(attrName.substring(0, attrName.length() - 2)));
              }else if(attrName.endsWith("-2"))
              {
                  firstList.add(attrName.substring(0, attrName.length() - 2));
              }
          }
          firstList.add("上一级父件编号");//CR5 添加"上一级父件编号"列。
          resultVector.insertElementAt(firstList.toArray(), 0);
          return resultVector;
      }//End CR4：重写服务端逻辑

  /**
   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
   * 产品中存在的该方法依然保留，以备使用。
   * liuming add 20070209
   * 无合件装配表的显示。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。
   * 本方法调用了递归方法：
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。
   * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范。
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-01
   */
  public Vector setMaterialList2(QMPartIfc partIfc, String attrNames[],
                                String affixAttrNames[], String[] routeNames,
                                PartConfigSpecIfc configSpecIfc) throws
      QMException {
    try {
      WfUtil wfutil = new WfUtil();
      int level = 0;
      PartUsageLinkInfo partLinkInfo = new PartUsageLinkInfo();
      float parentQuantity = 1.0F;

      int quantitySite = 0;
      for (int i = 0; i < attrNames.length; i++) {
        String attr = attrNames[i];
        attr = attr.trim();
        if (attr != null && attr.length() > 0) {
          if (attr.equals("quantity-0")) {
            quantitySite = 4 + i;
          }
        }
      }

      Vector vector = null;

      vector = fenji2(partIfc, partIfc, attrNames, affixAttrNames, routeNames,
                     configSpecIfc, level, partLinkInfo, parentQuantity, "",
                     parentQuantity);
      if(vector != null && vector.size() > 0)
      {
        Object first[] = (Object[]) vector.elementAt(0);
        if (quantitySite > 0) {
        	//CCBegin by liunan 2008-10-13 数量的位置应该在下一列。
        	//源码如下
          //first[quantitySite] = "";
          first[quantitySite+1] = "";
          //CCEnd by liunan 2008-10-13
        }
        vector.setElementAt( ( (Object) (first)), 0);
      }
      Vector firstElement = new Vector();
      firstElement.addElement("");
      firstElement.addElement("");
      firstElement.addElement("");
      String ssss = QMMessage.getLocalizedMessage(RESOURCE, "level", null);
      firstElement.addElement(ssss);

      for (int i = 0; i < attrNames.length; i++) {
        String attr = attrNames[i];
        if (attr.endsWith("-0")) {
          ssss = QMMessage.getLocalizedMessage(RESOURCE,
                                               attr.substring(0,
              attr.length() - 2), null);
          firstElement.addElement(ssss);
        }
        else if (attr.endsWith("-1")) {
          firstElement.addElement(wfutil.getDisplayName(attr.substring(0,
              attr.length() - 2)));
        }
        else if (attr.endsWith("-2")) {
          firstElement.addElement(attr.substring(0, attr.length() - 2));
        }
      }
      firstElement.addElement("上一级父件编号");//yanqi-20061222-在分级表上增加父件号

      String[] tempArray = new String[firstElement.size()];
      for (int i = 0; i < firstElement.size(); i++) {
        tempArray[i] = (String) firstElement.elementAt(i);
      }
      vector.insertElementAt( ( (Object[]) (tempArray)), 0);

      for (int i = 0; i < vector.size(); i++) {
        Object temp[] = (Object[]) vector.elementAt(i);
        for (int j = 0; j < temp.length; j++) {
          if (temp[j] == null) {
            temp[j] = "";
          }
        }
      }

      Vector v2 = new Vector() ;
      if(vector != null && vector.size() > 0)
      {
        v2.addElement(vector.firstElement());
        for(int i=1;i<vector.size() ;i++)
        {
          Object[] gg = (Object[])vector.elementAt(i);
          String isLogic = gg[4].toString();
          if(isLogic.equalsIgnoreCase("false"))
          {
            Object[] hh = new Object[gg.length-1];
            hh[0]=gg[0];
            hh[1]=gg[1];
            hh[2]=gg[2];
            hh[3]=gg[3];
            for(int j=4;j<hh.length;j++)
            {
              hh[j]=gg[j+1];
            }
            v2.addElement(hh);
          }
        }
      }
      vector = v2;

      return vector;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e.toString());
    }
  }


  /**
   * 被setMaterialList(QMPartIfc partIfc, String attrNames[])方法调用，实现定制分级物料清单的功能。 
   * 返回结果是vector,其中vector中的每个元素都是一个数组： 
   * 0：当前part的值对象； 
   * 1：当前part的父件编号； 
   * 2：数量； 
   * 3：所在的级别； 
   * 4：单位。
   * @param parentPartNum 父件编号。
   * @param partIfc 当前零部件。
   * @param level 所在的级别。
   * @param partUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象。
   * @return Vector
   * @throws QMException
   */
  private Vector fenji(String parentPartNum, QMPartIfc partIfc, int level, PartUsageLinkIfc partUsageLinkIfc) throws QMException//Begin CR4：重写服务端逻辑
  {
      Vector resultVector = new Vector();
      //五个元素的数组：当前partIfc、父件编号、数量、级别、单位。
      Object[] usageArray = new Object[5];
      usageArray[0] = partIfc;
      usageArray[1] = parentPartNum;
      //设置数量。
      if(level == 0)
      {
          usageArray[2] = "";
      }else
      {
          String quantity = String.valueOf(partUsageLinkIfc.getQuantity());
          if(quantity.endsWith(".0"))
          {
              quantity = quantity.substring(0, quantity.length() - 2);
          }
          usageArray[2] = quantity;
      }
      //设置级别。
      usageArray[3] = new Integer(level); //level的初始值为0。
      //设置单位。
      Unit unit = partUsageLinkIfc.getDefaultUnit();
      if(unit != null)
          usageArray[4] = unit.getDisplay();
      else
          usageArray[4] = "";
      resultVector.add(usageArray);
      //得到排序后的子件信息。集合中的每个元素都是Object[2]：0：PartUsageLinkIfc；1：QMPartIfc。
      Collection collection = sort(getUsesPartIfcs(partIfc));
      if(collection == null || collection.size() == 0)
      {
          return resultVector;
      }
      Object temp[] = collection.toArray();
      level++;
      //作为fenji的父件编号参数。
      String partNumber = partIfc.getPartNumber();
      for(int i = 0;i < temp.length;i++)
      {
          if(temp[i] instanceof Object[])
          {
              Object obj[] = (Object[])temp[i];
              resultVector.addAll(fenji(partNumber, (QMPartIfc)obj[1], level, (PartUsageLinkIfc)obj[0]));
          }
      }
      level--;
      return resultVector;
  }//End CR4：重写服务端逻辑

  /**
   * 同级子件需要根据编号排序。
   * @param collection 集合中的每个元素都是Object[2]：0：PartUsageLinkIfc；1：QMPartIfc。
   * @return Vector
   */
  public Collection sort(Collection collection)//Begin CR4：重写服务端逻辑
  {
      Object[] objectArray = (Object[])collection.toArray();
      Vector objectVector = new Vector();
      for(int i = 0;i < objectArray.length;i++)
      {
          if(objectArray[i] instanceof Object[])
          {
              Object[] objectArray2 = (Object[])objectArray[i];
              if((objectArray2[1] instanceof QMPartIfc) && (objectArray2[0] instanceof PartUsageLinkIfc))
              {
                  for(int j = i;j < objectArray.length;j++)
                  {
                      Object[] obj1 = (Object[])objectArray[j];
                      Object[] obj2 = (Object[])objectArray[i];
                      if((obj1[1] instanceof QMPartIfc) && (obj1[0] instanceof PartUsageLinkIfc))
                      {
                          String partNUM1 = ((QMPartIfc)obj1[1]).getPartNumber();
                          String partNUM2 = ((QMPartIfc)obj2[1]).getPartNumber();
                          if(partNUM2.compareTo(partNUM1) > 0)
                          {
                              objectArray[i] = obj1;
                              objectArray[j] = obj2;
                          }
                      }
                  }
                  objectVector.add(objectArray[i]);
              }
          }
      }
      return objectVector;
  }//End CR4：重写服务端逻辑

  /**
   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
   * 产品中存在的该方法依然保留，以备使用。
   * liuming add 20070209
   * 私有方法。被setMaterialList2()方法调用，实现定制无合件装配表的功能。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。

   * @param partIfc :QMPartIfc 当前的部件；
   * @param attrNames :String[] 定制的属性集合，可以为空；
   * @param affixAttrNames :String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames :String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范；
   * @param level :int 当前part所在的级别；
   * @param partLinkIfc :PartUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象；
   * @param parentQuantity :int 当前part的父亲节点被最顶级部件使用的数量。
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-01
   */
  private Vector fenji2(QMPartIfc partProductIfc, QMPartIfc partIfc,
                       String attrNames[], String affixAttrNames[],
                       String[] routeNames, PartConfigSpecIfc configSpecIfc,
                       int level, PartUsageLinkIfc partLinkIfc,
                       float parentQuantity, String parentFirstMakeRoute,
                       float parentInProductCount) throws
      QMException {
    Vector resultVector = new Vector();
    Object[] tempArray = null;
    WfUtil wfutil = new WfUtil();
    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
    //记录数量和编号在排序中所在的位置。
    int numberSite = 0;
    for (int i = 0; i < attrNames.length; i++) {
      String attr = attrNames[i];
      attr = attr.trim();
      if (attr != null && attr.length() > 0) {
        if (attr.equals("partNumber-0")) {
          numberSite = 4 + i;
        }
      }
    }
    if (attrNullTrueFlag) {
      //tempArray = new Object[4];
      tempArray = new Object[6];//yanqi-20061222 增加“父件编号”属性
      tempArray[2] = new Integer(numberSite);//yanqi-20061222
    }
    else {
      //tempArray = new Object[4 + attrNames.length];
      tempArray = new Object[6 + attrNames.length];//yanqi-20061222增加“父件编号”属性
      tempArray[2] = new Integer(numberSite);
    }
    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
        affixAttrNames.length == 0;
    boolean routeNullTrueFlag = routeNames == null ||
        routeNames.length == 0;

    tempArray[0] = partIfc.getBsoID();
    tempArray[1] = new Integer(1);
    tempArray[3] = new Integer(level); //level的初始值为0。

    ///////////标记当前零件是否是逻辑总成 liuming 20070303 add
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
      tempArray[4]="true";
    }
    else
    {
      tempArray[4]="false";
    }
    ///////////////////////////////liuming 20070303 add end

    //获得零部件的工艺路线。
    TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
        getService("TechnicsRouteService");
    float countInparent = parentInProductCount * parentQuantity; //quxg add for jiefang 此变量并不一定是在直接父亲下的数量,而是其制造路线所取的直接或间接父亲下的使用总数,比如,其直接父亲为逻辑总成,没有路线,那就取逻辑总成父亲下的数量,如此往复,但总称数量要计算在内

    String parentMakeRouteFirst = "";
    Vector routeVec = null;
    for (int j = 0; j < attrNames.length; j++) {
      String attrfull = attrNames[j];
      if (attrfull.endsWith("-0")) {
        String attr = attrfull.substring(0, attrfull.length() - 2);
        if (attr != null && attr.length() > 0) {
          String temp = tempArray[1].toString();
          if (attr.equals("defaultUnit") && !temp.equals("0")) {
            Unit unit = partLinkIfc.getDefaultUnit();
            if (unit != null) {
              tempArray[5 + j] = unit.getDisplay();
            }
            else {
              tempArray[5 + j] = "";
            }
          }
          else if (attr.equals("quantity")) {
            if (level == 0) {
              parentQuantity = 1f;
              String quan = "1";
              tempArray[5 + j] = new String(quan);
            }
            else {
              parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
              String tempQuantity = String.valueOf(parentQuantity);
              if (tempQuantity.endsWith(".0")) {
                tempQuantity = tempQuantity.substring(0,
                    tempQuantity.length() - 2);
              }
              tempArray[5 + j] = tempQuantity;
            }
          }
          else {

            attr = attr.substring(0, 1).toUpperCase() +
                attr.substring(1, attr.length());
            attr = "get" + attr;

            try {
              Class partClass = Class.forName(
                  "com.faw_qm.part.model.QMPartInfo");
              Method method1 = partClass.getMethod(attr, null);
              Object obj = method1.invoke(partIfc, null);
              if (obj == null) {
                tempArray[5 + j] = "";
              }
              else {
                if (obj instanceof String) {
                  String tempString = (String) obj;
                  if (tempString != null && tempString.length() > 0) {
                    tempArray[5 + j] = tempString;
                  }
                  else {
                    tempArray[5 + j] = "";
                  }
                }
                else {
                  if (obj instanceof EnumeratedType) {
                    EnumeratedType tempType = (EnumeratedType) obj;
                    if (tempType != null) {
                      tempArray[5 + j] = tempType.getDisplay();
                    }
                    else {
                      tempArray[5 + j] = "";
                    }
                  }
                }
              }
            }
            catch (Exception ex) {
              ex.printStackTrace();
              throw new QMException(ex);
            }
          }

        }

      }
      else if (attrfull.endsWith("-1")) {
        affixAttrNames = new String[1];
        affixAttrNames[0] = attrfull.substring(0, attrfull.length() - 2);
        tempArray = wfutil.getIBAValue(tempArray, affixAttrNames, partIfc,
                                       j - 3);
        if (tempArray[5 + j] == null) {
          tempArray[5 + j] = "";
        }
      }
      else if (attrfull.endsWith("-2")) {
        routeNames = new String[1];
        routeNames[0] = attrfull.substring(0, attrfull.length() - 2);
        if (routeVec == null) {
          routeVec = trService.getListAndBrances(partProductIfc, partIfc,
                                                 routeNames,
                                                 parentFirstMakeRoute);

        }

        if (routeVec != null && routeVec.size() > 0) {
          HashMap map = (HashMap) routeVec.elementAt(0);
          String makeRoute = (String) map.get("制造路线");
          if (makeRoute == null) {
            makeRoute = "";
          }
          if (makeRoute.indexOf("...") < 0) { //bushi逻辑总成
            parentMakeRouteFirst = "";
            StringTokenizer yy = new StringTokenizer(makeRoute, "/");
            while (yy.hasMoreTokens()) {
              String aa = yy.nextToken();
              StringTokenizer MM = new StringTokenizer(aa, "--");
              parentMakeRouteFirst += MM.nextToken() + "/";
            }
            if (parentMakeRouteFirst.endsWith("/")) {
              parentMakeRouteFirst = parentMakeRouteFirst.substring(0,
                  parentMakeRouteFirst.length() - 1);
            }
            parentMakeRouteFirst = parentMakeRouteFirst + "..." +
                partIfc.getPartNumber();
          }
          else { //是逻辑总成
            parentMakeRouteFirst = makeRoute;
          }

          if (partIfc.getPartType().toString().equalsIgnoreCase("Logical") &&
              (makeRoute != null && makeRoute.length() > 0)) {
            parentInProductCount = countInparent;
          }
          else {
            parentInProductCount = 1.0F;
          }

          String assRoute3 = (String) map.get("装配路线");
          if (assRoute3 == null) {
            assRoute3 = "";
          }
          int o = assRoute3.indexOf("...");
          if (o > 0) {
            map.put("装入合件数量", new Integer( (new Float(countInparent)).intValue()));
            String realassRoute = assRoute3.substring(0, o);
            String realParentNum = assRoute3.substring(o + 3);
            map.put("装配路线", realassRoute);
            map.put("装配路线合件号", realParentNum);

          }

//quxg add finish

          tempArray[1] = new Integer(routeVec.size());
          Object[] tempRoute = new Object[routeVec.size()];
          String[] tempArray1 = new String[routeNames.length];
          for (int ii = 0; ii < routeNames.length; ii++) {
            if (map.get(routeNames[ii]) == null) {
              tempArray1[ii] = "";
            }
            else {
              if (partIfc.getPartType().toString().equalsIgnoreCase("Logical")) {
                tempArray1[ii] = "";
              }
              else {
                tempArray1[ii] = map.get(routeNames[ii].trim()).toString();
              }
            }
          }
          tempRoute[0] = tempArray1;

          tempArray[5 + j] = tempArray1[0];
        }
        else {
          Object[] temp1 = new Object[1];
          Object[] temp2 = new Object[routeNames.length];
          for (int i = 0; i < routeNames.length; i++) {
            temp2[i] = "";
          }
          temp1[0] = temp2;
          tempArray[5 + j] = temp2[0];
        }
      }
    }
    //yanqi-20061222-增加父件编号属性
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
      QMPartIfc part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
      if(part!=null)
        tempArray[tempArray.length-1]=part.getPartNumber();
    }//yanqi－20061222
    resultVector.addElement( ( (Object[]) (tempArray)));
    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);

    /////////////////////////////////////////排序 屈晓光添加

    Object[] ququ = (Object[]) collection.toArray();
    Vector objectVec = new Vector();
    for (int k = 0; k < ququ.length; k++)
    {
        for (int j = k; j < ququ.length; j++)
        {
            Object[] obj1 = (Object[]) ququ[j];
            Object[] obj2 = (Object[]) ququ[k];
              String partNUM2 = ( (QMPartIfc) obj2[1]).getPartNumber();
              String partNUM1 = ( (QMPartIfc) obj1[1]).getPartNumber();
              if (partNUM2.compareTo(partNUM1) > 0) {
                ququ[k] = obj1;
                ququ[j] = obj2;
              }
          }
          objectVec.add(ququ[k]);
      collection = objectVec;
    }
    //////////////////////////////////排序完毕

    if (collection == null || collection.size() == 0) {
      return resultVector;
    }
    Object temp[] = collection.toArray();
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
    }
    else
    {
      level++;
    }
    for (int k = 0; k < temp.length; k++)
    {
        Object obj[] = (Object[]) temp[k];
        Vector tempResult = fenji2(partProductIfc, (QMPartIfc) obj[1], attrNames,
                             affixAttrNames, routeNames, configSpecIfc, level,
                             (PartUsageLinkIfc) obj[0], parentQuantity,
                             parentMakeRouteFirst == null ? "" :
                             parentMakeRouteFirst, parentInProductCount);

        for (int m = 0; m < tempResult.size(); m++) {
          resultVector.addElement(tempResult.elementAt(m));
        }

    }

    level--;
    return resultVector;
  }


//屈晓光添加 将物料清单输出成本地文件
  /**
  * 将物料清单输出成本地文件，用于分级报表的输出,对逻辑总成的数量作了处理.。
  * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
  * @param map HashMap 整理好的报表属性集合。
  * @throws QMException
  * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
  * @author leix 2010-12-22
  */
	//CCBegin by leix	 2010-12-20  增加逻辑总成数量报表
  public String getExportBOMClassfiyLogicString(HashMap map) throws QMException {

	    StringBuffer backBuffer = new StringBuffer();
	    try
	    {
	    String PartID = (String) map.get("PartID");
	    PersistService persistService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
	    String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
	        ")" + part.getVersionValue() +
	        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
	         ("(" + part.getViewName() + ")")) +
	        "的物料清单" +
	        "\n";
	    backBuffer.append(head);
	    //String attributeName = (String) map.get("attributeName");
	    String attributeName1 = (String) map.get("attributeName1");
	    PartServiceHelper partHelper = new PartServiceHelper();
	    Vector vec = partHelper.getMaterialLogicList(PartID, attributeName1);
	    String table = "";
	    Object[] tableHeader = (Object[])vec.elementAt(0);//CR5
	    for (int i = 0; i < tableHeader.length; i++) {
	      String colName = (String)tableHeader[i];//CR5
	      if (colName != null && colName.length() > 0) {
	        table += colName + ",";
	      }
	    }
	    table += "\n";
	    backBuffer.append(table);
	    for (int i = 1; i < vec.size(); i++) {
	      Object[] information = (Object[]) vec.elementAt(i);
	      for (int j = 3; j < information.length; j++) {//CR5 不能输出"级别"，原先j=3，改为j=2。
	        Object hh = information[j];
	        writeInformation(backBuffer, hh);
	      }

	      backBuffer.append("\n");
	    }
	  }
	  catch(Exception e)
	  {
	  	e.printStackTrace();
	  }
	    return backBuffer.toString();	    
  }
	//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
	
	//CCBegin SS7
  /**
  * 将物料清单输出成本地文件，用于分级报表的输出,对逻辑总成的数量作了处理.。
  * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
  * @param map HashMap 整理好的报表属性集合。
  * @throws QMException
  * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
  */
  public String getExportFirstLeveList(HashMap map) throws QMException
  {
	    StringBuffer backBuffer = new StringBuffer();
	    try
	    {
	    String PartID = (String) map.get("PartID");
	    PersistService persistService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
	    String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
	        ")" + part.getVersionValue() +
	        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
	         ("(" + part.getViewName() + ")")) +
	        "的整车一级件清单" +
	        "\n";
	    backBuffer.append(head);
	    String attributeName1 = (String) map.get("attributeName1");
	    PartServiceHelper partHelper = new PartServiceHelper();
	    Vector vec = partHelper.getExportFirstLeveList(PartID, attributeName1);
	    String table = "";
	    Object[] tableHeader = (Object[])vec.elementAt(0);
	    for (int i = 0; i < tableHeader.length; i++)
	    {
	      String colName = (String)tableHeader[i];
	      if (colName != null && colName.length() > 0)
	      {
	        table += colName + ",";
	      }
	    }
	    table += "\n";
	    backBuffer.append(table);
	    for (int i = 1; i < vec.size(); i++) {
	      Object[] information = (Object[]) vec.elementAt(i);
	      for (int j = 3; j < information.length; j++) {//CR5 不能输出"级别"，原先j=3，改为j=2。
	        Object hh = information[j];
	        writeInformation(backBuffer, hh);
	      }
	      backBuffer.append("\n");
	    }
	  }
	  catch(Exception e)
	  {
	  	e.printStackTrace();
	  }
	    return backBuffer.toString();	    
  }
	//CCEnd SS7
  
  /**
   * 将物料清单输出成本地文件，用于分级报表的输出。
   * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
  public String getExportBOMClassfiyString(HashMap map) throws QMException {
    StringBuffer backBuffer = new StringBuffer();
    try
    {
    String PartID = (String) map.get("PartID");
    PersistService persistService = (PersistService) EJBServiceHelper.
        getPersistService();
    QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
    String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "的物料清单" +
        "\n";
    backBuffer.append(head);
    //String attributeName = (String) map.get("attributeName");
    String attributeName1 = (String) map.get("attributeName1");
    PartServiceHelper partHelper = new PartServiceHelper();
    Vector vec = partHelper.getMaterialList(PartID, attributeName1);
    String table = "";
    Object[] tableHeader = (Object[])vec.elementAt(0);//CR5
    for (int i = 0; i < tableHeader.length; i++) {
      String colName = (String)tableHeader[i];//CR5
      if (colName != null && colName.length() > 0) {
        table += colName + ",";
      }
    }
    table += "\n";
    backBuffer.append(table);
    for (int i = 1; i < vec.size(); i++) {
      Object[] information = (Object[]) vec.elementAt(i);
      for (int j = 3; j < information.length; j++) {//CR5 不能输出"级别"，原先j=3，改为j=2。
        Object hh = information[j];
        writeInformation(backBuffer, hh);
      }

      backBuffer.append("\n");
    }
  }
  catch(Exception e)
  {
  	e.printStackTrace();
  }
    return backBuffer.toString();
  }

  //liuming 20070209 add
  /**
   * 将物料清单输出成本地文件，用于无合件装配表的输出。
   * 在com.faw_qm.part.client.other.view.LogicBomFrame调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
    public String getExportBOMClassfiyString2(HashMap map) throws QMException {
      StringBuffer backBuffer = new StringBuffer();
      String PartID = (String) map.get("PartID");
      PersistService persistService = (PersistService) EJBServiceHelper.
          getPersistService();
      QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
      String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
          ")" + part.getVersionValue() +
          ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
           ("(" + part.getViewName() + ")")) +
          "的物料清单" +
          "\n";
      backBuffer.append(head);
      //String attributeName = (String) map.get("attributeName");
      String attributeName1 = (String) map.get("attributeName1");
      PartServiceHelper partHelper = new PartServiceHelper();
      //CCBegin SS4
//      Vector vec = partHelper.getMaterialList2(PartID, attributeName1);
      String comp=RouteClientUtil.getUserFromCompany();
      Vector vec=new Vector();
      if(comp.equals("zczx")){
    	  String source=(String)map.get("source");
    	  String type=(String)map.get("type");
    	  String makeV=(String)map.get("makeV");
    	  String conV=(String)map.get("conV");
    	  vec=partHelper.getSubCompBom(PartID,attributeName1, source, type,makeV,conV);
      }{
          vec = partHelper.getMaterialList2(PartID, attributeName1);
      }
      //CCEnd SS4
      String table = "";
      String[] tableHeader = (String[]) vec.elementAt(0);
      for (int i = 0; i < tableHeader.length; i++) {
        String colName = tableHeader[i];
        if (colName != null && colName.length() > 0) {
          table += colName + ",";
        }
      }
      table += "\n";
      backBuffer.append(table);
      for (int i = 1; i < vec.size(); i++) {
        Object[] information = (Object[]) vec.elementAt(i);
        for (int j = 3; j < information.length; j++) {
          Object hh = information[j];
          writeInformation(backBuffer, hh);
        }

        backBuffer.append("\n");
      }
      return backBuffer.toString();
  }

//屈晓光添加 将物料清单输出成本地文件
  /**
   * 整理数据，将报表中每行的数据，各值间用“,”分割开，用于生成本地excel文件时，
   * @param backBuffer StringBuffer 报表数据整理成的字符集合。
   * @param object Object
   * @author liunan 2008-08-01
   */
  private void writeInformation(StringBuffer backBuffer, Object object) {
    String hehe = "";
    if ( (object instanceof String) || (object instanceof Integer)) {
      hehe += object + ",";
      int r1 = hehe.indexOf("\n");
      if (r1 > 0) {
        hehe = hehe.substring(0, r1) + "  " +
            hehe.substring(r1 + 1);
      }
      backBuffer.append(hehe);
    }
    else {
      Object[] kk = (Object[]) object;
      for (int i = 0; i < kk.length; i++) {
        Object[] kiki = (Object[]) kk[i];
        String qq = "";
        for (int j = 0; j < kiki.length; j++) {
          String jojo = (String) kiki[j];
          int r1 = jojo.indexOf("\n");
          if (r1 > 0) {
            jojo = jojo.substring(0, r1) + "  " +
                jojo.substring(r1 + 1);
          }
          qq += jojo + " ,";
        }
        backBuffer.append(qq + ",");
      }
    }

  }

//屈晓光添加 将物料清单输出成本地文件
  /**
   * 将物料清单输出成本地文件，用于统计报表的输出。
   * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
  public String getExportBOMStatisticsString(HashMap map) throws QMException {
    String partBsoID = (String) map.get("PartID");
    //  String attributeName = (String) map.get("attributeName");
    String attributeName1 = (String) map.get("attributeName1");
    String source = (String) map.get("source");
    String type = (String) map.get("type");
    PartServiceHelper helper = new PartServiceHelper();
    StringBuffer backBuffer = new StringBuffer();
    QMPartIfc part = (QMPartIfc) helper.getObjectForID(partBsoID);

    String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "的零部件明细表\n";
    backBuffer.append(head);
    //CCBegin SS4
//    vec = helper.getBOMList(partBsoID, attributeName1, source,
//			type);
	String comp = RouteClientUtil.getUserFromCompany();
	Vector vec= new Vector();
	if (comp.equals("zczx")) {
		String makeV = (String) map.get("makeV");
		String conV = (String) map.get("conV");
	    vec = helper.getSubCompBom(partBsoID, attributeName1,
					source, type, makeV, conV);
	} else {
		vec = helper.getBOMList(partBsoID, attributeName1, source,
					type);
	}
	//CCEnd SS4
	String table = "";
    String[] tableHeader = (String[]) vec.elementAt(0);
    for (int i = 0; i < tableHeader.length; i++) {
      String colName = tableHeader[i];
      if (colName != null && colName.length() > 0) {
        table += colName + ",";
      }
    }
    table += "\n";
    backBuffer.append(table);
    for (int i = 1; i < vec.size(); i++) {
      Object[] information = (Object[]) vec.elementAt(i);
      for (int j = 3; j < information.length; j++) {
        Object hh = information[j];
        writeInformation(backBuffer, hh);
      }
      backBuffer.append("\n");
    }
    return backBuffer.toString();
  }
  //CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"
  /*
   *quxg 添加 ,获得在某个结构下其下的所有子部件的数量,键为子part baoid,值为part数量
   *当某个子任务重复出现多次时,数量叠加. 此方法适用需要计算多个子部件数量,上面的计算数量方法
   *是计算一个子part,计算一个子part需要将所给结构都展开一遍,这样当计算多个子part时,就展开多次
   *此方法将所给结构一次展开,将对应的part和数量缓存到hashmap中,这样计算多个子部件数量时,只从
   *map中取就行了,效率会提高很多
   */
  public HashMap getSonPartsQuantityMap(QMPartIfc parentPartIfc) throws
      QMException {
    Collection sonPartColl = getQuantity(parentPartIfc.getBsoID(),
                                         findPartConfigSpecIfc(), 1);
    java.util.HashMap map32 = new HashMap();
    for (Iterator it = sonPartColl.iterator(); it.hasNext(); ) {
      String partID = null;
      String partCount = null;
      String partNumCount = (String) it.next();
      StringTokenizer yyy = new StringTokenizer(partNumCount, "...");
      if (yyy.hasMoreTokens()) {
        partID = yyy.nextToken();
        partCount = yyy.nextToken();
        if (map32.get(partID) == null) {
          map32.put(partID, partCount);
        }
        else {
          String count32 = (String) map32.get(partID);
          int countFinal = Integer.parseInt(count32) +
              Integer.parseInt(partCount);
          map32.put(partID, Integer.toString(countFinal));
        }
      }
    }
    map32.put(parentPartIfc.getBsoID(), "1");
    return map32;

  }


  private ArrayList getQuantity(String partID, PartConfigSpecIfc
          partConfigSpecIfc, int count) throws
QMException {
try {
PersistService pservice = (PersistService) EJBServiceHelper.
getPersistService();
ArrayList list = new ArrayList();
QMPartIfc part = (QMPartIfc) pservice.refreshInfo(partID);
Collection collection = getUsesPartIfcs(part, partConfigSpecIfc);
for (Iterator it = collection.iterator(); it.hasNext(); ) {
Object[] obj1 = (Object[]) it.next();
if (obj1[0] != null || obj1[1] != null) {
if ( (obj1[1] instanceof QMPartIfc) &&
(obj1[0] instanceof PartUsageLinkIfc)) {

PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) obj1[0];
QMPartIfc sonPart = (QMPartIfc) obj1[1];
int count2 = (new Float(usageLinkIfc.getQuantity())).intValue() *
count;
list.add(sonPart.getBsoID() + "..." + count2);
ArrayList list2 = getQuantity(sonPart.getBsoID(), partConfigSpecIfc,
                    count2);
for (int i = 0; i < list2.size(); i++) {
list.add(list2.get(i));
}
}
}
}
return list;
}
catch (QMException ex) {
ex.printStackTrace();
throw ex;
}
}

//CCEndby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"


//CCBegin by liunan 2008-10-4 打补丁200816
    //问题(1)20080811 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
	/**
	 * 根据指定零部件、产品的BsoId和指定的筛选条件获得零部件在该筛选条件下被该产品所使用的结构。
	 * 使用结果保存在返回值vector中。
	 * @param partIfc :QMPartIfc 指定的零部件值对象。
	 * @param productBsoId :String 使用该零部件的产品的BsoId。
	 * @param configSpecIfc :PartConfigSpecIfc 指定的筛选条件。
	 * @return vector:Vector 被其他部件所使用的信息集合。
	 * vector返回值的数据结构为：vector中的每个元素都是Vector类型的，这里为了叙述方便，起名为subVector.
	 * 而subVector的每个元素都是String[5]类型的。
	 * 这个String[5]分别记录了:<br>
	 * String[0]:层次号；<br>
	 * String[1]:零部件编号(零部件名称)版本(视图);<br>
	 * String[2]:零部件在此结构中(顶级件中)使用的数量，所以在同一结构下的记录使用数量是相同的，零部件下同一子件的使用数量合并。<br>
	 * String[3]:零部件的BsoId。<br>
	 * String[4]:零部件值对象。<br>
	 * @throws QMException
	 * @see QMPartInfo
	 * @see PartConfigSpecInfo
	 */
	public Vector getUsedProductStruct(QMPartIfc partIfc, String productBsoId,
			PartConfigSpecIfc configSpecIfc) throws QMException {
		PartDebug.trace(this, PartDebug.PART_SERVICE,
				"getUsedProductStruct begin ....");
		Vector resuletVec = new Vector();
		Vector vector = new Vector();
		PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
		pcon = new PartConfigSpecAssistant(configSpecIfc);
		Vector vector1 = usage(partIfc, configSpecIfc, partLinkIfc, vector,
				pcon);
		//需要对vector1进行处理，如果vector1中只有一个元素，而且是partIfc，就清空vector1:
		if (vector1.size() == 1) {
			Vector subVector = (Vector) vector1.elementAt(0);
			if (subVector.size() == 1) {
				String[] str = (String[]) subVector.elementAt(0);
				if (str[0].equals("0")) {
					vector1.removeElementAt(0);
				}
			}
			//end if(subVector.size() == 1)
		}
		//end if(vector1.size() == 1)
		PersistService pService = (PersistService) EJBServiceHelper
				.getService("PersistService");
		for (int index = 0; index < vector1.size(); index++) {
			Vector tempVec = (Vector) vector1.elementAt(index);
			String[] tempStr = (String[]) tempVec.elementAt(0);
			if (tempStr[0].equals("0") && tempStr[3].equals(productBsoId)) {
				Vector subVec = new Vector();
				for (int i = 0; i < tempVec.size(); i++) {
					tempStr = (String[]) tempVec.elementAt(i);
					QMPartIfc tempIfc = (QMPartIfc) pService
							.refreshInfo((String) tempStr[3]);
					Object[] tempObj = new Object[5];
					System.arraycopy(tempStr, 0, tempObj, 0, tempStr.length);
					tempObj[4] = tempIfc;
					subVec.add(tempObj);
				}
				resuletVec.add(subVec);
			}
		}
		PartDebug.trace(this, PartDebug.PART_SERVICE,
				"getUsedProductStruct end....return is Vector");
		return resuletVec;
	}
	//问题(1)20080811 zhangq end
	//CCEnd by liunan 2008-10-4
	
	//CR1重写服务端逻辑     以下方法是为优化输出物料清单添加的私有方法 
	/**
	 * 对集合中的零部件的IBA属性取值，并通过记录的位置信息进行数组定位。
	 * @param refreshIBAMap HashMap 整理好刷的IBA集合
	 * @param ibaPositionMap HashMap IBA属性位置
	 * @param countMap HashMap  次数
	 * @throws QMException
	 */
	public void refreshWholeIBA(HashMap refreshIBAMap, HashMap ibaPositionMap,
	                            HashMap countMap) throws QMException {
//	  Iterator iter = refreshIBAMap.keySet().iterator();
//	  ArrayList partIDs = new ArrayList();
//	  while (iter.hasNext())
//	    partIDs.add(iter.next());
		AbstractValueIfc valueIfc = null;
	  Object[] tempObj = null;

	  PersistService pService = (PersistService) EJBServiceHelper.
	      getPersistService();

	  ArrayList temp = new ArrayList();
	  int i = 0;
	  String[] tempString = new String[500];
	  System.out.println("iba map sjize :"+refreshIBAMap.size());
	  for (Iterator iter = refreshIBAMap.keySet().iterator();iter.hasNext();) {
//	    temp.add( ( ( (String) iter.next()).split(";"))[0]); //取出零件ID来加载
	   tempString[i] =  (( (String) iter.next()).split(";"))[0];
	  
	   i++;
	    if (i == 499 || !iter.hasNext()) { //以一百为单位处理，防止数据库溢出



	      QMQuery query = new QMQuery("AbstractValue");
	      query.addCondition(new QueryCondition("iBAHolderBsoID", "In",
	                                            tempString));
	      temp.clear();
	      
	      temp.add(pService.findValueInfo(query));
	      Collection col;
	      Iterator iters;
	      int tempInt;
	      String s;
	      for (int j = 0; j < temp.size(); j++) {
	        col = (Collection) temp.get(j);
	        iters = col.iterator();
	        while (iters.hasNext()) {

	          valueIfc = (AbstractValueIfc) iters.next();
	          
	          Object countObject = countMap.get(valueIfc.getIBAHolderBsoID());
	          String countString = countObject.toString();
	          tempInt = Integer.valueOf(countString).intValue();

	          if (countObject != null) {

	            s = (String) ibaPositionMap.get(valueIfc.
	                                            getDefinitionBsoID()); //包含该IBA属性
	            if (s != null) {
	              for (int t = 1; t <= tempInt; t++) {
	                tempObj = (Object[]) refreshIBAMap.get(valueIfc.getIBAHolderBsoID() );
	                if (tempObj != null) {
	                	
	                	if(valueIfc instanceof StringValueIfc)
	                  tempObj[Integer.valueOf(s).intValue()] = ((StringValueIfc)valueIfc).getValue();
	                	else
	                	if(valueIfc instanceof BooleanValueIfc)
	                		  tempObj[Integer.valueOf(s).intValue()] =Boolean.toString(((BooleanValueIfc)valueIfc).getValue());
	                	else
	                	if(valueIfc instanceof FloatValueIfc)
	              		  tempObj[Integer.valueOf(s).intValue()] =Double.toString(((FloatValueIfc)valueIfc).getValue());
	                	else
	                	if(valueIfc instanceof IntegerValueIfc)
	              		  tempObj[Integer.valueOf(s).intValue()] =Long.toString( ((IntegerValueIfc)valueIfc).getValue());
	                	else
	                	if(valueIfc instanceof RatioValueIfc)
	              		  tempObj[Integer.valueOf(s).intValue()] =Double.toString( ((RatioValueIfc)valueIfc).getValue());
	                	else
	                	if(valueIfc instanceof TimestampValueIfc)
	              		  tempObj[Integer.valueOf(s).intValue()] =((TimestampValueIfc)valueIfc).getValue().toString();
	                	else
	                	if(valueIfc instanceof UnitValueIfc)
	              		  tempObj[Integer.valueOf(s).intValue()] =Double.toString( ((UnitValueIfc)valueIfc).getValue());
	                	else
	                	if(valueIfc instanceof URLValueIfc)
	              		  tempObj[Integer.valueOf(s).intValue()] = ((URLValueIfc)valueIfc).getValue();
	                	else
	                	if(valueIfc instanceof ReferenceValueIfc)
	                	{
	                		ReferenceValueIfc ref=(ReferenceValueIfc)valueIfc;
	                		String ibaRefID=ref.getIBAReferenceableID();
	                		
	                		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
	                		IBAReferenceableIfc ibaReferenceIfc=(IBAReferenceableIfc)ps.refreshInfo(ibaRefID);
	                		
	              		  tempObj[Integer.valueOf(s).intValue()] =ibaReferenceIfc.getIBAReferenceableDisplayString();
	              		
	                		
	                	}
	                	
	                	
	                }
	              }

	            }
	          }

	        }
	      }
	      temp.clear();
	      i=0;
	      tempString = new String[500];
	    }

	  }

	}
	/**
	 * 通过iba属性定义的名称获取定义的bsoID
	 * @param defName String
	 * @return String iba属性定义的 bsoID
	 */
	private static HashMap IBAMap = new HashMap(5);
	private AbstractAttributeDefinitionIfc getDefByName(String name) throws
	    QMException {
	  if (IBAMap.get(name) != null)
	    return (AbstractAttributeDefinitionIfc) IBAMap.get(name);
	  PersistService pService = (PersistService) EJBServiceHelper.
	      getPersistService();
	  Collection coll = null;
	  try {
	    QMQuery query = new QMQuery("AbstractAttributeDefinition");
	    QueryCondition condition = new QueryCondition("name", "=", name);
	    query.addCondition(condition);
	    coll = (Collection) pService.findValueInfo(query);
	  }
	  catch (QMException e) {
	    throw e;
	  }
	  AbstractAttributeDefinitionIfc defIfc = null;
	  if (coll != null && coll.size() != 0) {
	    defIfc = (AbstractAttributeDefinitionIfc) coll.iterator().next();
	    if (name != null)
	      IBAMap.put(name, defIfc);
	  }

	  return defIfc;

	}
	
	   /**
     * 获取当前用户的“显示设置”信息
     * @return “显示设置”值对象
     */
    	public PartAttrSetIfc getCurUserInfo()
        throws QMException
        {
        	PartAttrSetIfc setifc=null;
        	SessionService session=(SessionService)EJBServiceHelper.getService("SessionService");
        	String userid=session.getCurUserID();
        	 PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
             QMQuery query = new QMQuery("PartAttrSet");
             QueryCondition condition1 = new QueryCondition("owner", "=", userid);
             query.addCondition(condition1);
             Collection coll= pservice.findValueInfo(query);
             if(coll!=null)
             for(Iterator ite=coll.iterator();ite.hasNext();)
             {
            setifc=(PartAttrSetIfc)ite.next();
             }
        	return setifc;
        }
    	/**
    	 * 获取指定对象的EPM文档
    	 * @param baseIfc
    	 * @return EPM文档值对象集合
    	 * @throws QMException
    	 *@see Vector
    	 */
        public Vector getEMPDocument(BaseValueIfc baseIfc)
        throws QMException
        {
        	 PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        	    Vector retVal = new Vector();
        	    QMQuery query = new QMQuery("EPMBuildHistory");
        	   // if(baseIfc instanceof QMPartIfc)
        	    QueryCondition condition = new QueryCondition("rightBsoID","=",baseIfc.getBsoID());
        	    query.addCondition(condition);
        	    Collection coll = pService.findValueInfo(query);
        	    Iterator iter = coll.iterator();
        	    while(iter.hasNext())
        	    {
        	      EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iter.next();
        	      EPMDocumentIfc doc = (EPMDocumentIfc)link.getBuiltBy();
        	      
        	      retVal.addElement(doc);
        	     
        	    }
        	      return retVal;
        }
        //CCBegin SS2
        /**
    	 * 分级物料清单的显示。 本方法调用了递归方法： fenji(QMPartIfc partIfc, String[] attrNames,
    	 * PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int
    	 * parentQuantity);
    	 * 
    	 * @param partIfc
    	 *            :QMPartIfc 最顶级的部件值对象。
    	 * @param attrNames
    	 *            :String[] 定制的属性，可以为空。
    	 * @param affixAttrNames :
    	 *            String[] 定制的扩展属性名集合，可以为空。
    	 * @param configSpecIfc
    	 *            :PartConfigSpecIfc 配置规范。
    	 * @throws QMException
    	 * @see QMPartInfo
    	 * @see PartConfigSpecInfo
    	 * @return Vector 其中vector中的每个元素都是一个集合：<br>
    	 *         String[0]：当前part的BsoID；<br>
    	 *         String[1]：当前part所在的级别，转化为字符型；<br>
    	 *         2-...：可变的：如果没有定制属性:<br>
    	 *         String[2]：当前part的编号，<br>
    	 *         String[3]：当前part的名称,<br>
    	 *         String[4]：当前part被最顶层部件使用的数量，转化为字符型，<br>
    	 *         String[5]：当前part的版本号，<br>
    	 *         String[6]：当前part的视图；<br>
    	 *         如果定制了属性：按照所有定制的属性加到结果集合中。
    	 */

    		public Vector setMaterialListERP(QMPartIfc qmpartifc, String as[], String as1[], PartConfigSpecIfc partconfigspecifc)
            throws QMException
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "setMaterialList begin ....");
            PersistService persistservice = (PersistService)EJBServiceHelper.getService("PersistService");
            int i = 0;
            PartUsageLinkInfo partusagelinkinfo = new PartUsageLinkInfo();
            float f = 1.0F;
            int j = 0;
            boolean flag = false;
            for(int l = 0; l < as.length; l++)
            {
                String s = as[l]; 
                s = s.trim();
                if(s != null && s.length() > 0)
                {
                    if(s.equals("quantity-0"))
                        j = 3 + l;
                    int k;
                    if(s.equals("partNumber-0"))
                        k = 3 + l;
                }
            }

            Vector vector = null;
            vector = fenjiERP(qmpartifc, as, as1, partconfigspecifc, i, partusagelinkinfo, f);
            PartDebug.trace(this, PartDebug.PART_SERVICE, "setMaterialList end....return is Vector");
            if(vector != null && vector.size() > 0)
            {
                Object aobj[] = (Object[])vector.elementAt(0);
                if(j > 0)
                    aobj[j] = "";
                vector.setElementAt(((Object) (aobj)), 0);
            }
            Vector vector1 = new Vector();
            vector1.addElement("");
            vector1.addElement("");
            String s1 = QMMessage.getLocalizedMessage(RESOURCE, "level", null);
            vector1.addElement(s1);
            boolean flag1 = as == null || as.length == 0;
            boolean flag2 = as1 == null || as1.length == 0;
            if(flag1)
            {
                if(!flag2);
            } else
            {
                for(int i1 = 0; i1 < as.length; i1++)
                {
                    String s4 = as[i1];
                    if(s4.endsWith("-0"))
                    {
                        String s2 = QMMessage.getLocalizedMessage(RESOURCE, s4.substring(0, s4.length() - 2), null);
                        if(s2.equals("\u6570\u91CF"))
                        {
                            vector1.addElement("\u7236\u4EF6\u6570\u91CF");
                            vector1.addElement("\u6BCF\u8F66\u6570\u91CF");
                        } else
                        {
                            vector1.addElement(s2);
                        }
                    } else
                    if(s4.endsWith("-1") || s4.endsWith("-2") || s4.endsWith("-3"))
                    {
                        String s3 = s4.substring(0, s4.length() - 2);
                        vector1.addElement(s3);
                    }
                }

            }
            Object aobj1[] = new Object[vector1.size()];
            for(int j1 = 0; j1 < vector1.size(); j1++)
                aobj1[j1] = vector1.elementAt(j1);

            vector.insertElementAt(((Object) (aobj1)), 0);
            for(int k1 = 0; k1 < vector.size(); k1++)
            {
                Object aobj2[] = (Object[])vector.elementAt(k1);
                for(int l1 = 0; l1 < aobj2.length; l1++)
                    if(aobj2[l1] == null)
                        aobj2[l1] = "";

            }

            return vector;
        }

        private Vector fenjiERP(QMPartIfc qmpartifc, String as[], String as1[], PartConfigSpecIfc partconfigspecifc, int i, PartUsageLinkIfc partusagelinkifc, float f)
            throws QMException
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji begin ....");
            Vector vector = new Vector();
            Object aobj[] = null;
            float f1 = 1.0F;
            BSXUtil bsxutil = new BSXUtil();
            boolean flag = as == null || as.length == 0;
            boolean flag1 = as1 == null || as1.length == 0;
            if(!flag)
            {
                boolean flag2 = false;
                for(int j = 0; j < as.length; j++)
                {
                    String s1 = as[j];
                    if(s1 == null || s1.indexOf("quantity") == -1)
                        continue;
                    flag2 = true;
                    break;
                }

                if(flag2)
                    aobj = new Object[4 + as.length];
                else
                    aobj = new Object[3 + as.length];
            }
            String s = qmpartifc.getBsoID();
            aobj[0] = s;
            int k = 0;
            for(int l = 0; l < as.length; l++)
            {
                String s2 = as[l];
                s2 = s2.trim();
                if(s2 != null && s2.length() > 0 && s2.equals("partNumber-0"))
                    k = 3 + l;
            }

            aobj[1] = (new Integer(k)).toString();
            aobj[2] = (new Integer(i)).toString();
            if(flag)
            {
                if(!flag1);
            } else
            {
                int i1 = 0;
                for(int j1 = 0; i1 < as.length; j1++)
                {
                    String s3 = as[i1];
                    if(s3.endsWith("-0"))
                    {
                        String s4 = s3.substring(0, s3.length() - 2);
                        s4 = s4.trim();
                        if(s4 != null && s4.length() > 0)
                        {
                            String s6 = aobj[1].toString();
                            if(s4.equals("alternates"))
                                aobj[3 + j1] = getAlternates(qmpartifc);
                            else
                            if(s4.equals("substitutes"))
                                aobj[3 + j1] = getSubstitutes(partusagelinkifc);
                            else
                            if(s4.equals("defaultUnit") && !s6.equals("0"))
                            {
                                Unit unit = partusagelinkifc.getDefaultUnit();
                                if(unit != null)
                                    aobj[3 + j1] = unit.getDisplay();
                                else
                                    aobj[3 + j1] = "";
                            } else
                            if(s4.equals("quantity"))
                            {
                                if(i == 0)
                                {
                                    f = 1.0F;
                                    String s7 = "1";
                                    aobj[3 + j1] = new String(s7);
                                    j1++;
                                    aobj[3 + j1] = "";
                                } else
                                {
                                    f *= partusagelinkifc.getQuantity();
                                    float f2 = partusagelinkifc.getQuantity();
                                    String s8 = String.valueOf(f2);
                                    if(s8.endsWith(".0"))
                                        s8 = s8.substring(0, s8.length() - 2);
                                    aobj[3 + j1] = s8;
                                    j1++;
                                    String s9 = String.valueOf(f);
                                    if(s9.endsWith(".0"))
                                        s9 = s9.substring(0, s9.length() - 2);
                                    aobj[3 + j1] = s9;
                                }
                            } else
                            if(s4.trim().length() > 0)
                            {
                                s4 = s4.substring(0, 1).toUpperCase() + s4.substring(1, s4.length());
                                s4 = "get" + s4;
                                try
                                {
                                    Class class1 = Class.forName("com.faw_qm.part.model.QMPartInfo");
                                    Method method = class1.getMethod(s4, null);
                                    Object obj1 = method.invoke(qmpartifc, null);
                                    if(obj1 == null)
                                        aobj[3 + j1] = "";
                                    else
                                    if(obj1 instanceof String)
                                    {
                                        String s10 = (String)obj1;
                                        if(s10 != null && s10.length() > 0)
                                            aobj[3 + j1] = s10;
                                        else
                                            aobj[3 + j1] = "";
                                    } else
                                    if(obj1 instanceof EnumeratedType)
                                    {
                                        EnumeratedType enumeratedtype = (EnumeratedType)obj1;
                                        if(enumeratedtype != null)
                                            aobj[3 + j1] = enumeratedtype.getDisplay();
                                        else
                                            aobj[3 + j1] = "";
                                    }
                                }
                                catch(Exception exception)
                                {
                                    exception.printStackTrace();
                                    throw new QMException(exception);
                                }
                            }
                        }
                    } else
                    if(s3.endsWith("-1"))
                    {
                        as1 = new String[1];
                        as1[0] = s3.substring(0, s3.length() - 2);
                        aobj = bsxutil.getIBAValue(aobj, as1, qmpartifc, j1 - 5);
                        if(aobj[3 + j1] == null)
                            aobj[3 + j1] = "";
                    } else
                    if(s3.endsWith("-2"))
                    {
                        String s5 = bsxutil.getTdan(s);
                        aobj[3 + j1] = s5;
                    }
                    i1++;
                }

            }
            vector.addElement(((Object) (aobj)));
            Object obj = getUsesPartIfcs(qmpartifc, partconfigspecifc);
            Object aobj1[] = (Object[])((Collection) (obj)).toArray();
            Vector vector1 = new Vector();
            for(int k1 = 0; k1 < aobj1.length; k1++)
            {
                if(aobj1[k1] instanceof Object[])
                {
                    Object aobj2[] = (Object[])aobj1[k1];
                    if((aobj2[1] instanceof QMPartIfc) && (aobj2[0] instanceof PartUsageLinkIfc))
                    {
                        for(int l1 = k1; l1 < aobj1.length; l1++)
                        {
                            Object aobj4[] = (Object[])aobj1[l1];
                            Object aobj6[] = (Object[])aobj1[k1];
                            if((aobj4[1] instanceof QMPartIfc) && (aobj4[0] instanceof PartUsageLinkIfc))
                            {
                                String s11 = ((QMPartIfc)aobj6[1]).getPartNumber();
                                String s12 = ((QMPartIfc)aobj4[1]).getPartNumber();
                                if(s11.compareTo(s12) > 0)
                                {
                                    aobj1[k1] = ((Object) (aobj4));
                                    aobj1[l1] = ((Object) (aobj6));
                                }
                            }
                        }

                        vector1.add(aobj1[k1]);
                    }
                }
                obj = vector1;
            }

            if(obj == null || ((Collection) (obj)).size() == 0)
            {
                PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return is Vector");
                return vector;
            }
            Object aobj3[] = (Object[])((Collection) (obj)).toArray();
            i++;
            for(int i2 = 0; i2 < aobj3.length; i2++)
                if(aobj3[i2] instanceof Object[])
                {
                    Object aobj5[] = (Object[])aobj3[i2];
                    Vector vector2 = new Vector();
                    if((aobj5[1] instanceof QMPartIfc) && (aobj5[0] instanceof PartUsageLinkIfc))
                        vector2 = fenjiERP((QMPartIfc)aobj5[1], as, as1, partconfigspecifc, i, (PartUsageLinkIfc)aobj5[0], f);
                    for(int j2 = 0; j2 < vector2.size(); j2++)
                        vector.addElement(vector2.elementAt(j2));

                }

            i--;
            PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return Vector ");
            return vector;
        }

    	/**
    	 * 获取part的替换件的编号（名称）以字串形式返回
    	 * 
    	 * @param part
    	 *            零部件的
    	 * @return 换件的编号（名称）
    	 */
    	private String getAlternates(QMPartIfc part) throws QMException {
    		String alternates = "";

    		ExtendedPartService pservice = (ExtendedPartService) EJBServiceHelper
    				.getService("ExtendedPartService");
    		Collection altes = pservice
    				.getAlternatesPartMasters((QMPartMasterIfc) part.getMaster());
    		Iterator ite = altes.iterator();
    		for (; ite.hasNext();) {
    			QMPartMasterIfc master = (QMPartMasterIfc) ite.next();
    			if (alternates.length() == 0) {
    				alternates = master.getPartNumber() + "("
    						+ master.getPartName() + ")";
    			} else
    				alternates = alternates + ";" + master.getPartNumber() + "("
    						+ master.getPartName() + ")";
    		}
    		return alternates;
    	}

    	/**
    	 * 获取part的结构替换件的编号（名称）以字串形式返回
    	 * 
    	 * @param part
    	 *            零部件的
    	 * @return 结构换件的编号（名称）
    	 */
    	private String getSubstitutes(PartUsageLinkIfc usageLinkIfc)
    			throws QMException {
    		String substitutes = "";
    		if (usageLinkIfc == null)
    			return substitutes;
    		if (!PersistHelper.isPersistent(usageLinkIfc))
    			return substitutes;
    		System.out.println("aaaaaaaaaaa the usagelink is "
    				+ usageLinkIfc.getBsoID());
    		ExtendedPartService pservice = (ExtendedPartService) EJBServiceHelper
    				.getService("ExtendedPartService");
    		Collection subst = pservice.getSubstitutesPartMasters(usageLinkIfc);
    		Iterator ite = subst.iterator();
    		for (; ite.hasNext();) {
    			QMPartMasterIfc master = (QMPartMasterIfc) ite.next();
    			if (substitutes.length() == 0) {
    				substitutes = master.getPartNumber() + "("
    						+ master.getPartName() + ")";
    			} else
    				substitutes = substitutes + ";" + master.getPartNumber() + "("
    						+ master.getPartName() + ")";
    		}
    		return substitutes;
    	}
//    	CCEnd SS2


	//CCBegin SS3
	/**
	 * 通过masterid获取最新版本的零部件。
	 * @param masterID masterid
	 * @return QMPartInfo 最新版本的零部件(QMPart);
	 * @throws QMException
	 * @see QMPartInfo
	 */
	public QMPartInfo getPartByMasterID(String masterID) throws QMException
	{
        PartServiceHelper pshelper= new PartServiceHelper();
		QMPartInfo partInfo = pshelper.getPartByMasterID(masterID);
		return partInfo;
	}
	//CCEnd SS3
	
//	CCBegin SS5
	public boolean isSubNode(QMPartIfc part,QMPartIfc subpart)
	{
		boolean flag = false;
		try
		{
			// 获得当前用户配置规范
			PartConfigSpecIfc configSpec = findPartConfigSpecIfc();
			// 获得master在当前配置规范下的零件
			// QMPartIfc partIfc = partService.getPartByConfigSpec(master, configSpec);
			if(part == null)
			{
				return flag;
			}
			// 获得part所使用的下一级零件的主信息的集合
			PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
			StandardPartService partService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");

			if(part.getBsoName().equals("QMPart"))
			{
				Collection c2 = pservice.navigateValueInfo(part, "usedBy", "PartUsageLink");
				if(c2!=null&&c2.size()>0){
					Vector reVec = new Vector() ;
					for(Iterator ite = c2.iterator();ite.hasNext();){
						QMPartMasterIfc subMaster = (QMPartMasterIfc)ite.next();
						QMPartIfc temPartIfc = partService.getPartByConfigSpec(subMaster, configSpec);
						if(subMaster.getBsoID().equals(subpart.getMasterBsoID())){
							flag= true;
							break;
						}
						else
						{
							reVec.add(temPartIfc);
						}
					}
					if(!flag && reVec!=null&&reVec.size()>0){
                		for(Iterator ites = reVec.iterator();ites.hasNext();){
                			QMPartIfc temPartIfc1 = (QMPartIfc)ites.next();
                			boolean aa=isSubNode(temPartIfc1,subpart);
                			if(aa)
                			{
                				flag=aa;
                				break;
                			}
                		}
                	}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public QMPartIfc findSubPartMaster(String partNumber) throws QMException
	{
		QMPartIfc subpart = null;
		PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
		QMQuery query = new QMQuery("QMPartMaster");
		QueryCondition condition1 = new QueryCondition("partNumber", "=", partNumber);
		query.addCondition(condition1);
		Collection col = pservice.findValueInfo(query);
		Iterator iter = col.iterator();
		// 获取此零部件的第一个版本
		while(iter.hasNext())
		{
			QMPartMasterIfc pmaster = (QMPartMasterIfc)iter.next();
			if(pmaster instanceof QMPartMasterIfc)
			{
				subpart = (QMPartIfc)getPartByMasterID(pmaster.getBsoID());
			}
		}
		return subpart;
	}
//	CCEnd SS5
	
	//CCBegin SS8
	
	/**
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
	   * 本方法调用了bianli()方法实现递归。
	   * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
	   * 1、如果不定制属性：
	   * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
	   * 2、如果定制属性：
	   * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

	   * @param partIfc :QMPartIfc 指定的零部件的值对象。
	   * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
	   * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
	   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
	   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
	   * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
	   * @throws QMException
	   * @return Vector
	   */
	  public ArrayList setBOMList(QMPartIfc partIfc, String[] attrNames,
	                              String[] affixAttrNames, String[] routeNames,
	                              String source, String type,
	                              PartConfigSpecIfc configSpecIfc,
	                              String routeDepartment) throws QMException {

	    PartDebug.trace(this, PartDebug.PART_SERVICE, "setBOMList begin ....");
	    PersistService pService = (PersistService) EJBServiceHelper.getService(
	        "PersistService");
	    com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService trService = (com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService) EJBServiceHelper.
	        getService("consTechnicsRouteService");
	    ArrayList vector = new ArrayList();
	    float parentQuantity = 1.0f;
	    //added by dikef for export iba attribute
	    WfUtil wfutil = new WfUtil();
	    PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
	    //vector =
	    bianli(partIfc, partIfc, attrNames, affixAttrNames, routeNames,
	           source, type,
	           configSpecIfc, parentQuantity, partLinkIfc, trService, pService,
	           vector);
	    //下面对vector中的元素进行合并数量的处理。...........
	    Vector resultVector = new Vector();
	    int vectorSize = vector.size();
	    for (int i = 0; i < vectorSize; i++) {
	      Object[] temp1 = (Object[]) vector.get(i);
	      //2003.09.12为了防止"null"进入到返回值中：可以对temp1中的每个元素判断
	      //其是否为null, 如果是null，就转化为""
	      for (int j = 0; j < temp1.length; j++) {
	        if (temp1[j] == null) {
	          temp1[j] = "";
	        }
	      }
	      //需求是按照partNumber进行合并的！！！
	      String partNumber1 = (String) temp1[3];
	      boolean flag = false;
	      for (int j = 0; j < resultVector.size(); j++) {
	        Object[] temp2 = (Object[]) resultVector.elementAt(j);
	        String partNumber2 = (String) temp2[3];
	        if (partNumber1.equals(partNumber2)) {
	          flag = true;
	          //把temp2和temp1中的元素进行合并，放到resultVector中去。:::
	          float float1 = (new Float(temp1[6].toString())).floatValue();
	          float float2 = (new Float(temp2[6].toString())).floatValue();
	          String tempQuantity = String.valueOf(float1 + float2);
	          if (tempQuantity.endsWith(".0")) {
	            tempQuantity = tempQuantity.substring(0,
	                                                  tempQuantity.length() - 2);
	          }
	          temp1[6] = tempQuantity;
	          resultVector.setElementAt(temp1, j);
	          break;
	        }
	        //end if (partNumber1.equals(partNumber2))
	      }
	      //end for (int j=0; j<resultVector.size(); j++)
	      if (flag == false) {
	        resultVector.addElement(temp1);
	      }
	      //end if(flag == false)
	    }
	    //需要对第一个元素进行判断，如果其，source，type都和输入的source, type相同的
	    //就保留，否则，删除掉。
	    //其实是由partIfc决定的:::
	    boolean flag1 = false;
	    boolean flag2 = false;
	    String source1 = (partIfc.getProducedBy()).toString();
	    String type1 = (partIfc.getPartType()).toString();
	    if (source != null && source.length() > 0) {
	      if (source.equals(source1)) {
	        flag1 = true;
	      }
	    }
	    else {
	      flag1 = true;
	    }
	    if (type != null && type.length() > 0) {
	      if (type.equals(type1)) {
	        flag2 = true;
	      }
	    }
	    else {
	      flag2 = true;
	    }
	    if (!flag1 || !flag2) {
	      resultVector.removeElementAt(0);
	    }
	    else {
	      //把第一个元素的数量改成""
	      Object[] firstObj = (Object[]) resultVector.elementAt(0);
	      firstObj[6] = "";
	      resultVector.setElementAt(firstObj, 0);
	    }
	    //这里才保存最后最后的结果：
	    ArrayList result = new ArrayList();
	    //然后，这里还需要对最后的返回值集合按照当前的source和type进行过滤：
	    int resultVectorSize = resultVector.size();
	    for (int i = 0; i < resultVectorSize; i++) {
	      Object[] element = (Object[]) resultVector.elementAt(i);
	      QMPartIfc onePart = (QMPartIfc) pService.refreshInfo( (String) element[0]);
	      boolean flag11 = false;
	      boolean flag22 = false;

	      if (source != null && source.length() > 0) {
	        if (onePart.getProducedBy().toString().equals(source)) {
	          flag11 = true;
	        }
	      }
	      else {
	        flag11 = true;
	      }
	      if (type != null && type.length() > 0) {
	        if (onePart.getPartType().toString().equals(type)) {
	          flag22 = true;
	        }
	      }
	      else {
	        flag22 = true;
	      }
	      //added by dikef  for filtrate by routedepartment

	      boolean flag33 = false;
	      if (routeDepartment != null && routeDepartment.length() > 0) {
	        if (trService.isIncludeDepartment(onePart, routeDepartment)) {
	          flag33 = true;
	        }
	      }
	      else {
	        flag33 = true;
	      }
	      //added by dikef for filtrate by routedepartment
	      if (flag11 && flag22 && flag33) {
	        result.add(element);
	      }
	    }
	    //还需要向该vector中添加一个元素：
	    Vector firstElement = new Vector();
	    firstElement.addElement("");
	    firstElement.addElement("");
	    firstElement.addElement("");
	    String ssss = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
	    firstElement.addElement(ssss);
	    ssss = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
	    firstElement.addElement(ssss);
	    ssss = QMMessage.getLocalizedMessage(RESOURCE, "isHasSubParts", null);
	    firstElement.addElement(ssss);
	    ssss = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);
	    firstElement.addElement(ssss);
	    //下面需要通过判断来确定firstElement的值:
	    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
	    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
	        affixAttrNames.length == 0;
	    //如果定制的普通属性为空：
	    if (attrNullTrueFlag) {
	      //如果定制的扩展属性也为空：
	      if (affixAttrNullTrueFlag) {
	        ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
	        firstElement.addElement(ssss);
	        ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
	        firstElement.addElement(ssss);
	      }
	    }
	    //如果定制的普通属性不为空的话：
	    else {
	      for (int i = 0; i < attrNames.length; i++) {
	        String attr = attrNames[i];
	        ssss = QMMessage.getLocalizedMessage(RESOURCE, attr, null);
	        firstElement.addElement(ssss);
	      }
	    }
	    //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
	    //added by dikef for export iba attribute
	    if (!affixAttrNullTrueFlag) {
	      //added by  dikef  for  export iba attribute
	      for (int i = 0; i < affixAttrNames.length; i++) {
	        String affixName = affixAttrNames[i];
	        firstElement.addElement(wfutil.getDisplayName(affixName));
	      }
	    }
	    //如果有工艺路线，则添加。这里添加的是名称。
	    if (routeNames != null && routeNames.length > 0) {
	      for (int i = 0; i < routeNames.length; i++) {
	        firstElement.addElement(routeNames[i]);
	      }
	    }

	    //再添加到vector中的第一个位置：
	    Object[] tempArray = new Object[firstElement.size()];
	    for (int i = 0; i < firstElement.size(); i++) {
	      tempArray[i] = firstElement.elementAt(i);
	    }
	    result.add(0, tempArray);
	    return result;
	  }
	
	  
	  /**
	   * 本方法被setBOMList所调用，实现递归调用的功能。
	   * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
	   * 1、如果不定制属性：
	   * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
	   * 2、如果定制属性：
	   * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

	   * @param partIfc :QMPartIfc 指定的零部件的值对象。
	   * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
	   * @param affixAttrNames : String[] 定制的要输出的扩展属性的集合，可以为空。
	   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
	   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
	   * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
	   * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量。
	   * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象。
	   * @throws QMException
	   * @return Vector
	   */
	  private void bianli(QMPartIfc partProductIfc, QMPartIfc partIfc,
	                      String[] attrNames, String[] affixAttrNames,
	                      String[] routeNames, String source, String type,
	                      PartConfigSpecIfc configSpecIfc,
	                      float parentQuantity, PartUsageLinkIfc partLinkIfc,
	                      com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService tr, PersistService ps,
	                      ArrayList al) throws
	      QMException {
	    //本方法的主要实现过程为:::
	    //1：判断当前的零部件是否是可分的零部件，以方便在把该零部件放到最后结果集合中的时候，可以确定
	    //该零部件的可分标志
	    PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli begin ....");
	    //ArrayList resultVector = new ArrayList();
	    //added by dikef  for export iba attribute
	    //WfUtil wfutil = new WfUtil();
	    //用来保存当前的零部件的所有合格的子零部件的集合：
	    ArrayList hegeVector = new ArrayList();
	    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
	    //2006-7-11 by lilei xiugai begin
	    String isHasSubParts1 = "否";
	    if (collection != null && collection.size() > 0) {
	      Iterator iterator = collection.iterator();
	      while (iterator.hasNext()) {
	        Object obj = iterator.next();
	        Object[] objs = (Object[]) obj;
	        if (objs[1] instanceof QMPartInfo) {
	          hegeVector.add(obj);
	          isHasSubParts1 = "是";
	        }
	      }
	    } //2006-7-11 by lilei xiugai end
	    //这个时候就应该先判断collection是否是"null"
	    /*if (collection != null && collection.size() > 0) {
	      //需要对collection中的所有元素进行循环，如果有这样的元素
	      //是QMPartIfc而且其来源和类型和输入的参数是一致的，
	      //表明该输入的零部件是可分的.既是根据source, type来对子节点进行过滤:::
	      Object[] resultArray = new Object[collection.size()];
	      collection.toArray(resultArray);
	      for (int i = 0; i < resultArray.length; i++) {
	        //boolean isHasSubParts = true; //false
	        Object obj = resultArray[i];
	        if (obj instanceof Object[]) {
	          Object[] obj1 = (Object[]) obj;
	          if (obj1[1] instanceof QMPartIfc) {
	            //if (isHasSubParts == true) {
	              hegeVector.add(obj);
	            //}
	            //end if(isHasSubParts == true)
	          }
	          //end if (obj1[1] instanceof QMPartIfc)
	        }
	        //end if(obj instanceof Object[])
	      }
	      //end for (int i=0; i<resultArray.length; i++)
	         }*/
	    //end if(collection != null && collection.size() > 0)
	    //把本part->resultVector中;
	    Object[] tempArray = null;
	    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
	    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
	        affixAttrNames.length == 0;
	    boolean routeNullTrueFlag = routeNames == null || routeNames.length == 0;
	    try {
	      if (attrNullTrueFlag) {
	        //如果两个定制的属性集合都为空的时候：
	        if (affixAttrNullTrueFlag) {
	          if (routeNullTrueFlag) {
	            tempArray = new Object[9];
	            tempArray[2] = new Integer(8);
	          }
	          else {
	            tempArray = new Object[9 + routeNames.length];
	            tempArray[2] = new Integer(8);
	          }
	        }
	        //如果定制的普通属性为空，而定制的扩展属性不为空的时候：
	        else {
	          if (routeNullTrueFlag) {
	            tempArray = new Object[7 + affixAttrNames.length];
	            tempArray[2] = new Integer(7 + affixAttrNames.length - 1);
	          }
	          else {
	            tempArray = new Object[7 + affixAttrNames.length +
	                routeNames.length];
	            tempArray[2] = new Integer(7 + affixAttrNames.length - 1);
	          }
	        }
	      }
	      else {
	        //如果定制的普通属性集合不为空，定制的扩展属性集合为空的时候：
	        if (affixAttrNullTrueFlag) {
	          if (routeNames == null || routeNames.length == 0) {
	            tempArray = new Object[7 + attrNames.length];
	          }
	          else {
	            tempArray = new Object[7 + attrNames.length + routeNames.length];
	          }
	          tempArray[2] = new Integer(7 + attrNames.length - 1);
	        }
	        //如果两个定制的属性集合都不为空的时候：
	        else {
	          tempArray = new Object[7 + affixAttrNames.length + attrNames.length +
	              routeNames.length];
	          tempArray[2] = new Integer(7 + affixAttrNames.length +
	                                     attrNames.length -
	                                     1);
	        }
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new QMException(e);
	    }
	    //构造tempArray数组，根据属性名和类型
	    tempArray[0] = partIfc.getBsoID();
	    tempArray[1] = new Integer(1);
	    tempArray[3] = partIfc.getPartNumber();
	    tempArray[4] = partIfc.getPartName();
	    tempArray[5] = isHasSubParts1;
	    //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
	    //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
	    if (partLinkIfc == null || !PersistHelper.isPersistent(partLinkIfc)) {
	      parentQuantity = 1.0f;
	    }
	    else {
	      parentQuantity = parentQuantity * partLinkIfc.getQuantity();
	    }
	    String tempQuantity = String.valueOf(parentQuantity);
	    if (tempQuantity.endsWith(".0")) {
	      tempQuantity = tempQuantity.substring(0,
	                                            tempQuantity.length() - 2);
	    }
	    tempArray[6] = tempQuantity;
	    //下面需要根据两个定制的属性集合来对最后的结果集合进行组织：
	    if (attrNullTrueFlag) {
	      //当两个定制的属性集合都为空的时候：
	      if (affixAttrNullTrueFlag) {
	        tempArray[7] = partIfc.getVersionValue();
	        if (partIfc.getViewName() == null ||
	            partIfc.getViewName().length() == 0) {
	          tempArray[8] = "";
	        }
	        else {
	          tempArray[8] = partIfc.getViewName();
	        }
	      }
	      else {
	        //added by dikef for export iba attribute
	        //tempArray = wfutil.getIBAValue(tempArray, affixAttrNames, partIfc, 0);
	        if (affixAttrNames != null && affixAttrNames.length > 0) {
	          //String ibavalue[]=new String[affixAttrNames.length];
	          tempArray = addIBAValues(tempArray, partIfc, affixAttrNames, ps, 7);
	          //ibavalue=getIBAValues(partIfc,affixAttrNames,ps);
	          //for (int i = 0; i < affixAttrNames.length; i++) {
	          //tempArray[7 + i] = getIBAValue(partIfc, affixAttrNames[i], ps);
	          //} //lilei xiugai 2006-7-11
	        }
	      }
	      //结束：当定制的普通属性为空，而定制的扩展属性集合不为空的时候：
	    }
	    //上面结束：当定制的普通属性集合为空的时候。
	    //下面开始：当定制的普通属性集合不为空的时候：
	    else {
	      //先把所有的普通属性的值放到tempArray中：
	      for (int i = 0; i < attrNames.length; i++) {
	        String attr = attrNames[i];
	        attr = attr.trim();
	        if (attr != null && attr.length() > 0) {
	          //modify by liun 2005.3.25 改为从关联中得到单位
	          if (attr.equals("defaultUnit")) {
	            Unit unit = partLinkIfc.getDefaultUnit();
	            tempArray[7 + i] = unit != null ? unit.getDisplay() : "";
	          }
	          else {
	            attr = (attr.substring(0, 1)).toUpperCase() +
	                attr.substring(1, attr.length());
	            attr = "get" + attr;
	            //现在的attr就是"getProducedBy"等固定的字符串了：
	            try {
	              Class partClass = Class.forName(
	                  "com.faw_qm.part.model.QMPartInfo");
	              Method method1 = partClass.getMethod(attr, null);
	              Object obj = method1.invoke(partIfc, null);
	              //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
	              //如果obj不为null, 而且是String, tempArray[i + 5] = (String)obj;
	              //如果obj是枚举类型，tempArray[i + 5] = (EnumerationType)obj.getDisplay();
	              if (obj == null) {
	                tempArray[i + 7] = "";
	              }
	              else {
	                if (obj instanceof String) {
	                  String tempString = (String) obj;
	                  if (tempString != null &&
	                      tempString.length() > 0) {
	                    tempArray[i + 7] = tempString;
	                  }
	                  else {
	                    tempArray[i + 7] = "";
	                  }
	                }
	                else {
	                  if (obj instanceof EnumeratedType) {
	                    EnumeratedType tempType = (
	                        EnumeratedType)
	                        obj;
	                    if (tempType != null) {
	                      tempArray[i +
	                          7] = tempType.getDisplay();
	                    }
	                    else {
	                      tempArray[i + 7] = "";
	                    }
	                  }
	                }
	              }
	            }
	            catch (Exception ex) {
	              ex.printStackTrace();
	              throw new QMException(ex);
	            }
	          }
	        }
	      }
	      if (!affixAttrNullTrueFlag) {
	        //tempArray = wfutil.getIBAValue(tempArray, affixAttrNames, partIfc,
	        //attrNames.length);
	        if (affixAttrNames != null && affixAttrNames.length > 0) {
	          int attrNamesLength = attrNames.length;
	          tempArray = addIBAValues(tempArray, partIfc, affixAttrNames, ps,
	                                   7 + attrNamesLength);
	          //for (int i = 0; i < affixAttrNames.length; i++) {
	          //tempArray[7 + i +
	          //  attrNamesLength] = getIBAValue(partIfc, affixAttrNames[i], ps);
	          //}
	        } //lilei xiugai 2006-7-11
	      }
	      //end for (int i=0; i<attrNames.length; i++)
	    }
	    //added by dikef  for export iba attribute

	    //end if and else if (attrNames == null || attrNames.length == 0)
	    int nowLen;
	    if (attrNullTrueFlag && affixAttrNullTrueFlag) {
	      nowLen = 9;
	    }
	    else {
	      if (attrNullTrueFlag) {
	        nowLen = affixAttrNames.length + 7;
	      }
	      else if (affixAttrNullTrueFlag) {
	        nowLen = attrNames.length + 7;
	      }
	      else {
	        nowLen = attrNames.length + affixAttrNames.length + 7;
	      }
	    }
	    //获得零部件的工艺路线。
	    //如果路线选择不为空
	    if (!routeNullTrueFlag) {
	      //获得零件的路线
	      //modified by dikef for get latest route
	      int routesSize = routeNames.length;
	      String[] routes = new String[routesSize];
	      String[] routeStrs = tr.getMaterialRoute(partIfc, routeNames, routes);
	      //modified by dikef for get latest route
	      if (routeStrs != null && routeStrs.length > 0) {
	        tempArray[1] = new Integer(1);
	        //HashMap routeMap = new HashMap();
	        //int routeVecSize = routeStrs.length;
	        Object[] tempRoute = new Object[1];
	        //for (int i = 0; i < routeVecSize; i++) {
	        //获得每一个路线
	        //routeMap = (HashMap) routeVec.elementAt(i);
	        Object[] tempArray1 = new Object[routesSize];
	        for (int ii = 0; ii < routesSize; ii++) {
	          //Object aa = routeMap.get(routeNames[ii]);
	          Object aa = routeStrs[ii];
	          tempArray1[ii] = aa != null ? aa.toString() : "";
	        }
	        tempRoute[0] = tempArray1;
	        //}
	        tempArray[nowLen] = tempRoute;
	      }
	      else {
	        Object[] temp1 = new Object[1];
	        Object[] temp2 = new Object[routeNames.length];
	        for (int i = 0; i < routeNames.length; i++) {
	          temp2[i] = "";
	        }
	        temp1[0] = temp2;
	        tempArray[nowLen] = temp1;
	      }
	    }
	    //resultVector.add(tempArray);
	    al.add(tempArray);
	    //对已经过滤处理的当前输入的零部件的所有子零部件进行递归处理::::
	    if (hegeVector != null && hegeVector.size() > 0) {
	      for (int j = 0; j < hegeVector.size(); j++) {
	        Object obj = hegeVector.get(j);
	        if (obj instanceof Object[]) {
	          Object[] obj2 = (Object[]) obj;
	          if ( (obj2[0] != null) && (obj2[1] != null)) {
	            //ArrayList tempVector =
	            bianli(partProductIfc, (QMPartIfc) obj2[1],
	                   attrNames,
	                   affixAttrNames, routeNames, source, type,
	                   configSpecIfc,
	                   parentQuantity,
	                   (PartUsageLinkIfc) obj2[0], tr, ps, al);
	            /*for (int k = 0; k < tempVector.size(); k++) {
	              resultVector.add(tempVector.get(k));
	                         }*/
	          }
	        }
	      }
	    }
	    PartDebug.trace(this, PartDebug.PART_SERVICE,
	                    "bianli end....return is Vector ");
	    //return resultVector;
	  }
	  private HashMap ibaDefinitionMap = new HashMap();
	  
	  private Object[] addIBAValues(Object[] resultArray, IBAHolderIfc holder,
              String[] attrName,PersistService ps, int s) {
		try {
			QMQuery query = new QMQuery("StringValue");
			QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=",
					holder.getBsoID());
			query.addCondition(qc);
			query.addAND();
			query.addLeftParentheses();
			String definitionid = "";
			for (int i = 0; i < attrName.length; i++) {
				definitionid = (String) ibaDefinitionMap.get(attrName[i]);
				if (definitionid == null || definitionid.length() == 0) {
					QMQuery definitionquery = new QMQuery("StringDefinition");
					QueryCondition definitionquerycon = new QueryCondition(
							"name", " = ", attrName[i]);
					definitionquery.addCondition(definitionquerycon);
					Collection decol = ps.findValueInfo(definitionquery, false);
					if (decol != null && decol.size() > 0) {
						Iterator defite = decol.iterator();
						StringDefinitionIfc defifc = (StringDefinitionIfc) defite
								.next();
						definitionid = defifc.getBsoID();
						ibaDefinitionMap.put(attrName[i], definitionid);
					}
				}
				if (definitionid != null && definitionid.length() > 0) {
					if (i != 0) {
						query.addOR();
					}
					QueryCondition qc1 = new QueryCondition("definitionBsoID",
							"=", definitionid);
					query.addCondition(qc1);
				}
			}
			query.addRightParentheses();
			System.out.println("query==============" + query.getDebugSQL());
			Collection col = ps.findValueInfo(query, false);
			if (col == null || col.size() == 0) {
				return resultArray;
			}
			Iterator ite = col.iterator();
			HashMap temp = new HashMap();
			while (ite.hasNext()) {
				StringValueInfo svi = (StringValueInfo) ite.next();
				temp.put(svi.getDefinitionBsoID(), svi.getValue());
			}
			for (int i = 0; i < attrName.length; i++) {
				String defid = (String) ibaDefinitionMap.get(attrName[i]);
				if (temp.get(defid) == null) {
					resultArray[s + i] = "";
				} else {
					resultArray[s + i] = temp.get(defid);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultArray;
}
	  
	//CCEnd SS8
	
	
}
