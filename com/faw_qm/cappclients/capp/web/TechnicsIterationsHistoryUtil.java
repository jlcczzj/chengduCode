/** 生成程序TechnicsIterationsHistoryUtil.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.web;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsMasterIfc;
import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;


/**
 * <p>Title: 查看工艺规程的版序历史</p>
 * <p>Description: 本类提供的方法为廋客户界面使用</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class TechnicsIterationsHistoryUtil
{

    public TechnicsIterationsHistoryUtil()
    {
    }


    /**
     * 获得瘦客户端头部的必要显示信息(工艺卡的编号和名称)
     * @param Bso 工艺卡的BsoID
     * return 工艺卡的编号和名称
     */
    public static String getVersionHead(String Bso)
    {

        String id = Bso.trim();
        String technicsNum;
        String technicsName;
        BaseValueIfc obj;
        String rStr;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService("PersistService");
            obj = pService.refreshInfo(id);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
            obj = null;
        }
        if (obj != null)
        {
            if (obj instanceof QMTechnicsIfc)
            {
                technicsNum = ((QMTechnicsIfc) obj).getTechnicsNumber();
                technicsName = ((QMTechnicsIfc) obj).getTechnicsName();
                rStr = "工艺规程编号：" + technicsNum + " ---------" + " 工艺规程名称：" +
                       technicsName;
                return rStr;
            }
            else
            {
                technicsNum = ((QMTechnicsMasterInfo) obj).getTechnicsNumber();
                technicsName = ((QMTechnicsMasterInfo) obj).getTechnicsName();
                String[] vv = new String[2];
                rStr = "工艺规程编号：" + technicsNum + " ---------" + " 工艺规程名称：" +
                       technicsName;
                return rStr;
            }
            //String iconUrl=pb.getIconName("StandardIcon");
            //vv.addElement(id);
        }
        else
        {
            rStr = "工艺规程编号： ---------" + " 工艺规程名称： ";
            return rStr;
        }
    }


    /**
     * 获得该对象的版序历史对象的集合(所有的小版本的集合)
     * @param BsoID 工艺卡对象的BsoID
     * @return Collection 版本历史对象的集合
     */
    public static Collection getVersionsCollection(String BsoID)
    {
        QMTechnicsMasterIfc myMasterInfo;
        Collection myCollection = new Vector();
        try
        {
            PersistService service = (PersistService) EJBServiceHelper.
                                     getService("PersistService");
            myMasterInfo = (QMTechnicsMasterIfc) service.refreshInfo(BsoID);

            VersionControlService service1 = (VersionControlService)
                                             EJBServiceHelper.getService(
                    "VersionControlService");
            myCollection = service1.allIterationsOf((MasteredIfc) myMasterInfo);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
        }
        return myCollection;
    }


    /**
     * 获得该对象的版序历史对象的集合，得到符合
     * 瘦客户端显示必须的内容的集合
     * @param BsoID 工艺卡对象的BsoID
     * @return Collection String数组的对象集合，数组内容为:
     *         版本对象的BsoID,版本,对象状态,项目名称,创建者,创建时间
     *
     */
    public static Collection getMyCollection(String BsoID)
    {

        Collection myCo = getVersionsCollection(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMTechnicsIfc myInfo;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        Collection myCollection = new Vector();
        String versionBsoID;

        for (int i = 0; i < myCo.size(); i++)
        {
            //得到小版本
            myInfo = (QMTechnicsIfc) (aa[i]);
            //得到需要的值

            versionID = myInfo.getVersionValue();
            if (myInfo.getLifeCycleState() != null)
            {
                objectState = (myInfo.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
                //项目组
            }
            ProjectIfc project = null;
            try
            {
                PersistService pService = (PersistService) EJBServiceHelper.
                                          getService("PersistService");
                if (((LifeCycleManagedIfc) myInfo).getProjectId() != null)
                {
                    project = (ProjectIfc) pService.refreshInfo(((
                            LifeCycleManagedIfc) myInfo).getProjectId());

                }
                objectCreator = CappServiceHelper.getUserNameByID(myInfo.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            if (project != null)
            {
                projectName = CappServiceHelper.getIdentity(project);
            }
            else
            {
                projectName = "";
            }
            createTime = (myInfo.getModifyTime()).toString();
            versionBsoID = myInfo.getBsoID();
            //构造数组
            String[] myVersionArray1 =
                    {versionBsoID, versionID, objectState,
                    projectName, objectCreator, createTime};
            //加到集合中
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }
    //add by wangh on 20080415
    public static Collection getCurrentCollection(String BsoID)
    {

        Collection myCo = getCurrentVersionsCollection(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMTechnicsIfc myInfo;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        String modifyTime;//liunan 2011-10-09 更新时间
        Collection myCollection = new Vector();
        String versionBsoID;

        for (int i = 0; i < myCo.size(); i++)
        {
            //得到小版本
            myInfo = (QMTechnicsIfc) (aa[i]);
            //得到需要的值

            versionID = myInfo.getVersionValue();
            if (myInfo.getLifeCycleState() != null)
            {
                objectState = (myInfo.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
                //项目组
            }
            ProjectIfc project = null;
            try
            {
                PersistService pService = (PersistService) EJBServiceHelper.
                                          getService("PersistService");
                if (((LifeCycleManagedIfc) myInfo).getProjectId() != null)
                {
                    project = (ProjectIfc) pService.refreshInfo(((
                            LifeCycleManagedIfc) myInfo).getProjectId());

                }
                objectCreator = CappServiceHelper.getUserNameByID(myInfo.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            if (project != null)
            {
                projectName = CappServiceHelper.getIdentity(project);
            }
            else
            {
                projectName = "";
            }
            //CCBegin by liunan 2011-10-09 更新时间
            createTime = (myInfo.getCreateTime()).toString();
            modifyTime = (myInfo.getModifyTime()).toString();
            //CCEnd by liunan 2011-10-09
            versionBsoID = myInfo.getBsoID();
            //构造数组
            String[] myVersionArray1 =
                    {versionBsoID, versionID, objectState,
                    //projectName, objectCreator, createTime};
                    projectName, objectCreator, createTime, modifyTime};//liunan 2011-10-09 更新时间
            //加到集合中
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }
    
    public static Collection getCurrentVersionsCollection(String BsoID) {
    	QMTechnicsIfc myInfo;
        Collection myCollection = new Vector();
        try {
            PersistService service = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            myInfo = (QMTechnicsIfc)service
                    .refreshInfo(BsoID);
            VersionControlService service1 = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");

            myCollection = service1.getAllPredecessor((IteratedIfc)myInfo);
        } catch (Exception sle) {
            sle.printStackTrace();
        }

        return myCollection;
    }
    //add end

}