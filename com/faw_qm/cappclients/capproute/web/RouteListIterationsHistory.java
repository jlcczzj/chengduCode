/**
 *生成程序 RouteListIterationsHistory.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.web;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.IteratedIfc;

/**
 * <p>
 * Title:查看路线表的版序历史
 * </p>
 * <p>
 * Description:本类提供的方法为廋客户界面使用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @version 1.0
 */

public class RouteListIterationsHistory {

    public RouteListIterationsHistory() {
    }

    /**
     * 获得瘦客户端头部的必要显示信息(路线表的编号和名称)
     *
     * @param Bso
     *            路线表的BsoID return 路线表的编号和名称
     */
    public static String getVersionHead(String Bso) {

        String id = Bso.trim();
        String technicsNum;
        String technicsName;
        BaseValueIfc obj;
        String rStr;
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            obj = pService.refreshInfo(id);
        } catch (Exception sle) {
            sle.printStackTrace();
            obj = null;
        }
        if (obj != null) {
            if (obj instanceof TechnicsRouteListIfc) {
                technicsNum = ((TechnicsRouteListIfc) obj).getRouteListNumber();
                technicsName = ((TechnicsRouteListIfc) obj).getRouteListName();
//              CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线 
                rStr = "艺准编号：" + technicsNum + " ---------" + " 项目名称："
                        + technicsName;
                return rStr;
            } else {
                technicsNum = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListNumber();
                technicsName = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListName();
                String[] vv = new String[2];
                rStr = "艺准编号：" + technicsNum + " ---------" + " 项目名称："
                        + technicsName;
                return rStr;
            }
            //String iconUrl=pb.getIconName("StandardIcon");
            //vv.addElement(id);
        } else {
            rStr = "艺准编号： ---------" + " 项目名称： ";
            return rStr;
//          CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线 
        }
    }

    /**
     * 获得该对象的版序历史对象的集合(所有的小版本的集合)
     *
     * @param BsoID
     *            工艺卡对象的BsoID
     * @return Collection 版本历史对象的集合
     */
    public static Collection getVersionsCollection(String BsoID) {
        TechnicsRouteListMasterIfc myMasterInfo;
        Collection myCollection = new Vector();
        try {
            PersistService service = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            myMasterInfo = (TechnicsRouteListMasterIfc) service
                    .refreshInfo(BsoID);

            VersionControlService service1 = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");
            myCollection = service1.allIterationsOf((MasteredIfc) myMasterInfo);
        } catch (Exception sle) {
            sle.printStackTrace();
        }
        return myCollection;
    }


    /**
     * 获得该对象的版序历史对象的集合，得到符合 瘦客户端显示必须的内容的集合
     *
     * @param BsoID
     *            工艺卡对象的BsoID
     * @return Collection String数组的对象集合，数组内容为: 版本对象的BsoID,版本,对象状态,项目名称,创建者,创建时间
     *
     */
    public static Collection getMyCollection(String BsoID) {
        Collection myCo = getVersionsCollection(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        TechnicsRouteListIfc myInfo;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        Collection myCollection = new Vector();
        String versionBsoID;

        for (int i = 0; i < myCo.size(); i++) {
            //得到小版本
            myInfo = (TechnicsRouteListIfc) (aa[i]);
            //得到需要的值
            versionID = myInfo.getVersionValue();
            if (myInfo.getLifeCycleState() != null)
                objectState = (myInfo.getLifeCycleState()).getDisplay();
            else
                objectState = "";

            //项目组
            ProjectIfc project = null;
            try {
                PersistService pService = (PersistService) EJBServiceHelper
                        .getService("PersistService");
                if (((LifeCycleManagedIfc) myInfo).getProjectId() != null)
                    project = (ProjectIfc) pService
                            .refreshInfo(((LifeCycleManagedIfc) myInfo)
                                    .getProjectId());

                objectCreator = RouteWebHelper.getUserNameByID(myInfo
                        .getIterationCreator());
            } catch (Exception e) {
                e.printStackTrace();
                objectCreator = "";
            }

            if (project != null)
                projectName = CappServiceHelper.getIdentity(project);
            else
                projectName = "";
            createTime = (myInfo.getCreateTime()).toString();
            versionBsoID = myInfo.getBsoID();
            //构造数组
            String[] myVersionArray1 = { versionBsoID, versionID, objectState,
                    projectName, objectCreator, createTime };
            //加到集合中
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }


    /**
      * add by guoxl on 20080304
      *获得该对象的版序历史对象时只显示当前版本及它的前驱，得到符合 瘦客户端显示必须的内容的集合
      * @param BsoID
      *            TechnicsRoutList的BsoID
      * @return Collection String数组的对象集合，数组内容为: 版本对象的BsoID,版本,对象状态,项目名称,创建者,创建时间
      *
      */

   public Collection getAllPredecessor(String BsoID) {
           Collection myCo = getVersionsCollection1(BsoID);
           Object[] aa = new Object[myCo.size()];
           myCo.toArray(aa);

           TechnicsRouteListIfc myInfo;
           String versionID;
           String objectState;
           String projectName;
           String objectCreator;
           String createTime;
           String modifyTime;//liunan 2011-10-09 更新时间
           Collection myCollection = new Vector();
           String versionBsoID;

           for (int i = 0; i < myCo.size(); i++) {
               //得到小版本
               myInfo = (TechnicsRouteListIfc) (aa[i]);
               //得到需要的值
               versionID = myInfo.getVersionValue();
               if (myInfo.getLifeCycleState() != null){

                 objectState = (myInfo.getLifeCycleState()).getDisplay();

               }
               else
                   objectState = "";

               //项目组
               ProjectIfc project = null;
               try {
                   PersistService pService = (PersistService) EJBServiceHelper
                           .getService("PersistService");
                   if (((LifeCycleManagedIfc) myInfo).getProjectId() != null)
                       project = (ProjectIfc) pService
                               .refreshInfo(((LifeCycleManagedIfc) myInfo)
                                       .getProjectId());

                   objectCreator = RouteWebHelper.getUserNameByID(myInfo
                           .getIterationCreator());
               } catch (Exception e) {
                   e.printStackTrace();
                   objectCreator = "";
               }

               if (project != null){
                 projectName = CappServiceHelper.getIdentity(project);
               }
               else
                   projectName = "";
               createTime = (myInfo.getCreateTime()).toString();
               modifyTime = (myInfo.getModifyTime()).toString();//liunan 2011-10-09 更新时间
               versionBsoID = myInfo.getBsoID();
               //构造数组
               String[] myVersionArray1 = { versionBsoID, versionID, objectState,
                       //projectName, objectCreator, createTime };
                       projectName, objectCreator, createTime, modifyTime };//liunan 2011-10-09 更新时间
               //加到集合中
               myCollection.add(myVersionArray1);
           }

           return myCollection;
       }
   /**
    * 获得该对象的版序历史对象的集合(当前版本及它的前驱的小版本的集合)
    *
    * @param BsoID
    *            TechnicsRoutList的BsoID
    * @return Collection 版本历史对象的集合
    */

       public static Collection getVersionsCollection1(String BsoID) {
               TechnicsRouteListIfc myInfo;
               Collection myCollection = new Vector();
               try {
                   PersistService service = (PersistService) EJBServiceHelper
                           .getService("PersistService");
                   myInfo = (TechnicsRouteListIfc) service
                           .refreshInfo(BsoID);

                   VersionControlService service1 = (VersionControlService) EJBServiceHelper
                           .getService("VersionControlService");

                   myCollection = service1.getAllPredecessor((IteratedIfc)myInfo);
               } catch (Exception sle) {
                   sle.printStackTrace();
               }

               return myCollection;
           }
      // add by guoxl end

}

