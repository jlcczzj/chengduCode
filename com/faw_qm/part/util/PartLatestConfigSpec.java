/**
 * ���ɳ���PartLatestConfigSpec.java 1.0 Sep 19, 2008 ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ���� δ������˾��Ȩ���÷Ƿ������͵��� ���ڱ���˾��Ȩ��Χ��ʹ�ñ����� ��������Ȩ��
 */
package com.faw_qm.part.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.faw_qm.config.exception.ConfigException;
import com.faw_qm.config.util.ConfigDebug;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.VersionedIfc;

/**
 * <p>Title: �㲿�������°汾���ù淶��</p>
 * <p>Description: ��LatestConfigSpec���ơ����˷����°汾�ĵ�������ÿ��Mastered���󷵻ظ����汾�����°���</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class PartLatestConfigSpec extends LatestConfigSpec
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * ���Ӳ�ѯ�������������κβ�ѯ������
     * @param qmQuery QMQuery ��ѯ��䡣
     * @return QMQuery ���Ӳ�ѯ����֮��Ĳ�ѯ��䡣
     * @throws ConfigException ��qmquery == nullʱ���׳��쳣��Ϣ��"����*��ֵ����Ϊ��"��
     */
    public QMQuery appendSearchCriteria(QMQuery qmQuery) throws ConfigException
    {
        return super.appendSearchCriteria(qmQuery);
    }

    /**
    * �������¹�����ָ���ĵ������󼯺ϣ������ط��������ĵ������󼯺ϣ�
    * <br>1.�����ǰ�û�����MasteredIfc�����ҵ������ĳЩ���汾��ʹ���ߣ�������Щ�汾��
    * <br>2.������汾��ֹһ��������ݰ汾�űȽϷ������汾�ŵİ汾��
    * <br>3.���������ǲ�ֹһ��������ݵ����ŷ������İ汾��
    * <br>4.�����������ͬ�������ʱ����������İ汾��һ��MasteredIfcֻ�ܷ��ض�Ӧ��һ�����汾��
    * @param collection �������󼯺ϡ�
    * @return collection ���������ĵ������󼯺ϡ�
    * @throws ConfigException ����ѯҵ���಻��IteratedIfcʱ���׳��쳣��Ϣ��"QMquery��Ч:������Ϊ�գ�����δ������Ϊ��ѯ*����"��
    */
    public Collection process(Collection collection) throws ConfigException
    {
        if(ConfigDebug.isDebugOut())
            ConfigDebug.log("PartLatestConfigSpec: process(Collection) "
                    + "enter..  collection is " + collection);
        List vector = ConfigSpecHelper.transform(collection);
        //key������Ϣ��BsoId�ʹ�汾��value��ֵ����
        HashMap hashMap = new HashMap();
        //ֵ������Ϣ��BsoId�ʹ�汾
        List masterIdAndVersionList = new ArrayList();
        String versionId = null;
        for (int i = 0; i < vector.size(); i++)
        {
            Object[] obj = (Object[]) vector.get(i);
            if(!(obj[0] instanceof VersionedIfc))
            {
                Object[] aobj = {"VersionedIfc"};
                throw new ConfigException(
                        "com.faw_qm.config.util.ConfigResource", "9", aobj);
            }
            versionId = ((VersionedIfc) obj[0]).getVersionID();
            MasterIfc masterIfc = (MasterIfc) ((VersionedIfc) obj[0])
                    .getMaster();
            ConfigDebug.log("in latestconfigsepc process:masterIfc = "
                    + masterIfc);
            String masterID = masterIfc.getBsoID();
            ConfigDebug.log("in latestconfigsepc process:masterID = "
                    + masterID);
            VersionedIfc iteratedIfc = (VersionedIfc) hashMap.get(masterID
                    + versionId);
            ConfigDebug.log("in latestconfigsepc process:iteratedIfc = "
                    + iteratedIfc);
            if(!masterIdAndVersionList.contains(masterID + versionId))
                masterIdAndVersionList.add(masterID + versionId);
            boolean flag4 = false;
            {
                if(iteratedIfc == null)
                {
                    hashMap.put(masterID + versionId, obj[0]);
                    ConfigDebug
                            .log("iteratedIfc == nul     hashMap.put(masterID+versionId,obj[0]: masterID: "
                                    + masterID
                                    + versionId
                                    + " Obj[0]:"
                                    + obj[0]);
                }//endif ���obj[0]û��ʹ�ã����߱���ǰ�û�ʹ�ã�����iteratedIfcΪ��
                /*������������������������obj[0]��Versioned��ʵ�����Ƚ�iteratedIfc��obj[0]�İ汾���к�*/
                else
                //if(!flag3 || flag1 || (!flag1 && flag3))
                {
                    flag4 = false;
                    if(obj[0] instanceof VersionedIfc)
                    {
                        String objVerID = ((VersionedIfc) obj[0])
                                .getVersionID();
                        ConfigDebug
                                .log("in latestconfigsepc process:objVerID = "
                                        + objVerID);
                        String iterateVerID = ((VersionedIfc) iteratedIfc)
                                .getVersionID();
                        ConfigDebug
                                .log("in latestconfigsepc process:iterateVerID = "
                                        + iterateVerID);
                        if(objVerID.compareTo(iterateVerID) > 0)
                        {
                            hashMap.put(masterID+versionId, obj[0]);
                            ConfigDebug
                                    .log("objVerID.compareTo(iterateVerID)>0    hashMap.put(masterID,obj[0]: masterID: "
                                            + masterID + " Obj[0]:" + obj[0]);
                        }//endif obj[0]�İ汾����iteratedIfc�İ汾��
                        else if(objVerID.equals(iterateVerID))
                            flag4 = true;
                    }
                    //if(flag4 || (!(obj[0] instanceof VersionedIfc)))
                    if(flag4)
                    {
                        //���flag4Ϊ�棨iteratedIfc��obj[0]���к���ȣ�������obj[0]����Versioned��ʵ������Ƚ����ǵĵ�����
                        flag4 = false;
                        String objID = ((IteratedIfc) obj[0]).getIterationID();
                        ConfigDebug.log("in latestconfigsepc process:objID = "
                                + objID);
                        String iterateID = iteratedIfc.getIterationID();
                        ConfigDebug
                                .log("in latestconfigsepc process:iterateID = "
                                        + iterateID);
                        if(Integer.parseInt(objID) > Integer
                                .parseInt(iterateID))
                        {
                            hashMap.put(masterID + versionId, obj[0]);
                            ConfigDebug
                                    .log("Integer.parseInt(objID)>Integer.parseInt(iterateID)    hashMap.put(masterID,obj[0]: masterID: "
                                            + masterID + " Obj[0]:" + obj[0]);
                        }
                        else if(Integer.parseInt(objID) == Integer
                                .parseInt(iterateID))
                            flag4 = true;
                    }
                    if(flag4)
                    {
                        //���iteratedIfc��obj[0]�ĵ�������ȣ���Ƚ����ǵ�ʱ���.
                        flag4 = false;
                        Timestamp objTime = ((BaseValueIfc) obj[0])
                                .getCreateTime();
                        ConfigDebug
                                .log("in latestconfigsepc process:objTime = "
                                        + objTime);
                        Timestamp iterateTime = iteratedIfc.getCreateTime();
                        ConfigDebug
                                .log("in latestconfigsepc process:iterateTime = "
                                        + iterateTime);
                        if(objTime.after(iterateTime))
                        {
                            hashMap.put(masterID + versionId, obj[0]);
                            ConfigDebug
                                    .log("objTime.after(iterateTime)   hashMap.put(masterID,obj[0]: masterID: "
                                            + masterID + " Obj[0]:" + obj[0]);
                        }
                        else if(objTime.equals(iterateTime))
                            flag4 = true;
                    }
                    if(flag4)
                    {
                        try
                        {
                            VersionControlService vcService = (VersionControlService) EJBServiceHelper
                                    .getService("VersionControlService");
                            IteratedIfc objIfc = (IteratedIfc) vcService
                                    .predecessorOf((IteratedIfc) obj[0]); //���obj[0]ǰһ�汾
                            if(objIfc != null
                                    && PersistHelper.isEquivalent(objIfc,
                                            iteratedIfc))
                            {
                                hashMap.put(masterID + versionId, obj[0]);
                                ConfigDebug
                                        .log("objIfc !=null && PersistHelper.isEquivalent(objIfc,iteratedIfc)   hashMap.put(masterID,obj[0]: masterID: "
                                                + masterID
                                                + " Obj[0]:"
                                                + obj[0]);
                            }//endif ���iteratedIfc��obj[0]��ʱ�����Ȳ������ǵ�ǰ��Ҳ��ͬһ������
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            throw new ConfigException(ex);
                        }
                    }
                }
            }
        }
        if(ConfigDebug.isDebugOut())
            ConfigDebug.log("hashMap:   " + hashMap);
        Collection collection2 = hashMap.values();
        if(ConfigDebug.isDebugOut())
            ConfigDebug.log("collection2==" + collection2);
        List resultVector = new Vector();
        resultVector = ConfigSpecHelper.transform(collection2);
        return resultVector;
    }
}
