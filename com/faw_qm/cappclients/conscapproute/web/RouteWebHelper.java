/** 
 * 生成程序 RouteWebHelper.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.web;

import com.faw_qm.framework.service.*;
import com.faw_qm.framework.exceptions.*;
import com.faw_qm.persist.ejb.service.*;
import com.faw_qm.util.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.users.model.*;
import java.util.*;
import com.faw_qm.lifecycle.util.*;
import com.faw_qm.project.ejb.service.*;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.part.model.*;
import com.faw_qm.identity.*;
import com.faw_qm.lifecycle.model.*;
import com.faw_qm.project.model.*;
import com.faw_qm.technics.consroute.model.*;

/**
 * <p> Title:为Web页提供服务 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class RouteWebHelper
{
    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    public RouteWebHelper()
    {}

    /**
     * 根据用户BsoID获得用户名
     * @param userID 用户BsoID
     * @return 用户名
     * @throws QMException
     */
    public static String getUserNameByID(String userID) throws QMException
    {
        if(verbose)
            System.out.println("capp.util.CappServiceHelper.getUserNameByID() begin...");

        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        UserInfo userInfo = (UserInfo)pService.refreshInfo(userID);
        String userName = userInfo.getUsersDesc();
        if(verbose)
            System.out.println("capp.util.CappServiceHelper.getUserNameByID() end...return: " + userName);
        return userName;
    }

    /**
     * 获取业务对象的标准图标名称。
     * @param info 业务对象
     * @return String
     */
    public static String getStandardIconName(BaseValueIfc info)
    {
        String icon = info.getIconName("StandardIcon");
        if(icon != null && icon.length() > 0)
        {
            String sub = icon.substring(0, 1);
            if(sub.equals("/"))
            {
                String icon1 = "";
                for(int i = 1;i < icon.length();i++)
                {
                    icon1 = icon1 + icon.substring(i, i + 1);
                }
                return icon1;
            }else
            {
                return icon;
            }
        }else
        {
            return "";
        }
    }

    /**
     * 获取所有生命周期状态
     * @return 所有生命周期状态(State集合)
     * @throws QMException
     */
    public static Vector findAllLifeCycleState() throws QMException
    {
        Vector vector = new Vector(42);
        LifeCycleState state = LifeCycleState.newLifeCycleState();
        LifeCycleState allState[] = state.getLifeCycleStateSet();
        for(int i = 0;i < allState.length;i++)
            vector.add(allState[i]);
        return vector;
    }//end

    /**
     * 获取所有项目组。
     * @return Vector 所有项目组
     * @exception com.faw_qm.framework.exceptions.QMException
     */
    public static Vector findAllProjectTeamInfo() throws QMException
    {
        Vector vector = null;
        try
        {
            //获取项目服务
            ProjectService projService = (ProjectService)EJBServiceHelper.getService("ProjectService");
            //获取所有可用的项目
            vector = (Vector)projService.getCandidateProjects();
        }catch(Exception e)
        {
            throw new QMException(e);
        }
        if(vector == null)
            vector = new Vector(0);
        return vector;
    }

    /**
     * 获得所有的路线级别
     * @return 路线级别
     */
    public static Vector findAllRouteListLevel()
    {
        Vector v = new Vector();
        v.addElement(RouteListLevelType.FIRSTROUTE.getDisplay());
        v.addElement(RouteListLevelType.SENCONDROUTE.getDisplay());
        return v;
    }

    /**
     * 获得零部件主信息对象的bsoID
     * @param bsoID 零部件值对象的bsoID
     * @return 零部件主信息对象QMPartMaster的bsoID
     */
    public static String getPartMasterID(String bsoID)
    {
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            QMPartInfo info = (QMPartInfo)pService.refreshInfo(bsoID);
            return info.getMaster().getBsoID();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 把指定的业务对象的bsoID按照升序排列后返回
     * @param originalIDs 业务对象的bsoID的数组
     * @return 经排序后的bsoID数组
     */
    public static String[] sortedIterate(String[] originalIDs)
    {
        if(originalIDs != null && originalIDs.length > 0)
        {
            for(int j = originalIDs.length;j > 0;j--)
            {
                String temp;
                String bsoID1;
                String bsoID2;
                for(int i = 0;i < j - 1;i++)
                {
                    bsoID1 = (String)originalIDs[i];
                    bsoID2 = (String)originalIDs[i + 1];
                    //由于bsoid号是升序，按bsoid确定版序。（可排出同一版本的原本和副本的顺序。）
                    if(bsoID1.compareTo(bsoID2) > 0)
                    {
                        //进行替换
                        temp = originalIDs[i];
                        originalIDs[i] = originalIDs[i + 1];
                        originalIDs[i + 1] = temp;
                    }
                }
            }
        }
        return originalIDs;
    }

    /**
     * 获得指定业务对象的对象类型和标识
     * @param bsoID 业务对象bsoID
     * @return 对象类型和标识
     */
    public static String getIdentity(String bsoID)
    {
        String s = "";
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            BaseValueInfo obj = (BaseValueInfo)pService.refreshInfo(bsoID);
            if(obj instanceof BaseValueInfo)
            {
                //通过标识工厂获得与给定值对象对应的显示标识对象。
                DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(obj);
                //获得对象类型 + 标识
                s = displayidentity.getLocalizedMessage(null).trim();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 由路线表的BsoID获得其生命周期\项目\状态\是否等待升级
     * @param id 路线表的BsoID
     * @return 生命周期\项目\状态\是否等待升级
     */
    public static String[] getLifeCycleByID(String id)
    {
        if(verbose)
            System.out.println("RouteWebHelper.getLifeCycleByID() begin..id=" + id);
        String name = "";
        String lifecycleID = "";
        String projectName = "";
        String objectState = "";
        String lifeCycleAtGate = "";
        try
        {
            PersistService service = (PersistService)EJBServiceHelper.getPersistService();
            TechnicsRouteListInfo objInfo = (TechnicsRouteListInfo)service.refreshInfo(id);
            //获得生命周期名
            if(((LifeCycleManagedIfc)objInfo).getLifeCycleTemplate() != null)
            {
                lifecycleID = ((LifeCycleManagedIfc)objInfo).getLifeCycleTemplate();
                LifeCycleTemplateInfo info = (LifeCycleTemplateInfo)service.refreshInfo(lifecycleID);
                if(info != null)
                    name = info.getLifeCycleName();
            }
            if(((LifeCycleManagedIfc)objInfo).getProjectId() != null)
            {
                ProjectIfc project = (ProjectIfc)service.refreshInfo(((LifeCycleManagedIfc)objInfo).getProjectId());
                //获得项目组名
                if(project != null)
                    projectName = project.getName();
            }
            //获得对象的状态
            if(objInfo.getLifeCycleState() != null)
                objectState = (objInfo.getLifeCycleState()).getDisplay();
            //是否等待升级
            lifeCycleAtGate = new Boolean(objInfo.getLifeCycleAtGate()).toString();

        }catch(QMException e)
        {
            e.printStackTrace();
        }
        String[] array = {name, lifecycleID, projectName, objectState, lifeCycleAtGate};
        if(verbose)
            System.out.println("RouteWebHelper.getLifeCycleByID() end..return is " + name);
        return array;
    }

}