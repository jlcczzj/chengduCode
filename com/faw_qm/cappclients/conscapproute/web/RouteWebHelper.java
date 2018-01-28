/** 
 * ���ɳ��� RouteWebHelper.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title:ΪWebҳ�ṩ���� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class RouteWebHelper
{
    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    public RouteWebHelper()
    {}

    /**
     * �����û�BsoID����û���
     * @param userID �û�BsoID
     * @return �û���
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
     * ��ȡҵ�����ı�׼ͼ�����ơ�
     * @param info ҵ�����
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
     * ��ȡ������������״̬
     * @return ������������״̬(State����)
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
     * ��ȡ������Ŀ�顣
     * @return Vector ������Ŀ��
     * @exception com.faw_qm.framework.exceptions.QMException
     */
    public static Vector findAllProjectTeamInfo() throws QMException
    {
        Vector vector = null;
        try
        {
            //��ȡ��Ŀ����
            ProjectService projService = (ProjectService)EJBServiceHelper.getService("ProjectService");
            //��ȡ���п��õ���Ŀ
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
     * ������е�·�߼���
     * @return ·�߼���
     */
    public static Vector findAllRouteListLevel()
    {
        Vector v = new Vector();
        v.addElement(RouteListLevelType.FIRSTROUTE.getDisplay());
        v.addElement(RouteListLevelType.SENCONDROUTE.getDisplay());
        return v;
    }

    /**
     * ����㲿������Ϣ�����bsoID
     * @param bsoID �㲿��ֵ�����bsoID
     * @return �㲿������Ϣ����QMPartMaster��bsoID
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
     * ��ָ����ҵ������bsoID�����������к󷵻�
     * @param originalIDs ҵ������bsoID������
     * @return ��������bsoID����
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
                    //����bsoid�������򣬰�bsoidȷ�����򡣣����ų�ͬһ�汾��ԭ���͸�����˳�򡣣�
                    if(bsoID1.compareTo(bsoID2) > 0)
                    {
                        //�����滻
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
     * ���ָ��ҵ�����Ķ������ͺͱ�ʶ
     * @param bsoID ҵ�����bsoID
     * @return �������ͺͱ�ʶ
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
                //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(obj);
                //��ö������� + ��ʶ
                s = displayidentity.getLocalizedMessage(null).trim();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * ��·�߱��BsoID�������������\��Ŀ\״̬\�Ƿ�ȴ�����
     * @param id ·�߱��BsoID
     * @return ��������\��Ŀ\״̬\�Ƿ�ȴ�����
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
            //�������������
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
                //�����Ŀ����
                if(project != null)
                    projectName = project.getName();
            }
            //��ö����״̬
            if(objInfo.getLifeCycleState() != null)
                objectState = (objInfo.getLifeCycleState()).getDisplay();
            //�Ƿ�ȴ�����
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