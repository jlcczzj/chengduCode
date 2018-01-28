/**
  * ���ɳ��� StandardPartServiceEJB.java    1.0    2003/02/19
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * CR1 2009/04/02 л�� ԭ��:ֻ��Ҫֵ�����ͼ����Ϣ����
  *                     ����:��д����
  *                     ��ע:���v3r11-�����ڲ�Ʒ�����Ż�
  *                      
  *CR2 2008/03/27 ������ ԭ���Ż����ͳ�������嵥
  *                       ������ͨ����ӻ��棬������ʱ����ȼ�����������嵥����Ӧʱ����ڴ�
  *                       ���������ͳ�������嵥
  *CR3 2008/03/20 ������ ԭ���Ż�����ּ������嵥
  *                       ������ͨ����ӻ��棬������ʱ����ȼ�����������嵥����Ӧʱ����ڴ�
  *                       ����������ּ������嵥
  *CR4 2009/04/27 л�� ԭ��:�߼�����
  *                     ����:��д������߼�
  *                     ��ע:���v3r11-�ּ������嵥�����Ż�-л��
  *CR5 2009/05/20  ���� �μ�ȱ��ID��2141��09CTAuditing��
  *CR51 2009/06/18 л�� ԭ�򣺷ּ������嵥�޷�����滻�����ṹ�滻��������������Ϣ��TD-2154
  *                    ��������������ֲ��������Ż����룬�����û�ж��滻�����ṹ�滻������������������߼����������ܿ��ǣ���Ʒ��ֻ��ʾ�ַ��������������ԣ���������������Ҫʵʩ�׶ο��������ӡ�
  *CR6 2009/06/19 ��� ԭ��TD2447 ���㲿��ֻ��һ������������ʱ�򣬶��������ڲ鿴���㲿���ġ������ڲ�Ʒ����Ϣ��ʱ����ʾ
  *                    ��ע����ʱ����setUsageList�����п�����ڼ�����ֻ��һ��Ԫ�ص������ȥ�����Ǹ�Ԫ�أ���Ϊ��������������㲿��û�и�����ʱ�򷵻��Լ������������ڲ�Ʒ��������ʾ�Լ�����
  *                    ��������setUsageList�����м�����ֻ��һ��Ԫ�ص�������ж�bsoid�Ƿ�ͱ�����㲿��bsoidһ�£����һ��ɾ�������е�Ԫ��
  * CR7 20090619 ��ǿ �޸�ԭ�򣺽��нṹ�ȽϺ󣬲�Ʒ��Ϣ���������ù淶��ʾΪ�ṹ�Ƚ�Ŀ��������ù淶����TD-2190�� 
  *                   ������������нṹ�ȽϺ󣬲�Ʒ��Ϣ��������ȷ��ʾ�����ù淶��                    
  * CCBegin by liunan 2011-04-20 �򲹶�v4r3_p032
  *CR9 2011/03/24 ��� ԭ��TD3687 �鿴�㲿���ı����ڲ�����ʾ����ȷ(�˴����޸ı����ڲ�Ʒ)
  *                    ��ע�������ڵĲ��Ҹ����ķ�ʽ��鵽����ĸ���
  *                    ���������Ҹ����Ժ����÷������ù淶�����¸������²���һ���Ӽ���������ҵĹ��������ھ�������ȷ���������������֤�������Ǹ��������Ѿ����á�                                                 
  * CCEnd by liunan 2011-04-20 
  * CCBegin by liunan 2012-03-21
  * CR10 2012/03/15 ���� �޸�ԭ�� �����ڲ����ͱ����ڲ�Ʒ��Ϊ��jdbc��ѯ�� 
  * CCEnd by liunan 2012-03-21
  * SS1 ���������Ż����롣 liunan 2013-7-15
  * CR11 2012/07/15 ���� �޸�ԭ��td2586�������ڲ�Ʒ����ϵͳ������
  * SS2 2013-1-21 ��Ʒ��Ϣ����������������嵥�����������������erp���Ա����ܣ��ò��ִ���Ϊ���������á�����Դ�����Ѿ���ʧ��
  * SS3 ͨ��masterid��ȡ���°汾���㲿���� liunan 2013-11-25
  * SS4 ������ķ��ӹ�˾�����嵥����EXCEL����·�ߵ�λ liuyang 2013-12-2
  * SS5 ���Ӳ�Ʒ��Ϣ�������������λ����������� pante 2015-01-146
  * SS6 A004-2015-3156 �鿴�����ڲ���ʱ��Ȩ������û�и����㲿��״̬���ˣ�����Ȩ���жϲ�׼�� liunan 2015-6-26
  * SS7 A004-2016-3286 ����һ�����嵥 liunan 2016-1-20
  * SS8  �ɶ�������ӻ���Ӽ� guoxiaoliang 2016-7-28
  * SS9 A004-2017-3491 �㲿�����ܱ�ɾ������ʾ�й�����ʵ�ʱ�ĳ��������ʷ�汾ʹ�ã��������ڲ����������� liunan 2017-5-16
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
 * �㲿����׼����EJBʵ���ࡣ
 * @author ���ȳ�
 * @version 1.0
 */
//����(1)20080811 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��

public class StandardPartServiceEJB extends BaseServiceImp
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    private String RESOURCE = "com.faw_qm.part.util.PartResource";
    PartConfigSpecAssistant pcon=null;
    //CCBegin by liunan 2009-01-06 �򲹶�v3r11_p021_20090105
    private HashMap configSpecCach=new HashMap();
    //CCEnd by liunan 2009-01-06
    HashMap masterMap=new HashMap();//CR2
    String noHasSubParts = QMMessage.getLocalizedMessage(RESOURCE, "false", null);//CR2
    String isHasSubParts = QMMessage.getLocalizedMessage(RESOURCE, "true", null);//CR2
    /**
     * ���캯����
     */
    public StandardPartServiceEJB()
    {
        super();
    }

    /**
     * ͨ���㲿�������ֺͺ�������㲿��������Ϣ�����صļ�����ֻ��һ��QMPartMasterIfc����
     * @param partName :String �㲿�������ơ�
     * @param partNumber :String �㲿���ĺ��롣
     * @return collection:���ҵ���QMPartMasterIfc�㲿������Ϣ����ļ��ϣ�ֻ��һ��Ԫ�ء�
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
     * ͨ��һ���㲿������Ϣ�ҵ���Ӧ���㲿��������С�汾(������ͬ��֧)ֵ����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection :Collection���ж�Ӧ��partMasterIfc���㲿������ļ��ϡ�
     * @exception QMException �־û��쳣�����߰汾�����쳣��
     */
    public Collection findPart(QMPartMasterIfc partMasterIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPart begin ....");
        VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPart end....return is Collection ");
        return vcService.allIterationsOf(partMasterIfc);
    }

    /**
     * ɾ���ο��ĵ����㲿���Ĺ�����ϵ��
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
     * ����ο��ĵ����㲿���Ĺ�����ϵ��
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
     * �����㲿�����㲿������Ϣ�Ĺ�����ϵ��
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
     * ɾ���㲿�����㲿������Ϣ�Ĺ�����ϵ��
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
     * ���������ĵ����㲿���Ĺ�����ϵ��
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
     * ɾ�������ĵ����㲿���Ĺ�����ϵ��
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
     * �޶��㲿������������һ���µĴ�汾��
     * @param partIfc :QMPartIfc �޶�ǰ���㲿����ֵ���󣬱���Ϊ���µĵ����汾��
     * @return partIfc :QMPartIfc �޶�����㲿����ֵ����
     * @throws QMException
     */
    public QMPartIfc revisePart(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "revisePart begin ....");
        //�ж�part�Ƿ������޶���
        boolean flag = VersionControlHelper.isReviseAllowed(partIfc, true);
        VersionControlService vcservice = (VersionControlService)EJBServiceHelper.
            getService("VersionControlService");
        PartDebug.trace(this, PartDebug.PART_SERVICE, "revisePart end....return is QMPartIfc ");
        return (QMPartIfc)vcservice.newVersion(partIfc);
    }

    /**
     * �����㲿��ֵ�����ø��㲿�����������вο��ĵ�(DocIfc)���°汾��ֵ����ļ��ϡ�
     * �ȸ����㲿����ȡ���еĲο��ĵ�����Ϣ(DocMasterIfc)�ļ���,�ٶ�DocMasterIfc����
     * ���й��ˣ��ҳ�ÿ��DocMasterIfc����Ӧ�����°汾DocIfc����󷵻�DocIfc�ļ��ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @return Vector �㲿���ο��ĵ�(DocIfc)ֵ����ļ��ϡ�
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
            //��Ҫ��vector�е����е�DocMasterIfc���˳�����С�汾::
            ConfigService cService = (ConfigService)EJBServiceHelper.getService("ConfigService");
            Collection collection1 = cService.filteredIterationsOf(vector, new LatestConfigSpec());
            Iterator iterator1 = collection1.iterator();
            while(iterator1.hasNext())
            {
                //��iterator1�е����кϸ��DocIfc�ŵ����������::
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
     * ����ָ�����㲿����ֵ���󷵻ظ��㲿�������ĵ���ֵ���󼯺ϡ������־flagΪtrue,
     * ���صĽ��Vector����DocIfc�ļ��ϣ����flagΪfalse�����ص�Vector��
     * PartDescribeLinkIfc�ļ��ϡ�
     * @param partIfc :QMPartIfc�㲿����ֵ����
     * @param flag : boolean
     * @return vector:Vector �㲿�������ĵ�(DocIfc)���ϣ�����������������ϵֵ����ļ��ϡ�
     * @throws QMException
     */
    public Vector getDescribedByDocs(QMPartIfc partIfc, boolean flag) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getDescribedByDocs begin ....");
        //��������������׳�PartException�쳣"��������Ϊ�ա�"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        StructService sservice = (StructService)EJBServiceHelper.getService("StructService");
        //���part�����ĵ���ֵ����ļ��ϣ�����collection1
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
     * �������ĵ���ֵ�����ú͸òο��ĵ������ĵ��㲿����ֵ����ļ��ϡ�
     * @param docMasterIfc :DocMasterIfc �ο����ĵ���ֵ����
     * @return vector:Vector ���ĵ��ο����㲿����ֵ����ļ��ϡ�
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
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection ��partIfc������PartUsageLinkIfc�Ķ��󼯺ϡ�
     * @throws QMException
     */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters begin ....");
        //����������������׳�PartException"��������Ϊ��"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters end....return is Colletion ");
        return pservice.navigateValueInfo(partIfc,"usedBy","PartUsageLink",false);
    }

    /**
     * ����ָ�����㲿�������¼��㲿�������°汾��ֵ����ļ��ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection partIfcʹ�õ��¼��Ӽ������°汾��ֵ���󼯺ϡ�
     * @throws QMException
     */
    public Collection getSubParts(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubParts begin ....");
        //��������������׳�PartEception�쳣"��������Ϊ��"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //CCBegin by leixiao 2008-10-06 ԭ�򣺽������,��ȡ�Ӽ�ʱ GenericPart �Ҳ����Ӽ�
        Collection collection=null;
        if(partIfc.getBsoName().equals("GenericPart"))
         collection = pservice.navigateValueInfo(partIfc,"usedBy","GenericPartUsageLink");
        else if(partIfc.getBsoName().equals("QMPart"))
         collection = pservice.navigateValueInfo(partIfc,"usedBy","PartUsageLink");
        //CCEnd by leixiao 2008-10-06 ԭ�򣺽������
        Object[] tempArray = (Object[])collection.toArray();
        VersionControlService vcservice = (VersionControlService)EJBServiceHelper.getService
            ("VersionControlService");
        Vector result = new Vector();
        Vector tempResult = new Vector();
        for (int i=0; i<tempArray.length; i++)
        {
        	  //CCBegin by leixiao 2008-10-06 ԭ�򣺽������
            tempResult = new Vector(vcservice.allVersionsOf((MasteredIfc)tempArray[i]));
            //CCEnd by leixiao 2008-10-06 ԭ�򣺽������
            if (tempResult != null && (tempResult.iterator()).hasNext())
                result.addElement((tempResult.iterator()).next());
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubParts end....return is Collection ");
        return result;
    }


    /**
     * ����ָ���㲿�����������¼��㲿�������°汾��ֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @param result Collection  ��������
     * @param indexID Collection ����ID��
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
     * ����ָ���㲿�����������¼��㲿�������°汾��ֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection partIfcʹ�õ��¼��Ӽ������°汾��ֵ���󼯺ϡ�
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
     * ����ָ����QMPartMasterIfc����
     * ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾
     * ��QMPartIfc����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ�
     * @throws QMException
     */
    public Collection getUsedByParts(QMPartMasterIfc partMasterIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts begin ....");
        //CCBegin by liunan 2012-03-21
        /*QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        //�����°汾��ѯ����������cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //���ݴ������ֵȷ�����λ�ã���������Ӳ�ѯ����,����0��ʾ�ǵ�һ����:�ǶԵ�һ������Ӳ�ѯ����
        query.addCondition(0,condition1);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts end....return is Collection");
        //����һ��ֵ����partMasterIfc�ͱ�ֵ�����ڹ������еĹ�����ɫ��"uses", �����������һ�ߵ�bso����
        //query - ��ѯ�Ĺ�������
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
     * ����ָ����QMPartMasterIfc����
     * ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾
     * ��QMPartIfc����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ�
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
        //�����°汾��ѯ����������cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //���ݴ������ֵȷ�����λ�ã���������Ӳ�ѯ����,����0��ʾ�ǵ�һ����:�ǶԵ�һ������Ӳ�ѯ����
        query.addCondition(0,condition1);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsedByParts end....return is Collection");
        //����һ��ֵ����partMasterIfc�ͱ�ֵ�����ڹ������еĹ�����ɫ��"uses", �����������һ�ߵ�bso����
        //query - ��ѯ�Ĺ�������
        return pservice.navigateValueInfo(partMasterIfc, "uses", query);
    }
    /**
     * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
     * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
     * @param collection :Collection ��PartUsageLinkIfc����ļ��ϡ�
     * @param configSpecIfc :PartConfigSpecIfc �㲿����ɸѡ������
     * @return collection:Collection ����ÿ��Ԫ��Ϊһ������:
     * ����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc����
     * ���û��QMPartIfc����Ϊ������QMPartMasterIfc����
     * @throws QMException
     */
    public Collection getUsesPartsFromLinks(Collection collection,
        PartConfigSpecIfc configSpecIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartsFromLinks begin ....");
        //���collection�й��������ɫΪ"uses"��Masteredֵ����ļ��ϡ�
        //����masterCollection
       // Collection masterCollection = ConfigSpecHelper.mastersOf(collection, "uses");
        Collection masterCollection =mastersOf(collection);
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
        //����masterCollection��mastered�������ķ������ù淶configSpec��
        //���е����汾�Ķ���ļ��ϣ�����ֵΪiteratedCollection
        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, new PartConfigSpecAssistant(configSpecIfc));
        //���ڸ�����master����Щmaster�������iteration����ļ��ϣ�
        //�������е�iteration�����������iteration������û���κ�һ��iteration����
        //��֮��ƥ���master���󡣷��ض��󼯺�ΪallCollection
        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection,iteratedCollection);
        //����ָ���Ĺ������ϣ�master��iteration֮�䣩�Ͷ�Ӧmaster�����iteration���ϣ�
        //����ÿ�����������ӵ�iteration�����ؽ����������ÿ����������0λ�ô�Ź�������
        //��1λ�ô��iteration�������û�ж�Ӧ��iteration����
        //���Ź����������ӵ�master���� ��
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartsFromLinks end....return is Collection ");
        return ConfigSpecHelper.buildConfigResultFromLinks(collection,"uses",allCollection);
    }
    
    /**
     * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
     * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
     * @param collection ��PartUsageLinkIfc����ļ��ϡ�
     * @return ÿ��Ԫ��Ϊһ������.
     * ����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc�������û��QMPartIfc����Ϊ������QMPartMasterIfc����
     * @throws QMException
     */
    public Collection getUsesPartsFromLinks(Collection collection) throws QMException//Begin CR4����д������߼���
    {
        Collection masterCollection = mastersOf(collection);
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
        if(pcon == null)
            pcon = new PartConfigSpecAssistant(findPartConfigSpecIfc());
        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, pcon);
        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection, iteratedCollection);
        return ConfigSpecHelper.buildConfigResultFromLinks(collection, "uses", allCollection);
    }//End CR4����д������߼�
    
    /**
     * �����㲿����
     * @param partIfc :QMPartIfc Ҫ������㲿����ֵ����
     * @return partIfc:QMPartIfc �������㲿����ֵ����
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
     * ɾ��ָ�����㲿�����������������ʹ�øò�����
     * ���쳣"���㲿���Ѿ�����������ʹ�ã�����ɾ����"��
     * @param partIfc :QMPartIfc Ҫɾ�����㲿����ֵ����
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
     * �ж��㲿��partIfc2�Ƿ����㲿��partIfc1�����Ȳ�������partIfc1����
     * @param partIfc1 :QMPartIfc ָ�����㲿����ֵ����
     * @param partIfc2 :QMPartIfc ��������㲿����ֵ����
     * @return flag:boolean��
     * flag==true:�㲿��part2���㲿��part1�����Ȳ�������part1����
     * flag==false:�㲿��part2�����㲿��part1�����Ȳ�����Ҳ����part1����
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
        //���partIfc1û�и��׽ڵ㣬˵��partIfc2��Զ��������partIfc1�ĸ��׽ڵ㣬���Է���
        //��Զ����false
        if(temp == null || temp.size() == 0)
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "isParentPart end....return is false");
            return false;
        }
        for(int i=0; i<temp.size(); i++)
        {
            String bsoID1 = partMasterIfc2.getBsoID();
            String bsoID2 = ((QMPartMasterIfc)temp.elementAt(i)).getBsoID();
            //���partMasterIfc2��BsoID��partIfc1��ĳ�����׽ڵ��BsoID��ȣ�����true;
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
     * ���ָ���㲿�������и�����������Ϣֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @return vector:Vector ָ���㲿�������и�����(ֱ��������)������Ϣֵ����ļ��ϡ�
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
     * ���ָ���㲿�������и�����������Ϣֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @return vector:Vector ָ���㲿�������и�����(ֱ��������)������Ϣֵ����ļ��ϡ�
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
     * ��ȡ����ֱ��ʹ�õ�ǰ������㲿����
     * �����ݵ�ǰ�Ĵ�������QMPartIfc�ҵ���Ӧ��PartMaster��
     * �ٵ���getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * �����ҵ����е�ʹ�ø�����ļ��ϡ�
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
        //collection��Ӧ����QMPartIfc�ļ���
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof QMPartIfc)
                {
                    //�ڲ鿴�����ڽ��棬���һ������ʹ��һ���Ӽ���Σ���ֻ���г�һ����¼������ÿ�ζ��г�
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
     * ��ȡ����ֱ��ʹ�õ�ǰ������㲿����
     * �����ݵ�ǰ�Ĵ�������QMPartIfc�ҵ���Ӧ��PartMaster��
     * �ٵ���getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * �����ҵ����е�ʹ�ø�����ļ��ϡ�
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
        //collection��Ӧ����QMPartIfc�ļ���
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof QMPartIfc)
                {
                    //�ڲ鿴�����ڽ��棬���һ������ʹ��һ���Ӽ���Σ���ֻ���г�һ����¼������ÿ�ζ��г�
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
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
     * ������������bianli()����ʵ�ֵݹ顣
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID���ǣ��񣩿ɷ֣�"true","false"������š����ơ�������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID���ǣ��񣩿ɷ֡������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
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

        //��¼�����ͱ�������������ڵ�λ�á�
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
        //�����vector�е�Ԫ�ؽ��кϲ������Ĵ���...........
        Vector resultVector = new Vector();
        for (int i=0; i<vector.size(); i++)
        {
            Object[] temp1 = (Object[])vector.elementAt(i);
            //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�temp1�е�ÿ��Ԫ���ж�
            //���Ƿ�Ϊnull, �����null����ת��Ϊ""
            for(int j=0; j<temp1.length; j++)
            {
                if(temp1[j] == null)
                {
                    temp1[j] = "";
                }
            }
            //�����ǰ���partNumber���кϲ��ģ�����
            String partNumber1 = (String)temp1[numberSite];
            boolean flag = false;
            for (int j=0; j<resultVector.size(); j++)
            {
                Object[] temp2 = (Object[])resultVector.elementAt(j);
                String partNumber2 = (String)temp2[numberSite];
                if (partNumber1.equals(partNumber2))
                {
                    flag = true;

                    //���������λ�ô���0��˵���������������������Ȼ����ͬ�㲿��
                    //�������ϲ���
                    if(quantitySite>0)
                    {
                        //��temp2��temp1�е�Ԫ�ؽ��кϲ����ŵ�resultVector��ȥ��:::
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

        //��Ҫ�Ե�һ��Ԫ�ؽ����жϣ�����䣬source��type���������source, type��ͬ��
        //�ͱ���������ɾ������
        //��ʵ����partIfc������:::
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
            //�ѵ�һ��Ԫ�ص������ĳ�""
            Object[] firstObj = (Object[])resultVector.elementAt(0);

            //����������������������������Ϊ�ա�
            if(quantitySite>0)
            {
                firstObj[quantitySite] = "";
            }
            resultVector.setElementAt(firstObj, 0);
        }
        //����ű���������Ľ����
        Vector result = new Vector();
        //Ȼ�����ﻹ��Ҫ�����ķ���ֵ���ϰ��յ�ǰ��source��type���й��ˣ�
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
        //����Ҫ���vector�����һ��Ԫ�أ�
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
        //������Ҫͨ���ж���ȷ��firstElement��ֵ:
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        //������Ƶ���ͨ����Ϊ�գ�
        if(attrNullTrueFlag)
        {
            //������Ƶ���չ����ҲΪ�գ�
            if(affixAttrNullTrueFlag)
            {
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
//                firstElement.addElement(ssss);
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
//                firstElement.addElement(ssss);
            }
        }
        //������Ƶ���ͨ���Բ�Ϊ�յĻ���
        else
        {
            for(int i=0; i<attrNames.length; i++)
            {
                String attr = attrNames[i];
                ssss = QMMessage.getLocalizedMessage(RESOURCE, attr, null);
                firstElement.addElement(ssss);
            }
        }
        //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
        //����ӵ�vector�еĵ�һ��λ�ã�
        Object[] tempArray = new Object[firstElement.size()];
        for(int i=0; i<firstElement.size(); i++)
        {
            tempArray[i] = firstElement.elementAt(i);
        }
        result.insertElementAt(tempArray, 0);
        return result;
    }

    /**
     * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ�
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID���ǣ��񣩿ɷ֣�"true","false"������š����ơ�������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID���ǣ��񣩿ɷ֡������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Եļ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
     * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
     * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ����
     * @throws QMException
     * @return Vector
     */
    private Vector bianli(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                         String source, String type, PartConfigSpecIfc configSpecIfc,
                         float parentQuantity,PartUsageLinkIfc partLinkIfc)
       throws QMException
   {
       //����������Ҫʵ�ֹ���Ϊ:::
       //1���жϵ�ǰ���㲿���Ƿ��ǿɷֵ��㲿�����Է����ڰѸ��㲿���ŵ�����������е�ʱ�򣬿���ȷ��
       //���㲿���Ŀɷֱ�־
       PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli begin ....");
       Vector resultVector = new Vector();
       //�������浱ǰ���㲿�������кϸ�����㲿���ļ��ϣ�
       Vector hegeVector = new Vector();
       Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
       //���ʱ���Ӧ�����ж�collection�Ƿ���"null"
       if(collection != null && collection.size() > 0)
       {
           //��Ҫ��collection�е�����Ԫ�ؽ���ѭ���������������Ԫ��
           //��QMPartIfc��������Դ�����ͺ�����Ĳ�����һ�µģ�
           //������������㲿���ǿɷֵ�.���Ǹ���source, type�����ӽڵ���й���:::
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
                       //��һ���൱��������һ���Ե�ǰ�㲿�������ж����㲿���Ĺ�������.
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

       //�ѱ�part->resultVector��;
       Object[] tempArray = null;
       boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
       boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
       if(attrNullTrueFlag)
       {
           //����������Ƶ����Լ��϶�Ϊ�յ�ʱ��
           if(affixAttrNullTrueFlag)
           {
               tempArray = new Object[3];
//               tempArray = new Object[7];
           }
           //������Ƶ���ͨ����Ϊ�գ������Ƶ���չ���Բ�Ϊ�յ�ʱ��
           else
           {
               tempArray = new Object[3 + affixAttrNames.length];
           }
       }
       else
       {
           //������Ƶ���ͨ���Լ��ϲ�Ϊ�գ����Ƶ���չ���Լ���Ϊ�յ�ʱ��
           if(affixAttrNullTrueFlag)
           {
               tempArray = new Object[3 + attrNames.length];
           }
           //����������Ƶ����Լ��϶���Ϊ�յ�ʱ��
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
        tempArray[1] = new Integer(numberSite);//����������ڵ�λ��
//       tempArray[1] = partIfc.getPartNumber();
//       tempArray[2] = partIfc.getPartName();
       String isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "false", null);
       if(hegeVector != null && hegeVector.size() > 0)
       {
           isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "true", null);
       }
       tempArray[2] = isHasSubParts1;
       //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
       //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
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
       //������Ҫ�����������Ƶ����Լ����������Ľ�����Ͻ�����֯��
       if (attrNullTrueFlag)
       {
           //���������Ƶ����Լ��϶�Ϊ�յ�ʱ��
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
           //�����������Ƶ���ͨ����Ϊ�գ������Ƶ���չ���Լ��ϲ�Ϊ�յ�ʱ��
       }
       //��������������Ƶ���ͨ���Լ���Ϊ�յ�ʱ��
       //���濪ʼ�������Ƶ���ͨ���Լ��ϲ�Ϊ�յ�ʱ��
       else
       {
           //�Ȱ����е���ͨ���Ե�ֵ�ŵ�tempArray�У�
           for (int i=0; i<attrNames.length; i++)
           {
               String attr = attrNames[i];
               attr = attr.trim();
               if(attr != null && attr.length() > 0)
               {
                   //modify by liun 2005.3.25 ��Ϊ�ӹ����еõ���λ
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
                       //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
                       //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
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
                       //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
                       try
                       {
                           Class partClass = Class.forName(
                               "com.faw_qm.part.model.QMPartInfo");
                           Method method1 = partClass.getMethod(attr, null);
                           Object obj = method1.invoke(partIfc, null);
                           //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
                           //���obj��Ϊnull, ������String, tempArray[i + 5] = (String)obj;
                           //���obj��ö�����ͣ�tempArray[i + 5] = (EnumerationType)obj.getDisplay();
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
       //���Ѿ����˴���ĵ�ǰ������㲿�����������㲿�����еݹ鴦��::::
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
     * �ּ������嵥����ʾ��
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
     * 0����ǰpart��BsoID��
     * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
     * 2-...���ɱ�ģ����û�ж������ԣ�2����ǰpart�ı�ţ�3����ǰpart������
     *                              4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     *                              5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
     *               ������������ԣ��������ж��Ƶ����Լӵ���������С�
     * �����������˵ݹ鷽����
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
     * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
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

        //��¼�����ͱ�������������ڵ�λ�á�
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
        //�ѽ�������еĵ�һ��Ԫ�ص�ʹ�õ��������""
        if(vector != null && vector.size() > 0)
        {
            Object[] first = (Object[])vector.elementAt(0);

            //����������������������������Ϊ�ա�
            if(quantitySite>0)
            {
                first[quantitySite] = "";
            }
            vector.setElementAt(first, 0);
        }
        //����Ҫ���vector�����һ��Ԫ�أ�
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
        //������Ҫͨ���ж���ȷ��firstElement��ֵ:
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
        //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
        //����ӵ�vector�еĵ�һ��λ�ã�
        Object[] tempArray = new Object[firstElement.size()];
        for(int i=0; i<firstElement.size(); i++)
        {
            tempArray[i] = firstElement.elementAt(i);
        }
        vector.insertElementAt(tempArray, 0);
        //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�vector�е�ÿ��Ԫ���ж�
        //���Ƿ�Ϊnull, �����null����ת��Ϊ""
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
     * ˽�з�������setMaterialList()�������ã�ʵ�ֶ��Ʒּ������嵥�Ĺ��ܡ�
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
     * 0����ǰpart��BsoID��
     * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
     * 2-...���ɱ�ģ����û�ж������ԣ�2����ǰpart�ı�ţ�3����ǰpart������
     *                              4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     *                              5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
     *               ������������ԣ��������ж��Ƶ����Լӵ���������С�
     * @param partIfc :QMPartIfc ��ǰ�Ĳ�����
     * @param attrNames :String[] ���Ƶ����Լ��ϣ�����Ϊ�ա�
     * @param affixAttrNames :String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
     * @param level :int ��ǰpart���ڵļ���
     * @param partLinkIfc :PartUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
     * @param parentQuantity :int ��ǰpart�ĸ��׽ڵ㱻�������ʹ�õ�������
     * @throws QMException
     * @return Vector
     */

    private Vector fenji(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
        PartConfigSpecIfc configSpecIfc, int level, PartUsageLinkIfc partLinkIfc,
        float parentQuantity) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji begin ....");
        //���������ֵ��
        Vector resultVector = new Vector();
        Object[] tempArray = null;
        //��ʶ���Ƶ���ͨ�����Ƿ�Ϊ�գ����Ϊ�գ��ñ�ʶΪ�棬����Ϊ�٣�
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        //��ʶ���Ƶ���չ�����Ƿ�Ϊ�գ����Ϊ�գ��ñ�ʶΪ�棬����Ϊ�٣�
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        //����ַ��������б������"ͷ��Ϣ":
        if(attrNullTrueFlag)
        {
            if(affixAttrNullTrueFlag)
            {
                //��������Ķ��Ƶ����Լ��϶�Ϊ�յĻ���
                tempArray = new Object[3];
//                tempArray = new Object[7];
            }
            else
            {
                //���ֻ�ж��Ƶ���չ���Բ�Ϊ�յ�ʱ��
                tempArray = new Object[3 + affixAttrNames.length];
            }
        }
        else
        {
            if(affixAttrNullTrueFlag)
            {
                //���ֻ�ж��Ƶ���ͨ���Բ�Ϊ�յ�ʱ��
                tempArray = new Object[3 + attrNames.length];
            }
            else
            {
                //����������Ƶ����Լ��϶���Ϊ�յ�ʱ��
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
        tempArray[1] = new Integer(numberSite);//����������ڵ�λ��
        tempArray[2] = new Integer(level);//level�ĳ�ʼֵΪ0��
//        tempArray[2] = partIfc.getPartNumber();
//        tempArray[3] = partIfc.getPartName();
        //���level = 0 ˵��������Ĳ�����
       /**if (level == 0)
        {
            parentQuantity = 1f;
            String quan = "1";
            tempArray[4] = new String(quan);
        }
        else
        {
            //�ɲ��������������ٱ���parentBsoID,���Ǳ���PartUsageLinkIfc����ѭ������������
            //��������ʡ���ٲ��ҵĹ��̡�QMPartUsageLinkIfc partLinkIfc
            parentQuantity = partLinkIfc.getQuantity();//parentQuantity*partLinkIfc.getQuantity();
            String tempQuantity = String.valueOf(parentQuantity);
            if (tempQuantity.endsWith(".0"))
                tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
            tempArray[4] = tempQuantity;
        }*/
        //�ж��Ƿ���Ҫ�������Խ��������
        if (attrNullTrueFlag)
        {
            //����������Ƶ����Լ��϶�Ϊ�յĻ���
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
        //������������Ƶ���ͨ����Ϊ�յ�ʱ��
        //���棺������Ƶ���ͨ���Բ�Ϊ�յ�ʱ��
        else
        {
            //�Զ��Ƶ���ͨ���Խ���ѭ����
            for (int j=0; j<attrNames.length; j++)
            {
                String attr = attrNames[j];
                attr = attr.trim();
                if(attr != null && attr.length() > 0)
                {
                    //modify by liun 2005.3.25 ��Ϊ�ӹ����еõ���λ
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
                        //���level = 0 ˵��������Ĳ�����
                        if (level == 0)
                        {
                            parentQuantity = 1f;
                            String quan = "1";
                            tempArray[3 + j] = new String(quan);
                        }
                        else
                        {
                            //�ɲ��������������ٱ���parentBsoID,���Ǳ���PartUsageLinkIfc����ѭ������������
                            //��������ʡ���ٲ��ҵĹ��̡�QMPartUsageLinkIfc partLinkIfc
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
                        //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
                        try
                        {
                            Class partClass = Class.forName(
                                "com.faw_qm.part.model.QMPartInfo");
                            Method method1 = partClass.getMethod(attr, null);
                            Object obj = method1.invoke(partIfc, null);
                            //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
                            //���obj��Ϊnull, ������String, attrNames[i] = (String)obj;
                            //���obj��ö�����ͣ�attrNames[i] = (EnumerationType)obj.getDisplay();
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
                    //ȡtemp�е�Ԫ�ؽ���ѭ����temp[k][0]��PartUsageLinkIfc,
                    //temp[k][1]��QMPartIfc
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
     * ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á�
	 * ����ֵΪvector������ÿ��Ԫ�ض���String[2]���͵ġ� <br>
	 * ���String[2]�ֱ��¼��:String[0]���㲿��ֵ����String[1]��html��ʽ��ͼ����Ϣ��
     * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
     * @param configSpecIfc :PartConfigSpecIfc ָ����ɸѡ������
     * @return vector:Vector ������������ʹ�õ���Ϣ���ϡ�
     * @throws QMException
     */
    public Vector setUsageList(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException//Begin CR1 ��д������߼�
{
    Vector resultVector = new Vector();
    pcon = new PartConfigSpecAssistant(configSpecIfc);
    Collection parentPartList = usage2(partIfc, configSpecIfc).values();
    //��Ҫ��parentPartList���д������parentPartList��ֻ��һ��Ԫ�أ�������partIfc�������:
    //CR6 begin 
    if (parentPartList.size() > 0) 
    {
        for (Iterator iterator = parentPartList.iterator(); iterator.hasNext();) 
        {
            BaseValueInfo partInfo = (BaseValueInfo) iterator.next();
            Object[] objects = new Object[2];
            objects[0] = partInfo;
            objects[1] = DocServiceHelper.getObjectImageHtml(partInfo);
            //CCBegin by liunan 2011-04-20 �򲹶�v4r3_p032
            // CR9begin
            //resultVector.add(objects);           
            String partBsoid=partIfc.getBsoID();
            String parentPartBsoid=partInfo.getBsoID();
            //if (resultVector.size() ==1&&partBsoid.equals(parentPartBsoid)){
            //	resultVector.removeElementAt(0);
            //}
            // ��������д����Լ������Ͳ�����ӵ��������
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
}//End CR1 ��д������߼�

    /**
     * ��setUsageList�����õķ�����ʵ�ֵݹ���á�
     * ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á�
     * ʹ�ý�������ڷ���ֵvector�С�
     * @param partIfc :QMPartIfcָ�����㲿��ֵ����
     * @param configSpecIfc :PartConfigSpecIfcָ����ɸѡ������
     * @param  partLinkIfc :PartUsageLinkIfc ��¼��ǰ��partIfc���䵱ǰ�ĸ��׽ڵ��ʹ�ù�ϵ��
     * @param vector :Vector ����ʹ�õĹ�ϵ��PartIfc�ļ��ϣ��������ǰһ��Ԫ�ر���һ��Ԫ��ʹ�á�
     * @return vector:Vector������������ʹ�õ���Ϣ���ϣ�ÿ��Ԫ��Ϊһ���ַ�������String[4]:��
     * String[0]:��κţ�
     * String[1]:�㲿�����(�㲿������) �汾(��ͼ)
     * String[2]:�㲿���ڴ˽ṹ�У��������У�ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ģ��㲿����ͬһ�Ӽ���ʹ�������ϲ���
     * String[3]:�㲿����BsoID
     * @throws QMException
     */
    private Vector usage(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc,
        PartUsageLinkIfc partLinkIfc, Vector vector,PartConfigSpecAssistant pcon) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "usage begin ....");
        Vector resultVector = new Vector();
        //��Ҫ�޸�vector�б���Ԫ�ص����ݽṹ:(A)--->(A, partUsageLink)
        //��Ϊ�����(A)�Ļ����޷���������ø÷����Ĳ���partLinkIfc��ʹ�������ˡ�
        //���Ľڵ��parUsageLinkIfc�ǣ��ַ�����"null"
        //�����û�ֻ��Ҫ��ײ�ڵ㱻���Ľڵ��ʹ�õ������������Ļ������ֻ��Ҫ���ص������ֵ��
        Object[] obj1 = new Object[2];
        obj1[0] = partIfc;
        if(partLinkIfc == null || !(PersistHelper.isPersistent(partLinkIfc)))
            obj1[1] = "null";
        else
            obj1[1] = partLinkIfc;
        vector.addElement(obj1);
        Collection coll1 = getParentPartsByConfigSpec(partIfc, configSpecIfc);
        //coll1Ϊ�յ�ʱ���൱��˵һ����֧�Ѿ������ˡ�
        //���԰�vector�е�����Ԫ�ؼӵ�mainVector���ˡ�
        if ((coll1 == null)||(coll1.size() == 0))
        {
            //��vector�е�����Ԫ�ذ��Ӻ���ǰ��˳���ŵ�mainVector��,�ȷŵ�tempArray�С�
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
                    //������൱�ڼ��𣬴�0��ʼ:
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
            //CCBegin by liunan 2008-10-4 �򲹶�200816
            //����(1)20080811 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
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
            //����(1)20080811 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
            //CCEnd by liunan 2008-10-4
            int tempSize1 = vector.size();
            vector.remove(tempSize1-1);
            PartDebug.trace(this, PartDebug.PART_SERVICE, "usage end....return is Vector ");
            return resultVector;
        }
    }
    
    /**
     * ��setUsageList�����õķ�����ʵ�ֵݹ���á�
     * ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á�
     * ʹ�ý�������ڷ���ֵHashMap�С�
     * @param partIfc QMPartIfcָ�����㲿��ֵ����
     * @param configSpecIfc PartConfigSpecIfcָ����ɸѡ������
     * @return ������������ʹ�õ���Ϣ���ϡ�HashMap�м���bsoid��ֵ��ֵ����
     * @throws QMException
     */    
    private HashMap usage2(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException//Begin CR1  ��д������߼�
    {
        HashMap resultMap = new HashMap();
        Collection srcParentPartList = navigateUsedByToIteration((QMPartMasterIfc) partIfc.getMaster(), pcon);
        ArrayList parentPartList = new ArrayList();
        //CCBegin by liunan 2011-04-20 �򲹶�v4r3_p032
        //CR9 begin
        PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
        //CCBegin by liunan 2011-04-20
        if ((srcParentPartList != null) && (srcParentPartList.size() > 0)) 
        {
            for (Iterator iterator = srcParentPartList.iterator(); iterator.hasNext();) 
            {
                Object object = (Object) iterator.next();
                //ֻ������QMPartIfc�Ķ���
                if (object instanceof QMPartIfc)
                //CCBegin by liunan 2011-04-20 �򲹶�v4r3_p032
                //CR9 begin
                {
                	//Դ��
                    //parentPartList.add(object);
                    ArrayList PartmasterbsoID = new ArrayList();
                    // ��ͨ��������õĸ�����ͨ�����ù淶���ҵ��������ù淶�����°汾��QMpartIfcΪ�ҵ��ķ������ù淶�����°汾
                    QMPartIfc QMpartIfc = getPartByConfigSpec((QMPartMasterIfc)(((QMPartIfc)object).getMaster()), configSpecIfc);
                    // �˴�����sql����QMpartIfc����һ���Ӽ�
                    QMQuery query = new QMQuery("PartUsageLink");
                    QueryCondition condition = new QueryCondition("rightBsoID", "=", QMpartIfc.getBsoID());
                    query.addCondition(condition);
                    //CCBegin SS1
                    Vector linkAttr = new Vector();//Begin CR11
                    linkAttr.add("leftBsoID");
                    query.addAttribute(0, linkAttr);
                    query.setJdbc(true);//End CR11
                    //CCEnd SS1
                    // �ҵ�QMpartIfc��һ���Ӽ���link
                    Collection link = (Collection)pService.findValueInfo(query);
                    // ����Ӽ���Ϊ�գ����Ӽ�ѭ������һ����ʱ�����б�������
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
                    // ����Ӽ������д���ԭ�е�Ŀ�����֤�������ҵĸ�������ȷ�����ǵĹ��������ڣ�������һ�����ң�����֤�������Ѿ����Ͽ��ˣ����ҽ���
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
        //parentPartListΪ�յ�ʱ���൱��˵һ����֧�Ѿ������ˡ����԰ѷ��ؽ���ˡ�
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
    }//End CR1  ��д������߼�

    /**
     * ͨ��ָ�������ù淶�������ݿ���Ѱ��ʹ����(partIfc����Ӧ��)PartMasterIfc��
     * ���з������ù淶�Ĳ���(QMPartIfc)��
     * ����ֵ����Object[] = (PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ��ϡ�
     * @param partIfc �㲿��ֵ����
     * @param partConfigSpecIfc �㲿�����ù淶��
     * @return Collection
     * @throws QMException
     */
    public Collection getParentPartsByConfigSpec(QMPartIfc partIfc, PartConfigSpecIfc partConfigSpecIfc)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getParentPartsByConfigSpec() begin ....");
        //����navigateUsedByToIteration(partIfc, partConfigSpecIfc)
        //�Խ�����Ͻ��й��ˣ�ֻ������QMPartIfc�Ķ���
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
                //leftBsoID�Ǳ�ʹ�õ��㲿����QMPartMaster��BsoID
                //rightBsoID��ʹ�����㲿����BsoID::
                String leftBsoID = partIfc.getMasterBsoID();
                String rightBsoID = parentPartIfc.getBsoID();
                //��Ҫ����leftBsoID��rightBsoID�ҵ�PartUsageLinkIfc����Ӧ��ֻ��һ��������һ��ֻ��һ������Ϊ�ж�����ͬһ�Ӽ������ skx��
                Collection coll = pService.findLinkValueInfo("PartUsageLink", leftBsoID, "uses", rightBsoID);

                //modify by skx ���ж����ӵ����Ҫ�ڱ����ڲ�Ʒ�����а�ÿһ��·������ʾ����
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
     * ����ָ�������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
     * @param partConfigSpecIfc PartConfigSpecIfc �㲿�����ù淶ֵ����
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
            //"��ǰû��ѡ��һ����Ч��ɸѡ����"
            throw new PartException(RESOURCE, "12", null);
        }
        PartConfigSpecAssistant assistant = new PartConfigSpecAssistant(partConfigSpecIfc);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "appendSearchCriteria end....return is QMQuery");
        return assistant.appendSearchCriteria(query);
    }

    /**
     * �������ù淶���˳��������ù淶��collection������QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾��
     * @param collection Collection ������ļ��ϡ�
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
     * ��ѯ��ǰ�û������ù淶��
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    public PartConfigSpecIfc findPartConfigSpecIfc() throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartConfigSpecIfc begin ....");
        QMQuery query = new QMQuery("PartConfigSpec");
        SessionService service = (SessionService)EJBServiceHelper.getService("SessionService");
        String curUserID = service.getCurUserID();
        //CCBegin by liunan 2009-01-06 �򲹶�v3r11_p021_20090105
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
            //CCBegin by liunan 2009-01-06 �򲹶�v3r11_p021_20090105
            configSpecCach.put(curUserID, partcsIfc);
            //CCEnd by liunan 2009-01-06
            PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartConfigSpecIfc end....return is PartConfigSpecIfc ");
            return partcsIfc;
        }
        else
        {

            //"�������һ����Ʒ���ù淶�����ǲ�ѯ�����ö������"
            throw new PartException(RESOURCE, "2", null);
        }
        //CCBegin by liunan 2009-01-06 �򲹶�v3r11_p021_20090105
        }
        //CCEnd by liunan 2009-01-06
    }

    /**
     * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
     * ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
     * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
     * ���û��ƥ����󣬼�¼mastered����
     * @param partIfc �㲿��ֵ����
     * @param configSpecIfc �㲿�����ù淶��
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
     * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
     * ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
     * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
     * ���û��ƥ����󣬼�¼mastered����
     * @param partIfc �㲿��ֵ����
     * @return
     * @throws QMException
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException {//Begin CR4����д������߼�
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
    }//End CR4����д������߼�

    /**
     * �������ù淶�а�������Ϣ���˽�����͡�
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
     * �����㲿�������ù淶��
     * @param configSpecIfc PartConfigSpecIfc ��������㲿�����ù淶ֵ����
     * @throws QMException
     * @return PartConfigSpecIfc �������㲿�����ù淶ֵ����
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
        //CCBegin by liunan 2009-01-06 �򲹶�v3r11_p021_20090105
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
     * ͨ�����ƺͺ���������㲿��������ģ����ѯ��
     * ���nameΪnull���������ѯ�����numberΪnull�������Ʋ�ѯ��
     * ���name��numnber��Ϊnull������������㲿����ֵ����
     * @param name :String ģ����ѯ���㲿�����ơ�
     * @param number :String ģ����ѯ���㲿���ĺ��롣
     * @return collection:Collection ���ϲ�ѯ���������㲿����ֵ����ļ��ϡ�
     * @throws QMException
     */
    public Collection getAllPartMasters(String name, String number) throws QMException
    {

          PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllPartMasters(name, number) begin ....");
        //����Ľ��÷�����֧��*��%����ͨ��������������û������������*������%,������
        //���ҵ������
        //�޸����£�
        //��һ�������ж�name�����һ���ַ��Ƿ���*,����%��
        //�����*, ��ȥ��name�е�*, ���ڲ�ѯ����������*.ͬ��������ǣ�������
        //name��ȥ��%�����ڲ�ѯ����������%.
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        boolean flag = false;
        if((name != null)&&(name.length()>0))
        {
            //��Ҫ�Ȼ�ȡname�е����һ���ַ���
            int length = name.length();
            String nameLast = name.substring(length -1 , length);
            QueryCondition condition1 = null;
            if(nameLast.equals("*"))
            {
                //��Ҫȥ��name�е����һ���ַ���
                name = name.substring(0, length -1);
                condition1 = new QueryCondition("partName", "like", name + "*");
            }
            else
            {
                if(nameLast.equals("%"))
                {
                    //��Ҫȥ��name�е����һ���ַ���
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
            //��Ҫ�Ȼ�ȡnumber�е����һ���ַ���
            int length = number.length();
            String numberLast = number.substring(length -1 , length);
            QueryCondition condition2 = null;
            if(numberLast.equals("*"))
            {
                //��Ҫȥ��number�е����һ���ַ���
                number = number.substring(0, length -1);
                condition2 = new QueryCondition("partNumber", "like", number + "*");
            }
            else
            {
                if(numberLast.equals("%"))
                {
                    //��Ҫȥ��number�е����һ���ַ���
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
    * ͨ�����ƺͺ���������㲿��������ģ����ѯ�����nameΪnull���������ѯ�����number
    * Ϊnull�������Ʋ�ѯ�����name��numnber��Ϊnull������������㲿����ֵ����
    * �������nameFlagΪtrue, �����㲿�����ƺ�name����ͬ����Щ�㲿�������򣬲����㲿��
    * ���ƺ�name��ͬ���㲿�����Բ���numFlag��ͬ���Ĵ���
    * @param name ����ѯ���㲿�����ơ�
    * @param nameFlag ����������
    * @param number ����ѯ���㲿����š�
    * @param numFlag ����������
    * @return Collection ��ѯ���������㲿��(QMPartMasterIfc)�ļ��ϡ�
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
            //��Ҫ����name�����е�*, % �������ѯ������
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
     * ����partIfcѰ�Ҷ�Ӧ��partMasterIfc,�ٲ���partUsageLink����ȡʹ���˸�partMasterIfc
     * �������㲿����
     * @param partIfc :QMPartIfc �㲿��ֵ����
     * @exception QMException �־û�������쳣��
     * @return Collection QMPartIfc�ļ��ϡ�
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
     * ����ָ�����㲿��ֵ�����ɸѡ��������㲿�����ӡ�����Vector:
     * Vector�д��NormalPart�Ķ���ļ��ϣ�ÿ��NormalPart�����е����԰�����
     * ����+����+�汾+��ͼ+����+��λ��ע�⣬����ֵVector�в�������ǰ����Ĳ���partIfc��
     * @param partIfc :��ǰ�㲿��ֵ����
     * @param configSpecIfc ��ǰ�㲿�����ù淶ֵ����
     * @return Vector
     * @throws QMException
     */
    public Vector getSubProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubProductStructure() begin ....");
      Vector resultVector = new Vector();
      // ��һ��������getUsesPartIfcs����������Collection�����CollectionΪ�գ����߳���Ϊ0,�׳��쳣
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      if((collection.size() == 0) || (collection == null))
      {
        //"δ�ҵ����ϵ�ǰɸѡ�����İ汾"
        //throw new PartException(RESOURCE, "E03014", null);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubProductStructure() end....return is new Vector()");
        return resultVector;
      }
      //�ڶ���: ��collection�е�ÿ��Ԫ�ؽ���ѭ������Ҫ��������һ���ݹ�ķ���productStructure()
      //productStructure(subPartIfc, configSpecIfc, parentBsoID, partUsageLinkIfc)
      else
      {
        PartServiceHelper pshelper= new PartServiceHelper();
        Vector tempVector = new Vector();
        Object[] tempArray = new Object[collection.size()];
        collection.toArray(tempArray);
        //�Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
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


            //��Ϊ�㲿������Ϣ�ڲ鿴�ṹ�в�����չ����
            Collection collection1 = getUsesPartIfcs(partIfc1, configSpecIfc);

            //�Ƿ����Ӽ��ı�ʶ����ʼ��Ϊ0����û���Ӽ����û���Ӽ��ķ���
            //getUsesPartIfcs()���������к����Ӽ���������Ϊ1��
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
            //�����õ����Ӽ�Ҫ�����Ƿ�չ������ʼ������Ϊ0��������Ҫչ����
            //��Ϊ�ڲ鿴�ṹ�Ľ��������ڵ������չ�������Ե�ǰ�㶼�ǲ�չ��״̬��
            //ֻ�е��չ���󣬲Ż�ı�״̬��
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

            //�����õ����Ӽ�Ҫ�����Ƿ����Ӽ����Ƿ�չ���ı궼Ϊ0������û�С�
            //��Ϊ�㲿������Ϣ�ڲ鿴�ṹ�в�����չ����
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
     * ����ָ�����㲿��ֵ�����ɸѡ��������㲿���Ĳ�Ʒ�ṹ������Vector:
     * Vector�д��NormalPart�Ķ���ļ��ϣ�ÿ��NormalPart�����е����԰�����
     * ����+����+�汾+��ͼ+����+��λ��ע�⣬����ֵVector�в�������ǰ����Ĳ���partIfc��
     * @param partIfc :��ǰ�㲿��ֵ����
     * @param configSpecIfc ��ǰ�㲿�����ù淶ֵ����
     * @return Vector
     * @throws QMException
     */
    public Vector getProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getProductStructure() begin ....");
      Vector resultVector = new Vector();
      // ��һ��������getUsesPartIfcs����������Collection�����CollectionΪ�գ����߳���Ϊ0,�׳��쳣
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      if((collection.size() == 0) || (collection == null))
      {
        //"δ�ҵ����ϵ�ǰɸѡ�����İ汾"
        //throw new PartException(RESOURCE, "E03014", null);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getProductStructure() end....return is new Vector()");
        return resultVector;
      }
      //�ڶ���: ��collection�е�ÿ��Ԫ�ؽ���ѭ������Ҫ��������һ���ݹ�ķ���productStructure()
      //productStructure(subPartIfc, configSpecIfc, parentBsoID, partUsageLinkIfc)
      else
      {
        Vector tempVector = new Vector();
        Object[] tempArray = new Object[collection.size()];
        collection.toArray(tempArray);
        //�Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
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
    }//���!!!!

    /**
     * �ݹ鷽������getProductStructure()���������ã�ʵ�ֶԵ�ǰpartIfc����partConfigSpecIfcɸѡʹ�ýṹ��
     * @param partIfc �㲿��ֵ����
     * @param configSpecIfc �㲿��ֵ����
     * @param parentBsoID ��ǰpartIfc�ĸ��ڵ��bsoID��
     * @param partUsageLinkIfc ��¼partIfc����parentBsoID��Ӧ��parentPartIfc�Ĺ�����ϵ��
     * @return Vector NormalPart����ļ��ϡ�
     * @throws QMException
     */
    private Vector productStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc,
                                    String parentBsoID, PartUsageLinkIfc partUsageLinkIfc)
        throws QMException
    {
      Vector resultVector = new Vector();
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      //�����ǰpartIfcû���ӽڵ�,�Ͱѵ�ǰ��resultVectorֱ�ӷ���
      if((collection == null) || (collection.size() < 1))
        return resultVector;
      //���򣬶�collection�е�����Ԫ�ؽ���ѭ�������ñ�����productStructure()
      else
      {
        Vector tempVector = new Vector();
        //������һ��ѭ���ݹ�Ĳ���
        Object[] tempArray = new Object[collection.size()];
        collection.toArray(tempArray);
        //�����޸ģ���ȱ���Ϊ��ȱ���
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
          //tempArray[i]�е�tempArray[1]�����QMPartIfc���͵Ļ����ʹ��������QMPartMasterIfc���͵Ļ���������
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
     * �޸�Ψһ�Ա����㲿����Ψһ��ʶ��
     * @param partMaster QMPartMaster����
     * @param number ���ĺ���㲿�����롣
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
     * �޸��㲿�����˷������ͻ���ֱ�ӵ��ã������ƣ���š�
     *
     * @param partMasterIfc QMPartMasterIfcֵ����,
     *   ���㲿�����ƣ��㲿��������û�������µ����ƺͱ�š�
     * @param flag �Ƿ��޸��㲿���ı��,trueΪ�޸�,false���޸ġ�
     * @return partMasterIfc �޸ĺ���㲿����ֵ����
     * @throws QMException
     */
    public QMPartMasterIfc renamePartMaster(QMPartMasterIfc partMasterIfc,boolean flag) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "renamePartMaster begin......");
        //�������Ϊ�գ��׳��쳣
        if(partMasterIfc== null)
        {
            java.lang.Object aobj[] = {"QMPartMasterInfo"};
            throw new PartException(RESOURCE, "CP00001", aobj);
        }
        PersistService persistService = (PersistService)EJBServiceHelper.getPersistService();
        //��ȡ�㲿������Ϣ����
        QMPartMaster master = (QMPartMaster)persistService.refreshBso(partMasterIfc.getBsoID());
        //�����name��number�����û���������µ��㲿�����ƺͱ�ţ�
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
    
    //CR7 Begin 20090619 zhangq  �޸�ԭ��TD-2190��
    /**
     * ������ɢ��������Ϣ����PartConfigSpecIfc����
     * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0��
     * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0��
     * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0��
     * @param baselineID ��׼�ߵ�BsoID��
     * @param configItemID ���ù淶��BsoID��
     * @param viewObjectID ��ͼ�����bsoID��
     * @param effectivityType ��Ч�����͡�
     * @param effectivityUnit ��Ч�Ե�λ(��Ч�Ե�ֵ)��
     * @param state ״̬��
     * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true��
     * @return PartConfigSpecIfc ��װ�õ��㲿�����ù淶ֵ����
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
//        //�������Ч�����ù淶:
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
//            //����(1)20080410 zhangq begin:���ݿͻ�������û��ѡ����Ч�Է������Ƶ����ù淶ʱ�����ֿ�ָ���쳣����TD-1390��
//            //����Ч��������������ʽת����Ӣ����ʽ��
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
//            //����(1)20080410 zhangq end
//            if(viewObjectIfc != null)
//                pEffcs.setViewObjectIfc(viewObjectIfc);
//            pEffcs.setEffectiveUnit(effectivityUnit);
//            partConfigSpecIfc.setEffectivity(pEffcs);
//            partConfigSpecIfc.setEffectivityActive(true);
//            partConfigSpecIfc.setBaselineActive(false);
//            partConfigSpecIfc.setStandardActive(false);
//        }
//        //����ǻ�׼�����ù淶:
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
//        //����Ǳ�׼���ù淶:
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
     * ������ɢ��������Ϣ����PartConfigSpecIfc����
     * @param isUseCache �Ƿ��û�������ù淶�ı�־ ��1,��0��
     * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0��
     * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0��
     * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0��
     * @param baselineID ��׼�ߵ�BsoID��
     * @param configItemID ���ù淶��BsoID��
     * @param viewObjectID ��ͼ�����bsoID��
     * @param effectivityType ��Ч�����͡�
     * @param effectivityUnit ��Ч�Ե�λ(��Ч�Ե�ֵ)��
     * @param state ״̬��
     * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true��
     * @return PartConfigSpecIfc ��װ�õ��㲿�����ù淶ֵ����
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
        //�������Ч�����ù淶:
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
            //����(1)20080410 zhangq begin:���ݿͻ�������û��ѡ����Ч�Է������Ƶ����ù淶ʱ�����ֿ�ָ���쳣����TD-1390��
            //����Ч��������������ʽת����Ӣ����ʽ��
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
            //����(1)20080410 zhangq end
            if(viewObjectIfc != null)
                pEffcs.setViewObjectIfc(viewObjectIfc);
            pEffcs.setEffectiveUnit(effectivityUnit);
            partConfigSpecIfc.setEffectivity(pEffcs);
            partConfigSpecIfc.setEffectivityActive(true);
            partConfigSpecIfc.setBaselineActive(false);
            partConfigSpecIfc.setStandardActive(false);
        }
        //����ǻ�׼�����ù淶:
        if(standardActive.equals("0") && baselineActive.equals("1") && effectivityActive.equals("0"))
        {
            BaselineIfc baselineIfc = (BaselineIfc)pService.refreshInfo(baselineID);
            PartBaselineConfigSpec pBaselinecs = new PartBaselineConfigSpec(baselineIfc);
            partConfigSpecIfc.setBaseline(pBaselinecs);
            partConfigSpecIfc.setEffectivityActive(false);
            partConfigSpecIfc.setBaselineActive(true);
            partConfigSpecIfc.setStandardActive(false);

        }
        //����Ǳ�׼���ù淶:
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
     * �����ĵ�ֵ�����ѯ���ݿ⣬��ȡ���б����ĵ����������㲿���ļ��ϡ�
     * ���flag = true, ��ʾֻ���ع�������һ�ߵ��㲿��(QMPartIfc)�ļ��ϣ����flag = false
     * ���ع�����(PartDescribeLinkIfc)�ļ��ϡ�
     * @param docIfc DocIfc �ĵ�ֵ����
     * @param flag boolean ��������ֵ�Ľṹ��
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
     * �������ù淶��ȡʹ����(ָ�����㲿������Ӧ��)QMPartMaster�������㲿���ļ��ϡ�
     * @param partIfc ָ�����㲿��ֵ����
     * @param configSpec �������ù淶��
     * @return Vector �㲿���ļ��ϡ�
     * @throws QMException
     */
    private Vector navigateUsedByToIteration(QMPartMasterIfc masterIfc, ConfigSpec configSpec)
        throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "navigateUsedByToIteration() begin ....");
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        //���صĽ������:
        Vector resultVector = new Vector();

        //CCBegin by liunan 2012-03-21
        /*QMQuery qmQuery = new QMQuery("QMPart", "PartUsageLink");   //Begin CR10     
        qmQuery = configSpec.appendSearchCriteria(qmQuery);
        //���ݲ�ѯ���������Ӧ�Ľ����:
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
        //����������:
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
     * add by ������ at 2004.5.24
     * �����ݿͻ���ʾ���Ϊ��ʷ��ѭ���صõ�ָ���㲿�������Ϊ��ʷ����
     * �����㲿��a���㲿��b���Ϊ��������b��c���Ϊ��������ô����a��bsoID����Ի��b��c����Ӧ��Ϣ
     * ����ֵ��Vector������ÿ��Ԫ����String���͵����飬�Դ����ʷ��������Ϣ��
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
     * add by ������ ���ָ���㲿����ǰһ�㲿���������㲿�������ĸ��㲿�����Ϊ�����ġ�
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
     * add by ������ 2004.6.14 ����ɸ����㲿�����������㲿��
     * ���ɸ��㲿�����Ϊ�õ��������㲿���������Ϣ�����ݿͻ���ʾ
     * ����ֵΪһVector������ÿ��Ԫ����һ�ַ������顣
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
     * ����ָ���ľ����㲿����ȡ����������Ϊ��Ʒ�ĸ����㲿�������°汾��
     * @param qmPartIfc QMPartIfc �����㲿����
     * @throws QMRemoteException
     * @throws QMException
     * @return Collection �����㲿�������°汾��
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
     * ����ָ���ľ����㲿�����ϻ�ȡ����������Ϊ��Ʒ�ĸ����㲿�������°汾��
     * @param Collection QMPartIfcs �����㲿�����ϡ�
     * @throws QMRemoteException
     * @throws QMException
     * @return Collection �����㲿�������°汾���ϡ�
     */
    public Collection getParentProduct(Collection qmPartIfcs)
            throws QMException
    {
        //QMProperties
        String s = null;
        String s1 = RemoteProperty.getProperty("com.faw_qm.part.productType");
        //����Ϊ��Ʒ�ļ�ֵ��
        List typeList = new ArrayList();
        StringTokenizer stringtokenizer = new StringTokenizer(s1, ",");
        while (stringtokenizer.countTokens() > 0)
        {
            s = ((String) stringtokenizer.nextElement()).trim();
            typeList.add(s);
        }
        //���и����㲿��������Ϣ���ϡ�
        List parentPartMasters = new Vector();
        Iterator I = qmPartIfcs.iterator();

        while(I.hasNext())
        {
        	QMPartIfc part = (QMPartIfc) I.next();
        	List list = getAllParentParts(part);
        	parentPartMasters.addAll(list);
        }


        //���и����㲿�������°汾���ϡ�
        List parentParts = (List) filteredIterationsOf(
                parentPartMasters, findPartConfigSpecIfc());
        //����Ϊ��Ʒ�ĸ����㲿���Ľ������
        List result = new ArrayList();
        Object[] parentPartsArray = parentParts.toArray();
        Object[] typeArray = typeList.toArray();
        //���˳�����Ϊ��Ʒ�ĸ����㲿����
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
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param childPartIfc QMPartIfc ���㲿����
     * @throws QMException
     * @return String ʹ��������
     */
    public String getPartQuantity(QMPartIfc parentPartIfc, QMPartIfc childPartIfc)
            throws QMException
    {
        //��ȡ��ǰ�û������ù淶��
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                              findPartConfigSpecIfc();
        if(parentPartIfc.getPartNumber().equals(childPartIfc.getPartNumber()))
            return "1";
        //��ȡ���㲿��ͳ�Ʊ�
        String[] attrNames = {"quantity"};
        List childParts = setBOMList(parentPartIfc, attrNames, null, null, null,
                                     partConfigSpecIfc);
        Object[] childPartsArray = childParts.toArray();
        //��ȡָ�����㲿����ʹ��������
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
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param middlePartMasterIfc QMPartMasterIfc �м��㲿��������Ϣ��
     * @param childPartIfc QMPartIfc ���㲿����
     * @throws QMException
     * @return String ʹ��������
     */
    public String getPartQuantity(QMPartIfc parentPartIfc,
                                  QMPartMasterIfc middlePartMasterIfc,
                                  QMPartIfc childPartIfc)
            throws QMException
    {
        //��ȡ��ǰ�û������ù淶��
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
            //��ȡ���㲿��ͳ�Ʊ�
            List childParts = setBOMList(parentPartIfc, attrNames, null, null, null,
                                         partConfigSpecIfc);
            Object[] childPartsArray = childParts.toArray();

            //��ȡ�м��㲿����ʹ��������
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
     * ��ȡ�������ù淶���㲿����
     * @param partMasterIfc QMPartMasterIfc �㲿������Ϣ��
     * @param partConfigSpecIfc PartConfigSpecIfc ���ù淶��
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
     * ��ָ����Ʒ�У���ȡָ�����㲿�������и��㲿����
     * @param parentPartMasterIfc QMPartMasterIfc ��Ʒ������Ϣ��
     * @param childPartMasterIfc QMPartMasterIfc �ò�Ʒ�е����㲿��������Ϣ��
     * @throws QMException
     * @return HashMap ���㲿������,����PartNumber��ֵ��ֵ����
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
     * ��ָ����Ʒ�У���ȡָ�����㲿�������и��㲿������ͬ�����з������á�ʵ�ֵ�����ȡ���㲿����
     * @param parentPartMasterIfc QMPartMasterIfc ��Ʒ������Ϣ��
     * @param partConfigSpecIfc PartConfigSpecIfc ��ǰ�û�ʹ�õ����ù淶��
     * @param childPartIfc QMPartIfc �ò�Ʒ�еķ��ϵ�ǰ���ù淶�����㲿�������°汾��
     * @param tempList List ��ʱ���档
     * @throws QMException
     * @return HashMap �������
     */
    private HashMap getParentPartsFromProduct(QMPartMasterIfc parentPartMasterIfc,
                      PartConfigSpecIfc partConfigSpecIfc, QMPartMasterIfc childPartMasterIfc, List tempList)
            throws QMException
    {
        //�����ʹ��HashMap����֤������е�Ԫ��Ψһ��
        HashMap result = new HashMap();
        List parentsList = (List) navigateUsedByToIteration(childPartMasterIfc,
                new PartConfigSpecAssistant(partConfigSpecIfc));
        Object[] parentsObject = parentsList.toArray();
        //�Ը��㲿������ѭ����
        for (int i = 0; i < parentsObject.length; i++)
        {
            //������㲿����ָ����Ʒ��
            if (((QMPartIfc) parentsObject[i]).getPartNumber().equals(
                    parentPartMasterIfc.getPartNumber()))
            {
            	Object[] tempObject = tempList.toArray();
                for (int j = 0; j < tempObject.length; j++)
                {
                    //���������в���������ӡ�
                    if (!result.containsKey(((QMPartIfc) tempObject[j]).getPartNumber()))
                        result.put(((QMPartIfc) tempObject[j]).getPartNumber(), tempObject[j]);
                }
                //�����ʱ���档
                tempList = new ArrayList();
            }
            //�������������ϵ�����ȡ���㲿����
            else
            {
                tempList.add(parentsObject[i]);
                HashMap tempResult = getParentPartsFromProduct(parentPartMasterIfc, partConfigSpecIfc,
                                       (QMPartMasterIfc)((QMPartIfc) parentsObject[i]).getMaster(), tempList);
                //�������ӵ�������С�
                for (int j = 0; j < tempResult.size(); j++)
                {
                    Collection col = tempResult.values();
                    Object[] obj = col.toArray();
                    for (int k = 0; k < obj.length; k++)
                    {
                        //���������в���������ӡ�
                        if (!result.containsKey(((QMPartIfc) obj[k]).getPartNumber()))
                            result.put(((QMPartIfc) obj[k]).getPartNumber(), obj[k]);
                    }
                }
                //�����ʱ���档
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
     * ��ȡ��������
     * @return "StandardPartService"
     */
    public String getServiceName()
    {
        return "StandardPartService";
    }

    /**
     * �����Ʒ�������Ӽ��ڲ�Ʒ��ʹ�õ�������
     * @param product
     * @param partConfigSpecIfc
     * @return HashMap �����Ӽ���bsoid��ֵ��һ�����飬��һλ���Ӽ���ֵ���󣬵�2λ
                        ���Ӽ��ڲ�Ʒ�е�ʹ��������
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
       // ��һ��������getUsesPartIfcs����������Collection�����CollectionΪ�գ����߳���Ϊ0,�׳��쳣
       Collection collection = getUsesPartIfcs(partifc, partConfigSpecIfc);
       if((collection.size() == 0) || (collection == null))
       {
           //"δ�ҵ����ϵ�ǰɸѡ�����İ汾"
           //throw new PartException(RESOURCE, "E03014", null);
           PartDebug.trace(this, PartDebug.PART_SERVICE,
                   "getSubProductStructure() end....return is new Vector()");
           return result;
       }
       //�ڶ���: ��collection�е�ÿ��Ԫ�ؽ���ѭ������Ҫ��������һ���ݹ�ķ���productStructure()
       //productStructure(subPartIfc, configSpecIfc, parentBsoID, partUsageLinkIfc)
       else
       {
           Object[] tempArray = new Object[collection.size()];
           collection.toArray(tempArray);
           QMPartIfc partIfc1 = null;
           PartUsageLinkIfc partUsageLinkIfc1=null;
           //�Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
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
     * ����ָ���Ĺ�����master��iteration֮�䣩������ÿ�������������ӵ�
     * mastered����ָ����ɫmaster���Ľ����
     * @param links ������ֵ���󼯺�
     * @param role ��ɫ��
     * @exception com.faw_qm.config.exception.ConfigException
     * @return ��Ӧ������ֵ�����Mastered���󼯺ϡ�
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
                throw new QMException(e, "���ݽ�ɫ�������BsoIDʱ����");
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
            }//endif ���collection�е�Ԫ�������Ӷ���object����MasteredIfc��
            //ʵ�����׳���ɫ��Ч����
            masterMap.put(bsoID, bsoObj);
           
            }
            resultVector.addElement(bsoObj);
        }//endfor i
        return removeDuplicates(resultVector);//end CR2
    }//end mastersOf(Collection,String)

    /**
     * ��ָ���Ľ�������ظ���Ԫ���ų���
     * @param collection �����
     * @return Collection  �ų����ظ����ݵļ���
     * Collection��ÿһ��Ԫ��ΪһObject����
     * ��Object�����еĵ�0��Ԫ��Ϊһֵ����
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
            hashtable.put(objBsoID, "");//��BsoID��Ϊ��־����Hash��
            resultVector.addElement(obj);
        }//endfor i
        return resultVector;
    }//end removeDuplicates(Collection)

    /**
     * ���������㲿�����ݿͻ��˸����㲿����11�����������㲿���Ĺ��ܣ�֧��ģ����ѯ�ͷǲ�ѯ��
     * �������ĵ������㲿�������У�Ҳ������������Ҫ���ݶ����Բ�ѯ�㲿����������
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
     * @return �㲿��ֵ���󼯺ϡ�
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
        //  ��ó־û�����
        PersistService ps = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //  �����µĲ��Ҷ���query
        QMQuery query1 = new QMQuery("QMPart");
        //             �����㲿����Ų�ѯ
        query1 = getFindPartInfoByNum(query1, partnumber, checkboxNum);
        //  �����㲿�����Ʋ�ѯ
        query1 = getFindPartInfoByName(query1, partname, checkboxName);
        query1 = getFindPartInfoByVersion(query1, partver, checkboxVersion);
        query1 = getFindPartInfoByView(query1, partview, checkboxView);
        //�����㲿���������ڲ�ѯ
        query1 = getFindPartInfoByLifeCycle(query1, partstate,
                checkboxLifeCState);
        query1 = getFindPartInfoByType(query1, parttype, checkboxPartType);
        query1 = getFindPartInfoByProducedBy(query1, partby, checkboxProducedBy);
        //  �����㲿����Ŀ���ѯ
        query1 = getFindPartInfoByProject(query1, partproject, checkboxProject);
        //  �����ļ��в�ѯ
        query1 = getFindPartInfoByFolder(query1, partfolder, checkboxFolder);
        //  ���ݴ����߲�ѯ
        query1 = getFindPartInfoByCreator(query1, partcreator, checkboxCreator);
        // ���ݴ���time��ѯ
        query1 = getFindPartInfoByTime(query1, partupdatetime,
                checkboxModifyTime, "modifyTime");
        /*
         modify by ShangHaiFeng 2004.03.22 BE:�����µİ汾����,����ȷ����������.
         ����ҵ������ں�״̬�£�ϵͳ��Ȩ�û�ֻ������������Ȩ�޵����°汾��ԭ���򸱱�����
         ҵ�������δ����ʱ����ҵ����󴴽��û�����ϵͳȨ�޿�����������ҵ�����ͼ����ʾΪ�����汾��ͼ�ꡣ
         ҵ��������״̬��ϵͳ�û�����ϵͳȨ�޿�����������ҵ����󣬰汾Ϊ���°汾��ͼ����ʾΪ�����汾��ͼ�ꡣ
         ҵ������ڼ��״̬ʱ��ҵ��������û�������������ҵ����󣬰汾Ϊ���°汾������汾����
         ����ʾ��ҵ������ԭ����ͼ����ʾΪҵ����󸱱���ͼ�ꡣ
         ҵ������ڼ��״̬ʱ����ҵ��������û����ҶԼ���û��ĸ������ϼ��޷���Ȩ�޵������û���
         ���Ը���ϵͳ��Ȩ����������ҵ����󣬰汾Ϊ��ҵ������ԭ����ͼ����ʾΪԭ����ͼ�ꡣ
         ҵ������ڼ��״̬ʱ����ҵ��������û����ҶԼ���û��ĸ������ϼ��з���Ȩ�޵������û���
         ������������ҵ����󹥣��汾Ϊ���°汾������汾��������ʾ��ҵ������ԭ����ͼ����ʾΪҵ����󸱱���ͼ��
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
        //  ����ĵ���Ų�Ϊ��
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
                //����ȷ����ֹʱ��ָ���
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
                //����ȷ����ֹʱ��ָ���
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
            //�������ȷ����ֹʱ��ָ�������ȡ��ֹʱ�䣬
            //���û����ȷ����ֹʱ��ָ�������һ���ж��Ƿ������ʱ�䣨��2003-04-28 10:00:00.0��
            //�粻�ǣ��������Ӧ��ֹʱ�䡣
            //��ָ��������2003-04-28 10����
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
            //�Ƚ�����ʱ���С�������ʼʱ�������ֹʱ�䣬ʱ��Ҫ����
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
                //  ����û���ѯ +
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
            //  ����û����ѯ -
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
        //  ����ĵ���Ų�Ϊ��
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
            //  ����û���ѯ���ĵ��в���������ĵ����
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
                //����Root��ͷ
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
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxFolder == null)
            {
                QueryCondition cond = new QueryCondition("location", "=",
                        partfolder);
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
        //  ����ĵ���Ų�Ϊ��
        if(partproject != null && !partproject.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxProject == null)
            {
                QueryCondition cond = new QueryCondition("projectId", "=",
                        partproject);
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
        //  ����ĵ���Ų�Ϊ��
        if(partby != null && !partby.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxProducedBy == null)
            {
                QueryCondition cond = new QueryCondition("producedByStr", "=",
                        partby);
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
        //  ����ĵ���Ų�Ϊ��
        if(parttype != null && !parttype.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxPartType == null)
            {
                QueryCondition cond = new QueryCondition("partTypeStr", "=", parttype);
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
        //  ����ĵ���Ų�Ϊ��
        if(partstate != null && !partstate.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxLifeCState == null)
            {
                QueryCondition cond = new QueryCondition("lifeCycleState", "=",
                        partstate);
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
        //  ����ĵ���Ų�Ϊ��
        if(partview != null && !partview.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxView == null)
            {
                QueryCondition cond = new QueryCondition("viewName", "like",
                        getLikeSearchString(partview));
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
        //  ����ĵ���Ų�Ϊ��
        if(partver != null && !partver.trim().equals(""))
        {
            if(query.getConditionCount() > 0)
            {
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
            }
            if(checkboxVersion == null)
            {
                QueryCondition cond = new QueryCondition("versionValue",
                        "like", getLikeSearchString(partver));
                query.addCondition(0, cond);
            }
            //  ����û����ѯ���ĵ����
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
     * �����㲿�����Ʋ�ѯ���ݿ�
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
            //  ����㲿�����Ʋ�Ϊ�գ����в�ѯ
            if(partname != null && !partname.trim().equals(""))
            {
                //׷��ҵ������
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
                    //����2����
                    QueryCondition qc1 = new QueryCondition("masterBsoID",
                            "bsoID");
                    qu.addCondition(0, i, qc1);
                    qu.addAND();
                }
                //  ����û���ѯ���㲿���в���������㲿������
                if(checkboxName == null)
                {
                    QueryCondition cond = new QueryCondition("partName",
                            QueryCondition.LIKE, getLikeSearchString(partname));
                    qu.addCondition(i, cond);
                }
                //  ����û����ѯ���㲿������
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
     * �����㲿����Ų�ѯ���ݿ�
     * @param  QMQuery qu,String partnumber,String checkboxNum
     * @return QMQuery
     * @exception QMException
     */
    public QMQuery getFindPartInfoByNum(QMQuery qu, String partnumber,
            String checkboxNum) throws QMException
    {
        QMQuery query = qu;
        //  ����㲿����Ų�Ϊ��
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
                //����2����
                QueryCondition qc1 = new QueryCondition("masterBsoID", "bsoID");
                query.addCondition(0, i, qc1);
                query.addAND();
            }
            //  ����û���ѯ���㲿���в���������㲿�����
            if(checkboxNum == null)
            {
                QueryCondition cond = new QueryCondition("partNumber",
                        QueryCondition.LIKE, getLikeSearchString(partnumber));
                query.addCondition(i, cond);
            }
            //  ����û����ѯ���㲿�����
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
     * ƥ���ַ���ѯ�������ַ���oldStr�е�"/*"ת����"*"��"*"ת����"%"��"%"������
     * �� "shf/*pdm%cax*"  ת���� "shf*pdm%cax%"
     * @param oldStr
     * @return ת�����ƥ���ַ���ѯ��
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
     * ����ʱ���ʽ�ַ�����ת����ָ����ʽ��ʱ���ַ���
     * @param date Ҫת����ʱ��
     * @param javaFormat ʱ���ʽ�ַ���
     * @return ��ָ����ʽת�����ʱ���ַ���
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
     * �������ù淶���˳��������ù淶��collection������QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾��
     * @param masterIfc MasteredIfc �������ֵ����
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
     * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
     * ������������bianli()����ʵ�ֵݹ顣
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����.
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼                                                                                     �������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
     * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
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

        //��¼�����ͱ�������������ڵ�λ�á�
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
            //�ѵ�һ��Ԫ�ص������ĳ�""
            Object[] firstObj = (Object[])resultVector.elementAt(0);

            //����������������������������Ϊ�ա�
            if(quantitySite>0)
            {
                firstObj[quantitySite] = "";
            }
            resultVector.setElementAt(firstObj, 0);
        }
        //����ű���������Ľ����
        Vector result = new Vector();
        //Ȼ�����ﻹ��Ҫ�����ķ���ֵ���ϰ��յ�ǰ��source��type���й��ˣ�
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

               //����й���·�ߣ�����ӡ�������ӵ������ơ�
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
     * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ�
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����.
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Եļ��ϣ�����Ϊ�ա�
     * @param routeNames : String[] ���Ƶ�Ҫ����Ĺ���·�����ļ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
     * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
     * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ����
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
      //��¼�����ͱ�������������ڵ�λ�á�
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
     
      //����㲿���Ĺ���·�ߡ�
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
	      
	        //ͬʱ������IBA���Ե�λ�ü��ǵڼ��γ���
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
    /**���������
     * ����numVec�����еı�Ŷ�objectVec��������numVec�е�ÿ�������objectVec�е��Ƕ�Ӧ�ģ��������������嵥
     * @param numVec
     * @param objectVec
     * @param pp trueΪ����falseΪ����
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
     * �ּ������嵥����ʾ������IBA�͹���·�ߡ�
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ��Object[]��
     * 0����ǰpart��BsoID��ʾλ�ã�Ϊҳ�泬����ʹ�ã�
     * 1����ǰpart��BsoID��
     * 2����ǰpart���ڵļ���
     * 3-...�������������ԣ�
     * ���Ԫ�أ���ǰpart�ĸ�����š�
     * �����������˵ݹ鷽����fenji(String parentPartNum, QMPartIfc partIfc, int level, PartUsageLinkIfc partUsageLinkIfc);
     * ΪPart-Other-classifylisting-001.jspʹ�á�
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNameArray :String[] ���Ƶ����ԣ�����Ϊ�ա�
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialList(QMPartIfc partIfc, String attrNameArray[]) throws QMException//Begin CR4����д������߼�
      {
          //�ּ��Ľṹ��Ϣ��vector��ÿ��Ԫ�ض���Object[5]����ǰpartIfc��������š����������𡢵�λ��
          Vector allUsageVec = fenji("", partIfc, 0, new PartUsageLinkInfo());
          TechnicsRouteService trService = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
          PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
          WfUtil wfutil = new WfUtil();
          //��ʱ����partֵ����ʹ��Map��ԭ���ǿ��԰��ظ����㲿�����˵���
          HashMap partIfcMap = new HashMap();
          for(int i = 0;i < allUsageVec.size();i++)
          {
              Object[] usageArray = (Object[])allUsageVec.get(i);
              partIfcMap.put((QMPartIfc)usageArray[0], (QMPartIfc)usageArray[0]);
          }
          //�㲿�����ϣ����ظ���
          Collection parIfcCol = partIfcMap.values();
          //�㲿��BsoID���ϣ����ظ���
          String[] partBsoIDArray = new String[parIfcCol.size()];
          //�㲿�����ϣ����ظ���
          QMPartIfc[] partIfcArray = new QMPartIfc[parIfcCol.size()];
          int z = 0;
          for(Iterator iterator = parIfcCol.iterator();iterator.hasNext();)
          {
              Object object = (Object)iterator.next();
              partIfcArray[z] = (QMPartIfc)object;
              partBsoIDArray[z] = ((QMPartIfc)object).getBsoID();
              z++;
          }
          //����ڶ������Լ����е�λ�ã�Ϊ��õ�ǰpart��BsoID��ʾλ����׼����
          int partNumberSite = 0;
          //���Զ��建�档����defIfc.getBsoID(), ֵ��ibaDisPlayName��
          HashMap defMap = new HashMap();
          //����ֵ���棺partBsoID + ibaDisPlayName, ����ֵ��
          HashMap ibaMap = new HashMap();
          //���Զ�����ʾ���ƻ��棺attrNames[i]��ȫ������, ibaDisPlayName��
          HashMap defDisplayMap = new HashMap();
          //·���������Ƽ��ϡ�������������
          Collection routeNameCol = new ArrayList();
          //·�߻��棺partIfcs[i].getBsoID() + routeNames[j], ����ֵ��
          HashMap routeMap = new HashMap();
          if(partBsoIDArray.length > 0 && attrNameArray != null)
          {
              //ѭ����ö��Ƶ��������ƣ�������������Ϊ�����������ֵ��׼����
              for(int i = 0;i < attrNameArray.length;i++)
              {
                  String attrName = attrNameArray[i];
                  if(attrName.equals("partNumber-0"))
                  {
                      partNumberSite = i;
                  }else if(attrName.endsWith("-1"))
                  {
                      //��Խ�Ŷ��ƣ����ڽ����ʷ����ԭ����Ϊ��ʾ������ͬ�ļ�Ϊ��ͬ���ԡ��������������֡���Ʒ��Ӧȡ��������
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
              //��������㲿��������IBA����ֵ��
              if(defMap.size() > 0)
              {
                  Object[] obj = defMap.values().toArray();
                  //���Զ�����ʾ���Ƽ��ϡ�
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
                  //�����ֻ����StringValueֵ����
                  query.setVisiableResult(0);
                  Collection col = pService.findValueInfo(query, false);
                  for(Iterator iterator2 = col.iterator();iterator2.hasNext();)
                  {
                      StringValueIfc valueIfc = (StringValueIfc)iterator2.next();
                      ibaMap.put(valueIfc.getIBAHolderBsoID() + defMap.get(valueIfc.getDefinitionBsoID()), valueIfc.getValue());
                  }
              }
              //��������㲿�������й���·������ֵ��
              if(routeNameCol.size() > 0)
              {
                  String[] routeNameArray = new String[routeNameCol.size()];
                  System.arraycopy(routeNameCol.toArray(), 0, routeNameArray, 0, routeNameArray.length);
                  for(int i = 0;i < partIfcArray.length;i++)
                  {
                      Vector routeVec = trService.getListAndBrances(partIfc, partIfcArray[i], routeNameArray, "");
                      if(routeVec != null && routeVec.size() > 0)
                      {
                          //routeVecֻ��һ��HashMapԪ�ء�������������ֵ������ֵ��
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
          //�������棺attrFull, method��
          HashMap methodMap = new HashMap();
          //���Ի��棺attrFull, attr��
          HashMap attrMap = new HashMap();
          //���ؽ������
          Vector resultVector = new Vector();
          for(Iterator iterator = allUsageVec.iterator();iterator.hasNext();)
          {
              //���Ԫ�ص����飺��ǰpartIfc��������š����������𡢵�λ��
              Object[] usageArray = (Object[])iterator.next();
              String partBsoID = ((QMPartIfc)usageArray[0]).getBsoID();
              //ֻ�Ƕ�������ֵ�����顣�������յķ��ؽ����
              Object[] resultArray1 = new Object[attrNameArray.length];
              for(int j = 0;j < attrNameArray.length;j++)
              {
                  String attrFull = attrNameArray[j];
                  //��û�������ֵ��
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
                  //���IBA����ֵ��
                  else if(attrFull.endsWith("-1"))
                  {
                      if(ibaMap.containsKey(partBsoID + defDisplayMap.get(attrFull)))
                          resultArray1[j] = ibaMap.get(partBsoID + defDisplayMap.get(attrFull));
                      else
                          resultArray1[j] = "";
                  }
                  //��ù���·������ֵ��
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
              //���շ��ؽ�����顣������bsoidλ�á���ǰbsoid������͸��������Ϣ��
              Object[] resultArray2 = new Object[attrNameArray.length + 4];
              System.arraycopy(resultArray1, 0, resultArray2, 3, resultArray1.length);
              //bsoidλ��
              resultArray2[0] = new Integer(partNumberSite + 3);
              //��ǰbsoid
              resultArray2[1] = partBsoID;
              //����
              resultArray2[2] = usageArray[3];
              //�������
              resultArray2[resultArray2.length - 1] = usageArray[1];
              resultVector.add(resultArray2);
          }
          //�������
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
          firstList.add("��һ���������");//CR5 ���"��һ���������"�С�
          resultVector.insertElementAt(firstList.toArray(), 0);
          return resultVector;
      }//End CR4����д������߼�

  /**
   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * liuming add 20070209
   * �޺ϼ�װ������ʾ��
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
   * �����������˵ݹ鷽����
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
   * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
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
        	//CCBegin by liunan 2008-10-13 ������λ��Ӧ������һ�С�
        	//Դ������
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
      firstElement.addElement("��һ���������");//yanqi-20061222-�ڷּ��������Ӹ�����

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
   * ��setMaterialList(QMPartIfc partIfc, String attrNames[])�������ã�ʵ�ֶ��Ʒּ������嵥�Ĺ��ܡ� 
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����飺 
   * 0����ǰpart��ֵ���� 
   * 1����ǰpart�ĸ�����ţ� 
   * 2�������� 
   * 3�����ڵļ��� 
   * 4����λ��
   * @param parentPartNum ������š�
   * @param partIfc ��ǰ�㲿����
   * @param level ���ڵļ���
   * @param partUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
   * @return Vector
   * @throws QMException
   */
  private Vector fenji(String parentPartNum, QMPartIfc partIfc, int level, PartUsageLinkIfc partUsageLinkIfc) throws QMException//Begin CR4����д������߼�
  {
      Vector resultVector = new Vector();
      //���Ԫ�ص����飺��ǰpartIfc��������š����������𡢵�λ��
      Object[] usageArray = new Object[5];
      usageArray[0] = partIfc;
      usageArray[1] = parentPartNum;
      //����������
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
      //���ü���
      usageArray[3] = new Integer(level); //level�ĳ�ʼֵΪ0��
      //���õ�λ��
      Unit unit = partUsageLinkIfc.getDefaultUnit();
      if(unit != null)
          usageArray[4] = unit.getDisplay();
      else
          usageArray[4] = "";
      resultVector.add(usageArray);
      //�õ��������Ӽ���Ϣ�������е�ÿ��Ԫ�ض���Object[2]��0��PartUsageLinkIfc��1��QMPartIfc��
      Collection collection = sort(getUsesPartIfcs(partIfc));
      if(collection == null || collection.size() == 0)
      {
          return resultVector;
      }
      Object temp[] = collection.toArray();
      level++;
      //��Ϊfenji�ĸ�����Ų�����
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
  }//End CR4����д������߼�

  /**
   * ͬ���Ӽ���Ҫ���ݱ������
   * @param collection �����е�ÿ��Ԫ�ض���Object[2]��0��PartUsageLinkIfc��1��QMPartIfc��
   * @return Vector
   */
  public Collection sort(Collection collection)//Begin CR4����д������߼�
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
  }//End CR4����д������߼�

  /**
   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * liuming add 20070209
   * ˽�з�������setMaterialList2()�������ã�ʵ�ֶ����޺ϼ�װ���Ĺ��ܡ�
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�

   * @param partIfc :QMPartIfc ��ǰ�Ĳ�����
   * @param attrNames :String[] ���Ƶ����Լ��ϣ�����Ϊ�գ�
   * @param affixAttrNames :String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames :String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
   * @param level :int ��ǰpart���ڵļ���
   * @param partLinkIfc :PartUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
   * @param parentQuantity :int ��ǰpart�ĸ��׽ڵ㱻�������ʹ�õ�������
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
    //��¼�����ͱ�������������ڵ�λ�á�
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
      tempArray = new Object[6];//yanqi-20061222 ���ӡ�������š�����
      tempArray[2] = new Integer(numberSite);//yanqi-20061222
    }
    else {
      //tempArray = new Object[4 + attrNames.length];
      tempArray = new Object[6 + attrNames.length];//yanqi-20061222���ӡ�������š�����
      tempArray[2] = new Integer(numberSite);
    }
    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
        affixAttrNames.length == 0;
    boolean routeNullTrueFlag = routeNames == null ||
        routeNames.length == 0;

    tempArray[0] = partIfc.getBsoID();
    tempArray[1] = new Integer(1);
    tempArray[3] = new Integer(level); //level�ĳ�ʼֵΪ0��

    ///////////��ǵ�ǰ����Ƿ����߼��ܳ� liuming 20070303 add
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
      tempArray[4]="true";
    }
    else
    {
      tempArray[4]="false";
    }
    ///////////////////////////////liuming 20070303 add end

    //����㲿���Ĺ���·�ߡ�
    TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
        getService("TechnicsRouteService");
    float countInparent = parentInProductCount * parentQuantity; //quxg add for jiefang �˱�������һ������ֱ�Ӹ����µ�����,����������·����ȡ��ֱ�ӻ��Ӹ����µ�ʹ������,����,��ֱ�Ӹ���Ϊ�߼��ܳ�,û��·��,�Ǿ�ȡ�߼��ܳɸ����µ�����,�������,���ܳ�����Ҫ��������

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
          String makeRoute = (String) map.get("����·��");
          if (makeRoute == null) {
            makeRoute = "";
          }
          if (makeRoute.indexOf("...") < 0) { //bushi�߼��ܳ�
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
          else { //���߼��ܳ�
            parentMakeRouteFirst = makeRoute;
          }

          if (partIfc.getPartType().toString().equalsIgnoreCase("Logical") &&
              (makeRoute != null && makeRoute.length() > 0)) {
            parentInProductCount = countInparent;
          }
          else {
            parentInProductCount = 1.0F;
          }

          String assRoute3 = (String) map.get("װ��·��");
          if (assRoute3 == null) {
            assRoute3 = "";
          }
          int o = assRoute3.indexOf("...");
          if (o > 0) {
            map.put("װ��ϼ�����", new Integer( (new Float(countInparent)).intValue()));
            String realassRoute = assRoute3.substring(0, o);
            String realParentNum = assRoute3.substring(o + 3);
            map.put("װ��·��", realassRoute);
            map.put("װ��·�ߺϼ���", realParentNum);

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
    //yanqi-20061222-���Ӹ����������
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
      QMPartIfc part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
      if(part!=null)
        tempArray[tempArray.length-1]=part.getPartNumber();
    }//yanqi��20061222
    resultVector.addElement( ( (Object[]) (tempArray)));
    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);

    /////////////////////////////////////////���� ���������

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
    //////////////////////////////////�������

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


//��������� �������嵥����ɱ����ļ�
  /**
  * �������嵥����ɱ����ļ������ڷּ���������,���߼��ܳɵ��������˴���.��
  * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
  * @param map HashMap ����õı������Լ��ϡ�
  * @throws QMException
  * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
  * @author leix 2010-12-22
  */
	//CCBegin by leix	 2010-12-20  �����߼��ܳ���������
  public String getExportBOMClassfiyLogicString(HashMap map) throws QMException {

	    StringBuffer backBuffer = new StringBuffer();
	    try
	    {
	    String PartID = (String) map.get("PartID");
	    PersistService persistService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
	    String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
	        ")" + part.getVersionValue() +
	        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
	         ("(" + part.getViewName() + ")")) +
	        "�������嵥" +
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
	      for (int j = 3; j < information.length; j++) {//CR5 �������"����"��ԭ��j=3����Ϊj=2��
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
	//CCEnd by leix	 2010-12-20  �����߼��ܳ���������
	
	//CCBegin SS7
  /**
  * �������嵥����ɱ����ļ������ڷּ���������,���߼��ܳɵ��������˴���.��
  * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
  * @param map HashMap ����õı������Լ��ϡ�
  * @throws QMException
  * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
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
	    String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
	        ")" + part.getVersionValue() +
	        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
	         ("(" + part.getViewName() + ")")) +
	        "������һ�����嵥" +
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
	      for (int j = 3; j < information.length; j++) {//CR5 �������"����"��ԭ��j=3����Ϊj=2��
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
   * �������嵥����ɱ����ļ������ڷּ�����������
   * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
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
    String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "�������嵥" +
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
      for (int j = 3; j < information.length; j++) {//CR5 �������"����"��ԭ��j=3����Ϊj=2��
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
   * �������嵥����ɱ����ļ��������޺ϼ�װ���������
   * ��com.faw_qm.part.client.other.view.LogicBomFrame�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
   * @author liunan 2008-08-01
   */
    public String getExportBOMClassfiyString2(HashMap map) throws QMException {
      StringBuffer backBuffer = new StringBuffer();
      String PartID = (String) map.get("PartID");
      PersistService persistService = (PersistService) EJBServiceHelper.
          getPersistService();
      QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
      String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
          ")" + part.getVersionValue() +
          ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
           ("(" + part.getViewName() + ")")) +
          "�������嵥" +
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

//��������� �������嵥����ɱ����ļ�
  /**
   * �������ݣ���������ÿ�е����ݣ���ֵ���á�,���ָ���������ɱ���excel�ļ�ʱ��
   * @param backBuffer StringBuffer ������������ɵ��ַ����ϡ�
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

//��������� �������嵥����ɱ����ļ�
  /**
   * �������嵥����ɱ����ļ�������ͳ�Ʊ���������
   * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
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

    String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "���㲿����ϸ��\n";
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
  //CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"
  /*
   *quxg ��� ,�����ĳ���ṹ�����µ������Ӳ���������,��Ϊ��part baoid,ֵΪpart����
   *��ĳ���������ظ����ֶ��ʱ,��������. �˷���������Ҫ�������Ӳ�������,����ļ�����������
   *�Ǽ���һ����part,����һ����part��Ҫ�������ṹ��չ��һ��,��������������partʱ,��չ�����
   *�˷����������ṹһ��չ��,����Ӧ��part���������浽hashmap��,�����������Ӳ�������ʱ,ֻ��
   *map��ȡ������,Ч�ʻ���ߺܶ�
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

//CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"


//CCBegin by liunan 2008-10-4 �򲹶�200816
    //����(1)20080811 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
	/**
	 * ����ָ���㲿������Ʒ��BsoId��ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±��ò�Ʒ��ʹ�õĽṹ��
	 * ʹ�ý�������ڷ���ֵvector�С�
	 * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
	 * @param productBsoId :String ʹ�ø��㲿���Ĳ�Ʒ��BsoId��
	 * @param configSpecIfc :PartConfigSpecIfc ָ����ɸѡ������
	 * @return vector:Vector ������������ʹ�õ���Ϣ���ϡ�
	 * vector����ֵ�����ݽṹΪ��vector�е�ÿ��Ԫ�ض���Vector���͵ģ�����Ϊ���������㣬����ΪsubVector.
	 * ��subVector��ÿ��Ԫ�ض���String[5]���͵ġ�
	 * ���String[5]�ֱ��¼��:<br>
	 * String[0]:��κţ�<br>
	 * String[1]:�㲿�����(�㲿������)�汾(��ͼ);<br>
	 * String[2]:�㲿���ڴ˽ṹ��(��������)ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ģ��㲿����ͬһ�Ӽ���ʹ�������ϲ���<br>
	 * String[3]:�㲿����BsoId��<br>
	 * String[4]:�㲿��ֵ����<br>
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
		//��Ҫ��vector1���д������vector1��ֻ��һ��Ԫ�أ�������partIfc�������vector1:
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
	//����(1)20080811 zhangq end
	//CCEnd by liunan 2008-10-4
	
	//CR1��д������߼�     ���·�����Ϊ�Ż���������嵥��ӵ�˽�з��� 
	/**
	 * �Լ����е��㲿����IBA����ȡֵ����ͨ����¼��λ����Ϣ�������鶨λ��
	 * @param refreshIBAMap HashMap �����ˢ��IBA����
	 * @param ibaPositionMap HashMap IBA����λ��
	 * @param countMap HashMap  ����
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
//	    temp.add( ( ( (String) iter.next()).split(";"))[0]); //ȡ�����ID������
	   tempString[i] =  (( (String) iter.next()).split(";"))[0];
	  
	   i++;
	    if (i == 499 || !iter.hasNext()) { //��һ��Ϊ��λ������ֹ���ݿ����



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
	                                            getDefinitionBsoID()); //������IBA����
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
	 * ͨ��iba���Զ�������ƻ�ȡ�����bsoID
	 * @param defName String
	 * @return String iba���Զ���� bsoID
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
     * ��ȡ��ǰ�û��ġ���ʾ���á���Ϣ
     * @return ����ʾ���á�ֵ����
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
    	 * ��ȡָ�������EPM�ĵ�
    	 * @param baseIfc
    	 * @return EPM�ĵ�ֵ���󼯺�
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
    	 * �ּ������嵥����ʾ�� �����������˵ݹ鷽���� fenji(QMPartIfc partIfc, String[] attrNames,
    	 * PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int
    	 * parentQuantity);
    	 * 
    	 * @param partIfc
    	 *            :QMPartIfc ����Ĳ���ֵ����
    	 * @param attrNames
    	 *            :String[] ���Ƶ����ԣ�����Ϊ�ա�
    	 * @param affixAttrNames :
    	 *            String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
    	 * @param configSpecIfc
    	 *            :PartConfigSpecIfc ���ù淶��
    	 * @throws QMException
    	 * @see QMPartInfo
    	 * @see PartConfigSpecInfo
    	 * @return Vector ����vector�е�ÿ��Ԫ�ض���һ�����ϣ�<br>
    	 *         String[0]����ǰpart��BsoID��<br>
    	 *         String[1]����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�<br>
    	 *         2-...���ɱ�ģ����û�ж�������:<br>
    	 *         String[2]����ǰpart�ı�ţ�<br>
    	 *         String[3]����ǰpart������,<br>
    	 *         String[4]����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�<br>
    	 *         String[5]����ǰpart�İ汾�ţ�<br>
    	 *         String[6]����ǰpart����ͼ��<br>
    	 *         ������������ԣ��������ж��Ƶ����Լӵ���������С�
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
    	 * ��ȡpart���滻���ı�ţ����ƣ����ִ���ʽ����
    	 * 
    	 * @param part
    	 *            �㲿����
    	 * @return �����ı�ţ����ƣ�
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
    	 * ��ȡpart�Ľṹ�滻���ı�ţ����ƣ����ִ���ʽ����
    	 * 
    	 * @param part
    	 *            �㲿����
    	 * @return �ṹ�����ı�ţ����ƣ�
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
	 * ͨ��masterid��ȡ���°汾���㲿����
	 * @param masterID masterid
	 * @return QMPartInfo ���°汾���㲿��(QMPart);
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
			// ��õ�ǰ�û����ù淶
			PartConfigSpecIfc configSpec = findPartConfigSpecIfc();
			// ���master�ڵ�ǰ���ù淶�µ����
			// QMPartIfc partIfc = partService.getPartByConfigSpec(master, configSpec);
			if(part == null)
			{
				return flag;
			}
			// ���part��ʹ�õ���һ�����������Ϣ�ļ���
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
		// ��ȡ���㲿���ĵ�һ���汾
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
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
	   * ������������bianli()����ʵ�ֵݹ顣
	   * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
	   * 1��������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
	   * 2������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

	   * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
	   * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
	   * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
	   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
	   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
	   * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
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
	    //�����vector�е�Ԫ�ؽ��кϲ������Ĵ���...........
	    Vector resultVector = new Vector();
	    int vectorSize = vector.size();
	    for (int i = 0; i < vectorSize; i++) {
	      Object[] temp1 = (Object[]) vector.get(i);
	      //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�temp1�е�ÿ��Ԫ���ж�
	      //���Ƿ�Ϊnull, �����null����ת��Ϊ""
	      for (int j = 0; j < temp1.length; j++) {
	        if (temp1[j] == null) {
	          temp1[j] = "";
	        }
	      }
	      //�����ǰ���partNumber���кϲ��ģ�����
	      String partNumber1 = (String) temp1[3];
	      boolean flag = false;
	      for (int j = 0; j < resultVector.size(); j++) {
	        Object[] temp2 = (Object[]) resultVector.elementAt(j);
	        String partNumber2 = (String) temp2[3];
	        if (partNumber1.equals(partNumber2)) {
	          flag = true;
	          //��temp2��temp1�е�Ԫ�ؽ��кϲ����ŵ�resultVector��ȥ��:::
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
	    //��Ҫ�Ե�һ��Ԫ�ؽ����жϣ�����䣬source��type���������source, type��ͬ��
	    //�ͱ���������ɾ������
	    //��ʵ����partIfc������:::
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
	      //�ѵ�һ��Ԫ�ص������ĳ�""
	      Object[] firstObj = (Object[]) resultVector.elementAt(0);
	      firstObj[6] = "";
	      resultVector.setElementAt(firstObj, 0);
	    }
	    //����ű���������Ľ����
	    ArrayList result = new ArrayList();
	    //Ȼ�����ﻹ��Ҫ�����ķ���ֵ���ϰ��յ�ǰ��source��type���й��ˣ�
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
	    //����Ҫ���vector�����һ��Ԫ�أ�
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
	    //������Ҫͨ���ж���ȷ��firstElement��ֵ:
	    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
	    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
	        affixAttrNames.length == 0;
	    //������Ƶ���ͨ����Ϊ�գ�
	    if (attrNullTrueFlag) {
	      //������Ƶ���չ����ҲΪ�գ�
	      if (affixAttrNullTrueFlag) {
	        ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
	        firstElement.addElement(ssss);
	        ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
	        firstElement.addElement(ssss);
	      }
	    }
	    //������Ƶ���ͨ���Բ�Ϊ�յĻ���
	    else {
	      for (int i = 0; i < attrNames.length; i++) {
	        String attr = attrNames[i];
	        ssss = QMMessage.getLocalizedMessage(RESOURCE, attr, null);
	        firstElement.addElement(ssss);
	      }
	    }
	    //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
	    //added by dikef for export iba attribute
	    if (!affixAttrNullTrueFlag) {
	      //added by  dikef  for  export iba attribute
	      for (int i = 0; i < affixAttrNames.length; i++) {
	        String affixName = affixAttrNames[i];
	        firstElement.addElement(wfutil.getDisplayName(affixName));
	      }
	    }
	    //����й���·�ߣ�����ӡ�������ӵ������ơ�
	    if (routeNames != null && routeNames.length > 0) {
	      for (int i = 0; i < routeNames.length; i++) {
	        firstElement.addElement(routeNames[i]);
	      }
	    }

	    //����ӵ�vector�еĵ�һ��λ�ã�
	    Object[] tempArray = new Object[firstElement.size()];
	    for (int i = 0; i < firstElement.size(); i++) {
	      tempArray[i] = firstElement.elementAt(i);
	    }
	    result.add(0, tempArray);
	    return result;
	  }
	
	  
	  /**
	   * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ�
	   * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
	   * 1��������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
	   * 2������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

	   * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
	   * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
	   * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Եļ��ϣ�����Ϊ�ա�
	   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
	   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
	   * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
	   * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
	   * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ����
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
	    //����������Ҫʵ�ֹ���Ϊ:::
	    //1���жϵ�ǰ���㲿���Ƿ��ǿɷֵ��㲿�����Է����ڰѸ��㲿���ŵ�����������е�ʱ�򣬿���ȷ��
	    //���㲿���Ŀɷֱ�־
	    PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli begin ....");
	    //ArrayList resultVector = new ArrayList();
	    //added by dikef  for export iba attribute
	    //WfUtil wfutil = new WfUtil();
	    //�������浱ǰ���㲿�������кϸ�����㲿���ļ��ϣ�
	    ArrayList hegeVector = new ArrayList();
	    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
	    //2006-7-11 by lilei xiugai begin
	    String isHasSubParts1 = "��";
	    if (collection != null && collection.size() > 0) {
	      Iterator iterator = collection.iterator();
	      while (iterator.hasNext()) {
	        Object obj = iterator.next();
	        Object[] objs = (Object[]) obj;
	        if (objs[1] instanceof QMPartInfo) {
	          hegeVector.add(obj);
	          isHasSubParts1 = "��";
	        }
	      }
	    } //2006-7-11 by lilei xiugai end
	    //���ʱ���Ӧ�����ж�collection�Ƿ���"null"
	    /*if (collection != null && collection.size() > 0) {
	      //��Ҫ��collection�е�����Ԫ�ؽ���ѭ���������������Ԫ��
	      //��QMPartIfc��������Դ�����ͺ�����Ĳ�����һ�µģ�
	      //������������㲿���ǿɷֵ�.���Ǹ���source, type�����ӽڵ���й���:::
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
	    //�ѱ�part->resultVector��;
	    Object[] tempArray = null;
	    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
	    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
	        affixAttrNames.length == 0;
	    boolean routeNullTrueFlag = routeNames == null || routeNames.length == 0;
	    try {
	      if (attrNullTrueFlag) {
	        //����������Ƶ����Լ��϶�Ϊ�յ�ʱ��
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
	        //������Ƶ���ͨ����Ϊ�գ������Ƶ���չ���Բ�Ϊ�յ�ʱ��
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
	        //������Ƶ���ͨ���Լ��ϲ�Ϊ�գ����Ƶ���չ���Լ���Ϊ�յ�ʱ��
	        if (affixAttrNullTrueFlag) {
	          if (routeNames == null || routeNames.length == 0) {
	            tempArray = new Object[7 + attrNames.length];
	          }
	          else {
	            tempArray = new Object[7 + attrNames.length + routeNames.length];
	          }
	          tempArray[2] = new Integer(7 + attrNames.length - 1);
	        }
	        //����������Ƶ����Լ��϶���Ϊ�յ�ʱ��
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
	    //����tempArray���飬����������������
	    tempArray[0] = partIfc.getBsoID();
	    tempArray[1] = new Integer(1);
	    tempArray[3] = partIfc.getPartNumber();
	    tempArray[4] = partIfc.getPartName();
	    tempArray[5] = isHasSubParts1;
	    //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
	    //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
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
	    //������Ҫ�����������Ƶ����Լ����������Ľ�����Ͻ�����֯��
	    if (attrNullTrueFlag) {
	      //���������Ƶ����Լ��϶�Ϊ�յ�ʱ��
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
	      //�����������Ƶ���ͨ����Ϊ�գ������Ƶ���չ���Լ��ϲ�Ϊ�յ�ʱ��
	    }
	    //��������������Ƶ���ͨ���Լ���Ϊ�յ�ʱ��
	    //���濪ʼ�������Ƶ���ͨ���Լ��ϲ�Ϊ�յ�ʱ��
	    else {
	      //�Ȱ����е���ͨ���Ե�ֵ�ŵ�tempArray�У�
	      for (int i = 0; i < attrNames.length; i++) {
	        String attr = attrNames[i];
	        attr = attr.trim();
	        if (attr != null && attr.length() > 0) {
	          //modify by liun 2005.3.25 ��Ϊ�ӹ����еõ���λ
	          if (attr.equals("defaultUnit")) {
	            Unit unit = partLinkIfc.getDefaultUnit();
	            tempArray[7 + i] = unit != null ? unit.getDisplay() : "";
	          }
	          else {
	            attr = (attr.substring(0, 1)).toUpperCase() +
	                attr.substring(1, attr.length());
	            attr = "get" + attr;
	            //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
	            try {
	              Class partClass = Class.forName(
	                  "com.faw_qm.part.model.QMPartInfo");
	              Method method1 = partClass.getMethod(attr, null);
	              Object obj = method1.invoke(partIfc, null);
	              //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
	              //���obj��Ϊnull, ������String, tempArray[i + 5] = (String)obj;
	              //���obj��ö�����ͣ�tempArray[i + 5] = (EnumerationType)obj.getDisplay();
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
	    //����㲿���Ĺ���·�ߡ�
	    //���·��ѡ��Ϊ��
	    if (!routeNullTrueFlag) {
	      //��������·��
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
	        //���ÿһ��·��
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
	    //���Ѿ����˴���ĵ�ǰ������㲿�����������㲿�����еݹ鴦��::::
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
