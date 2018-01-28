/** 生成程序 CappClientUtil.java    1.0    2003/09/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/10/26 徐春英 原因：操作EJB的地方一律改为操作值对象
 * CR2 2009/11/02 刘玉柱 原因：统一客户端或工具类调用服务的方式
 */
package com.faw_qm.cappclients.capp.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.jview.chrset.SpeCharaterInfo;
import com.faw_qm.resource.support.model.SpecialCharInfo;


/**
 * <p>Title:为客户端提供服务。 </p>
 * <p>Description:本类提供了一系列静态方法，以方便客户端调用服务 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class CappClientUtil
{
    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**
     * 缓存的所有特殊符号
     */
    private static HashMap allSpechar;


    /**
     * 构造函数
     */
    public CappClientUtil()
    {
    }


    /**
     * 本方法用于客户端远程调用服务端方法
     * @param serviceName 要调用的服务类名称
     * @param methodName 要调用的服务方法名称
     * @param paraClass  要调用的服务方法的参数的类型
     * @param paraObject 要调用的服务方法的参数值
     * @return
     */
    //Begin CR2
//    public static Object useServiceMethod(String serviceName, String methodName,
//                                          Class[] paraClass,
//                                          Object[] paraObject)
//            throws QMRemoteException
//    {
//        if (verbose)
//        {
//            System.out.println(
//                    "cappclients.capp.util.CappClientUtil.useServiceMethod() begin...");
//        }
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
//        if (verbose)
//        {
//            System.out.println(
//                    "cappclients.capp.util.CappClientUtil.useServiceMethod() end...return : " +
//                    obj);
//        }
//        return obj;
//    }//End CR2


    /**
     * 根据持久化信息刷新业务对象
     * 返回更新完后的值对象。
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMException 
     * @throws QMRemoteException
     */
    public static BaseValueInfo refresh(BaseValueIfc baseValueIfc) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(1) begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //参数值的集合
        Object[] objs =
                {baseValueIfc};
        BaseValueInfo obj = (BaseValueInfo) RequestHelper.request("PersistService",//CR2
                "refreshInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(1) end...return : " +
                    obj);
        }
        return obj;
    }


    /**
     * 根据持久化信息刷新业务对象
     * 返回更新完后的值对象。
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMRemoteException 
     */
    public static BaseValueInfo refresh(String bsoID) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(1) begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {String.class};
        //参数值的集合
        Object[] objs =
                {bsoID};
        BaseValueInfo obj = (BaseValueInfo) RequestHelper.request("PersistService",
                "refreshInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(1) end...return : " +
                    obj);
        }
        return obj;
    }


    /**
     * 根据输入的工艺卡值对象参数，对数据库进行更新，返回更新后的工艺卡值对象。
     * @param technics QMFawTechnicsInfo 刷新前的工艺值对象
     * @return QMFawTechnicsInfo 刷新前的工艺值对象
     * @throws QMException 
     */
    public static QMFawTechnicsInfo refresh(QMFawTechnicsInfo technics) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(2) begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //参数值的集合
        Object[] objs =
                {technics};
        QMFawTechnicsInfo obj = (QMFawTechnicsInfo) RequestHelper.request(//CR2
                "PersistService", "refreshInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(2) end...return : " +
                    obj);
        }
        return obj;
    }


    /**
     * 删除业务对象。
     * @param info 要删除的业务对象
     * @throws QMException 
     * @exception QMRemoteException
     */
    public static void deleteBaseValue(BaseValueIfc info) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.deleteBaseValue() begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //参数值的集合
        Object[] objs =
                {info};
        RequestHelper.request("PersistService", "deleteValueInfo", paraClass, objs);//CR2
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.deleteBaseValue() end...return is void");
        }
    }


    /**
     * 根据输入的时间戳参数，截取其"年-月-日"部分。
     * @param timestamp 输入的时间戳
     * @return String 字符串格式的"年-月-日"
     */
//    public static String getDate(Timestamp timestamp)
//    {
//        if (verbose)
//        {
//            System.out.println(
//                    "cappclients.capp.util.CappClientUtil.getDate() begin...");
//        }
//        if (timestamp == null)
//        {
//            return "";
//        }
//        String timeString = timestamp.toString().trim();
//        String resultString = "";
//        for (int i = 0; i < timeString.length(); i++)
//        {
//            if (timeString.substring(i, i + 1).equals(" "))
//            {
//                break;
//            }
//            else
//            {
//                resultString = resultString + timeString.substring(i, i + 1);
//            }
//        }
//        if (verbose)
//        {
//            System.out.println(
//                    "cappclients.capp.util.CappClientUtil.getDate() end...return: " +
//                    resultString);
//        }
//        return resultString;
//    }


    /**
     * 获取指定类里面的指定属性的最大长度。
     * @param class1 指定的类
     * @param s 指定类的指定属性
     * @return int 最大长度
     */
//    public static int getMaxLength(Class class1, String s)
//    {
//        int i = 0;
//
//        return i;
//    }


    /**
     * 判断当前用户是否具有访问权限
     * @param className String
     * @param permission String
     * @return boolean
     * @throws QMRemoteException
     */
//    public static Boolean hasPermission(String className, String permission)
//    {
//        if (verbose)
//        {
//            System.out.println(
//                    "cappclients.capp.util.CappClientUtil.hasPermission() begin...");
//        }
//        RequestServer server = RequestServerFactory.getRequestServer();
//        //调用会话服务获得当前用户
//        //Begin CR2
////        ServiceRequestInfo info1 = new ServiceRequestInfo();
////        info1.setServiceName("SessionService");
////        info1.setMethodName("getCurUser");
////        //参数类型的集合
////        Class[] paraClass =
////                {};
////        info1.setParaClasses(paraClass);
////        //参数值的集合
////        Object[] objs =
////                {};
////        info1.setParaValues(objs);
//       
//        UserIfc user = null; //CR1
//        user = (UserIfc) RequestHelper.request("SsssionService", "getCurUser", 
//            		new Class[]{}, new Object[]{}); //CR1、End CR2
//        
//
//        //调用访问控制服务判断当前用户对指定的类、指定的域、
//        //指定的生命周期状态是否具有指定的访问控制权限
//        //Begin CR2
////        ServiceRequestInfo info2 = new ServiceRequestInfo();
////        info2.setServiceName("AccessControlService");
////        info2.setMethodName("hasAccess");
////        //参数类型的集合
////        Class[] paraClass1 =
////                {ActorIfc.class,
////                String.class,
////                String.class,
////                LifeCycleState.class,
////                String.class
////        }; //CR1
////        info2.setParaClasses(paraClass);
////        //参数值的集合
////        Object[] objs1 =
////                {user, //用户
////                className, //类名
////                DomainHelper.SYSTEM_DOMAIN, //域
////                null, //状态
////                permission //访问控制权限
////        }; //CR1
////        info2.setParaValues(objs);
//        Boolean flag = null;
//
//        flag = (Boolean) RequestHelper.request("AccessControlService", "hasAccess", new Class[]
//				{ ActorIfc.class, String.class, String.class,
//						LifeCycleState.class, String.class },
//						new Object[]
//						{ user, className, DomainHelper.SYSTEM_DOMAIN, null,
//								permission });// End CR2
//       
//        if (verbose)
//        {
//            System.out.println(
//                    "cappclients.capp.util.CappClientUtil.hasPermission() end...return: " +
//                    flag);
//        }
//        return flag;
//    }


    /**
     *查寻工序，工步使用的资源关联
     *@param  bsoID 对象的bsoId
     *@return 使用资源关联的集合
     * @throws QMRemoteException 
     */
    public static Collection getUsageResources(String bsoID) throws QMRemoteException
    {
        Class[] paraclass =
                {String.class, Boolean.TYPE};
        Object[] paraobj =
                {bsoID, Boolean.FALSE};
        Collection c = (Collection) RequestHelper.request("StandardCappService",
                "getUsageResources", paraclass, paraobj);//CR2
        return c;
    }


    /**
     * 判断执行复制操作的源对象和目标对象的工序种类是否一致。
     * @param original 源工序对象
     * @param object   目标工序对象
     * @return 如果工序种类一致，则返回为真；否则返回为假。
     */
    public static boolean isTypeSame(QMProcedureInfo original,
                                     QMProcedureInfo object)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.controller.ProcedureCopyController.isTypeSame() begin...");
        }
        String originalType = original.getTechnicsType().getCodeContent();
        String objectType = object.getTechnicsType().getCodeContent();
        if (originalType.equals(objectType))
        {
            if (verbose)
            {
                System.out.println("cappclients.capp.controller.ProcedureCopyController.isTypeSame() end...return: true");
            }
            return true;
        }
        else
        {
            if (verbose)
            {
                System.out.println("cappclients.capp.controller.ProcedureCopyController.isTypeSame() end...return: false");
            }
            return false;
        }
    }


    /**
     * 判断执行复制操作的源对象和目标对象的工艺种类是否一致。
     * @param original 源工艺对象
     * @param object   目标工艺对象
     * @return 如果工艺种类一致，则返回为真；否则返回为假。
     */
    public static boolean isTypeSame(QMTechnicsInfo original,
                                     QMTechnicsInfo object)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.controller.TechnicsCopyController.isTypeSame() begin...");
        }
        String originalType = original.getTechnicsType().getCodeContent();
        String objectType = object.getTechnicsType().getCodeContent();
        if (originalType.equals(objectType))
        {
            if (verbose)
            {
                System.out.println("cappclients.capp.controller.TechnicsCopyController.isTypeSame() end...return: true");
            }
            return true;
        }
        else
        {
            if (verbose)
            {
                System.out.println("cappclients.capp.controller.TechnicsCopyController.isTypeSame() end...return: false");
            }
            return false;
        }
    }


    /**
     * 获得所有特殊符号,供特殊符号面板使用
     * @return HashMap 键:特殊符号类型 类型String 值:SpeCharaterInfo对象集合 类型Vector
     * @throws QMRemoteException 
     */
    public static HashMap getSpechar() throws QMRemoteException
    {
        if (allSpechar != null)
        {
            return allSpechar;
        }
        else
        {
            allSpechar = new HashMap();
            HashMap table = null;
            table = (HashMap) RequestHelper.request("StandardCappService",//CR2
                        "getAllSpechar", null,
                        null);
            Iterator keys = table.keySet().iterator();
            while (keys.hasNext())
            {
                String type = (String) keys.next();

                Collection c = (Collection) table.get(type);

                Vector vec = new Vector();
                if (c != null && c.size() != 0)
                {
                    for (Iterator it = c.iterator(); it.hasNext(); )
                    {
                        SpecialCharInfo specialCharInfo = (SpecialCharInfo) it.
                                next();
                        SpeCharaterInfo info = new SpeCharaterInfo();
                        info.setDrawingName(specialCharInfo.getDrawingName());
                        info.setDrawingNumber(specialCharInfo.getDrawingNumber());
                        info.setDrawingType(specialCharInfo.getDrawingType().
                                            toString());
                        info.setRemark(specialCharInfo.getRemark());
                        info.setDrawingData(specialCharInfo.getDrawingData());
                        vec.add(info);
                    }
                }
                allSpechar.put(type.toString(), vec);
            }
            return allSpechar;

        }

    }

}
