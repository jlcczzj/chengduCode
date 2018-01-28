/**
 * 生成程序RouteClientUtil.java    1.0    2012-1-19
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 判断用户所属公司 liuyang 2013-12-23
 * SS2 从代码管理器中获得采购标识 liuyang 2013-12-25
 * SS3 采购标识默认值为空 liuyang 2014-2-26
 * SS4 判断长特用户组 guoxiaoliang 2014-9-1
 * SS5 补全所有厂家的判断。 liunan 2015-7-17
 * SS6 增加成都。 liunan 2016-8-12
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
 * <p>Title:为客户端提供服务。 </p> <p>Description:本类提供了一系列静态方法，以方便客户端调用服务 </p> <p>Copyright: Copyright (c) 2012</p> <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public class RouteClientUtil
{

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * 构造函数
     */
    public RouteClientUtil()
    {}
   
    /**
     * 根据持久化信息刷新业务对象 返回更新完后的值对象。
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMRemoteException
     */
    public static BaseValueInfo refresh(BaseValueIfc baseValueIfc)
    {
        Class[] paraClass = {BaseValueIfc.class};
        //参数值的集合
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
     * 根据持久化信息刷新业务对象 返回更新完后的值对象。
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMRemoteException
     */
    public static BaseValueInfo refresh(String bsoID)
    {
        Class[] paraClass = {String.class};
        //参数值的集合
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
     * 删除业务对象。
     * @param info 要删除的业务对象
     * @exception QMRemoteException
     */
    public static void deleteBaseValue(BaseValueIfc info)
    {
        Class[] paraClass = {BaseValueIfc.class};
        //参数值的集合
        Object[] objs = {info};
        try {
			RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}//CR2
    }

    /**
     * 根据单位简称获得单位bsoID
     * @param departmentName 简称
     * @return 单位bsoID
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
     * 获得当前用户
     * @return String 当前用户
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
     * 获得当前用户的快捷路线信息
     */
    public static HashMap getShortCutRoute()
    {
        UserIfc currentUser = getCurrentUser();
        //通过用户名查找快捷路线
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
     * 从代码管理器中获取更改标记
     * @return
     */
    public static Vector getMarks()
    {
        Vector marks = new Vector();
        Class[] params = {String.class, String.class};
        Object[] values = {"更改标记", "工艺路线"};
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
     * 判断一个对象是否可以被修订 对于未被检入的对象（包含两种情况：在个人资料夹，或已被检出）不可以进行修订操作； 不是最新版本的对象不能执行修订操作。
     * @param versioned:需要判断的对象
     * @return 如果对象是工作副本，则不允许修订（False）；否则允许修订。
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
     * 获取解析后的路线串信息
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
     * 比较路线信息是否变化
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
     * 判断用户所属公司
     * @return 单位名称
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
		   if (groupName.startsWith("轴齿中心")) {
		    	return "zczx";
		    	
		    }
		 //CCBegin guoguo
		   else if(groupName.startsWith("长特")){
		    	
		    	return "ct";
		    }
		   //CCEnd guoguo
		    //CCBegin SS5
			  else if (groupName.startsWith("发动机"))
			  {
			  	return "fdj";
			  }
			  else if(groupName.startsWith("变速箱"))
			  {
			  	return "bsx";
			  }
			  //CCBegin SS6
			  else if(groupName.startsWith("成都"))
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
     * 从代码管理器中获取采购标识
     * @return
     */
    public static Vector getStockID()
    {
        Vector marks = new Vector();
        //CCBegin SS3
        marks.add("");
        //CCEnd SS3
        Class[] params = {String.class, String.class};
        Object[] values = {"采购标识", "工艺路线"};
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
