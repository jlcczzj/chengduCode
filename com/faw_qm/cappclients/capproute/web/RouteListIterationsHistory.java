/**
 *���ɳ��� RouteListIterationsHistory.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * Title:�鿴·�߱�İ�����ʷ
 * </p>
 * <p>
 * Description:�����ṩ�ķ���Ϊ�C�ͻ�����ʹ��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */

public class RouteListIterationsHistory {

    public RouteListIterationsHistory() {
    }

    /**
     * ����ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ(·�߱�ı�ź�����)
     *
     * @param Bso
     *            ·�߱��BsoID return ·�߱�ı�ź�����
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
//              CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·�� 
                rStr = "��׼��ţ�" + technicsNum + " ---------" + " ��Ŀ���ƣ�"
                        + technicsName;
                return rStr;
            } else {
                technicsNum = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListNumber();
                technicsName = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListName();
                String[] vv = new String[2];
                rStr = "��׼��ţ�" + technicsNum + " ---------" + " ��Ŀ���ƣ�"
                        + technicsName;
                return rStr;
            }
            //String iconUrl=pb.getIconName("StandardIcon");
            //vv.addElement(id);
        } else {
            rStr = "��׼��ţ� ---------" + " ��Ŀ���ƣ� ";
            return rStr;
//          CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·�� 
        }
    }

    /**
     * ��øö���İ�����ʷ����ļ���(���е�С�汾�ļ���)
     *
     * @param BsoID
     *            ���տ������BsoID
     * @return Collection �汾��ʷ����ļ���
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
     * ��øö���İ�����ʷ����ļ��ϣ��õ����� �ݿͻ�����ʾ��������ݵļ���
     *
     * @param BsoID
     *            ���տ������BsoID
     * @return Collection String����Ķ��󼯺ϣ���������Ϊ: �汾�����BsoID,�汾,����״̬,��Ŀ����,������,����ʱ��
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
            //�õ�С�汾
            myInfo = (TechnicsRouteListIfc) (aa[i]);
            //�õ���Ҫ��ֵ
            versionID = myInfo.getVersionValue();
            if (myInfo.getLifeCycleState() != null)
                objectState = (myInfo.getLifeCycleState()).getDisplay();
            else
                objectState = "";

            //��Ŀ��
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
            //��������
            String[] myVersionArray1 = { versionBsoID, versionID, objectState,
                    projectName, objectCreator, createTime };
            //�ӵ�������
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }


    /**
      * add by guoxl on 20080304
      *��øö���İ�����ʷ����ʱֻ��ʾ��ǰ�汾������ǰ�����õ����� �ݿͻ�����ʾ��������ݵļ���
      * @param BsoID
      *            TechnicsRoutList��BsoID
      * @return Collection String����Ķ��󼯺ϣ���������Ϊ: �汾�����BsoID,�汾,����״̬,��Ŀ����,������,����ʱ��
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
           String modifyTime;//liunan 2011-10-09 ����ʱ��
           Collection myCollection = new Vector();
           String versionBsoID;

           for (int i = 0; i < myCo.size(); i++) {
               //�õ�С�汾
               myInfo = (TechnicsRouteListIfc) (aa[i]);
               //�õ���Ҫ��ֵ
               versionID = myInfo.getVersionValue();
               if (myInfo.getLifeCycleState() != null){

                 objectState = (myInfo.getLifeCycleState()).getDisplay();

               }
               else
                   objectState = "";

               //��Ŀ��
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
               modifyTime = (myInfo.getModifyTime()).toString();//liunan 2011-10-09 ����ʱ��
               versionBsoID = myInfo.getBsoID();
               //��������
               String[] myVersionArray1 = { versionBsoID, versionID, objectState,
                       //projectName, objectCreator, createTime };
                       projectName, objectCreator, createTime, modifyTime };//liunan 2011-10-09 ����ʱ��
               //�ӵ�������
               myCollection.add(myVersionArray1);
           }

           return myCollection;
       }
   /**
    * ��øö���İ�����ʷ����ļ���(��ǰ�汾������ǰ����С�汾�ļ���)
    *
    * @param BsoID
    *            TechnicsRoutList��BsoID
    * @return Collection �汾��ʷ����ļ���
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

