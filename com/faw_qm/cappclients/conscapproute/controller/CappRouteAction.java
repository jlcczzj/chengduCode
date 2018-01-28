/**
 * 生成程序 CappRouteAction.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.controller;

import javax.swing.JFrame;
import com.faw_qm.framework.remote.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.framework.service.*;
import com.faw_qm.identity.*;

/**
 * <p> Title:工具类 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public class CappRouteAction extends QMThread
{
    private JFrame parentFrame;

    public static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 用于代码测试 */
    public static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * 构造函数
     * @roseuid 403189BA02EB
     */
    public CappRouteAction()
    {

    }

    /**
     * 本方法用于客户端远程调用服务端方法
     * @param serviceName 要调用的服务类名称
     * @param methodName 要调用的服务方法名称
     * @param paraClass 要调用的服务方法的参数的类型
     * @param paraObject 要调用的服务方法的参数值
     * @throws QMRemoteException
     * @roseuid 4030B8BB007B
     */
        public static Object useServiceMethod(String serviceName, String methodName, Class[] paraClass, Object[] paraObject)
        throws QMRemoteException{
            RequestServer server = RequestServerFactory.getRequestServer();
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName(serviceName);
            info1.setMethodName(methodName);
            Class[] paraClass1 = paraClass;
            info1.setParaClasses(paraClass1);
            Object[] objs1 = paraObject;
            info1.setParaValues(objs1);
            Object obj = null;
           
				obj = server.request(info1);
			
            return obj;
        }

    /**
     * 设置父窗口
     * @param parent 父窗口
     * @roseuid 4031603F0147
     */
    public void setParentJFrame(JFrame parent)
    {
        parentFrame = parent;
    }

    /**
     * 获得父窗口
     * @return javax.swing.JFrame
     * @roseuid 403160650246
     */

    public JFrame getParentJFrame()
    {
        JFrame frame = null;
        if(parentFrame != null)
            frame = parentFrame;
        return frame;
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

}