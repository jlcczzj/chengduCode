/**
 * 生成程序 RouteListVersionHistory.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.web;

import java.util.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.persist.ejb.service.*;
import com.faw_qm.util.*;
import com.faw_qm.project.model.*;
import com.faw_qm.lifecycle.model.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.version.ejb.service.*;
import com.faw_qm.version.model.*;
import com.faw_qm.capp.util.*;


/**
 * （问题一）zz 周茁修改 查看历史版本时不应该看见检出的路线表
 * <p>Title:查看路线表的版本历史 </p>
 * <p>Description: 本类提供的方法为廋客户界面使用</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */
public class RouteListVersionHistory {

    public RouteListVersionHistory() {
    }

    /**
     * 得到瘦客户端头部的必要显示信息（路线表的编号和名称）
     *
     * @param Bso
     *            路线表的BsoID
     * @return 路线表的编号和名称
     */
    public static String getVersionHead(String BsoID) {
        String id = BsoID.trim();
        String technicsNumber;
        String technicsName;
        BaseValueIfc obj;
        String rStr;
        //调用服务，根据工艺卡的BsoID获得其值对象
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            obj = pService.refreshInfo(id);
        } catch (Exception sle) {
            System.out.println(sle.toString());
            obj = null;
        }

        if (obj != null) {
            if (obj instanceof TechnicsRouteListIfc) {
                technicsNumber = ((TechnicsRouteListIfc) obj)
                        .getRouteListNumber();
                technicsName = ((TechnicsRouteListIfc) obj).getRouteListName();
//              CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线           
                rStr = "艺准编号：" + technicsNumber + " ---------" + " 项目名称："
                        + technicsName;
              
                return rStr;
            } else {
                technicsNumber = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListNumber();
                technicsName = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListName();
                rStr = "艺准编号：" + technicsNumber + " ---------" + " 项目名称："
                        + technicsName;
                return rStr;
            }
        } else {
            rStr = "艺准编号： ---------" + " 项目名称： ";
            return rStr;
//          CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线 
        }

    }

    /**
     * 获得指定路线表的版本历史对象的集合（所有的小版本）
     *
     * @param BsoID
     *            路线表的BsoID
     * @return Collection 版本历史对象的集合
     */
    public static Collection getVersionsCollection(String BsoID) {

        String id = BsoID;
        BaseValueIfc obj;
        Collection myVersionCollection = new Vector();

        //调用服务，根据工艺卡的BsoID获得其值对象
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            obj = pService.refreshInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            obj = null;
        }

        //获得所有小版本
        if (obj != null) {
            try {
                VersionControlService service1 = (VersionControlService) EJBServiceHelper
                        .getService("VersionControlService");
                if (obj instanceof TechnicsRouteListIfc)

                    myVersionCollection = (Collection) (service1
                            .allVersionsOf((TechnicsRouteListIfc) obj));
                else
                    myVersionCollection = (Collection) (service1
                            .allVersionsOf((MasteredIfc) obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //（问题一）zz 周茁修改 查看历史版本时不应该看见检出的路线表 start
          if(myVersionCollection!=null){
         Vector myVersions  = new Vector(myVersionCollection);
          for(int i =0 ;i<myVersions.size();i++)
          {TechnicsRouteListIfc techrouteListifc = (TechnicsRouteListIfc)myVersions.elementAt(i);

            if(techrouteListifc.getWorkableState().equals("c/o")){

           boolean ok =  myVersions.remove(techrouteListifc);
           //System.out.print("过滤了 溅出的版本 " + ok);
            }
          }
           return myVersions;
        }

            else return myVersionCollection;
            //（问题一）zz 周茁修改 查看历史版本时不应该看见检出的路线表 end
        } else {
            return null;
        }

    }

    /**
     * 得到符合瘦客户端显示必须的内容的集合(版本,对象状态,项目名称,创建者,创建时间)
     *
     * @param BsoID
     *            路线表的BsoID
     * @return Collection String数组的对象集合，数组内容为 版本对象的BsoID,版本,对象状态,项目名称,创建者,创建时间
     *
     */
    public static Collection getMyCollection(String BsoID) {
        //System.out.println("getMyCollection begin...BsoID="+BsoID);
        //获得指定工艺卡的版本历史对象的集合（所有的小版本）
        Collection versionCollection = getVersionsCollection(BsoID);
        //转化为数组
        Object[] aa = new Object[versionCollection.size()];
        versionCollection.toArray(aa);

        TechnicsRouteListIfc technics;
        String versionID; //版本
        String objectState; //对象状态
        String projectName; //项目名称
        String objectCreator; //创建者
        String createTime; //创建时间
        //用于返回
        Collection myCollection = new Vector();
        //每个版本的工艺卡的ID
        String versionBsoID;

        for (int i = 0; i < versionCollection.size(); i++) {
            //得到工艺卡的某一小版本
            technics = (TechnicsRouteListIfc) (aa[i]);
            //获得版本号
            versionID = technics.getVersionID();

            //获得对象的状态
            if (technics.getLifeCycleState() != null)
                objectState = (technics.getLifeCycleState()).getDisplay();
            else
                objectState = "";
            //调用服务，根据工艺卡的BsoID获得其值对象
            ProjectIfc project = null;
            try {
                PersistService pService = (PersistService) EJBServiceHelper
                        .getService("PersistService");
                if (((LifeCycleManagedIfc) technics).getProjectId() != null)
                    project = (ProjectIfc) pService
                            .refreshInfo(((LifeCycleManagedIfc) technics)
                                    .getProjectId());

            } catch (Exception e) {
                e.printStackTrace();
            }
            //获得项目组名
            if (project != null)
                projectName = CappServiceHelper.getIdentity(project);
            else
                projectName = "";

            //获得创建者
            if (technics.getIterationCreator() != null) {
                try {
                    objectCreator = RouteWebHelper.getUserNameByID((technics
                            .getIterationCreator()));
                } catch (Exception e) {
                    objectCreator = "";
                }
            } else {
                objectCreator = "";
            }

            //获得创建时间
            createTime = (technics.getCreateTime()).toString();
            //获得BsoID
            versionBsoID = technics.getBsoID();
            //构造数组
            String[] myVersionArray1 = { versionBsoID, versionID, objectState,
                    projectName, objectCreator, createTime };
            //加到集合中
            myCollection.add(myVersionArray1);
            // System.out.println("projectName="+projectName);
        }

        return myCollection;
    }

}
