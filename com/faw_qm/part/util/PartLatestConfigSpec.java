/**
 * 生成程序PartLatestConfigSpec.java 1.0 Sep 19, 2008 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料 未经本公司授权不得非法传播和盗用 可在本公司授权范围内使用本程序 保留所有权利
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
 * <p>Title: 零部件的最新版本配置规范。</p>
 * <p>Description: 和LatestConfigSpec相似。过滤非最新版本的跌代对象，每个Mastered对象返回各个版本的最新版序。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class PartLatestConfigSpec extends LatestConfigSpec
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * 附加查询条件，不附加任何查询条件。
     * @param qmQuery QMQuery 查询语句。
     * @return QMQuery 增加查询条件之后的查询语句。
     * @throws ConfigException 当qmquery == null时，抛出异常信息："参数*的值不能为空"。
     */
    public QMQuery appendSearchCriteria(QMQuery qmQuery) throws ConfigException
    {
        return super.appendSearchCriteria(qmQuery);
    }

    /**
    * 根据如下规则处理指定的迭代对象集合，并返回符合条件的迭代对象集合：
    * <br>1.如果当前用户是受MasteredIfc管理的业务对象的某些最后版本的使用者，返回这些版本；
    * <br>2.如果最后版本不止一个，则根据版本号比较返回最大版本号的版本；
    * <br>3.如果结果还是不止一个，则根据迭代号返回最后的版本；
    * <br>4.如果迭代号相同，则根据时间戳返回最后的版本。一个MasteredIfc只能返回对应的一个最后版本。
    * @param collection 迭代对象集合。
    * @return collection 符合条件的迭代对象集合。
    * @throws ConfigException 当查询业务类不是IteratedIfc时，抛出异常信息："QMquery无效:它或者为空，或者未被设置为查询*对象"。
    */
    public Collection process(Collection collection) throws ConfigException
    {
        if(ConfigDebug.isDebugOut())
            ConfigDebug.log("PartLatestConfigSpec: process(Collection) "
                    + "enter..  collection is " + collection);
        List vector = ConfigSpecHelper.transform(collection);
        //key是主信息的BsoId和大版本，value是值对象。
        HashMap hashMap = new HashMap();
        //值是主信息的BsoId和大版本
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
                }//endif 如果obj[0]没被使用，或者被当前用户使用，并且iteratedIfc为空
                /*如果以上情况都不成立，并且obj[0]是Versioned的实例，比较iteratedIfc与obj[0]的版本序列号*/
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
                        }//endif obj[0]的版本大于iteratedIfc的版本号
                        else if(objVerID.equals(iterateVerID))
                            flag4 = true;
                    }
                    //if(flag4 || (!(obj[0] instanceof VersionedIfc)))
                    if(flag4)
                    {
                        //如果flag4为真（iteratedIfc与obj[0]序列号相等），或者obj[0]不是Versioned的实例，则比较它们的迭代号
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
                        //如果iteratedIfc与obj[0]的迭代号相等，则比较它们的时间戳.
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
                                    .predecessorOf((IteratedIfc) obj[0]); //获得obj[0]前一版本
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
                            }//endif 如果iteratedIfc与obj[0]的时间戳相等并且它们的前驱也是同一个对象，
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
