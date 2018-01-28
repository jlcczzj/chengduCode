/**
 * 生成程序 RParentJPanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import javax.swing.JPanel;
import javax.swing.JFrame;
import com.faw_qm.framework.remote.*;

import java.awt.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.identity.*;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.version.model.IteratedIfc;

/**
 * Title:界面工具类 Description: Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @version 1.0
 */
public class RParentJPanel extends JPanel
{
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * @roseuid 4031A738005C
     */
    public RParentJPanel()
    {}

    /**
     * 本方法用于客户端远程调用服务端方法
     * @param serviceName 要调用的服务类名称
     * @param methodName 要调用的服务方法名称
     * @param paraClass 要调用的服务方法的参数的类型
     * @param paraObject 要调用的服务方法的参数值
     * @return 从服务方法获得的返回值
     * @throws QMRemoteException
     * @roseuid 402A11460095
     */
    //    public static Object useServiceMethod(String serviceName, String methodName, Class[] paraClass, Object[] paraObject) 
    //    {
    //        RequestServer server = RequestServerFactory.getRequestServer();
    //        ServiceRequestInfo info1 = new ServiceRequestInfo();
    //        info1.setServiceName(serviceName);
    //        info1.setMethodName(methodName);
    //        Class[] paraClass1 = paraClass;
    //        info1.setParaClasses(paraClass1);
    //        Object[] objs1 = paraObject;
    //        info1.setParaValues(objs1);
    //        Object obj = null;
    //        obj = server.request(info1);
    //        return obj;
    //    }

    /**
     * 获得父窗口
     * @return javax.swing.JFrame
     * @roseuid 402A11F40212
     */
    public JFrame getParentJFrame()
    {
        Component parent = getParent();
        while(!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        return (JFrame)parent;
    }

    /**
     * 根据业务对象的 BsoID获得其值对象
     * @param bsoID 业务对象的 BsoID
     * @return 业务对象的值对象
     * @throws QMRemoteException
     */
    public static Object refreshInfo(String bsoID)
    {
        Class[] cla = {String.class};
        Object[] obj = {bsoID};
        //return useServiceMethod("PersistService", "refreshInfo", cla, obj);
        try {
			return (Object)RequestHelper.request("PersistService", "refreshInfo", cla, obj);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		return null;
    }

    /**
     * 根据工艺路线表刷新值对象，找到最新版本
     * @param info 工艺路线表
     * @return 业务对象的值对象
     * @throws QMRemoteException
     */
    public static TechnicsRouteListIfc refreshListForNew(TechnicsRouteListIfc info)
    {
        Class[] cla = {IteratedIfc.class};
        Object[] obj = {info};
        try {
			return (TechnicsRouteListIfc)RequestHelper.request("VersionControlService", "getLatestIteration", cla, obj);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		
		return null;
    }

    /**
     * 获得指定业务对象的对象类型和标识
     * @param obj 业务对象
     * @return 对象类型和标识
     */
    public static String getIdentity(Object obj)
    {
        String s = "";
        if(obj instanceof BaseValueInfo)
        {
            //通过标识工厂获得与给定值对象对应的显示标识对象。
            DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(obj);
            //获得对象类型 + 标识
            s = displayidentity.getLocalizedMessage(null).trim();
        }
        return s;
    }

    /**
     * 使指定的业务对象丢弃持久化信息
     * @param bvi 业务对象
     */
    public static void setObjectNotPersist(BaseValueIfc bvi)
    {
        if(bvi != null)
        {
            bvi.setBsoID(null);
            bvi.setCreateTime(null);
            bvi.setModifyTime(null);
        }
    }

}
