/**
 * 生成程序IntePackServiceEJB.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.acl.ejb.service.AccessControlService;
//import com.faw_qm.adoptnotice.exception.AdoptNoticeException;
//import com.faw_qm.adoptnotice.model.AdoptNoticeInfo;
import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkIfc;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.erp.exception.IntePackException;
import com.faw_qm.erp.model.IntePackIfc;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.erp.util.BaseDataPublisher;
import com.faw_qm.erp.util.IntePackSourceType;
import com.faw_qm.erp.util.RequestHelper;
import com.faw_qm.erp.util.RouteCodeIBAName;
import com.faw_qm.erp.util.TechnicsDataPublisher;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.query.DateHelper;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackServiceEJB extends BaseServiceImp
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(IntePackServiceEJB.class);

    /**
     * 资源名
     */
    private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";

    /**获取服务名。
     * @return String
     */
    public String getServiceName()
    {
        return "IntePackService";
    }

    /**
     * 根据集成包搜索零部件
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByIntePackID(String bsoid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByIntePackID(String) - start"); //$NON-NLS-1$
        }
        //创建查找对象query
        QMQuery query;
        QueryCondition cond;
        //获得持久化服务
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //1.首先根据集成包的ID查找集成包值对象
        //查询到的集成包值对象
        IntePackInfo intePackInfo = null;
        //集成包的查询结果
        Collection collection1 = null;
        //更改采用通知书的查询结果
        Collection collection2 = null;
        //Part的查询结果
        Collection collection3 = null;
        try
        {
            //创建新的查找对象query
            query = new QMQuery("IntePack");
            cond = new QueryCondition("bsoID", QueryCondition.EQUAL, bsoid);
            query.addCondition(cond);
            collection1 = persistService.findValueInfo(query);
            Iterator iter1 = collection1.iterator();
            IntePackInfo intePackInfoTemp;
            while (iter1.hasNext())
            {
                intePackInfoTemp = (IntePackInfo) iter1.next();
                intePackInfo = intePackInfoTemp;
            }
            if(intePackInfo == null)
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("getProductsByIntePackID(String) - end"); //$NON-NLS-1$
                }
                return null;
            }
            //2.根据集成包值对象的来源查询相关的零部件
            //如果来源类型是更改采用通知书
            /*if(intePackInfo.getSourceType() == IntePackSourceType.ADOPTNOTICE)
            {
                //根据集成包值对象的来源查询相关的更改采用通知书的值对象
                //查询到的更改采用通知书值对象
                AdoptNoticeInfo adoptNoticeInfo = null;
                //创建新的查找对象query
                query = new QMQuery("AdoptNotice");
                cond = new QueryCondition("bsoID", QueryCondition.EQUAL,
                        intePackInfo.getSource());
                query.addCondition(cond);
                collection2 = persistService.findValueInfo(query);
                Iterator iter2 = collection2.iterator();
                AdoptNoticeInfo adoptNoticeTemp;
                while (iter2.hasNext())
                {
                    adoptNoticeTemp = (AdoptNoticeInfo) iter2.next();
                    adoptNoticeInfo = adoptNoticeTemp;
                }
                if(adoptNoticeInfo == null)
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("getProductsByIntePackID(String) - end"); //$NON-NLS-1$
                    }
                    return null;
                }
                query = new QMQuery("QMPart", "AdoptNoticePartLink");
                query.addCondition(0, 1, new QueryCondition("bsoID",
                        "rightBsoID"));
                query.addAND();
                query.addCondition(1, new QueryCondition("leftBsoID",
                        QueryCondition.EQUAL, adoptNoticeInfo.getBsoID()));
                query.setVisiableResult(1);
                collection3 = persistService.findValueInfo(query, false);
            }
            //如果来源类型是采用通知书
            else */if(intePackInfo.getSourceType() == IntePackSourceType.PROMULGATENOTIFY)
            {
                PromulgateNotifyService promulgateNotifyService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("PromulgateNotifyService");
                collection3 = promulgateNotifyService
                        .getPartsByProId(intePackInfo.getSource());
            }
        }
        catch (Exception e)
        {
            logger.error("getProductsByIntePackID(String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByIntePackID(String) - end"); //$NON-NLS-1$
        }
        return collection3;
    }

    /**
     * 创建集成包
     * @param intePackInfo IntePackInfo
     * @return IntePackInfo
     */
    public IntePackIfc createIntePack(IntePackIfc intePackInfo)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("createIntePack(IntePackInfo) - start"); //$NON-NLS-1$
        }
        //20071130  add by zhangq begin
//        if(intePackInfo.getSource() == null
//                || intePackInfo.getSource().length() <= 0)
//        {
//            Object aobj[] = {intePackInfo.getName()};
//            //"集成包*的来源不能为空！"
//            throw new IntePackException(RESOURCE, "IntePack.01", aobj);
//        }
        //20071130  add by zhangq begin
        //获取Session服务
        SessionService sService = (SessionService) EJBServiceHelper
                .getService("SessionService");
        //获得持久化服务
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //得到当前用户
        UserIfc user = (UserIfc) sService.getCurUserInfo();
        intePackInfo.setCreator(user.getBsoID());
        //得到属性文件中配置的默认的集成包管理域
        String defaultDomainName = (String) RemoteProperty
                .getProperty("intePackDefaultDomain","System");
        if(defaultDomainName == null || defaultDomainName.trim().length() <= 0)
        {
            //"没有找到集成包默认管理域"
            throw new IntePackException(RESOURCE, "IntePack.02", null);
        }
        intePackInfo.setDomain(DomainHelper.getDomainID(defaultDomainName));
        //创建时的状态是“已创建”。
        intePackInfo.setState(0);
        //FolderService fService = (FolderService) EJBServiceHelper
        //.getService("FolderService");
        //fService.assignFolder(newinfo, (String) RemoteProperty
        //.getProperty("promulgateNotifyDefaultFolder"));
        try
        {
            intePackInfo = (IntePackInfo) pService.saveValueInfo(intePackInfo);
        }
        catch (QMException e)
        {
            logger.error("createIntePack(IntePackInfo)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("createIntePack(IntePackInfo) - end"); //$NON-NLS-1$
        }
        return intePackInfo;
    }

    /**
     * 删除集成包
     * @param id String
     * @throws QMException
     */
    public void deleteIntePack(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteIntePack(String) - start"); //$NON-NLS-1$
        }
        PersistService pService = null;
        try
        {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            pService.deleteBso(id);
        }
        catch (QMException e)
        {
            logger.error("deleteIntePack(String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteIntePack(String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 删除集成包
     * @param intePackIfc IntePackIfc
     * @throws QMException
     */
    public void deleteIntePack(IntePackIfc intePackIfc) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteIntePack(IntePackIfc) - start"); //$NON-NLS-1$
        }
        PersistService pService = null;
        try
        {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            pService.deleteValueInfo(intePackIfc);
        }
        catch (QMException e)
        {
            logger.error("deleteIntePack(IntePackIfc)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteIntePack(IntePackIfc) - end"); //$NON-NLS-1$
        }
    }
    
    /**
     * 青汽ERP数据发布
     * @param id 数据集成包标识
     * @param xmlName 发布XML文件的名称
     * @return IntePackInfo
     * @throws QMExcepiton
     */
    public IntePackInfo publishIntePack(String id ,String xmlName,Collection coll)throws QMException
    {
    	BaseDataPublisher.setXmlName(xmlName);
    	BaseDataPublisher.setColleciton(coll);
        IntePackInfo info = publishIntePack(id);
        //RequestHelper.initRouteHashMap();
        return info;
    }
    /**
     * 青汽ERP数据发布
     * 为了使得在发布的时候知道本次新发的物料都有哪些，需要在这里把过滤后的零件清单返回一个
     * @param id 数据集成包标识
     * @param xmlName 发布XML文件的名称
     * @return IntePackInfo
     * @throws QMExcepiton
     */
    public IntePackInfo publishIntePack(String id ,String xmlName,Collection coll,Vector filterParts)throws QMException
    {
    	try{
    	BaseDataPublisher.setXmlName(xmlName);
    	BaseDataPublisher.setColleciton(coll);
    	BaseDataPublisher.setFilterParts(filterParts);
        IntePackInfo info = publishIntePack(id);
        //RequestHelper.initRouteHashMap();
        return info;
    	}catch(QMException e){
    		e.printStackTrace();
    		throw e;
    	}
    }

    /**
     * 发布集成包
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo publishIntePack(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("publishIntePack(String) - end");
        }
        IntePackInfo intePackInfo1 = null;
        //获取持久化服务
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        IntePackInfo intePackInfo = (IntePackInfo) pService.refreshInfo(id);
        //获取Session服务
        SessionService sService = (SessionService) EJBServiceHelper
                .getService("SessionService");
        //得到当前用户
        UserIfc user = (UserIfc) sService.getCurUserInfo();
        if(!user.getBsoID().equals(intePackInfo.getCreator()))
        {
            Object aobj[] = {user.getUsersName()};
            //"用户*不是该集成包的创建者，无法发布集成包！"
            throw new IntePackException(RESOURCE, "IntePack.03", aobj);
        }
//        如果当前用户没有修改集成包的权限
        if(!isIntePackPublish(intePackInfo))
        {
            DisplayIdentity displayidentity = IdentityFactory
                    .getDisplayIdentity(intePackInfo);
            Locale locale = RemoteProperty.getVersionLocale();
            Object aobj[] = {displayidentity.getLocalizedMessage(locale)};
            //"集成包服务:对该集成包无发布权限，不能发布集成包 * ，请检查权限！"
            throw new IntePackException(RESOURCE, "IntePack.04", aobj);
        }
        try
        {
            //首先发布数据
            BaseDataPublisher.publish(pService.refreshInfo(intePackInfo
                    .getSource()));
            //接着更新集成包：设置发布者、发布时间和“已发布”状态
            //设置发布者
            intePackInfo.setPublisher(user.getBsoID());
            long l = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(l);
            //设置发布时间
            intePackInfo.setPublishTime(timestamp);
            //设置状态：已发布
            intePackInfo.setState(9);
            //将文档对象存储到数据库中
            intePackInfo1 = (IntePackInfo) pService
                    .updateValueInfo(intePackInfo);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            logger.error("publishIntePack(String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("publishIntePack(String) - end");
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("publishIntePack(String) - end"); //$NON-NLS-1$
        }
        return intePackInfo1;
    }

    /**
     * 发布集成包
     * @param intePackInfo IntePackInfo
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo publishIntePack(IntePackInfo intePackInfo)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("publishIntePack(IntePackInfo) - start"); //$NON-NLS-1$
        }
        publishIntePack(intePackInfo.getBsoID());
        if(logger.isDebugEnabled())
        {
            logger.debug("publishIntePack(IntePackInfo) - end"); //$NON-NLS-1$
        }
        return intePackInfo;
    }

    /**
     * 搜索集成包
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo searchIntePackByID(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("searchIntePackByID(String) - start");
        }
        //查询到的集成包值对象
        IntePackInfo intePackInfo = null;
        //集成包的查询结果
        Collection collection = null;
        try
        {
            //创建新的查找对象query
            QMQuery query = new QMQuery("IntePack");
            QueryCondition cond = new QueryCondition("bsoID",
                    QueryCondition.EQUAL, id);
            query.addCondition(cond);
            //获得持久化服务
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            collection = persistService.findValueInfo(query);
        }
        catch (Exception e)
        {
            logger.error("searchIntePackByID(String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        Iterator iter = collection.iterator();
        IntePackInfo intePackInfoTemp;
        while (iter.hasNext())
        {
            intePackInfoTemp = (IntePackInfo) iter.next();
            intePackInfo = intePackInfoTemp;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("searchIntePackByID(String) - end");
        }
        return intePackInfo;
    }

    /**
     * 搜索集成包
     * @param name String:集成包的名称
     * @param checkboxName String：名称的查询类型：包含或者不包含
     * @param sourceType String：集成包的来源类型
     * @param checkboxSourceType String：来源类型的查询类型：包含或者不包含
     * @param source String：集成包的来源
     * @param checkboxSource String：来源的查询类型：包含或者不包含
     * @param state String：集成包的状态
     * @param checkboxState String：状态的查询类型：包含或者不包含
     * @param creator String：集成包的创建者
     * @param checkboxCreator String：创建者的查询类型：包含或者不包含
     * @param createTime String：集成包的创建时间
     * @param checkboxCreateTime String：创建时间的查询类型：包含或者不包含
     * @param publisher String：集成包的发布者
     * @param checkboxPublisher String：发布者的查询类型：包含或者不包含
     * @param publishTime String：集成包的发布时间
     * @param checkboxPublishTime String：发布时间的查询类型：包含或者不包含
     * @throws QMException
     * @return Collection：查询结果集合
     */
    public Collection searchIntePackByID(String name, String checkboxName,
            String sourceType, String checkboxSourceType, String source,
            String checkboxSource, String state, String checkboxState,
            String creator, String checkboxCreator, String createTime,
            String checkboxCreateTime, String publisher,
            String checkboxPublisher, String publishTime,
            String checkboxPublishTime) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchIntePackByID(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) - start"); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchIntePackByID(String, String, String, String, String, String, String, String) - start");
        }
        Collection vec = new Vector();
        //  获得持久化服务
        PersistService ps = null;
        try
        {
            ps = (PersistService) EJBServiceHelper.getService("PersistService");
            //创建新的查找对象query
            QMQuery query1 = null;
            query1 = new QMQuery("IntePack");
            //根据集成包名称查询
            query1 = addStringCondition(query1, "name", name, checkboxName);
            //根据集成包来源类型查询
            query1 = addStringCondition(query1, "sourceTypeStr", sourceType,
                    checkboxSourceType);
            //根据集成包来源编号查询
            query1 = addStringCondition(query1, "source", source,
                    checkboxSource);
            //根据集成包状态查询
            query1 = addIntegerCondition(query1, "state", state, checkboxState);
            //根据集成创建者查询
            query1 = addUserCondition(query1, "creator", creator,
                    checkboxCreator);
            //根据集成包创建时间查询
            query1 = addTimeCondition(query1, "createTime", createTime,
                    checkboxCreateTime);
            //根据集成包发布者查询
            query1 = addUserCondition(query1, "publisher", publisher,
                    checkboxPublisher);
            //根据集成包发布时间查询
            query1 = addTimeCondition(query1, "publishTime", publishTime,
                    checkboxPublishTime);
            Collection col = ps.findValueInfo(query1, false);
            //获得迭代
            Iterator iter = col.iterator();
            while (iter.hasNext())
            {
                vec.add(iter.next());
            }
        }
        catch (QMException e)
        {
            logger
                    .error(
                            "searchIntePackByID(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        //判断集合中的内容
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchIntePackByID(String, String, String, String, String, String, String, String) - end"); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchIntePackByID(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) - end"); //$NON-NLS-1$
        }
        return vec;
    }

    /**
     * 搜索集成包
     * @param name String:集成包的名称
     * @param sourceType String：集成包的来源类型
     * @param source String：集成包的来源
     * @param state String：集成包的状态
     * @param creator String：集成包的创建者
     * @param createTime String：集成包的创建时间
     * @param publisher String：集成包的发布者
     * @param publishTime String：集成包的发布时间
     * @throws QMException
     * @return Collection：查询结果集合
     */
    public Collection searchIntePackByID(String name, String sourceType,
            String source, String state, String creator, String createTime,
            String publisher, String publishTime) throws QMException
    {
        return searchIntePackByID(name, "false", sourceType, "false", source,
                "false", state, "false", creator, "false", createTime, "false",
                publisher, "false", publishTime, "false");
    }

    /**
     * 增加查询条件：查询colName列的值为value。
     * 注意：这个只能查询VARCHAR2类型的列，且不能查询用户（比如创建者和修改者等等）。
     * @param query QMQuery：原来的查询条件
     * @param colName String：查询的列名
     * @param value String：查询的值
     * @param isNotFlag String：查询条件类型的标志：是“LIKE”或者“NOT LIKE”
     * @throws QMException
     * @return QMQuery
     */
    private QMQuery addStringCondition(QMQuery query1, String colName,
            String value, String isNotFlag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addStringCondition(QMQuery,String,String,String) - start");
        }
        QMQuery query = query1;
        try
        {
            //如果值不为空，进行查询
            if(value != null && !value.trim().equals(""))
            {
                if(query.getConditionCount() >= 1)
                {
                    query.addAND();
                }
                //如果用户查询结果中想包括此名称
                if(isNotFlag != null && isNotFlag.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(colName,
                            QueryCondition.LIKE, getLikeSearchString(value));
                    query.addCondition(cond);
                }
                //如果用户查询结果中不想包括此名称
                else
                {
                    QueryCondition cond = new QueryCondition(colName,
                            QueryCondition.NOT_LIKE, getLikeSearchString(value));
                    query.addCondition(cond);
                }
            }
        }
        catch (QMException e)
        {
            logger.error(
                    "addStringCondition(QMQuery, String, String, String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addStringCondition(QMQuery,String,String,String) - end"); //$NON-NLS-1$
        }
        return query;
    }

    /**
     * 增加查询条件：查询colName列的值为value。
     * 注意：这个只能查询整数类型的列。
     * @param query1 QMQuery：原来的查询条件
     * @param colName String：查询的列名
     * @param value String：查询的值
     * @param isNotFlag String：查询条件类型的标志：是“=”或者“!=”
     * @throws QMException
     * @return QMQuery
     */
    private QMQuery addIntegerCondition(QMQuery query1, String colName,
            String value, String isNotFlag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addIntegerCondition(QMQuery , String, String, String) - start");
        }
        QMQuery query = query1;
        try
        {
            //如果值不为空，进行查询
            if(value != null && !value.trim().equals(""))
            {
                Integer inte = Integer.valueOf(value);
                if(query.getConditionCount() >= 1)
                {
                    query.addAND();
                }
                //如果用户查询结果中想包括此值
                if(isNotFlag != null && isNotFlag.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(colName,
                            QueryCondition.EQUAL, inte);
                    query.addCondition(cond);
                }
                //如果用户查询结果中不想包括此值
                else
                {
                    QueryCondition cond = new QueryCondition(colName,
                            QueryCondition.NOT_EQUAL, inte);
                    query.addCondition(cond);
                }
            }
        }
        catch (QMException e)
        {
            logger.error(
                    "addIntegerCondition(QMQuery, String, String, String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addIntegerCondition(QMQuery , String, String, String) - end");
        }
        return query;
    }

    /**
     * 增加查询条件：查询colName列的值为value。
     * 注意：这个只能查询用户类型（比如创建者）的列。
     * @param query QMQuery：原来的查询条件
     * @param colName String：查询的列名
     * @param value String：查询的值
     * @param isNotFlag String：查询条件类型的标志：是“LIKE”或者“NOT LIKE”
     * @throws QMException
     * @return QMQuery
     */
    private QMQuery addUserCondition(QMQuery query1, String colName,
            String value, String isNotFlag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addUserCondition(QMQuery , String, String, String) - start");
        }
        QMQuery query = query1;
        //如果用户值不为空
        if(value != null && !value.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
            }
            int j = query.appendBso("User", false);
            QueryCondition cond1 = new QueryCondition(colName, "bsoID");
            query.addCondition(0, j, cond1);
            query.addAND();
            QueryCondition cond2;
            QueryCondition cond3;
            //如果用户查询的结果中想包括此用户
            if(isNotFlag != null && isNotFlag.trim().equals("false"))
            {
                query.addLeftParentheses();
                cond2 = new QueryCondition("usersName", "like",
                        getLikeSearchString(value));
                query.addCondition(j, cond2);
                cond3 = new QueryCondition("usersDesc", "like",
                        getLikeSearchString(value));
                query.addOR();
                query.addCondition(j, cond3);
                query.addRightParentheses();
            }
            //如果用户查询的结果中不想包括此用户
            else
            {
                query.addLeftParentheses();
                cond2 = new QueryCondition("usersName", "not like",
                        getLikeSearchString(value));
                query.addCondition(j, cond2);
                cond3 = new QueryCondition("usersDesc", "not like",
                        getLikeSearchString(value));
                query.addAND();
                query.addCondition(j, cond3);
                query.addRightParentheses();
            }
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addUserCondition(QMQuery , String, String, String) - end");
        }
        return query;
    }

    /**
     * 增加查询条件：查询colName列的值为value。
     * 注意：这个只能查询DATE类型的列。
     * @param query QMQuery：原来的查询条件
     * @param colName String：查询的列名
     * @param value String：查询的值
     * @param isNotFlag String：查询条件类型的标志
     * @throws QMException
     * @return QMQuery
     */
    private QMQuery addTimeCondition(QMQuery query, String colName,
            String textTime, String isNotFlag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addTimeCondition(QMQuery , String, String, String) - start");
        }
        //如果时间值不为空
        if(textTime != null && !textTime.trim().equals(""))
        {
            boolean isNot = true;
            if(isNotFlag != null && isNotFlag.trim().equals("false"))
            {
                isNot = false;
            }
            //开始时间
            Timestamp beginTime = null;
            //结束时间
            Timestamp endTime = null;
            //开始时间字符串
            String beginTimeStr = null;
            //结束时间字符串
            String endTimeStr = null;
            //开始时间与结束时间之间的标志：这里为“-”（有的为“;”）
            int i = textTime.indexOf("-");
            try
            {
                //只有结束时间
                if(i == 0)
                {
                    endTimeStr = textTime.substring(1);
                    DateHelper dateHelperEnd = new DateHelper(endTimeStr);
                    endTime = new Timestamp(dateHelperEnd.getDate().getTime());
                }
                //只有开始时间
                else if(i == textTime.length() - 1)
                {
                    beginTimeStr = textTime.substring(0, i);
                    DateHelper dateHelperBegin = new DateHelper(beginTimeStr);
                    beginTime = new Timestamp(dateHelperBegin.getDate()
                            .getTime());
                }
                else
                {
                    //是时间区间：有开始时间和结束时间
                    beginTimeStr = textTime.substring(0, i);
                    endTimeStr = textTime.substring(i + 1);
                    DateHelper dateHelperBegin = new DateHelper(beginTimeStr);
                    beginTime = new Timestamp(dateHelperBegin.getDate()
                            .getTime());
                    DateHelper dateHelperEnd = new DateHelper(endTimeStr);
                    endTime = new Timestamp(dateHelperEnd.getDate().getTime());
                }
            }
            catch (Exception ex)
            {
                logger
                        .error(
                                "addTimeCondition(QMQuery, String, String, String)", ex); //$NON-NLS-1$
                throw new IntePackException(ex);
            }
            if(logger.isDebugEnabled())
            {
                logger
                        .debug("=> addTimeCondition(QMQuery , String, String, String)  beginTimeStr = "
                                + beginTimeStr);
                logger
                        .debug("=> addTimeCondition(QMQuery , String, String, String)  endTimeStr = "
                                + endTimeStr);
            }
            //搜索条件1
            QueryCondition cond1;
            //搜索条件2
            QueryCondition cond2;
            //如果开始时间和结束时间都有
            if(beginTime != null && endTime != null)
            {
                //如果开始时间小于完成时间，则交换
                if(beginTime.compareTo(endTime) > 0)
                {
                    Timestamp temp = beginTime;
                    beginTime = endTime;
                    endTime = temp;
                }
                query.addLeftParentheses();
                //如果用户查询的结果中不想包括此时间区间
                if(isNot)
                {
                    cond1 = new QueryCondition(colName, "<", beginTime);
                    query.addCondition(cond1);
                    query.addOR();
                    cond2 = new QueryCondition(colName, ">", endTime);
                    query.addCondition(cond2);
                }
                else
                {
                    cond1 = new QueryCondition(colName, ">=", beginTime);
                    query.addCondition(cond1);
                    query.addAND();
                    cond2 = new QueryCondition(colName, "<=", endTime);
                    query.addCondition(cond2);
                }
                query.addRightParentheses();
            }
            //如果只有开始时间
            else if(beginTime != null)
            {
                //如果用户查询的结果中不想包括开始时间(即小于等于开始时间)
                if(isNot)
                {
                    cond1 = new QueryCondition(colName, "<=", beginTime);
                }
                else
                {
                    cond1 = new QueryCondition(colName, ">=", beginTime);
                }
                query.addCondition(cond1);
            }
            //如果只有结束时间
            else if(endTime != null)
            {
                //如果用户查询的结果中不想包括结束时间(即大于等于结束时间)
                if(isNot)
                {
                    cond1 = new QueryCondition(colName, ">=", endTime);
                }
                else
                {
                    cond1 = new QueryCondition(colName, "<=", endTime);
                }
                query.addCondition(cond1);
            }
            if(logger.isDebugEnabled())
            {
                logger
                        .debug("addTimeCondition(QMQuery , String, String, String) - end");
            }
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("addTimeCondition(QMQuery, String, String, String) - end"); //$NON-NLS-1$
        }
        return query;
    }

    /**
     * 匹配字符查询处理。将字符串oldStr中的"/*"转化成"*"，"*"转化成"%"，"?"转化成"_","%"不处理。
     * 例 "shf/*pdm%caxfhy?*"  转化成 "shf*pdm%cax%fhy_"
     * @param oldStr
     * @return 转化后的匹配字符查询串
     */
    private String getLikeSearchString(String oldStr)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLikeSearchString(String) - start");
        }
        String resultStr;
        if(oldStr == null || oldStr.trim().equals(""))
        {
            resultStr = oldStr;
        }
        else
        {
            char charArray[] = oldStr.toCharArray();
            int length = charArray.length;
            for (int j = 0; j < length; j++)
            {
                if(charArray[j] == '*')
                {
                    if(j > 0 && charArray[j - 1] == '/')
                    {
                        for (int k = j - 1; k < length - 1; k++)
                        {
                            charArray[k] = charArray[k + 1];
                        }
                        length--;
                        charArray[length] = ' ';
                    }
                    else
                    {
                        charArray[j] = '%';
                    }
                }
                if(charArray[j] == '?')
                {
                    charArray[j] = '_';
                }
            }
            resultStr = (new String(charArray)).trim();
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getLikeSearchString(String) - end");
        }
        return resultStr;
    }

    /**
     * 判断当前用户是否有权修改此文档
     * 如果对该文档无QMPermission.MODIFY权限，返回 false
     * 如果当前用户具有Administrator权限，返回 true
     * @param intePackInfo 文档值对象
     * @return true(有权限) or false(无权限)
     * @throws AdoptNoticeException
     */
    public boolean isIntePackPublish(IntePackInfo intePackInfo)
            throws IntePackException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("isIntePackPublish(IntePackInfo) - start");
        }
        //是否有发布集成包的权限
        boolean hasPublish;
        AccessControlService accessControlService = null;
        try
        {
            accessControlService = (AccessControlService) EJBServiceHelper
                    .getService("AccessControlService");
            if(!accessControlService.hasAccess(intePackInfo,
                    QMPermission.MODIFY))
                hasPublish = false;
            //            if(accessControlService.hasAccess(adoptNoticeInfo,
            //                    QMPermission.ADMINISTRATIVE))
            //                hasPublish = true;
            else
                hasPublish = true;
        }
        catch (QMException ex)
        {
            logger.error("isIntePackPublish(IntePackInfo)", ex); //$NON-NLS-1$
            throw new IntePackException(ex);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("isIntePackPublish(IntePackInfo) - end");
        }
        return hasPublish;
    }

    /**
     * 获得相关的物料结构。
     * @param parentNumber:父物料号
     * @param partNumber:父件号
     * @param partVersionID:父件版本
     * @return 相关的物料结构
     * @throws QMException
     */
    public Collection getStructureLinkMap(String parentNumber,
            String partNumber, String partVersionID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getStructureLinkMap(String) - start");
        }
        //物料结构的查询结果
        Collection collection = null;
        try
        {
            //创建新的查找对象query
            //用父物料号、编号和版本作为查询条件，在物料结构表中查找相关的物料结构
            QMQuery query = new QMQuery("MaterialStructure");
            QueryCondition condition1 = new QueryCondition("partNumber",
                    QueryCondition.EQUAL, partNumber);
            query.addCondition(condition1);
            query.addAND();
            QueryCondition condition2 = new QueryCondition("partVersion",
                    QueryCondition.EQUAL, partVersionID);
            query.addCondition(condition2);
            query.addAND();
            QueryCondition condition3 = new QueryCondition("parentNumber",
                    QueryCondition.EQUAL, parentNumber);
            query.addCondition(condition3);
            //获得持久化服务
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            collection = persistService.findValueInfo(query);
        }
        catch (Exception e)
        {
            logger.error("searchIntePackByID(String)", e); //$NON-NLS-1$
            throw new IntePackException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getStructureLinkMap(String) - end");
        }
        return collection;
    }
    
    /**
     * 发布集成包
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public void publishTechByIntePack(String intePackID) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("getTechsByProID(String) - start"); //$NON-NLS-1$
		}
		MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
		//需要发布的工艺的集合
		Vector vecTech = new Vector();
		QMPartIfc partIfc = null;
		//记录零部件关联的工艺，key和value都是工艺的Bsoid
		HashMap techMap = new HashMap();
		QMFawTechnicsIfc techIfc = null;
		QMFawTechnicsIfc tempTechIfc = null;
		String logString = "";
		try {
			StandardCappService cappService = (StandardCappService) EJBServiceHelper
					.getService("StandardCappService");
			PersistService pService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			VersionControlService versionService = (VersionControlService) EJBServiceHelper
					.getService("VersionControlService");
			TechnicDataService techDataService = (TechnicDataService) EJBServiceHelper
					.getService("TechnicDataService");
			MaterialSplitService matSplitService = (MaterialSplitService) EJBServiceHelper
					.getService("MaterialSplitService");
			PromulgateNotifyService anService = (PromulgateNotifyService) EJBServiceHelper
			.getService("PromulgateNotifyService");
			//逗号分隔符。用于分隔useProcessPartRouteCode。
			String delimiter = "，";
			IntePackInfo intePackInfo = (IntePackInfo) pService
					.refreshInfo(intePackID);
			//采用通知书采用部件集合
			Vector vecPart = anService.getPartsByProId(intePackInfo.getSource());
			//根据零部件查询相应的工艺
			for (int i = 0; i < vecPart.size(); i++) {
				partIfc = (QMPartIfc) vecPart.get(i);
				Collection linkCol = cappService
						.findPartUsageQMTechnicsLinkByPart(partIfc.getBsoID());
				Iterator linkIter = linkCol.iterator();
				// 存放该零部件的工艺下的工序编号，key是工艺的部门代码，value是该部门下的所有工序编号的字符串
				HashMap temp = new HashMap();
				//根据工艺和零部件的关联类，获取工艺信息，重复的过滤。
				while (linkIter.hasNext()) {
					PartUsageQMTechnicsLinkIfc link = (PartUsageQMTechnicsLinkIfc) linkIter
							.next();
					techIfc = (QMFawTechnicsIfc) pService.refreshInfo(link
							.getRightBsoID(), false);
					// techColl = versionService.allVersionsOf(techIfc);
					// techIter = techColl.iterator();
					//得到该工艺的最新版本。
					tempTechIfc = (QMFawTechnicsIfc) versionService
							.getLatestVersion(techIfc);
					// while (techIter.hasNext()) {
					// tempTechIfc = (QMFawTechnicsIfc) techIter.next();
					if (!techMap.containsKey(tempTechIfc.getBsoID())) {
						techMap.put(tempTechIfc.getBsoID(), tempTechIfc
								.getBsoID());
						String noPublishTechType = RemoteProperty.getProperty(
								"noPublishTechType", "09");
						StringTokenizer stringToken = new StringTokenizer(
								noPublishTechType, "，");
						String techType;
						String tempTechtype = tempTechIfc.getTechnicsType()
								.getCode();
						//是否发布该工艺的标志（包装工艺不发布）。
						boolean flag1 = true;
						while (stringToken.hasMoreTokens()) {
							techType = stringToken.nextToken();
							if (tempTechtype != null && techType != null
									&& tempTechtype.equalsIgnoreCase(techType)) {
								flag1 = false;
								break;
							}
						}
						if (flag1) {
							vecTech.add(tempTechIfc);
							msservice.publicTechnics(tempTechIfc.getBsoID());
						}
						BaseValueInfo info = tempTechIfc.getWorkShop();
						//工艺的部门代码
						String technicShop = "";
						if (info instanceof CodingInfo) {
							technicShop = ((CodingInfo) info).getSearchWord();
						}
						// 获得工艺使用的工序集合
						Collection procedureCol = techDataService
								.browseProceduresByTechnics(tempTechIfc
										.getBsoID());
						if (procedureCol != null && procedureCol.size() > 0) {
							for (Iterator iterator = procedureCol.iterator(); iterator
									.hasNext();) {
								QMProcedureIfc procedure = (QMProcedureIfc) iterator
										.next();
								if (!temp.containsKey(technicShop)) {
									temp.put(technicShop, procedure
											.getDescStepNumber());
								} else {
									String stepNumber = (String) temp
											.get(technicShop);
									stepNumber += "->"
											+ procedure.getDescStepNumber();
									temp.put(technicShop, stepNumber);
								}
							}
						}
					}
				}
				Iterator iter = temp.keySet().iterator();
				logger.debug("temp.keySet().size() is " + temp.keySet().size());
				//对该零部件所有的过程流程信息进行校验。
				while (iter.hasNext()) {
					boolean flag1 = true;
					String processRC = (String) iter.next();
					if (processRC != null) {
						logger.debug("processRC is " + processRC);
						String noProcessRouteCode = RemoteProperty.getProperty(
								"noProcessRouteCode", "CY");
						StringTokenizer stringToken = new StringTokenizer(
								noProcessRouteCode, delimiter);
						String processRouteName;
						while (stringToken.hasMoreTokens()) {
							processRouteName = stringToken.nextToken();
							if (processRC != null
									&& processRouteName != null
									&& processRC
											.equalsIgnoreCase(processRouteName)) {
								flag1 = false;
								break;
							}
						}
						logger.debug("flag1 is " + flag1);
						// 如果不是特殊工艺部门代码，则进行检验，
						// 判断该部门的过程流程中的工序是否在工艺的工序中有，
						if (flag1) {
							// 首先获取该零部件的过程代码。
							RouteCodeIBAName routeCodeIBAName = RouteCodeIBAName
									.toRouteCodeIBAName(processRC);
							logger.debug("routeCodeIBAName is "
									+ routeCodeIBAName);
							// 如果有该车间的过程流程事物特性
							if (routeCodeIBAName != null) {
								String display = routeCodeIBAName.getDisplay();
								String processCode = "";
								processCode = matSplitService.getPartIBA(
										partIfc, display);
								logger.debug("processCode is " + processCode);
								// 如果有该车间的过程流程
								if (processCode != null
										&& processCode.length() > 0) {
									stringToken = new StringTokenizer(
											processCode, "->");
									String processNum;
									String tempProNum;
									String tempStr = (String) temp
											.get(processRC);
									logger.debug("tempStr is " + tempStr);
									while (stringToken.hasMoreTokens()) {
										processNum = stringToken.nextToken();
										logger.debug("processNum is "
												+ processNum);
										boolean isFind = false;
										if (tempStr != null
												&& tempStr.trim().length() > 0) {
											StringTokenizer tempStrToken = new StringTokenizer(
													tempStr, "->");
											while (tempStrToken.hasMoreTokens()) {
												tempProNum = tempStrToken
														.nextToken();
												if (tempProNum
														.equals(processNum)) {
													isFind = true;
												}
											}
											if (!isFind) {
												logString += "零部件"
														+ partIfc
																.getPartNumber()
														+ "的\"" + display
														+ "\"中的\'" + processNum
														+ "\'在工艺中找不到该工序。\n";
											}
										}
									}
								} else {
									logString += "零部件"
											+ partIfc.getPartNumber() + "没有\""
											+ processRC + "\"车间的过程流程信息。\n";
								}

							} else {
								logString += "零部件" + partIfc.getPartNumber()
										+ "没有\"" + processRC + "\"车间的过程流程信息。\n";
							}
						}
						// else {
						// continue;
						// }
					}
				}
			}
			// intePackInfo
			logString = "发布集成包\"" + intePackInfo.getName() + "\"时，需要发布"
					+ vecTech.size() + "条工艺。\n" + logString;

		} catch (Exception e) {
			logger.error("getTechsByProID(String)", e); //$NON-NLS-1$
			e.printStackTrace();
			throw new QMException(e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getTechsByProID(String) - end" + vecTech.size()); //$NON-NLS-1$
		}
		logger.debug("vecTech.size() is " + vecTech.size());
		// 如果零部件没有过程流程或者过程流程中的工序在工艺中找不到，记录到文件中。
		if (logString.length() > 0) {
		    TechnicsDataPublisher.createTechnicsLogFile("partInfo", logString);
//			String techPath = RemoteProperty.getProperty("techPath");
//			try {
//				File f = new File(techPath);
//				if (!f.exists()) {
//					f.mkdir();
//				}
//				Timestamp time = new Timestamp(System.currentTimeMillis());
//				String timestr = time.toString();
//				int r = timestr.indexOf(" ");
//				timestr = timestr.substring(0, r);
//				File f1 = new File(techPath + "/partInfo-" + timestr + ".log");
//				if (!f1.exists() || !f1.isFile()) {
//					f1.createNewFile();
//				}
//				PrintWriter out = new PrintWriter(new FileWriter(f1, true),
//						true);
//				StringBuffer buffer = new StringBuffer();
//				final SimpleDateFormat simple = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm:ss ");
//				buffer.append(simple.format(new Date()) + "\n");
//				buffer.append(logString);
//				out.print(buffer);
//				out.flush();
//				out.close();
//			} catch (Exception e) {
//				logger.error("create technics log file", e); //$NON-NLS-1$
//				e.printStackTrace();
//			}
		}
	}
}