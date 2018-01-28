/**
 * ���ɳ���RouteClientUtil.java    1.0    2012-1-19
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �ж��û�������˾ liuyang 2013-12-23
 * SS2 �Ӵ���������л�òɹ���ʶ liuyang 2013-12-25
 * SS3 �ɹ���ʶĬ��ֵΪ�� liuyang 2014-2-26
 * SS4 �жϳ����û��� guoxiaoliang 2014-9-1
 * SS5 ��ȫ���г��ҵ��жϡ� liunan 2015-7-17
 * SS6 ���ӳɶ��� liunan 2016-8-12
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.conscapproute.view.CappRouteListManageJFrame;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title:Ϊ�ͻ����ṩ���� </p> <p>Description:�����ṩ��һϵ�о�̬�������Է���ͻ��˵��÷��� </p> <p>Copyright: Copyright (c) 2012</p> <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public class RouteClientUtil
{

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * ���캯��
     */
    public RouteClientUtil()
    {}
   
    /**
     * ���ݳ־û���Ϣˢ��ҵ����� ���ظ�������ֵ����
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMRemoteException
     */
    public static BaseValueInfo refresh(BaseValueIfc baseValueIfc)
    {
        Class[] paraClass = {BaseValueIfc.class};
        //����ֵ�ļ���
        Object[] objs = {baseValueIfc};
        BaseValueInfo obj=null;
		try {
			obj = (BaseValueInfo)RequestHelper.request("PersistService",//CR2
			        "refreshInfo", paraClass, objs);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
        return obj;
    }

    /**
     * ���ݳ־û���Ϣˢ��ҵ����� ���ظ�������ֵ����
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMRemoteException
     */
    public static BaseValueInfo refresh(String bsoID)
    {
        Class[] paraClass = {String.class};
        //����ֵ�ļ���
        Object[] objs = {bsoID};
        BaseValueInfo obj=null;
		try {
			obj = (BaseValueInfo)RequestHelper.request("PersistService", "refreshInfo", paraClass, objs);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
        return obj;
    }

    /**
     * ɾ��ҵ�����
     * @param info Ҫɾ����ҵ�����
     * @exception QMRemoteException
     */
    public static void deleteBaseValue(BaseValueIfc info)
    {
        Class[] paraClass = {BaseValueIfc.class};
        //����ֵ�ļ���
        Object[] objs = {info};
        try {
			RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}//CR2
    }

    /**
     * ���ݵ�λ��ƻ�õ�λbsoID
     * @param departmentName ���
     * @return ��λbsoID
     */
    public static String getDepartmentID(String departmentName)
    {
        // CodingManageService service =
        // (CodingManageService)EJBServiceHelper.getService("CodingManageService");
        Class[] params = {String.class};
        Object[] values = {departmentName};
        String departId = null;
        try {
			departId = (String)RequestHelper.request("CodingManageService", "getIDbySort", params, values);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
        return departId;
    }

    /**
     * ��õ�ǰ�û�
     * @return String ��ǰ�û�
     */
    public static UserIfc getCurrentUser()
    {

        UserIfc sysUser = null;
        try {
			sysUser = (UserIfc)RequestHelper.request("SessionService", "getCurUserInfo", null, null);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
        return sysUser;
    }

    /**
     * ��õ�ǰ�û��Ŀ��·����Ϣ
     */
    public static HashMap getShortCutRoute()
    {
        UserIfc currentUser = getCurrentUser();
        //ͨ���û������ҿ��·��
        HashMap map = null;
        Class[] paraClass = {String.class};
        Object[] obj = {currentUser.getBsoID()};
        try {
			map = (HashMap)RequestHelper.request("consTechnicsRouteService", "getShortRouteMap", paraClass, obj);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
        return map;
    }

    /**
     * �Ӵ���������л�ȡ���ı��
     * @return
     */
    public static Vector getMarks()
    {
        Vector marks = new Vector();
        Class[] params = {String.class, String.class};
        Object[] values = {"���ı��", "����·��"};
        Collection result = null;
        try
        {
            result = (Collection)RequestHelper.request("CodingManageService", "findDirectClassificationByName", params, values);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(result != null && result.size() > 0)
        {
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
                marks.add((iterator.next()).toString());
            }
        }
        return marks;
    }

    /**
     * �ж�һ�������Ƿ���Ա��޶� ����δ������Ķ��󣨰�������������ڸ������ϼУ����ѱ�����������Խ����޶������� �������°汾�Ķ�����ִ���޶�������
     * @param versioned:��Ҫ�жϵĶ���
     * @return ��������ǹ����������������޶���False�������������޶���
     */
    public static boolean isReviseAllowed(VersionedIfc versioned)
    {
        boolean flag = true;
        if(CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc)versioned))
            flag = true;
        else
            flag = false;
        return flag;
    }

    
    /**
     * ��ȡ�������·�ߴ���Ϣ
     * @param routeBranchStr
     * @return
     */
    public static String getRouteBranchStr(String routeBranchStr,String sign)
    {
        if(routeBranchStr != null && !routeBranchStr.equals(""))
        {
            if(routeBranchStr.startsWith(sign))
            {
                routeBranchStr = routeBranchStr.substring(1);
            }else if(routeBranchStr.lastIndexOf(sign) == routeBranchStr.length() - 1)
            {
                routeBranchStr = routeBranchStr.substring(0, routeBranchStr.length() - 1);
            }else if(routeBranchStr.startsWith(sign) && routeBranchStr.lastIndexOf(sign) == routeBranchStr.length() - 1)
            {
                routeBranchStr = routeBranchStr.substring(1, routeBranchStr.length() - 1);
            }
        }
        else
        {
            return null;
        }
        return routeBranchStr;
    }
    
    
    /**
     * �Ƚ�·����Ϣ�Ƿ�仯
     * 20120120 xucy add
     */
    public static boolean compareRouteStr(String str1, String str2)
    {
    	if(str1 == null && str2 == null)
    	{
    		return true;
    	}
    	else if(str1 == null && str2 != null)
    	{
    		return false;
    	}
    	else if(str1 != null && !"".equals(str1) && str2 == null)
    	{
    		return false;
    	}
    	else if(str1 == null && str2 != null && !"".equals(str2))
        {
            return false;
        }else if("".equals(str1) && str2 == null)
        {
            return true;
        }
    	else 
    	{
    		return str1.equals(str2);
    	}
    }
    
    public static Collection getAllChildColByDepartID(String departmentID)
    {
        //String departID = getDepartmentID();
        Class[] c = {String.class};
        Object[] objs = {departmentID};
        try {
			return (Collection)RequestHelper.request("CodingManageService", "getAllChildCol", c, objs);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		return null;
    }
    //CCBegin SS1
    /**
     * �ж��û�������˾
     * @return ��λ����
     * @throws QMException
     */
	public static String getUserFromCompany() throws QMException 
	{
		Vector groupVec = new Vector();
		SessionService sessionser = (SessionService) EJBServiceHelper.getService("SessionService");
		UserIfc user = sessionser.getCurUserInfo();
		UsersService userservice = (UsersService) EJBServiceHelper.getService("UsersService");
		Enumeration groups = userservice.userMembers((UserInfo) user, true);
		for (; groups.hasMoreElements();) 
		{			
			GroupInfo group = (GroupInfo) groups.nextElement();
			String groupName = group.getUsersName();
		   if (groupName.startsWith("�������")) {
		    	return "zczx";
		    	
		    }
		 //CCBegin guoguo
		   else if(groupName.startsWith("����")){
		    	
		    	return "ct";
		    }
		   //CCEnd guoguo
		    //CCBegin SS5
			  else if (groupName.startsWith("������"))
			  {
			  	return "fdj";
			  }
			  else if(groupName.startsWith("������"))
			  {
			  	return "bsx";
			  }
			  //CCBegin SS6
			  else if(groupName.startsWith("�ɶ�"))
			  {
			  	return "cd";
			  }
			  //CCEnd SS6
			  else if(groupName.equals("Administrators") || groupName.equals("WorkflowAdministrators") || groupName.equals("LifeCycleAdministrators"))
			  {
		    	return "Admin";
		    }
		    //CCEnd SS5
		}
		return "jf";
	}
	//CCEnd SS1
	//CCBegin SS2
    /**
     * �Ӵ���������л�ȡ�ɹ���ʶ
     * @return
     */
    public static Vector getStockID()
    {
        Vector marks = new Vector();
        //CCBegin SS3
        marks.add("");
        //CCEnd SS3
        Class[] params = {String.class, String.class};
        Object[] values = {"�ɹ���ʶ", "����·��"};
        Collection result = null;
        try
        {
            result = (Collection)RequestHelper.request("CodingManageService", "findDirectClassificationByName", params, values);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(result != null && result.size() > 0)
        {
            Iterator iterator = result.iterator();
            while(iterator.hasNext())
            {
                marks.add((iterator.next()).toString());
            }
        }
        return marks;
    }
    //CCEnd SS2
}
