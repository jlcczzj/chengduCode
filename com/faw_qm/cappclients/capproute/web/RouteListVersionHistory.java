/**
 * ���ɳ��� RouteListVersionHistory.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * ������һ��zz �����޸� �鿴��ʷ�汾ʱ��Ӧ�ÿ��������·�߱�
 * <p>Title:�鿴·�߱�İ汾��ʷ </p>
 * <p>Description: �����ṩ�ķ���Ϊ�C�ͻ�����ʹ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
public class RouteListVersionHistory {

    public RouteListVersionHistory() {
    }

    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ��·�߱�ı�ź����ƣ�
     *
     * @param Bso
     *            ·�߱��BsoID
     * @return ·�߱�ı�ź�����
     */
    public static String getVersionHead(String BsoID) {
        String id = BsoID.trim();
        String technicsNumber;
        String technicsName;
        BaseValueIfc obj;
        String rStr;
        //���÷��񣬸��ݹ��տ���BsoID�����ֵ����
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
//              CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��           
                rStr = "��׼��ţ�" + technicsNumber + " ---------" + " ��Ŀ���ƣ�"
                        + technicsName;
              
                return rStr;
            } else {
                technicsNumber = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListNumber();
                technicsName = ((TechnicsRouteListMasterIfc) obj)
                        .getRouteListName();
                rStr = "��׼��ţ�" + technicsNumber + " ---------" + " ��Ŀ���ƣ�"
                        + technicsName;
                return rStr;
            }
        } else {
            rStr = "��׼��ţ� ---------" + " ��Ŀ���ƣ� ";
            return rStr;
//          CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·�� 
        }

    }

    /**
     * ���ָ��·�߱�İ汾��ʷ����ļ��ϣ����е�С�汾��
     *
     * @param BsoID
     *            ·�߱��BsoID
     * @return Collection �汾��ʷ����ļ���
     */
    public static Collection getVersionsCollection(String BsoID) {

        String id = BsoID;
        BaseValueIfc obj;
        Collection myVersionCollection = new Vector();

        //���÷��񣬸��ݹ��տ���BsoID�����ֵ����
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            obj = pService.refreshInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            obj = null;
        }

        //�������С�汾
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
            //������һ��zz �����޸� �鿴��ʷ�汾ʱ��Ӧ�ÿ��������·�߱� start
          if(myVersionCollection!=null){
         Vector myVersions  = new Vector(myVersionCollection);
          for(int i =0 ;i<myVersions.size();i++)
          {TechnicsRouteListIfc techrouteListifc = (TechnicsRouteListIfc)myVersions.elementAt(i);

            if(techrouteListifc.getWorkableState().equals("c/o")){

           boolean ok =  myVersions.remove(techrouteListifc);
           //System.out.print("������ �����İ汾 " + ok);
            }
          }
           return myVersions;
        }

            else return myVersionCollection;
            //������һ��zz �����޸� �鿴��ʷ�汾ʱ��Ӧ�ÿ��������·�߱� end
        } else {
            return null;
        }

    }

    /**
     * �õ������ݿͻ�����ʾ��������ݵļ���(�汾,����״̬,��Ŀ����,������,����ʱ��)
     *
     * @param BsoID
     *            ·�߱��BsoID
     * @return Collection String����Ķ��󼯺ϣ���������Ϊ �汾�����BsoID,�汾,����״̬,��Ŀ����,������,����ʱ��
     *
     */
    public static Collection getMyCollection(String BsoID) {
        //System.out.println("getMyCollection begin...BsoID="+BsoID);
        //���ָ�����տ��İ汾��ʷ����ļ��ϣ����е�С�汾��
        Collection versionCollection = getVersionsCollection(BsoID);
        //ת��Ϊ����
        Object[] aa = new Object[versionCollection.size()];
        versionCollection.toArray(aa);

        TechnicsRouteListIfc technics;
        String versionID; //�汾
        String objectState; //����״̬
        String projectName; //��Ŀ����
        String objectCreator; //������
        String createTime; //����ʱ��
        //���ڷ���
        Collection myCollection = new Vector();
        //ÿ���汾�Ĺ��տ���ID
        String versionBsoID;

        for (int i = 0; i < versionCollection.size(); i++) {
            //�õ����տ���ĳһС�汾
            technics = (TechnicsRouteListIfc) (aa[i]);
            //��ð汾��
            versionID = technics.getVersionID();

            //��ö����״̬
            if (technics.getLifeCycleState() != null)
                objectState = (technics.getLifeCycleState()).getDisplay();
            else
                objectState = "";
            //���÷��񣬸��ݹ��տ���BsoID�����ֵ����
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
            //�����Ŀ����
            if (project != null)
                projectName = CappServiceHelper.getIdentity(project);
            else
                projectName = "";

            //��ô�����
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

            //��ô���ʱ��
            createTime = (technics.getCreateTime()).toString();
            //���BsoID
            versionBsoID = technics.getBsoID();
            //��������
            String[] myVersionArray1 = { versionBsoID, versionID, objectState,
                    projectName, objectCreator, createTime };
            //�ӵ�������
            myCollection.add(myVersionArray1);
            // System.out.println("projectName="+projectName);
        }

        return myCollection;
    }

}
