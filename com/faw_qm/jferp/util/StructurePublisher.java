/**
 * ���ɳ���StructurePublisher.java	1.0              2006-11-6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �Ƚ�����汾��С ������ 2013-09-25
 */
package com.faw_qm.jferp.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.jferp.ejb.service.PromulgateNotifyService;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.FilterPartIfc;
import com.faw_qm.jferp.model.FilterPartInfo;
import com.faw_qm.jferp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;

/**
 * <p>Title: �㲿��������Ϣ�ͽṹ��Ϣ�ķ�������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public final class StructurePublisher extends BaseDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(StructurePublisher.class);

    /**
     * ��ű��Ϊ�޸ĺ��ط����㲿����Ӧ��filterPart��
     */
    private HashMap updatePartMap = new HashMap();

    /**
     * ȱʡ���캯����
     */
    public StructurePublisher()
    {
        super();
    }

    /**
     * ��ǰֻ֪��֪ͨ�飬��Ҫ����֪ͨ����Ϣ�������ɸѡ���������˺��㲿������������ø�������������Ϊ����ɸѡ�����׼����
     * ����������ļ���recordԪ����Ϣ���뵽dataList�С�
     * @throws QMXMLException 
     */
    protected synchronized final void invoke() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        filterParts();
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke() - end"); //$NON-NLS-1$
        }
    }

    /**
     * ִ���㲿����ɸѡ���ܡ��������˺��㲿������������ø�������������Ϊ����ɸѡ�����׼����
     * ɸѡ�����н��㲿������Ϣ�ͽṹ��������Ϣ���õ�QMXMLPart��QMXMLStructure�У���������������������߼���
     * 
     * ɸѡ������¼�������
     * 1.�ȸ���ɸѡ��Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼��ɸѡ�������ٷ�����
     * 2.�㲿���źͰ汾����ͬ������ֻ��ɸѡ������д���һ����¼��״̬�仯���޸�������Ϣ��
     * 
     * �㲿��������򣺲�ѯɸѡ�����������
     * 1.�������ݲ����ڣ����Ϊ����N��
     * 2.�������ݴ��ڣ����汾��a�汾�仯�����Ϊ�޸�U���Խṹ���д���
     *                         b�汾û�б仯��1״̬�仯�����Ϊ�ط�Z��������ṹ��
     *                                       2״̬û�б仯��������
     *                                      
     * �ṹ����������㲿���İ汾�仯ʱʹ�ô˹���
     * 1.����Ϊ����ʱ���ṹ��Ϣ�������Ӽ��������ṹ��ϵ���Ϊ����N��
     * 2.����Ϊ�汾�仯ʱ�������ݵ��Ӽ�����и����ݵİ汾���Ӽ����бȽϣ�
     *   a�µ��������ԭ�����в����ڣ��ṹ���ݱ��Ϊ����N��
     *   b�µ��������ԭ�����д��ڣ�1��ʹ��������ͬ�����Ϊ����O��
     *                            2��ʹ��������ͬ�����Ϊ��������U��
     *   cԭ����������������в����ڣ����Ϊȡ��D��
     * ע�����Խṹ���еݹ鴦��ֻ�����һ���ṹ���ݹ鲿���Ѿ���������ȡʱ��ɡ�
     * @param publishSourseObject ����֪ͨ�顣
     * @throws QMXMLException 
     */
    private final void filterParts() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterParts() - start"); //$NON-NLS-1$
        }
        //ͨ������֪ͨ�����֪ͨ���ȡ�������㲿����
        List partList = new ArrayList();
        logger
                .debug("publishSourseObject==============="
                        + publishSourseObject);
        try
        {
        	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
            if(publishSourseObject instanceof PromulgateNotifyIfc)
            {
            	partList=(List)pnservice.getPartsByProId((PromulgateNotifyIfc)publishSourseObject);
            }
            else if(publishSourseObject instanceof QMChangeRequestIfc)
            {
                //���صĽ�����ǵ�һ��λ��ΪArraylist�����Ľ��,�ڶ���λ�ñ��ǰ�Ľ����
            	ArrayList[] list = (ArrayList[])pnservice.obtainDataForChange(publishSourseObject);
               logger.debug("list.size()==" + list.length);
                if(list.length > 0)
                    partList = (List) list[0];
            }
            logger.debug("publishSourseObject222222==============="
                    + publishSourseObject);
        }
        catch (QMException e)
        {
            //"ͨ������֪ͨ�����֪ͨ���ȡ�������㲿��ʧ�ܣ�"
            logger.error(Messages.getString("Util.22"), e);
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("partList==" + partList);
        }
        //����ɸѡ������¼����������㲿��������ɸѡ���QMXMLPart���ϴ���xmlPartList�С�
        final List xmlPartList = filterByIdentity(partList);
        if(logger.isDebugEnabled())
        {
            logger.debug("ɸѡ������¼��������xmlPartList����" + xmlPartList);
        }
        //�����㲿������������㲿�����������Ϊ�޸ĵ��㲿����Ӧ��filterPart�ŵ�Map�С�
        filterByPartRule(xmlPartList);
        if(logger.isDebugEnabled())
        {
            logger.debug("�㲿����������xmlPartList����" + xmlPartList);
            logger.debug("�㲿����������updatePartMap����" + updatePartMap.size());
        }
        //���ݽṹ������򣬴���ṹ������ɸѡ���QMXMLStructure���ϴ���xmlStructureList�С�
        final List xmlStructureList = filterByStructureRule(xmlPartList);
        if(logger.isDebugEnabled())
        {
            logger.debug("�ṹ���������˺�xmlPartList����" + xmlPartList);
            logger.debug("�ṹ���������˺�xmlStructureList����" + xmlStructureList);
        }
        //�����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
        setDataRecord(xmlPartList, xmlStructureList);
        //�����˺��㲿������������ø�������������Ϊ����ɸѡ�����׼���� 
        BaseDataPublisher.xmlPartList = xmlPartList;
        if(logger.isDebugEnabled())
        {
            logger.debug("filterParts() - end"); //$NON-NLS-1$
        }
    }

    /**
     * ����ɸѡ������¼����������㲿����
     * @param partList ��ɸѡ���㲿�����ϡ�
     * @return ɸѡ���QMXMLPart���ϡ�
     */
    private final List filterByIdentity(final List partList)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
        }
        final List xmlPartList = new ArrayList();
        //���ͨ��Ψһ��ʶɸѡ����㲿����
        final HashMap tempPartMap = new HashMap();
        for (int i = 0; i < partList.size(); i++)
        {
            final QMPartIfc partIfc = (QMPartIfc) partList.get(i);
            //ɸѡ������¼�������1���ȸ���ɸѡ��Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼��ɸѡ�������ٷ�����
            if(!tempPartMap.containsKey(getPartIdentity(partIfc)))
            {
                tempPartMap.put(getPartIdentity(partIfc), partIfc);
                final QMXMLPart xmlPart = new QMXMLPart(partIfc);
                xmlPartList.add(xmlPart);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByIdentity(List) - end"); //$NON-NLS-1$
        }
        return xmlPartList;
    }

    /**
     *  ��ȡɸѡ��Ψһ��ʶ��
     * @param part �㲿����
     * @return String ɸѡ��Ψһ��ʶ��
     */
    private final String getPartIdentity(final QMPartIfc part)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIdentity(QMPartIfc) - start"); //$NON-NLS-1$
        }
        final String returnString = part.getPartNumber() + part.getVersionID()
                + part.getLifeCycleState().getDisplay();
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIdentity(QMPartIfc) - end"); //$NON-NLS-1$
        }
        return returnString;
    }

    /**
     * �����㲿������������㲿����
     * 
     * �㲿��������򣺲�ѯɸѡ�����������
     * 1.�������ݲ����ڣ����Ϊ����N��
     * 2.�������ݴ��ڣ����汾��a�汾�仯�����Ϊ�޸�U���Խṹ���д���
     *                         b�汾û�б仯��1״̬�仯�����Ϊ�ط�Z��������ṹ��
     *                                       2״̬û�б仯��������
     * @param xmlPartList ɸѡ���QMXMLPart���ϡ�
     */
    private final void filterByPartRule(final List xmlPartList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByPartRule(List) - start"); //$NON-NLS-1$
        }
		PartHelper partHelper = new PartHelper();
        final List tempPartList = new ArrayList();
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            FilterPartIfc filterPartIfc = null;
            final QMXMLPart xmlPart = (QMXMLPart) xmlPartList.get(i);
            List filterPartList = new ArrayList();
            try
            {
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
                //�ñ����Ϊ��ѯ�����������Ƿ��з���������FilterPart��
                final QMQuery query = new QMQuery("FilterPart");
                final QueryCondition condition = new QueryCondition(
                        "partNumber", "=", xmlPart.getPartNumber());
                query.addCondition(condition);
                filterPartList = (List)pservice.findValueInfo(query);
            }
            catch (QMException e)
            {
                //"���ұ��Ϊ*��FilterPartʱ����"
                logger.error(Messages.getString("Util.15", new Object[]{xmlPart
                        .getPartNumber()})
                        + e);
                throw new QMXMLException(e);
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("filterPartList==" + filterPartList);
            }
            //�Ƚ���������filterpart�İ汾������µİ汾���ھɵ����ٷ�����
            FilterPartIfc tempFilterPart = null;
            if(filterPartList != null && filterPartList.size() > 0)
            {
                for (int j = 0; j < filterPartList.size(); j++)
                {
                    filterPartIfc = (FilterPartIfc) filterPartList.get(j);
//  modified by tangjinyu 20120830 ���ְ汾AA���ȱȽϰ汾����		    
                    //CCBegin SS1
//                    if(xmlPart.getPartVesionID().length()<filterPartIfc.getVersionValue().length()||
//                    	(xmlPart.getPartVesionID().length()==filterPartIfc.getVersionValue().length()&&
//                    				xmlPart.getPartVesionID().compareTo(
//                            filterPartIfc.getVersionValue()) < 0))
                    int compare = partHelper.compareVersion(xmlPart.getPartVesionID(),filterPartIfc.getVersionValue());
  		          if(compare==2)
                    {//CCEnd SS1
                        tempPartList.add(xmlPart);
                        break;
                    }
                    else
                    {
                        if(tempFilterPart == null)
                        {
                            tempFilterPart = filterPartIfc;
                        }
                        else if(tempFilterPart.getVersionValue().length()<filterPartIfc.getVersionValue().length()||
                        		(tempFilterPart.getVersionValue().length()==filterPartIfc.getVersionValue().length()&&
                        				tempFilterPart.getVersionValue().compareTo(
                                filterPartIfc.getVersionValue()) < 0))
                        {
                            tempFilterPart = filterPartIfc;
                        }
                    }
                }
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("xmlPart.getPartNumber()=="
                        + xmlPart.getPartNumber());
            }
            //�㲿���������1���������ݲ����ڣ����Ϊ����N��
            if(tempFilterPart == null)
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------�㲿���������1");
                }
                if(!tempPartList.contains(xmlPart))
                    xmlPart.setPart_publish_type("N");
            }
            //�㲿���������2���������ݴ��ڣ����汾��
            else if(xmlPartList.contains(xmlPart))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------�㲿���������2");
                }
                //�㲿���������2.b���汾û�б仯��
                if(xmlPart.getPartVesionID().equals(
                        tempFilterPart.getVersionValue()))
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("---------�㲿���������2.b");
                    }
                    //�㲿���������2.b.2��״̬û�б仯����ȥ��
                    if(xmlPart.getPartLifeCycleState().equals(
                            tempFilterPart.getState()))
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------�㲿���������2.b.2");
                        }
                        //��δ�ı���㲿��������ʱ�б��С�
                        tempPartList.add(xmlPart);
                    }
                    //�㲿���������2.b.1��״̬�仯�����Ϊ�ط�Z��������ṹ��
                    else
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------�㲿���������2.b.1");
                        }
                        updatePartMap.put(xmlPart, tempFilterPart);
                        xmlPart.setPart_publish_type("Z");
                    }
                }
                //�㲿���������2.a���汾�仯�����Ϊ�޸�U���Խṹ���д���
                else
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("---------�㲿���������2.a");
                    }
                    updatePartMap.put(xmlPart, tempFilterPart);
                    xmlPart.setPart_publish_type("U");
                }
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("�㲿����������xmlPart.getPart_publish_type()����"
                        + xmlPart.getPart_publish_type());
            }
        }
        //��δ�ı���㲿�����б��г�ȥ��
        xmlPartList.removeAll(tempPartList);
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByPartRule(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ���ݽṹ������򣬴���ṹ������ɸѡ���QMXMLStructure���ϴ���xmlStructureList�С�
     * 
     * ע1��������ʹ�ýṹ�У�ͬһ�����ܴ��ڶ����ͬ��������ͬ�Ӽ��������ڴ���ṹǰ��
     * Ҫ�ȶԸ�������д������Ƚ�ԭ�ṹ�е��Ӽ������ϲ����½ṹ�е��Ӽ�����ԭ����
     * Ȼ��ԭ�ṹ�е��Ӽ������½ṹ�е��Ӽ����бȽϣ�
     * ͷһ���Ӽ�������������ĵڶ�������������������ͬ�Ӽ�����Ϊ�����Ӽ�����
     * 
     * �ṹ����������㲿���İ汾�仯ʱʹ�ô˹���
     * 1.����Ϊ����ʱ���ṹ��Ϣ�������Ӽ��������ṹ��ϵ���Ϊ����N��
     * 2.����Ϊ�汾�仯ʱ�������ݵ��Ӽ�����и����ݵİ汾���Ӽ����бȽϣ�
     *   a�µ��������ԭ�����в����ڣ��ṹ���ݱ��Ϊ����N��
     *   b�µ��������ԭ�����д��ڣ�1��ʹ��������ͬ�����Ϊ����O��
     *                           2��ʹ��������ͬ�����Ϊ��������U��
     *   cԭ����������������в����ڣ����Ϊȡ��D��
     * ע2�����Խṹ���еݹ鴦��ֻ�����һ���ṹ���ݹ鲿���Ѿ���������ȡʱ��ɡ�
     * @param xmlPartList ɸѡ���QMXMLPart���ϡ�
     * @throws QMXMLException
     */
    private final List filterByStructureRule(final List xmlPartList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(List, HashMap) - start"); //$NON-NLS-1$
        }
        //������ͼ����������״̬��������㲿���ṹ�õ������ù淶��
        //        final PartConfigSpecIfc partConfigSpecIfc = populateConfigSpec();
        final List xmlStructureList = new ArrayList();
        //��ʱ������
        QMXMLPart xmlPart;
        PartUsageLinkIfc newUsageLinkIfc;
        PartUsageLinkIfc oldUsageLinkIfc;
        QMXMLStructure xmlStructure;
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            xmlPart = (QMXMLPart) xmlPartList.get(i);
            //�ṹ�������1������Ϊ����ʱ���ṹ��Ϣ�������Ӽ��������ṹ��ϵ���Ϊ����N��
            if(xmlPart.getPart_publish_type().equals("N"))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------�ṹ�������1");
                }
                //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
                //�������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
                final HashMap usesLinkMap = getUsageLinkMap(xmlPart.getPart());
                final Iterator usesLinkIter = usesLinkMap.values().iterator();
                while (usesLinkIter.hasNext())
                {
                    newUsageLinkIfc = (PartUsageLinkIfc) usesLinkIter.next();
                    xmlStructure = new QMXMLStructure(newUsageLinkIfc);
                    xmlStructure.setStructure_publish_type("N");
                    xmlStructureList.add(xmlStructure);
                }
            }
            //�ṹ�������2������Ϊ�汾�仯ʱ�������ݵ��Ӽ�����и����ݵİ汾���Ӽ����бȽϡ�
            else if(xmlPart.getPart_publish_type().equals("U"))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------�ṹ�������2");
                }
                xmlPart = (QMXMLPart) xmlPartList.get(i);
                //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
                //�������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
                final HashMap newUsageLinkMap = getUsageLinkMap(xmlPart
                        .getPart());
                HashMap oldUsageLinkMap = new HashMap();
                //��ȡ���㲿����Ӧ���Ӽ��ṹ������                
                //����filterPart��¼���㲿�����ƺͰ汾�Ż�ȡ����С�汾��
                //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
                //���PartUsageLink����HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
                final FilterPartIfc filterPartIfc = (FilterPartIfc) updatePartMap
                        .get(xmlPart);
                List partMasterList = new ArrayList();
                try
                {
                	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
                    final QMQuery query = new QMQuery("QMPartMaster");
                    final QueryCondition condition2 = new QueryCondition(
                            "partNumber", "=", filterPartIfc.getPartNumber());
                    query.addCondition(condition2);
                    //��ʱ�����ǹ��岿���������
                    query.setChildQuery(false);
                    partMasterList = (List) pservice.findValueInfo(query);
                }
                catch (QueryException e)
                {
                    //"�����ѯ����ʱ����"
                    logger.error(Messages.getString("Util.19") + e);
                    throw new QMXMLException(e);
                }
                catch (QMException e)
                {
                    //"��ȡ���Ϊ*���㲿���Ļ�����Ϣʱ����"
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
                    	VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
                    	versionList = (List) vcservice.allVersionsOf((MasteredIfc)partMasterList.get(0));
                    }
                    catch (QMException e)
                    {
                        //"��ȡ���Ϊ*���㲿����С�汾����ʱ����"
                        logger.error(Messages.getString("Util.21",
                                new Object[]{filterPartIfc.getPartNumber()})
                                + e);
                        throw new QMXMLException(e);
                    }
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("versionList==" + versionList);
                    }
                    final List tempPartList = versionList;
                    for (int j = 0; j < versionList.size(); j++)
                    {
                        //��������ݰ汾��������ݵĴ�汾�Ų�һ�£���ȥ��
                        if(!((QMPartIfc) versionList.get(j)).getVersionID()
                                .equals(filterPartIfc.getVersionValue()))
                        {
                            tempPartList.remove(versionList.get(j));
                        }
                    }
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("tempPartList==" + tempPartList);
                    }
                    //���˷����°汾�ĵ������󣬷������µĵ����汾����
                    QMPartIfc latestPartIfc = filterPartByVersion(tempPartList);
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("latestPartIfc ==" + latestPartIfc);
                    }
                    //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
                    //�������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
                    if(latestPartIfc != null)
                    {
                        oldUsageLinkMap = getUsageLinkMap(latestPartIfc);
                    }
                }
                //��ʽִ�нṹ�������
                //��������������һ���Ӽ���
                if(newUsageLinkMap.size() > 0)
                {
                    //�����ݵĽṹ�������顣
                    Object[] newUsageLinks = newUsageLinkMap.keySet().toArray();
                    //�����ݵĽṹ�������顣
                    final Object[] oldUsageLinks = oldUsageLinkMap.keySet()
                            .toArray();
                    //���¾����ݶ��е��Ӽ�����Ӧ�ľ������еĽṹ������
                    HashMap updateOldUsageLinkMap = new HashMap();
                    //���¾����ݶ��е��Ӽ�����Ӧ���������еĽṹ������
                    final HashMap updateNewUsageLinkMap = new HashMap();
                    for (int j = 0; j < newUsageLinks.length; j++)
                    {
                        newUsageLinkIfc = (PartUsageLinkIfc) newUsageLinks[j];
                        for (int k = 0; k < oldUsageLinks.length; k++)
                        {
                            oldUsageLinkIfc = (PartUsageLinkIfc) oldUsageLinks[k];
                            if(oldUsageLinkIfc.getLeftBsoID().equals(
                                    newUsageLinkIfc.getLeftBsoID()))
                            {
                                updateNewUsageLinkMap.put(newUsageLinkIfc,
                                        newUsageLinkIfc);
                                updateOldUsageLinkMap.put(oldUsageLinkIfc,
                                        oldUsageLinkIfc);
                                //���ܵļ����г�ȥ��ʣ�µı�ʶΪN��
                                newUsageLinkMap.remove(newUsageLinkIfc);
                                //���ܵļ����г�ȥ��ʣ�µı�ʶΪD��
                                oldUsageLinkMap.remove(oldUsageLinkIfc);
                            }
                        }
                    }
                    //�ϲ��ṹ��ʹ���Ӽ�����������Ϊ�Ӽ�����ϢBsoID��ֵΪPartUsageLinkIfc��
                    updateOldUsageLinkMap = uniteQuantity(updateOldUsageLinkMap);
                    newUsageLinks = updateNewUsageLinkMap.keySet().toArray();
                    for (int j = 0; j < newUsageLinks.length; j++)
                    {
                        newUsageLinkIfc = (PartUsageLinkIfc) newUsageLinks[j];
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("newUsageLinkIfc.getQuantity()=="
                                    + newUsageLinkIfc.getQuantity());
                        }
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------�ṹ�������2.b");
                        }
                        oldUsageLinkIfc = (PartUsageLinkIfc) updateOldUsageLinkMap
                                .get(newUsageLinkIfc.getLeftBsoID());
                        //�ṹ�������2.b���µ��������ԭ�����д��ڡ�
                        if(oldUsageLinkIfc != null)
                        {
                            //�ṹ�������2.b.1����ʹ��������ͬ�����Ϊ����O��
                            if(logger.isDebugEnabled())
                            {
                                logger.debug("oldUsageLinkIfc.getQuantity()=="
                                        + oldUsageLinkIfc.getQuantity());
                            }
                            if(newUsageLinkIfc.getQuantity() == oldUsageLinkIfc
                                    .getQuantity())
                            {
                                if(logger.isDebugEnabled())
                                {
                                    logger.debug("---------�ṹ�������2.b.1");
                                }
                                xmlStructure = new QMXMLStructure(
                                        newUsageLinkIfc);
                                xmlStructure.setStructure_publish_type("O");
                                xmlStructureList.add(xmlStructure);
                            }
                            //�ṹ�������2.b.2����ʹ��������ͬ�����Ϊ��������U��
                            else
                            {
                                if(logger.isDebugEnabled())
                                {
                                    logger.debug("---------�ṹ�������2.b.2");
                                }
                                xmlStructure = new QMXMLStructure(
                                        newUsageLinkIfc);
                                xmlStructure.setStructure_publish_type("U");
                                xmlStructureList.add(xmlStructure);
                            }
                            //ȥ��ԭ�������¾ɶ��е����ݣ�ʣ��ԭ�����е�������û�е����ݡ���֪��ν������
                            updateOldUsageLinkMap.remove(oldUsageLinkIfc
                                    .getLeftBsoID());
                        }
                        //�ṹ�������2.a���µ��������ԭ�����в����ڣ��ṹ���ݱ��Ϊ����N��
                        else
                        {
                            if(logger.isDebugEnabled())
                            {
                                logger.debug("---------�ṹ�������2.a");
                            }
                            xmlStructure = new QMXMLStructure(newUsageLinkIfc);
                            xmlStructure.setStructure_publish_type("N");
                            xmlStructureList.add(xmlStructure);
                        }
                    }
                    //�ṹ�������2.c��ԭ����������������в����ڣ����Ϊȡ��D��
                    Iterator iter = oldUsageLinkMap.values().iterator();
                    while (iter.hasNext())
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------�ṹ�������2.c");
                        }
                        oldUsageLinkIfc = (PartUsageLinkIfc) iter.next();
                        xmlStructure = new QMXMLStructure(oldUsageLinkIfc);
                        xmlStructure.setStructure_publish_type("D");
                        xmlStructureList.add(xmlStructure);
                    }
                    iter = newUsageLinkMap.values().iterator();
                    while (iter.hasNext())
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------�ṹ�������2.a");
                        }
                        newUsageLinkIfc = (PartUsageLinkIfc) iter.next();
                        xmlStructure = new QMXMLStructure(newUsageLinkIfc);
                        xmlStructure.setStructure_publish_type("N");
                        xmlStructureList.add(xmlStructure);
                    }
                }
                //��������û���Ӽ���
                else
                {
                    final Iterator iter = oldUsageLinkMap.values().iterator();
                    while (iter.hasNext())
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------�ṹ�������2.c");
                        }
                        oldUsageLinkIfc = (PartUsageLinkIfc) iter.next();
                        xmlStructure = new QMXMLStructure(oldUsageLinkIfc);
                        xmlStructure.setStructure_publish_type("D");
                        xmlStructureList.add(xmlStructure);
                    }
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(List, HashMap) - end"); //$NON-NLS-1$
        }
        return xmlStructureList;
    }

    /**
     * �ϲ��ṹ��ʹ���Ӽ���������
     * @param usageLinkMap ���ϲ�������ʹ�ýṹMap��ֵΪPartUsageLinkIfc��
     * @return HashMap �ϲ����ʹ�ýṹMap����Ϊ�Ӽ�����ϢBsoID��ֵΪPartUsageLinkIfc��
     */
    private final HashMap uniteQuantity(HashMap usageLinkMap)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - start"); //$NON-NLS-1$
        }
        //�ϲ����ʹ�ýṹ�������ϡ�
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
            //��ʶ�ڼ���usageLinkList���Ƿ���ڵ�ǰ��ѭ��������㲿��,��ʼ�����,��Ϊ������:
            flag = false;
            //�����еĺϲ���ϵļ��Ͻ���ѭ��:
            for (int i = 0; i < usageLinkList.size(); i++)
            {
                usageLinkIfc2 = (PartUsageLinkIfc) usageLinkList.get(i);
                float oldQuantity = usageLinkIfc2.getQuantity();
                String oldLeftBsoID = usageLinkIfc2.getLeftBsoID();
                //���ʹ�õ���ͬһ���㲿���Ļ�,�ϲ�����,
                if(leftBsoID.equals(oldLeftBsoID))
                {
                    usageLinkIfc2.setQuantity(quantity + oldQuantity);
                    //�ҵ�������㲿��:
                    flag = true;
                    usageLinkList.set(i, usageLinkIfc2);
                    break;
                }
            }
            if(!flag)
                usageLinkList.add(usageLinkIfc);
        }
        usageLinkMap = new HashMap();
        //���ϲ���Ľ�����ϼӵ�Map�У���Ϊ�Ӽ�����ϢBsoID��ֵΪPartUsageLinkIfc��
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
     * ���˷����°汾�ĵ������󣬷������µĵ����汾����
     * @param tempPartList �㲿�����ϡ�
     * ������汾��ֹһ��������ݰ汾�űȽϷ������汾�ŵİ汾��
     * ���������ǲ�ֹһ��������ݵ����ŷ������İ汾��
     * �����������ͬ�������ʱ����������İ汾��
     * @return ���°汾�㲿����
     * @throws QMXMLException 
     */
    private final QMPartIfc filterPartByVersion(final List tempPartList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterPartByVersion(List) - start"); //$NON-NLS-1$
        }
        final HashMap hashMap = new HashMap();
        PartHelper partHelper = new PartHelper();
        String partNumber = "";
        for (int i = 0; i < tempPartList.size(); i++)
        {
            final QMPartIfc partIfc = (QMPartIfc) tempPartList.get(i);
            partNumber = partIfc.getPartNumber();
            final IteratedIfc iteratedIfc = (IteratedIfc) hashMap
                    .get(partNumber);
            boolean flag4 = false;
            if(iteratedIfc == null)
            {
                hashMap.put(partNumber, partIfc);
            }//endif ���partIfcû��ʹ�ã����߱���ǰ�û�ʹ�ã�����iteratedIfcΪ�ա�
            //������������������������partIfc��Versioned��ʵ����
            //�Ƚ�iteratedIfc��partIfc�İ汾���кš�
            else
            {
                flag4 = false;
                if(partIfc instanceof VersionedIfc)
                {
                    final String objVerID = ((VersionedIfc) partIfc)
                            .getVersionID();
                    final String iterateVerID = ((VersionedIfc) iteratedIfc)
                            .getVersionID();
//                  modified by tangjinyu 20120830 ���ְ汾AA���ȱȽϰ汾����	  
                    //CCBegin SS1
//                    if(objVerID.length()>iterateVerID.length()||
//                    		(objVerID.length()==iterateVerID.length()&&
//                    				objVerID.compareTo(iterateVerID) > 0))
                    int compare = partHelper.compareVersion(objVerID,iterateVerID);
  		          if(compare==1)
                    {//CCEnd SS1
                        hashMap.put(partNumber, partIfc);
                    }//endif partIfc�İ汾����iteratedIfc�İ汾��
                    else if(objVerID.equals(iterateVerID))
                        flag4 = true;
                }
                if(flag4 || (!(partIfc instanceof VersionedIfc)))
                {
                    //���flag4Ϊ�棨iteratedIfc��partIfc���к���ȣ���
                    //����partIfc����Versioned��ʵ������Ƚ����ǵĵ����š�
                    flag4 = false;
                    final String objID = ((IteratedIfc) partIfc)
                            .getIterationID();
                    final String iterateID = iteratedIfc.getIterationID();
                    if(Integer.parseInt(objID) > Integer.parseInt(iterateID))
                    {
                        hashMap.put(partNumber, partIfc);
                    }
                    else if(Integer.parseInt(objID) == Integer
                            .parseInt(iterateID))
                        flag4 = true;
                }
                if(flag4)
                {
                    //���iteratedIfc��partIfc�ĵ�������ȣ���Ƚ����ǵ�ʱ�����
                    flag4 = false;
                    final Timestamp objTime = ((BaseValueIfc) partIfc)
                            .getCreateTime();
                    final Timestamp iterateTime = iteratedIfc.getCreateTime();
                    if(objTime.after(iterateTime))
                    {
                        hashMap.put(partNumber, partIfc);
                    }
                    else if(objTime.equals(iterateTime))
                        flag4 = true;
                }
                if(flag4)
                {
                    try
                    {
                    	VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
                    	final IteratedIfc objIfc = (IteratedIfc)vcservice.predecessorOf((IteratedIfc)partIfc);
                        if(objIfc != null
                                && PersistHelper.isEquivalent(objIfc,
                                        iteratedIfc))
                        {
                            hashMap.put(partNumber, partIfc);
                        }//endif ���iteratedIfc��partIfc��ʱ�����Ȳ������ǵ�ǰ��Ҳ��ͬһ������
                    }
                    catch (Exception e)
                    {
                        //"��ȡ���Ϊ*���㲿����ǰһ�汾����"
                        logger.error(Messages.getString("Util.23",
                                new Object[]{partNumber}), e);
                        throw new QMXMLException(e);
                    }
                }
            }
        }
        final QMPartIfc returnQMPartIfc = (QMPartIfc) hashMap.get(partNumber);
        if(logger.isDebugEnabled())
        {
            logger.debug("filterPartByVersion(List) - end"); //$NON-NLS-1$
        }
        return returnQMPartIfc;
    }

    /**
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
     * @param partIfc �㲿����
     * @throws QMXMLException
     */
    private final HashMap getUsageLinkMap(final QMPartIfc partIfc)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getUsageLinkMap(PartConfigSpecIfc, QMPartIfc) - start"); //$NON-NLS-1$
        }
        final HashMap usageLinkMap = new HashMap();
        //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
        List usesPartList = new ArrayList();
        try
        {
        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
        	usesPartList = (List)spservice.getUsesPartMasters(partIfc);
        }
        catch (QMException e)
        {
            //"��ȡ��Ϊ*���㲿���ṹʱ����"
            logger.error(Messages.getString("Util.17", new Object[]{partIfc
                    .getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        //��Ҫ�������ӹ����ŵ�HashMap�С�
        Iterator iter = usesPartList.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            usageLinkMap.put(obj, obj);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(PartConfigSpecIfc, QMPartIfc) - end"); //$NON-NLS-1$
        }
        return usageLinkMap;
    }

    /**
     * ����ɸѡ�����
     * @throws QMXMLException 
     */
    protected final void saveFilterPart() throws QMXMLException
    {
    	try
    	{
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - start"); //$NON-NLS-1$
        }
        if(logger.isInfoEnabled())
        {
            //"��ʼ����FilterPart���ݣ�"
            logger.info(Messages.getString("Util.28")); //$NON-NLS-1$
        }
        PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        //���úõ�Ҫ�����ɸѡ����ļ��ϡ�
        List filterPartList = new ArrayList();
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            final QMXMLPart xmlPart = (QMXMLPart) xmlPartList.get(i);
            FilterPartIfc filterPartIfc = (FilterPartIfc) updatePartMap
                    .get(xmlPart);
            if(filterPartIfc != null
                    && xmlPart.getPart_publish_type().equals("Z"))
            {
                //����״̬��
                filterPartIfc.setState(xmlPart.getPartLifeCycleState());
                filterPartList.add(filterPartIfc);
                continue;
            }
            filterPartIfc = new FilterPartInfo();
            if(publishSourseObject instanceof PromulgateNotifyIfc)
            {
                filterPartIfc
                        .setNoticeNumber(((PromulgateNotifyIfc) publishSourseObject)
                                .getPromulgateNotifyNumber());
                filterPartIfc.setNoticeType("����");
            }
            else if(publishSourseObject instanceof QMChangeRequestIfc)
            {
                filterPartIfc
                        .setNoticeNumber(((QMChangeRequestIfc) publishSourseObject)
                                .getNumber());
                filterPartIfc.setNoticeType("���");
            }
            filterPartIfc.setPartNumber(xmlPart.getPartNumber());
            filterPartIfc.setState(xmlPart.getPartLifeCycleState());
            filterPartIfc.setVersionValue(xmlPart.getPartVesionID());
            filterPartList.add(filterPartIfc);
            if(logger.isDebugEnabled())
            {
                logger.debug(filterPartIfc.getNoticeNumber());
                logger.debug(filterPartIfc.getNoticeType());
                logger.debug(filterPartIfc.getPartNumber());
                logger.debug(filterPartIfc.getState());
                logger.debug(filterPartIfc.getVersionValue());
            }
        }
        for (int i = 0; i < filterPartList.size(); i++)
        {
            try
            {
            	pservice.saveValueInfo((BaseValueIfc)filterPartList.get(i));
            }
            catch (QMException e)
            {
                //"�������*ʱ����"
                logger.error(Messages.getString("Util.16",
                        new Object[]{((FilterPartIfc) filterPartList.get(i))
                                .getIdentity()})
                        + e);
                throw new QMXMLException(e);
            }
        }
        //"������*��FilterPart���ݡ�"
        logger
                .info(Messages
                        .getString(
                                "Util.44", new Object[]{Integer.toString(filterPartList.size())})); //$NON-NLS-1$
        if(logger.isInfoEnabled())
        {
            //"��ɱ���FilterPart���ݣ�"
            logger.info(Messages.getString("Util.29")); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - end"); //$NON-NLS-1$
        }
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		throw new QMXMLException(ex);
    	}
    }

    /**
     * �����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
     * @param xmlPartList ���˺�xmlPartList��
     * @param xmlStructureList ���˺�xmlStructureList��
     * @throws QMXMLException 
     */
    private final void setDataRecord(final List xmlPartList,
            final List xmlStructureList) throws QMXMLException
    {
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
            if(data.getName().equals("part"))
            {
                final List partRecordList = new ArrayList();
                for (int i = 0; i < xmlPartList.size(); i++)
                {
                    final QMXMLPart xmlPart = (QMXMLPart) xmlPartList.get(i);
                    xmlPart.setPropertyList(data.getPropertyList());
                    partRecordList.add(xmlPart.getRecord());
                }
                data.setRecordList(partRecordList);
                setDescNote(Messages.getString("Util.34",
                        new Object[]{Integer.toString(partRecordList.size()),
                                data.getName()}));
                continue;
            }
            else if(data.getName().equals("structure"))
            {
                final List structureRecordList = new ArrayList();
                for (int i = 0; i < xmlStructureList.size(); i++)
                {
                    final QMXMLStructure xmlStructure = (QMXMLStructure) xmlStructureList
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
    }

    public static void main(String[] args)
    {
    }
}
