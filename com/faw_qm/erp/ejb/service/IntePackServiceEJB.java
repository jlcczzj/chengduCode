/**
 * ���ɳ���IntePackServiceEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
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
     * ��Դ��
     */
    private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";

    /**��ȡ��������
     * @return String
     */
    public String getServiceName()
    {
        return "IntePackService";
    }

    /**
     * ���ݼ��ɰ������㲿��
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByIntePackID(String bsoid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByIntePackID(String) - start"); //$NON-NLS-1$
        }
        //�������Ҷ���query
        QMQuery query;
        QueryCondition cond;
        //��ó־û�����
        PersistService persistService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //1.���ȸ��ݼ��ɰ���ID���Ҽ��ɰ�ֵ����
        //��ѯ���ļ��ɰ�ֵ����
        IntePackInfo intePackInfo = null;
        //���ɰ��Ĳ�ѯ���
        Collection collection1 = null;
        //���Ĳ���֪ͨ��Ĳ�ѯ���
        Collection collection2 = null;
        //Part�Ĳ�ѯ���
        Collection collection3 = null;
        try
        {
            //�����µĲ��Ҷ���query
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
            //2.���ݼ��ɰ�ֵ�������Դ��ѯ��ص��㲿��
            //�����Դ�����Ǹ��Ĳ���֪ͨ��
            /*if(intePackInfo.getSourceType() == IntePackSourceType.ADOPTNOTICE)
            {
                //���ݼ��ɰ�ֵ�������Դ��ѯ��صĸ��Ĳ���֪ͨ���ֵ����
                //��ѯ���ĸ��Ĳ���֪ͨ��ֵ����
                AdoptNoticeInfo adoptNoticeInfo = null;
                //�����µĲ��Ҷ���query
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
            //�����Դ�����ǲ���֪ͨ��
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
     * �������ɰ�
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
//            //"���ɰ�*����Դ����Ϊ�գ�"
//            throw new IntePackException(RESOURCE, "IntePack.01", aobj);
//        }
        //20071130  add by zhangq begin
        //��ȡSession����
        SessionService sService = (SessionService) EJBServiceHelper
                .getService("SessionService");
        //��ó־û�����
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //�õ���ǰ�û�
        UserIfc user = (UserIfc) sService.getCurUserInfo();
        intePackInfo.setCreator(user.getBsoID());
        //�õ������ļ������õ�Ĭ�ϵļ��ɰ�������
        String defaultDomainName = (String) RemoteProperty
                .getProperty("intePackDefaultDomain","System");
        if(defaultDomainName == null || defaultDomainName.trim().length() <= 0)
        {
            //"û���ҵ����ɰ�Ĭ�Ϲ�����"
            throw new IntePackException(RESOURCE, "IntePack.02", null);
        }
        intePackInfo.setDomain(DomainHelper.getDomainID(defaultDomainName));
        //����ʱ��״̬�ǡ��Ѵ�������
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
     * ɾ�����ɰ�
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
     * ɾ�����ɰ�
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
     * ����ERP���ݷ���
     * @param id ���ݼ��ɰ���ʶ
     * @param xmlName ����XML�ļ�������
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
     * ����ERP���ݷ���
     * Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
     * @param id ���ݼ��ɰ���ʶ
     * @param xmlName ����XML�ļ�������
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
     * �������ɰ�
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
        //��ȡ�־û�����
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        IntePackInfo intePackInfo = (IntePackInfo) pService.refreshInfo(id);
        //��ȡSession����
        SessionService sService = (SessionService) EJBServiceHelper
                .getService("SessionService");
        //�õ���ǰ�û�
        UserIfc user = (UserIfc) sService.getCurUserInfo();
        if(!user.getBsoID().equals(intePackInfo.getCreator()))
        {
            Object aobj[] = {user.getUsersName()};
            //"�û�*���Ǹü��ɰ��Ĵ����ߣ��޷��������ɰ���"
            throw new IntePackException(RESOURCE, "IntePack.03", aobj);
        }
//        �����ǰ�û�û���޸ļ��ɰ���Ȩ��
        if(!isIntePackPublish(intePackInfo))
        {
            DisplayIdentity displayidentity = IdentityFactory
                    .getDisplayIdentity(intePackInfo);
            Locale locale = RemoteProperty.getVersionLocale();
            Object aobj[] = {displayidentity.getLocalizedMessage(locale)};
            //"���ɰ�����:�Ըü��ɰ��޷���Ȩ�ޣ����ܷ������ɰ� * ������Ȩ�ޣ�"
            throw new IntePackException(RESOURCE, "IntePack.04", aobj);
        }
        try
        {
            //���ȷ�������
            BaseDataPublisher.publish(pService.refreshInfo(intePackInfo
                    .getSource()));
            //���Ÿ��¼��ɰ������÷����ߡ�����ʱ��͡��ѷ�����״̬
            //���÷�����
            intePackInfo.setPublisher(user.getBsoID());
            long l = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(l);
            //���÷���ʱ��
            intePackInfo.setPublishTime(timestamp);
            //����״̬���ѷ���
            intePackInfo.setState(9);
            //���ĵ�����洢�����ݿ���
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
     * �������ɰ�
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
     * �������ɰ�
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
        //��ѯ���ļ��ɰ�ֵ����
        IntePackInfo intePackInfo = null;
        //���ɰ��Ĳ�ѯ���
        Collection collection = null;
        try
        {
            //�����µĲ��Ҷ���query
            QMQuery query = new QMQuery("IntePack");
            QueryCondition cond = new QueryCondition("bsoID",
                    QueryCondition.EQUAL, id);
            query.addCondition(cond);
            //��ó־û�����
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
     * �������ɰ�
     * @param name String:���ɰ�������
     * @param checkboxName String�����ƵĲ�ѯ���ͣ��������߲�����
     * @param sourceType String�����ɰ�����Դ����
     * @param checkboxSourceType String����Դ���͵Ĳ�ѯ���ͣ��������߲�����
     * @param source String�����ɰ�����Դ
     * @param checkboxSource String����Դ�Ĳ�ѯ���ͣ��������߲�����
     * @param state String�����ɰ���״̬
     * @param checkboxState String��״̬�Ĳ�ѯ���ͣ��������߲�����
     * @param creator String�����ɰ��Ĵ�����
     * @param checkboxCreator String�������ߵĲ�ѯ���ͣ��������߲�����
     * @param createTime String�����ɰ��Ĵ���ʱ��
     * @param checkboxCreateTime String������ʱ��Ĳ�ѯ���ͣ��������߲�����
     * @param publisher String�����ɰ��ķ�����
     * @param checkboxPublisher String�������ߵĲ�ѯ���ͣ��������߲�����
     * @param publishTime String�����ɰ��ķ���ʱ��
     * @param checkboxPublishTime String������ʱ��Ĳ�ѯ���ͣ��������߲�����
     * @throws QMException
     * @return Collection����ѯ�������
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
        //  ��ó־û�����
        PersistService ps = null;
        try
        {
            ps = (PersistService) EJBServiceHelper.getService("PersistService");
            //�����µĲ��Ҷ���query
            QMQuery query1 = null;
            query1 = new QMQuery("IntePack");
            //���ݼ��ɰ����Ʋ�ѯ
            query1 = addStringCondition(query1, "name", name, checkboxName);
            //���ݼ��ɰ���Դ���Ͳ�ѯ
            query1 = addStringCondition(query1, "sourceTypeStr", sourceType,
                    checkboxSourceType);
            //���ݼ��ɰ���Դ��Ų�ѯ
            query1 = addStringCondition(query1, "source", source,
                    checkboxSource);
            //���ݼ��ɰ�״̬��ѯ
            query1 = addIntegerCondition(query1, "state", state, checkboxState);
            //���ݼ��ɴ����߲�ѯ
            query1 = addUserCondition(query1, "creator", creator,
                    checkboxCreator);
            //���ݼ��ɰ�����ʱ���ѯ
            query1 = addTimeCondition(query1, "createTime", createTime,
                    checkboxCreateTime);
            //���ݼ��ɰ������߲�ѯ
            query1 = addUserCondition(query1, "publisher", publisher,
                    checkboxPublisher);
            //���ݼ��ɰ�����ʱ���ѯ
            query1 = addTimeCondition(query1, "publishTime", publishTime,
                    checkboxPublishTime);
            Collection col = ps.findValueInfo(query1, false);
            //��õ���
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
        //�жϼ����е�����
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
     * �������ɰ�
     * @param name String:���ɰ�������
     * @param sourceType String�����ɰ�����Դ����
     * @param source String�����ɰ�����Դ
     * @param state String�����ɰ���״̬
     * @param creator String�����ɰ��Ĵ�����
     * @param createTime String�����ɰ��Ĵ���ʱ��
     * @param publisher String�����ɰ��ķ�����
     * @param publishTime String�����ɰ��ķ���ʱ��
     * @throws QMException
     * @return Collection����ѯ�������
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
     * ���Ӳ�ѯ��������ѯcolName�е�ֵΪvalue��
     * ע�⣺���ֻ�ܲ�ѯVARCHAR2���͵��У��Ҳ��ܲ�ѯ�û������紴���ߺ��޸��ߵȵȣ���
     * @param query QMQuery��ԭ���Ĳ�ѯ����
     * @param colName String����ѯ������
     * @param value String����ѯ��ֵ
     * @param isNotFlag String����ѯ�������͵ı�־���ǡ�LIKE�����ߡ�NOT LIKE��
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
            //���ֵ��Ϊ�գ����в�ѯ
            if(value != null && !value.trim().equals(""))
            {
                if(query.getConditionCount() >= 1)
                {
                    query.addAND();
                }
                //����û���ѯ����������������
                if(isNotFlag != null && isNotFlag.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(colName,
                            QueryCondition.LIKE, getLikeSearchString(value));
                    query.addCondition(cond);
                }
                //����û���ѯ����в������������
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
     * ���Ӳ�ѯ��������ѯcolName�е�ֵΪvalue��
     * ע�⣺���ֻ�ܲ�ѯ�������͵��С�
     * @param query1 QMQuery��ԭ���Ĳ�ѯ����
     * @param colName String����ѯ������
     * @param value String����ѯ��ֵ
     * @param isNotFlag String����ѯ�������͵ı�־���ǡ�=�����ߡ�!=��
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
            //���ֵ��Ϊ�գ����в�ѯ
            if(value != null && !value.trim().equals(""))
            {
                Integer inte = Integer.valueOf(value);
                if(query.getConditionCount() >= 1)
                {
                    query.addAND();
                }
                //����û���ѯ������������ֵ
                if(isNotFlag != null && isNotFlag.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(colName,
                            QueryCondition.EQUAL, inte);
                    query.addCondition(cond);
                }
                //����û���ѯ����в��������ֵ
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
     * ���Ӳ�ѯ��������ѯcolName�е�ֵΪvalue��
     * ע�⣺���ֻ�ܲ�ѯ�û����ͣ����紴���ߣ����С�
     * @param query QMQuery��ԭ���Ĳ�ѯ����
     * @param colName String����ѯ������
     * @param value String����ѯ��ֵ
     * @param isNotFlag String����ѯ�������͵ı�־���ǡ�LIKE�����ߡ�NOT LIKE��
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
        //����û�ֵ��Ϊ��
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
            //����û���ѯ�Ľ������������û�
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
            //����û���ѯ�Ľ���в���������û�
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
     * ���Ӳ�ѯ��������ѯcolName�е�ֵΪvalue��
     * ע�⣺���ֻ�ܲ�ѯDATE���͵��С�
     * @param query QMQuery��ԭ���Ĳ�ѯ����
     * @param colName String����ѯ������
     * @param value String����ѯ��ֵ
     * @param isNotFlag String����ѯ�������͵ı�־
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
        //���ʱ��ֵ��Ϊ��
        if(textTime != null && !textTime.trim().equals(""))
        {
            boolean isNot = true;
            if(isNotFlag != null && isNotFlag.trim().equals("false"))
            {
                isNot = false;
            }
            //��ʼʱ��
            Timestamp beginTime = null;
            //����ʱ��
            Timestamp endTime = null;
            //��ʼʱ���ַ���
            String beginTimeStr = null;
            //����ʱ���ַ���
            String endTimeStr = null;
            //��ʼʱ�������ʱ��֮��ı�־������Ϊ��-�����е�Ϊ��;����
            int i = textTime.indexOf("-");
            try
            {
                //ֻ�н���ʱ��
                if(i == 0)
                {
                    endTimeStr = textTime.substring(1);
                    DateHelper dateHelperEnd = new DateHelper(endTimeStr);
                    endTime = new Timestamp(dateHelperEnd.getDate().getTime());
                }
                //ֻ�п�ʼʱ��
                else if(i == textTime.length() - 1)
                {
                    beginTimeStr = textTime.substring(0, i);
                    DateHelper dateHelperBegin = new DateHelper(beginTimeStr);
                    beginTime = new Timestamp(dateHelperBegin.getDate()
                            .getTime());
                }
                else
                {
                    //��ʱ�����䣺�п�ʼʱ��ͽ���ʱ��
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
            //��������1
            QueryCondition cond1;
            //��������2
            QueryCondition cond2;
            //�����ʼʱ��ͽ���ʱ�䶼��
            if(beginTime != null && endTime != null)
            {
                //�����ʼʱ��С�����ʱ�䣬�򽻻�
                if(beginTime.compareTo(endTime) > 0)
                {
                    Timestamp temp = beginTime;
                    beginTime = endTime;
                    endTime = temp;
                }
                query.addLeftParentheses();
                //����û���ѯ�Ľ���в��������ʱ������
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
            //���ֻ�п�ʼʱ��
            else if(beginTime != null)
            {
                //����û���ѯ�Ľ���в��������ʼʱ��(��С�ڵ��ڿ�ʼʱ��)
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
            //���ֻ�н���ʱ��
            else if(endTime != null)
            {
                //����û���ѯ�Ľ���в����������ʱ��(�����ڵ��ڽ���ʱ��)
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
     * ƥ���ַ���ѯ�������ַ���oldStr�е�"/*"ת����"*"��"*"ת����"%"��"?"ת����"_","%"������
     * �� "shf/*pdm%caxfhy?*"  ת���� "shf*pdm%cax%fhy_"
     * @param oldStr
     * @return ת�����ƥ���ַ���ѯ��
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
     * �жϵ�ǰ�û��Ƿ���Ȩ�޸Ĵ��ĵ�
     * ����Ը��ĵ���QMPermission.MODIFYȨ�ޣ����� false
     * �����ǰ�û�����AdministratorȨ�ޣ����� true
     * @param intePackInfo �ĵ�ֵ����
     * @return true(��Ȩ��) or false(��Ȩ��)
     * @throws AdoptNoticeException
     */
    public boolean isIntePackPublish(IntePackInfo intePackInfo)
            throws IntePackException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("isIntePackPublish(IntePackInfo) - start");
        }
        //�Ƿ��з������ɰ���Ȩ��
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
     * �����ص����Ͻṹ��
     * @param parentNumber:�����Ϻ�
     * @param partNumber:������
     * @param partVersionID:�����汾
     * @return ��ص����Ͻṹ
     * @throws QMException
     */
    public Collection getStructureLinkMap(String parentNumber,
            String partNumber, String partVersionID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getStructureLinkMap(String) - start");
        }
        //���Ͻṹ�Ĳ�ѯ���
        Collection collection = null;
        try
        {
            //�����µĲ��Ҷ���query
            //�ø����Ϻš���źͰ汾��Ϊ��ѯ�����������Ͻṹ���в�����ص����Ͻṹ
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
            //��ó־û�����
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
     * �������ɰ�
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public void publishTechByIntePack(String intePackID) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("getTechsByProID(String) - start"); //$NON-NLS-1$
		}
		MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
		//��Ҫ�����Ĺ��յļ���
		Vector vecTech = new Vector();
		QMPartIfc partIfc = null;
		//��¼�㲿�������Ĺ��գ�key��value���ǹ��յ�Bsoid
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
			//���ŷָ��������ڷָ�useProcessPartRouteCode��
			String delimiter = "��";
			IntePackInfo intePackInfo = (IntePackInfo) pService
					.refreshInfo(intePackID);
			//����֪ͨ����ò�������
			Vector vecPart = anService.getPartsByProId(intePackInfo.getSource());
			//�����㲿����ѯ��Ӧ�Ĺ���
			for (int i = 0; i < vecPart.size(); i++) {
				partIfc = (QMPartIfc) vecPart.get(i);
				Collection linkCol = cappService
						.findPartUsageQMTechnicsLinkByPart(partIfc.getBsoID());
				Iterator linkIter = linkCol.iterator();
				// ��Ÿ��㲿���Ĺ����µĹ����ţ�key�ǹ��յĲ��Ŵ��룬value�Ǹò����µ����й����ŵ��ַ���
				HashMap temp = new HashMap();
				//���ݹ��պ��㲿���Ĺ����࣬��ȡ������Ϣ���ظ��Ĺ��ˡ�
				while (linkIter.hasNext()) {
					PartUsageQMTechnicsLinkIfc link = (PartUsageQMTechnicsLinkIfc) linkIter
							.next();
					techIfc = (QMFawTechnicsIfc) pService.refreshInfo(link
							.getRightBsoID(), false);
					// techColl = versionService.allVersionsOf(techIfc);
					// techIter = techColl.iterator();
					//�õ��ù��յ����°汾��
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
								noPublishTechType, "��");
						String techType;
						String tempTechtype = tempTechIfc.getTechnicsType()
								.getCode();
						//�Ƿ񷢲��ù��յı�־����װ���ղ���������
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
						//���յĲ��Ŵ���
						String technicShop = "";
						if (info instanceof CodingInfo) {
							technicShop = ((CodingInfo) info).getSearchWord();
						}
						// ��ù���ʹ�õĹ��򼯺�
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
				//�Ը��㲿�����еĹ���������Ϣ����У�顣
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
						// ����������⹤�ղ��Ŵ��룬����м��飬
						// �жϸò��ŵĹ��������еĹ����Ƿ��ڹ��յĹ������У�
						if (flag1) {
							// ���Ȼ�ȡ���㲿���Ĺ��̴��롣
							RouteCodeIBAName routeCodeIBAName = RouteCodeIBAName
									.toRouteCodeIBAName(processRC);
							logger.debug("routeCodeIBAName is "
									+ routeCodeIBAName);
							// ����иó���Ĺ���������������
							if (routeCodeIBAName != null) {
								String display = routeCodeIBAName.getDisplay();
								String processCode = "";
								processCode = matSplitService.getPartIBA(
										partIfc, display);
								logger.debug("processCode is " + processCode);
								// ����иó���Ĺ�������
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
												logString += "�㲿��"
														+ partIfc
																.getPartNumber()
														+ "��\"" + display
														+ "\"�е�\'" + processNum
														+ "\'�ڹ������Ҳ����ù���\n";
											}
										}
									}
								} else {
									logString += "�㲿��"
											+ partIfc.getPartNumber() + "û��\""
											+ processRC + "\"����Ĺ���������Ϣ��\n";
								}

							} else {
								logString += "�㲿��" + partIfc.getPartNumber()
										+ "û��\"" + processRC + "\"����Ĺ���������Ϣ��\n";
							}
						}
						// else {
						// continue;
						// }
					}
				}
			}
			// intePackInfo
			logString = "�������ɰ�\"" + intePackInfo.getName() + "\"ʱ����Ҫ����"
					+ vecTech.size() + "�����ա�\n" + logString;

		} catch (Exception e) {
			logger.error("getTechsByProID(String)", e); //$NON-NLS-1$
			e.printStackTrace();
			throw new QMException(e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getTechsByProID(String) - end" + vecTech.size()); //$NON-NLS-1$
		}
		logger.debug("vecTech.size() is " + vecTech.size());
		// ����㲿��û�й������̻��߹��������еĹ����ڹ������Ҳ�������¼���ļ��С�
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