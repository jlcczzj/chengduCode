/** ���ɳ���IterationsViewUtil.java	  1.0  2003/05/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/12/25 ���� �޸�ԭ��TD2741���ڲ鿴������ʹ�ýṹʱ�����һ���䲻�������ù淶���Ӽ��ı�����ӣ�ҳ�����
 */
package com.faw_qm.part.client.web.versionWeb;

import java.util.Collection;
import java.util.Vector;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;


/**
 *
 * <p>Title: �鿴������ʷ�����඼�Ǿ�̬���������ݿͻ�����ʾ�á�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author wawa
 * @version 1.0
 */
public class IterationsViewUtil
{

    /**
     * ���캯����
     */
    public IterationsViewUtil()
    {
    }


    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ��
     *
     * @param Bso �����BsoID��
     * @return String
     */
    public static String getVersionHead(String Bso)
    {
        String id = Bso.trim();
        String partNum;
        String partName;
        BaseValueIfc pb;
        String rStr;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService("PersistService");
            pb = pService.refreshInfo(id);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
            pb = null;
        }
        if (pb != null)
        {
            if (pb instanceof QMPartIfc)
            {
                partNum = ((QMPartIfc) pb).getPartNumber();
                partName = ((QMPartIfc) pb).getPartName();
                rStr = "��ţ� " + partNum + "         " + "���ƣ� " + partName;
                return rStr;
            }
            else
            {
                partNum = ((QMPartMasterIfc) pb).getPartNumber();
                partName = ((QMPartMasterIfc) pb).getPartName();
                String[] vv = new String[2];
                rStr = "��ţ� " + partNum + "         " + "���ƣ� " + partName;
                return rStr;
            }
        }
        else
        {
            rStr = "��ţ�          " + "���ƣ� ";
            return rStr;
        }
    }


    /**
     * ͨ���õ����󣬻�øö���İ�����ʷ����ļ��ϡ�
     * @param BsoID Part�����BsoID��
     * @return Collection ͨ���õ����󣬻�øö���İ汾��ʷ����ļ��ϡ�
     * @deprecated replace with getAllPredecessor(String partBsoID)
     */
    //ͨ���õ���part�����ð汾��Ϣ,�õ����е�С�汾�ļ���
    public static Collection getVersionsCollection(String BsoID)
    {
        QMPartMasterIfc myMasterIfc;
        Collection myCollection = new Vector();
        try
        {
            PersistService service = (PersistService) EJBServiceHelper.
                                     getService("PersistService");
            myMasterIfc = (QMPartMasterIfc) service.refreshInfo(BsoID);

            VersionControlService service1 = (VersionControlService)
                                             EJBServiceHelper.getService(
                    "VersionControlService");
            myCollection = service1.allIterationsOf((MasteredIfc) myMasterIfc);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
        }
        return myCollection;
    }
    
    //��ð汾С�����е���Ϣ����д���µļ�������
    /**
     * ͨ���õ����󣬻�øö���İ�����ʷ����ļ��ϣ��õ������ݿͻ�����ʾ��������ݵļ��ϡ�
     * @param BsoID String Part�����BsoID��
     * @return Collection String����Ķ��󼯺ϣ���������Ϊ�汾�����BsoID,�汾,����״̬,��Ŀ����,������,����ʱ��,����ע�͡�
     */
    public static Collection getMyCollection(String BsoID)
    {
        Collection myCo = getAllPredecessor(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMPartIfc myIfc;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        Collection myCollection = new Vector();
        String versionBsoID;
        String iterationNote;

        for (int i = 0; i < myCo.size(); i++)
        {
            //�õ�С�汾
            myIfc = (QMPartIfc) (aa[i]);
            //�õ���Ҫ��ֵ

            versionID = myIfc.getVersionValue();
            if (myIfc.getLifeCycleState() != null)
            {
                objectState = (myIfc.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
            }
            projectName = myIfc.getProjectName();
            try
            {
                objectCreator = PartServiceHelper.getUserNameByID(myIfc.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            createTime = (myIfc.getCreateTime()).toString();
            iterationNote = myIfc.getIterationNote();
            versionBsoID = myIfc.getBsoID();
            //��������
            String[] myVersionArray1 =
                                       {versionBsoID, versionID, objectState,
                                       projectName, objectCreator, createTime, iterationNote};
            //�ӵ�������
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }
    
    /**
     * ͨ���õ����󣬻�øö���İ�����ʷ����ļ��ϣ��õ������ݿͻ�����ʾ��������ݵļ��ϡ�
     * @param BsoID String PartMaster�����BsoID��
     * @return Collection String����Ķ��󼯺ϣ���������Ϊ�汾�����BsoID,�汾,����״̬,��Ŀ����,������,����ʱ��,����ע�͡�
     */
    public static Collection getAllVersionCollection(String masterBsoId)//CR1
    {
        Collection myCo =getVersionsCollection(masterBsoId);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMPartIfc myIfc;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        String modifyTime;//liunan 2011-09-30 �鿴����ʱ��ʾ����ʱ�䡣
        Collection myCollection = new Vector();
        String versionBsoID;
        String iterationNote;

        for (int i = 0; i < myCo.size(); i++)
        {
            //�õ�С�汾
            myIfc = (QMPartIfc) (aa[i]);
            //�õ���Ҫ��ֵ

            versionID = myIfc.getVersionValue();
            if (myIfc.getLifeCycleState() != null)
            {
                objectState = (myIfc.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
            }
            projectName = myIfc.getProjectName();
            try
            {
                objectCreator = PartServiceHelper.getUserNameByID(myIfc.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            createTime = (myIfc.getCreateTime()).toString();
            modifyTime = (myIfc.getModifyTime()).toString();//liunan 2011-09-30 �鿴����ʱ��ʾ����ʱ�䡣
            iterationNote = myIfc.getIterationNote();
            versionBsoID = myIfc.getBsoID();
            //��������
            String[] myVersionArray1 =
                                       {versionBsoID, versionID, objectState,
                                       //projectName, objectCreator, createTime, iterationNote};
                                       projectName, objectCreator, createTime, modifyTime, iterationNote};//liunan 2011-09-30 �鿴����ʱ��ʾ����ʱ�䡣
            //�ӵ�������
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }

    /**
     * �����㲿����id,�����iba����ֵ�ȡ�
     * @param partBsoID String
     * @return Vector ���е�Ԫ��Ϊ��ά�ַ�0������,��һά��Ÿ�iba����ֵ�����Զ��������,
     * �ڶ�ά���iba���Ե�ֵ��
     */
    public Vector getIBANameAndValue(String partBsoID)
    {
        IBAHolderIfc part = null;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService(
                    "PersistService");
            part = (IBAHolderIfc) pService.refreshInfo(partBsoID);
            IBAValueService ibaService = (IBAValueService) EJBServiceHelper.
                                         getService("IBAValueService");
            part = (IBAHolderIfc) ibaService.refreshAttributeContainer(part, null, null, null);
        }
        catch (ServiceLocatorException ex)
        {
            ex.printStackTrace();
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        DefaultAttributeContainer container = (DefaultAttributeContainer) part.
                                              getAttributeContainer();
        if (container == null)
        {
            container = new DefaultAttributeContainer();
        }
        AbstractValueView[] values = container.getAttributeValues();
        Vector retVal = new Vector();
        for (int i = 0; i < values.length; i++)
        {
            String[] nameAndValue = new String[2];
            AbstractValueView value = values[i];
            AttributeDefDefaultView definition = value.getDefinition();
            nameAndValue[0] = definition.getName();
            if (value instanceof AbstractContextualValueDefaultView)
            {
                nameAndValue[1] = ((AbstractContextualValueDefaultView) value).
                                  getValueAsString();
            }
            else
            {
                nameAndValue[1] = ((ReferenceValueDefaultView) value).
                                  getLocalizedDisplayString();
            }
            retVal.add(nameAndValue);
            nameAndValue = null;
        }
        return retVal;
    }
    
    /**
     * ��ȡ��ǰ��������һ��ǰ����֧��Ϊ������ʷ����ʹ�á��統ǰΪA.3���򷵻ؼ���ΪA.3,A.2,A.1��
     * @param partBsoID �������BsoID��
     * @return ���򼯺ϡ�
     * @throws QMException
     */
    public static Collection getAllPredecessor(String partBsoID)
    {
        Collection coll = null;
        try
        {
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMPartIfc partIfc = (QMPartIfc) persistService.refreshInfo(partBsoID);
            VersionControlService vcService = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");
            coll = vcService.getAllPredecessor(partIfc);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return coll;
    }
}
