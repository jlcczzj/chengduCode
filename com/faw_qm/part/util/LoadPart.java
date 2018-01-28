/**
 * ����LoadPart.java 1.0 2003/06/12 ��Ȩ��һ��������˾���� ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵��� ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ����� ��������Ȩ��
 * CR1 2010/08/25 ��� ԭ��:TD2286�㲿�����ݵ��빦�ܣ�ȱ�ٵ��������㲿���ṹ����
 *                     ����:ʵʩҪ��Ѷ������㲿���ṹ�����ټ���
 * CR2 2010/09/17 ��� ԭ��:�����㲿����ʱ����������Ѿ����ڣ��޶����ܲ���ʹ
 * CCBegin by liunan 2011-08-25 �򲹶�P035
 * CR3 2011/05/31 ��� �޸�ԭ��:TD2405�㲿�����빦������
 *                     ����ԭ�򣺵����Ѵ��ڵ��㲿��ʱ�������Դ���������Ա��޸ĵĻ����µ���Դ������û�б����ý����µ�partIfc�У�����ɴ���
 *                     �޸ķ������ٵ����Ѵ��ڵ��㲿��ʱ������Դ������������partIfc����������һ���ٱ���
 * CCEnd by liunan 2011-08-25
 * CCBegin by liunan 2011-08-29 �򲹶�P036
 * CR4 2011/08/05 ���� ԭ��TD2429���㲿������״̬���ԡ�
 * CCEnd by liunan 2011-08-29
 * SS1 ����BOM����ʹ ����� 2014-09-27 
 * SS2 A004-2015-3143�޶�ʱ��δ������������״̬�� liunan 2015-6-10
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
 * <p>Title: �㲿�����ݵĵ��롣</p>
 * <p>Description: �㲿���������Ϣ������
 * �㲿���Ļ������ԣ�
 * �㲿�����������ԣ�
 * �㲿���Ľṹʹ�ù�ϵ��
 * �㲿�����ĵ��Ĳο���ϵ��
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����ҡ�л��
 * @version 1.0
 */
public class LoadPart implements Serializable
{
    /**���ڱ����Դ��Ϣ·��*/
    protected static final String RESOURCE = "com.faw_qm.part.client.main.util.QMProductManagerRB";

    /** ����ṹ��*/
    private static String CLASSIFICATION_STRUCTURE = "Classification Struct";

    private static String LOAD_CLASSIFICATION = "Load Part With Classification";

    //�����㲿�����ӵĲο��ĵ�����
    private static String CURRENT_PART_REFERENCE_DOC_LINK = "CURRENT-PART-REFERENCE-DOC-LINK";

    //��ǰ������㲿��
    private static String CURRENT_PART = "Current Part";

    //��ǰ������㲿��
    private static String CURRENT_CONTENT_HOLDER = "Current ContentHolder";

    //��ʱ���㲿��
    private static String TEMP_PART = "com.faw_qm.load.LoadPart.TempQMPart";

    //�����ģʽ���������Ǹ��£�
    private static String CURRENT_LOAD_MODE = "CURRENT_LOAD_MODE";

    //�����㲿�����ӵ�ʹ�ù�ϵ����
    private static String CURRENT_PART_USAGE_LINK = "CURRENT-PART-USAGE-LINK";

    //�Ƿ�������
    private static String CHECKOUT_ALLOWED = "CHECKOUT_ALLOWED";

    //��ϵͳ�д�����ͬ��ŵ��㲿��ʱ�Ĳ��������������С�汾���������޶���
    private static String BIG_VERSION = "bigversion";

    public LoadPart()
    {
        super();
    }

    static final long serialVersionUID = 1L;
    

    /**CR1 begin
     * ֻ�����㲿���ṹ
     * ����.csv�ļ��е�"AddUsageLink"����
     * �ù������еĲ���Ϊ���Թ���һ���Ѿ����ڵ��㲿����ʹ�ù�ϵ�����������ݿ���
     * Ҫ�������漰���㲿�������������ݿ����Ѿ����ڵģ�����ᷢ���쳣��
     * @param nv               �������Ե���/ֵ ��
     * ����������            parentNumber
     * ���㲿������          childNumber
     * ʹ�õ�λ              unitStr
     * ʹ������              quantityStr
     * @param return_objects   <code>Vector</code> ��ȷ��������ķ��ؼ���
     * @return boolean
     */ 
    public static boolean createPartUsageLinkonly(Hashtable nv,
            Vector return_objects)
    {
        // �����Ƿ�ɹ��ı�־
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
            // ��ø�������bsoID
            String parentBsoID = getPartIDByNumber(parentNumber);
            // ������㲿����Ӧ���°汾�Ͱ�����㲿����BsoID
            String childBsoID = getPartIDByMasterNumber(childNumber);
            if((parentBsoID != null) && (childBsoID != null))
            {
                /*
                 * ������ǰ������㲿�������и��ڵ㣬�����ж����и��ڵ����Ƿ��и�csv�ļ��ĸ������ parentBsoID��ͬ�ġ��Ӷ��������ƽṹ�ظ����롣
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
                //��������ʹ��������
                quantity1.setQuantity(quantity);
                quantity1.setDefaultUnit(unit);
                usageLink.setQuantity(quantity);

                usageLink.setDefaultUnit(unit);
                usageLink.setQuantity(quantity);
                usageLink.setQMQuantity(quantity1);

                //�����ݿ��б���usageLink����
                usageLink = (PartUsageLinkIfc)PartServiceRequest.savePartUsageLink(usageLink);
                return true;
            }else
            {
            	System.out.println("0000+++++"+"ID��Ϊ��");
                return false;
            }
        }catch(QMException exception)
        {
            String message = parentNumber + " - " + childNumber;
            LoadHelper.printMessage("����" + message + "ʱ��������");
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
     * ����.csv�ļ��е�"AddUsageLink"����
     * �ù������еĲ���Ϊ���Թ���һ���Ѿ����ڵ��㲿����ʹ�ù�ϵ�����������ݿ���
     * Ҫ�������漰���㲿�������������ݿ����Ѿ����ڵģ�����ᷢ���쳣��
     *
     * @param attributes               �������Ե���/ֵ ��
     *                         �������£�
     * ����������            parentNumber
     * ���㲿������          childNumber
     * ʹ�õ�λ              unitStr
     * ʹ������              quantityStr
     *
     * @param return_objects   <code>Vector</code> ��ȷ��������ķ��ؼ���
     * @return boolean
     */
    public static boolean createPartUsageLink(Hashtable attributes,
            Vector return_objects)
    {
        //�����Ƿ�ɹ��ı�־
        boolean flag = false;
        //���븸�㲿���ı��
        String parentNumber = "";
        //�������㲿����
        String childNumber = "";
        //ʹ�õ�λ
        String unitStr = "";
        //ʹ������
        String quantityStr = "";
        try
        {
            parentNumber = LoadHelper.getValue("parentNumber", attributes, 1);
            childNumber = LoadHelper.getValue("childNumber", attributes, 1);
            unitStr = LoadHelper.getValue("unitStr", attributes, 1);
            quantityStr = LoadHelper.getValue("quantityStr", attributes, 1);
            PartUsageLinkIfc usageLink = new PartUsageLinkInfo();
            QMPartIfc exitedPartIfc = getPartIfcByNumber(parentNumber);
            //��ø�������bsoID
            String parentBsoID = null;
            if(exitedPartIfc != null)
            {
                parentBsoID = exitedPartIfc.getBsoID();
            }
            if(parentBsoID == null)
            {
                String s = "\ncreatePartUsageLink:����" + parentNumber
                        + "��װ���ļ��в����ڻ�δ���ɹ�������";
                //                LoadHelper.printMessage(s);
                throw new QMException(s);
            }
            //������㲿����Ӧ���°汾�Ͱ�����㲿����BsoID
            String childMasterBsoID = getPartIDByMasterNumber(childNumber);
            if(childMasterBsoID == null)
            {
                String s1 = "\ncreatePartUsageLink:����" + childNumber
                        + "��װ���ļ��в����ڻ�δ���ɹ�������";
                //                LoadHelper.printMessage(s1);
                throw new QMException(s1);
            }
            if(parentBsoID != null && childMasterBsoID != null)
            {
                /*������ǰ������㲿�������и��ڵ㣬�����ж����и��ڵ����Ƿ��и�csv�ļ��ĸ������
                 *parentBsoID��ͬ�ġ��Ӷ��������ƽṹ�ظ����롣
                 */
                //��ʼ��
                try
                {
                    boolean exitFlag = isHasPartUsageLink(parentBsoID,
                            childNumber);
                    //������
                    //���ϵͳ�в�������ͬ�Ľṹ
                    //����ѡ��Ĳ�ͬ������ͬ�����顣
                    int bigVersion = (Integer) attributes.get(BIG_VERSION);
                    //���ѡ����ԣ��򲻵���ṹ��Ϣ��
                    if((exitFlag && (bigVersion == 1 || bigVersion == 2)))
                    {
                        //�Ѵ����㲿�����°汾ֵ����
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
                        //��������ʹ��������
                        quantity1.setQuantity(quantity);
                        quantity1.setDefaultUnit(unit);
                        usageLink.setQuantity(quantity);
                        usageLink.setDefaultUnit(unit);
                        usageLink.setQuantity(quantity);
                        usageLink.setQMQuantity(quantity1);
                        //�����ݿ��б���usageLink����
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
            LoadHelper.printMessage("����" + message + "ʱ��������");
            exception.printStackTrace();
            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception e)
        {
            String message = parentNumber + " - " + childNumber;
            LoadHelper.printMessage("����" + message + "ʱ��������");
            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * ������ǰ������㲿�������и��ڵ㣬�����ж����и��ڵ����Ƿ��и�csv�ļ��ĸ������
     * parentBsoID��ͬ�ġ��Ӷ��������ƽṹ�ظ����롣
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
     * �Դ�����㲿��������С�汾�����������޶������������µ��㲿��ֵ����
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
        //С�汾������
        if(bigVersion == 1)
        {
            // �㲿���ļ��
            //			QMPartIfc checkingIn = (QMPartIfc) getCheckingInObject(partIfc);
            //
            //			// ���ݵ����ļ��е��㲿�����ͺ���Դ�޸�ϵͳ���Ѿ����ڵ��㲿��
            //			String producedByStr = (String) hashtable.get("producedByStr");
            //			String partTypeStr = (String) hashtable.get("partTypeStr");
            //			// ��������޶�ʱ��˵����Ϣ
            //			String versionDes = (String) hashtable.get("versionDes");
            //			if (versionDes == null) {
            //				versionDes = "";
            //			}
            //			// ������Դ
            //			if (producedByStr != null && !producedByStr.equals("")) {
            //				ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            //				partIfc.setProducedBy(produce);
            //			}
            //			// ��������
            //			if (partTypeStr != null && !partTypeStr.equals("")) {
            //				QMPartType type = QMPartType.toQMPartType(partTypeStr);
            //				partIfc.setPartType(type);
            //			}
            //			// �㲿���ļ���
            //			PersistService pService = (PersistService) EJBServiceHelper
            //					.getPersistService();
            //			WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
            //					.getService("WorkInProgressService");
            //			pService.updateValueInfo(checkingIn);
            //			if(isCheckoutAllowed(checkingIn)){
            //				newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            //			}
            //			else{
            //	        	//�ж��Ƿ�Ϊ�״μ���
            ////	            Object object = IBAUtility.invokeServiceMethod("SessionService",
            ////	                    "getCurUserID", new Class[]{}, new Object[]{});
            //	            SessionService sessionService=(SessionService) EJBServiceHelper
            //	            .getService("SessionService");
            //	            //����ļ��з���
            //	            FolderService  folderService = (FolderService)EJBServiceHelper.getService("FolderService");
            //	            Object object = sessionService.getCurUserID();
            //	            //�Ƿ񱻵�ǰ�û�ӵ��
            //	        	boolean isOwnFlag = OwnershipHelper.isOwnedBy((OwnableIfc) checkingIn, (String) object);
            //	        	boolean isInPerson=folderService.inPersonalFolder(checkingIn);
            //	        	if(isOwnFlag&&isInPerson){
            //	        		newPartIfc= (QMPartIfc)pService.saveValueInfo(checkingIn);
            //	        	}
            //			}
            ////			newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            newPartIfc = saveOrCheckIn(partIfc, hashtable);
        }
        // �޶���
        else if(bigVersion == 2)
        {
            StandardPartService partService = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            //�޶�
            newPartIfc = partService.revisePart(partIfc);
            String lifeCycleName = (String) hashtable.get("lifeCycleName");
            String location = (String) hashtable.get("location");
            String projectName = (String) hashtable.get("projectName");
            //CCBegin SS2
            String lifeCycleStateStr = (String) hashtable.get("lifeCycleStateStr");
            //CCEnd SS2
            if(lifeCycleName != null && !lifeCycleName.equals(""))
            {
                // ����������ڷ���
                LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                        .getService("LifeCycleService");
                // ������������ģ�����ƻ����������ģ��
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
                
                //������������״̬
                LifeCycleState state = LifeCycleState.toLifeCycleState(lifeCycleStateStr);
                newPartIfc = (QMPartIfc) lifeCycleService.setLifeCycleState((LifeCycleManagedIfc) newPartIfc, state);
                newPartIfc.setLifeCycleState(state);
                //CCEnd SS2
            }
            // Ϊ�㲿�����ô洢λ��
            if(location != null && !location.equals(""))
            {
                // ������ϼз���
                FolderService folderService = (FolderService) EJBServiceHelper
                        .getService("FolderService");
                partIfc = (QMPartIfc) folderService.assignFolder(
                        (FolderEntryIfc) partIfc, location);
            }
            // ���ù�����
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
     * �Դ�����㲿��������С�汾�����������޶������������µ��㲿��ֵ����
     * @param partIfc
     * @param hashtable
     * @return
     * @throws QMException
     */
    private static QMPartIfc getNewPartByCache(QMPartIfc partIfc, int bigVersion)
            throws QMException
    {
        QMPartIfc newPartIfc = null;
        //С�汾������
        if(bigVersion == 1)
        {
            // �㲿���ļ��
            //			QMPartIfc checkingIn = (QMPartIfc) getCheckingInObject(partIfc);
            //
            //			// ���ݵ����ļ��е��㲿�����ͺ���Դ�޸�ϵͳ���Ѿ����ڵ��㲿��
            //			String producedByStr = (String) hashtable.get("producedByStr");
            //			String partTypeStr = (String) hashtable.get("partTypeStr");
            //			// ��������޶�ʱ��˵����Ϣ
            //			String versionDes = (String) hashtable.get("versionDes");
            //			if (versionDes == null) {
            //				versionDes = "";
            //			}
            //			// ������Դ
            //			if (producedByStr != null && !producedByStr.equals("")) {
            //				ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            //				partIfc.setProducedBy(produce);
            //			}
            //			// ��������
            //			if (partTypeStr != null && !partTypeStr.equals("")) {
            //				QMPartType type = QMPartType.toQMPartType(partTypeStr);
            //				partIfc.setPartType(type);
            //			}
            //			// �㲿���ļ���
            //			PersistService pService = (PersistService) EJBServiceHelper
            //					.getPersistService();
            //			WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
            //					.getService("WorkInProgressService");
            //			pService.updateValueInfo(checkingIn);
            //			if(isCheckoutAllowed(checkingIn)){
            //				newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            //			}
            //			else{
            //	        	//�ж��Ƿ�Ϊ�״μ���
            ////	            Object object = IBAUtility.invokeServiceMethod("SessionService",
            ////	                    "getCurUserID", new Class[]{}, new Object[]{});
            //	            SessionService sessionService=(SessionService) EJBServiceHelper
            //	            .getService("SessionService");
            //	            //����ļ��з���
            //	            FolderService  folderService = (FolderService)EJBServiceHelper.getService("FolderService");
            //	            Object object = sessionService.getCurUserID();
            //	            //�Ƿ񱻵�ǰ�û�ӵ��
            //	        	boolean isOwnFlag = OwnershipHelper.isOwnedBy((OwnableIfc) checkingIn, (String) object);
            //	        	boolean isInPerson=folderService.inPersonalFolder(checkingIn);
            //	        	if(isOwnFlag&&isInPerson){
            //	        		newPartIfc= (QMPartIfc)pService.saveValueInfo(checkingIn);
            //	        	}
            //			}
            ////			newPartIfc = (QMPartIfc) wService.checkin(checkingIn, versionDes);
            newPartIfc = saveOrCheckInByCache(partIfc);
        }
        // �޶���
        else if(bigVersion == 2)
        {
            StandardPartService partService = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            //�޶�
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
                // ����������ڷ���
                LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                        .getService("LifeCycleService");
                // ������������ģ�����ƻ����������ģ��
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
                
                //������������״̬
                LifeCycleState state = LifeCycleState.toLifeCycleState(lifeCycleStateStr);
                newPartIfc = (QMPartIfc) lifeCycleService.setLifeCycleState((LifeCycleManagedIfc) newPartIfc, state);
                newPartIfc.setLifeCycleState(state);
                //CCEnd SS2
            }
            // Ϊ�㲿�����ô洢λ��
            if(location != null && !location.equals(""))
            {
                // ������ϼз���
                FolderService folderService = (FolderService) EJBServiceHelper
                        .getService("FolderService");
                newPartIfc = (QMPartIfc) folderService.assignFolder(
                        (FolderEntryIfc) newPartIfc, location);//CR2
            }
            // ���ù�����
            if(projectName != null && !projectName.equals(""))
            {
                newPartIfc.setProjectName(projectName);//CR2
            }
            PersistService pService = (PersistService) EJBServiceHelper
                    .getPersistService();
            newPartIfc = (QMPartIfc) pService.saveValueInfo(newPartIfc);
            // Ϊ�㲿�����ô洢λ��
            if(location != null && !location.equals(""))
            {
                // ������ϼз���
                FolderService folderService = (FolderService) EJBServiceHelper
                        .getService("FolderService");
                newPartIfc = (QMPartIfc) folderService.assignFolder(
                        (FolderEntryIfc) newPartIfc, location);
            }
        }
        return newPartIfc;
    }

    /**
     * �����㲿������Ϣ�ĺ��������´�汾�µ����°�����㲿����BosID��
     * 
     * @param partMasterNumber
     *            �㲿������Ϣ�ĺ��롣
     * @return partBsoID ���´�汾�µ����°�����㲿����BosID��
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
                //���ð汾���Ʒ���
                VersionControlService vcService = (VersionControlService) EJBServiceHelper
                        .getService("VersionControlService");
                //���master�µ�����С�汾�������°������У�
                //���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
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
     * �����㲿������Ϣ�ĺ��������´�汾�µ����°�����㲿����BosID��
     * @param partMasterNumber   �㲿������Ϣ�ĺ��롣
     * @return partBsoID         ���´�汾�µ����°�����㲿����BosID��
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
                //���ð汾���Ʒ���
                VersionControlService vcService = (VersionControlService) EJBServiceHelper
                        .getService("VersionControlService");
                //���master�µ�����С�汾�������°������У�
                //���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
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
     * �����㲿���ĺ������㲿����Master��BosID��
     * @param partNumber   �㲿���ĺ��롣
     * @return partBsoID   �㲿����BosID��
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
     * ����.csv�ļ��е�"AddRefernceLink"���
     * �ù������еĲ���Ϊ���Թ���һ���Ѿ����ڵ��㲿���Ĳο���ϵ�����������ݿ���
     * Ҫ�������漰���㲿�����ĵ������������ݿ����Ѿ����ڵģ�����ᷢ���쳣��
     * @param nv               �������Ե���/ֵ ��
     *                         �������£�
     * �㲿������            partNumber
     * �ο��ĵ�����          docNumber
     * @param return_objects   <code>Vector</code> ��ȷ��������ķ��ؼ��ϡ�
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
            //����㲿����BsoID
            String partBsoID = getPartIDByNumber(partNumber);
            //������ĵ�BsoID
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
                //�����㲿����׼����
                //�����ݿ��б���referenceLink����
                referenceLink = (PartReferenceLinkIfc) PartServiceRequest
                        .savePartReferenceLink(referenceLink);
                flag = true;
            }
            else
            {
                String s1 = "\ncreatePartDocReference:û�е�ǰ�㲿�����������ȳɹ������㲿����";
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
     * ����û���������Ե��㲿����
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return �ɹ���ʶ��
     */
    public static boolean createPart(Hashtable hashtable, Hashtable hashtable1,
            Vector vector)
    {
    	System.out.println("1111111111111111111");
        return createPartObject(hashtable, hashtable1, vector);
    }

    /**
     * ����û���������Ե��㲿����
     * @param hashtable
     * @param vector
     * @return �ɹ���ʶ��
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
     * ��ʼ�����������������Ե��㲿����
     * @param attributes Hashtable ���Լ���
     * @param vector
     * @return �ɹ���ʶ��
     */
    public static boolean beginCreateQMPart(Hashtable attributes, Vector vector)
    {
        return beginCreateQMPart(attributes);
    }

    /**
     * ��ʼ�����������������Ե��㲿����
     * @param attributes Hashtable ���Լ���
     * @return �ɹ���ʶ��
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
     * ���������������������Ե��㲿����
     * @param attributes
     * @param vector
     * @return �ɹ���ʶ��
     */
    public static boolean endCreateQMPart(Hashtable attributes, Vector vector)
    {
        return endCreateQMPart(attributes, new Hashtable(), vector);
    }

    /**
     * ���������������������Ե��㲿����
     * 
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return �ɹ���ʶ��
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
     * �ж��Ƿ����������
     * @param workable ���������
     * @return ��ʶ��
     * @throws LockException
     * @throws QMException
     */
    private static boolean isCheckoutAllowed(WorkableIfc workable)
            throws LockException, QMException
    {
        boolean flag = false;
        //����ļ��з���
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
     * �ж��Ƿ��������������
     * @param workable �������������
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
     * ��ñ��������Ĺ���������
     * @param workable ���������
     * @return ���������Ĺ���������
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
     * ������߱����㲿�����������ϼ��е��㲿�����м���ټ��룬�Ѿ�����ǰ�û�������㲿�����м��������
     * ��ǰ�û��������㲿�����浽�������ϼУ������˼���������˴������㲿���׳��쳣��
     * @param workable ���������
     * @return ���������Ĺ���������
     * @throws LockException
     * @throws QMException
     */
    private static QMPartIfc saveOrCheckIn(QMPartIfc partIfc,
            Hashtable hashtable) throws LockException, QMException
    {
        boolean isCheckoutAllowed = isCheckoutAllowed(partIfc);
        //�Ƿ񱻵�ǰ�û�ӵ��
        boolean isOwnFlag = false;
        // �㲿���ļ��
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
            //�ж��Ƿ�Ϊ�״μ���
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
        // ���ݵ����ļ��е��㲿�����ͺ���Դ�޸�ϵͳ���Ѿ����ڵ��㲿��
        String producedByStr = (String) hashtable.get("producedByStr");
        String partTypeStr = (String) hashtable.get("partTypeStr");
        // ��������޶�ʱ��˵����Ϣ
        String versionDes = (String) hashtable.get("versionDes");
        if(versionDes == null)
        {
            versionDes = "";
        }
        // ������Դ
        if(producedByStr != null && !producedByStr.equals(""))
        {
            ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            partIfc.setProducedBy(produce);
            //			checkingIn.setProducedBy(produce);
        }
        // ��������
        if(partTypeStr != null && !partTypeStr.equals(""))
        {
            QMPartType type = QMPartType.toQMPartType(partTypeStr);
            partIfc.setPartType(type);
            //			checkingIn.setPartType(type);
        }
        // �㲿���ļ���
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
            //�ж��Ƿ�Ϊ�״μ���
            //����ļ��з���
            FolderService folderService = (FolderService) EJBServiceHelper
                    .getService("FolderService");
            boolean isInPerson = folderService.inPersonalFolder(checkingIn);
            if(isOwnFlag && isInPerson)
            {
                newPartIfc = (QMPartIfc) pService.saveValueInfo(checkingIn);
                String location = (String) hashtable.get("location");
                //Ϊ�㲿�����ô洢λ��
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
     * ������߱����㲿�����������ϼ��е��㲿�����м���ټ��룬�Ѿ�����ǰ�û�������㲿�����м��������
     * ��ǰ�û��������㲿�����浽�������ϼУ������˼���������˴������㲿���׳��쳣��
     * @param workable ���������
     * @return ���������Ĺ���������
     * @throws LockException
     * @throws QMException
     */
    private static QMPartIfc saveOrCheckInByCache(QMPartIfc partIfc)
            throws LockException, QMException
    {
        boolean isCheckoutAllowed = (Boolean) LoadHelper
                .getCacheValue(CHECKOUT_ALLOWED);
        //�Ƿ񱻵�ǰ�û�ӵ��
        boolean isOwnFlag = false;
        // �㲿���ļ��
        QMPartIfc checkingIn = null;
        QMPartIfc newPartIfc = null;
        if(isCheckoutAllowed)
        {
            WorkInProgressService wService = (WorkInProgressService) EJBServiceHelper
                    .getService("WorkInProgressService");
            FolderIfc folder = wService.getCheckoutFolder();
            wService.checkout(partIfc, folder, "");
            checkingIn = (QMPartIfc) wService.workingCopyOf(partIfc);
            //CCBegin by liunan 2011-08-25 �򲹶�P035
            checkingIn.setProducedBy(partIfc.getProducedBy());//CR3
            checkingIn.setPartType(partIfc.getPartType());//CR3
            //CCEnd by liunan 2011-08-25
        }
        else
        {
            //�ж��Ƿ�Ϊ�״μ���
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
        // ���ݵ����ļ��е��㲿�����ͺ���Դ�޸�ϵͳ���Ѿ����ڵ��㲿��
        String producedByStr = (String) LoadHelper
                .getCacheValue("producedByStr");
        String partTypeStr = (String) LoadHelper.getCacheValue("partTypeStr");
        // ��������޶�ʱ��˵����Ϣ
        String versionDes = (String) LoadHelper.getCacheValue("versionDes");
        if(versionDes == null)
        {
            versionDes = "";
        }
        // ������Դ
        if(producedByStr != null && !producedByStr.equals(""))
        {
            ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
            partIfc.setProducedBy(produce);
            //			checkingIn.setProducedBy(produce);
        }
        // ��������
        if(partTypeStr != null && !partTypeStr.equals(""))
        {
            QMPartType type = QMPartType.toQMPartType(partTypeStr);
            partIfc.setPartType(type);
            //			checkingIn.setPartType(type);
        }
        // �㲿���ļ���
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
            //�ж��Ƿ�Ϊ�״μ���
            //����ļ��з���
            FolderService folderService = (FolderService) EJBServiceHelper
                    .getService("FolderService");
            boolean isInPerson = folderService.inPersonalFolder(checkingIn);
            if(isOwnFlag && isInPerson)
            {
                newPartIfc = (QMPartIfc) pService.saveValueInfo(checkingIn);
                String location = (String) LoadHelper.getCacheValue("location");
                //Ϊ�㲿�����ô洢λ��
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
     * ��ʼ��������¿������������Ե��㲿�����������޷�֪��ϵͳ���Ƿ��и��㲿���������
     * @param attributes
     * @param vector
     * @return �ɹ���ʶ��
     * @throws QMException
     */
    public static boolean beginCreateOrUpdateQMPart(Hashtable attributes,
            Vector vector) throws QMException
    {
        return beginCreateOrUpdateQMPart(attributes);
    }

    /**
     * ��ʼ��������¿������������Ե��㲿�����������޷�֪��ϵͳ���Ƿ��и��㲿���������
     * @param attributes
     * @return �ɹ���ʶ��
     * @throws QMException
     */
    private static boolean beginCreateOrUpdateQMPart(Hashtable attributes)
            throws QMException
    {
        boolean flag = false;
        QMPartIfc partIfc = null;
        //�����㲿���ı��
        String partNumber = null;
        //����ģʽ���������߸��£�
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
            //���浼���㲿������Ϣ
            cacheLoadPartInfo(attributes);
            //������°汾���㲿��ֵ����
            partIfc = getPartIfcByNumber(partNumber);
            if(partIfc != null)
            {
                IBAValueService ibaValueService = (IBAValueService) EJBServiceHelper
                        .getService("IBAValueService");
                //�ж��Ƿ���
                isCheckoutAllowed = isCheckoutAllowed(partIfc);
                //                LoadHelper.setCacheValue(TEMP_PART, part);
                //����ģʽ�Ǹ���
                loadMode = "UPDATE";
                partIfc = (QMPartIfc) ibaValueService
                        .refreshAttributeContainer(partIfc, null, null, null);
                DefaultAttributeContainer attrContainer = null;
                //���Կ���ȥ���÷�������Ϊ�������������ÿյ���������
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
                //CCBegin by liunan 2011-08-25 �򲹶�P035
                //CR3 begin
                String producedByStr = "";
                String partTypeStr = "";
                producedByStr = getValue(attributes, "producedByStr", true);
                partTypeStr = getValue(attributes, "partTypeStr", true);
//              ������Դ
                ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
                partIfc.setProducedBy(produce);
                //��������
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
                    //                    LoadHelper.printMessage("�����滻��" + as1[0]);
                    exception3.printStackTrace();
                    throw new PartException(exception3);
                }
            }
        }
        return flag;
    }

    /**
     * ������������¿������������Ե��㲿�����������޷�֪��ϵͳ���Ƿ��и��㲿���������
     * @param attributes
     * @param vector
     * @return �ɹ���ʶ��
     * @throws PartException
     */
    public static boolean endCreateOrUpdateQMPart(Hashtable attributes,
            Vector vector) throws PartException
    {
        return endCreateOrUpdateQMPart(attributes, new Hashtable(), vector);
    }

    /**
     * ������������¿������������Ե��㲿�����������޷�֪��ϵͳ���Ƿ��и��㲿���������
     * 
     * @param attributes
     * @param hashtable1
     * @param vector
     * @return �ɹ���ʶ��
     * @throws PartException
     */
    public static boolean endCreateOrUpdateQMPart(Hashtable attributes,
            Hashtable hashtable1, Vector vector) throws PartException
    {
        QMPartIfc partIfc = null;
        boolean flag = false;
        //����ģʽ���������߸��£�
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
                        //CCBegin by liunan 2011-08-25 �򲹶�P035
                        //CR3 begin
                        exitingPart.setProducedBy(partIfc.getProducedBy());
                        exitingPart.setPartType(partIfc.getPartType());
                        //CCEnd by liunan 2011-08-25
                    }
                }
                // ���ϵͳ�в�������ͬ��ŵ��㲿�����򱣴��㲿����
                if(exitingPart == null)
                {
                    // loadPartIfc = (QMPartIfc)
                    // pService.storeValueInfo(loadPartIfc);
                }
                // ϵͳ�д�����ͬ��ŵ��㲿��
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
                    // 1.2 ���ϵͳ���Ѵ��ڵ�����㲿�����ݣ��Դ��ڵ����ݽ���С�汾��������
                    // ������getPartIDByNumber(String );
                    // 1.2.1 ���ȸ����㲿����Ų�ѯ�㲿��ֵ�����ж�ϵͳ���Ƿ������ͬ��ŵ��㲿����
                    // 1.2.2 ���ϵͳ�д�����ͬ��ŵ��㲿���������С�汾��������
                    // ϵͳ�д�����ͬ��ź���ͼ���㲿��
                    if(exitingPart != null)
                    {
                        partIfc = getNewPartByCache(exitingPart, bigVersion);
                    }
                    else
                    {
                        // ϵͳ�в�������ͬ��ź���ͼ���㲿��
                        // ѡ������С�汾�����׳��쳣
                        if(bigVersion == 1)
                        {
                            throw new QMException("ϵͳ���Ѿ������㲿�����Ϊ"
                                    + partIfc.getPartNumber() + "��"
                                    + exitingPartViewName + "�㲿��");
                        }
                        // ѡ���޶�
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
                                            //CCBegin by liunan 2011-08-25 �򲹶�P035
                                            exitingPart.setProducedBy(partIfc.getProducedBy());
                                            exitingPart.setPartType(partIfc.getPartType());//CR3 end
                                            //CCEnd by liunan 2011-08-25
                                            exitFlag = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            // ���ϵͳ�д��ڣ��򷢲���ͼ�汾
                            if(exitingPart != null)
                            {
                                QMPartIfc newPartIfc = (QMPartIfc) viewService
                                        .newBranchForView(exitingPart,
                                                loadPartViewIfc);
                                String lifeCycleName = (String) LoadHelper
                                        .getCacheValue("lifeCycleName");
                                String location = (String) LoadHelper
                                        .getCacheValue("location");
                                // ����������ڷ���
                                LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                                        .getService("LifeCycleService");
                                // ������������ģ�����ƻ����������ģ��
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
                                // Ϊ�㲿��������������ģ��
                                // exitingPart = (QMPartIfc)
                                // lifeCycleService.setLifeCycle(
                                // (LifeCycleManagedIfc) exitingPart,
                                // templateIfc);
                                // ������ϼз���
                                FolderService folderService = (FolderService) EJBServiceHelper
                                        .getService("FolderService");
                                // Ϊ�㲿�����ô洢λ��
                                newPartIfc = (QMPartIfc) folderService
                                        .assignFolder(
                                                (FolderEntryIfc) newPartIfc,
                                                location);
                                // ���ù�����
                                String projectName = (String) LoadHelper
                                        .getCacheValue("projectName");
                                // newPartIfc.setProjectName(loadPartIfc.getProjectName());
                                newPartIfc.setProjectName(projectName);
                                partIfc = (QMPartIfc) pService
                                        .saveValueInfo(newPartIfc);
                            }
                            // ���ϵͳ�в����ڣ����׳��쳣
                            else
                            {
                                throw new QMException("ϵͳ���Ѵ��ڵı��Ϊ"
                                        + partIfc.getPartNumber()
                                        + "���㲿�����޷�������" + loadPartViewName
                                        + "�����鵼���ļ�����ͼ��");
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
                                //TODO ɾ������ķ����������������������ģ�	
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
                        // �����ݿ��б���usageLink����
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
                        // �����ݿ��б���usageLink����
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
                    LoadHelper.printMessage("�����滻��" + as[0]);
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
     * ����㲿����ʹ�ù�ϵ��
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
     * ����㲿���Ĳο���ϵ��
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
     * ����㲿���Ĳο���ϵ��
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
        // ����㲿���Ļ���������Ϣ
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
     * ��ʼ�����㲿��������Ϣ����
     * @param hashtable
     * @param vector
     * @return �ɹ���ʶ��
     */
    public static boolean beginQMPM(Hashtable hashtable, Vector vector)
    {
        Hashtable hash = new Hashtable();
        return beginQMPM(hashtable, hash, vector);
    }

    /**
     * ��ʼ�����㲿��������Ϣ����
     * @param hashtable
     * @param hashtable1
     * @param vector
     * @return �ɹ���ʶ��
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
     * ���������㲿��������Ϣ����
     * @param hashtable
     * @param vector
     * @return �ɹ���ʶ��
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
     * �����½��㲿���Ļ������ԡ�
     * @param hashtable
     * @return �㲿����
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
        //����㲿���Ļ���������Ϣ
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
        //��������
        part.setPartName(partName);
        //���ú���
        part.setPartNumber(partNumber);
        //����Ĭ�ϵ�λ
        Unit unit = Unit.toUnit(defaultUnitStr);
        part.setDefaultUnit(unit);
        //������Դ
        ProducedBy produce = ProducedBy.toProducedBy(producedByStr);
        part.setProducedBy(produce);
        //��������
        QMPartType type = QMPartType.toQMPartType(partTypeStr);
        part.setPartType(type);
        //������Ŀ
        part.setProjectName(projectName);
        //����������ڷ���
        LifeCycleService lifeCycleService = (LifeCycleService) EJBServiceHelper
                .getService("LifeCycleService");
        //������������ģ�����ƻ����������ģ��
        LifeCycleTemplateIfc templateIfc = lifeCycleService
                .getLifeCycleTemplate(lifeCycleName);
        //Ϊ�㲿��������������ģ��
        part = (QMPartIfc) lifeCycleService.setLifeCycle(
                (LifeCycleManagedIfc) part, templateIfc);
        //������ͼ
        part.setViewName(viewName);
        //������ϼз���
        FolderService folderService = (FolderService) EJBServiceHelper
                .getService("FolderService");
        //Ϊ�㲿�����ô洢λ��
        part = (QMPartIfc) folderService.assignFolder((FolderEntryIfc) part,
                location);
        //������������״̬
        LifeCycleState state = LifeCycleState
                .toLifeCycleState(lifeCycleStateStr);
        part = (QMPartIfc) lifeCycleService.setLifeCycleState(
                (LifeCycleManagedIfc) part, state);
        //CCBegin by liunan 2011-08-29 �򲹶�P036
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
            String s2 = as[0] + "����Ч���ֶ����ơ��� csvmapfile.txt �м����ȷ�����ơ�";
            throw new QMException(s2);
        }
        if(flag && s1.equals(""))
        {
            String as1[] = {s};
            String s3 = "�������ļ���δ�ҵ�������ֶ�" + as1[0] + "��ֵ��";
            throw new QMException(s3);
        }
        if(!flag && s1.equals(""))
        {
            s1 = null;
        }
        return s1;
    }

    /**
     * ���浱ǰ������㲿����Ϣ��
     * @param part Ҫ������㲿����
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
     * ���浱ǰ������㲿��������¼�������Ϣ����
     * @param partmaster Ҫ������㲿��������¼����
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
     * ���÷���ķ�����
     * �����жϵ�ǰ�ĵ������ڷ���˻��ǿͻ��ˣ�����Ƿ���˼����շ���˵ķ�ʽ���÷�������ǿͻ��˼����տͻ��˵ķ�ʽ���÷���
     * @param serviceName String �������ơ�
     * @param methodName String �������ơ�
     * @param paraClass Class[] ���������༯�ϡ�
     * @param para Object[] �������ϡ�
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
     * ����.csv�ļ��е�"AddPartUsageLink"����
     * �ù������еĲ���Ϊ���Թ���һ���Ѿ����ڵ��㲿����ʹ�ù�ϵ�����������ݿ���
     * Ҫ�������漰���㲿�������������ݿ����Ѿ����ڵģ�����ᷢ���쳣��
     *
     * @param hashtable               �������Ե���/ֵ ��
     *                         �������£�
     * ���㲿������          childNumber
     * ʹ�õ�λ              unitStr
     * ʹ������              quantityStr
     *
     * @param return_objects   <code>Vector</code> ��ȷ��������ķ��ؼ���
     * @return boolean
     */
    public static boolean addPartUsage(Hashtable hashtable,
            Vector return_objects)
    {
        //�����Ƿ�ɹ��ı�־
        boolean flag = false;
        //���㲿���ı��
        //        String parentNumber = "";
        //�Ӽ��ı��
        String childNumber = "";
        //ʹ�õ�λ
        String unitStr = "";
        //ʹ������
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
            //������㲿����Ӧ���°汾�Ͱ�����㲿����BsoID
            String childMasterBsoID = getPartIDByMasterNumber(childNumber);
            if(childMasterBsoID == null)
            {
                String s1 = "\ncreatePartUsageLink:����" + childNumber
                        + "��װ���ļ��в����ڻ�δ���ɹ�������";
                //                LoadHelper.printMessage(s1);
                throw new QMException(s1);
            }
            if(childMasterBsoID != null)
            {
                Unit unit = Unit.toUnit(unitStr);
                usageLink.setLeftBsoID(childMasterBsoID);
                float quantity = (new Float(quantityStr)).floatValue();
                QMQuantity quantity1 = new QMQuantity();
                //��������ʹ��������
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
            //            LoadHelper.printMessage("����" + message + "ʱ��������");
            exception.printStackTrace();
            //            LoadHelper.printExceptionStatckTrace(exception);
        }
        catch (Exception e)
        {
            //            String message = parentNumber + " - " + childNumber;
            //            LoadHelper.printMessage("����" + message + "ʱ��������");
            //            LoadHelper.printExceptionStatckTrace(e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * ����.csv�ļ��е�"AddRefernceDocLink"���
     * �ù������еĲ���Ϊ���Թ���һ���Ѿ����ڵ��㲿���Ĳο���ϵ�����������ݿ���
     * Ҫ�������漰���㲿�����ĵ������������ݿ����Ѿ����ڵģ�����ᷢ���쳣��
     * @param nv               �������Ե���/ֵ ��
     *                         �������£�
     * �㲿������            partNumber
     * �ο��ĵ�����          docNumber
     * @param return_objects   <code>Vector</code> ��ȷ��������ķ��ؼ��ϡ�
     * @return boolean
     */
    public static boolean createPartRefernceDocLink(Hashtable nv,
            Vector return_objects)
    {
        //�����Ƿ�ɹ��ı�־
        boolean flag = false;
        String docNumber = "";
        try
        {
            docNumber = LoadHelper.getValue("docNumber", nv, 1);
            //������ĵ�BsoID
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
     * �����ĵ�����Ϣ�ĺ������ĵ���BosID��
     * @param docMasterNumber   �ĵ�����Ϣ�ĺ��롣
     * @return docBsoID         ���ĵ���BosID��
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
     * �����㲿����ŵõ��㲿�����а汾���㲿��ֵ����
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
            // ���ð汾���Ʒ���
            VersionControlService vcService = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");
            collection1 = vcService.allVersionsOf(master);
        }
        return collection1;
    }

    /**
     * ��ȡ�µ�����������������������ֻ���㲿�������������е�Լ��������
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

    /** Լ��������ʶ�� */
    private static final String SOURCING_FACTOR = "Sourcing Factor";

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(LoadPart.class);

    /** �ָ����� */
    public static final char CSV_DELIMITER = 44;

    /** �ӷָ����� */
    public static final char CSV_DELIMITER_SUB = 255;

    /** �㲿��BSOID�� */
    public static String PART_BSOID = "Part BsoID";

    /** �ڵ����� */
    private static String NODE_NAME = "Classification Node Name";

    /** ���ڵ㡣 */
    private static String PARENT_NODE = "Parent Classification Node";

    /**
     * ��ʼ�����㲿���ķ���ڵ㣨����1�������÷���2��
     * @param hashtable Hashtable ���Ա�
     * @param collection Vector δ�á�
     * @throws LoadDataException
     * @return boolean ����ɹ���ʶ��trueΪ�ɹ���falseΪʧ�ܡ�
     */
    public static boolean loadPartWithClassification(Hashtable hashtable,
            Vector collection) throws QMException
    {
        return beginLoadPartWithClassification(hashtable);
    }

    /**
     * ��ʼ�����㲿���ķ���ڵ㣨����2����
     * @param hashtable Hashtable ���Ա�
     * @throws LoadDataException
     * @return boolean ����ɹ���ʶ��trueΪ�ɹ���falseΪʧ�ܡ�
     */
    public static boolean beginLoadPartWithClassification(Hashtable hashtable)
            throws LoadDataException
    {
        Hashtable hashtable1 = new Hashtable();
        // ���ݲ�ͬ���������������ֵ��
        String structureClassName = LoadHelper.getValue("structureClassName",
                hashtable, hashtable1, 0);
        // ����ڵ������
        String nodeName = LoadHelper.getValue("nodeName", hashtable,
                hashtable1, 0);
        // ����ڵ�ĸ��ڵ�����
        String parentID = LoadHelper.getValue("parentNodeName", hashtable,
                hashtable1, 1);
        // ����ṹ��Ӧ�Ĳο�������������
        String refDefName = LoadHelper.getValue("refDefName", hashtable,
                hashtable1, 1);
        // ʵ����һ���ο����塣
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
     * ���������㲿���ķ���ڵ㣨����2����
     * @return boolean trueʱ�ɹ���falseʱʧ�ܡ�
     */
    public static boolean endLoadPartWithClassification(
            IBAHolderIfc ibaHolderIfc)
    {
        // ������㲿���ķ���ڵ�����
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
     * Ϊ�㲿����ӷ���ڵ㡣
     * @param ibaHolderIfc IBAHolderIfc����������ӵ���ߡ�
     * @param classificationNodeIfc ClassificationNodeIfc������ڵ�ֵ����
     */
    private static void addClassificationOfPart(IBAHolderIfc ibaHolderIfc,
            ClassificationNodeIfc classificationNodeIfc,ClassificationStructDefaultView classificationStructDefView)
            throws IBAConstraintException, QMException
    {
//        ClassificationStructDefaultView classificationStructDef = (ClassificationStructDefaultView) NavigationUtil
//                .getClassificationStructure(QMPartInfo.class.getName());
        // ����ṹ��Ӧ�Ĳο����������ԡ�
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
        // �жϵ�ǰ�����Ƿ����з����ʶ��
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
        // ����ڵ�Ĳο�����ֵ��
        boolean flag2 = true;
        // �������Լ��ϡ�
        final List attrDefList = new ArrayList(5);
        // ����ڵ�����Եļ��ϡ�
        final List attrOfNodelist = new ArrayList(5);
        processValueAndConstraint(cnDefaultView, attrDefList, attrOfNodelist);
        // ���������ͷ���ڵ㶼�е��������ԡ�
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
     * ɾ�����������ͷ���ڵ㹲ͬ���е����ԡ�
     * @param defAttrContainer
     * @param attrDefList
     * @param attrOfNodelist
     */
    private static void removeTogetherAttribute(
            DefaultAttributeContainer defAttrContainer, List attrDefList,
            List attrOfNodelist)
    {
        // ��ǰ�����Ѻ��е��������Լ��ϡ�
        final AttributeDefDefaultView aAttributeDefDV2[] = defAttrContainer
                .getAttributeDefinitions();
        final boolean aflag[] = new boolean[aAttributeDefDV2.length];
        for (int j1 = 0; j1 < aflag.length; j1++)
        {
            aflag[j1] = false;
        }
        for (int k1 = 0; k1 < attrOfNodelist.size(); k1++)
        {
            // ����ڵ��Ϻ��е��������ԡ�
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
            // �ӷ���ڵ����Ƴ���ǰ�������е��������ԡ�
            if(aflag[i2])
            {
                removeDefFromCollection(attrOfNodelist, aAttributeDefDV2[i2]);
            }
        }
        for (int k2 = 0; k2 < aAttributeDefDV2.length; k2++)
        {
            // ��attrDefList���Ƴ���ǰ�������е��������ԡ�
            removeDefFromCollection(attrDefList, aAttributeDefDV2[k2]);
        }
    }

    /**
     * ��ȡ����ڵ�ֵ����
     * @param classificationNodeName String������ڵ����ơ�
     * @param parentID String���÷���ڵ㸸�ڵ��BsoID��
     * @param classificationStructBsoID String������ṹ��BsoID��
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
     * �Ƿ�ӵ�з������ԡ�
     * @param defAttContainer DefaultAttributeContainer������������
     * @param clfStructDefView ClassificationStructDefaultView������ṹ
     * @return boolean ӵ�б�־��
     */
    protected static boolean isHasClassificationAttribute(
            DefaultAttributeContainer defAttContainer,
            ClassificationStructDefaultView clfStructDefView)
    {
        logger.debug("**********start hasClassificationAttribute**********");
        final AttributeDefDefaultView attributeDefDV[] = defAttContainer
                .getAttributeDefinitions();
        // �������Ƿ��з���ṹ�Ĳο����ԡ�
        boolean flag = false;
        // �������Ƿ��з���ṹ�Ĳο�ֵ��
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
     * ���ݲ���������ֵ�����µ�����ֵ��
     * @param flag1 boolean �жϵ�ǰ�����Ƿ����з����ʶ��
     * @param referenceDefView ReferenceDefView ����ṹ��Ӧ�Ĳο����������ԡ�
     * @param flag2 boolean ����ڵ�Ĳο�����ֵ��־��
     * @param list1 List �������Լ��ϡ�
     * @param referenceValueDV ReferenceValueDefaultView ����ڵ�Ĳο�����ֵ��
     * @param flag5 boolean �Ƿ�ѡȡ�˷���ڵ��־��
     * @param cnDefaultView ClassificationNodeDefaultView ����ڵ�
     * @param list7 ���б�����ӵ��������Լ��ϡ� ת�����ClassificationNodeDefaultView���͵ķ���ڵ�ļ��ϡ�
     * @param aDefListSelector AttrDefListSelector ѡ�еķ���ڵ���������Ե�ѡ������
     */
    private static List createNewValue(final boolean flag1,
            final ReferenceDefView referenceDefView, final boolean flag2,
            final List list1, final ReferenceValueDefaultView referenceValueDV,
            final boolean flag5,
            final ClassificationNodeDefaultView cnDefaultView,
            final List list7,
            DefaultAttributeContainer defaultattributecontainer)
    {
        // �Ƿ���ӷ���ڵ��ϵ����Եı�־
        // boolean isAddFlag = true;
        // �����б��ϵ��������Զ�Ӧ������ֵ��
        final int count = list7.size();
        // ����������ֵ���ϡ�
        final Collection collection6 = new ArrayList(5);
        // ����ڵ��������
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
                // �б��ϵ��������Զ�Ӧ�������е�����ֵ��
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
        // ����Լ������Ϊ��ѡ����������Զ�Ӧ������ֵ��
        for (int l3 = 0, listSize1 = list1.size(); l3 < listSize1; l3++)
        {
            final AbstractValueView aAbstractValueV1[] = defaultAC
                    .getAttributeValues((AttributeDefDefaultView) list1.get(l3));
            for (int l4 = 0; l4 < aAbstractValueV1.length; l4++)
            {
                collection6.add(aAbstractValueV1[l4]);
            }
        }
        // ���ݲ���������ֵ��ɾ������ԭ������ֵ��Լ�����������������ֵ��Լ��������
        // ������ֵ���ϡ�
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
     * ����ֵ��
     * @param collection Collection �������ϡ�
     * @return Collection ֵ���ϡ�
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
     * ������ֵ��
     * @param list List ֵ���ϡ�
     * @param map Map ֵӳ�䡣
     * @return Collection ��ֵ���ϡ�
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
     * ��ȡ�ο���ֵ���ϡ�
     * @param map Map ӳ�䡣
     * @return Map ֵӳ��
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
     * �������ڵ���������Ժ�Լ��������
     * @param cnDefaultView ClassificationNodeDefaultView ����ڵ�
     * @param attrConstraintList List �������Լ��ϡ�
     * @param attrOfNodelist List ����ڵ��Ϻ��е��������ԡ�
     * @throws CSMClassificationNavigationException
     * @throws QMRemoteException
     */
    private static void processValueAndConstraint(
            ClassificationNodeDefaultView cnDefaultView,
            final List attrConstraintList, List attrOfNodelist)
            throws CSMClassificationNavigationException, QMRemoteException
    {
        // ����ڵ㺬�е�������
        final DefaultAttributeContainer defAttrCont = (DefaultAttributeContainer) cnDefaultView
                .getAttributeContainer();
        // ����ڵ㺬�е��������ԡ�
        final AttributeDefDefaultView attrDefDefView[] = defAttrCont
                .getAttributeDefinitions();
        // ������ڵ㺬�е���������ѭ����ӵ�attrOfNodelist�ϡ�
        for (int i1 = 0; i1 < attrDefDefView.length; i1++)
        {
            attrOfNodelist.add(attrDefDefView[i1]);
        }
        // ����ڵ㺬�е�Լ��������
        final AbstractCSMAttributeConstraintView abstractCSMACV[] = cnDefaultView
                .getCSMAttributeConstraints();
        // ��Լ���������д���
        for (int l1 = 0; l1 < abstractCSMACV.length; l1++)
        {
            final ValueConstraint valueCons = abstractCSMACV[l1]
                    .getValueConstraint();
            // ���Լ����������Ϊ��ѡ�
            if(valueCons instanceof ValueRequired)
            {
                // �ڵ����÷�Χ��Լ��������
                if(abstractCSMACV[l1] instanceof CSMContainerConstraintDefaultView)
                {
                    for (int l2 = 0; l2 < attrDefDefView.length; l2++)
                    {
                        addDefToCollection(attrConstraintList, attrDefDefView[l2]);
                    }
                }
                // �����������÷�Χ��Լ��������
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
     * ����������Ե�����
     * @param list List ������
     * @param attrDefDV AttributeDefDefaultView ������������������
     */
    public static void addDefToCollection(final List attrList,
            final AttributeDefDefaultView attrDefDV)
    {
        // �Ƿ�ӵ�и��������Եı�־
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
     * ���������Ƴ��������ԡ�
     * @param collection Collection ������
     * @param attributeDefDV AttributeDefDefaultView ������������������
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
     * ��ӷ�������Լ����
     * @param attributeCIfc AttributeConstraintIfc ����Լ��ֵ����
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
     * �������ֵԼ����
     * @param collection Collection �������ϡ�
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
     * ɾ�����������ķ���־û�Լ����
     * @param defaultAttributeC DefaultAttributeContainer����ɾ��Լ������������
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
     * �ж��Ƿ��ǳ־û�Լ����
     * @param attributeCIfc AttributeConstraintIfc ����Լ��ֵ����
     * @return boolean �־û���־��
     */
    protected static boolean isPersistedConstraint(
            final AttributeConstraintIfc attributeCIfc)
    {
        return PersistHelper.isPersistent(attributeCIfc);
    }

    /**
     * �Ƴ���������Լ����(��ӷ��� �� ɾ������ ʱ�������˴˷���)
     * @param defaultAttributeC DefaultAttributeContainer
     * @param structure ClassificationStructDefaultView
     * @return AttributeConstraintIfc ����Լ��ֵ����
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
                    // ��singleԼ������vector����󣬴Ӷ�ȷ���˷����ķ���ֵattributeCIfc Ϊ
                    // singleԼ��������������Լ����
                    // ����ӷ���ʱ���ں����ִ�й����л��attributeCIfc���õ����������С�
                    // (��ӷ���ʱ���������һ��singleԼ��)
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
     * ��û������
     * @param s String ��������
     * @return Object ����Ķ���
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
