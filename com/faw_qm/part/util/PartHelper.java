/** ���ɳ��� PartHelper.java    1.0    2003/02/24
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/06/19 л�� ԭ�򣺻������Խ��桰״̬����û�ж��롣TD-2233
 *                     �����������������ں���״̬ʹ�õ���һ����壬����Ԫ�ر仯ʱ�޷�����ͳһ��Ӧ���ָ�Ϊ״̬Ϊ������壬����ͳһ��Ӧ����仯��
 * CR2 2009/10/23  ���  ԭ�򣺲���EJB�ĵط���Ϊ����ֵ����  
 * CR3 2009/10/27  ��� ԭ��:ͳһ�ͻ��˻򹤾�����÷���ķ�ʽ   
 * CR4 2010/02/09  ���� ԭ��TD2142��ȥ����ʾ��Ϣ��  
 * CR5 2010/04/01  ���� ԭ��TD3179����ĳЩ�ĵ���������ҳ������㲿���޷���ӣ����쳣ҳ��  
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
 * �ͻ����㲿����װ��ĸ����ࡣ
 * @author ���ȳ�
 * @version 1.0
 */
public class PartHelper
{
    /**�󶨵���Դ��Ϣ*/
    private static ResourceBundle resource = null;
    static String resource1 = "com.faw_qm.part.util.PartResource";

    /**��Դ��Ϣ·��*/
    private static String RESOURCE = "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**
     * ���캯����
     */
    public PartHelper()
    {}

    /**
     * ��ʼ����Դ��Ϣ��
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
     * ��ȡ��Դ��Ϣ��
     * @return ResourceBundle �󶨵���Դ��Ϣ��
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
     * �����㲿���ı�ʶbsoID��ѯ���ݿ⣬��ȡ�㲿��ֵ����
     * @param s :String �㲿��BsoID��
     * @throws QMException 
     * @
     * @return QMPartIfc ���ݱ�ʶbsoID�õ����㲿��ֵ����
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
     * ���ݳ־û���Ϣˢ�����ݿ⡣
     * ���ظ�������ֵ����
     * @param baseValueIfc BaseValueIfc ���и��µ�ֵ����
     * @return Object ���º��ֵ����
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
     * ����������㲿��ֵ��������������ݿ���и��£����ظ��º���㲿��ֵ����
     * @param qmPartIfc QMPartIfc ������㲿��ֵ���������
     * @return QMPartIfc ���º���㲿��ֵ����
     * @throws QMException 
     * @
     */
    public static QMPartIfc refresh(QMPartIfc qmPartIfc) throws QMException 
    {
        //����ֵ������Ϣ�������ݿ��еļ�¼��
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
     * ��ȡ�㲿�����ù淶��
     * @throws QMException 
     * @
     * @return PartConfigSpecIfc ��ǰ���㲿�����ù淶��
     */
    public static PartConfigSpecIfc getConfigSpec() throws QMException 
    {
        PartConfigSpecIfc partConfigSpecIfc = null;
            partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
        return partConfigSpecIfc;
    }

    /**
     * �����㲿�������ù淶��
     * ����savePartConfigSpec(PartConfigSpecIfc configSpecIfc)������
     * @param partConfigSpecIfc PartConfigSpecIfc ���õ��㲿�����ù淶��
     * @throws QMException 
     * @
     * @return PartConfigSpecIfc ���úõ��㲿�����ù淶��
     */
    public static PartConfigSpecIfc setConfigSpec(PartConfigSpecIfc partConfigSpecIfc) throws QMException 
    {
            partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.savePartConfigSpecIfc(partConfigSpecIfc);
        return partConfigSpecIfc;
    }

    /**
     * ��ȡ�㲿����״̬���ϡ�
     * @
     * @return LifeCycleState[] ״̬���ϡ�
     */
    public static LifeCycleState[] getStates() 
    {
        LifeCycleState astate[] = LifeCycleState.getLifeCycleStateSet();
        return astate;
    }

    /**
     * ����û�Ȩ�ޣ���ȡ���пɼ�����ͼ��
     * @throws QMException 
     * @
     * @return ViewObjectIfc[] ��ͼֵ���󼯺ϡ�
     */
    public static ViewObjectIfc[] getViews() throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getViews() begin ....");

        //��Ҫ����ViewService�еķ�������ȡ������ͼ��
        //�������͵ļ��ϡ�
        Class[] paraClass = {};

        //����ֵ�ļ��ϡ�
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
     * ��ȡ�㲿���Ͳο��ĵ�������ϵ�ļ��ϡ�
     * @param partIfc QMPartIfc Ŀ���㲿��ֵ����
     * @return PartReferenceLinkIfc[] ������ֵ���󼯺ϡ�
     * @throws QMException 
     * @
     */
    public static PartReferenceLinkIfc[] getReferences(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getReferences() begin ....");
        PartReferenceLinkIfc partreferencelinkifc[] = null;

        //����������Ĳ����Ѿ��ǳ־û����ˣ��ż�������Ĳ�����
        if(PersistHelper.isPersistent(partIfc))
        {
            //����StructService�еķ������Ҳο���ϵ,navigateReferences
            //(IteratedIfc iteratedIfc) :Collection��
            //�������͵ļ��ϡ�
            Class[] paraClass = {IteratedIfc.class, Boolean.TYPE};

            //����ֵ�ļ��ϡ�
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
     * ��ȡ���㲿������Щ������ʹ�õ�"ʹ�ù�ϵ"�ļ��ϡ�
     * @param partIfc :QMPartIfc �㲿��ֵ����
     * @return PartUsageLinkIfc[] ʹ�ù�ϵ�ļ��ϡ�
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc[] getUsedBy(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsedBy() begin ....");
        PartUsageLinkIfc partusagelinkifc[] = null;
        QMPartMasterIfc partmasterifc = (QMPartMasterIfc)partIfc.getMaster();

        //����StructService�е�navigateUsedBy��������ȡʹ�ù�ϵ�ļ��ϡ�
        //navigateUsedBy(QMPartMasterIfc, false):Collection��
        //�������͵ļ��ϡ�
        Class[] paraClass = {MasterIfc.class, Boolean.TYPE};

        //����ֵ�ļ��ϡ�
        Object[] objs = {partmasterifc, new Boolean(false)};
            Collection tempCollection = (Collection)RequestHelper.request("StructService", "navigateUsedBy", paraClass, objs);
            //            Collection tempCollection = (Collection) IBAUtility.
            //            invokeServiceMethod("StructService",
            //"navigateUsedBy", paraClass, objs);
            partusagelinkifc = new PartUsageLinkIfc[tempCollection.size()];

            //������Ҫ��tempCollectionת��ΪPartUsageLinkIfc[]���͡�
            Iterator iterator = tempCollection.iterator();
            for(int i = 0, j = tempCollection.size();i < j;i++)
            {
                partusagelinkifc[i] = (PartUsageLinkIfc)iterator.next();
            }
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsedBy() " + "end....return is PartUsageLinkIfc[]");
        return partusagelinkifc;
    }

    /**
     * �����µ��㲿�����ù淶��
     * @param flag :boolean
     * @param state :LifeCycleState ��������״̬��
     * @param viewObjectIfc :ViewObjectIfc ��ͼֵ����
     * @return PartConfigSpecInfo �㲿�����ù淶ֵ����
     * @
     */
    public static PartConfigSpecInfo newConfigSpec(boolean flag, LifeCycleState state, ViewObjectIfc viewObjectIfc) 
    {
        return new PartConfigSpecInfo();
    }

    /**
     * �������ù淶��ѯ��ǰ�㲿��ʹ������Щ�㲿����
     * @param partIfc :QMPartIfc �㲿��ֵ����
     * @param configSpecIfc :PartConfigSpecIfc �㲿�����ù淶ֵ����
     * @throws QMException 
     * @
     * @return Explorable[] ʹ�õ��㲿��ֵ���󼯺ϡ�
     */
    public static Explorable[] getUses(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getUses begin ....");
        Explorable aexplorable[] = null;

        //�������͵ļ��ϡ�
        Class[] paraClass = {IteratedIfc.class, Boolean.TYPE, ConfigSpec.class};

        //����ֵ�ļ��ϡ�
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
     * ��ȡָ���㲿����PartUsageLinkIfc�ļ��ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����
     * @return PartUsageLinkIfc[] �㲿��ʹ�ù�ϵ�ļ��ϡ�
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc[] getUsesInterface(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getUsesInterface() begin ....");
        PartUsageLinkIfc partusagelinkifc[] = null;

        //������StructService�е�navigateUses(IteratedIfc, boolean)������
        //�������͵ļ��ϡ�
        Class[] paraClass = {IteratedIfc.class, Boolean.TYPE};

        //����ֵ�ļ��ϡ�
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
     * �����㲿����
     * @param partIfc :QMPartIfc �㲿��ֵ����
     * @exception QMRemoteException
     * @return QMPartIfc �㲿��ֵ����
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
     * ɾ���㲿����
     * @param partIfc :QMPartIfc ��ɾ�����㲿����
     * @throws QMException 
     * @exception QMRemoteException
     */
    public static void deletePart(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePart() begin ....");

        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
     * �޶��㲿����
     * @param partIfc :QMPartIfc ���޶����㲿����
     * @exception QMRemoteException
     * @return QMPartIfc �޶�����㲿��ֵ����
     * @throws QMException 
     */
    public static QMPartIfc revisePart(QMPartIfc partIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "revisePart() begin ....");

        //����VersionControlService��newVersion(VersionedIfc):VersionedIfc��
        //�������͵ļ��ϡ�
        Class[] paraClass = {VersionedIfc.class};

        //����ֵ�ļ��ϡ�
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
     * ɾ��ָ�����㲿���Ͳο��ĵ��Ĺ�����ϵ��
     * @param partreferencelinkifc :PartReferenceLinkIfc Ҫɾ�����㲿���Ͳο��ĵ�
     * �Ĺ���ֵ��ϵ����
     * @throws QMException 
     * @
     */
    public static void deletePartReferenceLink(PartReferenceLinkIfc partreferencelinkifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePartReferenceLink() begin ....");

        //����PersistService�е�deleteValueInfo(BaseValueIfc):BaseValueIfc������
        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
     * �����㲿���Ͳο��ĵ��Ĺ�����ϵ��
     * @param partReferenceLinkIfc :PartReferenceLinkIfc �㲿���Ͳο��ĵ��Ĺ�����ϵ��
     * @return PartReferenceLinkIfc �㲿���Ͳο��ĵ��Ĺ�����ϵֵ����
     * @throws QMException 
     * @
     */
    public static PartReferenceLinkIfc savePartReferenceLink(PartReferenceLinkIfc partReferenceLinkIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "savePartReferenceLink() begin ....");

        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
     * �����㲿�����ʹ�ù�ϵ��
     * @param partusagelinkifc :PartUsageLinkIfc �㲿��ʹ�ù�ϵֵ����
     * @return PartUsageLinkIfc �㲿��ʹ�ù�ϵֵ����
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc saveUsageLink(PartUsageLinkIfc partusagelinkifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "---------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, "����Ĳ���Ϊ����partusagelinkifc��:::");
        PartDebug.trace(PartDebug.PART_CLIENT, "   " + partusagelinkifc.getLeftBsoID() + "   " + partusagelinkifc.getRightBsoID() + "  quantity: " + partusagelinkifc.getQuantity() + "   Unit:"
                + partusagelinkifc.getDefaultUnit());
        PartDebug.trace(PartDebug.PART_CLIENT, "--------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, "saveUsageLink() begin ....");

        //��Ҫ����PersistService�е�saveValueInfo(BaseValueIfc):BaseValueIfc��
        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
        PartDebug.trace(PartDebug.PART_CLIENT, "�������ǣ�partusagelinkifc��:::");
        PartDebug.trace(PartDebug.PART_CLIENT, "   " + partusagelinkifc.getLeftBsoID() + "   " + partusagelinkifc.getRightBsoID() + "  quantity: " + partusagelinkifc.getQuantity() + "   Unit:"
                + partusagelinkifc.getDefaultUnit());
        PartDebug.trace(PartDebug.PART_CLIENT, "-----------------------------------------------------------------");
        return partusagelinkifc;
    }

    /**
     * ɾ���㲿�����ʹ�ù�ϵ��
     * @param partusagelinkifc :PartUsageLinkIfc ��������㲿����ʹ�ù�ϵ��
     * @throws QMException 
     * @
     */
    public static void deleteUsageLink(PartUsageLinkIfc partusagelinkifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deleteUsageLink() begin ....");

        //����PersistService�е�deleteValueInfo(BaseValueIfc):BaseValueIfc������
        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
     * ����ָ�����㲿�����ʹ�ù�ϵ��
     * @param partUsageLinkIfc :PartUsageLinkIfc ָ�����㲿�����ʹ�ù�ϵ��
     * @return PartUsageLinkIfc  ָ�����㲿���䱣����ʹ�ù�ϵ��
     * @throws QMException 
     * @
     */
    public static PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc partUsageLinkIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "savePartUsageLink() begin ....");

        //����PersistService�е�saveValueInfo(BaseValueIfc):BaseValueIfc������
        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
     * ɾ��ָ�����㲿�����ʹ�ù�ϵ��
     * @param partUsageLinkIfc :PartUsageLinkIfc ָ�����㲿�����ʹ�ù�ϵ��
     * @throws QMException 
     * @
     */
    public static void deletePartUsageLink(PartUsageLinkIfc partUsageLinkIfc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "deletePartUsageLink() begin ....");

        //����PersistService�е�deleteValueInfo(BaseValueIfc):BaseValueIfc������
        //�������͵ļ��ϡ�
        Class[] paraClass = {BaseValueIfc.class};

        //����ֵ�ļ��ϡ�
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
     * ��ȡָ���㲿���������������ơ�
     * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
     * @throws QMException 
     * @
     * @return String ���㲿���������������ơ�
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
     * ��ȡ�㲿������Ŀ�顣
     * @param partIfc QMPartIfc ָ�����㲿��ֵ����
     * @return String ���㲿������Ŀ�����ơ�
     * @
     */
    public static String getProject(QMPartIfc partIfc) 
    {
        return partIfc.getProjectName();
    }

    /**
     * ��ȡָ�����㲿������������״̬��
     * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
     * @
     * @return String ���㲿������������״̬��
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
     * �����㲿������Ϣ�����ù淶����ȡ���а汾���㲿��ֵ����
     * ����ֵVector��Object[]�ļ��ϣ���Vector�е�ÿ��Ԫ�ض���Object[]��ʽ��
     * ������ÿ��Object[]��Ӧ��ÿ�������QMPartMasterIfc[]��ÿ��QMPartMasterIfcԪ������Ӧ��
     * ���з������ù淶���㲿��ֵ����
     * @param partmasterIfc :QMPartMasterIfc[] �㲿������Ϣֵ����ļ��ϡ�
     * @param partconfigspecifc :PartConfigSpecIfc �㲿�����ù淶ֵ����
     * @return Vector ָ���㲿������Ϣ�����а汾���㲿��ֵ���󼯺ϡ�
     * @throws QMException 
     * @
     */
    public static Vector getAllVersions(QMPartMasterIfc partmasterIfc[], PartConfigSpecIfc partconfigspecifc) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersions() begin ....");
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, " ����Ĳ���Ϊ��  ");
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            PartDebug.trace(PartDebug.PART_CLIENT, "     " + partmasterIfc[i]);
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "������㲿�����ù淶Ϊ��");
        PartEffectivityConfigSpec effConfigSpec = partconfigspecifc.getEffectivity();
        if(null != effConfigSpec)
        {
            ViewObjectIfc viewObjectIfc = effConfigSpec.getViewObjectIfc();
            if(null != viewObjectIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "   ��ͼ���ƣ�   " + viewObjectIfc.getViewName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "��Ч�����ù淶����ͼΪ�գ�");
            }
            QMConfigurationItemIfc effItemIfc = effConfigSpec.getEffectiveConfigItemIfc();
            if(null != effItemIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "���ù淶����Ч�������" + effItemIfc.getConfigItemName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "���ù淶����Ч��������Ϊ�գ�");
            }
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");
        Vector result = new Vector();
        Vector tempResult = new Vector();
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            tempResult.add(partmasterIfc[i]);
        }
        //���˷������ù淶���㲿��С�汾��
        //����ConfigService�е�filteredIterationsOf(Collection, ConfigSpec):Collection��
        //��ԭ���ĵ��÷���ConfigService.filteredIterationsOf�޸�Ϊ��filterIterationsOf()��
        //�������͵ļ��ϡ�
        Class[] paraClass = {Collection.class, ConfigSpec.class};

        //����ֵ�ļ���
        Object[] objs = {(Collection)tempResult, (ConfigSpec)(new PartConfigSpecAssistant(partconfigspecifc))};
            //            Collection collection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "ConfigService", "filteredIterationsOf", paraClass, objs);
            Collection collection = (Collection)RequestHelper.request("ConfigService", "filteredIterationsOf", paraClass, objs);
            if((null == collection) || (collection.size() == 0))
            {
                //�׳��쳣��������ֱ�ӷ��ؿա�
                return new Vector();
            }
            //��Ҫ��collection�е�����Ԫ�ض��ŵ�result������
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
        PartDebug.trace(PartDebug.PART_CLIENT, " ����ֵΪ������  ");
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
     * �����㲿������Ϣ��ȡ���㲿�������з������ù淶�İ汾��
     * ����ֵVector�Ǻ���һ��Ԫ��(��Ԫ��ΪObject[]����)�����飬
     * ���Object[]�е�����Ԫ�ض���QMPartIfc���͵ģ�
     * ���Ƕ�Ӧͬһ��QMPartMasterIfc����������Ĳ�����
     * @param partmasterIfc :QMPartMasterIfc �㲿������Ϣֵ����
     * @param partconfigspecIfc :PartConfigSpecIfc �㲿�����ù淶��
     * @return Vector ����Ҫ��İ汾���㲿���ļ��ϡ�
     * @throws QMException 
     * @
     */
    public static Vector getAllVersions(QMPartMasterIfc partmasterIfc, PartConfigSpecIfc partconfigspecIfc) throws QMException 
    {
        QMPartMasterIfc partmasterIfc1[] = {partmasterIfc};
        return getAllVersions(partmasterIfc1, partconfigspecIfc);
    }

    /**
     * �����㲿������Ϣ���Ϻ����ù淶����ȡÿ���㲿������Ϣ��Ӧ�ķ������ù淶�����°汾���㲿����
     * ����ֵ��Hashtable����Hashtable��������key:"part", "partmaster", �ֱ��Ӧ��
     * ����Vector�������ҵ����㲿���ļ��ϣ���û���ҵ����ʰ汾��QMPartMasterIfc�ļ��ϡ�
     * @param partmasterIfc :�㲿������Ϣֵ����ļ��ϡ�
     * @param partconfigspecifc :�㲿�����ù淶ֵ����
     * @return Hashtable �������ù淶���㲿�����ϡ�
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
     * �����㲿������Ϣ���Ϻ����ù淶�����isLatestΪtrue����ȡÿ���㲿������Ϣ��Ӧ�ķ������ù淶�����°汾���°�����㲿����
     * ���isLatestΪfalse����ȡÿ���㲿������Ϣ��Ӧ�ķ������ù淶�ĸ�����汾���°�����㲿����
     * ����ֵ��Hashtable����Hashtable��������key:"part", "partmaster", �ֱ��Ӧ��
     * ����Vector�������ҵ����㲿���ļ��ϣ���û���ҵ����ʰ汾��QMPartMasterIfc�ļ��ϡ�
     * @param partmasterIfc :�㲿������Ϣֵ����ļ��ϡ�
     * @param partconfigspecifc :�㲿�����ù淶ֵ����
     * @param isLatest :���°汾�ı�־��
     * @return Hashtable �������ù淶���㲿�����ϡ�
     * @throws QMException 
     * @
     */
    public static Hashtable getAllVersionsNow(QMPartMasterIfc partmasterIfc[], PartConfigSpecIfc partconfigspecifc, boolean isLatest) throws QMException 
    {
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersionsNow() begin ....");
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");
        PartDebug.trace(PartDebug.PART_CLIENT, " ����Ĳ���Ϊ��  ");
        for(int i = 0, j = partmasterIfc.length;i < j;i++)
        {
            PartDebug.trace(PartDebug.PART_CLIENT, "     " + partmasterIfc[i]);
        }
        PartDebug.trace(PartDebug.PART_CLIENT, "������㲿�����ù淶Ϊ��");
        PartEffectivityConfigSpec effConfigSpec = partconfigspecifc.getEffectivity();
        if(null != effConfigSpec)
        {
            ViewObjectIfc viewObjectIfc = effConfigSpec.getViewObjectIfc();
            if(null != viewObjectIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "   ��ͼ���ƣ�   " + viewObjectIfc.getViewName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "��Ч�����ù淶����ͼΪ�գ�");
            }
            QMConfigurationItemIfc effItemIfc = effConfigSpec.getEffectiveConfigItemIfc();
            if(null != effItemIfc)
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "���ù淶����Ч�������" + effItemIfc.getConfigItemName());
            }else
            {
                PartDebug.trace(PartDebug.PART_CLIENT, "���ù淶����Ч��������Ϊ�գ�");
            }
        } //end if (null != effConfigSpec)
        PartDebug.trace(PartDebug.PART_CLIENT, "----------------------------------------------");

        //�����м���ֵ��
        Vector result = new Vector();
        Vector tempResult = new Vector();

        //����һ��Collection������Vector��Ϊ����filterIterationsOf�Ĳ�����
        for(int i = 0;i < partmasterIfc.length;i++)
        {
            tempResult.add(partmasterIfc[i]);

        }
        //���˷������ù淶���㲿��С�汾��
        //����ConfigService�е�filteredIterationsOf(Collection, ConfigSpec):Collection��
        //��ԭ���ĵ��÷���ConfigService.filterIterationsOf�޸�Ϊ��filterIterationsOf()��
        //�������͵ļ��ϡ�
        Class[] paraClass = {Collection.class, ConfigSpec.class};

        //����ֵ�ļ��ϡ�
        Object[] objs = {(Collection)tempResult, (ConfigSpec)(new PartConfigSpecAssistant(partconfigspecifc, isLatest))};
            //            Collection collection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "ConfigService", "partFilterIterationsOf", paraClass, objs);
            Collection collection = (Collection)RequestHelper.request("ConfigService", "filterIterationsOf", paraClass, objs);

            //��Ҫ��collection�е�����Ԫ�ض��ŵ�result������
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
        //������Ҫ��result�����е�Ԫ�ؽ��д���
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

        //��װ���ķ���ֵ��
        Hashtable hashtable = new Hashtable();
        hashtable.put("part", partVector);
        hashtable.put("partmaster", partMasterVector);
        PartDebug.trace(PartDebug.PART_CLIENT, "getAllVersionsNow()" + " end....return is Hashtable");

        //������λ��
        partVector = null;
        partMasterVector = null;
        return hashtable;
    }

    /**
     * �����㲿������Ϣ�����ù淶����ȡ���а汾���㲿��ֵ����
     * ����ֵVector��Object[]�ļ��ϣ���Vector�е�ÿ��Ԫ�ض���Object[]��ʽ��
     * ������ÿ��Object[]��Ӧ��ÿ�������QMPartMasterIfc[]��ÿ��QMPartMasterIfcԪ������Ӧ��
     * ���з������ù淶���㲿��ֵ����
     * @param partmasterIfc :QMPartMasterIfc[] �㲿������Ϣֵ����ļ��ϡ�
     * @param partconfigspecifc :PartConfigSpecIfc �㲿�����ù淶ֵ����
     * @return Vector �������ù淶���㲿��ֵ���󼯺ϡ�
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

        //���˷������ù淶���㲿��С�汾��
        //����ConfigService�е�cappFilteredIterationsOf(Collection, ConfigSpec):Collection��
        //�������͵ļ��ϡ�
        Class[] paraClass = {Collection.class, ConfigSpec.class};

        //����ֵ�ļ��ϡ�
        Object[] objs = {(Collection)paramsForCapp, (ConfigSpec)(new PartConfigSpecAssistant(partconfigspecifc))};
            //            Collection collection = (Collection) IBAUtility.invokeServiceMethod(
            //                    "ConfigService", "cappFilterIterationsOf", paraClass, objs);
            Collection collection = (Collection)RequestHelper.request("ConfigService", "cappFilterIterationsOf", paraClass, objs);

            //��Ҫ��collection�е�����Ԫ�ض��ŵ�result������
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

        //������λ��
        partVector = null;
        partMasterVector = null;

        return hashtable;
    }

    /**
     * ���������ʱ�����������ȡ��"��-��-��"���֡�
     * @param timestamp �����ʱ�����
     * @return String �ַ�����ʽ��"��-��-��"��
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
     * ��ȡָ���������ָ�����Ե���󳤶ȡ�
     * @param class1 ָ�����ࡣ
     * @param s ָ�����ָ�����ԡ�
     * @return int ��󳤶ȡ�
     */
    public static int getMaxLength(Class class1, String s)
    {
        int i = 0;
        return i;
    }

    /**
     * �жϵ�ǰ�û��Ƿ���з���Ȩ�ޡ�
     * @param className String ���ʵ�������
     * @param permission String ���ʿ���Ȩ�ޡ�
     * @return boolean ��true��ʾ�з���Ȩ�ޣ������޷���Ȩ�ޡ�
     * @throws QMException 
     * @
     */
    public static Boolean hasPermission(String className, String permission) throws QMException 
    {
        //�������͵ļ��ϡ�
        Class[] paraClass = {};

        //����ֵ�ļ��ϡ�
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

        //���÷��ʿ��Ʒ����жϵ�ǰ�û���ָ�����ࡢָ������
        //ָ������������״̬�Ƿ����ָ���ķ��ʿ���Ȩ�ޣ�
        //�������͵ļ��ϡ�
        Class[] paraClass1 = {ActorIfc.class,//CR2
                String.class, String.class, LifeCycleState.class, String.class};
        //����ֵ�ļ��ϡ�
        Object[] objs1 = {user, //�û���
                className, //������
                DomainHelper.SYSTEM_DOMAIN, //��
                null, //״̬��
                permission //���ʿ���Ȩ�ޡ�
        };
        Boolean flag = null;
        //            flag = (Boolean) IBAUtility.invokeServiceMethod(
		//                    "AccessControlService", "hasAccess", paraClass1, objs1);
		flag = (Boolean)RequestHelper.request("AccessControlService", "hasAccess", paraClass1, objs1);
        return flag;
    }

    /**
     * �����û�BsoID��ȡ�û���ȫ�ƣ�
     * @param userID �û���BsoID
     * @return String �û���
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
     * �жϸ��㲿���Ƿ���Ը��¡�
     * @param selectObject QMPartIfc 
     * @return boolean ������㲿�����Ը��·���true�����򷵻�false��
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
                    //if 1:����������û���������㲿��,��ʾ"�����Ѿ������˼��!"��
                    if(CheckInOutHelper.isCheckedOutByOther((WorkableIfc)selectObject))
                    {
                        isupdate = false;
                    } //end if 12:�������ǰ�û������
                    else if(CheckInOutHelper.isCheckedOutByCur((WorkableIfc)selectObject))
                    {
                        //if 2:����Ǳ���ǰ�û���������ǹ�������,�ͻ�ù���������
                        if(!WorkInProgressUtil.isWorkingCopy((WorkableIfc)selectObject))
                        {
                            isupdate = false;
                        }
                    }
                    //if 3:�ж϶����Ƿ������޸ġ�
                    else if(!isUpdateAllowed((FolderedIfc)selectObject))
                    {
                        isupdate = false;
                    }
                }
            }
        return isupdate;
    }

    /**
     * �ж϶����Ƿ������޸ġ�
     * @param obj FolderedIfc ���ϼ�ֵ����
     * @throws QMException 
     * @
     * @
     * @return boolean ����true�������޸ģ���������
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
     * �ڴ���/�����ĵ�ʱ�����ĵ����Ϊ�㲿���������ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docBsoID �ĵ�ֵ����BsoID��
     * @return String ��ʾ��Ϣ��
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
            //�㲿���ѱ���ǰ�û�������ڸ������ϼ��С�
            if(CheckInOutHelper.isCheckedOutByCur(partIfc)
                    || !CheckInOutHelper.isInVault(partIfc))
            {
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partBsoID);
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                //"�ɹ����Ϊ * �������ĵ���\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M002", new Object[] {partIfc.getIdentity()});//CR4
            }
            //�㲿���ѱ������û������
            else if(CheckInOutHelper.isCheckedOutByOther(partIfc))
            {
                //"* �ѱ��û� * �����\n"
//                message = message + QMMessage.getLocalizedMessage(resource1, "M003",
//                        new Object[]{partIfc.getIdentity(),
//                        getUserNameByID(partIfc.getLocker())});//CR4
            }
            //��ǰ�û����޸��㲿����Ȩ�ޡ�����㲿�������������������㲿����
            else if(WorkInProgressUtil.isCheckoutAllowed(partIfc))
            {
                WorkingPair workingPair = CheckInOutHelper.checkOutObject(partIfc);
                if(!workingPair.isOperateSuccess())//CR5
                {
                    throw new QMException(workingPair.getException());
                }
                // ��������BsoID��
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
                // "�ɹ����Ϊ * �������ĵ���\n"
//                message = message + QMMessage.getLocalizedMessage(resource1, "M002", new Object[]{workableIfc.getIdentity()});//CR4
            }
            // ��ǰ�û�û���޸��㲿����Ȩ�ޡ�
            else
            {
                // "��û���޸� * ��Ȩ�ޣ�����Ϊ���㲿����������ĵ���\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M005", new Object[] {partIfc.getIdentity()});//CR4
            }
        }
//        return message;
    }
    /**
     * �ڴ���/�����ĵ�ʱ�����ĵ����Ϊ�㲿���Ĳο��ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docMasterBsoID �ĵ�����ϢBsoID��
     * @return String ��ʾ��Ϣ��
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
            //�㲿���ѱ���ǰ�û�������ڸ������ϼ��С�
            if(CheckInOutHelper.isCheckedOutByCur(partIfc)
                    || !CheckInOutHelper.isInVault(partIfc))
            {
                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partBsoID);
                PartServiceRequest.savePartReferenceLink(linkIfc);
                //"�ɹ����Ϊ * �Ĳο��ĵ���\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M001", new Object[] {partIfc.getIdentity()});//CR4
            }
            //�㲿���ѱ������û������
            else if(CheckInOutHelper.isCheckedOutByOther(partIfc))
            {
                //"* �ѱ��û� * �����\n"
//                message = message
//                        + QMMessage.getLocalizedMessage(resource1, "M003",
//                                new Object[]{partIfc.getIdentity(),
//                                        getUserNameByID(partIfc.getLocker())});//CR4
            }
            //��ǰ�û����޸��㲿����Ȩ�ޡ�����㲿�������������������㲿����
            else if(WorkInProgressUtil.isCheckoutAllowed(partIfc))
            {
                WorkingPair workingPair = CheckInOutHelper.checkOutObject(partIfc);
                // ��������BsoID��
                String workableID = workingPair.getWorkingCopy().getBsoID();
                partIfc = (QMPartIfc)RequestHelper.request("PersistService", "refreshInfo", new Class[]{String.class}, new Object[]{workableID});

                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partIfc.getBsoID());
                PartServiceRequest.savePartReferenceLink(linkIfc);
                WorkingPair workableIfc1 = CheckInOutHelper.checkInObject(partIfc, "");
                WorkableIfc workableIfc=workableIfc1.getWorkingCopy();
                // "�ɹ����Ϊ * �Ĳο��ĵ���\n"
//                message = message + QMMessage.getLocalizedMessage(resource1, "M001", new Object[]{workableIfc.getIdentity()});//CR4
            }
            // ��ǰ�û�û���޸��㲿����Ȩ�ޡ�
            else
            {
                // "��û���޸� * ��Ȩ�ޣ�����Ϊ���㲿����Ӳο��ĵ���\n"
//                message = message + QMMessage.getLocalizedMessage(resource1,
//                        "M004", new Object[] {partIfc.getIdentity()});//CR4
            }
        }
//        return message;//CR4
    }
    /**
     * �ڸ����ĵ�ʱ��ɾ���ĵ����㲿���Ĳο��ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docMasterBsoID �ĵ�����ϢBsoID��
     * @return String ��ʾ��Ϣ��
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
            //ֻ����һ������
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
     * �ڸ����ĵ�ʱ��ɾ���ĵ����㲿���������ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docBsoID �ĵ�ֵ����BsoID��
     * @return String ��ʾ��Ϣ��
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
